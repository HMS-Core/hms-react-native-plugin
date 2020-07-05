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

package com.huawei.hms.rn.ml.translate;

import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import com.huawei.hms.mlsdk.translate.cloud.MLRemoteTranslateSetting;
import com.huawei.hms.mlsdk.translate.cloud.MLRemoteTranslator;
import com.huawei.hms.rn.ml.utils.RNUtils;

public class HmsTranslateManager {
    private static final String TAG = HmsTranslateManager.class.getSimpleName();
    private static volatile HmsTranslateManager instance;
    private MLRemoteTranslateSetting mlRemoteTranslateSetting;
    private MLRemoteTranslator mlRemoteTranslator;

    public static HmsTranslateManager getInstance() {
        if (instance == null)
            instance = new HmsTranslateManager();
        return instance;
    }

    public void setTranslatorSetting(ReadableMap readableMap) {
        mlRemoteTranslateSetting = createSetting(readableMap);
    }

    public MLRemoteTranslateSetting getTranslatorSetting() {
        return mlRemoteTranslateSetting == null ? createSetting(null) : mlRemoteTranslateSetting;
    }

    public MLRemoteTranslator getMlRemoteTranslator() {
        return mlRemoteTranslator;
    }

    public void setMlRemoteTranslator(MLRemoteTranslator mlRemoteTranslator) {
        this.mlRemoteTranslator = mlRemoteTranslator;
    }

    public MLRemoteTranslateSetting createSetting(ReadableMap readableMap) {
        String sourceLanguageCode = "en";
        String targetLanguageCode = "zh";

        if (readableMap == null) {
            Log.i(TAG, "MLRemoteTranslateSetting object is created using default options.");
            return new MLRemoteTranslateSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "sourceLanguageCode", ReadableType.String)) {
            sourceLanguageCode = readableMap.getString("sourceLanguageCode");
            Log.i(TAG, "sourceLanguageCode option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "targetLanguageCode", ReadableType.String)) {
            targetLanguageCode = readableMap.getString("targetLanguageCode");
            Log.i(TAG, "targetLanguageCode option set.");
        }

        return new MLRemoteTranslateSetting.Factory().setSourceLangCode(sourceLanguageCode).setTargetLangCode(targetLanguageCode).create();
    }
}
