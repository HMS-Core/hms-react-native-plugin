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

package com.huawei.hms.rn.ml.langdetect;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.mlsdk.langdetect.MLDetectedLang;
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetector;
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetectorSetting;
import com.huawei.hms.rn.ml.utils.RNUtils;

import java.util.List;

public class HmsLanguageDetectionManager {
    private static final String TAG = HmsLanguageDetectionManager.class.getSimpleName();
    private static volatile HmsLanguageDetectionManager instance;
    private MLRemoteLangDetectorSetting mlRemoteLangDetectorSetting;
    private MLRemoteLangDetector mlRemoteLangDetector;

    public static HmsLanguageDetectionManager getInstance() {
        if (instance == null)
            instance = new HmsLanguageDetectionManager();
        return instance;
    }

    public void setMlRemoteLangDetectorSetting(ReadableMap readableMap) {
        mlRemoteLangDetectorSetting = createLanguageDetector(readableMap);
    }

    public MLRemoteLangDetectorSetting getMlRemoteLangDetectorSetting() {
        return mlRemoteLangDetectorSetting == null ? new MLRemoteLangDetectorSetting.Factory().create() : mlRemoteLangDetectorSetting;
    }

    public MLRemoteLangDetector getMlRemoteLangDetector() {
        return mlRemoteLangDetector;
    }

    public void setMlRemoteLangDetector(MLRemoteLangDetector mlRemoteLangDetector) {
        this.mlRemoteLangDetector = mlRemoteLangDetector;
    }

    public MLRemoteLangDetectorSetting createLanguageDetector(ReadableMap readableMap) {
        double trustedThreshold = 0.0F;

        if (readableMap == null) {
            Log.i(TAG, "MLRemoteLangDetectorSetting object is created using default options.");
            return new MLRemoteLangDetectorSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "trustedThreshold", ReadableType.Number)) {
            trustedThreshold = readableMap.getDouble("trustedThreshold");
            Log.i(TAG, "trustedThreshold option set.");
        }

        return new MLRemoteLangDetectorSetting.Factory().setTrustedThreshold((float) trustedThreshold).create();
    }

    public WritableArray multipleLanguageResults(List<MLDetectedLang> langs) {
        WritableArray array = Arguments.createArray();
        for (MLDetectedLang lang : langs) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("languageCode", lang.getLangCode());
            writableMap.putDouble("probability", lang.getProbability());
            writableMap.putInt("hashCode", lang.hashCode());
            array.pushMap(writableMap);
        }
        return array;
    }
}
