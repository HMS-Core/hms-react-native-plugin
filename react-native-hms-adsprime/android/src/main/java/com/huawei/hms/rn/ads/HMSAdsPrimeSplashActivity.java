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

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Promise;

import com.huawei.hms.ads.AudioFocusType;
import com.huawei.hms.ads.splash.SplashAdDisplayListener;
import com.huawei.hms.ads.splash.SplashView;

import com.huawei.hms.rn.ads.utils.CommonUtils;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import static com.huawei.hms.rn.ads.HMSAdsPrimeSplashAdModule.Event;
import static com.huawei.hms.rn.ads.HMSAdsPrimeSplashAdModule.sendEvent;

public class HMSAdsPrimeSplashActivity extends ReactActivity {
    private static final String TAG = HMSAdsPrimeSplashActivity.class.getSimpleName();

    private static final int AD_TIMEOUT = 5000;

    private static final int MSG_AD_TIMEOUT = 1001;

    @SuppressLint("StaticFieldLeak")
    private static SplashView splashView;

    private String mAdId;

    private String mLogoText;

    private String mCopyrightText;

    private int mOrientation;

    private int mSloganResId;

    private int mWideSloganResId;

    private int mLogoResId;

    private int mMediaNameResId;

    private int mAudioFocusType;

    private Bundle mAdParamBundle;

    private SplashView.SplashAdLoadListener splashAdLoadListener = new SplashAdLoadListener();

    private SplashAdDisplayListenerInner adDisplayListener = new SplashAdDisplayListenerInner();

    static void pause(final Promise promise) {
        if (splashView != null) {
            splashView.pauseView();
            promise.resolve(null);
        } else {
            promise.reject("AD_NOT_LOADED", "Splash is not loaded");
        }
    }

    static void resume(final Promise promise) {
        if (splashView != null) {
            splashView.resumeView();
            promise.resolve(null);
        } else {
            promise.reject("AD_NOT_LOADED", "Splash is not loaded");
        }
    }

    static void destroy(final Promise promise) {
        if (splashView != null) {
            splashView.destroyView();
            promise.resolve(null);
        } else {
            promise.reject("AD_NOT_LOADED", "Splash is not loaded");
        }
    }

    static void isLoading(final Promise promise) {
        if (splashView != null) {
            promise.resolve(splashView.isLoading());
        } else {
            promise.reject("AD_NOT_LOADED", "Splash is not loaded");
        }
    }

    static void isLoaded(final Promise promise) {
        if (splashView != null) {
            promise.resolve(splashView.isLoaded());
        } else {
            promise.reject("AD_NOT_LOADED", "Splash is not loaded");
        }
    }

    /**
     * Pause flag.
     * On the splash ad screen:
     * Set this parameter to true when exiting the app to ensure that the app home screen is not displayed.
     * Set this parameter to false when returning to the splash ad screen from another screen to ensure that
     * the app home screen can be displayed properly.
     */
    private boolean hasPaused = false;

    // Callback handler used when the ad display timeout message is received.
    private Handler timeoutHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (HMSAdsPrimeSplashActivity.this.hasWindowFocus()) {
                jump();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = getIntent();
        mOrientation = CommonUtils.GetIntegerExtra(intent,"orientation", ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSloganResId = CommonUtils.GetIntegerExtra(intent, "sloganResId", R.drawable.default_slogan);
        mLogoResId = CommonUtils.GetIntegerExtra(intent, "logoResId", R.drawable.ic_launcher);
        mWideSloganResId = CommonUtils.GetIntegerExtra(intent, "wideSloganResId", R.drawable.default_slogan);
        mMediaNameResId = CommonUtils.GetIntegerExtra(intent, "mediaNameResId", 2131493009);
        mAudioFocusType = CommonUtils.GetIntegerExtra(intent, "audioFocusType", AudioFocusType.NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE);
        mLogoText = CommonUtils.GetStringExtra(intent, "logoText");
        mCopyrightText = CommonUtils.GetStringExtra(intent,"copyrightText");
        mAdId = CommonUtils.GetStringExtra(intent,"adId");
        mAdParamBundle = CommonUtils.GetBundleExtra(intent,"adParam");
        loadAd();
    }

    private void loadAd() {
        View mLogoTextView = findViewById(R.id.text_logo);
        if (mLogoTextView instanceof TextView) {
            ((TextView) mLogoTextView).setText(mLogoText);
        }

        View mCopyrightTextView = findViewById(R.id.text_copyright);
        if (mCopyrightTextView instanceof TextView) {
            ((TextView) mCopyrightTextView).setText(mCopyrightText);
        }

        View mLogoImageView = findViewById(R.id.image_logo);
        if (mLogoImageView instanceof ImageView) {
            ((ImageView) mLogoImageView).setImageResource(mLogoResId);
        }

        splashView = findViewById(R.id.splash_ad_view);
        splashView.setAdDisplayListener(adDisplayListener);

        // Set a default app launch image.
        splashView.setSloganResId(mSloganResId);
        // Set a default app launch image.
        splashView.setWideSloganResId(mWideSloganResId);
        // Set a logo image.
        splashView.setLogoResId(mLogoResId);
        // Set logo description.
        splashView.setMediaNameResId(mMediaNameResId);
        // Set the audio focus type for a video splash ad.
        splashView.setAudioFocusType(mAudioFocusType);

        splashView.load(mAdId, mOrientation,
            ReactUtils.getAdParamFromReadableMap(ReactUtils.getWritableMapFromAdParamBundle(mAdParamBundle)),
            splashAdLoadListener);

        // Remove the timeout message from the message queue.
        timeoutHandler.removeMessages(MSG_AD_TIMEOUT);
        // Send a delay message to ensure that the app home screen can be displayed when the ad display times out.
        timeoutHandler.sendEmptyMessageDelayed(MSG_AD_TIMEOUT, AD_TIMEOUT);
    }

    /**
     * Switch from the splash ad screen to the app home screen when the ad display is complete.
     */
    private void jump() {
        if (!hasPaused) {
            hasPaused = true;
            Handler mainHandler = new Handler();
            mainHandler.postDelayed(this::finish, 1000);
        }
    }

    /**
     * Set this parameter to true when exiting the app to ensure that the app home screen is not displayed.
     */
    @Override
    protected void onStop() {
        // Remove the timeout message from the message queue.
        timeoutHandler.removeMessages(MSG_AD_TIMEOUT);
        hasPaused = true;
        super.onStop();
    }

    /**
     * Call this method when returning to the splash ad screen from another screen to access the app home screen.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        hasPaused = false;
        jump();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (splashView != null) {
            splashView.destroyView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (splashView != null) {
            splashView.pauseView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (splashView != null) {
            splashView.resumeView();
        }
    }

    private static class SplashAdDisplayListenerInner extends SplashAdDisplayListener {
        @Override
        public void onAdClick() {
            sendEvent(Event.AD_CLICK, null);
        }

        @Override
        public void onAdShowed() {
            sendEvent(Event.AD_SHOWED, null);
        }
    }

    private class SplashAdLoadListener extends SplashView.SplashAdLoadListener {
        @Override
        public void onAdLoaded() {
            sendEvent(Event.AD_LOADED, null);
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            sendEvent(Event.AD_FAILED_TO_LOAD, ReactUtils.getWritableMapFromErrorCode(errorCode));
            jump();
        }

        @Override
        public void onAdDismissed() {
            sendEvent(Event.AD_DISMISSED, null);
            jump();
        }
    }
}
