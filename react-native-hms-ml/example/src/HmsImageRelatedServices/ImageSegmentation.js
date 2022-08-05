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
  Image,
  ToastAndroid,
  Switch
} from 'react-native';
import { styles } from '../Styles';
import { HMSImageSegmentation, HMSApplication } from '@hmscore/react-native-hms-ml';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class ImageSegmentation extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      foreground: '',
      grayscale: '',
      isBodySeg: true,
    };
  }

  getImSegSetting = () => {
    return { analyzerType: this.state.isBodySeg ? HMSImageSegmentation.BODY_SEG : HMSImageSegmentation.HAIR_SEG,
       scene: HMSImageSegmentation.ALL, exact: true };
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  async analyzeFrame() {
    try {
      var result = await HMSImageSegmentation.analyzeFrame(true, this.getFrameConfiguration(), this.getImSegSetting());
      this.parseResult(result, false);
    } catch (e) {
      console.log(e);
    }
  }

  async asyncAnalyzeFrame() {
    try {
      var result = await HMSImageSegmentation.asyncAnalyzeFrame(true, this.getFrameConfiguration(), this.getImSegSetting());
      this.parseResult(result, true);
    } catch (e) {
      console.log(e);
    }
  }

  parseResult = (result, isAsync) => {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
      if (isAsync) {
        const first = { uri: result.result.foreground };
        const second = { uri: result.result.grayscale };
        this.setState({ foreground: first, grayscale: second });
      }
      else {
        const first = { uri: result.result[0].foreground };
        const second = { uri: result.result[0].grayscale };
        this.setState({ foreground: first, grayscale: second });
      }
      if(!this.state.isBodySeg) {
        this.setState({ foreground: ''});
      }
    }
    else {
      ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
  }

  startAnalyze(isAsync) {
    this.setState({
      foreground: '',
      grayscale: '',
    })
    isAsync ? this.asyncAnalyzeFrame() : this.analyzeFrame();
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={styles.containerCenter}>
          <View style={styles.spaceBetweenRow}>
          <Text style={styles.boldText}>Hair Segmentation</Text>
          <Switch
            thumbColor="#2196F3"
            trackColor={{ false: "#b2dfdc", true: "#b2dfdc" }}
            onValueChange={() => this.setState({isBodySeg: !this.state.isBodySeg})}
            value={this.state.isBodySeg}
          />
          <Text style={styles.boldText}>Body Segmentation</Text>
          </View>
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

        <Text style={styles.h1}>Results</Text>

        <View style={styles.containerCenter}>
          {this.state.foreground !== '' && this.state.foreground.uri !== undefined &&
            <Image
              style={styles.imageSelectView}
              source={this.state.foreground}
            />
          }
        </View>

        <View style={styles.containerCenter}>
          {this.state.grayscale !== '' &&
            <Image
              style={styles.imageSelectView}
              source={this.state.grayscale}
            />
          }
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(true)}
            disabled={this.state.imageUri == '' ? true : false}>
            <Text style={styles.startButtonLabel}> ASYNC START </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(false)}
            disabled={this.state.imageUri == '' ? true : false}>
            <Text style={styles.startButtonLabel}> SYNC START </Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    );
  }
}
