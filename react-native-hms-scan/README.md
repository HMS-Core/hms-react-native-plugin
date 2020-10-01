# react-native-hms-scan

## Table of contents

* [Introduction](#intruduction)
* [Installation Guide](#installation-guide)
* [API Definition](#api-definition)
* [Sample Project](#sample-project)
* [Question or Issues](#question-or-issues)
* [License and Terms](#license-and-terms)

## Intruduction

This module enables communication between Huawei Scan SDK and React Native platform. It exposes all functionality provided by Huawei Scan SDK.

## Installation Guide

### AppGallery Connect Integration

* Add maven repository address and AppGallery Connect service dependencies into 'android/build.gradle' file.

```groovy
maven {url 'https://developer.huawei.com/repo/'}
classpath 'com.huawei.agconnect:agcp:1.4.1.300'
```

* Add AppGallery Connect plugin into 'android/app/build.gradle' file.

```groovy
apply plugin: 'com.huawei.agconnect'
```

* Download 'agconnect-services.json' file and put it under 'android/app' folder.

* Put keystore file under 'android/app' folder. Add signing configuration into 'android/app/build.gradle' file.

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

### Plugin Integration

The plugin can be added to the project using npm or download link.

<details open><summary>Using Npm</summary>

```bash
npm i @hmscore/react-native-hms-scan
```

</details>

---

<details open><summary>Using Download Link</summary>

* Download the module and copy it into '@hmscore' folder in 'node_modules' folder. The folder structure can be seen below;

```text
project-name
    |_ node_modules
        |_ ...
        |_ @hmscore
          |_ ...
          |_ react-native-hms-scan
          |_ ...
        |_ ...
```

* Add following lines into 'android/settings.gradle' file

```gradle
include ':react-native-hms-scan'
project(':react-native-hms-scan').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-scan/android')
```

* Add 'react-native-hms-scan' dependency into 'android/app/build.gradle' file.

```groovy
implementation project(":react-native-hms-scan")
```

* Add 'RNHMSScanPackage' to your application.

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

</details>

Run the app by executing following command.

```bash
react-native run-android
```

## API Definition

### Modules

  <details open><summary>Details</summary>

* #### `Utils`

  * Methods
    * [`disableLogger()`](#a-utilsdisablelogger)
    * [`enableLogger()`](#b-utilsenablelogger)
    * [`decodeWithBitmap()`](#c-utilsdecodewithbitmap)
    * [`buildBitmap()`](#d-utilsbuildbitmap)
    * [`startDefaultView()`](#e-utilsstartdefaultview)

* #### `Permission`

  * Methods
    * [`hasCameraAndStoragePermission()`](#a-permissionhascameraandstoragepermission)
    * [`requestCameraAndStoragePermission()`](#b-permissionrequestcameraandstoragepermission)

* #### `MultiProcessor`

  * Methods
    * [`decodeMultiSync()`](#a-multiprocessordecodemultisync)
    * [`decodeMultiAsync()`](#b-multiprocessordecodemultiasync)
    * [`startMultiProcessorCamera()`](#c-multiprocessorstartmultiprocessorcamera)
  * Event Listeners
    * [`onMultiProcessorResponseListenerAdd()`](#a-multiprocessoronmultiprocessorresponselisteneradd)
    * [`onMultiProcessorResponseListenerRemove()`](#b-multiprocessoronmultiprocessorresponselistenerremove)
    * [`allListenersRemove()`](#c-multiprocessoralllistenersremove)

* #### `CustomizedView`

  * Methods
    * [`startCustomizedView()`](#a-customizedviewstartcustomizedview)
    * [`pauseContinuouslyScan()`](#b-customizedviewpausecontinuouslyscan)
    * [`resumeContinuouslyScan()`](#c-customizedviewresumecontinuouslyscan)
    * [`switchLight()`](#d-customizedviewswitchlight)
    * [`getLightStatus()`](#e-customizedviewgetlightstatus)
  * Event Listeners
    * [`onResponseListenerAdd()`](#a-customizedviewonresponselisteneradd)
    * [`onResponseListenerRemove()`](#b-customizedviewonresponselistenerremove)
    * [`onStartListenerAdd()`](#c-customizedviewonstartlisteneradd)
    * [`onStartListenerRemove()`](#d-customizedviewonstartlistenerremove)
    * [`onResumeListenerAdd()`](#e-customizedviewonresumelisteneradd)
    * [`onResumeListenerRemove()`](#f-customizedviewonresumelistenerremove)
    * [`onPauseListenerAdd()`](#g-customizedviewonpauselisteneradd)
    * [`onPauseListenerRemove()`](#h-customizedviewonpauselistenerremove)
    * [`onDestroyListenerAdd()`](#i-customizedviewondestroylisteneradd)
    * [`onDestroyListenerRemove()`](#j-customizedviewondestroylistenerremove)
    * [`onStopListenerAdd()`](#k-customizedviewonstoplisteneradd)
    * [`onStopListenerRemove()`](#l-customizedviewonstoplistenerremove)
    * [`allListenersRemove()`](#m-customizedviewalllistenersremove)

</details>

---

### Methods

<details open><summary>Utils Module</summary>

#### a) `Utils.disableLogger()`

Disables HMSLogger capability which is used for sending usage analytics of Scan SDK's methods to improve the service quality.

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';
//Call disableLogger API.
ScanPlugin.Utils.disableLogger()
```

---

#### b) `Utils.enableLogger()`

Enables HMSLogger capability which is used for sending usage analytics of Scan SDK's methods to improve the service quality.

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';
//Call enableLogger API.
ScanPlugin.Utils.enableLogger()
```

---

#### c) `Utils.decodeWithBitmap()`

Decodes bitmap and returns a promise that resolves [`ScanResponse`](#scanresponse) object.

Parameters

| Name | Type | Description |
| ---- | ---- | ----------- |
| request | [`DecodeRequest`](#decoderequest) | Request object for synchronous decoding operation. |

Return Type

| Type | Description |
| ---- | ----------- |
| `Promise<`[`ScanResponse`](#scanresponse)`>` | Promise that resolves [`ScanResponse`](#scanresponse) object if the operation is successful |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';
// String of Base64 encoded bitmap image of Aztec barcode
let base64data  = "iVBORw0KGgoAAAANSUhEUgAAALIAAACjCAYAAAAuAERWAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAANcSURBVHhe7d3BahsxFEDRpP//z22hWQR18RAaO57rczYt2GNpxhfxCKH9/P3XB9zcr68/4daETIKQSRAyCUImQcgkCJkEIZMgZBKETIKQSRAyCUImQcgkCJkEIZMgZBKETIKQSRAyCUImQcgkCJkEIZMgZBKETIKQSRAyCbf6Rww/Pz+//vbPtPX1/ZPdz1vff/X+Tr+a3f3cmROZBCGTIGQSUjPy6Ux4OuOerrc6vb/p/bvrvzInMglCJkHIJNx6Rp5cfWu7M+fu+rsz6+5+Vlc/n5/kRCZByCQImYS3npF3P2/X7vrr+0/v14wMNyNkEoRMQuo/VZ9mzsnuzDq9frXpfh59/6/MiUyCkEkQMgm3npFPZ9rJ7vW7+1k9en9lTmQShEyCkElI/Rx5tTuTTu9fTY/u0TPxZHd/d07BiUyCkEkQMgm3/n3k3RmwZr3/05n3zjOzE5kEIZMgZBLMyBue/ahO97/7fG6Uwn+cyCQImQQhk2BG/mb9vGm90xl2tbv+atrP7vXT+1+JE5kEIZMgZBLSM/Jkd8Zd1zvdz3T97uuTaX+n9/OTnMgkCJkEIZOQmpGnmXH3VnfXe/Trq6vv586cyCQImQQhk3CrGXkyzZSr05n0dOacrr96P6evvzInMglCJkHIJKRm5NU0Y+6aZspTz55Z7zwTr5zIJAiZBCGTcOsZuT5DTjP4up/SzLvLiUyCkEkQMglvNSOfzpyvZverK8/QTmQShEyCkEkwI39z9aN49nqr0xn/Tmk4kUkQMglCJiE9I5/OqLsz5u76k9P7uXr9V+ZEJkHIJAiZhLeekSe7j+Z0vV2n93vjr/4/TmQShEyCkEm49Yw8mWbGacacHs3u56+ePVOvSl+9E5kEIZMgZBLMyN/szqzT9buvP9pPr/9ITmQShEyCkElIz8ir3Zl5dXr9ZPr8Xet+Hr3/n+REJkHIJAiZhLf6feSrZ8ZnXz/dz+T0+lfmRCZByCQImYS3+jkyXU5kEoRMgpBJEDIJQiZByCQImQQhkyBkEoRMgpBJEDIJQiZByCQImQQhkyBkEoRMgpBJEDIJQiZByCQImQQhkyBkEoRMgpBJEDIJQiZByCQImQQhkyBkEoRMgpBJEDIJQiZByCQImQQhkyBkAj4+/gCO197ZKL532gAAAABJRU5ErkJggg==";
//Constructing request object.
let decodeRequest = {
    data: base64data,
    scanType: ScanPlugin.ScanType.All,
    additionalScanTypes: [],
};
//Call decodeWithBitmap API with request object.
ScanPlugin.Utils.decodeWithBitmap(decodeRequest)
  .then((res) =>console.log(res)) // ScanResponse
  .catch((e) => console.log(e))
```

---

#### d) `Utils.buildBitmap()`

Generates 1D or 2D barcodes.

Parameters

| Name | Type | Description |
| ---- | ---- | ----------- |
| request | [`BuildBitmapRequest`](#buildbitmaprequest) | Request object for building bitmaps. |

Return Type

| Type | Description |
| ---- | ----------- |
| `Promise<String>` | Promise that resolves `String` of Base64 encoded bitmap image if the operation is successful |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';
//Constructing request object.
let buildBitmapRequest = {
    content: "Hello",
    type: ScanPlugin.ScanType.All,
    width: 200,
    height: 200,
    margin: 1,
    color: -16777216, // BLACK,
    backgroundColor: -1, // WHITE,
}
//Call buildBitmap API with request object.
ScanPlugin.Utils.buildBitmap(buildBitmapRequest)
  .then((res) => console.log(res)) // String of Base64 encoded bitmap image
  .catch((e) => console.log(e))
```

---

#### e) `Utils.startDefaultView()`

Starts the barcode scanning UI of Huawei and returns a promise that resolves [`ScanResponse`](#scanresponse) object.

Parameters

| Name | Type | Description |
| ---- | ---- | ----------- |
| request | [`DefaultViewRequest`](#defaultviewrequest) | Request object for default view. |

Return Type

| Type | Description |
| ---- | ----------- |
| `Promise<`[`ScanResponse`](#scanresponse)`>` | Promise that resolves [`ScanResponse`](#scanresponse) object if the operation is successful |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';
//Constructing request object.
let defaultViewRequest = {
    scanType: ScanPlugin.ScanType.All,
    additionalScanTypes: [],
}
//Calling defaultView API with the request object.
ScanPlugin.Utils.startDefaultView(defaultViewRequest)
  .then((res) => console.log(res)) // ScanResponse  
  .catch((e) => console.log(e))
```

</details>

---

<details open><summary>Permission Module</summary>

#### a) `Permission.hasCameraAndStoragePermission()`

Checks whether your app has camera and storage permissions return a promise that resolves a boolean.

Return Type

| Type               | Description                                               |
| ------------------ | --------------------------------------------------------- |
| `Promise<boolean>` | Promise that resolves true if the permissions are granted |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';
//Call hasCameraAndStoragePermission API.
ScanPlugin.Permission.hasCameraAndStoragePermission()
    .then((res) => console.log("Has permission:", res))
```

---

#### b) `Permission.requestCameraAndStoragePermission()`

Requests camera and storage permissions for your app and return a promise that resolves a boolean.

Return Type

| Type               | Description                                               |
| ------------------ | --------------------------------------------------------- |
| `Promise<boolean>` | Promise that resolves true if the permissions are granted |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';
//Call requestCameraAndStoragePermission API.
ScanPlugin.Permission.requestCameraAndStoragePermission()
    .then((res) => console.log("Request response", res))
```

</details>

---

<details open><summary>MultiProcessor Module</summary>

#### a) `MultiProcessor.decodeMultiSync()`

Scans barcodes synchronously in MultiProcessor mode and returns a promise that resolves [`ScanResponse[]`](#scanresponse) object array.

Parameters

| Name | Type | Description |
| ---- | ---- | ----------- |
| request | [`DecodeRequest`](#decoderequest) | Request object for synchronous decoding operation. |

Return Type

| Type | Description |
| ---- | ----------- |
| `Promise<`[`ScanResponse[]`](#scanresponse)`>` | Promise that resolves [`ScanResponse`](#scanresponse) object array if the operation is successful |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';
//Constructing request object.
let decodeRequest = {
    data: "<put_base64_string_of_an_image_including_multiple_barcodes>", // String of Base64 encoded bitmap image
    scanType: ScanPlugin.ScanType.All,
    additionalScanTypes: [],
}
//Call decodeMultiSync API with request object.
ScanPlugin.MultiProcessor.decodeMultiSync(decodeRequest)
  .then((res) =>console.log(res)) // ScanResponse[]
  .catch((e) => console.log(e))
```

---

#### b) `MultiProcessor.decodeMultiAsync()`

Scans barcodes asynchronously in MultiProcessor mode and returns a promise that resolves [`ScanResponse[]`](#scanresponse) object array.

Parameters

| Name | Type | Description |
| ---- | ---- | ----------- |
| request | [`DecodeRequest`](#decoderequest) | Request object for asynchronous decoding operation. |

Return Type

| Type | Description |
| ---- | ----------- |
| `Promise<`[`ScanResponse[]`](#scanresponse)`>` | Promise that resolves [`ScanResponse`](#scanresponse) object array if the operation is successful |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';
//Constructing request object.
let decodeRequest = {
    data: "<put_base64_string_of_an_image_including_multiple_barcodes>", // String of Base64 encoded bitmap image
    scanType: ScanPlugin.ScanType.All,
    additionalScanTypes: [],
}
//Call decodeMultiAsync API with request object.
ScanPlugin.MultiProcessor.decodeMultiAsync(decodeRequest)
  .then((res) =>console.log(res)) // ScanResponse[]
  .catch((e) => console.log(e))
```

---

#### c) `MultiProcessor.startMultiProcessorCamera()`

Starts multi processor barcode scanning UI of Huawei React Native Scan Plugin and returns a promise that resolves [`ScanResponse[]`](#scanresponse) object array.

Parameters

| Name | Type | Description |
| ---- | ---- | ----------- |
| request | [`MultiCameraRequest`](#multicamerarequest) | Request object for multiprocessor camera. |

Return Type

| Type | Description |
| ---- | ----------- |
| `Promise<`[`ScanResponse[]`](#scanresponse)`>` | Promise that resolves [`ScanResponse`](#scanresponse) object array if the operation is successful |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';
//Constructing request object.
let multiCameraRequest = {
    scanMode: ScanPlugin.ScanMode.Sync,
    scanType: ScanPlugin.ScanType.All,
    additionalScanTypes: [],
    colorList: [
      -65281, // Magenta
      -65536. // Red
    ],
    strokeWidth: 4,
    scanTextOptions: {
        textColor: -1, // White
        textSize: 35,
        showText: true,
        showTextOutBounds: false,
        textBackgroundColor: -16777216, // Black
        autoSizeText: false,
        minTextSize: 24,
        granularity: 2,
    },
    isGalleryAvailable: false,
}
//Call the startMultiProcessorCamera with the request object.
ScanPlugin.MultiProcessor.startMultiProcessorCamera(multiCameraRequest)
  .then((res) =>console.log(res)) // ScanResponse[]  
  .catch((e) => console.log(e));
```

</details>

---

<details open><summary>CustomizedView Module</summary>

#### a) `CustomizedView.startCustomizedView()`

Controls views of camera preview and barcode scanning in Customized View mode and returns a promise that resolves [`ScanResponse`](#scanresponse) object.

Parameters

| Name    | Type                                              | Description                         |
| ------- | ------------------------------------------------- | ----------------------------------- |
| request | [`CustomizedViewRequest`](#customizedviewrequest) | Request object for customized view. |

Return Type

| Type               | Description                                               |
| ------------------ | --------------------------------------------------------- |
| `Promise<`[`ScanResponse`](#scanresponse)`>` | Promise that resolves [`ScanResponse`](#scanresponse) object if the operation is successful |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';
//Constructing request object.
let customizedViewRequest = {
    scanType: ScanPlugin.ScanType.All,
    additionalScanTypes: [],
    rectHeight: 200,
    rectWidth: 200,
    continuouslyScan: false,
    isFlashAvailable: false,
    flashOnLightChange: false,
    isGalleryAvailable: false,
}
//Call the customizedView API with the request object.
ScanPlugin.CustomizedView.startCustomizedView(customizedViewRequest)
  .then((res) => console.log(res)) // ScanResponse  
  .catch((e) => console.log(e));
```

---

#### b) `CustomizedView.pauseContinuouslyScan()`

Pauses barcode scanning.

Return Type

| Type               | Description                                               |
| ------------------ | --------------------------------------------------------- |
| `Promise<boolean>` | Promise that resolves true if the operation is successful |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.pauseContinuouslyScan()
    .then((res) => console.log("Scan paused:", res)) // boolean
```

---

#### c) `CustomizedView.resumeContinuouslyScan()`

Resumes barcode scanning.

Return Type

| Type               | Description                                               |
| ------------------ | --------------------------------------------------------- |
| `Promise<boolean>` | Promise that resolves true if the operation is successful |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.resumeContinuouslyScan()
    .then((res) => console.log("Scan resumed", res))
```

---

#### d) `CustomizedView.switchLight()`

Switch on/off flashlight

Return Type

| Type               | Description                                               |
| ------------------ | --------------------------------------------------------- |
| `Promise<boolean>` | Promise that resolves true if the operation is successful |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.switchLight()
    .then((res) => console.log("Flash light status:", res))
```

---

#### e) `CustomizedView.getLightStatus()`

Return Type

| Type               | Description                                 |
| ------------------ | ------------------------------------------- |
| `Promise<boolean>` | Promise that resolves the flashlight status |

Obtains the flashlight status

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.getLightStatus()
    .then((res) => console.log("Flash light status", res))
```

</details>

---

### Event Listeners

<details open><summary>MultiProcessor Module</summary>

#### a) `MultiProcessor.onMultiProcessorResponseListenerAdd()`

Adds listener for `onMultiProcessorResponse` event

Parameters:

| Name | Type       | Description                                                                                                                                                               |
| ---- | ---------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| fn   | function: `(res:`[`ScanResponse`](#scanresponse)`)=>void` | Listener function on `onMultiProcessorResponse` event which is triggered when camera detect a barcode. The listener function gets [ScanResponse](#scanresponse) as input. |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.MultiProcessor.onMultiProcessorResponseListenerAdd((res) => {
  console.log('onMultiProcessorResponse event triggered, res: ', res);
});
```

---

#### b) `MultiProcessor.onMultiProcessorResponseListenerRemove()`

Removes the listener for `onMultiProcessorResponse` event.

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.MultiProcessor.onMultiProcessorResponseListenerRemove();
```

---

#### c) `MultiProcessor.allListenersRemove()`

Removes all event listeners.

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.MultiProcessor.allListenersRemove();
```

</details>

---

<details open><summary>CustomizedView Module</summary>

#### a) `CustomizedView.onResponseListenerAdd()`

Adds listener for `onResponse` event.

Parameters:

| Name | Type                                                        | Description                                                                                                                                                                                                                |
| ---- | ----------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| fn   | function: `(res:`[`ScanResponse`](#scanresponse)`)=>void` | Listener function on customized view's `onResponse`  event which is triggered when continuous scanning option enabled and the camera detects a barcode. The listener function gets [ScanResponse](#scanresponse) as input. |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onResponseListenerAdd((res) => {
  console.log('onResponse event triggered, res: ', res);
});
```

---

#### b) `CustomizedView.onResponseListenerRemove()`

Removes the listener for `onResponse` event.

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onResponseListenerRemove();
```

---

#### c) `CustomizedView.onStartListenerAdd()`

Adds listener for `onStart` event.

Parameters:

| Name | Type                   | Description                                                       |
| ---- | ---------------------- | ----------------------------------------------------------------- |
| fn   | function: `() => void` | Listener function on customized view's `onStart` life cycle event |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onStartListenerAdd(() => {
  console.log('onStart event triggered');
});
```

---

#### d) `CustomizedView.onStartListenerRemove()`

Removes the listener for `onStart` event.

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onStartListenerRemove();
```

---

#### e) `CustomizedView.onResumeListenerAdd()`

Adds listener for `onResume` event.

Parameters:

| Name | Type                   | Description                                                        |
| ---- | ---------------------- | ------------------------------------------------------------------ |
| fn   | function: `() => void` | Listener function on customized view's `onResume` life cycle event |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onResumeListenerAdd(() => {
  console.log('onResume event triggered');
});
```

---

#### f) `CustomizedView.onResumeListenerRemove()`

Removes the listener for `onResume` event.

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onResumeListenerRemove();
```

---

#### g) `CustomizedView.onPauseListenerAdd()`

Adds listener for `onPause` event.

Parameters:

| Name | Type                   | Description                                                       |
| ---- | ---------------------- | ----------------------------------------------------------------- |
| fn   | function: `() => void` | Listener function on customized view's `onPause` life cycle event |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onPauseListenerAdd(() => {
  console.log('onPause event triggered');
});
```

---

#### h) `CustomizedView.onPauseListenerRemove()`

Removes the listener for `onPause` event.

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onPauseListenerRemove();
```

---

#### i) `CustomizedView.onDestroyListenerAdd()`

Adds listener for `onDestroy` event.

Parameters:

| Name | Type                   | Description                                                         |
| ---- | ---------------------- | ------------------------------------------------------------------- |
| fn   | function: `() => void` | Listener function on customized view's `onDestroy` life cycle event |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onDestroyListenerAdd(() => {
  console.log('onDestroy event triggered');
});
```

---

#### j) `CustomizedView.onDestroyListenerRemove()`

Removes the listener for `onDestroy` event.

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onDestroyListenerRemove();
```

---

#### k) `CustomizedView.onStopListenerAdd()`

Adds listener for `onStop` event.

Parameters:

| Name | Type                   | Description                                                      |
| ---- | ---------------------- | ---------------------------------------------------------------- |
| fn   | function: `() => void` | Listener function on customized view's `onStop` life cycle event |

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onStopListenerAdd(() => {
  console.log('onStop event triggered');
});
```

---

#### l) `CustomizedView.onStopListenerRemove()`

Removes the listener for `onStop` event.

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.onStopListenerRemove();
```

---

#### m) `CustomizedView.allListenersRemove()`

Removes all event listeners.

Call Example:

```jsx
import ScanPlugin from '@hmscore/react-native-hms-scan';

ScanPlugin.CustomizedView.allListenersRemove();
```

</details>

---

### DataTypes

<details open><summary>Show Details</summary>

---

#### **`AddressInfo`**

Object that contains address details.

| Key              | Value                         | Definition                 |
| ---------------- | ----------------------------- | -------------------------- |
| `addressDetails` | `String[]`                    | Address information array. |
| `addressType`    | [`AddressType`](#addresstype) | Address type.              |

---

#### **`CornerPoint`**

Object that contains corner point coordinates.

| Key | Value    | Definition    |
| --- | -------- | ------------- |
| `x` | `number` | X coordinate. |
| `y` | `number` | Y coordinate. |

---

#### **`DriverInfo`**

Object that contains driver details.

| Key                 | Value    | Definition                                  |
| ------------------- | -------- | ------------------------------------------- |
| `avenue`            | `String` | Street.                                     |
| `certificateNumber` | `String` | Driver license String.                      |
| `certificateType`   | `String` | Driver license type.                        |
| `city`              | `String` | City.                                       |
| `countryOfIssue`    | `String` | Country where the driver license is issued. |
| `dateOfBirth`       | `String` | Birth date.                                 |
| `dateOfExpire`      | `String` | Expiration date of the driver license.      |
| `dateOfIssue`       | `String` | Issue date of the driver license.           |
| `familyName`        | `String` | Last name.                                  |
| `givenName`         | `String` | First name.                                 |
| `middleName`        | `String` | Middle name.                                |
| `province`          | `String` | Province or state in the address.           |
| `sex`               | `String` | Gender.                                     |
| `zipCode`           | `String` | ZIP code.                                   |

---

#### **`EmailContent`**

Object that contains email details.

| Key           | Value                                    | Definition           |
| ------------- | ---------------------------------------- | -------------------- |
| `addressInfo` | `String`                                 | Email address.       |
| `addressType` | [`EmailAddressTypes`](#emailaddresstype) | Email address type.  |
| `bodyInfo`    | `String`                                 | Email content.       |
| `subjectInfo` | `String`                                 | Subject of an email. |

---

#### **`EventTime`**

Object that contains event time details.

| Key             | Value     | Definition                                    |
| --------------- | --------- | --------------------------------------------- |
| `day`           | `number`  | Day.                                          |
| `hours`         | `number`  | Hours.                                        |
| `isUTCTime`     | `boolean` | Determines whether the time is in UTC format. |
| `minutes`       | `number`  | Minutes.                                      |
| `month`         | `number`  | Month.                                        |
| `originalValue` | `String`  | Barcode value.                                |
| `seconds`       | `number`  | Seconds.                                      |
| `year`          | `number`  | Year.                                         |

---

#### **`LinkUrl`**

Object that contains url details.

| Key         | Value    | Definition |
| ----------- | -------- | ---------- |
| `linkvalue` | `String` | URL.       |
| `theme`     | `String` | Title.     |

---

#### **`LocationCoordinate`**

Object that contains location coordinates.

| Key         | Value    | Definition |
| ----------- | -------- | ---------- |
| `latitude`  | `number` | Latitude.  |
| `longitude` | `number` | Longitude. |

---

#### **`PeopleName`**

Object that contains person details.

| Key          | Value    | Definition                |
| ------------ | -------- | ------------------------- |
| `familyName` | `String` | Last name.                |
| `fullName`   | `String` | Full name of a contact.   |
| `givenName`  | `String` | First name.               |
| `middleName` | `String` | Middle name.              |
| `namePrefix` | `String` | Prefix of a contact name. |
| `nameSuffix` | `String` | Suffix of a contact name. |
| `spelling`   | `String` | Contact name spelling.    |

---

#### **`SmsContent`**

Object that contains sms details.

| Key               | Value    | Definition       |
| ----------------- | -------- | ---------------- |
| `msgContent`      | `String` | SMS information. |
| `destPhoneNumber` | `String` | Phone number.    |

---

#### **`TelPhoneNumber`**

Object that contains phone number details.

| Key              | Value                               | Definition         |
| ---------------- | ----------------------------------- | ------------------ |
| `telPhoneNumber` | `String`                            | Phone number.      |
| `useType`        | [`TelPhoneNumber`](#telphonenumber) | Phone number type. |

---

#### **`WiFiConnectionInfo`**

Object that contains wifi connection details.

| Key          | Value                           | Definition             |
| ------------ | ------------------------------- | ---------------------- |
| `password`   | `String`                        | Wi-Fi password.        |
| `ssidNumber` | `String`                        | SSID.                  |
| `cipherMode` | [`WifiModeType`](#wifimodetype) | Wi-Fi encryption mode. |

---

#### **`ContactDetail`**

Object that contains contact details.

| Key               | Value                                   | Definition           |
| ----------------- | --------------------------------------- | -------------------- |
| `addressesInfos`  | [`AddressInfo`](#addressinfo)`[]`       | Address information. |
| `company`         | `String`                                | Company information. |
| `contactLinks`    | `String[]`                              | URL information.     |
| `eMailContents`   | [`EmailContent`](#emailcontent)`[]`     | Email content.       |
| `note`            | `String`                                | Note.                |
| `peopleName`      | `PeopleName`                            | Contact information. |
| `telPhoneNumbers` | [`TelPhoneNumber`](#telphonenumber)`[]` | Phone number list.   |
| `title`           | `string`                                | Title.               |

---

#### **`EventInfo`**

Object that contains event details.

| Key            | Value                     | Definition                            |
| -------------- | ------------------------- | ------------------------------------- |
| `abstractInfo` | `String`                  | Calendar event description.           |
| `beginTime`    | [`EventTime`](#eventtime) | Start date of a calendar event.       |
| `closeTime`    | [`EventTime`](#eventtime) | End date of a calendar event.         |
| `condition`    | `String`                  | Calendar event status information.    |
| `placeInfo`    | `String`                  | Calendar event location information.  |
| `sponsor`      | `String`                  | Calendar event organizer information. |
| `theme`        | `String`                  | Calendar event summary.               |

---

#### **`BorderRect`**

Object that contains barcode rectangle details.

| Key                | Value                             | Definition                            |
| ------------------ | --------------------------------- | ------------------------------------- |
| `left`             | `number`                          | Left x coordinate.                    |
| `top`              | `number`                          | Top y coordinate.                     |
| `right`            | `number`                          | Right x coordinate.                   |
| `bottom`           | `number`                          | Bottom y coordinate.                  |
| `exactCenterX`     | `number`                          | Exact value of x coordinate center.   |
| `exactCenterY`     | `number`                          | Exact value of y coordinate center.   |
| `centerX`          | `number`                          | Rounded value of x coordinate center. |
| `centerY`          | `number`                          | Rounded value of y coordinate center. |
| `cornerPointsList` | [`CornerPoint`](#cornerpoint)`[]` | List of corner points                 |

---

#### **`ScanTextOptions`**

Text options for MultiCameraRequest object.

| Key                   | Value     | Definition                                                                      |
| --------------------- | --------- | ------------------------------------------------------------------------------- |
| `textColor`           | `number`  | Text color. Default value: -16777216(black).                                    |
| `textSize`            | `number`  | Text size. Default value: 35.                                                   |
| `showText`            | `boolean` | Indicates whether the text is visible. Default value:  true.                    |
| `showTextOutBounds`   | `boolean` | Indicates whether to limit the text in rectangle bounds. Default value:  false. |
| `textBackgroundColor` | `number`  | Text background color. Default value:  0(transparent).                          |
| `autoSizeText`        | `boolean` | Indicates whether the text auto size itself. Default value:  false.             |
| `minTextSize`         | `number`  | Minimum text size. Default value:  24.                                          |
| `granularity`         | `number`  | Granularity. Default value:  2.                                                 |

---

#### **`ScanResponse`**

Information returned when the startDefaultView, startCustomizedView, decodeWithBitmap, decodeMultiSync, decodeMultiAsync and startMultiProcessorCamera APIs are succesfully called.

| Key                  | Value                                       | Definition                        |
| -------------------- | ------------------------------------------- | --------------------------------- |
| `hmsScanVersion`     | `number`                                    | HMS Scan Version.                 |
| `cornerPoints`       | [`CornerPoint`](#cornerpoint)`[]`           | Barcode corner point information. |
| `originValueByte`    | `number[]`                                  | Byte array.                       |
| `originalValue`      | `String`                                    | Barcode information.              |
| `scanType`           | [`ScanType`](#scantype)                     | Barcode format.                   |
| `scanTypeForm`       | [`ScanForm`](#scanform)                     | Barcode content type.             |
| `showResult`         | `String`                                    | Barcode value.                    |
| `zoomValue`          | `number`                                    | Barcode zoom ratio.               |
| `smsContent`         | [`SmsContent`](#smscontent)                 | SMS information.                  |
| `emailContent`       | [`EmailContent`](#emailcontent)             | Email content.                    |
| `telPhoneNumber`     | [`TelPhoneNumber`](#telphonenumber)         | Phone number.                     |
| `linkUrl`            | [`LinkUrl`](#linkurl)                       | URL bookmark.                     |
| `wifiConnectionInfo` | [`WiFiConnectionInfo`](#wificonnectioninfo) | Wi-Fi connection info.            |
| `locationCoordinate` | [`LocationCoordinate`](#locationcoordinate) | Location information.             |
| `driverInfo`         | [`DriverInfo`](#driverinfo)                 | Driver license information.       |
| `contactDetail`      | [`ContactDetail`](#contactdetail)           | Contact information.              |
| `eventInfo`          | [`EventInfo`](#eventinfo)                   | Calendar event.                   |
| `borderRect`         | [`BorderRect`](#borderrect)                 | Barcode rectangle information     |

---

#### **`CustomizedViewRequest`**

Request information of the startCustomizedView API.

| Key                   | Value                       | Definition                                                          |
| --------------------- | --------------------------- | ------------------------------------------------------------------- |
| `scanType`            | [`ScanType`](#scantype)     | Barcode type.                                                       |
| `additionalScanTypes` | [`ScanType`](#scantype)`[]` | List of additional barcode types.                                   |
| `rectHeight`          | `number`                    | Height of scan area. Default value: 240.                            |
| `rectWidth`           | `number`                    | Width of scan area. Default value: 240.                             |
| `flashOnLightChange`  | `boolean`                   | Availability of the flash button under dim light.                   |
| `isFlashAvailable`    | `boolean`                   | Availability of the flash button. Default value: true.              |
| `isGalleryAvailable`  | `boolean`                   | Availability of gallery button. Default value: true.                |
| `continuouslyScan`    | `boolean`                   | Start customized view in continuous scan mode. Default value: true. |

---

#### **`BuildBitmapRequest`**

Request information of the buildBitmap API.

| Key               | Value                   | Definition                                         |
| ----------------- | ----------------------- | -------------------------------------------------- |
| `content`         | `String`                | Barcode content.                                   |
| `type`            | [`ScanType`](#scantype) | Barcode type. Default value: ScanType.QrCode       |
| `width`           | `number`                | Barcode width. Default value: 700                  |
| `height`          | `number`                | Barcode height. Default value: 700                 |
| `bitmapColor`     | `number`                | Barcode color. Default value: -16777216(black)     |
| `margin`          | `number`                | Barcode margin. Default value: 1                   |
| `backgroundColor` | `number`                | Barcode background color. Default value: -1(white) |

---

#### **`DecodeRequest`**

Request information of the decodeWithBitmap, decodeMultiSync and decodeMultiAsync APIs.

| Key                   | Value                       | Definition                        |
| --------------------- | --------------------------- | --------------------------------- |
| `data`                | `String`                    | The base64 string of the image.   |
| `scanType`            | [`ScanType`](#scantype)     | Barcode type.                     |
| `additionalScanTypes` | [`ScanType`](#scantype)`[]` | List of additional barcode types. |

---

#### **`DefaultViewRequest`**

Request information of the startDefaultView API.

| Key                   | Value                       | Definition                        |
| --------------------- | --------------------------- | --------------------------------- |
| `scanType`            | [`ScanType`](#scantype)     | Barcode type.                     |
| `additionalScanTypes` | [`ScanType`](#scantype)`[]` | List of additional barcode types. |

---

#### **`MultiCameraRequest`**

Request information of the startMultiProcessorCamera API.

| Key                   | Value                                 | Definition                                          |
| --------------------- | ------------------------------------- | --------------------------------------------------- |
| `scanMode`            | [`ScanMode`](#scanmode)               | Scan mode.                                          |
| `scanType`            | [`ScanType`](#scantype)               | Barcode type.                                       |
| `additionalScanTypes` | [`ScanType`](#scantype)`[]`           | List of additional barcode types.                   |
| `colorList`           | `number[]`                            | Color list. Default value: [-256] (yellow)          |
| `strokeWidth`         | `number`                              | Stroke width of rectangles. Default value: 4.0      |
| `isGalleryAvailable`  | `boolean`                             | Availability of gallery button. Default value: true |
| `scanTextOptions`     | [`ScanTextOptions`](#scantextoptions) | Text options for Multi Processor Camera.            |

</details>

---

### Constants

<details open><summary>Show Details</summary>

---

#### **`ScanType`**

| Key          | Value  | Definition                     |
| ------------ | ------ | ------------------------------ |
| `Other`      | `-1`   | Unknown barcode format.        |
| `All`        | `0`    | All supported barcode formats. |
| `Code128`    | `64`   | Code 128.                      |
| `Code39`     | `16`   | Code 39.                       |
| `Code93`     | `32`   | Code 93.                       |
| `Codabar`    | `4096` | Codabar.                       |
| `DataMatrix` | `4`    | Data Matrix.                   |
| `EAN13`      | `128`  | EAN-13.                        |
| `EAN8`       | `256`  | EAN-8.                         |
| `ITF14`      | `512`  | ITF-14.                        |
| `QRCode`     | `1`    | QR code.                       |
| `UPCCod`     | `1024` | UPC-A.                         |
| `UPCCod`     | `2048` | UPC-E.                         |
| `Pdf417`     | `8`    | PDF-417.                       |
| `Aztec`      | `2`    | Aztec.                         |

---

#### **`ScanMode`**

| Key     | Value | Definition                   |
| ------- | ----- | ---------------------------- |
| `Sync`  | `444` | Multi processor Sync Mode.   |
| `Async` | `555` | Multi processor Async Mode.. |

---

#### **`ScanForm`**

| Key                  | Value  | Definition                  |
| -------------------- | ------ | --------------------------- |
| `Other`              | `-1`   | Unknown barcode content.    |
| `ContactDetail`      | `1009` | Contact information.        |
| `EmailContent`       | `1002` | Email information.          |
| `ISBNNumber`         | `1012` | ISBN.                       |
| `TelPhoneNumber`     | `1003` | Phone number.               |
| `ArticleNumber`      | `1001` | Product information.        |
| `SMS`                | `1005` | SMS content.                |
| `PureText`           | `1004` | Text.                       |
| `Url`                | `1006` | URL.                        |
| `WIFIConnectInfo`    | `1007` | Wi-Fi.                      |
| `LocationCoordinate` | `1011` | Location.                   |
| `EventInfo`          | `1008` | Calendar event.             |
| `DriverInfo`         | `1010` | Driver license information. |

---

#### **`AddressType`**

| Key           | Value | Definition      |
| ------------- | ----- | --------------- |
| `Residential` | `0`   | Family address. |
| `Other`       | `-1`  | Unknown type.   |
| `Office`      | `1`   | Work address.   |

---

#### **`PhoneNumberType`**

| Key           | Value | Definition            |
| ------------- | ----- | --------------------- |
| `Fax`         | `0`   | Fax number.           |
| `Residential` | `1`   | Home phone number.    |
| `Cellphone`   | `2`   | Mobile number.        |
| `Other`       | `-1`  | Unknown phone number. |
| `Office`      | `3`   | Work phone number.    |

---

#### **`EmailAddressType`**

| Key           | Value | Definition             |
| ------------- | ----- | ---------------------- |
| `Office`      | `1`   | Work email address.    |
| `Residential` | `0`   | Family email address.  |
| `Other`       | `-1`  | Unknown email address. |

---

#### **`WifiModeType`**

| Key      | Value | Definition         |
| -------- | ----- | ------------------ |
| `NoPass` | `0`   | Open Wi-Fi.        |
| `WEP`    | `1`   | Wi-Fi in WEP mode. |
| `WPA`    | `2`   | Wi-Fi in WPA mode. |

---

#### **`ScanError`**

| Key                           | Error Code | Error Message                            | Possible Solution                                                                                                  |
| ----------------------------- | ---------- | ---------------------------------------- | ------------------------------------------------------------------------------------------------------------------ |
| `scanUtilNoCameraPermission`  | `"1"`      | No Camera Permission.                    | Call requestCameraAndStoragePermission API.                                                                        |
| `scanUtilNoReadPermission`    | `"2"`      | No Read Permission.                      | Call requestCameraAndStoragePermission API.                                                                        |
| `decodeMultiAsyncCouldntFind` | `"13"`     | Multi Async - Couldn't find anything.    | Analyzer couldn't find any suitable barcode from the provided image. Try changing scanType to proper HmsScanTypes. |
| `decodeMultiAsyncOnFailure`   | `"14"`     | Multi Async - On Failure.                | Analyzer failed to respond. Try recalling the API.                                                                 |
| `decodeMultiSyncCouldntFind`  | `"15"`     | Multi Sync - Couldn't find anything.     | Analyzer couldn't find any suitable barcode from the provided image. Try changing scanType to proper HmsScanTypes. |
| `mpCameraScanModeError`       | `"16"`     | Please check your scan mode.             | Provided scan mode is not suitable for analyzer. Try to use HmsMultiProcessor's Constant Values.                   |
| `decodeWithBitmapError`       | `"17"`     | Please check your barcode and scan type. | Analyzer couldn't find any suitable barcode from the provided image. Try changing scanType to proper HmsScanTypes. |
| `buildBitmap`                 | `"18"`     | Barcode generation failed.               | Try changing barcode content according to your error logs.                                                         |
| `hmsScanAnalyzerError`        | `"19"`     | Analyzer is not available.               | Try recalling the API.                                                                                             |
| `remoteViewError`             | `"20"`     | Remote View is not initialized.          | Try recalling the API.                                                                                             |

</details>

## Sample Project

Demo project in [example](example) folder, you can find more usage examples in there.

## Question or Issues

If you have questions about how to use HMS samples, try the following options:

* [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services)is the best place for any programming questions. Be sure to tag your question with **huawei-mobile-services**.
* [Github](https://github.com/HMS-Core/hms-react-native-plugin) is the official repository for these plugins, You can open an issue or submit your ideas.
* [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001) HMS Core Module is great for general questions, or seeking recommendations and opinions.
* [Huawei Developer Docs](https://developer.huawei.com/consumer/en/doc/overview/HMS-Core-Plugin) is place to official documentation for all HMS Core Kits, you can find detailed documentations in there.

If you run into a bug in our samples, please submit an issue to the Repository.

## License and Terms

Huawei React-Native SDK is licensed under [Apache 2.0 license](LICENCE)
