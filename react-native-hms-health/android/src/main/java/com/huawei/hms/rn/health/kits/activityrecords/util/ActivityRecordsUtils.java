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

package com.huawei.hms.rn.health.kits.activityrecords.util;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.hihealth.data.ActivityRecord;
import com.huawei.hms.hihealth.data.ActivitySummary;
import com.huawei.hms.hihealth.data.DataCollector;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.PaceSummary;
import com.huawei.hms.hihealth.data.SamplePoint;
import com.huawei.hms.hihealth.options.ActivityRecordReadOptions;
import com.huawei.hms.hihealth.options.DeleteOptions;
import com.huawei.hms.rn.health.foundation.constant.Constants;
import com.huawei.hms.rn.health.foundation.util.MapUtils;
import com.huawei.hms.rn.health.foundation.util.Utils;
import com.huawei.hms.rn.health.kits.activityrecords.HmsActivityRecordsController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import static com.huawei.hms.rn.health.foundation.constant.Constants.activityRecordIdKey;
import static com.huawei.hms.rn.health.foundation.constant.Constants.activityTypeKey;
import static com.huawei.hms.rn.health.foundation.constant.Constants.descriptionKey;
import static com.huawei.hms.rn.health.foundation.constant.Constants.durationTimeKey;
import static com.huawei.hms.rn.health.foundation.constant.Constants.endTimeKey;
import static com.huawei.hms.rn.health.foundation.constant.Constants.nameKey;
import static com.huawei.hms.rn.health.foundation.constant.Constants.startTimeKey;
import static com.huawei.hms.rn.health.foundation.constant.Constants.timeZoneKey;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toArrayList;

/**
 * ActivityRecordsUtils exposes a set of helper methods for working with
 * {@link HmsActivityRecordsController}.
 *
 * @since v.5.0.1
 */
public enum ActivityRecordsUtils {
    INSTANCE;

    /**
     * ActivityBuilder Record Types
     */
    enum RecordTypes {
        ID,
        NAME
    }

    /**
     * Looks for each key and converts ReadableMap instance into {@link ActivityRecord} instance.
     *
     * @param readableMap ReadableMap instance that will be converted.
     * @return {@link ActivityRecord} instance.
     */
    public synchronized ActivityRecord toActivityRecord(final ReadableMap readableMap, final ReactContext context, final Promise promise) {
        // Build an ActivityRecord object
        ActivityRecord.Builder builder = new ActivityRecord.Builder();

        builder.setId(Utils.INSTANCE.createEmptyStringIfNull(readableMap, activityRecordIdKey));
        builder.setName(Utils.INSTANCE.createEmptyStringIfNull(readableMap, nameKey));
        builder.setDesc(Utils.INSTANCE.createEmptyStringIfNull(readableMap, descriptionKey));
        builder.setActivityTypeId(Utils.INSTANCE.createEmptyStringIfNull(readableMap, activityTypeKey));
        builder.setTimeZone(Utils.INSTANCE.createEmptyStringIfNull(readableMap, timeZoneKey));
        setBuilderTime(builder, readableMap, Constants.TimeConstants.START);
        setBuilderTime(builder, readableMap, Constants.TimeConstants.END);
        setBuilderTime(builder, readableMap, Constants.TimeConstants.DURATION);


        if (Utils.INSTANCE.hasKey(readableMap, "activitySummary")) {
            builder.setActivitySummary(toActivitySummary(readableMap.getMap("activitySummary"), context, promise));
        }
        return builder.build();
    }

    /**
     * Converts into {@link ActivityRecordReadOptions} instance.
     */
    public synchronized ActivityRecordReadOptions toActivityRecordReadOptions(final ReadableMap dataTypeMap, final ReadableMap dateReadableMap, final @Nullable String activityRecordId, final @Nullable String activityRecordName, final Promise promise) {
        DataType dataType = Utils.INSTANCE.toDataType(dataTypeMap);

        ActivityRecordReadOptions.Builder builder = new ActivityRecordReadOptions.Builder();
        // Build the time range of the request object: start time and end time
        Date startDate = Utils.INSTANCE.toDate(Constants.TimeConstants.START, null, dateReadableMap, promise);
        Date endDate = Utils.INSTANCE.toDate(Constants.TimeConstants.END, null, dateReadableMap, promise);

        if (startDate != null && endDate != null && dataType != null) {
            builder.setTimeInterval(startDate.getTime(), endDate.getTime(),
                    Utils.INSTANCE.toTimeUnit(dateReadableMap))
                    .readActivityRecordsFromAllApps().read(dataType);
        }

        setBuilderRecord(builder, activityRecordId, RecordTypes.ID);
        setBuilderRecord(builder, activityRecordName, RecordTypes.NAME);
        return builder.build();
    }

    /**
     * Converts into {@link DeleteOptions} instance.
     */
    public synchronized DeleteOptions toActivityDeleteOptions(final ActivityRecord activityRecord, final TimeUnit timeUnit) {
        return new DeleteOptions.Builder().addActivityRecord(activityRecord)
                .setTimeInterval(activityRecord.getStartTime(timeUnit),
                        activityRecord.getEndTime(timeUnit), timeUnit)
                .build();
    }

    /* Private Methods */

    /**
     * Sets {@link ActivityRecord.Builder} Time
     */
    private synchronized void setBuilderTime(ActivityRecord.Builder builder, final ReadableMap readableMap, final Constants.TimeConstants time) {
        switch (time) {
            case START:
                Date startDate = Utils.INSTANCE.toDate(readableMap, startTimeKey);
                if (startDate != null) {
                    builder.setStartTime(startDate.getTime(),
                            Utils.INSTANCE.toTimeUnit(readableMap));
                }
                break;
            case END:
                Date endDate = Utils.INSTANCE.toDate(readableMap, endTimeKey);
                if (endDate != null) {
                    builder.setEndTime(endDate.getTime(),
                            Utils.INSTANCE.toTimeUnit(readableMap));
                }
                break;
            case DURATION:
                if (readableMap.hasKey(durationTimeKey)) {
                    builder.setDurationTime((long) readableMap.getInt(durationTimeKey), Utils.INSTANCE.toTimeUnit(readableMap));
                }
                break;
            default:
                break;
        }
    }

    /**
     * Sets {@link ActivityRecordReadOptions.Builder} Records
     */
    private synchronized void setBuilderRecord(final ActivityRecordReadOptions.Builder builder, final @Nullable String recordVal, final RecordTypes types) {
        if (recordVal == null) {
            return;
        }

        switch (types) {
            case ID:
                builder.setActivityRecordId(recordVal);
                return;
            case NAME:
                builder.setActivityRecordName(recordVal);
        }
    }

    /**
     * Converts the ReadableMap into ActivitySummary
     *
     * @param readableMap ReadableMap Object
     * @param context     Package context of the app for data collector
     * @param promise     Promise for giving error if a parsing error occurs in time
     * @return ActivitySummary Object
     */
    private ActivitySummary toActivitySummary(ReadableMap readableMap, final ReactContext context, final Promise promise) {
        ActivitySummary activitySummary = new ActivitySummary();
        if (Utils.INSTANCE.hasKey(readableMap, "paceSummary")) {
            activitySummary.setPaceSummary(toPaceSummary(readableMap.getMap("paceSummary")));
        }
        if (Utils.INSTANCE.hasKey(readableMap, "dataSummary")) {
            activitySummary.setDataSummary(toDataSummary(readableMap.getMap("dataSummary"), context, promise));
        }

        return activitySummary;
    }

    /**
     * Converts ReadableMap Object to List of SamplePoint objects in DataSummary
     *
     * @param dataSummaryMap ReadableMap Object
     * @param context        Package context of the app for data collector
     * @param promise        Promise for giving error if a parsing error occurs in time
     * @return List of SamplePoint Object
     */
    private synchronized List<SamplePoint> toDataSummary(final ReadableMap dataSummaryMap, final ReactContext context, final Promise promise) {
        List<SamplePoint> dataSummary = new ArrayList<>();
        if (dataSummaryMap != null && Utils.INSTANCE.hasKey(dataSummaryMap, "dataCollector")) {
            DataCollector dataCollector = Utils.INSTANCE.toDataCollector(dataSummaryMap.getMap("dataCollector"), context);
            List<Object> samplePointList = toArrayList(dataSummaryMap.getArray("samplePoints"));
            if (dataCollector != null) {
                for (Object samplePointObj : samplePointList) {
                    Map<String, Object> samplePointMap = (Map<String, Object>) samplePointObj;
                    SamplePoint samplePoint = Utils.INSTANCE.toSamplePoint(dataCollector, samplePointMap, promise);
                    dataSummary.add(samplePoint);
                }
            }
        }

        return dataSummary;
    }

    /**
     * Converts ReadableArray to PaceSummary Object
     *
     * @param readableMap ReadableMap Object
     * @return PaceSummary Object
     */
    private synchronized PaceSummary toPaceSummary(final ReadableMap readableMap) {
        PaceSummary paceSummary = new PaceSummary();
        if (Utils.INSTANCE.hasKey(readableMap, "avgPace")) {
            paceSummary.setAvgPace(readableMap.getDouble("avgPace"));
        }
        if (Utils.INSTANCE.hasKey(readableMap, "bestPace")) {
            paceSummary.setBestPace(readableMap.getDouble("bestPace"));
        }
        if (Utils.INSTANCE.hasKey(readableMap, "paceMap")) {
            paceSummary.setPaceMap((Map<String, Double>) MapUtils.toMap(readableMap.getMap("paceMap")));
        }
        if (Utils.INSTANCE.hasKey(readableMap, "britishPaceMap")) {
            paceSummary.setBritishPaceMap((Map<String, Double>) MapUtils.toMap(readableMap.getMap("britishPaceMap")));
        }
        if (Utils.INSTANCE.hasKey(readableMap, "partTimeMap")) {
            paceSummary.setPartTimeMap((Map<String, Double>) MapUtils.toMap(readableMap.getMap("partTimeMap")));
        }
        if (Utils.INSTANCE.hasKey(readableMap, "britishPartTimeMap")) {
            paceSummary.setBritishPartTimeMap((Map<String, Double>) MapUtils.toMap(readableMap.getMap("britishPartTimeMap")));
        }
        if (Utils.INSTANCE.hasKey(readableMap, "sportHealthPaceMap")) {
            paceSummary.setSportHealthPaceMap((Map<String, Double>) MapUtils.toMap(readableMap.getMap("sportHealthPaceMap")));
        }

        return paceSummary;
    }

}
