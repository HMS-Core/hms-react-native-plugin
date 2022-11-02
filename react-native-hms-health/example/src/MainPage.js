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
  TouchableHighlight,
  TouchableOpacity,
  StyleSheet,
} from "react-native";

import { HmsHealthAccount } from "@hmscore/react-native-hms-health";
import { styles } from "./styles";
import Utils from "./Utils";
import DataController from "./DataController";
import AutoRecorderController from "./AutoRecorderController";
import ActivityRecordsController from "./ActivityRecordsController";
import SettingController from "./SettingController";
import ConsentsController from "./ConsentsController"; 
import HealthRecordController from "./HealthRecordController";

// Add scopes to apply for. The following only shows an example.
// Developers need to add scopes according to their specific needs.
const scopes = [
  // View and save steps in HUAWEI Health Kit.
  HmsHealthAccount.HEALTHKIT_STEP_READ,
  HmsHealthAccount.HEALTHKIT_STEP_WRITE,
  // View and save height and weight in HUAWEI Health Kit.
  HmsHealthAccount.HEALTHKIT_HEIGHTWEIGHT_READ,
  HmsHealthAccount.HEALTHKIT_HEIGHTWEIGHT_WRITE,
  // View and save the heart rate data in HUAWEI Health Kit.
  HmsHealthAccount.HEALTHKIT_HEARTRATE_READ,
  HmsHealthAccount.HEALTHKIT_HEARTRATE_WRITE,
  // View and save activity data
  HmsHealthAccount.HEALTHKIT_ACTIVITY_READ,
  HmsHealthAccount.HEALTHKIT_ACTIVITY_WRITE,
  //View and save workout record data
  HmsHealthAccount.HEALTHKIT_ACTIVITY_RECORD_READ,
  HmsHealthAccount.HEALTHKIT_ACTIVITY_RECORD_WRITE,
  //Calories Burnt
  HmsHealthAccount.HEALTHKIT_CALORIES_READ,
  HmsHealthAccount.HEALTHKIT_NUTRITION_READ,
  HmsHealthAccount.HEALTHKIT_LOCATION_READ,
  HmsHealthAccount.HEALTHKIT_CALORIES_WRITE,
  HmsHealthAccount.HEALTHKIT_NUTRITION_WRITE,
  HmsHealthAccount.HEALTHKIT_LOCATION_WRITE,
  //Health Record
  HmsHealthAccount.HEALTHKIT_HEARTHEALTH_READ,
  HmsHealthAccount.HEALTHKIT_HEARTHEALTH_WRITE
];

const pages = [
  {
    title: "DataController",
    component: DataController,
  },
  {
    title: "AutoRecorderController",
    component: AutoRecorderController,
  },
  {
    title: "ActivityRecordsController",
    component: ActivityRecordsController,
  },
  {
    title: "SettingController",
    component: SettingController,
  },
  {
    title: "ConsentsController",
    component: ConsentsController,
  },
  {
    title: "HealthRecordController",
    component: HealthRecordController,
  }, 
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
      currentPage: pages[0],
    };
  }

  renderButtons() {
    return pages.map((b) => (
      <View
        key={b.title}
        style={[
          { padding: 4, margin: 2 },
          this.state.currentPage == b ? customStyle.buttonBorder : null,
        ]}
      >
        <TouchableHighlight
          onPress={() => {
            this.setState({ currentPage: b });
          }}
        >
          <Text>{b.title}</Text>
        </TouchableHighlight>
      </View>
    ));
  }

  renderScreen() {
    const Page = this.state.currentPage.component;
    return <Page />;
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <Text style={styles.h1}>Health Kit Demo App</Text>
        <ScrollView horizontal style={{ padding: 4 }}>
          {this.renderButtons()}
        </ScrollView>
        <View style={customStyle.lineStyle} />
        <Text style={styles.h3}>
          Touch Sign In to HMS Account to complete login and authorization, and
          then use other buttons to try the related API functions.
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
        {this.renderScreen()}
      </ScrollView>
    );
  }
}

const customStyle = StyleSheet.create({
  lineStyle: {
    marginTop: 8,
    borderBottomColor: "gray",
    borderBottomWidth: 1,
  },
  buttonBorder: {
    borderColor: "black",
    borderWidth: 1,
    borderRadius: 5,
  },
});
