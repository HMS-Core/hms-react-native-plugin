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

package com.huawei.hms.rn.push.remote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.push.HmsMessaging;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.hms.rn.push.constants.Core;
import com.huawei.hms.rn.push.constants.NotificationConstants;
import com.huawei.hms.rn.push.constants.ResultCode;
import com.huawei.hms.rn.push.utils.ActivityUtils;
import com.huawei.hms.rn.push.utils.BundleUtils;
import com.huawei.hms.rn.push.utils.MapUtils;
import com.huawei.hms.rn.push.utils.RemoteMessageUtils;
import com.huawei.hms.rn.push.logger.HMSLogger;
import com.huawei.hms.rn.push.utils.ResultUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HmsPushMessaging extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener {
    private final String TAG = HmsPushMessaging.class.getSimpleName();


    private static volatile ReactApplicationContext context;
    private static Map<String, Object> initialNotification = new HashMap<>();

    public HmsPushMessaging(ReactApplicationContext reactContext) {

        super(reactContext);
        setContext(reactContext);

        reactContext.addActivityEventListener(this);
        reactContext.addLifecycleEventListener(this);
    }

    @Override
    public void initialize() {

        super.initialize();
    }

    @Override
    public String getName() {

        return TAG;
    }

    public static ReactApplicationContext getContext() {
        return context;
    }

    public static void setContext(ReactApplicationContext context) {

        HmsPushMessaging.context = context;
    }

    @Override
    public Map<String, Object> getConstants() {

        return new HashMap<>();
    }

    public static void setInitialNotification(Map<String, Object> initialNotification) {
        HmsPushMessaging.initialNotification = initialNotification;
    }

    public static Map<String, Object> getInitialNotification() {
        return HmsPushMessaging.initialNotification;
    }

    @ReactMethod
    public void isAutoInitEnabled(final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("isAutoInitEnabled");
        try {
            boolean autoInit = HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).isAutoInitEnabled();
            HMSLogger.getInstance(getContext()).sendSingleEvent("isAutoInitEnabled");
            ResultUtils.handleResult(true, autoInit, promise);
        } catch (Exception ex) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("isAutoInitEnabled", ex.getMessage());
            ResultUtils.handleResult(false, ex.getLocalizedMessage(), promise);
        }
    }

    @ReactMethod
    public void setAutoInitEnabled(boolean enabled, final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setAutoInitEnabled");
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).setAutoInitEnabled(enabled);
            HMSLogger.getInstance(getContext()).sendSingleEvent("setAutoInitEnabled");
            ResultUtils.handleResult(true, enabled, promise);
        } catch (Exception ex) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("setAutoInitEnabled", ex.getMessage());
            ResultUtils.handleResult(false, ex.getLocalizedMessage(), promise);
        }
    }

    @ReactMethod
    public void turnOnPush(final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("turnOnPush");
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).turnOnPush()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        HMSLogger.getInstance(getContext()).sendSingleEvent("turnOnPush");
                        ResultUtils.handleResult(true, true, promise);

                    } else {
                        HMSLogger.getInstance(getContext()).sendSingleEvent("turnOnPush", task.getException().getMessage());
                        ResultUtils.handleResult(false, task.getException().getLocalizedMessage(), promise);
                    }
                });
        } catch (Exception ex) {
            ResultUtils.handleResult(false, ex.getLocalizedMessage(), promise);

        }
    }

    @ReactMethod
    public void turnOffPush(final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("turnOffPush");
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).turnOffPush()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        HMSLogger.getInstance(getContext()).sendSingleEvent("turnOffPush");
                        ResultUtils.handleResult(true, true, promise);

                    } else {
                        HMSLogger.getInstance(getContext()).sendSingleEvent("turnOffPush", task.getException().getMessage());
                        ResultUtils.handleResult(false, task.getException().getLocalizedMessage(), promise);
                    }
                });
        } catch (Exception ex) {
            ResultUtils.handleResult(false, ex.getLocalizedMessage(), promise);
        }
    }

    @ReactMethod
    public void subscribe(String topic, final Promise promise) {

        if (topic == null || topic.equals("")) {
            ResultUtils.handleResult(false, "topic is empty!", promise, ResultCode.PARAMETER_IS_EMPTY);
            return;
        }

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("subscribe");
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).subscribe(topic)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        HMSLogger.getInstance(getContext()).sendSingleEvent("subscribe");
                        ResultUtils.handleResult(true, true, promise);

                    } else {
                        HMSLogger.getInstance(getContext()).sendSingleEvent("subscribe", task.getException().getMessage());
                        ResultUtils.handleResult(false, task.getException().getLocalizedMessage(), promise);
                    }
                });
        } catch (Exception ex) {
            ResultUtils.handleResult(false, ex.getLocalizedMessage(), promise);
        }
    }

    @ReactMethod
    public void unsubscribe(String topic, final Promise promise) {

        if (topic == null || topic.equals("")) {
            ResultUtils.handleResult(false, "topic is empty!", promise, ResultCode.PARAMETER_IS_EMPTY);
            return;
        }
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("unsubscribe");
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).unsubscribe(topic)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        HMSLogger.getInstance(getContext()).sendSingleEvent("unsubscribe");
                        ResultUtils.handleResult(true, true, promise);

                    } else {
                        HMSLogger.getInstance(getContext()).sendSingleEvent("unsubscribe", task.getException().getMessage());
                        ResultUtils.handleResult(false, task.getException().getLocalizedMessage(), promise);
                    }
                });
        } catch (Exception ex) {
            ResultUtils.handleResult(false, ex.getLocalizedMessage(), promise);
        }
    }

    @ReactMethod
    public void sendRemoteMessage(ReadableMap arguments, final Promise promise) {

        try {
            RemoteMessage remoteMessage = RemoteMessageUtils.toRemoteMessage(arguments);
            HMSLogger.getInstance(getContext()).startMethodExecutionTimer("sendRemoteMessage");
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext()))
                .send(remoteMessage);
            HMSLogger.getInstance(getContext()).sendSingleEvent("sendRemoteMessage");

            ResultUtils.handleResult(true, true, promise);
        } catch (IllegalArgumentException e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("sendRemoteMessage");
            ResultUtils.handleResult(false, false, promise);
        }

    }

    @ReactMethod
    public void enableLogger(final Promise promise) {

        HMSLogger.getInstance(getContext()).enableLogger();
        ResultUtils.handleResult(true, true, promise);
    }

    @ReactMethod
    public void disableLogger(final Promise promise) {

        HMSLogger.getInstance(getContext()).disableLogger();
        ResultUtils.handleResult(true, true, promise);
    }

    @ReactMethod
    public void getInitialNotification(final Promise promise) {

        ResultUtils.handleResult(true, MapUtils.copyToWritableMap(getInitialNotification()), promise);

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onNewIntent(Intent intent) {
        Activity currentActivity = context.getCurrentActivity();

        if (currentActivity != null) {
            currentActivity.setIntent(intent);
        }
    }

    public boolean checkFlag(Intent intent) {
        int flagNumber = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_RECEIVER_REPLACE_PENDING | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
        int flagNumberAndBroughtToFront = flagNumber | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;
        return intent.getFlags() == flagNumber || intent.getFlags() == flagNumberAndBroughtToFront || intent.getBundleExtra(NotificationConstants.NOTIFICATION) != null;
    }

    public void sendOpenedNotificationData(Intent intent) {
        try {
            Map<String, Object> map = new HashMap<>();
            Bundle extras = intent.getExtras();
            if (extras != null) {
                RemoteMessage remoteMessage = new RemoteMessage(extras);
                map.put("remoteMessage", RemoteMessageUtils.toMap(remoteMessage));
                JSONObject extrasData = BundleUtils.convertJSONObject(extras);
                map.put("extras", extrasData);
            }
            if (intent.getDataString() != null)
                map.put("uriPage", intent.getDataString());
            HmsPushMessaging.setInitialNotification(map);
            getContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Core.Event.NOTIFICATION_OPENED_EVENT, MapUtils.copyToWritableMap(map));

            intent.setFlags(0);
            intent.replaceExtras(new Bundle());
            intent.setData(null);
        } catch (Exception e) {
            Log.w(TAG, "sendOpenedNotificationData: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onHostResume() {
        Activity activity = getContext().getCurrentActivity();

        if (activity == null) return;
        Intent intent = activity.getIntent();

        if (checkFlag(intent))
            sendOpenedNotificationData(intent);
    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {

    }

}
