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

package com.huawei.hms.rn.ml.productvision;

import java.util.List;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.productvisionsearch.MLProductVisionSearch;
import com.huawei.hms.rn.ml.frame.HmsFrameManager;

import static com.huawei.hms.rn.ml.constants.Errors.E_PRODUCT_VISION_MODULE;

public class HmsProductVision extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME =  HmsProductVision.class.getSimpleName();

    public HmsProductVision(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void create(ReadableMap readableMap, final Promise promise) {
        HmsProductVisionManager.getInstance().setSearchAnalyzerSetting(readableMap);
        promise.resolve("MLRemoteProductVisionSearchAnalyzerSetting create");
    }

    @ReactMethod
    public void analyze(final Promise promise) {
        try {
            HmsProductVisionManager.getInstance().setProductVisionSearchAnalyzer(MLAnalyzerFactory.getInstance().getRemoteProductVisionSearchAnalyzer(HmsProductVisionManager.getInstance().getSearchAnalyzerSetting()));
            Task<List<MLProductVisionSearch>> task = HmsProductVisionManager.getInstance().getProductVisionSearchAnalyzer().asyncAnalyseFrame(HmsFrameManager.getInstance().getFrame());
            task.addOnSuccessListener(resultList -> promise.resolve(HmsProductVisionManager.getInstance().analyzeResult(resultList)));
            task.addOnFailureListener(e -> promise.reject(E_PRODUCT_VISION_MODULE, e.getMessage()));
        }catch (Exception e){
            promise.reject(E_PRODUCT_VISION_MODULE, e.getMessage());
        }
    }

    @ReactMethod
    public void stop(final Promise promise) {
        HmsProductVisionManager.getInstance().getProductVisionSearchAnalyzer().stop();
        promise.resolve("Analyzer stopped");
    }

    @ReactMethod
    public void equals(ReadableMap rm, final Promise promise) {
        promise.resolve(HmsProductVisionManager.getInstance().getSearchAnalyzerSetting().equals(HmsProductVisionManager.getInstance().createSearchAnalyzerSetting(rm)));
    }

    @ReactMethod
    public void getLargestNumOfReturns(final Promise promise) {
        promise.resolve(HmsProductVisionManager.getInstance().getSearchAnalyzerSetting().getLargestNumOfReturns());
    }

    @ReactMethod
    public void hashCode(final Promise promise) {
        promise.resolve(HmsProductVisionManager.getInstance().getSearchAnalyzerSetting().hashCode());
    }

}
