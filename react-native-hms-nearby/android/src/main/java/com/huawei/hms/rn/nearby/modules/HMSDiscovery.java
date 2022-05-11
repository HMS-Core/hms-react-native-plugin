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

package com.huawei.hms.rn.nearby.modules;

import android.text.TextUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.nearby.Nearby;
import com.huawei.hms.nearby.discovery.BroadcastOption;
import com.huawei.hms.nearby.discovery.ConnectCallback;
import com.huawei.hms.nearby.discovery.ConnectInfo;
import com.huawei.hms.nearby.discovery.ConnectOption;
import com.huawei.hms.nearby.discovery.ConnectResult;
import com.huawei.hms.nearby.discovery.Policy;
import com.huawei.hms.nearby.discovery.ScanEndpointCallback;
import com.huawei.hms.nearby.discovery.ScanEndpointInfo;
import com.huawei.hms.nearby.discovery.ScanOption;
import com.huawei.hms.nearby.transfer.Data;
import com.huawei.hms.nearby.transfer.DataCallback;
import com.huawei.hms.nearby.transfer.TransferStateUpdate;
import com.huawei.hms.rn.nearby.utils.HMSUtils;

import java.io.IOException;

import static com.huawei.hms.rn.nearby.constants.HMSConstants.CONNECT_CALLBACK;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.CONNECT_ON_DISCONNECTED;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.CONNECT_ON_ESTABLISH;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.CONNECT_ON_RESULT;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.DATA_CALLBACK;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.DATA_ON_RECEIVED;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.DATA_ON_TRANSFER_UPDATE;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.DISCOVERY_CONSTANTS;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.SCAN_CALLBACK;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.SCAN_ON_FOUND;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.SCAN_ON_LOST;
import static com.huawei.hms.rn.nearby.utils.HMSResult.POLICY_FAIL;
import static com.huawei.hms.rn.nearby.utils.HMSResult.STRING_PARAM_FAIL;
import static com.huawei.hms.rn.nearby.utils.HMSResult.SUCCESS;

public class HMSDiscovery extends HMSBase {

    /**
     * Constructor that initializes discovery module
     *
     * @param context app context
     */
    public HMSDiscovery(ReactApplicationContext context) {
        super(context, HMSDiscovery.class.getSimpleName(), DISCOVERY_CONSTANTS);
    }

    /**
     * Accepts a connection. This API must be called before data transmission.
     * If the connection request is not accepted within 8 seconds, the connection fails and needs to be re-initiated.
     * Sets {@link #getDataCallback()} : A callback class called after data is received.
     *
     * @param endpointId ID of the remote endpoint.
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void acceptConnect(String endpointId, final Promise promise) {
        startMethodExecTimer("acceptConnect");

        if (TextUtils.isEmpty(endpointId)) {
            handleResult("acceptConnect", STRING_PARAM_FAIL, promise);
            return;
        }

        handleResult("acceptConnect",
            Nearby.getDiscoveryEngine(getContext()).acceptConnect(endpointId, getDataCallback()), promise);
    }

    /**
     * Disconnects from a remote endpoint. Then communication with the remote endpoint is no longer available.
     *
     * @param endpointId ID of the remote endpoint.
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void disconnect(String endpointId, final Promise promise) {
        startMethodExecTimer("disconnect");

        if (TextUtils.isEmpty(endpointId)) {
            handleResult("disconnect", STRING_PARAM_FAIL, promise);
            return;
        }

        Nearby.getDiscoveryEngine(getContext()).disconnect(endpointId);
        handleResult("disconnect", SUCCESS, promise);
    }

    /**
     * Rejects a connection request from a remote endpoint.
     *
     * @param endpointId ID of the remote endpoint.
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void rejectConnect(String endpointId, final Promise promise) {
        startMethodExecTimer("rejectConnect");

        if (TextUtils.isEmpty(endpointId)) {
            handleResult("rejectConnect", STRING_PARAM_FAIL, promise);
            return;
        }

        handleResult("rejectConnect", Nearby.getDiscoveryEngine(getContext()).rejectConnect(endpointId), promise);
    }

    /**
     * Sends a request to connect to a remote endpoint.
     * Sets {@link #getConnectCallback()} : A callback listener class called during connection.
     *
     * @param name Local endpoint name.
     * @param endpointId ID of the remote endpoint.
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void requestConnect(String name, String endpointId, final Promise promise) {
        startMethodExecTimer("requestConnect");

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(endpointId)) {
            handleResult("requestConnect", STRING_PARAM_FAIL, promise);
            return;
        }

        handleResult("requestConnect",
            Nearby.getDiscoveryEngine(getContext()).requestConnect(name, endpointId, getConnectCallback()), promise);
    }

    /**
     * Sends a connection request carrying specific connection options to the remote endpoint.
     * This is an extended method for requestConnect(String, String, ConnectCallback).
     * Sets {@link #getConnectCallback()} : A callback listener class called during connection.
     *
     * @param name Local endpoint name.
     * @param endpointId ID of the remote endpoint.
     * @param connectOptionMap Options Map.
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void requestConnectEx(String name, String endpointId, ReadableMap connectOptionMap, final Promise promise) {
        startMethodExecTimer("requestConnectEx");

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(endpointId)) {
            handleResult("requestConnectEx", STRING_PARAM_FAIL, promise);
            return;
        }

        ConnectOption connectOption = HMSUtils.getInstance().getConnectOptionFromReadableMap(connectOptionMap);

        handleResult("requestConnectEx", Nearby.getDiscoveryEngine(getContext())
            .requestConnectEx(name, endpointId, getConnectCallback(), connectOption), promise);
    }

    /**
     * Starts broadcasting.
     * Sets {@link #getConnectCallback()} : A callback listener class called when detecting a connection request sent by a remote endpoint.
     *
     * @param name Local endpoint name.
     * @param serviceId Service ID. The app package name is recommended.
     * @param policy Specifies the policy type that creates BroadcastOption: MESH, P2P, STAR.
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void startBroadcasting(String name, String serviceId, int policy, final Promise promise) {
        startMethodExecTimer("startBroadCasting");
        Policy broadcastPolicy = HMSUtils.getInstance().getPolicyByNumber(policy);

        if (broadcastPolicy == null) {
            handleResult("startBroadCasting", POLICY_FAIL, promise);
            return;
        }

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(serviceId)) {
            handleResult("startBroadCasting", STRING_PARAM_FAIL, promise);
            return;
        }

        handleResult("startBroadCasting", Nearby.getDiscoveryEngine(getContext())
            .startBroadcasting(name, serviceId, getConnectCallback(),
                new BroadcastOption.Builder().setPolicy(broadcastPolicy).build()), promise);
    }

    /**
     * Starts to scan for remote endpoints with the specified service ID.
     * Sets {@link #getScanEndpointCallback()} : A callback listener class called when discovering a remote
     * endpoint with the specified service ID.
     *
     * @param serviceId Service ID. The app package name is recommended.
     * @param policy Specifies the policy type that creates ScanOption: MESH, P2P, STAR.
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void startScan(String serviceId, int policy, final Promise promise) {
        startMethodExecTimer("startScan");
        Policy scanPolicy = HMSUtils.getInstance().getPolicyByNumber(policy);

        if (scanPolicy == null) {
            handleResult("startScan", POLICY_FAIL, promise);
            return;
        }

        if (TextUtils.isEmpty(serviceId)) {
            handleResult("startScan", STRING_PARAM_FAIL, promise);
            return;
        }

        handleResult("startScan", Nearby.getDiscoveryEngine(getContext())
                .startScan(serviceId, getScanEndpointCallback(), new ScanOption.Builder().setPolicy(scanPolicy).build()),
            promise);
    }

    /**
     * Stops broadcasting.
     * 
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void stopBroadCasting(final Promise promise) {
        startMethodExecTimer("stopBroadCasting");
        Nearby.getDiscoveryEngine(getContext()).stopBroadcasting();
        handleResult("stopBroadCasting", SUCCESS, promise);
    }

    /**
     * Disconnects all connections.
     * 
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void disconnectAll(final Promise promise) {
        startMethodExecTimer("disconnectAll");
        Nearby.getDiscoveryEngine(getContext()).disconnectAll();
        handleResult("disconnectAll", SUCCESS, promise);
    }

    /**
     * Stops discovering devices.
     * 
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void stopScan(final Promise promise) {
        startMethodExecTimer("stopScan");
        Nearby.getDiscoveryEngine(getContext()).stopScan();
        handleResult("stopScan", SUCCESS, promise);
    }

    /**
     * creator method for listener that obtains the data sending/receiving status
     *
     * @return DataCallback
     */
    private DataCallback getDataCallback() {
        return new DataCallback() {
            @Override
            public void onReceived(String endpointId, Data data) {
                WritableMap wm = Arguments.createMap();
                wm.putString("endpointId", endpointId);
                wm.putInt("type", data.getType());
                wm.putString("id", Long.toString(data.getId()));

                if (data.getType() == Data.Type.FILE) {
                    wm.putString("size", Long.toString(data.asFile().getSize()));
                    String fileUri = HMSUtils.getInstance().getFileUri(data.asFile());
                    if (fileUri != null) {
                        wm.putString("fileUri", fileUri);
                    }
                } else if (data.getType() == Data.Type.BYTES) {
                    wm.putArray("data", HMSUtils.getInstance().convertByteArrayToWritableArray(data.asBytes()));
                } else if (data.getType() == Data.Type.STREAM) {
                    try {
                        wm.putArray("data",
                            HMSUtils.getInstance().convertInputStreamToWritableArray(data.asStream().asInputStream()));
                    } catch (IOException e) {
                        wm.putString("message", e.getMessage());
                        wm.putArray("data", Arguments.createArray());
                    }
                }
                sendEvent(DATA_ON_RECEIVED, DATA_CALLBACK, wm);
            }

            @Override
            public void onTransferUpdate(String endpointId, TransferStateUpdate transferStateUpdate) {
                WritableMap onTransferUpdate = Arguments.createMap();
                onTransferUpdate.putString("endpointId", endpointId);
                onTransferUpdate.putString("transferredBytes",
                    Long.toString(transferStateUpdate.getBytesTransferred()));
                onTransferUpdate.putString("dataId", Long.toString(transferStateUpdate.getDataId()));
                onTransferUpdate.putInt("hashCode", transferStateUpdate.hashCode());
                onTransferUpdate.putInt("status", transferStateUpdate.getStatus());
                onTransferUpdate.putString("totalBytes", Long.toString(transferStateUpdate.getTotalBytes()));
                sendEvent(DATA_ON_TRANSFER_UPDATE, DATA_CALLBACK, onTransferUpdate);
            }
        };
    }

    /**
     * creator method for listener object called during connection.
     *
     * @return ConnectCallback
     */
    private ConnectCallback getConnectCallback() {
        return new ConnectCallback() {
            @Override
            public void onEstablish(String endpointId, ConnectInfo connectInfo) {
                WritableMap onEstablish = Arguments.createMap();
                onEstablish.putString("endpointId", endpointId);
                onEstablish.putString("authCode", connectInfo.getAuthCode());
                onEstablish.putString("endpointName", connectInfo.getEndpointName());
                onEstablish.putBoolean("isRemoteConnect", connectInfo.isRemoteConnect());
                sendEvent(CONNECT_ON_ESTABLISH, CONNECT_CALLBACK, onEstablish);
            }

            @Override
            public void onResult(String endpointId, ConnectResult connectResult) {
                WritableMap onResult = Arguments.createMap();
                onResult.putString("endpointId", endpointId);
                onResult.putInt("statusCode", connectResult.getStatus().getStatusCode());
                onResult.putString("statusMessage", connectResult.getStatus().getStatusMessage());
                onResult.putString("channelPolicy", connectResult.getChannelPolicy().toString());
                sendEvent(CONNECT_ON_RESULT, CONNECT_CALLBACK, onResult);
            }

            @Override
            public void onDisconnected(String endpointId) {
                WritableMap onDisconnected = Arguments.createMap();
                onDisconnected.putString("endpointId", endpointId);
                sendEvent(CONNECT_ON_DISCONNECTED, CONNECT_CALLBACK, onDisconnected);
            }
        };
    }

    /**
     * creator method for listener object for the device scanning result.
     *
     * @return ScanEndpointCallback
     */
    private ScanEndpointCallback getScanEndpointCallback() {
        return new ScanEndpointCallback() {
            @Override
            public void onFound(String endpointId, ScanEndpointInfo scanEndpointInfo) {
                WritableMap onFound = Arguments.createMap();
                onFound.putString("endpointId", endpointId);
                onFound.putString("name", scanEndpointInfo.getName());
                onFound.putString("serviceId", scanEndpointInfo.getServiceId());
                sendEvent(SCAN_ON_FOUND, SCAN_CALLBACK, onFound);
            }

            @Override
            public void onLost(String endpointId) {
                WritableMap onLost = Arguments.createMap();
                onLost.putString("endpointId", endpointId);
                sendEvent(SCAN_ON_LOST, SCAN_CALLBACK, onLost);
            }
        };
    }

}
