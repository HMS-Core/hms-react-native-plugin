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
import { HmsIcrRecognition, HmsFrame } from 'react-native-hms-ml';

import ImagePicker from 'react-native-image-picker';


const options = {
  title: 'Choose Method',
  storageOptions: {
    skipBackup: true,
    path: 'images',
  },
};


export default class IdCardRecognition extends React.Component {


  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      isEnabled: false,
      result: '',
      name: '',
      nation: '',
      address: '',
      authority: '',
      birthday: '',
      idNumber: '',
      sex: '',
      validateDate: '',
      cardSide: 0,
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


  async createIdCardSettings() {
    try {
      var result = await HmsIcrRecognition.create({ countryCode: "CN", sideType: HmsIcrRecognition.FRONT }, this.state.isEnabled);
      console.log(result);

    } catch (e) {
      console.error(e);
    }
  }

  async analyze() {
    try {
      var result = await HmsIcrRecognition.analyze(this.state.isEnabled);
      console.log(result);
      this.setState({
        name: result.name || '<name> not recognized',
        nation: result.nation || '<nation> not recognized',
        address: result.address || '<address> not recognized',
        authority: result.authority || '<authority> not recognized',
        birthday: result.birthday || '<birthday> not recognized',
        idNumber: result.idNumber || '<idNumber> not recognized',
        sex: result.sex || '<sex> not recognized',
        validateDate: result.validateDate || '<validateDate> not recognized',
        cardSide: result.cardSide || '<cardSide> not recognized',
      });
    } catch (e) {
      console.error(e);
    }
  }

  async close() {
    try {
      var result = await HmsIcrRecognition.stop(this.state.isEnabled);
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
      name: 'processing...',
      nation: 'processing...',
      address: 'processing...',
      authority: 'processing...',
      birthday: 'processing...',
      idNumber: 'processing...',
      sex: 'processing...',
      validateDate: 'processing...',
      cardSide: 'processing...',
    }, () => {
      this.createIdCardSettings()
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
          style={styles.customInput}
          value={this.state.name}
          placeholder="Name"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.nation}
          placeholder="Nation"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.authority}
          placeholder="Authority"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.address}
          placeholder="Address"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.birthday}
          placeholder="Birthday"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.idNumber}
          placeholder="Id Number"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.sex}
          placeholder="Sex"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.validateDate}
          placeholder="Validate Date"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          placeholder="Card Side"
          value={`${this.state.cardSide}`}
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
