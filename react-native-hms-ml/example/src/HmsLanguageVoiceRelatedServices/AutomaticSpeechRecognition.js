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
  TouchableOpacity,
  NativeEventEmitter,
  TextInput,
  ToastAndroid,
  ScrollView
} from 'react-native';
import { styles } from '../Styles';
import { HMSAsr, HMSApplication } from '@hmscore/react-native-hms-ml';

export default class AutomaticSpeechRecognition extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      result: '',
      isAsrSet: false,
      listening: false,
    };
  }

  componentDidMount() {

    this.eventEmitter = new NativeEventEmitter(HMSAsr);

    this.eventEmitter.addListener(HMSAsr.ASR_ON_RESULTS, (event) => {
      this.setState({ result: event.result, listening: false });
      console.log(event);
    });

    this.eventEmitter.addListener(HMSAsr.ASR_ON_RECOGNIZING_RESULTS, (event) => {
      this.setState({ result: event.result });
      console.log(event);
    });

    this.eventEmitter.addListener(HMSAsr.ASR_ON_ERROR, (event) => {
      this.setState({ result: event.errorMessage });
      console.log(event);
    });

    this.eventEmitter.addListener(HMSAsr.ASR_ON_START_LISTENING, (event) => {
      this.setState({ result: event.info, listening: true });
      console.log(event);
    });

    this.eventEmitter.addListener(HMSAsr.ASR_ON_STARTING_SPEECH, (event) => {
      this.setState({ result: event.info });
      console.log(event);
    });

    this.eventEmitter.addListener(HMSAsr.ASR_ON_VOICE_DATA_RECEIVED, (event) => {
      console.log(event);
    });

    this.eventEmitter.addListener(HMSAsr.ASR_ON_STATE, (event) => {
      console.log(event);
    });
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSAsr.ASR_ON_STATE);
    this.eventEmitter.removeAllListeners(HMSAsr.ASR_ON_VOICE_DATA_RECEIVED);
    this.eventEmitter.removeAllListeners(HMSAsr.ASR_ON_STARTING_SPEECH);
    this.eventEmitter.removeAllListeners(HMSAsr.ASR_ON_START_LISTENING);
    this.eventEmitter.removeAllListeners(HMSAsr.ASR_ON_ERROR);
    this.eventEmitter.removeAllListeners(HMSAsr.ASR_ON_RECOGNIZING_RESULTS);
    this.eventEmitter.removeAllListeners(HMSAsr.ASR_ON_RESULTS);

    if (this.state.isAsrSet) {
      this.destroy();
    }
  }

  async getLanguages() {
    try {
      var result = await HMSAsr.getLanguages();
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({
          result: result.result.toString()
        });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  async createAsrRecognizer() {
    try {
      var result = await HMSAsr.createAsrRecognizer();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async destroy() {
    try {
      var result = await HMSAsr.destroy();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async startRecognizing() {
    try {
      var result = await HMSAsr.startRecognizing(HMSAsr.LAN_EN_US, HMSAsr.FEATURE_WORD_FLUX);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async startRecognizingPlugin() {
    try {
      var result = await HMSAsr.startRecognizingPlugin(HMSAsr.LAN_EN_US, HMSAsr.FEATURE_WORD_FLUX);
      console.log(result);
      this.setState({ result: result.result })
    } catch (e) {
      console.log(e);
    }
  }

  startAsr = () => {
    this.setState({
      isAsrSet: true,
    }, () => {
      this.createAsrRecognizer()
        .then(() => this.startRecognizing());
    });
  }

  stopAsr = () => {
    this.destroy()
      .then(() => this.setState({ isAsrSet: false, listening: false }));
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <Text style={styles.h1}>Normal and UI Speech Recognition</Text>

        <TextInput
          style={styles.customEditBox2}
          value={this.state.result}
          placeholder="Recognition Result"
          multiline={true}
          scrollEnabled={true}
          editable={this.state.result == '' ? false : true}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.startAsr.bind(this)}
            disabled={this.state.listening ? true : false}>
            <Text style={styles.startButtonLabel}> Start </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.stopAsr.bind(this)}
            disabled={this.state.listening ? false : true}>
            <Text style={styles.startButtonLabel}> Stop </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.getLanguages.bind(this)}>
            <Text style={styles.startButtonLabel}> Get Languages </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.startRecognizingPlugin.bind(this)}
            disabled={this.state.listening ? true : false}>
            <Text style={styles.startButtonLabel}> Start Speech Pickup UI </Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    );
  }
}
