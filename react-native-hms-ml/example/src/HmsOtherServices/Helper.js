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

import { HMSLensEngine, HMSApplication } from '@hmscore/react-native-hms-ml';
import { ToastAndroid } from 'react-native';
import ImagePicker from 'react-native-image-picker';

const options = {
  title: 'Choose Method',
  storageOptions: {
    skipBackup: true,
    path: 'images',
  },
};

export async function createLensEngine(analyzer, analyzerConfig) {
  try {
    var result = await HMSLensEngine.createLensEngine(
      analyzer,
      analyzerConfig,
      {
        width: 480,
        height: 540,
        lensType: HMSLensEngine.BACK_LENS,
        automaticFocus: true,
        fps: 20.0,
        flashMode: HMSLensEngine.FLASH_MODE_OFF,
        focusMode: HMSLensEngine.FOCUS_MODE_CONTINUOUS_VIDEO
      }
    )
    this.renderResult(result, "Lens engine creation successfull");
  } catch (error) {
    console.log(error);
  }
}

export async function runWithView() {
  try {
    var result = await HMSLensEngine.runWithView();
    this.renderResult(result, "Lens engine running");
  } catch (error) {
    console.log(error);
  }
}

export async function close() {
  try {
    var result = await HMSLensEngine.close();
    this.renderResult(result, "Lens engine closed");
  } catch (error) {
    console.log(error);
  }
}

export async function doZoom(scale) {
  try {
    var result = await HMSLensEngine.doZoom(scale);
    this.renderResult(result, "Lens engine zoomed");
  } catch (error) {
    console.log(error);
  }
}

export async function photograph() {
  try {
    var result = await HMSLensEngine.photograph();
    this.renderResult(result, "Lens engine photo");
  } catch (error) {
    console.log(error);
  }
}

export async function release() {
  try {
    var result = await HMSLensEngine.release();
    this.renderResult(result, "Lens engine released");
  } catch (error) {
    console.log(error);
  }
}

export async function setApiKey() {
  try {
    var result = await HMSApplication.setApiKey("<your_api_key>");
    this.renderResult(result, "Api key set");
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

export function showImagePicker() {
  var result = new Promise(
    function (resolve, reject) {
      ImagePicker.showImagePicker(options, (response) => {
        if (response.didCancel) {
          resolve('');
        } else if (response.error) {
          resolve('');
        } else {
          resolve(response.uri);
        }
      });
    }
  );
  return result;
}

export function showImagePickerCustom() {
  var result = new Promise(
    function (resolve, reject) {
      ImagePicker.showImagePicker(options, (response) => {
        if (response.didCancel) {
          resolve('');
        } else if (response.error) {
          resolve('');
        } else {
          resolve({uri: response.uri, width: response.width, height: response.height});
        }
      });
    }
  );
  return result;
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

