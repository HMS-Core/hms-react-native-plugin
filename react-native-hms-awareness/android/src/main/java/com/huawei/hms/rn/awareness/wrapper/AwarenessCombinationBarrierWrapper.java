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
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.StandardCharsets;
import com.huawei.hms.kit.awareness.Awareness;
import com.huawei.hms.kit.awareness.barrier.WifiBarrier;
import com.huawei.hms.kit.awareness.barrier.AmbientLightBarrier;
import com.huawei.hms.kit.awareness.barrier.BehaviorBarrier;
import com.huawei.hms.kit.awareness.barrier.BluetoothBarrier;
import com.huawei.hms.kit.awareness.barrier.AwarenessBarrier;
import com.huawei.hms.kit.awareness.barrier.BarrierUpdateRequest;
import com.huawei.hms.kit.awareness.barrier.BeaconBarrier;
import com.huawei.hms.kit.awareness.barrier.HeadsetBarrier;
import com.huawei.hms.kit.awareness.barrier.LocationBarrier;
import com.huawei.hms.kit.awareness.barrier.ScreenBarrier;
import com.huawei.hms.kit.awareness.status.BeaconStatus;
import com.huawei.hms.rn.awareness.constants.Constants;
import com.huawei.hms.kit.awareness.barrier.TimeBarrier;
import com.huawei.hms.rn.awareness.logger.HMSLogger;
import com.huawei.hms.rn.awareness.utils.PermissionUtils;

import java.util.Objects;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TimeZone;

import static com.huawei.hms.rn.awareness.utils.DataUtils.valueConvertToMap;
import static com.huawei.hms.rn.awareness.constants.Constants.getAllConstants;
import static com.huawei.hms.rn.awareness.utils.DataUtils.errorMessage;
import static com.huawei.hms.rn.awareness.utils.DataUtils.readableArrayConvertToIntArray;

public class AwarenessCombinationBarrierWrapper {

    private ReactContext context;
    private PendingIntent pendingIntent;

    private String TAG = "AwarenessCombinationBarrier::";
    private String barrierLabel;
    private String WRONG_PARAMS = "Wrong parameter! Please check your parameters.";

    public AwarenessCombinationBarrierWrapper(@NonNull ReactContext reactContext, @NonNull PendingIntent intent) {
        context = reactContext;
        pendingIntent = intent;
    }

    public void addCombinationBarrier(String bLabel, ReadableArray array, Promise promise) {
        String method = "addCombinationBarrier";
        barrierLabel = bLabel;
        if (array.size() == 0) {
            errorMessage(null, method, TAG, WRONG_PARAMS, promise);
            return;
        }
        try {
            ReadableMap map = array.getMap(0);
            if (map == null) {
                errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                return;
            }
            String type = map.getString("type");
            ReadableArray children = map.getArray("children");
            if (children == null || type == null) {
                errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                return;
            }
            AwarenessBarrier awarenessBarrier = addCombinationBarrierUtil(children, new ArrayList<>(), type, promise);
            if (awarenessBarrier == null) {
                errorMessage(null, method, TAG, WRONG_PARAMS, promise);
                return;
            }
            buildBroadcastReceiver(awarenessBarrier, promise);
        } catch (IllegalArgumentException e) {
            errorMessage(null, method, TAG, e, promise);
        }
    }

    private AwarenessBarrier addCombinationBarrierUtil(ReadableArray array, Collection<AwarenessBarrier> coll, String type, Promise promise) {
        for (int i = 0; i < array.size(); i++) {
            ReadableMap map = array.getMap(i);
            if (map != null && map.hasKey("type")
                    && map.getString("type") != null) {
                if (Objects.equals(map.getString("type"), "not")) {
                    ReadableMap child = map.getMap("child");
                    if (child == null){
                        return null;
                    }
                    String barrierEventType = child.getString("barrierEventType");
                    AwarenessBarrier awar = getAwarenessBarrier(barrierEventType, child, promise);
                    if (awar != null) {
                        coll.add(AwarenessBarrier.not(awar));
                    }
                } else {
                    ReadableArray children = map.getArray("children");
                    String innerType = map.getString("type");
                    if (children == null || innerType == null) {
                        return null;
                    }
                    AwarenessBarrier innerCombinationBarrier =
                            addCombinationBarrierUtil(children, new ArrayList<>(), innerType, promise);
                    coll.add(innerCombinationBarrier);
                }
            } else {
                assert map != null;
                String barrierEventType = map.getString("barrierEventType");
                AwarenessBarrier awareness = getAwarenessBarrier(barrierEventType, map, promise);
                coll.add(awareness);
            }
        }
        if (type.equals("and")) {
            return AwarenessBarrier.and(coll);
        } else {
            return AwarenessBarrier.or(coll);
        }
    }

    private AwarenessBarrier getAwarenessBarrier(String barrierEventType, ReadableMap map, Promise promise) {
        AwarenessBarrier awarenessBarrier = null;

        if (map == null || barrierEventType == null) {
            errorMessage(null, "updateBarrier", TAG, "wrong parameter", promise);
            return null;
        }

        if (barrierEventType.equals(Constants.getAllConstants().get("EVENT_HEADSET"))) {
            awarenessBarrier = headsetBarrier(map, promise);
        } else if (barrierEventType.equals(Constants.getAllConstants().get("EVENT_AMBIENTLIGHT"))) {
            awarenessBarrier = ambientLightBarrier(map, promise);
        } else if (barrierEventType.equals(Constants.getAllConstants().get("EVENT_WIFI"))) {
            awarenessBarrier = wifiBarrier(map, promise);
        } else if (barrierEventType.equals(Constants.getAllConstants().get("EVENT_BLUETOOTH"))) {
            awarenessBarrier = bluetoothBarrier(map, promise);
        } else if (barrierEventType.equals(Constants.getAllConstants().get("EVENT_BEHAVIOR"))) {
            awarenessBarrier = behaviorBarrier(map, promise);
        } else if (barrierEventType.equals(Constants.getAllConstants().get("EVENT_LOCATION"))) {
            awarenessBarrier = locationBarrier(map, promise);
        } else if (barrierEventType.equals(Constants.getAllConstants().get("EVENT_SCREEN"))) {
            awarenessBarrier = screenBarrier(map, promise);
        } else if (barrierEventType.equals(Constants.getAllConstants().get("EVENT_TIME"))) {
            awarenessBarrier = timeBarrier(map, promise);
        } else if (barrierEventType.equals(Constants.getAllConstants().get("EVENT_BEACON"))) {
            awarenessBarrier = beaconBarrier(map, promise);
        }
        return awarenessBarrier;
    }

    private void buildBroadcastReceiver(@NonNull AwarenessBarrier awarenessBarrier, @NonNull Promise promise) {
        String method = "buildBroadcastReceiver";
        BarrierUpdateRequest.Builder builder = new BarrierUpdateRequest.Builder();
        BarrierUpdateRequest request = builder.addBarrier(barrierLabel, awarenessBarrier, pendingIntent).build();
        try {
            HMSLogger.getInstance(context).startMethodExecutionTimer(method);
            Awareness.getBarrierClient(context).updateBarriers(request)
                    .addOnSuccessListener(aVoid -> {
                        HMSLogger.getInstance(context).sendSingleEvent(method);
                        Log.i(TAG, "buildBroadcastReceiver");
                        valueConvertToMap("Response:",
                                "Success", "combination barrier", promise);
                    })
                    .addOnFailureListener(e -> {
                        Log.i(TAG, "Err:buildBroadcastReceiver");
                        errorMessage(context, method, TAG, e, promise);
                    });
        } catch (IllegalArgumentException e) {
            Log.i(TAG, "Err:buildBroadcastReceiver");
            errorMessage(context, method, TAG, e, promise);
        }
    }

    private String barrierReceiverActionControl(ReadableMap map, Promise promise) {
        String typeErr = "wrong parameter::barrierReceiverAction";
        String nullErr = "wrong parameter::barrierReceiverAction is null";

        if (map == null || !map.hasKey("barrierReceiverAction")
                || map.getString("barrierReceiverAction") == null) {
            Log.i(TAG,"Err:barrierReceiverAction-combination");
            errorMessage(null, "barrier", TAG, typeErr, promise);
            return null;
        }
        String barrierReceiverAction = map.getString("barrierReceiverAction");
        if (barrierReceiverAction == null) {
            Log.i(TAG,"Err:barrierReceiverAction-combination");
            errorMessage(null, "barrier", TAG, nullErr, promise);
            return null;
        }
        return barrierReceiverAction;
    }

    private AwarenessBarrier headsetBarrier(ReadableMap map, Promise promise) {
        AwarenessBarrier awarenessBarrier;
        try {
            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return null;
            }
            if (barrierReceiverAction.equals(Constants.getAllConstants().get("EVENT_HEADSET_KEEPING"))) {
                if (!map.hasKey("headsetStatus")) {
                    errorMessage(null, "headsetBarrier", TAG, WRONG_PARAMS + "::headsetStatus", promise);
                    return null;
                }
                int headsetStatus = map.getInt("headsetStatus");
                awarenessBarrier = HeadsetBarrier.keeping(headsetStatus);
                Log.i(TAG, "CombinationBarrier-HeadsetBarrier.keeping");
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("EVENT_HEADSET_CONNECTING"))) {
                awarenessBarrier = HeadsetBarrier.connecting();
                Log.i(TAG, "CombinationBarrier-HeadsetBarrier.connecting");
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("EVENT_HEADSET_DISCONNECTING"))) {
                awarenessBarrier = HeadsetBarrier.disconnecting();
                Log.i(TAG, "CombinationBarrier-HeadsetBarrier.disconnecting");
            } else {
                errorMessage(null, "headsetBarrier", TAG, WRONG_PARAMS, promise);
                Log.i(TAG, "Err:HeadsetBarrier");
                return null;
            }
            return awarenessBarrier;
        } catch (IllegalArgumentException e) {
            errorMessage(null, "headsetBarrier", TAG, e, promise);
            return null;
        }
    }

    private AwarenessBarrier ambientLightBarrier(ReadableMap map, Promise promise) {
        AwarenessBarrier awarenessBarrier = null;
        try {
            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return null;
            }
            if (barrierReceiverAction.equals(Constants.getAllConstants().get("AMBIENTLIGHT_ABOVE"))) {
                awarenessBarrier = ambientLightBarrierAbove(map, promise);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("AMBIENTLIGHT_BELOW"))) {
                awarenessBarrier = ambientLightBarrierBelow(map, promise);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("AMBIENTLIGHT_RANGE"))) {
                awarenessBarrier = ambientLightBarrierRange(map, promise);
            } else {
                errorMessage(null, "ambientLightBarrier", TAG, WRONG_PARAMS, promise);
                return null;
            }
            return awarenessBarrier;
        } catch (IllegalArgumentException e) {
            errorMessage(null, "ambientLightBarrier", TAG, e, promise);
        }
        return awarenessBarrier;
    }

    private AwarenessBarrier ambientLightBarrierAbove(ReadableMap map, Promise promise) {
        if (!map.hasKey("minLightIntensity")) {
            errorMessage(null, "ambientLightBarrier", TAG, WRONG_PARAMS + "::minLightIntensity", promise);
            return null;
        }
        final float minLightIntensity = (float) map.getDouble("minLightIntensity");
        return AmbientLightBarrier.above(minLightIntensity);
    }

    private AwarenessBarrier ambientLightBarrierBelow(ReadableMap map, Promise promise) {
        if (!map.hasKey("maxLightIntensity")) {
            errorMessage(null, "ambientLightBarrier",
                    TAG, WRONG_PARAMS + "::maxLightIntensity", promise);
            return null;
        }
        final float maxLightIntensity = (float) map.getDouble("maxLightIntensity");
        return AmbientLightBarrier.below(maxLightIntensity);
    }

    private AwarenessBarrier ambientLightBarrierRange(ReadableMap map, Promise promise) {
        if (!map.hasKey("minLightIntensity")
                || !map.hasKey("maxLightIntensity")) {
            errorMessage(null, "ambientLightBarrier", TAG, WRONG_PARAMS +
                    "::maxLightIntensity or minLightIntensity", promise);
            return null;
        }
        final float minLightIntensity = (float) map.getDouble("minLightIntensity");
        final float maxLightIntensity = (float) map.getDouble("maxLightIntensity");
        return AmbientLightBarrier.range(minLightIntensity, maxLightIntensity);
    }

    private AwarenessBarrier wifiBarrier(ReadableMap map, Promise promise) {
        AwarenessBarrier awarenessBarrier;
        try {
            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return null;
            }
            if (barrierReceiverAction.equals(Constants.getAllConstants().get("WIFI_KEEPING"))) {
                awarenessBarrier = wifiKeeping(map, promise);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("WIFI_CONNECTING"))) {
                awarenessBarrier = wifiConnecting(map);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("WIFI_DISCONNECTING"))) {
                awarenessBarrier = wifiDisconnecting(map);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("WIFI_ENABLING"))) {
                awarenessBarrier = wifiEnabling();
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("WIFI_DISABLING"))) {
                awarenessBarrier = wifiDisabling();
            } else {
                errorMessage(null, "wifiBarrier", TAG, WRONG_PARAMS, promise);
                return null;
            }
        } catch (IllegalArgumentException e) {
            errorMessage(null, "wifiBarrier", TAG, e, promise);
            return null;
        }
        return awarenessBarrier;
    }

    private AwarenessBarrier wifiKeeping(ReadableMap map, Promise promise) {
        if (!map.hasKey("wifiStatus")) {
            errorMessage(null, "wifiBarrier", TAG, WRONG_PARAMS + "::wifiStatus", promise);
            return null;
        }
        AwarenessBarrier awarenessBarrier;
        int wifiStatus = map.getInt("wifiStatus");
        if (map.hasKey("bssid") && map.hasKey("ssid")) {
            String bssid = map.getString("bssid");
            String ssid = map.getString("ssid");
            awarenessBarrier = WifiBarrier.keeping(wifiStatus, bssid, ssid);
        } else {
            awarenessBarrier = WifiBarrier.keeping(wifiStatus);
        }
        return awarenessBarrier;
    }

    private AwarenessBarrier wifiConnecting(ReadableMap map) {
        AwarenessBarrier awarenessBarrier;
        if (map.hasKey("bssid") && map.hasKey("ssid")) {
            String bssid = map.getString("bssid");
            String ssid = map.getString("ssid");
            awarenessBarrier = WifiBarrier.connecting(bssid, ssid);
        } else {
            awarenessBarrier = WifiBarrier.connecting();
        }
        return awarenessBarrier;
    }

    private AwarenessBarrier wifiDisconnecting(ReadableMap map) {
        if (map.hasKey("bssid") && map.hasKey("ssid")) {
            String bssid = map.getString("bssid");
            String ssid = map.getString("ssid");
            return WifiBarrier.disconnecting(bssid, ssid);
        } else {
            return WifiBarrier.disconnecting();
        }
    }

    private AwarenessBarrier wifiEnabling() {
        return WifiBarrier.enabling();
    }

    private AwarenessBarrier wifiDisabling() {
        return WifiBarrier.disabling();
    }

    private AwarenessBarrier screenBarrier(ReadableMap map, Promise promise) {
        AwarenessBarrier awarenessBarrier;
        try {
            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return null;
            }
            if (barrierReceiverAction.equals(Constants.getAllConstants().get("SCREEN_KEEPING"))) {
                if (!map.hasKey("screenStatus")) {
                    errorMessage(null, "screenBarrier", TAG, WRONG_PARAMS + "::screenStatus", promise);
                    return null;
                }
                int screenStatus = map.getInt("screenStatus");
                awarenessBarrier = ScreenBarrier.keeping(screenStatus);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("SCREEN_ON"))) {
                awarenessBarrier = ScreenBarrier.screenOn();
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("SCREEN_OFF"))) {
                awarenessBarrier = ScreenBarrier.screenOff();
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("SCREEN_UNLOCK"))) {
                awarenessBarrier = ScreenBarrier.screenUnlock();
            } else {
                errorMessage(null, "screenBarrier", TAG, WRONG_PARAMS, promise);
                return null;
            }
        } catch (IllegalArgumentException e) {
            errorMessage(null, "screenBarrier", TAG, e, promise);
            return null;
        }
        return awarenessBarrier;
    }

    private AwarenessBarrier bluetoothBarrier(ReadableMap map, Promise promise) {
        AwarenessBarrier awarenessBarrier;
        try {
            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return null;
            }
            if (barrierReceiverAction.equals(Constants.getAllConstants().get("BLUETOOTH_KEEP"))) {
                awarenessBarrier = bluetoothKeeping(map, promise);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("BLUETOOTH_CONNECTING"))) {
                awarenessBarrier = bluetoothConnecting(map, promise);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("BLUETOOTH_DISCONNECTING"))) {
                awarenessBarrier = bluetoothDisconnecting(map, promise);
            } else {
                errorMessage(null, "bluetoothBarrier", TAG, WRONG_PARAMS, promise);
                return null;
            }
        } catch (IllegalArgumentException e) {
            errorMessage(null, "bluetoothBarrier", TAG, e, promise);
            return null;
        }
        return awarenessBarrier;
    }

    private AwarenessBarrier bluetoothKeeping(ReadableMap map, Promise promise) {
        AwarenessBarrier awarenessBarrier;
        if (map.hasKey("deviceType") && map.hasKey("bluetoothStatus")) {
            int deviceType = map.getInt("deviceType");
            int bluetoothStatus = map.getInt("bluetoothStatus");
            awarenessBarrier = BluetoothBarrier.keep(deviceType, bluetoothStatus);
        } else {
            errorMessage(null, "bluetoothBarrier", TAG,
                    WRONG_PARAMS + "::deviceType and bluetoothStatus", promise);
            return null;
        }
        return awarenessBarrier;
    }

    private AwarenessBarrier bluetoothConnecting(ReadableMap map, Promise promise) {
        if (!map.hasKey("bluetoothStatus")) {
            errorMessage(null, "bluetoothBarrier", TAG,
                    WRONG_PARAMS + "::bluetoothStatus", promise);
            return null;
        }
        int bluetoothStatus = map.getInt("bluetoothStatus");
        return BluetoothBarrier.connecting(bluetoothStatus);
    }

    private AwarenessBarrier bluetoothDisconnecting(ReadableMap map, Promise promise) {
        if (!map.hasKey("bluetoothStatus")) {
            errorMessage(null, "bluetoothBarrier", TAG,
                    WRONG_PARAMS + "::bluetoothStatus", promise);
            return null;
        }
        int bluetoothStatus = map.getInt("bluetoothStatus");
        return BluetoothBarrier.disconnecting(bluetoothStatus);
    }

    private AwarenessBarrier behaviorBarrier(ReadableMap map, Promise promise) {
        AwarenessBarrier awarenessBarrier;
        try {
            if (!PermissionUtils.hasActivityRecognitionPermission(getCurrentActivity())) {
                PermissionUtils.requestActivityRecognitionPermission(getCurrentActivity(), promise);
                return null;
            }

            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return null;
            }
            if (!map.hasKey("behaviorTypes")
                    || Objects.requireNonNull(map.getArray("behaviorTypes")).size() == 0) {
                errorMessage(null, "behaviorBarrier", TAG,
                        WRONG_PARAMS + "::behaviorTypes", promise);
                return null;
            }

            ReadableArray readableTypes = map.getArray("behaviorTypes");
            readableArrayConvertToIntArray(readableTypes);
            int[] behaviorTypes = readableArrayConvertToIntArray(readableTypes);

            if (barrierReceiverAction.equals(Constants.getAllConstants().get("BEHAVIOR_KEEPING"))) {
                awarenessBarrier = BehaviorBarrier.keeping(behaviorTypes);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("BEHAVIOR_BEGINNING"))) {
                awarenessBarrier = BehaviorBarrier.beginning(behaviorTypes);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("BEHAVIOR_ENDING"))) {
                awarenessBarrier = BehaviorBarrier.ending(behaviorTypes);
            } else {
                errorMessage(null, "behaviorBarrier", TAG, WRONG_PARAMS, promise);
                return null;
            }
        } catch (IllegalArgumentException e) {
            errorMessage(null, "behaviorBarrier", TAG, e, promise);
            return null;
        }
        return awarenessBarrier;
    }

    private AwarenessBarrier locationBarrier(ReadableMap map, Promise promise) {
        AwarenessBarrier awarenessBarrier = null;
        try {
            if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
                PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
                return null;
            }

            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return null;
            }
            if (barrierReceiverAction.equals(Constants.getAllConstants().get("LOCATION_ENTER"))) {
                awarenessBarrier = locationEnter(map, promise);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("LOCATION_STAY"))) {
                awarenessBarrier = locationStay(map, promise);
            } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("LOCATION_EXIT"))) {
                awarenessBarrier = locationExit(map, promise);
            } else {
                errorMessage(null, "locationBarrier", TAG, WRONG_PARAMS, promise);
            }
        } catch (IllegalArgumentException e) {
            errorMessage(null, "locationBarrier", TAG, e, promise);
            return null;
        }
        return awarenessBarrier;
    }

    @SuppressLint("MissingPermission")
    private AwarenessBarrier locationEnter(@NonNull ReadableMap map, @NonNull Promise promise) {
        if (map.hasKey("latitude")
                && map.hasKey("longitude")
                && map.hasKey("radius")) {
            double latitude = map.getDouble("latitude");
            double longitude = map.getDouble("longitude");
            double radius = map.getDouble("radius");
            return LocationBarrier.enter(latitude, longitude, radius);
        } else {
            errorMessage(null, "locationBarrier", TAG,
                    WRONG_PARAMS + "::latitude,longitude,radius", promise);
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    private AwarenessBarrier locationExit(@NonNull ReadableMap map, @NonNull Promise promise) {
        if (map.hasKey("latitude")
                && map.hasKey("longitude")
                && map.hasKey("radius")) {
            double latitude = map.getDouble("latitude");
            double longitude = map.getDouble("longitude");
            double radius = map.getDouble("radius");
            return LocationBarrier.exit(latitude, longitude, radius);
        } else {
            errorMessage(null, "locationBarrier", TAG,
                    WRONG_PARAMS + "::latitude,longitude,radius", promise);
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    private AwarenessBarrier locationStay(ReadableMap map, Promise promise) {
        if (map.hasKey("latitude")
                && map.hasKey("longitude")
                && map.hasKey("radius")
                && map.hasKey("timeOfDuration")) {
            double latitude = map.getDouble("latitude");
            double longitude = map.getDouble("longitude");
            double radius = map.getDouble("radius");
            long timeOfDuration = (long) map.getDouble("timeOfDuration");
            return LocationBarrier.stay(latitude, longitude, radius, timeOfDuration);
        } else {
            errorMessage(null, "locationBarrier", TAG,
                    WRONG_PARAMS + "::latitude,longitude,radius,timeOfDuration", promise);
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    private AwarenessBarrier timeBarrier(ReadableMap map, Promise promise) {
        AwarenessBarrier awarenessBarrier;
        try {
            if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
                PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
                return null;
            }

            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return null;
            }
            if (barrierReceiverAction.equals(getAllConstants().get("TIME_IN_SUNRISE_OR_SUNSET_PERIOD"))) {
                awarenessBarrier = inSunriseOrSunsetPeriod(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("TIME_DURING_PERIOD_OF_DAY"))) {
                awarenessBarrier = duringPeriodOfDay(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("TIME_DURING_TIME_PERIOD"))) {
                awarenessBarrier = duringTimePeriod(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("TIME_DURING_PERIOD_OF_WEEK"))) {
                awarenessBarrier = duringPeriodOfWeek(map, promise);
            } else if (barrierReceiverAction.equals(getAllConstants().get("TIME_IN_TIME_CATEGORY"))) {
                awarenessBarrier = inTimeCategory(map, promise);
            } else {
                errorMessage(null, "timeBarrier", TAG, WRONG_PARAMS, promise);
                return null;
            }
        } catch (IllegalArgumentException e) {
            errorMessage(null, "timeBarrier", TAG, e, promise);
            return null;
        }
        return awarenessBarrier;
    }

    @SuppressLint("MissingPermission")
    private AwarenessBarrier inSunriseOrSunsetPeriod(ReadableMap map, Promise promise) {
        if (map.hasKey("startTimeOffset") &&
                map.hasKey("stopTimeOffset") &&
                map.hasKey("timeInstant")) {
            long startTimeOffset = (long) map.getDouble("startTimeOffset");
            long stopTimeOffset = (long) map.getDouble("stopTimeOffset");
            int timeInstant = map.getInt("timeInstant");
            return TimeBarrier.inSunriseOrSunsetPeriod(timeInstant, startTimeOffset, stopTimeOffset);
        } else {
            errorMessage(null, "timeBarrier", TAG,
                    WRONG_PARAMS + "::startTimeOffset,stopTimeOffset,timeInstant", promise);
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    private AwarenessBarrier duringPeriodOfDay(ReadableMap map, Promise promise) {
        if (map.hasKey("startTimeOfDay")
                && map.hasKey("stopTimeOfDay")) {
            long startTimeOfDay = (long) map.getDouble("startTimeOfDay");
            long stopTimeOfDay = (long) map.getDouble("stopTimeOfDay");

            TimeZone timeZone;
            if (map.hasKey("timeZoneId") && map.getString("timeZoneId") != null) {
                String timeZoneId = map.getString("timeZoneId");
                timeZone = TimeZone.getTimeZone(timeZoneId);
            } else {
                timeZone = TimeZone.getDefault();
            }
            return TimeBarrier.duringPeriodOfDay(timeZone, startTimeOfDay, stopTimeOfDay);
        } else {
            errorMessage(null, "timeBarrier", TAG,
                    WRONG_PARAMS + "::startTimeOfDay,stopTimeOfDay,timeZoneId", promise);
            return null;
        }
    }

    private AwarenessBarrier duringTimePeriod(ReadableMap map, Promise promise) {
        if (map.hasKey("startTimeStamp")
                && map.hasKey("stopTimeStamp")) {

            long startTimeStamp = (long) map.getDouble("startTimeStamp");
            long stopTimeStamp = (long) map.getDouble("stopTimeStamp");
            return TimeBarrier.duringTimePeriod(startTimeStamp, stopTimeStamp);
        } else {
            errorMessage(null, "timeBarrier", TAG,
                    WRONG_PARAMS + "::startTimeStamp,stopTimeStamp", promise);
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    private AwarenessBarrier duringPeriodOfWeek(ReadableMap map, Promise promise) {
        if (map.hasKey("dayOfWeek")
                && map.hasKey("startTimeOfSpecifiedDay")
                && map.hasKey("stopTimeOfSpecifiedDay")) {

            int dayOfWeek = map.getInt("dayOfWeek");
            long startTimeOfSpecifiedDay = (long) map.getDouble("startTimeOfSpecifiedDay");
            long stopTimeOfSpecifiedDay = (long) map.getDouble("stopTimeOfSpecifiedDay");
            return TimeBarrier.duringPeriodOfWeek(
                    dayOfWeek, TimeZone.getDefault(), startTimeOfSpecifiedDay, stopTimeOfSpecifiedDay);
        } else {
            errorMessage(null, "timeBarrier", TAG,
                    WRONG_PARAMS + "::dayOfWeek,startTimeOfSpecifiedDay,stopTimeOfSpecifiedDay", promise);
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    private AwarenessBarrier inTimeCategory(ReadableMap map, Promise promise) {
        if (map.hasKey("timeCategory")) {
            int timeCategory = map.getInt("timeCategory");
            return TimeBarrier.inTimeCategory(timeCategory);
        } else {
            errorMessage(null, "timeBarrier", TAG,
                    WRONG_PARAMS + "::timeCategory", promise);
            return null;
        }
    }

    private AwarenessBarrier beaconBarrier(ReadableMap map, Promise promise) {
        try {
            if (!PermissionUtils.hasLocationPermission(getCurrentActivity())) {
                PermissionUtils.requestLocationPermission(getCurrentActivity(), promise);
                return null;
            }

            String barrierReceiverAction = barrierReceiverActionControl(map, promise);
            if (barrierReceiverAction == null){
                return null;
            }
            return createBeaconBarrier(barrierReceiverAction, map, promise);

        } catch (IllegalArgumentException e) {
            errorMessage(null, "beaconBarrier", TAG, e, promise);
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    private AwarenessBarrier createBeaconBarrier(String barrierReceiverAction, ReadableMap map, Promise promise) {
        String method = "beaconBarrier";
        if (map == null
                || !map.hasKey("beaconArray")
                || map.getArray("beaconArray") == null
                || Objects.requireNonNull(map.getArray("beaconArray")).size() < 0) {
            errorMessage(null, method, TAG, WRONG_PARAMS, promise);
            return null;
        }

        ReadableArray array = map.getArray("beaconArray");
        int size = Objects.requireNonNull(array).size();

        BeaconStatus.Filter[] filters = new BeaconStatus.Filter[size];
        for (int i = 0; i < size; i++) {
            ReadableMap item = array.getMap(i);
            assert item != null;
            String namespace = item.getString("namespace");
            String type = item.getString("type");
            String content = item.getString("content");

            if (namespace == null || type == null || content == null) {
                errorMessage(null, "beaconBarrier", TAG,
                        WRONG_PARAMS + "::null::nameSpace,type,content", promise);
                return null;
            }
            BeaconStatus.Filter filter = BeaconStatus.Filter.match(namespace, type, content.getBytes(StandardCharsets.UTF_8));
            filters[i] = filter;
        }

        AwarenessBarrier awarenessBarrier;
        if (barrierReceiverAction.equals(Constants.getAllConstants().get("BEACON_DISCOVER"))) {
            awarenessBarrier = BeaconBarrier.discover(filters);
        } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("BEACON_KEEP"))) {
            awarenessBarrier = BeaconBarrier.keep(filters);
        } else if (barrierReceiverAction.equals(Constants.getAllConstants().get("BEACON_MISSED"))) {
            awarenessBarrier = BeaconBarrier.missed(filters);
        } else {
            errorMessage(null, "beaconBarrier", TAG, WRONG_PARAMS, promise);
            return null;
        }
        return awarenessBarrier;
    }

    private Activity getCurrentActivity() {
        return context.getCurrentActivity();
    }
}
