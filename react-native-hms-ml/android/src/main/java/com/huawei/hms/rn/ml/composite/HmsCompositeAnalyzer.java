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

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class HmsCompositeAnalyzer extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsCompositeAnalyzer.class.getSimpleName();

    HmsCompositeAnalyzer(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void create(ReadableMap readableMap, final Promise promise) {
        HmsCompositAnalyzerManager.getInstance().createCompositeAnalyzer(readableMap);
        promise.resolve("HmsCompositeAnalyzer create");
    }

    @ReactMethod
    public void analyze(final Promise promise) {
        promise.resolve("No analyzer set yet");
    }

    @ReactMethod
    public void destroy(final Promise promise) {
        HmsCompositAnalyzerManager.getInstance().getMlCompositeAnalyzer().destroy();
        promise.resolve("HmsCompositeAnalyzer destroy");
    }

    @ReactMethod
    public void isAvailable(final Promise promise) {
        promise.resolve(HmsCompositAnalyzerManager.getInstance().getMlCompositeAnalyzer().isAvailable());
    }

}
