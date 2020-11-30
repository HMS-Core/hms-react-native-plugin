# React-Native HMS Contact Shield

---

## Contents

  - [1. Introduction](#1-introduction)
  - [2. Installation Guide](#2-installation-guide)
    - [Creating a Project in App Gallery Connect](#creating-a-project-in-appgallery-connect)
    - [Configuring the Signing Certificate Fingerprint](#configuring-the-signing-certificate-fingerprint)
    - [Integrating the React-Native Contact Shield Plugin](#integrating-react-native-contact-shield-plugin)
  - [3. API Reference](#3-api-reference)
    - [HMSContactShieldModule](#hmscontactshieldmodule)
    - [Constants](#hmscontactshieldsetting)
      - [HMSStatusCodeConstants](#hmsstatuscodeconstants)
      - [HMSTokenModeConstants](#hmstokenmodeconstants)
      - [HMSContactShieldSettingConstants](#hmscontactshieldsettingconstants)
      - [HMSRiskLevelConstants](#hmsrisklevelconstants)
    - [Data Types](#data-types)
      - [ContactDetail](#contactdetail)
      - [ContactSketch](#contactsketch)
      - [ContactWindow](#contactwindow)
      - [PeriodicKey](#periodickey)
      - [DiagnosisConfiguration](#diagnosisconfiguration)
      - [ScanInfo](#scaninfo)
  - [4. Configuration and Description](#4-configuration-and-description)
  - [5. Sample Project](#5-sample-project)
  - [6. Questions or Issues](#6-questions-or-issues)
  - [7. Licensing and Terms](#7-licensing-and-terms)

---

## 1. Introduction

This plugin enables communication between Huawei Contact Shield kit and React Native platform.


- Android: core layer, bridging Huawei's Contact Shield Kit bottom-layer code.
- src/modules: Android interface layer, exposes Modules and methods to React Native.
- index.js: external interface definition layer, which is used to define interface classes or reference files.
---

## 2. Installation Guide

Before you get started, you must register as a HUAWEI Developer and complete identity verification on the [HUAWEI Developer](https://developer.huawei.com/consumer/en/) website. For details, please refer to [Register a HUAWEI ID](https://developer.huawei.com/consumer/en/doc/10104).

### Creating a Project in AppGallery Connect

Creating an app in AppGallery Connect is required in order to communicate with the Huawei services. To create an app, perform the following steps:

**Step 1.** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html)  and select **My projects**.

**Step 2.** Select your project from the project list or create a new one by clicking the **Add Project** button.

**Step 3.** Go to **Project Setting** > **General information**, and click **Add app**.
If an app exists in the project and you need to add a new one, expand the app selection area on the top of the page and click **Add app**.

**Step 4.** On the **Add app** page, enter the app information, and click **OK**.

- A signing certificate fingerprint is used to verify the authenticity of an app when it attempts to access an HMS Core service through the HMS Core SDK. Before using HMS Core (APK), you must locally generate a signing certificate fingerprint and configure it in AppGallery Connect. Ensure that the JDK has been installed on your computer.


### Configuring the Signing Certificate Fingerprint

**Step 1.** Go to **Project Setting > General information**. In the **App information** field, click the icon next to SHA-256 certificate fingerprint, and enter the obtained **SHA256 certificate fingerprint**.

**Step 2.** After completing the configuration, click check mark.

### Integrating React Native Contact Shield Plugin

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
    /*          
      * <Other repositories>        
      */  
    maven { url 'https://developer.huawei.com/repo/' }
  }
}
```

**Step 9:** Open the **build.gradle** file in the **android/app** directory of your React Native project.

- Set your package name in **defaultConfig** > **applicationId** and set **minSdkVersion** to **21** or **higher**.

- Package name must match with the **package_name** entry in **agconnect-services.json** file.

```groovy
defaultConfig {
  applicationId "<package_name>"
  minSdkVersion 21
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
      signingConfig signingConfigs.config
      minifyEnabled enableProguardInReleaseBuilds
      ...
    }
  }
}
```

#### Option 1: Using NPM

**Step 1:**  Download plugin using command below.

```bash
npm i @hmscore/react-native-hms-contactshield
```

**Step 2:**  Run your project. 

- Run the following command to the project directory.

#### Option 2: Download Link

To integrate the plugin, follow the steps below:

**Step 1:** Download the React Native Contact Shield Plugin and place **react-native-hms-contactshield** under **node_modules/@hmscore** of your React Native project, as shown in the directory tree below.

```
demo-app
  |_ node_modules
    |_ @hmscore
      |_ react-native-hms-contactshield
...
```

**Step 2:** Open **build.gradle** file which is located under project.dir > android > app directory.

- Configure build dependencies.

```groovy
buildscript {
  ...
  dependencies {
    /*
    * <Other dependencies>
    */
    implementation project(":react-native-hms-contactshield")    
    ...    
  }
}
```

**Step 3:** Add the following lines to the **android/settings.gradle** file in your project:

```groovy
include ':app'
include ':react-native-hms-contactshield'
project(':react-native-hms-contactshield').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-contactshield/android')
```

**Step 4:**  Import the following classes to the **MainApplication.java** file of your project.

```java
import com.huawei.hms.rn.contactshield
```

Then, add the **HMSContactShieldPackage()** to your **getPackages** method. In the end, your file will be similar to the following:

```java 
import com.huawei.hms.rn.contactshield.HMSContactShieldPackage;
...
@Override
protected List<ReactPackage> getPackages() {
    List<ReactPackage> packages = new PackageList(this).getPackages();
    packages.add(new HMSContactShieldPackage()); // <-- Add this line 
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

### **HMSContactShieldModule**

#### Public Method Summary

| Method                                                       | Return Type                       | Description                                                  |
| ------------------------------------------------------------ | --------------------------------- | ------------------------------------------------------------ |
| [startContactShield(incubationPeriod)](#hmscontactshieldmodulestartcontactshieldincubationperiod) | Promise\<void\>                   | Enables Contact Shield.                                      |
| [startContactShieldCallback(incubationPeriod)](#hmscontactshieldmodulestartcontactshieldcallbackincubationperiod) | Promise\<void\>                   | Enables Contact Shield.                                      |
| [startContactShieldNoPersistent(incubationPeriod)](#hmscontactshieldmodulestartcontactshieldnopersistentincubationperiod) | Promise\<void\>                   | Enables Contact Shield                                       |
| [stopContactShield()](#hmscontactshieldmodulestopcontactshield) | Promise\<void\>                   | Disables Contact Shield                                      |
| [getContactDetail(token)](#hmscontactshieldmodulegetcontactdetailtoken) | Promise\<ContactDetail[]\>        | This API is called to obtain the latest diagnosis result details from Contact Shield. |
| [getContactSketch(token)](#hmscontactshieldmodulegetcontactsketchtoken) | Promise\<ContactSketch\>          | This API is called to obtain the latest diagnosis result summary from Contact Shield. |
| [putSharedKeyFiles(path,token)](#hmscontactshieldmoduleputsharedkeyfilespathtoken) | Promise\<void\>                   | This API is called to provide the shared key list file obtained from the diagnosis server to the Contact Shield SDK. |
| [putSharedKeyFilesCallback(path,token)](#hmscontactshieldmoduleputsharedkeyfilescallbackpathtoken) | Promise\<void\>                   | This API is called to provide the shared key list file obtained from the diagnosis server to the Contact Shield SDK. |
| [getContactWindow(token)](#hmscontactshieldmodulegetcontactwindowtoken) | Promise\<ContactWindow[]\>        | This API is called to obtain the latest diagnosis result details from Contact Shield in Window mode. |
| [clearAllData()](#hmscontactshieldmoduleclearalldata)        | Promise\<void\>                   | This API is called to delete all data stored on the device.  |
| [getPeriodicKey()](#hmscontactshieldmodulegetperiodickey)    | Promise\<PeriodicKey[]\>          | This API is called to obtain the periodic key list from the Contact Shield SDK. |
| [isContactShieldRunning()](#hmscontactshieldmoduleiscontactshieldrunning) | Promise\<void\>                   | This API is called to check whether Contact Shield is running. |
| [getDiagnosisConfiguration()](#hmscontactshieldmodulegetdiagnosisconfiguration) | Promise\<DiagnosisConfiguration\> | This API is called default configurations for Contact Shield. |
| [enableLogger()](#hmscontactshieldmoduleenablelogger)        | Promise\<void\>                   | Enables HMS Plugin Method Analytics.                         |
| [disableLogger()](#hmscontactshieldmoduledisablelogger)      | Promise\<void\>                   | Disables HMS Plugin Method Analytics.                        |

#### 3.1.2 Public Methods

##### HMSContactShieldModule.startContactShield(incubationPeriod)

This API is called to enables Contact Shield. Before calling this method, your app must obtain the user's authorization for contact tracing.

Parameters

| Name             | Type   | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| incubationPeriod | number | IncubationPeriod is defined by HMSContactShieldSetting.DEFAULT constant. |

Return Type

| Type            | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| Promise\<void\> | If the operation is successful, contact shield will enable. Otherwise it will throw an error. |


Call Example :

```js
HMSContactShieldModule.startContactShield(HMSContactShieldSetting.DEFAULT)
    .then((res) => {
        alert("Response: " + res);
    })
    .catch((err) => {
        alert("Error: " + err);
    });
```
##### HMSContactShieldModule.startContactShieldCallback(incubationPeriod)

This API is called to enables Contact Shield. Before calling this method, your app must obtain the user's authorization for contact tracing.

Parameters

| Name             | Type   | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| incubationPeriod | number | IncubationPeriod is defined by HMSContactShieldSetting.DEFAULT constant. |

Return Type

| Type            | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| Promise\<void\> | If the operation is successful, contact shield will enable. Otherwise it will throw an error. |


Call Example :

```js
HMSContactShieldModule.startContactShieldCallback(HMSContactShieldSetting.DEFAULT)
    .then((res) => {
        alert("Response: " + res);
    })
    .catch((err) => {
        alert("Error: " + err);
    });
```
##### HMSContactShieldModule.stopContactShield()

This API is called to disables Contact Shield.

Return Type

| Type            | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| Promise\<void\> | If the operation is successful, contact shield will disable. Otherwise it will throw an error. |


Call Example :

```js
HMSContactShieldModule.stopContactShield()
    .then((res) => {
        alert("Response: " + res);
    })
    .catch((err) => {
        alert("Error: " + err);
    });
```
##### HMSContactShieldModule.startContactShieldNoPersistent(incubationPeriod)

This API is used to enable Contact Shield. When a user exits the app, Contact Shield stops running.

Parameters

| Name             | Type   | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| incubationPeriod | number | IncubationPeriod is defined by HMSContactShieldSetting.DEFAULT constant. |

Return Type

| Type            | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| Promise\<void\> | If the operation is successful, contact shield will enable. Otherwise it will throw an error. |


Call Example :

```js
HMSContactShieldModule.startContactShieldNoPersistent(HMSContactShieldSetting.DEFAULT)
    .then((res) => {
        alert("Response: " + res);
    })
    .catch((err) => {
        alert("Error: " + err);
    });
```

##### HMSContactShieldModule.getContactDetail(token)

This API is called to obtains the latest diagnosis result details from Contact Shield.

Parameters

| Name  | Type   | Description         |
| ----- | ------ | ------------------- |
| token | string | Defines TOKEN_TEST. |


Return Type

| Type                             | Description                                                  |
| -------------------------------- | ------------------------------------------------------------ |
| Promise\<ContactDetail[]\>   | When getContactDetail() is called to obtain the latest diagnosis result, a ContactDetail list is returned. |


Call Example :

```js
HMSContactShieldModule.getContactDetail("TOKEN_TEST")
    .then((res) => {
        alert("Response: " + JSON.stringify(res));
    })
    .catch((err) => {
        alert("Error: " + err);
    });
```

##### HMSContactShieldModule.getContactSketch(token)

This API is called to obtains the latest diagnosis result summary from Contact Shield.

Parameters

| Name  | Type   | Description         |
| ----- | ------ | ------------------- |
| token | string | Defines TOKEN_TEST. |


Return Type

| Type                     | Description                                                  |
| ------------------------ | ------------------------------------------------------------ |
| Promise\<ContactSketch\> | When getContactSketch() is called to obtain the latest diagnosis result, a ContactSketch list is returned. |


Call Example :

```js
HMSContactShieldModule.getContactSketch("TOKEN_TEST")
    .then((res) => {
        alert("Response: " + JSON.stringify(res));
    })
    .catch((err) => {
        alert("Error: " + err);
    });
```
##### HMSContactShieldModule.putSharedKeyFiles(path,token)

This API is called to provide the shared key list file obtained from the diagnosis server to the Contact Shield SDK.  

Parameters

| Name  | Type   | Description                 |
| ----- | ------ | --------------------------- |
| path  | string | Returns selected file path. |
| token | string | Defines TOKEN_TEST.         |

Return Type

| Type            | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| Promise\<void\> | If the operation is successful, put shared key file will success. Otherwise it will throw an error. |


Call Example :

```js
HMSContactShieldModule.putSharedKeyFiles("<path>", "TOKEN_TEST")
    .then((res) => {
        alert("Response: " + res);
    })
    .catch((err) => {
        alert("Error: " + err);
    })
```
##### HMSContactShieldModule.putSharedKeyFilesCallback(path,token)

Provides the shared key list file obtained from the diagnosis server to the Contact Shield SDK for future calls of APIs such as getContactSketch().

Parameters

| Name  | Type   | Description                 |
| ----- | ------ | --------------------------- |
| path  | string | Returns selected file path. |
| token | string | Defines TOKEN_TEST.         |


Return Type

| Type            | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| Promise\<void\> | If the operation is successful, put shared key file will success. Otherwise it will throw an error. |


Call Example :

```js
HMSContactShieldModule.putSharedKeyFilesCallback("<path>", "TOKEN_TEST")
    .then((res) => {
        alert("Response: " + res);
    })
    .catch((err) => {
        alert("Error: " + err);
    })
```

##### HMSContactShieldModule.getContactWindow(token)

Obtains the latest diagnosis result details from Contact Shield in Window mode.

Parameters

| Name  | Type   | Description                                        |
| ----- | ------ | -------------------------------------------------- |
| token | string | Token is defined by HMSTokenMode.TOKEN_A constant. |

Return Type

| Type                             | Description                                                  |
| -------------------------------- | ------------------------------------------------------------ |
| Promise\<ContactWindow[]\>   | When token = TOKEN_A, the Window mode is enabled. When getContactWindow() is called to obtain the latest diagnosis result, a ContactWindow list is returned. |


Call Example :

```js
HMSContactShieldModule.getContactWindow(HMSTokenMode.TOKEN_A)
    .then((res) => {
        alert("Response: " + JSON.stringify(res));
    })
    .catch((err) => {
        alert("Error: " + err);
    });
```
##### HMSContactShieldModule.clearAllData()

This API is called to delete all data stored on the device by Contact Shield. Deletes all data stored on the device by Contact Shield, including periodic keys, historical shared keys detected, supplementary data, and diagnosis records.

Return Type

| Type            | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| Promise\<void\> | If the operation is successful, clear all data will success. Otherwise it will throw an error. |


Call Example :

```js
HMSContactShieldModule.clearAllData()
    .then((res) => {
        alert("Response: " + res);
    })
    .catch((err) => {
        alert("Error: " + err);
    });
```

##### HMSContactShieldModule.getPeriodicKey()

Obtains the periodic key list from the Contact Shield SDK. Before calling this method, your app must obtain the user's authorization. Your app can upload these periodic keys to the diagnosis server.

Return Type

| Type                           | Description                                                  |
| ------------------------------ | ------------------------------------------------------------ |
| Promise\<PeriodicKey[]\>   | If the operation is successful, app can upload these periodic keys to the diagnosis server. Otherwise it will throw an error. |


Call Example :

```js
HMSContactShieldModule.getPeriodicKey()
    .then((res) => {
        alert("Response: " + JSON.stringify(res));
    })
    .catch((err) => {
        alert("Error: " + err);
    });
```

##### HMSContactShieldModule.isContactShieldRunning()

Checks whether Contact Shield is running.

Return Type

| Type            | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| Promise\<void\> | If the operation is successful, contact shield running success. Otherwise it will throw an error. |


Call Example :

```js
HMSContactShieldModule.isContactShieldRunning()
    .then((res) => {
        alert("Response: " + res);
    })
    .catch((err) => {
        alert("Error: " + err);
    });

```

##### HMSContactShieldModule.getDiagnosisConfiguration()

This API is called default diagnosis configurations for Contact Shield. 


Return Type

| Type                              | Description                                                  |
| --------------------------------- | ------------------------------------------------------------ |
| Promise\<DiagnosisConfiguration\> | If the operation is successful, returns default diagnosis configuration. Otherwise it will throw an error. |


Call Example :

```js
HMSContactShieldModule.getDiagnosisConfiguration()
    .then((res) => {
        alert("Response: " + JSON.stringify(res));
    })
    .catch((err) => {
        alert("Error: " + err);
    });
```

##### HMSContactShieldModule.enableLogger()

This method enables HMSLogger capability which is used for sending usage analytics of Contact Shield SDK's methods to improve the service quality.

Return Type

| Type | Description                                          |
| ---- | ---------------------------------------------------- |
| void | If the operation is successful, a logger is enabled. |

Call Example

```js
HMSContactShieldModule.enableLogger()
```


##### HMSContactShieldModule.disableLogger()

This method disables HMSLogger capability which is used for sending usage analytics of Contact Shield SDK's methods to improve the service quality.

Return Type

| Type | Description                                           |
| ---- | ----------------------------------------------------- |
| void | If the operation is successful, a logger is disabled. |


Call Example

```js
HMSContactShieldModule.disableLogger()
```

### Data Types

#### ContactDetail

| Field Name           | Type     | Description                                                  |
| -------------------- | -------- | ------------------------------------------------------------ |
| attenuationDurations | number[] | Obtains the time-related details for the contact in an array. The unit is minute. |
| attenuationRiskValue | number   | Obtains the signal attenuation level during the contact.     |
| dayNumber            | number   | Obtains the number of days elapsed since the contact.        |
| durationMinutes      | number   | Obtains the duration of the contact, in minutes.             |
| initialRiskLevel     | number   | Obtains the initial risk level corresponding to a shared key that is successfully matched. |
| totalRiskValue       | number   | Obtains the current risk value corresponding to a shared key that is successfully matched. |

#### ContactSketch

| Field Name           | Type     | Description                                                  |
| -------------------- | -------- | ------------------------------------------------------------ |
| attenuationDurations | number[] | Obtains the time-related details for the contact in an array. The unit is minute. |
| daysSinceLastHint    | number   | Obtains the number of days elapsed since the contact.        |
| maxRiskValue         | number   | Obtains the highest risk level among all shared keys that are successfully matched. |
| numberOfHits         | number   | Obtains the number of shared keys that are successfully matched. |
| summationRiskValue   | number   | Obtains the sum of contact risk values of all matched shared keys. |

#### ContactWindow

| Field Name    | Type                                                         | Description                                                  |
| ------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| dateMillis    | number                                                       | Obtains the date when the contact occurs.                    |
| getReportType | number                                                       | Obtains the diagnosis type that is set when a shared key is uploaded. |
| scanInfos     | [ScanInfo[\]](x-wc://file=en-us_topic_0000001063069573.html) | Obtains the ScanInfo list recorded during the contact.       |

#### PeriodicKey

| Field Name           | Type     | Description                                                  |
| -------------------- | -------- | ------------------------------------------------------------ |
| content              | number[] | Obtains the periodic key content.                            |
| initialRiskLevel     | number   | Obtains the initial risk level corresponding to the periodic key. |
| periodicKeyLifeTime  | number   | Obtains the number of time segments elapsed since the periodic key takes effect. |
| periodicKeyValidTime | number   | Obtains the time segment to which the periodic key effective time belongs. |
| reportType           | number   | Obtains the diagnosis type that is set when shared key is uploaded |

#### DiagnosisConfiguration

| Field Name                    | Type     | Description                                                  |
| ----------------------------- | -------- | ------------------------------------------------------------ |
| attenuationDurationThresholds | number[] | Obtains signal attenuation threshold details.                |
| attenuationRiskValue          | number[] | Obtains signal attenuation risk values.                      |
| daysAfterContactedRiskValues  | number[] | Obtains the risk value associated with the last contact based on the number of days elapsed since the last contact. |
| durationRiskValues            | number[] | Obtains the risk value associated with the last contact based on the duration of the last contact. |
| initialRiskLevelRiskValues    | number[] | Obtains the initial contact risk value.                      |
| minimumRiskValueThresold      | number   | Obtains the lowest risk value that would be recorded.        |

#### ScanInfo

| Field Name           | Type   | Description                                                  |
| -------------------- | ------ | ------------------------------------------------------------ |
| averageAttenuation   | number | Obtains the average of all attenuations detected during the scanning. |
| minimumAttenuation   | number | Obtains the minimum attenuation.                             |
| secondsSinceLastScan | number | Obtains the number of seconds elapsed since last scanning.   |


### Constants

#### HMSStatusCodeConstants

| Constant Fields                     | Value | Definition                                                   |
| ----------------------------------- | ----- | ------------------------------------------------------------ |
| STATUS_SUCCESS                      | 0     | Operation successful                                         |
| STATUS_FAILURE                      | -1    | Operation failed                                             |
| STATUS_API_DISORDER                 | 8001  | Call the startContactShield() API before calling other APIs. |
| STATUS_APP_QUOTA_LIMITED            | 8100  | This error code is returned when startContactShield() is called. The error cause is that multiple apps use Contact Shield at the same time. This error code is returned when putSharedKeyFiles() is called. The error cause is that the number of call times exceeds the upper limit. If the WINDOW mode is used, that is, TOKEN_WINDOW_MODE, a maximum of 60 calls are allowed within 24 hours. A common token can be called for a maximum of 200 times within 24 hours. |
| STATUS_DISK_FULL                    | 8101  | The storage space is insufficient.                           |
| STATUS_BLUETOOTH_OPERATION_ERROR    | 8102  | An error occurred when setting Bluetooth.                    |
| STATUS_MISSING_PERMISSION_BLUETOOTH | 8016  | No Bluetooth permission                                      |
| STATUS_MISSING_SETTING_LOCATION_ON  | 8020  | The location permission must be enabled.                     |
| STATUS_INTERNAL_ERROR               | 8060  | Internal error. For details, please refer to the error description. |
| STATUS_MISSING_PERMISSION_INTERNET  | 8064  | The network permission is missing in Contact Shield.         |



#### HMSTokenModeConstants

| Constant Fields | Value               | Definition                  |
| --------------- | ------------------- | --------------------------- |
| TOKEN_A         | "TOKEN_WINDOW_MODE" | Value to token window mode. |

#### HMSContactShieldSettingConstants

| Constant Fields | Value | Definition         |
| --------------- | ----- | ------------------ |
| DEFAULT         | 14    | Incubation period. |


#### HMSRiskLevelConstants

| Constant Fields        | Value | Definition                     |
| ---------------------- | ----- | ------------------------------ |
| RISK_LEVEL_INVALID     | 0     | Exposes RISK_LEVEL_INVALID     |
| RISK_LEVEL_LOWEST      | 1     | Exposes RISK_LEVEL_LOWEST      |
| RISK_LEVEL_LOW         | 2     | Exposes RISK_LEVEL_LOWEST      |
| RISK_LEVEL_MEDIUM_LOW  | 3     | Exposes RISK_LEVEL_MEDIUM_LOW  |
| RISK_LEVEL_MEDIUM      | 4     | Exposes RISK_LEVEL_MEDIUM      |
| RISK_LEVEL_MEDIUM_HIGH | 5     | Exposes RISK_LEVEL_MEDIUM_HIGH |
| RISK_LEVEL_HIGH        | 6     | Exposes RISK_LEVEL_HIGH        |
| RISK_LEVEL_EXT_HIGH    | 7     | Exposes RISK_LEVEL_EXT_HIGH    |
| RISK_LEVEL_HIGHEST     | 8     | Exposes RISK_LEVEL_HIGHEST     |


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

### Contact Shield Permissions 
To use Contact Shield you need to declare required permissions inside **AndroidManifest.xml** file as shown below. 

```xml
<manifest ...>
    <uses-permission android:name="android.permission.INTERNET " />
    <uses-permission android:name="android.permission.BLUETOOTH" />

  <application ...>
    <activity ...>
      <!-- Other configurations -->
    </activity>
  </application>
</manifest>
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
