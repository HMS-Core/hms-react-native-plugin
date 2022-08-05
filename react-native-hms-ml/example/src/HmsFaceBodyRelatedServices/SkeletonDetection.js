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
import { HMSSkeletonDetection, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class SkeletonDetection extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      asyncResult: [],
      syncResult: []
    };
  }

  startAnalyze(isAsync) {
    isAsync ? this.asyncAnalyzeFrame() : this.analyzeFrame();
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  async asyncAnalyzeFrame() {
    try {
      this.setState({ asyncResult: [] });
      var result = await HMSSkeletonDetection.asyncAnalyzeFrame(true, HMSSkeletonDetection.TYPE_NORMAL, this.getFrameConfiguration());
      this.parseResult(result, true);
    } catch (e) {
      console.log(e);
    }
  }

  async analyzeFrame() {
    try {
      this.setState({ syncResult: [] });
      var result = await HMSSkeletonDetection.analyzeFrame(true, HMSSkeletonDetection.TYPE_NORMAL, this.getFrameConfiguration());
      this.parseResult(result, false);
    } catch (e) {
      console.log(e);
    }
  }

  async calculateSimilarity() {
    try {
      var result = await HMSSkeletonDetection.calculateSimilarity(true, HMSSkeletonDetection.TYPE_NORMAL, this.state.asyncResult, this.state.syncResult);
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        ToastAndroid.showWithGravity("Similarity :" + result.result, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  parseResult = (result, isAsync) => {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
      if (isAsync) {
        this.setState({ asyncResult: result.result });
        ToastAndroid.showWithGravity(this.state.asyncResult.length + " skeleton detected", ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
      else {
        this.setState({ syncResult: result.result });
        ToastAndroid.showWithGravity(this.state.syncResult.length + " skeleton detected", ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
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

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.calculateSimilarity()}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> SIMILARITY ASYNC AND SYNC RESULTS </Text>
          </TouchableOpacity>
        </View>


      </ScrollView>
    );
  }
}
