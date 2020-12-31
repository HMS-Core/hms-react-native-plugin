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

import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.UIManagerModule;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.AudioFocusType;
import com.huawei.hms.ads.ContentClassification;
import com.huawei.hms.ads.Gender;
import com.huawei.hms.ads.HwAds;

import com.huawei.hms.ads.NonPersonalizedAd;
import com.huawei.hms.ads.RequestOptions;
import com.huawei.hms.ads.TagForChild;
import com.huawei.hms.ads.UnderAge;
import com.huawei.hms.ads.consent.bean.AdProvider;
import com.huawei.hms.ads.consent.constant.ConsentStatus;
import com.huawei.hms.ads.consent.constant.DebugNeedConsent;
import com.huawei.hms.ads.consent.inter.Consent;
import com.huawei.hms.ads.consent.inter.ConsentUpdateListener;
import com.huawei.hms.ads.nativead.NativeAdAssetNames;
import com.huawei.hms.ads.nativead.NativeAdConfiguration;
import com.huawei.hms.rn.ads.logger.HMSLogger;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import java.util.List;
import java.util.Map;

public class HMSAdsModule extends ReactContextBaseJavaModule implements ConsentUpdateListener {
    private static final String TAG = HMSAdsModule.class.getSimpleName();
    private final ReactApplicationContext reactContext;
    private HMSLogger hmsLogger;
    private RequestOptions mRequestOptions;
    private Promise mPromise;

    HMSAdsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        hmsLogger = HMSLogger.getInstance(reactContext);
    }

    public enum CallMode {
        AIDL("aidl"),
        SDK("sdk");

        private String value;

        CallMode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static CallMode forValue(String s) {
            if ("aidl".equals(s)) {
                return AIDL;
            }
            return SDK;
        }
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new ArrayMap<>();

        Map<String, Object> consentStatus = new ArrayMap<>();
        consentStatus.put("PERSONALIZED", ConsentStatus.PERSONALIZED.getValue());
        consentStatus.put("NON_PERSONALIZED", ConsentStatus.NON_PERSONALIZED.getValue());
        consentStatus.put("UNKNOWN", ConsentStatus.UNKNOWN.getValue());
        constants.put("ConsentStatus", consentStatus);

        Map<String, Object> debugNeedConsent = new ArrayMap<>();
        debugNeedConsent.put("DEBUG_DISABLED", DebugNeedConsent.DEBUG_DISABLED.getValue());
        debugNeedConsent.put("DEBUG_NEED_CONSENT", DebugNeedConsent.DEBUG_NEED_CONSENT.getValue());
        debugNeedConsent.put("DEBUG_NOT_NEED_CONSENT", DebugNeedConsent.DEBUG_NOT_NEED_CONSENT.getValue());
        constants.put("DebugNeedConsent", debugNeedConsent);

        Map<String, Object> audioFocusType = new ArrayMap<>();
        audioFocusType.put("GAIN_AUDIO_FOCUS_ALL", AudioFocusType.GAIN_AUDIO_FOCUS_ALL);
        audioFocusType.put("NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE", AudioFocusType.NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE);
        audioFocusType.put("NOT_GAIN_AUDIO_FOCUS_ALL", AudioFocusType.NOT_GAIN_AUDIO_FOCUS_ALL);
        constants.put("AudioFocusType", audioFocusType);

        Map<String, Object> contentClassification = new ArrayMap<>();
        contentClassification.put("AD_CONTENT_CLASSIFICATION_W", ContentClassification.AD_CONTENT_CLASSIFICATION_W);
        contentClassification.put("AD_CONTENT_CLASSIFICATION_PI", ContentClassification.AD_CONTENT_CLASSIFICATION_PI);
        contentClassification.put("AD_CONTENT_CLASSIFICATION_J", ContentClassification.AD_CONTENT_CLASSIFICATION_J);
        contentClassification.put("AD_CONTENT_CLASSIFICATION_A", ContentClassification.AD_CONTENT_CLASSIFICATION_A);
        contentClassification.put("AD_CONTENT_CLASSIFICATION_UNKOWN",
                ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN);
        constants.put("ContentClassification", contentClassification);

        Map<String, Object> gender = new ArrayMap<>();
        gender.put("UNKNOWN", Gender.UNKNOWN);
        gender.put("MALE", Gender.MALE);
        gender.put("FEMALE", Gender.FEMALE);
        constants.put("Gender", gender);

        Map<String, Object> nonPersonalizedAd = new ArrayMap<>();
        nonPersonalizedAd.put("ALLOW_ALL", NonPersonalizedAd.ALLOW_ALL);
        nonPersonalizedAd.put("ALLOW_NON_PERSONALIZED", NonPersonalizedAd.ALLOW_NON_PERSONALIZED);
        constants.put("NonPersonalizedAd", nonPersonalizedAd);

        Map<String, Object> tagForChild = new ArrayMap<>();
        tagForChild.put("TAG_FOR_CHILD_PROTECTION_FALSE", TagForChild.TAG_FOR_CHILD_PROTECTION_FALSE);
        tagForChild.put("TAG_FOR_CHILD_PROTECTION_TRUE", TagForChild.TAG_FOR_CHILD_PROTECTION_TRUE);
        tagForChild.put("TAG_FOR_CHILD_PROTECTION_UNSPECIFIED", TagForChild.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED);
        constants.put("TagForChild", tagForChild);

        Map<String, Object> underAge = new ArrayMap<>();
        underAge.put("PROMISE_FALSE", UnderAge.PROMISE_FALSE);
        underAge.put("PROMISE_TRUE", UnderAge.PROMISE_TRUE);
        underAge.put("PROMISE_UNSPECIFIED", UnderAge.PROMISE_UNSPECIFIED);
        constants.put("UnderAge", underAge);

        Map<String, Object> nativeAdAssetNames = new ArrayMap<>();
        nativeAdAssetNames.put("TITLE", NativeAdAssetNames.TITLE);
        nativeAdAssetNames.put("CALL_TO_ACTION", NativeAdAssetNames.CALL_TO_ACTION);
        nativeAdAssetNames.put("ICON", NativeAdAssetNames.ICON);
        nativeAdAssetNames.put("DESC", NativeAdAssetNames.DESC);
        nativeAdAssetNames.put("AD_SOURCE", NativeAdAssetNames.AD_SOURCE);
        nativeAdAssetNames.put("MARKET", NativeAdAssetNames.MARKET);
        nativeAdAssetNames.put("PRICE", NativeAdAssetNames.PRICE);
        nativeAdAssetNames.put("IMAGE", NativeAdAssetNames.IMAGE);
        nativeAdAssetNames.put("RATING", NativeAdAssetNames.RATING);
        nativeAdAssetNames.put("MEDIA_VIDEO", NativeAdAssetNames.MEDIA_VIDEO);
        nativeAdAssetNames.put("CHOICES_CONTAINER", NativeAdAssetNames.CHOICES_CONTAINER);
        constants.put("NativeAdAssetNames", nativeAdAssetNames);

        Map<String, Object> choicesPosition = new ArrayMap<>();
        choicesPosition.put("TOP_LEFT", NativeAdConfiguration.ChoicesPosition.TOP_LEFT);
        choicesPosition.put("TOP_RIGHT", NativeAdConfiguration.ChoicesPosition.TOP_RIGHT);
        choicesPosition.put("BOTTOM_RIGHT", NativeAdConfiguration.ChoicesPosition.BOTTOM_RIGHT);
        choicesPosition.put("BOTTOM_LEFT", NativeAdConfiguration.ChoicesPosition.BOTTOM_LEFT);
        choicesPosition.put("INVISIBLE", NativeAdConfiguration.ChoicesPosition.INVISIBLE);
        constants.put("ChoicesPosition", choicesPosition);

        Map<String, Object> direction = new ArrayMap<>();
        direction.put("ANY", NativeAdConfiguration.Direction.ANY);
        direction.put("PORTRAIT", NativeAdConfiguration.Direction.PORTRAIT);
        direction.put("LANDSCAPE", NativeAdConfiguration.Direction.LANDSCAPE);
        constants.put("Direction", direction);

        Map<String, Object> scaleType = new ArrayMap<>();
        scaleType.put("MATRIX", ImageView.ScaleType.MATRIX.name());
        scaleType.put("FIT_XY", ImageView.ScaleType.FIT_XY.name());
        scaleType.put("FIT_START", ImageView.ScaleType.FIT_START.name());
        scaleType.put("FIT_CENTER", ImageView.ScaleType.FIT_CENTER.name());
        scaleType.put("FIT_END", ImageView.ScaleType.FIT_END.name());
        scaleType.put("CENTER", ImageView.ScaleType.CENTER.name());
        scaleType.put("CENTER_CROP", ImageView.ScaleType.CENTER_CROP.name());
        scaleType.put("CENTER_INSIDE", ImageView.ScaleType.CENTER_INSIDE.name());
        constants.put("ScaleType", scaleType);

        Map<String, Object> bannerAdSizes = new ArrayMap<>();
        bannerAdSizes.put("B_160_600", HMSAdsBannerView.BannerSize.B_160_600.getValue());
        bannerAdSizes.put("B_300_250", HMSAdsBannerView.BannerSize.B_300_250.getValue());
        bannerAdSizes.put("B_320_50", HMSAdsBannerView.BannerSize.B_320_50.getValue());
        bannerAdSizes.put("B_320_100", HMSAdsBannerView.BannerSize.B_320_100.getValue());
        bannerAdSizes.put("B_360_57", HMSAdsBannerView.BannerSize.B_360_57.getValue());
        bannerAdSizes.put("B_360_144", HMSAdsBannerView.BannerSize.B_360_144.getValue());
        bannerAdSizes.put("B_468_60", HMSAdsBannerView.BannerSize.B_468_60.getValue());
        bannerAdSizes.put("B_728_90", HMSAdsBannerView.BannerSize.B_728_90.getValue());
        bannerAdSizes.put("B_CURRENT_DIRECTION", HMSAdsBannerView.BannerSize.B_CURRENT_DIRECTION.getValue());
        bannerAdSizes.put("B_PORTRAIT", HMSAdsBannerView.BannerSize.B_PORTRAIT.getValue());
        bannerAdSizes.put("B_SMART", HMSAdsBannerView.BannerSize.B_SMART.getValue());
        bannerAdSizes.put("B_DYNAMIC", HMSAdsBannerView.BannerSize.B_DYNAMIC.getValue());
        bannerAdSizes.put("B_LANDSCAPE", HMSAdsBannerView.BannerSize.B_LANDSCAPE.getValue());
        bannerAdSizes.put("B_INVALID", HMSAdsBannerView.BannerSize.B_INVALID.getValue());
        constants.put("BannerAdSizes", bannerAdSizes);

        Map<String, Object> bannerMediaTypes = new ArrayMap<>();
        bannerMediaTypes.put("IMAGE", HMSAdsBannerView.BannerMediaType.IMAGE.getValue());
        constants.put("BannerMediaTypes", bannerMediaTypes);

        Map<String, Object> nativeMediaTypes = new ArrayMap<>();
        nativeMediaTypes.put("IMAGE_LARGE", HMSAdsNativeView.NativeMediaType.IMAGE_LARGE.getValue());
        nativeMediaTypes.put("IMAGE_SMALL", HMSAdsNativeView.NativeMediaType.IMAGE_SMALL.getValue());
        nativeMediaTypes.put("VIDEO", HMSAdsNativeView.NativeMediaType.VIDEO.getValue());
        constants.put("NativeMediaTypes", nativeMediaTypes);

        Map<String, Object> interstitialMediaTypes = new ArrayMap<>();
        interstitialMediaTypes.put("IMAGE", HMSAdsInterstitialAdModule.InterstitialMediaType.IMAGE.getValue());
        interstitialMediaTypes.put("VIDEO", HMSAdsInterstitialAdModule.InterstitialMediaType.VIDEO.getValue());
        constants.put("InterstitialMediaTypes", interstitialMediaTypes);

        Map<String, Object> rewardMediaTypes = new ArrayMap<>();
        rewardMediaTypes.put("VIDEO", HMSAdsRewardAdModule.RewardMediaType.VIDEO.getValue());
        constants.put("RewardMediaTypes", rewardMediaTypes);

        Map<String, Object> splashMediaTypes = new ArrayMap<>();
        splashMediaTypes.put("IMAGE", HMSAdsSplashAdModule.SplashMediaType.IMAGE.getValue());
        splashMediaTypes.put("VIDEO", HMSAdsSplashAdModule.SplashMediaType.VIDEO.getValue());
        constants.put("SplashMediaTypes", splashMediaTypes);

        Map<String, Object> callModes = new ArrayMap<>();
        callModes.put("AIDL", CallMode.AIDL.getValue());
        callModes.put("SDK", CallMode.SDK.getValue());
        constants.put("CallMode", callModes);

        return constants;
    }

    @Override
    public void onSuccess(ConsentStatus consentStatus, boolean isNeedConsent, List<AdProvider> adProviderList) {
        hmsLogger.sendSingleEvent("requestConsentUpdate");
        WritableMap result = new WritableNativeMap();
        if (consentStatus != null) {
            result.putInt("consentStatus", consentStatus.getValue());
        }
        if (adProviderList != null) {
            result.putArray("adProviders", ReactUtils.mapList(adProviderList,
                    ReactUtils::getWritableMapFromAdProvider));
        }
        result.putBoolean("isNeedConsent", isNeedConsent);
        mPromise.resolve(result);
    }

    @Override
    public void onFail(String errorDescription) {
        hmsLogger.sendSingleEvent("requestConsentUpdate", "-1");
        Log.e(TAG, "User's consent failed to update: " + errorDescription);
        mPromise.reject("USER_CONSENT_FAILED", "Error: " + errorDescription);
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSAds";
    }

    @ReactMethod
    public void disableLogger(final Promise promise) {
        hmsLogger.disableLogger();
        promise.resolve(null);
    }

    @ReactMethod
    public void enableLogger(final Promise promise) {
        hmsLogger.enableLogger();
        promise.resolve(null);
    }

    @ReactMethod
    public void init(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("init");
        HwAds.init(reactContext);
        hmsLogger.sendSingleEvent("init");
        mRequestOptions = HwAds.getRequestOptions();
        promise.resolve("Hw Ads initialized");
    }

    @ReactMethod
    public void getSDKVersion(final Promise promise) {
        hmsLogger.startMethodExecutionTimer("getSDKVersion");
        String sdkVersion = HwAds.getSDKVersion();
        hmsLogger.sendSingleEvent("getSDKVersion");
        promise.resolve(sdkVersion);
    }

    @ReactMethod
    public void getRequestOptions(final Promise promise) {
        if (mRequestOptions == null) {
            hmsLogger.startMethodExecutionTimer("getRequestOptions");
            mRequestOptions = HwAds.getRequestOptions();
            hmsLogger.sendSingleEvent("getRequestOptions");
        }
        promise.resolve(ReactUtils.getWritableMapFromRequestOptions(mRequestOptions));
    }

    @ReactMethod
    public void setRequestOptions(final ReadableMap rm, final Promise promise) {
        RequestOptions requestOptions = ReactUtils.getRequestOptionsFromReadableMap(rm);
        hmsLogger.startMethodExecutionTimer("setRequestOptions");
        HwAds.setRequestOptions(requestOptions);
        hmsLogger.sendSingleEvent("setRequestOptions");
        mRequestOptions = HwAds.getRequestOptions();
        promise.resolve(ReactUtils.getWritableMapFromRequestOptions(mRequestOptions));
    }

    @ReactMethod
    public void setConsent(final ReadableMap rm, final Promise promise) {
        Consent consentInfo = Consent.getInstance(reactContext);
        mPromise = promise;
        if (ReactUtils.hasValidKey(rm, "consentStatus", ReadableType.Number)) {
            consentInfo.setConsentStatus(ConsentStatus.forValue(rm.getInt("consentStatus")));
        }
        if (ReactUtils.hasValidKey(rm, "debugNeedConsent", ReadableType.Number)) {
            consentInfo.setDebugNeedConsent(DebugNeedConsent.forValue(rm.getInt("debugNeedConsent")));
        }
        if (ReactUtils.hasValidKey(rm, "underAgeOfPromise", ReadableType.Number)
                && rm.getInt("underAgeOfPromise") != UnderAge.PROMISE_UNSPECIFIED) {
            consentInfo.setUnderAgeOfPromise(rm.getInt("underAgeOfPromise") == UnderAge.PROMISE_TRUE);
        }
        if (ReactUtils.hasValidKey(rm, "testDeviceId", ReadableType.String)) {
            consentInfo.addTestDeviceId(rm.getString("testDeviceId"));
        }
        hmsLogger.startMethodExecutionTimer("requestConsentUpdate");
        consentInfo.requestConsentUpdate(this);
    }

    @ReactMethod
    public void setConsentString(final String consent, final Promise promise) {
        HwAds.setConsent(consent);
        promise.resolve(null);
    }

    @ReactMethod
    public void checkConsent(final Promise promise) {
        Consent consentInfo = Consent.getInstance(reactContext);
        mPromise = promise;
        hmsLogger.startMethodExecutionTimer("requestConsentUpdate");
        consentInfo.requestConsentUpdate(this);
    }

    @ReactMethod
    public void getViewInfo(final int viewId, final Promise promise) {
        UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(nativeViewHierarchyManager -> {
            View view = nativeViewHierarchyManager.resolveView(viewId);
            if (view instanceof HMSAdsBannerView) {
                HMSAdsBannerView myView = (HMSAdsBannerView) view;
                WritableMap wm = new WritableNativeMap();
                wm.putString("adId", myView.getAdId());
                wm.putBoolean("isLoading", myView.isLoading());
                wm.putMap("bannerAdSize",
                        ReactUtils.getWritableMapFromBannerAdSizeWithContext(myView.getBannerAdSize(), reactContext));
                promise.resolve(wm);
            } else if (view instanceof HMSAdsNativeView) {
                HMSAdsNativeView myView = (HMSAdsNativeView) view;
                WritableMap wm = new WritableNativeMap();
                wm.putMap("nativeAd", ReactUtils.getWritableMapFromNativeAd(myView.mNativeAd));
                wm.putMap("nativeAdConfiguration",
                        ReactUtils.getWritableMapFromNativeAdConfiguration(myView.mNativeAdConfiguration));
                wm.putMap("nativeAdLoader", ReactUtils.getWritableMapFromNativeAdLoader(myView.mNativeAdLoader));
                promise.resolve(wm);
            } else if (view instanceof HMSAdsInstreamView) {
                HMSAdsInstreamView myView = (HMSAdsInstreamView) view;
                WritableMap wm = new WritableNativeMap();
                wm.putString("adId", myView.mAdId);
                wm.putInt("maxCount", myView.mMaxCount);
                wm.putInt("totalDuration", myView.mTotalDuration);
                wm.putBoolean("isPlaying", myView.isPlaying());
                wm.putBoolean("isLoading", myView.mInstreamAdLoader != null && myView.mInstreamAdLoader.isLoading());
                wm.putArray("instreamAds", ReactUtils.mapList(myView.mInstreamAds,
                        ReactUtils::getWritableMapFromInstreamAd));
                promise.resolve(wm);
            } else {
                promise.reject("NOT_AD_VIEW", "Unexpected view type");
            }
        });
    }

    public static String getErrorMessage(int errorCode) {
        switch (errorCode) {
            case AdParam.ErrorCode.INNER:
                return "Internal error, an invalid response was received from the ad server.";
            case AdParam.ErrorCode.INVALID_REQUEST:
                return "Invalid ad request due to unspecified ad slot ID or invalid banner ad size.";
            case AdParam.ErrorCode.NETWORK_ERROR:
                return "The ad request was unsuccessful due to a network connection error.";
            case AdParam.ErrorCode.NO_AD:
                return "The ad request was successful, but no ad is returned due to a lack of ad resources.";
            case AdParam.ErrorCode.AD_LOADING:
                return "The ad request was successful, and ad was loading.";
            case AdParam.ErrorCode.LOW_API:
                return "The ad request was successful, but api version is not supported by the HUAWEI Ads SDK.";
            case AdParam.ErrorCode.BANNER_AD_EXPIRE:
                return "The ad request was successful, but banner ad was expired.";
            case AdParam.ErrorCode.BANNER_AD_CANCEL:
                return "The ad request was successful, but banner ad task removed.";
        }
        return "Unknown error";
    }
}
