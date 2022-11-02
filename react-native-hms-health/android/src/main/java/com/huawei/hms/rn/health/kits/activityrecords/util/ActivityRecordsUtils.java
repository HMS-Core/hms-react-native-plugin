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

package com.huawei.hms.rn.health.kits.activityrecords.util;

import static com.huawei.hms.rn.health.foundation.constant.Constants.ACTIVITY_RECORD_ID_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.ACTIVITY_TYPE_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.DESCRIPTION_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.DURATION_TIME_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.END_TIME_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.NAME_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.START_TIME_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.TIME_ZONE_KEY;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toArray;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toArrayList;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toList;

import com.huawei.hms.hihealth.data.ActivityRecord;
import com.huawei.hms.hihealth.data.ActivitySummary;
import com.huawei.hms.hihealth.data.DataCollector;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.DeviceInfo;
import com.huawei.hms.hihealth.data.PaceSummary;
import com.huawei.hms.hihealth.data.SamplePoint;
import com.huawei.hms.hihealth.options.ActivityRecordDeleteOptions;
import com.huawei.hms.hihealth.options.ActivityRecordReadOptions;
import com.huawei.hms.hihealth.options.DeleteOptions;
import com.huawei.hms.rn.health.foundation.constant.Constants;
import com.huawei.hms.rn.health.foundation.util.MapUtils;
import com.huawei.hms.rn.health.foundation.util.Utils;
import com.huawei.hms.rn.health.kits.activityrecords.HmsActivityRecordsController;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

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
    public synchronized ActivityRecord toActivityRecord(final ReadableMap readableMap, final ReactContext context,
        final Promise promise) {
        // Build an ActivityRecord object
        ActivityRecord.Builder builder = new ActivityRecord.Builder();

        builder.setId(Utils.INSTANCE.createEmptyStringIfNull(readableMap, ACTIVITY_RECORD_ID_KEY));
        builder.setName(Utils.INSTANCE.createEmptyStringIfNull(readableMap, NAME_KEY));
        builder.setDesc(Utils.INSTANCE.createEmptyStringIfNull(readableMap, DESCRIPTION_KEY));
        builder.setActivityTypeId(Utils.INSTANCE.createEmptyStringIfNull(readableMap, ACTIVITY_TYPE_KEY));
        builder.setTimeZone(Utils.INSTANCE.createEmptyStringIfNull(readableMap, TIME_ZONE_KEY));
        builder.setDeviceInfo(DeviceInfo.getLocalDevice(context));
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
    public synchronized ActivityRecordReadOptions toActivityRecordReadOptions(final ReadableMap dataTypeMap,
        final ReadableMap dateReadableMap, final @Nullable String activityRecordId,
        final @Nullable String activityRecordName, final Promise promise) {
        DataType dataType = Utils.INSTANCE.toDataType(dataTypeMap);

        ActivityRecordReadOptions.Builder builder = new ActivityRecordReadOptions.Builder();
        // Build the time range of the request object: start time and end time
        Date startDate = Utils.INSTANCE.toDate(Constants.TimeConstants.START, null, dateReadableMap, promise);
        Date endDate = Utils.INSTANCE.toDate(Constants.TimeConstants.END, null, dateReadableMap, promise);

        if (startDate != null && endDate != null && dataType != null) {
            builder.setTimeInterval(startDate.getTime(), endDate.getTime(), Utils.INSTANCE.toTimeUnit(dateReadableMap))
                .readActivityRecordsFromAllApps()
                .read(dataType);
        }

        setBuilderRecord(builder, activityRecordId, RecordTypes.ID);
        setBuilderRecord(builder, activityRecordName, RecordTypes.NAME);
        return builder.build();
    }

    /**
     * Converts into {@link DeleteOptions} instance.
     */
    public synchronized DeleteOptions toActivityDeleteOptions(final ActivityRecord activityRecord,
        final TimeUnit timeUnit) {
        return new DeleteOptions.Builder().addActivityRecord(activityRecord)
            .setTimeInterval(activityRecord.getStartTime(timeUnit), activityRecord.getEndTime(timeUnit), timeUnit)
            .build();
    }

    public synchronized ActivityRecordDeleteOptions toActivityRecordDeleteOptions(final ReadableMap readableMap,
        final Promise promise) {
        ActivityRecordDeleteOptions.Builder builder = new ActivityRecordDeleteOptions.Builder();

        Date startDate = Utils.INSTANCE.toDate(Constants.TimeConstants.START, null, readableMap, promise);
        Date endDate = Utils.INSTANCE.toDate(Constants.TimeConstants.END, null, readableMap, promise);

        if (startDate != null && endDate != null) {
            builder.setTimeInterval(startDate.getTime(), endDate.getTime(), Utils.INSTANCE.toTimeUnit(readableMap));
        }

        Boolean isDeleteSubData = readableMap.getBoolean("isDeleteSubData");

        ReadableArray activityRecordIdsRA = readableMap.getArray("activityRecordIds");
        List<String> activityRecordIds = toList(toArray(activityRecordIdsRA));

        ReadableArray subDataTypesRA = readableMap.getArray("subDataTypes");
        List<DataType> subDataTypes = Utils.INSTANCE.toDataTypeList(subDataTypesRA);

        builder.setSubDataTypeList(subDataTypes)
            .setActivityRecordIds(activityRecordIds)
            .isDeleteSubData(isDeleteSubData);

        return builder.build();
    }

    /* Private Methods */

    /**
     * Sets {@link ActivityRecord.Builder} Time
     */
    private synchronized void setBuilderTime(ActivityRecord.Builder builder, final ReadableMap readableMap,
        final Constants.TimeConstants time) {
        switch (time) {
            case START:
                Date startDate = Utils.INSTANCE.toDate(readableMap, START_TIME_KEY);
                if (startDate != null) {
                    builder.setStartTime(startDate.getTime(), Utils.INSTANCE.toTimeUnit(readableMap));
                }
                break;
            case DURATION:
                if (readableMap.hasKey(DURATION_TIME_KEY)) {
                    builder.setDurationTime((long) readableMap.getInt(DURATION_TIME_KEY),
                        Utils.INSTANCE.toTimeUnit(readableMap));
                }
                break;
            case END:
                Date endDate = Utils.INSTANCE.toDate(readableMap, END_TIME_KEY);
                if (endDate != null) {
                    builder.setEndTime(endDate.getTime(), Utils.INSTANCE.toTimeUnit(readableMap));
                }
                break;
            default:
                break;
        }
    }

    /**
     * Sets {@link ActivityRecordReadOptions.Builder} Records
     */
    private synchronized void setBuilderRecord(final ActivityRecordReadOptions.Builder builder,
        final @Nullable String recordVal, final RecordTypes types) {
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
     * @param context Package context of the app for data collector
     * @param promise Promise for giving error if a parsing error occurs in time
     * @return ActivitySummary Object
     */
    private ActivitySummary toActivitySummary(ReadableMap readableMap, final ReactContext context,
        final Promise promise) {
        ActivitySummary activitySummary = new ActivitySummary();
        if (Utils.INSTANCE.hasKey(readableMap, "paceSummary")) {
            activitySummary.setPaceSummary(toPaceSummary(readableMap.getMap("paceSummary")));
        }
        if (Utils.INSTANCE.hasKey(readableMap, "dataSummary")) {
            activitySummary.setDataSummary(toDataSummary(readableMap.getArray("dataSummary"), context, promise));
        }

        return activitySummary;
    }

    /**
     * Converts ReadableMap Object to List of SamplePoint objects in DataSummary
     *
     * @param dataSummaryArray ReadableMap Array
     * @param context Package context of the app for data collector
     * @param promise Promise for giving error if a parsing error occurs in time
     * @return List of SamplePoint Object
     */
    private synchronized List<SamplePoint> toDataSummary(final ReadableArray dataSummaryArray,
        final ReactContext context, final Promise promise) {
        List<SamplePoint> dataSummary = new ArrayList<>();
        for (int i = 0; i < dataSummaryArray.size(); i++) {
            ReadableMap dataSummaryMap = dataSummaryArray.getMap(i);
            if (dataSummaryArray != null && Utils.INSTANCE.hasKey(dataSummaryMap, "dataCollector")) {
                DataCollector dataCollector = Utils.INSTANCE.toDataCollector(dataSummaryMap.getMap("dataCollector"),
                    context);
                List<Object> samplePointList = toArrayList(dataSummaryMap.getArray("samplePoints"));
                if (dataCollector != null) {
                    for (Object samplePointObj : samplePointList) {
                        Map<String, Object> samplePointMap = (Map<String, Object>) samplePointObj;
                        SamplePoint samplePoint = Utils.INSTANCE.toSamplePoint(dataCollector, samplePointMap, promise);
                        dataSummary.add(samplePoint);
                    }
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
            paceSummary.setBritishPartTimeMap(
                (Map<String, Double>) MapUtils.toMap(readableMap.getMap("britishPartTimeMap")));
        }
        if (Utils.INSTANCE.hasKey(readableMap, "sportHealthPaceMap")) {
            paceSummary.setSportHealthPaceMap(
                (Map<String, Double>) MapUtils.toMap(readableMap.getMap("sportHealthPaceMap")));
        }

        return paceSummary;
    }

}
