/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

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

import "react-native-gesture-handler";

import React from "react";
import { createStackNavigator } from "react-navigation-stack";
import { createAppContainer } from "react-navigation";

import MainPage from "./src/MainPage";
import CustomURI from "./src/CustomURI";
import LocalNotification from "./src/LocalNotification";

const AppNavigator = createStackNavigator(
  {
    MainPage: {
      screen: MainPage,
      navigationOptions: {
        headerTitle: "🔔 ReactNative HMS Push Kit Demo",
      },
      path: "app1",
    },
    CustomURI: {
      screen: CustomURI,
      navigationOptions: {
        headerTitle: "Push Kit Demo - Custom intent URI Page",
      },
      path: "app2",
    },
    LocalNotification: {
      screen: LocalNotification,
      navigationOptions: {
        headerTitle: "Push Kit Demo - LocalNotification",
      },
      path: "notif",
    },
  },
  {
    initialRouteName: "MainPage",
  }
);

const AppContainer = createAppContainer(AppNavigator);

export default class App extends React.Component {
  render() {
    return <AppContainer />;
  }
}
