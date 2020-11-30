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

import java.util.Map;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

import com.huawei.hms.rn.location.backend.providers.GeofenceProvider;
import com.huawei.hms.rn.location.helpers.ReactUtils;

import static com.huawei.hms.rn.location.helpers.RNCallback.fromPromise;

public class RNGeofenceModule extends ReactContextBaseJavaModule {
    private GeofenceProvider provider;

    public RNGeofenceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        provider = ReactUtils.initializeProvider(new GeofenceProvider(reactContext), reactContext,
                this::getCurrentActivity);
    }

    @Override
    public Map<String, Object> getConstants() {
        return ReactUtils.getConstants(provider);
    }

    @Override
    public String getName() {
        return "HMSGeofence";
    }

    @ReactMethod
    public void createGeofenceList(final int requestCode, final ReadableArray geofences, final int initConversions,
        final int coordinateType, final Promise promise) {
        provider.createGeofenceList(requestCode, ReactUtils.toJA(geofences), initConversions, coordinateType,
                fromPromise(promise));
    }

    @ReactMethod
    public void deleteGeofenceList(final int requestCode, final Promise promise) {
        provider.deleteGeofenceList(requestCode, fromPromise(promise));
    }
}
