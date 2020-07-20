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

import { NativeModules, DeviceEventEmitter } from 'react-native';
import { areKeysExact, isArray } from '../utils/typeCheck';

const { HMSGeofence } = NativeModules;

export const GeofenceBuilder = {
  configure(val) {
    if (areKeysExact(this.geofence, val)) {
      this.geofence = val;
    } else {
      throw new Error(
        'Provided GeofenceBuilder object keys does not match required ones' +
        {
          longitude: 'float',
          latitude: 'float',
          radius: 'float',
          uniqueId: 'string',
          conversions: 'number',
          validContinueTime: 'float',
          dwellDelayTime: 'number',
          notificationInterval: 'number',
        },
      );
    }

    return this;
  },
  geofence: {
    longitude: 0.0,
    latitude: 0.0,
    radius: 0.0,
    uniqueId: '',
    conversions: 0,
    validContinueTime: 0.0,
    dwellDelayTime: 0,
    notificationInterval: 0,
  },
  build() {
    return this.geofence;
  },
};

export const GeofenceRequest = {
  configure(val) {
    if (areKeysExact(this.geofenceRequest, val)) {
      this.geofenceRequest = val;
    } else {
      throw new Error(
        'Provided GeofenceRequest object keys does not match required ones ' +
        { geonfences: 'Array', conversions: 'number', coordinate: 'number' },
      );
    }
    return this;
  },
  geofenceRequest: {
    geofences: [],
    conversions: 0,
    coordinate: 0,
  },
  build() {
    return this.geofenceRequest;
  },
};

export const GeofenceResponse = {
  configure(val) {
    if (areKeysExact(this.geofenceResponse, val)) {
      this.geofenceResponse = val;
    } else {
      throw new Error(
        'Provided GeofenceResponse object keys does not match required ones ' +
        { uniqueId: 'string' },
      );
    }
    return this;
  },
  geofenceResponse: {
    uniqueId: 'id'
  },
  build() {
    return this.geofenceResponse;
  },
};

export const GeofenceData = {
  configure(val) {
    if (areKeysExact(this.geofenceData, val)) {
      if (isArray(val.convertingGeofenceList)) {
        val.convertingGeofenceList.forEach(el => {
          if (!areKeysExact(GeofenceResponse.geofenceResponse, el)) {
            throw new Error(
              'Keys of one of the elements of the provided convertingGeofenceList object do not match required ones ' +
              { uniqueId: 'string' },
            );
          }
        });
        this.geofenceData = val;
        return this;
      }
      throw new Error('convertingGeofenceList element of the provided GeofenceData object is not an array ');
    } else {
      throw new Error(
        'Keys of one of the elements of the provided GeofenceData object do not match required ones ' +
        {
          convertingGeofenceList: 'Array',
          conversion: 'number',
          convertingLocation: 'Array',
          errorCode: 'number',
          errorMessage: 'string',
        },
      );
    }
  },
  geofenceData: {
    convertingGeofenceList: [],
    conversion: 0,
    convertingLocation: [],
    errorCode: 0,
    errorMessage: '',
  },
  build() {
    return this.geofenceData;
  },
};

export const addGeofenceEventListener = callback => {
  DeviceEventEmitter.addListener(
    HMSGeofence.Events.GEOFENCE_RESULT,
    callback,
  );
};

export const removeGeofenceEventListener = callback => {
  DeviceEventEmitter.removeListener(
    HMSGeofence.Events.GEOFENCE_RESULT,
    callback,
  );
};

export default HMSGeofence;
