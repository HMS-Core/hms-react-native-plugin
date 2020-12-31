# React-Native HMS Ads

---

## Contents

- [1. Introduction](#1-introduction)
- [2. Installation Guide](#2-installation-guide)
  - [Integrating React Native Ads Plugin](#integrating-react-native-ads-plugin)
- [3. API Reference](#3-api-reference)
  - [Components](#components)
  - [Modules](#modules)
  - [Methods](#methods)
  - [Events](#events)
  - [Constants](#constants)
  - [DataTypes](#datatypes)
- [4. Configuration and Description](#4-configuration-and-description)
- [5. Sample Project](#5-sample-project)
- [6. Questions or Issues](#6-questions-or-issues)
- [7. Licensing and Terms](#7-licensing-and-terms)

---

## 1. Introduction

This module enables communication between Huawei Ads SDK and React Native platform. It exposes all functionality provided by Huawei Ads SDK.

---

## 2. Installation Guide

### Add Maven Repository

- Add maven repository address into 'android/build.gradle' file.

```groovy
maven {url 'https://developer.huawei.com/repo/'}
```

### Integrating the React Native HMS Ads

#### Using NPM

- You can download the module from [npm](https://www.npmjs.com/package/@hmscore/react-native-hms-ads)

  `npm i @hmscore/react-native-hms-ads`

#### Using Download Link

- Download the module and copy it into `node_modules/@hmscore` folder. If `@hmscore` folder does not exist, create one. The folder structure can be seen below;

```text
project-name
    |_ node_modules
        |_ ...
        |_ @hmscore
          |_ ...
          |_ react-native-hms-ads
          |_ ...
        |_ ...
```

- Add following lines into 'android/settings.gradle' file

```gradle
include ':react-native-hms-ads'
project(':react-native-hms-ads').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-ads/android')
```

- Add 'react-native-hms-ads' dependency into 'android/app/build.gradle' file.

```groovy
implementation project(":react-native-hms-ads")
```

- Add 'HMSAdsPackage' to your application.

```java
import com.huawei.hms.rn.ads.HMSAdsPackage;
...
...

@Override
protected List<ReactPackage> getPackages() {
  List<ReactPackage> packages = new PackageList(this).getPackages();
  packages.add(new HMSAdsPackage());
  return packages;
}
```

---

## 3. API Reference

### Components

#### HMSBanner

Banner ad component

##### Simple Call Example

```jsx
import {
  HMSBanner,
  BannerAdSizes,
} from '@hmscore/react-native-hms-ads';

let bannerRef;

<HMSBanner
  style={{height: 100}}
  bannerAdSize={{
    bannerAdSize:BannerAdSizes.B_320_100,
  }}
  adId="testw6vs28auh3"
  ref={(el) => (bannerRef = el)}
/>
```

##### Props

| Prop           | Type                                  | Description                                                                                                                                                              |
| -------------- | ------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| bannerAdSize   | [BannerAdSizeProp](#banneradsizeprop) | The object  parameter that has banner size information. It can have width information if needed.                                                                         |
| adId           | string                                | Ad slot id.                                                                                                                                                              |
| adParam        | [AdParam](#adparam)                   | Ad request parameter.                                                                                                                                                    |
| onAdLoaded     | function                              | The function to handle the event when ad loads.                                                                                                                          |
| onAdFailed     | function                              | The function to handle the event when ad fails to load. It gets event information object as argument which has `nativeEvent` as key and [Error](#error) object as value. |
| onAdOpened     | function                              | The function to handle the event when ad is opened.                                                                                                                      |
| onAdClicked    | function                              | The function to handle the event when ad is clicked.                                                                                                                     |
| onAdClosed     | function                              | The function to handle the event when ad is closed.                                                                                                                      |
| onAdImpression | function                              | The function to handle the event when ad impression is detected.                                                                                                         |
| onAdLeave      | function                              | The function to handle the event when user leaves the app.                                                                                                               |

##### Events

| Event          | Description                               |
| -------------- | ----------------------------------------- |
| onAdLoaded     | Triggered when ad loads.                  |
| onAdFailed     | Triggered when ad fails to load.          |
| onAdOpened     | Triggered when ad is opened.              |
| onAdClicked    | Triggered when ad is clicked.             |
| onAdClosed     | Triggered when ad is closed.              |
| onAdImpression | Triggered when ad impression is detected. |
| onAdLeave      | Triggered when user leaves the app.       |

##### BannerAdSizeProp

| Parameter    | Type   | Definition                                                                                                                         |
| ------------ | ------ | ---------------------------------------------------------------------------------------------------------------------------------- |
| bannerAdSize | string | Banner ad sizes.  Check [BannerAdSizes](#banneradsizes) for possible values.                                                       |
| width        | number | If banner ad size is `'portrait'`, or `'landscape'`, or `'currentDirection'`, this will be needed to set the width of the banner . |

##### Functions

###### getInfo()

Obtains information related to component.

| Return Type                        | Description                                                                           |
| ---------------------------------- | ------------------------------------------------------------------------------------- |
| Promise<[BannerInfo](#bannerinfo)> | Promise that resolves [BannerInfo](#bannerinfo) object if the operation is successful |

Sample Code:

```jsx
bannerRef.getInfo()
  .then((res) => console.log("HMSBanner.getInfo", res))
  .catch((err) => console.warn(err));
```

###### loadAd()

Loads banner.

Sample Code:

```jsx
bannerRef.loadAd()
```

###### setRefresh()

Sets a rotation interval for banner ads. Input is rotation interval, in seconds. It should range from 30 to 120.

| Parameter | Type   | Description                                                     |
| --------- | ------ | --------------------------------------------------------------- |
| interval  | number | Rotation interval, in seconds. It should range from 30 to 120 . |

Sample Code:

```jsx
bannerRef.setRefresh(60)
```

###### pause()

Pauses any additional processing related to ad.

Sample Code:

```jsx
bannerRef.pause()
```

###### resume()

Resumes ad after the pause() method is called last time.

Sample Code:

```jsx
bannerRef.resume()
```

###### destroy()

Destroys ad.

Sample Code:

```jsx
bannerRef.destroy()
```

##### Complex Call Example

```jsx
import {
  HMSBanner,
  BannerAdSizes,
  ContentClassification,
  Gender,
  NonPersonalizedAd,
  TagForChild,
  UnderAge,
} from '@hmscore/react-native-hms-ads';

<HMSBanner
  style={{height: 100}}
  bannerAdSize={{
    bannerAdSize:BannerAdSizes.B_PORTRAIT,
    width: 300,
  }}
  adId="testw6vs28auh3"
  adParam={{
    adContentClassification:
      ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
    gender: Gender.UNKNOWN,
    nonPersonalizedAd: NonPersonalizedAd.ALLOW_ALL,
    tagForChildProtection:
      TagForChild.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED,
    tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED,
  }}
  onAdLoaded={(_) => console.log("HMSBanner onAdLoaded")}
  onAdFailed={(e) => {
    console.warn("HMSBanner onAdFailed", e.nativeEvent);
  }}
  onAdOpened={(_) => console.log("HMSBanner onAdOpened")}
  onAdClicked={(_) => console.log("HMSBanner onAdClicked")}
  onAdClosed={(_) => console.log("HMSBanner onAdClosed")}
  onAdImpression={(_) => console.log("HMSBanner onAdImpression")}
  onAdLeave={(_) => console.log("HMSBanner onAdLeave")}
/>
```

---

#### HMSInstream

Instream ad component

##### Simple Call Example

```jsx
import { HMSInstream } from '@hmscore/react-native-hms-ads';

let instreamRef;
<HMSInstream
  style={{height: 189, width: 328}}
  adId="testy3cglm3pj0"
  maxCount={4}
  totalDuration={60}
  ref={(el) => (instreamRef = el)}
/>
```

##### Props

| Prop                 | Type                | Description                                                                                                                                                                                                                                                                |
| -------------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| adId                 | string              | Ad slot id.                                                                                                                                                                                                                                                                |
| maxCount             | number              | Maximum number of roll ads.id.                                                                                                                                                                                                                                             |
| totalDuration        | number              | Maximum total duration of roll ads, in secondsid.                                                                                                                                                                                                                          |
| adParam              | [AdParam](#adparam) | Ad request parameter.                                                                                                                                                                                                                                                      |
| onMute               | function            | The function to handle the event called when ad is muted                                                                                                                                                                                                                   |
| onUnmute             | function            | The function to handle the event called when ad is unmuted                                                                                                                                                                                                                 |
| onAdLoaded           | function            | The function to handle the event called when roll ads are successfully loaded.                                                                                                                                                                                             |
| onAdFailed           | function            | The function to handle the event called when roll ads fail to be loaded. It gets event information object as argument which has `nativeEvent` as key and [Error](#error) object as value.                                                                                  |
| onSegmentMediaChange | function            | The function to handle the event called when a roll ad is switched to another.  It gets event information object as argument which has `nativeEvent` as key and [InstreamAd](#instreamad) object as value.                                                                 |
| onMediaProgress      | function            | The function to handle the event called during the playback of a roll ad. It gets event information object as argument which has `nativeEvent` as key and union of [PlayTime](#playtime) and [WithPercentage](#withpercentage) objects as value.                           |
| onMediaStart         | function            | The function to handle the event called when the playback of a roll ad starts. It gets event information object as argument which has `nativeEvent` as key and [PlayTime](#playtime) object as value.                                                                      |
| onMediaPause         | function            | The function to handle the event called when the playback of a roll ad is paused. It gets event information object as argument which has `nativeEvent` as key and [PlayTime](#playtime) object as value.                                                                   |
| onMediaStop          | function            | The function to handle the event called when the playback of a roll ad stops. It gets event information object as argument which has `nativeEvent` as key and [PlayTime](#playtime) object as value.                                                                       |
| onMediaCompletion    | function            | The function to handle the event called when the playback of a roll ad is complete.  It gets event information object as argument which has `nativeEvent` as key and union of [PlayTime](#playtime), [WithExtra](#withextra) and [WithError](#witherror) objects as value. |
| onMediaError         | function            | The function to handle the event called when a roll ad fails to be played.  It gets event information object as argument which has `nativeEvent` as key and [PlayTime](#playtime) object as value.                                                                         |
| onClick              | function            | The function to handle the event called when ad is clicked.                                                                                                                                                                                                                |

##### Events

| Event                | Description                                           |
| -------------------- | ----------------------------------------------------- |
| onMute               | Triggered when ad is muted                            |
| onUnmute             | Triggered when ad is unmuted                          |
| onAdLoaded           | Triggered when roll ads are successfully loaded.      |
| onAdFailed           | Triggered when roll ads fail to be loaded.            |
| onSegmentMediaChange | Triggered when a roll ad is switched to another.      |
| onMediaProgress      | Triggered during the playback of a roll ad.           |
| onMediaStart         | Triggered when the playback of a roll ad starts.      |
| onMediaPause         | Triggered when the playback of a roll ad is paused.   |
| onMediaStop          | Triggered when the playback of a roll ad stops.       |
| onMediaCompletion    | Triggered when the playback of a roll ad is complete. |
| onMediaError         | Triggered when a roll ad fails to be played.          |
| onClick              | Triggered when ad is clicked.                         |

##### Functions

###### getInfo()

Obtains information related to component.

| Return Type                            | Description                                                                               |
| -------------------------------------- | ----------------------------------------------------------------------------------------- |
| Promise<[InstreamInfo](#instreaminfo)> | Promise that resolves [InstreamInfo](#instreaminfo) object if the operation is successful |

Sample Code:

```jsx
instreamRef.getInfo()
  .then((res) => console.log("HMSInstream.getInfo", res))
  .catch((err) => console.warn(err));
```

###### loadAd()

Loads instream ad.

Sample Code:

```jsx
instreamRef.loadAd()
```

###### register()

Sets loaded ads to view in order to show them.

Sample Code:

```jsx
instreamRef.register()
```

###### mute()

Mutes ad.

Sample Code:

```jsx
instreamRef.mute()
```

###### unmute()

Unmutes ad.

Sample Code:

```jsx
instreamRef.unmute()
```

###### stop()

Stops ad.

Sample Code:

```jsx
instreamRef.stop()
```

###### pause()

Pauses ad.

Sample Code:

```jsx
instreamRef.pause()
```

###### play()

Plays ad.

Sample Code:

```jsx
instreamRef.play()
```

###### destroy()

Destroys ad.

Sample Code:

```jsx
instreamRef.destroy()
```

##### Complex Call Example

```jsx
import {
  HMSInstream,
} from '@hmscore/react-native-hms-ads';

<HMSInstream
  style={{height: 189, width: 328}}
  adId="testy3cglm3pj0"
  maxCount={4}
  totalDuration={60}
  onClick={(_) => console.log("HMSInstream onClick")}
  onMute={(_) => console.log("HMSInstream onMute")}
  onUnmute={(_) => console.log("HMSInstream onUnmute")}
  onAdLoaded={(_) => console.log("HMSInstream onAdLoaded")}
  onAdFailed={(e) => console.log("HMSInstream onAdFailed", e.nativeEvent)}
  onSegmentMediaChange={(e) =>
    console.log("HMSInstream onSegmentMediaChange", e.nativeEvent)
  }
  onMediaProgress={(e) =>
    console.log("HMSInstream onMediaProgress", e.nativeEvent)
  }
  onMediaStart={(e) =>
    console.log("HMSInstream onMediaStart", e.nativeEvent)
  }
  onMediaPause={(e) =>
    console.log("HMSInstream onMediaPause", e.nativeEvent)
  }
  onMediaStop={(e) => console.log("HMSInstream onMediaStop", e.nativeEvent)}
  onMediaCompletion={(e) =>
    console.log("HMSInstream onMediaCompletion", e.nativeEvent)
  }
  onMediaError={(e) =>
    console.log("HMSInstream onMediaError", e.nativeEvent)
  }
/>
```

---

#### HMSNative

Native ad component

##### Simple Call Example

```jsx
import {
  HMSNative,
  NativeMediaTypes,
} from '@hmscore/react-native-hms-ads';

let nativeRef;

<HMSNative
  style={{ height: 322 }}
  displayForm={{
    mediaType: NativeMediaTypes.VIDEO,
    adId: 'testy63txaom86'
  }}
  ref={(el) => (nativeRef = el)}
/>
```

##### Props

| Parameter        | Type                                            | Description                                                                                                                                                                             |
| ---------------- | ----------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| displayForm      | [DisplayFormProp](#displayformprop)             | The object parameter that has ad slot id and media type information.                                                                                                                    |
| adParam          | [AdParam](#adparam)                             | Ad request parameter.                                                                                                                                                                   |
| nativeConfig     | [NativeAdConfiguration](#nativeadconfiguration) | Native ad configuration parameter.                                                                                                                                                      |
| viewOptions      | [ViewOptionsProp](#viewoptionsprop)             | View options parameter.                                                                                                                                                                 |
| onNativeAdLoaded | function                                        | The function to handle the event when ad loads.                                                                                                                                         |
| onAdDisliked     | function                                        | The function to handle the event when ad is disliked.                                                                                                                                   |
| onAdFailed       | function                                        | The function to handle the event when ad fails to load. It gets event information object as argument which has `nativeEvent` as key and [Error](#error) object as value.                |
| onAdClicked      | function                                        | The function to handle the event when ad is clicked.                                                                                                                                    |
| onAdImpression   | function                                        | The function to handle the event when ad impression is detected.                                                                                                                        |
| onVideoStart     | function                                        | The function to handle the event when ad video starts playing.                                                                                                                          |
| onVideoPlay      | function                                        | The function to handle the event when ad video plays.                                                                                                                                   |
| onVideoEnd       | function                                        | The function to handle the event when ad video ends.                                                                                                                                    |
| onVideoPause     | function                                        | The function to handle the event when ad video pauses.                                                                                                                                  |
| onVideoMute      | function                                        | The function to handle the event when mute status of ad video changes. It gets event information object as argument which has `nativeEvent` as key and [Muted](#muted) object as value. |

##### Events

| Event            | Description                                     |
| ---------------- | ----------------------------------------------- |
| onNativeAdLoaded | Triggered when ad loads.                        |
| onAdDisliked     | Triggered when ad is disliked.                  |
| onAdFailed       | Triggered when ad fails to load                 |
| onAdClicked      | Triggered when ad is clicked.                   |
| onAdImpression   | Triggered when ad impression is detected.       |
| onVideoStart     | Triggered when ad video starts playing.         |
| onVideoPlay      | Triggered when ad video plays.                  |
| onVideoEnd       | Triggered when ad video ends.                   |
| onVideoPause     | Triggered when ad video pauses.                 |
| onVideoMute      | Triggered when mute status of ad video changes. |

##### DisplayFormProp

| Parameter | Type   | Definition                                                                                    |
| --------- | ------ | --------------------------------------------------------------------------------------------- |
| mediaType | string | Media type of the native ad. Check [NativeMediaTypes](#nativemediatypes) for possible values. |
| adId      | string | Ad slot id.                                                                                   |

##### ViewOptionsProp

| Parameter            | Type                        | Definition                                                              |
| -------------------- | --------------------------- | ----------------------------------------------------------------------- |
| showMediaContent     | boolean                     | The option for showing media content.                                   |
| mediaImageScaleType  | string                      | The image scale type. Check [ScaleType](#scaletype) for possible values |
| adSourceTextStyle    | [AdTextStyle](#adtextstyle) | The style of ad source.                                                 |
| adFlagTextStyle      | [AdTextStyle](#adtextstyle) | The style of ad flag.                                                   |
| titleTextStyle       | [AdTextStyle](#adtextstyle) | The style of ad title.                                                  |
| descriptionTextStyle | [AdTextStyle](#adtextstyle) | The style of ad description.                                            |
| callToActionStyle    | [AdTextStyle](#adtextstyle) | The style of ad call-to-action button.                                  |

##### Functions

###### getInfo()

Obtains information related to component.

| Return Type                        | Description                                                                           |
| ---------------------------------- | ------------------------------------------------------------------------------------- |
| Promise<[NativeInfo](#nativeinfo)> | Promise that resolves [NativeInfo](#nativeinfo) object if the operation is successful |

Sample Code:

```jsx
nativeRef.getInfo()
  .then((res) => console.log("HMSNative.getInfo", res))
  .catch((err) => console.warn(err));
```

###### loadAd()

Loads native ad.

Sample Code:

```jsx
nativeRef.loadAd()
```

###### dislikeAd()

Dislikes ad with description.

| Parameter | Type   | Description                     |
| --------- | ------ | ------------------------------- |
| reason    | string | Reason why the ad is disliked . |

Sample Code:

```jsx
nativeRef.dislikeAd('Just dont like it')
```

###### destroy()

Destroys ad.

Sample Code:

```jsx
nativeRef.destroy()
```

###### gotoWhyThisAdPage()

Goes to the page explaining why an ad is displayed.

Sample Code:

```jsx
nativeRef.gotoWhyThisAdPage()
```

###### setAllowCustomClick()

Enables custom tap gestures.

Sample Code:

```jsx
nativeRef.setAllowCustomClick()
```

###### recordClickEvent()

Reports a custom tap gesture.

Sample Code:

```jsx
nativeRef.recordClickEvent()
```

###### recordImpressionEvent()

Reports an ad impression.

| Parameter  | Type   | Description              |
| ---------- | ------ | ------------------------ |
| impression | object | Custom impression data . |

Sample Code:

```jsx
nativeRef.recordImpressionEvent({myKey: 'myValue', yourKey:{ cool: true}})
```

##### Complex Call Example

```jsx
import {
  HMSNative,
  NativeMediaTypes,
  ContentClassification,
  Gender,
  NonPersonalizedAd,
  TagForChild,
  UnderAge,
  ChoicesPosition,
  Direction,
  AudioFocusType,
  ScaleType
} from '@hmscore/react-native-hms-ads';

<HMSNative
  style={{height: 322}}
  displayForm={{
    mediaType:NativeMediaTypes.VIDEO,
    adId:'testy63txaom86',
  }}
  adParam={{
    adContentClassification:
      ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
    gender: Gender.UNKNOWN,
    nonPersonalizedAd: NonPersonalizedAd.ALLOW_ALL,
    tagForChildProtection:
      TagForChild.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED,
    tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED,
  }}
  nativeConfig={{
    choicesPosition: ChoicesPosition.BOTTOM_RIGHT,
    mediaDirection: Direction.ANY,
    videoConfiguration: {
      audioFocusType: AudioFocusType.NOT_GAIN_AUDIO_FOCUS_ALL,
      startMuted: true,
    },
  }}
  viewOptions={{
    showMediaContent: false,
    mediaImageScaleType: ScaleType.FIT_CENTER,
    adSourceTextStyle: {color: 'red'},
    adFlagTextStyle: {backgroundColor: 'red', fontSize: 10},
    titleTextStyle: {color: 'red'},
    descriptionTextStyle: {visibility: false},
    callToActionStyle: {color: 'black', fontSize: 12},
  }}
  onNativeAdLoaded={(_) => console.log("HMSNative onNativeAdLoaded")}
  onAdDisliked={(_) => console.log("HMSNative onAdDisliked")}
  onAdFailed={(e) => console.log("HMSNative onAdFailed", e.nativeEvent)}
  onAdClicked={(_) => console.log("HMSNative onAdClicked")}
  onAdImpression={(_) => console.log("HMSNative onAdImpression")}
  onVideoStart={(_) => console.log("HMSNative onVideoStart")}
  onVideoPlay={(_) => console.log("HMSNative onVideoPlay")}
  onVideoEnd={(_) => console.log("HMSNative onVideoEnd")}
  onVideoPause={(_) => console.log("HMSNative onVideoPause")}
  onVideoMute={(e) => console.log("HMSNative onVideoMute", e.nativeEvent)}
/>
```

---

### Modules

#### HMSAds
  
##### Functions

###### init()

Initializes the HUAWEI Ads SDK. The function returns a promise that resolves a string 'Hw Ads Initialized'.

| Return Type     | Description                                                                 |
| --------------- | --------------------------------------------------------------------------- |
| Promise<string> | Promise that resolves `"Hw Ads Initialized"` if the operation is successful |

Sample Code:

```jsx
import HMSAds from '@hmscore/react-native-hms-ads';

HMSAds.init()
  .then((res) => console.log('HMSAds.init, result:', res));
```

###### enableLogger()

This method enables HMSLogger capability which is used for sending usage analytics of Ads SDK's methods to improve the service quality

Return Type

| Type          | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import HMSAds from '@hmscore/react-native-hms-ads';
HMSAds.enableLogger()
  .then((res) => console.log('HMSAds.enableLogger, result:', res));
```

###### disableLogger()

This method disables HMSLogger capability which is used for sending usage analytics of Ads SDK's methods to improve the service quality

Return Type

| Type          | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import HMSAds from '@hmscore/react-native-hms-ads';
HMSAds.disableLogger()
  .then((res) => console.log('HMSAds.disableLogger, result:', res));
```

###### getSDKVersion()

Obtains the version number of the HUAWEI Ads SDK. The function returns a promise that resolves a string of the version number.

| Return Type     | Description                                                                                      |
| --------------- | ------------------------------------------------------------------------------------------------ |
| Promise<string> | Promise that resolves the version number of the HMS Core Ads SDK if the operation is successful. |

Sample Code:

```jsx
import HMSAds from '@hmscore/react-native-hms-ads';

HMSAds.getSDKVersion()
  .then((res) => console.log('HMSAds.getSDKVersionrsion, result:', res));
```

###### setRequestOptions()

Provides the global ad request configuration. The function returns a promise that resolves a [RequestOptions](#requestoptions) object.

| Parameter      | Type                              | Description         |
| -------------- | --------------------------------- | ------------------- |
| requestOptions | [RequestOptions](#requestoptions) | Ad request options. |

| Return Type                                | Description                                                                                                                                     |
| ------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| Promise<[RequestOptions](#requestoptions)> | Promise that resolves a [RequestOptions](#requestoptions) object which should be the same as the given argument if the operation is successful. |

Sample Code:

```jsx
import HMSAds, {
  ContentClassification,
  NonPersonalizedAd,
  TagForChild,
  UnderAge,
} from '@hmscore/react-native-hms-ads';

HMSAds.setRequestOptions({
  tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED,
  nonPersonalizedAd: NonPersonalizedAd.ALLOW_ALL,
  adContentClassification:
    ContentClassification.AD_CONTENT_CLASSIFICATION_A,
  tagForChildProtection:
    TagForChild.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED,
  appCountry: "TR",
  appLang: "tr",
})
  .then((res) => console.log('HMSAds.setRequestOptions, result:', res));
```

###### getRequestOptions()

Obtains the global request configuration. The function returns a promise that resolves a [RequestOptions](#requestoptions) object.

| Return Type                         | Description                                                                                      |
| ----------------------------------- | ------------------------------------------------------------------------------------------------ |
| <[RequestOptions](#requestoptions)> | Promise that resolves a [RequestOptions](#requestoptions) object if the operation is successful. |

Sample Code:

```jsx
import HMSAds from '@hmscore/react-native-hms-ads';

HMSAds.getRequestOptions()
  .then((res) => console.log('HMSAds.getRequestOptions, result:', res));
```

###### setConsent()

Provides ad consent configuration. The function returns a promise that resolves a [ConsentResult](#consentresult) object.

| Parameter     | Type                | Description               |
| ------------- | ------------------- | ------------------------- |
| consentResult | [Consent](#consent) | Ad consent configuration. |

| Return Type                              | Description                                                                                    |
| ---------------------------------------- | ---------------------------------------------------------------------------------------------- |
| Promise<[ConsentResult](#consentresult)> | Promise that resolves a [ConsentResult](#consentresult) object if the operation is successful. |

Sample Code:

```jsx
import HMSAds, {
  ConsentStatus,
  DebugNeedConsent,
  UnderAge,
} from '@hmscore/react-native-hms-ads';

HMSAds.setConsent({
  consentStatus: ConsentStatus.NON_PERSONALIZED,
  debugNeedConsent: DebugNeedConsent.DEBUG_NEED_CONSENT,
  underAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED,
  // testDeviceId: '********',
})
  .then((res) => console.log('HMSAds.setConsent, result:', res))
  .catch((err) => console.warn(err));
```

###### checkConsent()

Obtains ad consent configuration. The function returns a promise that resolves a [ConsentResult](#consentresult) object.

| Return Type                              | Description                                                                                    |
| ---------------------------------------- | ---------------------------------------------------------------------------------------------- |
| Promise<[ConsentResult](#consentresult)> | Promise that resolves a [ConsentResult](#consentresult) object if the operation is successful. |

Sample Code:

```jsx
import HMSAds from '@hmscore/react-native-hms-ads';

HMSAds.checkConsent()
  .then((res) => console.log('HMSAds.checkConsent, result:', res))
  .catch((err) => console.warn(err));
```

###### setConsentString()

Sets the user consent string that complies with [TCF 2.0](https://iabeurope.eu/tcf-2-0/)

| Parameter     | Type   | Description                                                                     |
| ------------- | ------ | ------------------------------------------------------------------------------- |
| consentString | string | User consent string that complies with [TCF 2.0](https://iabeurope.eu/tcf-2-0/) |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import HMSAds from '@hmscore/react-native-hms-ads';

HMSAds.setConsentString("")
  .then((res) => console.log('HMSAds.setConsentString, result:', res))
  .catch((err) => console.warn(err));
```

---

#### HMSOaid

##### Functions

###### getAdvertisingIdInfo()

Obtains the OAID and 'Limit ad tracking' setting. The string argument should be one of values of [CallMode](#callmode). The function returns a promise that resolves a [AdvertisingIdClientInfo](#advertisingidclientinfo) object.

| Parameter | Type   | Description                                                                                                          |
| --------- | ------ | -------------------------------------------------------------------------------------------------------------------- |
| callMode  | string | Option for functions that can use Huawei SDK or [Aidl](https://developer.android.com/guide/components/aidl) service. |

| Return Type                                                  | Description                                                                                                                                                            |
| ------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Promise<[AdvertisingIdClientInfo](#advertisingidclientinfo)> | Promise that resolves an [AdvertisingIdClientInfo](#advertisingidclientinfo) object that contains information about advertised clients if the operation is successful. |

Sample Code:

```jsx
import {HMSOaid, CallMode} from '@hmscore/react-native-hms-ads';

let callMode = CallMode.SDK;
HMSOaid.getAdvertisingIdInfo(callMode)
  .then((res) => console.log('HMSOaid.getAdvertisingIdInfo, result:', res))
  .catch((err) => console.warn(err));
```

###### verifyAdvertisingId()

Verifies the OAID and 'Limit ad tracking' setting. The function returns a promise that resolves a boolean showing the verification result.

| Parameter               | Type                                                | Description                           |
| ----------------------- | --------------------------------------------------- | ------------------------------------- |
| advertisingIdClientInfo | [AdvertisingIdClientInfo](#advertisingidclientinfo) | Information about advertised clients. |

| Return Type      | Description                                                                                     |
| ---------------- | ----------------------------------------------------------------------------------------------- |
| Promise<boolean> | Promise that resolves a boolean showing the verification result if the operation is successful. |

Sample Code:

```jsx
import {HMSOaid} from '@hmscore/react-native-hms-ads';

// should use information obtained from 'getAdvertisingIdInfo()' function
let advertisingInfo = {
  id: "01234567-89abc-defe-dcba-987654321012",
  isLimitAdTrackingEnabled: false
}
HMSOaid.verifyAdvertisingId(advertisingInfo)
  .then((res) => console.log('HMSOaid.verifyAdvertisingId, result:', res))
  .catch((err) => console.warn(err));
```

---

#### HMSReward

##### Functions

###### setAdParam()

Sets parameters of ad request.

| Parameter | Type                | Description            |
| --------- | ------------------- | ---------------------- |
| adParam   | [AdParam](#adparam) | Ad request parameters. |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSReward, ContentClassification, UnderAge} from '@hmscore/react-native-hms-ads';

HMSReward.setAdParam({
  adContentClassification: ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
  tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED
})
  .then((res) => console.log("HMSReward.setAdParam", res))
  .catch((err) => console.warn(err));
```

###### setAdId()

Sets ad slot id.

| Parameter | Type   | Description |
| --------- | ------ | ----------- |
| slotId    | string | Slot id.    |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.setAdId("testx9dtjwj8hp") // video ad
  .then((res) => console.log("HMSReward.setAdId", res))
  .catch((err) => console.warn(err));
```

###### setUserId()

Sets user id.

| Parameter | Type   | Description |
| --------- | ------ | ----------- |
| userId    | string | User id.    |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.setUserId("HMS User")
  .then((res) => console.log("HMSReward.setUserId", res))
  .catch((err) => console.warn(err));
```

###### setData()

Sets custom data in string.

| Parameter | Type   | Description            |
| --------- | ------ | ---------------------- |
| data      | string | Custom data in string. |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.setData("HMS Data")
  .then((res) => console.log("HMSReward.setData", res))
  .catch((err) => console.warn(err));
```

###### setVerifyConfig()

Sets server-side verification parameters.

| Parameter    | Type                          | Description                         |
| ------------ | ----------------------------- | ----------------------------------- |
| verifyConfig | [VerifyConfig](#verifyconfig) | Server-side verification parameter. |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.setVerifyConfig({userId: 'userxxxxx', data: 'dataxxxx'})
  .then((res) => console.log("HMSReward.setVerifyConfig", res))
  .catch((err) => console.warn(err));
```

###### pause()

Pauses the ad.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.pause()
  .then((res) => console.log("HMSReward.pause", res))
  .catch((err) => console.warn(err));
```

###### resume()

Resumes the ad.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.resume()
  .then((res) => console.log("HMSReward.resume", res))
  .catch((err) => console.warn(err));
```

###### destroy()

Destroys ad.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.destroy()
  .then((res) => console.log("HMSReward.destroy", res))
  .catch((err) => console.warn(err));
```

###### onHMSCore()

Sets to display ad on HMS Core app

| Parameter | Type    | Description              |
| --------- | ------- | ------------------------ |
| onHMSCore | boolean | Open ad on HMS Core app. |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.onHMSCore(true)
  .then((res) => console.log("HMSReward.onHMSCore", res))
  .catch((err) => console.warn(err));
```

###### loadAd()

Requests ad.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.loadAd()
  .then((res) => console.log("HMSReward.loadAd", res))
  .catch((err) => console.warn(err));
```

###### show()

Displays ad.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.show()
  .then((res) => console.log("HMSReward.show", res))
  .catch((err) => console.warn(err));
```

###### isLoaded()

Checks whether ad is successfully loaded. The function returns a promise that resolves  a boolean indicating whether the ad is loaded or not.

| Return Type      | Description                               |
| ---------------- | ----------------------------------------- |
| Promise<boolean> | Indicates whether the ad is loaded or not |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.isLoaded()
  .then((res) => console.log('HMSReward isLoaded, result:', res))
```

##### Events

| Event          | Description                                         |
| -------------- | --------------------------------------------------- |
| adFailedToLoad | Event emitted when ad fails to load.                |
| adFailedToShow | Event emitted when ad fails to be displayed.        |
| adClosed       | Event emitted when ad is closed.                    |
| adOpened       | Event emitted when ad is opened.                    |
| adLoaded       | Event emitted when ad loads.                        |
| adRewarded     | Event emitted when a [reward](#reward) is provided. |
| adLeftApp      | Event emitted when user leaves the app.             |
| adCompleted    | Event emitted ad is completed.                      |
| adStarted      | Event emitted ad is started.                        |

###### adFailedToLoadListenerAdd()

Adds listener for adFailedToLoad event. The listener function gets [Error](#error) as input.

| Parameter | Type                         | Description        |
| --------- | ---------------------------- | ------------------ |
| fn        | (res: [Error](#error) )=> {} | Listener function. |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adFailedToLoadListenerAdd((error) => {
  console.warn('HMSReward adFailedToLoad, error: ', error);
});
```

###### adFailedToLoadListenerRemove()

Removes listeners for adFailedToLoad event.

Sample Code:

```jsx
HMSReward.adFailedToLoadListenerRemove();
```

###### adFailedToShowListenerAdd()

Adds listener for adFailedToShow event. The listener function gets [Error](#error) as input.

| Parameter | Type                         | Description        |
| --------- | ---------------------------- | ------------------ |
| fn        | (res: [Error](#error) )=> {} | Listener function. |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adFailedToShowListenerAdd((error) => {
  console.warn('HMSReward adFailedToShow, error: ', error);
});
```

###### adFailedToShowListenerRemove()

Removes listeners for adFailedToShow event.

Sample Code:

```jsx
HMSReward.adFailedToShowListenerRemove();
```

###### adClosedListenerAdd()

Adds listener for adClosed event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adClosedListenerAdd(() => {
  console.log('HMSReward adClosed');
});
```

###### adClosedListenerRemove()

Removes listeners for adClosed event.

Sample Code:

```jsx
HMSReward.adClosedListenerRemove();
```

###### adOpenedListenerAdd()

Adds listener for adOpened event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adOpenedListenerAdd(() => {
  console.log('HMSReward adOpened');
});
```

###### adOpenedListenerRemove()

Removes listeners for adOpened event.

Sample Code:

```jsx
HMSReward.adOpenedListenerRemove();
```

###### adLoadedListenerAdd()

Adds listener for adLoaded event. The listener function gets [RewardAd](#rewardad) as input.

Adds listener for adClosed event.

| Parameter | Type                              | Description        |
| --------- | --------------------------------- | ------------------ |
| fn        | (res: [RewardAd](#rewardad))=> {} | Listener function. |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adLoadedListenerAdd((rewardAd) => {
  console.log('HMSReward adLoaded, Reward ad: ', rewardAd);
});
```

###### adLoadedListenerRemove()

Removes listeners for adLoaded event.

Sample Code:

```jsx
HMSReward.adLoadedListenerRemove();
```

###### adRewardedListenerAdd()

Adds listener for adRewarded event.

| Parameter | Type                          | Description        |
| --------- | ----------------------------- | ------------------ |
| fn        | (res: [Reward](#reward))=> {} | Listener function. |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adRewardedListenerAdd((reward) => {
  console.log('HMSReward adRewarded, reward: ', reward);
});
```

###### adRewardedListenerRemove()

Removes listeners for adRewarded event.

Sample Code:

```jsx
HMSReward.adRewardedListenerRemove();
```

###### adLeftAppListenerAdd()

Adds listener for adLeftApp event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adLeftAppListenerAdd(() => {
  console.log('HMSReward adLeftApp');
});
```

###### adLeftAppListenerRemove()

Removes listeners for adLeftApp event.

Sample Code:

```jsx
HMSReward.adLeftAppListenerRemove();
```

###### adCompletedListenerAdd()

Adds listener for adCompleted event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adCompletedListenerAdd(() => {
  console.log('HMSReward adCompleted';
});
```

###### adCompletedListenerRemove()

Removes listeners for adCompleted event.

Sample Code:

```jsx
HMSReward.adCompletedListenerRemove();
```

###### adStartedListenerAdd()

Adds listener for adStarted event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adStartedListenerAdd(() => {
  console.log('HMSReward adStarted');
});
```

###### adStartedListenerRemove()

Removes listeners for adStarted event.

Sample Code:

```jsx
HMSReward.adStartedListenerRemove();
```

###### allListenersRemove()

Remove all listeners for events of HMSReward.

Sample Code:

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.allListenersRemove();
```

---

#### HMSInterstitial

##### Functions

###### setAdId()

Sets ad slot id.

| Parameter | Type   | Description |
| --------- | ------ | ----------- |
| slotId    | string | Ad slot id. |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.setAdId("testb4znbuh3n2") // video ad
  .then((res) => console.log("HMSInterstitial.setAdId", res))
  .catch((err) => console.warn(err));
```

###### setAdParam()

Sets parameters of ad request.

| Parameter | Type                | Description            |
| --------- | ------------------- | ---------------------- |
| adParam   | [AdParam](#adparam) | Ad request parameters. |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSInterstitial, ContentClassification, UnderAge} from '@hmscore/react-native-hms-ads';

HMSInterstitial.setAdParam({
  adContentClassification: ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
  tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED
})
  .then((res) => console.log("HMSInterstitial.setAdParam", res))
  .catch((err) => console.warn(err));
```

###### onHMSCore()

Sets to display ad on HMS Core app

| Parameter | Type    | Description              |
| --------- | ------- | ------------------------ |
| onHMSCore | boolean | Open ad on HMS Core app. |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.onHMSCore(true)
  .then((res) => console.log("HMSInterstitial.onHMSCore", res))
  .catch((err) => console.warn(err));
```

###### loadAd()

Initiates a request to load an ad.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.loadAd()
  .then((res) => console.log("HMSInterstitial.loadAd", res))
  .catch((err) => console.warn(err));
```

###### show()

Displays an interstitial ad.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<null> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.show()
  .then((res) => console.log("HMSInterstitial.show", res))
  .catch((err) => console.warn(err));
```

###### isLoaded()

Checks whether ad loading is complete.

| Return Type      | Description                            |
| ---------------- | -------------------------------------- |
| Promise<boolean> | Indicates whether ad is loaded or not. |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.isLoaded()
  .then((res) => console.log("HMSInterstitial.isLoaded", res))
  .catch((err) => console.warn(err));
```

###### isLoading()

Checks whether ad is loading.

| Return Type      | Description                            |
| ---------------- | -------------------------------------- |
| Promise<boolean> | Indicates whether ad is loaded or not. |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.isLoading()
  .then((res) => console.log("HMSInterstitial.isLoading", res))
  .catch((err) => console.warn(err));
```

##### Events

| Event        | Description                                   |
| ------------ | --------------------------------------------- |
| adFailed     | Event emitted when ad fails to load.          |
| adClosed     | Event emitted when ad is closed.              |
| adLeave      | Event emitted when the user leaves the app.   |
| adOpened     | Event emitted when ad is displayed.           |
| adLoaded     | Event emitted when ad loads.                  |
| adClicked    | Event emitted when ad is clicked.             |
| adImpression | Event emitted when ad impression is detected. |
| adCompleted  | Event emitted when ad is completed.           |
| adStarted    | Event emitted when ad is started.             |

###### adFailedListenerAdd()

Adds listener for adFailed event. The listener function gets [Error](#error) as input.

| Parameter | Type                         | Description        |
| --------- | ---------------------------- | ------------------ |
| fn        | (res: [Error](#error) )=> {} | Listener function. |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adFailedListenerAdd((error) => {
  console.warn('HMSInterstitial adFailed, error: ', error);
});
```

###### adFailedListenerRemove()

Removes listeners for adFailed event.

Sample Code:

```jsx
HMSInterstitial.adFailedListenerRemove();
```

###### adClosedListenerAdd()

Adds listener for adClosed event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adClosedListenerAdd(() => {
  console.log('HMSInterstitial adClosed');
});
```

###### adClosedListenerRemove()

Removes listeners for adClosed event.

Sample Code:

```jsx
HMSInterstitial.adClosedListenerRemove();
```

###### adLeaveListenerAdd()

Adds listener for adLeave event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adLeaveListenerAdd(() => {
  console.warn('HMSInterstitial adLeave');
});
```

###### adLeaveListenerRemove()

Removes listeners for adLeave event.

Sample Code:

```jsx
HMSInterstitial.adLeaveListenerRemove();
```

###### adOpenedListenerAdd()

Adds listener for adOpened event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adOpenedListenerAdd(() => {
  console.log('HMSInterstitial adOpened');
});
```

###### adOpenedListenerRemove()

Removes listeners for adOpened event.

Sample Code:

```jsx
HMSInterstitial.adOpenedListenerRemove();
```

###### adLoadedListenerAdd()

Adds listener for adLoaded event. The listener function gets [InterstitialAd](#interstitialad) as input.

| Parameter | Type                                          | Description        |
| --------- | --------------------------------------------- | ------------------ |
| fn        | (res: [InterstitialAd](#interstitialad))=> {} | Listener function. |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adLoadedListenerAdd((interstitialAd) => {
  console.log('HMSInterstitial adLoaded, Interstitial Ad: ', interstitialAd);
});
```

###### adLoadedListenerRemove()

Removes listeners for adLoaded event.

Sample Code:

```jsx
HMSInterstitial.adLoadedListenerRemove();
```

###### adClickedListenerAdd()

Adds listener for adClicked event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adClickedListenerAdd(() => {
  console.log('HMSInterstitial adClicked');
});
```

###### adClickedListenerRemove()

Removes listeners for adClicked event.

Sample Code:

```jsx
HMSInterstitial.adClickedListenerRemove();
```

###### adImpressionListenerAdd()

Adds listener for adImpression event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adImpressionListenerAdd(() => {
  console.log('HMSInterstitial adImpression');
});
```

###### adImpressionListenerRemove()

Removes listeners for adImpression event.

Sample Code:

```jsx
HMSInterstitial.adImpressionListenerRemove();
```

###### adCompletedListenerAdd()

Adds listener for adCompleted event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adCompletedListenerAdd(() => {
  console.log('HMSInterstitial adCompleted');
});
```

###### adCompletedListenerRemove()

Removes listeners for adCompleted event.

Sample Code:

```jsx
HMSInterstitial.adCompletedListenerRemove();
```

###### adStartedListenerAdd()

Adds listener for adStarted event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adStartedListenerAdd(() => {
  console.log('HMSInterstitial adStarted');
});
```

###### adStartedListenerRemove()

Removes listeners for adStarted event.

Sample Code:

```jsx
HMSInterstitial.adStartedListenerRemove();
```

###### allListenersRemove()

Remove all listeners for events of HMSInterstitial.

Sample Code:

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.allListenersRemove();
```

---

#### HMSSplash

##### Functions

###### setAdId()

Sets ad slot id.

| Parameter | Type   | Description |
| --------- | ------ | ----------- |
| slotId    | string | Slot id.    |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.setAdId("testd7c5cewoj6") // video ad
  .then((res) => console.log("HMSSplash.setAdId", res))
  .catch((err) => console.warn(err));
```

###### setLogoText()

Sets logo text.

| Parameter | Type   | Description |
| --------- | ------ | ----------- |
| logoText  | string | Logo text.  |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.setLogoText("HMS Sample")
  .then((res) => console.log("HMSSplash.setLogoText", res))
  .catch((err) => console.warn(err));
```

###### setCopyrightText()

Sets copyright text.

| Parameter     | Type   | Description     |
| ------------- | ------ | --------------- |
| copyRightText | string | Copyright text. |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.setCopyrightText("Copyright HMS")
  .then((res) => console.log("HMSSplash.setCopyrightText", res))
  .catch((err) => console.warn(err));
```

###### setOrientation()

Sets [screen orientation](https://developer.android.com/reference/android/content/pm/ActivityInfo#screenOrientation).

| Parameter         | Type   | Description                                                                                                           |
| ----------------- | ------ | --------------------------------------------------------------------------------------------------------------------- |
| screenOrientation | number | Sets [screen orientation](https://developer.android.com/reference/android/content/pm/ActivityInfo#screenOrientation). |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.setOrientation(1)
  .then((res) => console.log("HMSSplash.setOrientation", res))
  .catch((err) => console.warn(err));
```

###### setSloganResource()

Sets default app launch image in portrait mode, which is displayed before a splash ad is displayed.

| Parameter      | Type   | Description                                             |
| -------------- | ------ | ------------------------------------------------------- |
| sloganResource | string | text that is displayed before a splash ad is displayed. |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';
// file_name_without_extension.png added to res/drawable or res/mipmap
HMSSplash.setSloganResource("file_name_without_extension")
  .then((res) => console.log("HMSSplash.setSloganResource", res))
  .catch((err) => console.warn(err));
```

###### setWideSloganResource()

Sets default app launch image in landscape mode, which is displayed before a splash ad is displayed.

| Parameter          | Type   | Description                                             |
| ------------------ | ------ | ------------------------------------------------------- |
| wideSloganResource | string | Text that is displayed before a splash ad is displayed. |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';
// file_name_without_extension.png added to res/drawable or res/mipmap
HMSSplash.setWideSloganResource("file_name_without_extension")
  .then((res) => console.log("HMSSplash.setWideSloganResource", res))
  .catch((err) => console.warn(err));
```

###### setLogoResource()

Sets app logo.

| Parameter | Type   | Description                          |
| --------- | ------ | ------------------------------------ |
| appLogo   | string | App logo file path without extension |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';
// file_name_without_extension.png added to res/drawable or res/mipmap
HMSSplash.setLogoResource("file_name_without_extension")
  .then((res) => console.log("HMSSplash.setLogoResource", res))
  .catch((err) => console.warn(err));
```

###### setMediaNameResource()

Sets app text resource.

| Parameter    | Type   | Description       |
| ------------ | ------ | ----------------- |
| textResource | string | app text resource |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';
// <string name="media_name">HUAWEI Ads</string> line inserted to strings.xml
HMSSplash.setMediaNameResource("media_name")
  .then((res) => console.log("HMSSplash.setMediaNameResource", res))
  .catch((err) => console.warn(err));
```

###### setAudioFocusType()

Sets the audio focus preemption policy for a video splash ad.

| Parameter      | Type   | Description                                                                    |
| -------------- | ------ | ------------------------------------------------------------------------------ |
| audioFocusType | number | Number value that determines the the audio focus preemption policy for a video |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash, AudioFocusType} from '@hmscore/react-native-hms-ads';

HMSSplash.setAudioFocusType(AudioFocusType.GAIN_AUDIO_FOCUS_ALL)
  .then((res) => console.log("HMSSplash.setAudioFocusType", res))
  .catch((err) => console.warn(err));
```

###### setAdParam()

Sets parameters of ad request.

| Parameter | Type                | Description            |
| --------- | ------------------- | ---------------------- |
| adParam   | [AdParam](#adparam) | Ad request parameters. |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash, ContentClassification, UnderAge} from '@hmscore/react-native-hms-ads';

HMSSplash.setAdParam({
  adContentClassification: ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
  tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED
})
  .then((res) => console.log("HMSSplash.setAdParam", res))
  .catch((err) => console.warn(err));
```

###### pause()

Pauses ad.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.pause()
  .then((res) => console.log("HMSSplash.pause", res))
  .catch((err) => console.warn(err));
```

###### resume()

Resumes ad.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.resume()
  .then((res) => console.log("HMSSplash.resume", res))
  .catch((err) => console.warn(err));
```

###### destroy()

Destroys ad.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.destroy()
  .then((res) => console.log("HMSSplash.destroy", res))
  .catch((err) => console.warn(err));
```

###### show()

Shows ad.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.show()
  .then((res) => console.log("HMSSplash.show", res))
  .catch((err) => console.warn(err));
```

###### isLoaded()

Checks whether a splash ad has been loaded.

| Return Type      | Description                            |
| ---------------- | -------------------------------------- |
| Promise<boolean> | Indicates whether ad is loaded or not. |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.isLoaded()
  .then((res) => console.log("HMSSplash.isLoaded", res))
  .catch((err) => console.warn(err));
```

###### isLoading()

Checks whether a splash ad is being loaded.

| Return Type      | Description                             |
| ---------------- | --------------------------------------- |
| Promise<boolean> | Indicates whether ad is loading or not. |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.isLoading()
  .then((res) => console.log("HMSSplash.isLoading", res))
  .catch((err) => console.warn(err));
```

##### Events

| Event          | Description                          |
| -------------- | ------------------------------------ |
| adFailedToLoad | Event emitted when ad fails to load. |
| adDismissed    | Event emitted when ad is dismissed.  |
| adShowed       | Event emitted when ad is shown.      |
| adLoaded       | Event emitted when ad loads.         |
| adClick        | Event emitted when ad is clicked.    |

###### adFailedToLoadListenerAdd()

Adds listener for adFailedToLoad event. The listener function gets [Error](#error) as input.

| Parameter | Type                         | Description        |
| --------- | ---------------------------- | ------------------ |
| fn        | (res: [Error](#error) )=> {} | Listener function. |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.adFailedToLoadListenerAdd((error) => {
  console.warn('HMSSplash adFailedToLoad, error: ', error);
});
```

###### adFailedToLoadListenerRemove()

Removes listeners for adFailedToLoad event.

Sample Code:

```jsx
HMSSplash.adFailedToLoadListenerRemove();
```

###### adDismissedListenerAdd()

Adds listener for adDismissed event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.adDismissedListenerAdd(() => {
  console.log('HMSSplash adDismissed');
});
```

###### adDismissedListenerRemove()

Removes listeners for adDismissed event.

Sample Code:

```jsx
HMSSplash.adDismissedListenerRemove();
```

###### adShowedListenerAdd()

Adds listener for adShowed event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.adShowedListenerAdd(() => {
  console.log('HMSSplash adShowed');
});
```

###### adShowedListenerRemove()

Removes listeners for adShowed event.

Sample Code:

```jsx
HMSSplash.adShowedListenerRemove();
```

###### adLoadedListenerAdd()

Adds listener for adLoaded event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.adLoadedListenerAdd(() => {
  console.log('HMSSplash adLoaded');
});
```

###### adLoadedListenerRemove()

Removes listeners for adLoaded event.

Sample Code:

```jsx
HMSSplash.adLoadedListenerRemove();
```

###### adClickListenerAdd()

Adds listener for adClick event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.adClickListenerAdd(() => {
  console.log('HMSSplash adClick');
});
```

###### adClickListenerRemove()

Removes listeners for adClick event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
HMSSplash.adClickListenerRemove();
```

###### allListenersRemove()

Remove all listeners for events of HMSSplash.

Sample Code:

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.allListenersRemove();
```

---

#### HMSInstallReferrer

##### Functions

###### startConnection()

Starts to connect to the install referrer service. The first string argument should be one of values of [CallMode](#callmode). And the boolean argument indicates test mode. The last string argument is the name of the package that the service receives information about. The function returns a promise that resolves a boolean indicating whether the service is successfully connected.

| Parameter   | Type    | Description                                                                                                          |
| ----------- | ------- | -------------------------------------------------------------------------------------------------------------------- |
| callMode    | string  | Option for functions that can use Huawei SDK or [Aidl](https://developer.android.com/guide/components/aidl) service. |
| testMode    | boolean | Indicates test mode.                                                                                                 |
| packageName | string  | Name of the package that the service receives information about.                                                     |

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSInstallReferrer, CallMode} from '@hmscore/react-native-hms-ads';

let callMode = CallMode.SDK;
let isTest = 'true';
let pkgName = 'com.huawei.hms.rn.ads.demo'; // your app package name
HMSInstallReferrer.startConnection(callMode, isTest, pkgName)
  .then((res) => console.log('HMSInstallReferrer.startConnection', res))
  .catch((err) => console.warn('HMSInstallReferrer.startConnection, error:', err));
```

###### endConnection()

Ends the service connection and releases all occupied resources.

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

Sample Code:

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.endConnection()
  .then((res) => console.log('HMSInstallReferrer.endConnection', res))
  .catch((err) => console.warn('HMSInstallReferrer.endConnection, error:', err));
```

###### getReferrerDetails()

Obtains install referrer information.

| Return Type                                  | Description                                 |
| -------------------------------------------- | ------------------------------------------- |
| Promise<[ReferrerDetails](#referrerdetails)> | Describes the install referrer information. |

Sample Code:

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.getReferrerDetails()
  .then((res) => console.log('HMSInstallReferrer.getReferrerDetails, result:', res))
  .catch((err) => console.warn('HMSInstallReferrer.getReferrerDetails, error:', err));
```

###### isReady()

Indicates whether the service connection is ready.

| Return Type      | Description                                        |
| ---------------- | -------------------------------------------------- |
| Promise<boolean> | Indicates whether the service connection is ready. |

Sample Code:

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.isReady()
  .then((res) => console.log('HMSInstallReferrer.isReady, result:', res));
```

##### Events

| Event               | Description                                          |
| ------------------- | ---------------------------------------------------- |
| serviceConnected    | Event emitted when service connection is complete.   |
| serviceDisconnected | Event emitted when the service is crashed or killed. |

###### serviceConnectedListenerAdd()

Adds listener for serviceConnected event. The listener function gets [InstallReferrerResponse](#installreferrerresponse) as input.

| Parameter | Type                                                            | Description        |
| --------- | --------------------------------------------------------------- | ------------------ |
| fn        | (res: [InstallReferrerResponse](#installreferrerresponse))=> {} | Listener function. |

Sample Code:

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.serviceConnectedListenerAdd((response) => {
  console.log('HMSInstallReferrer serviceConnected, response:', response);
});
```

###### serviceConnectedListenerRemove()

Removes listeners for serviceConnected event.

Sample Code:

```jsx
HMSInstallReferrer.serviceConnectedListenerRemove();
```

###### serviceDisconnectedListenerAdd()

Adds listener for serviceDisconnected event.

| Parameter | Type    | Description        |
| --------- | ------- | ------------------ |
| fn        | ()=> {} | Listener function. |

Sample Code:

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.serviceDisconnectedListenerAdd(() => {
  console.log('HMSInstallReferrer serviceDisconnected');
});
```

###### serviceDisconnectedListenerRemove()

Removes listeners for serviceDisconnected event.

Sample Code:

```jsx
HMSInstallReferrer.serviceDisconnectedListenerRemove();
```

###### allListenersRemove()

Remove all listeners for events of HMSInstallReferrer.

Sample Code:

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.allListenersRemove();
```

---

### Constants

#### ConsentStatus

| Key              | Value | Definition                       |
| ---------------- | ----- | -------------------------------- |
| PERSONALIZED     | 0     | Personalized consent option.     |
| NON_PERSONALIZED | 1     | Non-personalized consent option. |
| UNKNOWN          | 2     | Unknown consent option.          |

---

#### DebugNeedConsent

| Key                    | Value | Definition                       |
| ---------------------- | ----- | -------------------------------- |
| DEBUG_DISABLED         | 0     | Disabled debug option.           |
| DEBUG_NEED_CONSENT     | 1     | Consent-needed debug option.     |
| DEBUG_NOT_NEED_CONSENT | 2     | Consent-not-needed debug option. |

---

#### AudioFocusType

Whether to obtain the audio focus during video playback.

| Key                            | Value | Definition                                                                                  |
| ------------------------------ | ----- | ------------------------------------------------------------------------------------------- |
| GAIN_AUDIO_FOCUS_ALL           | 0     | Obtain the audio focus when a video is played, no matter whether the video is muted.        |
| NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE | 1     | Obtain the audio focus only when a video is played without being muted.                     |
| NOT_GAIN_AUDIO_FOCUS_ALL       | 2     | Do not obtain the audio focus when a video is played, no matter whether the video is muted. |

---

#### ContentClassification

Ad content rating.

| Key                              | Value | Definition                                          |
| -------------------------------- | ----- | --------------------------------------------------- |
| AD_CONTENT_CLASSIFICATION_W      | 'W'   | Content suitable for toddlers and older audiences.  |
| AD_CONTENT_CLASSIFICATION_PI     | 'PI'  | Content suitable for kids and older audiences.      |
| AD_CONTENT_CLASSIFICATION_J      | 'J'   | Content suitable for teenagers and older audiences. |
| AD_CONTENT_CLASSIFICATION_A      | 'A'   | Content suitable only for adults.                   |
| AD_CONTENT_CLASSIFICATION_UNKOWN | ''    | Unknown rating.                                     |

---

#### Gender

Gender.

| Key     | Value | Definition      |
| ------- | ----- | --------------- |
| UNKNOWN | 0     | Unknown gender. |
| MALE    | 1     | Male.           |
| FEMALE  | 2     | Female.         |

#### NonPersonalizedAd

Whether to request only non-personalized ads.

| Key                    | Value | Definition                                          |
| ---------------------- | ----- | --------------------------------------------------- |
| ALLOW_ALL              | 0     | Request both personalized and non-personalized ads. |
| ALLOW_NON_PERSONALIZED | 1     | Request only non-personalized ads.                  |

---

#### TagForChild

Child-directed setting.

| Key                                  | Value | Definition                                                                                    |
| ------------------------------------ | ----- | --------------------------------------------------------------------------------------------- |
| TAG_FOR_CHILD_PROTECTION_FALSE       | 0     | Do not process ad requests according to the Children’s Online Privacy Protection Act (COPPA). |
| TAG_FOR_CHILD_PROTECTION_TRUE        | 1     | Process ad requests according to the COPPA.                                                   |
| TAG_FOR_CHILD_PROTECTION_UNSPECIFIED | -1    | Whether to process ad requests according to the COPPA is not specified.                       |

---

#### UnderAge

Setting directed to users under the age of consent.

| Key                 | Value | Definition                                                                                     |
| ------------------- | ----- | ---------------------------------------------------------------------------------------------- |
| PROMISE_FALSE       | 0     | Do not process ad requests as directed to users under the age of consent.                      |
| PROMISE_TRUE        | 1     | Process ad requests as directed to users under the age of consent.                             |
| PROMISE_UNSPECIFIED | -1    | Whether to process ad requests as directed to users under the age of consent is not specified. |

---

#### NativeAdAssetNames

Constant IDs of all native ad components.

| Key               | Value | Definition                                                  |
| ----------------- | ----- | ----------------------------------------------------------- |
| TITLE             | 1     | Title material ID.                                          |
| CALL_TO_ACTION    | 2     | Material ID of the action text to be displayed on a button. |
| ICON              | 3     | Icon material ID.                                           |
| DESC              | 4     | Description material ID.                                    |
| AD_SOURCE         | 5     | Advertiser material ID.                                     |
| MARKET            | 6     | Market material ID.                                         |
| PRICE             | 7     | Price material ID.                                          |
| IMAGE             | 8     | Image material ID.                                          |
| RATING            | 9     | Rating material ID.                                         |
| MEDIA_VIDEO       | 10    | Multimedia view material ID.                                |
| CHOICES_CONTAINER | 11    | Ad choice material ID.                                      |

---

#### ChoicesPosition

Choice icon position constants.

| Key          | Value | Definition    |
| ------------ | ----- | ------------- |
| TOP_LEFT     | 0     | Top left.     |
| TOP_RIGHT    | 1     | Top right.    |
| BOTTOM_RIGHT | 2     | Bottom right. |
| BOTTOM_LEFT  | 3     | Bottom left.  |
| INVISIBLE    | 4     | Invisible.    |

---

#### Direction

Orientation constant.

| Key       | Value | Definition       |
| --------- | ----- | ---------------- |
| ANY       | 0     | Any orientation. |
| PORTRAIT  | 1     | Portrait.        |
| LANDSCAPE | 2     | Landscape.       |

---

#### ScaleType

[Options](https://developer.android.com/reference/android/widget/ImageView.ScaleType) for scaling the bounds of an image.

| Key           | Value           |
| ------------- | --------------- |
| MATRIX        | 'MATRIX'        |
| FIT_XY        | 'FIT_XY'        |
| FIT_START     | 'FIT_START'     |
| FIT_CENTER    | 'FIT_CENTER'    |
| FIT_END       | 'FIT_END'       |
| CENTER        | 'CENTER'        |
| CENTER_CROP   | 'CENTER_CROP'   |
| CENTER_INSIDE | 'CENTER_INSIDE' |

---

#### BannerAdSizes

| Key                 | Value              | Definition                                                                                        |
| ------------------- | ------------------ | ------------------------------------------------------------------------------------------------- |
| B_160_600           | '160_600'          | 160 x 600 dp banner ad size.                                                                      |
| B_300_250           | '300_250'          | 300 x 250 dp banner ad size.                                                                      |
| B_320_50            | '320_50'           | 320 x 50  dp banner ad size.                                                                      |
| B_320_100           | '320_100'          | 320 x 100 dp banner ad size.                                                                      |
| B_360_57            | '360_57'           | 360 x 57 dp banner ad size.                                                                       |
| B_360_144           | '360_144'          | 360 x 144 dp banner ad size.                                                                      |
| B_468_60            | '468_60'           | 468 x 60 dp banner ad size.                                                                       |
| B_728_90            | '728_90'           | 728 x 90 dp banner ad size.                                                                       |
| B_CURRENT_DIRECTION | 'currentDirection' | Banner ad size based on a based on the current device orientation and a custom width.             |
| B_PORTRAIT          | 'portrait'         | Banner ad size based on a custom width in portrait  orientation.                                  |
| B_SMART             | 'smart'            | Dynamic banner ad size. The screen width and an adaptive height are used.                         |
| B_DYNAMIC           | 'dynamic'          | Dynamic banner ad size. The width of the parent layout and the height of the ad content are used. |
| B_LANDSCAPE         | 'landscape'        | Banner ad size based on a custom width in landscape orientation.                                  |
| B_INVALID           | 'invalid'          | Invalid size. No ad can be requested using this size.                                             |

---

#### NativeMediaTypes

Native ad media types.

| Key         | Value         | Definition           |
| ----------- | ------------- | -------------------- |
| VIDEO       | 'video'       | Ad with video.       |
| IMAGE_SMALL | 'image_small' | Ad with small image. |
| IMAGE_LARGE | 'image_large' | Ad with large image. |

---

#### BannerMediaTypes

Banner ad media types.

| Key   | Value   | Definition     |
| ----- | ------- | -------------- |
| IMAGE | 'image' | Ad with image. |

---

#### InterstitialMediaTypes

Interstitial ad media types.

| Key   | Value   | Definition     |
| ----- | ------- | -------------- |
| VIDEO | 'video' | Ad with video. |
| IMAGE | 'image' | Ad with image. |

---

#### RewardMediaTypes

Reward ad media types.

| Key   | Value   | Definition     |
| ----- | ------- | -------------- |
| VIDEO | 'video' | Ad with video. |

---

#### SplashMediaTypes

Splash ad media types.

| Key   | Value   | Definition     |
| ----- | ------- | -------------- |
| VIDEO | 'video` | Ad with video. |
| IMAGE | 'image` | Ad with image. |

---

#### CallMode

Option for functions that can use Huawei SDK or [Aidl](https://developer.android.com/guide/components/aidl) service.

| Key  | Value  | Definition                                                                    |
| ---- | ------ | ----------------------------------------------------------------------------- |
| SDK  | 'sdk'  | Use Huawei Ads SDK .                                                          |
| AIDL | 'aidl' | Use aidl service. (This option will not be used anymore, please prefer `SDK`) |

---

### DataTypes

#### Reward

Information about the reward item in a rewarded ad.

| Parameter | Type   | Definition                  |
| --------- | ------ | --------------------------- |
| name      | string | The name of a reward item.  |
| amount    | number | The number of reward items. |

---

#### RewardAd

Rewarded ad.

| Parameter | Type              | Definition                                          |
| --------- | ----------------- | --------------------------------------------------- |
| userId    | string            | User id.                                            |
| data      | string            | Custom data.                                        |
| reward    | [Reward](#reward) | Reward item.                                        |
| isLoaded  | boolean           | Shows whether a rewarded ad is successfully loaded. |

---

#### InstreamAd

Instream ad.

| Parameter    | Type    | Definition                               |
| ------------ | ------- | ---------------------------------------- |
| isClicked    | boolean | Indicates whether ad has been clicked.   |
| isExpired    | boolean | Indicates whether ad has expired.        |
| isImageAd    | boolean | Indicates whether ad is an image ad.     |
| isShown      | boolean | Indicates whether ad has been displayed. |
| isVideoAd    | boolean | Indicates whether ad is a video ad.      |
| duration     | number  | Duration of a roll ad, in milliseconds.  |
| whyThisAd    | string  | Redirection link to `Why this ad`.       |
| callToAction | string  | Text to be displayed on a button.        |
| adSign       | string  | Indicates whether a task is an ad task.  |
| adSource     | string  | Ad source.                               |

---

#### InterstitialAd

Interstitial ad.

| Parameter | Type    | Definition                            |
| --------- | ------- | ------------------------------------- |
| adId      | string  | The ad slot id.                       |
| isLoaded  | boolean | Shows whether ad loading is complete. |
| isLoading | boolean | Shows whether ads are being loaded.   |

---

#### DislikeAdReason

Obtains the reason why a user dislikes an ad.

| Parameter   | Type   | Definition                            |
| ----------- | ------ | ------------------------------------- |
| description | string | The reason why a user dislikes an ad. |

---

#### Muted

| Parameter | Type    | Definition                            |
| --------- | ------- | ------------------------------------- |
| isMuted   | boolean | Shows whether a video is muted ratio. |

---

#### PlayTime

| Parameter | Type   | Definition                        |
| --------- | ------ | --------------------------------- |
| playTime  | number | Played duration, in milliseconds. |

---

#### WithPercentage

| Parameter  | Type   | Definition                        |
| ---------- | ------ | --------------------------------- |
| percentage | number | Playback progress, in percentage. |

---

#### WithExtra

| Parameter | Type   | Definition              |
| --------- | ------ | ----------------------- |
| extra     | number | Additional information. |

---

#### WithError

| Parameter | Type            | Definition         |
| --------- | --------------- | ------------------ |
| error     | [Error](#error) | Error information. |

---

#### VideoOperator

Video controller, which implements video control such as playing, pausing, and muting a video

| Parameter                  | Type    | Definition                                                        |
| -------------------------- | ------- | ----------------------------------------------------------------- |
| aspectRatio                | number  | The video aspect ratio.                                           |
| hasVideo                   | boolean | Shows whether ad content contains a video.                        |
| isCustomizeOperateEnabled  | boolean | Shows whether a custom video control is used for a video ad.      |
| isClickToFullScreenEnabled | boolean | Shows whether click to full screen option enabled for a video ad. |
| isMuted                    | boolean | Shows whether a video is muted.                                   |

---

#### NativeAd

Native ad.

| Parameter                    | Type                                  | Definition                                                                  |
| ---------------------------- | ------------------------------------- | --------------------------------------------------------------------------- |
| adSign                       | string                                | Indicates whether a task is an ad task.                                     |
| adSource                     | string                                | Ad source.                                                                  |
| description                  | string                                | Ad description.                                                             |
| callToAction                 | string                                | The text to be displayed on a button, for example, View Details or Install. |
| title                        | string                                | Ad title.                                                                   |
| dislikeAdReasons             | [DislikeAdReason](#dislikeadreason)[] | The choices of not displaying the current ad.                               |
| whyThisAd                    | string                                | Redirection link to Why this ad.                                            |
| uniqueId                     | string                                | Unique ID of an ad.                                                         |
| creativeType                 | string                                | Ad creative type.                                                           |
| videoOperator                | [VideoOperator](#videooperator)       | Video operator used for the ad.                                             |
| isCustomClickAllowed         | boolean                               | Shows whether custom tap gestures are enabled.                              |
| isCustomDislikeThisAdEnabled | boolean                               | Shows whether custom ad closing is enabled.                                 |

---

#### AdProvider

Ad provider.

| Parameter        | Type   | Definition                                        |
| ---------------- | ------ | ------------------------------------------------- |
| id               | string | Id of ad provider.                                |
| name             | string | Name of ad provider.                              |
| privacyPolicyUrl | string | The url for privacy policy.                       |
| serviceArea      | string | The service area for ad (ex: 'Global' or 'Asia'). |

---

#### AdSize

Ad size.

| Parameter | Type   | Definition        |
| --------- | ------ | ----------------- |
| height    | number | Ad height, in dp. |
| width     | number | Ad width, in dp.  |

---

#### BannerAdSize

Banner ad size.

| Parameter        | Type    | Definition                                 |
| ---------------- | ------- | ------------------------------------------ |
| height           | number  | Ad height, in dp.                          |
| width            | number  | Ad width, in dp.                           |
| heightPx         | number  | Ad height, in pixels.                      |
| widthPx          | number  | Ad width, in pixels.                       |
| isAutoHeightSize | boolean | Shows whether an adaptive height is used.  |
| isDynamicSize    | boolean | Shows whether a dynamic size is used.      |
| isFullWidthSize  | boolean | Shows whether a full-screen width is used. |

---

#### VideoConfiguration

Video configuration used to control video playback.

| Parameter                    | Type    | Definition                                                                                                                           |
| ---------------------------- | ------- | ------------------------------------------------------------------------------------------------------------------------------------ |
| audioFocusType               | number  | The video playback scenario where the audio focus needs to be obtained. Check [AudioFocusType](#audiofocustype) for possible values. |
| isCustomizeOperateRequested  | boolean | The setting for using custom video control.                                                                                          |
| isClickToFullScreenRequested | boolean | Setting indicating whether a video ad can be displayed in full-screen mode upon a click.                                             |
| isStartMuted                 | boolean | The setting for muting video when it starts.                                                                                         |

---

#### NativeAdConfiguration

Native Ad configuration.

| Parameter             | Type                                      | Definition                                                                                    |
| --------------------- | ----------------------------------------- | --------------------------------------------------------------------------------------------- |
| adSize                | [AdSize](#adsize)                         | Ad size.                                                                                      |
| choicesPosition       | number                                    | Position of an ad choice icon. Check [ChoicesPosition](#choicesposition) for possible values. |
| mediaDirection        | number                                    | Direction of an ad image.  Check [Direction](#direction) for possible values.                 |
| mediaAspect           | number                                    | Aspect ratio of an ad image.                                                                  |
| videoConfiguration    | [VideoConfiguration](#videoconfiguration) | Video Configuration.                                                                          |
| isRequestMultiImages  | boolean                                   | The setting for requesting multiple ad images.                                                |
| isReturnUrlsForImages | boolean                                   | The setting for enabling the SDK to download native ad images.                                |

---

#### NativeAdLoader

Native Ad loader.

| Parameter | Type    | Definition                          |
| --------- | ------- | ----------------------------------- |
| isLoading | boolean | Shows whether ads are being loaded. |

---

#### AdvertisingIdClientInfo

Information about advertised clients.

| Parameter                | Type    | Definition                   |
| ------------------------ | ------- | ---------------------------- |
| id                       | string  | The OAID.                    |
| isLimitAdTrackingEnabled | boolean | 'Limit ad tracking' setting. |

---

#### ReferrerDetails

Describes the install referrer information.

| Parameter                         | Type   | Definition                                       |
| --------------------------------- | ------ | ------------------------------------------------ |
| installReferrer                   | string | Install referrer information.                    |
| installBeginTimestampMillisecond  | number | The app installation timestamp, in milliseconds. |
| installBeginTimestampSeconds      | number | The app installation timestamp, in seconds.      |
| referrerClickTimestampMillisecond | number | The ad click timestamp, in milliseconds.         |
| referrerClickTimestampSeconds     | number | The ad click timestamp, in seconds.              |

---

#### RequestOptions

Ad request options.

| Parameter               | Type   | Definition                                                                                                          |
| ----------------------- | ------ | ------------------------------------------------------------------------------------------------------------------- |
| adContentClassification | string | Ad content rating. Check [ContentClassification](#contentclassification) for possible values.                       |
| appCountry              | string | The country for the app.                                                                                            |
| appLang                 | string | The language for the app.                                                                                           |
| nonPersonalizedAd       | number | The setting for requesting non-personalized ads. Check [NonPersonalizedAd](#nonpersonalizedad) for possible values. |
| tagForChildProtection   | number | The child-directed setting. Check [TagForChild](#tagforchild) for possible values.                                  |
| tagForUnderAgeOfPromise | number | The setting directed to users under the age of consent. Check [UnderAge](#underage) for possible values.            |

---

#### Consent

Ad consent object to be submitted.

| Parameter         | Type   | Definition                                    |
| ----------------- | ------ | --------------------------------------------- |
| consentStatus     | number | [ConsentStatus](#consentstatus) option.       |
| debugNeedConsent  | number | [DebugNeedConsent](#debugneedconsent) option. |
| underAgeOfPromise | number | [UnderAge](#underage) option.                 |
| testDeviceId      | string | Device Id.                                    |

---

#### ConsentResult

Consent information from api result

| Parameter     | Type                        | Definition                       |
| ------------- | --------------------------- | -------------------------------- |
| consentStatus | number                      | Status of consent.               |
| isNeedConsent | boolean                     | Shows whether consent is needed. |
| adProviders   | [AdProvider](#adprovider)[] | Ad provider list.                |

---

#### BannerInfo

Banner ad information.

| Parameter    | Type                          | Definition                       |
| ------------ | ----------------------------- | -------------------------------- |
| adId         | string                        | Ad slot id.                      |
| isLoading    | boolean                       | Shows whether banner is loading. |
| bannerAdSize | [BannerAdSize](#banneradsize) | BannerAdSize information.        |

---

#### InstreamInfo

Instream ad information.

| Parameter     | Type                        | Definition                                      |
| ------------- | --------------------------- | ----------------------------------------------- |
| adId          | string                      | Ad slot id.                                     |
| isLoading     | boolean                     | Indicates whether ad is loading.                |
| isPlaying     | boolean                     | Indicates whether ad is being played.           |
| totalDuration | number                      | Maximum total duration of roll ads, in seconds. |
| maxCount      | number                      | Maximum number of roll ads.                     |
| instreamAds   | [InstreamAd](#instreamad)[] | List of roll ads                                |

---

#### NativeInfo

Native ad information.

| Parameter             | Type                                            | Definition                           |
| --------------------- | ----------------------------------------------- | ------------------------------------ |
| nativeAd              | [NativeAd](#nativead)                           | Native ad information.               |
| nativeAdConfiguration | [NativeAdConfiguration](#nativeadconfiguration) | Native ad configuration information. |
| nativeAdLoader        | [NativeAdLoader](#nativeadloader)               | Native ad loader information.        |

---

#### Error

Ad error.

| Parameter    | Type   | Definition     |
| ------------ | ------ | -------------- |
| errorCode    | number | Error code.    |
| errorMessage | string | Error message. |

---

#### InstallReferrerResponse

Install referrer connection response.

| Parameter       | Type   | Definition        |
| --------------- | ------ | ----------------- |
| responseCode    | number | Response code.    |
| responseMessage | string | Response message. |

---

#### AdParam

Ad request parameters.

| Parameter               | Type   | Definition                                                                                                                            |
| ----------------------- | ------ | ------------------------------------------------------------------------------------------------------------------------------------- |
| adContentClassification | string | Ad content rating. Check [ContentClassification](#contentclassification) for possible values.                                         |
| appCountry              | string | Country code corresponding to the language in which an ad needs to be returned for an app.                                            |
| appLang                 | string | Language in which an ad needs to be returned for an app.                                                                              |
| belongCountryCode       | string | Home country code.                                                                                                                    |
| gender                  | number | Gender.  Check [Gender](#gender) for possible values.                                                                                 |
| nonPersonalizedAd       | number | The setting of requesting personalized ads. Check [NonPersonalizedAd](#nonpersonalizedad) for possible values.                        |
| requestOrigin           | string | Origin of request.                                                                                                                    |
| tagForChildProtection   | number | The setting of processing ad requests according to the COPPA. Check [TagForChild](#tagforchild) for possible values.                  |
| tagForUnderAgeOfPromise | number | The setting of processing ad requests as directed to users under the age of consent. Check [UnderAge](#underage) for possible values. |
| targetingContentUrl     | string | Targeting content url.                                                                                                                |

---

#### VerifyConfig

Server-side verification parameter.

| Parameter | Type   | Definition   |
| --------- | ------ | ------------ |
| userId    | number | User Id.     |
| data      | string | Custom data. |

---

#### AdTextStyle

| Parameter       | Type    | Definition        |
| --------------- | ------- | ----------------- |
| fontSize        | number  | Font size.        |
| color           | number  | Color.            |
| backgroundColor | number  | Background color. |
| visibility      | boolean | Visibility.       |

---

## 4. Configuration and Description

No.

---

## 5. Sample Project

This plugin includes a demo project in the [example](example) folder, there you can find more usage examples.

---

## 6. Questions or Issues

If you have questions about how to use HMS samples, try the following options:

- [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services) is the best place for any programming questions. Be sure to tag your question with **huawei-mobile-services**.
- [Github](https://github.com/HMS-Core/hms-react-native-plugin) is the official repository for these plugins, You can open an issue or submit your ideas.
- [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001) HMS Core Module is great for general questions, or seeking recommendations and opinions.
- [Huawei Developer Docs](https://developer.huawei.com/consumer/en/doc/overview/HMS-Core-Plugin) is place to official documentation for all HMS Core Kits, you can find detailed documentations in there.

If you run into a bug in our samples, please submit an issue to the [GitHub repository](https://github.com/HMS-Core/hms-react-native-plugin).

---

## 7. Licensing and Terms

Huawei React-Native Plugin is licensed under [Apache 2.0 license](LICENCE)