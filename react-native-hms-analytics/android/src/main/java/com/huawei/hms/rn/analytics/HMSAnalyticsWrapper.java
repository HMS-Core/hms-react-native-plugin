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

package com.huawei.hms.rn.analytics;

import android.content.Context;
import android.os.Build;
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
import java.util.Objects;
import java.util.Set;

import static com.facebook.react.bridge.Arguments.createMap;

public class HMSAnalyticsWrapper {

    private final String TAG = HMSAnalyticsWrapper.class.getSimpleName();
    private HiAnalyticsInstance instance;
    private final WeakReference<Context> weakContext;

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

    public HMSAnalyticsWrapper(Context context, String routePolicy, Promise promise) {
        this.weakContext = new WeakReference<>(context);
        this.instance = HiAnalytics.getInstance(getContext(), routePolicy);
        createResponseObj("response", true, promise);
    }

    public HMSAnalyticsWrapper(Context context, Promise promise) {
        this.weakContext = new WeakReference<>(context);
        this.instance = HiAnalytics.getInstance(getContext());
        createResponseObj("response", true, promise);
    }

    public void pageStart(String pageName, String pageClassOverride, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("pageStart");
        instance.pageStart(pageName, pageClassOverride);
        HMSLogger.getInstance(getContext()).sendSingleEvent("pageStart");
        createResponseObj("response", true, promise);
    }

    public void pageEnd(String pageName, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("pageEnd");
        instance.pageEnd(pageName);
        HMSLogger.getInstance(getContext()).sendSingleEvent("pageEnd");
        createResponseObj("response", true, promise);
    }

    public void onEvent(String event, ReadableMap rMap, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("onEvent");
        try {
            Bundle bundle = mapToBundle(rMap);
            instance.onEvent(event, bundle);
            createResponseObj("response", true, promise);
        } catch (IllegalArgumentException e) {
            createResponseObj("", e, promise);
        }
        HMSLogger.getInstance(getContext()).sendSingleEvent("onEvent");
    }

    public void setAnalyticsEnabled(boolean enabled, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setAnalyticsEnabled");
        instance.setAnalyticsEnabled(enabled);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setAnalyticsEnabled");
        createResponseObj("response", true, promise);
    }

    public void setUserId(String userId, Promise promise) throws IllegalArgumentException {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setUserId");
        instance.setUserId(userId);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setUserId");
        createResponseObj("response", true, promise);
    }

    public void setUserProfile(String name, String value, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setUserProfile");
        instance.setUserProfile(name, value);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setUserProfile");
        createResponseObj("response", true, promise);
    }

    public void setPushToken(String token, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setPushToken");
        instance.setPushToken(token);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setPushToken");
        createResponseObj("response", true, promise);
    }

    public void setMinActivitySessions(long milliseconds, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setMinActivitySessions");
        instance.setMinActivitySessions(milliseconds);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setMinActivitySessions");
        createResponseObj("response", true, promise);
    }

    public void setSessionDuration(int milliseconds, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setSessionDuration");
        instance.setSessionDuration(milliseconds);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setSessionDuration");
        createResponseObj("response", true, promise);
    }

    public void clearCachedData(Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("clearCachedData");
        instance.clearCachedData();
        HMSLogger.getInstance(getContext()).sendSingleEvent("clearCachedData");
        createResponseObj("response", true, promise);
    }

    public void getAAID(Promise promise) {
        try {
            HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getAAID");
            instance.getAAID()
                    .addOnSuccessListener(s -> createResponseObj("aaid", s, promise))
                    .addOnFailureListener(e -> createResponseObj("", e, promise));
        } catch (IllegalArgumentException e) {
            createResponseObj("Err", e.toString(), promise);
        }
    }

    public void isRestrictionEnabled(Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("isRestrictionEnabled");
        Boolean result = instance.isRestrictionEnabled();
        HMSLogger.getInstance(getContext()).sendSingleEvent("isRestrictionEnabled");
        createResponseObj("isRestrictionEnabled", result, promise);
    }

    public void addDefaultEventParams(ReadableMap map, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("addDefaultEventParams");
        Bundle bundle = mapToBundle(map);
        instance.addDefaultEventParams(bundle);
        createResponseObj("addDefaultEventParams", true, promise);
    }

    public void setRestrictionEnabled(Boolean enabled, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setRestrictionEnabled");
        instance.setRestrictionEnabled(enabled);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setRestrictionEnabled");
        createResponseObj("response", true, promise);
    }

    public void setReportPolicies(ReadableArray array, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setReportPolicies");
        ArrayList<Object> list = array.toArrayList();
        Set<ReportPolicy> policies = new HashSet<>();

        for (Object reportPolicy : list) {
            if (reportPolicy instanceof ReadableMap && (((ReadableMap) reportPolicy).hasKey("reportPolicyType"))) {
                if (((ReadableMap) reportPolicy).getString("reportPolicyType") == null) {
                    return;
                }
                ReportPolicy reportPolicyType = toReportPolicy(Objects.requireNonNull(((ReadableMap) reportPolicy).getString("reportPolicyType")));
                switch (reportPolicyType) {
                    case ON_SCHEDULED_TIME_POLICY:
                        if (((ReadableMap) reportPolicy).hasKey("seconds")) {
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
                        if (((ReadableMap) reportPolicy).hasKey("threshold")) {
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
        createResponseObj("response", true, promise);
    }

    public void getReportPolicyThreshold(String reportPolicyType, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getReportPolicyThreshold");
        long threshold = toReportPolicy(reportPolicyType).getThreshold();
        HMSLogger.getInstance(getContext()).sendSingleEvent("getReportPolicyThreshold");
        createResponseObj("threshold", threshold, promise);
    }

    public void getUserProfiles(boolean predefined, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getUserProfiles");

        Map<String, String> userProfiles = instance.getUserProfiles(predefined);

        if (userProfiles == null) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getUserProfiles");
            createResponseObj("response", true, promise);
            return;
        }

        WritableMap result = createMap();
        Set<Map.Entry<String, String>> entries = userProfiles.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            result.putString(entry.getKey(), entry.getValue());
        }
        HMSLogger.getInstance(getContext()).sendSingleEvent("getUserProfiles");
        promise.resolve(result);
    }

    public void setCollectAdsIdEnabled(boolean isEnabled, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("setCollectAdsIdEnabled");
        instance.setCollectAdsIdEnabled(isEnabled);
        HMSLogger.getInstance(getContext()).sendSingleEvent("setCollectAdsIdEnabled");
        createResponseObj("response", true, promise);
    }

    //HiAnalyticsTools

    public void enableLog(Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("enableLog");
        HiAnalyticsTools.enableLog();
        HMSLogger.getInstance(getContext()).sendSingleEvent("enableLog");
        createResponseObj("response", true, promise);
    }

    public void enableLogWithLevel(String level, Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("enableLogWithLevel");
        int intValueOfLevel = LogLevel.valueOf(level).intValue;
        HMSLogger.getInstance(getContext()).sendSingleEvent("enableLogWithLevel");
        HiAnalyticsTools.enableLog(intValueOfLevel);
        createResponseObj("response", true, promise);
    }

    //HMSLogger

    public void enableLogger(final Promise promise) {
        Log.i(TAG, "enableLogger:: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            HMSLogger.getInstance(getContext()).enableLogger();
        }
        createResponseObj("response", true, promise);
    }

    public void disableLogger(final Promise promise) {
        Log.i(TAG, "enableLogger:: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            HMSLogger.getInstance(getContext()).disableLogger();
        }
        createResponseObj("response", true, promise);
    }

    public <T> void createResponseObj(String key, T value, Promise promise) {
        WritableMap map = createMap();
        if (value instanceof String) {
            map.putString(key, value.toString());
        } else if (value instanceof Exception) {
            String message = ((Exception) value).getMessage();
            map.putString(key, message);
        } else if (value instanceof Boolean) {
            map.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            int valueInt = Integer.parseInt(String.valueOf(value));
            map.putInt(key, valueInt);
        }
        promise.resolve(map);
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
                    //not supported
                    break;
                case Array: {
                    ReadableArray rArray = map.getArray(key);
                    assert rArray != null;
                    ArrayList<Bundle> listBundle = bundleArrayList(rArray);
                    bundle.putParcelableArrayList("items", listBundle);
                    break;
                }
                default:
                    break;
            }
        }
        return bundle;
    }

    private ArrayList<Bundle> bundleArrayList(ReadableArray rArray) {
        ArrayList<Bundle> bundleArrayList = new ArrayList<>();
        for (int i = 0; i < rArray.size(); i++) {
            ReadableMap map = rArray.getMap(i);
            Bundle bundle = mapToBundle(map);
            bundleArrayList.add(bundle);
        }
        return bundleArrayList;
    }

    private ReportPolicy toReportPolicy(String reportPolicy) {
        switch (reportPolicy) {
            case "onScheduledTimePolicy":
                return ReportPolicy.ON_SCHEDULED_TIME_POLICY;
            case "onMoveBackgroundPolicy":
                return ReportPolicy.ON_MOVE_BACKGROUND_POLICY;
            case "onCacheThresholdPolicy":
                return ReportPolicy.ON_CACHE_THRESHOLD_POLICY;
            default:
                return ReportPolicy.ON_APP_LAUNCH_POLICY;
        }
    }

    private Context getContext() {
        return weakContext.get();
    }
}