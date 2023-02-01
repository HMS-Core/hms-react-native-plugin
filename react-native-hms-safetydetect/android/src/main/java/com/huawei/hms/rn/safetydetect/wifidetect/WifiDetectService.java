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

package com.huawei.hms.rn.safetydetect.wifidetect;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.rn.safetydetect.logger.HMSLogger;
import com.huawei.hms.support.api.safetydetect.SafetyDetect;
import com.huawei.hms.support.api.safetydetect.SafetyDetectClient;
import com.huawei.hms.support.api.safetydetect.SafetyDetectStatusCodes;

import java.util.HashMap;
import java.util.Map;

public class WifiDetectService {
    private String TAG = WifiDetectService.class.getSimpleName();
    private SafetyDetectClient client;
    private final HMSLogger hmsLogger;

    public WifiDetectService(ReactApplicationContext reactApplicationContext) {
        client = SafetyDetect.getClient(reactApplicationContext);
        hmsLogger = HMSLogger.getInstance(reactApplicationContext);
    }

    public void invokeGetWifiDetectStatus(Promise promise) {
        hmsLogger.startMethodExecutionTimer("getWifiDetectStatus");
        client.getWifiDetectStatus().addOnSuccessListener(wifiDetectResponse -> {
            int wifiDetectStatus = wifiDetectResponse.getWifiDetectStatus();
            hmsLogger.sendSingleEvent("getWifiDetectStatus");
            promise.resolve(wifiDetectStatus);
        }).addOnFailureListener(e -> {
            if (e instanceof ApiException) {
                ApiException apiException = (ApiException) e;
                String errMsg = "Error: " + apiException.getStatusCode() + ":"
                    + SafetyDetectStatusCodes.getStatusCodeString(apiException.getStatusCode()) + ": "
                    + apiException.getStatusMessage();
                hmsLogger.sendSingleEvent("getWifiDetectStatus", errMsg);
                Log.e(TAG, errMsg);
                promise.reject(errMsg);
            } else {
                hmsLogger.sendSingleEvent("getWifiDetectStatus", e.getMessage());
                Log.e(TAG, "ERROR! " + e.getMessage());
                promise.reject("ERROR! " + e.getMessage());
            }
        });
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();
        constants.put("NO_WiFi", 0);
        constants.put("WiFi_SECURE", 1);
        constants.put("WiFi_INSECURE", 2);
        return constants;
    }
}
