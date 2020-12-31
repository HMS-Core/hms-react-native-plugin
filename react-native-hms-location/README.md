# React-Native HMS Location

---

## Contents

  - [1. Introduction](#1-introduction)
  - [2. Installation Guide](#2-installation-guide)
  - [3. API Reference](#3-api-reference)
  - [4. Configuration and Description](#4-configuration-and-description)
  - [5. Sample Project](#5-sample-project)
  - [6. Questions or Issues](#6-questions-or-issues)
  - [7. Licensing and Terms](#7-licensing-and-terms)

## 1. Introduction

This module enables communication between Huawei Location SDK and React Native platform. It exposes all functionality provided by Huawei Location SDK.

## 2. Installation Guide

### Add Maven Repository

- Add maven repository address into 'android/build.gradle' file.

```groovy
maven {url 'https://developer.huawei.com/repo/'}
```

### Integrating the React Native HMS Location

#### Using NPM

- You can download the module from [npm](https://www.npmjs.com/package/@hmscore/react-native-hms-location)

  `npm i @hmscore/react-native-hms-location`

#### Using Download Link

- Download the module and copy it into `node_modules/@hmscore` folder. If `@hmscore` folder does not exist, create one. The folder structure can be seen below;

```text
project-name
    |_ node_modules
        |_ ...
        |_ @hmscore
          |_ ...
          |_ react-native-hms-location
          |_ ...
        |_ ...
```

- Add following lines into 'android/settings.gradle' file

```gradle
include ':react-native-hms-location'
project(':react-native-hms-location').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-location/android')
```

- Add 'react-native-hms-location' dependency into 'android/app/build.gradle' file.

```groovy
implementation project(":react-native-hms-location")
```

- Add 'RNHMSLocationPackage' to your application.

```java
import com.huawei.hms.rn.location.RNHMSLocationPackage;
...
...

@Override
protected List<ReactPackage> getPackages() {
  @SuppressWarnings("UnnecessaryLocalVariable")
  List<ReactPackage> packages = new PackageList(this).getPackages();
  packages.add(new RNHMSLocationPackage());
  return packages;
}
```

### Configure event mechanisms

- Initialize Location Kit in your React-Native project in order to make event mechanisms work properly.

```javascript
import HMSLocation from '@hmscore/react-native-hms-location';

// Initialize Location Kit
HMSLocation.LocationKit.Native.init()
    .then(_ => console.log("Done loading"))
    .catch(err => console.warn("Error while initializing." + err));;
```

- Add broadcast receiver and service in `AndroidManifest.xml` file to run headless tasks, register headless tasks in `index.js` file to receive events when the app is in the background or killed state.

`AndroidManifest.xml`

```xml
<manifest .../>
    <!-- Other configurations -->
    <!-- Add the permissions below for to run foreground services to send event to RN -->
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

    <application ...>
        <!-- These are needed to send event to RN part -->
        <receiver
            android:name="com.huawei.hms.rn.location.RNLocationBroadcastReceiver"
            android:exported="false"
            android:enabled="true">
        <intent-filter>
            <action android:name="${applicationId}.ACTION_HMS_LOCATION" />
            <action android:name="${applicationId}.ACTION_HMS_ACTIVITY_IDENTIFICATION" />
            <action android:name="${applicationId}.ACTION_HMS_ACTIVITY_CONVERSION" />
            <action android:name="${applicationId}.ACTION_HMS_GEOFENCE" />
        </intent-filter>
        </receiver>
        <service android:name="com.huawei.hms.rn.location.RNTaskService" />
    </application>
</manifest>

```

`index.js`

```javascript

import { AppRegistry } from 'react-native';
import App from './App';
import { name as appName } from './app.json';
import HMSLocation from '@hmscore/react-native-hms-location';

const yourFunction = (data)=> console.log(data) // set your listener function

// register headless tasks
HMSLocation.ActivityIdentification.Events.registerActivityIdentificationHeadlessTask(yourFunction);
HMSLocation.ActivityIdentification.Events.registerActivityConversionHeadlessTask(yourFunction);
HMSLocation.FusedLocation.Events.registerFusedLocationHeadlessTask(yourFunction);
HMSLocation.Geofence.Events.registerGeofenceHeadlessTask(yourFunction);
// then register the application component
AppRegistry.registerComponent(appName, () => App);
```

---

## 3. API Reference

### Modules

| Module                                                   | Description                                                                                                                                                                                               |
| -------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [LocationKit](#locationkit-module)                       | A module for initializing the package, customizing foreground notification and enabling/disabling logger.                                                                                                 |
| [FusedLocation](#fusedlocation-module)                   | With this module you can check the device location settings, get the last known location information once or continuously, set mock location and others                                                   |
| [ActivityIdentification](#activityidentification-module) | If your app needs to obtain the activity status of the user's device (for example, walking, running, or bicycling) or your app needs to detect activity status change of a user, you can use this module. |
| [Geofence](#geofence-module)                             | If you are interested in a place, you can create a geofence based on the place. When the device enters the geofence or stays for a duration of time, a notification can be sent to your app.              |

#### LocationKit Module

##### Function Summary

| Return Type       | Function                                                                                                                     | Description                                                                                                                                   |
| ----------------- | ---------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------- |
| Promise\<boolean> | [init()](#init)                                                                                                              | Initialize plugin.                                                                                                                            |
| Promise\<boolean> | [enableLogger()](#enablelogger)                                                                                              | This method enables HMSLogger capability which is used for sending usage analytics of Location SDK's methods to improve the service quality.  |
| Promise\<boolean> | [disableLogger()](#disablelogger)                                                                                            | This method disables HMSLogger capability which is used for sending usage analytics of Location SDK's methods to improve the service quality. |
| Promise\<boolean> | [setNotification(contentTitle,contentText,defType,resourceName)](#setnotificationcontenttitlecontenttextdeftyperesourcename) | This method sets the notification for the foreground service.                                                                                 |

---

##### Functions

###### init()

Initializes the context based broadcast receiver and enables this receiver to send data to RN side.

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.LocationKit.Native.init()
  .then(_ => console.log("Done loading"))
  .catch(ex => console.log("Error while initializing." + ex));
```

---

###### enableLogger()

This method enables HMSLogger capability which is used for sending usage analytics of Location SDK's methods to improve the service quality

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.LocationKit.Native.enableLogger()
  .then(_ => console.log("HMS Logger enabled"));
```

---

###### disableLogger()

This method disables HMSLogger capability which is used for sending usage analytics of Location SDK's methods to improve the service quality

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.LocationKit.Native.disableLogger()
  .then(_ => console.log("HMS Logger disabled"));
```

---

###### setNotification(contentTitle,contentText,defType,resourceName)

This method sets the notification for the foreground service.

| Parameter    | Type   | Description                             |
| ------------ | ------ | --------------------------------------- |
| contentTitle | string | Title of the notification.              |
| contentText  | string | Text on the notification.               |
| defType      | string | Resource type of the notification icon. |
| resourceName | string | Resource name of the notification icon. |

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.LocationKit.Native.setNotification({
    contentTitle: "Hello",
    contentText: "You received something",
    defType: "mipmap",
    resourceName: "ic_launcher"}
)
  .then(_ => console.log("Notification set"));
```

---

#### FusedLocation Module

##### Function Summary

| Return Type                                                     | Function                                                                                      | Description                                                                                                                                                                                                                           |
| --------------------------------------------------------------- | --------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Promise\<boolean>                                               | [flushLocations()](#flushlocations)                                                           | This API is used to update locations being processed.                                                                                                                                                                                 |
| Promise\<[LocationSettingsStates](#locationsettingsstates)>     | [checkLocationSettings(request)](#checklocationsettingsrequest)                               | This API is used to check whether related location settings are available.                                                                                                                                                            |
| Promise\<[Location](#location)>                                 | [getLastLocation()](#getlastlocation)                                                         | This API is used to obtain the latest available location.                                                                                                                                                                             |
| Promise\<[NavigationResult](#navigationresult)>                 | [getNavigationContextState(request)](#getnavigationcontextstaterequest)                       | This API is used for obtaining navigation status and checking whether the user device supports high-precision location.                                                                                                               |
| Promise\<[LocationData](#locationdata)>                         | [getLastLocationWithAddress(request)](#getlastlocationwithaddressrequest)                     | This API is used to obtain the available location of the last request, including the detailed address information.                                                                                                                    |
| Promise\<[LocationAvailability](#locationavailability)>         | [getLocationAvailability()](#getlocationavailability)                                         | This API is used to check whether the location data is available.                                                                                                                                                                     |
| Promise\<boolean>                                               | [setMockMode(shouldMock)](#setmockmodeshouldmock)                                             | This API is used to specify whether the location provider uses the location mock mode. If yes, the GPS or network location is not used and the location set through [setMockLocation()](#setmocklocationlatlng) is directly returned. |
| Promise\<boolean>                                               | [setMockLocation(latlng)](#setmocklocationlatlng)                                             | This API is used to update locations being processed if mock location is being used.                                                                                                                                                  |
| Promise\<[RequestCode](#requestcode)>                           | [requestLocationUpdates(requestId,request)](#requestlocationupdatesrequestidrequest)          | This API is used to request location updates with pending intents that enables the app to send data to RN even in killed state.                                                                                                       |
| Promise\<boolean>                                               | [removeLocationUpdates(requestId)](#removelocationupdatesrequestid)                           | This API is used to remove location updates with pending intents with specified request code.                                                                                                                                         |
| void                                                            | [registerFusedLocationHeadlessTask(fn)](#registerfusedlocationheadlesstaskfn)                 | This API is used to register headless task to obtain location update result when the application is in the background or killed state.                                                                                                |
| Promise\<[RequestCode](#requestcode)>                           | [requestLocationUpdatesWithCallback(request)](#requestlocationupdateswithcallbackrequest)     | This API is used to request location updates with location callback.                                                                                                                                                                  |
| Promise\<[RequestCode](#requestcode)>                           | [requestLocationUpdatesWithCallbackEx(request)](#requestlocationupdateswithcallbackexrequest) | This is an extended location service API that supports high-precision location and is compatible with common location APIs.                                                                                                           |
| Promise\<boolean>                                               | [removeLocationUpdatesWithCallback(requestId)](#removelocationupdateswithcallbackrequestid)   | This API is used to remove location updates of the specified callback information.                                                                                                                                                    |
| void                                                            | [addFusedLocationEventListener(fn)](#addfusedlocationeventlistenerfn)                         | This API is used to set a callback function that is continuously called with location data.                                                                                                                                           |
| void                                                            | [removeFusedLocationEventListener(fn)](#removefusedlocationeventlistenerfn)                   | This API is used to remove the event listener that is added by [addFusedLocationEventListener()](#addfusedlocationeventlistenerfn)                                                                                                    |
| Promise\<[LocationPermissionResult](#locationpermissionresult)> | [requestPermission()](#requestpermission)                                                     | This API is used to request permission to use location services.                                                                                                                                                                      |
| Promise\<[HasPermissionResult](#haspermissionresult)>           | [hasPermission()](#haspermission)                                                             | This API is used to check if the permission to use location services has been granted.                                                                                                                                                |

---

##### Functions

###### flushLocations()

This API is used to update locations being processed.

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.FusedLocation.Native.flushLocations()
  .then(_ => console.log("HMS Logger disabled"));
```

---

###### checkLocationSettings(request)

This API is used to check whether related location settings are available.

| Parameter | Type                                                | Description                                    |
| --------- | --------------------------------------------------- | ---------------------------------------------- |
| request   | [LocationSettingsRequest](#locationsettingsrequest) | Request object for checking location settings. |

| Return Type                                                 | Description                                                                                                   |
| ----------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- |
| Promise\<[LocationSettingsStates](#locationsettingsstates)> | Promise that resolves [LocationSettingsStates](#locationsettingsstates) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const locationRequest = {
    priority: HMSLocation.FusedLocation.PriorityConstants.PRIORITY_HIGH_ACCURACY,
    interval: 5000,
    numUpdates: 20,
    fastestInterval: 6000,
    expirationTime: 100000,
    expirationTimeDuration: 100000,
    smallestDisplacement: 0,
    maxWaitTime: 1000.0,
    needAddress: false,
    language: "en",
    countryCode: "en",
};

const locationSettingsRequest = {
    locationRequests: [locationRequest],
    alwaysShow: false,
    needBle: false,
};

HMSLocation.FusedLocation.Native.checkLocationSettings(locationSettingsRequest)
    .then(res => console.log(res))
    .catch(err => console.log("Error while getting location settings. " + err))
```

---

###### getNavigationContextState(request)

This API is used for obtaining navigation status and checking whether the user device supports high-precision location.

| Parameter | Type                                                      | Description                 |
| --------- | --------------------------------------------------------- | --------------------------- |
| request   | [NavigationRequestConstants](#navigationrequestconstants) | Navigation request constant |

| Return Type                                     | Description                                                                                       |
| ----------------------------------------------- | ------------------------------------------------------------------------------------------------- |
| Promise\<[NavigationResult](#navigationresult)> | Promise that resolves [NavigationResult](#navigationresult) object if the operation is successful |

Sample Code:

```jsx
HMSLocation.FusedLocation.Native.getNavigationContextState(
  HMSLocation.FusedLocation.NavigationRequestConstants.IS_SUPPORT_EX)
    .then(res => console.log(res))
    .catch(err => console.log("Error while getting navigation state. " + err))
```

---

###### getLastLocation()

This API is used to obtain the latest available location.

| Return Type                     | Description                                                                       |
| ------------------------------- | --------------------------------------------------------------------------------- |
| Promise\<[Location](#location)> | Promise that resolves [Location](#location) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.FusedLocation.Native.getLastLocation()
    .then(res => console.log(res))
    .catch(err => console.log('Failed to get last location', err));
```

---

###### getLastLocationWithAddress(request)

This API is used to obtain the available location of the last request, including the detailed address information.

| Parameter | Type                                | Description             |
| --------- | ----------------------------------- | ----------------------- |
| request   | [LocationRequest](#locationrequest) | Location request object |

| Return Type                         | Description                                                                           |
| ----------------------------------- | ------------------------------------------------------------------------------------- |
| Promise\<[HWLocation](#hwlocation)> | Promise that resolves [HWLocation](#hwlocation) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const locationRequest = {
    priority: HMSLocation.FusedLocation.PriorityConstants.PRIORITY_HIGH_ACCURACY,
    interval: 3,
    numUpdates: 10,
    fastestInterval: 1000.0,
    expirationTime: 200000.0,
    expirationTimeDuration: 200000.0,
    smallestDisplacement: 0.0,
    maxWaitTime: 2000000.0,
    needAddress: true,
    language: 'en',
    countryCode: 'en',
};
HMSLocation.FusedLocation.Native.getLastLocationWithAddress(locationRequest)
    .then(res => { console.log('Address: ', res) });
```

---

###### getLocationAvailability()

This API is used to check whether the location data is available.

| Return Type                                             | Description                                                                                               |
| ------------------------------------------------------- | --------------------------------------------------------------------------------------------------------- |
| Promise\<[LocationAvailability](#locationavailability)> | Promise that resolves [LocationAvailability](#locationavailability) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.FusedLocation.Native.getLocationAvailability()
    .then(res => console.log(res))
    .catch(err => console.log('Failed to get location availability', err));
```

---

###### requestLocationUpdates(requestId,request)

This API is used to request location updates with pending intents that enables the app to send data to RN even in killed state.

| Parameter | Type                                | Description             |
| --------- | ----------------------------------- | ----------------------- |
| requestId | number                              | Location request id     |
| request   | [LocationRequest](#locationrequest) | Location request object |

| Return Type                           | Description                                                                             |
| ------------------------------------- | --------------------------------------------------------------------------------------- |
| Promise\<[RequestCode](#requestcode)> | Promise that resolves [RequestCode](#requestcode) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const requestId = 20;
const locationRequest = {
    priority: HMSLocation.FusedLocation.PriorityConstants.PRIORITY_HIGH_ACCURACY,
    interval: 3,
    numUpdates: 10,
    fastestInterval: 1000.0,
    expirationTime: 200000.0,
    expirationTimeDuration: 200000.0,
    smallestDisplacement: 0.0,
    maxWaitTime: 2000000.0,
    needAddress: true,
    language: 'en',
    countryCode: 'en',
};
HMSLocation.FusedLocation.Native.requestLocationUpdates(requestId, locationRequest)
    .then(({ requestCode }) => console.log(requestCode))
    .catch(err => console.log("Exception while requestLocationUpdates " + err))
```

---

###### removeLocationUpdates(requestId)

This API is used to remove location updates with pending intents with specified request code.

| Parameter | Type   | Description                                                                                                 |
| --------- | ------ | ----------------------------------------------------------------------------------------------------------- |
| requestId | number | A number that contains the request ID obtained from the response of [requestLocationUpdates](#req) function |

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const requestId = 20;
HMSLocation.FusedLocation.Native.removeLocationUpdates(requestId)
  .then(_ => console.log("Remove location update successful"));
```

---

###### registerFusedLocationHeadlessTask(fn)

This API is used to register headless task to obtain location update result when the application is in the background or killed state. You need to register for updates to get location update result by using the [requestLocationUpdates()](#requestlocationupdatesrequestidrequest) function.

| Parameter | Type                                            | Description                                                                       |
| --------- | ----------------------------------------------- | --------------------------------------------------------------------------------- |
| fn        | (res:[LocationResult](#locationresult)) => void | Callback function that takes [LocationResult](#locationresult) object as argument |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.FusedLocation.Events.registerFusedLocationHeadlessTask((data) =>
  console.log('Fused Location Headless Task, data:', data)
);
// then register the application component
// AppRegistry.registerComponent(appName, () => App);
```

###### requestLocationUpdatesWithCallback(request)

This API is used to request location updates with location callback.

| Parameter | Type                                | Description             |
| --------- | ----------------------------------- | ----------------------- |
| request   | [LocationRequest](#locationrequest) | Location request object |

| Return Type                           | Description                                                                             |
| ------------------------------------- | --------------------------------------------------------------------------------------- |
| Promise\<[RequestCode](#requestcode)> | Promise that resolves [RequestCode](#requestcode) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const locationRequest = {
    priority: HMSLocation.FusedLocation.PriorityConstants.PRIORITY_HIGH_ACCURACY,
    interval: 3,
    numUpdates: 10,
    fastestInterval: 1000.0,
    expirationTime: 200000.0,
    expirationTimeDuration: 200000.0,
    smallestDisplacement: 0.0,
    maxWaitTime: 2000000.0,
    needAddress: true,
    language: 'en',
    countryCode: 'en',
};
HMSLocation.FusedLocation.Native.requestLocationUpdatesWithCallback(locationRequest)
    .then(({ requestCode }) => console.log(requestCode))
    .catch(err => console.log("Exception while requestLocationUpdatesWithCallback " + err))

```

---

###### requestLocationUpdatesWithCallbackEx(request)

This API is also used to request location updates. But this is an extended location service API that supports high-precision location and is compatible with common location APIs.

| Parameter | Type                                | Description             |
| --------- | ----------------------------------- | ----------------------- |
| request   | [LocationRequest](#locationrequest) | Location Request object |

| Return Type                                   | Description                                                                                     |
| --------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| Promise\<[LocationRequest](#locationrequest)> | Promise that resolves [LocationRequest](#locationrequest) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const locationRequest = {
    priority: HMSLocation.FusedLocation.PriorityConstants.PRIORITY_HIGH_ACCURACY,
    interval: 3,
    numUpdates: 10,
    fastestInterval: 1000.0,
    expirationTime: 200000.0,
    expirationTimeDuration: 200000.0,
    smallestDisplacement: 0.0,
    maxWaitTime: 2000000.0,
    needAddress: true,
    language: 'en',
    countryCode: 'en',
};
HMSLocation.FusedLocation.Native.requestLocationUpdatesWithCallbackEx(locationRequest)
    .then(({ requestCode }) => console.log(requestCode))
    .catch(err => console.log("Exception while requestLocationUpdatesWithCallbackEx " + err))

```

---

###### removeLocationUpdatesWithCallback(requestId)

This API is used to remove location updates of the specified callback information.

| Parameter | Type   | Description                                                                                                                                                                                                                                            |
| --------- | ------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| requestId | number | A number that contains the request ID obtained from the response of [requestLocationUpdatesWithCallback](#requestlocationupdateswithcallbackrequest) or [requestLocationUpdatesWithCallbackEx](#requestlocationupdateswithcallbackexrequest) functions |

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const requestId = 20;
HMSLocation.FusedLocation.Native.removeLocationUpdatesWithCallback(requestId)
  .then(_ => console.log("Remove location update successful"));
```

---

###### setMockMode(shouldMock)

This API is used to specify whether the location provider uses the location mock mode. If yes, the GPS or network location is not used and the location set through setMockLocation (Location) is directly returned.

| Parameter  | Type      | Description          |
| ---------- | --------- | -------------------- |
| shouldMock | `boolean` | Should mock location |

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.FusedLocation.Native.setMockMode(true)
    .then(res => console.log('Mock mode ', res))
    .catch(err => console.log(err));
```

---

###### setMockLocation(location)

This API is used to update locations being processed if mock location is being used.

| Parameter | Type              | Description   |
| --------- | ----------------- | ------------- |
| location  | [LatLng](#latlng) | Mock location |

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
// Location object
const latLng = { latitude: 41.2, longitude: 29.1 };
HMSLocation.FusedLocation.Native.setMockLocation(latLng)
    .then(res => console.log('Mock set ', res))
    .catch(err => console.log(err));
```

---

###### requestPermission()

This API is used to request permission to use location services.

| Return Type                                                     | Description                                                                                                |
| --------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- |
| Promise\<[LocationPermissionResult](#locationpermissionresult)> | Promise that resolves [LocationPermissionResult](#locationpermissionresult) if the operation is successful |
Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.FusedLocation.Native.requestPermission()
  .then(res => console.log('Permissions:', res));
```

---

###### hasPermission()

This API is used to check if the permission to use location services has been granted.

| Return Type                                           | Description                                                                                             |
| ----------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
| Promise\<[HasPermissionResult](#haspermissionresult)> | Promise that resolves [HasPermissionResult](#haspermissionresult) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.FusedLocation.Native.hasPermission()
    .then(res => console.log(res))
    .catch(err => console.log("Error while getting location permission info: " + err));
```

###### addFusedLocationEventListener(fn)

This function takes a callback function that is continuously called with location data. You need to register for updates first by using the [requestLocationUpdates()](#requestlocationupdatesrequestidrequest) or [requestLocationUpdatesWithCallback()](#requestlocationupdateswithcallbackrequest) or [requestLocationUpdatesWithCallbackEx()](#requestlocationupdateswithcallbackexrequest)  functions.

| Parameter | Type                                            | Description                                                                       |
| --------- | ----------------------------------------------- | --------------------------------------------------------------------------------- |
| fn        | (res:[LocationResult](#locationresult)) => void | Callback function that takes [LocationResult](#locationresult) object as argument |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const handleLocationUpdate = location => {
    console.log(location);
};
HMSLocation.FusedLocation.Events.addFusedLocationEventListener(
    handleLocationUpdate,
);
```

---

###### removeFusedLocationEventListener(fn)

This API is used to remove the event listener that is added by [addFusedLocationEventListener()](#addfusedlocationeventlistenerfn)

| Parameter | Type                                            | Description                                                                       |
| --------- | ----------------------------------------------- | --------------------------------------------------------------------------------- |
| fn        | (res:[LocationResult](#locationresult)) => void | Callback function that takes [LocationResult](#locationresult) object as argument |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const handleLocationUpdate = location => {
    console.log(location);
};
HMSLocation.FusedLocation.Events.removeFusedLocationEventListener(
    handleLocationUpdate,
);
```

##### Events

| Event      | Description                       |
| ---------- | --------------------------------- |
| onLocation | Event emitted on location update. |

#### ActivityIdentification Module

##### Function Summary

| Return Type                                                     | Function                                                                                                                     | Description                                                                                                                                                                                                                                                                        |
| --------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Promise\<[RequestCode](#requestcode)>                           | [createActivityConversionUpdates(requestId,request)](#createactivityconversionupdatesrequestidrequest)                       | This API is used to activity conversions (entering and exit), for example, detecting user status change from walking to bicycling.                                                                                                                                                 |
| void                                                            | [registerActivityConversionHeadlessTask(fn)](#registeractivityconversionheadlesstaskfn)                                      | This API is used to register headless task to obtain activity conversion result when the application is in the background or killed state.                                                                                                                                         |
| void                                                            | [addActivityConversionEventListener(fn)](#addactivityconversioneventlistenerfn)                                              | This API is used to take set a callback function that is continuously called with activity conversion data. You need to register for updates first by using the [createActivityConversionUpdates()](#createactivityconversionupdatesrequestidrequest) function.                    |
| void                                                            | [removeActivityConversionEventListener(fn)](#removeactivityconversioneventlistenerfn)                                        | This API is used to remove the event listener that is added by [addActivityConversionEventListener()](#addactivityconversioneventlistenerfn).                                                                                                                                      |
| Promise\<boolean>                                               | [deleteActivityConversionUpdates(requestId)](#deleteactivityconversionupdatesrequestid)                                      | This API is used to remove activity conversion updates by their request code.                                                                                                                                                                                                      |
| Promise\<[RequestCode](#requestcode)>                           | [createActivityIdentificationUpdates(requestId,intervalMillis)](#createactivityidentificationupdatesrequestidintervalmillis) | This API is used to register for activity identification updates. After this, you can subscribe to updates using [addActivityIdentificationEventListener()](#addactivityidentificationeventlistenerfn) function.                                                                   |
| void                                                            | [registerActivityIdentificationHeadlessTask(fn)](#registeractivityidentificationheadlesstaskfn)                              | This API is used to register headless task to obtain activity identification result when the application is in the background or killed state.                                                                                                                                     |
| void                                                            | [addActivityIdentificationEventListener(fn)](#addActivityIdentificationEventListenerfn)                                      | This API is used to take set a callback function that is continuously called with activity identification data. You need to register for updates first by using the [createActivityIdentificationUpdates()](#createactivityidentificationupdatesrequestidintervalmillis) function. |
| void                                                            | [removeActivityIdentificationEventListener(fn)](#removeactivityidentificationeventlistenerfn)                                | This API is used to remove the event listener that is added by [addActivityIdentificationEventListener()](#addactivityidentificationeventlistenerfn)                                                                                                                               |
| Promise\<boolean>                                               | [deleteActivityIdentificationUpdates(requestId)](#deleteactivityidentificationupdatesrequestid)                              | This API is used to remove activity identification updates by their request code.                                                                                                                                                                                                  |
| Promise\<[ActivityPermissionResult](#activitypermissionresult)> | requestPermission()                                                                                                          | This API is used to request permission to use activity identification services.                                                                                                                                                                                                    |
| Promise\<[HasPermissionResult](#haspermissionresult)>           | hasPermission()                                                                                                              | This API is used to check if the permission to use activity identfication services has been granted.                                                                                                                                                                               |

---

##### Functions

###### createActivityConversionUpdates(requestId,request)

This API is used to detect activity conversions (entering and exit), for example, detecting user status change from walking to bicycling. After this, you can subscribe to updates using [addActivityConversionEventListener()](#addactivityconversioneventlistenerfn) function.

| Parameter | Type                                                      | Description                                          |
| --------- | --------------------------------------------------------- | ---------------------------------------------------- |
| requestId | number                                                    | Activity conversion request id                       |
| request   | [ActivityConversionRequest](#activityconversionrequest)[] | Request object array for activity conversion update. |

| Return Type                           | Description                                                                             |
| ------------------------------------- | --------------------------------------------------------------------------------------- |
| Promise\<[RequestCode](#requestcode)> | Promise that resolves [RequestCode](#requestcode) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const activityConversionRequestArray = [
    // STILL
    {
        conversionType: HMSLocation.ActivityIdentification.ActivityConversions.ENTER_ACTIVITY_CONVERSION,
        activityType: HMSLocation.ActivityIdentification.Activities.STILL
    },
    {
        conversionType: HMSLocation.ActivityIdentification.ActivityConversions.EXIT_ACTIVITY_CONVERSION,
        activityType: HMSLocation.ActivityIdentification.Activities.STILL
    },

    // ON FOOT
    {
        conversionType: HMSLocation.ActivityIdentification.ActivityConversions.ENTER_ACTIVITY_CONVERSION,
        activityType: HMSLocation.ActivityIdentification.Activities.FOOT
    },
    {
        conversionType: HMSLocation.ActivityIdentification.ActivityConversions.EXIT_ACTIVITY_CONVERSION,
        activityType: HMSLocation.ActivityIdentification.Activities.FOOT
    },

    // RUNNING
    {
        conversionType: HMSLocation.ActivityIdentification.ActivityConversions.ENTER_ACTIVITY_CONVERSION,
        activityType: HMSLocation.ActivityIdentification.Activities.RUNNING
    },
    {
        conversionType: HMSLocation.ActivityIdentification.ActivityConversions.EXIT_ACTIVITY_CONVERSION,
        activityType: HMSLocation.ActivityIdentification.Activities.RUNNING
    }
];
const requestId = 30;
HMSLocation.ActivityIdentification.Native.createActivityConversionUpdates(requestId, activityConversionRequestArray)
    .then(res => console.log(res))
    .catch(err => console.log('ERROR: Activity Conversion creation failed', err));
```

---

###### deleteActivityConversionUpdates(requestId)

This API is used to remove activity conversion updates by their request code.

| Parameter | Type   | Description                                                                                                                             |
| --------- | ------ | --------------------------------------------------------------------------------------------------------------------------------------- |
| requestId | number | Activity conversion request id given to [createActivityConversionUpdates()](#createactivityconversionupdatesrequestidrequest) function. |

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const requestId = 30;
HMSLocation.ActivityIdentification.Native.deleteActivityConversionUpdates(requestId)
    .then(res => console.log(res))
    .catch(err => console.log('ERROR: Activity Conversion deletion failed', err));
```

---

###### createActivityIdentificationUpdates(requestId,intervalMillis)

This API is used to register for activity identification updates. After this, you can subscribe to updates using [addActivityIdentificationEventListener()](#addactivityidentificationeventlistenerfn) function.

| Parameter      | Type   | Description                                                    |
| -------------- | ------ | -------------------------------------------------------------- |
| requestId      | number | Activity identification request id                             |
| intervalMillis | number | Interval for activity identification updates, in milliseconds. |

| Return Type                           | Description                                                                             |
| ------------------------------------- | --------------------------------------------------------------------------------------- |
| Promise\<[RequestCode](#requestcode)> | Promise that resolves [RequestCode](#requestcode) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const requestId = 50;
HMSLocation.ActivityIdentification.Native.createActivityIdentificationUpdates(requestId, 2000)
    .then(res => console.log(res))
    .catch(err => console.log('ERROR: Activity identification failed', err));
```

---

###### deleteActivityIdentificationUpdates(requestId)

This API is used to remove activity identification updates by their request code.

| Parameter | Type   | Description                                                                                                                                                |
| --------- | ------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------- |
| requestId | number | Activity identification request id given to [createActivityIdentificationUpdates()](#createactivityidentificationupdatesrequestidintervalmillis) function. |

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const requestId = 50;
HMSLocation.ActivityIdentification.Native.deleteActivityIdentificationUpdates(requestId)
    .then(res => console.log(res))
    .catch(err => console.log('ERROR: Activity identification deletion failed', err));
```

---

###### requestPermission()

This API is used to request activity permissions.

| Return Type                                                     | Description                                                                                                |
| --------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- |
| Promise\<[ActivityPermissionResult](#activitypermissionresult)> | Promise that resolves [ActivityPermissionResult](#activitypermissionresult) if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.ActivityIdentification.Native.requestPermission()
  .then(res => console.log('Permissions:', res));
```

---

###### hasPermission()

This API is used to check if the permission to gather activity information.

| Return Type                                           | Description                                                                                             |
| ----------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
| Promise\<[HasPermissionResult](#haspermissionresult)> | Promise that resolves [HasPermissionResult](#haspermissionresult) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
 HMSLocation.ActivityIdentification.Native.hasPermission()
    .then(res => console.log(res))
    .catch(err => console.log("Error while getting activity identification permission info: " + err));
```

###### registerActivityConversionHeadlessTask(fn)

This API is used to register headless task to obtain activity conversion data when the application is in the background or killed state. You need to register for updates to get activity conversion data by using the [createActivityConversionUpdates()](#createactivityconversionupdatesrequestidrequest) function.

| Parameter | Type                                                                    | Description                                                                                               |
| --------- | ----------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------- |
| fn        | (res:[ActivityConversionResponse](#activityconversionresponse)) => void | Callback function that takes [ActivityConversionResponse](#activityconversionresponse) object as argument |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.ActivityIdentification.Events.registerActivityConversionHeadlessTask((data) =>
  console.log('Activity Conversion Headless Task, data:', data)
);
// then register the application component
// AppRegistry.registerComponent(appName, () => App);
```

---

###### addActivityConversionEventListener(fn)

This function takes a callback function that is continuously called with activity conversion data. You need to register for updates first by using the [createActivityConversionUpdates()](#createactivityconversionupdatesrequestidrequest) function.

| Parameter | Type                                                                    | Description                                                                                               |
| --------- | ----------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------- |
| fn        | (res:[ActivityConversionResponse](#activityconversionresponse)) => void | Callback function that takes [ActivityConversionResponse](#activityconversionresponse) object as argument |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const handleActivityConversion = location => {
    console.log('CONVERSION : ', conv);
};
HMSLocation.ActivityIdentification.Events.addActivityConversionEventListener(
    handleActivityConversion,
);
```

---

###### removeActivityConversionEventListener(fn)

This API is used to remove the event listener that is added by [addActivityConversionEventListener()](#addactivityconversioneventlistenerfn)

| Parameter | Type                                                                    | Description                                                                                               |
| --------- | ----------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------- |
| fn        | (res:[ActivityConversionResponse](#activityconversionresponse)) => void | Callback function that takes [ActivityConversionResponse](#activityconversionresponse) object as argument |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const handleActivityConversion = location => {
    console.log('CONVERSION : ', conv);
};
HMSLocation.ActivityIdentification.Events.removeActivityConversionEventListener(
    handleActivityConversion,
);
```

---

###### registerActivityIdentificationHeadlessTask(fn)

This API is used to register headless task to obtain activity identification data when the application is in the background or killed state. You need to register for updates to get activity identification data by using the [createActivityIdentificationUpdates()](#createactivityidentificationupdatesrequestidintervalmillis) function.

| Parameter | Type                                                                            | Description                                                                                                       |
| --------- | ------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| fn        | (res:[ActivityIdentificationResponse](#activityidentificationresponse)) => void | Callback function that takes [ActivityIdentificationResponse](#activityidentificationresponse) object as argument |

Sample Code:

```jsx
HMSLocation.ActivityIdentification.Events.registerActivityIdentificationHeadlessTask((data) =>
  console.log('Activity Identification Headless Task, data:', data)
);
```

---

###### addActivityIdentificationEventListener(fn)

This function takes a callback function that is continuously called with activity identification data. You need to register for updates first by using the [createActivityIdentificationUpdates()](#createactivityidentificationupdatesrequestidintervalmillis) function.

| Parameter | Type                                                                            | Description                                                                                                       |
| --------- | ------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| fn        | (res:[ActivityIdentificationResponse](#activityidentificationresponse)) => void | Callback function that takes [ActivityIdentificationResponse](#activityidentificationresponse) object as argument |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const handleActivityIdentification = act => {
    console.log('ACTIVITY : ', act);
};
HMSLocation.ActivityIdentification.Events.addActivityIdentificationEventListener(
    handleActivityIdentification,
);
```

---

###### removeActivityIdentificationEventListener(fn)

This API is used to remove the event listener that is added by [addActivityIdentificationEventListener()](#addactivityidentificationeventlistenerfn)

| Parameter | Type                                                                            | Description                                                                                                       |
| --------- | ------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| fn        | (res:[ActivityIdentificationResponse](#activityidentificationresponse)) => void | Callback function that takes [ActivityIdentificationResponse](#activityidentificationresponse) object as argument |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const handleActivityConversion = location => {
    console.log('CONVERSION : ', conv);
};
HMSLocation.ActivityIdentification.Events.removeActivityIdentificationEventListener(
    handleActivityIdentification,
);
```

---

##### Events

| Event                    | Description                                      |
| ------------------------ | ------------------------------------------------ |
| onActivityIdentification | Event emitted on activity identification update. |
| onActivityConversion     | Event emitted on activity conversion update.     |

---

#### Geofence Module

##### Function Summary

| Return Type                                     | Function                                                                                                                                   | Description                                                                                                                                                                                                                                                                          |
| ----------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Promise\<[GeofenceResponse](#geofenceresponse)> | [createGeofenceList(requestId,geofences,initConversion,coordinateType)](#creategeofencelistrequestidgeofencesinitconversioncoordinatetype) | This API is used to add multiple geofences.  When a geofence is triggered, a notification is broadcasted. You can subscribe to the broadcast by using [addGeofenceEventListener()](#addgeofenceeventlistenerfn) function.                                                            |
| Promise\<boolean>                               | [deleteGeofenceList(requestId)](#deletegeofencelistrequestid)                                                                              | This API is used to remove geofences by their request IDs. An error is reported if the list is empty.                                                                                                                                                                                |
| void                                            | [registerGeofenceHeadlessTask(fn)](#registergeofenceheadlesstaskfn)                                                                        | This API is used to register headless task to obtain geofence result when the application is in the background or killed state.                                                                                                                                                      |
| void                                            | [addGeofenceEventListener(fn)](#addgeofenceeventlistenerfn)                                                                                | This API is used to subscribe geofence updates. It takes a callback function that is continuously called with geofence data. You need to register for updates first by using the [createGeofenceList()](#creategeofencelistrequestidgeofencesinitconversioncoordinatetype) function. |
| void                                            | [removeGeofenceEventListener(fn)](#removegeofenceeventlistenerfn)                                                                          | This function removes the event listener that is added by [addGeofenceEventListener()](#addgeofenceeventlistenerfn)                                                                                                                                                                  |

---

##### Functions

###### createGeofenceList(requestId,geofences,initConversion,coordinateType)

This API is used to add geofences. When a geofence is triggered, a notification is broadcasted. You can subscribe to the broadcast by using [addGeofenceEventListener()](#addgeofenceeventlistenerfn) function.

| Parameter      | Type                    | Description                                                                                        |
| -------------- | ----------------------- | -------------------------------------------------------------------------------------------------- |
| requestId      | number                  | Geofence request id                                                                                |
| geofences      | [Geofence](#geofence)[] | Array of [Geofence](#geofence) objects.                                                            |
| initConversion | number                  | Geofence convert type. Please refer to [Init Constants](#geofencerequestconstants) to see options. |
| coordinateType | number                  | Coordinate type. Please refer to [Type Constants](#geofencerequestconstants) to see options        |

| Return Type                           | Description                                                                             |
| ------------------------------------- | --------------------------------------------------------------------------------------- |
| Promise\<[RequestCode](#requestcode)> | Promise that resolves [RequestCode](#requestcode) object if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const geofence1 = {
    longitude: 42.0,
    latitude: 29.0,
    radius: 20.0,
    uniqueId: 'e00322',
    conversions: 1,
    validContinueTime: 10000.0,
    dwellDelayTime: 10,
    notificationInterval: 1,
};

const geofence2 = {
    longitude: 41.0,
    latitude: 27.0,
    radius: 340.0,
    uniqueId: 'e00491',
    conversions: 2,
    validContinueTime: 1000.0,
    dwellDelayTime: 10,
    notificationInterval: 1,
};

const geofenceRequest = {
    geofences: [geofence1, geofence2],
    conversions: 1,
    coordinate: 1,
};
const requestId = 60;
HMSLocation.Geofence.Native.createGeofenceList(
    requestId,
    geofenceRequest.geofences,
    geofenceRequest.conversions,
    geofenceRequest.coordinate,
)
    .then(res => console.log(res))
    .catch(err => console.log('ERROR: GeofenceList creation failed', err));
```

---

###### deleteGeofenceList(requestId)

This API is used to remove geofences by their request code.

| Parameter | Type   | Description                                                                                                             |
| --------- | ------ | ----------------------------------------------------------------------------------------------------------------------- |
| requestId | number | Request id given to [createGeofenceList()](#creategeofencelistrequestidgeofencesinitconversioncoordinatetype) function. |

| Return Type       | Description                                               |
| ----------------- | --------------------------------------------------------- |
| Promise\<boolean> | Promise that resolves true if the operation is successful |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const requestId = 60;
HMSLocation.Geofence.Native.deleteGeofenceList(requestId)
    .then(res => console.log(res))
    .catch(err => console.log('ERROR: GeofenceList deletion failed', err))
```

---

###### registerGeofenceHeadlessTask(fn)

This API is used to register headless task to obtain geofence result when the application is in the background or killed state. You need to register for updates to get geofence result by using the [createGeofenceList()](#creategeofencelistrequestidgeofencesinitconversioncoordinatetype) function.

| Parameter | Type                                        | Description                                                                   |
| --------- | ------------------------------------------- | ----------------------------------------------------------------------------- |
| fn        | (res:[GeofenceData](#geofencedata)) => void | Callback function that takes [GeofenceData](#geofencedata) object as argument |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
HMSLocation.Geofence.Events.registerGeofenceHeadlessTask((data) =>
  console.log('Geofence Headless Task, data:', data)
);  
// then register the application component
// AppRegistry.registerComponent(appName, () => App);
```

---

###### addGeofenceEventListener(fn)

This API is used to subscribe geofence updates. It takes a callback function that is continuously called with geofence data. You need to register for updates first by using the [createGeofenceList()](#creategeofencelistrequestidgeofencesinitconversioncoordinatetype) function.

| Parameter | Type                                        | Description                                                                   |
| --------- | ------------------------------------------- | ----------------------------------------------------------------------------- |
| fn        | (res:[GeofenceData](#geofencedata)) => void | Callback function that takes [GeofenceData](#geofencedata) object as argument |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const handleGeofenceEvent = geo => {
    console.log('GEOFENCE : ', geo);
};
HMSLocation.Geofence.Events.addGeofenceEventListener(
    handleGeofenceEvent,
);
```

---

###### removeGeofenceEventListener(fn)

This API is used to remove the event listener that is added by [addGeofenceEventListener()](#addgeofenceeventlistenerfn)

| Parameter | Type                                        | Description                                                                   |
| --------- | ------------------------------------------- | ----------------------------------------------------------------------------- |
| fn        | (res:[GeofenceData](#geofencedata)) => void | Callback function that takes [GeofenceData](#geofencedata) object as argument |

Sample Code:

```jsx
import HMSLocation from '@hmscore/react-native-hms-location';
const handleGeofenceEvent = geo => {
    console.log('GEOFENCE : ', geo);
};
HMSLocation.Geofence.Events.removeGeofenceEventListener(
    handleGeofenceEvent,
)
```

---

##### Events

| Event      | Description                       |
| ---------- | --------------------------------- |
| onGeofence | Event emitted on geofence update. |

---

### Data Types

#### RequestCode

| Field       | Type   | Description  |
| ----------- | ------ | ------------ |
| requestCode | number | Request code |

---

#### LocationAvailability

| Fields              | Type    | Description                                    |
| ------------------- | ------- | ---------------------------------------------- |
| isLocationAvailable | boolean | Indicates if the location is available or not. |

---

#### Location

A simple object that contains data about location.

| Field                        | Type    |                                                                                                                         |
| ---------------------------- | ------- | ----------------------------------------------------------------------------------------------------------------------- |
| latitude                     | number  | Latitude of a location. If no latitude is available, 0.0 is returned.                                                   |
| longitude                    | number  | Longitude of a location. If no longitude is available, 0.0 is returned.                                                 |
| speed                        | number  | Speed of a device at the current location, in meters per second. If no speed is available, 0.0 is returned.             |
| bearing                      | number  | Bearing of a device at the current location, in degrees. If no bearing is available, 0.0 is returned.                   |
| accuracy                     | number  | Horizontal error of a location, in meters. If no horizontal error is available, 0.0 is returned.                        |
| verticalAccuracyMeters       | number  | Vertical error of a location, in meters. If no vertical error is available, 0.0 is returned.                            |
| bearingAccuracyDegrees       | number  | Bearing error of the current location, in degrees. If no bearing error is available, 0.0 is returned.                   |
| speedAccuracyMetersPerSecond | number  | Speed error of a device at the current location, in meters per second. If no speed error is available, 0.0 is returned. |
| time                         | number  | Current timestamp, in milliseconds.                                                                                     |
| fromMockProvider             | boolean | Indicates whether location coming from mock provider.                                                                   |

---

#### LocationResult

A result object that contains data about location.

| Field          | Type                        |                                                                                     |
| -------------- | --------------------------- | ----------------------------------------------------------------------------------- |
| lastLocation   | [Location](#location)       | Available location of the last request.                                             |
| locations      | [Location](#location)[]     | Set of available locations.                                                         |
| lastHWLocation | [HWLocation](#hwlocation)   | Available location of the last request, including the detailed address information. |
| hwLocationList | [HWLocation](#hwlocation)[] | List of available locations, including the detailed address information.            |

---

#### HWLocation

A simple object that contains data about location.

| Fields                 | Type   | Description                                                                                                                                        |
| ---------------------- | ------ | -------------------------------------------------------------------------------------------------------------------------------------------------- |
| latitude               | number | Latitude of a location. If no latitude is available, 0.0 is returned.                                                                              |
| longitude              | number | Longitude of a location. If no longitude is available, 0.0 is returned.                                                                            |
| altitude               | number | Altitude of the current location. If no altitude is available, 0.0 will be returned.                                                               |
| speed                  | number | Speed of a device at the current location, in meters per second. If no speed is available, 0.0 is returned.                                        |
| bearing                | number | Bearing of a device at the current location, in degrees. If no bearing is available, 0.0 is returned.                                              |
| accuracy               | number | Horizontal error of a location, in meters. If no horizontal error is available, 0.0 is returned.                                                   |
| provider               | string | Location method, such as network location, GNSS, Wi-Fi, and Bluetooth. If no location method is available, null will be returned.                  |
| time                   | number | Current timestamp, in milliseconds.                                                                                                                |
| elapsedRealtimeNanos   | number | Time elapsed since system boot, in nanoseconds.                                                                                                    |
| countryName            | string | Country name. If no country name is available, null will be returned.                                                                              |
| state                  | string | Administrative region. If no administrative region is available, null will be returned.                                                            |
| city                   | string | City. If no city is available, null will be returned.                                                                                              |
| county                 | string | District/county. If no district/county is available, null will be returned.                                                                        |
| street                 | string | Street. If no street is available, null will be returned.                                                                                          |
| featureName            | string | Feature building of the current location. If no feature building is available, null will be returned.                                              |
| postalCode             | string | Postal code of the current location. If no postal code is available, null will be returned.                                                        |
| phone                  | string | Phone number of the feature building (such as a store or company) of the current location. If no phone number is available, null will be returned. |
| url                    | string | Website of the feature building (such as a store or company) of the current location. If no website is available, null will be returned.           |
| extraInfo              | string | Additional information, which is a key-value pair. If no additional information is available, null will be returned.                               |
| verticalAccuracyMeters | number | Vertical error of a location, in meters. If no vertical error is available, 0.0 is returned.                                                       |
| bearingAccuracyDegrees | number | Bearing error of the current location, in degrees. If no bearing error is available, 0.0 is returned.                                              |

---

#### LocationRequest

A simple object that contains required information for location request.

| Field                  | Type    | Description                                                                                                                                                                                                  |
| ---------------------- | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| priority               | number  | Request priority. The default value is 100.                                                                                                                                                                  |
| interval               | number  | Request interval, in milliseconds. The default value is 3600000.                                                                                                                                             |
| numUpdates             | number  | Number of requested location updates.                                                                                                                                                                        |
| fastestInterval        | number  | Shortest request interval, in milliseconds. The default value is 600000. If another app initiates a location request, the location is also reported to the app at the interval specified in fastestInterval. |
| expirationTime         | number  | Request expiration time, in milliseconds.                                                                                                                                                                    |
| expirationTimeDuration | number  | Request expiration duration, in milliseconds.                                                                                                                                                                |
| smallestDisplacement   | number  | Minimum displacement between location updates, in meters.                                                                                                                                                    |
| maxWaitTime            | number  | Maximum waiting timeIndicates whether to return the address information.                                                                                                                                     |
| needAddress            | boolean | Indicates whether to return the address information. The default value is false.                                                                                                                             |
| language               | string  | Language. The value consists of two letters and complies with the ISO 639-1 international standard. By default, the value is empty.                                                                          |
| countryCode            | string  | Country code. The value consists of two letters and complies with the ISO 3166-1 international standard. By default, the value is empty.                                                                     |

---

#### LocationSettingsRequest

A simple object that contains required information for location settings request.

| Fields           | Type                                  | Description                                                                                                  |
| ---------------- | ------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| locationRequests | [LocationRequest](#locationrequest)[] | Collection of [LocationRequest](#locationrequest) object.                                                    |
| needBle          | boolean                               | Indicates whether BLE scanning needs to be enabled. The options are true (yes) and false (no).               |
| alwaysShow       | boolean                               | Indicates whether a location is required for the app to continue. The options are true (yes) and false (no). |

---

#### LocationSettingsStates

A simple object that contains location settings states.

| Fields                   | Type    | Description                                                                    |
| ------------------------ | ------- | ------------------------------------------------------------------------------ |
| isBlePresent             | boolean | Indicates whether the Bluetooth function is available.                         |
| isBleUsable              | boolean | Indicates whether the Bluetooth function is enabled.                           |
| isGpsPresent             | boolean | Indicates whether the GNSS function is available.                              |
| isGpsUsable              | boolean | Indicates whether the GNSS function is enabled.                                |
| isLocationPresent        | boolean | Indicates whether the location is available                                    |
| isLocationUsable         | boolean | Indicates whether the location is enabled                                      |
| isNetworkLocationPresent | boolean | Indicates whether the network location function is available.                  |
| isNetworkLocationUsable  | boolean | Indicates whether the network location function is enabled.                    |
| isHMSLocationPresent     | boolean | Indicates whether HMS Core (APK) has the location permission.                  |
| isHMSLocationUsable      | boolean | Indicates whether the location permission has been assigned to HMS Core (APK). |

---

#### LocationPermissionResult

A simple object that contains location permission information.

| Fields             | Type    | Description                                      |
| ------------------ | ------- | ------------------------------------------------ |
| granted            | boolean | Permission is granted or not.                    |
| fineLocation       | boolean | Fine location permission is granted or not.      |
| coarseLocation     | boolean | Coarse location permission is granted or not.    |
| backgroundLocation | boolean | ackground location permission is granted or not. |

---

#### LatLng

A simple object that contains required information about latitude and longitude.

| Fields    | Type   | Description              |
| --------- | ------ | ------------------------ |
| longitude | number | Longitude of a location. |
| latitude  | number | Latitude of a location.  |

---

#### NavigationResult

A simple object that contains required information about navigation type.

| Fields      | Type   |                                       |
| ----------- | ------ | ------------------------------------- |
| state       | string | Status information.                   |
| possibility | string | Confidence of the status information. |

---

#### HasPermissionResult

| Fields        | Type    | Description                                      |
| ------------- | ------- | ------------------------------------------------ |
| hasPermission | boolean | Indicates if the permission is available or not. |

---

#### ActivityConversionInfo

A simple object that contains information about activity conversions.

| Fields         | Type   | Description                                                                                                                                                  |
| -------------- | ------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| conversionType | number | Activity conversion information. The [options](#activitiyconversions) are  `Activities.ENTER_ACTIVITY_CONVERSION` and `Activities.EXIT_ACTIVITY_CONVERSION`. |
| activityType   | number | Activity type. Please refer to [Constants](#activities) to see options.                                                                                      |

---

#### ActivityConversionData

A simple object that contains information about activity conversions.

| Fields                | Type   | Description                                                                                                                                                  |
| --------------------- | ------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| activityType          | number | Activity type. Please refer to [Constants](#activities) to see options.                                                                                      |
| elapsedTimeFromReboot | number | The elapsed real time (in milliseconds) of this conversion since boot, including sleeping time obtained by SystemClock.elapsedRealtime().                    |
| conversionType        | number | Activity conversion information. The [options](#activitiyconversions) are  `Activities.ENTER_ACTIVITY_CONVERSION` and `Activities.EXIT_ACTIVITY_CONVERSION`. |

---

#### ActivityConversionRequest

| Fields         | Type   | Description                               |
| -------------- | ------ | ----------------------------------------- |
| conversionType | number | [Conversion](#activitiyconversions) type. |
| activityType   | number | [Activity](#activities) type.             |

---

#### ActivityConversionResponse

A simple object that contains activity conversion response.

| Fields                  | Type                                                | Description                                                                                                       |
| ----------------------- | --------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| activityConversionDatas | [ActivityConversionData](#activityconversiondata)[] | All activity conversion events in the result. The obtained activity events are sorted by time in ascending order. |

---

#### ActivityIdentificationData

A simple object that contains activity conversion data.

| Fields                 | Type   | Description             |
| ---------------------- | ------ | ----------------------- |
| possibility            | number | Confidence information. |
| identificationActivity | number | Detected activity type. |

---

#### ActivityIdentificationResponse

A simple object that contains activity identification response.

| Fields                      | Type                                                        | Description                                                                                                                              |
| --------------------------- | ----------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| elapsedTimeFromReboot       | number                                                      | The elapsed real time (in milliseconds) of this detection since boot, including sleeping time obtained by SystemClock.elapsedRealtime(). |
| mostActivityIdentification  | number                                                      | The most probable activity identification of the user.                                                                                   |
| activityIdentificationDatas | [ActivityIdentificationData](#activityidentificationdata)[] | The list of activitiy identification list. The activity identifications are sorted by most probable activity first.                      |
| time                        | number                                                      | The time of this identification, which is in milliseconds since January 1, 1970,obtained by System.currentTimeMillis().                  |

---

#### ActivityPermissionResult

A simple object that contains location permission information.

| Fields              | Type    | Description                       |
| ------------------- | ------- | --------------------------------- |
| granted             | boolean | Permission is granted or not.     |
| activityRecognition | boolean | Activitiy recognation permission. |

---

#### Geofence

A simple object that contains information about geofence.

| Fields               | Type   | Description                                                                                                                                                                            |
| -------------------- | ------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| longitude            | number | Longitude. The value range is [-180,180].                                                                                                                                              |
| latitude             | number | Latitude. The value range is [-90,90].                                                                                                                                                 |
| radius               | number | Radius, in meters.                                                                                                                                                                     |
| uniqueId             | string | Unique ID. If the unique ID already exists, the new geofence will overwrite the old one.                                                                                               |
| conversions          | number | Geofence conversions. The bitwise-OR operation is supported.                                                                                                                           |
| validContinueTime    | number | Geofence timeout interval, in milliseconds. The geofence will be automatically deleted after this amount of time.                                                                      |
| dwellDelayTime       | number | Geofence timeout interval, in milliseconds. The geofence will be automatically deleted after this amount of time.                                                                      |
| notificationInterval | number | Notification response capability. The default value is 0. Setting it to a larger value can reduce power consumption accordingly. However, reporting of geofence events may be delayed. |

---

#### GeofenceResponse

A geofence response object.

| Fields   | Type   | Description |
| -------- | ------ | ----------- |
| uniqueId | string | Unique id   |

---

#### GeofenceData

A geofence data object.

| Fields                 | Type                                    |                                                       |
| ---------------------- | --------------------------------------- | ----------------------------------------------------- |
| convertingGeofenceList | [GeofenceResponse](#geofenceresponse)[] | Information about converted geofences.                |
| conversion             | number                                  | Geofence convert type.                                |
| convertingLocation     | [Location](#location)                   | The location when a geofence is converted.            |
| errorCode              | number                                  | Error code. For details, please refer to Error Codes. |
| errorMessage           | string                                  | Detailed error message.                               |

### Constants

#### FusedLocation Constants

##### PriorityConstants

| Name                             | Type   | Value | Description                                                                                                                                                                                               |
| -------------------------------- | ------ | ----- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| PRIORITY_BALANCED_POWER_ACCURACY | number | `102` | Used to request the block-level location.                                                                                                                                                                 |
| PRIORITY_HIGH_ACCURACY           | number | `100` | Used to request the most accurate location.                                                                                                                                                               |
| PRIORITY_LOW_POWER               | number | `104` | Used to request the city-level location.                                                                                                                                                                  |
| PRIORITY_NO_POWER                | number | `105` | Used to request the location with the optimal accuracy without additional power consumption.                                                                                                              |
| PRIORITY_HD_ACCURACY             | number | `200` | Used to request the high-precision location information. Currently, this parameter is available only for the [requestLocationUpdatesWithCallbackEx](#requestlocationupdateswithcallbackexrequest) method. |

---

##### NavigationRequestConstants

| Name          | Type   | Value | Description                                                        |
| ------------- | ------ | ----- | ------------------------------------------------------------------ |
| IS_SUPPORT_EX | number | `2`   | Used to check whether the device supports high-precision location. |

---

#### ActivityIdentification Constants

##### Activities

| Name    | Type   | Value | Description                                                  |
| ------- | ------ | ----- | ------------------------------------------------------------ |
| VEHICLE | number | `100` | The device user is in a vehicle, such as car.                |
| BIKE    | number | `101` | The device user is on a bicycle.                             |
| FOOT    | number | `102` | The device user is walking or running.                       |
| RUNNING | number | `108` | The device user is running, which is a sub-activity of FOOT. |
| STILL   | number | `103` | The device user is still.                                    |
| OTHERS  | number | `104` | The device is in other status                                |
| WALKING | number | `107` | The device user is walking. It is a sub-activity of FOOT     |

---

##### ActivitiyConversions

| Name                      | Type   | Value | Description                      |
| ------------------------- | ------ | ----- | -------------------------------- |
| ENTER_ACTIVITY_CONVERSION | number | `0`   | A user enters the given activity |
| EXIT_ACTIVITY_CONVERSION  | number | `1`   | A user exits the given activity. |

---

#### Geofence Constants

##### GeofenceConstants

| Name                      | Type   | Value | Description                                         |
| ------------------------- | ------ | ----- | --------------------------------------------------- |
| ENTER_GEOFENCE_CONVERSION | number | `1`   | A user enters the geofence.                         |
| EXIT_GEOFENCE_CONVERSION  | number | `2`   | A user exits the geofence.                          |
| DWELL_GEOFENCE_CONVERSION | number | `4`   | A user lingers in the geofence.                     |
| GEOFENCE_NEVER_EXPIRE     | number | `-1`  | No timeout interval is configured for the geofence. |

---

##### GeofenceRequestConstants

| Name                   | Type   | Value | Description                                                                                                                                                                         |
| ---------------------- | ------ | ----- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| ENTER_INIT_CONVERSION  | number | `1`   | `ENTER_GEOFENCE_CONVERSION` is triggered immediately when a request is initiated to add the geofence where a user device has already entered.                                       |
| EXIT_INIT_CONVERSION   | number | `2`   | `EXIT_GEOFENCE_CONVERSION` is triggered immediately when a request is initiated to add the geofence where a user device has already exited.                                         |
| DWELL_INIT_CONVERSION  | number | `4`   | `DWELL_GEOFENCE_CONVERSION` is triggered immediately when a request is initiated to add the geofence where a user device has already entered and stayed for the specified duration. |
| COORDINATE_TYPE_WGS_84 | number | `1`   | Use the coordinate type WGS_84                                                                                                                                                      |
| COORDINATE_TYPE_GCJ_02 | number | `0`   | Use the coordinate type GCJ_02.                                                                                                                                                     |

---

##### ErrorCodes

| Name                              | Type   | Value   | Description                                                                                       |
| --------------------------------- | ------ | ------- | ------------------------------------------------------------------------------------------------- |
| GEOFENCE_NUMBER_OVER_LIMIT        | number | `10201` | The used geofences amount has reached the maximum limitation.                                     |
| GEOFENCE_PENDINGINTENT_OVER_LIMIT | number | `10202` | The used pendingIntent amount (5 pendingIntents) in the geofences reached the maximum limitation. |
| GEOFENCE_INSUFFICIENT_PERMISSION  | number | `10204` | Insufficient permission to perform geofence-related operations.                                   |
| GEOFENCE_REQUEST_TOO_OFTEN        | number | `10205` | Geofences are added too frequently.                                                               |
| GEOFENCE_UNAVAILABLE              | number | `10200` | The geofence service is unavailable.                                                              |

---

## 4. Configuration and Description

### Configuring Obfuscation Scripts

Do not obfuscate HMS Core SDK before compiling the APK. Otherwise, function errors may occur. The configuration file in Android Studio is **proguard-rules.pro**

1. Open the obfuscation configuration file of your Android project.

2. Add configuration to ensure that HMS Core SDK is not obfuscated.

```text
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

3. If you have used AndResGuard, add the AndResGuard allowlist to the obfuscation configuration file.

```text
"R.string.hms*","R.string.connect_server_fail_prompt_toast",
"R.string.getting_message_fail_prompt_toast",
"R.string.no_available_network_prompt_toast",
"R.string.third_app_*",
"R.string.upsdk_*",
"R.layout.hms*",
"R.layout.upsdk_*",
"R.drawable.upsdk*",
"R.color.upsdk*",
"R.dimen.upsdk*",
"R.style.upsdk*
```

---

## 5. Sample Project

This plugin includes a demo project in the [example](example) folder, there you can find more usage examples.

---

## 6. Questions or Issues

If you have questions about how to use HMS samples, try the following options:

- [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services) is the best place for any programming questions. Be sure to tag your question with **huawei-mobile-services**.
- [Github](https://github.com/HMS-Core/hms-react-native-plugin) is the official repository for these plugins, You can open an issue or submit your ideas.
- [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001) HMS Core Module is great for general questions, or seeking recommendations and opinions.
- [Huawei Developer Docs](https://developer.huawei.com/consumer/en/doc/overview/HMS-Core-Plugin) is place to official documentation for all HMS Core Kits, you can find detailed documentations in there.

If you run into a bug in our samples, please submit an issue to the [GitHub repository](https://github.com/HMS-Core/hms-react-native-plugin).

---

## 7. Licensing and Terms

Huawei React-Native Plugin is licensed under [Apache 2.0 license](LICENCE)
