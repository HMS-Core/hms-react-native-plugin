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
import { Text, TouchableOpacity, View } from "react-native";
import { styles } from "./styles";

import { HmsConsentsController } from "@hmscore/react-native-hms-health";
import Utils from "./Utils";

export default class ConsentsController extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      lang: "en-us",
      appId: "<app_id>",
      scopeList: [
        "https://www.huawei.com/healthkit/step.read",
        "https://www.huawei.com/healthkit/calories.read"
      ],
    };
  }

  async getScope() {
    try {
      const result = await HmsConsentsController.get(
        this.state.lang,
        this.state.appId
      );

      // Return the list of activity records that have stopped
      Utils.logResult("getScopes", result);
      Utils.notify("getScopes - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  async revokeAll() {
    try {
      const result = await HmsConsentsController.revoke(this.state.appId, null);

      // Return the list of activity records that have stopped
      Utils.logResult("revokeAllScopes", result);
      Utils.notify("revokeAllScopes - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  async revokeSelected() {
    try {
      const result = await HmsConsentsController.revoke(
        this.state.appId,
        this.state.scopeList
      );

      // Return the list of activity records that have stopped
      Utils.logResult("revokeSelectedScopes", result);
      Utils.notify("revokeSelectedScopes - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  async cancelAuthorization() {
    try {
      const result = await HmsConsentsController.cancelAuthorization(
        this.state.appId,
        this.state.scopeList
      );

      // Return the list of activity records that have stopped
      Utils.logResult("cancelAuthorization", result);
      Utils.notify("cancelAuthorization - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  async cancelAuthorizationAll() {
    try {
      const result = await HmsConsentsController.cancelAuthorizationAll(false);

      // Return the list of activity records that have stopped
      Utils.logResult("cancelAuthorizationAll", result);
      Utils.notify("cancelAuthorizationAll - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  render() {
    return (
      <View style={styles.bg}>
        <Text style={styles.h1}>CONSENTS CONTROLLER</Text>
        <View style={styles.innerBody}>
          <Text style={styles.h2}>
            You can manage the permissions given to the app.
          </Text>
          <View style={styles.buttonDataController}>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.getScope()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> getScopes </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.revokeAll()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> revokeAll </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.revokeSelected()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> revokeSelected </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.cancelAuthorization()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> cancelAuthorization </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.cancelAuthorizationAll()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> cancelAuthorizationAll </Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    );
  }
}
