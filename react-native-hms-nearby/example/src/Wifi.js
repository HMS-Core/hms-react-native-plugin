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
import {
  Text,
  View,
  TouchableOpacity,
  Switch,
  NativeEventEmitter,
  ToastAndroid,
  Alert,
} from "react-native";
import {
  HMSWifiShare,
  HMSNearbyApplication,
} from "@hmscore/react-native-hms-nearby";
import { styles } from "./Styles";
import { messageResult } from "./Converter.js";

export default class Wifi extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      wifiType: false,
      isWifiShareStarted: false,
      tintColor: "black",
    };
  }

  componentDidMount() {
    this.eventEmitter = new NativeEventEmitter(HMSWifiShare);

    this.eventEmitter.addListener(HMSWifiShare.WIFI_ON_FOUND, (event) => {
      console.log(event);
      this.setState({ endpointId: event.endpointId });
      ToastAndroid.showWithGravity(
        "Wifi Found",
        ToastAndroid.SHORT,
        ToastAndroid.CENTER
      );
      this.shareWifiConfig();
    });

    this.eventEmitter.addListener(HMSWifiShare.WIFI_ON_LOST, (event) => {
      console.log(event);
      this.setState({ endpointId: "" });
      ToastAndroid.showWithGravity(
        "Wifi Lost",
        ToastAndroid.SHORT,
        ToastAndroid.CENTER
      );
    });

    this.eventEmitter.addListener(
      HMSWifiShare.WIFI_ON_FETCH_AUTH_CODE,
      (event) => {
        console.log(event);
        this.setState({ endpointId: event.endpointId });
        Alert.alert(
          "Verification",
          "Verify auth codes with the other phone. Code :" + event.authCode,
          [
            {
              text: "OK",
              onPress: () => console.log("OK"),
            },
          ],
          { cancelable: true }
        );
      }
    );

    this.eventEmitter.addListener(HMSWifiShare.WIFI_ON_RESULT, (event) => {
      console.log(event);
      if (event.statusCode == HMSNearbyApplication.SUCCESS) {
        ToastAndroid.showWithGravity(
          "Wifi Connection Established",
          ToastAndroid.SHORT,
          ToastAndroid.CENTER
        );
      } else {
        ToastAndroid.showWithGravity(
          "Wifi Connection Failed",
          ToastAndroid.SHORT,
          ToastAndroid.CENTER
        );
      }
    });
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSWifiShare.WIFI_ON_FOUND);
    this.eventEmitter.removeAllListeners(HMSWifiShare.WIFI_ON_LOST);
    this.eventEmitter.removeAllListeners(HMSWifiShare.WIFI_ON_FETCH_AUTH_CODE);
    this.eventEmitter.removeAllListeners(HMSWifiShare.WIFI_ON_RESULT);

    if (this.state.isWifiShareStarted) {
      this.stopWifiShare();
    }
  }

  async startWifiShare(isShare) {
    try {
      this.setState({ tintColor: "black" });
      var result = await HMSWifiShare.startWifiShare(
        isShare ? HMSWifiShare.SET : HMSWifiShare.SHARE
      );
      console.log(result);
      if (result.status == HMSNearbyApplication.SUCCESS) {
        ToastAndroid.showWithGravity(
          isShare ? "Searching For Wifi..." : "Share Started...",
          ToastAndroid.SHORT,
          ToastAndroid.CENTER
        );
        this.setState({ isWifiShareStarted: true, tintColor: "green" });
      } else {
        ToastAndroid.showWithGravity(
          result.message,
          ToastAndroid.SHORT,
          ToastAndroid.CENTER
        );
        this.stopWifiShare().then(() =>
          this.setState({ isWifiShareStarted: false, tintColor: "black" })
        );
      }
    } catch (e) {
      console.log(e);
    }
  }

  async stopWifiShare() {
    try {
      var result = await HMSWifiShare.stopWifiShare();
      messageResult(result, "Wifi Share Stopped");
    } catch (e) {
      console.log(e);
    }
  }

  async shareWifiConfig() {
    try {
      var result = await HMSWifiShare.shareWifiConfig(this.state.endpointId);
      console.log(result);
      messageResult(result, "Configuration Shared");
    } catch (e) {
      console.log(e);
    }
  }

  toggleSwitch = () => {
    this.setState({
      wifiType: !this.state.wifiType,
    });
  };

  handleWifiShare = () => {
    if (this.state.isWifiShareStarted) {
      this.stopWifiShare().then(() =>
        this.setState({ isWifiShareStarted: false, tintColor: "black" })
      );
    } else {
      this.startWifiShare(this.state.wifiType);
    }
  };

  render() {
    return (
      <View style={styles.baseView}>
        <View style={styles.toolbar}>
          <View style={styles.viewdividedtwo}>
            <View style={styles.halfItem1}>
              <Text style={styles.titleToolbar}>
                {this.state.wifiType ? "Request For Wifi" : "Share Your Wifi"}
              </Text>
            </View>
            <View style={styles.halfItem2}>
              <Switch
                style={{ alignSelf: "center" }}
                trackColor={{ false: "#e5e5e5", true: "#50AF52" }}
                thumbColor={this.state.wifiType ? "#ffffff" : "#ffffff"}
                onValueChange={this.toggleSwitch.bind(this)}
                value={this.state.wifiType}
                disabled={this.state.isWifiShareStarted}
              />
            </View>
          </View>
        </View>

        <View
          style={{
            marginTop: 20,
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <TouchableOpacity onPress={() => this.handleWifiShare()}>
            <Text
              style={{
                color: this.state.tintColor,
                fontSize: 48,
                fontWeight: "bold",
              }}
            >
              ACTIVATE
            </Text>
          </TouchableOpacity>
        </View>

        <Text style={styles.h1}>Instructions</Text>
        <Text
          style={{
            textAlign: "center",
            fontWeight: "bold",
            fontSize: 12,
            marginTop: 10,
          }}
        >
          Phone 1 : Shares wifi - Switch not enabled{"\n"}
          Phone 2 : Requests wifi - Switch is enabled{"\n\n"}
          Phone 1 starts sharing by pressing ACTIVE button{"\n"}
          Phone 2 starts requesting by pressing ACTIVE button{"\n"}
          If Phone 1 founds Phone 2, it shares wifi config{"\n"}
          Then on UI, app shows alert dialog to check auth code{"\n"}
          If Phone 1 approves the auth code, Phone 2 connects to wifi
          successfully{"\n"}
        </Text>
      </View>
    );
  }
}
