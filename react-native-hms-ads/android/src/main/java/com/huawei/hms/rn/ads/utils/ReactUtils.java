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

package com.huawei.hms.rn.ads.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.ArrayMap;

import com.facebook.react.bridge.ReactContext;
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
import com.huawei.hms.ads.vast.adapter.SdkFactory;
import com.huawei.hms.ads.vast.adapter.VastSdkConfiguration;
import com.huawei.hms.ads.vast.application.requestinfo.CreativeMatchStrategy;
import com.huawei.hms.ads.vast.domain.advertisement.CreativeExtension;
import com.huawei.hms.ads.vast.player.api.PlayerConfig;
import com.huawei.hms.ads.vast.player.api.VastAdPlayer;
import com.huawei.hms.ads.vast.player.model.LinearCreative;
import com.huawei.hms.ads.vast.player.model.adslot.AdsData;
import com.huawei.hms.ads.vast.player.model.adslot.LinearAdSlot;
import com.huawei.hms.rn.ads.HMSAdsBannerView;
import com.huawei.hms.rn.ads.HMSAdsModule;
import com.huawei.hms.rn.ads.HMSAdsVastModule;
import com.huawei.hms.rn.ads.HMSAdsVastView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        if (list == null || list.isEmpty()) {
            return null;
        }
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

    public static WritableMap getWritableMapFromVastErrorCode(int responseCode) {
        WritableMap wm = new WritableNativeMap();
        wm.putInt("responseCode", responseCode);
        wm.putString("errorMessage", HMSAdsVastModule.getVastErrorMessages(responseCode));
        return wm;
    }

    public static WritableMap getWritableMapFromVastPlayState(int playState) {
        WritableMap wm = new WritableNativeMap();
        wm.putInt("playStateCode", playState);
        wm.putString("playStateMessage", HMSAdsVastModule.getVastPlayStateChangedMessage(playState));
        return wm;
    }

    public static WritableMap getWritableMapFromVastScreenState(int screenState) {
        WritableMap wm = new WritableNativeMap();
        wm.putInt("screenStateCode", screenState);
        wm.putString("screenStateMessage", HMSAdsVastModule.getVastScreenStateChangedMessage(screenState));
        return wm;
    }

    public static WritableMap getWritableMapFromVastProgressListener(long duration, long currentPosition,
        long skipDuration) {
        WritableMap wm = new WritableNativeMap();
        wm.putDouble("duration", duration);
        wm.putDouble("currentPosition", currentPosition);
        wm.putDouble("skipDuration", skipDuration);
        return wm;
    }

    public static WritableMap getWritableMapFromReward(Reward obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putString("name", obj.getName());
        wm.putInt("amount", obj.getAmount());
        return wm;
    }

    public static WritableMap getWritableMapFromRewardAd(RewardAd obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
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
            wm.putArray("dislikeAdReasons",
                mapList(obj.getDislikeAdReasons(), ReactUtils::getWritableMapFromDislikeAdReason));
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

    public static BannerAdSize getBannerAdSizeFromReadableMap(Context context, String bannerAdSize) {
        if (bannerAdSize == null || context == null) {
            return BannerAdSize.BANNER_SIZE_SMART;
        }

        switch (HMSAdsBannerView.BannerSize.forValue(bannerAdSize)) {
            case B_DYNAMIC:
                return BannerAdSize.BANNER_SIZE_DYNAMIC;
            case B_INVALID:
                return BannerAdSize.BANNER_SIZE_INVALID;
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
            default:
                break;
        }

        return BannerAdSize.BANNER_SIZE_SMART;
    }

    public static AdSize getAdSizeFromReadableMap(ReadableMap rm) {
        if (rm != null && ReactUtils.hasValidKey(rm, "height", ReadableType.Number) && ReactUtils.hasValidKey(rm,
            "width", ReadableType.Number)) {
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
            wm.putString("installChannel", obj.getInstallChannel());
            wm.putDouble("installBeginTimestampMillisecond", obj.getInstallBeginTimestampMillisecond());
            wm.putDouble("installBeginTimestampSeconds", obj.getInstallBeginTimestampSeconds());
            wm.putDouble("referrerClickTimestampMillisecond", obj.getReferrerClickTimestampMillisecond());
            wm.putDouble("referrerClickTimestampSeconds", obj.getReferrerClickTimestampSeconds());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromRequestOptions(RequestOptions requestOptions) {
        WritableMap wm = new WritableNativeMap();
        if (requestOptions != null) {
            if (requestOptions.getAdContentClassification() != null) {
                wm.putString("adContentClassification", requestOptions.getAdContentClassification());
            }
            if (requestOptions.getAppCountry() != null) {
                wm.putString("appCountry", requestOptions.getAppCountry());
            }
            if (requestOptions.getAppLang() != null) {
                wm.putString("appLang", requestOptions.getAppLang());
            }
            if (requestOptions.getNonPersonalizedAd() != null) {
                wm.putInt("nonPersonalizedAd", requestOptions.getNonPersonalizedAd());
            }
            if (requestOptions.getTagForChildProtection() != null) {
                wm.putInt("tagForChildProtection", requestOptions.getTagForChildProtection());
            }
            if (requestOptions.getTagForUnderAgeOfPromise() != null) {
                wm.putInt("tagForUnderAgeOfPromise", requestOptions.getTagForUnderAgeOfPromise());
            }
        }
        return wm;
    }

    public static RequestOptions getRequestOptionsFromReadableMap(ReadableMap rm) {
        RequestOptions.Builder requestOptions = new RequestOptions.Builder();
        if (rm != null) {
            if (hasValidKey(rm, "adContentClassification", ReadableType.String)) {
                requestOptions.setAdContentClassification(rm.getString("adContentClassification"));
            }
            if (hasValidKey(rm, "appCountry", ReadableType.String)) {
                requestOptions.setAppCountry(rm.getString("appCountry"));
            }
            if (hasValidKey(rm, "appLang", ReadableType.String)) {
                requestOptions.setAppLang(rm.getString("appLang"));
            }
            if (hasValidKey(rm, "nonPersonalizedAd", ReadableType.Number)) {
                requestOptions.setNonPersonalizedAd(rm.getInt("nonPersonalizedAd"));
            }
            if (hasValidKey(rm, "tagForChildProtection", ReadableType.Number)) {
                requestOptions.setTagForChildProtection(rm.getInt("tagForChildProtection"));
            }
            if (hasValidKey(rm, "tagForUnderAgeOfPromise", ReadableType.Number)) {
                requestOptions.setTagForUnderAgeOfPromise(rm.getInt("tagForUnderAgeOfPromise"));
            }
            if (hasValidKey(rm, "requestLocation", ReadableType.Boolean)) {
                requestOptions.setRequestLocation(rm.getBoolean("requestLocation"));
            }
        }
        return requestOptions.build();
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
            if (hasValidKey(rm, "requestLocation", ReadableType.Boolean)) {
                obj.setRequestLocation(rm.getBoolean("requestLocation"));
            }
            if (hasValidKey(rm, "detailedCreativeTypes", ReadableType.Array)) {
                obj.setDetailedCreativeTypeList(fromReadableArrayToListInteger(rm.getArray("detailedCreativeTypes")));
            }
            if (hasValidKey(rm, "contentBundle", ReadableType.String)) {
                obj.setContentBundle(rm.getString("contentBundle"));
            }
            if (hasValidKey(rm, "location", ReadableType.Map)) {
                obj.setLocation(fromReadableMapToLocation(rm.getMap("location")));
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

    public static VastSdkConfiguration getVastSdkConfigurationFromReadableMap(ReadableMap rm) {
        VastSdkConfiguration vastSdkConfiguration = SdkFactory.getConfiguration();
        if (rm != null) {
            if (hasValidKey(rm, "httpCallTimeoutMs", ReadableType.Number)) {
                vastSdkConfiguration.setHttpCallTimeoutMs(rm.getInt("httpCallTimeoutMs"));
            }
            if (hasValidKey(rm, "httpConnectTimeoutMs", ReadableType.Number)) {
                vastSdkConfiguration.setHttpConnectTimeoutMs(rm.getInt("httpConnectTimeoutMs"));
            }
            if (hasValidKey(rm, "httpKeepAliveDurationMs", ReadableType.Number)) {
                vastSdkConfiguration.setHttpKeepAliveDurationMs(rm.getInt("httpKeepAliveDurationMs"));
            }
            if (hasValidKey(rm, "httpReadTimeoutMs", ReadableType.Number)) {
                vastSdkConfiguration.setHttpReadTimeoutMs(rm.getInt("httpReadTimeoutMs"));
            }
            if (hasValidKey(rm, "maxHttpConnections", ReadableType.Number)) {
                vastSdkConfiguration.setMaxHttpConnections(rm.getInt("maxHttpConnections"));
            }
            if (hasValidKey(rm, "maxRedirectWrapperLimit", ReadableType.Number)) {
                vastSdkConfiguration.setMaxRedirectWrapperLimit(rm.getInt("maxRedirectWrapperLimit"));
            }
            if (hasValidKey(rm, "isTest", ReadableType.Boolean)) {
                vastSdkConfiguration.setTest(rm.getBoolean("isTest"));
            }
            if (hasValidKey(rm, "vastEventRetryBatchSize", ReadableType.Number)) {
                vastSdkConfiguration.setVastEventRetryBatchSize(rm.getInt("vastEventRetryBatchSize"));
            }
            if (hasValidKey(rm, "vastEventRetryIntervalSeconds", ReadableType.Number)) {
                vastSdkConfiguration.setVastEventRetryIntervalSeconds(rm.getInt("vastEventRetryIntervalSeconds"));
            }
            if (hasValidKey(rm, "vastEventRetryUploadTimes", ReadableType.Number)) {
                vastSdkConfiguration.setVastEventRetryUploadTimes(rm.getInt("vastEventRetryUploadTimes"));
            }
        }
        return vastSdkConfiguration;
    }

    public static WritableMap getWritableMapFromVastSdkConfiguration(VastSdkConfiguration obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putInt("httpCallTimeoutMs", obj.getHttpCallTimeoutMs());
            wm.putInt("httpConnectTimeoutMs", obj.getHttpConnectTimeoutMs());
            wm.putInt("httpKeepAliveDurationMs", obj.getHttpKeepAliveDurationMs());
            wm.putInt("httpReadTimeoutMs", obj.getHttpReadTimeoutMs());
            wm.putInt("maxHttpConnections", obj.getMaxHttpConnections());
            wm.putInt("maxRedirectWrapperLimit", obj.getMaxRedirectWrapperLimit());
            wm.putInt("vastEventRetryBatchSize", obj.getVastEventRetryBatchSize());
            wm.putInt("vastEventRetryIntervalSeconds", obj.getVastEventRetryIntervalSeconds());
            wm.putInt("vastEventRetryUploadTimes", obj.getVastEventRetryUploadTimes());
            wm.putBoolean("isTest", obj.isTest());
        }
        return wm;
    }

    public static Location fromReadableMapToLocation(ReadableMap rm) {
        Location location = new Location("");

        if (hasValidKey(rm, "lat", ReadableType.Number)) {
            location.setLatitude(rm.getInt("lat"));
        }
        if (hasValidKey(rm, "lng", ReadableType.Number)) {
            location.setLatitude(rm.getInt("lng"));
        }

        return location;
    }

    public static List<Integer> fromReadableArrayToListInteger(ReadableArray arr) {
        List<Integer> detailedCreativeTypeList = new ArrayList<>();

        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                detailedCreativeTypeList.add(arr.getInt(i));
            }
        }
        return detailedCreativeTypeList;
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

    public static com.huawei.hms.ads.vast.openalliance.ad.beans.parameter.RequestOptions getVastRequestOptionsFromReadableMap(
        ReadableMap rm) {
        com.huawei.hms.ads.vast.openalliance.ad.beans.parameter.RequestOptions.Builder vastRequestOptions
            = new com.huawei.hms.ads.vast.openalliance.ad.beans.parameter.RequestOptions.Builder();
        if (rm != null) {
            if (hasValidKey(rm, "adContentClassification", ReadableType.String)) {
                vastRequestOptions.setAdContentClassification(rm.getString("adContentClassification"));
            }
            if (hasValidKey(rm, "appCountry", ReadableType.String)) {
                vastRequestOptions.setAppCountry(rm.getString("appCountry"));
            }
            if (hasValidKey(rm, "appLang", ReadableType.String)) {
                vastRequestOptions.setAppLang(rm.getString("appLang"));
            }
            if (hasValidKey(rm, "nonPersonalizedAd", ReadableType.Number)) {
                vastRequestOptions.setNonPersonalizedAd(rm.getInt("nonPersonalizedAd"));
            }
            if (hasValidKey(rm, "tagForChildProtection", ReadableType.Number)) {
                vastRequestOptions.setTagForChildProtection(rm.getInt("tagForChildProtection"));
            }
            if (hasValidKey(rm, "tagForUnderAgeOfPromise", ReadableType.Number)) {
                vastRequestOptions.setTagForUnderAgeOfPromise(rm.getInt("tagForUnderAgeOfPromise"));
            }
            if (hasValidKey(rm, "requestLocation", ReadableType.Boolean)) {
                vastRequestOptions.setRequestLocation(rm.getBoolean("requestLocation"));
            }
            if (hasValidKey(rm, "consent", ReadableType.String)) {
                vastRequestOptions.setConsent(rm.getString("consent"));
            }
        }
        return vastRequestOptions.build();
    }

    public static PlayerConfig getPlayerConfigsFromReadableMap(ReadableMap rm) {
        PlayerConfig.Builder obj = PlayerConfig.newBuilder();
        if (rm != null) {
            if (hasValidKey(rm, "enableRotation", ReadableType.Boolean)) {
                obj.setEnableRotation(rm.getBoolean("enableRotation"));
            }
            if (hasValidKey(rm, "isEnableCutout", ReadableType.Boolean)) {
                obj.setIsEnableCutout(rm.getBoolean("isEnableCutout"));
            }
            if (hasValidKey(rm, "skipLinearAd", ReadableType.Boolean)) {
                obj.setSkipLinearAd(rm.getBoolean("skipLinearAd"));
            }
            if (hasValidKey(rm, "isEnablePortrait", ReadableType.Boolean)) {
                obj.setIsEnablePortrait(rm.getBoolean("isEnablePortrait"));
            }
        }
        return obj.build();
    }

    public static CreativeMatchStrategy.CreativeMatchType toCreativeMatchType(int creativeMatchType) {
        switch (HMSAdsVastView.CreativeMatchType.forValue(creativeMatchType)) {
            case EXACT:
                return CreativeMatchStrategy.CreativeMatchType.EXACT;
            case SMART:
                return CreativeMatchStrategy.CreativeMatchType.SMART;
            case UNKNOWN:
                return CreativeMatchStrategy.CreativeMatchType.UNKNOWN;
            case LANDSCAPE:
                return CreativeMatchStrategy.CreativeMatchType.LANDSCAPE;
            case PORTRAIT:
                return CreativeMatchStrategy.CreativeMatchType.PORTRAIT;
            default:
                break;
        }
        return CreativeMatchStrategy.CreativeMatchType.ANY;
    }

    public static LinearAdSlot getLinearAdSlotFromReadableMap(ReadableMap rm) {
        LinearAdSlot linearAdSlot = new LinearAdSlot();
        if (rm != null) {
            if (hasValidKey(rm, "adId", ReadableType.String)) {
                linearAdSlot.setSlotId(rm.getString("adId"));
            }
            if (hasValidKey(rm, "totalDuration", ReadableType.Number)) {
                linearAdSlot.setTotalDuration(rm.getInt("totalDuration"));
            }
            if (hasValidKey(rm, "allowMobileTraffic", ReadableType.Boolean)) {
                linearAdSlot.setAllowMobileTraffic(rm.getBoolean("allowMobileTraffic"));
            }
            if (hasValidKey(rm, "adOrientation", ReadableType.Number)) {
                linearAdSlot.setOrientation(rm.getInt("adOrientation"));
            }
            if (hasValidKey(rm, "creativeMatchStrategy", ReadableType.Number)) {
                CreativeMatchStrategy creativeMatchStrategy = new CreativeMatchStrategy(
                    toCreativeMatchType(rm.getInt("creativeMatchStrategy")));
                linearAdSlot.setCreativeMatchStrategy(creativeMatchStrategy);
            }
            if (hasValidKey(rm, "requestOption", ReadableType.Map)) {
                linearAdSlot.setRequestOptions(getVastRequestOptionsFromReadableMap(rm.getMap("requestOption")));
            }
            if (hasValidKey(rm, "size", ReadableType.Map)) {
                linearAdSlot.setSize(Objects.requireNonNull(rm.getMap("size")).getInt("width"),
                    Objects.requireNonNull(rm.getMap("size")).getInt("height"));
            }
            if (hasValidKey(rm, "maxAdPods", ReadableType.Number)) {
                linearAdSlot.setMaxAdPods(rm.getInt("maxAdPods"));
            }
        }
        return linearAdSlot;
    }

    public static WritableMap getWritableMapFromPlayerConfig(PlayerConfig obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putBoolean("isEnableRotation", obj.isEnableRotation());
            wm.putBoolean("isSkipLinearAd", obj.isSkipLinearAd());
            wm.putBoolean("isEnableCutout", obj.isEnableCutout());
            wm.putBoolean("isEnablePortrait", obj.isEnablePortrait());
            wm.putBoolean("isForceMute", obj.isForceMute());
            wm.putBoolean("isIndustryIconShow", obj.isIndustryIconShow());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromVastAdPlayerConfigs(ReactContext mReactContext) {
        VastAdPlayer vastAdPlayer = VastAdPlayer.getInstance();
        WritableMap wm = new WritableNativeMap();
        if (mReactContext != null) {
            wm.putMap("playerConfigs", getWritableMapFromPlayerConfig(vastAdPlayer.getConfig()));
            wm.putBoolean("isLinearAdShown", vastAdPlayer.isLinearAdShown());
            wm.putBoolean("isLinearPlaying", vastAdPlayer.isLinearPlaying());
            wm.putBoolean("isNonlinearPlaying", vastAdPlayer.isNonlinearPlaying());
            wm.putBoolean("onBackPressed", vastAdPlayer.onBackPressed(mReactContext.getCurrentActivity()));
        }
        return wm;
    }

    public static WritableMap getWritableMapFromCreativeMatchTStrategy(CreativeMatchStrategy obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putInt("creativeMatchType", obj.getCreativeMatchType().getCode());

            if (obj.expectedCreativeHeight != null) {
                wm.putInt("height", obj.expectedCreativeHeight);
            }
            if (obj.expectedCreativeWidth != null) {
                wm.putInt("height", obj.expectedCreativeWidth);
            }
        }
        return wm;
    }

    public static WritableMap getWritableMapFromLinearAdSlot(LinearAdSlot obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putMap("creativeMatchStrategy",
                getWritableMapFromCreativeMatchTStrategy(obj.getCreativeMatchStrategy()));
            wm.putInt("height", obj.getHeight());
            wm.putInt("width", obj.getWidth());
            wm.putInt("maxAdPods", obj.getMaxAdPods());
            wm.putInt("orientation", obj.getOrientation());
            wm.putMap("requestOptions", getWritableMapFromVastRequestOptions(obj.getRequestOptions()));
            wm.putInt("totalDuration", obj.getTotalDuration());
            wm.putBoolean("isAllowMobileTraffic", obj.isAllowMobileTraffic());
            if (obj.getSlotId() != null) {
                wm.putString("slotId", obj.getSlotId());
            }
        }
        return wm;
    }

    public static WritableMap getWritableMapFromVastRequestOptions(
        com.huawei.hms.ads.vast.openalliance.ad.beans.parameter.RequestOptions vastRequestOptions) {
        WritableMap wm = new WritableNativeMap();
        if (vastRequestOptions != null) {
            if (vastRequestOptions.getAdContentClassification() != null) {
                wm.putString("adContentClassification", vastRequestOptions.getAdContentClassification());
            }
            if (vastRequestOptions.getAppCountry() != null) {
                wm.putString("appCountry", vastRequestOptions.getAppCountry());
            }
            if (vastRequestOptions.getAppLang() != null) {
                wm.putString("appLang", vastRequestOptions.getAppLang());
            }
            if (vastRequestOptions.getConsent() != null) {
                wm.putString("consent", vastRequestOptions.getConsent());
            }
            if (vastRequestOptions.getNonPersonalizedAd() != null) {
                wm.putInt("nonPersonalizedAd", vastRequestOptions.getNonPersonalizedAd());
            }
            if (vastRequestOptions.getTagForChildProtection() != null) {
                wm.putInt("tagForChildProtection", vastRequestOptions.getTagForChildProtection());
            }
            if (vastRequestOptions.getTagForUnderAgeOfPromise() != null) {
                wm.putInt("tagForUnderAgeOfPromise", vastRequestOptions.getTagForUnderAgeOfPromise());
            }
            wm.putBoolean("isRequestLocation", vastRequestOptions.isRequestLocation());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromAdsData(AdsData obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putArray("linearAdCreatives",
                mapList(obj.getLinearCreations(), ReactUtils::getWritableMapFromLinearCreative));
            wm.putArray("backupAdCreatives",
                mapList(obj.getBackUpCreation(), ReactUtils::getWritableMapFromLinearCreative));
        }
        return wm;
    }

    public static WritableMap getWritableMapFromLinearCreative(LinearCreative obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj != null) {
            wm.putMap("adExtensions", getWritableMapFromCreativeExtensionMap(obj.getAdExtensionMap()));
            wm.putMap("typeToCreativeExtensions",
                getWritableMapFromCreativeExtensionMap(obj.getTypeToCreativeExtension()));
            wm.putString("contentId", obj.getContentId());
            wm.putString("requestId", obj.getRequestId());
            wm.putString("showId", obj.getShowId());
            wm.putString("slotId", obj.getSlotId());
            wm.putString("type", obj.getType());
            wm.putString("url", obj.getUrl());
        }
        return wm;
    }

    public static WritableMap getWritableMapFromCreativeExtensionMap(Map<String, CreativeExtension> map) {
        WritableMap wm = new WritableNativeMap();
        Iterator<Map.Entry<String, CreativeExtension>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, CreativeExtension> pair = it.next();
            wm.putString(pair.getKey(), pair.getValue().getValue());
        }
        return wm;
    }
}
