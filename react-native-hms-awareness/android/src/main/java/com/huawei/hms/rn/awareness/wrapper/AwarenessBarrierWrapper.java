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
import android.app.PendingIntent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.StandardCharsets;
import com.huawei.hms.kit.awareness.Awareness;
import com.huawei.hms.kit.awareness.barrier.AmbientLightBarrier;
import com.huawei.hms.kit.awareness.barrier.AwarenessBarrier;
import com.huawei.hms.kit.awareness.barrier.BarrierQueryRequest;
import com.huawei.hms.kit.awareness.barrier.BarrierUpdateRequest;
import com.huawei.hms.kit.awareness.barrier.BeaconBarrier;
import com.huawei.hms.kit.awareness.barrier.BehaviorBarrier;
import com.huawei.hms.kit.awareness.barrier.BluetoothBarrier;
import com.huawei.hms.kit.awareness.barrier.HeadsetBarrier;
import com.huawei.hms.kit.awareness.barrier.LocationBarrier;
import com.huawei.hms.kit.awareness.barrier.ScreenBarrier;
import com.huawei.hms.kit.awareness.barrier.TimeBarrier;
import com.huawei.hms.kit.awareness.barrier.WifiBarrier;
import com.huawei.hms.kit.awareness.status.BeaconStatus;
import com.huawei.hms.rn.awareness.logger.HMSLogger;
import com.huawei.hms.rn.awareness.utils.PermissionUtils;

import java.util.Objects;
import java.util.TimeZone;

import static com.huawei.hms.kit.awareness.status.BeaconStatus.Filter.match;
import static com.huawei.hms.rn.awareness.constants.Constants.getAllConstants;
import static com.huawei.hms.rn.awareness.utils.DataUtils.barrierQueryResConvertToMap;
import static com.huawei.hms.rn.awareness.utils.DataUtils.errorMessage;
import static com.huawei.hms.rn.awareness.utils.DataUtils.readableArrayConvertToIntArray;
import static com.huawei.hms.rn.awareness.utils.DataUtils.valueConvertToMap;

public class AwarenessBarrierWrapper {

    private AwarenessBarrier awarenessBarrier;
    private ReactContext context;
    private PendingIntent pendingIntent;
    private String barrierEventType;

    private String TAG = "AwarenessBarrier::";
    private String wrongParams = "Wrong parameter! Please check your parameters.";

    public AwarenessBarrierWrapper(@NonNull ReactContext reactContext, @NonNull PendingIntent intent) {
        context = reactContext;
        pendingIntent = intent;
    }

    public void queryBarrier(ReadableArray array, Promise promise) {
        String method = "queryBarrier";
        try {
            int size = array.size();
            String[] labels = new String[size];
            for (int i = 0; i < size; i++) {
                labels[i] = array.getString(i);
            }
            BarrierQueryRequest request = BarrierQueryRequest.forBarriers(labels);
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getBarrierClient(context).queryBarriers(request)
                    .addOnSuccessListener(barrierQueryResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        Log.i(TAG, "queryBarrier");
                        barrierQueryResConvertToMap(barrierQueryResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            Log.i(TAG, "Err:queryBarrier");
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void queryAllBarrier(Promise promise) {
        String method = "queryAllBarrier";
        try {
            BarrierQueryRequest request = BarrierQueryRequest.all();
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getBarrierClient(context).queryBarriers(request)
                    .addOnSuccessListener(barrierQueryResponse -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        Log.i(TAG, "queryAllBarrier");
                        barrierQueryResConvertToMap(barrierQueryResponse, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            Log.i(TAG, "Err:queryAllBarrier");
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void deleteBarrier(ReadableArray labels, Promise promise) {
        String method = "removeBarrier";
        try {
            BarrierUpdateRequest.Builder builder = new BarrierUpdateRequest.Builder();
            for (int i = 0; i < labels.size(); i++) {
                String label = labels.getString(i);
                if (label == null) {
                    errorMessage(null, method, TAG, "The label cannot be null!", promise);
                    return;
                }
                builder.deleteBarrier(label);
            }
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getBarrierClient(context).updateBarriers(builder.build())
                    .addOnSuccessListener(aVoid -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        valueConvertToMap("Response",
                                "Success", method, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void deleteAllBarrier(Promise promise) {
        String method = "deleteAllBarrier";
        try {
            BarrierUpdateRequest.Builder builder = new BarrierUpdateRequest.Builder();
            builder.deleteAll();
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getBarrierClient(context).updateBarriers(builder.build())
                    .addOnSuccessListener(aVoid -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        Log.i(TAG, "deleteAllBarrier");
                        valueConvertToMap("Response",
                                "Success", method, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            Log.i(TAG, "Err:deleteAllBarrier");
            errorMessage(context, method, TAG, e, promise);
        }
    }

    public void updateBarrier(String barrierType, ReadableMap map, Promise promise) {
        if (barrierEventType != null) {
            barrierEventType = null;
        }

        if (map == null || barrierType == null) {
            errorMessage(null, "updateBarrier", TAG, "wrong parameter", promise);
            return;
        }

        barrierEventType = barrierType;

        if (barrierType.equals(getAllConstants().get("EVENT_HEADSET"))) {
            headsetBarrier(map, promise);
        } else if (barrierType.equals(getAllConstants().get("EVENT_AMBIENTLIGHT"))) {
            ambientLightBarrier(map, promise);
        } else if (barrierType.equals(getAllConstants().get("EVENT_WIFI"))) {
            wifiBarrier(map, promise);
        } else if (barrierType.equals(getAllConstants().get("EVENT_BLUETOOTH"))) {
            bluetoothBarrier(map, promise);
        } else if (barrierType.equals(getAllConstants().get("EVENT_BEHAVIOR"))) {
            behaviorBarrier(map, promise);
        } else if (barrierType.equals(getAllConstants().get("EVENT_LOCATION"))) {
            locationBarrier(map, promise);
        } else if (barrierType.equals(getAllConstants().get("EVENT_SCREEN"))) {
            screenBarrier(map, promise);
        } else if (barrierType.equals(getAllConstants().get("EVENT_TIME"))) {
            timeBarrier(map, promise);
        } else if (barrierType.equals(getAllConstants().get("EVENT_BEACON"))) {
            beaconBarrier(map, promise);
        } else if (barrierType.equals(getAllConstants().get("EVENT_UPDATE_WINDOW"))) {
            enableUpdateWindow(map, promise);
        }
    }

    private void addBarrier(String barrierReceiverAction, ReadableMap map, Promise promise) {
        String method = "updateBarrier";
        try {
            if (awarenessBarrier == null) {
                errorMessage(null, method, TAG, "awarenessBarrier = null!", promise);
                return;
            }

            String barrierLabel = barrierLabelControl(map, promise);
            if (barrierLabel == null) {
                return;
            }

            BarrierUpdateRequest.Builder builder = new BarrierUpdateRequest.Builder();
            BarrierUpdateRequest request = builder.addBarrier(barrierLabel, awarenessBarrier, pendingIntent).build();

            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getBarrierClient(context).updateBarriers(request)
                    .addOnSuccessListener(aVoid -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        Log.i(TAG,"addBarrier");
                        valueConvertToMap("Response",
                                "Success", barrierEventType + "::" + barrierReceiverAction, promise);
                    })
                    .addOnFailureListener(e -> errorMessage(context, method +
                            barrierEventType + "::" + barrierReceiverAction, TAG, e.toString(), promise));
        } catch (IllegalArgumentException e) {
            Log.i(TAG,"Err:addBarrier");
            errorMessage(context, method, TAG, e, promise);
        }
    }

    private String barrierReceiverActionControl(ReadableMap map, Promise promise) {
        String paramErr = "wrong parameter::barrierReceiverAction";
        String err = "wrong parameter::barrierReceiverAction is null";

        if (map == null || !map.hasKey("barrierReceiverAction")
                || map.getString("barrierReceiverAction") == null) {
            Log.i(TAG,"Err:barrierReceiverAction");
            errorMessage(null, "barrier", TAG, paramErr, promise);
            return null;
        }
        String barrierReceiverAction = map.getString("barrierReceiverAction");
        if (barrierReceiverAction == null) {
            Log.i(TAG,"Err:barrierReceiverAction");
            errorMessage(null, "barrier", TAG, err, promise);
            return null;
        }
        return barrierReceiverAction;
    }

    private String barrierLabelControl(ReadableMap map, Promise promise) {
        String typeErr = "wrong parameter::barrierLabel";

        if (map == null || !map.hasKey("barrierLabel")
                || map.getString("barrierLabel") == null) {
            errorMessage(null, "barrier", TAG, typeErr, promise);
            return null;
        }

        String barrierLabel = map.getString("barrierLabel");
        if (barrierLabel == null) {
            errorMessage(null, "barrier", TAG, typeErr, promise);
            return null;
        }
        return barrierLabel;
    }

    private void headsetBarrier(ReadableMap map, Promise promise) {
        try {
            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return;
            }
            if (barrierReceiverAction.equals(getAllConstants().get("EVENT_HEADSET_KEEPING"))) {
                if (!map.hasKey("headsetStatus")) {
                    errorMessage(null, "headsetBarrier", TAG, wrongParams + "::headsetStatus", promise);
                    return;
                }
                int headsetStatus = map.getInt("headsetStatus");
                awarenessBarrier = HeadsetBarrier.keeping(headsetStatus);
            } else if (barrierReceiverAction.equals(getAllConstants().get("EVENT_HEADSET_CONNECTING"))) {
                awarenessBarrier = HeadsetBarrier.connecting();
            } else if (barrierReceiverAction.equals(getAllConstants().get("EVENT_HEADSET_DISCONNECTING"))) {
                awarenessBarrier = HeadsetBarrier.disconnecting();
            } else {
                errorMessage(null, "headsetBarrier", TAG, wrongParams, promise);
                return;
            }
            addBarrier(barrierReceiverAction, map, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(null, "headsetBarrier", TAG, e, promise);
        }
    }

    private void ambientLightBarrier(ReadableMap map, Promise promise) {
        try {
            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return;
            }
            if (barrierReceiverAction.equals(getAllConstants().get("AMBIENTLIGHT_ABOVE"))) {
                ambientLightBarrierAbove(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("AMBIENTLIGHT_BELOW"))) {
                ambientLightBarrierBelow(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("AMBIENTLIGHT_RANGE"))) {
                ambientLightBarrierRange(map, promise);
            } else {
                errorMessage(null, "ambientLightBarrier", TAG, wrongParams, promise);
                return;
            }
            addBarrier(barrierReceiverAction, map, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(null, "ambientLightBarrier", TAG, e, promise);
        }
    }

    private void ambientLightBarrierAbove(ReadableMap map, Promise promise) {
        if (!map.hasKey("minLightIntensity")) {
            errorMessage(null, "ambientLightBarrier", TAG, wrongParams + "::minLightIntensity", promise);
            return;
        }
        final float minLightIntensity = (float) map.getDouble("minLightIntensity");
        awarenessBarrier = AmbientLightBarrier.above(minLightIntensity);
    }

    private void ambientLightBarrierBelow(ReadableMap map, Promise promise) {
        if (!map.hasKey("maxLightIntensity")) {
            errorMessage(null, "ambientLightBarrier",
                    TAG, wrongParams + "::maxLightIntensity", promise);
            return;
        }
        final float maxLightIntensity = (float) map.getDouble("maxLightIntensity");
        awarenessBarrier = AmbientLightBarrier.below(maxLightIntensity);
    }

    private void ambientLightBarrierRange(ReadableMap map, Promise promise) {
        if (!map.hasKey("minLightIntensity")
                || !map.hasKey("maxLightIntensity")) {
            errorMessage(null, "ambientLightBarrier", TAG, wrongParams + "::maxLightIntensity or minLightIntensity", promise);
            return;
        }
        final float minLightIntensity = (float) map.getDouble("minLightIntensity");
        final float maxLightIntensity = (float) map.getDouble("maxLightIntensity");
        awarenessBarrier = AmbientLightBarrier.range(minLightIntensity, maxLightIntensity);
    }

    private void wifiBarrier(ReadableMap map, Promise promise) {
        try {
            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return;
            }
            if (barrierReceiverAction.equals(getAllConstants().get("WIFI_KEEPING"))) {
                wifiKeeping(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("WIFI_CONNECTING"))) {
                wifiConnecting(map);
            } else if (barrierReceiverAction.equals(getAllConstants().get("WIFI_DISCONNECTING"))) {
                wifiDisconnecting(map);
            } else if (barrierReceiverAction.equals(getAllConstants().get("WIFI_ENABLING"))) {
                wifiEnabling();
            } else if (barrierReceiverAction.equals(getAllConstants().get("WIFI_DISABLING"))) {
                wifiDisabling();
            } else {
                errorMessage(null, "wifiBarrier", TAG, wrongParams, promise);
                return;
            }
            addBarrier(barrierReceiverAction, map, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(null, "wifiBarrier", TAG, e, promise);
        }
    }

    private void wifiKeeping(ReadableMap map, Promise promise) {
        if (!map.hasKey("wifiStatus")) {
            errorMessage(null, "wifiBarrier", TAG, wrongParams + "::wifiStatus", promise);
            return;
        }
        int wifiStatus = map.getInt("wifiStatus");
        if (map.hasKey("bssid") && map.hasKey("ssid")) {
            String bssid = map.getString("bssid");
            String ssid = map.getString("ssid");
            awarenessBarrier = WifiBarrier.keeping(wifiStatus, bssid, ssid);
        } else {
            awarenessBarrier = WifiBarrier.keeping(wifiStatus);
        }
    }

    private void wifiConnecting(ReadableMap map) {
        if (map.hasKey("bssid") && map.hasKey("ssid")) {
            String bssid = map.getString("bssid");
            String ssid = map.getString("ssid");
            awarenessBarrier = WifiBarrier.connecting(bssid, ssid);
        } else {
            awarenessBarrier = WifiBarrier.connecting();
        }
    }

    private void wifiDisconnecting(ReadableMap map) {
        if (map.hasKey("bssid") && map.hasKey("ssid")) {
            String bssid = map.getString("bssid");
            String ssid = map.getString("ssid");
            awarenessBarrier = WifiBarrier.disconnecting(bssid, ssid);
        } else {
            awarenessBarrier = WifiBarrier.disconnecting();
        }
    }

    private void wifiEnabling() {
        awarenessBarrier = WifiBarrier.enabling();
    }

    private void wifiDisabling() {
        awarenessBarrier = WifiBarrier.disabling();
    }

    private void screenBarrier(ReadableMap map, Promise promise) {
        try {
            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return;
            }
            if (barrierReceiverAction.equals(getAllConstants().get("SCREEN_KEEPING"))) {
                if (!map.hasKey("screenStatus")) {
                    errorMessage(null, "screenBarrier", TAG, wrongParams + "::screenStatus", promise);
                    return;
                }
                int screenStatus = map.getInt("screenStatus");
                awarenessBarrier = ScreenBarrier.keeping(screenStatus);
            } else if (barrierReceiverAction.equals(getAllConstants().get("SCREEN_ON"))) {
                awarenessBarrier = ScreenBarrier.screenOn();
            } else if (barrierReceiverAction.equals(getAllConstants().get("SCREEN_OFF"))) {
                awarenessBarrier = ScreenBarrier.screenOff();
            } else if (barrierReceiverAction.equals(getAllConstants().get("SCREEN_UNLOCK"))) {
                awarenessBarrier = ScreenBarrier.screenUnlock();
            } else {
                errorMessage(null, "screenBarrier", TAG, wrongParams, promise);
                return;
            }
            addBarrier(barrierReceiverAction, map, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(null, "screenBarrier", TAG, e, promise);
        }
    }

    private void bluetoothBarrier(ReadableMap map, Promise promise) {
        try {
            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return;
            }
            if (barrierReceiverAction.equals(getAllConstants().get("BLUETOOTH_KEEP"))) {
                bluetoothKeeping(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("BLUETOOTH_CONNECTING"))) {
                bluetoothConnecting(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("BLUETOOTH_DISCONNECTING"))) {
                bluetoothDisconnecting(map, promise);
            } else {
                errorMessage(null, "bluetoothBarrier", TAG, wrongParams, promise);
                return;
            }
            addBarrier(barrierReceiverAction, map, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(null, "bluetoothBarrier", TAG, e, promise);
        }
    }

    private void bluetoothKeeping(ReadableMap map, Promise promise) {
        if (map.hasKey("deviceType") && map.hasKey("bluetoothStatus")) {
            int deviceType = map.getInt("deviceType");
            int bluetoothStatus = map.getInt("bluetoothStatus");
            awarenessBarrier = BluetoothBarrier.keep(deviceType, bluetoothStatus);
        } else {
            errorMessage(null, "bluetoothBarrier", TAG,
                    wrongParams + "::deviceType and bluetoothStatus", promise);
        }
    }

    private void bluetoothConnecting(ReadableMap map, Promise promise) {
        if (!map.hasKey("deviceType")) {
            errorMessage(null, "bluetoothBarrier", TAG,
                    wrongParams + "::deviceType", promise);
            return;
        }
        int deviceType = map.getInt("deviceType");
        awarenessBarrier = BluetoothBarrier.connecting(deviceType);
    }

    private void bluetoothDisconnecting(ReadableMap map, Promise promise) {
        if (!map.hasKey("deviceType")) {
            errorMessage(null, "bluetoothBarrier", TAG,
                    wrongParams + "::deviceType", promise);
            return;
        }
        int deviceType = map.getInt("deviceType");
        awarenessBarrier = BluetoothBarrier.disconnecting(deviceType);
    }

    private void behaviorBarrier(ReadableMap map, Promise promise) {
        try {
            if (!PermissionUtils.hasActivityRecognitionPermission(getCurrentActivity())) {
                PermissionUtils.requestActivityRecognitionPermission(getCurrentActivity(), promise);
                return;
            }

            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return;
            }
            if (!map.hasKey("behaviorTypes")
                    || Objects.requireNonNull(map.getArray("behaviorTypes")).size() == 0) {
                errorMessage(null, "behaviorBarrier", TAG,
                        wrongParams + "::behaviorTypes", promise);
                return;
            }

            ReadableArray readableTypes = map.getArray("behaviorTypes");
            readableArrayConvertToIntArray(readableTypes);
            int[] behaviorTypes = readableArrayConvertToIntArray(readableTypes);
            if (barrierReceiverAction.equals(getAllConstants().get("BEHAVIOR_KEEPING"))) {
                awarenessBarrier = BehaviorBarrier.keeping(behaviorTypes);
            } else if (barrierReceiverAction.equals(getAllConstants().get("BEHAVIOR_BEGINNING"))) {
                awarenessBarrier = BehaviorBarrier.beginning(behaviorTypes);
            } else if (barrierReceiverAction.equals(getAllConstants().get("BEHAVIOR_ENDING"))) {
                awarenessBarrier = BehaviorBarrier.ending(behaviorTypes);
            } else {
                errorMessage(null, "behaviorBarrier", TAG, wrongParams, promise);
                return;
            }
            addBarrier(barrierReceiverAction, map, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(null, "behaviorBarrier", TAG, e, promise);
        }
    }

    private void locationBarrier(ReadableMap map, Promise promise) {
        try {
            if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
                PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
                return;
            }

            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return;
            }
            if (barrierReceiverAction.equals(getAllConstants().get("LOCATION_ENTER"))) {
                locationEnter(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("LOCATION_STAY"))) {
                locationStay(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("LOCATION_EXIT"))) {
                locationExit(map, promise);
            } else {
                errorMessage(null, "locationBarrier", TAG, wrongParams, promise);
                return;
            }

            addBarrier(barrierReceiverAction, map, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(null, "locationBarrier", TAG, e, promise);
        }
    }

    @SuppressLint("MissingPermission")
    private void locationEnter(ReadableMap map, Promise promise) {

        if (map.hasKey("latitude")
                && map.hasKey("longitude")
                && map.hasKey("radius")) {
            double latitude = map.getDouble("latitude");
            double longitude = map.getDouble("longitude");
            double radius = map.getDouble("radius");
            awarenessBarrier = LocationBarrier.enter(latitude, longitude, radius);
        } else {
            errorMessage(null, "locationBarrier", TAG,
                    wrongParams + "::latitude,longitude,radius", promise);
        }
    }

    @SuppressLint("MissingPermission")
    private void locationExit(ReadableMap map, Promise promise) {
        if (map.hasKey("latitude")
                && map.hasKey("longitude")
                && map.hasKey("radius")) {
            double latitude = map.getDouble("latitude");
            double longitude = map.getDouble("longitude");
            double radius = map.getDouble("radius");
            awarenessBarrier = LocationBarrier.exit(latitude, longitude, radius);
        } else {
            errorMessage(null, "locationBarrier", TAG,
                    wrongParams + "::latitude,longitude,radius", promise);
        }
    }

    @SuppressLint("MissingPermission")
    private void locationStay(ReadableMap map, Promise promise) {
        if (map.hasKey("latitude")
                && map.hasKey("longitude")
                && map.hasKey("radius")
                && map.hasKey("timeOfDuration")) {
            double latitude = map.getDouble("latitude");
            double longitude = map.getDouble("longitude");
            double radius = map.getDouble("radius");
            long timeOfDuration = (long) map.getDouble("timeOfDuration");
            awarenessBarrier = LocationBarrier.stay(latitude, longitude, radius, timeOfDuration);
        } else {
            errorMessage(null, "locationBarrier", TAG,
                    wrongParams + "::latitude,longitude,radius,timeOfDuration", promise);
        }
    }

    private void timeBarrier(ReadableMap map, Promise promise) {
        try {
            if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
                PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
                return;
            }

            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return;
            }
            if (barrierReceiverAction.equals(getAllConstants().get("TIME_IN_SUNRISE_OR_SUNSET_PERIOD"))) {
                inSunriseOrSunsetPeriod(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("TIME_DURING_PERIOD_OF_DAY"))) {
                duringPeriodOfDay(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("TIME_DURING_TIME_PERIOD"))) {
                duringTimePeriod(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("TIME_DURING_PERIOD_OF_WEEK"))) {
                duringPeriodOfWeek(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("TIME_IN_TIME_CATEGORY"))) {
                inTimeCategory(map, promise);
            } else {
                errorMessage(null, "timeBarrier", TAG, wrongParams, promise);
                return;
            }
            addBarrier(barrierReceiverAction, map, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(null, "timeBarrier", TAG, e, promise);
        }
    }

    @SuppressLint("MissingPermission")
    private void inSunriseOrSunsetPeriod(ReadableMap map, Promise promise) {

        if (map.hasKey("startTimeOffset") &&
                map.hasKey("stopTimeOffset") &&
                map.hasKey("timeInstant")) {
            long startTimeOffset = (long) map.getDouble("startTimeOffset");
            long stopTimeOffset = (long) map.getDouble("stopTimeOffset");
            int timeInstant = map.getInt("timeInstant");
            awarenessBarrier = TimeBarrier.inSunriseOrSunsetPeriod(timeInstant, startTimeOffset, stopTimeOffset);
        } else {
            errorMessage(null, "timeBarrier", TAG,
                    wrongParams + "::startTimeOffset,stopTimeOffset,timeInstant", promise);
        }
    }

    @SuppressLint("MissingPermission")
    private void duringPeriodOfDay(ReadableMap map, Promise promise) {
        if (map.hasKey("startTimeOfDay")
                && map.hasKey("stopTimeOfDay")) {
            long startTimeOfDay = (long) map.getDouble("startTimeOfDay");
            long stopTimeOfDay = (long) map.getDouble("stopTimeOfDay");
            awarenessBarrier = TimeBarrier.duringPeriodOfDay(getTimeZone(map), startTimeOfDay, stopTimeOfDay);
        } else {
            errorMessage(null, "timeBarrier", TAG,
                    wrongParams + "::startTimeOfDay,stopTimeOfDay,timeZoneId", promise);
        }
    }

    private void duringTimePeriod(ReadableMap map, Promise promise) {
        if (map.hasKey("startTimeStamp")
                && map.hasKey("stopTimeStamp")) {

            long startTimeStamp = (long) map.getDouble("startTimeStamp");
            long stopTimeStamp = (long) map.getDouble("stopTimeStamp");
            awarenessBarrier = TimeBarrier.duringTimePeriod(startTimeStamp, stopTimeStamp);
        } else {
            errorMessage(null, "timeBarrier", TAG,
                    wrongParams + "::startTimeStamp,stopTimeStamp", promise);
        }
    }

    @SuppressLint("MissingPermission")
    private void duringPeriodOfWeek(ReadableMap map, Promise promise) {
        if (map.hasKey("dayOfWeek")
                && map.hasKey("startTimeOfSpecifiedDay")
                && map.hasKey("stopTimeOfSpecifiedDay")) {

            int dayOfWeek = map.getInt("dayOfWeek");
            long startTimeOfSpecifiedDay = (long) map.getDouble("startTimeOfSpecifiedDay");
            long stopTimeOfSpecifiedDay = (long) map.getDouble("stopTimeOfSpecifiedDay");
            awarenessBarrier = TimeBarrier.duringPeriodOfWeek(
                    dayOfWeek, TimeZone.getDefault(), startTimeOfSpecifiedDay, stopTimeOfSpecifiedDay);
        } else {
            errorMessage(null, "timeBarrier", TAG,
                    wrongParams + "::dayOfWeek,startTimeOfSpecifiedDay,stopTimeOfSpecifiedDay", promise);
        }
    }

    @SuppressLint("MissingPermission")
    private void inTimeCategory(ReadableMap map, Promise promise) {
        if (map.hasKey("timeCategory")) {
            int timeCategory = map.getInt("timeCategory");
            awarenessBarrier = TimeBarrier.inTimeCategory(timeCategory);
        } else {
            errorMessage(null, "timeBarrier", TAG,
                    wrongParams + "::timeCategory", promise);
        }
    }

    private TimeZone getTimeZone(ReadableMap map) {
        TimeZone timeZone;
        if (map.hasKey("timeZoneId") && map.getString("timeZoneId") != null) {
            String timeZoneId = map.getString("timeZoneId");
            timeZone = TimeZone.getTimeZone(timeZoneId);
        } else {
            timeZone = TimeZone.getDefault();
        }
        return timeZone;
    }

    private void beaconBarrier(ReadableMap map, Promise promise) {
        try {
            if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
                PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
                return;
            }

            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return;
            }
            createBeaconBarrier(barrierReceiverAction, map, promise);
            addBarrier(barrierReceiverAction, map, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(null, "beaconBarrier", TAG, e, promise);
        }
    }

    @SuppressLint("MissingPermission")
    private void createBeaconBarrier(String barrierReceiverAction, ReadableMap map, Promise promise) {
        String method = "beaconBarrier";
        if (!map.hasKey("beaconArray")
                || map.getArray("beaconArray") == null) {
            errorMessage(null, method, TAG, wrongParams, promise);
            return;
        }
        ReadableArray array = map.getArray("beaconArray");
        if (array == null) {
            errorMessage(null, method, TAG, wrongParams, promise);
            return;
        }
        int size = array.size();
        BeaconStatus.Filter[] filters = new BeaconStatus.Filter[size];
        for (int i = 0; i < size; i++) {
            ReadableMap item = array.getMap(i);
            assert item != null;
            BeaconStatus.Filter filter = null;
            if(item.hasKey("beaconId")){
                String beaconId=item.getString("beaconId");
                assert beaconId != null;
                filter = match(beaconId);
            }else if(item.hasKey("namespace") && item.hasKey("type") && item.hasKey("content")){
                String namespace = item.getString("namespace");
                String type = item.getString("type");
                String contentString = item.getString("content");
                if (contentString == null) {
                    errorMessage(null, method, TAG, wrongParams, promise);
                    return;
                }
                byte[] content = contentString.getBytes(StandardCharsets.UTF_8);
                assert namespace != null;
                assert type != null;
                filter = match(namespace, type, content);
            }else if(item.hasKey("namespace")&& item.hasKey("content")){
                String namespace = item.getString("namespace");
                String type = item.getString("type");
                assert namespace != null;
                assert type != null;
                match(namespace, type);
            }else{
                errorMessage(null, method, TAG, wrongParams, promise);
                return;
            }
            filters[i] = filter;
        }

        if (barrierReceiverAction.equals(getAllConstants().get("BEACON_DISCOVER"))) {
            awarenessBarrier = BeaconBarrier.discover(filters);
        } else if (barrierReceiverAction.equals(getAllConstants().get("BEACON_KEEP"))) {
            awarenessBarrier = BeaconBarrier.keep(filters);
        } else if (barrierReceiverAction.equals(getAllConstants().get("BEACON_MISSED"))) {
            awarenessBarrier = BeaconBarrier.missed(filters);
        } else {
            errorMessage(null, "beaconBarrier", TAG, wrongParams, promise);
        }
    }

    private void enableUpdateWindow(ReadableMap map, Promise promise) {
        String method = "enableUpdateWindowWithBarrierClient";
        try {
            if (map == null || !map.hasKey("isEnable")) {
                errorMessage(null, "enableUpdateWindow", TAG, wrongParams, promise);
                return;
            }
            boolean isEnable = map.getBoolean("isEnable");
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getBarrierClient(context).enableUpdateWindow(isEnable);
            HMSLogger.getInstance(context).sendSingleEvent(method);
            valueConvertToMap("isEnabled", isEnable, method, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(null, method, TAG, e, promise);
        }
    }

    private Activity getCurrentActivity() {
        return context.getCurrentActivity();
    }
}