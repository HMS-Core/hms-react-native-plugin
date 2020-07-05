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
import { HmsFaceRecognition, HmsFrame } from 'react-native-hms-ml';
import { styles } from './styles';
import ImagePicker from 'react-native-image-picker';

const options = {
  title: 'Choose Method',
  storageOptions: {
    skipBackup: true,
    path: 'images',
  },
};

export default class FaceRecognition extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      angryProbability: 0.0,
      disgustProbability: 0.0,
      fearProbability: 0.0,
      neutralProbability: 0.0,
      sadProbability: 0.0,
      smilingProbability: 0.0,
      surpriseProbability: 0.0,
    };
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
      angryProbability: 'processing...',
      disgustProbability: 'processing...',
      fearProbability: 'processing...',
      neutralProbability: 'processing...',
      sadProbability: 'processing...',
      smilingProbability: 'processing...',
      surpriseProbability: 'processing...',
    }, () => {
      this.createFaceRecognitionSettings()
        .then(() => this.setMLFrame())
        .then(() => this.analyze())
        .then(() => this.stop());
    });
  }

  async setMLFrame() {
    try {
      var result = await HmsFrame.fromBitmap(this.state.imageUri);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async createFaceRecognitionSettings() {
    try {
      var result = await HmsFaceRecognition.create({});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async analyze() {
    try {
      var result = await HmsFaceRecognition.analyze();
      console.log(result);

      if (result.length > 0 && result[0]['emotions']) {
        this.setState({
          angryProbability: result[0]['emotions']['angryProbability'],
          disgustProbability: result[0]['emotions']['disgustProbability'],
          fearProbability: result[0]['emotions']['fearProbability'],
          neutralProbability: result[0]['emotions']['neutralProbability'],
          sadProbability: result[0]['emotions']['sadProbability'],
          smilingProbability: result[0]['emotions']['smilingProbability'],
          surpriseProbability: result[0]['emotions']['surpriseProbability'],
        });
      }

    } catch (e) {
      console.error(e);
    }
  }

  async stop() {
    try {
      var result = await HmsFaceRecognition.stop();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <Text style={styles.title}>ANGRY</Text>
        <TextInput
          style={styles.customInput}
          value={`${this.state.angryProbability}`}
          placeholder="angryProbability"
          multiline={true}
          editable={false}
        />
        <Text style={styles.title}>DISGUST</Text>
        <TextInput
          style={styles.customInput}
          value={`${this.state.disgustProbability}`}
          placeholder="disgustProbability"
          multiline={true}
          editable={false}
        />
        <Text style={styles.title}>FEAR</Text>
        <TextInput
          style={styles.customInput}
          value={`${this.state.fearProbability}`}
          placeholder="fearProbability"
          multiline={true}
          editable={false}
        />
        <Text style={styles.title}>NEUTRAL</Text>
        <TextInput
          style={styles.customInput}
          value={`${this.state.neutralProbability}`}
          placeholder="neutralProbability"
          multiline={true}
          editable={false}
        />
        <Text style={styles.title}>SAD</Text>
        <TextInput
          style={styles.customInput}
          value={`${this.state.sadProbability}`}
          placeholder="sadProbability"
          multiline={true}
          editable={false}
        />
        <Text style={styles.title}>SMILING</Text>
        <TextInput
          style={styles.customInput}
          value={`${this.state.smilingProbability}`}
          placeholder="smilingProbability"
          multiline={true}
          editable={false}
        />
        <Text style={styles.title}>SURPRIZE</Text>
        <TextInput
          style={styles.customInput}
          value={`${this.state.surpriseProbability}`}
          placeholder="surpriseProbability"
          multiline={true}
          editable={false}
        />

        <View style={styles.buttonTts}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.showImagePicker()}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Start Analyze </Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    );
  }
}
