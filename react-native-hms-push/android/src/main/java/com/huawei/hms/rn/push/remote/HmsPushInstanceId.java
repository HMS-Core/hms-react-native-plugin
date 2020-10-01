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

package com.huawei.hms.rn.push.remote;

import android.util.Log;

import com.facebook.react.bridge.Callback;
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
import com.huawei.hms.rn.push.constants.Core;
import com.huawei.hms.rn.push.constants.ResultCode;
import com.huawei.hms.rn.push.utils.ActivityUtils;
import com.huawei.hms.rn.push.logger.HMSLogger;

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
    public void getToken(Callback callback) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getToken");
        try {
            String appId = AGConnectServicesConfig.fromContext(getContext()).getString(Core.CLIENT_APP_ID);
            String token = HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).getToken(appId, Core.HCM);
            HMSLogger.getInstance(getContext()).sendSingleEvent("getToken");
            Log.d(TAG, "Token Received");
            callback.invoke(ResultCode.SUCCESS, token);
        } catch (ApiException e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getToken", e.getMessage());
            callback.invoke(e.getStatusCode(), e.getStatusCode());
        }

    }

    @ReactMethod
    public void getId(Callback callback) {

        try {
            HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getId");
            String instanceId = HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).getId();
            HMSLogger.getInstance(getContext()).sendSingleEvent("getId");
            callback.invoke(ResultCode.SUCCESS, instanceId);
        } catch (Exception e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getId", e.getMessage());
            callback.invoke(ResultCode.RESULT_FAILURE, e.getMessage());
        }
    }

    @ReactMethod
    public void getAAID(final Callback callback) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getAAID");
        Task<AAIDResult> idResult = HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).getAAID();
        idResult
                .addOnSuccessListener(aaidResult -> {
                    HMSLogger.getInstance(getContext()).sendSingleEvent("getAAID");
                    callback.invoke(ResultCode.SUCCESS, aaidResult.getId());
                })
                .addOnFailureListener(e -> {
                    HMSLogger.getInstance(getContext()).sendSingleEvent("getAAID", e.getMessage());
                    callback.invoke(ResultCode.RESULT_FAILURE, e.getMessage());
                });

    }

    @ReactMethod
    public void getCreationTime(Callback callback) {

        try {
            HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getCreationTime");
            String createTime = HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).getCreationTime() + "";
            HMSLogger.getInstance(getContext()).sendSingleEvent("getCreationTime");
            callback.invoke(ResultCode.SUCCESS, createTime);
        } catch (Exception e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("getCreationTime", e.getMessage());
            callback.invoke(ResultCode.RESULT_FAILURE, e.getMessage());
        }
    }

    @ReactMethod
    public void deleteAAID(Callback callback) {

        try {
            HMSLogger.getInstance(getContext()).startMethodExecutionTimer("deleteAAID");
            HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).deleteAAID();
            HMSLogger.getInstance(getContext()).sendSingleEvent("deleteAAID");
            callback.invoke(ResultCode.SUCCESS);
        } catch (ApiException e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("deleteAAID", e.getMessage());
            callback.invoke(e.getStatusCode(), e.getMessage());
        }
    }

    @ReactMethod
    public void deleteToken(Callback callback) {

        try {
            HMSLogger.getInstance(getContext()).startMethodExecutionTimer("deleteToken");
            String appId = AGConnectServicesConfig.fromContext(getContext()).getString(Core.CLIENT_APP_ID);
            HmsInstanceId.getInstance(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).deleteToken(appId, Core.HCM);
            HMSLogger.getInstance(getContext()).sendSingleEvent("deleteToken");
            callback.invoke(ResultCode.SUCCESS);
        } catch (ApiException e) {
            HMSLogger.getInstance(getContext()).sendSingleEvent("deleteToken", e.getMessage());
            callback.invoke(e.getStatusCode(), e.getMessage());
        }
    }
}
