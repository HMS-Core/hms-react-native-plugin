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
import androidx.annotation.Nullable;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.InterstitialAd;

import com.huawei.hms.rn.ads.utils.ReactUtils;

public class RNHMSAdsInterstitialAdModule extends ReactContextBaseJavaModule {
    private static final String TAG = RNHMSAdsInterstitialAdModule.class.getSimpleName();

    private ReactApplicationContext mReactContext;
    private InterstitialAd interstitialAd;
    private ReadableMap mAdParamReadableMap;

    public enum Event {
        AD_CLOSED("adClosed"),
        AD_FAILED("adFailed"),
        AD_LEAVE("adLeave"),
        AD_OPENED("adOpened"),
        AD_LOADED("adLoaded"),
        AD_CLICKED("adClicked"),
        AD_IMPRESSION("adImpression");

        private String name;

        Event(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "RNHMSAdsInterstitial";
    }

    RNHMSAdsInterstitialAdModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
        interstitialAd = new InterstitialAd(reactContext);
        Log.i(TAG, "InterstitialAd object is created");
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                sendEvent(Event.AD_CLOSED, null);
            }

            @Override
            public void onAdFailed(int errorCode) {
                WritableMap wm = new WritableNativeMap();
                wm.putInt("errorCode", errorCode);
                wm.putString("errorMessage", RNHMSAdsModule.getErrorMessage(errorCode));
                sendEvent(Event.AD_FAILED, wm);
            }

            @Override
            public void onAdLeave() {
                sendEvent(Event.AD_LEAVE, null);
            }

            @Override
            public void onAdOpened() {
                sendEvent(Event.AD_OPENED, null);
            }

            @Override
            public void onAdLoaded() {
                sendEvent(Event.AD_LOADED, ReactUtils.getWritableMapFromInterstitialAd(interstitialAd));
            }

            @Override
            public void onAdClicked() {
                sendEvent(Event.AD_CLICKED, null);
            }

            @Override
            public void onAdImpression() {
                sendEvent(Event.AD_IMPRESSION, null);
            }
        });
        Log.i(TAG, "AdListener object is created");
        Log.i(TAG, "setAdListener() is called");
    }

    private void sendEvent(Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event.getName(), wm);
    }

    @ReactMethod
    public void setAdParam(ReadableMap adParamReadableMap) {
        mAdParamReadableMap = adParamReadableMap;
        Log.i(TAG, "adParam is set.");
    }

    @ReactMethod
    public void setAdId(String adId) {
        interstitialAd.setAdId(adId);
        Log.i(TAG, "adId is set.");
        Log.i(TAG, "setAdId() is called.");
    }

    @ReactMethod
    public void loadAd() {
        new Handler(Looper.getMainLooper()).post(() -> {
            interstitialAd.loadAd(ReactUtils.getAdParamFromReadableMap(mAdParamReadableMap));
            Log.i(TAG, "loadAd() is called.");
        });
    }

    @ReactMethod
    public void show() {
        new Handler(Looper.getMainLooper()).post(() -> {
            interstitialAd.show();
            Log.i(TAG, "show() is called.");
        });
    }

    @ReactMethod
    public void isLoaded(final Promise promise) {
        new Handler(Looper.getMainLooper()).post(() -> {
            promise.resolve(interstitialAd.isLoaded());
            Log.i(TAG, "isLoaded() is called.");
        });
    }
}
