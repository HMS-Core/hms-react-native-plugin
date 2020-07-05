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
package com.huawei.hms.rn.ml.langdetect;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.langdetect.MLDetectedLang;
import com.huawei.hms.mlsdk.langdetect.MLLangDetectorFactory;
import com.huawei.hms.rn.ml.constants.Constants;

import java.util.List;
import java.util.Map;

import static com.huawei.hms.rn.ml.constants.Errors.E_LANG_DETECT_MODULE;

public class HmsLanguageDetection extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME =  HmsLanguageDetection.class.getSimpleName();

    HmsLanguageDetection(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return Constants.getLanguageDetectionConstants();
    }

    @ReactMethod
    public void create(ReadableMap rm, final Promise promise) {
        HmsLanguageDetectionManager.getInstance().setMlRemoteLangDetectorSetting(rm);
        promise.resolve("LanguageDetectorSetting set");
    }

    @ReactMethod
    public void probabilityDetect(String sourceText, final Promise promise) {
        HmsLanguageDetectionManager.getInstance().setMlRemoteLangDetector(MLLangDetectorFactory.getInstance().getRemoteLangDetector(HmsLanguageDetectionManager.getInstance().getMlRemoteLangDetectorSetting()));
        Task<List<MLDetectedLang>> probabilityDetectTask = HmsLanguageDetectionManager.getInstance().getMlRemoteLangDetector().probabilityDetect(sourceText);
        probabilityDetectTask.addOnSuccessListener(result -> {
            promise.resolve(HmsLanguageDetectionManager.getInstance().multipleLanguageResults(result));
        }).addOnFailureListener(e -> {
            promise.reject(E_LANG_DETECT_MODULE, e.getMessage());
        });
    }

    @ReactMethod
    public void firstBestDetect(String sourceText, final Promise promise) {
        HmsLanguageDetectionManager.getInstance().setMlRemoteLangDetector(MLLangDetectorFactory.getInstance().getRemoteLangDetector(HmsLanguageDetectionManager.getInstance().getMlRemoteLangDetectorSetting()));
        Task<String> firstBestDetectTask = HmsLanguageDetectionManager.getInstance().getMlRemoteLangDetector().firstBestDetect(sourceText);
        firstBestDetectTask.addOnSuccessListener(s -> {
            promise.resolve(s);
        }).addOnFailureListener(e -> {
            promise.reject(E_LANG_DETECT_MODULE, e.getMessage());
        });
    }

    @ReactMethod
    public void stop(final Promise promise) {
        HmsLanguageDetectionManager.getInstance().getMlRemoteLangDetector().stop();
        promise.resolve("languageDetector stop");
    }

    @ReactMethod
    public void equals(ReadableMap rm, final Promise promise) {
        promise.resolve(HmsLanguageDetectionManager.getInstance().getMlRemoteLangDetectorSetting().equals(HmsLanguageDetectionManager.getInstance().createLanguageDetector(rm)));
    }

    @ReactMethod
    public void getTrustedThreshold(final Promise promise) {
        promise.resolve(HmsLanguageDetectionManager.getInstance().getMlRemoteLangDetectorSetting().getTrustedThreshold());
    }

    @ReactMethod
    public void hashCode(final Promise promise) {
        promise.resolve(HmsLanguageDetectionManager.getInstance().getMlRemoteLangDetectorSetting().hashCode());
    }

}
