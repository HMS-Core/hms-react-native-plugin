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
import { HmsBcrRecognition, HmsFrame } from 'react-native-hms-ml';

import ImagePicker from 'react-native-image-picker';


const options = {
  title: 'Choose Method',
  storageOptions: {
    skipBackup: true,
    path: 'images',
  },
};


export default class BankCardRecognition extends React.Component {


  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      result: '',
      expire: '',
      image: '',
      cardNumber: '',
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


  async createBcrSettings() {
    try {
      var result = await HmsBcrRecognition.create({ languageType: HmsBcrRecognition.ENGLISH });
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async analyze() {
    try {
      var result = await HmsBcrRecognition.analyze();
      this.setState({
        expire: result.expire || '<expire> not recognized',
        image: result.numberImage || '<image> not recognized',
        cardNumber: result.number || '<cardNumber> not recognized',
      });
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async close() {
    try {
      var result = await HmsBcrRecognition.stop();
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
      expire: 'processing...',
      image: 'processing...',
      cardNumber: 'processing...',
    }, () => {
      this.createBcrSettings()
        .then(() => this.setMLFrame())
        .then(() => this.analyze())
        .then(() => this.close());
    });
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <TextInput
          style={styles.customInput}
          value={this.state.cardNumber}
          placeholder="Card Number"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.expire}
          placeholder="Card Expire"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.image}
          placeholder="Recognized Image Saved URI"
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
