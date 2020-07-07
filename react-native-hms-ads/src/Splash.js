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

import {NativeEventEmitter, NativeModules} from 'react-native';
import {addCheckerToModule, addListenerToModule} from './utils';

const {RNHMSAdsSplash} = NativeModules;

const eventEmitter = new NativeEventEmitter(RNHMSAdsSplash);

const checkedFunctions = {
  setAdId: ['string'],
  setLogoText: ['string'],
  setCopyrightText: ['string'],
  setOrientation: ['integer'],
  setSloganResource: ['string'],
  setWideSloganResource: ['string'],
  setLogoResource: ['string'],
  setMediaNameResource: ['string'],
  setAudioFocusType: ['integer'],
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
  destroy: [],
  isLoading: [],
  isLoaded: [],
  show: [],
};
addCheckerToModule(RNHMSAdsSplash, checkedFunctions);

const events = [
  'adLoaded',
  'adFailedToLoad',
  'adDismissed',
  'adShowed',
  'adClick',
];
addListenerToModule(RNHMSAdsSplash, eventEmitter, events);

export default RNHMSAdsSplash;
