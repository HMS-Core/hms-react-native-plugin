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

import { NativeModules, DeviceEventEmitter, AppRegistry } from 'react-native';
const { HMSGeofence } = NativeModules;

export const registerGeofenceHeadlessTask = (callback) =>
  AppRegistry.registerHeadlessTask(HMSGeofence.Events.GEOFENCE, () => async (taskData) => callback(taskData));

export const addGeofenceEventListener = (callback) =>
  DeviceEventEmitter.addListener(HMSGeofence.Events.GEOFENCE, callback);

export const removeGeofenceEventListener = (callback) =>
  DeviceEventEmitter.removeListener(HMSGeofence.Events.GEOFENCE, callback);

export default HMSGeofence;
