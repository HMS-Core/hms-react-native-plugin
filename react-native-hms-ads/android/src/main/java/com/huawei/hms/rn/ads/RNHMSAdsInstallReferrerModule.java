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

package com.huawei.hms.rn.ads;

import androidx.annotation.NonNull;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.hms.rn.ads.installreferrer.HmsInstallReferrer;
import com.huawei.hms.rn.ads.installreferrer.InstallReferrerAidlUtil;
import com.huawei.hms.rn.ads.installreferrer.InstallReferrerSdkUtil;

public class RNHMSAdsInstallReferrerModule extends ReactContextBaseJavaModule {
    private static final String TAG = RNHMSAdsInstallReferrerModule.class.getSimpleName();

    private final ReactApplicationContext reactContext;
    private HmsInstallReferrer referrer;
    private InstallReferrerSdkUtil sdkUtil;
    private InstallReferrerAidlUtil aidlUtil;

    public enum Event {
        SERVICE_CONNECTED("serviceConnected"),
        SERVICE_DISCONNECTED("serviceDisconnected");

        private String name;

        Event(String name) {
            this.name = name ;
        }

        public String getName() {
            return name;
        }
    }

    RNHMSAdsInstallReferrerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "RNHMSAdsInstallReferrer";
    }

    @ReactMethod
    public void startConnection(final String callMode, final boolean isTest, final String pkgName,
                                final Promise promise) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (callMode.equals("aidl")) {
                    aidlUtil = HmsInstallReferrer.createAidlReferrer(reactContext, pkgName);
                    referrer = aidlUtil;
                } else {
                    sdkUtil = HmsInstallReferrer.createSdkReferrer(reactContext);
                    referrer = sdkUtil;
                }
                if (referrer.getStatus() == HmsInstallReferrer.Status.CREATED ||
                        referrer.getStatus() == HmsInstallReferrer.Status.DISCONNECTED) {
                    promise.resolve(referrer.connect(isTest));
                } else {
                    Log.i(TAG, "Referrer already connected");
                    promise.reject("REFERRER_CANNOT_CONNECT", "Referrer already connected");
                }
            }
        });
    }

    @ReactMethod
    public void endConnection(final Promise promise) {
        if (referrer != null) {
            if (referrer.getStatus() == HmsInstallReferrer.Status.CONNECTED) {
                new Handler(Looper.getMainLooper()).post(() -> referrer.disconnect());
            } else {
                Log.i(TAG, "Referrer already disconnected");
            }
            promise.resolve(null);
        } else {
            promise.reject("END_CONN_FAILED", "EndConnection failed");
        }
    }

    @ReactMethod
    public void getReferrerDetails(final Promise promise) {
        if (referrer != null) {
            if (referrer.getStatus() == HmsInstallReferrer.Status.CONNECTED) {
                referrer.getInstallReferrer(promise);
            } else {
                Log.i(TAG, "Referrer already disconnected");
            }
        } else {
            Log.i(TAG, "Referrer is null");
        }
    }

    @ReactMethod
    public void isReady(final Promise promise) {
        if (referrer != null) {
            promise.resolve(referrer.isReady());
        } else {
            Log.i(TAG, "Referrer is null");
            promise.resolve(false);
        }
    }
}
