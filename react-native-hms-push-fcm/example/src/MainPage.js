/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

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

import React, { Component } from "react";
import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  ToastAndroid,
} from "react-native";

import HMSPushFCM from "@hmscore/react-native-hms-push-fcm";

import {
  HmsPushEvent,
  HmsPushInstanceId,
} from "@hmscore/react-native-hms-push";

import { styles } from "./styles";

export default class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      log: "",
      topic: ""
    };

    this.componentDidMount = this.componentDidMount.bind(this);
  }

  componentDidMount() {
    this.onTokenReceivedListener = HmsPushEvent.onTokenReceived((result) => {
      this.log("onTokenReceived", result);
    });

    this.onTokenErrorListener = HmsPushEvent.onTokenError((result) => {
      this.log("onTokenError", result);
    });

    this.onMultiSenderTokenReceivedListener = HmsPushEvent.onMultiSenderTokenReceived(
      (result) => {
        this.log("onMultiSenderTokenReceived", result);
      }
    );

    this.onMultiSenderTokenErrorListener = HmsPushEvent.onMultiSenderTokenError(
      (result) => {
        this.log("onMultiSenderTokenError", result);
      }
    );
  }

  componentWillUnmount() {
    this.onTokenReceivedListener.remove();
    this.onTokenErrorListener.remove();
    this.onMultiSenderTokenReceivedListener.remove();
    this.onMultiSenderTokenErrorListener.remove();
  }

  log(tag, msg) {
    this.setState(
      {
        log: `[${tag}]: ${JSON.stringify(msg, "\n", 4)} \n ${this.state.log}`,
      },
      this.toast(JSON.stringify(msg, "\n", 4))
    );

    console.log(tag, msg);
  }

  toast = (msg) => {
    ToastAndroid.show(msg, ToastAndroid.SHORT);
  };

  clearLog() {
    this.setState({
      log: "",
    });
  }

  initFCM() {
    HMSPushFCM.init()
      .then((result) => {
        this.log("init", result);
      })
      .catch((err) => {
        alert("[init] Error/Exception: " + JSON.stringify(err));
      });
  }

  setCountryCode(countryCode) {
    HMSPushFCM.setCountryCode(countryCode)
      .then((result) => {
        this.log("setCountryCode", result);
      })
      .catch((err) => {
        alert("[setCountryCode] Error/Exception: " + JSON.stringify(err));
      });
  }

  isProxyInitEnabled() {
    HMSPushFCM.isProxyInitEnabled()
      .then((result) => {
        this.log("isProxyInitEnabled", result);
      })
      .catch((err) => {
        alert("[isProxyInitEnabled] Error/Exception: " + JSON.stringify(err));
      });
  }

  getAAID() {
    HmsPushInstanceId.getAAID()
      .then((result) => {
        this.log("getAAID", result);
      })
      .catch((err) => {
        alert("[getAAID] Error/Exception: " + JSON.stringify(err));
      });
  }

  getToken() {
    HmsPushInstanceId.getToken("")
      .then((result) => {
        this.log("getToken", result);
      })
      .catch((err) => {
        alert("[getToken] Error/Exception: " + JSON.stringify(err));
      });
  }

  render() {
    return (
      <ScrollView>
        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.tertiaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.initFCM()}
          >
            <Text style={styles.buttonText}>Init FCM</Text>
          </TouchableOpacity>
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.secondaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.isProxyInitEnabled()}
          >
            <Text style={styles.buttonText}>Is Proxy Enabled</Text>
          </TouchableOpacity>
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.tertiaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.setCountryCode("CH")}
          >
            <Text style={styles.buttonText}>Set Country Code</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getToken();
            }}
          >
            <Text style={styles.buttonText}>Get Token</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getAAID();
            }}
          >
            <Text style={styles.buttonText}>Get AAID</Text>
          </TouchableOpacity>
        </View>

        <ScrollView style={styles.containerShowResultMsg}>
          <Text>{this.state.log}</Text>
        </ScrollView>
      </ScrollView>
    );
  }
}
