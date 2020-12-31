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

package com.huawei.hms.rn.ads.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.MapBuilder;

import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.AdSize;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.RequestOptions;
import com.huawei.hms.ads.VideoConfiguration;
import com.huawei.hms.ads.VideoOperator;
import com.huawei.hms.ads.InterstitialAd;
import com.huawei.hms.ads.consent.bean.AdProvider;
import com.huawei.hms.ads.identifier.AdvertisingIdClient;
import com.huawei.hms.ads.installreferrer.api.ReferrerDetails;
import com.huawei.hms.ads.instreamad.InstreamAd;
import com.huawei.hms.ads.nativead.DislikeAdReason;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdConfiguration;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.huawei.hms.ads.reward.Reward;
import com.huawei.hms.ads.reward.RewardAd;
import com.huawei.hms.rn.ads.HMSAdsBannerView;
import com.huawei.hms.rn.ads.HMSAdsModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.huawei.hms.rn.ads.HMSAdsNativeView.getCreativeType;

public class ReactUtils {
    public interface Mapper<T, R> {
        /**
         * Used to map classes
         *
         * @param in mapped from
         * @return mapped to
         */
        R map(T in);
    }

    public interface NamedEvent {
        /**
         * Gets name of the event
         *
         * @return String of name of the event
         */
        String getName();
    }

    public interface NamedCommand {
        /**
         * Gets name of the command
         *
         * @return String of name of the command
         */
        String getName();
    }

    public static <R> List<R> mapReadableArray(ReadableArray array, Mapper<ReadableMap, R> mapper) {
        List<R> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            list.add(mapper.map(array.getMap(i)));
        }
        return list;
    }

    public static <T> WritableArray mapList(List<T> list, Mapper<T, WritableMap> mapper) {
        WritableArray array = new WritableNativeArray();
        for (T item : list) {
            array.pushMap(mapper.map(item));
        }
        return array;
    }

    public static boolean hasValidKey(ReadableMap rm, String key, ReadableType type) {
        return rm.hasKey(key) && rm.getType(key) == type;
    }

    public static WritableMap getWritableMapFromErrorCode(int errorCode) {
        WritableMap wm = new WritableNativeMap();
        wm.putInt("errorCode", errorCode);
        wm.putString("errorMessage", HMSAdsModule.getErrorMessage(errorCode));
        return wm;
    }

    public static WritableMap getWritableMapFromReward(Reward obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) return wm;
        wm.putString("name", obj.getName());
        wm.putInt("amount", obj.getAmount());
        return wm;
    }

    public static WritableMap getWritableMapFromRewardAd(RewardAd obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) return wm;
        wm.putString("userId", obj.getUserId());
        wm.putString("data", obj.getData());
        wm.putMap("reward", getWritableMapFromReward(obj.getReward()));
        wm.putBoolean("isLoaded", obj.isLoaded());
        return wm;
    }

    public static WritableMap getWritableMapFromInterstitialAd(InterstitialAd obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putString("adId", obj.getAdId());
            wm.putBoolean("isLoaded", obj.isLoaded());
            wm.putBoolean("isLoading", obj.isLoading());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromInstreamAd(InstreamAd obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putString("adSource", obj.getAdSource());
            wm.putString("adSign", obj.getAdSign());
            wm.putString("callToAction", obj.getCallToAction());
            wm.putString("whyThisAd", obj.getWhyThisAd());
            wm.putDouble("duration", obj.getDuration());
            wm.putBoolean("isClicked", obj.isClicked());
            wm.putBoolean("isExpired", obj.isExpired());
            wm.putBoolean("isImageAd", obj.isImageAd());
            wm.putBoolean("isShown", obj.isShown());
            wm.putBoolean("isVideoAd", obj.isVideoAd());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromNativeAd(NativeAd obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putString("adSign", obj.getAdSign());
            wm.putString("adSource", obj.getAdSource());
            wm.putString("description", obj.getDescription());
            wm.putString("callToAction", obj.getCallToAction());
            wm.putString("whyThisAd", obj.getWhyThisAd());
            wm.putString("uniqueId", obj.getUniqueId());
            wm.putString("creativeType", getCreativeType(obj.getCreativeType()));
            wm.putArray("dislikeAdReasons", mapList(obj.getDislikeAdReasons(),
                    ReactUtils::getWritableMapFromDislikeAdReason));
            wm.putString("title", obj.getTitle());
            wm.putMap("videoOperator", getWritableMapFromVideoOperator(obj.getVideoOperator()));
            wm.putBoolean("isCustomClickAllowed", obj.isCustomClickAllowed());
            wm.putBoolean("isCustomDislikeThisAdEnabled", obj.isCustomDislikeThisAdEnabled());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromDislikeAdReason(DislikeAdReason obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putString("description", obj.getDescription());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromVideoOperator(VideoOperator obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putDouble("aspectRatio", obj.getAspectRatio());
            wm.putBoolean("hasVideo", obj.hasVideo());
            wm.putBoolean("isCustomizeOperateEnabled", obj.isCustomizeOperateEnabled());
            wm.putBoolean("isClickToFullScreenEnabled", obj.isClickToFullScreenEnabled());
            wm.putBoolean("isMuted", obj.isMuted());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromAdProvider(AdProvider obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putString("id", obj.getId());
            wm.putString("name", obj.getName());
            wm.putString("privacyPolicyUrl", obj.getPrivacyPolicyUrl());
            wm.putString("serviceArea", obj.getServiceArea());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromAdSize(AdSize obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putInt("height", obj.getHeight());
            wm.putInt("width", obj.getWidth());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromBannerAdSizeWithContext(BannerAdSize obj, Context context) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putInt("height", obj.getHeight());
            wm.putInt("width", obj.getWidth());
            wm.putInt("heightPx", obj.getHeightPx(context));
            wm.putInt("widthPx", obj.getWidthPx(context));
            wm.putBoolean("isAutoHeightSize", obj.isAutoHeightSize());
            wm.putBoolean("isDynamicSize", obj.isDynamicSize());
            wm.putBoolean("isFullWidthSize", obj.isFullWidthSize());
        }
        return wm;
    }

    public static BannerAdSize getBannerAdSizeFromReadableMap(Context context, ReadableMap rm) {
        if (rm == null || context == null || !rm.hasKey("bannerAdSize")
                || rm.getType("bannerAdSize") != ReadableType.String
                || rm.getString("bannerAdSize") == null) {
            return BannerAdSize.BANNER_SIZE_SMART;
        }
        String bannerAdSize = rm.getString("bannerAdSize");
        int width = 0;
        if (hasValidKey(rm, "width", ReadableType.Number)) {
            width = rm.getInt("width");
        }
        if (bannerAdSize != null) {
            switch (HMSAdsBannerView.BannerSize.forValue(bannerAdSize)) {
                case B_CURRENT_DIRECTION:
                    return BannerAdSize.getCurrentDirectionBannerSize(context, width);
                case B_LANDSCAPE:
                    return BannerAdSize.getLandscapeBannerSize(context, width);
                case B_PORTRAIT:
                    return BannerAdSize.getPortraitBannerSize(context, width);
                case B_DYNAMIC:
                    return BannerAdSize.BANNER_SIZE_DYNAMIC;
                case B_INVALID:
                    return BannerAdSize.BANNER_SIZE_INVALID;
                case B_160_600:
                    return BannerAdSize.BANNER_SIZE_160_600;
                case B_300_250:
                    return BannerAdSize.BANNER_SIZE_300_250;
                case B_320_50:
                    return BannerAdSize.BANNER_SIZE_320_50;
                case B_320_100:
                    return BannerAdSize.BANNER_SIZE_320_100;
                case B_360_57:
                    return BannerAdSize.BANNER_SIZE_360_57;
                case B_360_144:
                    return BannerAdSize.BANNER_SIZE_360_144;
                case B_468_60:
                    return BannerAdSize.BANNER_SIZE_468_60;
                case B_728_90:
                    return BannerAdSize.BANNER_SIZE_728_90;
                default:
                    break;
            }
        }
        return BannerAdSize.BANNER_SIZE_SMART;
    }

    public static AdSize getAdSizeFromReadableMap(ReadableMap rm) {
        if (rm != null && ReactUtils.hasValidKey(rm, "height", ReadableType.Number)
                && ReactUtils.hasValidKey(rm, "width", ReadableType.Number)) {
            return new AdSize(rm.getInt("height"), rm.getInt("width"));
        }
        return new AdSize(0, 0);
    }

    public static WritableMap getWritableMapFromVideoConfiguration(VideoConfiguration obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putInt("audioFocusType", obj.getAudioFocusType());
            wm.putBoolean("isCustomizeOperateRequested", obj.isCustomizeOperateRequested());
            wm.putBoolean("isClickToFullScreenRequested", obj.isClickToFullScreenRequested());
            wm.putBoolean("isStartMuted", obj.isStartMuted());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromNativeAdConfiguration(NativeAdConfiguration obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putMap("adSize", getWritableMapFromAdSize(obj.getAdSize()));
            wm.putInt("choicesPosition", obj.getChoicesPosition());
            wm.putInt("mediaDirection", obj.getMediaDirection());
            wm.putInt("mediaAspect", obj.getMediaAspect());
            wm.putMap("videoConfiguration", getWritableMapFromVideoConfiguration(obj.getVideoConfiguration()));
            wm.putBoolean("isRequestMultiImages", obj.isRequestMultiImages());
            wm.putBoolean("isReturnUrlsForImages", obj.isReturnUrlsForImages());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromNativeAdLoader(NativeAdLoader obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putBoolean("isLoading", obj.isLoading());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromAdvertisingIdClientInfo(AdvertisingIdClient.Info obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putString("id", obj.getId());
            wm.putBoolean("isLimitAdTrackingEnabled", obj.isLimitAdTrackingEnabled());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromReferrerDetails(ReferrerDetails obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putString("installReferrer", obj.getInstallReferrer());
            wm.putDouble("installBeginTimestampMillisecond", obj.getInstallBeginTimestampMillisecond());
            wm.putDouble("installBeginTimestampSeconds", obj.getInstallBeginTimestampSeconds());
            wm.putDouble("referrerClickTimestampMillisecond", obj.getReferrerClickTimestampMillisecond());
            wm.putDouble("referrerClickTimestampSeconds", obj.getReferrerClickTimestampSeconds());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromRequestOptions(RequestOptions obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            if (obj.getAdContentClassification() != null) {
                wm.putString("adContentClassification", obj.getAdContentClassification());
            }
            if (obj.getAppCountry() != null) {
                wm.putString("appCountry", obj.getAppCountry());
            }
            if (obj.getAppLang() != null) {
                wm.putString("appLang", obj.getAppLang());
            }
            if (obj.getNonPersonalizedAd() != null) {
                wm.putInt("nonPersonalizedAd", obj.getNonPersonalizedAd());
            }
            if (obj.getTagForChildProtection() != null) {
                wm.putInt("tagForChildProtection", obj.getTagForChildProtection());
            }
            if (obj.getTagForUnderAgeOfPromise() != null) {
                wm.putInt("tagForUnderAgeOfPromise", obj.getTagForUnderAgeOfPromise());
            }
        }
        return wm;
    }

    public static RequestOptions getRequestOptionsFromReadableMap(ReadableMap rm) {
        RequestOptions.Builder obj = new RequestOptions.Builder();
        if (rm != null) {
            if (hasValidKey(rm, "adContentClassification", ReadableType.String)) {
                obj.setAdContentClassification(rm.getString("adContentClassification"));
            }
            if (hasValidKey(rm, "appCountry", ReadableType.String)) {
                obj.setAppCountry(rm.getString("appCountry"));
            }
            if (hasValidKey(rm, "appLang", ReadableType.String)) {
                obj.setAppLang(rm.getString("appLang"));
            }
            if (hasValidKey(rm, "nonPersonalizedAd", ReadableType.Number)) {
                obj.setNonPersonalizedAd(rm.getInt("nonPersonalizedAd"));
            }
            if (hasValidKey(rm, "tagForChildProtection", ReadableType.Number)) {
                obj.setTagForChildProtection(rm.getInt("tagForChildProtection"));
            }
            if (hasValidKey(rm, "tagForUnderAgeOfPromise", ReadableType.Number)) {
                obj.setTagForUnderAgeOfPromise(rm.getInt("tagForUnderAgeOfPromise"));
            }
        }
        return obj.build();
    }

    public static AdParam getAdParamFromReadableMap(ReadableMap rm) {
        AdParam.Builder obj = new AdParam.Builder();
        if (rm != null) {
            if (hasValidKey(rm, "adContentClassification", ReadableType.String)) {
                obj.setAdContentClassification(rm.getString("adContentClassification"));
            }
            if (hasValidKey(rm, "belongCountryCode", ReadableType.String)) {
                obj.setBelongCountryCode(rm.getString("belongCountryCode"));
            }
            if (hasValidKey(rm, "appCountry", ReadableType.String)) {
                obj.setAppCountry(rm.getString("appCountry"));
            }
            if (hasValidKey(rm, "gender", ReadableType.Number)) {
                obj.setGender(rm.getInt("gender"));
            }
            if (hasValidKey(rm, "appLang", ReadableType.String)) {
                obj.setAppLang(rm.getString("appLang"));
            }
            if (hasValidKey(rm, "nonPersonalizedAd", ReadableType.Number)) {
                obj.setNonPersonalizedAd(rm.getInt("nonPersonalizedAd"));
            }
            if (hasValidKey(rm, "requestOrigin", ReadableType.String)) {
                obj.setRequestOrigin(rm.getString("requestOrigin"));
            }
            if (hasValidKey(rm, "tagForChildProtection", ReadableType.Number)) {
                obj.setTagForChildProtection(rm.getInt("tagForChildProtection"));
            }
            if (hasValidKey(rm, "tagForUnderAgeOfPromise", ReadableType.Number)) {
                obj.setTagForUnderAgeOfPromise(rm.getInt("tagForUnderAgeOfPromise"));
            }
            if (hasValidKey(rm, "targetingContentUrl", ReadableType.String)) {
                obj.setTargetingContentUrl(rm.getString("targetingContentUrl"));
            }
        }
        return obj.build();
    }

    public static WritableMap getWritableMapFromAdParamBundle(Bundle obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            if (obj.containsKey("adContentClassification")) {
                wm.putString("adContentClassification", obj.getString("adContentClassification"));
            }
            if (obj.containsKey("appCountry")) {
                wm.putString("appCountry", obj.getString("appCountry"));
            }
            if (obj.containsKey("appLang")) {
                wm.putString("appLang", obj.getString("appLang"));
            }
            if (obj.containsKey("belongCountryCode")) {
                wm.putString("belongCountryCode", obj.getString("belongCountryCode"));
            }
            if (obj.containsKey("gender")) {
                wm.putInt("gender", obj.getInt("gender"));
            }
            if (obj.containsKey("nonPersonalizedAd")) {
                wm.putInt("nonPersonalizedAd", obj.getInt("nonPersonalizedAd"));
            }
            if (obj.containsKey("requestOrigin")) {
                wm.putString("requestOrigin", obj.getString("requestOrigin"));
            }
            if (obj.containsKey("tagForChildProtection")) {
                wm.putInt("tagForChildProtection", obj.getInt("tagForChildProtection"));
            }
            if (obj.containsKey("tagForUnderAgeOfPromise")) {
                wm.putInt("tagForUnderAgeOfPromise", obj.getInt("tagForUnderAgeOfPromise"));
            }
            if (obj.containsKey("targetingContentUrl")) {
                wm.putString("targetingContentUrl", obj.getString("targetingContentUrl"));
            }
        }
        return wm;
    }

    public static Map<String, Object> getExportedCustomDirectEventTypeConstantsFromEvents(NamedEvent[] eventList) {
        Map<String, Object> obj = new ArrayMap<>();
        for (NamedEvent event : eventList) {
            obj.put(event.getName(), MapBuilder.of("registrationName", event.getName()));
        }
        return obj;
    }

    public static Map<String, Integer> getCommandsMap(NamedCommand[] commandList) {
        Map<String, Integer> obj = new ArrayMap<>();
        for (int i = 0; i < commandList.length; i++) {
            obj.put(commandList[i].getName(), i);
        }
        return obj;
    }

    public static Bundle getBundleFromReadableMap(ReadableMap rm) {
        Bundle obj = new Bundle();
        if (rm != null) {
            obj.putSerializable("data", rm.toHashMap());
        }
        return obj;
    }
}
