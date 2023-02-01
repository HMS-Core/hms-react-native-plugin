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

package com.huawei.hms.rn.safetydetect.sysintegrity;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.rn.safetydetect.logger.HMSLogger;
import com.huawei.hms.support.api.entity.safetydetect.SysIntegrityRequest;
import com.huawei.hms.support.api.safetydetect.SafetyDetect;
import com.huawei.hms.support.api.safetydetect.SafetyDetectClient;
import com.huawei.hms.support.api.safetydetect.SafetyDetectStatusCodes;

import java.nio.charset.Charset;

public class SysIntegrityService {
    private String TAG = SysIntegrityService.class.getSimpleName();
    private SafetyDetectClient client;
    private final HMSLogger hmsLogger;
    private ReactApplicationContext context;

    public SysIntegrityService(ReactApplicationContext reactApplicationContext) {
        context = reactApplicationContext;
        this.client = SafetyDetect.getClient(reactApplicationContext);
        hmsLogger = HMSLogger.getInstance(reactApplicationContext);
    }

    public void invokeSysIntegrity(String nonce, String appId, Promise promise) {
        hmsLogger.startMethodExecutionTimer("sysIntegrity");
        byte[] byteNonce = nonce.getBytes(Charset.defaultCharset());
        client.sysIntegrity(byteNonce, appId).addOnSuccessListener(sysIntegrityResp -> {
            String jwsStr = sysIntegrityResp.getResult();
            hmsLogger.sendSingleEvent("sysIntegrity");
            promise.resolve(jwsStr);
        }).addOnFailureListener(e -> {
            if (e instanceof ApiException) {
                ApiException apiException = (ApiException) e;
                String errorMessage = "Error: " +
                    SafetyDetectStatusCodes.getStatusCodeString(apiException.getStatusCode()) +
                    ": " +
                    apiException.getMessage();
                hmsLogger.sendSingleEvent("sysIntegrity", errorMessage);
                Log.e(TAG, errorMessage);
                promise.reject("",errorMessage);
            } else {
                hmsLogger.sendSingleEvent("sysIntegrity", e.getMessage());
                Log.e(TAG, "ERROR:" + e.getMessage());
                promise.reject("ERROR: ", e.getMessage());
            }
        });
    }

    public void invokeSysIntegretiyWithRequest(ReadableMap args, Promise promise){
        hmsLogger.startMethodExecutionTimer("sysIntegrityWithRequest");
        SysIntegrityRequest sysIntegrityRequest = new SysIntegrityRequest();
        sysIntegrityRequest.setAlg(args.getString("alg"));
        sysIntegrityRequest.setAppId(args.getString("appId"));
        sysIntegrityRequest.setNonce(args.getString("nonce").getBytes(Charset.defaultCharset()));

        SafetyDetect.getClient(context).sysIntegrity(sysIntegrityRequest).addOnFailureListener(e -> {
            hmsLogger.sendSingleEvent("sysIntegrityWithRequest",e.getMessage());
            Log.e(TAG, e.getMessage());
            promise.reject("",e.getMessage());
        }).addOnSuccessListener(sysIntegrityResp -> {
            hmsLogger.sendSingleEvent("sysIntegrityWithRequest");
            promise.resolve(sysIntegrityResp.getResult());
        });
    }
}
