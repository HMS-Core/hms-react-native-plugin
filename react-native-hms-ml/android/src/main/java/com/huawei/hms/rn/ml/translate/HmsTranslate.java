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

package com.huawei.hms.rn.ml.translate;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.translate.MLTranslatorFactory;

import static com.huawei.hms.rn.ml.constants.Errors.E_TRANSLATE_MODULE;

public class HmsTranslate extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsTranslate.class.getSimpleName();

    HmsTranslate(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void create(ReadableMap readableMap, final Promise promise) {
        HmsTranslateManager.getInstance().setTranslatorSetting(readableMap);
        promise.resolve("Translate Setting set");
    }

    @ReactMethod
    public void translate(String sourceText, final Promise promise) {
        HmsTranslateManager.getInstance().setMlRemoteTranslator(MLTranslatorFactory.getInstance().getRemoteTranslator(HmsTranslateManager.getInstance().getTranslatorSetting()));
        final Task<String> task = HmsTranslateManager.getInstance().getMlRemoteTranslator().asyncTranslate(sourceText);
        task.addOnSuccessListener(text -> promise.resolve(text));
        task.addOnFailureListener(e -> promise.reject(E_TRANSLATE_MODULE, e.getMessage()));
    }

    @ReactMethod
    public void stop(final Promise promise) {
        HmsTranslateManager.getInstance().getMlRemoteTranslator().stop();
        promise.resolve("MLRemoteTranslator stop");
    }

    @ReactMethod
    public void equals(ReadableMap readableMap, final Promise promise) {
        promise.resolve(HmsTranslateManager.getInstance().getTranslatorSetting().equals(HmsTranslateManager.getInstance().createSetting(readableMap)));
    }

    @ReactMethod
    public void getSourceLanguageCode(final Promise promise) {
        promise.resolve(HmsTranslateManager.getInstance().getTranslatorSetting().getSourceLangCode());
    }

    @ReactMethod
    public void getTargetLanguageCode(final Promise promise) {
        promise.resolve(HmsTranslateManager.getInstance().getTranslatorSetting().getTargetLangCode());
    }

    @ReactMethod
    public void hashCode(final Promise promise) {
        promise.resolve(HmsTranslateManager.getInstance().getTranslatorSetting().hashCode());
    }

}
