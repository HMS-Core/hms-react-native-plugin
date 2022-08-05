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
import {
  Text,
  View,
  ScrollView,
  TextInput,
  TouchableOpacity,
  Switch,
  ToastAndroid
} from 'react-native';
import { styles } from '../Styles';
import {
  HMSLanguageDetection,
  HMSApplication
} from '@hmscore/react-native-hms-ml';

export default class LanguageDetection extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      languageCode: [],
      probability: [],
      firstBest: '',
      text: '',
      isEnabled: false,
    };
  }

  async probabilityDetect(textValue) {
    try {
      var result = await HMSLanguageDetection.probabilityDetect(this.state.isEnabled, true, HMSLanguageDetection.PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD, textValue);
      console.log(result);
      this.probabilityDetectResult(result);
    } catch (e) {
      console.log(e);
      this.probabilityDetectError(e);
    }
  }

  async firstBestDetect(textValue) {
    try {
      var result = await HMSLanguageDetection.firstBestDetect(this.state.isEnabled, true, HMSLanguageDetection.FIRST_BEST_DETECTION_LANGUAGE_TRUSTED_THRESHOLD, textValue);
      console.log(result);
      this.firstBestDetectResult(result);
    } catch (e) {
      console.log(e);
      this.firstBestDetectError(e);
    }
  }

  async syncFirstBestDetect(textValue) {
    try {
      var result = await HMSLanguageDetection.syncFirstBestDetect(this.state.isEnabled, true, HMSLanguageDetection.FIRST_BEST_DETECTION_LANGUAGE_TRUSTED_THRESHOLD, textValue);
      console.log(result);
      this.firstBestDetectResult(result);
    } catch (e) {
      console.log(e);
      this.firstBestDetectError(e);
    }
  }

  async syncProbabilityDetect(textValue) {
    try {
      var result = await HMSLanguageDetection.syncProbabilityDetect(this.state.isEnabled, true, HMSLanguageDetection.PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD, textValue);
      console.log(result);
      this.probabilityDetectResult(result);
    } catch (e) {
      console.log(e);
      this.probabilityDetectError(e);
    }
  }

  probabilityDetectResult = (result) => {
    if (result.status == HMSApplication.SUCCESS) {
      result.result.forEach(element => {
        this.state.languageCode.push(element['languageCode']);
        this.state.probability.push(element['probability']);
      });
      this.setState({
        languageCode: this.state.languageCode,
        probability: this.state.probability,
      });
    }
    else {
      this.setState({
        languageCode: [result.message],
        probability: [result.message],
      });
      ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
  }

  probabilityDetectError = (e) => {
    this.setState({
      languageCode: [],
      probability: [],
    });
    ToastAndroid.showWithGravity(e, ToastAndroid.SHORT, ToastAndroid.CENTER);
  }

  firstBestDetectResult = (result) => {
    if (result.status == HMSApplication.SUCCESS) {
      this.setState({
        firstBest: result.result,
      });
    }
    else {
      this.setState({
        firstBest: '',
      });
      ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
  }

  firstBestDetectError = (e) => {
    this.setState({
      firstBest: '',
    });
    ToastAndroid.showWithGravity(e, ToastAndroid.SHORT, ToastAndroid.CENTER);
  }

  startProbabilityDetect = (isSync) => {
    this.setState({
      languageCode: [],
      probability: [],
    });
    isSync ? this.syncProbabilityDetect(this.state.text) : this.probabilityDetect(this.state.text);
  }

  startFirstBestDetect = (isSync) => {
    this.setState({
      firstBest: ''
    });
    isSync ? this.syncFirstBestDetect(this.state.text) : this.firstBestDetect(this.state.text);
  }

  toggleSwitch = () => {
    this.setState({
      isEnabled: !this.state.isEnabled,
    })
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={styles.viewdividedtwo}>
          <View style={styles.itemOfView}>
            <Text style={{ fontWeight: 'bold', fontSize: 15, alignSelf: "center" }}>
              {"Detection Method : " + (this.state.isEnabled ? 'Remote' : 'Local')}
            </Text>
          </View>

          <View style={styles.itemOfView3}>
            <Switch
              trackColor={{ false: "#767577", true: "#81b0ff" }}
              thumbColor={this.state.isEnabled ? "#fffff" : "#ffff"}
              onValueChange={this.toggleSwitch.bind(this)}
              value={this.state.isEnabled}
              style={{ alignSelf: 'center' }}
            />
          </View>
        </View >

        <TextInput
          style={styles.customInput}
          placeholder="Write Something and Detect Language"
          onChangeText={text => this.setState({ text: text })}
          multiline={true}
        />

        <Text style={styles.h1}>Probability Detect Results</Text>

        <TextInput
          style={styles.customInput}
          value={`${this.state.languageCode}`}
          placeholder="Language Code"
          multiline={true}
          editable={true}
        />

        <TextInput
          style={styles.customInput}
          value={`${this.state.probability}`}
          placeholder="Probability"
          multiline={true}
          editable={true}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startProbabilityDetect(false)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Probability Detect </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startProbabilityDetect(true)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Sync Probability Detect </Text>
          </TouchableOpacity>
        </View>

        <Text style={styles.h1}>First Best Detect Result</Text>

        <TextInput
          style={styles.customInput}
          value={this.state.firstBest}
          placeholder="Result"
          multiline={true}
          editable={true}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startFirstBestDetect(false)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> First Best Detect </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startFirstBestDetect(true)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Sync First Best Detect </Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    );
  }
}
