/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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
import { Text, View, ScrollView, TextInput, TouchableOpacity, Image } from 'react-native';
import { HMSDocumentRecognition, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class DocumentRecognition extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      result: '',
      isAnalyzeEnabled: false,
    };
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  getDocumentConfiguration = () => {
    return {
      borderType: HMSDocumentRecognition.NGON,
      isFingerPrintEnabled: false,
      languageList: [HMSDocumentRecognition.ENGLISH, HMSDocumentRecognition.CHINESE]
    }
  }

  startAnalyze = () => {
    this.setState({
      result: 'Recognizing ... ',
      isAnalyzeEnabled: true,
    }, () => {
      this.asyncAnalyzeFrame();
    });
  }

  async asyncAnalyzeFrame() {
    try {
      var result = await HMSDocumentRecognition.asyncAnalyzeFrame(
        true,
        this.getFrameConfiguration(),
        this.getDocumentConfiguration()
      );
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({ result: result.completeResult, isAnalyzeEnabled: false });
      }
      else {
        this.setState({ result: 'Error Code : ' + result.status.toString() + '\n Error Message :' + result.message, isAnalyzeEnabled: false });
      }
    } catch (e) {
      console.log(e);
      this.setState({ result: "This is an " + e, isAnalyzeEnabled: false });
    }
  }

  async createDocumentAnalyzer() {
    try {
      var result = await HMSDocumentRecognition.createDocumentAnalyzer(
        true,
        this.getFrameConfiguration(),
        this.getDocumentConfiguration()
      );
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({ result: result.completeResult, isAnalyzeEnabled: false });
      }
      else {
        this.setState({ result: 'Error Code : ' + result.status.toString() + '\n Error Message :' + result.message, isAnalyzeEnabled: false });
      }
    } catch (e) {
      console.log(e);
      this.setState({ result: "This is an " + e, isAnalyzeEnabled: false });
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={styles.containerCenter}>
          <TouchableOpacity
            onPress={() => { showImagePicker().then((result) => this.setState({ imageUri: result })) }}
            disabled={this.state.isAnalyzeEnabled}>
            <Image style={styles.imageSelectView} source={this.state.imageUri == '' ? require('../../assets/ml.png') : { uri: this.state.imageUri }} />
          </TouchableOpacity>
        </View>

        <Text style={styles.h1}>Touch Brain and Select Image</Text>

        <TextInput
          style={styles.customEditBox2}
          value={this.state.result}
          placeholder="Recognition Result"
          multiline={true}
          scrollEnabled={true}
          editable={this.state.result == '' ? false : true}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.startAnalyze.bind(this)}
            disabled={this.state.imageUri == '' ? !this.state.isAnalyzeEnabled : this.state.isAnalyzeEnabled}>
            <Text style={styles.startButtonLabel}> Start Recognition </Text>
          </TouchableOpacity>
        </View>

      </ScrollView >
    );
  }
}