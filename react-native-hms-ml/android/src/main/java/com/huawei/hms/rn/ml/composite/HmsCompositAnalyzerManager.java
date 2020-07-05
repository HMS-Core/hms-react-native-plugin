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

package com.huawei.hms.rn.ml.composite;

import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import com.huawei.hms.mlsdk.common.MLCompositeAnalyzer;
import com.huawei.hms.rn.ml.utils.RNUtils;

public class HmsCompositAnalyzerManager {
    private static volatile HmsCompositAnalyzerManager instance;
    private static final String TAG = HmsCompositAnalyzerManager.class.getSimpleName();
    private MLCompositeAnalyzer mlCompositeAnalyzer;

    public static HmsCompositAnalyzerManager getInstance() {
        if (instance == null)
            instance = new HmsCompositAnalyzerManager();
        return instance;
    }

    public void setMlCompositeAnalyzer(ReadableMap readableMap) {
        this.mlCompositeAnalyzer = createCompositeAnalyzer(readableMap);
    }

    public MLCompositeAnalyzer getMlCompositeAnalyzer() {
        return mlCompositeAnalyzer;
    }

    public MLCompositeAnalyzer createCompositeAnalyzer(ReadableMap readableMap) {
        MLCompositeAnalyzer.Creator creator = new MLCompositeAnalyzer.Creator();

        if (readableMap == null) {
            Log.i(TAG, "MLFrameProperty object is created using default options.");
            return new MLCompositeAnalyzer.Creator().create();
        }

        for (int i = 1; i < 10; i++) {
            if (RNUtils.hasValidKey(readableMap, Integer.toString(i), ReadableType.Number)) {
                creator.add(RNUtils.findAnalyzerByTag(readableMap.getInt(Integer.toString(i))));
            }
        }

        return creator.create();
    }
}
