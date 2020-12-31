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
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.InterstitialAd;

import com.huawei.hms.ads.reward.Reward;
import com.huawei.hms.ads.reward.RewardAdListener;
import com.huawei.hms.rn.ads.logger.HMSLogger;
import com.huawei.hms.rn.ads.utils.ReactUtils;

public class HMSAdsInterstitialAdModule extends ReactContextBaseJavaModule {
    private static final String TAG = HMSAdsInterstitialAdModule.class.getSimpleName();
    private ReactApplicationContext mReactContext;
    private HMSLogger hmsLogger;
    private InterstitialAd interstitialAd;

    private ReadableMap mAdParamReadableMap;
    private boolean mOnHMSCore;
    private String mAdId;
    private AdListener mAdListener;
    private final RewardAdListener mRewardAdListener;

    public enum InterstitialMediaType {
        IMAGE("image"),
        VIDEO("video");

        private String value;

        InterstitialMediaType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Event {
        AD_CLOSED("adClosed"),
        AD_FAILED("adFailed"),
        AD_LEAVE("adLeave"),
        AD_OPENED("adOpened"),
        AD_LOADED("adLoaded"),
        AD_CLICKED("adClicked"),
        AD_IMPRESSION("adImpression"),
        AD_COMPLETED("adCompleted"),
        AD_STARTED("adStarted");

        private String interstitialEventName;

        Event(String interstitialEventName) {
            this.interstitialEventName = interstitialEventName;
        }

        public String getName() {
            return interstitialEventName;
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSAdsInterstitial";
    }

    HMSAdsInterstitialAdModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
        mAdListener = new AdListener() {
            @Override
            public void onAdClosed() {
            }

            @Override
            public void onAdFailed(int errorCode) {
            }

            @Override
            public void onAdLeave() {
                sendEvent(Event.AD_LEAVE, null);
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClicked() {
                sendEvent(Event.AD_CLICKED, null);
            }

            @Override
            public void onAdImpression() {
                sendEvent(Event.AD_IMPRESSION, null);
            }
        };
        mRewardAdListener = new RewardAdListener() {
            @Override
            public void onRewarded(Reward reward) {
            }

            @Override
            public void onRewardAdClosed() {
                sendEvent(Event.AD_CLOSED, null);
            }

            @Override
            public void onRewardAdFailedToLoad(int errorCode) {
                sendEvent(Event.AD_FAILED, ReactUtils.getWritableMapFromErrorCode(errorCode));
            }

            @Override
            public void onRewardAdLeftApp() {
            }

            @Override
            public void onRewardAdLoaded() {
                sendEvent(Event.AD_LOADED, ReactUtils.getWritableMapFromInterstitialAd(interstitialAd));
            }

            @Override
            public void onRewardAdOpened() {
                sendEvent(Event.AD_OPENED, null);
            }

            @Override
            public void onRewardAdCompleted() {
                sendEvent(Event.AD_COMPLETED, null);
            }

            @Override
            public void onRewardAdStarted() {
                sendEvent(Event.AD_STARTED, null);
            }
        };
        hmsLogger = HMSLogger.getInstance(reactContext);
    }

    private void sendEvent(Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event.getName(), wm);
    }

    @ReactMethod
    public void setAdId(final String adId, final Promise promise) {
        mAdId = adId;
        hmsLogger.sendSingleEvent("interstitialAd.setAdId");
        promise.resolve(null);
    }

    @ReactMethod
    public void onHMSCore(final boolean onHMSCore, final Promise promise) {
        hmsLogger.sendSingleEvent("rewardAd.onHMSCore");
        mOnHMSCore = onHMSCore;
        promise.resolve(null);
    }

    @ReactMethod
    public void setAdParam(final ReadableMap rm, final Promise promise) {
        mAdParamReadableMap = rm;
        hmsLogger.sendSingleEvent("interstitialAd.setAdParam");
        promise.resolve(null);
    }

    @ReactMethod
    public void loadAd(final Promise promise) {
        new Handler(Looper.getMainLooper()).post(() -> {
            interstitialAd = new InterstitialAd(mOnHMSCore ? mReactContext : mReactContext.getCurrentActivity());
            interstitialAd.setAdListener(mAdListener);
            interstitialAd.setRewardAdListener(mRewardAdListener);
            interstitialAd.setAdId(mAdId);
            AdParam adParam = ReactUtils.getAdParamFromReadableMap(mAdParamReadableMap);
            hmsLogger.startMethodExecutionTimer("interstitialAd.loadAd");
            interstitialAd.loadAd(adParam);
            hmsLogger.sendSingleEvent("interstitialAd.loadAd");
            promise.resolve(null);
        });
    }

    @ReactMethod
    public void show(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("interstitialAd.show");
        new Handler(Looper.getMainLooper()).post(() -> {
            if (interstitialAd == null) {
                promise.reject("AD_NOT_CREATED", "Interstitial ad is not created");
                hmsLogger.sendSingleEvent("interstitialAd.show", "-1");
                return;
            }
            interstitialAd.show();
            hmsLogger.sendSingleEvent("interstitialAd.show");
            promise.resolve(null);
        });
    }

    @ReactMethod
    public void isLoaded(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("interstitialAd.isLoaded");
        promise.resolve(interstitialAd != null && interstitialAd.isLoaded());
        hmsLogger.sendSingleEvent("interstitialAd.isLoaded");
    }

    @ReactMethod
    public void isLoading(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("interstitialAd.isLoading");
        promise.resolve(interstitialAd != null && interstitialAd.isLoading());
        hmsLogger.sendSingleEvent("interstitialAd.isLoading");
    }
}
