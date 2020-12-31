# React-Native HMS Awareness

---

## Contents

- [1. Introduction](#1-introduction)
- [2. Installation Guide](#2-installation-guide)
  - [Creating Project in App Gallery Connect](#creating-project-in-app-gallery-connect)
  - [Configuring the Signing Certificate Fingerprint](#configuring-the-signing-certificate-fingerprint)
  - [Integrating React Native Awareness Plugin](#integrating-react-native-awareness-plugin)
- [3. API Reference](#3-api-reference)
  - [Modules](#modules)
    - [HMSAwarenessCaptureModule](#1hmsawarenesscapturemodule)
    - [HMSAwarenessBarrierModule](#2hmsawarenessbarriermodule)
    - [HMSLoggerModule](#3hmsloggermodule)
  - [Data Types](#data-types)
  - [Constants](#constants)
- [4. Configuration and Description](#4-configuration-and-description)
- [5. Sample Project](#5-sample-project)
- [6. Questions or Issues](#6-questions-or-issues)
- [7. Licensing and Terms](#7-licensing-and-terms)

---

## 1. Introduction

This plugin enables communication between Huawei Awareness SDK and React Native platform. HUAWEI's Awareness service provides your app with the ability to obtain contextual information including users' current time, location, behavior, audio device status, ambient light, weather, and nearby beacons. Your app can gain insight into a user's current situation more efficiently, making it possible to deliver a smarter, more considerate user experience. Users can purchase a variety of virtual products, including one-time virtual products and subscriptions, directly within your app.

### HMSAwarenessCaptureModule
The HMSAwarenessCaptureModule allows your app to request the current user status, such as time, location, behavior, and whether a headset is connected. For example, after your app runs, it can request the user's time and location in order to recommend entertainment activities available nearby at weekends to the user.
- **getBeaconStatus**: Uses a variable number of the filters parameters to obtain beacon information.
- **getBehavior**: Obtains current user behavior, for example, running, walking, cycling, or driving.
- **getHeadsetStatus**: Obtains headset connection status.
- **getLocation**: Re-obtains the latest device location information (latitude and longitude).
- **getCurrentLocation**: Obtains the current location (latitude and longitude) of a device. 
- **getTimeCategories**: Obtains the current time. 
- **getTimeCategoriesByUser**: Obtains the current time of a specified location. 
- **getTimeCategoriesByCountryCode**: Obtains the current time by country/region code that complies with ISO 3166-1 alpha-2.
- **getTimeCategoriesByIP**: Obtains the current time by IP address.
- **getTimeCategoriesForFuture**: Obtains the time of a specified date by IP address. 
- **getLightIntensity**: Obtains the illuminance.
- **getWeatherByDevice**: Obtains the weather of the current location of a device.
- **getWeatherByPosition**: Obtains weather information about a specified address.
- **getBluetoothStatus**: Obtains the Bluetooth connection status.
- **querySupportingCapabilities**: Obtains capabilities supported by Awareness RN Plugin on the current device.
- **getScreenStatus**: Obtains the screen status response of a device.
- **getWifiStatus**: Obtains the Wi-Fi connection status of a device.
- **getApplicationStatus**: Obtains the app status of a device.
- **getDarkModeStatus**: Obtains the dark mode status of a device.
- **enableUpdateWindow**: Indicates whether to display a dialog box before Awareness Kit or HMS Core (APK) starts an upgrade in your app.

### HMSAwarenessBarrierModule
The HMSAwarenessBarrierModule allows your app to set a barrier for specific contextual conditions. When the conditions are met, your app will receive a notification. For example, a notification is triggered when an audio device connects to a mobile phone for an audio device status barrier about connection or illuminance is less than 100 lux for an ambient light barrier whose trigger condition is set for illuminance that is less than 100 lux. You can also combine different types of barriers for your app to support different use cases. For example, your app can recommend nearby services if the user has stayed in a specified business zone (geofence) for the preset period of time (time barrier).
- **queryBarrier**: Queries the current status of a target barrier.
- **queryBarrierAll**: Queries the current state of all registered barriers.
- **updateBarrier**: Adds barriers. If a barrier is added using a key that has been registered, the new barrier will replace the original one. You can also specify whether to display a dialog box before initiating an upgrade in your React Native Awareness Plugin or HMS Core (APK) application.
- **combinationBarrier**: You can use logical operations("and", "or", "not") in a single barrier by combining multiple features. For example, you can create a barrier that is triggered when both bluetooth and wifi states connect.
- **deleteBarrier**: Allows you to delete the target barrier.
- **deleteAllBarrier**: Allows you to delete all saved barriers. 

### HMSLoggerModule
This module Includes the functions for enabling and disabling HMSLogger capability which is used for sending usage analytics of DTM SDK's functions in order to improve the service quality. HMSLogger is enabled by default on the Huawei Awareness Plugin for React Native, it can be disabled with the disableLogger() function.
- **enableLogger**: Enables HMS Plugin function Analytics.
- **disableLogger**: Disables HMS Plugin function Analytics. 

---

## 2. Installation Guide

Before you get started, you must register as a HUAWEI developer and complete identity verification on the [HUAWEI Developer](https://developer.huawei.com/consumer/en/) website. For details, please refer to [Register a HUAWEI ID](https://developer.huawei.com/consumer/en/doc/10104).

Create an app in your project is required in AppGallery Connect in order to communicate with Huawei services. To create an app, perform the following steps:

### Creating Project in App Gallery Connect

**Step 1.** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en) and select **My projects**.

**Step 2.** Click your project from the project list.

**Step 3.** Go to **Project Setting** > **General information**, and click **Add app**. If an app exists in the project, and you need to add a new one, expand the app selection area on the top of the page and click **Add app**.

**Step 4.** On the **Add app** page, enter app information, and click **OK**.

- A signing certificate fingerprint is used to verify the authenticity of an app when it attempts to access an HMS Core service through the HMS Core SDK. Before using HMS Core (APK), you must locally generate a signing certificate fingerprint and configure it in AppGallery Connect. Ensure that the JDK has been installed on your computer.

- To use HUAWEI Awareness, you need to enable the Awareness service. For details, please refer to [Enabling Services](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-enable_service#h1-1574822945685).

### Configuring the Signing Certificate Fingerprint

**Step 1.** Go to **Project Setting** > **General information**. In the **App information** field, click the icon next to SHA-256 certificate fingerprint, and enter the obtained **SHA256 certificate fingerprint**.

**Step 2.** After completing the configuration, click check mark.

### Integrating React Native Awareness Plugin

**Step 1.** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and select **My projects**.

**Step 2.** Find your app project, and click the desired app name.

**Step 3.** Go to **Project Setting** > **General information**. In the **App information** section, click **agconnect-service.json** to download the configuration file.

**Step 4.** Create a React Native project if you do not have one.

**Step 5.** Copy the **agconnect-service.json** file to the **android/app** directory of your React Native project.

**Step 6.** Copy the signature file that generated in [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#3) section, to the android/app directory of your React Native project.

**Step 7.** Check whether the **agconnect-services.json** file and signature file are successfully added to the **android/app** directory of the React Native project.

**Step 8.** Open the **build.gradle** file in the **android** directory of your React Native project.

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
      classpath 'com.huawei.agconnect:agcp:{latestVersion}'    
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

- Change the value of minSdkVersion in buildscript to 24.

```groovy
buildscript {
    ext {
        minSdkVersion = 24
       /*
        * <Other configurations>
        */
    }
   /*
    * <Other configurations>
    */
}
```

**Step 9.** Open the **build.gradle** file in the **android/app** directory of your React Native project.

- Add **apply plugin:"com.huawei.agconnect"** to the top of the file as shown below. 
```groovy
apply plugin: "com.android.application"
apply plugin: "com.huawei.agconnect"
```

- Set your package name in **defaultConfig** > **applicationId** and set **minSdkVersion** to **24** or higher.

```groovy
defaultConfig {
  applicationId "<package_name>"
  minSdkVersion rootProject.ext.minSdkVersion
  targetSdkVersion rootProject.ext.targetSdkVersion  
  /*
   * <Other configurations>
   */
}
```

- Package name must match with the **package_name** entry in **agconnect-services.json** file.

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

- Copy the signature file that generated in [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#3) to **android/app** directory.

- Configure the signature in **android** according to the signature file information and configure Obfuscation Scripts.

#### Using NPM

**Step 1.**  Download plugin using command below.

```bash
npm i @hmscore/react-native-hms-awareness
```

**Step 2.**  Run your project. 

- Run the following command to the project directory.

```bash
react-native run-android  
```

#### Download Link
To integrate the plugin, follow the steps below:

**Step 1.** Download the React Native Awareness Plugin and place **react-native-hms-awareness** under **node_modules/@hmscore** of your React Native project, as shown in the directory tree below.

            demo-app
                |_ node_modules
                    |_ @hmscore
                        |_ react-native-hms-awareness
                        ...

**Step 2.** Open build.gradle file which is located under project.dir > android > app directory.

- Configure build dependencies.

```groovy
buildscript {
  ...
  dependencies {
    /*
    * <Other dependencies>
    */
    implementation project(":react-native-hms-awareness")    
    ...    
  }
}
```

**Step 3.** Add the following lines to the android/settings.gradle file in your project:

```groovy
include ':app'
include ':react-native-hms-awareness'
project(':react-native-hms-awareness').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-awareness/android')
```

**Step 4.**  Open the your application class and add the **HMSAwarenessPackage()**

Import the following classes to the your application file of your project.import **com.huawei.hms.rn.awareness.HMSAwarenessPackage**. 

Then, add the **HMSAwarenessPackage** to your **getPackages** method. In the end, your file will be similar to the following:

```java 
import com.huawei.hms.rn.awareness.HMSAwarenessPackage;

@Override
protected List<ReactPackage> getPackages() {
  List<ReactPackage> packages = new PackageList(this).getPackages();
  packages.add(new HMSAwarenessPackage()); // <-- Add this line 
  return packages;
}
...
```

**Step 5.** To listen for barrier triggers while the application is closed, add the BackgroundBarrierReceiver and the TaskService to your application's Manifest file.

```groovy
<receiver
  android:name="com.huawei.hms.rn.awareness.utils.BackgroundBarrierReceiver"
  android:enabled="true"
  android:exported="false">
  <intent-filter>
    <action android:name="com.huawei.hms.rn.awareness.modules.ReceiverAction"/>
  </intent-filter>
</receiver>
<service android:name="com.huawei.hms.rn.awareness.utils.TaskService" />
```

**Step 6.**  Run your project. 

- Run the following command to the project directory.

```bash
react-native run-android  
```

---

## 3. API Reference

### Modules

The React Native plugin enables communication between HUAWEI Awareness and the React Native platform. This plugin exposes functionalities provided by HUAWEI Awareness. 

| Modules                                                   | Description                                                  |
| --------------------------------------------------------- | ------------------------------------------------------------ |
| [HMSAwarenessCaptureModule](#31hmsawarenesscapturemodule) | The HMSAwarenessCaptureModule allows your app to request the current user status, such as time, location, behavior, and whether a headset is connected. |
| [HMSAwarenessBarrierModule](#32hmsawarenessbarriermodule) | The HMSAwarenessBarrierModule allows your app to set a barrier for specific contextual conditions. When the conditions are met, your app will receive a notification. |
| [HMSLoggerModule](#33hmsloggermodule)                     | This module Includes the functions for enabling and disabling HMSLogger capability which is used for sending usage analytics of Awareness SDK's functions in order to improve the service quality. HMSLogger is enabled by default on the Huawei Awareness Plugin for React Native, it can be disabled with the disableLogger() function. |


#### 1.HMSAwarenessCaptureModule 

##### Function Summary

| Function                                                     | Return Type                                                  | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| [getBeaconStatus(beaconStatusReq)](#hmsawarenesscapturemodulegetbeaconstatusbeaconstatusreq) | Promise\<[BeaconStatusResponse](#beaconstatusresponse)>      | Uses a variable number of the filters parameters to obtain beacon information. |
| [getBehavior()](#hmsawarenesscapturemodulegetbehavior)       | Promise\<[BehaviorResponse](#behaviorResponse)>              | Obtains current user behavior, for example, running, walking, cycling, or driving. |
| [getHeadsetStatus()](#hmsawarenesscapturemodulegetheadsetstatus) | Promise\<[HeadsetStatusResponse](#headsetresponse)>          | Obtains headset connection status.                           |
| [getLocation()](#hmsawarenesscapturemodulegetlocation)       | Promise\<[LocationResponse](#locationResponse)>              | Re-obtains the latest device location information (latitude and longitude). |
| [getCurrentLocation()](#hmsawarenesscapturemodulegetcurrentlocation) | Promise\<[LocationResponse](#locationresponse)>              | Obtains the current location (latitude and longitude) of a device. |
| [getTimeCategories()](#hmsawarenesscapturemodulegettimecategories) | Promise\<[TimeCategoriesResponse](#timecategoriesresponse)>  | Obtains the current time.                                    |
| [getTimeCategoriesByUser(timeCategoriesByUserReq)](#hmsawarenesscapturemodulegettimecategoriesbyusertimecategoriesbyuserreq) | Promise\<[TimeCategoriesResponse](#timecategoriesresponse)>  | Obtains the current time of a specified location.            |
| [getTimeCategoriesByCountryCode(timeCategoriesByCountryCodeReq)](#hmsawarenesscapturemodulegettimecategoriesbycountrycodetimecategoriesbycontrycodereq) | Promise\<[TimeCategoriesResponse](#timecategoriesresponse)>  | Obtains the current time by country/region code that complies with ISO 3166-1 alpha-2. |
| [getTimeCategoriesByIP()](#hmsawarenesscapturemodulegettimecategoriesbyip) | Promise\<[TimeCategoriesResponse](#timecategoriesresponse)>  | Obtains the current time by IP address.                      |
| [getTimeCategoriesForFuture(timeCategoriesForFutureReq)](#hmsawarenesscapturemodulegettimecategoriesforfuturetimecategoriesforfuturereq) | Promise\<[TimeCategoriesResponse](#timecategoriesresponse)>  | Obtains the time of a specified date by IP address.          |
| [getLightIntensity()](#hmsawarenesscapturemodulegetlightintensity) | Promise\<[AmbientLightResponse](#ambientlightresponse)>      | Obtains the illuminance.                                     |
| [getWeatherByDevice()](#hmsawarenesscapturemodulegetweatherbydevice) | Promise\<[WeatherStatusResponse](#weatherstatusresponse)>    | Obtains the weather of the current location of a device.     |
| [getWeatherByPosition(weatherByPositionReq)](#hmsawarenesscapturemodulegetweatherbypositionweatherbypositionreq) | Promise\<[WeatherStatusResponse](#weatherstatusresponse)>    | Obtains weather information about a specified address.       |
| [getBluetoothStatus()](#hmsawarenesscapturemodulegetbluetoothstatus) | Promise\<[BluetoothStatusResponse](#bluetoothstatusresponse)> | Obtains the Bluetooth connection status.                     |
| [querySupportingCapabilities()](#hmsawarenesscapturemodulequerysupportingcapabilities) | Promise\<[CapabilityResponse](#capabilityresponse)>          | Obtains capabilities supported by Awareness React Native Awareness Plugin on the current device. |
| [getScreenStatus()](#hmsawarenesscapturemodulegetscreenstatus) | Promise\<[ScreenStatusResponse](#screenstatusresponse)>      | Obtains the screen status response of a device.              |
| [getWifiStatus()](#hmsawarenesscapturemodulegetwifistatus)   | Promise\<[WifiStatusResponse](#wifistatusresponse)>          | Obtains the Wi-Fi connection status of a device.             |
| [getApplicationStatus()](#hmsawarenesscapturemodulegetapplicationstatus) | Promise\<[ApplicationStatusResponse](#applicationstatusresponse)> | Obtains the app status of a device.                          |
| [getDarkModeStatus()](#hmsawarenesscapturemodulegetdarkmodestatus) | Promise\<[DarkModeStatusResponse](#darkmodestatusresponse)>  | Obtains the dark mode status of a device.                    |
| [enableUpdateWindow(isEnabled)](#hmsawarenesscapturemoduleenableupdatewindowisenabled) | Promise\<[EnableUpdateWindowResponse](#enableupdatewindowresponse)> | Indicates whether to display a dialog box before Awareness Kit or HMS Core (APK) starts an upgrade in your app. |

##### Functions

###### HMSAwarenessCaptureModule.getBeaconStatus(beaconStatusReq) 

Uses a variable number of the filters parameters to obtain beacon information.

| Parameter     | Type                              |
| --------------- | ------------------------------------------- |
| beaconStatusReq | [BeaconStatusReq](#beaconstatusreq) object. |


| Return Type                                             | Description                                                  |
| ------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[BeaconStatusResponse](#beaconstatusresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [BeaconStatusResponse](#beaconstatusresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Create a request object.
const beaconStatusReq = {
    "namespace": "dev736430079244559178",
    "type": "ibeacon",
    "content": "content"
}

//Call the getBeaconStatus API.
HMSAwarenessCaptureModule.getBeaconStatus(beaconStatusReq)
    .then((beaconStatusResponse) => {
    	console.log("getBeaconStatus:", JSON.stringify(beaconStatusResponse))
    })
    .catch((errorObject) => {
    	console.log("getBehavior:", JSON.stringify(errorObject)
    })
```

###### HMSAwarenessCaptureModule.getBehavior()

Obtains current user behavior, for example, running, walking, cycling, or driving.

| Return Type                                     | Description                                                  |
| ----------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[BehaviorResponse](#behaviorResponse)> | A task containing the API request result through the promise instance. In the success scenario, an [BehaviorResponse](#behaviorResponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getBehavior API.
HMSAwarenessCaptureModule.getBehavior()
  .then((behaviorResponse) => {
    console.log("getBehavior:", JSON.stringify(behaviorResponse));
  })
  .catch((errorobject) => {
    console.log("getBehavior:", JSON.stringify(errorobject));
  });
```

###### HMSAwarenessCaptureModule.getHeadsetStatus()

Obtains headset connection status.

| Name                                                | Description                                                  |
| --------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[HeadsetStatusResponse](#headsetresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [HeadsetStatusResponse](#headsetresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getHeadsetStatus API.
HMSAwarenessCaptureModule.getBehavior()
    .then((headsetStatusResponse) => {
      console.log("getBeaconStatus:", JSON.stringify(headsetStatusResponse))
    })
    .catch((errorObject) => {
      console.log("getBehavior:", JSON.stringify(errorObject))
    })
```

###### HMSAwarenessCaptureModule.getLocation() 

Re-obtains the latest device location information (latitude and longitude).

| Name                                                | Description                                                  |
| --------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[LocationResponse](#headsetresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [LocationResponse](#headsetresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getLocation API.
HMSAwarenessCaptureModule.getBehavior()
  .then((locationResponse) => {
    console.log("getLocation:", JSON.stringify(locationResponse));
  })
  .catch((errorobject) => {
    console.log("getLocation:", JSON.stringify(errorobject));
  });
```

###### HMSAwarenessCaptureModule.getCurrentLocation()

Obtains the current location (latitude and longitude) of a device. 

| Return Type                                    | Description                                                  |
| ---------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[LocationResponse](#headsetresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [LocationResponse](#headsetresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getCurrentLocation API.
HMSAwarenessCaptureModule.getCurrentLocation()
  .then((locationResponse) => {
    console.log("getCurrentLocation:", JSON.stringify(locationResponse));
  })
  .catch((errorobject) => {
    console.log("getCurrentLocation:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getTimeCategories()

Obtains the current time.

| Return Type                                                 | Description                                                  |
| ----------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[TimeCategoriesResponse](#timecategoriesresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [TimeCategoriesResponse](#timecategoriesresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getTimeCategories API.
HMSAwarenessCaptureModule.getTimeCategories()
  .then((timeCategoriesResponse) => {
    console.log("getTimeCategories:", JSON.stringify(timeCategoriesResponse));
  })
  .catch((errorobject) => {
    console.log("getTimeCategories:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getTimeCategoriesByUser(timeCategoriesByUserReq)

Obtains the current time of a specified location.

| Parameter    | Description                                                  |
| ------- | ------------------------------------------------------------ |
| timeCategoriesByUserReq | [TimeCategoriesByUserReq](#timecategoriesbyuserreq) object. |

| Return Type                                                 | Description                                                  |
| ----------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[TimeCategoriesResponse](#timecategoriesresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [TimeCategoriesResponse](#timecategoriesresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Create a request object.
const timeCategoriesByUserReq = {
  latitude: 36.314909917631724,
  longitude: 30.166312199567606,
};

//Call the getTimeCategoriesByUser API.
HMSAwarenessCaptureModule.getTimeCategoriesByUser(timeCategoriesByUserReq)
  .then((timeCategoriesResponse) => {
    console.log(
      "getTimeCategoriesByUser:",
      JSON.stringify(timeCategoriesResponse)
    );
  })
  .catch((errorobject) => {
    console.log("getTimeCategoriesByUser:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getTimeCategoriesByCountryCode(timeCategoriesByCountryCodeReq)

Obtains the current time by country/region code that complies with ISO 3166-1 alpha-2. 


| Parameter    | Type                                    |
| ------- | ------------------------------------------------- |
| timeCategoriesByCountryCodeReq | [TimeCategoriesByCountryCodeReq](#timecategoriesbycountrycodereq) object. |

| Return Type                                                 | Description                                                  |
| ----------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[TimeCategoriesResponse](#timecategoriesresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [TimeCategoriesResponse](#timecategoriesresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getTimeCategoriesByUser API.
HMSAwarenessCaptureModule.getTimeCategoriesByCountryCode(
  TimeCategoriesByCountryCodeReq
)
  .then((timeCategoriesResponse) => {
    console.log(
      "getTimeCategoriesByCountryCode:",
      JSON.stringify(timeCategoriesResponse)
    );
  })
  .catch((errorobject) => {
    console.log("getTimeCategoriesByCountryCode:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getTimeCategoriesByIP()

Obtains the current time by IP address. 

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise\<[TimeCategoriesResponse](#timecategoriesresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [TimeCategoriesResponse](#timecategoriesresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getTimeCategoriesByIP API.
HMSAwarenessCaptureModule.getTimeCategoriesByIP()
  .then((timeCategoriesResponse) => {
    console.log(
      "getTimeCategoriesByIP:",
      JSON.stringify(timeCategoriesResponse)
    );
  })
  .catch((errorobject) => {
    console.log("getTimeCategoriesByIP:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getTimeCategoriesForFuture(timeCategoriesForFutureReq)

Obtains the time of a specified date by IP address.

| Parameter                  | Type                                                         |
| -------------------------- | ------------------------------------------------------------ |
| timeCategoriesForFutureReq | [TimeCategoriesForFutureReq](#timecategoriesforfuturereq) object. |

| Return Type                                                 | Description                                                  |
| ----------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[TimeCategoriesResponse](#timecategoriesresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [TimeCategoriesResponse](#timecategoriesresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Create a request object.
const timeCategoriesForFutureReq = {
  futureTimestamp: 1606906743000.0,
};

//Call the getTimeCategoriesByIP API.
HMSAwarenessCaptureModule.getTimeCategoriesForFuture(timeCategoriesForFutureReq)
  .then((timeCategoriesResponse) => {
    console.log(
      "getTimeCategoriesForFuture:",
      JSON.stringify(timeCategoriesResponse)
    );
  })
  .catch((errorobject) => {
    console.log("getTimeCategoriesForFuture:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getLightIntensity()

Obtains the illuminance.

| Return Type                                             | Description                                                  |
| ------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[AmbientLightResponse](#ambientlightresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [AmbientLightResponse](#ambientlightresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getLightIntensity API.
HMSAwarenessCaptureModule.getLightIntensity()
  .then((ambientLightResponse) => {
    console.log("getLightIntensity:", JSON.stringify(ambientLightResponse));
  })
  .catch((errorobject) => {
    console.log("getLightIntensity:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getWeatherByDevice()

Obtains the weather of the current location of a device.

| Return Type                                               | Description                                                  |
| --------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[WeatherStatusResponse](#weatherstatusresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [WeatherStatusResponse](#weatherstatusresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getWeatherByDevice API.
HMSAwarenessCaptureModule.getWeatherByDevice()
  .then((weatherStatusResponse) => {
    console.log("getWeatherByDevice:", JSON.stringify(weatherStatusResponse));
  })
  .catch((errorobject) => {
    console.log("getWeatherByDevice:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getWeatherByPosition(weatherByPositionReq)

Obtains the weather status response by IP address.

| Parameter            | Type                                                  |
| -------------------- | ----------------------------------------------------- |
| weatherByPositionReq | [WeatherByPositionReq](#weatherbypositionreq) object. |

| Return Type                                               | Description                                                  |
| --------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[WeatherStatusResponse](#weatherstatusresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [WeatherStatusResponse](#weatherstatusresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Create a request object.
const weatherByPositionReq = {
    city: "London",
    locale: "en_GB",
    country: "United Kingdom",
    province: "London"
}

//Call the getWeatherByPosition API.
HMSAwarenessCaptureModule.getWeatherByPosition(weatherByPositionReq)
  .then((weatherStatusResponse) => {
    console.log("weatherStatusResponse:",JSON.stringify(WeatherStatusResponse))
  })
  .catch((errorobject) => {
    console.log("WeatherStatusResponse:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getBluetoothStatus()

Obtains the Bluetooth connection status.

| Return Type                                                  | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Promise\<[BluetoothStatusResponse](#bluetoothstatusresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [BluetoothStatusResponse](#bluetoothstatusresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getBluetoothStatus API.
HMSAwarenessCaptureModule.getBluetoothStatus()
  .then((bluetoothStatusResponse) => {
    console.log("getBluetoothStatus:", JSON.stringify(bluetoothStatusResponse));
  })
  .catch((errorobject) => {
    console.log("getBluetoothStatus:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.querySupportingCapabilities()

Obtains capabilities supported by React Native Awareness Plugin on the current device.

| Return Type                                         | Description                                                  |
| --------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[CapabilityResponse](#capabilityresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [CapabilityResponse](#capabilityresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the querySupportingCapabilities API.
HMSAwarenessCaptureModule.querySupportingCapabilities()
  .then((capabilityResponse) => {
    console.log("querySupportingCapabilities:",JSON.stringify(capabilityResponse))
  })
  .catch((errorobject) => {
    console.log("querySupportingCapabilities:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getScreenStatus()

Obtains the screen status response of a device.

| Return Type                                             | Description                                                  |
| ------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[ScreenStatusResponse](#screenstatusresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [ScreenStatusResponse](#screenstatusresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getScreenStatus API.
HMSAwarenessCaptureModule.getScreenStatus()
  .then((screenStatusResponse) => {
    console.log("getScreenStatus:", JSON.stringify(screenStatusResponse));
  })
  .catch((errorobject) => {
    console.log("getScreenStatus:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getWifiStatus()

Obtains the Wi-Fi connection status of a device.

| Return Type                                         | Description                                                  |
| --------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[WifiStatusResponse](#wifistatusresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [WifiStatusResponse](#wifistatusresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getWifiStatus API.
HMSAwarenessCaptureModule.getWifiStatus()
  .then((wifiStatusResponse) => {
    console.log("getWifiStatus:", JSON.stringify(wifiStatusResponse));
  })
  .catch((errorobject) => {
    console.log("getWifiStatus:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getApplicationStatus()

Obtains the app status of a device.

| Return Type                                                  | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Promise\<[ApplicationStatusResponse](#applicationstatusresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [ApplicationStatusResponse](#applicationstatusresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getApplicationStatus API.
HMSAwarenessCaptureModule.getApplicationStatus()
  .then((applicationStatusResponse) => {console.log("getApplicationStatus:",JSON.stringify(applicationStatusResponse))
  })
  .catch((errorobject) => {
    console.log("getApplicationStatus:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.getDarkModeStatus()

Obtains the dark mode status of a device.

| Return Type                                                 | Description                                                  |
| ----------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[DarkModeStatusResponse](#darkmodestatusresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [DarkModeStatusResponse](#darkmodestatusresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the getDarkModeStatus API.
HMSAwarenessCaptureModule.getDarkModeStatus()
  .then((darkModeStatusResponse) => {
    console.log("getDarkModeStatus:", JSON.stringify(darkModeStatusResponse));
  })
  .catch((errorobject) => {
    console.log("getDarkModeStatus:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessCaptureModule.enableUpdateWindow(isEnabled)

Indicates whether to display a dialog box before Awareness Kit or HMS Core (APK) starts an upgrade in your app.

| Parameter | Type    |
| --------- | ------- |
| isEnabled | boolean |

| Return Type                                                  | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Promise\<[EnableUpdateWindowResponse](#enableupdatewindowresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [EnableUpdateWindowResponse](#enableupdatewindowresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Create a request object.
const isEnabled = true

//Call the enableUpdateWindow API.
HMSAwarenessCaptureModule.enableUpdateWindow(isEnabled)
  .then((enableUpdateWindowResponse) => {
    console.log(
      "enableUpdateWindow:",
      JSON.stringify(enableUpdateWindowResponse)
    );
  })
  .catch((errorobject) => {
    console.log("enableUpdateWindow:", JSON.stringify(errorObject))
  });
```

#### 2.HMSAwarenessBarrierModule

##### Function Summary

| Function                                                     | Return Type                                             | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------- | ------------------------------------------------------------ |
| [queryBarrier(queryBarrierReq)](#hmsawarenessbarriermodulequerybarrierquerybarrierreq) | Promise\<[BarrierQueryResponse](#barrierqueryresponse)> | Queries the current status of a target barrier.              |
| [queryBarrierAll()](#hmsawarenessbarriermodulequerybarrierall) | Promise\<[BarrierQueryResponse](#barrierqueryresponse)> | Queries the current state of all registered barriers.        |
| [updateBarrier(barrierEvent,barrierUpdateRequest)](#hmsawarenessbarriermoduleupdatebarrierbarriereventbarrierupdaterequest) | Promise\<[SuccessObject](#successobject)>               | Adds barriers. You can also specify whether to display a dialog box before initiating an upgrade in your React Native Awareness Plugin or HMS Core (APK) application.  If a barrier is added using a key that has been registered, the new barrier will replace the original one. No matter whether the new barrier is registered successfully, all callback requests of the original one will be invalid. |
| [combinationBarrier(barrierLabel, combinationBarrierReq)](#hmsawarenessbarriermodulecombinationbarrierbarrierlabelcombinationbarrierreq) | Promise\<[SuccessObject](#successobject)>               | You can use logical operations("and", "or", "not") in a single barrier by combining multiple features. For example, you can create a barrier that is triggered when both bluetooth and wifi states connect. |
| [deleteBarrier(deleteBarrierReq)](#hmsawarenessbarriermoduledeletebarrierdeletebarrierreq) | Promise\<[SuccessObject](#successobject)>               | Allows you to delete the target barrier.                     |
| [deleteAllBarrier()](#hmsawarenessbarriermoduledeleteallbarrier) | Promise\<[SuccessObject](#successobject)>               | Allows you to delete all saved barriers.                     |
| [setBackgroundNotification(notificationReq)](#hmsawarenessbarriermodulesetbackgroundnotificationnotificationreq) | Promise\<[SuccessObject](#successobject)>               | Allows you to customize notifications.                       |

##### Functions

###### HMSAwarenessBarrierModule.queryBarrier(queryBarrierReq) 

Queries the current status of a target barrier.

| Parameter       | Type                                       |
| --------------- | ------------------------------------------ |
| queryBarrierReq | [QueryBarrierReq](#querybarrierreq) array. |


| Return Type                                             | Description                                                  |
| ------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[BarrierQueryResponse](#barrierqueryresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [BarrierQueryResponse](#barrierqueryresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Create a request object.
const queryBarrierReq = [
    "headset connecting barrier",
    "headset disconnecting barrier",
    "light above barrier",
    "wifi keeping with bssid barrier",
    "wifi keeping barrier"
]

//Call the queryBarrier API.
HMSAwarenessBarrierModule.queryBarrier(queryBarrierReq)
  .then((barrierQueryResponse) => {
    console.log("queryBarrier:", JSON.stringify(barrierQueryResponse));
  })
  .catch((errorobject) => {
    console.log("queryBarrier:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessBarrierModule.queryBarrierAll() 

Queries the current state of all registered barriers.

| Return Type                                             | Description                                                  |
| ------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[BarrierQueryResponse](#barrierqueryresponse)> | A task containing the API request result through the promise instance. In the success scenario, an [BarrierQueryResponse](#barrierqueryresponse) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the queryBarrierAll API.
HMSAwarenessBarrierModule.queryBarrierAll()
  .then((barrierQueryResponse) => {
    console.log("queryBarrierAll:", JSON.stringify(barrierQueryResponse));
  })
  .catch((errorobject) => {
    console.log("queryBarrierAll:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessBarrierModule.updateBarrier(barrierEvent,barrierUpdateRequest) 

Adds barriers. You can also specify whether to display a dialog box before initiating an upgrade in your React Native Awareness Plugin or HMS Core (APK) application.  If a barrier is added using a key that has been registered, the new barrier will replace the original one. No matter whether the new barrier is registered successfully, all callback requests of the original one will be invalid.

| Parameter            | Type                                                         |
| -------------------- | ------------------------------------------------------------ |
| barrierEvent         | string.  You can check [Constants](#constants) for barrier event types. |
| barrierUpdateRequest | [BarrierUpdateRequest](#barrierppdaterequest) object.        |


| Return Type                               | Description                                                  |
| ----------------------------------------- | ------------------------------------------------------------ |
| Promise\<[SuccessObject](#successobject)> | A task containing the API request result through the promise instance. In the success scenario, an [SuccessObject](#successobject) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Create request objects.
const barrierUpdateRequest = behaviorKeepingReq //BehaviorKeepingReq is a data type.
const barrierEvent = HMSAwarenessBarrierModule.EVENT_BEHAVIOR

//Call the updateBarrier API.
HMSAwarenessBarrierModule.updateBarrier(barrierEvent, barrierUpdateRequest)
  .then((SuccessObject) => {
    console.log("updateBarrier:", JSON.stringify(SuccessObject));
  })
  .catch((errorobject) => {
    console.log("updateBarrier:", JSON.stringify(errorObject))
  });
```

The first trigger occurs after your barrier has been successfully registered. You can use the barrierReceiver for this and all triggers that will occur. With the code below you will write the barrier triggers to the index.js file of your application while the application is closed.  This code returns a [BarrierStatus](#barrierStatus) object.

**Sample Code**

```js
eventEmitter.addListener("barrierReceiver", (barrierStatus) => {
    let status = `${barrierStatus.presentStatusName}`;
    let eventType = `${barrierStatus.barrierLabel}`;

    this.setState({
        receiverMessage: `${eventType} - ${status}`,
    });
    console.log(`Barrier Receiver: ${eventType} - ${status}`);
});
```

To listen for barrier triggers while the application is closed, add following code to your application's index.js file. This code returns a [BarrierStatus](#barrierStatus) object. A task is an async function that you register on AppRegistry, similar to registering React applications:

**Sample Code**

```js
AppRegistry.registerHeadlessTask(HMSAwarenessBarrierModule.TASK_NAME, () => async (taskData) => {
    console.log(taskData); 
    ToastAndroid.show(JSON.stringify(taskData), ToastAndroid.SHORT);
});
```

###### HMSAwarenessBarrierModule.combinationBarrier(barrierLabel,combinationBarrierReq) 
You can use logical operations ("and", "or", "not") in a single barrier by combining multiple features. For example, you can create a barrier that is triggered when both bluetooth and wifi states connect.

| Parameter             | Type                                                  |
| --------------------- | ----------------------------------------------------- |
| barrierLabel          | string                                                |
| combinationBarrierReq | [CombinationBarrierReq](#combinationbarrierreq) array |


| Return Type                               | Description                                                  |
| ----------------------------------------- | ------------------------------------------------------------ |
| Promise\<[SuccessObject](#successobject)> | A task containing the API request result through the promise instance. In the success scenario, an [SuccessObject](#successobject) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Create a request object.
const notObject = {
  //notObject is a LogicalObject
  type: HMSAwarenessBarrierModule.BARRIER_TYPE_NOT, //type is LogicalOperationType.
  child: screenKeepingReq,                          //ScreenKeepingReq.
};

const andChildren = [];                             //andChildren is a Children Object
andChildren.push(headsetConnectingReq);             //HeadsetConnectingReq
andChildren.push(notObject);

const andObject = {
  //andObject is a LogicalObject
  type: HMSAwarenessBarrierModule.BARRIER_TYPE_OR, //type is LogicalOperationType.
  children: andChildren,
};

const orChildren = [];                             //orChildren is a Children Object
orChildren.push(ambientLightAboveReq);             //AmbientLightAboveReq
orChildren.push(andObject);

const orObject = {
  //orObject is a LogicalObject
  type: HMSAwarenessBarrierModule.BARRIER_TYPE_OR, //type is LogicalOperationType.
  children: orChildren,
};

const combinationBarrierReq = [];
combinationBarrierReq.push(orObject);
const barrierLabel = "Combination Barrier";

//Call the combinationBarrier API.
HMSAwarenessBarrierModule.combinationBarrier(
  barrierLabel,
  combinationBarrierReq
)
  .then((combinationBarrierResponse) => {
    console.log(
      "combinationBarrier:",
      JSON.stringify(combinationBarrierResponse)
    );
  })
  .catch((errorobject) => {
    console.log("combinationBarrier:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessBarrierModule.deleteBarrier(deleteBarrierReq) 

Allows you to delete the target barrier.

| Parameter        | Type                                  |
| ---------------- | ------------------------------------- |
| deleteBarrierReq | [DeleteBarrierReq](#deletebarrierreq) |


| Return Type                               | Description                                                  |
| ----------------------------------------- | ------------------------------------------------------------ |
| Promise\<[SuccessObject](#successobject)> | A task containing the API request result through the promise instance. In the success scenario, an [SuccessObject](#successobject) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Create a request object.
const deleteBarrierReq = [
    "headset keeping barrier",
    "wifi keeping barrier"
]
//Call the deleteBarrier API.
HMSAwarenessBarrierModule.deleteBarrier(deleteBarrierReq)
  .then((successObject) => {
    console.log("deleteBarrier:", JSON.stringify(successObject));
  })
  .catch((errorobject) => {
    console.log("deleteBarrier:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessBarrierModule.deleteAllBarrier() 

Allows you to delete all saved barriers.

| Return Type                               | Description                                                  |
| ----------------------------------------- | ------------------------------------------------------------ |
| Promise\<[SuccessObject](#successobject)> | A task containing the API request result through the promise instance. In the success scenario, an [SuccessObject](#successobject) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the deleteAllBarrier API.
HMSAwarenessBarrierModule.deleteAllBarrier()
  .then((successObject) => {
    console.log("deleteAllBarrier:", JSON.stringify(successObject));
  })
  .catch((errorobject) => {
    console.log("deleteAllBarrier:", JSON.stringify(errorObject))
  });
```

###### HMSAwarenessBarrierModule.setBackgroundNotification(notificationReq) 

Allows you to customize notifications.

| Parameter       | Type                                |
| --------------- | ----------------------------------- |
| notificationReq | [NotificationReq](#notificationreq) |


| Return Type                               | Description                                                  |
| ----------------------------------------- | ------------------------------------------------------------ |
| Promise\<[SuccessObject](#successobject)> | A task containing the API request result through the promise instance. In the success scenario, an [SuccessObject](#successobject) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the setNotification API.
HMSAwarenessBarrierModule.setNotification(notificationReq)
  .then((successObject) => {
    console.log("setNotification:", JSON.stringify(successObject));
  })
  .catch((errorobject) => {
    console.log("setNotification:", JSON.stringify(errorObject))
  });
```

#### 3.HMSLoggerModule 

##### Function Summary

| Function                                         | Return Type                               | Description                             |
| ------------------------------------------------ | ----------------------------------------- | --------------------------------------- |
| [enableLogger()](#hmsloggermoduleenablelogger)   | Promise\<[SuccessObject](#successobject)> | Enables HMS Plugin Function Analytics.  |
| [disableLogger()](#hmsloggermoduledisablelogger) | Promise\<[SuccessObject](#successobject)> | Disables HMS Plugin Function Analytics. |

##### Functions

###### HMSLoggerModule.enableLogger() 

Enables HMS Plugin function Analytics.

| Return Type                               | Description                                                  |
| ----------------------------------------- | ------------------------------------------------------------ |
| Promise\<[SuccessObject](#successobject)> | A task containing the API request result through the promise instance. In the success scenario, an [SuccessObject](#successobject) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the enableLogger API.
HMSLoggerModule.enableLogger()
  .then((successObject) => {
    console.log("enableLogger:", JSON.stringify(successObject));
  })
  .catch((errorobject) => {
    console.log("enableLogger:", JSON.stringify(errorObject))
  });
```

###### HMSLoggerModule.disableLogger() 

Disables HMS Plugin function Analytics.

| Return Type                               | Description                                                  |
| ----------------------------------------- | ------------------------------------------------------------ |
| Promise\<[SuccessObject](#successobject)> | A task containing the API request result through the promise instance. In the success scenario, an [SuccessObject](#successobject) instance is returned; in the failure scenario, an [ErrorObject](#errorobject) object is returned. |

**Sample Code**

```js
//Call the disableLogger API.
HMSLoggerModule.disableLogger()
  .then((successObject) => {
    console.log("disableLogger:", JSON.stringify(successObject));
  })
  .catch((errorobject) => {
    console.log("disableLogger:", JSON.stringify(errorObject))
  });
```

### Data Types

| Type                                                         | Description                                                  |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [BeaconStatusReq](#beaconstatusreq)                          | Request information of the [getBeaconStatus](#hmsawarenesscapturemodulegetbeaconstatusbeaconstatusreq) API. |
| [TimeCategoriesByUserReq](#timecategoriesbyuserreq)          | Request information of the [getTimeCategoriesByUser](#hmsawarenesscapturemodulegettimecategoriesbyusertimecategoriesbyuserreq) API. |
| [TimeCategoriesByCountryCodeReq](#timecategoriesbycountrycodeReq) | Request information of the [getTimeCategoriesByCountryCode](#hmsawarenesscapturemodulegettimecategoriesbycountrycodetimecategoriesbycontrycodereq) API. |
| [TimeCategoriesForFutureReq](#timecategoriesforfuturereq)    | Request information of the [getTimeCategoriesForFuture](#hmsawarenesscapturemodulegettimecategoriesforfuturetimecategoriesforfuturereq) API. |
| [WeatherByPositionReq](#weatherbypositionreq)                | Request information of the [getWeatherByPosition](#hmsawarenesscapturemodulegetweatherbypositionweatherbypositionreq) API. |
| [CombinationBarrierReq](#combinationbarrierreq)              | Request information of the [combinationBarrier](#hmsawarenessbarriermodulecombinationbarrierbarrierlabelcombinationbarrierreq) API |
| [QueryBarrierReq](#querybarrierreq)                          | Request information of the [queryBarrier](#hmsawarenessbarriermodulequerybarrierquerybarrierreq) API. |
| [BarrierUpdateReq](#barrierupdatereq)                        | Request information of the [updateBarrier](#hmsawarenessbarriermoduleupdatebarrierbarrierLabelquerybarrierreq) API. |
| [DeleteBarrierReq](#deletebarrier)                           | Request information of the [getTimeCategoriesForFuture](#hmsawarenesscapturemodulegettimecategoriesforfuturetimecategoriesforfuturereq) API. |
| [BarrierQueryResponse](#barrierqueryresponse)                | Response to the barrier query request.                       |
| [BeaconStatusResponse](#beaconstatusresponse)                | API response of the status of a nearby beacon device.        |
| [HeadsetStatusResponse](#headsetstatusresponse)              | "Response to the request for obtaining the headset status.   |
| [WeatherStatusResponse](#weatherstatusresponse)              | Response to the weather query request.                       |
| [BluetoothStatusResponse](#bluetoothstatusresponse)          | Response to the request for obtaining the Bluetooth car stereo status, |
| [ScreenStatusResponse](#screenstatusresponse)                | Response to the request for obtaining the screen status.     |
| [WifiStatusResponse](#wifistatusresponse)                    | Response to the request for obtaining the Wi-Fi status       |
| [ApplicationStatusResponse](#applicationstatusresponse)      | Response to the request for obtaining the app status corresponding to a package. |
| [DarkModeStatusResponse](#darkmodestatusresponse)            | Response to the request for obtaining the dark mode status.  |
| [BehaviorResponse](#behaviorresponse)                        | Response to the request for obtaining the user behavior.     |
| [LocationResponse](#locationresponse)                        | Response to the location query request.                      |
| [TimeCategoriesResponse](#timecategoriesresponse)            | Response to the request for obtaining the time.              |
| [AmbientLightResponse](#ambientlightresponse)                | Response to the request for obtaining the illuminance.       |
| [CapabilityResponse](#capabilityresponse)                    | Response to the request for obtaining supported capabilities |
| [EnableUpdateWindowResponse](#enableupdatewindowresponse)    | Request to specify whether a dialog box should appear before initiating an upgrade in your Awareness Kit or HMS Core (APK) application. |
| [BarrierEventType](#barriereventtype)                        | When defining barrier, defines which barrier group it belongs to.                                                                                 Options:                                                            **HMSAwarenessBarrierModule.EVENT_HEADSET**   **HMSAwarenessBarrierModule.EVENT_AMBIENTLIGHT**                                       **HMSAwarenessBarrierModule.EVENT_WIFI**                         **HMSAwarenessBarrierModule.EVENT_BLUETOOTH**   **HMSAwarenessBarrierModule.EVENT_BEHAVIOR**    **HMSAwarenessBarrierModule.EVENT_LOCATION**    **HMSAwarenessBarrierModule.EVENT_SCREEN**                                                   **HMSAwarenessBarrierModule.EVENT_TIME**    **HMSAwarenessBarrierModule.EVENT_BEACON**    **HMSAwarenessBarrierModule.EVENT_COMBINED** |
| [BarrierReceiverAction](#barrierreceiveraction)              | It is the parameter that shows which barrier group the barrier belongs to.                           Options:                                                                          **HMSAwarenessBarrierModule.AMBIENTLIGHT_ABOVE**   **HMSAwarenessBarrierModule.AMBIENTLIGHT_BELOW**                                       **HMSAwarenessBarrierModule.AMBIENTLIGHT_RANGE**                         **HMSAwarenessBarrierModule.EVENT_HEADSET_KEEPING**   **HMSAwarenessBarrierModule.EVENT_HEADSET_CONNECTING**    **HMSAwarenessBarrierModule.EVENT_HEADSET_DISCONNECTING**    **HMSAwarenessBarrierModule.BEACON_DISCOVER**                                                   **HMSAwarenessBarrierModule.BEACON_KEEP**                         **HMSAwarenessBarrierModule.BEACON_MISSED**                    **HMSAwarenessBarrierModule.BEHAVIOR_KEEPING**                                                                 **HMSAwarenessBarrierModule.BEHAVIOR_BEGINNING**   **HMSAwarenessBarrierModule.BEHAVIOR_ENDING**                                       **HMSAwarenessBarrierModule.BLUETOOTH_KEEP**                         **HMSAwarenessBarrierModule.BLUETOOTH_CONNECTING**   **HMSAwarenessBarrierModule.BLUETOOTH_DISCONNECTING**    **HMSAwarenessBarrierModule.LOCATION_ENTER**                          **HMSAwarenessBarrierModule.LOCATION_STAY**                                                   **HMSAwarenessBarrierModule.LOCATION_EXIT**                            **HMSAwarenessBarrierModule.SCREEN_KEEPING**                                   **HMSAwarenessBarrierModule.SCREEN_ON**                                      **HMSAwarenessBarrierModule.SCREEN_OFF**                                 **HMSAwarenessBarrierModule.SCREEN_UNLOCK**                                       **HMSAwarenessBarrierModule.TIME_IN_SUNRISE_OR_SUNSET_PERIOD**                         **HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_DAY**   **HMSAwarenessBarrierModule.TIME_DURING_TIME_PERIOD**    **HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_WEEK**    **HMSAwarenessBarrierModule.TIME_IN_TIME_CATEGORY**                                                   **HMSAwarenessBarrierModule.WIFI_KEEPING**                   **HMSAwarenessBarrierModule.WIFI_CONNECTING**    **HMSAwarenessBarrierModule.WIFI_DISCONNECTING**             **HMSAwarenessBarrierModule.WIFI_ENABLING**                      **HMSAwarenessBarrierModule.WIFI_DISABLING** |
| [BarrierStatusMap](#barrierstatusmap)                        | Barrier status map obtained by entering barrier parameters in BarrierQueryResponse |
| [BarrierStatus](#barrierstatus)                              | Obtains the barrier status by **barrierKey**.                |
| [Barrier](#barrier)                                          | Registered barrier information.                              |
| [TimeCategories](#timecategories)                            | Parameter that represents semantic time of the current location. |
| [Aqi](#aqi)                                                  | Air quality indexes in [WeatherStatusResponse](#weatherstatusresponse). The indexes include those about the concentration of carbon monoxide (CO), nitrogen dioxide (NO2), ozone (O3), PM10, PM2.5, and sulphur dioxide (SO2), as well as the AQI. This data type supports only data in China.[] |
| [LiveInfo](#liveinfo)                                        | Living index level for the day and the next one or two days, including the code and level for the current day and next one to two days. This data type supports only data in China. |
| [DailyLiveInfo](#dailyliveinfo)                              | Information about a living index on a day. The indexes are about dressing, sports, temperature, car washing, tourism, and UV. This parameter supports only data in China. |
| [DailyWeather](#dailyweather)                                | Weather information of the current day and the next six to seven days, including the moonrise, moonset, sunrise, sunset, lowest temperature and highest temperature (Celsius and Fahrenheit), local timestamp in the early morning, month phase, weather in daytime (specified by [DailySituation](#dailysituation)), and weather at night (specified by [DailySituation](#dailysituation)). |
| [DailySituation](#dailysituation)                            | Weather conditions in the daytime or at night, including the weather ID, wind speed, wind level, and wind direction. |
| [CapabilityStatus](#capabilitystatus)                        | This object includes 10 status constants, which represent the capabilities respectively. |
| [BluetoothStatus](#bluetoothstatus)                          | Bluetooth status object.                                     |
| [WifiStatus](#wifistatus)                                    | Wifi status object                                           |
| [HeadsetStatus](#headsetstatus)                              | Headset status  object.                                      |
| [AmbientLightStatus](#ambientlightstatus)                    | Illuminance status object.                                   |
| [ScreenStatus](#screenstatus)                                | Screen status object.                                        |
| [ApplicationStatus](#applicationstatus)                      | Application status object.                                   |
| [DarkModeStatus](#darkmodestatus)                            | Darkmode status object.                                      |
| [LogicalObject](#logicalobject)                              | LogicalObject is a parameter of the [CombinationBarrierReq](#combinationbarrierreq). |
| [WeatherSituation](#weathersituation)                        | Current weather information, which consists of the current weather information (specified by [Situation](#situation)) and city information (specified by [City](#city)). |
| [HourlyWeather](#hourlyweather)                              | Weather information in the current hour and the next 24 hours, including the weather ID, time, temperature (Celsius and Fahrenheit), rainfall probability, and whether it is in the daytime or at night. |
| [City](#city)                                                | City information in [WeatherStatusResponse](#weatherstatusresponse), including the city code, province name, time zone, and city/district name. |
| [Situation](#situation)                                      | Current weather information. The information includes the weather ID, humidity, atmospheric temperature (Celsius and Fahrenheit), somatosensory temperature (Celsius and Fahrenheit), wind direction, wind level, wind speed, atmospheric pressure, UV intensity, and update time. |
| [DetectedBehavior](#detectedbehavior)                        | Type and confidence of a detected behavior.                  |
| [Beacon](#beacon)                                            | This is the becon object.                                    |
| [SuccessObject](#successobject)                              | This is the object that returns because the function runs successfully. |
| [ErrorObject](#errorobject)                                  | This is the object that returns when the function does not work successfully. |

#### BeaconStatusReq

Request information of the [getBeaconStatus](#hmsawarenesscapturemodulegetbeaconstatusbeaconstatusreq) API.

**Optional Parameter**

If "name" and "type" parameters are available, "beaconId" parameter is not mandatory, otherwise it is mandatory.

If "beaconId" parameter are available, "namespace",  "type" parameters is not mandatory, otherwise it is mandatory.

##### Properties

| Name      | Type   | Description                                                  |
| --------- | ------ | ------------------------------------------------------------ |
| beaconId  | string | Beacon broadcast ID. Currently, the Eddystone-UID and iBeacon ID (combination of UUID, major, and minor values) are supported.                 **Optional Parameter** |
| namespace | string | Beacon namespace. **Optional Parameter**                     |
| type      | string | Beacon type.              **Optional Parameter**             |
| content   | string | Beacon context.                              |

**Sample**

```js
const beaconStatusReq = {
    "namespace": "dev736430079244559178",
    "type": "ibeacon",
    "content": "content"
}
```

#### TimeCategoriesByUserReq

Request information of the [getTimeCategoriesByUser](#hmsawarenesscapturemodulegettimecategoriesbyusertimecategoriesbyuserreq) API..

##### Properties

| Name      | Type   | Description                |
| --------- | ------ | -------------------------- |
| latitude  | number | Latitude.    **Mandatory** |
| longitude | number | Longitude  **Mandatory**   |

**Sample**

```js
const timeCategoriesByUserReq = {
  latitude: 36.314909917631724,
  longitude: 30.166312199567606,
};
```

#### TimeCategoriesByCountryCodeReq

Request information of the [getTimeCategoriesByCountryCode](#hmsawarenesscapturemodulegettimecategoriesbycountrycodetimecategoriesbycontrycodereq) API.

##### Properties

| Name               | Type   | Description                                                  |
| ------------------ | ------ | ------------------------------------------------------------ |
| countryCode | string | Country/Region code that complies with ISO 3166-1 alpha-2.   **Mandatory** |

**Sample**

```js
const timeCategoriesByCountryCodeReq = {
  countryCode: "TR",
};
```

#### TimeCategoriesForFutureReq

Request information of the [getTimeCategoriesForFuture](#hmsawarenesscapturemodulegettimecategoriesforfuturetimecategoriesforfuturereq) API.

##### Properties

| Name            | Type   | Description                                                  |
| --------------- | ------ | ------------------------------------------------------------ |
| futureTimestamp | number | Timestamp of the specified date. You can only set this parameter to a timestamp in the current year, because cross-year query is not supported. The timestamp is the total number of milliseconds from 00:00:00 on January 1, 1970 (GMT) to the current time.  **Mandatory** |

**Sample**

```js
const timeCategoriesForFutureReq = {
  futureTimestamp: 1606906743000.0,
};
```

#### WeatherByPositionReq

Request information of the [getWeatherByPosition](#hmsawarenesscapturemodulegetweatherbypositionweatherbypositionreq) API.

##### Properties

| Name     | Type          | Description                                                  |
| -------- | ------------- | ------------------------------------------------------------ |
| city     | [City](#city) | City name.     **Mandatory**                                 |
| locale   | string        | District name. You are advised to pass this parameter so that the address will be more accurate.  **Mandatory** |
| country  | string        | County name. You are advised to pass this parameter so that the address will be more accurate. |
| province | string        | Language type of the passed address, for example, **zh_CN** or **en_US**. Note that the language type is mandatory and must meet the locale format language code_country/region code. |
| district | string        | District name. You are advised to pass this parameter so that the address will be more accurate. |

**Sample**

```js
const weatherByPositionReq = {
  city: "London",
  locale: "en_GB",
  country: "United Kingdom",
  province: "London",
};
```

#### QueryBarrierReq

Request information of the [queryBarrier](#hmsawarenessbarriermodulequerybarrierquerybarrierreq) API. QueryBarrierReq is an array of barrierLabel parameters.

##### Properties

| Name         | Type   | Description                                                  |
| ------------ | ------ | ------------------------------------------------------------ |
| barrierLabel | string | Unique value to create the barrier. Allows you to query a barrier registered with barrierLabel.   **Mandatory** |

**Sample**

```js
const queryBarrierReq = [
    "headset connecting barrier",      // barrierLabel
    "headset disconnecting barrier",   // barrierLabel
    "light above barrier",             // barrierLabel
    "wifi keeping with bssid barrier", // barrierLabel
    "wifi keeping barrier"             // barrierLabel
]
```

#### BarrierUpdateReq

Request information of the [updateBarrier](#hmsawarenessbarriermoduleupdatebarrierbarrierlabelbarrierupdaterequest) API.

**Optional Parameter**

The value of the BarrierUpdateReq object must be one of this properties.

##### Properties

| Name                        | Type                                                      | Description                                                  |
| --------------------------- | --------------------------------------------------------- | ------------------------------------------------------------ |
| ambientLightAboveBarrierReq | [AmbientLightAboveReq](#ambientlightabovereq)             | Request for adding AmbientLightAbove barrier.                 **Optional Parameter** |
| ambientLightBelowReq        | [AmbientLightBelowReq](#ambientlightbelowreq)             | Request for adding AmbientLightBelow barrier.                 **Optional Parameter** |
| ambientLightRangeReq        | [AmbientLightRangeReq](#ambientlightrange)                | Request for adding AmbientLightRange barrier.                 **Optional Parameter** |
| beaconDiscoverReq           | [BeaconDiscoverReq](#beacondiscoverreq)                   | Request for adding BeaconDiscover barrier.                       **Optional Parameter** |
| beaconKeepReq               | [BeaconKeepReq](#beaconkeepreq)                           | Request for adding BeaconKeepReq barrier.                       **Optional Parameter** |
| beaconMissedReq             | [BeaconMissedReq](#beaconmissedreq)                       | Request for adding BeaconMissedReq barrier.                   **Optional Parameter** |
| behaviorKeepingReq          | [BehaviorKeepingReq](#behaviorkeepingreq)                 | Request for adding BehaviorKeepingReq barrier.              **Optional Parameter** |
| behaviorBeginningReq        | [BehaviorBeginningReq](#behaviorbeginningreq)             | Request for adding BehaviorBeginningReq barrier.           **Optional Parameter** |
| behaviorEndingReq           | [BehaviorEndingReq](#behaviorendingreq)                   | Request for adding BehaviorEndingReq barrier.                 **Optional Parameter** |
| bluetoothKeepingReq         | [BluetoothKeepingReq](#bluetoothkeepingreq)               | Request for adding BluetoothKeepingReq barrier.             **Optional Parameter** |
| bluetoothConnectingReq      | [BluetoothConnectingReq](#bluetoothconnectingreq)         | Request for adding BluetoothConnectingReq barrier.       **Optional Parameter** |
| bluetoothDisconnectingReq   | [BluetoothDisconnectingReq](#bluetoothdisconnectingreq)   | Request for adding BluetoothDisconnectingReq barrier.  **Optional Parameter** |
| headsetKeepingReq           | [HeadsetKeepingReq](#headsetkeepingreq)                   | Request for adding HeadsetKeepingReq barrier.               **Optional Parameter** |
| headsetConnectingReq        | [HeadsetConnectingReq](headsetconnectingreq)              | Request for adding HeadsetConnectingReq barrier.         **Optional Parameter** |
| headsetDisonnectingReq      | [HeadsetDisonnectingReq](@headsetdisconnectingreq)        | for adding HeadsetDisonnectingReq barrier.                     **Optional Parameter** |
| locationEnterReq            | [LocationEnterReq](#locationenterreq)                     | Request for adding LocationEnterReq barrier.                   **Optional Parameter** |
| locationStayReq             | [LocationStayReq](#locationstayreq)                       | Request for adding LocationStayReq barrier.                     **Optional Parameter** |
| locationExitReq             | [LocationExitReq](#locationexitreq)                       | Request for adding LocationExitReq barrier.                       **Optional Parameter** |
| screenKeepingReq            | [ScreenKeepingReq](#screenkeepingreq)                     | Request for adding ScreenKeepingReq barrier.                  **Optional Parameter** |
| screenOnReq                 | [ScreenOnReq](#screenonreq)                               | Request for adding ScreenOnReq barrier.                           **Optional Parameter** |
| screenOffReq                | [ScreenOffReq](#screenoffreq)                             | Request for adding ScreenOffReq barrier.                          **Optional Parameter** |
| screenUnlockReq             | [ScreenUnlockReq](#screenunlockreq)                       | Request for adding ScreenUnlockReq barrier.                    **Optional Parameter** |
| timeSunriseandSunSetPerReq  | [TimeSunriseandSunSetPerReq](#timesunriseandsunsetperreq) | Request for adding TimeSunriseandSunSetPerReq barrier.                                           .                                                                                                    **Optional Parameter** |
| timeDuringPerOfDayReq       | [TimeDuringPerOfDayReq](#timeduringperofdayreq)           | Request for adding TimeDuringPerOfDayReq barrier.      **Optional Parameter** |
| timeDuringTimePerReq        | [TimeDuringTimePerReq](#timeduringtimeperreq)             | Request for adding TimeDuringTimePerReq barrier.        **Optional Parameter** |
| timePerOfWeekReq            | [TimePerOfWeekReq](#timeperofweekreq)                     | Request for adding TimePerOfWeekReq barrier.               **Optional Parameter** |
| timeInTimeCategoryReq       | [TimeInTimeCategoryReq](#timeintimecategoryreq)           | Request for adding TimeInTimeCategoryReq barrier.       **Optional Parameter** |
| wifiKeepingReq              | [WifiKeepingReq](#wifikeepingreq)                         | Request for adding WifiKeepingReq barrier.                       **Optional Parameter** |
| wifiKeepingWithBssidReq     | [WifiKeepingWithBssidReq](#wifikeepingwithbssidreq)       | Request for adding WifiKeepingWithBssidReq barrier.     **Optional Parameter** |
| wifiConnectingReq           | [WifiConnectingReq](#wificonnectingreq)                   | Request for adding WifiConnectingReq barrier.                 **Optional Parameter** |
| wifiConnectingBssidReq      | [WifiConnectingBssidReq](#wificonnectingbssidreq)         | Request for adding WifiConnectingBssidReq barrier.       **Optional Parameter** |
| wifiDisconnectingReq        | [WifiDisconnectingReq](#wifidisconnectingreq)             | Request for adding WifiDisconnectingReq barrier.           **Optional Parameter** |
| wifiDisconnectingBssidReq   | [WifiDisconnectingBssidReq](#wifidisconnectingbssidreq)   | Request for adding WifiDisconnectingBssidReq barrier.  **Optional Parameter** |
| wifiEnablingReq             | [WifiEnablingReq](#wifienablingreq)                       | Request for adding WifiEnablingReq barrier.                     **Optional Parameter** |
| wifiDisablingReq            | [WifiDisablingReq](#wifidisablingreq)                     | Request for adding WifiDisablingReq barrier.                    **Optional Parameter** |
| enableUpdateWindowReq       | [EnableUpdateWindowReq](#enableupdatewindowreq)           | Awareness Kit or HMS Core (APK) is the request object required to specify whether a dialog box should be displayed before initiating an upgrade in your application.                                                                      **Optional Parameter** |

**Sample**

```js
const barrierUpdateRequest = wifiKeepingReq;
```

#### CombinationBarrierReq

Request information of the combinationBarrier API.

##### Properties

| Name          | Type                            | Description                                                  |
| ------------- | ------------------------------- | ------------------------------------------------------------ |
| barrierLabel  | string                          | Unique value to create the barrier. Allows you to query a barrier registered with barrierLabel.   **Mandatory** |
| logicalObject | [LogicalObject](#logicalObject) | It is a logical operator object. It has barrier or barrier information. It allows you to create a nested structure.                      .                                                                                                                                                                        **Mandatory** |

**Sample**

```js
const notObject = {                                  //notObject is a LogicalObject
  type: HMSAwarenessBarrierModule.BARRIER_TYPE_NOT, //type is LogicalOperationType.
  child: ScreenKeepingReq                           //ScreenKeepingReq.
}

const andChildren = []                                //andChildren is a Children Object
andChildren.push(HeadsetConnectingReq)                //HeadsetConnectingReq
andChildren.push(notObject)

const andObject = {                                   //andObject is a LogicalObject
  type: HMSAwarenessBarrierModule.BARRIER_TYPE_OR,  //type is LogicalOperationType.
  children: andChildren
}

const orChildren = []                                 //orChildren is a Children Object
orChildren.push(AmbientLightAboveReq)                 //AmbientLightAboveReq
orChildren.push(andObject)

const orObject = {                                    //orObject is a LogicalObject
  type: HMSAwarenessBarrierModule.BARRIER_TYPE_OR,   //type is LogicalOperationType.
  children: orChildren
}

const combinationBarrierReq = []
combinationBarrierReq.push(orObject)
const barrierLabel= "Combination Barrier"
```

#### DeleteBarrierReq

Request information of the [deleteBarrier](#hmsawarenessbarriermoduledeletebarrierdeletebarrierreq) API.  Add the barrierLabels of the barriers you want to be deleted to the array.

##### Properties

| Name         | Type                    | Description                                                  |
| ------------ | ----------------------- | ------------------------------------------------------------ |
| barrierLabel | [s](#barrierLabel)tring | Unique value to create the barrier.  The barrierLabel of the barrier you want to be deleted.    **Mandatory** |

**Sample**

```js
const deleteBarrierReq = [
    "headset keeping barrier",  // barrierLabel
    "wifi keeping barrier"      // barrierLabel
]
```

#### AmbientLightAboveReq

Request for adding AmbientLightAbove barrier.      

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                          Its value must be equal to the constant value of **HMSAwarenessBarrierModule.AMBIENTLIGHT_ABOVE**       **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                               **Mandatory** |
| minLightIntensity     | number | Minimum illuminance.                                                                                                     **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.AMBIENTLIGHT_ABOVE;
const barrierLabel = "light above barrier";
const minLightIntensity = 500.0;
const ambientLightAboveReq = {
  barrierReceiverAction: barrierReceiverAction,
  minLightIntensity: minLightIntensity,
  barrierLabel: barrierLabel,
};
```

#### AmbientLightBelowReq

Request for adding AmbientLightBelow barrier. 

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.AMBIENTLIGHT_BELOW**        **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                **Mandatory** |
| maxLightIntensity     | number | Maximum illuminance.                                                                                                     **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.AMBIENTLIGHT_BELOW;
const barrierLabel = "light below barrier";
const maxLightIntensity = 2500.0;
const ambientLightBelowReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  maxLightIntensity: maxLightIntensity,
};
```

#### AmbientLightRangeReq

Request for adding AmbientLightRange barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                             Its value must be equal to the constant value of **HMSAwarenessBarrierModule.AMBIENTLIGHT_RANGE**    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                            **Mandatory** |
| maxLightIntensity     | number | Maximum illuminance.                                                                                                 **Mandatory** |
| minLightIntensity     | number | Minimum illuminance.                                                                                                  **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.AMBIENTLIGHT_RANGE;
const barrierLabel = "light range barrier";
const minLightIntensity = 500.0;
const maxLightIntensity = 2500.0;
const ambientLightRangeReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  minLightIntensity: minLightIntensity,
  maxLightIntensity: maxLightIntensity,
};
```

#### BeaconDiscoverReq

Request for adding BeaconDiscover barrier.

##### Properties

| Name                  | Type                | Description                                                  |
| --------------------- | ------------------- | ------------------------------------------------------------ |
| barrierReceiverAction | string              | It is the parameter that shows which barrier group the barrier belongs to.                                                                          Its value must be equal to the constant value of **HMSAwarenessBarrierModule.BEACON_DISCOVER**        **Mandatory** |
| barrierLabel          | string              | Unique value to create the barrier.                                                                         **Mandatory** |
| beaconArray           | [Beacon](#beacon)[] | Beacon Array.                                                                                                              **Mandatory** |

**Sample**

```js
const beaconArray = [
  {
    namespace: "dev736430079244559178",
    type: "ibeacon",
    content: "content",
  },
  {
    namespace: "dev736430079244559179",
    type: "ibeacon",
    content: "content2",
  },
];
const barrierReceiverAction = HMSAwarenessBarrierModule.BEACON_DISCOVER;
const barrierLabel = "discover beacon barrier";
const beaconDiscoverReq = {
  barrierReceiverAction: barrierReceiverAction,
  beaconArray: beaconArray,
  barrierLabel: barrierLabel,
};
```

#### BeaconKeepReq

Request for adding BeaconKeep barrier.

##### Properties

| Name                  | Type                     | Description                                                  |
| --------------------- | ------------------------ | ------------------------------------------------------------ |
| barrierReceiverAction | string                   | It is the parameter that shows which barrier group the barrier belongs to.                                                                  Its value must be equal to the constant value of **HMSAwarenessBarrierModule.BEACON_KEEP**                  **Mandatory** |
| barrierLabel          | string                   | Unique value to create the barrier.                                                                         **Mandatory** |
| beaconArray           | [Beacon[]](#beaconarray) | Beacon Array.                                                                                                              **Mandatory** |

**Sample**

```js
const beaconArray = [
  {
    namespace: "dev736430079244559178",
    type: "ibeacon",
    content: "content",
  },
  {
    namespace: "dev736430079244559179",
    type: "ibeacon",
    content: "content2",
  },
];
const barrierReceiverAction = HMSAwarenessBarrierModule.BEACON_KEEP;
const barrierLabel = "keep beacon barrier";
const beaconKeepReq = {
  barrierReceiverAction: barrierReceiverAction,
  beaconArray: beaconArray,
  barrierLabel: barrierLabel,
};
```

#### BeaconMissedReq

Request for adding BeaconMissed barrier.

##### Properties

| Name                  | Type                     | Description                                                  |
| --------------------- | ------------------------ | ------------------------------------------------------------ |
| barrierReceiverAction | string                   | It is the parameter that shows which barrier group the barrier belongs to.                                                                        Its value must be equal to the constant value of **HMSAwarenessBarrierModule.BEACON_MISSED**             **Mandatory** |
| barrierLabel          | string                   | Unique value to create the barrier.                                                                         **Mandatory** |
| beaconArray           | [Beacon](#beaconarray)[] | Beacon Array.                                                                                                              **Mandatory** |

**Sample**

```js
const beaconArray = [
  {
    namespace: "dev736430079244559178",
    type: "ibeacon",
    content: "content",
  },
  {
    namespace: "dev736430079244559179",
    type: "ibeacon",
    content: "content2",
  },
];
const barrierReceiverAction = HMSAwarenessBarrierModule.BEACON_MISSED;
const barrierLabel = "missed beacon barrier";
const beaconMissedReq = {
  barrierReceiverAction: barrierReceiverAction,
  beaconArray: beaconArray,
  barrierLabel: barrierLabel,
};
```

#### BehaviorKeepingReq

Request for adding BehaviorKeeping barrier.

##### Properties

| Name                  | Type                    | Description                                                  |
| --------------------- | ----------------------- | ------------------------------------------------------------ |
| barrierReceiverAction | string                  | It is the parameter that shows which barrier group the barrier belongs to.                                                                        Its value must be equal to the constant value of **HMSAwarenessBarrierModule.BEHAVIOR_KEEPING**.       **Mandatory** |
| barrierLabel          | string                  | Unique value to create the barrier.                                                                         **Mandatory** |
| behaviorTypes         | [Behavior](#behavior)[] | Behavior Array.                                                                                                           **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.BEHAVIOR_KEEPING;
const barrierLabel = "behavior keeping barrier";
const behaviorTypes = [];
behaviorTypes.push(HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_STILL);
const behaviorKeepingReq = {
  barrierReceiverAction: barrierReceiverAction,
  behaviorTypes: behaviorTypes,
  barrierLabel: barrierLabel,
};
```

#### BehaviorBeginningReq

Request for adding BehaviorBeginning barrier.

##### Properties

| Name                  | Type                    | Description                                                  |
| --------------------- | ----------------------- | ------------------------------------------------------------ |
| barrierReceiverAction | string                  | It is the parameter that shows which barrier group the barrier belongs to.                                                                               Its value must be equal to the constant value of **HMSAwarenessBarrierModule.BEHAVIOR_BEGINNING**.        **Mandatory** |
| barrierLabel          | string                  | Unique value to create the barrier.                                                                                **Mandatory** |
| behaviorTypes         | [Behavior](#behavior)[] | Behavior Array.                                                                                                                  **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.BEHAVIOR_BEGINNING;
const barrierLabel = "behavior beginning barrier";
const behaviorTypes = [];
behaviorTypes.push(HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_WALKING);
behaviorTypes.push(HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_RUNNING);
const behaviorBeginningReq = {
  barrierReceiverAction: barrierReceiverAction,
  behaviorTypes: behaviorTypes,
  barrierLabel: barrierLabel,
};
```

#### BehaviorEndingReq

Request for adding BehaviorEnding barrier.

##### Properties

| Name                  | Type                    | Description                                                  |
| --------------------- | ----------------------- | ------------------------------------------------------------ |
| barrierReceiverAction | string                  | It is the parameter that shows which barrier group the barrier belongs to.                                                                           Its value must be equal to the constant value of **HMSAwarenessBarrierModule.BEHAVIOR_ENDING**   .           **Mandatory** |
| barrierLabel          | string                  | Unique value to create the barrier.                                                                                **Mandatory** |
| behaviorTypes         | [Behavior](#behavior)[] | Behavior Array.                                                                                                                  **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.BEHAVIOR_ENDING;
const barrierLabel = "behavior ending barrier";
const behaviorTypes = [];
behaviorTypes.push(HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_WALKING);
const behaviorEndingReq = {
  barrierReceiverAction: barrierReceiverAction,
  behaviorTypes: behaviorTypes,
  barrierLabel: barrierLabel,
};
```

#### BluetoothKeepingReq

Request for adding BluetoothKeeping barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                            Its value must be equal to the constant value of **HMSAwarenessBarrierModule.BLUETOOTH_KEEP**   .                     .                                                                                                                                                    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |
| deviceType            | string | Bluetooth device type.                                                                                                                                                             Value **HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR** indicates a Bluetooth car stereo.                                                                                                                                                    .                                                                                                                                                    **Mandatory** |
| bluetoothStatus       | number | Connection status of the Bluetooth car stereo.         **HMSAwarenessBarrierModule.BluetoothStatus_CONNECTED** : Bluetooth is connected.                          **HMSAwarenessBarrierModule.BluetoothStatus_DISCONNECTED**:  Bluetooth is not connected.                                     **HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR** : Bluetooth car stereo.                                **HMSAwarenessBarrierModule.BluetoothStatus_UNKNOWN**:  The Bluetooth status is not identified.                                                                                                                                   .                                                                                                                                                    **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.BLUETOOTH_KEEP;
const barrierLabel = "bluetooth keeping barrier";
const deviceType = HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR;
const bluetoothStatus = HMSAwarenessBarrierModule.BluetoothStatus_CONNECTED;
const bluetoothKeepingReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  deviceType: deviceType,
  bluetoothStatus: bluetoothStatus,
};
```

#### BluetoothConnectingReq 

Request for adding BluetoothConnecting barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.BLUETOOTH_CONNECTING**                                                                                                                                                                     .                                                                                                                                                    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |
| deviceType            | string | Bluetooth device type.                                                                                                                                                               Value **HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR** indicates a Bluetooth car stereo.                                                                                                                                  **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.BLUETOOTH_CONNECTING;
const barrierLabel = "bluetooth connecting barrier";
const deviceType = HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR;
const bluetoothConnectingReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  deviceType: deviceType,
};
```

#### BluetoothDisconnectingReq   

Request for adding BluetoothDisconnecting barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.BLUETOOTH_DISCONNECTING** .                                                                                                                                                               **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.        Mandatory**       |
| deviceType            | string | Bluetooth device type.                                                                                                                                                             Value **HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR** indicates a Bluetooth car stereo.                                                                                                                                                             **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.BLUETOOTH_DISCONNECTING;
const barrierLabel = "bluetooth disconnecting barrier";
const deviceType = HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR;
const BluetoothDisconnectingReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  deviceType: deviceType,
};
```

#### HeadsetKeepingReq   

Request for adding HeadsetKeeping barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.EVENT_HEADSET_KEEPING**                                                                                                                                                                    .                                                                                                                                                    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |
| headsetStatus         | string | Headset status.                                                                           **HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED**  : A headset is connected.       **HMSAwarenessBarrierModule.HeadsetStatus_DISCONNECTED** : A headset is disconnected.                                                                                                                                               .                                                                                                                                                    **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.EVENT_HEADSET_KEEPING;
const barrierLabel = "headset keeping barrier";
const headsetStatus = HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED;
const headsetKeepingReq = {
  barrierReceiverAction: barrierReceiverAction,
  headsetStatus: headsetStatus,
  barrierLabel: barrierLabel,
};
```

#### HeadsetConnectingReq   

Request for adding HeadsetConnecting barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.EVENT_HEADSET_CONNECTING**                                                                                                                                                                    .                                                                                                                                                    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |
| headsetStatus         | string | Headset status.                                                                           **HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED**  : A headset is connected.       **HMSAwarenessBarrierModule.HeadsetStatus_DISCONNECTED** : A headset is disconnected.                                                                                                                                               .                                                                                                                                                    **Mandatory** |

**Sample**

```js
const barrierReceiverAction =
  HMSAwarenessBarrierModule.EVENT_HEADSET_CONNECTING;
const barrierLabel = "headset connecting barrier";
const headsetConnectingReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
};
```

#### HeadsetDisconnectingReq   

Request for adding HeadsetDisconnecting barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.EVENT_HEADSET_DISCONNECTING**                                                                                                                                                                    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |
| headsetStatus         | string | Headset status.                                                                           **HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED**  : A headset is connected.        **HMSAwarenessBarrierModuleHeadsetStatus_DISCONNECTED** : A headset is disconnected.                                                                                                                                                                                                            **Mandatory** |

**Sample**

```js
const barrierReceiverAction =
  HMSAwarenessBarrierModule.EVENT_HEADSET_DISCONNECTING;
const barrierLabel = "headset disconnecting barrier";
const headsetDisonnectingReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
};
```

#### LocationEnterReq   

Request for adding LocationEnter barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.LOCATION_ENTER**                                                                                                                                                                    .                                                                                                                                                    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |
| latitude              | number | Center latitude of an area. The unit is degree and the value range is [–90°,90°].         **Mandatory** |
| longitude             | number | Center longitude of an area. The unit is degree and the value range is (–180°,180°]. **Mandatory** |
| radius                | number | Radius of an area. The unit is meter.                                                                                    **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.LOCATION_ENTER;
const barrierLabel = "location enter barrier";
const locationEnterReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  latitude: 36.3148328,
  longitude: 30.1663369,
  radius: 10000,
};
```

#### LocationStayReq   

Request for adding LocationStay barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.LOCATION_STAY**                                                                                                                                                                    .                                                                                                                                                    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |
| latitude              | number | Center latitude of an area. The unit is degree and the value range is [–90°,90°].         **Mandatory** |
| longitude             | number | Center longitude of an area. The unit is degree and the value range is (–180°,180°]. **Mandatory** |
| radius                | number | Radius of an area. The unit is meter.                                                                                    **Mandatory** |
| timeOfDuration        | number | Minimum stay time in a specified area. The unit is millisecond.                                     **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.LOCATION_STAY;
const barrierLabel = "location stay barrier";
const locationStayReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  latitude: 36.3148328,
  longitude: 30.1663369,
  radius: 10000,
  timeOfDuration: 10000,
};
```

#### LocationExitReq   

Request for adding LocationExit barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.LOCATION_EXIT**                                                                                                                                                                    .                                                                                                                                                    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |
| latitude              | number | Center latitude of an area. The unit is degree and the value range is [–90°,90°].         **Mandatory** |
| longitude             | number | Center longitude of an area. The unit is degree and the value range is (–180°,180°]. **Mandatory** |
| radius                | number | Radius of an area. The unit is meter.                                                                                    **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.LOCATION_EXIT;
const barrierLabel = "location exit barrier";
const locationExitReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  latitude: 36.3148328,
  longitude: 30.1663369,
  radius: 10000,
};
```

#### ScreenKeepingReq   

Request for adding ScreenKeeping barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.SCREEN_KEEPING**                                                                                                                                                                    .                                                                                                                                                    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |
| screenStatus          | number | Screen status.                                                                           **HMSAwarenessBarrierModule.ScreenStatus_UNLOCK** : The screen is unlock.       **HMSAwarenessBarrierModule.ScreenStatus_SCREEN_OFF** : The screen is off. **HMSAwarenessBarrierModule.ScreenStatus_SCREEN_ON** :The screen is on.                                                                                                                                           .                                                                                                                                                    **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.SCREEN_KEEPING;
const barrierLabel = "SCREEN_ACTION_KEEPING";
const screenStatus = HMSAwarenessBarrierModule.ScreenStatus_UNLOCK;
const screenKeepingReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  screenStatus: screenStatus,
};
```

#### ScreenOnReq   

Request for adding ScreenOn barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.SCREEN_ON**                                                                                                                                                                                                                                                                                                                  **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.SCREEN_ON;
const barrierLabel = "SCREEN_ACTION_ON";
const ScreenOnReq = {
    barrierReceiverAction: barrierReceiverAction,
    barrierLabel: barrierLabel,
};
```

#### ScreenOffReq   

Request for adding ScreenOff barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.SCREEN_OFF**                                                                                                                                                                   .                                                                                                                                                    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.SCREEN_OFF;
const barrierLabel = "SCREEN_ACTION_OFF";
const screenOffReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
};
```

#### ScreenUnlockReq   

Request for adding ScreenUnlock barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.SCREEN_UNLOCK**                                                                                                                                                                    .                                                                                                                                                    **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                       **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.SCREEN_UNLOCK;
const barrierLabel = "SCREEN_ACTION_UNLOCK";
const screenUnlockReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
};
```

#### TimeSunriseandSunSetPerReq   

Request for adding TimeSunriseandSunSetPer barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.TIME_IN_SUNRISE_OR_SUNSET_PERIOD**                                                                                                                                                                    .                                                                                                                                                       **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                          **Mandatory** |
| timeInstant           | number | Sunrise or sunset.                                                                                                                                                                Options are :                                                                                         **HMSAwarenessBarrierModule.TimeBarrier_SUNSET_CODE** :  Sunset     **HMSAwarenessBarrierModule.TimeBarrier_SUNRISE_CODE**: Sunrise                                                               .                .                                                                                                                                                      **Mandatory** |
| startTimeOffset       | number | Start time offset of the time barrier. The unit is millisecond. The value range is milliseconds from –24 hours to +24 hours. The value must be less than the value of **stopTimeOffset**.                           **Mandatory** |
| stopTimeOffset        | number | Stop time offset of the time barrier. The unit is millisecond. The value range is milliseconds from –24 hours to +24 hours. The value must be greater than the value of **startTimeOffset**.                    **Mandatory** |

**Sample**

```js
const barrierReceiverAction =
  HMSAwarenessBarrierModule.TIME_IN_SUNRISE_OR_SUNSET_PERIOD;
const barrierLabel = "sunrice or sunset period barrier";
const timeInstant = HMSAwarenessBarrierModule.TimeBarrier_SUNSET_CODE;
const timeSunriseandSunSetPerReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  timeInstant: timeInstant,
  startTimeOffset: -3600000.0,
  stopTimeOffset: 36000000.0,
};
```

#### TimeDuringPerOfDayReq   

Request for adding TimeDuringPerOfDay barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_DAY**                                                                                                                                                                   .                                                                                                                                                       **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                          **Mandatory** |
| startTimeOfDay        | number | Start time of the barrier, in milliseconds. The value **0** indicates 00:00. The maximum value is the number of milliseconds of 24 hours.                                                                                                           **Mandatory** |
| stopTimeOfDay         | number | Stop time of the barrier, in milliseconds. The value of **stopTimeOfDay** must be greater than or equal to the value of **startTimeOfDay**.                                                                                                         **Mandatory** |
| timeZoneId            | string | Time zone id specified by you. A string containing the ID of the time zone, such as "America/Los_Angeles" or "Australia/Sydney".                                                                                                                      **Mandatory** |

**Sample**

```js
const barrierReceiverAction =
  HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_DAY;
const barrierLabel = "period of day barrier";
const timeDuringPerOfDayReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  startTimeOfDay: 39600000,
  stopTimeOfDay: 43200000,
  timeZoneId: "Europe/Istanbul",
};
```

#### TimeDuringTimePerReq   

Request for adding TimeDuringTimePer barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.TIME_DURING_TIME_PERIOD**                                                                                                                                                                   .                                                                                                                                                       **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                          **Mandatory** |
| startTimeStamp        | number | Start time of the barrier, in milliseconds. The value **0** indicates 00:00. The maximum value is the number of milliseconds of 24 hours.                                                                                                           **Mandatory** |
| stopTimeStamp         | number | Stop time of the barrier, in milliseconds. The value of **stopTimeOfDay** must be greater than or equal to the value of **startTimeOfDay**.                                                                                                         **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.TIME_DURING_TIME_PERIOD;
const barrierLabel = "time period barrier";
const timeDuringTimePerReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  startTimeStamp: 1607515517229.0,
  stopTimeStamp: 1607515527229.0,
};
```

#### TimePerOfWeekReq   

Request for adding TimeDuringTimePer barrier.

##### Properties

| Name                    | Type   | Description                                                  |
| ----------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction   | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_WEEK**                                                                                                                                                                   .                                                                                                                                                       **Mandatory** |
| barrierLabel            | string | Unique value to create the barrier.                                                                                          **Mandatory** |
| dayOfWeek               | number | Day of a week. The options are as follows:                         **HMSAwarenessBarrierModule.TimeBarrier_FRIDAY_CODE                HMSAwarenessBarrierModule.TimeBarrier_MONDAY_CODE         HMSAwarenessBarrierModule.TimeBarrier_SATURDAY_CODE          HMSAwarenessBarrierModule.TimeBarrier_SUNDAY_CODE         HMSAwarenessBarrierModule.TimeBarrier_THURSDAY_CODE    HMSAwarenessBarrierModule.TimeBarrier_TUESDAY_CODE HMSAwarenessBarrierModule.TimeBarrier_WEDNESDAY_CODE**          .                                                                                                                                                                                                                                 **Mandatory** |
| startTimeOfSpecifiedDay | number | Start time of the barrier, in milliseconds. The value **0** indicates 00:00. The maximum value is the number of milliseconds of 24 hours.                                                                                                           **Mandatory** |
| stopTimeOfSpecifiedDay  | number | Stop time of the barrier, in milliseconds. The value of **stopTimeOfSpecifiedDay** must be greater than or equal to the value of **startTimeOfSpecifiedDay**.                                                                 **Mandatory** |
| timeZoneId              | string | Time zone id specified by you. A string containing the ID of the time zone.                     **Mandatory** |

**Sample**

```js
const barrierReceiverAction =
  HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_WEEK;
const barrierLabel = "period of week barrier";
const timePerOfWeekReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  dayOfWeek: HMSAwarenessBarrierModule.TimeBarrier_MONDAY_CODE,
  startTimeOfSpecifiedDay: 32400000,
  stopTimeOfSpecifiedDay: 36000000,
  timeZoneId: "Europe/Istanbul",
};
```

#### TimeInTimeCategoryReq   

Request for adding TimeInTimeCategory barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.TIME_IN_TIME_CATEGORY**                                                                                                                                                                   .                                                                                                                                                       **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                          **Mandatory** |
| timeCategory          | number | Time. The options are as follows:                **HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_WEEKEND**               **HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_WEEKDAY**    **HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_MORNING**     **HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_AFTERNOON**    **HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_EVENING**                      **HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_NIGHT**     **HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_HOLIDAY**       **HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_NOT_HOLIDAY**                                                      .          .                                                                                                                                                        **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.TIME_IN_TIME_CATEGORY;
const barrierLabel = "in timecategory barrier";
const timeCategory =
  HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_WEEKEND;
const timeInTimeCategoryReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  timeCategory: timeCategory,
};
```

#### WifiKeepingReq   

Request for adding WifiKeeping barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.WIFI_KEEPING**                                                                                                                                                                   .                                                                                                                                                       **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                          **Mandatory** |
| wifiStatus            | number | Wifi Status. The options are as follows:                                             **HMSAwarenessBarrierModule.WifiStatus_CONNECTED**               **HMSAwarenessBarrierModule.WifiStatus_DISCONNECTED**    **HMSAwarenessBarrierModule.WifiStatus_ENABLED**     **HMSAwarenessBarrierModule.WifiStatus_DISABLED**    **HMSAwarenessBarrierModule.WifiStatus_UNKNOWN**                                                        .          .                                                                                                                                                        .                                                                                                                                                        **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.WIFI_KEEPING;
const barrierLabel = "wifi keeping barrier";
const wifiStatus = HMSAwarenessBarrierModule.WifiStatus_CONNECTED;
const wifiKeepingReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  wifiStatus: wifiStatus,
};
```

#### WifiKeepingWithBssidReq   

Request for adding WifiKeeping barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.WIFI_KEEPING**                                                                                                                                                                   .                                                                                                                                                       **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                          **Mandatory** |
| wifiStatus            | number | Wifi Status. The options are as follows:                                             **HMSAwarenessBarrierModule.WifiStatus_CONNECTED**               **HMSAwarenessBarrierModule.WifiStatus_DISCONNECTED**    **HMSAwarenessBarrierModule.WifiStatus_ENABLED**     **HMSAwarenessBarrierModule.WifiStatus_DISABLED**    **HMSAwarenessBarrierModule.WifiStatus_UNKNOWN**                                                        .          .                                                                                                                                                        .                                                                                                                                                           **Mandatory** |
| bssid                 | string | If the value of **wifiStatus** is **CONNECTED** or **DISCONNECTED**, the BSSID (physical address of the Wi-Fi source) of the Wi-Fi barrier can be specified. The value **null** indicates any BSSID. If the value of **wifiStatus** is **ENABLED** or **DISABLED**, the BSSID is invalid.                                                                                                    **Mandatory** |
| ssid                  | string | If the value of **wifiStatus** is **CONNECTED** or **DISCONNECTED**, the SSID (name of the Wi-Fi source) of the Wi-Fi barrier can be specified. The value **null** indicates any SSID. If the value of **wifiStatus** is **ENABLED** or **DISABLED**, the SSID is invalid.                                                                                                                            **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.WIFI_KEEPING;
const barrierLabel = "wifi keeping with bssid barrier";
const wifiStatus = HMSAwarenessBarrierModule.WifiStatus_CONNECTED;
const ssid = "HUAWEI_WIFI";
const bssid = "18:28:61:f8:...";
const wifiKeepingWithBssidReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  wifiStatus: wifiStatus,
  bssid: bssid,
  ssid: ssid,
};
```

#### WifiConnectingReq   

Request for adding WifiConnecting barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.WIFI_CONNECTING**                                                                                                                                                                   .                                                                                                                                                       **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                          **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.WIFI_CONNECTING;
const barrierLabel = "wifi connecting barrier";
const wifiConnectingReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
};
```

#### WifiConnectingWithBssidReq   

Request for adding WifiConnectingWithBssid barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.WIFI_CONNECTING**                                                                                                                                                                   .                                                                                                                                                                                **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                                                   **Mandatory** |
| bssid                 | string | BSSID (physical address of the Wi-Fi source) of a specific Wi-Fi. The value **null** indicates any BSSID.   **Mandatory** |
| ssid                  | string | SSID (name of the Wi-Fi source) of a specific Wi-Fi. The value **null** indicates any SSID.                           **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.WIFI_CONNECTING;
const barrierLabel = "wifi connecting with bssid barrier";
const ssid = "HUAWEI_WIFI";
const bssid = "18:28:61:f8:...";
const wifiConnectingBssidReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
  bssid: bssid,
  ssid: ssid,
};
```

#### WifiDisconnectingReq   

Request for adding WifiDisconnecting barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.WIFI_DISCONNECTING**                                                                                                                                                                   .                                                                                                                                              **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                          **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.WIFI_DISCONNECTING;
const barrierLabel = "wifi disconnecting barrier";
const wifiDisconnectingReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
};
```

#### WifiDisconnectingBssidReq   

Request for adding WifiDisconnectingBssid barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.WIFI_DISCONNECTING**                                                                                                                                                                   .                                                                                                                                                                                **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                                                   **Mandatory** |
| bssid                 | string | BSSID (physical address of the Wi-Fi source) of a specific Wi-Fi. The value **null** indicates any BSSID.   **Mandatory** |
| ssid                  | string | SSID (name of the Wi-Fi source) of a specific Wi-Fi. The value **null** indicates any SSID.                           **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.WIFI_DISCONNECTING
const barrierLabel = "wifi disconnecting with bssid barrier"
const ssid = "HUAWEI_WIFI"
const bssid = "18:28:61:f8:..."
const wifiDisconnectingBssidReq = {
    "barrierReceiverAction": barrierReceiverAction,
    "barrierLabel": barrierLabel,
    "bssid": bssid,
    "ssid": ssid
}
```

#### WifiEnablingReq   

Request for adding WifiEnabling barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.HMSAwarenessBarrierModule.WIFI_ENABLING**                                                                                                                                                                   .                                                                                                                                                       **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                          **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.WIFI_ENABLING;
const barrierLabel = "wifi enabling barrier";
const wifiEnablingReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
};
```

#### WifiDisablingReq   

Request for adding WifiDisabling barrier.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| barrierReceiverAction | string | It is the parameter that shows which barrier group the barrier belongs to.                                                                       Its value must be equal to the constant value of **HMSAwarenessBarrierModule.WIFI_DISABLING**                                                                                                                                                                   .                                                                                                                                                       **Mandatory** |
| barrierLabel          | string | Unique value to create the barrier.                                                                                          **Mandatory** |

**Sample**

```js
const barrierReceiverAction = HMSAwarenessBarrierModule.WIFI_DISABLING;
const barrierLabel = "wifi disabling barrier";
const wifiDisablingReq = {
  barrierReceiverAction: barrierReceiverAction,
  barrierLabel: barrierLabel,
};
```

#### LogicalObject  

LogicalObject is a parameter of the [CombinationBarrierReq](#combinationbarrierreq). It is a logical operator object. It has barrier or barrier information. It allows you to create a nested structure.  

**Optional Parameter**

If the **type** property is equal to the value **HMSAwarenessBarrierModule.BARRIER_TYPE_NOT** , the parameter **child** is required, otherwise it is not.   

If the **type** property equals **HMSAwarenessBarrierModule.BARRIER_TYPE_AND** or **HMSAwarenessBarrierModule.BARRIER_TYPE_OR** , the parameter **children** is mandatory, otherwise it is not.

##### Properties

| Field Name | Type                                    | Description                                                  |
| ---------- | --------------------------------------- | ------------------------------------------------------------ |
| type       | string                                  | The options are as follows:                                                                                           **HMSAwarenessBarrierModule.BARRIER_TYPE_AND**                                            **HMSAwarenessBarrierModule.BARRIER_TYPE_OR**                                                                            **HMSAwarenessBarrierModule.BARRIER_TYPE_NOT**                                                                                                                                                      .                                                                                                                              **Mandatory** |
| children   | [BarrierUpdateReq](#barrierupdatereq)[] | This is an BarrierUpdateReq array.                                                                  **Optional Parameter** |
| child      | [BarrierUpdateReq](#barrierupdatereq)   | This is an BarrierUpdateReq object.                                                                **Optional Parameter** |

**Sample**

```js
const notObject = {                                   //notObject is a LogicalObject
  type: HMSAwarenessBarrierModule.BARRIER_TYPE_NOT, //type is LogicalOperationType.
  child: ScreenKeepingReq                           //ScreenKeepingReq.
}

const andChildren = []                                //andChildren is a Children Object
andChildren.push(HeadsetConnectingReq)                //HeadsetConnectingReq
andChildren.push(notObject)

const andObject = {                                   //andObject is a LogicalObject
  type: HMSAwarenessBarrierModule.BARRIER_TYPE_OR,  //type is LogicalOperationType.
  children: andChildren
}

const orChildren = []                                 //orChildren is a Children Object
orChildren.push(AmbientLightAboveReq)                 //AmbientLightAboveReq
orChildren.push(andObject)

const orObject = {                                    //orObject is a LogicalObject
  type: HMSAwarenessBarrierModule.BARRIER_TYPE_OR,   //type is LogicalOperationType.
  children: orChildren
}
```

#### NotificationReq  

This object is the request object of the setNotificationBackground function.

##### Properties

| Name         | Type   | Description   |
| ------------ | ------ | ------------- |
| contentTitle | string | Content Title |
| contentText  | string | Content Text  |
| type         | string | Type          |
| resourceName | string | Resource Name |

**Sample**

```js
const notificationReq = {
    contentTitle: "Custom title",
    contentText: "Custom text",
    type: "mipmap",
    resourceName: "ic_launcher"
}
```

#### BarrierQueryResponse   

Barrier query response.

##### Properties

| Name             | Type                                  | Description    |
| ---------------- | ------------------------------------- | -------------- |
| barrierStatusMap | [BarrierStatusMap](#barrierstatusmap) | Barrier status |

#### BarrierStatusMap  

Information of registered barriers.

##### Properties

| Name     | Type                  | Description                         |
| -------- | --------------------- | ----------------------------------- |
| barriers | [Barrier](#barrier)[] | Information of registered barriers. |

#### Barrier

Registered barrier information.

##### Properties

| Name          | Type                            | Description                     |
| ------------- | ------------------------------- | ------------------------------- |
| barrierStatus | [BarrierStatus](#barrierstatus) | Registered barrier information. |
| barrierLabel  | string                          | Barrier label.                  |

#### BarrierStatus

Registered barrier information.

##### Properties

| Name                  | Type   | Description                                                  |
| --------------------- | ------ | ------------------------------------------------------------ |
| lastBarrierUpdateTime | number | Timestamp of the last barrier status change.                 |
| barrierLabel          | string | Key value that uniquely identifies a barrier                 |
| lastStatus            | number | Barrier status. Barrier status.                                                                                        **HMSAwarenessBarrierModule.BarrierStatus_TRUE**                      **HMSAwarenessBarrierModule.BarrierStatus_FALSE**                                                                                     **HMSAwarenessBarrierModule.BarrierStatus_UNKNOWN** |
| lastStatusName        | string | Description of lastStatusName parameter.                     |
| presentStatus         | number | Barrier status. Barrier status.                                                                                        **HMSAwarenessBarrierModule.BarrierStatus_TRUE**                      **HMSAwarenessBarrierModule.BarrierStatus_FALSE**                                                                                     **HMSAwarenessBarrierModule.BarrierStatus_UNKNOWN** |
| presentStatusName     | string | Description of presentStatus parameter.                      |
| describeContents      | number | Describe contents.                                           |

#### HeadsetStatusResponse   

Response to the request for obtaining the headset status

##### Properties

| Name       | Type   | Description                                                  |
| ---------- | ------ | ------------------------------------------------------------ |
| status     | string | Headset status. Options;   **CONNECTED**, **DISCONNECTED** , **UNKNOWN** |
| statusCode | number | Headset status code.                                    **HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED** , **HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED**,  **HMSAwarenessBarrierModule.HeadsetStatus_UNKNOWN** |

#### BeaconStatusResponse   

API response of the status of a nearby beacon device.

##### Properties

| Name           | Type                | Description              |
| -------------- | ------------------- | ------------------------ |
| beaconDataList | [Beacon](#beacon)[] | Beacon information list. |

#### HeadsetStatusResponse   

Response to the request for obtaining the headset status

##### Properties

| Name       | Type   | Description                                                  |
| ---------- | ------ | ------------------------------------------------------------ |
| status     | string | Headset status. Options;   **CONNECTED**, **DISCONNECTED** , **UNKNOWN** |
| statusCode | number | Headset status code.                                    **HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED** , **HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED**,  **HMSAwarenessBarrierModule.HeadsetStatus_UNKNOWN** |

#### WeatherStatusResponse   

Response to the weather query request. This API provides five functions for obtaining the air quality index (AQI), current weather information, weather in the next seven days, weather in the next 24 hours, and living index in the next one to two days. The AQI and living index are about only the data of **China**.

##### Properties

| Name              | Type                                  | Description                                                  |
| ----------------- | ------------------------------------- | ------------------------------------------------------------ |
| aqi               | [Aqi](#aqi)                           | AQI information, including data about carbon monoxide, nitrogen dioxide, ozone, PM10, PM2.5, and sulphur dioxide, and comprehensive weather index. |
| liveInfoList      | [LiveInfo](#liveinfo)[]               | Living index information of the current day and the next one to two days, including the living index (code values), living level, and time information. |
| dailyWeatherList  | [DailyWeather](#dailyweather)[]       | Weather information of the next seven days, including the moon phase, moonrise and moonset time, sunrise and sunset time, lowest temperature and highest temperature (Celsius and Fahrenheit), AQI value, date, and weather IDs, wind direction, wind level, and wind speed in the daytime and at night. |
| hourlyWeatherList | [HourlyWeather](#hourlyweather)[]     | Weather information in the next 24 hours. The weather information includes the weather ID, time, temperature (Celsius and Fahrenheit), rainfall probability, and whether it is at night. |
| weatherSituation  | [WeatherSituation](#weathersituation) | The weather information includes the weather ID, humidity, atmospheric temperature (Celsius and Fahrenheit), somatosensory temperature (Celsius and Fahrenheit), wind direction, wind level, wind speed, atmospheric pressure, UV intensity, and update time. The city information includes the name, city code, province name, and time zone. |

#### BluetoothStatusResponse   

Response to the request for obtaining the Bluetooth car stereo status.

##### Properties

| Name            | Type   | Description                                                  |
| --------------- | ------ | ------------------------------------------------------------ |
| bluetoothStatus | number | Bluetooth status                                                          **HMSAwarenessBarrierModule.BluetoothStatus_CONNECTED** : Bluetooth is connected.                          **HMSAwarenessBarrierModule.BluetoothStatus_DISCONNECTED**:  Bluetooth is not connected.                                     **HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR** : Bluetooth car stereo.                                **HMSAwarenessBarrierModule.BluetoothStatus_UNKNOWN**:  The Bluetooth status is not identified.                                                                                                                                   .                                                                                                                                                    **Mandatory** |

#### ScreenStatusResponse   

Response to the request for obtaining the screen status.

##### Properties

| Name         | Type                          | Description           |
| ------------ | ----------------------------- | --------------------- |
| screenStatus | [ScreenStatus](#screenstatus) | Screen status object. |

#### WifiStatusResponse   

Response to the request for obtaining the wifi status.

##### Properties

| Name       | Type                      | Description         |
| ---------- | ------------------------- | ------------------- |
| wifiStatus | [WifiStatus](#wifistatus) | Wifi status object. |

#### ApplicationStatusResponse   

Response to the request for obtaining the app status corresponding to a package.

##### Properties

| Name              | Type                                    | Description                |
| ----------------- | --------------------------------------- | -------------------------- |
| applicationStatus | [ApplicationStatus](#applicationstatus) | Application status object. |

#### DarkModeStatusResponse   

Response to the request for obtaining the dark mode status.

##### Properties

| Name           | Type                              | Description             |
| -------------- | --------------------------------- | ----------------------- |
| darkModeStatus | [DarkModeStatus](#darkmodestatus) | Darkmode status object. |

#### BehaviorResponse   

Response to the request for obtaining the user behavior.

##### Properties

| Name                  | Type                                    | Description                                                  |
| --------------------- | --------------------------------------- | ------------------------------------------------------------ |
| elapsedRealtimeMillis | number                                  | Actual time (in milliseconds) used for this detection since startup. |
| mostLikelyBehavior    | [DetectedBehavior](#detectedbehavior)   | Most likely behavior.                                        |
| time                  | number                                  | Detection time, in timestamp format.                         |
| propableBehavior      | [DetectedBehavior](#detectedbehavior)[] | Obtains a list of behaviors of a user in a specified period. The behaviors on the list are sorted by confidence. Each possible behavior has confidence, which indicates how likely a user is executing the behavior. |
| behaviourConfidence   | number                                  | Obtains the confidence of a behavior of a given type.        |
| describeContent       | number                                  | Describe content.                                            |

#### LocationResponse   

Response to the location query request. It is an android Location object.

##### Properties

| Name                               | Type    | Description                                                  |
| ---------------------------------- | ------- | ------------------------------------------------------------ |
| latitude                           | number  | Latitude of a location. If no latitude is available, 0.0 is returned. |
| longitude                          | number  | Longitude of a location. If no longitude is available, 0.0 is returned. |
| hasAltitude                        | boolean | True if this location has an altitude.                       |
| altitude                           | number  | Altitude of a location. If no altitude is available, 0.0 is returned. |
| hasSpeed                           | number  | True if this location has a speed.                           |
| speed                              | number  | Obtains a list of behaviors of a user in a specified period. The behaviors on the list are sorted by confidence. Each possible behavior has confidence, which indicates how likely a user is executing the behavior. |
| hasBearing                         | boolean | True if this location has a bearing.                         |
| bearing                            | number  | Bearing accuracy at the current location, in degrees. If no bearing accuracy is available, 0.0 is returned. |
| hasAccuracy                        | boolean | True if this location has a horizontal accuracy.             |
| accuracy                           | number  | Get the estimated horizontal accuracy of this location, radial, in meters. |
| elapsedRealtimeNanos               | number  | Time elapsed since system boot, in nanoseconds.              |
| time                               | number  | Timestamp, in milliseconds.                                  |
| isFromMockProvider                 | boolean | Returns true if the Location came from a mock provider.      |
| describeContents                   | number  | Describe the kinds of special objects contained in this Parcelable instance's marshaled representation. |
| provider                           | string  | Location provider, such as network location, GPS, Wi-Fi, and Bluetooth. |
| elapsedRealtimeUncertaintyNanos    | number  | Get estimate of the relative precision of the alignment of the ElapsedRealtimeNanos timestamp, with the reported measurements in nanoseconds (68% confidence). |
| hasElapsedRealtimeUncertaintyNanos | boolean | True if this location has a elapsed realtime accuracy.       |
| verticalAccuracyMeters             | number  | Get the estimated vertical accuracy of this location, in meters. |
| bearingAccuracyDegrees             | number  | Get the estimated bearing accuracy of this location, in degrees. |
| speedAccuracyMetersPerSecond       | number  | Get the estimated speed accuracy of this location, in meters per second. |
| hasSpeedAccuracy                   | boolean | True if this location has a speed accuracy.                  |
| hasBearingAccuracy                 | boolean | True if this location has a bearing accuracy.                |
| hasVerticalAccuracy                | boolean | True if this location has a vertical accuracy.               |

#### TimeCategoriesResponse   

Response to the request for obtaining the time.

##### Properties

| Name           | Type                                | Description           |
| -------------- | ----------------------------------- | --------------------- |
| timeCategories | [TimeCategories](#timecategories)[] | TimeCategories array. |

#### AmbientLightResponse   

Response to the request for obtaining the illuminance.

##### Properties

| Name               | Type                                        | Description               |
| ------------------ | ------------------------------------------- | ------------------------- |
| ambientLightStatus | [AmbientLightStatus](#ambientlightstatus)[] | AmbientLightStatus array. |

#### CapabilityResponse   

Response to the request for obtaining supported capabilities.

##### Properties

| Name         | Type                                    | Description             |
| ------------ | --------------------------------------- | ----------------------- |
| capabilities | [CapabilityStatus](#capabilitystatus)[] | CapabilityStatus array. |

#### EnableUpdateWindowResponse   

It is the answer from the [enableUpdateWindow](##hmsawarenesscapturemoduleenableupdatewindowisenabled) API.

##### Properties

| Name      | Type    | Description                  |
| --------- | ------- | ---------------------------- |
| isEnabled | boolean | Enable update window status. |

#### TimeCategories 

Parameter that represents semantic time of the current location.

##### Properties

| Name  | Type   | Description                                                  |
| ----- | ------ | ------------------------------------------------------------ |
| name  | string | Time categories name.                                                                                                                                            Options:                                                                                                                                                                     **"TIME_CATEGORY_WEEKDAY",  "TIME_CATEGORY_WEEKEND",  "TIME_CATEGORY_HOLIDAY", "TIME_CATEGORY_NOT_HOLIDAY", "TIME_CATEGORY_MORNING", "TIME_CATEGORY_AFTERNOON", "TIME_CATEGORY_EVENING", "TIME_CATEGORY_NIGHT"** |
| value | number | Time categories value.                                                                                                                                             Options:                                                                                                                                                                     **HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_WEEKDAY,  HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_WEEKEND,  HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_HOLIDAY   , HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_NOT_HOLIDAY     HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_MORNING    HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_AFTERNOON     HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_EVENING    HMSAwarenessBarrierModule. TimeBarrier_TIME_CATEGORY_NIGHT** |

#### Aqi  

Air quality indexes in [WeatherStatusResponse](#weatherstatusresponse). The indexes include those about the concentration of carbon monoxide (CO), nitrogen dioxide (NO2), ozone (O3), PM10, PM2.5, and sulphur dioxide (SO2), as well as the AQI. This object supports only data in China.

##### Properties

| Name             | Type   | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| co               | number | CO concentration index, which is the average value in 8 hours, in mg/m3. |
| no2              | number | NO2 concentration index, which is the average value in 1 hour, in μg/m3. |
| describeContents | number | Describe contents.                                           |
| aqiValue         | number | AQI information.                                             |
| getO3            | number | O3 concentration index, which is the average value in 1 hour, in μg/m3. |
| pm10             | number | PM10 concentration index (the diameter of particles is less than or equal to 10 μm), which is the average value in 24 hours, in μg/m3. |
| pm25             | number | PM2.5 concentration index (the diameter of particles is less than or equal to 2.5 μm), which is the average value in 24 hours, in μg/m3. |
| so2              | number | SO2 concentration index, which is the average value in 1 hour, in μg/m3. |

#### LiveInfo  

Living index level for the day and the next one or two days, including the code and level for the current day and next one to two days. This parameter supports only data in China.

##### Properties

| Name              | Type                              | Description                                                  |
| ----------------- | --------------------------------- | ------------------------------------------------------------ |
| dailyLiveInfoList | [DailyLiveInfo](#dailyliveinfo)[] | Information about a living index on a day. The indexes are about dressing, sports, temperature, car washing, tourism, and UV. This parameter supports only data in China. |
| code              | string                            | Obtains the living index code. Options are as follows: **1**: dressing index; **2**: sports index; **3**: coldness index; **4**: car washing index; **5**: tourism index; **6**: UV index. |
| describeContents  | number                            | Describe contents.                                           |

#### DailyLiveInfo  

Living index level for the day and the next one or two days, including the code and level for the current day and next one to two days. This parameter supports only data in China.

##### Properties

| Name             | Type   | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| level            | string | Obtains the level of a living index. For example, a dressing index has six levels from 1 to 6. |
| dateTimeStamp    | number | Timestamp of a living index, which is 00:00 of the local location. |
| describeContents | number | Describe contents.                                           |

#### DailyWeather  

Weather information of the current day and the next six to seven days, including the moonrise, moonset, sunrise, sunset, lowest temperature and highest temperature (Celsius and Fahrenheit), local timestamp in the early morning, month phase, weather in daytime (specified by [DailySituation](#dailysituation)), and weather at night (specified by [DailySituation](#dailysituation)).

##### Properties

| Name             | Type                              | Description                                                  |
| ---------------- | --------------------------------- | ------------------------------------------------------------ |
| timeStamp        | number                            | Timestamp of the weather of a day, which is 00:00 of the local location. |
| maxTempC         | number                            | Highest temperature (Celsius) of a day.                      |
| maxTempF         | number                            | Highest temperature (Fahrenheit) of a day.                   |
| minTempC         | number                            | Obtains the lowest temperature (Celsius) of a day.           |
| minTempF         | number                            | Lowest temperature (Fahrenheit) of a day.                    |
| moonphase        | string                            | Moon phase of a day. The options are **New**, **Waxingcrescent**, **First**, **WaxingGibbous**, **Full**, **WaningGibbous**, **Last**, and **WaningCrescent**. |
| moonRise         | number                            | Moonrise of a day.                                           |
| moonSet          | number                            | Moonset of a day.                                            |
| sunRise          | number                            | Sunrise of a day.                                            |
| sunSet           | number                            | Sunset of a day.                                             |
| aqiValue         | number                            |                                                              |
| describeContents | number                            | Describe contents.                                           |
| situationDay     | [DailySituation](#dailysituation) | Weather information in the daytime. For details, please refer to [DailySituation](#dailysituation) |
| situationNight   | [DailySituation](#dailysituation) | Weather information at night. For details, please refer to [DailySituation](#dailysituation) |

#### DailySituation  

Weather conditions in the daytime or at night, including the weather ID, wind speed, wind level, and wind direction.

##### Properties

| Name             | Type   | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| weatherId        | string | Weather ID. Check out the [Constants](#constants).           |
| weather          | number | Description of wetherId                                      |
| describeContents | number | Describe contents.                                           |
| cnWeatherId      | number | CN-Weather ID.  Check out the [Constants](#constants).       |
| cnWeather        | string | Description of cnWetherId                                    |
| windDir          | string | Wind direction. The options are **NE** (northeast), **E** (east), **SE** (southeast), **S** (south), **SW** (southwest), **W** (west), **N** (north), and **NW** (northwest). |
| windLevel        | number | Wind level. The value ranges from **0** to **17**. The value **0** indicates a breeze in China and no wind outside China. |
| windSpeed        | number | Wind speed (unit: km/h).                                     |

#### WeatherSituation  

Current weather information, which consists of the current weather information (specified by [Situation](#situation)) and city information (specified by [City](#city)).

##### Properties

| Name             | Type                    | Description                                                  |
| ---------------- | ----------------------- | ------------------------------------------------------------ |
| city             | [City](#city)           | City information in [WeatherStatusResponse](#weatherstatusresponse), including the city code, province name, time zone, and city/district name. |
| situation        | [Situation](#situation) | Current weather information. The information includes the weather ID, humidity, atmospheric temperature (Celsius and Fahrenheit), somatosensory temperature (Celsius and Fahrenheit), wind direction, wind level, wind speed, atmospheric pressure, UV intensity, and update time. |
| describeContents | number                  | Describe contents.                                           |
| timeZone         | number                  | Obtains the time zone of the city in Alson format. Example: Asia/shanghai |

#### City  

The object is includes the city code, province name, time zone, and city / district name.

##### Properties

| Name             | Type   | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| cityCode         | string | Code used in React Native Awareness Plugin to identify the state/province, city, county, and town in the world. |
| province         | string | Province name, which may be set to null.                     |
| describeContents | number | Describe contents.                                           |
| timeZone         | number | Obtains the time zone of the city in Alson format. Example: Asia/shanghai |

#### Situation  

Current weather information. The information includes the weather ID, humidity, atmospheric temperature (Celsius and Fahrenheit), somatosensory temperature (Celsius and Fahrenheit), wind direction, wind level, wind speed, atmospheric pressure, UV intensity, and update time.

##### Properties

| Name             | Type   | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| weatherId        | number | Weather ID.  Check out the [Constants](#constants).          |
| weather          | string | Description of weatherId                                     |
| humudity         | string | Humidity, in percentage.Province name, which may be set to null. |
| describeContents | number | Describe contents.                                           |
| timeZone         | number | Current atmospheric temperature (Celsius).                   |
| cnWeatherId      | number | CN-WeatherId. Check out the [Constants](#constants).         |
| cnWeather        | string | Description of cnWeatherId                                   |
| pressure         | number | Atmospheric pressure, in hPa.                                |
| realFeelC        | number | Somatosensory temperature (Celsius).                         |
| realFeelF        | number | Somatosensory temperature (Fahrenheit).                      |
| temperatureC     | number | Current atmospheric temperature (Celsius).                   |
| temperatureF     | number | Current temperature (Fahrenheit).                            |
| updateTime       | number | Release timestamp of the weather broadcast.                  |
| uvIndex          | number | UV intensity.                                                |
| windDir          | string | Wind direction. The options are **NE** (northeast), **E** (east), **SE** (southeast), **S** (south), **SW** (southwest), **W** (west), **N** (north), and **NW** (northwest). |
| windSpeed        | number | Wind speed (unit: km/h).                                     |
| windLevel        | number | Wind level. The value ranges from **0** to **17**. **0**: no wind |

#### DetectedBehavior  

Type and confidence of a detected behavior.

##### Properties

| Name             | Type   | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| type             | number | Behavior type.  You can view the returned data from [Constants](#constants) |
| confidence       | number | Confidence of the behavior. The value ranges from 0 to 100.  |
| describeContents | number | Describe contents.                                           |

#### HourlyWeather  

Weather information in the current hour and the next 24 hours, including the weather ID, time, temperature (Celsius and Fahrenheit), rainfall probability, and whether it is in the daytime or at night.

##### Properties

| Name             | Type    | Description                                            |
| ---------------- | ------- | ------------------------------------------------------ |
| weatherId        | number  | Weather ID.                                            |
| weather          | string  | Description of weatherId                               |
| describeContents | number  | Describe contents.                                     |
| cnWeatherId      | number  | CN-Weather ID.                                         |
| cnWeather        | string  | Description of cnWeatherId                             |
| rainprobability  | number  | Rainfall probability, in percentage.                   |
| tempC            | number  | Current temperature (Celsius).                         |
| tempF            | number  | Current temperature (Fahrenheit).                      |
| isDayNight       | boolean | **true** if it is in the daytime; **false** otherwise. |
| timeStamp        | number  | Timestamp in the current hour.                         |

#### BluetoothStatus  

Bluetooth status object.

##### Properties

| Name       | Type   | Description                                                  |
| ---------- | ------ | ------------------------------------------------------------ |
| status     | number | Bluetooth status.                                                         **HMSAwarenessBarrierModule.BluetoothStatus_CONNECTED**                       **HMSAwarenessBarrierModule.BluetoothStatus_DISCONNECTED**                                                                                     **HMSAwarenessBarrierModule.BluetoothStatus_UNKNOWN**   **HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR** |
| statusName | string | Bluetooth status name.                                       |

#### ScreenStatus  

Screen status object.

##### Properties

| Name       | Type   | Description                                                  |
| ---------- | ------ | ------------------------------------------------------------ |
| status     | number | Screen status.                                                                                                                                             **HMSAwarenessBarrierModule.ScreenStatus_UNLOCK**                       **HMSAwarenessBarrierModule.ScreenStatus_SCREEN_OFF**                                                                                     **HMSAwarenessBarrierModule.ScreenStatus_SCREEN_ON**   **HMSAwarenessBarrierModule.ScreenStatus_UNKNOWN** |
| statusName | string | Screen status name.                                          |

#### ApplicationStatus  

App status API. Three constants are defined for the app status: **RUNNING** (indicating that the app is running), **SILENT** (indicating that the app is not started), and **UNKNOWN** (indicating that the app status is unknown).

##### Properties

| Name       | Type   | Description                                                  |
| ---------- | ------ | ------------------------------------------------------------ |
| status     | number | Screen status.                                                                                                                                       **HMSAwarenessBarrierModule.ApplicationStatus_RUNNING**                       **HMSAwarenessBarrierModuleApplicationStatus_SILENT**                                                                                     **HMSAwarenessBarrierModule.ApplicationStatus_UNKNOWN**                              |
| statusName | string | Application status name.                                     |

#### AmbientLightStatus  

Illuminance status object.

##### Properties

| Name           | Type   | Description        |
| -------------- | ------ | ------------------ |
| lightIntensity | number | Illuminance value. |

#### CapabilityStatus 

This object includes 10 status constants, which represent the capabilities respectively.

##### Properties

| Name  | Type   | Description                                                  |
| ----- | ------ | ------------------------------------------------------------ |
| name  | string | Capability  name.                                            |
| value | number | Time categories value.                                                                                                                                             Options:                                                                                                                                                                     **HMSAwarenessBarrierModule.CapabilityStatus_HEADSET,  HMSAwarenessBarrierModule.CapabilityStatus_LOCATION_CAPTURE,  HMSAwarenessBarrierModule.CapabilityStatus_LOCATION_NORMAL_BARRIER   , HMSAwarenessBarrierModule.CapabilityStatus_LOCATION_LOW_POWER_BARRIER,     HMSAwarenessBarrierModule.CapabilityStatus_BEHAVIOR ,   HMSAwarenessBarrierModule.CapabilityStatus_TIME,     HMSAwarenessBarrierModule.CapabilityStatus_AMBIENT_LIGHT,   HMSAwarenessBarrierModule.CapabilityStatus_WEATHER,  HMSAwarenessBarrierModule.CapabilityStatus_BEACON ,  HMSAwarenessBarrierModule.CapabilityStatus_INCAR_BLUETOOTH,  HMSAwarenessBarrierModule.CapabilityStatus_SCREEN, HMSAwarenessBarrierModule.CapabilityStatus_WIFI, HMSAwarenessBarrierModule.CapabilityStatus_APPLICATION,  HMSAwarenessBarrierModule.CapabilityStatus_DARK_MODE** |

#### HeadsetStatus 

Headset status object.

##### Properties

| Name  | Type   | Description                                                  |
| ----- | ------ | ------------------------------------------------------------ |
| name  | string | Headset status name.                                         |
| value | number | Headset status value.                                                                                                                                             Options:                                                                                                                                                                     **HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED,  HMSAwarenessBarrierModule.HeadsetStatus_DISCONNECTED,  HMSAwarenessBarrierModule.HeadsetStatus_UNKNOWN** |

#### DarkModeStatus 

Darkmode status object.

##### Properties

| Name         | Type    | Description                                                  |
| ------------ | ------- | ------------------------------------------------------------ |
| isDarkModeOn | boolean | If **true** is returned, the dark mode is enabled. If **false** is returned, the dark mode is disabled. |

#### WifiStatus 

Wifi status object.

##### Properties

| Name       | Type   | Description                                                  |
| ---------- | ------ | ------------------------------------------------------------ |
| bssid      | string | Obtains the Wi-Fi BSSID.                                     |
| ssid       | string | Obtains the Wi-Fi SSID.                                      |
| status     | number | Wifi status value.                                                                                                                                             Options:                                                                                                                                                                     **HMSAwarenessBarrierModule.WifiStatus_CONNECTED,  HMSAwarenessBarrierModule.WifiStatus_DISCONNECTED,  HMSAwarenessBarrierModule.WifiStatus_ENABLED                   HMSAwarenessBarrierModule.WifiStatus_DISABLED,  HMSAwarenessBarrierModule.WifiStatus_UNKNOWN** |
| statusName | string | Wifi status name.                                            |

#### Beacon  

This is the becon object. 

##### Properties

| Name      | Type   | Description      |
| --------- | ------ | ---------------- |
| namespace | string | Beacon namespace |
| type      | string | Beacon type      |
| content   | string | Beacon Content   |
| beaconid  | string | Beacon Id        |

#### DailyLiveInfo  

Information about a living index on a day. The indexes are about dressing, sports, temperature, car washing, tourism, and UV. This object supports only data in China.

##### Properties

| Name             | Type   | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| timeStamp        | number | Timestamp of a living index, which is 00:00 of the local location. |
| level            | string | Level of a living index. For example, a dressing index has six levels from 1 to 6. |
| describeContents | number | Describe Contents                                            |

#### Beacon  

This is the becon object.

**Properties**

| Name      | Type   | Description      |
| --------- | ------ | ---------------- |
| namespace | string | Beacon namespace |
| type      | string | Beacon type      |
| content   | string | Beacon Content   |
| beaconid  | string | Beacon Id        |

#### SuccessObject  

This is the object that returns because the function runs successfully.

##### Properties

| Name     | Type   | Description           |
| -------- | ------ | --------------------- |
| response | string | It returns "success". |

#### ErrorObject  

This is the object that returns when the function does not work successfully.

##### Properties

| Name    | Type   | Description                                                  |
| ------- | ------ | ------------------------------------------------------------ |
| tag     | string | Tag name                                                                                                                                                       . |
| method  | string | Metod name                                                   |
| message | string | Error message                                                |


### Constants
| Name                                                         | Type   | Description                                                  |
| ------------------------------------------------------------ | ------ | ------------------------------------------------------------ |
| HMSAwarenessBarrierModule.AwarenessSC_SUCCESS                | number | Success.                                                     |
| HMSAwarenessBarrierModule.AwarenessSC_UNKNOWN_ERROR          | number | Unknown error.                                               |
| HMSAwarenessBarrierModule.AwarenessSC_BINDER_ERROR           | number | Bind service failure caused by an empty binder.              |
| HMSAwarenessBarrierModule.AwarenessSC_REGISTER_FAILED        | number | Barrier registration failure.                                |
| HMSAwarenessBarrierModule.AwarenessSC_TIMEOUT                | number | Timeout error. For example, one of the following events times out: barrier update, time attribute obtaining, beacon status obtaining, bind service connection, and waiting for intent. |
| HMSAwarenessBarrierModule.AwarenessSC_COUNT_LIMIT            | number | Error due to the access times limit. (For example, the number of times that a developer accesses the barrier reaches the upper limit, which is 200.) |
| HMSAwarenessBarrierModule.AwarenessSC_FREQUENCY_LIMIT        | number | Error due to the access frequency limit of the barrier or capture, which is 1000 times per hour or 20 times every 5s. The total number of barriers to be registered in 5s cannot exceed 500. |
| HMSAwarenessBarrierModule.AwarenessSC_BARRIER_PARAMETER_ERROR | number | Invalid barrier parameter. (An input parameter of **AddBarrier** is incorrect.) |
| HMSAwarenessBarrierModule.AwarenessSC_REQUEST_ERROR          | number | Incorrect request parameter. (An input parameter of **UpdateBarriers** is incorrect.) |
| HMSAwarenessBarrierModule.AwarenessSC_AGC_FILE_ERROR         | number | Incorrect AppGallery Connect file.                           |
| HMSAwarenessBarrierModule.AwarenessSC_RESULT_INVALID_ERROR   | number | Invalid result returned by Awareness Kit. For example, the returned headset status is unknown. |
| HMSAwarenessBarrierModule.AwarenessSC_REMOTE_EXCEPTION_ERROR | number | The remote exception is thrown by the bind function.         |
| HMSAwarenessBarrierModule.AwarenessSC_WAIT_CALLBACK          | number | Wait for the service callback.                               |
| HMSAwarenessBarrierModule.AwarenessSC_INTERFACE_INVALID      | number | The requested API no longer provides services.               |
| HMSAwarenessBarrierModule.AwarenessSC_NO_ENOUGH_RESOURCE     | number | Insufficient system resources.                               |
| HMSAwarenessBarrierModule.AwarenessSC_SDK_VERSION_ERROR      | number | Incorrect SDK version. Check whether the API level is greater than or equal to 24. |
| HMSAwarenessBarrierModule.AwarenessSC_LOCATION_PERMISSION    | number | The location permission in the app is forbidden.             |
| HMSAwarenessBarrierModule.AwarenessSC_LOCATION_CORE_PERMISSION | number | The location permission in HMS Core (APK) is forbidden.      |
| HMSAwarenessBarrierModule.AwarenessSC_BEHAVIOR_PERMISSION    | number | The behavior recognition permission in the app is forbidden. |
| HMSAwarenessBarrierModule.AwarenessSC_BLUETOOTH_PERMISSION   | number | The app does not apply for the Bluetooth permission.         |
| HMSAwarenessBarrierModule.AwarenessSC_WIFI_PERMISSION        | number | The Wi-Fi permission in the app is forbidden.                |
| HMSAwarenessBarrierModule.AwarenessSC_WIFI_CORE_PERMISSION   | number | The Wi-Fi permission in HMS Core (APK) is forbidden.         |
| HMSAwarenessBarrierModule.AwarenessSC_LOCATION_NOCACHE       | number | No location caches.                                          |
| HMSAwarenessBarrierModule.AwarenessSC_LOCATION_NOT_AVAILABLE | number | HUAWEI Location Kit is unavailable.                          |
| HMSAwarenessBarrierModule.AwarenessSC_BEACON_NOT_AVAILABLE   | number | The beacon service is unavailable.                           |
| HMSAwarenessBarrierModule.AwarenessSC_BLUETOOTH_NOT_AVAILABLE | number | The Bluetooth service is unavailable.                        |
| HMSAwarenessBarrierModule.AwarenessSC_APPLICATION_NOT_HUAWEI_PHONE | number | Non-Huawei phones do not support app status awareness.       |
| HMSAwarenessBarrierModule.AwarenessSC_UPDATE_KIT             | number | Awareness Kit needs to be updated.                           |
| HMSAwarenessBarrierModule.AwarenessSC_UPDATE_HMS             | number | HMS Core (APK) needs to be updated.                          |
| HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_IN_VEHICLE | number | The user is driving.                                         |
| HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_ON_BICYCLE | number | The user is cycling.                                         |
| HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_ON_FOOT   | number | The user is walking or running.                              |
| HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_STILL     | number | The user is running.                                         |
| HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_UNKNOWN   | number | The user is walking.                                         |
| HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_WALKING   | number | The user is still.                                           |
| HMSAwarenessBarrierModule.BehaviorBarrier_BEHAVIOR_RUNNING   | number | The current status of the user is not detected.              |
| HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_WEEKDAY  | number | Workday                                                      |
| HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_WEEKEND  | number | Weekend                                                      |
| HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_HOLIDAY  | number | Holiday                                                      |
| HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_NOT_HOLIDAY | number | Non-holiday date.                                            |
| HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_MORNING  | number | Morning time period, that is, 6:00–12:00.                    |
| HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_AFTERNOON | number | Afternoon time period, that is, 12:00–17:00.                 |
| HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_EVENING  | number | Evening time period, that is, 17:00–21:00.                   |
| HMSAwarenessBarrierModule.TimeBarrier_TIME_CATEGORY_NIGHT    | number | Night time period, that is, 21:00–06:00 (next day).          |
| HMSAwarenessBarrierModule.TimeBarrier_FRIDAY_CODE            | number | Friday                                                       |
| HMSAwarenessBarrierModule.TimeBarrier_MONDAY_CODE            | number | Monday                                                       |
| HMSAwarenessBarrierModule.TimeBarrier_SATURDAY_CODE          | number | Saturday                                                     |
| HMSAwarenessBarrierModule.TimeBarrier_SUNDAY_CODE            | number | Sunday                                                       |
| HMSAwarenessBarrierModule.TimeBarrier_THURSDAY_CODE          | number | Thursday                                                     |
| HMSAwarenessBarrierModule.TimeBarrier_TUESDAY_CODE           | number | Tuesday                                                      |
| HMSAwarenessBarrierModule.TimeBarrier_WEDNESDAY_CODE         | number | Wednesday                                                    |
| HMSAwarenessBarrierModule.TimeBarrier_SUNRISE_CODE           | number | Sunrise.                                                     |
| HMSAwarenessBarrierModule.TimeBarrier_SUNSET_CODE            | number | Sunset.                                                      |
| HMSAwarenessBarrierModule.CapabilityStatus_HEADSET           | number | Headset awareness capability.                                |
| HMSAwarenessBarrierModule.CapabilityStatus_LOCATION_CAPTURE  | number | Location awareness (capture) capability.                     |
| HMSAwarenessBarrierModule.CapabilityStatus_LOCATION_NORMAL_BARRIER | number | Location awareness (normal barrier) capability.              |
| HMSAwarenessBarrierModule.CapabilityStatus_LOCATION_LOW_POWER_BARRIER | number | Location awareness (low-power barrier) capability.           |
| HMSAwarenessBarrierModule.CapabilityStatus_BEHAVIOR          | number | Behavior awareness capability.                               |
| HMSAwarenessBarrierModule.CapabilityStatus_TIME              | number | Time awareness capability.                                   |
| HMSAwarenessBarrierModule.CapabilityStatus_AMBIENT_LIGHT     | number | Ambient light awareness capability.                          |
| HMSAwarenessBarrierModule.CapabilityStatus_WEATHER           | number | Weather awareness capability.                                |
| HMSAwarenessBarrierModule.CapabilityStatus_BEACON            | number | Beacon awareness capability.                                 |
| HMSAwarenessBarrierModule.CapabilityStatus_INCAR_BLUETOOTH   | number | Bluetooth car stereo awareness capability.                   |
| HMSAwarenessBarrierModule.CapabilityStatus_SCREEN            | number | Screen status awareness.                                     |
| HMSAwarenessBarrierModule.CapabilityStatus_WIFI              | number | Wi-Fi status awareness.                                      |
| HMSAwarenessBarrierModule.CapabilityStatus_APPLICATION       | number | App status awareness.                                        |
| HMSAwarenessBarrierModule.CapabilityStatus_DARK_MODE         | number | Dark mode awareness.                                         |
| HMSAwarenessBarrierModule.WeatherId_SUNNY                    | number | Sunny                                                        |
| HMSAwarenessBarrierModule.WeatherId_MOSTLY_SUNNY             | number | Mostly sunny.                                                |
| HMSAwarenessBarrierModule.WeatherId_PARTLY_SUNNY             | number | Partly sunny.                                                |
| HMSAwarenessBarrierModule.WeatherId_INTERMITTENT_CLOUDS      | number | Intermittent clouds.                                         |
| HMSAwarenessBarrierModule.WeatherId_HAZY_SUNSHINE            | number | Hazy sunshine.                                               |
| HMSAwarenessBarrierModule.WeatherId_MOSTLY_CLOUDY            | number | Mostly cloudy.                                               |
| HMSAwarenessBarrierModule.WeatherId_CLOUDY                   | number | Cloudy.                                                      |
| HMSAwarenessBarrierModule.WeatherId_DREARY                   | number | Dreary.                                                      |
| HMSAwarenessBarrierModule.WeatherId_FOG                      | number | Fog.                                                         |
| HMSAwarenessBarrierModule.WeatherId_SHOWERS                  | number | Shower.                                                      |
| HMSAwarenessBarrierModule.WeatherId_MOSTLY_CLOUDY_WITH_SHOWERS | number | Mostly cloudy with showers.                                  |
| HMSAwarenessBarrierModule.WeatherId_PARTLY_SUNNY_WITH_SHOWERS | number | Partly sunny with showers.                                   |
| HMSAwarenessBarrierModule.WeatherId_T_STORMS                 | number | Thunderstorm.                                                |
| HMSAwarenessBarrierModule.WeatherId_MOSTLY_CLOUDY_WITH_T_STORMS | number | Mostly cloudy with thunderstorms.                            |
| HMSAwarenessBarrierModule.WeatherId_PARTLY_SUNNY_WITH_T_STORMS | number | Partly sunny with thunderstorms.                             |
| HMSAwarenessBarrierModule.WeatherId_RAIN                     | number | Rain.                                                        |
| HMSAwarenessBarrierModule.WeatherId_FLURRIES                 | number | Light snow.                                                  |
| HMSAwarenessBarrierModule.WeatherId_MOSTLY_CLOUDY_WITH_FLURRIES | number | Mostly cloudy with light snow.                               |
| HMSAwarenessBarrierModule.WeatherId_PARTLY_SUNNY_WITH_FLURRIES | number | Partly sunny with light snow.                                |
| HMSAwarenessBarrierModule.WeatherId_SNOW                     | number | Snow.                                                        |
| HMSAwarenessBarrierModule.WeatherId_MOSTLY_CLOUDY_WITH_SNOW  | number | Mostly cloudy with snow.                                     |
| HMSAwarenessBarrierModule.WeatherId_ICE                      | number | Ice.                                                         |
| HMSAwarenessBarrierModule.WeatherId_SLEET                    | number | Sleet.                                                       |
| HMSAwarenessBarrierModule.WeatherId_FREEZING_RAIN            | number | Ice rain.                                                    |
| HMSAwarenessBarrierModule.WeatherId_RAIN_AND_SNOW            | number | Rain and snow.                                               |
| HMSAwarenessBarrierModule.WeatherId_HOT                      | number | Hot.                                                         |
| HMSAwarenessBarrierModule.WeatherId_COLD                     | number | Cold.                                                        |
| HMSAwarenessBarrierModule.WeatherId_WINDY                    | number | Windy.                                                       |
| HMSAwarenessBarrierModule.WeatherId_CLEAR                    | number | Clear.                                                       |
| HMSAwarenessBarrierModule.WeatherId_PARTLY_CLOUDY            | number | Mostly clear.                                                |
| HMSAwarenessBarrierModule.WeatherId_INTERMITTENT_CLOUDS_2    | number | Partly cloudy.                                               |
| HMSAwarenessBarrierModule.WeatherId_HAZY_MOONLIGHT           | number | Hazy moonlight.                                              |
| HMSAwarenessBarrierModule.WeatherId_MOSTLY_CLOUDY_2          | number | Mostly cloudy.                                               |
| HMSAwarenessBarrierModule.WeatherId_PARTLY_CLOUDY_WITH_SHOWERS | number | Partly cloudy with showers.                                  |
| HMSAwarenessBarrierModule.WeatherId_MOSTLY_CLOUDY_WITH_SHOWERS_2 | number | Mostly cloudy with showers.                                  |
| HMSAwarenessBarrierModule.WeatherId_PARTLY_CLOUDY_WITH_T_STORMS | number | Cloudy with thunderstorms.                                   |
| HMSAwarenessBarrierModule.WeatherId_MOSTLY_CLOUDY_WITH_T_STORMS_2 | number | Mostly cloudy with thunderstorms.                            |
| HMSAwarenessBarrierModule.WeatherId_MOSTLY_CLOUDY_WITH_FLURRIES_2 | number | Mostly cloudy with flurries.                                 |
| HMSAwarenessBarrierModule.WeatherId_MOSTLY_CLOUDY_WITH_SNOW_2 | number | Mostly cloudy with snow.                                     |
| HMSAwarenessBarrierModule.CNWeatherId_INVALID_VALUE          | number | Invalid value.                                               |
| HMSAwarenessBarrierModule.CNWeatherId_SUNNY                  | number | Sunny.                                                       |
| HMSAwarenessBarrierModule.CNWeatherId_CLOUDY                 | number | Cloudy.                                                      |
| HMSAwarenessBarrierModule.CNWeatherId_OVERCAST               | number | Overcast.                                                    |
| HMSAwarenessBarrierModule.CNWeatherId_SHOWER                 | number | Shower.                                                      |
| HMSAwarenessBarrierModule.CNWeatherId_THUNDERSHOWER          | number | Thundershower.                                               |
| HMSAwarenessBarrierModule.CNWeatherId_THUNDERSHOWER_WITH_HAIL | number | Thunder shower with hail.                                    |
| HMSAwarenessBarrierModule.CNWeatherId_SLEET                  | number | Sleet.                                                       |
| HMSAwarenessBarrierModule.CNWeatherId_LIGHT_RAIN             | number | Light rain.                                                  |
| HMSAwarenessBarrierModule.CNWeatherId_MODERATE_RAIN          | number | Moderate rain.                                               |
| HMSAwarenessBarrierModule.CNWeatherId_HEAVY_RAIN             | number | Heavy rain.                                                  |
| HMSAwarenessBarrierModule.CNWeatherId_STORM                  | number | Storm.                                                       |
| HMSAwarenessBarrierModule.CNWeatherId_HEAVY_STORM            | number | Heavy storm.                                                 |
| HMSAwarenessBarrierModule.CNWeatherId_SEVERE_STORM           | number | Severe storm.                                                |
| HMSAwarenessBarrierModule.CNWeatherId_SNOW_FLURRY            | number | Snow flurry.                                                 |
| HMSAwarenessBarrierModule.CNWeatherId_LIGHT_SNOW             | number | Light snow.                                                  |
| HMSAwarenessBarrierModule.CNWeatherId_MODERATE_SNOW          | number | Moderate snow.                                               |
| HMSAwarenessBarrierModule.CNWeatherId_HEAVY_SNOW             | number | Heavy snow.                                                  |
| HMSAwarenessBarrierModule.CNWeatherId_SNOWSTORM              | number | Snowstorm.                                                   |
| HMSAwarenessBarrierModule.CNWeatherId_FOGGY                  | number | Foggy.                                                       |
| HMSAwarenessBarrierModule.CNWeatherId_ICE_RAIN               | number | Ice rain.                                                    |
| HMSAwarenessBarrierModule.CNWeatherId_DUSTSTORM              | number | Duststorm.                                                   |
| HMSAwarenessBarrierModule.CNWeatherId_LIGHT_TO_MODERATE_RAIN | number | Light to moderate rain.                                      |
| HMSAwarenessBarrierModule.CNWeatherId_MODERATE_TO_HEAVY_RAIN | number | Moderate to heavy rain.                                      |
| HMSAwarenessBarrierModule.CNWeatherId_HEAVY_RAIN_TO_STORM    | number | Heavy rain to storm.                                         |
| HMSAwarenessBarrierModule.CNWeatherId_STORM_TO_HEAVY_STORM   | number | Storm to heavy storm.                                        |
| HMSAwarenessBarrierModule.CNWeatherId_HEAVY_TO_SEVERE_STORM  | number | Heavy to severe storm.                                       |
| HMSAwarenessBarrierModule.CNWeatherId_LIGHT_TO_MODERATE_SNOW | number | Light to moderate snow.                                      |
| HMSAwarenessBarrierModule.CNWeatherId_MODERATE_TO_HEAVY_SNOW | number | Moderate to heavy snow.                                      |
| HMSAwarenessBarrierModule.CNWeatherId_HEAVY_SNOW_TO_SNOWSTORM | number | Heavy snow to snowstorm.                                     |
| HMSAwarenessBarrierModule.CNWeatherId_DUST                   | number | Dust.                                                        |
| HMSAwarenessBarrierModule.CNWeatherId_SAND                   | number | Sand.                                                        |
| HMSAwarenessBarrierModule.CNWeatherId_SANDSTORM              | number | Sand storm.                                                  |
| HMSAwarenessBarrierModule.CNWeatherId_DENSE_FOGGY            | number | Dense foggy.                                                 |
| HMSAwarenessBarrierModule.CNWeatherId_SNOW                   | number | Snow.                                                        |
| HMSAwarenessBarrierModule.CNWeatherId_MODERATE_FOGGY         | number | Moderate foggy.                                              |
| HMSAwarenessBarrierModule.CNWeatherId_HAZE                   | number | Haze.                                                        |
| HMSAwarenessBarrierModule.CNWeatherId_MODERATE_HAZE          | number | Moderate haze.                                               |
| HMSAwarenessBarrierModule.CNWeatherId_HEAVY_HAZE             | number | Heavy haze.                                                  |
| HMSAwarenessBarrierModule.CNWeatherId_SEVERE_HAZE            | number | Severe haze.                                                 |
| HMSAwarenessBarrierModule.CNWeatherId_HEAVY_FOGGY            | number | Heavy foggy.                                                 |
| HMSAwarenessBarrierModule.CNWeatherId_SEVERE_FOGGY           | number | Severe foggy                                                 |
| HMSAwarenessBarrierModule.CNWeatherId_RAINFALL               | number | Rainfall.                                                    |
| HMSAwarenessBarrierModule.CNWeatherId_SNOWFALL               | number | Snowfall.                                                    |
| HMSAwarenessBarrierModule.CNWeatherId_UNKNOWN                | number | Unknown.                                                     |
| HMSAwarenessBarrierModule.WeatherStatus_CLOTHING_INDEX       | number | Coudhing index.                                              |
| HMSAwarenessBarrierModule.WeatherStatus_SPORT_INDEX          | number | Sport index.                                                 |
| HMSAwarenessBarrierModule.WeatherStatus_COLD_INDEX           | number | Cold index.                                                  |
| HMSAwarenessBarrierModule.WeatherStatus_CAR_WASH_INDEX       | number | Car wash inex.                                               |
| HMSAwarenessBarrierModule.WeatherStatus_TOURISM_INDEX        | number | Tourism index                                                |
| HMSAwarenessBarrierModule.WeatherStatus_UV_INDEX             | number | UV index.                                                    |
| HMSAwarenessBarrierModule.WeatherStatus_DOWN_JACKET          | number | Down jacket.                                                 |
| HMSAwarenessBarrierModule.WeatherStatus_HEAVY_COAT           | number | Heavy coat                                                   |
| HMSAwarenessBarrierModule.WeatherStatus_SWEATER              | number | Sweater                                                      |
| HMSAwarenessBarrierModule.WeatherStatus_THIN_COAT            | number | Thin coat                                                    |
| HMSAwarenessBarrierModule.WeatherStatus_LONG_SLEEVES         | number | Long sleeves                                                 |
| HMSAwarenessBarrierModule.WeatherStatus_SHORT_SLEEVE         | number | Short sleeve                                                 |
| HMSAwarenessBarrierModule.WeatherStatus_THIN_SHORT_SLEEVE    | number | Thin short sleeve                                            |
| HMSAwarenessBarrierModule.WeatherStatus_SUITABLE_SPORT       | number | Suitable sport                                               |
| HMSAwarenessBarrierModule.WeatherStatus_MORE_SUITABLE_SPORT  | number | More suitable sport                                          |
| HMSAwarenessBarrierModule.WeatherStatus_NOT_SUITABLE_SPORT   | number | Not suitable sport                                           |
| HMSAwarenessBarrierModule.WeatherStatus_NOT_EASY_CATCH_COLD  | number | Not easy catch cold                                          |
| HMSAwarenessBarrierModule.WeatherStatus_EASIER_CATCH_COLD    | number | Easier catch cold                                            |
| HMSAwarenessBarrierModule.WeatherStatus_BE_SUSCEPTIBLE_COLD  | number | Be susceptible cold.                                         |
| HMSAwarenessBarrierModule.WeatherStatus_EXTREMELY_SUSCEPTIBLE_COLD | number | Extremely susceptible cold                                   |
| HMSAwarenessBarrierModule.WeatherStatus_UNSUITABLE           | number | Unsuitable                                                   |
| HMSAwarenessBarrierModule.WeatherStatus_NOT_VERY_SUITABLE    | number | Not very suitable                                            |
| HMSAwarenessBarrierModule.WeatherStatus_MORE_SUITABLE        | number | More suitable                                                |
| HMSAwarenessBarrierModule.WeatherStatus_SUITABLE             | number | Suitable                                                     |
| HMSAwarenessBarrierModule.WeatherStatus_WEAKEST              | number | Weakest                                                      |
| HMSAwarenessBarrierModule.WeatherStatus_WEAK                 | number | Week                                                         |
| HMSAwarenessBarrierModule.WeatherStatus_MEDIUM               | number | Medium                                                       |
| HMSAwarenessBarrierModule.WeatherStatus_STRONG               | number | Strong.                                                      |
| HMSAwarenessBarrierModule.WeatherStatus_VERY_STRONG          | number | Very strong.                                                 |
| HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED            | number | A headset is connected.                                      |
| HMSAwarenessBarrierModule.HeadsetStatus_DISCONNECTED         | number | A headset is disconnected.                                   |
| HMSAwarenessBarrierModule.HeadsetStatus_UNKNOWN              | number | A headset is unknown.                                        |
| HMSAwarenessBarrierModule.BluetoothStatus_CONNECTED          | number | The Bluetooth car stereo is connected.                       |
| HMSAwarenessBarrierModule.BluetoothStatus_DISCONNECTED       | number | The Bluetooth car stereo is not connected.                   |
| HMSAwarenessBarrierModule.BluetoothStatus_UNKNOWN            | number | The Bluetooth car stereo is unkown.                          |
| HMSAwarenessBarrierModule.BluetoothStatus_DEVICE_CAR         | number | Bluetooth car stereo                                         |
| HMSAwarenessBarrierModule.ScreenStatus_UNLOCK                | number | The screen is unlock.                                        |
| HMSAwarenessBarrierModule.ScreenStatus_SCREEN_OFF            | number | The screen is off.                                           |
| HMSAwarenessBarrierModule.ScreenStatus_SCREEN_ON             | number | The screen is on.                                            |
| HMSAwarenessBarrierModule.ScreenStatus_UNKNOWN               | number | The screen is unknown.                                       |
| HMSAwarenessBarrierModule.WifiStatus_CONNECTED               | number | The wifi is connected.                                       |
| HMSAwarenessBarrierModule.WifiStatus_DISCONNECTED            | number | The screen is disconnected.                                  |
| HMSAwarenessBarrierModule.WifiStatus_ENABLED                 | number | The screen is enabled.                                       |
| HMSAwarenessBarrierModule.WifiStatus_DISABLED                | number | The screen is disabled.                                      |
| HMSAwarenessBarrierModule.WifiStatus_UNKNOWN                 | number | The screen is unknown.                                       |
| HMSAwarenessBarrierModule.BarrierStatus_TRUE                 | number | Barrier status True.                                         |
| HMSAwarenessBarrierModule.BarrierStatus_FALSE                | number | Barrier status False                                         |
| HMSAwarenessBarrierModule.BarrierStatus_UNKNOWN              | number | Barrier status Unknown.                                      |
| HMSAwarenessBarrierModule.ApplicationStatus_RUNNING          | number | The app is running                                           |
| HMSAwarenessBarrierModule.ApplicationStatus_SILENT           | number | The app is not started.                                      |
| HMSAwarenessBarrierModule.ApplicationStatus_UNKNOWN          | number | The app status cannot be obtained.                           |
| HMSAwarenessBarrierModule.DarkModeStatus_DARK_MODE_OFF       | number | The dark mode is disabled.                                   |
| HMSAwarenessBarrierModule.DarkModeStatus_DARK_MODE_ON        | number | The dark mode is enabled.                                    |
| HMSAwarenessBarrierModule.DarkModeStatus_DARK_MODE_UNKNOWN   | number | The dark mode is unknown.                                    |
| HMSAwarenessBarrierModule.EVENT_HEADSET                      | string | It is the [BarrierEventType ](#barriereventType)parameter used when creating the headset barrier. |
| HMSAwarenessBarrierModule.EVENT_AMBIENTLIGHT                 | string | It is the [BarrierEventType ](#barriereventType) parameter used when creating the ambient light barrier. |
| HMSAwarenessBarrierModule.EVENT_WIFI                         | string | It is the [BarrierEventType ](#barriereventType) parameter used when creating the wifi barrier. |
| HMSAwarenessBarrierModule.EVENT_BLUETOOTH                    | string | It is the [BarrierEventType ](#barriereventType) parameter used when creating the bluetooth barriers. |
| HMSAwarenessBarrierModule.EVENT_BEHAVIOR                     | string | It is the [BarrierEventType ](#barriereventType) parameter used when creating the behavior barriers. |
| HMSAwarenessBarrierModule.EVENT_LOCATION                     | string | It is the [BarrierEventType ](#barriereventType) parameter used when creating the location barriers. |
| HMSAwarenessBarrierModule.EVENT_SCREEN                       | string | It is the [BarrierEventType ](#barriereventType) parameter used when creating the screen barriers. |
| HMSAwarenessBarrierModule.EVENT_TIME                         | string | It is the [BarrierEventType ](#barriereventType) parameter used when creating the time barriers. |
| HMSAwarenessBarrierModule.EVENT_BEACON                       | string | It is the [BarrierEventType ](#barriereventType) parameter used when creating the beacon barriers. |
| HMSAwarenessBarrierModule.EVENT_UPDATE_WINDOW                | string | It is the [BarrierEventType ](#barriereventType) parameter used  for updateWindowEnable function. |
| HMSAwarenessBarrierModule.EVENT_COMBINED                     | string | It is the [BarrierEventType ](#barriereventType) parameter used when creating the combined barrier. |
| HMSAwarenessBarrierModule.EVENT_HEADSET_KEEPING              | string | It is the barrierReceiverAction parameter used when creating the headset keeping barrier. |
| HMSAwarenessBarrierModule.EVENT_HEADSET_CONNECTING           | string | It is the barrierReceiverAction parameter used when creating the headset connecting barrier. |
| HMSAwarenessBarrierModule.EVENT_HEADSET_DISCONNECTING        | string | It is the barrierReceiverAction parameter used when creating the headset disconnecting barrier. |
| HMSAwarenessBarrierModule.AMBIENTLIGHT_ABOVE                 | string | It is the barrierReceiverAction parameter used when creating the headset ambient light above barrier. |
| HMSAwarenessBarrierModule.AMBIENTLIGHT_BELOW                 | string | It is the barrierReceiverAction parameter used when creating the headset ambient light below barrier. |
| HMSAwarenessBarrierModule.AMBIENTLIGHT_RANGE                 | string | It is the barrierReceiverAction parameter used when creating the headset ambient light  range barrier. |
| HMSAwarenessBarrierModule.BEACON_DISCOVER                    | string | It is the barrierReceiverAction parameter used when creating the beacon discover barrier. |
| HMSAwarenessBarrierModule.BEACON_KEEP                        | string | It is the barrierReceiverAction parameter used when creating the beacon keep barrier. |
| HMSAwarenessBarrierModule.BEACON_MISSED                      | string | It is the barrierReceiverAction parameter used when creating the beacon missed barrier. |
| HMSAwarenessBarrierModule.BEHAVIOR_KEEPING                   | string | It is the barrierReceiverAction parameter used when creating the behavior keeping barrier. |
| HMSAwarenessBarrierModule.BEHAVIOR_BEGINNING                 | string | It is the barrierReceiverAction parameter used when creating the behavior beginning barrier. |
| HMSAwarenessBarrierModule.BEHAVIOR_ENDING                    | string | It is the barrierReceiverAction parameter used when creating the behavior ending barrier. |
| HMSAwarenessBarrierModule.BLUETOOTH_KEEP                     | string | It is the barrierReceiverAction parameter used when creating the bluetooth keeping  barrier. |
| HMSAwarenessBarrierModule.BLUETOOTH_CONNECTING               | string | It is the barrierReceiverAction parameter used when creating the bluetooth connecting barrier. |
| HMSAwarenessBarrierModule.BLUETOOTH_DISCONNECTING            | string | It is the barrierReceiverAction parameter used when creating the bluetooth disconnecting barrier. |
| HMSAwarenessBarrierModule.LOCATION_ENTER                     | string | It is the barrierReceiverAction parameter used when creating the location enter barrier. |
| HMSAwarenessBarrierModule.LOCATION_STAY                      | string | It is the barrierReceiverAction parameter used when creating the location stay barrier. |
| HMSAwarenessBarrierModule.LOCATION_EXIT                      | string | It is the barrierReceiverAction parameter used when creating the location exit barrier. |
| HMSAwarenessBarrierModule.SCREEN_KEEPING                     | string | It is the barrierReceiverAction parameter used when creating the screen keeping barrier. |
| HMSAwarenessBarrierModule.SCREEN_ON                          | string | It is the barrierReceiverAction parameter used when creating the screen on barrier. |
| HMSAwarenessBarrierModule.SCREEN_OFF                         | string | It is the barrierReceiverAction parameter used when creating the screen off barrier. |
| HMSAwarenessBarrierModule.SCREEN_UNLOCK                      | string | It is the barrierReceiverAction parameter used when creating the screen unlock barrier. |
| HMSAwarenessBarrierModule.TIME_IN_SUNRISE_OR_SUNSET_PERIOD   | string | It is the barrierReceiverAction parameter used when creating the time-in sunrise or sunset period barrier. |
| HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_DAY          | string | It is the barrierReceiverAction parameter used when creating the time-during period of day barrier. |
| HMSAwarenessBarrierModule.TIME_DURING_TIME_PERIOD            | string | It is the barrierReceiverAction parameter used when creating the time-during time period barrier. |
| HMSAwarenessBarrierModule.TIME_DURING_PERIOD_OF_WEEK         | string | It is the barrierReceiverAction parameter used when creating the time-during period of week barrier. |
| HMSAwarenessBarrierModule.TIME_IN_TIME_CATEGORY              | string | It is the barrierReceiverAction parameter used when creating the time-in time category barrier. |
| HMSAwarenessBarrierModule.WIFI_KEEPING                       | string | It is the barrierReceiverAction parameter used when creating the wifi keeping barrier. |
| HMSAwarenessBarrierModule.WIFI_CONNECTING                    | string | It is the barrierReceiverAction parameter used when creating the wifi connecting barrier. |
| HMSAwarenessBarrierModule.WIFI_DISCONNECTING                 | string | It is the barrierReceiverAction parameter used when creating the wifi disconnecting barrier. |
| HMSAwarenessBarrierModule.WIFI_ENABLING                      | string | It is the barrierReceiverAction parameter used when creating the wifi enabling barrier. |
| HMSAwarenessBarrierModule.WIFI_DISABLING                     | string | It is the barrierReceiverAction parameter used when creating the wifi disabling barrier. |
| HMSAwarenessBarrierModule.BARRIER_TYPE_AND                   | string | It is the barrierType parameter used when creating the    [LogicalObject](#logicalobject) barrier. |
| HMSAwarenessBarrierModule.BARRIER_TYPE_OR                    | string | It is the barrierType parameter used when creating the    [LogicalObject](#logicalobject) barrier. |
| HMSAwarenessBarrierModule.BARRIER_TYPE_NOT                   | string | It is the barrierType parameter used when creating the    [LogicalObject](#logicalobject) barrier. |
| HMSAwarenessBarrierModule.TASK_NAME                          | string | The name of the task to rest. (You can listen to barrier triggers with this Task name when the application is closed.) |

---

## 4. Configuration and Description

Before building a release version of your app you may need to customize the **proguard-rules**.pro obfuscation configuration file to prevent the HMS Core SDK from being obfuscated. Add the configurations below to exclude the HMS Core SDK from obfuscation. For more information on this topic refer to [this Android developer guide](https://developer.android.com/studio/build/shrink-code).

**\<react_native_project>/android/app/proguard-rules.pro**

```
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
```

**\<react_native_project>/android/app/build.gradle**

```groovy
buildTypes {
    debug {
        signingConfig signingConfigs.config
    }
    release {
        signingConfig signingConfigs.config
        // Enables code shrinking, obfuscation and optimization for release builds
        minifyEnabled true
        // Unused resources will be removed, resources defined in the res/raw/keep.xml will be kept.
        shrinkResources true
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }
}
```
---

## 5. Sample Project

This plugin includes a demo project in the [example](example) folder, there you can find more usage examples.

<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-awareness/.docs/Page1.jpg" width = 300px style="margin:1em">
<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-awareness/.docs/Page2.jpg" width = 300px style="margin:1em">
<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-awareness/.docs/Page3.jpg" width = 300px style="margin:1em">
<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-awareness/.docs/Page4.jpg" width = 300px style="margin:1em">
<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-awareness/.docs/Page5.jpg" width = 300px style="margin:1em">
<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-awareness/.docs/Page6.jpg" width = 300px style="margin:1em">
<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-awareness/.docs/Page7.jpg" width = 300px style="margin:1em">

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