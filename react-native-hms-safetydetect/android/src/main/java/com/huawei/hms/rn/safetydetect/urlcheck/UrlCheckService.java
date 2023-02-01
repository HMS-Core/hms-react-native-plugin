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

package com.huawei.hms.rn.safetydetect.urlcheck;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.api.CommonStatusCodes;
import com.huawei.hms.rn.safetydetect.logger.HMSLogger;
import com.huawei.hms.rn.safetydetect.utils.HMSSafetyDetectUtils;
import com.huawei.hms.support.api.entity.safetydetect.UrlCheckThreat;
import com.huawei.hms.support.api.safetydetect.SafetyDetect;
import com.huawei.hms.support.api.safetydetect.SafetyDetectClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlCheckService {
    private String TAG = UrlCheckService.class.getSimpleName();
    private SafetyDetectClient client;
    private final HMSLogger hmsLogger;

    public UrlCheckService(ReactApplicationContext reactApplicationContext) {
        this.client = SafetyDetect.getClient(reactApplicationContext);
        this.hmsLogger = HMSLogger.getInstance(reactApplicationContext);
    }

    public void initUrlCheck(Promise promise) {
        hmsLogger.startMethodExecutionTimer("initUrlCheck");
        Task task = client.initUrlCheck();
        HMSSafetyDetectUtils.taskHandler(task, promise, hmsLogger, "initUrlCheck");
    }

    public void urlCheck(ReadableMap params, Promise promise) {
        hmsLogger.startMethodExecutionTimer("urlCheck");
        if (params == null) {
            String errorMessage = "Illegal argument. Params must not be null.";
            hmsLogger.sendSingleEvent("urlCheck", errorMessage);
            Log.e(TAG, errorMessage);
            promise.reject(errorMessage);
            return;
        }

        if (!params.hasKey("url") || params.isNull("url")) {
            String errorMessage = "Illegal argument. url field is mandatory and it must not be null..";
            hmsLogger.sendSingleEvent("urlCheck", errorMessage);
            Log.e(TAG, errorMessage);
            promise.reject(errorMessage);
            return;
        }

        if (!params.hasKey("appId") || params.isNull("appId")) {
            String errorMessage = "Illegal argument. appId field is mandatory and it must not be null.";
            hmsLogger.sendSingleEvent("urlCheck", errorMessage);
            Log.e(TAG, errorMessage);
            promise.reject(errorMessage);
            return;
        }

        invokeUrlCheck(params, promise);
    }

    private void invokeUrlCheck(ReadableMap params, Promise promise) {
        ArrayList urlCheckThreats = params.getArray("UrlCheckThreat").toArrayList();
        client.urlCheck(
            params.getString("url"),
            params.getString("appId"),
            UrlCheckUtils.convertIntegerArray(urlCheckThreats)
        ).addOnSuccessListener(urlResponse -> {
            List<UrlCheckThreat> urlCheckResponses = urlResponse.getUrlCheckResponse();
            ReadableArray threatsArray = UrlCheckUtils.convertListToArray(urlCheckResponses);
            hmsLogger.sendSingleEvent("urlCheck");
            promise.resolve(threatsArray);
        }).addOnFailureListener(e -> {
            if (e instanceof ApiException) {
                ApiException apiException = (ApiException) e;
                String errorMessage = "Error: " +
                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode());
                hmsLogger.sendSingleEvent("urlCheck", errorMessage);
                Log.e(TAG, errorMessage);
                promise.reject(errorMessage);
            } else {
                hmsLogger.sendSingleEvent("urlCheck", e.getMessage());
                Log.e(TAG, "Error: " + e.getMessage());
                promise.reject("Error: " + e.getMessage());
            }
        });
    }

    public void shutdownUrlCheck(Promise promise) {
        hmsLogger.startMethodExecutionTimer("shutdownUrlCheck");
        Task task = client.shutdownUrlCheck();
        HMSSafetyDetectUtils.taskHandler(task, promise, hmsLogger, "shutdownUrlCheck");
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();
        constants.put("MALWARE", UrlCheckThreat.MALWARE);
        constants.put("PHISHING", UrlCheckThreat.PHISHING);
        return constants;
    }
}
