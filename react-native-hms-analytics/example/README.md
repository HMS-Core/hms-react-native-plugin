# React-Native HMS Analytics - Demo

---

## Contents

- [Introduction](#1-introduction)
- [Installation](#2-installation)
- [Configuration](#3-configuration)
- [Licensing and Terms](#4-licensing-and-terms)

---

## 1. Introduction

This demo project is an example to demonstrate the features of the **Huawei React-Native Analytics Kit** Plugin.

---

## 2. Installation

Before you get started, you must register as a HUAWEI developer and complete identity verification on the [HUAWEI Developer](https://developer.huawei.com/consumer/en/) website. For details, please refer to [Register a HUAWEI ID](https://developer.huawei.com/consumer/en/doc/10104).

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

A signing certificate fingerprint is used to verify the authenticity of an app when it attempts to access an HMS Core (APK) through the HMS SDK. Before using the HMS Core (APK), you must locally generate a signing certificate fingerprint and configure it in the **AppGallery Connect**. You can refer to 3rd and 4th steps of [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#2) codelab tutorial for the certificate generation. Perform the following steps after you have generated the certificate.

**Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and select your project from **My Projects**. Then go to **Project Setting** > **General information**. In the **App information** field, click the  icon next to SHA-256 certificate fingerprint, and enter the obtained **SHA-256 certificate fingerprint**.

**Step 2:**  After completing the configuration, click **OK** to save the changes. (Check mark icon)

**Step 3:** Enter the properties of the key you generated to the **build.gradle** file located on **example/android/app/build.gradle**.
```
 signingConfigs {
        config {
            storeFile file('<keystore_file>')
            keyAlias '<key_alias>'
            keyPassword '<key_password>'
            storePassword '<keystore_password>'
        }
    }
```

#### Integrating the React-Native HMS Anaytics into the Android Studio

**Step 1:** Set an unique **Application ID** on the app level build gradle file located on **example/android/app/build.gradle**. You should also change the **package names** for the manifest files in the **/example/android/app/src/** directory to match with the Application ID. 
  ```gradle
  <!-- Other configurations ... -->
    defaultConfig {
      // Specify your own unique Application ID (https://developer.android.com/studio/build/application-id.html). You may need to change the package name on AndroidManifest.xml and MainActivity.java respectively.
      // The Application ID here should match with the Package Name on the AppGalleryConnect
      applicationId "<Enter_Your_Package_Here>" // For ex: "com.example.analytics"
      <!-- Other configurations ... -->
  }
  ```
**Step 2:** Copy the **agconnect-services.json** file to the app's root directory of your project.
    
**Package name must match with the _package_name_ entry in _agconnect-services.json_ file.**

---

#### iOS App Development

#### Integrating the React-Native HMS Anaytics into the Xcode Project

- Add the AppGallery Connect configuration file of the app to your Xcode project.

    **Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and click **My projects**.
    
    **Step 2:** Find your **app** project and click the app that needs to integrate the HMS Core SDK.
    
    **Step 3:** Go to **Project Setting > App information**. In the **App information** area, download the **agconnect-services.plist** file.
    
    **Step 4:** Copy the **agconnect-services.plist** file to the app's root directory of your Xcode project.
    
    Before obtaining the **agconnect-services.plist** file, ensure that you have enabled HUAWEI Analytics. For details, please refer to [Enabling HUAWEI Analytics](#enabling-analytics-service).
    
    If you have made any changes on the Project Setting page, such as setting the data storage location and enabling or managing APIs, you need to download the latest **agconnect-services.plist** file and replace the existing file in the app's root directory.

- Navigate into example/ios and run 
    
    ```
    $ cd ios && pod install
    ```
    
- Initialize the HMS Core Analytics SDK.

    After the **agconnect-services.plist** file is imported successfully, add the **agconnect-services.plist** file under example/ios folder. 



### Build & Run the project

#### Android

Run the following command to start the demo app.
```
[project_path]> npm run android
```

#### iOS

Run the following command to start the demo app.
```
[project_path]> npm run ios
```

---

## 3. Configuration

- [Using the Debug Mode for Android and iOS platforms](https://developer.huawei.com/consumer/en/doc/development/HMS-Plugin-Guides/enabling-debug-mode-0000001050132798)
- [Enable HUAWEI Analytics](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/android-config-agc-0000001050163815)

---

## 4. Licensing and Terms

Huawei React-Native HMS Analytics - Demo is licensed under [Apache 2.0 license](LICENCE)







