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

package com.huawei.hms.rn.health.kits.activityrecords;

import static com.huawei.hms.rn.health.kits.activityrecords.util.ActivityRecordsConstants.ACTIVITY_CONSTANTS_MAP;
import static com.huawei.hms.rn.health.kits.autorecorder.utils.AutoRecorderConstants.BACKGROUND_SERVICE_KEY;

import android.content.ComponentName;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.huawei.hms.hihealth.ActivityRecordsController;
import com.huawei.hms.hihealth.DataController;
import com.huawei.hms.hihealth.HiHealthOptions;
import com.huawei.hms.hihealth.HiHealthStatusCodes;
import com.huawei.hms.hihealth.HuaweiHiHealth;
import com.huawei.hms.hihealth.data.ActivityRecord;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.hihealth.options.ActivityRecordDeleteOptions;
import com.huawei.hms.hihealth.options.ActivityRecordReadOptions;
import com.huawei.hms.hihealth.options.OnActivityRecordListener;
import com.huawei.hms.hihealth.result.ActivityRecordReply;
import com.huawei.hms.rn.health.foundation.helper.ResultHelper;
import com.huawei.hms.rn.health.foundation.helper.VoidResultHelper;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;
import com.huawei.hms.rn.health.foundation.util.Utils;
import com.huawei.hms.rn.health.foundation.view.BaseProtocol;
import com.huawei.hms.rn.health.kits.activityrecords.util.ActivityRecordsUtils;
import com.huawei.hms.rn.health.kits.activityrecords.viewmodel.ActivityRecordsService;
import com.huawei.hms.rn.health.kits.activityrecords.viewmodel.ActivityRecordsViewModel;
import com.huawei.hms.rn.health.kits.autorecorder.utils.AutoRecorderBackgroundService;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * {@link HmsActivityRecordsController} class is a module that refers to {@link ActivityRecordsController}
 * <p>
 * Create ActivityRecords for ongoing workout activities.
 * The workout data during an active ActivityRecord is implicitly associated with the ActivityRecord on the Health platform.
 *
 * <p>
 * Note: When the user initiates a workout activity, use the {@code ActivityRecordsController.beginActivityRecord} method to start an ActivityRecord.
 * When the user stops a workout activity, use the {@code ActivityRecordsController.endActivityRecord} method to stop an ActivityRecord.
 * </p>
 *
 * @since v.5.0.1
 */
public class HmsActivityRecordsController extends ReactContextBaseJavaModule implements BaseProtocol.Event {

    private static final String TAG = HmsActivityRecordsController.class.getSimpleName();

    // Internal context object
    public static ReactContext reactContext;

    // ActivityRecordsController for managing activity records
    private ActivityRecordsController activityRecordsController;

    // DataController for deleting activity records
    private DataController dataController;

    // ViewModel instance to reach ActivityRecordsController tasks
    private final ActivityRecordsService activityRecordsViewModel;

    private final HMSLogger logger;

    private final Intent serviceIntent;

    /**
     * Initialization
     */
    public HmsActivityRecordsController(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.activityRecordsViewModel = new ActivityRecordsViewModel();
        serviceIntent = new Intent(reactContext, AutoRecorderBackgroundService.class);
        serviceIntent.setPackage(reactContext.getPackageName());
        serviceIntent.setAction(BACKGROUND_SERVICE_KEY);
        logger = HMSLogger.getInstance(reactContext);
        initActivityRecordsController();
    }

    /**
     * Creating ActivityRecords in Real Time
     * <p>
     * Create ActivityRecords for ongoing workout activities.
     * The workout data during an active ActivityRecord is implicitly associated with the ActivityRecord on the Health platform.
     * <p>
     * Note: When the user initiates a workout activity, use the ActivityRecordsController.beginActivityRecord method to start an ActivityRecord.
     * </p>
     *
     * @param readableMap ReadableMap instance that refers to {@link ActivityRecord} instance.
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void beginActivityRecord(final ReadableMap readableMap, final Promise promise) {
        String logName = "HmsActivityRecordsController.beginActivityRecord";
        logger.startMethodExecutionTimer(logName);
        checkActivityRecordsController();
        // Build an ActivityRecord object
        ActivityRecord activityRecord = ActivityRecordsUtils.INSTANCE.toActivityRecord(readableMap, reactContext,
            promise);

        // Calling beginActivity.
        activityRecordsViewModel.startActivityRecord(this.activityRecordsController, activityRecord,
            new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Starts an activity record that can run in the background and allows it to continue for 10 minutes by default.
     * <p>
     * Create ActivityRecords for ongoing workout activities.
     * The workout data during an active ActivityRecord is implicitly associated with the ActivityRecord on the Health platform.
     * <p>
     * Note: When the user initiates a workout activity, use the ActivityRecordsController.beginActivityRecord method to start an ActivityRecord.
     * </p>
     *
     * @param readableMap ReadableMap instance that refers to {@link ActivityRecord} instance.
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void beginBackgroundActivityRecord(final ReadableMap readableMap, final Promise promise) {
        String logName = "HmsActivityRecordsController.beginBackgroundActivityRecord";
        logger.startMethodExecutionTimer(logName);
        checkActivityRecordsController();
        // Build an ActivityRecord object
        ActivityRecord activityRecord = ActivityRecordsUtils.INSTANCE.toActivityRecord(readableMap, reactContext,
            promise);

        ComponentName componentName = new ComponentName(reactContext, HmsActivityRecordsController.class);

        OnActivityRecordListener activityRecordListener = new OnActivityRecordListener() {
            @Override
            public void onStatusChange(int statusCode) {

                if (HiHealthStatusCodes.WORK_OUT_TIME_OUT == statusCode
                    || HiHealthStatusCodes.WORK_OUT_BE_OCCUPIED == statusCode) {
                    reactContext.stopService(getForegroundServiceIntent());
                }
            }
        };

        // Calling beginActivity.
        activityRecordsViewModel.startBackgroundActivityRecord(this.activityRecordsController, activityRecord,
            activityRecordListener, componentName, reactContext, new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Applies for an activity record to continue in the background for another 10 minutes.
     *
     * @param activityRecordId Unique ID of an activity record.
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void continueActivityRecord(final String activityRecordId, final Promise promise) {
        String logName = "HmsActivityRecordsController.continueActivityRecord";
        logger.startMethodExecutionTimer(logName);
        checkActivityRecordsController();

        // Calling continueActivityRecord.
        activityRecordsViewModel.continueActivityRecord(this.activityRecordsController, activityRecordId,
            new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Stop the ActivityRecord
     * <p>
     * The app uses the {@code HmsActivityRecordsController.endActivityRecord} method to stop a specified ActivityRecord.
     * <p>
     * Note: When the user stops a workout activity, use the {@code HmsActivityRecordsController.endActivityRecord} method to stop an ActivityRecord.
     * </p>
     *
     * @param activityRecordId the ID string of {@link ActivityRecord} or null
     * @param promise In the success scenario, {@link List<ActivityRecord>} instance is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void endActivityRecord(final @Nullable String activityRecordId, final Promise promise) {
        String logName = "HmsActivityRecordsController.endActivityRecord";
        logger.startMethodExecutionTimer(logName);

        checkActivityRecordsController();

        // Call the related method of ActivityRecordsController to stop activity records.
        // The input parameter can be the ID string of ActivityRecord or null
        // Stop an activity record of the current app by specifying the ID string as the input parameter
        // Stop activity records of the current app by specifying null as the input parameter
        // Return the list of activity records that have stopped
        activityRecordsViewModel.endActivityRecord(this.activityRecordsController, activityRecordId,
            new ResultHelper<>(List.class, promise, logger, logName));
    }

    @ReactMethod
    public void endBackgroundActivityRecord(final @Nullable String activityRecordId, final Promise promise) {
        String logName = "HmsActivityRecordsController.endBackgroundActivityRecord";
        logger.startMethodExecutionTimer(logName);

        checkActivityRecordsController();

        reactContext.stopService(getForegroundServiceIntent());

        // Call the related method of ActivityRecordsController to stop activity records.
        // The input parameter can be the ID string of ActivityRecord or null
        // Stop an activity record of the current app by specifying the ID string as the input parameter
        // Stop activity records of the current app by specifying null as the input parameter
        // Return the list of activity records that have stopped
        activityRecordsViewModel.endActivityRecord(this.activityRecordsController, activityRecordId,
            new ResultHelper<>(List.class, promise, logger, logName));
    }

    /**
     * Stop activity records of the current app by calling {@code HmsActivityRecordsController.endAllActivityRecords}.
     * <p>
     * The app uses the {@code HmsActivityRecordsController.endAllActivityRecords} method to stop all the activity records.
     * <p>
     * Note: When ending all activity records, use the {@code HmsActivityRecordsController.endAllActivityRecords} method to stop an ActivityRecord.
     * </p>
     *
     * @param promise In the success scenario, {@link List<ActivityRecord>} instance is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void endAllActivityRecords(final Promise promise) {
        String logName = "HmsActivityRecordsController.endAllActivityRecords";
        logger.startMethodExecutionTimer(logName);

        checkActivityRecordsController();

        // Call the related method of ActivityRecordsController to stop activity records.
        // The input parameter can be the ID string of ActivityRecord or null
        // Stop an activity record of the current app by specifying the ID string as the input parameter
        // Stop activity records of the current app by specifying null as the input parameter
        activityRecordsViewModel.endActivityRecord(this.activityRecordsController, null,
            new ResultHelper<>(List.class, promise, logger, logName));
    }

    /**
     * Inserting ActivityRecords to the Health Platform
     * </br>
     * To insert ActivityRecords with data that has been previously collected to the Health platform, perform the following:
     * 1. Create an ActivityRecord by specifying a time period and other necessary information.
     * 2. Create an ActivityRecordInsertOptions using the ActivityRecord and optional data set or grouped sampling point data.
     * 3. Use the ActivityRecordsController.addActivityRecord method to insert an ActivityRecordInsertOptions.
     * <p>
     * Note: The app uses the ActivityRecordsController.addActivityRecord method to insert the ActivityRecord and associated data to the Health platform.
     * </p>
     *
     * @param activityRecordReadableMap ReadableMap instance that refers to {@link ActivityRecord} instance.
     * @param sampleSetMap ReadableMap instance that refers to {@link SampleSet} instance.
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void addActivityRecord(final ReadableMap activityRecordReadableMap, final ReadableMap sampleSetMap,
        final Promise promise) {
        String logName = "HmsActivityRecordsController.addActivityRecord";
        logger.startMethodExecutionTimer(logName);

        checkActivityRecordsController();

        // Build the time range of the request object: start time and end time
        // Build the activity record request object
        ActivityRecord activityRecord = ActivityRecordsUtils.INSTANCE.toActivityRecord(activityRecordReadableMap,
            reactContext, promise);

        // Build the sampling sampleSet based on the dataCollector
        // Build the data type and add it to the sampling dataSet
        SampleSet sampleSet = Utils.INSTANCE.toSampleSet(sampleSetMap, reactContext, promise);

        activityRecordsViewModel.addActivityRecord(this.activityRecordsController, activityRecord, sampleSet,
            new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Inserting ActivityRecords to the Health Platform
     * </br>
     * To insert ActivityRecords with data that has been previously collected to the Health platform, perform the following:
     * 1. Create an ActivityRecord by specifying a time period and other necessary information.
     * 2. Create an ActivityRecordInsertOptions using the ActivityRecord and optional data set or grouped sampling point data.
     * 3. Use the ActivityRecordsController.addActivityRecord method to insert an ActivityRecordInsertOptions.
     * <p>
     * Note: The app uses the ActivityRecordsController.addActivityRecord method to insert the ActivityRecord and associated data to the Health platform.
     * </p>
     *
     * @param activityRecordReadableMap ReadableMap instance that refers to {@link ActivityRecord} instance.
     * @param sampleSetMapArr ReadableMap instance that refers to {@link SampleSet} instance.
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void addMultipleActivityRecords(final ReadableMap activityRecordReadableMap,
        final ReadableArray sampleSetMapArr, final Promise promise) {
        String logName = "HmsActivityRecordsController.addMultipleActivityRecords";
        logger.startMethodExecutionTimer(logName);

        checkActivityRecordsController();

        // Build the time range of the request object: start time and end time
        // Build the activity record request object
        ActivityRecord activityRecord = ActivityRecordsUtils.INSTANCE.toActivityRecord(activityRecordReadableMap,
            reactContext, promise);

        // Build the sampling sampleSet based on the dataCollector
        // Build the data type and add it to the sampling dataSet
        List<SampleSet> sampleSetList = Utils.INSTANCE.toSampleSetList(sampleSetMapArr, reactContext, promise);

        activityRecordsViewModel.addMultipleActivityRecord(this.activityRecordsController, activityRecord,
            sampleSetList, new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Reading ActivityRecords and Associated Data from the Health Platform
     * </br>
     * To obtain a list of ActivityRecords that meet the criteria, create an ActivityRecordReadOptions instance first.
     * Use the ActivityRecordsController.getActivityRecord method to obtain data.
     * <p>
     * Note: The user can obtain a list of ActivityRecords and associated data that meets certain criteria from the Health platform.
     * For example, you can obtain all ActivityRecords within a specific period of time for particular data, or obtain a specific ActivityRecord by name or ID.
     * You can also obtain ActivityRecords created by other apps.
     * </p>
     *
     * @param dataTypeMap ReadableMap instance that refers to {@link DataType} instance.
     * @param dateMap ReadableMap instance that refers to startTime, endTime and timeUnit params.
     * @param promise In the success scenario, {@link List<ActivityRecord>} instance is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void getActivityRecord(final ReadableMap dataTypeMap, final ReadableMap dateMap,
        final @Nullable String activityRecordId, final @Nullable String activityRecordName, final Promise promise) {
        String logName = "HmsActivityRecordsController.getActivityRecord";
        logger.startMethodExecutionTimer(logName);

        checkActivityRecordsController();

        // Build the request body for reading activity records
        ActivityRecordReadOptions readRequest = ActivityRecordsUtils.INSTANCE.toActivityRecordReadOptions(dataTypeMap,
            dateMap, activityRecordId, activityRecordName, promise);

        // Get the requested ActivityRecords
        activityRecordsViewModel.getActivityRecord(this.activityRecordsController, readRequest,
            new ResultHelper<>(ActivityRecordReply.class, promise, logger, logName));

    }

    /**
     * Delete the activity record.
     *
     * @param readableMap ReadableMap instance that refers to ActivityRecordDeleteOptions instance.
     * @param promise In the success scenario, {@link String} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void deleteActivityRecord(final ReadableMap readableMap, final Promise promise) {
        String logName = "HmsActivityRecordsController.deleteActivityRecord";
        logger.startMethodExecutionTimer(logName);
        checkActivityRecordsController();

        ActivityRecordDeleteOptions deleteOptions = ActivityRecordsUtils.INSTANCE.toActivityRecordDeleteOptions(
            readableMap, promise);

        activityRecordsViewModel.deleteActivityRecord(this.activityRecordsController, deleteOptions,
            new VoidResultHelper(promise, logger, logName));

    }

    /**
     * {@link HmsActivityRecordsController} class name.
     *
     * @return class name
     */
    @NonNull
    @Override
    public String getName() {
        return TAG;
    }

    /**
     * getConstants exposes the constant values
     * to RN Side.
     *
     * @return constants
     */
    @Override
    public Map<String, Object> getConstants() {
        return ACTIVITY_CONSTANTS_MAP;
    }

    /**
     * Sends event to RN Side.
     *
     * @param reactContext ReactContext instance.
     * @param eventName Event name that will be available via {@link HmsActivityRecordsController}.
     * @param params Event params.
     */
    @Override
    public void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit("addActivityRecordsMonitor", params);
    }

    /* Private Methods */

    /**
     * Check whether activityRecordsController is initialized, or not.
     */
    private void checkActivityRecordsController() {
        if (this.activityRecordsController == null || this.dataController == null) {
            initActivityRecordsController();
        }
    }

    /**
     * Initialize {@link ActivityRecordsController}.
     */
    private void initActivityRecordsController() {
        HiHealthOptions hiHealthOptions = HiHealthOptions.builder().build();
        AuthHuaweiId signInHuaweiId = HuaweiIdAuthManager.getExtendedAuthResult(hiHealthOptions);
        dataController = HuaweiHiHealth.getDataController(reactContext, signInHuaweiId);
        activityRecordsController = HuaweiHiHealth.getActivityRecordsController(reactContext, signInHuaweiId);
    }

    public static Intent getForegroundServiceIntent() {

        Intent intent = new Intent();
        intent.setClassName(reactContext.getPackageName(),
            "com.huawei.hms.rn.health.kits.activityrecords.util.ActivityRecordBackgroundService");
        return intent;
    }

}
