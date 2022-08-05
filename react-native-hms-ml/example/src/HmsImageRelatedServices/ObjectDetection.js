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
  ToastAndroid
} from 'react-native';
import { styles } from '../Styles';
import { HMSObjectRecognition, HMSApplication } from '@hmscore/react-native-hms-ml';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class ObjectRecognition extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      tracingIdentity: [],
      typeIdentity: [],
      typePossibility: [],
    };
  }

  getObjectAnalyzerSetting = () => {
    return { analyzerType: HMSObjectRecognition.TYPE_PICTURE, allowClassification: true, allowMultiResults: true };
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  async asyncAnalyzeFrame() {
    try {
      var result = await HMSObjectRecognition.asyncAnalyzeFrame(true, this.getFrameConfiguration(), this.getObjectAnalyzerSetting());
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async analyzeFrame() {
    try {
      var result = await HMSObjectRecognition.analyzeFrame(true, this.getFrameConfiguration(), this.getObjectAnalyzerSetting());
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  parseResult = (result) => {
    console.log(result);

    if (result.status == HMSApplication.SUCCESS) {
      result.result.forEach(element => {
        this.state.tracingIdentity.push(element.tracingIdentity);
        this.state.typeIdentity.push(element.typeIdentity);
        this.state.typePossibility.push(element.typePossibility);
      });
      this.setState({
        tracingIdentity: this.state.tracingIdentity,
        typeIdentity: this.state.typeIdentity,
        typePossibility: this.state.typePossibility,
      });
    }
    else {
      ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      this.setState({
        tracingIdentity: [],
        typeIdentity: [],
        typePossibility: [],
      });
    }
  }

  startAnalyze(isAsync) {
    this.setState({
      tracingIdentity: [],
      typeIdentity: [],
      typePossibility: [],
    })
    isAsync ? this.asyncAnalyzeFrame() : this.analyzeFrame();
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={styles.containerCenter}>
          <TouchableOpacity onPress={() => { showImagePicker().then((result) => this.setState({ imageUri: result })) }}
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

        <View style={{ alignSelf: 'center', alignContent: 'center' }}>
          <Text >{HMSObjectRecognition.TYPE_OTHER} - TYPE_OTHER</Text>
          <Text >{HMSObjectRecognition.TYPE_FACE} - TYPE_FACE</Text>
          <Text >{HMSObjectRecognition.TYPE_FOOD} - TYPE_FOOD</Text>
          <Text >{HMSObjectRecognition.TYPE_FURNITURE} - TYPE_FURNITURE</Text>
          <Text >{HMSObjectRecognition.TYPE_GOODS} - TYPE_GOODS</Text>
          <Text >{HMSObjectRecognition.TYPE_PLACE} - TYPE_PLACE</Text>
          <Text >{HMSObjectRecognition.TYPE_PLANT} - TYPE_PLANT</Text>
        </View>

        <TextInput
          style={styles.customInput}
          value={this.state.tracingIdentity.toString()}
          placeholder="tracingIdentity"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.typeIdentity.toString()}
          placeholder="typeIdentity"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.typePossibility.toString()}
          placeholder="typePossibility"
          multiline={true}
          editable={false}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(true)}
            disabled={this.state.imageUri == '' ? true : false}>
            <Text style={styles.startButtonLabel}> ASYNC START </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(false)}
            disabled={this.state.imageUri == '' ? true : false}>
            <Text style={styles.startButtonLabel}> SYNC START </Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    );
  }
}