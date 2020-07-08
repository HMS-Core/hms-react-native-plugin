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

package com.huawei.hms.rn.location.utils;

import android.util.Log;
import android.os.Build;
import android.location.Location;
import android.app.Activity;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;

import com.huawei.hms.location.HWLocation;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationSettingsRequest;
import com.huawei.hms.location.LocationSettingsStates;
import com.huawei.hms.rn.location.helpers.Exceptions;
import com.huawei.hms.rn.location.helpers.Mapper;

import java.util.List;


public class LocationUtils {

    private static final String TAG = LocationUtils.class.getSimpleName();

    public static Mapper<ReadableMap, LocationRequest> fromReadableMapToLocationRequest = new Mapper<ReadableMap, LocationRequest>() {
        @Override
        public LocationRequest map(ReadableMap readableMap) {
            return LocationRequest.create()
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
                    .setCountryCode(readableMap.getString("countryCode"));
        }
    };

    public static Mapper<ReadableMap, LocationSettingsRequest> fromReadableMapToLocationSettingsRequest = new Mapper<ReadableMap, LocationSettingsRequest>() {
        @Override
        public LocationSettingsRequest map(ReadableMap locationRequestMap) {
            ReadableArray locationRequestsArray = locationRequestMap.getArray("locationRequests");
            List<LocationRequest> locationRequestList = ReactUtils.mapReadableArray(locationRequestsArray, fromReadableMapToLocationRequest);

            LocationSettingsRequest.Builder locationRequest = new LocationSettingsRequest.Builder();
            return locationRequest.addAllLocationRequests(locationRequestList)
                    .setAlwaysShow(locationRequestMap.getBoolean("alwaysShow"))
                    .setNeedBle(locationRequestMap.getBoolean("needBle"))
                    .build();
        }
    };

    public static WritableMap fromLocationToWritableMap(Location location) {
        WritableMap map = Arguments.createMap();

        map.putDouble("latitude", location.getLatitude());
        map.putDouble("longitude", location.getLongitude());
        map.putDouble("altitude", location.getAltitude());
        map.putDouble("speed", location.getSpeed());
        map.putDouble("bearing", location.getBearing());
        map.putDouble("accuracy", location.getAccuracy());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            map.putDouble("verticalAccuracyMeters", location.getVerticalAccuracyMeters());
            map.putDouble("bearingAccuracyDegrees", location.getBearingAccuracyDegrees());
            map.putDouble("speedAccuracyMetersPerSecond", location.getSpeedAccuracyMetersPerSecond());
        } else {
            map.putDouble("verticalAccuracyMeters", 0.0);
            map.putDouble("bearingAccuracyDegrees", 0.0);
            map.putDouble("speedAccuracyMetersPerSecond", 0.0);
        }

        map.putDouble("time", location.getTime());
        map.putBoolean("fromMockProvider", location.isFromMockProvider());

        return map;
    }

    public static boolean checkForObstacles(Activity activity, FusedLocationProviderClient fused) {
        return checkForObstacles(activity, fused, null);
    }

    public static boolean checkForObstacles(Activity activity, FusedLocationProviderClient fused, final Promise promise) {
        if (!PermissionUtils.hasLocationPermission(activity)) {
            Log.i(TAG, "checkForObstacles -> no permissions");
            if (promise != null) {
                promise.reject(new Exceptions.NoPermissionsError());
            }
            return true;
        }

        if (fused == null) {
            Log.i(TAG, "checkForObstacles -> fusedLocationProviderClient is null");
            if (promise != null) {
                promise.reject(new Exceptions.NoFusedLocationProviderError());
            }
            return true;
        }

        return false;
    }

    public static String hwLocationToString(HWLocation hwLocation) {
        return "HWLocation ["
            + "longitude=" + hwLocation.getLongitude()
            + ",latitude=" + hwLocation.getLatitude()
            + ",accuracy=" + hwLocation.getAccuracy()
            + ",countryName=" + hwLocation.getCountryName()
            + ",state=" + hwLocation.getState()
            + ",city=" + hwLocation.getCity()
            + ",county=" + hwLocation.getCounty()
            + ",featureName=" + hwLocation.getFeatureName()
            + "]";
    }

    public static WritableMap fromHWLocationToWritableMap(HWLocation hwLocation) {
        WritableMap result = Arguments.createMap();
        result.putDouble("latitude", hwLocation.getLatitude());
        result.putDouble("longitude", hwLocation.getLongitude());
        result.putDouble("altitude", hwLocation.getAltitude ());
        result.putDouble("speed", hwLocation.getSpeed());
        result.putDouble("bearing", hwLocation.getBearing());
        result.putDouble("accuracy", hwLocation.getAccuracy());
        result.putString("provider", hwLocation.getProvider());
        result.putDouble("time", hwLocation.getTime());
        result.putDouble("elapsedRealtimeNanos", hwLocation.getElapsedRealtimeNanos());
        result.putString("countryCode", hwLocation.getCountryCode());
        result.putString("countryName", hwLocation.getCountryName());
        result.putString("state", hwLocation.getState());
        result.putString("city", hwLocation.getCity());
        result.putString("county", hwLocation.getCounty());
        result.putString("street", hwLocation.getStreet());
        result.putString("featureName", hwLocation.getFeatureName());
        result.putString("postalCode", hwLocation.getPostalCode());
        result.putString("phone", hwLocation.getPhone());
        result.putString("url", hwLocation.getUrl());
        result.putMap("extraInfo", ReactUtils.fromMapToWritableMap(hwLocation.getExtraInfo()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            result.putDouble("verticalAccuracyMeters", hwLocation.getVerticalAccuracyMeters());
            result.putDouble("bearingAccuracyDegrees", hwLocation.getBearingAccuracyDegrees());
            result.putDouble("speedAccuracyMetersPerSecond", hwLocation.getSpeedAccuracyMetersPerSecond());
        } else {
            result.putDouble("verticalAccuracyMeters", 0.0);
            result.putDouble("bearingAccuracyDegrees", 0.0);
            result.putDouble("speedAccuracyMetersPerSecond", 0.0);
        }

        return result;
    }

    public static WritableMap fromLocationSettingsStatesResponseToWritableMap(LocationSettingsStates locationSettingsStates) {
        WritableMap result = Arguments.createMap();
        result.putBoolean("isBlePresent", locationSettingsStates.isBlePresent());
        result.putBoolean("isBleUsable", locationSettingsStates.isBleUsable());
        result.putBoolean("isGpsPresent", locationSettingsStates.isGpsPresent());
        result.putBoolean("isGpsUsable", locationSettingsStates.isGpsUsable());
        result.putBoolean("isLocationPresent", locationSettingsStates.isLocationPresent());
        result.putBoolean("isLocationUsable", locationSettingsStates.isLocationUsable());
        result.putBoolean("isNetworkLocationPresent", locationSettingsStates.isNetworkLocationPresent());
        result.putBoolean("isNetworkLocationUsable", locationSettingsStates.isNetworkLocationUsable());

        // TODO: result.putMap("status", fromStatusToWritableMap(locationSettingsStates.getStatus()))
        return result;
    }

}
