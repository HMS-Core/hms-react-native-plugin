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

package com.huawei.hms.rn.ml.card;

import java.io.IOException;
import java.util.Map;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.card.MLCardAnalyzerFactory;
import com.huawei.hms.mlsdk.card.icr.MLIdCard;
import com.huawei.hms.rn.ml.constants.Constants;
import com.huawei.hms.rn.ml.frame.HmsFrameManager;

import static com.huawei.hms.rn.ml.constants.Errors.E_CARD_MODULE;

public class HmsIcrRecognition extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsIcrRecognition.class.getSimpleName();

    public HmsIcrRecognition(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return Constants.getIcrConstants();
    }

    @ReactMethod
    public void create(ReadableMap readableMap, Boolean isRemote, final Promise promise) {
        HmsCardRecognitionManager.getInstance().setMlIcrAnalyzerSetting(readableMap, isRemote);
        promise.resolve("IcrRecognition Setting Set");
    }

    @ReactMethod
    public void analyze(Boolean isRemote, final Promise promise) {
        Task<MLIdCard> task;
        if (isRemote) {
            HmsCardRecognitionManager.getInstance().setRemoteIcrAnalyzer(MLCardAnalyzerFactory
                    .getInstance()
                    .getRemoteIcrAnalyzer(HmsCardRecognitionManager.getInstance()
                            .getMlRemoteIcrAnalyzerSetting()));
            task = HmsCardRecognitionManager.getInstance()
                    .getRemoteIcrAnalyzer()
                    .asyncAnalyseFrame(HmsFrameManager.getInstance().getFrame());
        } else {
            HmsCardRecognitionManager.getInstance()
                    .setAnalyzer(MLCardAnalyzerFactory
                            .getInstance()
                            .getIcrAnalyzer(HmsCardRecognitionManager
                                    .getInstance()
                                    .getMlIcrAnalyzerSetting()));
            task = HmsCardRecognitionManager
                    .getInstance()
                    .getAnalyzer()
                    .asyncAnalyseFrame(HmsFrameManager.getInstance().getFrame());
        }
        task.addOnSuccessListener(idCard -> {
            promise.resolve(HmsCardRecognitionManager.getInstance().resultIcrAnalyze(idCard));
        }).addOnFailureListener(e -> {
            promise.reject(E_CARD_MODULE, e.getMessage());
        });
    }

    @ReactMethod
    public void stop(Boolean isRemote, final Promise promise) {
        try {
            if (isRemote) {
                HmsCardRecognitionManager.getInstance().getRemoteIcrAnalyzer().stop();
                promise.resolve("Remote Icr Analyzer Stop");
            }
            else {
                HmsCardRecognitionManager.getInstance().getAnalyzer().stop();
                promise.resolve("Local Icr Analyzer Stop");
            }
        } catch (IOException e) {
            promise.reject(E_CARD_MODULE, e.getMessage());
        }
    }

    @ReactMethod
    public void equals(ReadableMap readableMap, Boolean isRemote, final Promise promise) {
        if (isRemote)
            promise.resolve(HmsCardRecognitionManager.getInstance()
                    .getMlIcrAnalyzerSetting().equals(HmsCardRecognitionManager.getInstance().createIcrAnalyzerSetting(readableMap, true)));
        else
            promise.resolve(HmsCardRecognitionManager.getInstance().getMlRemoteIcrAnalyzerSetting().equals(HmsCardRecognitionManager.getInstance().createIcrAnalyzerSetting(readableMap, false)));
    }

    @ReactMethod
    public void getCountryCode(Boolean isRemote, final Promise promise) {
        if (isRemote)
            promise.resolve(HmsCardRecognitionManager.getInstance().getMlIcrAnalyzerSetting().getCountryCode());
        else
            promise.resolve(HmsCardRecognitionManager.getInstance().getMlRemoteIcrAnalyzerSetting().getCountryCode());
    }

    @ReactMethod
    public void getSideType(Boolean isRemote, final Promise promise) {
        if (isRemote)
            promise.resolve(HmsCardRecognitionManager.getInstance().getMlIcrAnalyzerSetting().getSideType());
        else
            promise.resolve(HmsCardRecognitionManager.getInstance().getMlRemoteIcrAnalyzerSetting().getSideType());
    }

    @ReactMethod
    public void hashCode(Boolean isRemote, final Promise promise) {
        if (isRemote)
            promise.resolve(HmsCardRecognitionManager.getInstance().getMlIcrAnalyzerSetting().hashCode());
        else
            promise.resolve(HmsCardRecognitionManager.getInstance().getMlRemoteIcrAnalyzerSetting().hashCode());
    }

}
