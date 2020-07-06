# react-native-hms-iap

## Contents
- Introduction
- Installation Guide
- React-Native HMS IAP SDK Method Definition
- Configuration Description
- Licensing and Terms

## 1. Introduction

The React-Native HMS IAP SDK code encapsulates the Huawei's In-App Purchases (IAP) **IapClient** interface. It provides many APIs for your reference or use.

The React-Native HMS IAP SDK code package is described as follows:

- Android: core layer, bridging Huawei's In-App Purchases (IAP) bottom-layer code;
- src/modules: Android interface layer, which is used to parse the received data, send requests and generate class instances.
- index.js: external interface definition layer, which is used to define interface classes or reference files. 

## 2. Installation Guide
Before using react-native-hms-iap, ensure that the RN development environment has been installed. 

### 2.1. Install react-native-hms-iap

#### Run below script, 

`$ react-native-hms-iap`

#### Or, copy the library into the demo project 

In order to reach the library from demo app, the library should be copied under the node_modules folder of the project.
 
The structure should be like this

            rn-hms-iap-demo
                |_ node_modules
                    |_ ...
                        react-native-hms-iap
                        ...
                        

### 2.2. Add IAP package to your application
```java
import com.huawei.hms.rn.iap.HmsIapPackage; // <-- Add this import line with HmsIapPackage name.
```
Then add the following line to your getPackages() method.
```java
packages.add(new HmsIapPackage()); // <-- Add this line with HmsIapPackage name.
```

### 2.3. Copy the agconnect-service.json file to the android/app directory of the demo project.

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

- **Do not forget to copy the signature file that generated in [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#3) to _android/app_ directory.**

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


## 3. HUAWEI IAP Kit SDK for React Native

HMSIapModule, contains all APIs of Huawei IAP.

### Public Method Summary

| Method                                             | Return Type                            |
|----------------------------------------------------|----------------------------------------|
| isEnvironmentReady()                               | Promise `<IsEnvReadyResult>`           |
| isSandboxActivated()                               | Promise `<IsSandboxActivatedResult>`   |
| obtainOwnedPurchases(req: OwnedPurchasesReq)       | Promise `<OwnedPurchasesResult>`       |
| obtainProductInfo(req: ProductInfoReq)             | Promise `<ProductInfoResult>`          |
| createPurchaseIntent(req: PurchaseIntentReq)       | Promise `<PurchaseResultInfo>`         |
| consumeOwnedPurchase(req: ConsumeOwnedPurchaseReq) | Promise `<ConsumeOwnedPurchaseResult>` |
| obtainOwnedPurchaseRecord(req: OwnedPurchasesReq)  | Promise `<OwnedPurchasesResult>`       |
| startIapActivity(req: StartIapActivityReq)         | Promise `<>`                           |


### Public Methods

#### isEnvironmentReady()

Checks whether the currently signed-in HUAWEI ID is located in a country or region where HUAWEI IAP is available.

##### Return

| Return Type                          | Description                                                                                                                                                                                                   |
|:------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| `Promise <IsEnvReadyResult>` | Returns the task containing the API request result via promise instance. In the success scenario, IsEnvReadyResult instance is returned, in the failure scenario, IapApiException object is returned. |
 
##### Call Example

```javascript
/* Obtains IsEnvReadyResult instance. */
  
//Call the isEnvReady API.
const isEnvReadyResult = await HmsIapModule.isEnvironmentReady();
```

#### isSandboxActivated()

Checks whether the sign-in HUAWEI ID and app APK version meets the requirements of the sandbox testing. 

##### Return

| Return Type                          | Description                                                                                                                                                                                                   |
|:------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| `Promise <IsSandboxActivatedResult>` | Returns the task containing the API request result via promise instance. In the success scenario, IsSandboxActivatedResult instance is returned, in the failure scenario, IapApiException object is returned. |

##### Call Example

```javascript
/* Obtains IsSandboxActivatedResult instance. */
  
//Call the isSandboxActivated API.
const isSandboxActivatedResult = await HmsIapModule.isSandboxActivated();
```

#### obtainOwnedPurchases(request)

Queries information about all purchased in-app products, including consumables, non-consumables, and auto-renewable subscriptions.

##### Parameters

| Parameter           | Description                                                                 |
|:-------------------:|-----------------------------------------------------------------------------|
| `ownedPurchasesReq` | Object to get OwnedPurchasesReq instance that contains request information. |

##### Return

| Return Type                      | Description                                                                                                                                                                                               |
|:--------------------------------:|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Promise <OwnedPurchasesResult>` | Returns the task containing the API request result via promise instance. In the success scenario, OwnedPurchasesResult instance is returned, in the failure scenario, IapApiException object is returned. |

##### Call Example

```javascript
/* Obtains queries information about consumable products. */

//Constructing request.
const ownedPurchasesReq = {
  priceType: HmsIapModule.PRICE_TYPE_IN_APP_CONSUMABLE,//Use PRICE_TYPE_IN_APP_CONSUMABLE from HMSIapModule's constant values.
};

//Call the obtainOwnedPurchases API.
const ownedPurchasesResult = await HmsIapModule.obtainOwnedPurchases(ownedPurchasesReq);
```

#### obtainProductInfo(request)

Obtains in-app product details configured in AppGallery Connect. If you use Huawei’s PMS to price in-app products, you can use this API to obtain in-app product details from the PMS to ensure that the in-app product information in your app is the same as that displayed on the checkout page of HUAWEI IAP.

##### Parameters

| Parameter        | Description                                                              |
|:----------------:|--------------------------------------------------------------------------|
| `productInfoReq` | Object to get ProductInfoReq instance that contains request information. |

##### Return

| Return Type                   | Description                                                                                                                                                                                            |
|:-----------------------------:|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Promise <ProductInfoResult>` | Returns the task containing the API request result via promise instance. In the success scenario, ProductInfoResult instance is returned, in the failure scenario, IapApiException object is returned. |

##### Call Example

```javascript
/* Obtains in-app product details for requested consumable products. */

//Constructing request.
const productInfoReq = {
    priceType: HmsIapModule.PRICE_TYPE_IN_APP_CONSUMABLE, //Use PRICE_TYPE_IN_APP_CONSUMABLE from HMSIapModule's constant values.
    productList: ['CONSUMANLE_PRODUCT_ID_1,CONSUMANLE_PRODUCT_ID_2...'], //Your requested consumable product ids.
};

//Call the obtainProductInfo API.
const productInfoResult = await HmsIapModule.obtainProductInfo(productInfoReq);
```

#### createPurchaseIntent(request)

Creates orders for PMS products, including consumables, non-consumables, and subscriptions. After creating an in-app product in AppGallery Connect, calls this API to open the HUAWEI IAP checkout page and display the product, price, and payment method.

##### Parameters

| Parameter           | Description                                                                    |
|:-------------------:|--------------------------------------------------------------------------------|
| `purchaseIntentReq` | Object to get PurchaseIntentReq instance that contains request information.    |

##### Return

| Return Type                    | Description                                                                                                                                                                                              |
|:------------------------------:|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Promise <PurchaseResultInfo>` | Returns the task containing the API request result via promise instance. In the success scenario, PurchaseResultInfo instance is returned, in the failure scenario, IapApiException object is returned. |
 
##### Call Example

```javascript
/* Opens the HUAWEI IAP checkout page and display the requested product, with given price, and payment method. */
/* Then returns the purchaseResultInfo's return code via promise */

//Constructing request.
const purchaseIntentReq = {
    priceType: HmsIapModule.PRICE_TYPE_IN_APP_CONSUMABLE, //Use PRICE_TYPE_IN_APP_CONSUMABLE from HMSIapModule's constant values.
    productId: 'CONSUMABLE_PRODUCT_ID', //Your consumable product id.
    developerPayload: 'DEVELOPER_PAYLOAD',
};

//Call the createPurchaseIntent API.
const purchaseResultInfo = await HmsIapModule.createPurchaseIntent(purchaseIntentReq);
```

#### consumeOwnedPurchase(request)

Consumes a consumable after the consumable is delivered to a user who has completed payment.

##### Parameters

| Parameter                 | Description                                                                       |
|:-------------------------:|-----------------------------------------------------------------------------------|
| `consumeOwnedPurchaseReq` | Object to get ConsumeOwnedPurchaseReq instance that contains request information. |

##### Return

| Return Type                            | Description                                                                                                                                                                                                     |
|:--------------------------------------:|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Promise <ConsumeOwnedPurchaseResult>` | Returns the task containing the API request result via promise instance. In the success scenario, ConsumeOwnedPurchaseResult instance is returned, in the failure scenario, IapApiException object is returned. |
 
##### Call Example

```javascript
/* Consumes a consumable after the consumable is delivered to a user who has completed payment. */

//Constructing request.
const consumeOwnedPurchaseReq = {
    developerChallenge: 'DEVELOPER_CHALLENGE',
    purchaseToken: 'PURCHASE_TOKEN_OF_THE_CONSUMABLE_PRODUCT',
};

//Call the consumeOwnedPurchase API.
const consumeOwnedPurchaseResult = await HmsIapModule.consumeOwnedPurchase(consumeOwnedPurchaseReq);
```

#### obtainOwnedPurchaseRecord(request)

Obtains the historical consumption information about a consumable or all subscription receipts of a subscription.

● For consumables, this method returns information about products that have been delivered or consumed in the product list.

● For non-consumables, this method does not return product information.

● For subscriptions, this method returns all subscription receipts of the current user in this app.

##### Parameters

| Parameter           | Description                                                                        |
|:-------------------:|------------------------------------------------------------------------------------|
| `ownedPurchasesReq` | Object to get OwnedPurchasesReq instance that contains request information. |

##### Return

| Return Type                      | Description                                                                                                                                                                                                     |
|:--------------------------------:|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Promise <OwnedPurchasesResult>` | Returns the task containing the API request result via promise instance. In the success scenario, OwnedPurchasesResult instance is returned, in the failure scenario, IapApiException object is returned.       |

##### Call Example

```javascript
/* Obtains the historical consumption information about all subscription receipts. */

//Constructing request.
const ownedPurchasesReq = {
  priceType: HmsIapModule.PRICE_TYPE_IN_APP_SUBSCRIPTION,//Use PRICE_TYPE_IN_APP_SUBSCRIPTION from HMSIapModule's constant values.
};

//Call the obtainOwnedPurchaseRecord API.
const ownedPurchasesResult = await HmsIapModule.obtainOwnedPurchaseRecord(ownedPurchasesReq);
```

#### startIapActivity(request)

Brings up in-app payment pages, including:  
- Subscription editing page 
- Subscription management page

##### Parameters

| Parameter             | Description                                                                   |
|:---------------------:|-------------------------------------------------------------------------------|
| `startIapActivityReq` | Object to get StartIapActivityReq instance that contains request information. |

##### Return

| Return Type  | Description                                                                                                                                                                                                              |
|:------------:|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Promise <>` | Returns the task containing the API request result via promise instance. In the success scenario, starts activity with the StartIapActivityResult instance, in the failure scenario, IapApiException object is returned. |

##### Call Example

Redirecting to the subscription management page:

```javascript
/* Brings up the subscription management page. */

//Constructing request.
const startIapActivityReq {
    type: HmsIapModule.TYPE_SUBSCRIBE_MANAGER_ACTIVITY, //Use TYPE_SUBSCRIBE_MANAGER_ACTIVITY from HMSIapModule's constant values.
};

//Call the StartIapActivity.
const startIapActivityResult = await HmsIapModule.startIapActivity(startIapActivityReq);
```

Switching to the subscription editing page:

```javascript
/* Redirecting to the subscription editing page. */

//Constructing request.
const startIapActivityReq {
    type: HmsIapModule.TYPE_SUBSCRIBE_EDIT_ACTIVITY, //Use TYPE_SUBSCRIBE_EDIT_ACTIVITY from HMSIapModule's constant values.
    subscribeProductId: 'REQUESTED_SUBSCRIPTION_PRODUCT_ID',
};

//Call the StartIapActivity.
const startIapActivityResult = await HmsIapModule.startIapActivity(startIapActivityReq);
```

  
### API Reference

| Data Type                  | Description                                                                                                 |
|----------------------------|-------------------------------------------------------------------------------------------------------------|
| ConsumeOwnedPurchaseReq    | Request of the consumeOwnedPurchase API.                                                                    |
| ConsumeOwnedPurchaseResult | Information returned when the consumeOwnedPurchase API is successfully called.                              |
| ConsumePurchaseData        | Represents an object that contains consumption result data.                                                 |
| InAppPurchaseData          | Purchase information about products including consumables, non-consumables, and subscriptions.              |
| IsEnvReadyResult           | Information returned when the isEnvReady API is successfully called.                                        |
| IsSandboxActivatedResult   | Information returned when the isSandboxActivated API is successfully called.                                |
| OwnedPurchasesReq          | Request of the obtainOwnedPurchases or obtainOwnedPurchaseRecord API.                                       |
| OwnedPurchasesResult       | Information returned when the obtainOwnedPurchases or obtainOwnedPurchaseRecord API is successfully called. |
| ProductInfo                | Details of a product.                                                                                       |
| ProductInfoReq             | Request of the obtainProductInfo API.                                                                       |
| ProductInfoResult          | Information returned when the obtainProductInfo API is successfully called.                                 |
| PurchaseIntentReq          | Request of the createPurchaseIntent API.                                                                    |
| PurchaseResultInfo         | Returned payment result information.                                                                        |
| StartIapActivityReq        | Request information of the startIapActivity API.                                                            |
| Status                     | Represents the task processing result.                                                                       |

Detailed documentation is available in the [developer.huawei.com](https://developer.huawei.com).


## 4. Configure parameters.    
No.
## 5. Licensing and Terms  
Huawei Reactive-Native SDK uses the Apache 2.0 license.
