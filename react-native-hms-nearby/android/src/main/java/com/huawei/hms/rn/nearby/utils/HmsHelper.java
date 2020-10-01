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

package com.huawei.hms.rn.nearby.utils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.nearby.discovery.BleSignal;
import com.huawei.hms.nearby.discovery.ConnectCallback;
import com.huawei.hms.nearby.discovery.ConnectInfo;
import com.huawei.hms.nearby.discovery.ConnectResult;
import com.huawei.hms.nearby.discovery.Distance;
import com.huawei.hms.nearby.discovery.ScanEndpointCallback;
import com.huawei.hms.nearby.discovery.ScanEndpointInfo;
import com.huawei.hms.nearby.message.GetCallback;
import com.huawei.hms.nearby.message.Message;
import com.huawei.hms.nearby.message.MessageHandler;
import com.huawei.hms.nearby.message.PutCallback;
import com.huawei.hms.nearby.message.StatusCallback;
import com.huawei.hms.nearby.transfer.Data;
import com.huawei.hms.nearby.transfer.DataCallback;
import com.huawei.hms.nearby.transfer.TransferStateUpdate;

import java.io.IOException;

public class HmsHelper {
    private static volatile HmsHelper instance;
    private final ReactApplicationContext reactApplicationContext;
    private StatusCallback statusCallback;
    private ConnectCallback connectCallback;
    private ScanEndpointCallback scanEndpointCallback;
    private DataCallback dataCallback;
    private PutCallback putCallback;
    private GetCallback getCallback;
    private MessageHandler messageHandler;


    HmsHelper(ReactApplicationContext context) {
        reactApplicationContext = context;
    }

    public static HmsHelper getInstance(ReactApplicationContext context) {
        if (instance == null)
            instance = new HmsHelper(context);
        return instance;
    }

    private void setConnectCallback() {
        connectCallback = new ConnectCallback() {
            @Override
            public void onEstablish(String endpointId, ConnectInfo connectInfo) {
                WritableMap onEstablish = Arguments.createMap();
                onEstablish.putString("endpointId", endpointId);
                onEstablish.putString("authCode", connectInfo.getAuthCode());
                onEstablish.putString("endpointName", connectInfo.getEndpointName());
                onEstablish.putBoolean("isRemoteConnect", connectInfo.isRemoteConnect());
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "connectOnEstablish", "setConnectCallback", onEstablish);
            }

            @Override
            public void onResult(String endpointId, ConnectResult connectResult) {
                WritableMap onResult = Arguments.createMap();
                onResult.putString("endpointId", endpointId);
                onResult.putInt("statusCode", connectResult.getStatus().getStatusCode());
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "connectOnResult", "setConnectCallback", onResult);
            }

            @Override
            public void onDisconnected(String endpointId) {
                WritableMap onDisconnected = Arguments.createMap();
                onDisconnected.putString("endpointId", endpointId);
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "connectOnDisconnected", "setConnectCallback", onDisconnected);
            }
        };
    }

    private void setScanEndpointCallback() {
        scanEndpointCallback = new ScanEndpointCallback() {
            @Override
            public void onFound(String endpointId, ScanEndpointInfo scanEndpointInfo) {
                WritableMap onFound = Arguments.createMap();
                onFound.putString("endpointId", endpointId);
                onFound.putString("name", scanEndpointInfo.getName());
                onFound.putString("serviceId", scanEndpointInfo.getServiceId());
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "scanCallbackOnFound", "setScanEndpointCallback", onFound);
            }

            @Override
            public void onLost(String endpointId) {
                WritableMap onLost = Arguments.createMap();
                onLost.putString("endpointId", endpointId);
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "scanCallbackOnLost", "setScanEndpointCallback", onLost);
            }
        };
    }

    private void setDataCallback() {
        dataCallback = new DataCallback() {
            @Override
            public void onReceived(String endpointId, Data data) {
                WritableMap received = Arguments.createMap();
                received.putString("endpointId", endpointId);
                received.putInt("type", data.getType());
                received.putString("id", Long.toString(data.getId()));
                if (data.getType() == Data.Type.FILE) {
                    received.putString("size", Long.toString(data.asFile().getSize()));
                    received.putString("fileUri", data.asFile().asJavaFile().toURI().toString());
                } else if (data.getType() == Data.Type.BYTES) {
                    received.putArray("data", HmsUtils.getInstance().convertByteArrToWa(data.asBytes()));
                } else {
                    try {
                        received.putString("data", HmsUtils.getInstance().convertStreamToString(data.asStream().asInputStream(), "UTF-8"));
                    } catch (IOException e) {
                        received.putString("data", e.getMessage());
                    }
                }
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "dataCallbackOnReceived", "setDataCallback", received);
            }

            @Override
            public void onTransferUpdate(String endpointId, TransferStateUpdate transferStateUpdate) {
                WritableMap onTransferUpdate = Arguments.createMap();
                onTransferUpdate.putString("endpointId", endpointId);
                onTransferUpdate.putString("transferredBytes", Long.toString(transferStateUpdate.getBytesTransferred()));
                onTransferUpdate.putString("dataId", Long.toString(transferStateUpdate.getDataId()));
                onTransferUpdate.putInt("hashCode", transferStateUpdate.hashCode());
                onTransferUpdate.putInt("status", transferStateUpdate.getStatus());
                onTransferUpdate.putString("totalBytes", Long.toString(transferStateUpdate.getTotalBytes()));
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "dataCallbackOnTransferUpdate", "setDataCallback", onTransferUpdate);
            }
        };
    }

    private void setPutCallback() {
        putCallback = new PutCallback() {
            @Override
            public void onTimeout() {
                WritableMap onTimeout = Arguments.createMap();
                onTimeout.putString("onTimeout", "Message publishing expired");
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "putCallback", "setPutCallback", onTimeout);
            }
        };
    }

    private void setStatusCallback() {
        statusCallback = new StatusCallback() {
            @Override
            public void onPermissionChanged(boolean grantPermission) {
                WritableMap onTimeout = Arguments.createMap();
                onTimeout.putBoolean("onPermissionChanged", grantPermission);
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "statusCallback", "setStatusCallback", onTimeout);
            }
        };
    }

    private void setGetCallback() {
        getCallback = new GetCallback() {
            @Override
            public void onTimeout() {
                WritableMap onTimeout = Arguments.createMap();
                onTimeout.putString("onTimeout", "Message subscription expired");
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "getCallback", "setGetCallback", onTimeout);
            }
        };
    }

    private void setMessageHandler() {
        messageHandler = new MessageHandler() {
            @Override
            public void onBleSignalChanged(Message message, BleSignal bleSignal) {
                WritableMap onBleSignalChanged = Arguments.createMap();
                onBleSignalChanged.putString("namespace", message.getNamespace());
                onBleSignalChanged.putString("type", message.getType());
                onBleSignalChanged.putArray("content", HmsUtils.getInstance().convertByteArrToWa(message.getContent()));
                onBleSignalChanged.putInt("rSSI", bleSignal.getRssi());
                onBleSignalChanged.putInt("txPower", bleSignal.getTxPower());
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "onBleSignalChanged", "setMessageHandler", onBleSignalChanged);
            }

            @Override
            public void onDistanceChanged(Message message, Distance distance) {
                WritableMap onDistanceChanged = Arguments.createMap();
                onDistanceChanged.putString("namespace", message.getNamespace());
                onDistanceChanged.putString("type", message.getType());
                onDistanceChanged.putArray("content", HmsUtils.getInstance().convertByteArrToWa(message.getContent()));
                onDistanceChanged.putDouble("meters", distance.getMeters());
                onDistanceChanged.putInt("precision", distance.getPrecision());
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "onDistanceChanged", "setMessageHandler", onDistanceChanged);
            }

            @Override
            public void onFound(Message message) {
                WritableMap onFound = Arguments.createMap();
                onFound.putString("namespace", message.getNamespace());
                onFound.putString("type", message.getType());
                onFound.putArray("content", HmsUtils.getInstance().convertByteArrToWa(message.getContent()));
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "onFound", "setMessageHandler", onFound);
            }

            @Override
            public void onLost(Message message) {
                WritableMap onLost = Arguments.createMap();
                onLost.putString("namespace", message.getNamespace());
                onLost.putString("type", message.getType());
                onLost.putArray("content", HmsUtils.getInstance().convertByteArrToWa(message.getContent()));
                HmsUtils.getInstance().sendEvent(reactApplicationContext, "onLost", "setMessageHandler", onLost);
            }
        };
    }

    public ConnectCallback getConnectCallback() {
        if (connectCallback == null)
            setConnectCallback();
        return connectCallback;
    }

    public ScanEndpointCallback getScanEndpointCallback() {
        if (scanEndpointCallback == null)
            setScanEndpointCallback();
        return scanEndpointCallback;
    }

    public DataCallback getDataCallback() {
        if (dataCallback == null)
            setDataCallback();
        return dataCallback;
    }

    public PutCallback getPutCallback() {
        if (putCallback == null)
            setPutCallback();
        return putCallback;
    }

    public StatusCallback getStatusCallback() {
        if (statusCallback == null)
            setStatusCallback();
        return statusCallback;
    }

    public GetCallback getGetCallback() {
        if (getCallback == null)
            setGetCallback();
        return getCallback;
    }

    public MessageHandler getMessageHandler() {
        if (messageHandler == null)
            setMessageHandler();
        return messageHandler;
    }
}
