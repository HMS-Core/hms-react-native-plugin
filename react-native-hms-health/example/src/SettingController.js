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

import {
  HmsDataController,
  HmsSettingController,
} from "@hmscore/react-native-hms-health";
import Utils from "./Utils";

const dataTypeName = "com.demo.health.anyExtendedCustomDataType";

export default class SettingController extends React.Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {}

  componentWillUnmount() {}

  /**
   * add new DataType.
   * you need two object to add new DataType: DataTypeAddOptions and SettingController.
   * specify the field by drop-down box, You cannot add DataType with duplicate DataType's name.
   * You can add multiple field，For simplicity, only one field is added here.
   *
   * @returns {Promise<void>}
   */
  async addNewDataType() {
    try {
      Utils.logCall("addNewDataType - SettingController");

      const result = await HmsSettingController.addNewDataType(
        dataTypeName,
        [HmsSettingController.FIELD_STEPS_DELTA, HmsSettingController.FIELD_DISTANCE_DELTA]
      );

      Utils.logResult("addNewDataType", result);
      Utils.notify("addNewDataType - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * read DataType.
   * Get DataType with the specified name
   *
   * @returns {Promise<void>}
   */
  async readDataType() {
    try {
      Utils.logCall("readDataType - SettingController");

      const result = await HmsSettingController.readDataType(dataTypeName);
      // Return the list of activity records that have stopped
      Utils.logResult("readDataType", result);
      Utils.notify("readDataType - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * disable HiHealth.
   * After calling this function, HiHealth will cancel All your Records.
   *
   * @returns {Promise<void>}
   */
  async disableHiHealth() {
    try {
      Utils.logCall("disableHiHealth - SettingController");

      const result = await HmsSettingController.disableHiHealth();
      // Return the list of activity records that have stopped
      Utils.logResult("disableHiHealth", result);
      Utils.notify("disableHiHealth - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Use the data controller to add a sampling dataset.
   *
   * @returns {Promise<void>}
   */
  async insertSelfData() {
    try {
      Utils.logCall("insertTestData - DataController");
      // Build the dataCollector object
      const dataCollector = {
        dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
        dataStreamName: "STEPS_DELTA",
        dataGenerateType: 0,
      };
      //You can use sampleSets to add more sampling points to the sampling dataset.
      const sampleSets = [
        {
          startTime: "2020-07-17 12:00:00",
          endTime: "2020-07-17 12:12:00",
          fields: [
            {
              fieldName: HmsDataController.FIELD_STEPS_DELTA,
              fieldValue: 1000,
            },
          ],
          timeUnit: HmsDataController.MILLISECONDS,
        },
      ];
      const result = await HmsDataController.insert(dataCollector, sampleSets);
      Utils.logResult("insert  - SettingController", result);
      Utils.notify(
        "insert  - SettingController - success!" + JSON.stringify(sampleSets)
      );
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Use the data controller to query the sampling dataset by specific criteria.
   *
   * @returns {Promise<void>}
   */
  async readSelfData() {
    try {
      Utils.logCall("readSelfData - SettingController");
      // Return the list of activity records that have stopped
      Utils.logResult("readSelfData", result);

      const dataCollector = {
        dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
        dataStreamName: "STEPS_DELTA",
        dataGenerateType: 0,
      };
      const dateMap = {
        startTime: "2020-07-17 12:00:00",
        endTime: "2020-07-17 12:12:00",
        timeUnit: HmsDataController.MILLISECONDS,
      };
      const result = await HmsDataController.read(dataCollector, dateMap, null);
      Utils.logResult("read  - DataController", result);
      Utils.notify("readSelfData - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  render() {
    return (
      <View style={styles.bg}>
        <Text style={styles.h1}>SETTTING CONTROLLER</Text>

        <View style={styles.innerBody}>
          <Text style={styles.h2}>
            {"Set new data type name, Duplicate names are unacceptable, this name must start with package name, and End with a custom name." +
              "\n \n In this demo app it is " +
              dataTypeName +
              "\n \n Choose which Field to select via HmsSettingController.FIELD_VALUES."}
          </Text>
          <View style={styles.buttonDataController}>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.addNewDataType()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> addNewDataType </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.readDataType()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> readDataType </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.disableHiHealth()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> disableHiHealth </Text>
            </TouchableOpacity>
          </View>
        </View>
        <View style={styles.innerBody}>
          <Text style={styles.h2}>
            {"Use the newly added data type to write or read value."}
          </Text>
          <View style={styles.buttonDataController}>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.insertSelfData()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> insertSelfData </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.readSelfData()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> readSelfData </Text>
            </TouchableOpacity>
          </View>
        </View>
        <View style={styles.innerBody}>
          <Text style={styles.h2}>{"Authorization methods"}</Text>
          <View style={styles.buttonDataController}>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() =>
                HmsSettingController.getHealthAppAuthorization().then((e) => {
                  console.log(e);
                })
              }
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> getAuthorization </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() =>
                HmsSettingController.checkHealthAppAuthorization().then((e) => {
                  console.log(e);
                })
              }
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> checkAuthorization </Text>
            </TouchableOpacity>
          </View>
        </View>

        <View style={styles.innerBody}>
          <Text style={styles.h2}>{"Logger methods"}</Text>
          <View style={styles.buttonDataController}>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() =>
                HmsSettingController.enableLogger().then((e) => {
                  console.log(e);
                })
              }
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> enableLogger </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() =>
                HmsSettingController.disableLogger().then((e) => {
                  console.log(e);
                })
              }
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> disableLogger </Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    );
  }
}
