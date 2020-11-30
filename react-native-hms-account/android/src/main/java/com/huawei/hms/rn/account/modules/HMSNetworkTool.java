/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.account.modules;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.rn.account.utils.Utils;
import com.huawei.hms.support.hwid.tools.NetworkTool;
import com.huawei.hms.rn.account.logger.HMSLogger;


public class HMSNetworkTool extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "HMSNetworkTool";
    private static final String FIELD_COOKIE_NAME = "cookieName";
    private static final String FIELD_COOKIE_VALUE = "cookieValue";
    private static final String FIELD_DOMAIN = "domain";
    private static final String FIELD_PATH = "path";
    private static final String FIELD_IS_HTTP_ONLY = "isHttpOnly";
    private static final String FIELD_IS_SECURE = "isSecure";
    private static final String FIELD_MAX_AGE = "maxAge";
    private static final String FIELD_IS_USE_HTTPS = "isUseHttps";
    private HMSLogger logger;

    public HMSNetworkTool(ReactApplicationContext reactContext) {
        super(reactContext);
        logger = HMSLogger.getInstance(reactContext);
    }

    @NonNull
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void buildNetworkCookie(ReadableMap arguments, Promise promise) {

        String fieldCookieName = (String) Utils.argumentNullCheck(arguments, FIELD_COOKIE_NAME);
        String fieldCookieValue = (String) Utils.argumentNullCheck(arguments, FIELD_COOKIE_VALUE);
        String fieldDomain = (String) Utils.argumentNullCheck(arguments, FIELD_DOMAIN);
        String fieldPath = (String) Utils.argumentNullCheck(arguments, FIELD_PATH);
        Boolean fieldIsHttpOnly = arguments.hasKey(FIELD_IS_HTTP_ONLY) && arguments.getBoolean(FIELD_IS_HTTP_ONLY);
        Boolean fieldIsSecure= arguments.hasKey(FIELD_IS_SECURE) && arguments.getBoolean(FIELD_IS_SECURE);
        Long fieldMaxAge = Utils.argumentNullCheckAndConvert(arguments, FIELD_MAX_AGE);

        if(fieldCookieName != null) {
            logger.startMethodExecutionTimer("buildNetworkCookie");
            String networkCookieData = NetworkTool.buildNetworkCookie(fieldCookieName, fieldCookieValue, fieldDomain, fieldPath, fieldIsHttpOnly, fieldIsSecure, fieldMaxAge);
            logger.sendSingleEvent("buildNetworkCookie");
            promise.resolve(networkCookieData);
        } else{
            promise.reject("3010", "Null cookieName");
        }

    }

    @ReactMethod
    public void buildNetworkUrl(ReadableMap arguments, Promise promise) {
        String fieldDomain = (String) Utils.argumentNullCheck(arguments, FIELD_DOMAIN);
        Boolean fieldIsHttpOnly = arguments.hasKey(FIELD_IS_USE_HTTPS) && arguments.getBoolean(FIELD_IS_USE_HTTPS);

        if(fieldDomain != null) {
            logger.startMethodExecutionTimer("buildNetworkUrl");
            String networkUrl = NetworkTool.buildNetworkUrl(fieldDomain, fieldIsHttpOnly);
            logger.sendSingleEvent("buildNetworkUrl");
            promise.resolve(networkUrl);
        } else {
            promise.reject("3011", "Null domain");
        }
    }
}