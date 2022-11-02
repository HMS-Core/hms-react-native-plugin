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
import { NativeEventEmitter, Text, TouchableOpacity, View } from "react-native";

import { styles } from "./styles";

import { HmsDataController } from "@hmscore/react-native-hms-health";
import Utils from "./Utils";

/**
 * {@link DataController} class has sample codes for managing fitness and health data that refers to {@link DataController}
 *
 * DataController is used to manage fitness and health data (DataType, HealthDataTypes, and SportDataTypes).
 * The related operations include adding, deleting, modifying, and querying of the data.
 * </br>
 * In addition, DataController allows for feature-based query on the summary data of the current day,
 * as well as the summary data on the local device of the current day.
 * Events can be registered for updates of six data types.
 * 1. Basic metabolic rate per day (unit: kcal): HmsDataController.DT_INSTANTANEOUS_CALORIES_BMR
 * 2. Body fat rate: HmsDataController.DT_INSTANTANEOUS_BODY_FAT_RATE
 * 3. Height (unit: meter): HmsDataController.DT_INSTANTANEOUS_HEIGHT
 * 4. Water taken over a single drink (unit: liter): HmsDataController.DT_INSTANTANEOUS_HYDRATE
 * 5. Nutrient intake over a meal: HmsDataController.DT_INSTANTANEOUS_NUTRITION_FACTS
 * 6. Weight (unit: kg): HmsDataController.DT_INSTANTANEOUS_BODY_WEIGHT
 */
export default class DataController extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
      samplingTime: "2020-12-19 08:00:00",
      streamName: "HEALTH_DEMO",
      startTime: "2020-12-19 05:00:00",
      endTime: "2020-12-19 21:00:00",
    };
  }

  componentDidMount() {
    Utils.logCall("call componentDidMount - DataController");
    (async () => {
      await this.create.call(this);
    })();

    const eventEmitter = new NativeEventEmitter(HmsDataController);
    /**
     *     Events can be registered for updates of the following six data types:
     *     1. Basic metabolic rate per day (unit: kcal): DataType.DT_INSTANTANEOUS_CALORIES_BMR
     *     2. Body fat rate: DataType.DT_INSTANTANEOUS_BODY_FAT_RATE
     *     3. Height (unit: meter): DataType.DT_INSTANTANEOUS_HEIGHT
     *     4. Water taken over a single drink (unit: liter): DataType.DT_INSTANTANEOUS_HYDRATE
     *     5. Nutrient intake over a meal: DataType.DT_INSTANTANEOUS_NUTRITION_FACTS
     *     6. Weight (unit: kg): DataType.DT_INSTANTANEOUS_BODY_WEIGHT
     *
     */
    this.eventListener = eventEmitter.addListener(
      "registerModifyDataMonitor",
      (event) => {
        console.log(event);
        Utils.notify("registerModifyDataMonitor - " + JSON.stringify(event));
      }
    );
  }

  componentWillUnmount() {
    try {
      this.eventListener.remove();
    } catch (error) {}
  }

  /**
   * Initialize a data controller object.
   * <p>
   * Note:  Before using {@link HmsDataController} methods,
   * always initDataController method must be called with requested dataTypes.
   * </p>
   *
   * @returns {Promise<void>}
   */
  async create() {
    try {
      Utils.logCall("init  - DataController");

      // Obtain and set the read & write permissions for DT_CONTINUOUS_STEPS_DELTA and DT_INSTANTANEOUS_HEIGHT.
      const result = await HmsDataController.initDataController([
        {
          dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
          hiHealthOptions: HmsDataController.ACCESS_READ,
        },
        {
          dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
          hiHealthOptions: HmsDataController.ACCESS_WRITE,
        },
        {
          dataType: HmsDataController.DT_INSTANTANEOUS_HEIGHT,
          hiHealthOptions: HmsDataController.ACCESS_READ,
        },
        {
          dataType: HmsDataController.DT_INSTANTANEOUS_HEIGHT,
          hiHealthOptions: HmsDataController.ACCESS_WRITE,
        },
        {
          dataType: HmsDataController.DT_INSTANTANEOUS_NUTRITION_FACTS,
          hiHealthOptions: HmsDataController.ACCESS_READ,
        },
        {
          dataType: HmsDataController.DT_INSTANTANEOUS_NUTRITION_FACTS,
          hiHealthOptions: HmsDataController.ACCESS_WRITE,
        },
      ]);

      // Use the obtained permissions in account page, to create the data controller object.
      Utils.logResult("init  - DataController", result);
      Utils.notify(
        "init  - DataController - success!" + JSON.stringify(result)
      );
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Add a sampling dataset.
   *
   * @returns {Promise<void>}
   */
  async insert() {
    try {
      Utils.logCall("insert  - DataController");

      // Build the dataCollector object
      const dataCollector = {
        dataType: this.state.dataType,
        dataStreamName: this.state.streamName,
        dataGenerateType: 0,
      };

      //You can use sampleSets to add more sampling points to the sampling dataset.
      const sampleSets = [
        {
          startTime: this.state.startTime,
          endTime: this.state.endTime,
          // samplingTime: this.state.samplingTime,
          fields: [
            {
              fieldName: HmsDataController.FIELD_STEPS_DELTA,
              fieldValue: 90,
            },
          ],
          timeUnit: HmsDataController.MILLISECONDS,
          metaData: {
            metaDataKey: "Key",
            metaDataValue: "Value"
          }
        },
      ];

      // Call the data controller to insert the sampling dataset
      const result = await HmsDataController.insert(dataCollector, sampleSets);
      Utils.logResult("insert  - DataController", result);
      Utils.notify(
        "insert  - DataController - success!" + JSON.stringify(sampleSets)
      );
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Delete the sampling data by specific criteria.
   *
   * @returns {Promise<void>}
   */
  async delete() {
    try {
      Utils.logCall("delete  - DataController");

      // Build the dataCollector object for deletion
      const dataCollectorForDeletion = {
        dataType: this.state.dataType,
        dataStreamName: this.state.streamName,
        dataGenerateType: 0,
      };

      // Build the time range for the deletion: start time and end time.
      const dateMapForDeletion = {
        startTime: this.state.startTime,
        endTime: this.state.endTime,
        timeUnit: HmsDataController.MILLISECONDS,
      };

      //Call the data controller to delete the sampling dataset
      const result = await HmsDataController.delete(
        dataCollectorForDeletion,
        dateMapForDeletion
      );
      Utils.logResult("delete  - DataController", result);
      Utils.notify(
        "delete  - DataController - success!" + JSON.stringify(result)
      );
    } catch (error) {
      Utils.logError(error);
    }
  }


  /**
   * Modify the sampling data by specific criteria.
   *
   * @returns {Promise<void>}
   */
  async update() {
    try {
      Utils.logCall("update  - DataController");

      // Build the dataCollector object
      const dataCollector = {
        dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
        dataStreamName: this.state.streamName,
        dataGenerateType: 0,
      };

      // You can use sampleSets to add more sampling points to the sampling dataset.
      const sampleSets = [
        {
          startTime: this.state.startTime,
          endTime: this.state.endTime,
          samplingTime: this.state.samplingTime,
          fields: [
            {
              fieldName: HmsDataController.FIELD_STEPS_DELTA,
              fieldValue: 2000,
            }
          ],
        },
      ];

      // Build a parameter object for the update.
      // Note: (1) The start time of the modified object updateOptions cannot be greater than the minimum
      // value of the start time of all sample data points in the modified data sample set
      // (2) The end time of the modified object updateOptions cannot be less than the maximum value of the
      // end time of all sample data points in the modified data sample set
      const updateOptions = {
        startTime: this.state.startTime,
        endTime: this.state.endTime,
        timeUnit: HmsDataController.MILLISECONDS,
      };

      const result = await HmsDataController.update(
        dataCollector,
        sampleSets,
        updateOptions
      );
      // Call the data controller to modify the sampling dataset.
      Utils.logResult("update  - DataController", result);
      Utils.notify(
        "update  - DataController - success!" + JSON.stringify(sampleSets)
      );
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Query the sampling dataset by specific criteria.
   *
   * @returns {Promise<void>}
   */
  async read() {
    try {
      Utils.logCall("read  - DataController");

      // Build the dataCollector object
      const dataCollector = {
        dataType: this.state.dataType,
        dataStreamName: this.state.streamName,
        dataGenerateType: 0,
      };

      // Group the resultData with this object
      const groupData = {
        groupByTime: { duration: 6, timeUnit: HmsDataController.HOURS },
        inputDataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
        outputDataType: HmsDataController.DT_CONTINUOUS_STEPS_TOTAL,
      };

      // Build the time range for the query: start time and end time.
      const dateMap = {
        startTime: this.state.startTime,
        endTime: this.state.endTime,
        timeUnit: HmsDataController.MILLISECONDS,
      };

      // Call the data controller to query the sampling dataset.
      const result = await HmsDataController.read(
        dataCollector,
        dateMap,
        groupData
      );

      Utils.logResult("read  - DataController", result);
      Utils.notify(
        "read  - DataController - success!" + JSON.stringify(result)
      );
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Query the summary data of the current day by data type.
   *
   * @returns {Promise<void>}
   */
  async readToday() {
    try {
      Utils.logCall("readTodaySummation  - DataController");

      // Use the specified data type (DT_CONTINUOUS_STEPS_DELTA) to call the data controller to query
      const dataType = {
        dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
      };

      // Call the data controller to query the summary data of the current day.
      const result = await HmsDataController.readTodaySummation(dataType);
      Utils.logResult("readTodaySummation  - DataController", result);
      Utils.notify(
        "readTodaySummation  - DataController - success!" +
          JSON.stringify(result)
      );
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Query the summary data of the the duration that is specified
   *
   * @returns {Promise<void>}
   */
  async readDailySummation() {
    try {
      Utils.logCall("readDailySummation  - DataController");

      // Use the specified data type (DT_CONTINUOUS_STEPS_DELTA) to call the data controller to query
      const dataType = {
        dataType: HmsDataController.DT_CONTINUOUS_CALORIES_BURNT,
      };

      const startTime = 20201202;
      const endTime = 20201205;

      // Call the data controller to query the summary data of the current day.
      const result = await HmsDataController.readDailySummation(
        dataType,
        startTime,
        endTime
      );
      Utils.logResult("readDailySummation  - DataController", result);
      Utils.notify(
        "readDailySummation  - DataController - success!" +
          JSON.stringify(result)
      );
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Trigger the listener for data changes for testing purposes by inserting a data entry.
   *
   * @returns {Promise<void>}
   */
  async insertTestData() {
    try {
      Utils.logCall("insertTestData");

      // Build the dataCollector object
      const dataCollector = {
        dataType: HmsDataController.DT_INSTANTANEOUS_HEIGHT,
        dataStreamName: "STEPS_DELTA",
        dataGenerateType: 0,
      };

      //You can use sampleSets to add more sampling points to the sampling dataset.
      const sampleSets = [
        {
          startTime: "2020-07-14 13:12:00",
          endTime: "2020-07-14 13:12:00",
          fieldValue: HmsDataController.FIELD_HEIGHT,
          floatValue: 1.56,
          timeUnit: HmsDataController.MILLISECONDS,
        },
      ];
      const result = await HmsDataController.insert(dataCollector, sampleSets);
      Utils.logResult("insertTestData  - DataController", result);
      Utils.notify(
        "insertTestData  - DataController - success!" +
          JSON.stringify(sampleSets)
      );
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Clear all user data from the device and cloud.
   *
   * @returns {Promise<void>}
   */
  async clearAll() {
    try {
      Utils.logCall("clearAll  - DataController");

      // Call the clearAll method of the data controller to delete data
      const result = await HmsDataController.clearAll();
      Utils.logResult("clearAll", result);
      Utils.notify("clearAll - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  render() {
    return (
      <View style={styles.bg}>
        <Text style={styles.h1}>DATA CONTROLLER</Text>
        <View style={styles.innerBody}>
          <Text style={styles.h2}>
            {
              "Managing fitness and health data, including inserting, updating, reading, and deleting data"
            }
          </Text>
          <View style={styles.horizontalButtons}>
            <TouchableOpacity
              style={styles.smallButton}
              onPress={() => this.insert()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> insert </Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.smallButton}
              onPress={() => this.delete()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> delete </Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.smallButton}
              onPress={() => this.update()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> update </Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.smallButton}
              onPress={() => this.read()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> read </Text>
            </TouchableOpacity>
          </View>
        </View>
        <View style={styles.innerBody}>
          <Text style={styles.h2}>
            {
              "Feature-based query APIs: Querying the summary data of the current day, as well as the summary data on the local device of the current day"
            }
          </Text>
          <View style={styles.buttonDataController}>
            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.readToday()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> readTodaySummation </Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.horizontalButton}
              onPress={() => this.readDailySummation()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}>readDailySummation</Text>
            </TouchableOpacity>
          </View>
        </View>
        <View style={styles.innerBody}>
          <Text style={styles.h2}>
            {
              "Synchronizing fitness and health data between the device and cloud, as well as clearing all data"
            }
          </Text>
          <View style={styles.horizontalCenterButtons}>
            <TouchableOpacity
              style={styles.smallButton}
              onPress={() => this.clearAll()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}>clearAll</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    );
  }
}
