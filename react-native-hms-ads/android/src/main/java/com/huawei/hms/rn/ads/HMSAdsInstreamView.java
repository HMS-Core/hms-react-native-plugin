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

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.huawei.hms.ads.MediaMuteListener;
import com.huawei.hms.ads.instreamad.InstreamAd;
import com.huawei.hms.ads.instreamad.InstreamAdLoadListener;
import com.huawei.hms.ads.instreamad.InstreamAdLoader;
import com.huawei.hms.ads.instreamad.InstreamMediaChangeListener;
import com.huawei.hms.ads.instreamad.InstreamMediaStateListener;
import com.huawei.hms.ads.instreamad.InstreamView;
import com.huawei.hms.rn.ads.logger.HMSLogger;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HMSAdsInstreamView extends InstreamView implements InstreamMediaChangeListener,
        InstreamMediaStateListener, MediaMuteListener, InstreamAdLoadListener, InstreamView.OnInstreamAdClickListener {
    private static final String TAG = HMSAdsInstreamView.class.getSimpleName();
    private ReactContext mReactContext;

    protected List<InstreamAd> mInstreamAds = new ArrayList<>();
    protected InstreamAdLoader mInstreamAdLoader;
    protected String mAdId;
    protected ReadableMap mAdParamReadableMap;
    protected int mMaxCount;
    protected int mTotalDuration;

    public HMSAdsInstreamView(final Context context) {
        super(context);
    }

    public HMSAdsInstreamView(final ThemedReactContext context) {
        super(context);
        mReactContext =  context;
        setInstreamMediaChangeListener(this);
        setInstreamMediaStateListener(this);
        setMediaMuteListener(this);
        setOnInstreamAdClickListener(this);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(() -> {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        });
    }

    @Override
    public void onClick() {
        sendEvent(Manager.Event.CLICK, null);
    }

    @Override
    public void onMute() {
        sendEvent(Manager.Event.MUTE, null);
    }

    @Override
    public void onUnmute() {
        sendEvent(Manager.Event.UNMUTE, null);
    }

    @Override
    public void onAdLoaded(List<InstreamAd> ads) {
        if (null == ads || ads.size() == 0) {
            return;
        }
        Iterator<InstreamAd> it = ads.iterator();
        while (it.hasNext()) {
            InstreamAd ad = it.next();
            if (ad.isExpired()) {
                it.remove();
            }
        }
        if (ads.size() == 0) {
            return;
        }
        mInstreamAds = ads;
        sendEvent(Manager.Event.AD_LOADED, null);
    }

    @Override
    public void onAdFailed(int errorCode) {
        sendEvent(Manager.Event.AD_FAILED, ReactUtils.getWritableMapFromErrorCode(errorCode));
    }

    @Override
    public void onSegmentMediaChange(InstreamAd instreamAd) {
        sendEvent(Manager.Event.SEGMENT_MEDIA_CHANGE, ReactUtils.getWritableMapFromInstreamAd(instreamAd));
    }

    @Override
    public void onMediaProgress(int percent, int playTime) {
        WritableNativeMap wm = new WritableNativeMap();
        wm.putInt("percent", percent);
        wm.putInt("playTime", playTime);
        sendEvent(Manager.Event.MEDIA_PROGRESS, wm);
    }

    @Override
    public void onMediaStart(int playTime) {
        WritableNativeMap wm = new WritableNativeMap();
        wm.putInt("playTime", playTime);
        sendEvent(Manager.Event.MEDIA_START, wm);
    }

    @Override
    public void onMediaPause(int playTime) {
        WritableNativeMap wm = new WritableNativeMap();
        wm.putInt("playTime", playTime);
        sendEvent(Manager.Event.MEDIA_PAUSE, wm);
    }

    @Override
    public void onMediaStop(int playTime) {
        WritableNativeMap wm = new WritableNativeMap();
        wm.putInt("playTime", playTime);
        sendEvent(Manager.Event.MEDIA_STOP, wm);
    }

    @Override
    public void onMediaCompletion(int playTime) {
        WritableNativeMap wm = new WritableNativeMap();
        wm.putInt("playTime", playTime);
        sendEvent(Manager.Event.MEDIA_COMPLETION, wm);
    }

    @Override
    public void onMediaError(int playTime, int errorCode, int extra) {
        WritableNativeMap wm = new WritableNativeMap();
        wm.putInt("playTime", playTime);
        wm.putInt("extra", extra);
        wm.putMap("error", ReactUtils.getWritableMapFromErrorCode(errorCode));
        sendEvent(Manager.Event.MEDIA_ERROR, wm);
    }

    private void sendEvent(Manager.Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), event.getName(), wm);
    }

    public void loadAd() {
        mInstreamAdLoader = new InstreamAdLoader.Builder(mReactContext, mAdId)
                .setTotalDuration(mTotalDuration)
                .setMaxCount(mMaxCount)
                .setInstreamAdLoadListener(this)
                .build();
        mInstreamAdLoader.loadAd(ReactUtils.getAdParamFromReadableMap(mAdParamReadableMap));
    }

    public void register() {
        setInstreamAds(mInstreamAds);
    }

    public void setAdParam(ReadableMap rm) {
        mAdParamReadableMap = rm;
    }

    public void setAdId(String adId) {
        mAdId = adId;
    }

    public void setTotalDuration(int totalDuration) {
        mTotalDuration = totalDuration;
    }

    public void setMaxCount(int maxCount) {
        mMaxCount = maxCount;
    }

    public static class Manager extends ViewGroupManager<HMSAdsInstreamView> {
        private HMSLogger hmsLogger;

        public Manager(ReactApplicationContext reactContext) {
            hmsLogger = HMSLogger.getInstance(reactContext);
        }

        public enum Event implements ReactUtils.NamedEvent {
            MUTE("onMute"),
            UNMUTE("onUnmute"),
            AD_LOADED("onAdLoaded"),
            AD_FAILED("onAdFailed"),
            SEGMENT_MEDIA_CHANGE("onSegmentMediaChange"),
            MEDIA_PROGRESS("onMediaProgress"),
            MEDIA_START("onMediaStart"),
            MEDIA_PAUSE("onMediaPause"),
            MEDIA_STOP("onMediaStop"),
            MEDIA_COMPLETION("onMediaCompletion"),
            MEDIA_ERROR("onMediaError"),
            CLICK("onClick");

            private String instreamEventName;

            Event(String instreamEventName) {
                this.instreamEventName = instreamEventName;
            }

            public String getName() {
                return instreamEventName;
            }
        }

        @Nullable
        @Override
        public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
            return ReactUtils.getExportedCustomDirectEventTypeConstantsFromEvents(Manager.Event.values());
        }

        public enum Command implements ReactUtils.NamedCommand {
            LOAD_AD("loadAd"),
            REGISTER("register"),
            MUTE("mute"),
            UNMUTE("unmute"),
            STOP("stop"),
            PAUSE("pause"),
            PLAY("play"),
            DESTROY("destroy");

            private String instreamCommandName;

            Command(String instreamCommandName) {
                this.instreamCommandName = instreamCommandName;
            }

            public String getName() {
                return instreamCommandName;
            }
        }

        @NonNull
        @Override
        public String getName() {
            return "HMSAdsInstreamView";
        }

        @NonNull
        @Override
        protected HMSAdsInstreamView createViewInstance(@NonNull ThemedReactContext reactContext) {
            hmsLogger.sendSingleEvent("instreamView.create");
            return new HMSAdsInstreamView(reactContext);
        }

        @Nullable
        @Override
        public Map<String, Integer> getCommandsMap() {
            return ReactUtils.getCommandsMap(Manager.Command.values());
        }

        @Override
        public void receiveCommand(@NonNull HMSAdsInstreamView root, int commandId, @Nullable ReadableArray args) {
            if (commandId < Manager.Command.values().length) {
                switch (Manager.Command.values()[commandId]) {
                    case LOAD_AD:
                        hmsLogger.startMethodExecutionTimer("instreamView.loadAd");
                        root.loadAd();
                        hmsLogger.sendSingleEvent("instreamView.loadAd");
                        break;
                    case REGISTER:
                        hmsLogger.startMethodExecutionTimer("instreamView.register");
                        root.register();
                        hmsLogger.sendSingleEvent("instreamView.register");
                        break;
                    case MUTE:
                        hmsLogger.startMethodExecutionTimer("instreamView.mute");
                        root.mute();
                        hmsLogger.sendSingleEvent("instreamView.mute");
                        break;
                    case UNMUTE:
                        hmsLogger.startMethodExecutionTimer("instreamView.unmute");
                        root.unmute();
                        hmsLogger.sendSingleEvent("instreamView.unmute");
                        break;
                    case STOP:
                        hmsLogger.startMethodExecutionTimer("instreamView.stop");
                        root.stop();
                        hmsLogger.sendSingleEvent("instreamView.stop");
                        break;
                    case PAUSE:
                        hmsLogger.startMethodExecutionTimer("instreamView.pause");
                        root.pause();
                        hmsLogger.sendSingleEvent("instreamView.pause");
                        break;
                    case PLAY:
                        hmsLogger.startMethodExecutionTimer("instreamView.play");
                        root.play();
                        hmsLogger.sendSingleEvent("instreamView.play");
                        break;
                    case DESTROY:
                        hmsLogger.startMethodExecutionTimer("instreamView.destroy");
                        root.destroy();
                        hmsLogger.sendSingleEvent("instreamView.destroy");
                        break;
                    default:
                        break;
                }
            }
        }

        @ReactProp(name = "adParam")
        public void setAdParam(final HMSAdsInstreamView view, final ReadableMap rm) {
            hmsLogger.sendSingleEvent("instreamView.setAdParam");
            view.setAdParam(rm);
        }

        @ReactProp(name = "adId")
        public void setAdId(final HMSAdsInstreamView view, final String adId) {
            hmsLogger.sendSingleEvent("instreamView.setAdId");
            view.setAdId(adId);
        }

        @ReactProp(name = "maxCount")
        public void setMaxCount(final HMSAdsInstreamView view, final int maxCount) {
            hmsLogger.sendSingleEvent("instreamView.setMaxCount");
            view.setMaxCount(maxCount);
        }

        @ReactProp(name = "totalDuration")
        public void setTotalDuration(final HMSAdsInstreamView view, final int totalDuration) {
            hmsLogger.sendSingleEvent("instreamView.setTotalDuration");
            view.setTotalDuration(totalDuration);
        }
    }
}
