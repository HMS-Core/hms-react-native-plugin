/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.ml.document;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.document.MLDocument;
import com.huawei.hms.rn.ml.constants.Constants;
import com.huawei.hms.rn.ml.frame.HmsFrameManager;
import com.huawei.hms.rn.ml.utils.RNUtils;

import java.io.IOException;
import java.util.Map;

import static com.huawei.hms.rn.ml.constants.Errors.E_DOCUMENT_MODULE;

public class HmsDocumentRecognition extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsDocumentRecognition.class.getSimpleName();

    HmsDocumentRecognition(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return Constants.getDocumentRecognitionConstants();
    }

    @ReactMethod
    public void create(ReadableMap readableMap, Promise promise) {
        HmsDocumentRecognitionManager.getInstance().setMlDocumentSetting(readableMap);
        promise.resolve("Created document setting");
    }

    @ReactMethod
    public void analyze(Boolean isBlock, Promise promise) {
        HmsDocumentRecognitionManager
                .getInstance()
                .setAnalyzer(MLAnalyzerFactory
                        .getInstance()
                        .getRemoteDocumentAnalyzer(HmsDocumentRecognitionManager
                                .getInstance()
                                .getMlDocumentSetting()));
        Task<MLDocument> task = HmsDocumentRecognitionManager
                .getInstance()
                .getAnalyzer()
                .asyncAnalyseFrame(HmsFrameManager
                        .getInstance()
                        .getFrame());
        task.addOnSuccessListener(document -> {
            if (isBlock) {
                promise.resolve(RNUtils.documentBlockIntoWritableArray(document.getBlocks()));
            } else {
                promise.resolve(document.getStringValue());
            }
        });
        task.addOnFailureListener(e -> promise.reject(E_DOCUMENT_MODULE, e.getMessage()));
    }

    @ReactMethod
    public void close(Promise promise) {
        try {
            HmsDocumentRecognitionManager.getInstance().getAnalyzer().close();
            promise.resolve("DocumentRecognitionAnalyzer stopped");
        } catch (IOException e) {
            promise.reject(E_DOCUMENT_MODULE, e.getMessage());
        }
    }

    @ReactMethod
    public void equals(ReadableMap readableMap, Promise promise) {
        promise.resolve(HmsDocumentRecognitionManager
                .getInstance()
                .getMlDocumentSetting()
                .equals(HmsDocumentRecognitionManager
                        .getInstance()
                        .createDocumentRecognitionSetting(readableMap)));
    }

    @ReactMethod
    public void getBorderType(Promise promise) {
        promise.resolve(HmsDocumentRecognitionManager
                .getInstance()
                .getMlDocumentSetting()
                .getBorderType());
    }

    @ReactMethod
    public void getLanguageList(Promise promise) {
        promise.resolve(RNUtils
                        .stringListIntoWritableArray(HmsDocumentRecognitionManager
                        .getInstance()
                        .getMlDocumentSetting()
                        .getLanguageList()));
    }

    @ReactMethod
    public void hashCode(Promise promise) {
        promise.resolve(HmsDocumentRecognitionManager.getInstance().getMlDocumentSetting().hashCode());
    }

    @ReactMethod
    public void isEnableFingerPrintVerification(Promise promise) {
        promise.resolve(HmsDocumentRecognitionManager
                .getInstance()
                .getMlDocumentSetting()
                .isEnableFingerprintVerification());
    }

}
