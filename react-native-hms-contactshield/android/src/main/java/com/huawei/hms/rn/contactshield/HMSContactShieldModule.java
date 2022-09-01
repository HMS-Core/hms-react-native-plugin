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

package com.huawei.hms.rn.contactshield;

import android.app.PendingIntent;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.gson.Gson;

import com.huawei.hms.contactshield.ContactShield;
import com.huawei.hms.contactshield.ContactShieldEngine;
import com.huawei.hms.contactshield.ContactShieldSetting;
import com.huawei.hms.contactshield.ContactShieldStatus;
import com.huawei.hms.contactshield.DailySketchConfiguration;
import com.huawei.hms.contactshield.DiagnosisConfiguration;
import com.huawei.hms.contactshield.SharedKeyFileProvider;
import com.huawei.hms.contactshield.SharedKeysDataMapping;
import com.huawei.hms.rn.contactshield.constants.IntentAction;
import com.huawei.hms.rn.contactshield.constants.RequestCode;
import com.huawei.hms.rn.contactshield.logger.HMSLogger;
import com.huawei.hms.rn.contactshield.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huawei.hms.rn.contactshield.utils.ObjectProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HMSContactShieldModule extends ReactContextBaseJavaModule {
    private final String TAG = HMSContactShieldModule.class.getSimpleName();

    private static ReactApplicationContext context;

    private final ContactShieldEngine mEngine;

    private final HMSLogger logger;

    private final Gson gson;

    public HMSContactShieldModule(ReactApplicationContext reactContext) {
        super(reactContext);
        setContext(reactContext);
        gson = new Gson();
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
    public void getStatus(Promise promise) {
        logger.startMethodExecutionTimer("getStatus");

        mEngine.getStatus().addOnSuccessListener(result -> {
            WritableNativeArray statusResult = new WritableNativeArray();
            try {
                for (ContactShieldStatus value : result) {
                    WritableNativeMap keyMap = new WritableNativeMap();
                    keyMap.putString("status", value.name());
                    keyMap.putDouble("value", value.getStatusValue());
                    statusResult.pushMap(keyMap);
                }
            } catch (Exception e) {
                Log.e(TAG, "getStatus: error ->" + e.toString());
            }
            Log.d(TAG, "getStatus succeeded");
            logger.sendSingleEvent("getStatus");
            promise.resolve(statusResult);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getStatus failed: " + e.getMessage());
            logger.sendSingleEvent("getStatus", e.getMessage());
            promise.reject("", e.getMessage());
        });
    }

    @ReactMethod
    public void getContactShieldVersion(Promise promise) {
        logger.startMethodExecutionTimer("getContactShieldVersion");

        mEngine.getContactShieldVersion().addOnFailureListener(e -> {
            Log.d(TAG, "getContactShieldVersion failed: " + e.getMessage());
            logger.sendSingleEvent("getContactShieldVersion", e.getMessage());
            promise.reject("", e.getMessage());
        }).addOnSuccessListener(result -> {
            Log.d(TAG, "getContactShieldVersion succeeded");
            logger.sendSingleEvent("getContactShieldVersion");
            promise.resolve(result.toString());
        });
    }

    @ReactMethod
    public void getDeviceCalibrationConfidence(Promise promise) {
        logger.startMethodExecutionTimer("getDeviceCalibrationConfidence");

        mEngine.getDeviceCalibrationConfidence().addOnFailureListener(e -> {
            Log.d(TAG, "getDeviceCalibrationConfidence failed: " + e.getMessage());
            logger.sendSingleEvent("getDeviceCalibrationConfidence", e.getMessage());
            promise.reject("", e.getMessage());
        }).addOnSuccessListener(result -> {
            Log.d(TAG, "getDeviceCalibrationConfidence succeeded");
            logger.sendSingleEvent("getDeviceCalibrationConfidence");
            promise.resolve(result.toString());
        });
    }

    @ReactMethod
    public void isSupportScanningWithoutLocation(Promise promise) {
        logger.startMethodExecutionTimer("isSupportScanningWithoutLocation");

        final boolean isSupportScan = mEngine.isSupportScanningWithoutLocation();
        logger.sendSingleEvent("isSupportScanningWithoutLocation");

        promise.resolve(isSupportScan);
    }

    @ReactMethod
    public void setSharedKeysDataMapping(ReadableMap args, Promise promise) throws JSONException {
        final ReadableMap daysSinceCreationToContagiousness = args.getMap("daysSinceCreationToContagiousness");
        final int defaultReportType = args.getInt("defaultReportType");
        final int defaultContagiousness = args.getInt("defaultContagiousness");

        // Convert ReadableMap to JsonObject
        final JSONObject jsonObject = Utils.toJSONObject(daysSinceCreationToContagiousness);
        // Convert JSONObject to Map<Integer,Integer>
        Map<Integer, Integer> mapObject = Utils.getMapObject(jsonObject);

        final SharedKeysDataMapping sharedKeysDataMapping
            = new SharedKeysDataMapping.Builder().setDaysSinceCreationToContagiousness(mapObject)
            .setDefaultContagiousness(defaultContagiousness)
            .setDefaultReportType(defaultReportType)
            .build();

        logger.startMethodExecutionTimer("setSharedKeysDataMapping");

        mEngine.setSharedKeysDataMapping(sharedKeysDataMapping).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "setSharedKeysDataMapping succeeded");
            logger.sendSingleEvent("setSharedKeysDataMapping");
            promise.resolve(true);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "setSharedKeysDataMapping failed:" + e.getMessage());
            logger.sendSingleEvent("setSharedKeysDataMapping", e.getMessage());
            promise.reject("", e.getMessage());
        });
    }

    @ReactMethod
    public void getSharedKeysDataMapping(Promise promise) {

        logger.startMethodExecutionTimer("getSharedKeysDataMapping");

        mEngine.getSharedKeysDataMapping().addOnSuccessListener(sharedKeysDataMapping -> {
            logger.sendSingleEvent("getSharedKeysDataMapping");
            try {
                promise.resolve(Utils.fromSharedKeysDataMappingToMap(sharedKeysDataMapping));
            } catch (JSONException e) {
                Log.e(TAG, "JSONException: error ->" + e.toString());
            }
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getSharedKeysDataMapping failed:" + e.getMessage());
            logger.sendSingleEvent("getSharedKeysDataMapping", e.getMessage());
            promise.reject("", e.getMessage());
        });
    }

    @ReactMethod
    public void getDailySketch(ReadableMap args, Promise promise) throws JSONException {
        final ReadableMap dailySketchConfigurationObject = args.getMap("dailySketchConfiguration");
        final JSONObject dailySketchConfigurationJson = Utils.convertMapToJson(dailySketchConfigurationObject);
        final DailySketchConfiguration dailySketchConfiguration = ObjectProvider.dailySketchConfiguration(
            dailySketchConfigurationJson, gson);

        logger.startMethodExecutionTimer("getDailySketch");

        mEngine.getDailySketch(dailySketchConfiguration).addOnSuccessListener(dailySketches -> {
            Log.d(TAG, "getDailySketch succeeded");
            logger.sendSingleEvent("getDailySketch");
            promise.resolve(Utils.fromDailySketchListToMap(dailySketches));
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getDailySketch error: " + e.getMessage());
            logger.sendSingleEvent("getDailySketch", e.getMessage());
            promise.reject("", e.getMessage());
        });
    }

    @ReactMethod
    public void putSharedKeyFilesProvider(ReadableArray paths, Promise promise) throws JSONException {
        final JSONArray pathsJSON = Utils.convertArrayToJson(paths);
        final List<File> files = Utils.convertJSONArrayToFileList(pathsJSON);

        final PendingIntent pendingIntent = ObjectProvider.getPendingIntent(context, IntentAction.CHECK_CONTACT_STATUS,
            RequestCode.PUT_SHARED_KEY_FILES);

        final SharedKeyFileProvider provider = new SharedKeyFileProvider(files);

        logger.startMethodExecutionTimer("putSharedKeyFilesProvider");

        mEngine.putSharedKeyFiles(pendingIntent, provider).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "putSharedKeyFilesProvider succeeded");
            logger.sendSingleEvent("putSharedKeyFilesProvider");
            deleteFiles(files);
            promise.resolve(true);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "putSharedKeyFilesProvider error:" + e.getMessage());
            logger.sendSingleEvent("putSharedKeyFilesProvider", e.getMessage());
            promise.reject("", e.getMessage());
        });
    }

    @ReactMethod
    public void putSharedKeyFilesKeys(ReadableArray paths, ReadableMap args, Promise promise) throws JSONException {
        final JSONArray pathsJSON = Utils.convertArrayToJson(paths);
        final List<File> files = Utils.convertJSONArrayToFileList(pathsJSON);

        final String token = args.getString("token");

        final ReadableMap diagnosisConfig = args.getMap("diagnosisConfiguration");
        final JSONObject diagnosisConfigJSON = Utils.toJSONObject(
            diagnosisConfig); //Convert ReadableMap to JSONObject for diagnosisConfiguration

        final ReadableArray publicKeysReadable = args.getArray("publicKeys");
        final JSONArray publicKeysJSON = Utils.convertArrayToJson(
            publicKeysReadable); //Convert ReadableArray to JSONArray for publicKeys
        final List<String> publicKeys = Utils.convertJSONArrayToList(
            publicKeysJSON); //Convert JSONArray to List for publicKeys

        final PendingIntent pendingIntent = ObjectProvider.getPendingIntent(context, IntentAction.CHECK_CONTACT_STATUS,
            RequestCode.PUT_SHARED_KEY_FILES);

        final DiagnosisConfiguration diagnosisConfiguration = ObjectProvider.getDiagnosisConfiguration(
            diagnosisConfigJSON, gson);

        logger.startMethodExecutionTimer("putSharedKeyFilesKeys");

        mEngine.putSharedKeyFiles(pendingIntent, files, publicKeys, diagnosisConfiguration, token)
            .addOnSuccessListener(aVoid -> {
                Log.d(TAG, "putSharedKeyFilesKeys succeeded");
                logger.sendSingleEvent("putSharedKeyFilesKeys");
                promise.resolve(true);
            })
            .addOnFailureListener(e -> {
                Log.d(TAG, "putSharedKeyFilesKeys failed: " + e.getMessage());
                logger.sendSingleEvent("putSharedKeyFilesKeys", e.getMessage());
                promise.resolve(e.getMessage());
            });
    }

    @ReactMethod
    public void startContactShield(int incubationPeriod, Promise promise) {
        ContactShieldSetting contactShieldSetting = new ContactShieldSetting.Builder().setIncubationPeriod(
            incubationPeriod).build();

        logger.startMethodExecutionTimer("startContactShield");
        mEngine.startContactShield(contactShieldSetting).addOnFailureListener(e -> {
            Log.d(TAG, "startContactShield failed, cause: " + e.getMessage());
            logger.sendSingleEvent("startContactShield", e.getMessage());
            promise.reject("", e.getMessage());
        }).addOnSuccessListener(aVoid -> {
            Log.i(TAG, "startContactShield succeed");
            logger.sendSingleEvent("startContactShield");
            promise.resolve(true);
        });
    }

    @ReactMethod
    public void startContactShieldCallback(int incubationPeriod, Promise promise) {
        ContactShieldSetting contactShieldSetting = new ContactShieldSetting.Builder().setIncubationPeriod(
            incubationPeriod).build();

        logger.startMethodExecutionTimer("startContactShield");

        final PendingIntent pendingIntent = ObjectProvider.getPendingIntent(context, IntentAction.CHECK_CONTACT_STATUS,
            RequestCode.PUT_SHARED_KEY_FILES);

        mEngine.startContactShield(pendingIntent, contactShieldSetting).addOnSuccessListener(aVoid -> {
            Log.i(TAG, "startContactShield succeed");
            logger.sendSingleEvent("startContactShield");
            promise.resolve(true);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "startContactShield failed, cause: " + e.getMessage());
            logger.sendSingleEvent("startContactShield", e.getMessage());
            promise.reject("", e.getMessage());
        });
    }

    @ReactMethod
    public void stopContactShield(Promise promise) {
        logger.startMethodExecutionTimer("stopContactShield");
        mEngine.stopContactShield().addOnSuccessListener(aVoid -> {
            Log.d(TAG, "stopContactShield succeed.");
            logger.sendSingleEvent("stopContactShield");
            promise.resolve(true);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "stopContactShield failed, cause: " + e.getMessage());
            logger.sendSingleEvent("stopContactShield", e.getMessage());
            promise.reject("", e.getMessage());
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
            logger.sendSingleEvent("getPeriodicKey", e.getMessage());
            promise.reject("", e.getMessage());
        }));
    }

    @ReactMethod
    void getContactWindow(String token, Promise promise) {
        logger.startMethodExecutionTimer("getContactWindow");
        mEngine.getContactWindow(token).addOnCompleteListener(task -> task.addOnSuccessListener(contactWindow -> {
            Log.d(TAG, "getContactWindow succeeded");
            logger.sendSingleEvent("getContactWindow");
            promise.resolve(Utils.fromContactWindowListToMap(contactWindow));
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getContactWindow failed, cause: " + e.getMessage());
            logger.sendSingleEvent("getContactWindow", e.getMessage());
            promise.reject("", e.getMessage());
        }));
    }

    @ReactMethod
    public void putSharedKeyFilesCallback(ReadableArray paths, String token, ReadableMap diagnosisConfiguration,
        Promise promise) throws JSONException {
        final JSONArray pathsJSON = Utils.convertArrayToJson(paths);
        final List<File> files = Utils.convertJSONArrayToFileList(pathsJSON);

        final PendingIntent pendingIntent = ObjectProvider.getPendingIntent(context, IntentAction.CHECK_CONTACT_STATUS,
            RequestCode.PUT_SHARED_KEY_FILES);

        final JSONObject diagnosisConfigJSON = Utils.toJSONObject(diagnosisConfiguration);
        final DiagnosisConfiguration diagnosisConfig = ObjectProvider.getDiagnosisConfiguration(diagnosisConfigJSON,
            gson);

        logger.startMethodExecutionTimer("putSharedKeyFiles");
        mEngine.putSharedKeyFiles(pendingIntent, files, diagnosisConfig, token).addOnSuccessListener(aVoid -> {
            logger.sendSingleEvent("putSharedKeyFiles");
            promise.resolve(true);
        }).addOnFailureListener(e -> {
            logger.sendSingleEvent("putSharedKeyFiles", e.getMessage());
            promise.reject("", e.getMessage());
        });
    }

    @ReactMethod
    public void putSharedKeyFiles(ReadableArray paths, String token, ReadableMap diagnosisConfiguration,
        Promise promise) throws JSONException {
        final JSONArray pathsJSON = Utils.convertArrayToJson(paths);
        final List<File> files = Utils.convertJSONArrayToFileList(pathsJSON);

        final JSONObject diagnosisConfigJSON = Utils.toJSONObject(diagnosisConfiguration);
        final DiagnosisConfiguration diagnosisConfig = ObjectProvider.getDiagnosisConfiguration(diagnosisConfigJSON,
            gson);

        logger.startMethodExecutionTimer("putSharedKeyFiles");
        mEngine.putSharedKeyFiles(files, diagnosisConfig, token).addOnFailureListener(e -> {
            logger.sendSingleEvent("putSharedKeyFiles", e.getMessage());
            promise.reject("", e.getMessage());
        }).addOnSuccessListener(aVoid -> {
            logger.sendSingleEvent("putSharedKeyFiles");
            promise.resolve(true);
        });
    }

    @ReactMethod
    public void putSharedKeyFilesKeysProvider(ReadableArray paths, ReadableArray publicKeys, Promise promise)
        throws JSONException {
        final JSONArray pathsJSON = Utils.convertArrayToJson(paths);
        final List<File> files = Utils.convertJSONArrayToFileList(pathsJSON);

        final PendingIntent pendingIntent = ObjectProvider.getPendingIntent(context, IntentAction.CHECK_CONTACT_STATUS,
            RequestCode.PUT_SHARED_KEY_FILES);

        final SharedKeyFileProvider provider = new SharedKeyFileProvider(files);

        final JSONArray publicKeysJSON = Utils.convertArrayToJson(publicKeys);
        final List<String> publicKeysList = Utils.convertJSONArrayToList(publicKeysJSON);

        logger.startMethodExecutionTimer("putSharedKeyFilesKeysProvider");
        mEngine.putSharedKeyFiles(pendingIntent, provider, publicKeysList).addOnFailureListener(e -> {
            logger.sendSingleEvent("putSharedKeyFilesKeysProvider", e.getMessage());
            promise.reject("", e.getMessage());
        }).addOnSuccessListener(aVoid -> {
            logger.sendSingleEvent("putSharedKeyFilesKeysProvider");
            promise.resolve(true);
        });

    }

    @ReactMethod
    public void getContactSketch(String token, Promise promise) {
        logger.startMethodExecutionTimer("getContactSketch");

        mEngine.getContactSketch(token).addOnCompleteListener(task -> task.addOnSuccessListener(contactSketch -> {
            Log.d(TAG, "getContactSketch succeeded");
            logger.sendSingleEvent("getContactSketch");
            promise.resolve(Utils.fromContactSketchDataToMap(contactSketch));
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getContactSketch failed, cause: " + e.getMessage());
            logger.sendSingleEvent("getContactSketch", e.getMessage());
            promise.reject("", e.getMessage());
        }));
    }

    @ReactMethod
    public void getContactDetail(String token, Promise promise) {
        logger.startMethodExecutionTimer("getContactDetail");

        mEngine.getContactDetail(token).addOnCompleteListener(task -> task.addOnSuccessListener(contactDetails -> {
            Log.d(TAG, "getContactDetail succeeded.");
            logger.sendSingleEvent("getContactDetail");
            promise.resolve(Utils.fromContactDetailsListToMap(contactDetails));
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getContactDetail failed, cause: " + e.getMessage());
            logger.sendSingleEvent("getContactDetail", e.getMessage());
            promise.reject("", e.getMessage());
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
            logger.sendSingleEvent("clearData", e.getMessage());
            promise.reject("", e.getMessage());
        });
    }

    @ReactMethod
    void startContactShieldNoPersistent(int incubationPeriod, Promise promise) {
        ContactShieldSetting contactShieldSetting = new ContactShieldSetting.Builder().setIncubationPeriod(
            incubationPeriod).build();

        logger.startMethodExecutionTimer("startContactShieldNoPersistent");

        mEngine.startContactShieldNoPersistent(contactShieldSetting).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "startContactShieldNoPersistent succeed.");
            logger.sendSingleEvent("startContactShieldNoPersistent");
            promise.resolve(true);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "startContactShieldNoPersistent failed, cause: " + e.getMessage());
            logger.sendSingleEvent("startContactShieldNoPersistent", e.getMessage());
            promise.reject("", e.getMessage());
        });
    }

    @ReactMethod
    public void isContactShieldRunning(Promise promise) {
        logger.startMethodExecutionTimer("isContactShieldRunning");

        mEngine.isContactShieldRunning().addOnCompleteListener(task -> {
            Log.d(TAG, "isContactShieldRunning succeeded");
            logger.sendSingleEvent("isContactShieldRunning");
            promise.resolve(task.getResult());
        }).addOnFailureListener(e -> {
            Log.d(TAG, "isContactShieldRunning failed, cause: " + e.getMessage());
            logger.sendSingleEvent("isContactShieldRunning", e.getMessage());
            promise.reject("", e.getMessage());
        });
    }

    @ReactMethod
    public void getDiagnosisConfiguration(Promise promise) {
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

    private void deleteFiles(List<File> files) {
        for (File file : files) {
            Log.i(TAG, "isFileDelete: " + file.delete());
        }
    }
}
