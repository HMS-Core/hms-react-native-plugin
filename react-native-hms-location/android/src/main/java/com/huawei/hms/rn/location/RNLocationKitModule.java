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

package com.huawei.hms.rn.location;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.ArrayMap;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.huawei.hms.rn.location.backend.helpers.Constants;
import com.huawei.hms.rn.location.backend.helpers.HMSBroadcastReceiver;
import com.huawei.hms.rn.location.backend.logger.HMSMethod;
import com.huawei.hms.rn.location.helpers.ReactUtils;

import java.util.Map;

public class RNLocationKitModule extends ReactContextBaseJavaModule {
    private ReactContext reactContext;

    public RNLocationKitModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "HMSLocationKit";
    }

    @ReactMethod
    public void init(final Promise promise) {
        HMSBroadcastReceiver.init(getCurrentActivity(), (eventName, params) -> ReactUtils.sendEvent(reactContext,
                eventName, ReactUtils.toWM(params)));
        promise.resolve(true);
    }

    @ReactMethod
    public void enableLogger(final Promise promise) {
        HMSMethod.enableLogger(getCurrentActivity());
        promise.resolve(true);
    }

    @ReactMethod
    public void disableLogger(final Promise promise) {
        HMSMethod.disableLogger(getCurrentActivity());
        promise.resolve(true);
    }

    public String getStringKey(ReadableMap rm, String key, String fallback) {
        return (rm != null && rm.hasKey(key) && rm.getType(key) == ReadableType.String) ? rm.getString(key) : fallback;
    }

    @ReactMethod
    public void setNotification(final ReadableMap rm, final Promise promise) {
        SharedPreferences.Editor editor = reactContext.getSharedPreferences(reactContext.getPackageName(),
                Context.MODE_PRIVATE).edit();
        editor.putString(Constants.KEY_CONTENT_TITLE, getStringKey(rm, Constants.KEY_CONTENT_TITLE,
                Constants.DEFAULT_CONTENT_TITLE));
        editor.putString(Constants.KEY_CONTENT_TEXT, getStringKey(rm, Constants.KEY_CONTENT_TEXT, Constants.DEFAULT_CONTENT_TEXT));
        editor.putString(Constants.KEY_DEF_TYPE, getStringKey(rm, Constants.KEY_DEF_TYPE,
                Constants.DEFAULT_DEF_TYPE));
        editor.putString(Constants.KEY_RESOURCE_NAME, getStringKey(rm, Constants.KEY_RESOURCE_NAME,
                Constants.DEFAULT_RESOURCE_NAME));
        editor.apply();
        promise.resolve(true);
    }
}
