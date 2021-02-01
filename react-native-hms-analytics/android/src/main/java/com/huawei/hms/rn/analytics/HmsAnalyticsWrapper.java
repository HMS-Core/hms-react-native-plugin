/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

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

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.analytics.HiAnalyticsTools;
import com.huawei.hms.analytics.type.ReportPolicy;
import com.huawei.hms.rn.analytics.logger.HMSLogger;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.facebook.react.bridge.Arguments.createMap;
import static com.huawei.hms.utils.ResourceLoaderUtil.getString;

public class HmsAnalyticsWrapper {

    private String TAG = HmsAnalyticsWrapper.class.getSimpleName();

    private HiAnalyticsInstance instance;
    private WeakReference<Context> weakContext;

    public HmsAnalyticsWrapper(Context context) {
        this.instance = HiAnalytics.getInstance(context);
        this.weakContext = new WeakReference<>(context);
    }

    public void setAnalyticsEnabled(boolean enabled, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setAnalyticsEnabled");
        instance.setAnalyticsEnabled(enabled);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setAnalyticsEnabled");
        promise.resolve(getPlainWritableMap());
    }

    public void setUserId(String userId, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setUserId");
        instance.setUserId(userId);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setUserId");
        promise.resolve(getPlainWritableMap());
    }

    public void setUserProfile(String name, String value, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setUserProfile");

        instance.setUserProfile(name, value);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setUserProfile");
        promise.resolve(getPlainWritableMap());
    }

    public void setPushToken(String token, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setPushToken");

        instance.setPushToken(token);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setPushToken");
        promise.resolve(getPlainWritableMap());
    }

    public void setMinActivitySessions(long milliseconds, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setMinActivitySessions");

        instance.setMinActivitySessions(milliseconds);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setMinActivitySessions");
        promise.resolve(getPlainWritableMap());
    }

    public void setSessionDuration(int milliseconds, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setSessionDuration");

        instance.setSessionDuration(milliseconds);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setSessionDuration");

        promise.resolve(getPlainWritableMap());
    }

    public void clearCachedData(Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("clearCachedData");
        instance.clearCachedData();
        HMSLogger.getInstance(getContext()).sendSingleEvent("clearCachedData");
        promise.resolve(getPlainWritableMap());
    }

    public void getAAID(Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getAAID");

        instance.getAAID().addOnSuccessListener(aaid -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getAAID");
            promise.resolve(aaid);
        }).addOnFailureListener(ex -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getAAID", ex.getLocalizedMessage());
            promise.reject("Error: ",ex.getMessage());
        });
    }

    /**
     * Obtains the restriction status of HUAWEI Analytics.
     * @param promise: Promise instance.
     */
    public void isRestrictionEnabled(Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("isRestrictionEnabled");
        Boolean result = instance.isRestrictionEnabled();
        HMSLogger.getInstance(getContext()).sendSingleEvent("isRestrictionEnabled");
        promise.resolve(result);
    }

    /**
     * Specifies whether to enable restriction of HUAWEI Analytics. The default value is false, which indicates that HUAWEI Analytics is enabled by default.
     * @param enabled: Indicates whether to enable restriction of HUAWEI Analytics. The default value is false, which indicates that HUAWEI Analytics is enabled by default.
     * - true: Enables restriction of HUAWEI Analytics.
     * - false: Disables restriction of HUAWEI Analytics.
     * @param promise: Promise instance.
     */
    public void setRestrictionEnabled(Boolean enabled, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setRestrictionEnabled");
        instance.setRestrictionEnabled(enabled);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setRestrictionEnabled");
        promise.resolve(true);
    }

    /**
     * Sets the automatic event reporting policy.
     *
     * @param array: Policy for data reporting.
     * @param promise: Promise instance.
     */
    public void setReportPolicies(ReadableArray array, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setReportPolicies");
        ArrayList<Object> list = array.toArrayList();
        Set<ReportPolicy> policies = new HashSet<>();

        for(Object reportPolicy: list){
            if (reportPolicy instanceof ReadableMap && (((ReadableMap) reportPolicy).hasKey("reportPolicyType"))){
                if (((ReadableMap) reportPolicy).getString("reportPolicyType") == null){
                    return;
                }
                ReportPolicy reportPolicyType = toReportPolicy(((ReadableMap) reportPolicy).getString("reportPolicyType"));
                switch (reportPolicyType){
                    case ON_SCHEDULED_TIME_POLICY:
                        if (((ReadableMap) reportPolicy).hasKey("seconds")){
                            int timer = ((ReadableMap) reportPolicy).getInt("seconds");
                            ReportPolicy reportPolicyScheduled = ReportPolicy.ON_SCHEDULED_TIME_POLICY;
                            reportPolicyScheduled.setThreshold(timer);
                            policies.add(reportPolicyScheduled);
                            break;
                        }
                        break;
                    case ON_MOVE_BACKGROUND_POLICY:
                        policies.add(ReportPolicy.ON_MOVE_BACKGROUND_POLICY);
                        break;
                    case ON_CACHE_THRESHOLD_POLICY:
                        if (((ReadableMap) reportPolicy).hasKey("threshold")){
                            int threshold = ((ReadableMap) reportPolicy).getInt("threshold");
                            ReportPolicy reportPolicyThreshold = ReportPolicy.ON_CACHE_THRESHOLD_POLICY;
                            reportPolicyThreshold.setThreshold(threshold);
                            policies.add(reportPolicyThreshold);
                            break;
                        }
                        break;
                    default:
                        policies.add(ReportPolicy.ON_APP_LAUNCH_POLICY);
                }
            }
        }
        instance.setReportPolicies(policies);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setReportPolicies");
        promise.resolve(true);
    }

    /**
     * Obtains the threshold for event reporting.
     *
     * @param reportPolicyType: Event reporting policy name.
     * @param promise: Promise instance.
     */
    public void getReportPolicyThreshold(String reportPolicyType, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getReportPolicyThreshold");
        long threshold = toReportPolicy(reportPolicyType).getThreshold();
        HMSLogger.getInstance(getContext()).sendSingleEvent("getReportPolicyThreshold");
        promise.resolve(Long.toString(threshold));
    }

    private ReportPolicy toReportPolicy(String reportPolicy){
        ReportPolicy policy = ReportPolicy.ON_APP_LAUNCH_POLICY;
        switch (reportPolicy){
            case "onScheduledTimePolicy":
                return ReportPolicy.ON_SCHEDULED_TIME_POLICY;
            case "onAppLaunchPolicy":
                return ReportPolicy.ON_APP_LAUNCH_POLICY;
            case "onMoveBackgroundPolicy":
                return ReportPolicy.ON_MOVE_BACKGROUND_POLICY;
            case "onCacheThresholdPolicy":
                return ReportPolicy.ON_CACHE_THRESHOLD_POLICY;
        }
        return policy;
    }

    public void getUserProfiles(boolean predefined, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getUserProfiles");

        Map<String, String> userProfiles = instance.getUserProfiles(predefined);

        if (userProfiles == null ) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getUserProfiles");
            promise.resolve(getPlainWritableMap());
            return;
        }

        WritableMap result = createMap();
        Set<Map.Entry<String, String>> entries = userProfiles.entrySet();
        for(Map.Entry<String, String> entry : entries) {
            result.putString(entry.getKey(), entry.getValue());
        }
        HMSLogger.getInstance(getContext()).sendSingleEvent("getUserProfiles");
        promise.resolve(result);
    }


    public void pageStart(String pageName, String pageClassOverride, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("pageStart");
        instance.pageStart(pageName, pageClassOverride);
        HMSLogger.getInstance(getContext()).sendSingleEvent("pageStart");
        promise.resolve(getPlainWritableMap());
    }


    public void pageEnd(String pageName, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("pageEnd");

        instance.pageEnd(pageName);
        HMSLogger.getInstance(getContext()).sendSingleEvent("pageEnd");
        promise.resolve(getPlainWritableMap());
    }


    public void onEvent(String event, ReadableMap rMap, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("onEvent");
        Bundle bundle = mapToBundle(rMap);
        instance.onEvent(event, bundle);
        HMSLogger.getInstance(getContext()).sendSingleEvent("onEvent");
        promise.resolve(getPlainWritableMap());
    }

    private Bundle mapToBundle(ReadableMap map) {
        Bundle bundle = new Bundle();

        if (map == null) {
            Log.i(TAG, "event params is null");
            return bundle;
        }

        ReadableMapKeySetIterator keySetIterator = map.keySetIterator();
        while (keySetIterator.hasNextKey()) {
            String key = keySetIterator.nextKey();
            switch (map.getType(key)) {
                case Null:
                    //do nothing
                    break;
                case Boolean:
                    bundle.putBoolean(key, map.getBoolean(key));
                    break;
                case Number:
                    bundle.putDouble(key, map.getDouble(key));
                    break;
                case String:
                    bundle.putString(key, map.getString(key));
                    break;
                case Map:
                    //not supported in AGC
                    break;
                case Array:
                    //not supported in JAVA
                    break;
                default:
                    break;
            }
        }
        return bundle;
    }

    //-------------------------------------------------------------------------
    // HiAnalyticsTools
    //-------------------------------------------------------------------------

    public void enableLog(Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("enableLog");
        HiAnalyticsTools.enableLog();
        HMSLogger.getInstance(getContext()).sendSingleEvent("enableLog");
        promise.resolve(getPlainWritableMap());
    }

    public void enableLogWithLevel(String level, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("enableLogWithLevel");

        Integer intValueOfLevel = null;

        try {
            intValueOfLevel = LogLevel.valueOf(level).intValue;
        } catch (IllegalArgumentException ex) {
            String msg = "Invalid log level. level = " + level;
            HMSLogger.getInstance(getContext()).sendSingleEvent("enableLogWithLevel", msg);
            promise.reject("Error: ", msg);
            return;
        }
        HMSLogger.getInstance(getContext()).sendSingleEvent("enableLogWithLevel");
        HiAnalyticsTools.enableLog(intValueOfLevel);
        promise.resolve(getPlainWritableMap());
    }

    private WritableMap getPlainWritableMap(){
        WritableMap plainWritableMap = createMap();
        plainWritableMap.putBoolean("isSuccess", true);
        return plainWritableMap;
    }

    private enum LogLevel {
        DEBUG(3),
        INFO(4),
        WARN(5),
        ERROR(6);

        int intValue;

        LogLevel(int logLevel) {
            this.intValue = logLevel;
        }
    }

    /**
     * Returns context instance.
     * @return Context
     */
    private Context getContext(){
        return weakContext.get();
    }

}
