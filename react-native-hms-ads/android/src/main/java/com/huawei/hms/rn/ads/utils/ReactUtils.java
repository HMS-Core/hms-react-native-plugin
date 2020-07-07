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

package com.huawei.hms.rn.ads.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;

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
import com.huawei.hms.ads.nativead.DislikeAdReason;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdConfiguration;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.huawei.hms.ads.reward.Reward;
import com.huawei.hms.ads.reward.RewardAd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReactUtils {
    private static final String TAG = ReactUtils.class.getSimpleName();

    public interface Mapper<T, R> {
        /**
         * Used to map classes
         *
         * @param in mapped from
         * @return mapped to
         */
        R map(T in);
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

    public static WritableMap getWritableMapFromReward(Reward obj) {
        Log.i(TAG, "Getting serialized Reward object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "Reward object is serialized");
            return wm;
        }
        wm.putString("name", obj.getName());
        Log.i(TAG, "name attribute is set");
        wm.putInt("amount", obj.getAmount());
        Log.i(TAG, "amount attribute is set");
        Log.i(TAG, "Reward object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromRewardAd(RewardAd obj) {
        Log.i(TAG, "Getting serialized RewardAd object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "RewardAd object is serialized");
            return wm;
        }
        wm.putString("userId", obj.getUserId());
        Log.i(TAG, "userId attribute is set");
        wm.putString("data", obj.getData());
        Log.i(TAG, "data attribute is set");
        wm.putMap("reward", getWritableMapFromReward(obj.getReward()));
        Log.i(TAG, "reward attribute is set");
        wm.putBoolean("isLoaded", obj.isLoaded());
        Log.i(TAG, "isLoaded attribute is set");
        Log.i(TAG, "RewardAd object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromInterstitialAd(InterstitialAd obj) {
        Log.i(TAG, "Getting serialized InterstitialAd object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "InterstitialAd object is serialized");
            return wm;
        }
        wm.putString("adId", obj.getAdId());
        Log.i(TAG, "adId attribute is set");
        wm.putBoolean("isLoaded", obj.isLoaded());
        Log.i(TAG, "isLoaded attribute is set");
        wm.putBoolean("isLoading", obj.isLoading());
        Log.i(TAG, "isLoading attribute is set");
        Log.i(TAG, "InterstitialAd object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromNativeAd(NativeAd obj) {
        Log.i(TAG, "Getting serialized NativeAd object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "NativeAd object is serialized");
            return wm;
        }
        wm.putString("adSource", obj.getAdSource());
        Log.i(TAG, "adSource attribute is set");
        wm.putString("description", obj.getDescription());
        Log.i(TAG, "description attribute is set");
        wm.putString("callToAction", obj.getCallToAction());
        Log.i(TAG, "callToAction attribute is set");
        wm.putArray("dislikeAdReasons", mapList(obj.getDislikeAdReasons(),
                ReactUtils::getWritableMapFromDislikeAdReason));
        Log.i(TAG, "dislikeAdReasons attribute is set");
        wm.putString("title", obj.getTitle());
        Log.i(TAG, "title attribute is set");
        wm.putMap("videoOperator", getWritableMapFromVideoOperator(obj.getVideoOperator()));
        Log.i(TAG, "videoOperator attribute is set");
        wm.putBoolean("isCustomClickAllowed", obj.isCustomClickAllowed());
        Log.i(TAG, "isCustomClickAllowed attribute is set");
        wm.putBoolean("isCustomDislikeThisAdEnabled", obj.isCustomDislikeThisAdEnabled());
        Log.i(TAG, "isCustomDislikeThisAdEnabled attribute is set");
        Log.i(TAG, "NativeAd object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromDislikeAdReason(DislikeAdReason obj) {
        Log.i(TAG, "Getting serialized DislikeAdReason object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "DislikeAdReason object is serialized");
            return wm;
        }
        wm.putString("description", obj.getDescription());
        Log.i(TAG, "description attribute is set");
        Log.i(TAG, "DislikeAdReason object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromVideoOperator(VideoOperator obj) {
        Log.i(TAG, "Getting serialized VideoOperator object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "VideoOperator object is serialized");
            return wm;
        }
        wm.putDouble("aspectRatio", obj.getAspectRatio());
        Log.i(TAG, "aspectRatio attribute is set");
        wm.putBoolean("hasVideo", obj.hasVideo());
        Log.i(TAG, "hasVideo attribute is set");
        wm.putBoolean("isCustomizeOperateEnabled", obj.isCustomizeOperateEnabled());
        Log.i(TAG, "isCustomizeOperateEnabled attribute is set");
        wm.putBoolean("isClickToFullScreenEnabled", obj.isClickToFullScreenEnabled());
        Log.i(TAG, "isClickToFullScreenEnabled attribute is set");
        wm.putBoolean("isMuted", obj.isMuted());
        Log.i(TAG, "isMuted attribute is set");
        Log.i(TAG, "VideoOperator object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromAdProvider(AdProvider obj) {
        Log.i(TAG, "Getting serialized AdProvider object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "AdProvider object is serialized");
            return wm;
        }
        wm.putString("id", obj.getId());
        Log.i(TAG, "id attribute is set");
        wm.putString("name", obj.getName());
        Log.i(TAG, "name attribute is set");
        wm.putString("privacyPolicyUrl", obj.getPrivacyPolicyUrl());
        Log.i(TAG, "privacyPolicyUrl attribute is set");
        wm.putString("serviceArea", obj.getServiceArea());
        Log.i(TAG, "serviceArea attribute is set");
        Log.i(TAG, "AdProvider object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromAdSize(AdSize obj) {
        Log.i(TAG, "Getting serialized AdSize object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "AdSize object is serialized");
            return wm;
        }
        wm.putInt("height", obj.getHeight());
        Log.i(TAG, "height attribute is set");
        wm.putInt("width", obj.getWidth());
        Log.i(TAG, "width attribute is set");
        Log.i(TAG, "AdSize object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromBannerAdSizeWithContext(BannerAdSize obj, Context context) {
        Log.i(TAG, "Getting serialized BannerAdSize object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "BannerAdSize object is serialized");
            return wm;
        }
        wm.putInt("height", obj.getHeight());
        Log.i(TAG, "height attribute is set");
        wm.putInt("width", obj.getWidth());
        Log.i(TAG, "width attribute is set");
        wm.putInt("heightPx", obj.getHeightPx(context));
        Log.i(TAG, "heightPx attribute is set");
        wm.putInt("widthPx", obj.getWidthPx(context));
        Log.i(TAG, "widthPx attribute is set");
        wm.putBoolean("isAutoHeightSize", obj.isAutoHeightSize());
        Log.i(TAG, "isAutoHeightSize attribute is set");
        wm.putBoolean("isDynamicSize", obj.isDynamicSize());
        Log.i(TAG, "isDynamicSize attribute is set");
        wm.putBoolean("isFullWidthSize", obj.isFullWidthSize());
        Log.i(TAG, "isFullWidthSize attribute is set");
        Log.i(TAG, "BannerAdSize object is serialized");
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
        if (rm.hasKey("width")
                && rm.getType("width") == ReadableType.Number) {
            width = rm.getInt("width");
        }
        if (bannerAdSize != null) {
            switch (bannerAdSize) {
                case "currentDirection":
                    Log.i(TAG, "BannerAdSize.getCurrentDirectionBannerSize() is called");
                    return BannerAdSize.getCurrentDirectionBannerSize(context, width);
                case "landscape":
                    Log.i(TAG, "BannerAdSize.getLandscapeBannerSize() is called");
                    return BannerAdSize.getLandscapeBannerSize(context, width);
                case "portrait":
                    Log.i(TAG, "BannerAdSize.getPortraitBannerSize() is called");
                    return BannerAdSize.getPortraitBannerSize(context, width);
                case "dynamic":
                    return BannerAdSize.BANNER_SIZE_DYNAMIC;
                case "invalid":
                    return BannerAdSize.BANNER_SIZE_INVALID;
                case "160_600":
                    return BannerAdSize.BANNER_SIZE_160_600;
                case "300_250":
                    return BannerAdSize.BANNER_SIZE_300_250;
                case "320_50":
                    return BannerAdSize.BANNER_SIZE_320_50;
                case "320_100":
                    return BannerAdSize.BANNER_SIZE_320_100;
                case "360_57":
                    return BannerAdSize.BANNER_SIZE_360_57;
                case "360_144":
                    return BannerAdSize.BANNER_SIZE_360_144;
                case "468_60":
                    return BannerAdSize.BANNER_SIZE_468_60;
                case "728_90":
                    return BannerAdSize.BANNER_SIZE_728_90;
                default:
                    break;
            }
        }
        return BannerAdSize.BANNER_SIZE_SMART;
    }

    public static AdSize getAdSizeFromReadableMap(ReadableMap rm) {
        Log.i(TAG, "AdSize object is created.");
        if (rm != null && ReactUtils.hasValidKey(rm, "height", ReadableType.Number) && ReactUtils.hasValidKey(rm,
                "width", ReadableType.Number)) {
            return new AdSize(rm.getInt("height"), rm.getInt("width"));
        }
        return new AdSize(0, 0);
    }

    public static WritableMap getWritableMapFromVideoConfiguration(VideoConfiguration obj) {
        Log.i(TAG, "Getting serialized VideoConfiguration object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "VideoConfiguration object is serialized");
            return wm;
        }
        wm.putInt("audioFocusType", obj.getAudioFocusType());
        Log.i(TAG, "audioFocusType attribute is set");
        wm.putBoolean("isCustomizeOperateRequested", obj.isCustomizeOperateRequested());
        Log.i(TAG, "isCustomizeOperateRequested attribute is set");
        wm.putBoolean("isStartMuted", obj.isStartMuted());
        Log.i(TAG, "isStartMuted attribute is set");
        Log.i(TAG, "VideoConfiguration object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromNativeAdConfiguration(NativeAdConfiguration obj) {
        Log.i(TAG, "Getting serialized NativeAdConfiguration object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "NativeAdConfiguration object is serialized");
            return wm;
        }
        wm.putMap("adSize", getWritableMapFromAdSize(obj.getAdSize()));
        Log.i(TAG, "adSize attribute is set");
        wm.putInt("choicesPosition", obj.getChoicesPosition());
        Log.i(TAG, "choicesPosition attribute is set");
        wm.putInt("mediaDirection", obj.getMediaDirection());
        Log.i(TAG, "mediaDirection attribute is set");
        wm.putInt("mediaAspect", obj.getMediaAspect());
        Log.i(TAG, "mediaAspect attribute is set");
        wm.putMap("videoConfiguration", getWritableMapFromVideoConfiguration(obj.getVideoConfiguration()));
        Log.i(TAG, "videoConfiguration attribute is set");
        wm.putBoolean("isRequestMultiImages", obj.isRequestMultiImages());
        Log.i(TAG, "isRequestMultiImages attribute is set");
        wm.putBoolean("isReturnUrlsForImages", obj.isReturnUrlsForImages());
        Log.i(TAG, "isReturnUrlsForImages attribute is set");
        Log.i(TAG, "NativeAdConfiguration object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromNativeAdLoader(NativeAdLoader obj) {
        Log.i(TAG, "Getting serialized NativeAdLoader object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "NativeAdLoader object is serialized");
            return wm;
        }
        wm.putBoolean("isLoading", obj.isLoading());
        Log.i(TAG, "isLoading attribute is set");
        Log.i(TAG, "NativeAdLoader object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromAdvertisingIdClientInfo(AdvertisingIdClient.Info obj) {
        Log.i(TAG, "Getting serialized AdvertisingIdClient.Info object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "AdvertisingIdClient.Info object is serialized");
            return wm;
        }
        wm.putString("id", obj.getId());
        Log.i(TAG, "id attribute is set");
        wm.putBoolean("isLimitAdTrackingEnabled", obj.isLimitAdTrackingEnabled());
        Log.i(TAG, "isLimitAdTrackingEnabled attribute is set");
        Log.i(TAG, "AdvertisingIdClient.Info object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromReferrerDetails(ReferrerDetails obj) {
        Log.i(TAG, "Getting serialized ReferrerDetails object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "ReferrerDetails object is serialized");
            return wm;
        }
        wm.putString("installReferrer", obj.getInstallReferrer());
        Log.i(TAG, "installReferrer attribute is set");
        wm.putDouble("installBeginTimestampMillisecond", obj.getInstallBeginTimestampMillisecond());
        Log.i(TAG, "installBeginTimestampMillisecond attribute is set");
        wm.putDouble("installBeginTimestampSeconds", obj.getInstallBeginTimestampSeconds());
        Log.i(TAG, "installBeginTimestampSeconds attribute is set");
        wm.putDouble("referrerClickTimestampMillisecond", obj.getReferrerClickTimestampMillisecond());
        Log.i(TAG, "referrerClickTimestampMillisecond attribute is set");
        wm.putDouble("referrerClickTimestampSeconds", obj.getReferrerClickTimestampSeconds());
        Log.i(TAG, "referrerClickTimestampSeconds attribute is set");
        Log.i(TAG, "ReferrerDetails object is serialized");
        return wm;
    }

    public static WritableMap getWritableMapFromRequestOptions(RequestOptions obj) {
        Log.i(TAG, "Getting serialized RequestOptions object");
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            Log.i(TAG, "RequestOptions object is serialized");
            return wm;
        }
        if (obj.getAdContentClassification() != null) {
            wm.putString("adContentClassification", obj.getAdContentClassification());
            Log.i(TAG, "adContentClassification attribute is set");
        }
        if (obj.getAppCountry() != null) {
            wm.putString("appCountry", obj.getAppCountry());
            Log.i(TAG, "appCountry attribute is set");
        }
        if (obj.getAppLang() != null) {
            wm.putString("appLang", obj.getAppLang());
            Log.i(TAG, "appLang attribute is set");
        }
        if (obj.getNonPersonalizedAd() != null) {
            wm.putInt("nonPersonalizedAd", obj.getNonPersonalizedAd());
            Log.i(TAG, "nonPersonalizedAd attribute is set");
        }
        if (obj.getTagForChildProtection() != null) {
            wm.putInt("tagForChildProtection", obj.getTagForChildProtection());
            Log.i(TAG, "tagForChildProtection attribute is set");
        }
        if (obj.getTagForUnderAgeOfPromise() != null) {
            wm.putInt("tagForUnderAgeOfPromise", obj.getTagForUnderAgeOfPromise());
            Log.i(TAG, "tagForUnderAgeOfPromise attribute is set");
        }
        Log.i(TAG, "RequestOptions object is serialized");
        return wm;
    }

    public static RequestOptions getRequestOptionsFromReadableMap(ReadableMap rm) {
        Log.i(TAG, "RequestOptions object is being created...");
        RequestOptions.Builder obj = new RequestOptions.Builder();
        if (rm == null) {
            Log.i(TAG, "RequestOptions object is created.");
            return obj.build();
        }
        if (hasValidKey(rm, "adContentClassification", ReadableType.String)) {
            obj.setAdContentClassification(rm.getString("adContentClassification"));
            Log.i(TAG, "adContentClassification attribute is set.");
        }
        if (hasValidKey(rm, "appCountry", ReadableType.String)) {
            obj.setAppCountry(rm.getString("appCountry"));
            Log.i(TAG, "appCountry attribute is set.");
        }
        if (hasValidKey(rm, "appLang", ReadableType.String)) {
            obj.setAppLang(rm.getString("appLang"));
            Log.i(TAG, "appLang attribute is set.");
        }
        if (hasValidKey(rm, "nonPersonalizedAd", ReadableType.Number)) {
            obj.setNonPersonalizedAd(rm.getInt("nonPersonalizedAd"));
            Log.i(TAG, "nonPersonalizedAd attribute is set.");
        }
        if (hasValidKey(rm, "tagForChildProtection", ReadableType.Number)) {
            obj.setTagForChildProtection(rm.getInt("tagForChildProtection"));
            Log.i(TAG, "tagForChildProtection attribute is set.");
        }
        if (hasValidKey(rm, "tagForUnderAgeOfPromise", ReadableType.Number)) {
            obj.setTagForUnderAgeOfPromise(rm.getInt("tagForUnderAgeOfPromise"));
            Log.i(TAG, "tagForUnderAgeOfPromise attribute is set.");
        }
        Log.i(TAG, "RequestOptions object is created.");
        return obj.build();
    }

    public static boolean hasValidKey(ReadableMap rm, String key, ReadableType type) {
        return rm.hasKey(key) && rm.getType(key) == type;
    }

    public static AdParam getAdParamFromReadableMap(ReadableMap rm) {
        Log.i(TAG, "AdParam object is being created...");
        AdParam.Builder obj = new AdParam.Builder();
        if (rm == null) {
            Log.i(TAG, "AdParam object is created.");
            return obj.build();
        }
        if (hasValidKey(rm, "adContentClassification", ReadableType.String)) {
            obj.setAdContentClassification(rm.getString("adContentClassification"));
            Log.i(TAG, "adContentClassification attribute is set.");
        }
        if (hasValidKey(rm, "belongCountryCode", ReadableType.String)) {
            obj.setBelongCountryCode(rm.getString("belongCountryCode"));
            Log.i(TAG, "belongCountryCode attribute is set.");
        }
        if (hasValidKey(rm, "appCountry", ReadableType.String)) {
            obj.setAppCountry(rm.getString("appCountry"));
            Log.i(TAG, "appCountry attribute is set.");
        }
        if (hasValidKey(rm, "gender", ReadableType.Number)) {
            obj.setGender(rm.getInt("gender"));
            Log.i(TAG, "gender attribute is set.");
        }
        if (hasValidKey(rm, "appLang", ReadableType.String)) {
            obj.setAppLang(rm.getString("appLang"));
            Log.i(TAG, "appLang attribute is set.");
        }
        if (hasValidKey(rm, "nonPersonalizedAd", ReadableType.Number)) {
            obj.setNonPersonalizedAd(rm.getInt("nonPersonalizedAd"));
            Log.i(TAG, "nonPersonalizedAd attribute is set.");
        }
        if (hasValidKey(rm, "requestOrigin", ReadableType.String)) {
            obj.setRequestOrigin(rm.getString("requestOrigin"));
            Log.i(TAG, "requestOrigin attribute is set.");
        }
        if (hasValidKey(rm, "tagForChildProtection", ReadableType.Number)) {
            obj.setTagForChildProtection(rm.getInt("tagForChildProtection"));
            Log.i(TAG, "tagForChildProtection attribute is set.");
        }
        if (hasValidKey(rm, "tagForUnderAgeOfPromise", ReadableType.Number)) {
            obj.setTagForUnderAgeOfPromise(rm.getInt("tagForUnderAgeOfPromise"));
            Log.i(TAG, "tagForUnderAgeOfPromise attribute is set.");
        }
        if (hasValidKey(rm, "targetingContentUrl", ReadableType.String)) {
            obj.setTargetingContentUrl(rm.getString("targetingContentUrl"));
            Log.i(TAG, "targetingContentUrl attribute is set.");
        }
        Log.i(TAG, "AdParam object is created.");
        return obj.build();
    }

    public static WritableMap getWritableMapFromAdParamBundle(Bundle obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
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
        return wm;
    }

    public static Map<String, Object> getExportedCustomDirectEventTypeConstantsFromEvents(String[] events) {
        Map<String, Object> obj = new ArrayMap<>();
        for (String event : events) {
            obj.put(event, MapBuilder.of("registrationName", event));
        }
        return obj;
    }

    public static Bundle getBundleFromReadableMap(ReadableMap rm) {
        Bundle obj = new Bundle();
        if (rm == null) {
            return obj;
        }
        obj.putSerializable("data", rm.toHashMap());
        return obj;
    }
}
