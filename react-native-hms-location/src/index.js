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

import HMSLocationKit from './modules/LocationKit';

import HMSActivityIdentification, {
    registerActivityIdentificationHeadlessTask,
    addActivityIdentificationEventListener,
    removeActivityIdentificationEventListener,
    registerActivityConversionHeadlessTask,
    addActivityConversionEventListener,
    removeActivityConversionEventListener
} from './modules/ActivityIdentification';

import HMSFusedLocation, {
    registerFusedLocationHeadlessTask,
    addFusedLocationEventListener,
    removeFusedLocationEventListener,
} from './modules/FusedLocation';

import HMSGeofence, {
    registerGeofenceHeadlessTask,
    addGeofenceEventListener,
    removeGeofenceEventListener
} from './modules/Geofence';

const LocationKit = {
    Native: HMSLocationKit
};

const Geofence = {
    Native: HMSGeofence,
    GeofenceRequestConstants: HMSGeofence.GeofenceRequestConstants,
    GeofenceConstants: HMSGeofence.GeofenceConstants,
    ErrorCodes: HMSGeofence.ErrorCodes,
    Events: {
        registerGeofenceHeadlessTask,
        addGeofenceEventListener,
        removeGeofenceEventListener
    }
};

const FusedLocation = {
    Native: HMSFusedLocation,
    PriorityConstants: HMSFusedLocation.PriorityConstants,
    NavigationRequestConstants: HMSFusedLocation.NavigationRequestConstants,
    Events: {
        registerFusedLocationHeadlessTask,
        addFusedLocationEventListener,
        removeFusedLocationEventListener,
    },
};

const ActivityIdentification = {
    Native: HMSActivityIdentification,
    Activities: HMSActivityIdentification.Activities,
    ActivityConversions: HMSActivityIdentification.ActivityConversions,
    Events: {
        registerActivityIdentificationHeadlessTask,
        addActivityIdentificationEventListener,
        removeActivityIdentificationEventListener,
        registerActivityConversionHeadlessTask,
        addActivityConversionEventListener,
        removeActivityConversionEventListener
    }
};

export default {
    LocationKit,
    Geofence,
    FusedLocation,
    ActivityIdentification
};
