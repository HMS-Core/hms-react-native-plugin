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

import androidx.annotation.NonNull;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.HwAds;

import com.huawei.hms.ads.RequestOptions;
import com.huawei.hms.ads.UnderAge;
import com.huawei.hms.ads.consent.bean.AdProvider;
import com.huawei.hms.ads.consent.constant.ConsentStatus;
import com.huawei.hms.ads.consent.constant.DebugNeedConsent;
import com.huawei.hms.ads.consent.inter.Consent;
import com.huawei.hms.ads.consent.inter.ConsentUpdateListener;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import java.util.List;

public class RNHMSAdsModule extends ReactContextBaseJavaModule implements ConsentUpdateListener {
    private static final String TAG = RNHMSAdsModule.class.getSimpleName();
    private final ReactApplicationContext reactContext;
    private RequestOptions mRequestOptions;
    private Promise mPromise;

    RNHMSAdsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public void onSuccess(ConsentStatus consentStatus, boolean isNeedConsent, List<AdProvider> adProviderList) {
        Log.i(TAG, "ConsentStatus: " + consentStatus + ", isNeedConsent: " + isNeedConsent);
        WritableMap result = new WritableNativeMap();
        result.putInt("consentStatus", consentStatus.getValue());
        result.putBoolean("isNeedConsent", isNeedConsent);
        result.putArray("adProviders", ReactUtils.mapList(adProviderList,
                ReactUtils::getWritableMapFromAdProvider));
        mPromise.resolve(result);
    }

    @Override
    public void onFail(String errorDescription) {
        Log.e(TAG, "User's consent failed to update: " + errorDescription);
        mPromise.reject("USER_CONSENT_FAILED", "Error: " + errorDescription);
    }

    @NonNull
    @Override
    public String getName() {
        return "RNHMSAds";
    }

    @ReactMethod
    public void init(final Promise promise) {
        HwAds.init(reactContext);
        Log.i(TAG, "init() is called.");
        mRequestOptions = HwAds.getRequestOptions();
        Log.i(TAG, "getRequestOptions() is called.");
        promise.resolve("Hw Ads initialized");
    }

    @ReactMethod
    public void getSDKVersion(final Promise promise) {
        promise.resolve(HwAds.getSDKVersion());
        Log.i(TAG, "getSDKVersion() is called.");
    }

    @ReactMethod
    public void setVideoMuted(final boolean isMuted) {
        HwAds.setVideoMuted(isMuted);
        Log.i(TAG, "setVideoMuted() is called.");
    }

    @ReactMethod
    public void setVideoVolume(final float videoVolume) {
        HwAds.setVideoVolume(videoVolume);
        Log.i(TAG, "setVideoVolume() is called.");
    }

    @ReactMethod
    public void getRequestOptions(final Promise promise) {
        if (mRequestOptions == null) {
            mRequestOptions = HwAds.getRequestOptions();
            Log.i(TAG, "getRequestOptions() is called.");
        }
        promise.resolve(ReactUtils.getWritableMapFromRequestOptions(mRequestOptions));
    }

    @ReactMethod
    public void setRequestOptions(ReadableMap requestOptions, final Promise promise) {
        HwAds.setRequestOptions(ReactUtils.getRequestOptionsFromReadableMap(requestOptions));
        Log.i(TAG, "setRequestOptions() is called.");
        mRequestOptions = HwAds.getRequestOptions();
        Log.i(TAG, "getRequestOptions() is called.");
        promise.resolve(ReactUtils.getWritableMapFromRequestOptions(mRequestOptions));
    }

    @ReactMethod
    public void setConsent(ReadableMap consent, final Promise promise) {
        Log.i(TAG, "Consent instance is being created...");
        Consent consentInfo = Consent.getInstance(reactContext);
        Log.i(TAG, "getInstance() is called.");
        mPromise = promise;
        if (ReactUtils.hasValidKey(consent, "consentStatus", ReadableType.Number)) {
            consentInfo.setConsentStatus(ConsentStatus.forValue(consent.getInt("consentStatus")));
            Log.i(TAG, "consentStatus attribute is set.");
        }
        if (ReactUtils.hasValidKey(consent, "debugNeedConsent", ReadableType.Number)) {
            consentInfo.setDebugNeedConsent(DebugNeedConsent.forValue(consent.getInt("debugNeedConsent")));
            Log.i(TAG, "debugNeedConsent attribute is set.");
        }
        if (ReactUtils.hasValidKey(consent, "underAgeOfPromise", ReadableType.Number)
                && consent.getInt("underAgeOfPromise") != UnderAge.PROMISE_UNSPECIFIED) {
            consentInfo.setUnderAgeOfPromise(consent.getInt("underAgeOfPromise") == UnderAge.PROMISE_TRUE);
            Log.i(TAG, "underAgeOfPromise attribute is set.");
        }
        if (ReactUtils.hasValidKey(consent, "testDeviceId", ReadableType.String)) {
            consentInfo.addTestDeviceId(consent.getString("testDeviceId"));
            Log.i(TAG, "testDeviceId attribute is set.");
        }
        Log.i(TAG, "Consent instance is created.");
        consentInfo.requestConsentUpdate(this);
        Log.i(TAG, "requestConsentUpdate() is called.");
    }

    @ReactMethod
    public void checkConsent(final Promise promise) {
        Log.i(TAG, "Consent instance is being created...");
        Consent consentInfo = Consent.getInstance(reactContext);
        Log.i(TAG, "getInstance() is called.");
        mPromise = promise;
        consentInfo.requestConsentUpdate(this);
        Log.i(TAG, "requestConsentUpdate() is called.");
    }

    static String getErrorMessage(int errorCode) {
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
