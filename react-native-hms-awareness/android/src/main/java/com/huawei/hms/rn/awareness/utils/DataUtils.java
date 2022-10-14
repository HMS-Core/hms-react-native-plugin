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

package com.huawei.hms.rn.awareness.utils;

import android.location.Location;
import android.os.Build;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.StandardCharsets;
import com.huawei.hms.kit.awareness.barrier.BarrierQueryResponse;
import com.huawei.hms.kit.awareness.barrier.BarrierStatus;
import com.huawei.hms.kit.awareness.barrier.BarrierStatusMap;
import com.huawei.hms.kit.awareness.capture.AmbientLightResponse;
import com.huawei.hms.kit.awareness.capture.ApplicationStatusResponse;
import com.huawei.hms.kit.awareness.capture.BeaconStatusResponse;
import com.huawei.hms.kit.awareness.capture.BehaviorResponse;
import com.huawei.hms.kit.awareness.capture.BluetoothStatusResponse;
import com.huawei.hms.kit.awareness.capture.CapabilityResponse;
import com.huawei.hms.kit.awareness.capture.DarkModeStatusResponse;
import com.huawei.hms.kit.awareness.capture.HeadsetStatusResponse;
import com.huawei.hms.kit.awareness.capture.LocationResponse;
import com.huawei.hms.kit.awareness.capture.ScreenStatusResponse;
import com.huawei.hms.kit.awareness.capture.TimeCategoriesResponse;
import com.huawei.hms.kit.awareness.capture.WeatherPosition;
import com.huawei.hms.kit.awareness.capture.WeatherStatusResponse;
import com.huawei.hms.kit.awareness.capture.WifiStatusResponse;
import com.huawei.hms.kit.awareness.status.AmbientLightStatus;
import com.huawei.hms.kit.awareness.status.ApplicationStatus;
import com.huawei.hms.kit.awareness.status.BeaconStatus;
import com.huawei.hms.kit.awareness.status.BehaviorStatus;
import com.huawei.hms.kit.awareness.status.BluetoothStatus;
import com.huawei.hms.kit.awareness.status.CapabilityStatus;
import com.huawei.hms.kit.awareness.status.DarkModeStatus;
import com.huawei.hms.kit.awareness.status.DetectedBehavior;
import com.huawei.hms.kit.awareness.status.HeadsetStatus;
import com.huawei.hms.kit.awareness.status.ScreenStatus;
import com.huawei.hms.kit.awareness.status.TimeCategories;
import com.huawei.hms.kit.awareness.status.WeatherStatus;
import com.huawei.hms.kit.awareness.status.WifiStatus;
import com.huawei.hms.kit.awareness.status.weather.Aqi;
import com.huawei.hms.kit.awareness.status.weather.City;
import com.huawei.hms.kit.awareness.status.weather.DailyLiveInfo;
import com.huawei.hms.kit.awareness.status.weather.DailySituation;
import com.huawei.hms.kit.awareness.status.weather.DailyWeather;
import com.huawei.hms.kit.awareness.status.weather.HourlyWeather;
import com.huawei.hms.kit.awareness.status.weather.LiveInfo;
import com.huawei.hms.kit.awareness.status.weather.Situation;
import com.huawei.hms.kit.awareness.status.weather.WeatherSituation;
import com.huawei.hms.rn.awareness.constants.LocaleConstants;
import com.huawei.hms.rn.awareness.logger.HMSLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.huawei.hms.rn.awareness.utils.DataUtils.IntType.TIME;

public class DataUtils {

    public enum IntType {
        TIME(3),
        CAPABILITY(4),
        DEFAULT(5);
        int intValue;

        IntType(int IntType) {
            this.intValue = IntType;
        }
    }

    private static final String TAG = DataUtils.class.getSimpleName();

    // For Barrier

    /**
     * Converts the WritableMap instance to BarrierStatus.
     *
     * @param obj : BarrierStatus instance.
     * @return WritableMap
     */
    public static WritableMap barrierStatusConvertToMap(BarrierStatus obj) {
        WritableMap map;
        if (obj == null) {
            return null;
        }
        try {
            JSONObject j = new JSONObject();
            int presentStatus = obj.getPresentStatus();
            j.put("barrierLabel", obj.getBarrierLabel());
            j.put("describeContents", obj.describeContents());
            j.put("lastBarrierUpdateTime", obj.getLastBarrierUpdateTime());
            j.put("lastStatus", obj.getLastStatus());
            j.put("presentStatus", presentStatus);
            j.put("presentStatusName", LocaleConstants.BARRIER_STATUS.get(presentStatus));
            map = toWritableMap(j);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            map = valueConvertToMap("error", eMessage);
        }
        return map;
    }

    /**
     * Converts the JSONObject instance to BarrierStatus.
     *
     * @param obj : BarrierStatus instance.
     * @return JSONObject
     */
    public static JSONObject barrierStatusConvertToJSONObject(BarrierStatus obj) {
        JSONObject j = new JSONObject();
        if (obj == null) {
            return null;
        }
        try {
            j.put("barrierLabel", obj.getBarrierLabel());
            j.put("describeContents", obj.describeContents());
            j.put("lastBarrierUpdateTime", obj.getLastBarrierUpdateTime());
            j.put("lastStatus", obj.getLastStatus());
            j.put("presentStatus", obj.getPresentStatus());
            j.put("presentStatusName", LocaleConstants.BARRIER_STATUS.get(obj.getPresentStatus()));
            j.put("lastStatusName", LocaleConstants.BARRIER_STATUS.get(obj.getLastStatus()));
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            Log.e("Error", eMessage);
        }
        return j;
    }

    /**
     * Converts the JSONObject instance to BarrierStatusMap.
     *
     * @param obj : BarrierStatusMap instance.
     * @return JSONObject
     */
    public static JSONObject barrierStatusMapConvertToJSONObject(BarrierStatusMap obj) {
        if (obj == null) {
            return null;
        }

        JSONObject j = new JSONObject();
        try {
            Set<String> barrierLabels = obj.getBarrierLabels();
            JSONArray labelArray = new JSONArray();

            for (String barrierLabel : barrierLabels) {
                JSONObject labelObj = new JSONObject();
                BarrierStatus barrierStatus = obj.getBarrierStatus(barrierLabel);
                JSONObject barrierStatusObject = barrierStatusConvertToJSONObject(barrierStatus);
                labelObj.put("barrierStatus", barrierStatusObject);
                labelObj.put("barrierLabel", barrierLabel);
                labelArray.put(labelObj);
            }
            j.put("barriers", labelArray);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            Log.e("Error", eMessage);
            return null;
        }
        return j;
    }

    /**
     * This method converts the BarrierQueryResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : BarrierQueryResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void barrierQueryResConvertToMap(BarrierQueryResponse obj, Promise promise) {
        try {
            if (obj == null) {
                errorMessage("barrierQueryResAllConvertToMap", "object==null", promise);
                return;
            }
            WritableMap writableMap;
            JSONObject j = new JSONObject();
            BarrierStatusMap barrierStatusMap = obj.getBarrierStatusMap();
            JSONObject barrierStatusMapObject = barrierStatusMapConvertToJSONObject(barrierStatusMap);
            j.put("barrierStatusMap", barrierStatusMapObject);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("barrierQueryResAllConvertToMap", eMessage, promise);
        }
    }

    // For Capture

    /**
     * This method converts ReadableMap to WeatherPosition.
     *
     * @param map : ReadableMap instance
     * @return Returns a WeatherPosition
     */
    public static WeatherPosition weatherPositionReqObjToWeatherPosition(ReadableMap map) {
        WeatherPosition position = new WeatherPosition();
        if (map.hasKey("city")) {
            String city = map.getString("city");
            position.setCity(city);
        }
        if (map.hasKey("country")) {
            String country = map.getString("country");
            position.setCountry(country);
        }
        if (map.hasKey("locale")) {
            String locale = map.getString("locale");
            position.setLocale(locale);
        }
        if (map.hasKey("province")) {
            String province = map.getString("province");
            position.setProvince(province);
        }
        if (map.hasKey("district")) {
            String district = map.getString("district");
            position.setDistrict(district);
        }
        return position;
    }

    /**
     * This method converts the BeaconStatusResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : BeaconStatusResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void beaconStatusResponseConvertToMap(BeaconStatusResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            JSONObject j = new JSONObject();

            BeaconStatus beaconStatus = obj.getBeaconStatus();
            List<BeaconStatus.BeaconData> beaconDataList = beaconStatus.getBeaconData();
            JSONArray beaconArray = new JSONArray();
            for (int i = 0; i < beaconDataList.size(); i++) {
                JSONObject beaconObject = new JSONObject();
                BeaconStatus.BeaconData beaconData = beaconDataList.get(i);
                String beaconId = beaconData.getBeaconId();
                byte[] contentBytes = beaconData.getContent();
                String content = new String(contentBytes, StandardCharsets.UTF_8);
                String namespace = beaconData.getNamespace();
                String type = beaconData.getType();
                beaconObject.put("beaconId", beaconId);
                beaconObject.put("content", content);
                beaconObject.put("namespace", namespace);
                beaconObject.put("type", type);
                beaconArray.put(beaconObject);
            }
            j.put("beaconDataList", beaconArray);

            writableMap = toWritableMap(j);
            promise.resolve(writableMap);

        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("beaconStatusResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the BehaviorResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : BehaviorResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void behaviorResponseConvertToMap(BehaviorResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            JSONObject j = new JSONObject();
            BehaviorStatus behaviorStatus = obj.getBehaviorStatus();
            JSONObject detectedBehavior = detectedBehaviorConvertToJSONObject(behaviorStatus.getMostLikelyBehavior());
            long elapsedRealtimeMills = behaviorStatus.getElapsedRealtimeMillis();
            long time = behaviorStatus.getTime();
            int describeContent = behaviorStatus.describeContents();

            JSONArray probableBehaviorArray = new JSONArray();
            List<DetectedBehavior> probableBehavior = behaviorStatus.getProbableBehavior();
            for (int i = 0; i < probableBehavior.size(); i++) {
                JSONObject detectorBehavior2 = detectedBehaviorConvertToJSONObject(probableBehavior.get(i));
                probableBehaviorArray.put(detectorBehavior2);
            }
            int type = detectedBehavior.getInt("type");
            j.put("behaviourConfidence", behaviorStatus.getBehaviorConfidence(type));
            j.put("mostLikelyBehavior", detectedBehavior);
            j.put("elapsedRealtimeMills", elapsedRealtimeMills);
            j.put("probableBehavior", probableBehaviorArray);
            j.put("describeContent", describeContent);
            j.put("time", time);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("behaviorResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the HeadsetStatusResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : HeadsetStatusResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void headsetStatusResponseConvertToMap(HeadsetStatusResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            HeadsetStatus headsetStatus = obj.getHeadsetStatus();
            JSONObject j = new JSONObject();
            int status = headsetStatus.getStatus();
            j.put("status", LocaleConstants.HEADSET_STATUS.get(status));
            j.put("statusCode", status);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("headsetStatusResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the TimeCategoriesResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : TimeCategoriesResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void timeCategoriesResponseConvertToMap(TimeCategoriesResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            TimeCategories timeCategories = obj.getTimeCategories();
            JSONObject j = new JSONObject();
            JSONArray timeCategoryArray = intArrayConvertToJSONArray(timeCategories.getTimeCategories(), TIME);
            j.put("timeCategories", timeCategoryArray);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("timeCategoriesResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the LocationResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : LocationResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void locationResponseConvertToMap(LocationResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            Location location = obj.getLocation();
            JSONObject j = new JSONObject();

            j.put("latitude", location.getLatitude());
            j.put("longitude", location.getLongitude());
            j.put("altitude", location.getAltitude());
            j.put("speed", location.getSpeed());
            j.put("bearing", location.getBearing());
            j.put("accuracy", location.getAccuracy());
            j.put("elapsedRealtimeNanos", location.getElapsedRealtimeNanos());
            j.put("time", location.getTime());
            j.put("fromMockProvider", location.isFromMockProvider());
            j.put("describeContents", location.describeContents());
            j.put("provider", location.getProvider());
            j.put("hasAccuracy", location.hasAccuracy());
            j.put("hasAltitude", location.hasAltitude());
            j.put("hasBearing", location.hasBearing());
            j.put("hasSpeed", location.hasSpeed());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                j.put("elapsedRealtimeUncertaintyNanos", location.getElapsedRealtimeUncertaintyNanos());
                j.put("hasElapsedRealtimeUncertaintyNanos", location.hasElapsedRealtimeUncertaintyNanos());
            }
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                j.put("verticalAccuracyMeters", location.getVerticalAccuracyMeters());
                j.put("bearingAccuracyDegrees", location.getBearingAccuracyDegrees());
                j.put("speedAccuracyMetersPerSecond", location.getSpeedAccuracyMetersPerSecond());
                j.put("hasSpeedAccuracy", location.hasSpeedAccuracy());
                j.put("hasBearingAccuracy", location.hasBearingAccuracy());
                j.put("hasVerticalAccuracy", location.hasVerticalAccuracy());
            } else {
                j.put("verticalAccuracyMeters", 0.0);
                j.put("bearingAccuracyDegrees", 0.0);
                j.put("speedAccuracyMetersPerSecond", 0.0);
            }

            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("locationResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the AmbientLightResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : AmbientLightResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void ambientLightResponseConvertToMap(AmbientLightResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            AmbientLightStatus ambientLightStatus = obj.getAmbientLightStatus();
            JSONObject j = new JSONObject();
            JSONObject ambientLightStatusObject = new JSONObject();
            ambientLightStatusObject.put("lightIntensity", ambientLightStatus.getLightIntensity());
            j.put("ambientLightStatus", ambientLightStatusObject);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("ambientLightResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the WeatherStatusResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : WeatherStatusResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void weatherStatusResponseConvertToMap(WeatherStatusResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            JSONObject j = new JSONObject();

            WeatherStatus weatherStatus = obj.getWeatherStatus();
            WeatherSituation weatherSituation = weatherStatus.getWeatherSituation();
            JSONObject weatherSituationObject = weatherSituationConvertToJSONObject(weatherSituation);

            JSONArray hourlyWeatherArray = new JSONArray();
            List<HourlyWeather> hourlyWeatherList = weatherStatus.getHourlyWeather();
            for (int i = 0; i < hourlyWeatherList.size(); i++) {
                HourlyWeather hourlyWeather = hourlyWeatherList.get(i);
                hourlyWeatherArray.put(hourlyWeatherConvertToJSONObject(hourlyWeather));
            }

            JSONArray dailyWeatherArray = new JSONArray();
            List<DailyWeather> dailyWeatherList = weatherStatus.getDailyWeather();
            for (int i = 0; i < dailyWeatherList.size(); i++) {
                DailyWeather dailyWeather = dailyWeatherList.get(i);
                dailyWeatherArray.put(dailyWeatherConvertToJSONObject(dailyWeather));
            }

            JSONArray liveInfoArray = new JSONArray();
            List<LiveInfo> liveInfoList = weatherStatus.getLiveInfo();
            for (int i = 0; i < liveInfoList.size(); i++) {
                LiveInfo liveInfo = liveInfoList.get(i);
                liveInfoArray.put(liveInfoConvertToJSONObject(liveInfo));
            }

            j.put("aqi", aqiConvertToJSONObject(weatherStatus.getAqi()));
            j.put("liveInfoList", liveInfoArray);
            j.put("dailyWeatherList", dailyWeatherArray);
            j.put("hourlyWeatherList", hourlyWeatherArray);
            j.put("weatherSituation", weatherSituationObject);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("weatherStatusResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the BluetoothStatusResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : BluetoothStatusResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void bluetoothStatusResponseConvertToMap(BluetoothStatusResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            JSONObject j = new JSONObject();
            BluetoothStatus bluetoothStatus = obj.getBluetoothStatus();
            int status = bluetoothStatus.getStatus();
            JSONObject bluetoothStatusObject = new JSONObject();
            bluetoothStatusObject.put("status", status);
            bluetoothStatusObject.put("statusName", LocaleConstants.BLUETOOTH_STATUS.get(status));
            j.put("bluetoothStatus", bluetoothStatusObject);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("bluetoothStatusResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the CapabilityResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : CapabilityResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void capabilityResponseConvertToMap(CapabilityResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            JSONObject j = new JSONObject();
            CapabilityStatus capabilityStatus = obj.getCapabilityStatus();
            int[] capabilitiesArray = capabilityStatus.getCapabilities();
            JSONArray capabilityStatusJSONArray = intArrayConvertToJSONArray(capabilitiesArray, IntType.CAPABILITY);
            j.put("capabilities", capabilityStatusJSONArray);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("capabilityResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the ScreenStatusResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : ScreenStatusResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void screenStatusResponseConvertToMap(ScreenStatusResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            JSONObject j = new JSONObject();
            JSONObject screenStatusObject = screenStatusConvertToJSONObject(obj.getScreenStatus());
            j.put("screenStatus", screenStatusObject);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("screenStatusResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the WifiStatusResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : WifiStatusResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void wifiStatusResponseConvertToMap(WifiStatusResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            JSONObject j = new JSONObject();
            JSONObject wifistatusObject = wifiStatusConvertToJSONObject(obj.getWifiStatus());
            j.put("wifiStatus", wifistatusObject);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("wifiStatusResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the ApplicationStatusResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : ApplicationStatusResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void applicationStatusResponseConvertToMap(ApplicationStatusResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            JSONObject j = new JSONObject();
            ApplicationStatus applicationStatus = obj.getApplicationStatus();
            JSONObject aaplicationStatusObject = applicationStatusConvertToJSONObject(applicationStatus);
            j.put("applicationStatus", aaplicationStatusObject);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("applicationStatusResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts the DarkModeStatusResponse type object to a WritableMap and returns to the RN side.
     *
     * @param obj     : DarkModeStatusResponse instance
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static void darkModeStatusResponseConvertToMap(DarkModeStatusResponse obj, Promise promise) {
        try {
            WritableMap writableMap;
            JSONObject j = new JSONObject();
            JSONObject darkModestatusObject = darkModeStatusConvertToJSONObject(obj.getDarkModeStatus());
            j.put("darkModeStatus", darkModestatusObject);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage("darkModeStatusResponseConvertToMap", eMessage, promise);
        }
    }

    /**
     * This method converts an instance of generic type object
     * and String type to WritableMap and returns a WritableMap type response to the RN side.
     * There is a variable in WritableMap with key=key and value=instance.
     *
     * @param instance : Generic type
     * @param key      : String
     * @param method   : String
     *                 When returning information to the RN side, it provides the method
     *                 from which this information is returned.
     * @param promise  :        Returns a WritableMap type promise to the RN side.
     */
    public static <T> void valueConvertToMap(String key, T instance, String method, Promise promise) {
        try {
            WritableMap writableMap;
            JSONObject j = new JSONObject();
            j.put(key, instance);
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            String eMessage = e.getMessage() != null ? e.getMessage() : "error";
            errorMessage(method, eMessage, promise);
        }
    }

    /**
     * Converts an instance of a generic type object to WritableMap.
     * There is a variable in WritableMap with key=key and value=instance.
     *
     * @param instance : Generic type
     * @param key      : String
     * @return a WritableMap
     */
    public static <T> WritableMap valueConvertToMap(String key, T instance) {
        WritableMap writableMap = null;
        try {
            JSONObject j = new JSONObject();
            j.put(key, instance);
            writableMap = toWritableMap(j);
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return writableMap;
    }

    /**
     * Converts the DarkModeStatus instance to JSONObject.
     *
     * @param obj : DarkModeStatus instance.
     * @return JSONObject
     */
    private static JSONObject darkModeStatusConvertToJSONObject(DarkModeStatus obj) {
        if (obj == null){
            return null;
        }
        JSONObject j = new JSONObject();
        try {
            boolean isDarkModeOn = obj.isDarkModeOn();
            j.put("isDarkModeOn", isDarkModeOn);
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return j;
    }

    /**
     * Converts the ApplicationStatus instance to JSONObject.
     *
     * @param obj : ApplicationStatus instance.
     * @return JSONObject
     */
    private static JSONObject applicationStatusConvertToJSONObject(ApplicationStatus obj) {
        if (obj == null){
            return null;
        }
        JSONObject j = new JSONObject();
        try {
            int status = obj.getStatus();
            j.put("status", status);
            j.put("statusName", LocaleConstants.APPLICATION_STATUS.get(status));
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return j;
    }

    /**
     * Converts the WifiStatus instance to JSONObject.
     *
     * @param obj : WifiStatus instance.
     * @return JSONObject
     */
    private static JSONObject wifiStatusConvertToJSONObject(WifiStatus obj) {
        if (obj == null){
            return null;
        }
        JSONObject j = new JSONObject();
        try {
            int status = obj.getStatus();
            j.put("bssid", obj.getBssid());
            j.put("ssid", obj.getSsid());
            j.put("status", status);
            j.put("statusName", LocaleConstants.WIFI_STATUS.get(status));
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return j;
    }

    /**
     * Converts the ScreenStatus instance to JSONObject.
     *
     * @param obj : ScreenStatus instance.
     * @return JSONObject
     */
    private static JSONObject screenStatusConvertToJSONObject(ScreenStatus obj) {
        if (obj == null){
            return null;
        }
        JSONObject j = new JSONObject();
        try {
            int status = obj.getStatus();
            j.put("status", status);
            j.put("statusName", LocaleConstants.SCREEN_STATUS.get(status));
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return j;
    }

    /**
     * Converts the DetectedBehavior instance to JSONObject.
     *
     * @param obj : DetectedBehavior instance.
     * @return JSONObject
     */
    private static JSONObject detectedBehaviorConvertToJSONObject(DetectedBehavior obj) {
        if (obj == null){
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("describeContents", obj.describeContents());
            jsonObject.put("confidence", obj.getConfidence());
            jsonObject.put("type", obj.getType());
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return jsonObject;
    }

    /**
     * Converts the WeatherSituation instance to JSONObject.
     *
     * @param obj : WeatherSituation instance.
     * @return JSONObject
     */
    private static JSONObject weatherSituationConvertToJSONObject(WeatherSituation obj) {
        if (obj == null){
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            Situation situation = obj.getSituation();
            City city = obj.getCity();
            jsonObject.put("city", cityConvertToJSONObject(city));
            jsonObject.put("situation", situationConvertToJSONObject(situation));
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return jsonObject;
    }

    /**
     * Converts the City instance to JSONObject.
     *
     * @param obj : City instance.
     * @return JSONObject
     */
    private static JSONObject cityConvertToJSONObject(City obj) {
        if (obj == null){
            return null;
        }
        JSONObject j = new JSONObject();
        try {
            j.put("describeContents", obj.describeContents());
            j.put("cityCode", obj.getCityCode());
            j.put("provinceName", obj.getProvinceName());
            j.put("timeZone", obj.getTimeZone());
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return j;
    }

    /**
     * Converts the Situation instance to JSONObject.
     *
     * @param obj : Situation instance.
     * @return JSONObject
     */
    private static JSONObject situationConvertToJSONObject(Situation obj) {
        if (obj == null){
            return null;
        }
        JSONObject j = new JSONObject();
        try {
            j.put("describeContents", obj.describeContents());
            j.put("cnWeatherId", obj.getCnWeatherId());
            j.put("cnWeather", LocaleConstants.CN_WEATHER_ID.get(obj.getCnWeatherId()));
            j.put("weatherId", obj.getWeatherId());
            j.put("weather", LocaleConstants.WEATHER_ID.get(obj.getWeatherId()));
            j.put("humidity", obj.getHumidity());
            j.put("pressure", obj.getPressure());
            j.put("realFeelC", obj.getRealFeelC());
            j.put("realFeelF", obj.getRealFeelF());
            j.put("temperatureC", obj.getTemperatureC());
            j.put("temperatureF", obj.getTemperatureF());
            j.put("updateTime", obj.getUpdateTime());
            j.put("uvIndex", obj.getUvIndex());
            j.put("windDir", obj.getWindDir());
            j.put("windSpeed", obj.getWindSpeed());
            j.put("windLevel", obj.getWindLevel());
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return j;
    }

    /**
     * Converts the Aqi instance to JSONObject.
     *
     * @param obj : Aqi instance.
     * @return JSONObject
     */
    private static JSONObject aqiConvertToJSONObject(Aqi obj) {
        if (obj == null){
            return null;
        }
        JSONObject object = new JSONObject();
        try {
            object.put("aqiValue", obj.getAqiValue());
            object.put("describeContents", obj.describeContents());
            object.put("co", obj.getCo());
            object.put("no2", obj.getNo2());
            object.put("getO3", obj.getO3());
            object.put("pm10", obj.getPm10());
            object.put("pm25", obj.getPm25());
            object.put("so2", obj.getSo2());
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return object;
    }

    /**
     * Converts the DailyWeather instance to JSONObject.
     *
     * @param obj : DailyWeather instance.
     * @return JSONObject
     */
    private static JSONObject dailyWeatherConvertToJSONObject(DailyWeather obj) {
        if (obj == null){
            return null;
        }
        JSONObject object = new JSONObject();
        try {
            object.put("timeStamp", obj.getDateTimeStamp());
            object.put("maxTempC", obj.getMaxTempC());
            object.put("maxTempF", obj.getMaxTempF());
            object.put("minTempC", obj.getMinTempC());
            object.put("minTempF", obj.getMinTempF());
            object.put("moonphase", obj.getMoonphase());
            object.put("moonRise", obj.getMoonRise());
            object.put("moonSet", obj.getMoonSet());
            object.put("sunRise", obj.getSunRise());
            object.put("sunSet", obj.getSunSet());
            object.put("aqiValue", obj.getAqiValue());
            object.put("describeContents", obj.describeContents());
            object.put("situationDay", dailySituationConvertToJSONObject(obj.getSituationDay()));
            object.put("situationNight", dailySituationConvertToJSONObject(obj.getSituationNight()));
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return object;
    }

    /**
     * Converts the DailySituation instance to JSONObject.
     *
     * @param obj : DailySituation instance.
     * @return JSONObject
     */
    private static JSONObject dailySituationConvertToJSONObject(DailySituation obj) {
        if (obj == null){
            return null;
        }
        JSONObject j = new JSONObject();
        try {
            j.put("describeContents", obj.describeContents());
            j.put("cnWeatherId", obj.getCnWeatherId());
            j.put("cnWeather", LocaleConstants.CN_WEATHER_ID.get(obj.getCnWeatherId()));
            j.put("weatherId", obj.getWeatherId());
            j.put("weather", LocaleConstants.WEATHER_ID.get(obj.getWeatherId()));
            j.put("windDir", obj.getWindDir());
            j.put("windLevel", obj.getWindLevel());
            j.put("windSpeed", obj.getWindSpeed());
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return j;
    }

    /**
     * Converts the HourlyWeather instance to JSONObject.
     *
     * @param obj : HourlyWeather instance.
     * @return JSONObject
     */
    private static JSONObject hourlyWeatherConvertToJSONObject(HourlyWeather obj) {
        if (obj == null){
            return null;
        }
        JSONObject j = new JSONObject();
        try {
            j.put("timeStamp", obj.getDateTimeStamp());
            j.put("cnWeatherId", obj.getCnWeatherId());
            j.put("cnWeather", LocaleConstants.CN_WEATHER_ID.get(obj.getCnWeatherId()));
            j.put("weatherId", obj.getWeatherId());
            j.put("weather", LocaleConstants.WEATHER_ID.get(obj.getWeatherId()));
            j.put("rainprobability", obj.getRainprobability());
            j.put("tempC", obj.getTempC());
            j.put("tempF", obj.getTempF());
            j.put("describeContents", obj.describeContents());
            j.put("isDayNight", obj.isDayNight());
            j.put("timeStamp", obj.getDateTimeStamp());
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return j;
    }

    /**
     * Converts the LiveInfo instance to JSONObject.
     *
     * @param obj : LiveInfo instance.
     * @return JSONObject
     */
    private static JSONObject liveInfoConvertToJSONObject(LiveInfo obj) {
        if (obj == null){
            return null;
        }
        JSONObject j = new JSONObject();
        try {
            JSONArray dailyLiveInfoArray = new JSONArray();
            List<DailyLiveInfo> dailyLiveInfoList = obj.getLevelList();
            for (int i = 0; i < dailyLiveInfoList.size(); i++) {
                DailyLiveInfo dailyLiveInfo = dailyLiveInfoList.get(i);
                dailyLiveInfoArray.put(dailyLiveInfoConvertToJSONObject(dailyLiveInfo));
            }
            j.put("dailyLiveInfoList", dailyLiveInfoArray);
            j.put("code", obj.getCode());
            j.put("describeContents", obj.describeContents());
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return j;
    }

    /**
     * Converts the DailyLiveInfo instance to JSONObject.
     *
     * @param obj : DailyLiveInfo instance.
     * @return JSONObject
     */
    private static JSONObject dailyLiveInfoConvertToJSONObject(DailyLiveInfo obj) {
        if (obj == null){
            return null;
        }
        JSONObject j = new JSONObject();
        try {
            j.put("timeStamp", obj.getDateTimeStamp());
            j.put("level", obj.getLevel());
            j.put("describeContents", obj.describeContents());
        } catch (JSONException | IllegalArgumentException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            return null;
        }
        return j;
    }

    /**
     * Converts the int [] instance to JSONArray by type.
     *
     * @param intArray : ReadableArray instance.
     * @param type     : enum
     * @return JSONArray
     */
    private static JSONArray intArrayConvertToJSONArray(int[] intArray, IntType type) {
        if (intArray == null){
            return null;
        }
        JSONArray timeArray = new JSONArray();
        for (int value : intArray) {
            try {
                JSONObject j = new JSONObject();
                switch (type) {
                    case TIME: {
                        j.put("name", LocaleConstants.TIME_BARRIER.get(value));
                        break;
                    }
                    case CAPABILITY: {
                        j.put("name", LocaleConstants.CAPABILITY_STATUS.get(value));
                        break;
                    }
                    case DEFAULT: {
                        break;
                    }
                }
                j.put("value", value);
                timeArray.put(j);
            } catch (JSONException | IllegalArgumentException e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                return null;
            }
        }
        return timeArray;
    }

    /**
     * Converts a ReadableArray instance to a int[].
     *
     * @param readableArray : ReadableArray instance.
     * @return int[]
     */
    public static int[] readableArrayConvertToIntArray(ReadableArray readableArray) {
        if (readableArray == null) {
            return new int[0];
        }

        int[] intArray = new int[readableArray.size()];
        for (int i = 0; i < readableArray.size(); i++) {
            int item = readableArray.getInt(i);
            intArray[i] = item;
        }
        return intArray;
    }

    /**
     * Converts a JSONObject instance to a WritableMap.
     *
     * @param j: JSONObject instance.
     * @return WritableMap
     */
    public static WritableMap toWritableMap(final JSONObject j) {
        if (j == null){
            return null;
        }
        WritableMap map = new WritableNativeMap();
        Iterator<String> iterator = j.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                Object value = j.get(key);
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
            } catch (JSONException | IllegalArgumentException e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                return null;
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
        if (jsonArray == null){
            return null;
        }
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
            } catch (JSONException | IllegalArgumentException e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                return null;
            }
        }
        return array;
    }

    /**
     * This method converts an instance of generic type object and
     * returns a WritableMap type errorObject for the RN side.
     * Calls sendSingleEvent for HMSLogger.
     *
     * @param method  : method name
     * @param tag     : class name
     * @param error   :  generic type
     * @param context : ReactContext instance.
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static <T> void errorMessage(ReactContext context, String method, String tag, T error, Promise promise) {
        Log.i(tag, method + error.toString());
        WritableMap writableMap;
        JSONObject j = new JSONObject();
        try {
            j.put("tag", tag);
            j.put("method", method);
            if (error instanceof Exception) {
                if (((Exception) error).getMessage() != null
                        && Objects.requireNonNull(((Exception) error).getMessage()).isEmpty()) {
                    String message = ((Exception) error).getMessage();
                    j.put("message", message);
                    if (context != null) {
                        HMSLogger.getInstance(context).sendSingleEvent(method, message);
                    }
                } else {
                    if (context != null) {
                        HMSLogger.getInstance(context).sendSingleEvent(method, "unknown exception");
                    }
                    j.put("message", "unknown exception");
                }

            } else if (error instanceof String) {
                HMSLogger.getInstance(context).sendSingleEvent(method, error + "");
                j.put("message", error);
            } else {
                HMSLogger.getInstance(context).sendSingleEvent(method, "error");
                j.put("message", "error");
            }
            writableMap = toWritableMap(j);
            if (promise != null) {
                promise.resolve(writableMap);
            }
        } catch (JSONException | IllegalArgumentException e) {
            if (promise != null) {
                promise.resolve(TAG + "-" + e.getMessage());
            }
        }
    }

    /**
     * This method converts an instance of generic type object and
     * returns a WritableMap type errorObject for the RN side.
     *
     * @param method  : method name
     * @param error   :  generic type
     * @param promise : Returns a WritableMap type promise to the RN side.
     */
    public static <T> void errorMessage(String method, T error, Promise promise) {
        Log.i(TAG, method + error.toString());
        WritableMap writableMap;
        JSONObject j = new JSONObject();
        try {
            j.put("method", method);
            if (error instanceof Exception) {
                String message = ((Exception) error).getMessage();
                j.put("message", message);
            } else if (error instanceof String) {
                j.put("message", error);
            } else {
                j.put("message", "error");
            }
            writableMap = toWritableMap(j);
            promise.resolve(writableMap);
        } catch (JSONException | IllegalArgumentException e) {
            promise.resolve(TAG + "-" + e.getMessage());
        }
    }
}