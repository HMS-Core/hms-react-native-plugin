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

package com.huawei.hms.rn.ar;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.huawei.hms.plugin.ar.core.util.AREngineAvailability;
import com.huawei.hms.rn.ar.logger.HMSLogger;
import com.huawei.hms.rn.ar.utils.EnumGenerator;

import java.util.Map;

import javax.annotation.Nullable;

public class HmsARModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;

    private final HMSLogger hmsLogger;

    public HmsARModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        hmsLogger = HMSLogger.getInstance(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "HmsARModule";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return EnumGenerator.getConstants();
    }

    @ReactMethod
    public void isAREngineReady(Promise promise) {
        hmsLogger.sendSingleEvent("isAREngineServiceApkReady");
        promise.resolve(AREngineAvailability.isArEngineServiceApkReady(reactContext));
    }

    @ReactMethod
    public void navigateToAppMarket() {
        hmsLogger.sendSingleEvent("navigateToAppMarketPage");
        if (reactContext.getCurrentActivity() != null) {
            AREngineAvailability.navigateToAppMarketPage(reactContext.getCurrentActivity());
        }
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
