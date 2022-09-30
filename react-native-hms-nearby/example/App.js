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

import React, { Component } from 'react';
import { View, Text, TouchableOpacity, Image } from 'react-native';
import { styles } from "./src/Styles"; 
import StartPage from './src/StartPage';
import Connection from './src/Connection';
import Message from './src/Message';
import Wifi from './src/Wifi';

const pages = {
  StartPage: {
    screen: StartPage,
    navigationOptions: {
      headerTitle: 'HMS RN Nearby Kit Demo',
    },
    path: 'start',
  },
  Connection: {
    screen: Connection,
    navigationOptions: {
      headerTitle: 'Nearby Connection',
    },
    path: 'start/nearbyconnection',
  },
  Message: {
    screen: Message,
    navigationOptions: {
      headerTitle: 'Nearby Message',
    },
    path: 'start/nearbymessage',
  },
  Wifi: {
    screen: Wifi,
    navigationOptions: {
      headerTitle: 'Wifi Share',
    },
    path: 'start/wifishare',
  },
};

export default class App extends Component {
  state = {
    pageItem: pages.StartPage,
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