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

package com.huawei.hms.rn.ml.object;

import java.util.List;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.mlsdk.objects.MLObject;
import com.huawei.hms.mlsdk.objects.MLObjectAnalyzer;
import com.huawei.hms.mlsdk.objects.MLObjectAnalyzerSetting;
import com.huawei.hms.rn.ml.utils.RNUtils;


public class HmsObjectRecognitionManager {
    private static final String TAG = HmsObjectRecognitionManager.class.getSimpleName();
    private static volatile HmsObjectRecognitionManager instance;
    private MLObjectAnalyzerSetting analyzerSetting;
    private MLObjectAnalyzer analyzer;

    public static HmsObjectRecognitionManager getInstance() {
        if (instance == null)
            instance = new HmsObjectRecognitionManager();
        return instance;
    }

    public void setAnalyzerSetting(ReadableMap readableMap) {
        analyzerSetting = createObjectAnalyzerSetting(readableMap);
    }

    public MLObjectAnalyzerSetting getAnalyzerSetting() {
        return analyzerSetting == null ? new MLObjectAnalyzerSetting.Factory().create() : analyzerSetting;
    }

    public MLObjectAnalyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(MLObjectAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public MLObjectAnalyzerSetting createObjectAnalyzerSetting(ReadableMap readableMap) {
        boolean allowClassification = false;
        boolean allowMultiResults = false;
        int analyzerType = MLObjectAnalyzerSetting.TYPE_PICTURE;

        if (readableMap == null) {
            Log.i(TAG, "MLObjectAnalyzerSetting object is created using default options.");
            return new MLObjectAnalyzerSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "allowClassification", ReadableType.Boolean)) {
            allowClassification = readableMap.getBoolean("allowClassification");
            Log.i(TAG, "allowClassification option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "allowMultiResults", ReadableType.Boolean)) {
            allowMultiResults = readableMap.getBoolean("allowMultiResults");
            Log.i(TAG, "allowMultiResults option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "analyzerType", ReadableType.Number)) {
            analyzerType = readableMap.getInt("analyzerType");
            Log.i(TAG, "analyzerType option set.");
        }
        if (allowClassification && allowMultiResults)
            return new MLObjectAnalyzerSetting.Factory().setAnalyzerType(analyzerType).allowClassification().allowMultiResults().create();
        else if (allowClassification)
            return new MLObjectAnalyzerSetting.Factory().setAnalyzerType(analyzerType).allowClassification().create();
        else if (allowMultiResults)
            return new MLObjectAnalyzerSetting.Factory().setAnalyzerType(analyzerType).allowMultiResults().create();
        else
            return new MLObjectAnalyzerSetting.Factory().setAnalyzerType(analyzerType).create();
    }

    public WritableArray analyzeResult(List<MLObject> list) {
        WritableArray array = Arguments.createArray();
        for (MLObject object : list) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putInt("tracingIdentity", object.getTracingIdentity() == null ? 0 : object.getTracingIdentity());
            writableMap.putInt("typeIdentity", object.getTypeIdentity());
            writableMap.putDouble("typePossibility", object.getTypePossibility() == null ? 0 : object.getTypePossibility());
            writableMap.putInt("bottom", object.getBorder().bottom);
            writableMap.putInt("left", object.getBorder().left);
            writableMap.putInt("right", object.getBorder().right);
            writableMap.putInt("top", object.getBorder().top);
            array.pushMap(writableMap);
        }
        return array;
    }
}
