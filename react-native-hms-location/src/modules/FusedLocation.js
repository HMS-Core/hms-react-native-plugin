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

import {NativeModules, DeviceEventEmitter} from 'react-native';

const {HMSFusedLocation} = NativeModules;

import {areKeysExact} from '../utils/typeCheck';

export const LocationData = {
  configure(val) {
    if (areKeysExact(this.locationData, val)) {
      this.locationData = val;
    } else {
      throw new Error(
        'Provided LocationData object keys does not match required ones ' +
          {
            latitude: 'float',
            longitude: 'float',
            speed: 'float',
            bearing: 'float',
            accuracy: 'float',
            verticalAccuracyMeters: 'float',
            bearingAccuracyDegrees: 'float',
            speedAccuracyMetersPerSecond: 'float',
            time: 'float',
            fromMockProvider: 'boolean',
          },
      );
    }
    return this;
  },
  locationData: {
    latitude: 0.0,
    longitude: 0.0,
    speed: 0.0,
    bearing: 0.0,
    accuracy: 0.0,
    verticalAccuracyMeters: 0.0,
    bearingAccuracyDegrees: 0.0,
    speedAccuracyMetersPerSecond: 0.0,
    time: 0.0,
    fromMockProvider: false,
  },
  build() {
    return this.locationData;
  },
};

export const LocationRequest = {
  configure(val) {
    if (areKeysExact(this.locationRequest, val)) {
      this.locationRequest = val;
    } else {
      throw new Error(
        'Provided LocationRequest object keys does not match required ones ' +
          {
            priority: 'number',
            interval: 'float',
            numUpdates: 'number',
            fastestInterval: 'float',
            expirationTime: 'float',
            expirationTimeDuration: 'float',
            smallestDisplacement: 'float',
            maxWaitTime: 'float',
            needAddress: 'boolean',
            language: 'string',
            countryCode: 'string',
          },
      );
    }
    return this;
  },
  locationRequest: {
    priority: 0,
    interval: 0.0,
    numUpdates: 0,
    fastestInterval: 0.0,
    expirationTime: 0.0,
    expirationTimeDuration: 0.0,
    smallestDisplacement: 0.0,
    maxWaitTime: 0.0,
    needAddress: false,
    language: 'en',
    countryCode: 'en',
  },
  build() {
    return this.locationRequest;
  },
};

export const LocationSettingsRequest = {
  configure(val) {
    if (areKeysExact(this.locationSettingsRequest, val)) {
      this.locationSettingsRequest = val;
    } else {
      throw new Error(
        'Provided LocationRequest object keys does not match required ones ' +
          {
            locationRequests: 'array',
            needBle: 'boolean',
            alwaysShow: 'boolean',
          },
      );
    }
    return this;
  },
  locationSettingsRequest: {
    locationRequests: [],
    needBle: false,
    alwaysShow: false
  },
  build() {
    return this.locationSettingsRequest;
  },
};

export const addFusedLocationEventListener = callback => {
  DeviceEventEmitter.addListener(
    HMSFusedLocation.Events.SCANNING_RESULT,
    callback,
  );
};

export const removeFusedLocationEventListener = async (id, callback) => {
  DeviceEventEmitter.removeListener(
    HMSFusedLocation.Events.SCANNING_RESULT,
    callback,
  );
  return await HMSFusedLocation.removeLocationUpdates(id);
};

export default HMSFusedLocation;
