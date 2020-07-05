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

package com.huawei.hms.rn.ml.classification;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.classification.MLImageClassification;
import com.huawei.hms.rn.ml.frame.HmsFrameManager;

import java.io.IOException;
import java.util.List;

import static com.huawei.hms.rn.ml.constants.Errors.E_IMAGE_CLASSIFICATION_MODULE;

public class HmsClassificationLocal extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsClassificationLocal.class.getSimpleName();

    HmsClassificationLocal(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void create(ReadableMap readableMap, final Promise promise) {
        HmsClassificationManager.getInstance().setLocalClassificationAnalyzerSetting(readableMap);
        promise.resolve("ClassificationSetting set");
    }

    @ReactMethod
    public void analyze(final Promise promise) {
        HmsClassificationManager
                .getInstance()
                .setMlImageClassificationAnalyzer(MLAnalyzerFactory
                        .getInstance()
                        .getLocalImageClassificationAnalyzer(HmsClassificationManager
                                .getInstance()
                                .getLocalAnalyzerSetting()));
        Task<List<MLImageClassification>> task = HmsClassificationManager
                .getInstance()
                .getMlImageClassificationAnalyzer()
                .asyncAnalyseFrame(HmsFrameManager
                        .getInstance()
                        .getFrame());
        task.addOnSuccessListener(classifications -> promise.resolve(HmsClassificationManager
                .getInstance()
                .classificationResult(classifications)));
        task.addOnFailureListener(e -> promise.reject(E_IMAGE_CLASSIFICATION_MODULE, e.getMessage()));
    }

    @ReactMethod
    public void stop(final Promise promise) {
        try {
            HmsClassificationManager.getInstance().getMlImageClassificationAnalyzer().stop();
            promise.resolve("RemoteAnalyzer stop");
        } catch (IOException e) {
            promise.reject(E_IMAGE_CLASSIFICATION_MODULE, e.getMessage());
        }
    }

    @ReactMethod
    public void equals(ReadableMap readableMap, final Promise promise) {
        promise.resolve(HmsClassificationManager.getInstance().getLocalAnalyzerSetting().equals(HmsClassificationManager.getInstance().createLocalClassificationAnalyzerSetting(readableMap)));
    }

    @ReactMethod
    public void getMinAcceptablePossibility(final Promise promise) {
        promise.resolve(HmsClassificationManager.getInstance().getLocalAnalyzerSetting().getMinAcceptablePossibility());
    }

    @ReactMethod
    public void hashCode(final Promise promise) {
        promise.resolve(HmsClassificationManager.getInstance().getLocalAnalyzerSetting().hashCode());
    }

}
