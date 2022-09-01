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

package com.huawei.hms.rn.contactshield.utils;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.huawei.hms.contactshield.ContactDetail;
import com.huawei.hms.contactshield.ContactSketch;
import com.huawei.hms.contactshield.ContactWindow;
import com.huawei.hms.contactshield.DailySketch;
import com.huawei.hms.contactshield.DiagnosisConfiguration;
import com.huawei.hms.contactshield.PeriodicKey;
import com.huawei.hms.contactshield.ScanInfo;
import com.huawei.hms.contactshield.SharedKeysDataMapping;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Utils {

    public static WritableArray getIntArray(int[] i) {
        WritableArray wa = new WritableNativeArray();
        for (int intArray : i) {
            wa.pushInt(intArray);
        }
        return wa;
    }

    public static WritableArray getByteArray(byte[] b) {
        WritableArray wa = new WritableNativeArray();
        for (byte byteArray : b) {
            wa.pushInt(byteArray);
        }
        return wa;
    }

    public static WritableMap fromSharedKeysDataMappingToMap(SharedKeysDataMapping sharedKeysDataMapping)
        throws JSONException {
        WritableMap wm = new WritableNativeMap();
        final JSONObject jsonObject = new JSONObject();
        if (sharedKeysDataMapping == null) {
            return null;
        }

        wm.putInt("defaultContagiousness", sharedKeysDataMapping.getDefaultContagiousness());
        wm.putInt("defaultReportType", sharedKeysDataMapping.getDefaultReportType());
        jsonObject.put("daysSinceCreationToContagiousness",
            sharedKeysDataMapping.getDaysSinceCreationToContagiousness());
        wm.putMap("daysSinceCreationToContagiousness", convertJsonToMap(jsonObject));
        return wm;
    }

    public static WritableMap fromDailySketchDataToMap(DailySketch dailySketch) {
        WritableMap wm = new WritableNativeMap();
        if (dailySketch == null) {
            return null;
        }

        wm.putInt("daysSinceEpoch", dailySketch.getDaysSinceEpoch());
        wm.putDouble("maxScore", dailySketch.getSketchData().getMaxScore());
        wm.putDouble("scoreSum", dailySketch.getSketchData().getScoreSum());
        wm.putDouble("weightedDurationSum", dailySketch.getSketchData().getWeightedDurationSum());
        return wm;
    }

    public static WritableArray fromDailySketchListToMap(List<DailySketch> dailySketches) {
        WritableArray array = new WritableNativeArray();
        for (DailySketch dailySketch : dailySketches) {
            array.pushMap(fromDailySketchDataToMap(dailySketch));
        }
        return array;
    }

    public static WritableMap fromContactSketchDataToMap(ContactSketch contactSketch) {
        WritableMap wm = new WritableNativeMap();
        if (contactSketch == null) {
            return null;
        }

        wm.putArray("attenuationDurations", getIntArray(contactSketch.getAttenuationDurations()));
        wm.putInt("daysSinceLastHit", contactSketch.getDaysSinceLastHit());
        wm.putInt("maxRiskValue", contactSketch.getMaxRiskValue());
        wm.putInt("numberOfHits", contactSketch.getNumberOfHits());
        wm.putInt("summationRiskValue", contactSketch.getSummationRiskValue());
        return wm;
    }

    public static WritableArray fromContactDetailsListToMap(List<ContactDetail> contactDetailList) {
        WritableArray array = new WritableNativeArray();
        for (ContactDetail contactDetail : contactDetailList) {
            array.pushMap(fromContactDetailsDataToMap(contactDetail));
        }
        return array;
    }

    public static WritableMap fromContactDetailsDataToMap(ContactDetail contactDetail) {
        WritableMap wm = new WritableNativeMap();
        if (contactDetail == null) {
            return null;
        }
        wm.putInt("attenuationRiskValue", contactDetail.getAttenuationRiskValue());
        wm.putInt("durationMinutes", contactDetail.getDurationMinutes());
        wm.putInt("totalRiskValue", contactDetail.getTotalRiskValue());
        wm.putInt("totalRiskLevel", contactDetail.getInitialRiskLevel());
        wm.putArray("attenuationDurations", getIntArray(contactDetail.getAttenuationDurations()));
        wm.putDouble("dayNumber", contactDetail.getDayNumber());
        return wm;
    }

    public static WritableArray fromPeriodicKeyListToMap(List<PeriodicKey> periodicKeyList) {
        if (periodicKeyList == null) {
            return null;
        }
        WritableArray array = new WritableNativeArray();
        for (PeriodicKey periodicKey : periodicKeyList) {
            array.pushMap(fromPeriodicKeyToMap(periodicKey));
        }
        return array;
    }

    public static WritableMap fromPeriodicKeyToMap(PeriodicKey periodicKey) {
        WritableMap wm = new WritableNativeMap();
        if (periodicKey == null) {
            return null;
        }
        wm.putArray("content", getByteArray(periodicKey.getContent()));
        wm.putInt("initialRiskLevel", periodicKey.getInitialRiskLevel());
        wm.putDouble("periodicKeyLifeTime", periodicKey.getPeriodicKeyLifeTime());
        wm.putDouble("periodicKeyValidTime", periodicKey.getPeriodicKeyValidTime());
        wm.putInt("reportType", periodicKey.getReportType());
        return wm;
    }

    public static WritableArray fromContactWindowListToMap(List<ContactWindow> contactWindowList) {
        if (contactWindowList == null) {
            return null;
        }
        WritableArray array = new WritableNativeArray();
        for (ContactWindow contactWindow : contactWindowList) {
            array.pushMap(fromContactWindowToMap(contactWindow));
        }
        return array;
    }

    public static WritableMap fromContactWindowToMap(ContactWindow contactWindow) {
        WritableMap wm = new WritableNativeMap();
        WritableArray scanMap = new WritableNativeArray();
        if (contactWindow == null) {
            return null;
        }

        wm.putInt("reportType", contactWindow.getReportType());
        wm.putDouble("dateMillis", contactWindow.getDateMillis());

        for (final ScanInfo scanInfo : contactWindow.getScanInfos()) {
            scanMap.pushMap(fromScanInfoToMap(scanInfo));
        }
        wm.putArray("scanInfos", scanMap);
        return wm;
    }

    public static WritableMap fromScanInfoToMap(ScanInfo scanInfo) {
        WritableMap wm = new WritableNativeMap();
        if (scanInfo == null) {
            return null;
        }

        wm.putInt("averageAttenuation", scanInfo.getAverageAttenuation());
        wm.putInt("minimumAttenuation", scanInfo.getMinimumAttenuation());
        wm.putInt("secondsSinceLastScan", scanInfo.getSecondsSinceLastScan());

        return wm;
    }

    public static WritableMap fromDiagnosisConfigurationToMap(DiagnosisConfiguration diagnosisConfiguration) {
        WritableMap wm = new WritableNativeMap();

        wm.putArray("attenuationDurationThresholds",
            getIntArray(diagnosisConfiguration.getAttenuationDurationThresholds()));
        wm.putArray("attenuationRiskValues", getIntArray(diagnosisConfiguration.getAttenuationRiskValues()));
        wm.putArray("daysAfterContactedRiskValues",
            getIntArray(diagnosisConfiguration.getDaysAfterContactedRiskValues()));
        wm.putArray("durationRiskValues", getIntArray(diagnosisConfiguration.getDurationRiskValues()));
        wm.putArray("initialRiskLevelRiskValues", getIntArray(diagnosisConfiguration.getInitialRiskLevelRiskValues()));
        wm.putInt("minimumRiskValueThreshold", diagnosisConfiguration.getMinimumRiskValueThreshold());

        return wm;
    }

    public static JSONObject toJSONObject(ReadableMap readableMap) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();

        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            ReadableType type = readableMap.getType(key);

            switch (type) {
                case Null:
                    jsonObject.put(key, null);
                    break;
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
                    jsonObject.put(key, Utils.toJSONObject(readableMap.getMap(key)));
                    break;
                case Array:
                    jsonObject.put(key, toJSONArray(readableMap.getArray(key)));
                    break;
                default:
                    break;
            }
        }
        return jsonObject;
    }

    public static Map<Integer, Integer> getMapObject(JSONObject daysSinceCreationToContagiousness) {
        return new Gson().fromJson(String.valueOf(daysSinceCreationToContagiousness),
            new TypeToken<HashMap<Integer, Integer>>() { }.getType());
    }

    public static JSONObject convertMapToJson(ReadableMap readableMap) throws JSONException {
        JSONObject object = new JSONObject();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
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
                    object.put(key, convertMapToJson(readableMap.getMap(key)));
                    break;
                case Array:
                    object.put(key, convertArrayToJson(readableMap.getArray(key)));
                    break;
                default:
                    break;
            }
        }
        return object;
    }

    public static JSONArray convertArrayToJson(ReadableArray readableArray) throws JSONException {
        JSONArray array = new JSONArray();
        for (int i = 0; i < readableArray.size(); i++) {
            switch (readableArray.getType(i)) {
                case Null:
                    break;
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
                    array.put(convertMapToJson(readableArray.getMap(i)));
                    break;
                case Array:
                    array.put(convertArrayToJson(readableArray.getArray(i)));
                    break;
                default:
                    break;
            }
        }
        return array;

    }

    public static List<String> convertJSONArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    public static List<File> convertJSONArrayToFileList(JSONArray jsonArray) throws JSONException {
        List<File> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new File(jsonArray.getString(i)));
        }
        return list;
    }

    public static JSONArray toJSONArray(ReadableArray readableArray) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < readableArray.size(); i++) {
            ReadableType type = readableArray.getType(i);

            switch (type) {
                case Null:
                    jsonArray.put(i, null);
                    break;
                case Boolean:
                    jsonArray.put(i, readableArray.getBoolean(i));
                    break;
                case Number:
                    jsonArray.put(i, readableArray.getDouble(i));
                    break;
                case String:
                    jsonArray.put(i, readableArray.getString(i));
                    break;
                case Map:
                    jsonArray.put(i, toJSONObject(readableArray.getMap(i)));
                    break;
                case Array:
                    jsonArray.put(i, toJSONArray(readableArray.getArray(i)));
                    break;
                default:
                    break;
            }
        }

        return jsonArray;
    }

    public static WritableMap convertJsonToMap(JSONObject jsonObject) throws JSONException {
        WritableMap map = new WritableNativeMap();

        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.putMap(key, convertJsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                map.putArray(key, convertJsonToArray((JSONArray) value));
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

    public static WritableArray convertJsonToArray(JSONArray jsonArray) throws JSONException {
        WritableArray array = new WritableNativeArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                array.pushMap(convertJsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                array.pushArray(convertJsonToArray((JSONArray) value));
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

}
