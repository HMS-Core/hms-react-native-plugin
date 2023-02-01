/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.push.remote;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.aaid.entity.AAIDResult;
import com.huawei.hms.common.ApiException;

import java.util.HashMap;
import java.util.Map;

import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.common.ResolvableApiException;
import com.huawei.hms.rn.push.constants.Core;
import com.huawei.hms.rn.push.utils.ActivityUtils;
import com.huawei.hms.rn.push.logger.HMSLogger;
import com.huawei.hms.rn.push.utils.ResultUtils;

public class HmsPushInstanceId extends ReactContextBaseJavaModule {
    private final String TAG = HmsPushInstanceId.class.getSimpleName();

    private static volatile ReactApplicationContext context;

    public HmsPushInstanceId(ReactApplicationContext reactContext) {
        super(reactContext);
        setContext(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public Map<String, Object> getConstants() {
        return new HashMap<>();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    public static ReactApplicationContext getContext() {
        return context;
    }

    public static void setContext(ReactApplicationContext context) {
        HmsPushInstanceId.context = context;
    }

    @ReactMethod
    public void getToken(String scope, final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getToken");
        try {
            String appId = AGConnectServicesConfig.fromContext(getContext()).getString(Core.CLIENT_APP_ID);
            scope = scope == null ? Core.DEFAULT_TOKEN_SCOPE : scope;
            if (scope.trim().isEmpty()) {
                scope = Core.DEFAULT_TOKEN_SCOPE;
            }
            String token = HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).getToken(appId, scope);
            HMSLogger.getInstance(getContext()).sendSingleEvent("getToken");
            Log.d(TAG, "Token Received");
            ResultUtils.handleResult(true, token, promise);
        } catch (ApiException e) {
            if (e instanceof ResolvableApiException) {
                PendingIntent resolution = ((ResolvableApiException) e).getResolution();
                if (resolution != null) {
                    try {
                        resolution.send();
                    } catch (PendingIntent.CanceledException ex) {
                        HMSLogger.getInstance(getContext()).sendSingleEvent("getToken," + e.getMessage());
                    }
                } else {
                    Intent resolutionIntent = ((ResolvableApiException) e).getResolutionIntent();
                    if (resolutionIntent != null) {
                        HMSLogger.getInstance(getContext()).sendSingleEvent("has resolution by intent");
                        resolutionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(resolutionIntent);
                    }
                }
            }
            HMSLogger.getInstance(getContext()).sendSingleEvent("getToken", e.getMessage());
            ResultUtils.handleResult(false, e.getLocalizedMessage(), promise);
        }
    }

    @ReactMethod
    public void getTokenWithSubjectId(String subjectId, final Promise promise) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getTokenWithSubjectId");
        try {
            String token = HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext()))
                .getToken(subjectId);
            HMSLogger.getInstance(getContext()).sendSingleEvent("getTokenWithSubjectId");
            Log.d(TAG, "Token Received");
            ResultUtils.handleResult(true, token, promise);
        } catch (ApiException e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getTokenWithSubjectId", e.getMessage());
            ResultUtils.handleResult(false, e.getLocalizedMessage(), promise);
        }

    }

    @ReactMethod
    public void getId(final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getId");
        try {
            String instanceId = HmsInstanceId.getInstance(
                ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).getId();
            HMSLogger.getInstance(getContext()).sendSingleEvent("getId");
            ResultUtils.handleResult(true, instanceId, promise);
        } catch (Exception e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getId", e.getMessage());
            ResultUtils.handleResult(false, e.getLocalizedMessage(), promise);
        }
    }

    @ReactMethod
    public void getAAID(final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getAAID");
        Task<AAIDResult> idResult = HmsInstanceId.getInstance(
            ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).getAAID();
        idResult.addOnSuccessListener(aaidResult -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getAAID");
            ResultUtils.handleResult(true, aaidResult.getId(), promise);
        }).addOnFailureListener(e -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getAAID", e.getMessage());
            ResultUtils.handleResult(false, e.getLocalizedMessage(), promise);
        });

    }

    @ReactMethod
    public void getCreationTime(final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getCreationTime");
        try {
            String createTime =
                HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext()))
                    .getCreationTime() + "";
            HMSLogger.getInstance(getContext()).sendSingleEvent("getCreationTime");
            ResultUtils.handleResult(true, createTime, promise);
        } catch (Exception e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getCreationTime", e.getMessage());
            ResultUtils.handleResult(false, e.getLocalizedMessage(), promise);
        }
    }

    @ReactMethod
    public void deleteAAID(final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("deleteAAID");
        try {
            HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).deleteAAID();
            HMSLogger.getInstance(getContext()).sendSingleEvent("deleteAAID");
            ResultUtils.handleResult(true, true, promise);
        } catch (ApiException e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("deleteAAID", e.getMessage());
            ResultUtils.handleResult(false, e.getLocalizedMessage(), promise);
        }
    }

    @ReactMethod
    public void deleteToken(String scope, final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("deleteToken");
        try {
            String appId = AGConnectServicesConfig.fromContext(getContext()).getString(Core.CLIENT_APP_ID);
            scope = scope == null ? Core.DEFAULT_TOKEN_SCOPE : scope;
            if (scope.trim().isEmpty()) {
                scope = Core.DEFAULT_TOKEN_SCOPE;
            }
            HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext()))
                .deleteToken(appId, scope);
            HMSLogger.getInstance(getContext()).sendSingleEvent("deleteToken");
            ResultUtils.handleResult(true, true, promise);
        } catch (ApiException e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("deleteToken", e.getMessage());
            ResultUtils.handleResult(false, e.getLocalizedMessage(), promise);
        }
    }

    @ReactMethod
    public void deleteTokenWithSubjectId(String subjectId, final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("deleteTokenWithSubjectId");
        try {
            HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext()))
                .deleteToken(subjectId);
            HMSLogger.getInstance(getContext()).sendSingleEvent("deleteTokenWithSubjectId");
            ResultUtils.handleResult(true, true, promise);
        } catch (ApiException e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("deleteTokenWithSubjectId", e.getMessage());
            ResultUtils.handleResult(false, e.getLocalizedMessage(), promise);
        }
    }
}
