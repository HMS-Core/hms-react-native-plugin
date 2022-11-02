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

package com.huawei.hms.rn.health.kits.activityrecords.viewmodel;

import android.content.ComponentName;
import android.util.Log;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.hihealth.ActivityRecordsController;
import com.huawei.hms.hihealth.DataController;
import com.huawei.hms.hihealth.data.ActivityRecord;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.hihealth.options.ActivityRecordDeleteOptions;
import com.huawei.hms.hihealth.options.ActivityRecordInsertOptions;
import com.huawei.hms.hihealth.options.ActivityRecordReadOptions;
import com.huawei.hms.hihealth.options.DeleteOptions;
import com.huawei.hms.hihealth.options.OnActivityRecordListener;
import com.huawei.hms.hihealth.result.ActivityRecordReply;
import com.huawei.hms.rn.health.foundation.listener.ResultListener;
import com.huawei.hms.rn.health.foundation.listener.VoidResultListener;
import com.huawei.hms.rn.health.foundation.util.Utils;
import com.huawei.hms.rn.health.kits.activityrecords.HmsActivityRecordsController;
import com.huawei.hms.rn.health.kits.activityrecords.util.ActivityRecordsUtils;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.util.List;

/**
 * All the tasks for {@link ActivityRecordsController} methods
 * are used in {@link ActivityRecordsViewModel} class.
 *
 * @since v.5.0.1
 */
public class ActivityRecordsViewModel implements ActivityRecordsService {

    private static final String TAG = ActivityRecordsViewModel.class.getSimpleName();

    /**
     * Creating ActivityRecords in Real Time
     * <p>
     * Create ActivityRecords for ongoing workout activities.
     * The workout data during an active ActivityRecord is implicitly associated with the ActivityRecord on the Health platform.
     * <p>
     * Note: When the user initiates a workout activity, use the ActivityRecordsController.beginActivityRecord method to start an ActivityRecord.
     * </p>
     *
     * @param activityRecordsController {@link ActivityRecordsController} instance.
     * @param activityRecord {@link ActivityRecord} instance.
     * @param listener {@link VoidResultListener} instance.
     */
    @Override
    public void startActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecord activityRecord, final VoidResultListener listener) {
        Log.i(TAG, "call startActivityRecord");
        // Add a listener for the ActivityRecord start success
        Task<Void> beginTask = activityRecordsController.beginActivityRecord(activityRecord);

        // Add a listener for the ActivityRecord start failure
        beginTask.addOnSuccessListener(voidValue -> {
            Log.i(TAG, "startActivityRecord success");
            listener.onSuccess(voidValue);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "startActivityRecord error");
            listener.onFail(error);
        });
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
     * @param activityRecordsController {@link ActivityRecordsController} instance.
     * @param activityRecord {@link ActivityRecord} instance.
     * @param listener {@link VoidResultListener} instance.
     */
    @Override
    public void startBackgroundActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecord activityRecord, OnActivityRecordListener activityRecordListener,
        ComponentName componentName, ReactContext reactContext, final VoidResultListener listener) {
        Log.i(TAG, "call startBackgroundActivityRecord");
        // Add a listener for the ActivityRecord start success
        Task<Void> beginTask = activityRecordsController.beginActivityRecord(activityRecord, componentName,
            activityRecordListener);

        // Add a listener for the ActivityRecord start failure
        beginTask.addOnSuccessListener(voidValue -> {
            Log.i(TAG, "startBackgroundActivityRecord success");
            reactContext.startService(HmsActivityRecordsController.getForegroundServiceIntent());
            listener.onSuccess(voidValue);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "startBackgroundActivityRecord error");
            listener.onFail(error);
        });
    }

    /**
     * Applies for an activity record to continue in the background for another 10 minutes.
     *
     * @param activityRecordsController {@link ActivityRecordsController} instance.
     * @param activityRecordId Unique ID of an activity record.
     * @param listener {@link VoidResultListener} instance.
     */
    @ReactMethod
    public void continueActivityRecord(final ActivityRecordsController activityRecordsController,
        final String activityRecordId, final VoidResultListener listener) {
        Log.i(TAG, "call continueActivityRecord");

        // Add a listener for the ActivityRecord start success
        Task<Void> beginTask = activityRecordsController.continueActivityRecord(activityRecordId);

        // Add a listener for the ActivityRecord start failure
        beginTask.addOnSuccessListener(voidValue -> {
            Log.i(TAG, "continueActivityRecord success");
            listener.onSuccess(voidValue);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "continueActivityRecord error");
            listener.onFail(error);
        });

    }

    /**
     * Stop the ActivityRecord
     * <p>
     * The app uses the {@code HmsActivityRecordsController.endActivityRecord} method to stop a specified ActivityRecord.
     * <p>
     * Note: When the user stops a workout activity, use the {@code HmsActivityRecordsController.endActivityRecord} method to stop an ActivityRecord.
     * </p>
     *
     * @param activityRecordsController {@link ActivityRecordsController} instance.
     * @param activityRecordId the ID string of {@link ActivityRecord}.
     * @param listener List ActivityRecord instance.
     */
    @Override
    public void endActivityRecord(final ActivityRecordsController activityRecordsController,
        final String activityRecordId, final ResultListener<List> listener) {
        Log.i(TAG, "call endActivityRecord");
        // Call the related method of ActivityRecordsController to stop activity records.
        // The input parameter can be the ID string of ActivityRecord or null
        // Stop an activity record of the current app by specifying the ID string as the input parameter
        // Stop activity records of the current app by specifying null as the input parameter
        Task<List<ActivityRecord>> endTask = activityRecordsController.endActivityRecord(activityRecordId);
        endTask.addOnSuccessListener(activityRecords -> {
            // Return the list of activity records that have stopped
            Log.i(TAG, "endActivityRecord success");
            // Return the list of activity records that have stopped
            listener.onSuccess(activityRecords);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "endActivityRecord error");
            listener.onFail(error);
        });
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
     * @param activityRecordsController {@link ActivityRecordsController} instance.
     * @param activityRecord {@link ActivityRecord} instance.
     * @param sampleSet {@link SampleSet} instance.
     * @param listener {@link VoidResultListener} instance.
     */
    @Override
    public void addActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecord activityRecord, final SampleSet sampleSet, final VoidResultListener listener) {
        Log.i(TAG, "call addActivityRecord");
        // Build the activity record addition request object
        ActivityRecordInsertOptions insertRequest = new ActivityRecordInsertOptions.Builder().setActivityRecord(
            activityRecord).addSampleSet(sampleSet).build();

        // Call the related method in the ActivityRecordsController to add activity records
        Task<Void> addTask = activityRecordsController.addActivityRecord(insertRequest);
        addTask.addOnSuccessListener(voidValue -> {
            Log.i(TAG, "addActivityRecord success");
            listener.onSuccess(voidValue);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "addActivityRecord error");
            listener.onFail(error);
        });
    }

    /**
     * Inserting Multiple ActivityRecords to the Health Platform
     * </br>
     * To insert ActivityRecords with data that has been previously collected to the Health platform, perform the following:
     * 1. Create an ActivityRecord by specifying a time period and other necessary information.
     * 2. Create an ActivityRecordInsertOptions using the ActivityRecord and optional data set or grouped sampling point data.
     * 3. Use the ActivityRecordsController.addActivityRecord method to insert an ActivityRecordInsertOptions.
     * <p>
     * Note: The app uses the ActivityRecordsController.addActivityRecord method to insert the ActivityRecord and associated data to the Health platform.
     * </p>
     *
     * @param activityRecordsController {@link ActivityRecordsController} instance.
     * @param activityRecord {@link ActivityRecord} instance.
     * @param sampleSetList {@link SampleSet} instance.
     * @param listener {@link VoidResultListener} instance.
     */
    @Override
    public void addMultipleActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecord activityRecord, final List<SampleSet> sampleSetList, final VoidResultListener listener) {
        Log.i(TAG, "call addMultipleActivityRecord");
        // Build the activity record addition request object
        ActivityRecordInsertOptions.Builder builder = new ActivityRecordInsertOptions.Builder().setActivityRecord(
            activityRecord);

        for (SampleSet set : sampleSetList) {
            builder.addSampleSet(set);
        }

        // Call the related method in the ActivityRecordsController to add activity records
        Task<Void> addTask = activityRecordsController.addActivityRecord(builder.build());
        addTask.addOnSuccessListener(voidValue -> {
            Log.i(TAG, "addMultipleActivityRecord success");
            listener.onSuccess(voidValue);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "addMultipleActivityRecord error");
            listener.onFail(error);
        });
    }

    /**
     * Delete the activity record.
     *
     * @param activityRecordsController {@link ActivityRecordsController} instance.
     * @param deleteOptions ActivityRecordDeleteOptions instance.
     * @param listener {@link ResultListener<ActivityRecordReply>} instance.
     */
    @Override
    public void deleteActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecordDeleteOptions deleteOptions, final VoidResultListener listener) {
        Log.i(TAG, "call deleteActivityRecord");
        // Call the read method of the ActivityRecordsController to obtain activity records
        Task<Void> task = activityRecordsController.deleteActivityRecord(deleteOptions);
        task.addOnSuccessListener(activityRecordReply -> {
            Log.i("DeleteActivityRecords", "DeleteActivityRecords success");
            listener.onSuccess(activityRecordReply);
        }).addOnFailureListener(error -> {
            Log.i("DeleteActivityRecords", "DeleteActivityRecords error");
            listener.onFail(error);
        });
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
     * @param activityRecordsController {@link ActivityRecordsController} instance.
     * @param readRequest {@link ActivityRecordReadOptions} request.
     * @param listener {@link ResultListener<ActivityRecordReply>} instance.
     */
    @Override
    public void getActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecordReadOptions readRequest, final ResultListener<ActivityRecordReply> listener) {
        // Call the read method of the ActivityRecordsController to obtain activity records
        Log.i("ActivityRecords", "call getActivityRecord");
        // from the Health platform based on the conditions in the request body
        Task<ActivityRecordReply> getTask = activityRecordsController.getActivityRecord(readRequest);
        getTask.addOnSuccessListener(activityRecordReply -> {
            Log.i("ActivityRecords", "getActivityRecord success");
            listener.onSuccess(activityRecordReply);
        }).addOnFailureListener(error -> {
            Log.i("ActivityRecords", "getActivityRecord error");
            listener.onFail(error);
        });
    }

    /**
     * Deleting ActivityRecords and Associated Data from the Health Platform
     */
    private final static class DeleteActivityRecordHelper implements ResultListener<ActivityRecordReply> {
        /* Private Variables */
        private final ReadableMap dateReadableMap;

        private final DataController dataController;

        private final ResultListener<List> listener;

        /**
         * Calls the read method of the ActivityRecordsController to obtain activity records then deletes the requested activityRecords.
         *
         * @param dateReadableMap ReadableMap instance that will be referred into date.
         * @param dataController DataController instance.
         * @param listener ResultListener<String> returned with a requested activityRecordId.
         */
        DeleteActivityRecordHelper(final ReadableMap dateReadableMap, final DataController dataController,
            final ResultListener<List> listener) {
            this.dataController = dataController;
            this.dateReadableMap = dateReadableMap;
            this.listener = listener;
        }

        @Override
        public void onSuccess(ActivityRecordReply result) {
            Log.i(TAG, "Reading ActivityRecord  response status " + result.getStatus());
            List<ActivityRecord> activityRecords = result.getActivityRecords();

            // Get ActivityRecord and corresponding activity data in the result, then delete for the requested time and other data.
            for (final ActivityRecord activityRecord : activityRecords) {
                DeleteOptions deleteOptions = ActivityRecordsUtils.INSTANCE.toActivityDeleteOptions(activityRecord,
                    Utils.INSTANCE.toTimeUnit(dateReadableMap));
                Log.i(TAG, ("begin delete ActivityRecord is :" + activityRecord.getId()));
                // Delete ActivityRecord
                Task<Void> deleteTask = dataController.delete(deleteOptions);
                deleteTask.addOnSuccessListener(
                    voidValue -> Log.i(TAG, "delete ActivityRecord is Success:" + activityRecord.getId()))
                    .addOnFailureListener(error -> {
                        Log.i(TAG, "deleteActivityRecord error");
                        listener.onFail(error);
                    });
            }
            listener.onSuccess(activityRecords);
        }

        @Override
        public void onFail(Exception exception) {
            Log.i(TAG, "deleteActivityRecord error");
            listener.onFail(exception);
        }
    }
}
