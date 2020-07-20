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

package com.huawei.hms.rn.ml.imseg;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentation;
import com.huawei.hms.rn.ml.constants.Constants;
import com.huawei.hms.rn.ml.frame.HmsFrameManager;

import java.io.IOException;
import java.util.Map;

import static com.huawei.hms.rn.ml.constants.Errors.E_IMAGE_SEGMENTATION_MODULE;

public class HmsImageSegmentation extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsImageSegmentation.class.getSimpleName();

    public HmsImageSegmentation(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return Constants.getImageSegmentationConstants();
    }

    @ReactMethod
    public void create(ReadableMap readableMap, final Promise promise) {
        HmsImageSegmentationManager.getInstance().setImageSegmentationSetting(readableMap);
        promise.resolve("ImageSegmentationSetting set");
    }

    @ReactMethod
    public void analyze(final Promise promise) {
        HmsImageSegmentationManager
                .getInstance()
                .setAnalyzer(MLAnalyzerFactory
                        .getInstance()
                        .getImageSegmentationAnalyzer());
        Task task = HmsImageSegmentationManager
                .getInstance()
                .getAnalyzer()
                .asyncAnalyseFrame(HmsFrameManager
                        .getInstance()
                        .getFrame());
        task.addOnSuccessListener((OnSuccessListener<MLImageSegmentation>) imagesegmentation ->
                promise.resolve(HmsImageSegmentationManager
                        .getInstance()
                        .imageSegmentationResult(getReactApplicationContext(), imagesegmentation)));
        task.addOnFailureListener(e -> promise.reject(E_IMAGE_SEGMENTATION_MODULE, e.getMessage()));
    }

    @ReactMethod
    public void stop(final Promise promise) {
        try {
            HmsImageSegmentationManager.getInstance().getAnalyzer().stop();
            promise.resolve("Analyzer stop");
        } catch (IOException e) {
            promise.reject(E_IMAGE_SEGMENTATION_MODULE, e.getMessage());
        }
    }

    @ReactMethod
    public void equals(ReadableMap readableMap, final Promise promise) {
        promise.resolve(HmsImageSegmentationManager.getInstance().getImageSegmentationSetting().equals(HmsImageSegmentationManager.getInstance().createImageSegmentation(readableMap)));
    }

    @ReactMethod
    public void getAnalyzerType(final Promise promise) {
        promise.resolve(HmsImageSegmentationManager.getInstance().getImageSegmentationSetting().getAnalyzerType());
    }

    @ReactMethod
    public void getScene(final Promise promise) {
        promise.resolve(HmsImageSegmentationManager.getInstance().getImageSegmentationSetting().getScene());
    }

    @ReactMethod
    public void hashCode(final Promise promise) {
        promise.resolve(HmsImageSegmentationManager.getInstance().getImageSegmentationSetting().hashCode());
    }

    @ReactMethod
    public void isExact(final Promise promise) {
        promise.resolve(HmsImageSegmentationManager.getInstance().getImageSegmentationSetting().isExact());
    }

}
