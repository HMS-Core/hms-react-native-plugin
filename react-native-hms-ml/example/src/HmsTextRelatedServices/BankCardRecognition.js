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
  Image,
  ToastAndroid,
  NativeEventEmitter
} from 'react-native';
import { HMSBankCardRecognition, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';

export default class BankCardRecognition extends React.Component {

  componentDidMount() {
    this.eventEmitter = new NativeEventEmitter(HMSBankCardRecognition);

    this.eventEmitter.addListener(HMSBankCardRecognition.BCR_IMAGE_SAVE, (event) => {
      console.log(event);
      if (event.status == HMSApplication.SUCCESS) {
        const image = { uri: event.result }
        this.setState({ numberBitmap: image });
        ToastAndroid.showWithGravity('Images are saved to gallery', ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
      else {
        ToastAndroid.showWithGravity('Image Save Error :' + event.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    });
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSBankCardRecognition.BCR_IMAGE_SAVE);
  }

  constructor(props) {
    super(props);
    this.state = {
      numberBitmap: '',
      expire: '',
      issuer: '',
      number: '',
      organization: '',
      type: ''
    };
  }

  getBcrSetting = () => {
    return {
      orientation: HMSBankCardRecognition.ORIENTATION_PORTRAIT,
      resultType: HMSBankCardRecognition.RESULT_ALL,
      recMode: HMSBankCardRecognition.SIMPLE_MODE
    };
  }

  async captureFrame() {
    try {
      var result = await HMSBankCardRecognition.captureFrame(this.getBcrSetting());
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        if (result.errorCode == HMSBankCardRecognition.ERROR_CODE_INIT_CAMERA_FAILED) {
          ToastAndroid.showWithGravity('Error Code : ' + result.errorCode.toString() + '\n Error Message : Camera failed', ToastAndroid.SHORT, ToastAndroid.CENTER);
        }
        else {
          this.setState({
            expire: result.expire,
            issuer: result.issuer,
            number: result.number,
            organization: result.organization,
            type: result.type
          });
        }
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  startBankCardRecognition = () => {
    this.setState({
      expire: 'Recognizing ... ',
      issuer: 'Recognizing ... ',
      number: 'Recognizing ... ',
      organization: 'Recognizing ... ',
      type: 'Recognizing ... '
    }, () => {
      this.captureFrame();
    });
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <Text style={styles.h1}>Recognition Results</Text>
        <View style={styles.containerCenter}>
          {this.state.numberBitmap !== '' &&
            <Image
              style={styles.imageSelectView}
              source={this.state.numberBitmap}
            />
          }
        </View>

        <TextInput
          style={styles.customInput}
          value={this.state.expire}
          placeholder="Expire"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.issuer}
          placeholder="Issuer"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.number}
          placeholder="Number"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.organization}
          placeholder="Organization"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.type}
          placeholder="Type"
          multiline={true}
          editable={false}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.startBankCardRecognition.bind(this)}>
            <Text style={styles.startButtonLabel}> Start BCR </Text>
          </TouchableOpacity>
        </View>

      </ScrollView >
    );
  }
}