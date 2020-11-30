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

package com.huawei.hms.rn.location.backend.helpers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.huawei.hms.location.ActivityConversionResponse;
import com.huawei.hms.location.ActivityIdentificationResponse;
import com.huawei.hms.location.GeofenceData;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.rn.location.backend.helpers.Constants.Event;
import com.huawei.hms.rn.location.backend.interfaces.EventSender;
import com.huawei.hms.rn.location.backend.utils.ActivityUtils;
import com.huawei.hms.rn.location.backend.utils.GeofenceUtils;
import com.huawei.hms.rn.location.backend.utils.LocationUtils;

import org.json.JSONObject;

public class HMSBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = HMSBroadcastReceiver.class.getSimpleName();
    public static final String ACTION_HMS_LOCATION = "ACTION_HMS_LOCATION";
    public static final String ACTION_HMS_IDENTIFICATION = "ACTION_HMS_ACTIVITY_IDENTIFICATION";
    public static final String ACTION_HMS_CONVERSION = "ACTION_HMS_ACTIVITY_CONVERSION";
    public static final String ACTION_HMS_GEOFENCE = "ACTION_HMS_GEOFENCE";

    private static HMSBroadcastReceiver instance;
    private EventSender eventSender;

    public static synchronized HMSBroadcastReceiver getInstance() {
        if (instance == null) {
            instance = new HMSBroadcastReceiver();
        }
        return instance;
    }

    public static String getPackageAction(Context context, String action) {
        return context.getPackageName() + "." + action;
    }

    public void setEventSender(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    public static Pair<String, JSONObject> handleIntent(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            Log.d(TAG, action);
            if (getPackageAction(context, ACTION_HMS_LOCATION).equals(action) && LocationResult.hasResult(intent)) {
                return Pair.create(action,
                        LocationUtils.FROM_LOCATION_RESULT_TO_JSON_OBJECT.map(LocationResult.extractResult(intent)));
            } else if (getPackageAction(context, ACTION_HMS_IDENTIFICATION).equals(action) && ActivityIdentificationResponse.containDataFromIntent(intent)) {
                return Pair.create(action,
                        ActivityUtils.FROM_ACTIVITY_IDENTIFICATION_RESPONSE_TO_JSON_OBJECT.map(ActivityIdentificationResponse.getDataFromIntent(intent)));
            } else if (getPackageAction(context, ACTION_HMS_CONVERSION).equals(action) && ActivityConversionResponse.containDataFromIntent(intent)) {
                return Pair.create(action,
                        ActivityUtils.FROM_ACTIVITY_CONVERSION_RESPONSE_TO_JSON_OBJECT.map(ActivityConversionResponse.getDataFromIntent(intent)));
            } else if (getPackageAction(context, ACTION_HMS_GEOFENCE).equals(action)) {
                return Pair.create(action,
                        GeofenceUtils.FROM_GEOFENCE_DATA_TO_JSON_OBJECT.map(GeofenceData.getDataFromIntent(intent)));
            } else {
                Log.d(TAG, "onReceive unhandled intent, " + action);
            }
        }
        return null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive start");

        if (intent == null) {
            Log.d(TAG, "onReceive, intent is null");
            return;
        }

        if (eventSender == null) {
            Log.d(TAG, "onReceive, eventSender not initalized yet");
            return;
        }

        Pair<String, JSONObject> intentData = handleIntent(context, intent);
        if (intentData != null) {
            String eventName;
            if (getPackageAction(context, ACTION_HMS_LOCATION).equals(intentData.get0())) {
                eventName = Event.SCANNING_RESULT.getVal();
            } else if (getPackageAction(context, ACTION_HMS_IDENTIFICATION).equals(intentData.get0())) {
                eventName = Event.ACTIVITY_IDENTIFICATION_RESULT.getVal();
            } else if (getPackageAction(context, ACTION_HMS_CONVERSION).equals(intentData.get0())) {
                eventName = Event.ACTIVITY_CONVERSION_RESULT.getVal();
            } else if (getPackageAction(context, ACTION_HMS_GEOFENCE).equals(intentData.get0())) {
                eventName = Event.GEOFENCE_RESULT.getVal();
            } else {
                return;
            }
            eventSender.send(eventName, intentData.get1());
        }
        Log.d(TAG, "onReceive end");
    }

    public static IntentFilter getIntentFilter(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getPackageAction(context, ACTION_HMS_LOCATION));
        intentFilter.addAction(getPackageAction(context, ACTION_HMS_CONVERSION));
        intentFilter.addAction(getPackageAction(context, ACTION_HMS_IDENTIFICATION));
        intentFilter.addAction(getPackageAction(context, ACTION_HMS_GEOFENCE));
        return intentFilter;
    }

    public static HMSBroadcastReceiver init(Activity activity, final EventSender eventSender) {
        activity.registerReceiver(getInstance(), getIntentFilter(activity.getApplicationContext()));
        getInstance().setEventSender(eventSender);
        return getInstance();
    }
}
