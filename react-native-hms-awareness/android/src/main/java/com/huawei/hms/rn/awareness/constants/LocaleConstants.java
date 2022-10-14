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

import android.util.SparseArray;

import com.huawei.hms.kit.awareness.barrier.BarrierStatus;
import com.huawei.hms.kit.awareness.barrier.TimeBarrier;
import com.huawei.hms.kit.awareness.status.ApplicationStatus;
import com.huawei.hms.kit.awareness.status.BluetoothStatus;
import com.huawei.hms.kit.awareness.status.CapabilityStatus;
import com.huawei.hms.kit.awareness.status.DarkModeStatus;
import com.huawei.hms.kit.awareness.status.HeadsetStatus;
import com.huawei.hms.kit.awareness.status.ScreenStatus;
import com.huawei.hms.kit.awareness.status.WifiStatus;
import com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId;
import com.huawei.hms.kit.awareness.status.weather.constant.WeatherId;

public class LocaleConstants {

    public static final String KEY_CONTENT_TITLE = "contentTitle";
    public static final String KEY_CONTENT_TEXT = "contentText";
    public static final String KEY_DEF_TYPE = "defType";
    public static final String KEY_RESOURCE_NAME = "resourceName";
    public static final String DEFAULT_CONTENT_TITLE = "Awareness Kit";
    public static final String DEFAULT_CONTENT_TEXT = "Service is running";
    public static final String DEFAULT_DEF_TYPE = "mipmap";
    public static final String DEFAULT_RESOURCE_NAME = "ic_launcher";

    public static final SparseArray<String> CAPABILITY_STATUS = new SparseArray<>();

    static {
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_HEADSET, "AWA_CAP_CODE_HEADSET");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_LOCATION_CAPTURE, "AWA_CAP_CODE_LOCATION_CAPTURE");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_LOCATION_NORMAL_BARRIER, "AWA_CAP_CODE_LOCATION_NORMAL_BARRIER");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_LOCATION_LOW_POWER_BARRIER, "AWA_CAP_CODE_LOCATION_LOW_POWER_BARRIER");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_BEHAVIOR, "AWA_CAP_CODE_BEHAVIOR");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_TIME, "AWA_CAP_CODE_TIME");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_AMBIENT_LIGHT, "AWA_CAP_CODE_AMBIENT_LIGHT");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_WEATHER, "AWA_CAP_CODE_WEATHER");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_BEACON, "AWA_CAP_CODE_BEACON");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_INCAR_BLUETOOTH, "AWA_CAP_CODE_INCAR_BLUETOOTH");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_SCREEN, "AWA_CAP_CODE_SCREEN");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_WIFI, "AWA_CAP_CODE_WIFI");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_APPLICATION, "AWA_CAP_CODE_APPLICATION");
        CAPABILITY_STATUS.put(CapabilityStatus.AWA_CAP_CODE_DARK_MODE, "AWA_CAP_CODE_DARK_MODE");
    }

    public static final SparseArray<String> WEATHER_ID = new SparseArray<>();

    static {
        WEATHER_ID.put(WeatherId.SUNNY, "SUNNY");
        WEATHER_ID.put(WeatherId.MOSTLY_SUNNY, "MOSTLY_SUNNY");
        WEATHER_ID.put(WeatherId.PARTLY_SUNNY, "PARTLY_SUNNY");
        WEATHER_ID.put(WeatherId.INTERMITTENT_CLOUDS, "INTERMITTENT_CLOUDS");
        WEATHER_ID.put(WeatherId.HAZY_SUNSHINE, "HAZY_SUNSHINE");
        WEATHER_ID.put(WeatherId.MOSTLY_CLOUDY, "MOSTLY_CLOUDY");
        WEATHER_ID.put(WeatherId.CLOUDY, "CLOUDY");
        WEATHER_ID.put(WeatherId.DREARY, "DREARY");
        WEATHER_ID.put(WeatherId.FOG, "FOG");
        WEATHER_ID.put(WeatherId.SHOWERS, "SHOWERS");
        WEATHER_ID.put(WeatherId.MOSTLY_CLOUDY_WITH_SHOWERS, "MOSTLY_CLOUDY_WITH_SHOWERS");
        WEATHER_ID.put(WeatherId.PARTLY_SUNNY_WITH_SHOWERS, "PARTLY_SUNNY_WITH_SHOWERS");
        WEATHER_ID.put(WeatherId.T_STORMS, "T_STORMS");
        WEATHER_ID.put(WeatherId.MOSTLY_CLOUDY_WITH_T_STORMS, "MOSTLY_CLOUDY_WITH_T_STORMS");
        WEATHER_ID.put(WeatherId.PARTLY_SUNNY_WITH_T_STORMS, "PARTLY_SUNNY_WITH_T_STORMS");
        WEATHER_ID.put(WeatherId.RAIN, "RAIN");
        WEATHER_ID.put(WeatherId.FLURRIES, "FLURRIES");
        WEATHER_ID.put(WeatherId.MOSTLY_CLOUDY_WITH_FLURRIES, "MOSTLY_CLOUDY_WITH_FLURRIES");
        WEATHER_ID.put(WeatherId.PARTLY_SUNNY_WITH_FLURRIES, "PARTLY_SUNNY_WITH_FLURRIES");
        WEATHER_ID.put(WeatherId.SNOW, "SNOW");
        WEATHER_ID.put(WeatherId.MOSTLY_CLOUDY_WITH_SNOW, "MOSTLY_CLOUDY_WITH_SNOW");
        WEATHER_ID.put(WeatherId.ICE, "ICE");
        WEATHER_ID.put(WeatherId.SLEET, "SLEET");
        WEATHER_ID.put(WeatherId.FREEZING_RAIN, "FREEZING_RAIN");
        WEATHER_ID.put(WeatherId.RAIN_AND_SNOW, "RAIN_AND_SNOW");
        WEATHER_ID.put(WeatherId.HOT, "HOT");
        WEATHER_ID.put(WeatherId.COLD, "COLD");
        WEATHER_ID.put(WeatherId.WINDY, "WINDY");
        WEATHER_ID.put(WeatherId.CLEAR, "CLEAR");
        WEATHER_ID.put(WeatherId.PARTLY_CLOUDY, "PARTLY_CLOUDY");
        WEATHER_ID.put(WeatherId.INTERMITTENT_CLOUDS_2, "INTERMITTENT_CLOUDS_2");
        WEATHER_ID.put(WeatherId.HAZY_MOONLIGHT, "HAZY_MOONLIGHT");
        WEATHER_ID.put(WeatherId.MOSTLY_CLOUDY_2, "MOSTLY_CLOUDY_2");
        WEATHER_ID.put(WeatherId.PARTLY_CLOUDY_WITH_SHOWERS, "PARTLY_CLOUDY_WITH_SHOWERS");
        WEATHER_ID.put(WeatherId.MOSTLY_CLOUDY_WITH_SHOWERS_2, "MOSTLY_CLOUDY_WITH_SHOWERS_2");
        WEATHER_ID.put(WeatherId.PARTLY_CLOUDY_WITH_T_STORMS, "PARTLY_CLOUDY_WITH_T_STORMS");
        WEATHER_ID.put(WeatherId.MOSTLY_CLOUDY_WITH_T_STORMS_2, "MOSTLY_CLOUDY_WITH_T_STORMS_2");
        WEATHER_ID.put(WeatherId.MOSTLY_CLOUDY_WITH_FLURRIES_2, "MOSTLY_CLOUDY_WITH_FLURRIES_2");
        WEATHER_ID.put(WeatherId.MOSTLY_CLOUDY_WITH_SNOW_2, "MOSTLY_CLOUDY_WITH_SNOW_2");
    }

    public static final SparseArray<String> CN_WEATHER_ID = new SparseArray<>();

    static {
        CN_WEATHER_ID.put(CNWeatherId.INVALID_VALUE, "INVALID_VALUE");
        CN_WEATHER_ID.put(CNWeatherId.SUNNY, "SUNNY");
        CN_WEATHER_ID.put(CNWeatherId.CLOUDY, "CLOUDY");
        CN_WEATHER_ID.put(CNWeatherId.OVERCAST, "OVERCAST");
        CN_WEATHER_ID.put(CNWeatherId.SHOWER, "SHOWER");
        CN_WEATHER_ID.put(CNWeatherId.THUNDERSHOWER, "THUNDERSHOWER");
        CN_WEATHER_ID.put(CNWeatherId.THUNDERSHOWER_WITH_HAIL, "THUNDERSHOWER_WITH_HAIL");
        CN_WEATHER_ID.put(CNWeatherId.SLEET, "SLEET");
        CN_WEATHER_ID.put(CNWeatherId.LIGHT_RAIN, "LIGHT_RAIN");
        CN_WEATHER_ID.put(CNWeatherId.MODERATE_RAIN, "MODERATE_RAIN");
        CN_WEATHER_ID.put(CNWeatherId.HEAVY_RAIN, "HEAVY_RAIN");
        CN_WEATHER_ID.put(CNWeatherId.STORM, "STORM");
        CN_WEATHER_ID.put(CNWeatherId.HEAVY_STORM, "HEAVY_STORM");
        CN_WEATHER_ID.put(CNWeatherId.SEVERE_STORM, "SEVERE_STORM");
        CN_WEATHER_ID.put(CNWeatherId.SNOW_FLURRY, "SNOW_FLURRY");
        CN_WEATHER_ID.put(CNWeatherId.LIGHT_SNOW, "LIGHT_SNOW");
        CN_WEATHER_ID.put(CNWeatherId.MODERATE_SNOW, "MODERATE_SNOW");
        CN_WEATHER_ID.put(CNWeatherId.HEAVY_SNOW, "HEAVY_SNOW");
        CN_WEATHER_ID.put(CNWeatherId.SNOWSTORM, "SNOWSTORM");
        CN_WEATHER_ID.put(CNWeatherId.FOGGY, "FOGGY");
        CN_WEATHER_ID.put(CNWeatherId.ICE_RAIN, "ICE_RAIN");
        CN_WEATHER_ID.put(CNWeatherId.DUSTSTORM, "DUSTSTORM");
        CN_WEATHER_ID.put(CNWeatherId.LIGHT_TO_MODERATE_RAIN, "LIGHT_TO_MODERATE_RAIN");
        CN_WEATHER_ID.put(CNWeatherId.MODERATE_TO_HEAVY_RAIN, "MODERATE_TO_HEAVY_RAIN");
        CN_WEATHER_ID.put(CNWeatherId.HEAVY_RAIN_TO_STORM, "HEAVY_RAIN_TO_STORM");
        CN_WEATHER_ID.put(CNWeatherId.STORM_TO_HEAVY_STORM, "STORM_TO_HEAVY_STORM");
        CN_WEATHER_ID.put(CNWeatherId.HEAVY_TO_SEVERE_STORM, "HEAVY_TO_SEVERE_STORM");
        CN_WEATHER_ID.put(CNWeatherId.LIGHT_TO_MODERATE_SNOW, "LIGHT_TO_MODERATE_SNOW");
        CN_WEATHER_ID.put(CNWeatherId.MODERATE_TO_HEAVY_SNOW, "MODERATE_TO_HEAVY_SNOW");
        CN_WEATHER_ID.put(CNWeatherId.HEAVY_SNOW_TO_SNOWSTORM, "HEAVY_SNOW_TO_SNOWSTORM");
        CN_WEATHER_ID.put(CNWeatherId.DUST, "DUST");
        CN_WEATHER_ID.put(CNWeatherId.SAND, "SAND");
        CN_WEATHER_ID.put(CNWeatherId.SANDSTORM, "SANDSTORM");
        CN_WEATHER_ID.put(CNWeatherId.DENSE_FOGGY, "DENSE_FOGGY");
        CN_WEATHER_ID.put(CNWeatherId.SNOW, "SNOW");
        CN_WEATHER_ID.put(CNWeatherId.MODERATE_FOGGY, "MODERATE_FOGGY");
        CN_WEATHER_ID.put(CNWeatherId.HAZE, "HAZE");
        CN_WEATHER_ID.put(CNWeatherId.MODERATE_HAZE, "MODERATE_HAZE");
        CN_WEATHER_ID.put(CNWeatherId.HEAVY_HAZE, "HEAVY_HAZE");
        CN_WEATHER_ID.put(CNWeatherId.SEVERE_HAZE, "SEVERE_HAZE");
        CN_WEATHER_ID.put(CNWeatherId.HEAVY_FOGGY, "HEAVY_FOGGY");
        CN_WEATHER_ID.put(CNWeatherId.SEVERE_FOGGY, "SEVERE_FOGGY");
        CN_WEATHER_ID.put(CNWeatherId.RAINFALL, "RAINFALL");
        CN_WEATHER_ID.put(CNWeatherId.SNOWFALL, "SNOWFALL");
        CN_WEATHER_ID.put(CNWeatherId.UNKNOWN, "UNKNOWN");
    }

    public static final SparseArray<String> APPLICATION_STATUS = new SparseArray<>();

    static {
        APPLICATION_STATUS.put(ApplicationStatus.RUNNING, "RUNNING");
        APPLICATION_STATUS.put(ApplicationStatus.SILENT, "SILENT");
        APPLICATION_STATUS.put(ApplicationStatus.UNKNOWN, "UNKNOWN");
    }

    public static final SparseArray<String> BLUETOOTH_STATUS = new SparseArray<>();

    static {
        BLUETOOTH_STATUS.put(BluetoothStatus.CONNECTED, "CONNECTED");
        BLUETOOTH_STATUS.put(BluetoothStatus.DISCONNECTED, "DISCONNECTED");
        BLUETOOTH_STATUS.put(BluetoothStatus.UNKNOWN, "UNKNOWN");
        BLUETOOTH_STATUS.put(BluetoothStatus.DEVICE_CAR, "DEVICE_CAR");
    }

    public static final SparseArray<String> HEADSET_STATUS = new SparseArray<>();

    static {
        HEADSET_STATUS.put(HeadsetStatus.CONNECTED, "CONNECTED");
        HEADSET_STATUS.put(HeadsetStatus.DISCONNECTED, "DISCONNECTED");
        HEADSET_STATUS.put(HeadsetStatus.UNKNOWN, "UNKNOWN");
    }

    public static final SparseArray<String> SCREEN_STATUS = new SparseArray<>();

    static {
        SCREEN_STATUS.put(ScreenStatus.UNLOCK, "UNLOCK");
        SCREEN_STATUS.put(ScreenStatus.SCREEN_OFF, "UNKNOWN");
        SCREEN_STATUS.put(ScreenStatus.SCREEN_ON, "UNKNOWN");
        SCREEN_STATUS.put(ScreenStatus.UNKNOWN, "UNKNOWN");
    }

    public static final SparseArray<String> WIFI_STATUS = new SparseArray<>();

    static {
        WIFI_STATUS.put(WifiStatus.CONNECTED, "CONNECTED");
        WIFI_STATUS.put(WifiStatus.DISCONNECTED, "DISCONNECTED");
        WIFI_STATUS.put(WifiStatus.ENABLED, "ENABLED");
        WIFI_STATUS.put(WifiStatus.DISABLED, "DISABLED");
        WIFI_STATUS.put(WifiStatus.UNKNOWN, "UNKNOWN");
    }

    public static final SparseArray<String> TIME_BARRIER = new SparseArray<>();

    static {
        TIME_BARRIER.put(TimeBarrier.TIME_CATEGORY_WEEKDAY, "TIME_CATEGORY_WEEKDAY");
        TIME_BARRIER.put(TimeBarrier.TIME_CATEGORY_WEEKEND, "TIME_CATEGORY_WEEKEND");
        TIME_BARRIER.put(TimeBarrier.TIME_CATEGORY_HOLIDAY, "TIME_CATEGORY_HOLIDAY");
        TIME_BARRIER.put(TimeBarrier.TIME_CATEGORY_NOT_HOLIDAY, "TIME_CATEGORY_NOT_HOLIDAY");
        TIME_BARRIER.put(TimeBarrier.TIME_CATEGORY_MORNING, "TIME_CATEGORY_MORNING");
        TIME_BARRIER.put(TimeBarrier.TIME_CATEGORY_AFTERNOON, "TIME_CATEGORY_AFTERNOON");
        TIME_BARRIER.put(TimeBarrier.TIME_CATEGORY_EVENING, "TIME_CATEGORY_EVENING");
        TIME_BARRIER.put(TimeBarrier.TIME_CATEGORY_NIGHT, "TIME_CATEGORY_NIGHT");
    }

    public static final SparseArray<String> DARK_MODE_STATUS = new SparseArray<>();

    static {
        DARK_MODE_STATUS.put(DarkModeStatus.DARK_MODE_OFF, "DarkModeStatus.DARK_MODE_OFF");
        DARK_MODE_STATUS.put(DarkModeStatus.DARK_MODE_ON, "DarkModeStatus.DARK_MODE_ON");
        DARK_MODE_STATUS.put(DarkModeStatus.DARK_MODE_UNKNOWN, "DarkModeStatus.DARK_MODE_UNKNOWN");
    }

    public static final SparseArray<String> BARRIER_STATUS = new SparseArray<>();

    static {
        BARRIER_STATUS.put(BarrierStatus.FALSE, "FALSE");
        BARRIER_STATUS.put(BarrierStatus.TRUE, "TRUE");
        BARRIER_STATUS.put(BarrierStatus.UNKNOWN, "UNKNOWN");
    }
}