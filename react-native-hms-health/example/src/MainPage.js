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

import React, { Component } from "react";
import { View, Text, ScrollView, TouchableOpacity } from "react-native";

import { HmsHealthAccount } from "@hmscore/react-native-hms-health";
import { styles } from "./styles";
import Utils from "./Utils";
import DataController from "./DataController";
import AutoRecorderController from "./AutoRecorderController";
import ActivityRecordsController from "./ActivityRecordsController";
import SettingController from "./SettingController";
import ConsentsController from "./ConsentsController";

// Add scopes to apply for. The following only shows an example.
// Developers need to add scopes according to their specific needs.
const scopes = [
  // View and save steps in HUAWEI Health Kit.
  HmsHealthAccount.HEALTHKIT_STEP_BOTH,
  // View and save height and weight in HUAWEI Health Kit.
  HmsHealthAccount.HEALTHKIT_HEIGHTWEIGHT_BOTH,
  // View and save the heart rate data in HUAWEI Health Kit.
  HmsHealthAccount.HEALTHKIT_HEARTRATE_BOTH,
  // View and save activity data
  HmsHealthAccount.HEALTHKIT_ACTIVITY_BOTH,
  //View and save workout record data
  HmsHealthAccount.HEALTHKIT_ACTIVITY_RECORD_BOTH,
  //Calories Burnt
  HmsHealthAccount.HEALTHKIT_CALORIES_BOTH,
  HmsHealthAccount.HEALTHKIT_NUTRITION_BOTH,
  HmsHealthAccount.HEALTHKIT_LOCATION_BOTH,
];
/**
 * Signing In and applying for Scopes.
 * </br>
 * Sign-in and authorization method.
 * The authorization screen will display up if authorization has not granted by the current account.
 */
async function signIn(scopes) {
  try {
    Utils.logCall("signIn");
    const result = await HmsHealthAccount.signIn(scopes);
    Utils.logResult("signIn", result);
    Utils.notify("signIn success");
  } catch (error) {
    Utils.logError(error);
    Utils.notify("signIn fail");
  }
}
/**
 * Main page for functional description.
 *
 */
export default class MainPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      receiveContent: "",
      topic: "",
      disableAutoInit: false,
      enableAutoInit: true,
    };
  }

  componentWillUnmount() {}

  render() {
    return (
      <ScrollView style={styles.bg}>
        <Text style={styles.h1}>Health Kit Demo App</Text>
        <Text style={styles.h3}>
          Touch Sign In to HMS Account to complete login and authorization, and
          then use other buttons to try the related API functions.
        </Text>
        <Text style={styles.h3Color}>
          Note: If the login dialog box is not displayed, change the package
          name, app ID, and configure the signature file by referring to the
          developer guide on the official website.
        </Text>
        <View style={styles.containerFlex}>
          <View style={styles.mainPageButton}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => signIn(scopes)}
              underlayColor="#fff"
            >
              <Text style={styles.buttonText}>Sign In to HMS Account</Text>
            </TouchableOpacity>
          </View>
        </View>
        <DataController />
        <AutoRecorderController />
        <ActivityRecordsController />
        <SettingController />
        <ConsentsController />
      </ScrollView>
    );
  }
}
