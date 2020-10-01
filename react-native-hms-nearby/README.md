# React-Native HMS Nearby Kit

## Contents
- [1. Introduction](#1-introduction)
- [2. Installation Guide](#2-installation-guide)
- [3. API Reference](#3-api-reference)
- [4. Configuration Description](#4-configuration-description)
- [5. Sample Project](#5-sample-project)
- [6. Questions or Issues](#6-questions-or-issues)
- [7. Licencing and Terms](#7-licencing-and-terms)

---

## 1. Introduction

React-Native Hms Nearby Kit allows apps to easily discover nearby devices and set up communication with them using technologies such as Bluetooth and Wi-Fi.
This module enables communication between Huawei Nearby SDK and React Native platform. It exposes functionality provided by Huawei Nearby SDK.

---

## 2. Installation Guide
Before you get started, you must register as a HUAWEI Developer and complete identity verification on the [HUAWEI Developer](https://developer.huawei.com/consumer/en/) website. For details, please refer to [Register a HUAWEI ID](https://developer.huawei.com/consumer/en/doc/10104).

Set up React Native development environment. You may refer to [React Native Environment Setup](https://reactnative.dev/docs/environment-setup).

### Creating a Project in AppGallery Connect
Creating an app in AppGallery Connect is required in order to communicate with the Huawei services. To create an app, perform the following steps:

**Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html)  and select **My projects**.

**Step 2:** Select your project from the project list or create a new one by clicking the **Add Project** button.

**Step 3:** Go to **Project Setting** > **General information**, and click **Add app**.
If an app exists in the project and you need to add a new one, expand the app selection area on the top of the page and click **Add app**.

**Step 4:** On the **Add app** page, enter the app information, and click **OK**.

### Configuring the Signing Certificate Fingerprint

A signing certificate fingerprint is used to verify the authenticity of an app when it attempts to access an HMS Core (APK) through the HMS SDK. Before using the HMS Core (APK), you must locally generate a signing certificate fingerprint and configure it in the **AppGallery Connect**. You can refer to 3rd and 4th steps of [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#2) codelab tutorial for the certificate generation. Perform the following steps after you have generated the certificate.

**Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and select your project from **My Projects**. Then go to **Project Setting** > **General information**. In the **App information** field, click the  icon next to SHA-256 certificate fingerprint, and enter the obtained **SHA-256 certificate fingerprint**.

**Step 2:**  After completing the configuration, click **OK** to save the changes. (Check mark icon)

### Integrating React Native Nearby Plugin

#### **Prerequisites**
**Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and select your project from **My Projects**. Then go to **Build > Nearby Service** and click **Enable Now** to enable the Huawei Nearby Service. You can also check **Manage APIs** tab on the **Project Settings** page for the enabled HMS services on your app.

**Step 2:** Go to **Project Setting > General information** page, under the **App information** field, click **agconnect-services.json** to download the configuration file.

**Step 3:** Copy the **agconnect-services.json** file to the **project-dir/android/app** directory of your project.

**Step 4:** Open the **build.gradle** file in the **project-dir/android** directory of your project.
- Navigate to the **buildscript** section and configure the Maven repository address and agconnect plugin for the HMS SDK.
  ```gradle
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
- Go to **allprojects** and configure the Maven repository address for the HMS SDK.
  
  ```gradle
  allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://developer.huawei.com/repo/' }
    }
  }
  ```
**Step 5:** Open the **build.gradle** file in the **project-dir/android/app** directory.
- Add `apply plugin: 'com.huawei.agconnect'` line after other `apply` entries.
  ```gradle
  apply plugin: 'com.android.application'
  apply plugin: 'com.huawei.agconnect'
  ```
- Set your package name in **defaultConfig > applicationId** and set **minSdkVersion** to **21** or higher. Package name must match with the **package_name** entry in **agconnect-services.json** file. 
  ```gradle
  defaultConfig {
      applicationId "<package_name>"
      minSdkVersion 21
      /*
      * <Other configurations>
      */
  }

  dependencies {    
        /*
        * <Other dependencies>
        */
        implementation 'com.huawei.agconnect:agconnect-core:1.4.1.300'
    }
  ```
**Step 6:** Create a file **project-dir/android/app/keystore_file.jks** that contains a reference to your keystore which you generated using [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#2). Fill the required parameters in **project-dir/android/app/build.gradle** file.
```
signingConfigs {
    config {
        storeFile file('<keystore_file>.jks')
        storePassword '<keystore_password>'
        keyAlias '<key_alias>'
        keyPassword '<key_password>'
    }
}
...
```
> Warning: Keep this file private and don't include it on the public source control.

**Step 7:** Go to **'project-dir/android/app/proguard-rules.pro'** and put the related configurations given below. 

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

If you are using AndResGuard, add it to the allowlist in the obfuscation configuration file.

```
"R.string.hms*",
"R.string.connect_server_fail_prompt_toast",
"R.string.getting_message_fail_prompt_toast",
"R.string.no_available_network_prompt_toast",
"R.string.third_app_*",
"R.string.upsdk_*",
"R.layout.hms*",
"R.layout.upsdk_*",
"R.drawable.upsdk*",
"R.color.upsdk*",
"R.dimen.upsdk*",
"R.style.upsdk*", 
"R.string.agc*"
```

#### **Using Npm**

**Step 1:** Please make sure that you completed [Prerequsites](#prerequisites) part.

**Step 2:** Download the library using command below.

```bash
npm i @hmscore/react-native-hms-nearby
```

**Step 3:** The **node_modules** directory structure should be like given below. 

    project-dir
        |_ node_modules
            |_ ...
            |_ @hmscore/react-native-hms-nearby
            |_ ...

#### **Using Download Link**

**Step 1:** Please make sure that you completed [Prerequsites](#prerequisites) part.

**Step 2:** Download the library from .

**Step 3:** The **node_modules** directory structure should be like given below. 

    project-dir
        |_ node_modules
            |_ ...
            |_ @hmscore/react-native-hms-nearby
            |_ ...

**Step 4:** Open **settings.gradle** located under **project-dir/android** directory and add following lines.

```gradle
include ':react-native-hms-nearby'
project(':react-native-hms-nearby').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-nearby/android')
```

**Step 5:** Open **project-dir/android/app/build.gradle** file. Configure build dependencies of your project.

```gradle
dependencies {    
        implementation project(":react-native-hms-nearby")    
        ...   
        implementation 'com.huawei.agconnect:agconnect-core:1.4.1.300'
    }
```

**Step 6:** Open **project-dir/android/app/src/main/java/your_package/MainApplication.java** file and modify the getPackages method as following.

```java
import com.huawei.hms.rn.nearby.HmsNearbyPackage;
```
Then add the following line to your getPackages() method.
```java
packages.add(new HmsNearbyPackage());
```

---

## 3. API Reference    

### **HmsDiscoveryModule**
Represents a module that provides methods for scanning and broadcasting.

#### Public Method Summary 

| Method Name             | Return Type                | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|createBroadcastOption()  |Promise (String)            |Sets broadcast option object, using the given policy number. This object is used when startBroadcasting method call.|
|getPolicyBroadcast()     |Promise (Number)            |Returns broadcast option policy number that set before using createBroadcastOption method.|
|hashCodeBroadcast()      |Promise (Number)            |Returns the hash code of broadcast option object that set before using createBroadcastOption method.|
|equalsBroadcast()        |Promise (Boolean)           |Checks if broadcast option object that set before using createBroadcastOption, is equal to a new one that is created by given policy number. Returns True if they are equal or False if they are not.|
|createScanOption()       |Promise (String)            |Sets the scan option object by using the given policy number. This object is used when startScan method call.|
|getPolicyScan()          |Promise (Number)            |Returns scan option policy number that set before using createScanOption method.|
|hashCodeScan()           |Promise (Number)            |Returns the hash code of scan option that set before using createScanOption method.|
|equalsScan()             |Promise (Boolean)           |Checks if scan option object that set before using createScanOption, is equal to a new one that is created by given policy number. Returns True if they are equal or False if they are not.|
|acceptConnect()          |Promise (String)            |Accepts connection for given endpoint id and sets Data Events.|
|disconnect()             |Promise (String)            |Disconnects from given endpoint id.|
|rejectConnect()          |Promise (String)            |Rejects connection for given endpoint id.|
|requestConnect()         |Promise (String)            |Request connection for given endpoint id using given name and sets Connect Events.|
|startBroadcasting()      |Promise (String)            |Starts broadcasting on given service id and sets Connect Events.|
|startScan()              |Promise (String)            |Starts scan on given service id and sets Scan Events.|
|stopBroadcasting()       |Promise (String)            |Stops broadcasting.|
|disconnectAll()          |Promise (String)            |Disconnects all connections.|
|stopScan()               |Promise (String)            |Stops scanning.|

#### Public Methods

##### HmsDiscoveryModule.createBroadcastOption(Number policy)

Sets broadcast option object, using the given policy number. This object is used when startBroadcasting method call.  Returns success or failure message.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| policy     | This number represents policy type to create broadcast option. HmsDiscoveryModule.MESH,  HmsDiscoveryModule.P2P, HmsDiscoveryModule.STAR constants represents policy types and their values are 1, 2, 3 in order. Both of them are allowed.|

##### Call Example

```javascript
  async createBroadcastOption() {
    try {
      var result = await HmsDiscoveryModule.createBroadcastOption(HmsDiscoveryModule.STAR);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.getPolicyBroadcast()

Returns broadcast option policy number that set before using createBroadcastOption method.

##### Parameters

None.

##### Call Example

```javascript
  async getPolicyBroadcast() {
    try {
      var result = await HmsDiscoveryModule.getPolicyBroadcast();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.hashCodeBroadcast()

Returns the hash code of broadcast option object that set before using createBroadcastOption method.

##### Parameters

None.

##### Call Example

```javascript
  async hashCodeBroadcast() {
    try {
      var result = await HmsDiscoveryModule.hashCodeBroadcast();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.equalsBroadcast(Number policy)

Checks if broadcast option object that set before using createBroadcastOption, is equal to a new one that is created by given policy number. Returns True if they are equal or False if they are not.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| policy     | This number represents policy type to create broadcast option. HmsDiscoveryModule.MESH,  HmsDiscoveryModule.P2P, HmsDiscoveryModule.STAR constants represents policy types and their values are 1, 2, 3 in order. Both of them are allowed.|

##### Call Example

```javascript
  async equalsBroadcast() {
    try {
      var result = await HmsDiscoveryModule.equalsBroadcast(HmsDiscoveryModule.STAR);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.createScanOption(Number policy)

Sets the scan option object by using the given policy number. This object is used when startScan method call.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| policy     | This number represents policy type to create broadcast option. HmsDiscoveryModule.MESH,  HmsDiscoveryModule.P2P, HmsDiscoveryModule.STAR constants represents policy types and their values are 1, 2, 3 in order. Both of them are allowed.|

##### Call Example

```javascript
  async createScanOption() {
    try {
      var result = await HmsDiscoveryModule.createScanOption(HmsDiscoveryModule.STAR);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.getPolicyScan()

Returns scan option policy number that set before using createScanOption method.

##### Parameters

None.

##### Call Example

```javascript
  async getPolicyScan() {
    try {
      var result = await HmsDiscoveryModule.getPolicyScan();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.hashCodeScan()

Returns the hash code of scan option that set before using createScanOption method.

##### Parameters

None.

##### Call Example

```javascript
  async hashCodeScan() {
    try {
      var result = await HmsDiscoveryModule.hashCodeScan();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.equalsScan(Number policy)

Checks if scan option object that set before using createScanOption, is equal to a new one that is created by given policy number. Returns True if they are equal or False if they are not.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| policy     | This number represents policy type to create broadcast option. HmsDiscoveryModule.MESH,  HmsDiscoveryModule.P2P, HmsDiscoveryModule.STAR constants represents policy types and their values are 1, 2, 3 in order. Both of them are allowed.|

##### Call Example

```javascript
  async equalsScan() {
    try {
      var result = await HmsDiscoveryModule.equalsScan(HmsDiscoveryModule.STAR);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.acceptConnect(String endpointId)

Accepts connection for given endpoint id and sets Data Event.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| endpointId     | ID of the remote endpoint. |

##### Call Example

```javascript
  async acceptConnect() {
    try {
      var result = await HmsDiscoveryModule.acceptConnect("endpointId");
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.disconnect(String endpointId)

Disconnects from given endpoint id.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| endpointId     | ID of the remote endpoint. |

##### Call Example

```javascript
  async disconnect() {
    try {
      var result = await HmsDiscoveryModule.disconnect("endpointId");
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.rejectConnect(String endpointId)

Rejects connection for given endpoint id.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| endpointId     | ID of the remote endpoint. |

##### Call Example

```javascript
  async rejectConnect() {
    try {
      var result = await HmsDiscoveryModule.rejectConnect("endpointId");
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.requestConnect(String name, String endpointId)

Request connection for given endpoint id using given name and sets Connect Events.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| name       | 	Local endpoint name.                 | 
| endpointId     | ID of the remote endpoint. |

##### Call Example

```javascript
  async requestConnect() {
    try {
      var result = await HmsDiscoveryModule.requestConnect("name","endpointId");
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.startBroadcasting(String name, String serviceId)

Starts broadcasting on given service id and sets Connect Events.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| name       | 	Local endpoint name.                 | 
| serviceId     |Service ID.|

##### Call Example

```javascript
  async startBroadcasting() {
    try {
      var result = await HmsDiscoveryModule.startBroadcasting("name","serviceId");
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.startScan(String serviceId)

Starts scan on given service id and sets Scan Events.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| serviceId     |Service ID.|

##### Call Example

```javascript
  async startScan() {
    try {
      var result = await HmsDiscoveryModule.startScan("serviceId");
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.stopBroadcasting()

Stops broadcasting.

##### Parameters

None.

##### Call Example

```javascript
  async stopBroadcasting() {
    try {
      var result = await HmsDiscoveryModule.stopBroadcasting();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.disconnectAll()

Disconnects from all connections.

##### Parameters

None.

##### Call Example

```javascript
  async disconnectAll() {
    try {
      var result = await HmsDiscoveryModule.disconnectAll();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsDiscoveryModule.stopScan()

Stops scanning.

##### Parameters

None.

##### Call Example

```javascript
  async stopScan() {
    try {
      var result = await HmsDiscoveryModule.stopScan();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

#### Constants

| Key                     | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|MESH                     |1                           |Point-to-point connection **policy**, which supports an **M-to-N** connection topology.|
|P2P                      |2                           |Point-to-point connection **policy**, which supports a **1-to-1** connection topology.|
|STAR                     |3                           |Point-to-point connection **policy**, which supports a **1-to-N** connection topology.|
|BLE_UNKNOWN_TX_POWER     |0x80000000                  |Unknown transmit power level.|
|PRECISION_LOW            |1                           |Precision of the distance estimated based on the BLE signal strength.|

##### Call Example

```javascript
    HmsDiscoveryModule.STAR
```

#### Data Events

Data events are dataCallbackOnReceived, dataCallbackOnTransferUpdate. Tables represent returning objects for events. Key, type and description is included as columns in tables.

| dataCallbackOnReceived  | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|endpointId               |String                      |This key gives the enpoint id information.|
|type                     |Number                      |This key gives the data type information. File is 1, Byte is 2 and Stream is 3.|
|id                       |String                      |This key gives unique ID of the payload.|
|data                     |String / Array              |This key gives the data according the type information.|
|size                     |String                      |This key gives size of the file. Only exists when the type information is file.|
|fileUri                  |String                      |This key gives uri of the file. Only exists when the type information is file.|

| dataCallbackOnUpdate    | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|endpointId               |String                      |This key gives the enpoint id information.|
|transferredBytes         |String                      |This key gives the number of transferred bytes.|
|dataId                   |String                      |This key gives the data id.|
|hashCode                 |Number                      |This key gives the hash value.|
|status                   |Number                      |This key gives the transmission status.|
|totalBytes               |String                      |This key gives the total number of bytes to transfer.|

##### Call Example

```javascript
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HmsDiscoveryModule);
    
    eventEmitter.addListener('dataCallbackOnReceived', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('dataCallbackOnUpdate', (event) => {
      console.log(event);
    });
  }
```

#### Scan Events

Scan events are scanCallbackOnFound, scanCallbackOnLost. Tables represent returning objects for events. Key, type and description is included as columns in tables.


| scanCallbackOnFound     | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|endpointId               |String                      |This key gives the enpoint id information.|
|name                     |String                      |This key gives the name information of the discovered endpoint.|
|serviceId                |String                      |This key gives the service id information of the discovered endpoint.|


| scanCallbackOnLost      | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|endpointId               |String                      |This key gives the enpoint id information.|

##### Call Example

```javascript
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HmsDiscoveryModule);
    
    eventEmitter.addListener('scanCallbackOnFound', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('scanCallbackOnLost', (event) => {
      console.log(event);
    });
  }
```

#### Connect Events

Connect events are connectOnEstablish, connectOnResult and connectOnDisconnected. Tables represent returning objects for events. Key, type and description is included as columns in tables.

| connectOnEstablish      | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|endpointId               |String                      |This key gives the enpoint id information.|
|authCode                 |String                      |This key gives the information of symmetric authentication codes from both ends|
|endpointName             |String                      |This key gives the name of the remote endpoint.|
|isRemoteConnect          |Boolean                     |This key gives the information of whether the connection request is initiated by the remote endpoint. If the connection request is initiated by the remote endpoint, true is returned. Otherwise, false is returned.|

| connectOnResult         | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|endpointId               |String                      |This key gives the enpoint id information.|
|statusCode               |Number                      |This key gives the connection status code information. Success is 0, failure is -1, in progress is 3, cancelled is 4.|

| connectOnDisconnected   | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|endpointId               |String                      |This key gives the enpoint id information.|

##### Call Example

```javascript
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HmsDiscoveryModule);
    
    eventEmitter.addListener('connectOnEstablish', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('connectOnResult', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('connectOnDisconnected', (event) => {
      console.log(event);
    });
  }
```

### **HmsTransferModule**

Represents a module that provides methods for transferring data.

#### Public Method Summary 

| Method Name             | Return Type                  | Description                          |
|-------------------------|------------------------------|--------------------------------------|
|transferBytes            |Promise (String)              |Transfers given bytes to given endpoint ids. Obtain the result using Data Events|
|transferFile             |Promise (String)              |Transfers file from given URI to given endpoint ids. Obtain the result using Data Events. Transferred file is saved in subscriber's device under downloads/nearby/ directory with name data id.|
|transferStream           |Promise (String)              |Transfers stream for given endpoint to given endpoint ids. Obtain the result using Data Events.|
|cancelDataTransfer       |Promise (String)              |Cancels data transmission when sending or receiving for given data id parameter.|

#### Public Methods

##### HmsTransferModule.transferBytes(Array bytes, Array endpointIds)

Transfers given bytes to given endpoint ids. Sets Data Events.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| bytes      |Byte array that contains your data|       | 
| endpointIds  |String array that contains your endpointIds. |

##### Call Example

```javascript
  async transferBytes() {
    try {
      var result = await HmsTransferModule.transferBytes([1,1,1,1],["endpointId"]);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsTransferModule.transferFile(String URI, Array endpointIds)

Transfers file from given URI to given endpoint ids.Sets Data Events. Transferred file is saved in subscriber's device under downloads/nearby/ directory with name data id.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| URI      |File URI.|       | 
| endpointIds  |String array that contains your endpointIds. |

##### Call Example

```javascript
  async transferFile() {
    try {
      var result = await HmsTransferModule.transferFile("fileUri",["endpointId"]);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsTransferModule.transferStream(String endpoint, Array endpointIds)

Transfers stream for given endpoint to given endpoint ids. Sets Data Events.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| endpoint      |Stream endpoint url.|       | 
| endpointIds  |String array that contains your endpointIds. |

##### Call Example

```javascript
  async transferStream() {
    try {
      var result = await HmsTransferModule.transferStream("https://developer.huawei.com/",["endpointId"]);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsTransferModule.cancelDataTransfer(String id)

Cancels data transmission when sending or receiving for given data id parameter.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| id      |Data id parameter obtained from Data Events.|       | 


##### Call Example

```javascript
  async cancelDataTransfer() {
    try {
      var result = await HmsTransferModule.cancelDataTransfer("dataId");
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

#### Constants

| Key                     | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|FILE                     |1                           |Type data in dataCallbackOnReceived event in Data Events. This represents bytes data type.|
|BYTES                    |2                           |Type data in dataCallbackOnReceived event in Data Events. This represents bytes data type for received content.|
|STREAM                   |3                           |Type data in dataCallbackOnReceived event in Data Events. This represents stream data type for received content.|
|MAX_SIZE_DATA            |32768                       |Maximum data size that transfer functions able to send.|
|TRANSFER_STATE_SUCCESS   |1                           |Success value in dataCallbackOnTransferUpdate event in Data Events.|
|TRANSFER_STATE_FAILURE   |2                           |Success value in dataCallbackOnTransferUpdate event in Data Events.|
|TRANSFER_STATE_IN_PROGRESS|3                          |Success value in dataCallbackOnTransferUpdate event in Data Events.|
|TRANSFER_STATE_CANCELED  |4                           |Success value in dataCallbackOnTransferUpdate event in Data Events.|

##### Call Example

```javascript
HmsTransferModule.FILE
```

### **HmsMessageModule**

Represents a module that provides methods for app based message publishing and beacon based message publishing.

#### Methods

| Method Name             | Return Type                      | Description                          |
|-------------------------|------------------------------|--------------------------------------|
|setApiKey                |Promise (String)              |Sets the API credential for your app.|
|getApiKey                |Promise (String)              |Returns the api key credential.|
|enableLogger             |Promise (String)              |This method enables HmsLogger capability which is used for sending usage analytics of Nearby SDK's methods to improve service quality.|
|disableLogger            |Promise (String)              |This method disables HmsLogger capability which is used for sending usage analytics of Nearby SDK's methods to improve service quality.|
|createBeaconId           |Promise (String)              |Creates the beacon ID object, which can either be an iBeacon ID object or an Eddystone UID object. This function uses Beacon object to create beacon ID.|
|beaconLength             |Promise (Number)              |Returns the length of a beacon ID object that set before using createBeaconId. The value 20 is returned for an iBeacon ID and the value 16 is returned for an Eddystone UID.|
|beaconType               |Promise (Number)              |Obtains the beacon ID type. For an iBeacon ID, 1 is returned.For an Eddystone UID, 2 is returned.|
|beaconEquals             |Promise (Boolean)             |Checks if beacon ID object that set before using createBeaconId, is equal to a new one that is created by given Beacon object. Returns True if they are equal or False if they are not.|
|beaconHashCode           |Promise (Number)              |Obtains the hash value of beacon ID object.|
|beaconToString           |Promise (String)              |Converts beacon ID object into a readable character string.|
|beaconHex                |Promise (Number)              |Returns a 16-byte beacon ID.|
|beaconInstance           |Promise (String)              |Returns the instance (last 6 bytes) of an Eddystone UID.|
|beaconNamespace          |Promise (String)              |Returns the namespace (first 6 bytes) of an Eddystone UID.|
|beaconMajor              |Promise (String)              |Returns the major value of an iBeacon ID.|
|beaconMinor              |Promise (String)              |Returns the minor value of an iBeacon ID.|
|beaconUuid               |Promise (String)              |Returns the UUID of an iBeacon ID.|
|createPutOption          |Promise (String)              |Creates options from given object for using to call put.|
|createGetOption          |Promise (String)              |Creates options from given object for using to call getMessage.|
|createPolicy             |Promise (String)              |Creates a policy for publishing or subscribing to messages.|
|policyEquals             |Promise (Boolean)             |Checks if created policy object is the with given object.|
|policyHashCode           |Promise (Number)              |Obtains the hash value.|
|policyToString           |Promise (String)              |Converts an object into a readable character string.|
|getPolicyPutOption       |Promise (PolicyPutOption)     |Returns policy informations of putOption as an object.|
|createMessagePicker      |Promise (String)              |Specifies the set of messages to be received. When the sharing code is discovered, the MessagePicker must match the message. A MessagePicker is the logical OR of multiple pickers.|
|messagePickerEquals      |Promise (Boolean)             |Returns true if given object is the same as this object. Otherwise false.|
|messagePickerHashCode    |Promise (Number)              |Obtains the hash value.|
|messagePickerToString    |Promise (String)              |Converts an object into a readable character string.|
|put                      |Promise (String)              |This function publishes a message and broadcasts a token for nearby devices to scan. This message is published only to apps that use the same project ID and have registered the message type with the cloud for subscription. If the isOption parameter is true, putOption object must be created by using createPutOption method before.|
|registerStatusCallback   |Promise (String)              |Registers to Status Events event, which will notify your app of key events. When your app calls one of the APIs for the first time, the function will return the status.|
|unRegisterStatusCallback |Promise (String)              |Cancels the Status Events registered before.|
|getMessage               |Promise (String)               |If isOption is true this function registers the messages to be obtained with the cloud. Only messages with the same project ID can be obtained, getOption object must be created by using createGetOption method before.If isOption is false this function registers the messages to be obtained with the cloud using the default option (DEFAULT).This function sets Message Handler Events by default.|
|getPending               |Promise (String)              |If isOption true this function identifies only BLE beacon messages, getOption object must be created by using createGetOption method before. Scanning is going on no matter whether your app runs in the background or foreground. The scanning stops when the app process is killed.If isOption false this function identifies only BLE beacon messages. It subscribes to messages published by nearby devices in a persistent and low-power manner and uses the default configuration (DEFAULT). Scanning is going on no matter whether your app runs in the background or foreground. The scanning stops when the app process is killed.This function sets Background Events by default.|
|unput                    |Promise (String)              |Cancels message publishing for given message.|
|unget                    |Promise (String)              |If isCurrent is true this function cancels the message subscription created with getPending.If isCurrent is false cancels message subscription created with getMessage.|

#### Public Methods

##### HmsMessageModule.setApiKey(String apiKey)

Sets the API credential for your app.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| apiKey     |Api key is parameter that exists in your **agconnect-services.json** | 


##### Call Example

```javascript
  async setApiKey() {
    try {
      var result = await HmsMessageModule.setApiKey("apiKey");
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.getApiKey()

Returns the api key credential.

##### Parameters

None.

##### Call Example

```javascript
  async getApiKey() {
    try {
      var result = await HmsMessageModule.getApiKey();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.enableLogger()

This method enables HmsLogger capability which is used for sending usage analytics of Nearby SDK's methods to improve service quality.

##### Parameters

None.

##### Call Example

```javascript
  async enableLogger() {
    try {
      var result = await HmsMessageModule.enableLogger();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.disabLogger()

This method disables HmsLogger capability which is used for sending usage analytics of Nearby SDK's methods to improve service quality.

##### Parameters

None.

##### Call Example

```javascript
  async disableLogger() {
    try {
      var result = await HmsMessageModule.disableLogger();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.createBeaconId(Object Beacon)

Creates the beacon ID object, which can either be an iBeacon ID object or an Eddystone UID object. This function uses Beacon object to create beacon ID.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| Beacon     |Object that contains configurations for beacon. For details please explore Data Types.|

##### Call Example

```javascript
  async createBeaconId() {
    try {
      var result = await HmsMessageModule.createBeaconId({uuid:"",major:"",minor:"",hexId:"",namespace:"",hexInstance:""});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.beaconEquals(Object Beacon)

Checks if beacon ID object that set before using createBeaconId, is equal to a new one that is created by given Beacon object. Returns True if they are equal or False if they are not.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| Beacon     |Object that contains configurations for beacon. For details please explore Data Types.|

##### Call Example

```javascript
  async beaconEquals() {
    try {
      var result = await HmsMessageModule.beaconEquals({uuid:"",major:"",minor:"",hexId:"",namespace:"",hexInstance:""});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.beaconLength()

Returns the length of a beacon ID object that set before using createBeaconId. The value 20 is returned for an iBeacon ID and the value 16 is returned for an Eddystone UID.

##### Parameters

None.

##### Call Example

```javascript
  async beaconLength() {
    try {
      var result = await HmsMessageModule.beaconLength();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.beaconType()

Obtains the beacon ID type. For an iBeacon ID, 1 is returned.For an Eddystone UID, 2 is returned.

##### Parameters

None.

##### Call Example

```javascript
  async beaconType() {
    try {
      var result = await HmsMessageModule.beaconType();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.beaconHashCode()

Obtains the hash value of beacon ID object.

##### Parameters

None.

##### Call Example

```javascript
  async beaconHashCode() {
    try {
      var result = await HmsMessageModule.beaconHashCode();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.beaconToString()

Converts beacon ID object into a readable character string.

##### Parameters

None.

##### Call Example

```javascript
  async beaconToString() {
    try {
      var result = await HmsMessageModule.beaconToString();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.beaconHex()

Returns a 16-byte beacon ID.

##### Parameters

None.

##### Call Example

```javascript
  async beaconHex() {
    try {
      var result = await HmsMessageModule.beaconHex();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.beaconInstance()

Returns the instance (last 6 bytes) of an Eddystone UID.

##### Parameters

None.

##### Call Example

```javascript
  async beaconInstance() {
    try {
      var result = await HmsMessageModule.beaconInstance();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.beaconNamespace()

Returns the namespace (first 6 bytes) of an Eddystone UID.

##### Parameters

None.

##### Call Example

```javascript
  async beaconNamespace() {
    try {
      var result = await HmsMessageModule.beaconNamespace();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.beaconMajor()

Returns the major value of an iBeacon ID.

##### Parameters

None.

##### Call Example

```javascript
  async beaconMajor() {
    try {
      var result = await HmsMessageModule.beaconMajor();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.beaconMinor()

Returns the minor value of an iBeacon ID.

##### Parameters

None.

##### Call Example

```javascript
  async beaconMinor() {
    try {
      var result = await HmsMessageModule.beaconMinor();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.beaconUuid()

Returns the UUID of an iBeacon ID.

##### Parameters

None.

##### Call Example

```javascript
  async beaconUuid() {
    try {
      var result = await HmsMessageModule.beaconUuid();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.createPutOption(Object PutOption)

Creates options from given object for using to call put() method.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| PutOption     |Object that contains configurations for put() method. For details please explore Data Types.|

##### Call Example

```javascript
  async createPutOption() {
    try {
      var result = await HmsMessageModule.createPutOption({setPolicy : true, setCallback: true});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.createGetOption(Object GetOption)

Creates options from given object for using to call getMessage() method.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| GetOption     |Object that contains configurations for getMessage() method. For details please explore Data Types.|

##### Call Example

```javascript
  async createGetOption() {
    try {
      var result = await HmsMessageModule.createGetOption({setPolicy : true, setCallback: true, setPicker: true});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.createPolicy(Object Policy)

Creates a policy for publishing or subscribing to messages.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| Policy     |Object that contains configurations. For details please explore Data Types.|

##### Call Example

```javascript
  async createPolicy() {
    try {
      var result = await HmsMessageModule.createPolicy({findingMode :HmsMessageModule.POLICY_FINDING_MODE_DEFAULT, 
                                                        distanceType: HmsMessageModule.POLICY_DISTANCE_TYPE_DEFAULT, 
                                                        ttlSeconds: HmsMessageModule.POLICY_TTL_SECONDS_DEFAULT});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.policyEquals(Object Policy)

Checks if created policy object is the with given object.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| Policy     |Object that contains configurations. For details please explore Data Types.|

##### Call Example

```javascript
  async policyEquals() {
    try {
      var result = await HmsMessageModule.policyEquals({findingMode :HmsMessageModule.POLICY_FINDING_MODE_DEFAULT, 
                                                        distanceType: HmsMessageModule.POLICY_DISTANCE_TYPE_DEFAULT, 
                                                        ttlSeconds: HmsMessageModule.POLICY_TTL_SECONDS_DEFAULT});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.policyHashCode()

Obtains hash value.

##### Parameters

None.

##### Call Example

```javascript
  async policyHashCode() {
    try {
      var result = await HmsMessageModule.policyHashCode();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.policyToString()

Converts an object into a readable character string.

##### Parameters

None.

##### Call Example

```javascript
  async policyToString() {
    try {
      var result = await HmsMessageModule.policyToString();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.getPolicyPutOption()

Returns policy informations as PolicyPutOption object. Please refer to Data Types for details.

##### Parameters

None.

##### Call Example

```javascript
  async getPolicyPutOption() {
    try {
      var result = await HmsMessageModule.getPolicyPutOption();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.createMessagePicker(Object MessagePicker)

Specifies the set of messages to be received. When the sharing code is discovered, the MessagePicker must match the message. A MessagePicker is the logical OR of multiple pickers.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| MessagePicker  |Object that contains configurations. For details please explore Data Types.|

##### Call Example

```javascript
  async createMessagePicker() {
    try {
      var result = await HmsMessageModule.createMessagePicker({includeAllTypes:false, includeEddyStoneUids:true, hexNamespace:"", hexInstance:""});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.messagePickerEquals(Object MessagePicker)

Returns policy informations as PolicyPutOption object. Please refer to Data Types for details.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| MessagePicker  |Object that contains configurations. For details please explore Data Types.|

##### Call Example

```javascript
  async messagePickerEquals() {
    try {
      var result = await HmsMessageModule.messagePickerEquals({includeAllTypes:false, includeEddyStoneUids:true, hexNamespace:"", hexInstance:""});
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.messagePickerHashCode()

Returns hash value.

##### Parameters

None.

##### Call Example

```javascript
  async messagePickerHashCode() {
    try {
      var result = await HmsMessageModule.messagePickerHashCode();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.messagePickerToString()

Converts an object into a readable character string.

##### Parameters

None.

##### Call Example

```javascript
  async messagePickerToString() {
    try {
      var result = await HmsMessageModule.messagePickerToString();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.put(String message, Boolean isOption)

This function publishes a message and broadcasts a token for nearby devices to scan. This message is published only to apps that use the same project ID and have registered the message type with the cloud for subscription. If the isOption parameter is true, putOption object must be created by using createPutOption method before.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| message    |Message to be published|
| isOption   |If isOption is true, this function uses putOption object that you created before.|

##### Call Example

```javascript
  async put() {
    try {
      var result = await HmsMessageModule.put("Huawei", true);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.getMessage(Boolean isOption)

If isOption is true this function registers the messages to be obtained with the cloud. Only messages with the same project ID can be obtained, getOption object must be created by using createGetOption method before.If isOption is false this function registers the messages to be obtained with the cloud using the default option (DEFAULT).This function sets Message Handler Events by default.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| isOption   |If isOption is true, this function uses getOption object that you created before.|

##### Call Example

```javascript
  async getMessage() {
    try {
      var result = await HmsMessageModule.getMessage(true);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.getPending(Boolean isOption)

If isOption true this function identifies only BLE beacon messages, getOption object must be created by using createGetOption method before. Scanning is going on no matter whether your app runs in the background or foreground. The scanning stops when the app process is killed.If isOption false this function identifies only BLE beacon messages. It subscribes to messages published by nearby devices in a persistent and low-power manner and uses the default configuration (DEFAULT). Scanning is going on no matter whether your app runs in the background or foreground. The scanning stops when the app process is killed.This function sets Background Events by default.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| isOption   |If isOption is true, this function uses getOption object that you created before.|

##### Call Example

```javascript
  async getPending() {
    try {
      var result = await HmsMessageModule.getPending(true);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.unput(String message)

Cancels message publishing for given message.

##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| message    | Given message to be cancelled|

##### Call Example

```javascript
  async unput() {
    try {
      var result = await HmsMessageModule.unput("Huawei");
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.unget(Boolean isCurrent)

If isCurrent is true this function cancels the message subscription created with getPending.If isCurrent is false cancels message subscription created with getMessage.


##### Parameters

| Name       |  Description                          |
|------------|---------------------------------------|
| isCurrent  | If true cancels the message subscription created with getPending. If false cancels the message subscription created with getMessage|

##### Call Example

```javascript
  async unget() {
    try {
      var result = await HmsMessageModule.unget(false);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.registerStatusCallback()

Registers to Status Events, which will notify your app of key events. When your app calls one of the APIs for the first time, the function will return the status.

##### Parameters

None.

##### Call Example

```javascript
  async registerStatusCallback() {
    try {
      var result = await HmsMessageModule.registerStatusCallback();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

##### HmsMessageModule.unRegisterStatusCallback()

Cancels the Status Events registered before.

##### Parameters

None.

##### Call Example

```javascript
  async unRegisterStatusCallback() {
    try {
      var result = await HmsMessageModule.unRegisterStatusCallback();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }
```

#### Constants

| Key                     | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|BEACON_TYPE_IBEACON                     |1                           |iBeacon ID type.|
|BEACON_TYPE_EDDYSTONE_UID                    |2                           |Eddystone UID type.|
|IBEACON_ID_LENGTH                   |20                           |Length of an iBeacon ID, in bytes. An iBeacon ID contains a 16-byte UUID, followed by a 2-byte major value and then a 2-byte minor value.|
|EDDYSTONE_UID_LENGTH            |16                       |Length of an Eddystone UID, in bytes. An Eddystone UID contains a 10-byte namespace, followed by a 6-byte instance.|
|EDDYSTONE_NAMESPACE_LENGTH   |10                           |Length of an Eddystone UID namespace, in bytes.|
|EDDYSTONE_INSTANCE_LENGTH   |6                           |Length of an Eddystone UID instance, in bytes.|
|MAX_CONTENT_SIZE|64KB                          |Maximum size of the message content, in bytes.|
|MAX_TYPE_LENGTH  |16                           |Maximum size of a message type, in bytes.|
|MESSAGE_NAMESPACE_RESERVED                     |"_reserved_namespace"                           |Namespace reserved for special messages.|
|MESSAGE_TYPE_EDDYSTONE_UID                    |"_eddystone_uid"                           |Message type for Eddystone UID.|
|MESSAGE_TYPE_IBEACON_ID                   |"_ibeacon_id"                           |Message type for Ibeacon ID.|
|PERMISSION_NONE            |-1                       |No permission.|
|PERMISSION_DEFAULT   |0                           |Default permission.|
|PERMISSION_BLE   |1                           |BLE permission.|
|PERMISSION_BLUETOOTH|2                          |Bluetooth permission.|
|PERMISSION_MICROPHONE  |3                           |Microphone permission.|
|POLICY_FINDING_MODE_DEFAULT  |1                           |To discover nearby devices, broadcasts a sharing code and scans for other devices' sharing codes.|
|POLICY_FINDING_MODE_BROADCAST                    |2                           |To discover nearby devices, broadcasts a sharing code for other devices to scan.|
|POLICY_FINDING_MODE_SCAN                   |0                           |To discover nearby devices, broadcasts a sharing code and scans for other devices' sharing codes.|
|POLICY_DISTANCE_TYPE_DEFAULT            |0                       |Allows messages to be transmitted over any distance.|
|POLICY_DISTANCE_TYPE_EARSHOT   |1                           |Allows messages to be transmitted only within the earshot. It is recommended that this policy be used together with POLICY_FINDING_MODE_BROADCAST to reduce the device discovery delay.|
|POLICY_TTL_SECONDS_DEFAULT   |240                           |Default TTL, in seconds.|
|POLICY_TTL_SECONDS_INFINITE|0x7FFFFFFF                          |Indefinite TTL, in seconds.|
|POLICY_TTL_SECONDS_MAX  |86400                           |Maximum TTL, in seconds.|

##### Call Example

```javascript
HmsMessageModule.BEACON_TYPE_IBEACON
```

#### Status Events

Status events are statusCallback, getCallback and putCallback. These events inform developer using status messages. Tables represent returning objects for events. Key, type and description is included as columns in tables. 

| statusCallback          | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|onPermissionChanged      |String                      |Called when a Nearby permission is assigned or revoked.|

| getCallback             | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|onTimeout                |String                      |Called when message subscription expires.|

| putCallback             | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|onTimeout                |String                      |This key gives the enpoint id information.|

##### Call Example

```javascript
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HmsMessageModule);
    
    eventEmitter.addListener('statusCallback', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('getCallback', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('putCallback', (event) => {
      console.log(event);
    });
  }
```

#### Message Handler Events

| onBleSignalChanged      | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|namespace                |String                      |Non-empty string for a public namespace or an empty string for the private namespace. For example, a beacon attachment is a public namespace.|
|type                     |String                      |Message type, an empty string is returned if no type is specified when the message is created.|
|content                  |Array                       |Message content as bytes.|
|rSSI                     |Number                      |Received signal strength in dBm. The value range is [–127,127]. This value is a weighted value. The shorter the distance is, the higher the weight is.|
|txPower                  |Number                      |Returns the transmit power from 1 m away, in dBm.|

| onDistanceChanged       | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|namespace                |String                      |Non-empty string for a public namespace or an empty string for the private namespace. For example, a beacon attachment is a public namespace.|
|type                     |String                      |Message type, an empty string is returned if no type is specified when the message is created.|
|content                  |Array                       |Message content as bytes.|
|meters                     |Number                    |Estimated distance, in m.|
|precision                  |Number                    |Precision of the estimated instance.|

| onFound                 | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|namespace                |String                      |Non-empty string for a public namespace or an empty string for the private namespace. For example, a beacon attachment is a public namespace.|
|type                     |String                      |Message type, an empty string is returned if no type is specified when the message is created.|
|content                  |Array                       |Message content as bytes.|

| onLost                  | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|namespace                |String                      |Non-empty string for a public namespace or an empty string for the private namespace. For example, a beacon attachment is a public namespace.|
|type                     |String                      |Message type, an empty string is returned if no type is specified when the message is created.|
|content                  |Array                       |Message content as bytes.|


##### Call Example

```javascript
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HmsMessageModule);
    
    eventEmitter.addListener('onBleSignalChanged', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('onDistanceChanged', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('onFound', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('onLost', (event) => {
      console.log(event);
    });
  }
```

#### Background Events

| backgroundOnFound                 | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|namespace                |String                      |Non-empty string for a public namespace or an empty string for the private namespace. For example, a beacon attachment is a public namespace.|
|type                     |String                      |Message type, an empty string is returned if no type is specified when the message is created.|
|content                  |Array                       |Message content as bytes.|

| backgroundOnLost                  | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|namespace                |String                      |Non-empty string for a public namespace or an empty string for the private namespace. For example, a beacon attachment is a public namespace.|
|type                     |String                      |Message type, an empty string is returned if no type is specified when the message is created.|
|content                  |Array                       |Message content as bytes.|

##### Call Example

```javascript
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HmsMessageModule);

    eventEmitter.addListener('backgroundOnFound', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('backgroundOnLost', (event) => {
      console.log(event);
    });
  }
```

#### Data Types

| Beacon                  | Type                       | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|uuid                     |String                      |Sets a 16-byte UUID.|
|major                    |String                      |Sets the 2-byte major value.|
|minor                    |String                      |Sets the 2-byte minor value.|
|hexId                    |String                      |Sets hexId.|
|namespace                |String                      |Sets hexNamespace.|
|hexInstance              |String                      |Sets hexInstance.|

| PutOption                  | Type                       | Description                          |
|----------------------------|----------------------------|--------------------------------------|
|setPolicy                   |Boolean                     |Sets the policy for publishing messages. The DEFAULT policy is used by default.|
|setCallback                 |Boolean                     |Sets putCallback executed when key publishing events occur.|

| GetOption                  | Type                       | Description                          |
|----------------------------|----------------------------|--------------------------------------|
|setPolicy                   |Boolean                     |Sets the policy for publishing messages. The DEFAULT policy is used by default.|
|setCallback                 |Boolean                     |Sets putCallback executed when key publishing events occur.|
|setPicker                   |Boolean                     |Sets the rule for filtering messages to be received.|

| MessagePicker                  | Type                       | Description                          |
|----------------------------|----------------------------|--------------------------------------|
|includeAllTypes                   |Boolean                     |Picks among all messages published by the app.|
|includeEddyStoneUids, hexNamespace, hexInstance                 |Boolean, String, String                     |If includeEddyStoneUids is true, hexNamespace and hexInstance need to be set. If false, they are ignored.|
|includePicker                   |Boolean                     |Includes the previously constructed picker.|
|includeBeaconIds, proximityUuid, major, minor                   |Boolean, String, String, String                     |If includeBeaconIdsis true, proximityUuid, major and minor need to be set. If false, they are ignored.|
|includeNameSpaceType, namespace, type                   |Boolean, String, String                     |If includeNameSpaceType is true, namespace and type need to be set. If false, they are ignored.|

| Policy                     | Type                       | Description                          |
|----------------------------|----------------------------|--------------------------------------|
|distanceType                |Number                     |Distance type.|
|ttlSeconds                  |Number                     |Ttl seconds.|
|findingMode                 |Number                     |Finding mode.|


| PolicyPutOption                  | Type                       | Description                          |
|----------------------------|----------------------------|--------------------------------------|
|str                         |String                     |String version of policy object|
|hashCode                    |Number                     |Hash value of policy object|
|backgroundScanMode          |Number                     |Backgorund scan mode.|
|changeFindingMode           |Number                     |Change Finding mode.|
|distanceType                |Number                     |Distance type.|
|findingMedium               |Number                     |Finding medium|
|ttlSeconds                  |Number                     |Ttl seconds.|
|findingMode                 |Number                     |Finding mode.|


## 4. Configuration Description
No.

## 5. Sample Project
This plugin includes a demo project under the **example** folder.

**Note That :**  To use demo application, prepare 2 mobile phones (HmsCore Apk installed) . 
Please don't forget to give permissions in HmsCore App from **Settings > Apps > HmsCore > Permissions**

**Step 1:** Run the following command

```bash
npm i
npm run react-native-hms-nearby
```

**Step 2:** Put your **agconnect-services.json** and **your_keystore.jks** files under **example/android/app** and configure the **build.gradle** file.

```gradle
defaultConfig {
        applicationId "<package_name>"
        minSdkVersion rootProject.ext.minSdkVersion
        targetSdkVersion rootProject.ext.targetSdkVersion
        versionCode 1
        versionName "1.0"
    }
signingConfigs {
        config {
            storeFile file('<keystore_file>.jks')
            storePassword '<keystore_password>'
            keyAlias '<key_alias>'
            keyPassword '<key_password>'
        }
    }
```

**Step 3:** Choose your run command and run the demo application.

```bash
npx react-native run-android
npm run android
react-native run-android
```
---

## 6. Questions or Issues
If you have questions about how to use HMS samples, try the following options:
- [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services) is the best place for any programming questions. Be sure to tag your question with 
**huawei-mobile-services**.
- [Github](https://github.com/HMS-Core/hms-react-native-plugin/) is the official repository for these plugins, You can open an issue or submit your ideas.
- [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001) HMS Core Module is great for general questions, or seeking recommendations and opinions.
- [Huawei Developer Docs](https://developer.huawei.com/consumer/en/doc/overview/HMS-Core-Plugin) is place to official documentation for all HMS Core Kits, you can find detailed documentations in there.

If you run into a bug in our samples, please submit an issue to the Github Repository.

## 7. Licencing and Terms  
Huawei React-Native SDK is licensed under [Apache 2.0 license](LICENCE)