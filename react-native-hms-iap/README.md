# React-Native HMS IAP

---

## Contents

  - [1. Introduction](#1-introduction)
  - [2. Installation Guide](#2-installation-guide)
    - [Creating Project in App Gallery Connect](#creating-project-in-app-gallery-connect)
    - [Configuring the Signing Certificate Fingerprint](#configuring-the-signing-certificate-fingerprint)
    - [Integrating React Native IAP Plugin](#integrating-react-native-iap-plugin)
  - [3. API Reference](#3-api-reference)
    - [HmsIapModule](#hmsiapmodule)
    - [Data Types](#data-types)
    - [Constants](#constants)
  - [4. Configuration and Description](#4-configuration-and-description)
  - [5. Sample Project](#5-sample-project)
  - [6. Questions or Issues](#6-questions-or-issues)
  - [7. Licensing and Terms](#7-licensing-and-terms)

---

## 1. Introduction

This plugin enables communication between Huawei IAP SDK and React Native platform. Huawei's In-App Purchases (IAP) service allows you to offer in-app purchases and facilitates in-app payment. Users can purchase a variety of virtual products, including one-time virtual products and subscriptions, directly within your app.

Huawei IAP provides the following core capabilities you need to quickly build apps with which your users can buy, consume and subscribe services you provide:
- **isEnvReady**: Returns a response which indicates user's environment status.
- **isSandboxActivated**: Returns a response which indicates user's account capabilities of sandbox testing.
- **obtainProductInfo**: Returns a array of product information. 
- **startIapActivity**: Starts an activity to manage and edit subscriptions.
- **consumeOwnedPurchase**: Consumes the desired purchased product.
- **obtainOwnedPurchases**: Returns a array of products that purchased by user.
- **obtainOwnedPurchaseRecord**: Returns a array of products that purchased and consumed by user. 
- **enableLogger**:  This method enables the HMSLogger capability which is used for sending usage analytics of IAP SDK's methods to improve the service quality.
- **disableLogger**: This method disables the HMSLogger capability which is used for sending usage analytics of IAP SDK's methods to improve the service quality.

---

## 2. Installation Guide

- Before you get started, you must register as a HUAWEI developer and complete identity verification on the [HUAWEI Developer](https://developer.huawei.com/consumer/en/) website. For details, please refer to [Register a HUAWEI ID](https://developer.huawei.com/consumer/en/doc/10104).

- Create an app in your project is required in AppGallery Connect in order to communicate with Huawei services. To create an app, perform the following steps:

### Creating Project in App Gallery Connect

**Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en) and select **My projects**.

**Step 2:** Click your project from the project list.

**Step 3:** Go to **Project Setting** > **General information**, and click **Add app**. If an app exists in the project, and you need to add a new one, expand the app selection area on the top of the page and click **Add app**.

**Step 4:** On the **Add app** page, enter app information, and click **OK**.

- A signing certificate fingerprint is used to verify the authenticity of an app when it attempts to access an HMS Core service through the HMS Core SDK. Before using HMS Core (APK), you must locally generate a signing certificate fingerprint and configure it in AppGallery Connect. Ensure that the JDK has been installed on your computer.

- To use HUAWEI IAP, you need to enable the IAP service first and also set IAP parameters. For details, please refer to [Enabling Services](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-enable_service#h1-1574822945685).

### Configuring the Signing Certificate Fingerprint

**Step 1:** Go to **Project Setting** > **General information**. In the **App information** field, click the icon next to SHA-256 certificate fingerprint, and enter the obtained **SHA256 certificate fingerprint**.

**Step 2:** After completing the configuration, click check mark.

### Integrating React Native IAP Plugin

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

- Set your package name in **defaultConfig** > **applicationId** and set **minSdkVersion** to **19** or higher.

- Package name must match with the **package_name** entry in **agconnect-services.json** file.

```groovy
defaultConfig {
  applicationId "<package_name>"
  minSdkVersion 19
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
npm i @hmscore/react-native-hms-iap
```

**Step 2:**  Run your project. 

- Run the following command to the project directory.

```bash
react-native run-android  
```

#### Download Link
To integrate the plugin, follow the steps below:

**Step 1:** Download the React Native IAP Plugin and place **react-native-hms-iap** under **node_modules/@hmscore** of your React Native project, as shown in the directory tree below.

            demo-app
                |_ node_modules
                    |_ @hmscore
                        |_ react-native-hms-iap
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
    implementation project(":react-native-hms-iap")    
    ...    
  }
}
```

**Step 3:** Add the following lines to the android/settings.gradle file in your project:

```groovy
include ':app'
include ':react-native-hms-iap'
project(':react-native-hms-iap').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-iap/android')
```

**Step 4:**  Open the your application class and add the **HmsIapPackage()**

Import the following classes to the your application file of your project.import **com.huawei.hms.rn.HmsIapPackage**. 

Then, add the **HmsIapPackage** to your **getPackages** method. In the end, your file will be similar to the following:

```java 
import com.huawei.hms.rn.iap.HmsIapPackage;

@Override
protected List<ReactPackage> getPackages() {
    List<ReactPackage> packages = new PackageList(this).getPackages();
    packages.add(new HmsIapPackage()); // <-- Add this line with HmsIapPackage name.
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

### HmsIapModule

Provides all APIs of HUAWEI IAP.

#### Public Method Summary

| Method                                                       | Return Type                           | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------- | ------------------------------------------------------------ |
| [isEnvironmentReady()](#isenvironmentready)     | Promise\<*IsEnvReadyResult*>           | Returns a response which indicates user's environment status. |
| [isSandboxActivated()](#issandboxactivated) | Promise\<*IsSandboxActivatedResult*>   | Returns a response which indicates user's account capabilities of sandbox testing. |
| [obtainProductInfo(*Object* ProductInfoReq)](#obtainproductinfoproductinforeq)) | Promise\<*ProductInfoResult*>          | Returns a array of product information.                       |
| [startIapActivity(*Object* StartIapActivityReq)](#startiapactivitystartiapactivityreq) | Promise\<*StartIapActivityResult*>                         | Starts an activity to manage and edit subscriptions.         |
| [createPurchaseIntent(*Object* PurchaseIntentReq)](#createpurchaseintentpurchaseintentreq) | Promise\<*PurchaseResultInfo*>         | Starts an activity to buy the desired product or subscribe a product. |
| [consumeOwnedPurchase(*Object* ConsumeOwnedPurchaseReq)](#consumeownedpurchaseconsumeownedpurchasereq)) | Promise\<*ConsumeOwnedPurchaseResult*> | Consumes the desired purchased product.                      |
| [obtainOwnedPurchases(*Object* OwnedPurchasesReq)](#obtainownedpurchaserecordownedpurchasesreq)) | Promise\<*OwnedPurchasesResult*>       | Returns a array of products that purchased by user.           |
| [obtainOwnedPurchaseRecord(*Object* OwnedPurchasesReq)](#obtainownedpurchaserecordownedpurchasesreq)) | Promise\<*OwnedPurchasesResult*>       | Returns a array of products that purchased and consumed by user. |
| [disableLogger()](#disablelogger)           | Promise\<false>                         | Disables HMS Plugin Method Analytics.                                         |
| [enableLogger()](#enablelogger)             | Promise\<true>                         | Enables HMS Plugin Method Analytics.                                          |

#### Public Methods

##### isEnvironmentReady() 

Checks whether the currently signed-in Huawei ID is located in a country or region where Huawei IAP is available.

###### Return Type

| Return Type                                      | Description                                                  |
| ------------------------------------------------ | ------------------------------------------------------------ |
| Promise\<[*IsEnvReadyResult*](#isenvreadyresult)>| A task containing the API request result through the promise instance. In the success scenario, an IsEnvReadyResult instance is returned; in the failure scenario, an IapApiException object is returned. |

###### Call Example

```js
/* Obtains IsEnvReadyResult instance. */

async function isEnvironmentReady () {

//Call the isEnvironmentReady API.
  try {
      const IsEnvReadyResult = await HmsIapModule.isEnvironmentReady();
      console.log('Response:: ' + JSON.stringify(IsEnvReadyResult));
  } catch (error) {
    console.log('isEnvironmentReady fail');
  }
}
```

##### isSandboxActivated()

Checks whether the signed-in Huawei ID and the app APK version meet the requirements of the sandbox testing.

###### Return Type

| Return Type                                                  | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Promise\<[*IsSandboxActivatedResult*](#issandboxactivatedresult)> | Represents a response object to gather information about user's sandbox permissions. |

###### Call Example

```js
/* Obtains IsSandboxActivatedResult instance. */

async function isSandboxActivated() {

  //Call the isSandboxActivated API.
  try {
    const IsSandboxActivatedResult = await HmsIapModule.isSandboxActivated();
    console.log('Response:: ' + JSON.stringify(IsSandboxActivatedResult ));
  } catch (error) {
    console.log('isSandboxActivated fail');
  }
}
```

##### obtainProductInfo(ProductInfoReq)

Obtains product details configured in AppGallery Connect. If you use Huawei's PMS to price products, you can use this method to obtain product details from the PMS to ensure that the product information in your app is the same as that displayed on the checkout page of Huawei IAP.

###### Parameters

| Name           | Description                                 |
| -------------- | ------------------------------------------- |
| ProductInfoReq | [*ProductInfoReq*](#productinforeq) object. |

###### Return Type

| Return Type                                        | Description                                                  |
| -------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[*ProductInfoResult*](#productinforesult)> | Represents a response object used to obtain product information. |

###### Call Example

```js
/* Obtains in-app product details for requested consumable products. */

async function obtainProductInfo(CONSUMANLE_PRODUCT_ID_1, CONSUMANLE_PRODUCT_ID_2 ...) {

  //Constructing request.
  const ProductInfoReq = { 
    priceType:HmsIapModule.PRICE_TYPE_IN_APP_CONSUMABLE, /*Use PRICE_TYPE_IN_APP_CONSUMABLE from HMSIapModule's constant values.*/
    productList: [ CONSUMANLE_PRODUCT_ID_1, CONSUMANLE_PRODUCT_ID_2...] /*Your requested consumable product ids.*/
  }

  //Call the obtainProductInfo API.
  try {
    const ProductInfoResult = await HmsIapModule.obtainProductInfo( ProductInfoReq )
    console.log('Response:: ' + JSON.stringify(ProductInfoResult));
  } catch (error) {
    console.log('obtainProductInfo fail');
  }
}
```

##### startIapActivity(StartIapActivityReq) 

Brings up in-app payment pages, including:

- Subscription editing page
- Subscription management page

###### Parameters

| Name                | Description                                                 |
| --------------------| ----------------------------------------------------------- |
| startIapActivityReq | [*startIapActivityReq*](#startiapactivityreq) object. |

###### Return Type

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise\<StartIapActivityResult> |  |

###### Call Example

- Redirecting to the subscription management page:

```js
/* Brings up the subscription management page. */

async function startIapActivity() {

  //Constructing request.
  const StartIapActivityReq = {
    type: HmsIapModule.TYPE_SUBSCRIBE_MANAGER_ACTIVITY /*Use TYPE_SUBSCRIBE_MANAGER_ACTIVITY from HMSIapModule's constant values.*/
  }

  //Call the StartIapActivity.
  try {
    const StartIapActivityResult = await HmsIapModule.startIapActivity(StartIapActivityReq);
    console.log("Response:: " + JSON.stringify(StartIapActivityResult))
  } catch (e) {
    console.log("startIapActivity fail.")
  }
}
```

- Redirecting to the subscription editing page:

```js
/* Redirecting to the subscription editing page. */

async function startIapActivity() {

  //Constructing request.
  const StartIapActivityReq = {
    type: HmsIapModule.TYPE_SUBSCRIBE_EDIT_ACTIVITY  /*Use TYPE_SUBSCRIBE_EDIT_ACTIVITY from HMSIapModule's constant values.*/
    subscribeProductId: 'REQUESTED_SUBSCRIPTION_PRODUCT_ID'
  }

  //Call the StartIapActivity.
  try {
    const StartIapActivityResult = await HmsIapModule.startIapActivity(StartIapActivityReq);
    console.log("Response:: " + JSON.stringify(StartIapActivityResult))
  } catch (e) {
    console.log("startIapActivity fail.")
  }
}
```

##### createPurchaseIntent(PurchaseIntentReq)

Creates orders for PMS products, including consumables, non-consumables, and subscriptions.

After creating a product in AppGallery Connect, you can call this method to open the HUAWEI IAP checkout page and display the product, price, and payment method. Huawei can adjust product prices by foreign exchange rate changes. To ensure price consistency, your app needs to call the *obtainProductInfo* method to obtain product details from Huawei instead of your own server.

###### Parameters

| Name              | Description                                       |
| ----------------- | ------------------------------------------------- |
| purchaseIntentReq | [*PurchaseIntentReq*](#purchaseintentreq) object. |

###### Return Type

| Return Type                                          | Description                                      |
| ---------------------------------------------------- | ------------------------------------------------ |
| Promise\<[*PurchaseResultInfo*](#purchaseresultinfo)> | Represents a response object of purchase intent. |

###### Call Example

```js
/* Opens the HUAWEI IAP checkout page and display the requested product, with given price, and payment method. */
/* Then returns the PurchaseResultInfo's return code via promise */

//Constructing request.
async function createPurchaseIntent ( productId, productType ) {
  let pType;
  switch (productType) {
  case ProductTypes.CONSUMABLE:
    pType = HmsIapModule.PRICE_TYPE_IN_APP_CONSUMABLE; /*Use PRICE_TYPE_IN_APP_CONSUMABLE from HMSIapModule's constant*/
    break;
  case ProductTypes.NON_CONSUMABLE:
    pType = HmsIapModule.PRICE_TYPE_IN_APP_NONCONSUMABLE; /*Use PRICE_TYPE_IN_APP_NONCONSUMABLE from HMSIapModule's constant*/
    break;
  case ProductTypes.SUBSCRIPTION:
    pType = HmsIapModule.PRICE_TYPE_IN_APP_SUBSCRIPTION; /*Use PRICE_TYPE_IN_APP_SUBSCRIPTION from HMSIapModule's constant*/
    break;
  }

  const reservedInfo = {
    "key1": "value1"
  }

  const PurchaseIntentReq = {
    priceType: type,
    productId: productId,
    developerPayload: "payload"
    reservedInfor: JSON.stringify(reservedInfo)
  };

  try {
    //Call the createPurchaseIntent API.
    const PurchaseResultInfo = await HmsIapModule.createPurchaseIntent(PurchaseIntentReq );
    console.log('Response:: ' + JSON.stringify(PurchaseResultInfo));
  } catch (error) {
    console.log('createPurchaseIntent fail');
  }
}
```

##### obtainOwnedPurchases(OwnedPurchasesReq)

###### Parameters

| Name    | Description                                       |
| ------- | ------------------------------------------------- |
| ownedPurchasesReq | [*OwnedPurchasesReq*](#ownedpurchasesreq) object. |

###### Return Type

| Return Type                                              | Description                                                  |
| -------------------------------------------------------- | ------------------------------------------------------------ |
| Promise\<[*OwnedPurchasesResult*](#ownedpurchasesresult)> | Represents a response object of obtain owned purchases or owned purchase record APIs. |

###### Call Example

```js
/* Obtains queries information about consumable products. */

async function obtainOwnedPurchases(){

  //Constructing request.
  const OwnedPurchasesReq = {
    priceType: HmsIapModule.PRICE_TYPE_IN_APP_CONSUMABLE, /*Use PRICE_TYPE_IN_APP_CONSUMABLE from HMSIapModule's constant values.*/
  };

  //Call the obtainOwnedPurchases API.
  try {
    const OwnedPurchasesResult = await HmsIapModule.obtainOwnedPurchases(OwnedPurchasesReq );
    console.log('Response:: ' + JSON.stringify(OwnedPurchasesResult));

    //Check purchase state of products
    for (var i = 0; i < OwnedPurchasesResult.inAppPurchaseDataList.length; i++) {
      console.log(OwnedPurchasesResult.inAppPurchaseDataList[i].purchaseState);
    }
  } catch (error) {
    console.log('obtainOwnedPurchases fail');
  }
}
```

##### consumeOwnedPurchase(ConsumeOwnedPurchaseReq)

Consumes a consumable after the consumable is delivered to a user who has completed payment.

###### Parameters

| Name    | Description                                                  |
| ------- | ------------------------------------------------------------ |
| consumeOwnedPurchaseReq | [*ConsumeOwnedPurchaseReq*](#consumeownedpurchasereq) object. |

###### Return Type

| Return Type                                                  | Description                                |
| ------------------------------------------------------------ | ------------------------------------------ |
| Promise\<[*ConsumeOwnedPurchaseResult*](#consumeownedpurchaseresult)> | Represents details about consumed product. |

###### Call Example

```js
/* Consumes a consumable after the consumable is delivered to a user who has completed payment. */
async function consumeOwnedPurchase(){

  //Constructing request.
  const ConsumeOwnedPurchaseReq= {
    developerChallenge: 'DEVELOPER_CHALLENGE',
    purchaseToken: "PURCHASE_TOKEN_OF_THE_CONSUMABLE_PRODUCT"
  };

  //Call the consumeOwnedPurchase API.
  try {
    const ConsumeOwnedPurchaseResult = await HmsIapModule.consumeOwnedPurchase(ConsumeOwnedPurchaseReq);
    console.log('Response:: ' + JSON.stringify(ConsumeOwnedPurchaseResult));
  } catch (error) {
   console.log('consumeOwnedPurchase fail');
  }
}
```

##### obtainOwnedPurchaseRecord(OwnedPurchasesReq)

Obtains the historical consumption information about a consumable or all subscription receipts of a subscription.

- For consumables, this method returns information about products that have been delivered or consumed in the product list.

- For non-consumables, this method **does not** return product information.
- For subscriptions, this method returns all subscription receipts of the current user in this app

###### Parameters

| Name    | Description                                       |
| ------- | ------------------------------------------------- |
| OwnedPurchasesReq | [*OwnedPurchasesReq*](#ownedpurchasesreq) object. |

###### Return Type

| Return Type                                            | Description                                                  |
| ------------------------------------------------------ | ------------------------------------------------------------ |
| Promise\<[OwnedPurchasesResult](#ownedpurchasesresult)> | Represents a response object of obtain owned purchases or owned purchase record APIs. |

###### Call Example

```js
/* Obtains the historical consumption information */

async function obtainOwnedPurchaseRecord(productType) {
  //Constructing request.
  let priceType;
  try {
  switch (productType) {
    case ProductTypes.CONSUMABLE:
      priceType = HmsIapModule.PRICE_TYPE_IN_APP_CONSUMABLE; /*Use PRICE_TYPE_IN_APP_CONSUMABLE from HMSIapModule's constant*/
      break;
    case ProductTypes.NON_CONSUMABLE:
      priceType = HmsIapModule.PRICE_TYPE_IN_APP_NONCONSUMABLE; /*Use PRICE_TYPE_IN_APP_NONCONSUMABLE from HMSIapModule's constant*/
      break;
    case ProductTypes.SUBSCRIPTION:
      priceType = HmsIapModule.PRICE_TYPE_IN_APP_SUBSCRIPTION; /*Use PRICE_TYPE_IN_APP_SUBSCRIPTION from HMSIapModule's constant*/
      break;
  }

  //Call the obtainOwnedPurchaseRecord API.
    const OwnedPurchasesResult = await HmsIapModule.obtainProductInfo(priceType)
    console.log('Response:: ' + JSON.stringify(OwnedPurchasesResult.inAppPurchaseDataList));

  //Print product ids from purchase history
    console.log('ProductId:: ' + OwnedPurchasesResult.inAppPurchaseDataList[0].productId);
  } catch (error) {
    console.log('obtainProductInfo fail');
  }
}
```

##### disableLogger()

This method disables the HMSLogger capability which is used for sending usage analytics of IAP SDK's methods to improve the service quality.

###### Return Type

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise\<false> | Promise result of an execution that returns boolean value. |

###### Call Example
```js
async function disableLogger() {
  try {
    const response = await HmsIapModule.disableLogger()
    console.log("disableLogger:: " + response)
  }catch(e){
    console.log("disableLogger fail!")
  }
}
```

##### enableLogger()

This method enables the HMSLogger capability which is used for sending usage analytics of IAP SDK's methods to improve the service quality.

###### Return Type

| Return Type   | Description                                          |
| ------------- | ---------------------------------------------------- |
| Promise\<true> | Promise result of an execution that returns boolean value. |

###### Call Example

```js
async function enableLogger() {
  try {
    const response = await HmsIapModule.enableLogger()
    console.log("enabledLogger:: " + response)
  }catch(e){
    console.log("enableLogger fail!")
  }
}
```

### Data Types

#### Data Types Summary

| Class                                                     | Description                                                  |
| :-------------------------------------------------------- | :----------------------------------------------------------- |
| [ConsumeOwnedPurchaseReq](#consumeownedpurchasereq)       | Represents a request object used to consume a product.       |
| [ConsumeOwnedPurchaseResult](#consumeownedpurchaseresult) | Represents a response object used to consume a product.      |
| [ConsumePurchaseData](#consumepurchasedata)               | Represents details about consumed product.                   |
| [InAppPurchaseData](#inapppurchasedata)                   | Represents details about purchased product.                  |
| [IsEnvReadyResult](#isenvreadyresult)                     | Represents a response object used to gather information about user environment. |
| [IsSandboxActivatedResult](#issandboxactivatedresult)     | Represents a response object to gather information about user's sandbox permissions. |
| [OwnedPurchasesReq](#ownedpurchasesreq)                   | Represents a request object used to obtain owned purchases or owned purchase record. |
| [OwnedPurchasesResult](#ownedpurchasesresult)             | Represents a response object used to obtain owned purchases or owned purchase record. |
| [ProductInfo](#productinfo)                               | Represents details of product.                                   |
| [ProductInfoReq](#productinforeq)                         | Represents a request object used to obtain product information.  |
| [ProductInfoResult](#productinforesult)                   | Represents a response object used to obtain product information. |
| [PurchaseIntentReq](#purchaseintentreq)                   | Represents a request object used to create a purchase intent.    |
| [PurchaseResultInfo](#purchaseresultinfo)                 | Represents a response object used to create a purchase intent.   |
| [StartIapActivityReq](#StartIapActivityReq)               | Represents a request object used to start activity for editing or managing subscriptions. |
| [StartIapActivityResult](#startiapactivityresult)         | Represents a response object.                                |
| [Status](#status)                                         | Represents status of the API call.                           |
| [IapApiException](#IapApiException)                       | Represents an error class for HmsIapModule Methods.  |

#### ConsumeOwnedPurchaseReq

Request information of the *consumeOwnedPurchase* API.

##### Public Properties

| Name               | Type   | Description                                                  |
| ------------------ | ------ | ------------------------------------------------------------ |
| purchaseToken      | string | Purchase token, which is generated by the Huawei IAP server during payment and returned to the app through [*InAppPurchaseData*](#inapppurchasedata). The app passes this parameter for the Huawei IAP server to update the order status and then deliver the product. |
| developerChallenge | string | Custom challenge, which uniquely identifies a consumption request. After the consumption is successful, the challenge is recorded in the purchase information and returned. Note: The value length of this parameter is within (0,64). |

#### ConsumeOwnedPurchaseResult

Information returned when the *consumeOwnedPurchase* API is successfully called.

##### Public Properties

| Name                | Type                                          | Description                                                  |
| ------------------- | --------------------------------------------- | ------------------------------------------------------------ |
| consumePurchaseData | [*ConsumePurchaseData*](#consumepurchasedata) | [*ConsumePurchaseData*](#consumepurchasedata) object that contains consumption result data. |
| dataSignature       | string                                        | Signature string generated after consumption data is signed using a private payment key. The signature algorithm is SHA256withRSA |
| errMsg              | string                                        | Result code description.                                     |
| returnCode          | number                                        | Result code.                                                 |
| status              | [*Status*](#status)                           | [*Status*](#status) object that contains the task processing result. |

#### ConsumePurchaseData

Object that contains consumption result data.

##### Public Properties

<details>
  <summary>Click to expand/collapse Properties table</summary>

| Name               | Type   | Description                                                  |
| ------------------ | ------ | ------------------------------------------------------------ |
| applicationId      | number    | ID of an app that initiates a purchase.                      |
| autoRenewing       | boolean   | Indicates whether the subscription is automatically renewed. Currently, the value is always **false**. |
| confirmed          | number    | Confirmation.                                                |
| orderId            | string | Order ID on the Huawei IAP server, which uniquely identifies a transaction and is generated by the Huawei IAP server during payment. |
| kind               | number    | Product type.                                                |
| packageName        | string | Software package name of the app that initiates a purchase.  |
| payOrderId         | string | Merchant ID, which uniquely identifies a transaction and is generated by the Huawei IAP server during payment. |
| payType            | string | Payment method. **"0"**: HUAWEI Points **"3"**: Credit card **"4"**: Alipay **"6"**: Carrier billing **"13"**: PayPal **"16"**: Debit card **"17"**: WeChat Pay **"19"**: Gift card **"20"**: Balance **"21"**: HUAWEI Point card **"24"**: WorldPay **"31"**: HUAWEI Pay **"32"**: Ant Credit Pay **"200"**: M-Pesa |
| productId          | string | Product ID.                                                  |
| productName        | string | Product name.                                                |
| purchaseTime       | number    | Purchase timestamp, which is the number of milliseconds from 00:00:00 on January 1, 1970 to the purchase time. |
| purchaseTimeMillis | number    | Purchase time.                                               |
| purchaseType       | number    | Purchase type. **0**: In the sandbox **1**: In the promotion period (currently unsupported). This parameter is not returned during formal purchase. |
| purchaseState      | number    | Order status. **-1**: initialized and invisible **0**: Purchased **1**: Canceled **2**: Refunded |
| developerPayload   | string | Reserved information on the merchant side, which is passed by the app during payment. |
| purchaseToken      | string | Purchase token, which uniquely identifies the mapping between a product and a user. It is generated by the Huawei IAP server when the payment is complete. |
| developerChallenge | string | Challenge defined when the app initiates a consumption request, which uniquely identifies a consumption request. |
| consumptionState   | number    | Consumption status. **0**: Not consumed **1**: Consumed      |
| acknowledged       | number    | Receiving status. **0**: Not received **1**: Received. This parameter is valid only for receiving APIs. The value is always **0**. You can **ignore this parameter**. |
| currency           | string | Currency. The value must be a currency defined in the [ISO 4217](https://www.iso.org/iso-4217-currency-codes.html) standard. Example: USD, CNY, and TRY |
| price              | number    | Value after the actual price of a product is multiplied by 100. The actual price is accurate to two decimal places. For example, if the value of this parameter is **501**, the actual product price is 5.01. |
| country            | string | Country or region code of a user service area. The value must comply with the [ISO 3166](https://www.iso.org/iso-3166-country-codes.html) standard. Example: US, CN, and TR |
| responseCode       | number | Response code. **0**: The execution is successful.           |
| responseMessage    | string | Response information.                                        |

</details>


#### InAppPurchaseData

Purchase information about products including consumables, non-consumables, and subscriptions.

##### Public Properties

<details>
  <summary>Click to expand/collapse Properties table</summary>

| Name                 | Type   | Description                                                  |
| -------------------- | ------ | ------------------------------------------------------------ |
| applicationId        | number    | ID of an app that initiates a purchase.                      |
| autoRenewing         | boolean   | Indicates whether the subscription is automatically renewed. Currently, the value is always **false**. |
| orderId              | string | Order ID on the Huawei IAP server, which uniquely identifies a transaction and is generated by the Huawei IAP server during payment. |
| packageName          | string | Software package name of the app that initiates a purchase.  |
| productId            | string | Product ID.                                                  |
| productName          | string | Product name.                                                |
| purchaseTime         | number    | Purchase timestamp, which is the number of milliseconds from 00:00:00 on January 1, 1970 to the purchase time. |
| purchaseState        | number    | Order status. **-1**: Initialized and invisible **0**: Purchased **1**: Canceled **2**: Refunded |
| developerPayload     | string | Reserved information on the merchant side, which is passed by the app during payment. |
| purchaseToken        | string | Purchase token, which uniquely identifies the mapping between a product and a user. It is generated by the Huawei IAP server when the payment is complete. |
| purchaseType         | number    | Purchase type.**0**: in the sandbox**1**: in the promotion period (currently unsupported)This parameter is not returned during formal purchase. |
| currency             | string | Currency. The value must be a currency defined in the [ISO 4217](https://www.iso.org/iso-4217-currency-codes.html) standard. Example: USD, CNY, and TRY |
| price                | number    | Value after the actual price of a product is multiplied by 100. The actual price is accurate to two decimal places. For example, if the value of this parameter is **501**, the actual product price is 5.01. |
| country              | string | Country or region code of a user service area. The value must comply with the [ISO 3166](https://www.iso.org/iso-3166-country-codes.html) standard. Example: US, CN, and TR |
| lastOrderId          | string | Order ID generated by the Huawei IAP server during fee deduction on the previous renewal. |
| productGroup         | string | ID of the subscription group to which a subscription belongs. |
| oriPurchaseTime      | number    | First fee deduction timestamp, which is the number of milliseconds since 00:00:00 on January 1, 1970. |
| subscriptionId       | string | Subscription ID.                                             |
| quantity             | number    | Purchase quantity.                                           |
| daysLasted           | number    | Days of a paid subscription, excluding the free trial period and promotion period. |
| numOfPeriods         | number    | Number of successful standard renewal periods (that is, renewal periods without promotion). If the parameter is set to **0** or left empty, no renewal has been performed successfully. |
| numOfDiscounts       | number    | Number of successful renewal periods with promotion.         |
| expirationDate       | number    | Subscription expiration timestamp. For an automatic renewal receipt where the fee has been deducted successfully, this parameter indicates the renewal date or expiration date. If the value is a past time for the latest receipt of a product, the subscription has expired. |
| expirationIntent     | number    | Reason why a subscription expires. **1**: Canceled by a user. **2**: Product being unavailable. **3**: Abnormal user signing information. **4**: Billing error. **5**: Price increase disagreed with by a user. **6**: Unknown error |
| retryFlag            | number    | Indicates whether the system still tries to renew an expired subscription. |
| introductoryFlag     | number    | Indicates whether a subscription is in the renewal period with promotion. |
| trialFlag            | number    | Indicates whether a subscription is in the free trial period. |
| cancelTime           | number    | Subscription cancellation timestamp. This parameter has a value when a user makes a complaint and cancels a subscription through the customer service, or when a user performs subscription upgrade or cross-grade that immediately takes effect and cancels the previous receipt of the original subscription. |
| cancelReason         | number    | Reason why a subscription is canceled. **0**: Others. For example, a user mistakenly purchases a subscription and has to cancel it. **1**: A user encounters a problem within the app and cancels the subscription. **2**: A user performs subscription upgrade or cross-grade. |
| appInfo              | string | App information, which is reserved.                          |
| notifyClosed         | number    | Indicates whether a user has disabled the subscription notification function. |
| renewStatus          | number    | Renewal status. **1**: The subscription renewal is normal. **0**: The user cancels subscription renewal. |
| priceConsentStatus   | number    | User opinion on the price increase of a product. **1**: The user has agreed to the price increase. **0**: The user does not take any action. After the subscription expires, it becomes invalid. |
| renewPrice           | number    | Renewal price.                                               |
| subIsvalid           | boolean   | **true**: A user has been charged for a product, the product has not expired, and no refund has been made. In this case, you can provide services for the user. **false**: The purchase of a product is not finished, the product has expired, or a refund has been made for the product after its purchase is the subscription valid. |
| cancelledSubKeepDays | number    | Number of days for retaining a subscription relationship after the subscription is canceled. |
| kind                 | number    | Product type. **0**: Consumable. **1**: Non-consumable. **2**: Renewable subscription. **3**: Non-renewable subscription |
| developerChallenge   | string | Challenge defined when an app initiates a consumption request, which uniquely identifies the consumption request. This parameter is valid only for one-off products. |
| consumptionState     | number    | Consumption status, which is valid only for one-off products. The options are as follows: **0**: Not consumed. **1**: Consumed. |
| payOrderId           | string | Merchant ID, which uniquely identifies a transaction and is generated by the Huawei IAP server during payment. |
| payType              | string | Payment method. **"0"**: HUAWEI Points **"3"**: Credit card **"4"**: Alipay **"6"**: Carrier billing **"13"**: PayPal **"16"**: Debit card **"17"**: WeChat Pay **"19"**: Gift card **"20"**: Balance **"21"**: HUAWEI Point card **"24"**: WorldPay **"31"**: HUAWEI Pay **"32"**: Ant Credit Pay **"200"**: M-Pesa |
| deferFlag            | number    | Indicates whether to postpone the settlement date. The value **1** indicates that the settlement date is postponed. |
| oriSubscriptionId    | string | Original subscription ID. If the parameter is set to a value, the current subscription is switched from another one. The value can be associated with the original subscription. |
| cancelWay            | number    | Subscription cancellation initiator. **0**: User **1**: Developer **2**: Huawei |
| cancellationTime     | number    | Subscription cancellation time in UTC.                       |
| resumeTime           | number    | Time when a subscription is resumed.                         |
| accountFlag          | number    | Account type. **1**: AppTouch ID. **Other values**: HUAWEI ID |
| purchaseTimeMillis   | number    | Purchase time.                                               |
| confirmed            | number    | Confirmation.                                                |
| graceExpirationTime  | number    | Obtains timestamp when a grace period ends.                  |

</details>

#### IsEnvReadyResult

Information returned when the *isEnvReady* API is successfully called.

##### Public Properties

| Name       | Type                | Description                                                  |
| ---------- | ------------------- | ------------------------------------------------------------ |
| returnCode | number              | Result code. **0**: The country or region of the signed-in HUAWEI ID supports HUAWEI IAP. |
| status     | [*Status*](#status) | [*Status*](#status) object that contains the task processing result. |

#### IsSandboxActivatedResult

Information returned when the *isSandboxActivated* API is successfully called. 

##### Public Properties

| Name            | Type                | Description                                                  |
| --------------- | ------------------- | ------------------------------------------------------------ |
| errMsg          | string              | Result code description.                                     |
| isSandboxApk    | boolean                | Indicates whether the app APK version meets the requirements of the sandbox testing. |
| isSandboxUser   | boolean                | Indicates whether a sandbox testing account is used.         |
| returnCode      | number              | Result code. **0**: Success                                  |
| versionFrMarket | string              | Information about the app version that is last released on HUAWEI AppGallery. |
| versionInApk    | string              | App version information.                                     |
| status          | [*Status*](#status) | [*Status*](#status) object that contains the task processing result. |

#### OwnedPurchasesReq

Request information of the *obtainOwnedPurchases* and *obtainOwnedPurchaseRecord* API.

##### Public Properties

| Name              | Type   | Description                                                  |
| ----------------- | ------ | ------------------------------------------------------------ |
| priceType         | number    | Type of a product to be queried. **0**: Consumable **1**: Non-consumable **2**: Auto-renewable subscription |
| continuationToken | string | Data location flag for query in pagination mode. This parameter is optional for the first query. After the API is called, the returned information contains this parameter. If query in pagination mode is required for the next API call, this parameter can be set for the second query. |

#### OwnedPurchasesResult

Information returned when the *obtainedOwnedPurchases* and *obtainOwnedPurchaseRecord* API is successfully called. 

##### Public Properties

| Name                        | Type                                             | Description                                                  |
| --------------------------- | ------------------------------------------------ | ------------------------------------------------------------ |
| continuationToken           | string                                           | Data location flag. If a user has a large number of products and the response contains **continuationToken**, the app must initiate another call on the current method and pass **continuationToken** currently received. If product query is still incomplete, the app needs to call the API again until no **continuationToken** is returned, indicating that all products are returned. |
| errMsg                      | string                                           | Result code description.                                     |
| inAppPurchaseDataList       | string[] | Information about products that have been purchased but not consumed or about all existing subscription relationships of users using the *obtainOwnedPurchases* method. Historical consumable information or all subscription receipts, which are returned using the *obtainOwnedPurchaseRecord* method. |
| inAppSignature              | string[]                                   | Signature character string of each subscription relationship in the **InAppPurchaseDataList** array. |
| itemList                    | string[]                                    | ID array of found products. The value is a string array.      |
| returnCode                  | number                                           | Result code.**0**: The query is successful.                  |
| status                      | [*Status*](#status)                              | [*Status*](#status) object that contains the task processing result. |
| placedInappPurchaseDataList | string[]                                   | Subscription relationship information about a user who has performed subscription switchover. The value is a JSON string array. For details about the parameters contained in each JSON string, please refer to the description of [*InAppPurchaseData*](#inapppurchasedata). |
| placedInappSignatureList    | string[]                                    | Signature string of each subscription relationship in the **placedInappPurchaseDataList** array. |

#### ProductInfo

Details of a product.

##### Public Properties

<details>
  <summary>Click to expand/collapse Properties table</summary>

| Name                   | Type   | Description                                                  |
| ---------------------- | ------ | ------------------------------------------------------------ |
| productId              | string | Product ID.                                                  |
| priceType              | number    | Product type. **0**: Consumable **1**: Non-consumable **2**: Auto-renewable subscription |
| price                  | string | Displayed price of a product, including the currency symbol and actual price of the product. The value is in the **Currency symbolPrice** format, for example, ¥0.15. The price includes the tax. |
| microsPrice            | number    | Product price in micro unit, which equals to the actual product price multiplied by 1,000,000. For example, if the actual price of a product is US$1.99, the product price in micro unit is 1990000 (1.99 x 1000000). |
| originalLocalPrice     | string | Original price of a product, including the currency symbol and actual price of the product. The value is in the **Currency symbolPrice** format, for example, ¥0.15. The price includes the tax. |
| originalMicroPrice     | number    | Original price of a product in micro unit, which equals to the original product price multiplied by 1,000,000. For example, if the original price of a product is US$1.99, the product price in micro unit is 1990000 (1.99 x 1000000). |
| currency               | string | Currency used to pay for a product. The value must comply with the [ISO 4217](https://www.iso.org/iso-4217-currency-codes.html) standard. Example: USD, CNY, and TRY |
| productName            | string | Product name, which is set during product information configuration. The name is displayed on the checkout page. |
| productDesc            | string | Description of a product, which is set during product information configuration. |
| subSpecialPriceMicros  | number    | Promotional subscription price in micro unit, which equals to the actual promotional subscription price multiplied by 1,000,000. For example, if the actual price of a product is US$1.99, the product price in micro unit is 1990000 (1.99 x 1000000). This parameter is returned only when subscriptions are queried. |
| subSpecialPeriodCycles | number    | Number of promotion periods of a subscription. It is set when you set the promotional price of a subscription in AppGallery Connect. For details, please refer to [Setting a Promotional Price](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-modify_product#h1-1575968939349). This parameter is returned only when subscriptions are queried. |
| subProductLevel        | number    | Level of a subscription in its subscription group.           |
| status                 | number    | Product status. **0**: Valid. **1**: Deleted. Products in this state cannot be renewed or subscribed to. **6**: Removed. New subscriptions are not allowed, but users who have subscribed to products can still renew them. |
| subFreeTrialPeriod     | string | Free trial period of a subscription. It is set when you set the promotional price of a subscription in AppGallery Connect. For details, please refer to [Setting a Promotional Price](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-modify_product#h1-1575968939349). |
| subGroupId             | string | ID of the subscription group to which a subscription belongs. |
| subGroupTitle          | string | Description of the subscription group to which a subscription belongs. |
| subSpecialPeriod       | string | Promotion period unit of a subscription, which complies with the [ISO 8601](https://www.iso.org/iso-8601-date-and-time-format.html) standard. For example, P1W indicates 1 week, P1M indicates 1 month, P2M indicates 2 months, P6M indicates 6 months, and P1Y indicates 1 year. This parameter is returned only when subscriptions are queried. |
| subPeriod              | string | Unit of a subscription period, which complies with the [ISO 8601](https://www.iso.org/iso-8601-date-and-time-format.html) standard. For example, P1W indicates 1 week, P1M indicates 1 month, P2M indicates 2 months, P6M indicates 6 months, and P1Y indicates 1 year. This parameter is returned only when subscriptions are queried. |
| subSpecialPrice        | string | Promotional price of a subscription, including the currency symbol and actual price. The value is in the **Currency symbolPrice** format, for example, ¥0.15. The price includes the tax. This parameter is returned only when subscriptions are queried. |

</details>

#### ProductInfoReq

Request object of the *obtainProductInfo* API.

##### Public Properties

| Name      | Type          | Description                                                  |
| --------- | ------------- | ------------------------------------------------------------ |
| priceType | number           | Type of a product to be queried. **0**: Consumable **1**: Non-consumable **2**: Auto-renewable subscription |
| skuIds    | string[]     | ID array of products to be queried. Each product ID must exist and be unique in the current app. The product ID is the same as that you set when configuring product information in AppGallery Connect. For details, please refer to [Adding a Product](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-create_product). |

#### ProductInfoResult

Information returned when the *obtainProductInfo* API is successfully called.

##### Public Properties

| Name            | Type                                 | Description                                                  |
| --------------- | ------------------------------------ | ------------------------------------------------------------ |
| errMsg          | string                               | Result code description.                                     |
| productInfoList | ProductInfo[] | Array of found products.                                      |
| returnCode      | number                               | Result code. **0**: The query is successful.                 |
| status          | [*Status*](#status)                  | [*Status*](#status) object that contains the task processing result. |

#### PurchaseIntentReq

Request object of the *createPurchaseIntent* API.

##### Public Properties

| Name             | Type   | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| priceType        | number    | Product type. **0**: Consumable **1**: Non-consumable **2**: Auto-renewable subscription |
| productId        | string | ID of a product to be paid. The product ID is the same as that you set when configuring product information in [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html). For details, please refer to [Adding a Product](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-create_product). |
| developerPayload | string | Information stored on the merchant side. If this parameter is set, the value will be returned in the callback result to the app after successful payment. Note: The value length of this parameter is within (0, 128). |
| reservedInfor    | string | This parameter is used to pass the extra fields set by a merchant in a JSON string in the key-value format. |

#### PurchaseResultInfo

Returned payment result information.

##### Public Properties

| Name               | Type                                      | Description                                                  |
| ------------------ | ----------------------------------------- | ------------------------------------------------------------ |
| returnCode         | number                                    | Result code. **0**: The payment is successful. **Other values**: The payment failed. For details about the result codes, please refer to [Troubleshooting and Common Result Codes](https://developer.huawei.com/consumer/en/doc/development/HMS-References/iap-ExceptionHandlingAndGeneralErrorCodes-v4). |
| inAppPurchaseData  | [*InAppPurchaseData*](#inapppurchasedata) | [*InAppPurchaseData*](#inapppurchasedata) object that contains purchase order details. For details about the parameters contained in the string, please refer to [*InAppPurchaseData*](#inapppurchasedata). |
| inAppDataSignature | string                                    | Signature string generated after purchase data is signed using a private payment key. The signature algorithm is SHA256withRSA. After the payment is successful, the app needs to perform signature verification on the string of [*InAppPurchaseData*](#inapppurchasedata) using the payment public key. For details about how to obtain the public key, please refer to [Querying IAP Information](https://developer.huawei.com/consumer/en/doc/development/HMS-Guides/appgallery_querypaymentinfo). |
| errMsg             | string                                    | Result code description.                                     |

#### StartIapActivityResult

Information returned when the *startIapActivity* API is successfully called.

##### Public Properties

| Name               | Type   | Description                                                  |
| ------------------ | ------ | ------------------------------------------------------------ |
| isSuccess          | boolean| The value of isSuccess returns true.                         |

#### StartIapActivityReq

Request information of the *startIapActivity* API.

##### Public Properties

| Name               | Type   | Description                                                  |
| ------------------ | ------ | ------------------------------------------------------------ |
| type               | number | Type of the page to be redirected to. **2:** subscription management page, **3:** subscription editing page |
| productId         | string  | ID of a subscription.  |

#### IapApiException

Represents an error class for HmsIapModule Methods.

##### Public Properties

| Name          | Type   | Description     |
| ------------- | ------ | --------------- |
| status        | [*Status*](#status) | Status object that contains status information. |

### Constants
| Name                                                  | Description                                                       |
|-------------------------------------------------------|-------------------------------------------------------------------|
| HmsIapModule.ORDER_ACCOUNT_AREA_NOT_SUPPORTED         | The country or region of the signed-in HUAWEI ID does not support HUAWEI IAP. |
| HmsIapModule.ORDER_HIGH_RISK_OPERATIONS               | The user triggers risk control, and the transaction is rejected. |
| HmsIapModule.ORDER_HWID_NOT_LOGIN                     | The user does not sign in using a HUAWEI ID. |
| HmsIapModule.ORDER_NOT_ACCEPT_AGREEMENT               | The user does not agree to the payment agreement. |
| HmsIapModule.ORDER_PRODUCT_CONSUMED                   | The product has been consumed and cannot be consumed again.  |
| HmsIapModule.ORDER_PRODUCT_NOT_OWNED                  | The user failed to consume a product because the user does not own the product.
| HmsIapModule.ORDER_PRODUCT_OWNED                      | The user failed to purchase a product because the user already owns the product.  |
| HmsIapModule.ORDER_STATE_CANCEL                       | The payment is canceled by the user.  |
| HmsIapModule.ORDER_STATE_FAILED                       | Common failure result code.  |
| HmsIapModule.ORDER_STATE_NET_ERROR                    | Network connection exception.  |
| HmsIapModule.ORDER_STATE_PARAM_ERROR                  | Parameter error (including no parameter).  |
| HmsIapModule.ORDER_STATE_SUCCESS                      | Success.  |
| HmsIapModule.ORDER_VR_UNINSTALL_ERROR                 | The VR APK is not installed. |
| HmsIapModule.ORDER_STATE_IAP_NOT_ACTIVATED| In-App Purchases could not be activated. |
| HmsIapModule.ORDER_STATE_PRODUCT_INVALID| Incorrect product information. |
| HmsIapModule.ORDER_STATE_CALLS_FREQUENT| Too frequent API calls. |
| HmsIapModule.ORDER_STATE_PMS_TYPE_NOT_MATCH | The found product type is inconsistent with that defined in the PMS  |
| HmsIapModule.ORDER_STATE_PRODUCT_COUNTRY_NOT_SUPPORTED | The app to which the product belongs is not released in a specified location.  |
| HmsIapModule.PURCHASE_DATA_NOT_PRESENT | Not present.  |
| HmsIapModule.PURCHASE_STATE_CANCELED| Canceled.  |
| HmsIapModule.PURCHASE_STATE_INITIALIZED | Initialized.  |
| HmsIapModule.PURCHASE_STATE_PURCHASED | Purchased.  |
| HmsIapModule.PURCHASE_STATE_REFUNDED | Refunded.  |
| HmsIapModule.PRICE_TYPE_IN_APP_CONSUMABLE | Consumable.  |
| HmsIapModule.PRICE_TYPE_IN_APP_NONCONSUMABLE | Non-consumable.  |
| HmsIapModule.PRICE_TYPE_IN_APP_SUBSCRIPTION | Subscription.  |
| HmsIapModule.TYPE_SUBSCRIBE_MANAGER_ACTIVITY | Your app is redirected to the subscription management page of HUAWEI IAP.  |
| HmsIapModule.TYPE_SUBSCRIBE_EDIT_ACTIVITY | Your app is redirected to the subscription editing page of HUAWEI IAP.  |

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
-keepattributes SourceFile,LineNumberTable
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

<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-iap/.docs/page1.jpg" width = 300px style="margin:1em">

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
