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

package com.huawei.hms.rn.dtm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.rn.dtm.helpers.ContextHolder;
import com.huawei.hms.rn.dtm.helpers.MapHelper;
import com.huawei.hms.rn.dtm.interfaces.CustomVariable;
import com.huawei.hms.rn.dtm.logger.HMSLogger;

public class HMSDtmWrapper {
    private static String errorMessage = "Failed! Please check your params.";
    private static String successMessage = "Success";
    private static String tag = "DTM Wrapper:: ";
    private final HiAnalyticsInstance analyticsInstance;
    private final ReactContext cContext;

    public HMSDtmWrapper(ReactContext context) {
        cContext = context;
        this.analyticsInstance = HiAnalytics.getInstance(context);
        ContextHolder.getInstance().setContext(context);
    }

    public void onEvent(String eventId, ReadableMap map, Promise promise) {
        if (!isNetworkAvailable()) {
            String netWorkError = "Check your internet access !";
            WritableMap responseObject = MapHelper.createResponseObject(true,
                    "onEvent", netWorkError);
            promise.resolve(responseObject);
            return;
        }
        try {
            if (map == null || eventId == null) {
                WritableMap responseObject = MapHelper.createResponseObject(true,
                        "onEvent", errorMessage);
                promise.resolve(responseObject);
                return;
            }
            Log.i(tag, "onEvent:: ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                HMSLogger.getInstance(cContext).startMethodExecutionTimer("onEvent: " + eventId);
            }
            Bundle bundle = MapHelper.mapToBundle(map);
            analyticsInstance.onEvent(eventId, bundle);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                HMSLogger.getInstance(cContext).sendSingleEvent("onEvent: " + eventId);
            }
            WritableMap responseObject = MapHelper.createResponseObject(false,
                    "onEvent", successMessage);
            promise.resolve(responseObject);
        } catch (IllegalArgumentException e) {
            WritableMap responseObject = MapHelper.createResponseObject(true,
                    "onEvent", e.toString());
            promise.resolve(responseObject);
        }
    }

    public void setCustomVariable(String varName, String value, Promise promise) {
        try {
            Log.i(tag, "setCustomVariable:: ");
            if (!varName.isEmpty() && !value.isEmpty()) {
                CustomVariable.setter(varName, value);
                WritableMap responseObject = MapHelper.createResponseObject(false,
                        "setCustomVariable", successMessage);
                promise.resolve(responseObject);
            } else {
                WritableMap responseObject = MapHelper.createResponseObject(true,
                        "setCustomVariable", errorMessage);
                promise.resolve(responseObject);
            }
        } catch (IllegalArgumentException e) {
            WritableMap responseObject = MapHelper.createResponseObject(true,
                    "setCustomVariable", e.toString());
            promise.resolve(responseObject);
        }
    }

    public void enableLogger(final Promise promise) {
        Log.i(tag, "enableLogger:: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            HMSLogger.getInstance(cContext).enableLogger();
        }
        WritableMap responseObject = MapHelper.createResponseObject(false,
                "enableLogger", true);
        promise.resolve(responseObject);
    }

    public void disableLogger(final Promise promise) {
        Log.i(tag, "enableLogger:: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            HMSLogger.getInstance(cContext).disableLogger();
        }

        WritableMap responseObject = MapHelper.createResponseObject(false,
                "disableLogger", false);
        promise.resolve(responseObject);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = ((ConnectivityManager) cContext.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}