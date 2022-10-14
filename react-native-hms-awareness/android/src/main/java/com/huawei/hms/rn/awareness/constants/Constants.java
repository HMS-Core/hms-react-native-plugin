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

package com.huawei.hms.rn.awareness.constants;

import com.facebook.common.internal.ImmutableMap;
import com.huawei.hms.kit.awareness.AwarenessStatusCodes;
import com.huawei.hms.kit.awareness.barrier.BarrierStatus;
import com.huawei.hms.kit.awareness.barrier.BehaviorBarrier;
import com.huawei.hms.kit.awareness.barrier.TimeBarrier;
import com.huawei.hms.kit.awareness.status.ApplicationStatus;
import com.huawei.hms.kit.awareness.status.BluetoothStatus;
import com.huawei.hms.kit.awareness.status.CapabilityStatus;
import com.huawei.hms.kit.awareness.status.DarkModeStatus;
import com.huawei.hms.kit.awareness.status.HeadsetStatus;
import com.huawei.hms.kit.awareness.status.ScreenStatus;
import com.huawei.hms.kit.awareness.status.WeatherStatus;
import com.huawei.hms.kit.awareness.status.WifiStatus;
import com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId;
import com.huawei.hms.kit.awareness.status.weather.constant.WeatherId;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    /*
     *All constants are expose through the getAllConstants() method.
     */
    private static final Map<String, Object> ALL_CONSTANTS = new HashMap<String, Object>() {
        {
            put("AwarenessSC_SUCCESS", AwarenessStatusCodes.AWARENESS_SUCCESS_CODE);
            put("AwarenessSC_UNKNOWN_ERROR", AwarenessStatusCodes.AWARENESS_UNKNOWN_ERROR_CODE);
            put("AwarenessSC_BINDER_ERROR", AwarenessStatusCodes.AWARENESS_BINDER_ERROR_CODE);
            put("AwarenessSC_REGISTER_FAILED", AwarenessStatusCodes.AWARENESS_REGISTER_FAILED_CODE);
            put("AwarenessSC_TIMEOUT", AwarenessStatusCodes.AWARENESS_TIMEOUT_CODE);
            put("AwarenessSC_COUNT_LIMIT", AwarenessStatusCodes.AWARENESS_COUNT_LIMIT_CODE);
            put("AwarenessSC_FREQUENCY_LIMIT", AwarenessStatusCodes.AWARENESS_FREQUENCY_LIMIT_CODE);
            put("AwarenessSC_BARRIER_PARAMETER_ERROR", AwarenessStatusCodes.AWARENESS_BARRIER_PARAMETER_ERROR_CODE);
            put("AwarenessSC_REQUEST_ERROR", AwarenessStatusCodes.AWARENESS_REQUEST_ERROR_CODE);
            put("AwarenessSC_AGC_FILE_ERROR", AwarenessStatusCodes.AWARENESS_AGC_FILE_ERROR);
            put("AwarenessSC_RESULT_INVALID_ERROR", AwarenessStatusCodes.AWARENESS_RESULT_INVALID_ERROR);
            put("AwarenessSC_REMOTE_EXCEPTION_ERROR", AwarenessStatusCodes.AWARENESS_REMOTE_EXCEPTION_ERROR);
            put("AwarenessSC_WAIT_CALLBACK", AwarenessStatusCodes.AWARENESS_WAIT_CALLBACK_CODE);
            put("AwarenessSC_INTERFACE_INVALID", AwarenessStatusCodes.AWARENESS_INTERFACE_INVALID);
            put("AwarenessSC_NO_ENOUGH_RESOURCE", AwarenessStatusCodes.AWARENESS_NO_ENOUGH_RESOURCE);
            put("AwarenessSC_SDK_VERSION_ERROR", AwarenessStatusCodes.AWARENESS_SDK_VERSION_ERROR);
            put("AwarenessSC_LOCATION_PERMISSION", AwarenessStatusCodes.AWARENESS_LOCATION_PERMISSION_CODE);
            put("AwarenessSC_LOCATION_CORE_PERMISSION", AwarenessStatusCodes.AWARENESS_LOCATION_CORE_PERMISSION_CODE);
            put("AwarenessSC_BEHAVIOR_PERMISSION", AwarenessStatusCodes.AWARENESS_BEHAVIOR_PERMISSION_CODE);
            put("AwarenessSC_BLUETOOTH_PERMISSION", AwarenessStatusCodes.AWARENESS_BLUETOOTH_PERMISSION_CODE);
            put("AwarenessSC_WIFI_PERMISSION_CODE", AwarenessStatusCodes.AWARENESS_WIFI_PERMISSION_CODE);
            put("AwarenessSC_WIFI_CORE_PERMISSION", AwarenessStatusCodes.AWARENESS_WIFI_CORE_PERMISSION_CODE);
            put("AwarenessSC_LOCATION_NOCACHE", AwarenessStatusCodes.AWARENESS_LOCATION_NOCACHE_CODE);
            put("AwarenessSC_LOCATION_NOT_AVAILABLE", AwarenessStatusCodes.AWARENESS_LOCATION_NOT_AVAILABLE_CODE);
            put("AwarenessSC_BEACON_NOT_AVAILABLE", AwarenessStatusCodes.AWARENESS_BEACON_NOT_AVAILABLE_CODE);
            put("AwarenessSC_BLUETOOTH_NOT_AVAILABLE", AwarenessStatusCodes.AWARENESS_BLUETOOTH_NOT_AVAILABLE_CODE);
            put("AwarenessSC_APPLICATION_NOT_HUAWEI_PHONE", AwarenessStatusCodes.AWARENESS_APPLICATION_NOT_HUAWEI_PHONE);
            put("AwarenessSC_UPDATE_KIT", AwarenessStatusCodes.UPDATE_KIT_CODE);
            put("AwarenessSC_UPDATE_HMS", AwarenessStatusCodes.UPDATE_HMS_CODE);

            put("BehaviorBarrier_BEHAVIOR_IN_VEHICLE", BehaviorBarrier.BEHAVIOR_IN_VEHICLE);
            put("BehaviorBarrier_BEHAVIOR_ON_BICYCLE", BehaviorBarrier.BEHAVIOR_ON_BICYCLE);
            put("BehaviorBarrier_BEHAVIOR_ON_FOOT", BehaviorBarrier.BEHAVIOR_ON_FOOT);
            put("BehaviorBarrier_BEHAVIOR_STILL", BehaviorBarrier.BEHAVIOR_STILL);
            put("BehaviorBarrier_BEHAVIOR_UNKNOWN", BehaviorBarrier.BEHAVIOR_UNKNOWN);
            put("BehaviorBarrier_BEHAVIOR_WALKING", BehaviorBarrier.BEHAVIOR_WALKING);
            put("BehaviorBarrier_BEHAVIOR_RUNNING", BehaviorBarrier.BEHAVIOR_RUNNING);

            put("TimeBarrier_TIME_CATEGORY_WEEKDAY", TimeBarrier.TIME_CATEGORY_WEEKDAY);
            put("TimeBarrier_TIME_CATEGORY_WEEKEND", TimeBarrier.TIME_CATEGORY_WEEKEND);
            put("TimeBarrier_TIME_CATEGORY_HOLIDAY", TimeBarrier.TIME_CATEGORY_HOLIDAY);
            put("TimeBarrier_TIME_CATEGORY_NOT_HOLIDAY", TimeBarrier.TIME_CATEGORY_NOT_HOLIDAY);
            put("TimeBarrier_TIME_CATEGORY_MORNING", TimeBarrier.TIME_CATEGORY_MORNING);
            put("TimeBarrier_TIME_CATEGORY_AFTERNOON", TimeBarrier.TIME_CATEGORY_AFTERNOON);
            put("TimeBarrier_TIME_CATEGORY_EVENING", TimeBarrier.TIME_CATEGORY_EVENING);
            put("TimeBarrier_TIME_CATEGORY_NIGHT", TimeBarrier.TIME_CATEGORY_NIGHT);
            put("TimeBarrier_FRIDAY_CODE", TimeBarrier.FRIDAY_CODE);
            put("TimeBarrier_MONDAY_CODE", TimeBarrier.MONDAY_CODE);
            put("TimeBarrier_SATURDAY_CODE", TimeBarrier.SATURDAY_CODE);
            put("TimeBarrier_SUNDAY_CODE", TimeBarrier.SUNDAY_CODE);
            put("TimeBarrier_THURSDAY_CODE", TimeBarrier.THURSDAY_CODE);
            put("TimeBarrier_TUESDAY_CODE", TimeBarrier.TUESDAY_CODE);
            put("TimeBarrier_WEDNESDAY_CODE", TimeBarrier.WEDNESDAY_CODE);
            put("TimeBarrier_SUNRISE_CODE", TimeBarrier.SUNRISE_CODE);
            put("TimeBarrier_SUNSET_CODE", TimeBarrier.SUNSET_CODE);

            put("CapabilityStatus_HEADSET", CapabilityStatus.AWA_CAP_CODE_HEADSET);
            put("CapabilityStatus_LOCATION_CAPTURE", CapabilityStatus.AWA_CAP_CODE_LOCATION_CAPTURE);
            put("CapabilityStatus_LOCATION_NORMAL_BARRIER", CapabilityStatus.AWA_CAP_CODE_LOCATION_NORMAL_BARRIER);
            put("CapabilityStatus_LOCATION_LOW_POWER_BARRIER", CapabilityStatus.AWA_CAP_CODE_LOCATION_LOW_POWER_BARRIER);
            put("CapabilityStatus_BEHAVIOR", CapabilityStatus.AWA_CAP_CODE_BEHAVIOR);
            put("CapabilityStatus_TIME", CapabilityStatus.AWA_CAP_CODE_TIME);
            put("CapabilityStatus_AMBIENT_LIGHT", CapabilityStatus.AWA_CAP_CODE_AMBIENT_LIGHT);
            put("CapabilityStatus_WEATHER", CapabilityStatus.AWA_CAP_CODE_WEATHER);
            put("CapabilityStatus_BEACON", CapabilityStatus.AWA_CAP_CODE_WEATHER);
            put("CapabilityStatus_INCAR_BLUETOOTH", CapabilityStatus.AWA_CAP_CODE_INCAR_BLUETOOTH);
            put("CapabilityStatus_SCREEN", CapabilityStatus.AWA_CAP_CODE_SCREEN);
            put("CapabilityStatus_WIFI", CapabilityStatus.AWA_CAP_CODE_WIFI);
            put("CapabilityStatus_APPLICATION", CapabilityStatus.AWA_CAP_CODE_APPLICATION);
            put("CapabilityStatus_DARK_MODE", CapabilityStatus.AWA_CAP_CODE_DARK_MODE);

            put("WeatherId_SUNNY", WeatherId.SUNNY);
            put("WeatherId_MOSTLY_SUNNY", WeatherId.MOSTLY_SUNNY);
            put("WeatherId_PARTLY_SUNNY", WeatherId.PARTLY_SUNNY);
            put("WeatherId_INTERMITTENT_CLOUDS", WeatherId.INTERMITTENT_CLOUDS);
            put("WeatherId_HAZY_SUNSHINE", WeatherId.HAZY_SUNSHINE);
            put("WeatherId_MOSTLY_CLOUDY", WeatherId.MOSTLY_CLOUDY);
            put("WeatherId_CLOUDY", WeatherId.CLOUDY);
            put("WeatherId_DREARY", WeatherId.DREARY);
            put("WeatherId_FOG", WeatherId.FOG);
            put("WeatherId_SHOWERS", WeatherId.SHOWERS);
            put("WeatherId_MOSTLY_CLOUDY_WITH_SHOWERS", WeatherId.MOSTLY_CLOUDY_WITH_SHOWERS);
            put("WeatherId_PARTLY_SUNNY_WITH_SHOWERS", WeatherId.PARTLY_SUNNY_WITH_SHOWERS);
            put("WeatherId_T_STORMS", WeatherId.T_STORMS);
            put("WeatherId_MOSTLY_CLOUDY_WITH_T_STORMS", WeatherId.MOSTLY_CLOUDY_WITH_T_STORMS);
            put("WeatherId_PARTLY_SUNNY_WITH_T_STORMS", WeatherId.PARTLY_SUNNY_WITH_T_STORMS);
            put("WeatherId_RAIN", WeatherId.RAIN);
            put("WeatherId_FLURRIES", WeatherId.FLURRIES);
            put("WeatherId_MOSTLY_CLOUDY_WITH_FLURRIES", WeatherId.MOSTLY_CLOUDY_WITH_FLURRIES);
            put("WeatherId_PARTLY_SUNNY_WITH_FLURRIES", WeatherId.PARTLY_SUNNY_WITH_FLURRIES);
            put("WeatherId_SNOW", WeatherId.SNOW);
            put("WeatherId_MOSTLY_CLOUDY_WITH_SNOW", WeatherId.MOSTLY_CLOUDY_WITH_SNOW);
            put("WeatherId_ICE", WeatherId.ICE);
            put("WeatherId_SLEET", WeatherId.SLEET);
            put("WeatherId_FREEZING_RAIN", WeatherId.FREEZING_RAIN);
            put("WeatherId_RAIN_AND_SNOW", WeatherId.RAIN_AND_SNOW);
            put("WeatherId_HOT", WeatherId.HOT);
            put("WeatherId_COLD", WeatherId.COLD);
            put("WeatherId_WINDY", WeatherId.WINDY);
            put("WeatherId_CLEAR", WeatherId.CLEAR);
            put("WeatherId_PARTLY_CLOUDY", WeatherId.PARTLY_CLOUDY);
            put("WeatherId_INTERMITTENT_CLOUDS_2", WeatherId.INTERMITTENT_CLOUDS_2);
            put("WeatherId_HAZY_MOONLIGHT", WeatherId.HAZY_MOONLIGHT);
            put("WeatherId_MOSTLY_CLOUDY_2", WeatherId.MOSTLY_CLOUDY_2);
            put("WeatherId_PARTLY_CLOUDY_WITH_SHOWERS", WeatherId.PARTLY_CLOUDY_WITH_SHOWERS);
            put("WeatherId_MOSTLY_CLOUDY_WITH_SHOWERS_2", WeatherId.MOSTLY_CLOUDY_WITH_SHOWERS_2);
            put("WeatherId_PARTLY_CLOUDY_WITH_T_STORMS", WeatherId.PARTLY_CLOUDY_WITH_T_STORMS);
            put("WeatherId_MOSTLY_CLOUDY_WITH_T_STORMS_2", WeatherId.MOSTLY_CLOUDY_WITH_T_STORMS_2);
            put("WeatherId_MOSTLY_CLOUDY_WITH_FLURRIES_2", WeatherId.MOSTLY_CLOUDY_WITH_FLURRIES_2);
            put("WeatherId_MOSTLY_CLOUDY_WITH_SNOW_2", WeatherId.MOSTLY_CLOUDY_WITH_SNOW_2);

            put("CNWeatherId_INVALID_VALUE", CNWeatherId.INVALID_VALUE);
            put("CNWeatherId_SUNNY", CNWeatherId.SUNNY);
            put("CNWeatherId_CLOUDY", CNWeatherId.CLOUDY);
            put("CNWeatherId_OVERCAST", CNWeatherId.OVERCAST);
            put("CNWeatherId_SHOWER", CNWeatherId.SHOWER);
            put("CNWeatherId_THUNDERSHOWER", CNWeatherId.THUNDERSHOWER);
            put("CNWeatherId_THUNDERSHOWER_WITH_HAIL", CNWeatherId.THUNDERSHOWER_WITH_HAIL);
            put("CNWeatherId_SLEET", CNWeatherId.SLEET);
            put("CNWeatherId_LIGHT_RAIN", CNWeatherId.LIGHT_RAIN);
            put("CNWeatherId_MODERATE_RAIN", CNWeatherId.MODERATE_RAIN);
            put("CNWeatherId_HEAVY_RAIN", CNWeatherId.HEAVY_RAIN);
            put("CNWeatherId_STORM", CNWeatherId.STORM);
            put("CNWeatherId_HEAVY_STORM", CNWeatherId.HEAVY_STORM);
            put("CNWeatherId_SEVERE_STORM", CNWeatherId.SEVERE_STORM);
            put("CNWeatherId_SNOW_FLURRY", CNWeatherId.SNOW_FLURRY);
            put("CNWeatherId_LIGHT_SNOW", CNWeatherId.LIGHT_SNOW);
            put("CNWeatherId_MODERATE_SNOW", CNWeatherId.MODERATE_SNOW);
            put("CNWeatherId_HEAVY_SNOW", CNWeatherId.HEAVY_SNOW);
            put("CNWeatherId_SNOWSTORM", CNWeatherId.SNOWSTORM);
            put("CNWeatherId_FOGGY", CNWeatherId.FOGGY);
            put("CNWeatherId_ICE_RAIN", CNWeatherId.ICE_RAIN);
            put("CNWeatherId_DUSTSTORM", CNWeatherId.DUSTSTORM);
            put("CNWeatherId_LIGHT_TO_MODERATE_RAIN", CNWeatherId.LIGHT_TO_MODERATE_RAIN);
            put("CNWeatherId_MODERATE_TO_HEAVY_RAIN", CNWeatherId.MODERATE_TO_HEAVY_RAIN);
            put("CNWeatherId_HEAVY_RAIN_TO_STORM", CNWeatherId.HEAVY_RAIN_TO_STORM);
            put("CNWeatherId_STORM_TO_HEAVY_STORM", CNWeatherId.STORM_TO_HEAVY_STORM);
            put("CNWeatherId_HEAVY_TO_SEVERE_STORM", CNWeatherId.HEAVY_TO_SEVERE_STORM);
            put("CNWeatherId_LIGHT_TO_MODERATE_SNOW", CNWeatherId.LIGHT_TO_MODERATE_SNOW);
            put("CNWeatherId_MODERATE_TO_HEAVY_SNOW", CNWeatherId.MODERATE_TO_HEAVY_SNOW);
            put("CNWeatherId_HEAVY_SNOW_TO_SNOWSTORM", CNWeatherId.HEAVY_SNOW_TO_SNOWSTORM);
            put("CNWeatherId_DUST", CNWeatherId.DUST);
            put("CNWeatherId_SAND", CNWeatherId.SAND);
            put("CNWeatherId_SANDSTORM", CNWeatherId.SANDSTORM);
            put("CNWeatherId_DENSE_FOGGY", CNWeatherId.DENSE_FOGGY);
            put("CNWeatherId_SNOW", CNWeatherId.SNOW);
            put("CNWeatherId_MODERATE_FOGGY", CNWeatherId.MODERATE_FOGGY);
            put("CNWeatherId_HAZE", CNWeatherId.HAZE);
            put("CNWeatherId_MODERATE_HAZE", CNWeatherId.MODERATE_HAZE);
            put("CNWeatherId_HEAVY_HAZE", CNWeatherId.HEAVY_HAZE);
            put("CNWeatherId_SEVERE_HAZE", CNWeatherId.SEVERE_HAZE);
            put("CNWeatherId_HEAVY_FOGGY", CNWeatherId.HEAVY_FOGGY);
            put("CNWeatherId_SEVERE_FOGGY", CNWeatherId.SEVERE_FOGGY);
            put("CNWeatherId_RAINFALL", CNWeatherId.RAINFALL);
            put("CNWeatherId_SNOWFALL", CNWeatherId.SNOWFALL);
            put("CNWeatherId_UNKNOWN", CNWeatherId.UNKNOWN);

            put("WeatherStatus_CLOTHING_INDEX", WeatherStatus.CLOTHING_INDEX);
            put("WeatherStatus_SPORT_INDEX", WeatherStatus.SPORT_INDEX);
            put("WeatherStatus_COLD_INDEX", WeatherStatus.COLD_INDEX);
            put("WeatherStatus_CAR_WASH_INDEX", WeatherStatus.CAR_WASH_INDEX);
            put("WeatherStatus_TOURISM_INDEX", WeatherStatus.TOURISM_INDEX);
            put("WeatherStatus_UV_INDEX", WeatherStatus.UV_INDEX);
            put("WeatherStatus_DOWN_JACKET", WeatherStatus.DOWN_JACKET);
            put("WeatherStatus_HEAVY_COAT", WeatherStatus.HEAVY_COAT);
            put("WeatherStatus_SWEATER", WeatherStatus.SWEATER);
            put("WeatherStatus_THIN_COAT", WeatherStatus.THIN_COAT);
            put("WeatherStatus_LONG_SLEEVES", WeatherStatus.LONG_SLEEVES);
            put("WeatherStatus_SHORT_SLEEVE", WeatherStatus.SHORT_SLEEVE);
            put("WeatherStatus_THIN_SHORT_SLEEVE", WeatherStatus.THIN_SHORT_SLEEVE);
            put("WeatherStatus_SUITABLE_SPORT", WeatherStatus.SUITABLE_SPORT);
            put("WeatherStatus_MORE_SUITABLE_SPORT", WeatherStatus.MORE_SUITABLE_SPORT);
            put("WeatherStatus_NOT_SUITABLE_SPORT", WeatherStatus.NOT_SUITABLE_SPORT);
            put("WeatherStatus_NOT_EASY_CATCH_COLD", WeatherStatus.NOT_EASY_CATCH_COLD);
            put("WeatherStatus_EASIER_CATCH_COLD", WeatherStatus.EASIER_CATCH_COLD);
            put("WeatherStatus_BE_SUSCEPTIBLE_COLD", WeatherStatus.BE_SUSCEPTIBLE_COLD);
            put("WeatherStatus_EXTREMELY_SUSCEPTIBLE_COLD", WeatherStatus.EXTREMELY_SUSCEPTIBLE_COLD);
            put("WeatherStatus_UNSUITABLE", WeatherStatus.UNSUITABLE);
            put("WeatherStatus_NOT_VERY_SUITABLE", WeatherStatus.NOT_VERY_SUITABLE);
            put("WeatherStatus_MORE_SUITABLE", WeatherStatus.MORE_SUITABLE);
            put("WeatherStatus_SUITABLE", WeatherStatus.SUITABLE);
            put("WeatherStatus_WEAKEST", WeatherStatus.WEAKEST);
            put("WeatherStatus_WEAK", WeatherStatus.WEAK);
            put("WeatherStatus_MEDIUM", WeatherStatus.MEDIUM);
            put("WeatherStatus_STRONG", WeatherStatus.STRONG);
            put("WeatherStatus_VERY_STRONG", WeatherStatus.VERY_STRONG);

            put("HeadsetStatus_CONNECTED", HeadsetStatus.CONNECTED);
            put("HeadsetStatus_DISCONNECTED", HeadsetStatus.DISCONNECTED);
            put("HeadsetStatus_UNKNOWN", HeadsetStatus.UNKNOWN);

            put("BluetoothStatus_CONNECTED", BluetoothStatus.CONNECTED);
            put("BluetoothStatus_DISCONNECTED", BluetoothStatus.DISCONNECTED);
            put("BluetoothStatus_UNKNOWN", BluetoothStatus.UNKNOWN);
            put("BluetoothStatus_DEVICE_CAR", BluetoothStatus.DEVICE_CAR);

            put("ScreenStatus_UNLOCK", ScreenStatus.UNLOCK);
            put("ScreenStatus_SCREEN_OFF", ScreenStatus.UNKNOWN);
            put("ScreenStatus_SCREEN_ON", ScreenStatus.UNKNOWN);
            put("ScreenStatus_UNKNOWN", ScreenStatus.UNKNOWN);

            put("WifiStatus_CONNECTED", WifiStatus.CONNECTED);
            put("WifiStatus_DISCONNECTED", WifiStatus.DISCONNECTED);
            put("WifiStatus_ENABLED", WifiStatus.ENABLED);
            put("WifiStatus_DISABLED", WifiStatus.DISABLED);
            put("WifiStatus_UNKNOWN", WifiStatus.UNKNOWN);

            put("BarrierStatus_TRUE", BarrierStatus.TRUE);
            put("BarrierStatus_FALSE", BarrierStatus.FALSE);
            put("BarrierStatus_UNKNOWN", BarrierStatus.UNKNOWN);

            put("ApplicationStatus_RUNNING", ApplicationStatus.RUNNING);
            put("ApplicationStatus_SILENT", ApplicationStatus.SILENT);
            put("ApplicationStatus_UNKNOWN", ApplicationStatus.UNKNOWN);

            put("DarkModeStatus_DARK_MODE_OFF", DarkModeStatus.DARK_MODE_OFF);
            put("DarkModeStatus_DARK_MODE_ON", DarkModeStatus.DARK_MODE_ON);
            put("DarkModeStatus_DARK_MODE_UNKNOWN", DarkModeStatus.DARK_MODE_UNKNOWN);

            put("EVENT_HEADSET", "HEADSET_BARRIER_RECEIVER_ACTION");
            put("EVENT_AMBIENTLIGHT", "AMBIENT_LIGHT_BARRIER_RECEIVER_ACTION");
            put("EVENT_WIFI", "WIFI_BARRIER_RECEIVER_ACTION");
            put("EVENT_BLUETOOTH", "BLUETOOTH_BARRIER_RECEIVER_ACTION");
            put("EVENT_BEHAVIOR", "BEHAVIOR_BARRIER_RECEIVER_ACTION");
            put("EVENT_LOCATION", "LOCATION_BARRIER_RECEIVER_ACTION");
            put("EVENT_SCREEN", "SCREEN_BARRIER_RECEIVER_ACTION");
            put("EVENT_TIME", "TIME_BARRIER_RECEIVER_ACTION");
            put("EVENT_BEACON", "BEACON_BARRIER_RECEIVER_ACTION");
            put("EVENT_UPDATE_WINDOW", "EVENT_UPDATE_WINDOW");
            put("EVENT_COMBINED", "COMBINED_BARRIER_RECEIVER_ACTION");

            put("AMBIENTLIGHT_ABOVE", "AMBIENTLIGHT_ABOVE");
            put("AMBIENTLIGHT_BELOW", "AMBIENTLIGHT_BELOW");
            put("AMBIENTLIGHT_RANGE", "AMBIENTLIGHT_RANGE");

            put("EVENT_HEADSET_KEEPING", "EVENT_HEADSET_KEEPING");
            put("EVENT_HEADSET_CONNECTING", "EVENT_HEADSET_CONNECTING");
            put("EVENT_HEADSET_DISCONNECTING", "EVENT_HEADSET_DISCONNECTING");

            put("BEACON_DISCOVER", "BEACON_DISCOVER");
            put("BEACON_KEEP", "BEACON_KEEP");
            put("BEACON_MISSED", "BEACON_MISSED");

            put("BEHAVIOR_KEEPING", "BEHAVIOR_KEEPING");
            put("BEHAVIOR_BEGINNING", "BEHAVIOR_BEGINNING");
            put("BEHAVIOR_ENDING", "BEHAVIOR_ENDING");

            put("BLUETOOTH_KEEP", "BLUETOOTH_KEEP");
            put("BLUETOOTH_CONNECTING", "BLUETOOTH_CONNECTING");
            put("BLUETOOTH_DISCONNECTING", "BLUETOOTH_DISCONNECTING");

            put("LOCATION_ENTER", "LOCATION_ENTER");
            put("LOCATION_STAY", "LOCATION_STAY");
            put("LOCATION_EXIT", "LOCATION_EXIT");

            put("SCREEN_KEEPING", "SCREEN_KEEPING");
            put("SCREEN_ON", "SCREEN_ON");
            put("SCREEN_OFF", "SCREEN_OFF");
            put("SCREEN_UNLOCK", "SCREEN_UNLOCK");

            put("TIME_IN_SUNRISE_OR_SUNSET_PERIOD", "TIME_IN_SUNRISE_OR_SUNSET_PERIOD");
            put("TIME_DURING_PERIOD_OF_DAY", "TIME_DURING_PERIOD_OF_DAY");
            put("TIME_DURING_TIME_PERIOD", "TIME_DURING_TIME_PERIOD");
            put("TIME_DURING_PERIOD_OF_WEEK", "TIME_DURING_PERIOD_OF_WEEK");
            put("TIME_IN_TIME_CATEGORY", "TIME_IN_TIME_CATEGORY");

            put("WIFI_KEEPING", "WIFI_KEEPING");
            put("WIFI_CONNECTING", "WIFI_CONNECTING");
            put("WIFI_DISCONNECTING", "WIFI_DISCONNECTING");
            put("WIFI_ENABLING", "WIFI_ENABLING");
            put("WIFI_DISABLING", "WIFI_DISABLING");

            put("BARRIER_TYPE_AND", "and");
            put("BARRIER_TYPE_OR", "or");
            put("BARRIER_TYPE_NOT", "not");

            put("TASK_NAME","barrierReceiver");
        }
    };

    public static Map<String, Object> getAllConstants() {
        return ImmutableMap.copyOf(ALL_CONSTANTS);
    }
}