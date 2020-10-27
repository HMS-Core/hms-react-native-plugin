# React-Native HMS Analytics

---

## Contents

  - [1. Introduction](#1-introduction)
  - [2. Installation Guide](#2-installation-guide)
    - [Creating a Project in App Gallery Connect](#creating-a-project-in-appgallery-connect)
    - [Enabling Analytics Service](#enabling-analytics-service)
    - [Integrating the React Native Analytics Plugin](#integrating-the-react-native-analytics-plugin)
        - [Android App Development](#android-app-development)
            - [Configuring the Signing Certificate Fingerprint](#configuring-the-signing-certificate-fingerprint)
            - [Integrating the React-Native HMS Anaytics into the Android Studio](#integrating-the-react-native-hms-anaytics-into-the-android-studio)
        - [iOS App Development](#ios-app-development)
            - [Integrating the React-Native HMS Anaytics into the Xcode Project](#integrating-the-react-native-hms-anaytics-into-the-xcode-project)
  - [3. API Reference](#3-api-reference)
    - [HmsAnalytics](#hmsanalytics)
    - [Data Types](#data-types)
  - [4. Configuration and Description](#4-configuration-and-description)
  - [5. Sample Project](#5-sample-project)
  - [6. Questions or Issues](#6-questions-or-issues)
  - [7. Licensing and Terms](#7-licensing-and-terms)

---

## 1. Introduction

This module enables communication between **HUAWEI Analytics Kit** and React Native platform. It exposes all functionality provided by **HUAWEI Analytics Kit** which offers a rich array of preset analytics models that help you gain a deeper insight into your users, products, and content. With this insight, you can then take a data-driven approach to market your apps and optimize your products.

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

### Enabling Analytics Service

**Step 1.** In [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html), find your project, and click the app for which you want to view analytics data.

**Step 2.** Select any menu under **HUAWEI Analytics** and click **Enable Analytics service**. (Only users with the management permission can perform this operation.)

**Step 3:** On the **Project access settings** page, set the data storage location, data sharing, industry analysis, user identification mode, time zone, currency, user data storage time, and calendar week, and click **Finish**. If you have set the app category, **Enable industry analysis** is turned on by default.

For further details, please refer to [Service Enabling](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/service-enabling-0000001050745155).

### Integrating the React-Native Analytics Plugin

Before using **@hmscore/react-native-hms-analytics**, ensure that the ReactNative development environment has been installed.

#### Option 1: Install via NPM

```
npm i @hmscore/react-native-hms-analytics
```

#### Option 2: Copy the library into the demo project 

- In order to reach the library from demo app, the library should be copied under the node_modules folder of the project.

The structure should be like this
```
            demo-app
                |_ node_modules
                    |_ @hmscore
                        |_ react-native-hms-analytics
                        ...
```

#### Android App Development

#### Configuring the Signing Certificate Fingerprint

A signing certificate fingerprint is used to verify the authenticity of an android app when it attempts to access an HMS Core (APK) through the HMS SDK. Before using the HMS Core (APK), you must locally generate a signing certificate fingerprint and configure it in the **AppGallery Connect**. You can refer to 3rd and 4th steps of [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#2) codelab tutorial for the certificate generation. Perform the following steps after you have generated the certificate.

**Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and select your project from **My Projects**. Then go to **Project Setting** > **General information**. In the **App information** field, click the  icon next to SHA-256 certificate fingerprint, and enter the obtained **SHA-256 certificate fingerprint**.

**Step 2:**  After completing the configuration, click **OK** to save the changes. (Check mark icon)

#### Integrating the React-Native HMS Anaytics into the Android Studio

- Add the AppGallery Connect configuration file of the app to your Android Studio project.

    **Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and click **My projects**.
    
    **Step 2:** Find your **app** project and click the app that needs to integrate the HMS Core SDK.
    
    **Step 3:** Go to **Project Setting > App information**. In the **App information** area, download the **agconnect-services.json** file.
    
    **Step 4:** Copy the **agconnect-services.json** file to the app's root directory of your project.
    
- Open the **build.gradle** file in the **android** directory of your React Native project. Navigate into **buildscript**, configure the Maven repository address and agconnect plugin for the HMS SDK.

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

- Open the **build.gradle** file in the **android/app** directory of your React Native project.

    Set your package name in **defaultConfig** > **applicationId** and set **minSdkVersion** to **19** or **higher**.

    ```groovy
    defaultConfig {
     applicationId "<package_name>"
     minSdkVersion 19
     /*
      * <Other configurations>
      */
    }
    ```
    
    **Package name must match with the _package_name_ entry in _agconnect-services.json_ file.**

- Configure the signature according to the signature file that generated in [Configuring the Signing Certificate Fingerprint](#configuring-the-signing-certificate-fingerprint) to _android/app_ directory information.
    
    ```gradle
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
            }
        }
    }
    ```
- Open build.gradle file which is located under project.dir > android > app directory. 

    Configure build dependencies.

    ```groovy
    buildscript {
    ...
    dependencies {
        /*
         * <Other dependencies>
         */
        implementation project(":react-native-hms-analytics")    
        ...    
    }
    }
    ```

- Add the following lines to the **android/settings.gradle** file in your project:

    ```groovy
    include ':app'
    include ':react-native-hms-analytics'
    project(':react-native-hms-analytics').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-analytics/android')
    ```
    
- Add **HmsAnalyticsPackage** to your application.

    Refer to your application, add the following line to your **getPackages()** method.

    ```java
    packages.add(new com.huawei.hms.rn.analytics.HmsAnalyticsPackage());// <-- Add this line in `getPackages()` to import the HmsAnalyticsPackage and use in your app.
    ```

#### iOS App Development

#### Integrating the React-Native HMS Anaytics into the Xcode Project

- Add the AppGallery Connect configuration file of the app to your Xcode project.

    **Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and click **My projects**.
    
    **Step 2:** Find your **app** project and click the app that needs to integrate the HMS Core SDK.
    
    **Step 3:** Go to **Project Setting > App information**. In the **App information** area, download the **agconnect-services.plist** file.
    
    **Step 4:** Copy the **agconnect-services.plist** file to the app's root directory of your Xcode project.
    
    Before obtaining the **agconnect-services.plist** file, ensure that you have enabled HUAWEI Analytics. For details, please refer to [Enabling HUAWEI Analytics](#enabling-analytics-service).
    
    If you have made any changes on the Project Setting page, such as setting the data storage location and enabling or managing APIs, you need to download the latest **agconnect-services.plist** file and replace the existing file in the app's root directory.

- Edit the Podfile file.

   **Step 1:** Add **pod 'HiAnalytics'**, that is, the dependency of pods.
    
    ```ruby
    # Huawei Analytics Kit SDK
    pod 'HiAnalytics', '5.0.4.300'
    ``` 
    
    **Step 2:** Run the **pod install** command to install the pods.
    
    ```
    $ pod install
    ```
    
- Initialize the HMS Core Analytics SDK.

    After the **agconnect-services.plist** file is imported successfully, initialize the Analytics SDK using the config API in AppDelegate.
    
    Swift sample code for initialization in **AppDelegate.swift**:
    
    ```swift
    import HiAnalytics
 
    @UIApplicationMain
    class AppDelegate: UIResponder, UIApplicationDelegate {
    ...
    func Application(_ Application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool  {
        // Override point for customization after Application launch.
        HiAnalytics.config();//Initialization
        return true
    }
    ...
    }
    ```

- Add all the files from @hmscore/react-native-hms-analytics/ios into Xcode target **Build Phases** by following below steps.

    **Step 1:** In Xcode, select your project, select your target, go to the **Build Phases** tab, and under the Compile Sources section click **+** icon, then select *Add Other*:

    <img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-analytics/example/.docs/ios/compile_sources_1.png"> 
   
    **Step 2:** Navigate into your **node_modules/hmscore/ios** and select all files.
    
    <img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-analytics/example/.docs/ios/compile_sources_2.png">
    
    At the end, your file should look like this:
    
    <img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-analytics/example/.docs/ios/compile_sources_3.png"> 


- All the React-Native HMS Analytics plugin implementations are written in **swift**. 

    Make sure your **Xcode target -> Swift Compiler - General tab** includes **Objective-C Bridging Header** and **Objective-C Generated Interface Header Name** like below:

    <img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-analytics/example/.docs/ios/_ObjC_Header.png"> 

---

## 3. API Reference

## Module Overview

| Module        | Description|
| ------------- | -------------------------------------------- |
| [HMSAnalytics](#hmsanalytics)  | HUAWEI Analytics Kit offers a rich array of preset analytics models that help you gain a deeper insight into your users, products, and content. With this insight, you can then take a data-driven approach to market your apps and optimize your products. |

## HMSAnalytics

### Public Method Summary

| Methods                                           | Return Type      | Definition                                                   |
| ------------------------------------------------- | ---------------- | ------------------------------------------------------------ |
| [setAnalyticsEnabled](#hmsanalyticssetanalyticsenabledenabled)       | Promise\<object> | This method is called to specifies whether to enable event collection If the function is disabled, no data is recorded. |
| [config](#hmsanalyticsconfig)                                 | Promise\<object> | This method is called to initialize in the main thread based on the configuration. |
| [getAAID](#hmsanalyticsgetaaid)                               | Promise\<string> | This method is called to obtain the app instance ID from AppGalleryConnect. |
| [onEvent](#hmsanalyticsoneventeventidbundle)                               | Promise\<object> | This method is called to record custom or predefined events. |
| [setUserId](#hmsanalyticssetuseriduserid)                           | Promise\<object> | This method is called to set user IDs.                       |
| [setUserProfile](#hmsanalyticssetuserprofilenamevalue)                 | Promise\<object> | This method is called to set user attributes.                |
| [getUserProfiles](#hmsanalyticsgetuserprofilespredefined)               | Promise\<object> | This method is called to obtain user attributes in the A/B test. |
| [enableLog](#hmsanalyticsenablelog)                           | Promise\<object> | This method is called to enable the HUAWEI Analytics Kit log function. |
| [enableLogWithLevel](#hmsanalyticsenablelogwithlevelloglevel)         | Promise\<object> | This method is called to enable debug logs and set the minimum log level (minimum level of log records that will be printed). |
| [setPushToken](#hmsanalyticssetpushtokentoken)                     | Promise\<object> | This method is called to set the push token, which can be obtained from HUAWEI Push Kit. |
| [setMinActivitySessions](#hmsanalyticssetminactivitysessionsinterval) | Promise\<object> | This method is called to set the minimum interval between two sessions. |
| [setSessionDuration](#hmsanalyticssetsessiondurationduration)         | Promise\<object> | This method is called to set the session timeout interval.   |
| [clearCachedData](#hmsanalyticsclearcacheddata)               | Promise\<object> | This method is called to delete all collected data cached locally, including cached data that failed to be sent. |
| [pageStart](#hmsanalyticspagestartpagenamepageclassoverride)                           | Promise\<object> | This method is called to customizes a page entry event. The API applies only to non-activity pages because automatic collection is supported for activity pages. |
| [pageEnd](#hmsanalyticspageendpagename)                               | Promise\<object> | This method is called to customizes a page end event. The API applies only to non-activity pages because automatic collection is supported for activity pages. |
| [setReportPolicies](#hmsanalyticssetreportpoliciesobject)           | Promise\<object> | This method is called to set data reporting policies.        |
| [enableLogger](#hmsanalyticsenablelogger)                   | Promise\<object> | This method enables HMSLogger capability which is used for sending usage analytics of AppLinking SDK's methods to improve the service quality. This method is only to support on Android Platform. |
| [disableLogger](#hmsanalyticsdisablelogger)                 | Promise\<object> | This method disables HMSLogger capability which is used for sending usage analytics of AppLinking SDK's methods to improve the service quality. This method is only to support on Android Platform. |

## Public Methods

### HmsAnalytics.setAnalyticsEnabled(enabled)

Specifies whether to enable event collection. If this function is disabled, all cached data is cleared and no data is recorded.

| Parameters |Type | Definition |
|----------------|---------------|-----------------------------|
|enabled| boolean |Indicates whether to enable event collection. The function is enabled by default. If enabled is true, enabled event collection.|

| Return | Definition |
|-------------------------------|-----------------------------|
|Promise\<object> | If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Specifies whether to enable event collection.
 * If the function is disabled, no data is recorded.
 */
HmsAnalytics.setAnalyticsEnabled(true)
    .then(result => console.log(result))
    .catch((err) => {
        alert("[setAnalyticsEnabled] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.config()

Analytics Kit will be initialized in the main thread based on the configuration.
- This method is specifically used by iOS Platforms.

| Return | Definition |
|-------------------------------|-----------------------------|
|Promise\<object> | If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Initializes Analytics Kit.
 * @note This function is specifically used by iOS Platforms.
 */
HmsAnalytics.config()
    .then(result => console.log(result))
    .catch((err) => {
        alert("[config] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.getAAID()

This method is called to obtain the app instance ID from AppGallery Connect.

| Return | Definition |
|----------------|---------------|
|Promise\<string>|Obtains the app instance ID from AppGallery Connect.|

Call Example

```js
/**
 * Obtains the app instance ID from AppGallery Connect.
 * @note For Android specific platforms 'HmsAnalytics.getAAID()', for IOS specific platforms 'HmsAnalytics.aaid()' is being called.
 */
HmsAnalytics.getAAID()
    .then(result => console.log(result))
    .catch((err) => {
        alert("[getAAID] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.onEvent(eventId,bundle)

This method is called to record events.

| Parameters | Type                        | Definition                                                   |
| ---------- | --------------------------- | ------------------------------------------------------------ |
| eventId    | Promise<[HAEventType](#haeventtype) \| string> | Indicates the ID of a customized event. The value cannot be empty and can contain a maximum of 256 characters, including digits, letters, and underscores (_). It cannot start with a digit or underscore. The value of this parameter cannot be an ID of an automatically collected event. |
| value      | Promise<[HAParamType](#haparamtype) \| string> | Indicates the information carried by the event. The number of built-in key-value pairs in the object cannot exceed 2048 and the size cannot exceed 200 KB. The key value in the object cannot contain spaces or invisible characters. |

| Return | Definition |
|----------------|---------------|
|Promise\<object>|If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Report custom events.
 */
HmsAnalytics.onEvent(eventId, bundle)
    .then(result => console.log(result))
    .catch((err) => {
        alert("[onEvent] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.setUserId(userId)

This method is called to set user IDs.

When the method is called, a new session is generated if the old value of userId is not empty and is different from the new value.If you do not want to use userId to identify a user (for example, when a user signs out), you must set userId to null.

Huawei Analytics uses ID of a user to associate user data.The use of userId must comply with related privacy regulations. You need to declare the use of such information in the privacy statement of your app.

| Parameters |Type | Definition |
|----------------|---------------|-----------------------------|
|userId| string|Indicates the user ID.This parameter cannot be empty and its value can contain a maximum of 256 characters.|
  
| Return | Definition |
|----------------|---------------|
|Promise\<object>|If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Set a user ID.
 * @important: When the setUserId API is called, if the old userId is not empty and is different from the new userId, a new session is generated.
 * If you do not want to use setUserId to identify a user (for example, when a user signs out), set userId to **null**.
 */
HmsAnalytics.setUserId(userId)
    .then(result => console.log(result))
    .catch((err) => {
        alert("[setUserId] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.setUserProfile(name,value)

This method is called to set user attributes.The values of user attributes remain unchanged throughout the app lifecycle and during each session.A maximum of 25 user attributes are supported. If the name of an attribute set later is the same as that of an existing attribute, the value of the existing attribute is updated.
  
| Parameters |Type | Definition |
|----------------|---------------|-----------------------------|
|key| string |Indicates the ID of a user attribute.The value cannot be empty and can contain a maximum of 256 characters, including digits, letters, and underscores (_). It cannot start with a digit or underscore.|
|value|string |Indicates the attribute value,which is a non-empty string containing up to 256 characters.|

| Return | Definition |
|----------------|---------------|
|Promise\<object>|If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * User attribute values remain unchanged throughout the app's lifecycle and session.
 * A maximum of 25 user attribute names are supported.
 * If an attribute name is duplicate with an existing one, the attribute names needs to be changed.
 */
HmsAnalytics.setUserProfile(name, value)
    .then(result => console.log(result))
    .catch((err) => {
        alert("[setUserProfile] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.getUserProfiles(preDefined)

This method is called to obtain predefined and custom user attributes in the A/B test.

| Parameters |Type | Definition |
|----------------|---------------|---------|
|predefined| boolean |Indicates whether to obtain predefined user attributes.***True:*** obtains predefined user attributes ***False:*** obtains developer-defined user attributes|

| Return | Definition |
|----------------|---------------|
| Promise\<[UserProfiles](#userprofiles)>| Predefined or custom user attributes. |

Call Example

```js
/**
 * Enables AB Testing. Predefined or custom user attributes are supported.
 * @note For Android specific platforms 'HmsAnalytics.getUserProfiles()', for IOS specific platforms 'HmsAnalytics.userProfiles()' is being called.
 */
HmsAnalytics.getUserProfiles(preDefined)
    .then(result => console.log(result))
    .catch((err) => {
        alert("[getUserProfiles] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.enableLog()

Enables the console log function of the SDK. The default level is DEBUG.
- This method is specifically used by Android Platforms.

| Return | Definition |
|-------------------------------|-----------------------------|
|Promise\<object> | If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Enables the log function.
 * @note This function is specifically used by Android Platforms.
 */
HmsAnalytics.enableLog()
    .then(result => console.log(result))
    .catch((err) => {
        alert("[enableLog] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.enableLogWithLevel(logLevel)

Enables the console log function of the SDK. The log level is passed.
- This method is specifically used by Android Platforms.

| Parameters |Type | Definition |
|----------------|---------------|-----------------------------|
|logLevel| [LogLevel](#loglevel) |Log level. The value can be ***DEBUG***, ***INFO***, ***WARN*** or ***ERROR***. The value is case insensitive.|

| Return | Definition |
|----------------|---------------|
|Promise\<object>| If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Enables the debug log function and sets the minimum log level.
 * @note This function is specifically used by Android Platforms.
 */
HmsAnalytics.enableLogWithLevel(logLevel)
    .then(result => console.log(result))
    .catch((err) => {
        alert("[enableLogWithLevel] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.setPushToken(token)

This method is called to set the push token. After obtaining a push token through HUAWEI Push Kit, use this method to save the push token so that you can use the audience defined by HUAWEI Analytics to create HCM notification tasks.
- This method is specifically used by Android Platforms.

| Parameters |Type | Definition |
|----------------|---------------|-----------------------------|
|token| string |Push token, which is a non-empty string containing up to 256 characters.|

| Return | Definition |
|----------------|---------------|
|Promise\<object>|If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Sets the push token, which is obtained using the Push Kit.
 * @note This function is specifically used by Android Platforms.
 */
HmsAnalytics.setPushToken(token)
    .then(result => console.log(result))
    .catch((err) => {
        alert("[setPushToken] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.setMinActivitySessions(interval)

This method is called to set the minimum interval for starting a new session. A new session is generated when an app is switched back to the foreground after it runs in the background for the specified minimum interval. The default value is 30 seconds.

| Parameters |Type | Definition |
|----------------|---------------|-----------------------------|
|interval| number |Indicates the minimum interval between two sessions.|

| Return | Definition |
|----------------|---------------|
|Promise\<object>|If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Sets the minimum interval for starting a new session.
 */
HmsAnalytics.setMinActivitySessions(interval)
    .then(result => console.log(result))
    .catch((err) => {
        alert("[setMinActivitySessions] Error/Exception: " + JSON.stringify(err));
    });
```
  
### HmsAnalytics.setSessionDuration(duration)

This method is called to set the session timeout interval. A new session is generated when an app keeps running in the foreground but the interval between two adjacent events exceeds the specified timeout interval. The default value is 30 minutes.

| Parameters |Type | Definition |
|----------------|---------------|---------|
|duration| number |Indicates the session timeout interval.|

| Return | Definition |
|----------------|---------------|
|Promise\<object>|If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Sets the session timeout interval.
 */
HmsAnalytics.setSessionDuration(duration)
    .then(result => console.log(result))
    .catch((err) => {
        alert("[setSessionDuration] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.clearCachedData()

This method is called to delete all collected data cached locally, including cached data that failed to be sent.

| Return | Definition |
|----------------|---------------|
|Promise\<object>|If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Delete all collected data in the local cache, including the cached data that fails to be sent.
 */
HmsAnalytics.clearCachedData()
    .then(result => console.log(result))
    .catch((err) => {
        alert("[clearCachedData] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.pageStart(pageName,pageClassOverride)

This method is called to customizes a page entry event. The metod applies only to non-activity pages because automatic collection is supported for activity pages. If this API is called for an activity page, statistics on page entry and exit events will be inaccurate.After this API is called, the [pageEnd()](#pageend) method must be called.
- This method is specifically used by Android Platforms.

| Parameters |Type | Definition |
|----------------|---------------|---------|
|pageName| string |Name of the current page, a string containing a maximum of 256 characters.|
|pageClassOverride| string |Class name of the current page, a string containing a maximum of 256 characters.|

| Return | Definition |
|----------------|---------------|
|Promise\<object>|If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Defines a custom page entry event.
 * @note This function is specifically used by Android Platforms.
 */
HmsAnalytics.pageStart(pageName, pageClassOverride)
    .then(result => console.log(result))
    .catch((err) => {
        alert("[pageStart] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.pageEnd(pageName)

This method is called to customizes a page end event. The method applies only to non-activity pages because automatic collection is supported for activity pages. If this API is called for an activity page, statistics on page entry and exit events will be inaccurate. Before this method is called, the [pageStart()](#pagestart) function must be called.
- This method is specifically used by Android Platforms.

| Parameters |Type | Definition |
|----------------|---------------|---------|
|pageName| string |Indicates the name of the current activity screen, which is mandatory.This parameter cannot be empty and its value can contain a maximum of 256 characters. It must be the same as the value of pageName passed in pageStart().|

| Return | Definition |
|----------------|---------------|
|Promise\<object>|If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
/**
 * Defines a custom page exit event.
 * @note This function is specifically used by Android Platforms.
 */
HmsAnalytics.pageEnd(pageName)
    .then(result => console.log(result))
    .catch((err) => {
        alert("[pageEnd] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.setReportPolicies(Object[])

Sets data reporting policies.
- This method is specifically used by IOS Platforms.

| Parameters |Type | Definition |
|----------------|---------------|---------|
|[reportPolicyType]| [HAReportPolicy](#hareportpolicy) |Sets the policy of reporting type.|

| Return | Definition |
|----------------|---------------|
|Promise\<object>|If the operation is successful, promise will resolve successfully. Otherwise it throws an error.|

Call Example

```js
export const isAndroid = Platform.OS === 'android';

/**
 * Sets data reporting policies.
 * @note This function is specifically used by IOS Platforms.
 */
async setReportPolicies() {
    if(isAndroid) { return }
    console.log("Calling setReportPolicies")

    try {
        let result = await Utils.setReportPolicies([{"reportPolicyType": HmsAnalytics.onAppLaunchPolicy},
            {"reportPolicyType": HmsAnalytics.onScheduledTimePolicy, "seconds": 200},
            {"reportPolicyType": HmsAnalytics.onMoveBackgroundPolicy},
            {"reportPolicyType": HmsAnalytics.onCacheThresholdPolicy, "threshold": 200}])
        this.createCustomView("setReportPolicies :  ", JSON.stringify(result) + "")
        console.log(result);
    } catch (e) {
        alert(JSON.stringify(e))
        console.log('ERR: config ' + e)
    }
}

```

### HmsAnalytics.enableLogger()

This method enables HMSLogger capability in Android Platforms which is used for sending usage analytics of Analytics SDK's methods to improve the service quality.

Call Example

```js
HmsAnalytics.enableLogger()
    .then(result => console.log(result))
    .catch((err) => {
        alert("[enableLogger] Error/Exception: " + JSON.stringify(err));
    });
```

### HmsAnalytics.disableLogger()

This method disables HMSLogger capability in Android Platforms which is used for sending usage analytics of Analytics SDK's methods to improve the service quality.

Call Example

```js
HmsAnalytics.disableLogger()
    .then(result => console.log(result))
    .catch((err) => {
        alert("[disableLogger] Error/Exception: " + JSON.stringify(err));
    });
```

## Data Types

### LogLevel
| Field Name |Type | Definition |
|----------------|---------------|---------|
|values |string | The value can be DEBUG, INFO, WARN, or ERROR.|

### EventParams

| Field Name |Type | Definition |
|----------------|---------------|---------|
|key|string|Event param key.|
|value|string| Event param value.|

### UserProfiles

| Field Name |Type | Definition |
|----------------|---------------|---------|
|key|string|Predefined user attribute key.|
|value|string| Predefined user attribute value.|

### HAReportPolicy

| Field Name |Type | Description |
|----------------|---------------|---------|
|onScheduledTimePolicy?|number|Sets the policy of reporting data at the scheduled interval. You can configure the interval as needed. The value ranges from 60 to 1800, in seconds.|
|onAppLaunchPolicy?|boolean|Sets the policy of reporting data upon app launch.|
|onMoveBackgroundPolicy?|boolean|Sets the policy of reporting data when the app moves to the background. This policy is enabled by default.|
|onCacheThresholdPolicy?|number|Sets the policy of reporting data when the specified threshold is reached. This policy is enabled by default. You can configure the interval as needed. The value ranges from 30 to 1000. The default value is 200.|

## Constants

| Name | Definition |
|-------------------------------|-----------------------------|
|[HMSAnalytics.HAParamType](#haparamtype)| provides the IDs of all predefined parameters, including the ID constants of predefined parameters and user attributes.|
|[HMSAnalytics.HAEventType](#haeventtype) | provides the ID constants of all predefined events.|

### HAEventType

| Constant Fields |Type | Value |
|----------------|---------------|---------|
|CREATEPAYMENTINFO|string|Event reported when a user has added payment information but has not initiated payment during checkout. It can be used with STARTCHECKOUT and COMPLETEPURCHASE to construct funnel analysis for the checkout process.|
|ADDPRODUCT2CART|string| Event reported when a user adds a product to the shopping cart. It can be used with VIEWPRODUCT and STARTCHECKOUT to construct funnel analysis for product purchase. It can also be used to analyze products that users are interested in, helping you promote the products to the users.|
|ADDPRODUCT2WISHLIST|string| Event reported when a user adds a product to favorites. It can be used to analyze products that users are interested in, helping you promote the products to the users.|
|STARTAPP| string |Event reported when a user launches the app.|
|STARTCHECKOUT|string|Event reported when a user clicks the checkout button after placing an order. It can be used with VIEWPRODUCT and COMPLETEPURCHASE to construct funnel analysis for the e-commerce purchase conversion rate.|
|VIEWCAMPAIGN|string|Event reported when a user view details of a marketing campaign. It can be used to analyze the conversion rate of a marketing campaign.|
|VIEWCHECKOUTSTEP|string|Event reported when a user views steps of the settlement process.|
|WINVIRTUALCOIN|string|Event reported when a user obtains virtual currency. It can be used to analyze the difficulty for users to obtain virtual currency, which helps you optimize the product design.|
|COMPLETEPURCHASE|string|This event is reported after a user purchases a product. It can be used to analyze the products or contents that users are more interested in, which helps you optimize the operations policy.|
|OBTAINLEADS|string|Event reported when a user joins in a group, for example, joining in a group chart in a social app. It can be used to evaluate the attractiveness of your product's social features to users.|
|COMPLETELEVEL|string|Event reported when a user completes a game level in a game app. It can be used with STARTLEVEL to analyze whether the game level design is proper and make targeted optimization policies.|
|STARTLEVEL|string|Event reported when a user starts a game level in a game app. It can be used together with COMPLETELEVEL to analyze whether the game level design is proper and make targeted optimization policies.|
|UPGRADELEVEL|string|Event reported when a user levels up in a game app. It can be used to analyze whether your product's user level design is optimal and make targeted optimization policies.|
|SIGNIN|string|Event reported when a user signs in to an app requiring sign-in. It can be used to analyze users' sign-in habits and make targeted operations policies.|
|SIGNOUT|string|Event reported when a user signs out.|
|SUBMITSCORE|string|Event reported when a user submits the score in a game or education app. It can be used to analyze the difficulty of product content and make targeted optimization policies.|
|CREATEORDER|string|Event reported when a user creates an order.|
|REFUNDORDER|string|Event reported when the refund is successful for a user. It can be used to analyze loss caused by the refund and make targeted optimization policies.|
|DELPRODUCTFROMCART|string|Event reported when a user removes a product from the shopping cart. It can be used for targeted marketing to the user.|
|SEARCH|string|Event reported when a user searches for content in an app. It can be used with events such as VIEWSEARCHRESULT and VIEWPRODUCT to analyze the accuracy of search results.|
|VIEWCONTENT| string| Event reported when a user touches a content, for example, touching a product in the product list in an e-commerce app to view the product details. It can be used to analyze the products that users are interested in.|
|UPDATECHECKOUTOPTION|string|Event reported when a user sets some checkout options during checkout. It can be used to analyze checkout preferences of users.|
|SHARECONTENT|string|Event reported when a user shares a product or content through a social channel. It can be used to analyze users' loyalty to the product.|
|REGISTERACCOUNT|string|Event reported when a user registers an account. It can be used to analyze the user sources and optimize operations policies.|
|CONSUMEVIRTUALCOIN|string|Event reported when a user consumes virtual currency. It can be used to analyze the products that users are interested in.|
|STARTTUTORIAL|string|Event reported when a user starts to use the tutorial. It can be used with COMPLETETUTORIAL to evaluate the tutorial quality and formulate targeted optimization policies.|
|COMPLETETUTORIAL|string|Event reported when a user completes the tutorial. It can be used with STARTTUTORIAL to evaluate the tutorial quality and formulate targeted optimization policies.|
|OBTAINACHIEVEMENT|string|Event reported when a user unlocks an achievement. It can be used to analyze whether the achievement level design is optimal and make targeted optimization policies.|
|VIEWPRODUCT|string|Event reported when a user browses a product. It can be used to analyze the products that users are interested in, or used with other events for funnel analysis.|
|VIEWPRODUCTLIST|string|Event reported when a user browses a list of products, for example, browsing the list of products by category. It can be used to analyze the types of contents that users are more interested in.|
|VIEWSEARCHRESULT|string|Event reported when a user views the search results. It can be used with VIEWPRODUCT and SEARCH to measure the accuracy of the search algorithm.|
|UPDATEMEMBERSHIPLEVEL|string|Event reported when a user purchases membership or signs in for the first time after membership expires. It can be used to analyze user habits.|
|FILTRATEPRODUCT|string|Event reported when a user sets conditions to filter products displayed. It can be used to analyze user preferences.|
|VIEWCATEGORY|string|Event reported when a user taps a product category. It can be used to analyze popular product categories or user preferences.|
|UPDATEORDER|string|Event reported when a user modifies an order.|
|CANCELORDER|string|Event reported when a user cancels an order.|
|COMPLETEORDER|string|Event reported when a user confirms the reception.|
|CANCELCHECKOUT|string|Event reported when a user cancels an ongoing payment of a submitted order. It can be used to analyze the cause of user churn.|
|OBTAINVOUCHER|string|Event reported when a user claims a voucher.|
|CONTACTCUSTOMSERVICE|string|Event reported when a user contacts customer service personnel to query product details or make a complaint.|
|RATE|string|Event reported when a user comments on an app, service, or product.|
|INVITE|string|Event reported when a user invites other users to use the app through the social channel.|

### HAParamType

| Constant Fields |Type | Value |
|----------------|---------------|---------|
|STORENAME|string|Indicates the store or organization where a transaction occurred.|
|BRAND|string|Indicates the item brand.|
|CATEGORY|string|Indicates the item category.|
|PRODUCTID|string|Indicates the item ID.|
|PRODUCTNAME|string|Indicates the item name.|
|PRODUCTFEATURE|string|Indicates the item variant.|
|PRICE|string|Indicates the purchase price.|
|QUANTITY|string|Indicates the purchase quantity.|
|REVENUE|string|Indicates the context-specific value that is automatically accumulated for each event type.|
|CURRNAME|string|Indicates the currency type of the revenue, which is used together with $Revenue.|
|PLACEID|string|Indicates the item's location ID.|
|DESTINATION|string|Indicates the flight or travel destination.|
|ENDDATE|string|Indicates the project end date, checkout date, or lease end date.|
|BOOKINGDAYS|string|Indicates the number of days that a user can stay in the case of hotel reservation.|
|PASSENGERSnumber|string|Indicates the number of customers in the case of hotel reservation.|
|BOOKINGROOMS|string|Indicates the number of rooms reserved by a user in the case of hotel reservation.|
|ORIGINATINGPLACE|string|Indicates the departure location.|
|BEGINDATE|string|Indicates the departure date, hotel check-in date, or lease start date.|
|TRANSACTIONID|string|Indicates the e-commerce transaction ID.|
|CLASS|string|Indicates the level of a room reserved by a user in the case of hotel reservation.|
|CLICKID|string|Indicates the ID generated by the ad network and used to record ad clicks.|
|PROMOTIONNAME|string|Indicates the name of a marketing activity.|
|CONTENT|string|Indicates the content of a marketing activity.|
|EXTENDPARAM|string|Indicates a customized parameter.|
|MATERIALNAME|string|Indicates the name of the creative material used in a marketing activity.|
|MATERIALSLOT|string|Indicates the location where the creative material is displayed.|
|MEDIUM|string|Indicates the media where the campaign occurred, for example, CPC and email.|
|SOURCE|string|Indicates the source of a marketing activity provider, for example, Huawei PPS.|
|KEYWORDS|string|Indicates the search string or keyword.|
|OPTION|string|Indicates the checkout option entered by a user in the current settlement step.|
|STEP|string|Indicates the step where a user is currently in during the settlement process.|
|VIRTUALCURRNAME|string|Indicates the virtual currency type.|
|VOUCHER|string|Indicates the coupons used by a user in this transaction.|
|PLACE|string|Indicates the item’s location ID.|
|SHIPPING|string|Indicates the transportation fee generated in this transaction.|
|TAXFEE|string|Indicates the tax entailed in this transaction.|
|USERGROUPID|string|Indicates the ID of a group that a user joins.|
|LEVELNAME|string|Indicates the name of a game level.|
|RESULT|string|Indicates the operation result.|
|ROLENAME|string|Indicates the role of a user.|
|LEVELID|string|Indicates the name of a game level.|
|CHANNEL|string|Indicates the channel through which a user signs in.|
|SCORE|string|Indicates the score in a game.|
|SEARCHKEYWORDS|string|Indicates the search string or keyword.|
|CONTENTTYPE|string|Indicates the content type selected by a user.|
|ACHIEVEMENTID|string|Indicates the achievement ID.|
|FLIGHTNO|string|Indicates the flight number generated by the transaction system.|
|POSITIONID|string|Indicates the index of an item in the list.|
|PRODUCTLIST|string|Indicates the item list displayed to a user.|
|ACOUNTTYPE|string|Account type of a user, for example, email or mobile number.|
|OCCURREDTIME|string|Time when an account is registered.|
|EVTRESULT|string|Sign-in result.|
|PREVLEVEL|string|Level before the change.|
|CURRVLEVEL|string|Current level.|
|VOUCHERS|string|Names of vouchers applicable to a product.|
|MATERIALSLOTTYPE|string|Type of the slot where a creative material is displayed, for example, the ad slot, operations slot, or banner.|
|LISTID|string|Product ID list.|
|FILTERS|string|Filter condition.|
|SORTS|string|Sorting condition.|
|ORDERID|string|Order ID generated by your transaction system.|
|PAYTYPE|string|Payment mode selected by a user.|
|REASON|string|Change reason.|
|EXPIREDATE|string|Expiration time of a voucher.|
|VOUCHERTYPE|string|Voucher type.|
|SERVICETYPE|string|Type of a service provided for a user, for example, consultation or complaint.|
|DETAILS|string|Details of user evaluation on an object.|
|COMMENTTYPE|string|Evaluated object.|
|REGISTMETHOD|string|User source.|

---

## 4. Configuration and Description

- [Using the Debug Mode for Android and iOS platforms](https://developer.huawei.com/consumer/en/doc/development/HMS-Plugin-Guides/enabling-debug-mode-0000001050132798)
- [Enable HUAWEI Analytics](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/android-config-agc-0000001050163815)

---

## 5. Sample Project

This plugin includes a demo project in the [example](example) folder, there you can find more usage examples.

<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-analytics/example/.docs/ios/_1.png" width = 30% height = 30% style="margin:1em"> <img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-analytics/example/.docs/android/_1.png" width = 30% height = 30% style="margin:1em"> 

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
