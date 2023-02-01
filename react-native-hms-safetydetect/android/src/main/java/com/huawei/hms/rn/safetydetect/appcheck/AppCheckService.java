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

package com.huawei.hms.rn.safetydetect.appcheck;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.rn.safetydetect.logger.HMSLogger;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.api.entity.safetydetect.MaliciousAppsData;
import com.huawei.hms.support.api.entity.safetydetect.MaliciousAppsListResp;
import com.huawei.hms.support.api.safetydetect.SafetyDetect;
import com.huawei.hms.support.api.safetydetect.SafetyDetectClient;
import com.huawei.hms.support.api.safetydetect.SafetyDetectStatusCodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppCheckService {
    private final String TAG = AppCheckService.class.getSimpleName();
    private SafetyDetectClient client;
    private final HMSLogger hmsLogger;

    public AppCheckService(ReactApplicationContext reactApplicationContext) {
        this.client = SafetyDetect.getClient(reactApplicationContext);
        hmsLogger = HMSLogger.getInstance(reactApplicationContext);
    }

    public void invokeGetMaliciousApps(Promise promise) {
        hmsLogger.startMethodExecutionTimer("getMaliciousAppsList");
        Task task = client.getMaliciousAppsList();
        task.addOnSuccessListener(new OnSuccessListener<MaliciousAppsListResp>() {
            @Override
            public void onSuccess(MaliciousAppsListResp maliciousAppsListResp) {
                List<MaliciousAppsData> appsDataList = maliciousAppsListResp.getMaliciousAppsList();
                if (maliciousAppsListResp.getRtnCode() == CommonCode.OK) {
                    ReadableArray arrayAppDataList = AppCheckUtils.convertToWritableMap(appsDataList);
                    hmsLogger.sendSingleEvent("getMaliciousAppsList");
                    promise.resolve(arrayAppDataList);
                }else {
                    hmsLogger.sendSingleEvent("getMaliciousAppsList", maliciousAppsListResp.getErrorReason());
                    Log.e(TAG, "getMaliciousAppsList failed: " + maliciousAppsListResp.getErrorReason());
                    promise.reject("getMaliciousAppsList failed: " + maliciousAppsListResp.getErrorReason());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    String errorMessage = "Error: " + 
                        SafetyDetectStatusCodes.getStatusCodeString(apiException.getStatusCode()) + 
                        ": " + 
                        apiException.getStatusMessage();
                    hmsLogger.sendSingleEvent("getMaliciousAppsList", errorMessage);
                    Log.e(TAG, errorMessage );
                    promise.reject(errorMessage);
                } else {
                    hmsLogger.sendSingleEvent("getMaliciousAppsList", e.getMessage());
                    Log.e(TAG, "ERROR: " + e.getMessage());
                    promise.reject("ERROR: " + e.getMessage());
                }
            }
        });
    }

    public void enableAppsCheck(Promise promise) {
        hmsLogger.startMethodExecutionTimer("enableAppsCheck");
        client.enableAppsCheck().addOnSuccessListener(enableAppsCheckResp -> {
            hmsLogger.sendSingleEvent("enableAppsCheck");
            promise.resolve(enableAppsCheckResp.getResult());
        }).addOnFailureListener(e -> {
            hmsLogger.sendSingleEvent("enableAppsCheck", e.getMessage());
            promise.reject(e.getMessage());
        });
    }

    public void isVerifyAppsCheck(Promise promise) {
        hmsLogger.startMethodExecutionTimer("isVerifyAppsCheck");
        client.isVerifyAppsCheck().addOnSuccessListener(verifyAppsCheckEnabledResp -> {
            hmsLogger.sendSingleEvent("isVerifyAppsCheck");
            promise.resolve(verifyAppsCheckEnabledResp.getResult());
        }).addOnFailureListener(error -> {
            hmsLogger.sendSingleEvent("isVerifyAppsCheck", error.getMessage());
            promise.reject(error.getMessage());
        });
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();
        constants.put("RISK_APP", 1);
        constants.put("VIRUS_APP", 2);
        return constants;
    }
}
