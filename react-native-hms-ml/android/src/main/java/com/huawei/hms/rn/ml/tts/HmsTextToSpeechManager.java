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

package com.huawei.hms.rn.ml.tts;

import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import com.huawei.hms.mlsdk.tts.MLTtsConfig;
import com.huawei.hms.mlsdk.tts.MLTtsEngine;
import com.huawei.hms.rn.ml.utils.RNUtils;

public class HmsTextToSpeechManager {
    private static final String TAG = HmsTextToSpeechManager.class.getSimpleName();
    private static volatile HmsTextToSpeechManager instance;
    private MLTtsConfig config;
    private MLTtsEngine mlTtsEngine;

    public static HmsTextToSpeechManager getInstance() {
        if (instance == null)
            instance = new HmsTextToSpeechManager();
        return instance;
    }

    public void setConfig(ReadableMap readableMap) {
        config = createConfiguration(readableMap);
    }

    public MLTtsConfig getConfig() {
        return config;
    }

    public void setMlTtsEngine(MLTtsEngine mlTtsEngine) {
        this.mlTtsEngine = mlTtsEngine;
    }

    public MLTtsEngine getMlTtsEngine() {
        return mlTtsEngine;
    }

    public MLTtsConfig createConfiguration(ReadableMap readableMap) {
        double speed = 0.0F;
        double volume = 0.0F;
        String language = null;
        String person = null;

        Log.i(TAG, "MLTtsConfig object is being created...");

        if (readableMap == null) {
            Log.i(TAG, "MLTtsConfig object is created using default options.");
            return new MLTtsConfig();
        }
        if (RNUtils.hasValidKey(readableMap, "volume", ReadableType.Number)) {
            volume = readableMap.getDouble("volume");
            Log.i(TAG, "volume option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "speed", ReadableType.Number)) {
            speed = readableMap.getDouble("speed");
            Log.i(TAG, "speed option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "language", ReadableType.String)) {
            language = readableMap.getString("language");
            Log.i(TAG, "language option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "person", ReadableType.String)) {
            person = readableMap.getString("person");
            Log.i(TAG, "person option set.");
        }

        return new MLTtsConfig().setVolume((float) volume).setSpeed((float) speed)
                .setLanguage(language)
                .setPerson(person);
    }
}
