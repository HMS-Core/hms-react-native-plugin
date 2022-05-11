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

import React, { Component } from "react";
import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  PermissionsAndroid,
  BackHandler,
  Alert,
} from "react-native";
import { styles } from "./Styles";
import { HMSNearbyApplication } from "@hmscore/react-native-hms-nearby";

export default class App extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.requestPermissions().then(this.setApiKey());
  }

  alertPermission = () =>
    Alert.alert(
      "Permission !",
      "Please allow permissions to use this app",
      [
        {
          text: "OK",
          onPress: () => BackHandler.exitApp(),
        },
      ],
      { cancelable: false }
    );

  alertApiKey = () =>
    Alert.alert(
      "Api Key !",
      "Please set your API key in StartPage.js to use this app",
      [
        {
          text: "OK",
          onPress: () => BackHandler.exitApp(),
        },
      ],
      { cancelable: false }
    );

  async requestPermissions() {
    try {
      const userResponse = await PermissionsAndroid.requestMultiple([
        PermissionsAndroid.PERMISSIONS.CAMERA,
        PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION,
        PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
        PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE,
        PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
      ]);
      if (
        userResponse["android.permission.ACCESS_COARSE_LOCATION"] ==
          PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.ACCESS_COARSE_LOCATION"] ==
          PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.CAMERA"] ==
          PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.CAMERA"] ==
          PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.ACCESS_FINE_LOCATION"] ==
          PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.ACCESS_FINE_LOCATION"] ==
          PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.READ_EXTERNAL_STORAGE"] ==
          PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.READ_EXTERNAL_STORAGE"] ==
          PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.WRITE_EXTERNAL_STORAGE"] ==
          PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.WRITE_EXTERNAL_STORAGE"] ==
          PermissionsAndroid.RESULTS.DENIED
      ) {
        this.alertPermission();
      }
    } catch (err) {
      console.log(err);
    }
  }

  async setApiKey() {
    try {
      var result = await HMSNearbyApplication.setApiKey("<api_key>");
      console.log(result);
      if (result.status != HMSNearbyApplication.SUCCESS) {
        this.alertApiKey();
      }
    } catch (error) {
      console.log(error);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <Text style={styles.h1}>Nearby Example Applications</Text>

        <View style={styles.containerFlex}>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate("Connection")}
              underlayColor="#fff"
            >
              <Text style={styles.buttonText}>Nearby{"\n"}Connection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate("Message")}
              underlayColor="#fff"
            >
              <Text style={styles.buttonText}>Nearby{"\n"}Message</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate("Wifi")}
              underlayColor="#fff"
            >
              <Text style={styles.buttonText}>Wifi{"\n"}Share</Text>
            </TouchableOpacity>
          </View>
        </View>
      </ScrollView>
    );
  }
}
