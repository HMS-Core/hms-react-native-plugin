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

import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import com.huawei.hms.rn.ads.utils.ReactUtils;

import java.util.Map;

public class RNHMSAdsNativeViewManager extends ViewGroupManager<RNHMSAdsNativeView> {
    private static final String TAG = RNHMSAdsNativeViewManager.class.getSimpleName();

    public enum Event {
        NATIVE_AD_LOADED("onNativeAdLoaded"),
        AD_DISLIKED("onAdDisliked"),
        AD_FAILED("onAdFailed"),
        AD_CLICKED("onAdClicked"),
        AD_IMPRESSION("onAdImpression"),
        AD_VIDEO_START("onVideoStart"),
        AD_VIDEO_PLAY("onVideoPlay"),
        AD_VIDEO_END("onVideoEnd");

        private String name;

        Event(String name) {
            this.name = name ;
        }

        public String getName() {
            return name;
        }
    }

    public enum Command {
        LOAD_AD("loadAd"),
        DISLIKE_AD("dislikeAd"),
        DESTROY("destroy"),
        GO_TO_WHY("gotoWhyThisAdPage"),
        ALLOW_CUSTOM_CLICK("setAllowCustomClick"),
        RECORD_CLICK("recordClickEvent"),
        RECORD_IMPRESSION("recordImpressionEvent");

        private String name;

        Command(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @NonNull
    @Override
    public String getName() {
        return "RNHMSAdsNativeView";
    }

    @NonNull
    @Override
    protected RNHMSAdsNativeView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new RNHMSAdsNativeView(reactContext);
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        Event[] eventList = Event.values();
        String[] events = new String[eventList.length];
        for (int i = 0; i < eventList.length; i++) {
            events[i] = eventList[i].getName();
        }
        return ReactUtils.getExportedCustomDirectEventTypeConstantsFromEvents(events);
    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        Command[] commandList = Command.values();
        Map<String, Integer> obj = new ArrayMap<>();
        for (int i = 0; i < commandList.length; i++) {
            obj.put(commandList[i].getName(), i);
        }
        return obj;
    }

    @Override
    public void receiveCommand(@NonNull RNHMSAdsNativeView root, int commandId, @Nullable ReadableArray args) {
        if (commandId < Command.values().length) {
            switch (Command.values()[commandId]) {
                case LOAD_AD:
                    root.loadAd();
                    break;
                case DISLIKE_AD:
                    assert args != null;
                    root.dislikeAd(args.getString(0));
                    break;
                case DESTROY:
                    root.destroy();
                    break;
                case GO_TO_WHY:
                    root.gotoWhyThisAdPage();
                    break;
                case ALLOW_CUSTOM_CLICK:
                    root.setAllowCustomClick();
                    break;
                case RECORD_CLICK:
                    root.recordClickEvent();
                    break;
                case RECORD_IMPRESSION:
                    assert args != null;
                    root.recordImpressionEvent(args.getMap(0));
                    break;
                default:
                    break;
            }
        }
    }

    @ReactProp(name = "adParam")
    public void setAdParam(final RNHMSAdsNativeView view, final ReadableMap adParamReadableMap) {
        view.setAdParam(adParamReadableMap);
    }

    @ReactProp(name = "displayForm")
    public void setDisplayForm(final RNHMSAdsNativeView view, final ReadableMap displayForm) {
        view.setDisplayForm(displayForm);
    }

    @ReactProp(name = "nativeConfig")
    public void setNativeConfig(final RNHMSAdsNativeView view, final ReadableMap nativeAdConfiguration) {
        view.setNativeAdConfiguration(nativeAdConfiguration);
    }

    @ReactProp(name = "viewOptions")
    public void setViewOptions(final RNHMSAdsNativeView view, final ReadableMap viewOptions) {
        view.setViewOptions(viewOptions);
    }
}
