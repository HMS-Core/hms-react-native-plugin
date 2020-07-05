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

import { HmsLandmarkRecognition, HmsFrame } from 'react-native-hms-ml';
import ImagePicker from 'react-native-image-picker';


const options = {
  title: 'Choose Method',
  storageOptions: {
    skipBackup: true,
    path: 'images',
  },
};


export default class Landmark extends React.Component {


  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      landMark: [],
      landMarkIdentity: [],
      possibility: [],
      bottom: [],
      top: [],
      left: [],
      right: [],

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


  async createLandMarkSettings() {
    try {
      var result = await HmsLandmarkRecognition.create({});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async analyze() {
    try {
      this.state.landMark.length = 0;
      this.state.landMarkIdentity.length = 0;
      this.state.possibility.length = 0;
      this.state.bottom.length = 0;
      this.state.top.length = 0;
      this.state.left.length = 0;
      this.state.right.length = 0;

      var result = await HmsLandmarkRecognition.analyze();

      for (var i = 0; i < result.length; i++) {
        this.state.landMark.push(result[i]['landMark']);
        this.state.landMarkIdentity.push(result[i]['landMarkIdentity']);
        this.state.possibility.push(result[i]['possibility'].toString());
        this.state.bottom.push(result[i]['bottom'].toString());
        this.state.top.push(result[i]['top'].toString());
        this.state.left.push(result[i]['left'].toString());
        this.state.right.push(result[i]['right'].toString());
      }

      console.log(result);
      this.setState({
        landMark: this.state.landMark,
        landMarkIdentity: this.state.landMarkIdentity,
        possibility: this.state.possibility,
        bottom: this.state.bottom,
        top: this.state.top,
        left: this.state.left,
        right: this.state.right,
      });
    } catch (e) {
      console.error(e);
    }
  }

  async close() {
    try {
      var result = await HmsLandmarkRecognition.close();
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
    this.createLandMarkSettings()
      .then(() => this.setMLFrame())
      .then(() => this.analyze())
      .then(() => this.close());
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <TextInput
          style={styles.customInput}
          value={this.state.landMark.toString()}
          placeholder="Landmark"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.landMarkIdentity.toString()}
          placeholder="Identity"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.possibility.toString()}
          placeholder="Possibility"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.bottom.toString()}
          placeholder="Bottom Corner"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.left.toString()}
          placeholder="Left Corner"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.top.toString()}
          placeholder="Top Corner"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.right.toString()}
          placeholder="Right Corner"
          multiline={true}
          editable={false}
        />

        <View style={styles.buttonTts}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.showImagePicker.bind(this)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Start Analyze</Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    );
  }
}
