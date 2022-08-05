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

import React from 'react';
import {
  Text,
  View,
  ScrollView,
  TextInput,
  TouchableOpacity,
  NativeEventEmitter
} from 'react-native';
import { HMSSoundDetect, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';

export default class SoundDetection extends React.Component {

  componentDidMount() {
    this.eventEmitter = new NativeEventEmitter(HMSSoundDetect);

    this.eventEmitter.addListener(HMSSoundDetect.SOUND_DETECT_ON_SUCCESS, (event) => {
      console.log(event);
      switch (event.soundType) {
        case HMSSoundDetect.SOUND_EVENT_TYPE_ALARM:
          this.setState({ result: "Your Sound Type : ALARM" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_BABY_CRY:
          this.setState({ result: "Your Sound Type : BABY CRY" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_BARK:
          this.setState({ result: "Your Sound Type : BARK" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_CAR_ALARM:
          this.setState({ result: "Your Sound Type : CAR ALARM" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_DOOR_BELL:
          this.setState({ result: "Your Sound Type : DOOR BELL" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_KNOCK:
          this.setState({ result: "Your Sound Type : KNOCK" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_LAUGHTER:
          this.setState({ result: "Your Sound Type : LAUGHTER" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_MEOW:
          this.setState({ result: "Your Sound Type : MEOW" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_SCREAMING:
          this.setState({ result: "Your Sound Type : SCREAM" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_SNEEZE:
          this.setState({ result: "Your Sound Type : SNEEZE" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_SNORING:
          this.setState({ result: "Your Sound Type : SNORE" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_STEAM_WHISTLE:
          this.setState({ result: "Your Sound Type : WHISTLE" });
          break;
        case HMSSoundDetect.SOUND_EVENT_TYPE_WATER:
          this.setState({ result: "Your Sound Type : WATER" });
          break;
        default:
          break;
      }
    });

    this.eventEmitter.addListener(HMSSoundDetect.SOUND_DETECT_ON_FAILURE, (event) => {
      console.log(event);
      this.setState({ result: "Error Code : " + event.errorCode.toString() });
    });

    this.createSoundDetector()
      .then(() => this.setSoundDetectorListener());
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSSoundDetect.SOUND_DETECT_ON_SUCCESS);
    this.eventEmitter.removeAllListeners(HMSSoundDetect.SOUND_DETECT_ON_FAILURE);

    if (this.state.isDetectorSet) {
      this.destroy();
    }
  }

  constructor(props) {
    super(props);
    this.state = {
      result: '',
      isDetectorSet: false,
      isStarted: false,
    };
  }

  async createSoundDetector() {
    try {
      var result = await HMSSoundDetect.createSoundDetector();
      console.log(result);
      this.setState({ isDetectorSet: true });
    } catch (e) {
      console.log(e);
    }
  }

  async setSoundDetectorListener() {
    try {
      var result = await HMSSoundDetect.setSoundDetectorListener();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async destroy() {
    try {
      var result = await HMSSoundDetect.destroy();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async stop() {
    try {
      var result = await HMSSoundDetect.stop();
      console.log(result);
      this.setState({ result: "Detection Stopped" });
    } catch (e) {
      console.log(e);
    }
  }

  async start() {
    try {
      var result = await HMSSoundDetect.start();
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        if (result.result == false) {
          this.recreate();
        }
        else {
          this.setState({ result: "Detection Started", isStarted: true });
        }
      }
      else {
        this.recreate();
      }
    } catch (e) {
      console.log(e);
    }
  }

  recreate = () => {
    this.destroy()
      .then(() => this.createSoundDetector())
      .then(() => this.setSoundDetectorListener())
      .then(() => this.setState({ result: "Press Start Again", isStarted: false }));
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <Text style={styles.h1}>Start Detection then Try to Generate a Sound Type Given Below</Text>

        <View style={{ alignSelf: 'center', alignContent: 'center' }}>
          <Text >ALARM</Text>
          <Text >BABY CRY</Text>
          <Text >BARK</Text>
          <Text >CAR ALARM</Text>
          <Text >DOOR BELL</Text>
          <Text >KNOCK</Text>
          <Text >LAUGHTER</Text>
          <Text >MEOW</Text>
          <Text >SCREAM</Text>
          <Text >SNEEZE</Text>
          <Text >SNORE</Text>
          <Text >WHISTLE</Text>
          <Text >WATER</Text>
        </View>

        <TextInput
          style={styles.customInput}
          value={this.state.result}
          placeholder="Sound Detect Result"
          multiline={false}
          editable={false}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.start.bind(this)}
            disabled={this.state.isStarted}>
            <Text style={styles.startButtonLabel}> Start Sound Detection </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.stop.bind(this)}
            disabled={this.state.isDetectorSet ? false : true}>
            <Text style={styles.startButtonLabel}> Stop Sound Detection </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.destroy()
              .then(() => this.createSoundDetector()
                .then(() => this.setSoundDetectorListener()
                  .then(() => this.setState({ isDetectorSet: true, isStarted: false, result: 'Sound Detector Destroyed' }))))}
            disabled={this.state.isDetectorSet ? false : true}>
            <Text style={styles.startButtonLabel}> Destroy Sound Detection </Text>
          </TouchableOpacity>
        </View>

      </ScrollView >
    );
  }
}