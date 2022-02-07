/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.push.fcm;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.hms.push.plugin.base.proxy.ProxySettings;
import com.huawei.hms.push.plugin.fcm.FcmPushProxy;

public class HMSPushFCM extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private HMSLogger hmsLogger;

    public HMSPushFCM(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        hmsLogger = HMSLogger.getInstance(reactContext);
    }

    @Override
    public String getName() {
        return "HMSPushFCM";
    }

    @ReactMethod
    public void init(final Promise promise) {
        boolean result = FcmPushProxy.init(reactContext);
        promise.resolve(result);
    }

    @ReactMethod
    public void setCountryCode(String countryCode, Promise promise){
        ProxySettings.setCountryCode(reactContext,countryCode);
        promise.resolve("success");
    }

    @ReactMethod
    public void isProxyInitEnabled(final Promise promise) {
        boolean isProxyInitEnabled = FcmPushProxy.isProxyInitEnabled(reactContext);
        promise.resolve(isProxyInitEnabled);
    }

    @ReactMethod
    public void enableLogger(final Promise promise){
        hmsLogger.enableLogger();
        promise.resolve(true);
    }

    @ReactMethod
    public void disableLogger(final Promise promise){
        hmsLogger.disableLogger();
        promise.resolve(true);
    }
}
