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
  Image
} from 'react-native';
import { HMSFormRecognition, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class FormRecognition extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      result: '',
      isAnalyzeEnabled: false
    };
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  async asyncAnalyzeFrame() {
    try {
      var result = await HMSFormRecognition.asyncAnalyzeFrame(true, this.getFrameConfiguration());
      this.handleResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async analyzeFrame() {
    try {
      var result = await HMSFormRecognition.analyzeFrame(true, this.getFrameConfiguration());
      this.handleResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  startAnalyze = (isAsync) => {
    this.setState({
      result: 'Recognizing ... ',
      isAnalyzeEnabled: true,
    }, () => {
      isAsync ? this.asyncAnalyzeFrame() : this.analyzeFrame();
    });
  }

  handleResult = (result) => {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
      this.setState({ result: "Success. Please See Debug Log for detailed table content result." });
    }
    else {
      this.setState({ result: 'Error Code : ' + result.status.toString() + '\n Error Message :' + result.message });
    }
    this.setState({ isAnalyzeEnabled: false });
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

        <TextInput
          style={styles.customEditBox2}
          value={this.state.result}
          placeholder="Recognition Result"
          multiline={true}
          scrollEnabled={true}
          editable={this.state.result == '' ? false : true} />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(true)}
            disabled={this.state.imageUri == '' || this.state.isAnalyzeEnabled ? true : false}>
            <Text style={styles.startButtonLabel}> ASYNC Recognition</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(false)}
            disabled={this.state.imageUri == '' || this.state.isAnalyzeEnabled ? true : false}>
            <Text style={styles.startButtonLabel}> SYNC Recognition </Text>
          </TouchableOpacity>
        </View>

      </ScrollView >
    );
  }
}