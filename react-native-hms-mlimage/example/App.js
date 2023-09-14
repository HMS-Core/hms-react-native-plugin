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
import { View, Text, TouchableOpacity, Image, LogBox} from 'react-native';
import { styles } from './src/Styles';
import StartPage from './src/StartPage';
import ImageClassification from './src/HmsImageRelatedServices/ImageClassification';
import ObjectDetection from './src/HmsImageRelatedServices/ObjectDetection';
import LandmarkRecognition from './src/HmsImageRelatedServices/LandmarkRecognition';
import ImageSegmentation from './src/HmsImageRelatedServices/ImageSegmentation';
import ProductVisualSearch from './src/HmsImageRelatedServices/ProductVisualSearch';
import ImageSuperResolution from './src/HmsImageRelatedServices/ImageSuperResolution';
import DocumentSkewCorrection from './src/HmsImageRelatedServices/DocumentSkewCorrection';
import TextImageSuperResolution from './src/HmsImageRelatedServices/TextImageSuperResolution';
import SceneDetection from './src/HmsImageRelatedServices/SceneDetection';
import Frame from './src/HmsImageRelatedServices/Frame';
import ClassificationLive from './src/HmsImageRelatedServices/ImageClassificationLive';
import ObjectDetectionLive from './src/HmsImageRelatedServices/ObjectDetectionLive';
import SceneDetectionLive from './src/HmsImageRelatedServices/SceneDetectionLive';
import CompositeAnalyzer from './src/HmsOtherServices/HMSCompositeAnalyzer';

const pages = {
  StartPage: {
    screen: StartPage,
    navigationOptions: {
      headerTitle: 'Hms React Native ML Image Kit Demo',
    },
    path: 'start',
  },
  ImageClassification: {
    screen: ImageClassification,
    navigationOptions: {
      headerTitle: 'Image Classification',
    },
    path: 'start/classification',
  },
  ObjectDetection: {
    screen: ObjectDetection,
    navigationOptions: {
      headerTitle: 'Object Detection',
    },
    path: 'start/objectdetection',
  },
  LandmarkRecognition: {
    screen: LandmarkRecognition,
    navigationOptions: {
      headerTitle: 'Landmark Recognition',
    },
    path: 'start/landmarkrecognition',
  },
  ImageSegmentation: {
    screen: ImageSegmentation,
    navigationOptions: {
      headerTitle: 'Image Segmentation',
    },
    path: 'start/imseg',
  },
  ProductVisualSearch: {
    screen: ProductVisualSearch,
    navigationOptions: {
      headerTitle: 'Product Vision Search',
    },
    path: 'start/productvision',
  },
  ImageSuperResolution: {
    screen: ImageSuperResolution,
    navigationOptions: {
      headerTitle: 'Image Super Resolution',
    },
    path: 'start/imagesuperresolution',
  },
  DocumentSkewCorrection: {
    screen: DocumentSkewCorrection,
    navigationOptions: {
      headerTitle: 'Document Skew Correction',
    },
    path: 'start/documentskewcorrection',
  },
  TextImageSuperResolution: {
    screen: TextImageSuperResolution,
    navigationOptions: {
      headerTitle: 'Text Image Super Resolution',
    },
    path: 'start/textimagesuperresolution',
  },
  SceneDetection: {
    screen: SceneDetection,
    navigationOptions: {
      headerTitle: 'Scene Detection',
    },
    path: 'start/scenedetection',
  },
  Frame: {
    screen: Frame,
    navigationOptions: {
      headerTitle: 'Frame',
    },
    path: 'start/frame',
  },
  ClassificationLive: {
    screen: ClassificationLive,
    navigationOptions: {
      headerTitle: 'Image Classification Live',
    },
    path: 'start/classificationlive',
  },
  ObjectDetectionLive: {
    screen: ObjectDetectionLive,
    navigationOptions: {
      headerTitle: 'Object Detection Live',
    },
    path: 'start/objectdetectionlive',
  },
  SceneDetectionLive: {
    screen: SceneDetectionLive,
    navigationOptions: {
      headerTitle: 'Scene Detection Live',
    },
    path: 'start/scenedetectionlive',
  },
  CompositeAnalyzer: {
    screen: CompositeAnalyzer,
    navigationOptions: {
      headerTitle: 'Composite Analyzer',
    },
    path: 'start/compositeanalyzer',
  },
};

LogBox.ignoreLogs(["EventEmitter"]);

export default class App extends Component {
  state = {
    pageItem: {
      screen: StartPage,
      navigationOptions: {
        headerTitle: 'Hms React Native ML Image Kit Demo',
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
