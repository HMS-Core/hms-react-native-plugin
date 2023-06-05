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

package com.huawei.hms.rn.push.utils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapUtils {

    private MapUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static JSONObject toJSONObject(ReadableMap readableMap) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();

        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            ReadableType type = readableMap.getType(key);

            switch (type) {
                case Boolean:
                    jsonObject.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    jsonObject.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    jsonObject.put(key, readableMap.getString(key));
                    break;
                case Map:
                    jsonObject.put(key, MapUtils.toJSONObject(readableMap.getMap(key)));
                    break;
                case Array:
                    jsonObject.put(key, ArrayUtils.toJSONArray(readableMap.getArray(key)));
                    break;
                case Null:
                default:
                    jsonObject.put(key, null);
                    break;
            }
        }

        return jsonObject;
    }

    public static Map<String, Object> toMap(JSONObject jsonObject) {

        Map<String, Object> map = new HashMap<>();
        Iterator<String> iterator = jsonObject.keys();
        try {
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = jsonObject.get(key);

                if (value instanceof JSONObject) {
                    value = MapUtils.toMap((JSONObject) value);
                }
                if (value instanceof JSONArray) {
                    value = ArrayUtils.toArray((JSONArray) value);
                }

                map.put(key, value);
            }

            return map;
        } catch (JSONException e) {
            return map;
        }

    }

    public static Map<String, Object> toMap(ReadableMap readableMap) {

        Map<String, Object> map = new HashMap<>();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();

        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            ReadableType type = readableMap.getType(key);

            switch (type) {
                case Boolean:
                    map.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    map.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    map.put(key, readableMap.getString(key));
                    break;
                case Map:
                    map.put(key, MapUtils.toMap(readableMap.getMap(key)));
                    break;
                case Array:
                    map.put(key, ArrayUtils.toArray(readableMap.getArray(key)));
                    break;
                case Null:
                default:
                    map.put(key, null);
                    break;
            }
        }

        return map;
    }

    public static Map<String, String> toStringMap(ReadableMap readableMap) {

        Map<String, Object> objectMap = toMap(readableMap);
        Map<String, String> map = new HashMap<>();

        for (Map.Entry<String, Object> entry : objectMap.entrySet())
            map.put(entry.getKey(), String.valueOf(entry.getValue()));

        return map;
    }

    public static WritableMap toWritableMap(Map<String, Object> map) {

        WritableMap writableMap = Arguments.createMap();
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Object value = pair.getValue();

            if (value == null) {
                writableMap.putNull((String) pair.getKey());
            } else if (value instanceof Boolean) {
                writableMap.putBoolean((String) pair.getKey(), (Boolean) value);
            } else if (value instanceof Double) {
                writableMap.putDouble((String) pair.getKey(), (Double) value);
            } else if (value instanceof Integer) {
                writableMap.putInt((String) pair.getKey(), (Integer) value);
            } else if (value instanceof String) {
                writableMap.putString((String) pair.getKey(), (String) value);
            } else if (value instanceof Map) {
                writableMap.putMap((String) pair.getKey(), MapUtils.toWritableMap((Map<String, Object>) value));
            } else if (value instanceof JSONObject) {
                writableMap.putMap((String) pair.getKey(), MapUtils.toWritableMap(toMap((JSONObject) value)));
            } else {
                value.getClass();
                if (value.getClass().isArray()) {
                    writableMap.putArray((String) pair.getKey(), ArrayUtils.toWritableArray((Object[]) value));
                }
            }

            iterator.remove();
        }

        return writableMap;
    }

    public static WritableMap copyToWritableMap(Map<String, Object> map) {

        WritableMap writableMap = Arguments.createMap();

        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            Map.Entry pair = (Map.Entry) stringObjectEntry;
            Object value = pair.getValue();

            if (value == null) {
                writableMap.putNull((String) pair.getKey());
            } else if (value instanceof Double) {
                writableMap.putDouble((String) pair.getKey(), (Double) value);
            } else if (value instanceof Boolean) {
                writableMap.putBoolean((String) pair.getKey(), (Boolean) value);
            } else if (value instanceof Integer) {
                writableMap.putInt((String) pair.getKey(), (Integer) value);
            } else if (value instanceof Map) {
                writableMap.putMap((String) pair.getKey(), MapUtils.copyToWritableMap((Map<String, Object>) value));
            } else if (value instanceof JSONObject) {
                writableMap.putMap((String) pair.getKey(), MapUtils.toWritableMap(toMap((JSONObject) value)));
            } else if (value instanceof String) {
                writableMap.putString((String) pair.getKey(), (String) value);
            } else {
                value.getClass();
                if (value.getClass().isArray()) {
                    writableMap.putArray((String) pair.getKey(), ArrayUtils.toWritableArray((Object[]) value));
                }
            }
        }
        return writableMap;
    }

    public static WritableMap put(WritableMap writableMap, String key, Object value) {

        if (value == null) {
            writableMap.putNull(key);
        } else if (value instanceof Boolean) {
            writableMap.putBoolean(key, (Boolean) value);
        } else if (value instanceof Double) {
            writableMap.putDouble(key, (Double) value);
        } else if (value instanceof Integer) {
            writableMap.putInt(key, (Integer) value);
        } else if (value instanceof String) {
            writableMap.putString(key, (String) value);
        } else if (value instanceof Map) {
            writableMap.putMap(key, MapUtils.toWritableMap((Map<String, Object>) value));
        } else if (value instanceof WritableMap) {
            writableMap.putMap(key, (WritableMap) value);
        } else if (value instanceof WritableArray) {
            writableMap.putArray(key, (WritableArray) value);
        } else {
            value.getClass();
            if (value.getClass().isArray()) {
                writableMap.putArray(key, ArrayUtils.toWritableArray((Object[]) value));
            }
        }

        return writableMap;
    }
}
