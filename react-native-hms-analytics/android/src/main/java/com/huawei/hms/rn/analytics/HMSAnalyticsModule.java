/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.analytics;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import javax.annotation.Nonnull;

public class HMSAnalyticsModule extends ReactContextBaseJavaModule {

    private HMSAnalyticsWrapper hmsAnalyticsWrapper;

    private ReactApplicationContext reactApplicationContext;

    public HMSAnalyticsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactApplicationContext = reactContext;
    }

    @Nonnull
    @Override
    public String getName() {
        return "HMSAnalyticsModule";
    }

    @ReactMethod
    public void getInstance(String routePolicy, final Promise promise) {
        if (routePolicy != null && !routePolicy.isEmpty()) {
            hmsAnalyticsWrapper = new HMSAnalyticsWrapper(this.reactApplicationContext, routePolicy, promise);
        } else {
            hmsAnalyticsWrapper = new HMSAnalyticsWrapper(this.reactApplicationContext, promise);
        }
    }

    @ReactMethod
    public void setAnalyticsEnabled(boolean enabled, Promise promise) {
        hmsAnalyticsWrapper.setAnalyticsEnabled(enabled, promise);
    }

    @ReactMethod
    public void setUserId(String userId, Promise promise) {
        hmsAnalyticsWrapper.setUserId(userId, promise);
    }

    @ReactMethod
    public void setUserProfile(String name, String value, Promise promise) {
        hmsAnalyticsWrapper.setUserProfile(name, value, promise);
    }

    @ReactMethod
    public void setPushToken(String token, Promise promise) {
        hmsAnalyticsWrapper.setPushToken(token, promise);
    }

    @ReactMethod
    public void setMinActivitySessions(int milliseconds, Promise promise) {
        hmsAnalyticsWrapper.setMinActivitySessions(milliseconds, promise);
    }

    @ReactMethod
    public void setSessionDuration(int milliseconds, Promise promise) {
        hmsAnalyticsWrapper.setSessionDuration(milliseconds, promise);
    }

    @ReactMethod
    public void clearCachedData(Promise promise) {
        hmsAnalyticsWrapper.clearCachedData(promise);
    }

    @ReactMethod
    public void getAAID(Promise promise) {
        hmsAnalyticsWrapper.getAAID(promise);
    }

    @ReactMethod
    public void getUserProfiles(boolean predefined, Promise promise) {
        hmsAnalyticsWrapper.getUserProfiles(predefined, promise);
    }

    @ReactMethod
    public void pageStart(String pageName, String pageClassOverride, Promise promise) {
        hmsAnalyticsWrapper.pageStart(pageName, pageClassOverride, promise);
    }

    @ReactMethod
    public void pageEnd(String pageName, Promise promise) {
        hmsAnalyticsWrapper.pageEnd(pageName, promise);
    }

    @ReactMethod
    public void onEvent(String event, ReadableMap rMap, Promise promise) {
        hmsAnalyticsWrapper.onEvent(event, rMap, promise);
    }

    @ReactMethod
    public void enableLog(Promise promise) {
        hmsAnalyticsWrapper.enableLog(promise);
    }

    @ReactMethod
    public void enableLogWithLevel(String level, Promise promise) {
        hmsAnalyticsWrapper.enableLogWithLevel(level, promise);
    }

    @ReactMethod
    public void deleteUserProfile(String name, Promise promise) {
        hmsAnalyticsWrapper.setUserProfile(name, null, promise);
    }

    @ReactMethod
    public void setRestrictionEnabled(Boolean enabled, Promise promise) {
        hmsAnalyticsWrapper.setRestrictionEnabled(enabled, promise);
    }

    @ReactMethod
    public void setCollectAdsIdEnabled(Boolean isEnabled, Promise promise) {
        hmsAnalyticsWrapper.setCollectAdsIdEnabled(isEnabled, promise);
    }

    @ReactMethod
    public void isRestrictionEnabled(Promise promise) {
        hmsAnalyticsWrapper.isRestrictionEnabled(promise);
    }

    @ReactMethod
    public void addDefaultEventParams(ReadableMap map, Promise promise) {
        hmsAnalyticsWrapper.addDefaultEventParams(map, promise);
    }

    @ReactMethod
    public void setReportPolicies(ReadableArray array, Promise promise) {
        hmsAnalyticsWrapper.setReportPolicies(array, promise);
    }

    @ReactMethod
    public void getReportPolicyThreshold(String reportPolicyType, Promise promise) {
        hmsAnalyticsWrapper.getReportPolicyThreshold(reportPolicyType, promise);
    }

    @ReactMethod
    public void enableLogger(Promise promise) {
        hmsAnalyticsWrapper.enableLogger(promise);
    }

    @ReactMethod
    public void disableLogger(Promise promise) {
        hmsAnalyticsWrapper.disableLogger(promise);
    }

    @ReactMethod
    public void setChannel(String channel, Promise promise) {
        hmsAnalyticsWrapper.setChannel(channel, promise);
    }

    @ReactMethod
    public void setPropertyCollection(String property, boolean enabled, Promise promise) {
        hmsAnalyticsWrapper.setPropertyCollection(property, enabled, promise);
    }

    @ReactMethod
    public void setCustomReferrer(String customReferrer, Promise promise) {
        hmsAnalyticsWrapper.setCustomReferrer(customReferrer, promise);
    }

    @ReactMethod
    public void getDataUploadSiteInfo(Promise promise) {
        hmsAnalyticsWrapper.getDataUploadSiteInfo(promise);
    }

}
