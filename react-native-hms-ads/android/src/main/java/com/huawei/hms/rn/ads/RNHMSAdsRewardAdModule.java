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
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.rn.ads.utils.ReactUtils;
import com.huawei.hms.ads.reward.Reward;
import com.huawei.hms.ads.reward.RewardAd;
import com.huawei.hms.ads.reward.RewardAdLoadListener;
import com.huawei.hms.ads.reward.RewardAdStatusListener;
import com.huawei.hms.ads.reward.RewardVerifyConfig;

public class RNHMSAdsRewardAdModule extends ReactContextBaseJavaModule {
    private static final String TAG = RNHMSAdsRewardAdModule.class.getSimpleName();
    private ReactApplicationContext mReactContext;
    private RewardAd mRewardAd;
    private ReadableMap mAdParamReadableMap;
    private RewardVerifyConfig mRewardVerifyConfig = new RewardVerifyConfig.Builder().build();
    private String mAdId = "testx9dtjwj8hp";
    private String mUserId;
    private String mData;

    public enum Event {
        AD_LOADED("adLoaded"),
        AD_FAILED_TO_LOAD("adFailedToLoad"),
        AD_FAILED_TO_SHOW("adFailedToShow"),
        AD_OPENED("adOpened"),
        AD_CLOSED("adClosed"),
        AD_REWARDED("adRewarded");

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
        return "RNHMSAdsRewardAd";
    }

    RNHMSAdsRewardAdModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
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
        mAdId = adId;
        Log.i(TAG, "adId is set.");
    }

    @ReactMethod
    public void setData(String data) {
        mData = data;
        Log.i(TAG, "data is set.");
    }

    @ReactMethod
    public void setUserId(String userId) {
        mUserId = userId;
        Log.i(TAG, "userId is set.");
    }

    @ReactMethod
    public void pause() {
        if (mRewardAd != null) {
            mRewardAd.pause();
            Log.i(TAG, "pause() is called");
        }
    }

    @ReactMethod
    public void resume() {
        if (mRewardAd != null) {
            mRewardAd.resume();
            Log.i(TAG, "resume() is called");
        }
    }

    @ReactMethod
    public void setVerifyConfig(ReadableMap config) {
        Log.i(TAG, "RewardVerifyConfig object is being created...");
        RewardVerifyConfig.Builder builder = new RewardVerifyConfig.Builder();
        if (ReactUtils.hasValidKey(config, "userId", ReadableType.String)) {
            builder.setUserId(config.getString("userId"));
            Log.i(TAG, "userId attribute is set.");
        }
        if (ReactUtils.hasValidKey(config, "data", ReadableType.String)) {
            builder.setData(config.getString("data"));
            Log.i(TAG, "data attribute is set.");
        }
        mRewardVerifyConfig = builder.build();
        Log.i(TAG, "RewardVerifyConfig object is created.");
    }

    @ReactMethod
    public void loadAd() {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (mRewardAd != null) {
                mRewardAd.destroy();
                Log.i(TAG, "destroy() is called");
            }
            mRewardAd = new RewardAd(mReactContext, mAdId);
            Log.i(TAG, "RewardAd object is created with given adId");
            if (mUserId != null) {
                mRewardAd.setUserId(mUserId);
                Log.i(TAG, "setUserId() is called");
            }
            if (mData != null) {
                mRewardAd.setData(mData);
                Log.i(TAG, "setData() is called");
            }
            mRewardAd.setRewardVerifyConfig(mRewardVerifyConfig);
            Log.i(TAG, "setRewardVerifyConfig() is called");
            mRewardAd.loadAd(ReactUtils.getAdParamFromReadableMap(mAdParamReadableMap), new RewardAdLoadListener() {
                @Override
                public void onRewardAdFailedToLoad(int errorCode) {
                    WritableMap wm = new WritableNativeMap();
                    wm.putInt("errorCode", errorCode);
                    wm.putString("errorMessage", RNHMSAdsModule.getErrorMessage(errorCode));
                    sendEvent(Event.AD_FAILED_TO_LOAD, wm);
                }

                @Override
                public void onRewardedLoaded() {
                    sendEvent(Event.AD_LOADED, ReactUtils.getWritableMapFromRewardAd(mRewardAd));
                }
            });
            Log.i(TAG, "RewardAdLoadListener object is created");
            Log.i(TAG, "loadAd() is called");
        });
    }

    @ReactMethod
    public void show() {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (mRewardAd != null && mRewardAd.isLoaded()) {
                mRewardAd.show(mReactContext.getCurrentActivity(), new RewardAdStatusListener() {
                    @Override
                    public void onRewardAdClosed() {
                        sendEvent(Event.AD_CLOSED, null);
                    }

                    @Override
                    public void onRewardAdFailedToShow(int errorCode) {
                        WritableMap wm = new WritableNativeMap();
                        wm.putInt("errorCode", errorCode);
                        wm.putString("errorMessage", getErrorMessage(errorCode));
                        sendEvent(Event.AD_FAILED_TO_SHOW, wm);
                    }

                    @Override
                    public void onRewardAdOpened() {
                        sendEvent(Event.AD_OPENED, null);
                    }

                    @Override
                    public void onRewarded(Reward reward) {
                        sendEvent(Event.AD_REWARDED, ReactUtils.getWritableMapFromReward(reward));
                    }
                });
                Log.i(TAG, "RewardAdStatusListener object is created");
                Log.i(TAG, "show() is called");
            }
        });
    }

    @ReactMethod
    public void isLoaded(final Promise promise) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (mRewardAd != null) {
                promise.resolve(mRewardAd.isLoaded());
                Log.i(TAG, "isLoaded() is called");
            } else {
                promise.resolve(false);
            }
        });
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