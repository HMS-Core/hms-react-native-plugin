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
import { NativeEventEmitter, Text, TouchableOpacity, View, ToastAndroid } from "react-native";

import { styles } from "./styles";

import { HmsActivityRecordsController, HmsDataController } from "@hmscore/react-native-hms-health";
import Utils from "./Utils";

/**
 * {@link ActivityRecordsController} class has sample codes for {@link HmsActivityRecordsController}
 * <p>
 * Create ActivityRecords for ongoing workout activities.
 * The workout data during an active ActivityRecord is implicitly associated with the ActivityRecord on the Health platform.
 *
 * <p>
 * Note: When the user initiates a workout activity, use the {@code HmsActivityRecordsController.beginActivityRecord} method to start an ActivityRecord.
 * When the user stops a workout activity, use the {@code HmsActivityRecordsController.endActivityRecord} method to stop an ActivityRecord.
 * </p>
 */
export default class ActivityRecordsController extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      activityRecordId: "AR:" + 124545875278785445, //Date.now().toLocaleString(),
      dateMap: {
        startTime: "2022-10-07 09:00:00",
        endTime:  "2022-10-10 10:00:00",
        timeUnit: HmsActivityRecordsController.MILLISECONDS,
      },
      dataType: {
        dataType: HmsActivityRecordsController.DT_CONTINUOUS_STEPS_DELTA,
      },
    };
  }

  componentDidMount() {
    Utils.logCall("call componentDidMount - ActivityRecordsController");

    const eventEmitter = new NativeEventEmitter(ActivityRecordsController);
    /**
     * While registering an event to Monitor the Status Changes of Activity Records for RN Side,
     * The user can use addActivityRecordsMonitor to register a listener to monitor the status changes of activity records.
     * When an activity record starts or stops, the caller will be notified via the "addActivityRecordsMonitor" event.
     * The user can use removeActivityRecordsMonitor to unregister the event for activity records they no longer wish to monitor.
     * <p>
     * Note: The events can be listened via event name: "addActivityRecordsMonitor" on RN Side.
     * </p>
     */
    this.eventListener = eventEmitter.addListener(
      "addActivityRecordsMonitor",
      (event) => {
        console.log(event);
        Utils.notify("addActivityRecordsMonitor - " + JSON.stringify(event));
      }
    );
  }

  componentWillUnmount() {}

  /**
   * Start an activity record
   * <p>
   * Note: When the user initiates a workout activity, use the ActivityRecordsController.beginActivityRecord method to start an ActivityRecord.
   * </p>
   *
   * @returns {Promise<void>}
   */
  async beginActivityRecord() {
    try {
      Utils.logCall("beginActivityRecord - ActivityRecordsController");
      // Build an ActivityRecord object
      const activityRecord = {
        activityRecordId: this.state.activityRecordId,
        name: this.state.activityRecordId,
        description:
          "This is ActivityRecord begin test!: " + this.state.activityRecordId,
        activityType: HmsActivityRecordsController.RUNNING,
        startTime: "2020-12-20 12:39:00",
        timeUnit: HmsActivityRecordsController.MILLISECONDS,
        timeZone: "+0100",
      };
      const result = await HmsActivityRecordsController.beginActivityRecord(
        activityRecord
      );
      Utils.logResult("beginActivityRecord", result);
      Utils.notify("beginActivityRecord - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  async beginBackgroundActivityRecord() {
    try {
      Utils.logCall("beginActivityRecord - ActivityRecordsController");
      // Build an ActivityRecord object
      const activityRecord = {
        activityRecordId: this.state.activityRecordId,
        name: this.state.activityRecordId,
        description:
          "This is ActivityRecord begin test!: " + this.state.activityRecordId,
        activityType: HmsActivityRecordsController.RUNNING,
        startTime: "2020-12-20 12:39:00",
        timeUnit: HmsActivityRecordsController.MILLISECONDS,
        timeZone: "+0100",
      };
      const result = await HmsActivityRecordsController.beginBackgroundActivityRecord(
        activityRecord
      );
      Utils.logResult("beginBackgroundActivityRecord", result);
      Utils.notify("beginBackgroundActivityRecord - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Stop the ActivityRecord
   * <p>
   * The app uses the {@code HmsActivityRecordsController.endActivityRecord} method to stop a specified ActivityRecord.
   * <p>
   * Note: When the user stops a workout activity, use the {@code HmsActivityRecordsController.endActivityRecord} method to stop an ActivityRecord.
   * </p>
   *
   * @returns {Promise<void>}
   */
  async endActivityRecord() {
    try {
      Utils.logCall("endActivityRecord - ActivityRecordsController");
      const result = await HmsActivityRecordsController.endActivityRecord(
        this.state.activityRecordId
      );
      // Return the list of activity records that have stopped
      Utils.logResult("endActivityRecord", result);
      Utils.notify("endActivityRecord - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  async endBackgroundActivityRecord() {
    try {
      Utils.logCall("endBackgroundActivityRecord - ActivityRecordsController");
      const result = await HmsActivityRecordsController.endBackgroundActivityRecord(
        this.state.activityRecordId
      );
      // Return the list of activity records that have stopped
      Utils.logResult("endBackgroundActivityRecord", result);
      Utils.notify("endBackgroundActivityRecord - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Stop activity records of the current app by calling {@code HmsActivityRecordsController.endAllActivityRecords}.
   * <p>
   * The app uses the {@code HmsActivityRecordsController.endAllActivityRecords} method to stop all the activity records.
   * <p>
   * Note: When ending all activity records, use the {@code HmsActivityRecordsController.endAllActivityRecords} method to stop an ActivityRecord.
   * </p>
   *
   * @returns {Promise<void>}
   */
  async endAllActivityRecords() {
    try {
      Utils.logCall("endAllActivityRecords - ActivityRecordsController");
      // Return the list of activity records that have stopped
      const result = await HmsActivityRecordsController.endAllActivityRecords();
      Utils.logResult("endAllActivityRecords", result);
      Utils.notify("endAllActivityRecords - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Inserting ActivityRecords to the Health Platform
   * </br>
   * To insert ActivityRecords with data that has been previously collected to the Health platform, perform the following:
   * 1. Create an ActivityRecord by specifying a time period and other necessary information.
   * 2. Create a DataCollector using the ActivityRecord and optional data set or grouped sampling point data.
   * 3. Use the {@code HmsActivityRecordsController.addActivityRecord} method to insert an ActivityRecord.
   * <p>
   * Note: The app uses the {@code HmsActivityRecordsController.addActivityRecord} method to insert the ActivityRecord and associated data to the Health platform.
   * </p>
   *
   * @returns {Promise<void>}
   */
   async addActivityRecord() {
    try {
      Utils.logCall("addActivityRecord - ActivityRecordsController");
      // Create start time that will be used to add activity record.
      const startTime = "2020-12-19 18:00:00";

      // Create end time that will be used to add activity record.
      const endTime = "2020-12-19 19:00:00";

      const dataCollector1 = {
        dataType: HmsActivityRecordsController.DT_CONTINUOUS_DISTANCE_TOTAL,
        dataGenerateType: HmsActivityRecordsController.DATA_TYPE_RAW,
        dataCollectorName: "test1",
      };

      const dataCollector2 = {
        dataType: HmsActivityRecordsController.POLYMERIZE_CONTINUOUS_SPEED_STATISTICS,
        dataGenerateType: HmsActivityRecordsController.DATA_TYPE_RAW,
        dataCollectorName: "test1",
      };

      const dataCollector3 = {
        dataType: HmsActivityRecordsController.DT_CONTINUOUS_STEPS_TOTAL,
        dataGenerateType: HmsActivityRecordsController.DATA_TYPE_RAW,
        dataCollectorName: "test1",
      };

      const dataCollector4 = {
        dataType: HmsActivityRecordsController.DT_INSTANTANEOUS_STEPS_RATE,
        dataGenerateType: HmsActivityRecordsController.DATA_TYPE_RAW,
        dataCollectorName: "test1",
      };

      const samplePoint1 = {
        startTime: startTime,
        endTime: endTime,
        fields: [
          {
            fieldName: HmsActivityRecordsController.FIELD_DISTANCE,
            fieldValue: 400.0,
          },
        ],
      };

      const samplePoint2 = {
        startTime: startTime,
        endTime: endTime,
        fields: [
          {
            fieldName: HmsActivityRecordsController.FIELD_AVG,
            fieldValue: 60.0,
          },
          {
            fieldName: HmsActivityRecordsController.FIELD_MIN,
            fieldValue: 40.0,
          },
          {
            fieldName: HmsActivityRecordsController.FIELD_MAX,
            fieldValue: 80.0,
          },
        ],
      };

      const samplePoint3 = {
        startTime: startTime,
        endTime: endTime,
        fields: [
          {
            fieldName: HmsActivityRecordsController.FIELD_STEPS,
            fieldValue: 1024,
          },
        ],
      };

      const activitySummmary = {
        dataSummary: [{
          dataCollector: dataCollector1,
          samplePoints: [samplePoint1],
        }, {
          dataCollector: dataCollector2,
          samplePoints: [samplePoint2],
        }, {
          dataCollector: dataCollector3,
          samplePoints: [samplePoint3],
        }],
      };

      // Build an ActivityRecord object
      const activityRecord = {
        activityRecordId: this.state.activityRecordId,
        name: "BeginActivityRecord",
        description: "This is ActivityRecord begin test!",
        activityType: HmsActivityRecordsController.RUNNING,
        startTime: startTime,
        endTime: endTime,
        timeUnit: HmsActivityRecordsController.MILLISECONDS,
        activitySummary: activitySummmary,
        timeZone: "+0800",
      };

      //You can use sampleSets to add more sampling points to the sampling dataset.
      //Build the (DT_CONTINUOUS_STEPS_DELTA) sampling data object and add it to the sampling dataSet
      const sampleSetMapObject = {
        dataCollector: dataCollector4,
        samplePoints: [
          {
            startTime: startTime,
            endTime: endTime,
            timeUnit: HmsActivityRecordsController.MILLISECONDS,
            fields: [
              {
                fieldName: HmsActivityRecordsController.FIELD_STEP_RATE,
                fieldValue: 10,
              },
            ]
          },
        ]
      };

      // Call the related method in the HmsActivityRecordsController to add activity records
      const result = await HmsActivityRecordsController.addActivityRecord(
        activityRecord,
        sampleSetMapObject
      );
      Utils.logResult("addActivityRecord", result);
      Utils.notify("addActivityRecord - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }


  /**
   * Reading ActivityRecords and Associated Data from the Health Platform
   * </br>
   * To obtain a list of ActivityRecords that meet the criteria, create an date and data type instance first.
   * Use the HmsActivityRecordsController.getActivityRecord method to obtain data.
   * <p>
   * Note:  The user can obtain a list of ActivityRecords and associated data that meets certain criteria from the Health platform.
   * For example, you can obtain all ActivityRecords within a specific period of time for particular data, or obtain a specific ActivityRecord by name or ID.
   * You can also obtain ActivityRecords created by other apps.
   * </p>
   *
   *
   * @returns {Promise<void>}
   */
  async getActivityRecord() {
    try {
      Utils.logCall("getActivityRecord - ActivityRecordsController");

      // In this example we will get all the activities for the requested times.
      // Thus, activityRecordId and activityRecordName will be null.
      const activityRecordId = null;
      const activityRecordName = null;

      // Call the related method in the HmsActivityRecordsController to get activity records
      const result = await HmsActivityRecordsController.getActivityRecord(
        this.state.dataType,
        this.state.dateMap,
        activityRecordId,
        activityRecordName
      );
      Utils.logResult("getActivityRecord", result);
      Utils.notify("getActivityRecord - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  async deleteActivityRecord() {
    try {
      Utils.logCall("deleteActivityRecord - ActivityRecordsController");
      // In this example we will delete all the activities for the requested times.
      // Thus, activityRecordId and activityRecordName will be null.

      const activityRecordOptions = {
        startTime: "2020-12-19 18:45:00",
        endTime: "2020-12-19 18:58:00",
        timeUnit: HmsActivityRecordsController.MILLISECONDS,
        activityRecordIds: ["MyBackgroundActivityRecordId"],
        subDataTypes: [{
         dataType: HmsActivityRecordsController.DT_CONTINUOUS_STEPS_TOTAL, 
         hiHealthOption: HmsDataController.ACCESS_READ
         },
         ],
         isDeleteSubData: true
         }

      const result = await HmsActivityRecordsController.deleteActivityRecord(activityRecordOptions); 

      Utils.logResult("deleteActivityRecord", result);
      Utils.notify("deleteActivityRecord - " + JSON.stringify(result));
      } catch (error) {
      Utils.logError(error);
    }
  }

  render() {
    return (
      <View style={styles.bg}>
        <Text style={styles.h1}>ACTIVITY RECORDS CONTROLLER</Text>
        <View style={styles.innerBody}>
          <Text style={styles.h2}>
            {"Starting or stopping an activity record"}
          </Text>
          <View style={styles.buttonDataController}>
            <TouchableOpacity
              style={styles.autoRecorderButton}
              onPress={() => this.beginActivityRecord()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> beginActivityRecord </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.autoRecorderButton}
              onPress={() => this.beginBackgroundActivityRecord()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> beginBackgroundActivityRecord </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.autoRecorderButton}
              onPress={() => this.endActivityRecord()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> endActivityRecord </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.autoRecorderButton}
              onPress={() => this.endBackgroundActivityRecord()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> endBackgroundActivityRecord </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.autoRecorderButton}
              onPress={() => this.endAllActivityRecords()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}>
                {" "}
                endAllActivityRecords{" "}
              </Text>
            </TouchableOpacity>
          </View>
        </View>
        <View style={styles.innerBody}>
          <Text style={styles.h2}>
            {"Adding or reading an activity record"}
          </Text>
          <View style={styles.buttonDataController}>
            <TouchableOpacity
              style={styles.autoRecorderButton}
              onPress={() => this.addActivityRecord()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> addActivityRecord </Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.autoRecorderButton}
              onPress={() => this.getActivityRecord()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}> getActivityRecord </Text>
            </TouchableOpacity>
          </View>
        </View>
        <View style={styles.innerBody}>
          <Text style={styles.h2}>{"Deleting an activity record"}</Text>
          <View style={styles.buttonDataController}>
          </View>
          <View style={styles.buttonDataController}>
            <TouchableOpacity
              style={styles.autoRecorderButton}
              onPress={() => this.deleteActivityRecord()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}>deleteActivityRecord</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    );
  }
}
