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

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.huawei.hms.ads.AudioFocusType;
import com.huawei.hms.ads.splash.SplashAdDisplayListener;
import com.huawei.hms.ads.splash.SplashView;

import com.huawei.hms.rn.ads.utils.ReactUtils;

import static com.huawei.hms.rn.ads.RNHMSAdsSplashAdModule.Event;
import static com.huawei.hms.rn.ads.RNHMSAdsSplashAdModule.sendEvent;

public class RNHMSAdsSplashActivity extends ReactActivity {
    private static final String TAG = RNHMSAdsSplashActivity.class.getSimpleName();
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

    static void pause() {
        if (splashView != null) {
            splashView.pauseView();
            Log.i(TAG, "pauseView() is called");
        }
    }

    static void resume() {
        if (splashView != null) {
            splashView.resumeView();
            Log.i(TAG, "resumeView() is called");
        }
    }

    static void destroy() {
        if (splashView != null) {
            splashView.destroyView();
            Log.i(TAG, "destroyView() is called");
        }
    }

    static void isLoading(final Promise promise) {
        if (splashView != null) {
            promise.resolve(splashView.isLoading());
            Log.i(TAG, "isLoading() is called");
        } else {
            promise.reject("AD_NOT_LOADED", "Splash is not loaded");
        }
    }

    static void isLoaded(final Promise promise) {
        if (splashView != null) {
            promise.resolve(splashView.isLoaded());
            Log.i(TAG, "isLoaded() is called");
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
            if (RNHMSAdsSplashActivity.this.hasWindowFocus()) {
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
        mOrientation = intent.getIntExtra("orientation", ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSloganResId = intent.getIntExtra("sloganResId", R.drawable.default_slogan);
        mLogoResId = intent.getIntExtra("logoResId", R.drawable.ic_launcher);
        mWideSloganResId = intent.getIntExtra("wideSloganResId", R.drawable.default_slogan);
        mMediaNameResId = intent.getIntExtra("mediaNameResId", R.string.media_name);
        mAudioFocusType = intent.getIntExtra("audioFocusType", AudioFocusType.NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE);
        mLogoText = intent.getStringExtra("logoText");
        mCopyrightText = intent.getStringExtra("copyrightText");
        mAdId = intent.getStringExtra("adId");
        mAdParamBundle = intent.getBundleExtra("adParam");
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
        Log.i(TAG, "SplashView is created");
        splashView.setAdDisplayListener(adDisplayListener);
        Log.i(TAG, "setAdDisplayListener() is called");

        // Set a default app launch image.
        splashView.setSloganResId(mSloganResId);
        Log.i(TAG, "setSloganResId() is called");
        // Set a default app launch image.
        splashView.setWideSloganResId(mWideSloganResId);
        Log.i(TAG, "setWideSloganResId() is called");
        // Set a logo image.
        splashView.setLogoResId(mLogoResId);
        Log.i(TAG, "setLogoResId() is called");
        // Set logo description.
        splashView.setMediaNameResId(mMediaNameResId);
        Log.i(TAG, "setMediaNameResId() is called");
        // Set the audio focus type for a video splash ad.
        splashView.setAudioFocusType(mAudioFocusType);
        Log.i(TAG, "setAudioFocusType() is called");

        splashView.load(mAdId, mOrientation,
                ReactUtils.getAdParamFromReadableMap(ReactUtils.getWritableMapFromAdParamBundle(mAdParamBundle)),
                splashAdLoadListener);
        Log.i(TAG, "load() is called");

        // Remove the timeout message from the message queue.
        timeoutHandler.removeMessages(MSG_AD_TIMEOUT);
        // Send a delay message to ensure that the app home screen can be displayed when the ad display times out.
        timeoutHandler.sendEmptyMessageDelayed(MSG_AD_TIMEOUT, AD_TIMEOUT);
    }

    /**
     * Switch from the splash ad screen to the app home screen when the ad display is complete.
     */
    private void jump() {
        Log.i(TAG, "jump hasPaused: " + hasPaused);
        if (!hasPaused) {
            hasPaused = true;
            Log.i(TAG, "jump into application");

            Handler mainHandler = new Handler();
            mainHandler.postDelayed(this::finish, 1000);
        }
    }

    /**
     * Set this parameter to true when exiting the app to ensure that the app home screen is not displayed.
     */
    @Override
    protected void onStop() {
        Log.i(TAG, "SplashActivity onStop.");
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
        Log.i(TAG, "SplashActivity onRestart.");
        super.onRestart();
        hasPaused = false;
        jump();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "SplashActivity onDestroy.");
        super.onDestroy();
        if (splashView != null) {
            splashView.destroyView();
        }
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "SplashActivity onPause.");
        super.onPause();
        if (splashView != null) {
            splashView.pauseView();
        }
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "SplashActivity onResume.");
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
            WritableMap wm = new WritableNativeMap();
            wm.putInt("errorCode", errorCode);
            wm.putString("errorMessage", RNHMSAdsModule.getErrorMessage(errorCode));
            sendEvent(Event.AD_FAILED_TO_LOAD, wm);
            jump();
        }

        @Override
        public void onAdDismissed() {
            sendEvent(Event.AD_DISMISSED, null);
            jump();
        }
    }
}
