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

import {NativeModules, NativeEventEmitter} from 'react-native';
import {addCheckerToModule, addListenerToModule} from './utils';

const {RNHMSAdsRewardAd} = NativeModules;

const eventEmitter = new NativeEventEmitter(RNHMSAdsRewardAd);

const checkedFunctions = {
  setAdId: ['string'],
  setData: ['string'],
  setUserId: ['string'],
  setVerifyConfig: [
    {
      'userId!': 'string',
      'data!': 'string',
    },
  ],
  setAdParam: [
    {
      adContentClassification: 'string',
      appCountry: 'string',
      appLang: 'string',
      belongCountryCode: 'string',
      gender: 'integer',
      nonPersonalizedAd: 'integer',
      requestOrigin: 'string',
      tagForChildProtection: 'integer',
      tagForUnderAgeOfPromise: 'integer',
      targetingContentUrl: 'string',
    },
  ],
  pause: [],
  resume: [],
  loadAd: [],
  show: [],
  isLoaded: [],
};
addCheckerToModule(RNHMSAdsRewardAd, checkedFunctions);

const events = [
  'adFailedToLoad',
  'adLoaded',
  'adClosed',
  'adFailedToShow',
  'adOpened',
  'adRewarded',
];
addListenerToModule(RNHMSAdsRewardAd, eventEmitter, events);

export default RNHMSAdsRewardAd;
