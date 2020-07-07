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
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.ads.identifier.AdIdVerifyException;
import com.huawei.hms.ads.identifier.AdvertisingIdClient;
import com.huawei.hms.rn.ads.oaid.OaidAidlUtil;
import com.huawei.hms.rn.ads.oaid.OaidSdkUtil;

public class RNHMSAdsOaidModule extends ReactContextBaseJavaModule {
    private static final String TAG = RNHMSAdsOaidModule.class.getSimpleName();

    private final ReactApplicationContext reactContext;

    RNHMSAdsOaidModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "RNHMSAdsOaid";
    }

    @ReactMethod
    public void getAdvertisingIdInfo(final String callMode, final Promise promise) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (callMode.equals("aidl")) {
                OaidAidlUtil aidlUtil = new OaidAidlUtil(reactContext);
                aidlUtil.getOaid(promise);
            } else {
                OaidSdkUtil.getOaid(reactContext, promise);
            }
        });
    }

    @ReactMethod
    public void verifyAdvertisingId(ReadableMap advertisingInfo, final Promise promise) {
        try {
            boolean isVerified = AdvertisingIdClient.verifyAdId(reactContext, advertisingInfo.getString("id"),
                    advertisingInfo.getBoolean("isLimitAdTrackingEnabled"));
            promise.resolve(isVerified);
        } catch (AdIdVerifyException e) {
            Log.e(TAG, "verifyAdvertisingId Exception: " + e.getMessage());
            promise.reject("VERIFY_AD_ID_FAILED", "Exception: " + e.getMessage());
        }
    }
}
