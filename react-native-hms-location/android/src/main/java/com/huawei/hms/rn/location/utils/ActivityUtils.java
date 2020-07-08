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

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.location.ActivityConversionData;
import com.huawei.hms.location.ActivityConversionInfo;
import com.huawei.hms.location.ActivityConversionRequest;
import com.huawei.hms.location.ActivityConversionResponse;
import com.huawei.hms.location.ActivityIdentificationData;
import com.huawei.hms.location.ActivityIdentificationResponse;
import com.huawei.hms.rn.location.helpers.Mapper;

import java.util.List;


public class ActivityUtils {

    private static final String TAG = ActivityUtils.class.getSimpleName();

    public static Mapper<ReadableMap, ActivityConversionInfo> fromReadableMapToActivityConversionInfo = new Mapper<ReadableMap, ActivityConversionInfo>() {
        @Override
        public ActivityConversionInfo map(ReadableMap readableMap) {
            Log.i(TAG, "fromReadableMapToActivityConversionInfo, readableMap = " + readableMap.toString());
            ActivityConversionInfo.Builder builder = new ActivityConversionInfo.Builder();
            ActivityConversionInfo info = builder
                .setConversionType(readableMap.getInt("conversionType"))
                .setActivityType(readableMap.getInt("activityType"))
                .build();

            Log.i(TAG, "fromReadableMapToActivityConversionInfo, activityConversionInfo = " + info.toString());
            return info;
        }
    };

    public static Mapper<ActivityIdentificationData, WritableMap> fromActivityIdentificationDataToWritableMap = new Mapper<ActivityIdentificationData, WritableMap>() {
        @Override
        public WritableMap map(ActivityIdentificationData data) {
            WritableMap result = Arguments.createMap();
            result.putInt("possibility", data.getPossibility());
            result.putInt("identificationActivity", data.getIdentificationActivity());
            return result;
        }
    };

    private static Mapper<ActivityConversionData, WritableMap> fromActivityConversionDataToWritableMap = new Mapper<ActivityConversionData, WritableMap>() {
        @Override
        public WritableMap map(ActivityConversionData data) {
            WritableMap result = Arguments.createMap();
            result.putInt("activityType", data.getActivityType());
            result.putDouble("elapsedTimeFromReboot", data.getElapsedTimeFromReboot());
            result.putInt("conversionType", data.getConversionType());
            return result;
        }
    };

    public static ActivityConversionRequest fromReadableArrayToActivityConversionRequest(ReadableArray readableArray) {
        Log.i(TAG, "fromReadableMapToActivityConversionRequest, readableArray = " + readableArray.toString());
        List<ActivityConversionInfo> activityConversions =
                ReactUtils.mapReadableArray(readableArray, fromReadableMapToActivityConversionInfo);
        // TODO: activityConversions.setDataToIntent?
        return new ActivityConversionRequest(activityConversions);
    }

    public static WritableMap fromActivityIdentificationResponseToWritableMap(ActivityIdentificationResponse response) {
        WritableMap result = Arguments.createMap();
        result.putDouble("elapsedTimeFromReboot", (double)response.getElapsedTimeFromReboot());
        result.putMap("mostActivityIdentification", fromActivityIdentificationDataToWritableMap.map(response.getMostActivityIdentification()));
        result.putArray("activityIdentificationDatas", ReactUtils.mapList(response.getActivityIdentificationDatas(), fromActivityIdentificationDataToWritableMap));
        result.putDouble("time", (double)response.getTime());
        return result;
    }

    public static WritableMap fromActivityConversionResponseToWritableMap(ActivityConversionResponse response) {
        WritableMap result = Arguments.createMap();
        result.putArray("activityConversionDatas", ReactUtils.mapList(response.getActivityConversionDatas(), fromActivityConversionDataToWritableMap));
        return result;
    }

}
