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

import com.huawei.hms.hihealth.ActivityRecordsController;
import com.huawei.hms.hihealth.data.ActivityRecord;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.hihealth.options.ActivityRecordDeleteOptions;
import com.huawei.hms.hihealth.options.ActivityRecordReadOptions;
import com.huawei.hms.hihealth.options.OnActivityRecordListener;
import com.huawei.hms.hihealth.result.ActivityRecordReply;
import com.huawei.hms.rn.health.foundation.listener.ResultListener;
import com.huawei.hms.rn.health.foundation.listener.VoidResultListener;

import com.facebook.react.bridge.ReactContext;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Blueprint of {@link ActivityRecordsViewModel}.
 *
 * @since v.5.0.1
 */
public interface ActivityRecordsService {
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
    void startActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecord activityRecord, final VoidResultListener listener);

    void startBackgroundActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecord activityRecord, OnActivityRecordListener activityRecordListener,
        ComponentName componentName, ReactContext reactContext, final VoidResultListener listener);

    /**
     * Applies for an activity record to continue in the background for another 10 minutes.
     *
     * @param activityRecordsController {@link ActivityRecordsController} instance.
     * @param activityRecordId Unique ID of an activity record.
     * @param listener {@link VoidResultListener} instance.
     */
    void continueActivityRecord(final ActivityRecordsController activityRecordsController,
        final String activityRecordId, final VoidResultListener listener);

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
    void endActivityRecord(final ActivityRecordsController activityRecordsController,
        final @Nullable String activityRecordId, final ResultListener<List> listener);

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
    void addActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecord activityRecord, final SampleSet sampleSet, final VoidResultListener listener);

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
    void addMultipleActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecord activityRecord, final List<SampleSet> sampleSetList, final VoidResultListener listener);

    /**
     * Delete the activity record.
     *
     * @param activityRecordsController {@link ActivityRecordsController} instance.
     * @param deleteOptions ActivityRecordDeleteOptions instance.
     * @param listener {@link ResultListener<ActivityRecordReply>} instance.
     */
    void deleteActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecordDeleteOptions deleteOptions, final VoidResultListener listener);

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
    void getActivityRecord(final ActivityRecordsController activityRecordsController,
        final ActivityRecordReadOptions readRequest, final ResultListener<ActivityRecordReply> listener);
}
