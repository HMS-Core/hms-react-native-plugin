/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.location.backend.providers;

import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import com.huawei.hms.location.GeofenceErrorCodes;
import com.huawei.hms.location.GeofenceRequest;
import com.huawei.hms.location.GeofenceService;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.rn.location.backend.helpers.Constants;
import com.huawei.hms.rn.location.backend.helpers.Exceptions;
import com.huawei.hms.rn.location.backend.helpers.HMSBroadcastReceiver;
import com.huawei.hms.rn.location.backend.helpers.Pair;
import com.huawei.hms.rn.location.backend.interfaces.HMSCallback;
import com.huawei.hms.rn.location.backend.interfaces.HMSProvider;
import com.huawei.hms.rn.location.backend.logger.HMSLogger;
import com.huawei.hms.rn.location.backend.logger.HMSMethod;
import com.huawei.hms.rn.location.backend.utils.GeofenceUtils;
import com.huawei.hms.rn.location.backend.utils.PlatformUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.huawei.hms.rn.location.backend.helpers.Exceptions.ERR_NO_EXISTENT_REQUEST_ID;

public class GeofenceProvider extends HMSProvider {
    private final static String TAG = GeofenceProvider.class.getSimpleName();

    private GeofenceService geofenceService;

    public GeofenceProvider(Context ctx) {
        super(ctx);
        this.geofenceService = LocationServices.getGeofenceService(getContext());
    }

    public JSONObject getConstants() throws JSONException {
        Log.d(TAG, "Initializing constants");

        JSONObject geofenceRequestConstants = new JSONObject();
        geofenceRequestConstants.put("ENTER_INIT_CONVERSION", GeofenceRequest.ENTER_INIT_CONVERSION);
        geofenceRequestConstants.put("EXIT_INIT_CONVERSION", GeofenceRequest.EXIT_INIT_CONVERSION);
        geofenceRequestConstants.put("DWELL_INIT_CONVERSION", GeofenceRequest.DWELL_INIT_CONVERSION);
        geofenceRequestConstants.put("COORDINATE_TYPE_WGS_84", GeofenceRequest.COORDINATE_TYPE_WGS_84);
        geofenceRequestConstants.put("COORDINATE_TYPE_GCJ_02", GeofenceRequest.COORDINATE_TYPE_GCJ_02);

        JSONObject geofenceConstants = new JSONObject();
        geofenceConstants.put("ENTER_GEOFENCE_CONVERSION", com.huawei.hms.location.Geofence.ENTER_GEOFENCE_CONVERSION);
        geofenceConstants.put("EXIT_GEOFENCE_CONVERSION", com.huawei.hms.location.Geofence.EXIT_GEOFENCE_CONVERSION);
        geofenceConstants.put("DWELL_GEOFENCE_CONVERSION", com.huawei.hms.location.Geofence.DWELL_GEOFENCE_CONVERSION);
        geofenceConstants.put("GEOFENCE_NEVER_EXPIRE", com.huawei.hms.location.Geofence.GEOFENCE_NEVER_EXPIRE);

        JSONObject errorConstants = new JSONObject();
        errorConstants.put("GEOFENCE_UNAVAILABLE", GeofenceErrorCodes.GEOFENCE_UNAVAILABLE);
        errorConstants.put("GEOFENCE_NUMBER_OVER_LIMIT", GeofenceErrorCodes.GEOFENCE_NUMBER_OVER_LIMIT);
        errorConstants.put("GEOFENCE_PENDINGINTENT_OVER_LIMIT", GeofenceErrorCodes.GEOFENCE_PENDINGINTENT_OVER_LIMIT);
        errorConstants.put("GEOFENCE_INSUFFICIENT_PERMISSION", GeofenceErrorCodes.GEOFENCE_INSUFFICIENT_PERMISSION);
        errorConstants.put("GEOFENCE_REQUEST_TOO_OFTEN", GeofenceErrorCodes.GEOFENCE_REQUEST_TOO_OFTEN);

        JSONObject eventConstants = new JSONObject();
        eventConstants.put("GEOFENCE_RESULT", Constants.Event.GEOFENCE_RESULT.getVal());

        JSONObject constants = new JSONObject();
        constants.put("GeofenceRequestConstants", geofenceRequestConstants);
        constants.put("GeofenceConstants", geofenceConstants);
        constants.put("ErrorCodes", errorConstants);
        constants.put("Events", eventConstants);

        return constants;
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

        HMSLogger.getInstance(getActivity()).startMethodExecutionTimer(method.getName());
        geofenceService.createGeofenceList(geofenceRequest, pendingIntent)
                .addOnSuccessListener(PlatformUtils.successListener(method, getActivity(), callback,
                        PlatformUtils.keyValPair("requestCode", requestCode)))
                .addOnFailureListener(PlatformUtils.failureListener(method, getActivity(), callback));
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

        HMSLogger.getInstance(getActivity()).startMethodExecutionTimer(method.getName());
        geofenceService.deleteGeofenceList(requests.get(requestCode))
                .addOnSuccessListener(PlatformUtils.successListener(method, getActivity(), callback))
                .addOnFailureListener(PlatformUtils.failureListener(method, getActivity(), callback));
        Log.i(TAG, "deleteGeofenceList end");
    }
}
