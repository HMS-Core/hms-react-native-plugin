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

import com.huawei.hms.location.ActivityConversionData;
import com.huawei.hms.location.ActivityConversionInfo;
import com.huawei.hms.location.ActivityConversionRequest;
import com.huawei.hms.location.ActivityConversionResponse;
import com.huawei.hms.location.ActivityIdentificationData;
import com.huawei.hms.location.ActivityIdentificationResponse;
import com.huawei.hms.rn.location.backend.interfaces.Mapper;

import org.json.JSONArray;
import org.json.JSONObject;

public class ActivityUtils {
    public static final Mapper<JSONObject, ActivityConversionInfo> FROM_JSON_OBJECT_TO_ACTIVITY_CONVERSION_INFO
        = mapperWrapper(
        (JSONObject jo) -> new ActivityConversionInfo.Builder().setConversionType(jo.getInt("conversionType"))
            .setActivityType(jo.getInt("activityType"))
            .build());

    public static final Mapper<ActivityIdentificationData, Object> FROM_ACTIVITY_IDENTIFICATION_DATA_TO_JSON_OBJECT
        = mapperWrapper((ActivityIdentificationData obj) -> new JSONObject().put("possibility", obj.getPossibility())
        .put("identificationActivity", obj.getIdentificationActivity()), new JSONObject());

    private static final Mapper<ActivityConversionData, Object> FROM_ACTIVITY_CONVERSION_DATA_TO_JSON_OBJECT
        = mapperWrapper((ActivityConversionData obj) -> new JSONObject().put("activityType", obj.getActivityType())
        .put("elapsedTimeFromReboot", obj.getElapsedTimeFromReboot())
        .put("conversionType", obj.getConversionType()), new JSONObject());

    public static final Mapper<JSONArray, ActivityConversionRequest> FROM_JSON_ARRAY_TO_ACTIVITY_CONVERSION_REQUEST
        = mapperWrapper((JSONArray ja) -> new ActivityConversionRequest(
        PlatformUtils.mapJSONArray(ja, ActivityUtils.FROM_JSON_OBJECT_TO_ACTIVITY_CONVERSION_INFO)));

    public static final Mapper<ActivityIdentificationResponse, JSONObject>
        FROM_ACTIVITY_IDENTIFICATION_RESPONSE_TO_JSON_OBJECT = mapperWrapper(
        (ActivityIdentificationResponse response) -> new JSONObject().put("elapsedTimeFromReboot",
            (double) response.getElapsedTimeFromReboot())
            .put("mostActivityIdentification",
                FROM_ACTIVITY_IDENTIFICATION_DATA_TO_JSON_OBJECT.map(response.getMostActivityIdentification()))
            .put("activityIdentificationDatas", PlatformUtils.mapList(response.getActivityIdentificationDatas(),
                ActivityUtils.FROM_ACTIVITY_IDENTIFICATION_DATA_TO_JSON_OBJECT))
            .put("time", (double) response.getTime()), new JSONObject());

    public static final Mapper<ActivityConversionResponse, JSONObject> FROM_ACTIVITY_CONVERSION_RESPONSE_TO_JSON_OBJECT
        = mapperWrapper((ActivityConversionResponse response) -> new JSONObject().put("activityConversionDatas",
        PlatformUtils.mapList(response.getActivityConversionDatas(),
            ActivityUtils.FROM_ACTIVITY_CONVERSION_DATA_TO_JSON_OBJECT)), new JSONObject());
}
