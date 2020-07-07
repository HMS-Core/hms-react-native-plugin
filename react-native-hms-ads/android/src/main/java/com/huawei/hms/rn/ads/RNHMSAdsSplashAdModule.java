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

import com.huawei.hms.rn.ads.utils.ReactUtils;
import com.huawei.hms.rn.ads.utils.ResourceUtils;

public class RNHMSAdsSplashAdModule extends ReactContextBaseJavaModule {
    private static final String TAG = RNHMSAdsSplashAdModule.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static ReactApplicationContext mReactContext;

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

    public enum Event {
        AD_LOADED("adLoaded"),
        AD_FAILED_TO_LOAD("adFailedToLoad"),
        AD_DISMISSED("adDismissed"),
        AD_SHOWED("adShowed"),
        AD_CLICK("adClick");

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
        return "RNHMSAdsSplash";
    }

    RNHMSAdsSplashAdModule(ReactApplicationContext reactContext) {
        super(reactContext);
        initContext(reactContext);
    }

    private static void initContext(final ReactApplicationContext reactContext) {
        synchronized (RNHMSAdsSplashAdModule.class) {
            mReactContext = reactContext;
        }
    }

    static void sendEvent(Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event.getName(), wm);
    }

    @ReactMethod
    public void setAdId(String adId) {
        mAdId = adId;
        Log.i(TAG, "adId is set.");
    }

    @ReactMethod
    public void setAdParam(ReadableMap adParamReadableMap) {
        mAdParamReadableMap = adParamReadableMap;
        Log.i(TAG, "adParam is set.");
    }

    @ReactMethod
    public void setOrientation(int orientation) {
        mOrientation = orientation;
        Log.i(TAG, "orientation is set.");
    }

    @ReactMethod
    public void setSloganResource(String sloganResName) {
        mSloganResId = ResourceUtils.getLogoResourceIdFromContext(mReactContext, sloganResName);
        Log.i(TAG, "sloganResId is set.");
    }

    @ReactMethod
    public void setWideSloganResource(String wideSloganResName) {
        mWideSloganResId = ResourceUtils.getLogoResourceIdFromContext(mReactContext, wideSloganResName);
        Log.i(TAG, "wideSloganResId is set.");
    }

    @ReactMethod
    public void setLogoResource(String logoResName) {
        mLogoResId = ResourceUtils.getLogoResourceIdFromContext(mReactContext, logoResName);
        Log.i(TAG, "logoResId is set.");
    }

    @ReactMethod
    public void setLogoText(String logoText) {
        mLogoText = logoText;
        Log.i(TAG, "logoText is set.");
    }

    @ReactMethod
    public void setCopyrightText(String copyrightText) {
        mCopyrightText = copyrightText;
        Log.i(TAG, "copyrightText is set.");
    }

    @ReactMethod
    public void setMediaNameResource(String mediaNameResName) {
        mMediaNameResId = ResourceUtils.getStringResourceIdFromContext(mReactContext, mediaNameResName);
        Log.i(TAG, "mediaNameResId is set.");
    }

    @ReactMethod
    public void setAudioFocusType(int audioFocusType) {
        mAudioFocusType = audioFocusType;
        Log.i(TAG, "audioFocusType is set.");
    }

    @ReactMethod
    public void pause() {
        RNHMSAdsSplashActivity.pause();
    }

    @ReactMethod
    public void resume() {
        RNHMSAdsSplashActivity.resume();
    }

    @ReactMethod
    public void destroy() {
        RNHMSAdsSplashActivity.destroy();
    }

    @ReactMethod
    public void isLoading(final Promise promise) {
        RNHMSAdsSplashActivity.isLoading(promise);
    }

    @ReactMethod
    public void isLoaded(final Promise promise) {
        RNHMSAdsSplashActivity.isLoaded(promise);
    }

    @ReactMethod
    public void show() {
        Activity mActivity = getCurrentActivity();
        if (mActivity != null) {
            Intent intent = new Intent(mActivity, RNHMSAdsSplashActivity.class);
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
            mActivity.startActivity(intent);
            Log.i(TAG, "startActivity() is called, starting new splash activity...");
        }
    }
}