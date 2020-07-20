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

package com.huawei.hms.rn.ml.text;

import java.io.IOException;
import java.util.Map;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.text.MLText;
import com.huawei.hms.rn.ml.constants.Constants;
import com.huawei.hms.rn.ml.frame.HmsFrameManager;
import com.huawei.hms.rn.ml.utils.RNUtils;

import static com.huawei.hms.rn.ml.constants.Errors.E_LOCAL_TEXT_MODULE;

public class HmsTextRecognitionLocal extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsTextRecognitionLocal.class.getSimpleName();

    public HmsTextRecognitionLocal(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return Constants.getLocalTextSettingConstants();
    }

    @ReactMethod
    public void create(ReadableMap localTextSetting, final Promise promise) {
        HmsTextRecognitionLocalManager.getInstance().setMlLocalTextSetting(localTextSetting);
        promise.resolve("LocalTextSetting created");
    }

    @ReactMethod
    public void getLanguage(final Promise promise) {
        promise.resolve(HmsTextRecognitionLocalManager.getInstance().getMlLocalTextSetting().getLanguage());
    }

    @ReactMethod
    public void getOCRMode(final Promise promise) {
        promise.resolve(HmsTextRecognitionLocalManager.getInstance().getMlLocalTextSetting().getOCRMode());
    }

    @ReactMethod
    public void equals(ReadableMap readableMap, final Promise promise) {
        promise.resolve(HmsTextRecognitionLocalManager
                .getInstance()
                .getMlLocalTextSetting()
                .equals(HmsTextRecognitionLocalManager
                        .getInstance()
                        .createLocalTextRecognitionSetting(readableMap)));
    }

    @ReactMethod
    public void hashCode(final Promise promise) {
        promise.resolve(HmsTextRecognitionLocalManager
                .getInstance()
                .getMlLocalTextSetting()
                .hashCode());
    }

    @ReactMethod
    public void analyze(Boolean isBlock, Promise promise) {
        HmsTextRecognitionLocalManager.getInstance().setMlTextAnalyzer(MLAnalyzerFactory.getInstance().getLocalTextAnalyzer(HmsTextRecognitionLocalManager.getInstance().getMlLocalTextSetting()));
        Task<MLText> task = HmsTextRecognitionLocalManager.getInstance().getMlTextAnalyzer().asyncAnalyseFrame(HmsFrameManager.getInstance().getFrame());
        task.addOnSuccessListener(mlText -> {
            if (isBlock) {
                promise.resolve(RNUtils.blockListIntoWritableArray(mlText.getBlocks()));
            } else {
                promise.resolve(mlText.getStringValue());
            }
        });

        task.addOnFailureListener(e -> {
            promise.reject(E_LOCAL_TEXT_MODULE, e.getMessage());
        });
    }

    @ReactMethod
    public void close(Promise promise) {
        try {
            HmsTextRecognitionLocalManager.getInstance().getMlTextAnalyzer().close();
            promise.resolve("LocalTextRecognitionAnalyzer closed");
        } catch (IOException e) {
            promise.reject(E_LOCAL_TEXT_MODULE, e.getMessage());
        }
    }

    @ReactMethod
    public void getAnalyzeType(Promise promise) {
        promise.resolve(HmsTextRecognitionLocalManager.getInstance().getMlTextAnalyzer().getAnalyseType());
    }

    @ReactMethod
    public void isAvailable(Promise promise) {
        promise.resolve(HmsTextRecognitionLocalManager.getInstance().getMlTextAnalyzer().isAvailable());
    }

}
