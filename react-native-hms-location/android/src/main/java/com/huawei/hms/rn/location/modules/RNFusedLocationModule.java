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

package com.huawei.hms.rn.location.modules;

import static com.huawei.hms.rn.location.helpers.RNCallback.fromPromise;
import static com.huawei.hms.rn.location.helpers.ReactUtils.toJO;

import android.app.Activity;
import android.content.Intent;

import com.huawei.hms.rn.location.backend.providers.FusedLocationProvider;
import com.huawei.hms.rn.location.helpers.ReactUtils;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.util.Map;

public class RNFusedLocationModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private FusedLocationProvider provider;

    public RNFusedLocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
        provider = ReactUtils.initializeProvider(new FusedLocationProvider(reactContext), reactContext);
    }

    @Override
    public Map<String, Object> getConstants() {
        return ReactUtils.getConstants(provider);
    }

    @Override
    public String getName() {
        return "HMSFusedLocation";
    }

    @ReactMethod
    public void enableBackgroundLocation(final int id, final ReadableMap notification, final Promise promise) {
        provider.enableBackgroundLocation(id, notification, fromPromise(promise));
    }

    @ReactMethod
    public void disableBackgroundLocation(final Promise promise) {
        provider.disableBackgroundLocation(fromPromise(promise));
    }

    @ReactMethod
    public void setLogConfig(final ReadableMap LogConfig, final Promise promise) {
        provider.setLogConfig(toJO(LogConfig), fromPromise(promise));
    }

    @ReactMethod
    public void getLogConfig(final Promise promise) {
        provider.getLogConfig(fromPromise(promise));
    }

    @ReactMethod
    public void flushLocations(final Promise promise) {
        provider.flushLocations(fromPromise(promise));
    }

    @ReactMethod
    public void checkLocationSettings(final ReadableMap locationRequestMap, final Promise promise) {
        provider.checkLocationSettings(toJO(locationRequestMap), fromPromise(promise));
    }

    @ReactMethod
    public void getNavigationContextState(final int requestType, final Promise promise) {
        provider.getNavigationContextState(requestType, fromPromise(promise));
    }

    @ReactMethod
    public void getLastLocation(final Promise promise) {
        provider.getLastLocation(fromPromise(promise));
    }

    @ReactMethod
    public void getLastLocationWithAddress(final ReadableMap map, final Promise promise) {
        provider.getLastLocationWithAddress(toJO(map), fromPromise(promise));
    }

    @ReactMethod
    public void getLocationAvailability(final Promise promise) {
        provider.getLocationAvailability(fromPromise(promise));
    }

    @ReactMethod
    public void setMockLocation(ReadableMap map, final Promise promise) {
        provider.setMockLocation(toJO(map), fromPromise(promise));
    }

    @ReactMethod
    public void setMockMode(final boolean shouldMock, final Promise promise) {
        provider.setMockMode(shouldMock, fromPromise(promise));
    }

    @ReactMethod
    public void requestLocationUpdates(final int requestCode, final ReadableMap readableMap, final Promise promise) {
        provider.requestLocationUpdates(requestCode, toJO(readableMap), fromPromise(promise));
    }

    @ReactMethod
    public void removeLocationUpdates(final int requestCode, final Promise promise) {
        provider.removeLocationUpdates(requestCode, fromPromise(promise));
    }

    @ReactMethod
    public void requestLocationUpdatesWithCallback(final ReadableMap readableMap, final Promise promise) {
        provider.requestLocationUpdatesWithCallback(toJO(readableMap), fromPromise(promise));
    }

    @ReactMethod
    public void requestLocationUpdatesWithCallbackEx(final ReadableMap readableMap, final Promise promise) {
        provider.requestLocationUpdatesWithCallbackEx(toJO(readableMap), fromPromise(promise));
    }

    @ReactMethod
    public void removeLocationUpdatesWithCallback(final int requestCode, final Promise promise) {
        provider.removeLocationUpdatesWithCallback(requestCode, fromPromise(promise));
    }

    @ReactMethod
    public void hasPermission(final Promise promise) {
        provider.hasPermission(fromPromise(promise));
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        provider.onActivityResult(activity, requestCode, resultCode, data);
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
