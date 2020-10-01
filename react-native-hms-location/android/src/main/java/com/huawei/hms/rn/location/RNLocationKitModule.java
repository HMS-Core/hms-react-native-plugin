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

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.hms.rn.location.backend.helpers.HMSBroadcastReceiver;
import com.huawei.hms.rn.location.backend.logger.HMSMethod;
import com.huawei.hms.rn.location.helpers.ReactUtils;

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
}
