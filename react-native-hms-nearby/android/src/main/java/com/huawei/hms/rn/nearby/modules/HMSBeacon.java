/*
 *  Copyright 2020-2024. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License")
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.huawei.hms.rn.nearby.modules;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.nearby.Nearby;
import com.huawei.hms.nearby.beacon.BeaconMsgCondition;
import com.huawei.hms.nearby.beacon.BeaconPicker;
import com.huawei.hms.nearby.beacon.GetBeaconOption;
import com.huawei.hms.nearby.beacon.RawBeaconCondition;
import com.huawei.hms.nearby.beacon.TriggerOption;
import com.huawei.hms.nearby.message.BeaconInfo;
import com.huawei.hms.rn.nearby.utils.HMSLogger;
import com.huawei.hms.rn.nearby.utils.HMSUtils;

import static android.content.Context.RECEIVER_EXPORTED;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.ACTION_SCAN_ONFOUND_RESULT;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.BEACON_CONSTANTS;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.KEY_SCAN_BEACON_DATA;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.KEY_SCAN_ONFOUND_FLAG;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.START_REGISTER_RECEIVER;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.TAG;

import java.util.List;
import java.util.Objects;

public class HMSBeacon extends HMSBase {

    private BeaconBroadcastReceiver beaconReceiver;

    /**
     * Constructor that initializes beacon module
     *
     * @param context app context
     */
    public HMSBeacon(ReactApplicationContext context) {
        super(context, HMSBeacon.class.getSimpleName(), BEACON_CONSTANTS);
    }

    private BeaconPicker beaconPicker;

    @ReactMethod
    public void registerScan(ReadableMap readableMap, final Promise promise) {
        startMethodExecTimer("registerScan");

        String beaconId = null;
        int beaconType = 1;
        String namespace = null;
        String type = null;

        if(readableMap != null) {
            if(HMSUtils.getInstance().hasValidKey(readableMap,"beaconId", ReadableType.String)){
                beaconId = readableMap.getString("beaconId");
            }
            if(HMSUtils.getInstance().hasValidKey(readableMap,"beaconType", ReadableType.Number)){
                beaconType = readableMap.getInt("beaconType");
            }
            if(HMSUtils.getInstance().hasValidKey(readableMap,"namespace", ReadableType.String)){
                namespace = readableMap.getString("namespace");
            }
            if(HMSUtils.getInstance().hasValidKey(readableMap,"type", ReadableType.String)){
                type = readableMap.getString("type");
            }
        }

        TriggerOption triggerOption = new TriggerOption.Builder().setTriggerMode(2).setTriggerClassName(BeaconBroadcastReceiver.class.getName()).build();
        Intent intent = new Intent();
        intent.putExtra(GetBeaconOption.KEY_TRIGGER_OPTION,triggerOption);


        if(beaconId != null && namespace == null && type == null){
            beaconPicker = new BeaconPicker.Builder().includeBeaconId(beaconId,beaconType).build();

        } else if (beaconId == null && namespace != null && type != null) {
            beaconPicker = new BeaconPicker.Builder().includeNamespaceType(namespace,type).build();

        } else if (beaconId != null && namespace != null && type != null) {
            beaconPicker = new BeaconPicker.Builder().includeNamespaceType(namespace,type,beaconId,beaconType).build();

        } else {
            promise.reject("Error","Invalid Values");
        }

        GetBeaconOption getOption = new GetBeaconOption.Builder().picker(beaconPicker).build();

        Task<Void> register = Nearby.getBeaconEngine(getContext()).registerScanTask(intent,getOption);
        startRegisterReceiver(register, "registerScan", promise);
    }

    @ReactMethod
    public void unRegisterScan(final Promise promise) {
        startMethodExecTimer("unRegisterScan");

        TriggerOption triggerOption = new TriggerOption.Builder().setTriggerMode(2).setTriggerClassName(BeaconBroadcastReceiver.class.getName()).build();
        Intent intent = new Intent();
        intent.putExtra(GetBeaconOption.KEY_TRIGGER_OPTION,triggerOption);

        beaconPicker = null;
        handleResult("unRegisterScan", Nearby.getBeaconEngine(getContext()).unRegisterScanTask(intent),promise);
    }

    @ReactMethod
    public void getBeaconMsgConditions(final Promise promise) {
        startMethodExecTimer("getBeaconIdBeaconMsg");
        List<BeaconMsgCondition> beaconMsgConditionList;
        WritableMap wm = Arguments.createMap();

        if(beaconPicker == null){
            getLogger().sendSingleEvent("getBeaconMsgConditions", "Register ERROR");
            promise.reject("BeaconMsgError","Register ERROR");
        } else {
                beaconMsgConditionList = beaconPicker.getBeaconMsgConditions();
                for(int i=0; i < beaconMsgConditionList.size(); i++){
                    WritableMap map = Arguments.createMap();
                    map.putString("beaconId", beaconMsgConditionList.get(i).getBeaconId());
                    map.putInt("beaconType", beaconMsgConditionList.get(i).getBeaconType());
                    map.putString("namespace", beaconMsgConditionList.get(i).getNamespace());
                    map.putString("type", beaconMsgConditionList.get(i).getType());
                    wm.merge(map);

                }
                getLogger().sendSingleEvent("getBeaconMsgConditions");
                promise.resolve(wm);
        }
    }

    @ReactMethod
    public void getRawBeaconConditions(final Promise promise){
        startMethodExecTimer("getBeaconIdRawBeacon");
        List<RawBeaconCondition> rawBeaconConditionList;
        WritableMap wm = Arguments.createMap();

        if(beaconPicker == null) {
            getLogger().sendSingleEvent("getRawBeaconConditions", "Register ERROR");
            promise.reject("RawBeaconError","Register ERROR");
        } else {
                rawBeaconConditionList = beaconPicker.getRawBeaconConditions();
                for(int i=0; i < rawBeaconConditionList.size(); i++){
                    WritableMap map = Arguments.createMap();
                    map.putString("beaconId", rawBeaconConditionList.get(i).getBeaconId());
                    map.putInt("beaconType", rawBeaconConditionList.get(i).getBeaconType());
                    wm.merge(map);
                }
                getLogger().sendSingleEvent("getRawBeaconConditions");
                promise.resolve(wm);
        }

    }

    @ReactMethod
    public void getRawBeaconConditionsWithBeaconType(int beaconType, final Promise promise){
        startMethodExecTimer("getBeaconIdRawBeaconWithBeaconType");
        List<RawBeaconCondition> rawBeaconConditionList;
        WritableMap wm = Arguments.createMap();

        if(beaconPicker == null){
            getLogger().sendSingleEvent("getRawBeaconConditionsWithBeaconType", "Register ERROR");
            promise.reject("RawBeaconError","Register ERROR");
        }else {
            rawBeaconConditionList = beaconPicker.getRawBeaconConditions(beaconType);
            for(int i=0; i < rawBeaconConditionList.size(); i++){
                WritableMap map = Arguments.createMap();
                map.putString("beaconId", rawBeaconConditionList.get(i).getBeaconId());
                map.putInt("beaconType", rawBeaconConditionList.get(i).getBeaconType());
                wm.merge(map);
            }
            getLogger().sendSingleEvent("getRawBeaconConditionsWithBeaconType");
            promise.resolve(wm);
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private void startRegisterReceiver(Task<Void> taskRegister, String methodName, final Promise promise){
        taskRegister.addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                if(beaconReceiver != null) {
                    Objects.requireNonNull(getCurrentActivity()).unregisterReceiver(beaconReceiver);
                }
                beaconReceiver = new BeaconBroadcastReceiver(promise, methodName, getContext());
                IntentFilter intentBeaconFilter = new IntentFilter(START_REGISTER_RECEIVER);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    Objects.requireNonNull(getCurrentActivity()).registerReceiver(beaconReceiver, intentBeaconFilter, RECEIVER_EXPORTED);
                } else {
                    Objects.requireNonNull(getCurrentActivity()).registerReceiver(beaconReceiver, intentBeaconFilter);
                }

            }
        }).addOnFailureListener(e -> Log.e(methodName,"Error RegisterReceiver: " + e.getMessage()));
    }


    public static class BeaconBroadcastReceiver extends BroadcastReceiver {


        private final Promise promise;
        private final String methodName;
        private final ReactContext reactContext;

        public BeaconBroadcastReceiver(Promise promise, String methodName, ReactContext context) {
            this.promise = promise;
            this.methodName = methodName;
            reactContext = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if(intent == null){
                    Log.e(TAG,"intent is null");
                    return;
                }

                Log.i(TAG,"onReceive:" + intent);
                String action = intent.getAction();

                if(ACTION_SCAN_ONFOUND_RESULT.equals(action)){
                    int onFound = intent.getIntExtra(KEY_SCAN_ONFOUND_FLAG, -1);
                    Log.i(TAG, "onReceive onFound, isFound:" + onFound);
                    List<BeaconInfo> beaconIds = intent.getParcelableArrayListExtra(KEY_SCAN_BEACON_DATA);
                    if(beaconIds == null) {
                        Log.w(TAG,"beacon Ids is null");
                        return;
                    }
                    for(BeaconInfo beacon : beaconIds) {
                        HMSLogger.getInstance(context).sendPeriodicEvent(methodName);
                        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("beaconReceiver", beacon.getBeaconId());
                    }
                }
            } catch (IllegalArgumentException e) {
                promise.reject("Error BeaconReceive", e);
            }

        }
    }

}
