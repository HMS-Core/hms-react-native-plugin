/*
    Copyright 2023. Huawei Technologies Co., Ltd. All rights reserved.

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
import { View, Text, TouchableOpacity, Image } from 'react-native';
import { styles } from './src/Styles';
import StartPage from './src/StartPage';
import Translation from './src/HmsLanguageVoiceRelatedServices/Translation';
import LanguageDetection from './src/HmsLanguageVoiceRelatedServices/LanguageDetection';
import TextToSpeech from './src/HmsLanguageVoiceRelatedServices/TextToSpeech';
import RealTimeTranscription from './src/HmsLanguageVoiceRelatedServices/RealTimeTranscription';
import AudioFileTranscription from './src/HmsLanguageVoiceRelatedServices/AudioFileTranscription';
import SoundDetection from './src/HmsLanguageVoiceRelatedServices/SoundDetection';
import AutomaticSpeechRecognition from './src/HmsLanguageVoiceRelatedServices/AutomaticSpeechRecognition';
import ModelDownload from './src/HmsOtherServices/ModelDownload';
import CustomModel from './src/HmsOtherServices/CustomModel';
const pages = {
  StartPage: {
    screen: StartPage,
    navigationOptions: {
      headerTitle: 'Hms React Native ML Language Demo',
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
};

export default class App extends Component {
  state = {
    pageItem: {
      screen: StartPage,
      navigationOptions: {
        headerTitle: 'Hms React Native ML Language Demo',
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
