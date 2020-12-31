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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.rn.ads.logger.HMSLogger;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import java.util.Map;

public class HMSAdsBannerView extends BannerView {
    private static final String TAG = HMSAdsBannerView.class.getSimpleName();
    private ReactContext mReactContext;
    protected ReadableMap mAdParamReadableMap;

    public enum BannerMediaType {
        IMAGE("image");

        private String value;

        BannerMediaType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum BannerSize {
        B_160_600("160_600"),
        B_300_250("300_250"),
        B_320_50("320_50"),
        B_320_100("320_100"),
        B_360_57("360_57"),
        B_360_144("360_144"),
        B_468_60("468_60"),
        B_728_90("728_90"),
        B_CURRENT_DIRECTION("currentDirection"),
        B_PORTRAIT("portrait"),
        B_SMART("smart"),
        B_DYNAMIC("dynamic"),
        B_LANDSCAPE("landscape"),
        B_INVALID("invalid");

        private String value;

        BannerSize(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static BannerSize forValue(String s) {
            switch (s) {
                case "160_600":
                    return B_160_600;
                case "300_250":
                    return B_300_250;
                case "320_50":
                    return B_320_50;
                case "320_100":
                    return B_320_100;
                case "360_57":
                    return B_360_57;
                case "360_144":
                    return B_360_144;
                case "468_60":
                    return B_468_60;
                case "728_90":
                    return B_728_90;
                case "currentDirection":
                    return B_CURRENT_DIRECTION;
                case "portrait":
                    return B_PORTRAIT;
                case "dynamic":
                    return B_DYNAMIC;
                case "landscape":
                    return B_LANDSCAPE;
                case "invalid":
                    return B_INVALID;
                default:
                    return B_SMART;
            }
        }
    }

    public HMSAdsBannerView(final Context context) {
        super(context);
    }

    public HMSAdsBannerView(final ThemedReactContext context) {
        super(context);
        mReactContext = context;
        setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                sendEvent(Manager.Event.AD_LOADED, null);
            }

            @Override
            public void onAdFailed(int errorCode) {
                sendEvent(Manager.Event.AD_FAILED, ReactUtils.getWritableMapFromErrorCode(errorCode));
            }

            @Override
            public void onAdOpened() {
                sendEvent(Manager.Event.AD_OPENED, null);
            }

            @Override
            public void onAdClicked() {
                sendEvent(Manager.Event.AD_CLICKED, null);
            }

            @Override
            public void onAdClosed() {
                sendEvent(Manager.Event.AD_CLOSED, null);
            }

            @Override
            public void onAdImpression() {
                sendEvent(Manager.Event.AD_IMPRESSION, null);
            }

            @Override
            public void onAdLeave() {
                sendEvent(Manager.Event.AD_LEAVE, null);
            }
        });
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(() -> {
            measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        });
    }

    private void sendEvent(Manager.Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), event.getName(), wm);
    }

    void loadAd() {
        loadAd(ReactUtils.getAdParamFromReadableMap(mAdParamReadableMap));
    }

    public void setAdParam(ReadableMap adParamReadableMap) {
        mAdParamReadableMap = adParamReadableMap;
    }

    public static class Manager extends ViewGroupManager<HMSAdsBannerView> {
        private HMSLogger hmsLogger;
        private ReactApplicationContext mReactContext;

        public Manager(ReactApplicationContext reactContext) {
            mReactContext = reactContext;
            hmsLogger = HMSLogger.getInstance(reactContext);
        }

        public enum Event implements ReactUtils.NamedEvent {
            AD_LOADED("onAdLoaded"),
            AD_FAILED("onAdFailed"),
            AD_OPENED("onAdOpened"),
            AD_CLICKED("onAdClicked"),
            AD_CLOSED("onAdClosed"),
            AD_IMPRESSION("onAdImpression"),
            AD_LEAVE("onAdLeave");

            private String bannerEventName;

            Event(String bannerEventName) {
                this.bannerEventName = bannerEventName;
            }

            public String getName() {
                return bannerEventName;
            }
        }

        public enum Command implements ReactUtils.NamedCommand {
            LOAD_AD("loadAd"),
            SET_REFRESH("setRefresh"),
            PAUSE("pause"),
            RESUME("resume"),
            DESTROY("destroy");

            private String bannerCommandName;

            Command(String bannerCommandName) {
                this.bannerCommandName = bannerCommandName;
            }

            public String getName() {
                return bannerCommandName;
            }
        }

        @NonNull
        @Override
        public String getName() {
            return "HMSAdsBannerView";
        }

        @NonNull
        @Override
        protected HMSAdsBannerView createViewInstance(@NonNull ThemedReactContext reactContext) {
            hmsLogger.sendSingleEvent("bannerView.create");
            return new HMSAdsBannerView(reactContext);
        }

        @Nullable
        @Override
        public Map<String, Integer> getCommandsMap() {
            return ReactUtils.getCommandsMap(Manager.Command.values());
        }

        @Override
        public void receiveCommand(@NonNull HMSAdsBannerView root, int commandId, @Nullable ReadableArray args) {
            if (commandId < Manager.Command.values().length) {
                switch (Manager.Command.values()[commandId]) {
                    case LOAD_AD:
                        hmsLogger.startMethodExecutionTimer("bannerView.loadAd");
                        root.loadAd();
                        hmsLogger.sendSingleEvent("bannerView.loadAd");
                        break;
                    case SET_REFRESH:
                        assert args != null;
                        hmsLogger.startMethodExecutionTimer("bannerView.setBannerRefresh");
                        root.setBannerRefresh(args.getInt(0));
                        hmsLogger.sendSingleEvent("bannerView.setBannerRefresh");
                        break;
                    case PAUSE:
                        hmsLogger.startMethodExecutionTimer("bannerView.pause");
                        root.pause();
                        hmsLogger.sendSingleEvent("bannerView.pause");
                        break;
                    case RESUME:
                        hmsLogger.startMethodExecutionTimer("bannerView.resume");
                        root.resume();
                        hmsLogger.sendSingleEvent("bannerView.resume");
                        break;
                    case DESTROY:
                        hmsLogger.startMethodExecutionTimer("bannerView.destroy");
                        root.destroy();
                        hmsLogger.sendSingleEvent("bannerView.destroy");
                        break;
                    default:
                        break;
                }
            }
        }

        @Nullable
        @Override
        public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
            return ReactUtils.getExportedCustomDirectEventTypeConstantsFromEvents(Manager.Event.values());
        }

        @ReactProp(name = "adParam")
        public void setAdParam(final HMSAdsBannerView view, final ReadableMap rm) {
            hmsLogger.sendSingleEvent("bannerView.setAdParam");
            view.setAdParam(rm);
        }

        @ReactProp(name = "bannerAdSize")
        public void setBannerAdSize(final HMSAdsBannerView view, final ReadableMap rm) {
            hmsLogger.sendSingleEvent("bannerView.setBannerAdSize");
            view.setBannerAdSize(ReactUtils.getBannerAdSizeFromReadableMap(mReactContext, rm));
        }

        @ReactProp(name = "adId")
        public void setAdId(final HMSAdsBannerView view, final String adId) {
            hmsLogger.sendSingleEvent("bannerView.setAdId");
            view.setAdId(adId);
        }
    }
}
