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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

import static com.huawei.hms.rn.ml.constants.Errors.E_REMOTE_TEXT_MODULE;

public class HmsTextRecognitionRemote extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsTextRecognitionRemote.class.getSimpleName();

    HmsTextRecognitionRemote(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return Constants.getRemoteTextSettingConstants();
    }

    @ReactMethod
    public void create(ReadableMap rm, Promise promise) {
        HmsTextRecognitionRemoteManager.getInstance().setMlRemoteTextSetting(rm);
        promise.resolve("RemoteTextSetting created successfully");
    }

    @ReactMethod
    public void equals(ReadableMap readableMap, final Promise promise) {
        promise.resolve(HmsTextRecognitionRemoteManager
                .getInstance()
                .getMlRemoteTextSetting()
                .equals(HmsTextRecognitionRemoteManager
                        .getInstance()
                        .createRemoteTextRecognitionSetting(readableMap)));
    }

    @ReactMethod
    public void getBorderType(final Promise promise) {
        promise.resolve(HmsTextRecognitionRemoteManager.getInstance().getMlRemoteTextSetting().getBorderType());
    }

    @ReactMethod
    public void getLanguageList(final Promise promise) {
        promise.resolve(RNUtils.stringListIntoWritableArray(HmsTextRecognitionRemoteManager.getInstance().getMlRemoteTextSetting().getLanguageList()));
    }

    @ReactMethod
    public void hashCode(final Promise promise) {
        promise.resolve(HmsTextRecognitionRemoteManager.getInstance().getMlRemoteTextSetting().hashCode());
    }

    @ReactMethod
    public void getTextDensityScene(final Promise promise) {
        promise.resolve(HmsTextRecognitionRemoteManager.getInstance().getMlRemoteTextSetting().getTextDensityScene());
    }

    @ReactMethod
    public void analyze(Boolean isBlock, Promise promise) {
        HmsTextRecognitionRemoteManager.getInstance().setMlTextAnalyzer(MLAnalyzerFactory.getInstance().getRemoteTextAnalyzer(HmsTextRecognitionRemoteManager.getInstance().getMlRemoteTextSetting()));
        Task<MLText> task = HmsTextRecognitionRemoteManager.getInstance().getMlTextAnalyzer().asyncAnalyseFrame(HmsFrameManager.getInstance().getFrame());
        task.addOnSuccessListener(mlText -> {
            if (isBlock) {
                promise.resolve(RNUtils.blockListIntoWritableArray(mlText.getBlocks()));
            } else {
                promise.resolve(mlText.getStringValue());
            }
        });

        task.addOnFailureListener(e -> {
            promise.reject(E_REMOTE_TEXT_MODULE, e.getMessage());
        });
    }

    @ReactMethod
    public void close(Promise promise) {
        try {
            HmsTextRecognitionRemoteManager.getInstance().getMlTextAnalyzer().close();
            promise.resolve("RemoteTextRecognitionAnalyzer closed");
        } catch (IOException e) {
            promise.reject(E_REMOTE_TEXT_MODULE, e.getMessage());
        }
    }

    @ReactMethod
    public void getAnalyzeType(Promise promise) {
        promise.resolve(HmsTextRecognitionRemoteManager.getInstance().getMlTextAnalyzer().getAnalyseType());
    }

    @ReactMethod
    public void isAvailable(Promise promise) {
        promise.resolve(HmsTextRecognitionRemoteManager.getInstance().getMlTextAnalyzer().isAvailable());
    }

}
