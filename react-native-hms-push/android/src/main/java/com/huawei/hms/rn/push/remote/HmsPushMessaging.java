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

package com.huawei.hms.rn.push.remote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.push.HmsMessaging;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.hms.rn.push.constants.Core;
import com.huawei.hms.rn.push.constants.ResultCode;
import com.huawei.hms.rn.push.utils.ActivityUtils;
import com.huawei.hms.rn.push.utils.RemoteMessageUtils;
import com.huawei.hms.rn.push.logger.HMSLogger;

import java.util.HashMap;
import java.util.Map;

public class HmsPushMessaging extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener {
    private final String TAG = HmsPushMessaging.class.getSimpleName();


    private static volatile ReactApplicationContext context;
    private RemoteMessage initialNotification = null;

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

    @ReactMethod
    public void isAutoInitEnabled(Callback callBack) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("isAutoInitEnabled");
        try {
            String autoInit = HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).isAutoInitEnabled() + "";
            HMSLogger.getInstance(getContext()).sendSingleEvent("isAutoInitEnabled");
            callBack.invoke(ResultCode.SUCCESS, autoInit);
        } catch (Exception e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("isAutoInitEnabled", e.getMessage());
            callBack.invoke(ResultCode.RESULT_FAILURE, e.getMessage());
        }
    }

    @ReactMethod
    public void setAutoInitEnabled(boolean enabled, Callback callBack) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setAutoInitEnabled");
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).setAutoInitEnabled(enabled);
            HMSLogger.getInstance(getContext()).sendSingleEvent("setAutoInitEnabled");
            callBack.invoke(ResultCode.SUCCESS, enabled);
        } catch (Exception e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("setAutoInitEnabled", e.getMessage());
            callBack.invoke(ResultCode.RESULT_FAILURE, e.getMessage());
        }
    }

    @ReactMethod
    public void turnOnPush(final Callback callBack) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("turnOnPush");
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).turnOnPush()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            HMSLogger.getInstance(getContext()).sendSingleEvent("turnOnPush");
                            callBack.invoke(ResultCode.SUCCESS);
                        } else {
                            HMSLogger.getInstance(getContext()).sendSingleEvent("turnOnPush", task.getException().getMessage());
                            callBack.invoke(ResultCode.RESULT_FAILURE, task.getException().getMessage());
                        }
                    });
        } catch (Exception e) {
            callBack.invoke(ResultCode.RESULT_FAILURE, e.getMessage());
        }
    }


    @ReactMethod
    public void turnOffPush(final Callback callBack) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("turnOffPush");
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).turnOffPush()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            HMSLogger.getInstance(getContext()).sendSingleEvent("turnOffPush");
                            callBack.invoke(ResultCode.SUCCESS);
                        } else {
                            HMSLogger.getInstance(getContext()).sendSingleEvent("turnOffPush", task.getException().getMessage());
                            callBack.invoke(ResultCode.RESULT_FAILURE, task.getException().getMessage());
                        }
                    });
        } catch (Exception e) {
            callBack.invoke(ResultCode.RESULT_FAILURE, e.getMessage());
        }
    }


    @ReactMethod
    public void subscribe(String topic, final Callback callBack) {

        if (topic == null || topic.equals("")) {
            callBack.invoke(ResultCode.PARAMETER_IS_EMPTY, "topic is empty!");
            return;
        }
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("subscribe");
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).subscribe(topic)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            HMSLogger.getInstance(getContext()).sendSingleEvent("subscribe");
                            callBack.invoke(ResultCode.SUCCESS);
                        } else {
                            HMSLogger.getInstance(getContext()).sendSingleEvent("subscribe", task.getException().getMessage());
                            callBack.invoke(ResultCode.RESULT_FAILURE, task.getException().getMessage());
                        }
                    });
        } catch (Exception e) {
            callBack.invoke(ResultCode.RESULT_FAILURE, e.getMessage());
        }
    }

    @ReactMethod
    public void unsubscribe(String topic, final Callback callBack) {

        if (topic == null || topic.equals("")) {
            callBack.invoke(ResultCode.PARAMETER_IS_EMPTY, "topic is empty!");
            return;
        }
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("unsubscribe");
        try {
            HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).unsubscribe(topic)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            HMSLogger.getInstance(getContext()).sendSingleEvent("unsubscribe");
                            callBack.invoke(ResultCode.SUCCESS);
                        } else {
                            HMSLogger.getInstance(getContext()).sendSingleEvent("unsubscribe", task.getException().getMessage());
                            callBack.invoke(ResultCode.RESULT_FAILURE, task.getException().getMessage());
                        }
                    });
        } catch (Exception e) {
            callBack.invoke(ResultCode.RESULT_FAILURE, e.getMessage());
        }
    }

    @ReactMethod
    public void sendRemoteMessage(ReadableMap arguments) {

        RemoteMessage remoteMessage = RemoteMessageUtils.toRemoteMessage(arguments);
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("sendRemoteMessage");
        HmsMessaging.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext()))
                .send(remoteMessage);
        HMSLogger.getInstance(getContext()).sendSingleEvent("sendRemoteMessage");
    }

    @ReactMethod
    public void enableLogger() {

        HMSLogger.getInstance(getContext()).enableLogger();
    }

    @ReactMethod
    public void disableLogger() {

        HMSLogger.getInstance(getContext()).disableLogger();
    }

    @ReactMethod
    public void getInitialNotification(Callback callback) {

        callback.invoke("initial notification", initialNotification == null ?
                null : RemoteMessageUtils.fromMap(initialNotification));
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onNewIntent(Intent intent) {

        context.getCurrentActivity().setIntent(intent);
    }

    @Override
    public void onHostResume() {

        Bundle extras = getContext().getCurrentActivity().getIntent().getExtras();
        if (extras != null) {
            initialNotification = new RemoteMessage(extras);
            getContext()
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(Core.Event.NOTIFICATION_OPENED_EVENT, RemoteMessageUtils.fromMap(initialNotification));
        }
    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {

    }
}
