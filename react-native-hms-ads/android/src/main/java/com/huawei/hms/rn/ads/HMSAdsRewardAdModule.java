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

import android.util.Log;
import android.os.Handler;
import android.os.Looper;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.reward.Reward;
import com.huawei.hms.ads.reward.RewardAdListener;
import com.huawei.hms.rn.ads.logger.HMSLogger;
import com.huawei.hms.rn.ads.utils.ReactUtils;
import com.huawei.hms.ads.reward.RewardAd;
import com.huawei.hms.ads.reward.RewardAdLoadListener;
import com.huawei.hms.ads.reward.RewardAdStatusListener;
import com.huawei.hms.ads.reward.RewardVerifyConfig;

import static com.huawei.hms.rn.ads.utils.ReactUtils.hasValidKey;

public class HMSAdsRewardAdModule extends ReactContextBaseJavaModule {
    private static final String TAG = HMSAdsRewardAdModule.class.getSimpleName();

    private ReactApplicationContext mReactContext;

    private HMSLogger hmsLogger;

    private RewardAd mRewardAd;

    private ReadableMap mAdParamReadableMap;

    private RewardVerifyConfig mRewardVerifyConfig;

    private boolean mLoadWithAdId;

    private String mAdId;

    private String mUserId;

    private String mData;

    private RewardAdLoadListener mAdLoadListener;

    private RewardAdStatusListener mAdStatusListener;

    private RewardAdListener mAdListener;

    public enum RewardMediaType {
        VIDEO("video");

        private String value;

        RewardMediaType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Event {
        AD_LOADED("adLoaded"),
        AD_FAILED_TO_LOAD("adFailedToLoad"),
        AD_FAILED_TO_SHOW("adFailedToShow"),
        AD_OPENED("adOpened"),
        AD_CLOSED("adClosed"),
        AD_REWARDED("adRewarded");

        private String rewardEventName;

        Event(String rewardEventName) {
            this.rewardEventName = rewardEventName;
        }

        public String getName() {
            return rewardEventName;
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSAdsRewardAd";
    }

    private void sendEvent(Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event.getName(), wm);
    }

    HMSAdsRewardAdModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
        mAdLoadListener = new RewardAdLoadListener() {
            @Override
            public void onRewardAdFailedToLoad(int errorCode) {
                hmsLogger.sendSingleEvent("rewardAd.loadAd", String.valueOf(errorCode));
                sendEvent(Event.AD_FAILED_TO_LOAD, ReactUtils.getWritableMapFromErrorCode(errorCode));
            }

            @Override
            public void onRewardedLoaded() {
                hmsLogger.sendSingleEvent("rewardAd.loadAd");
                sendEvent(Event.AD_LOADED, ReactUtils.getWritableMapFromRewardAd(mRewardAd));
            }
        };
        mAdStatusListener = new RewardAdStatusListener() {
            @Override
            public void onRewardAdClosed() {
                sendEvent(Event.AD_CLOSED, null);
            }

            @Override
            public void onRewardAdFailedToShow(int errorCode) {
                hmsLogger.sendSingleEvent("rewardAd.show", String.valueOf(errorCode));
                sendEvent(Event.AD_FAILED_TO_SHOW, getWritableMapFromErrorCode(errorCode));
            }

            @Override
            public void onRewardAdOpened() {
                hmsLogger.sendSingleEvent("rewardAd.show");
                sendEvent(Event.AD_OPENED, null);
            }

            @Override
            public void onRewarded(Reward reward) {
                sendEvent(Event.AD_REWARDED, ReactUtils.getWritableMapFromReward(reward));
            }
        };
        mAdListener = new RewardAdListener() {
            @Override
            public void onRewardAdFailedToLoad(int i) {
                mAdLoadListener.onRewardAdFailedToLoad(i);
            }

            @Override
            public void onRewardAdLoaded() {
                mAdLoadListener.onRewardedLoaded();
            }

            @Override
            public void onRewardAdClosed() {
                mAdStatusListener.onRewardAdClosed();
            }

            @Override
            public void onRewardAdOpened() {
                mAdStatusListener.onRewardAdOpened();
            }

            @Override
            public void onRewarded(Reward reward) {
                mAdStatusListener.onRewarded(reward);
            }

            @Override
            public void onRewardAdStarted() {
            }

            @Override
            public void onRewardAdLeftApp() {
            }

            @Override
            public void onRewardAdCompleted() {
            }
        };
        hmsLogger = HMSLogger.getInstance(reactContext);
    }

    private WritableMap getWritableMapFromErrorCode(int errorCode) {
        WritableMap wm = new WritableNativeMap();
        wm.putInt("errorCode", errorCode);
        wm.putString("errorMessage", getErrorMessage(errorCode));
        return wm;
    }

    @ReactMethod
    public void setAdParam(ReadableMap rm, final Promise promise) {
        hmsLogger.sendSingleEvent("rewardAd.setAdParam");
        mAdParamReadableMap = rm;
        promise.resolve(null);
    }

    @ReactMethod
    public void loadWithAdId(boolean loadWithAdId, final Promise promise) {
        hmsLogger.sendSingleEvent("rewardAd.loadWithAdId");
        mLoadWithAdId = loadWithAdId;
        promise.resolve(null);
    }

    @ReactMethod
    public void setAdId(String adId, final Promise promise) {
        hmsLogger.sendSingleEvent("rewardAd.setAdId");
        mAdId = adId;
        promise.resolve(null);
    }

    @ReactMethod
    public void setData(String data, final Promise promise) {
        hmsLogger.sendSingleEvent("rewardAd.setData");
        mData = data;
        promise.resolve(null);
    }

    @ReactMethod
    public void setUserId(String userId, final Promise promise) {
        hmsLogger.sendSingleEvent("rewardAd.setUserId");
        mUserId = userId;
        promise.resolve(null);
    }

    @ReactMethod
    public void pause(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("rewardAd.pause");
        if (mRewardAd == null) {
            hmsLogger.sendSingleEvent("rewardAd.pause", "-1");
            promise.reject("AD_NOT_CREATED", "Reward ad is not created");
            return;
        }
        mRewardAd.pause();
        hmsLogger.sendSingleEvent("rewardAd.pause");
        promise.resolve(null);
    }

    @ReactMethod
    public void resume(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("rewardAd.resume");
        if (mRewardAd == null) {
            hmsLogger.sendSingleEvent("rewardAd.resume", "-1");
            promise.reject("AD_NOT_CREATED", "Reward ad is not created");
            return;
        }
        mRewardAd.resume();
        hmsLogger.sendSingleEvent("rewardAd.resume");
        promise.resolve(null);
    }

    @ReactMethod
    public void destroy(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("rewardAd.destroy");
        if (mRewardAd == null) {
            hmsLogger.sendSingleEvent("rewardAd.destroy", "-1");
            promise.reject("AD_NOT_CREATED", "Reward ad is not created");
            return;
        }
        mRewardAd.destroy();
        hmsLogger.sendSingleEvent("rewardAd.destroy");
        promise.resolve(null);
    }

    @ReactMethod
    public void setVerifyConfig(final ReadableMap config, final Promise promise) {
        RewardVerifyConfig.Builder builder = new RewardVerifyConfig.Builder();
        if (hasValidKey(config, "userId", ReadableType.String)) {
            builder.setUserId(config.getString("userId"));
        }
        if (hasValidKey(config, "data", ReadableType.String)) {
            builder.setData(config.getString("data"));
        }
        mRewardVerifyConfig = builder.build();
        hmsLogger.sendSingleEvent("rewardAd.setVerifyConfig");
        promise.resolve(null);
    }

    @ReactMethod
    public void loadAd(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("rewardAd.loadAd");
        new Handler(Looper.getMainLooper()).post(() -> {
            if (mRewardAd != null) {
                mRewardAd.destroy();
            }
            mRewardAd = new RewardAd(mReactContext, mAdId);

            if (mUserId != null) {
                mRewardAd.setUserId(mUserId);
            }
            if (mData != null) {
                mRewardAd.setData(mData);
            }
            if (mRewardVerifyConfig != null) {
                mRewardAd.setRewardVerifyConfig(mRewardVerifyConfig);
            }

            AdParam adParam = ReactUtils.getAdParamFromReadableMap(mAdParamReadableMap);
            if (mLoadWithAdId) {
                mRewardAd.setRewardAdListener(mAdListener);
                mRewardAd.loadAd(mAdId, adParam);
            } else {
                mRewardAd.loadAd(adParam, mAdLoadListener);
            }
            promise.resolve(null);
        });
    }

    @ReactMethod
    public void show(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("rewardAd.show");
        new Handler(Looper.getMainLooper()).post(() -> {
            if (mRewardAd == null) {
                promise.reject("AD_NOT_CREATED", "Reward ad is not created");
                hmsLogger.sendSingleEvent("rewardAd.show", "-1");
                return;
            }
            if (!mRewardAd.isLoaded()) {
                promise.reject("AD_NOT_LOADED", "Reward ad is not loaded");
                hmsLogger.sendSingleEvent("rewardAd.show", "-1");
                return;
            }
            if (mLoadWithAdId) {
                mRewardAd.show(mReactContext.getCurrentActivity());
            } else {
                mRewardAd.show(mReactContext.getCurrentActivity(), mAdStatusListener);
            }
            promise.resolve(null);
        });
    }

    @ReactMethod
    public void isLoaded(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("rewardAd.isLoaded");
        promise.resolve(mRewardAd != null && mRewardAd.isLoaded());
        hmsLogger.sendSingleEvent("rewardAd.isLoaded");
    }

    static String getErrorMessage(int errorCode) {
        switch (errorCode) {
            case RewardAdStatusListener.ErrorCode.INTERNAL:
                return "Internal error.";
            case RewardAdStatusListener.ErrorCode.REUSED:
                return "The rewarded ad has been displayed.";
            case RewardAdStatusListener.ErrorCode.NOT_LOADED:
                return "The ad has not been loaded.";
            case RewardAdStatusListener.ErrorCode.BACKGROUND:
                return "An activity of playing a rewarded ad is performed in the background.";
        }
        return "Unknown error";
    }
}