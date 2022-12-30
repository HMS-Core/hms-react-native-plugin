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

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.VideoConfiguration;
import com.huawei.hms.ads.VideoOperator;
import com.huawei.hms.ads.nativead.MediaView;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdConfiguration;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.huawei.hms.ads.nativead.NativeView;

import com.huawei.hms.rn.ads.logger.HMSLogger;
import com.huawei.hms.rn.ads.utils.ReactUtils;
import com.huawei.hms.rn.ads.utils.ResourceUtils;

import java.util.HashMap;
import java.util.Map;

public class HMSAdsNativeView extends LinearLayout {
    private static final String TAG = HMSAdsNativeView.class.getSimpleName();

    protected NativeAd mNativeAd;

    protected NativeAdConfiguration mNativeAdConfiguration;

    protected NativeAdLoader mNativeAdLoader;
    
    private ReactContext mReactContext;

    private NativeView mNativeView;

    private MediaView mMediaView;

    private TextView mTitleView;

    private TextView mDescriptionView;

    private TextView mAdSourceView;

    private TextView mFlagView;

    private Button mCallToActionView;

    private NativeAdConfiguration.Builder mNativeAdConfigurationBuilder;

    private VideoConfiguration.Builder mVideoConfigurationBuilder;

    private VideoConfiguration mVideoConfiguration;

    private NativeAd.NativeAdLoadedListener mNativeAdLoadedListener;

    private AdListener mAdListener;

    private ReadableMap mAdParamReadableMap;

    private String mAdId = "testy63txaom86";

    private NativeMediaType mMediaType = NativeMediaType.VIDEO;

    private int mLayoutId = R.layout.native_video_template;

    private NativeAdViewOptions mNativeAdViewOptions = new NativeAdViewOptions().build(null);

    private final Runnable measureAndLayout = () -> {
        measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
        layout(getLeft(), getTop(), getRight(), getBottom());
    };

    public enum NativeMediaType {
        IMAGE_LARGE("image_large"),
        IMAGE_SMALL("image_small"),
        VIDEO("video");

        private String value;

        NativeMediaType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static NativeMediaType forValue(String s) {
            switch (s) {
                case "image_large":
                    return IMAGE_LARGE;
                case "image_small":
                    return IMAGE_SMALL;
                default:
                    return VIDEO;
            }
        }
    }

    public HMSAdsNativeView(Context context) {
        super(context);
        if (context instanceof ReactContext) {
            mReactContext = (ReactContext) context;
        }
        setupInitialConfigurations();
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    private void setupInitialConfigurations() {
        mAdListener = new AdListener() {
            @Override
            public void onAdFailed(int errorCode) {
                sendEvent(Manager.Event.AD_FAILED, ReactUtils.getWritableMapFromErrorCode(errorCode));
            }

            @Override
            public void onAdClicked() {
                sendEvent(Manager.Event.AD_CLICKED, null);
            }

            @Override
            public void onAdImpression() {
                sendEvent(Manager.Event.AD_IMPRESSION, null);
            }
        };
        mNativeAdLoadedListener = nativeAd -> {
            // Call this method when an ad is successfully loaded.
            sendEvent(Manager.Event.NATIVE_AD_LOADED, null);
            // Display native ad.
            showNativeAd(nativeAd);
            nativeAd.setDislikeAdListener(() -> {
                // Call this method when an ad is closed.
                sendEvent(Manager.Event.AD_DISLIKED, null);
            });
        };

        mVideoConfigurationBuilder = new VideoConfiguration.Builder();
        mVideoConfiguration = mVideoConfigurationBuilder.build();

        mNativeAdConfigurationBuilder = new NativeAdConfiguration.Builder().setVideoConfiguration(mVideoConfiguration);
        mNativeAdConfiguration = mNativeAdConfigurationBuilder.build();
    }

    void loadAd() {
        mNativeAdLoader = new NativeAdLoader.Builder(mReactContext, mAdId).setNativeAdLoadedListener(
            mNativeAdLoadedListener).setAdListener(mAdListener).setNativeAdOptions(mNativeAdConfiguration).build();
        mNativeAdLoader.loadAd(ReactUtils.getAdParamFromReadableMap(mAdParamReadableMap));
    }

    void setVideoConfiguration(ReadableMap videoConfiguration) {
        if (videoConfiguration == null) {
            return;
        }
        if (ReactUtils.hasValidKey(videoConfiguration, "audioFocusType", ReadableType.Number)) {
            mVideoConfigurationBuilder.setAudioFocusType(videoConfiguration.getInt("audioFocusType"));
        }
        if (ReactUtils.hasValidKey(videoConfiguration, "clickToFullScreenRequested", ReadableType.Boolean)) {
            mVideoConfigurationBuilder.setClickToFullScreenRequested(
                videoConfiguration.getBoolean("clickToFullScreenRequested"));
        }
        if (ReactUtils.hasValidKey(videoConfiguration, "customizeOperateRequested", ReadableType.Boolean)) {
            mVideoConfigurationBuilder.setCustomizeOperateRequested(
                videoConfiguration.getBoolean("customizeOperateRequested"));
        }
        if (ReactUtils.hasValidKey(videoConfiguration, "startMuted", ReadableType.Boolean)) {
            mVideoConfigurationBuilder.setStartMuted(videoConfiguration.getBoolean("startMuted"));
        }
        mVideoConfiguration = mVideoConfigurationBuilder.build();
    }

    void setNativeAdConfiguration(ReadableMap nativeAdConfiguration) {
        if (nativeAdConfiguration != null) {
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "choicesPosition", ReadableType.Number)) {
                mNativeAdConfigurationBuilder.setChoicesPosition(nativeAdConfiguration.getInt("choicesPosition"));
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "mediaDirection", ReadableType.Number)) {
                mNativeAdConfigurationBuilder.setMediaDirection(nativeAdConfiguration.getInt("mediaDirection"));
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "mediaAspect", ReadableType.Number)) {
                mNativeAdConfigurationBuilder.setMediaAspect(nativeAdConfiguration.getInt("mediaAspect"));
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "requestCustomDislikeThisAd", ReadableType.Boolean)) {
                mNativeAdConfigurationBuilder.setRequestCustomDislikeThisAd(
                        nativeAdConfiguration.getBoolean("requestCustomDislikeThisAd"));
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "requestMultiImages", ReadableType.Boolean)) {
                mNativeAdConfigurationBuilder.setRequestMultiImages(
                        nativeAdConfiguration.getBoolean("requestMultiImages"));
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "returnUrlsForImages", ReadableType.Boolean)) {
                mNativeAdConfigurationBuilder.setReturnUrlsForImages(
                        nativeAdConfiguration.getBoolean("returnUrlsForImages"));
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "adSize", ReadableType.Map)) {
                mNativeAdConfigurationBuilder.setAdSize(
                    ReactUtils.getAdSizeFromReadableMap(nativeAdConfiguration.getMap("adSize")));
            }
            if (ReactUtils.hasValidKey(nativeAdConfiguration, "videoConfiguration", ReadableType.Map)) {
                setVideoConfiguration(nativeAdConfiguration.getMap("videoConfiguration"));
                mNativeAdConfigurationBuilder.setVideoConfiguration(mVideoConfiguration);
            }
        }
        mNativeAdConfiguration = mNativeAdConfigurationBuilder.build();
    }

    private VideoOperator.VideoLifecycleListener videoLifecycleListener = new VideoOperator.VideoLifecycleListener() {
        @Override
        public void onVideoStart() {
            sendEvent(Manager.Event.AD_VIDEO_START, null);
        }

        @Override
        public void onVideoPlay() {
            sendEvent(Manager.Event.AD_VIDEO_PLAY, null);
        }

        @Override
        public void onVideoEnd() {
            sendEvent(Manager.Event.AD_VIDEO_END, null);
        }

        @Override
        public void onVideoPause() {
            sendEvent(Manager.Event.AD_VIDEO_PAUSE, null);
        }

        @Override
        public void onVideoMute(boolean isMuted) {
            WritableNativeMap wm = new WritableNativeMap();
            wm.putBoolean("isMuted", isMuted);
            sendEvent(Manager.Event.AD_VIDEO_MUTE, wm);
        }
    };

    public static String getCreativeType(int code) {
        switch (code) {
            case 1:
                return "Text";
            case 3:
                return "Large image with text";
            case 6:
                return "Video with text";
            case 7:
                return "Small image with text";
            case 8:
                return "Three small images with text";
            default:
                return "Large image";
        }
    }

    private void showNativeAd(NativeAd nativeAd) {
        // Destroy the original native ad.
        if (null != mNativeAd) {
            mNativeAd.destroy();
        }
        mNativeAd = nativeAd;

        LayoutInflater layoutInflater = LayoutInflater.from(mReactContext);

        View inflated = layoutInflater.inflate(mLayoutId, this, false);
        if (inflated instanceof NativeView) {
            // Obtain NativeView.
            mNativeView = (NativeView) inflated;
            // Register and populate a native ad material view.
            initNativeAdView();

            this.removeAllViews();
            this.addView(mNativeView);
            this.requestLayout();
        }
    }

    private void sendEvent(Manager.Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), event.getName(), wm);
    }

    private void initNativeAdView() {
        mMediaView = mNativeView.findViewById(R.id.ad_media);
        mNativeView.setMediaView(mMediaView);

        mTitleView = mNativeView.findViewById(R.id.ad_title);
        mNativeView.setTitleView(mTitleView);

        mAdSourceView = mNativeView.findViewById(R.id.ad_source);
        mNativeView.setAdSourceView(mAdSourceView);

        mCallToActionView = mNativeView.findViewById(R.id.ad_call_to_action);
        mNativeView.setCallToActionView(mCallToActionView);

        mDescriptionView = mNativeView.findViewById(R.id.ad_description);
        mNativeView.setDescriptionView(mDescriptionView);

        mFlagView = mNativeView.findViewById(R.id.ad_flag);

        if (mNativeAd.getTitle() != null) {
            mTitleView.setText(mNativeAd.getTitle());
        }
        if (mNativeAd.getMediaContent() != null) {
            mMediaView.setMediaContent(mNativeAd.getMediaContent());
        }

        if (null != mNativeAd.getAdSource()) {
            mAdSourceView.setText(mNativeAd.getAdSource());
            mAdSourceView.setVisibility(View.VISIBLE);
        } else {
            mAdSourceView.setVisibility(View.INVISIBLE);
        }

        if (null != mNativeAd.getDescription()) {
            mDescriptionView.setText(mNativeAd.getDescription());
            mDescriptionView.setVisibility(View.VISIBLE);
        } else {
            mDescriptionView.setVisibility(View.INVISIBLE);
        }

        if (null != mNativeAd.getCallToAction()) {
            mCallToActionView.setText(mNativeAd.getCallToAction());
            mCallToActionView.setVisibility(View.VISIBLE);
        } else {
            mCallToActionView.setVisibility(View.INVISIBLE);
        }

        // Obtain a video controller.
        VideoOperator videoOperator = mNativeAd.getVideoOperator();

        // Check whether a native ad contains video materials.
        if (videoOperator.hasVideo()) {
            // Add a video lifecycle event listener.
            videoOperator.setVideoLifecycleListener(videoLifecycleListener);
        }

        updateViewOptions();
        // Register a native ad object.
        mNativeView.setNativeAd(mNativeAd);
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

    private int getLayoutIdFromMediaType(NativeMediaType mediaType) {
        if (NativeMediaType.IMAGE_SMALL == mediaType) {
            return ResourceUtils.getLayoutResourceIdFromContext(mReactContext, "native_small_template");
        }
        return ResourceUtils.getLayoutResourceIdFromContext(mReactContext, "native_video_template");
    }

    public void setDisplayForm(ReadableMap displayForm) {
        if (ReactUtils.hasValidKey(displayForm, "adId", ReadableType.String)) {
            mAdId = displayForm.getString("adId");
        }
        if (ReactUtils.hasValidKey(displayForm, "mediaType", ReadableType.String)) {
            mMediaType = NativeMediaType.forValue(displayForm.getString("mediaType"));
            mLayoutId = getLayoutIdFromMediaType(mMediaType);
        }
    }

    public void dislikeAd(String description) {
        if (mNativeAd != null) {
            mNativeAd.dislikeAd(() -> description);
        }
    }

    public void destroy() {
        mNativeView.destroy();
    }

    public void gotoWhyThisAdPage() {
        mNativeView.gotoWhyThisAdPage();
    }

    public void setAllowCustomClick() {
        if (mNativeAd != null) {
            mNativeAd.setAllowCustomClick();
        }
    }

    public void recordClickEvent() {
        if (mNativeAd != null) {
            mNativeAd.recordClickEvent();
        }
    }

    public void recordImpressionEvent(ReadableMap impressionEvent) {
        if (mNativeAd != null) {
            mNativeAd.recordImpressionEvent(ReactUtils.getBundleFromReadableMap(impressionEvent));
        }
    }

    public void setAdParam(ReadableMap adParamReadableMap) {
        mAdParamReadableMap = adParamReadableMap;
    }

    interface AdTextStyle {
        String VISIBILITY = "visibility";
        String FONT_SIZE = "fontSize";
        String COLOR = "color";
        String BACKGROUND_COLOR = "backgroundColor";
    }

    public static class Manager extends ViewGroupManager<HMSAdsNativeView> {
        private HMSLogger hmsLogger;

        public Manager(ReactApplicationContext reactContext) {
            hmsLogger = HMSLogger.getInstance(reactContext);
        }

        public enum Event implements ReactUtils.NamedEvent {
            NATIVE_AD_LOADED("onNativeAdLoaded"),
            AD_DISLIKED("onAdDisliked"),
            AD_FAILED("onAdFailed"),
            AD_CLICKED("onAdClicked"),
            AD_IMPRESSION("onAdImpression"),
            AD_VIDEO_START("onVideoStart"),
            AD_VIDEO_PLAY("onVideoPlay"),
            AD_VIDEO_END("onVideoEnd"),
            AD_VIDEO_PAUSE("onVideoPause"),
            AD_VIDEO_MUTE("onVideoMute");

            private String nativeEventName;

            Event(String nativeEventName) {
                this.nativeEventName = nativeEventName;
            }

            public String getName() {
                return nativeEventName;
            }
        }

        public enum Command implements ReactUtils.NamedCommand {
            LOAD_AD("loadAd"),
            DISLIKE_AD("dislikeAd"),
            DESTROY("destroy"),
            GO_TO_WHY("gotoWhyThisAdPage"),
            ALLOW_CUSTOM_CLICK("setAllowCustomClick"),
            RECORD_CLICK("recordClickEvent"),
            RECORD_IMPRESSION("recordImpressionEvent");

            private String nativeCommandName;

            Command(String nativeCommandName) {
                this.nativeCommandName = nativeCommandName;
            }

            public String getName() {
                return nativeCommandName;
            }
        }

        @NonNull
        @Override
        public String getName() {
            return "HMSAdsNativeView";
        }

        @NonNull
        @Override
        protected HMSAdsNativeView createViewInstance(@NonNull ThemedReactContext reactContext) {
            hmsLogger.sendSingleEvent("nativeView.create");
            return new HMSAdsNativeView(reactContext);
        }

        @Nullable
        @Override
        public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
            return ReactUtils.getExportedCustomDirectEventTypeConstantsFromEvents(Manager.Event.values());
        }

        @Nullable
        @Override
        public Map<String, Integer> getCommandsMap() {
            return ReactUtils.getCommandsMap(Manager.Command.values());
        }

        @Override
        public void receiveCommand(@NonNull HMSAdsNativeView root, int commandId, @Nullable ReadableArray args) {
            if (commandId < Manager.Command.values().length) {
                switch (Manager.Command.values()[commandId]) {
                    case LOAD_AD:
                        hmsLogger.startMethodExecutionTimer("nativeView.loadAd");
                        root.loadAd();
                        hmsLogger.sendSingleEvent("nativeView.loadAd");
                        break;
                    case DISLIKE_AD:
                        assert args != null;
                        hmsLogger.startMethodExecutionTimer("nativeView.dislikeAd");
                        root.dislikeAd(args.getString(0));
                        hmsLogger.sendSingleEvent("nativeView.dislikeAd");
                        break;
                    case DESTROY:
                        hmsLogger.startMethodExecutionTimer("nativeView.destroy");
                        root.destroy();
                        hmsLogger.sendSingleEvent("nativeView.destroy");
                        break;
                    case GO_TO_WHY:
                        hmsLogger.startMethodExecutionTimer("nativeView.gotoWhyThisAdPage");
                        root.gotoWhyThisAdPage();
                        hmsLogger.sendSingleEvent("nativeView.gotoWhyThisAdPage");
                        break;
                    case ALLOW_CUSTOM_CLICK:
                        hmsLogger.startMethodExecutionTimer("nativeView.setAllowCustomClick");
                        root.setAllowCustomClick();
                        hmsLogger.sendSingleEvent("nativeView.setAllowCustomClick");
                        break;
                    case RECORD_CLICK:
                        hmsLogger.startMethodExecutionTimer("nativeView.recordClickEvent");
                        root.recordClickEvent();
                        hmsLogger.sendSingleEvent("nativeView.recordClickEvent");
                        break;
                    case RECORD_IMPRESSION:
                        assert args != null;
                        hmsLogger.startMethodExecutionTimer("nativeView.recordImpressionEvent");
                        root.recordImpressionEvent(args.getMap(0));
                        hmsLogger.sendSingleEvent("nativeView.recordImpressionEvent");
                        break;
                    default:
                        break;
                }
            }
        }

        @ReactProp(name = "adParam")
        public void setAdParam(final HMSAdsNativeView view, final ReadableMap adParamReadableMap) {
            hmsLogger.sendSingleEvent("nativeView.setAdParam");
            view.setAdParam(adParamReadableMap);
        }

        @ReactProp(name = "displayForm")
        public void setDisplayForm(final HMSAdsNativeView view, final ReadableMap displayForm) {
            hmsLogger.sendSingleEvent("nativeView.setDisplayForm");
            view.setDisplayForm(displayForm);
        }

        @ReactProp(name = "nativeConfig")
        public void setNativeConfig(final HMSAdsNativeView view, final ReadableMap nativeAdConfiguration) {
            hmsLogger.sendSingleEvent("nativeView.setNativeConfig");
            view.setNativeAdConfiguration(nativeAdConfiguration);
        }

        @ReactProp(name = "viewOptions")
        public void setViewOptions(final HMSAdsNativeView view, final ReadableMap viewOptions) {
            hmsLogger.sendSingleEvent("nativeView.setViewOptions");
            view.setViewOptions(viewOptions);
        }
    }

    public static class NativeAdViewOptions {
        boolean showMediaContent = true;

        ImageView.ScaleType mediaImageScaleType = ImageView.ScaleType.FIT_CENTER;

        Map<String, Object> adSourceTextStyle = createAdTextStyle(View.VISIBLE, 14f, Color.BLACK);

        Map<String, Object> adFlagTextStyle = createAdTextStyle(View.VISIBLE, 12f, Color.WHITE,
            Color.parseColor("#FFCC66"));

        Map<String, Object> titleTextStyle = createAdTextStyle(View.VISIBLE, 16f, Color.BLACK);

        Map<String, Object> descriptionTextStyle = createAdTextStyle(View.INVISIBLE, 12f, Color.GRAY);

        Map<String, Object> callToActionStyle = createAdTextStyle(View.VISIBLE, 15f, Color.WHITE,
            Color.parseColor("#4CBE99"));

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
