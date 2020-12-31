/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/text.png')}
                />
              </View>
              <Text style={styles.buttonText}>Text Recognition</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('TextRecognitionLive')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/text-live.png')}
                />
              </View>
              <Text style={styles.buttonText}>Live Text Recognition</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('DocumentRecognition')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/doc.png')}
                />
              </View>
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
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/gcr.png')}
                />
              </View>
              <Text style={styles.buttonText}>Gen. Card Recognition</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('BankCardRecognition')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/bcr.png')}
                />
              </View>
              <Text style={styles.buttonText}>Bank Card Recognition</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('FormRecognition')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/gcr.png')}
                />
              </View>
              <Text style={styles.buttonText}>Form Recognition</Text>
            </TouchableOpacity>
          </View>

        </View>

        <Text style={styles.h1}>Language / Voice Related Services</Text>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('Translation')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/translation.png')}
                />
              </View>
              <Text style={styles.buttonText}>Translate Service</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('LanguageDetection')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/langdetect.png')}
                />
              </View>
              <Text style={styles.buttonText}>Language Detection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('TextToSpeech')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/tts.png')}
                />
              </View>
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
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/asr.png')}
                />
              </View>
              <Text style={styles.buttonText}>Speech Recognition</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('AudioFileTranscription')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/aft.png')}
                />
              </View>
              <Text style={styles.buttonText}>Audio File Transcript</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('RealTimeTranscription')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/rtt.png')}
                />
              </View>
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
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/sounddetect.png')}
                />
              </View>
              <Text style={styles.buttonText}>Sound Detection</Text>
            </TouchableOpacity>
          </View>

        </View>

        <Text style={styles.h1}>Image Related Services</Text>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ImageClassification')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/class.png')}
                />
              </View>
              <Text style={styles.buttonText}>Image{'\n'}Classify</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ClassificationLive')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/class-live.png')}
                />
              </View>
              <Text style={styles.buttonText}>Live Image{'\n'}Classify</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('LandmarkRecognition')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/landmark.png')}
                />
              </View>
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
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/object.png')}
                />
              </View>
              <Text style={styles.buttonText}>Object Detection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ObjectDetectionLive')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/object-live.png')}
                />
              </View>
              <Text style={styles.buttonText}>Live Object Detection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ProductVisualSearch')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/productsegment.png')}
                />
              </View>
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
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/imseg.png')}
                />
              </View>
              <Text style={styles.buttonText}>Image Segment.</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('Frame')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/frame.png')}
                />
              </View>
              <Text style={styles.buttonText}>Frame Methods</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ImageSuperResolution')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/isr.png')}
                />
              </View>
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
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/skew.png')}
                />
              </View>
              <Text style={styles.buttonText}>Doc. Skew Correction</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('TextImageSuperResolution')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/tisr.png')}
                />
              </View>
              <Text style={styles.buttonText}>Text Super Resolution</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('SceneDetection')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/scene.png')}
                />
              </View>
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
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/scene-live.png')}
                />
              </View>
              <Text style={styles.buttonText}>Live Scene Detection</Text>
            </TouchableOpacity>
          </View>

        </View>

        <Text style={styles.h1}>Face Related Services</Text>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('FaceRecognition')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/face.png')}
                />
              </View>
              <Text style={styles.buttonText}>Face Detection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('FaceRecognitionLive')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/face-live.png')}
                />
              </View>
              <Text style={styles.buttonText}>Live Face Detection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('LivenessDetection')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/liveness.png')}
                />
              </View>
              <Text style={styles.buttonText}>Liveness Detection</Text>
            </TouchableOpacity>
          </View>

        </View>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('HandKeypointDetection')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/handkey.png')}
                />
              </View>
              <Text style={styles.buttonText}>Hand Detection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('HandDetectionLive')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/handkey-live.png')}
                />
              </View>
              <Text style={styles.buttonText}>Live Hand Detection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('SkeletonDetection')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/skeleton.png')}
                />
              </View>
              <Text style={styles.buttonText}>Skeleton Detection</Text>
            </TouchableOpacity>
          </View>

        </View>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('SkeletonDetectionLive')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/skeleton-live.png')}
                />
              </View>
              <Text style={styles.buttonText}>Live Skeleton Detection</Text>
            </TouchableOpacity>
          </View>

        </View>

        <Text></Text>

        <Text style={styles.h1}>Natural Language Processing Services</Text>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('TextEmbedding')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/textembed.png')}
                />
              </View>
              <Text style={styles.buttonText}>Text Embedding</Text>
            </TouchableOpacity>
          </View>

        </View>

        <Text style={styles.h1}>Model</Text>

        <View style={styles.containerFlex}>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('CustomModel')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/custom.png')}
                />
              </View>
              <Text style={styles.buttonText}>Custom Model</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('ModelDownload')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/modeldownload.png')}
                />
              </View>
              <Text style={styles.buttonText}>Model Download</Text>
            </TouchableOpacity>
          </View>

        </View>

        <Text></Text>

      </ScrollView>
    );
  }
}
