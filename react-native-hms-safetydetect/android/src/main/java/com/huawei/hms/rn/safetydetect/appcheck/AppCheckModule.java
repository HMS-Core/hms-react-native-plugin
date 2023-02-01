/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.safetydetect.appcheck;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;

public class AppCheckModule extends ReactContextBaseJavaModule {
    private final AppCheckService appCheckService;

    public AppCheckModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.appCheckService = new AppCheckService(reactContext);
    }

    @Override
    public String getName() {
        return "HMSAppsCheck";
    }

    @Override
    public Map<String, Object> getConstants() {
        return appCheckService.getConstants();
    }

    @ReactMethod
    public void getMaliciousAppsList(Promise promise) {
        appCheckService.invokeGetMaliciousApps(promise);
    }

    @ReactMethod
    public void enableAppsCheck(Promise promise) {
        appCheckService.enableAppsCheck(promise);
    }

    @ReactMethod
    public void isVerifyAppsCheck(Promise promise) {
        appCheckService.isVerifyAppsCheck(promise);
    }
}