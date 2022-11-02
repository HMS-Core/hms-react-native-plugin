/*
    Copyright 2020-2022. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.dtm;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class HMSDtmModule extends ReactContextBaseJavaModule {
    private final HMSDtmWrapper hmsDtmWrapper;

    public HMSDtmModule(ReactApplicationContext reactContext) {
        super(reactContext);
        hmsDtmWrapper = new HMSDtmWrapper(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSDTMModule";
    }

    /**
     * It is Analytics' onEvent API. It reports to Analytics.
     *
     * @param eventId: String
     * @param map:     ReadableMap
     * @param promise: WritableMap
     */
    @ReactMethod
    public void onEvent(String eventId, ReadableMap map, Promise promise) {
        hmsDtmWrapper.onEvent(eventId, map, promise);
    }

    /**
     * Sets the returnValue value for CustomVariable.
     *
     * @param varName: String
     * @param value:   String
     * @param promise: WritableMap
     */
    @ReactMethod
    public void setCustomVariable(String varName, String value, Promise promise) {
        hmsDtmWrapper.setCustomVariable(varName, value, promise);
    }

    /**
     * Enables logging.
     *
     * @param promise: WritableMap
     */
    @ReactMethod
    public void enableLogger(Promise promise) {
        hmsDtmWrapper.enableLogger(promise);
    }

    /**
     * Disables logging.
     *
     * @param promise: WritableMap
     */
    @ReactMethod
    public void disableLogger(Promise promise) {
        hmsDtmWrapper.disableLogger(promise);
    }
}