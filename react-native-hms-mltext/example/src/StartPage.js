/*
 * Copyright 2023-2024. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import React, { Component } from 'react';
import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  PermissionsAndroid,
  BackHandler,
  Alert
} from 'react-native';
import { styles } from './Styles';
import { setApiKey } from './HmsOtherServices/Helper';

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
          PermissionsAndroid.PERMISSIONS.CAMERA,
          PermissionsAndroid.PERMISSIONS.READ_MEDIA_IMAGES,
          PermissionsAndroid.PERMISSIONS.READ_MEDIA_VIDEO,
          PermissionsAndroid.PERMISSIONS.READ_MEDIA_AUDIO,
          PermissionsAndroid.PERMISSIONS.RECORD_AUDIO,
        ]
      );
      if (
        userResponse["android.permission.RECORD_AUDIO"] == PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.RECORD_AUDIO"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.CAMERA"] == PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.CAMERA"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN 
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

        <Text style={styles.h1}>Text Related Services</Text>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('TextRecognition')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Text Recognition</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('TextRecognitionLive')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Live Text Recognition</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('DocumentRecognition')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Document Recognition</Text>
            </TouchableOpacity>
          </View>

        </View>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('GeneralCardRecognition')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Gen. Card Recognition</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('BankCardRecognition')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Bank Card Recognition</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('FormRecognition')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Form Recognition</Text>
            </TouchableOpacity>
          </View>

        </View>

        <View style={styles.containerFlex}>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('VietnamIDCardRecognition')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Vietnam ID Card</Text>
            </TouchableOpacity>
          </View>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('IDCardRecognition')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>ID Card Recognition</Text>
            </TouchableOpacity>
          </View>
        </View>

        <Text style={styles.h1}>Natural Language Processing Services</Text>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('TextEmbedding')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Text Embedding</Text>
            </TouchableOpacity>
          </View>

        </View>
        
      </ScrollView>
    );
  }
}
