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
  Image,
  ToastAndroid
} from 'react-native';
import { styles } from '../Styles';
import { HMSLandmarkRecognition, HMSApplication } from '@hmscore/react-native-hms-ml';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class LandmarkRecognition extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      landmark: [],
      coordinates: [],
      possibility: []
    };
  }

  getLandmarkAnalyzerSetting = () => {
    return { largestNumOfReturns: 10, patternType: HMSLandmarkRecognition.STEADY_PATTERN };
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  async asyncAnalyzeFrame() {
    try {
      var result = await HMSLandmarkRecognition.asyncAnalyzeFrame(true, this.getFrameConfiguration(), this.getLandmarkAnalyzerSetting());
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        result.result.forEach(element => {
          this.state.landmark.push(element.landMark);
          this.state.possibility.push(element.possibility);
          long = [];
          lat = [];
          element.coordinates.forEach(ll => {
            long.push(ll.longitude);
            lat.push(ll.latitude);
          })
          this.state.coordinates.push(lat, long);
        });
        this.setState({
          landMark: this.state.landmark,
          possibility: this.state.possibility,
          coordinates: this.state.coordinates,
        });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.error(e);
    }
  }

  startAnalyze() {
    this.setState({
      landmark: [],
      possibility: [],
      coordinates: [],
    })
    this.asyncAnalyzeFrame();
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
        <TextInput
          style={styles.customInput}
          value={this.state.landmark.toString()}
          placeholder="landmarks"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.coordinates.toString()}
          placeholder="coordinates"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.possibility.toString()}
          placeholder="possibility"
          multiline={true}
          editable={false}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.startAnalyze.bind(this)}
            disabled={this.state.imageUri == '' ? true : false} >
            <Text style={styles.startButtonLabel}> Start Analyze </Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    );
  }
}
