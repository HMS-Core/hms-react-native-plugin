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

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.huawei.hms.ads.AudioFocusType;

import com.huawei.hms.rn.ads.logger.HMSLogger;
import com.huawei.hms.rn.ads.utils.ReactUtils;
import com.huawei.hms.rn.ads.utils.ResourceUtils;

public class HMSAdsSplashAdModule extends ReactContextBaseJavaModule {
    private static final String TAG = HMSAdsSplashAdModule.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static ReactApplicationContext mReactContext;
    private HMSLogger hmsLogger;

    private ReadableMap mAdParamReadableMap;
    private int mOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private int mSloganResId = R.drawable.default_slogan;
    private int mWideSloganResId = R.drawable.default_slogan;
    private int mLogoResId = R.drawable.ic_launcher;
    private int mMediaNameResId = R.string.media_name;
    private int mAudioFocusType = AudioFocusType.NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE;
    private String mAdId = "testd7c5cewoj6";
    private String mLogoText = "Huawei Developer";
    private String mCopyrightText = "Copyright 2020. Huawei Technologies Co., Ltd";

    public enum SplashMediaType {
        IMAGE("image"),
        VIDEO("video");

        private String value;

        SplashMediaType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Event {
        AD_LOADED("adLoaded"),
        AD_FAILED_TO_LOAD("adFailedToLoad"),
        AD_DISMISSED("adDismissed"),
        AD_SHOWED("adShowed"),
        AD_CLICK("adClick");

        private String splashEventName;

        Event(String splashEventName) {
            this.splashEventName = splashEventName;
        }

        public String getName() {
            return splashEventName;
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSAdsSplash";
    }

    HMSAdsSplashAdModule(ReactApplicationContext reactContext) {
        super(reactContext);
        hmsLogger = HMSLogger.getInstance(reactContext);
        initContext(reactContext);
    }

    private static void initContext(final ReactApplicationContext reactContext) {
        synchronized (HMSAdsSplashAdModule.class) {
            mReactContext = reactContext;
        }
    }

    static void sendEvent(Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event.getName(), wm);
    }

    @ReactMethod
    public void setAdId(final String adId, final Promise promise) {
        hmsLogger.sendSingleEvent("splashAd.setAdId");
        mAdId = adId;
        promise.resolve(null);
    }

    @ReactMethod
    public void setAdParam(final ReadableMap rm, final Promise promise) {
        hmsLogger.sendSingleEvent("splashAd.setAdParam");
        mAdParamReadableMap = rm;
        promise.resolve(null);
    }

    @ReactMethod
    public void setOrientation(final int orientation, final Promise promise) {
        hmsLogger.sendSingleEvent("splashAd.setOrientation");
        mOrientation = orientation;
        promise.resolve(null);
    }

    @ReactMethod
    public void setSloganResource(final String sloganResName, final Promise promise) {
        hmsLogger.sendSingleEvent("splashAd.setSloganResource");
        mSloganResId = ResourceUtils.getLogoResourceIdFromContext(mReactContext, sloganResName);
        promise.resolve(null);
    }

    @ReactMethod
    public void setWideSloganResource(final String wideSloganResName, final Promise promise) {
        hmsLogger.sendSingleEvent("splashAd.setWideSloganResource");
        mWideSloganResId = ResourceUtils.getLogoResourceIdFromContext(mReactContext, wideSloganResName);
        promise.resolve(null);
    }

    @ReactMethod
    public void setLogoResource(final String logoResName, final Promise promise) {
        hmsLogger.sendSingleEvent("splashAd.setLogoResource");
        mLogoResId = ResourceUtils.getLogoResourceIdFromContext(mReactContext, logoResName);
        promise.resolve(null);
    }

    @ReactMethod
    public void setLogoText(final String logoText, final Promise promise) {
        hmsLogger.sendSingleEvent("splashAd.setLogoText");
        mLogoText = logoText;
        promise.resolve(null);
    }

    @ReactMethod
    public void setCopyrightText(final String copyrightText, final Promise promise) {
        hmsLogger.sendSingleEvent("splashAd.setCopyrightText");
        mCopyrightText = copyrightText;
        promise.resolve(null);
    }

    @ReactMethod
    public void setMediaNameResource(final String mediaNameResName, final Promise promise) {
        hmsLogger.sendSingleEvent("splashAd.setMediaNameResource");
        mMediaNameResId = ResourceUtils.getStringResourceIdFromContext(mReactContext, mediaNameResName);
        promise.resolve(null);
    }

    @ReactMethod
    public void setAudioFocusType(int audioFocusType, final Promise promise) {
        hmsLogger.sendSingleEvent("splashAd.setAudioFocusType");
        mAudioFocusType = audioFocusType;
        promise.resolve(null);
    }

    @ReactMethod
    public void pause(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("splashAd.pause");
        HMSAdsSplashActivity.pause(promise);
        hmsLogger.sendSingleEvent("splashAd.pause");
    }

    @ReactMethod
    public void resume(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("splashAd.resume");
        HMSAdsSplashActivity.resume(promise);
        hmsLogger.sendSingleEvent("splashAd.resume");
    }

    @ReactMethod
    public void destroy(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("splashAd.destroy");
        HMSAdsSplashActivity.destroy(promise);
        hmsLogger.sendSingleEvent("splashAd.destroy");
    }

    @ReactMethod
    public void isLoading(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("splashAd.isLoading");
        HMSAdsSplashActivity.isLoading(promise);
        hmsLogger.sendSingleEvent("splashAd.isLoading");
    }

    @ReactMethod
    public void isLoaded(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("splashAd.isLoaded");
        HMSAdsSplashActivity.isLoaded(promise);
        hmsLogger.sendSingleEvent("splashAd.isLoaded");
    }

    @ReactMethod
    public void show(final Promise promise) {
        Activity mActivity = getCurrentActivity();
        if (mActivity == null) {
            promise.reject("NO_CURRENT_ACTIVITY", "Cannot get current activity");
            return;
        }
        Intent intent = new Intent(mActivity, HMSAdsSplashActivity.class);
        intent.putExtra("logoText", mLogoText);
        intent.putExtra("copyrightText", mCopyrightText);
        intent.putExtra("orientation", mOrientation);
        intent.putExtra("sloganResId", mSloganResId);
        intent.putExtra("logoResId", mLogoResId);
        intent.putExtra("wideSloganResId", mWideSloganResId);
        intent.putExtra("mediaNameResId", mMediaNameResId);
        intent.putExtra("audioFocusType", mAudioFocusType);
        intent.putExtra("adId", mAdId);
        intent.putExtra("adParam", ReactUtils.getBundleFromReadableMap(mAdParamReadableMap));
        hmsLogger.startMethodExecutionTimer("splashAd.show");
        mActivity.startActivity(intent);
        hmsLogger.sendSingleEvent("splashAd.show");
        Log.i(TAG, "startActivity() is called, starting new splash activity...");
        promise.resolve(null);
    }
}
