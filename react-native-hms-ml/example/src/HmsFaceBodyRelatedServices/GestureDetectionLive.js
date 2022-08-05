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
  NativeEventEmitter,
  Dimensions,
} from 'react-native';
import { createLensEngine, runWithView, close, release, doZoom, setApiKey } from '../HmsOtherServices/Helper';
import SurfaceView, { HMSLensEngine, HMSGestureDetection } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';


export default class GestureDetectionLive extends React.Component {

  componentDidMount() {

    this.eventEmitter = new NativeEventEmitter(HMSLensEngine);

    this.eventEmitter.addListener(HMSLensEngine.LENS_SURFACE_ON_CREATED, (event) => {
      createLensEngine(
        8, {}
      );
    });

    this.eventEmitter.addListener(HMSLensEngine.LENS_SURFACE_ON_CHANGED, (event) => {
      console.log(event);
    });

    this.eventEmitter.addListener(HMSLensEngine.LENS_SURFACE_ON_DESTROY, (event) => {
      console.log(event);
      close();
    });

    this.eventEmitter.addListener(HMSLensEngine.GESTURE_TRANSACTOR_ON_RESULT, (event) => {
      console.log(event);
      this.setState({ result: event.result.length + " gesture results detected see console log" });
    });

    this.eventEmitter.addListener(HMSLensEngine.GESTURE_TRANSACTOR_ON_DESTROY, (event) => {
      console.log(event);
    });

    Dimensions.addEventListener('change', () => {
      this.state.isLensRun ? close().then(() => runWithView()) : null;
    });
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSLensEngine.LENS_SURFACE_ON_CREATED);
    this.eventEmitter.removeAllListeners(HMSLensEngine.LENS_SURFACE_ON_CHANGED);
    this.eventEmitter.removeAllListeners(HMSLensEngine.LENS_SURFACE_ON_DESTROY);
    this.eventEmitter.removeAllListeners(HMSLensEngine.GESTURE_TRANSACTOR_ON_RESULT);
    this.eventEmitter.removeAllListeners(HMSLensEngine.GESTURE_TRANSACTOR_ON_DESTROY);
    Dimensions.removeEventListener('change');
    release();
    setApiKey();
  }

  constructor(props) {
    super(props);
    this.state = {
      isZoomed: false,
      isLensRun: false,
    };
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <ScrollView style={{ width: '95%', height: 300, alignSelf: 'center' }}>
          <SurfaceView style={{ width: '95%', height: 300, alignSelf: 'center' }} />
        </ScrollView>
        <TextInput
          style={styles.customInput}
          value={this.state.result}
          placeholder="Recognition Result"
          multiline={true}
          scrollEnabled={false}
        />
        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => runWithView().then(() => this.setState({ isLensRun: true }))}>
            <Text style={styles.startButtonLabel}> RUN LENS </Text>
          </TouchableOpacity>
        </View>
        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => close().then(() => this.setState({ isLensRun: false, isZoomed: false }))}
            disabled={!this.state.isLensRun}>
            <Text style={styles.startButtonLabel}> CLOSE LENS </Text>
          </TouchableOpacity>
        </View>
        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.state.isZoomed ? doZoom(0.0).then(() => this.setState({ isZoomed: false })) : doZoom(3.0).then(() => this.setState({ isZoomed: true }))}
            disabled={!this.state.isLensRun}>
            <Text style={styles.startButtonLabel}> {this.state.isZoomed ? 'ZOOM 0X' : 'ZOOM 3X'}  </Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    );
  }
}