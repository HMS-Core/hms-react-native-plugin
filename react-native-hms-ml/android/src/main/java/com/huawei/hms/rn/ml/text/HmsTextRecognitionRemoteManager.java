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

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import com.huawei.hms.mlsdk.text.MLRemoteTextSetting;
import com.huawei.hms.mlsdk.text.MLTextAnalyzer;
import com.huawei.hms.rn.ml.utils.RNUtils;


public class HmsTextRecognitionRemoteManager {
    private static volatile HmsTextRecognitionRemoteManager instance;
    private static final String TAG = HmsTextRecognitionRemoteManager.class.getSimpleName();
    private MLRemoteTextSetting mlRemoteTextSetting;
    private MLTextAnalyzer mlTextAnalyzer;

    public static HmsTextRecognitionRemoteManager getInstance() {
        if (instance == null)
            instance = new HmsTextRecognitionRemoteManager();
        return instance;
    }

    public void setMlRemoteTextSetting(ReadableMap readableMap) {
        mlRemoteTextSetting = createRemoteTextRecognitionSetting(readableMap);
    }

    public MLRemoteTextSetting getMlRemoteTextSetting() {
        return mlRemoteTextSetting;
    }

    public void setMlTextAnalyzer(MLTextAnalyzer analyzer) {
        mlTextAnalyzer = analyzer;
    }

    public MLTextAnalyzer getMlTextAnalyzer() {
        return mlTextAnalyzer;
    }

    public MLRemoteTextSetting createRemoteTextRecognitionSetting(ReadableMap readableMap) {
        int textDensityScene = MLRemoteTextSetting.OCR_LOOSE_SCENE;
        String borderType = MLRemoteTextSetting.NGON;
        List<String> languageList = new ArrayList<>();

        Log.i(TAG, "RemoteTextRecognitionSetting object is being created...");

        if (readableMap == null) {
            Log.i(TAG, "RemoteTextRecognitionSetting object is created using default options.");
            return new MLRemoteTextSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "borderType", ReadableType.String)) {
            borderType = readableMap.getString("borderType");
            Log.i(TAG, "borderType option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "textDensityScene", ReadableType.Number)) {
            textDensityScene = readableMap.getInt("textDensityScene");
            Log.i(TAG, "textDensityScene option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "languageList", ReadableType.Array)) {
            languageList = RNUtils.readableArrayIntoStringList(readableMap.getArray("languageList"));
            Log.i(TAG, "languageList option set.");
        }

        Log.i(TAG, "RemoteTextRecognitionSetting object is created.");

        return new MLRemoteTextSetting.Factory()
                .setTextDensityScene(textDensityScene)
                .setBorderType(borderType)
                .setLanguageList(languageList)
                .create();
    }
}
