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

package com.huawei.hms.rn.health.kits.autorecorder;

import static com.huawei.hms.rn.health.foundation.util.MapUtils.createWritableMapWithSuccessStatus;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toWritableMap;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toWritableMapWithMessage;
import static com.huawei.hms.rn.health.foundation.view.BaseProtocol.View.getActivity;
import static com.huawei.hms.rn.health.kits.autorecorder.utils.AutoRecorderConstants.BACKGROUND_SERVICE_KEY;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.hihealth.AutoRecorderController;
import com.huawei.hms.hihealth.HiHealthOptions;
import com.huawei.hms.hihealth.HuaweiHiHealth;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.SamplePoint;
import com.huawei.hms.rn.health.foundation.helper.VoidResultHelper;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;
import com.huawei.hms.rn.health.foundation.util.Utils;
import com.huawei.hms.rn.health.foundation.view.BaseController;
import com.huawei.hms.rn.health.foundation.view.BaseProtocol;
import com.huawei.hms.rn.health.kits.autorecorder.listener.TaskVoidResultListener;
import com.huawei.hms.rn.health.kits.autorecorder.utils.AutoRecorderBackgroundService;
import com.huawei.hms.rn.health.kits.autorecorder.utils.AutoRecorderConstants;
import com.huawei.hms.rn.health.kits.autorecorder.viewmodel.AutoRecorderService;
import com.huawei.hms.rn.health.kits.autorecorder.viewmodel.AutoRecorderViewModel;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/**
 * {@link HmsAutoRecorderController} class is a module that refers to {@link AutoRecorderController}
 *
 * @since v.5.0.1
 */
public class HmsAutoRecorderController extends BaseController implements BaseProtocol.Event {

    private static final String TAG = HmsAutoRecorderController.class.getSimpleName();

    // Internal context object
    private final ReactContext reactContext;

    // ViewModel instance to reach AutoRecorderController tasks
    private final AutoRecorderService autoRecorderViewModel;

    // HMS Health AutoRecorderController
    private AutoRecorderController autoRecorderController;

    // Intent for background service
    private final Intent serviceIntent;

    // Broadcast receiver for getting data from service
    private AutoRecorderReceiver receiver;

    // Whether is recording now
    private boolean isRecording;

    // HMSLogger instance
    private HMSLogger logger;

    private final ActivityEventListener activityEventListener = new ActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
            Log.i(TAG, "onActivityResult, requestCode=" + requestCode + ", resultCode=" + resultCode);
            initAutoRecorderController();
        }

        @Override
        public void onNewIntent(Intent intent) {
            Log.d(TAG, "onNewIntent");
        }
    };

    /**
     * Initialization
     */
    public HmsAutoRecorderController(ReactApplicationContext reactContext) {
        super(TAG, reactContext);
        this.reactContext = reactContext;
        this.reactContext.addActivityEventListener(activityEventListener);
        autoRecorderViewModel = new AutoRecorderViewModel();
        serviceIntent = new Intent(reactContext, AutoRecorderBackgroundService.class);
        serviceIntent.setPackage(reactContext.getPackageName());
        serviceIntent.setAction(BACKGROUND_SERVICE_KEY);
        isRecording = false;

        logger = HMSLogger.getInstance(reactContext);
    }

    /**
     * Record data via DataType supported by Huawei.
     * </br>
     * Start record By DataType, the data from sensor will be inserted into database automatically until call Stop
     * Interface
     *
     * @param readableMap ReadableMap instance to get {@link DataType} object that contains request information.
     * @param promise In the success scenario, Void instance is returned , or Exception is returned in the failure scenario.
     * Also, the interface won't always success, onCompleteStartRecordByType event will be triggered once the task is completed to get the judgement of result is successful or not.
     * The fail reason includes:
     * 1. The app hasn't been granted the scopes.
     * 2. This type is not supported so far.
     */
    @ReactMethod
    public void startRecord(final ReadableMap readableMap, final ReadableMap notificationOptions,
        final Promise promise) {
        String logName = "HmsAutoRecorderController.startRecord";
        logger.startMethodExecutionTimer(logName);

        checkAutoRecorderController();
        DataType dataType = Utils.INSTANCE.toDataType(readableMap);

        if (!isRecording) {
            registerReceiver(dataType, notificationOptions);
            isRecording = true;
            sendEvent(reactContext, AutoRecorderConstants.OnCompleteEventType.START_RECORD_BY_TYPE.getValue(),
                toWritableMapWithMessage("onComplete - startRecordByType completed.", true));
            promise.resolve(createWritableMapWithSuccessStatus(true));
        } else {
            promise.reject("record_already_started", "Recorder is already started");
        }

    }

    /**
     * Stop recoding by specifying the data type.
     * </br>
     * Stop record By DataType, the data from sensor will NOT be inserted into database automatically
     *
     * <p>
     * Note: You are advised to obtain the record using the getRecords method or create the record by specifying the data type and/or data collector.
     * If both the data type and data collector are specified, ensure that the data type in the data collector is the same as that in the record information. Otherwise, errors will occur.
     * </p>
     *
     * @param dataTypeMap ReadableMap instance to get {@link DataType} object that contains request information.
     * @param promise In the success scenario, Void instance is returned , or Exception is returned in the failure scenario. Also,
     * the interface won't always success, onCompleteStartRecordByType event will be triggered once the task is completed to get the judgement of
     * result is successful or not.
     * The fail reason includes:
     * 1. The app hasn't been granted the scopes.
     * 2. This type is not supported so far.
     */
    @ReactMethod
    public void stopRecord(final ReadableMap dataTypeMap, final Promise promise) {
        String logName = "HmsAutoRecorderController.stopRecord";
        logger.startMethodExecutionTimer(logName);

        checkAutoRecorderController();
        DataType dataType = Utils.INSTANCE.toDataType(dataTypeMap);

        try {
            unregisterReceiver();
            autoRecorderViewModel.stopRecord(this.autoRecorderController, dataType,
                new TaskVoidReqHelper(promise, "onComplete - stopRecordByType completed.",
                    AutoRecorderConstants.OnCompleteEventType.STOP_RECORD_BY_TYPE, logger, logName));
            isRecording = false;
        } catch (RuntimeException e) {
            promise.reject("runtime_exeception", "There is a runtime problem");
        } catch (Exception e) {
            promise.reject("record_not_found", "Ongoing record is not found");
        }
    }

    /**
     * Sends event to RN Side.
     *
     * @param reactContext ReactContext instance.
     * @param eventName Event name that will be available via {@link HmsAutoRecorderController}.
     * @param params Event params.
     */
    @Override
    public void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    /* Private Methods */

    /**
     * Sends event with requested ON_COMPLETE_EVENT_TYPE.
     */

    private void registerReceiver(DataType dataType, ReadableMap notification) {
        serviceIntent.putExtra("DataType", dataType);
        Utils.INSTANCE.fillServiceIntent(serviceIntent, notification);
        reactContext.startService(serviceIntent);
        receiver = new AutoRecorderReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BACKGROUND_SERVICE_KEY);
        reactContext.registerReceiver(receiver, filter, Manifest.permission.FOREGROUND_SERVICE, null);
    }

    private void unregisterReceiver() {
        reactContext.stopService(serviceIntent);
        reactContext.unregisterReceiver(receiver);
    }

    private void sendEvent(final AutoRecorderConstants.OnCompleteEventType type, final WritableMap writableMap) {
        sendEvent(reactContext, type.getValue(), writableMap);
    }

    /**
     * Initialize {@link AutoRecorderController}.
     */
    private void initAutoRecorderController() {
        HiHealthOptions options = HiHealthOptions.builder().build();
        AuthHuaweiId signInHuaweiId = HuaweiIdAuthManager.getExtendedAuthResult(options);
        this.autoRecorderController = HuaweiHiHealth.getAutoRecorderController(getActivity(getCurrentActivity()),
            signInHuaweiId);
    }

    /**
     * Check whether autoRecorderController is initialized, or not.
     */
    private void checkAutoRecorderController() {
        if (this.autoRecorderController == null) {
            initAutoRecorderController();
        }
    }

    /* Private Inner Classes */

    /**
     * TaskVoidReqHelper final nested class is a helper class for reaching {@link TaskVoidResultListener}.
     */
    private final class TaskVoidReqHelper extends VoidResultHelper implements TaskVoidResultListener {

        private String message;

        private AutoRecorderConstants.OnCompleteEventType type;

        TaskVoidReqHelper(final Promise promise, final String message,
            final AutoRecorderConstants.OnCompleteEventType type, HMSLogger logger, String logName) {
            super(promise, logger, logName);
            this.message = message;
            this.type = type;
        }

        @Override
        public void onComplete(Task<Void> taskResult) {
            sendEvent(type, toWritableMapWithMessage(message, taskResult.isSuccessful()));
        }
    }

    /**
     * Broadcast receiver
     */
    public class AutoRecorderReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SamplePoint samplePoint = (SamplePoint) bundle.get("SamplePoint");
                sendEvent(reactContext, "onSamplePointListener", toWritableMap(samplePoint));
            }
        }
    }
}
