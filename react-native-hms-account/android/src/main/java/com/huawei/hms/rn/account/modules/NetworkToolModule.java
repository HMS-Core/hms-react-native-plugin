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

package com.huawei.hms.rn.account.modules;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.support.hwid.tools.NetworkTool;

import java.util.Objects;


public class NetworkToolModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "NetworkTool";

    private static final String FIELD_COOKIE_NAME = "cookieName";
    private static final String FIELD_COOKIE_VALUE = "cookieValue";
    private static final String FIELD_DOMAIN = "domain";
    private static final String FIELD_PATH = "path";
    private static final String FIELD_IS_HTTP_ONLY = "isHttpOnly";
    private static final String FIELD_IS_SECURE = "isSecure";
    private static final String FIELD_MAX_AGE = "maxAge";
    private static final String FIELD_IS_USE_HTTPS = "isUseHttps";

    public NetworkToolModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void buildNetWorkCookie(ReadableMap arguments, Promise promise) {
        promise.resolve(NetworkTool.buildNetworkCookie(
            Objects.requireNonNull(arguments.getString(FIELD_COOKIE_NAME)),
            arguments.getString(FIELD_COOKIE_VALUE),
            arguments.getString(FIELD_DOMAIN),
            arguments.getString(FIELD_PATH),
            arguments.getBoolean(FIELD_IS_HTTP_ONLY),
            arguments.getBoolean(FIELD_IS_SECURE),
            ((Double) arguments.getDouble(FIELD_MAX_AGE)).longValue()));
    }

    @ReactMethod
    public void buildNetworkUrl(ReadableMap arguments, Promise promise) {
        promise.resolve(NetworkTool.buildNetworkUrl(arguments.getString(FIELD_DOMAIN),
            arguments.getBoolean(FIELD_IS_USE_HTTPS)));
    }
}
