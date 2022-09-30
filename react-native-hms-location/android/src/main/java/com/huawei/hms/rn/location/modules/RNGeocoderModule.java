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

import androidx.annotation.Nullable;

import com.huawei.hms.rn.location.backend.providers.GeocoderProvider;
import com.huawei.hms.rn.location.helpers.ReactUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import org.json.JSONObject;

public class RNGeocoderModule extends ReactContextBaseJavaModule {

    private GeocoderProvider provider;

    public RNGeocoderModule(ReactApplicationContext reactContext) {
        super(reactContext);
        provider = ReactUtils.initializeProvider(new GeocoderProvider(reactContext), reactContext);
    }

    @Override
    public String getName() {
        return "HMSGeocoder";
    }

    @ReactMethod
    public void getFromLocation(final ReadableMap getFromLocationRequest, @Nullable ReadableMap locale,
        final Promise promise) {
        JSONObject mLocale = null;
        if (locale != null) {
            mLocale = toJO(locale);
        }
        provider.getFromLocation(toJO(getFromLocationRequest), mLocale, fromPromise(promise));
    }

    @ReactMethod
    public void getFromLocationName(final ReadableMap getFromLocationNameRequest, @Nullable ReadableMap locale,
        final Promise promise) {
        JSONObject mLocale = null;
        if (locale != null) {
            mLocale = toJO(locale);
        }
        provider.getFromLocationName(toJO(getFromLocationNameRequest), mLocale, fromPromise(promise));
    }

}
