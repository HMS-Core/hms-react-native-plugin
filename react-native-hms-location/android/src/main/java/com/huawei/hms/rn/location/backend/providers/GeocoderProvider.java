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

package com.huawei.hms.rn.location.backend.providers;

import android.util.Log;

import androidx.annotation.Nullable;

import com.huawei.hms.location.GeocoderService;
import com.huawei.hms.location.GetFromLocationNameRequest;
import com.huawei.hms.location.GetFromLocationRequest;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.rn.location.backend.interfaces.HMSCallback;
import com.huawei.hms.rn.location.backend.interfaces.HMSProvider;
import com.huawei.hms.rn.location.backend.logger.HMSLogger;
import com.huawei.hms.rn.location.backend.logger.HMSMethod;
import com.huawei.hms.rn.location.backend.utils.GeocoderUtils;
import com.huawei.hms.rn.location.backend.utils.LocationUtils;
import com.huawei.hms.rn.location.backend.utils.PlatformUtils;

import com.facebook.react.bridge.ReactApplicationContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class GeocoderProvider extends HMSProvider {
    private final static String TAG = GeocoderProvider.class.getSimpleName();

    private GeocoderService geocoderService;

    public GeocoderProvider(ReactApplicationContext ctx) {
        super(ctx);
    }

    @Override
    public JSONObject getConstants() throws JSONException {
        return null;
    }

    public void getFromLocation(final JSONObject getFromLocationRequest, @Nullable JSONObject locale,
        final HMSCallback callback) {
        Log.i(TAG, "getFromLocation start");
        HMSMethod method = new HMSMethod("getFromLocation");

        Locale mLocale = GeocoderUtils.FROM_JSON_OBJECT_TO_LOCALE.map(locale);

        geocoderService = LocationServices.getGeocoderService(getContext(), mLocale);

        final GetFromLocationRequest getFromLocationRequestObject
            = GeocoderUtils.FROM_JSON_OBJECT_TO_GET_FROM_LOCATION_REQUEST.map(getFromLocationRequest);

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer(method.getName());
        geocoderService.getFromLocation(getFromLocationRequestObject)
            .addOnSuccessListener(PlatformUtils.successListener(method, getContext(), callback,
                LocationUtils.FROM_HW_LOCATION_LIST_TO_JSON_ARRAY))
            .addOnFailureListener(PlatformUtils.failureListener(method, getContext(), callback));
        Log.i(TAG, "getFromLocation end");
    }

    public void getFromLocationName(final JSONObject getFromLocationNameRequest, @Nullable JSONObject locale,
        final HMSCallback callback) {
        Log.i(TAG, "getFromLocationName start");
        HMSMethod method = new HMSMethod("getFromLocationName");

        Locale mLocale = GeocoderUtils.FROM_JSON_OBJECT_TO_LOCALE.map(locale);

        geocoderService = LocationServices.getGeocoderService(getContext(), mLocale);

        final GetFromLocationNameRequest getFromLocationNameRequestObject
            = GeocoderUtils.FROM_JSON_OBJECT_TO_GET_FROM_LOCATION_NAME_REQUEST.map(getFromLocationNameRequest);

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer(method.getName());
        geocoderService.getFromLocationName(getFromLocationNameRequestObject)
            .addOnSuccessListener(PlatformUtils.successListener(method, getContext(), callback,
                LocationUtils.FROM_HW_LOCATION_LIST_TO_JSON_ARRAY))
            .addOnFailureListener(PlatformUtils.failureListener(method, getContext(), callback));
        Log.i(TAG, "getFromLocationName end");
    }
}
