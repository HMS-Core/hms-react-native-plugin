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
  TextInput,
  TouchableOpacity,
  ScrollView,
  Switch,
  NativeEventEmitter,
  ToastAndroid
} from 'react-native';
import { styles } from '../Styles';
import {
  HMSTranslate,
  HMSModelDownload,
  HMSApplication
} from '@hmscore/react-native-hms-ml';

export default class Translate extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      text: '',
      result: '',
      isEnabled: false,
      showPreparedModel: false,
    };
  }

  componentDidMount() {
    this.eventEmitter = new NativeEventEmitter(HMSTranslate);

    this.eventEmitter.addListener(HMSTranslate.TRANSLATE_DOWNLOAD_ON_PROCESS, (event) => {
      console.log(event);
      ToastAndroid.showWithGravity(event.alreadyDownloadLength + "/" + event.totalLength + "is downloaded", ToastAndroid.SHORT, ToastAndroid.CENTER);
    });
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSTranslate.TRANSLATE_DOWNLOAD_ON_PROCESS);
  }

  getTranslateSetting = () => {
    return { sourceLanguageCode: HMSTranslate.ENGLISH, targetLanguageCode: HMSTranslate.CHINESE }
  }

  getStrategyConfiguration = () => {
    return { needWifi: true, needCharging: false, needDeviceIdle: false, region: HMSModelDownload.AFILA }
  }

  toggleSwitch = () => {
    this.setState({
      isEnabled: !this.state.isEnabled,
    })
  }

  async preparedModel() {
    try {
      var result = await HMSTranslate.preparedModel(this.getStrategyConfiguration(), this.getTranslateSetting());
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({ result: "Model download Success. Now you can use local analyze" });
      }
      else {
        this.setState({ result: result.message });
      }
    } catch (e) {
      console.log(e);
      this.setState({ result: "This is an " + e });
    }
  }

  async asyncTranslate(sentence) {
    try {
      if (sentence !== "") {
        var result = await HMSTranslate.asyncTranslate(this.state.isEnabled, true, sentence, this.getTranslateSetting());
        console.log(result);
        if (result.status == HMSApplication.SUCCESS) {
          this.setState({ result: result.result });
        }
        else {
          this.setState({ result: result.message });
          if (result.status == HMSApplication.NO_FOUND) {
            this.setState({ showPreparedModel: true });
            ToastAndroid.showWithGravity("Download Using Prepared Button Below", ToastAndroid.SHORT, ToastAndroid.CENTER);
          }
        }
      }
    } catch (e) {
      console.log(e);
      this.setState({ result: "This is an " + e });
    }
  }

  async syncTranslate(sentence) {
    try {
      if (sentence !== "") {
        var result = await HMSTranslate.syncTranslate(this.state.isEnabled, true, sentence, this.getTranslateSetting());
        console.log(result);
        if (result.status == HMSApplication.SUCCESS) {
          this.setState({ result: result.result });
        }
        else {
          this.setState({ result: result.message });
        }
      }
    } catch (e) {
      console.log(e);
      this.setState({ result: "This is an " + e });
    }
  }

  async getAllLanguages() {
    try {
      var result = await HMSTranslate.getAllLanguages(this.state.isEnabled);
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({ result: result.result.toString() });
      }
      else {
        this.setState({ result: result.message });
      }
    } catch (e) {
      console.log(e);
    }
  }

  async syncGetAllLanguages() {
    try {
      var result = await HMSTranslate.syncGetAllLanguages(this.state.isEnabled);
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({ result: result.result.toString() });
      }
      else {
        this.setState({ result: result.message });
      }
    } catch (e) {
      console.log(e);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={styles.viewdividedtwo}>
          <View style={styles.itemOfView}>
            <Text style={{ fontWeight: 'bold', fontSize: 15, alignSelf: "center" }}>
              {"TRANSLATE METHOD : " + (this.state.isEnabled ? 'REMOTE' : 'LOCAL')}
            </Text>
          </View>
          <View style={styles.itemOfView3}>
            <Switch
              trackColor={{ false: "#767577", true: "#81b0ff" }}
              thumbColor={this.state.isEnabled ? "#fffff" : "#ffff"}
              onValueChange={this.toggleSwitch.bind(this)}
              value={this.state.isEnabled}
              style={{ alignSelf: 'center' }} />
          </View>
        </View >

        <TextInput
          style={styles.customEditBox2}
          placeholder="ENGLISH INPUT"
          onChangeText={text => this.setState({ text: text })}
          multiline={true}
          editable={true} />

        <TextInput
          style={styles.customEditBox2}
          value={this.state.result}
          placeholder="CHINESE RESULT"
          multiline={true}
          editable={true} />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.asyncTranslate(this.state.text.trim())}>
            <Text style={styles.startButtonLabel}> ASYNC Translation </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.syncTranslate(this.state.text.trim())}>
            <Text style={styles.startButtonLabel}> SYNC Translation </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.getAllLanguages()}>
            <Text style={styles.startButtonLabel}> ASYNC Language List </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.syncGetAllLanguages()}>
            <Text style={styles.startButtonLabel}> SYNC Language List </Text>
          </TouchableOpacity>
        </View>

        {this.state.showPreparedModel ?
          <View style={styles.basicButton}>
            <TouchableOpacity
              style={styles.startButton}
              onPress={() => this.preparedModel()}>
              <Text style={styles.startButtonLabel}> Prepared Model Download </Text>
            </TouchableOpacity>
          </View>
          :
          <View></View>
        }

      </ScrollView>
    );
  }
}
