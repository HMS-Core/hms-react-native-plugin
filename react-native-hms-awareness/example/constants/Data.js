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

import { Dimensions, NativeEventEmitter } from "react-native";
import { HMSAwarenessBarrierModule } from "@hmscore/react-native-hms-awareness";

export default class Data { }

export const eventEmitter = new NativeEventEmitter(HMSAwarenessBarrierModule);

export const ScreenWidth = Dimensions.get("window").width;
export const ScreenHeight = Dimensions.get("window").height;
export const buttonWidth = 200;
export const buttonHeight = 50;

export const captureDesc =
  "The Capture API allows your app to request the current user status, such as time, location, behavior, " +
  "and whether a headset is connected. For example, after your app runs, it can request the user's time and location in order to recommend entertainment activities available nearby at weekends to the user.";
export const barrierDesc =
  "The Barrier API allows your app to set a barrier for specific contextual conditions. When the conditions are met, your app will receive a notification. For example, a notification is triggered when an audio device connects to a mobile phone for an audio device status barrier about connection or illuminance is less than 100 lux for an ambient light barrier whose trigger condition is set for illuminance that is less than 100 lux. You can also combine different types of barriers for your app to support different use cases. For example, your app can recommend nearby services if the user has stayed in a specified business zone (geofence) for the preset period of time (time barrier).After your app registers with a barrier, your app can be automatically started when the barrier is triggered. This is because the barrier function of Awareness Kit can run in the background. Your app can then recommend services based on the user's current situation.";
export const captureConditions =
  "* getTimeCategoriesForFuture:  Note that cross-year query isn't supported." +
  "\n\n* getDarkModeStatus:  Use a device with Android 10 for DarkModeStatus to be true." +
  "\n\n* If your methods don't work, check HMSCore for updates. Check your HMSCore permissions. Try to delete the application and install it again." +
  "\n\n* The Aqi variable may be blank outside of the Chinese location.";

export const barrierConditions =
  "* oneHourMilliSecond = 60 * 60 * 1000L " +
  "\n  You can use onehourmilisecond and its multiples for variables whose parameter value is millisecond." +
  "\n  max = oneHourMilliSecond * 24 " +
  "\n  min = 0" +
  "\n\n* If you don't send the timeZoneId parameter, the automatic TimeZone.getDefault is received. (for these methods duringPeriodOfWeek and duringPeriodOfDay)" +
  "\n\n* In QueryBarrier =>" +
  "\n   - headset connecting barrier" +
  "\n   - headset disconnecting barrier" +
  "\n   - light above barrier" +
  "\n   - wifi keeping with bssid barrier" +
  "\n   - wifi keeping barrier\n";

export const Page = {
  Barrier: 0,
  Capture: 1,
};
export const AmbientLightTypes = {
  ABOVE: "ABOVE",
  BELOW: "BELOW",
  RANGE: "RANGE",
};
export const ScreenTypes = {
  SCREEN_KEEPING: "SCREEN_KEEPING",
  SCREEN_ON: "SCREEN_ON",
  SCREEN_OFF: "SCREEN_OFF",
  SCREEN_UNLOCK: "SCREEN_UNLOCK",
};

export const HeadsetTypes = {
  KEEPING: "HEADSET_KEEPING",
  CONNECTING: "HEADSET_CONNECTING",
  DISCONNECTING: "HEADSET_DISCONNECTING",
};

export const BluetoothTypes = {
  KEEPING: "BLUETOOTH_KEEPING",
  CONNECTING: "BLUETOOTH_KEEPING",
  DISCONNECTING: "BLUETOOTH_DISCONNECTING",
};

export const BehaviorTypes = {
  KEEPING: "BEHAVIOR_KEEPING",
  BEGINNING: "BEHAVIOR_BEGINNING",
  ENDING: "BEHAVIOR_ENDING",
};

export const BeaconTypes = {
  DISCOVER: "BEACON_DISCOVER",
  KEEP: "BEACON_KEEP",
  MISSED: "BEACON_MISSED",
};
export const LocationTypes = {
  ENTER: "LOCATION_ENTER",
  STAY: "LOCATION_STAY",
  EXIT: "LOCATION_EXIT",
};
export const TimeTypes = {
  SUNRISE_OR_SUNSET_PERIOD: "SUNRISE_OR_SUNSET_PERIOD",
  DURING_PERIOD_OF_DAY: "DURING_PERIOD_OF_DAY",
  DURING_TIME_PERIOD: "DURING_TIME_PERIOD",
  PERIOD_OF_WEEK: "PERIOD_OF_WEEK",
  IN_TIME_CATEGORY: "IN_TIME_CATEGORY",
};
export const WifiTypes = {
  WIFI_KEEPING: "WIFI_KEEPING",
  WIFI_KEEPING_WITH_BSSID: "WIFI_KEEPING_WITH_BSSID",
  WIFI_CONNECTING: "WIFI_CONNECTING",
  WIFI_CONNECTING_WITH_BSSID: "WIFI_CONNECTING_WITH_BSSID",
  WIFI_DISCONNECTING: "WIFI_DISCONNECTING",
  WIFI_DISCONNECTING_WITH_BSSID: "WIFI_DISCONNECTING_WITH_BSSID",
  WIFI_ENABLING: "WIFI_ENABLING",
  WIFI_DISABLING: "WIFI_DISABLING",
};
export const MethodType = {
  BeaconStatus: "BeaconStatus",
  Behavior: "Behavior",
  HeadsetStatus: "HeadsetStatus",
  Location: "Location",
  CurrentLocation: "CurrentLocation",
  TimeCategories: "TimeCategories",
  TimeCategoriesByUser: "TimeCategoriesByUser",
  TimeCategoriesByCountryCode: "TimeCategoriesByCountryCode",
  TimeCategoriesByIp: "TimeCategoriesByIp",
  TimeCategoriesForFuture: "TimeCategoriesForFuture",
  LightIntensity: "LightIntensity",
  WeatherByDevice: "WeatherByDevice",
  WeatherByPosition: "WeatherByPosition",
  BluetoothStatus: "BluetoothStatus",
  QuerySupportinCapabilities: "QuerySupportinCapabilities",
  EnableUpdateWindow: "EnableUpdateWindow",
  ScreenStatus: "ScreenStatus",
  Wifistatus: "WifiStatus",
  ApplicationStatus: "ApplicationStatus",
  DarkModeStatus: "DarkModeStatus",
};
