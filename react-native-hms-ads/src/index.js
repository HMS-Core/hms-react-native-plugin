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

import HMSAds from './Ads';
import HMSOaid from './Oaid';
import HMSBanner from './Banner';
import HMSNative from './Native';
import HMSInstallReferrer from './InstallReferrer';
import HMSInterstitial from './Interstitial';
import HMSSplash from './Splash';
import HMSReward from './Reward';
import {
  ConsentStatus,
  DebugNeedConsent,
  AudioFocusType,
  ContentClassification,
  Gender,
  NonPersonalizedAd,
  TagForChild,
  UnderAge,
  NativeAdAssetNames,
  ChoicesPosition,
  Direction,
  BannerAdSizes,
  NativeMediaTypes,
  BannerMediaTypes,
  InterstitialMediaTypes,
  RewardMediaTypes,
  SplashMediaTypes,
  ScaleType,
  CallMode,
} from './constants';

export {
  HMSBanner,
  HMSNative,
  HMSOaid,
  HMSInterstitial,
  HMSInstallReferrer,
  HMSSplash,
  HMSReward,
  ConsentStatus,
  DebugNeedConsent,
  AudioFocusType,
  ContentClassification,
  Gender,
  NonPersonalizedAd,
  TagForChild,
  UnderAge,
  NativeAdAssetNames,
  ChoicesPosition,
  Direction,
  BannerAdSizes,
  NativeMediaTypes,
  BannerMediaTypes,
  InterstitialMediaTypes,
  RewardMediaTypes,
  SplashMediaTypes,
  ScaleType,
  CallMode,
};
export default HMSAds;
