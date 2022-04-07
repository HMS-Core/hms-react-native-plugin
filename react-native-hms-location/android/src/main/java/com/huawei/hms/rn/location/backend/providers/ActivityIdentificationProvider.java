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

import com.huawei.hms.location.ActivityConversionInfo;
import com.huawei.hms.location.ActivityConversionRequest;
import com.huawei.hms.location.ActivityIdentification;
import com.huawei.hms.location.ActivityIdentificationData;
import com.huawei.hms.location.ActivityIdentificationService;
import com.huawei.hms.rn.location.backend.helpers.Constants;
import com.huawei.hms.rn.location.backend.helpers.Exceptions;
import com.huawei.hms.rn.location.backend.helpers.HMSBroadcastReceiver;
import com.huawei.hms.rn.location.backend.interfaces.HMSCallback;
import com.huawei.hms.rn.location.backend.interfaces.HMSProvider;
import com.huawei.hms.rn.location.backend.logger.HMSLogger;
import com.huawei.hms.rn.location.backend.logger.HMSMethod;
import com.huawei.hms.rn.location.backend.utils.ActivityUtils;
import com.huawei.hms.rn.location.backend.utils.PermissionUtils;
import com.huawei.hms.rn.location.backend.utils.PlatformUtils;

import com.facebook.react.bridge.ReactApplicationContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityIdentificationProvider extends HMSProvider {
    private static final String TAG = ActivityIdentificationProvider.class.getSimpleName();

    private HMSCallback permissionResultCallback;

    private ActivityIdentificationService activityService;

    public ActivityIdentificationProvider(ReactApplicationContext ctx) {
        super(ctx);
        this.activityService = ActivityIdentification.getService(getContext());
    }

    @Override
    public JSONObject getConstants() throws JSONException {
        return new JSONObject().put("Activities", new JSONObject().put("VEHICLE", ActivityIdentificationData.VEHICLE)
            .put("BIKE", ActivityIdentificationData.BIKE)
            .put("FOOT", ActivityIdentificationData.FOOT)
            .put("RUNNING", ActivityIdentificationData.RUNNING)
            .put("STILL", ActivityIdentificationData.STILL)
            .put("OTHERS", ActivityIdentificationData.OTHERS)
            .put("WALKING", ActivityIdentificationData.WALKING))
            .put("ActivityConversions",
                new JSONObject().put("ENTER_ACTIVITY_CONVERSION", ActivityConversionInfo.ENTER_ACTIVITY_CONVERSION)
                    .put("EXIT_ACTIVITY_CONVERSION", ActivityConversionInfo.EXIT_ACTIVITY_CONVERSION))
            .put("Events", new JSONObject().put("ACTIVITY_CONVERSION", Constants.Event.ACTIVITY_CONVERSION.getVal())
                .put("ACTIVITY_IDENTIFICATION", Constants.Event.ACTIVITY_IDENTIFICATION.getVal()));
    }

    // @ExposedMethod
    public void createActivityConversionUpdates(final int requestCode, JSONArray activityConversionRequestArray,
        final HMSCallback callback) {
        Log.i(TAG, "createActivityConversionUpdates start");
        HMSMethod method = new HMSMethod("createActivityConversionUpdates", true);

        ActivityConversionRequest request = ActivityUtils.FROM_JSON_ARRAY_TO_ACTIVITY_CONVERSION_REQUEST.map(
            activityConversionRequestArray);

        final PendingIntent pendingIntent = buildPendingIntent(requestCode,
            HMSBroadcastReceiver.getPackageAction(getContext(), HMSBroadcastReceiver.ACTION_HMS_CONVERSION));

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer(method.getName());
        activityService.createActivityConversionUpdates(request, pendingIntent)
            .addOnSuccessListener(PlatformUtils.successListener(method, getContext(), callback,
                PlatformUtils.keyValPair("requestCode", requestCode)))
            .addOnFailureListener(PlatformUtils.failureListener(method, getContext(), callback));

        Log.i(TAG, "createActivityConversionUpdates end");
    }

    // @ExposedMethod
    public void createActivityIdentificationUpdates(final int requestCode, double intervalMillis,
        final HMSCallback callback) {
        Log.i(TAG, "createActivityIdentificationUpdates start");
        HMSMethod method = new HMSMethod("createActivityIdentificationUpdates", true);

        final PendingIntent pendingIntent = buildPendingIntent(requestCode,
            HMSBroadcastReceiver.getPackageAction(getContext(), HMSBroadcastReceiver.ACTION_HMS_IDENTIFICATION));

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer(method.getName());
        activityService.createActivityIdentificationUpdates((long) intervalMillis, pendingIntent)
            .addOnSuccessListener(PlatformUtils.successListener(method, getContext(), callback,
                PlatformUtils.keyValPair("requestCode", requestCode)))
            .addOnFailureListener(PlatformUtils.failureListener(method, getContext(), callback));

        Log.i(TAG, "createActivityIdentificationUpdates end");
    }

    // @ExposedMethod
    public void deleteActivityConversionUpdates(final int requestCode, final HMSCallback callback) {
        Log.i(TAG, "deleteActivityConversionUpdates start");
        HMSMethod method = new HMSMethod("deleteActivityConversionUpdates", true);

        if (!requests.containsKey(requestCode)) {
            callback.error(Exceptions.toErrorJSON(ERR_NO_EXISTENT_REQUEST_ID));
            return;
        }

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer(method.getName());
        activityService.deleteActivityConversionUpdates(requests.get(requestCode))
            .addOnSuccessListener(PlatformUtils.successListener(method, getContext(), callback))
            .addOnFailureListener(PlatformUtils.failureListener(method, getContext(), callback));

        Log.i(TAG, "deleteActivityConversionUpdates end");
    }

    // @ExposedMethod
    public void deleteActivityIdentificationUpdates(final int requestCode, final HMSCallback callback) {
        Log.i(TAG, "deleteActivityIdentificationUpdates start");
        HMSMethod method = new HMSMethod("deleteActivityIdentificationUpdates", true);

        if (!requests.containsKey(requestCode)) {
            callback.error(Exceptions.toErrorJSON(ERR_NO_EXISTENT_REQUEST_ID));
            return;
        }

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer(method.getName());
        activityService.deleteActivityIdentificationUpdates(requests.get(requestCode))
            .addOnSuccessListener(PlatformUtils.successListener(method, getContext(), callback))
            .addOnFailureListener(PlatformUtils.failureListener(method, getContext(), callback));

        Log.i(TAG, "deleteActivityIdentificationUpdates end");
    }

    // @ExposedMethod
    public void requestPermission(final HMSCallback callback) {
        PermissionUtils.requestActivityRecognitionPermission(this);
        permissionResultCallback = callback;
    }

    // @ExposedMethod
    public void hasPermission(final HMSCallback callback) {
        boolean result = PermissionUtils.hasActivityRecognitionPermission(this);
        callback.success(PlatformUtils.keyValPair("hasPermission", result));
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        onRequestPermissionResult(requestCode, permissions, grantResults);
        return false;
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        JSONObject json = PermissionUtils.HANDLE_PERMISSION_REQUEST_RESULT.map(requestCode, permissions, grantResults);
        if (permissionResultCallback != null) {
            permissionResultCallback.success(json);
        } else {
            Log.w(TAG, "onRequestPermissionResult() :: null callback");
        }
    }
}
