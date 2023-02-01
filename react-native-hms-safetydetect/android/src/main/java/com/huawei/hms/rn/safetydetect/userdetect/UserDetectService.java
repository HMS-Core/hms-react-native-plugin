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

package com.huawei.hms.rn.safetydetect.userdetect;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.rn.safetydetect.logger.HMSLogger;
import com.huawei.hms.rn.safetydetect.utils.HMSSafetyDetectUtils;
import com.huawei.hms.support.api.safetydetect.SafetyDetect;
import com.huawei.hms.support.api.safetydetect.SafetyDetectClient;
import com.huawei.hms.support.api.safetydetect.SafetyDetectStatusCodes;

public class UserDetectService {
    private String TAG = UserDetectService.class.getSimpleName();
    private SafetyDetectClient client;
    private final HMSLogger hmsLogger;

    public UserDetectService(ReactApplicationContext reactApplicationContext) {
        this.client = SafetyDetect.getClient(reactApplicationContext);
        this.hmsLogger = HMSLogger.getInstance(reactApplicationContext);
    }

    public void initUserDetect(Promise promise) {
        hmsLogger.startMethodExecutionTimer("initUserDetect");
        Task task = client.initUserDetect();
        HMSSafetyDetectUtils.taskHandler(task, promise, hmsLogger, "initUserDetect");
    }

    public void userDetection(String appId, Promise promise) {
        hmsLogger.startMethodExecutionTimer("userDetection");
        client.userDetection(appId).addOnSuccessListener(userDetectResponse -> {
            String responseToken = userDetectResponse.getResponseToken();
            if (!responseToken.isEmpty()) {
                hmsLogger.sendSingleEvent("userDetection");
                promise.resolve(responseToken);
            }
        }).addOnFailureListener(e -> {
            String errorMsg;
            if (e instanceof ApiException) {
                ApiException apiException = (ApiException) e;
                errorMsg = SafetyDetectStatusCodes.getStatusCodeString(apiException.getStatusCode()) +
                    ": " +
                    apiException.getMessage();
                hmsLogger.sendSingleEvent("userDetection", errorMsg);
                Log.e(TAG, "onFailure: " + errorMsg);
                promise.reject(errorMsg);
            } else {
                errorMsg = e.getMessage();
                hmsLogger.sendSingleEvent("userDetection", errorMsg);
                Log.e(TAG, "onFailure: " + errorMsg);
                promise.reject(errorMsg);
            }
        });
    }

    public void shutdownUserDetect(Promise promise) {
        hmsLogger.startMethodExecutionTimer("shutdownUserDetect");
        Task task = client.shutdownUserDetect();
        HMSSafetyDetectUtils.taskHandler(task, promise, hmsLogger, "shutdownUserDetect");
    }

    public void initAntiFraud(String appId, Promise promise) {
        hmsLogger.startMethodExecutionTimer("initAntiFraud");
        Task task = client.initAntiFraud(appId);
        HMSSafetyDetectUtils.taskHandler(task, promise, hmsLogger, "initAntiFraud");
    }

    public void getRiskToken(Promise promise) {
        hmsLogger.startMethodExecutionTimer("getRiskToken");
        client.getRiskToken().addOnSuccessListener(riskTokenResponse -> {
            String riskToken = riskTokenResponse.getRiskToken();
            hmsLogger.sendSingleEvent("getRiskToken");
            promise.resolve(riskToken);
        }).addOnFailureListener(e -> {
            hmsLogger.sendSingleEvent("getRiskToken", e.getMessage());
            Log.e(TAG, "onFailure: " + e.getMessage());
            promise.reject("ERROR: " + e.getMessage());
        });
    }

    public void releaseAntiFraud(Promise promise) {
        hmsLogger.startMethodExecutionTimer("releaseAntiFraud");
        Task task = client.releaseAntiFraud();
        HMSSafetyDetectUtils.taskHandler(task, promise, hmsLogger, "releaseAntiFraud");
    }
}
