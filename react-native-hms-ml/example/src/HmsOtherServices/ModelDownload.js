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
  TouchableOpacity,
  Switch,
  ToastAndroid,
  TextInput,
  NativeEventEmitter
} from 'react-native';
import { HMSModelDownload, HMSTextToSpeech, HMSTranslate, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';

export default class ModelDownload extends React.Component {

  componentDidMount() {
    this.eventEmitter = new NativeEventEmitter(HMSModelDownload);

    this.eventEmitter.addListener(HMSModelDownload.DOWNLOAD_ON_PROCESS, (event) => {
      console.log(event);
      this.setState({ downloadStatus: event.alreadyDownloadLength + '/' + event.totalLength });
    });
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSModelDownload.DOWNLOAD_ON_PROCESS);
  }

  constructor(props) {
    super(props);
    this.state = {
      isEnabled: false,
      isOperationStart: false,
      downloadStatus: '',
      isExistState: false,
    };
  }

  getStrategyConfiguration = () => {
    return { needWifi: true, needCharging: false, needDeviceIdle: false, region: HMSModelDownload.AFILA }
  }

  async download() {
    try {
      if (this.state.isExistState == false) {
        var result = await HMSModelDownload.downloadModel(this.getModelInformation(), this.getStrategyConfiguration());
        console.log(result);
        if (result.status == HMSApplication.SUCCESS) {
          ToastAndroid.showWithGravity("Download Completed", ToastAndroid.SHORT, ToastAndroid.CENTER);
        }
        else {
          ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
        }
      } else {
        ToastAndroid.showWithGravity("Model exist in this device", ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
      this.setState({ isOperationStart: false, isExistState: false });
    } catch (e) {
      console.log(e);
      this.setState({ isOperationStart: false, isExistState: false });
    }
  }

  async delete() {
    try {
      if (this.state.isExistState == true) {
        var result = await HMSModelDownload.deleteModel(this.getModelInformation());
        console.log(result);
        if (result.status == HMSApplication.SUCCESS) {
          ToastAndroid.showWithGravity("Model deleted", ToastAndroid.SHORT, ToastAndroid.CENTER);
        }
        else {
          ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
        }
      }
      else {
        ToastAndroid.showWithGravity("Model does not exist in this device", ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
      this.setState({ isOperationStart: false, isExistState: false });
    } catch (e) {
      console.log(e);
      this.setState({ isOperationStart: false, isExistState: false });
    }
  }

  async isModelExist() {
    try {
      var result = await HMSModelDownload.isModelExist(this.getModelInformation());
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({ isExistState: result.result });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
        this.setState({ isExistState: false });
      }
    } catch (e) {
      console.log(e);
      this.setState({ isExistState: false });
    }
  }

  async getRecentModelFile() {
    try {
      var result = await HMSModelDownload.getRecentModelFile(this.getModelInformation());
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({ downloadStatus: result.result });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  async getModels(tag) {
    try {
      var result = await HMSModelDownload.getModels(tag);
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({ downloadStatus: result.result.toString() });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  getModelInformation = () => {
    if (this.state.isEnabled) {
      return { tts: { speakerName: HMSTextToSpeech.TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE } };
    } else {
      return { translate: { languageCode: HMSTranslate.CHINESE } };
    }
  }

  toggleSwitch = () => {
    this.setState({
      isEnabled: !this.state.isEnabled,
      isOperationStart: false,
    })
  }

  startDownload = () => {
    this.setState({
      isOperationStart: true
    }, () => {
      this.isModelExist()
        .then(() => this.download());
    });

  }

  startDelete = () => {
    this.setState({
      isOperationStart: true
    }, () => {
      this.isModelExist()
        .then(() => this.delete());
    });
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <Text style={styles.h1}>Select Your Model and Use Methods Below</Text>

        <View style={styles.viewdividedtwo}>
          <View style={styles.itemOfView}>
            <Text style={{ fontWeight: 'bold', fontSize: 15, alignSelf: "center" }}>
              {"Model Name : " + (this.state.isEnabled ? 'Text To Speech' : 'Translate')}
            </Text>
          </View>

          <View style={styles.itemOfView3}>
            <Switch
              trackColor={{ false: "#767577", true: "#81b0ff" }}
              thumbColor={this.state.isEnabled ? "#fffff" : "#ffff"}
              onValueChange={this.toggleSwitch.bind(this)}
              value={this.state.isEnabled}
              style={{ alignSelf: 'center' }}
              disabled={this.state.isOperationStart}
            />
          </View>
        </View >

        <TextInput
          style={styles.customInput}
          value={this.state.downloadStatus}
          placeholder="Download Status"
          multiline={true}
          editable={false}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startDownload()}
            disabled={this.state.isOperationStart}>
            <Text style={styles.startButtonLabel}> DOWNLOAD </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startDelete()}
            disabled={this.state.isOperationStart}>
            <Text style={styles.startButtonLabel}> DELETE</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.getModels(HMSModelDownload.MODEL_TTS_TAG)}
            disabled={this.state.isOperationStart}>
            <Text style={styles.startButtonLabel}> GET TTS MODELS</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.getModels(HMSModelDownload.MODEL_TRANSLATE_TAG)}
            disabled={this.state.isOperationStart}>
            <Text style={styles.startButtonLabel}> GET TRANSLATE MODELS</Text>
          </TouchableOpacity>
        </View>


        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.getRecentModelFile()}
            disabled={this.state.isOperationStart}>
            <Text style={styles.startButtonLabel}> GET RECENT MODELS</Text>
          </TouchableOpacity>
        </View>

      </ScrollView >
    );
  }
}