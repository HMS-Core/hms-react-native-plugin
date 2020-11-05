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

### Integrating React Native Ads Plugin

**Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and select **My projects**.

**Step 2:** Find your app project, and click the desired app name.

**Step 3:** Go to **Project Setting** > **General information**. In the **App information** section, click **agconnect-service.json** to download the configuration file.

**Step 4:** Create a React Native project if you do not have one.

**Step 5:** Copy the **agconnect-service.json** file to the **android/app** directory of your React Native project.

**Step 6:** Copy the signature file that generated in [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#3) section, to the android/app directory of your React Native project.

**Step 7:** Check whether the **agconnect-services.json** file and signature file are successfully added to the **android/app** directory of the React Native project.

**Step 8:** Open the **build.gradle** file in the **android** directory of your React Native project.

- Go to **buildscript** then configure the Maven repository address and agconnect plugin for the HMS SDK.

```groovy
buildscript {
  repositories {
    google()
    jcenter()
    maven { url 'https://developer.huawei.com/repo/' }
  }

  dependencies {
    /*
      * <Other dependencies>
      */
    classpath 'com.huawei.agconnect:agcp:1.4.1.300'
  }
}
```

- Go to **allprojects** then configure the Maven repository address for the HMS SDK.

```groovy
allprojects {
  repositories {
    google()
    jcenter()
    maven { url 'https://developer.huawei.com/repo/' }
  }
}
```

**Step 9:** Set your package name in **defaultConfig** > **applicationId** and set **minSdkVersion** to **19** or **higher**.

- Package name must match with the **package_name** entry in **agconnect-services.json** file.

```groovy
defaultConfig {
  applicationId "<package_name>"
  minSdkVersion {{your_min version}}
  /*
   * <Other configurations>
   */
}
```

- Copy the signature file that generated in [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#3) to **android/app** directory.

- Configure the signature in **android** according to the signature file information and configure Obfuscation Scripts.

```groovy
android {
  /*
   * <Other configurations>
   */

  signingConfigs {
    config {
      storeFile file('<keystore_file>.jks')
      storePassword '<keystore_password>'
      keyAlias '<key_alias>'
      keyPassword '<key_password>'
    }
  }

  buildTypes {
    debug {
      signingConfig signingConfigs.config
    }
    release {
      signingConfig signingConfigs.debug
      minifyEnabled enableProguardInReleaseBuilds
      ...
    }
  }
}
```

#### Using NPM

**Step 1:**  Download plugin using command below.

```bash
npm i @hmscore/react-native-hms-ads
```

**Step 2:**  Run your project.

- Run the following command to the project directory.

```bash
react-native run-android  
```

#### Download Link

To integrate the plugin, follow the steps below:

**Step 1:** Download the React Native Ads Plugin and place **react-native-hms-ads** under **node_modules/@hmscore** of your React Native project, as shown in the directory tree below.

            project.dir
                |_ node_modules
                    |_ @hmscore
                        |_ react-native-hms-ads
                        ...

**Step 2:** Open build.gradle file which is located under project.dir > android > app directory.

- Configure build dependencies.

```groovy
buildscript {
  ...
  dependencies {
    /*
    * <Other dependencies>
    */
    implementation project(":react-native-hms-ads")
    ...
  }
}
```

**Step 3:** Add the following lines to the android/settings.gradle file in your project:

```groovy
include ':app'
include ':react-native-hms-ads'
project(':react-native-hms-ads').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-ads/android')
```

**Step 4:**  Open the your application class and add the RNHMSAdsPackage

Import the following classes to the MainApplication.java file of your project.import **com.huawei.hms.rn.ads.RNHMSAdsPackage**.

Then, add the **RNHMSAdsPackage** to your **getPackages** method. In the end, your file will be similar to the following:

```java
import com.huawei.hms.rn.ads.RNHMSAdsPackage;

@Override
protected List<ReactPackage> getPackages() {
    List<ReactPackage> packages = new PackageList(this).getPackages();
    packages.add(new RNHMSAdsPackage());
    return packages;
}
...
```

**Step 5:**  Run your project.

- Run the following command to the project directory.

```bash
react-native run-android  
```

---

## 3. API Reference

### Components

#### `HMSBanner`

Banner ad component

`Simple Call Example`

```jsx
import {
  HMSBanner,
  BannerAdSizes,
} from '@hmscore/react-native-hms-ads';

<HMSBanner
  style={{height: 100}}
  bannerAdSize={{
    bannerAdSize:BannerAdSizes.B_320_100,
  }}
  adId="testw6vs28auh3"
/>
```

##### **`Properties`**

| Prop             | Type                                    | Definition                                                                                                                                                                       |
| ---------------- | --------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `bannerAdSize`   | [`BannerAdSizeProp`](#banneradsizeprop) | The object  parameter that has banner size information. It can have width information if needed.                                                                                 |
| `adId`           | `string`                                | Ad slot id.                                                                                                                                                                      |
| `adParam`        | [`AdParam`](#adparam)                   | Ad request parameter.                                                                                                                                                            |
| `onAdLoaded`     | `function`                              | The function to handle the event when ad loads. It gets event information object as argument which has `nativeEvent` as key and [`BannerResult`](#bannerresult) object as value. |
| `onAdFailed`     | `function`                              | The function to handle the event when ad fails to load. It gets event information object as argument which has `nativeEvent` as key and [`Error`](#error) object as value.       |
| `onAdOpened`     | `function`                              | The function to handle the event when ad is opened.                                                                                                                              |
| `onAdClicked`    | `function`                              | The function to handle the event when ad is clicked.                                                                                                                             |
| `onAdClosed`     | `function`                              | The function to handle the event when ad is closed.                                                                                                                              |
| `onAdImpression` | `function`                              | The function to handle the event when ad impression is detected.                                                                                                                 |
| `onAdLeave`      | `function`                              | The function to handle the event when user leaves the app.                                                                                                                       |

---

##### **`BannerAdSizeProp`**

| Parameter      | Type      | Definition                                                                                                                         |
| -------------- | --------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| `bannerAdSize` | `string`  | Banner ad sizes.  Check [`BannerAdSizes`](#banneradsizes) for possible values.                                                     |
| `width`        | `integer` | If banner ad size is `'portrait'`, or `'landscape'`, or `'currentDirection'`, this will be needed to set the width of the banner . |

`Commands`

Commands can be used with component references which are created with ref prop of React component.

`Call Example`

```jsx
import {
  HMSBanner,
  BannerAdSizes,
} from '@hmscore/react-native-hms-ads';

// Example for commands
let adBannerElement;
// ...
<HMSBanner
  style={{height: 100}}
  bannerAdSize={{
    bannerAdSize:BannerAdSizes.B_320_100,
  }}
  adId="testw6vs28auh3"
  ref={(el) => (adBannerElement = el)}
/>
```

a) `loadAd()`

Loads banner.

`Call Example`

```jsx
adBannerElement.loadAd()
```

b) `setRefresh()`

Sets a rotation interval for banner ads. Input is rotation interval, in seconds. It should range from 30 to 120.

`Parameters`

| Name     | Type     | Description                                                     |
| -------- | -------- | --------------------------------------------------------------- |
| interval | `number` | Rotation interval, in seconds. It should range from 30 to 120 . |

`Call Example`

```jsx
adBannerElement.setRefresh(60)
```

c) `pause()`

Pauses any additional processing related to ad.

`Call Example`

```jsx
adBannerElement.pause()
```

f) `resume()`

Resumes ad after the pause() method is called last time.

`Call Example`

```jsx
adBannerElement.resume()
```

e) `destroy()`

Destroys ad.

`Call Example`

```jsx
adBannerElement.destroy()
```

`Complex Call Example`

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
  onAdLoaded={(e) => {
    console.log('HMSBanner onAdLoaded', e.nativeEvent);
  }}
  onAdFailed={(e) => {
    console.warn('HMSBanner onAdFailed', e.nativeEvent);
  }}
  onAdOpened={(e) => console.log('HMSBanner onAdOpened')}
  onAdClicked={(e) => console.log('HMSBanner onAdClicked')}
  onAdClosed={(e) => console.log('HMSBanner onAdClosed')}
  onAdImpression={(e) => console.log('HMSBanner onAdImpression')}
  onAdLeave={(e) => console.log('HMSBanner onAdLeave')}
/>
```

---

#### `HMSNative`

Native ad component

`Simple Call Example`

```jsx
import {
  HMSNative,
  NativeMediaTypes,
} from '@hmscore/react-native-hms-ads';

<HMSNative
  style={{ height: 322 }}
  displayForm={{
    mediaType: NativeMediaTypes.VIDEO,
    adId: 'testy63txaom86'
  }}
/>
```

##### **`Properties`**

| Parameter          | Type                                              | Definition                                                                                                                                                                       |
| ------------------ | ------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `displayForm`      | [`DisplayFormProp`](#displayformprop)             | The object parameter that has ad slot id and media type information.                                                                                                             |
| `adParam`          | [`AdParam`](#adparam)                             | Ad request parameter.                                                                                                                                                            |
| `nativeConfig`     | [`NativeAdConfiguration`](#nativeadconfiguration) | Native ad configuration parameter.                                                                                                                                               |
| `viewOptions`      | [`ViewOptionsProp`](#viewoptionsprop)             | View options parameter.                                                                                                                                                          |
| `onNativeAdLoaded` | `function`                                        | The function to handle the event when ad loads. It gets event information object as argument which has `nativeEvent` as key and [`NativeResult`](#nativeresult) object as value. |
| `onAdDisliked`     | `function`                                        | The function to handle the event when ad is disliked.                                                                                                                            |
| `onAdFailed`       | `function`                                        | The function to handle the event when ad fails to load. It gets event information object as argument which has `nativeEvent` as key and [`Error`](#error) object as value.       |
| `onAdClicked`      | `function`                                        | The function to handle the event when ad is clicked.                                                                                                                             |
| `onAdImpression`   | `function`                                        | The function to handle the event when ad impression is detected.                                                                                                                 |
| `onVideoStart`     | `function`                                        | The function to handle the event when ad video starts playing.                                                                                                                   |
| `onVideoPlay`      | `function`                                        | The function to handle the event when ad video plays.                                                                                                                            |
| `onVideoEnd`       | `function`                                        | The function to handle the event when ad video ends.                                                                                                                             |

---

##### **`DisplayFormProp`**

| Parameter   | Type     | Definition                                                                                      |
| ----------- | -------- | ----------------------------------------------------------------------------------------------- |
| `mediaType` | `string` | Media type of the native ad. Check [`NativeMediaTypes`](#nativemediatypes) for possible values. |
| `adId`      | `string` | Ad slot id.                                                                                     |

---

##### **`ViewOptionsProp`**

| Parameter              | Type                          | Definition                                                                |
| ---------------------- | ----------------------------- | ------------------------------------------------------------------------- |
| `showMediaContent`     | `boolean`                     | The option for showing media content.                                     |
| `mediaImageScaleType`  | `integer`                     | The image scale type. Check [`ScaleType`](#scaletype) for possible values |
| `adSourceTextStyle`    | [`AdTextStyle`](#adtextstyle) | The style of ad source.                                                   |
| `adFlagTextStyle`      | [`AdTextStyle`](#adtextstyle) | The style of ad flag.                                                     |
| `titleTextStyle`       | [`AdTextStyle`](#adtextstyle) | The style of ad title.                                                    |
| `descriptionTextStyle` | [`AdTextStyle`](#adtextstyle) | The style of ad description.                                              |
| `callToActionStyle`    | [`AdTextStyle`](#adtextstyle) | The style of ad call-to-action button.                                    |

---

`Commands`

Commands can be used with component references which are created with ref prop of React component.

`Call Example`

```jsx
import {
  HMSNative,
  NativeMediaTypes,
} from '@hmscore/react-native-hms-ads';

let adNativeElement;
// ...
<HMSNative
  style={{height: 100}}
  displayForm={{
    mediaType:NativeMediaTypes.VIDEO,
    adId:'testy63txaom86',
  }}
  ref={(el) => (adNativeElement = el)}
/>
```

a) `loadAd()`

Loads native ad.

`Call Example`

```jsx
adNativeElement.loadAd()
```

b) `dislikeAd()`

Dislikes ad with description.

`Parameters`

| Name   | Type     | Description                     |
| ------ | -------- | ------------------------------- |
| reason | `string` | Reason why the ad is disliked . |

`Call Example`

```jsx
adNativeElement.dislikeAd('Just dont like it')
```

c) `destroy()`

Destroys ad.

`Call Example`

```jsx
adNativeElement.destroy()
```

d) `gotoWhyThisAdPage()`

Goes to the page explaining why an ad is displayed.

`Call Example`

```jsx
adNativeElement.gotoWhyThisAdPage()
```

e) `setAllowCustomClick()`

Enables custom tap gestures.

`Call Example`

```jsx
adNativeElement.setAllowCustomClick()
```

f) `recordClickEvent()`

Reports a custom tap gesture.

`Call Example`

```jsx
adNativeElement.recordClickEvent()
```

g) `recordImpressionEvent()`

Reports an ad impression.

`Parameters`

| Name       | Type     | Description              |
| ---------- | -------- | ------------------------ |
| impression | `object` | Custom impression data . |

`Call Example`

```jsx
adNativeElement.recordImpressionEvent({myKey: 'myValue', yourKey:{ cool: true}})
```

`Complex Call Example`

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
  onNativeAdLoaded={(e) => {
    console.log('HMSNative onNativeAdLoaded', e.nativeEvent);
  }}
  onAdDisliked={(e) => console.log('HMSNative onAdDisliked')}
  onAdFailed={(e) => {
    console.warn('HMSNative onAdFailed', e.nativeEvent);
  }}
  onAdClicked={(e) => console.log('HMSNative onAdClicked')}
  onAdImpression={(e) => console.log('HMSNative onAdImpression')}
  onVideoStart={(e) => console.log('HMSNative onVideoStart')}
  onVideoPlay={(e) => console.log('HMSNative onVideoPlay')}
  onVideoEnd={(e) => console.log('HMSNative onVideoEnd')}
  ref={(el) => {
    adNativeElement = el;
  }}
/>
```

### Modules

- #### `HMSAds`
  
  - Methods
    - [`init()`](#hmsadsinit)
    - [`getSDKVersion()`](#hmsadsgetsdkversion)
    - [`setRequestOptions()`](#hmsadssetrequestoptions)
    - [`getRequestOptions()`](#hmsadsgetrequestoptions)
    - [`setConsent()`](#hmsadssetconsent)
    - [`checkConsent()`](#hmsadscheckconsent)

- #### `HMSOaid`

  - Methods
    - [`verifyAdvertisingId()`](#hmsoaidverifyadvertisingid)
    - [`getAdvertisingIdInfo()`](#hmsoaidgetadvertisingidinfo)
  
- #### `HMSReward`

  - Methods
    - [`setAdParam()`](#hmsrewardsetadparam)
    - [`setAdId()`](#hmsrewardsetadid)
    - [`setData()`](#hmsrewardsetdata)
    - [`setUserId()`](#hmsrewardsetuserid)
    - [`pause()`](#hmsrewardpause)
    - [`resume()`](#hmsrewardresume)
    - [`setVerifyConfig()`](#hmsrewardsetverifyconfig)
    - [`loadAd()`](#hmsrewardloadad)
    - [`show()`](#hmsrewardshow)
    - [`isLoaded()`](#hmsrewardisloaded)
    - [`allListenersRemove()`](#hmsrewardalllistenersremove)
  
  - Events
    - [`adLoaded`](#hmsrewardadloadedlisteneradd)
    - [`adFailedToLoad`](#hmsrewardadfailedtoloadlisteneradd)
    - [`adFailedToShow`](#hmsrewardadfailedtoshowlisteneradd)
    - [`adOpened`](#hmsrewardadopenedlisteneradd)
    - [`adClosed`](#hmsrewardadclosedlisteneradd)
    - [`adRewarded`](#hmsrewardadrewardedlisteneradd)

- #### `HMSInterstitial`

  - Methods
    - [`setAdParam()`](#hmsinterstitialsetadparam)
    - [`setAdId()`](#hmsinterstitialsetadid)
    - [`loadAd()`](#hmsinterstitialloadad)
    - [`show()`](#hmsinterstitialshow)
    - [`isLoaded()`](#hmsinterstitialisloaded)
    - [`allListenersRemove()`](#hmsinterstitialalllistenersremove)

  - Events
    - [`adLoaded`](#hmsinterstitialadloadedlisteneradd)
    - [`adFailed`](#hmsinterstitialadfailedlisteneradd)
    - [`adLeave`](#hmsinterstitialadleavelisteneradd)
    - [`adOpened`](#hmsinterstitialadopenedlisteneradd)
    - [`adClosed`](#hmsinterstitialadclosedlisteneradd)
    - [`adClicked`](#hmsinterstitialadclickedlisteneradd)
    - [`adImpression`](#hmsinterstitialadimpressionlisteneradd)

- #### `HMSSplash`

  - Methods
    - [`setAdParam()`](#hmssplashsetadparam)
    - [`setAdId()`](#hmssplashsetadid)
    - [`setLogoText()`](#hmssplashsetlogotext)
    - [`setCopyrightText()`](#hmssplashsetcopyrighttext)
    - [`setOrientation()`](#hmssplashsetorientation)
    - [`setSloganResource()`](#hmssplashsetsloganresource)
    - [`setWideSloganResource()`](#hmssplashsetwidesloganresource)
    - [`setLogoResource()`](#hmssplashsetlogoresource)
    - [`setMediaNameResource()`](#hmssplashsetmedianameresource)
    - [`setAudioFocusType()`](#hmssplashsetaudiofocustype)
    - [`pause()`](#hmssplashpause)
    - [`resume()`](#hmssplashresume)
    - [`destroy()`](#hmssplashdestroy)
    - [`isLoading()`](#hmssplashisloading)
    - [`isLoaded()`](#hmssplashisloaded)
    - [`show()`](#hmssplashshow)
    - [`allListenersRemove()`](#hmssplashalllistenersremove)

  - Events
    - [`adLoaded`](#hmssplashadloadedlisteneradd)
    - [`adFailedToLoad`](#hmssplashadfailedtoloadlisteneradd)
    - [`adDismissed`](#hmssplashaddismissedlisteneradd)
    - [`adShowed`](#hmssplashadshowedlisteneradd)
    - [`adClick`](#hmssplashadclicklisteneradd)
  
- #### `HMSInstallReferrer`

  - Methods
    - [`startConnection()`](#hmsinstallreferrerstartconnection)
    - [`endConnection()`](#hmsinstallreferrerendconnection)
    - [`getReferrerDetails()`](#hmsinstallreferrergetreferrerdetails)
    - [`isReady()`](#hmsinstallreferrerisready)
    - [`allListenersRemove()`](#hmsinstallreferreralllistenersremove)

  - Events
    - [`serviceConnected`](#hmsinstallreferrerserviceconnectedlisteneradd)
    - [`serviceDisconnected`](#hmsinstallreferrerservicedisconnectedlisteneradd)

### Methods

#### `HMSAds.init()`

Initializes the HUAWEI Ads SDK. The function returns a promise that resolves a string 'Hw Ads Initialized'.

`Return Type`

| Type            | Description        |
| --------------- | ------------------ |
| Promise<string> | Hw Ads Initialized |

`Call Example`

```jsx
import HMSAds from '@hmscore/react-native-hms-ads';

HMSAds.init()
  .then((result) => console.log('HMSAds init, result:', result))
```

#### `HMSAds.getSDKVersion()`

Obtains the version number of the HUAWEI Ads SDK. The function returns a promise that resolves a string of the version number.

`Return Type`

| Type            | Description |
| --------------- | ----------- |
| Promise<string> | SDK Version |

`Call Example`

```jsx
import HMSAds from '@hmscore/react-native-hms-ads';

HMSAds.getSDKVersion()
  .then((result) => console.log('HMS getSDKVersion, result:', result));
```

#### `HMSAds.setRequestOptions()`

Provides the global ad request configuration. The function returns a promise that resolves a [`RequestOptions`](#requestoptions) object.

`Parameters`

| Name           | Type                                | Description         |
| -------------- | ----------------------------------- | ------------------- |
| requestOptions | [`RequestOptions`](#requestoptions) | Ad request options. |

`Return Type`

| Type                                         | Description         |
| -------------------------------------------- | ------------------- |
| Promise<[`RequestOptions`](#requestoptions)> | Ad request options. |

`Call Example`

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
  .then((result) => console.log('HMS setRequestOptions, result:', result))
```

#### `HMSAds.getRequestOptions()`

Obtains the global request configuration. The function returns a promise that resolves a [`RequestOptions`](#requestoptions) object.

`Return Type`

| Type                                  | Description         |
| ------------------------------------- | ------------------- |
| <[`RequestOptions`](#requestoptions)> | Ad request options. |

`Call Example`

```jsx
import HMSAds from '@hmscore/react-native-hms-ads';

HMSAds.getRequestOptions()
  .then((result) => console.log('HMS getRequestOptions, result:', result))
```

#### `HMSAds.setConsent()`

Provides ad consent configuration. The function returns a promise that resolves a [`ConsentResult`](#consentresult) object.

`Parameters`

| Name          | Type                              | Description                         |
| ------------- | --------------------------------- | ----------------------------------- |
| consentResult | [`ConsentResult`](#consentresult) | Consent information from api result |

`Return Type`

| Type                                       | Description                         |
| ------------------------------------------ | ----------------------------------- |
| Promise<[`ConsentResult`](#consentresult)> | Consent information from api result |

`Call Example`

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
  .then((result) => console.log('HMS setConsent, result:', result))
  .catch((e) => console.warn('HMS setConsent, error:', e))
```

#### `HMSAds.checkConsent()`

Obtains ad consent configuration. The function returns a promise that resolves a [`ConsentResult`](#consentresult) object.

`Return Type`

| Type                                       | Description                         |
| ------------------------------------------ | ----------------------------------- |
| Promsie<[`ConsentResult`](#consentresult)> | Consent information from api result |

`Call Example`

```jsx
import HMSAds from '@hmscore/react-native-hms-ads';

HMSAds.checkConsent()
  .then((result) => console.log('HMS checkConsent, result:', result))
  .catch((e) => console.log('HMS checkConsent, error:', e))
```
  
---

#### `HMSOaid.getAdvertisingIdInfo()`

Obtains the OAID and 'Limit ad tracking' setting. The string argument should be one of values of [`CallMode`](#callmode). The function returns a promise that resolves a [`AdvertisingIdClientInfo`](#advertisingidclientinfo) object.

`Parameters`

| Name     | Type   | Description                                                                                                          |
| -------- | ------ | -------------------------------------------------------------------------------------------------------------------- |
| callMode | string | Option for functions that can use Huawei SDK or [Aidl](https://developer.android.com/guide/components/aidl) service. |

`Return Type`

| Type                                                           | Description                           |
| -------------------------------------------------------------- | ------------------------------------- |
| Promise<[`AdvertisingIdClientInfo`](#advertisingidclientinfo)> | Information about advertised clients. |

`Call Example`

```jsx
import {HMSOaid, CallMode} from '@hmscore/react-native-hms-ads';

let callMode = CallMode.SDK;
HMSOaid.getAdvertisingIdInfo(callMode)
  .then((result) => console.log('HMSOaid getAdvertisingIdInfo, result:', result))
  .catch((e) => console.log('HMSOaid getAdvertisingIdInfo, error:', e))
```

#### `HMSOaid.verifyAdvertisingId()`

Verifies the OAID and 'Limit ad tracking' setting. The function returns a promise that resolves a boolean showing the verification result.

`Parameters`

| Name                    | Type                                                  | Description                           |
| ----------------------- | ----------------------------------------------------- | ------------------------------------- |
| advertisingIdClientInfo | [`AdvertisingIdClientInfo`](#advertisingidclientinfo) | Information about advertised clients. |

`Return Type`

| Type                                                           | Description                           |
| -------------------------------------------------------------- | ------------------------------------- |
| Promise<[`AdvertisingIdClientInfo`](#advertisingidclientinfo)> | Information about advertised clients. |

`Call Example`

```jsx
import {HMSOaid} from '@hmscore/react-native-hms-ads';

// should use information obtained from 'getAdvertisingIdInfo()' function
let advertisingInfo = {
  id: "01234567-89abc-defe-dcba-987654321012",
  isLimitAdTrackingEnabled: false
}
HMSOaid.verifyAdvertisingId(advertisingInfo)
  .then((result) => console.log('HMSOaid verifyAdvertisingId, result:', result))
  .catch((e) => console.warn('HMSOaid verifyAdvertisingId, error:', e))
```
  
---

#### `HMSReward.setAdId()`

Sets ad slot id.

`Parameters`

| Name   | Type     | Description |
| ------ | -------- | ----------- |
| slotId | `string` | Slot id.    |

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.setAdId("testx9dtjwj8hp"); // video ad
```

#### `HMSReward.setUserId()`

Sets user id.

`Parameters`

| Name   | Type     | Description |
| ------ | -------- | ----------- |
| userId | `string` | User id.    |

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.setUserId("HMS User");
```

#### `HMSReward.setData()`

Sets custom data in string.

`Parameters`

| Name | Type     | Description            |
| ---- | -------- | ---------------------- |
| data | `string` | Custom data in string. |

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.setData("HMS Data");
```

#### `HMSReward.setVerifyConfig()`

Sets server-side verification parameters.

`Parameters`

| Name         | Type                            | Description                         |
| ------------ | ------------------------------- | ----------------------------------- |
| verifyConfig | [`VerifyConfig`](#verifyconfig) | Server-side verification parameter. |

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.setVerifyConfig({userId: 'userxxxxx', data: 'dataxxxx'});
```

#### `HMSReward.setAdParam()`

Sets parameters of ad request.

`Parameters`

| Name    | Type                  | Description            |
| ------- | --------------------- | ---------------------- |
| adParam | [`AdParam`](#adparam) | Ad request parameters. |

`Call Example`

```jsx
import {HMSReward, ContentClassification, UnderAge} from '@hmscore/react-native-hms-ads';

HMSReward.setAdParam({
  adContentClassification: ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
  tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED
});
```

#### `HMSReward.pause()`

Pauses the ad.

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.pause();
```

#### `HMSReward.resume()`

Resumes the ad.

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.resume();
```

#### `HMSReward.loadAd()`

Requests ad.

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.loadAd();
```

#### `HMSReward.show()`

Displays ad.

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.show();
```

#### `HMSReward.isLoaded()`

Checks whether ad is successfully loaded. The function returns a promise that resolves  a boolean indicating whether the ad is loaded or not.

`Return Type`

| Type               | Description                               |
| ------------------ | ----------------------------------------- |
| Promise<`boolean`> | Indicates whether the ad is loaded or not |

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.isLoaded()
  .then((result) => console.log('HMSReward isLoaded, result:', result))
```

#### `HMSReward.allListenersRemove()`

Remove all listeners for events of HMSReward.

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.allListenersRemove();
```
  
---

#### `HMSSplash.setAdId()`

Sets ad slot id.

`Parameters`

| Name   | Type     | Description |
| ------ | -------- | ----------- |
| slotId | `string` | Slot id.    |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.setAdId("testd7c5cewoj6"); // video ad
```

#### `HMSSplash.setLogoText()`

Sets logo text.

`Parameters`

| Name     | Type     | Description |
| -------- | -------- | ----------- |
| logoText | `string` | Logo text.  |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.setLogoText("HMS Sample");
```

#### `HMSSplash.setCopyrightText()`

Sets copyright text.

`Parameters`

| Name          | Type     | Description     |
| ------------- | -------- | --------------- |
| copyRightText | `string` | Copyright text. |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.setCopyrightText("Copyright HMS");
```

#### `HMSSplash.setOrientation()`

Sets [screen orientation](https://developer.android.com/reference/android/content/pm/ActivityInfo#screenOrientation).

`Parameters`

| Name              | Type     | Description                                                                                                           |
| ----------------- | -------- | --------------------------------------------------------------------------------------------------------------------- |
| screenOrientation | `number` | Sets [screen orientation](https://developer.android.com/reference/android/content/pm/ActivityInfo#screenOrientation). |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.setOrientation(1);
```

#### `HMSSplash.setSloganResource()`

Sets default app launch image in portrait mode, which is displayed before a splash ad is displayed.

`Parameters`

| Name           | Type     | Description                                             |
| -------------- | -------- | ------------------------------------------------------- |
| sloganResource | `string` | text that is displayed before a splash ad is displayed. |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';
// file_name_without_extension.png added to res/drawable or res/mipmap
HMSSplash.setSloganResource("file_name_without_extension");
```

#### `HMSSplash.setWideSloganResource()`

Sets default app launch image in landscape mode, which is displayed before a splash ad is displayed.

`Parameters`

| Name               | Type     | Description                                             |
| ------------------ | -------- | ------------------------------------------------------- |
| wideSloganResource | `string` | Text that is displayed before a splash ad is displayed. |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';
// file_name_without_extension.png added to res/drawable or res/mipmap
HMSSplash.setWideSloganResource("file_name_without_extension");
```

#### `HMSSplash.setLogoResource()`

Sets app logo.

`Parameters`

| Name    | Type     | Description                          |
| ------- | -------- | ------------------------------------ |
| appLogo | `string` | App logo file path without extension |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';
// file_name_without_extension.png added to res/drawable or res/mipmap
HMSSplash.setLogoResource("file_name_without_extension");
```

#### `HMSSplash.setMediaNameResource()`

Sets app text resource.

`Parameters`

| Name         | Type     | Description       |
| ------------ | -------- | ----------------- |
| textResource | `string` | app text resource |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';
// <string name="media_name">HUAWEI Ads</string> line inserted to strings.xml
HMSSplash.setMediaNameResource("media_name");
```

#### `HMSSplash.setAudioFocusType()`

Sets the audio focus preemption policy for a video splash ad.

`Parameters`

| Name           | Type     | Description                                                                    |
| -------------- | -------- | ------------------------------------------------------------------------------ |
| audioFocusType | `number` | Number value that determines the the audio focus preemption policy for a video |

`Call Example`

```jsx
import {HMSSplash, AudioFocusType} from '@hmscore/react-native-hms-ads';

HMSSplash.setAudioFocusType(AudioFocusType.GAIN_AUDIO_FOCUS_ALL);
```

#### `HMSSplash.setAdParam()`

Sets parameters of ad request.

`Parameters`

| Name    | Type                  | Description            |
| ------- | --------------------- | ---------------------- |
| adParam | [`AdParam`](#adparam) | Ad request parameters. |

`Call Example`

```jsx
import {HMSSplash, ContentClassification, UnderAge} from '@hmscore/react-native-hms-ads';

HMSSplash.setAdParam({
  adContentClassification: ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
  tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED
});
```

#### `HMSSplash.pause()`

Pauses ad.

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.pause();
```

#### `HMSSplash.resume()`

Resumes ad.

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.resume();
```

#### `HMSSplash.destroy()`

Destroys ad.

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.destroy();
```

#### `HMSSplash.show()`

Shows ad.

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.show();
```

#### `HMSSplash.isLoaded()`

Checks whether a splash ad has been loaded.

`Return Type`

| Type               | Description                            |
| ------------------ | -------------------------------------- |
| Promise<`boolean`> | Indicates whether ad is loaded or not. |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.isLoaded()
  .then((result) => console.log('HMSSplash isLoaded, result:', result))
```

#### `HMSSplash.isLoading()`

Checks whether a splash ad is being loaded.

`Return Type`

| Type               | Description                             |
| ------------------ | --------------------------------------- |
| Promise<`boolean`> | Indicates whether ad is loading or not. |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.isLoading()
  .then((result) => console.log('HMSSplash isLoading, result:', result))
```

#### `HMSSplash.allListenersRemove()`

Remove all listeners for events of HMSSplash.

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.allListenersRemove();
```

---

#### `HMSInterstitial.setAdId()`

Sets ad slot id.

`Parameters`

| Name   | Type     | Description |
| ------ | -------- | ----------- |
| slotId | `string` | Ad slot id. |

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.setAdId("testb4znbuh3n2"); // video ad
```

#### `HMSInterstitial.setAdParam()`

Sets parameters of ad request.

`Parameters`

| Name    | Type                  | Description            |
| ------- | --------------------- | ---------------------- |
| adParam | [`AdParam`](#adparam) | Ad request parameters. |

`Call Example`

```jsx
import {HMSInterstitial, ContentClassification, UnderAge} from '@hmscore/react-native-hms-ads';

HMSInterstitial.setAdParam({
  adContentClassification: ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
  tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED
});
```

#### `HMSInterstitial.loadAd()`

Initiates a request to load an ad.

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.loadAd();
```

#### `HMSInterstitial.show()`

Displays an interstitial ad.

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.show();
```

#### `HMSInterstitial.isLoaded()`

Checks whether ad loading is complete.

`Return Type`

| Type               | Description                            |
| ------------------ | -------------------------------------- |
| Promise<`boolean`> | Indicates whether ad is loaded or not. |

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.isLoaded()
  .then((result) => console.log('HMSInterstitial isLoaded, result:', result))
```

#### `HMSInterstitial.allListenersRemove()`

Remove all listeners for events of HMSInterstitial.

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.allListenersRemove();
```

---

#### `HMSInstallReferrer.startConnection()`

Starts to connect to the install referrer service. The first string argument should be one of values of [`CallMode`](#callmode). And the boolean argument indicates test mode. The last string argument is the name of the package that the service receives information about. The function returns a promise that resolves a boolean indicating whether the service is successfully connected.

`Parameters`

| Name        | Type      | Description                                                                                                          |
| ----------- | --------- | -------------------------------------------------------------------------------------------------------------------- |
| callMode    | `string`  | Option for functions that can use Huawei SDK or [Aidl](https://developer.android.com/guide/components/aidl) service. |
| testMode    | `boolean` | Indicates test mode.                                                                                                 |
| packageName | `string`  | Name of the package that the service receives information about.                                                     |

`Return Type`

| Type               | Description                                              |
| ------------------ | -------------------------------------------------------- |
| Promise<`boolean`> | Indicates whether the service is successfully connected. |

`Call Example`

```jsx
import {HMSInstallReferrer, CallMode} from '@hmscore/react-native-hms-ads';

let callMode = CallMode.SDK;
let isTest = 'true';
let pkgName = 'com.huawei.rnhmsadsdemo'; // your app package name
HMSInstallReferrer.startConnection(callMode, isTest, pkgName)
  .then((result) => console.log('HMSInstallReferrer startConnection, result:', result))
  .catch((e) => console.warn('HMSInstallReferrer startConnection, error:', e));
```

#### `HMSInstallReferrer.endConnection()`

Ends the service connection and releases all occupied resources.

`Return Type`

| Type          | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise<void> | Promise that resolves if the operation is successful |

`Call Example`

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.endConnection()
  .then(() => console.log('HMSInstallReferrer endConnection'))
  .catch((e) => console.warn('HMSInstallReferrer endConnection, error:', e));
```

#### `HMSInstallReferrer.getReferrerDetails()`

Obtains install referrer information.

`Return Type`

| Type                                         | Description                                 |
| -------------------------------------------- | ------------------------------------------- |
| Promise<[ReferrerDetails](#referrerdetails)> | Describes the install referrer information. |

`Call Example`

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.getReferrerDetails()
  .then((result) => console.log('HMSInstallReferrer getReferrerDetails, result:', result))
  .catch((e) => console.warn('HMSInstallReferrer getReferrerDetails, error:', e));
```

#### `HMSInstallReferrer.isReady()`

Indicates whether the service connection is ready.

`Return Type`

| Type               | Description                                        |
| ------------------ | -------------------------------------------------- |
| Promise<`boolean`> | Indicates whether the service connection is ready. |

`Call Example`

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.isReady()
  .then((result) => console.log('HMSInstallReferrer isReady, result:', result));
```

#### `HMSInstallReferrer.allListenersRemove()`

Remove all listeners for events of HMSInstallReferrer.

`Call Example`

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.allListenersRemove();
```

### Events

#### `Events of HMSInterstitial`

| Event          | Description                                   |
| -------------- | --------------------------------------------- |
| `adFailed`     | Event emitted when ad fails to load.          |
| `adClosed`     | Event emitted when ad is closed.              |
| `adLeave`      | Event emitted when the user leaves the app.   |
| `adOpened`     | Event emitted when ad is displayed.           |
| `adLoaded`     | Event emitted when ad loads.                  |
| `adClicked`    | Event emitted when ad is clicked.             |
| `adImpression` | Event emitted when ad impression is detected. |

#### `Listener Functions of HMSInterstitial`

| Functions                                                                |
| ------------------------------------------------------------------------ |
| [adFailedListenerAdd](#hmsinterstitialadfailedlisteneradd)               |
| [adFailedListenerRemove](#hmsinterstitialadfailedlistenerremove)         |
| [adClosedListenerAdd](#hmsinterstitialadclosedlisteneradd)               |
| [adClosedListenerRemove](#hmsinterstitialadclosedlistenerremove)         |
| [adLeaveListenerAdd](#hmsinterstitialadleavelisteneradd)                 |
| [adLeaveListenerRemove](#hmsinterstitialadleavelistenerremove)           |
| [adOpenedListenerAdd](#hmsinterstitialadopenedlisteneradd)               |
| [adOpenedListenerRemove](#hmsinterstitialadopenedlistenerremove)         |
| [adLoadedListenerAdd](#hmsinterstitialadloadedlisteneradd)               |
| [adLoadedListenerRemove](#hmsinterstitialadloadedlistenerremove)         |
| [adClickedListenerAdd](#hmsinterstitialadclickedlisteneradd)             |
| [adClickedListenerRemove](#hmsinterstitialadclickedlistenerremove)       |
| [adImpressionListenerAdd](#hmsinterstitialadimpressionlisteneradd)       |
| [adImpressionListenerRemove](#hmsinterstitialadimpressionlistenerremove) |

##### `HMSInterstitial.adFailedListenerAdd()`

Adds listener for adFailed event. The listener function gets [Error](#error) as input.

`Parameters`

| Name | Type                         | Description        |
| ---- | ---------------------------- | ------------------ |
| fn   | (res: [Error](#error) )=> {} | Listener function. |

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adFailedListenerAdd((error) => {
  console.warn('HMSInterstitial adFailed, error: ', error);
});
```

##### `HMSInterstitial.adFailedListenerRemove()`

Removes listeners for adFailed event.

`Call Example`

```jsx
HMSInterstitial.adFailedListenerRemove();
```

##### `HMSInterstitial.adClosedListenerAdd()`

Adds listener for adClosed event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adClosedListenerAdd(() => {
  console.log('HMSInterstitial adClosed');
});
```

##### `HMSInterstitial.adClosedListenerRemove()`

Removes listeners for adClosed event.

`Call Example`

```jsx
HMSInterstitial.adClosedListenerRemove();
```

##### `HMSInterstitial.adLeaveListenerAdd()`

Adds listener for adLeave event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adLeaveListenerAdd(() => {
  console.warn('HMSInterstitial adLeave');
});
```

##### `HMSInterstitial.adLeaveListenerRemove()`

Removes listeners for adLeave event.

`Call Example`

```jsx
HMSInterstitial.adLeaveListenerRemove();
```

##### `HMSInterstitial.adOpenedListenerAdd()`

Adds listener for adOpened event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adOpenedListenerAdd(() => {
  console.log('HMSInterstitial adOpened');
});
```

##### `HMSInterstitial.adOpenedListenerRemove()`

Removes listeners for adOpened event.

`Call Example`

```jsx
HMSInterstitial.adOpenedListenerRemove();
```

##### `HMSInterstitial.adLoadedListenerAdd()`

Adds listener for adLoaded event. The listener function gets [InterstitialAd](#interstitialad) as input.

`Parameters`

| Name | Type                                          | Description        |
| ---- | --------------------------------------------- | ------------------ |
| fn   | (res: [InterstitialAd](#interstitialad))=> {} | Listener function. |

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adLoadedListenerAdd((interstitialAd) => {
  console.log('HMSInterstitial adLoaded, Interstitial Ad: ', interstitialAd);
});
```

##### `HMSInterstitial.adLoadedListenerRemove()`

Removes listeners for adLoaded event.

`Call Example`

```jsx
HMSInterstitial.adLoadedListenerRemove();
```

##### `HMSInterstitial.adClickedListenerAdd()`

Adds listener for adClicked event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adClickedListenerAdd(() => {
  console.log('HMSInterstitial adClicked');
});
```

##### `HMSInterstitial.adClickedListenerRemove()`

Removes listeners for adClicked event.

`Call Example`

```jsx
HMSInterstitial.adClickedListenerRemove();
```

##### `HMSInterstitial.adImpressionListenerAdd()`

Adds listener for adImpression event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSInterstitial} from '@hmscore/react-native-hms-ads';

HMSInterstitial.adImpressionListenerAdd(() => {
  console.log('HMSInterstitial adImpression');
});
```

##### `HMSInterstitial.adImpressionListenerRemove()`

Removes listeners for adImpression event.

`Call Example`

```jsx
HMSInterstitial.adImpressionListenerRemove();
```
  
---

#### `Events of HMSSplash`

| Event            | Description                          |
| ---------------- | ------------------------------------ |
| `adFailedToLoad` | Event emitted when ad fails to load. |
| `adDismissed`    | Event emitted when ad is dismissed.  |
| `adShowed`       | Event emitted when ad is shown.      |
| `adLoaded`       | Event emitted when ad loads.         |
| `adClick`        | Event emitted when ad is clicked.    |

#### `Listener Functions of HMSSplash`

| Functions                                                              |
| ---------------------------------------------------------------------- |
| [adFailedToLoadListenerAdd](#hmssplashadfailedtoloadlisteneradd)       |
| [adFailedToLoadListenerRemove](#hmssplashadfailedtoloadlistenerremove) |
| [adDismissedListenerAdd](#hmssplashaddismissedlisteneradd)             |
| [adDismissedListenerRemove](#hmssplashaddismissedlistenerremove)       |
| [adShowedListenerAdd](#hmssplashadshowedlisteneradd)                   |
| [adShowedListenerRemove](#hmssplashadshowedlistenerremove)             |
| [adLoadedListenerAdd](#hmssplashadloadedlisteneradd)                   |
| [adLoadedListenerRemove](#hmssplashadloadedlistenerremove)             |
| [adClickListenerAdd](#hmssplashadclicklisteneradd)                     |
| [adClickListenerRemove](#hmssplashadclicklistenerremove)               |

##### `HMSSplash.adFailedToLoadListenerAdd()`

Adds listener for adFailedToLoad event. The listener function gets [Error](#error) as input.

`Parameters`

| Name | Type                         | Description        |
| ---- | ---------------------------- | ------------------ |
| fn   | (res: [Error](#error) )=> {} | Listener function. |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.adFailedToLoadListenerAdd((error) => {
  console.warn('HMSSplash adFailedToLoad, error: ', error);
});
```

##### `HMSSplash.adFailedToLoadListenerRemove()`

Removes listeners for adFailedToLoad event.

`Call Example`

```jsx
HMSSplash.adFailedToLoadListenerRemove();
```

##### `HMSSplash.adDismissedListenerAdd()`

Adds listener for adDismissed event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.adDismissedListenerAdd(() => {
  console.log('HMSSplash adDismissed');
});
```

##### `HMSSplash.adDismissedListenerRemove()`

Removes listeners for adDismissed event.

`Call Example`

```jsx
HMSSplash.adDismissedListenerRemove();
```

##### `HMSSplash.adShowedListenerAdd()`

Adds listener for adShowed event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.adShowedListenerAdd(() => {
  console.log('HMSSplash adShowed');
});
```

##### `HMSSplash.adShowedListenerRemove()`

Removes listeners for adShowed event.

`Call Example`

```jsx
HMSSplash.adShowedListenerRemove();
```

##### `HMSSplash.adLoadedListenerAdd()`

Adds listener for adLoaded event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.adLoadedListenerAdd(() => {
  console.log('HMSSplash adLoaded');
});
```

##### `HMSSplash.adLoadedListenerRemove()`

Removes listeners for adLoaded event.

`Call Example`

```jsx
HMSSplash.adLoadedListenerRemove();
```

##### `HMSSplash.adClickListenerAdd()`

Adds listener for adClick event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSSplash} from '@hmscore/react-native-hms-ads';

HMSSplash.adClickListenerAdd(() => {
  console.log('HMSSplash adClick');
});
```

##### `HMSSplash.adClickListenerRemove()`

Removes listeners for adClick event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
HMSSplash.adClickListenerRemove();
```

---

#### `Events of HMSReward`

| Event            | Description                                         |
| ---------------- | --------------------------------------------------- |
| `adFailedToLoad` | Event emitted when ad fails to load.                |
| `adFailedToShow` | Event emitted when ad fails to be displayed.        |
| `adClosed`       | Event emitted when ad is closed.                    |
| `adOpened`       | Event emitted when ad is opened.                    |
| `adLoaded`       | Event emitted when ad loads.                        |
| `adRewarded`     | Event emitted when a [reward](#reward) is provided. |

#### `Listener Functions of HMSReward`

| Functions                                                              |
| ---------------------------------------------------------------------- |
| [adFailedToLoadListenerAdd](#hmsrewardadfailedtoloadlisteneradd)       |
| [adFailedToLoadListenerRemove](#hmsrewardadfailedtoloadlistenerremove) |
| [adFailedToShowListenerAdd](#hmsrewardadfailedtoshowlisteneradd)       |
| [adFailedToShowListenerRemove](#hmsrewardadfailedtoshowlistenerremove) |
| [adClosedListenerAdd](#hmsrewardadclosedlisteneradd)                   |
| [adClosedListenerRemove](#hmsrewardadclosedlistenerremove)             |
| [adOpenedListenerAdd](#hmsrewardadopenedlisteneradd)                   |
| [adOpenedListenerRemove](#hmsrewardadopenedlistenerremove)             |
| [adLoadedListenerAdd](#hmsrewardadloadedlisteneradd)                   |
| [adLoadedListenerRemove](#hmsrewardadloadedlistenerremove)             |
| [adRewardedListenerAdd](#hmsrewardadrewardedlisteneradd)               |
| [adRewardedListenerRemove](#hmsrewardadrewardedlistenerremove)         |

##### `HMSReward.adFailedToLoadListenerAdd()`

Adds listener for adFailedToLoad event. The listener function gets [Error](#error) as input.

`Parameters`

| Name | Type                         | Description        |
| ---- | ---------------------------- | ------------------ |
| fn   | (res: [Error](#error) )=> {} | Listener function. |

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adFailedToLoadListenerAdd((error) => {
  console.warn('HMSReward adFailedToLoad, error: ', error);
});
```

##### `HMSReward.adFailedToLoadListenerRemove()`

Removes listeners for adFailedToLoad event.

`Call Example`

```jsx
HMSReward.adFailedToLoadListenerRemove();
```

##### `HMSReward.adFailedToShowListenerAdd()`

Adds listener for adFailedToShow event. The listener function gets [Error](#error) as input.

`Parameters`

| Name | Type                         | Description        |
| ---- | ---------------------------- | ------------------ |
| fn   | (res: [Error](#error) )=> {} | Listener function. |

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adFailedToShowListenerAdd((error) => {
  console.warn('HMSReward adFailedToShow, error: ', error);
});
```

##### `HMSReward.adFailedToShowListenerRemove()`

Removes listeners for adFailedToShow event.

`Call Example`

```jsx
HMSReward.adFailedToShowListenerRemove();
```

##### `HMSReward.adClosedListenerAdd()`

Adds listener for adClosed event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adClosedListenerAdd(() => {
  console.log('HMSReward adClosed');
});
```

##### `HMSReward.adClosedListenerRemove()`

Removes listeners for adClosed event.

`Call Example`

```jsx
HMSReward.adClosedListenerRemove();
```

##### `HMSReward.adOpenedListenerAdd()`

Adds listener for adOpened event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adOpenedListenerAdd(() => {
  console.log('HMSReward adOpened');
});
```

##### `HMSReward.adOpenedListenerRemove()`

Removes listeners for adOpened event.

`Call Example`

```jsx
HMSReward.adOpenedListenerRemove();
```

##### `HMSReward.adLoadedListenerAdd()`

Adds listener for adLoaded event. The listener function gets [RewardAd](#rewardad) as input.

Adds listener for adClosed event.

`Parameters`

| Name | Type                              | Description        |
| ---- | --------------------------------- | ------------------ |
| fn   | (res: [RewardAd](#rewardad))=> {} | Listener function. |

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adLoadedListenerAdd((rewardAd) => {
  console.log('HMSReward adLoaded, Reward ad: ', rewardAd);
});
```

##### `HMSReward.adLoadedListenerRemove()`

Removes listeners for adLoaded event.

`Call Example`

```jsx
HMSReward.adLoadedListenerRemove();
```

##### `HMSReward.adRewardedListenerAdd()`

Adds listener for adRewarded event.

`Parameters`

| Name | Type                          | Description        |
| ---- | ----------------------------- | ------------------ |
| fn   | (res: [Reward](#reward))=> {} | Listener function. |

`Call Example`

```jsx
import {HMSReward} from '@hmscore/react-native-hms-ads';

HMSReward.adRewardedListenerAdd((reward) => {
  console.log('HMSReward adRewarded, reward: ', reward);
});
```

##### `HMSReward.adRewardedListenerRemove()`

Removes listeners for adRewarded event.

`Call Example`

```jsx
HMSReward.adRewardedListenerRemove();
```

---

#### `Events of HMSInstallReferrer`

| Event                 | Description                                          |
| --------------------- | ---------------------------------------------------- |
| `serviceConnected`    | Event emitted when service connection is complete.   |
| `serviceDisconnected` | Event emitted when the service is crashed or killed. |

#### `Listener Functions of HMSInstallReferrer`

| Functions                                                                                 |
| ----------------------------------------------------------------------------------------- |
| [serviceConnectedListenerAdd](#hmsinstallreferrerserviceconnectedlisteneradd)             |
| [serviceConnectedListenerRemove](#hmsinstallreferrerserviceconnectedlistenerremove)       |
| [serviceDisconnectedListenerAdd](#hmsinstallreferrerservicedisconnectedlisteneradd)       |
| [serviceDisconnectedListenerRemove](#hmsinstallreferrerservicedisconnectedlistenerremove) |

##### `HMSInstallReferrer.serviceConnectedListenerAdd()`

Adds listener for serviceConnected event. The listener function gets [InstallReferrerResponse](#installreferrerresponse) as input.

`Parameters`

| Name | Type                                                            | Description        |
| ---- | --------------------------------------------------------------- | ------------------ |
| fn   | (res: [InstallReferrerResponse](#installreferrerresponse))=> {} | Listener function. |

`Call Example`

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.serviceConnectedListenerAdd((response) => {
  console.log('HMSInstallReferrer serviceConnected, response:', response);
});
```

##### `HMSInstallReferrer.serviceConnectedListenerRemove()`

Removes listeners for serviceConnected event.

`Call Example`

```jsx
HMSInstallReferrer.serviceConnectedListenerRemove();
```

##### `HMSInstallReferrer.serviceDisconnectedListenerAdd()`

Adds listener for serviceDisconnected event.

`Parameters`

| Name | Type    | Description        |
| ---- | ------- | ------------------ |
| fn   | ()=> {} | Listener function. |

`Call Example`

```jsx
import {HMSInstallReferrer} from '@hmscore/react-native-hms-ads';

HMSInstallReferrer.serviceDisconnectedListenerAdd(() => {
  console.log('HMSInstallReferrer serviceDisconnected');
});
```

##### `HMSInstallReferrer.serviceDisconnectedListenerRemove()`

Removes listeners for serviceDisconnected event.

`Call Example`

```jsx
HMSInstallReferrer.serviceDisconnectedListenerRemove();
```

### Constants

#### **`ConsentStatus`**

| Key                | Value | Definition                       |
| ------------------ | ----- | -------------------------------- |
| `PERSONALIZED`     | `0`   | Personalized consent option.     |
| `NON_PERSONALIZED` | `1`   | Non-personalized consent option. |
| `UNKNOWN`          | `2`   | Unknown consent option.          |

---

#### **`DebugNeedConsent`**

| Key                      | Value | Definition                       |
| ------------------------ | ----- | -------------------------------- |
| `DEBUG_DISABLED`         | `0`   | Disabled debug option.           |
| `DEBUG_NEED_CONSENT`     | `1`   | Consent-needed debug option.     |
| `DEBUG_NOT_NEED_CONSENT` | `2`   | Consent-not-needed debug option. |

---

#### **`AudioFocusType`**

Whether to obtain the audio focus during video playback.

| Key                              | Value | Definition                                                                                  |
| -------------------------------- | ----- | ------------------------------------------------------------------------------------------- |
| `GAIN_AUDIO_FOCUS_ALL`           | `0`   | Obtain the audio focus when a video is played, no matter whether the video is muted.        |
| `NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE` | `1`   | Obtain the audio focus only when a video is played without being muted.                     |
| `NOT_GAIN_AUDIO_FOCUS_ALL`       | `2`   | Do not obtain the audio focus when a video is played, no matter whether the video is muted. |

---

#### **`ContentClassification`**

Ad content rating.

| Key                                | Value  | Definition                                          |
| ---------------------------------- | ------ | --------------------------------------------------- |
| `AD_CONTENT_CLASSIFICATION_W`      | `'W'`  | Content suitable for toddlers and older audiences.  |
| `AD_CONTENT_CLASSIFICATION_PI`     | `'PI'` | Content suitable for kids and older audiences.      |
| `AD_CONTENT_CLASSIFICATION_J`      | `'J'`  | Content suitable for teenagers and older audiences. |
| `AD_CONTENT_CLASSIFICATION_A`      | `'A'`  | Content suitable only for adults.                   |
| `AD_CONTENT_CLASSIFICATION_UNKOWN` | `''`   | Unknown rating.                                     |

---

#### **`Gender`**

Gender.

| Key       | Value | Definition      |
| --------- | ----- | --------------- |
| `UNKNOWN` | `0`   | Unknown gender. |
| `MALE`    | `1`   | Male.           |
| `FEMALE`  | `2`   | Female.         |

---

#### **`NonPersonalizedAd`**

Whether to request only non-personalized ads.

| Key                      | Value | Definition                                          |
| ------------------------ | ----- | --------------------------------------------------- |
| `ALLOW_ALL`              | `0`   | Request both personalized and non-personalized ads. |
| `ALLOW_NON_PERSONALIZED` | `1`   | Request only non-personalized ads.                  |

---

#### **`TagForChild`**

Child-directed setting.

| Key                                    | Value | Definition                                                                                    |
| -------------------------------------- | ----- | --------------------------------------------------------------------------------------------- |
| `TAG_FOR_CHILD_PROTECTION_FALSE`       | `0`   | Do not process ad requests according to the Children’s Online Privacy Protection Act (COPPA). |
| `TAG_FOR_CHILD_PROTECTION_TRUE`        | `1`   | Process ad requests according to the COPPA.                                                   |
| `TAG_FOR_CHILD_PROTECTION_UNSPECIFIED` | `-1`  | Whether to process ad requests according to the COPPA is not specified.                       |

---

#### **`UnderAge`**

Setting directed to users under the age of consent.

| Key                   | Value | Definition                                                                                     |
| --------------------- | ----- | ---------------------------------------------------------------------------------------------- |
| `PROMISE_FALSE`       | `0`   | Do not process ad requests as directed to users under the age of consent.                      |
| `PROMISE_TRUE`        | `1`   | Process ad requests as directed to users under the age of consent.                             |
| `PROMISE_UNSPECIFIED` | `-1`  | Whether to process ad requests as directed to users under the age of consent is not specified. |

---

#### **`NativeAdAssetNames`**

Constant IDs of all native ad components.

| Key                 | Value | Definition                                                  |
| ------------------- | ----- | ----------------------------------------------------------- |
| `TITLE`             | `1`   | Title material ID.                                          |
| `CALL_TO_ACTION`    | `2`   | Material ID of the action text to be displayed on a button. |
| `ICON`              | `3`   | Icon material ID.                                           |
| `DESC`              | `4`   | Description material ID.                                    |
| `AD_SOURCE`         | `5`   | Advertiser material ID.                                     |
| `IMAGE`             | `8`   | Image material ID.                                          |
| `MEDIA_VIDEO`       | `10`  | Multimedia view material ID.                                |
| `CHOICES_CONTAINER` | `11`  | Ad choice material ID.                                      |

---

#### **`ChoicesPosition`**

Choice icon position constants.

| Key            | Value | Definition    |
| -------------- | ----- | ------------- |
| `TOP_LEFT`     | `0`   | Top left.     |
| `TOP_RIGHT`    | `1`   | Top right.    |
| `BOTTOM_RIGHT` | `2`   | Bottom right. |
| `BOTTOM_LEFT`  | `3`   | Bottom left.  |
| `INVISIBLE`    | `4`   | Invisible.    |

---

#### **`Direction`**

Orientation constant.

| Key         | Value | Definition       |
| ----------- | ----- | ---------------- |
| `ANY`       | `0`   | Any orientation. |
| `PORTRAIT`  | `1`   | Portrait.        |
| `LANDSCAPE` | `2`   | Landscape.       |

---

#### **`ScaleType`**

[Options](https://developer.android.com/reference/android/widget/ImageView.ScaleType) for scaling the bounds of an image.

| Key             | Value |
| --------------- | ----- |
| `MATRIX`        | `0`   |
| `FIT_XY`        | `1`   |
| `FIT_START`     | `2`   |
| `FIT_CENTER`    | `3`   |
| `FIT_END`       | `4`   |
| `CENTER`        | `5`   |
| `CENTER_CROP`   | `6`   |
| `CENTER_INSIDE` | `7`   |

---

#### **`BannerAdSizes`**

| Key                   | Value                | Definition                                                                                        |
| --------------------- | -------------------- | ------------------------------------------------------------------------------------------------- |
| `B_160_600`           | `'160_600'`          | 160 x 600 dp banner ad size.                                                                      |
| `B_300_250`           | `'300_250'`          | 300 x 250 dp banner ad size.                                                                      |
| `B_320_50`            | `'320_50'`           | 320 x 50  dp banner ad size.                                                                      |
| `B_320_100`           | `'320_100'`          | 320 x 100 dp banner ad size.                                                                      |
| `B_360_57`            | `'360_57'`           | 360 x 57 dp banner ad size.                                                                       |
| `B_360_144`           | `'360_144'`          | 360 x 144 dp banner ad size.                                                                      |
| `B_468_60`            | `'468_60'`           | 468 x 60 dp banner ad size.                                                                       |
| `B_728_90`            | `'728_90'`           | 728 x 90 dp banner ad size.                                                                       |
| `B_CURRENT_DIRECTION` | `'currentDirection'` | Banner ad size based on a based on the current device orientation and a custom width.             |
| `B_PORTRAIT`          | `'portrait'`         | Banner ad size based on a custom width in portrait  orientation.                                  |
| `B_SMART`             | `'smart'`            | Dynamic banner ad size. The screen width and an adaptive height are used.                         |
| `B_DYNAMIC`           | `'dynamic'`          | Dynamic banner ad size. The width of the parent layout and the height of the ad content are used. |
| `B_LANDSCAPE`         | `'landscape'`        | Banner ad size based on a custom width in landscape orientation.                                  |
| `B_INVALID`           | `'invalid'`          | Invalid size. No ad can be requested using this size.                                             |

---

#### **`NativeMediaTypes`**

Native ad media types.

| Key           | Value           | Definition           |
| ------------- | --------------- | -------------------- |
| `VIDEO`       | `'video'`       | Ad with video.       |
| `IMAGE_SMALL` | `'image_small'` | Ad with small image. |
| `IMAGE_LARGE` | `'image_large'` | Ad with large image. |

---

#### **`BannerMediaTypes`**

Banner ad media types.

| Key     | Value     | Definition     |
| ------- | --------- | -------------- |
| `IMAGE` | `'image'` | Ad with image. |

---

#### **`InterstitialMediaTypes`**

Interstitial ad media types.

| Key     | Value     | Definition     |
| ------- | --------- | -------------- |
| `VIDEO` | `'video'` | Ad with video. |
| `IMAGE` | `'image'` | Ad with image. |

---

#### **`RewardMediaTypes`**

Reward ad media types.

| Key     | Value     | Definition     |
| ------- | --------- | -------------- |
| `VIDEO` | `'video'` | Ad with video. |

---

#### **`SplashMediaTypes`**

Splash ad media types.

| Key     | Value     | Definition     |
| ------- | --------- | -------------- |
| `VIDEO` | `'video'` | Ad with video. |
| `IMAGE` | `'image'` | Ad with image. |

---

#### **`CallMode`**

Option for functions that can use Huawei SDK or [Aidl](https://developer.android.com/guide/components/aidl) service.

| Key    | Value    | Definition                                                                    |
| ------ | -------- | ----------------------------------------------------------------------------- |
| `SDK`  | `'sdk'`  | Use Huawei Ads SDK .                                                          |
| `AIDL` | `'aidl'` | (This option will not be used anymore, please prefer `SDK`) Use aidl service. |

### DataTypes

#### **`Reward`**

Information about the reward item in a rewarded ad.

| Parameter | Type      | Definition                  |
| --------- | --------- | --------------------------- |
| `name`    | `string`  | The name of a reward item.  |
| `amount`  | `integer` | The number of reward items. |

---

#### **`RewardAd`**

Rewarded ad.

| Parameter  | Type                | Definition                                          |
| ---------- | ------------------- | --------------------------------------------------- |
| `userId`   | `string`            | User id.                                            |
| `data`     | `string`            | Custom data.                                        |
| `reward`   | [`Reward`](#reward) | Reward item.                                        |
| `isLoaded` | `boolean`           | Shows whether a rewarded ad is successfully loaded. |

---

#### **`InterstitialAd`**

Interstitial ad.

| Parameter   | Type      | Definition                            |
| ----------- | --------- | ------------------------------------- |
| `adId`      | `string`  | The ad slot id.                       |
| `isLoaded`  | `boolean` | Shows whether ad loading is complete. |
| `isLoading` | `boolean` | Shows whether ads are being loaded.   |

---

#### **`DislikeAdReason`**

Obtains the reason why a user dislikes an ad.

| Parameter     | Type     | Definition                            |
| ------------- | -------- | ------------------------------------- |
| `description` | `string` | The reason why a user dislikes an ad. |

---

#### **`VideoOperator`**

Video controller, which implements video control such as playing, pausing, and muting a video

| Parameter                    | Type      | Definition                                                        |
| ---------------------------- | --------- | ----------------------------------------------------------------- |
| `aspectRatio`                | `number`  | The video aspect ratio.                                           |
| `hasVideo`                   | `boolean` | Shows whether ad content contains a video.                        |
| `isCustomizeOperateEnabled`  | `boolean` | Shows whether a custom video control is used for a video ad.      |
| `isClickToFullScreenEnabled` | `boolean` | Shows whether click to full screen option enabled for a video ad. |
| `isMuted`                    | `boolean` | Shows whether a video is muted.                                   |

---

#### **`NativeAd`**

Native ad.

| Parameter                      | Type                                    | Definition                                                                  |
| ------------------------------ | --------------------------------------- | --------------------------------------------------------------------------- |
| `adSource`                     | `string`                                | Ad source.                                                                  |
| `description`                  | `string`                                | Ad description.                                                             |
| `callToAction`                 | `string`                                | The text to be displayed on a button, for example, View Details or Install. |
| `dislikeAdReasons`             | [`DislikeAdReason[]`](#dislikeadreason) | The choices of not displaying the current ad.                               |
| `title`                        | `string`                                | Ad title.                                                                   |
| `videoOperator`                | [`VideoOperator`](#videooperator)       | Video operator used for the ad.                                             |
| `isCustomClickAllowed`         | `boolean`                               | Shows whether custom tap gestures are enabled.                              |
| `isCustomDislikeThisAdEnabled` | `boolean`                               | Shows whether custom ad closing is enabled.                                 |

---

#### **`AdProvider`**

Ad provider.

| Parameter          | Type     | Definition                                        |
| ------------------ | -------- | ------------------------------------------------- |
| `id`               | `string` | Id of ad provider.                                |
| `name`             | `string` | Name of ad provider.                              |
| `privacyPolicyUrl` | `string` | The url for privacy policy.                       |
| `serviceArea`      | `string` | The service area for ad (ex: 'Global' or 'Asia'). |

---

#### **`AdSize`**

Ad size.

| Parameter | Type      | Definition        |
| --------- | --------- | ----------------- |
| `height`  | `integer` | Ad height, in dp. |
| `width`   | `integer` | Ad width, in dp.  |

---

#### **`BannerAdSize`**

Banner ad size.

| Parameter          | Type      | Definition                                 |
| ------------------ | --------- | ------------------------------------------ |
| `height`           | `integer` | Ad height, in dp.                          |
| `width`            | `integer` | Ad width, in dp.                           |
| `heightPx`         | `integer` | Ad height, in pixels.                      |
| `widthPx`          | `integer` | Ad width, in pixels.                       |
| `isAutoHeightSize` | `boolean` | Shows whether an adaptive height is used.  |
| `isDynamicSize`    | `boolean` | Shows whether a dynamic size is used.      |
| `isFullWidthSize`  | `boolean` | Shows whether a full-screen width is used. |

---

#### **`VideoConfiguration`**

Video configuration used to control video playback.

| Parameter                     | Type      | Definition                                                                                                                             |
| ----------------------------- | --------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| `audioFocusType`              | `integer` | The video playback scenario where the audio focus needs to be obtained. Check [`AudioFocusType`](#audiofocustype) for possible values. |
| `isCustomizeOperateRequested` | `boolean` | The setting for using custom video control.                                                                                            |
| `isStartMuted`                | `boolean` | The setting for muting video when it starts.                                                                                           |

---

#### **`NativeAdConfiguration`**

Native Ad configuration.

| Parameter               | Type                                        | Definition                                                                                      |
| ----------------------- | ------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| `adSize`                | [`AdSize`](#adsize)                         | Ad size.                                                                                        |
| `choicesPosition`       | `integer`                                   | Position of an ad choice icon. Check [`ChoicesPosition`](#choicesposition) for possible values. |
| `mediaDirection`        | `integer`                                   | Direction of an ad image.  Check [`Direction`](#direction) for possible values.                 |
| `mediaAspect`           | `integer`                                   | Aspect ratio of an ad image.                                                                    |
| `videoConfiguration`    | [`VideoConfiguration`](#videoconfiguration) | Video Configuration.                                                                            |
| `isRequestMultiImages`  | `boolean`                                   | The setting for requesting multiple ad images.                                                  |
| `isReturnUrlsForImages` | `boolean`                                   | The setting for enabling the SDK to download native ad images.                                  |

---

#### **`NativeAdLoader`**

Native Ad loader.

| Parameter   | Type      | Definition                          |
| ----------- | --------- | ----------------------------------- |
| `isLoading` | `boolean` | Shows whether ads are being loaded. |

---

#### **`AdvertisingIdClientInfo`**

Information about advertised clients.

| Parameter                  | Type      | Definition                   |
| -------------------------- | --------- | ---------------------------- |
| `id`                       | `string`  | The OAID.                    |
| `isLimitAdTrackingEnabled` | `boolean` | 'Limit ad tracking' setting. |

---

#### **`ReferrerDetails`**

Describes the install referrer information.

| Parameter                           | Type     | Definition                                       |
| ----------------------------------- | -------- | ------------------------------------------------ |
| `installReferrer`                   | `string` | Install referrer information.                    |
| `installBeginTimestampMillisecond`  | `number` | The app installation timestamp, in milliseconds. |
| `installBeginTimestampSeconds`      | `number` | The app installation timestamp, in seconds.      |
| `referrerClickTimestampMillisecond` | `number` | The ad click timestamp, in milliseconds.         |
| `referrerClickTimestampSeconds`     | `number` | The ad click timestamp, in seconds.              |

---

#### **`RequestOptions`**

Ad request options.

| Parameter                 | Type      | Definition                                                                                                            |
| ------------------------- | --------- | --------------------------------------------------------------------------------------------------------------------- |
| `adContentClassification` | `string`  | Ad content rating. Check [`ContentClassification`](#contentclassification) for possible values.                       |
| `appCountry`              | `string`  | The country for the app.                                                                                              |
| `appLang`                 | `string`  | The language for the app.                                                                                             |
| `nonPersonalizedAd`       | `integer` | The setting for requesting non-personalized ads. Check [`NonPersonalizedAd`](#nonpersonalizedad) for possible values. |
| `tagForChildProtection`   | `integer` | The child-directed setting. Check [`TagForChild`](#tagforchild) for possible values.                                  |
| `tagForUnderAgeOfPromise` | `integer` | The setting directed to users under the age of consent. Check [`UnderAge`](#underage) for possible values.            |

---

#### **`Consent`**

Ad consent object to be submitted.

| Parameter           | Type      | Definition                                    |
| ------------------- | --------- | --------------------------------------------- |
| `consentStatus`     | `integer` | [ConsentStatus](#consentstatus) option.       |
| `debugNeedConsent`  | `integer` | [DebugNeedConsent](#debugneedconsent) option. |
| `underAgeOfPromise` | `integer` | [UnderAge](#underage) option.                 |
| `testDeviceId`      | `string`  | Device Id.                                    |

---

#### **`ConsentResult`**

Consent information from api result

| Parameter       | Type                          | Definition                       |
| --------------- | ----------------------------- | -------------------------------- |
| `consentStatus` | `integer`                     | Status of consent.               |
| `isNeedConsent` | `boolean`                     | Shows whether consent is needed. |
| `adProviders`   | [`AdProvider[]`](#adprovider) | Ad provider list.                |

---

#### **`BannerResult`**

Banner information from banner load event

| Parameter      | Type                            | Definition                       |
| -------------- | ------------------------------- | -------------------------------- |
| `adId`         | `string`                        | Ad slot id.                      |
| `isLoading`    | `boolean`                       | Shows whether banner is loading. |
| `bannerAdSize` | [`BannerAdSize`](#banneradsize) | BannerAdSize information.        |

---

#### **`NativeResult`**

Native ad information from native ad load event.

| Parameter               | Type                                              | Definition                           |
| ----------------------- | ------------------------------------------------- | ------------------------------------ |
| `nativeAd`              | [`NativeAd`](#nativead)                           | Native ad information.               |
| `nativeAdConfiguration` | [`NativeAdConfiguration`](#nativeadconfiguration) | Native ad configuration information. |
| `nativeAdLoader`        | [`NativeAdLoader`](#nativeadloader)               | Native ad loader information.        |

---

#### **`Error`**

Ad error.

| Parameter      | Type      | Definition     |
| -------------- | --------- | -------------- |
| `errorCode`    | `integer` | Error code.    |
| `errorMessage` | `string`  | Error message. |

---

#### **`InstallReferrerResponse`**

Install referrer connection response.

| Parameter         | Type      | Definition        |
| ----------------- | --------- | ----------------- |
| `responseCode`    | `integer` | Response code.    |
| `responseMessage` | `string`  | Response message. |

---

#### **`AdParam`**

Ad request parameters.

| Parameter                 | Type      | Definition                                                                                                                              |
| ------------------------- | --------- | --------------------------------------------------------------------------------------------------------------------------------------- |
| `adContentClassification` | `string`  | Ad content rating. Check [`ContentClassification`](#contentclassification) for possible values.                                         |
| `appCountry`              | `string`  | Country code corresponding to the language in which an ad needs to be returned for an app.                                              |
| `appLang`                 | `string`  | Language in which an ad needs to be returned for an app.                                                                                |
| `belongCountryCode`       | `string`  | Home country code.                                                                                                                      |
| `gender`                  | `integer` | Gender.  Check [`Gender`](#gender) for possible values.                                                                                 |
| `nonPersonalizedAd`       | `integer` | The setting of requesting personalized ads. Check [`NonPersonalizedAd`](#nonpersonalizedad) for possible values.                        |
| `requestOrigin`           | `string`  | Origin of request.                                                                                                                      |
| `tagForChildProtection`   | `integer` | The setting of processing ad requests according to the COPPA. Check [`TagForChild`](#tagforchild) for possible values.                  |
| `tagForUnderAgeOfPromise` | `integer` | The setting of processing ad requests as directed to users under the age of consent. Check [`UnderAge`](#underage) for possible values. |
| `targetingContentUrl`     | `string`  | Targeting content url.                                                                                                                  |

---

#### **`VerifyConfig`**

Server-side verification parameter.

| Parameter | Type      | Definition   |
| --------- | --------- | ------------ |
| `userId`  | `integer` | User Id.     |
| `data`    | `string`  | Custom data. |

---

#### **`AdTextStyle`**

| Parameter         | Type      | Definition        |
| ----------------- | --------- | ----------------- |
| `fontSize`        | `number`  | Font size.        |
| `color`           | `integer` | Color.            |
| `backgroundColor` | `integer` | Background color. |
| `visibility`      | `boolean` | Visibility.       |

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
