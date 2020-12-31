/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.location.backend.utils;

import android.location.Location;
import android.util.Log;

import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.HWLocation;
import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationSettingsRequest;
import com.huawei.hms.location.LocationSettingsResponse;
import com.huawei.hms.location.LocationSettingsStates;
import com.huawei.hms.location.NavigationResult;
import com.huawei.hms.rn.location.backend.helpers.Exceptions;
import com.huawei.hms.rn.location.backend.interfaces.HMSCallback;
import com.huawei.hms.rn.location.backend.interfaces.HMSProvider;
import com.huawei.hms.rn.location.backend.interfaces.Mapper;

import org.json.JSONObject;

import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.GE_OREO;
import static com.huawei.hms.rn.location.backend.helpers.Exceptions.ERR_NO_FUSED_LOCATION_PROVIDER;
import static com.huawei.hms.rn.location.backend.helpers.Exceptions.ERR_NO_PERMISSION;
import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.mapperWrapper;

public class LocationUtils {
    private static final String TAG = LocationUtils.class.getSimpleName();

    public static final Mapper<JSONObject, LocationRequest> FROM_JSON_OBJECT_TO_LOCATION_REQUEST =
            mapperWrapper((JSONObject jo) -> LocationRequest.create()
                    .setPriority(jo.optInt("priority", LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY))
                    .setInterval((long) jo.optDouble("interval", 3600000L))
                    .setNumUpdates(jo.optInt("numUpdates", Integer.MAX_VALUE))
                    .setFastestInterval((long) jo.optDouble("fastestInterval", 600000L))
                    .setExpirationDuration((long) jo.optDouble("expirationTimeDuration", Long.MAX_VALUE))
                    .setExpirationTime((long) jo.optDouble("expirationTime", Long.MAX_VALUE))
                    .setSmallestDisplacement((float) jo.optDouble("smallestDisplacement", 0))
                    .setMaxWaitTime((long) jo.optDouble("maxWaitTime", 0L))
                    .setNeedAddress(jo.optBoolean("needAddress", false))
                    .setLanguage(jo.optString("language", "EN"))
                    .setCountryCode(jo.optString("countryCode", "")));

    public static final Mapper<JSONObject, LocationSettingsRequest> FROM_JSON_OBJECT_TO_LOCATION_SETTINGS_REQUEST =
            mapperWrapper((JSONObject jo) -> new LocationSettingsRequest.Builder()
                    .addAllLocationRequests(PlatformUtils.mapJSONArray(jo.getJSONArray("locationRequests"),
                            FROM_JSON_OBJECT_TO_LOCATION_REQUEST))
                    .setAlwaysShow(jo.optBoolean("alwaysShow"))
                    .setNeedBle(jo.optBoolean("needBle"))
                    .build());

    public static final Mapper<LocationResult, JSONObject> FROM_LOCATION_RESULT_TO_JSON_OBJECT =
            mapperWrapper((LocationResult obj) -> new JSONObject()
                    .put("lastHWLocation", LocationUtils.FROM_HW_LOCATION_TO_JSON_OBJECT.map(obj.getLastHWLocation()))
                    .put("lastLocation", LocationUtils.FROM_LOCATION_TO_JSON_OBJECT.map(obj.getLastLocation()))
                    .put("locations", PlatformUtils.mapList(obj.getLocations(),
                            LocationUtils.FROM_LOCATION_TO_JSON_OBJECT))
                    .put("hwLocationList", PlatformUtils.mapList(obj.getHWLocationList(),
                            LocationUtils.FROM_HW_LOCATION_TO_JSON_OBJECT)), new JSONObject());

    public static final Mapper<Location, JSONObject> FROM_LOCATION_TO_JSON_OBJECT =
            mapperWrapper((Location obj) -> new JSONObject()
                    .put("latitude", obj.getLatitude())
                    .put("longitude", obj.getLongitude())
                    .put("altitude", obj.getAltitude())
                    .put("speed", obj.getSpeed())
                    .put("bearing", obj.getBearing())
                    .put("accuracy", obj.getAccuracy())
                    .put("time", obj.getTime())
                    .put("fromMockProvider", obj.isFromMockProvider())
                    .put("verticalAccuracyMeters", GE_OREO ? obj.getVerticalAccuracyMeters() : 0.0)
                    .put("bearingAccuracyDegrees", GE_OREO ? obj.getBearingAccuracyDegrees() : 0.0)
                    .put("speedAccuracyMetersPerSecond", GE_OREO ?
                            obj.getSpeedAccuracyMetersPerSecond() : 0.0), new JSONObject());

    public static final Mapper<HWLocation, JSONObject> FROM_HW_LOCATION_TO_JSON_OBJECT =
            mapperWrapper((HWLocation obj) -> new JSONObject()
                    .put("latitude", obj.getLatitude())
                    .put("longitude", obj.getLongitude())
                    .put("altitude", obj.getAltitude())
                    .put("speed", obj.getSpeed())
                    .put("bearing", obj.getBearing())
                    .put("accuracy", obj.getAccuracy())
                    .put("provider", obj.getProvider())
                    .put("time", obj.getTime())
                    .put("elapsedRealtimeNanos", obj.getElapsedRealtimeNanos())
                    .put("countryCode", obj.getCountryCode())
                    .put("countryName", obj.getCountryName())
                    .put("state", obj.getState())
                    .put("city", obj.getCity())
                    .put("county", obj.getCounty())
                    .put("street", obj.getStreet())
                    .put("featureName", obj.getFeatureName())
                    .put("postalCode", obj.getPostalCode())
                    .put("phone", obj.getPhone())
                    .put("url", obj.getUrl())
                    .put("extraInfo", PlatformUtils.fromMapToJSONObject(obj.getExtraInfo()))
                    .put("verticalAccuracyMeters", GE_OREO ? obj.getVerticalAccuracyMeters() : 0.0)
                    .put("bearingAccuracyDegrees", GE_OREO ? obj.getBearingAccuracyDegrees() : 0.0)
                    .put("speedAccuracyMetersPerSecond", GE_OREO ? obj.getSpeedAccuracyMetersPerSecond() : 0.0),
                    new JSONObject());

    public static final Mapper<LocationSettingsStates, JSONObject> FROM_LOCATION_SETTINGS_STATES_TO_JSON_OBJECT =
            mapperWrapper((LocationSettingsStates obj) -> new JSONObject()
                    .put("isBlePresent", obj.isBlePresent())
                    .put("isBleUsable", obj.isBleUsable())
                    .put("isGpsPresent", obj.isGpsPresent())
                    .put("isGpsUsable", obj.isGpsUsable())
                    .put("isLocationPresent", obj.isLocationPresent())
                    .put("isLocationUsable", obj.isLocationUsable())
                    .put("isNetworkLocationPresent", obj.isNetworkLocationPresent())
                    .put("isNetworkLocationUsable", obj.isNetworkLocationUsable())
                    .put("isHMSLocationPresent", obj.isHMSLocationPresent())
                    .put("isHMSLocationUsable", obj.isHMSLocationUsable()), new JSONObject());

    public static final Mapper<LocationAvailability, JSONObject> FROM_LOCATION_AVAILABILITY_TO_JSON_OBJECT =
            mapperWrapper((LocationAvailability obj) -> new JSONObject()
                    .put("isLocationAvailable", obj.isLocationAvailable()));

    public static final Mapper<LocationSettingsStates, JSONObject> FROM_LOCATION_SETTINGS_RESULT_TO_JSON_OBJECT =
            mapperWrapper((LocationSettingsStates obj) -> new JSONObject()
                    .put("locationSettingsStates", FROM_LOCATION_SETTINGS_STATES_TO_JSON_OBJECT.map(obj)));

    public static final Mapper<LocationSettingsResponse, JSONObject> FROM_LOCATION_SETTINGS_STATES_RESPONSE_TO_JSON_OBJECT =
            mapperWrapper((LocationSettingsResponse obj) -> new JSONObject()
                    .put("locationSettingsStates",
                            FROM_LOCATION_SETTINGS_STATES_TO_JSON_OBJECT.map(obj.getLocationSettingsStates())));

    public static final Mapper<NavigationResult, JSONObject> FROM_NAVIGATION_RESULT_TO_JSON_OBJECT =
            mapperWrapper((NavigationResult obj) -> new JSONObject()
                    .put("state", obj.getState())
                    .put("possibility", obj.getPossibility()), new JSONObject());

    public static boolean checkForObstacles(HMSProvider provider, FusedLocationProviderClient fused,
                                            final HMSCallback callback) {
        if (!PermissionUtils.hasLocationPermission(provider)) {
            Log.i(TAG, "checkForObstacles -> no permissions");
            if (callback != null) {
                callback.error(Exceptions.toErrorJSON(ERR_NO_PERMISSION));
            }
            return true;
        }

        if (fused == null) {
            Log.i(TAG, "checkForObstacles -> fusedLocationProviderClient is null");
            if (callback != null) {
                callback.error(Exceptions.toErrorJSON(ERR_NO_FUSED_LOCATION_PROVIDER));
            }
            return true;
        }

        return false;
    }
}
