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

export declare enum HMSNearbyApplication {
    SUCCESS = 0,
    FAILURE = -1,
    POLICY_FAIL = 8200,
    STRING_PARAM_FAIL = 8201,
    ENDPOINT_ID_FAIL = 8202,
    BYTES_DATA_FAIL = 8203,
    WIFI_NOT_SUPPORT_SHARE = 8068,
    WIFI_MUST_BE_ENABLED = 8069,
    ANDROID_HMS_RESTRICTED = 8070
}

export declare enum HMSDiscovery {
    MESH = 1,
    P2P = 2,
    STAR = 3,
    POLICY = Policy,
    CONNECT_ON_DISCONNECTED = "connectOnDisconnected",
    CONNECT_ON_ESTABLISH = "connectOnEstablish",
    CONNECT_ON_RESULT = "connectOnResult",
    SCAN_ON_FOUND = "scanOnFound",
    SCAN_ON_LOST = "scanOnLost",
    DATA_ON_RECEIVED = "dataOnReceived",
    DATA_ON_TRANSFER_UPDATE = "dataOnTransferUpdate",
    CHANNEL_AUTO = 1,
    CHANNEL_HIGH_THROUGHPUT = 2,
    CHANNEL_INSTANCE = 3
}

export declare enum HMSTransfer {
    FILE = 1,
    BYTES = 2,
    STREAM = 3,
    MAX_SIZE_DATA = 32768,
    TRANSFER_STATE_SUCCESS = 1,
    TRANSFER_STATE_FAILURE = 2,
    TRANSFER_STATE_IN_PROGRESS = 3,
    TRANSFER_STATE_CANCELED = 4
}

export declare enum HMSMessage {
    MAX_CONTENT_SIZE = 65536,
    MAX_TYPE_LENGTH = 16,
    MESSAGE_NAMESPACE_RESERVED = "_reserved_namespace",
    MESSAGE_TYPE_EDDYSTONE_UID = "_eddystone_uid",
    MESSAGE_TYPE_IBEACON_ID = "_ibeacon_id",
    POLICY_FINDING_MODE_DEFAULT = 0,
    POLICY_FINDING_MODE_BROADCAST = 1,
    POLICY_FINDING_MODE_SCAN = 2,
    POLICY_DISTANCE_TYPE_DEFAULT = 0,
    POLICY_DISTANCE_TYPE_EARSHOT = 1,
    POLICY_TTL_SECONDS_DEFAULT = 240,
    POLICY_TTL_SECONDS_INFINITE = 2147483647,
    POLICY_TTL_SECONDS_MAX = 86400,
    BLE_UNKNOWN_TX_POWER = -2147483648,
    PRECISION_LOW = 1,
    GET_ON_TIMEOUT = "getOnTimeOut",
    PUT_ON_TIMEOUT = "putOnTimeOut",
    STATUS_ON_CHANGED = "statusOnChanged",
    BLE_ON_SIGNAL_CHANGED = "onBleSignalChanged",
    DISTANCE_ON_CHANGED = "onDistanceChanged",
    MESSAGE_ON_FOUND = "messageOnFound",
    MESSAGE_ON_LOST = "messageOnLost" 
}

export declare enum HMSWifiShare {
    SHARE = 1,
    SET = 2,
    WIFI_ON_FOUND = "wifiOnFound",
    WIFI_ON_LOST = "wifiOnLost",
    WIFI_ON_RESULT = "wifiOnResult",
    WIFI_ON_FETCH_AUTH_CODE = "wifiOnFetchAuthCode"
}

interface ConnectOption {
    policy: HMSDiscovery
}

interface MessageConfiguration {
    type: HMSMessage,
    namespace: HMSMessage
}

interface PutOptionConfiguration {
    policy: PolicyConfiguration,
    setCallback: boolean
}

interface GetOptionConfiguration {
    policy: PolicyConfiguration,
    picker: PickerConfiguration,
    setCallback: boolean
}

interface PolicyConfiguration {
    findingMode: number,
    distanceType: number,
    ttlSeconds: number
}

interface PickerConfiguration {
    includeAllTypes: boolean,
    includeEddyStoneUids: IncludeEddyStoneUidConfiguration,
    includeIBeaconIds: IncludeIBeaconIdsConfiguration,
    picker: PickerConfiguration,
    includeNamespaceType: NamespaceTypeConfiguration
}

interface IncludeEddyStoneUidConfiguration {
    hexNamespace: string,
    hexInstance: string
}

interface IncludeIBeaconIdsConfiguration {
    iBeaconUuid: string,
    major: string,
    minor: string
}

interface NamespaceTypeConfiguration {
    namespace: string,
    type: string
}

export const HMSNearbyApplication = {
    enableLogger(): Promise<Object>;,
    disableLogger(): Promise<Object>;,
    setApiKey(apiKey: string): Promise<Object>;,
    getApiKey(): Promise<Object>;,
    getVersion(): Promise<Object>
}

export const HMSDiscovery = {
    acceptConnect(endpointId: string): Promise<Object>;,
    disconnect(endpointId: string): Promise<Object>;,
    rejectConnect(endpointId: string): Promise<Object>;,
    requestConnect(name: string, endpointId: string): Promise<Object>;,
    requestConnectEx(name: string, endpointId: string, connectOption: ConnectOption): Promise<Object>;,
    startBroadcasting(name: string, serviceId: string, policy: HMSDiscovery): Promise<Object>;,
    startScan(serviceId: string, policy: HMSDiscovery): Promise<Object>;,
    stopBroadCasting(): Promise<Object>;,
    disconnectAll(): Promise<Object>;,
    stopScan(): Promise<Object>
}

export const HMSTransfer = {
    transferBytes(bytes: number[], endpointIds: string[]): Promise<Object>;,
    transferFile(uri: string, endpointIds: string[]): Promise<Object>;,
    transferStream(endpoint: string, endpointIds: string[]): Promise<Object>;,
    cancelDataTransfer(id: string): Promise<Object>
}

export const HMSMessage = {
    put(messageConfig: MessageConfiguration, bytes: number[]): Promise<Object>;,
    putWithOption(messageConfig: MessageConfiguration, bytes: number[], putOptionConfiguration: PutOptionConfiguration): Promise<Object>;,
    registerStatusCallback(): Promise<Object>;,
    unRegisterStatusCallback(): Promise<Object>;,
    getMessage(): Promise<Object>;,
    getMessageWithOption(getOptionConfiguration: GetOptionConfiguration): Promise<Object>;,
    getMessagePending(): Promise<Object>;,
    getMessagePendingWithOption(getOptionConfiguration: GetOptionConfiguration): Promise<Object>;,
    unput(messageConfig: MessageConfiguration, bytes: number[]): Promise<Object>;,
    unget(): Promise<Object>;,
    ungetPending(): Promise<Object>
}

export const HMSWifiShare = {
    startWifiShare(policy: HMSDiscovery): Promise<Object>;,
    stopWifiShare(): Promise<Object>;,
    shareWifiConfig(endpointId: string): Promise<Object>
}
