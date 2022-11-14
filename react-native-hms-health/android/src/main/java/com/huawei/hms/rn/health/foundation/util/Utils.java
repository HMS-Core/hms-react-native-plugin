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

package com.huawei.hms.rn.health.foundation.util;

import static com.huawei.hms.rn.health.foundation.constant.Constants.DATA_TYPE_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.END_TIME_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.INSERTION_TIME_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.SAMPLING_TIME_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.START_TIME_KEY;
import static com.huawei.hms.rn.health.foundation.constant.Constants.TIME_UNIT_KEY;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toArrayList;
import static com.huawei.hms.rn.health.kits.datacontroller.util.DataControllerConstants.DATA_COLLECTOR_NAME_KEY;
import static com.huawei.hms.rn.health.kits.datacontroller.util.DataControllerConstants.DATA_GENERATE_TYPE_KEY;
import static com.huawei.hms.rn.health.kits.datacontroller.util.DataControllerConstants.DATA_STREAM_NAME_KEY;
import static com.huawei.hms.rn.health.kits.datacontroller.util.DataControllerConstants.DEVICE_ID_KEY;
import static com.huawei.hms.rn.health.kits.datacontroller.util.DataControllerConstants.DEVICE_INFO_KEY;
import static com.huawei.hms.rn.health.kits.datacontroller.util.DataControllerConstants.IS_LOCALIZED_KEY;

import android.content.Intent;

import com.huawei.hms.hihealth.data.DataCollector;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.DeviceInfo;
import com.huawei.hms.hihealth.data.Field;
import com.huawei.hms.hihealth.data.SamplePoint;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.rn.health.foundation.constant.Constants;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

/**
 * All the util methods for internal {@link com.huawei.hms.rn.health} kits.
 *
 * @since v.5.0.1
 */
public enum Utils {
    INSTANCE;

    /**
     * Date format which is used in the project
     */
    public synchronized SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    }

    /**
     * Returns whether key is in the ReadableMap instance or not.
     *
     * @param readableMap ReadableMap instance.
     * @param key String key.
     * @return Boolean
     */
    public synchronized Boolean hasKey(final ReadableMap readableMap, final String key) {
        return readableMap.hasKey(key);
    }

    /**
     * In case readableMap instance has requested key, returns the String Value,
     * in case not, returns empty string.
     *
     * @param readableMap ReadableMap instance.
     * @param key String key value.
     * @return String
     */
    public synchronized String createEmptyStringIfNull(final ReadableMap readableMap, final String key) {
        if (readableMap.hasKey(key)) {
            return readableMap.getString(key);
        }
        return "";
    }

    /**
     * In case String value is not pull, puts value into writableMap.
     *
     * @param writableMap WritableMap instance.
     * @param value refers to added value.
     * @param key String key value.
     * @return String
     */
    public synchronized <T> WritableMap putKeyIfNotNull(final WritableMap writableMap, final @Nullable T value,
        final String key) {
        if (value != null) {
            if (value instanceof String) {
                writableMap.putString(key, (String) value);
            } else if (value instanceof DataType) {
                writableMap.putString(key, ((DataType) value).getName());
            } else if (value instanceof DataCollector) {
                writableMap.putString(key, value.toString());
            } else if (value instanceof Date) {
                writableMap.putString(key, getDateFormat().format((Date) value));
            }
        }
        return writableMap;
    }

    /**
     * In case readableMap instance has requested key, returns the Date Value,
     * in case not, returns null.
     *
     * @param readableMap ReadableMap instance.
     * @param key String key value.
     * @return String
     */
    @Nullable
    public synchronized Date toDate(final ReadableMap readableMap, final String key) {
        if (hasKey(readableMap, key)) {
            try {
                return getDateFormat().parse(readableMap.getString(key));
            } catch (ParseException error) {
                ExceptionHandler.INSTANCE.fail(error);
            }
        }
        return null;
    }

    /**
     * Returns String into {@link TimeUnit} Instance.
     * In case it doesn't exist returns {@code TimeUnit.MILLISECONDS}
     *
     * @param timeUnitStr String value.
     * @return {@link TimeUnit} instance.
     */
    public synchronized TimeUnit toTimeUnit(final String timeUnitStr) {
        Constants.TimeUnitTypes variable = Constants.TimeUnitTypes.fromString(timeUnitStr);
        return variable != null ? variable.getTimeUnitType() : TimeUnit.MILLISECONDS;
    }

    /**
     * Returns ReadableMap instance into {@link TimeUnit} Instance.
     * In case it doesn't exist returns {@code TimeUnit.MILLISECONDS}
     *
     * @param readableMap ReadableMap value.
     * @return {@link TimeUnit} instance.
     */
    public synchronized TimeUnit toTimeUnit(final ReadableMap readableMap) {
        if (hasKey(readableMap, TIME_UNIT_KEY)) {
            return toTimeUnit(readableMap.getString(TIME_UNIT_KEY));
        }

        return TimeUnit.MILLISECONDS;
    }

    /**
     * Returns Map<String, Object> instance into {@link TimeUnit} Instance.
     * In case it doesn't exist returns {@code TimeUnit.MILLISECONDS}
     *
     * @param map Map<String, Object> value.
     * @return {@link TimeUnit} instance.
     */
    public synchronized TimeUnit toTimeUnit(final Map<String, Object> map) {
        if (map.containsKey(TIME_UNIT_KEY)) {
            return toTimeUnit(String.valueOf(map.get(TIME_UNIT_KEY)));
        }
        return TimeUnit.MILLISECONDS;
    }

    /**
     * Returns String into {@link DataType} Instance.
     * In case it doesn't exist returns null.
     *
     * @param dataTypeStr String Value.
     * @return {@link DataType} Instance
     */
    @Nullable
    public synchronized DataType toDataType(final String dataTypeStr) {
        Constants.DataTypeConstants variable = Constants.DataTypeConstants.fromString(dataTypeStr);
        return variable != null ? variable.getDataType() : null;
    }

    /**
     * Returns ReadableMap instance into {@link DataType} Instance.
     * In case it doesn't exist returns null.
     *
     * @param readableMap ReadableMap instance.
     * @return {@link DataType} Instance
     */
    @Nullable
    public synchronized DataType toDataType(final ReadableMap readableMap) {
        if (hasKey(readableMap, DATA_TYPE_KEY)) {
            return toDataType(readableMap.getString(DATA_TYPE_KEY));
        } else {
            return null;
        }
    }

    /**
     * Returns Map<String, Object> instance into {@link DataType} Instance.
     * In case it doesn't exist returns null.
     *
     * @param map Map<String, Object> instance.
     * @return {@link DataType} Instance
     */
    @Nullable
    public synchronized DataType toDataType(final Map<String, Object> map) {
        if (map.containsKey(DATA_TYPE_KEY)) {
            return toDataType(String.valueOf(map.get(DATA_TYPE_KEY)));
        } else {
            return null;
        }
    }

    /**
     * Checks all the array variables of words, then returns if it contains all or not.
     */
    public synchronized boolean stringContainsItemFromList(String inputStr, String[] items) {
        for (String item : items) {
            if (inputStr.contains(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts DateMap into a Date. As requested startDate instance.
     */
    @Nullable
    public synchronized Date toDate(final Constants.TimeConstants type, final @Nullable Map<String, Object> dateMap,
        final @Nullable ReadableMap dateReadableMap, final Promise promise) {
        Date date = null;
        String key;
        try {
            switch (type) {
                case START:
                    key = START_TIME_KEY;
                    break;
                case END:
                    key = END_TIME_KEY;
                    break;
                case SAMPLING:
                    key = SAMPLING_TIME_KEY;
                    break;
                case INSERTION:
                    key = INSERTION_TIME_KEY;
                    break;
                default:
                    return null;
            }
            if (dateReadableMap != null && dateReadableMap.hasKey(key)) {
                date = getDateFormat().parse(dateReadableMap.getString(key));
            } else if (dateMap != null && dateMap.containsKey(key)) {
                date = getDateFormat().parse(String.valueOf(dateMap.get(key)));
            }
            return date;
        } catch (ParseException parseException) {
            ExceptionHandler.INSTANCE.fail(parseException, promise);
            return null;
        }
    }

    /**
     * Converts String FieldType value into {@link Field}.
     */
    public synchronized Field toFieldType(final String fieldTypeStr) {
        Constants.FieldConstants variable = Constants.FieldConstants.fromString(fieldTypeStr);
        return variable != null ? variable.getFieldType() : null;
    }

    public synchronized void fillServiceIntent(final Intent serviceIntent, ReadableMap map) {
        if (map != null) {
            serviceIntent.putExtra("title", createEmptyStringIfNull(map, "title"));
            serviceIntent.putExtra("text", createEmptyStringIfNull(map, "text"));
            serviceIntent.putExtra("subText", createEmptyStringIfNull(map, "subText"));
            serviceIntent.putExtra("ticker", createEmptyStringIfNull(map, "ticker"));
            serviceIntent.putExtra("chronometer", map.hasKey("chronometer") && map.getBoolean("chronometer"));
            if (map.hasKey("largeIcon")) {
                serviceIntent.putExtra("largeIcon", map.getString("largeIcon"));
            }
        }

    }

    /**
     * Converts into {@link SampleSet} instance.
     *
     * @param sampleSetMapArr ReadableArray instance.
     * @param dataCollector DataCollector instance.
     * @param promise Promise resolved with fail status in case of missing parameters.
     * @return SampleSet instance.
     */
    public synchronized SampleSet toSampleSet(final ReadableArray sampleSetMapArr, final DataCollector dataCollector,
        final Promise promise) {
        final SampleSet sampleSet = SampleSet.create(dataCollector);

        List<Object> sampleSetList = toArrayList(sampleSetMapArr);
        Map<?, ?> metaData = null;
        for (Object samplePointObj : sampleSetList) {
            Map<String, Object> samplePointMap = (Map<String, Object>) samplePointObj;

            // Build a DT_CONTINUOUS_STEPS_DELTA sampling point.
            SamplePoint samplePoint = toSamplePoint(sampleSet.getDataCollector(), samplePointMap, promise);

            if (((Map<?, ?>) samplePointObj).containsKey("metaData")) {
                metaData = (Map<?, ?>) ((Map<?, ?>) samplePointObj).get("metaData");
                String key = (String) metaData.get("metaDataKey");
                String value = (String) metaData.get("metaDataValue");
                samplePoint.addMetadata(key, value);
            }

            // Save a DT_CONTINUOUS_STEPS_DELTA sampling point to the sampling dataset.
            // You can repeat steps 3 through 5 to add more sampling points to the sampling dataset.
            sampleSet.addSample(samplePoint);
        }
        return sampleSet;
    }

    /**
     * Converts into {@link SampleSet} instance.
     *
     * @param sampleSetMap ReadableMap instance.
     * @param context ReactContext instance.
     * @param promise Promise resolved with fail status in case of missing parameters.
     * @return SampleSet instance.
     */
    public synchronized SampleSet toSampleSet(final ReadableMap sampleSetMap, ReactContext context,
        final Promise promise) {

        DataCollector dataCollector = Utils.INSTANCE.toDataCollector(sampleSetMap.getMap("dataCollector"), context);

        final SampleSet.Builder builder = new SampleSet.Builder(dataCollector);

        ReadableArray samplePointArray = sampleSetMap.getArray("samplePoints");
        List<Object> sampleSetList = toArrayList(samplePointArray);
        Map<?, ?> metaData = null;
        for (Object samplePointObj : sampleSetList) {
            Map<String, Object> samplePointMap = (Map<String, Object>) samplePointObj;

            // Build a DT_CONTINUOUS_STEPS_DELTA sampling point.
            SamplePoint samplePoint = toSamplePoint(dataCollector, samplePointMap, promise);

            if (((Map<?, ?>) samplePointObj).containsKey("metaData")) {
                metaData = (Map<?, ?>) ((Map<?, ?>) samplePointObj).get("metaData");
                String key = (String) metaData.get("metaDataKey");
                String value = (String) metaData.get("metaDataValue");
                samplePoint.addMetadata(key, value);
            }

            // Save a DT_CONTINUOUS_STEPS_DELTA sampling point to the sampling dataset.
            // You can repeat steps 3 through 5 to add more sampling points to the sampling dataset.
            builder.addSample(samplePoint);
        }
        return builder.build();
    }

    public synchronized List<SampleSet> toSampleSetList(final ReadableArray sampleSetList, ReactContext context,
        final Promise promise) {
        List<SampleSet> sampleSets = new ArrayList<>();

        for (int i = 0; i < sampleSetList.size(); i++) {
            ReadableMap sampleSetMap = sampleSetList.getMap(i);
            if (sampleSetMap != null) {
                sampleSets.add(toSampleSet(sampleSetMap, context, promise));
            }
        }

        return sampleSets;
    }

    public synchronized List<DataType> toDataTypeList(final ReadableArray dataTypeArray) {
        List<DataType> dataTypes = new ArrayList<>();
        if (dataTypeArray != null) {
            for (int i = 0; i < dataTypeArray.size(); i++) {
                dataTypes.add(toDataType(dataTypeArray.getMap(i).getString("dataType")));
            }
        }

        return dataTypes;
    }

    /**
     * Converts into {@link SamplePoint} instance.
     *
     * @param dataCollector {@link DataCollector} instance.
     * @param samplePointMap ReadableMap instance that will be converted into SamplePoint instance.
     * @param promise Promise resolved with fail status in case of missing parameters.
     * @return SamplePoint
     */
    public synchronized SamplePoint toSamplePoint(final DataCollector dataCollector,
        final Map<String, Object> samplePointMap, final Promise promise) {
        SamplePoint.Builder samplePoint = new SamplePoint.Builder(dataCollector);

        Date startTime = Utils.INSTANCE.toDate(Constants.TimeConstants.START, samplePointMap, null, promise);
        Date endTime = Utils.INSTANCE.toDate(Constants.TimeConstants.END, samplePointMap, null, promise);
        Date samplingTime = Utils.INSTANCE.toDate(Constants.TimeConstants.SAMPLING, samplePointMap, null, promise);

        if (startTime != null && endTime != null) {
            samplePoint.setTimeInterval(startTime.getTime(), endTime.getTime(),
                Utils.INSTANCE.toTimeUnit(samplePointMap));
        }

        if (samplePointMap.containsKey(Constants.SAMPLING_TIME_KEY) && samplingTime != null) {
            samplePoint.setSamplingTime(samplingTime.getTime(), Utils.INSTANCE.toTimeUnit(samplePointMap));
        }

        Object[] fieldsList = (Object[]) samplePointMap.get("fields");
        if (fieldsList != null) {
            for (Object obj : fieldsList) {
                Map<String, Object> fieldMap = (Map<String, Object>) obj;
                Field field = toFieldType(String.valueOf(fieldMap.get("fieldName")));
                if (field != null) {
                    switch (field.getFormat()) {
                        case Field.FORMAT_INT32:
                            samplePoint.setFieldValue(field, ((Double) fieldMap.get("fieldValue")).intValue());
                            break;
                        case Field.FORMAT_FLOAT:
                            samplePoint.setFieldValue(field, ((Double) fieldMap.get("fieldValue")).floatValue());
                            break;
                        case Field.FORMAT_STRING:
                            samplePoint.setFieldValue(field, (String) fieldMap.get("fieldValue"));
                            break;
                        case Field.FORMAT_MAP:
                            Map<String, Double> doubleMap = (Map<String, Double>) fieldMap.get("fieldValue");
                            for (Map.Entry<String, Double> entry : doubleMap.entrySet()) {
                                samplePoint.setFieldValue(entry.getKey(), entry.getValue());
                            }
                            break;
                        case Field.FORMAT_LONG:
                            samplePoint.setFieldValue(field, ((Double) fieldMap.get("fieldValue")).longValue());
                            break;
                        default:
                            break;
                    }
                }

            }
        }

        return samplePoint.build();
    }

    public synchronized List<SamplePoint> toSamplePointList(final ReadableArray sampleSetList, ReactContext context,
        Promise promise) {
        List<SamplePoint> samplePoints = new ArrayList<>();

        for (int i = 0; i < sampleSetList.size(); i++) {
            ReadableMap sampleSetMap = sampleSetList.getMap(i);
            if (sampleSetMap != null) {
                DataCollector dataCollector = Utils.INSTANCE.toDataCollector(sampleSetMap.getMap("dataCollector"),
                    context);
                ReadableArray samplePointArray = sampleSetMap.getArray("samplePoints");
                List<Object> sampleSetArrayList = toArrayList(samplePointArray);
                for (Object samplePointObj : sampleSetArrayList) {
                    Map<String, Object> samplePointMap = (Map<String, Object>) samplePointObj;

                    SamplePoint samplePoint = toSamplePoint(dataCollector, samplePointMap, promise);

                    samplePoints.add(samplePoint);
                }
            }
        }

        return samplePoints;
    }

    /**
     * Converts into {@link DataCollector} instance.
     */
    public synchronized DataCollector toDataCollector(final ReadableMap dataCollectorMap, ReactContext context) {
        DataCollector.Builder builder = new DataCollector.Builder();
        builder.setDataType(Utils.INSTANCE.toDataType(dataCollectorMap));
        builder = new HmsDataCollectorBuilder(builder, dataCollectorMap).setDataStreamName()
            .setDeviceId()
            .setDataCollectorName()
            .setDeviceInfo(context)
            .setLocalized()
            .setDataGenerateType()
            .build();
        return builder.setPackageName(context.getPackageName()).build();
    }

    /**
     * @param millis Returns a formatted string that presents the milliseconds value
     * @return formatted String
     */
    public String getDurationStringFromMilliseconds(long millis) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)
            - TimeUnit.HOURS.toSeconds(hours);
        long remainingMillis = millis - TimeUnit.SECONDS.toMillis(seconds) - TimeUnit.MINUTES.toMillis(minutes)
            - TimeUnit.HOURS.toMillis(hours);
        return String.format(Locale.ENGLISH, "%02d:%02d:%02d.%03d", hours, minutes, seconds, remainingMillis);
    }

    /* Private Declarations */

    /**
     * {@link HmsDataCollectorBuilder} is a helper inner class to convert ReadableMap instance
     * into {@link DataCollector.Builder}.
     */
    private static class HmsDataCollectorBuilder {
        private DataCollector.Builder builder;

        private ReadableMap dataCollectorMap;

        HmsDataCollectorBuilder(DataCollector.Builder builder, final ReadableMap dataCollectorMap) {
            this.builder = builder;
            this.dataCollectorMap = dataCollectorMap;
        }

        HmsDataCollectorBuilder setDataStreamName() {
            if (!Utils.INSTANCE.hasKey(dataCollectorMap, DATA_STREAM_NAME_KEY)) {
                return this;
            }
            this.builder.setDataStreamName(dataCollectorMap.getString(DATA_STREAM_NAME_KEY));
            return this;
        }

        HmsDataCollectorBuilder setDeviceId() {
            if (!Utils.INSTANCE.hasKey(dataCollectorMap, DEVICE_ID_KEY)) {
                return this;
            }
            this.builder.setDeviceId(dataCollectorMap.getString(DEVICE_ID_KEY));
            return this;
        }

        HmsDataCollectorBuilder setDataCollectorName() {
            if (!Utils.INSTANCE.hasKey(dataCollectorMap, DATA_COLLECTOR_NAME_KEY)) {
                return this;
            }
            this.builder.setDataCollectorName(dataCollectorMap.getString(DATA_COLLECTOR_NAME_KEY));
            return this;
        }

        HmsDataCollectorBuilder setDeviceInfo(ReactContext context) {
            if (!Utils.INSTANCE.hasKey(dataCollectorMap, DEVICE_INFO_KEY)) {
                return this;
            }
            this.builder.setDeviceInfo(DeviceInfo.getLocalDevice(context));
            return this;
        }

        HmsDataCollectorBuilder setLocalized() {
            if (!Utils.INSTANCE.hasKey(dataCollectorMap, IS_LOCALIZED_KEY)) {
                return this;
            }
            this.builder.setLocalized(dataCollectorMap.getBoolean(IS_LOCALIZED_KEY));
            return this;
        }

        HmsDataCollectorBuilder setDataGenerateType() {
            if (!Utils.INSTANCE.hasKey(dataCollectorMap, DATA_GENERATE_TYPE_KEY)) {
                return this;
            }
            this.builder.setDataGenerateType(dataCollectorMap.getInt(DATA_GENERATE_TYPE_KEY));
            return this;
        }

        DataCollector.Builder build() {
            return builder;
        }
    }
}
