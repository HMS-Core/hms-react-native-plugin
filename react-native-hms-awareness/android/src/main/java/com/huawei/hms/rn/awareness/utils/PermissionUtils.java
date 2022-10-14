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

package com.huawei.hms.rn.awareness.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.facebook.react.bridge.Promise;

public class PermissionUtils {

    private static final String TAG = "PermissionUtils::";
    private static String permissionError = "Permission Error";

    public static boolean hasLocationPermission(Activity activity) {
        boolean fineLoc = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION, activity);
        boolean coarseLoc = hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION, activity);
        boolean result = fineLoc || coarseLoc;

        if (!result || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            boolean accessBackgroundLocation = hasPermission("android.permission.ACCESS_BACKGROUND_LOCATION", activity);
            result = result || accessBackgroundLocation;
        }
        return result;
    }

    public static boolean hasBluetoothPermission(Activity activity) {
        return hasPermission(Manifest.permission.BLUETOOTH, activity);
    }

    public static void requestBluetoothPermission(Activity activity, Promise promise) {

        Log.d(TAG, "requestBluetoothPermission start");
        if (hasLocationPermission(activity)) {
            Log.d(TAG, "requestBluetoothPermission :: already have the permissions");
        }

        String[] permissions = {
                Manifest.permission.BLUETOOTH
        };
        requestPermissions(1, permissions, activity);
        Log.d(TAG, "requestPermissions:: apply permission");
        DataUtils.errorMessage("requestBluetoothPermission", permissionError, promise);
    }

    public static void requestLocationPermission(Activity activity, Promise promise) {

        Log.d(TAG, "requestLocationPermission start");

        if (hasLocationPermission(activity)) {
            Log.d(TAG, "requestLocationPermission -> already have the permissions");
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            String[] permissions = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
            int requestLocationP = 501;
            requestPermissions(requestLocationP, permissions, activity);
        } else {
            String[] permissions = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
            };

            int requestLocation = 502;
            requestPermissions(requestLocation, permissions, activity);
        }

        Log.d(TAG, "requestPermissions:: apply permission");
        DataUtils.errorMessage("requestLocationPermission", permissionError, promise);
    }

    public static boolean hasActivityRecognitionPermission(Activity activity) {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            return hasPermission("com.huawei.hms.permission.ACTIVITY_RECOGNITION", activity);
        } else {
            return hasPermission("android.permission.ACTIVITY_RECOGNITION", activity);
        }
    }

    public static void requestActivityRecognitionPermission(Activity activity, Promise promise) {
        Log.d(TAG, "requestActivityRecognitionPermission start");

        if (hasActivityRecognitionPermission(activity)) {
            Log.d(TAG, "requestActivityRecognitionPermission:: already have the permissions");
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            String permissions = "com.huawei.hms.permission.ACTIVITY_RECOGNITION";
            int requestActivityP = 503;
            requestPermission(requestActivityP, permissions, activity);
        } else {
            String permissions = "android.permission.ACTIVITY_RECOGNITION";
            int requestActivity = 504;
            requestPermission(requestActivity, permissions, activity);
        }
        Log.d(TAG, "requestActivityRecognitionPermission -> apply permission");
        DataUtils.errorMessage("requestActivityRecognitionPermission", permissionError, promise);
    }

    private static boolean hasPermission(String permission, Activity activity) {
        if (activity != null) {
            int result = activity.checkSelfPermission(permission);
            return PackageManager.PERMISSION_GRANTED == result;
        } else {
            Log.e(TAG, "hasPermission::  Activity is null!");
            return false;
        }
    }

    public static void requestPermission(int requestNo, String permission, Activity activity) {
        if (activity != null) {
            String[] permissions = new String[1];
            permissions[0] = permission;
            requestPermissions(requestNo, permissions, activity);
        } else {
            Log.e(TAG, "requestPermission::  Activity is null!");
        }
    }

    public static void requestPermissions(int requestNo, String[] permissions, Activity activity) {
        if (activity != null) {
            ActivityCompat.requestPermissions(activity, permissions, requestNo);
        } else {
            Log.e(TAG, "requestPermission::  Activity is null!");
        }
    }
}
