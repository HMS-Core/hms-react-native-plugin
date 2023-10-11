/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.push.local;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.huawei.hms.rn.push.constants.ResultCode;
import com.huawei.hms.rn.push.utils.NotificationConfigUtils;
import com.huawei.hms.rn.push.utils.ResultUtils;

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
    public void localNotification(ReadableMap details, final Promise promise) {

        Bundle bundle = Arguments.toBundle(details);
        if (bundle == null) {
            ResultUtils.handleResult(false, false, promise, ResultCode.NULL_BUNDLE);
            return;
        }
        NotificationConfigUtils.configId(bundle);

        hmsLocalNotificationController.localNotificationNow(bundle, promise);

    }

    @ReactMethod
    public void localNotificationSchedule(ReadableMap details, final Promise promise) {

        Bundle bundle = Arguments.toBundle(details);
        if (bundle == null) {
            ResultUtils.handleResult(false, false, promise, ResultCode.NULL_BUNDLE);
            return;
        }
        NotificationConfigUtils.configId(bundle);

        hmsLocalNotificationController.localNotificationSchedule(bundle, promise);

    }

    @ReactMethod
    public void cancelAllNotifications(final Promise promise) {

        hmsLocalNotificationController.cancelScheduledNotifications();
        hmsLocalNotificationController.cancelNotifications();

        if (promise != null)
            ResultUtils.handleResult(true, true, promise);

    }

    @ReactMethod
    public void cancelNotifications(final Promise promise) {

        hmsLocalNotificationController.cancelNotifications();

        if (promise != null)
            ResultUtils.handleResult(true, true, promise);

    }

    @ReactMethod
    public void cancelScheduledNotifications(final Promise promise) {

        hmsLocalNotificationController.cancelScheduledNotifications();

        if (promise != null)
            ResultUtils.handleResult(true, true, promise);

    }

    @ReactMethod
    public void cancelNotificationsWithId(ReadableArray idArr, final Promise promise) {

        hmsLocalNotificationController.cancelNotificationsWithId(idArr);

        if (promise != null)
            ResultUtils.handleResult(true, true, promise);

    }

    @ReactMethod
    public void cancelNotificationsWithIdTag(ReadableArray idTagArr, final Promise promise) {

        hmsLocalNotificationController.cancelNotificationsWithIdTag(idTagArr);

        if (promise != null)
            ResultUtils.handleResult(true, true, promise);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @ReactMethod
    public void cancelNotificationsWithTag(String tag, final Promise promise) {

        hmsLocalNotificationController.cancelNotificationsWithTag(tag);

        if (promise != null)
            ResultUtils.handleResult(true, true, promise);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @ReactMethod
    public void getNotifications(final Promise promise) {

        WritableArray result = hmsLocalNotificationController.getNotifications();

        if (promise != null)
            ResultUtils.handleResult(true, result, promise);

    }

    @ReactMethod
    public void getScheduledNotifications(final Promise promise) {

        WritableArray result = hmsLocalNotificationController.getScheduledNotifications();

        if (promise != null)
            ResultUtils.handleResult(true, result, promise);

    }


    @ReactMethod
    public void getChannels(final Promise promise) {

        WritableArray result = Arguments.fromList(hmsLocalNotificationController.listChannels());

        if (promise != null)
            ResultUtils.handleResult(true, result, promise);

    }

    @ReactMethod
    public void channelExists(String channelId, final Promise promise) {

        hmsLocalNotificationController.channelExists(channelId, promise);


    }

    @ReactMethod
    public void channelBlocked(String channelId, final Promise promise) {

        hmsLocalNotificationController.isChannelBlocked(channelId, promise);

    }

    @ReactMethod
    public void deleteChannel(String channelId, final Promise promise) {

        hmsLocalNotificationController.deleteChannel(channelId, promise);

    }

}
