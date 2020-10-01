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
package com.huawei.hms.rn.dtm;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

public class HmsDtmModule extends ReactContextBaseJavaModule {
    private HmsDtmWrapper hmsDtmWrapper;

    public HmsDtmModule(ReactApplicationContext reactContext) {
        super(reactContext);
        hmsDtmWrapper = new HmsDtmWrapper(reactContext);
    }

    @Override
    public String getName() {
        return "HmsDTMModule";
    }

    @ReactMethod
    public void onEvent(String eventId, ReadableMap map, Promise promise) {
        hmsDtmWrapper.onEvent(eventId, map, promise);
    }

    @ReactMethod
    public void customFunction(String eventId, ReadableArray readableArray, Promise promise) {
        hmsDtmWrapper.customFunction(eventId, readableArray, promise);
    }

    @ReactMethod
    public void enableLogger(Promise promise) {
        hmsDtmWrapper.enableLogger(promise);
    }

    @ReactMethod
    public void disableLogger(Promise promise) {
        hmsDtmWrapper.disableLogger(promise);
    }
}
