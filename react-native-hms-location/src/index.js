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

import HMSActivityIdentification, {
  ActivityConversionInfo,
  ActivityConversionData,
  ActivityConversionRequest,
  ActivityConversionResponse,
  ActivityIdentificationData,
  ActivityIdentificationResponse,
  addActivityIdentificationEventListener,
  removeActivityIdentificationEventListener,
  addActivityConversionEventListener,
  removeActivityConversionEventListener
} from './modules/ActivityIdentification';
import HMSFusedLocation, {
  LocationData,
  LocationRequest,
  LocationSettingsRequest,
  addFusedLocationEventListener,
  removeFusedLocationEventListener,
} from './modules/FusedLocation';
import HMSGeofence, {
  GeofenceBuilder,
  GeofenceData,
  GeofenceRequest,
  addGeofenceEventListener,
  removeGeofenceEventListener
} from './modules/Geofence';

const Geofence = {
  Native: HMSGeofence,
  Builder: GeofenceBuilder,
  Data: GeofenceData,
  Request: GeofenceRequest,
  GeofenceRequestConstants: HMSGeofence.GeofenceRequestConstants,
  GeofenceConstants: HMSGeofence.GeofenceConstants,
  ErrorCodes: HMSGeofence.ErrorCodes,
  Events: {
    addGeofenceEventListener,
    removeGeofenceEventListener
  }
};

const FusedLocation = {
  Native: HMSFusedLocation,
  Data: LocationData,
  Request: LocationRequest,
  SettingsRequest: LocationSettingsRequest,
  PriorityConstants: HMSFusedLocation.PriorityConstants,
  Events: {
    addFusedLocationEventListener,
    removeFusedLocationEventListener,
  },
};

const ActivityIdentification = {
  Native: HMSActivityIdentification,
  ConversionInfo: ActivityConversionInfo,
  ConversionData: ActivityConversionData,
  ConversionRequest: ActivityConversionRequest,
  ConversionResponse: ActivityConversionResponse,
  IdData: ActivityIdentificationData,
  IdResponse: ActivityIdentificationResponse,
  Activities: HMSActivityIdentification.Activities,
  ActivityConversions: HMSActivityIdentification.ActivityConversions,
  Events: {
    addActivityIdentificationEventListener,
    removeActivityIdentificationEventListener,
    addActivityConversionEventListener,
    removeActivityConversionEventListener
  }
}

export default {
  Geofence,
  FusedLocation,
  ActivityIdentification
};
