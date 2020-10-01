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

import android.Manifest;
import android.os.Build;
import android.util.Log;

import com.huawei.hms.rn.location.backend.interfaces.HMSProvider;
import com.huawei.hms.rn.location.backend.interfaces.TriMapper;

import org.json.JSONObject;

import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.triMapperWrapper;

public class PermissionUtils {
    private static final String TAG = PermissionUtils.class.getSimpleName();
    private static int REQUEST_LOCATION_P = 501;
    private static int REQUEST_LOCATION = 502;
    private static int REQUEST_ACTIVITY_P = 503;
    private static int REQUEST_ACTIVITY = 504;

    public static boolean hasLocationPermission(HMSProvider provider) {
        boolean fineLoc = provider.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        boolean coarseLoc = provider.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

        boolean result = fineLoc || coarseLoc;

        if (!result || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            boolean accessBackgroundLocation = provider.hasPermission("android.permission.ACCESS_BACKGROUND_LOCATION");
            result = result || accessBackgroundLocation;
        }
        return result;
    }

    public static void requestLocationPermission(HMSProvider provider) {
        Log.d(TAG, "requestLocationPermission start");

        if (hasLocationPermission(provider)) {
            Log.d(TAG, "requestLocationPermission -> already have the permissions");
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            String[] permissions = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
            provider.requestPermissions(REQUEST_LOCATION_P, permissions);
        } else {
            String[] permissions = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
            };

            provider.requestPermissions(REQUEST_LOCATION, permissions);
        }
        Log.d(TAG, "requestPermissions -> apply permission");
    }

    public static boolean hasActivityRecognitionPermission(HMSProvider provider) {
        return
                (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P && provider.hasPermission("com.huawei.hms.permission" +
                        ".ACTIVITY_RECOGNITION"))
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && provider.hasPermission("android" +
                        ".permission.ACTIVITY_RECOGNITION"));
    }

    public static void requestActivityRecognitionPermission(HMSProvider provider) {
        Log.d(TAG, "requestActivityRecognitionPermission start");

        if (hasActivityRecognitionPermission(provider)) {
            Log.d(TAG, "requestActivityRecognitionPermission -> already have the permissions");
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            String permissions = "com.huawei.hms.permission.ACTIVITY_RECOGNITION";
            provider.requestPermission(REQUEST_ACTIVITY_P, permissions);
        } else {
            String permissions = "android.permission.ACTIVITY_RECOGNITION";
            provider.requestPermission(REQUEST_ACTIVITY, permissions);
        }
        Log.d(TAG, "requestActivityRecognitionPermission -> apply permission");
    }

    public static final TriMapper<Integer, String[], int[], JSONObject> HANDLE_PERMISSION_REQUEST_RESULT =
            triMapperWrapper((requestCode, permissions, grantResults) -> {
        JSONObject json = new JSONObject();
        if (requestCode.equals(REQUEST_LOCATION) || requestCode.equals(REQUEST_LOCATION_P)) {
            json.put("granted", grantResults[0] == 0);
            json.put("fineLocation", grantResults[0] == 0);
            json.put("coarseLocation", grantResults[1] == 0);

            if (requestCode.equals(REQUEST_LOCATION)) {
                json.put("backgroundLocation", grantResults[2] == 0);
            }

        } else if (requestCode.equals(REQUEST_ACTIVITY) || requestCode.equals(REQUEST_ACTIVITY_P)) {
            json.put("granted", grantResults[0] == 0);
            json.put("activityRecognition", grantResults[0] == 0);
        }

        return json;
    }, new JSONObject());
}
