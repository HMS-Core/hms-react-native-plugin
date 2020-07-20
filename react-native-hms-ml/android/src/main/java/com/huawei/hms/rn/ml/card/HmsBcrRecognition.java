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

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.card.MLCardAnalyzerFactory;
import com.huawei.hms.mlsdk.card.bcr.MLBankCard;
import com.huawei.hms.rn.ml.frame.HmsFrameManager;

import static com.huawei.hms.rn.ml.constants.Errors.E_CARD_MODULE;

public class HmsBcrRecognition extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsBcrRecognition.class.getSimpleName();

    public HmsBcrRecognition(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void create(ReadableMap readableMap, final Promise promise) {
        HmsCardRecognitionManager.getInstance().setMlBcrAnalyzerSetting(readableMap);
        promise.resolve("BcrRecognitionSetting set");
    }

    @ReactMethod
    public void getLanguageType(final Promise promise) {
        promise.resolve(HmsCardRecognitionManager.getInstance().getMlBcrAnalyzerSetting().getLangType());
    }

    @ReactMethod
    public void equals(ReadableMap readableMap, final Promise promise) {
        promise.resolve(HmsCardRecognitionManager.getInstance().getMlBcrAnalyzerSetting().equals(HmsCardRecognitionManager.getInstance().createBcrAnalyzerSetting(readableMap)));
    }

    @ReactMethod
    public void hashCode(final Promise promise) {
        promise.resolve(HmsCardRecognitionManager.getInstance().getMlBcrAnalyzerSetting().hashCode());
    }

    @ReactMethod
    public void analyze(final Promise promise) {
        HmsCardRecognitionManager.getInstance().setMlBcrAnalyzer(MLCardAnalyzerFactory.getInstance().getBcrAnalyzer(HmsCardRecognitionManager.getInstance().getMlBcrAnalyzerSetting()));
        Task<MLBankCard> task = HmsCardRecognitionManager.getInstance().getMlBcrAnalyzer().asyncAnalyseFrame(HmsFrameManager.getInstance().getFrame());
        task.addOnSuccessListener(bankCard -> {
            promise.resolve(HmsCardRecognitionManager.getInstance().captureResult(getReactApplicationContext(),
                    bankCard.getOriginalBitmap(),
                    bankCard.getNumberBitmap(),
                    bankCard.getNumber(),
                    bankCard.getExpire()));
        });
        task.addOnFailureListener(e -> {
            promise.reject(E_CARD_MODULE, e.getMessage());
        });
    }

    @ReactMethod
    public void stop(final Promise promise) {
        HmsCardRecognitionManager.getInstance().getMlBcrAnalyzer().stop();
        promise.resolve("Analyzer stopped");
    }

}
