# React-Native HMS Account

---

## Contents

  - [1. Introduction](#1-introduction)
  - [2. Installation Guide](#2-installation-guide)
    - [Creating a Project in App Gallery Connect](#creating-a-project-in-appgallery-connect)
    - [Configuring the Signing Certificate Fingerprint](#configuring-the-signing-certificate-fingerprint)
    - [Integrating the React-Native Account Plugin](#integrating-react-native-account-plugin)
  - [3. API Reference](#3-api-reference)
    - [HMSAccount](#hmsaccount)
    - [HMSHuaweiIdAuthManager](#hmshuaweiidauthmanager)
    - [HMSHuaweiIdAuthTool](#hmshuaweiidauthtool)
    - [HMSNetworkTool](#hmsnetworktool)
    - [HMSReadSMSManager](#hmsreadsmsmanager)
    - [HMSHuaweiIdAuthButton](#hmshuaweiidauthbutton)
    - [Constants](#constants)
        - [HMSAuthRequestOptionConstants](#hmsauthrequestoptionconstants)
        - [HMSAuthScopeListConstants](#hmsauthscopelistconstants)
        - [HMSAuthParamConstants](#hmsauthparamconstants)
    - [Data Types](#data-types)
        - [SignInData](#signindata)
        - [SilentSignInData](#silentsignindata)
        - [AuthHuaweiIdBuilder](#authhuaweiidbuilder)
        - [AuthHuaweiId](#authhuaweiid)
        - [AuthScopeData](#authscopedata)
        - [ContainScopesData](#containscopesdata)
        - [AccessTokenData](#accesstokendata)
        - [AccountData](#accountdata)
        - [RequestAccessTokenData](#requestaccesstokendata)
        - [CookieData](#cookiedata)
        - [UrlData](#urldata)
        - [HuaweiAccount](#huaweiaccount)
        - [HashCodeResult](#hmscoderesult)
        - [SmsMessageResult](#smsmessageresult)
    - [Error Codes](#error-codes)
  - [4. Configuration and Description](#4-configuration-and-description)
  - [5. Sample Project](#5-sample-project)
  - [6. Questions or Issues](#6-questions-or-issues)
  - [7. Licensing and Terms](#7-licensing-and-terms)

---

## 1. Introduction

This plugin enables communication between Huawei Account kit and React Native platform.


- Android: core layer, bridging Huawei's Account Kit bottom-layer code.
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

### Integrating React Native Account Plugin

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
npm i @hmscore/react-native-hms-account
```

**Step 2:**  Run your project. 

- Run the following command to the project directory.

```bash
react-native run-android  
```

#### Option 2: Download Link

To integrate the plugin, follow the steps below:

**Step 1:** Download the React Native Account Plugin and place **react-native-hms-account** under **node_modules/@hmscore** of your React Native project, as shown in the directory tree below.

```
demo-app
  |_ node_modules
    |_ @hmscore
      |_ react-native-hms-account
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
    implementation project(":react-native-hms-account")    
    ...    
  }
}
```

**Step 3:** Add the following lines to the **android/settings.gradle** file in your project:

```groovy
include ':app'
include ':react-native-hms-account'
project(':react-native-hms-account').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-account/android')
```

**Step 4:**  Import the following classes to the **MainApplication.java** file of your project.

```java
import com.huawei.hms.rn.account.HmsAccountPackage
```

Then, add the **HmsAccountPackage()** to your **getPackages** method. In the end, your file will be similar to the following:

```java 
import com.huawei.hms.rn.account.HmsAccountPackage;
...
@Override
protected List<ReactPackage> getPackages() {
    List<ReactPackage> packages = new PackageList(this).getPackages();
    packages.add(new HmsAccountPackage()); // <-- Add this line 
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

### **HMSAccount**

#### Public Method Summary

| Method                                                       | Return Type                             | Description                                                  |
| ------------------------------------------------------------ | --------------------------------------- | ------------------------------------------------------------ |
| [signIn(signInData)](#hmsaccountsigninsignindata)            | Promise <[AuthHuaweiId](#authhuaweiid)> | Obtains the intent of the HUAWEI ID sign-in authorization page. |
| [signOut()](#hmsaccountsignout)                              | Promise \<void>                         | Implements sign-out.                                         |
| [silentSignIn(silentSignInData)](#hmsaccountsilentsigninsilentsignindata) | Promise <[AuthHuaweiId](#authhuaweiid)> | Obtains the sign-in information (or error information) about the HUAWEI ID that has been used to sign in to the app. |
| [cancelAuthorization()](#hmsaccountcancelauthorization)      | Promise \<void>                         | Cancels the authorization.                                   |
| [enableLogger()](#hmsaccountenablelogger)                    | void                                    | Enables HMS Plugin Method Analytics.                         |
| [disableLogger()](#hmsaccountdisablelogger)                  | void                                    | Disables HMS Plugin Method Analytics.                        |


### 3.1.2 Public Methods

##### HMSAccount.signIn(signInData)

This method is used to obtain the Intent of the authorization page. signIn method takes a signInData object as a parameter and returns a AuthHuaweiId object if the sign-in is successfully completed.

Parameters

| Name       | Type                      | Description                                                  |
| ---------- | ------------------------- | ------------------------------------------------------------ |
| signInData | [SignInData](#signindata) | SignInData object that includes huaweiIdAuthParams, authRequestOption and authScopeList fields for sign-in in huawei account. |

Return Type

| Type                                    | Description                                                  |
| --------------------------------------- | ------------------------------------------------------------ |
| Promise<[AuthHuaweiId](#authhuaweiid) > | Returns the [AuthHuaweiId](#authhuaweiid) object if the operation is successful. |


Call Example :

```js
let signInData = {
    huaweiIdAuthParams: HMSAuthParamConstants.DEFAULT_AUTH_REQUEST_PARAM,
    authRequestOption: [HMSAuthRequestOptionConstants.ID_TOKEN, HMSAuthRequestOptionConstants.EMAIL],
};
HMSAccount.signIn(signInData)
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```

Example Response

```js
{ account: { type: 'com.huawei', name: 'myEmail@xxx.com' }, extensionScopes: [], status: 0, describeContentsInAuthHuaweiId: 0, gender: -1, openId: 'MDFAMTAyOTk5NDI3QDI3MDNlZDg4YmZmMTYzZGVkNDhjYmUzYjlhZTYzY2NlQDVhMGI4Y2RlZTNiaOGM0MzQ3NDRmOWIyMGUzNmQzNmUxOTUyYxxxxxx', serviceCountryCode: null, expressionTimeSecs: 0, ageRange: null, unionId: 'MDEqcOV544cP6RFdWj4RBhdqK3s5zibYvwxxxxx', authorizationCode: null, familyName: '', displayName: 'betty', authorizedScopes: ['openid', 'profile', 'email'], uid: null, avatarUriString: 'https://upfile7.hicloud.com/FileServer/image/b.5190090000026859775.20201021133953.Gpd7AKr6gltQNNipzp8A6c5JD6UzF2W7.0001.hwid.null.9F7ACB512C3C7B4C5744C1217E3BCE3B06xxxxxxx.jpg', countryCode: null, idToken: 'eyJraWQiOiJmMzY3ZDYyMzZmMzdkOWQyOGMzNWMyYzIyZDM3NjlmMWI2YTFiNjZhZjhlMDFhOGE0MDU3N2JmNmJmYjYxZTRkIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJhdF9oYXNoIjoid0VhQ2ZPckhwRlpmcmtvb2RmM19OdyIsInN1YiI6Ik1ERXFjT1Y1NDRjUDZSRmRXajRSQmhkcUszczV6aWJZdndZYnRaRko2QVN0QTdRIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImlzcyI6Imh0dHBzOi8vYWNjb3VudHMuaHVhd2VpLmNvbSIsImdpdmVuX25hbWUiOiJCZXR1bCIsImRpc3BsYXlfbmFtZSI6ImJldHR5Iiwibm9uY2UiOiIwZjU1YWI3NS1lMzI4LTRhNGItYTA3OC05Njc2N2Y2YzczYjIiLCJwaWN0dXJlIjoiaHR0cHM6Ly91cGZpbGU3LmhpY2xvdWQuY29tL0ZpbGVTZXJ2ZXIvaW1hZ2UvYi41MTkwMDkwMDAwMDI2ODU5Nzc1LjIwMjAxMDIxMTMzOTUzLkdwZDdBS3I2Z2x0UU5OaXB6cDhBNmM1SkQ2VXpGMlc3LjEwMDAuREM2MkZGMzk5MDIxNUExNDAxOEM0QTQ1QzdGQUY1NjZEQTk1RjhCMkI1MzRDNUQzRjU0ODU3Q0IzNTxxxxxxxxxxxxxxxxxx', givenName: 'Betxx', email: 'myEmail@xxx.com', accessToken: null }

```

##### HMSAccount.signOut()

This method is used to sign out a HUAWEI ID. The HMS Core Account SDK deletes the cached HUAWEI ID information.

Return Type

| Type            | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| `Promise<void>` | If the operation is successful, sign-out will perform. Otherwise it will throw an error. |

Call Example

```js
HMSAccount.signOut()
    .catch((err) => {
        console.log(err)
    });
```

##### HMSAccount.silentSignIn(silentSignInData)

Obtains the sign-in information (or error information) about the HUAWEI ID that has been used to sign in to the app. In this process, the authorization page is not displayed to the HUAWEI ID user. silentSignIn method takes a silentSignInData object as a parameter and returns a AuthHuaweiId object if the silent sign-in is successfully completed.

Parameters

| Name             | Type                                  | Description                                                  |
| ---------------- | ------------------------------------- | ------------------------------------------------------------ |
| silentSignInData | [SilentSignInData](#silentsignindata) | silentSignInData object that includes field for silent sign-in in huawei account |

Return Type

| Type                                    | Description                                                  |
| --------------------------------------- | ------------------------------------------------------------ |
| Promise<[AuthHuaweiId](#authhuaweiid) > | Returns the [AuthHuaweiId](#authhuaweiid) object if the operation is successful. |


Call Example

```js
let silentSignInData = {
    huaweiIdAuthParams: HMSAuthParamConstants.DEFAULT_AUTH_REQUEST_PARAM,
};
HMSAccount.silentSignIn(silentSignInData)
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```

Example Response

```js
{ account: null, extensionScopes: [], status: 0, describeContentsInAuthHuaweiId: 0, gender: -1, openId:'MDFAMTAyOTk5NDI3QDI3MDNlZDg4YmZmMTYzZGVkNDhjYmUzYjlhZTYzY2NlQDVhMGI4Y2RlZTNiaOGM0MzQ3NDRmOWIyMGUzNmxxxxxx', serviceCountryCode: null, expressionTimeSecs: 0, ageRange: null, unionId: 'MDEqcOV544cP6RFdWj4RBhdqK3s5zibxxxx', authorizationCode: null, familyName: '', displayName: 'betty', authorizedScopes: ['openid', 'profile', 'email'], uid: null, avatarUriString: 'https://upfile7.hicloud.com/FileServer/image/b.5190090000026859775.20201021133953.Gpd7AKr6gltQNNipzp8A6c5JD6UzF2W7.0001.hwid.null.9F7ACB512C3C7B4C5744C1217E3BCE3B06D7xxxx.jpg', countryCode: null, idToken: null, givenName: 'Betxx', email: null, accessToken: null }
```

##### HMSAccount.cancelAuthorization()

cancelAuthorization method is called to revoking the authorization of the HUAWEI ID

Return Type

| Type            | Description                                                  |
| --------------- | ------------------------------------------------------------ |
| `Promise<void>` | If the operation is successful, revoking authorizarion will perform. Otherwise it will throw an error. |

Call Example

```js
HMSAccount.cancelAuthorization()
    .catch((err) => {
        console.log(err)
    });
```

##### HMSAccount.enableLogger()

This method enables HMSLogger capability which is used for sending usage analytics of Account SDK's methods to improve the service quality.

Return Type

| Type   | Description                                          |
| ------ | ---------------------------------------------------- |
| `void` | If the operation is successful, a logger is enabled. |

Call Example

```js
HMSAccount.enableLogger()
```


##### HMSAccount.disableLogger()

This method disables HMSLogger capability which is used for sending usage analytics of Account SDK's methods to improve the service quality.

Return Type

| Type   | Description                                           |
| ------ | ----------------------------------------------------- |
| `void` | If the operation is successful, a logger is disabled. |

Call Example

```js
HMSAccount.disableLogger()
```

### **HMSHuaweiIdAuthManager**

#### Public Method Summary

| Method                                                       | Return Type                             | Description                                                  |
| ------------------------------------------------------------ | --------------------------------------- | ------------------------------------------------------------ |
| [getAuthResult() ](#hmshuaweiidauthmanagergetauthresult)     | Promise\<[AuthHuaweiId](#authhuaweiid)> | Obtains the information about the HUAWEI ID in the latest sign-in. |
| [getAuthResultWithScopes(authScopeData)](#hmshuaweiidauthmanagergetauthresultwithscopesauthscopedata) | Promise\<[AuthHuaweiId](#authhuaweiid)> | Obtains an AuthHuaweiId instance.                            |
| [addAuthScopes(authScopeData)](#hmshuaweiidauthmanageraddauthscopesauthscopedata) | Promise\<boolean>                       | Requests the permission specified by [authScopeList](#hmsauthscopelistconstants) from a HUAWEI ID. |
| [containScopes(containScopesData)](#hmshuaweiidauthmanagercontainscopescontainscopesdata) | Promise\<boolean>                       | Checks whether a specified HUAWEI ID has been assigned all permission specified by [authScopeList](#hmsauthscopelistconstants). |

##### HMSHuaweiIdAuthManager.getAuthResult()

Obtains the information about the HUAWEI ID in the latest sign-in. 

Return Type

| Type                                   | Description                                                  |
| -------------------------------------- | ------------------------------------------------------------ |
| Promise<[AuthHuaweiId](#authhuaweiid)> | Returns the [AuthHuaweiId](#authhuaweiid) object if the operation is successful. |

Call Example

```js
HMSHuaweiIdAuthManager.getAuthResult()
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```

Example Response

```js
{ account: { type: 'com.huawei', name: 'myEmail@xxx.com' }, extensionScopes: [], status: 0, describeContentsInAuthHuaweiId: 0, gender: -1, openId: 'MDFAMTAyOTk5NDI3QDI3MDNlZDg4YmZmMTYzZGVkNDhjYmUzYjlhZTYzY2NlQDVhMGI4Y2RlZTNiaOGM0MzQ3NDRmOWIyMGUzNmQzNmUxOTUyYjJmYmYzMDVkZmE4YWQzNDc4ZWVlNjM', serviceCountryCode: null, expressionTimeSecs: 0, ageRange: null, unionId: 'MDEqcOV544cP6RFdWj4RBhdqK3s5zibYvwYbtZFJ6AStA7Q', authorizationCode: null, familyName: '', displayName: 'betty', authorizedScopes: ['openid', 'profile', 'email'], uid: null, avatarUriString: 'https://upfile7.hicloud.com/FileServer/image/b.5190090000026859775.20201021133953.Gpd7AKr6gltQNNipzp8A6c5JD6UzF2W7.0001.hwid.null.9F7ACB512C3C7B4C5xxxxxx.jpg', countryCode: null, idToken: 'eyJraWQiOiJmMzY3ZDYyMzZmMzdkOWQyOGMzNWMyYzIyZDM3NjlmMWI2YTFiNjZhZjhlMDFhOGE0MDU3N2JmNmJmYjYxZTRkIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJhdF9oYXNoIjoid0VhQ2ZPckhwRlpmcmtvb2RmM19OdyIsInN1YiI6Ik1ERXFjT1Y1NDRjUDZSRmRXajRSQmhkcUszczV6aWJZdndZYnRaRko2QVN0QTdRIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImlzcyI6Imh0dHBzOi8vYWNjb3VudHMuaHVhd2VpLmNvbSIsImdpdmVuX25hbWUiOiJCZXR1bCIsImRpc3BsYXlfbmFtZSI6ImJxxxxxxx', givenName: 'Betxx', email: 'myEmail@xxx.com', accessToken: null }
```

##### HMSHuaweiIdAuthManager.getAuthResultWithScopes(authScopeData)

Obtains an [AuthHuaweiId](#authhuaweiid) instance with given [HMSAuthScopeListConstants](#hmsauthscopelistconstants) data.

Parameters

| Name          | Type                            | Description                                                  |
| ------------- | ------------------------------- | ------------------------------------------------------------ |
| authScopeData | [AuthScopeData](#authscopedata) | authScopeData object includes [authScopeList](#hmsauthscopelistconstants) |

Return Type

| Type                                   | Description                                                  |
| -------------------------------------- | ------------------------------------------------------------ |
| Promise<[AuthHuaweiId](#authhuaweiid)> | Returns the [AuthHuaweiId](#authhuaweiid) object if the operation is successful. |


Call Example

```js
let authScopeData = {
    authScopeList: [HMSAuthScopeListConstants.OPENID, HMSAuthScopeListConstants.PROFILE],
};
HMSHuaweiIdAuthManager.getAuthResultWithScopes(authScopeData)
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```

Example Response

```js
{ account: { type: 'com.huawei', name: 'myEmail@xxx.com' }, extensionScopes: ['openid', 'profile'], status: 0, describeContentsInAuthHuaweiId: 0, gender: -1, openId: 'MDFAMTAyOTk5NDI3QDI3MDNlZDg4YmZmMTYzZGVkNDhjYmUzYjlhZTYzY2NlQDVhMGI4Y2RlZTNiaOGM0MzQ3NDRxxxx', serviceCountryCode: null, expressionTimeSecs: 0, ageRange: null, unionId: 'MDEqcOV544cP6RFdWj4RBhdqK3s5xxxx', authorizationCode: null, displayName: 'betty', authorizedScopes: ['openid', 'profile', 'email'], uid: null, avatarUriString: 'https://upfile7.hicloud.com/FileServer/image/b.5190090000026859775.20201021133953.Gpd7AKr6gltQNNipzp8A6c5JD6UzF2W7.0001.hwid.null.9F7ACB512C3C7B4C5744C1217Exxxxxx.jpg', countryCode: null, idToken: 'eyJraWQiOiJmMzY3ZDYyMzZmMzdkOWQyOGMzNWMyYzIyZDM3NjlmMWI2YTFiNjZhZjhlMDFhOGE0MDU3N2JmNmJmYjYxZTRkIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJhdF9oYXNoIjoiMGNiMW9IWWlqQVhnREl5bmNWR0JKZyIsInN1YiI6Ik1ERXFjT1Y1NDRjUDZSRmRXajRSQmhkcUszczV6aWJZdndZYnRaRko2QVN0QTdRIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImlzcyI6Imh0dHBzOi8vYWNjb3VudHMuaHVhd2VpLmNvbSIsImdpdmVuX25hbWUiOiJCZXR1bCIsImRpc3BsYXlfbmFtZSI6ImJldHR5Iiwibm9uY2UiOiI1NTY0YzQ4Yy1kMDY4LTQyZDQtYWRiNy0wMzNlNWVjZGZjMmUiLCJwaWN0dXJlIjoiaHR0cHM6Ly91cGZpbGU3LmhpY2xvdWQuY29tL0ZpbGVTZXJ2ZXIvaW1hZ2UvYi41MTkwMDkwMDAwMDI2ODU5Nzc1LjIwMjAxMDIxMTMzOTUxxxxxx', givenName: 'Betxx', email: 'myEmail@xxx.com', accessToken: null }
```

##### HMSHuaweiIdAuthManager.addAuthScopes(authScopeData)

Requests the permission specified by [authScopeList](#hmsauthscopelistconstants) from a HUAWEI ID. 

Parameters

| Name          | Type                            | Description                                                  |
| ------------- | ------------------------------- | ------------------------------------------------------------ |
| authScopeData | [AuthScopeData](#authscopedata) | authScopeData object includes [authScopeList](#hmsauthscopelistconstants) field |


Return Type

| Type               | Description                             |
| ------------------ | --------------------------------------- |
| `Promise<boolean>` | Returns true on a successfull operation |

Call Example

```js
let authScopeData = {
    authScopeList: [HMSAuthScopeListConstants.EMAIL],
};
HMSHuaweiIdAuthManager.addAuthScopes(authScopeData)
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```

Example Response

```js
true
```

##### HMSHuaweiIdAuthManager.containScopes(containScopesData)

Checks whether a specified HUAWEI ID has been assigned all permission specified by [authScopeList](#hmsauthscopelistconstants).

Parameters

| Name              | Type                                    | Description                                                  |
| ----------------- | --------------------------------------- | ------------------------------------------------------------ |
| containScopesData | [ContainScopesData](#containscopesdata) | ContainScopesData object includes [AuthHuaweiId](#authhuaweiid) and [authScopeList](#hmsauthscopelistconstants) fields. |

Return Type

| Type               | Description                             |
| ------------------ | --------------------------------------- |
| `Promise<boolean>` | Returns true on a successfull operation |

Call Example

```js
let containScopesData = {
    authHuaweiId: {
        openId: "myopenid",
        uid: "myuid",
        displayName: "mydisplayname",
        photoUrl: "myphotourl",
        accessToken: "myaccesstoken",
        serviceCountryCode: "myservicecountrycode",
        status: 0,
        gender: 0,
        authScopeList: [HMSAuthScopeListConstants.OPENID, HMSAuthScopeListConstants.PROFILE, HMSAuthScopeListConstants.EMAIL],
        serverAuthCode: "myserverAuthCode",
        unionId: "myunionId",
        countryCode: "myCountryCode",
    },
    authScopeList: [HMSAuthScopeListConstants.OPENID, HMSAuthScopeListConstants.PROFILE],
};
HMSHuaweiIdAuthManager.containScopes(containScopesData)
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });

```

Example Response

```js
true
```

### **HMSHuaweiIdAuthTool**

#### Public Method Summary

| Method                                                       | Return Type       | Description             |
| ------------------------------------------------------------ | ----------------- | ----------------------- |
| [deleteAuthInfo(accesTokenData)](#hmshuaweiidauthtooldeleteauthinfoaccestokendata) | Promise\<boolean> | Clears the local cache. |
| [requestUnionId(accountData)](#hmshuaweiidauthtoolrequestunionidaccountdata) | Promise\<string>  | Obtains a UnionID.      |
| [requestAccessToken(requestAccessTokenData)](#hmshuaweiidauthtoolrequestaccesstokenrequestaccesstokendata) | Promise\<string>  | Obtains a token.        |


##### HMSHuaweiIdAuthTool.deleteAuthInfo(accesTokenData)

Clears the local cache.

Parameters

| Name           | Type                              | Description                                                  |
| -------------- | --------------------------------- | ------------------------------------------------------------ |
| accesTokenData | [AccesTokenData](#accestokendata) | accesTokenData object includes accessToken that belongs to be deleted HUAWEI ID |

Return Type

| Type               | Description                             |
| ------------------ | --------------------------------------- |
| `Promise<boolean>` | Returns true on a successfull operation |

Call Example

```js
let accesTokenData = {
    accessToken: "CgB6e3x9vFRwdhMna2oMrI/LRYIT5WTMMPlUCUzSt4eh+zkCJVDPODpjqXwN0DTcNuuVf2zixqOxxxxx",
};
HMSHuaweiIdAuthTool.deleteAuthInfo(accesTokenData)
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```

Example Response

```js
true
```

##### HMSHuaweiIdAuthTool.requestUnionId(accountData)

Obtains a UnionID. 

Parameters

| Name        | Type                        | Description                                          |
| ----------- | --------------------------- | ---------------------------------------------------- |
| accountData | [AccountData](#accountdata) | accountData object includes huaweiAccountName field. |

Return Type

| Type              | Description                                                 |
| ----------------- | ----------------------------------------------------------- |
| `Promise<string>` | Returns the `unionId` value if the operation is successful. |

Call Example

```js
let accountData = {
    huaweiAccountName: "huawei@huawei.com",
};
HMSHuaweiIdAuthTool.requestUnionId(accountData)
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```


Example Response

```js
MDEqcOV544cP6RFdWj4RBhdqK3s5zibYvwxxxxx
```

##### HMSHuaweiIdAuthTool.requestAccessToken(requestAccessTokenData)

Obtains a token.

Parameters

| Name                   | Type                                              | Description                                                  |
| ---------------------- | ------------------------------------------------- | ------------------------------------------------------------ |
| requestAccessTokenData | [RequestAccessTokenData](#requestaccesstokendata) | requestAccessTokenData object includes [authScopeList](#hmsauthscopelistconstants) and [huaweiAccount](#huaweiaccount) fields. |


Return Type

| Type              | Description                                                  |
| ----------------- | ------------------------------------------------------------ |
| `Promise<string>` | Returns the `accessToken` value if the operation is successful. |

Call Example

```js
let requestAccessTokenData = {
    authScopeList: [HMSAuthScopeListConstants.OPENID, HMSAuthScopeListConstants.PROFILE],
    huaweiAccount: {
        name: "huawei@huawei.com",
        type: "com.huawei.hwid",
    },
};
HMSHuaweiIdAuthTool.requestAccessToken(requestAccessTokenData)
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```
Example Response

```js
CgB6e3x9vFRwdhMna2oMrI/LRYIT5WTMMPlUCUzSt4eh+zkCJVDPODpjqXwN0DTcNuuVf2zixqOxxxxx
```

### **HMSNetworkTool**

#### Public Method Summary

| Method                                                       | Return Type      | Description                                                  |
| ------------------------------------------------------------ | ---------------- | ------------------------------------------------------------ |
| [buildNetWorkCookie(cookieData)](#hmsnetworktoolbuildnetworkcookiecookiedata) | Promise\<string> | Constructs a cookie by combining entered values.             |
| [buildNetworkUrl(urlData)](#hmsnetworktoolbuildnetworkurlurldata) | Promise\<string> | Obtains a cookie URL based on the domain name and isUseHttps. |


##### HMSNetworkTool.buildNetworkCookie(cookieData)

Constructs a cookie by combining entered values.

Parameters

| Name | Type | Description |
| ---- | ---- | ----------- |
| cookieData| [CookieData](#cookiedata) | cookieData includes cookieName, cookieValue, domain, path, isHttpOnly, isSecure, maxAge fields.

Return Type

| Type              | Description                       |
| ----------------- | --------------------------------- |
| `Promise<string>` | Returns the combined cookie data. |


Call Example

```js
let cookieData = {
    cookieName: "mycookiename",
    cookieValue: "mycookievalue",
    domain: "mydomain",
    path: "mypath",
    isHttpOnly: true,
    isSecure: true,
    maxAge: 130,
};
HMSNetworkTool.buildNetworkCookie(cookieData)
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```

Example Response

```js
mycookiename=mycookievalue;HttpOnly;Secure;Domain=mydomain;Path=mypath;Max-Age=130
```

##### HMSNetworkTool.buildNetworkUrl(urlData)

Obtains a cookie URL based on the domain name and isUseHttps.

Parameters

| Name    | Type                | Description                                           |
| ------- | ------------------- | ----------------------------------------------------- |
| urlData | [UrlData](#urldata) | urlData object includes domain and isUseHttps fields. |

Return Type

| Type              | Description           |
| ----------------- | --------------------- |
| `Promise<string>` | Returns a cookie URL. |

Call Example

```js
let urlData = {
    isUseHttps: true,
    domain: "mydomain",
};
HMSNetworkTool.buildNetworkUrl(urlData)
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```

Example Response

```js
https://mydomain
```

### **HMSReadSMSManager**

#### Public Method Summary

| Method                                           | Return Type                                    | Description                                                  |
| ------------------------------------------------ | ---------------------------------------------- | ------------------------------------------------------------ |
| [smsVerificationCode()](#hmssmsverificationcode) | Promise<[SmsMessageResult](#smsmessageresult)> | Use this method to start smsVerificationCode that listens for verification the SMS broadcasts. |
| [getHashCode()](#hmsreadsmsmanagergethashcode)   | Promise<[HashCodeResult](#hashcoderesult)>     | Use this method to obtain a Hashcode. The hash code is added at the end of the verification SMS. |


##### HMSReadSMSManager.smsVerificationCode()

With this function, app can automatically retrieve SMS verification codes without requesting the permission of reading SMS messages. In case of get SMS Message, returned the SMS Message.

Return Type

| Type                                           | Description                                                  |
| ---------------------------------------------- | ------------------------------------------------------------ |
| Promise<[SmsMessageResult](#smsmessageresult)> | Returns the SmsMessageResult object that contains SMS content on a successful operation. |

Call Example

```js
HMSReadSMSManager.smsVerificationCode()
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```

Example Response

```js
{ Message: '<#> Account verification code is 123456 Aa4+BmA0###', Status: { statusCode: 0, statusMessage: null, errorCode: null } }
```

##### HMSReadSMSManager.getHashCode()

Obtains hash code which indicates the hash value generated by the HMS SDK based on app package name to uniquely identify app.

Return Type

| Type                                       | Description                                                  |
| ------------------------------------------ | ------------------------------------------------------------ |
| Promise<[HashCodeResult](#hashcoderesult)> | Returns the HashCodeResult object that contains a hash value on a successful operation. |

Call Example

```js
HMSReadSMSManager.getHashCode()
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.log(err)
    });
```

Example Response

```js
{ base64Hash: 'Aa4+BmA03Vz' }
```

### **HMSHuaweiIdAuthButton**

 Visual button that can be used authorization by using required function.

| Prop           | Type    | Description                                                  |
| -------------- | ------- | ------------------------------------------------------------ |
| `colorPolicy`  | number  | Sets the color of the button. [HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_BLUE](#classconstants), [HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_RED](#classconstants) and other options in [classconstants](#classconstants) can be used as a color. |
| `enabled`      | boolean | This boolean property determines if the button is enabled. True value indicates that the button is enabled, the button is disabled if the value is set to false. |
| `theme`        | number  | Sets the theme of the button. [HUAWEI_ID_AUTH_BUTTON_THEME_NO_TITLE](#classconstants) and [HUAWEI_ID_AUTH_BUTTON_THEME_FULL_TITLE](#classconstants) can be used as a theme values. |
| `cornerRadius` | number  | Sets the corner radius size. [HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_LARGE](#classconstants), [HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_MEDIUM](#classconstants) and [HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_SMALL](#classconstants) can be used as a values of corner radius. |


Call Example

```js

signIn = () => {
    let signInData = {
        huaweiIdAuthParams: HMSAuthParamConstants.DEFAULT_AUTH_REQUEST_PARAM,
        authRequestOption: [HMSAuthRequestOptionConstants.ID_TOKEN],
    };
    HMSAccount.signIn(signInData)
        .then((response) => {
            console.log(response)
        })
        .catch((err) => {
            console.log(err)
        });
};

  <HMSAuthButton
    style={styles.viewcontainer}
    colorPolicy={
      HMSAccount.HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_RED
    }
    enabled={true}
    theme={HMSAccount.HUAWEI_ID_AUTH_BUTTON_THEME_FULL_TITLE}
    cornerRadius={
      HMSAccount.HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_MEDIUM
    }
    onPress={signIn}
    ref={(el) => (buttonView = el)}
  />

```


### Data Types

#### **SignInData** 

SignInData encapsulates the huaweiIdAuthParams, authRequestOption and authScopeList to perform sign-in.

| Field Name           | Type                                                         | Description                                                  | Mandatory |
| -------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | --------- |
| `huaweiIdAuthParams` | [HMSAuthParamConstants](#hmsauthparamconstants)              | Authorization parameter. [DEFAULT_AUTH_REQUEST_PARAM](#hmsauthparamconstants) or [DEFAULT_AUTH_REQUEST_PARAM_GAME](#hmsauthparamconstants) can be used as a [huaweiIdAuthParams](#hmsauthparamconstants) | yes       |
| `authRequestOption`  | [HMSAuthRequestOptionConstants[]](#hmsauthrequestoptionconstants) | Requested authorization option. See authRequestOption options in [HMSAuthRequestOptionConstants](#hmsauthrequestoptionconstants) | no        |
| `authScopeList`      | [HMSAuthScopeListConstants[]](#hmsauthscopelistconstants)    | Authorized scopes. See authScopeList options in [HMSAuthScopeListConstants](#hmsauthscopelistconstants) | no        |


#### **SilentSignInData** 

SilentSignInData encapsulates the huaweiIdAuthParams to perform silent sign in. 

| Field Name           | Type                                            | Description                                                  | Mandatory |
| -------------------- | ----------------------------------------------- | ------------------------------------------------------------ | --------- |
| `huaweiIdAuthParams` | [HMSAuthParamConstants](#hmsauthparamconstants) | Authorization parameter. [DEFAULT_AUTH_REQUEST_PARAM](#hmsauthparamconstants) or [DEFAULT_AUTH_REQUEST_PARAM_GAME](#hmsauthparamconstants) can be used as a [huaweiIdAuthParams](#hmsauthparamconstants) | yes       |

#### **AuthHuaweiIdBuilder** 

AuthHuaweiIdBuilder object that includes openId, uid, displayName, photoUrl, accessToken, serviceCountryCode, status, gender, authScopeList, serverAuthCode, unionId and countryCode fields.

| Field Name           | Type                                                      | Description                                                  | Mandatory |
| -------------------- | --------------------------------------------------------- | ------------------------------------------------------------ | --------- |
| `authScopeList`      | [HMSAuthScopeListConstants[]](#hmsauthscopelistconstants) | Authorized scopes. See authScopeList options in [HMSAuthScopeListConstants](#hmsauthscopelistconstants) | yes       |
| `openId`             | string                                                    | The value differs for the same user in different apps. The value is unique in a single app. | no        |
| `uid`                | string                                                    | User ID, which is unique.                                    | no        |
| `displayName`        | string                                                    | Nickname.                                                    | no        |
| `photoUrl`           | string                                                    | Profile picture URI.                                         | no        |
| `accessToken`        | string                                                    | Access token.                                                | no        |
| `serviceCountryCode` | string                                                    | Code of the country or region code to which an account belongs. | no        |
| `status`             | number                                                    | User status.                                                 | no        |
| `gender`             | number                                                    | User gender.                                                 | no        |
| `serverAuthCode`     | string                                                    | Authorization code granted by the HUAWEI Account Kit server. | no        |
| `unionId`            | string                                                    | A UnionID uniquely identifies a user across all your apps.   | no        |
| `countryCode`        | string                                                    | Service country or region code of an account.                | no        |


#### **AuthHuaweiId**

| Name                | Type                                                      | Description                                                  |
| ------------------- | --------------------------------------------------------- | ------------------------------------------------------------ |
| `accessToken`       | string                                                    | Access token.                                                |
| `account`           | [HuaweiAccount](#huaweiaccount)                           | Account object based on the email address that is obtained by HUAWEI ID. |
| `displayName`       | string                                                    | Nickname from a HUAWEI ID if the authorization parameter specified by [DEFAULT_AUTH_REQUEST_PARAM](#hmsauthparamconstants) or [PROFILE](#hmsauthrequestoptionconstants) is carried during authorization. |
| `email`             | string                                                    | Email address from a HUAWEI ID if the authorization parameter specified by [EMAIL](#hmsauthrequestoptionconstants) is carried during authorization. Otherwise, null is returned. |
| `familyName`        | string                                                    | Family name                                                  |
| `givenName`         | string                                                    | Given name from a HUAWEI ID if the authorization parameter specified by [DEFAULT_AUTH_REQUEST_PARAM](#hmsauthparamconstants) or [PROFILE](#hmsauthrequestoptionconstants) is carried during authorization. Otherwise, value null is returned. |
| `authorizedScopes`  | [HMSAuthScopeListConstants[]](#hmsauthscopelistconstants) | Authorized Scopes                                            |
| `idToken`           | string                                                    | ID token from a HUAWEI ID if the authorization parameter specified by [ID_TOKEN](#hmsauthrequestoptionconstants) is carried during authorization. Otherwise, null is returned. Otherwise, value null is returned. |
| `avatarUriString`   | string                                                    | Profile picture URI from HUAWEI ID information if the authorization parameter specified by [DEFAULT_AUTH_REQUEST_PARAM](#hmsauthparamconstants) or [PROFILE](#hmsauthrequestoptionconstants) is carried during authorization. Otherwise, value null is returned. |
| `authorizationCode` | string                                                    | Authorization Code from a HUAWEI ID. If no authorization code is carried, null is returned. The app can use the authorization code to obtain the access token from the Account Kit server. Otherwise, value null is returned. |
| `unionId`           | string                                                    | UnionId from a HUAWEI ID if the authorization parameter specified by [DEFAULT_AUTH_REQUEST_PARAM](#hmsauthparamconstants) or [ID](#hmsauthrequestoptionconstants) is carried during authorization. Otherwise, value null is returned. |
| `openId`            | string                                                    | OpenID from HUAWEI ID information if the authorization parameter specified by [DEFAULT_AUTH_REQUEST_PARAM](#hmsauthparamconstants) or [ID](#hmsauthrequestoptionconstants) is carried during authorization. Otherwise, value null is returned. |


#### **AuthScopeData** 

AuthScopeData includes the [authScopeList](#hmsauthscopelistconstants) field to create [AuthHuaweiId](#authHuaweiId).

| Field Name      | Type                                                      | Description                                                  | Mandatory |
| --------------- | --------------------------------------------------------- | ------------------------------------------------------------ | --------- |
| `authScopeList` | [HMSAuthScopeListConstants[]](#hmsauthscopelistconstants) | Authorized scopes. See authScopeList options in [HMSAuthScopeListConstants](#hmsauthscopelistconstants) | yes       |

#### **ContainScopesData** 

ContainScopesData includes the [AuthHuaweiId](#authHuaweiId) and [authScopeList](#hmsauthscopelistconstants) fields to resolve whether authHuaweiId's scope contains [authScopeList](#hmsauthscopelistconstants) or not.

| Field Name      | Type                                                      | Description                                                  | Mandatory |
| --------------- | --------------------------------------------------------- | ------------------------------------------------------------ | --------- |
| `authHuaweiId`  | [AuthHuaweiIdBuilder](#authHuaweiIdbuilder)               | Authorized HUAWEI ID information.                            | yes       |
| `authScopeList` | [HMSAuthScopeListConstants[]](#hmsauthscopelistconstants) | Authorized scopes. See authScopeList options in [HMSAuthScopeListConstants](#hmsauthscopelistconstants) | yes       |

#### **AccesTokenData** 

| Field Name    | Type   | Description          | Mandatory |
| ------------- | ------ | -------------------- | --------- |
| `accessToken` | string | Token to be deleted. | yes       |

#### **AccountData** 

| Field Name          | Type   | Description    | Mandatory |
| ------------------- | ------ | -------------- | --------- |
| `huaweiAccountName` | string | HUAWEI ID name | yes       |

#### **RequestAccessTokenData** 

 Encapsulates the huaweiAccount and authScopeList to get access token. 

| Field Name      | Type                                                      | Description                                                  | Mandatory |
| --------------- | --------------------------------------------------------- | ------------------------------------------------------------ | --------- |
| `authScopeList` | [HMSAuthScopeListConstants[]](#hmsauthscopelistconstants) | Authorized scopes. See authScopeList options in [HMSAuthScopeListConstants](#hmsauthscopelistconstants) | yes       |
| `huaweiAccount` | [HuaweiAccount](#huaweiaccount)                           | [huaweiAccount](#huaweiaccount) that you need to obtain a token. | yes       |

#### **CookieData** 

 Encapsulates the cookieName, cookieValue, domain, path, isHttpOnly, isSecure and maxAge to get cookie.

| Field Name    | Type    | Description                                                  | Mandatory |
| ------------- | ------- | ------------------------------------------------------------ | --------- |
| `cookieName`  | string  | Cookie name.                                                 | yes       |
| `cookieValue` | string  | Cookie value.                                                | no        |
| `domain`      | string  | Cookie domain name.                                          | no        |
| `path`        | string  | Page URL for accessing the cookie.                           | no        |
| `isHttpOnly`  | boolean | Indicates whether cookie information is contained only in the HTTP request header | no        |
| `isSecure`    | boolean | Indicates whether to use HTTPS or HTTP to transmit the cookie. | no        |
| `maxAge`      | number  | Cookie lifetime, in seconds.                                 | no        |

#### **UrlData**

 Encapsulates the attributes to get cookie URL.

| Field Name   | Type    | Description                             | Mandatory |
| ------------ | ------- | --------------------------------------- | --------- |
| `isUseHttps` | boolean | Indicates whether to use HTTPS or HTTP. | no        |
| `domain`     | string  | Domain name.                            | yes       |

#### **HuaweiAccount**

Encapsulates the name and type to create huaweiAccount. 

| Field Name | Type   | Description  | Mandatory |
| ---------- | ------ | ------------ | --------- |
| `name`     | string | Account name | yes       |
| `type`     | string | Account type | yes       |

#### **HashCodeResult**

Includes hash code which indicates the hash value generated by the HMS SDK based on app package name to uniquely identify app.

```js
{ 
  base64Hash: 'Aa4+BmA03Vz' 
}
```


#### **SmsMessageResult**

Includes SMS Message content and status.

```js
{
  Message:<#> Account Plugin verification code is 123456 Aa4+BmA0###,
  Status:
    {
      statusCode:0,
      statusMessage:null,
      errorCode:null
    }
}
```

### Constants

#### HMSAccount

| Constant Fields                                        | Value | Definition                                                |
| ------------------------------------------------------ | ----- | --------------------------------------------------------- |
| `HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_BLUE`              | number     | Exposes HuaweiIdAuthButton.COLOR_POLICY_BLUE.             |
| `HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_RED`               | number     | Exposes HuaweiIdAuthButton.COLOR_POLICY_RED.              |
| `HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_WHITE`             | number     | Exposes HuaweiIdAuthButton.COLOR_POLICY_WHITE             |
| `HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_WHITE_WITH_BORDER` | number     | Exposes HuaweiIdAuthButton.COLOR_POLICY_WHITE_WITH_BORDER |
| `HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_BLACK`             | number     | Exposes HuaweiIdAuthButton.COLOR_POLICY_POLICY_BLACK      |
| `HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_GRAY`              | number     | Exposes HuaweiIdAuthButton.COLOR_POLICY_GRAY              |
| `HUAWEI_ID_AUTH_BUTTON_THEME_NO_TITLE`                 | number     | Exposes HuaweiIdAuthButton.THEME_NO_TITLE                 |
| `HUAWEI_ID_AUTH_BUTTON_THEME_FULL_TITLE`               | number     | Exposes HuaweiIdAuthButton.THEME_FULL_TITLE               |
| `HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_LARGE`            | number     | Exposes HuaweiIdAuthButton.CORNER_RADIUS_LARGE            |
| `HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_MEDIUM`           | number     | Exposes HuaweiIdAuthButton.CORNER_RADIUS_MEDIUM           |
| `HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_SMALL`            | number     | Exposes HuaweiIdAuthButton.CORNER_RADIUS_SMALL            |


#### HMSAuthParamConstants

| Constant Fields                   | Value                             | Definition                                                   |
| --------------------------------- | --------------------------------- | ------------------------------------------------------------ |
| `DEFAULT_AUTH_REQUEST_PARAM`      | string      | Default authorization parameter of a HUAWEI ID. By default [PROFILE](#hmsauthrequestoptionconstants) and [ID](#hmsauthrequestoptionconstants) options are set up. |
| `DEFAULT_AUTH_REQUEST_PARAM_GAME` | string | Default authorization parameter of a game.                   |


#### HMSAuthRequestOptionConstants

| Constant Fields      | Value               | Definition                                                   |
| -------------------- | ------------------- | ------------------------------------------------------------ |
| `PROFILE`            | string           | Requests a HUAWEI ID user to authorize profile information to an app. |
| `ID_TOKEN`           | string           | Requests a HUAWEI ID user to authorize ID token to an app.   |
| `ACCESS_TOKEN`       | string           | Requests a HUAWEI ID user to authorize access token to an app. |
| `EMAIL`              | string           | Requests a HUAWEI ID user to authorize email address to an app. |
| `ID`                 | string           | Requests a HUAWEI ID user to authorize ID to an app.         |
| `AUTHORIZATION_CODE` | string | Requests a HUAWEI ID user to authorize authorization code to an app. |

 #### HMSAuthScopeListConstants

| Constant Fields | Value                               | Definition                      |
| --------------- | ----------------------------------- | ------------------------------- |
| `GAME`          | string                           | Value to specify game scope.    |
| `OPENID`        | string                            | Value to specify openid scope.  |
| `EMAIL`         | string                             | Value to specify email scope.   |
| `PROFILE`       | string                           | Value to specify profile scope. |


### Error Codes

| Error Code | Error Message                                                |
| ---------- | ------------------------------------------------------------ |
| `"2002"`   | The app has not been authorized. This result code is generally generated for the silentSignIn API. |
| `"2012"`   | The user taps the back button to cancel the sign-in.         |
| `"3000"`   | Null huaweiIdAuthParams Parameter                            |
| `"3001"`   | Null service                                                 |
| `"3003"`   | Invalid huaweiIdAuthParams Parameter                         |
| `"3004"`   | Null authScopeList                                           |
| `"3005"`   | Null authHuaweiId or authScopeList                           |
| `"3006"`   | Null accessToken                                             |
| `"3007"`   | Null huaweiAccountName                                       |
| `"3008"`   | Null huaweiAccount                                           |
| `"3009"`   | Null huaweiAccount name or type parameter                    |
| `"3010"`   | Null cookieName                                              |
| `"3011"`   | Null domain                                                  |
| `"3012"`   | Null MessageDigest                                           |
| `"3013"`   | Invalid hashCode                                             |

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
