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
  ToastAndroid
} from 'react-native';
import { HMSLivenessDetection, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';

export default class LivenessDetection extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      pitch: 0.0,
      roll: 0.0,
      score: 0.0,
      yaw: 0.0,
      isLive: 0.0
    };
  }

  async setConfig() {
    try {
      var result = await HMSLivenessDetection.setConfig(
        {
          option: HMSLivenessDetection.DETECT_MASK
        }
      );
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        ToastAndroid.showWithGravity("Detect Mask Config is Set", ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  async startDetect() {
    try {
      var result = await HMSLivenessDetection.startDetect();
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({
          pitch: result.result.pitch,
          roll: result.result.roll,
          score: result.result.score,
          yaw: result.result.yaw,
          isLive: result.result.isLive,
        });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <Text style={styles.h1}>Detection Results</Text>

        <TextInput
          style={styles.customInput}
          value={"Pitch :" + this.state.pitch.toString()}
          placeholder="Pitch"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={"Yaw :" + this.state.yaw.toString()}
          placeholder="Yaw"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={"Score :" + this.state.score.toString()}
          placeholder="Score"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={"Islive :" + this.state.isLive.toString()}
          placeholder="IsLive"
          multiline={true}
          editable={false}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.startDetect.bind(this)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Start Detection </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.setConfig.bind(this)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Set Detect Mask Config </Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    );
  }
}
