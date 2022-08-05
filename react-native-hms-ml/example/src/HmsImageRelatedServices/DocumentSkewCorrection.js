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
  Image,
  ToastAndroid
} from 'react-native';
import { styles } from '../Styles';
import { HMSDocumentSkewCorrection } from '@hmscore/react-native-hms-ml';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class DocumentSkewCorrection extends React.Component {

  componentDidMount() { }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
    this.state = {
      imageUri: '',
      points: [],
      corrected: '',
    };
  }

  getFrameConfiguration = () => {
    return { filePath: this.state.imageUri };
  }

  async asyncDocumentSkewDetect() {
    try {
      var result = await HMSDocumentSkewCorrection.asyncDocumentSkewDetect(true, this.getFrameConfiguration());
      console.log(result);
      if (result.status == HMSDocumentSkewCorrection.SUCCESS) {
        this.state.points.push({ x: result.result.leftTopPosition.x, y: result.result.leftTopPosition.y });
        this.state.points.push({ x: result.result.rightTopPosition.x, y: result.result.rightTopPosition.y });
        this.state.points.push({ x: result.result.rightBottomPosition.x, y: result.result.rightBottomPosition.y });
        this.state.points.push({ x: result.result.leftBottomPosition.x, y: result.result.leftBottomPosition.y });
        this.setState({ points: this.state.points });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  async asyncDocumentSkewCorrect() {
    try {
      var result = await HMSDocumentSkewCorrection.asyncDocumentSkewCorrect(true, this.getFrameConfiguration(), this.state.points);
      console.log(result);
      if (result.status == HMSDocumentSkewCorrection.SUCCESS) {
        const source = { uri: result.result };
        this.setState({ corrected: source });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.error(e);
    }
  }

  startAnalyze() {
    this.setState({
      points: []
    });
    this.asyncDocumentSkewDetect()
      .then(() => this.asyncDocumentSkewCorrect())
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

        <Text style={styles.h1}>Corrected Document Result</Text>

        <View style={styles.containerCenter}>
          <TouchableOpacity>
            {this.state.corrected !== '' && <Image style={styles.imageSelectView} source={this.state.corrected} />}
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.startAnalyze()}
            disabled={this.state.imageUri == '' ? true : false}>
            <Text style={styles.startButtonLabel}>ASYNC START</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    );
  }
}
