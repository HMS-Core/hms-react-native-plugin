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

package com.huawei.hms.rn.safetydetect.huaweiapi;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.hms.rn.safetydetect.logger.HMSLogger;

public class HuaweiApiModule extends ReactContextBaseJavaModule {
    private final HuaweiApiService huaweiApiService = new HuaweiApiService();
    private final HMSLogger hmsLogger;

    public HuaweiApiModule(ReactApplicationContext reactContext) {
        super(reactContext);
        hmsLogger = HMSLogger.getInstance(reactContext);
    }

    @Override
    public String getName() {
        return "HMSHuaweiApi";
    }

    @ReactMethod
    public void isHuaweiMobileServicesAvailable(Promise promise) {
        hmsLogger.sendSingleEvent("isHuaweiMobileServicesAvailable");
        huaweiApiService.isHuaweiMobileServicesAvailable(getCurrentActivity(), promise);
    }

    @ReactMethod
    public void disableLogger() {
        hmsLogger.disableLogger();
    }

    @ReactMethod
    public void enableLogger() {
        hmsLogger.enableLogger();
    }
}
