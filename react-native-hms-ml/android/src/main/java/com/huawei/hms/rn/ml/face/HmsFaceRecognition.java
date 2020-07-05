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

package com.huawei.hms.rn.ml.face;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.face.MLFace;
import com.huawei.hms.rn.ml.constants.Constants;
import com.huawei.hms.rn.ml.frame.HmsFrameManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.huawei.hms.rn.ml.constants.Errors.E_FACE_MODULE;

public class HmsFaceRecognition extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsFaceRecognition.class.getSimpleName();

    HmsFaceRecognition(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return Constants.getFaceRecognitionConstants();
    }

    @ReactMethod
    public void create(ReadableMap readableMap, final Promise promise) {
        HmsFaceRecognitionManager.getInstance().setMlFaceAnalyzerSetting(readableMap);
        promise.resolve("FaceRecognitionSetting set");
    }

    @ReactMethod
    public void maxSizeFaceTransactorCreator(ReadableMap rm, final Promise promise) {
        HmsFaceRecognitionManager.getInstance().setMaxSizeFaceTransactor(rm);
        promise.resolve("MaxSizeFaceTransactor created");
    }

    @ReactMethod
    public void analyze(final Promise promise) {
        HmsFaceRecognitionManager.getInstance().setMlFaceAnalyzer(MLAnalyzerFactory.getInstance()
                .getFaceAnalyzer(HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzerSetting()));
        Task<List<MLFace>> task = HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzer()
                .asyncAnalyseFrame(HmsFrameManager.getInstance().getFrame());
        task.addOnSuccessListener(
                faces -> promise.resolve(HmsFaceRecognitionManager.getInstance().faceAnalyzerResult(faces)));
        task.addOnFailureListener(e -> promise.reject(E_FACE_MODULE, e.getMessage()));
    }

    @ReactMethod
    public void addTransactor(final Promise promise) {
        HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzer()
                .setTransactor(HmsFaceRecognitionManager.getInstance().getMaxSizeFaceTransactor());
        promise.resolve("MaxSizeFaceTransactor add");
    }

    @ReactMethod
    public void isAvailable(final Promise promise) {
        promise.resolve(HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzer().isAvailable());
    }

    @ReactMethod
    public void stop(final Promise promise) {
        try {
            HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzer().stop();
            promise.resolve("Analyzer Stop");
        } catch (IOException e) {
            promise.reject(E_FACE_MODULE, e.getMessage());
        }
    }

    @ReactMethod
    public void equals(ReadableMap readableMap, final Promise promise) {
        promise.resolve(HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzerSetting()
                .equals(HmsFaceRecognitionManager.getInstance().createFaceAnalyzerSetting(readableMap)));
    }

    @ReactMethod
    public void getFeatureType(final Promise promise) {
        promise.resolve(HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzerSetting().getFeatureType());
    }

    @ReactMethod
    public void getKeyPointType(final Promise promise) {
        promise.resolve(HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzerSetting().getKeyPointType());
    }

    @ReactMethod
    public void getMinFaceProportion(final Promise promise) {
        promise.resolve(HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzerSetting().getMinFaceProportion());
    }

    @ReactMethod
    public void getPerformanceType(final Promise promise) {
        promise.resolve(HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzerSetting().getPerformanceType());
    }

    @ReactMethod
    public void getShapeType(final Promise promise) {
        promise.resolve(HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzerSetting().getShapeType());
    }

    @ReactMethod
    public void hashCode(final Promise promise) {
        promise.resolve(HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzerSetting().hashCode());
    }

    @ReactMethod
    public void isMaxSizeFaceOnly(final Promise promise) {
        promise.resolve(HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzerSetting().isMaxSizeFaceOnly());
    }

    @ReactMethod
    public void isTracingAllowed(final Promise promise) {
        promise.resolve(HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzerSetting().isTracingAllowed());
    }

}
