# React-Native HMS AR

---

## Contents

  - [1. Introduction](#1-introduction)
  - [2. Installation Guide](#2-installation-guide)
    - [Integrating React Native AR Plugin](#integrating-react-native-ar-plugin)
  - [3. API Reference](#3-api-reference)
    - [ARView](#arview)
    - [HmsARModule](#hmsarmodule)
  - [4. Configuration and Description](#4-configuration-and-description)
  - [5. Sample Project](#5-sample-project)
  - [6. Questions or Issues](#6-questions-or-issues)
  - [7. Licensing and Terms](#7-licensing-and-terms)

---

## 1. Introduction

This module enables communication between Huawei AREngine SDK and React Native platform. It exposes all functionality provided by Huawei AREngine SDK.

---

## 2. Installation Guide

### Integrating React Native AR Plugin

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
    google()
    jcenter()
    maven { url 'https://developer.huawei.com/repo/' }
  }
}
```

**Step 9:** Set your package name in **defaultConfig** > **applicationId** and set **minSdkVersion** to **26** or **higher**.

- Package name must match with the **package_name** entry in **agconnect-services.json** file.

```groovy
defaultConfig {
  applicationId "<package_name>"
  minSdkVersion {{your_min version}}
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
npm i @hmscore/react-native-hms-ar
```

**Step 2:**  Run your project.

- Run the following command to the project directory.

```bash
react-native run-android  
```

#### Download Link
To integrate the plugin, follow the steps below:

**Step 1:** Download the React Native AR Plugin and place **react-native-hms-ar** under **node_modules/@hmscore** of your React Native project, as shown in the directory tree below.

            project.dir
                |_ node_modules
                    |_ @hmscore
                        |_ react-native-hms-ar
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
    implementation project(":react-native-hms-ar")
    ...
  }
}
```

**Step 3:** Add the following lines to the android/settings.gradle file in your project:

```groovy
include ':app'
include ':react-native-hms-ar'
project(':react-native-hms-ar').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-ar/android')
```

**Step 4:**  Open the your application class and add the HmsARPackage

Import the following classes to the MainApplication.java file of your project.import **com.huawei.hms.rn.HmsARPackage**.

Then, add the **HmsARPackage** to your **getPackages** method. In the end, your file will be similar to the following:

```java 
import com.huawei.hms.rn.ar.HmsARPackage;

@Override
protected List<ReactPackage> getPackages() {
    List<ReactPackage> packages = new PackageList(this).getPackages();
    packages.add(new HmsARPackage());
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

### ARView

React component that shows AR View.

#### Props

| Prop        | Required | Type           | Description                                                         |
| ----------- | -------- | -------------- | ------------------------------------------------------------------- |
| onDrawFrame | no       | `function`     | Register a callback method on gl surface view's onDrawFrame method. |
| config      | yes      | [ARViewConfig](#arviewconfig) | Set config to the ARView Component.                                 |

##### ARViewConfig

The first attribute of ARViewConfig determines which AR view will be shown.

- If config has a key as `hand` and `ARHandConfig` type object as value, ARHand view will show.
- If config has a key as `face` and `ARFaceConfig` type object as value, ARFace view will show.
- If config has a key as `body` and `ARBodyConfig` type object as value, ARBody view will show.
- If config has a key as `world` and `ARWorldConfig` type object as value, ARWorld view will show.

 > Do not set 2 attributes at the same time ARViewConfig will ignore others.

| Entity | Type            | Description                                                 |
| ------ | --------------- | ----------------------------------------------------------- |
| hand   | [ARHandConfig](#arhandconfig)  | Set configurations of AR Hand View.                         |
| face   | [ARFaceConfig](#arfaceconfig)  | Set configurations of AR Face View.                         |
| body   | [ARBodyConfig](#arbodyconfig)  | Set configurations of AR Body View.                         |
| world  | [ARWorldConfig](#arworldconfig) | Set configurations of AR World View.                        |

##### ARHandConfig

| Entity    | Type        | Description                                                                     |
| --------- | ----------- | ------------------------------------------------------------------------------- |
| drawBox   | `boolean`   | If set to true a box will be displayed around hand when hand gestures captured. |
| boxColor  | `ColorRGBA` | Color of hand box.                                                              |
| lineWidth | `number`    | Line width of hand box.                                                         |

###### Example Usage

```js
import ARView from "@hmscore/react-native-hms-ar";

<ARView
  onDrawFrame={(e) => {
    console.log(e);
  }}
  config={config}
/>

const config = {
  hand: {
    boxColor: {
      red: 255,
      blue: 255,
      green: 128,
      alpha: 192,
    },
    lineWidth: 10,
    drawBox: true,
  },
}
```

##### ARFaceConfig

| Entity      | Type        | Description                                                 |
| ----------- | ----------- | ----------------------------------------------------------- |
| drawFace    | `boolean`   | Boolean value to determine draw face function availability. |
| pointSize   | `number`    | Set point size of face points.                              |
| texturePath | `string`    | A texture asset name that will be shown on face.            |
| depthColor  | `ColorRGBA` | Set color of points.                                        |

###### Example Usage

```js
import ARView from "@hmscore/react-native-hms-ar";

<ARView
  onDrawFrame={(e) => {
    console.log(e);
  }}
  config={config}
/>

const config = {
  face: {
    drawFace: true,
    pointSize: 0,
    depthColor: {
      red: 0,
      blue: 255,
      green: 255,
      alpha: 0,
    },
    texturePath: "Solid_red.png",
  },
};
```

##### ARBodyConfig

| Entity     | Type        | Description                                                                  |
| ---------- | ----------- | ---------------------------------------------------------------------------- |
| drawLine   | `boolean`   | If set to true skeleton connection lines will be drawn when person detected. |
| drawPoint  | `boolean`   | If set to true skeleton connection point will be drawn when person detected. |
| lineWidth  | `number`    | Line width of skeleton connection lines.                                     |
| pointSize  | `number`    | Point size of skeleton connection points.                                    |
| lineColor  | `ColorRGBA` | Skeleton connection line color.                                              |
| pointColor | `ColorRGBA` | Skeleton connection point color.                                             |

###### Example Usage

```js
import ARView from "@hmscore/react-native-hms-ar";

<ARView
  onDrawFrame={(e) => {
    console.log(e);
  }}
  config={config}
/>

const config = {
  body: {
    drawLine: true,
    drawPoint: true,
    lineWidth: 10,
    pointSize: 10,
    lineColor: {
      red: 255,
      blue: 128,
      green: 128,
      alpha: 192,
    },
    pointColor: {
      red: 255,
      blue: 128,
      green: 255,
      alpha: 192,
    },
  },
};
```

##### ARWorldConfig

| Entity        | Type          | Description                                                                           |
| ------------- | ------------- | ------------------------------------------------------------------------------------- |
| objectName    | `string`      | Virtual object file path. When object file given you can put that object to ar scene. |
| objectTexture | `string`      | Virtual object texture file path.                                                     |
| showPlanes    | `boolean`     | If set to true label will be drawn when plane detected.                               |
| planeOther    | `planeConfig` | Config for other plane type detected.                                                 |
| planeWall     | `planeConfig` | Config for wall detected.                                                             |
| planeFloor    | `planeConfig` | Config for floor detected.                                                            |
| planeSeat     | `planeConfig` | Config for seat detected.                                                             |
| planeTable    | `planeConfig` | Config for table detected.                                                            |
| planeCeiling  | `planeConfig` | Config for ceiling detected.                                                          |

###### Example Usage

```js
import ARView from "@hmscore/react-native-hms-ar";

<ARView
  onDrawFrame={(e) => {
    console.log(e);
  }}
  config={config}
/>

const config = {
  world: {
    objectName: "deer.obj",
    objectTexture: "Diffuse.png",
    showPlanes: true,
    planeOther: {
      text: "Hey",
      red: 255,
      blue: 255,
      green: 0,
      alpha: 255,
    },
    planeWall: {
      text: "Wall",
      red: 255,
      blue: 255,
      green: 0,
      alpha: 255,
    },
    planeFloor: {
      image: "floor.png",
    },
    planeSeat: {
      image: "seat.png",
    },
    planeTable: {
      image: "table.png",
    },
    planeCeiling: {
      image: "ceiling.png",
    },
  },
};
```

##### planeConfig

| Entity | Type     | Description                                             |
| ------ | -------- | ------------------------------------------------------- |
| red    | `number` | Red color range between 0-255.                          |
| green  | `number` | Green color range between 0-255.                        |
| blue   | `number` | Blue color range between 0-255.                         |
| alpha  | `number` | Alpha.                                                  |
| text   | `string` | Text when plane detected.                               |
| image  | `string` | Image when plane detected. Disables text when set.      |

##### ColorRGBA

| Entity | Type     | Description                      |
| ------ | -------- | -------------------------------- |
| red    | `number` | Red color range between 0-255.   |
| green  | `number` | Green color range between 0-255. |
| blue   | `number` | Blue color range between 0-255.  |
| alpha  | `number` | Alpha.                           |

##### onDrawFrame( (e) => {} )
This method returns the data of ARView.

###### Parameters

None.

###### Call Example

```javascript
    <ARView
      onDrawFrame={(e) => console.log(e)}
    />
```

###### ARHand

| Field                    | Type                                            | Description                                                                                                                                                                                                                           |
| ------------------------ | ----------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| gestureType              | `number`                                        | Different static gestures can be identified based on whether deep flow inspection is enabled.                                                                                                                                         |
| arHandType               | [HandType](#handtype)[]                   | The hand type, which can left hand, right hand, or unknown (if left/right hand recognition is not supported).                                                                                                                         |
| anchors                  | [Anchors](#anchors)[]                         | Represents an anchor at a fixed location and a specified direction in an actual environment. HUAWEI AR Engine continuously updates this value so that the location and direction remains unchanged even when the environment changes. |
| gestureHandBox           | `number[]`                                      | The rectangle that covers the hand, in the format of [x0,y0,0,x1,y1,0]. (x0,y0) indicates the upper left corner, (x1,y1) indicates the lower right corner, and x/y is based on the OpenGL NDC coordinate system.                      |
| gestureCenter            | `number[]`                                      | Coordinates of the center point of a hand in the format of [x,y,0]. The point is the center coordinates of the bounding rectangle of the hand.                                                                                        |
| handSkeletonArray        | `number[]`                                      | The coordinates of a hand skeleton point in the format of [x0,y0,z0,x1,y1,z1, ...].                                                                                                                                                   |
| handSkeletonConnection   | `number[]`                                      | The connection data of a hand skeleton point, which is the index of the skeleton point type.                                                                                                                                          |
| arHandSkeletonTypes      | [HandSkeletonType](#handskeletontype)[]   | The list of hand skeleton point types.                                                                                                                                                                                                |
| gestureCoordinateSystem  | `any`                                           | Coordinate type used by the current gesture posture. For example, COORDINATE_SYSTEM_TYPE_2D_IMAGE indicates the OpenGL NDC coordinate system.                                                                                         |
| skeletonCoordinateSystem | `any`                                           | Coordinate system of the hand skeleton data: COORDINATE_SYSTEM_TYPE_2D_IMAGE indicates 2D hand tracking, and COORDINATE_SYSTEM_TYPE_3D_CAMERA indicates 3D hand tracking.                                                             |
| trackingState            | [TrackingState](#trackingstate)[]             | Status of the current trackable.                                                                                                                                                                                                      |

###### ARPlane

| Field         | Type                                 | Description                                                                                                                                                       |
| ------------- | ------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| centerPose    | [ARPose](#arpose)[]                | The pose transformed from the local coordinate system of a plane to the world coordinate system.                                                                  |
| extentX       | `number`                             | The length of the plane's bounding rectangle measured along the local X-axis of the coordinate system centered on the plane, such as the width of the rectangle.  |
| extentZ       | `number`                             | The length of the plane's bounding rectangle measured along the local Z-axis of the coordinate system centered on the plane, such as the height of the rectangle. |
| planePolygon  | `number[]`                           | The 2D vertices of the detected plane, in the format of [x1, z1, x2, z2, ...].                                                                                    |
| label         | [PlaneLabel](#planelabel)[]                  | The semantic type of the current plane, such as desktop or floor.                                                                                                 |
| type          | [PlaneType](#planetype)[]                    | The plane type.                                                                                                                                                   |
| trackingState | [TrackingState](#trackingstate)[]  | Status of the current trackable.                                                                                                                                  |

###### ARFace

| Field             | Type                                      | Description                                                                                                                                                                                                                                                                    |
| ----------------- | ----------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| anchors           | [Anchors](#anchors)[]                   | Represents an anchor at a fixed location and a specified direction in an actual environment. HUAWEI AR Engine continuously updates this value so that the location and direction remains unchanged even when the environment changes.                                          |
| arFaceBlendShapes | [ARFaceBlendShapes](#arfaceblendshapes) | The facial blend shapes, which contain several expression parameters.                                                                                                                                                                                                          |
| pose              | [ARPose](#arpose)[]                     | The pose of a face mesh center in the camera coordinate system, a right-handed coordinate system. The origin is located at the nose tip, the center of a face, X+ points leftwards, Y+ points up, and Z+ points from the inside to the outside with the face as the reference. |
| trackingState     | [TrackingState](#trackingstate)[]       | Status of the current trackable.                                                                                                                                                                                                                                               |

###### ARBody

| Field                  | Type                                              | Description                                                                                                                                                                                                                           |
| ---------------------- | ------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| bodyAction             | `number`                                          | Body action type, including six preset static postures.                                                                                                                                                                               |
| anchors                | [Anchors](#anchors)[]                           | Represents an anchor at a fixed location and a specified direction in an actual environment. HUAWEI AR Engine continuously updates this value so that the location and direction remains unchanged even when the environment changes. |
| bodySkeletonTypes      | [BodySkeletonType](#BodySkeletonType)[]         | An array of body skeleton types, including the head, neck, left shoulder, and right shoulder.                                                                                                                                         |
| skeletonPoint2D        | `number[]`                                        | The 2D coordinates of a skeleton point measured with the screen center as the origin, in the format of [x0,y0,0,x1,y1,0]. The coordinates are normalized device coordinates (NDC) of OpenGL, and the value range of x/y is [-1,1].    |
| skeletonPoint3D        | `number[]`                                        | The 3D coordinates of a skeleton point, in the format of [x0,y0,z0,x1,y1,z1...].                                                                                                                                                      |
| skeletonConfidince     | `number[]`                                        | The confidence of each skeleton point. The number of values are the same as that of skeleton points, and each element indicates the confidence within the range [0,1].                                                                |
| bodySkeletonConnection | `number[]`                                        | The connection data of a skeleton point, which is the index of the skeleton point type.                                                                                                                                               |
| skeletonPointIsExist2D | `number[]`                                        | 0 or 1 to indicate that whether the 2D coordinates of a skeleton point are valid. The value 0 indicates that the data is invalid, while 1 indicates that the data is valid.                                                           |
| skeletonPointIsExist3D | `number[]`                                        | 0 or 1 to indicate that whether the 3D coordinates of a skeleton point are valid. The value 0 indicates that the data is invalid, while 1 indicates that the data is valid.                                                           |
| coordinateSystemType   | [CoordinateSystemType](#coordinatesystemtype)[] | The coordinate system type.                                                                                                                                                                                                           |
| trackingState          | [TrackingState](#trackingstate)[]               | Status of the current trackable.                                                                                                                                                                                                      |

###### Anchor

| Field         | Type     | Description                                                                       |
| ------------- | -------- | --------------------------------------------------------------------------------- |
| pose          | `ARPose` | Returns the location and pose of the anchor point in the world coordinate system. |
| trackingState | `number` | Status of the current trackable.                                                  |

###### ARFaceBlendShapes

| Field             | Type       | Description                                                                                                                                                 |
| ----------------- | ---------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| blendShapeCount   | `number`   | Number of blend shapes.                                                                                                                                     |
| blendShapeData    | `number[]` | The value ranges from 0 to 1, indicating the expression degree of each blend shape.                                                                         |
| blendShapeType    | `string[]` | An array of blend shape types.                                                                                                                              |
| blendShapeDataMap | `any`      | Key indicates the string corresponding to the blend shape enumeration type, and the value indicates the blend shape expression degree, ranging from [0, 1]. |

###### ARPose

| Field       | Type       | Description                                                                                      |
| ----------- | ---------- | ------------------------------------------------------------------------------------------------ |
| translation | `number[]` | Indicates the translation from the destination coordinate system to the local coordinate system. |
| rotation    | `number[]` | Indicates the rotation variable, which is a Hamilton quaternion.                                 |

### HmsARModule

#### Public Method Summary

| Method                      | Return Type        | Description                           |
| --------------------------- | ------------------ | ------------------------------------- |
| isAREngineReady()           | `Promise<boolean>` | Check if ar engine service apk ready. |
| navigateToAppMarket()       | `void`             | Opens AR Engine App market page.      |
| enableLogger()              | `void`             | Enables HMS Plugin Method Analytics.  |
| disableLogger()             | `void`             | Disables HMS Plugin Method Analytics. |

##### HmsARModule.isAREngineReady()
Checks whether AR Engine Service APK is installed on the device.

###### Parameters

None.

###### Call Example

```javascript
    import {HmsARModule} from "@hmscore/react-native-hms-ar";

    HmsARModule.isAREngineReady().then((response) => {
      console.log(response)
    });
```

##### HmsARModule.navigateToAppMarket()
Navigates user to AR Engine App Market.

###### Parameters

None.

###### Call Example

```javascript
    import {HmsARModule} from "@hmscore/react-native-hms-ar";

    HmsARModule.navigateToAppMarket();
```

##### HmsARModule.enableLogger()
This method enables HMSLogger capability which is used for sending usage analytics of AR SDK's methods to improve the service quality.

###### Parameters

None.

###### Call Example

```javascript
    import {HmsARModule} from "@hmscore/react-native-hms-ar";

    HmsARModule.enableLogger();
```

##### HmsARModule.disableLogger()
This method disables HMSLogger capability which is used for sending usage analytics of AR SDK's methods to improve the service quality.

###### Parameters

None.

###### Call Example

```javascript
    import {HmsARModule} from "@hmscore/react-native-hms-ar";

    HmsARModule.disableLogger();
```

#### Constants

##### TrackingState

| Value  | Type     | Field                            | Description      |
| ------ | -------- | -------------------------------- | ---------------- |
| 1      | `number` | TRACKING                         | Tracking status. |
| 2      | `number` | PAUSED                           | Paused status.   |
| 3      | `number` | STOPPED                          | Stopped status.  |

##### HandType

| Value  | Type     | Field                            | Description      |
| ------ | -------- | -------------------------------- | ---------------- |
| 0      | `number` | AR_HAND_UNKNOWN                   | Unknown or the hand type cannot be distinguished. |
| 1      | `number` | AR_HAND_RIGHT                    | Right hand. |
| 2      | `number` | AR_HAND_LEFT                     | Left Hand. |

##### HandSkeletonType

| Value  | Type     | Field                            | Description      |
| ------ | -------- | -------------------------------- | ---------------- |
| 1      | `number` | ROOT               | The root point of the hand bone, that is, the wrist. |
| 2      | `number` | PINKY_1             | Pinky knuckle 1. |
| 3      | `number` | PINKY_2             | Pinky knuckle 2. |
| 4      | `number` | PINKY_3             | Pinky knuckle 3. |
| 5      | `number` | PINKY_4             | Pinky finger tip. |
| 6      | `number` | RING_1              | Ring finger knuckle 1.|
| 7      | `number` | RING_2              | Ring finger knuckle 2. |
| 8      | `number` | RING_3              | Ring finger knuckle 3. |
| 9      | `number` | RING_4              | Ring finger tip. |
| 10     | `number` | MIDDLE_1            | Middle finger knuckle 1. |
| 11     | `number` | MIDDLE_2            | Middle finger knuckle 1. |
| 12     | `number` | MIDDLE_3            | Middle finger knuckle 1. |
| 13     | `number` | MIDDLE_4            | Middle tip. |
| 14     | `number` | INDEX_1             | Index finger knuckle 1. |
| 15     | `number` | INDEX_2             | Index finger knuckle 2. |
| 16     | `number` | INDEX_3             | Index finger knuckle 3. |
| 17     | `number` | INDEX_4             | Index finger tip. |
| 18     | `number` | THUMB_1             | Thumb knuckle 1. |
| 19     | `number` | THUMB_2             | Thumb knuckle 2. |
| 20     | `number` | THUMB_3             | Thumb knuckle 3. |
| 21     | `number` | THUMB_4             | Thumb tip. |
| 22     | `number` | LENGTH              | Number of knuckles. |

##### PlaneLabel

| Value  | Type     | Field                            | Description      |
| ------ | -------- | -------------------------------- | ---------------- |
| 0      | `number` | PLANE_OTHER                      | Others. |
| 1      | `number` | PLANE_WALL                       | Wall. |
| 2      | `number` | PLANE_FLOOR                      | Floor. |
| 3      | `number` | PLANE_SEAT                       | Seat. |
| 4      | `number` | PLANE_TABLE                      | Table. |
| 5      | `number` | PLANE_CEILING                    | Ceiling. |

##### PlaneType

| Value  | Type     | Field                            | Description      |
| ------ | -------- | -------------------------------- | ---------------- |
| 0      | `number` | HORIZONTAL_UPWARD_FACING         | A horizontal plane facing up (such as the ground and desk platform). |
| 1      | `number` | HORIZONTAL_DOWNWARD_FACING       | A horizontal plane facing down (such as the ceiling). |
| 2      | `number` | VERTICAL_FACING                  | A vertical plane. |
| 3      | `number` | UNKNOWN_FACING                   | Unsupported type. |

###### BodySkeletonType

| Value  | Type     | Field                            | Description      |
| ------ | -------- | -------------------------------- | ---------------- |
| 1      | `number` | HEAD                | Head. |
| 2      | `number` | NECK                | Neck. |
| 3      | `number` | R_SHO               | Right shoulder. |
| 4      | `number` | R_ELBOW             | Right elbow. |
| 5      | `number` | R_WRIST             | Right wrist. |
| 6      | `number` | L_SHO               | Left shoulder. |
| 7      | `number` | L_ELBOW             | Left elbow. |
| 8      | `number` | L_WRIST             | Left wrist. |
| 9      | `number` | R_HIP               | Right hip. |
| 10     | `number` | R_KNEE              | Right knee. |
| 11     | `number` | R_ANKLE             | Right ankle. |
| 12     | `number` | L_HIP               | Left hip joint. |
| 13     | `number` | L_KNEE              | Left knee. |
| 14     | `number` | L_ANKLE             | Left ankle. |
| 15     | `number` | HIP_MID             | Center of hip joint. |
| 16     | `number` | R_EAR               | Right ear. |
| 17     | `number` | R_EYE               | Right eye. |
| 18     | `number` | NOSE                | Nose. |
| 19     | `number` | L_EYE               | Left eye. |
| 20     | `number` | L_EAR               | Left eyer. |
| 21     | `number` | SPINE               | Spine. |
| 22     | `number` | R_TOE               | Right toe. |
| 23     | `number` | L_TOE               | Left toe. |
| 24     | `number` | LENGTH              | Number of joints, instead of a joint point. |

##### CoordinateSystemType

| Value  | Type     | Field                            | Description      |
| ------ | -------- | -------------------------------- | ---------------- |
| 0      | `number` | UNKNOWN   | Unknown coordinate system. |
| 1      | `number` | 3D_WORLD  | World coordinate system. |
| 2      | `number` | 3D_SELF   | Local coordinate system. |
| 3      | `number` | 2D_IMAGE  | OpenGL NDC coordinate system. |
| 4      | `number` | 3D_CAMERA | Camera coordinate system. |

---

## 4. Configuration and Description

No.

---

## 5. Sample Project

This plugin includes a demo project in the [example](example) folder, there you can find more usage examples.

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
