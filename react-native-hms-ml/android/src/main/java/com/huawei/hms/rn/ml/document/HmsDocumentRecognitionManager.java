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

package com.huawei.hms.rn.ml.document;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import com.huawei.hms.mlsdk.document.MLDocumentAnalyzer;
import com.huawei.hms.mlsdk.document.MLDocumentSetting;
import com.huawei.hms.mlsdk.text.MLRemoteTextSetting;
import com.huawei.hms.rn.ml.utils.RNUtils;


public class HmsDocumentRecognitionManager {
    private static volatile HmsDocumentRecognitionManager instance;
    private static final String TAG = HmsDocumentRecognitionManager.class.getSimpleName();
    private MLDocumentSetting mlDocumentSetting;
    private MLDocumentAnalyzer analyzer;

    public static HmsDocumentRecognitionManager getInstance() {
        if (instance == null)
            instance = new HmsDocumentRecognitionManager();
        return instance;
    }

    public void setMlDocumentSetting(ReadableMap readableMap) {
        mlDocumentSetting = createDocumentRecognitionSetting(readableMap);
    }

    public MLDocumentSetting getMlDocumentSetting() {
        return mlDocumentSetting;
    }

    public void setAnalyzer(MLDocumentAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public MLDocumentAnalyzer getAnalyzer() {
        return analyzer;
    }

    public MLDocumentSetting createDocumentRecognitionSetting(ReadableMap readableMap) {
        String borderType = MLRemoteTextSetting.NGON;
        List<String> languageList = new ArrayList<>();
        boolean isFingerPrintEnabled = false;

        Log.i(TAG, "MLDocumentSetting object is being created...");

        if (readableMap == null) {
            Log.i(TAG, "MLDocumentSetting object is created using default options.");
            return new MLDocumentSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "borderType", ReadableType.String)) {
            borderType = readableMap.getString("borderType");
            Log.i(TAG, "borderType option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "languageList", ReadableType.Array)) {
            languageList = RNUtils.readableArrayIntoStringList(readableMap.getArray("languageList"));
            Log.i(TAG, "languageList option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "isFingerPrintEnabled", ReadableType.Boolean)) {
            isFingerPrintEnabled = readableMap.getBoolean("isFingerPrintEnabled");
            Log.i(TAG, "isFingerPrintEnabled option set.");
        }

        Log.i(TAG, "RemoteTextRecognitionSetting object is created.");

        if (!isFingerPrintEnabled)
            return new MLDocumentSetting.Factory()
                    .setBorderType(borderType)
                    .setLanguageList(languageList)
                    .create();

        return new MLDocumentSetting.Factory()
                .setBorderType(borderType)
                .setLanguageList(languageList)
                .enableFingerprintVerification()
                .create();
    }

}
