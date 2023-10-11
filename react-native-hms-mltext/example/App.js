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
import { View, Text, TouchableOpacity, Image,LogBox } from 'react-native';
import { styles } from './src/Styles';
import StartPage from './src/StartPage';
import TextEmbedding from './src/HmsNaturalLanguageProcessingServices/TextEmbedding';
import TextRecognition from './src/HmsTextRelatedServices/TextRecognition';
import DocumentRecognition from './src/HmsTextRelatedServices/DocumentRecognition';
import BankCardRecognition from './src/HmsTextRelatedServices/BankCardRecognition';
import GeneralCardRecognition from './src/HmsTextRelatedServices/GeneralCardRecognition';
import IDCardRecognition from './src/HmsTextRelatedServices/IDCardRecognition';
import VietnamIDCardRecognition from './src/HmsTextRelatedServices/VietnamIDCardRecognition';
import FormRecognition from './src/HmsTextRelatedServices/FormRecognition';
import TextRecognitionLive from './src/HmsTextRelatedServices/TextRecognitionLive';

const pages = {
  StartPage: {
    screen: StartPage,
    navigationOptions: {
      headerTitle: 'Hms React Native ML Text Kit Demo',
    },
    path: 'start',
  },
  TextEmbedding: {
    screen: TextEmbedding,
    navigationOptions: {
      headerTitle: 'Text Embedding',
    },
    path: 'start/textembedding',
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
  IDCardRecognition: {
    screen: IDCardRecognition,
    navigationOptions: {
      headerTitle: 'ID Card Recognition',
    },
    path: 'start/idcardrecognition',
  },
  VietnamIDCardRecognition: {
    screen: VietnamIDCardRecognition,
    navigationOptions: {
      headerTitle: 'Vietnam ID Card Recognition',
    },
    path: 'start/vietnamidcardrecognition',
  },
  FormRecognition: {
    screen: FormRecognition,
    navigationOptions: {
      headerTitle: 'Form Recognition',
    },
    path: 'start/formrecognition',
  },
  TextRecognitionLive: {
    screen: TextRecognitionLive,
    navigationOptions: {
      headerTitle: 'Text Recognition Live',
    },
    path: 'start/textrecognitionlive',
  },
};

LogBox.ignoreLogs(["EventEmitter"]);

export default class App extends Component {
  state = {
    pageItem: {
      screen: StartPage,
      navigationOptions: {
        headerTitle: 'Hms React Native ML Text Kit Demo',
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
