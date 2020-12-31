# React-Native HMS Nearby

---

## Contents

  - [1. Introduction](#1-introduction)
  - [2. Installation Guide](#2-installation-guide)
    - [Creating a Project in App Gallery Connect](#creating-a-project-in-appgallery-connect)
    - [Configuring the Signing Certificate Fingerprint](#configuring-the-signing-certificate-fingerprint)
    - [Integrating the React-Native Nearby Plugin](#integrating-react-native-nearby-plugin)
  - [3. API Reference](#3-api-reference)
    - [HMSApplication](#hmsapplication)
      - [Constants](#hmsapplication-constants)
    - [HMSDiscovery](#hmsdiscovery)
      - [Constants](#hmsdiscovery-constants)
      - [Events](#hmsdiscovery-events)
    - [HMSTransfer](#hmstransfer)
      - [Constants](#hmstransfer-constants)
    - [HMSMessage](#hmsmessage)
      - [Constants](#hmsmessage-constants)
      - [Events](#hmsmessage-events)
      - [Data Types](#hmsmessage-data-types)
    - [HMSWifiShare](#hmswifishare)
      - [Constants](#hmswifishare-constants)
      - [Events](#hmswifishare-events)
  - [4. Configuration and Description](#4-configuration-and-description)
  - [5. Sample Project](#5-sample-project)
  - [6. Questions or Issues](#6-questions-or-issues)
  - [7. Licensing and Terms](#7-licensing-and-terms)

---

## 1. Introduction

This module enables communication between Huawei Nearby Service SDK and React Native platform. It exposes all functionality provided by Huawei Nearby Service SDK.

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

- To use HUAWEI Nearby Service, you need to enable the Nearby service. For details, please refer to [Enabling Services](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-enable_service#h1-1574822945685).


### Configuring the Signing Certificate Fingerprint

**Step 1.** Go to **Project Setting > General information**. In the **App information** field, click the icon next to SHA-256 certificate fingerprint, and enter the obtained **SHA256 certificate fingerprint**.

**Step 2.** After completing the configuration, click check mark.

### Integrating React Native Nearby Plugin

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
    classpath 'com.huawei.agconnect:agcp:1.4.2.301'    
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

- Add **apply plugin:"com.huawei.agconnect"** to the top of the file as shown below.

```groovy
apply plugin: "com.android.application"
apply plugin: "com.huawei.agconnect"
```

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
npm i @hmscore/react-native-hms-nearby
```

**Step 2:**  Run your project. 

- Run the following command to the project directory.

```bash
react-native run-android  
```

#### Option 2: Download Link

To integrate the plugin, follow the steps below:

**Step 1:** Download the React Native Nearby Plugin and place **react-native-hms-nearby** under **node_modules/@hmscore** of your React Native project, as shown in the directory tree below.

```
demo-app
  |_ node_modules
    |_ @hmscore
      |_ react-native-hms-nearby
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
    implementation project(":react-native-hms-nearby")    
    ...    
  }
}
```

**Step 3:** Add the following lines to the **android/settings.gradle** file in your project:

```groovy
include ':app'
include ':react-native-hms-nearby'
project(':react-native-hms-nearby').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-nearby/android')
```

**Step 4:**  Import the following classes to the **MainApplication.java** file of your project.

```java
import com.huawei.hms.rn.nearby.HMSNearby
```

Then, add the **HMSNearby()** to your **getPackages** method. In the end, your file will be similar to the following:

```java 
import com.huawei.hms.rn.nearby.HMSNearby;
...
@Override
protected List<ReactPackage> getPackages() {
    List<ReactPackage> packages = new PackageList(this).getPackages();
    packages.add(new HMSNearby()); // <-- Add this line 
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

### **HMSApplication**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [enableLogger()](#hmsapplicationenablelogger)            | Promise\<object> | This method enables HMSLogger capability.               |
| [disableLogger()](#hmsapplicationdisablelogger)          | Promise\<object> | This method disables HMSLogger capability.              |
| [setApiKey(apiKey)](#hmsapplicationsetapikeyapikey)      | Promise\<object> | Sets the API credential for your app.                   |
| [getApiKey()](#hmsapplicationgetapikey)                  | Promise\<object> | Obtains the current API credential.                     |
| [getVersion()](#hmsapplicationgetversion)                | Promise\<object> | Obtains the Nearby Service SDK version number.          |

#### Public Methods

##### HMSApplication.enableLogger()

This method enables HMSLogger capability which is used for sending usage analytics of Nearby Service SDK's methods to improve service quality.

Call Example

```js
 async enableLogger() {
    try {
      var result = await HMSApplication.enableLogger();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSApplication.disableLogger()

This method disables HMSLogger capability which is used for sending usage analytics of Nearby Service SDK's methods to improve service quality. 

Call Example

```js
 async disableLogger() {
    try {
      var result = await HMSApplication.disableLogger();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSApplication.setApiKey(apiKey)

This method sets the API credential for your app.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| apiKey     | string | api_key in agconnect-services.json file|

Call Example

```js
 async setApiKey(apiKey) {
    try {
      var result = await HMSApplication.setApiKey(apiKey);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSApplication.getApiKey()

Obtains the current API credential. 

Call Example

```js
 async getApiKey() {
    try {
      var result = await HMSApplication.getApiKey();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : "apiKey" }
```

##### HMSApplication.getVersion()

Obtains the Nearby Service SDK version number. 

Call Example

```js
 async getVersion() {
    try {
      var result = await HMSApplication.getVersion();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : "5.0.4.302" }
```

#### HMSApplication Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|SUCCESS                  |0                           |Success status.|
|FAILURE                  |-1                          |Failure status.|
|POLICY_FAIL              |8200                        |Policy is not valid. It does not match any of allowed policies.|
|STRING_PARAM_FAIL        |8201                        |Given string parameter is null or empty.|
|ENDPOINT_ID_FAIL         |8202                        |Given endpoint id array are not valid.|
|BYTES_DATA_FAIL          |8203                        |Given bytes data array is empty or exceeds max allowed size.|

Call Example

```js
  async setApiKey() {
    try {
      var result = await HMSApplication.setApiKey("your_api_key");
      if (result.status == HMSApplication.SUCCESS){
        console.log(result);
      }
    } catch (e) {
      console.log(e);
    }
  }
```

### **HMSDiscovery**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [acceptConnect(endpointId)](#hmsdiscoveryacceptconnectendpointid)  | Promise\<object> | Accepts connection for remote endpoint.       |
| [disconnect(endpointId)](#hmsdiscoverydisconnectendpointid)        | Promise\<object> | Disconnects from a remote endpoint.           |
| [rejectConnect(endpointId)](#hmsdiscoveryrejectconnectendpointid)  | Promise\<object> | Rejects a connection request from a remote endpoint.|
| [requestConnect(name,endpointId)](#hmsdiscoveryrequestconnectnameendpointid) | Promise\<object> | Sends a request to connect to a remote endpoint. |
| [startBroadcasting(name, serviceId, policy)](#hmsdiscoverystartbroadcastingnameserviceidpolicy) | Promise\<object> | Starts broadcasting. |
| [startScan(serviceId, policy)](#hmsdiscoverystartscanserviceidpolicy) | Promise\<object> | Starts scan.            |
| [stopBroadcasting()](#hmsdiscoverystopbroadcasting) | Promise\<object> | Stops broadcasting.                |
| [disconnectAll()](#hmsdiscoverydisconnectall) | Promise\<object> | Disconnects all connections.             |
| [stopScan()](#hmsdiscoverystopscan) | Promise\<object> | Stops discovering devices.             |

#### Public Methods

##### HMSDiscovery.acceptConnect(endpointId)

Accepts a connection. This API must be called before data transmission. If the connection request is not accepted within 8 seconds, the connection fails and needs to be re-initiated. Sets [DATA_ON_RECEIVED and DATA_ON_TRANSFER_UPDATE](#hmsdiscovery-events) which works after data is received.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| endpointId | string | ID of the remote endpoint.           |

Call Example

```javascript
  async acceptConnect(endpointId) {
    try {
      var result = await HMSDiscovery.acceptConnect(endpointId);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSDiscovery.disconnect(endpointId)

Disconnects from a remote endpoint. Then communication with the remote endpoint is no longer available.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| endpointId | string | ID of the remote endpoint.           |

Call Example

```javascript
  async disconnect(endpointId) {
    try {
      var result = await HMSDiscovery.disconnect(endpointId);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSDiscovery.rejectConnect(endpointId)

Rejects a connection request from a remote endpoint.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| endpointId | string | ID of the remote endpoint.           |

Call Example

```javascript
  async rejectConnect(endpointId) {
    try {
      var result = await HMSDiscovery.rejectConnect(endpointId);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSDiscovery.requestConnect(name,endpointId)

Sends a request to connect to a remote endpoint.  Sets [CONNECT_ON_DISCONNECTED, CONNECT_ON_ESTABLISH and CONNECT_ON_RESULT](#hmsdiscovery-events) which works during connection.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| name       | string | Local endpoint name.                 |
| endpointId | string | ID of the remote endpoint.           |

Call Example

```javascript
  async requestConnect(name,endpointId) {
    try {
      var result = await HMSDiscovery.requestConnect(name,endpointId);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSDiscovery.startBroadcasting(name,serviceId,policy)

Sends a request to connect to a remote endpoint. Sets [CONNECT_ON_DISCONNECTED, CONNECT_ON_ESTABLISH and CONNECT_ON_RESULT](#hmsdiscovery-events) which works during connection.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| name       | string | Local endpoint name.                 |
| serviceId  | string | Service ID. The app package name is recommended.           |
| policy     | number | Specifies the policy type: [MESH](#hmsdiscovery-constants), [P2P](#hmsdiscovery-constants), [STAR](#hmsdiscovery-constants) |

Call Example

```javascript
  async startBroadcasting(name,serviceId,policy) {
    try {
      var result = await HMSDiscovery.startBroadcasting(name,serviceId,policy);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSDiscovery.startScan(serviceId,policy)

 Starts to scan for remote endpoints with the specified service ID. Sets [SCAN_ON_FOUND and SCAN_ON_LOST](#events) which works when discovering a remote endpoint with the specified service ID.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| serviceId  | string | Service ID. The app package name is recommended.           |
| policy     | number | Specifies the policy type: [MESH](#constants), [P2P](#constants), [STAR](#constants)   |

Call Example

```javascript
  async startScan(serviceId,policy) {
    try {
      var result = await HMSDiscovery.startScan(serviceId,policy);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSDiscovery.stopBroadCasting()

Stops broadcasting.

Call Example

```javascript
  async stopBroadCasting() {
    try {
      var result = await HMSDiscovery.stopBroadCasting();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSDiscovery.disconnectAll()

Disconnects all connections.

Call Example

```javascript
  async disconnectAll() {
    try {
      var result = await HMSDiscovery.disconnectAll();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSDiscovery.stopScan()

Disconnects all connections.

Call Example

```javascript
  async stopScan() {
    try {
      var result = await HMSDiscovery.stopScan();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSDiscovery Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|MESH                     |1                           |Point-to-point connection **policy**, which supports an **M-to-N** connection topology.|
|P2P                      |2                           |Point-to-point connection **policy**, which supports a **1-to-1** connection topology.|
|STAR                     |3                           |Point-to-point connection **policy**, which supports a **1-to-N** connection topology.|
|CONNECT_ON_DISCONNECTED  |"connectOnDisconnected"     |Event type key that represents remote endpoint disconnection. |
|CONNECT_ON_ESTABLISH     |"connectOnEstablish"        |Event type key that represents remote endpoint connection establishment. |
|CONNECT_ON_RESULT        |"connectOnResult"           |Event type key that represents remote endpoint connection result. |
|SCAN_ON_FOUND            |"scanOnFound"               |Event type key that represents discovering an endpoint. |
|SCAN_ON_LOST             |"scanOnLost"                |Event type key that represents an endpoint is not discoverable. |
|DATA_ON_RECEIVED         |"dataOnReceived"            |Event type key that represents data received. |
|DATA_ON_TRANSFER_UPDATE  |"dataOnTransferUpdate"      |Event type key that represents data status.|

Call Example

```js
  async startScan() {
    try {
      var result = await HMSDiscovery.startScan("your_service_id", HMSDiscovery.STAR);
      if (result.status == HMSApplication.SUCCESS) {
        console.log(result);
      }
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSDiscovery Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| CONNECT_ON_DISCONNECTED         | Event emitted when the remote endpoint disconnects or the connection is unreachable. |
| CONNECT_ON_ESTABLISH            | Event emitted when a connection has been established and both ends need to confirm whether to accept the connection.                   |
| CONNECT_ON_RESULT               | Event emitted when either end accepts or rejects the connection. |
| SCAN_ON_FOUND                   | Event emitted when an endpoint is discovered. |
| SCAN_ON_LOST                    | Event emitted when an endpoint is no longer discoverable. |
| DATA_ON_RECEIVED                | Event emitted to obtain received data.                |
| DATA_ON_TRANSFER_UPDATE         | Event emitted to obtain the data sending or receiving status. |

Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSDiscovery);
    
    eventEmitter.addListener(HMSDiscovery.CONNECT_ON_DISCONNECTED, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    endpointId : "", // id of the remote endpoint
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSDiscovery.CONNECT_ON_ESTABLISH, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    endpointId : "",                  // id of the remote endpoint.
       *    authCode : "",                    // symmetric authentication codes from both ends.
       *    endpointName :"",                 // name of the remote endpoint.
       *    isRemoteConnect : true or false   // whether the connection request is initiated by the remote endpoint.
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSDiscovery.CONNECT_ON_RESULT, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    endpointId : "",    // id of the remote endpoint.
       *    statusCode : 0,     // connection status code 0 = SUCCESS, 8010 = Rejected
       *    statusMessage :"",  // status message
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSDiscovery.SCAN_ON_FOUND, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    endpointId : "", // id of the remote endpoint.
       *    name : "",       // name of the discovered endpoint.
       *    serviceId :""    // service id of the discovered endpoint.
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSDiscovery.SCAN_ON_LOST, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    endpointId : "", // id of the remote endpoint.
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSDiscovery.DATA_ON_RECEIVED, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    endpointId : "",                             // id of the remote endpoint.
       *    type : 1 or 2 or 3,                          // data type
       *    id :""                                       // unique ID of the payload.
       *    // if type is 1 means File 
       *    size : "",                                   // file size
       *    fileUri : "",                                // file uri 
       *    // if type is 2 means Bytes or 3 means STREAM
       *    data : []                                    // data array contains bytes data
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSDiscovery.DATA_ON_TRANSFER_UPDATE, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    endpointId : "",           // id of the remote endpoint.
       *    transferredBytes : "",     // the number of transferred bytes.
       *    dataId :"",                // the data ID.
       *    hashCode : 123XXXXX,       // hash code
       *    status : 1 or 2 or 3 or 4  // status of the transfer state
       * }
       * 
      */
    });
  }
```

### **HMSTransfer**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [transferBytes(bytes,endpointIds)](#hmstransfertransferbytesbytesendpointids)        | Promise\<object> | Transfers given bytes to given endpoint ids.                   |
| [transferFile(uri,endpointIds)](#hmstransfertransferfileuriendpointids)              | Promise\<object> | Transfers file from given URI to given endpoint ids.      |
| [transferStream(endpoint,endpointIds)](#hmstransfertransferstreamendpointendpointids)| Promise\<object> | Transfers stream for given endpoint to given endpoint ids. |
| [cancelDataTransfer(id)](#hmstransfercanceldatatransferid)                           | Promise\<object> | Cancels data transmission for given data id.                    |

#### Public Methods

##### HMSTransfer.transferBytes(bytes,endpointIds)

Transfers given bytes to given endpoint ids.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| bytes      | number[] | data to be transferred. Value should be in a range of -127 and +127|
| endpointIds| string[] | ids of remote endpoints to send data.|

Call Example

```js
  async transferBytes() {
    try {
      var result = await HMSTransfer.transferBytes(
        [1,1,1,1],
        ["endpointId"]
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSTransfer.transferFile(uri,endpointIds)

Transfers file from given uri to given endpoint ids. Transferred file is saved in subscriber's device under downloads/nearby/ directory with name data id.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| uri        | number[] | file uri. |
| endpointIds| string[] | ids of remote endpoints to send data.|

Call Example

```js
  async transferFile() {
    try {
      var result = await HMSTransfer.transferFile(
        "file_uri",
        ["endpointId"]
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSTransfer.transferStream(endpoint,endpointIds)

Transfers stream for given endpoint to given endpoint ids.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| endpoint   | string | endpoint. |
| endpointIds| string[] | ids of remote endpoints to send data.|

Call Example

```js
  async transferStream() {
    try {
      var result = await HMSTransfer.transferStream(
        "https://developer.huawei.com/",
        ["endpointId"]
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSTransfer.cancelDataTransfer(id)

Cancels data transmission when sending or receiving for given data id.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| id         | string | data id. |

Call Example

```js
  async cancelDataTransfer() {
    try {
      var result = await HmsTransferModule.cancelDataTransfer("dataId");
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

#### HMSTransfer Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|FILE                     |1                           | File data type.|
|BYTES                    |2                           | Bytes data type.|
|STREAM                   |3                           | Stream data type.|
|MAX_SIZE_DATA            |32768                       | Maximum length of bytes that can be sent. |
|TRANSFER_STATE_SUCCESS   |1                           | Transfer state that represents data sent successfully.|
|TRANSFER_STATE_FAILURE   |2                           | Transfer state that represents data sent failure.|
|TRANSFER_STATE_IN_PROGRESS|3                          | Transfer state that represents data is being transmitted.|
|TRANSFER_STATE_CANCELED  |4                           | Transfer state that represents data sending cancelled.|

Call Example

```js
    eventEmitter.addListener(HMSDiscovery.DATA_ON_RECEIVED, (event) => {
      console.log(event);
      if (event.type == HMSTransfer.FILE){
        // your code
      }
      else if (event.type == HMSTransfer.BYTES) {
        // your code  
      }
      else if (event.type == HMSTransfer.STREAM) {
        // your code
      }
    });
```

### **HMSMessage**

#### Public Method Summary

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [put(messageConfig,bytes)](#hmsmessageputmessageconfigbytes) | Promise\<object> | Publishes a message and broadcasts a token for nearby devices to scan. |
| [putWithOption(messageConfig,bytes,putOptionConfiguration)](#hmsmessageputwithoptionmessageconfigbytesputoptionconfiguration) | Promise\<object> | Publishes a message and broadcasts a token for nearby devices to scan.   |
| [registerStatusCallback()](#hmsmessageregisterstatuscallback)      | Promise\<object> | Registers to [STATUS_ON_CHANGED](#hmsmessage-events) event which will notify your app of key events. |
| [unRegisterStatusCallback()](#hmsmessageunregisterstatuscallback)   | Promise\<object> | Cancels the [STATUS_ON_CHANGED](#hmsmessage-events) event registered before. |
| [getMessage()](#hmsmessagegetmessage)     | Promise\<object> | Obtains messages from the cloud using the default options. |
| [getMessageWithOption(getOptionConfiguration)](#hmsmessagegetmessagewithoptiongetoptionconfiguration)     | Promise\<object> |Obtains messages from the cloud using the customized options. |
| [getMessagePending()](#hmsmessagegetmessagepending)     | Promise\<object> |Identifies only BLE beacon messages. |
| [getMessagePendingWithOption(getOptionConfiguration)](#hmsmessagegetmessagependingwithoptiongetoptionconfiguration)     | Promise\<object> |Identifies only BLE beacon messages with customized options. |
| [unput(messageConfig,bytes)](#hmsmessageunputmessageconfigbytes)     | Promise\<object> | Cancels message publishing. |
| [unget()](#hmsmessageunget)     | Promise\<object> | Cancels message subscription. |
| [ungetPending()](#hmsmessageungetpending)     | Promise\<object> | Cancels background message subscription. |

#### Public Methods

##### HMSMessage.put(messageConfig,bytes)

Publishes a message and broadcasts a token for nearby devices to scan.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| messageConfig | [MessageConfig](#hmsmessage-data-types) | message configuration.           |
| bytes     | number[] | message content.  Value range is [-127, 127]  |

Call Example

```js
  async put() {
    try {
      var result = await HMSMessage.put(
        {
         type: HMSMessage.MESSAGE_TYPE_EDDYSTONE_UID,
         namespace: HMSMessage.MESSAGE_NAMESPACE_RESERVED
        },
        [1,2,3,4,5]
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSMessage.putWithOption(messageConfig,bytes,putOptionConfiguration)

Publishes a message and broadcasts a token for nearby devices to scan. Message is published only to apps that use the same project ID and have registered the message type with the cloud for subscription.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| messageConfig | [MessageConfig](#hmsmessage-data-types) | message configuration.           |
| bytes     | number[] | message content.  Value range is [-127, 127]. |
| putOptionConfiguration     | [PutOptionConfig](#hmsmessage-data-types) | Obtains options for calling this method.  |

Call Example

```js
  async putWithOption() {
    try {
      var result = await HMSMessage.putWithOption(
        {
          type: HMSMessage.MESSAGE_TYPE_EDDYSTONE_UID,
          namespace: HMSMessage.MESSAGE_NAMESPACE_RESERVED
        },
        [1,2,3,4,5],
        {
          policy : {
            findingMode: HMSMessage.POLICY_FINDING_MODE_DEFAULT,
            distanceType: HMSMessage.POLICY_DISTANCE_TYPE_DEFAULT,
            ttlSeconds: HMSMessage.POLICY_TTL_SECONDS_DEFAULT
          },
          setCallback : true // registers for event PUT_ON_TIMEOUT to obtain message publishing timeout information
        }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSMessage.registerStatusCallback()

Registers to [STATUS_ON_CHANGED](#hmsmessage-events) event, which will notify your app of key events. When your app calls one of the APIs for the first time, the function will return the status.

Call Example

```js
  async registerStatusCallback() {
    try {
      var result = await HMSMessage.registerStatusCallback();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSMessage.unRegisterStatusCallback()

Cancels the [STATUS_ON_CHANGED](#hmsmessage-events) event registered before.

Call Example

```js
  async unRegisterStatusCallback() {
    try {
      var result = await HMSMessage.unRegisterStatusCallback();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSMessage.getMessage()

Obtains messages from the cloud using the default options. Uses [MESSAGE_ON_FOUND and MESSAGE_ON_LOST](#hmsmessage-events) events to obtain messages.

Call Example

```js
  async getMessage() {
    try {
      var result = await HMSMessage.getMessage();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSMessage.getMessageWithOption(getOptionConfiguration)

Obtains messages from the cloud using the customized options. Only messages with the same project ID can be obtained. Uses [MESSAGE_ON_FOUND and MESSAGE_ON_LOST](#hmsmessage-events) events to obtain messages.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| getOptionConfiguration | [GetOptionConfig](#hmsmessage-data-types) | message obtaining configuration.  |

Call Example

```js
  async getMessageWithOption() {
    try {
      var result = await HMSMessage.getMessageWithOption(
        {
          policy: {
            findingMode: HMSMessage.POLICY_FINDING_MODE_DEFAULT,
            distanceType: HMSMessage.POLICY_DISTANCE_TYPE_DEFAULT,
            ttlSeconds: HMSMessage.POLICY_TTL_SECONDS_DEFAULT
          },
          picker: {
            includeAllTypes: true,
            includeIBeaconIds: [{
              iBeaconUuid: "<your_beacon_uuid>",
              major: "<your_major_value>",
              minor: "<your_minor_value>"
            }],
            includeNamespaceType: [{
              namespace: "<your_namespace>",
              type: "<your_type>"
            }]
          },
          setCallback: true // registers for event GET_ON_TIMEOUT to obtain message subscription timeout information
        }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSMessage.getMessagePending()

It subscribes to messages published by nearby devices in a persistent and low-power manner and uses the default configuration. Scanning is going on no matter whether your app runs in the background or foreground. The scanning stops when the app process is killed. Uses [MESSAGE_ON_FOUND, MESSAGE_ON_LOST, BLE_ON_SIGNAL_CHANGED, DISTANCE_ON_CHANGED](#hmsmessage-events) events to obtain information.

Call Example

```js
  async getMessagePending() {
    try {
      var result = await HMSMessage.getMessagePending();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSMessage.getMessagePendingWithOption(getOptionConfiguration)

Identifies only BLE beacon messages. Scanning is going on no matter whether your app runs in the background or foreground. The scanning stops when the app process is killed. Uses [MESSAGE_ON_FOUND, MESSAGE_ON_LOST, BLE_ON_SIGNAL_CHANGED, DISTANCE_ON_CHANGED](#hmsmessage-events) events to obtain information.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| getOptionConfiguration | [GetOptionConfig](#hmsmessage-data-types) | message obtaining configuration.  |

Call Example

```js
  async getMessagePendingWithOption() {
    try {
      var result = await HMSMessage.getMessagePendingWithOption(
        {
          policy: {
            findingMode: HMSMessage.POLICY_FINDING_MODE_DEFAULT,
            distanceType: HMSMessage.POLICY_DISTANCE_TYPE_DEFAULT,
            ttlSeconds: HMSMessage.POLICY_TTL_SECONDS_DEFAULT
          },
          picker: {
            includeAllTypes: true,
            includeIBeaconIds: [{
              iBeaconUuid: "<your_beacon_uuid>",
              major: "<your_major_value>",
              minor: "<your_minor_value>"
            }],
            includeNamespaceType: [{
              namespace: "<your_namespace>",
              type: "<your_type>"
            }]
          },
          setCallback: true // registers for event GET_ON_TIMEOUT to obtain message subscription timeout information
        }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSMessage.unput(messageConfig,bytes)

Cancels message publishing.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| messageConfig | [MessageConfig](#hmsmessage-data-types) | message configuration.  |
| bytes | number[] | message data. Value range is [-127, 127]  |

Call Example

```js
  async unput() {
    try {
      var result = await HMSMessage.unput(
        {
         type: HMSMessage.MESSAGE_TYPE_EDDYSTONE_UID,
         namespace: HMSMessage.MESSAGE_NAMESPACE_RESERVED
        },
        [1,2,3,4,5]
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```


##### HMSMessage.unget()

 Cancels a message subscription. Uses [MESSAGE_ON_FOUND and MESSAGE_ON_LOST](#hmsmessage-events) events to obtain information.

Call Example

```js
  async unget() {
    try {
      var result = await HMSMessage.unget();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSMessage.ungetPending()

 Cancels the background message subscription. Uses [MESSAGE_ON_FOUND, MESSAGE_ON_LOST, BLE_ON_SIGNAL_CHANGED, DISTANCE_ON_CHANGED](#hmsmessage-events) events to obtain information.

Call Example

```js
  async ungetPending() {
    try {
      var result = await HMSMessage.ungetPending();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

#### HMSMessage Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|MAX_CONTENT_SIZE              |64KB                   | Maximum size of the message content, in bytes. |
|MAX_TYPE_LENGTH               |16                     | Maximum size of a message type, in bytes. |
|MESSAGE_NAMESPACE_RESERVED    |"_reserved_namespace"  | Namespace reserved for special messages. |
|MESSAGE_TYPE_EDDYSTONE_UID    |"_eddystone_uid"       | Message type. |
|MESSAGE_TYPE_IBEACON_ID       |"_ibeacon_id"          | Message type. |
|POLICY_FINDING_MODE_DEFAULT   |0                      | To discover nearby devices, broadcasts a sharing code and scans for other devices' sharing codes. |
|POLICY_FINDING_MODE_BROADCAST |1                      | To discover nearby devices, broadcasts a sharing code for other devices to scan.|
|POLICY_FINDING_MODE_SCAN      |2                      | To discover nearby devices, broadcasts a sharing code and scans for other devices' sharing codes.|
|POLICY_DISTANCE_TYPE_DEFAULT  |0                      | Allows messages to be transmitted over any distance.|
|POLICY_DISTANCE_TYPE_EARSHOT  |1                      | Allows messages to be transmitted only within the earshot.|
|POLICY_TTL_SECONDS_DEFAULT    |240                    | Default TTL, in seconds.|
|POLICY_TTL_SECONDS_INFINITE   |0x7FFFFFFF             | Indefinite TTL, in seconds. |
|POLICY_TTL_SECONDS_MAX        |86400                  | Maximum TTL, in seconds.|
|BLE_UNKNOWN_TX_POWER          |0x80000000             | Unknown transmit power level.|
|PRECISION_LOW                 |1                      | Precision of the distance estimated based on the BLE signal strength.|
|GET_ON_TIMEOUT                |"getOnTimeOut"         | Event type key that represents message subscription expired.|
|PUT_ON_TIMEOUT                |"putOnTimeOut"         | Event type key that represents message publishing expired.|
|STATUS_ON_CHANGED             |"statusOnChanged"      | Event type key that represents app permission changed.|
|BLE_ON_SIGNAL_CHANGED         |"onBleSignalChanged"   | Event type key that represents BLE signal changes with associated message.|
|DISTANCE_ON_CHANGED           |"onDistanceChanged"    | Event type key that represents estimated distance changes to a message.|
|MESSAGE_ON_FOUND              |"messageOnFound"       | Event type key that represents message found.|
|MESSAGE_ON_LOST               |"messageOnLost"        | Event type key that represents message is no longer detectable.|

Call Example

```js
  eventEmitter.addListener(HMSMessage.STATUS_ON_CHANGED, (event) => {
    console.log(event);
  });
```

#### HMSMessage Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| GET_ON_TIMEOUT                  | Event emitted when the remote endpoint disconnects or the connection is unreachable. |
| PUT_ON_TIMEOUT                  | Event emitted when a connection has been established and both ends need to confirm whether to accept the connection.                       |
| STATUS_ON_CHANGED               | Event emitted when either end accepts or rejects the connection. |
| BLE_ON_SIGNAL_CHANGED           | Event emitted when the first BLE broadcast message associated with Message or the BLE signal associated with Message changes. This event currently supports only BLE beacon messages. |
| DISTANCE_ON_CHANGED             | Called when the estimated distance to a message changes. This callback currently supports only BLE beacon messages. |
| MESSAGE_ON_FOUND                | Called when a message is detected for the first time or a message is no longer detectable. |
| MESSAGE_ON_LOST                 | Called when a message is no longer detectable. This callback currently suits BLE beacon messages the best. For other messages, it may not respond in a timely manner. |

Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSMessage);
    
    eventEmitter.addListener(HMSMessage.GET_ON_TIMEOUT, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    onTimeout : "Message subscription expired"
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSMessage.PUT_ON_TIMEOUT, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    onTimeout : "Message publishing expired"
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSMessage.STATUS_ON_CHANGED, (event) => {
      console.log(event);
     /**
       * Sample Event Result
       * {
       *    onPermissionChanged : true or false //permission granted or not
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSMessage.BLE_ON_SIGNAL_CHANGED, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    namespace : ""  // namespace
       *    type : "",      // message type.
       *    content :[]     // message content in byte[]
       *    rSSI : 0        // received signal strength in dBm. The value range is [–127,127].
       *    txPower :0      // transmit power from 1 m away, in dBm.
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSMessage.DISTANCE_ON_CHANGED, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    namespace : ""  // namespace
       *    type : "",      // message type.
       *    content :[]     // message content in byte[]
       *    isKnown : 0     // distance is unknown or not
       *    meters :0.0     // estimated distance, in m.
       *    precision : 0   // precision of the estimated instance.
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSMessage.MESSAGE_ON_FOUND, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    namespace : ""  // namespace
       *    type : "",      // message type.
       *    content :[]     // message content in byte[]
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSMessage.MESSAGE_ON_LOST, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    namespace : ""  // namespace
       *    type : "",      // message type.
       *    content :[]     // message content in byte[]
       * }
       * 
      */
    });
  }
```

#### HMSMessage Data Types
 
#### MessageConfig
An object that represents message configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| type                              | string    | Message type : [MESSAGE_TYPE_EDDYSTONE_UID or MESSAGE_TYPE_IBEACON_ID](#hmsmessage-constants) |
| namespace                         | string    | Message namespace : [MESSAGE_NAMESPACE_RESERVED](#hmsmessage-constants)  |

#### PutOptionConfig
An object that represents put option configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| policy                            | [PolicyConfig](#policyconfig) | policy configuration object. |
| setCallback                       | boolean    | Sets [PUT_ON_TIMEOUT](#hmsmessage-events) event if true. |

#### GetOptionConfig
An object that represents get option configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| policy                            | [PolicyConfig](#policyconfig) | policy configuration object. |
| picker                            | [PickerConfig](#PickerConfig) | policy configuration object. |
| setCallback                       | boolean    | Sets [GET_ON_TIMEOUT](#hmsmessage-events) event if true. |

#### PolicyConfig
An object that represents policy configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| findingMode                       | number    | Sets the scanning mode, which determines how devices detect each other. [POLICY_FINDING_MODE_DEFAULT, POLICY_FINDING_MODE_BROADCAST and POLICY_FINDING_MODE_SCAN](#hmsmessage-constants) |
| distanceType                      | number    | Sets the distance for message subscription and publishing. [POLICY_DISTANCE_TYPE_DEFAULT and POLICY_DISTANCE_TYPE_EARSHOT](#hmsmessage-constants)   |
| ttlSeconds                        | number    | Sets the TTL of a published or subscribed message. [POLICY_TTL_SECONDS_DEFAULT, POLICY_TTL_SECONDS_INFINITE and POLICY_TTL_SECONDS_MAX](#hmsmessage-constants)  |

#### PickerConfig
An object that represents picker configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| includeAllTypes                   | boolean   | Includes all messages published by the app. |
| includeEddyStoneUids              | [[IncludeEddyStoneUidConfig]](#IncludeEddyStoneUidConfig)|Includes Eddystone UIDs.   |
| includeIBeaconIds                 | [[IncludeIBeaconIdsConfig]](#IncludeIBeaconIdsConfig)    | Includes iBeacon ID messages.  |
| picker                            | [PickerConfig](#PickerConfig)                            | Includes the previously constructed picker.  |
| includeNamespaceType              | [[NameSpaceTypeConfig]](#NameSpaceTypeConfig)            | Picks among all messages in the specified namespace and with the specified type.  |

#### IncludeEddyStoneUidConfig
An object that represents includeEddyStoneUids configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| hexNamespace                      | string    |  10-byte namespace of an Eddystone UID (in hexadecimal format), for example, "c526dfec5403adc62585". |
| hexInstance                       | string    | 6-byte instance of an Eddystone UID (in hexadecimal format), for example, "32ddbcad1576".   |

#### IncludeIBeaconIdsConfig
An object that represents includeEddyStoneUids configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| iBeaconUuid                       | string    | UUID. |
| major                             | string    | major   |
| minor                             | string    | minor   |

#### NameSpaceTypeConfig
An object that represents includeNamespaceType configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| namespace                         | string    | Namespace of a message. The value cannot be empty or contain asterisks (*). |
| type                              | string    | Type of a message. The value cannot be empty or contain asterisks (*).   |

### **HMSWifiShare**

#### Public Method Summary

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [startWifiShare(policy)](#hmswifisharestartwifisharepolicy) | Promise\<object> | Enables the Wi-Fi sharing function.                  |
| [stopWifiShare()](#hmswifisharestopwifishare)               | Promise\<object> | Disables the Wi-Fi sharing function.                 |
| [shareWifiConfig(endpointid)](#hmswifisharesharewificonfigendpointid) | Promise\<object> | Shares Wi-Fi with a remote device.                   |

#### Public Methods

##### HMSWifiShare.startWifiShare(policy)

Enables the Wi-Fi sharing function. Sets [WIFI_ON_FOUND, WIFI_ON_LOST, WIFI_ON_FETCH_AUTH_CODE and WIFI_ON_RESULT](#hmswifishare-events) events.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| policy     | number | Specifies the policy type : [SET and SHARE](#hmswifishare-constants)|

Call Example

```js
 async startWifiShare() {
    try {
      var result = await HMSWifiShare.startWifiShare(HMSWifiShare.SHARE);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSWifiShare.stopWifiShare()

Disables the Wi-Fi sharing function.

Call Example

```js
 async stopWifiShare() {
    try {
      var result = await HMSWifiShare.stopWifiShare());
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSWifiShare.shareWifiConfig(endpointid)

Enables the Wi-Fi sharing function.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| endpointid | string | ID of the remote endpoint.|

Call Example

```js
 async shareWifiConfig() {
    try {
      var result = await HMSWifiShare.shareWifiConfig("endpoint_id");
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

#### HMSWifiShare Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|SHARE                    |1                           | Wi-Fi sharing policy. |
|SET                      |2                           | Configures the Wi-Fi sharing mode. |
|WIFI_ON_FOUND            |"wifiSOnFound"              | Event type key that represents a nearby endpoint on which Wi-Fi can be configured is discovered. |
|WIFI_ON_LOST             |"wifiOnLost"                | Event type key that represents an endpoint on which Wi-Fi can be configured is lost.  |
|WIFI_ON_RESULT           |"wifiOnResult"              | Event type key that represents Wi-Fi sharing result.  |
|WIFI_ON_FETCH_AUTH_CODE  |"wifiOnFetchAuthCode"       | Event type key that represents obtaining the verification code for Wi-Fi sharing.|

Call Example

```js
  async startWifiShare() {
    try {
      var result = await HMSWifiShare.startWifiShare(HMSWifiShare.SET);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSWifiShare Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| WIFI_ON_FOUND                   | Event emitted when a nearby endpoint on which Wi-Fi can be configured is discovered. Endpoint information can be obtained for the display and selection of target endpoints.|
| WIFI_ON_LOST                    | Event emitted when an endpoint on which Wi-Fi can be configured is lost.   |
| WIFI_ON_RESULT                  | Event emitted when Wi-Fi share results obtained.  |
| WIFI_ON_FETCH_AUTH_CODE         | Event emitted when obtains the verification code for Wi-Fi sharing. The verification code must be obtained and displayed on the UI so that users can confirm the target endpoint.  |

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSWifiShare);
    
    eventEmitter.addListener(HMSWifiShare.WIFI_ON_FOUND, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    endpointId : "", // id of the remote endpoint.
       *    name : "",       // name of the discovered endpoint.
       *    serviceId :""    // service id of the discovered endpoint.
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSWifiShare.WIFI_ON_LOST, (event) => {
      console.log(event);
     /**
       * Sample Event Result
       * {
       *    endpointId : "", // id of the remote endpoint.
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSWifiShare.WIFI_ON_RESULT, (event) => {
      console.log(event);
     /**
       * Sample Event Result
       * {
       *    endpointId : "", // id of the remote endpoint.
       *    statusCode : 0   // Wi-Fi configuration result status code.
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSWifiShare.WIFI_ON_FETCH_AUTH_CODE, (event) => {
      console.log(event);
       /**
       * Sample Event Result
       * {
       *    endpointId : "", // id of the remote endpoint.
       *    authCode : ""    // Wi-Fi sharing verification code.
       * }
       * 
      */
    });
  }
```

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
### Permissions
To use Nearby Kit you need to dynamically get permissions from user. Nearby Kit uses the given permissions below by default. 
```xml
<manifest ...>
    <uses-permission android:name="android.permission.INTERNET" />
    <!-- Required for Nearby -->
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <!-- Required for FILE payloads Nearby Service -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
</manifest>
```

---

## 5. Sample Project

This plugin includes a demo project in the [example](example) folder, there you can find more usage examples.

<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-nearby/example/.docs/main.jpg" width = 45% height = 45% style="margin:1em">
<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-nearby/example/.docs/connection.jpg" width = 45% height = 45% style="margin:1em">
<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-nearby/example/.docs/message.jpg" width = 45% height = 45% style="margin:1em">
<img src="https://github.com/HMS-Core/hms-react-native-plugin/raw/master/react-native-hms-nearby/example/.docs/wifishare.jpg" width = 45% height = 45% style="margin:1em">

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
