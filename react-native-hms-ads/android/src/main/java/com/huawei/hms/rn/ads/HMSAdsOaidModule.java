/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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
import com.huawei.hms.rn.ads.logger.HMSLogger;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import java.io.IOException;

import static com.huawei.hms.rn.ads.HMSAdsModule.CallMode;

public class HMSAdsOaidModule extends ReactContextBaseJavaModule {
    private static final String TAG = HMSAdsOaidModule.class.getSimpleName();

    private final ReactApplicationContext reactContext;
    private HMSLogger hmsLogger;

    HMSAdsOaidModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        hmsLogger = HMSLogger.getInstance(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSAdsOaid";
    }

    @ReactMethod
    public void getAdvertisingIdInfo(final String callMode, final Promise promise) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (CallMode.forValue(callMode) == CallMode.AIDL) {
                promise.reject("AIDL_SERVICE_INVALID", "Aidl service is disabled for HMSOaid module.");
                return;
            }
            try {
                //Get advertising id information. Do not call this method in the main thread.
                hmsLogger.startMethodExecutionTimer("getAdvertisingIdInfo");
                AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(reactContext);
                hmsLogger.sendSingleEvent("getAdvertisingIdInfo");
                promise.resolve(ReactUtils.getWritableMapFromAdvertisingIdClientInfo(info));
            } catch (IOException e) {
                hmsLogger.sendSingleEvent("getAdvertisingIdInfo", "-1");
                Log.e(TAG, "getAdvertisingIdInfo IOException");
                promise.reject("GET_AD_ID_INFO_FAILED", "getAdvertisingIdInfo IOException");
            }
        });
    }

    @ReactMethod
    public void verifyAdvertisingId(final ReadableMap advertisingInfo, final Promise promise) {
        try {
            String id = advertisingInfo.getString("id");
            boolean isLimitAdTrackingEnabled = advertisingInfo.getBoolean("isLimitAdTrackingEnabled");
            hmsLogger.startMethodExecutionTimer("verifyAdvertisingId");
            boolean isVerified = AdvertisingIdClient.verifyAdId(reactContext, id, isLimitAdTrackingEnabled);
            hmsLogger.sendSingleEvent("verifyAdvertisingId");
            promise.resolve(isVerified);
        } catch (AdIdVerifyException e) {
            Log.e(TAG, "verifyAdvertisingId Exception: " + e.getMessage());
            promise.reject("VERIFY_AD_ID_FAILED", "Exception: " + e.getMessage());
        }
    }
}
