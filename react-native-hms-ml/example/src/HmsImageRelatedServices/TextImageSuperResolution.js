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
import { HMSTextImageSuperResolution, HMSApplication } from '@hmscore/react-native-hms-ml';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class TextImageSuperResolution extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      corrected: '',
      superWidth: 0,
      superHeight: 0,
      updateWidth: 200,
      updateHeight: 200,
    };
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  async asyncAnalyzeFrame() {
    try {
      var result = await HMSTextImageSuperResolution.asyncAnalyzeFrame(true, this.getFrameConfiguration());
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        Image.getSize(result.result, (width, height) => { this.setState({ superWidth: width, superHeight: height }) });
        this.setState({ corrected: result.result, updateWidth: 300, updateHeight: 300 });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  startAnalyze() {
    this.setState({
      corrected: '',
      superWidth: 0,
      superHeight: 0,
      updateWidth: 200,
      updateHeight: 200,
    });
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

        <Text style={styles.h1}>Corrected Document Result</Text>

        <View style={styles.containerCenter}>
          {this.state.corrected !== '' &&
            <Image
              style={styles.imageSelectView}
              source={{ uri: this.state.corrected }}
            />
          }
        </View>

        <Text style={styles.h1}>Super Resoluted Image Width X Height</Text>

        <TextInput
          style={styles.customInput}
          value={this.state.superWidth.toString() + "X" + this.state.superHeight.toString()}
          placeholder="Super Resolution Image : Width X Height"
          multiline={false}
          editable={false}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.startAnalyze.bind(this)}
            disabled={this.state.imageBase64display == '' ? true : false}
          >
            <Text style={styles.startButtonLabel}> Start Analyze </Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    );
  }
}
