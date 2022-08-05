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

package com.huawei.hms.rn.ml.textrelatedservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import android.util.Log;
import android.util.SparseArray;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.fr.MLFormRecognitionAnalyzer;
import com.huawei.hms.mlsdk.fr.MLFormRecognitionConstant;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.IOException;

public class HMSFormRecognition extends HMSBase {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSFormRecognition(ReactApplicationContext reactContext) {
        super(reactContext, HMSFormRecognition.class.getSimpleName(), null);
    }

    /**
     * Asynchronous calling entry of form recognition.
     *
     * @param isStop if true stops analyzer and releases resources silently. Using in the last frame recognition recommended
     * @param frameConfiguration MLFrame configuration parameters
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isStop, ReadableMap frameConfiguration, final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLFormRecognitionAnalyzer formRecognitionAnalyzer = HMSObjectCreator.getInstance()
            .createFormRecognizerAnalyzer();
        formRecognitionAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(jsonObject -> {
            if (isStop) {
                stopAnalyzer(formRecognitionAnalyzer);
            }

            try {
                handleResult("asyncAnalyzeFrame", HMSResultCreator.getInstance().getFormRecognitionResult(jsonObject),
                    promise);
            } catch (JSONException e) {
                handleResult("asyncAnalyzeFrame", e, promise);
            }
        }).addOnFailureListener(e -> {
            if (isStop) {
                stopAnalyzer(formRecognitionAnalyzer);
            }

            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }

    /**
     * Synchronous calling entry of form recognition.
     *
     * @param isStop if true stops analyzer and releases resources silently. Using in the last frame recognition recommended
     * @param frameConfiguration MLFrame configuration parameters
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeFrame(boolean isStop, ReadableMap frameConfiguration, final Promise promise) {
        startMethodExecTimer("analyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("analyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLFormRecognitionAnalyzer formRecognitionAnalyzer = HMSObjectCreator.getInstance()
            .createFormRecognizerAnalyzer();
        SparseArray<JsonObject> recognizeResult = formRecognitionAnalyzer.analyseFrame(frame);

        if (isStop) {
            stopAnalyzer(formRecognitionAnalyzer);
        }

        if (recognizeResult != null
            && recognizeResult.get(0).get("retCode").getAsInt() == MLFormRecognitionConstant.SUCCESS) {
            try {
                handleResult("analyzeFrame",
                    HMSResultCreator.getInstance().getSyncFormRecognitionResult(recognizeResult), promise);
            } catch (JSONException e) {
                handleResult("analyzeFrame", e, promise);
            }
        } else {
            handleResult("analyzeFrame", FAILURE, promise);
        }
    }

    private void stopAnalyzer(MLFormRecognitionAnalyzer formRecognitionAnalyzer) {
        try {
            formRecognitionAnalyzer.stop();
            Log.i(getName(), "MLFormRecognitionAnalyzer stop");
        } catch (IOException e) {
            Log.i(getName(), "MLFormRecognitionAnalyzer stop :" + e.getMessage());
        }
    }
}
