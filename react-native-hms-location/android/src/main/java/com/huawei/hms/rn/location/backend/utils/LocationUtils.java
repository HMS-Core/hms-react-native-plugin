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

package com.huawei.hms.rn.location.backend.utils;

import android.location.Location;
import android.os.Build;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.huawei.hms.rn.location.backend.helpers.Exceptions.ERR_NO_FUSED_LOCATION_PROVIDER;
import static com.huawei.hms.rn.location.backend.helpers.Exceptions.ERR_NO_PERMISSION;
import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.mapperWrapper;

public class LocationUtils {
    private static final String TAG = LocationUtils.class.getSimpleName();

    public static final Mapper<JSONObject, LocationRequest> FROM_JSON_OBJECT_TO_LOCATION_REQUEST =
            mapperWrapper((JSONObject readableMap) -> LocationRequest.create()
                    .setPriority(readableMap.getInt("priority"))
                    .setInterval((long) readableMap.getDouble("interval"))
                    .setNumUpdates(readableMap.getInt("numUpdates"))
                    .setFastestInterval((long) readableMap.getDouble("fastestInterval"))
                    .setExpirationTime((long) readableMap.getDouble("expirationTime"))
                    .setExpirationDuration((long) readableMap.getDouble("expirationTimeDuration"))
                    .setSmallestDisplacement((long) readableMap.getDouble("smallestDisplacement"))
                    .setMaxWaitTime((long) readableMap.getDouble("maxWaitTime"))
                    .setNeedAddress(readableMap.getBoolean("needAddress"))
                    .setLanguage(readableMap.getString("language"))
                    .setCountryCode(readableMap.getString("countryCode")));

    public static final Mapper<JSONObject, LocationSettingsRequest> FROM_JSON_OBJECT_TO_LOCATION_SETTINGS_REQUEST =
            mapperWrapper((JSONObject locationRequestMap) -> {
                JSONArray locationRequestsArray = locationRequestMap.getJSONArray("locationRequests");
                List<LocationRequest> locationRequestList = PlatformUtils.mapJSONArray(locationRequestsArray,
                        FROM_JSON_OBJECT_TO_LOCATION_REQUEST);

                LocationSettingsRequest.Builder locationRequest = new LocationSettingsRequest.Builder();
                return locationRequest.addAllLocationRequests(locationRequestList)
                        .setAlwaysShow(locationRequestMap.getBoolean("alwaysShow"))
                        .setNeedBle(locationRequestMap.getBoolean("needBle"))
                        .build();
            });

    public static final Mapper<LocationResult, JSONObject> FROM_LOCATION_RESULT_TO_JSON_OBJECT =
            mapperWrapper((LocationResult locationResult) -> {
                JSONObject map = new JSONObject();
                map.put("lastHWLocation",
                        LocationUtils.FROM_HW_LOCATION_TO_JSON_OBJECT.map(locationResult.getLastHWLocation()));
                map.put("lastLocation",
                        LocationUtils.FROM_LOCATION_TO_JSON_OBJECT.map(locationResult.getLastLocation()));
                map.put("locations",
                        PlatformUtils.mapList(locationResult.getLocations(),
                                LocationUtils.FROM_LOCATION_TO_JSON_OBJECT));
                map.put("hwLocationList",
                        PlatformUtils.mapList(locationResult.getHWLocationList(),
                                LocationUtils.FROM_HW_LOCATION_TO_JSON_OBJECT));
                return map;
            }, new JSONObject());

    public static final Mapper<Location, JSONObject> FROM_LOCATION_TO_JSON_OBJECT =
            mapperWrapper((Location location) -> {
                JSONObject map = new JSONObject();

                map.put("latitude", location.getLatitude());
                map.put("longitude", location.getLongitude());
                map.put("altitude", location.getAltitude());
                map.put("speed", location.getSpeed());
                map.put("bearing", location.getBearing());
                map.put("accuracy", location.getAccuracy());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    map.put("verticalAccuracyMeters", location.getVerticalAccuracyMeters());
                    map.put("bearingAccuracyDegrees", location.getBearingAccuracyDegrees());
                    map.put("speedAccuracyMetersPerSecond", location.getSpeedAccuracyMetersPerSecond());
                } else {
                    map.put("verticalAccuracyMeters", 0.0);
                    map.put("bearingAccuracyDegrees", 0.0);
                    map.put("speedAccuracyMetersPerSecond", 0.0);
                }

                map.put("time", location.getTime());
                map.put("fromMockProvider", location.isFromMockProvider());

                return map;
            }, new JSONObject());

    public static final Mapper<HWLocation, JSONObject> FROM_HW_LOCATION_TO_JSON_OBJECT =
            mapperWrapper((HWLocation hwLocation) -> {
                JSONObject result = new JSONObject();
                result.put("latitude", hwLocation.getLatitude());
                result.put("longitude", hwLocation.getLongitude());
                result.put("altitude", hwLocation.getAltitude());
                result.put("speed", hwLocation.getSpeed());
                result.put("bearing", hwLocation.getBearing());
                result.put("accuracy", hwLocation.getAccuracy());
                result.put("provider", hwLocation.getProvider());
                result.put("time", hwLocation.getTime());
                result.put("elapsedRealtimeNanos", hwLocation.getElapsedRealtimeNanos());
                result.put("countryCode", hwLocation.getCountryCode());
                result.put("countryName", hwLocation.getCountryName());
                result.put("state", hwLocation.getState());
                result.put("city", hwLocation.getCity());
                result.put("county", hwLocation.getCounty());
                result.put("street", hwLocation.getStreet());
                result.put("featureName", hwLocation.getFeatureName());
                result.put("postalCode", hwLocation.getPostalCode());
                result.put("phone", hwLocation.getPhone());
                result.put("url", hwLocation.getUrl());
                result.put("extraInfo", PlatformUtils.fromMapToJSONObject(hwLocation.getExtraInfo()));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    result.put("verticalAccuracyMeters", hwLocation.getVerticalAccuracyMeters());
                    result.put("bearingAccuracyDegrees", hwLocation.getBearingAccuracyDegrees());
                    result.put("speedAccuracyMetersPerSecond", hwLocation.getSpeedAccuracyMetersPerSecond());
                } else {
                    result.put("verticalAccuracyMeters", 0.0);
                    result.put("bearingAccuracyDegrees", 0.0);
                    result.put("speedAccuracyMetersPerSecond", 0.0);
                }

                return result;
            }, new JSONObject());

    public static final Mapper<LocationSettingsStates, JSONObject> FROM_LOCATION_SETTINGS_STATES_TO_JSON_OBJECT =
            mapperWrapper((LocationSettingsStates locationSettingsStates) -> {
                JSONObject result = new JSONObject();
                result.put("isBlePresent", locationSettingsStates.isBlePresent());
                result.put("isBleUsable", locationSettingsStates.isBleUsable());
                result.put("isGpsPresent", locationSettingsStates.isGpsPresent());
                result.put("isGpsUsable", locationSettingsStates.isGpsUsable());
                result.put("isLocationPresent", locationSettingsStates.isLocationPresent());
                result.put("isLocationUsable", locationSettingsStates.isLocationUsable());
                result.put("isNetworkLocationPresent", locationSettingsStates.isNetworkLocationPresent());
                result.put("isNetworkLocationUsable", locationSettingsStates.isNetworkLocationUsable());

                return result;
            }, new JSONObject());

    public static final Mapper<LocationAvailability, JSONObject> FROM_LOCATION_AVAILABILITY_TO_JSON_OBJECT =
            mapperWrapper((LocationAvailability locationAvailability) -> {
                JSONObject result = new JSONObject();
                result.put("isLocationAvailable", locationAvailability.isLocationAvailable());
                return result;
            });

    public static final Mapper<LocationSettingsResponse, JSONObject> FROM_LOCATION_SETTINGS_STATES_RESPONSE_TO_JSON_OBJECT = mapperWrapper((LocationSettingsResponse locationSettingsResponse) -> {
        JSONObject result = new JSONObject();
        result.put("locationSettingsStates",
                FROM_LOCATION_SETTINGS_STATES_TO_JSON_OBJECT.map(locationSettingsResponse.getLocationSettingsStates()));
        return result;
    });

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

    public static final Mapper<NavigationResult, JSONObject> FROM_NAVIGATION_RESULT_TO_JSON_OBJECT =
            mapperWrapper((NavigationResult navigationResult) -> {
                JSONObject result = new JSONObject();
                result.put("state", navigationResult.getState());
                result.put("possibility", navigationResult.getPossibility());

                return result;
            }, new JSONObject());
}
