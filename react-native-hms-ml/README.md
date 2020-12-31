# React-Native HMS ML

---

## Contents

  - [1. Introduction](#1-introduction)
  - [2. Installation Guide](#2-installation-guide)
    - [Creating a Project in App Gallery Connect](#creating-a-project-in-appgallery-connect)
    - [Configuring the Signing Certificate Fingerprint](#configuring-the-signing-certificate-fingerprint)
    - [Integrating the React-Native ML Plugin](#integrating-react-native-ml-plugin)
  - [3. API Reference](#3-api-reference)
    - [HMSApplication](#hmsapplication)
      - [Constants](#hmsapplication-constants)
    - [HMSFrame](#hmsframe)
      - [Constants](#hmsframe-constants)
      - [Data Types](#hmsframe-data-types)
    - [HMSTextRecognition](#hmstextrecognition)
      - [Constants](#hmstextrecognition-constants)
      - [Data Types](#hmstextrecognition-data-types)
    - [HMSDocumentRecognition](#hmsdocumentrecognition)
      - [Constants](#hmsdocumentrecognition-constants)
      - [Data Types](#hmsdocumentrecognition-data-types)
    - [HMSBankCardRecognition](#hmsbankcardrecognition)
      - [Constants](#hmsbankcardrecognition-constants)
      - [Data Types](#hmsbankcardrecognition-data-types)
      - [Events](#hmsbankcardrecognition-events)
    - [HMSGeneralCardRecognition](#hmsgeneralcardrecognition)
      - [Constants](#hmsgeneralcardrecognition-constants)
      - [Data Types](#hmsgeneralcardrecognition-data-types)
      - [Events](#hmsgeneralcardrecognition-events)
    - [HMSFormRecognition](#hmsformrecognition)
    - [HMSAft](#hmsaft)
      - [Constants](#hmsaft-constants)
      - [Data Types](#hmsaft-data-types)
      - [Events](#hmsaft-events)
    - [HMSAsr](#hmsasr)
      - [Constants](#hmsasr-constants)
      - [Events](#hmsasr-events)
    - [HMSTranslate](#hmstranslate)
      - [Constants](#hmstranslate-constants)
      - [Data Types](#hmstranslate-data-types)
      - [Events](#hmstranslate-events)
    - [HMSLanguageDetection](#hmslanguagedetection)
      - [Constants](#hmslanguagedetection-constants)
    - [HMSSpeechRtt](#hmsspeechrtt)
      - [Constants](#hmsspeechrtt-constants)
      - [Data Types](#hmsspeechrtt-data-types)
      - [Events](#hmsspeechrtt-events)
    - [HMSSoundDetect](#hmssounddetect)
      - [Constants](#hmssounddetect-constants)
      - [Events](#hmssounddetect-events)
    - [HMSTextToSpeech](#hmstexttospeech)
      - [Constants](#hmstexttospeech-constants)
      - [Data Types](#hmstexttospeech-data-types)
      - [Events](#hmstexttospeech-events)
    - [HMSModelDownload](#hmsmodeldownload)
      - [Constants](#hmsmodeldownload-constants)
      - [Data Types](#hmsmodeldownload-data-types)
      - [Events](#hmsmodeldownload-events)
    - [HMSCustomModel](#hmscustommodel)
      - [Data Types](#hmscustommodel-data-types)
    - [HMSTextEmbedding](#hmstextembedding)
      - [Constants](#hmstextembedding-constants)
    - [HMSFaceRecognition](#hmsfacerecognition)
      - [Constants](#hmsfacerecognition-constants)
      - [Data Types](#hmsfacerecognition-data-types)
    - [HMSHandKeypointDetection](#hmshandkeypointdetection)
      - [Constants](#hmshandkeypointdetection-constants)
      - [Data Types](#hmshandkeypointdetection-data-types)
    - [HMSLivenessDetection](#hmslivenessdetection)
      - [Constants](#hmslivenessdetection-constants)
      - [Data Types](#hmslivenessdetection-data-types)
    - [HMSSkeletonDetection](#hmsskeletondetection)
      - [Constants](#hmsskeletondetection-constants)
      - [Data Types](#hmsskeletondetection-data-types)
    - [HMSLensEngine](#hmslensengine)
      - [Constants](#hmslensengine-constants)
      - [Data Types](#hmslensengine-data-types)
      - [Events](#hmslensengine-events)
    - [HMSTextImageSuperResolution](#hmstextimagesuperresolution)
    - [HMSSceneDetection](#hmsscenedetection)
    - [HMSProductVisionSearch](#hmsproductvisionsearch)
      - [Constants](#hmsproductvisionsearch-constants)
      - [Events](#hmsproductvisionsearch-events)
    - [HMSImageSuperResolution](#hmsimagesuperresolution)
      - [Constants](#hmsimagesuperresolution-constants)
    - [HMSImageClassification](#hmsimageclassification)
      - [Data Types](#hmsimageclassification-data-types)
    - [HMSImageSegmentation](#hmsimagesegmentation)
      - [Constants](#hmsimagesegmentation-constants)
      - [Data Types](#hmsimagesegmentation-data-types)
    - [HMSObjectRecognition](#hmsobjectrecognition)
      - [Constants](#hmsobjectrecognition-constants)
      - [Data Types](#hmsobjectrecognition-data-types)
    - [HMSLandmarkRecognition](#hmslandmarkrecognition)
      - [Constants](#hmslandmarkrecognition-constants)
      - [Data Types](#hmslandmarkrecognition-data-types)
    - [HMSDocumentSkewCorrection](#hmsdocumentskewcorrection)
      - [Constants](#hmsdocumentskewcorrection-constants)
      - [Data Types](#hmsdocumentskewcorrection-data-types)
    - [HMSSurfaceView](#hmssurfaceview)
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

### Integrating React Native ML Plugin

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

- Set your package name in **defaultConfig** > **applicationId** and set **minSdkVersion** to **19** or **higher**.

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
npm i @hmscore/react-native-hms-ml
```

**Step 2:**  Run your project. 

- Run the following command to the project directory.

```bash
react-native run-android  
```

#### Option 2: Download Link

To integrate the plugin, follow the steps below:

**Step 1:** Download the React Native ML Plugin and place **react-native-hms-ml** under **node_modules/@hmscore** of your React Native project, as shown in the directory tree below.

```
demo-app
  |_ node_modules
    |_ @hmscore
      |_ react-native-hms-ml
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
    implementation project(":react-native-hms-ml")    
    ...    
  }
}
```

**Step 3:** Add the following lines to the **android/settings.gradle** file in your project:

```groovy
include ':app'
include ':react-native-hms-ml'
project(':react-native-hms-ml').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-ml/android')
```

**Step 4:**  Import the following classes to the **MainApplication.java** file of your project.

```java
import com.huawei.hms.rn.ml.HMSML
```

Then, add the **HMSML()** to your **getPackages** method. In the end, your file will be similar to the following:

```java 
import com.huawei.hms.rn.ml.HMSML;
...
@Override
protected List<ReactPackage> getPackages() {
    List<ReactPackage> packages = new PackageList(this).getPackages();
    packages.add(new HMSML()); // <-- Add this line 
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
| [setAccessToken(token)](#hmsapplicationsetaccesstokentoken) | Promise\<object> | Sets access token for your app. |

#### Public Methods

##### HMSApplication.enableLogger()

This method enables HMSLogger capability which is used for sending usage analytics of ML SDK's methods to improve service quality.

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

This method disables HMSLogger capability which is used for sending usage analytics of ML SDK's methods to improve service quality. 

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
  async setApiKey() {
    try {
      var result = await HMSApplication.setApiKey("");
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
{ status : 0, message : "Success", result : "" }
```

##### HMSApplication.setAccessToken(token)

Sets the token of an app.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| token      | string | access token                         |

Call Example

```js
  async setAccessToken() {
    try {
      var result = await HMSApplication.setAccessToken("");
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

#### HMSApplication Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|UNKNOWN                  |-1                          |Unknown error.|
|SUCCESS                  |0                           |Service running properly.|
|DISCARDED                |1                           |Operation canceled.|
|INNER                    |2                           |Internal error.|
|INACTIVE                 |3                           |Service unavailable.|
|NOT_SUPPORTED            |4                           |Operation not supported.|
|ILLEGAL_PARAMETER        |5                           |Incorrect parameter. This exception is irrelevant to services.|
|OVERDUE                  |6                           |Operation timed out.|
|NO_FOUND                 |7                           |Requested resource not found or not exist.|
|DUPLICATE_FOUND          |8                           |Resource created repeatedly.|
|NO_PERMISSION            |9                           |No operation permission.|
|INSUFFICIENT_RESOURCE    |10                          |Resources exhausted. The system resources or resources of the current user may be insufficient.|
|ANALYSIS_FAILURE         |11                          |Operation failed because the system was not ready.|
|INTERRUPTED              |12                          |Operation interrupted, possibly due to concurrent operations.|
|EXCEED_RANGE             |13                          |Out-of-bounds access.|
|DATA_MISSING             |14                          |Unrecoverable data damage or loss.|
|AUTHENTICATION_REQUIRED  |15                          |Identity authentication required.|
|TFLITE_NOT_COMPATIBLE    |16                          |Downloaded model incompatible with the running model.|
|INSUFFICIENT_SPACE       |17                          |Insufficient disk space.|
|HASH_MISS                |18                          |Unexpected hash value of the downloaded model.|
|TOKEN_INVALID            |19                          |Invalid token.|
|FRAME_NULL               |20                          |Frame creation failed.|
|ANALYZER_NOT_AVAILABLE   |21                          |Analyzer is not available or created.|
|CURRENT_ACTIVITY_NULL    |22                          |Current activity is null.|
|CANCEL                   |23                          |Recognition cancelled by user.|
|FAILURE                  |24                          |Recognition failed.|
|DENY                     |25                          |Recognition failed.|
|STRING_PARAM_NULL        |26                          |Given string parameter is not valid.|
|REMOTE_MODEL_NULL        |27                          |Remote model object creation failed with given parameters.|
|ASR_RECOGNIZER_NULL      |28                          |Asr recognizer is null or not initialized.|
|TTS_ENGINE_NULL          |29                          |Tts engine is not created.|
|SOUND_DECT_NULL          |31                          |Sound detector is not initialized.|
|CUSTOM_MODEL_SETTING_NULL|32                          |Input output setting creation failure with given parameters.|
|CUSTOM_MODEL_INPUT_NULL  |33                          |Model input creation failure with given parameters.|
|CUSTOM_MODEL_EXECUTOR_SETTING_NULL|34                 |Model executor creation failure with given parameters.|
|DATA_SET_NOT_VALID       |35                          |Data set is not valid.|
|LENS_ENGINE_NULL         |36                          |Lens engine creation failure.|
|LENS_HOLDER_NULL         |37                          |Lens holder creation failure.|

Call Example

```js
  async setApiKey() {
    try {
      var result = await HMSApplication.setApiKey("");
      if (result.status == HMSApplication.SUCCESS){
        console.log(result);
      }
    } catch (e) {
      console.log(e);
    }
  }
```

### **HMSFrame**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [getPreviewBitmap(frameConfiguration)](#hmsframegetpreviewbitmapframeconfiguration)  | Promise\<object> |Obtains bitmap data of the preview image. |
| [readBitmap(frameConfiguration)](#hmsframereadbitmapframeconfiguration) | Promise\<object> |Obtains bitmap data of a converted image. |
| [rotate(quadrant,fileUri)](#hmsframerotatequadrantfileuri)| Promise\<object> |Rotates the bitmap of a preview image based on the screen orientation.|

#### Public Methods

##### HMSFrame.getPreviewBitmap(frameConfiguration)

Obtains bitmap uri of the preview image. Saves result bitmap to gallery.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| frameConfiguration | [Frame](#hmsframe-data-types) | Object that contains frame configuration. | 

Call Example

```javascript
  async getPreviewBitmap() {
    try {
      var result = await HMSFrame.getPreviewBitmap({filePath : ""});
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : "" }
```

##### HMSFrame.readBitmap(frameConfiguration)

Obtains bitmap uri of the converted image. Saves result bitmap to gallery.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| frameConfiguration | [Frame](#hmsframe-data-types) | Object that contains frame configuration. | 

Call Example

```javascript
  async readBitmap() {
    try {
      var result = await HMSFrame.readBitmap({filePath : ""});
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : "" }
```

##### HMSFrame.rotate(quadrant,fileUri)

Rotates the bitmap of a preview image based on the screen orientation. Saves result bitmap to gallery.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| quadrant   | number | Screen orientation that represents rotation. | 
| fileUri    | string | Image uri.| 

Call Example

```javascript
  async rotate() {
    try {
      var result = await HMSFrame.rotate(HMSFrame.SCREEN_SECOND_QUADRANT, "");
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : "" }
```

#### HMSFrame Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|SCREEN_FIRST_QUADRANT    |0                           |Screen quadrant that represents landscape.|
|SCREEN_SECOND_QUADRANT   |1                           |Screen quadrant that represents portrait, which is 90 degrees clockwise from **SCREEN_FIRST_QUADRANT**.|
|SCREEN_THIRD_QUADRANT    |2                           |Screen quadrant that represents reverse landscape, which is 90 degrees clockwise from **SCREEN_SECOND_QUADRANT**.|
|SCREEN_FOURTH_QUADRANT   |3                           |Screen quadrant that represents reverse portrait, which is 90 degrees clockwise from **SCREEN_THIRD_QUADRANT**.|
|IMAGE_FORMAT_NV21        |17                          |Frame property, NV21 format, which corresponds to the YYYYYYYY VU VU encoding format. |
|IMAGE_FORMAT_YV12        |842094169                   |Frame property, YV12 format, which corresponds to the YYYYYYYY VV UU encoding format. |

Call Example

```js
   async rotate() {
    try {
      var result = await HMSFrame.rotate(HMSFrame.SCREEN_THIRD_QUADRANT, "");
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSFrame Data Types

#### Frame
An object that represents video frame or static image data sourced from a camera as well as related data processing logic. All fields in this object are optional. When a Frame object creation is needed by a function, only one field given below is enough to use, because first matching valid field will be used to create frame if more than one field is used.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| bitmap                            | string                    | Bitmap data as Base64 string. |
| bytes                             | [Bytes](#bytes)           | An object that contains bytes configuration.|
| byteBuffer                        | [ByteBuffer](#bytebuffer) | An object that contains byte buffer configuration.|
| filePath                          | string                    | File uri that contains frame.|
| creator                           | [Creator](#creator)       | An object that contains creator configuration.|

#### Bytes

An object that creates a frame.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| frameProperty                     | [FrameProperty](#frameproperty)    | An object that contains frame property configurations. |
| values                            | number[]                           | Array of numbers that contains data.|

#### Bytebuffer

An object that creates a frame.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| frameProperty                     | [FrameProperty](#frameproperty)    | An object that contains frame property configurations. |
| buffer                            | string                             | Buffer that contains data.|

#### Creator

A frame creation object. 

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| frameProperty                     | [FrameProperty](#frameproperty)    | An object that contains frame property configurations. |
| buffer                            | string        | Buffer that contains data.|

#### FrameProperty

Frame metadata configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| width                             | number    | Frame width, in pixels.|
| height                            | number    | Frame height, in pixels.|
| quadrant                          | number    | Screen orientation of a frame.|
| formatType                        | number    | Frame format.|
| itemIdentity                      | number    | ID of a frame.|
| timeStamp                         | number    | Timestamp of a frame.|

### **HMSTextRecognition**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [asyncAnalyzeFrame(isRemote,isStop,frameConfiguration,analyzerConfiguration)](#hmstextrecognitionasyncAnalyzeFrameisRemoteisStopframeConfigurationanalyzerConfiguration) | Promise\<object> |Asynchronously detects text information in an image. |
| [analyzeFrame(isStop,frameConfiguration,analyzerConfiguration)](#hmstextrecognitionanalyzeFrameisStopframeConfigurationanalyzerConfiguration) | Promise\<object> |Detects text information in a supplied image. |

#### Public Methods

##### HMSTextRecognition.asyncAnalyzeFrame(isRemote,isStop,frameConfiguration,analyzerConfiguration)

Asynchronously detects text information in an image.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote              | boolean | If true on-cloud analyze runs otherwise on-device.|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion. | 
| analyzerConfiguration | [AnalyzerConfiguration](#hmstextrecognition-data-types) | Analyzer configuration that includes on-cloud or on-device analyzer setting according to isRemote parameter value.|

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSTextRecognition.asyncAnalyzeFrame(
        true,
        true,
        { filePath : "" },
        { 
          textDensityScene: HMSTextRecognition.OCR_LOOSE_SCENE,
          borderType: HMSTextRecognition.NGON, 
          languageList: [HMSTextRecognition.ENGLISH, HMSTextRecognition.CHINESE]
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
{ 
  status : 0, 
  message : "Success",
  completeResult : "",                  // string that contains complete text result
  blocks : [                            // text block array that contains block objects
    {
      languageList : ["en", "zh", ...], // language list detected in block
      stringValue : "",                 // block text complete result
      possibility : 0.5,                // block possibility
      border : {                        // block border
        left : 1,
        right : 1,
        top : 1,
        down : 1
      },
      vertexes : [                      // block vertex array
        { x : 1, y : 1},                
        ...
      ],
      lines : [                         // lines in block
        {
          stringValue : "",             // line text
          border : {                    // line borders
            left : 1,
            right : 1,
            top : 1,
            down : 1
          },
          vertexes : [                  // line vertex array
            { x : 1, y : 1},                
            ...
          ],
          words : [                     // words in a line
            {
              stringValue : "",         // word text
              border : {                // word borders 
                left : 1,
                right : 1,
                top : 1,
                down : 1
              },
              vertexes : [              // word vertex array
                { x : 1, y : 1},                
                ...
              ],
            },
            ...
          ],                   
          rotatingDegree : 0.3,         // rotating degree of a line
          isVertical : true             // line is vertical or not
        },
        ...
      ]
    },
    ...
  ]
}
```

##### HMSTextRecognition.analyzeFrame(isStop,frameConfiguration,analyzerConfiguration)

Detects text information in a supplied image synchronously. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop     | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion. | 
| analyzerConfiguration | [AnalyzerConfiguration](#hmstextrecognition-data-types) | Analyzer configuration that includes on-cloud or on-device setting according to isRemote parameter value.|

Call Example

```js
  async analyzeFrame() {
    try {
      var result = await HMSTextRecognition.analyzeFrame(
        true,
        { filePath : "" },
        { language : HMSTextRecognition.ENGLISH, OCRMode : HMSTextRecognition.OCR_DETECT_MODE }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  blocks : [                           // text block array that contains block objects
   {
     languageList : ["en", "zh", ...], // language list detected in block
     stringValue : "",                 // block text complete result
     possibility : 0.5,                // block possibility
     border : {                        // block border
       left : 1,
       right : 1,
       top : 1,
       down : 1
     },
     vertexes : [                      // block vertex array
       { x : 1, y : 1},                
       ...
     ],
     lines : [                         // lines in block
       {
         stringValue : "",             // line text
         border : {                    // line borders
           left : 1,
           right : 1,
           top : 1,
           down : 1
         },
         vertexes : [                  // line vertex array
           { x : 1, y : 1},                
           ...
         ],
         words : [                     // words in a line
           {
             stringValue : "",         // word text
             border : {                // word borders 
               left : 1,
               right : 1,
               top : 1,
               down : 1
             },
             vertexes : [              // word vertex array
               { x : 1, y : 1},                
               ...
             ],
           },
           ...
         ],                   
         rotatingDegree : 0.3,         // rotating degree of a line
         isVertical : true             // line is vertical or not
       },
       ...
     ]
   },
   ...
  ]
}
```

#### HMSTextRecognition Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|OCR_DETECT_MODE          |1                           | Local analyzer OCRmode.Each supplied image is detected independently.This mode applies to single image detection.|
|OCR_TRACKING_MODE        |2                           | Local analyzer OCRmode.The detection result of the preceding frame is used as the basis to quickly detect the text position in the image.|
|OCR_COMPACT_SCENE        |2                           | Remote analyzer textDensityScene. Dense text type, such as user instructions.|
|OCR_LOOSE_SCENE          |1                           | Remote analyzer textDensityScene. Sparse text type, such as business cards. |
|ARC                      |"ARC"                       | Remote analyzer borderType. Text bounding box that is a polygon.|
|NGON                     |"NGON"                      | Remote analyzer borderType. Text bounding box that is a quadrilateral.|
|LATIN                    |"rm"                        | Analyzer language code.|
|ENGLISH                  |"en"                        | Analyzer language code.|
|CHINESE                  |"zh"                        | Analyzer language code.|
|JAPANESE                 |"ja"                        | Analyzer language code.|
|KOREAN                   |"ko"                        | Analyzer language code.|
|RUSSIAN                  |"ru"                        | Analyzer language code.|
|GERMAN                   |"de"                        | Analyzer language code.|
|FRENCH                   |"fr"                        | Analyzer language code.|
|ITALIAN                  |"it"                        | Analyzer language code.|
|PORTUGUESE               |"pt"                        | Analyzer language code.|
|SPANISH                  |"es"                        | Analyzer language code.|
|POLISH                   |"pl"                        | Analyzer language code.|
|NORWEGIAN                |"no"                        | Analyzer language code.|
|SWEDISH                  |"sv"                        | Analyzer language code.|
|DANISH                   |"da"                        | Analyzer language code.|
|TURKISH                  |"tr"                        | Analyzer language code.|
|FINNISH                  |"fi"                        | Analyzer language code.|
|THAI                     |"th"                        | Analyzer language code.|
|ARABIC                   |"ar"                        | Analyzer language code.|
|HINDI                    |"hi"                        | Analyzer language code.|

Call Example

```js
  async analyzeFrame() {
    try {
      var result = await HMSTextRecognition.analyzeFrame(
        true,
        { filePath : "" },
        { language : HMSTextRecognition.ENGLISH, OCRMode : HMSTextRecognition.OCR_DETECT_MODE }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSTextRecognition Data Types
 
##### AnalyzerConfiguration
An object that represent text recognition analyzer configuration. This can be devided into 2 parts on-cloud and on-device analyzer settings.

###### On-Cloud Analyzer

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| borderType                        | string    | Text bounding box configuration. |
| textDentisyScene                  | number    | Text type configuration.|
| languageList                      | string[]  | Language list configuration.|

###### On-Device Analyzer

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| language                          | string    | Language configuration. |
| OCRMode                           | number    | Mode configuration.|


### **HMSDocumentRecognition**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [asyncAnalyzeFrame(isStop,frameConfiguration,analyzerConfiguration)](#hmsdocumentrecognitionasyncanalyzeframeisstopframeconfigurationanalyzerconfiguration) | Promise\<object> |Asynchronously detects document information in an input image.|

#### Public Methods

##### HMSDocumentRecognition.asyncAnalyzeFrame(isStop,frameConfiguration,analyzerConfiguration)

Asynchronously detects document information in an image.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion. | 
| analyzerConfiguration | [AnalyzerConfiguration](#hmsdocumentrecognition-data-types) | Analyzer configuration that includes on-cloud or on-device setting according to isRemote parameter value.|

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSDocumentRecognition.asyncAnalyzeFrame(
        true,
        { filePath : "" },
        { 
          isFingerPrintEnabled: false,
          borderType: HMSTextRecognition.NGON, 
          languageList: [HMSTextRecognition.ENGLISH, HMSTextRecognition.CHINESE]
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
{ 
  status : 0, 
  message : "Success",
  completeResult : "",                  // string that contains complete text result
  blocks : [                            // text block array that contains block objects
    {
      stringValue : "",                 // block text complete result
      languageList : ["en", "zh", ...], // language list detected in block
      possibility : 0.5,                // block possibility
      border : {                        // block border
        left : 1,
        right : 1,
        top : 1,
        down : 1
      },
      interval : {
        intervalType : 5,               // SPACE:6 NEW_LINE_CHARACTER:8, OTHER:5
        isTextFollowed : false
      },
      sections : [                      // sections in block
        {
          stringValue : "",             // section text
          border : {                    // section borders
            left : 1,
            right : 1,
            top : 1,
            down : 1
          },
          lineList : [   
            {                             // lines in a section
              stringValue : "",           // line text
              border : {                  // line borders
                left : 1,
                right : 1,
                top : 1,
                down : 1
              },
              points : [                  // line points array
                { x : 1, y : 1},                
                ...
              ],
              wordList : [                // word list
                {
                  stringValue : "",       // word text
                  border : {              // word borders
                    left : 1,
                    right : 1,
                    top : 1,
                    down : 1
                  },
                  characterList : [      // characters in a word
                    {
                      stringValue : "",  // character text
                      border : {         // character borders
                        left : 1,
                        right : 1,
                        top : 1,
                        down : 1
                      }
                    },
                    ...
                  ]
                },
                ...
              ]
            },
            ...
          ]
        },
        ...
      ]
    },
    ...
  ]
}
```

#### HMSDocumentRecognition Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|ARC                      |"ARC"                       | Text bounding box that is a polygon.|
|NGON                     |"NGON"                      | Text bounding box that is a quadrilateral.|
|LATIN                    |"rm"                        | Analyzer language code.|
|ENGLISH                  |"en"                        | Analyzer language code.|
|CHINESE                  |"zh"                        | Analyzer language code.|
|JAPANESE                 |"ja"                        | Analyzer language code.|
|KOREAN                   |"ko"                        | Analyzer language code.|
|RUSSIAN                  |"ru"                        | Analyzer language code.|
|GERMAN                   |"de"                        | Analyzer language code.|
|FRENCH                   |"fr"                        | Analyzer language code.|
|ITALIAN                  |"it"                        | Analyzer language code.|
|PORTUGUESE               |"pt"                        | Analyzer language code.|
|SPANISH                  |"es"                        | Analyzer language code.|
|POLISH                   |"pl"                        | Analyzer language code.|
|NORWEGIAN                |"no"                        | Analyzer language code.|
|SWEDISH                  |"sv"                        | Analyzer language code.|
|DANISH                   |"da"                        | Analyzer language code.|
|TURKISH                  |"tr"                        | Analyzer language code.|
|FINNISH                  |"fi"                        | Analyzer language code.|
|THAI                     |"th"                        | Analyzer language code.|
|ARABIC                   |"ar"                        | Analyzer language code.|
|HINDI                    |"hi"                        | Analyzer language code.|
|OTHER                    |5                           | Unknown spacing interval type.|
|NEW_LINE_CHARACTER       |8                           | Newline character interval type.|
|SPACE                    |6                           | Space interval type.|

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSDocumentRecognition.asyncAnalyzeFrame(
        true,
        { filePath : "image_uri" },
        { 
          isFingerPrintEnabled: false,
          borderType: HMSTextRecognition.NGON, 
          languageList: [HMSTextRecognition.ENGLISH, HMSTextRecognition.CHINESE]
        }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSDocumentRecognition Data Types
 
##### AnalyzerConfiguration
An object that represent document recognition analyzer configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| isFingerPrintEnabled              | boolean   | Sets whether to enable certificate fingerprint verification. |
| borderType                        | string    | Sets the type of a text bounding box. |
| languageList                      | string[]  | Language list configuration. |

### **HMSBankCardRecognition**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [captureFrame(bcrCaptureConfiguration)](#hmsbankcardrecognitioncaptureframebcrcaptureconfiguration) | Promise\<object> | Start an activity to capture bank card according to given configurations. Uses [BCR_IMAGE_SAVE](#hmsbankcardrecognition-events) event to obtain saved image uri.|

#### Public Methods

##### HMSBankCardRecognition.captureFrame(bcrCaptureConfiguration)

Start an activity to capture bank card according to given configurations.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| bcrCaptureConfiguration | [BcrCaptureConfiguration](#hmsbankcardrecognition-data-types) | An object that configures capture activity. |

Call Example

```js
  async captureFrame() {
    try {
      var result = await HMSBankCardRecognition.captureFrame(
        {
          orientation: HMSBankCardRecognition.ORIENTATION_PORTRAIT,
          resultType: HMSBankCardRecognition.RESULT_ALL,
          recMode: HMSBankCardRecognition.SIMPLE_MODE
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
{ 
  status : 0, 
  message : "Success",
  errorCode : 0,        // error if code is not 0
  expire :"",           // card expire
  issuer : "",          // card issuer
  number : "",          // card number
  organization : "",    // card organization
  type : ""             // card type
}
```

#### HMSBankCardRecognition Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|ORIENTATION_AUTO              |0                      | Adaptive mode.|
|ORIENTATION_LANDSCAPE         |1                      | Landscape mode.|
|ORIENTATION_PORTRAIT          |2                      | Portrait mode.|
|ERROR_CODE_INIT_CAMERA_FAILED |10101                  | Failed to initialize the camera.|
|RESULT_ALL                    |2                      | Recognized information, such as the bank card number, validity period, issuing bank, card organization, and card type.|
|RESULT_NUM_ONLY               |0                      | Only the bank card number is recognized.|
|RESULT_SIMPLE                 |1                      | Only two items recognized, including bank card number and validity period.|
|STRICT_MODE                   |1                      | Strict mode.|
|SIMPLE_MODE                   |0                      | Weak mode.|
|BCR_IMAGE_SAVE                |"bcrSuccessImage"      | Event key that represents [BCR_IMAGE_SAVE](#hmsbankcardrecognition-events)|

Call Example

```js
  this.eventEmitter.addListener(HMSBankCardRecognition.BCR_IMAGE_SAVE, (event) => {
    console.log(event);
  });
```

#### HMSBankCardRecognition Data Types
 
##### BcrCaptureConfiguration
An object that represent capture activity configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| orientation                       | number    | Screen orientation. |
| resultType                        | number    | Result type. |
| recMode                           | number    | Recognition mode. |

#### HMSBankCardRecognition Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| BCR_IMAGE_SAVE                  | Event emitted when the bcr capture successfully completed and image saved to gallery. |

Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSBankcardRecognition);
    
    eventEmitter.addListener(HMSBankcardRecognition.BCR_IMAGE_SAVE, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    status : 0,
       *    message : "Success",
       *    result : ""          // Exists if status is 0
       * }
       * 
      */
    });
```

### **HMSGeneralCardRecognition**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [capturePreview(language,uiConfig)](#hmsgeneralcardrecognitioncapturepreviewlanguageuiconfig) | Promise\<object> | Enables the plug-in for recognizing general cards in camera streams. Uses [GCR_IMAGE_SAVE](#hmsgeneralcardrecognition-events) event to obtain saved image uri.|
| [capturePhoto(language,uiConfig)](#hmsgeneralcardrecognitioncapturephotolanguageuiconfig) | Promise\<object> | Enables the plug-in for taking a photo of a general card and recognizing the general card on the photo. Uses [GCR_IMAGE_SAVE](#hmsgeneralcardrecognition-events) event to obtain saved image uri.|
| [captureImage(language,imageUri)](#hmsgeneralcardrecognitioncaptureimagelanguageimageuri) | Promise\<object> | Enables the plug-in for recognizing static images of general cards. Uses [GCR_IMAGE_SAVE](#hmsgeneralcardrecognition-events) event to obtain saved image uri.|

#### Public Methods

##### HMSGeneralCardRecognition.capturePreview(language,uiConfig)

Enables the plug-in for recognizing general cards in camera streams. Uses [GCR_IMAGE_SAVE](#hmsgeneralcardrecognition-events) event to obtain saved image uri.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| language   | string | Language of general card recognition. Only Latin-based languages (such as en, de, fr, it, pt, and es) are supported. The default value is en.|
| uiConfig   | [UiConfig](#hmsgeneralcardrecognition-data-types) | Ui configuration for plugin.|

Call Example

```js
  async capturePreview() {
    try {
      var result = await HMSGeneralCardRecognition.capturePreview(
        "en",
        {
          tipText: 'Align Edges...',
          tipTextColor: HMSGeneralCardRecognition.GREEN,
          scanBoxCornerColor: HMSGeneralCardRecognition.CYAN,
          orientation: HMSGeneralCardRecognition.LANDSCAPE
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
{ 
  status : 0, 
  message : "Success",
  stringValue : "",      // complete result
  textBlocks : [
    {
      languageList : ["en", "zh", ...], // language list detected in block
      stringValue : "",                 // block text complete result
      possibility : 0.5,                // block possibility
      border : {                        // block border
        left : 1,
        right : 1,
        top : 1,
        down : 1
      },
      vertexes : [                      // block vertex array
        { x : 1, y : 1},                
        ...
      ],
      lines : [                         // lines in block
      {
        stringValue : "",               // line text
        border : {                      // line borders
          left : 1,
          right : 1,
          top : 1,
          down : 1
        },
        vertexes : [                    // line vertex array
          { x : 1, y : 1},                
          ...
        ],
        words : [                       // words in a line
          {
            stringValue : "",           // word text
            border : {                  // word borders 
              left : 1,
              right : 1,
              top : 1,
              down : 1
            },
            vertexes : [                // word vertex array
              { x : 1, y : 1},                
             ...
            ],
          },
          ...
        ],                   
        rotatingDegree : 0.3,           // rotating degree of a line
        isVertical : true               // line is vertical or not
      },
      ...
    ]
   },
   ...
  ] 
}
```

##### HMSGeneralCardRecognition.capturePhoto(language,uiConfig)

Enables the plug-in for taking a photo of a general card and recognizing the general card on the photo. Uses [GCR_IMAGE_SAVE](#hmsgeneralcardrecognition-events) event to obtain saved image uri.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| language   | string | Language of general card recognition. Only Latin-based languages (such as en, de, fr, it, pt, and es) are supported. The default value is en.|
| uiConfig   | [UiConfig](#hmsgeneralcardrecognition-data-types) | Ui configuration for plugin.|

Call Example

```js
  async capturePhoto() {
    try {
      var result = await HMSGeneralCardRecognition.capturePhoto(
        "en",
        {
          tipText: 'Align Edges...',
          tipTextColor: HMSGeneralCardRecognition.GREEN,
          scanBoxCornerColor: HMSGeneralCardRecognition.CYAN,
          orientation: HMSGeneralCardRecognition.LANDSCAPE
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
{ 
  status : 0, 
  message : "Success",
  stringValue : "",      // complete result
  textBlocks : [
    {
      languageList : ["en", "zh", ...], // language list detected in block
      stringValue : "",                 // block text complete result
      possibility : 0.5,                // block possibility
      border : {                        // block border
        left : 1,
        right : 1,
        top : 1,
        down : 1
      },
      vertexes : [                      // block vertex array
        { x : 1, y : 1},                
        ...
      ],
      lines : [                         // lines in block
      {
        stringValue : "",               // line text
        border : {                      // line borders
          left : 1,
          right : 1,
          top : 1,
          down : 1
        },
        vertexes : [                    // line vertex array
          { x : 1, y : 1},                
          ...
        ],
        words : [                       // words in a line
          {
            stringValue : "",           // word text
            border : {                  // word borders 
              left : 1,
              right : 1,
              top : 1,
              down : 1
            },
            vertexes : [                // word vertex array
              { x : 1, y : 1},                
             ...
            ],
          },
          ...
        ],                   
        rotatingDegree : 0.3,           // rotating degree of a line
        isVertical : true               // line is vertical or not
      },
      ...
    ]
   },
   ...
  ] 
}
```

##### HMSGeneralCardRecognition.captureImage(language,imageUri)

Enables the plug-in for recognizing static images of general cards. Uses [GCR_IMAGE_SAVE](#hmsgeneralcardrecognition-events) event to obtain saved image uri.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| language   | string | Language of general card recognition. Only Latin-based languages (such as en, de, fr, it, pt, and es) are supported. The default value is en.|
| imageUri   | string | Image uri.|

Call Example

```js
  async captureImage() {
    try {
      var result = await HMSGeneralCardRecognition.captureImage(
        "en",
        ""
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  stringValue : "",                     // complete result
  textBlocks : [
    {
      languageList : ["en", "zh", ...], // language list detected in block
      stringValue : "",                 // block text complete result
      possibility : 0.5,                // block possibility
      border : {                        // block border
        left : 1,
        right : 1,
        top : 1,
        down : 1
      },
      vertexes : [                      // block vertex array
        { x : 1, y : 1},                
        ...
      ],
      lines : [                         // lines in block
      {
        stringValue : "",               // line text
        border : {                      // line borders
          left : 1,
          right : 1,
          top : 1,
          down : 1
        },
        vertexes : [                    // line vertex array
          { x : 1, y : 1},                
          ...
        ],
        words : [                       // words in a line
          {
            stringValue : "",           // word text
            border : {                  // word borders 
              left : 1,
              right : 1,
              top : 1,
              down : 1
            },
            vertexes : [                // word vertex array
              { x : 1, y : 1},                
             ...
            ],
          },
          ...
        ],                   
        rotatingDegree : 0.3,           // rotating degree of a line
        isVertical : true               // line is vertical or not
      },
      ...
    ]
   },
   ...
  ] 
}
```

#### HMSGeneralCardRecognition Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|ORIENTATION_AUTO         |0                           | Adaptive mode.|
|ORIENTATION_LANDSCAPE    |1                           | Landscape mode.|
|ORIENTATION_PORTRAIT     |2                           | Portrait mode.|
|BLACK                    |-16777216                   | Color code.|
|BLUE                     |-16776961                   | Color code.|
|CYAN                     |-16711681                   | Color code.|
|DKGRAY                   |-12303292                   | Color code.|
|GRAY                     |-7829368                    | Color code.|
|GREEN                    |-16711936                   | Color code.|
|LTGRAY                   |-3355444                    | Color code.|
|MAGENTA                  |-65281                      | Color code.|
|RED                      |-0                          | Color code.|
|TRANSPARENT              |0                           | Color code.|
|WHITE                    |0                           | Color code.|
|YELLOW                   |0                           | Color code.|
|GCR_IMAGE_SAVE           |"gcrOnResult"               | Event key that represents [GCR_IMAGE_SAVE](#hmsgeneralcardrecognition-events)|

Call Example

```js
  this.eventEmitter.addListener(HMSGeneralCardRecognition.GCR_IMAGE_SAVE, (event) => {
    console.log(event);
  });
```

#### HMSGeneralCardRecognition Data Types
 
##### UiConfig

An object that represent ui configuration for general card recognition plugins.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| orientation                       | number    | Screen orientation. |
| resultType                        | number    | Result type. |
| recMode                           | number    | Recognition mode. |

#### HMSGeneralCardRecognition Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| GCR_IMAGE_SAVE                  | Event emitted when the general card recognition successfully completed and image saved to gallery. |

Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSGeneralCardRecognition);
    
    eventEmitter.addListener(HMSGeneralCardRecognition.GCR_IMAGE_SAVE, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    status : 0,
       *    message : "Success",
       *    result : "image_uri"  // Exists if status is 0
       * }
       * 
      */
    });
```


### **HMSFormRecognition**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [asyncAnalyzeFrame(isStop,frameConfiguration)](#hmsformrecognitionasyncanalyzeframeisstopframeconfiguration) | Promise\<object> | Asynchronous calling entry of form recognition.|
| [analyzeFrame(isStop,frameConfiguration)](#hmsformrecognitionanalyzeframeisstopframeconfiguration) | Promise\<object> | Synchronous calling entry of form recognition.|

#### Public Methods

##### HMSFormRecognition.asyncAnalyzeFrame(isStop,frameConfiguration)

Asynchronous calling entry of form recognition.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop     | boolean | Releases resources used by analyzer if true.|
| frameConfiguration   | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.|

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSFormRecognition.asyncAnalyzeFrame(
        true,
        { filePath : "" }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : {
    retCode: 0,         // table recognition is success 0 or not -1
    tableContent : {
      tableCount : 1,
      tables : [
        {
          footerInfo : "",
          headerInfo : "",
          tableID :1,
          tableBody : [
            {
              endCol: 1,
              endRow: 1,
              startCol: 1,
              startRow: 1,
              textInfo: "",
              cellCoordinate : {
                bottomLeft_x : 42,
                bottomLeft_y : 288,
                bottomRight_x : 290,
                bottomRight_y : 288,
                topLeft_x : 42,
                topLeft_y : 171,
                topRight_x : 290,
                topRight_y : 171
              }
            },
            ...
          ]
        },
        ...
      ]
    }

  }
}
```

##### HMSFormRecognition.analyzeFrame(isStop,frameConfiguration)

Synchronous calling entry of form recognition.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop     | boolean | Releases resources used by analyzer if true.|
| frameConfiguration   | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.|

Call Example

```js
  async analyzeFrame() {
    try {
      var result = await HMSFormRecognition.analyzeFrame(
        true,
        { filePath : "" }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      retCode: 0,          // table recognition is success 0 or not -1
      tableContent : {
        tableCount : 1,
        tables : [
          {
            footerInfo : "",
            headerInfo : "",
            tableID :1,
            tableBody : [
              {
                endCol: 1,
                endRow: 1,
                startCol: 1,
                startRow: 1,
                textInfo: "",
                cellCoordinate : {
                  bottomLeft_x : 42,
                  bottomLeft_y : 288,
                  bottomRight_x : 290,
                  bottomRight_y : 288,
                  topLeft_x : 42,
                  topLeft_y : 171,
                  topRight_x : 290,
                  topRight_y : 171
                }
              },
              ...
            ]
          },
          ...
        ]
      }
    },
    ...
  ]
}
```

### **HMSAft**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [init()](#hmsaftinit)                                    | Promise\<object> | Initializes the audio transcription engine on the cloud and loads engine resources. |
| [close()](#hmsaftclose)                                  | Promise\<object> | Disables the audio transcription engine to release engine resources. |
| [destroyTask(taskId)](#hmsaftdestroytasktaskid)          | Promise\<object> | Destroys a long audio transcription task on the cloud. If the task is destroyed after the audio file is successfully uploaded, the transcription has started and charging cannot be canceled. |
| [getLongAftResult(taskId)](#hmsaftgetlongaftresulttaskid)| Promise\<object> | Obtains the long audio transcription result from the cloud. |
| [pauseTask(taskId)](#hmsaftpausetasktaskid)              | Promise\<object> | Pauses the task for given taskId. |
| [startTask(taskId)](#hmsaftstarttasktaskid)             | Promise\<object> | Resumes long audio transcription task on the cloud. |
| [setAftListener()](#hmsaftsetaftlistener)                | Promise\<object> | Sets [AFT_ON_INIT_COMPLETE,AFT_ON_UPLOAD_PROGRESS,AFT_ON_EVENT,AFT_ON_RESULT,AFT_ON_ERROR](#hmsaft-events) events. |
| [shortRecognize(uri,remoteAftSetting)](#hmsaftshortrecognizeuriremoteaftsetting) | Promise\<object> | Converts a short audio file on the cloud. |
| [longRecognize(uri,remoteAftSetting)](#hmsaftlongrecognizeuriremoteaftsetting) | Promise\<object> | Converts a long audio file on the cloud. |

#### Public Methods

##### HMSAft.init()

Initializes the audio transcription engine on the cloud and loads engine resources.

Call Example

```js
  async init() {
    try {
      var result = await HMSAft.init()
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

##### HMSAft.close()

Disables the audio transcription engine to release engine resources.

Call Example

```js
  async close() {
    try {
      var result = await HMSAft.close()
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

##### HMSAft.setAftListener()

Sets [AFT_ON_INIT_COMPLETE,AFT_ON_UPLOAD_PROGRESS,AFT_ON_EVENT,AFT_ON_RESULT,AFT_ON_ERROR](#hmsaft-events) events.

Call Example

```js
  async setAftListener() {
    try {
      var result = await HMSAft.setAftListener()
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

##### HMSAft.destroyTask(taskId)

Destroys a long audio transcription task on the cloud. If the task is destroyed after the audio file is successfully uploaded, the transcription has started and charging cannot be canceled.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| taskId     | string | ID of the audio transcription task.  |

Call Example

```js
  async destroyTask() {
    try {
      var result = await HMSAft.destroyTask("")
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

##### HMSAft.getLongAftResult(taskId)

Obtains the long audio transcription result from the cloud.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| taskId     | string | ID of the audio transcription task.  |

Call Example

```js
  async getLongAftResult() {
    try {
      var result = await HMSAft.getLongAftResult("")
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

##### HMSAft.pauseTask(taskId)

Pauses a long audio transcription task on the cloud.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| taskId     | string | ID of the audio transcription task.  |

Call Example

```js
  async pauseTask() {
    try {
      var result = await HMSAft.pauseTask("")
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

##### HMSAft.startTask(taskId)

Resumes long audio transcription task on the cloud.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| taskId     | string | ID of the audio transcription task.  |

Call Example

```js
  async startTask() {
    try {
      var result = await HMSAft.startTask("")
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

##### HMSAft.shortRecognize(uri,remoteAftSetting)

Converts a short audio file on the cloud.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| uri        | string | File uri.  |
| remoteAftSetting | [RemoteAftSetting](#hmsaft-data-types) | Aft setting for recognition.  |

Call Example

```js
  async shortRecognize() {
    try {
      var result = await HMSAft.shortRecognize(
        "",
        {
          languageCode: HMSAft.LANGUAGE_EN_US,
          enablePunctuation: true,
          enableWordTimeOffset: false,
          enableSentenceTimeOffset: false
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
{ 
  status : 0, 
  message : "Success", 
  result : ""         // transcription task id 
}
```

##### HMSAft.longRecognize(uri,remoteAftSetting)

Converts a long audio file on the cloud.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| uri        | string | File uri.  |
| remoteAftSetting | [RemoteAftSetting](#hmsaft-data-types) | Aft setting for recognition.  |

Call Example

```js
  async shortRecognize() {
    try {
      var result = await HMSAft.shortRecognize(
        "",
        {
          languageCode: HMSAft.LANGUAGE_EN_US,
          enablePunctuation: true,
          enableWordTimeOffset: false,
          enableSentenceTimeOffset: false
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
{ 
  status : 0, 
  message : "Success", 
  result : ""          // transcription task id 
}
```

#### HMSAft Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|LANGUAGE_ZH                    |"zh"                  | Language code.|
|LANGUAGE_EN_US                 |"en-US"               | Language code.|
|ERR_UNKNOWN                    |11199                 | Unknown error.|
|ERR_TASK_NOT_EXISTED           |11110                 | The transcription task does not exist.|
|ERR_TASK_ALREADY_INPROGRESS    |11114                 | The task is being executed and cannot be submitted.|
|ERR_RESULT_WHEN_UPLOADING      |11109                 | The transcription result cannot be obtained during upload.|
|ERR_NO_ENOUGH_STORAGE          |11115                 | Insufficient device storage space.|
|ERR_NETCONNECT_FAILED          |11108                 | Network connection exception.|
|ERR_LANGUAGE_CODE_NOTSUPPORTED |11102                 | The language is not supported.|
|ERR_INTERNAL                   |11198                 | Internal error.|
|ERR_ILLEGAL_PARAMETER          |11106                 | Invalid parameter.|
|ERR_FILE_NOT_FOUND             |11105                 | The audio file does not exist.|
|ERR_ENGINE_BUSY                |11107                 | The transcription engine is busy.|
|ERR_SERVICE_CREDIT             |11122                 | Account is in arrears or the free quota has been used up.|
|ERR_AUTHORIZE_FAILED           |11119                 | Authentication failed.|
|ERR_AUDIO_UPLOAD_FAILED        |11113                 | Audio file upload failed.|
|ERR_AUDIO_TRANSCRIPT_FAILED    |11111                 | File transcription failed.|
|ERR_AUDIO_LENGTH_OVERFLOW      |11104                 | The audio duration is too long.|
|ERR_AUDIO_FILE_NOTSUPPORTED    |11101                 | The audio file is not supported. |
|ERR_AUDIO_FILE_SIZE_OVERFLOW   |11103                 | The audio file size is too large.|
|ERR_AUDIO_INIT_FAILED          |11112                 | File transcription failed.|
|AFT_ON_ERROR                   |"aftOnError"          | Event key that represents [AFT_ON_ERROR](#hmsaft-events)|
|AFT_ON_EVENT                   |"aftOnEvent"          | Event key that represents [AFT_ON_EVENT](#hmsaft-events)|
|AFT_ON_INIT_COMPLETE           |"aftOnInitComplete"   | Event key that represents [AFT_ON_INIT_COMPLETE](#hmsaft-events)|
|AFT_ON_UPLOAD_PROGRESS         |"aftOnUploadProgress" | Event key that represents [AFT_ON_UPLOAD_PROGRESS](#hmsaft-events)|
|AFT_ON_RESULT                  |"aftOnResult"         | Event key that represents [AFT_ON_RESULT](#hmsaft-events)|

Call Example

```js
  this.eventEmitter.addListener(HMSAft.AFT_ON_RESULT, (event) => {
    console.log(event);
  });
```

#### HMSAft Data Types
 
##### RemoteAftSetting

An object that represents audio transcription settings on the cloud.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| languageCode                      | string    | Language code. |
| enablePunctuation                 | boolean   | Automatically add punctuations to the converted text. Default is false. |
| enableWordTimeOffset              | boolean   | Time shift of a sentence in the audio file. (This parameter needs to be set only when the audio duration is less than 1 minute.) . Default is false. |
| enableSentenceTimeOffset          | boolean   | Time shift of a sentence in the audio file. Default is false. |

#### HMSAft Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| AFT_ON_ERROR                    | Event emitted when if an audio transcription error occurs. |
| AFT_ON_EVENT                    | Reserved. |
| AFT_ON_INIT_COMPLETE            | Reserved. |
| AFT_ON_UPLOAD_PROGRESS          | Reserved. |
| AFT_ON_RESULT                   | Event emitted when the audio transcription result is returned on the cloud. |

Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSAft);
    
    eventEmitter.addListener(HMSAft.AFT_ON_ERROR, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    taskId : "",
       *    error : 11113, // example error code
       *    message : "Audio file upload failed."   // example error message
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSAft.AFT_ON_RESULT, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    isComplete : true  
       *    taskId : "",       // This field exist if isComplete is true
       *    text : "",         // This field exist if isComplete is true
       *    words : [          // This field exist if isComplete is true
       *      {                       
       *        startTime : 0,
       *        endTime : 0,
       *        text : ""
       *      },
       *      ...
       *    ],              
       *    sentences : [    // This field exist if isComplete is true
       *      {                       
       *        startTime : 0,
       *        endTime : 0,
       *        text : ""
       *      },
       *      ...
       *    ],              
       * }
      */
    });

    eventEmitter.addListener(HMSAft.AFT_ON_EVENT, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    taskId : "",
       *    eventId : 11111111 
       * }
      */
    });

    eventEmitter.addListener(HMSAft.AFT_ON_UPLOAD_PROGRESS, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    taskId : "",
       *    progress : 100.0
       * }
      */
    });

    eventEmitter.addListener(HMSAft.AFT_ON_INIT_COMPLETE, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    taskId : "",
       * }
      */
    });
```

### **HMSAsr**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [destroy()](#hmsasrdestroy)                              | Promise\<object> | Destroy and release Asr recognizer. |
| [createAsrRecognizer()](#hmsasrcreateasrrecognizer)      | Promise\<object> | Creates Asr recognizer. Sets [ASR_ON_RESULTS,ASR_ON_RECOGNIZING_RESULTS, ASR_ON_ERROR and ASR_ON_START_LISTENING](#hmsasr-events) events. |
| [startRecognizing(language,feature)](#hmsasrstartrecognizinglanguagefeature)| Promise\<object> | Start recognizer. |
| [startRecognizingPlugin(language,feature)](#hmsasrstartrecognizingpluginlanguagefeature)| Promise\<object> | Start recognizer plugin.|

#### Public Methods

##### HMSAsr.destroy()

Destroy and release Asr recognizer.

Call Example

```js
  async destroy() {
    try {
      var result = await HMSAsr.destroy()
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

##### HMSAsr.createAsrRecognizer()

Creates Asr recognizer. Sets [ASR_ON_RESULTS,ASR_ON_RECOGNIZING_RESULTS, ASR_ON_ERROR and ASR_ON_START_LISTENING](#hmsasr-events) events.

Call Example

```js
  async createAsrRecognizer() {
    try {
      var result = await HMSAsr.createAsrRecognizer()
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

##### HMSAsr.startRecognizing(language,feature)

Start recognizer.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| language   | string | Language code.  |
| feature    | number | Recognition result type.  |

Call Example

```js
  async startRecognizing() {
    try {
      var result = await HMSAsr.startRecognizing(HMSAsr.LAN_EN_US, HMSAsr.FEATURE_WORD_FLUX);
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

##### HMSAsr.startRecognizingPlugin(language,feature)

Start recognizer plugin.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| language   | string | Language code.  |
| feature    | number | Recognition result type.  |

Call Example

```js
  async startRecognizingPlugin() {
    try {
      var result = await HMSAsr.startRecognizingPlugin(HMSAsr.LAN_EN_US, HMSAsr.FEATURE_WORD_FLUX);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : "" }
```

#### HMSAsr Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|LANGUAGE_ZH_CN                 |"zh-CN"               | Language code.|
|LANGUAGE_EN_US                 |"en-US"               | Language code.|
|LAN_FR_FR                      |"fr-FR"               | Language code.|
|LANGUAGE_ES_ES                 |"es-ES"               | Language code.|
|LAN_DE_DE                      |"de-DE"               | Language code.|
|STATE_WAITING                  |9                     | Status code in the [ASR_ON_STATE](#hmsasr-events) event, indicating that data is sent for the first time on a weak network.|
|STATE_NO_UNDERSTAND            |6                     | Status code in the [ASR_ON_STATE](#hmsasr-events) event, indicating that the current frame fails to be detected on the cloud.|
|STATE_NO_SOUND_TIMES_EXCEED    |3                     | Status code in the [ASR_ON_STATE](#hmsasr-events) event, indicating that no result is detected within 6s.|
|STATE_NO_SOUND                 |2                     | Status code in the [ASR_ON_STATE](#hmsasr-events) event, indicating that no speech is detected within 3s.|
|STATE_NO_NETWORK               |7                     | Status code in the [ASR_ON_STATE](#hmsasr-events) event, indicating that no network is available in the current environment.|
|STATE_LISTENING                |1                     | Status code in the [ASR_ON_STATE](#hmsasr-events) event, indicating that the recorder is ready.|
|ASR_ON_ERROR                   |"asrOnError"               | Event key that represents [ASR_ON_ERROR](#hmsasr-events)|
|ASR_ON_RECOGNIZING_RESULTS     |"asrOnRecognizingResults"  | Event key that represents [ASR_ON_RECOGNIZING_RESULTS](#hmsasr-events)|
|ASR_ON_RESULTS                 |"asrOnResults"             | Event key that represents [ASR_ON_RESULTS](#hmsasr-events)|
|ASR_ON_START_LISTENING         |"asrOnStartListening"      | Event key that represents [ASR_ON_START_LISTENING](#hmsasr-events)|
|ASR_ON_STARTING_SPEECH         |"asrOnStartingOfSpeech"    | Event key that represents [ASR_ON_STARTING_SPEECH](#hmsasr-events)|
|ASR_ON_STATE                   |"asrOnState"               | Event key that represents [ASR_ON_STATE](#hmsasr-events)|
|ASR_ON_VOICE_DATA_RECEIVED     |"asrOnVoiceDataReceived"   | Event key that represents [ASR_ON_VOICE_DATA_RECEIVED](#hmsasr-events)|
|ERR_NO_NETWORK                  |11202                 | Network unavailable.|
|ERR_NO_UNDERSTAND               |11204                 | The on-cloud recognition result is null.|
|ERR_SERVICE_UNAVAILABLE         |11203                 | Service unavailable.|
|ERR_INVALIDATE_TOKEN            |11219                 | Authentication failed.|
|FEATURE_ALL_IN_ONE              |12                    | After the speech is complete, the recognition result is returned at a time.|
|FEATURE_WORD_FLUX               |11                    | The recognition result is returned along with the speech.|

Call Example

```js
  this.eventEmitter.addListener(HMSAsr.ASR_ON_ERROR, (event) => {
    console.log(event);
  });
```

#### HMSAsr Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| ASR_ON_ERROR                    | Event emitted when a network error or recognition error occurs. |
| ASR_ON_RECOGNIZING_RESULTS      | Event emitted when the speech recognition mode is set to FEATURE_WORDFLUX, the speech recognizer continuously returns the speech recognition result through this API. |
| ASR_ON_RESULTS                  | Event emitted when the speech recognition result is received from the speech recognizer. |
| ASR_ON_START_LISTENING          | Event emitted when the recorder starts to receive speech. |
| ASR_ON_STARTING_SPEECH          | Event emitted when a user starts to speak, that is, the speech recognizer detects that the user starts to speak. |
| ASR_ON_STATE                    | Event emitted when the app status change. |
| ASR_ON_VOICE_DATA_RECEIVED      | Event emitted when voice data received by recognizer. |

Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSAsr);
    
    eventEmitter.addListener(HMSAsr.ASR_ON_ERROR, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    error : 11204,                          // example error code
       *    errorMessage : "Speech unrecognized."   // example error message
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSAsr.ASR_ON_START_LISTENING, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    info : "Listening started"
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSAsr.ASR_ON_STARTING_SPEECH, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    info : "Speech started"
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSAsr.ASR_ON_VOICE_DATA_RECEIVED, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    data : [1,2,3,4,5]  // data array in bytes
       *    energy : 1.0
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSAsr.ASR_ON_STATE, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    state : 1,
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSAsr.ASR_ON_RESULTS, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    result : "",
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSAsr.ASR_ON_RECOGNIZING_RESULTS, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    result : "",
       * }
       * 
      */
    });
```

### **HMSTranslate**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [asyncTranslate(isRemote,isStop,text,translatorSetting)](#hmstranslateasynctranslateisremoteisstoptexttranslatorsetting)           | Promise\<object> | Asynchronously translates text with analyzer created with given configuration. |
| [preparedModel(strategyConfiguration,translatorSetting)](#hmstranslatepreparedmodelstrategyconfigurationtranslatorsetting)  | Promise\<object> | Downloads the model for local translation. |
| [syncTranslate(isRemote,isStop,text,translatorSetting)](#hmstranslatesynctranslateisremoteisstoptexttranslatorsetting)   | Promise\<object> | Synchronously translates text with analyzer created with given configuration. |
| [getAllLanguages(isRemote)](#hmstranslategetalllanguagesisremote)| Promise\<object> | Asynchronously obtains languages. |
| [syncGetAllLanguages(isRemote)](#hmstranslatesyncgetalllanguagesisremote)   | Promise\<object> | Synchronously obtains languages. |

#### Public Methods

##### HMSTranslate.asyncTranslate(isRemote,isStop,text,translatorSetting)

Asynchronously translates text with analyzer created with given configuration.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote   | boolean | If true translator runs on-cloud otherwise on-device. |
| isStop     | boolean | Releases resources used by translator. |
| text       | string  | Text to be translated. |
| translatorSetting    | [TranslatorSetting](#hmstranslate-data-types) | Configuration for translator. |

Call Example

```js
  async asyncTranslate() {
    try {
      var result = await HMSTranslate.asyncTranslate(
        true, 
        true, 
        "hello",
        { 
          sourceLanguageCode: HMSTranslate.ENGLISH, 
          targetLanguageCode: HMSTranslate.CHINESE
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
{ status : 0, message : "Success", result : "你好" }
```

##### HMSTranslate.syncTranslate(isRemote,isStop,text,translatorSetting)

Synchronously translates text with analyzer created with given configuration.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote   | boolean | If true translator runs on-cloud otherwise on-device. |
| isStop     | boolean | Releases resources used by translator. |
| text       | string  | Text to be translated. |
| translatorSetting    | [TranslatorSetting](#hmstranslate-data-types) | Configuration for translator. |

Call Example

```js
  async syncTranslate() {
    try {
      var result = await HMSTranslate.syncTranslate(
        true, 
        true, 
        "hello",
        { 
          sourceLanguageCode: HMSTranslate.ENGLISH, 
          targetLanguageCode: HMSTranslate.CHINESE
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
{ status : 0, message : "Success", result : "你好" }
```

##### HMSTranslate.preparedModel(strategyConfiguration,translatorSetting)

Downloads the model for local translation. Sets [TRANSLATE_DOWNLOAD_ON_PROCESS](#hmstranslate-events) for tracking download process.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| strategyConfiguration| [StrategyConfiguration](#hmsmodeldownload-data-types) | Model download strategy configuration. |
| translatorSetting    | [TranslatorSetting](#hmstranslate-data-types) | Configuration for translator. |

Call Example

```js
  async preparedModel() {
    try {
      var result = await HMSTranslate.preparedModel(
        {
          needWifi : true,
          needCharging : false,
          needDeviceIdle : true,
          region : HMSModelDownload.REGION_DR_EUROPE
        },
        { 
          sourceLanguageCode: HMSTranslate.ENGLISH, 
          targetLanguageCode: HMSTranslate.CHINESE
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
{ status : 0, message : "Success", result : "你好" }
```

##### HMSTranslate.getAllLanguages(isRemote)

Asynchronously obtains languages.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote   | boolean | If true on-cloud languages will be returned otherwise on-device.|

Call Example

```js
  async getAllLanguages() {
    try {
      var result = await HMSTranslate.getAllLanguages(true);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : {["en", "zh", ...]} }
```

##### HMSTranslate.syncGetAllLanguages(isRemote)

Synchronously obtains languages.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote   | boolean | If true on-cloud languages will be returned otherwise on-device.|

Call Example

```js
  async syncGetAllLanguages() {
    try {
      var result = await HMSTranslate.syncGetAllLanguages(true);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : {["en", "zh", ...]} }
```


#### HMSTranslate Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|TRANSLATE_DOWNLOAD_ON_PROCESS  |"translateDownloadProcess"  | Event key that represents [TRANSLATE_DOWNLOAD_ON_PROCESS](#hmstranslate-events)|
|LATIN                    |"rm"                        | Translator language code.|
|ENGLISH                  |"en"                        | Translator language code.|
|CHINESE                  |"zh"                        | Translator language code.|
|JAPANESE                 |"ja"                        | Translator language code.|
|KOREAN                   |"ko"                        | Translator language code.|
|RUSSIAN                  |"ru"                        | Translator language code.|
|GERMAN                   |"de"                        | Translator language code.|
|FRENCH                   |"fr"                        | Translator language code.|
|ITALIAN                  |"it"                        | Translator language code.|
|PORTUGUESE               |"pt"                        | Translator language code.|
|SPANISH                  |"es"                        | Translator language code.|
|POLISH                   |"pl"                        | Translator language code.|
|NORWEGIAN                |"no"                        | Translator language code.|
|SWEDISH                  |"sv"                        | Translator language code.|
|DANISH                   |"da"                        | Translator language code.|
|TURKISH                  |"tr"                        | Translator language code.|
|FINNISH                  |"fi"                        | Translator language code.|
|THAI                     |"th"                        | Translator language code.|
|ARABIC                   |"ar"                        | Translator language code.|
|HINDI                    |"hi"                        | Translator language code.|

Call Example

```js
  this.eventEmitter.addListener(HMSTranslate.TRANSLATE_DOWNLOAD_ON_PROCESS, (event) => {
    console.log(event);
  });
```

#### HMSTranslate Data Types
 
##### TranslatorSetting

An object that represents audio transcription settings on the cloud.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| sourceLanguageCode                | string    | Source language code that represents given text language code. |
| targetLanguageCode                | string    | Target language code that represents result language code. |

#### HMSTranslate Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| TRANSLATE_DOWNLOAD_ON_PROCESS   | Event emitted when prepared model download starts. |

Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSAft);
    
    eventEmitter.addListener(HMSTranslate.TRANSLATE_DOWNLOAD_ON_PROCESS, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    alreadyDownloadLength : "1000"
       *    totalLength : "2000"   
       * }
       * 
      */
    });
```

### **HMSLanguageDetection**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [probabilityDetect(isRemote,isStop,trustedThreshold,sourceText)](#hmslanguagedetectionprobabilitydetectisremoteisstoptrustedthresholdsourcetext) | Promise\<object> | Returns multi-language detection results based on the supplied text. |
| [firstBestDetect(isRemote,isStop,trustedThreshold,sourceText)](#hmslanguagedetectionfirstbestdetectisremoteisstoptrustedthresholdsourcetext)  | Promise\<object> | Returns the language detection result with the highest confidence based on the supplied text. |
| [syncProbabilityDetect(isRemote,isStop,trustedThreshold,sourceText)](#hmslanguagedetectionsyncprobabilitydetectisremoteisstoptrustedthresholdsourcetext)  | Promise\<object> | Synchronously returns multi-language detection results based on the supplied text. |
| [syncFirstBestDetect(isRemote,isStop,trustedThreshold,sourceText)](#hmslanguagedetectionsyncfirstbestdetectisremoteisstoptrustedthresholdsourcetext)| Promise\<object> | Synchronously returns the language detection result with the highest confidence based on the supplied text. |

#### Public Methods

##### HMSLanguageDetection.probabilityDetect(isRemote,isStop,trustedThreshold,sourceText)

Returns multi-language detection results based on the supplied text.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote         | boolean | If true detector run on-cloud otherwise on-device. |
| isStop           | boolean | If true releases resources used by detector. |
| trustedThreshold | number  | Confidence of language detection. |
| sourceText       | string  | Text to be detected. |

Call Example

```js
  async probabilityDetect() {
    try {
      var result = await HMSLanguageDetection.probabilityDetect(
        true, 
        true, 
        HMSLanguageDetection.PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD, 
        ""
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      languageCode : "",
      probability : 1.0
    },
    ...
  ]
}
```

##### HMSLanguageDetection.firstBestDetect(isRemote,isStop,trustedThreshold,sourceText)

Returns the language detection result with the highest confidence based on the supplied text.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote         | boolean | If true detector run on-cloud otherwise on-device. |
| isStop           | boolean | If true releases resources used by detector. |
| trustedThreshold | number  | Confidence of language detection. |
| sourceText       | string  | Text to be detected. |

Call Example

```js
  async firstBestDetect() {
    try {
      var result = await HMSLanguageDetection.firstBestDetect(
        true, 
        true, 
        HMSLanguageDetection.PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD, 
        ""
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : "en"
}
```

##### HMSLanguageDetection.syncProbabilityDetect(isRemote,isStop,trustedThreshold,sourceText)

Synchronously returns multi-language detection results based on the supplied text.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote         | boolean | If true detector run on-cloud otherwise on-device. |
| isStop           | boolean | If true releases resources used by detector. |
| trustedThreshold | number  | Confidence of language detection. |
| sourceText       | string  | Text to be detected. |

Call Example

```js
  async syncProbabilityDetect() {
    try {
      var result = await HMSLanguageDetection.syncProbabilityDetect(
        true, 
        true, 
        HMSLanguageDetection.PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD, 
        ""
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      languageCode : "",
      probability : 1.0
    },
    ...
  ]
}
```

##### HMSLanguageDetection.syncFirstBestDetect(isRemote,isStop,trustedThreshold,sourceText)

Synchronously returns the language detection result with the highest confidence based on the supplied text.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote         | boolean | If true detector run on-cloud otherwise on-device. |
| isStop           | boolean | If true releases resources used by detector. |
| trustedThreshold | number  | Confidence of language detection. |
| sourceText       | string  | Text to be detected. |

Call Example

```js
  async syncFirstBestDetect() {
    try {
      var result = await HMSLanguageDetection.syncFirstBestDetect6(
        true, 
        true, 
        HMSLanguageDetection.PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD, 
        ""
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : "en"
}
```

#### HMSLanguageDetection Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|FIRST_BEST_DETECTION_LANGUAGE_TRUSTED_THRESHOLD   |0.5f      | Confidence threshold in the scenario where the language detection result with the highest confidence is returned. |
|PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD  |0.01f     | Confidence threshold in the scenario where detection results of multiple languages are returned. |
|UNDETECTION_LANGUAGE_TRUSTED_THRESHOLD            |"unknown" | Undetected language code. |

### **HMSSpeechRtt**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [startRecognizing(rttConfiguration)](#hmsspeechrttstartrecognizingrttconfiguration) | Promise\<object> | Starts recognition. |
| [setRealTimeTranscriptionListener()](#hmsspeechrttsetrealtimetranscriptionlistener)  | Promise\<object> | Sets [SPEECH_RTT_ON_RECOGNIZING_RESULTS, SPEECH_RTT_ON_ERROR, SPEECH_RTT_ON_LISTENING, SPEECH_RTT_ON_STARTING_OF_SPEECH, SPEECH_RTT_ON_VOICE_DATA_RECEIVED and SPEECH_RTT_ON_STATE](#hmsspeechrtt-events) events.|
| [destroy()](#hmsspeechrttdestroy)   | Promise\<object> | Stops recognition and releases resources. |

#### Public Methods

##### HMSSpeechRtt.startRecognizing(rttConfiguration)

Starts recognition.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| rttConfiguration  | [RttConfiguration](#hmsspeechrtt-data-types) | Recognizer configuration. |


Call Example

```js
  async startRecognizing(sentence) {
    try {
      var result = await HMSSpeechRtt.startRecognizing(
        {
          language: HMSSpeechRtt.LAN_EN_US,
          enablePunctuation: true,
          enableSentenceTimeOffset: true,
          enableWordTimeOffset: true
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

##### HMSSpeechRtt.setRealTimeTranscriptionListener()

Sets [SPEECH_RTT_ON_RECOGNIZING_RESULTS, SPEECH_RTT_ON_ERROR, SPEECH_RTT_ON_LISTENING, SPEECH_RTT_ON_STARTING_OF_SPEECH, SPEECH_RTT_ON_VOICE_DATA_RECEIVED and SPEECH_RTT_ON_STATE](#hmsspeechrtt-events) events.

Call Example

```js
  async setRealTimeTranscriptionListener() {
    try {
      var result = await HMSSpeechRtt.setRealTimeTranscriptionListener();
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

##### HMSSpeechRtt.destroy()

Stops recognition and releases resources.

Call Example

```js
  async destroy() {
    try {
      var result = await HMSSpeechRtt.destroy();
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
"

#### HMSSpeechRtt Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|ERR_INVALID_TOKEN                |13219                          | Authentication failed.|
|ERR_NO_NETWORK                   |13202                          | Network error.|
|ERR_SERVICE_CREDIT               |13222                          | Your account is in arrears or the free quota has been used up.|
|ERR_SERVICE_UNAVAILABLE          |13203                          | Service unavailable.|
|LAN_EN_US                        |"en-US"                        | Language code.|
|LAN_FR_FR                        |"fr-FR"                        | Language code.|
|LAN_ZH_CN                        |"zn-CN"                        | Language code.|
|STATE_LISTENING                  |1                              | The recoder is ready. |
|STATE_NO_NETWORK                 |7                              | No network is available.|
|STATE_NO_UNDERSTAND              |6                              | Translator language code.|
|STATE_SERVICE_RECONNECTED        |43                             | The session is reconnected successfully.|
|STATE_SERVICE_RECONNECTING       |42                             | The session is reconnecting.|
|SPEECH_RTT_ON_RECOGNIZING_RESULTS|"speechRttOnRecognizingResults"| Event key that represents [SPEECH_RTT_ON_RECOGNIZING_RESULTS](#hmsspeechrtt-events).|
|SPEECH_RTT_ON_ERROR              |"speechRttOnError"             |Event key that represents [SPEECH_RTT_ON_ERROR](#hmsspeechrtt-events).|
|SPEECH_RTT_ON_LISTENING          |"speechRttOnListening"         |Event key that represents [SPEECH_RTT_ON_LISTENING](#hmsspeechrtt-events).|
|SPEECH_RTT_ON_STARTING_OF_SPEECH |"speechRttOnStartingOfSpeech"  | Event key that represents [SPEECH_RTT_ON_STARTING_OF_SPEECH](#hmsspeechrtt-events).|
|SPEECH_RTT_ON_VOICE_DATA_RECEIVED|"speechRttOnVoiceDataReceived" |Event key that represents [SPEECH_RTT_ON_VOICE_DATA_RECEIVED](#hmsspeechrtt-events).|
|SPEECH_RTT_ON_STATE              |"speechRttOnState"             |Event key that represents [SPEECH_RTT_ON_STATE](#hmsspeechrtt-events).|
|SCENES_SHOPPING                  |"shopping"                     |The application scenario is shopping.|

Call Example

```js
  this.eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_ERROR, (event) => {
    console.log(event);
  });
```

#### HMSSpeechRtt Data Types
 
##### RttConfiguration

An object that represents audio transcription settings on the cloud.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| language                          | string    | Language code. |
| enablePunctuation                 | boolean   | Enables or disables punctuation.|
| enableSentenceTimeOffset          | boolean   | Enables or disables sentence time offset.|
| enableWordTimeOffset              | boolean   | Enables or disables word time offset. |

#### HMSSpeechRtt Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| SPEECH_RTT_ON_RECOGNIZING_RESULTS   | Event emitted when a network error or recognition error occurs. |
| SPEECH_RTT_ON_ERROR                 | Event emitted when text recognized by the speech recognizer. |
| SPEECH_RTT_ON_LISTENING             | Event emitted when the recorder starts to receive speech. |
| SPEECH_RTT_ON_STARTING_OF_SPEECH    | Event emitted when a user starts to speak, that is, the speech recognizer detects that the user starts to speak. |
| SPEECH_RTT_ON_VOICE_DATA_RECEIVED   | Event emitted when voice received. |
| SPEECH_RTT_ON_STATE                 | Event emitted when app status change. |

Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSSpeechRtt);
    
    eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_RECOGNIZING_RESULTS, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    text : "",
       *    sentenceOffset : {
       *      startTime : "",
       *      endTime : "",
       *      text : ""
       *    }
       *    wordOffset : {
       *      startTime : "",
       *      endTime : "",
       *      text : ""
       *    }
       *    isComplete : false
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_ERROR, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    error : 13203,
       *    errorMessage : "Service unavailable."
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_LISTENING, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    info : "Listening start"
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_STARTING_OF_SPEECH, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    info : "Speech start"
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_VOICE_DATA_RECEIVED, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    data : [1,1,2,2,3,4],
       *    energy : 1.0,
       *    encoding : "",
       *    sampleRate : 1,
       *    channelCount : 2,
       *    bitWidth : 1
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSSpeechRtt.SPEECH_RTT_ON_STATE, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    state : 1
       * }
       * 
      */
    });
```


### **HMSSoundDetect**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [createSoundDetector()](#hmssounddetectcreatesounddetector)  | Promise\<object> | Creates sound detector. |
| [destroy()](#hmssounddetectdestroy)      | Promise\<object> | Destroys sound detector and releases resources. |
| [stop()](#hmssounddetectstop)| Promise\<object> | Stops sound detector. |
| [start()](#hmssounddetectstart)| Promise\<object> | Starts sound detector and returns true if it is started.|
| [setSoundDetectorListener()](#hmssounddetectsetsounddetectorlistener)| Promise\<object> |Sets [SOUND_DETECT_ON_SUCCESS and SOUND_DETECT_ON_FAILURE](#hmssounddetect-events) events to obtain results. |

#### Public Methods

##### HMSSoundDetect.createSoundDetector()

Creates sound detector.

Call Example

```js
  async createSoundDetector() {
    try {
      var result = await HMSSoundDetect.createSoundDetector();
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

##### HMSSoundDetect.destroy()

Destroys sound detector and releases resources.

Call Example

```js
  async createSoundDetector() {
    try {
      var result = await HMSSoundDetect.createSoundDetector();
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

##### HMSSoundDetect.stop()

Stops sound detector and releases resources.

Call Example

```js
  async stop() {
    try {
      var result = await HMSSoundDetect.stop();
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

##### HMSSoundDetect.start()

Starts sound detector and returns true if it is started.

Call Example

```js
  async start() {
    try {
      var result = await HMSSoundDetect.start();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : true }
```

##### HMSSoundDetect.setSoundDetectorListener()

Sets [SOUND_DETECT_ON_SUCCESS and SOUND_DETECT_ON_FAILURE](#hmssounddetect-events) events to obtain results.

Call Example

```js
  async setSoundDetectorListener() {
    try {
      var result = await HMSSoundDetect.setSoundDetectorListener()
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

#### HMSSoundDetect Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|SOUND_DETECT_ERROR_AUDIO          |12203                  | Detection failure: microphone error.|
|SOUND_DETECT_ERROR_FATAL_ERROR    |12202                  | Detection failure: fatal error.|
|SOUND_DETECT_ERROR_NO_MEM         |12201                  | Detection failure: memory exhausted.|
|SOUND_DETECT_ERROR_INTERNAL       |12298                  | Detection failure: internal error.|
|SOUND_EVENT_TYPE_ALARM            |11                     | Detection result type: siren sound.|
|SOUND_EVENT_TYPE_BABY_CRY         |1                      | Detection result type: baby's cry.|
|SOUND_EVENT_TYPE_BARK             |6                      | Detection result type: dog's bark.|
|SOUND_EVENT_TYPE_CAR_ALARM        |8                      | Detection result type: car horn sound.|
|SOUND_EVENT_TYPE_DOOR_BELL        |9                      | Detection result type: doorbell sound.|
|SOUND_EVENT_TYPE_KNOCK            |10                     | Detection result type: knock.|
|SOUND_EVENT_TYPE_LAUGHTER         |0                      | Detection result type: laughter.|
|SOUND_EVENT_TYPE_MEOW             |5                      | Detection result type: cat's meow.|
|SOUND_EVENT_TYPE_SCREAMING        |4                      | Detection result type: shout.|
|SOUND_EVENT_TYPE_SNEEZE           |3                      | Detection result type: sneeze.|
|SOUND_EVENT_TYPE_SNORING          |2                      | Detection result type: snore.|
|SOUND_EVENT_TYPE_STEAM_WHISTLE    |12                     | Detection result type: whistle.|
|SOUND_EVENT_TYPE_WATER            |7                      | Detection result type: babble of running water.|
|SOUND_DETECT_ON_FAILURE           |"soundDetectOnFailure" | Event key that represents [SOUND_DETECT_ON_FAILURE](#hmssounddetect-events).|
|SOUND_DETECT_ON_SUCCESS           |"soundDetectOnSuccess" | Event key that represents [SOUND_DETECT_ON_SUCCESS](#hmssounddetect-events).|

Call Example

```js
  this.eventEmitter.addListener(HMSSoundDetect.SOUND_DETECT_ON_SUCCESS, (event) => {
    console.log(event);
  });
```

#### HMSSoundDetect Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| SOUND_DETECT_ON_FAILURE         | Event emitted when an error occurs. |
| SOUND_DETECT_ON_SUCCESS         | Event emitted when sound detected. |

Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSSoundDetect);
    
    eventEmitter.addListener(HMSSoundDetect.SOUND_DETECT_ON_FAILURE, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    errorCode : 11203                    
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSSoundDetect.SOUND_DETECT_ON_SUCCESS, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    soundType : 1     // if -1 sound not detected
       * }
       * 
      */
    });
```

### **HMSTextToSpeech**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [createEngine(ttsConfiguration)](#hmstexttospeechcreateenginettsconfiguration) | Promise\<object> | Creates tts engine. Sets [TTS_ON_ERROR, TTS_ON_WARN, TTS_ON_RANGE_START, TTS_ON_AUDIO_AVAILABLE and TTS_ON_EVENT](#hmstexttospeech-events) events. |
| [speak(text,mode)](#hmstexttospeechspeaktextmode)  | Promise\<object> | Runs engine to speak.|
| [resume()](#hmstexttospeechresume)   | Promise\<object> | Resumes engine. |
| [stop()](#hmstexttospeechstop)   | Promise\<object> | Stops engine. |
| [pause()](#hmstexttospeechpause)   | Promise\<object> | Pause engine. |
| [shutdown()](#hmstexttospeechshutdown)   | Promise\<object> | Shutdown engine and release engine and config resources. |
| [getLanguages()](#hmstexttospeechgetlanguages)   | Promise\<object> | Obtains supported languages. |
| [getSpeaker(language)](#hmstexttospeechgetspeakerlanguage)   | Promise\<object> | Obtain the speaker of a specified language. |
| [getSpeakers()](#hmstexttospeechgetspeakers)   | Promise\<object> | Obtain the all speakers. |
| [isLanguageAvailable(language)](#hmstexttospeechislanguageavailablelanguage)   | Promise\<object> | Obtains if given language available. |
| [updateConfig(ttsConfiguration)](#hmstexttospeechupdateconfigttsconfiguration)   | Promise\<object> | Updates configuration created before. If no configuration created before, creates a new one. |

#### Public Methods

##### HMSTextToSpeech.createEngine(ttsConfiguration)

Creates tts engine. Sets [TTS_ON_ERROR, TTS_ON_WARN, TTS_ON_RANGE_START, TTS_ON_AUDIO_AVAILABLE and TTS_ON_EVENT](#hmstexttospeech-events) events.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| ttsConfiguration  | [TtsConfiguration](#hmstexttospeech-data-types) | Configures engine. |


Call Example

```js
  async createEngine() {
    try {
      var result = await HMSTextToSpeech.createEngine(
        {
          "volume": 1.0,
          "speed": 1.0,
          "language": HMSTextToSpeech.TTS_EN_US,
          "person": HMSTextToSpeech.TTS_SPEAKER_FEMALE_EN,
          "synthesizeMode": HMSTextToSpeech.TTS_ONLINE_MODE
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

##### HMSTextToSpeech.speak(text,mode)

Runs engine to speak.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| text       | string | Text to be vocalize. |
| mode       | number | Engine mode. |

Call Example

```js
  async speak() {
    try {
      var result = await HMSTextToSpeech.speak(
          "",
          HMSTextToSpeech.QUEUE_FLUSH
        );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0,
  message : "Success",
  result : ""        //ID of the audio synthesis task.
}
```

##### HMSTextToSpeech.resume()

Resumes engine.

Call Example

```js
  async resume() {
    try {
      var result = await HMSTextToSpeech.resume();
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

##### HMSTextToSpeech.stop()

Stops engine.

Call Example

```js
  async stop() {
    try {
      var result = await HMSTextToSpeech.stop();
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

##### HMSTextToSpeech.shutdown()

Shutdown engine and release engine and config resources.

Call Example

```js
  async shutdown() {
    try {
      var result = await HMSTextToSpeech.shutdown();
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

##### HMSTextToSpeech.pause()

Pause engine.

Call Example

```js
  async pause() {
    try {
      var result = await HMSTextToSpeech.pause();
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

##### HMSTextToSpeech.getLanguages()

Obtains supported languages.

Call Example

```js
  async getLanguages() {
    try {
      var result = await HMSTextToSpeech.getLanguages();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : ["en", ...]  
}
```

##### HMSTextToSpeech.getSpeaker(language)

Obtain the speaker of a specified language.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| language   | string | Language code. |

Call Example

```js
  async getSpeaker() {
    try {
      var result = await HMSTextToSpeech.getSpeaker(HMSTextToSpeech.TTS_EN_US);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : ""           // language code
}
```

##### HMSTextToSpeech.getSpeakers()

Obtain the all speakers.

Call Example

```js
  async getSpeakers() {
    try {
      var result = await HMSTextToSpeech.getSpeakers();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      language : "",
      name : ""
    },
    ...
  ]
}
```

##### HMSTextToSpeech.isLanguageAvailable(language)

Obtains if given language available.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| language   | string | Language code. |

Call Example

```js
  async isLanguageAvailable() {
    try {
      var result = await HMSTextToSpeech.isLanguageAvailable(HMSTextToSpeech.TTS_EN_US);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : 0          // HMSTextToSpeech.LANGUAGE_AVAILABLE
}
```

##### HMSTextToSpeech.updateConfig(ttsConfiguration)

Updates configuration created before. If no configuration created before, creates a new one.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| ttsConfiguration  | [TtsConfiguration](#hmstexttospeech-data-types) | Configures engine. |

Call Example

```js
  async updateConfig() {
    try {
      var result = await HMSTextToSpeech.updateConfig(
        {
          "volume": 1.0,
          "speed": 1.0,
          "language": HMSTextToSpeech.TTS_EN_US,
          "person": HMSTextToSpeech.TTS_SPEAKER_MALE_EN,
          "synthesizeMode": HMSTextToSpeech.TTS_ONLINE_MODE
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

#### HMSTextToSpeech Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|TTS_OFFLINE_MODE                        |"offline"             | Offline mode.|
|TTS_ONLINE_MODE                         |"online"              | Online mode.|
|EXTERNAL_PLAYBACK                       |2                     | Your account is in arrears or the free quota has been used up.|
|OPEN_STREAM                             |4                     | Service unavailable.|
|QUEUE_APPEND                            |0                     | Language code.|
|QUEUE_FLUSH                             |1                     | Language code.|
|ERR_AUDIO_PLAYER_FAILED                 |11305                 | Audio playback error.|
|ERR_AUTHORIZE_FAILED                    |11306                 | Authentication failed.|
|ERR_ILLEGAL_PARAMETER                   |11301                 | Insufficient balance.|
|ERR_INSUFFICIENT_BALANCE                |11303                 | Translator language code.|
|ERR_INTERNAL                            |11398                 | Internal error.|
|ERR_NET_CONNECT_FAILED                  |11302                 | Abnormal network connection.|
|ERR_SPEECH_SYNTHESIS_FAILED             |11304                 | Audio synthesis failed.|
|ERR_UNKNOWN                             |11399                 | Unknown error. |
|WARN_INSUFFICIENT_BANDWIDTH             |113001                |Event key that represents |
|EVENT_PLAY_PAUSE                        |3                     |ID of the playback pause event.|
|EVENT_PLAY_RESUME                       |2                     |ID of the playback resume event. |
|EVENT_PLAY_START                        |1                     |ID of the playback start event.|
|EVENT_PLAY_STOP                         |4                     |ID of the playback stop event.|
|EVENT_PLAY_STOP_INTERRUPTED             |"interrupted"         |Indicates whether playback is interrupted abnormally.|
|EVENT_SYNTHESIS_START                   |5                     |ID of the audio synthesis start event.|
|EVENT_SYNTHESIS_COMPLETE                |7                     |ID of the audio synthesis completion event (that is, the synthesized audio data is completely processed).|
|EVENT_SYNTHESIS_END                     |6                     |ID of the audio synthesis end event.|
|EVENT_SYNTHESIS_INTERRUPTED             |"interrupted"         |Indicates whether playback is interrupted.|
|TTS_EN_US                               |"en-US"               |English (US).|
|TTS_LAN_DE_DE                           |"de-DE"               |German (Germany).|
|TTS_LAN_ES_ES                           |"es-ES"               |Spanish (Spain).|
|TTS_LAN_FR_FR                           |"fr-FR"               |French (France).|
|TTS_LAN_IT_IT                           |"it-IT"               |Italian (Italy).|
|TTS_SPEAKER_FEMALE_EN                   |"Female-en"           |English female voice.|
|TTS_SPEAKER_FEMALE_ZH                   |"Female-zh"           |Chinese female voice.|
|TTS_SPEAKER_MALE_EN                     |"Male-en"             |English male voice.|
|TTS_SPEAKER_MALE_ZH                     |"Male-zh"             |Chinese male voice.|
|TTS_ZH_HANS                             |"zh-Hans"             |Simplified Chinese.|
|TTS_SPEAKER_FEMALE_DE                   |"de-DE-st-1"          |German standard female voice.|
|TTS_SPEAKER_FEMALE_ES                   |"es-ES-st-1"          |Spanish standard female voice.|
|TTS_SPEAKER_FEMALE_FR                   |"fr-FR-st-1"          |French standard female voice.|
|TTS_SPEAKER_FEMALE_IT                   |"it-IT-st-1"          |Italian standard female voice.|
|TTS_SPEAKER_OFFLINE_DE_DE_FEMALE_BEE    |"de-DE-st-bee-1"      |German female voice (bee).|
|TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE    |"en-US-st-bee-1"      |English female voice (bee).|
|TTS_SPEAKER_OFFLINE_ES_ES_FEMALE_BEE    |"es-ES-st-bee-1"      |Spanish female voice (bee).|
|TTS_SPEAKER_OFFLINE_FR_FR_FEMALE_BEE    |"fr-FR-st-bee-1"      |French female voice (bee).|
|TTS_SPEAKER_OFFLINE_IT_IT_FEMALE_BEE    |"it-IT-st-bee-1"      |Italian standard female voice.|
|TTS_SPEAKER_OFFLINE_ZH_HANS_MALE_EAGLE  |"zh-Hans-st-eagle-2"  |Chinese male voice (eagle).|
|TTS_SPEAKER_OFFLINE_ZH_HANS_FEMALE_EAGLE|"zh-Hans-st-eagle-1"  |Chinese female voice (eagle).|
|TTS_SPEAKER_OFFLINE_EN_US_MALE_EAGLE    |"en-US-st-eagle-2"    |English male voice (eagle).|
|TTS_SPEAKER_OFFLINE_EN_US_FEMALE_EAGLE  |"en-US-st-eagle-1"    |English female voice (eagle).|
|LANGUAGE_AVAILABLE                      |0                     |The language is supported.|
|LANGUAGE_NOT_SUPPORT                    |1                     |The language is not supported.|
|LANGUAGE_UPDATING                       |2                     |The language list is being updated.|
|CHANNEL_OUT_MONO                        |4                     |Mono.|
|FORMAT_PCM_8BIT                         |1                     |PCM stream, with 8-bit depth.|
|FORMAT_PCM_16BIT                        |2                     |PCM stream, with 16-bit depth.|
|SAMPLE_RATE_16K                         |16000                 |16 kHz audio sampling rate.|
|TTS_ON_AUDIO_AVAILABLE                  |"ttsOnAudioAvailable" |Event key that represents [TTS_ON_AUDIO_AVAILABLE](#hmstexttospeech-events)|
|TTS_ON_EVENT                            |"ttsOnEvent"          |Event key that represents [TTS_ON_EVENT](#hmstexttospeech-events)|
|TTS_ON_RANGE_START                      |"ttsOnRangeStart"     |Event key that represents [TTS_ON_RANGE_START](#hmstexttospeech-events)|
|TTS_ON_WARN                             |"ttsOnWarn"           |Event key that represents [TTS_ON_WARN](#hmstexttospeech-events)|
|TTS_ON_ERROR                            |"ttsOnError"          |Event key that represents [TTS_ON_ERROR](#hmstexttospeech-events)|

Call Example

```js
  this.eventEmitter.addListener(HMSTextToSpeech.TTS_ON_ERROR, (event) => {
    console.log(event);
  });
```

#### HMSTextToSpeech Data Types
 
##### RttConfiguration

An object that represents audio transcription settings on the cloud.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| language                          | string    | Language code. |
| enablePunctuation                 | boolean   | Enables or disables punctuation.|
| enableSentenceTimeOffset          | boolean   | Enables or disables sentence time offset.|
| enableWordTimeOffset              | boolean   | Enables or disables word time offset. |

#### HMSTextToSpeech Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| TTS_ON_AUDIO_AVAILABLE          | Event emitted when the synthesized audio data available. |
| TTS_ON_EVENT                    | Event emitted when starting, pausing, resuming, and stopping the playback after speech is generated. |
| TTS_ON_RANGE_START              | Event emitted when playback start event of the split text. |
| TTS_ON_WARN                     | Event emitted when alarm notifications.  |
| TTS_ON_ERROR                    | Event emitted when an error occurs in an audio synthesis task. |


Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSTextToSpeech);
    
    eventEmitter.addListener(HMSTextToSpeech.TTS_ON_AUDIO_AVAILABLE, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *   taskId : "",      // ID of an audio synthesis task corresponding to an audio clip.
       *   audioData : []    // bytes array audio data.
       *   channelInfo : 4,  // channel of audio data in an audio segment. CHANNEL_OUT_MONO
       *   sampleRateInHz : 16000 // sampling rate of audio data in an audio segment. SAMPLE_RATE_16K
       *   audioFormat : 1,  // format of audio data in an audio segment FORMAT_PCM_16BIT, FORMAT_PCM_8BIT
       *   offset : 1,       // Offset in the queue. One audio synthesis task corresponds to an audio synthesis queue.
       *   first : 0,        // start position
       *   second : 100      // end position
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSTextToSpeech.TTS_ON_EVENT, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    taskId : "",
       *    eventId : 1 // for example EVENT_PLAY_START 
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSTextToSpeech.TTS_ON_RANGE_START, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    taskId : "",
       *    start : "", // start position
       *    end : ""    // end position
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSTextToSpeech.TTS_ON_WARN, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    taskId : "",
       *    warningMessage : "",
       *    warningId : 113001   //WARN_INSUFFICIENT_BANDWIDTH
       * }
       * 
      */
    });

    eventEmitter.addListener(HMSTextToSpeech.TTS_ON_ERROR, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    taskId : "",
       *    errorMessage : "",
       *    errorId : 11305   // ERR_AUDIO_PLAYER_FAILED
       * }
       * 
      */
    });
```

### **HMSModelDownload**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [downloadModel(modelConfig,strategyConfig)](#hmsmodeldownloaddownloadmodelmodelconfigstrategyconfig) | Promise\<object> | Downloads a specified model from the cloud based on a specified download condition. |
| [deleteModel(modelConfig)](#hmsmodeldownloaddeletemodelmodelconfig)  | Promise\<object> | Deletes a specified model from the device.|
| [isModelExist(modelConfig)](#hmsmodeldownloadismodelexistmodelconfig) | Promise\<object> | Checks whether a specified model has been downloaded to the device. |
| [getModels(modelTag)](#hmsmodeldownloadgetmodelsmodeltag) | Promise\<object> | Queries the model set that has been downloaded to a local path. |
| [getRecentModelFile(modelConfig)](#hmsmodeldownloadgetrecentmodelfilemodelconfig)   | Promise\<object> | Queries the storage path of a specified model on the device. |

#### Public Methods

##### HMSModelDownload.downloadModel(modelConfig,strategyConfig)

Downloads a specified model from the cloud based on a specified download condition. If the download condition is not met, a failure message will be returned. If the model does not exist locally or a new version is available on the cloud, the model will be downloaded. Otherwise, the call of this method will stop immediately.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| modelConfig     | [ModelConfig](#hmsmodeldownload-data-types) | Specifies model to be downloaded. |
| strategyConfig  | [StrategyConfiguration](#hmsmodeldownload-data-types) | Defines download strategy. |


Call Example

```js
  async downloadModel() {
    try {
      var result = await HMSModelDownload.downloadModel(
        {
          tts: { speakerName: HMSTextToSpeech.TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE }
          // other options
          // translate : { languageCode : HMSTranslate.ENGLISH }
          // customRemoteModel : "modelName"
        },
        {
          needWifi : true,
          needCharging : false,
          needDeviceIdle : true,
          region : HMSModelDownload.REGION_DR_EUROPE
        },
      )
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSModelDownload.deleteModel(modelConfig)

Deletes a specified model from the device.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| modelConfig     | [ModelConfiguration](#hmsmodeldownload-data-types) | Specifies model to be deleted. |

Call Example

```js
  async deleteModel() {
    try {
      var result = await HMSModelDownload.deleteModel(
        {
          tts: { speakerName: HMSTextToSpeech.TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE }
          // other options
          // translate : { languageCode : HMSTranslate.ENGLISH }
          // customRemoteModel : "modelName"
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
{ status : 0, message : "Success"}
```

##### HMSModelDownload.isModelExist(modelConfig)

Checks whether a specified model has been downloaded to the device.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| modelConfig     | [ModelConfiguration](#hmsmodeldownload-data-types) | Specifies model to be checked. |

Call Example

```js
  async isModelExist() {
    try {
      var result = await HMSModelDownload.isModelExist(
        {
          tts: { speakerName: HMSTextToSpeech.TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE }
          // other options
          // translate : { languageCode : HMSTranslate.ENGLISH }
          // customRemoteModel : "modelName"
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
{ status : 0, message : "Success", result : true}
```

##### HMSModelDownload.getModels(modelTag)

Queries the model set that has been downloaded to a local path.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| modelTag   | number | 1 : TTS, 2: TRANSLATE, 3: CUSTOM |

Call Example

```js
  async getModels() {
    try {
      var result = await HMSModelDownload.getModels(1);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : ["modelName", ...] }
```

##### HMSModelDownload.getRecentModelFile(modelConfig)

Queries the storage path of a specified model on the device.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| modelConfig| [ModelConfiguration](#hmsmodeldownload-data-types) | Specifies model. |

Call Example

```js
  async getRecentModelFile() {
    try {
      var result = await HMSModelDownload.getRecentModelFile(
        {
          tts: { speakerName: HMSTextToSpeech.TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE }
          // other options
          // translate : { languageCode : HMSTranslate.ENGLISH }
          // customRemoteModel : "modelName"
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
{ status : 0, message : "Success", result : "" // file path }
```

#### HMSModelDownload Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|REGION_DR_AFILA          |1003                        | Asia, Africa, and Latin America.|
|REGION_DR_CHINA          |1002                        | China.|
|REGION_DR_EUROPE         |1004                        | Europe.|
|REGION_DR_RUSSIA         |1005                        | Russia.|
|DOWNLOAD_ON_PROCESS      |"modelDownloadOnProcess"    | Event key that represents [DOWNLOAD_ON_PROCESS](#hmsmodeldownload-events) event.|
|MODEL_TTS_TAG            |1                           | HMSTextToSpeech model tag.|
|MODEL_TRANSLATE_TAG      |2                           | HMSTranslate model tag.|
|MODEL_CUSTOM_TAG         |3                           | HMSCustom model tag.|

Call Example

```js
 async downloadModel() {
    try {
      var result = await HMSModelDownload.downloadModel(
        {
          tts: { 
            speakerName: HMSTextToSpeech.TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE 
          }
        },
        {
          needWifi : true,
          needCharging : false,
          needDeviceIdle : true,
          region : HMSModelDownload.REGION_DR_EUROPE
        },
      );
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSModelDownload Data Types
 
##### ModelConfiguration

An object that defines the models. One of the three configuration is required to create this object. Multiple configurations in this object is not allowed. First matching configuration will be considered.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| translate                         | [Translate Model Configuration](#translate-model-configuration) | HMSTranslate model configuration. |
| tts                               | [Tts Model Configuration](#tts-model-configuration) | HMSTextToSpeech model configuration. |
| customRemoteModel                 | string | Model name. HMSCustomModel model configuration. |


###### Translate Model Configuration

An object that represents HMSTranslate model configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| languageCode                      | string    | Language code. |

###### Tts Model Configuration

An object that represents HMSTextToSpeech model configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| speakerName                       | string    | Speaker name. |

###### StrategyConfiguration

An object that represents model download strategy configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| needWifi                       | boolean    | Device uses wifi if true. |
| needCharging                   | boolean    | Device waits to be in charging state if true. |
| deviceIdle                   | boolean    | Device waits to be idle if true. |
| region                   | number    | Download region. |


#### HMSModelDownload Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| DOWNLOAD_ON_PROCESS             | Event emitted when download starts. |


Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSModelDownload);
    
    eventEmitter.addListener(HMSTextToSpeech.DOWNLOAD_ON_PROCESS, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *   alreadyDownloadLength : "",    // already downloaded
       *   totalLength : ""               // total to be downloaded
       * }
       * 
      */
    });
```

### **HMSCustomModel**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [exec(isRemote,modelInputOutputSettings,modelInputConfiguration,modelConfig)](#hmscustommodelexecisremotemodelinputoutputsettingsmodelinputconfigurationmodelconfig) | Promise\<object> | Performs inference using input and output configurations and content. |
| [close(isRemote,modelConfig)](#hmscustommodelcloseisremotemodelconfig)  | Promise\<object> | Stops an inference task to release resources.|
| [getOutputIndex(isRemote,channelName,modelConfig)](#hmscustommodelgetoutputindexisremotechannelnamemodelconfig) | Promise\<object> | Obtains the channel index based on the output channel name. |

#### Public Methods

##### HMSCustomModel.exec(isRemote,modelInputOutputSettings,modelInputConfiguration,modelConfig)

Performs inference using input and output configurations and content.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote                  | boolean | Remote or local model. |
| modelInputOutputSettings  | [ModelInputOutputSettings](#hmscustommodel-data-types) | Input output setting. |
| modelInputConfiguration   | [ModelInputConfiguration](#hmscustommodel-data-types) | Source data to be inferred. |
| modelConfig               | [ModelConfiguration](#hmscustommodel-data-types) | Model information container. |

Call Example

```js
  async exec() {
    try {
      var result = await HMSCustomModel.exec(
        true,
        {
          inputFormat : {
            width : 100,
            height : 100
          },
          outputFormat : {
            outputSize : 100
          }
        },
        {
          uri : "",
          height : 224,
          width : 224
        },
        {
          // if isRemote is true
          modelName : ""
          // if isRemote is false
          // assetPath : "",
          // localFullPath : "",
          // modelName : ""
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
{ status : 0, message : "Success", result : [10.0,20.0, ...] }
```

##### HMSCustomModel.close(isRemote,modelConfig)

Stops an inference task to release resources.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote            | boolean | Remote or local model. |
| modelConfig         | [ModelConfiguration](#hmscustommodel-data-types) | Model information container. |


Call Example

```js
  async close() {
    try {
      var result = await HMSCustomModel.close(
        false,
        {
          // if isRemote is true
          //modelName : ""
          // if isRemote is false
          assetPath : "",
          localFullPath : "",
          modelName : ""
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

##### HMSCustomModel.getOutputIndex(isRemote,channelName,modelConfig)

Obtains the channel index based on the output channel name.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote            | boolean | Remote or local model. |
| channelName         | string | Channel name. |
| modelConfig         | [ModelConfiguration](#hmscustommodel-data-types) | Model information container. |


Call Example

```js
  async getOutputIndex() {
    try {
      var result = await HMSCustomModel.getOutputIndex(
        false,
        "",     // channel name
        {
          // if isRemote is true
          //modelName : ""
          // if isRemote is false
          assetPath : "",
          localFullPath : "",
          modelName : ""
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
{ status : 0, message : "Success", result : 0 }
```

#### HMSCustomModel Data Types
 
##### ModelInputOutputSettings

An object that represents model input output setting.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| inputFormat                       | [InputFormat](#hmscustommodel-data-types)    | Input format. |
| outputFormat                      | [OutputFormat](#hmscustommodel-data-types)   | Output format.|

##### ModelInputConfiguration

An object that represents model input configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| uri                               | string    | Image uri. |
| height                            | number    | Height.|
| width                             | number    | Width.|

##### ModelConfiguration

An object that represents model model configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| modelName                         | string    | Model name. |
| assetPath                         | string    | Asset path only used in local operations.|
| localFullPath                     | string    | Full path only used in local operations.|

##### InputFormat

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| width                             | number    | Width. |
| height                            | number    | Height. |

##### OutputFormat

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| outputSize                        | number    | Output size. |

### **HMSTextEmbedding**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [analyzeSentenceVector(sentence,language)](#hmstextembeddinganalyzesentencevectorsentencelanguage) | Promise\<object> | Queries the sentence vector asynchronously.   |
| [analyzeSentencesSimilarity(sentenceFirst,sentenceSecond,language)](#hmstextembeddinganalyzesentencessimilaritysentencefirst,sentencesecondlanguage)   | Promise\<object> | Asynchronously queries the similarity between two sentences. The similarity range is [–1,1].  |
| [analyzeSimilarWords(word,similarNum,language)](#hmstextembeddinganalyzesimilarwordswordsimilarnumlanguage)  | Promise\<object> | Asynchronously queries a specified number of similar words.  |
| [analyzeWordVector(word,language)](#hmstextembeddinganalyzewordvectorwordlanguage)    | Promise\<object> | Queries the word vector asynchronously. |
| [analyzeWordsSimilarity(wordFirst,wordSecond,language)](#hmstextembeddinganalyzewordssimilaritywordfirstwordsecondlanguage) | Promise\<object> | Asynchronously queries the similarity between two words. The similarity range is [–1,1]. |
| [analyzeWordVectorBatch(words,language)](#hmstextembeddinganalyzewordvectorbatchwordslanguage) | Promise\<object> | Asynchronously queries word vectors in batches. (The number of words ranges from 1 to 500.) |
| [getVocabularyVersion(language)](#hmstextembeddinggetvocabularyversionlanguage) | Promise\<object> | Asynchronously queries dictionary version information. |

#### Public Methods

##### HMSTextEmbedding.analyzeSentenceVector(sentence,language)

Queries the sentence vector asynchronously. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| sentence   | string | Sentence to be analyzed.             |
| language   | string | Language code.                       |

Call Example

```js
  async analyzeSentenceVector() {
    try {
      var result = await HMSTextEmbedding.analyzeSentenceVector("Hello world", HMSTextEmbedding.LANGUAGE_EN);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : [1.0, 0.43, ...] }
```

##### HMSTextEmbedding.analyzeSentencesSimilarity(sentenceFirst,sentenceSecond,language)

Asynchronously queries the similarity between two sentences. The similarity range is [–1,1].

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| sentenceFirst    | string | First sentence.                |
| sentenceSecond   | string | Second sentence.               |
| language         | string | Language code.                 |

Call Example

```js
 async analyzeSentencesSimilarity() {
    try {
      var result = await HMSTextEmbedding.analyzeSentencesSimilarity("Hello World","Hello World",HMSTextEmbedding.LANGUAGE_EN);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : 1.0 }
```

##### HMSTextEmbedding.analyzeSimilarWords(word,similarNum,language)

Asynchronously queries a specified number of similar words.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| word       | string | Word to be analyzed.                 |
| similarNum | number | Similarity number.                   |
| language   | string | Language code.                 |

Call Example

```js
 async analyzeSimilarWords() {
    try {
      var result = await HMSTextEmbedding.analyzeSimilarWords("hello", 10, HMSTextEmbedding.LANGUAGE_EN)
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", results : ["hi", ...] }
```

##### HMSTextEmbedding.analyzeWordsSimilarity(wordFirst,wordSecond,language)

Asynchronously queries the similarity between two words. The similarity range is [–1,1].

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| wordFirst    | string | First word.                |
| wordSecond   | string | Second word.               |
| language     | string | Language code.                 |

Call Example

```js
  async analyzeWordsSimilarity() {
    try {
      var result = await HMSTextEmbedding.analyzeWordsSimilarity("hello", "hello", HMSTextEmbedding.LANGUAGE_EN);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : 1.0 }
```

##### HMSTextEmbedding.analyzeWordVector(word,language)

Queries the word vector asynchronously. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| word       | string | Word to be analyzed.             |
| language   | string | Language code.                       |

Call Example

```js
  async analyzeWordVector() {
    try {
      var result = await HMSTextEmbedding.analyzeWordVector("Hello", HMSTextEmbedding.LANGUAGE_EN);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : [1.0, 0.43, ...] }
```

##### HMSTextEmbedding.analyzeWordVectorBatch(words,language)

Queries the word vector asynchronously. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| words      | string[] | Word batch to be analyzed.             |
| language   | string | Language code.                       |

Call Example

```js
  async analyzeWordVectorBatch() {
    try {
      var result = await HMSTextEmbedding.analyzeWordVectorBatch(["Hello","Huawei"], HMSTextEmbedding.LANGUAGE_EN);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success", 
  result : {
    hello : [0.34,0.23,..],
    Huawei : [0.74,0.13,..],
    ...
  } 
}
```

##### HMSTextEmbedding.getVocabularyVersion(language)

Asynchronously queries dictionary version information.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| language     | string | Language code.                 |

Call Example

```js
  async getVocabularyVersion() {
    try {
      var result = await HMSTextEmbedding.getVocabularyVersion(HMSTextEmbedding.LANGUAGE_EN);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0,
  message : "Success",
  result : {
    dictionaryDimension : "100",
    dictionarySize : "1425110",
    versionNo : "1.0.2"
  } 
}
```

#### HMSTextEmbedding Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|LANGUAGE_EN                |"en"                      |Language code. |
|LANGUAGE_ZH                |"zh"                      |Language code. |
|INNER                      |12101                     |An internal error occurred.|
|ERR_AUTH_FAILED            |12102                     |Service authentication failed.|
|ERR_PARAM_ILLEGAL          |12103                     |The input parameter is invalid. For example, the input language is not supported.|
|ERR_ANALYZE_FAILED         |12104                     |The word vector or sentence vector is not found.|
|ERR_AUTH_TOKEN_INVALID     |12105                     |The token has expired or is invalid.|
|ERR_NET_UNAVAILABLE        |12918                     |The network is unavailable.|
|ERR_SERVICE_IS_UNAVAILABLE |12919                     |Other error.|

Call Example

```js
  async getVocabularyVersion() {
    try {
      var result = await HMSTextEmbedding.getVocabularyVersion(HMSTextEmbedding.LANGUAGE_EN);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

### **HMSFaceRecognition**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [asyncAnalyzeFrame(is3D,isStop,frameConfiguration,analyzerConfiguration)](#hmsfacerecognitionasyncanalyzeframeis3disstopframeconfigurationanalyzerconfiguration) | Promise\<object> |Detects faces in a supplied image in asynchronous mode.|
| [analyzeFrame(is3D,isStop,frameConfiguration,analyzerConfiguration)](#hmsfacerecognitionasyncanalyzeframeis3disstopframeconfigurationanalyzerconfiguration) | Promise\<object> |Detects faces in a supplied image in synchronous mode.|

#### Public Methods

##### HMSFaceRecognition.asyncAnalyzeFrame(is3D,isStop,frameConfiguration,analyzerConfiguration)

Detects faces in a supplied image in asynchronous mode.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| is3D                  | boolean | Uses 3d face analyzer if true otherwise 2d face analyzer.|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion. | 
| analyzerConfiguration | [AnalyzerConfiguration](#hmsfacerecognition-data-types) | 2d or 3d face analyzer configuration according to is3D parameter.|

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSFaceRecognition.asyncAnalyzeFrame(
        true,
        true,
        { filePath : "" },
        { 
          performanceType: HMSFaceRecognition.TYPE_SPEED,
          isTracingAllowed: false,
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
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      allPoints : [
        {
          X : 1.0,
          Y : 1.0,
          Z : 1.0
        },
        ...
      ],
      faceEulerY : 1.0,
      faceEulerX : 1.0,
      faceEulerZ : 1.0
    },
    ...
  ]
}
```

##### HMSFaceRecognition.analyzeFrame(is3D,isStop,frameConfiguration,analyzerConfiguration)

Detects faces in a supplied image in synchronous mode.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| is3D                  | boolean | Uses 3d face analyzer if true.|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| analyzerConfiguration | [AnalyzerConfiguration](#hmsfacerecognition-data-types) | 2d or 3d face analyzer configuration according to is3D parameter.|

Call Example

```js
  async analyzeFrame() {
    try {
      var result = await HMSFaceRecognition.analyzeFrame(
        false,
        true,
        { filePath : "" },
        { 
          featureType: HMSFaceRecognition.TYPE_FEATURES,
          shapeType: HMSFaceRecognition.TYPE_SHAPES,
          keyPointType: HMSFaceRecognition.TYPE_KEYPOINTS,
          performanceType: HMSFaceRecognition.TYPE_SPEED,
          tracingMode: HMSFaceRecognition.MODE_TRACING_ROBUST,
          minFaceProportion: 0.5,
          isPoseDisabled: false,
          isTracingAllowed: false,
          isMaxSizeFaceOnly: false
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
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      allPoints : [
        {
          X : 1.0,
          Y : 1.0,
          Z : 1.0
        },
        ...
      ],
      rotationAngleX : 1.0,
      rotationAngleY : 1.0,
      rotationAngleZ : 1.0,
      tracingIdentity : 123,
      height : 100,
      width : 100,
      border : {
        left : 1,
        right : 1,
        bottom : 1,
        top : 1
      }
      emotions : {
        angryProbability : 0.2,
        disgustProbability : 0.2,
        fearProbability : 0.2,
        neutralProbability : 0.2,
        sadProbability : 0.2,
        smilingProbability : 0.2,
        surpriseProbability : 0.2
      },
      coordinatePoints : {
        length : 10.0,
        x : 1.0,
        y : 1.0
      },
      features : {
        age : 24,
        hatProbability : 0.2,
        leftEyeOpenProbability : 0.3,
        rightEyeOpenProbability : 0.4,
        moustacheProbability : 0.4,
        sexProbability : 0.2,
        sunGlassProbability : 0.3
      },
      faceKeyPointList : [
        {
          points : {
            X : 1.0,
            Y : 1.0,
            Z : 1.0
          },
          type : 4
        },
        ...
      ],
      faceShapeList : [
        {
           points : {
            X : 1.0,
            Y : 1.0,
            Z : 1.0
          },
          faceShapeType : 3
        },
        ...
      ]
    },
    ...
  ]
}
```

#### HMSFaceRecognition Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|TYPE_ALL                     |0                       | All contours of a face.|
|TYPE_FACE                    |1                       | Overall face contour.|
|TYPE_LEFT_EYE_SHAPE          |2                       | Contour of the left eye.|
|TYPE_RIGHT_EYE_SHAPE         |3                       | Contour of the right eye.|
|TYPE_BOTTOM_OF_LEFT_EYEBROW  |4                       | Bottom contour of the left eyebrow.|
|TYPE_BOTTOM_OF_RIGHT_EYEBROW |5                       | Bottom contour of the right eyebrow.|
|TYPE_TOP_OF_LEFT_EYEBROW     |6                       | Top contour of the left eyebrow.|
|TYPE_TOP_OF_RIGHT_EYEBROW    |7                       | Top contour of the right eyebrow.|
|TYPE_BOTTOM_OF_LOWER_LIP     |8                       | Bottom contour of the lower lip.|
|TYPE_TOP_OF_LOWER_LIP        |9                       | Top contour of the lower lip.|
|TYPE_TOP_OF_UPPER_LIP        |10                      | Top contour of the upper lip.|
|TYPE_BOTTOM_OF_UPPER_LIP     |11                      | Bottom contour of the upper lip.|
|TYPE_BOTTOM_OF_NOSE          |12                      | Bottom contour of the nose.|
|TYPE_BRIDGE_OF_NOSE          |13                      | Contour of the nose bridge.|
|TYPE_BOTTOM_OF_MOUTH         |1                       | Center of the lower lip.|
|TYPE_LEFT_CHEEK              |2                       | Center of the left cheek.|
|TYPE_LEFT_EAR                |3                       | Center of the left ear tragus.|
|TYPE_TIP_OF_LEFT_EAR         |4                       | Tip of the left ear.|
|TYPE_LEFT_EYE                |5                       | Center of the left eyeball.|
|TYPE_LEFT_SIDE_OF_MOUTH      |6                       | Left mouth corner point.|
|TYPE_TIP_OF_NOSE             |7                       | Center of the nasal tip.|
|TYPE_RIGHT_CHEEK             |8                       | Center of the right cheek.|
|TYPE_RIGHT_EAR               |9                       | Center of the right ear tragus.|
|TYPE_TIP_OF_RIGHT_EAR        |10                      | Tip of the right ear.|
|TYPE_RIGHT_EYE               |11                      | Center of the right eyeball.|
|TYPE_RIGHT_SIDE_OF_MOUTH     |12                      | Right mouth corner point.|
|MODE_TRACING_FAST            |1                       | Fast tracking mode. |
|MODE_TRACING_ROBUST          |2                       | Common tracking mode.|
|TYPE_FEATURES                |3                       | Detects all facial features and expressions.|
|TYPE_FEATURE_AGE             |256                     | Detects the age.|
|TYPE_FEATURE_BEARD           |32                      | Detects whether a person has a beard.|
|TYPE_FEATURE_EMOTION         |4                       | Detects facial expressions.|
|TYPE_FEATURE_EYEGLASS        |8                       | Detects whether a person wears glasses.|
|TYPE_FEATURE_GENDAR          |128                     | Detects the gender.|
|TYPE_FEATURE_HAT             |16                      | Detects whether a person wears a hat.|
|TYPE_FEATURE_OPEN_CLOSE_EYE  |64                      | Detects eye opening and eye closing.|
|TYPE_KEYPOINTS               |1                       | Detects key face points.|
|TYPE_PRECISION               |1                       | Precision preference mode.|
|TYPE_SHAPES                  |2                       | Detects facial contours.|
|TYPE_SPEED                   |2                       | Speed preference mode.|
|TYPE_UNSUPPORT_FEATURES      |2                       | Detects only basic data: including contours, key points, and three-dimensional rotation angles.|
|TYPE_UNSUPPORT_KEYPOINTS     |0                        | Does not detect key face points.|
|TYPE_UNSUPPORT_SHAPES        |3                        | Does not detect facial contours.|

Call Example

```js
  async analyzeFrame() {
    try {
      var result = await HMSFaceRecognition.analyzeFrame(
        false,
        true,
        { filePath : "image_uri" },
        { 
          featureType: HMSFaceRecognition.TYPE_FEATURES,
          shapeType: HMSFaceRecognition.TYPE_SHAPES,
          keyPointType: HMSFaceRecognition.TYPE_KEYPOINTS,
          performanceType: HMSFaceRecognition.TYPE_SPEED,
          tracingMode: HMSFaceRecognition.MODE_TRACING_ROBUST,
          minFaceProportion: 0.5,
          isPoseDisabled: false,
          isTracingAllowed: false,
          isMaxSizeFaceOnly: false
        }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSFaceRecognition Data Types
 
##### AnalyzerConfiguration - 3D
An object that represent face recognition analyzer configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| performanceType                   | number    | Performance type of analyzer. |
| isTracingAllowed                  | boolean   | Tracing is allowed or not in analyzer. |

##### AnalyzerConfiguration - 2D
An object that represent face recognition analyzer configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| performanceType                   | number    | Performance type of analyzer. |
| isTracingAllowed                  | boolean   | Tracing is allowed or not in analyzer. |
| featureType                       | number    | Feature type of analyzer. |
| keyPointType                      | number    | Keypoint type of analyzer. |
| shapeType                         | number    | Shape type of analyzer. |
| isPoseDisabled                    | boolean   | Pose disabled or enabled status. |
| tracingMode                       | number    | Tracing mode of analyzer. |
| minFaceProportion                 | number    | Minumum face proportion. |
| isMaxSizeFaceOnly                 | boolean   | Only maximum sized face detection. |


### **HMSHandKeypointDetection**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [asyncAnalyzeFrame(isStop,frameConfiguration,analyzerSetting)](#hmshandkeypointdetectionasyncanalyzeframeisstopframeconfigurationanalyzersetting) | Promise\<object> |Detects hand key points in an input image in asynchronous mode.|
| [analyzeFrame(isStop,frameConfiguration,analyzerConfiguration)](#hmshandkeypointdetectionanalyzeframeisstopframeconfigurationanalyzerconfiguration) | Promise\<object> | Detects hand key points in an input image in synchronous mode.|

#### Public Methods

##### HMSHandKeypointDetection.asyncAnalyzeFrame(isStop,frameConfiguration,analyzerSetting)

Detects hand key points in an input image in asynchronous mode.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| analyzerSetting | [AnalyzerConfiguration](#hmshandkeypointdetection-data-types) | Analyzer configuration object.|

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSHandKeypointDetection.asyncAnalyzeFrame(
        true,
        { filePath : "" },
        { 
            sceneType: HMSHandKeypointDetection.TYPE_KEYPOINT_ONLY,
            maxHandResults: HMSHandKeypointDetection.MAX_HANDS_NUM
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
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      handKeyPoints : [
        {
          type : 13,
          pointX : 1.0,
          pointY : 1.0,
          score : 1.0
        },
        ...
      ],
      score : 1.0,
      border : {
        left : 1,
        top : 1,
        right : 1,
        down : 1
      }
    },
    ...
  ]
}
```

##### HMSHandKeypointDetection.analyzeFrame(isStop,frameConfiguration,analyzerConfiguration)

Detects hand key points in an input image in synchronous mode.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| analyzerSetting | [AnalyzerConfiguration](#hmshandkeypointdetection-data-types) | Analyzer configuration object.|

Call Example

```js
  async analyzeFrame() {
    try {
      var result = await HMSHandKeypointDetection.analyzeFrame(
        true,
        { filePath : "" },
        { 
          sceneType: HMSHandKeypointDetection.TYPE_KEYPOINT_ONLY,
          maxHandResults: HMSHandKeypointDetection.MAX_HANDS_NUM
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
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      handKeyPoints : [
        {
          type : 13,
          pointX : 1.0,
          pointY : 1.0,
          score : 1.0
        },
        ...
      ],
      score : 1.0,
      border : {
        left : 1,
        top : 1,
        right : 1,
        down : 1
      }
    },
    ...
  ]
}
```

#### HMSHandKeypointDetection Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|TYPE_FOREFINGER_FIRST     |5                       | First joint of the index finger near the wrist.|
|TYPE_FOREFINGER_FOURTH    |8                       | Index finger tip.|
|TYPE_FOREFINGER_SECOND    |6                       | Second joint of the index finger near the wrist.|
|TYPE_FOREFINGER_THIRD     |7                       | Third joint of the index finger near the wrist.|
|TYPE_LITTLE_FINGER_FIRST  |17                      | First joint of the little finger near the wrist.|
|TYPE_LITTLE_FINGER_FOURTH |20                      | Little finger tip.|
|TYPE_LITTLE_FINGER_SECOND |18                      | Second joint of the little finger near the wrist.|
|TYPE_LITTLE_FINGER_THIRD  |19                      | Third joint of the little finger near the wrist.|
|TYPE_MIDDLE_FINGER_FIRST  |9                       | First joint of the middle finger near the wrist.|
|TYPE_MIDDLE_FINGER_SECOND |10                      | Second joint of the middle finger near the wrist.|
|TYPE_MIDDLE_FINGER_THIRD  |11                      | Third joint of the middle finger near the wrist.|
|TYPE_MIDDLE_FINGER_FOURTH |12                      | Middle finger tip.|
|TYPE_RING_FINGER_FIRST    |13                      | First joint of the ring finger near the wrist.|
|TYPE_RING_FINGER_FOURTH   |16                      | Ring finger tip.|
|TYPE_RING_FINGER_SECOND   |14                      | Second joint of the ring finger near the wrist.|
|TYPE_RING_FINGER_THIRD    |15                      | Third joint of the ring finger near the wrist.|
|TYPE_THUMB_FIRST          |1                       | First joint of the thumb near the wrist.|
|TYPE_THUMB_FOURTH         |4                       | Thumb tip.|
|TYPE_THUMB_SECOND         |2                       | Second joint of the thumb near the wrist.|
|TYPE_THUMB_THIRD          |3                       | Third joint of the thumb near the wrist.|
|TYPE_WRIST                |0                       | Wrist joint.|
|TYPE_ALL                  |0                       | TYPE_ALL indicates that all results are returned.|
|TYPE_KEYPOINT_ONLY        |1                       | TYPE_KEYPOINT_ONLY indicates that only hand keypoint information is returned.|
|TYPE_RECT_ONLY            |2                       | TYPE_RECT_ONLY indicates that only palm information is returned.|
|MAX_HANDS_NUM             |10                      | Maximum number of returned hand regions. |

Call Example

```js
  async analyzeFrame() {
    try {
      var result = await HMSHandKeypointDetection.analyzeFrame(
        true,
        { filePath : "image_uri" },
        { 
            sceneType: HMSHandKeypointDetection.TYPE_KEYPOINT_ONLY,
            maxHandResults: HMSHandKeypointDetection.MAX_HANDS_NUM
        }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSHandKeypointDetection Data Types
 
##### AnalyzerConfiguration 

An object that represent hand recognition analyzer configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| maxHandResults                    | number    | Maximum number of returned results. |
| sceneType                         | number    | Return mode of the hand keypoint recognition result. |

### **HMSLivenessDetection**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [startDetect()](#hmslivenessdetectionstartdetect) | Promise\<object> |  Starts liveness detection. |
| [setConfig(captureConfig)](#hmslivenessdetectionsetconfigcaptureconfig)  | Promise\<object> |Sets the liveness detection capture configuration. |

#### Public Methods

##### HMSLivenessDetection.startDetect()

Starts liveness detection.

Call Example

```js
  async startDetect() {
    try {
      var result = await HMSLivenessDetection.startDetect();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : {
    pitch : 1.0,
    roll : 1.0,
    score : 1.0,
    yaw : 1.0,
    isLive : true
  }  
}
```

##### HMSLivenessDetection.setConfig(captureConfig)

Sets the liveness detection capture configuration. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| captureConfig     | [CaptureConfiguration](#hmslivenessdetection-data-types) | Configures capture. |

Call Example

```js
  async setConfig() {
    try {
      var result = await HMSLivenessDetection.setConfig(
        {
          option : HMSLivenessDetection.DETECT_MASK;
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
{ status : 0, message : "Success"}
```

#### HMSLivenessDetection Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|CAMERA_NO_PERMISSION         |11401                   | The camera permission is not obtained.|
|CAMERA_START_FAILED          |11402                   | Failed to start the camera.|
|USER_CANCEL                  |11403                   | The operation is canceled by the user.|
|DETECT_FACE_TIME_OUT         |11404                   | The face detection module times out.|
|DETECT_MASK                  |1                       | Sets whether to detect the mask.|

Call Example

```js
  async setConfig() {
    try {
      var result = await HMSLivenessDetection.setConfig(
        {
          option : HMSLivenessDetection.DETECT_MASK;
        }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSLivenessDetection Data Types
 
##### CaptureConfiguration

An object that configures capture.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| option                            | number    | Liveness detection parameters. Currently, the [DETECT_MASK](#hmslivenessdetection-constants) parameter is supported. |

### **HMSSkeletonDetection**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [analyzeFrame(isStop,analyzeType,frameConfiguration)](#hmsskeletondetectionanalyzeframeisstopanalyzetypeframeconfiguration) | Promise\<object> | Detects skeleton points in an input image in synchronous mode.   |
| [asyncAnalyzeFrame(isStop,analyzeType,frameConfiguration)](#hmsskeletondetectionasyncanalyzeframeisstopanalyzetypeframeconfiguration)   | Promise\<object> | Detects skeleton points in an input image in asynchronous mode.  |
| [calculateSimilarity(isStop,analyzeType,dataSet1,dataSet2)](#hmsskeletondetectioncalculatesimilarityisstopanalyzetypedataset1dataset2)  | Promise\<object> | Calculates the similarity between two sets of skeleton data. |


#### Public Methods

##### HMSSkeletonDetection.analyzeFrame(isStop,analyzeType,frameConfiguration)

Detects skeleton points in an input image in synchronous mode.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| analyzeType           | boolean | Analyze type.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion. | 


Call Example

```js
 async analyzeFrame() {
    try {
      var result = await HMSSkeletonDetection.analyzeFrame(
        true,
        HMSSkeletonDetection.TYPE_NORMAL,
        { filePath : "" }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      joints : [
        {
          type : 1,
          pointX : 1.0,
          pointY : 1.0,
          score : 1.0
        },
        ...
      ]
    },
    ...
  ]
}
```

##### HMSSkeletonDetection.asyncAnalyzeFrame(isStop,analyzeType,frameConfiguration)

Detects skeleton points in an input image in asynchronous mode.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| analyzeType           | boolean | Analyze type.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 


Call Example

```js
 async asyncAnalyzeFrame() {
    try {
      var result = await HMSSkeletonDetection.asyncAnalyzeFrame(
        true,
        HMSSkeletonDetection.TYPE_NORMAL,
        { filePath : "" }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      joints : [
        {
          type : 112,
          pointX : 1.0,
          pointY : 1.0,
          score : 1.0
        },
        ...
      ]
    },
    ...
  ]
}
```

##### HMSSkeletonDetection.calculateSimilarity(isStop,analyzeType,dataSet1,dataSet2)

Detects skeleton points in an input image in asynchronous mode.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| analyzeType           | boolean | Analyze type.|
| dataSet1              | [SkeletonDataSet[]](#hmsskeletondetection-data-types)| First data set that contains joints.| 
| dataSet2              | [SkeletonDataSet[]](#hmsskeletondetection-data-types)| Second data set that contains joints.| 

Call Example

```js
 async calculateSimilarity() {
    try {
      var result = await HMSSkeletonDetection.calculateSimilarity(
        true,
        HMSSkeletonDetection.TYPE_NORMAL,
        [
          {
            joints  : [
              {
                type : 109,
                pointX : 1.0,
                pointY : 1.0,
                score : 1.0
              },
              ...
            ]
          },
          ...
        ],
        [
          {
            joints  : [
              {
                type : 109,
                pointX : 1.0,
                pointY : 1.0,
                score : 1.0
              },
              ...
            ]
          },
          ...
        ],
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : 0.89 }
```

#### HMSSkeletonDetection Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|TYPE_HEAD_TOP           |113                       |Head point. |
|TYPE_LEFT_ANKLE         |112                       |Left ankle point. |
|TYPE_LEFT_ELBOW         |105                       |Left elbow point.|
|TYPE_LEFT_HIP           |110                       |Left hip point.|
|TYPE_LEFT_KNEE          |111                       |Left knee point.|
|TYPE_LEFT_SHOULDER      |104                       |Left shoulder point.|
|TYPE_LEFT_WRIST         |106                       |Left wrist point.|
|TYPE_NECK               |114                       |Neck point.|
|TYPE_RIGHT_ANKLE        |109                       |Right ankle point.|
|TYPE_RIGHT_ELBOW        |102                       |Right elbow point.|
|TYPE_RIGHT_HIP          |107                       |Right hip point.|
|TYPE_RIGHT_KNEE         |108                       |Right knee point.|
|TYPE_RIGHT_SHOULDER     |101                       |Right shoulder point.|
|TYPE_RIGHT_WRIST        |103                       |Right wrist point.|
|TYPE_NORMAL             |0                         |Detect skeleton points for normal postures.|
|TYPE_YOGA               |1                         |Detect skeleton points for yoga postures.|

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSSkeletonDetection.asyncAnalyzeFrame(
        true,
        HMSSkeletonDetection.TYPE_NORMAL,
        { filePath : "" }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSSkeletonDetection Data Types
 
##### SkeletonDataSet

An object that contains skeleton data.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| joints                            | [Joints[]](#hmsskeletondetection-data-types)  | Array of joints.  |

##### Joints

An object that contains joints data.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| type                              | number    | Skeleton point type. |
| pointX                            | number    | X coordinate. |
| pointY                            | number    | Y coordinate.  |
| score                             | number    | Confidence value of a skeleton point.  |

### **HMSLensEngine**

#### Public Method Summary 
| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [createLensEngine(analyzerTag,analyzerConfig,lensConfig)](#hmslensenginecreatelensengineanalyzertaganalyzerconfiglensconfig) | Promise\<object> | Creates lens engine. |
| [close()](#hmslensengineclose)  | Promise\<object> | Closes the camera and stops sending frames to the analyzer. |
| [doZoom(scale)](#hmslensenginedozoomscale)  | Promise\<object> |  Adjusts the focal length of the camera based on the scaling coefficient (digital zoom). |
| [getDisplayDimension()](#hmslensenginegetdisplaydimension)  | Promise\<object> | Obtains the size of the preview image of a camera. |
| [getLensType()](#hmslensenginegetlenstype)  | Promise\<object> | Obtains the selected camera type. |
| [photograph()](#hmslensenginephotograph)  | Promise\<object> | Monitors photographing. |
| [release()](#hmslensenginerelease)  | Promise\<object> | Releases resources occupied by lens engine. |
| [run()](#hmslensenginerun)  | Promise\<object> | Starts lens engine. |
| [runWithView()](#hmslensenginerunwithview)  | Promise\<object> | Starts the lens engine and uses [HMSSurfaceView](#hmssurfaceview) as the frame preview panel. |

#### Public Methods

##### HMSLensEngine.createLensEngine(analyzerTag,analyzerConfig,lensConfig)

Creates lens engine and sets analyzer according to given analyzerTag and analyzerConfig.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| analyzerTag | number | A tag that represents analyzer to be used in lens engine. |
| analyzerConfig | [AnalyzerConfiguration](#hmslensengine-data-types) | Analyzer configuration for related analyzer with given analyzerTag. |
| lensConfig | [LensConfiguration](#hmslensengine-data-types) | Configuration for lens engine. |

Call Example

```js
async createLensEngine() {
  try {
    var result = await HMSLensEngine.createLensEngine(
      HMSLensEngine.LENS_TEXT_ANALYZER,
      {
        language: "en",
        OCRMode: HMSTextRecognition.OCR_DETECT_MODE
      },
      {
        width: 480,
        height: 540,
        lensType: HMSLensEngine.BACK_LENS,
        automaticFocus: true,
        fps: 20.0,
        flashMode: HMSLensEngine.FLASH_MODE_OFF,
        focusMode: HMSLensEngine.FOCUS_MODE_CONTINUOUS_VIDEO
      }
    );
    console.log(result);
  } catch (error) {
    console.log(error);
  }
}
```

Example Response

```js
{ status : 0, message : "Success"}
```

##### HMSLensEngine.close()

Closes the camera and stops sending frames to the frame analyzer.

Call Example

```js
async close() {
  try {
    var result = await HMSLensEngine.close();
    console.log(result);
  } catch (error) {
    console.log(error);
  }
}
```

Example Response

```js
{ status : 0, message : "Success"}
```

##### HMSLensEngine.doZoom(scale)

Adjusts the focal length of the camera based on the scaling coefficient (digital zoom).

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| scale      | number | Digital zoom scale. |

Call Example

```js
async doZoom() {
  try {
    var result = await HMSLensEngine.doZoom(3.0);
    console.log(result);
  } catch (error) {
    console.log(error);
  }
}
```

Example Response

```js
{ status : 0, message : "Success"}
```

##### HMSLensEngine.getDisplayDimension()

Obtains the size of the preview image of a camera.

Call Example

```js
async getDisplayDimension() {
  try {
    var result = await HMSLensEngine.getDisplayDimension();
    console.log(result);
  } catch (error) {
    console.log(error);
  }
}
```

Example Response

```js
{ status : 0, message : "Success", width : 480, height : 520 }
```


##### HMSLensEngine.getLensType()

Obtains the selected camera type.

Call Example

```js
async getLensType() {
  try {
    var result = await HMSLensEngine.getLensType();
    console.log(result);
  } catch (error) {
    console.log(error);
  }
}
```

Example Response

```js
{ status : 0, message : "Success", result : 0 }
```

##### HMSLensEngine.photograph()

Monitors photographing and sets [LENS_ON_PHOTO_TAKEN and LENS_ON_CLICK_SHUTTER](#hmslensengine-events) events.

Call Example

```js
async photograph() {
  try {
    var result = await HMSLensEngine.photograph();
    console.log(result);
  } catch (error) {
    console.log(error);
  }
}
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSLensEngine.release()

Releases resources occupied by lens engine.

Call Example

```js
async release() {
  try {
    var result = await HMSLensEngine.release();
    console.log(result);
  } catch (error) {
    console.log(error);
  }
}
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSLensEngine.run()

Starts lens engine.

Call Example

```js
async run() {
  try {
    var result = await HMSLensEngine.run();
    console.log(result);
  } catch (error) {
    console.log(error);
  }
}
```

Example Response

```js
{ status : 0, message : "Success" }
```

##### HMSLensEngine.runWithView()

Starts the lens engine and uses [HMSSurfaceView](#hmssurfaceview) as the frame preview panel.

Call Example

```js
async runWithView() {
  try {
    var result = await HMSLensEngine.runWithView();
    console.log(result);
  } catch (error) {
    console.log(error);
  }
}
```

Example Response

```js
{ status : 0, message : "Success" }
```


#### HMSLensEngine Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|FLASH_MODE_OFF               |"off"                  | Flash off.|
|FLASH_MODE_ON                |"on"                   | Flash on.|
|FLASH_MODE_AUTO              |"auto"                 | Flash automatic.|
|FOCUS_MODE_CONTINUOUS_PICTURE|"continuous_picture"   | Continuous picture focus mode.|
|FOCUS_MODE_CONTINUOUS_VIDEO  |"continuous_video"     | Continuous video focus mode.|
|BACK_LENS                    |0                      | Rear camera.|
|FRONT_LENS                   |1                      | Front camera.|
|LENS_ON_PHOTO_TAKEN          |"lensOnPhotoTaken"     | [LENS_ON_PHOTO_TAKEN](#hmslensengine-events) event key.|
|LENS_ON_CLICK_SHUTTER        |"lensOnShutterClick"   | [LENS_ON_CLICK_SHUTTER](#hmslensengine-events) event key. |
|LENS_SURFACE_ON_CREATED      |"lensSurfaceOnCreated" | [LENS_SURFACE_ON_CREATED](#hmslensengine-events) event key.|
|LENS_SURFACE_ON_CHANGED      |"lensSurfaceOnChanged" | [LENS_SURFACE_ON_CHANGED](#hmslensengine-events) event key.|
|LENS_SURFACE_ON_DESTROY      |"lensSurfaceOnDestroy" | [LENS_SURFACE_ON_DESTROY](#hmslensengine-events) event key.|
|TEXT_TRANSACTOR_ON_DESTROY          |"textTransactorOnDestroy"          |[TEXT_TRANSACTOR_ON_DESTROY](#hmslensengine-events) event key. |
|TEXT_TRANSACTOR_ON_RESULT           |"textTransactorOnResult"           |[TEXT_TRANSACTOR_ON_RESULT](#hmslensengine-events) event key. |
|FACE_2D_TRANSACTOR_ON_DESTROY       |"face2dTransactorOnDestroy"        |[FACE_2D_TRANSACTOR_ON_DESTROY](#hmslensengine-events) event key. |
|FACE_2D_TRANSACTOR_ON_RESULT        |"face2dTransactorOnResult"         |[FACE_2D_TRANSACTOR_ON_RESULT](#hmslensengine-events) event key. |
|FACE_3D_TRANSACTOR_ON_DESTROY       |"face3dTransactorOnDestroy"        |[FACE_3D_TRANSACTOR_ON_DESTROY](#hmslensengine-events) event key. |
|FACE_3D_TRANSACTOR_ON_RESULT        |"face3dTransactorOnResult"         |[FACE_3D_TRANSACTOR_ON_RESULT](#hmslensengine-events) event key. |
|CLASSIFICATION_TRANSACTOR_ON_DESTROY|"classificationTransactorOnDestroy"|[CLASSIFICATION_TRANSACTOR_ON_DESTROY](#hmslensengine-events) event key. |
|CLASSIFICATION_TRANSACTOR_ON_RESULT |"classificationTransactorOnResult" |[CLASSIFICATION_TRANSACTOR_ON_RESULT](#hmslensengine-events) event key. |
|OBJECT_TRANSACTOR_ON_DESTROY        |"objectTransactorOnDestroy"        |[OBJECT_TRANSACTOR_ON_DESTROY](#hmslensengine-events) event key. |
|OBJECT_TRANSACTOR_ON_RESULT         |"objectTransactorOnResult"         |[OBJECT_TRANSACTOR_ON_RESULT](#hmslensengine-events) event key. |
|SCENE_TRANSACTOR_ON_DESTROY         |"sceneTransactorOnDestroy"         |[SCENE_TRANSACTOR_ON_DESTROY](#hmslensengine-events) event key. |
|SCENE_TRANSACTOR_ON_RESULT          |"sceneTransactorOnResult"          |[SCENE_TRANSACTOR_ON_RESULT](#hmslensengine-events) event key. |
|SKELETON_TRANSACTOR_ON_DESTROY      |"skeletonTransactorOnDestroy"      |[SKELETON_TRANSACTOR_ON_DESTROY](#hmslensengine-events) event key. |
|SKELETON_TRANSACTOR_ON_RESULT       |"skeletonTransactorOnResult"       |[SKELETON_TRANSACTOR_ON_RESULT](#hmslensengine-events) event key. |
|HAND_TRANSACTOR_ON_DESTROY          |"handTransactorOnDestroy"          |[HAND_TRANSACTOR_ON_DESTROY](#hmslensengine-events) event key. |
|HAND_TRANSACTOR_ON_RESULT           |"handTransactorOnResult"           |[HAND_TRANSACTOR_ON_RESULT](#hmslensengine-events) event key. |
|LENS_TEXT_ANALYZER                  |0                   | Text analyzer.|
|LENS_FACE_2D_ANALYZER               |1                   | 2d face analyzer.|
|LENS_FACE_3D_ANALYZER               |2                   | 3d face analyzer.|
|LENS_SKELETON_ANALYZER              |3                   | Skeleton analyzer.|
|LENS_CLASSIFICATION_ANALYZER        |4                   | Classification analyzer.|
|LENS_OBJECT_ANALYZER                |5                   | Object analyzer.|
|LENS_SCENE_ANALYZER                 |6                   | Scene analyzer.|
|LENS_HAND_ANALYZER                  |7                   | Hand analyzer.|

Call Example

```js
  this.eventEmitter.addListener(HMSLensEngine.LENS_ON_PHOTO_TAKEN, (event) => {
      console.log(event);
  });
```

#### HMSLensEngine Data Types
 
##### AnalyzerConfiguration

Please refer to related modules analyzer configurations.

##### LensConfiguration

An object that configures lens engine.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| width                             | number    | Width of preview image of a camera.  |
| height                            | number    | Height of preview image of a camera.  |
| lensType                          | number    | Back or front camera.  |
| fps                               | number    | Frame rate (FPS) of a camera.  |
| automaticFocus                    | boolean   | Enables automatic focus if true.  |
| flashMode                         | string    | Flash mode.  |
| focusMode                         | string    | Focus mode.  |

#### HMSLensEngine Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| LENS_ON_PHOTO_TAKEN                  | Event emitted when a photo taken. |
| LENS_ON_CLICK_SHUTTER                | Event emitted when a shutter clicked. |
| LENS_SURFACE_ON_CREATED              | Event emitted when HMSSurfaceView surface created. |
| LENS_SURFACE_ON_CHANGED              | Event emitted when HMSSurfaceView surface changed. |
| LENS_SURFACE_ON_DESTROY              | Event emitted when HMSSurfaceView surface destroyed. |
| TEXT_TRANSACTOR_ON_DESTROY           | Event emitted when text analyzer transactor destroyed. |
| TEXT_TRANSACTOR_ON_RESULT            | Event emitted when text analyzer transactor obtained the result. |
| FACE_2D_TRANSACTOR_ON_DESTROY        | Event emitted when 2d face analyzer transactor destroyed. |
| FACE_2D_TRANSACTOR_ON_RESULT         | Event emitted when 2d face analyzer transactor obtained the result. |
| FACE_3D_TRANSACTOR_ON_DESTROY        | Event emitted when 3d face analyzer transactor destroyed. |
| FACE_3D_TRANSACTOR_ON_RESULT         | Event emitted when 3d face analyzer transactor obtained the result. |
| CLASSIFICATION_TRANSACTOR_ON_DESTROY | Event emitted when classification analyzer transactor destroyed. |
| CLASSIFICATION_TRANSACTOR_ON_RESULT  | Event emitted when classification analyzer transactor obtained the result. |
| OBJECT_TRANSACTOR_ON_DESTROY         | Event emitted when object analyzer transactor destroyed. |
| OBJECT_TRANSACTOR_ON_RESULT          | Event emitted when object analyzer transactor obtained the result. |
| SCENE_TRANSACTOR_ON_DESTROY          | Event emitted when scene analyzer transactor destroyed. |
| SCENE_TRANSACTOR_ON_RESULT           | Event emitted when scene analyzer transactor obtained the result. |
| SKELETON_TRANSACTOR_ON_DESTROY       | Event emitted when skeleton analyzer transactor destroyed. |
| SKELETON_TRANSACTOR_ON_RESULT        | Event emitted when skeleton analyzer transactor obtained the result. |
| HAND_TRANSACTOR_ON_DESTROY           | Event emitted when hand analyzer transactor destroyed. |
| HAND_TRANSACTOR_ON_RESULT            | Event emitted when hand analyzer transactor obtained the result. |

Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSLensEngine);
    
    eventEmitter.addListener(HMSLensEngine.LENS_ON_PHOTO_TAKEN, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    status : 0,
       *    message : "Success",
       *    result : ""           // image uri                  
       * }
      */
    });

    eventEmitter.addListener(HMSLensEngine.LENS_ON_CLICK_SHUTTER, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * null
      */
    });

    eventEmitter.addListener(HMSLensEngine.LENS_SURFACE_ON_CREATED, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * null
      */
    });

    eventEmitter.addListener(HMSLensEngine.LENS_SURFACE_ON_CHANGED, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    format : 4,   // surface format
       *    width : 400, 
       *    height : 400 
       * }
      */
    });

    eventEmitter.addListener(HMSLensEngine.LENS_SURFACE_ON_DESTROY, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * null
      */
    });
```

### **HMSTextImageSuperResolution**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [analyzeFrame(isStop,frameConfiguration)](#hmstextimagesuperresolutionanalyzeframeisstopframeconfiguration) | Promise\<object> | Performs super-resolution processing on the source image using the synchronous method. |
| [asyncAnalyzeFrame(isStop,frameConfiguration)](#hmstextimagesuperresolutionasyncanalyzeframeisstopframeconfiguration)   | Promise\<object> | Performs super-resolution processing on the source image using the asynchronous method. |

#### Public Methods

##### HMSTextImageSuperResolution.analyzeFrame(isStop,frameConfiguration)

Performs super-resolution processing on the source image using the synchronous method. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 

Call Example

```js
 async analyzeFrame() {
    try {
      var result = await HMSTextImageSuperResolution.analyzeFrame(
        true,
        { filePath : "" }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : ["", ...] // image uris }
```

##### HMSTextImageSuperResolution.asyncAnalyzeFrame(isStop,frameConfiguration)

Performs super-resolution processing on the source image using the asynchronous method.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 


Call Example

```js
 async asyncAnalyzeFrame() {
    try {
      var result = await HMSTextImageSuperResolution.asyncAnalyzeFrame(
        true,
        { filePath : "" }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ status : 0, message : "Success", result : "" // image uri }
```

### **HMSSceneDetection**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [analyzeFrame(isStop,confidence,frameConfiguration)](#hmsscenedetectionanalyzeframeisstopconfidenceframeconfiguration) | Promise\<object> | Detects scene information in an input image in synchronous mode. |
| [asyncAnalyzeFrame(isStop,confidence,frameConfiguration)](#hmsscenedetectionasyncanalyzeframeisstopconfidenceframeconfiguration)   | Promise\<object> | Detects scene information in an input image in asynchronous mode. |

#### Public Methods

##### HMSSceneDetection.analyzeFrame(isStop,confidence,frameConfiguration)

Detects scene information in an input image in synchronous mode.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| confidence            | number  | The result less than confidence filtered out.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion. | 

Call Example

```js
  async analyzeFrame() {
    try {
      var result = await HMSSceneDetection.analyzeFrame(
        true,
        0.5,
        { filePath : "" }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0,
  message : "Success", 
  result : [
    {
      result : "",
      confidence : 0.6
    },
    ...
  ] 
}
```

##### HMSSceneDetection.asyncAnalyzeFrame(isStop,confidence,frameConfiguration)

Detects scene information in an input image in asynchronous mode.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| confidence            | number  | The result less than confidence filtered out.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion. | 

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSSceneDetection.asyncAnalyzeFrame(
        true,
        0.5,
        { filePath : "" }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0,
  message : "Success", 
  result : [
    {
      result : "",
      confidence : 0.6
    },
    ...
  ] 
}
```

### **HMSProductVisionSearch**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [asyncAnalyzeFrame(isStop,frameConfiguration,analyzerConfiguration)](#hmsproductvisionsearchasyncanalyzeframeisstopframeconfigurationanalyzerconfiguration)| Promise\<object> | Search products asynchronously. |
| [startProductVisionSearchCapturePlugin(pluginConfiguration)](#hmsproductvisionsearchstartproductvisionsearchcapturepluginpluginconfiguration) | Promise\<object> | Start product vision search plugin. Sets [PRODUCT_ON_RESULT](#hmsproductvisionsearch-events) event.|

#### Public Methods

##### HMSProductVisionSearch.asyncAnalyzeFrame(isStop,frameConfiguration,analyzerConfiguration)

Search products asynchronously.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| analyzerConfiguration | [AnalyzerConfiguration](#hmsproductvisionsearch-data-types) | Configures search analyzer. | 

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSProductVisionSearch.asyncAnalyzeFrame(
        true,
        { filePath : "" },
        { maxResults: 10, productSetId: "", region: HMSProductVisionSearch.REGION_DR_CHINA }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0,
  message : "Success", 
  result : [
    {
      type : "",
      border : {
        left : 1,
        right : 1,
        top : 1,
        down : 1
      },
      products : [
        {
          productId : "",
          productUrl : "",
          customContent : "",
          possibility : 1.0,
          images : [
            {
              imageId : "",
              productId : "",
              possibility : 1.0
            },
            ...
          ]
        },
        ...
      ]
    },
    ...
  ] 
}
```

##### HMSProductVisionSearch.startProductVisionSearchCapturePlugin(pluginConfiguration)

Start product vision search plugin. Sets [PRODUCT_ON_RESULT](#hmsproductvisionsearch-events) event.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| pluginConfiguration | [AnalyzerConfiguration](#hmsproductvisionsearch-data-types) | Configures plugin analyzer. | 

Call Example

```js
 async startProductVisionSearchCapturePlugin() {
    try {
      var result = await HMSProductVisionSearch.startProductVisionSearchCapturePlugin(
        { maxResults: 10, productSetId: "", region: HMSProductVisionSearch.REGION_DR_CHINA }
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

#### HMSProductVisionSearch Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|REGION_DR_CHINA          |1002                        | China.|
|REGION_DR_SIANGAPORE     |1007                        | Singapore.|
|REGION_DR_GERMAN         |1006                        | Germany.|
|REGION_DR_RUSSIA         |1005                        | Russia.|
|PRODUCT_ON_RESULT        |"productOnResult"           | [PRODUCT_ON_RESULT](#hmsproductvisionsearch-events) event key.|

Call Example

```js
  async startProductVisionSearchCapturePlugin() {
    try {
      var result = await HMSProductVisionSearch.startProductVisionSearchCapturePlugin(
        { maxResults: 10, productSetId: "", region: HMSProductVisionSearch.REGION_DR_CHINA }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSProductVisionSearch Data Types
 
##### AnalyzerConfiguration

An object that configures product vision search analyzer.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| maxResults                        | number    | Maximum number of results.  |
| productSetId                      | string    | Product set id.  |
| region                            | number    | Region.  |

#### HMSProductVisionSearch Events

| Event                           | Description                                                  |
| ------------------------------- | ------------------------------------------------------------ |
| PRODUCT_ON_RESULT               | Event emitted when plugin result obtained. |

Call Example

```js
  componentDidMount(){
    const eventEmitter = new NativeEventEmitter(HMSProductVisionSearch);
    
    eventEmitter.addListener(HMSProductVisionSearch.PRODUCT_ON_RESULT, (event) => {
      console.log(event);
      /**
       * Sample Event Result
       * {
       *    status : 0,
       *    message : "Success",
       *    result : [
       *      {
       *        type : "",
       *        border : {
       *          left : 1,
       *          right : 1,
       *          top : 1,
       *          down : 1
       *        },
       *        products : [
       *          {
       *            productId : "",
       *            productUrl : "",
       *            customContent : "",
       *            possibility : 1.0,
       *            images : [
       *              {
       *                imageId : "",
       *                productId : "",
       *                possibility : 1.0
       *              },
       *              ...
       *            ]
       *          },
       *          ...
       *        ]
       *      },
       *      ...
       *    ]
       * }
       * 
      */
    });
```

### **HMSImageSuperResolution**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [asyncAnalyzeFrame(isStop,frameConfiguration,scale)](#hmsimagesuperresolutionasyncanalyzeframeisstopframeconfigurationscale)| Promise\<object> | Performs super-resolution processing on the source image using the asynchronous method.|
| [analyzeFrame(isStop,frameConfiguration,scale)](#hmsimagesuperresolutionanalyzeframeisstopframeconfigurationscale) | Promise\<object> | Performs super-resolution processing on the source image using the synchronous method. |

#### Public Methods

##### HMSImageSuperResolution.asyncAnalyzeFrame(isStop,frameConfiguration,scale)

Performs super-resolution processing on the source image using the asynchronous method.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion. | 
| scale | number | Image super-resolution multiplier. | 

Call Example

```js
 async asyncAnalyzeFrame() {
    try {
      var result = await HMSImageSuperResolution.asyncAnalyzeFrame(
        true,
        { filePath : "" },
        HMSImageSuperResolution.ISR_SCALE_3X
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success", 
  result : "" // image uri 
}
```
##### HMSImageSuperResolution.analyzeFrame(isStop,frameConfiguration,scale)

Performs super-resolution processing on the source image using the synchronous method. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion. | 
| scale | number | Image super-resolution multiplier. | 

Call Example

```js
 async analyzeFrame() {
    try {
      var result = await HMSImageSuperResolution.analyzeFrame(
        true,
        { filePath : "" },
        HMSImageSuperResolution.ISR_SCALE_3X
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success", 
  result : ["", ...] // image uris 
}
```

#### HMSImageSuperResolution Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|ISR_SCALE_1X             |1.0f                        | 1x super-resolution, which is used to remove the blocking artifact caused by image compression.|
|ISR_SCALE_3X             |3.0f                        | 3x super-resolution, which suppresses some compressed noises, improves the detail texture effect, and provides the 3x enlargement capability.|

Call Example

```js
  async analyzeFrame() {
    try {
      var result = await HMSImageSuperResolution.analyzeFrame(
        true,
        { filePath : "" },
        HMSImageSuperResolution.ISR_SCALE_3X
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

### **HMSImageClassification**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [analyzeFrame(isRemote,isStop,frameConfiguration,analyzerSetting)](#hmsimageclassificationanalyzeframeisremoteisstopframeconfigurationanalyzersetting) | Promise\<object> |Classifies images by synchronous processing.  |
| [asyncAnalyzeFrame(isRemote,isStop,frameConfiguration,analyzerSetting)](#hmsimageclassificationasyncanalyzeframeisremoteisstopframeconfigurationanalyzersetting)   | Promise\<object> | Classifies images by asynchronous processing.  |

#### Public Methods

##### HMSImageClassification.analyzeFrame(isRemote,isStop,frameConfiguration,analyzerSetting)

Classifies images by synchronous processing. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote              | boolean | If true uses on-cloud analyzer otherwise on-device.|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| analyzerSetting      | [AnalyzerConfiguration](#hmsimageclassification-data-types) | Object that contains analyzer configuration.| 


Call Example

```js
 async analyzeFrame() {
    try {
      var result = await HMSImageClassification.analyzeFrame(
        true,
        true,
        { filePath : "" },
        { maxNumberOfReturns: 5, minAcceptablePossibility: 0.8 }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      classificationIdentity : "",
      name : "",
      possibility : 1.0
    },
    ...
  ]
}
```

##### HMSImageClassification.asyncAnalyzeFrame(isRemote,isStop,frameConfiguration,analyzerSetting)

Classifies images by asynchronous processing. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isRemote              | boolean | If true uses on-cloud analyzer otherwise on-device.|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| analyzerSetting      | [AnalyzerConfiguration](#hmsimageclassification-data-types) | Object that contains analyzer configuration.| 

Call Example

```js
 async asyncAnalyzeFrame() {
    try {
      var result = await HMSImageClassification.asyncAnalyzeFrame(
        false,
        true,
        { filePath : "" },
        { minAcceptablePossibility: 0.8 }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success",
  result : [
    {
      classificationIdentity : "",
      name : "",
      possibility : 1.0
    },
    ...
  ]
}
```

#### HMSImageClassification Data Types
 
##### AnalyzerConfiguration

An object that represents analyzer configuration.

###### On-Cloud Analyzer

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| maxNumberOfReturns                | number    | Maximum number of results.  |
| minAcceptablePossibility          | number    | Minimum result acceptable possibility.  |

###### On-Device Analyzer

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| minAcceptablePossibility          | number    | Minimum result acceptable possibility.  |


### **HMSImageSegmentation**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [analyzeFrame(isStop,frameConfiguration,analyzerSetting)](#hmsimagesegmentationanalyzeframeisstopframeconfigurationanalyzersetting) | Promise\<object> |Implements image segmentation in synchronous mode. |
| [asyncAnalyzeFrame(isStop,frameConfiguration,analyzerSetting)](#hmsimageclassificationasyncanalyzeframeisremoteisstopframeconfigurationanalyzersetting)   | Promise\<object> | Implements image segmentation in asynchronous mode.  |

#### Public Methods

##### HMSImageSegmentation.analyzeFrame(isStop,frameConfiguration,analyzerSetting)

Implements image segmentation in synchronous mode. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| analyzerSetting      | [AnalyzerConfiguration](#hmsimagesegmentation-data-types) | Object that contains analyzer configuration.| 

Call Example

```js
  async analyzeFrame() {
    try {
      var result = await HMSImageSegmentation.analyzeFrame(
        true,
        { filePath : "" },
        { 
          analyzerType: HMSImageSegmentation.BODY_SEG, 
          scene: HMSImageSegmentation.ALL, 
          exact: true 
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
{ 
  status : 0, 
  message : "Success", 
  result : [
    { 
      foreground : "",  // foreground uri
      grayscale : ""    // grayscale uri
    }, 
    ...
  ]
}
```

##### HMSImageSegmentation.asyncAnalyzeFrame(isStop,frameConfiguration,analyzerSetting)

Implements image segmentation in asynchronous mode. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| analyzerSetting      | [AnalyzerConfiguration](#hmsimageclassification-data-types) | Object that contains analyzer configuration.| 

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSImageSegmentation.asyncAnalyzeFrame(
        true,
        { filePath : "" },
        { 
          analyzerType: HMSImageSegmentation.BODY_SEG, 
          scene: HMSImageSegmentation.ALL, 
          exact: true 
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
{ 
  status : 0, 
  message : "Success", 
  result : { 
    foreground : "",  // foreground uri
    grayscale : ""    // grayscale uri
  } 
}
```

#### HMSImageSegmentation Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|TYPE_BACKGROUND          |0                           | Background. |
|TYPE_HUMAN               |1                           | Human body. |
|TYPE_SKY                 |2                           | Sky. |
|TYPE_GRASS               |3                           | Plant |
|TYPE_FOOD                |4                           | Food. |
|TYPE_CAT                 |5                           | Cat. |
|TYPE_BUILD               |6                           | Building. |
|TYPE_FLOWER              |7                           | Flower. |
|TYPE_WATER               |8                           | Water. |
|TYPE_SAND                |9                           | Sand. |
|TYPE_MOUNTAIN            |10                          | Mountain. |
|ALL                      |0                           | Obtains all segmentation results by default.|
|FOREGROUND_ONLY          |2                           | Obtains the human body image with a transparent background. |
|GRAYSCALE_ONLY           |3                           | Obtains the gray-scale image with a white human body and black background.|
|MASK_ONLY                |1                           | Obtains the pixel-level label information. |
|BODY_SEG                 |0                           | Detection based on the portrait model |
|IMAGE_SEG                |1                           | Detection based on the multiclass image mode |

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSImageSegmentation.asyncAnalyzeFrame(
        true,
        { filePath : "" },
        { 
          analyzerType: HMSImageSegmentation.BODY_SEG, 
          scene: HMSImageSegmentation.ALL, 
          exact: true 
        }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSImageSegmentation Data Types
 
##### AnalyzerConfiguration

An object that represents analyzer configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| analyzerType                      | number    | Sets the classification mode.  |
| scene                             | number    | Sets the type of the returned result.  |
| isExact                           | boolean   | Determines whether to support fine detection.  |

### **HMSObjectRecognition**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [analyzeFrame(isStop,frameConfiguration,analyzerSetting)](#hmsobjectrecognitionanalyzeframeisstopframeconfigurationanalyzersetting) | Promise\<object> |Recognizes objects in images by synchronous processing. |
| [asyncAnalyzeFrame(isStop,frameConfiguration,analyzerSetting)](#hmsobjectrecognitionasyncanalyzeframeisstopframeconfigurationanalyzersetting)   | Promise\<object> |Recognizes objects in images by asynchronous processing. |

#### Public Methods

##### HMSObjectRecognition.analyzeFrame(isStop,frameConfiguration,analyzerSetting)

Recognizes objects in images by synchronous processing. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion. | 
| analyzerSetting      | [AnalyzerConfiguration](#hmsobjectrecognition-data-types) | Object that contains analyzer configuration.| 

Call Example

```js
 async analyzeFrame() {
    try {
      var result = await HMSObjectRecognition.analyzeFrame(
        true,
        { filePath : "" },
        { 
          analyzerType: HMSObjectRecognition.TYPE_PICTURE,
          allowClassification: true, 
          allowMultiResults: true 
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
{ 
  status : 0, 
  message : "Success", 
  result : [
    {
      tracingIdentity : 0,
      typeIdentity : 0,
      typePossibility : 0,
      border : {
        left : 1,
        right : 1,
        top : 1,
        down : 1
      }
    },
    ...
  ]
}
```

##### HMSObjectRecognition.asyncAnalyzeFrame(isStop,frameConfiguration,analyzerSetting)

Recognizes objects in images by asynchronous processing. 

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| analyzerSetting      | [AnalyzerConfiguration](#hmsobjectrecognition-data-types) | Object that contains analyzer configuration.| 


Call Example

```js
 async asyncAnalyzeFrame() {
    try {
      var result = await HMSObjectRecognition.asyncAnalyzeFrame(
        true,
        { filePath : "" },
        { 
          analyzerType: HMSObjectRecognition.TYPE_PICTURE,
          allowClassification: true, 
          allowMultiResults: true 
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
{ 
  status : 0, 
  message : "Success", 
  result : [
    {
      tracingIdentity : 0,
      typeIdentity : 0,
      typePossibility : 0,
      border : {
        left : 1,
        right : 1,
        top : 1,
        down : 1
      }
    },
    ...
  ]
}
```

#### HMSObjectRecognition Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|TYPE_PICTURE             |0                           | Image-based detection. |
|TYPE_VIDEO               |1                           | Video stream-based detection. |
|TYPE_OTHER               |0                           | Others. |
|TYPE_FACE                |6                           | Faces |
|TYPE_FOOD                |2                           | Food. |
|TYPE_FURNITURE           |3                           | Furniture. |
|TYPE_GOODS               |1                           | Goods. |
|TYPE_PLACE               |5                           | Places. |
|TYPE_PLANT               |4                           | Plants. |

Call Example

```js
   async asyncAnalyzeFrame() {
    try {
      var result = await HMSObjectRecognition.asyncAnalyzeFrame(
        true,
        { filePath : "" },
        { 
          analyzerType: HMSObjectRecognition.TYPE_PICTURE,
          allowClassification: true, 
          allowMultiResults: true 
        }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSObjectRecognition Data Types
 
##### AnalyzerConfiguration

An object that represents analyzer configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| analyzerType                      | number    | Sets the object detection mode.  |
| allowMultiResults                 | boolean   | Sets whether to support multi-object detection.  |
| allowClassification               | boolean   | Sets whether to support detection result classification.  |

### **HMSLandmarkRecognition**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [asyncAnalyzeFrame(isStop,frameConfiguration,analyzerSetting)](#hmslandmarkrecognitionasyncanalyzeframeisstopframeconfigurationanalyzersetting)   | Promise\<object> |Recognizes landmarks in images by asynchronous processing. |

#### Public Methods

##### HMSLandmarkRecognition.asyncAnalyzeFrame(isStop,frameConfiguration,analyzerSetting)

Recognizes landmarks in images by asynchronous processing.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| analyzerSetting       | [AnalyzerConfiguration](#hmslandmarkrecognition-data-types) | Object that contains analyzer configuration.| 


Call Example

```js
 async asyncAnalyzeFrame() {
    try {
      var result = await HMSLandmarkRecognition.asyncAnalyzeFrame(
        true,
        { filePath : "" },
        { 
          largestNumOfReturns: 10, 
          patternType: HMSLandmarkRecognition.STEADY_PATTERN 
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
{ 
  status : 0, 
  message : "Success", 
  result : [
    {
      landMark : "",
      possibility : 0.6,
      coordinates : [
        {
          latitude : 43.23,
          longitude : 37.29
        },
        ...
      ],
      border : {
        left : 1,
        right : 1,
        top : 1,
        down : 1
      }
    },
    ...
  ]
}
```

#### HMSLandmarkRecognition Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|NEWEST_PATTERN           |2                           | Latest mode. |
|STEADY_PATTERN           |1                           | Stable mode.|

Call Example

```js
  async asyncAnalyzeFrame() {
    try {
      var result = await HMSLandmarkRecognition.asyncAnalyzeFrame(
        true,
        { filePath : "" },
        { 
          largestNumOfReturns: 10, 
          patternType: HMSLandmarkRecognition.STEADY_PATTERN 
        }
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSLandmarkRecognition Data Types
 
##### AnalyzerConfiguration

An object that represents analyzer configuration.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| largestNumOfReturns               | number    | Maximum number of detection results.  |
| patternType                       | number    | Mode used by the analyzer.  |

### **HMSDocumentSkewCorrection**

#### Public Method Summary 

| Method                                                   | Return Type      | Description                                             |
| -------------------------------------------------------- | ---------------- | ------------------------------------------------------- |
| [analyzeFrame(isStop,frameConfiguration)](#hmsdocumentskewcorrectionanalyzeframeisstopframeconfiguration)   | Promise\<object> | Synchronous calling entry for text box tilt detection. |
| [asyncDocumentSkewDetect(isStop,frameConfiguration)](#hmsdocumentskewcorrectionasyncdocumentskewdetectisstopframeconfiguration)   | Promise\<object> |  Asynchronous calling entry for text box tilt detection. |
| [asyncDocumentSkewCorrect(isStop,frameConfiguration,points)](#hmsdocumentskewcorrectionasyncdocumentskewcorrectisstopframeconfigurationpoints)   | Promise\<object> |  Asynchronous calling entry for text box tilt correction. |
| [syncDocumentSkewCorrect(isStop,frameConfiguration,points)](#hmsdocumentskewcorrectionasyncdocumentskewcorrectisstopframeconfigurationpoints)   | Promise\<object> |  Synchronous calling entry for text box tilt correction. |

#### Public Methods

##### HMSDocumentSkewCorrection.analyzeFrame(isStop,frameConfiguration)

Synchronous calling entry for text box tilt detection.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 


Call Example

```js
  async analyzeFrame() {
    try {
      var result = await HMSDocumentSkewCorrection.analyzeFrame(
        true,
        { filePath : "" },
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success", 
  result : [
    {
      leftBottomPosition : {
        x : 1,
        y : 1
      },
      leftTopPosition : {
        x : 1,
        y : 1
      },
      rightBottomPosition : {
        x : 1,
        y : 1
      },
      rightTopPosition : {
        x : 1,
        y : 1
      }
    },
    ...
  ]
}
```

##### HMSDocumentSkewCorrection.asyncDocumentSkewDetect(isStop,frameConfiguration)

Asynchronous calling entry for text box tilt detection.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion. | 

Call Example

```js
 async asyncDocumentSkewDetect() {
    try {
      var result = await HMSDocumentSkewCorrection.asyncDocumentSkewDetect(
        true,
        { filePath : "" },
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success", 
  result :
    {
      leftBottomPosition : {
        x : 1,
        y : 1
      },
      leftTopPosition : {
        x : 1,
        y : 1
      },
      rightBottomPosition : {
        x : 1,
        y : 1
      },
      rightTopPosition : {
        x : 1,
        y : 1
      }
    }
}
```

##### HMSDocumentSkewCorrection.asyncDocumentSkewCorrect(isStop,frameConfiguration,points)

Asynchronous calling entry for text box tilt correction.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| points                | [Points[]](#hmsdocumentskewcorrection-data-types) | Array of objects that contains points to be corrected. | 


Call Example

```js
 async asyncDocumentSkewCorrect() {
    try {
      var result = await HMSDocumentSkewCorrection.asyncDocumentSkewCorrect(
        true,
        { filePath : "" },
        [
          {x : 1, y : 1},
          ...
        ]
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success", 
  result :""          // corrected image uri
}
```

##### HMSDocumentSkewCorrection.syncDocumentSkewCorrect(isStop,frameConfiguration,points)

Asynchronous calling entry for text box tilt correction.

###### Parameters

| Parameter  | Type   | Description                          |
|------------|--------|--------------------------------------|
| isStop                | boolean | Releases resources for analyzer.|
| frameConfiguration    | [Frame](#hmsframe-data-types) | Object that contains frame configuraion.| 
| points                | [Points[]](#hmsdocumentskewcorrection-data-types) | Array of objects that contains points to be corrected. | 

Call Example

```js
  async syncDocumentSkewCorrect() {
    try {
      var result = await HMSDocumentSkewCorrection.syncDocumentSkewCorrect(
        true,
        { filePath : "" },
        [
          {x : 1, y : 1},
          ...
        ]
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }
```

Example Response

```js
{ 
  status : 0, 
  message : "Success", 
  result : ["", ... ]  // corrected image uris
}
```

#### HMSDocumentSkewCorrection Constants

| Name                    | Value                      | Description                          |
|-------------------------|----------------------------|--------------------------------------|
|CORRECTION_FAILED        |2                           | Text box correction  failure. |
|DETECT_FAILED            |1                           | Text box detection failure.|
|IMAGE_DATA_ERROR         |3                           | Incorrect input parameter for text box detection/correction. |
|SUCCESS                  |0                           | Text box detection/correction succeeded.|

Call Example

```js
   async syncDocumentSkewCorrect() {
    try {
      var result = await HMSDocumentSkewCorrection.syncDocumentSkewCorrect(
        true,
        { filePath : "" },
        [
          {x : 1, y : 1},
          ...
        ]
      );
      console.log(result);
      if (result.status == HMSDocumentSkewCorrection.DETECT_FAILED){
        console.log("Detection failed");
      }
    } catch (e) {
      console.log(e);
    }
  }
```

#### HMSDocumentSkewCorrection Data Types
 
##### Points

An object that represent point to be corrected.

| Field Name                        | Type      | Description                                             |
| --------------------------------- | --------- | ------------------------------------------------------- |
| x                                 | number    | X axis coordinate.|
| y                                 | number    | Y axis coordinate. |

### **HMSSurfaceView**

A view that provides camera preview for [HMSLensEngine](#hmslensengine). 

Call Example

```js
  // by default index.js provides SurfaceView
  import SurfaceView, { HMSLensEngine } from '@hmscore/react-native-hms-ml';  

  // this method uses HMSSufrfaceView as camera preview.
  async runWithView() {
    try {
      var result = await HMSLensEngine.runWithView();
      console.log(result);
    } catch (error) {
      console.log(error);
    }
  }

   render() {
    return (
      // other code
        <SurfaceView 
          style={{ width: 300, height: 300, alignSelf: 'center' }} 
        />
      // other code
    );
   }
```

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

### Permissions

To use ML Plugin you need to dynamically get permissions from user. ML Plugin uses the given permissions below by default. 

```xml
<manifest ...>
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
</manifest>
```

---

## 5. Sample Project

This plugin includes a demo project in the [example](example) folder, there you can find more usage examples.

**NOTE THAT :**  Before running sample project please set your **api key** in setApiKey method under **example > src > HmsOtherServices > Helper.js** .

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
