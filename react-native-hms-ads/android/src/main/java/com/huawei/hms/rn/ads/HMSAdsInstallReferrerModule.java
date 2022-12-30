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

package com.huawei.hms.rn.ads;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.ads.installreferrer.api.InstallReferrerClient;
import com.huawei.hms.ads.installreferrer.api.InstallReferrerStateListener;
import com.huawei.hms.ads.installreferrer.api.ReferrerDetails;
import com.huawei.hms.rn.ads.logger.HMSLogger;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import java.io.IOException;

import static com.huawei.hms.rn.ads.HMSAdsModule.CallMode;

public class HMSAdsInstallReferrerModule extends ReactContextBaseJavaModule implements InstallReferrerStateListener {
    private static final String TAG = HMSAdsInstallReferrerModule.class.getSimpleName();

    private final ReactApplicationContext reactContext;
    private HMSLogger hmsLogger;
    private InstallReferrerClient mReferrerClient;

    public enum Event {
        SERVICE_CONNECTED("serviceConnected"),
        SERVICE_DISCONNECTED("serviceDisconnected");

        private String name;

        Event(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    HMSAdsInstallReferrerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        hmsLogger = HMSLogger.getInstance(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSAdsInstallReferrer";
    }

    @Override
    public void onInstallReferrerSetupFinished(int responseCode) {
        WritableMap wm = new WritableNativeMap();
        wm.putInt("responseCode", responseCode);
        wm.putString("responseMessage", getResponseMessage(responseCode));
        sendEvent(Event.SERVICE_CONNECTED, wm);
    }

    @Override
    public void onInstallReferrerServiceDisconnected() {
        sendEvent(Event.SERVICE_DISCONNECTED, null);
    }

    void sendEvent(Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event.getName(), wm);
    }

    @ReactMethod
    public void startConnection(final String callMode, final boolean isTest, final Promise promise) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (CallMode.forValue(callMode) == CallMode.AIDL) {
                promise.reject("AIDL_SERVICE_INVALID", "Aidl service is disabled");
                return;
            }
            if (mReferrerClient != null) {
                promise.reject("REFERRER_CANNOT_CONNECT", "Referrer already connected");
                return;
            }
            mReferrerClient = InstallReferrerClient.newBuilder(reactContext).setTest(isTest).build();
            hmsLogger.startMethodExecutionTimer("startConnection");
            mReferrerClient.startConnection(this);
            hmsLogger.sendSingleEvent("startConnection");
            promise.resolve(null);
        });
    }

    @ReactMethod
    public void endConnection(final Promise promise) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (mReferrerClient == null) {
                promise.reject("REFERRER_NOT_AVAILABLE", "Referrer is not available");
                return;
            }
            hmsLogger.startMethodExecutionTimer("endConnection");
            mReferrerClient.endConnection();
            hmsLogger.sendSingleEvent("endConnection");
            mReferrerClient = null;
            promise.resolve(null);
        });
    }

    @ReactMethod
    public void getReferrerDetails(final String installChannel, final Promise promise) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (mReferrerClient == null) {
                promise.reject("REFERRER_NOT_AVAILABLE", "Referrer is not available");
                return;
            }
            try {
                hmsLogger.startMethodExecutionTimer("getInstallReferrer");
                ReferrerDetails referrerDetails = mReferrerClient.getInstallReferrer();
                referrerDetails.setInstallChannel(installChannel);
                hmsLogger.sendSingleEvent("getInstallReferrer");
                promise.resolve(ReactUtils.getWritableMapFromReferrerDetails(referrerDetails));
            } catch (RemoteException e) {
                hmsLogger.sendSingleEvent("getInstallReferrer", "-1");
                promise.reject("REMOTE_EXCEPTION", e.getMessage());
            } catch (IOException e) {
                hmsLogger.sendSingleEvent("getInstallReferrer", "-1");
                promise.reject("IO_EXCEPTION", e.getMessage());
            }
        });
    }

    @ReactMethod
    public void isReady(final Promise promise) {
        if (mReferrerClient == null) {
            promise.reject("REFERRER_NOT_AVAILABLE", "Referrer is not available");
            return;
        }
        hmsLogger.startMethodExecutionTimer("isReady");
        promise.resolve(mReferrerClient.isReady());
        hmsLogger.sendSingleEvent("isReady");
    }

    private String getResponseMessage(int responseCode) {
        String responseMessage = "Unknown response";
        switch (responseCode) {
            case InstallReferrerClient.InstallReferrerResponse.OK:
                responseMessage = "Connected to the service successfully";
                break;
            case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                // Service not supported. Please download and install the latest version of Huawei Mobile
                // Services(APK).
                responseMessage = "The service is not supported";
                break;
            case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                // Service unavailable. Please update the version of Huawei Mobile Services(APK) to 2.6.5 or later.
                responseMessage = "The service does not exist";
                break;
            case InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED:
                responseMessage = "Failed to connect to the service";
                break;
            case InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR:
                responseMessage = "A call error occurred";
                break;
            default:
                break;
        }
        return responseMessage;
    }
}
