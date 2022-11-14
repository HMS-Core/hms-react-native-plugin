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

package com.huawei.hms.rn.health.kits.autorecorder.utils;

import static com.huawei.hms.rn.health.kits.autorecorder.utils.AutoRecorderConstants.BACKGROUND_SERVICE_KEY;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.huawei.hms.hihealth.AutoRecorderController;
import com.huawei.hms.hihealth.HiHealthOptions;
import com.huawei.hms.hihealth.HuaweiHiHealth;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class AutoRecorderBackgroundService extends Service {

    private static final String TAG = AutoRecorderBackgroundService.class.getSimpleName();

    private static final String NOTIFICATION_CHANNEL_ID = "hms-health";

    // HMS Health AutoRecorderController
    private AutoRecorderController autoRecorderController;

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initAutoRecorderController();
        Log.i(TAG, "service is created.");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Invoke the real-time callback interface of the HealthKit.
        getRemoteService(intent.getParcelableExtra("DataType"));
        // Binding a notification bar
        getNotification(intent.getExtras());
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * init AutoRecorderController
     */
    private void initAutoRecorderController() {
        HiHealthOptions options = HiHealthOptions.builder().build();
        AuthHuaweiId signInHuaweiId = HuaweiIdAuthManager.getExtendedAuthResult(options);
        autoRecorderController = HuaweiHiHealth.getAutoRecorderController(context, signInHuaweiId);
    }

    /**
     * Bind the service to the notification bar so that the service can be changed to a foreground service.
     */
    private void getNotification(Bundle bundle) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "HmsHealth",
                NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        PackageManager pm = context.getPackageManager();
        Intent notificationIntent = pm.getLaunchIntentForPackage(context.getPackageName());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,
            NOTIFICATION_CHANNEL_ID).setWhen(System.currentTimeMillis())
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSmallIcon(configSmallIcon(bundle, context))
            .setContentTitle(getBundleString(bundle, "title"))
            .setContentText(getBundleString(bundle, "text"))
            .setContentIntent(pendingIntent)
            .setUsesChronometer(getBundleBoolean(bundle, "chronometer"))
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setOngoing(true);

        if (hasValue(bundle, "ticker")) {
            notificationBuilder.setTicker(getBundleString(bundle, "ticker"));
        }
        if (hasValue(bundle, "subText")) {
            notificationBuilder.setSubText(getBundleString(bundle, "subText"));
        }

        if (hasValue(bundle, "largeIcon")) {
            notificationBuilder.setLargeIcon(getIconFromAsset(getBundleString(bundle, "largeIcon")));
        }
        Notification notification = notificationBuilder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        startForeground(1, notification);

    }

    /**
     * Callback Interface for Starting the Total Step Count
     */
    private void getRemoteService(DataType datatype) {
        if (autoRecorderController == null) {
            initAutoRecorderController();
        }
        // Start recording real-time steps.
        autoRecorderController.startRecord(datatype, samplePoint -> {
            // The step count, time, and type data reported by the pedometer is called back to the app through
            // samplePoint.
            Intent intent = new Intent();
            intent.putExtra("SamplePoint", samplePoint);
            intent.setAction(BACKGROUND_SERVICE_KEY);
            // Transmits service data to activities through broadcast.
            sendBroadcast(intent, Manifest.permission.FOREGROUND_SERVICE);

        }).addOnSuccessListener(aVoid -> {
            Log.i(TAG, "record steps success... ");
            HMSLogger.getInstance(context).sendSingleEvent("HmsAutoRecorderController.startRecord");

        }).addOnFailureListener(e -> Log.i(TAG, "report steps failed... "));
    }

    private Bitmap getIconFromAsset(String name) {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        Bitmap bitmap;
        try {
            inputStream = assetManager.open(name);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException ignored) {
            bitmap = null;
        } finally {
            closeStream(inputStream);
        }

        return bitmap;
    }

    private void closeStream(Closeable stream) {
        try{
            if(stream != null){
                stream.close();
            }
        }catch(IOException e){
            Log.e(TAG, "There is a problem with closing the input stream");
        }
    }

    private static String getBundleString(Bundle bundle, String key) {
        if (bundle != null) {
            return bundle.getString(key);
        }
        return "";
    }

    private static boolean getBundleBoolean(Bundle bundle, String key) {
        if (bundle != null) {
            return bundle.getBoolean(key);
        }
        return false;
    }

    private static boolean hasValue(Bundle bundle, String key) {
        if (bundle != null) {
            String val = bundle.getString(key);
            if (val != null) {
                return !val.isEmpty();
            }
        }
        return false;
    }

    private static int configSmallIcon(Bundle bundle, Context context) {
        Resources res = context.getResources();
        String packageName = context.getPackageName();

        int resourceId;
        String value = null;
        if (bundle != null) {
            value = bundle.getString("smallIcon");

        }

        resourceId = value != null
            ? res.getIdentifier(value, "mipmap", packageName)
            : res.getIdentifier("ic_notification", "mipmap", packageName);

        if (resourceId == 0) {
            resourceId = res.getIdentifier("ic_launcher", "mipmap", packageName);

            if (resourceId == 0) {
                resourceId = android.R.drawable.ic_dialog_info;
            }
        }
        return resourceId;
    }

}
