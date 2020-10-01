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

import android.util.Log;

import com.huawei.hms.location.Geofence;
import com.huawei.hms.location.GeofenceData;
import com.huawei.hms.location.GeofenceErrorCodes;
import com.huawei.hms.location.GeofenceRequest;
import com.huawei.hms.rn.location.backend.interfaces.Mapper;
import com.huawei.hms.rn.location.backend.interfaces.TriMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.mapperWrapper;
import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.triMapperWrapper;

public class GeofenceUtils {
    private static final String TAG = GeofenceUtils.class.getSimpleName();

    public static final Mapper<JSONObject, Geofence> FROM_JSON_OBJECT_TO_GEOFENCE =
            mapperWrapper((JSONObject readableMap) -> {
        Log.i(TAG, "FROM_JSON_OBJECT_TO_GEOFENCE: " + readableMap.toString());
        Geofence.Builder builder = new Geofence.Builder();
        builder
                .setRoundArea(readableMap.getDouble("latitude"),
                        readableMap.getDouble("longitude"),
                        (float) readableMap.getDouble("radius"))
                .setUniqueId(readableMap.getString("uniqueId"))
                .setConversions(readableMap.getInt("conversions"))
                .setValidContinueTime((long) readableMap.getDouble("validContinueTime"))
                .setDwellDelayTime(readableMap.getInt("dwellDelayTime"))
                .setNotificationInterval(readableMap.getInt("notificationInterval"));

        return builder.build();
    });

    public static final Mapper<Geofence, JSONObject> FROM_GEOFENCE_TO_JSON_OBJECT =
            mapperWrapper((Geofence geofence) -> {
        JSONObject result = new JSONObject();
        result.put("uniqueId", geofence.getUniqueId());
        return result;
    }, new JSONObject());

    public static final TriMapper<JSONArray, Integer, Integer, GeofenceRequest> FROM_JSON_ARRAY_TO_GEOFENCE =
            triMapperWrapper((arrayGeofences, initConversions, coordinateType) -> {
        Log.i(TAG, "buildGeofenceRequest start");
        List<Geofence> geofences = PlatformUtils.mapJSONArray(arrayGeofences,
                GeofenceUtils.FROM_JSON_OBJECT_TO_GEOFENCE);

        GeofenceRequest.Builder request = new GeofenceRequest.Builder();
        request.createGeofenceList(geofences)
                .setInitConversions(initConversions)
                .setCoordinateType(coordinateType);

        Log.i(TAG, "buildGeofenceRequest end");
        return request.build();
    });

    public static final Mapper<GeofenceData, JSONObject> FROM_GEOFENCE_DATA_TO_JSON_OBJECT =
            mapperWrapper((GeofenceData geofenceData) -> {
        JSONObject result = new JSONObject();

        // Add convertingGeofenceList
        result.put("convertingGeofenceList", PlatformUtils.mapList(geofenceData.getConvertingGeofenceList(),
                GeofenceUtils.FROM_GEOFENCE_TO_JSON_OBJECT));

        result.put("conversion", geofenceData.getConversion());
        result.put("convertingLocation",
                LocationUtils.FROM_LOCATION_TO_JSON_OBJECT.map(geofenceData.getConvertingLocation()));
        result.put("errorCode", geofenceData.getErrorCode());
        result.put("errorMessage", GeofenceErrorCodes.getErrorMessage(geofenceData.getErrorCode()));

        return result;
    }, new JSONObject());
}
