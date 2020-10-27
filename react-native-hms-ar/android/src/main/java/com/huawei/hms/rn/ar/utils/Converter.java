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

package com.huawei.hms.rn.ar.utils;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.huawei.hiar.ARTrackable;
import com.huawei.hms.plugin.ar.core.config.ColorRGBA;
import com.huawei.hms.plugin.ar.core.serializer.PluginARTrackableSerializer;

import java.util.List;
import java.util.Map;

public class Converter {
    private static String TAG = Converter.class.getSimpleName();

    public static boolean hasValidKey(ReadableMap rm, String key, ReadableType type) {
        return rm.hasKey(key) && rm.getType(key) == type;
    }

    public static boolean hasValidElement(ReadableArray ra, int index, ReadableType type) {
        return !ra.isNull(index) && ra.getType(index) == type;
    }

    public static ColorRGBA toColorRGBA(final ReadableMap rm) {
        int red = 0;
        int blue = 0;
        int green = 0;
        int alpha = 0;
        if (rm != null) {
            if (hasValidKey(rm, "red", ReadableType.Number)) {
                red = (int) rm.getDouble("red");
            }
            if (hasValidKey(rm, "blue", ReadableType.Number)) {
                blue = (int) rm.getDouble("blue");
            }
            if (hasValidKey(rm, "green", ReadableType.Number)) {
                green = (int) rm.getDouble("green");
            }
            if (hasValidKey(rm, "alpha", ReadableType.Number)) {
                alpha = (int) rm.getDouble("alpha");
            }
        }
        return new ColorRGBA(red, green, blue, alpha);
    }

    public static WritableMap arTrackableToWritableMap(List<ARTrackable> arTrackables) {
        for (ARTrackable arTrackable : arTrackables) {
            Map<String, Object> m = PluginARTrackableSerializer.serialize(arTrackable);
            return toWritableMap(m);
        }
        return null;
    }

    public static WritableMap toWritableMap(final Map<String, Object> map) {
        WritableNativeMap result = new WritableNativeMap();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                result.putNull(key);
            } else if (value instanceof Map) {
                //noinspection unchecked,rawtypes
                result.putMap(key, toWritableMap((Map) value));
            } else if (value instanceof List) {
                //noinspection unchecked,rawtypes
                result.putArray(key, toWritableArray((List) value));
            } else if (value instanceof float[]) {
                WritableArray wa = Arguments.fromArray(value);
                result.putArray(key, wa);
            } else if (value instanceof String[]) {
                WritableArray wa = Arguments.fromArray(value);
                result.putArray(key, wa);
            } else if (value instanceof int[]) {
                WritableArray wa = Arguments.fromArray(value);
                result.putArray(key, wa);
            } else if (value instanceof Boolean) {
                result.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                result.putInt(key, (Integer) value);
            } else if (value instanceof String) {
                result.putString(key, (String) value);
            } else if (value instanceof Double) {
                result.putDouble(key, (Double) value);
            } else if (value instanceof Float) {
                result.putDouble(key, (float) value);
            } else {
                Log.e(TAG, "Could not convert object " + value.toString());
            }
        }
        return result;
    }

    private static WritableArray toWritableArray(List<Object> array) {
        WritableNativeArray result = new WritableNativeArray();

        for (Object value : array) {
            if (value == null) {
                result.pushNull();
            } else if (value instanceof Map) {
                //noinspection unchecked,rawtypes
                result.pushMap(toWritableMap((Map) value));
            } else if (value instanceof List) {
                //noinspection unchecked,rawtypes
                result.pushArray(toWritableArray((List) value));
            } else if (value instanceof float[]) {
                WritableArray wa = Arguments.fromArray(value);
                result.pushArray(wa);
            } else if (value instanceof Boolean) {
                result.pushBoolean((Boolean) value);
            } else if (value instanceof Integer) {
                result.pushInt((Integer) value);
            } else if (value instanceof String) {
                result.pushString((String) value);
            } else if (value instanceof Double) {
                result.pushDouble((Double) value);
            } else if (value instanceof Float) {
                result.pushDouble((float) value);
            } else {
                Log.e(TAG, "Could not convert object " + value.toString());
            }
        }
        return result;
    }
}
