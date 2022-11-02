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

package com.huawei.hms.rn.health.kits.datacontroller.util;

import static com.huawei.hms.rn.health.foundation.constant.Constants.TIME_UNIT_KEY;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toArrayList;
import static com.huawei.hms.rn.health.kits.datacontroller.util.DataControllerConstants.GROUP_BY_TIME_KEY;

import com.huawei.hms.hihealth.HiHealthOptions;
import com.huawei.hms.hihealth.data.DataCollector;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.hihealth.options.DeleteOptions;
import com.huawei.hms.hihealth.options.ReadOptions;
import com.huawei.hms.hihealth.options.UpdateOptions;
import com.huawei.hms.rn.health.foundation.constant.Constants;
import com.huawei.hms.rn.health.foundation.util.ExceptionHandler;
import com.huawei.hms.rn.health.foundation.util.Utils;
import com.huawei.hms.rn.health.kits.datacontroller.HmsDataController;
import com.huawei.hms.rn.health.kits.datacontroller.model.OptionModel;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

/**
 * DataControllerUtils exposes a set of helper methods for working with
 * {@link HmsDataController}.
 *
 * @since v.5.0.1
 */
public enum DataControllerUtils {
    INSTANCE;

    /**
     * Converts ReadableArray instance into {@link HiHealthOptions} instance.
     */
    public synchronized HiHealthOptions toHiHealthOptions(final ReadableArray dataTypeReadableArr) {
        List<Object> dataTypeList = toArrayList(dataTypeReadableArr);
        HiHealthOptions.Builder hiHealthOptionsBuilder = HiHealthOptions.builder();
        for (Object dataType : dataTypeList) {
            DataType requestedDataType = Utils.INSTANCE.toDataType((Map<String, Object>) dataType);
            Double hiHealthOptionsAccessVal = (Double) ((Map<String, Object>) dataType).get("hiHealthOptions");
            if (requestedDataType != null && hiHealthOptionsAccessVal != null) {
                hiHealthOptionsBuilder.addDataType(requestedDataType, hiHealthOptionsAccessVal.intValue());
            }
        }
        return hiHealthOptionsBuilder.build();
    }

    /**
     * Converts into {@link UpdateOptions} instance.
     */
    @Nullable
    public synchronized UpdateOptions toUpdateOptions(final ReadableMap dateReadableMap, final SampleSet sampleSet,
        final Promise promise) {
        // Build the start time, end time, and incremental step count for a DT_CONTINUOUS_STEPS_DELTA sampling point.
        OptionModel optionModel = createOptionModel(dateReadableMap, promise);

        // Build a parameter object for the update.
        // Note: (1) The start time of the modified object updateOptions cannot be greater than the minimum
        // value of the start time of all sample data points in the modified data sample set
        // (2) The end time of the modified object updateOptions cannot be less than the maximum value of the
        // end time of all sample data points in the modified data sample set
        return new UpdateOptions.Builder().setTimeInterval(optionModel.getStartDate().getTime(),
            optionModel.getEndDate().getTime(), optionModel.getTimeUnit()).setSampleSet(sampleSet).build();
    }

    /**
     * Converts into {@link DeleteOptions} instance.
     */
    @Nullable
    public synchronized DeleteOptions toDeleteOptions(final DataCollector dataCollector,
        final ReadableMap dateReadableMap, final Promise promise) {
        // Build the time range for the deletion: start time and end time.
        OptionModel optionModel = createOptionModel(dateReadableMap, promise);

        // Build a parameter object as the conditions for the deletion.
        return new DeleteOptions.Builder().addDataCollector(dataCollector)
            .setTimeInterval(optionModel.getStartDate().getTime(), optionModel.getEndDate().getTime(),
                optionModel.getTimeUnit())
            .build();
    }

    /**
     * Converts into {@link DeleteOptions} instance.
     */
    @Nullable
    public synchronized DeleteOptions toDeleteOptions(final List<DataType> dataTypes, final ReadableMap dateReadableMap,
        final Promise promise) {
        // Build the time range for the deletion: start time and end time.
        OptionModel optionModel = createOptionModel(dateReadableMap, promise);

        // Build a parameter object as the conditions for the deletion.
        DeleteOptions.Builder options = new DeleteOptions.Builder().setTimeInterval(
            optionModel.getStartDate().getTime(), optionModel.getEndDate().getTime(), optionModel.getTimeUnit());

        for (DataType dataType : dataTypes) {
            options.addDataType(dataType);
        }

        return options.build();
    }

    /**
     * Converts into {@link ReadOptions} instance.
     */
    @Nullable
    public synchronized ReadOptions toReadOptions(final DataCollector dataCollector, final ReadableMap dateReadableMap,
        final ReadableMap groupingMap, final Promise promise) {
        // Build the time range for the deletion: start time and end time.
        // Build the start time, end time, and incremental step count for a DT_CONTINUOUS_STEPS_DELTA sampling point.
        OptionModel optionModel = createOptionModel(dateReadableMap, promise);

        // Build the condition-based query object.
        ReadOptions.Builder builder = new ReadOptions.Builder().read(dataCollector)
            .setTimeRange(optionModel.getStartDate().getTime(), optionModel.getEndDate().getTime(),
                optionModel.getTimeUnit());

        groupingByTime(builder, groupingMap);

        return builder.build();
    }

    /**
     * Converts into {@link ReadOptions} instance.
     */
    @Nullable
    public synchronized ReadOptions toReadOptions(final List<DataType> dataTypes, final ReadableMap dateReadableMap,
        final ReadableMap groupingMap, final Promise promise) {
        // Build the time range for the deletion: start time and end time.
        // Build the start time, end time, and incremental step count for a DT_CONTINUOUS_STEPS_DELTA sampling point.
        OptionModel optionModel = createOptionModel(dateReadableMap, promise);

        // Build the condition-based query object.
        ReadOptions.Builder builder = new ReadOptions.Builder().setTimeRange(optionModel.getStartDate().getTime(),
            optionModel.getEndDate().getTime(), optionModel.getTimeUnit());

        for (DataType dataType : dataTypes) {
            builder.read(dataType);
        }

        groupingByTime(builder, groupingMap);
        return builder.build();
    }

    /* Private Methods */

    /**
     * Grouping map helper function
     *
     * @param builder Builder instance
     * @param groupingMap ReadableMap
     */
    private synchronized void groupingByTime(ReadOptions.Builder builder, ReadableMap groupingMap) {
        if (groupingMap != null) {
            if (groupingMap.hasKey(GROUP_BY_TIME_KEY)) {
                ReadableMap durationMap = groupingMap.getMap(GROUP_BY_TIME_KEY);
                if (durationMap != null && durationMap.hasKey("duration") && durationMap.hasKey(TIME_UNIT_KEY)) {
                    int duration = durationMap.getInt("duration");
                    builder.groupByTime(duration, Utils.INSTANCE.toTimeUnit(durationMap));
                }

            }

            if (groupingMap.hasKey("inputDataType") && groupingMap.hasKey("outputDataType")) {
                DataType input = Utils.INSTANCE.toDataType(groupingMap.getString("inputDataType"));
                DataType output = Utils.INSTANCE.toDataType(groupingMap.getString("outputDataType"));
                builder.polymerize(input, output);
            }
        }
    }

    /**
     * Creates {@link OptionModel} instance.
     */
    private synchronized OptionModel createOptionModel(final @Nullable ReadableMap dateReadableMap,
        final Promise promise) {
        if (dateReadableMap == null) {
            return new OptionModel(new Date(), new Date(), TimeUnit.MILLISECONDS);
        }
        Date startDate = Utils.INSTANCE.toDate(Constants.TimeConstants.START, null, dateReadableMap, promise);
        Date endDate = Utils.INSTANCE.toDate(Constants.TimeConstants.END, null, dateReadableMap, promise);
        TimeUnit timeUnit = Utils.INSTANCE.toTimeUnit(dateReadableMap);

        if (startDate == null || endDate == null) {
            ExceptionHandler.INSTANCE.fail(promise, "Add Time Parameters.");
        }

        return new OptionModel(startDate, endDate, timeUnit);
    }

}
