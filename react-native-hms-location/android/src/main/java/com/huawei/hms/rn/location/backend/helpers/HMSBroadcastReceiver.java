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
import com.huawei.hms.rn.location.backend.helpers.Constants.Event;
import com.huawei.hms.rn.location.backend.interfaces.EventSender;
import com.huawei.hms.rn.location.backend.utils.ActivityUtils;
import com.huawei.hms.rn.location.backend.utils.GeofenceUtils;

public class HMSBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = HMSBroadcastReceiver.class.getSimpleName();
    public static final String ACTION_PROCESS_LOCATION =
            "com.huawei.hms.rn.location.backend.helpers.HMSBroadcastReceiver.ACTION_PROCESS_LOCATION";
    public static final String ACTION_PROCESS_IDENTIFICATION =
            "com.huawei.hms.rn.location.backend.helpers.HMSBroadcastReceiver.ACTION_PROCESS_ACTIVITY_IDENTIFICATION";
    public static final String ACTION_PROCESS_CONVERSION =
            "com.huawei.hms.rn.location.backend.helpers.HMSBroadcastReceiver.ACTION_PROCESS_ACTIVITY_CONVERSION";

    private static HMSBroadcastReceiver instance;
    private EventSender eventSender;

    public static synchronized HMSBroadcastReceiver getInstance() {
        if (instance == null) {
            instance = new HMSBroadcastReceiver();
        }
        return instance;
    }

    public void setEventSender(EventSender eventSender) {
        this.eventSender = eventSender;
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

        if (ACTION_PROCESS_IDENTIFICATION.equals(intent.getAction())
                && ActivityIdentificationResponse.containDataFromIntent(intent)) {
            Log.d(TAG, intent.getAction());
            ActivityIdentificationResponse response = ActivityIdentificationResponse.getDataFromIntent(intent);
            eventSender.send(
                    Event.ACTIVITY_IDENTIFICATION_RESULT.getVal(),
                    ActivityUtils.FROM_ACTIVITY_IDENTIFICATION_RESPONSE_TO_JSON_OBJECT.map(response)
            );
        } else if (ACTION_PROCESS_CONVERSION.equals(intent.getAction()) && ActivityConversionResponse.containDataFromIntent(intent)) {
            Log.d(TAG, intent.getAction());
            ActivityConversionResponse response = ActivityConversionResponse.getDataFromIntent(intent);
            Log.d(TAG, String.valueOf(response.getActivityConversionDatas().size()));
            eventSender.send(
                    Event.ACTIVITY_CONVERSION_RESULT.getVal(),
                    ActivityUtils.FROM_ACTIVITY_CONVERSION_RESPONSE_TO_JSON_OBJECT.map(response)
            );
        } else if (ACTION_PROCESS_LOCATION.equals(intent.getAction())) {
            Log.d(TAG, intent.getAction());
            GeofenceData geofenceData = GeofenceData.getDataFromIntent(intent);
            eventSender.send(
                    Event.GEOFENCE_RESULT.getVal(),
                    GeofenceUtils.FROM_GEOFENCE_DATA_TO_JSON_OBJECT.map(geofenceData)
            );
        } else {
            Log.d(TAG, "onReceive unhandled intent, " + intent.getAction());
        }

        Log.d(TAG, "onReceive end");
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(HMSBroadcastReceiver.ACTION_PROCESS_CONVERSION);
        intentFilter.addAction(HMSBroadcastReceiver.ACTION_PROCESS_IDENTIFICATION);
        intentFilter.addAction(HMSBroadcastReceiver.ACTION_PROCESS_LOCATION);
        return intentFilter;
    }

    public static HMSBroadcastReceiver init(Activity activity, final EventSender eventSender) {
        activity.registerReceiver(getInstance(), getIntentFilter());
        getInstance().setEventSender(eventSender);
        return getInstance();
    }
}
