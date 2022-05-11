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

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.nearby.Nearby;
import com.huawei.hms.nearby.discovery.BleSignal;
import com.huawei.hms.nearby.discovery.Distance;
import com.huawei.hms.nearby.message.GetCallback;
import com.huawei.hms.nearby.message.GetOption;
import com.huawei.hms.nearby.message.Message;
import com.huawei.hms.nearby.message.MessageHandler;
import com.huawei.hms.nearby.message.MessagePicker;
import com.huawei.hms.nearby.message.Policy;
import com.huawei.hms.nearby.message.PutCallback;
import com.huawei.hms.nearby.message.PutOption;
import com.huawei.hms.nearby.message.StatusCallback;
import com.huawei.hms.rn.nearby.utils.HMSUtils;

import java.util.UUID;

import static com.huawei.hms.nearby.discovery.Distance.DISTANCE_UNKNOWN;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.BLE_ON_SIGNAL_CHANGED;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.DISTANCE_ON_CHANGED;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.GET_CALLBACK;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.GET_ON_TIMEOUT;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.MESSAGE_CONSTANTS;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.MESSAGE_HANDLER;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.MESSAGE_ON_FOUND;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.MESSAGE_ON_LOST;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.PUT_CALLBACK;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.PUT_ON_TIMEOUT;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.STATUS_CALLBACK;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.STATUS_ON_CHANGED;
import static com.huawei.hms.rn.nearby.utils.HMSResult.BYTES_DATA_FAIL;

public class HMSMessage extends HMSBase {

    /**
     * Constructor that initializes message module
     *
     * @param context app context
     */
    public HMSMessage(ReactApplicationContext context) {
        super(context, HMSMessage.class.getSimpleName(), MESSAGE_CONSTANTS);
    }

    /**
     * Publishes a message and broadcasts a token for nearby devices to scan.
     *
     * @param messageConfig message configuration
     * @param bytes message content.  Value range is [-128, 127]
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void put(ReadableMap messageConfig, ReadableArray bytes, final Promise promise) {
        startMethodExecTimer("put");

        if (bytes.size() == 0 || bytes.size() >= Message.MAX_CONTENT_SIZE) {
            handleResult("put", BYTES_DATA_FAIL, promise);
            return;
        }

        handleResult("put", Nearby.getMessageEngine(getCurrentActivity()).put(buildMessage(messageConfig, bytes)), promise);
    }

    /**
     * Publishes a message and broadcasts a token for nearby devices to scan.
     * Message is published only to apps that use the same project ID and
     * have registered the message type with the cloud for subscription.
     * Builds putOption using {@link #buildPutOption}
     *
     * @param messageConfig message configuration
     * @param bytes message content. Value range is [-128, 127]
     * @param putOptionConfiguration put option
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void putWithOption(ReadableMap messageConfig, ReadableArray bytes, ReadableMap putOptionConfiguration,
        final Promise promise) {
        startMethodExecTimer("putWithOption");

        if (bytes.size() == 0 || bytes.size() >= Message.MAX_CONTENT_SIZE) {
            handleResult("putWithOption", BYTES_DATA_FAIL, promise);
            return;
        }

        handleResult("putWithOption", Nearby.getMessageEngine(getCurrentActivity())
            .put(buildMessage(messageConfig, bytes), buildPutOption(putOptionConfiguration)), promise);
    }

    /**
     * Registers a status callback function {@link #getStatusCallback()}, which will notify your app of key events.
     * When your app calls one of the APIs for the first time, the function will return the status.
     * 
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void registerStatusCallback(final Promise promise) {
        startMethodExecTimer("registerStatusCallback");
        handleResult("registerStatusCallback",
            Nearby.getMessageEngine(getCurrentActivity()).registerStatusCallback(getStatusCallback()), promise);
    }

    /**
     * Cancels the status callback registered before {@link #getStatusCallback()}.
     * 
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void unRegisterStatusCallback(final Promise promise) {
        startMethodExecTimer("unRegisterStatusCallback");
        handleResult("unRegisterStatusCallback",
            Nearby.getMessageEngine(getCurrentActivity()).unregisterStatusCallback(getStatusCallback()), promise);
    }

    /**
     * Obtains messages from the cloud using the default option
     * 
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getMessage(final Promise promise) {
        startMethodExecTimer("getMessage");
        handleResult("getMessage", Nearby.getMessageEngine(getCurrentActivity()).get(getMessageHandler()), promise);
    }

    /**
     * Registers the messages to be obtained with the cloud.
     * Only messages with the same project ID can be obtained.
     * Builds getOption using {@link #buildGetOption}
     *
     * @param getOptionConfiguration configuration for messages obtaining
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getMessageWithOption(ReadableMap getOptionConfiguration, final Promise promise) {
        startMethodExecTimer("getMessageWithOption");
        handleResult("getMessageWithOption",
            Nearby.getMessageEngine(getCurrentActivity()).get(getMessageHandler(), buildGetOption(getOptionConfiguration)),
            promise);
    }

    /**
     * Identifies only BLE beacon messages.
     * It subscribes to messages published by nearby devices in a persistent and low-power manner and uses the default configuration.
     * Scanning is going on no matter whether your app runs in the background or foreground.
     * The scanning stops when the app process is killed.
     * Uses {@link BackgroundMessageService}
     * 
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getMessagePending(final Promise promise) {
        startMethodExecTimer("getMessagePending");
        BackgroundMessageService.initHandler(getMessageHandler());
        PendingIntent pendingIntent = PendingIntent.getService(getContext(), 0,
            new Intent(getContext(), BackgroundMessageService.class), PendingIntent.FLAG_UPDATE_CURRENT);
        handleResult("getMessagePending", Nearby.getMessageEngine(getCurrentActivity()).get(pendingIntent), promise);
    }

    /**
     * Identifies only BLE beacon messages.
     * Scanning is going on no matter whether your app runs in the background or foreground.
     * The scanning stops when the app process is killed.
     * Uses {@link BackgroundMessageService}
     *
     * @param getOptionConfiguration configuration parameter for background message obtaining
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getMessagePendingWithOption(ReadableMap getOptionConfiguration, final Promise promise) {
        startMethodExecTimer("getMessagePendingWithOption");
        BackgroundMessageService.initHandler(getMessageHandler());
        PendingIntent pendingIntent = PendingIntent.getService(getContext(), 0,
            new Intent(getContext(), BackgroundMessageService.class), PendingIntent.FLAG_UPDATE_CURRENT);
        handleResult("getMessagePendingWithOption",
            Nearby.getMessageEngine(getCurrentActivity()).get(pendingIntent, buildGetOption(getOptionConfiguration)), promise);
    }

    /**
     * Cancels message publishing.
     *
     * @param messageConfig message configuration
     * @param bytes Published message content
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void unput(ReadableMap messageConfig, ReadableArray bytes, final Promise promise) {
        startMethodExecTimer("unput");

        if (bytes.size() == 0 || bytes.size() >= Message.MAX_CONTENT_SIZE) {
            handleResult("unput", BYTES_DATA_FAIL, promise);
            return;
        }

        handleResult("unput", Nearby.getMessageEngine(getCurrentActivity()).unput(buildMessage(messageConfig, bytes)), promise);
    }

    /**
     * Cancels a message subscription.
     * Uses current message handler {@link #getMessageHandler()} that is registered
     * 
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void unget(final Promise promise) {
        startMethodExecTimer("unget");
        handleResult("unget", Nearby.getMessageEngine(getCurrentActivity()).unget(getMessageHandler()), promise);
    }

    /**
     * Cancels the background message subscription.
     * 
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void ungetPending(final Promise promise) {
        startMethodExecTimer("ungetPending");
        BackgroundMessageService.initHandler(getMessageHandler());
        PendingIntent pendingIntent = PendingIntent.getService(getContext(), 0,
            new Intent(getContext(), BackgroundMessageService.class), PendingIntent.FLAG_UPDATE_CURRENT);
        handleResult("ungetPending", Nearby.getMessageEngine(getCurrentActivity()).unget(pendingIntent), promise);
    }

    /**
     * Builds a new Policy object
     *
     * @param readableMap policy configuration
     * @return Policy
     */
    private Policy buildPolicy(ReadableMap readableMap) {
        int findingMode = Policy.POLICY_FINDING_MODE_DEFAULT;
        int distanceType = Policy.POLICY_DISTANCE_TYPE_DEFAULT;
        int ttlSeconds = Policy.POLICY_TTL_SECONDS_DEFAULT;

        if (readableMap == null) {
            Log.i(getName(), "Policy is created with default options");
            return Policy.DEFAULT;
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "findingMode", ReadableType.Number)) {
            findingMode = readableMap.getInt("findingMode");
            Log.i(getName(), "Policy findingMode option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "distanceType", ReadableType.Number)) {
            distanceType = readableMap.getInt("distanceType");
            Log.i(getName(), "Policy distanceType option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "ttlSeconds", ReadableType.Number)) {
            ttlSeconds = readableMap.getInt("ttlSeconds");
            Log.i(getName(), "Policy ttlSeconds option set");
        }

        return new Policy.Builder().setDistanceType(distanceType)
            .setFindingMode(findingMode)
            .setTtlSeconds(ttlSeconds)
            .build();
    }

    /**
     * Builds a new PutOption object
     *
     * @param readableMap put option configuration
     * @return PutOption
     */
    private PutOption buildPutOption(ReadableMap readableMap) {
        PutOption.Builder builder = new PutOption.Builder();

        if (readableMap == null) {
            Log.i(getName(), "PutOption is created with default options");
            return PutOption.DEFAULT;
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "policy", ReadableType.Map)) {
            builder.setPolicy(buildPolicy(readableMap.getMap("policy")));
            Log.i(getName(), "PutOption Policy option set");
        }
        if (HMSUtils.getInstance().boolKeyCheck(readableMap, "setCallback")) {
            builder.setCallback(getPutCallback());
            Log.i(getName(), "PutOption PutCallback option set");
        }

        Log.i(getName(), "PutOption build");
        return builder.build();
    }

    /**
     * Builds a new GetOption object
     *
     * @param readableMap get option configuration
     * @return GetOption
     */
    private GetOption buildGetOption(ReadableMap readableMap) {
        GetOption.Builder builder = new GetOption.Builder();

        if (readableMap == null) {
            Log.i(getName(), "GetOption is created with default options");
            return GetOption.DEFAULT;
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "policy", ReadableType.Map)) {
            builder.setPolicy(buildPolicy(readableMap.getMap("policy")));
            Log.i(getName(), "GetOption Policy is set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "picker", ReadableType.Map)) {
            builder.setPicker(buildMessagePicker(readableMap.getMap("picker")));
            Log.i(getName(), "GetOption MessagePicker is set");
        }
        if (HMSUtils.getInstance().boolKeyCheck(readableMap, "setCallback")) {
            builder.setCallback(getGetCallback());
            Log.i(getName(), "GetOption GetCallback is set");
        }

        Log.i(getName(), "GetOption build");
        return builder.build();
    }

    /**
     * Builds a new MessagePicker object
     *
     * @param readableMap message picker configuration
     * @return MessagePicker
     */
    private MessagePicker buildMessagePicker(ReadableMap readableMap) {
        MessagePicker.Builder builder = new MessagePicker.Builder();

        if (readableMap == null) {
            Log.i(getName(), "MessagePicker readableMap is null. Created with includeAllTypes");
            return MessagePicker.INCLUDE_ALL_TYPES;
        }
        if (HMSUtils.getInstance().boolKeyCheck(readableMap, "includeAllTypes")) {
            builder.includeAllTypes();
            Log.i(getName(), "MessagePicker includeAllTypes set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "includeEddyStoneUids", ReadableType.Array)) {
            ReadableArray includeEddyStoneUids = readableMap.getArray("includeEddyStoneUids");
            for (int i = 0; i < includeEddyStoneUids.size(); i++) {
                String hexNamespace = null;
                String hexInstance = null;
                ReadableMap tempMap = includeEddyStoneUids.getMap(i);
                if (HMSUtils.getInstance().hasValidKey(tempMap, "hexNamespace", ReadableType.String)) {
                    hexNamespace = tempMap.getString("hexNamespace");
                    Log.i(getName(), "MessagePicker hexNamespace set");
                }
                if (HMSUtils.getInstance().hasValidKey(tempMap, "hexInstance", ReadableType.String)) {
                    hexInstance = tempMap.getString("hexInstance");
                    Log.i(getName(), "MessagePicker hexInstance set");
                }
                if (!TextUtils.isEmpty(hexNamespace) && !TextUtils.isEmpty(hexInstance) && !hexNamespace.contains("*")
                    && !hexInstance.contains("*")) {
                    builder.includeEddystoneUids(hexNamespace, hexInstance);
                    Log.i(getName(), "MessagePicker includeEddyStoneUids set");
                } else {
                    Log.i(getName(), "MessagePicker includeEddyStoneUids setting rule not match");
                }
            }
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "picker", ReadableType.Map)) {
            builder.includePicker(buildMessagePicker(readableMap.getMap("picker")));
            Log.i(getName(), "MessagePicker picker set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "includeIBeaconIds", ReadableType.Array)) {
            ReadableArray includeIBeaconIds = readableMap.getArray("includeIBeaconIds");
            for (int i = 0; i < includeIBeaconIds.size(); i++) {
                ReadableMap tempMap = includeIBeaconIds.getMap(i);
                String iBeaconUuid = null;
                String major = null;
                String minor = null;
                if (HMSUtils.getInstance().hasValidKey(tempMap, "iBeaconUuid", ReadableType.String)) {
                    iBeaconUuid = tempMap.getString("iBeaconUuid");
                    Log.i(getName(), "MessagePicker iBeaconUuid set");
                }
                if (HMSUtils.getInstance().hasValidKey(tempMap, "major", ReadableType.String)) {
                    major = tempMap.getString("major");
                    Log.i(getName(), "MessagePicker major set");
                }
                if (HMSUtils.getInstance().hasValidKey(tempMap, "minor", ReadableType.String)) {
                    minor = tempMap.getString("minor");
                    Log.i(getName(), "MessagePicker minor set");
                }

                if (!TextUtils.isEmpty(iBeaconUuid) && !TextUtils.isEmpty(major) && !TextUtils.isEmpty(minor)) {
                    try {
                        builder.includeIBeaconIds(UUID.fromString(iBeaconUuid), Short.parseShort(major),
                            Short.parseShort(minor));
                        Log.i(getName(), "MessagePicker includeIBeaconIds set");
                    } catch (Exception e) {
                        Log.i(getName(),
                            "MessagePicker Exception happened when setting includeIBeaconIds" + e.getMessage());
                    }
                } else {
                    Log.i(getName(), "MessagePicker includeIBeaconIds is not set required params are not given");
                }
            }
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "includeNamespaceType", ReadableType.Array)) {
            ReadableArray includeNamespaceType = readableMap.getArray("includeNamespaceType");
            for (int i = 0; i < includeNamespaceType.size(); i++) {
                ReadableMap tempMap = includeNamespaceType.getMap(i);
                String namespace = null;
                String type = null;
                if (HMSUtils.getInstance().hasValidKey(tempMap, "namespace", ReadableType.String)) {
                    namespace = tempMap.getString("namespace");
                    Log.i(getName(), "MessagePicker namespace set");
                }
                if (HMSUtils.getInstance().hasValidKey(tempMap, "type", ReadableType.String)) {
                    type = tempMap.getString("type");
                    Log.i(getName(), "MessagePicker type set");
                }
                if (!TextUtils.isEmpty(namespace) && !TextUtils.isEmpty(type) && !namespace.contains("*")
                    && !type.contains("*")) {
                    builder.includeNamespaceType(namespace, type);
                    Log.i(getName(), "MessagePicker includeNamespaceType set");
                } else {
                    Log.i(getName(),
                        "MessagePicker includeNamespaceType namespace or type is empty or null or contains *");
                }
            }
        }

        return builder.build();
    }

    /**
     * Builds a new Message object
     *
     * @param readableMap message configuration
     * @param readableArray message content
     * @return Message
     */
    private Message buildMessage(ReadableMap readableMap, ReadableArray readableArray) {
        String type = Message.MESSAGE_TYPE_EDDYSTONE_UID;
        String nameSpace = Message.MESSAGE_NAMESPACE_RESERVED;

        if (readableMap == null) {
            Log.i(getName(), "Message readableMap is null.");
            return new Message(HMSUtils.getInstance().convertReadableArrayToByteArray(readableArray));
        }

        if (HMSUtils.getInstance().hasValidKey(readableMap, "type", ReadableType.String)) {
            type = readableMap.getString("type");
            Log.i(getName(), "Message type set.");
        }

        if (HMSUtils.getInstance().hasValidKey(readableMap, "namespace", ReadableType.String)) {
            nameSpace = readableMap.getString("namespace");
            Log.i(getName(), "Message nameSpace set.");
        }

        return new Message(HMSUtils.getInstance().convertReadableArrayToByteArray(readableArray), type, nameSpace);
    }

    /**
     * Creates or returns callback listener object for events related to message subscription.
     *
     * @return GetCallback
     */
    private GetCallback getGetCallback() {
        return new GetCallback() {
            @Override
            public void onTimeout() {
                WritableMap onTimeout = Arguments.createMap();
                onTimeout.putString("onTimeout", "Message subscription expired");
                sendEvent(GET_ON_TIMEOUT, GET_CALLBACK, onTimeout);
            }
        };
    }

    /**
     * Creates or returns callback listener object for events related to message publishing.
     *
     * @return PutCallback
     */
    private PutCallback getPutCallback() {
        return new PutCallback() {
            @Override
            public void onTimeout() {
                WritableMap onTimeout = Arguments.createMap();
                onTimeout.putString("onTimeout", "Message publishing expired");
                sendEvent(PUT_ON_TIMEOUT, PUT_CALLBACK, onTimeout);
            }
        };
    }

    /**
     * Creates or returns callback listener object for events related to MessageEngine status changes.
     *
     * @return StatusCallback
     */
    private StatusCallback getStatusCallback() {
        return new StatusCallback() {
            @Override
            public void onPermissionChanged(boolean grantPermission) {
                WritableMap onTimeout = Arguments.createMap();
                onTimeout.putBoolean("onPermissionChanged", grantPermission);
                sendEvent(STATUS_ON_CHANGED, STATUS_CALLBACK, onTimeout);
            }
        };
    }

    /**
     * Creates or returns callback listener object for events such as signal change, distance change,
     * message reception,and failure to receive a message.
     *
     * @return MessageHandler
     */
    private MessageHandler getMessageHandler() {
        return new MessageHandler() {
            @Override
            public void onBleSignalChanged(Message message, BleSignal bleSignal) {
                WritableMap onBleSignalChanged = Arguments.createMap();
                onBleSignalChanged.putString("namespace", message.getNamespace());
                onBleSignalChanged.putString("type", message.getType());
                onBleSignalChanged.putArray("content",
                    HMSUtils.getInstance().convertByteArrayToWritableArray(message.getContent()));
                onBleSignalChanged.putInt("rSSI", bleSignal.getRssi());
                onBleSignalChanged.putInt("txPower", bleSignal.getTxPower());
                sendEvent(BLE_ON_SIGNAL_CHANGED, MESSAGE_HANDLER, onBleSignalChanged);
            }

            @Override
            public void onDistanceChanged(Message message, Distance distance) {
                WritableMap onDistanceChanged = Arguments.createMap();
                onDistanceChanged.putString("namespace", message.getNamespace());
                onDistanceChanged.putString("type", message.getType());
                onDistanceChanged.putInt("isUnknown", DISTANCE_UNKNOWN.compareTo(distance));
                onDistanceChanged.putArray("content",
                    HMSUtils.getInstance().convertByteArrayToWritableArray(message.getContent()));
                onDistanceChanged.putDouble("meters",
                    Double.isNaN(distance.getMeters()) ? DISTANCE_UNKNOWN.getMeters() : distance.getMeters());
                onDistanceChanged.putInt("precision", distance.getPrecision());
                sendEvent(DISTANCE_ON_CHANGED, MESSAGE_HANDLER, onDistanceChanged);
            }

            @Override
            public void onFound(Message message) {
                WritableMap onFound = Arguments.createMap();
                onFound.putString("namespace", message.getNamespace());
                onFound.putString("type", message.getType());
                onFound.putArray("content",
                    HMSUtils.getInstance().convertByteArrayToWritableArray(message.getContent()));
                sendEvent(MESSAGE_ON_FOUND, MESSAGE_HANDLER, onFound);
            }

            @Override
            public void onLost(Message message) {
                WritableMap onLost = Arguments.createMap();
                onLost.putString("namespace", message.getNamespace());
                onLost.putString("type", message.getType());
                onLost.putArray("content",
                    HMSUtils.getInstance().convertByteArrayToWritableArray(message.getContent()));
                sendEvent(MESSAGE_ON_LOST, MESSAGE_HANDLER, onLost);
            }
        };
    }

    /**
     * This class provides identification of BLE beacon messages on background.
     * Scanning is going on no matter whether your app runs in the background or foreground.
     * The scanning stops when the app process is killed.
     */
    public static class BackgroundMessageService extends IntentService {
        private static final String SERVICE_NAME = BackgroundMessageService.class.getName();

        private static MessageHandler messageHandler;

        public BackgroundMessageService() {
            super(SERVICE_NAME);
        }

        public static void initHandler(MessageHandler handler) {
            messageHandler = handler;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            Log.i(SERVICE_NAME, "onCreate");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.i(SERVICE_NAME, "onDestroy");
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
            Log.i(SERVICE_NAME, "onHandleIntent");
            Nearby.getMessageEngine(getApplicationContext()).handleIntent(intent, messageHandler);
        }

    }

}
