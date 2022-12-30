/*
    Copyright 2020-2022. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

declare module "@hmscore/react-native-hms-location" {

    
    const HMSFusedLocation = {
        enableBackgroundLocation(id: number, notification: Notification): Promise<boolean>;,
        disableBackgroundLocation(): Promise<boolean>;,
        flushLocations(): Promise<boolean>;,
        checkLocationSettings(request: LocationSettingsRequest): Promise<LocationSettingsStates>;,
        getLastLocation(): Promise<Location>;,
        getNavigationContextState(request: NavigationRequestConstants): Promise<NavigationResult>;,
        getLastLocationWithAddress(request: LocationRequest): Promise<HWLocation>;,
        getLocationAvailability(): Promise<object>;,
        setMockLocation(location: LatLng): Promise<boolean>;,
        setMockMode(shouldMock: boolean): Promise<boolean>;,
        setLogConfig(logConfig: LogConfig): Promise<boolean>;, 
        getLogConfig(): Promise<LogConfig>;,
        requestLocationUpdates(requestId: number, request: LocationRequest): Promise<RequestCode>;,
        removeLocationUpdates(requestId: number): Promise<boolean>;,
        requestLocationUpdatesWithCallback(request: LocationRequest): Promise<RequestCode>;,
        requestLocationUpdatesWithCallbackEx(request: LocationRequest): Promise<RequestCode>;,
        removeLocationUpdatesWithCallback(requestId: number): Promise<boolean>;,
        hasPermission(): Promise<HasPermissionResult>;,
        NavigationRequestConstants,
        PriorityConstants
    }

    const FusedLocation = {
        Native: HMSFusedLocation,
        Events: {
            registerFusedLocationHeadlessTask(callback: (res: LocationResult) => void): void;,
            addFusedLocationEventListener(callback: (res: LocationResult) => void): void;,
            removeFusedLocationEventListener(callback: (res: LocationResult) => void): void;
        },
        
    }

    export interface HasPermissionResult {
        hasPermission: boolean
    }
    
    export interface HWLocation {
        latitude: number,
        longitude: number,
        altitude: number,
        speed: number,
        bearing: number,
        accuracy: number,
        provider: string,
        time: number,
        elapsedRealtimeNanos: number,
        countryName: string,
        state: string,
        city: string,
        country: string,
        street: string,
        featureName: string,
        postalCode: string,
        phone: string,
        url: string,
        extraInfo: string,
        verticalAccuracyMeters: number,
        bearingAccuracyDegrees: number
    }

    export interface LatLng {
        longitude: number,
        latitude: number
    }

    export interface Location {
        latitude: number,
        longitude: number,
        speed: number,
        bearing: number,
        accuracy: number,
        verticalAccuracyMeters: number,
        bearingAccuracyDegrees: number,
        speedAccuracyMetersPerSecond: number,
        time: number,
        fromMockProvider: boolean
    }

    export interface LocationResult {
        lastLocation: Location,
        locations: Location[],
        lastHWLocation: HWLocation,
        hwLocationList: HWLocation[]
    }

    export interface LocationRequest {
        priority: number,
        interval: number,
        numUpdates: number,
        fastestInterval: number,
        expirationTime: number,
        expirationTimeDuration: number,
        smallestDisplacement: number,
        maxWaitTime: number,
        needAddress: boolean,
        language: string,
        countryCode: string,
        coordinateType: number
    }

    export interface LocationSettingsRequest {
        locationRequests: LocationRequest[],
        needBle: boolean,
        alwaysShow: boolean
    }

    export interface LocationSettingsStates {
        isBlePresent: boolean,
        isBleUsable: boolean,
        isGpsPresent: boolean,
        isGpsUsable: boolean,
        isGnssPresent: boolean,
        isGnssUsable: boolean,
        isLocationPresent: boolean,
        isLocationUsable: boolean,
        isNetworkLocationPresent: boolean,
        isNetworkLocationUsable: boolean,
        isHMSLocationPresent: boolean,
        isHMSLocationUsable: boolean
    }

    export interface LogConfig {
        logPath: string,
        fileSize: number,
        fileNum: number,
        fileExpiredTime: number
    }

    export interface NavigationResult {
        state: string,
        possibility: string
    }
    
    export interface Notification {
        category: string,
        priority: number,
        channelName: string,
        contentTitle:string,
        contentText: string,
        defType?: string,
        resourceName?: string,
        color?: number,
        colorized?: boolean,
        contentInfo?: string,
        largeIcon?: string,
        onGoing?: boolean,
        subText?: string,
        sound?: string,
        vibrate?: number[],
        visibility?: number
    }

    export interface RequestCode {
        requestCode: number
    }


    enum NavigationRequestConstants {
        IS_SUPPORT_EX = 2,
        OVERPASS = 1
    }

    enum PriorityConstants {
        PRIORITY_BALANCED_POWER_ACCURACY = 102,
        PRIORITY_HIGH_ACCURACY = 100,
        PRIORITY_LOW_POWER = 104,
        PRIORITY_NO_POWER = 105,
        PRIORITY_HD_ACCURACY = 200,
        PRIORITY_INDOOR = 300,
        PRIORITY_HIGH_ACCURACY_AND_INDOOR = 400
    }

    const HMSGeofence = {
        createGeofenceList(requestId: number, geofences: Geofence[], initConversions: number, coordinateType: number): Promise<RequestCode>;,
        deleteGeofenceList(requestId: number): Promise<boolean>;,
        GeofenceConstants,
        GeofenceRequestConstants,
        ErrorCodes
    }

    const Geofence = {
        Native: HMSGeofence,
        Events: {
            registerGeofenceHeadlessTask(callback: (res: GeofenceData) => void): void;,
            addGeofenceEventListener(callback: (res: GeofenceData) => void): void;,
            removeGeofenceEventListener(callback: (res: GeofenceData) => void): void;
        }
    }

    export interface Geofence {
        longitude: number,
        latitude: number,
        radius: number,
        uniqueId: string,
        conversions: number,
        validContinueTime: number,
        dwellDelayTime: number,
        notificationInterval: number
    }

    export interface GeofenceData {
        convertingGeofenceList: GeofenceResponse[],
        conversion: number,
        convertingLocation: Location,
        errorCode: number,
        errorMessage: string
    }

    export interface GeofenceResponse {
        uniqueId: string
    }

    enum GeofenceConstants {
        ENTER_GEOFENCE_CONVERSION = 1,
        EXIT_GEOFENCE_CONVERSION = 2,
        DWELL_GEOFENCE_CONVERSION = 4,
        GEOFENCE_NEVER_EXPIRE = -1
    }

    enum GeofenceRequestConstants {
        ENTER_INIT_CONVERSION = 1,
        EXIT_INIT_CONVERSION = 2,
        DWELL_INIT_CONVERSION = 4,
        COORDINATE_TYPE_WGS_84 = 1,
        COORDINATE_TYPE_GCJ_02 = 0
    }

    enum ErrorCodes {
        GEOFENCE_NUMBER_OVER_LIMIT = 10201,
        GEOFENCE_PENDINGINTENT_OVER_LIMIT = 10202,
        GEOFENCE_INSUFFICIENT_PERMISSION = 10204,
        GEOFENCE_REQUEST_TOO_OFTEN = 10205,
        GEOFENCE_UNAVAILABLE = 10200
    }

    const HMSLocationKit = {
        init(): Promise<boolean>;,
        enableLogger(): Promise<boolean>;,
        disableLogger(): Promise<boolean>;,
        setNotification(notificationSetting: BasicNotification): Promise<boolean>;,
        convertCoord(latitude: number, longitude: number, coordType: number): Promise<LatLng>;
    }

    const LocationKit = {
        Native: HMSLocationKit
    }

    export interface BasicNotification {
        contentTitle: string,
        contentText: string,
        defType: string,
        resourceName: string,
    }

    const HMSActivityIdentification = {
        createActivityConversionUpdates(requestId: number, request: ActivityConversionRequest[]): Promise<RequestCode>;,
        deleteActivityConversionUpdates(requestId: number): Promise<boolean>;,
        createActivityIdentificationUpdates(requestId: number, intervalMillis: number): Promise<RequestCode>;,
        deleteActivityIdentificationUpdates(requestId: number): Promise<boolean>;,
        requestPermission(): Promise<ActivityPermissionResult>;,
        hasPermission(): Promise<HasPermissionResult>;,
        Activities,
        ActivitiyConversions
    }

    const ActivityIdentification = {
        Native: HMSActivityIdentification,
        Events: {
            registerActivityIdentificationHeadlessTask(callback: (res: ActivityConversionResponse) => void): void;,
            addActivityIdentificationEventListener(callback: (res: ActivityIdentificationResponse) => void): void;,
            removeActivityIdentificationEventListener(callback: (res: ActivityIdentificationResponse) => void): void;,
            registerActivityConversionHeadlessTask(callback: (res: ActivityConversionResponse) => void): void;,
            addActivityConversionEventListener(callback: (res: ActivityConversionResponse) => void): void;,
            removeActivityConversionEventListener(callback: (res: ActivityConversionResponse) => void): void;
        }
    }

    export interface ActivityConversionRequest {
        activityType: Activities,
        conversionType: ActivitiyConversions
    }

    export interface ActivityIdentificationResponse {
        elapsedTimeFromReboot: number,
        mostActivityIdentification: ActivityIdentificationData,
        activityIdentificationDatas: ActivityIdentificationData[],
        time: number
    }

    export interface ActivityPermissionResult {
        granted: boolean,
        activityRecognition: boolean
    }

    export interface ActivityConversionResponse {
        activityConversionDatas: ActivityConversionData[]
    }

    export interface ActivityIdentificationData {
        possibility: number,
        identificationActivity: Activities
    }

    export interface ActivityConversionData {
        activityType: Activities,
        conversionType: ActivitiyConversions,
        elapsedTimeFromReboot: number
    }

    enum Activities {
        VEHICLE = 100,
        BIKE = 101,
        FOOT = 102,
        RUNNING = 108,
        STILL = 103,
        OTHERS = 104,
        WALKING = 107
    }

    enum ActivitiyConversions {
        ENTER_ACTIVITY_CONVERSION = 0,
        EXIT_ACTIVITY_CONVERSION = 1
    }
    
    const HMSGeocoder = {
        getFromLocation(getFromLocationRequest: GetFromLocationRequest, locale: Locale): Promise<HWLocation[]>;,
        getFromLocationName(getFromLocationNameRequest: GetFromLocationNameRequest, locale: Locale): Promise<HWLocation[]>;,
    }

    const Geocoder = {
        Native: HMSGeocoder
    }

    export interface GetFromLocationNameRequest {
        locationName: string,
        maxResults: number,
        lowerLeftLatitude?: number,
        lowerLeftLongitude?: number,
        upperRightLatitude?: number,
        upperRightLongitude?: number
    }
    
    export interface GetFromLocationRequest {
        latitude: number,
        longitude: number,
        maxResults: number
    }

    export interface Locale {
        language: string,
        country?: string,
        variant?: string
    }


    export default {
        FusedLocation,
        Geofence,
        LocationKit,
        ActivityIdentification,
        Geocoder
    }

}