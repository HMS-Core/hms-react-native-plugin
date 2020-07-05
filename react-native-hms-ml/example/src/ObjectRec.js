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

import { styles } from './styles';
import { HmsObjectRecognition, HmsFrame } from 'react-native-hms-ml';
import ImagePicker from 'react-native-image-picker';

const options = {
  title: 'Choose Method',
  storageOptions: {
    skipBackup: true,
    path: 'images',
  },
};

export default class ObjectRecognition extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      tracingIdentity: [],
      typeIdentity: [],
      typePossibility: [],
      bottom: [],
      left: [],
      right: [],
      top: [],
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
    this.createObjectRecognitionSettings()
      .then(() => this.setMLFrame())
      .then(() => this.analyze())
      .then(() => this.stop());
  }


  async setMLFrame() {
    try {
      var result = await HmsFrame.fromBitmap(this.state.imageUri);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async createObjectRecognitionSettings() {
    try {
      var result = await HmsObjectRecognition.create({
        analyzerType: HmsObjectRecognition.TYPE_PICTURE,
        allowClassification: true
      });
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async analyze(textValue) {
    try {

      this.state.tracingIdentity.length = 0;
      this.state.typeIdentity.length = 0;
      this.state.typePossibility.length = 0;
      this.state.bottom.length = 0;
      this.state.top.length = 0;
      this.state.left.length = 0;
      this.state.right.length = 0;

      var result = await HmsObjectRecognition.analyze();
      console.log(result);

      for (var i = 0; i < result.length; i++) {
        this.state.tracingIdentity.push(result[i]['tracingIdentity'].toString());
        this.state.typeIdentity.push(result[i]['typeIdentity'].toString());
        this.state.typePossibility.push(result[i]['typePossibility'].toString());
        this.state.bottom.push(result[i]['bottom'].toString());
        this.state.top.push(result[i]['top'].toString());
        this.state.left.push(result[i]['left'].toString());
        this.state.right.push(result[i]['right'].toString());
      }

      this.setState({
        tracingIdentity: this.state.tracingIdentity,
        typeIdentity: this.state.typeIdentity,
        typePossibility: this.state.typePossibility,
        bottom: this.state.bottom,
        left: this.state.left,
        right: this.state.right,
        top: this.state.top,
      });

    } catch (e) {
      console.error(e);
    }
  }

  async stop() {
    try {
      var result = await HmsObjectRecognition.stop();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <TextInput
          style={styles.customInput}
          value={this.state.tracingIdentity.toString()}
          placeholder="tracingIdentity"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.typeIdentity.toString()}
          placeholder="typeIdentity"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.typePossibility.toString()}
          placeholder="typePossibility"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.bottom.toString()}
          placeholder="bottom"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.left.toString()}
          placeholder="left"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.right.toString()}
          placeholder="right"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.top.toString()}
          placeholder="top"
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
