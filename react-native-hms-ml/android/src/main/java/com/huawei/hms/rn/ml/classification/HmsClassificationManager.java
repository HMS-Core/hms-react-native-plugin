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

package com.huawei.hms.rn.ml.classification;

import java.util.List;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.mlsdk.classification.MLImageClassification;
import com.huawei.hms.mlsdk.classification.MLImageClassificationAnalyzer;
import com.huawei.hms.mlsdk.classification.MLLocalClassificationAnalyzerSetting;
import com.huawei.hms.mlsdk.classification.MLRemoteClassificationAnalyzerSetting;
import com.huawei.hms.rn.ml.utils.RNUtils;


public class HmsClassificationManager {
    private static final String TAG = HmsClassificationManager.class.getSimpleName();
    private static volatile HmsClassificationManager instance;
    private MLRemoteClassificationAnalyzerSetting remoteAnalyzerSetting;
    private MLLocalClassificationAnalyzerSetting localClassificationAnalyzerSetting;
    private MLImageClassificationAnalyzer mlImageClassificationAnalyzer;
    private MLImageClassificationAnalyzer mlRemoteImageClassificationAnalyzer;

    public static HmsClassificationManager getInstance() {
        if (instance == null)
            instance = new HmsClassificationManager();
        return instance;
    }

    public MLRemoteClassificationAnalyzerSetting getAnalyzerSetting() {
        return remoteAnalyzerSetting;
    }

    public void setAnalyzerSetting(ReadableMap readableMap) {
        remoteAnalyzerSetting = createClassificationAnalyzerSetting(readableMap);
    }

    public MLLocalClassificationAnalyzerSetting getLocalAnalyzerSetting() {
        return localClassificationAnalyzerSetting == null ?
                new MLLocalClassificationAnalyzerSetting.Factory().create() :
                localClassificationAnalyzerSetting;
    }

    public void setLocalClassificationAnalyzerSetting(ReadableMap readableMap) {
        localClassificationAnalyzerSetting = createLocalClassificationAnalyzerSetting(readableMap);
    }

    public MLImageClassificationAnalyzer getMlImageClassificationAnalyzer() {
        return mlImageClassificationAnalyzer;
    }

    public void setMlImageClassificationAnalyzer(MLImageClassificationAnalyzer mlImageClassificationAnalyzer) {
        this.mlImageClassificationAnalyzer = mlImageClassificationAnalyzer;
    }

    public MLImageClassificationAnalyzer getMlRemoteImageClassificationAnalyzer() {
        return mlRemoteImageClassificationAnalyzer;
    }

    public void setMlRemoteImageClassificationAnalyzer(MLImageClassificationAnalyzer mlRemoteImageClassificationAnalyzer) {
        this.mlRemoteImageClassificationAnalyzer = mlRemoteImageClassificationAnalyzer;
    }

    public MLRemoteClassificationAnalyzerSetting createClassificationAnalyzerSetting(ReadableMap readableMap) {
        int largestNumOfReturns = 0;
        double minAcceptablePossibility = 0.5F;

        if (readableMap == null) {
            Log.i(TAG, "MLRemoteClassificationAnalyzerSetting object is created using default options.");
            return new MLRemoteClassificationAnalyzerSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "maxResults", ReadableType.Number)) {
            largestNumOfReturns = readableMap.getInt("maxResults");
            Log.i(TAG, "maxResults option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "possibility", ReadableType.Boolean)) {
            minAcceptablePossibility = readableMap.getDouble("possibility");
            Log.i(TAG, "possibility option set.");
        }

        return new MLRemoteClassificationAnalyzerSetting
                .Factory()
                .setLargestNumOfReturns(largestNumOfReturns)
                .setMinAcceptablePossibility((float) minAcceptablePossibility)
                .create();
    }

    public MLLocalClassificationAnalyzerSetting createLocalClassificationAnalyzerSetting(ReadableMap readableMap) {
        double minAcceptablePossibility = 0.5F;

        if (readableMap == null) {
            Log.i(TAG, "MLLocalClassificationAnalyzerSetting object is created using default options.");
            return new MLLocalClassificationAnalyzerSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "possibility", ReadableType.Boolean)) {
            minAcceptablePossibility = readableMap.getDouble("possibility");
            Log.i(TAG, "possibility option set.");
        }
        return new MLLocalClassificationAnalyzerSetting.Factory().setMinAcceptablePossibility((float) minAcceptablePossibility).create();
    }

    public WritableArray classificationResult(List<MLImageClassification> classifications) {
        WritableArray array = Arguments.createArray();
        for (MLImageClassification classified : classifications) {
            WritableMap map = Arguments.createMap();
            map.putString("identity", classified.getClassificationIdentity());
            map.putString("name", classified.getName());
            map.putDouble("possibility", classified.getPossibility());
            array.pushMap(map);
        }
        return array;
    }
}
