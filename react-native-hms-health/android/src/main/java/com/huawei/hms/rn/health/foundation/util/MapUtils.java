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

package com.huawei.hms.rn.health.foundation.util;

import static com.facebook.react.bridge.Arguments.createArray;
import static com.facebook.react.bridge.Arguments.createMap;
import static com.huawei.hms.rn.health.foundation.constant.Constants.ERROR_MESSAGE_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.IS_SUCCESS_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.RESULT_BODY_KEY;

import com.huawei.hms.hihealth.data.ActivityRecord;
import com.huawei.hms.hihealth.data.ActivitySummary;
import com.huawei.hms.hihealth.data.DataCollector;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.DeviceInfo;
import com.huawei.hms.hihealth.data.Field;
import com.huawei.hms.hihealth.data.Group;
import com.huawei.hms.hihealth.data.HealthRecord;
import com.huawei.hms.hihealth.data.MapValue;
import com.huawei.hms.hihealth.data.PaceSummary;
import com.huawei.hms.hihealth.data.SamplePoint;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.hihealth.data.ScopeLangItem;
import com.huawei.hms.hihealth.data.Value;
import com.huawei.hms.hihealth.result.ActivityRecordReply;
import com.huawei.hms.hihealth.result.HealthRecordReply;
import com.huawei.hms.hihealth.result.ReadReply;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.result.HuaweiIdAuthResult;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

/**
 * MapUtil exposes a set of helper methods for working with
 * {@link ReadableMap}, {@link Map <>}.
 *
 * @since v.5.0.1
 */
public class MapUtils {

    /**
     * Converts an Object to a WritableMap.
     *
     * @param message String instance.
     * @return WritableMap.
     */
    public static WritableMap toWritableMapWithMessage(final String message, final @Nullable Boolean isSuccess) {
        WritableMap writableMap = createMap();
        writableMap.putString("msg", message);
        if (isSuccess != null) {
            return addIsSuccess(writableMap, isSuccess);
        }

        return writableMap;
    }

    /**
     * Adds isSuccess value to an empty or already initialized writableMap instance.
     *
     * @param writableMap WritableMap instance, that can either be null or already initialized.
     * @param isSuccess Boolean Value.
     * @return WritableMap instance.
     */
    public static WritableMap addIsSuccess(@Nullable WritableMap writableMap, final @Nullable Boolean isSuccess) {
        if (writableMap == null) {
            writableMap = createMap();
        }

        if (isSuccess != null) {
            writableMap.putBoolean(IS_SUCCESS_KEY, isSuccess);
        }

        return writableMap;
    }

    /**
     * Adds errorMessage value to an empty or already initialized writableMap instance.
     *
     * @param writableMap WritableMap instance, that can either be null or already initialized.
     * @param errorMessage String Value.
     * @return WritableMap instance.
     */
    public static WritableMap addErrorMessage(@Nullable WritableMap writableMap, final String errorMessage) {
        if (writableMap == null) {
            writableMap = createMap();
        }
        writableMap.putString(ERROR_MESSAGE_KEY, errorMessage);
        return writableMap;
    }

    /**
     * Converts a HashMap into a WritableMap.
     *
     * @param map Map<String, Object> to be converted.
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final Map<String, ?> map) {
        WritableMap writableMap = Arguments.createMap();
        Iterator iterator = map.entrySet().iterator();
        try {
            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                Object value = pair.getValue();

                if (value == null) {
                    writableMap.putNull((String) pair.getKey());
                } else if (value instanceof Boolean) {
                    writableMap.putBoolean((String) pair.getKey(), (Boolean) value);
                } else if (value instanceof Double) {
                    writableMap.putDouble((String) pair.getKey(), (Double) value);
                } else if (value instanceof Float) {
                    writableMap.putDouble((String) pair.getKey(), ((Float) value).doubleValue());
                } else if (value instanceof Integer) {
                    writableMap.putInt((String) pair.getKey(), (Integer) value);
                } else if (value instanceof String) {
                    writableMap.putString((String) pair.getKey(), (String) value);
                } else if (value instanceof Map) {
                    writableMap.putMap((String) pair.getKey(), toWritableMap((Map<String, Object>) value));
                } else if (value.getClass().isArray()) {
                    writableMap.putArray((String) pair.getKey(), toWritableArray((Object[]) value));
                } else if (value instanceof Value) {
                    switch (((Value) value).getFormat()) {
                        case Field.FORMAT_INT32:
                            writableMap.putInt((String) pair.getKey(), ((Value) value).asIntValue());
                            break;
                        case Field.FORMAT_FLOAT:
                            writableMap.putDouble((String) pair.getKey(), ((Value) value).asDoubleValue());
                            break;
                        case Field.FORMAT_STRING:
                            writableMap.putString((String) pair.getKey(), ((Value) value).asStringValue());
                            break;
                        case Field.FORMAT_MAP:
                            Map<String, MapValue> floatMap = new HashMap<>();
                            for (String key : ((Value) value).getMap().keySet()) {
                                floatMap.put(key, ((Value) value).getMapValue(key));
                            }
                            writableMap.putMap((String) pair.getKey(), toWritableMap(floatMap));
                            break;
                        case Field.FORMAT_LONG:
                            writableMap.putInt((String) pair.getKey(), (int) ((Value) value).asLongValue());
                            break;
                        default:
                            break;
                    }
                }

                iterator.remove();
            }
        } catch (RuntimeException e) {
            ExceptionHandler.INSTANCE.fail(e);
        } catch (Exception exception) {
            ExceptionHandler.INSTANCE.fail(exception);
        }

        return writableMap;
    }

    /**
     * Converts a JSONObject instance to a WritableMap.
     *
     * @param jsonObject JSONObject instance.
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final JSONObject jsonObject) {
        WritableMap map = new WritableNativeMap();
        Iterator<String> iterator = jsonObject.keys();
        try {
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = jsonObject.get(key);
                if (value instanceof JSONObject) {
                    map.putMap(key, toWritableMap((JSONObject) value));
                } else if (value instanceof JSONArray) {
                    map.putArray(key, toWritableArray((JSONArray) value));
                }
                checkPrimitiveValuesInMap(map, key, value);
            }
        } catch (Exception exception) {
            ExceptionHandler.INSTANCE.fail(exception);
        }
        return map;
    }

    /**
     * Converts ReadableMap instance to a JSONObject.
     *
     * @param readableMap ReadableMap instance.
     * @return JSONObject.
     */
    public static JSONObject toJson(final ReadableMap readableMap) {
        JSONObject object = new JSONObject();
        if (readableMap == null) {
            return object;
        }
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            try {
                switch (readableMap.getType(key)) {
                    case Null:
                        object.put(key, JSONObject.NULL);
                        break;
                    case Boolean:
                        object.put(key, readableMap.getBoolean(key));
                        break;
                    case Number:
                        object.put(key, readableMap.getDouble(key));
                        break;
                    case String:
                        object.put(key, readableMap.getString(key));
                        break;
                    case Map:
                        object.put(key, toJson(readableMap.getMap(key)));
                        break;
                    case Array:
                        object.put(key, toJson(readableMap.getArray(key)));
                        break;
                    default:
                        break;
                }
            } catch (Exception exception) {
                ExceptionHandler.INSTANCE.fail(exception);
            }
        }
        return object;
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

    /* Private Helper Methods */

    /**
     * Converts a Object[] array into a WritableArray.
     *
     * @param array The Object[] array to be converted.
     * @return WritableArray
     */
    private static WritableArray toWritableArray(final Object[] array) {
        WritableArray writableArray = createArray();
        if (array == null) {
            return writableArray;
        }

        for (Object value : array) {
            if (value == null) {
                writableArray.pushNull();
            }
            if (value instanceof Boolean) {
                writableArray.pushBoolean((Boolean) value);
            }
            if (value instanceof Double) {
                writableArray.pushDouble((Double) value);
            }
            if (value instanceof Integer) {
                writableArray.pushInt((Integer) value);
            }
            if (value instanceof String) {
                writableArray.pushString((String) value);
            }
            if (value instanceof Map) {
                Map<String, Object> valueMap = (Map<String, Object>) value;
                writableArray.pushMap(toWritableMap(valueMap));
            }
            if (value != null && value.getClass().isArray()) {
                if (value instanceof Object[]) {
                    writableArray.pushArray(toWritableArray((Object[]) value));
                }
            }
        }

        return writableArray;
    }

    /**
     * Converts JsonArray into WritableArray.
     *
     * @param jsonArray JsonArray instance.
     * @return WritableArray instance
     * @throws JSONException JsonException
     */
    private static WritableArray convertJsonToArray(JSONArray jsonArray) throws JSONException {
        WritableArray array = new WritableNativeArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                array.pushMap(convertJsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                array.pushArray(convertJsonToArray((JSONArray) value));
            }
            checkPrimitiveValuesInArray(array, value);
        }
        return array;
    }

    /**
     * Converts JsonObject into WritableMap instance.
     *
     * @param jsonObject JsonObject instance.
     * @return WritableMap instance
     * @throws JSONException JsonException
     */
    private static WritableMap convertJsonToMap(JSONObject jsonObject) throws JSONException {
        WritableMap map = new WritableNativeMap();

        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.putMap(key, convertJsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                map.putArray(key, convertJsonToArray((JSONArray) value));
            }
            checkPrimitiveValuesInMap(map, key, value);
        }
        return map;
    }

    /**
     * Check for primitive values in the map.
     *
     * @param map WritableMap instance.
     * @param key String key.
     * @param value Object that will be saved into map as a value.
     */
    private static void checkPrimitiveValuesInMap(WritableMap map, String key, Object value) {
        if (value instanceof Boolean) {
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

    /**
     * Check for primitive values in the array.
     *
     * @param array WritableArray instance.
     * @param value Object that will be saved into map as a value.
     */
    private static void checkPrimitiveValuesInArray(WritableArray array, Object value) {
        if (value instanceof Boolean) {
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

    /**
     * toArray converts a ReadableArray into a Object[].
     *
     * @param readableArray The ReadableArray to be converted.
     * @return Object[]
     */
    public static Object[] toArray(final ReadableArray readableArray) {
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

    public static List<String> toList(Object[] array) {
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < array.length; i++) {
            list.add(String.valueOf(array[i]));
        }
        return list;
    }

    /**
     * Converts a ReadableMap into a HashMap.
     *
     * @param readableMap The ReadableMap to be converted.
     * @return A HashMap containing the data that was in the ReadableMap.
     */
    public static Map<String, ?> toMap(final ReadableMap readableMap) {
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
                    map.put(key, MapUtils.toMap(readableMap.getMap(key)));
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
     * Converts a JSONArray into a WritableArray.
     *
     * @param jsonArray JSONArray instance.
     * @return WritableArray
     */
    private static WritableArray toWritableArray(final JSONArray jsonArray) {
        WritableArray array = new WritableNativeArray();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Object value = jsonArray.get(i);
                if (value instanceof JSONObject) {
                    array.pushMap(toWritableMap((JSONObject) value));
                } else if (value instanceof JSONArray) {
                    array.pushArray(toWritableArray((JSONArray) value));
                }
                checkPrimitiveValuesInArray(array, value);
            }
        } catch (Exception exception) {
            ExceptionHandler.INSTANCE.fail(exception);
        }
        return array;
    }

    /**
     * Converts a ReadableArray to a JSONArray.
     *
     * @param readableArray ReadableArray instance.
     * @return JSONArray
     */
    private static JSONArray toJson(final ReadableArray readableArray) {
        JSONArray array = new JSONArray();
        if (readableArray == null) {
            return array;
        }
        try {
            for (int i = 0; i < readableArray.size(); i++) {
                switch (readableArray.getType(i)) {
                    case Boolean:
                        array.put(readableArray.getBoolean(i));
                        break;
                    case Number:
                        array.put(readableArray.getDouble(i));
                        break;
                    case String:
                        array.put(readableArray.getString(i));
                        break;
                    case Map:
                        array.put(toJson(readableArray.getMap(i)));
                        break;
                    case Array:
                        array.put(toJson(readableArray.getArray(i)));
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception exception) {
            ExceptionHandler.INSTANCE.fail(exception);
        }
        return array;
    }

    /**
     * Transform ReadableArray to List<String>
     *
     * @param array ReadableArray
     * @return List<String>
     */
    public static List<String> toStringList(ReadableArray array) {
        List<String> list = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                list.add(array.getString(i));
            }
        }
        return list;
    }

    /**
     * Creates writableMap instance with isSuccess status.
     *
     * @param isSuccess Boolean value.
     * @return WritableMap instance.
     */
    public static WritableMap createWritableMapWithSuccessStatus(final @Nullable Boolean isSuccess) {
        WritableMap writableMap = new WritableNativeMap();
        return addIsSuccess(writableMap, isSuccess);
    }

    /**
     * Convert the WritableArray to a WritableMap with Success Status
     *
     * @param writableArray WritableArray Instance
     * @param isSuccess Boolean value.
     * @return WritableMap instance.
     */
    public static WritableMap wrapWritableObjectWithSuccessStatus(final WritableArray writableArray,
        final @Nullable Boolean isSuccess) {
        WritableMap resultMap = createMap();
        resultMap.putArray(RESULT_BODY_KEY, writableArray);
        return addIsSuccess(resultMap, isSuccess);
    }

    /**
     * Convert the WritableMap to a WritableMap with Success Status
     *
     * @param writableMap WritableMap Instance
     * @param isSuccess Boolean value.
     * @return WritableMap instance.
     */
    public static WritableMap wrapWritableObjectWithSuccessStatus(final WritableMap writableMap,
        final @Nullable Boolean isSuccess) {
        WritableMap resultMap = createMap();
        resultMap.putMap(RESULT_BODY_KEY, writableMap);
        return addIsSuccess(resultMap, isSuccess);
    }

    /**
     * Convert the boolean to a WritableMap with Success Status
     *
     * @param isSuccess Boolean value.
     * @return WritableMap instance.
     */
    public static WritableMap wrapWritableObjectWithSuccessStatus(final Boolean bool, final Boolean isSuccess) {
        WritableMap resultMap = createMap();
        resultMap.putBoolean(RESULT_BODY_KEY, bool);
        return addIsSuccess(resultMap, isSuccess);
    }

    /**
     * Creates a WritableMap from a SampleSet Object.
     *
     * @param sampleSet SampleSet Object
     * @return WritableMap instance
     */
    public static WritableMap toWritableMap(final SampleSet sampleSet) {
        WritableMap writableMap = createMap();
        if (sampleSet != null) {
            writableMap.putMap("dataCollector", toWritableMap(sampleSet.getDataCollector()));
            writableMap.putBoolean("isEmpty", sampleSet.isEmpty());
            WritableArray points = new WritableNativeArray();
            for (SamplePoint samplePoint : sampleSet.getSamplePoints()) {
                points.pushMap(toWritableMap(samplePoint));
            }
            writableMap.putArray("samplePoints", points);
        }

        return writableMap;
    }

    /**
     * Creates a WritableArray instance from ReadReply Object.
     *
     * @param readReply ReadReply Object
     * @return WritableArray
     */
    public static WritableMap toWritableMap(final ReadReply readReply) {
        WritableMap writableMap = new WritableNativeMap();

        if (readReply != null) {
            WritableArray setArray = new WritableNativeArray();
            for (SampleSet set : readReply.getSampleSets()) {
                setArray.pushMap(toWritableMap(set));
            }
            WritableArray groupArray = new WritableNativeArray();

            for (Group group : readReply.getGroups()) {
                groupArray.pushMap(toWritableMap(group));
            }

            writableMap.putArray("sampleSets", setArray);
            writableMap.putArray("groups", groupArray);
        }
        return writableMap;
    }

    /**
     * Creates a WritableMap instance from DataType Object
     *
     * @param dataType DataType Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final DataType dataType) {
        WritableMap writableMap = createMap();
        if (dataType != null) {
            writableMap.putString("name", dataType.getName());
            WritableArray fieldsArray = new WritableNativeArray();
            for (Field field : dataType.getFields()) {
                fieldsArray.pushMap(toWritableMap(field));
            }
            writableMap.putArray("fields", fieldsArray);
        }
        return writableMap;
    }

    /**
     * Creates a WritableMap instance from Field Object
     *
     * @param field Field Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final Field field) {
        WritableMap map = createMap();
        if (field != null) {
            map.putString("name", field.getName());
            map.putInt("format", field.getFormat());
            map.putBoolean("isOptional", field.isOptional());
        }
        return map;
    }

    /**
     * Creates a WritableArray instance from Set of Scope Object
     *
     * @param scopeSet Scope<Set>
     * @return WritableArray instance
     */
    public static WritableArray toWritableArray(Set<Scope> scopeSet) {
        WritableArray array = createArray();
        if (scopeSet != null) {
            for (Scope scope : scopeSet) {
                array.pushString(scope.getScopeUri());
            }
        }
        return array;
    }

    /**
     * Creates a WritableMap instance from DataCollector Object
     *
     * @param dataCollector DataCollector Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final DataCollector dataCollector) {
        WritableMap map = createMap();
        if (dataCollector != null) {
            map.putString("dataCollectorName", dataCollector.getDataCollectorName());
            map.putString("packageName", dataCollector.getPackageName());
            map.putString("dataStreamId", dataCollector.getDataStreamId());
            map.putString("dataStreamName", dataCollector.getDataStreamName());
            map.putInt("dataGenerateType", dataCollector.getDataGenerateType());
            map.putString("deviceId", dataCollector.getDeviceId());
            map.putMap("dataType", toWritableMap(dataCollector.getDataType()));
            map.putMap("deviceInfo", toWritableMap(dataCollector.getDeviceInfo()));
            map.putBoolean("isLocalized", dataCollector.isLocalized());
        }
        return map;
    }

    /**
     * Creates a WritableMap instance from DeviceInfo Object
     *
     * @param deviceInfo DeviceInfo Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final DeviceInfo deviceInfo) {
        WritableMap map = createMap();
        if (deviceInfo != null) {
            map.putString("deviceIdentifier", deviceInfo.getDeviceIdentifier());
            map.putInt("deviceType", deviceInfo.getDeviceType());
            map.putString("manufacturer", deviceInfo.getManufacturer());
            map.putString("modelName", deviceInfo.getModelName());
            map.putInt("platformType", deviceInfo.getPlatformType());
            map.putString("uuid", deviceInfo.getUuid());
            map.putBoolean("isFromBleDevice", deviceInfo.isFromBleDevice());
        }
        return map;
    }

    /**
     * Creates a WritableMap instance from SamplePoint Object
     *
     * @param samplePoint SamplePoint Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final SamplePoint samplePoint) {
        WritableMap map = createMap();
        if (samplePoint != null) {
            map.putMap("fieldValues", toWritableMap(samplePoint.getFieldValues()));
            map.putString("startTime",
                Utils.INSTANCE.getDateFormat().format(new Date(samplePoint.getStartTime(TimeUnit.MILLISECONDS))));
            map.putString("endTime",
                Utils.INSTANCE.getDateFormat().format(new Date(samplePoint.getEndTime(TimeUnit.MILLISECONDS))));
            map.putString("samplingTime",
                Utils.INSTANCE.getDateFormat().format(new Date(samplePoint.getSamplingTime(TimeUnit.MILLISECONDS))));
            map.putMap("dataCollector", toWritableMap(samplePoint.getDataCollector()));
        }

        return map;
    }

    /**
     * Creates a WritableMap instance from Group Object
     *
     * @param group Group Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final Group group) {
        WritableMap map = createMap();
        if (group != null) {
            map.putString("startTime",
                Utils.INSTANCE.getDateFormat().format(new Date(group.getStartTime(TimeUnit.MILLISECONDS))));
            map.putString("endTime",
                Utils.INSTANCE.getDateFormat().format(new Date(group.getEndTime(TimeUnit.MILLISECONDS))));
            map.putInt("groupType", group.getGroupType());
            WritableArray sampleSets = new WritableNativeArray();
            for (SampleSet set : group.getSampleSets()) {
                sampleSets.pushMap(toWritableMap(set));
            }
            map.putArray("sampleSets", sampleSets);
            map.putBoolean("hasMoreSample", group.hasMoreSample());
            map.putInt("activityType", group.getActivityType());
            map.putString("activity", group.getActivity());
            map.putMap("activityRecord", toWritableMap(group.getActivityRecord()));
        }
        return map;
    }

    /**
     * Creates a WritableMap instance from ActivityRecord Object
     *
     * @param activityRecord ActivityRecord Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final ActivityRecord activityRecord) {
        WritableMap map = createMap();
        if (activityRecord != null) {
            map.putString("activityType", activityRecord.getActivityType());
            map.putString("appDetailsUrl", activityRecord.getAppDetailsUrl());
            map.putString("appDomainName", activityRecord.getAppDomainName());
            map.putString("appVersion", activityRecord.getAppVersion());
            map.putString("description", activityRecord.getDesc());
            map.putString("id", activityRecord.getId());
            map.putString("startTime",
                Utils.INSTANCE.getDateFormat().format(new Date(activityRecord.getStartTime(TimeUnit.MILLISECONDS))));
            map.putString("endTime",
                Utils.INSTANCE.getDateFormat().format(new Date(activityRecord.getEndTime(TimeUnit.MILLISECONDS))));
            map.putString("durationTime", Utils.INSTANCE.getDurationStringFromMilliseconds(
                activityRecord.getDurationTime(TimeUnit.MILLISECONDS)));
            map.putString("name", activityRecord.getName());
            map.putString("packageName", activityRecord.getPackageName());
            map.putBoolean("hasDurationTime", activityRecord.hasDurationTime());
            map.putBoolean("isKeepGoing", activityRecord.isKeepGoing());
            map.putString("timeZone", activityRecord.getTimeZone());
            map.putMap("activitySummary", toWritableMap(activityRecord.getActivitySummary()));
            map.putMap("deviceInfo", toWritableMap(activityRecord.getDeviceInfo()));
        }
        return map;
    }

    public static WritableMap toWritableMap(final HealthRecord healthRecord) {
        WritableMap map = createMap();
        if (healthRecord != null) {
            map.putString("metaData", healthRecord.getMetadata());
            map.putString("startTime",
                Utils.INSTANCE.getDateFormat().format(new Date(healthRecord.getStartTime(TimeUnit.MILLISECONDS))));
            map.putString("endTime",
                Utils.INSTANCE.getDateFormat().format(new Date(healthRecord.getEndTime(TimeUnit.MILLISECONDS))));
            map.putString("getHealthRecordId", healthRecord.getHealthRecordId());
        }
        return map;
    }

    /**
     * Creates a WritableMap instance from ActivitySummary Object
     *
     * @param activitySummary ActivitySummary Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final ActivitySummary activitySummary) {
        WritableMap map = createMap();
        if (activitySummary != null) {
            map.putMap("paceSummary", toWritableMap(activitySummary.getPaceSummary()));
            WritableArray dataSummary = new WritableNativeArray();
            for (SamplePoint point : activitySummary.getDataSummary()) {
                dataSummary.pushMap(toWritableMap(point));
            }
            map.putArray("dataSummary", dataSummary);
        }
        return map;
    }

    /**
     * Creates a WritableMap instance from PaceSummary Object
     *
     * @param paceSummary PaceSummary Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final PaceSummary paceSummary) {
        WritableMap map = createMap();
        if (paceSummary != null) {

            map.putDouble("avgPace", paceSummary.getAvgPace() != null ? paceSummary.getAvgPace() : 0);
            map.putDouble("bestPace", paceSummary.getBestPace() != null ? paceSummary.getBestPace() : 0);
            map.putMap("paceMap", toWritableMap(paceSummary.getPaceMap()));
            map.putMap("britishPaceMap", toWritableMap(paceSummary.getBritishPaceMap()));
            map.putMap("partTimeMap", toWritableMap(paceSummary.getPartTimeMap()));
            map.putMap("britishPartTimeMap", toWritableMap(paceSummary.getBritishPartTimeMap()));
            map.putMap("sportHealthPaceMap", toWritableMap(paceSummary.getSportHealthPaceMap()));
        }
        return map;
    }

    /**
     * Creates a WritableArray instance from ActivityRecordReply Object
     *
     * @param recordReply ActivityRecordReply Object
     * @return WritableArray
     */
    public static WritableArray toWritableArray(final ActivityRecordReply recordReply) {
        WritableArray writableArray = new WritableNativeArray();
        for (ActivityRecord record : recordReply.getActivityRecords()) {
            WritableMap activityRecordMap = toWritableMap(record);
            if (!recordReply.getSampleSet(record).isEmpty()) {
                WritableArray sampleSetArray = new WritableNativeArray();
                for (SampleSet set : recordReply.getSampleSet(record)) {
                    sampleSetArray.pushMap(toWritableMap(set));
                }
                activityRecordMap.putArray("sampleSets", sampleSetArray);
            }
            writableArray.pushMap(activityRecordMap);
        }

        return writableArray;
    }

    public static WritableArray toWritableArray(final HealthRecordReply recordReply) {
        WritableArray writableArray = new WritableNativeArray();

        for (HealthRecord healthRecord : recordReply.getHealthRecords()) {
            WritableMap healthRecordMap = toWritableMap(healthRecord);
            writableArray.pushMap(healthRecordMap);
        }

        return writableArray;
    }

    /**
     * Creates a WritableArray instance from List<ActivityRecord> Object
     *
     * @param recordList List<ActivityRecord> Object
     * @return WritableArray
     */
    public static WritableArray toWritableArray(final List<ActivityRecord> recordList) {
        WritableArray writableArray = new WritableNativeArray();
        for (ActivityRecord record : recordList) {
            writableArray.pushMap(toWritableMap(record));
        }
        return writableArray;
    }

    /**
     * Creates a WritableMap instance from ScopeLangItem Object
     *
     * @param scopeLangItem ScopeLangItem Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final ScopeLangItem scopeLangItem) {
        WritableMap writableMap = createMap();
        if (scopeLangItem != null) {
            writableMap.putString("appIconPath", scopeLangItem.getAppIconPath());
            writableMap.putString("appName", scopeLangItem.getAppName());
            writableMap.putString("authTime", scopeLangItem.getAuthTime());
            writableMap.putMap("url2Desc", toWritableMap(scopeLangItem.getUrl2Desc()));
        }
        return writableMap;
    }

    /**
     * Creates a WritableMap instance from HuaweiIdAuthResult Object
     *
     * @param authResult HuaweiIdAuthResult Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(HuaweiIdAuthResult authResult) {
        WritableMap writableMap = createMap();
        if (authResult != null) {
            writableMap.putMap("huaweiId", toWritableMap(authResult.getHuaweiId()));
        }
        return writableMap;
    }

    /**
     * Creates a WritableMap instance from AuthHuaweiId Object
     *
     * @param huaweiId AuthHuaweiId Object
     * @return WritableMap
     */
    public static WritableMap toWritableMap(AuthHuaweiId huaweiId) {
        WritableMap map = createMap();
        if (huaweiId != null) {
            map.putString("accessToken", huaweiId.getAccessToken());
            map.putString("authorizationCode", huaweiId.getAuthorizationCode());
            map.putString("avatarUriString", huaweiId.getAvatarUriString());
            map.putString("displayName", huaweiId.getDisplayName());
            map.putString("email", huaweiId.getEmail());
            map.putString("familyName", huaweiId.getFamilyName());
            map.putString("givenName", huaweiId.getGivenName());
            map.putString("idToken", huaweiId.getIdToken());
            map.putString("openId", huaweiId.getOpenId());
            map.putString("unionId", huaweiId.getUnionId());
            map.putArray("authorizedScopes", toWritableArray(huaweiId.getAuthorizedScopes()));
        }
        return map;
    }
}
