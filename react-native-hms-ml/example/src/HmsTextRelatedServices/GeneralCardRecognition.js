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
import { HMSGeneralCardRecognition, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class GeneralCardRecognition extends React.Component {

  componentDidMount() {
    this.eventEmitter = new NativeEventEmitter(HMSGeneralCardRecognition);

    this.eventEmitter.addListener(HMSGeneralCardRecognition.GCR_IMAGE_SAVE, (event) => {
      console.log(event);
      if (event.status == HMSApplication.SUCCESS) {
        const image = { uri: event.result }
        this.setState({ resultImage: image });
        ToastAndroid.showWithGravity('Images are saved to gallery', ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
      else {
        ToastAndroid.showWithGravity('Image Save Error :' + event.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    });
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSGeneralCardRecognition.GCR_IMAGE_SAVE);
  }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      resultImage: '',
      result: '',
      isImage: false
    };
  }

  parseGcrResult = (result) => {
    console.log(result);
    switch (result.status) {
      case HMSApplication.SUCCESS:
        this.setState({ result: result.stringValue });
        break;
      default:
        this.setState({ result: result.message, imageUri: '' });
        break;
    }
  }

  getGcrCaptureUiConfig = () => {
    return {
      tipTextColor: HMSGeneralCardRecognition.GREEN,
      scanBoxCornerColor: HMSGeneralCardRecognition.CYAN,
      tipText: 'Align Edges...',
      orientation: HMSGeneralCardRecognition.LANDSCAPE
    }
  }

  async capturePreview() {
    try {
      var result = await HMSGeneralCardRecognition.capturePreview("en", this.getGcrCaptureUiConfig());
      this.parseGcrResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async capturePhoto() {
    try {
      var result = await HMSGeneralCardRecognition.capturePhoto("en", this.getGcrCaptureUiConfig());
      this.parseGcrResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async captureImage() {
    try {
      var result = await HMSGeneralCardRecognition.captureImage("en", this.state.imageUri);
      this.parseGcrResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  showImageRecognition = () => {
    this.setState({ isImage: !this.state.isImage, result: '', imageUri: '', resultImage: '' });
  }

  startPreviewAnalyze = () => {
    this.setState({
      result: 'Recognizing ... ',
      imageUri: '',
      resultImage: ''
    }, () => {
      this.capturePreview();
    });
  }

  startPhotoAnalyze = () => {
    this.setState({
      result: 'Recognizing ... ',
      imageUri: '',
      resultImage: ''
    }, () => {
      this.capturePhoto();
    });
  }

  startImageAnalyze = () => {
    this.setState({
      result: 'Recognizing ... ',
      resultImage: ''
    }, () => {
      this.captureImage();
    });
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <Text style={styles.h1}>Choose Your Recognition Method</Text>

        <View style={{ borderBottomWidth: 1, borderBottomColor: '#D3D3D3', alignSelf: 'center', width: '95%' }}>
          <View style={styles.menuButton}>
            <TouchableOpacity
              style={this.state.isImage ? styles.startButtonclicked : styles.startButton}
              onPress={() => { this.setState({ isImage: !this.state.isImage }) }}>
              <Text style={styles.startButtonLabel}> Image </Text>
            </TouchableOpacity>
          </View>

          <View style={styles.menuButton}>
            <TouchableOpacity
              style={styles.startButton}
              onPress={this.startPhotoAnalyze.bind(this)}
              disabled={this.state.isImage ? true : false}>
              <Text style={styles.startButtonLabel}> Capture Photo </Text>
            </TouchableOpacity>
          </View>

          <View style={styles.menuButton}>
            <TouchableOpacity
              style={styles.startButton}
              onPress={this.startPreviewAnalyze.bind(this)}
              disabled={this.state.isImage ? true : false}>
              <Text style={styles.startButtonLabel}>Capture Preview </Text>
            </TouchableOpacity>
          </View>
        </View>

        {this.state.isImage ?
          <View>
            <View style={styles.containerCenter}>
              <TouchableOpacity onPress={() => showImagePicker().then((uri) => this.setState({ imageUri: uri }))}
                style={styles.startButton}>
                <Text style={styles.startButtonLabel}>Select Image</Text>
              </TouchableOpacity>
              {this.state.imageUri !== '' &&
                <Image
                  style={styles.imageSelectView}
                  source={{ uri: this.state.imageUri }}
                />
              }
            </View>

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={this.startImageAnalyze.bind(this)}
                disabled={this.state.imageUri == '' ? true : false}>
                <Text style={styles.startButtonLabel}> Start Analyze From Image </Text>
              </TouchableOpacity>
            </View>
          </View>
          :
          <View>
          </View>
        }

        {this.state.result != '' ?
          <View>
            <View>
              <Text style={styles.h1}>Live Recognition Results</Text>
              <View style={styles.containerCenter}>
                {this.state.resultImage !== '' &&
                  <Image
                    style={styles.imageSelectView}
                    source={this.state.resultImage}
                  />
                }
              </View>
            </View>
            <TextInput
              style={styles.customEditBox2}
              value={this.state.result}
              placeholder="Recognition Result"
              multiline={true}
              scrollEnabled={true}
              editable={this.state.result == '' ? false : true} />
          </View>
          :
          <View>
          </View>}

      </ScrollView >
    );
  }
}