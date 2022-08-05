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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.OBJECT_RECOGNITION_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import android.util.Log;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.objects.MLObjectAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.io.IOException;

public class HMSObjectRecognition extends HMSBase {

    /**
     * Initializes module
     *
     * @param context app context
     */
    public HMSObjectRecognition(ReactApplicationContext context) {
        super(context, HMSObjectRecognition.class.getSimpleName(), OBJECT_RECOGNITION_CONSTANTS);
    }

    /**
     * Recognizes objects in images by asynchronous processing.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param objectAnalyzerSettingConfiguration Setting for creating analyzer
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isStop, ReadableMap frameConfiguration,
        ReadableMap objectAnalyzerSettingConfiguration, final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLObjectAnalyzer objectAnalyzer = HMSObjectCreator.getInstance()
            .createObjectAnalyzer(objectAnalyzerSettingConfiguration);
        objectAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(list -> {
            if (isStop) {
                stopAnalyzer(objectAnalyzer);
            }
            handleResult("asyncAnalyzeFrame", HMSResultCreator.getInstance().getObjectResult(list), promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                stopAnalyzer(objectAnalyzer);
            }
            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }

    /**
     * Recognizes objects in images by synchronous processing.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param objectAnalyzerSettingConfiguration Setting for creating analyzer
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeFrame(boolean isStop, ReadableMap frameConfiguration,
        ReadableMap objectAnalyzerSettingConfiguration, final Promise promise) {
        startMethodExecTimer("analyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("analyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLObjectAnalyzer objectAnalyzer = HMSObjectCreator.getInstance()
            .createObjectAnalyzer(objectAnalyzerSettingConfiguration);
        WritableMap objectResult = HMSResultCreator.getInstance().getObjectResult(objectAnalyzer.analyseFrame(frame));

        if (isStop) {
            stopAnalyzer(objectAnalyzer);
        }

        handleResult("analyzeFrame", objectResult, promise);
    }

    /**
     * Releases resources of analyzer
     *
     * @param objectAnalyzer analyzer
     */
    private void stopAnalyzer(MLObjectAnalyzer objectAnalyzer) {
        try {
            objectAnalyzer.stop();
            Log.i(getName(), "MLObjectAnalyzer stop");
        } catch (IOException e) {
            Log.i(getName(), "MLObjectAnalyzer stop:" + e.getMessage());
        }
    }
}
