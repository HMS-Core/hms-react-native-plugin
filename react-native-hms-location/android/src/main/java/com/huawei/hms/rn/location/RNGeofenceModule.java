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

import java.util.HashMap;
import java.util.Map;

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

import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.location.Geofence;
import com.huawei.hms.location.GeofenceData;
import com.huawei.hms.location.GeofenceErrorCodes;
import com.huawei.hms.location.GeofenceRequest;
import com.huawei.hms.location.GeofenceService;

import com.huawei.hms.location.LocationServices;
import com.huawei.hms.rn.location.utils.GeofenceUtils;
import com.huawei.hms.rn.location.utils.ReactUtils;
import com.huawei.hms.rn.location.helpers.Constants.Event;
import com.huawei.hms.rn.location.helpers.Exceptions;
import com.huawei.hms.rn.location.helpers.Pair;
import com.huawei.hms.rn.location.helpers.HMSBroadcastReceiver;


public class RNGeofenceModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    public GeofenceService geofenceService;
    private final static String TAG = RNGeofenceModule.class.getSimpleName();

    private int mRequestCode = 0;
    private Map<Integer, PendingIntent> requests;

    public RNGeofenceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.geofenceService = LocationServices.getGeofenceService(reactContext);
        this.requests = new HashMap<>();
    }

    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> geofenceRequestConstants = new HashMap<>();
        geofenceRequestConstants.put("ENTER_INIT_CONVERSION", GeofenceRequest.ENTER_INIT_CONVERSION);
        geofenceRequestConstants.put("EXIT_INIT_CONVERSION", GeofenceRequest.EXIT_INIT_CONVERSION);
        geofenceRequestConstants.put("DWELL_INIT_CONVERSION", GeofenceRequest.DWELL_INIT_CONVERSION);
        geofenceRequestConstants.put("COORDINATE_TYPE_WGS_84", GeofenceRequest.COORDINATE_TYPE_WGS_84);
        geofenceRequestConstants.put("COORDINATE_TYPE_GCJ_02", GeofenceRequest.COORDINATE_TYPE_GCJ_02);

        Map<String, Object> geofenceConstants = new HashMap<>();
        geofenceConstants.put("ENTER_GEOFENCE_CONVERSION",  Geofence.ENTER_GEOFENCE_CONVERSION);
        geofenceConstants.put("EXIT_GEOFENCE_CONVERSION",   Geofence.EXIT_GEOFENCE_CONVERSION);
        geofenceConstants.put("DWELL_GEOFENCE_CONVERSION",  Geofence.DWELL_GEOFENCE_CONVERSION);
        geofenceConstants.put("GEOFENCE_NEVER_EXPIRE", Geofence.GEOFENCE_NEVER_EXPIRE);

        Map<String, Object> errorConstants = new HashMap<>();
        errorConstants.put("GEOFENCE_UNAVAILABLE", GeofenceErrorCodes.GEOFENCE_UNAVAILABLE);
        errorConstants.put("GEOFENCE_NUMBER_OVER_LIMIT", GeofenceErrorCodes.GEOFENCE_NUMBER_OVER_LIMIT);
        errorConstants.put("GEOFENCE_PENDINGINTENT_OVER_LIMIT", GeofenceErrorCodes.GEOFENCE_PENDINGINTENT_OVER_LIMIT);
        errorConstants.put("GEOFENCE_INSUFFICIENT_PERMISSION", GeofenceErrorCodes.GEOFENCE_INSUFFICIENT_PERMISSION);
        errorConstants.put("GEOFENCE_REQUEST_TOO_OFTEN", GeofenceErrorCodes.GEOFENCE_REQUEST_TOO_OFTEN);

        Map<String, Object> eventConstants = new HashMap<>();
        eventConstants.put("GEOFENCE_RESULT", Event.GEOFENCE_RESULT.getVal());

        Map<String, Object> constants = new HashMap<>();
        constants.put("GeofenceRequestConstants", geofenceRequestConstants);
        constants.put("GeofenceConstants", geofenceConstants);
        constants.put("ErrorCodes", errorConstants);
        constants.put("Events", eventConstants);
        return constants;
    }

    @Override
    public String getName() {
        return "HMSGeofence";
    }

    @ReactMethod
    public void createGeofenceList(final ReadableArray geofences, final int initConversions, final int coordinateType, final Promise promise) {
        Log.i(TAG, "createGeofences start");

        final Pair<Integer, PendingIntent> intentData = buildPendingIntent();
        GeofenceRequest geofenceRequest = GeofenceUtils.fromReadableArrayToGeofences(geofences, initConversions, coordinateType);

        geofenceService.createGeofenceList(geofenceRequest, intentData.get1())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "createGeofences -> task success");
                            promise.resolve(ReactUtils.intMap("requestCode", intentData.get0()));
                        } else {
                            Log.w(TAG, "createGeofences -> task failed | " + task.getException().getMessage());
                            promise.reject(task.getException());
                        }
                    }
                });
    }

    @ReactMethod
    public void deleteGeofenceList(int requestCode, final Promise promise) {
        Log.i(TAG, "deleteGeofenceList start");
        if (!requests.containsKey(requestCode)) {
            promise.reject(new Exceptions.NonExistentRequestID());
        }

        geofenceService.deleteGeofenceList(requests.get(requestCode))
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "deleteGeofenceList onSuccess");
                        promise.resolve(true);
                    }
                })
            .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "deleteGeofenceList onFailure: " + e.getMessage());
                        promise.reject(e);
                    }
                });
    }

    private Pair<Integer, PendingIntent> buildPendingIntent() {
        Log.d(TAG, "buildPendingIntent start");
        Intent intent = new Intent();
        intent.setAction(HMSBroadcastReceiver.ACTION_PROCESS_LOCATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(reactContext, mRequestCode++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        this.requests.put(mRequestCode, pendingIntent);
        return Pair.create(mRequestCode, pendingIntent);
    }

}
