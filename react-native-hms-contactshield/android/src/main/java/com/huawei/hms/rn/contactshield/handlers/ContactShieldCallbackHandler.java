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

package com.huawei.hms.rn.contactshield.handlers;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.huawei.hms.contactshield.ContactShieldCallback;

public class ContactShieldCallbackHandler implements ContactShieldCallback {
    private ReactContext reactContext;

    public ContactShieldCallbackHandler(ReactContext _reactContext) {
        super();
        reactContext = _reactContext;
    }

    @Override
    public void onHasContact(String token) {
        WritableMap params = new WritableNativeMap();
        params.putString("token", token);
        sendEvent(reactContext, "onHasContact", params);
    }

    @Override
    public void onNoContact(String token) {
        WritableMap params = new WritableNativeMap();
        params.putString("token", token);
        sendEvent(reactContext, "onNoContact", params);
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
