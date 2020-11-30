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

import { AppRegistry, ToastAndroid } from 'react-native';
import App from './App';
import { name as appName } from './app.json';
import HMSLocation from '@hmscore/react-native-hms-location';

const showActivity = (data) => {
  console.log('Activity Identification Headless Task, data:', data)
  if (data && data.mostActivityIdentification) {
    switch (data.mostActivityIdentification.identificationActivity) {
      case HMSLocation.ActivityIdentification.Activities.STILL:
        ToastAndroid.show(
          `You are still with ${data.mostActivityIdentification.possibility} possibility`,
          ToastAndroid.SHORT
        );
        break;

      default:
        ToastAndroid.show(`You are not still`, ToastAndroid.SHORT);
        break;
    }
  }
};
HMSLocation.ActivityIdentification.Events.registerActivityIdentificationHeadlessTask(showActivity);
HMSLocation.ActivityIdentification.Events.registerActivityConversionHeadlessTask((data) =>
  console.log('Activity Conversion Headless Task, data:', data)
);
HMSLocation.FusedLocation.Events.registerFusedLocationHeadlessTask((data) =>
  console.log('Fused Location Headless Task, data:', data)
);
HMSLocation.Geofence.Events.registerGeofenceHeadlessTask((data) =>
  console.log('Geofence Headless Task, data:', data)
);
AppRegistry.registerComponent(appName, () => App);
