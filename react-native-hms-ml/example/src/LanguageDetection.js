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

import { HmsLanguageDetection } from 'react-native-hms-ml';
import { styles } from './styles';

export default class LanguageDetection extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      languageCode: '',
      probability: '',
      hashCode: '',
      firstBest: '',
      text: '',
    };
  }

  async createLanguageDetectionSettings() {
    try {
      var result = await HmsLanguageDetection.create({});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async probabilityDetect(textValue) {
    try {
      var result = await HmsLanguageDetection.probabilityDetect(textValue);
      console.log(result);
      this.setState({
        languageCode: result[0]['languageCode'],
        probability: result[0]['probability'],
        hashCode: result[0]['hashCode'],
      });
      this.stop.call();
    } catch (e) {
      console.error(e);
    }
  }



  async firstBestDetect(textValue) {
    try {
      var result = await HmsLanguageDetection.firstBestDetect(textValue);
      console.log(result);
      this.setState({
        firstBest: result,
      });
      this.stop.call();
    } catch (e) {
      console.error(e);
    }
  }

  async stop() {
    try {
      var result = await HmsLanguageDetection.stop();
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
          placeholder="Write Something and Detect Language"
          onChangeText={text => this.setState({ text: text })}
          multiline={true}
        />


        <Text style={styles.title}>Probability Detect Results</Text>

        <TextInput
          style={styles.customInput}
          value={this.state.languageCode}
          placeholder="Language Code"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={`${this.state.probability}`}
          placeholder="Probability"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={`${this.state.hashCode}`}
          placeholder="Hash Code"
          multiline={true}
          editable={false}
        />

        <View style={styles.buttonTts}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.probabilityDetect(this.state.text)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Probability Detect </Text>
          </TouchableOpacity>
        </View>

        <Text style={styles.title}>First Best Detect Result</Text>
        <TextInput
          style={styles.customInput}
          value={this.state.firstBest}
          placeholder="Result"
          multiline={true}
          editable={false}
        />



        <View style={styles.buttonTts}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.firstBestDetect(this.state.text)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> First Best Detect </Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    );
  }
}
