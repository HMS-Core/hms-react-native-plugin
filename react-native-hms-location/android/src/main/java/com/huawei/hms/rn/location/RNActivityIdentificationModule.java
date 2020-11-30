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
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

import com.huawei.hms.rn.location.backend.providers.ActivityIdentificationProvider;
import com.huawei.hms.rn.location.helpers.ReactUtils;

import java.util.Map;

import static com.huawei.hms.rn.location.helpers.RNCallback.fromPromise;
import static com.huawei.hms.rn.location.helpers.ReactUtils.toJA;

public class RNActivityIdentificationModule extends ReactContextBaseJavaModule {
    private ActivityIdentificationProvider provider;

    public RNActivityIdentificationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        provider = ReactUtils.initializeProvider(new ActivityIdentificationProvider(reactContext), reactContext,
                this::getCurrentActivity);
    }

    @Override
    public String getName() {
        return "HMSActivityIdentification";
    }

    @Override
    public Map<String, Object> getConstants() {
        return ReactUtils.getConstants(provider);
    }

    @ReactMethod
    public void createActivityConversionUpdates(final int requestCode,
        final ReadableArray activityConversionRequestArray, final Promise promise) {
        provider.createActivityConversionUpdates(requestCode, toJA(activityConversionRequestArray),
                fromPromise(promise));
    }

    @ReactMethod
    public void createActivityIdentificationUpdates(final int requestCode, double intervalMillis, final Promise promise) {
        provider.createActivityIdentificationUpdates(requestCode, intervalMillis, fromPromise(promise));
    }

    @ReactMethod
    public void deleteActivityConversionUpdates(final int requestCode, final Promise promise) {
        provider.deleteActivityConversionUpdates(requestCode, fromPromise(promise));
    }

    @ReactMethod
    public void deleteActivityIdentificationUpdates(final int requestCode, final Promise promise) {
        provider.deleteActivityIdentificationUpdates(requestCode, fromPromise(promise));
    }

    @ReactMethod
    public void hasPermission(final Promise promise) {
        provider.hasPermission(fromPromise(promise));
    }

    @ReactMethod
    public void requestPermission(final Promise promise) {
        provider.requestPermission(fromPromise(promise));
    }
}
