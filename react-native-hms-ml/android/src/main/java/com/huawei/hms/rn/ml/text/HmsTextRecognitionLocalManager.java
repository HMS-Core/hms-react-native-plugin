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

package com.huawei.hms.rn.ml.text;

import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import com.huawei.hms.mlsdk.text.MLLocalTextSetting;
import com.huawei.hms.mlsdk.text.MLTextAnalyzer;
import com.huawei.hms.rn.ml.utils.RNUtils;

public class HmsTextRecognitionLocalManager {
    private static volatile HmsTextRecognitionLocalManager instance;
    private static final String TAG = HmsTextRecognitionLocalManager.class.getSimpleName();
    private MLLocalTextSetting mlLocalTextSetting;
    private MLTextAnalyzer mlTextAnalyzer;

    public static HmsTextRecognitionLocalManager getInstance() {
        if (instance == null)
            instance = new HmsTextRecognitionLocalManager();
        return instance;
    }

    public void setMlLocalTextSetting(ReadableMap readableMap) {
        mlLocalTextSetting = createLocalTextRecognitionSetting(readableMap);
    }

    public MLLocalTextSetting getMlLocalTextSetting() {
        return mlLocalTextSetting;
    }

    public void setMlTextAnalyzer(MLTextAnalyzer analyzer) {
        mlTextAnalyzer = analyzer;
    }

    public MLTextAnalyzer getMlTextAnalyzer() {
        return mlTextAnalyzer;
    }

    public MLLocalTextSetting createLocalTextRecognitionSetting(ReadableMap readableMap) {
        String language = "rm";
        int ocrDetectMode = MLLocalTextSetting.OCR_DETECT_MODE;

        Log.i(TAG, "LocalTextRecognitionSetting object is being created...");

        if (readableMap == null) {
            Log.i(TAG, "LocalTextRecognitionSetting object is created using default options.");
            return new MLLocalTextSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "language", ReadableType.String)) {
            language = readableMap.getString("language");
            Log.i(TAG, "Language option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "OCRMode", ReadableType.Number)) {
            ocrDetectMode = readableMap.getInt("OCRMode");
            Log.i(TAG, "OCRMode option set.");
        }

        Log.i(TAG, "LocalTextRecognitionSetting object is created.");
        return new MLLocalTextSetting.Factory().setLanguage(language).setOCRMode(ocrDetectMode).create();
    }
}
