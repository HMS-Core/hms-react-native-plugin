/*
 * Copyright 2023-2024. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.hms.rn.mllanguage.helpers.utils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public final class HMSUtils {
    private static volatile HMSUtils instance;

    public static HMSUtils getInstance() {
        if (instance == null) {
            synchronized (HMSUtils.class) {
                if (instance == null) {
                    instance = new HMSUtils();
                }
            }
        }
        return instance;
    }

    /**
     * Converts JSONObject to WritableMap
     *
     * @param jsonObject JSONObject
     * @return WritableMap
     * @throws JSONException jsonObject.get throws it
     */
    public WritableMap convertJsonToWritableMap(JSONObject jsonObject) throws JSONException {
        WritableMap map = Arguments.createMap();
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.putMap(key, convertJsonToWritableMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                map.putArray(key, convertJsonToWritableArray((JSONArray) value));
            } else if (value instanceof Boolean) {
                map.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                map.putInt(key, (Integer) value);
            } else if (value instanceof Double) {
                map.putDouble(key, (Double) value);
            } else if (value instanceof String) {
                map.putString(key, (String) value);
            } else {
                map.putString(key, value.toString());
            }
        }
        return map;
    }

    /**
     * Converts JSONArray to WritableArray
     *
     * @param jsonArray JSONArray
     * @return WritableArray
     * @throws JSONException jsonObject.get throws it
     */
    private WritableArray convertJsonToWritableArray(JSONArray jsonArray) throws JSONException {
        WritableArray array = Arguments.createArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                array.pushMap(convertJsonToWritableMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                array.pushArray(convertJsonToWritableArray((JSONArray) value));
            } else if (value instanceof Boolean) {
                array.pushBoolean((Boolean) value);
            } else if (value instanceof Integer) {
                array.pushInt((Integer) value);
            } else if (value instanceof Double) {
                array.pushDouble((Double) value);
            } else if (value instanceof String) {
                array.pushString((String) value);
            } else {
                array.pushString(value.toString());
            }
        }

        return array;
    }



    /**
     * Converts string list to WritableArray
     *
     * @param list list of strings
     * @return WritableArray
     */
    public WritableArray convertStringListIntoWa(List<String> list) {
        WritableArray writableArray = Arguments.createArray();
        for (String value : list) {
            writableArray.pushString(value);
        }
        return writableArray;
    }

    /**
     * Checks if ReadableMap has valid key
     *
     * @param readableMap ReadableMap
     * @param key key to be checked
     * @param type key's type
     * @return true or false
     */
    public boolean hasValidKey(ReadableMap readableMap, String key, ReadableType type) {
        return readableMap.hasKey(key) && readableMap.getType(key) == type;
    }

    /**
     * Checks boolean key is valid or not and if valid then returns its value
     *
     * @param readableMap ReadableMap
     * @param key key to be checked
     * @return true or false
     */
    public boolean boolKeyCheck(ReadableMap readableMap, String key) {
        if (!hasValidKey(readableMap, key, ReadableType.Boolean)) {
            return false;
        }
        return readableMap.getBoolean(key);
    }

    /**
     * Converts byte array to WritableArray
     *
     * @param ba byte array
     * @return WritableArray
     */
    public WritableArray convertByteArrToWa(byte[] ba) {
        WritableArray wa = Arguments.createArray();
        for (byte b : ba) {
            wa.pushInt(b);
        }
        return wa;
    }



    /**
     * Converts Float array to WritableArray
     *
     * @param arr Float array
     * @return WritableArray
     */
    public WritableArray convert2DFloatArrToWa(Float[][] arr) {
        WritableArray wa = Arguments.createArray();
        for (float val : arr[0]) {
            wa.pushDouble(val);
        }
        return wa;
    }

}
