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

package com.huawei.hms.rn.nearby.constants;

import com.facebook.common.internal.ImmutableMap;

import com.huawei.hms.nearby.discovery.BleSignal;
import com.huawei.hms.nearby.discovery.Distance;
import com.huawei.hms.nearby.message.Message;
import com.huawei.hms.nearby.message.Policy;
import com.huawei.hms.nearby.transfer.Data;
import com.huawei.hms.nearby.transfer.TransferEngine;
import com.huawei.hms.nearby.transfer.TransferStateUpdate;
import com.huawei.hms.rn.nearby.modules.HMSNearbyApplication;

import java.util.HashMap;
import java.util.Map;

import static com.huawei.hms.rn.nearby.utils.HMSResult.ANDROID_HMS_RESTRICTED;
import static com.huawei.hms.rn.nearby.utils.HMSResult.BYTES_DATA_FAIL;
import static com.huawei.hms.rn.nearby.utils.HMSResult.ENDPOINT_ID_FAIL;
import static com.huawei.hms.rn.nearby.utils.HMSResult.FAILURE;
import static com.huawei.hms.rn.nearby.utils.HMSResult.POLICY_FAIL;
import static com.huawei.hms.rn.nearby.utils.HMSResult.STRING_PARAM_FAIL;
import static com.huawei.hms.rn.nearby.utils.HMSResult.SUCCESS;
import static com.huawei.hms.rn.nearby.utils.HMSResult.WIFI_MUST_BE_ENABLED;
import static com.huawei.hms.rn.nearby.utils.HMSResult.WIFI_NOT_SUPPORT_SHARE;

public final class HMSConstants {
    /**
     * Policy Constants for general use
     */
    public static final int POLICY_MESH = 1;

    public static final int POLICY_P2P = 2;

    public static final int POLICY_STAR = 3;

    public static final int POLICY_SHARE = 1;

    public static final int POLICY_SET = 2;

    public static final int CHANNEL_AUTO = 1;

    public static final int CHANNEL_HIGH_THROUGHPUT = 2;

    public static final int CHANNEL_INSTANCE = 3;

    /**
     * Discovery events
     * {@link com.huawei.hms.rn.nearby.modules.HMSDiscovery}
     */
    public static final String CONNECT_CALLBACK = "ConnectCallback";

    public static final String CONNECT_ON_ESTABLISH = "connectOnEstablish";

    public static final String CONNECT_ON_RESULT = "connectOnResult";

    public static final String CONNECT_ON_DISCONNECTED = "connectOnDisconnected";

    public static final String SCAN_CALLBACK = "ScanEndpointCallback";

    public static final String SCAN_ON_FOUND = "scanOnFound";

    public static final String SCAN_ON_LOST = "scanOnLost";

    public static final String DATA_CALLBACK = "DataCallback";

    public static final String DATA_ON_RECEIVED = "dataOnReceived";

    public static final String DATA_ON_TRANSFER_UPDATE = "dataOnTransferUpdate";

    /**
     * Message Events
     * {@link com.huawei.hms.rn.nearby.modules.HMSMessage}
     */
    public static final String PUT_ON_TIMEOUT = "putOnTimeOut";

    public static final String GET_ON_TIMEOUT = "getOnTimeOut";

    public static final String PUT_CALLBACK = "PutCallback";

    public static final String GET_CALLBACK = "GetCallback";

    public static final String STATUS_CALLBACK = "StatusCallback";

    public static final String STATUS_ON_CHANGED = "statusOnChanged";

    public static final String MESSAGE_HANDLER = "MessageHandler";

    public static final String BLE_ON_SIGNAL_CHANGED = "onBleSignalChanged";

    public static final String DISTANCE_ON_CHANGED = "onDistanceChanged";

    public static final String MESSAGE_ON_FOUND = "messageOnFound";

    public static final String MESSAGE_ON_LOST = "messageOnLost";

    /**
     * Wifi Share Events
     * {@link com.huawei.hms.rn.nearby.modules.HMSWifiShare}
     */
    public static final String WIFI_CALLBACK = "WifiShareCallback";

    public static final String WIFI_ON_FOUND = "wifiSOnFound";

    public static final String WIFI_ON_LOST = "wifiOnLost";

    public static final String WIFI_ON_FETCH_AUTH_CODE = "wifiOnFetchAuthCode";

    public static final String WIFI_ON_RESULT = "wifiOnResult";

    /**
     * {@link com.huawei.hms.rn.nearby.modules.HMSDiscovery} module constant values exposed to RN side.
     */
    public static final Map<String, Object> DISCOVERY_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("MESH", POLICY_MESH);
            put("P2P", POLICY_P2P);
            put("STAR", POLICY_STAR);
            put("CONNECT_ON_DISCONNECTED", CONNECT_ON_DISCONNECTED);
            put("CONNECT_ON_ESTABLISH", CONNECT_ON_ESTABLISH);
            put("CONNECT_ON_RESULT", CONNECT_ON_RESULT);
            put("SCAN_ON_FOUND", SCAN_ON_FOUND);
            put("SCAN_ON_LOST", SCAN_ON_LOST);
            put("DATA_ON_RECEIVED", DATA_ON_RECEIVED);
            put("DATA_ON_TRANSFER_UPDATE", DATA_ON_TRANSFER_UPDATE);
            put("CHANNEL_AUTO", CHANNEL_AUTO);
            put("CHANNEL_HIGH_THROUGHPUT", CHANNEL_HIGH_THROUGHPUT);
            put("CHANNEL_INSTANCE", CHANNEL_INSTANCE);
        }
    });

    /**
     * {@link com.huawei.hms.rn.nearby.modules.HMSTransfer} module constant values exposed to RN side.
     */
    public static final Map<String, Object> TRANSFER_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("FILE", Data.Type.FILE);
            put("BYTES", Data.Type.BYTES);
            put("STREAM", Data.Type.STREAM);
            put("MAX_SIZE_DATA", TransferEngine.MAX_SIZE_DATA);
            put("TRANSFER_STATE_SUCCESS", TransferStateUpdate.Status.TRANSFER_STATE_SUCCESS);
            put("TRANSFER_STATE_FAILURE", TransferStateUpdate.Status.TRANSFER_STATE_FAILURE);
            put("TRANSFER_STATE_IN_PROGRESS", TransferStateUpdate.Status.TRANSFER_STATE_IN_PROGRESS);
            put("TRANSFER_STATE_CANCELED", TransferStateUpdate.Status.TRANSFER_STATE_CANCELED);
        }
    });

    /**
     * {@link com.huawei.hms.rn.nearby.modules.HMSMessage} module constant values exposed to RN side.
     */
    public static final Map<String, Object> MESSAGE_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("MAX_CONTENT_SIZE", Message.MAX_CONTENT_SIZE);
            put("MAX_TYPE_LENGTH", Message.MAX_TYPE_LENGTH);
            put("MESSAGE_NAMESPACE_RESERVED", Message.MESSAGE_NAMESPACE_RESERVED);
            put("MESSAGE_TYPE_EDDYSTONE_UID", Message.MESSAGE_TYPE_EDDYSTONE_UID);
            put("MESSAGE_TYPE_IBEACON_ID", Message.MESSAGE_TYPE_IBEACON_ID);
            put("POLICY_FINDING_MODE_DEFAULT", Policy.POLICY_FINDING_MODE_DEFAULT);
            put("POLICY_FINDING_MODE_BROADCAST", Policy.POLICY_FINDING_MODE_BROADCAST);
            put("POLICY_FINDING_MODE_SCAN", Policy.POLICY_FINDING_MODE_SCAN);
            put("POLICY_DISTANCE_TYPE_DEFAULT", Policy.POLICY_DISTANCE_TYPE_DEFAULT);
            put("POLICY_DISTANCE_TYPE_EARSHOT", Policy.POLICY_DISTANCE_TYPE_EARSHOT);
            put("POLICY_TTL_SECONDS_DEFAULT", Policy.POLICY_TTL_SECONDS_DEFAULT);
            put("POLICY_TTL_SECONDS_INFINITE", Policy.POLICY_TTL_SECONDS_INFINITE);
            put("POLICY_TTL_SECONDS_MAX", Policy.POLICY_TTL_SECONDS_MAX);
            put("BLE_UNKNOWN_TX_POWER", BleSignal.BLE_UNKNOWN_TX_POWER);
            put("PRECISION_LOW", Distance.Precision.PRECISION_LOW);
            put("GET_ON_TIMEOUT", GET_ON_TIMEOUT);
            put("PUT_ON_TIMEOUT", PUT_ON_TIMEOUT);
            put("STATUS_ON_CHANGED", STATUS_ON_CHANGED);
            put("BLE_ON_SIGNAL_CHANGED", BLE_ON_SIGNAL_CHANGED);
            put("DISTANCE_ON_CHANGED", DISTANCE_ON_CHANGED);
            put("MESSAGE_ON_FOUND", MESSAGE_ON_FOUND);
            put("MESSAGE_ON_LOST", MESSAGE_ON_LOST);
        }
    });

    /**
     * {@link com.huawei.hms.rn.nearby.modules.HMSWifiShare} module constant values exposed to RN side.
     */
    public static final Map<String, Object> WIFI_SHARE_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("SHARE", POLICY_SHARE);
            put("SET", POLICY_SET);
            put("WIFI_ON_FOUND", WIFI_ON_FOUND);
            put("WIFI_ON_LOST", WIFI_ON_LOST);
            put("WIFI_ON_RESULT", WIFI_ON_RESULT);
            put("WIFI_ON_FETCH_AUTH_CODE", WIFI_ON_FETCH_AUTH_CODE);
        }
    });

    /**
     * {@link HMSNearbyApplication} module constant values exposed to RN side.
     */
    public static final Map<String, Object> APPLICATION_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("SUCCESS", SUCCESS.getStatusCode());
            put("FAILURE", FAILURE.getStatusCode());
            put("POLICY_FAIL", POLICY_FAIL.getStatusCode());
            put("STRING_PARAM_FAIL", STRING_PARAM_FAIL.getStatusCode());
            put("ENDPOINT_ID_FAIL", ENDPOINT_ID_FAIL.getStatusCode());
            put("BYTES_DATA_FAIL", BYTES_DATA_FAIL.getStatusCode());
            put("WIFI_NOT_SUPPORT_SHARE", WIFI_NOT_SUPPORT_SHARE.getStatusCode());
            put("WIFI_MUST_BE_ENABLED", WIFI_MUST_BE_ENABLED.getStatusCode());
            put("ANDROID_HMS_RESTRICTED", ANDROID_HMS_RESTRICTED.getStatusCode());
        }
    });

}
