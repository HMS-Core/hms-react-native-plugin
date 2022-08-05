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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.DOCUMENT_RECOGNITION_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import android.util.Log;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.document.MLDocumentAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;

public class HMSDocumentRecognition extends HMSBase {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSDocumentRecognition(ReactApplicationContext reactContext) {
        super(reactContext, HMSDocumentRecognition.class.getSimpleName(), DOCUMENT_RECOGNITION_CONSTANTS);
    }

    /**
     * Detects document information in an input image.
     *
     * @param isStop if true releases resources for analyzer. Recommended to use this in latest frame for better performance.
     * @param frameConfiguration MLFrame configuration parameters
     * @param documentConfiguration Analyzer configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isStop, ReadableMap frameConfiguration, ReadableMap documentConfiguration,
        final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            promise.resolve(FRAME_NULL.getStatusAndMessage());
            return;
        }

        MLDocumentAnalyzer documentAnalyzer = HMSObjectCreator.getInstance()
            .createDocumentAnalyzer(documentConfiguration);
        documentAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(mlDocument -> {
            if (isStop) {
                stopSilent(documentAnalyzer);
            }
            handleResult("asyncAnalyzeFrame", HMSResultCreator.getInstance().getDocumentRecognitionResult(mlDocument),
                promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                stopSilent(documentAnalyzer);
            }
            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }

    /**
     * Releases resources of analyzer
     *
     * @param documentAnalyzer analyzer
     */
    private void stopSilent(MLDocumentAnalyzer documentAnalyzer) {
        try {
            documentAnalyzer.stop();
            Log.i(getName(), "MLDocumentAnalyzer stopped");
        } catch (IOException e) {
            Log.i(getName(), "MLDocumentAnalyzer not stopped because :" + e.getMessage());
        }
    }
}
