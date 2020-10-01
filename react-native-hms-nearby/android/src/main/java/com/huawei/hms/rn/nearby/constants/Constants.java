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

package com.huawei.hms.rn.nearby.constants;

import com.facebook.common.internal.ImmutableMap;
import com.huawei.hms.nearby.discovery.BleSignal;
import com.huawei.hms.nearby.discovery.Distance;
import com.huawei.hms.nearby.discovery.Policy;
import com.huawei.hms.nearby.message.BeaconId;
import com.huawei.hms.nearby.message.Message;
import com.huawei.hms.nearby.message.Permission;
import com.huawei.hms.nearby.transfer.Data;
import com.huawei.hms.nearby.transfer.TransferEngine;
import com.huawei.hms.nearby.transfer.TransferStateUpdate;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    private static final Map<String, Object> DISCOVERY_CONSTANTS = new HashMap<String, Object>() {
        {
            put("MESH", Policy.MESH);
            put("P2P", Policy.P2P);
            put("STAR", Policy.STAR);
            put("BLE_UNKNOWN_TX_POWER", BleSignal.BLE_UNKNOWN_TX_POWER);
            put("PRECISION_LOW", Distance.Precision.PRECISION_LOW);
        }
    };

    private static final Map<String, Object> TRANSFER_CONSTANTS = new HashMap<String, Object>() {
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
    };

    private static final Map<String, Object> MESSAGE_CONSTANTS = new HashMap<String, Object>() {
        {
            put("BEACON_TYPE_IBEACON", BeaconId.BEACON_TYPE_IBEACON);
            put("BEACON_TYPE_EDDYSTONE_UID", BeaconId.BEACON_TYPE_EDDYSTONE_UID);
            put("IBEACON_ID_LENGTH", BeaconId.IBEACON_ID_LENGTH);
            put("EDDYSTONE_UID_LENGTH", BeaconId.EDDYSTONE_UID_LENGTH);
            put("EDDYSTONE_NAMESPACE_LENGTH", BeaconId.EDDYSTONE_NAMESPACE_LENGTH);
            put("EDDYSTONE_INSTANCE_LENGTH", BeaconId.EDDYSTONE_INSTANCE_LENGTH);
            put("MAX_CONTENT_SIZE", Message.MAX_CONTENT_SIZE);
            put("MAX_TYPE_LENGTH", Message.MAX_TYPE_LENGTH);
            put("MESSAGE_NAMESPACE_RESERVED", Message.MESSAGE_NAMESPACE_RESERVED);
            put("MESSAGE_TYPE_EDDYSTONE_UID", Message.MESSAGE_TYPE_EDDYSTONE_UID);
            put("MESSAGE_TYPE_IBEACON_ID", Message.MESSAGE_TYPE_IBEACON_ID);
            put("PERMISSION_NONE", Permission.PERMISSION_NONE);
            put("PERMISSION_DEFAULT", Permission.PERMISSION_DEFAULT);
            put("PERMISSION_BLE", Permission.PERMISSION_BLE);
            put("PERMISSION_BLUETOOTH", Permission.PERMISSION_BLUETOOTH);
            put("PERMISSION_MICROPHONE", Permission.PERMISSION_MICROPHONE);
            put("POLICY_FINDING_MODE_DEFAULT", com.huawei.hms.nearby.message.Policy.POLICY_FINDING_MODE_DEFAULT);
            put("POLICY_FINDING_MODE_BROADCAST", com.huawei.hms.nearby.message.Policy.POLICY_FINDING_MODE_BROADCAST);
            put("POLICY_FINDING_MODE_SCAN", com.huawei.hms.nearby.message.Policy.POLICY_FINDING_MODE_SCAN);
            put("POLICY_DISTANCE_TYPE_DEFAULT", com.huawei.hms.nearby.message.Policy.POLICY_DISTANCE_TYPE_DEFAULT);
            put("POLICY_DISTANCE_TYPE_EARSHOT", com.huawei.hms.nearby.message.Policy.POLICY_DISTANCE_TYPE_EARSHOT);
            put("POLICY_TTL_SECONDS_DEFAULT", com.huawei.hms.nearby.message.Policy.POLICY_TTL_SECONDS_DEFAULT);
            put("POLICY_TTL_SECONDS_INFINITE", com.huawei.hms.nearby.message.Policy.POLICY_TTL_SECONDS_INFINITE);
            put("POLICY_TTL_SECONDS_MAX", com.huawei.hms.nearby.message.Policy.POLICY_TTL_SECONDS_MAX);
        }
    };

    public static Map<String, Object> getDiscoveryConstants() {
        return ImmutableMap.copyOf(DISCOVERY_CONSTANTS);
    }

    public static Map<String, Object> getTransferConstants() {
        return ImmutableMap.copyOf(TRANSFER_CONSTANTS);
    }

    public static Map<String, Object> getMessageConstants() {
        return ImmutableMap.copyOf(MESSAGE_CONSTANTS);
    }
}
