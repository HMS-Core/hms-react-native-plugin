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
declare module "@hmscore/react-native-hms-ads" {
  import * as React from "react";
  import {NativeSyntheticEvent, ViewProps} from "react-native";

  /**
   *  Ad content rating.
   *  AD_CONTENT_CLASSIFICATION_W = 'W';
   *  AD_CONTENT_CLASSIFICATION_PI = 'PI';
   *  AD_CONTENT_CLASSIFICATION_J = 'J';
   *  AD_CONTENT_CLASSIFICATION_A = 'A';
   *  AD_CONTENT_CLASSIFICATION_UNKOWN = '';
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-contentcassification
   */
  export type ContentClassificationType = "W" | "PI" | "J" | "A" | "";

  /**
   *  Whether to request only non-personalized ads.
   *  ALLOW_ALL = 0;
   *  ALLOW_NON_PERSONALIZED = 1;
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-nonpersonalizedad
   */
  export type NonPersonalizedAdType = 0 | 1;

  /**
   *  Child-directed setting.
   *  TAG_FOR_CHILD_PROTECTION_FALSE = 0;
   *  TAG_FOR_CHILD_PROTECTION_TRUE = 1;
   *  TAG_FOR_CHILD_PROTECTION_UNSPECIFIED = -1;
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-tagforchild
   */
  export type TagForChildType = 0 | 1 | -1;

  /**
   *  Setting directed to users under the age of consent.
   *  PROMISE_FALSE = 0;
   *  PROMISE_TRUE = 1;
   *  PROMISE_UNSPECIFIED = -1;
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-underage
   */
  export type UnderAgeType = 0 | 1 | -1;

  /**
   *  Gender type
   *  UNKNOWN = 0;
   *  MALE = 1;
   *  FEMALE = 2;
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-gender
   */
  export type GenderType = 0 | 1 | 2;

  /**
   *  Whether to obtain the audio focus during video playback
   *  B_160_600 = "160_600"
   *  B_300_250 = "300_250"
   *  B_320_50 = "320_50"
   *  B_320_100 = "320_100"
   *  B_360_57 = "360_57"
   *  B_360_144 = "360_144"
   *  B_468_60 = "468_60"
   *  B_728_90 = "728_90"
   *  B_CURRENT_DIRECTION = "currentDirection"
   *  B_PORTRAIT = "portrait"
   *  B_SMART = "smart"
   *  B_DYNAMIC = "dynamic"
   *  B_LANDSCAPE = "landscape"
   *  B_INVALID = "invalid"
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-banneradSize
   */
  export type BannerAdSizeType =
    | "160_600"
    | "300_250"
    | "320_50"
    | "320_100"
    | "360_57"
    | "360_144"
    | "468_60"
    | "728_90"
    | "currentDirection"
    | "portrait"
    | "smart"
    | "dynamic"
    | "landscape"
    | "invalid";

  /**
   *  Option for functions that can use Huawei SDK or
   *  [Aidl](https://developer.android.com/guide/components/aidl) service.
   *  SDK = 'sdk';
   *  AIDL = 'aidl'; // Will not be used anymore
   */
  export type CallModeType = "sdk" | "aidl";

  /**
   *  Debug consent setting.
   *  DEBUG_DISABLED = 0;
   *  DEBUG_NEED_CONSENT = 1;
   *  DEBUG_NOT_NEED_CONSENT = 2;
   */
  export type DebugNeedConsentType = 0 | 1 | 2;

  /**
   *  Consent status.
   *  PERSONALIZED = 0;
   *  NON_PERSONALIZED = 1;
   *  UNKNOWN = 2;
   */
  export type ConsentStatusType = 0 | 1 | 2;

  /**
   *  Choice icon position constants
   *  TOP_LEFT = 0
   *  TOP_RIGHT = 1
   *  BOTTOM_RIGHT = 2
   *  BOTTOM_LEFT = 3
   *  INVISIBLE = 4
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-nativeadconfiguration-choicesposition
   */
  export type ChoicesPositionType = 0 | 1 | 2 | 3 | 4;

  /**
   *  Orientation constant
   *  ANY = 0
   *  PORTRAIT = 1
   *  LANDSCAPE = 2
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-nativeadconfiguration-direction
   */
  export type DirectionType = 0 | 1 | 2;

  /**
   *  Whether to obtain the audio focus during video playback
   *  GAIN_AUDIO_FOCUS_ALL = 0
   *  NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE = 1
   *  NOT_GAIN_AUDIO_FOCUS_ALL = 2
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-audiofocustype
   */
  export type AudioFocusType = 0 | 1 | 2;

  /**
   *  Native ad media types
   *  VIDEO = "video"
   *  IMAGE_SMALL = "image_small"
   *  IMAGE_LARGE = "image_large"
   */
  export type NativeMediaType = "video" | "image_small" | "image_large";

  /**
   *  Options for scaling the bounds of an image
   *  MATRIX = 0
   *  FIT_XY = 1
   *  FIT_START = 2
   *  FIT_CENTER = 3
   *  FIT_END = 4
   *  CENTER = 5
   *  CENTER_CROP = 6
   *  CENTER_INSIDE = 7
   *  Refer this page https://developer.android.com/reference/android/widget/ImageView.ScaleType
   */
  export type ScaleTypes = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7;

  /**
   *  Ad request options.
   */
  export interface RequestOptions {
    /**
     *  The OAID.
     */
    adContentClassification?: ContentClassificationType;

    /**
     *  The OAID.
     */
    appCountry?: string;

    /**
     *  The OAID.
     */
    appLang?: string;

    /**
     *  The OAID.
     */
    nonPersonalizedAd?: NonPersonalizedAdType;

    /**
     *  The OAID.
     */
    tagForChildProtection?: TagForChildType;

    /**
     *  The OAID.
     */
    tagForUnderAgeOfPromise?: UnderAgeType;
  }

  /**
   *  Ad provider.
   */
  export interface AdProvider {
    /**
     *  Id of ad provider.
     */
    id: ContentClassificationType;

    /**
     *  Name of ad provider.
     */
    name: string;

    /**
     *  The url for privacy policy.
     */
    privacyPolicyUrl: string;

    /**
     *  The service area for ad (ex: 'Global' or 'Asia').
     */
    serviceArea: string;
  }

  /**
   *  Consent information from api result.
   */
  export interface ConsentResult {
    /**
     *  Status of consent.
     */
    consentStatus: ConsentStatusType;

    /**
     *  Shows whether consent is needed.
     */
    isNeedConsent: boolean;

    /**
     *  Ad provider list
     */
    adProviders: AdProvider[];
  }

  /**
   *  Ad consent object to be submitted.
   */
  export interface Consent {
    /**
     *  Consent option.
     */
    consentStatus?: ConsentStatusType;

    /**
     *  DebugNeedConsent option.
     */
    debugNeedConsent?: DebugNeedConsentType;

    /**
     *  UnderAge option.
     */
    underAgeOfPromise?: UnderAgeType;

    /**
     *  Device Id
     */
    testDeviceId?: string;
  }

  /**
   *  Information about advertised clients.
   */
  export interface AdvertisingIdClientInfo {
    /**
     *  The OAID.
     */
    id: string;

    /**
     *  'Limit ad tracking' setting.
     */
    isLimitAdTrackingEnabled: boolean;
  }

  /**
   *  HMSOaid module.
   */
  export const HMSOaid = {
    /**
     *  Obtains the OAID and 'Limit ad tracking' setting.
     */
    getAdvertisingIdInfo(callMode: CallModeType): Promise<{selam: 5}>;,

    /**
     *  Verifies the OAID and 'Limit ad tracking' setting
     */
    verifyAdvertisingId(
      advertisingInfo: AdvertisingIdClientInfo,
    ): Promise<boolean>;,
  };

  /**
   *  Server-side verification parameter.
   */
  export interface VerifyConfig {
    /**
     *  User Id.
     */
    userId: number;

    /**
     *  'Custom data.
     */
    data: boolean;
  }

  /**
   *  Ad request parameters.
   */
  export interface AdParam {
    /**
     *  Ad content rating. Check ContentClassification for possible values.
     */
    adContentClassification?: ContentClassificationType;

    /**
     *  Country code corresponding to the language in which an ad needs to be
     *  returned for an app.
     */
    appCountry?: string;

    /**
     *  Language in which an ad needs to be returned for an app.
     */
    appLang?: string;

    /**
     *  Home country code.
     */
    belongCountryCode?: string;

    /**
     *  Gender. Check Gender for possible values.
     */
    gender?: GenderType;

    /**
     *  The setting of requesting personalized ads. Check NonPersonalizedAd
     *  for possible values.
     */
    nonPersonalizedAd?: NonPersonalizedAdType;

    /**
     *  Origin of request.
     */
    requestOrigin?: string;

    /**
     *  The setting of processing ad requests according to the COPPA.
     *  Check TagForChild for possible values.
     */
    tagForChildProtection?: TagForChildType;

    /**
     *  The setting of processing ad requests as directed to users under
     *  the age of consent. Check UnderAge for possible values.
     */
    tagForUnderAgeOfPromise?: UnderAgeType;

    /**
     *  Targeting content url.
     */
    targetingContentUrl?: string;
  }

  /**
   *  Information about the reward item in a rewarded ad.
   */
  export interface Reward {
    /**
     *  The name of a reward item.
     */
    name: string;

    /**
     *  The number of reward items.
     */
    amount: number;
  }

  /**
   *  Information about the reward item in a rewarded ad.
   */
  export interface RewardAd {
    /**
     *  User id.
     */
    userId: string;

    /**
     *  Custom data.
     */
    data: string;

    /**
     *  Reward item.
     */
    reward: Reward;

    /**
     *  Shows whether a rewarded ad is successfully loaded.
     */
    isLoaded: boolean;
  }

  /**
   *  HMSReward module for reward ads.
   */
  export const HMSReward = {
    /**
     *  Sets ad slot id.
     */
    setAdId(adSlotId: string): void;,

    /**
     *  Sets user id
     */
    setUserId(userID: string): void;,

    /**
     *  Sets custom data in string
     */
    setData(data: string): void;,

    /**
     *  Sets custom data in string
     */
    setVerifyConfig(verifyConfig: VerifyConfig): void;,

    /**
     *  Sets parameters of ad request
     */
    setAdParam(adParam: AdParam): void;,

    /**
     *  Sets custom data in string
     */
    pause(): void;,

    /**
     *  Resumes the ad.
     */
    resume(): void;,

    /**
     *  Requests ad.
     */
    loadAd(): void;,

    /**
     *  Checks whether ad is successfully loaded
     */
    isLoaded(): Promise<boolean>;,

    /**
     *  Add listener for the event when ad loads.
     */
    adLoadedListenerAdd(listenerFn: (response: RewardAd) => void): void;,

    /**
     *  Remove the listener for the event when ad loads.
     */
    adLoadedListenerRemove(): void;,

    /**
     *  Add listener for the event when fails to load.
     */
    adFailedToLoadListenerAdd(listenerFn: (response: Error) => void): void;,

    /**
     *  Remove the listener for the event when fails to load.
     */
    adFailedToLoadListenerRemove(): void;,

    /**
     *  Add listener for the event when ad fails to be displayed.
     */
    adFailedToShowListenerAdd(listenerFn: (response: Error) => void): void;,

    /**
     *  Remove the listener for the event when ad fails to be displayed.
     */
    adFailedToShowListenerRemove(): void;,

    /**
     *  Add listener for the event when ad is opened.
     */
    adOpenedListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when ad is opened.
     */
    adOpenedListenerRemove(): void;,

    /**
     *  Add listener for the event when ad is closed.
     */
    adClosedListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when ad is closed.
     */
    adClosedListenerRemove(): void;,

    /**
     *  Add listener for the event when a reward is provided.
     */
    adRewardedListenerAdd(listenerFn: (response: Reward) => void): void;,

    /**
     *  Remove the listener for the event when a reward is provided.
     */
    adRewardedListenerRemove(): void;,

    /**
     *  Remove all listeners for events of HMSReward
     */
    allListenersRemove(): void;,
  };

  /**
   *  HMSSplash module for splash ads
   */
  export const HMSSplash = {
    /**
     *  Sets ad slot id.
     */
    setAdId(adSlotId: string): void;,

    /**
     *  Sets logo text.
     */
    setLogoText(logoText: string): void;,

    /**
     *  Sets copyright text.
     */
    setCopyrightText(cpyrightText: string): void;,

    /**
     *  Sets screen orientation
     */
    setOrientation(orientation: number): void;,

    /**
     *  Sets default app launch image in portrait mode,
     *  which is displayed before a splash ad is displayed
     */
    setSloganResource(sloganResource: string): void;,

    /**
     *  Sets default app launch image in landscape mode,
     *  which is displayed before a splash ad is displayed.
     */
    setWideSloganResource(wideSloganResource: string): void;,

    /**
     *  Sets app logo.
     */
    setLogoResource(logoResource: string): void;,

    /**
     *  Sets app text resource.
     */
    setMediaNameResource(mediaNameResource: string): void;,

    /**
     *  Sets the audio focus preemption policy for a video splash ad.
     */
    setAudioFocusType(audioFocusType: AudioFocusType): void;,

    /**
     *  Sets parameters of ad request
     */
    setAdParam(adParam: AdParam): void;,

    /**
     *  Pauses ad.
     */
    pause(): void;,

    /**
     *  Resumes the ad.
     */
    resume(): void;,

    /**
     *  Destroys the ad.
     */
    destroy(): void;,

    /**
     *  Shows the ad.
     */
    show(): void;,

    /**
     *  Checks whether ad is successfully loaded.
     */
    isLoaded(): Promise<boolean>;,

    /**
     *  Checks whether a splash ad is being loaded.
     */
    isLoading(): Promise<boolean>;,

    /**
     *  Add listener for the event when ad loads.
     */
    adLoadedListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when ad loads.
     */
    adLoadedListenerRemove(): void;,

    /**
     *  Add listener for the event when ad fails to load.
     */
    adFailedToLoadListenerAdd(listenerFn: (response: Error) => void): void;,

    /**
     *  Remove the listener for the event when ad fails to load.
     */
    adFailedToLoadListenerRemove(): void;,

    /**
     *  Add listener for the event when ad is dismissed.
     */
    adDismissedListenerAdd(listenerFn: (response: Error) => void): void;,

    /**
     *  Remove the listener for the event when ad is dismissed.
     */
    adDismissedListenerRemove(): void;,

    /**
     *  Add listener for the event when ad is shown.
     */
    adShowedListenerAdd(listenerFn: (response: Error) => void): void;,

    /**
     *  Remove the listener for the event when ad is shown.
     */
    adShowedListenerRemove(): void;,

    /**
     *  Add listener for the event when ad is clicked.
     */
    adClickListenerAdd(listenerFn: (response: Error) => void): void;,

    /**
     *  Remove the listener for the event when ad is clicked.
     */
    adClickListenerRemove(): void;,

    /**
     *  Remove all listeners for events of HMSSplash
     */
    allListenersRemove(): void;,
  };

  /**
   *  Interstitial ad.
   */
  export interface InterstitialAd {
    /**
     *  The ad slot id.
     */
    adId: string;

    /**
     *  Shows whether ad loading is complete.
     */
    isLoaded: boolean;

    /**
     *  Shows whether ads are being loaded.
     */
    isLoading: boolean;
  }

  /**
   *  HMSInterstitial module for Interstitial ads
   */
  export const HMSInterstitial = {
    /**
     *  Sets ad slot id.
     */
    setAdId(adSlotId: string): void;,

    /**
     *  Sets parameters of ad request
     */
    setAdParam(adParam: AdParam): void;,

    /**
     *  Initiates a request to load an ad.
     */
    loadAd(): void;,

    /**
     *  Displays an interstitial ad.
     */
    show(): void;,

    /**
     *  Checks whether ad loading is complete.
     */
    isLoaded(): Promise<boolean>;,

    /**
     *  Add listener for the event when ad fails to load.
     */
    adFailedListenerAdd(listenerFn: (response: Error) => void): void;,

    /**
     *  Remove the listener for the event when ad fails to load.
     */
    adFailedListenerRemove(): void;,

    /**
     *  Add listener for the event when ad is closed.
     */
    adClosedListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when ad is closed.
     */
    adClosedListenerRemove(): void;,

    /**
     *  Add listener for the event when the user leaves the app.
     */
    adLeaveListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event the user leaves the app.
     */
    adLeaveListenerRemove(): void;,

    /**
     *  Add listener for the event when ad is displayed.
     */
    adOpenedListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when ad is displayed.
     */
    adOpenedListenerRemove(): void;,

    /**
     *  Add listener for the event when ad loads.
     */
    adLoadedListenerAdd(listenerFn: (response: InterstatialAd) => void): void;,

    /**
     *  Remove the listener for the event when ad loads.
     */
    adLoadedListenerRemove(): void;,

    /**
     *  Add listener for the event when ad is clicked.
     */
    adClickedListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when ad is clicked.
     */
    adClickedListenerRemove(): void;,

    /**
     *  Add listener for the event when ad impression is detected.
     */
    adImpressionListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when ad impression is detected.
     */
    adImpressionListenerRemove(): void;,

    /**
     *  Remove all listeners for events of HMSInterstitial
     */
    allListenersRemove(): void;,
  };

  /**
   *  Describes the install referrer information.
   */
  export interface ReferrerDetails {
    /**
     *  Install referrer information.
     */
    installReferrer: string;

    /**
     *  The app installation timestamp, in milliseconds.
     */
    installBeginTimestampMillisecond: number;

    /**
     *  The app installation timestamp, in seconds.
     */
    installBeginTimestampSeconds: number;

    /**
     *  The ad click timestamp, in milliseconds.
     */
    referrerClickTimestampMillisecond: number;

    /**
     *  The ad click timestamp, in seconds.
     */
    referrerClickTimestampSeconds: number;
  }

  /**
   *  Install referrer connection response.
   */
  export interface InstallReferrerResponse {
    /**
     *  Response code.
     */
    responseCode: number;

    /**
     *  Response message.
     */
    responseMessage: string;
  }

  /**
   *  HMSInstallReferrer module for install referrer functions
   */
  export type HMSInstallReferrer = {
    /**
     *  Starts to connect to the install referrer service. The first string
     *  argument should be one of values of [`CallMode`](#callmode). And the
     *  boolean argument indicates test mode. The last string argument is the
     *  name of the package that the service receives information about.
     */
    startConnection(
      callMode: CallModeType,
      isTest: boolean,
      pkgName: string,
    ): Promise<boolean>;,

    /**
     *  Ends the service connection and releases all occupied resources.
     */
    endConnection(): Promise<null>;,

    /**
     *  Obtains install referrer information.
     */
    getReferrerDetails(): Promise<ReferrerDetails>;,

    /**
     *  Indicates whether the service connection is ready.
     */
    isReady(): Promise<boolean>;,

    /**
     *  Add listener for the event when service connection is complete
     */
    serviceConnectedListenerAdd(
      listenerFn: (response: InstallReferrerResponse) => void,
    ): void;,

    /**
     *  Remove the listener for the event when service connection is complete
     */
    serviceConnectedListenerRemove(): void;,

    /**
     *  Add listener for the event when service is crashed or killed.
     */
    serviceDisconnectedListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when service is crashed or killed.
     */
    serviceDisconnectedListenerRemove(): void;,

    /**
     *  Remove all listeners for events of HMSInstallReferrer
     */
    allListenersRemove(): void;,
  };

  /**
   *  React prop defining banner ad sizes.
   */
  export interface BannerAdSizeProp {
    /**
     *  Banner ad sizes. `BannerAdSizes` for possible values.
     */
    bannerAdSize: BannerAdSizeType;

    /**
     *  If banner ad size is 'portrait', or 'landscape', or
     *  'currentDirection', this will be needed to set
     *  the width of the banner.
     */
    width?: number;
  }

  /**
   *  Banner information from banner load event.
   */
  export interface BannerResult {
    /**
     *  Ad slot id.
     */
    adId: string;

    /**
     *  Shows whether banner is loading.
     */
    isLoading: boolean;

    /**
     *  BannerAdSize information.
     */
    bannerAdSize: BannerAdSize;
  }

  /**
   *  Ad error.
   */
  export interface Error {
    /**
     *  Error code.
     */
    errorCode: integer;

    /**
     *  Error message.
     */
    errorMessage: string;
  }

  /**
   *  Events triggered by the map.
   */
  export interface AdEvent<T = {}> extends NativeSyntheticEvent<T> {}

  /**
   *  Props for <HMSBanner> component.
   */
  export interface HMSBannerProps extends ViewProps {
    /**
     *  The banner ad size.
     */
    bannerAdSize: BannerAdSizeProp;

    /**
     *  Ad slot id.
     */
    adId: string;

    /**
     *  Ad request parameter.
     */
    adParam?: AdParam;

    /**
     *  Listener for the event called when ad loads.
     */
    onAdLoaded?: (event: AdEvent<BannerResult>) => void;

    /**
     *  Listener for the event called when ad fails to load.
     */
    onAdFailed?: (event: AdEvent<Error>) => void;

    /**
     *  Listener for the event called when ad is opened.
     */
    onAdOpened?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when ad is clicked.
     */
    onAdClicked?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when ad is closed.
     */
    onAdClosed?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when ad impression is detected.
     */
    onAdImpression?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when user leaves the app.
     */
    onAdLeave?: (event: AdEvent<{}>) => void;
  }

  /**
   *  React component that shows banner ads.
   */
  export class HMSBanner extends React.Component<HMSBannerProps, any> {
    /**
     *  Loads banner.
     */
    loadAd(): void;

    /**
     *  Sets a rotation interval for banner ads. Input is rotation
     *  interval, in seconds. It should range from 30 to 120.
     */
    setRefresh(interval: number): void;

    /**
     *  Pauses any additional processing related to ad.
     */
    pause(): void;

    /**
     *  Resumes ad after the pause() method is called last time.
     */
    resume(): void;

    /**
     *  Destroys ad.
     */
    destroy(): void;
  }

  /**
   *  React prop defining media type of the ad.
   */
  export interface DisplayFormProp {
    /**
     *  Error code.
     */
    mediaType: NativeMediaType;

    /**
     *  Ad slot id.
     */
    adId: string;
  }

  /**
   *  Ad size.
   */
  export interface AdSize {
    /**
     *  Ad height, in dp.
     */
    height: integer;

    /**
     *  Ad width, in dp.
     */
    width: integer;
  }

  /**
   *  Video configuration used to control video playback.
   */
  export interface VideoConfiguration {
    /**
     *  The video playback scenario where the audio focus needs to be obtained.
     */
    audioFocusType?: AudioFocusType;

    /**
     *  The setting for using custom video control.
     */
    isCustomizeOperateRequested?: boolean;

    /**
     *  The setting for muting video when it starts.
     */
    isStartMuted?: boolean;
  }

  /**
   *  Native ad configuration.
   */
  export interface NativeAdConfiguration {
    /**
     *  Ad size.
     */
    adSize?: AdSize;

    /**
     *  Position of an ad choice icon.
     */
    choicesPosition?: ChoicesPositionType;

    /**
     *  Direction of an ad image.
     */
    mediaDirection?: DirectionType;

    /**
     *  Aspect ratio of an ad image.
     */
    mediaAspect?: number;

    /**
     *  Video Configuration.
     */
    videoConfiguration?: VideoConfiguration;

    /**
     *  The setting for requesting multiple ad images.
     */
    isRequestMultiImages?: boolean;

    /**
     *  The setting for enabling the SDK to download native ad images.
     */
    isReturnUrlsForImages?: boolean;
  }

  /**
   *  Styles of the components in native ads.
   */
  export interface AdTextStyle {
    /**
     *  Font size.
     */
    fontSize?: number;

    /**
     *  Color.
     */
    color?: number;

    /**
     *  Background color.
     */
    backgroundColor?: number;

    /**
     *  Visibility.
     */
    visibility?: boolean;
  }

  /**
   *  View options for components in Native ads.
   */
  export interface ViewOptionsProp {
    /**
     *  The option for showing media content.
     */
    showMediaContent?: boolean;

    /**
     *  The image scale type.
     */
    mediaImageScaleType?: ScaleTypes;

    /**
     *  The style of ad source.
     */
    adSourceTextStyle?: AdTextStyle;

    /**
     *  The style of ad flag.
     */
    adFlagTextStyle?: AdTextStyle;

    /**
     *  The style of ad title.
     */
    titleTextStyle?: AdTextStyle;

    /**
     *  The style of ad description.
     */
    descriptionTextStyle?: AdTextStyle;

    /**
     *  The style of ad call-to-action button.
     */
    callToActionStyle?: AdTextStyle;
  }

  export interface DislikeAdReason {
    /**
     *  The reason why a user dislikes an ad.
     */
    description: string;
  }

  /**
   *  Video controller, which implements video control such as
   *  playing, pausing, and muting a video.
   */
  export interface VideoOperator {
    /**
     *  The video aspect ratio.
     */
    aspectRatio: number;
    /**
     *  Shows whether ad content contains a video.
     */
    hasVideo: boolean;
    /**
     *  Shows whether a custom video control is used for a video ad.
     */
    isCustomizeOperateEnabled: boolean;
    /**
     *  Shows whether click to full screen option enabled for a video ad.
     */
    isClickToFullScreenEnabled: boolean;
    /**
     *  Shows whether a video is muted.
     */
    isMuted: boolean;
  }

  /**
   *  Native ad information.
   */
  export interface NativeAd {
    /**
     *  Ad source.
     */
    adSource: string;

    /**
     *  Ad description.
     */
    description: string;

    /**
     *  The text to be displayed on a button, for example,
     *  View Details or Install.
     */
    callToAction: string;

    /**
     *  The choices of not displaying the current ad.
     */
    dislikeAdReasons: DislikeAdReason[];

    /**
     *  Ad title.
     */
    title: string;

    /**
     *  Video operator used for the ad.
     */
    videoOperator: VideoOperator;

    /**
     *  Shows whether custom tap gestures are enabled.
     */
    isCustomClickAllowed: boolean;

    /**
     *  Shows whether custom ad closing is enabled.
     */
    isCustomDislikeThisAdEnabled: boolean;
  }

  export interface NativeAdLoader {
    /**
     *  Shows whether ads are being loaded.
     */
    isLoading: boolean;
  }

  /**
   *  Information related to native ad returned when ad is loaded.
   */
  export interface NativeResult {
    /**
     *  Native ad information.
     */
    nativeAd: NativeAd;

    /**
     *  Native ad configuration information.
     */
    nativeAdConfiguration: NativeAdConfiguration;

    /**
     *  Native ad loader information.
     */
    nativeAdLoader: NativeAdLoader;
  }

  /**
   *  Props for <HMSNative> component.
   */
  export interface HMSNativeProps extends ViewProps {
    /**
     *  The object parameter that has ad slot id and media type information.
     */
    displayForm: DisplayFormProp;

    /**
     *  Ad request parameter.
     */
    adParam?: AdParam;

    /**
     *  Native ad configuration parameter.
     */
    nativeConfig?: NativeAdConfiguration;

    /**
     *  View options parameter.
     */
    viewOptions?: ViewOptionsProp;

    /**
     *  Listener for the event called when ad loads.
     */
    onNativeAdLoaded?: (event: AdEvent<NativeResult>) => void;

    /**
     *  Listener for the event called when ad is disliked.
     */
    onAdDisliked?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when ad fails to load.
     */
    onAdFailed?: (event: AdEvent<Error>) => void;

    /**
     *  Listener for the event called when ad impression is detected.
     */
    onAdImpression?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when ad video starts playing.
     */
    onVideoStart?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when ad video plays.
     */
    onVideoPlay?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when ad video ends.
     */
    onVideoEnd?: (event: AdEvent<{}>) => void;
  }

  /**
   *  React component that shows native ads.
   */
  export class HMSNative extends React.Component<HMSNativeProps, any> {
    /**
     *  Loads native ad.
     */
    loadAd(): void;

    /**
     *  Dislikes ad with description.
     */
    dislikeAd(reason: string): void;

    /**
     *  Destroys ad.
     */
    destroy(): void;

    /**
     *  Goes to the page explaining why an ad is displayed.
     */
    gotoWhyThisAdPage(): void;

    /**
     *  Enables custom tap gestures.
     */
    setAllowCustomClick(): void;

    /**
     *  Reports a custom tap gesture.
     */
    recordClickEvent(): void;

    /**
     *  Reports an ad impression.
     */
    recordImpressionEvent(data: object): void;
  }

  /**
   *  HMSAds module
   */
  export default {
    /**
     *  Initializes the HUAWEI Ads SDK. The function returns
     *  a promise that resolves a string 'Hw Ads Initialized'.
     */
    init(): Promise<string>;,

    /**
     *  Obtains the version number of the HUAWEI Ads SDK. The function
     *  returns a promise that resolves a string of the version number.
     */
    getSDKVersion(): Promise<string>;,

    /**
     *  Un/mute videos.
     */
    setVideoMuted(mute: boolean): void;,

    /**
     *  Sets video volume.
     */
    setVideoVolume(volume: number): void;,

    /**
     *  Provides the global ad request configuration.
     */
    setRequestOptions(requestOptions: RequestOptions): Promise<RequestOptions>;,

    /**
     *  Obtains the global request configuration.
     */
    getRequestOptions(): Promise<RequestOptions>;,

    /**
     *  Provides ad consent configuration.
     */
    setConsent(consent: Consent): Promise<ConsentResult>;,

    /**
     *  Obtains ad consent configuration.
     */
    checkConsent(): Promise<ConsentResult>;,
  };
}
