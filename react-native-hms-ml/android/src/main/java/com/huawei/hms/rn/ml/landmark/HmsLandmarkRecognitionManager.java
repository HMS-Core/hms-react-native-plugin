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

import java.util.List;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.mlsdk.landmark.MLRemoteLandmark;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmarkAnalyzer;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmarkAnalyzerSetting;
import com.huawei.hms.rn.ml.utils.RNUtils;


public class HmsLandmarkRecognitionManager {
    private static final String TAG = HmsLandmarkRecognitionManager.class.getSimpleName();
    private static volatile HmsLandmarkRecognitionManager instance;
    private MLRemoteLandmarkAnalyzerSetting landmarkAnalyzerSetting;
    private MLRemoteLandmarkAnalyzer mlRemoteLandmarkAnalyzer;

    public static HmsLandmarkRecognitionManager getInstance() {
        if (instance == null)
            instance = new HmsLandmarkRecognitionManager();
        return instance;
    }

    public void setMlLandmarkAnalyzerSetting(ReadableMap readableMap) {
        landmarkAnalyzerSetting = createLandMarkAnalyzerSetting(readableMap);
    }

    public MLRemoteLandmarkAnalyzerSetting getLandmarkAnalyzerSetting() {
        return landmarkAnalyzerSetting == null ? createLandMarkAnalyzerSetting(null) : landmarkAnalyzerSetting;
    }

    public MLRemoteLandmarkAnalyzer getMlRemoteLandmarkAnalyzer() {
        return mlRemoteLandmarkAnalyzer;
    }

    public void setMlRemoteLandmarkAnalyzer(MLRemoteLandmarkAnalyzer mlRemoteLandmarkAnalyzer) {
        this.mlRemoteLandmarkAnalyzer = mlRemoteLandmarkAnalyzer;
    }

    public MLRemoteLandmarkAnalyzerSetting createLandMarkAnalyzerSetting(ReadableMap readableMap) {
        int largestNum = 10;
        int patternType = 1;

        if (readableMap == null) {
            Log.i(TAG, "MLRemoteLandmarkAnalyzerSetting object is created using default options.");
            return new MLRemoteLandmarkAnalyzerSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "largestNum", ReadableType.Number)) {
            largestNum = readableMap.getInt("languageType");
            Log.i(TAG, "largestNum option set.");
        }

        if (RNUtils.hasValidKey(readableMap, "patternType", ReadableType.Number)) {
            patternType = readableMap.getInt("patternType");
            Log.i(TAG, "patternType option set.");
        }
        return new MLRemoteLandmarkAnalyzerSetting.Factory().setLargestNumOfReturns(largestNum).setPatternType(patternType).create();
    }

    public WritableArray landMarkResults(List<MLRemoteLandmark> landmarkResults) {
        WritableArray array = Arguments.createArray();
        for (MLRemoteLandmark landMark : landmarkResults) {
            WritableMap wm = Arguments.createMap();
            wm.putString("landMark", landMark.getLandmark());
            wm.putString("landMarkIdentity", landMark.getLandmarkIdentity());
            wm.putDouble("possibility", landMark.getPossibility());
            wm.putInt("bottom", landMark.getBorder().bottom);
            wm.putInt("top", landMark.getBorder().top);
            wm.putInt("left", landMark.getBorder().left);
            wm.putInt("right", landMark.getBorder().right);
            array.pushMap(wm);
        }
        return array;
    }

}
