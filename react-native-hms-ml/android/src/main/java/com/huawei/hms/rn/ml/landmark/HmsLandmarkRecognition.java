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

package com.huawei.hms.rn.ml.landmark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmark;
import com.huawei.hms.rn.ml.constants.Constants;
import com.huawei.hms.rn.ml.frame.HmsFrameManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.huawei.hms.rn.ml.constants.Errors.E_LANDMARK_MODULE;

public class HmsLandmarkRecognition extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsLandmarkRecognition.class.getSimpleName();

    HmsLandmarkRecognition(ReactApplicationContext context) {
        super(context);
    }

    @ReactMethod
    public void create(ReadableMap readableMap, final Promise promise) {
        HmsLandmarkRecognitionManager.getInstance().setMlLandmarkAnalyzerSetting(readableMap);
        promise.resolve("LandmarkRecognitionSetting set");
    }

    @ReactMethod
    public void analyze(final Promise promise) {
        HmsLandmarkRecognitionManager.getInstance().setMlRemoteLandmarkAnalyzer(MLAnalyzerFactory.getInstance().getRemoteLandmarkAnalyzer(HmsLandmarkRecognitionManager.getInstance().getLandmarkAnalyzerSetting()));
        Task<List<MLRemoteLandmark>> task = HmsLandmarkRecognitionManager.getInstance().getMlRemoteLandmarkAnalyzer().asyncAnalyseFrame(HmsFrameManager.getInstance().getFrame());
        task.addOnSuccessListener(landmarkResults -> promise.resolve(HmsLandmarkRecognitionManager.getInstance().landMarkResults(landmarkResults)));
        task.addOnFailureListener(e -> promise.reject(E_LANDMARK_MODULE, e.getMessage()));
    }

    @ReactMethod
    public void close(final Promise promise) {
        try {
            HmsLandmarkRecognitionManager.getInstance().getMlRemoteLandmarkAnalyzer().close();
            promise.resolve("LandMarkRecognition analyzer close");
        } catch (IOException e) {
            promise.reject(E_LANDMARK_MODULE, e.getMessage());
        }
    }

    @ReactMethod
    public void equals(ReadableMap readableMap, final Promise promise) {
        promise.resolve(HmsLandmarkRecognitionManager.getInstance().getLandmarkAnalyzerSetting().equals(HmsLandmarkRecognitionManager.getInstance().createLandMarkAnalyzerSetting(readableMap)));
    }

    @ReactMethod
    public void getLargestNumOfReturns(final Promise promise) {
        promise.resolve(HmsLandmarkRecognitionManager.getInstance().getLandmarkAnalyzerSetting().getLargestNumOfReturns());
    }

    @ReactMethod
    public void getPatternType(final Promise promise) {
        promise.resolve(HmsLandmarkRecognitionManager.getInstance().getLandmarkAnalyzerSetting().getPatternType());
    }

    @ReactMethod
    public void hashCode(final Promise promise) {
        promise.resolve(HmsLandmarkRecognitionManager.getInstance().getLandmarkAnalyzerSetting().hashCode());
    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return Constants.getLandmarkRecognitionConstants();
    }
}
