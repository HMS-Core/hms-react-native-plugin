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

package com.huawei.hms.rn.health.kits.healthrecordcontroller;

import static com.huawei.hms.rn.health.foundation.util.MapUtils.toArrayList;

import androidx.annotation.Nullable;

import com.huawei.hms.hihealth.HealthRecordController;
import com.huawei.hms.hihealth.HuaweiHiHealth;
import com.huawei.hms.hihealth.data.DataCollector;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.HealthRecord;
import com.huawei.hms.hihealth.data.SamplePoint;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.hihealth.options.HealthRecordDeleteOptions;
import com.huawei.hms.hihealth.options.HealthRecordReadOptions;
import com.huawei.hms.hihealth.result.HealthRecordReply;
import com.huawei.hms.rn.health.foundation.helper.ResultHelper;
import com.huawei.hms.rn.health.foundation.helper.VoidResultHelper;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;
import com.huawei.hms.rn.health.foundation.util.Utils;
import com.huawei.hms.rn.health.foundation.view.BaseController;
import com.huawei.hms.rn.health.foundation.view.BaseProtocol;
import com.huawei.hms.rn.health.kits.datacontroller.HmsDataController;
import com.huawei.hms.rn.health.kits.healthrecordcontroller.util.HealthRecordControllerUtils;
import com.huawei.hms.rn.health.kits.healthrecordcontroller.viewmodel.HealthRecordService;
import com.huawei.hms.rn.health.kits.healthrecordcontroller.viewmodel.HealthRecordViewModel;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @since 5.0.5-300
 * {@link HmsHealthRecordController} class is a module that refers to {@link HealthRecordController}
 */

public class HmsHealthRecordController extends BaseController implements BaseProtocol.Event {

    private static final String TAG = HmsHealthRecordController.class.getSimpleName();

    private final HMSLogger logger;

    private final ReactContext reactContext;

    private final HealthRecordService healthRecordViewModel;

    private HealthRecordController healthRecordController;

    public HmsHealthRecordController(ReactApplicationContext reactContext) {
        super(TAG, reactContext);
        logger = HMSLogger.getInstance(reactContext);
        healthRecordViewModel = new HealthRecordViewModel();
        this.reactContext = reactContext;
    }

    /**
     * Inserts the health records into Health Kit.
     *
     * @param healthRecordBuilder ReadableArray instance, referred to healthRecordBuilder that will be reached to create builder.
     * @param dataCollectorArray ReadableArray instance, referred to dataTypeList that will be reached to create healthRecordController.
     * @param sampleSet Refers to List<Object> sampleSetList instance.
     * @param promise In the success scenario, promise is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void addHealthRecord(ReadableArray healthRecordBuilder, final ReadableArray dataCollectorArray,
        final ReadableArray sampleSet, final ReadableArray samplePoint, final Promise promise) {

        String logName = "HmsHealthRecordController.addHealthRecord";
        logger.startMethodExecutionTimer(logName);

        checkDataController();

        DataCollector dataCollector = Utils.INSTANCE.toDataCollector(dataCollectorArray.getMap(0), reactContext);
        final SampleSet sampleSet1 = Utils.INSTANCE.toSampleSet(sampleSet, dataCollector, promise);
        List<SampleSet> sampleSetList1 = new ArrayList<>();
        sampleSetList1.add(sampleSet1);

        DataCollector dataCollector1 = Utils.INSTANCE.toDataCollector(dataCollectorArray.getMap(1), reactContext);
        List<SamplePoint> samplePointList = new ArrayList<>();
        SamplePoint samplePoint1 = null;

        List<Object> sampleSetList = toArrayList(samplePoint);
        for (Object samplePointObj : sampleSetList) {
            Map<String, Object> samplePointMap = (Map<String, Object>) samplePointObj;
            samplePoint1 = Utils.INSTANCE.toSamplePoint(dataCollector1, samplePointMap, promise);
        }

        samplePointList.add(samplePoint1);

        DataCollector dataCollector2 = Utils.INSTANCE.toDataCollector(dataCollectorArray.getMap(2), reactContext);

        HealthRecord healthRecord = HealthRecordControllerUtils.INSTANCE.toHealthRecord(healthRecordBuilder,
            sampleSetList1, samplePointList, dataCollector2);

        healthRecordViewModel.addHealthRecord(healthRecordController, healthRecord,
            new ResultHelper(String.class, promise, logger, logName));
    }

    /**
     * Updates the health records. If the data to be updated does not exist, an error message is displayed.
     *
     * @param healthRecordBuilder ReadableArray instance, referred to healthRecordBuilder that will be reached to create builder.
     * @param dataCollectorArray ReadableArray instance, referred to dataTypeList that will be reached to create healthRecordController.
     * @param sampleSet Refers to List<Object> sampleSetList instance.
     * @param promise In the success scenario, promise is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void updateHealthRecord(ReadableArray healthRecordBuilder, final ReadableArray dataCollectorArray,
        final ReadableArray sampleSet, final ReadableArray samplePoint, final Promise promise) {

        String updateLogName = "HmsHealthRecordController.updateHealthRecord";

        checkDataController();

        logger.startMethodExecutionTimer(updateLogName);

        DataCollector dataCollector = Utils.INSTANCE.toDataCollector(dataCollectorArray.getMap(0), reactContext);
        final SampleSet sampleSet1 = Utils.INSTANCE.toSampleSet(sampleSet, dataCollector, promise);
        List<SampleSet> sampleSetList1 = new ArrayList<>();
        sampleSetList1.add(sampleSet1);

        DataCollector dataCollector1 = Utils.INSTANCE.toDataCollector(dataCollectorArray.getMap(1), reactContext);
        List<SamplePoint> samplePointList = new ArrayList<>();
        SamplePoint samplePoint1 = null;

        List<Object> sampleSetList = toArrayList(samplePoint);
        for (Object samplePointObj : sampleSetList) {
            Map<String, Object> samplePointMap = (Map<String, Object>) samplePointObj;
            samplePoint1 = Utils.INSTANCE.toSamplePoint(dataCollector1, samplePointMap, promise);
        }

        DataCollector dataCollector2 = Utils.INSTANCE.toDataCollector(dataCollectorArray.getMap(2), reactContext);
        samplePointList.add(samplePoint1);
        HealthRecord updateHealthRecord = HealthRecordControllerUtils.INSTANCE.toHealthRecord(healthRecordBuilder,
            sampleSetList1, samplePointList, dataCollector2);

        healthRecordViewModel.updateHealthRecord(healthRecordController, updateHealthRecord,
            new VoidResultHelper(promise, logger, updateLogName));
    }

    /**
     * Reads the API of the user health record data. You can read data by time, data type, data collector, and more by specifying the related parameters in ReadOptions.
     *
     * @param dataType ReadableMap instance that refers to {@link DataType} instance.
     * @param dateMap ReadableMap instance that refers to startTime, endTime and timeUnit params.
     * @param promise In the success scenario, promise is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void getHealthRecord(final ReadableMap dataType, final ReadableMap dateMap, final Promise promise) {
        String logName = "HmsHealthRecordController.getHealthRecord";
        logger.startMethodExecutionTimer(logName);

        checkDataController();

        HealthRecordReadOptions healthRecordReadOptions
            = HealthRecordControllerUtils.INSTANCE.toReadHealthRecordOptions(dataType, dateMap, promise);

        healthRecordViewModel.getHealthRecord(healthRecordController, healthRecordReadOptions,
            new ResultHelper<>(HealthRecordReply.class, promise, logger, logName));
    }

    /**
     * Deletes health records based on the request parameters.
     *
     * @param readableMap ReadableMap instance that refers to HealthRecordDeleteOptions instance.
     * @param promise In the success scenario, promise is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void deleteHealthRecord(final ReadableMap readableMap, final Promise promise) {
        String logName = "HmsHealthRecordController.deleteHealthRecord";
        logger.startMethodExecutionTimer(logName);
        checkDataController();

        HealthRecordDeleteOptions deleteOptions = HealthRecordControllerUtils.INSTANCE.toHealthRecordDeleteOptions(
            readableMap, promise);

        healthRecordViewModel.deleteHealthRecord(healthRecordController, deleteOptions,
            new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Sends event to RN Side.
     *
     * @param reactContext ReactContext instance.
     * @param eventName Event name that will be available via {@link HmsDataController}.
     * @param params Event params.
     */
    @Override
    public void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        String eventTitle = eventName == null ? "registerModifyDataMonitor" : eventName;
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventTitle, params);
    }

    /* Private Methods */

    /**
     * Initialize variable of healthRecordController with no dataType params,
     * in case it is null.
     */
    private void initDataController() {
        this.healthRecordController = HuaweiHiHealth.getHealthRecordController(reactContext);
    }

    /**
     * Check whether dataController is initialized, or not.
     */
    private void checkDataController() {
        if (this.healthRecordController == null) {
            initDataController();
        }
    }
}