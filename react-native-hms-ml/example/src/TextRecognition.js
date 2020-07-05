/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

import React from 'react';
import { Text, View, ScrollView, TextInput, TouchableOpacity, Switch } from 'react-native';
import { HmsTextRecognitionLocal, HmsTextRecognitionRemote, HmsFrame } from 'react-native-hms-ml';

import { styles } from './styles';


import ImagePicker from 'react-native-image-picker';


const options = {
  title: 'Choose Method',
  storageOptions: {
    skipBackup: true,
    path: 'images',
  },
};


export default class TextRecognition extends React.Component {


  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      isEnabled: false,
      result: '',
    };
  }

  async setMLFrame() {
    try {
      var result = await HmsFrame.fromBitmap(this.state.imageUri);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }


  async createTextSettings() {
    try {

      if (this.state.isEnabled) {
        var result = await HmsTextRecognitionRemote.create({});
      }
      else {
        var result = await HmsTextRecognitionLocal.create({});
      }
      console.log(result);

    } catch (e) {
      console.error(e);
    }
  }

  async analyze() {
    try {
      if (this.state.isEnabled) {
        var result = await HmsTextRecognitionRemote.analyze(false);
      }
      else {
        var result = await HmsTextRecognitionLocal.analyze(false);
      }
      this.setState({ result: result });
    } catch (e) {
      console.error(e);
    }
  }

  async close() {
    try {
      if (this.state.isEnabled) {
        var result = await HmsTextRecognitionRemote.close();
      }
      else {
        var result = await HmsTextRecognitionLocal.close();
      }
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }


  showImagePicker() {
    ImagePicker.showImagePicker(options, (response) => {

      if (response.didCancel) {
        console.log('User cancelled image picker');
      } else if (response.error) {
        console.log('ImagePicker Error: ', response.error);
      } else {
        this.setState({
          imageUri: response.uri,
        });

        this.startAnalyze();
      }
    });
  }

  startAnalyze() {
    this.setState({
      result: 'processing...',
    }, () => {
      this.createTextSettings()
        .then(() => this.setMLFrame())
        .then(() => this.analyze())
        .then(() => this.close());
    });
  }


  toggleSwitch = () => {
    this.setState({
      isEnabled: !this.state.isEnabled,
    })
  }


  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={styles.container}>
          <Text>{this.state.isEnabled ? 'REMOTE' : 'LOCAL'}</Text>
          <Switch
            trackColor={{ false: "#767577", true: "#81b0ff" }}
            thumbColor={this.state.isEnabled ? "#f5dd4b" : "#f4f3f4"}
            onValueChange={this.toggleSwitch.bind(this)}
            value={this.state.isEnabled}

          />
        </View>

        <TextInput
          style={styles.customEditBox}
          value={this.state.result}
          placeholder="Text Recognition Result"
          multiline={true}
          editable={false}
        />

        <View style={styles.buttonTts}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.showImagePicker.bind(this)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Start Analyze </Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    );
  }
}
