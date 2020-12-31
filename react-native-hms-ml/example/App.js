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

import 'react-native-gesture-handler';
import React, { Component } from 'react';
import { createStackNavigator } from 'react-navigation-stack';
import { createAppContainer } from 'react-navigation';
import StartPage from './src/StartPage';
import Translation from './src/HmsLanguageVoiceRelatedServices/Translation';
import LanguageDetection from './src/HmsLanguageVoiceRelatedServices/LanguageDetection';
import TextToSpeech from './src/HmsLanguageVoiceRelatedServices/TextToSpeech';
import RealTimeTranscription from './src/HmsLanguageVoiceRelatedServices/RealTimeTranscription';
import AudioFileTranscription from './src/HmsLanguageVoiceRelatedServices/AudioFileTranscription';
import SoundDetection from './src/HmsLanguageVoiceRelatedServices/SoundDetection';
import AutomaticSpeechRecognition from './src/HmsLanguageVoiceRelatedServices/AutomaticSpeechRecognition';
import TextEmbedding from './src/HmsNaturalLanguageProcessingServices/TextEmbedding';
import CustomModel from './src/HmsOtherServices/CustomModel';
import ModelDownload from './src/HmsOtherServices/ModelDownload';
import TextRecognition from './src/HmsTextRelatedServices/TextRecognition';
import DocumentRecognition from './src/HmsTextRelatedServices/DocumentRecognition';
import BankCardRecognition from './src/HmsTextRelatedServices/BankCardRecognition';
import GeneralCardRecognition from './src/HmsTextRelatedServices/GeneralCardRecognition';
import FormRecognition from './src/HmsTextRelatedServices/FormRecognition';
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
import FaceRecognition from './src/HmsFaceBodyRelatedServices/FaceRecognition';
import SkeletonDetection from './src/HmsFaceBodyRelatedServices/SkeletonDetection';
import LivenessDetection from './src/HmsFaceBodyRelatedServices/LivenessDetection';
import HandKeypointDetection from './src/HmsFaceBodyRelatedServices/HandDetection';
import TextRecognitionLive from './src/HmsTextRelatedServices/TextRecognitionLive';
import ClassificationLive from './src/HmsImageRelatedServices/ImageClassificationLive';
import ObjectDetectionLive from './src/HmsImageRelatedServices/ObjectDetectionLive';
import SceneDetectionLive from './src/HmsImageRelatedServices/SceneDetectionLive';
import FaceRecognitionLive from './src/HmsFaceBodyRelatedServices/FaceRecognitionLive';
import SkeletonDetectionLive from './src/HmsFaceBodyRelatedServices/SkeletonDetectionLive';
import HandDetectionLive from './src/HmsFaceBodyRelatedServices/HandDetectionLive';

const AppNavigator = createStackNavigator(
  {
    StartPage: {
      screen: StartPage,
      navigationOptions: {
        headerTitle: 'Hms React Native ML Kit Demo',
      },
      path: 'start',
    },
    Translation: {
      screen: Translation,
      navigationOptions: {
        headerTitle: 'Translate Service',
      },
      path: 'start/translation',
    },
    LanguageDetection: {
      screen: LanguageDetection,
      navigationOptions: {
        headerTitle: 'Language Detection',
      },
      path: 'start/languagedetection',
    },
    TextToSpeech: {
      screen: TextToSpeech,
      navigationOptions: {
        headerTitle: 'Text to Speech',
      },
      path: 'start/tts',
    },
    AudioFileTranscription: {
      screen: AudioFileTranscription,
      navigationOptions: {
        headerTitle: 'Audio File Transcription',
      },
      path: 'start/aft',
    },
    RealTimeTranscription: {
      screen: RealTimeTranscription,
      navigationOptions: {
        headerTitle: 'Speech Real Time Transcription',
      },
      path: 'start/srtt',
    },
    SoundDetection: {
      screen: SoundDetection,
      navigationOptions: {
        headerTitle: 'Sound Detection',
      },
      path: 'start/sounddect',
    },
    AutomaticSpeechRecognition: {
      screen: AutomaticSpeechRecognition,
      navigationOptions: {
        headerTitle: 'Automatic Speech Recognition',
      },
      path: 'start/asr',
    },
    TextEmbedding: {
      screen: TextEmbedding,
      navigationOptions: {
        headerTitle: 'Text Embedding',
      },
      path: 'start/textembedding',
    },
    CustomModel: {
      screen: CustomModel,
      navigationOptions: {
        headerTitle: 'Custom Model',
      },
      path: 'start/custommodel',
    },
    ModelDownload: {
      screen: ModelDownload,
      navigationOptions: {
        headerTitle: 'Model Download',
      },
      path: 'start/modeldownload',
    },
    TextRecognition: {
      screen: TextRecognition,
      navigationOptions: {
        headerTitle: 'Text Recognition',
      },
      path: 'start/textrecognition',
    },
    DocumentRecognition: {
      screen: DocumentRecognition,
      navigationOptions: {
        headerTitle: 'Document Recognition',
      },
      path: 'start/documentrecognition',
    },
    BankCardRecognition: {
      screen: BankCardRecognition,
      navigationOptions: {
        headerTitle: 'Bank Card Recognition',
      },
      path: 'start/bankcardrecognition',
    },
    GeneralCardRecognition: {
      screen: GeneralCardRecognition,
      navigationOptions: {
        headerTitle: 'General Card Recognition',
      },
      path: 'start/generalcardrecognition',
    },
    FormRecognition: {
      screen: FormRecognition,
      navigationOptions: {
        headerTitle: 'Form Recognition',
      },
      path: 'start/formrecognition',
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
    FaceRecognition: {
      screen: FaceRecognition,
      navigationOptions: {
        headerTitle: 'Face Recognition',
      },
      path: 'start/facerecognition',
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
    HandKeypointDetection: {
      screen: HandKeypointDetection,
      navigationOptions: {
        headerTitle: 'Hand Keypoint Detection',
      },
      path: 'start/handkeypointdetection',
    },
    TextRecognitionLive: {
      screen: TextRecognitionLive,
      navigationOptions: {
        headerTitle: 'Text Recognition Live',
      },
      path: 'start/textrecognitionlive',
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
  }
);

const AppContainer = createAppContainer(AppNavigator);

export default class App extends Component {
  render() {
    return <AppContainer />;
  }
}
