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

package com.huawei.hms.rn.location.backend.providers;

import static com.huawei.hms.rn.location.backend.helpers.Exceptions.ERR_NO_EXISTENT_REQUEST_ID;

import android.app.PendingIntent;
import android.util.Log;

import com.huawei.hms.location.GeofenceErrorCodes;
import com.huawei.hms.location.GeofenceRequest;
import com.huawei.hms.location.GeofenceService;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.rn.location.backend.helpers.Constants;
import com.huawei.hms.rn.location.backend.helpers.Exceptions;
import com.huawei.hms.rn.location.backend.helpers.HMSBroadcastReceiver;
import com.huawei.hms.rn.location.backend.interfaces.HMSCallback;
import com.huawei.hms.rn.location.backend.interfaces.HMSProvider;
import com.huawei.hms.rn.location.backend.logger.HMSLogger;
import com.huawei.hms.rn.location.backend.logger.HMSMethod;
import com.huawei.hms.rn.location.backend.utils.GeofenceUtils;
import com.huawei.hms.rn.location.backend.utils.PlatformUtils;

import com.facebook.react.bridge.ReactApplicationContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeofenceProvider extends HMSProvider {
    private final static String TAG = GeofenceProvider.class.getSimpleName();

    private GeofenceService geofenceService;

    public GeofenceProvider(ReactApplicationContext ctx) {
        super(ctx);
        this.geofenceService = LocationServices.getGeofenceService(ctx);
    }

    public JSONObject getConstants() throws JSONException {
        return new JSONObject().put("GeofenceRequestConstants",
            new JSONObject().put("ENTER_INIT_CONVERSION", GeofenceRequest.ENTER_INIT_CONVERSION)
                .put("EXIT_INIT_CONVERSION", GeofenceRequest.EXIT_INIT_CONVERSION)
                .put("DWELL_INIT_CONVERSION", GeofenceRequest.DWELL_INIT_CONVERSION)
                .put("COORDINATE_TYPE_WGS_84", GeofenceRequest.COORDINATE_TYPE_WGS_84)
                .put("COORDINATE_TYPE_GCJ_02", GeofenceRequest.COORDINATE_TYPE_GCJ_02))
            .put("GeofenceConstants", new JSONObject().put("ENTER_GEOFENCE_CONVERSION",
                com.huawei.hms.location.Geofence.ENTER_GEOFENCE_CONVERSION)
                .put("EXIT_GEOFENCE_CONVERSION", com.huawei.hms.location.Geofence.EXIT_GEOFENCE_CONVERSION)
                .put("DWELL_GEOFENCE_CONVERSION", com.huawei.hms.location.Geofence.DWELL_GEOFENCE_CONVERSION)
                .put("GEOFENCE_NEVER_EXPIRE", com.huawei.hms.location.Geofence.GEOFENCE_NEVER_EXPIRE))
            .put("ErrorCodes", new JSONObject().put("GEOFENCE_UNAVAILABLE", GeofenceErrorCodes.GEOFENCE_UNAVAILABLE)
                .put("GEOFENCE_NUMBER_OVER_LIMIT", GeofenceErrorCodes.GEOFENCE_NUMBER_OVER_LIMIT)
                .put("GEOFENCE_PENDINGINTENT_OVER_LIMIT", GeofenceErrorCodes.GEOFENCE_PENDINGINTENT_OVER_LIMIT)
                .put("GEOFENCE_INSUFFICIENT_PERMISSION", GeofenceErrorCodes.GEOFENCE_INSUFFICIENT_PERMISSION)
                .put("GEOFENCE_REQUEST_TOO_OFTEN", GeofenceErrorCodes.GEOFENCE_REQUEST_TOO_OFTEN))
            .put("Events", new JSONObject().put("GEOFENCE", Constants.Event.GEOFENCE.getVal()));
    }

    // @ExposedMethod
    public void createGeofenceList(final int requestCode, final JSONArray geofences, final int initConversions,
        final int coordinateType, final HMSCallback callback) {
        Log.i(TAG, "createGeofences start");
        HMSMethod method = new HMSMethod("createGeofenceList", true);

        final PendingIntent pendingIntent = buildPendingIntent(requestCode,
            HMSBroadcastReceiver.getPackageAction(getContext(), HMSBroadcastReceiver.ACTION_HMS_GEOFENCE));
        GeofenceRequest geofenceRequest = GeofenceUtils.FROM_JSON_ARRAY_TO_GEOFENCE.map(geofences, initConversions,
            coordinateType);

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer(method.getName());
        geofenceService.createGeofenceList(geofenceRequest, pendingIntent)
            .addOnSuccessListener(PlatformUtils.successListener(method, getContext(), callback,
                PlatformUtils.keyValPair("requestCode", requestCode)))
            .addOnFailureListener(PlatformUtils.failureListener(method, getContext(), callback));
        Log.i(TAG, "createGeofences end");
    }

    // @ExposedMethod
    public void deleteGeofenceList(int requestCode, final HMSCallback callback) {
        Log.i(TAG, "deleteGeofenceList start");
        HMSMethod method = new HMSMethod("deleteGeofenceList", true);

        if (!requests.containsKey(requestCode)) {
            callback.error(Exceptions.toErrorJSON(ERR_NO_EXISTENT_REQUEST_ID));
            return;
        }

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer(method.getName());
        geofenceService.deleteGeofenceList(requests.get(requestCode))
            .addOnSuccessListener(PlatformUtils.successListener(method, getContext(), callback))
            .addOnFailureListener(PlatformUtils.failureListener(method, getContext(), callback));
        Log.i(TAG, "deleteGeofenceList end");
    }
}
