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


import android.Manifest;
import android.os.Build;
import android.util.Log;
import android.app.Activity;
import android.content.pm.PackageManager;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;

import androidx.core.app.ActivityCompat;


public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getSimpleName();

    public static boolean hasLocationPermission(Activity activity) {
        int fineLoc = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLoc = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);

        boolean result = fineLoc == PackageManager.PERMISSION_GRANTED || coarseLoc == PackageManager.PERMISSION_GRANTED;
        if (!result || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            int accessBackgroundLocation = ActivityCompat
                .checkSelfPermission(activity, "android.permission.ACCESS_BACKGROUND_LOCATION");
            result = result || accessBackgroundLocation == PackageManager.PERMISSION_GRANTED;
        }
        return result;
    }

    public static void requestLocationPermission(Activity activity) {
        Log.d(TAG, "requestLocationPermission start");

        if (hasLocationPermission(activity)) {
            Log.d(TAG, "requestLocationPermission -> already have the permissions");
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            };
            ActivityCompat.requestPermissions(activity, permissions, 1);
        } else {
            String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                "android.permission.ACCESS_BACKGROUND_LOCATION"
            };
            ActivityCompat.requestPermissions(activity, permissions, 2);
        }
        Log.d(TAG, "requestPermissions -> apply permission");
    }

    public static boolean hasActivityRecognitionPermission(Activity activity) {
        return
            (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P
             && ActivityCompat.checkSelfPermission(activity, "com.huawei.hms.permission.ACTIVITY_RECOGNITION")
                == PackageManager.PERMISSION_GRANTED)
            || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
                && ActivityCompat.checkSelfPermission(activity, "android.permission.ACTIVITY_RECOGNITION")
                   == PackageManager.PERMISSION_GRANTED);
    }

    public static void requestActivityRecognitionPermission(Activity activity) {
        Log.d(TAG, "requestActivityRecognitionPermission start");

        if (hasActivityRecognitionPermission(activity)) {
            Log.d(TAG, "requestActivityRecognitionPermission -> already have the permissions");
            return;
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            String permissions[] = {"com.huawei.hms.permission.ACTIVITY_RECOGNITION"};
            ActivityCompat.requestPermissions(activity, permissions, 1);
        } else {
            String permissions[] = {"android.permission.ACTIVITY_RECOGNITION"};
            ActivityCompat.requestPermissions(activity, permissions, 2);
        }
        Log.d(TAG, "requestActivityRecognitionPermission -> apply permission");
    }

}
