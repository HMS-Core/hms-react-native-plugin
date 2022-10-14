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
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.huawei.hms.kit.awareness.barrier.BarrierStatus;
import com.huawei.hms.rn.awareness.constants.LocaleConstants;

import static com.huawei.hms.rn.awareness.utils.DataUtils.barrierStatusConvertToMap;

public class TaskService extends HeadlessJsTaskService {
    private static final String TAG = TaskService.class.getName();
    private static final String CHANNEL_ID = "hms_rn_location";
    private static final String CHANNEL_NAME = "location";

    public Notification getNotification() {
        SharedPreferences prefs =
                getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(),
                        Context.MODE_PRIVATE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(prefs.getString(LocaleConstants.KEY_CONTENT_TITLE, LocaleConstants.DEFAULT_CONTENT_TITLE))
                .setContentText(prefs.getString(LocaleConstants.KEY_CONTENT_TEXT, LocaleConstants.DEFAULT_CONTENT_TEXT))
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, getMainActivityClass()),
                        PendingIntent.FLAG_CANCEL_CURRENT))
                .setSmallIcon(getApplicationContext().getResources().getIdentifier(
                        prefs.getString(LocaleConstants.KEY_RESOURCE_NAME, LocaleConstants.DEFAULT_RESOURCE_NAME),
                        prefs.getString(LocaleConstants.KEY_DEF_TYPE, LocaleConstants.DEFAULT_DEF_TYPE),
                        getApplicationContext().getPackageName()))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
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
        Log.d(TAG, "getTaskConfig");
        if (intent != null) {
            Log.d(TAG, "has intent");
            return new HeadlessJsTaskConfig(
                    "barrierReceiver",
                    barrierStatusConvertToMap(BarrierStatus.extract(intent)),
                    5000,
                    false
            );
        }
        return null;
    }
}