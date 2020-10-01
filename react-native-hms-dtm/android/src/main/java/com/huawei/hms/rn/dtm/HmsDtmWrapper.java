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
package com.huawei.hms.rn.dtm;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.rn.dtm.helpers.ContextHolder;
import com.huawei.hms.rn.dtm.logger.HMSLogger;

public class HmsDtmWrapper {
    private String TAG = "DTM Wrapper:: ";
    private String SUCCESS = "Success";
    private HiAnalyticsInstance analyticsInstance;
    private Context cContext;

    public HmsDtmWrapper(Context context) {
        cContext = context;
        this.analyticsInstance = HiAnalytics.getInstance(context);
        ContextHolder.getInstance().setContext(context);
    }

    public void onEvent(String eventId, ReadableMap map, Promise promise) {
        Log.i(TAG, "::onEvent::");
        HMSLogger.getInstance(cContext).startMethodExecutionTimer("onEvent: " + eventId);
        Bundle bundle = mapToBundle(TAG, eventId, map);
        analyticsInstance.onEvent(eventId, bundle);
        HMSLogger.getInstance(cContext).sendSingleEvent("onEvent: " + eventId);
        promise.resolve(SUCCESS);
    }

    public void customFunction(String eventId, ReadableArray readableArray, Promise promise) {
        Log.i(TAG, "onEvent-customFunction::");
        Bundle bundle = new Bundle();
        for (int i = 0; i < readableArray.size(); i++) {
            ReadableMap item = readableArray.getMap(i);
            assert item != null;
            if (item.hasKey("value") && item.hasKey("key")) {
                saveToBundle(bundle, item);
            }
        }
        HMSLogger.getInstance(cContext).startMethodExecutionTimer("onEvent: " + eventId);
        analyticsInstance.onEvent(eventId, bundle);
        HMSLogger.getInstance(cContext).sendSingleEvent("onEvent: " + eventId);
        promise.resolve(SUCCESS);
    }

    public void enableLogger(final Promise promise) {
        HMSLogger.getInstance(cContext).enableLogger();
        promise.resolve(true);
    }

    public void disableLogger(final Promise promise) {
        HMSLogger.getInstance(cContext).disableLogger();
        promise.resolve(true);
    }

    private Bundle mapToBundle(String TAG, String eventId, ReadableMap map) {
        Bundle bundle = new Bundle();
        if (map == null && eventId != null) {
            Log.i(TAG, "Event params is null");
            return bundle;
        }
        assert map != null;
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

    private void saveToBundle(Bundle bundle, @NonNull ReadableMap map) {
        if (map.hasKey("hasCustom"))
            HMSLogger.getInstance(cContext).startMethodExecutionTimer("CustomFunction::");
        String value = map.getString("value");
        switch (map.getType("key")) {
            case Boolean:
                bundle.putBoolean(value, map.getBoolean("key"));
                break;
            case Number:
                bundle.putDouble(value, map.getDouble("key"));
                break;
            case String:
                bundle.putString(value, map.getString("key"));
                break;
            default:
                break;
        }
    }
}
