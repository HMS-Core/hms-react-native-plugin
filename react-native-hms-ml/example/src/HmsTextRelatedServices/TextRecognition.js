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
import { Text, View, ScrollView, TextInput, TouchableOpacity, Switch, Image } from 'react-native';
import { HMSTextRecognition, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class TextRecognition extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      isEnabled: false,
      result: '',
      resultSync: [],
      isAnalyzeEnabled: false,
    };
  }

  async asyncAnalyzeFrame() {
    try {
      var result = await HMSTextRecognition.asyncAnalyzeFrame(this.state.isEnabled, true, this.getFrameConfiguration(), this.getTextSetting());
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

  toggleSwitch = () => {
    this.setState({
      isEnabled: !this.state.isEnabled,
    })
  }

  getTextSetting = () => {
    var textRecognitionSetting;
    if (this.state.isEnabled) {
      textRecognitionSetting = {
        textDensityScene: HMSTextRecognition.OCR_LOOSE_SCENE,
        borderType: HMSTextRecognition.NGON,
        languageList: ["en"]
      }
    }
    else {
      textRecognitionSetting = {
        language: "en",
        OCRMode: HMSTextRecognition.OCR_DETECT_MODE
      }
    }
    return textRecognitionSetting;
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  getAnalyzerConfiguration = () => {
    return {
      language: "en",
      OCRMode: HMSTextRecognition.OCR_DETECT_MODE
    };
  }

  startAnalyze = () => {
    this.setState({
      result: 'Recognizing ...',
      resultSync: [],
      isAnalyzeEnabled: true,
    }, () => {
      this.asyncAnalyzeFrame();
    });
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={styles.viewdividedtwo}>
          <View style={styles.itemOfView}>
            <Text style={{ fontWeight: 'bold', fontSize: 15, alignSelf: "center" }}>
              {"RECOGNITION ASYNC: " + (this.state.isEnabled ? 'ON-CLOUD' : 'ON-DEVICE')}
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
          editable={(this.state.result == 'Recognizing ...' || this.state.result == '') ? false : true}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze()}
            disabled={this.state.imageUri == '' ? !this.state.isAnalyzeEnabled : this.state.isAnalyzeEnabled}>
            <Text style={styles.startButtonLabel}> START ASYNC </Text>
          </TouchableOpacity>
        </View>

      </ScrollView >
    );
  }
}