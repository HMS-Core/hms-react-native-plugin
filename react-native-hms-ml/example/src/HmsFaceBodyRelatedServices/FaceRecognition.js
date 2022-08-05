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
  Switch,
  ToastAndroid,
  Image,
  TextInput
} from 'react-native';
import { HMSFaceRecognition, HMSApplication } from '@hmscore/react-native-hms-ml';
import { showImagePicker } from '../HmsOtherServices/Helper';
import { styles } from '../Styles';

export default class FaceRecognition extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      isEnabled: false,
      emotions: '',
      isAnalyzeEnabled: false
    };
  }

  startAnalyze(isAsync) {
    this.setState({ emotions: '', isAnalyzeEnabled: true }, () => {
      isAsync ? this.asyncAnalyzeFrame() : this.analyzeFrame();
    });
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  getFaceAnalyzerSetting = () => {
    return {
      featureType: HMSFaceRecognition.TYPE_FEATURES,
      shapeType: HMSFaceRecognition.TYPE_SHAPES,
      keyPointType: HMSFaceRecognition.TYPE_KEYPOINTS,
      performanceType: HMSFaceRecognition.TYPE_SPEED,
      tracingMode: HMSFaceRecognition.MODE_TRACING_ROBUST,
      minFaceProportion: 0.5,
      isPoseDisabled: false,
      isTracingAllowed: false,
      isMaxSizeFaceOnly: false
    };
  }

  async asyncAnalyzeFrame() {
    try {
      var result = await HMSFaceRecognition.asyncAnalyzeFrame(this.state.isEnabled, true, this.getFrameConfiguration(), this.getFaceAnalyzerSetting());
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async analyzeFrame() {
    try {
      var result = await HMSFaceRecognition.analyzeFrame(this.state.isEnabled, true, this.getFrameConfiguration(), this.getFaceAnalyzerSetting());
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  parseResult = (result) => {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
      if (result.result.length > 0) {
        this.state.isEnabled ? this.setState({ emotions: "X :" + result.result[0].faceEulerX.toString() + " Y :" + result.result[0].faceEulerY.toString() + " Z :" + result.result[0].faceEulerZ.toString() }) : this.setState({ emotions: JSON.stringify(result.result[0].emotions) });
        ToastAndroid.showWithGravity("Recognition Completed. See console logs for details.", ToastAndroid.LONG, ToastAndroid.CENTER);
      }
      else {
        ToastAndroid.showWithGravity("No Face Found", ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    }
    else {
      ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
    this.setState({ isAnalyzeEnabled: false });
  }

  toggleSwitch = () => {
    this.setState({
      isEnabled: !this.state.isEnabled,
    })
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={styles.viewdividedtwo}>
          <View style={styles.itemOfView}>
            <Text style={{ fontWeight: 'bold', fontSize: 15, alignSelf: "center" }}>
              {(this.state.isEnabled ? '3D' : '2D') + " RECOGNITION "}
            </Text>
          </View>

          <View style={styles.itemOfView3}>
            <Switch
              trackColor={{ false: "#767577", true: "#81b0ff" }}
              thumbColor={this.state.isEnabled ? "#fffff" : "#ffff"}
              onValueChange={this.toggleSwitch.bind(this)}
              value={this.state.isEnabled}
              style={{ alignSelf: 'center' }}
              disabled={this.state.isAnalyzeEnabled}
            />
          </View>
        </View >

        <View style={styles.containerCenter}>
          <TouchableOpacity
            onPress={() => { showImagePicker().then((result) => this.setState({ imageUri: result })) }}
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


        <TextInput
          style={styles.customEditBox2}
          value={this.state.emotions}
          placeholder="Recognition Result for One Face"
          multiline={true}
          scrollEnabled={true}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(true)}
            underlayColor="#fff"
            disabled={this.state.isAnalyzeEnabled}>
            <Text style={styles.startButtonLabel}> ASYNC START </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(false)}
            underlayColor="#fff"
            disabled={this.state.isAnalyzeEnabled}>
            <Text style={styles.startButtonLabel}> SYNC START </Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    );
  }
}
