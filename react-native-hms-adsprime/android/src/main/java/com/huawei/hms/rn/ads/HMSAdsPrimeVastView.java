/*
 * Copyright 2020-2024. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.hms.rn.ads;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.huawei.hms.ads.vast.player.VastApplication;
import com.huawei.hms.ads.vast.player.api.AdsRequestListener;
import com.huawei.hms.ads.vast.player.api.DefaultVideoController;
import com.huawei.hms.ads.vast.player.api.PlayerConfig;
import com.huawei.hms.ads.vast.player.api.VastAdPlayer;
import com.huawei.hms.ads.vast.player.api.VastPlayerListener;
import com.huawei.hms.ads.vast.player.model.adslot.AdsData;
import com.huawei.hms.ads.vast.player.model.adslot.LinearAdSlot;
import com.huawei.hms.ads.vast.player.model.remote.RequestCallback;
import com.huawei.hms.rn.ads.custome.CustomVideoController;
import com.huawei.hms.rn.ads.logger.HMSLogger;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import java.util.Map;

public class HMSAdsPrimeVastView extends LinearLayout implements LifecycleEventListener, VastPlayerListener {
    private static final String TAG = HMSAdsPrimeVastView.class.getSimpleName();

    public ReactContext mReactContext;

    private FrameLayout mLinearAdView;

    private ProgressBar mProgressBar;

    protected PlayerConfig playerConfig;
    protected AdsData mAdsData;
    protected LinearAdSlot mLinearAdSlot;
    protected CustomVideoController customVideoController;
    protected DefaultVideoController defaultVideoController;
    private HMSAdsPrimeVastView mHMSAdsPrimeVastView;
    private boolean isCustomVideoPlayer = false;
    private boolean isAdLoadWithAdsData = false;
    private boolean isTestAd = false;

    private final Runnable measureAndLayout = () -> {
        measure(
                MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
        layout(getLeft(), getTop(), getRight(), getBottom());
    };

    public HMSAdsPrimeVastView(Context context) {
        super(context);
        if (context instanceof ReactContext) {
            mReactContext = (ReactContext) context;
        }
        mReactContext.addLifecycleEventListener(this);
    }

    public HMSAdsPrimeVastView(Context context, AttributeSet attrs, int defStyle) {
        super(context);
    }

    public HMSAdsPrimeVastView(Context context, AttributeSet attrs) {
        super(context);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    private void sendEvent(HMSAdsPrimeVastView.Manager.Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), event.getName(), wm);
    }

    void loadAd() {
        VastApplication.init(mReactContext, isTestAd);

        VastAdPlayer.getInstance().setAdViewStrategy((expectedHeight, expectedWidth, height, width) -> true);

        LayoutInflater layoutInflater = LayoutInflater.from(mReactContext);

        if (mHMSAdsPrimeVastView != null) {
            releaseAd();
        }

        mHMSAdsPrimeVastView = (HMSAdsPrimeVastView) layoutInflater.inflate(R.layout.vast_template, null);
        mLinearAdView = mHMSAdsPrimeVastView.findViewById(R.id.fl_linear_ad);
        mProgressBar = mHMSAdsPrimeVastView.findViewById(R.id.progress);

        if (isCustomVideoPlayer) {
            customVideoController = new CustomVideoController(mReactContext);
            customVideoController.setPlayerListener(this);

            VastAdPlayer.getInstance().registerLinearAdView(mLinearAdView, customVideoController);
        } else {
            defaultVideoController = new DefaultVideoController(mReactContext);

            VastAdPlayer.getInstance().registerLinearAdView(mLinearAdView, defaultVideoController);
        }

        VastAdPlayer.getInstance().setConfig(playerConfig);

        if (isAdLoadWithAdsData) {
            VastAdPlayer.getInstance().loadLinearAd(mLinearAdSlot, new RequestCallback() {
                @Override
                public void onAdsLoadedSuccess(AdsData adsData) {
                    Log.d(TAG, "onAdsLoadedSuccess :");
                    mAdsData = adsData;
                    showAd();
                    sendEvent(Manager.Event.AD_LOAD_SUCCESS, ReactUtils.getWritableMapFromAdsData(adsData));
                }

                @Override
                public void onAdsLoadFailed() {
                    Log.d(TAG, "onAdsLoadFailed :");
                    mAdsData = null;
                    sendEvent(Manager.Event.AD_LOAD_FAILED, null);
                }
            });
        }else{
            showAd();
        }

    }

    void showAd() {
        if (mLinearAdSlot == null) {
            return;
        }

        if (mAdsData != null) {
            VastAdPlayer.getInstance().playLinearAds(mLinearAdSlot, mAdsData, adsRequestListener);
        } else {
            if (mLinearAdSlot.getMaxAdPods() != 0) {
                VastAdPlayer.getInstance().startAdPods(mLinearAdSlot, adsRequestListener);
            } else {
                VastAdPlayer.getInstance().startLinearAd(mLinearAdSlot, adsRequestListener);
            }
        }

        this.removeAllViews();
        this.addView(mHMSAdsPrimeVastView);
        this.requestLayout();
    }

    @Override
    public void onPlayStateChanged(int playState) {
        Log.d(TAG, "onPlayStateChanged :" + playState);
        sendEvent(Manager.Event.AD_PLAY_STATE_CHANGED, ReactUtils.getWritableMapFromVastPlayState(playState));
    }

    @Override
    public void onVolumeChanged(float volume) {
        Log.d(TAG, "onVolumeChanged :" + volume);
        WritableMap wm = new WritableNativeMap();
        wm.putDouble("volume", volume);
        sendEvent(Manager.Event.AD_VOLUME_CHANGED, wm);
    }

    @Override
    public void onScreenViewChanged(int screenState) {
        Log.d(TAG, "onScreenViewChanged :" + screenState);
        sendEvent(Manager.Event.AD_SCREEN_VIEW_CHANGED, ReactUtils.getWritableMapFromVastScreenState(screenState));
    }

    @Override
    public void onProgressChanged(long duration, long currentPosition, long skipDuration) {
        Log.d(TAG, "onProgressChanged :" + duration + " -- " + " -- " + currentPosition + " -- " + skipDuration);
        sendEvent(Manager.Event.AD_PROGRESS_CHANGED, ReactUtils.getWritableMapFromVastProgressListener(duration, currentPosition, skipDuration));
    }

    private AdsRequestListener adsRequestListener = new AdsRequestListener() {
        @Override
        public void onSuccess(View view, int responseCode) {
            Log.d(TAG, "onSuccess :" + responseCode);
            sendEvent(Manager.Event.AD_ON_SUCCESS, null);
        }

        @Override
        public void onFailed(View view, int responseCode) {
            Log.d(TAG, "onFailed :" + responseCode);
            sendEvent(Manager.Event.AD_ON_FAILED, ReactUtils.getWritableMapFromVastErrorCode(responseCode));
        }

        @Override
        public void playAdReady() {
            Log.d(TAG, "playAdReady :");
            mLinearAdView.setVisibility(View.VISIBLE);
            sendEvent(Manager.Event.AD_READY, null);
        }

        @Override
        public void playAdFinish() {
            Log.d(TAG, "playAdFinish :");
            mLinearAdView.setVisibility(View.INVISIBLE);
            sendEvent(Manager.Event.AD_FINISH, null);
        }

        @Override
        public void onBufferStart() {
            Log.d(TAG, "onBufferStart :");
            mProgressBar.setVisibility(View.VISIBLE);
            sendEvent(Manager.Event.AD_BUFFER_START, null);
        }

        @Override
        public void onBufferEnd() {
            Log.d(TAG, "onBufferEnd :");
            mProgressBar.setVisibility(View.INVISIBLE);
            sendEvent(Manager.Event.AD_BUFFER_END, null);
        }
    };

    public enum CreativeMatchType {
        EXACT(0),
        SMART(1),
        UNKNOWN(2),
        ANY(3),
        LANDSCAPE(4),
        PORTRAIT(5);

        private int value;

        CreativeMatchType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static HMSAdsPrimeVastView.CreativeMatchType forValue(int s) {
            switch (s) {
                case 0:
                    return EXACT;
                case 1:
                    return SMART;
                case 2:
                    return UNKNOWN;
                case 4:
                    return LANDSCAPE;
                case 5:
                    return PORTRAIT;
                default:
                    return ANY;
            }
        }
    }

    public void setAdParam(ReadableMap adParamReadableMap) {
        mLinearAdSlot = ReactUtils.getLinearAdSlotFromReadableMap(adParamReadableMap);
    }

    public void setIsCustomVideoPlayer(boolean isCustomVideoPlayer) {
        this.isCustomVideoPlayer = isCustomVideoPlayer;
    }

    public void setIsAdLoadWithAdsData(boolean isAdLoadWithAdsData) {
        this.isAdLoadWithAdsData = isAdLoadWithAdsData;
    }

    public void setIsTestAd(boolean isTestAd) {
        this.isTestAd = isTestAd;
    }

    public void setPlayerConfig(ReadableMap adPlayerConfigsReadableMap) {
        playerConfig = ReactUtils.getPlayerConfigsFromReadableMap(adPlayerConfigsReadableMap);
    }

    public void toggleMuteState(boolean isMute) {
        if (isCustomVideoPlayer) {
            customVideoController.toggleMuteState(isMute);
        } else {
            defaultVideoController.toggleMuteState(isMute);
        }
    }

    public void startOrPause() {
        if (isCustomVideoPlayer) {
            customVideoController.startOrPause();
        } else {
            defaultVideoController.startOrPause();
        }
    }

    public WritableMap getVideoControllerInfo() {
        WritableMap wm = new WritableNativeMap();
        if (isCustomVideoPlayer) {
            wm.putBoolean("isMute", customVideoController.isMute());
            wm.putInt("layoutId", customVideoController.getLayoutId());
        } else {
            wm.putBoolean("isMute", defaultVideoController.isMute());
            wm.putInt("layoutId", defaultVideoController.getLayoutId());
        }
        return wm;
    }

    private void resumeAd() {
        VastAdPlayer.getInstance().resume();
    }

    private void pauseAd() {
        VastAdPlayer.getInstance().pause();
    }

    private void releaseAd() {
        if (mLinearAdView != null) {
            VastAdPlayer.getInstance().unregisterLinearAdView(mLinearAdView);
        }
        VastAdPlayer.getInstance().release();
        this.removeAllViews();
    }

    @Override
    public void onHostResume() {
        Log.d(TAG, "onHostResume");
        resumeAd();
    }

    @Override
    public void onHostPause() {
        Log.d(TAG, "onHostPause");
        pauseAd();
    }

    @Override
    public void onHostDestroy() {
        Log.d(TAG, "onHostDestroy");
        releaseAd();
    }

    public static class Manager extends SimpleViewManager<HMSAdsPrimeVastView> {
        private HMSLogger hmsLogger;

        public Manager(ReactApplicationContext reactContext) {
            hmsLogger = HMSLogger.getInstance(reactContext);
        }

        public enum Event implements ReactUtils.NamedEvent {
            AD_LOAD_SUCCESS("onLoadSuccess"),
            AD_LOAD_FAILED("onLoadFailed"),
            AD_ON_SUCCESS("onSuccess"),
            AD_ON_FAILED("onFailed"),
            AD_READY("onPlayAdReady"),
            AD_FINISH("onPlayAdFinish"),
            AD_BUFFER_START("onBufferStart"),
            AD_BUFFER_END("onBufferEnd"),
            AD_PLAY_STATE_CHANGED("onPlayStateChanged"),
            AD_VOLUME_CHANGED("onVolumeChanged"),
            AD_SCREEN_VIEW_CHANGED("onScreenViewChanged"),
            AD_PROGRESS_CHANGED("onProgressChanged");

            private String nativeVastEventName;

            Event(String nativeEventName) {
                this.nativeVastEventName = nativeEventName;
            }

            public String getName() {
                return nativeVastEventName;
            }
        }

        public enum Command implements ReactUtils.NamedCommand {
            LOAD_AD("loadAd"),
            SHOW_AD("showAd"),
            PAUSE("pause"),
            RESUME("resume"),
            RELEASE("release"),
            TOGGLE_MUTE_STATE("toggleMuteState"),
            START_OR_PAUSE("startOrPause");

            private String nativeVastCommandName;

            Command(String nativeCommandName) {
                this.nativeVastCommandName = nativeCommandName;
            }

            public String getName() {
                return nativeVastCommandName;
            }
        }

        @NonNull
        @Override
        public String getName() {
            return "HMSAdsPrimeVastView";
        }

        @NonNull
        @Override
        protected HMSAdsPrimeVastView createViewInstance(@NonNull ThemedReactContext reactContext) {
            hmsLogger.sendSingleEvent("vastView.create");
            return new HMSAdsPrimeVastView(reactContext);
        }

        @Nullable
        @Override
        public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
            return ReactUtils.getExportedCustomDirectEventTypeConstantsFromEvents(HMSAdsPrimeVastView.Manager.Event.values());
        }

        @Nullable
        @Override
        public Map<String, Integer> getCommandsMap() {
            return ReactUtils.getCommandsMap(HMSAdsPrimeVastView.Manager.Command.values());
        }

        @Override
        public void receiveCommand(@NonNull HMSAdsPrimeVastView root, int commandId, @Nullable ReadableArray args) {
            if (commandId < HMSAdsPrimeVastView.Manager.Command.values().length) {
                switch (HMSAdsPrimeVastView.Manager.Command.values()[commandId]) {
                    case LOAD_AD:
                        hmsLogger.startMethodExecutionTimer("vastView.loadAd");
                        root.loadAd();
                        hmsLogger.sendSingleEvent("vastView.loadAd");
                        break;
                    case PAUSE:
                        hmsLogger.startMethodExecutionTimer("vastView.pause");
                        root.pauseAd();
                        hmsLogger.sendSingleEvent("vastView.pause");
                        break;
                    case RESUME:
                        hmsLogger.startMethodExecutionTimer("vastView.resume");
                        root.resumeAd();
                        hmsLogger.sendSingleEvent("vastView.resume");
                        break;
                    case RELEASE:
                        hmsLogger.startMethodExecutionTimer("vastView.release");
                        root.releaseAd();
                        hmsLogger.sendSingleEvent("vastView.release");
                        break;
                    case TOGGLE_MUTE_STATE:
                        assert args != null;
                        hmsLogger.startMethodExecutionTimer("vastView.toggleMuteState");
                        root.toggleMuteState(args.getBoolean(0));
                        hmsLogger.sendSingleEvent("vastView.toggleMuteState");
                        break;
                    case START_OR_PAUSE:
                        hmsLogger.startMethodExecutionTimer("vastView.startOrPause");
                        root.startOrPause();
                        hmsLogger.sendSingleEvent("vastView.startOrPause");
                        break;
                    default:
                        break;
                }
            }
        }

        @ReactProp(name = "adParam")
        public void setAdParam(final HMSAdsPrimeVastView view, final ReadableMap adParamReadableMap) {
            hmsLogger.sendSingleEvent("vastView.setAdParam");
            view.setAdParam(adParamReadableMap);
        }

        @ReactProp(name = "isCustomVideoPlayer")
        public void setIsCustomVideoPlayer(final HMSAdsPrimeVastView view, boolean isCustomVideoPlayer) {
            hmsLogger.sendSingleEvent("vastView.setIsCustomVideoPlayer");
            view.setIsCustomVideoPlayer(isCustomVideoPlayer);
        }

        @ReactProp(name = "isAdLoadWithAdsData")
        public void setIsAdLoadWithAdsData(final HMSAdsPrimeVastView view, boolean isAdLoadWithAdsData) {
            hmsLogger.sendSingleEvent("vastView.setIsAdLoadWithAdsData");
            view.setIsAdLoadWithAdsData(isAdLoadWithAdsData);
        }

        @ReactProp(name = "playerConfigs")
        public void setPlayerConfigs(final HMSAdsPrimeVastView view, final ReadableMap adParamReadableMap) {
            hmsLogger.sendSingleEvent("vastView.setAdParam");
            view.setPlayerConfig(adParamReadableMap);
        }

        @ReactProp(name = "isTestAd")
        public void setIsTestAd(final HMSAdsPrimeVastView view, boolean isTest) {
            hmsLogger.sendSingleEvent("vastView.isTestAd");
            view.setIsTestAd(isTest);
        }
    }
}

