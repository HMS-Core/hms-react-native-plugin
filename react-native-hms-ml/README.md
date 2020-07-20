# react-native-hms-ml

## Contents
- Introduction
- Installation Guide
- React-Native SDK Method Definition
- Configuration Description
- Licensing and Terms

## 1. Introduction

This package includes core layer which encapsulates Huawei ML sdk and a demo that shows usage of it, also provides APIs for your reference or use.

This package is described as follows:

- android: core layer, bridging ML SDK bottom-layer code;
- example: demo application that shows examples about usage of the core layer
- index.js: module definition layer, which is used to define interface classes or reference files.

## 2. Installation Guide
Before using Reactive-Native SDK code, ensure that the RN development environment has been installed.

### 2.1 Run Demo Project

In order to be able to use library in the demo, the library should be copied under the node_modules folder of the project.

#### 2.1.1 Install Dependencies
Go to example directory
```bash
cd example/
```
Install
```bash
npm i
```

#### 2.1.2 Copy Library

```bash
npm run react-native-hms-ml
```

After copy operation, the structure should be like this

    example
        |_ node_modules
            |_ ...
            |_ react-native-hms-ml
            |_ ...

#### 2.1.3 Keystore and Agconnect

Go to 'example/android/app/build.gradle' and replace package_name with yours (in this case it is demo application's package_name) and replace signingConfigs with appropriate parameters. 
```gradle
...
defaultConfig {
    applicationId "<package_name>"
    minSdkVersion rootProject.ext.minSdkVersion
    targetSdkVersion rootProject.ext.targetSdkVersion
    versionCode 1
    versionName "1.0"
    multiDexEnabled true
}
...
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
Download 'agconnect-services.json' file and move it to 'example/android/app' folder.

#### 2.1.4 Run Demo

To run your demo application you can use one of the following commands.

```bash
npx react-native run-android
npm run android
react-native run-android
```

### 2.2 Integrating Plugin

#### Step 1

Download the React Native ML Plugin and place the react-native-hms-ml folder to node_modules folder of your React Native project as shown in the directory tree below.

    project-dir
        |_ node_modules
            |_ ...
            |_ react-native-hms-ml
            |_ ...

#### Step 2
- Sign in to AppGallery Connect and select My apps.
- Find your App from the list, and click the link under Android App in the Mobile phone column.
- Go to Develop.
- In the App information area, Click agconnect-services.json to download the configuration file.
- Place the agconnect-services.json file to android > app folder of your React Native project.
#### Step 3
- Open build.gradle file in project-dir > android folder.
- Note : The Maven repository address cannot be accessed from a browser. It can only be configured in an IDE.
- Go to buildscript > repositories and allprojects > repositories, and configure the Maven repository address.

```gradle
buildscript {   
       repositories {   
           google()        
           jcenter()    
           maven {url 'https://developer.huawei.com/repo/'}   
       }   
}  
allprojects {      
       repositories {       
           google()        
           jcenter()       
           maven {url 'https://developer.huawei.com/repo/'}     
       }    
}
```

- Go to buildscript > dependencies and add dependency configurations.

```gradle
buildscript {  
    dependencies {  
        classpath 'com.huawei.agconnect:agcp:1.2.1.301'  
    }  
}

```
- Open settings.gradle located under project-dir > android directory  and add following lines.

```gradle
include ':react-native-hms-ml'
project(':react-native-hms-ml').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-hms-ml/android')
```

- Open build.gradle file which is located under project.dir > android > app directory.

- Add the AppGallery Connect plug-in dependency to the file header.

```gradle
apply plugin: 'com.huawei.agconnect'
```
- The apply plugin: 'com.huawei.agconnect' configuration must be added after the apply plugin: 'com.android.application' configuration.The minimum  Android API level (minSdkVersion) required for HUAWEI Site Kit is 19. Configure build dependencies.

```gradle
dependencies {    
        implementation project(":react-native-hms-ml")    
        ...   
        implementation 'com.huawei.agconnect:agconnect-core:1.0.0.301'
    }
```
- Configure the signature in project-dir > android. 
- Copy the signature file generated in Generating a Signing Certificate Fingerprint to the project-dir > android >app directory of your project and configure the signature in the build.gradle file.

```gradle
...
defaultConfig {
    applicationId "<package_name>"
    minSdkVersion rootProject.ext.minSdkVersion
    targetSdkVersion rootProject.ext.targetSdkVersion
    versionCode 1
    versionName "1.0"
    multiDexEnabled true
}
...
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
- Open project-dir > android > app > src > main > java > <package_name> >MainApplication.java file and modify the getPackages method as following.

```java
import com.huawei.hms.rn.ml.HmsMlPackage;
```
Then add the following line to your getPackages() method.
```java
packages.add(new HmsMlPackage());
```

## 3. HUAWEI ML Kit Plugin for React Native
### Summary

| Frame                   |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| fromBitmap             | Creates frame instance using bitmap uri.                                                           |
| fromFilePath             | Creates frame instance using file path uri.                                            |

| Local Text Recognition                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting for LocalTextAnalyzer.                                                           |
| getLanguage             | Returns the langugage setting.                                            |
| getOCRMode                | Returns the OCR mode setting.                                                     |
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| analyze    | Sets LocalTextAnalyzer, starts the analyze operation by using current Frame and returns the result.                                               |
| close | Closes LocalTextAnalyzer.              |
| getAnalyzeType | Returns analyze type.   |
| isAvailable | Returns analyzer status.    | 

| Remote Text Recognition                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting for RemoteTextAnalyzer.                                                           |
| getBorderType             | Returns the border type setting.                                            |
| getLanguageList                | Returns the language list setting.     
| getDensityScene | Returns density scene setting.                                                     |
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| analyze    | Sets RemoteTextAnalyzer, starts the analyze operation and returns the result.                                               |
| close | Closes RemoteTextAnalyzer.              
| getAnalyzeType | Returns analyze type.   
| isAvailable | Returns analyzer status.     

| Translate                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting for translator.                                                           |
| translate             | Creates the translator, translates given text and returns the result.                                            |
| stop                | Stops the translator.     
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| getSourceLanguageCode    | Returns the source language code setting.                                               |
| getTargetLanguageCode | Returns the target language code setting.        

| Text to Speech                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| configure             | Creates configuration for Text to Speech Engine.                                                           |
| updateConfig             | Updates current configuration.                                                           |
| getLanguage             | Returns the language configuration.                                                           |
| getPerson             | Returns the person configuration.                                                           |
| getSpeed             | Returns the speed configuration.                                                           |
| getVolume             | Returns the volume configuration.                                                           |
| createEngine             | Creates Text to Speech Engine .                                            |
| speak                | Starts the speech.     
| resume          | Resumes the speech.                                                           |
| pause          | Pauses the speech. |
| stop    | Stops the speech.                                               |
| setTtsCallback | Creates an event for Speech Engine.       |
| shutdown | Shutdowns the Speech Engine.        

| Product Vision                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting for Product Vision Search Analyzer.                                                           |
| analyze             | Creates the analyzer, analyzes the current frame, returns the result.                                            |
| stop                | Stops the analyzer.     
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| getLargestNumOfReturns    | Returns the source largest number of return setting.                                                   

| Object Recognition                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting for Object Recognition Analyzer.                                                           |
| analyze             | Creates the analyzer, analyzes the current frame, returns the result.                                            |
| stop                | Stops the analyzer.     
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| getAnalyzerType    | Returns the analyzer type setting.                                               |
| isClassificationAllowed    | Returns classification state setting.                                    |
| isMultipleResultsAllowed    | Returns multiple result state setting.         

| Language Detection                     |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting for Language Recognition Analyzer.                                                           |
| firstBestDetect             | Starts analyzer for first best detect.                                            |
| probabilityDetect             | Starts analyzer for probability detect.                                            |
| stop                | Stops the analyzer.     
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| getTrustedThreshold    | Returns the thrusted threshold setting.                                               

| Landmark Recognition                     |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting for Landmark Recognition Analyzer.                                                           |
| analyze             | Creates the analyzer, analyzes the frame, returns the result.                                            |
| close                | Closes the analyzer.     
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| getLargestNumOfReturns    | Returns the largest number of return setting.                |
| getPatternType    | Returns the pattern type setting.   

| Image Segmentation                     |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting for Image Segmentation Analyzer.                                                           |
| analyze             | Creates the analyzer, analyzes the frame, returns the result.                                            |
| stop                | Stops the analyzer.     
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| getAnalyzerType    | Returns the analyzer type setting.                |
| getScene    | Returns the scene setting.                          |
| isExact    | Returns the exact status setting.                          

| Face Recognition                     |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting for Face Recognition Analyzer.                                                           |
| analyze             | Creates the analyzer, analyzes the frame, returns the result.                                            |
| stop                | Stops the analyzer.     
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| maxSizeFaceTransactorCreator    | Creates the max size face transactor.                |
| addTransactor    | Adds the created max size face transactor.                |
| isAvailable    | Returns the analyzer available status.                |
| getFeatureType    | Returns the feature type setting.                |
| getKeyPointType    | Returns the keypoint type setting.                          |
| getPerformanceType    | Returns the performance type setting.                          |
| getShapeType    | Returns the shape type setting.                          |
| isMaxSizeFaceOnly    | Returns the max size face only status.                          |
| isTracingAllowed    | Returns the tracing allowed status.                          |
| getMinFaceProportion    | Returns the minimum face proportion setting.                        

| Document Recognition                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting.                                                           |
| getLanguageList             | Returns the langugage list setting.                                            |
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| analyze    | Sets DocumentAnalyzer, starts the analyze operation by using current Frame and returns the result.                                               |
| close | Closes LocalTextAnalyzer.              |
| getBorderType | Returns border type setting.   |
| isEnableFingerPrintVerificaiton | Returns fingerprint status setting.     

| Local Image Classification                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting.                                                           |
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| analyze    | Sets Image Classification Analyzer, starts the analyze operation by using current Frame and returns the result.                                               |
| stop | Stops Image Classification Analyzer.              |
| getMinAcceptablePossibility | Returns minimum acceptable possibility setting.   

| Remote Image Classification                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting.                                                           |
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| analyze    | Sets Image Classification Analyzer, starts the analyze operation by using current Frame and returns the result.                                               |
| stop | Stops Image Classification Analyzer.              |
| getMinAcceptablePossibility | Returns minimum acceptable possibility setting.   |
| getLargestNumOfReturns | Returns largest number of return setting.   

| Bank Card Recognition                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting.                                                           |
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| analyze    | Sets Bank Card Recognition Analyzer, starts the analyze operation by using current Frame and returns the result.                                               |
| stop | Stops Bank Card Recognition Analyzer.              |
| getLanguageType | Returns language type setting.   

| Id Card Recognition                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| create             | Creates the analyzer setting.                                                           |
| equals          | Checks given setting is equal to current one.                                                           |
| hashCode          | Returns hashCode. |
| analyze    | Sets Id Card Recognition Analyzer, starts the analyze operation by using current Frame and returns the result.                                               |
| stop | Stops Id  Card Recognition Analyzer.              |
| getCountryCode | Returns country code setting.   |
| getSideType | Returns side type setting.   

## 4. Configure parameters.    
No.
## 5. Licensing and Terms  
Huawei Reactive-Native SDK uses the Apache 2.0 license.
