# react-native-hms-site

## Contents
1. Introduction
2. Installation Guide
3. Function Definitions
4. Configuration & Description
5. Licencing & Terms

## 1. Intruduction

This module enables communication between Huawei Site SDK and React Native platform. It exposes all functionality provided by Huawei Site SDK.

## 2. Installation Guide

- Download the module and copy it into 'node_modules' folder. The folder structure can be seen below;

```
project-name
    |_ node_modules
        |_ ...
        |_ react-native-hms-site
        |_ ...
```
- Add following lines into 'android/settings.gradle' file
```groovy
include ':react-native-hms-site'
project(':react-native-hms-site').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-hms-site/android')
```

- Add maven repository address and AppGallery Connect service dependencies into 'android/build.gradle' file.

```groovy
maven {url 'https://developer.huawei.com/repo/'}
classpath 'com.huawei.agconnect:agcp:1.2.1.301'
```

- Add AppGallery Connect plugin and 'react-native-hms-site' dependency into 'android/app/build.gradle' file.

```groovy
apply plugin: 'com.huawei.agconnect'
implementation project(":react-native-hms-site")
```

- Download 'agconnect-services.json' file and put it under 'android/app' folder.

- Put keystore file under 'android/app' folder. Add signing configuration into 'android/app/build.gradle' file.

```groovy
signingConfigs {
        release {
            storeFile file('<keystore>')
            storePassword '<storePassword>'
            keyAlias '<keyAlias>'
            keyPassword '<keyPassword>'
        }

        debug {
            storeFile file('<keystore>')
            storePassword '<storePassword>'
            keyAlias '<keyAlias>'
            keyPassword '<keyPassword>'
        }
    }
    buildTypes {
        debug {
            signingConfig signingConfigs.debug
        }
        release {
            signingConfig signingConfigs.release
            minifyEnabled enableProguardInReleaseBuilds
            proguardFiles getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
        }
    }
```

- Add 'RNHMSSitePackage' to your application.

```java
import com.huawei.hms.rn.site.RNHMSSitePackage;
...
...

@Override
protected List<ReactPackage> getPackages() {
  @SuppressWarnings("UnnecessaryLocalVariable")
  List<ReactPackage> packages = new PackageList(this).getPackages();
  packages.add(new RNHMSSitePackage());
  return packages;
}
```

## 3. Function Definitions

|Return Type | Function                                     |
|:-----------|:---------------------------------------------|
|Promise     | initializeService(config)                    |
|Promise     | textSearch(textSearchRequest)                |
|Promise     | detailSearch(detailSearchRequest)            |
|Promise     | querySuggestion(querySuggestionRequest)      | 
|Promise     | nearbySearch(nearbySearchRequest)            |

## 3. Confuguration & Description
No.

## 4. Licencing & Terms
Apache 2.0 license.


