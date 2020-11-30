/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

import { NativeModules, DeviceEventEmitter, AppRegistry } from 'react-native';

const { HMSActivityIdentification } = NativeModules;

export const registerActivityIdentificationHeadlessTask = (callback) => {
  AppRegistry.registerHeadlessTask(
    HMSActivityIdentification.Events.ACTIVITY_IDENTIFICATION_RESULT,
    () => async (taskData) => {
      callback(taskData);
    }
  );
};

export const addActivityIdentificationEventListener = callback => {
    DeviceEventEmitter.addListener(
        HMSActivityIdentification.Events.ACTIVITY_IDENTIFICATION_RESULT,
        callback,
    );
};

export const removeActivityIdentificationEventListener = async (callback) => {
    DeviceEventEmitter.removeListener(
        HMSActivityIdentification.Events.ACTIVITY_IDENTIFICATION_RESULT,
        callback,
    );
};


export const registerActivityConversionHeadlessTask = (callback) => {
  AppRegistry.registerHeadlessTask(HMSActivityIdentification.Events.ACTIVITY_CONVERSION_RESULT, () => async (taskData) => {
    callback(taskData);
  });
};

export const addActivityConversionEventListener = callback => {
    DeviceEventEmitter.addListener(
        HMSActivityIdentification.Events.ACTIVITY_CONVERSION_RESULT,
        callback,
    );
};

export const removeActivityConversionEventListener = async (callback) => {
    DeviceEventEmitter.removeListener(
        HMSActivityIdentification.Events.ACTIVITY_CONVERSION_RESULT,
        callback,
    );
};

export default HMSActivityIdentification;
