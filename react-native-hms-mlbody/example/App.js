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
import { View, Text, TouchableOpacity, Image } from 'react-native';
import { styles } from './src/Styles';
import StartPage from './src/StartPage';
import FaceRecognition from './src/HmsFaceBodyRelatedServices/FaceRecognition';
import FaceVerification from './src/HmsFaceBodyRelatedServices/FaceVerification';
import SkeletonDetection from './src/HmsFaceBodyRelatedServices/SkeletonDetection';
import LivenessDetection from './src/HmsFaceBodyRelatedServices/LivenessDetection';
import InteractiveLivenessDetection from './src/HmsFaceBodyRelatedServices/InteractiveLivenessDetection';
import HandKeypointDetection from './src/HmsFaceBodyRelatedServices/HandDetection';
import FaceRecognitionLive from './src/HmsFaceBodyRelatedServices/FaceRecognitionLive';
import SkeletonDetectionLive from './src/HmsFaceBodyRelatedServices/SkeletonDetectionLive';
import HandDetectionLive from './src/HmsFaceBodyRelatedServices/HandDetectionLive';
import GestureDetectionLive from './src/HmsFaceBodyRelatedServices/GestureDetectionLive';
import GestureDetection from './src/HmsFaceBodyRelatedServices/GestureDetection';


const pages = {
  StartPage: {
    screen: StartPage,
    navigationOptions: {
      headerTitle: 'Hms React Native ML Body Kit Demo',
    },
    path: 'start',
  },
  FaceRecognition: {
    screen: FaceRecognition,
    navigationOptions: {
      headerTitle: 'Face Recognition',
    },
    path: 'start/facerecognition',
  },
  FaceVerification: {
    screen: FaceVerification,
    navigationOptions: {
      headerTitle: 'Face Verification',
    },
    path: 'start/faceverification',
  },
  SkeletonDetection: {
    screen: SkeletonDetection,
    navigationOptions: {
      headerTitle: 'Skeleton Detection',
    },
    path: 'start/skeletondetection',
  },
  LivenessDetection: {
    screen: LivenessDetection,
    navigationOptions: {
      headerTitle: 'Liveness Detection',
    },
    path: 'start/livenessdetection',
  },
  InteractiveLivenessDetection: {
    screen: InteractiveLivenessDetection,
    navigationOptions: {
      headerTitle: 'Interactive Liveness Detection',
    },
    path: 'start/interactivelivenessdetection',
  },
  HandKeypointDetection: {
    screen: HandKeypointDetection,
    navigationOptions: {
      headerTitle: 'Hand Keypoint Detection',
    },
    path: 'start/handkeypointdetection',
  },
  FaceRecognitionLive: {
    screen: FaceRecognitionLive,
    navigationOptions: {
      headerTitle: 'Face Recognition Live',
    },
    path: 'start/facerecognitionlive',
  },
  SkeletonDetectionLive: {
    screen: SkeletonDetectionLive,
    navigationOptions: {
      headerTitle: 'Skeleton Detection Live',
    },
    path: 'start/skeletondetectionlive',
  },
  HandDetectionLive: {
    screen: HandDetectionLive,
    navigationOptions: {
      headerTitle: 'Hand Detection Live',
    },
    path: 'start/handkeypointdetectionlive',
  },
  GestureDetectionLive: {
    screen: GestureDetectionLive,
    navigationOptions: {
      headerTitle: 'Gesture Detection Live',
    },
    path: 'start/gesturedetectionlive',
  },
  GestureDetection: {
    screen: GestureDetection,
    navigationOptions: {
      headerTitle: 'Gesture Detection',
    },
    path: 'start/gesturedetection',
  },
};

export default class App extends Component {
  state = {
    pageItem: {
      screen: StartPage,
      navigationOptions: {
        headerTitle: 'Hms React Native ML Body Demo',
      },
      path: 'start',
    },
  }

  changePage = (screenName) => {
    if (pages[screenName]) {
      this.setState({ pageItem: pages[screenName] })
    }
  }

  goBack = () => {
    this.changePage("StartPage");
  }

  render() {
    let { pageItem } = this.state;
    if (pageItem?.screen) {
      let Page = pageItem.screen;
      return (
        <>
          <View style={styles.header}>
            {pageItem.path != "start" && (
              <TouchableOpacity onPress={this.goBack} style={{ marginRight: 20 }}>
                <Image 
                  source={require("./src/Img/back.png")}
                  style={styles.headerImage}
                  resizeMode= "contain"
                />
              </TouchableOpacity>
            )}
            <Text style={styles.headerTitle}>{pageItem.navigationOptions.headerTitle}</Text>
          </View>
          <Page navigation={{ navigate: this.changePage }} />
        </>
      );
    }
    return <StartPage />;
  }
}
