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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.HANDKEYPOINT_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypointAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

public class HMSHandKeypointDetection extends HMSBase {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSHandKeypointDetection(ReactApplicationContext reactContext) {
        super(reactContext, HMSHandKeypointDetection.class.getSimpleName(), HANDKEYPOINT_CONSTANTS);
    }

    /**
     * Detects hand key points in an input image in synchronous mode.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param analyzerSetting Analyzer configuration to create analyzer
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeFrame(boolean isStop, ReadableMap frameConfiguration, ReadableMap analyzerSetting,
        final Promise promise) {
        startMethodExecTimer("analyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("analyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLHandKeypointAnalyzer handKeypointAnalyzer = HMSObjectCreator.getInstance()
            .createHandKeyPointAnalyzer(analyzerSetting);
        WritableMap wm = HMSResultCreator.getInstance()
            .getHandKeyPointResults(handKeypointAnalyzer.analyseFrame(frame));

        if (isStop) {
            handKeypointAnalyzer.stop();
        }

        handleResult("analyzeFrame", wm, promise);
    }

    /**
     * Detects hand key points in an input image in asynchronous mode.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param analyzerSetting Analyzer configuration to create analyzer
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isStop, ReadableMap frameConfiguration, ReadableMap analyzerSetting,
        final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLHandKeypointAnalyzer handKeyPointAnalyzer = HMSObjectCreator.getInstance()
            .createHandKeyPointAnalyzer(analyzerSetting);
        handKeyPointAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(keypoints -> {
            if (isStop) {
                handKeyPointAnalyzer.stop();
            }

            handleResult("asyncAnalyzeFrame", HMSResultCreator.getInstance().getHandKeyPointResults(keypoints),
                promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                handKeyPointAnalyzer.stop();
            }

            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }
}
