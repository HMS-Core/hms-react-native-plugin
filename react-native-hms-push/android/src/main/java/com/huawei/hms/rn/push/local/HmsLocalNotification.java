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

package com.huawei.hms.rn.push.local;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.huawei.hms.rn.push.constants.ResultCode;
import com.huawei.hms.rn.push.utils.NotificationConfigUtils;

import java.util.HashMap;
import java.util.Map;


public class HmsLocalNotification extends ReactContextBaseJavaModule {

    private final String TAG = HmsLocalNotification.class.getSimpleName();


    private HmsLocalNotificationController hmsLocalNotificationController;

    public HmsLocalNotification(ReactApplicationContext reactContext) {

        super(reactContext);
        Application applicationContext = (Application) reactContext.getApplicationContext();
        hmsLocalNotificationController = new HmsLocalNotificationController(applicationContext);

        hmsLocalNotificationController.createDefaultChannel();
    }

    @Override
    public String getName() {

        return TAG;
    }

    @Override
    public Map<String, Object> getConstants() {

        return new HashMap<>();
    }

    @ReactMethod
    public void localNotification(ReadableMap details, Callback callback) {

        Bundle bundle = Arguments.toBundle(details);
        if (bundle == null) {
            callback.invoke(ResultCode.NULL_BUNDLE);
            return;
        }
        NotificationConfigUtils.configId(bundle);

        hmsLocalNotificationController.localNotificationNow(bundle, callback);

    }

    @ReactMethod
    public void localNotificationSchedule(ReadableMap details, Callback callback) {

        Bundle bundle = Arguments.toBundle(details);
        if (bundle == null) {
            callback.invoke(ResultCode.NULL_BUNDLE);
            return;
        }
        NotificationConfigUtils.configId(bundle);

        hmsLocalNotificationController.localNotificationSchedule(bundle, callback);

    }

    @ReactMethod
    public void cancelAllNotifications(Callback callback) {

        hmsLocalNotificationController.cancelScheduledNotifications();
        hmsLocalNotificationController.cancelNotifications();

        if (callback != null)
            callback.invoke(ResultCode.SUCCESS, true);
    }

    @ReactMethod
    public void cancelNotifications(Callback callback) {

        hmsLocalNotificationController.cancelNotifications();

        if (callback != null)
            callback.invoke(ResultCode.SUCCESS, true);

    }

    @ReactMethod
    public void cancelScheduledNotifications(Callback callback) {

        hmsLocalNotificationController.cancelScheduledNotifications();

        if (callback != null)
            callback.invoke(ResultCode.SUCCESS, true);

    }

    @ReactMethod
    public void cancelNotificationsWithId(ReadableArray idArr, Callback callback) {

        hmsLocalNotificationController.cancelNotificationsWithId(idArr);

        if (callback != null)
            callback.invoke(ResultCode.SUCCESS, true);

    }

    @ReactMethod
    public void cancelNotificationsWithIdTag(ReadableArray idTagArr, Callback callback) {

        hmsLocalNotificationController.cancelNotificationsWithIdTag(idTagArr);

        if (callback != null)
            callback.invoke(ResultCode.SUCCESS, true);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @ReactMethod
    public void cancelNotificationsWithTag(String tag, Callback callback) {

        hmsLocalNotificationController.cancelNotificationsWithTag(tag);

        if (callback != null)
            callback.invoke(ResultCode.SUCCESS, true);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @ReactMethod
    public void getNotifications(Callback callback) {

        WritableArray result = hmsLocalNotificationController.getNotifications();

        if (callback != null)
            callback.invoke(ResultCode.SUCCESS, result);

    }

    @ReactMethod
    public void getScheduledNotifications(Callback callback) {

        WritableArray result = hmsLocalNotificationController.getScheduledNotifications();

        if (callback != null)
            callback.invoke(ResultCode.SUCCESS, result);

    }


    @ReactMethod
    public void getChannels(Callback callback) {

        WritableArray result = Arguments.fromList(hmsLocalNotificationController.listChannels());

        if (callback != null)
            callback.invoke(ResultCode.SUCCESS, result);

    }

    @ReactMethod
    public void channelExists(String channelId, Callback callback) {

        boolean result = hmsLocalNotificationController.channelExists(channelId);

        if (callback != null)
            callback.invoke(ResultCode.SUCCESS, result);

    }

    @ReactMethod
    public void channelBlocked(String channelId, Callback callback) {

        boolean result = hmsLocalNotificationController.isChannelBlocked(channelId);

        if (callback != null)
            callback.invoke(ResultCode.SUCCESS, result);

    }

    @ReactMethod
    public void deleteChannel(String channelId, Callback callback) {

        hmsLocalNotificationController.deleteChannel(channelId, callback);

    }

}
