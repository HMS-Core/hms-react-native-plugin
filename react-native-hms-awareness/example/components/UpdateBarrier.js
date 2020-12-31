/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

import React from "react";
import { StyleSheet, TouchableOpacity, Text, View } from "react-native";

import { darkJungleGreen, green } from "../constants/Colors";
import {
  buttonWidth,
  buttonHeight,
  AmbientLightTypes,
  BeaconTypes,
  BehaviorTypes,
  BluetoothTypes,
  HeadsetTypes,
  LocationTypes,
  ScreenTypes,
  TimeTypes,
  WifiTypes,
} from "../constants/Data.js";
import { HMSAwarenessBarrierModule } from "@hmscore/react-native-hms-awareness";

export default class BaseComponent extends React.Component {
  async ambientLightBarrier(type) {
    try {
      this.props.changeStateProgress(true);
      var AmbientLightBarrierReq;

      switch (type) {
        case AmbientLightTypes.ABOVE: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.AMBIENTLIGHT_ABOVE;
          const barrierLabel = "light above barrier";
          const minLightIntensity = 500.0;
          const AmbientLightAboveReq = {
            barrierReceiverAction: barrierReceiverAction,
            minLightIntensity: minLightIntensity,
            barrierLabel: barrierLabel,
          };
          AmbientLightBarrierReq = AmbientLightAboveReq;
          break;
        }
        case AmbientLightTypes.BELOW: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.AMBIENTLIGHT_BELOW;
          const barrierLabel = "light below barrier";
          const maxLightIntensity = 2500.0;
          const AmbientLightBelowReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            maxLightIntensity: maxLightIntensity,
          };
          AmbientLightBarrierReq = AmbientLightBelowReq;
          break;
        }
        case AmbientLightTypes.RANGE: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.AMBIENTLIGHT_RANGE;
          const barrierLabel = "light range barrier";
          const minLightIntensity = 500.0;
          const maxLightIntensity = 2500.0;
          const AmbientLightRangeReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            minLightIntensity: minLightIntensity,
            maxLightIntensity: maxLightIntensity,
          };
          AmbientLightBarrierReq = AmbientLightRangeReq;
          break;
        }
      }
      var UpdateBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
        HMSAwarenessBarrierModule.EVENT_AMBIENTLIGHT,
        AmbientLightBarrierReq
      );
      this.props.changeStateProgress(false);
      alert(JSON.stringify(UpdateBarrierRes));
    } catch (e) {
      this.props.changeStateProgress(false);
      alert("error::" + JSON.stringify(e));
    }
  }

  async beaconBarrier(type) {
    try {
      this.props.changeStateProgress(true);
      var BeaconBarrierReq;

      const beaconArray = [
        {
          namespace: "dev736430079244559178",
          type: "ibeacon",
          content: "content",
        },
        {
          namespace: "dev736430079244559179",
          type: "ibeacon",
          content: "content2",
        },
      ];

      switch (type) {
        case BeaconTypes.DISCOVER: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.BEACON_DISCOVER;
          const barrierLabel = "discover beacon barrier";
          const BeaconDiscoverReq = {
            barrierReceiverAction: barrierReceiverAction,
            beaconArray: beaconArray,
            barrierLabel: barrierLabel,
          };
          BeaconBarrierReq = BeaconDiscoverReq;
          break;
        }
        case BeaconTypes.KEEP: {
          const barrierReceiverAction = HMSAwarenessBarrierModule.BEACON_KEEP;
          const barrierLabel = "keep beacon barrier";
          const BeaconKeepReq = {
            barrierReceiverAction: barrierReceiverAction,
            beaconArray: beaconArray,
            barrierLabel: barrierLabel,
          };
          BeaconBarrierReq = BeaconKeepReq;
          break;
        }
        case BeaconTypes.MISSED: {
          const barrierReceiverAction = HMSAwarenessBarrierModule.BEACON_MISSED;
          const barrierLabel = "missed beacon barrier";
          const BeaconMissedReq = {
            barrierReceiverAction: barrierReceiverAction,
            beaconArray: beaconArray,
            barrierLabel: barrierLabel,
          };
          BeaconBarrierReq = BeaconMissedReq;
          break;
        }
      }

      var UpdateBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
        HMSAwarenessBarrierModule.EVENT_BEACON,
        BeaconBarrierReq
      );
      this.props.changeStateProgress(false);
      alert(JSON.stringify(UpdateBarrierRes));
    } catch (e) {
      this.props.changeStateProgress(false);
      alert("error::" + JSON.stringify(e));
    }
  }

  async behaviorBarrier(type) {
    try {
      this.props.changeStateProgress(true);
      var BehaviorBarrierReq;

      switch (type) {
        case BehaviorTypes.KEEPING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.BEHAVIOR_KEEPING;
          const barrierLabel = "behavior keeping barrier";
          const behaviorTypes = [];
          behaviorTypes.push(
            HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_STILL
          );
          const BehaviorKeepingReq = {
            barrierReceiverAction: barrierReceiverAction,
            behaviorTypes: behaviorTypes,
            barrierLabel: barrierLabel,
          };
          BehaviorBarrierReq = BehaviorKeepingReq;
          break;
        }
        case BehaviorTypes.BEGINNING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.BEHAVIOR_BEGINNING;
          const barrierLabel = "behavior beginning barrier";
          const behaviorTypes = [];
          behaviorTypes.push(
            HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_WALKING
          );
          behaviorTypes.push(
            HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_RUNNING
          );
          const BehaviorBeginningReq = {
            barrierReceiverAction: barrierReceiverAction,
            behaviorTypes: behaviorTypes,
            barrierLabel: barrierLabel,
          };
          BehaviorBarrierReq = BehaviorBeginningReq;
          break;
        }
        case BehaviorTypes.ENDING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.BEHAVIOR_ENDING;
          const barrierLabel = "behavior ending barrier";
          const behaviorTypes = [];
          behaviorTypes.push(
            HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_WALKING
          );
          const BehaviorEndingReq = {
            barrierReceiverAction: barrierReceiverAction,
            behaviorTypes: behaviorTypes,
            barrierLabel: barrierLabel,
          };
          BehaviorBarrierReq = BehaviorEndingReq;
          break;
        }
      }

      var UpdateBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
        HMSAwarenessBarrierModule.EVENT_BEHAVIOR,
        BehaviorBarrierReq
      );
      this.props.changeStateProgress(false);
      alert(JSON.stringify(UpdateBarrierRes));
    } catch (e) {
      this.props.changeStateProgress(false);
      alert("error::" + JSON.stringify(e));
    }
  }

  async bluetoothBarrier(type) {
    try {
      this.props.changeStateProgress(true);
      var BluetoothBarrierReq;

      switch (type) {
        case BluetoothTypes.KEEPING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.BLUETOOTH_KEEP;
          const barrierLabel = "bluetooth keeping barrier";
          const deviceType =
            HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR;
          const bluetoothStatus =
            HMSAwarenessBarrierModule.BluetoothStatus_CONNECTED;
          const BluetoothKeepingReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            deviceType: deviceType,
            bluetoothStatus: bluetoothStatus,
          };
          BluetoothBarrierReq = BluetoothKeepingReq;
          break;
        }
        case BluetoothTypes.CONNECTING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.BLUETOOTH_CONNECTING;
          const barrierLabel = "bluetooth connecting barrier";
          const deviceType =
            HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR;
          const BluetoothConnectingReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            deviceType: deviceType,
          };
          BluetoothBarrierReq = BluetoothConnectingReq;
          break;
        }
        case BluetoothTypes.DISCONNECTING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.BLUETOOTH_DISCONNECTING;
          const barrierLabel = "bluetooth disconnecting barrier";
          const deviceType =
            HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR;
          const BluetoothDisconnectingReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            deviceType: deviceType,
          };
          BluetoothBarrierReq = BluetoothDisconnectingReq;
          break;
        }
      }

      var UpdateBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
        HMSAwarenessBarrierModule.EVENT_BLUETOOTH,
        BluetoothBarrierReq
      );
      this.props.changeStateProgress(false);
      alert(JSON.stringify(UpdateBarrierRes));
    } catch (e) {
      this.props.changeStateProgress(false);
      alert("error::" + JSON.stringify(e));
    }
  }

  async headsetBarrier(type) {
    try {
      this.props.changeStateProgress(true);
      var HeadsetBarrierReq;

      switch (type) {
        case HeadsetTypes.KEEPING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.EVENT_HEADSET_KEEPING;
          const barrierLabel = "headset keeping barrier";
          const headsetStatus =
            HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED;
          const HeadsetKeepingReq = {
            barrierReceiverAction: barrierReceiverAction,
            headsetStatus: headsetStatus,
            barrierLabel: barrierLabel,
          };
          HeadsetBarrierReq = HeadsetKeepingReq;
          break;
        }
        case HeadsetTypes.CONNECTING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.EVENT_HEADSET_CONNECTING;
          const barrierLabel = "headset connecting barrier";
          const HeadsetConnectingReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          HeadsetBarrierReq = HeadsetConnectingReq;
          break;
        }
        case HeadsetTypes.DISCONNECTING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.EVENT_HEADSET_DISCONNECTING;
          const barrierLabel = "headset disconnecting barrier";
          const HeadsetDisonnectingReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          HeadsetBarrierReq = HeadsetDisonnectingReq;
          break;
        }
      }

      var UpdateBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
        HMSAwarenessBarrierModule.EVENT_HEADSET,
        HeadsetBarrierReq
      );
      this.props.changeStateProgress(false);
      alert(JSON.stringify(UpdateBarrierRes));
    } catch (e) {
      this.props.changeStateProgress(false);
      alert("error::" + JSON.stringify(e));
    }
  }

  async locationBarrier(type) {
    try {
      this.props.changeStateProgress(true);
      var LocationBarrierReq;

      switch (type) {
        case LocationTypes.ENTER: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.LOCATION_ENTER;
          const barrierLabel = "location enter barrier";
          const LocationEnterReq = {
            latitude: 36.3148328,
            longitude: 30.1663369,
            radius: 10000,
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          LocationBarrierReq = LocationEnterReq;
          break;
        }
        case LocationTypes.STAY: {
          const barrierReceiverAction = HMSAwarenessBarrierModule.LOCATION_STAY;
          const barrierLabel = "location stay barrier";
          const LocationStayReq = {
            latitude: 36.3148328,
            longitude: 30.1663369,
            radius: 10000,
            timeOfDuration: 10000,
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          LocationBarrierReq = LocationStayReq;
          break;
        }
        case LocationTypes.EXIT: {
          const barrierReceiverAction = HMSAwarenessBarrierModule.LOCATION_EXIT;
          const barrierLabel = "location exit barrier";
          const LocationExitReq = {
            latitude: 36.3148328,
            longitude: 30.1663369,
            radius: 10000,
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          LocationBarrierReq = LocationExitReq;
          break;
        }
      }

      var LocationBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
        HMSAwarenessBarrierModule.EVENT_LOCATION,
        LocationBarrierReq
      );
      this.props.changeStateProgress(false);
      alert(JSON.stringify(LocationBarrierRes));
    } catch (e) {
      this.props.changeStateProgress(false);
      alert("error::" + JSON.stringify(e));
    }
  }

  async screenBarrier(type) {
    try {
      this.props.changeStateProgress(true);
      var ScreenBarrierReq;

      switch (type) {
        case ScreenTypes.SCREEN_KEEPING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.SCREEN_KEEPING;
          const screenStatus = HMSAwarenessBarrierModule.ScreenStatus_UNLOCK;
          const barrierLabel = "SCREEN_ACTION_KEEPING";
          const ScreenKeepingReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            screenStatus: screenStatus,
          };
          ScreenBarrierReq = ScreenKeepingReq;
          break;
        }
        case ScreenTypes.SCREEN_ON: {
          const barrierReceiverAction = HMSAwarenessBarrierModule.SCREEN_ON;
          const barrierLabel = "SCREEN_ACTION_ON";
          const ScreenOnReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          ScreenBarrierReq = ScreenOnReq;
          break;
        }
        case ScreenTypes.SCREEN_OFF: {
          const barrierReceiverAction = HMSAwarenessBarrierModule.SCREEN_OFF;
          const barrierLabel = "SCREEN_ACTION_OFF";
          const ScreenOffReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          ScreenBarrierReq = ScreenOffReq;
          break;
        }
        case ScreenTypes.SCREEN_UNLOCK: {
          const barrierReceiverAction = HMSAwarenessBarrierModule.SCREEN_UNLOCK;
          const barrierLabel = "SCREEN_ACTION_UNLOCK";
          const ScreenUnlockReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          ScreenBarrierReq = ScreenUnlockReq;
          break;
        }
      }

      var ScreenBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
        HMSAwarenessBarrierModule.EVENT_SCREEN,
        ScreenBarrierReq
      );
      this.props.changeStateProgress(false);
      alert(JSON.stringify(ScreenBarrierRes));
    } catch (e) {
      this.props.changeStateProgress(false);
      alert("error" + JSON.stringify(e));
    }
  }

  async timeBarrier(type) {
    try {
      this.props.changeStateProgress(true);
      var TimeBarrierReq;

      switch (type) {
        case TimeTypes.SUNRISE_OR_SUNSET_PERIOD: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.TIME_IN_SUNRISE_OR_SUNSET_PERIOD;
          const barrierLabel = "sunrice or sunset period barrier";
          const timeInstant = HMSAwarenessBarrierModule.TimeBarrier_SUNSET_CODE;
          const TimeSunriseandSunSetPerReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            timeInstant: timeInstant,
            startTimeOffset: -3600000.0,
            stopTimeOffset: 36000000.0,
          };
          TimeBarrierReq = TimeSunriseandSunSetPerReq;
          break;
        }
        case TimeTypes.DURING_PERIOD_OF_DAY: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_DAY;
          const barrierLabel = "period of day barrier";
          const TimeDuringPerOfDayReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            startTimeOfDay: 39600000,
            stopTimeOfDay: 43200000,
            timeZoneId: "Europe/Istanbul",
          };
          TimeBarrierReq = TimeDuringPerOfDayReq;
          break;
        }
        case TimeTypes.DURING_TIME_PERIOD: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.TIME_DURING_TIME_PERIOD;
          const barrierLabel = "time period barrier";
          const TimeDuringTimePerReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            startTimeStamp: 1607515517229.0,
            stopTimeStamp: 1607515527229.0,
          };
          TimeBarrierReq = TimeDuringTimePerReq;
          break;
        }
        case TimeTypes.PERIOD_OF_WEEK: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_WEEK;
          const barrierLabel = "period of week barrier";
          const TimePerOfWeekReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            dayOfWeek: HMSAwarenessBarrierModule.TimeBarrier_MONDAY_CODE,
            startTimeOfSpecifiedDay: 32400000,
            stopTimeOfSpecifiedDay: 36000000,
            timeZoneId: "Europe/Istanbul",
          };
          TimeBarrierReq = TimePerOfWeekReq;
          break;
        }
        case TimeTypes.IN_TIME_CATEGORY: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.TIME_IN_TIME_CATEGORY;
          const barrierLabel = "in timecategory barrier";
          const timeCategory =
            HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_WEEKEND;
          const TimeInTimeCategoryReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            timeCategory: timeCategory,
          };
          TimeBarrierReq = TimeInTimeCategoryReq;
          break;
        }
      }

      var TimeBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
        HMSAwarenessBarrierModule.EVENT_TIME,
        TimeBarrierReq
      );
      this.props.changeStateProgress(false);
      alert(JSON.stringify(TimeBarrierRes));
    } catch (e) {
      this.props.changeStateProgress(false);
      alert("error::" + JSON.stringify(e));
    }
  }

  async wifiBarrier(type) {
    try {
      this.props.changeStateProgress(true);
      var WifiBarrierReq;

      switch (type) {
        case WifiTypes.WIFI_KEEPING: {
          const barrierReceiverAction = HMSAwarenessBarrierModule.WIFI_KEEPING;
          const barrierLabel = "wifi keeping barrier";
          const wifiStatus = HMSAwarenessBarrierModule.WifiStatus_CONNECTED;
          const WifiKeepingReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            wifiStatus: wifiStatus,
          };
          WifiBarrierReq = WifiKeepingReq;
          break;
        }
        case WifiTypes.WIFI_KEEPING_WITH_BSSID: {
          const barrierReceiverAction = HMSAwarenessBarrierModule.WIFI_KEEPING;
          const barrierLabel = "wifi keeping with bssid barrier";
          const wifiStatus = HMSAwarenessBarrierModule.WifiStatus_CONNECTED;
          const ssid = "TTNET_AirTies_Air5650_5353";
          const bssid = "18:28:61:f8:99:05";
          const WifiKeepingWithBssidReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            wifiStatus: wifiStatus,
            bssid: bssid,
            ssid: ssid,
          };
          WifiBarrierReq = WifiKeepingWithBssidReq;
          break;
        }
        case WifiTypes.WIFI_CONNECTING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.WIFI_CONNECTING;
          const barrierLabel = "wifi connecting barrier";
          const WifiConnectingReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          WifiBarrierReq = WifiConnectingReq;
          break;
        }
        case WifiTypes.WIFI_CONNECTING_WITH_BSSID: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.WIFI_CONNECTING;
          const barrierLabel = "wifi connecting with bssid barrier";
          const ssid = "TTNET_AirTies_Air5650_5353";
          const bssid = "18:28:61:f8:99:05";
          const WifiConnectingBssidReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            bssid: bssid,
            ssid: ssid,
          };
          WifiBarrierReq = WifiConnectingBssidReq;
          break;
        }
        case WifiTypes.WIFI_DISCONNECTING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.WIFI_DISCONNECTING;
          const barrierLabel = "wifi disconnecting barrier";
          const WifiDisconnectingReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          WifiBarrierReq = WifiDisconnectingReq;
          break;
        }
        case WifiTypes.WIFI_DISCONNECTING_WITH_BSSID: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.WIFI_DISCONNECTING;
          const barrierLabel = "wifi disconnecting with bssid barrier";
          const ssid = "TTNET_AirTies_Air5650_5353";
          const bssid = "18:28:61:f8:99:05";
          const WifiDisconnectingBssidReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
            bssid: bssid,
            ssid: ssid,
          };
          WifiBarrierReq = WifiDisconnectingBssidReq;
          break;
        }
        case WifiTypes.WIFI_ENABLING: {
          const barrierReceiverAction = HMSAwarenessBarrierModule.WIFI_ENABLING;
          const barrierLabel = "wifi enabling barrier";
          const WifiEnablingReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          WifiBarrierReq = WifiEnablingReq;
          break;
        }
        case WifiTypes.WIFI_DISABLING: {
          const barrierReceiverAction =
            HMSAwarenessBarrierModule.WIFI_DISABLING;
          const barrierLabel = "wifi disabling barrier";
          const WifiDisablingReq = {
            barrierReceiverAction: barrierReceiverAction,
            barrierLabel: barrierLabel,
          };
          WifiBarrierReq = WifiDisablingReq;
          break;
        }
      }
      var WifiBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
        HMSAwarenessBarrierModule.EVENT_WIFI,
        WifiBarrierReq
      );
      this.props.changeStateProgress(false);
      alert(JSON.stringify(WifiBarrierRes));
    } catch (e) {
      this.props.changeStateProgress(false);
      alert("error");
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.updateBarrierTitle}>
          Ambient Light Barrier Methods
        </Text>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.ambientLightBarrier(AmbientLightTypes.ABOVE)}
        >
          <Text style={styles.text}>Ambient Light Above</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.ambientLightBarrier(AmbientLightTypes.BELOW)}
        >
          <Text style={styles.text}>Ambient Light Belove</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.ambientLightBarrier(AmbientLightTypes.RANGE)}
        >
          <Text style={styles.text}>Ambient Light Range</Text>
        </TouchableOpacity>

        <Text style={styles.updateBarrierTitle}>Beacon Barrier Methods</Text>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.beaconBarrier(BeaconTypes.DISCOVER)}
        >
          <Text style={styles.text}>Beacon Discover</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.beaconBarrier(BeaconTypes.KEEP)}
        >
          <Text style={styles.text}>Beacon Keep</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.beaconBarrier(BeaconTypes.MISSED)}
        >
          <Text style={styles.text}>Beacon Missed</Text>
        </TouchableOpacity>

        <Text style={styles.updateBarrierTitle}>Behavior Barrier Methods</Text>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.behaviorBarrier(BehaviorTypes.KEEPING)}
        >
          <Text style={styles.text}>Behavior Keeping</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.behaviorBarrier(BehaviorTypes.BEGINNING)}
        >
          <Text style={styles.text}>Behavior Beginning</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.behaviorBarrier(BehaviorTypes.ENDING)}
        >
          <Text style={styles.text}>Behavior Ending</Text>
        </TouchableOpacity>

        <Text style={styles.updateBarrierTitle}>Bluetooth Barrier Methods</Text>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.bluetoothBarrier(BluetoothTypes.KEEPING)}
        >
          <Text style={styles.text}>Bluetooth Keeping</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.bluetoothBarrier(BluetoothTypes.CONNECTING)}
        >
          <Text style={styles.text}>Bluetooth Connecting</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.bluetoothBarrier(BluetoothTypes.DISCONNECTING)}
        >
          <Text style={styles.text}>Bluetooth Disconnecting</Text>
        </TouchableOpacity>

        <Text style={styles.updateBarrierTitle}>Headset Barrier Methods</Text>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.headsetBarrier(HeadsetTypes.KEEPING)}
        >
          <Text style={styles.text}>Headset Keeping</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.headsetBarrier(HeadsetTypes.CONNECTING)}
        >
          <Text style={styles.text}>Headset Connecting</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.headsetBarrier(HeadsetTypes.DISCONNECTING)}
        >
          <Text style={styles.text}>Headset Disconnecting</Text>
        </TouchableOpacity>

        <Text style={styles.updateBarrierTitle}>Location Barrier Methods</Text>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.locationBarrier(LocationTypes.ENTER)}
        >
          <Text style={styles.text}>Location Enter</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.locationBarrier(LocationTypes.EXIT)}
        >
          <Text style={styles.text}>Location Exit</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.locationBarrier(LocationTypes.STAY)}
        >
          <Text style={styles.text}>Location Stay</Text>
        </TouchableOpacity>

        <Text style={styles.updateBarrierTitle}>Screen Barrier Methods</Text>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.screenBarrier(ScreenTypes.SCREEN_KEEPING)}
        >
          <Text style={styles.text}>Screen Keeping</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.screenBarrier(ScreenTypes.SCREEN_ON)}
        >
          <Text style={styles.text}>Screen On</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.screenBarrier(ScreenTypes.SCREEN_OFF)}
        >
          <Text style={styles.text}>Screen Off</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.screenBarrier(ScreenTypes.SCREEN_UNLOCK)}
        >
          <Text style={styles.text}>Screen Unlock</Text>
        </TouchableOpacity>

        <Text style={styles.updateBarrierTitle}>Time Barrier Methods</Text>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.timeBarrier(TimeTypes.SUNRISE_OR_SUNSET_PERIOD)}
        >
          <Text style={styles.text}>Sunset or Sunrise Barrier</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.timeBarrier(TimeTypes.DURING_PERIOD_OF_DAY)}
        >
          <Text style={styles.text}>During Period Of Day Barrier</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.timeBarrier(TimeTypes.DURING_TIME_PERIOD)}
        >
          <Text style={styles.text}>During Time Period Barrier</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.timeBarrier(TimeTypes.PERIOD_OF_WEEK)}
        >
          <Text style={styles.text}>During Period Of Week Barrier</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.timeBarrier(TimeTypes.IN_TIME_CATEGORY)}
        >
          <Text style={styles.text}>In Time Category</Text>
        </TouchableOpacity>

        <Text style={styles.updateBarrierTitle}>Wifi Barrier Methods</Text>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.wifiBarrier(WifiTypes.WIFI_KEEPING)}
        >
          <Text style={styles.text}>Wifi Keeping</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.wifiBarrier(WifiTypes.WIFI_KEEPING_WITH_BSSID)}
        >
          <Text style={styles.text}>Wifi Keeping with bssi and ssid</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.wifiBarrier(WifiTypes.WIFI_CONNECTING)}
        >
          <Text style={styles.text}>Wifi Connecting</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.wifiBarrier(WifiTypes.WIFI_CONNECTING_WITH_BSSID)}
        >
          <Text style={styles.text}>Wifi Connecting with bssid and ssid</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.wifiBarrier(WifiTypes.WIFI_DISCONNECTING)}
        >
          <Text style={styles.text}>Wifi Disconnecting</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() =>
            this.wifiBarrier(WifiTypes.WIFI_DISCONNECTING_WITH_BSSID)
          }
        >
          <Text style={styles.text}>
            Wifi Disconnecting with bssid and ssid{" "}
          </Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.wifiBarrier(WifiTypes.WIFI_ENABLING)}
        >
          <Text style={styles.text}>Wifi Enabling</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.btn}
          onPress={() => this.wifiBarrier(WifiTypes.WIFI_DISABLING)}
        >
          <Text style={styles.text}>Wifi Disabling</Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    justifyContent: "center",
    alignSelf: "center",
  },
  updateBarrierTitle: {
    color: "black",
    fontSize: 13,
    fontStyle: "italic",
    fontWeight: "bold",
    marginTop: 13,
  },
  btn: {
    marginTop: 15,
    width: buttonWidth,
    height: buttonHeight,
    justifyContent: "center",
    alignSelf: "center",
    borderRadius: 10,
    marginLeft: 5,
    marginRight: 5,
    borderColor: green,
    borderWidth: 1.5,
  },
  text: {
    fontSize: 14,
    color: darkJungleGreen,
    textAlign: "center",
  },
});
