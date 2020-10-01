# react-native-hms-scan-demo

# Contents

1. Introduction
2. Installation Guide
3. Function Definitions
4. Configuration & Description
5. Licencing & Terms

## 1. Intruduction

This module enables communication between Huawei Scan SDK and React Native platform. It exposes all functionality provided by Huawei Scan SDK.

## 2. Installation Guide

- In order to able the library to be used in the demo, the library should be copied under the node_modules folder of the project.

The structure should be like this

            hms-scan-demo
                |_ node_modules
                    |_ ...
                        react-native-hms-scan
                        ...

- Add following lines into 'android/settings.gradle' file

```gradle
include ':react-native-hms-scan'
project(':react-native-hms-scan').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-hms-scan/android')
```

- Add maven repository address and AppGallery Connect service dependencies into 'android/build.gradle' file.

```groovy
maven {url 'https://developer.huawei.com/repo/'}
```

- Add 'react-native-hms-scan' dependency into 'android/app/build.gradle' file.

```groovy
  implementation project(":react-native-hms-scan")
```

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

- Add 'RNHMSScanPackage' to your application.
  
```java
import com.huawei.hms.rn.scan.RNHMSScanPackage;
...
...

@Override
protected List<ReactPackage> getPackages() {
  @SuppressWarnings("UnnecessaryLocalVariable")
  List<ReactPackage> packages = new PackageList(this).getPackages();
  packages.add(new RNHMSScanPackage());
  return packages;
}
```

```bash
react-native run-android
```

## 3. Function Definitions

No

## 4. Confuguration & Description

No.

## 5. Licencing & Terms

Apache 2.0 license.
