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

import android.content.pm.ActivityInfo;
import android.util.ArrayMap;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.UIManagerModule;
import com.huawei.hms.ads.vast.adapter.SdkFactory;
import com.huawei.hms.ads.vast.adapter.VastSdkConfiguration;
import com.huawei.hms.ads.vast.domain.event.AdContent;
import com.huawei.hms.ads.vast.domain.event.VastAdContent;
import com.huawei.hms.ads.vast.openalliance.ad.beans.parameter.RequestOptions;
import com.huawei.hms.ads.vast.openalliance.ad.constant.NonPersonalizedAd;
import com.huawei.hms.ads.vast.player.api.AdsRequestListener;
import com.huawei.hms.ads.vast.player.api.VastAdPlayer;
import com.huawei.hms.ads.vast.player.model.CreativeResource;
import com.huawei.hms.rn.ads.logger.HMSLogger;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import java.util.Map;

public class HMSAdsVastModule extends ReactContextBaseJavaModule {
    private static final String TAG = HMSAdsVastModule.class.getSimpleName();
    private final ReactContext reactContext;
    private HMSLogger hmsLogger;

    HMSAdsVastModule(ReactContext reactContext) {
        super((ReactApplicationContext) reactContext);
        this.reactContext = reactContext;
        hmsLogger = HMSLogger.getInstance(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSVast";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new ArrayMap<>();

        Map<String, Object> nonPersonalizedAd = new ArrayMap<>();
        nonPersonalizedAd.put("PERSONALIZED", NonPersonalizedAd.PERSONALIZED);
        nonPersonalizedAd.put("NON_PERSONALIZED", NonPersonalizedAd.NON_PERSONALIZED);
        constants.put("NonPersonalizedAd", nonPersonalizedAd);

        Map<String, Object> contentClassification = new ArrayMap<>();
        contentClassification.put("AD_CONTENT_CLASSIFICATION_W",  RequestOptions.AD_CONTENT_CLASSIFICATION_W);
        contentClassification.put("AD_CONTENT_CLASSIFICATION_PI", RequestOptions.AD_CONTENT_CLASSIFICATION_PI);
        contentClassification.put("AD_CONTENT_CLASSIFICATION_J", RequestOptions.AD_CONTENT_CLASSIFICATION_J);
        contentClassification.put("AD_CONTENT_CLASSIFICATION_A", RequestOptions.AD_CONTENT_CLASSIFICATION_A);
        constants.put("ContentClassification", contentClassification);

        Map<String, Object> tagForChild = new ArrayMap<>();
        tagForChild.put("TAG_FOR_CHILD_PROTECTION_FALSE", RequestOptions.TAG_FOR_CHILD_PROTECTION_FALSE);
        tagForChild.put("TAG_FOR_CHILD_PROTECTION_TRUE", RequestOptions.TAG_FOR_CHILD_PROTECTION_TRUE);
        tagForChild.put("TAG_FOR_CHILD_PROTECTION_UNSPECIFIED", RequestOptions.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED);
        constants.put("TagForChild", tagForChild);

        Map<String, Object> underAge = new ArrayMap<>();
        underAge.put("PROMISE_FALSE", RequestOptions.TAG_FOR_UNDER_AGE_OF_PROMISE_FALSE);
        underAge.put("PROMISE_TRUE", RequestOptions.TAG_FOR_UNDER_AGE_OF_PROMISE_TRUE);
        underAge.put("PROMISE_UNSPECIFIED", RequestOptions.TAG_FOR_UNDER_AGE_OF_PROMISE_UNSPECIFIED);
        constants.put("UnderAge", underAge);

        Map<String, Object> creativeMatchType = new ArrayMap<>();
        creativeMatchType.put("EXACT", HMSAdsVastView.CreativeMatchType.EXACT.getValue());
        creativeMatchType.put("SMART", HMSAdsVastView.CreativeMatchType.SMART.getValue());
        creativeMatchType.put("UNKNOWN", HMSAdsVastView.CreativeMatchType.UNKNOWN.getValue());
        creativeMatchType.put("ANY", HMSAdsVastView.CreativeMatchType.ANY.getValue());
        creativeMatchType.put("LANDSCAPE", HMSAdsVastView.CreativeMatchType.LANDSCAPE.getValue());
        creativeMatchType.put("PORTRAIT", HMSAdsVastView.CreativeMatchType.PORTRAIT.getValue());
        constants.put("CreativeMatchType", creativeMatchType);

        Map<String, Object> orientation = new ArrayMap<>();
        orientation.put("PORTRAIT", ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        orientation.put("LANDSCAPE", ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        constants.put("Orientation", orientation);

        return constants;
    }

    @ReactMethod
    public void init(ReadableMap rm, final Promise promise) {
        hmsLogger.startMethodExecutionTimer("VasInit");
        SdkFactory.init(reactContext, ReactUtils.getVastSdkConfigurationFromReadableMap(rm));
        hmsLogger.sendSingleEvent("VasInit");
        promise.resolve("Vast Ad SDK Factory initialized");
    }

    @ReactMethod
    public void getVastSdkConfiguration(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("getVastSdkConfiguration");
        VastSdkConfiguration vastSdkConfiguration = SdkFactory.getConfiguration();
        hmsLogger.sendSingleEvent("getVastSdkConfiguration");
        promise.resolve(ReactUtils.getWritableMapFromVastSdkConfiguration(vastSdkConfiguration));
    }

    @ReactMethod
    public void getEventProcessor(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("getEventProcessor");
        CreativeResource currentPlayerCreative = VastAdPlayer.getInstance().getCurrentLinearCreative();
        if (SdkFactory.getEventProcessor() == null) {
            return;
        }
        SdkFactory.getEventProcessor()
                .onAcceptInvitationLinear(currentPlayerCreative.getTrackingEvents(),
                        getAdContentByCreative(currentPlayerCreative));

        SdkFactory.getEventProcessor()
                .onRewind(currentPlayerCreative.getTrackingEvents(),
                        getAdContentByCreative(currentPlayerCreative), 5000,
                        currentPlayerCreative.getDuration());

        hmsLogger.sendSingleEvent("getEventProcessor");
        promise.resolve("success");
    }

    @ReactMethod
    public void updateSdkServerConfig(String slotId, final Promise promise) {
        hmsLogger.startMethodExecutionTimer("updateSdkServerConfig");
        SdkFactory.updateSdkServerConfig(slotId);
        hmsLogger.sendSingleEvent("updateSdkServerConfig");
        promise.resolve("success");
    }

    @ReactMethod
    public void userAcceptAdLicense(boolean isAcceptLicense, final Promise promise) {
        hmsLogger.startMethodExecutionTimer("userAcceptAdLicense");
        SdkFactory.userAcceptAdLicense(isAcceptLicense);
        hmsLogger.sendSingleEvent("userAcceptAdLicense");
        promise.resolve("success");
    }

    @ReactMethod
    public void getViewInfo(final int viewId, final Promise promise) {
        UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(nativeViewHierarchyManager -> {
            View view = nativeViewHierarchyManager.resolveView(viewId);
            if (view instanceof HMSAdsVastView) {
                HMSAdsVastView myView = (HMSAdsVastView) view;
                WritableMap wm = new WritableNativeMap();
                wm.putMap("AdPlayerConfigs", ReactUtils.getWritableMapFromVastAdPlayerConfigs(reactContext));
                wm.putMap("LinearAdSlot", ReactUtils.getWritableMapFromLinearAdSlot(myView.mLinearAdSlot));
                wm.putMap("AdsData", ReactUtils.getWritableMapFromAdsData(myView.mAdsData));
                wm.putMap("VideoControllerInfo", myView.getVideoControllerInfo());
                promise.resolve(wm);
            } else {
                promise.reject("NOT_AD_VIEW", "Unexpected view type");
            }
        });
    }

    private AdContent getAdContentByCreative(CreativeResource playerResource) {
        return VastAdContent.createByRequestId(playerResource.getRequestId())
                .setSlotId(playerResource.getSlotId())
                .setAssetUri(playerResource.getUrl())
                .setCreativeType(playerResource.getType())
                .setShowId(playerResource.getShowId())
                .setCreativeId(playerResource.getContentId())
                .setCreativeExtensionMap(playerResource.getTypeToCreativeExtension())
                .setAdExtensionMap(playerResource.getAdExtensionMap());
    }

    public static String getVastErrorMessages(int errorCode) {
        switch (errorCode) {
            case AdsRequestListener.LOAD_AD_FAILED:
                return "The XML content fails to be parsed.";
            case AdsRequestListener.MAIN_AD_LOAD_FAILED:
                return "The ad cannot be played or fails to be loaded.";
            case AdsRequestListener.CREATIVE_TYPE_ERROR:
                return "Incorrect type of the asset to be parsed.";
            case AdsRequestListener.ADSLOT_MORE_THAN_CREATIVE:
                return "The number of returned assets is less than that of ad units.";
            default:
                return "Unknown error";
        }
    }

    public static String getVastPlayStateChangedMessage(int playState) {
        switch (playState) {
            case 2001:
                return "An ad whose creative type is not specified is being played.";
            case 2002:
                return "Playback pauses.";
            case 2006:
                return "A video ad is being played.";
            case 2007:
                return "An image ad is being played.";
            default:
                return "Unknown Play State";
        }
    }

    public static String getVastScreenStateChangedMessage(int screenState) {
        switch (screenState) {
            case 1001:
                return "normal screen mode";
            case 1002:
                return "full screen mode";
            default:
                return "Unknown Screen State";
        }
    }
}
