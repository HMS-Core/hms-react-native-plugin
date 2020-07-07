# react-native-hms-ads

# Contents

1. Introduction
2. Installation Guide
3. Function Definitions
4. Configuration & Description
5. Licencing & Terms

## 1. Intruduction

This module enables communication between Huawei Ads SDK and React Native platform. It exposes all functionality provided by Huawei Ads SDK.

## 2. Installation Guide

- Download the module and copy it into 'node_modules' folder. The folder structure can be seen below;

```text
project-name
    |_ node_modules
        |_ ...
        |_ react-native-hms-ads
        |_ ...
```

- Add following lines into 'android/settings.gradle' file

```gradle
include ':react-native-hms-ads'
project(':react-native-hms-ads').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-hms-ads/android')
```

- Add maven repository address and AppGallery Connect service dependencies into 'android/build.gradle' file.

```groovy
maven {url 'https://developer.huawei.com/repo/'}
classpath 'com.huawei.agconnect:agcp:1.2.1.301'
```

- Add AppGallery Connect plugin and 'react-native-hms-ads' dependency into 'android/app/build.gradle' file.

```groovy
apply plugin: 'com.huawei.agconnect'
implementation project(":react-native-hms-ads")
```

- Download 'agconnect-services.json' file and put it under 'android/app' folder.

- Put keystore file under 'android/app' folder. Add signing configuration into 'android/app/build.gradle' file.

```groovy
signingConfigs {
        release {
            storeFile file('<keystore>')
            storePassword '<storePassword>'
            keyAlias '<keyAlias>'
            keyPassword '<keyPassword>'
        }

        debug {
            storeFile file('<keystore>')
            storePassword '<storePassword>'
            keyAlias '<keyAlias>'
            keyPassword '<keyPassword>'
        }
    }
    buildTypes {
        debug {
            signingConfig signingConfigs.debug
        }
        release {
            signingConfig signingConfigs.release
            minifyEnabled enableProguardInReleaseBuilds
            proguardFiles getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
        }
    }
```

- Add 'RNHMSAdsPackage' to your application.

```java
import com.huawei.hms.rn.ads.RNHMSAdsPackage;
...
...

@Override
protected List<ReactPackage> getPackages() {
  @SuppressWarnings("UnnecessaryLocalVariable")
  List<ReactPackage> packages = new PackageList(this).getPackages();
  packages.add(new RNHMSAdsPackage());
  return packages;
}
```

- Run the app by executing following command.

```bash
react-native run-android
```

## 3. Function Definitions

## Components

### `HMSBanner`

<details open><summary>Show Details</summary>

Banner ad component

```jsx
import {
  HMSBanner,
  BannerAdSizes,
} from 'react-native-hms-ads';

// Simple example
<HMSBanner
  style={{height: 100}}
  bannerAdSize={{
    bannerAdSize:BannerAdSizes.B_320_100,
  }}
  adId="testw6vs28auh3"
/>
```

`Properties`

| Prop | Type | Definition |
|-----------|--------------------|-----------------------------|
|`bannerAdSize` | [`BannerAdSizeProp`](#banneradsizeprop)  | The object  parameter that has banner size information. It can have width information if needed.|
|`adId` | `string` | Ad slot id.|
|`adParam` | [`AdParam`](#adparam) | Ad request parameter.|
|`onAdLoaded` | `function` | The function to handle the event when ad loads. It gets event information object as argument which has `nativeEvent` as key and [`BannerResult`](#bannerresult) object as value. |
|`onAdFailed` | `function` | The function to handle the event when ad fails to load. It gets event information object as argument which has `nativeEvent` as key and [`Error`](#error) object as value.|
|`onAdOpened` | `function` | The function to handle the event when ad is opened.|
|`onAdClicked` | `function` | The function to handle the event when ad is clicked.|
|`onAdClosed` | `function` | The function to handle the event when ad is closed.|
|`onAdImpression` | `function` | The function to handle the event when ad impression is detected.|
|`onAdLeave` | `function` | The function to handle the event when user leaves the app.|

---

#### **`BannerAdSizeProp`**

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`bannerAdSize` | `string` | Banner ad sizes.  Check [`BannerAdSizes`](#banneradsizes) for possible values. |
|`width` | `integer` | If banner ad size is `'portrait'`, or `'landscape'`, or `'currentDirection'`, this will be needed to set the width of the banner .|

`Commands`

Commands can be used with component references which are created with ref prop of React component.

```jsx
import {
  HMSBanner,
  BannerAdSizes,
} from 'react-native-hms-ads';

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

```jsx
adBannerElement.loadAd()
```

b) `setRefresh(number)`

Sets a rotation interval for banner ads. Input is rotation interval, in seconds. It should range from 30 to 120.

```jsx
adBannerElement.setRefresh(60)
```

c) `pause()`

Pauses any additional processing related to ad.

```jsx
adBannerElement.pause()
```

f) `resume()`

Resumes ad after the pause() method is called last time.

```jsx
adBannerElement.resume()
```

e) `destroy()`

Destroys ad.

```jsx
adBannerElement.destroy()
```

`Complex example`

```jsx
import {
  HMSBanner,
  BannerAdSizes,
  ContentClassification,
  Gender,
  NonPersonalizedAd,
  TagForChild,
  UnderAge,
} from 'react-native-hms-ads';

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

</details>

---

### `HMSNative`

<details open><summary>Show Details</summary>

Native ad component

```jsx
import {
  HMSNative,
  NativeMediaTypes,
} from 'react-native-hms-ads';

// Simple example
<HMSNative
  style={{ height: 322 }}
  displayForm={{
    mediaType: NativeMediaTypes.VIDEO,
    adId: 'testy63txaom86'
  }}
/>
```

`Properties`

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`displayForm` | [`DisplayFormProp`](#displayformprop)  | The object parameter that has ad slot id and media type information.|
|`adParam` | [`AdParam`](#adparam) | Ad request parameter.|
|`nativeConfig` | [`NativeAdConfiguration`](#nativeadconfiguration) | Native ad configuration parameter.|
|`viewOptions` | [`ViewOptionsProp`](#viewoptionsprop) | View options parameter.|
|`onNativeAdLoaded` | `function` | The function to handle the event when ad loads. It gets event information object as argument which has `nativeEvent` as key and [`NativeResult`](#nativeresult) object as value.|
|`onAdDisliked` | `function` | The function to handle the event when ad is disliked.|
|`onAdFailed` | `function` | The function to handle the event when ad fails to load. It gets event information object as argument which has `nativeEvent` as key and [`Error`](#error) object as value.|
|`onAdClicked` | `function` | The function to handle the event when ad is clicked.|
|`onAdImpression` | `function` | The function to handle the event when ad impression is detected.|
|`onVideoStart` | `function` | The function to handle the event when ad video starts playing.|
|`onVideoPlay` | `function` | The function to handle the event when ad video plays.|
|`onVideoEnd` | `function` | The function to handle the event when ad video ends.|

---

#### **`DisplayFormProp`**

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`mediaType` | `string` | Media type of the native ad. Check [`NativeMediaTypes`](#nativemediatypes) for possible values.|
|`adId` | `string` | Ad slot id.|

---

#### **`ViewOptionsProp`**

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`showMediaContent` | `boolean` | The option for showing media content.|
|`mediaImageScaleType` | `integer` | The image scale type. Check [`ScaleType`](#scaletype) for possible values|
|`adSourceTextStyle` | [`AdTextStyle`](#adtextstyle) | The style of ad source.|
|`adFlagTextStyle` | [`AdTextStyle`](#adtextstyle) | The style of ad flag.|
|`titleTextStyle` | [`AdTextStyle`](#adtextstyle) | The style of ad title.|
|`descriptionTextStyle` | [`AdTextStyle`](#adtextstyle) | The style of ad description.|
|`callToActionStyle` | [`AdTextStyle`](#adtextstyle) | The style of ad call-to-action button.|

`Commands`

Commands can be used with component references which are created with ref prop of React component.

```jsx
import {
  HMSNative,
  NativeMediaTypes,
} from 'react-native-hms-ads';

// Example for commands
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

```jsx
adNativeElement.loadAd()
```

b) `dislikeAd(string)`

Dislikes ad with description.

```jsx
adNativeElement.dislikeAd('Just dont like it')
```

c) `destroy()`

Destroys ad.

```jsx
adNativeElement.destroy()
```

d) `gotoWhyThisAdPage()`

Goes to the page explaining why an ad is displayed.

```jsx
adNativeElement.gotoWhyThisAdPage()
```

e) `setAllowCustomClick()`

Enables custom tap gestures.

```jsx
adNativeElement.setAllowCustomClick()
```

f) `recordClickEvent()`

Reports a custom tap gesture.

```jsx
adNativeElement.recordClickEvent()
```

g) `recordImpressionEvent(object)`

Reports an ad impression.

```jsx
adNativeElement.recordImpressionEvent({myKey: 'myValue', yourKey:{ cool: true}})
```

`Complex example`

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
} from 'react-native-hms-ads';

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

</details>

## Modules

  <details open><summary>Show Details</summary>

- ### `HMSAds`
  
  - Methods
    - [`init()`](#hmsadsinit)
    - [`getSDKVersion()`](#hmsadsgetsdkversion)
    - [`setVideoMuted(boolean)`](#hmsadssetvideomutedboolean)
    - [`setVideoVolume(number)`](#hmsadssetvideovolumenumber)
    - [`setRequestOptions(`](#hmsadssetrequestoptionsrequestoptions)[`RequestOptions`](#requestoptions)`)`
    - [`getRequestOptions()`](#hmsadsgetrequestoptions)
    - [`setConsent(`](#hmsadsgetrequestoptions)[`Consent`](#consent)`)`
    - [`checkConsent()`](#hmsadscheckconsent)

- ### `HMSOaid`

  - Methods
    - [`verifyAdvertisingId(`](#hmsoaidverifyadvertisingidadvertisingidclientinfo)[`AdvertisingIdClientInfo`](#advertisingidclientinfo)`)`
    - [`getAdvertisingIdInfo(string)`](#hmsoaidgetadvertisingidinfostring)
  
- ### `HMSReward`

  - Methods
    - [`setAdParam(`](#hmsrewardsetadparamadparam)[`AdParam`](#adparam)`)`
    - [`setAdId(string)`](#hmsrewardsetadidstring)
    - [`setData(string)`](#hmsrewardsetdatastring)
    - [`setUserId(string)`](#hmsrewardsetuseridstring)
    - [`pause()`](#hmsrewardpause)
    - [`resume()`](#hmsrewardresume)
    - [`setVerifyConfig(`](#hmsrewardsetverifyconfigverifyconfig)[`VerifyConfig`](#verifyconfig)`)`
    - [`loadAd()`](#hmsrewardloadad)
    - [`show()`](#hmsrewardshow)
    - [`isLoaded()`](#hmsrewardisloaded)
    - [`allListenersRemove()`](#hmsrewardalllistenersremove)
  
  - Events
    - [`adLoaded`](#hmsrewardadloadedlisteneraddfunction)
    - [`adFailedToLoad`](#hmsrewardadfailedtoloadlisteneraddfunction)
    - [`adFailedToShow`](#hmsrewardadfailedtoshowlisteneraddfunction)
    - [`adOpened`](#hmsrewardadopenedlisteneraddfunction)
    - [`adClosed`](#hmsrewardadclosedlisteneraddfunction)
    - [`adRewarded`](#hmsrewardadrewardedlisteneraddfunction)

- ### `HMSInterstitial`

  - Methods
    - [`setAdParam(`](#hmsinterstitialsetadparamadparam)[`AdParam`](#adparam)`)`
    - [`setAdId(string)`](#hmsinterstitialsetadidstring)
    - [`loadAd()`](#hmsinterstitialloadad)
    - [`show()`](#hmsinterstitialshow)
    - [`isLoaded()`](#hmsinterstitialisloaded)
    - [`allListenersRemove()`](#hmsinterstitialalllistenersremove)

  - Events
    - [`adLoaded`](#hmsinterstitialadloadedlisteneraddfunction)
    - [`adFailed`](#hmsinterstitialadfailedlisteneraddfunction)
    - [`adLeave`](#hmsinterstitialadleavelisteneraddfunction)
    - [`adOpened`](#hmsinterstitialadopenedlisteneraddfunction)
    - [`adClosed`](#hmsinterstitialadclosedlisteneraddfunction)
    - [`adClicked`](#hmsinterstitialadclickedlisteneraddfunction)
    - [`adImpression`](#hmsinterstitialadimpressionlisteneraddfunction)

- ### `HMSSplash`

  - Methods
    - [`setAdParam(`](#hmssplashsetadparamadparam)[`AdParam`](#adparam)`)`
    - [`setAdId(string)`](#hmssplashsetadidstring)
    - [`setLogoText(string)`](#hmssplashsetlogotextstring)
    - [`setCopyrightText(string)`](#hmssplashsetcopyrighttextstring)
    - [`setOrientation(integer)`](#hmssplashsetorientationinteger)
    - [`setSloganResource(string)`](#hmssplashsetsloganresourcestring)
    - [`setWideSloganResource(string)`](#hmssplashsetwidesloganresourcestring)
    - [`setLogoResource(string)`](#hmssplashsetlogoresourcestring)
    - [`setMediaNameResource(string)`](#hmssplashsetmedianameresourcestring)
    - [`setAudioFocusType(integer)`](#hmssplashsetaudiofocustypeinteger)
    - [`pause()`](#hmssplashpause)
    - [`resume()`](#hmssplashresume)
    - [`destroy()`](#hmssplashdestroy)
    - [`isLoading()`](#hmssplashisloading)
    - [`isLoaded()`](#hmssplashisloaded)
    - [`show()`](#hmssplashshow)
    - [`allListenersRemove()`](#hmssplashalllistenersremove)

  - Events
    - [`adLoaded`](#hmssplashadloadedlisteneraddfunction)
    - [`adFailedToLoad`](#hmssplashadfailedtoloadlisteneraddfunction)
    - [`adDismissed`](#hmssplashaddismissedlisteneraddfunction)
    - [`adShowed`](#hmssplashadshowedlisteneraddfunction)
    - [`adClick`](#hmssplashadclicklisteneraddfunction)
  
- ### `HMSInstallReferrer`

  - Methods
    - [`startConnection(string, boolean, string)`](#hmsinstallreferrerstartconnectionstring-boolean-string)
    - [`endConnection()`](#hmsinstallreferrerendconnection)
    - [`getReferrerDetails()`](#hmsinstallreferrergetreferrerdetails)
    - [`isReady()`](#hmsinstallreferrerisready)
    - [`allListenersRemove()`](#hmsinstallreferreralllistenersremove)

  - Events
    - [`serviceConnected`](#hmsinstallreferrerserviceconnectedlisteneraddfunction)
    - [`serviceDisconnected`](#hmsinstallreferrerservicedisconnectedlisteneraddfunction)

</details>

## Methods

<details open><summary>HMSAds</summary>

### `HMSAds.init()`

Initializes the HUAWEI Ads SDK. The function returns a promise that resolves a string 'Hw Ads Initialized'.

```jsx
import HMSAds from 'react-native-hms-ads';

HMSAds.init()
  .then((result) => console.log('HMSAds init, result:', result))
```

### `HMSAds.getSDKVersion()`

Obtains the version number of the HUAWEI Ads SDK. The function returns a promise that resolves a string of the version number.

```jsx
import HMSAds from 'react-native-hms-ads';

HMSAds.getSDKVersion()
  .then((result) => console.log('HMS getSDKVersion, result:', result));
```

### `HMSAds.setVideoMuted(boolean)`

Un/mute videos.

```jsx
import HMSAds from 'react-native-hms-ads';

HMSAds.setVideoMuted(true);
```

### `HMSAds.setVideoVolume(number)`

Sets video volume.

```jsx
import HMSAds from 'react-native-hms-ads';

HMSAds.setVideoVolume(100);
```

### `HMSAds.setRequestOptions(`[`RequestOptions`](#requestoptions)`)`

Provides the global ad request configuration. The function returns a promise that resolves a [`RequestOptions`](#requestoptions) object.

```jsx
import HMSAds, {
  ContentClassification,
  NonPersonalizedAd,
  TagForChild,
  UnderAge,
} from 'react-native-hms-ads';

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

### `HMSAds.getRequestOptions()`

Obtains the global request configuration. The function returns a promise that resolves a [`RequestOptions`](#requestoptions) object.

```jsx
import HMSAds from 'react-native-hms-ads';

HMSAds.getRequestOptions()
  .then((result) => console.log('HMS getRequestOptions, result:', result))
```

### `HMSAds.setConsent(`[`Consent`](#consent)`)`

Provides ad consent configuration. The function returns a promise that resolves a [`ConsentResult`](#consentresult) object.

```jsx
import HMSAds, {
  ConsentStatus,
  DebugNeedConsent,
  UnderAge,
} from 'react-native-hms-ads';

HMSAds.setConsent({
  consentStatus: ConsentStatus.NON_PERSONALIZED,
  debugNeedConsent: DebugNeedConsent.DEBUG_NEED_CONSENT,
  underAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED,
  // testDeviceId: '********',
})
  .then((result) => console.log('HMS setConsent, result:', result))
  .catch((e) => console.warn('HMS setConsent, error:', e))
```

### `HMSAds.checkConsent()`

Obtains ad consent configuration. The function returns a promise that resolves a [`ConsentResult`](#consentresult) object.

```jsx
import HMSAds from 'react-native-hms-ads';

HMSAds.checkConsent()
  .then((result) => console.log('HMS checkConsent, result:', result))
  .catch((e) => console.log('HMS checkConsent, error:', e))
```

</details>
  
---
  
<details open><summary>HMSOaid</summary>

### `HMSOaid.getAdvertisingIdInfo(string)`

Obtains the OAID and 'Limit ad tracking' setting. The string argument should be one of values of [`CallMode`](#callmode). The function returns a promise that resolves a [`AdvertisingIdClientInfo`](#advertisingidclientinfo) object.

```jsx
import {HMSOaid, CallMode} from 'react-native-hms-ads';

let callMode = CallMode.SDK;
HMSOaid.getAdvertisingIdInfo(callMode)
  .then((result) => console.log('HMSOaid getAdvertisingIdInfo, result:', result))
  .catch((e) => console.log('HMSOaid getAdvertisingIdInfo, error:', e))
```

### `HMSOaid.verifyAdvertisingId(`[`AdvertisingIdClientInfo`](#advertisingidclientinfo)`)`

Verifies the OAID and 'Limit ad tracking' setting. The function returns a promise that resolves a [`AdvertisingIdClientInfo`](#advertisingidclientinfo) object.

```jsx
import {HMSOaid} from 'react-native-hms-ads';

// should use information obtained from 'getAdvertisingIdInfo()' function
let advertisingInfo = {
  id: "01234567-89abc-defe-dcba-987654321012",
  isLimitAdTrackingEnabled: false
}
HMSOaid.verifyAdvertisingId(advertisingInfo)
  .then((result) => console.log('HMSOaid verifyAdvertisingId, result:', result))
  .catch((e) => console.warn('HMSOaid verifyAdvertisingId, error:', e))
```

</details>
  
---
  
<details open><summary>HMSReward</summary>

### `HMSReward.setAdId(string)`

Sets ad slot id.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.setAdId("testx9dtjwj8hp"); // video ad
```

### `HMSReward.setUserId(string)`

Sets user id.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.setUserId("HMS User");
```

### `HMSReward.setData(string)`

Sets custom data in string.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.setData("HMS Data");
```

### `HMSReward.setVerifyConfig(`[`VerifyConfig`](#verifyconfig)`)`

Sets server-side verification parameters.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.setVerifyConfig({userId: 'userxxxxx', data: 'dataxxxx'});
```

### `HMSReward.setAdParam(`[`AdParam`](#adparam)`)`

Sets parameters of ad request.

```jsx
import {HMSReward, ContentClassification, UnderAge} from 'react-native-hms-ads';

HMSReward.setAdParam({
  adContentClassification: ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
  tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED
});
```

### `HMSReward.pause()`

Pauses the ad.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.pause();
```

### `HMSReward.resume()`

Resumes the ad.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.resume();
```

### `HMSReward.loadAd()`

Requests ad.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.loadAd();
```

### `HMSReward.show()`

Displays ad.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.show();
```

### `HMSReward.isLoaded()`

Checks whether ad is successfully loaded. The function returns a promise that resolves  a boolean indicating whether the ad is loaded or not.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.isLoaded()
  .then((result) => console.log('HMSReward isLoaded, result:', result))
```

### `HMSReward.allListenersRemove()`

Remove all listeners for events of HMSReward.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.allListenersRemove();
```

</details>
  
---

<details open><summary>HMSSplash</summary>

### `HMSSplash.setAdId(string)`

Sets ad slot id.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.setAdId("testd7c5cewoj6"); // video ad
```

### `HMSSplash.setLogoText(string)`

Sets logo text.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.setLogoText("HMS Sample");
```

### `HMSSplash.setCopyrightText(string)`

Sets copyright text.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.setCopyrightText("Copyright HMS");
```

### `HMSSplash.setOrientation(integer)`

Sets [screen orientation](https://developer.android.com/reference/android/content/pm/ActivityInfo#screenOrientation).

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.setOrientation(1);
```

### `HMSSplash.setSloganResource(string)`

Sets default app launch image in portrait mode, which is displayed before a splash ad is displayed.

```jsx
import {HMSSplash} from 'react-native-hms-ads';
// file_name_without_extension.png added to res/drawable or res/mipmap
HMSSplash.setSloganResource("file_name_without_extension");
```

### `HMSSplash.setWideSloganResource(string)`

Sets default app launch image in landscape mode, which is displayed before a splash ad is displayed.

```jsx
import {HMSSplash} from 'react-native-hms-ads';
// file_name_without_extension.png added to res/drawable or res/mipmap
HMSSplash.setWideSloganResource("file_name_without_extension");
```

### `HMSSplash.setLogoResource(string)`

Sets app logo.

```jsx
import {HMSSplash} from 'react-native-hms-ads';
// file_name_without_extension.png added to res/drawable or res/mipmap
HMSSplash.setLogoResource("file_name_without_extension");
```

### `HMSSplash.setMediaNameResource(string)`

Sets app text resource.

```jsx
import {HMSSplash} from 'react-native-hms-ads';
// <string name="media_name">HUAWEI Ads</string> line inserted to strings.xml
HMSSplash.setMediaNameResource("media_name");
```

### `HMSSplash.setAudioFocusType(integer)`

Sets the audio focus preemption policy for a video splash ad.

```jsx
import {HMSSplash, AudioFocusType} from 'react-native-hms-ads';

HMSSplash.setAudioFocusType(AudioFocusType.GAIN_AUDIO_FOCUS_ALL);
```

### `HMSSplash.setAdParam(`[`AdParam`](#adparam)`)`

Sets parameters of ad request.

```jsx
import {HMSSplash, ContentClassification, UnderAge} from 'react-native-hms-ads';

HMSSplash.setAdParam({
  adContentClassification: ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
  tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED
});
```

### `HMSSplash.pause()`

Pauses ad.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.pause();
```

### `HMSSplash.resume()`

Resumes ad.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.resume();
```

### `HMSSplash.destroy()`

Destroys ad.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.destroy();
```

### `HMSSplash.show()`

Shows ad.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.show();
```

### `HMSSplash.isLoaded()`

Checks whether a splash ad has been loaded.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.isLoaded()
  .then((result) => console.log('HMSSplash isLoaded, result:', result))
```

### `HMSSplash.isLoading()`

Checks whether a splash ad is being loaded.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.isLoading()
  .then((result) => console.log('HMSSplash isLoading, result:', result))
```

### `HMSSplash.allListenersRemove()`

Remove all listeners for events of HMSSplash.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.allListenersRemove();
```

</details>

---
  
<details open><summary>HMSInterstitial</summary>

### `HMSInterstitial.setAdId(string)`

Sets ad slot id.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.setAdId("testb4znbuh3n2"); // video ad
```

### `HMSInterstitial.setAdParam(`[`AdParam`](#adparam)`)`

Sets parameters of ad request.

```jsx
import {HMSInterstitial, ContentClassification, UnderAge} from 'react-native-hms-ads';

HMSInterstitial.setAdParam({
  adContentClassification: ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
  tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED
});
```

### `HMSInterstitial.loadAd()`

Initiates a request to load an ad.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.loadAd();
```

### `HMSInterstitial.show()`

Displays an interstitial ad.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.show();
```

### `HMSInterstitial.isLoaded()`

Checks whether ad loading is complete.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.isLoaded()
  .then((result) => console.log('HMSInterstitial isLoaded, result:', result))
```

### `HMSInterstitial.allListenersRemove()`

Remove all listeners for events of HMSInterstitial.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.allListenersRemove();
```

</details>

---
  
<details open><summary>HMSInstallReferrer</summary>

### `HMSInstallReferrer.startConnection(string, boolean, string)`

Starts to connect to the install referrer service. The first string argument should be one of values of [`CallMode`](#callmode). And the boolean argument indicates test mode. The last string argument is the name of the package that the service receives information about. The function returns a promise that resolves a boolean indicating whether the service is successfully connected.

```jsx
import {HMSInstallReferrer, CallMode} from 'react-native-hms-ads';

let callMode = CallMode.SDK;
let isTest = 'true';
let pkgName = 'com.rnhmsadsdemo'; // your app package name
HMSInstallReferrer.startConnection(callMode, isTest, pkgName)
  .then((result) => console.log('HMSInstallReferrer startConnection, result:', result))
  .catch((e) => console.warn('HMSInstallReferrer startConnection, error:', e));
```

### `HMSInstallReferrer.endConnection()`

Ends the service connection and releases all occupied resources.

```jsx
import {HMSInstallReferrer} from 'react-native-hms-ads';

HMSInstallReferrer.endConnection()
  .then(() => console.log('HMSInstallReferrer endConnection'))
  .catch((e) => console.warn('HMSInstallReferrer endConnection, error:', e));
```

### `HMSInstallReferrer.getReferrerDetails()`

Obtains install referrer information.

```jsx
import {HMSInstallReferrer} from 'react-native-hms-ads';

HMSInstallReferrer.getReferrerDetails()
  .then((result) => console.log('HMSInstallReferrer getReferrerDetails, result:', result))
  .catch((e) => console.warn('HMSInstallReferrer getReferrerDetails, error:', e));
```

### `HMSInstallReferrer.isReady()`

Indicates whether the service connection is ready.

```jsx
import {HMSInstallReferrer} from 'react-native-hms-ads';

HMSInstallReferrer.isReady()
  .then((result) => console.log('HMSInstallReferrer isReady, result:', result));
```

### `HMSInstallReferrer.allListenersRemove()`

Remove all listeners for events of HMSInstallReferrer.

```jsx
import {HMSInstallReferrer} from 'react-native-hms-ads';

HMSInstallReferrer.allListenersRemove();
```

</details>

## Events
  
<details open><summary>HMSInterstitial</summary>

### `Events of HMSInterstitial`

a) `adFailed`

Triggered when ad fails to load.

#### `HMSInterstitial.adFailedListenerAdd(function)`

Adds listener for adFailed event. The listener function gets [Error](#error) as input.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.adFailedListenerAdd((error) => {
  console.warn('HMSInterstitial adFailed, error: ', error);
});
```

#### `HMSInterstitial.adFailedListenerRemove()`

Removes listeners for adFailed event.

```jsx
HMSInterstitial.adFailedListenerRemove();
```

---

b) `adClosed`

Triggered when ad is closed.

#### `HMSInterstitial.adClosedListenerAdd(function)`

Adds listener for adClosed event.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.adClosedListenerAdd(() => {
  console.log('HMSInterstitial adClosed');
});
```

#### `HMSInterstitial.adClosedListenerRemove()`

Removes listeners for adClosed event.

```jsx
HMSInterstitial.adClosedListenerRemove();
```

---

c) `adLeave`

Triggered when the user leaves the app.

#### `HMSInterstitial.adLeaveListenerAdd(function)`

Adds listener for adLeave event.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.adLeaveListenerAdd(() => {
  console.warn('HMSInterstitial adLeave');
});
```

#### `HMSInterstitial.adLeaveListenerRemove()`

Removes listeners for adLeave event.

```jsx
HMSInterstitial.adLeaveListenerRemove();
```

---

d) `adOpened`

Triggered when ad is displayed.

#### `HMSInterstitial.adOpenedListenerAdd(function)`

Adds listener for adOpened event.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.adOpenedListenerAdd(() => {
  console.log('HMSInterstitial adOpened');
});
```

#### `HMSInterstitial.adOpenedListenerRemove()`

Removes listeners for adOpened event.

```jsx
HMSInterstitial.adOpenedListenerRemove();
```

---

e) `adLoaded`

Triggered when ad loads.

#### `HMSInterstitial.adLoadedListenerAdd(function)`

Adds listener for adLoaded event. The listener function gets [InterstitialAd](#interstitialad) as input.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.adLoadedListenerAdd((interstitialAd) => {
  console.log('HMSInterstitial adLoaded, Interstitial Ad: ', interstitialAd);
});
```

#### `HMSInterstitial.adLoadedListenerRemove()`

Removes listeners for adLoaded event.

```jsx
HMSInterstitial.adLoadedListenerRemove();
```

---

f) `adClicked`

Triggered when ad is clicked.

#### `HMSInterstitial.adClickedListenerAdd(function)`

Adds listener for adClicked event.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.adClickedListenerAdd(() => {
  console.log('HMSInterstitial adClicked');
});
```

#### `HMSInterstitial.adClickedListenerRemove()`

Removes listeners for adClicked event.

```jsx
HMSInterstitial.adClickedListenerRemove();
```

---

g) `adImpression`

Triggered when ad impression is detected.

#### `HMSInterstitial.adImpressionListenerAdd(function)`

Adds listener for adImpression event.

```jsx
import {HMSInterstitial} from 'react-native-hms-ads';

HMSInterstitial.adImpressionListenerAdd(() => {
  console.log('HMSInterstitial adImpression');
});
```

#### `HMSInterstitial.adImpressionListenerRemove()`

Removes listeners for adImpression event.

```jsx
HMSInterstitial.adImpressionListenerRemove();
```

</details>
  
---

<details open><summary>HMSSplash</summary>

### `Events of HMSSplash`

a) `adFailedToLoad`

Triggered when ad fails to load.

#### `HMSSplash.adFailedToLoadListenerAdd(function)`

Adds listener for adFailedToLoad event. The listener function gets [Error](#error) as input.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.adFailedToLoadListenerAdd((error) => {
  console.warn('HMSSplash adFailedToLoad, error: ', error);
});
```

#### `HMSSplash.adFailedToLoadListenerRemove()`

Removes listeners for adFailedToLoad event.

```jsx
HMSSplash.adFailedToLoadListenerRemove();
```

---

b) `adDismissed`

Triggered when ad is dismissed.

#### `HMSSplash.adDismissedListenerAdd(function)`

Adds listener for adDismissed event.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.adDismissedListenerAdd(() => {
  console.log('HMSSplash adDismissed');
});
```

#### `HMSSplash.adDismissedListenerRemove()`

Removes listeners for adDismissed event.

```jsx
HMSSplash.adDismissedListenerRemove();
```

---

c) `adShowed`

Triggered when ad is shown.

#### `HMSSplash.adShowedListenerAdd(function)`

Adds listener for adShowed event.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.adShowedListenerAdd(() => {
  console.log('HMSSplash adShowed');
});
```

#### `HMSSplash.adShowedListenerRemove()`

Removes listeners for adShowed event.

```jsx
HMSSplash.adShowedListenerRemove();
```

---

d) `adLoaded`

Triggered when ad loads.

#### `HMSSplash.adLoadedListenerAdd(function)`

Adds listener for adLoaded event.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.adLoadedListenerAdd(() => {
  console.log('HMSSplash adLoaded');
});
```

#### `HMSSplash.adLoadedListenerRemove()`

Removes listeners for adLoaded event.

```jsx
HMSSplash.adLoadedListenerRemove();
```

---

e) `adClick`

Triggered when ad is clicked.

#### `HMSSplash.adClickListenerAdd(function)`

Adds listener for adClick event.

```jsx
import {HMSSplash} from 'react-native-hms-ads';

HMSSplash.adClickListenerAdd(() => {
  console.log('HMSSplash adClick');
});
```

#### `HMSSplash.adClickListenerRemove()`

Removes listeners for adClick event.

```jsx
HMSSplash.adClickListenerRemove();
```

</details>

---

<details open><summary>HMSReward</summary>

### `Events of HMSReward`

a) `adFailedToLoad`

Triggered when ad fails to load.

#### `HMSReward.adFailedToLoadListenerAdd(function)`

Adds listener for adFailedToLoad event. The listener function gets [Error](#error) as input.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.adFailedToLoadListenerAdd((error) => {
  console.warn('HMSReward adFailedToLoad, error: ', error);
});
```

#### `HMSReward.adFailedToLoadListenerRemove()`

Removes listeners for adFailedToLoad event.

```jsx
HMSReward.adFailedToLoadListenerRemove();
```

---

b) `adFailedToShow`

Triggered when ad fails to be displayed.

#### `HMSReward.adFailedToShowListenerAdd(function)`

Adds listener for adFailedToShow event. The listener function gets [Error](#error) as input.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.adFailedToShowListenerAdd((error) => {
  console.warn('HMSReward adFailedToShow, error: ', error);
});
```

#### `HMSReward.adFailedToShowListenerRemove()`

Removes listeners for adFailedToShow event.

```jsx
HMSReward.adFailedToShowListenerRemove();
```

---

c) `adClosed`

Triggered when ad is closed.

#### `HMSReward.adClosedListenerAdd(function)`

Adds listener for adClosed event.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.adClosedListenerAdd(() => {
  console.log('HMSReward adClosed');
});
```

#### `HMSReward.adClosedListenerRemove()`

Removes listeners for adClosed event.

```jsx
HMSReward.adClosedListenerRemove();
```

---

d) `adOpened`

Triggered when ad is opened.

#### `HMSReward.adOpenedListenerAdd(function)`

Adds listener for adOpened event.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.adOpenedListenerAdd(() => {
  console.log('HMSReward adOpened');
});
```

#### `HMSReward.adOpenedListenerRemove()`

Removes listeners for adOpened event.

```jsx
HMSReward.adOpenedListenerRemove();
```

---

e) `adLoaded`

Triggered when ad loads.

#### `HMSReward.adLoadedListenerAdd(function)`

Adds listener for adLoaded event. The listener function gets [RewardAd](#rewardad) as input.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.adLoadedListenerAdd((rewardAd) => {
  console.log('HMSReward adLoaded, Reward ad: ', rewardAd);
});
```

#### `HMSReward.adLoadedListenerRemove()`

Removes listeners for adLoaded event.

```jsx
HMSReward.adLoadedListenerRemove();
```

---

f) `adRewarded`

Triggered when a [reward](#reward) is provided.

#### `HMSReward.adRewardedListenerAdd(function)`

Adds listener for adRewarded event.

```jsx
import {HMSReward} from 'react-native-hms-ads';

HMSReward.adRewardedListenerAdd((reward) => {
  console.log('HMSReward adRewarded, reward: ', reward);
});
```

#### `HMSReward.adRewardedListenerRemove()`

Removes listeners for adRewarded event.

```jsx
HMSReward.adRewardedListenerRemove();
```

</details>

---
  
<details open><summary>HMSInstallReferrer</summary>

### `Events of HMSInstallReferrer`

a) `serviceConnected`

Triggered when service connection is complete.

#### `HMSInstallReferrer.serviceConnectedListenerAdd(function)`

Adds listener for serviceConnected event. The listener function gets [InstallReferrerResponse](#installreferrerresponse) as input.

```jsx
import {HMSInstallReferrer} from 'react-native-hms-ads';

HMSInstallReferrer.serviceConnectedListenerAdd((response) => {
  console.log('HMSInstallReferrer serviceConnected, response:', response);
});
```

#### `HMSInstallReferrer.serviceConnectedListenerRemove()`

Removes listeners for serviceConnected event.

```jsx
HMSInstallReferrer.serviceConnectedListenerRemove();
```

---

b) `serviceDisconnected`

Triggered when service is disconnected.

#### `HMSInstallReferrer.serviceDisconnectedListenerAdd(function)`

Adds listener for serviceDisconnected event.

```jsx
import {HMSInstallReferrer} from 'react-native-hms-ads';

HMSInstallReferrer.serviceDisconnectedListenerAdd(() => {
  console.log('HMSInstallReferrer serviceDisconnected');
});
```

#### `HMSInstallReferrer.serviceDisconnectedListenerRemove()`

Removes listeners for serviceDisconnected event.

```jsx
HMSInstallReferrer.serviceDisconnectedListenerRemove();
```

</details>

## Constants

<details open><summary>Show Details</summary>

### **`ConsentStatus`**

| Key | Value | Definition |
|-----------|---------|-----------|
|`PERSONALIZED` | `0` | Personalized consent option.|
|`NON_PERSONALIZED` | `1` | Non-personalized consent option.|
|`UNKNOWN` | `2` | Unknown consent option.|

---

### **`DebugNeedConsent`**

| Key | Value | Definition |
|-----------|---------|-----------|
|`DEBUG_DISABLED` | `0` | Disabled debug option.|
|`DEBUG_NEED_CONSENT` | `1` | Consent-needed debug option.|
|`DEBUG_NOT_NEED_CONSENT` | `2` | Consent-not-needed debug option.|

---

### **`AudioFocusType`**

Whether to obtain the audio focus during video playback.

| Key | Value | Definition |
|-----------|---------|-----------|
|`GAIN_AUDIO_FOCUS_ALL` | `0` | Obtain the audio focus when a video is played, no matter whether the video is muted.|
|`NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE` | `1` | Obtain the audio focus only when a video is played without being muted.|
|`NOT_GAIN_AUDIO_FOCUS_ALL` | `2` | Do not obtain the audio focus when a video is played, no matter whether the video is muted.|

---

### **`ContentClassification`**

Ad content rating.

| Key | Value | Definition |
|-----------|---------|-----------|
|`AD_CONTENT_CLASSIFICATION_W` | `'W'` | Content suitable for toddlers and older audiences.|
|`AD_CONTENT_CLASSIFICATION_PI` | `'PI'` | Content suitable for kids and older audiences.|
|`AD_CONTENT_CLASSIFICATION_J` | `'J'` | Content suitable for teenagers and older audiences.|
|`AD_CONTENT_CLASSIFICATION_A` | `'A'` | Content suitable only for adults.|
|`AD_CONTENT_CLASSIFICATION_UNKOWN` | `''` | Unknown rating.|

---

### **`Gender`**

Gender.

| Key | Value | Definition |
|-----------|---------|-----------|
|`UNKNOWN` | `0` | Unknown gender.|
|`MALE` | `1` | Male.|
|`FEMALE` | `2` | Female.|

---

### **`NonPersonalizedAd`**

Whether to request only non-personalized ads.

| Key | Value | Definition |
|-----------|---------|-----------|
|`ALLOW_ALL` | `0` | Request both personalized and non-personalized ads.|
|`ALLOW_NON_PERSONALIZED` | `1` | Request only non-personalized ads.|

---

### **`TagForChild`**

Child-directed setting.

| Key | Value | Definition |
|-----------|---------|-----------|
|`TAG_FOR_CHILD_PROTECTION_FALSE` | `0` | Do not process ad requests according to the Children’s Online Privacy Protection Act (COPPA).|
|`TAG_FOR_CHILD_PROTECTION_TRUE` | `1` | Process ad requests according to the COPPA.|
|`TAG_FOR_CHILD_PROTECTION_UNSPECIFIED` | `-1` | Whether to process ad requests according to the COPPA is not specified.|

---

### **`UnderAge`**

Setting directed to users under the age of consent.

| Key | Value | Definition |
|-----------|---------|-----------|
|`PROMISE_FALSE` | `0` | Do not process ad requests as directed to users under the age of consent.|
|`PROMISE_TRUE` | `1` | Process ad requests as directed to users under the age of consent.|
|`PROMISE_UNSPECIFIED` | `-1` | Whether to process ad requests as directed to users under the age of consent is not specified.|

---

### **`NativeAdAssetNames`**

Constant IDs of all native ad components.

| Key | Value | Definition |
|-----------|---------|-----------|
|`TITLE` | `1` | Title material ID.|
|`CALL_TO_ACTION` | `2` | Material ID of the action text to be displayed on a button.|
|`ICON` | `3` | Icon material ID.|
|`DESC` | `4` | Description material ID.|
|`AD_SOURCE` | `5` | Advertiser material ID.|
|`IMAGE` | `8` | Image material ID.|
|`MEDIA_VIDEO` | `10` | Multimedia view material ID.|
|`CHOICES_CONTAINER` | `11` | Ad choice material ID.|

---

### **`ChoicesPosition`**

Choice icon position constants.

| Key | Value | Definition |
|-----------|---------|-----------|
|`TOP_LEFT` | `0` | Top left.|
|`TOP_RIGHT` | `1` | Top right.|
|`BOTTOM_RIGHT` | `2` | Bottom right.|
|`BOTTOM_LEFT` | `3` | Bottom left.|
|`INVISIBLE` | `4` | Invisible.|

---

### **`Direction`**

Orientation constant.

| Key | Value | Definition |
|-----------|---------|-----------|
|`ANY` | `0` | Any orientation.|
|`PORTRAIT` | `1` | Portrait.|
|`LANDSCAPE` | `2` | Landscape.|

---

### **`ScaleType`**

[Options](https://developer.android.com/reference/android/widget/ImageView.ScaleType) for scaling the bounds of an image.

| Key | Value |
|-----------|----------|
|`MATRIX` | `0` |
|`FIT_XY` | `1` |
|`FIT_START` | `2` |
|`FIT_CENTER` | `3` |
|`FIT_END` | `4` |
|`CENTER` | `5` |
|`CENTER_CROP` | `6` |
|`CENTER_INSIDE` | `7` |

---

### **`BannerAdSizes`**

| Key | Value | Definition |
|-----------|---------|-----------|
|`B_160_600` | `'160_600'` | 160 x 600 dp banner ad size.|
|`B_300_250` | `'300_250'` | 300 x 250 dp banner ad size.|
|`B_320_50` | `'320_50'` | 320 x 50  dp banner ad size.|
|`B_320_100` | `'320_100'` | 320 x 100 dp banner ad size.|
|`B_360_57` | `'360_57'` | 360 x 57 dp banner ad size.|
|`B_360_144` | `'360_144'` | 360 x 144 dp banner ad size.|
|`B_468_60` | `'468_60'` | 468 x 60 dp banner ad size.|
|`B_728_90` | `'728_90'` | 728 x 90 dp banner ad size.|
|`B_CURRENT_DIRECTION` | `'current_direction'` | Banner ad size based on a based on the current device orientation and a custom width.|
|`B_PORTRAIT` | `'portrait'` | Banner ad size based on a custom width in portrait  orientation.|
|`B_SMART` | `'smart'` | Dynamic banner ad size. The screen width and an adaptive height are used.|
|`B_DYNAMIC` | `'dynamic'` | Dynamic banner ad size. The width of the parent layout and the height of the ad content are used.|
|`B_LANDSCAPE` | `'landscape'` | Banner ad size based on a custom width in landscape orientation.|
|`B_INVALID` | `'invalid'` | Invalid size. No ad can be requested using this size.|

---

### **`NativeMediaTypes`**

Native ad media types.

| Key | Value | Definition |
|-----------|---------|-----------|
|`VIDEO` | `'video'` | Ad with video.|
|`IMAGE_SMALL` | `'image_small'` | Ad with small image.|
|`IMAGE_LARGE` | `'image_large'` | Ad with large image.|

---

### **`BannerMediaTypes`**

Banner ad media types.

| Key | Value | Definition |
|-----------|---------|-----------|
|`IMAGE` | `'image'` | Ad with image.|

---

### **`InterstitialMediaTypes`**

Interstitial ad media types.

| Key | Value | Definition |
|-----------|---------|-----------|
|`VIDEO` | `'video'` | Ad with video.|
|`IMAGE` | `'image'` | Ad with image.|

---

### **`RewardMediaTypes`**

Reward ad media types.

| Key | Value | Definition |
|-----------|---------|-----------|
|`VIDEO` | `'video'` | Ad with video.|

---

### **`SplashMediaTypes`**

Splash ad media types.

| Key | Value | Definition |
|-----------|---------|-----------|
|`VIDEO` | `'video'` | Ad with video.|
|`IMAGE` | `'image'` | Ad with image.|

---

### **`CallMode`**

Option for functions that can use Huawei SDK or [Aidl](https://developer.android.com/guide/components/aidl) service.

| Key | Value | Definition |
|-----------|---------|-----------|
|`SDK` | `'sdk'` | Use Huawei Ads SDK .|
|`AIDL` | `'aidl'` | (Not properly working, please prefer `SDK`) Use aidl service.|

</details>

## DataTypes

<details open><summary>Show Details</summary>

### **`Reward`**

Information about the reward item in a rewarded ad.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`name` | `string` | The name of a reward item.|
|`amount` | `integer` | The number of reward items.|

---

### **`RewardAd`**

Rewarded ad.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`userId` | `string` | User id.|
|`data` | `string` | Custom data.|
|`reward` | [`Reward`](#reward) | Reward item.|
|`isLoaded` | `boolean` | Shows whether a rewarded ad is successfully loaded.|

---

### **`InterstitialAd`**

Interstitial ad.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`adId` | `string` | The ad slot id.|
|`isLoaded` | `boolean` | Shows whether ad loading is complete.|
|`isLoading` | `boolean` | Shows whether ads are being loaded.|

---

### **`DislikeAdReason`**

Obtains the reason why a user dislikes an ad.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`description` | `string` | The reason why a user dislikes an ad.|

---

### **`VideoOperator`**

Video controller, which implements video control such as playing, pausing, and muting a video

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`aspectRatio` | `number` | The video aspect ratio.|
|`hasVideo` | `boolean` | Shows whether ad content contains a video.|
|`isCustomizeOperateEnabled` | `boolean` | Shows whether a custom video control is used for a video ad.|
|`isClickToFullScreenEnabled` | `boolean` | Shows whether click to full screen option enabled for a video ad.|
|`isMuted` | `boolean` | Shows whether a video is muted.|

---

### **`NativeAd`**

Native ad.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`adSource` | `string` | Ad source.|
|`description` | `string` | Ad description.|
|`callToAction` | `string` | The text to be displayed on a button, for example, View Details or Install.|
|`dislikeAdReasons` | [`DislikeAdReason[]`](#dislikeadreason) | The choices of not displaying the current ad.|
|`title` | `string` | Ad title.|
|`videoOperator` | [`VideoOperator`](#videooperator) | Video operator used for the ad.|
|`isCustomClickAllowed` | `boolean` | Shows whether custom tap gestures are enabled.|
|`isCustomDislikeThisAdEnabled` | `boolean` | Shows whether custom ad closing is enabled.|

---

### **`AdProvider`**

Ad provider.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`id` | `string` | Id of ad provider.|
|`name` | `string` | Name of ad provider.|
|`privacyPolicyUrl` | `string` | The url for privacy policy.|
|`serviceArea` | `string` | The service area for ad (ex: 'Global' or 'Asia').|

---

### **`AdSize`**

Ad size.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`height` | `integer` | Ad height, in dp.|
|`width` | `integer` | Ad width, in dp.|

---

### **`BannerAdSize`**

Banner ad size.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`height` | `integer` | Ad height, in dp.|
|`width` | `integer` | Ad width, in dp.|
|`heightPx` | `integer` | Ad height, in pixels.|
|`widthPx` | `integer` | Ad width, in pixels.|
|`isAutoHeightSize` | `boolean` | Shows whether an adaptive height is used.|
|`isDynamicSize` | `boolean` | Shows whether a dynamic size is used.|
|`isFullWidthSize` | `boolean` | Shows whether a full-screen width is used.|

---

### **`VideoConfiguration`**

Video configuration used to control video playback.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`audioFocusType` | `integer` | The video playback scenario where the audio focus needs to be obtained. Check [`AudioFocusType`](#audiofocustype) for possible values.|
|`isCustomizeOperateRequested` | `boolean` | The setting for using custom video control.|
|`isStartMuted` | `boolean` | The setting for muting video when it starts.|

---

### **`NativeAdConfiguration`**

Native Ad configuration.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`adSize` | [`AdSize`](#adsize) | Ad size.|
|`choicesPosition` | `integer` | Position of an ad choice icon. Check [`ChoicesPosition`](#choicesposition) for possible values.|
|`mediaDirection` | `integer` | Direction of an ad image.  Check [`Direction`](#direction) for possible values.|
|`mediaAspect` | `integer` | Aspect ratio of an ad image.|
|`videoConfiguration` | [`VideoConfiguration`](#videoconfiguration) | Video Configuration.|
|`isRequestMultiImages` | `boolean` | The setting for requesting multiple ad images.|
|`isReturnUrlsForImages` | `boolean` | The setting for enabling the SDK to download native ad images.|

---

### **`NativeAdLoader`**

Native Ad loader.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`isLoading` | `boolean` | Shows whether ads are being loaded.|

---

### **`AdvertisingIdClientInfo`**

Information about advertised clients.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`id` | `string` | The OAID.|
|`isLimitAdTrackingEnabled` | `boolean` | 'Limit ad tracking' setting.|

---

### **`ReferrerDetails`**

Describes the install referrer information.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`installReferrer` | `string` | Install referrer information.|
|`installBeginTimestampMillisecond` | `number` | The app installation timestamp, in milliseconds.|
|`installBeginTimestampSeconds` | `number` | The app installation timestamp, in seconds.|
|`referrerClickTimestampMillisecond` | `number` | The ad click timestamp, in milliseconds.|
|`referrerClickTimestampSeconds` | `number` | The ad click timestamp, in seconds.|

---

### **`RequestOptions`**

Ad request options.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`adContentClassification` | `string` | Ad content rating. Check [`ContentClassification`](#contentclassification) for possible values.|
|`appCountry` | `string` | The country for the app.|
|`appLang` | `string` | The language for the app.|
|`nonPersonalizedAd` | `integer` | The setting for requesting non-personalized ads. Check [`NonPersonalizedAd`](#nonpersonalizedad) for possible values.|
|`tagForChildProtection` | `integer` | The child-directed setting. Check [`TagForChild`](#tagforchild) for possible values.|
|`tagForUnderAgeOfPromise` | `integer` | The setting directed to users under the age of consent. Check [`UnderAge`](#underage) for possible values.|

---

### **`Consent`**

Ad consent object to be submitted.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`consentStatus` | `integer` | [ConsentStatus](#consentstatus) option.|
|`debugNeedConsent` | `integer` |[DebugNeedConsent](#debugneedconsent) option.|
|`underAgeOfPromise` | `integer` | [UnderAge](#underage) option.|
|`testDeviceId` | `string` | Device Id.|

---

### **`ConsentResult`**

Consent information from api result

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`consentStatus` | `integer` | Status of consent.|
|`isNeedConsent` | `boolean` | Shows whether consent is needed.|
|`adProviders` | [`AdProvider[]`](#adprovider) | Ad provider list.|

---

### **`BannerResult`**

Banner information from banner load event

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`adId` | `string` | Ad slot id.|
|`isLoading` | `boolean` | Shows whether banner is loading.|
|`bannerAdSize` | [`BannerAdSize`](#banneradsize) | BannerAdSize information.|

---

### **`NativeResult`**

Native ad information from native ad load event.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`nativeAd` | [`NativeAd`](#nativead) | Native ad information.|
|`nativeAdConfiguration` | [`NativeAdConfiguration`](#nativeadconfiguration) | Native ad configuration information.|
|`nativeAdLoader` | [`NativeAdLoader`](#nativeadloader) | Native ad loader information.|

---

### **`Error`**

Ad error.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`errorCode` | `integer` | Error code.|
|`errorMessage` | `string` | Error message.|

---

### **`InstallReferrerResponse`**

Install referrer connection response.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`responseCode` | `integer` | Response code.|
|`responseMessage` | `string` | Response message.|

---

### **`AdParam`**

Ad request parameters.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`adContentClassification` | `string` | Ad content rating. Check [`ContentClassification`](#contentclassification) for possible values.|
|`appCountry` | `string` | Country code corresponding to the language in which an ad needs to be returned for an app.|
|`appLang` | `string` | Language in which an ad needs to be returned for an app.|
|`belongCountryCode` | `string` | Home country code.|
|`gender` | `integer` | Gender.  Check [`Gender`](#gender) for possible values.|
|`nonPersonalizedAd` | `integer` | The setting of requesting personalized ads. Check [`NonPersonalizedAd`](#nonpersonalizedad) for possible values.|
|`requestOrigin` | `string` | Origin of request.|
|`tagForChildProtection` | `integer` | The setting of processing ad requests according to the COPPA. Check [`TagForChild`](#tagforchild) for possible values.|
|`tagForUnderAgeOfPromise` | `integer` |  The setting of processing ad requests as directed to users under the age of consent. Check [`UnderAge`](#underage) for possible values.|
|`targetingContentUrl` | `string` | Targeting content url.|

---

### **`VerifyConfig`**

Server-side verification parameter.

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`userId` | `integer` | User Id.|
|`data` | `string` | Custom data.|

---

### **`AdTextStyle`**

| Parameter | Type | Definition |
|-----------|--------------------|-----------------------------|
|`fontSize` | `number` | Font size.|
|`color` | `integer` | Color.|
|`backgroundColor` | `integer` | Background color.|
|`visibility` | `boolean` | Visibility.|

</details>

## 4. Confuguration & Description

No.

## 5. Licencing & Terms

Apache 2.0 license.
