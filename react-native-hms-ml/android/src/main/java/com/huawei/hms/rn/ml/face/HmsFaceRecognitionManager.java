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

import java.util.List;

import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.mlsdk.common.MLPosition;
import com.huawei.hms.mlsdk.common.MLResultTrailer;
import com.huawei.hms.mlsdk.face.MLFace;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzer;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.face.MLFaceEmotion;
import com.huawei.hms.mlsdk.face.MLFaceFeature;
import com.huawei.hms.mlsdk.face.MLFaceKeyPoint;
import com.huawei.hms.mlsdk.face.MLFaceShape;
import com.huawei.hms.mlsdk.face.MLMaxSizeFaceTransactor;
import com.huawei.hms.rn.ml.utils.RNUtils;


public class HmsFaceRecognitionManager {
    private static final String TAG = HmsFaceRecognitionManager.class.getSimpleName();
    private static volatile HmsFaceRecognitionManager instance;
    private MLFaceAnalyzerSetting mlFaceAnalyzerSetting;
    private MLFaceAnalyzer mlFaceAnalyzer;
    private MLMaxSizeFaceTransactor maxSizeFaceTransactor;

    public static HmsFaceRecognitionManager getInstance() {
        if (instance == null)
            instance = new HmsFaceRecognitionManager();
        return instance;
    }

    public void setMlFaceAnalyzerSetting(ReadableMap readableMap) {
        mlFaceAnalyzerSetting = createFaceAnalyzerSetting(readableMap);
    }

    public MLFaceAnalyzerSetting getMlFaceAnalyzerSetting() {
        return mlFaceAnalyzerSetting == null ? new MLFaceAnalyzerSetting.Factory().create() : mlFaceAnalyzerSetting;
    }

    public MLFaceAnalyzer getMlFaceAnalyzer() {
        return mlFaceAnalyzer;
    }

    public void setMlFaceAnalyzer(MLFaceAnalyzer mlFaceAnalyzer) {
        this.mlFaceAnalyzer = mlFaceAnalyzer;
    }

    public MLMaxSizeFaceTransactor getMaxSizeFaceTransactor() {
        return maxSizeFaceTransactor;
    }

    public void setMaxSizeFaceTransactor(ReadableMap rm) {
        maxSizeFaceTransactor = createMaxSizeTransactor(rm);
    }

    public MLMaxSizeFaceTransactor createMaxSizeTransactor(ReadableMap readableMap) {
        int maxFrameLostCount = 0;

        if (readableMap == null) {
            Log.i(TAG, "MLMaxSizeFaceTransactor object is created using default options.");
            return new MLMaxSizeFaceTransactor.Creator(getMlFaceAnalyzer(), new MLResultTrailer<MLFace>()).create();
        }
        if (RNUtils.hasValidKey(readableMap, "maxFrameLostCount", ReadableType.Number)) {
            maxFrameLostCount = readableMap.getInt("maxFrameLostCount");
            Log.i(TAG, "maxFrameLostCount option set.");
        }

        return new MLMaxSizeFaceTransactor.Creator(getMlFaceAnalyzer(), new MLResultTrailer<MLFace>())
                .setMaxFrameLostCount(maxFrameLostCount).create();
    }

    public MLFaceAnalyzerSetting createFaceAnalyzerSetting(ReadableMap readableMap) {
        int featureType = 1;
        int keyPointType = 0;
        boolean isMaxSizeFaceOnly = false;
        double setMinFaceProportion = 0.1F;
        int setPerformanceType = 1;
        int setShapeType = 2;
        boolean isTracingAllowed = false;

        if (readableMap == null) {
            Log.i(TAG, "MLFaceAnalyzerSetting object is created using default options.");
            return new MLFaceAnalyzerSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "featureType", ReadableType.Number)) {
            featureType = readableMap.getInt("featureType");
            Log.i(TAG, "featureType option set.");
        }

        if (RNUtils.hasValidKey(readableMap, "keyPointType", ReadableType.Number)) {
            keyPointType = readableMap.getInt("keyPointType");
            Log.i(TAG, "keyPointType option set.");
        }

        if (RNUtils.hasValidKey(readableMap, "isMaxSizeFaceOnly", ReadableType.Boolean)) {
            isMaxSizeFaceOnly = readableMap.getBoolean("isMaxSizeFaceOnly");
            Log.i(TAG, "isMaxSizeFaceOnly option set.");
        }

        if (RNUtils.hasValidKey(readableMap, "minFaceProportion", ReadableType.Number)) {
            setMinFaceProportion = readableMap.getDouble("minFaceProportion");
            Log.i(TAG, "minFaceProportion option set.");
        }

        if (RNUtils.hasValidKey(readableMap, "performanceType", ReadableType.Number)) {
            setPerformanceType = readableMap.getInt("performanceType");
            Log.i(TAG, "performanceType option set.");
        }

        if (RNUtils.hasValidKey(readableMap, "shapeType", ReadableType.Number)) {
            setShapeType = readableMap.getInt("shapeType");
            Log.i(TAG, "shapeType option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "isTracingAllowed", ReadableType.Boolean)) {
            isTracingAllowed = readableMap.getBoolean("isTracingAllowed");
            Log.i(TAG, "isTracingAllowed option set.");
        }

        return new MLFaceAnalyzerSetting.Factory().setFeatureType(featureType).setKeyPointType(keyPointType)
                .setMaxSizeFaceOnly(isMaxSizeFaceOnly).setMinFaceProportion((float) setMinFaceProportion)
                .setPerformanceType(setPerformanceType).setTracingAllowed(isTracingAllowed).setShapeType(setShapeType)
                .setKeyPointType(keyPointType).create();
    }

    public WritableArray faceAnalyzerResult(List<MLFace> faces) {
        WritableArray array = Arguments.createArray();
        for (MLFace face : faces) {
            WritableMap map = Arguments.createMap();
            map.putInt("tracingIdentity", face.getTracingIdentity());
            map.putDouble("width", face.getWidth());
            map.putDouble("height", face.getHeight());
            map.putDouble("rotationAngleX", face.getRotationAngleX());
            map.putDouble("rotationAngleY", face.getRotationAngleY());
            map.putDouble("rotationAngleZ", face.getRotationAngleZ());
            map.putArray("positions", allPoints(face.getAllPoints()));
            map.putMap("borders", borders(face.getBorder()));
            map.putMap("coordinatePoints", coordinatePoints(face.getCoordinatePoint()));
            map.putMap("emotions", emotions(face.getEmotions()));
            map.putArray("faceShape", shape(face.getFaceShapeList()));
            map.putArray("faceKeyPoints", faceKeyPoints(face.getFaceKeyPoints()));
            map.putMap("features", features(face.getFeatures()));
            array.pushMap(map);
        }
        return array;
    }

    public WritableMap features(MLFaceFeature feature) {
        WritableMap map = Arguments.createMap();
        map.putInt("age", feature.getAge());
        map.putDouble("hatPossibility", feature.getHatProbability());
        map.putDouble("leftEyeOpenProbability", feature.getLeftEyeOpenProbability());
        map.putDouble("rightEyeOpenProbability", feature.getRightEyeOpenProbability());
        map.putDouble("moustacheProbability", feature.getMoustacheProbability());
        map.putDouble("sexProbability", feature.getSexProbability());
        map.putDouble("sunGlassProbability", feature.getSunGlassProbability());
        return map;
    }

    public WritableArray allPoints(List<MLPosition> positions) {
        WritableArray array = Arguments.createArray();
        for (MLPosition position : positions) {
            WritableMap map = Arguments.createMap();
            map.putDouble("X", position.getX() == null ? 0.0 : position.getX());
            map.putDouble("Y", position.getY() == null ? 0.0 : position.getY());
            map.putDouble("Z", position.getZ() == null ? 0.0 : position.getZ());
            array.pushMap(map);
        }
        return array;
    }

    public WritableMap borders(Rect border) {
        WritableMap map = Arguments.createMap();
        map.putInt("centerX", border.centerX());
        map.putInt("centerY", border.centerY());
        map.putDouble("exactCenterX", border.exactCenterX());
        map.putDouble("exactCenterY", border.exactCenterY());
        return map;
    }

    public WritableMap coordinatePoints(PointF pointF) {
        WritableMap map = Arguments.createMap();
        map.putDouble("length", pointF.length());
        map.putDouble("x", pointF.x);
        map.putDouble("y", pointF.y);
        return map;
    }

    public WritableMap emotions(MLFaceEmotion emotion) {
        WritableMap map = Arguments.createMap();
        map.putDouble("angryProbability", emotion.getAngryProbability());
        map.putDouble("disgustProbability", emotion.getDisgustProbability());
        map.putDouble("fearProbability", emotion.getFearProbability());
        map.putDouble("neutralProbability", emotion.getNeutralProbability());
        map.putDouble("sadProbability", emotion.getSadProbability());
        map.putDouble("smilingProbability", emotion.getSmilingProbability());
        map.putDouble("surpriseProbability", emotion.getSurpriseProbability());
        return map;
    }

    public WritableArray shape(List<MLFaceShape> shapes) {
        WritableArray array = Arguments.createArray();
        for (MLFaceShape shape : shapes) {
            WritableMap map = Arguments.createMap();
            map.putInt("faceShapeType", shape.getFaceShapeType());
            map.putArray("points", allPoints(shape.getPoints()));
            array.pushMap(map);
        }
        return array;
    }

    public WritableArray faceKeyPoints(List<MLFaceKeyPoint> points) {
        WritableArray array = Arguments.createArray();
        for (MLFaceKeyPoint val : points) {
            WritableMap map = Arguments.createMap();
            map.putMap("positions", position(val.getPoint()));
            map.putInt("type", val.getType());
            array.pushMap(map);
        }
        return array;
    }

    public WritableMap position(MLPosition position) {
        WritableMap map = Arguments.createMap();
        map.putDouble("Y", position.getY());
        map.putDouble("Z", position.getZ());
        map.putDouble("X", position.getX());
        return map;
    }
}
