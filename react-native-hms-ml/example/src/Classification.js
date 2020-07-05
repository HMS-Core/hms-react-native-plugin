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
import { HmsClassificationLocal, HmsClassificationRemote, HmsFrame } from 'react-native-hms-ml';

import ImagePicker from 'react-native-image-picker';


const options = {
  title: 'Choose Method',
  storageOptions: {
    skipBackup: true,
    path: 'images',
  },
};


export default class Classification extends React.Component {


  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      isEnabled: false,
      identity: [],
      name: [],
      possibility: [],
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


  async createClassificationSettings() {
    try {

      if (this.state.isEnabled) {
        var result = await HmsClassificationRemote.create({});
      }
      else {
        var result = await HmsClassificationLocal.create({});
      }
      console.log(result);

    } catch (e) {
      console.error(e);
    }
  }

  async analyze() {
    try {
      this.state.identity.length = 0;
      this.state.name.length = 0;
      this.state.possibility.length = 0;
      if (this.state.isEnabled) {
        var uri = await HmsClassificationRemote.analyze();
        for (var i = 0; i < uri.length; i++) {
          this.state.identity.push(uri[i]['identity']);
          this.state.name.push(uri[i]['name']);
          this.state.possibility.push(uri[i]['possibility']);
          this.setState({
            identity: this.state.identity,
            name: this.state.name,
            possibility: this.state.possibility,
          });
        }
      }
      else {
        let uri = await HmsClassificationLocal.analyze();

        for (var i = 0; i < uri.length; i++) {
          this.state.identity.push(uri[i]['identity']);
          this.state.name.push(uri[i]['name']);
          this.state.possibility.push(uri[i]['possibility'].toString());
        }

        this.setState({
          identity: this.state.identity,
          name: this.state.name,
          possibility: this.state.possibility,
        });
      }
      console.log(this.state.identity);
      console.log(this.state.name);
      console.log(this.state.possibility);
    } catch (e) {
      console.error(e);
    }
  }

  async close() {
    try {
      if (this.state.isEnabled) {
        var result = await HmsClassificationRemote.stop();
      }
      else {
        var result = await HmsClassificationLocal.stop();
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
    this.createClassificationSettings()
      .then(() => this.setMLFrame())
      .then(() => this.analyze())
      .then(() => this.close());
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
          value={this.state.identity.toString()}
          placeholder="Identity"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.name.toString()}
          placeholder="Name"
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
