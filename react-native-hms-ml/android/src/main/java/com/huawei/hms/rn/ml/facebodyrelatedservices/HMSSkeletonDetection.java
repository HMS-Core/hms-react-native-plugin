/*
    Copyright 2020-2022. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.ml.facebodyrelatedservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.SKELETON_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.DATA_SET_NOT_VALID;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import android.util.Log;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.skeleton.MLSkeletonAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.io.IOException;

public class HMSSkeletonDetection extends HMSBase {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSSkeletonDetection(ReactApplicationContext reactContext) {
        super(reactContext, HMSSkeletonDetection.class.getSimpleName(), SKELETON_CONSTANTS);
    }

    /**
     * Detects skeleton points in an input image in synchronous mode.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param analyzeType analyze type
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeFrame(boolean isStop, int analyzeType, ReadableMap frameConfiguration, final Promise promise) {
        startMethodExecTimer("analyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("analyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLSkeletonAnalyzer skeletonAnalyzer = HMSObjectCreator.getInstance().createSkeletonAnalyzer(analyzeType);
        WritableMap wm = HMSResultCreator.getInstance().getSkeletonSyncResults(skeletonAnalyzer.analyseFrame(frame));

        if (isStop) {
            stopAnalyzer(skeletonAnalyzer);
        }

        handleResult("analyzeFrame", wm, promise);
    }

    /**
     * Detects skeleton points in an input image in asynchronous mode.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param analyzeType analyze type
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isStop, int analyzeType, ReadableMap frameConfiguration,
        final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLSkeletonAnalyzer skeletonAnalyzer = HMSObjectCreator.getInstance().createSkeletonAnalyzer(analyzeType);
        skeletonAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(mlSkeletons -> {
            if (isStop) {
                stopAnalyzer(skeletonAnalyzer);
            }
            handleResult("asyncAnalyzeFrame", HMSResultCreator.getInstance().getSkeletonAsyncResults(mlSkeletons),
                promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                stopAnalyzer(skeletonAnalyzer);
            }
            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }

    /**
     * Calculates the similarity between two sets of skeleton data.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param analyzeType analyze type
     * @param dataSet1 first data set
     * @param dataSet2 second data set
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void calculateSimilarity(boolean isStop, int analyzeType, ReadableArray dataSet1, ReadableArray dataSet2,
        final Promise promise) {
        startMethodExecTimer("calculateSimilarity");

        if (dataSet1.size() == 0 || dataSet2.size() == 0) {
            handleResult("calculateSimilarity", DATA_SET_NOT_VALID, promise);
            return;
        }

        MLSkeletonAnalyzer skeletonAnalyzer = HMSObjectCreator.getInstance().createSkeletonAnalyzer(analyzeType);
        float similarity = skeletonAnalyzer.caluteSimilarity(HMSUtils.getInstance().convertRaToSkeletonList(dataSet1),
            HMSUtils.getInstance().convertRaToSkeletonList(dataSet2));

        if (isStop) {
            stopAnalyzer(skeletonAnalyzer);
        }

        handleResult("calculateSimilarity", HMSResultCreator.getInstance().floatResult(similarity), promise);
    }

    /**
     * Releases resources of analyzer
     *
     * @param skeletonAnalyzer analyzer
     */
    private void stopAnalyzer(MLSkeletonAnalyzer skeletonAnalyzer) {
        try {
            skeletonAnalyzer.stop();
            Log.i(getName(), "MLSkeletonAnalyzer stop");
        } catch (IOException e) {
            Log.i(getName(), "MLSkeletonAnalyzer stop :" + e.getMessage());
        }
    }

}
