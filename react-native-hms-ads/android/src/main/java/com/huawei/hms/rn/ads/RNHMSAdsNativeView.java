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

import androidx.annotation.Nullable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.VideoConfiguration;
import com.huawei.hms.ads.VideoOperator;
import com.huawei.hms.ads.nativead.MediaView;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdConfiguration;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.huawei.hms.ads.nativead.NativeView;

import com.huawei.hms.rn.ads.utils.ReactUtils;
import com.huawei.hms.rn.ads.utils.ResourceUtils;

import java.util.HashMap;
import java.util.Map;

import com.huawei.hms.rn.ads.RNHMSAdsNativeViewManager.Event;

public class RNHMSAdsNativeView extends LinearLayout {
    private static final String TAG = RNHMSAdsNativeView.class.getSimpleName();
    ReactContext mReactContext;
    NativeView mNativeView;
    MediaView mMediaView;
    TextView mTitleView;
    TextView mDescriptionView;
    TextView mAdSourceView;
    TextView mFlagView;
    Button mCallToActionView;
    NativeAd mNativeAd;
    NativeAdConfiguration.Builder mNativeAdConfigurationBuilder;
    NativeAdConfiguration mNativeAdConfiguration;
    NativeAdLoader mNativeAdLoader;
    VideoConfiguration.Builder mVideoConfigurationBuilder;
    VideoConfiguration mVideoConfiguration;
    NativeAd.NativeAdLoadedListener mNativeAdLoadedListener;
    AdListener mAdListener;
    ReadableMap mAdParamReadableMap;
    private String mAdId = "testy63txaom86";
    private String mMediaType = "video";
    private int mLayoutId = R.layout.native_video_template;
    private NativeAdViewOptions mNativeAdViewOptions = new NativeAdViewOptions().build(null);

    public RNHMSAdsNativeView(Context context) {
        super(context);
        if (context instanceof ReactContext) {
            mReactContext = (ReactContext) context;
        }
        setupInitialConfigurations();
    }

    void setupInitialConfigurations() {
        mAdListener = new AdListener() {
            @Override
            public void onAdFailed(int errorCode) {
                WritableMap wm = new WritableNativeMap();
                wm.putInt("errorCode", errorCode);
                wm.putString("errorMessage", RNHMSAdsModule.getErrorMessage(errorCode));
                sendEvent(Event.AD_FAILED, wm);
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
        Log.i(TAG, "AdListener object is created");
        mNativeAdLoadedListener = nativeAd -> {
            Log.i(TAG, "NativeAd object is created and returned after calling loadAd() ");
            // Call this method when an ad is successfully loaded.
            WritableMap wm = new WritableNativeMap();
            wm.putMap("nativeAd", ReactUtils.getWritableMapFromNativeAd(nativeAd));
            wm.putMap("nativeAdConfiguration",
                    ReactUtils.getWritableMapFromNativeAdConfiguration(mNativeAdConfiguration));
            wm.putMap("nativeAdLoader", ReactUtils.getWritableMapFromNativeAdLoader(mNativeAdLoader));
            sendEvent(Event.NATIVE_AD_LOADED, wm);
            // Display native ad.
            showNativeAd(nativeAd);
            nativeAd.setDislikeAdListener(() -> {
                // Call this method when an ad is closed.
                sendEvent(Event.AD_DISLIKED, null);
            });
            Log.i(TAG, "DislikeAdListener object is created");
        };
        Log.i(TAG, "NativeAdLoadedListener object is created");

        mVideoConfigurationBuilder = new VideoConfiguration.Builder();
        mVideoConfiguration = mVideoConfigurationBuilder.build();
        Log.i(TAG, "VideoConfiguration object is created");

        mNativeAdConfigurationBuilder = new NativeAdConfiguration.Builder()
                .setVideoConfiguration(mVideoConfiguration);
        mNativeAdConfiguration = mNativeAdConfigurationBuilder.build();
        Log.i(TAG, "NativeAdConfiguration object is created");
    }

    void loadAd() {
        Log.i(TAG, "NativeAdLoader is being created...");
        mNativeAdLoader = new NativeAdLoader.Builder(mReactContext, mAdId)
                .setNativeAdLoadedListener(mNativeAdLoadedListener)
                .setAdListener(mAdListener)
                .setNativeAdOptions(mNativeAdConfiguration).build();
        Log.i(TAG, "setNativeAdLoadedListener() is called");
        Log.i(TAG, "setAdListener() is called");
        Log.i(TAG, "setNativeAdOptions() is called");
        Log.i(TAG, "NativeAdLoader object is created");
        mNativeAdLoader.loadAd(ReactUtils.getAdParamFromReadableMap(mAdParamReadableMap));
        Log.i(TAG, "loadAd() is called");
    }

    void setVideoConfiguration(ReadableMap videoConfiguration) {
        if (videoConfiguration == null) {
            return;
        }
        Log.i(TAG, "VideoConfiguration object is being created...");
        if (ReactUtils.hasValidKey(videoConfiguration, "audioFocusType", ReadableType.Number)) {
            mVideoConfigurationBuilder.setAudioFocusType(videoConfiguration.getInt("audioFocusType"));
            Log.i(TAG, "audioFocusType attribute is set.");
        }
        if (ReactUtils.hasValidKey(videoConfiguration, "clickToFullScreenRequested", ReadableType.Boolean)) {
            mVideoConfigurationBuilder.setClickToFullScreenRequested(videoConfiguration.getBoolean(
                    "clickToFullScreenRequested"));
            Log.i(TAG, "clickToFullScreenRequested attribute is set.");
        }
        if (ReactUtils.hasValidKey(videoConfiguration, "customizeOperateRequested", ReadableType.Boolean)) {
            mVideoConfigurationBuilder.setCustomizeOperateRequested(videoConfiguration.getBoolean(
                    "customizeOperateRequested"));
            Log.i(TAG, "customizeOperateRequested attribute is set.");
        }
        if (ReactUtils.hasValidKey(videoConfiguration, "startMuted", ReadableType.Boolean)) {
            mVideoConfigurationBuilder.setStartMuted(videoConfiguration.getBoolean(
                    "startMuted"));
            Log.i(TAG, "startMuted attribute is set.");
        }
        mVideoConfiguration = mVideoConfigurationBuilder.build();
        Log.i(TAG, "VideoConfiguration object is created.");
    }

    void setNativeAdConfiguration(ReadableMap nativeAdConfiguration) {
        if (nativeAdConfiguration != null) {
            Log.i(TAG, "NativeAdConfiguration object is being created...");
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "choicesPosition", ReadableType.Number)) {
                mNativeAdConfigurationBuilder.setChoicesPosition(nativeAdConfiguration.getInt("choicesPosition"));
                Log.i(TAG, "choicesPosition attribute is set.");
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "mediaDirection", ReadableType.Number)) {
                mNativeAdConfigurationBuilder.setMediaDirection(nativeAdConfiguration.getInt("mediaDirection"));
                Log.i(TAG, "mediaDirection attribute is set.");
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "mediaAspect", ReadableType.Number)) {
                mNativeAdConfigurationBuilder.setMediaAspect(nativeAdConfiguration.getInt("mediaAspect"));
                Log.i(TAG, "mediaAspect attribute is set.");
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "requestCustomDislikeThisAd", ReadableType.Boolean)) {
                mNativeAdConfigurationBuilder.setRequestCustomDislikeThisAd(
                        nativeAdConfiguration.getBoolean("requestCustomDislikeThisAd"));
                Log.i(TAG, "requestCustomDislikeThisAd attribute is set.");
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "requestMultiImages", ReadableType.Boolean)) {
                mNativeAdConfigurationBuilder.setRequestMultiImages(
                        nativeAdConfiguration.getBoolean("requestMultiImages"));
                Log.i(TAG, "requestMultiImages attribute is set.");
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "returnUrlsForImages", ReadableType.Boolean)) {
                mNativeAdConfigurationBuilder.setReturnUrlsForImages(
                        nativeAdConfiguration.getBoolean("returnUrlsForImages"));
                Log.i(TAG, "returnUrlsForImages attribute is set.");
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "adSize", ReadableType.Map)) {
                mNativeAdConfigurationBuilder.setAdSize(ReactUtils.getAdSizeFromReadableMap(
                        nativeAdConfiguration.getMap("adSize")));
                Log.i(TAG, "adSize attribute is set.");
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "videoConfiguration", ReadableType.Map)) {
                setVideoConfiguration(nativeAdConfiguration.getMap("videoConfiguration"));
                mNativeAdConfigurationBuilder.setVideoConfiguration(mVideoConfiguration);
                Log.i(TAG, "videoConfiguration attribute is set.");
            }
        }
        mNativeAdConfiguration = mNativeAdConfigurationBuilder.build();
        Log.i(TAG, "NativeAdConfiguration object is created.");
    }

    private final Runnable measureAndLayout = () -> {
        measure(
                MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
        layout(getLeft(), getTop(), getRight(), getBottom());
    };

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    private VideoOperator.VideoLifecycleListener videoLifecycleListener = new VideoOperator.VideoLifecycleListener() {
        @Override
        public void onVideoStart() {
            sendEvent(Event.AD_VIDEO_START, null);
        }

        @Override
        public void onVideoPlay() {
            sendEvent(Event.AD_VIDEO_PLAY, null);
        }

        @Override
        public void onVideoEnd() {
            sendEvent(Event.AD_VIDEO_END, null);
        }
    };

    private void showNativeAd(NativeAd nativeAd) {
        // Destroy the original native ad.
        if (null != mNativeAd) {
            mNativeAd.destroy();
            Log.i(TAG, "destroy() is called");
        }
        mNativeAd = nativeAd;

        LayoutInflater layoutInflater = LayoutInflater.from(mReactContext);

        View inflated = layoutInflater.inflate(mLayoutId, this, false);
        if (inflated instanceof NativeView) {
            // Obtain NativeView.
            mNativeView = (NativeView) inflated;
            Log.i(TAG, "NativeView object is created");
            // Register and populate a native ad material view.
            initNativeAdView(mNativeAd, mNativeView);

            this.removeAllViews();
            this.addView(mNativeView);
            this.requestLayout();
        }
    }

    private void sendEvent(Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), event.getName(), wm);
    }

    private void initNativeAdView(NativeAd nativeAd, NativeView nativeView) {
        mMediaView = nativeView.findViewById(R.id.ad_media);
        nativeView.setMediaView(mMediaView);
        Log.i(TAG, "setMediaView() is called");

        mTitleView = nativeView.findViewById(R.id.ad_title);
        nativeView.setTitleView(mTitleView);
        Log.i(TAG, "setTitleView() is called");

        mAdSourceView = nativeView.findViewById(R.id.ad_source);
        nativeView.setAdSourceView(mAdSourceView);
        Log.i(TAG, "setAdSourceView() is called");

        mCallToActionView = nativeView.findViewById(R.id.ad_call_to_action);
        nativeView.setCallToActionView(mCallToActionView);
        Log.i(TAG, "setCallToActionView() is called");

        mDescriptionView = nativeView.findViewById(R.id.ad_description);
        nativeView.setDescriptionView(mDescriptionView);
        Log.i(TAG, "setDescriptionView() is called");

        mFlagView = nativeView.findViewById(R.id.ad_flag);

//         Populate a native ad material view.
        if (nativeAd.getTitle() != null) {
            mTitleView.setText(nativeAd.getTitle());
        }
        if (nativeAd.getMediaContent() != null) {
            mMediaView.setMediaContent(nativeAd.getMediaContent());
        }

        if (null != nativeAd.getAdSource()) {
            mAdSourceView.setText(nativeAd.getAdSource());
            mAdSourceView.setVisibility(View.VISIBLE);
        } else {
            mAdSourceView.setVisibility(View.INVISIBLE);
        }

        if (null != nativeAd.getDescription()) {
            mDescriptionView.setText(nativeAd.getDescription());
            mDescriptionView.setVisibility(View.VISIBLE);
        } else {
            mDescriptionView.setVisibility(View.INVISIBLE);
        }

        if (null != nativeAd.getCallToAction()) {
            mCallToActionView.setText(nativeAd.getCallToAction());
            mCallToActionView.setVisibility(View.VISIBLE);
        } else {
            mCallToActionView.setVisibility(View.INVISIBLE);
        }

        // Obtain a video controller.
        VideoOperator videoOperator = nativeAd.getVideoOperator();
        Log.i(TAG, "VideoOperator object is created");

        // Check whether a native ad contains video materials.
        if (videoOperator.hasVideo()) {
            // Add a video lifecycle event listener.
            videoOperator.setVideoLifecycleListener(videoLifecycleListener);
            Log.i(TAG, "setVideoLifecycleListener() is called");
        }

        updateViewOptions();
        // Register a native ad object.
        nativeView.setNativeAd(nativeAd);
        Log.i(TAG, "setNativeAd() is called");
    }

    void setViewOptions(ReadableMap rm) {
        if (rm != null) {
            mNativeAdViewOptions = new NativeAdViewOptions().build(rm);
            updateViewOptions();
        }
    }

    Drawable toRoundedColor(int color, Float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(radius * Resources.getSystem().getDisplayMetrics().density);
        drawable.setColor(color);
        return drawable;
    }

    void updateViewOptions() {
        if (mMediaView != null) {
            mMediaView.setVisibility(mNativeAdViewOptions.showMediaContent ? View.VISIBLE : View.GONE);
            mMediaView.setImageScaleType(mNativeAdViewOptions.mediaImageScaleType);
        }

        if (mFlagView != null) {
            int flagBackgroundColor = (int) mNativeAdViewOptions.adFlagTextStyle.get(AdTextStyle.BACKGROUND_COLOR);
            if (flagBackgroundColor != 0) {
                mFlagView.setBackground(toRoundedColor(flagBackgroundColor, 20f));
            }
            mFlagView.setTextSize((float) mNativeAdViewOptions.adFlagTextStyle.get(AdTextStyle.FONT_SIZE));
            mFlagView.setTextColor((int) mNativeAdViewOptions.adFlagTextStyle.get(AdTextStyle.COLOR));
            mFlagView.setVisibility((int) mNativeAdViewOptions.adFlagTextStyle.get(AdTextStyle.VISIBILITY));
        }

        if (mTitleView != null) {
            mTitleView.setTextSize((float) mNativeAdViewOptions.titleTextStyle.get(AdTextStyle.FONT_SIZE));
            mTitleView.setTextColor((int) mNativeAdViewOptions.titleTextStyle.get(AdTextStyle.COLOR));
            mTitleView.setVisibility((int) mNativeAdViewOptions.titleTextStyle.get(AdTextStyle.VISIBILITY));
        }

        if (mAdSourceView != null) {
            mAdSourceView.setTextSize((float) mNativeAdViewOptions.adSourceTextStyle.get(AdTextStyle.FONT_SIZE));
            mAdSourceView.setTextColor((int) mNativeAdViewOptions.adSourceTextStyle.get(AdTextStyle.COLOR));
            mAdSourceView.setVisibility((int) mNativeAdViewOptions.adSourceTextStyle.get(AdTextStyle.VISIBILITY));
        }

        if (mDescriptionView != null) {
            mDescriptionView.setTextSize((float) mNativeAdViewOptions.descriptionTextStyle.get(AdTextStyle.FONT_SIZE));
            mDescriptionView.setTextColor((int) mNativeAdViewOptions.descriptionTextStyle.get(AdTextStyle.COLOR));
            mDescriptionView.setVisibility((int) mNativeAdViewOptions.descriptionTextStyle.get(AdTextStyle.VISIBILITY));
        }

        if (mCallToActionView != null) {
            int callBackgroundColor = (int) mNativeAdViewOptions.callToActionStyle.get(AdTextStyle.BACKGROUND_COLOR);
            if (callBackgroundColor != 0) {
                mCallToActionView.setBackground(toRoundedColor(callBackgroundColor, 20f));
            }
            mCallToActionView.setTextSize((float) mNativeAdViewOptions.callToActionStyle.get(AdTextStyle.FONT_SIZE));
            mCallToActionView.setTextColor((int) mNativeAdViewOptions.callToActionStyle.get(AdTextStyle.COLOR));
            mCallToActionView.setVisibility((int) mNativeAdViewOptions.callToActionStyle.get(AdTextStyle.VISIBILITY));
        }
    }

    private int getLayoutIdFromMediaType(String mediaType) {
        if ("image_small".equals(mediaType)) {
            return ResourceUtils.getLayoutResourceIdFromContext(mReactContext, "native_small_template");
        }
        return ResourceUtils.getLayoutResourceIdFromContext(mReactContext, "native_video_template");
    }

    public void setDisplayForm(ReadableMap displayForm) {
        if (ReactUtils.hasValidKey(displayForm, "adId", ReadableType.String)) {
            mAdId = displayForm.getString("adId");
            Log.i(TAG, "adId is set");
        }
        if (ReactUtils.hasValidKey(displayForm, "mediaType", ReadableType.String)) {
            mMediaType = displayForm.getString("mediaType");
            mLayoutId = getLayoutIdFromMediaType(mMediaType);
            Log.i(TAG, "layoutId is set");
        }
    }

    public void dislikeAd(String description) {
        if (mNativeAd != null) {
            mNativeAd.dislikeAd(() -> description);
            Log.i(TAG, "dislikeAd() is called");
        }
    }

    public void destroy() {
        mNativeView.destroy();
        Log.i(TAG, "destroy() is called");
    }

    public void gotoWhyThisAdPage() {
        mNativeView.gotoWhyThisAdPage();
        Log.i(TAG, "gotoWhyThisAdPage() is called");
    }

    public void setAllowCustomClick() {
        if (mNativeAd != null) {
            mNativeAd.setAllowCustomClick();
            Log.i(TAG, "setAllowCustomClick() is called");
        }
    }

    public void recordClickEvent() {
        if (mNativeAd != null) {
            mNativeAd.recordClickEvent();
            Log.i(TAG, "recordClickEvent() is called");
        }
    }

    public void recordImpressionEvent(ReadableMap impressionEvent) {
        if (mNativeAd != null) {
            mNativeAd.recordImpressionEvent(ReactUtils.getBundleFromReadableMap(impressionEvent));
            Log.i(TAG, "recordImpressionEvent() is called");
        }
    }

    public void setAdParam(ReadableMap adParamReadableMap) {
        mAdParamReadableMap = adParamReadableMap;
        Log.i(TAG, "adParam is set");
    }

    interface AdTextStyle {
        String VISIBILITY = "visibility";
        String FONT_SIZE = "fontSize";
        String COLOR = "color";
        String BACKGROUND_COLOR = "backgroundColor";
    }

    public class NativeAdViewOptions {
        boolean showMediaContent = true;
        ImageView.ScaleType mediaImageScaleType = ImageView.ScaleType.FIT_CENTER;
        Map<String, Object> adSourceTextStyle = createAdTextStyle(View.VISIBLE, 14f, Color.BLACK);
        Map<String, Object> adFlagTextStyle = createAdTextStyle(
                View.VISIBLE, 12f, Color.WHITE, Color.parseColor("#FFCC66"));
        Map<String, Object> titleTextStyle = createAdTextStyle(View.VISIBLE, 16f, Color.BLACK);
        Map<String, Object> descriptionTextStyle = createAdTextStyle(View.INVISIBLE, 12f, Color.GRAY);
        Map<String, Object> callToActionStyle = createAdTextStyle(
                View.VISIBLE, 15f, Color.WHITE, Color.parseColor("#4CBE99"));

        public NativeAdViewOptions build(ReadableMap rm) {
            NativeAdViewOptions options = new NativeAdViewOptions();
            if (rm != null) {
                if (ReactUtils.hasValidKey(rm, "showMediaContent", ReadableType.Boolean)) {
                    showMediaContent = rm.getBoolean("showMediaContent");
                }
                if (ReactUtils.hasValidKey(rm, "mediaImageScaleType", ReadableType.String)) {
                    mediaImageScaleType = ImageView.ScaleType.valueOf(rm.getString("mediaImageScaleType"));
                }
                if (ReactUtils.hasValidKey(rm, "adFlagTextStyle", ReadableType.Map)) {
                    buildAdTextStyle(options.adFlagTextStyle, rm.getMap("adFlagTextStyle"));
                }
                if (ReactUtils.hasValidKey(rm, "adSourceTextStyle", ReadableType.Map)) {
                    buildAdTextStyle(options.adSourceTextStyle, rm.getMap("adSourceTextStyle"));
                }
                if (ReactUtils.hasValidKey(rm, "titleTextStyle", ReadableType.Map)) {
                    buildAdTextStyle(options.titleTextStyle, rm.getMap("titleTextStyle"));
                }
                if (ReactUtils.hasValidKey(rm, "descriptionTextStyle", ReadableType.Map)) {
                    buildAdTextStyle(options.descriptionTextStyle, rm.getMap("descriptionTextStyle"));
                }
                if (ReactUtils.hasValidKey(rm, "callToActionStyle", ReadableType.Map)) {
                    buildAdTextStyle(options.callToActionStyle, rm.getMap("callToActionStyle"));
                }
            }
            return options;
        }

        Map<String, Object> createAdTextStyle(int visibility, float fontSize, int color) {
            Map<String, Object> textStyle = new HashMap<>();
            textStyle.put(AdTextStyle.VISIBILITY, visibility);
            textStyle.put(AdTextStyle.FONT_SIZE, fontSize);
            textStyle.put(AdTextStyle.COLOR, color);
            return textStyle;
        }

        Map<String, Object> createAdTextStyle(int visibility, float fontSize, int color, int backgroundColor) {
            Map<String, Object> textStyle = new HashMap<>();
            textStyle.put(AdTextStyle.VISIBILITY, visibility);
            textStyle.put(AdTextStyle.FONT_SIZE, fontSize);
            textStyle.put(AdTextStyle.COLOR, color);
            textStyle.put(AdTextStyle.BACKGROUND_COLOR, backgroundColor);
            return textStyle;
        }

        void buildAdTextStyle(Map<String, Object> adTextStyle, ReadableMap rm) {
            if (rm != null) {
                if (ReactUtils.hasValidKey(rm, "fontSize", ReadableType.Number)) {
                    adTextStyle.put(AdTextStyle.FONT_SIZE, (float) rm.getDouble("fontSize"));
                }
                if (ReactUtils.hasValidKey(rm, "color", ReadableType.String)) {
                    adTextStyle.put(AdTextStyle.COLOR, Color.parseColor(rm.getString("color")));
                }
                if (ReactUtils.hasValidKey(rm, "backgroundColor", ReadableType.String)) {
                    adTextStyle.put(AdTextStyle.BACKGROUND_COLOR, Color.parseColor(rm.getString("backgroundColor")));
                }
                if (ReactUtils.hasValidKey(rm, "visibility", ReadableType.Boolean)) {
                    adTextStyle.put(AdTextStyle.VISIBILITY, rm.getBoolean("visibility") ? View.VISIBLE : View.GONE);
                }
            }
        }
    }
}
