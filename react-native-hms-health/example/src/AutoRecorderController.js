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
  TextInput,
  TouchableOpacity,
  NativeEventEmitter,
} from "react-native";

import { styles } from "./styles";

import { HmsAutoRecorderController } from "@hmscore/react-native-hms-health";
import Utils from "./Utils";

/**
 * {@link AutoRecorderController} class has sample codes for {@link HmsAutoRecorderController}
 */
export default class AutoRecorderController extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      record: {},
      text: "",
    };
  }

  componentDidMount() {
    Utils.logCall("call componentDidMount - AutoRecorderController");
    const eventEmitter = new NativeEventEmitter(HmsAutoRecorderController);

    this.eventListener = eventEmitter.addListener(
      HmsAutoRecorderController.AUTO_RECORDER_POINT_LISTENER,
      (event) => {
        console.log(event);
        this.setState((state, props) => ({
          text:
            state.text +
            `${event.samplingTime}: Steps: ${event.fieldValues["steps(i)"]}\n`,
        }));
      }
    );

    this.eventListener = eventEmitter;
  }

  componentWillUnmount() {
    try {
      this.eventListener.remove();
    } catch (error) {
      return;
    }
  }

  /**
   * Start record By DataType, the data from sensor will be inserted into database automatically until call Stop
   * Interface.
   *
   * DT_CONTINUOUS_STEPS_TOTAL as sample, after startRecord this type, the total steps will be inserted into
   * database when u shake ur handset.
   *
   * @returns {Promise<void>}
   */
  async startRecord() {
    try {
      Utils.logCall("startRecordByType - AutoRecorderController");
      const dataType = {
        dataType: HmsAutoRecorderController.DT_CONTINUOUS_STEPS_TOTAL,
      };

      const notificationOptions = {
        title: "AutoRecorderDemo",
        text: "It's running",
        subText: "Oh is this subtext?",
        ticker: "Ticker text",
        chronometer: false,
        largeIcon: "hearth.png",
      };

      const result = await HmsAutoRecorderController.startRecord(
        dataType,
        notificationOptions
      );
      this.setState((state, props) => ({
        text: state.text + "** Record Started ***\n",
      }));
      Utils.logResult("startRecordByType", result);
      Utils.notify("startRecordByType - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  /**
   * Stop record By DataType, the data from sensor will NOT be inserted into database automatically
   *
   * DT_CONTINUOUS_STEPS_TOTAL as sample, after stopRecord this type, the total steps will NOT be inserted into
   * database when u shake ur handset
   *
   * @returns {Promise<void>}
   */
  async stopRecord() {
    try {
      Utils.logCall("stopRecordByType - AutoRecorderController");
      const dataType = {
        dataType: HmsAutoRecorderController.DT_CONTINUOUS_STEPS_TOTAL,
      };
      const result = await HmsAutoRecorderController.stopRecord(dataType);
      this.setState((state, props) => ({
        text: state.text + "** Record Started ***\n",
      }));
      Utils.logResult("stopRecordByType", result);
      Utils.notify("stopRecordByType - " + JSON.stringify(result));
    } catch (error) {
      Utils.logError(error);
    }
  }

  render() {
    return (
      <View style={styles.bg}>
        <Text style={styles.h1}>AUTO RECORDER CONTROLLER</Text>
        <View style={styles.innerBody}>
          <Text style={styles.h2}>
            {
              "Starting or stopping automatic recording of accumulated step count by data type"
            }
          </Text>
          <View style={styles.buttonDataController}>
            <TouchableOpacity
              style={styles.autoRecorderButton}
              onPress={() => this.startRecord()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}>
                {"startRecord"}
              </Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.autoRecorderButton}
              onPress={() => this.stopRecord()}
              underlayColor="#fff"
            >
              <Text style={styles.smallButtonLabel}>
                {"stopRecord"}
              </Text>
            </TouchableOpacity>
          </View>
        </View>
        <View style={styles.innerBody}>
          <View
            style={{
              backgroundColor: "white",
              margin: 8,
            }}
          >
            <TextInput
              multiline
              numberOfLines={16}
              value={this.state.text}
              // editable={false}
            />
          </View>
        </View>
      </View>
    );
  }
}
