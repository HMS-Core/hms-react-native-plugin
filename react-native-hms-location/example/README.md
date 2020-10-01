# react-native-hms-location-demo

## Contents

- Introduction
- Installation Guide
- React-Native Example Method Definition
- Configuration Description
- Licensing and Terms

## 1. Introduction

The demo project is an example that aims to demonstrate how the HUAWEI Location Kit SDK for React Native can be used.

The React-Native SDK code encapsulates the Huawei location client interface. It provides many APIs for your reference or use.

The React-Native SDK code package is described as follows:

- Android: core layer, bridging Location SDK bottom-layer code;
- src/modules: Android interface layer, which is used to parse the received data, send requests and generate class instances.
- index.js: external interface definition layer, which is used to define interface classes or reference files.

## 2. Installation Guide

Before using Reactive-Native SDK code, ensure that the RN development environment has been installed.

### 2.1 Pre-requisites

Node.JS ( and npm ) should be installed.<br/>
Android SDK should be installed. ( Preferrably Android Studio )<br/>
Platform-tools and build-tools of the Android SDK should be added to the $PATH variable.
Developer options should be enabled on your phone, and debug option should be enabled.

<b>In order for Geofence feature to work, HMS-product-release-4.0.2.301.apk (HMS Core) should be installed on your device</b>

### 2.2 Installing the dependencies

Run the following command in the root of the demo project.

```bash
npm i
```

This will install the dependent packages used by the projects.

### 2.3 Add Huawei Maven Repo

Add maven repository address into 'android/build.gradle' file.

```groovy
maven {url 'https://developer.huawei.com/repo/'}
```

### 2.4 Copy the library into the demo project

In order to able the library to be used in the demo, the library should be copied under `@hmscore` folder in the `node_modules` folder of the project.

The structure should be like this

```text
hms-location-demo
    |_ node_modules
        |_ ...
        |_ @hmscore
          |_ ...
          |_ react-native-hms-location
          |_ ...
        |_ ...
```

### 2.5 Run the project

Open a command line of your choice, and run the following command in order to run the project.

<b>Note:</b> Please make sure your phone is connected via a USB cable.

```bash
npm run android
```

## 3. React-Native Example method definition

No. Developer can flexibly develop projects based on the example code.

## 4. Configure parameters

No.

## 5. Licensing and Terms

Huawei Reactive-Native SDK uses the Apache 2.0 license.
