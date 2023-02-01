/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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


import React from "react";

import MainPage from "./src/MainPage";
import LocalNotification from "./src/LocalNotification";

import { View, Text, TouchableOpacity, Image } from 'react-native';
import { styles } from "./src/styles"; 

const pages = {
    MainPage: {
      screen: MainPage,
      navigationOptions: {
        headerTitle: "🔔 ReactNative HMS Push Kit Demo",
      },
      path: "app1",
    },
    LocalNotification: {
      screen: LocalNotification,
      navigationOptions: {
        headerTitle: "Push Kit Demo - LocalNotification",
      },
      path: "notif",
    },
  };



export default class App extends React.Component {
  state = {
    pageItem: pages.MainPage,
  }
 
  changePage = (screenName) => {
    if (pages[screenName]) {
      this.setState({ pageItem: pages[screenName] })
    }
  }
 
  goBack = () => {
    this.changePage("MainPage");
  }

  render() {
    let { pageItem } = this.state;
    if (pageItem?.screen) {
      let Page = pageItem.screen;
      return (
        <>
          <View style={styles.header}>
            {pageItem.path != "app1" && (
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
    return <MainPage />;
  }
}
