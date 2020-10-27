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

package com.huawei.hms.rn.analytics;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.rn.analytics.logger.HMSLogger;

import java.lang.ref.WeakReference;


public class HmsAnalyticsModule extends ReactContextBaseJavaModule {

    private HmsAnalyticsWrapper hmsAnalyticsWrapper;
    private WeakReference<ReactContext> weakContext;

    public HmsAnalyticsModule(ReactApplicationContext reactContext) {
        super(reactContext);

        weakContext = new WeakReference<>(reactContext);
        hmsAnalyticsWrapper = new HmsAnalyticsWrapper(reactContext);
    }

    @Override
    public String getName() {
        return "HmsAnalytics";
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

    /**
     * Enables HMSLogger logging functions.
     */
    @ReactMethod
    public void enableLogger(Promise promise) {
        HMSLogger.getInstance(getContext()).enableLogger();
        promise.resolve(true);
    }

    /**
     * Disables HMSLogger logging functions.
     */
    @ReactMethod
    public void disableLogger(Promise promise) {
        HMSLogger.getInstance(getContext()).disableLogger();
        promise.resolve(true);
    }

    /**
     * Returns context instance.
     * @return Context
     */
    private ReactContext getContext(){
        return weakContext.get();
    }
}
