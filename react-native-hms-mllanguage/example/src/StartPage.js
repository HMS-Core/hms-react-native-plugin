/*
    Copyright 2023. Huawei Technologies Co., Ltd. All rights reserved.

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

import React, { Component } from 'react';
import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  Image,
  PermissionsAndroid,
  BackHandler,
  Alert
} from 'react-native';
import { styles } from './Styles';
import { setApiKey, setAccessToken } from './HmsOtherServices/Helper';


export default class App extends Component {

  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.requestPermissions()
      .then(() => setApiKey());
      
  }
  
  

  async requestPermissions() {
    try {
      const userResponse = await PermissionsAndroid.requestMultiple(
        [
          PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE,
          PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
          PermissionsAndroid.PERMISSIONS.RECORD_AUDIO,
        ]
      );
      if (
        userResponse["android.permission.RECORD_AUDIO"] == PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.RECORD_AUDIO"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.READ_EXTERNAL_STORAGE"] == PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.READ_EXTERNAL_STORAGE"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.WRITE_EXTERNAL_STORAGE"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.WRITE_EXTERNAL_STORAGE"] == PermissionsAndroid.RESULTS.DENIED
      ) {
        this.alertPermission();
      }
    }
    catch (err) {
      console.log(err);
    }
  }

  alertPermission = () =>
    Alert.alert(
      "Permission !",
      "Please allow permissions to use this app",
      [
        {
          text: "OK", onPress: () => BackHandler.exitApp()
        }
      ],
      { cancelable: false }
    );

  render() {
    return (
      <ScrollView style={styles.bg}>

        <Text style={styles.h1}>Language / Voice Related Services</Text>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('Translation')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Translate Service</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('LanguageDetection')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Language Detection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('TextToSpeech')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Text To Speech</Text>
            </TouchableOpacity>
          </View>

        </View>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('AutomaticSpeechRecognition')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Speech Recognition</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('AudioFileTranscription')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Audio File Transcript</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('RealTimeTranscription')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Real Time Transcript</Text>
            </TouchableOpacity>
          </View>

        </View>

        <View style={styles.containerFlex}>



          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('SoundDetection')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Sound Detection</Text>
            </TouchableOpacity>
          </View>
        </View>

        <Text></Text>
        <Text style={styles.h1}>Model</Text>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('CustomModel')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Custom Model</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ModelDownload')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Model Download</Text>
            </TouchableOpacity>
          </View>

        </View>
      </ScrollView>
    );
  }
}
