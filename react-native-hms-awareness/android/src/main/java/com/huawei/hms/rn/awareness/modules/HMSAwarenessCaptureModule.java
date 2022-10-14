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

package com.huawei.hms.rn.awareness.modules;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.rn.awareness.wrapper.AwarenessCaptureWrapper;

public class HMSAwarenessCaptureModule extends ReactContextBaseJavaModule {

    private AwarenessCaptureWrapper awarenessCapture;

    public HMSAwarenessCaptureModule(ReactApplicationContext reactContext) {
        super(reactContext);
        awarenessCapture = new AwarenessCaptureWrapper(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSAwarenessCaptureModule";
    }

    /**
     * Uses a variable number of the filters parameters to obtain beacon information.
     * The method is available only on devices with API level 24 or later.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getBeaconStatus(ReadableMap beaconStatusReq, Promise promise) {
        awarenessCapture.getBeaconStatus(beaconStatusReq, promise);
    }

    /**
     * Obtains current user behavior, for example, running, walking, cycling, or driving.
     * The method is available only on devices with API level 29 or later.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getBehavior(Promise promise) {
        awarenessCapture.getBehavior(promise);
    }

    /**
     * Obtains headset connection status.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getHeadsetStatus(Promise promise) {
        awarenessCapture.getHeadsetStatus(promise);
    }

    /**
     * Re-Obtains the current location (latitude and longitude) of a device.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getLocation(Promise promise) {
        awarenessCapture.getLocation(promise);
    }

    /**
     * Obtains the current location (latitude and longitude) of a device.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getCurrentLocation(Promise promise) {
        awarenessCapture.getCurrentLocation(promise);
    }

    /**
     * Obtains the current time.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getTimeCategories(Promise promise) {
        awarenessCapture.getTimeCategories(promise);
    }

    /**
     * Obtains the current time of a specified location.
     *
     * @param timeCategoriesByUserReq : ReadableMap
     * @param promise                 : WritableMap
     */
    @ReactMethod
    public void getTimeCategoriesByUser(ReadableMap timeCategoriesByUserReq, Promise promise) {
        awarenessCapture.getTimeCategoriesByUser(timeCategoriesByUserReq, promise);
    }

    /**
     * Obtains the current time by country/region code that complies with ISO 3166-1 alpha-2.
     *
     * @param timeCategoriesByCountryCodeReq : ReadableMap<
     * @param promise                        : WritableMap
     */
    @ReactMethod
    public void getTimeCategoriesByCountryCode(ReadableMap timeCategoriesByCountryCodeReq, Promise promise) {
        awarenessCapture.getTimeCategoriesByCountryCode(timeCategoriesByCountryCodeReq, promise);
    }

    /**
     * Obtains the current time by IP address.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getTimeCategoriesByIP(Promise promise) {
        awarenessCapture.getTimeCategoriesByIP(promise);
    }

    /**
     * Obtains the time of a specified date by IP address.
     *
     * @param timeCategoriesForFutureReq: ReadableMap
     * @param promise                     : WritableMap
     */
    @ReactMethod
    public void getTimeCategoriesForFuture(ReadableMap timeCategoriesForFutureReq, Promise promise) {
        awarenessCapture.getTimeCategoriesForFuture(timeCategoriesForFutureReq, promise);
    }

    /**
     * Obtains the illuminance.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getLightIntensity(Promise promise) {
        awarenessCapture.getLightIntensity(promise);
    }

    /**
     * Obtains the weather of the current location of a device.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getWeatherByDevice(Promise promise) {
        awarenessCapture.getWeatherByDevice(promise);
    }

    /**
     * Obtains weather information about a specified address.
     *
     * @param weatherByPositionReq: ReadableMap
     * @param promise               : WritableMap
     */
    @ReactMethod
    public void getWeatherByPosition(ReadableMap weatherByPositionReq, Promise promise) {
        awarenessCapture.getWeatherByPosition(weatherByPositionReq, promise);
    }

    /**
     * Obtains the Bluetooth connection status.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getBluetoothStatus(Promise promise) {
        awarenessCapture.getBluetoothStatus(promise);
    }

    /**
     * Obtains capabilities supported by Awareness Kit on the current device.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void querySupportingCapabilities(Promise promise) {
        awarenessCapture.querySupportingCapabilities(promise);
    }

    /**
     * Indicates whether to display a dialog box before
     * Awareness Kit or HMS Core (APK) starts an upgrade in your app.
     *
     * @param isEnabled : boolean
     * @param promise   : WritableMap
     */
    @ReactMethod
    public void enableUpdateWindow(boolean isEnabled, Promise promise) {
        awarenessCapture.enableUpdateWindow(isEnabled, promise);
    }

    /**
     * Obtains the screen status response of a device.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getScreenStatus(Promise promise) {
        awarenessCapture.getScreenStatus(promise);
    }

    /**
     * Obtains the Wi-Fi connection status of a device.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getWifiStatus(Promise promise) {
        awarenessCapture.getWifiStatus(promise);
    }

    /**
     * Obtains the app status of a device.
     *
     * @param promise : WritableMap
     */
    @ReactMethod
    public void getApplicationStatus(Promise promise) {
        awarenessCapture.getApplicationStatus(promise);
    }

    /**
     * Obtains the dark mode status of a device.
     *
     * @param promise : WritableMap
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @ReactMethod
    public void getDarkModeStatus(Promise promise) {
        awarenessCapture.getDarkModeStatus(promise);
    }
}