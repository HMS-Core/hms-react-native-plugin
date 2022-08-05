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
  Switch,
  Image
} from 'react-native';
import { styles } from '../Styles';
import { HMSImageClassification } from '@hmscore/react-native-hms-ml';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class ImageClassification extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      isEnabled: false,
      classificationIdentity: [],
      name: [],
      possibility: [],
      hashCode: [],
    };
  }

  getImageClassificationSetting = () => {
    if (this.state.isEnabled) {
      return { maxNumberOfReturns: 5, minAcceptablePossibility: 0.8 };
    }
    else {
      return { minAcceptablePossibility: 0.8 };
    }
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  async asyncAnalyzeFrame() {
    try {
      var result = await HMSImageClassification.asyncAnalyzeFrame(this.state.isEnabled, true, this.getFrameConfiguration(), this.getImageClassificationSetting());
      this.parseResult(result);
    } catch (e) {
      console.error(e);
    }
  }

  async analyzeFrame() {
    try {
      var result = await HMSImageClassification.analyzeFrame(this.state.isEnabled, true, this.getFrameConfiguration(), this.getImageClassificationSetting());
      this.parseResult(result);
    } catch (e) {
      console.error(e);
    }
  }

  parseResult = (result) => {
    console.log(result);
    result.result.forEach(element => {
      this.state.classificationIdentity.push(element.classificationIdentity);
      this.state.name.push(element.name);
      this.state.possibility.push(element.possibility);
    });
    this.setState({ classificationIdentity: this.state.classificationIdentity, name: this.state.name, possibility: this.state.possibility, hashCode: this.state.hashCode });
  }

  startAnalyze(isAsync) {
    this.setState({
      possibility: [],
      hashCode: [],
      name: [],
      classificationIdentity: [],
      result: 'Recognizing...',
    })
    isAsync ? this.asyncAnalyzeFrame() : this.analyzeFrame();
  }

  toggleSwitch = () => {
    this.setState({
      isEnabled: !this.state.isEnabled,
    })
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={styles.viewdividedtwo}>
          <View style={styles.itemOfView}>
            <Text style={{ fontWeight: 'bold', fontSize: 15, alignSelf: "center" }}>
              {"RECOGNITION METHOD : " + (this.state.isEnabled ? 'REMOTE' : 'LOCAL')}
            </Text>
          </View>

          <View style={styles.itemOfView3}>
            <Switch
              trackColor={{ false: "#767577", true: "#81b0ff" }}
              thumbColor={this.state.isEnabled ? "#fffff" : "#ffff"}
              onValueChange={this.toggleSwitch.bind(this)}
              value={this.state.isEnabled}
              style={{ alignSelf: 'center' }}
            />
          </View>
        </View >

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
        <TextInput
          style={styles.customInput}
          value={this.state.classificationIdentity.toString()}
          placeholder="classification identity"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={this.state.possibility.toString()}
          placeholder="possibility"
          multiline={true}
          editable={false}
        />

        <TextInput
          style={styles.customInput}
          value={this.state.name.toString()}
          placeholder="name"
          multiline={true}
          editable={false}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(true)}
            disabled={this.state.imageUri == '' ? true : false}>
            <Text style={styles.startButtonLabel}> START ASYNC </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze(false)}
            disabled={this.state.imageUri == '' ? true : false}>
            <Text style={styles.startButtonLabel}> START SYNC </Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    );
  }
}
