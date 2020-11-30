# React-Native HMS Safety Detect

---

## Contents

  - [1. Introduction](#1-introduction)
  - [2. Installation Guide](#2-installation-guide)
    - [Integrating React Native Safety Detect Plugin](#integrating-react-native-safety-detect-plugin)
  - [3. API Reference](#3-api-reference)
    - [HMSHuaweiApi](#hmshuaweiapi)
    - [HMSSysIntegrity](#hmssysintegrity)
    - [HMSAppsCheck](#hmsappscheck)
    - [HMSUrlCheck](#hmsurlcheck)
    - [HMSUserDetect](#hmsuserdetect)
    - [HMSWifiDetect](#hmswifidetect)
  - [4. Configuration and Description](#4-configuration-and-description)
  - [5. Sample Project](#5-sample-project)
  - [6. Questions or Issues](#6-questions-or-issues)
  - [7. Licensing and Terms](#7-licensing-and-terms)

---

## 1. Introduction

This module enables communication between Huawei Safety Detect SDK and React Native platform. It exposes all functionality provided by Huawei Safety Detect SDK.

---

## 2. Installation Guide

### Integrating React Native Safety Detect Plugin

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
npm i @hmscore/react-native-hms-safetydetect
```

**Step 2:**  Run your project.

- Run the following command to the project directory.

```bash
react-native run-android  
```

#### Download Link
To integrate the plugin, follow the steps below:

**Step 1:** Download the React Native Safety Detect Plugin and place **react-native-hms-safetydetect** under **node_modules/@hmscore** of your React Native project, as shown in the directory tree below.

            project.dir
                |_ node_modules
                    |_ @hmscore
                        |_ react-native-hms-safetydetect
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
    implementation project(":react-native-hms-safetydetect")
    ...
  }
}
```

**Step 3:** Add the following lines to the android/settings.gradle file in your project:

```groovy
include ':app'
include ':react-native-hms-safetydetect'
project(':react-native-hms-safetydetect').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-safetydetect/android')
```

**Step 4:**  Open the your application class and add the HmsRNSafetyDetectPackage

Import the following classes to the MainApplication.java file of your project.import **com.huawei.hms.rn.HmsRNSafetyDetectPackage**.

Then, add the **HmsRNSafetyDetectPackage** to your **getPackages** method. In the end, your file will be similar to the following:

```java 
import com.huawei.hms.rn.safetyDetect.HMSSafetyDetectPackage;

@Override
protected List<ReactPackage> getPackages() {
    List<ReactPackage> packages = new PackageList(this).getPackages();
    packages.add(new HMSSafetyDetectPackage());
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

### HMSHuaweiApi

#### Public Method Summary

|Function                                                                           |Return Type        |Description                                   |
|:--------------------------------------------------------------------------------- |-------------------|:---------------------------------------------|
| [isHuaweiMobileServicesAvailable()](#hmshuaweiapiishuaweimobileservicesavailable) | `Promise<boolean>`| Checks whether the installed HMS Core (APK) version is compatible with your Android SDK version.|
| [enableLogger()](#hmshuaweiapienablelogger)                                       | `void`              | Enables HMS Plugin Method Analytics.|
| [disableLogger()](#hmshuaweiapidisablelogger)                                     | `void`              | Disables HMS Plugin Method Analytics.|

#### Public Methods

##### HMSHuaweiApi.isHuaweiMobileServicesAvailable()
Checks whether the installed HMS Core (APK) version is compatible with your Android SDK version.

###### Return Type

| Return Type            | Description
|:---------------------- |:---------------------------------------------|
| `Promise<boolean>`     | Boolean value that shows HMS Core is installed or not.|

###### Call Example

```js
import {HMSHuaweiApi} from "@hmscore/react-native-hms-safetydetect";

HMSHuaweiApi.isHuaweiMobileServicesAvailable().then(response => {
  //HuaweiMobileServices is Available
  console.log(response)
}).catch(error => {
  console.log(error)
});
```

##### HMSHuaweiApi.enableLogger()
This method enables HMSLogger capability which is used for sending usage analytics of Safety Detect SDK's methods to improve the service quality.

###### Call Example

```javascript
import {HMSHuaweiApi} from "@hmscore/react-native-hms-safetydetect";

HMSHuaweiApi.enableLogger();
```

##### HMSHuaweiApi.disableLogger()
This method disables HMSLogger capability which is used for sending usage analytics of Safety Detect SDK's methods to improve the service quality.

###### Call Example

```javascript
import {HMSHuaweiApi} from "@hmscore/react-native-hms-safetydetect";

HMSHuaweiApi.disableLogger();
```

### HMSSysIntegrity

#### Public Method Summary

|Function                                                      |Return Type       |Description                                   |
|:------------------------------------------------------------ |----------------- |:---------------------------------------------|
| [sysIntegrity(nonce, appId)](#hmssysintegritysysintegrity)   | `Promise<string>` | Initiates a request to check the system integrity of the current device.|

#### Public Methods

##### HMSSysIntegrity.sysIntegrity()
Initiates a request to check the system integrity of the current device

###### Parameters

| Name    | Type                  | Description            |
| ------- | --------------------- | ---------------------- |
| nonce   | `string` | Random value that is generated anew for each use. |
| appId   | `string` | App ID applied in AppGallery Connect. HMS Core (APK) needs to authenticate the app ID. You need to enable the Safety Detect service on the Manage APIs page in AppGallery Connect. Since you have created an app during development preparations, you can obtain appId of the app on HUAWEI Developers as described in [Configuring the Signing Certificate Fingerprint](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/config-agc-0000001050416303-V5#EN-US_TOPIC_0000001050416303__section14605300811) and transfer it to the API as the second input parameter. |

###### Return Type

|Return Type     |Description
|:---------------|:---------------------------------------------|
| `Promise<string>` | System integrity check result.|

###### Call Example

```js
import {HMSSysIntegrity} from "@hmscore/react-native-hms-safetydetect";

const appId = "<Your_App_Id>";
const nonce = "Sample" + Date.now();
HMSSysIntegrity.sysIntegrity(nonce, appId).then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
});
```

### HMSAppsCheck

#### Public Method Summary

|Function                                                      |Return Type        |Description                                   |
|:------------------------------------------------------------ |-------------------|:---------------------------------------------|
| [enableAppsCheck()](#hmsappscheckenableappscheck)            | `Promise<boolean>` | Enables app security check.|
| [isVerifyAppsCheck()](#hmsappscheckisverifyappscheck)        | `Promise<boolean>` | Checks whether app security check is enabled.|
| [getMaliciousAppsList()](#hmsappscheckgetmaliciousappslist)  | `Promise<`[MaliciousApp[]](#maliciousapp)`>`  | Initiates an app security check request.|

#### Public Methods

##### HMSAppsCheck.enableAppsCheck()
Enables app security check

###### Return Type

|Return Type     |Description
|:---------------|:---------------------------------------------|
| `Promise<boolean>` | Indicates whether apps check security is enabled or not.|

###### Call Example

```js
import {HMSAppsCheck} from "@hmscore/react-native-hms-safetydetect";

HMSAppsCheck.enableAppsCheck().then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

##### HMSAppsCheck.isVerifyAppsCheck()
Checks whether app security check is enabled

###### Return Type

|Return Type     |Description
|:---------------|:---------------------------------------------|
| `Promise<boolean>` | Indicates whether apps check security is enabled or not.|

###### Call Example

```js
import {HMSAppsCheck} from "@hmscore/react-native-hms-safetydetect";

HMSAppsCheck.isVerifyAppsCheck().then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

##### HMSAppsCheck.getMaliciousAppsList()
Initiates an app security check request

###### Return Type

|Return Type     |Description
|:---------------|:---------------------------------------------|
| `Promise<`[MaliciousApp[]](#maliciousapp)`>` | Malicious app data.|

###### Call Example

```js
import {HMSAppsCheck} from "@hmscore/react-native-hms-safetydetect";

HMSAppsCheck.getMaliciousAppsList().then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

#### Data Types

##### MaliciousApp

| Name              | Type        | Description                                                 |
| ----------------- | ----------- | ----------------------------------------------------------- |
| apkPackageName    | `string`    | The package name of a malicious app. |
| apkSha256         | `string`    | Base64 encoding result of the SHA-256 value of a malicious app. |
| apkCategory       | [RiskType](#risktype)    | The type of a malicious app.            |

#### Constants

##### RiskType

| Value  | Type     | Field                            | Description      |
| ------ | -------- | -------------------------------- | ---------------- |
| 1      | `number` | HMSAppsCheck.RISK_APP            | Risk app. |
| 2      | `number` | HMSAppsCheck.VIRUS_APP           | Virus app. |

### HMSUrlCheck

#### Public Method Summary

|Function                                                      |Return Type        |Description                                   |
|:------------------------------------------------------------ |-------------------|:---------------------------------------------|
| [initUrlCheck()](#hmsurlcheckiniturlcheck)                   | `Promise<boolean>` | Initializes URL check.|
| [shutdownUrlCheck()](#hmsurlcheckshutdownurlcheck)           | `Promise<boolean>` | Disables URL check.|
| [urlCheck(params)](#hmsurlcheckurlcheck)                     | `Promise<`[UrlCheckThreat[]](#urlcheckthreat)`>`  | Initiates a URL check request.|

#### Public Methods

##### HMSUrlCheck.initUrlCheck()
Initializes URL check

###### Return Types

|Return Type         |Description
|:-------------------|:---------------------------------------------|
| `Promise<boolean>` | Indicates whether url check is enabled or not.|

###### Call Example

```js
import {HMSUrlCheck} from "@hmscore/react-native-hms-safetydetect";

HMSUrlCheck.initUrlCheck().then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

##### HMSUrlCheck.shutdownUrlCheck()
Disables URL check

###### Return Types

|Return Type      |Description
|:----------------|:---------------------------------------------|
| `Promise<boolean>` | Indicates whether url check is disabled or not.|

###### Call Example

```js
import {HMSUrlCheck} from "@hmscore/react-native-hms-safetydetect";

HMSUrlCheck.shutdownUrlCheck().then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

##### HMSUrlCheck.urlCheck()
Initiates a URL check request.

###### Parameters

| Name    | Type                  | Description            |
| ------- | --------------------- | ---------------------- |
| params  | [UrlCheckParam](#urlcheckparam) | Parameters of url check request. |

###### Return Types

|Return Type      |Description
|:----------------|:---------------------------------------------|
| `Promise<`[UrlCheckThreat[](#urlcheckthreat)]`>` | Shows url threat types.|

###### Call Example

```js
import {HMSUrlCheck} from "@hmscore/react-native-hms-safetydetect";

const params = {
  "appId": "<Your_App_Id>",
  "url": " http://example.com/hms/safetydetect/malware",
  "UrlCheckThreat": [HMSUrlCheck.MALWARE, HMSUrlCheck.PHISHING]
}
HMSUrlCheck.urlCheck(params).then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

#### Data Types

##### UrlCheckParam

| Name    | Type                  | Description            |
| ------- | --------------------- | ---------------------- |
| url     | `string` | URL to check, including the protocol, host, and path but excluding query parameters. The SDK will discard all passed query parameters. |
| appId   | `string` | App ID applied in AppGallery Connect. HMS Core (APK) needs to authenticate the app ID. You need to enable the Safety Detect service on the Manage APIs page in AppGallery Connect. Since you have created an app during development preparations, you can obtain appId of the app on HUAWEI Developers as described in [Configuring the Signing Certificate Fingerprint](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/config-agc-0000001050416303-V5#EN-US_TOPIC_0000001050416303__section14605300811) and transfer it to the API as the second input parameter. |
| urlCheckThreat   | [UrlCheckThreat[]](#urlcheckthreat) | Concerned threat types for URLs to check.  |

#### Constants

##### UrlCheckThreat

| Value  | Type     | Field                            | Description      |
| ------ | -------- | -------------------------------- | ---------------- |
| 1      | `number` | HMSUrlCheck.MALWARE              | Malware.         |
| 3      | `number` | HMSUrlCheck.PHISHING             | Phishing.        |

### HMSUserDetect

#### Public Method Summary

|Function                                                      |Return Type         |Description                                   |
|:------------------------------------------------------------ |--------------------|:---------------------------------------------|
| [initUserDetect()](#hmsuserdetectinituserdetect)             | `Promise<boolean>` | Initializes fake user detection.             |
| [userDetection(appId)](#hmsuserdetectuserdetection)          | `Promise<string>`  | Initiates a fake user detection request.     |
| [shutdownUserDetect()](#hmsuserdetectshutdownuserdetect)     | `Promise<boolean>` | Disables fake user detection.                |
| [initAntiFraud(appId)](#hmsuserdetectinitantifraud)          | `Promise<boolean>` | Initializes imperceptible fake user detection.|
| [getRiskToken()](#hmsuserdetectgetrisktoken)                 | `Promise<string>`  | Obtains a risk token.                        |
| [releaseAntiFraud()](#hmsuserdetectreleaseantifraud)         | `Promise<boolean>` | Disables imperceptible fake user detection.  |

#### Public Methods

##### HMSUserDetect.initUserDetect()
Initializes fake user detection.

###### Return Types

|Return Type         |Description
|:-------------------|:---------------------------------------------|
| `Promise<boolean>` | Indicates whether fake user detection is initialized or not.|

###### Call Example

```js
import {HMSUserDetect} from "@hmscore/react-native-hms-safetydetect";

HMSUserDetect.initUserDetect().then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

##### HMSUserDetect.userDetection()
Initiates a fake user detection request.

###### Parameters

| Name    | Type                  | Description            |
| ------- | --------------------- | ---------------------- |
| appId   | `string` | App ID applied in AppGallery Connect. HMS Core (APK) needs to authenticate the app ID. You need to enable the Safety Detect service on the Manage APIs page in AppGallery Connect. Since you have created an app during development preparations, you can obtain appId of the app on HUAWEI Developers as described in [Configuring the Signing Certificate Fingerprint](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/config-agc-0000001050416303-V5#EN-US_TOPIC_0000001050416303__section14605300811) and transfer it to the API as the second input parameter. |

###### Return Types

|Return Type        |Description
|:------------------|:---------------------------------------------|
| `Promise<string>` | Response token returned by the userDetection API. You can use the response token to obtain the fake user detection result from the cloud of UserDetect. |

###### Call Example

```js
import {HMSUserDetect} from "@hmscore/react-native-hms-safetydetect";

const appId = "<Your_App_Id>";
HMSUserDetect.userDetection(appId).then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

##### HMSUserDetect.shutdownUserDetect()
Disables fake user detection

###### Return Types

|Return Type        |Description
|:------------------|:---------------------------------------------|
| `Promise<boolean>` | Indicates whether fake user detection is disabled or not.|

###### Call Example

```js
import {HMSUserDetect} from "@hmscore/react-native-hms-safetydetect";

HMSUserDetect.shutdownUserDetect().then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

##### HMSUserDetect.initAntiFraud()
Initializes imperceptible fake user detection.

###### Parameters

| Name    | Type                  | Description            |
| ------- | --------------------- | ---------------------- |
| appId   | `string` | App ID applied in AppGallery Connect. HMS Core (APK) needs to authenticate the app ID. You need to enable the Safety Detect service on the Manage APIs page in AppGallery Connect. Since you have created an app during development preparations, you can obtain appId of the app on HUAWEI Developers as described in [Configuring the Signing Certificate Fingerprint](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/config-agc-0000001050416303-V5#EN-US_TOPIC_0000001050416303__section14605300811) and transfer it to the API as the second input parameter. |

###### Return Types

|Return Type        |Description
|:------------------|:---------------------------------------------|
| `Promise<boolean>` | Indicates whether imperceptible fake user detection is initialized or not.|

###### Call Example

```js
import {HMSUserDetect} from "@hmscore/react-native-hms-safetydetect";

const appId = "<Your_App_Id>";
HMSUserDetect.initAntiFraud(appId).then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

##### HMSUserDetect.getRiskToken()
Obtains a risk token.

###### Return Types

|Return Type        |Description
|:------------------|:---------------------------------------------|
| `Promise<string>`  | Risk token.|

###### Call Example

```js
import {HMSUserDetect} from "@hmscore/react-native-hms-safetydetect";

HMSUserDetect.getRiskToken().then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

##### HMSUserDetect.releaseAntiFraud()
Disables imperceptible fake user detection.

###### Return Types

|Return Type        |Description
|:------------------|:---------------------------------------------|
| `Promise<boolean>`  | Indicates whether imperceptible fake user detection is disabled or not.|

###### Call Example

```js
import {HMSUserDetect} from "@hmscore/react-native-hms-safetydetect";

HMSUserDetect.releaseAntiFraud().then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
})
```

### HMSWifiDetect

#### Public Method Summary

|Function                                                      |Return Type       |Description                                   |
|:------------------------------------------------------------ |----------------- |:---------------------------------------------|
| [getWifiDetectStatus()](#hmswifidetectgetwifidetectstatus)   | `Promise<`[WifiStatus](#wifistatus)`>` | Obtains the malicious Wi-Fi check result. |

#### Public Methods

##### HMSWifiDetect.getWifiDetectStatus()
Obtains the malicious Wi-Fi check result.

###### Return Types

|Return Type      |Description
|:----------------|:---------------------------------------------|
| `Promise<`[WifiStatus](#wifistatus)`>` | WiFi security status.|

###### Call Example

```js
import {HMSWifiDetect} from "@hmscore/react-native-hms-safetydetect";

HMSWifiDetect.getWifiDetectStatus().then(response => {
  console.log(response);
}).catch(error => {
  console.log(error);
});
```

#### Constants 

##### WifiStatus

| Value  | Type     | Field                            | Description      |
| ------ | -------- | -------------------------------- | ---------------- |
| 0      | `number` | HMSWifiDetect.NO_WiFi            | There is no connected WiFi. |
| 1      | `number` | HMSWifiDetect.WiFi_SECURE        | WiFi is secure. |
| 2      | `number` | HMSWifiDetect.WiFi_INSECURE      | WiFi is not secure. |

---

## 4. Configuration and Description

### Configuring Obfuscation Scripts

In order to prevent error while release build, you may need to add following lines in **proguard-rules.pro** file.

```
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
-repackageclasses
```

---

## 5. Sample Project

This plugin includes a demo project in the [example](example) folder, there you can find more usage examples.

---

## 6. Questions or Issues

If you have questions about how to use HMS samples, try the following options:
- [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services) is the best place for any programming questions. Be sure to tag your question with 
**huawei-mobile-services**.
- [Github](https://github.com/HMS-Core/hms-react-native-plugin) is the official repository for these plugins, You can open an issue or submit your ideas.
- [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001) HMS Core Module is great for general questions, or seeking recommendations and opinions.
- [Huawei Developer Docs](https://developer.huawei.com/consumer/en/doc/overview/HMS-Core-Plugin) is place to official documentation for all HMS Core Kits, you can find detailed documentations in there.

If you run into a bug in our samples, please submit an issue to the [GitHub repository](https://github.com/HMS-Core/hms-react-native-plugin).

---

## 7. Licensing and Terms

Huawei React-Native Plugin is licensed under [Apache 2.0 license](LICENCE)
