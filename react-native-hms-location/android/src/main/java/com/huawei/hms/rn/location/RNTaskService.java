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

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.huawei.hms.rn.location.backend.helpers.Constants;
import com.huawei.hms.rn.location.backend.helpers.HMSBroadcastReceiver;
import com.huawei.hms.rn.location.backend.helpers.Pair;
import com.huawei.hms.rn.location.helpers.ReactUtils;

import org.json.JSONObject;

public class RNTaskService extends HeadlessJsTaskService {
    private static final String TAG = RNTaskService.class.getName();
    private static final String CHANNEL_ID = "channel_id";

    public Notification getNotification() {
        SharedPreferences prefs =
                getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(),
                        Context.MODE_PRIVATE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(prefs.getString(Constants.KEY_CONTENT_TITLE, Constants.DEFAULT_CONTENT_TITLE))
                .setContentText(prefs.getString(Constants.KEY_CONTENT_TEXT, Constants.DEFAULT_CONTENT_TEXT))
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, getMainActivityClass()),
                        PendingIntent.FLAG_CANCEL_CURRENT))
                .setSmallIcon(getApplicationContext().getResources().getIdentifier(
                        prefs.getString(Constants.KEY_RESOURCE_NAME, Constants.DEFAULT_RESOURCE_NAME),
                        prefs.getString(Constants.KEY_DEF_TYPE, Constants.DEFAULT_DEF_TYPE),
                        getApplicationContext().getPackageName()))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "location",
                    NotificationManager.IMPORTANCE_NONE);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            startForeground(66666, getNotification());
        }
    }

    public Class getMainActivityClass() {
        Intent launchIntent =
                getApplicationContext().getPackageManager().getLaunchIntentForPackage(getApplicationContext().getPackageName());
        try {
            String className = launchIntent.getComponent().getClassName();
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Class not found", e);
            return null;
        }
    }

    @Nullable
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        Pair<String, JSONObject> intentData = HMSBroadcastReceiver.handleIntent(getApplicationContext(), intent);

        if (intentData != null) {
            String eventName;
            if (HMSBroadcastReceiver.getPackageAction(getApplicationContext(),
                    HMSBroadcastReceiver.ACTION_HMS_LOCATION).equals(intentData.get0())) {
                eventName = Constants.Event.SCANNING_RESULT.getVal();
            } else if (HMSBroadcastReceiver.getPackageAction(getApplicationContext(),
                    HMSBroadcastReceiver.ACTION_HMS_IDENTIFICATION).equals(intentData.get0())) {
                eventName = Constants.Event.ACTIVITY_IDENTIFICATION_RESULT.getVal();
            } else if (HMSBroadcastReceiver.getPackageAction(getApplicationContext(),
                    HMSBroadcastReceiver.ACTION_HMS_CONVERSION).equals(intentData.get0())) {
                eventName = Constants.Event.ACTIVITY_CONVERSION_RESULT.getVal();
            } else if (HMSBroadcastReceiver.getPackageAction(getApplicationContext(),
                    HMSBroadcastReceiver.ACTION_HMS_GEOFENCE).equals(intentData.get0())) {
                eventName = Constants.Event.GEOFENCE_RESULT.getVal();
            } else {
                return null;
            }
            return new HeadlessJsTaskConfig(
                    eventName,
                    ReactUtils.toWM(intentData.get1()),
                    5000,
                    false
            );
        }
        return null;
    }
}
