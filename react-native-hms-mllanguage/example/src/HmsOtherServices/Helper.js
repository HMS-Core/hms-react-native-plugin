/*
    Copyright 2023-2024. Huawei Technologies Co., Ltd. All rights reserved.

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

import { HMSApplication } from '@hmscore/react-native-hms-mllanguage';
import { ToastAndroid } from 'react-native';

const options = {
  title: 'Choose Method',
  storageOptions: {
    skipBackup: true,
    path: 'images',
  },
};


export async function setApiKey() {
  try {
    var result = await HMSApplication.setApiKey("Api_Key");
    this.renderResult(result, "Api key set");
  } catch (e) {
    console.log(e);
  }
}

export async function setUserRegion(userRegion) {
  try {
    var result = await HMSApplication.setUserRegion(userRegion);
    this.renderResult(result, "userRegion set");
  } catch (e) {
    console.log(e);
  }
}

export async function getCountryCode() {
  try {
    var result = await HMSApplication.getCountryCode();
    this.renderResult(result, "Get Country Code");
  } catch (e) {
    console.log(e);
  }
}

export async function setAccessToken() {
  try {
    var result = await HMSApplication.setAccessToken("<your_access_token>");
    this.renderResult(result, "Access Token set");
  } catch (e) {
    console.log(e);
  }
} 

renderResult = (result, message) => {
  console.log(result);
  if (result.status == HMSApplication.SUCCESS) {
    ToastAndroid.showWithGravity(message, ToastAndroid.SHORT, ToastAndroid.BOTTOM);
  }
  else {
    ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.BOTTOM);
  }
}

