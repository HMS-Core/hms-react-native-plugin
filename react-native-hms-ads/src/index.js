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

import HMSAds from "./Ads";
import HMSOaid from "./Oaid";
import HMSBanner from "./Banner";
import HMSInstream from "./Instream";
import HMSNative from "./Native";
import HMSInstallReferrer from "./InstallReferrer";
import HMSInterstitial from "./Interstitial";
import HMSSplash from "./Splash";
import HMSReward from "./Reward";
const {
  ConsentStatus,
  DebugNeedConsent,
  AudioFocusType,
  ContentClassification,
  NonPersonalizedAd,
  Gender,
  TagForChild,
  UnderAge,
  NativeAdAssetNames,
  ChoicesPosition,
  NativeMediaTypes,
  Direction,
  BannerAdSizes,
  BannerMediaTypes,
  InterstitialMediaTypes,
  RewardMediaTypes,
  SplashMediaTypes,
  ScaleType,
  CallMode,
} = HMSAds;

export {
  HMSBanner,
  HMSInstream,
  HMSNative,
  HMSOaid,
  HMSInterstitial,
  HMSInstallReferrer,
  HMSSplash,
  HMSReward,
  ConsentStatus,
  DebugNeedConsent,
  ContentClassification,
  AudioFocusType,
  Gender,
  NonPersonalizedAd,
  TagForChild,
  UnderAge,
  NativeAdAssetNames,
  ChoicesPosition,
  Direction,
  NativeMediaTypes,
  BannerAdSizes,
  BannerMediaTypes,
  InterstitialMediaTypes,
  RewardMediaTypes,
  SplashMediaTypes,
  ScaleType,
  CallMode,
};
export default HMSAds;
