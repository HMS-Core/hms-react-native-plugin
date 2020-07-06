# react-native-hms-account

# Contents

1. Introduction
2. Installation Guide
3. Function Definitions
4. Configuration & Description
5. Licencing & Terms

## 1. Introduction

This plugin enables communication between Huawei Account kit and React Native platform.


- Android: core layer, bridging Huawei's Account Kit bottom-layer code.
- src/modules: Android interface layer, exposes Modules and methods to React Native.
- index.js: external interface definition layer, which is used to define interface classes or reference files. 


## 2. Installation Guide

- Go to your app's directory
```bash
cd example/
```
- Install
```bash
npm i
```
- Copy Library

```bash
npm run react-native-hms-account
```

After copying, the structure should be like this


- Download the module and copy it in your demo project's 'node_modules' folder. The folder structure is shown below;

```text
project-name
    |_ node_modules
        |_ ...
        |_ react-native-hms-account
        |_ ...
```

- Add HMSAccount package to your application
```java
import com.huawei.hms.rn.account.HmsAccountPackage;
```
- Add the following line to your getPackages() method.
```java
packages.add(new HmsAccountPackage());
```

### Copy the agconnect-service.json file to the android/app directory of the demo project.

- Create an app by referring to [Creating an AppGallery Connect Project](https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-Guides/agc-get-started#h1-1587521853252) and [Adding an App to the Project](https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-Guides/agc-get-started#h1-1587521946133).

- A signing certificate fingerprint is used to verify the authenticity of an app when it attempts to access an HMS Core (APK) through the HMS SDK. Before using the HMS Core (APK), you must locally generate a signing certificate fingerprint and configure it in AppGallery Connect.  For details, please refer to [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#3).

- Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and select **My apps**. Then, on the **Project Setting** page, set **SHA-256 certificate fingerprint** to the SHA-256 fingerprint from [Configuring the Signing Certificate Fingerprint](https://developer.huawei.com/consumer/en/doc/development/HMS-Guides/iap-configuring-appGallery-connect).

- In [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html), on **My projects** page, in **Manage APIs** tab, find and activate **In App Purchases**, then on **My projects** page, find **In-App Purchases** and set required settings. For more information, please refer to [Setting In-App Purchases Parameters](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-enable_service#h1-1587376818335).

- In [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html), on **My apps** page, find your app from the list and click the app name. Go to **Development > Overview > App Information**. Click **agconnect-service.json** to download configuration file. 

- Finally, copy the **agconnect-service.json** file to the **android/app** directory of the demo project.

- **Package name must match with the _package_name_ entry in _agconnect-services.json_ file.**

```gradle
defaultConfig {
    applicationId "<package_name>"
    minSdkVersion 19 //minSdkVersion to 19 or higher.
    /*
     * <Other configurations>
     */
}
```

- **Do not forget to copy the signature file generated in [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#3) to _android/app_ directory.**

     Configure the signature in **android** according to the signature file information.

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
- Go to your project's direction and open a terminal here, run your app with the following.
```bash
react-native run-android
```

## 3. HUAWEI Account Plugin for React Native
### 3.1 HmsAccountModule
### 3.1.1 Public Method Summary
| Method                                             | Return Type                            |
|----------------------------------------------------|----------------------------------------|
| signIn(signInData)                               | Promise `<AuthHuaweiId>`           |
| signOut()                               | Promise `<Void>`   |
| silentSignIn()       | Promise `<AuthHuaweiId>`       |
| cancelAuthorization()            | Promise `<Void>`          |
### 3.1.2 Public Methods

#### signIn(signInData)
Use this method to sign in.

#### signOut()
Use this method to sign out.

#### silentSignIn()
Use this method to silent sign in. Thus, the user won't need to enter the login credentials for the subsequent sign ins.

#### cancelAuthorization()
Use this method to cancel authorization. By calling this, the user will need to be authorized for subsequent sign ins.

### 3.2 AuthHuaweiIdModule
### 3.2.1 Public Methods Summary
| Method                   | Return Type                                                                                                                                   |
| :-------------:                           | :-------------:                                                                                                                    |
| createDefault()                 |                   Promise `<AuthHuaweiId>`                                                   |
| build(AuthHuaweiId)                    | Promise `<AuthHuaweiId>`                                                                       |
| getRequestedScopes(AuthHuaweiId)                 | Promise `<ScopeString>`                                                       |

### 3.2.2 Public Methods

#### createDefault()
Returns a default AuthHuaweiId object.

#### build(AuthHuaweiId)
Builds an AuthHuaweiId object with the given parameters.

#### getRequestedScopes(AuthHuaweiId)
Returns the requested scopes of AuthHuaweiId object.

### 3.3 HuaweiIdAuthManagerModule

### 3.3.1 Public Methods Summary
| Method                    |                 Return Type                                                                                      |
| :-----:                     | :-------:                                                                                             |
| getAuthResult()             | Promise `<AuthHuaweiId>`                                                         |
| getAuthResultWithScopes(Array `<ScopeString>`)             | Promise `<AuthHuaweiId>`                                             |
| addAuthScopes(AddAuthScopesAndRequestCode)          | Void                                                           |
| containScopes(AuthHuaweiId)          | Promise `<Boolean>`               

### 3.3.2 Public Methods

### 3.4 HuaweiIdAuthToolModule
### 3.4.1 Public Method Summary
| Method                    |                 Return Type                                                                                      |
| :-----:                     | :-------:                                                                                             |
| deleteAuthInfo(AccessToken)             | Promise                                                          |
| requestUnionId(HuaweiAccountName)             | Promise                                            |
| requestAccessToken(AuthHuaweiId)                | Promise `<Boolean>`                                                     |

### 3.4.2 Public Methods

#### deleteAuthInfo(AccessToken)
Clears the local cache.

#### requestUnionId(HuaweiAccountName)
Returns a union Id.

#### requestAccessToken(AuthHuaweiId)
Returns a token.

### 3.5 NetworkToolModule
### 3.5.1 Public Method Summary
| Method                    |                 Return Type                                                                                      |
| :-----:                     | :-------:                                                                                             |
| buildNetWorkCookie(CookieData)             | Promise `<String>`                                                            |
| buildNetworkUrl(UrlData)             | Promise `<String>`                                           |

### 3.5.2 Public Methods

#### buildNetWorkCookie(CookieData)
Constructs a cookie by combining input values.

#### buildNetworkUrl(UrlData)
Returns cookie url based on the domain name and isUseHttps.

### 3.6 SMSManagerModule

### 3.6.1 Public Method Summary
| Method                    |                 Return Type                                                                                      |
| :-----:                     | :-------:                                                                                             |
| startReadSMSManager()             | Promise                                                           |
| getHashCode()             | Promise `<String>`                                         

### 3.6.2 Public Methods

#### startReadSMSManager()
Use this method to start readSMSManager. Which listens for verification the SMS broadcasts.
#### getHashCode()
Use this method to obtain a Hashcode. The hash code is added at the end of the verification SMS.

### 3.7 Objects

| Object                    |                 Description                                                                                      |
| :-----:                     | :-------:                                                                                             |
| AccessToken             | Access token as a String                                                           |
| AddAuthScopesAndRequestCode             | A map encapsulating a scope list and a request code                                           |
| AuthHuaweiId| A map containing Huawei ID data                                                           |
| CookieData| A map containing the cookie data to be built                                           |
| HuaweiAccountName             | A String indicating the name of Huawei Account                                                       |
| ScopeString             | Name of a scope as a String                                                          |
| SignInData             | A map containing the data to be used for signing in                                         |
| UrlData             | A map containing the data of URL to be built                                                         |

### 3.8 Constants

| Constant                    |                 Description                                                                                      |
| :-----:                     | :-------:                                                                                             |
| CONSTANT_HUAWEI_ID_AUTH_PARAMS_DEFAULT_AUTH_REQUEST_PARAM             | Exposes HuaweiIdAuthParam.DEFAULT_AUTH_REQUEST_PARAM                                                           |
| CONSTANT_HUAWEI_ID_AUTH_PARAMS_DEFAULT_AUTH_REQUEST_PARAM_GAME             | Exposes HuaweiIdAuthParam.DEFAULT_AUTH_REQUEST_PARAM_GAME                                           |
| CONSTANT_READ_SMS_CONSTANT_EXTRA_SMS_MESSAGE             | Exposes ReadSmsConstant.EXTRA_SMS_MESSAGE                                                           |
| CONSTANT_READ_SMS_CONSTANT_EXTRA_STATUS             | Exposes ReadSmsConstant.EXTRA_STATUS                                           |
| CONSTANT_READ_SMS_BROADCAST_ACTION             | Exposes ReadSmsConstant.READ_SMS_BROADCAST_ACTION                                                       
| CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_BLUE             | Exposes HuaweiIdAuthButton.COLOR_POLICY_BLUE                                           |
| CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_RED             | Exposes HuaweiIdAuthButton.COLOR_POLICY_RED                                                         |
| CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_WHITE             | Exposes HuaweiIdAuthButton.COLOR_POLICY_WHITE                                           |
| CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_WHITE_WITH_BORDER             | Exposes HuaweiIdAuthButton.COLOR_POLICY_WHITE_WITH_BORDER                                                       |
| CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_BLACK             | Exposes HuaweiIdAuthButton.COLOR_POLICY_POLICY_BLACK                                           |
| CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_GRAY             | Exposes HuaweiIdAuthButton.COLOR_POLICY_GRAY                                                           |
| CONSTANT_HUAWEI_ID_AUTH_BUTTON_THEME_FULL_TITLE             | Exposes HuaweiIdAuthButton.THEME_FULL_TITLE                                           |
| CONSTANT_HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_LARGE             |Exposes HuaweiIdAuthButton.CORNER_RADIUS_LARGE                                                           |
| CONSTANT_HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_MEDIUM             |Exposes HuaweiIdAuthButton.CORNER_RADIUS_MEDIUM                                            |
| CONSTANT_HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_SMALL             | Exposes HuaweiIdAuthButton.CORNER_RADIUS_SMALL                                                           |
| SCOPE_ID_TOKEN             | Exposes the scope setIdToken                                           |
| SCOPE_ID             | Exposes the scope setId                                                           |
| SCOPE_ACCESS_TOKEN             | Exposes the scope setAccessToken                                          |
| SCOPE_AUTHORIZATION_CODE             | Exposes the scope setAuthorizationCode                                                           |
| SCOPE_EMAIL             | Exposes the scope setEmail                                           |
| SCOPE_MOBILE_NUMBER             | Exposes the scope setMobileNumber                                                          |
| SCOPE_PROFILE             | Exposes the scope setProfile                                      |
| SCOPE_SCOPE_LIST             | Exposes the scope setScopeList                                                           |
| SCOPE_SHIPPING_ADDRESS             | Exposes the scope setShippingAdress                                           |
| SCOPE_UID            | Exposes the scope setUID                                                          |
| SCOPE_CREATE_PARAMS             | Exposes the scope createParams                                           |



## 4. Configure parameters.    
No.
## 5. Licensing and Terms  
Huawei Reactive-Native SDK uses the Apache 2.0 license.