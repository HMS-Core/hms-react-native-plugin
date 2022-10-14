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

package com.huawei.hms.rn.scan.utils;

import android.graphics.Point;
import android.util.ArrayMap;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.MapBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReactUtils {
    private static final String TAG = ReactUtils.class.getSimpleName();

    public interface NamedEvent {
        /**
         * Gets name of the event
         *
         * @return String of name of the event
         */
        String getName();
    }

    public interface NamedCommand {
        /**
         * Gets name of the command
         *
         * @return String of name of the command
         */
        String getName();
    }

    public interface Mapper<T, R> {
        /**
         * Used to map classes
         *
         * @param in mapped from
         * @return mapped to
         */
        R map(T in);
    }

    public static <R> List<R> mapReadableArray(ReadableArray array, Mapper<ReadableMap, R> mapper) {
        List<R> list = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                list.add(mapper.map(array.getMap(i)));
            }
        }
        return list;
    }

    public static <R> List<R> mapDoubleReadableArray(ReadableArray array, Mapper<ReadableArray, R> mapper) {
        List<R> list = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                list.add(mapper.map(array.getArray(i)));
            }
        }
        return list;
    }

    public static <T> WritableArray mapList(List<T> list, Mapper<T, WritableMap> mapper) {
        WritableArray array = new WritableNativeArray();
        if (list != null) {
            for (T item : list) {
                array.pushMap(mapper.map(item));
            }
        }
        return array;
    }

    public static boolean hasValidKey(ReadableMap rm, String key, ReadableType type) {
        return rm.hasKey(key) && rm.getType(key) == type;
    }

    public static boolean hasValidElement(ReadableArray ra, int index, ReadableType type) {
        return !ra.isNull(index) && ra.getType(index) == type;
    }

    public static WritableMap getWritableMapFromPoint(Point obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putInt("x", obj.x);
        wm.putInt("y", obj.y);
        return wm;
    }

    public static Point getPointFromReadableMap(ReadableMap rm) {
        if (rm != null && hasValidKey(rm, "x", ReadableType.Number) && hasValidKey(rm, "y", ReadableType.Number)) {
            return new Point(rm.getInt("x"), rm.getInt("y"));
        }
        return null;
    }

    public static int[] getIntegerArrayFromReadableArray(ReadableArray ra) {
        if (ra == null) {
            return new int[] {};
        }
        int[] intArray = new int[ra.size()];
        for (int i = 0; i < ra.size(); i++) {
            if (hasValidElement(ra, i, ReadableType.Number)) {
                intArray[i] = ra.getInt(i);
            }
        }
        return intArray;
    }

    public static long[] getLongArrayFromReadableArray(ReadableArray ra) {
        if (ra == null) {
            return new long[] {};
        }
        long[] longArray = new long[ra.size()];
        for (int i = 0; i < ra.size(); i++) {
            if (hasValidElement(ra, i, ReadableType.Number)) {
                longArray[i] = (long) ra.getDouble(i);
            }
        }
        return longArray;
    }

    /**
     * toArray converts a ReadableArray into a Object[].
     *
     * @param readableArray The ReadableArray to be converted.
     * @return Object[]
     */
    private static Object[] toArray(final ReadableArray readableArray) {
        if (readableArray == null || readableArray.size() == 0) {
            return new Object[0];
        }

        Object[] array = new Object[readableArray.size()];
        for (int i = 0; i < readableArray.size(); i++) {
            ReadableType type = readableArray.getType(i);

            switch (type) {
                case Null:
                    array[i] = null;
                    break;
                case Boolean:
                    array[i] = readableArray.getBoolean(i);
                    break;
                case Number:
                    array[i] = readableArray.getDouble(i);
                    break;
                case String:
                    array[i] = readableArray.getString(i);
                    break;
                case Map:
                    array[i] = toMap(readableArray.getMap(i));
                    break;
                case Array:
                    array[i] = toArray(readableArray.getArray(i));
                    break;
                default:
                    break;
            }
        }

        return array;
    }

    /**
     * Converts a ReadableMap into a HashMap.
     *
     * @param readableMap The ReadableMap to be converted.
     * @return A HashMap containing the data that was in the ReadableMap.
     */
    private static Map<String, Object> toMap(final ReadableMap readableMap) {
        Map<String, Object> map = new HashMap<>();
        if (readableMap == null) {
            return map;
        }
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();

        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            ReadableType type = readableMap.getType(key);

            switch (type) {
                case Null:
                    map.put(key, null);
                    break;
                case Boolean:
                    map.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    map.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    String valueStr = readableMap.getString(key);
                    if (valueStr != null) {
                        map.put(key, valueStr);
                        break;
                    }
                    break;
                case Map:
                    map.put(key, toMap(readableMap.getMap(key)));
                    break;
                case Array:
                    map.put(key, toArray(readableMap.getArray(key)));
                    break;
                default:
                    break;
            }
        }

        return map;
    }

    /**
     * toArrayList converts a ReadableArray into a ArrayList<Object>.
     *
     * @param array The ReadableArray to be converted.
     * @return ArrayList<Object>
     */
    public static ArrayList<Object> toArrayList(ReadableArray array) {
        if (array == null || array.size() == 0) {
            return new ArrayList<>();
        }
        ArrayList<Object> arrayList = new ArrayList<>(array.size());
        for (int i = 0, size = array.size(); i < size; i++) {
            switch (array.getType(i)) {
                case Null:
                    arrayList.add(null);
                    break;
                case Boolean:
                    arrayList.add(array.getBoolean(i));
                    break;
                case Number:
                    arrayList.add(array.getDouble(i));
                    break;
                case String:
                    arrayList.add(array.getString(i));
                    break;
                case Map:
                    arrayList.add(toMap(array.getMap(i)));
                    break;
                case Array:
                    arrayList.add(toArrayList(array.getArray(i)));
                    break;
                default:
                    throw new IllegalArgumentException("Could not convert object at index " + i + ".");
            }
        }
        return arrayList;
    }

    /**
     * toWM converts a String into a WritableMap.
     *
     * @param s The String to be converted.
     * @return WritableMap
     */
    public static WritableMap toWM(String s) {
        WritableMap response = null;
        try {
            response = toWM(new JSONObject(s));
        } catch (JSONException e) {
            Log.e(TAG, "JSONException" + e.getMessage());
        }
        return response;
    }

    /**
     * toWA converts a String into a WritableArray.
     *
     * @param s The String to be converted.
     * @return WritableArray
     */
    public static WritableArray toWA(String s) {
        WritableArray response = null;
        try {
            response = toWA(new JSONArray(s));
        } catch (JSONException e) {
            Log.e(TAG, "JSONException" + e.getMessage());
        }
        return response;
    }

    public static WritableMap toWM(JSONObject json) {
        WritableMap map = Arguments.createMap();

        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            Object value = null;
            String key = iterator.next();

            try {
                value = json.get(key);
            } catch (JSONException e) {
                Log.e(TAG, "JSONException" + e.getMessage());
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
                if (value != null) {
                    map.putString(key, value.toString());
                }
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
                Log.e(TAG, "JSONException" + e.getMessage());
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
                if (value != null) {
                    array.pushString(value.toString());
                }
            }
        }
        return array;
    }

    public static Map<String, Object> getExportedCustomDirectEventTypeConstantsFromEvents(NamedEvent[] eventList) {
        Map<String, Object> obj = new ArrayMap<>();
        for (NamedEvent event : eventList) {
            obj.put(event.getName(), MapBuilder.of("registrationName", event.getName()));
        }
        return obj;
    }

    public static Map<String, Integer> getCommandsMap(NamedCommand[] commandList) {
        Map<String, Integer> obj = new ArrayMap<>();
        for (int i = 0; i < commandList.length; i++) {
            obj.put(commandList[i].getName(), i);
        }
        return obj;
    }

    public static NamedCommand getCommand(String commandId, NamedCommand[] commands) {
        for (NamedCommand command : commands) {
            if (command.getName().equals(commandId)) {
                return command;
            }
        }
        return null;
    }
}
