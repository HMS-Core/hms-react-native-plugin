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

export const ConsentStatus = {
  PERSONALIZED: 0,
  NON_PERSONALIZED: 1,
  UNKNOWN: 2,
};

export const DebugNeedConsent = {
  DEBUG_DISABLED: 0,
  DEBUG_NEED_CONSENT: 1,
  DEBUG_NOT_NEED_CONSENT: 2,
};

export const AudioFocusType = {
  GAIN_AUDIO_FOCUS_ALL: 0,
  NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE: 1,
  NOT_GAIN_AUDIO_FOCUS_ALL: 2,
};

export const ContentClassification = {
  AD_CONTENT_CLASSIFICATION_W: 'W',
  AD_CONTENT_CLASSIFICATION_PI: 'PI',
  AD_CONTENT_CLASSIFICATION_J: 'J',
  AD_CONTENT_CLASSIFICATION_A: 'A',
  AD_CONTENT_CLASSIFICATION_UNKOWN: '',
};

export const Gender = {
  UNKNOWN: 0,
  MALE: 1,
  FEMALE: 2,
};

export const NonPersonalizedAd = {
  ALLOW_ALL: 0,
  ALLOW_NON_PERSONALIZED: 1,
};

export const TagForChild = {
  TAG_FOR_CHILD_PROTECTION_FALSE: 0,
  TAG_FOR_CHILD_PROTECTION_TRUE: 1,
  TAG_FOR_CHILD_PROTECTION_UNSPECIFIED: -1,
};

export const UnderAge = {
  PROMISE_FALSE: 0,
  PROMISE_TRUE: 1,
  PROMISE_UNSPECIFIED: -1,
};

export const NativeAdAssetNames = {
  TITLE: 1,
  CALL_TO_ACTION: 2,
  ICON: 3,
  DESC: 4,
  AD_SOURCE: 5,
  IMAGE: 8,
  MEDIA_VIDEO: 10,
  CHOICES_CONTAINER: 11,
};

export const ChoicesPosition = {
  TOP_LEFT: 0,
  TOP_RIGHT: 1,
  BOTTOM_RIGHT: 2,
  BOTTOM_LEFT: 3,
  INVISIBLE: 4,
};

export const Direction = {
  ANY: 0,
  PORTRAIT: 1,
  LANDSCAPE: 2,
};

export const ScaleType = {
  MATRIX: 0,
  FIT_XY: 1,
  FIT_START: 2,
  FIT_CENTER: 3,
  FIT_END: 4,
  CENTER: 5,
  CENTER_CROP: 6,
  CENTER_INSIDE: 7,
};

export const BannerAdSizes = {
  B_160_600: '160_600',
  B_300_250: '300_250',
  B_320_50: '320_50',
  B_320_100: '320_100',
  B_360_57: '360_57',
  B_360_144: '360_144',
  B_468_60: '468_60',
  B_728_90: '728_90',
  B_CURRENT_DIRECTION: 'current_direction',
  B_PORTRAIT: 'portrait',
  B_SMART: 'smart',
  B_DYNAMIC: 'dynamic',
  B_LANDSCAPE: 'landscape',
  B_INVALID: 'invalid',
};

export const NativeMediaTypes = {
  VIDEO: 'video',
  IMAGE_SMALL: 'image_small',
  IMAGE_LARGE: 'image_large',
};

export const BannerMediaTypes = {
  IMAGE: 'image',
};

export const InterstitialMediaTypes = {
  VIDEO: 'video',
  IMAGE: 'image',
};

export const RewardMediaTypes = {
  VIDEO: 'video',
};

export const SplashMediaTypes = {
  VIDEO: 'video',
  IMAGE: 'image',
};

export const CallMode = {
  AIDL: 'aidl',
  SDK: 'sdk',
};
