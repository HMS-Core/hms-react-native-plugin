# React-Native HMS Push

---

## Contents

  - [1. Introduction](#1-introduction)
  - [2. Installation Guide](#2-installation-guide)
    - [Creating a Project in App Gallery Connect](#creating-a-project-in-appgallery-connect)
    - [Configuring the Signing Certificate Fingerprint](#configuring-the-signing-certificate-fingerprint)
    - [Integrating the React-Native Push Plugin](#integrating-react-native-push-plugin)
  - [3. API Reference](#3-api-reference)
    - [HmsPushInstanceId](#hmspushinstanceid)
    - [HmsPushMessaging](#hmspushmessaging)
    - [HmsPushOpenDevice](#hmspushopendevice)
    - [HmsLocalNotification](#hmslocalnotification)
    - [RemoteMessageBuilder](#remotemessagebuilder)
    - [RNRemoteMessage](#rnremotemessage)
    - [HmsPushEvent](#hmspushevent)
    - [HmsPushResultCode](#hmspushresultcode)
  - [4. Configuration and Description](#4-configuration-and-description)
    - [Receiving Data Messages at Background/Killed Application State](#receiving-data-messages-at-backgroundkilled-application-state)
    - [Receiving Custom Intent URI](#receiving-custom-intent-uri)
    - [Auto-Initialization](#auto-initialization)
    - [Local Notification Configurations](#local-notification-configurations)
  - [5. Sample Project](#5-sample-project)
  - [6. Questions or Issues](#6-questions-or-issues)
  - [7. Licensing and Terms](#7-licensing-and-terms)

---

## 1. Introduction

This module enables communication between **Huawei Push SDK** and React Native platform. It exposes all functionality provided by Huawei Push SDK.

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

- To use HUAWEI Push, you need to enable the Push service. For details, please refer to [Enabling Services](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-enable_service#h1-1574822945685).


### Configuring the Signing Certificate Fingerprint

**Step 1.** Go to **Project Setting > General information**. In the **App information** field, click the icon next to SHA-256 certificate fingerprint, and enter the obtained **SHA256 certificate fingerprint**.

**Step 2.** After completing the configuration, click check mark.

### Integrating React Native Push Plugin

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

- Set your package name in **defaultConfig** > **applicationId** and set **minSdkVersion** to **17** or **higher**.

- Package name must match with the **package_name** entry in **agconnect-services.json** file.

```groovy
defaultConfig {
  applicationId "<package_name>"
  minSdkVersion 17
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
npm i @hmscore/react-native-hms-push
```

**Step 2:**  Run your project. 

- Run the following command to the project directory.

```bash
react-native run-android  
```

#### Option 2: Download Link

To integrate the plugin, follow the steps below:

**Step 1:** Download the React Native Push Plugin and place **react-native-hms-push** under **node_modules/@hmscore** of your React Native project, as shown in the directory tree below.

```
demo-app
  |_ node_modules
    |_ @hmscore
      |_ react-native-hms-push
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
    implementation project(":react-native-hms-push")    
    ...    
  }
}
```

**Step 3:** Add the following lines to the **android/settings.gradle** file in your project:

```groovy
include ':app'
include ':react-native-hms-push'
project(':react-native-hms-push').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-push/android')
```

**Step 4:**  Import the following classes to the **MainApplication.java** file of your project.

```java
import com.huawei.hms.rn.push.HmsPushPackage
```

Then, add the **HmsPushPackage()** to your **getPackages** method. In the end, your file will be similar to the following:

```java 
import com.huawei.hms.rn.push.HmsPushPackage;
...
@Override
protected List<ReactPackage> getPackages() {
    List<ReactPackage> packages = new PackageList(this).getPackages();
    packages.add(new HmsPushPackage()); // <-- Add this line 
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

### **HmsPushInstanceId**

#### Public Method Summary

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [getId()](#hmspushinstanceidgetid)                       | Promise\<object> | Obtains an AAID in synchronous mode.                    |
| [getAAID()](#hmspushinstanceidgetaaid)                   | Promise\<object> | Obtains an AAID in asynchronous mode.                   |
| [getCreationTime()](#hmspushinstanceidgetcreationiime)   | Promise\<object> | Obtains the generation timestamp of an AAID.            |
| [deleteAAID()](#hmspushinstanceiddeleteaaid)             | Promise\<object> | Deletes a local AAID and its generation timestamp.      |
| [getToken(scope)](#hmspushinstanceidgettokenscope)       | Promise\<object> | Obtains a token required for accessing HUAWEI Push Kit. |
| [deleteToken(scope)](#hmspushinstanceiddeletetokenscope) | Promise\<object> | Deletes a token.                                        |

#### Public Methods

##### HmsPushInstanceId.getId()

This method is used to obtain an AAID in synchronous mode. Before applying for a token, an app calls this method to obtain its unique AAID. The HUAWEI Push server generates a token for the app based on the AAID. If the AAID of the app changes, a new token will be generated next time when the app applies for a token. If an app needs to report statistics events, it must carry the AAID as its unique ID.

Call Example :

```js
HmsPushInstanceId.getId()
  .then((result) => {
    this.log("getId", result);
  })
  .catch((err) => {
    alert("[getID] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response :

```js
{ result: "e7f9ffa7-deef-9684-d715-xxxxxxxx", resultCode: "0" }
```

##### HmsPushInstanceId.getAAID()

This method is used to obtain an AAID in asynchronous mode.

Call Example

```js
HmsPushInstanceId.getAAID()
  .then((result) => {
    this.log("getAAID", result);
  })
  .catch((err) => {
    alert("[getAAID] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: "e7f9ffa7-deef-9684-d715-xxxxxxxx", resultCode: "0" }
```

##### HmsPushInstanceId.getCreationTime()

This method is used to obtain the generation timestamp of an AAID.

Call Example

```js
HmsPushInstanceId.getCreationTime()
  .then((result) => {
    this.log("getCreationTime", result);
  })
  .catch((err) => {
    alert("[getCreationTime] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: "1602419527611", resultCode: "0" }
```

##### HmsPushInstanceId.deleteAAID()
This method is used to delete a local AAID and its generation timestamp.

Call Example

```js
HmsPushInstanceId.deleteAAID()
  .then((result) => {
    this.log("deleteAAID", result);
  })
  .catch((err) => {
    alert("[deleteAAID] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsPushInstanceId.getToken(scope)

This method is used to obtain a token required for accessing HUAWEI Push Kit. If there is no local AAID, this method will automatically generate an AAID when it is called because the HUAWEI Push Kit server needs to generate a token based on the AAID.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| scope      | string |Authorization scope. Default scope value is "HCM" |

Call Example

```js
HmsPushInstanceId.getToken("")
  .then((result) => {
    this.log("getToken", result);
  })
  .catch((err) => {
    alert("[getToken] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: "ALzIeKRcy8fC1hUVpjL7AEIukYfytFbnj2IZgfO-xxxxx", resultCode: "0" }
```

##### HmsPushInstanceId.deleteToken(scope)

This method is used to delete a token. After a token is deleted, the corresponding AAID will not be deleted. This method is a synchronous method.

###### Parameters

| Parameter | Type   | Description                                       |
| --------- | ------ | ------------------------------------------------- |
| scope     | string | Authorization scope. Default scope value is "HCM" |

Call Example

```js
HmsPushInstanceId.deleteToken("")
  .then((result) => {
    this.log("deleteToken", result);
  })
  .catch((err) => {
    alert("[deleteToken] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```


### **HmsPushMessaging**

#### Public Method Summary

| Method                                                       | Return Type      | Description                                                  |
| ------------------------------------------------------------ | ---------------- | ------------------------------------------------------------ |
| [isAutoInitEnabled()](#hmspushmessagingisautoinitenabled)    | Promise\<object> | Checks whether automatic initialization is enabled.          |
| [setAutoInitEnabled(enabled)](#hmspushmessagingsetautoinitenabledenabled) | Promise\<object> | Determines whether to enable automatic initialization.       |
| [subscribe(topic)](#hmspushmessagingsubscribetopic)          | Promise\<object> | Subscribes to topics in asynchronous mode.                   |
| [unsubscribe(topic)](#hmspushmessagingunsubscribetopic)      | Promise\<object> | Unsubscribes from topics in asynchronous mode.               |
| [turnOnPush()](#hmspushmessagingturnonpush)                  | Promise\<object> | Enables the function of receiving notification messages in asynchronous mode. |
| [turnOffPush()](#hmspushmessagingturnoffpush)                | Promise\<object> | Disables the function of receiving notification messages in asynchronous mode. |
| [sendRemoteMessage(uplinkMessage)](#hmspushmessagingsendremotemessageuplinkmessage) | Promise\<object> | Sends an uplink message to the app server.                   |
| [getInitialNotification()](#hmspushmessaginggetinitialnotification) | Promise\<object> | Returns the object that includes **remoteMessage**, **extras** and **uriPage** of the notification which opened the app with clicking notification. |
| [setBackgroundMessageHandler((dataMessage)=>{})](#hmspushmessagingsetbackgroundmessagehandlercallback) | void             | Set a data message handler function which is called when the app is in the background or killed application state. |
| [enableLogger()](#hmspushmessagingenablelogger)              | Promise\<object> | Enables HMS Plugin Method Analytics.                         |
| [disableLogger()](#hmspushmessagingdisablelogger)            | Promise\<object> | Disables HMS Plugin Method Analytics.                        |

#### Public Methods

##### HmsPushMessaging.isAutoInitEnabled()

Checks whether automatic initialization is enabled.

Call Example

```js
HmsPushMessaging.isAutoInitEnabled()
  .then((result) => {
    this.log("isAutoInitEnabled", result);
  })
  .catch((err) => {
    alert("[isAutoInitEnabled] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```


##### HmsPushMessaging.setAutoInitEnabled(enabled)

This method is used to determine whether to enable automatic initialization. If this parameter is set to **true**, the SDK automatically generates an AAID and applies for a token.

###### Parameters

| Parameter  | Type    | Description                          |
|------------|---------|--------------------------------------|
| enabled    | boolean | Indicates whether to enable automatic initialization. The value true indicates yes and false indicates no. |

Call Example

```js
HmsPushMessaging.setAutoInitEnabled(value)
  .then((result) => {
    this.log("setAutoInitEnabled", result);
  })
  .catch((err) => {
    alert("[setAutoInitEnabled] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsPushMessaging.subscribe(topic)

This method is used to subscribe to topics in asynchronous mode. The HUAWEI Push Kit topic messaging function allows you to send messages to multiple devices whose users have subscribed to a specific topic. You can write messages about the topic as required, and HUAWEI Push Kit determines the release path and sends messages to the correct devices in a reliable manner.

###### Parameters

| Parameter  | Type    | Description                          |
|------------|---------|--------------------------------------|
| topic      | string  | Topic to be subscribed to. The value must match the following regular expression: [\u4e00-\u9fa5\w-_.~%]{1,900}. |

Call Example

```js
HmsPushMessaging.subscribe("hmscore-13")
  .then((result) => {
    this.log("subscribe", result);
  })
  .catch((err) => {
    alert("[subscribe] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsPushMessaging.unsubscribe(topic)

This method is used to unsubscribe from topics that are subscribed to through the subscribe method.

###### Parameters

| Parameter  | Type    | Description                          |
|------------|---------|--------------------------------------|
| topic      | string  | Topic to be subscribed to. The value must match the following regular expression: [\u4e00-\u9fa5\w-_.~%]{1,900}. |

Call Example

```js
HmsPushMessaging.unsubscribe(this.state.topic)
  .then((result) => {
    this.log("unsubscribe", result);
  })
  .catch((err) => {
    alert("[unsubscribe] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsPushMessaging.turnOnPush()

This method is used to enable the display of notification messages. If you want to control the display of notification messages in an app, you can call this method. This method applies to notification messages but not data messages. It is the app that determines whether to enable data messaging.

Call Example

```js
HmsPushMessaging.turnOnPush()
  .then((result) => {
    this.log("turnOnPush", result);
  })
  .catch((err) => {
    alert("[turnOnPush] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsPushMessaging.turnOffPush()

This method is used to disable the display of notification messages. If you want to control the display of notification messages in an app, you can call this method. This method applies to notification messages but not data messages. It is the app that determines whether to enable data messaging.

Call Example

```js
HmsPushMessaging.turnOffPush()
  .then((result) => {
    this.log("turnOffPush", result);
  })
  .catch((err) => {
    alert("[turnOffPush] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsPushMessaging.sendRemoteMessage(uplinkMessage)

Sends an uplink message to the application server.

###### Parameters

| Parameter       |  Description                          |
|-----------------|---------------------------------------|
| uplinkMessage   | Remote message to be sent to the application server |

Call Example

```js
HmsPushMessaging.sendRemoteMessage({
  [RemoteMessageBuilder.TO]: "",
  //[RemoteMessageBuilder.MESSAGE_ID]: '', // Auto generated
  [RemoteMessageBuilder.MESSAGE_TYPE]: "hms",
  [RemoteMessageBuilder.COLLAPSE_KEY]: "-1",
  [RemoteMessageBuilder.TTL]: 120,
  [RemoteMessageBuilder.RECEIPT_MODE]: 1,
  [RemoteMessageBuilder.SEND_MODE]: 1,
  [RemoteMessageBuilder.DATA]: {
    key1: "test",
    message: "ReactNative HMS Push",
  },
})
  .then((result) => {
    this.log("sendRemoteMessage", result);
  })
  .catch((err) => {
    alert("[sendRemoteMessage] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```


##### HmsPushMessaging.getInitialNotification()

Returns the object that includes **remoteMessage**, **extras** and **uriPage** of the notification which opened the app with clicking notification.

Call Example

```js
HmsPushMessaging.getInitialNotification()
  .then((result) => {
    this.log("getInitialNotification", result);
  })
  .catch((err) => {
    alert("[getInitialNotification] Error/Exception: " + JSON.stringify(err));
  });
```
Example Response

```js
{
  result: {
    extras: {
      _push_cmd_type: "cosa",
      _push_msgid: "-1744601771",
      _push_notifyid: 1218295586,
      KEY1: "VALUE1",
    },
    remoteMessage: {
      Link: "null",
      NotifyId: "0",
      contents: "0",
      ttl: "86400",
      isDefaultSound: "true",
      ...
    },
    uriPage: "app://app2",
  },
  resultCode: "0",
}
```

##### HmsPushMessaging.setBackgroundMessageHandler(callback)

Set a data message handler function which is called when the app is in the background or killed application state. 

###### Parameters

| Parameter | Description                                                  |
| --------- | ------------------------------------------------------------ |
| callback  | To be called when the app is in the background or killed application state. |


Call Example

```js
HmsPushMessaging.setBackgroundMessageHandler((dataMessage) => {
  HmsLocalNotification.localNotification({
    [HmsLocalNotification.Attr.title]: "[Headless] DataMessage Received",
    [HmsLocalNotification.Attr.message]: new RNRemoteMessage(
      dataMessage
    ).getDataOfMap(),
  })
    .then((result) => {
      console.log("[Headless] DataMessage Received", result);
    })
    .catch((err) => {
      console.log(
        "[LocalNotification Default] Error/Exception: " + JSON.stringify(err)
      );
    });

  return Promise.resolve();
});
```

Example Response

```js
{ msg: {
    BadgeNumber: "null",
    ChannelId: null,
    ClickAction: null,
    Color: null,
    ImageUrl: "null",
    Importance: "0",
    LightSettings: "null",
    Link: "null",
    NotifyId: "0",
    Sound: null,
    Tag: null,
    Ticker: null,
    When: "null",
    body: null,
    bodyLocalizationArgs: "null",
    bodyLocalizationKey: null,
    collapseKey: null,
    contents: "0",
    data: "{"KEY1":"VALUE1"}",
    dataOfMap: "{KEY1=VALUE1}",
    from: "102032571",
    icon: null,
    intentUri: null,
    isAutoCancel: "true",
    isDefaultLight: "true",
    isDefaultSound: "true",
    isDefaultVibrate: "true",
    isLocalOnly: "true",
    messageId: "0",
    messageType: null,
    originalUrgency: "2",
    receiptMode: "0",
    sendMode: "0",
    sentTime: "0",
    title: null,
    titleLocalizationArgs: "null",
    titleLocalizationKey: null,
    to: null,
    token: "AH1YfzXEW5KWFIrjf_PGfSte5rOW9RdAr_xxxx",
    ttl: "2147483647",
    urgency: "2",
    vibrateConfig: "null",
    visibility: "null",
  }
}
```

##### HmsPushMessaging.enableLogger()

This method enables HMSLogger capability which is used for sending usage analytics of Push SDK's methods to improve the service quality.

Call Example

```js
HmsPushMessaging.enableLogger()
  .then((result) => {
    this.log("enableLogger", result);
  })
  .catch((err) => {
    alert("[enableLogger] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsPushMessaging.disableLogger()

This method disables HMSLogger capability which is used for sending usage analytics of Push SDK's methods to improve the service quality.

Call Example

```js
HmsPushMessaging.disableLogger()
  .then((result) => {
    this.log("disableLogger", result);
  })
  .catch((err) => {
    alert("[disableLogger] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

### **HmsPushOpenDevice**

#### Public Method Summary

| Method                                 | Return Type      | Description                           |
| -------------------------------------- | ---------------- | ------------------------------------- |
| [getOdid()](#hmspushopendevicegetodid) | Promise\<object> | Obtains an Odid in asynchronous mode. |

#### Public Methods

##### HmsPushOpenDevice.getOdid()

This method is used to obtain an Odid in asynchronous mode.

Call Example

```js
HmsPushOpenDevice.getOdid()
  .then((result) => {
    this.log("getOdid", result);
  })
  .catch((err) => {
    alert("[getOdid] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: "99fdd7d7-777b-b9aa-f3e7-xxxxx", resultCode: "0" }
```

### **HmsLocalNotification**

#### Public Method Summary

| Method                                                       | Return Type      | Description                                                  |
| ------------------------------------------------------------ | ---------------- | ------------------------------------------------------------ |
| [localNotification(notification)](#hmslocalnotificationlocalnotificationnotification) | Promise\<object> | Pushes a local notification instantly.                       |
| [localNotificationSchedule(notification)](#hmslocalnotificationlocalnotificationschedulenotification) | Promise\<object> | Schedules a local notification to be pushed at a further time. |
| [cancelAllNotifications()](#hmslocalnotificationcancelallnotifications) | Promise\<object> | Cancels all pending scheduled notifications and the ones registered in the notification manager. |
| [cancelNotifications()](#hmslocalnotificationcancelnotifications) | Promise\<object> | Cancels all pending notifications registered in the notification manager. |
| [cancelScheduledNotifications()](#hmslocalnotificationcancelschedulednotifications) | Promise\<object> | Cancels all pending scheduled notifications.                 |
| [cancelNotificationsWithId(idArr)](#hmslocalnotificationcancelnotificationswithididarr) | Promise\<object> | Cancels all pending notifications by an array of IDs.        |
| [cancelNotificationsWithIdTag(idTagArr)](#hmslocalnotificationcancelnotificationswithidtagidtagarr) | Promise\<object> | Cancels all pending notifications by an array of objects encapsulating the fields "**Id**" and "**tag**", both of the field values are strings. |
| [cancelNotificationsWithTag(tag)](#hmslocalnotificationcancelnotificationswithtagtag) | Promise\<object> | Cancel a notification with tag.                              |
| [getNotifications()](#hmslocalnotificationgetnotifications)  | Promise\<object> | Returns an array of all notifications.                       |
| [getScheduledNotifications()](#hmslocalnotificationgetschedulednotifications) | Promise\<object> | Returns a list of all pending scheduled notifications.       |
| [getChannels()](#hmslocalnotificationgetchannels)            | Promise\<object> | Returns a string array of all notification channels.         |
| [channelExists(channelId)](#hmslocalnotificationchannelexistschanneld) | Promise\<object> | Checks whether a notification channel with the given **channelId** exists. |
| [channelBlocked(channelId)](#hmslocalnotificationchannelblockedchanneld) | Promise\<object> | Returns true if the notification channel with the specified ID is blocked |
| [deleteChannel(channelId)](#hmslocalnotificationdeletechannelchanneld) | Promise\<object> | Deletes the notification channel with specified ID.          |


#### Public Methods

##### HmsLocalNotification.localNotification(notification)

Pushes a local notification instantly.

###### Parameters

| Parameter       |  Description                          |
|-----------------|---------------------------------------|
| notification    | Local notification object to be pushed, refer **HmsLocalNotification.Attr** |

Call Example

```js
HmsLocalNotification.localNotification({
  [HmsLocalNotification.Attr.title]: "Notification Title",
  [HmsLocalNotification.Attr.message]: "Notification Message", // (required)
  [HmsLocalNotification.Attr.title]: "HMS Push",
  [HmsLocalNotification.Attr.message]: "This is Local Notification",
  [HmsLocalNotification.Attr.bigText]: "This is a bigText",
  [HmsLocalNotification.Attr.subText]: "This is a bigText",
  [HmsLocalNotification.Attr.tag]: "push-tag",
  [HmsLocalNotification.Attr.largeIcon]: "ic_launcher",
  [HmsLocalNotification.Attr.smallIcon]: "ic_notification",
  [HmsLocalNotification.Attr.bigText]: "This is a bigText",
  [HmsLocalNotification.Attr.subText]: "This is a subText",
  [HmsLocalNotification.Attr.importance]: HmsLocalNotification.Importance.max,
})
  .then((result) => {
    this.log("LocalNotification Default", result);
  })
  .catch((err) => {
    alert(
      "[LocalNotification Default] Error/Exception: " + JSON.stringify(err)
    );
  });
```
Example Response

```js
{ result: {
    action: "No"
    actions: "["Yes", "No"]"
    autoCancel: false
    bigText: "This is a bigText"
    color: "white"
    dontNotifyInForeground: false
    groupSummary: false
    id: "-981754571"
    importance: "max"
    invokeApp: false
    largeIcon: "ic_launcher"
    message: "This is Local Notification"
    ongoing: false
    showWhen: true
    smallIcon: "ic_notification"
    subText: "This is a subText"
    tag: null
    ticker: "Optional Ticker"
    title: "HMS Push"
    vibrate: false
    vibrateDuration: 1000
  },
  resultCode: "0"
}
```


##### HmsLocalNotification.localNotificationSchedule(notification)

Schedules a local notification to be pushed at a further time.

###### Parameters

| Parameter    | Description                                                  |
| ------------ | ------------------------------------------------------------ |
| notification | Local notification object to be pushed, refer **HmsLocalNotification.Attr** |

Call Example

```js
HmsLocalNotification.localNotificationSchedule({
  [HmsLocalNotification.Attr.title]: "Notification Title",
  [HmsLocalNotification.Attr.message]: "Notification Message", // (required)
  [HmsLocalNotification.Attr.fireDate]: new Date(
    Date.now() + 60 * 1000
  ).getTime(), // in 1 min
  [HmsLocalNotification.Attr.title]: "HMS Push",
  [HmsLocalNotification.Attr.message]: "This is Local Notification",
  [HmsLocalNotification.Attr.bigText]: "This is a bigText",
  [HmsLocalNotification.Attr.subText]: "This is a bigText",
  [HmsLocalNotification.Attr.tag]: "push-tag",
  [HmsLocalNotification.Attr.largeIcon]: "ic_launcher",
  [HmsLocalNotification.Attr.smallIcon]: "ic_notification",
  [HmsLocalNotification.Attr.bigText]: "This is a bigText",
  [HmsLocalNotification.Attr.subText]: "This is a subText",
  [HmsLocalNotification.Attr.importance]: HmsLocalNotification.Importance.max,
})
  .then((result) => {
    this.log("LocalNotification Scheduled", result);
  })
  .catch((err) => {
    alert(
      "[LocalNotification Scheduled] Error/Exception: " + JSON.stringify(err)
    );
  });
```

Example Response

```js
{ result: {
    fireDate: 1602527734143,
    action: "No"
    actions: "["Yes", "No"]"
    autoCancel: false
    bigText: "This is a bigText"
    color: "white"
    dontNotifyInForeground: false
    groupSummary: false
    id: "-981754571"
    importance: "max"
    invokeApp: false
    largeIcon: "ic_launcher"
    message: "This is Local Notification"
    ongoing: false
    showWhen: true
    smallIcon: "ic_notification"
    subText: "This is a subText"
    tag: null
    ticker: "Optional Ticker"
    title: "HMS Push"
    vibrate: false
    vibrateDuration: 1000
  },
  resultCode: "0"
}
```

##### HmsLocalNotification.cancelAllNotifications()

Cancels all pending scheduled notifications and the ones registered in the notification manager.

Call Example

```js
HmsLocalNotification.cancelAllNotifications()
  .then((result) => {
    this.log("cancelAllNotifications", result);
  })
  .catch((err) => {
    alert("[cancelAllNotifications] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsLocalNotification.cancelNotifications()

Cancels all pending notifications registered in the notification manager.

Call Example

```js
HmsLocalNotification.cancelNotifications()
  .then((result) => {
    this.log("cancelNotifications", result);
  })
  .catch((err) => {
    alert("[cancelNotifications] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsLocalNotification.cancelScheduledNotifications()

Cancels all notification messages to be scheduled for pushing.

Call Example

```js
HmsLocalNotification.cancelScheduledNotifications()
  .then((result) => {
    this.log("cancelScheduledNotifications", result);
  })
  .catch((err) => {
    alert(
      "[cancelScheduledNotifications] Error/Exception: " + JSON.stringify(err)
    );
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsLocalNotification.cancelNotificationsWithId(idArr)

Cancels all pending notifications by an array of IDs.

###### Parameters

| Parameter | Type     | Description               |
| --------- | -------- | ------------------------- |
| idArr     | number[] | Array of notification IDs |

Call Example

```js
HmsLocalNotification.cancelNotificationsWithId([13, 14])
  .then((result) => {
    this.log("cancelScheduledNotifications", result);
  })
  .catch((err) => {
    alert(
      "[cancelScheduledNotifications] Error/Exception: " + JSON.stringify(err)
    );
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsLocalNotification.cancelNotificationsWithIdTag(idTagArr)

Cancels all pending notifications by an array of objects encapsulating the fields "**Id**" and "**tag**", both of the field values are strings.

###### Parameters

| Parameter | Type     | Description                                                  |
| --------- | -------- | ------------------------------------------------------------ |
| idTagArr  | object[] | Array of objects encapsulating the fields "**Id**" and "**tag**" |

Call Example

```js
HmsLocalNotification.cancelNotificationsWithIdTag([
  { id: "13", tag: "some-tag" },
  { id: "14", tag: "some-tag" },
])
  .then((result) => {
    this.log("cancelNotificationsWithIdTag", result);
  })
  .catch((err) => {
    alert(
      "[cancelNotificationsWithIdTag] Error/Exception: " + JSON.stringify(err)
    );
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsLocalNotification.cancelNotificationsWithTag(tag)

Cancel a notifications with tag.

###### Parameters

| Parameter | Type   | Description      |
| --------- | ------ | ---------------- |
| tag       | string | Notification tag |

Call Example

```js
HmsLocalNotification.cancelNotificationsWithTag("tag")
  .then((result) => {
    this.log("cancelNotificationsWithTag", result);
  })
  .catch((err) => {
    alert(
      "[cancelNotificationsWithTag] Error/Exception: " + JSON.stringify(err)
    );
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsLocalNotification.getNotifications()

Returns an array of all notifications.

Call Example

```js
HmsLocalNotification.getNotifications()
  .then((result) => {
    this.log("getNotifications", result);
  })
  .catch((err) => {
    alert("[getNotifications] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{
  result: [
    {
      group: null,
      tag: null,
      body: "This is Local Notification",
      title: "HMS Push",
      statusBarNotificationId: "1713745810",
      ...
    },
    ...
  ];
  resultCode: "0";
}
```

##### HmsLocalNotification.getScheduledNotifications()

Returns a list of all pending scheduled notifications.

Call Example

```js
HmsLocalNotification.getScheduledNotifications()
  .then((result) => {
    this.log("getScheduledNotifications", result);
  })
  .catch((err) => {
    alert(
      "[getScheduledNotifications] Error/Exception: " + JSON.stringify(err)
    );
  });
```

Example Response

```js
{
  result: [
    {
      group: null,
      tag: null,
      body: "This is Local Notification",
      title: "HMS Push",
      statusBarNotificationId: "1713745810",
      ...
    },
    ...
  ];
  resultCode: "0";
}
```

##### HmsLocalNotification.getChannels()

Returns a string array of all notification channels.

Call Example

```js
HmsLocalNotification.getChannels()
  .then((result) => {
    this.log("getChannels", result);
  })
  .catch((err) => {
    alert("[getChannels] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ 
  result: [
          "com.huawei.android.pushagent51259"
          "huawei-hms-rn-push-channel-id-5-default-5000"
          "huawei-hms-rn-push-channel-id-4-default-250"
          "huawei-hms-rn-push-channel-id-5-default"
          "huawei-hms-rn-push-channel-id-4"
          "huawei-hms-rn-push-channel-id-5-huawei_bounce"
         ], 
  resultCode: "0"
}
```

##### HmsLocalNotification.channelExists(channeld)

Returns true if the notification channel with the specified ID is exists.

###### Parameters

| Parameter | Type   | Description             |
| --------- | ------ | ----------------------- |
| channeld  | string | Notification Channel ID |

Call Example

```js
HmsLocalNotification.channelExists("huawei-hms-rn-push-channel-id")
  .then((result) => {
    this.log("channelExists", result);
  })
  .catch((err) => {
    alert("[channelExists] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

##### HmsLocalNotification.channelBlocked(channeld)

Returns true if the notification channel with the specified ID is blocked.

###### Parameters

| Parameter | Type   | Description             |
| --------- | ------ | ----------------------- |
| channeld  | string | Notification Channel ID |


Call Example

```js
HmsLocalNotification.channelBlocked(
  "huawei-hms-rn-push-channel-id-5-huawei_bounce"
)
  .then((result) => {
    this.log("channelBlocked", result);
  })
  .catch((err) => {
    alert("[channelBlocked] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: false, resultCode: "0" }
```

##### HmsLocalNotification.deleteChannel(channeld)

Deletes the notification channel with specified ID.

###### Parameters

| Parameter | Type   | Description             |
| --------- | ------ | ----------------------- |
| channeld  | string | Notification Channel ID |


Call Example

```js
HmsLocalNotification.deleteChannel("hms-channel-custom")
  .then((result) => {
    this.log("deleteChannel", result);
  })
  .catch((err) => {
    alert("[deleteChannel] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

#### HmsLocalNotification.Attr

This object contains the field names for local notifications attributes. These fields are used to construct local notification messages.

| Name                                           | Type                            | Description                                                  |
| ---------------------------------------------- | ------------------------------- | ------------------------------------------------------------ |
| HmsLocalNotification.Attr.id                   | string                          | An identifier for the notification message to be pushed.     |
| HmsLocalNotification.Attr.message              | string                          | The message (second row) of the notification                 |
| HmsLocalNotification.Attr.fireDate             | Date                            | The time at which the notification will be posted.           |
| HmsLocalNotification.Attr.title                | string                          | The title (first row) of the notification                    |
| HmsLocalNotification.Attr.ticker               | string                          | The ticker of the notification which is sent to the accessibility services |
| HmsLocalNotification.Attr.autoCancel           | boolean                         | If true, the notification is dismissed on click.             |
| HmsLocalNotification.Attr.largeIcon            | string                          | The name of the file to be set as the large icon for notification |
| HmsLocalNotification.Attr.largeIconUrl         | string                          | URL of the image to be set as the large icon for the notification to be pushed. |
| HmsLocalNotification.Attr.smallIcon            | string                          | The name of the file to be set as the small icon for notification |
| HmsLocalNotification.Attr.bigText              | string                          | The longer text to be displayed in the big form of the template |
| HmsLocalNotification.Attr.subText              | string                          | Additional information to be displayed in notification       |
| HmsLocalNotification.Attr.bigPictureUrl        | string                          | URL of the image to be set as the big picture for the notification to be pushed. |
| HmsLocalNotification.Attr.shortcutId           | string                          | If the notification is duplicative of a launcher shortcut, sets the case the Launcher wants to hide the shortcut. |
| HmsLocalNotification.Attr.number               | number                          | Sets the number of items this notification represents. Launchers that support badging may display it as a badge. |
| HmsLocalNotification.Attr.channelId            | string                          | The id of the notification channel for the notification to be pushed |
| HmsLocalNotification.Attr.channelName          | string                          | Name of the channel to be created for the notification to be pushed |
| HmsLocalNotification.Attr.channelDescription   | string                          | Description for the channel to be created for the notification to be pushed. |
| HmsLocalNotification.Attr.color                | string                          | The notification color in #RRGGBB or #AARRGGBB formats or string like 'white' |
| HmsLocalNotification.Attr.group                | string                          | The notification group, the notifications in the same group are displayed in a stacked way, if the device used supports such rendering. |
| HmsLocalNotification.Attr.groupSummary         | boolean                         | If true, this notification is included in the specified group. |
| HmsLocalNotification.Attr.playSound            | boolean                         | If true, the specified sound will be played when a notification message is pushed. |
| HmsLocalNotification.Attr.soundName            | string                          | Name of the sound file in the raw folder to be played, when a notification message is pushed. |
| HmsLocalNotification.Attr.vibrate              | boolean                         | Turn on or off the vibration when a notification message a notification message is pushed. |
| HmsLocalNotification.Attr.vibrateDuration      | number                          | The duration of the vibration when a notification message is pushed |
| HmsLocalNotification.Attr.actions              | string[]                        | Adds action(s) to the notification to be pushed, actions are displayed as buttons adjacent notification content. |
| HmsLocalNotification.Attr.invokeApp            | boolean                         | If true, the app pushed the notification is invoked when a pushed notification is clicked. |
| HmsLocalNotification.Attr.tag                  | string                          | A tag for the notification to be set for identification      |
| HmsLocalNotification.Attr.repeatType           | HmsLocalNotification.RepeatType | The time to repeat pushing for a scheduled notification.     |
| HmsLocalNotification.Attr.repeatTime           | number                          | The Time in milliseconds to repeat pushing the next scheduled notification message. |
| HmsLocalNotification.Attr.ongoing              | boolean                         | If true, the notification to be pushed will be an ongoing notification, which can't be cancelled by the user by a swipe and the app must handle the cancelling. |
| HmsLocalNotification.Attr.allowWhileIdle       | boolean                         | If true, a scheduled notification message can be pushed even when the device is in low-power idle mode. |
| HmsLocalNotification.Attr.dontNotifyForeground | boolean                         | If true, the a notification won't be pushed when the app is in foreground. |
| HmsLocalNotification.Attr.priority             | HmsLocalNotification.Priority   | The priority for the notification to be pushed.              |
| HmsLocalNotification.Attr.importance           | HmsLocalNotification.Importance | The importance for the notification to be pushed             |
| HmsLocalNotification.Attr.visibility           | HmsLocalNotification.Visibility | The visibility of the notification to be pushed              |


#### HmsLocalNotification.Priority

This object contains the values for the local notification priority.

| Name                                  | Description                                        |
| ------------------------------------- | -------------------------------------------------- |
| HmsLocalNotification.Priority.max     | Max priority for the notification to be pushed     |
| HmsLocalNotification.Priority.high    | High priority for the notification to be pushed    |
| HmsLocalNotification.Priority.default | Default priority for the notification to be pushed |
| HmsLocalNotification.Priority.low     | Low priority for the notification to be pushed     |
| HmsLocalNotification.Priority.min     | Min priority for the notification to be pushed     |

#### HmsLocalNotification.Visibility

This object contains the values for the local notification visibility.

| Name                                    | Description                                                  |
| --------------------------------------- | ------------------------------------------------------------ |
| HmsLocalNotification.Visibility.public  | When set, the notification is shown entirely in all lock screens. |
| HmsLocalNotification.Visibility.secret  | When set, the notification is hidden entirely in secure lock screens. |
| HmsLocalNotification.Visibility.private | When set, the notification is shown entirely in all lock screens, but the private information is hidden in secure lock screens. |

#### HmsLocalNotification.Importance

This object contains the values for the local notification importance.

| Name                                        | Description                                                  |
| ------------------------------------------- | ------------------------------------------------------------ |
| HmsLocalNotification.Importance.max         | When set, the notification is pushed from the channel with max importance. |
| HmsLocalNotification.Importance.high        | When set, the notification is pushed from the channel with high importance. |
| HmsLocalNotification.Importance.default     | When set, the notification is pushed from the channel with default importance. |
| HmsLocalNotification.Importance.low         | When set, the notification is pushed from the channel with low importance. |
| HmsLocalNotification.Importance.min         | When set, the notification is pushed from the channel with min importance. |
| HmsLocalNotification.Importance.none        | When set, the notification is pushed from the channel with none importance. |
| HmsLocalNotification.Importance.unspecified | When set, the notification is pushed from the channel with unspecified importance. |


#### HmsLocalNotification.RepeatType

This object contains the repeat types (repeat periods) of the scheduled messages to be sent.

| Name                                       | Description |
| ------------------------------------------ | ----------- |
| HmsLocalNotification.RepeatType.hour       | 1 Hour      |
| HmsLocalNotification.RepeatType.minute     | 1 Minute    |
| HmsLocalNotification.RepeatType.day        | 1 Day       |
| HmsLocalNotification.RepeatType.week       | 1 Week      |
| HmsLocalNotification.RepeatType.customTime | Custom Time |

### **RemoteMessageBuilder**

Contains the fields required to construct remote messages to be sent as uplink messages.

#### Fields

| Name                              | Description                                                  |
| --------------------------------- | ------------------------------------------------------------ |
| RemoteMessageBuilder.TO           | For an uplink message, default value is **push.hcm.upstream**. |
| RemoteMessageBuilder.MESSAGE_ID   | Message ID, which is generated by an app and is unique for each message. |
| RemoteMessageBuilder.MESSAGE_TYPE | The message type is transparently transmitted by the SDK.    |
| RemoteMessageBuilder.TTL          | Maximum cache duration of an offline message set for HUAWEI Push Kit, in seconds. The duration can be set to 15 days at most. The value of the input parameter ttl must be within the range [1,1296000] |
| RemoteMessageBuilder.COLLAPSE_KEY | Sets the maximum cache duration of a message, in seconds. If you set to **-1**, all offline messages of the app are sent to the user after the user's device goes online. If you set 0, offline messages of the app sent to the user are determined by the default policy of HUAWEI Push Kit. Generally, only the latest offline message is sent to the user after the user's device goes online. You can set a value ranging from 1 to 100 to group messages. For example, if you send 10 messages and set collapse_key to 1 for the first five messages and to 2 for the rest, the latest offline message whose value is 1 and the latest offline message whose value is 2 are sent to the user after the user's device goes online. |
| RemoteMessageBuilder.RECEIPT_MODE | The value can be 0 or 1. The value 1 indicates that the receipt capability is enabled after messages are sent. That is, if an uplink message sent by the app is successfully sent to the app server, the server will respond and send a receipt to the app through the [**onPushMessageSentDelivered**](#hmspusheventonpushmessagesentdeliveredresult-) event. |
| RemoteMessageBuilder.SEND_MODE    | Sets whether to enable the message cache and resending capability of the Push Kit client. If this parameter is not set, messages cannot be cached or resent. For example, when the network is unavailable, messages are directly discarded. The value can be 0 or 1. The value 1 indicates that the cache and resending capability is enabled. |
| RemoteMessageBuilder.DATA         | Sets key-value pair data to a message.                       |

Call Example

```js
HmsPushMessaging.sendRemoteMessage({
  [RemoteMessageBuilder.TO]: "",
  //[RemoteMessageBuilder.MESSAGE_ID]: '', // Auto generated
  [RemoteMessageBuilder.MESSAGE_TYPE]: "hms",
  [RemoteMessageBuilder.COLLAPSE_KEY]: "-1",
  [RemoteMessageBuilder.TTL]: 120,
  [RemoteMessageBuilder.RECEIPT_MODE]: 1,
  [RemoteMessageBuilder.SEND_MODE]: 1,
  [RemoteMessageBuilder.DATA]: { key1: "test", message: "huawei-test" },
})
  .then((result) => {
    this.log("sendRemoteMessage", result);
  })
  .catch((err) => {
    alert("[sendRemoteMessage] Error/Exception: " + JSON.stringify(err));
  });
```

Example Response

```js
{ result: true, resultCode: "0" }
```

### **RNRemoteMessage**

Represents a message entity class encapsulated using JavaScript. You can use the get methods in this class to receive data messages that are obtained. This class maps the native class **RemoteMessage** of the Android Push SDK.


#### Public Method Summary

| Method                     | Return Type | Description                                                  |
| -------------------------- | ----------- | ------------------------------------------------------------ |
| getCollapseKey()           | string      | This method is used to obtain the classification identifier (collapse key) of a message. |
| getData()                  | string      | This method is used to obtain the payload of a message. Data obtained using the getData method is of the string type instead of the Map type of Java. You can determine the parsing rule of the data format. If data in the key-value format is transferred on the HUAWEI Push console, the data is converted into a JSON string and needs to be parsed. If data transmitted through HUAWEI Push is of high sensitivity and confidentiality, it is recommended that the message body be encrypted and decrypted by yourselves for security. |
| getDataOfMap()             | object      | This method is used to obtain the payload of a Map message. Different from the getData method, this method directly returns a Map data instance or null (indicating an empty Map data instance). |
| getMessageId()             | string      | This method is used to obtain the ID of a message.           |
| getMessageType()           | string      | This method is used to obtain the type of a message.         |
| getOriginalUrgency()       | number      | This method is used to obtain the message priority set by the app when it sends a message through an API of the HUAWEI Push Kit server. |
| getUrgency()               | number      | This method is used to obtain the message priority set on the HUAWEI Push Kit server. |
| getTtl()                   | number      | This method is used to obtain the maximum cache duration (in seconds) of a message. For a downstream message, the value of ttl in the AndroidConfig structure is returned. |
| getSentTime()              | number      | This method is used to obtain the time (in milliseconds) when a message is sent from the server. |
| getTo()                    | string      | This method is used to obtain the recipient of a message.    |
| getFrom()                  | string      | This method is used to obtain the source of a message. The value returned is the same as that set in the message body on the HUAWEI Push Kit server. |
| getToken()                 | string      | This method is used to obtain the token in a message.        |
| getNotificationTitle()     | string      | This method is used to obtain the title of a message.        |
| getTitleLocalizationKey()  | string      | This method is used to obtain the key of the localized title of a notification message for message display localization. To implement localization for notification messages, the key must be consistent with the node name defined in the strings.xml file of the app. |
| getTitleLocalizationArgs() | object      | This method is used to obtain variable string values in the localized title of a notification message. It must be used together with the **getTitleLocalizationKey()** method. The key obtained by the **getTitleLocalizationKey()** method must be the same as the node name in the strings.xml file of the app, and the number of variable string values obtained by the **getTitleLocalizationArgs()** method cannot be smaller than the number of placeholders in the value mapping the key in the strings.xml file. |
| getBodyLocalizationKey()   | string      | This method is used to obtain the key of the localized content of a notification message for message display localization. To implement localization for notification messages, the key must be consistent with the node name defined in the strings.xml file of the app. |
| getBodyLocalizationArgs()  | object      | This method is used to obtain variable string values in the localized content of a message. It must be used together with the **getBodyLocalizationKey()** method. The key obtained by the **getBodyLocalizationKey()** method must be the same as the node name in the strings.xml file of the app, and the number of variable string values obtained by the **getBodyLocalizationArgs()** method cannot be smaller than the number of placeholders in the value mapping the key in the strings.xml file. |
| getBody()                  | string      | This method is used to obtain the displayed content of a message. |
| getIcon()                  | string      | This method is used to obtain the image resource name of a notification icon. On an Android device, all icon files are stored in the /res/raw/** directory of the app. |
| getSound()                 | string      | This method is used to obtain the name of an audio resource to be played when a notification message is displayed. On an Android device, all audio files are stored in the /res/raw/** directory of the app. If no audio resource is set, set this parameter to null. |
| getTag()                   | string      | This method is used to obtain the tag from a message for message overwriting. A message will be overwritten by another message with the same tag but is sent later. |
| getColor()                 | string      | This method is used to obtain the colors (in #RRGGBB format) of icons in a message. |
| getClickAction()           | string      | This method is used to obtain the action triggered upon notification message tapping. If no action is specified, null is returned. |
| getChannelId()             | string      | This method is used to obtain IDs of channels that support the display of a message. If no channel is set, null is returned. |
| getImageUrl()              | object      | This method is used to obtain the URL of an image in a message. The image URL must be a URL that can be accessed from the public network. |
| getLink()                  | object      | This method is used to obtain the deep link from a message. A deep link is a specific URL, such as the URL of a web page or rich media. If no URL is set, null is returned. |
| getNotifyId()              | number      | This method is used to obtain the unique ID of a message. Different messages can have the same value of NotifyId, so that new messages can overwrite old messages. |
| isDefaultLight()           | boolean     | This method is used to check whether a notification message uses the default notification light settings. |
| isDefaultSound()           | boolean     | This method is used to check whether a notification message uses the default sound. |
| isDefaultVibrate()         | boolean     | This method is used to check whether a notification message uses the default vibration mode. |
| getWhen()                  | number      | This method is used to obtain the time (in milliseconds) when an event occurs from a notification message. Developers can sort notification messages by this time. |
| getLightSettings()         | number[]    | This method is used to obtain the blinking frequency and color of a breathing light. |
| getBadgeNumber()           | number      | This method is used to obtain the number of notification messages. |
| isAutoCancel()             | boolean     | This method is used to check whether a notification message is sticky. If true is returned, the notification message will disappear after a user taps it. If false is returned, the notification message will not disappear after a user taps it, but the user can swipe right or tap the trash can icon to delete the message. |
| getImportance()            | number      | This method is used to obtain the priority of a notification message. |
| getTicker()                | string      | This method is used to obtain the text to be displayed on the status bar for a notification message. |
| getVibrateConfig()         | number[]    | This method is used to obtain the vibration mode of a message. For details, please refer to the description of vibrate_config in the AndroidNotification structure in Sending Messages. |
| getVisibility()            | number      | This method is used to obtain the visibility of a notification message. For details, please refer to the definition of visibility in the AndroidNotification structure. |
| getIntentUri()             | string      | This method is used to obtain the intent in a notification message. The intent can be opening a page specified by the app. For details, please refer to the definition of the intent parameter in the ClickAction structure in Sending Messages of the development guide. |
| parseMsgAllAttribute()     | string      | Returns a string representation of the remote message.       |

Call Example

```js
this.listener = HmsPushEvent.onRemoteMessageReceived(
  (result) => {
    {
      const RNRemoteMessageObj = new RNRemoteMessage(result.msg);
      HmsLocalNotification.localNotification({
        [HmsLocalNotification.Attr.title]: "DataMessage Received",
        [HmsLocalNotification.Attr.message]: RNRemoteMessageObj.getDataOfMap(),
      });
      this.log("onRemoteMessageReceived", result);
    }
  }
);
```

#### RNRemoteMessage.NOTIFICATION

| Field                                              |
| -------------------------------------------------- |
| RNRemoteMessage.NOTIFICATION.TITLE                 |
| RNRemoteMessage.NOTIFICATION.TITLELOCALIZATIONKEY  |
| RNRemoteMessage.NOTIFICATION.TITLELOCALIZATIONARGS |
| RNRemoteMessage.NOTIFICATION.BODYLOCALIZATIONKEY   |
| RNRemoteMessage.NOTIFICATION.BODYLOCALIZATIONARGS  |
| RNRemoteMessage.NOTIFICATION.BODY                  |
| RNRemoteMessage.NOTIFICATION.ICON                  |
| RNRemoteMessage.NOTIFICATION.SOUND                 |
| RNRemoteMessage.NOTIFICATION.TAG                   |
| RNRemoteMessage.NOTIFICATION.COLOR                 |
| RNRemoteMessage.NOTIFICATION.CLICKACTION           |
| RNRemoteMessage.NOTIFICATION.CHANNELID             |
| RNRemoteMessage.NOTIFICATION.IMAGEURL              |
| RNRemoteMessage.NOTIFICATION.LINK                  |
| RNRemoteMessage.NOTIFICATION.NOTIFYID              |
| RNRemoteMessage.NOTIFICATION.WHEN                  |
| RNRemoteMessage.NOTIFICATION.LIGHTSETTINGS         |
| RNRemoteMessage.NOTIFICATION.BADGENUMBER           |
| RNRemoteMessage.NOTIFICATION.IMPORTANCE            |
| RNRemoteMessage.NOTIFICATION.TICKER                |
| RNRemoteMessage.NOTIFICATION.VIBRATECONFIG         |
| RNRemoteMessage.NOTIFICATION.VISIBILITY            |
| RNRemoteMessage.NOTIFICATION.INTENTURI             |
| RNRemoteMessage.NOTIFICATION.ISAUTOCANCEL          |
| RNRemoteMessage.NOTIFICATION.ISLOCALONLY           |
| RNRemoteMessage.NOTIFICATION.ISDEFAULTLIGHT        |
| RNRemoteMessage.NOTIFICATION.ISDEFAULTSOUND        |
| RNRemoteMessage.NOTIFICATION.ISDEFAULTVIBRATE      |

### **HmsPushEvent**

#### Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| REMOTE_DATA_MESSAGE_RECEIVED    | Event emitted when a data message is received.               |
| TOKEN_RECEIVED_EVENT            | Event emitted when a new token is received.                  |
| ON_TOKEN_ERROR_EVENT            | Event emitted when there was an error with the new token request. |
| NOTIFICATION_OPENED_EVENT       | Event emitted when the user clicks to a pushed notification. |
| LOCAL_NOTIFICATION_ACTION_EVENT | Event emitted when the user clicks to a notification action button. |
| ON_PUSH_MESSAGE_SENT            | Event emitted when an uplink message is sent.                |
| ON_PUSH_MESSAGE_SENT_ERROR      | Event emitted when there was an error with the uplink message to be sent. |
| ON_PUSH_MESSAGE_SENT_DELIVERED  | Event emitted when the uplink message is delivered.          |

Call Example

```js
HmsPushEvent.onTokenReceived = (result) =>
  new NativeEventEmitter()
    .addListener(HmsPushEvent.TOKEN_RECEIVED_EVENT, result);
```

#### Listeners

| Listener                                                     |
| ------------------------------------------------------------ |
| [onRemoteMessageReceived](#hmspusheventonremotemessagereceivedresult-) |
| [onTokenReceived](#hmspusheventontokenreceivedresult-)        |
| [onTokenError](#hmspusheventontokenerrorresult-)              |
| [onPushMessageSent](#hmspusheventonpushmessagesentresult-)    |
| [onPushMessageSentError](#hmspusheventonpushmessagesenterrorresult-) |
| [onPushMessageSentDelivered](#hmspusheventonpushmessagesentdeliveredresult-) |
| [onLocalNotificationAction](#hmspusheventonlocalnotificationactionresult-) |
| [onNotificationOpenedApp](#hmspusheventonnotificationopenedappresult-) |

##### HmsPushEvent.onRemoteMessageReceived((result) => {})

Listens for the HmsPushEvent.REMOTE_DATA_MESSAGE_RECEIVED events. The result is an object encapsulating the instance of the remoteMessage received in "**msg**" field.

Call Example

```js
HmsPushEvent.onRemoteMessageReceived((result) => {
  const RNRemoteMessageObj = new RNRemoteMessage(result.msg);
  HmsLocalNotification.localNotification({
    [HmsLocalNotification.Attr.title]: "DataMessage Received",
    [HmsLocalNotification.Attr.message]: RNRemoteMessageObj.getDataOfMap(),
  });
  this.log("onRemoteMessageReceived", result);
});
```

Example Response

```js
{ msg: {
    BadgeNumber: "null",
    ChannelId: null,
    ClickAction: null,
    Color: null,
    ImageUrl: "null",
    Importance: "0",
    LightSettings: "null",
    Link: "null",
    NotifyId: "0",
    Sound: null,
    Tag: null,
    Ticker: null,
    When: "null",
    body: null,
    bodyLocalizationArgs: "null",
    bodyLocalizationKey: null,
    collapseKey: null,
    contents: "0",
    data: "{"KEY1":"VALUE1"}",
    dataOfMap: "{KEY1=VALUE1}",
    from: "102032571",
    icon: null,
    intentUri: null,
    isAutoCancel: "true",
    isDefaultLight: "true",
    isDefaultSound: "true",
    isDefaultVibrate: "true",
    isLocalOnly: "true",
    messageId: "0",
    messageType: null,
    originalUrgency: "2",
    receiptMode: "0",
    sendMode: "0",
    sentTime: "0",
    title: null,
    titleLocalizationArgs: "null",
    titleLocalizationKey: null,
    to: null,
    token: "AH1YfzXEW5KWFIrjf_PGfSte5rOW9RdAr_xxxx",
    ttl: "2147483647",
    urgency: "2",
    vibrateConfig: "null",
    visibility: "null",
  }
}
```

##### HmsPushEvent.onTokenReceived((result) => {})

Listens for the HmsPushEvent.TOKEN_RECEIVED_EVENT events. The result is an object encapsulating the string representation of the token received in "**token**" field.

Call Example

```js
HmsPushEvent.onTokenReceived((result) => {
  this.log("[onTokenReceived]: " + result.token);
});
```

##### HmsPushEvent.onTokenError((result) => {})

Listens for the HmsPushEvent.ON_TOKEN_ERROR_EVENT events. The result is an object encapsulating the exception message in "**exception**" field.

Call Example

```js
HmsPushEvent.onTokenError((result) => {
  this.log("[onTokenError]: " + result.exception);
});
```

##### HmsPushEvent.onPushMessageSent((result) => {})

Listens for the HmsPushEvent.ON_PUSH_MESSAGE_SENT events. The result is an object encapsulating the message ID in "**msgId**" field.

Call Example

```js
HmsPushEvent.onPushMessageSent((result) => {
  this.log("[onPushMessageSent]: msgId:" + result.msgId);
});
```

Example Response

```js
{ msgId: "2132824630" }
```

##### HmsPushEvent.onPushMessageSentError((result) => {})

Listens for the HmsPushEvent.ON_PUSH_MESSAGE_SENT_ERROR events. The result is an object encapsulating the error code in "**result**", message ID in "**msgId**", and error info in "**resultInfo**" fields.

Call Example

```js
HmsPushEvent.onPushMessageSentError((result) => {
  this.log(
    "[onPushMessageSentError]:  msgId: " +
      result.msgId +
      ", result: " +
      result.result +
      ", resultInfo: " +
      result.resultInfo
  );
});
```

##### HmsPushEvent.onPushMessageSentDelivered((result) => {})

Listens for the HmsPushEvent.ON_PUSH_MESSAGE_SENT_DELIVERED events. The result is an object encapsulating the error code in "**result**", message ID in "**msgId**", and error info in "**resultInfo**" fields.

Call Example

```js
HmsPushEvent.onPushMessageSentDelivered((result) => {
  this.log(
    "[onPushMessageSentDelivered]:  msgId: " +
      result.msgId +
      ", result: " +
      result.result +
      ", resultInfo: " +
      result.resultInfo
  );
});
```

Example Response

```js
{ resultInfo: "success", msgId: "2132824630", result: "0" }
```

##### HmsPushEvent.onLocalNotificationAction((result) => {})

Listens for the HmsPushEvent.LOCAL_NOTIFICATION_ACTION_EVENT events. The result is the object encapsulating the dataJson of clicked notification.

Call Example

```js
HmsPushEvent.onLocalNotificationAction((result) => {
  this.log("[onLocalNotificationAction]: " + result);

  var notification = JSON.parse(result.dataJSON);
  if (notification.action === "Yes") {
    HmsLocalNotification.cancelNotificationsWithId([notification.id]);
  }
  this.log("Clicked: " + notification.action);
});
```
Example Response

```js
{ dataJSON: '{"subText":"This is a subText","invokeApp":false,"smallIcon":"ic_notification","action":"Yes","ongoing":false,"actions":"[\"Yes\", \"No\"]","ticker":"Optional Ticker","autoCancel":false,"showWhen":true,"vibrateDuration":1000,"bigText":"This is a bigText","id":"-1514910901","tag":null,"color":"white","title":"HMS Push","vibrate":false,"largeIcon":"ic_launcher","message":"This is Local Notification","groupSummary":false,"dontNotifyInForeground":false,"importance":"max"}' }
```

##### HmsPushEvent.onNotificationOpenedApp((result) => {})

Listens for the HmsPushEvent.NOTIFICATION_OPENED_EVENT events. The result is an object representing the clicked remoteMessage.

Call Example

```js
HmsPushEvent.onNotificationOpenedApp((result) => {
  this.log("[onNotificationOpenedApp]: " + JSON.stringify(result));
});
```

Example Response

```js
{
  result: {
    extras: {
      _push_cmd_type: "cosa",
      _push_msgid: "-1744601771",
      _push_notifyid: 1218295586,
      KEY1: "VALUE1",
    },
    remoteMessage: {
      Link: "null",
      NotifyId: "0",
      contents: "0",
      ttl: "86400",
      isDefaultSound: "true",
      ...
    },
    uriPage: "app://app2",
  },
  resultCode: "0",
}
```

### **HmsPushResultCode**

#### Constants

| Name                                      | ResultCode  | Description                                                  |
| ----------------------------------------- | ----------- | ------------------------------------------------------------ |
| SUCCESS                                   | "0"         | Success                                                      |
| ERROR                                     | "-1"        | Error                                                        |
| NULL_BUNDLE                               | "333"       | Bundle is null, exception                                    |
| ERROR_NO_TOKEN                            | "907122030" | You do not have a token. Apply for a token.                  |
| ERROR_NO_NETWORK                          | "907122031" | The current network is unavailable. Check the network connection. |
| ERROR_TOKEN_INVALID                       | "907122032" | The token has expired. Delete the token and apply for a new one. |
| ERROR_SERVICE_NOT_AVAILABLE               | "907122046" | If the Push service is unavailable, contact Huawei technical support. |
| ERROR_PUSH_SERVER                         | "907122047" | If the Push server returns an error, contact Huawei technical support. |
| ERROR_UNKNOWN                             | "907122045" | Unknown error. Contact Huawei technical support.             |
| ERROR_TOPIC_EXCEED                        | "907122034" | The number of subscribed topics exceeds 2000.                |
| ERROR_TOPIC_SEND                          | "907122035" | Failed to send the subscription topic. Contact Huawei technical support. |
| ERROR_NO_RIGHT                            | "907122036" | Push rights are not enabled. Enable the service and set push service parameters at AppGallery Connect. |
| ERROR_GET_TOKEN_ERR                       | "907122037" | Failed to apply for the token. Contact Huawei technical support. |
| ERROR_STORAGE_LOCATION_EMPTY              | "907122038" | No storage location is selected for the application or the storage location is invalid. |
| ERROR_NOT_ALLOW_CROSS_APPLY               | "907122053" | Failed to apply for a token. Cross-region token application is not allowed. |
| ERROR_SIZE                                | "907122041" | The message body size exceeds the maximum.                   |
| ERROR_INVALID_PARAMETERS                  | "907122042" | The message contains invalid parameters.                     |
| ERROR_TOO_MANY_MESSAGES                   | "907122043" | The number of sent messages reaches the upper limit. The messages will be discarded. |
| ERROR_TTL_EXCEED                          | "907122044" | The message lifetime expires before the message is successfully sent to the APP server. |
| ERROR_HMS_CLIENT_API                      | "907122048" | Huawei Mobile Services (APK) can't connect  Huawei Push  Kit. |
| ERROR_OPERATION_NOT_SUPPORTED             | "907122049" | The current EMUI version is too early to use the capability. |
| ERROR_MAIN_THREAD                         | "907122050" | The operation cannot be performed in the main thread.        |
| ERROR_HMS_DEVICE_AUTH_FAILED_SELF_MAPPING | "907122051" | The device certificate authentication fails.                 |
| ERROR_BIND_SERVICE_SELF_MAPPING           | "907122052" | Failed to bind the service.                                  |
| ERROR_ARGUMENTS_INVALID                   | "907122054" | The input parameter is incorrect. Check whether the related configuration information is correct. |
| ERROR_INTERNAL_ERROR                      | "907135000" | Internal Push error. Contact Huawei technical support engineers. |
| ERROR_NAMING_INVALID                      | "907135001" | Internal Push error. Contact Huawei technical support engineers. |
| ERROR_CLIENT_API_INVALID                  | "907135002" | The ApiClient object is invalid.                             |
| ERROR_EXECUTE_TIMEOUT                     | "907135003" | Invoking AIDL times out. Contact Huawei technical support.   |
| ERROR_NOT_IN_SERVICE                      | "907135004" | The current area does not support this service.              |
| ERROR_SESSION_INVALID                     | "907135005" | If the AIDL connection session is invalid, contact Huawei technical support. |
| ERROR_API_NOT_SPECIFIED                   | "907135006" | An error occurred when invoking an unspecified API.          |
| ERROR_GET_SCOPE_ERROR                     | "1002"      | Failed to invoke the gateway to query the application scope. |
| ERROR_SCOPE_LIST_EMPTY                    | "907135700" | Scope is not configured on the AppGallery Connect.           |
| ERROR_CERT_FINGERPRINT_EMPTY              | "907135701" | The certificate fingerprint is not configured on the AppGallery Connect. |
| ERROR_PERMISSION_LIST_EMPTY               | "907135702" | Permission is not configured on the AppGallery Connect.      |
| ERROR_AUTH_INFO_NOT_EXIST                 | "907135703" | The authentication information of the application does not exist. |
| ERROR_CERT_FINGERPRINT_ERROR              | "6002"      | An error occurred during certificate fingerprint verification. Check whether the correct certificate fingerprint is configured in AppGallery Connect. |
| ERROR_PERMISSION_NOT_EXIST                | "6003"      | Interface authentication: The permission does not exist and is not applied for in AppGallery Connect. |
| ERROR_PERMISSION_NOT_AUTHORIZED           | "6005"      | Interface authentication: unauthorized.                      |
| ERROR_PERMISSION_EXPIRED                  | "6006"      | Interface authentication: The authorization expires.         |

Call Example

```js
import {
  HmsPushResultCode
} from '@hmscore/react-native-hms-push';
...
HmsPushOpenDevice.getOdid()
  .then((result) => {
    this.log("getOdid", result.resultCode === HmsPushResultCode.SUCCESS);
  })
  .catch((err) => {
    alert("[getOdid] Error/Exception: " + JSON.stringify(err));
  });
```

---

## 4. Configuration and Description

### Receiving Data Messages at Background/Killed Application State

To listen to **Data Messages** in the foreground, call the [**onRemoteMessageReceived**](#hmspusheventonremotemessagereceivedresult-) method inside of your application. Code executed via this handler has access to React context and is able to interact with your application.

When the application is in a background or killed state, the [**onRemoteMessageReceived**](#hmspusheventonremotemessagereceivedresult-) handler will not be called when receiving data messages. Instead, you need to setup a background callback handler via the [**setBackgroundMessageHandler**](#hmspushmessagingsetbackgroundmessagehandlercallback) method.

To setup a background handler, call the [**setBackgroundMessageHandler**](#hmspushmessagingsetbackgroundmessagehandlercallback) outside of your application logic as early as possible:

```js
//index.js

import App from "./App";
import { name as appName } from "./app.json";

AppRegistry.registerComponent(appName, () => App);

HmsPushEvent.onNotificationOpenedApp((result) => {
  console.log("onNotificationOpenedApp", result);
});

HmsPushMessaging.setBackgroundMessageHandler((dataMessage) => {
  HmsLocalNotification.localNotification({
    [HmsLocalNotification.Attr.title]: "[Headless] DataMessage Received",
    [HmsLocalNotification.Attr.message]: new RNRemoteMessage(
      dataMessage
    ).getDataOfMap(),
  })
    .then((result) => {
      console.log("[Headless] DataMessage Received", result);
    })
    .catch((err) => {
      console.log(
        "[LocalNotification Default] Error/Exception: " + JSON.stringify(err)
      );
    });

  return Promise.resolve();
});
```

Check the sample project of the plugin for a working demonstration.


### Receiving Custom Intent URI
To receive the custom intent URIs you need to declare an intent filter inside **AndroidManifest.xml** file as shown below. Replace the value for the **android:scheme** with your custom scheme (for example: myapp). 

```xml
<manifest ...>
  <!-- Other configurations -->
  <application ...>
    <activity ...>
      <!-- Other configurations -->

      <!-- The Intent filter below is for receiving custom intents-->
      <intent-filter>
          <action android:name="android.intent.action.VIEW" />
          <category android:name="android.intent.category.DEFAULT" />
          <category android:name="android.intent.category.BROWSABLE" />
          <data android:scheme="<YOUR_SCHEME>"/>
      </intent-filter>
    </activity>
  </application>
</manifest>
```
For further details about this configuration please check the [Android developer documentation for deep links](https://developer.android.com/training/app-links/deep-linking).

### Auto-Initialization

The HMS Core Push SDK provides the capability of automatically generating AAIDs and automatically applying for tokens. After this capability is configured, the applied token is returned through the [**onTokenReceived**](#hmspusheventontokenreceivedresult-) listener. You can configure automatic initialization by adding the meta-data section to the AndroidManifest.xml file or calling the [**setAutoInitEnabled**](#hmspushmessagingsetautoinitenabledenabled) method from the Push SDK.

To enable Auto-Initialization with the configuration, add the meta-data section under application tag in the **AndroidManifest.xml** file.

```xml
<manifest ...>
    <!-- Other configurations -->
    <application ...>
        <!-- Setting push kit auto enable to true -->
        <meta-data
            android:name="push_kit_auto_init_enabled"
            android:value="true" />
        <!-- Other configurations -->
    </application>
</manifest>
```

### Local Notification Configurations

Scheduled local notifications use the Android Alarm Manager in order to be pushed at the specified time. Permissions and receivers that shown below should be added to the **AndroidManifest.xml** file of your project for scheduling local notifications.

**Example AndroidManifest.xml file**
```xml
<manifest .../>
    <!-- Other configurations -->
    <!-- Add the permissions below for local notifications -->
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
    <application ...>
        <!-- These receivers are for sending scheduled local notifications -->
        <receiver android:name="com.huawei.hms.rn.push.receiver.HmsLocalNotificationBootEventReceiver">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
            </intent-filter>
        </receiver>
        <receiver android:name="com.huawei.hms.rn.push.receiver.HmsLocalNotificationScheduledPublisher"
            android:enabled="true"
            android:exported="true">
        </receiver>
    </application>
</manifest>
```

#### Vibration

In order to customize the vibration pattern of the local notifications you need to add the permission below to **AndroidManifest.xml** file

```xml
<uses-permission android:name="android.permission.VIBRATE" />
```

#### Playing Custom Sound

For playing a custom sound for a local notification you should add your sound file as a raw resource. The path for raw resources as follows: **<project_folder>/android/app/src/main/res/raw** 

Check the [sample project](example) of the plugin for a working demonstration.

---

## 5. Sample Project

This plugin includes a demo project in the [example](example) folder, there you can find more usage examples.

<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-push/example/.docs/mainPage.jpg" width = 45% height = 45% style="margin:1em"><img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-push/example/.docs/localNotification.jpg" width = 45% height = 45% style="margin:1em">

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
