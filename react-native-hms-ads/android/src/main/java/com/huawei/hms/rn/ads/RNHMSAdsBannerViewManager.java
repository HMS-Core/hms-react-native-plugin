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

public class RNHMSAdsBannerViewManager extends ViewGroupManager<RNHMSAdsBannerView> {
    private static final String TAG = RNHMSAdsBannerViewManager.class.getSimpleName();

    public enum Event {
        AD_LOADED("onAdLoaded"),
        AD_FAILED("onAdFailed"),
        AD_OPENED("onAdOpened"),
        AD_CLICKED("onAdClicked"),
        AD_CLOSED("onAdClosed"),
        AD_IMPRESSION("onAdImpression"),
        AD_LEAVE("onAdLeave");

        private String name;

        Event(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Command {
        LOAD_AD("loadAd"),
        SET_REFRESH("setRefresh"),
        PAUSE("pause"),
        RESUME("resume"),
        DESTROY("destroy");

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
        return "RNHMSAdsBannerView";
    }

    @NonNull
    @Override
    protected RNHMSAdsBannerView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new RNHMSAdsBannerView(reactContext);
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
    public void receiveCommand(@NonNull RNHMSAdsBannerView root, int commandId, @Nullable ReadableArray args) {
        if (commandId < Command.values().length) {
            switch (Command.values()[commandId]) {
                case LOAD_AD:
                    root.loadAd();
                    break;
                case SET_REFRESH:
                    assert args != null;
                    root.setBannerRefresh(args.getInt(0));
                    break;
                case PAUSE:
                    root.pause();
                    break;
                case RESUME:
                    root.resume();
                    break;
                case DESTROY:
                    root.destroy();
                    break;
                default:
                    break;
            }
        }
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

    @ReactProp(name = "adParam")
    public void setAdParam(final RNHMSAdsBannerView view, final ReadableMap adParamReadableMap) {
        view.setAdParam(adParamReadableMap);
    }

    @ReactProp(name = "bannerAdSize")
    public void setBannerAdSize(final RNHMSAdsBannerView view, final ReadableMap bannerAdSizeReadableMap) {
        view.setBannerAdSize(bannerAdSizeReadableMap);
    }

    @ReactProp(name = "adID")
    public void setAdId(final RNHMSAdsBannerView view, final String adId) {
        view.setAdId(adId);
    }
}
