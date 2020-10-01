## Contents
- **1. Introduction**
- **2. Installation Guide**
- **3. HUAWEI DTM Plugin for React Native**
- **4. Configuration Description**
- **5. Licensing and Terms**

## 1. Introduction

The React-Native Plugin code encapsulates the Huawei location client interface. It provides many APIs for your reference or use.

The React-Native Plugin code package is described as follows:

- *Android*     : core layer, bridging Location Plugin bottom-layer code;
- *index.js*    : external interface definition layer, which is used to define interface classes or reference files.

HUAWEI Dynamic Tag Manager (DTM) is a dynamic tag management system. With DTM, it allows you to dynamically update its tags in a web-based user interface to track specific events and report them to third-party analytics platforms, as well as tracking types of marketing activities.

## 2. Installation Guide

Before using Reactive-Native DTM Plugin code, ensure that the RN development environment has been installed and completed. Also you need to have Huawei developer account to use Huawei Mobile Services and thus the HMS DTM Plugin. You can sign in/register to Huawei developer website from [here](https://developer.huawei.com/consumer/en/console).

### 2.1 Configuring App Information in AppGallery Connect

#### 2.1.1 Registering as a Developer
Before you get started, you must register as a Huawei developer and complete identity verification on [HUAWEI Developers](https://developer.huawei.com/consumer/en/). For details, please refer to [Registration and Verification](https://developer.huawei.com/consumer/en/doc/start/10104).


#### 2.1.2 Generating a Signing Certificate Fingerprint
The signing certificate fingerprint is used to verify the authenticity of an app. Before releasing an app, you must locally generate a signing certificate fingerprint and configure it in AppGallery Connect.

Before you do this, make sure that the following conditions are met:

You have created the app's signature file. For details, please refer to [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#3).
The [JDK](https://www.oracle.com/java/technologies/javase-downloads.html) has been installed on your PC.
To generate a signing certificate fingerprint, perform the following steps:

- Open the command line tool using the **cmd** command, and run the **cd** command to go to the directory where **keytool.exe** is located. In the following example, the Java program is installed in the **Program** Files folder in drive C.

```bash
   C:\>cd C:\Program Files\Java\jdk1.8.0_221\bin
   C:\Program Files\Java\jdk1.8.0_221>
```
- Run **keytool -list -v -keystore** <keystore-file> and respond as prompted. In the preceding command, **<keystore-file>** indicates the absolute path to the app's signature file.
Example:

```bash
    keytool -list -v -keystore C:\TestApp.jks
```
- Record the SHA-256 certificate fingerprint


#### 2.1.3 Configuring the Signing Certificate Fingerprint

- Sign in to AppGallery Connect and click My projects.
- Find your app project, and click the desired app name.
- Go to **Project Setting** > **General information**. In the **App information** area, click the  icon next to **SHA-256 certificate fingerprint**, and enter the obtained SHA-256 certificate fingerprint.


### 2.2 Enabling HUAWEI Analytics
The Analytics service uses capabilities of HUAWEI Analytics Kit to report analytics events. 
For details, please refer to [Service Enabling](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/service-enabling-0000001050745155).


### 2.3 Adding the AppGallery Connect Configuration File of Your App

- Sign in to AppGallery Connect and click **My projects**.
- Find your app project and click the app that needs to integrate the HMS Core SDK.
- Go to **Project Setting** > **General information**. In the **App information** area, download the **agconnect-services.json** file.
- Copy the **agconnect-services.json** file to the app's root directory of your Android Studio project.

-  ___ ***NOTE*** ___
    - *Before obtaining the **agconnect-services.json** file, ensure that you have enabled the HUAWEI Analytics service. For details, please refer to [Enabling HUAWEI Analytics Kit](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides/android-config-agc-0000001050163815-V5#EN-US_TOPIC_0000001050163815__li1031751692618).*
    - *If you have performed any modification such as setting the data storage location and enabling or managing some APIs, you need to download the latest **agconnect-services.json** file and use the file to replace the existing one in the **app** directory of your Android Studio project.*


### 2.4 Using the Debug Mode
During development, you can enable the debug mode to view the event records in real time, observe the results, and adjust the event tracking scheme as needed.

- **Huawei Analytics**
- To enable or disable the debug mode, perform the following steps:
    - Run the following command on an Android device to enable the debug mode:
    ```bash
        adb shell setprop debug.huawei.hms.analytics.app <package_name>
    ```
    - After the debug mode is enabled, all events will be reported in real time.
    - Run the following command to disable the debug mode:
    ```bash
        adb shell setprop debug.huawei.hms.analytics.app .none.
    ```
- **Third-Party Platforms**
- If you use a third-party template (such as a template of Adjust or AppsFlyer), run the following 

    - command to enable the debug mode:
    ```bash
        adb shell setprop debug.huawei.hms.dtm.app <package_name>
    ```    
    - Run the following command to disable the debug mode:
    ```bash
        adb shell setprop debug.huawei.hms.dtm.app .none.
    ```

### 2.5 Integration of Third Party Analytcis Platforms

Google Analytics => [Get started with Google Analytics](https://firebase.google.com/docs/analytics/get-started?platform=android)
Apps Flyer => [Get started with Apps Flyer](https://support.appsflyer.com/hc/en-us/articles/207033486-Getting-started-step-by-step#basic-attribution)

### 2.6 Operation To Server

- **1. Create A Custom Tag**
    - **Step 1)** Create a Variable (Parameter Event)
        - Name: app_type
        - Type: Event parameter
        - Event parameter key: type

    - **Step 2)** Create a Condition (Custom)
        - Name: type_condition
        - Type: Custom
        - Trigger: Some events
        - Variable: app_type, Operator: Equals, Value:big

    - **Step 3)** Create a Tag (Custom Function)
        - Name: CustomTag
        - Extension: Custom function
        - Class path: com.huawei.hms.rn.dtm.interfaces.CustomTag
        - Variable: Key: app_type, Value:big

    - ___ ***NOTE*** ___  *classpath=> your ICustomTag interface class path*

- **2. Trigger Function Call**

    - **Step 1)** Create a variable (Event Parameter)
        - Name: app_price
        - Type: Event parameter
        - Event parameter key: price

    - **Step 2)** Create a variable (Function Call)
        - Name: CustomVariable
        - Type: Function call
        - Class Path: com.huawei.hms.rn.dtm.interfaces.CustomVariable
        - Parameter: key:price , value:{{app_price}}

    - **Step 3)** Create a condition (Custom)
        - Name: PurchaseEvent
        - Type: Custom
        - Trigger: Some events
        - Variable: EventName, Operator :Equals, Value:Purchase
        - Variable: app_price, Operator: Equals, Value:40
        - Variable: CustomVariable, Operator: Equals, Value:40

    - **Step 4)** Create a tag (Huawei Analytics)
        - Name: HiTest
        - Extension: HUAWEI Analytics
        - Operation: Add Event
        - Event Name: HiPurchase
        - Parameters for addition or modification=> Key: HiPrice, Value:{{app_price}}
        - Parameters for addition or modification=> Key: HiPriceCall, Value:{{CustomVariable}}
        - Parameters for addition or modification=> Key: HiCurrency, Value:{{app_currency}}
        - Parameters for addition or modification=> Key: Puchase{{Event Name}}, Value:Purchase

    - **Step 5)** Create a group
        - Variable (Function call): CustomVariable
        - Variable (Event Parameter): app_price
        - Variable (Event Parameter): app_currency
        - Condition: PurchaseEvent
        - Tag: HiTest

    - **Step 6)** Create a version


- **3. Add a Tag for The AppsFlyer Platform**

    - **Step 1)** Create a Condition (Custom)
        - Name: eventNameContainsDTM
        - Type: Custom
        - Trigger: Some events
        - Variable: Event Name, Operator: Contains, Value:DTM_BOOK

    - **Step 2)** Create a Tag (AppsFlyer)
        - Name: AppsFlyerTag
        - Extension: AppsFlyer
        - Class path: {{AppsFlyer>your app>App Settings>app name}} (exp=>com.huawei.hms.dtm.rn.demo-AppGallery)
        - Developer Key: {{AppsFlyer>your app>App Settings>SDK Installation>Dev Key}} (exp=>zByAiBQUStyvpD8e9fNk2m)
        - AppsFlyer device ID: {{Device ID}}
        - Event name: {{Event Name}}
        - Advertising ID: false (true or false)
        - Event time: Add event trigger time (true)
        - Event currency: USD
        - Event parameters: Key:af_revenue Value:3000
        - Trigger Condition: eventNameContainsDTM

- **4. Add a Tag for The Google Analytics(Firebase) Platform**

    - **Step 1)** Create a Variable (Parameter Event)
        - Name: app_price
        - Type: Event parameter
        - Event parameter key: 120

    - **Step 2)** Create a Condition (Custom)
        - Name: min_condition
        - Type: Custom
        - Trigger: Some events
        - Variable: app_price, Operator: Equals, Value:120

    - **Step 3)** Create a Tag (Google Analytics (Firebase))
        - Name: FireBaseTest
        - Extension: Google Analytics (Firebase)
        - Event name: Rename Event false
        - Parameters for addition: Key: isHuawei Value:true
        - Trigger Condition: min_condition

    - **Step 5)** Create a version
        - Click **Create version** on the **Overview** page or click **Create** on the **Version** page.
        - On the **Create version** page that is displayed, enter the version name and description, and click **OK**.
        - View the created version on the **Version** page.
        - On the **Version** details page that is displayed, view the overview, operation records, variables, conditions, and tags of the version. 
        - Click **Release**
        - Click **Download**
        - Put the downloaded config file under **your app**> **src**> **main**> **assets**>**containers** folder.



### 2.7 Run Demo Project

In order to be able to use library in the demo, the library should be copied under the **node_modules** folder of the demo project.

### 2.7.1 Install Dependencies

- Go to example directory
```bash
    cd example/
```

- Install npm packages
```bash
    npm install
```

### 2.7.2 Copy Library

Run **react-native-hms-dtm**
```bash
    npm run react-native-hms-dtm
```

After copy operation, the structure should be like this

- example
    - |_ node_modules
        - |_ ...
        - |_ react-native-hms-dtm
        - |_ ...


## 3. HUAWEI DTM Plugin for React Native
### Summary

  | Return Type        | Function                                    | Description                                     |  
  |--------------------|---------------------------------------------|-------------------------------------------------|
  |  Promise(String)   | onEvent(eventType, bundle)                  | Reports an event.                               |
  |  Promise(String)   | customFunction(eventType, paramArray)       | It is a special case of the onEvent method.     |
  |                    |                                             | Used to trigger CustomVariables and CustomTags. |
  |                    |                                             | It is preferred for performing operations on    |
  |                    |                                             | Function Call and Custom Tags.                  | 
  | Promise(boolean)   | enableLogger                                | Enables HMS Plugin Method enabled.              |
  | Promise(boolean)   | disableLogger                               | Enables HMS Plugin Method disabled              |

### Public Methods

#### onEvent(String eventId, ReadableMap map)
- This API is called to record events.

| Parameters       | Description                                                         |
|------------------|---------------------------------------------------------------------|
| eventId          | ID of a customized event. The value cannot be empty and can contain |
|                  | a maximum of 256 characters, including digits, letters,             |
|                  | and underscores (_). It cannot start with a digit or underscore.    |
|                  | The value of this parameter cannot be an ID of an automatically     | 
|                  | collected event.                                                    |
| map              | Information carried by the event. The size of the data in the "map" |
|                  | cannot exceed 200 KB                                                |


For more information, refer to the Analytics [onEvent](https://developer.huawei.com/consumer/en/doc/development/HMS-Plugin-Guides-V1/recording-events-0000001052746055-V1) function description.


#### customFunction(String eventId, ReadableArray array)
- This API is called to record events.

| Parameters       | Description                                                         |
|------------------|---------------------------------------------------------------------|
| eventId          | ID of a customized event. The value cannot be empty and can contain |
|                  | a maximum of 256 characters, including digits, letters,             |
|                  | and underscores (_). It cannot start with a digit or underscore.    |
|                  | The value of this parameter cannot be an ID of an automatically     | 
|                  | collected event.                                                    |
| array            | Information carried by the event.The size of the data in the "array"|
|                  | cannot exceed 200 KB                                                |

#### enableLogger()

This method enables HMSLogger capability which is used for sending usage analytics of DTM SDK's methods to improve the service quality.

#### disableLogger() 

This method disables HMSLogger capability which is used for sending usage analytics of DTM SDK's methods to improve the service quality.   
                                                  
## 4. Configuration Description
   No.

## 5. Licensing and Terms  
    Apache 2.0 license.