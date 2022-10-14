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

const ssid = "TTNET_AirTies_Air5650_5353";
const bssid = "18:28:61:f8:99:05";

export default class BaseComponent extends React.Component {
  async ambientLightBarrier(type) {
    try {
      this.props.changeStateProgress(true);
      var AmbientLightBarrierReq;

      switch (type) {
        case AmbientLightTypes.ABOVE: {
          const AmbientLightAboveReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.AMBIENTLIGHT_ABOVE,
            minLightIntensity: 500.0,
            barrierLabel: "light above barrier",
          };
          AmbientLightBarrierReq = AmbientLightAboveReq;
          break;
        }
        case AmbientLightTypes.BELOW: {
          const AmbientLightBelowReq = {
            barrierReceiverAction:  HMSAwarenessBarrierModule.AMBIENTLIGHT_BELOW,
            barrierLabel: "light below barrier",
            maxLightIntensity: 2500.0,
          };
          AmbientLightBarrierReq = AmbientLightBelowReq;
          break;
        }
        case AmbientLightTypes.RANGE: {
          const AmbientLightRangeReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.AMBIENTLIGHT_RANGE,
            barrierLabel: "light range barrier",
            minLightIntensity: 500.0,
            maxLightIntensity: 2500.0,
          };
          AmbientLightBarrierReq = AmbientLightRangeReq;
          break;
        }
      }
      const UpdateBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
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
          const BeaconDiscoverReq = {
            barrierReceiverAction:  HMSAwarenessBarrierModule.BEACON_DISCOVER,
            beaconArray: beaconArray,
            barrierLabel: "discover beacon barrier",
          };
          BeaconBarrierReq = BeaconDiscoverReq;
          break;
        }
        case BeaconTypes.KEEP: {
          const BeaconKeepReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.BEACON_KEEP,
            beaconArray: beaconArray,
            barrierLabel: "keep beacon barrier",
          };
          BeaconBarrierReq = BeaconKeepReq;
          break;
        }
        case BeaconTypes.MISSED: {
          const BeaconMissedReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.BEACON_MISSED,
            beaconArray: beaconArray,
            barrierLabel: "missed beacon barrier",
          };
          BeaconBarrierReq = BeaconMissedReq;
          break;
        }
      }

      const UpdateBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
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
          const behaviorTypes = [];
          behaviorTypes.push(
            HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_STILL
          );
          const BehaviorKeepingReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.BEHAVIOR_KEEPING,
            behaviorTypes: behaviorTypes,
            barrierLabel: "behavior keeping barrier",
          };
          BehaviorBarrierReq = BehaviorKeepingReq;
          break;
        }
        case BehaviorTypes.BEGINNING: {
          const behaviorTypes = [];
          behaviorTypes.push(
            HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_WALKING
          );
          behaviorTypes.push(
            HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_RUNNING
          );
          const BehaviorBeginningReq = {
            barrierReceiverAction:  HMSAwarenessBarrierModule.BEHAVIOR_BEGINNING,
            behaviorTypes: behaviorTypes,
            barrierLabel: "behavior beginning barrier",
          };
          BehaviorBarrierReq = BehaviorBeginningReq;
          break;
        }
        case BehaviorTypes.ENDING: {
          const behaviorTypes = [];
          behaviorTypes.push(
            HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_WALKING
          );
          const BehaviorEndingReq = {
            barrierReceiverAction:  HMSAwarenessBarrierModule.BEHAVIOR_ENDING,
            behaviorTypes: behaviorTypes,
            barrierLabel: "behavior ending barrier",
          };
          BehaviorBarrierReq = BehaviorEndingReq;
          break;
        }
      }

      const UpdateBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
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
          const BluetoothKeepingReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.BLUETOOTH_KEEP,
            barrierLabel: "bluetooth keeping barrier",
            deviceType: HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR,
            bluetoothStatus: HMSAwarenessBarrierModule.BluetoothStatus_CONNECTED,
          };
          BluetoothBarrierReq = BluetoothKeepingReq;
          break;
        }
        case BluetoothTypes.CONNECTING: {
          const BluetoothConnectingReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.BLUETOOTH_CONNECTING,
            barrierLabel: "bluetooth connecting barrier",
            deviceType: HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR,
          };
          BluetoothBarrierReq = BluetoothConnectingReq;
          break;
        }
        case BluetoothTypes.DISCONNECTING: {
          const BluetoothDisconnectingReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.BLUETOOTH_DISCONNECTING,
            barrierLabel: "bluetooth disconnecting barrier",
            deviceType: HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR,
          };
          BluetoothBarrierReq = BluetoothDisconnectingReq;
          break;
        }
      }

      const UpdateBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
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
          const HeadsetKeepingReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.EVENT_HEADSET_KEEPING,
            headsetStatus: HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED,
            barrierLabel: "headset keeping barrier",
          };
          HeadsetBarrierReq = HeadsetKeepingReq;
          break;
        }
        case HeadsetTypes.CONNECTING: {
          const HeadsetConnectingReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.EVENT_HEADSET_CONNECTING,
            barrierLabel: "headset connecting barrier",
          };
          HeadsetBarrierReq = HeadsetConnectingReq;
          break;
        }
        case HeadsetTypes.DISCONNECTING: {
          const HeadsetDisonnectingReq = {
            barrierReceiverAction:  HMSAwarenessBarrierModule.EVENT_HEADSET_DISCONNECTING,
            barrierLabel: "headset disconnecting barrier",
          };
          HeadsetBarrierReq = HeadsetDisonnectingReq;
          break;
        }
      }

      const UpdateBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
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
          const LocationEnterReq = {
            latitude: 36.3148328,
            longitude: 30.1663369,
            radius: 10000,
            barrierReceiverAction: HMSAwarenessBarrierModule.LOCATION_ENTER,
            barrierLabel: "location enter barrier",
          };
          LocationBarrierReq = LocationEnterReq;
          break;
        }
        case LocationTypes.STAY: {
          const LocationStayReq = {
            latitude: 36.3148328,
            longitude: 30.1663369,
            radius: 10000,
            timeOfDuration: 10000,
            barrierReceiverAction: HMSAwarenessBarrierModule.LOCATION_STAY,
            barrierLabel: "location stay barrier",
          };
          LocationBarrierReq = LocationStayReq;
          break;
        }
        case LocationTypes.EXIT: {
          const LocationExitReq = {
            latitude: 36.3148328,
            longitude: 30.1663369,
            radius: 10000,
            barrierReceiverAction: HMSAwarenessBarrierModule.LOCATION_EXIT,
            barrierLabel: "location exit barrier",
          };
          LocationBarrierReq = LocationExitReq;
          break;
        }
      }

      const LocationBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
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
          const ScreenKeepingReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.SCREEN_KEEPING,
            barrierLabel: "SCREEN_ACTION_KEEPING",
            screenStatus: HMSAwarenessBarrierModule.ScreenStatus_UNLOCK,
          };
          ScreenBarrierReq = ScreenKeepingReq;
          break;
        }
        case ScreenTypes.SCREEN_ON: {
          const ScreenOnReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.SCREEN_ON,
            barrierLabel: "SCREEN_ACTION_ON",
          };
          ScreenBarrierReq = ScreenOnReq;
          break;
        }
        case ScreenTypes.SCREEN_OFF: {
          const ScreenOffReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.SCREEN_OFF,
            barrierLabel: "SCREEN_ACTION_OFF",
          };
          ScreenBarrierReq = ScreenOffReq;
          break;
        }
        case ScreenTypes.SCREEN_UNLOCK: {
          const ScreenUnlockReq = {
            barrierReceiverAction: HMSAwarenessBarrierModule.SCREEN_UNLOCK,
            barrierLabel:  "SCREEN_ACTION_UNLOCK",
          };
          ScreenBarrierReq = ScreenUnlockReq;
          break;
        }
      }

      const ScreenBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
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
      var TimeBarrierReq = this.createTimeReqObj(type);
      const TimeBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
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
      var WifiBarrierReq = this.createWifiReqObj(type);
      const WifiBarrierRes = await HMSAwarenessBarrierModule.updateBarrier(
        HMSAwarenessBarrierModule.EVENT_WIFI,
        WifiBarrierReq
      );
      this.props.changeStateProgress(false);
      alert(JSON.stringify(WifiBarrierRes));
    } catch (e) {
      this.props.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  createWifiReqObj(type) {
    var WifiBarrierReq;
    switch (type) {
      case WifiTypes.WIFI_KEEPING: {
        const WifiKeepingReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.WIFI_KEEPING,
          barrierLabel: "wifi keeping barrier",
          wifiStatus: HMSAwarenessBarrierModule.WifiStatus_CONNECTED,
        };
        WifiBarrierReq = WifiKeepingReq;
        break;
      }
      case WifiTypes.WIFI_KEEPING_WITH_BSSID: {
        const WifiKeepingWithBssidReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.WIFI_KEEPING,
          barrierLabel: "wifi keeping with bssid barrier",
          wifiStatus: HMSAwarenessBarrierModule.WifiStatus_CONNECTED,
          bssid: bssid,
          ssid: ssid,
        };
        WifiBarrierReq = WifiKeepingWithBssidReq;
        break;
      }
      case WifiTypes.WIFI_CONNECTING: {
        const WifiConnectingReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.WIFI_CONNECTING,
          barrierLabel: "wifi connecting barrier",
        };
        WifiBarrierReq = WifiConnectingReq;
        break;
      }
      case WifiTypes.WIFI_CONNECTING_WITH_BSSID: {
        const WifiConnectingBssidReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.WIFI_CONNECTING,
          barrierLabel: "wifi connecting with bssid barrier",
          bssid: bssid,
          ssid: ssid,
        };
        WifiBarrierReq = WifiConnectingBssidReq;
        break;
      }
      case WifiTypes.WIFI_DISCONNECTING: {
        const WifiDisconnectingReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.WIFI_DISCONNECTING,
          barrierLabel: "wifi disconnecting barrier",
        };
        WifiBarrierReq = WifiDisconnectingReq;
        break;
      }
      case WifiTypes.WIFI_DISCONNECTING_WITH_BSSID: {
        const WifiDisconnectingBssidReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.WIFI_DISCONNECTING,
          barrierLabel: "wifi disconnecting with bssid barrier",
          bssid: bssid,
          ssid: ssid,
        };
        WifiBarrierReq = WifiDisconnectingBssidReq;
        break;
      }
      case WifiTypes.WIFI_ENABLING: {
        const WifiEnablingReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.WIFI_ENABLING,
          barrierLabel: "wifi enabling barrier",
        };
        WifiBarrierReq = WifiEnablingReq;
        break;
      }
      case WifiTypes.WIFI_DISABLING: {
        const WifiDisablingReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.WIFI_DISABLING,
          barrierLabel: "wifi disabling barrier",
        };
        WifiBarrierReq = WifiDisablingReq;
        break;
      }
    }
    return WifiBarrierReq;
  }
  createTimeReqObj(type) {
    var TimeBarrierReq;
    switch (type) {
      case TimeTypes.SUNRISE_OR_SUNSET_PERIOD: {
        const TimeSunriseandSunSetPerReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.TIME_IN_SUNRISE_OR_SUNSET_PERIOD,
          barrierLabel: "sunrice or sunset period barrier",
          timeInstant: HMSAwarenessBarrierModule.TimeBarrier_SUNSET_CODE,
          startTimeOffset: -3600000.0,
          stopTimeOffset: 36000000.0,
        };
        TimeBarrierReq = TimeSunriseandSunSetPerReq;
        break;
      }
      case TimeTypes.DURING_PERIOD_OF_DAY: {
        const TimeDuringPerOfDayReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_DAY,
          barrierLabel: "period of day barrier",
          startTimeOfDay: 39600000,
          stopTimeOfDay: 43200000,
          timeZoneId: "Europe/Istanbul",
        };
        TimeBarrierReq = TimeDuringPerOfDayReq;
        break;
      }
      case TimeTypes.DURING_TIME_PERIOD: {
        const TimeDuringTimePerReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.TIME_DURING_TIME_PERIOD,
          barrierLabel: "time period barrier",
          startTimeStamp: 1607515517229.0,
          stopTimeStamp: 1607515527229.0,
        };
        TimeBarrierReq = TimeDuringTimePerReq;
        break;
      }
      case TimeTypes.PERIOD_OF_WEEK: {
        const TimePerOfWeekReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_WEEK,
          barrierLabel: "period of week barrier",
          dayOfWeek: HMSAwarenessBarrierModule.TimeBarrier_MONDAY_CODE,
          startTimeOfSpecifiedDay: 32400000,
          stopTimeOfSpecifiedDay: 36000000,
          timeZoneId: "Europe/Istanbul",
        };
        TimeBarrierReq = TimePerOfWeekReq;
        break;
      }
      case TimeTypes.IN_TIME_CATEGORY: {
        const TimeInTimeCategoryReq = {
          barrierReceiverAction: HMSAwarenessBarrierModule.TIME_IN_TIME_CATEGORY,
          barrierLabel: "in timecategory barrier",
          timeCategory: HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_WEEKEND,
        };
        TimeBarrierReq = TimeInTimeCategoryReq;
        break;
      }
    }
    return TimeBarrierReq
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
