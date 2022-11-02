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

import {Platform, ToastAndroid} from 'react-native';

export default class Utils {
  static notify(message) {
    if (Platform.OS === 'android') {
      ToastAndroid.show(message, ToastAndroid.SHORT);
    }
  }

  static logCall(msg) {
    console.log('call ' + msg);
  }

  static logResult(msg, result) {
    console.log('*'.repeat(20 + msg.length));
    console.log('****** ' + msg + ' *****');
    console.log('*'.repeat(20 + msg.length));
    console.log(JSON.stringify(result));
    console.log('*'.repeat(20 + msg.length));
  }

  static logError(error) {
    console.log('*'.repeat(20 + error.length));
    console.log('******ERROR*****');
    console.log('*'.repeat(20 + error.length));
    console.log(JSON.stringify(error));
    console.log('*'.repeat(20 + error.length));
  }
}
