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
import com.huawei.hms.location.Geofence;
import com.huawei.hms.location.GeofenceData;
import com.huawei.hms.location.GeofenceErrorCodes;
import com.huawei.hms.location.GeofenceRequest;
import com.huawei.hms.rn.location.helpers.Mapper;

import java.util.List;


public class GeofenceUtils {

    private static final String TAG = GeofenceUtils.class.getSimpleName();

    public static Mapper<ReadableMap, Geofence> fromReadableMapToGeofence = new Mapper<ReadableMap, Geofence>() {
        @Override
        public Geofence map(ReadableMap readableMap) {
            Log.i(TAG, "fromWritableMapToGeofence: " + readableMap.toString());
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
        }
    };

    public static Mapper<Geofence, WritableMap> fromGeofenceToWritableMap = new Mapper<Geofence, WritableMap>() {
            @Override
            public WritableMap map(Geofence geofence) {
                WritableMap result = Arguments.createMap();
                result.putString("uniqueId", geofence.getUniqueId());
                return result;
            }
        };

    public static GeofenceRequest fromReadableArrayToGeofences(final ReadableArray arrayGeofences, int initConversions, int coordinateType) {
        Log.i(TAG, "buildGeofenceRequest start");
        List<Geofence> geofences = ReactUtils.mapReadableArray(arrayGeofences, fromReadableMapToGeofence);

        GeofenceRequest.Builder request = new GeofenceRequest.Builder();
        request.createGeofenceList(geofences)
                .setInitConversions(initConversions)
                .setCoordinateType(coordinateType);

        Log.i(TAG, "buildGeofenceRequest end");
        return request.build();
    }

    public static WritableMap fromGeofenceDataToWritableMap(GeofenceData geofenceData) {
        WritableMap result = Arguments.createMap();

        // Add convertingGeofenceList
        result.putArray("convertingGeofenceList", ReactUtils.mapList(geofenceData.getConvertingGeofenceList(), fromGeofenceToWritableMap));

        result.putInt("conversion", geofenceData.getConversion());
        result.putMap("convertingLocation", LocationUtils.fromLocationToWritableMap(geofenceData.getConvertingLocation()));
        result.putInt("errorCode", geofenceData.getErrorCode());
        result.putString("errorMessage", GeofenceErrorCodes.getErrorMessage(geofenceData.getErrorCode()));

        return result;
    }

}
