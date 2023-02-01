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

package com.huawei.hms.rn.safetydetect.userdetect;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class UserDetectModule extends ReactContextBaseJavaModule {
    private final UserDetectService userDetectService;

    public UserDetectModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.userDetectService = new UserDetectService(reactContext);
    }

    @Override
    public String getName() {
        return "HMSUserDetect";
    }

    @ReactMethod
    public void initUserDetect(Promise promise) {
        userDetectService.initUserDetect(promise);
    }

    @ReactMethod
    public void userDetection(String appId, Promise promise) {
        userDetectService.userDetection(appId, promise);
    }

    @ReactMethod
    public void shutdownUserDetect(Promise promise) {
        userDetectService.shutdownUserDetect(promise);
    }

    @ReactMethod
    public void initAntiFraud(String appId, Promise promise) {
        userDetectService.initAntiFraud(appId, promise);
    }

    @ReactMethod
    public void getRiskToken(Promise promise) {
        userDetectService.getRiskToken(promise);
    }

    @ReactMethod
    public void releaseAntiFraud(Promise promise) {
        userDetectService.releaseAntiFraud(promise);
    }
}

