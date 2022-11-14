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

package com.huawei.hms.rn.health.kits.healthrecordcontroller.viewmodel;

import android.util.Log;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.hihealth.HealthRecordController;
import com.huawei.hms.hihealth.data.Field;
import com.huawei.hms.hihealth.data.HealthRecord;
import com.huawei.hms.hihealth.data.SamplePoint;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.hihealth.options.HealthRecordDeleteOptions;
import com.huawei.hms.hihealth.options.HealthRecordInsertOptions;
import com.huawei.hms.hihealth.options.HealthRecordReadOptions;
import com.huawei.hms.hihealth.options.HealthRecordUpdateOptions;
import com.huawei.hms.hihealth.result.HealthRecordReply;
import com.huawei.hms.rn.health.foundation.helper.ResultHelper;
import com.huawei.hms.rn.health.foundation.helper.VoidResultHelper;
import com.huawei.hms.rn.health.foundation.listener.VoidResultListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @since 5.0.5-300
 * All the tasks for {@link HealthRecordController} methods
 * are used in {@link HealthRecordViewModel} class.
 */
public class HealthRecordViewModel implements HealthRecordService {

    private static final String TAG = HealthRecordViewModel.class.getSimpleName();

    private String healthRecordIdFromInsertResult = "";

    /**
     * @param healthRecordController {@link HealthRecordController} instance.
     * @param healthRecord {@link HealthRecord} instance.
     * @param resultHelper {@link VoidResultListener} instance.
     */
    @Override
    public void addHealthRecord(HealthRecordController healthRecordController, final HealthRecord healthRecord,
        ResultHelper resultHelper) {
        HealthRecordInsertOptions insertOptions = new HealthRecordInsertOptions.Builder().setHealthRecord(healthRecord)
            .build();

        Task<String> addTask = healthRecordController.addHealthRecord(insertOptions);

        addTask.addOnSuccessListener(healthRecordId -> {
            healthRecordIdFromInsertResult = healthRecordId;
            resultHelper.onSuccess(healthRecordIdFromInsertResult);
        }).addOnFailureListener(e -> {
            Log.i("addHealthRecord: ", e.getMessage());
            resultHelper.onFail(e);
        });
    }

    /**
     * @param healthRecordController {@link HealthRecordController} instance.
     * @param healthRecord {@link HealthRecord} instance.
     * @param resultHelper {@link VoidResultListener} instance.
     */
    @Override
    public void updateHealthRecord(HealthRecordController healthRecordController, HealthRecord healthRecord,
        VoidResultHelper resultHelper) {
        if (healthRecordIdFromInsertResult.equals("")) {
            Log.e("updateHealthRecord",
                "Health record id is empty. Call addHealthRecord function before calling this function");
            resultHelper.sendFail(
                "Health record id is empty. Call addHealthRecord function before calling this function");
            return;
        }

        HealthRecordUpdateOptions updateOptions = new HealthRecordUpdateOptions.Builder().setHealthRecord(healthRecord)
            .setHealthRecordId(healthRecordIdFromInsertResult)
            .build();

        Task<Void> updateTask = healthRecordController.updateHealthRecord(updateOptions);

        updateTask.addOnSuccessListener(aVoid -> resultHelper.onSuccess(aVoid))
            .addOnFailureListener(e -> resultHelper.onFail(e));
    }

    /**
     * @param healthRecordController {@link HealthRecordController} instance.
     * @param healthRecordReadOptions {@link HealthRecordReadOptions} instance.
     * @param resultHelper {@link VoidResultListener} instance.
     */
    @Override
    public void getHealthRecord(HealthRecordController healthRecordController,
        HealthRecordReadOptions healthRecordReadOptions, ResultHelper resultHelper) {

        Task<HealthRecordReply> task = healthRecordController.getHealthRecord(healthRecordReadOptions);

        task.addOnSuccessListener(readResponse -> {
            List<HealthRecord> recordList = readResponse.getHealthRecords();
            for (HealthRecord record : recordList) {
                if (record == null) {
                    continue;
                }
                dumpHealthRecord(record);
                for (SampleSet dataSet : record.getSubDataDetails()) {
                    dumpDataSet(dataSet);
                }
                resultHelper.onSuccess(readResponse);
            }
        });
        task.addOnFailureListener(e -> resultHelper.onFail(e));
    }

    /**
     * Deletes health records based on the request parameters.
     *
     * @param healthRecordController {@link HealthRecordController} instance.
     * @param deleteOptions Request for querying health records to be deleted.
     * @param resultHelper {@link VoidResultListener} instance.
     */
    @Override
    public void deleteHealthRecord(HealthRecordController healthRecordController,
        HealthRecordDeleteOptions deleteOptions, VoidResultHelper resultHelper) {
        Task<Void> task = healthRecordController.deleteHealthRecord(deleteOptions);
        task.addOnSuccessListener(activityRecordReply -> {
            Log.i("DeleteHealthRecord", "DeleteHealthRecord success");
            resultHelper.onSuccess(activityRecordReply);
        }).addOnFailureListener(error -> {
            Log.i("DeleteHealthRecord", "DeleteHealthRecord error");
            resultHelper.onFail(error);
        });
    }

    private void dumpHealthRecord(HealthRecord healthRecord) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        DateFormat timeFormat = DateFormat.getTimeInstance();
        if (healthRecord != null) {
            logger("\tHealthRecordIdentifier: " + healthRecord.getHealthRecordId() + "\n\tpackageName: "
                + healthRecord.getDataCollector().getPackageName() + "\n\tStartTime: " + dateFormat.format(
                healthRecord.getStartTime(TimeUnit.MILLISECONDS)) + " " + timeFormat.format(
                healthRecord.getStartTime(TimeUnit.MILLISECONDS)) + "\n\tEndTime: " + dateFormat.format(
                healthRecord.getEndTime(TimeUnit.MILLISECONDS)) + " " + timeFormat.format(
                healthRecord.getEndTime(TimeUnit.MILLISECONDS)) + "\n\tHealthRecordDataType: "
                + healthRecord.getDataCollector().getDataType().getName() + "\n\tHealthRecordDataCollectorId: "
                + healthRecord.getDataCollector().getDataStreamId() + "\n\tmetaData: " + healthRecord.getMetadata()
                + "\n\tFileValueMap: " + healthRecord.getFieldValues());

            if (healthRecord.getSubDataSummary() != null && !healthRecord.getSubDataSummary().isEmpty()) {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                for (SamplePoint samplePoint : healthRecord.getSubDataSummary()) {
                    logger("Sample point type: " + samplePoint.getDataType().getName());
                    logger("Start: " + sDateFormat.format(new Date(samplePoint.getStartTime(TimeUnit.MILLISECONDS))));
                    logger("End: " + sDateFormat.format(new Date(samplePoint.getEndTime(TimeUnit.MILLISECONDS))));
                    for (Field field : samplePoint.getDataType().getFields()) {
                        logger("Field: " + field.getName() + " Value: " + samplePoint.getFieldValue(field));
                    }
                    logger(System.lineSeparator());
                }
            }
        }
    }

    private void dumpDataSet(SampleSet sampleSet) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (SamplePoint samplePoint : sampleSet.getSamplePoints()) {
            logger("Sample point type: " + samplePoint.getDataType().getName());
            logger("Start: " + dateFormat.format(new Date(samplePoint.getStartTime(TimeUnit.MILLISECONDS))));
            logger("End: " + dateFormat.format(new Date(samplePoint.getEndTime(TimeUnit.MILLISECONDS))));
            for (Field field : samplePoint.getDataType().getFields()) {
                logger("Field: " + field.getName() + " Value: " + samplePoint.getFieldValue(field));
            }
        }
    }

    private void logger(String string) {
        Log.i(TAG, string);
    }
}