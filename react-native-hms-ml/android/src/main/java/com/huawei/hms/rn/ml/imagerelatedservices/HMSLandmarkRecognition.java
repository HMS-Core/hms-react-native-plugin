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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.LANDMARK_RECOGNITION_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import android.util.Log;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmarkAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;

import javax.annotation.Nonnull;

public class HMSLandmarkRecognition extends HMSBase {

    /**
     * Initializes module
     *
     * @param context app context
     */
    public HMSLandmarkRecognition(ReactApplicationContext context) {
        super(context, HMSLandmarkRecognition.class.getSimpleName(), LANDMARK_RECOGNITION_CONSTANTS);
    }

    /**
     * Recognizes landmarks in images by asynchronous processing.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param landmarkAnalyzerConfiguration Setting for creating analyzer
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isStop, ReadableMap frameConfiguration,
        ReadableMap landmarkAnalyzerConfiguration, final Promise promise) {
        startMethodExecTimer("asyncAnalyseFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLRemoteLandmarkAnalyzer remoteLandmarkAnalyzer = HMSObjectCreator.getInstance()
            .createLandmarkAnalyzer(landmarkAnalyzerConfiguration);
        remoteLandmarkAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(mlRemoteLandmarks -> {
            if (isStop) {
                stopAnalyzer(remoteLandmarkAnalyzer);
            }
            handleResult("asyncAnalyzeFrame",
                HMSResultCreator.getInstance().getLandmarkDetectionResults(mlRemoteLandmarks), promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                stopAnalyzer(remoteLandmarkAnalyzer);
            }
            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }

    /**
     * Releases resources of analyzer
     *
     * @param landmarkAnalyzer analyzer
     */
    private void stopAnalyzer(@Nonnull MLRemoteLandmarkAnalyzer landmarkAnalyzer) {
        try {
            landmarkAnalyzer.stop();
            Log.i(getName(), "MLRemoteLandmarkAnalyzer stop");
        } catch (IOException e) {
            Log.i(getName(), "MLRemoteLandmarkAnalyzer stop:" + e.getMessage());
        }
    }
}
