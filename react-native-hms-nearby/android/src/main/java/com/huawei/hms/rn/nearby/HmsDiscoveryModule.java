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

package com.huawei.hms.rn.nearby;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.hms.nearby.Nearby;
import com.huawei.hms.nearby.discovery.BroadcastOption;
import com.huawei.hms.nearby.discovery.Policy;
import com.huawei.hms.nearby.discovery.ScanOption;
import com.huawei.hms.rn.nearby.constants.Constants;
import com.huawei.hms.rn.nearby.utils.HmsHelper;
import com.huawei.hms.rn.nearby.utils.HmsLogger;
import com.huawei.hms.rn.nearby.utils.HmsUtils;

import java.util.Map;

import static com.huawei.hms.rn.nearby.constants.Errors.E_DISCOVERY;

public class HmsDiscoveryModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsDiscoveryModule.class.getSimpleName();
    private BroadcastOption broadcastOption;
    private ScanOption scanOption;
    private ReactApplicationContext mContext;

    HmsDiscoveryModule(ReactApplicationContext context) {
        super(context);
        mContext = context;
    }

    /*
     * BROADCAST OPTION START
     */
    @ReactMethod
    public void createBroadcastOption(int policy, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("createBroadcastOption");
        Policy broadcastPolicy = HmsUtils.getInstance().getPolicyByNumber(policy);

        if (broadcastPolicy == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("createBroadcastOption", "Provide an allowed policy");
            promise.reject(E_DISCOVERY, "Provide an allowed policy");
            return;
        }

        broadcastOption = new BroadcastOption.Builder().setPolicy(broadcastPolicy).build();
        HmsLogger.getInstance(mContext).sendSingleEvent("createBroadcastOption");
        promise.resolve("broadcastOption set");
    }

    @ReactMethod
    public void getPolicyBroadcast(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("getPolicyBroadcast");

        if (broadcastOption == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("getPolicyBroadcast", "broadcastOption is null");
            promise.reject(E_DISCOVERY, "broadcastOption is null");
            return;
        }

        int policyNumber = -1;
        if (broadcastOption.getPolicy().equals(Policy.POLICY_MESH))
            policyNumber = Policy.MESH;
        else if (broadcastOption.getPolicy().equals(Policy.POLICY_P2P))
            policyNumber = Policy.P2P;
        else if (broadcastOption.getPolicy().equals(Policy.POLICY_STAR))
            policyNumber = Policy.STAR;

        HmsLogger.getInstance(mContext).sendSingleEvent("getPolicyBroadcast");
        promise.resolve(policyNumber);
    }

    @ReactMethod
    public void hashCodeBroadcast(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("hashCodeBroadcast");

        if (broadcastOption == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("hashCodeBroadcast", "broadcastOption is null");
            promise.reject(E_DISCOVERY, "broadcastOption is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("hashCodeBroadcast");
        promise.resolve(broadcastOption.hashCode());
    }

    @ReactMethod
    public void equalsBroadcast(int policy, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("equalsBroadcast");

        if (broadcastOption == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("equalsBroadcast", "broadcastOption is null");
            promise.reject(E_DISCOVERY, "broadcastOption is null");
            return;
        }

        Policy broadcastPolicy = HmsUtils.getInstance().getPolicyByNumber(policy);
        if (broadcastPolicy == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("equalsBroadcast", "Given policy number is not valid");
            promise.reject(E_DISCOVERY, "Given policy number is not valid");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("equalsBroadcast");
        promise.resolve(broadcastOption.equals(new BroadcastOption.Builder().setPolicy(broadcastPolicy).build()));
    }
    /*
     * BROADCAST OPTION END
     */

    /*
     * SCAN OPTION START
     * */
    @ReactMethod
    public void createScanOption(int policy, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("createScanOption");
        Policy scanPolicy = HmsUtils.getInstance().getPolicyByNumber(policy);

        if (scanPolicy == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("createScanOption", "Please provide an allowed policy");
            promise.reject(E_DISCOVERY, "Please provide an allowed policy");
            return;
        }

        scanOption = new ScanOption.Builder().setPolicy(scanPolicy).build();
        HmsLogger.getInstance(mContext).sendSingleEvent("createScanOption");
        promise.resolve("scanOption set");
    }

    @ReactMethod
    public void getPolicyScan(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("getPolicyScan");

        if (scanOption == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("getPolicyScan", "scanOptions is null");
            promise.reject(E_DISCOVERY, "scanOptions is null");
            return;
        }

        int policyNumber = -1;
        if (scanOption.getPolicy().equals(Policy.POLICY_MESH))
            policyNumber = Policy.MESH;
        else if (scanOption.getPolicy().equals(Policy.POLICY_P2P))
            policyNumber = Policy.P2P;
        else if (scanOption.getPolicy().equals(Policy.POLICY_STAR))
            policyNumber = Policy.STAR;

        HmsLogger.getInstance(mContext).sendSingleEvent("getPolicyScan");
        promise.resolve(policyNumber);
    }

    @ReactMethod
    public void hashCodeScan(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("hashCodeScan");

        if (scanOption == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("hashCodeScan", "scanOption is null");
            promise.reject(E_DISCOVERY, "scanOption is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("hashCodeScan");
        promise.resolve(scanOption.hashCode());
    }

    @ReactMethod
    public void equalsScan(int policy, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("equalsScan");

        if (scanOption == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("equalsScan", "scanOption is null");
            promise.reject(E_DISCOVERY, "scanOption is null");
            return;
        }

        Policy scanPolicy = HmsUtils.getInstance().getPolicyByNumber(policy);
        if (scanPolicy == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("equalsScan", "Given policy number is not valid");
            promise.reject(E_DISCOVERY, "Given policy number is not valid");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("equalsScan");
        promise.resolve(scanOption.equals(new ScanOption.Builder().setPolicy(scanPolicy).build()));
    }
    /*
     * SCAN OPTION END
     * */

    /*
     * DISCOVERY ENGINE START
     * */
    @ReactMethod
    public void acceptConnect(String enpointId, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("acceptConnect");

        if (enpointId == null || enpointId.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("acceptConnect", "Given endpoint id is empty or null");
            promise.reject(E_DISCOVERY, "Given endpoint id is empty or null");
            return;
        }

        Nearby.getDiscoveryEngine(mContext).acceptConnect(enpointId,
                HmsHelper.getInstance(mContext).getDataCallback())
                .addOnSuccessListener(aVoid -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("acceptConnect");
                    promise.resolve("Accept connect success");
                })
                .addOnFailureListener(e -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("acceptConnect", e.getMessage());
                    promise.reject(E_DISCOVERY, e.getMessage());
                });
    }

    @ReactMethod
    public void disconnect(String endpointId, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("disconnect");

        if (endpointId == null || endpointId.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("disconnect", "Given endpoint id is empty or null");
            promise.reject(E_DISCOVERY, "Given endpoint id is empty or null");
            return;
        }

        Nearby.getDiscoveryEngine(mContext).disconnect(endpointId);
        HmsLogger.getInstance(mContext).sendSingleEvent("disconnect");
        promise.resolve("Disconnected");
    }

    @ReactMethod
    public void rejectConnect(String endpointId, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("rejectConnect");

        if (endpointId == null || endpointId.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("rejectConnect", "Given endpoint id is empty or null");
            promise.reject(E_DISCOVERY, "Given endpoint id is empty or null");
            return;
        }

        Nearby.getDiscoveryEngine(mContext).rejectConnect(endpointId);
        HmsLogger.getInstance(mContext).sendSingleEvent("rejectConnect");
        promise.resolve("Rejected");
    }

    @ReactMethod
    public void requestConnect(String name, String endpointId, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("requestConnect");

        if (name == null || name.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("requestConnect", "Given name is null or empty");
            promise.reject(E_DISCOVERY, "Given name is null or empty");
            return;
        }

        if (endpointId == null || endpointId.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("requestConnect", "Given endpoint id is null or empty");
            promise.reject(E_DISCOVERY, "Given endpoint id is null or empty");
            return;
        }

        Nearby.getDiscoveryEngine(mContext).requestConnect(name, endpointId,
                HmsHelper.getInstance(mContext).getConnectCallback())
                .addOnSuccessListener(aVoid -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("requestConnect");
                    promise.resolve("Request Connect Success");
                })
                .addOnFailureListener(e -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("requestConnect", e.getMessage());
                    promise.reject(E_DISCOVERY, e.getMessage());
                });
    }

    @ReactMethod
    public void startBroadCasting(String name, String serviceId, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("startBroadCasting");

        if (broadcastOption == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("startBroadCasting", "broadcastOption is null");
            promise.reject(E_DISCOVERY, "broadcastOption is null");
            return;
        }

        if (name == null || name.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("startBroadCasting", "local endpoint name is null or empty");
            promise.reject(E_DISCOVERY, "local endpoint name is null or empty");
            return;
        }

        if (serviceId == null || serviceId.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("startBroadCasting", "service id is null or empty");
            promise.reject(E_DISCOVERY, "service id is null or empty");
            return;
        }

        Nearby.getDiscoveryEngine(mContext).startBroadcasting(name, serviceId,
                HmsHelper.getInstance(mContext).getConnectCallback(),
                broadcastOption)
                .addOnSuccessListener(aVoid -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("startBroadCasting");
                    promise.resolve("Broadcasting success");
                })
                .addOnFailureListener(e -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("startBroadCasting", e.getMessage());
                    promise.reject(E_DISCOVERY, e.getMessage());
                });
    }

    @ReactMethod
    public void startScan(String serviceId, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("startScan");

        if (scanOption == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("startScan", "scanOption is null");
            promise.reject(E_DISCOVERY, "scanOption is null");
            return;
        }

        if (serviceId == null || serviceId.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("startScan", "service id is null or empty");
            promise.reject(E_DISCOVERY, "service id is null or empty");
            return;
        }

        Nearby.getDiscoveryEngine(mContext).startScan(serviceId,
                HmsHelper.getInstance(mContext).getScanEndpointCallback(),
                scanOption)
                .addOnSuccessListener(aVoid -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("startScan");
                    promise.resolve("Start scan success");
                })
                .addOnFailureListener(e -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("startScan", e.getMessage());
                    promise.reject(E_DISCOVERY, e.getMessage());
                });
    }

    @ReactMethod
    public void stopBroadCasting(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("stopBroadCasting");
        Nearby.getDiscoveryEngine(mContext).stopBroadcasting();
        HmsLogger.getInstance(mContext).sendSingleEvent("stopBroadCasting");
        promise.resolve("Broadcasting stop");
    }

    @ReactMethod
    public void disconnectAll(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("disconnectAll");
        Nearby.getDiscoveryEngine(mContext).disconnectAll();
        HmsLogger.getInstance(mContext).sendSingleEvent("disconnectAll");
        promise.resolve("Disconnect All");
    }

    @ReactMethod
    public void stopScan(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("stopScan");
        Nearby.getDiscoveryEngine(mContext).stopScan();
        HmsLogger.getInstance(mContext).sendSingleEvent("stopScan");
        promise.resolve("Stop Scan");
    }
    /*
     * DISCOVERY ENGINE END
     * */

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return Constants.getDiscoveryConstants();
    }
}
