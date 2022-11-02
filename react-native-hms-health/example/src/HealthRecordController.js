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

import { HmsHealthRecordController, HmsDataController } from "@hmscore/react-native-hms-health";
import Utils from "./Utils";


export default class HealthRecordController extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      dateMap: {
        startTime: "2021-08-17 23:00:00",
        endTime: "2021-08-17 23:25:00",
        timeUnit: HmsHealthRecordController.MILLISECONDS,
      },
      dataType: {
        dataType: HmsHealthRecordController.DT_INSTANTANEOUS_HEART_RATE,
        dataGenerateType: HmsHealthRecordController.DATA_TYPE_RAW,
        dataCollectorName: "such as step count",
      },
    };
  }

  async addHealthRecord() {
    try {
      Utils.logCall("addHealthRecord - HealthRecordController");

      const startTime = "2021-08-17 23:00:00";
      const endTime = "2021-08-17 23:25:00";

      const samplePoint = [
        {
          fieldName: HmsHealthRecordController.FIELD_AVG,
          fieldValue: 90,
        },
        {
          fieldName: HmsHealthRecordController.FIELD_MAX,
          fieldValue: 100,
        },
        {
          fieldName: HmsHealthRecordController.FIELD_MIN,
          fieldValue: 80,
        },
      ]

      const sampleSet = [
        {
          fieldName: HmsHealthRecordController.FIELD_BPM,
          fieldValue: 88,
        }
      ]

      const samplePointForHealthBuilder = [
        {
          fieldName: HmsHealthRecordController.FIELD_THRESHOLD,
          fieldValue: 40,
        },
        {
          fieldName: HmsHealthRecordController.FIELD_AVG_HEART_RATE,
          fieldValue: 44,
        },
        {
          fieldName: HmsHealthRecordController.FIELD_MAX_HEART_RATE,
          fieldValue: 48,
        },
        {
          fieldName: HmsHealthRecordController.FIELD_MIN_HEART_RATE,
          fieldValue: 40,
        },
      ]

      const healthRecordBuilder = [
        {
          startTime: startTime,
          endTime: endTime,
          timeUnit: HmsHealthRecordController.MILLISECONDS,
          fields: samplePointForHealthBuilder
        }
      ]

      const sampleSetMapArr = [
        {
          startTime: startTime,
          endTime: endTime,
          timeUnit: HmsHealthRecordController.MILLISECONDS,
          fields: sampleSet
        },
      ];

      const samplePointMapArr = [
        {
          startTime: startTime,
          endTime: endTime,
          timeUnit: HmsHealthRecordController.MILLISECONDS,
          fields: samplePoint
        },
      ];

      const dataCollectorArray = [{
        dataType: HmsHealthRecordController.DT_INSTANTANEOUS_HEART_RATE,
        dataGenerateType: HmsHealthRecordController.DATA_TYPE_RAW,
        dataStreamName: "such as step count",
      }, {
        dataType: HmsHealthRecordController.POLYMERIZE_CONTINUOUS_HEART_RATE_STATISTICS,
        dataGenerateType: HmsHealthRecordController.DATA_TYPE_RAW,
        dataCollectorName: "such as step count",
      }, {
        dataType: HmsHealthRecordController.DT_HEALTH_RECORD_BRADYCARDIA,
        dataGenerateType: HmsHealthRecordController.DATA_TYPE_RAW,
        dataCollectorName: "such as step count",
      }
      ]

      const result = await HmsHealthRecordController.addHealthRecord(
        healthRecordBuilder,
        dataCollectorArray,
        sampleSetMapArr,
        samplePointMapArr
      );
      alert(JSON.stringify(result));
      Utils.logResult("addActivityRecord", result);
      Utils.notify("addActivityRecord - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  async updateHealthRecord() {
    try {
      Utils.logCall("addHealthRecord - HealthRecordController");

      const startTime = "2021-08-17 23:00:00";
      const endTime = "2021-08-17 23:25:00";

      const sampleSet = [{
        fieldName: HmsHealthRecordController.FIELD_BPM,
        fieldValue: 90,
      }]

      const samplePoint = [{
        fieldName: HmsHealthRecordController.FIELD_AVG,
        fieldValue: 90,
      },
      {
        fieldName: HmsHealthRecordController.FIELD_MAX,
        fieldValue: 100,
      },
      {
        fieldName: HmsHealthRecordController.FIELD_MIN,
        fieldValue: 80,
      }]

      const samplePointForHealthBuilder = [
        {
          fieldName: HmsHealthRecordController.FIELD_THRESHOLD,
          fieldValue: 42,
        },
        {
          fieldName: HmsHealthRecordController.FIELD_AVG_HEART_RATE,
          fieldValue: 45,
        },
        {
          fieldName: HmsHealthRecordController.FIELD_MAX_HEART_RATE,
          fieldValue: 48,
        },
        {
          fieldName: HmsHealthRecordController.FIELD_MIN_HEART_RATE,
          fieldValue: 42,
        },
      ]

      const healthRecordBuilder = [
        {
          startTime: startTime,
          endTime: endTime,
          timeUnit: HmsHealthRecordController.MILLISECONDS,
          fields: samplePointForHealthBuilder
        }
      ]

      const sampleSetMapArr = [
        {
          startTime: startTime,
          endTime: endTime,
          timeUnit: HmsHealthRecordController.MILLISECONDS,
          fields: sampleSet
        },
      ];

      const samplePointMapArr = [
        {
          startTime: startTime,
          endTime: endTime,
          timeUnit: HmsHealthRecordController.MILLISECONDS,
          fields: samplePoint
        },
      ];

      const dataCollectorArray = [

        dataCollector = {
          dataType: HmsHealthRecordController.DT_INSTANTANEOUS_HEART_RATE,
          dataGenerateType: HmsHealthRecordController.DATA_TYPE_RAW,
          dataStreamName: "such as step count",
        },

        dataCollector1 = {
          dataType: HmsHealthRecordController.POLYMERIZE_CONTINUOUS_HEART_RATE_STATISTICS,
          dataGenerateType: HmsHealthRecordController.DATA_TYPE_RAW,
          dataCollectorName: "such as step count",
        },

        dataCollector2 = {
          dataType: HmsHealthRecordController.DT_HEALTH_RECORD_BRADYCARDIA,
          dataGenerateType: HmsHealthRecordController.DATA_TYPE_RAW,
          dataCollectorName: "such as step count",
        }

      ]

      const result = await HmsHealthRecordController.updateHealthRecord(
        healthRecordBuilder,
        dataCollectorArray,
        sampleSetMapArr,
        samplePointMapArr
      );
      alert(JSON.stringify(result));
      Utils.logResult("addActivityRecord", result);
      Utils.notify("addActivityRecord - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  async getHealthRecord() {
    try {
      const result = await HmsHealthRecordController.getHealthRecord(
        this.state.dataType,
        this.state.dateMap
      );
      Utils.logResult("getHealthRecord", result);
      Utils.notify("getHealthRecord - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  async deleteHealthRecord() {
    try {

      const healthRecordDeleteOptions = {
        startTime: this.state.dateMap.startTime,
        endTime: this.state.dateMap.endTime,
        timeUnit: this.state.dateMap.timeUnit,

        isDeleteSubData: false,

        healthRecordIds: ["id"],

        dataType: HmsHealthRecordController.DT_HEALTH_RECORD_BRADYCARDIA,

        subDataTypes: [{
          dataType: HmsDataController.DT_INSTANTANEOUS_HEART_RATE,
          hiHealthOption: HmsDataController.ACCESS_READ
        }
        ]


      }

      const result = await HmsHealthRecordController.deleteHealthRecord(
        healthRecordDeleteOptions
      );
      Utils.logResult("deleteHealthRecord", result);
      Utils.notify("deleteHealthRecord - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  render() {
    return (
      <View style={styles.bg}>
        <Text style={styles.h1}>Health Recorder CONTROLLER</Text>
        <View style={styles.innerBody}>
          <TouchableOpacity
            style={styles.horizontalButton}
            onPress={() => this.addHealthRecord()}
            underlayColor="#fff">
            <Text style={styles.smallButtonLabel}> Add Health Record </Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.horizontalButton}
            onPress={() => this.updateHealthRecord()}
            underlayColor="#fff">
            <Text style={styles.smallButtonLabel}> Update Health Record </Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.horizontalButton}
            onPress={() => this.getHealthRecord()}
            underlayColor="#fff">
            <Text style={styles.smallButtonLabel}> Get Health Record </Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.horizontalButton}
            onPress={() => this.deleteHealthRecord()}
            underlayColor="#fff">
            <Text style={styles.smallButtonLabel}> Delete Health Record </Text>
          </TouchableOpacity>
        </View>

      </View>
    );
  }
}
