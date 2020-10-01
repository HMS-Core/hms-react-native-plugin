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

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.nearby.Nearby;
import com.huawei.hms.nearby.NearbyApiContext;
import com.huawei.hms.nearby.message.BeaconId;
import com.huawei.hms.nearby.message.GetOption;
import com.huawei.hms.nearby.message.Message;
import com.huawei.hms.nearby.message.MessageHandler;
import com.huawei.hms.nearby.message.MessagePicker;
import com.huawei.hms.nearby.message.Policy;
import com.huawei.hms.nearby.message.PutOption;
import com.huawei.hms.rn.nearby.constants.Constants;
import com.huawei.hms.rn.nearby.utils.HmsHelper;
import com.huawei.hms.rn.nearby.utils.HmsLogger;
import com.huawei.hms.rn.nearby.utils.HmsUtils;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import static com.huawei.hms.rn.nearby.constants.Errors.E_MESSAGE;

public class HmsMessageModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsMessageModule.class.getSimpleName();
    private ReactApplicationContext mContext;
    private BeaconId beaconId;
    private PutOption putOption;
    private GetOption getOption;
    private Policy policy;
    private MessagePicker picker;

    HmsMessageModule(ReactApplicationContext context) {
        super(context);
        mContext = context;
    }

    /* HmsLogger start */
    @ReactMethod
    public void enableLogger(final Promise promise) {
        HmsLogger.getInstance(mContext).enableLogger();
        promise.resolve("HmsLogger enabled");
    }

    @ReactMethod
    public void disableLogger(final Promise promise) {
        HmsLogger.getInstance(mContext).disableLogger();
        promise.resolve("HmsLogger disabled");
    }
    /* HmsLogger end */

    /* start set api key */
    @ReactMethod
    public void setApiKey(String apiKey, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("setApiKey");

        if (apiKey == null || apiKey.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("setApiKey", "Api key is empty or null");
            promise.reject(E_MESSAGE, "Api key is empty or null");
            return;
        }

        NearbyApiContext.getInstance().setApiKey(apiKey);
        HmsLogger.getInstance(mContext).sendSingleEvent("setApiKey");
        promise.resolve("Api key set");
    }

    @ReactMethod
    public void getApiKey(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("getApiKey");
        String apiKey = NearbyApiContext.getInstance().getApiKey();

        if (apiKey == null || apiKey.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("getApiKey", "Api key is empty or null");
            promise.reject(E_MESSAGE, "Api key is empty or null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("getApiKey");
        promise.resolve(NearbyApiContext.getInstance().getApiKey());
    }
    /* end set api key */

    /*Start Beacon*/
    @ReactMethod
    public void createBeaconId(ReadableMap rm, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("createBeaconId");
        beaconId = buildBeacon(rm);
        HmsLogger.getInstance(mContext).sendSingleEvent("createBeaconId");
        promise.resolve("BeaconId set");
    }

    @ReactMethod
    public void beaconLength(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("beaconLength");

        if (beaconId == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("beaconLength", "beaconId is null");
            promise.reject(E_MESSAGE, "beaconId is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("beaconLength");
        promise.resolve(beaconId.getLength());
    }

    @ReactMethod
    public void beaconType(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("beaconType");

        if (beaconId == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("beaconType", "beaconId is null");
            promise.reject(E_MESSAGE, "beaconId is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("beaconType");
        promise.resolve(beaconId.getType());
    }

    @ReactMethod
    public void beaconEquals(ReadableMap rm, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("beaconEquals");

        if (beaconId == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("beaconEquals", "beaconId is null");
            promise.reject(E_MESSAGE, "beaconId is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("beaconEquals");
        promise.resolve(beaconId.equals(buildBeacon(rm)));
    }

    @ReactMethod
    public void beaconHashCode(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("beaconHashCode");

        if (beaconId == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("beaconHashCode", "beaconId is null");
            promise.reject(E_MESSAGE, "beaconId is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("beaconHashCode");
        promise.resolve(beaconId.hashCode());
    }

    @ReactMethod
    public void beaconToString(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("beaconToString");

        if (beaconId == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("beaconToString", "beaconId is null");
            promise.reject(E_MESSAGE, "beaconId is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("beaconToString");
        promise.resolve(beaconId.toString());
    }

    @ReactMethod
    public void beaconHex(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("beaconHex");

        if (beaconId == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("beaconHex", "beaconId is null");
            promise.reject(E_MESSAGE, "beaconId is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("beaconHex");
        promise.resolve(beaconId.getHex());
    }

    @ReactMethod
    public void beaconInstance(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("beaconInstance");

        if (beaconId == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("beaconInstance", "beaconId is null");
            promise.reject(E_MESSAGE, "beaconId is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("beaconInstance");
        promise.resolve(beaconId.getInstance());
    }

    @ReactMethod
    public void beaconNamespace(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("beaconNamespace");

        if (beaconId == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("beaconNamespace", "beaconId is null");
            promise.reject(E_MESSAGE, "beaconId is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("beaconNamespace");
        promise.resolve(beaconId.getNamespace());
    }

    @ReactMethod
    public void beaconMajor(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("beaconMajor");

        if (beaconId == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("beaconMajor", "beaconId is null");
            promise.reject(E_MESSAGE, "beaconId is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("beaconMajor");
        promise.resolve(String.valueOf(beaconId.getMajor()));
    }

    @ReactMethod
    public void beaconMinor(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("beaconMinor");

        if (beaconId == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("beaconMinor", "beaconId is null");
            promise.reject(E_MESSAGE, "beaconId is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("beaconMinor");
        promise.resolve(String.valueOf(beaconId.getMinor()));
    }

    @ReactMethod
    public void beaconUuid(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("beaconUuid");

        if (beaconId == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("beaconUuid", "beaconId is null");
            promise.reject(E_MESSAGE, "beaconId is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("beaconUuid");
        promise.resolve(beaconId.getIBeaconUuid().toString());
    }
    /*End Beacon*/

    /*Start PutOption*/
    @ReactMethod
    public void createPutOption(ReadableMap rm, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("createPutOption");
        putOption = buildPutOption(rm);
        HmsLogger.getInstance(mContext).sendSingleEvent("createPutOption");
        promise.resolve("PutOption set");
    }

    @ReactMethod
    public void getPolicyPutOption(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("getPolicyPutOption");

        if (putOption == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("getPolicyPutOption", "putOption is null");
            promise.reject(E_MESSAGE, "putOption is null");
            return;
        }

        WritableMap wm = Arguments.createMap();
        wm.putString("str", putOption.getPolicy().toString());
        wm.putInt("hashCode", putOption.getPolicy().hashCode());
        wm.putInt("backgroundScanMode", putOption.getPolicy().getBackgroundScanMode());
        wm.putInt("changeFindingMode", putOption.getPolicy().getChangeFindingMode());
        wm.putInt("distanceType", putOption.getPolicy().getDistanceType());
        wm.putInt("findingMedium", putOption.getPolicy().getFindingMedium());
        wm.putInt("ttlSeconds", putOption.getPolicy().getTtlSeconds());
        wm.putInt("findingMode", putOption.getPolicy().getFindingMode());
        HmsLogger.getInstance(mContext).sendSingleEvent("getPolicyPutOption");
        promise.resolve(wm);
    }
    /*End PutOption*/

    /*Start GetOption*/
    @ReactMethod
    public void createGetOption(ReadableMap rm, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("createGetOption");
        getOption = buildGetOption(rm);
        HmsLogger.getInstance(mContext).sendSingleEvent("createGetOption");
        promise.resolve("GetOption set");
    }
    /*End GetOption*/

    /*Start Policy*/
    @ReactMethod
    public void createPolicy(ReadableMap rm, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("createPolicy");
        policy = buildPolicy(rm);
        HmsLogger.getInstance(mContext).sendSingleEvent("createPolicy");
        promise.resolve("Policy set");
    }

    @ReactMethod
    public void policyEquals(ReadableMap rm, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("policyEquals");

        if (policy == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("policyEquals", "Policy is null");
            promise.reject(E_MESSAGE, "Policy is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("policyEquals");
        promise.resolve(policy.equals(buildPolicy(rm)));
    }

    @ReactMethod
    public void policyHashCode(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("policyHashCode");

        if (policy == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("policyHashCode", "Policy is null");
            promise.reject(E_MESSAGE, "Policy is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("policyHashCode");
        promise.resolve(policy.hashCode());
    }

    @ReactMethod
    public void policyToString(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("policyToString");

        if (policy == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("policyToString", "Policy is null");
            promise.reject(E_MESSAGE, "Policy is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("policyToString");
        promise.resolve(policy.toString());
    }
    /*End Policy*/

    /*Start MessagePicker*/
    @ReactMethod
    public void createMessagePicker(ReadableMap rm, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("createMessagePicker");
        picker = buildMessagePicker(rm);
        HmsLogger.getInstance(mContext).sendSingleEvent("createMessagePicker");
        promise.resolve("MessagePicker set");
    }

    @ReactMethod
    public void messagePickerEquals(ReadableMap rm, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("messagePickerEquals");

        if (picker == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("messagePickerEquals", "messagePicker is null");
            promise.reject(E_MESSAGE, "messagePicker is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("messagePickerEquals");
        promise.resolve(picker.equals(buildMessagePicker(rm)));
    }

    @ReactMethod
    public void messagePickerHashCode(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("messagePickerHashCode");

        if (picker == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("messagePickerHashCode", "messagePicker is null");
            promise.reject(E_MESSAGE, "messagePicker is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("messagePickerHashCode");
        promise.resolve(picker.hashCode());
    }

    @ReactMethod
    public void messagePickerToString(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("messagePickerToString");

        if (picker == null) {
            HmsLogger.getInstance(mContext).sendSingleEvent("messagePickerToString", "messagePicker is null");
            promise.reject(E_MESSAGE, "messagePicker is null");
            return;
        }

        HmsLogger.getInstance(mContext).sendSingleEvent("messagePickerToString");
        promise.resolve(picker.toString());
    }
    /*End MessagePicker*/

    /*Start MessageEngine*/
    @ReactMethod
    public void put(ReadableArray bytes, boolean isOption, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("put");

        if (bytes.size() == 0) {
            HmsLogger.getInstance(mContext).sendSingleEvent("put", "given byte array is empty");
            promise.reject(E_MESSAGE, "given byte array is empty");
            return;
        }

        if (isOption) {
            if (putOption == null) {
                HmsLogger.getInstance(mContext).sendSingleEvent("put", "Put Option is null");
                promise.reject(E_MESSAGE, "Put Option is null");
                return;
            }

            Nearby.getMessageEngine(getCurrentActivity())
                    .put(new Message(HmsUtils.getInstance().convertRaToByteArray(bytes)), putOption)
                    .addOnSuccessListener(aVoid -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("put");
                        promise.resolve("Message put");
                    })
                    .addOnFailureListener(e -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("put", e.getMessage());
                        promise.reject(E_MESSAGE, e.getMessage());
                    });
        } else {
            Nearby.getMessageEngine(getCurrentActivity())
                    .put(new Message(HmsUtils.getInstance().convertRaToByteArray(bytes)))
                    .addOnSuccessListener(aVoid -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("put");
                        promise.resolve("Message put");
                    })
                    .addOnFailureListener(e -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("put", e.getMessage());
                        promise.reject(E_MESSAGE, e.getMessage());
                    });
        }
    }

    @ReactMethod
    public void registerStatusCallback(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("registerStatusCallback");

        Nearby.getMessageEngine(getCurrentActivity())
                .registerStatusCallback(HmsHelper.getInstance(mContext).getStatusCallback())
                .addOnSuccessListener(aVoid -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("registerStatusCallback");
                    promise.resolve("StatusCallback registered");
                })
                .addOnFailureListener(e -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("registerStatusCallback", e.getMessage());
                    promise.reject(E_MESSAGE, e.getMessage());
                });
    }

    @ReactMethod
    public void unRegisterStatusCallback(final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("unRegisterStatusCallback");

        Nearby.getMessageEngine(getCurrentActivity())
                .unregisterStatusCallback(HmsHelper.getInstance(mContext).getStatusCallback())
                .addOnSuccessListener(aVoid -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("unRegisterStatusCallback");
                    promise.resolve("StatusCallback unregistered");
                })
                .addOnFailureListener(e -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("unRegisterStatusCallback", e.getMessage());
                    promise.reject(E_MESSAGE, e.getMessage());
                });
    }

    @ReactMethod
    public void getMessage(boolean isOption, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("getMessage");

        if (isOption) {
            if (getOption == null) {
                HmsLogger.getInstance(mContext).sendSingleEvent("getMessage", "Option is null");
                promise.reject(E_MESSAGE, "Option is null");
                return;
            }

            Nearby.getMessageEngine(getCurrentActivity())
                    .get(HmsHelper.getInstance(mContext).getMessageHandler(), getOption)
                    .addOnSuccessListener(aVoid -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("getMessage");
                        promise.resolve("get message success");
                    })
                    .addOnFailureListener(e -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("getMessage", e.getMessage());
                        promise.reject(E_MESSAGE, e.getMessage());
                    });
        } else {
            Nearby.getMessageEngine(getCurrentActivity())
                    .get(HmsHelper.getInstance(mContext).getMessageHandler())
                    .addOnSuccessListener(aVoid -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("getMessage");
                        promise.resolve("get message success");
                    })
                    .addOnFailureListener(e -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("getMessage", e.getMessage());
                        promise.reject(E_MESSAGE, e.getMessage());
                    });
        }
    }

    @ReactMethod
    public void getPending(boolean isOption, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("getPending");
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, new Intent(mContext, BackgroundIntentService.class), PendingIntent.FLAG_UPDATE_CURRENT);

        if (isOption) {
            if (getOption == null) {
                HmsLogger.getInstance(mContext).sendSingleEvent("getPending", "Option is null");
                promise.reject(E_MESSAGE, "Option is null");
                return;
            }

            Nearby.getMessageEngine(getCurrentActivity())
                    .get(pendingIntent, getOption)
                    .addOnSuccessListener(aVoid -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("getPending");
                        promise.resolve("Get success");
                    })
                    .addOnFailureListener(e -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("getPending", e.getMessage());
                        promise.reject(E_MESSAGE, e.getMessage());
                    });
        } else {
            Nearby.getMessageEngine(getCurrentActivity())
                    .get(pendingIntent)
                    .addOnSuccessListener(aVoid -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("getPending");
                        promise.resolve("Get success");
                    })
                    .addOnFailureListener(e -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("getPending", e.getMessage());
                        promise.reject(E_MESSAGE, e.getMessage());
                    });
        }
    }

    @ReactMethod
    public void unput(ReadableArray bytes, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("unput");

        if (bytes.size() == 0) {
            HmsLogger.getInstance(mContext).sendSingleEvent("unput", "given byte array is empty");
            promise.reject(E_MESSAGE, "given byte array is empty");
            return;
        }

        Nearby.getMessageEngine(getCurrentActivity())
                .unput(new Message(HmsUtils.getInstance().convertRaToByteArray(bytes)))
                .addOnSuccessListener(aVoid -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("unput");
                    promise.resolve("Unput success");
                })
                .addOnFailureListener(e -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("unput", e.getMessage());
                    promise.reject(E_MESSAGE, e.getMessage());
                });
    }

    @ReactMethod
    public void unget(boolean isCurrent, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("unget");

        if (isCurrent) {
            PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, new Intent(mContext, BackgroundIntentService.class), PendingIntent.FLAG_UPDATE_CURRENT);

            Nearby.getMessageEngine(getCurrentActivity())
                    .unget(pendingIntent)
                    .addOnSuccessListener(aVoid -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("unget");
                        promise.resolve("Unget Success");
                    })
                    .addOnFailureListener(e -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("unget", e.getMessage());
                        promise.reject(E_MESSAGE, e.getMessage());
                    });
        } else {
            Nearby.getMessageEngine(getCurrentActivity())
                    .unget(HmsHelper.getInstance(mContext).getMessageHandler())
                    .addOnSuccessListener(aVoid -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("unget");
                        promise.resolve("Unget Success");
                    })
                    .addOnFailureListener(e -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("unget", e.getMessage());
                        promise.reject(E_MESSAGE, e.getMessage());
                    });
        }
    }
    /*End MessageEngine*/

    /*Start Builder Methods*/
    private Policy buildPolicy(ReadableMap readableMap) {
        int findingMode = Policy.POLICY_FINDING_MODE_DEFAULT;
        int distanceType = Policy.POLICY_DISTANCE_TYPE_DEFAULT;
        int ttlSeconds = Policy.POLICY_TTL_SECONDS_DEFAULT;

        if (readableMap == null) {
            return new Policy.Builder().build();
        }
        if (HmsUtils.getInstance().hasValidKey(readableMap, "findingMode", ReadableType.Number)) {
            findingMode = readableMap.getInt("findingMode");
        }
        if (HmsUtils.getInstance().hasValidKey(readableMap, "distanceType", ReadableType.Number)) {
            distanceType = readableMap.getInt("distanceType");
        }
        if (HmsUtils.getInstance().hasValidKey(readableMap, "ttlSeconds", ReadableType.Number)) {
            ttlSeconds = readableMap.getInt("ttlSeconds");
        }

        return new Policy.Builder()
                .setDistanceType(distanceType)
                .setFindingMode(findingMode)
                .setTtlSeconds(ttlSeconds)
                .build();
    }

    private PutOption buildPutOption(ReadableMap readableMap) {
        PutOption.Builder builder = new PutOption.Builder();

        if (readableMap == null) {
            return builder.build();
        }
        if (HmsUtils.getInstance().boolKeyCheck(readableMap, "setPolicy")) {
            builder.setPolicy(policy);
        }
        if (HmsUtils.getInstance().boolKeyCheck(readableMap, "setCallback")) {
            builder.setCallback(HmsHelper.getInstance(mContext).getPutCallback());
        }

        return builder.build();
    }

    private GetOption buildGetOption(ReadableMap readableMap) {
        GetOption.Builder builder = new GetOption.Builder();

        if (readableMap == null) {
            return GetOption.DEFAULT;
        }
        if (HmsUtils.getInstance().boolKeyCheck(readableMap, "setPolicy") && policy != null) {
            builder.setPolicy(policy);
        }
        if (HmsUtils.getInstance().boolKeyCheck(readableMap, "setPicker") && picker != null) {
            builder.setPicker(picker);
        }
        if (HmsUtils.getInstance().boolKeyCheck(readableMap, "setCallback")) {
            builder.setCallback(HmsHelper.getInstance(mContext).getGetCallback());
        }

        return builder.build();
    }

    private BeaconId buildBeacon(ReadableMap readableMap) {
        BeaconId.Builder builder = new BeaconId.Builder();

        if (readableMap == null) {
            return builder.build();
        }
        if (HmsUtils.getInstance().hasValidKey(readableMap, "uuid", ReadableType.String)) {
            builder.setIBeaconUuid(UUID.fromString(readableMap.getString("uuid")));
        }
        if (HmsUtils.getInstance().hasValidKey(readableMap, "major", ReadableType.String)) {
            builder.setMajor(Short.parseShort(readableMap.getString("major")));
        }
        if (HmsUtils.getInstance().hasValidKey(readableMap, "minor", ReadableType.String)) {
            builder.setMinor(Short.parseShort(readableMap.getString("minor")));
        }
        if (HmsUtils.getInstance().hasValidKey(readableMap, "hexId", ReadableType.String)) {
            builder.setHexId(readableMap.getString("hexId"));
        }
        if (HmsUtils.getInstance().hasValidKey(readableMap, "namespace", ReadableType.String)) {
            builder.setHexNamespace(readableMap.getString("namespace"));
        }
        if (HmsUtils.getInstance().hasValidKey(readableMap, "hexInstance", ReadableType.String)) {
            builder.setHexInstance(readableMap.getString("hexInstance"));
        }
        return builder.build();
    }

    private MessagePicker buildMessagePicker(ReadableMap readableMap) {
        MessagePicker.Builder builder = new MessagePicker.Builder();

        if (readableMap == null) {
            return builder.includeAllTypes().build();
        }
        if (HmsUtils.getInstance().boolKeyCheck(readableMap, "includeAllTypes")) {
            builder.includeAllTypes();
        }
        if (HmsUtils.getInstance().boolKeyCheck(readableMap, "includeEddyStoneUids") && HmsUtils.getInstance().hasValidKey(readableMap, "hexNameSpace", ReadableType.String) && HmsUtils.getInstance().hasValidKey(readableMap, "hexInstance", ReadableType.String)) {
            builder.includeEddystoneUids(readableMap.getString("hexNameSpace"), readableMap.getString("hexInstance"));
        }
        if (HmsUtils.getInstance().boolKeyCheck(readableMap, "includePicker")) {
            builder.includePicker(picker);
        }
        if (HmsUtils.getInstance().boolKeyCheck(readableMap, "includeBeaconIds") && HmsUtils.getInstance().hasValidKey(readableMap, "proximityUuid", ReadableType.String) && HmsUtils.getInstance().hasValidKey(readableMap, "major", ReadableType.String) && HmsUtils.getInstance().hasValidKey(readableMap, "minor", ReadableType.String)) {
            builder.includeIBeaconIds(
                    UUID.fromString(readableMap.getString("proximityUuid")),
                    Short.parseShort(readableMap.getString("major")),
                    Short.parseShort(readableMap.getString("minor"))
            );
        }
        if (HmsUtils.getInstance().boolKeyCheck(readableMap, "includeNameSpaceType") && HmsUtils.getInstance().hasValidKey(readableMap, "namespace", ReadableType.String) && HmsUtils.getInstance().hasValidKey(readableMap, "type", ReadableType.String)) {
            builder.includeNamespaceType(
                    readableMap.getString("namespace"),
                    readableMap.getString("type")
            );
        }
        return builder.build();
    }
    /*End Builder Methods*/

    public class BackgroundIntentService extends IntentService {

        public BackgroundIntentService() {
            super("BackgroundService");
        }

        protected void onHandleIntent(Intent intent) {
            Nearby.getMessageEngine(getCurrentActivity()).handleIntent(intent, new MessageHandler() {
                @Override
                public void onFound(Message message) {
                    WritableMap onFound = Arguments.createMap();
                    onFound.putString("namespace", message.getNamespace());
                    onFound.putString("type", message.getType());
                    onFound.putArray("content", HmsUtils.getInstance().convertByteArrToWa(message.getContent()));
                    HmsUtils.getInstance().sendEvent(mContext, "backgroundOnFound", "BackgroundIntentService", onFound);
                }

                @Override
                public void onLost(Message message) {
                    WritableMap onLost = Arguments.createMap();
                    onLost.putString("namespace", message.getNamespace());
                    onLost.putString("type", message.getType());
                    onLost.putArray("content", HmsUtils.getInstance().convertByteArrToWa(message.getContent()));
                    HmsUtils.getInstance().sendEvent(mContext, "backgroundOnLost", "BackgroundIntentService", onLost);
                }
            });
        }
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return Constants.getMessageConstants();
    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }
}
