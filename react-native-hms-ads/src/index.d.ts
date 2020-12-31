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
declare module "@hmscore/react-native-hms-ads" {
  import * as React from "react";
  import { NativeSyntheticEvent, ViewProps } from "react-native";

  /**
   *  Ad content rating.
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-contentcassification
   */
  export enum ContentClassification {
    AD_CONTENT_CLASSIFICATION_W = "W",
    AD_CONTENT_CLASSIFICATION_PI = "PI",
    AD_CONTENT_CLASSIFICATION_J = "J",
    AD_CONTENT_CLASSIFICATION_A = "A",
    AD_CONTENT_CLASSIFICATION_UNKOWN = "",
  }

  /**
   *  Whether to request only non-personalized ads.
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-nonpersonalizedad
   */
  export enum NonPersonalizedAd {
    ALLOW_ALL = 0,
    ALLOW_NON_PERSONALIZED = 1,
  }

  /**
   *  Child-directed setting.
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-tagforchild
   */
  export enum TagForChild {
    TAG_FOR_CHILD_PROTECTION_FALSE = 0,
    TAG_FOR_CHILD_PROTECTION_TRUE = 1,
    TAG_FOR_CHILD_PROTECTION_UNSPECIFIED = -1,
  }

  /**
   *  Setting directed to users under the age of consent.
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-underage
   */
  export enum UnderAge {
    PROMISE_FALSE = 0,
    PROMISE_TRUE = 1,
    PROMISE_UNSPECIFIED = -1,
  }

  /**
   *  Gender type
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-gender
   */
  export enum Gender {
    UNKNOWN = 0,
    MALE = 1,
    FEMALE = 2,
  }

  /**
   *  Whether to obtain the audio focus during video playback
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-banneradSize
   */
  export enum BannerAdSizes {
    B_160_600 = "160_600",
    B_300_250 = "300_250",
    B_320_50 = "320_50",
    B_320_100 = "320_100",
    B_360_57 = "360_57",
    B_360_144 = "360_144",
    B_468_60 = "468_60",
    B_728_90 = "728_90",
    B_CURRENT_DIRECTION = "currentDirection",
    B_PORTRAIT = "portrait",
    B_SMART = "smart",
    B_DYNAMIC = "dynamic",
    B_LANDSCAPE = "landscape",
    B_INVALID = "invalid",
  }

  /**
   *  Option for functions that can use Huawei SDK or
   *  [Aidl](https://developer.android.com/guide/components/aidl) service.
   */
  export enum CallMode {
    SDK = "sdk",
    AIDL = "aidl", // Will not be used anymore
  }

  /**
   *  Debug consent setting.
   */
  export enum DebugNeedConsent {
    DEBUG_DISABLED = 0,
    DEBUG_NEED_CONSENT = 1,
    DEBUG_NOT_NEED_CONSENT = 2,
  }

  /**
   *  Consent status.
   */
  export enum ConsentStatus {
    PERSONALIZED = 0,
    NON_PERSONALIZED = 1,
    UNKNOWN = 2,
  }

  /**
   *  Choice icon position constants
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-nativeadconfiguration-choicesposition
   */
  export enum ChoicesPosition {
    TOP_LEFT = 0,
    TOP_RIGHT = 1,
    BOTTOM_RIGHT = 2,
    BOTTOM_LEFT = 3,
    INVISIBLE = 4,
  }

  /**
   *  Orientation constant
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-nativeadconfiguration-direction
   */
  export enum Direction {
    ANY = 0,
    PORTRAIT = 1,
    LANDSCAPE = 2,
  }

  /**
   *  Whether to obtain the audio focus during video playback
   *  Refer this page https://developer.huawei.com/consumer/en/doc/development/HMS-References/ads-api-audiofocustype
   */
  export enum AudioFocusType {
    GAIN_AUDIO_FOCUS_ALL = 0,
    NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE = 1,
    NOT_GAIN_AUDIO_FOCUS_ALL = 2,
  }

  /**
   *  Native ad media types
   */
  export enum NativeMediaTypes {
    VIDEO = "video",
    IMAGE_SMALL = "image_small",
    IMAGE_LARGE = "image_large",
  }

  /**
   *  Options for scaling the bounds of an image
   *  Refer this page https://developer.android.com/reference/android/widget/ImageView.ScaleType
   */
  export enum ScaleTypes {
    MATRIX = "MATRIX",
    FIT_XY = "FIT_XY",
    FIT_START = "FIT_START",
    FIT_CENTER = "FIT_CENTER",
    FIT_END = "FIT_END",
    CENTER = "CENTER",
    CENTER_CROP = "CENTER_CROP",
    CENTER_INSIDE = "CENTER_INSIDE",
  }

  /**
   *  Ad request options.
   */
  interface RequestOptions {
    /**
     *  The OAID.
     */
    adContentClassification?: ContentClassification;

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
    nonPersonalizedAd?: NonPersonalizedAd;

    /**
     *  The OAID.
     */
    tagForChildProtection?: TagForChild;

    /**
     *  The OAID.
     */
    tagForUnderAgeOfPromise?: UnderAge;
  }

  /**
   *  Ad provider.
   */
  interface AdProvider {
    /**
     *  Id of ad provider.
     */
    id: ContentClassification;

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
  interface ConsentResult {
    /**
     *  Status of consent.
     */
    consentStatus: ConsentStatus;

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
  interface Consent {
    /**
     *  Consent option.
     */
    consentStatus?: ConsentStatus;

    /**
     *  DebugNeedConsent option.
     */
    debugNeedConsent?: DebugNeedConsent;

    /**
     *  UnderAge option.
     */
    underAgeOfPromise?: UnderAge;

    /**
     *  Device Id
     */
    testDeviceId?: string;
  }

  /**
   *  Information about advertised clients.
   */
  interface AdvertisingIdClientInfo {
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
    getAdvertisingIdInfo(callMode: CallMode): Promise<AdvertisingIdClientInfo>;,

    /**
     *  Verifies the OAID and 'Limit ad tracking' setting
     */
    verifyAdvertisingId(advertisingInfo: AdvertisingIdClientInfo): Promise<boolean>;,
  };

  /**
   *  Server-side verification parameter.
   */
  interface VerifyConfig {
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
  interface AdParam {
    /**
     *  Ad content rating. Check ContentClassification for possible values.
     */
    adContentClassification?: ContentClassification;

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
    gender?: Gender;

    /**
     *  The setting of requesting personalized ads. Check NonPersonalizedAd
     *  for possible values.
     */
    nonPersonalizedAd?: NonPersonalizedAd;

    /**
     *  Origin of request.
     */
    requestOrigin?: string;

    /**
     *  The setting of processing ad requests according to the COPPA.
     *  Check TagForChild for possible values.
     */
    tagForChildProtection?: TagForChild;

    /**
     *  The setting of processing ad requests as directed to users under
     *  the age of consent. Check UnderAge for possible values.
     */
    tagForUnderAgeOfPromise?: UnderAge;

    /**
     *  Targeting content url.
     */
    targetingContentUrl?: string;
  }

  /**
   *  Information about the reward item in a rewarded ad.
   */
  interface Reward {
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
  interface RewardAd {
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
    setAdId(adSlotId: string): Promise<null>;,

    /**
     *  Sets to display ad on HMS Core app
     */
    onHMSCore(onHMSCore: boolean): Promise<null>;,

    /**
     *  Sets user id
     */
    setUserId(userID: string): Promise<null>;,

    /**
     *  Sets custom data in string
     */
    setData(data: string): Promise<null>;,

    /**
     *  Sets custom data in string
     */
    setVerifyConfig(verifyConfig: VerifyConfig): Promise<null>;,

    /**
     *  Sets parameters of ad request
     */
    setAdParam(adParam: AdParam): Promise<null>;,

    /**
     *  Sets custom data in string
     */
    pause(): Promise<null>;,

    /**
     *  Resumes the ad.
     */
    resume(): Promise<null>;,

    /**
     *  Destroys the ad.
     */
    destroy(): Promise<null>;,

    /**
     *  Shows the ad.
     */
    show(): Promise<null>;,

    /**
     *  Requests ad.
     */
    loadAd(): Promise<null>;,

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
     *  Add listener for the event when user leaves the app.
     */
    adLeftAppListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when user leaves the app.
     */
    adLeftAppListenerRemove(): void;,

    /**
     *  Add listener for the event when ad is completed.
     */
    adCompletedListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when ad is completed.
     */
    adCompletedListenerRemove(): void;,

    /**
     *  Add listener for the event when ad is started.
     */
    adStartedListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when ad is started.
     */
    adStartedListenerRemove(): void;,

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
    setAdId(adSlotId: string): Promise<null>;,

    /**
     *  Sets logo text.
     */
    setLogoText(logoText: string): Promise<null>;,

    /**
     *  Sets copyright text.
     */
    setCopyrightText(cpyrightText: string): Promise<null>;,

    /**
     *  Sets screen orientation
     */
    setOrientation(orientation: number): Promise<null>;,

    /**
     *  Sets default app launch image in portrait mode,
     *  which is displayed before a splash ad is displayed
     */
    setSloganResource(sloganResource: string): Promise<null>;,

    /**
     *  Sets default app launch image in landscape mode,
     *  which is displayed before a splash ad is displayed.
     */
    setWideSloganResource(wideSloganResource: string): Promise<null>;,

    /**
     *  Sets app logo.
     */
    setLogoResource(logoResource: string): Promise<null>;,

    /**
     *  Sets app text resource.
     */
    setMediaNameResource(mediaNameResource: string): Promise<null>;,

    /**
     *  Sets the audio focus preemption policy for a video splash ad.
     */
    setAudioFocusType(audioFocusType: AudioFocusType): Promise<null>;,

    /**
     *  Sets parameters of ad request
     */
    setAdParam(adParam: AdParam): Promise<null>;,

    /**
     *  Pauses ad.
     */
    pause(): Promise<null>;,

    /**
     *  Resumes the ad.
     */
    resume(): Promise<null>;,

    /**
     *  Destroys the ad.
     */
    destroy(): Promise<null>;,

    /**
     *  Shows the ad.
     */
    show(): Promise<null>;,

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
  interface InterstitialAd {
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
    setAdId(adSlotId: string): Promise<null>;,

    /**
     *  Sets to display ad on HMS Core app
     */
    onHMSCore(onHMSCore: boolean): Promise<null>;,

    /**
     *  Sets parameters of ad request
     */
    setAdParam(adParam: AdParam): Promise<null>;,

    /**
     *  Initiates a request to load an ad.
     */
    loadAd(): Promise<null>;,

    /**
     *  Displays an interstitial ad.
     */
    show(): Promise<null>;,

    /**
     *  Checks whether ad loading is complete.
     */
    isLoaded(): Promise<boolean>;,

    /**
     *  Checks whether ad is loading.
     */
    isLoading(): Promise<boolean>;,

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
    adLoadedListenerAdd(listenerFn: (response: InterstitialAd) => void): void;,

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
     *  Add listener for the event when ad is completed.
     */
    adCompletedListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when ad is completed.
     */
    adCompletedListenerRemove(): void;,

    /**
     *  Add listener for the event when ad starts.
     */
    adStartedListenerAdd(listenerFn: () => void): void;,

    /**
     *  Remove the listener for the event when ad starts.
     */
    adStartedListenerRemove(): void;,

    /**
     *  Remove all listeners for events of HMSInterstitial
     */
    allListenersRemove(): void;,
  };

  /**
   *  Describes the install referrer information.
   */
  interface ReferrerDetails {
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
  interface InstallReferrerResponse {
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
  export const HMSInstallReferrer = {
    /**
     *  Starts to connect to the install referrer service. The first string
     *  argument should be one of values of [CallMode](#callmode). And the
     *  boolean argument indicates test mode. The last string argument is the
     *  name of the package that the service receives information about.
     */
    startConnection(callMode: CallMode, isTest: boolean, pkgName: string): Promise<null>;,

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
    serviceConnectedListenerAdd(listenerFn: (response: InstallReferrerResponse) => void): void;,

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
  interface BannerAdSizeProp {
    /**
     *  Banner ad sizes. `BannerAdSizes` for possible values.
     */
    bannerAdSize: BannerAdSizes;

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
  interface BannerInfo {
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
    bannerAdSize: BannerAdSizes;
  }

  /**
   *  Ad error.
   */
  interface Error {
    /**
     *  Error code.
     */
    errorCode: number;

    /**
     *  Error message.
     */
    errorMessage: string;
  }

  /**
   *  Events triggered by the map.
   */
  interface AdEvent<T = {}> extends NativeSyntheticEvent<T> {}

  /**
   *  Props for <HMSBanner> component.
   */
  interface HMSBannerProps extends ViewProps {
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
    onAdLoaded?: (event: AdEvent<{}>) => void;

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
     *  Gets information related to HMSBanner component.
     */
    getInfo(): Promise<BannerInfo>;

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

  interface PlayTime {
    /**
     *  Played duration, in milliseconds.
     */
    playTime: number;
  }

  interface WithPercentage {
    /**
     *  Playback progress, in percentage.
     */
    percentage: number;
  }

  interface WithExtra {
    /**
     *  Additional information.
     */
    extra: number;
  }

  interface WithError {
    /**
     *  Error information.
     */
    error: Error;
  }

  /**
   *  Instream ad information.
   */
  interface InstreamAd {
    /**
     *  Indicates whether ad has been clicked.
     */
    isClicked: boolean;

    /**
     *  Indicates whether an ad has expired.
     */
    isExpired: boolean;

    /**
     *  Indicates whether ad is an image ad
     */
    isImageAd: boolean;

    /**
     *  Indicates whether ad has been displayed.
     */
    isShown: boolean;

    /**
     *  Indicates whether ad is a video ad
     */
    isVideoAd: boolean;

    /**
     *  Duration of a roll ad, in milliseconds.
     */
    duration: number;

    /**
     *  Redirection link to `Why this ad`.
     */
    whyThisAd: string;

    /**
     *  Text to be displayed on a button.
     */
    callToAction: string;

    /**
     *  Indicates whether a task is an ad task.
     */
    adSign: string;

    /**
     *  Ad source.
     */
    adSource: string;
  }

  interface InstreamInfo {
    /**
     *  Indicates whether ad is being played.
     */
    isPlaying: boolean;

    /**
     *  Indicates whether ad is loading.
     */
    isLoading: boolean;

    /**
     *  Ad slot id
     */
    adId: string;

    /**
     *  Maximum total duration of roll ads, in seconds
     */
    totalDuration: number;

    /**
     *  Maximum number of roll ads.
     */
    maxCount: number;

    /**
     *  List of roll ads.
     */
    instreamAds: InstreamAd[];
  }

  /**
   *  Props for <HMSInstream> component.
   */
  interface HMSInstreamProps extends ViewProps {
    /**
     *  Ad slot id.
     */
    adId: string;

    /**
     *  Maximum number of roll ads.
     */
    maxCount: number;

    /**
     *  Maximum total duration of roll ads, in seconds
     */
    totalDuration: number;

    /**
     *  Ad request parameter.
     */
    adParam?: AdParam;

    /**
     *  Listener for the event called when ad is muted
     */
    onMute?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when ad is unmuted
     */
    onUnmute?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when roll ads are successfully loaded.
     */
    onAdLoaded?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when roll ads fail to be loaded.
     */
    onAdFailed?: (event: AdEvent<Error>) => void;

    /**
     *  Listener for the event called when a roll ad is switched to another.
     */
    onSegmentMediaChange?: (event: AdEvent<InstreamAd>) => void;

    /**
     *  Listener for the event called during the playback of a roll ad.
     */
    onMediaProgress?: (event: AdEvent<PlayTime | WithPercentage>) => void;

    /**
     *  Listener for the event called when the playback of a roll ad starts.
     */
    onMediaStart?: (event: AdEvent<PlayTime>) => void;

    /**
     *  Listener for the event called when the playback of a roll ad is paused.
     */
    onMediaPause?: (event: AdEvent<PlayTime>) => void;

    /**
     *  Listener for the event called when the playback of a roll ad stops.
     */
    onMediaStop?: (event: AdEvent<PlayTime>) => void;

    /**
     *  Listener for the event called when the playback of a roll ad
     *  is complete.
     */
    onMediaCompletion?: (event: AdEvent<PlayTime>) => void;

    /**
     *  Listener for the event called when a roll ad fails to be played.
     */
    onMediaError?: (event: AdEvent<PlayTime | WithExtra | WithError>) => void;

    /**
     *  Listener for the event called when ad is clicked.
     */
    onClick?: (event: AdEvent<{}>) => void;
  }

  /**
   *  React component that shows instream ads.
   */
  export class HMSInstream extends React.Component<HMSInstreamProps, any> {
    /**
     *  Gets information related to HMSInstream component.
     */
    getInfo(): Promise<InstreamInfo>;

    /**
     *  Loads instream ad.
     */
    loadAd(): void;

    /**
     *  Sets loaded ads to view in order to show them
     */
    register(): void;

    /**
     *  Mutes ad.
     */
    mute(): void;

    /**
     *  Unmutes ad.
     */
    unmute(): void;

    /**
     *  Stops ad.
     */
    stop(): void;

    /**
     *  Pauses ad.
     */
    pause(): void;

    /**
     *  Plays ad.
     */
    play(): void;

    /**
     *  Destroys ad.
     */
    destroy(): void;
  }

  /**
   *  React prop defining media type of the ad.
   */
  interface DisplayFormProp {
    /**
     *  Error code.
     */
    mediaType: NativeMediaTypes;

    /**
     *  Ad slot id.
     */
    adId: string;
  }

  /**
   *  Ad size.
   */
  interface AdSize {
    /**
     *  Ad height, in dp.
     */
    height: number;

    /**
     *  Ad width, in dp.
     */
    width: number;
  }

  /**
   *  Video configuration used to control video playback.
   */
  interface VideoConfiguration {
    /**
     *  The video playback scenario where the audio focus needs to be obtained.
     */
    audioFocusType?: AudioFocusType;

    /**
     *  The setting for using custom video control.
     */
    isCustomizeOperateRequested?: boolean;

    /**
     *  Setting indicating whether a video ad can be displayed
     *  in full-screen mode upon a click.
     */
    isClickToFullScreenRequested?: boolean;

    /**
     *  The setting for muting video when it starts.
     */
    isStartMuted?: boolean;
  }

  /**
   *  Native ad configuration.
   */
  interface NativeAdConfiguration {
    /**
     *  Ad size.
     */
    adSize?: AdSize;

    /**
     *  Position of an ad choice icon.
     */
    choicesPosition?: ChoicesPosition;

    /**
     *  Direction of an ad image.
     */
    mediaDirection?: Direction;

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
  interface AdTextStyle {
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
  interface ViewOptionsProp {
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

  interface DislikeAdReason {
    /**
     *  The reason why a user dislikes an ad.
     */
    description: string;
  }

  /**
   *  Video controller, which implements video control such as
   *  playing, pausing, and muting a video.
   */
  interface VideoOperator {
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
  }

  /**
   *  Native ad information.
   */
  interface NativeAd {
    /**
     *  Indicates whether a task is an ad task.
     */
    adSign: string;

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
     *  Ad title.
     */
    title: string;

    /**
     *  The choices of not displaying the current ad.
     */
    dislikeAdReasons: DislikeAdReason[];

    /**
     *  Redirection link to Why this ad.
     */
    whyThisAd: string;

    /**
     *  Unique ID of an ad.
     */
    uniqueId: string;

    /**
     *  Ad creative type.
     */
    creativeType: string;

    /**
     *  Video operator used for the ad.
     */
    videoOperator: VideoOperator | Muted;

    /**
     *  Shows whether custom tap gestures are enabled.
     */
    isCustomClickAllowed: boolean;

    /**
     *  Shows whether custom ad closing is enabled.
     */
    isCustomDislikeThisAdEnabled: boolean;
  }

  interface NativeAdLoader {
    /**
     *  Shows whether ads are being loaded.
     */
    isLoading: boolean;
  }

  /**
   *  Information related to native ad returned when ad is loaded.
   */
  interface NativeInfo {
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

  interface Muted {
    /**
     *  Shows whether a video is muted.
     */
    isMuted: boolean;
  }

  /**
   *  Props for <HMSNative> component.
   */
  interface HMSNativeProps extends ViewProps {
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
    onNativeAdLoaded?: (event: AdEvent<{}>) => void;

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

    /**
     *  Listener for the event called when ad video pauses.
     */
    onVideoPause?: (event: AdEvent<{}>) => void;

    /**
     *  Listener for the event called when the mute status of a video changes.
     */
    onVideoMute?: (event: AdEvent<Muted>) => void;
  }

  /**
   *  React component that shows native ads.
   */
  export class HMSNative extends React.Component<HMSNativeProps, any> {
    /**
     *  Gets information related to HMSNative component.
     */
    getInfo(): Promise<NativeInfo>;

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
     *  Enables HMSLogger capability which is used for sending usage
     *  analytics of Ads SDK's methods to improve the service quality.
     */
    enableLogger(): Promise<null>;,

    /**
     *  Disables HMSLogger capability which is used for sending usage
     *  analytics of Ads SDK's methods to improve the service quality.
     */
    disableLogger(): Promise<null>;,

    /**
     *  Obtains the version number of the HUAWEI Ads SDK. The function
     *  returns a promise that resolves a string of the version number.
     */
    getSDKVersion(): Promise<string>;,

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
     *  Sets the user consent string that complies with [TCF 2.0](https://iabeurope.eu/tcf-2-0/)
     */
    setConsentString(consent: string): Promise<null>;,

    /**
     *  Obtains ad consent configuration.
     */
    checkConsent(): Promise<ConsentResult>;,
  };
}
