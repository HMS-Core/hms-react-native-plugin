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

package com.huawei.hms.rn.location;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.location.ActivityConversionInfo;
import com.huawei.hms.location.ActivityConversionRequest;
import com.huawei.hms.location.ActivityConversionResponse;
import com.huawei.hms.location.ActivityIdentification;
import com.huawei.hms.location.ActivityIdentificationData;
import com.huawei.hms.location.ActivityIdentificationResponse;
import com.huawei.hms.location.ActivityIdentificationService;

import com.huawei.hms.rn.location.helpers.Exceptions;
import com.huawei.hms.rn.location.helpers.Pair;
import com.huawei.hms.rn.location.helpers.Constants.Event;
import com.huawei.hms.rn.location.helpers.HMSBroadcastReceiver;
import com.huawei.hms.rn.location.utils.ActivityUtils;
import com.huawei.hms.rn.location.utils.ReactUtils;
import com.huawei.hms.rn.location.utils.PermissionUtils;

import java.util.HashMap;
import java.util.Map;


public class RNActivityIdentificationModule extends ReactContextBaseJavaModule {

    private final static String TAG = RNActivityIdentificationModule.class.getSimpleName();

    private final ReactApplicationContext reactContext;
    private final ActivityIdentificationService activityService;

    private final Map<Integer, PendingIntent> requests;
    private int mRequestCode = 0;


    public RNActivityIdentificationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.activityService = ActivityIdentification.getService(reactContext);
        this.requests = new HashMap<>();
    }

    @Override
    public String getName() {
        return "HMSActivityIdentification";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> activityConstants = new HashMap<>();
        activityConstants.put("VEHICLE", ActivityIdentificationData.VEHICLE);
        activityConstants.put("BIKE", ActivityIdentificationData.BIKE);
        activityConstants.put("FOOT", ActivityIdentificationData.FOOT);
        activityConstants.put("RUNNING", ActivityIdentificationData.RUNNING);
        activityConstants.put("STILL", ActivityIdentificationData.STILL);
        activityConstants.put("TILTING", ActivityIdentificationData.TILTING);
        activityConstants.put("OTHERS", ActivityIdentificationData.OTHERS);
        activityConstants.put("WALKING", ActivityIdentificationData.WALKING);

        final Map<String, Object> eventConstants = new HashMap<>();
        eventConstants.put("ACTIVITY_CONVERSION_RESULT", Event.ACTIVITY_CONVERSION_RESULT.getVal());
        eventConstants.put("ACTIVITY_IDENTIFICATION_RESULT", Event.ACTIVITY_IDENTIFICATION_RESULT.getVal());

        final Map<String, Object> activityConversionConstants = new HashMap<>();
        activityConversionConstants.put("ENTER_ACTIVITY_CONVERSION", ActivityConversionInfo.ENTER_ACTIVITY_CONVERSION);
        activityConversionConstants.put("EXIT_ACTIVITY_CONVERSION", ActivityConversionInfo.EXIT_ACTIVITY_CONVERSION);

        final Map<String, Object> constants = new HashMap<>();
        constants.put("Activities", activityConstants);
        constants.put("Events", eventConstants);
        constants.put("ActivityConversions", activityConversionConstants);

        return constants;
    }

    @ReactMethod
    public void createActivityConversionUpdates(ReadableArray activityConversionRequestArray, final Promise promise) {
        Log.i(TAG, "createActivityConversionUpdates start");
        ActivityConversionRequest request =
            ActivityUtils.fromReadableArrayToActivityConversionRequest(activityConversionRequestArray);
        final Pair<Integer, PendingIntent> intentData = buildPendingIntent(HMSBroadcastReceiver.ACTION_PROCESS_CONVERSION);
        activityService.createActivityConversionUpdates(request, intentData.get1())
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "createActivityConversionUpdates onSuccess");
                        promise.resolve(ReactUtils.intMap("requestCode", intentData.get0()));
                    }
                })
            .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "createActivityConversionUpdates onFailure: " + e.getMessage());
                        promise.reject(e);
                    }
                });

        Log.i(TAG, "createActivityConversionUpdates end");
    }

    @ReactMethod
    public void createActivityIdentificationUpdates(double intervalMillis, final Promise promise) {
        Log.i(TAG, "createActivityIdentificationUpdates start");
        final Pair<Integer, PendingIntent> intentData = buildPendingIntent(HMSBroadcastReceiver.ACTION_PROCESS_IDENTIFICATION);
        activityService.createActivityIdentificationUpdates((long)intervalMillis, intentData.get1())
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "createActivityIdentificationUpdates onSuccess");
                        promise.resolve(ReactUtils.intMap("requestCode", intentData.get0()));
                    }
                })
            .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "createActivityIdentificationUpdates onFailure: " + e.getMessage());
                        promise.reject(e);
                    }
                });

        Log.i(TAG, "createActivityIdentificationUpdates end");
    }

    @ReactMethod
    public void deleteActivityConversionUpdates(final int requestCode, final Promise promise) {
        Log.i(TAG, "deleteActivityConversionUpdates start");
        if (!requests.containsKey(requestCode)) {
            promise.reject(new Exceptions.NonExistentRequestID());
        }

        activityService.deleteActivityConversionUpdates(requests.get(requestCode))
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "deleteActivityConversionUpdates onSuccess");
                        requests.remove(requestCode);
                        promise.resolve(true);
                    }
                })
            .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "deleteActivityConversionUpdates onFailure: " + e.getMessage());
                        promise.reject(e);
                    }
                });

        Log.i(TAG, "deleteActivityConversionUpdates end");
    }

    @ReactMethod
    public void deleteActivityIdentificationUpdates(final int requestCode, final Promise promise) {
        Log.i(TAG, "deleteActivityIdentificationUpdates start");
        if (!requests.containsKey(requestCode)) {
            promise.reject(new Exceptions.NonExistentRequestID());
        }

        activityService.deleteActivityIdentificationUpdates(requests.get(requestCode))
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "deleteActivityIdentificationUpdates onSuccess");
                        requests.remove(requestCode);
                        promise.resolve(true);
                    }
                })
            .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "deleteActivityIdentificationUpdates onFailure: " + e.getMessage());
                        promise.reject(e);
                    }
                });

        Log.i(TAG, "deleteActivityIdentificationUpdates end");
    }

    @ReactMethod
    public void requestPermission() {
        PermissionUtils.requestActivityRecognitionPermission(getCurrentActivity());
    }

    @ReactMethod
    public void hasPermission(final Promise promise) {
        promise.resolve(PermissionUtils.hasActivityRecognitionPermission(getCurrentActivity()));
    }

    private Pair<Integer, PendingIntent> buildPendingIntent(String action) {
        Log.d(TAG, "buildPendingIntent start");
        Intent intent = new Intent();
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(reactContext, mRequestCode++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        this.requests.put(mRequestCode, pendingIntent);
        return Pair.create(mRequestCode, pendingIntent);
    }

}
