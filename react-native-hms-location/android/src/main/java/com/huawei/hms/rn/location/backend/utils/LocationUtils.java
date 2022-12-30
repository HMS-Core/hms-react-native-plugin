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

package com.huawei.hms.rn.location.backend.utils;

import static com.huawei.hms.rn.location.backend.helpers.Constants.DEFAULT_DEF_TYPE;
import static com.huawei.hms.rn.location.backend.helpers.Constants.DEFAULT_RESOURCE_NAME;
import static com.huawei.hms.rn.location.backend.helpers.Exceptions.ERR_NO_FUSED_LOCATION_PROVIDER;
import static com.huawei.hms.rn.location.backend.helpers.Exceptions.ERR_NO_PERMISSION;
import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.GE_OREO;
import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.mapperWrapper;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
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
import com.huawei.hms.location.LogConfig;
import com.huawei.hms.location.NavigationResult;
import com.huawei.hms.rn.location.backend.helpers.Exceptions;
import com.huawei.hms.rn.location.backend.interfaces.HMSCallback;
import com.huawei.hms.rn.location.backend.interfaces.HMSProvider;
import com.huawei.hms.rn.location.backend.interfaces.Mapper;
import com.huawei.hms.support.api.entity.location.coordinate.LonLat;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtils {
    private static final String TAG = LocationUtils.class.getSimpleName();

    public static final Mapper<JSONObject, LocationRequest> FROM_JSON_OBJECT_TO_LOCATION_REQUEST = mapperWrapper(
        (JSONObject jo) -> LocationRequest.create()
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
            .setCountryCode(jo.optString("countryCode", ""))
            .setCoordinateType(jo.optInt("coordinateType", 0)));

    public static final Mapper<JSONObject, LocationSettingsRequest> FROM_JSON_OBJECT_TO_LOCATION_SETTINGS_REQUEST
        = mapperWrapper((JSONObject jo) -> new LocationSettingsRequest.Builder().addAllLocationRequests(
        PlatformUtils.mapJSONArray(jo.getJSONArray("locationRequests"), FROM_JSON_OBJECT_TO_LOCATION_REQUEST))
        .setAlwaysShow(jo.optBoolean("alwaysShow"))
        .setNeedBle(jo.optBoolean("needBle"))
        .build());

    public static final Mapper<LocationResult, JSONObject> FROM_LOCATION_RESULT_TO_JSON_OBJECT = mapperWrapper(
        (LocationResult obj) -> new JSONObject().put("lastHWLocation",
            LocationUtils.FROM_HW_LOCATION_TO_JSON_OBJECT.map(obj.getLastHWLocation()))
            .put("lastLocation", LocationUtils.FROM_LOCATION_TO_JSON_OBJECT.map(obj.getLastLocation()))
            .put("locations", PlatformUtils.mapList(obj.getLocations(), LocationUtils.FROM_LOCATION_TO_JSON_OBJECT))
            .put("hwLocationList",
                PlatformUtils.mapList(obj.getHWLocationList(), LocationUtils.FROM_HW_LOCATION_TO_JSON_OBJECT)),
        new JSONObject());

    public static final Mapper<Location, Object> FROM_LOCATION_TO_JSON_OBJECT = mapperWrapper(
        (Location obj) -> new JSONObject().put("latitude", obj.getLatitude())
            .put("longitude", obj.getLongitude())
            .put("altitude", obj.getAltitude())
            .put("speed", obj.getSpeed())
            .put("bearing", obj.getBearing())
            .put("accuracy", obj.getAccuracy())
            .put("time", obj.getTime())
            .put("fromMockProvider", obj.isFromMockProvider())
            .put("verticalAccuracyMeters", GE_OREO ? obj.getVerticalAccuracyMeters() : 0.0)
            .put("bearingAccuracyDegrees", GE_OREO ? obj.getBearingAccuracyDegrees() : 0.0)
            .put("speedAccuracyMetersPerSecond", GE_OREO ? obj.getSpeedAccuracyMetersPerSecond() : 0.0),
        new JSONObject());

    public static final Mapper<List<HWLocation>, Object> FROM_HW_LOCATION_LIST_TO_JSON_ARRAY = mapperWrapper(
        (List<HWLocation> obj) -> PlatformUtils.mapList(obj, LocationUtils.FROM_HW_LOCATION_TO_JSON_OBJECT));

    public static final Mapper<HWLocation, Object> FROM_HW_LOCATION_TO_JSON_OBJECT = mapperWrapper(
        (HWLocation obj) -> new JSONObject().put("latitude", obj.getLatitude())
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
            .put("coordinateType", obj.getCoordinateType())
            .put("verticalAccuracyMeters", GE_OREO ? obj.getVerticalAccuracyMeters() : 0.0)
            .put("bearingAccuracyDegrees", GE_OREO ? obj.getBearingAccuracyDegrees() : 0.0)
            .put("speedAccuracyMetersPerSecond", GE_OREO ? obj.getSpeedAccuracyMetersPerSecond() : 0.0),
        new JSONObject());

    public static final Mapper<LocationSettingsStates, JSONObject> FROM_LOCATION_SETTINGS_STATES_TO_JSON_OBJECT
        = mapperWrapper((LocationSettingsStates obj) -> new JSONObject().put("isBlePresent", obj.isBlePresent())
        .put("isBleUsable", obj.isBleUsable())
        .put("isGpsPresent", obj.isGpsPresent())
        .put("isGpsUsable", obj.isGpsUsable())
        .put("isGnssPresent", obj.isGnssPresent())
        .put("isGnssUsable", obj.isGnssUsable())
        .put("isLocationPresent", obj.isLocationPresent())
        .put("isLocationUsable", obj.isLocationUsable())
        .put("isNetworkLocationPresent", obj.isNetworkLocationPresent())
        .put("isNetworkLocationUsable", obj.isNetworkLocationUsable())
        .put("isHMSLocationPresent", obj.isHMSLocationPresent())
        .put("isHMSLocationUsable", obj.isHMSLocationUsable()), new JSONObject());

    public static final Mapper<LocationAvailability, Object> FROM_LOCATION_AVAILABILITY_TO_JSON_OBJECT = mapperWrapper(
        (LocationAvailability obj) -> new JSONObject().put("isLocationAvailable", obj.isLocationAvailable()));

    public static final Mapper<LocationSettingsStates, JSONObject> FROM_LOCATION_SETTINGS_RESULT_TO_JSON_OBJECT
        = mapperWrapper((LocationSettingsStates obj) -> new JSONObject().put("locationSettingsStates",
        FROM_LOCATION_SETTINGS_STATES_TO_JSON_OBJECT.map(obj)));

    public static final Mapper<LocationSettingsResponse, Object> FROM_LOCATION_SETTINGS_STATES_RESPONSE_TO_JSON_OBJECT
        = mapperWrapper((LocationSettingsResponse obj) -> new JSONObject().put("locationSettingsStates",
        FROM_LOCATION_SETTINGS_STATES_TO_JSON_OBJECT.map(obj.getLocationSettingsStates())));

    public static final Mapper<NavigationResult, Object> FROM_NAVIGATION_RESULT_TO_JSON_OBJECT = mapperWrapper(
        (NavigationResult obj) -> new JSONObject().put("state", obj.getState())
            .put("possibility", obj.getPossibility()), new JSONObject());

    public static final Mapper<JSONObject, LogConfig> FROM_JSON_OBJECT_TO_LOG_CONFIG = mapperWrapper(
        (JSONObject jo) -> new LogConfig(jo.optString("logPath"), jo.optInt("fileSize"), jo.optInt("fileNum"),
            jo.optInt("fileExpiredTime")));

    public static final Mapper<LogConfig, JSONObject> FROM_LOG_CONFIG_TO_JSON_OBJECT = mapperWrapper(
        (LogConfig obj) -> new JSONObject().put("logPath", obj.getLogPath())
            .put("fileSize", obj.getFileSize())
            .put("fileNum", obj.getFileNum())
            .put("fileExpiredTime", obj.getFileExpiredTime()));

    public static final Mapper<LonLat, JSONObject> FROM_LON_LAT_TO_JSON = mapperWrapper(
        (LonLat lonLat) -> new JSONObject()
            .put("latitude", lonLat.getLatitude())
            .put("longitude", lonLat.getLongitude()));

    public static void fillNotificationBuilder(Context context, Notification.Builder builder, ReadableMap readableMap) {
        if (readableMap.hasKey("contentTitle")) {
            builder = builder.setContentTitle(readableMap.getString("contentTitle"));
            Log.i(TAG, readableMap.getString("contentTitle"));
        }
        if (readableMap.hasKey("color")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = builder.setColor(readableMap.getInt("color"));
            }
        }
        if (readableMap.hasKey("colorized")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder = builder.setColorized(readableMap.getBoolean("colorized"));
            }
        }
        if (readableMap.hasKey("contentInfo")) {
            builder = builder.setContentInfo(readableMap.getString("contentInfo"));
        }
        if (readableMap.hasKey("contentText")) {
            builder = builder.setContentText(readableMap.getString("contentText"));
        }
        if (readableMap.hasKey("smallIcon")) {
            int resourceId = context.getResources()
                .getIdentifier(readableMap.getString("smallIcon"), "drawable", context.getPackageName());
            builder = builder.setSmallIcon(resourceId);
        } else {
            builder.setSmallIcon(context.getResources()
                .getIdentifier(DEFAULT_RESOURCE_NAME, DEFAULT_DEF_TYPE, context.getPackageName()));
        }
        if (readableMap.hasKey("largeIcon")) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(context.getAssets().open(readableMap.getString("largeIcon")));
            } catch (IOException | OutOfMemoryError e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            builder = builder.setLargeIcon(bitmap);
        }
        if (readableMap.hasKey("sound")) {
            String sourceName = readableMap.getString("sound");
            int resourceId = context.getResources().getIdentifier(sourceName, "raw", context.getPackageName());
            Uri soundUri = Uri.parse(
                String.format(Locale.ENGLISH, "android.resource://%s/%s", context.getPackageName(), resourceId));
            builder = builder.setSound(soundUri);
        }
        if (readableMap.hasKey("onGoing")) {
            builder = builder.setOngoing(readableMap.getBoolean("onGoing"));
        }
        if (readableMap.hasKey("subText")) {
            builder = builder.setSubText(readableMap.getString("subText"));
        }
        if (readableMap.hasKey("vibrate")) {
            ReadableArray patternRA = readableMap.getArray("vibrate");
            int length = patternRA.size();
            long[] pattern = new long[length];
            for (int i = 0; i < length; i++) {
                pattern[i] = (long) patternRA.getDouble(i);
            }
            builder = builder.setVibrate(pattern);
        }
        if (readableMap.hasKey("visibility")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = builder.setVisibility(readableMap.getInt("visibility"));
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setOngoing(true)
                .setPriority(readableMap.getInt("priority"))
                .setCategory(readableMap.getString("category"));
        }
    }

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
