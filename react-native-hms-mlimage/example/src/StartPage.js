/*
 * Copyright 2023. Huawei Technologies Co., Ltd. All rights reserved.
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
  Alert,
  Platform
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
      if (Platform.Version >= 30) {
        // For Android 30 (Android 13) and above, use the new permission model
        const userResponse = await PermissionsAndroid.requestMultiple(
          [
            PermissionsAndroid.PERMISSIONS.CAMERA,
            PermissionsAndroid.PERMISSIONS.READ_MEDIA_IMAGES,
            PermissionsAndroid.PERMISSIONS.READ_MEDIA_VIDEO,
            PermissionsAndroid.PERMISSIONS.READ_MEDIA_AUDIO,
            PermissionsAndroid.PERMISSIONS.RECORD_AUDIO,
          ]
        );
        if ( userResponse["android.permission.RECORD_AUDIO"] == PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.RECORD_AUDIO"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.CAMERA"] == PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.CAMERA"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.READ_MEDIA_IMAGES"] == PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.READ_MEDIA_IMAGES"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.READ_MEDIA_VIDEO"] == PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.READ_MEDIA_VIDEO"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
        userResponse["android.permission.READ_MEDIA_AUDIO"] == PermissionsAndroid.RESULTS.DENIED ||
        userResponse["android.permission.READ_MEDIA_AUDIO"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN
        ) {
          this.alertPermission();
        }
      } else {
        // For Android versions below 30, use the old permission model
        const userResponse = await PermissionsAndroid.requestMultiple(
          [
          PermissionsAndroid.PERMISSIONS.CAMERA,
          PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE,
          PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
          PermissionsAndroid.PERMISSIONS.RECORD_AUDIO,
          ]
        );
        if (
          userResponse["android.permission.RECORD_AUDIO"] == PermissionsAndroid.RESULTS.DENIED ||
          userResponse["android.permission.RECORD_AUDIO"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
          userResponse["android.permission.CAMERA"] == PermissionsAndroid.RESULTS.DENIED ||
          userResponse["android.permission.CAMERA"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
          userResponse["android.permission.READ_EXTERNAL_STORAGE"] == PermissionsAndroid.RESULTS.DENIED ||
          userResponse["android.permission.READ_EXTERNAL_STORAGE"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
          userResponse["android.permission.WRITE_EXTERNAL_STORAGE"] == PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN ||
          userResponse["android.permission.WRITE_EXTERNAL_STORAGE"] == PermissionsAndroid.RESULTS.DENIED
        ) {
          this.alertPermission();
        }
      }
    } catch (error) {
      console.warn('Error requesting permission:', error);
    }
  };

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


        <Text style={styles.h1}>Image Related Services</Text>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ImageClassification')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Image{'\n'}Classify</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ClassificationLive')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Live Image{'\n'}Classify</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('LandmarkRecognition')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Landmark Recognition</Text>
            </TouchableOpacity>
          </View>

        </View>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ObjectDetection')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Object Detection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ObjectDetectionLive')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Live Object Detection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ProductVisualSearch')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Product Search</Text>
            </TouchableOpacity>
          </View>

        </View>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ImageSegmentation')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Image Segment.</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('Frame')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Frame Methods</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ImageSuperResolution')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Super Resolution</Text>
            </TouchableOpacity>
          </View>

        </View>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('DocumentSkewCorrection')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Doc. Skew Correction</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('TextImageSuperResolution')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Text Super Resolution</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('SceneDetection')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Scene Detection</Text>
            </TouchableOpacity>
          </View>

        </View>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('SceneDetectionLive')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Live Scene Detection</Text>
            </TouchableOpacity>
          </View>

        </View>

        <Text style={styles.h1}>Common Services</Text>

        <View style={styles.containerFlex}>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('CompositeAnalyzer')}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Composite Analyzer</Text>
            </TouchableOpacity>
          </View>
        </View>

        <Text></Text>

      </ScrollView>
    );
  }
}
