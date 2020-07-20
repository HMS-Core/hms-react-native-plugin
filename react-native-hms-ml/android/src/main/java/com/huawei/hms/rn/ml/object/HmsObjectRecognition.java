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

package com.huawei.hms.rn.ml.object;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.objects.MLObject;
import com.huawei.hms.rn.ml.constants.Constants;
import com.huawei.hms.rn.ml.frame.HmsFrameManager;

import static com.huawei.hms.rn.ml.constants.Errors.E_OBJECT_MODULE;

public class HmsObjectRecognition extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsObjectRecognition.class.getSimpleName();

    public HmsObjectRecognition(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return Constants.getObjectRecognitionConstants();
    }

    @ReactMethod
    public void create(ReadableMap readableMap, final Promise promise) {
        HmsObjectRecognitionManager.getInstance().setAnalyzerSetting(readableMap);
        promise.resolve("ObjectAnalyzerSetting set");
    }

    @ReactMethod
    public void analyze(final Promise promise) {
        HmsObjectRecognitionManager.getInstance().setAnalyzer(MLAnalyzerFactory.getInstance().getLocalObjectAnalyzer(HmsObjectRecognitionManager.getInstance().getAnalyzerSetting()));
        Task<List<MLObject>> task = HmsObjectRecognitionManager.getInstance().getAnalyzer().asyncAnalyseFrame(HmsFrameManager.getInstance().getFrame());
        task.addOnSuccessListener(Object -> promise.resolve(HmsObjectRecognitionManager.getInstance().analyzeResult(Object)));
        task.addOnFailureListener(e -> promise.reject(E_OBJECT_MODULE, e.getMessage()));
    }

    @ReactMethod
    public void equals(ReadableMap readableMap, final Promise promise) {
        promise.resolve(HmsObjectRecognitionManager.getInstance().getAnalyzerSetting().equals(HmsObjectRecognitionManager.getInstance().createObjectAnalyzerSetting(readableMap)));
    }

    @ReactMethod
    public void getAnalyzerType(final Promise promise) {
        promise.resolve(HmsObjectRecognitionManager.getInstance().getAnalyzerSetting().getAnalyzerType());
    }

    @ReactMethod
    public void hashCode(final Promise promise) {
        promise.resolve(HmsObjectRecognitionManager.getInstance().getAnalyzerSetting().hashCode());
    }

    @ReactMethod
    public void isClassificationAllowed(final Promise promise) {
        promise.resolve(HmsObjectRecognitionManager.getInstance().getAnalyzerSetting().isClassificationAllowed());
    }

    @ReactMethod
    public void isMultipleResultsAllowed(final Promise promise) {
        promise.resolve(HmsObjectRecognitionManager.getInstance().getAnalyzerSetting().isMultipleResultsAllowed());
    }

    @ReactMethod
    public void stop(final Promise promise) {
        try {
            HmsObjectRecognitionManager.getInstance().getAnalyzer().stop();
            promise.resolve("ObjectAnalyzer stopped");
        } catch (IOException e) {
            promise.reject(E_OBJECT_MODULE, e.getMessage());
        }
    }

}
