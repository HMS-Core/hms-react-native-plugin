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
  ToastAndroid
} from 'react-native';
import { HMSFrame, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class Frame extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      imageResult: '',
      isAnalyzeEnabled: false,
    };
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  async getPreviewBitmap() {
    try {
      var result = await HMSFrame.getPreviewBitmap(this.getFrameConfiguration());
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async readBitmap() {
    try {
      var result = await HMSFrame.readBitmap(this.getFrameConfiguration());
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async rotate() {
    try {
      var result = await HMSFrame.rotate(HMSFrame.SCREEN_THIRD_QUADRANT, this.state.imageUri);
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  parseResult = (result) => {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
      this.setState({ imageResult: result.result });
    }
    else {
      ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={styles.containerCenter}>
          <TouchableOpacity
            onPress={() => { showImagePicker().then((result) => this.setState({ imageUri: result })) }}
            disabled={this.state.isAnalyzeEnabled}
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

        <Text style={styles.h1}>Result Image</Text>

        <View style={styles.containerCenter}>
          <TouchableOpacity
            onPress={null}
            disabled={this.state.isAnalyzeEnabled}>
          </TouchableOpacity>
          {this.state.imageResult !== '' &&
            <Image
              style={styles.imageSelectView}
              source={{ uri: this.state.imageResult }}
            />
          }
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.getPreviewBitmap.bind(this)}
            disabled={this.state.imageUri == '' ? !this.state.isAnalyzeEnabled : this.state.isAnalyzeEnabled}>
            <Text style={styles.startButtonLabel}> Preview Bitmap </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.readBitmap.bind(this)}
            disabled={this.state.imageUri == '' ? !this.state.isAnalyzeEnabled : this.state.isAnalyzeEnabled}>
            <Text style={styles.startButtonLabel}> Read Bitmap </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.rotate.bind(this)}
            disabled={this.state.imageUri == '' ? !this.state.isAnalyzeEnabled : this.state.isAnalyzeEnabled}>
            <Text style={styles.startButtonLabel}> Rotate </Text>
          </TouchableOpacity>
        </View>

      </ScrollView >
    );
  }
}