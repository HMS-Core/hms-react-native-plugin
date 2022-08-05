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

package com.huawei.hms.rn.ml.imagerelatedservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import android.util.Log;
import android.util.SparseArray;

import com.huawei.hms.mlsdk.classification.MLImageClassification;
import com.huawei.hms.mlsdk.classification.MLImageClassificationAnalyzer;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;

public class HMSImageClassification extends HMSBase {

    /**
     * Initializes module
     *
     * @param context app context
     */
    public HMSImageClassification(ReactApplicationContext context) {
        super(context, HMSImageClassification.class.getSimpleName(), null);
    }

    /**
     * Classifies images by synchronous processing.
     *
     * @param isRemote if true classifies on-cloud otherwise on-device
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param analyzerSetting Setting for creating analyzer
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeFrame(boolean isRemote, boolean isStop, ReadableMap frameConfiguration,
        ReadableMap analyzerSetting, final Promise promise) {
        startMethodExecTimer("analyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("analyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLImageClassificationAnalyzer classificationAnalyzer = HMSObjectCreator.getInstance()
            .createClassificationAnalyzer(isRemote, analyzerSetting);
        SparseArray<MLImageClassification> results = classificationAnalyzer.analyseFrame(frame);

        if (isStop) {
            stopSilent(classificationAnalyzer);
        }

        handleResult("analyzeFrame", HMSResultCreator.getInstance().getImageClassificationResult(results), promise);
    }

    /**
     * Classifies images by asynchronous processing.
     *
     * @param isRemote if true classifies on-cloud otherwise on-device
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param analyzerSetting Setting for creating analyzer
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isRemote, boolean isStop, ReadableMap frameConfiguration,
        ReadableMap analyzerSetting, final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLImageClassificationAnalyzer classificationAnalyzer = HMSObjectCreator.getInstance()
            .createClassificationAnalyzer(isRemote, analyzerSetting);
        classificationAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(mlImageClassifications -> {
            if (isStop) {
                stopSilent(classificationAnalyzer);
            }
            handleResult("asyncAnalyzeFrame",
                HMSResultCreator.getInstance().getImageClassificationResult(mlImageClassifications), promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                stopSilent(classificationAnalyzer);
            }
            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }

    /**
     * Releases resources of analyzer
     *
     * @param classificationAnalyzer analyzer
     */
    private void stopSilent(MLImageClassificationAnalyzer classificationAnalyzer) {
        try {
            classificationAnalyzer.stop();
            Log.i(getName(), "MLImageClassificationAnalyzer stop");
        } catch (IOException e) {
            Log.i(getName(), "MLImageClassificationAnalyzer stop" + e.getMessage());
        }
    }

}
