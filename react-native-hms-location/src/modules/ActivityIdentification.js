/*
    Copyright 2020-2024. Huawei Technologies Co., Ltd. All rights reserved.

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

import { NativeModules, DeviceEventEmitter, AppRegistry, NativeEventEmitter } from 'react-native';

const { HMSActivityIdentification } = NativeModules;

const HMSActivityIdentificationEmitter = new NativeEventEmitter();

export const registerActivityIdentificationHeadlessTask = (callback) =>
  AppRegistry.registerHeadlessTask(HMSActivityIdentification.Events.ACTIVITY_IDENTIFICATION, () => async (taskData) =>
    callback(taskData)
  );

export const addActivityIdentificationEventListener = (callback) =>
  HMSActivityIdentificationEmitter.addListener(HMSActivityIdentification.Events.ACTIVITY_IDENTIFICATION, callback);

export const removeActivityIdentificationEventListener = async () =>
  HMSActivityIdentificationEmitter.removeAllListeners(HMSActivityIdentification.Events.ACTIVITY_IDENTIFICATION);

export const registerActivityConversionHeadlessTask = (callback) => {
  AppRegistry.registerHeadlessTask(HMSActivityIdentification.Events.ACTIVITY_CONVERSION, () => async (taskData) =>
    callback(taskData)
  );
};

export const addActivityConversionEventListener = (callback) =>
  HMSActivityIdentificationEmitter.addListener(HMSActivityIdentification.Events.ACTIVITY_CONVERSION, callback);

export const removeActivityConversionEventListener = async () =>
  HMSActivityIdentificationEmitter.removeAllListeners(HMSActivityIdentification.Events.ACTIVITY_CONVERSION);

export default HMSActivityIdentification;
