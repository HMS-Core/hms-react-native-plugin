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

import React, { Component } from 'react';
import { createStackNavigator } from 'react-navigation-stack';
import { createAppContainer } from 'react-navigation';
import StartPage from './src/StartPage';
import Connection from './src/Connection';
import Message from './src/Message';
import Wifi from './src/Wifi';


const AppNavigator = createStackNavigator(
  {
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
      path: 'start/nearbyconnection',
    },
    Wifi: {
      screen: Wifi,
      navigationOptions: {
        headerTitle: 'Wifi Share',
      },
      path: 'start/wifishare',
    },
  }
);

const AppContainer = createAppContainer(AppNavigator);

export default class App extends Component {
  render() {
    return <AppContainer />;
  }
}