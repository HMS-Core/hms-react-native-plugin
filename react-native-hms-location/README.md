# react-native-hms-location

## Contents
- Introduction
- Installation Guide
- React-Native SDK Method Definition
- Configuration Description
- Licensing and Terms

## 1. Introduction

The React-Native SDK code encapsulates the Huawei location client interface. It provides many APIs for your reference or use.

The React-Native SDK code package is described as follows:

- Android: core layer, bridging Location SDK bottom-layer code;
- src/modules: Android interface layer, which is used to parse the received data, send requests and generate class instances.
- index.js: external interface definition layer, which is used to define interface classes or reference files.

## 2. Installation Guide
Before using Reactive-Native SDK code, ensure that the RN development environment has been installed.

### 2.1 Copy the library into the demo project 

In order to able the library to be used in the demo, the library should be copied under the node_modules folder of the project.
 
The structure should be like this

            hms-location-demo
                |_ node_modules
                    |_ ...
                        react-native-hms-location
                        ...

### 2.2 Add location package to your application
```java
import com.huawei.hms.rn.location.RNHMSLocationPackage
```
Then add the following line to your getPackages() method.
```java
packages.add(new RNHMSLocationPackage()); 
```

### 2.3 Initialize the broadcast receiver within your app


Import the following classes to the MainActivity.java file of your project.
```java
import com.huawei.hms.rn.location.RNHMSLocationPackage;
import com.huawei.hms.rn.location.helpers.HMSBroadcastReceiver;
```
Then add the following line to your onCreate() method: 
```java
HMSBroadcastReceiver.init(this, getReactNativeHost().getReactInstanceManager());
```

## 3. HUAWEI Location Kit SDK for React Native
### Summary

| Fused Location                     |                                                                                                                                                                                                                      |
| :-------------:                    | :-------------:                                                                                                                                                                                                      |
| LocationData                       | Object used used to store location data.                                                                                                                                                                             |
| LocationRequest                    | Object used to store location request information.                                                                                                                                                                   |
| LocationSettingsRequest            | Object used for specifying the location service types and checking the location settings to obtain optimal functionality of all requested services.                                                                  |
| flushLocations                     | This API is used to update locations being processed.                                                                                                                                                                |
| checkLocationSettings              | This API is used to check whether related location settings are available.                                                                                                                                           |
| getLastLocation                    | This API is used to obtain the latest available location.                                                                                                                                                            |
| getLastLocationWithAddress         | This API is used to obtain the available location of the last request, including the detailed address information.                                                                                                   |
| getLocationAvailability            | This API is used to check whether the location data is available.                                                                                                                                                    |
| setMockLocation                    | This API is used to update locations being processed.                                                                                                                                                                |
| setMockMode                        | This API is used to specify whether the location provider uses the location mock mode. If yes, the GPS or network location is not used and the location set through setMockLocation (Location) is directly returned. |
| requestLocationUpdatesWithCallback | This API is used to request location updates.                                                                                                                                                                        |
| removeLocationUpdates              | This API is used to remove location updates of the specified callback information.                                                                                                                                   |
| requestPermission                  | This API is used to request permission to use location services.                                                                                                                                                     |
| hasPermission                      | This API is used to check if the permission to use location services has been granted.                                                                                                                               |
| addFusedLocationEventListener      | This method is used add a listener for fused location events.                                                                                                                                                        |
| removeFusedLocationEventListener   | This method is used to remove the listener added for fused location events.                                                                                                                                          |


| Activity Identification                   |                                                                                                                                    |
| :-------------:                           | :-------------:                                                                                                                    |
| ActivityConversionInfo                    | This object is used to store activity conversion information.                                                                      |
| ActivityConversionData                    | This object is used to store activity conversion event data.                                                                       |
| ActivityConversionRequest                 | This object is used to store activity conversion request information.                                                              |
| ActivityConversionResponse                | This object is used to store activity conversion result information.                                                               |
| ActivityIdentificationData                | This object is used to store activity identification event data.                                                                   |
| ActivityIdentificationResponse            | This object is used to store activity identification result data.                                                                  |
| createActivityConversionUpdates           | This API is used to activity conversions (entering and exit), for example, detecting user status change from walking to bicycling. |
| createActivityIdentificationUpdates       | This API is used to register for activity identification updates.                                                                  |
| deleteActivityConversionUpdates           | This API is used to remove activity conversion updates associated with the given pendingIntent object.                             |
| deleteActivityIdentificationUpdates       | This API is used to remove all activity identification updates from the specified PendingIntent object.                            |
| requestPermission                         | This API is used to request permission to use activity identification services.                                                    |
| hasPermission                             | This API is used to check if the permission to use activity identfication services has been granted.                               |
| addActivityIdentificationEventListener    | This method is used add a listener for activity identification events.                                                             |
| removeActivityIdentificationEventListener | This method is used to remove the listener added for activity identification events.                                               |
| addActivityConversionEventListener        | This method is used add a listener for activity conversion events.                                                                 |
| removeActivityConversionEventListener     | This method is used to remove the listener added for activity conversion events.                                                   |


| GeoFence                    |                                                                                                       |
| :-----:                     | :-------:                                                                                             |
| GeofenceBuilder             | This object is used to store geofence data.                                                           |
| GeofenceRequest             | This object is used to store geofence request information.                                            |
| GeofenceData                | This object is used to store geofence event data.                                                     |
| createGeofenceList          | This API is used to add multiple geofences.                                                           |
| deleteGeofenceList          | This API is used to remove geofences by their request IDs. An error is reported if the list is empty. |
| addGeofenceEventListener    | This method is used add a listener for geofence events.                                               |
| removeGeofenceEventListener | This method is used to remove the listener added for activity conversion events.              

## 4. Configure parameters.    
No.
## 5. Licensing and Terms  
Huawei Reactive-Native SDK uses the Apache 2.0 license.