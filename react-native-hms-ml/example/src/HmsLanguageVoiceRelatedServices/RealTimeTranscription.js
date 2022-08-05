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
  NativeEventEmitter,
  ToastAndroid
} from 'react-native';
import { HMSSpeechRtt, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';

export default class RealTimeTranscription extends React.Component {

  componentDidMount() {
    this.setRealTimeTranscriptionListener();

    this.eventEmitter = new NativeEventEmitter(HMSSpeechRtt);

    this.eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_RECOGNIZING_RESULTS, (event) => {
      console.log(event);
      this.setState({ result: event.text });
    });

    this.eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_ERROR, (event) => {
      console.log(event);
      this.setState({ result: event.error.toString() + " / " + event.errorMessage });
    });

    this.eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_LISTENING, (event) => {
      console.log(event);
      this.setState({ result: event.info });
    });

    this.eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_STARTING_OF_SPEECH, (event) => {
      console.log(event);
      this.setState({ result: event.info });
    });

    this.eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_VOICE_DATA_RECEIVED, (event) => {
      console.log(event);
    });

    this.eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_STATE, (event) => {
      console.log(event);
    });
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSSpeechRtt.SPEECH_RTT_ON_RECOGNIZING_RESULTS);
    this.eventEmitter.removeAllListeners(HMSSpeechRtt.SPEECH_RTT_ON_ERROR);
    this.eventEmitter.removeAllListeners(HMSSpeechRtt.SPEECH_RTT_ON_LISTENING);
    this.eventEmitter.removeAllListeners(HMSSpeechRtt.SPEECH_RTT_ON_STARTING_OF_SPEECH);
    this.eventEmitter.removeAllListeners(HMSSpeechRtt.SPEECH_RTT_ON_VOICE_DATA_RECEIVED);
    this.eventEmitter.removeAllListeners(HMSSpeechRtt.SPEECH_RTT_ON_STATE);

    if (this.state.recognitionStart) {
      this.destroy();
    }
  }

  constructor(props) {
    super(props);
    this.state = {
      result: '',
      recognitionStart: false,
    };
  }

  getSpeechRttConfig = () => {
    return {
      language: HMSSpeechRtt.LAN_EN_US,
      enablePunctuation: true,
      enableSentenceTimeOffset: true,
      enableWordTimeOffset: true
    };
  }

  async destroy() {
    try {
      var result = await HMSSpeechRtt.destroy();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async startRecognizing() {
    try {
      var result = await HMSSpeechRtt.startRecognizing(this.getSpeechRttConfig());
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async getLanguages() {
    try {
      var result = await HMSSpeechRtt.getLanguages();
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

  async setRealTimeTranscriptionListener() {
    try {
      var result = await HMSSpeechRtt.setRealTimeTranscriptionListener();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <Text style={styles.h1}>Real Time Speech Transcription</Text>

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
            onPress={() => this.startRecognizing().then(() => this.setState({ recognitionStart: true }))}
          >
            <Text style={styles.startButtonLabel}> Start Speech Rtt </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.destroy().then(() => this.setState({ recognitionStart: false }))}
          >
            <Text style={styles.startButtonLabel}> Stop Speech Rtt </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.getLanguages.bind(this)}>
            <Text style={styles.startButtonLabel}> Get Languages </Text>
          </TouchableOpacity>
        </View>

      </ScrollView >
    );
  }
}