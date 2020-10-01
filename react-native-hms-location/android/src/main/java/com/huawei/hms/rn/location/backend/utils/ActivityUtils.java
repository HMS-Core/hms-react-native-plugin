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

import com.huawei.hms.location.ActivityConversionData;
import com.huawei.hms.location.ActivityConversionInfo;
import com.huawei.hms.location.ActivityConversionRequest;
import com.huawei.hms.location.ActivityConversionResponse;
import com.huawei.hms.location.ActivityIdentificationData;
import com.huawei.hms.location.ActivityIdentificationResponse;
import com.huawei.hms.rn.location.backend.interfaces.Mapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.mapperWrapper;

public class ActivityUtils {
    private static final String TAG = ActivityUtils.class.getSimpleName();

    public static final Mapper<JSONObject, ActivityConversionInfo> FROM_JSON_OBJECT_TO_ACTIVITY_CONVERSION_INFO =
            mapperWrapper((JSONObject readableMap) -> {
        Log.i(TAG, "FROM_JSON_OBJECT_TO_ACTIVITY_CONVERSION_INFO, readableMap = " + readableMap.toString());
        ActivityConversionInfo.Builder builder = new ActivityConversionInfo.Builder();
        ActivityConversionInfo info = builder
                .setConversionType(readableMap.getInt("conversionType"))
                .setActivityType(readableMap.getInt("activityType"))
                .build();

        Log.i(TAG, "FROM_JSON_OBJECT_TO_ACTIVITY_CONVERSION_INFO, activityConversionInfo = " + info.toString());
        return info;
    });

    public static final Mapper<ActivityIdentificationData, JSONObject> FROM_ACTIVITY_IDENTIFICATION_DATA_TO_JSON_OBJECT = mapperWrapper((ActivityIdentificationData data) -> {
        JSONObject result = new JSONObject();
        result.put("possibility", data.getPossibility());
        result.put("identificationActivity", data.getIdentificationActivity());
        return result;
    }, new JSONObject());

    private static final Mapper<ActivityConversionData, JSONObject> FROM_ACTIVITY_CONVERSION_DATA_TO_JSON_OBJECT =
            mapperWrapper((ActivityConversionData data) -> {
        JSONObject result = new JSONObject();
        result.put("activityType", data.getActivityType());
        result.put("elapsedTimeFromReboot", data.getElapsedTimeFromReboot());
        result.put("conversionType", data.getConversionType());
        return result;
    }, new JSONObject());

    public static final Mapper<JSONArray, ActivityConversionRequest> FROM_JSON_ARRAY_TO_ACTIVITY_CONVERSION_REQUEST =
            mapperWrapper((JSONArray readableArray) -> {
        Log.i(TAG, "fromJSONObjectToActivityConversionRequest, readableArray = " + readableArray.toString());
        List<ActivityConversionInfo> activityConversions = PlatformUtils.mapJSONArray(readableArray,
                ActivityUtils.FROM_JSON_OBJECT_TO_ACTIVITY_CONVERSION_INFO);
        return new ActivityConversionRequest(activityConversions);
    });

    public static final Mapper<ActivityIdentificationResponse, JSONObject> FROM_ACTIVITY_IDENTIFICATION_RESPONSE_TO_JSON_OBJECT = mapperWrapper((ActivityIdentificationResponse response) -> {
        JSONObject result = new JSONObject();
        result.put("elapsedTimeFromReboot", (double) response.getElapsedTimeFromReboot());
        result.put("mostActivityIdentification",
                FROM_ACTIVITY_IDENTIFICATION_DATA_TO_JSON_OBJECT.map(response.getMostActivityIdentification()));
        result.put("activityIdentificationDatas", PlatformUtils.mapList(response.getActivityIdentificationDatas(),
                ActivityUtils.FROM_ACTIVITY_IDENTIFICATION_DATA_TO_JSON_OBJECT));
        result.put("time", (double) response.getTime());
        return result;
    }, new JSONObject());

    public static final Mapper<ActivityConversionResponse, JSONObject> FROM_ACTIVITY_CONVERSION_RESPONSE_TO_JSON_OBJECT = mapperWrapper((ActivityConversionResponse response) -> {
        JSONObject result = new JSONObject();
        result.put("activityConversionDatas", PlatformUtils.mapList(response.getActivityConversionDatas(),
                ActivityUtils.FROM_ACTIVITY_CONVERSION_DATA_TO_JSON_OBJECT));
        return result;
    }, new JSONObject());
}
