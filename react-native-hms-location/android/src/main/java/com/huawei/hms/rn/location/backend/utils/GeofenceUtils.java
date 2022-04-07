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

import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.mapperWrapper;
import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.triMapperWrapper;

import com.huawei.hms.location.Geofence;
import com.huawei.hms.location.GeofenceData;
import com.huawei.hms.location.GeofenceErrorCodes;
import com.huawei.hms.location.GeofenceRequest;
import com.huawei.hms.rn.location.backend.interfaces.Mapper;
import com.huawei.hms.rn.location.backend.interfaces.TriMapper;

import org.json.JSONArray;
import org.json.JSONObject;

public class GeofenceUtils {
    public static final Mapper<JSONObject, Geofence> FROM_JSON_OBJECT_TO_GEOFENCE = mapperWrapper(
        (JSONObject jo) -> new Geofence.Builder().setRoundArea(jo.getDouble("latitude"), jo.getDouble("longitude"),
            (float) jo.getDouble("radius"))
            .setUniqueId(jo.getString("uniqueId"))
            .setConversions(jo.getInt("conversions"))
            .setValidContinueTime((long) jo.optDouble("validContinueTime", -1))
            .setDwellDelayTime(jo.optInt("dwellDelayTime", -1))
            .setNotificationInterval(jo.optInt("notificationInterval", 0))
            .build());

    public static final Mapper<Geofence, Object> FROM_GEOFENCE_TO_JSON_OBJECT = mapperWrapper(
        (Geofence obj) -> new JSONObject().put("uniqueId", obj.getUniqueId()), new JSONObject());

    public static final TriMapper<JSONArray, Integer, Integer, GeofenceRequest> FROM_JSON_ARRAY_TO_GEOFENCE
        = triMapperWrapper(
        (arrayGeofences, initConversions, coordinateType) -> new GeofenceRequest.Builder().createGeofenceList(
            PlatformUtils.mapJSONArray(arrayGeofences, GeofenceUtils.FROM_JSON_OBJECT_TO_GEOFENCE))
            .setInitConversions(initConversions)
            .setCoordinateType(coordinateType)
            .build());

    public static final Mapper<GeofenceData, JSONObject> FROM_GEOFENCE_DATA_TO_JSON_OBJECT = mapperWrapper(
        (GeofenceData obj) -> new JSONObject().put("convertingGeofenceList",
            PlatformUtils.mapList(obj.getConvertingGeofenceList(), GeofenceUtils.FROM_GEOFENCE_TO_JSON_OBJECT))
            .put("conversion", obj.getConversion())
            .put("convertingLocation", LocationUtils.FROM_LOCATION_TO_JSON_OBJECT.map(obj.getConvertingLocation()))
            .put("errorCode", obj.getErrorCode())
            .put("errorMessage", GeofenceErrorCodes.getErrorMessage(obj.getErrorCode())), new JSONObject());
}
