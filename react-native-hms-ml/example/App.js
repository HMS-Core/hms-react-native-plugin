/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

import 'react-native-gesture-handler';
import React , {Component} from 'react';
import {createStackNavigator} from 'react-navigation-stack';
import {createAppContainer} from 'react-navigation';

import StartPage from './src/StartPage';
import TTS from './src/TTS';
import Translate from './src/Translation';
import TextRecognition from './src/TextRecognition';
import DocumentRecognition from './src/Document';
import BankCardRecognition from './src/BCR';
import IdCardRecognition from './src/ICR';
import Segmentation from './src/Segmentation';
import Landmark from './src/Landmark';  
import LanguageDetection from './src/LanguageDetection';
import Classification from './src/Classification';
import ProductVision from './src/ProductVision';
import ObjectRecognition from './src/ObjectRec';
import FaceRecognition from './src/Face';


const AppNavigator = createStackNavigator(
  {
    StartPage: {
      screen: StartPage,
      navigationOptions: {
        headerTitle: 'RN HMS ML Kit Demo',
      },
      path: 'start',
    },
    TextToSpeech: {
      screen: TTS,
      navigationOptions: {
        headerTitle: 'Text To Speech',
      },
      path: 'start/tts',
    },
    Translate: {
      screen: Translate,
      navigationOptions: {
        headerTitle: 'Translate',
      },
      path: 'start/translate',
    },
    TextRecognition: {
      screen: TextRecognition,
      navigationOptions: {
        headerTitle: 'TextRecognition',
      },
      path: 'start/textrecognition',
    },
    DocumentRecognition: {
      screen: DocumentRecognition,
      navigationOptions: {
        headerTitle: 'DocumentRecognition',
      },
      path: 'start/documentrecognition',
    },
    BankCardRecognition: {
      screen: BankCardRecognition,
      navigationOptions: {
        headerTitle: 'BankCardRecognition',
      },
      path: 'start/bankcardrecognition',
    },
    IdCardRecognition: {
      screen: IdCardRecognition,
      navigationOptions: {
        headerTitle: 'IdCardRecognition',
      },
      path: 'start/idcardrecognition',
    },
    Segmentation: {
      screen: Segmentation,
      navigationOptions: {
        headerTitle: 'Segmentation',
      },
      path: 'start/segmentation',
    },
    Landmark: {
      screen: Landmark,
      navigationOptions: {
        headerTitle: 'Landmark',
      },
      path: 'start/landmark',
    },
    LanguageDetection: {
      screen: LanguageDetection,
      navigationOptions: {
        headerTitle: 'LanguageDetection',
      },
      path: 'start/languagedetection',
    },
    Classification: {
      screen: Classification,
      navigationOptions: {
        headerTitle: 'Classification',
      },
      path: 'start/classification',
    },
    ProductVision: {
      screen: ProductVision,
      navigationOptions: {
        headerTitle: 'ProductVision',
      },
      path: 'start/productvision',
    },
    ObjectRecognition: {
      screen: ObjectRecognition,
      navigationOptions: {
        headerTitle: 'ObjectRecognition',
      },
      path: 'start/objectrecognition',
    },
    FaceRecognition: {
      screen: FaceRecognition,
      navigationOptions: {
        headerTitle: 'FaceRecognition',
      },
      path: 'start/facerecognition',
    },
  }
);

const AppContainer = createAppContainer(AppNavigator);

export default class App extends Component {
  render() {
    return <AppContainer />;
  }
}
