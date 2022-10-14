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

package com.huawei.hms.rn.awareness.wrapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.common.StandardCharsets;
import com.huawei.hms.kit.awareness.Awareness;
import com.huawei.hms.kit.awareness.capture.WeatherPosition;
import com.huawei.hms.kit.awareness.status.BeaconStatus;
import com.huawei.hms.rn.awareness.logger.HMSLogger;
import com.huawei.hms.rn.awareness.utils.DataUtils;
import com.huawei.hms.rn.awareness.utils.PermissionUtils;

import java.util.Objects;

import static com.huawei.hms.kit.awareness.status.BluetoothStatus.DEVICE_CAR;
import static com.huawei.hms.rn.awareness.utils.DataUtils.errorMessage;

public class AwarenessCaptureWrapper {

    private ReactContext context;
    private String TAG = "AwarenessCapture::";
    private String WRONG_PARAMS = "Wrong parameter! Please check your parameters.";

    public AwarenessCaptureWrapper(ReactContext reactContext) {
        context = reactContext;
    }

    @SuppressLint("MissingPermission")
    public void getBeaconStatus(ReadableMap map, Promise promise) {
        if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
            PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
            return;
        }
        if (!PermissionUtils.hasBluetoothPermission(getCurrentActivity())) {
            PermissionUtils.requestBluetoothPermission(getCurrentActivity(), promise);
            return;
        }

        String method = "getBeaconStatus";
        BeaconStatus.Filter filter = null;
        try {
            if (map == null) {
                errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                return;
            }

            if (map.hasKey("beaconId")) {
                String beaconId = map.getString("beaconId");
                if (beaconId == null) {
                    errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                    return;
                }
                filter = BeaconStatus.Filter.match(beaconId);
            } else if (map.hasKey("namespace") && map.hasKey("type") && map.hasKey("type")) {
                String namespace = map.getString("namespace");
                String type = map.getString("type");
                String content = map.getString("content");
                if (namespace == null || type == null || content == null) {
                    errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                    return;
                }
                filter = BeaconStatus.Filter.match(namespace, type, content.getBytes(StandardCharsets.UTF_8));
            } else if (map.hasKey("namespace") && map.hasKey("type")) {
                String namespace = map.getString("namespace");
                String type = map.getString("type");
                if (namespace == null || type == null) {
                    errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                    return;
                }
                filter = BeaconStatus.Filter.match(namespace, type);
            }

            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).getBeaconStatus(filter)
                    .addOnSuccessListener(beaconStatusResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.beaconStatusResponseConvertToMap(beaconStatusResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));

        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void getBehavior(Promise promise) {
        if (!PermissionUtils.hasActivityRecognitionPermission(getCurrentActivity())) {
            PermissionUtils.requestActivityRecognitionPermission(getCurrentActivity(), promise);
            return;
        }
        String method = "getBehavior";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).getBehavior()
                    .addOnSuccessListener(behaviorResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.behaviorResponseConvertToMap(behaviorResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    @SuppressLint("MissingPermission")
    public void getHeadsetStatus(Promise promise) {
        if (!PermissionUtils.hasBluetoothPermission(getCurrentActivity())) {
            PermissionUtils.requestBluetoothPermission(getCurrentActivity(), promise);
            return;
        }
        String method = "getHeadsetStatus";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context)
                    .getHeadsetStatus()
                    .addOnSuccessListener(headsetStatusResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.headsetStatusResponseConvertToMap(headsetStatusResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    @SuppressLint("MissingPermission")
    public void getLocation(Promise promise) {
        if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
            PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
            return;
        }
        String method = "getLocation";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).getLocation()
                    .addOnSuccessListener(locationResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.locationResponseConvertToMap(locationResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation(Promise promise) {
        if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
            PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
            return;
        }
        String method = "getCurrentLocation";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                HMSLogger.getInstance(context).startMethodExecutionTimer(method);
                Awareness.getCaptureClient(context)
                        .getCurrentLocation()
                        .addOnSuccessListener(currentLocationResponse -> {
                            HMSLogger.getInstance(context).sendSingleEvent(method);
                            DataUtils.locationResponseConvertToMap(currentLocationResponse, promise);
                        })
                        .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
            } else {
                String warningMessage = "The Build SDK Version must be greater than 26";
                errorMessage(context, method, TAG, warningMessage, promise);
            }
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    @SuppressLint("MissingPermission")
    public void getTimeCategories(Promise promise) {
        if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
            PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
            return;
        }
        String method = "getTimeCategories";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).getTimeCategories()
                    .addOnSuccessListener(timeCategoriesResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.timeCategoriesResponseConvertToMap(timeCategoriesResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void getTimeCategoriesByUser(ReadableMap map, Promise promise) {
        String method = "getTimeCategoriesByUser";
        try {
            if (map == null || !map.hasKey("latitude") || !map.hasKey("longitude")) {
                errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                return;
            }
            double latitude = map.getDouble("latitude");
            double longitude = map.getDouble("longitude");
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context)
                    .getTimeCategoriesByUser(latitude, longitude)
                    .addOnSuccessListener(timeCategoriesResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.timeCategoriesResponseConvertToMap(timeCategoriesResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void getTimeCategoriesByCountryCode(ReadableMap map, Promise promise) {
        String method = "getTimeCategoriesByCountryCode";
        try {
            if (map == null || !map.hasKey("countryCode")) {
                errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                return;
            }
            String countryCode = map.getString("countryCode");

            if (countryCode == null || countryCode.isEmpty()) {
                errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                return;
            }

            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).getTimeCategoriesByCountryCode(countryCode)
                    .addOnSuccessListener(timeCategoriesResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.timeCategoriesResponseConvertToMap(timeCategoriesResponse, promise);
                        Log.i(TAG, "getTimeCategoriesByIP");
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    @SuppressLint("MissingPermission")
    public void getTimeCategoriesByIP(Promise promise) {
        if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
            PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
            return;
        }
        String method = "getTimeCategoriesByIP";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context)
                    .getTimeCategoriesByIP()
                    .addOnSuccessListener(timeCategoriesResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        Log.i(TAG, "getTimeCategoriesByCountryCode");
                        DataUtils.timeCategoriesResponseConvertToMap(timeCategoriesResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            Log.i(TAG, "Err:getTimeCategoriesByCountryCode");
            errorMessage(context, method, TAG, e, promise);
        }
    }

    @SuppressLint("MissingPermission")
    public void getTimeCategoriesForFuture(ReadableMap map, Promise promise) {
        String method = "getTimeCategoriesForFuture";
        if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
            PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
            return;
        }
        try {
            if (map == null || !map.hasKey("futureTimestamp")) {
                errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                return;
            }
            long featureTimestamp = (long) map.getDouble("futureTimestamp");
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context)
                    .getTimeCategoriesForFuture(featureTimestamp)
                    .addOnSuccessListener(timeCategoriesResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        Log.i(TAG, "getTimeCategoriesForFuture");
                        DataUtils.timeCategoriesResponseConvertToMap(timeCategoriesResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            Log.i(TAG, "Err:getTimeCategoriesForFuture");
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void getLightIntensity(Promise promise) {
        String method = "getLightIntensity";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).getLightIntensity()
                    .addOnSuccessListener(ambientLightResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.ambientLightResponseConvertToMap(ambientLightResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    @SuppressLint("MissingPermission")
    public void getWeatherByDevice(Promise promise) {
        if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
            PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
            return;
        }
        String method = "getWeatherByDevice";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).getWeatherByDevice()
                    .addOnSuccessListener(weatherStatusResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.weatherStatusResponseConvertToMap(weatherStatusResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    @SuppressLint("MissingPermission")
    public void getWeatherByPosition(ReadableMap map, Promise promise) {
        if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
            PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
            return;
        }

        String method = "getWeatherByPosition";
        try {
            if (!hasValidKey(map, "locale", ReadableType.String) || !hasValidKey(map, "city", ReadableType.String)) {
                errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                return;
            }
            WeatherPosition position = DataUtils.weatherPositionReqObjToWeatherPosition(map);
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).getWeatherByPosition(position)
                    .addOnSuccessListener(weatherStatusResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.weatherStatusResponseConvertToMap(weatherStatusResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void getBluetoothStatus(Promise promise) {
        String method = "getBluetoothStatus";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).getBluetoothStatus(DEVICE_CAR)
                    .addOnSuccessListener(bluetoothStatusResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.bluetoothStatusResponseConvertToMap(bluetoothStatusResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void querySupportingCapabilities(Promise promise) {
        String method = "querySupportingCapabilities";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).querySupportingCapabilities()
                    .addOnSuccessListener(capabilityResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.capabilityResponseConvertToMap(capabilityResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void enableUpdateWindow(boolean isEnable, Promise promise) {
        String method = "enableUpdateWindowWithCaptureClient";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).enableUpdateWindow(isEnable);
            HMSLogger.getInstance(context).sendSingleEvent(method);
            DataUtils.valueConvertToMap("isEnabled", isEnable, method, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void getScreenStatus(Promise promise) {
        String method = "getScreenStatus";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context)
                    .getScreenStatus()
                    .addOnSuccessListener(screenStatusResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.screenStatusResponseConvertToMap(screenStatusResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void getWifiStatus(Promise promise) {
        String method = "getWifiStatus";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).getWifiStatus()
                    .addOnSuccessListener(wifiStatusResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.wifiStatusResponseConvertToMap(wifiStatusResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void getApplicationStatus(Promise promise) {
        String method = "getApplicationStatus";
        try {
            String packageName = context != null ? context.getPackageName() : "";
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(Objects.requireNonNull(context))
                    .getApplicationStatus(packageName)
                    .addOnSuccessListener(applicationStatusResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.applicationStatusResponseConvertToMap(applicationStatusResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void getDarkModeStatus(Promise promise) {
        String method = "getDarkModeStatus";
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getCaptureClient(context).getDarkModeStatus()
                    .addOnSuccessListener(darkModeStatusResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        DataUtils.darkModeStatusResponseConvertToMap(darkModeStatusResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    private Activity getCurrentActivity() {
        return context.getCurrentActivity();
    }

    public static boolean hasValidKey(ReadableMap rm, String key, ReadableType type) {
        if (rm == null) {
            return false;
        }
        return rm.hasKey(key) && rm.getType(key) == type;
    }
}