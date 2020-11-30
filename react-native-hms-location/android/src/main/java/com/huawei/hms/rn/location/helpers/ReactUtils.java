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

package com.huawei.hms.rn.location.helpers;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.huawei.hms.rn.location.backend.interfaces.ActivityHolder;
import com.huawei.hms.rn.location.backend.interfaces.HMSProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class ReactUtils {
    private static String TAG = ReactUtils.class.getSimpleName();

    public static <T extends HMSProvider> T initializeProvider(T provider, ReactContext ctx, ActivityHolder holder) {
        provider.setActivityHolder(holder);
        provider.setEventSender((eventName, eventValue) -> ReactUtils.sendEvent(ctx, eventName, toWM(eventValue)));
        provider.setPermissionHandler((reqCode, permissions) -> ((PermissionAwareActivity) Objects.requireNonNull(ctx.getCurrentActivity())).requestPermissions(permissions, reqCode, (requestCode, permissions1, grantResults) -> {
            provider.onRequestPermissionsResult(requestCode, permissions1, grantResults);
            return false;
        }));
        return provider;
    }

    public static Map<String, Object> getConstants(HMSProvider provider) {
        try {
            return toMap(provider.getConstants());
        } catch (JSONException e) {
            Log.e(TAG, "JSONEx :: " + e.getMessage());
            return new HashMap<>();
        }
    }

    public static WritableMap toWM(JSONObject json) {
        WritableMap map = Arguments.createMap();

        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            Object value = null;
            String key = iterator.next();

            try {
                value = json.get(key);
            } catch (JSONException ex) {
                Log.e(TAG, "JSONEx :: " + ex.getMessage());
            }

            if (value instanceof JSONObject) {
                map.putMap(key, toWM((JSONObject) value));
            } else if (value instanceof JSONArray) {
                map.putArray(key, toWA((JSONArray) value));
            } else if (value instanceof Boolean) {
                map.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                map.putInt(key, (Integer) value);
            } else if (value instanceof Double) {
                map.putDouble(key, (Double) value);
            } else if (value instanceof String) {
                map.putString(key, (String) value);
            } else {
                if (value != null) map.putString(key, value.toString());
            }
        }
        return map;
    }

    private static WritableArray toWA(JSONArray json) {
        WritableArray array = Arguments.createArray();

        for (int i = 0; i < json.length(); i++) {
            Object value = null;
            try {
                value = json.get(i);
            } catch (JSONException e) {
                Log.e(TAG, "JSONEx :: " + e.getMessage());
            }

            if (value instanceof JSONObject) {
                array.pushMap(toWM((JSONObject) value));
            } else if (value instanceof JSONArray) {
                array.pushArray(toWA((JSONArray) value));
            } else if (value instanceof Boolean) {
                array.pushBoolean((Boolean) value);
            } else if (value instanceof Integer) {
                array.pushInt((Integer) value);
            } else if (value instanceof Double) {
                array.pushDouble((Double) value);
            } else if (value instanceof String) {
                array.pushString((String) value);
            } else {
                if (value != null) array.pushString(value.toString());
            }
        }
        return array;
    }

    public static JSONArray toJA(ReadableArray array) {
        JSONArray json = new JSONArray();
        try {
            for (int i = 0; i < array.size(); i++) {
                switch (array.getType(i)) {
                    case Null:
                        break;
                    case Boolean:
                        json.put(array.getBoolean(i));
                        break;
                    case Number:
                        json.put(array.getDouble(i));
                        break;
                    case String:
                        json.put(array.getString(i));
                        break;
                    case Map:
                        json.put(toJO(array.getMap(i)));
                        break;
                    case Array:
                        json.put(toJA(array.getArray(i)));
                        break;
                    default:
                        break;
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONEx :: " + e.getMessage());
        }
        return json;
    }

    public static JSONObject toJO(ReadableMap map) {
        JSONObject object = new JSONObject();

        ReadableMapKeySetIterator iterator = map.keySetIterator();
        try {
            while (iterator.hasNextKey()) {
                String key = iterator.nextKey();
                switch (map.getType(key)) {
                    case Null:
                        object.put(key, JSONObject.NULL);
                        break;
                    case Boolean:
                        object.put(key, map.getBoolean(key));
                        break;
                    case Number:
                        object.put(key, map.getDouble(key));
                        break;
                    case String:
                        object.put(key, map.getString(key));
                        break;
                    case Map:
                        object.put(key, toJO(map.getMap(key)));
                        break;
                    case Array:
                        object.put(key, toJA(map.getArray(key)));
                        break;
                    default:
                        break;
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONEx :: " + e.getMessage());
        }
        return object;
    }

    public static Map<String, Object> toMap(JSONObject json) {
        Map<String, Object> map = new HashMap<>();

        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = null;
            try {
                value = json.get(key);
            } catch (JSONException e) {
                Log.e(TAG, "JSONEx :: " + e.getMessage());
            }

            if (value instanceof JSONObject) {
                map.put(key, toMap((JSONObject) value));
            } else {
                map.put(key, value);
            }
        }
        return map;
    }

    public static void sendEvent(ReactContext reactContext, String eventName, ReadableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
