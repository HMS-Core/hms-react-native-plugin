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

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.hms.rn.push.constants.Core;
import com.huawei.hms.rn.push.utils.BundleUtils;
import com.huawei.hms.rn.push.utils.RemoteMessageUtils;


public class HmsMessagePublisher extends ReactContextBaseJavaModule {
    private static String TAG = HmsMessagePublisher.class.getSimpleName();
    private static volatile ReactApplicationContext context;

    private static String token;
    private static String multiSenderToken;
    private static String bundleString;

    public HmsMessagePublisher(ReactApplicationContext reactContext) {
        super(reactContext);
        setContext(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public void initialize() {
        super.initialize();

        try {
            if (getToken() != null) {
                WritableMap params = Arguments.createMap();
                params.putString(Core.Event.Result.TOKEN, getToken());
                getContext()
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(Core.Event.ON_TOKEN_RECEIVED_EVENT, params);
            }

            if (getBundleString() != null && getMultiSenderToken() != null) {
                WritableMap multiSenderParams = Arguments.createMap();
                multiSenderParams.putString(Core.Event.Result.TOKEN, getMultiSenderToken());
                multiSenderParams.putString(Core.Event.Result.DATA_JSON, getBundleString());
                getContext()
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(Core.Event.ON_MULTI_SENDER_TOKEN_RECEIVED_EVENT, multiSenderParams);
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public static ReactApplicationContext getContext() {
        return context;
    }

    public static void setContext(ReactApplicationContext context) {
        HmsMessagePublisher.context = context;
    }

    public static void sendOnNewTokenEvent(String token) {
        try {
            WritableMap params = Arguments.createMap();
            params.putString(Core.Event.Result.TOKEN, token);

            if (getContext() == null) {
                setToken(token);
                return;
            }

            getContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Core.Event.ON_TOKEN_RECEIVED_EVENT, params);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public static void sendOnNewMultiSenderTokenEvent(String token, Bundle bundle) {
        try {
            WritableMap params = Arguments.createMap();
            params.putString(Core.Event.Result.TOKEN, token);
            params.putString(Core.Event.Result.DATA_JSON, BundleUtils.convertJSON(bundle));

            if (getContext() == null) {
                setBundleString(BundleUtils.convertJSON(bundle));
                setMultiSenderToken(token);
                return;
            }

            getContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Core.Event.ON_MULTI_SENDER_TOKEN_RECEIVED_EVENT, params);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public static void sendMessageReceivedEvent(RemoteMessage remoteMessage) {
        try {
            WritableMap params = Arguments.createMap();
            params.putMap(Core.Event.Result.MSG, RemoteMessageUtils.toWritableMap(remoteMessage));

            ReactApplicationContext reactApplicationContext = getContext();
            if (reactApplicationContext != null) {
                reactApplicationContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(Core.Event.REMOTE_DATA_MESSAGE_RECEIVED, params);
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

        }
    }

    public static void sendTokenErrorEvent(Exception e) {
        try {
            WritableMap params = Arguments.createMap();
            params.putString(Core.Event.Result.EXCEPTION, e.getMessage());
            getContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Core.Event.ON_TOKEN_ERROR_EVENT, params);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

        }
    }

    public static void sendMultiSenderTokenErrorEvent(Exception e, Bundle bundle) {
        try {
            WritableMap params = Arguments.createMap();
            params.putString(Core.Event.Result.EXCEPTION, e.getLocalizedMessage());
            params.putString(Core.Event.Result.DATA_JSON, BundleUtils.convertJSON(bundle));
            getContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Core.Event.ON_MULTI_SENDER_TOKEN_ERROR_EVENT, params);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public static void sendOnMessageSentEvent(String msgId) {
        try {
            WritableMap params = Arguments.createMap();
            params.putString(Core.Event.Result.MSG_ID, msgId);

            getContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Core.Event.ON_PUSH_MESSAGE_SENT, params);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public static void sendOnMessageSentErrorEvent(String msgId, int errorCode, String errorInfo) {
        try {
            WritableMap params = Arguments.createMap();
            params.putString(Core.Event.Result.RESULT, errorCode + "");
            params.putString(Core.Event.Result.MSG_ID, msgId);
            params.putString(Core.Event.Result.RESULT_INFO, errorInfo);

            getContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Core.Event.ON_PUSH_MESSAGE_SENT_ERROR, params);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public static void sendOnMessageDeliveredEvent(String msgId, int errorCode, String errorInfo) {
        try {
            WritableMap params = Arguments.createMap();
            params.putString(Core.Event.Result.RESULT, errorCode + "");
            params.putString(Core.Event.Result.MSG_ID, msgId);
            params.putString(Core.Event.Result.RESULT_INFO, errorInfo);

            getContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Core.Event.ON_PUSH_MESSAGE_SENT_DELIVERED, params);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        HmsMessagePublisher.token = token;
    }

    public static String getMultiSenderToken() {
        return multiSenderToken;
    }

    public static void setMultiSenderToken(String multiSenderToken) {
        HmsMessagePublisher.multiSenderToken = multiSenderToken;
    }

    public static String getBundleString() {
        return bundleString;
    }

    public static void setBundleString(String bundleString) {
        HmsMessagePublisher.bundleString = bundleString;
    }
}
