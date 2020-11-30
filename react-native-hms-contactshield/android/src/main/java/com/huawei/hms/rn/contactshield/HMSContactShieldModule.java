/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.contactshield;

import android.app.PendingIntent;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.contactshield.ContactShield;
import com.huawei.hms.contactshield.ContactShieldEngine;
import com.huawei.hms.contactshield.ContactShieldSetting;
import com.huawei.hms.contactshield.DiagnosisConfiguration;
import com.huawei.hms.rn.contactshield.constants.IntentAction;
import com.huawei.hms.rn.contactshield.constants.RequestCode;
import com.huawei.hms.rn.contactshield.logger.HMSLogger;
import com.huawei.hms.rn.contactshield.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huawei.hms.rn.contactshield.utils.ObjectProvider;

public class HMSContactShieldModule extends ReactContextBaseJavaModule {
    private final String TAG = HMSContactShieldModule.class.getSimpleName();

    private static ReactApplicationContext context;
    private ContactShieldEngine mEngine;
    private HMSLogger logger;

    public HMSContactShieldModule(ReactApplicationContext reactContext) {
        super(reactContext);
        setContext(reactContext);
        logger = HMSLogger.getInstance(reactContext);
        mEngine = ContactShield.getContactShieldEngine(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public Map<String, Object> getConstants() {
        return new HashMap<>();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    public static ReactApplicationContext getContext() {
        return context;
    }

    public static void setContext(ReactApplicationContext context) {
        HMSContactShieldModule.context = context;
    }

    @ReactMethod
    public void startContactShield(int incubationPeriod, Promise promise) {
        ContactShieldSetting contactShieldSetting = new ContactShieldSetting.Builder().setIncubationPeriod(incubationPeriod).build();

        logger.startMethodExecutionTimer("startContactShield");
        mEngine.startContactShield(contactShieldSetting).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "startContactShield failed, cause: " + e.getMessage());
                logger.sendSingleEvent("startContactShield", e.getMessage());
                promise.reject("",e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "startContactShield succeed");
                logger.sendSingleEvent("startContactShield");
                promise.resolve(true);
            }
        });
    }

    @ReactMethod
    public void startContactShieldCallback(int incubationPeriod, Promise promise){
        ContactShieldSetting contactShieldSetting = new ContactShieldSetting.Builder().setIncubationPeriod(incubationPeriod).build();

        logger.startMethodExecutionTimer("startContactShield");

        final PendingIntent pendingIntent = ObjectProvider.getPendingIntent(this.context,
                IntentAction.CHECK_CONTACT_STATUS, RequestCode.PUT_SHARED_KEY_FILES);

        mEngine.startContactShield(pendingIntent,contactShieldSetting).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "startContactShield succeed");
                logger.sendSingleEvent("startContactShield");
                promise.resolve(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "startContactShield failed, cause: " + e.getMessage());
                logger.sendSingleEvent("startContactShield", e.getMessage());
                promise.reject("",e.getMessage());
            }
        });
    }


    @ReactMethod
    public void stopContactShield(Promise promise) {
        logger.startMethodExecutionTimer("stopContactShield");
        mEngine.stopContactShield().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "stopContactShield succeed.");
                logger.sendSingleEvent("stopContactShield");
                promise.resolve(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "stopContactShield failed, cause: " + e.getMessage());
                logger.sendSingleEvent("stopContactShield", e.getMessage());
                promise.reject("",e.getMessage());
            }
        });
    }


    @ReactMethod
    void getPeriodicKey(Promise promise) {
        logger.startMethodExecutionTimer("getPeriodicKey");
        mEngine.getPeriodicKey().addOnCompleteListener(task -> task.addOnSuccessListener(periodicKeys -> {
            Log.d(TAG, "getPeriodicKey succeeded");
            logger.sendSingleEvent("getPeriodicKey");
            promise.resolve(Utils.fromPeriodicKeyListToMap(periodicKeys));
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getPeriodicKey failed, cause: " + e.getMessage());
            logger.sendSingleEvent("getPeriodicKey",e.getMessage());
            promise.reject("",e.getMessage());
        }));
    }

    @ReactMethod
    void getContactWindow(String token,Promise promise) {
        logger.startMethodExecutionTimer("getContactWindow");
        mEngine.getContactWindow(token)
                .addOnCompleteListener(task -> task.addOnSuccessListener(contactWindow -> {
                    Log.d(TAG, "getContactWindow succeeded");
                    logger.sendSingleEvent("getContactWindow");
                    promise.resolve(Utils.fromContactWindowListToMap(contactWindow));
                }).addOnFailureListener(e -> {
                    Log.d(TAG, "getContactWindow failed, cause: " + e.getMessage());
                    logger.sendSingleEvent("getContactWindow", e.getMessage());
                    promise.reject("",e.getMessage());
                }));
    }

    @ReactMethod
    public void putSharedKeyFilesCallback(String path, String token, Promise promise) {
        final List<File> files = new ArrayList<>();
        final File file = new File(path);
        files.add(file);

        final PendingIntent pendingIntent = ObjectProvider.getPendingIntent(this.context,
                IntentAction.CHECK_CONTACT_STATUS, RequestCode.PUT_SHARED_KEY_FILES);

        DiagnosisConfiguration config = new DiagnosisConfiguration.Builder().build();

        logger.startMethodExecutionTimer("putSharedKeyFiles");
        mEngine.putSharedKeyFiles(pendingIntent,files, config, token).addOnSuccessListener(aVoid -> {
            logger.sendSingleEvent("putSharedKeyFiles");
            promise.resolve(true);
        }).addOnFailureListener(e -> {
            logger.sendSingleEvent("putSharedKeyFiles", e.getMessage());
            promise.reject("",e.getMessage());
        });
    }

    @ReactMethod
    public void putSharedKeyFiles(String path, String token, Promise promise) {
        final List<File> files = new ArrayList<>();
        final File file = new File(path);
        files.add(file);

        DiagnosisConfiguration config = new DiagnosisConfiguration.Builder().build();

        logger.startMethodExecutionTimer("putSharedKeyFiles");
        mEngine.putSharedKeyFiles(files,config, token).addOnFailureListener(e -> {
            logger.sendSingleEvent("putSharedKeyFiles", e.getMessage());
            promise.reject("",e.getMessage());
        }).addOnSuccessListener(aVoid -> {
            logger.sendSingleEvent("putSharedKeyFiles");
            promise.resolve(true);
        });
    }


    @ReactMethod
    public void getContactSketch(String token,Promise promise) {
        logger.startMethodExecutionTimer("getContactSketch");

        mEngine.getContactSketch(token)
                .addOnCompleteListener(task -> task.addOnSuccessListener(
                        contactSketch -> {
                            Log.d(TAG, "getContactSketch succeeded");
                            logger.sendSingleEvent("getContactSketch");
                            promise.resolve(Utils.fromContactSketchDataToMap(contactSketch));
                        })
                        .addOnFailureListener(e -> {
                            Log.d(TAG, "getContactSketch failed, cause: " + e.getMessage());
                            logger.sendSingleEvent("getContactSketch",e.getMessage());
                            promise.reject("",e.getMessage());
                        }));
    }

    @ReactMethod
    public void getContactDetail(String token,Promise promise) {
        logger.startMethodExecutionTimer("getContactDetail");

        mEngine.getContactDetail(token).addOnCompleteListener(
                task -> task.addOnSuccessListener(contactDetails -> {
                    Log.d(TAG, "getContactDetail succeeded.");
                    logger.sendSingleEvent("getContactDetail");
                    promise.resolve(Utils.fromContactDetailsListToMap(contactDetails));
                }).addOnFailureListener(e -> {
                    Log.d(TAG, "getContactDetail failed, cause: " +e.getMessage());
                    logger.sendSingleEvent("getContactDetail", e.getMessage());
                    promise.reject("",e.getMessage());
                }));
    }

    @ReactMethod
    public void clearAllData(Promise promise) {
        logger.startMethodExecutionTimer("clearData");
        mEngine.clearData().addOnSuccessListener(clearAllData -> {
            Log.d(TAG, "clearAllData succeeded.");
            logger.sendSingleEvent("clearData");
            promise.resolve(true);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "clearAllData failed, cause: " + e.getMessage());
            logger.sendSingleEvent("clearData",e.getMessage());
            promise.reject("",e.getMessage());
        });
    }

    @ReactMethod
    void startContactShieldNoPersistent(int incubationPeriod, Promise promise) {
        ContactShieldSetting contactShieldSetting = new ContactShieldSetting.Builder().setIncubationPeriod(incubationPeriod).build();

        logger.startMethodExecutionTimer("startContactShieldNoPersistent");

        mEngine.startContactShieldNoPersistent(contactShieldSetting).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "startContactShieldNoPersistent succeed.");
            logger.sendSingleEvent("startContactShieldNoPersistent");
            promise.resolve(true);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "startContactShieldNoPersistent failed, cause: " + e.getMessage());
            logger.sendSingleEvent("startContactShieldNoPersistent", e.getMessage());
            promise.reject("",e.getMessage());
        });
    }

    @ReactMethod
    public void isContactShieldRunning(Promise promise) {
        logger.startMethodExecutionTimer("isContactShieldRunning");

        mEngine.isContactShieldRunning().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(Task<Boolean> task) {
                Log.d(TAG, "isContactShieldRunning succeeded");
                logger.sendSingleEvent("isContactShieldRunning");
                promise.resolve(task.getResult());
            }
        }).addOnFailureListener(e -> {
            Log.d(TAG, "isContactShieldRunning failed, cause: " + e.getMessage());
            logger.sendSingleEvent("isContactShieldRunning",e.getMessage());
            promise.reject("",e.getMessage());
        });
    }

    @ReactMethod
    public void getDiagnosisConfiguration(Promise promise){
        DiagnosisConfiguration config = new DiagnosisConfiguration.Builder().build();
        promise.resolve(Utils.fromDiagnosisConfigurationToMap(config));
    }

    @ReactMethod
    public void enableLogger() {
        logger.enableLogger();
    }

    @ReactMethod
    public void disableLogger() {
        logger.disableLogger();
    }
}
