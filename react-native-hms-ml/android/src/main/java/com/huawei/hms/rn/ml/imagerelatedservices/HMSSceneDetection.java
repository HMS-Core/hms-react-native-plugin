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

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.scd.MLSceneDetectionAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

public class HMSSceneDetection extends HMSBase {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSSceneDetection(ReactApplicationContext reactContext) {
        super(reactContext, HMSSceneDetection.class.getSimpleName(), null);
    }

    /**
     * Detects scene information in an input image in synchronous mode.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration configuration to obtain frame
     * @param confidence confidence value
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeFrame(boolean isStop, double confidence, ReadableMap frameConfiguration, final Promise promise) {
        startMethodExecTimer("analyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("analyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLSceneDetectionAnalyzer sceneDetectionAnalyzer = HMSObjectCreator.getInstance()
            .getSceneDetectionAnalyzer(confidence);
        WritableMap result = HMSResultCreator.getInstance()
            .getSceneDetectionResultSync(sceneDetectionAnalyzer.analyseFrame(frame));

        if (isStop) {
            sceneDetectionAnalyzer.stop();
        }

        handleResult("analyzeFrame", result, promise);
    }

    /**
     * Detects scene information in an input image in asynchronous mode.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration configuration to obtain frame
     * @param confidence confidence value
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isStop, double confidence, ReadableMap frameConfiguration,
        final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLSceneDetectionAnalyzer sceneDetectionAnalyzer = HMSObjectCreator.getInstance()
            .getSceneDetectionAnalyzer(confidence);
        sceneDetectionAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(mlSceneDetections -> {
            if (isStop) {
                sceneDetectionAnalyzer.stop();
            }
            handleResult("asyncAnalyzeFrame",
                HMSResultCreator.getInstance().getSceneDetectionResultAsync(mlSceneDetections), promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                sceneDetectionAnalyzer.stop();
            }
            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }
}
