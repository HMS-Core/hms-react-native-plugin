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
  TouchableOpacity,
  ToastAndroid,
  Image
} from 'react-native';
import { HMSApplication, HMSHandKeypointDetection } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class HandKeypointDetection extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
    };
  }

  startAnalyze(isAsync) {
    isAsync ? this.asyncAnalyzeFrame() : this.analyzeFrame();
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  getFaceAnalyzerSetting = () => {
    return {
      sceneType: HMSHandKeypointDetection.TYPE_KEYPOINT_ONLY,
      maxHandResults: HMSHandKeypointDetection.MAX_HANDS_NUM
    };
  }

  async asyncAnalyzeFrame() {
    try {
      var result = await HMSHandKeypointDetection.asyncAnalyzeFrame(true, this.getFrameConfiguration(), this.getFaceAnalyzerSetting());
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async analyzeFrame() {
    try {
      var result = await HMSHandKeypointDetection.analyzeFrame(true, this.getFrameConfiguration(), this.getFaceAnalyzerSetting());
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  parseResult = (result) => {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
      ToastAndroid.showWithGravity(result.result.length + " keypoints array detected. Please see the debug logs for details.", ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
    else {
      ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={styles.containerCenter}>
          <TouchableOpacity onPress={() => { showImagePicker().then((result) => this.setState({ imageUri: result })) }}
            style={styles.startButton}>
            <Text style={styles.startButtonLabel}>Select Image</Text>
          </TouchableOpacity>
          {this.state.imageUri !== '' &&
            <Image
              style={styles.imageSelectView}
              source={{ uri: this.state.imageUri }}
            />
          }
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(true)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> ASYNC START </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(false)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> SYNC START </Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    );
  }
}
