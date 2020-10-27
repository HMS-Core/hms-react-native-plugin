# React-Native HMS AR - Demo

---

## Contents

- [Introduction](#1-introduction)
- [Installation](#2-installation)
- [Configuration](#3-configuration)
- [Licensing and Terms](#4-licensing-and-terms)

---

## 1. Introduction

This demo project is an example to demonstrate the features of the **Huawei React-Native AR Kit** Plugin.

---

## 2. Installation

Before you get started, you must register as a HUAWEI developer and complete identity verification on the [HUAWEI Developer](https://developer.huawei.com/consumer/en/) website. For details, please refer to [Register a HUAWEI ID](https://developer.huawei.com/consumer/en/doc/10104).

- In order to able the library to be used in the demo, the library should be copied under the node_modules folder of the project.

The structure should be like this

            hms-ar-demo
                |_ node_modules
                    |_ ...
                        react-native-hms-ar
                        ...

- Add following lines into 'android/settings.gradle' file

```gradle
include ':react-native-hms-ar'
project(':react-native-hms-ar').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-hms-ar/android')
```

- Add maven repository address and AppGallery Connect service dependencies into 'android/build.gradle' file.

```groovy
maven {url 'https://developer.huawei.com/repo/'}
```

- Add 'react-native-hms-ar' dependency into 'android/app/build.gradle' file.

```groovy
  implementation project(":react-native-hms-ar")
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

- Add 'HmsARPackage' to your application.

```java
import com.huawei.hms.rn.ar.HmsARPackage;

@Override
protected List<ReactPackage> getPackages() {
  @SuppressWarnings("UnnecessaryLocalVariable")
  List<ReactPackage> packages = new PackageList(this).getPackages();
  packages.add(new HmsARPackage());
  return packages;
}
```

### Build & Run the project

Sync your project with gradle files and run the project.

```bash
react-native run-android
```

---

## 3. Configuration

No.

---

## 4. Licensing and Terms

Huawei React-Native HMS AR - Demo is licensed under [Apache 2.0 license](LICENCE)
