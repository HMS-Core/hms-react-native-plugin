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

package com.huawei.hms.rn.dtm.helpers;

import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.facebook.react.bridge.Arguments.createMap;

public class MapHelper {

    /**
     * Converts a ReadableMap instance to a Bundle.
     *
     * @param map: JSONObject instance.
     * @return WritableMap
     */

    public static Bundle mapToBundle(ReadableMap map) {
        String tag = "DTM wrapper::";
        Bundle bundle = new Bundle();

        if (map == null) {
            Log.i(tag, "event params is null");
            return bundle;
        }

        ReadableMapKeySetIterator keySetIterator = map.keySetIterator();
        while (keySetIterator.hasNextKey()) {
            String key = keySetIterator.nextKey();
            switch (map.getType(key)) {
                case Boolean:
                    bundle.putBoolean(key, map.getBoolean(key));
                    break;
                case Number:
                    bundle.putDouble(key, map.getDouble(key));
                    break;
                case String:
                    bundle.putString(key, map.getString(key));
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

    private static ArrayList<Bundle> bundleArrayList(ReadableArray rArray) {
        ArrayList<Bundle> bundleArrayList = new ArrayList<>();
        for (int i = 0; i < rArray.size(); i++) {
            ReadableMap map = rArray.getMap(i);
            Bundle bundle = mapToBundle(map);
            bundleArrayList.add(bundle);
        }
        return bundleArrayList;
    }


    /**
     * Converts an DTM response Object to a WritableMap.
     *
     * @param instance: DTM response Object.
     * @param <T>:      Generic class type.
     * @param hasError: boolean.
     * @return WritableMap
     */
    public static <T> WritableMap createResponseObject(boolean hasError, String methodName, final T instance) {
        WritableMap writableMap = createMap();
        if (methodName.isEmpty()) {
            return writableMap;
        }

        // create an error message
        if (hasError && instance instanceof String) {
            String errorMessage = (String) instance;
            writableMap.putString("errorMessage", errorMessage);
        } else if (hasError) {
            writableMap.putString("errorMessage", methodName + " failed!");
        } else {
            writableMap.putString("errorMessage", null);
        }

        // create response data
        if (instance == null) {
            writableMap.putString("data", null);
        } else if (instance instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) instance;
            writableMap = toWritableMap(jsonObject);
        } else if (instance instanceof String) {
            String instanceString = (String) instance;
            writableMap.putString("data", instanceString);
        } else if (instance instanceof Boolean) {
            boolean instanceBoolean = (Boolean) instance;
            writableMap.putBoolean("data", instanceBoolean);
        } else {
            writableMap.putString("data", "Success");
        }
        return writableMap;
    }

    /**
     * Converts a JSONObject instance to a WritableMap.
     *
     * @param jsonObject: JSONObject instance.
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final JSONObject jsonObject) {
        WritableMap map = new WritableNativeMap();
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                Object value = jsonObject.get(key);
                if (value instanceof JSONObject) {
                    map.putMap(key, toWritableMap((JSONObject) value));
                } else if (value instanceof JSONArray) {
                    map.putArray(key, toWritableArray((JSONArray) value));
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
            } catch (JSONException e) {
                createResponseObject(true, "toWritableMap", e.toString());
            }
        }
        return map;
    }

    /**
     * Converts a JSONArray into a WritableArray.
     *
     * @param jsonArray: JSONArray instance.
     * @return WritableArray
     */
    public static WritableArray toWritableArray(final JSONArray jsonArray) {
        WritableArray array = new WritableNativeArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Object value = jsonArray.get(i);
                if (value instanceof JSONObject) {
                    array.pushMap(toWritableMap((JSONObject) value));
                } else if (value instanceof JSONArray) {
                    array.pushArray(toWritableArray((JSONArray) value));
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
            } catch (JSONException e) {
                createResponseObject(true, "toWritableArray", e.toString());
            }
        }
        return array;
    }
}