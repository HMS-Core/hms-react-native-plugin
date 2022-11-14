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

package com.huawei.hms.rn.health.kits.healthrecordcontroller.util;

import static com.huawei.hms.rn.health.foundation.constant.Constants.END_TIME_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.META_DATA;
import static com.huawei.hms.rn.health.foundation.constant.Constants.START_TIME_KEY;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toArray;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toArrayList;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toList;

import com.huawei.hms.hihealth.data.DataCollector;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.Field;
import com.huawei.hms.hihealth.data.HealthDataTypes;
import com.huawei.hms.hihealth.data.HealthRecord;
import com.huawei.hms.hihealth.data.SamplePoint;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.hihealth.options.HealthRecordDeleteOptions;
import com.huawei.hms.hihealth.options.HealthRecordReadOptions;
import com.huawei.hms.rn.health.foundation.constant.Constants;
import com.huawei.hms.rn.health.foundation.util.Utils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public enum HealthRecordControllerUtils {

    INSTANCE;

    /**
     * Looks for each key and converts ReadableMap instance into {@link HealthRecord} instance.
     *
     * @param healthRecordReadableMap ReadableMap instance that will be converted.
     * @param sampleSetList {@link SampleSet} instance.
     * @param samplePointsList {@link SamplePoint} instance.
     * @param dataCollectorMap DataCollector Object
     * @return {@link HealthRecord} instance.
     */
    public synchronized HealthRecord toHealthRecord(final ReadableArray healthRecordReadableMap,
        final List<SampleSet> sampleSetList, List<SamplePoint> samplePointsList, DataCollector dataCollectorMap) {

        HealthRecord.Builder builder = new HealthRecord.Builder(dataCollectorMap);
        builder.setSubDataSummary(samplePointsList);
        builder.setSubDataDetails(sampleSetList);
        builder.setMetadata(Utils.INSTANCE.createEmptyStringIfNull(healthRecordReadableMap.getMap(0), META_DATA));
        setBuilderTime(builder, healthRecordReadableMap.getMap(0), Constants.TimeConstants.START);
        setBuilderTime(builder, healthRecordReadableMap.getMap(0), Constants.TimeConstants.END);

        List<Object> sampleSetList1 = toArrayList(healthRecordReadableMap);
        for (Object samplePointObj : sampleSetList1) {
            Map<String, Object> samplePointMap = (Map<String, Object>) samplePointObj;
            setFieldValue(builder, samplePointMap);
        }

        return builder.build();
    }

    /**
     * @param builder HealthRecord.Builder object.
     */
    private synchronized void setFieldValue(HealthRecord.Builder builder, Map<String, Object> samplePointMap) {
        Object[] fieldsList = (Object[]) samplePointMap.get("fields");
        if (fieldsList != null) {
            for (Object obj : fieldsList) {
                Map<String, Object> fieldMap = (Map<String, Object>) obj;
                Field field = toFieldType(String.valueOf(fieldMap.get("fieldName")));
                if (field != null) {
                    switch (field.getFormat()) {
                        case Field.FORMAT_FLOAT:
                            builder.setFieldValue(field, ((Double) fieldMap.get("fieldValue")).floatValue());
                            break;
                        case Field.FORMAT_STRING:
                            builder.setFieldValue(field, (String) fieldMap.get("fieldValue"));
                            break;
                        case Field.FORMAT_MAP:
                            Map<String, Double> doubleMap = (Map<String, Double>) fieldMap.get("fieldValue");
                            Map<String, Float> floatMap = new HashMap<>();
                            for (Map.Entry<String, Double> entry : doubleMap.entrySet()) {
                                floatMap.put(entry.getKey(), entry.getValue().floatValue());
                            }
                            builder.setFieldValue(field, String.valueOf(floatMap));
                            break;
                        case Field.FORMAT_LONG:
                            builder.setFieldValue(field, ((Double) fieldMap.get("fieldValue")).longValue());
                            break;
                        case Field.FORMAT_INT32:
                            builder.setFieldValue(field, ((Double) fieldMap.get("fieldValue")).intValue());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public synchronized Field toFieldType(final String fieldTypeStr) {
        Constants.FieldConstants variable = Constants.FieldConstants.fromString(fieldTypeStr);
        return variable != null ? variable.getFieldType() : null;
    }

    public synchronized HealthRecordDeleteOptions toHealthRecordDeleteOptions(final ReadableMap readableMap,
        final Promise promise) {
        HealthRecordDeleteOptions.Builder builder = new HealthRecordDeleteOptions.Builder();

        Date startDate = Utils.INSTANCE.toDate(Constants.TimeConstants.START, null, readableMap, promise);
        Date endDate = Utils.INSTANCE.toDate(Constants.TimeConstants.END, null, readableMap, promise);

        if (startDate != null && endDate != null) {
            builder.setTimeInterval(startDate.getTime(), endDate.getTime(), Utils.INSTANCE.toTimeUnit(readableMap));
        }

        Boolean isDeleteSubData = readableMap.getBoolean("isDeleteSubData");

        ReadableArray healthRecordIdsRA = readableMap.getArray("healthRecordIds");
        List<String> healthRecordIds = toList(toArray(healthRecordIdsRA));

        String dataTypeName = readableMap.getString("dataType");
        DataType dataType = Utils.INSTANCE.toDataType(dataTypeName);

        ReadableArray subDataTypesRA = readableMap.getArray("subDataTypes");
        List<DataType> subDataTypes = Utils.INSTANCE.toDataTypeList(subDataTypesRA);

        builder.setHealthRecordIds(healthRecordIds)
            .isDeleteSubData(isDeleteSubData)
            .setDataType(dataType)
            .setSubDataTypeList(subDataTypes);

        return builder.build();
    }

    /**
     * Sets {@link HealthRecord.Builder} Time
     */
    private synchronized void setBuilderTime(HealthRecord.Builder builder, final ReadableMap readableMap,
        final Constants.TimeConstants time) {
        switch (time) {
            case END:
                Date endDate = Utils.INSTANCE.toDate(readableMap, END_TIME_KEY);
                if (endDate != null) {
                    builder.setEndTime(endDate.getTime(), Utils.INSTANCE.toTimeUnit(readableMap));
                }
                break;
            case START:
                Date startDate = Utils.INSTANCE.toDate(readableMap, START_TIME_KEY);
                if (startDate != null) {
                    builder.setStartTime(startDate.getTime(), Utils.INSTANCE.toTimeUnit(readableMap));
                }
                break;
            default:
                break;
        }
    }

    /**
     * Looks for each key and converts ReadableMap instance into {@link HealthRecord} instance.
     *
     * @param dataTypeMap ReadableMap instance that refers to {@link DataType} instance.
     * @param dateReadableMap ReadableMap instance that will be referred into date.
     */
    public synchronized HealthRecordReadOptions toReadHealthRecordOptions(final ReadableMap dataTypeMap,
        final ReadableMap dateReadableMap, final Promise promise) {
        DataType dataType = Utils.INSTANCE.toDataType(dataTypeMap);

        HealthRecordReadOptions.Builder builder = new HealthRecordReadOptions.Builder();

        List<DataType> subDataTypeList = new ArrayList<>();
        subDataTypeList.add(dataType);

        Date startDate = Utils.INSTANCE.toDate(Constants.TimeConstants.START, null, dateReadableMap, promise);
        Date endDate = Utils.INSTANCE.toDate(Constants.TimeConstants.END, null, dateReadableMap, promise);

        builder.setTimeInterval(startDate.getTime(), endDate.getTime(), TimeUnit.MILLISECONDS)
            .readHealthRecordsFromAllApps()
            .readByDataType(HealthDataTypes.DT_HEALTH_RECORD_BRADYCARDIA)
            .setSubDataTypeList(subDataTypeList)
            .build();

        return builder.build();
    }
}
