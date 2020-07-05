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
import { Text, View, ScrollView, TextInput, TouchableOpacity } from 'react-native';

import { styles } from './styles';
import { HmsImageSegmentation, HmsFrame } from 'react-native-hms-ml';

import ImagePicker from 'react-native-image-picker';


const options = {
  title: 'Choose Method',
  storageOptions: {
    skipBackup: true,
    path: 'images',
  },
};


export default class Segmentation extends React.Component {


  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      foreground: '',
      grayscale: '',
      original: '',
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


  async createSegmentationSettings() {
    try {
      var result = await HmsImageSegmentation.create({
        analyzerType: HmsImageSegmentation.BODY_SEG,
        scene: HmsImageSegmentation.ALL,
        exact: true
      });
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async analyze() {
    try {
      var result = await HmsImageSegmentation.analyze();
      console.log(result);
      this.setState({
        foreground: result.foreground,
        grayscale: result.grayscale,
        original: result.original,
      });
    } catch (e) {
      console.error(e);
    }
  }

  async close() {
    try {
      var result = await HmsImageSegmentation.stop();
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
    this.createSegmentationSettings()
      .then(() => this.setMLFrame())
      .then(() => this.analyze())
      .then(() => this.close());
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <Text style={styles.title}>Check Your Gallery After Segmentation Complete</Text>

        <TextInput
          style={styles.customInput}
          value={this.state.foreground}
          placeholder="Foreground Uri"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.grayscale}
          placeholder="Grayscale Uri"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.original}
          placeholder="Original Uri"
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
