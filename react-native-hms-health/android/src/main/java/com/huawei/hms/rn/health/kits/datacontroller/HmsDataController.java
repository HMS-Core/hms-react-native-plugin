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

package com.huawei.hms.rn.health.kits.datacontroller;

import static com.huawei.hms.rn.health.foundation.util.MapUtils.createWritableMapWithSuccessStatus;

import android.app.PendingIntent;

import androidx.annotation.Nullable;

import com.huawei.hms.hihealth.DataController;
import com.huawei.hms.hihealth.HiHealthOptions;
import com.huawei.hms.hihealth.HuaweiHiHealth;
import com.huawei.hms.hihealth.data.DataCollector;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.hihealth.options.DeleteOptions;
import com.huawei.hms.hihealth.options.ReadOptions;
import com.huawei.hms.hihealth.options.UpdateOptions;
import com.huawei.hms.hihealth.result.ReadReply;
import com.huawei.hms.rn.health.foundation.helper.ResultHelper;
import com.huawei.hms.rn.health.foundation.helper.VoidResultHelper;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;
import com.huawei.hms.rn.health.foundation.util.Utils;
import com.huawei.hms.rn.health.foundation.view.BaseController;
import com.huawei.hms.rn.health.foundation.view.BaseProtocol;
import com.huawei.hms.rn.health.kits.datacontroller.util.DataControllerUtils;
import com.huawei.hms.rn.health.kits.datacontroller.viewmodel.DataService;
import com.huawei.hms.rn.health.kits.datacontroller.viewmodel.DataViewModel;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.List;

/**
 * {@link HmsDataController} class is a module that refers to {@link DataController}
 * <p>
 * DataController is used to manage fitness and health data (DataType, HealthDataTypes, and SportDataTypes).
 * The related operations include adding, deleting, modifying, and querying of the data.
 * </br>
 * In addition, DataController allows for feature-based query on the summary data of the current day,
 * as well as the summary data on the local device of the current day.
 * Listeners can be registered for updates of six data types:
 * 1. Basic metabolic rate per day (unit: kcal): HmsDataController.DT_INSTANTANEOUS_CALORIES_BMR
 * 2. Body fat rate: HmsDataController.DT_INSTANTANEOUS_BODY_FAT_RATE
 * 3. Height (unit: meter): HmsDataController.DT_INSTANTANEOUS_HEIGHT
 * 4. Water taken over a single drink (unit: liter): HmsDataController.DT_INSTANTANEOUS_HYDRATE
 * 5. Nutrient intake over a meal: HmsDataController.DT_INSTANTANEOUS_NUTRITION_FACTS
 * 6. Weight (unit: kg): HmsDataController.DT_INSTANTANEOUS_BODY_WEIGHT
 *
 * @since v.5.0.1
 */
public class HmsDataController extends BaseController implements BaseProtocol.Event {

    private static final String TAG = HmsDataController.class.getSimpleName();

    // PendingIntent, required when registering or unregistering a listener within the data controller
    private PendingIntent pendingIntent;

    // ViewModel instance to reach DataController tasks
    private final DataService dataViewModel;

    // Internal context object
    private final ReactContext reactContext;

    // Object of controller for fitness and health data, providing APIs for read/write, batch read/write, and listening
    private DataController dataController;

    private final HMSLogger logger;

    /**
     * Initialization
     */
    public HmsDataController(ReactApplicationContext reactContext) {
        super(TAG, reactContext);
        this.reactContext = reactContext;
        dataViewModel = new DataViewModel();
        logger = HMSLogger.getInstance(reactContext);
    }

    /**
     * Initialize a data controller object.
     * <p>
     * Note:  Before using {@link HmsDataController} methods,
     * always initDataController method must be called with requested dataTypes.
     * </p>
     *
     * @param dataTypeReadableArr ReadableArray instance, referred to dataTypeList that will be reached to create dataController.
     * @param promise In the success scenario, promise is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void initDataController(final ReadableArray dataTypeReadableArr, final Promise promise) {
        String logName = "HmsDataController.initDataController";
        logger.startMethodExecutionTimer(logName);

        AuthHuaweiId signInHuaweiId = HuaweiIdAuthManager.getExtendedAuthResult(
            DataControllerUtils.INSTANCE.toHiHealthOptions(dataTypeReadableArr));
        this.dataController = HuaweiHiHealth.getDataController(reactContext, signInHuaweiId);
        promise.resolve(createWritableMapWithSuccessStatus(true));
        logger.sendSingleEvent(logName);
    }

    /**
     * Insert the user's fitness and health data into the Health platform.
     *
     * @param dataCollectorMap Refers to {@link DataCollector} instance.
     * @param sampleSetMapArr Refers to List<Object> sampleSetList instance.
     * @param promise In the success scenario, Void is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void insert(final ReadableMap dataCollectorMap, final ReadableArray sampleSetMapArr, final Promise promise) {
        String logName = "HmsDataController.insert";
        logger.startMethodExecutionTimer(logName);

        checkDataController();
        // Build a DataCollector object.
        DataCollector dataCollector = Utils.INSTANCE.toDataCollector(dataCollectorMap, reactContext);

        // Create a sampling dataset set based on the data collector.
        final SampleSet sampleSet = Utils.INSTANCE.toSampleSet(sampleSetMapArr, dataCollector, promise);

        dataViewModel.insertData(dataController, sampleSet, new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Updating the User's Fitness and Health Data
     * </br>
     * 1. Build the condition for data update: a DataCollector object.
     * 2. Build the sampling data set for the update: create a sampling data set for the update based on the data collector.
     * 3. Build the start time, end time, and incremental step count for a DataType sampling point for the update.
     * 4. Build a DataType sampling point for the update
     * 5. Add an updated DataType sampling point to the sampling data set for the update. You can add more updated sampling points to the sampling data set for the update.
     * 6. Build a parameter object for the update.
     * 7. Use the specified parameter object for the update to call the data controller to modify the sampling dataset.
     *
     * @param dataCollectorMap Refers to {@link DataCollector} instance.
     * @param sampleSetMapArr Refers to List<Object> sampleSetList instance.
     * @param updateOptionsReadableMap Refers to {@link UpdateOptions} instance.
     * @param promise In the success scenario, Void is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void update(final ReadableMap dataCollectorMap, final ReadableArray sampleSetMapArr,
        final ReadableMap updateOptionsReadableMap, final Promise promise) {
        String logName = "HmsDataController.update";
        logger.startMethodExecutionTimer(logName);

        checkDataController();
        // Build a DataCollector object.
        DataCollector dataCollector = Utils.INSTANCE.toDataCollector(dataCollectorMap, reactContext);

        // Create a sampling data set based on the data collector.
        final SampleSet sampleSet = Utils.INSTANCE.toSampleSet(sampleSetMapArr, dataCollector, promise);

        // Build a parameter object for the update.
        // Note: (1) The start time of the modified object updateOptions cannot be greater than the minimum
        // value of the start time of all sample data points in the modified data sample set
        // (2) The end time of the modified object updateOptions cannot be less than the maximum value of the
        // end time of all sample data points in the modified data sample set
        UpdateOptions updateOptions = DataControllerUtils.INSTANCE.toUpdateOptions(updateOptionsReadableMap, sampleSet,
            promise);

        dataViewModel.updateData(dataController, updateOptions, new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Querying the User's Fitness and Health Data
     * <p>
     * Note: To read historical data from the Health platform,
     * for example, to read the number of steps taken within a period of time, you can specify the read conditions in ReadOptions.
     * Furthermore, you can specify the data collector, data type, and detailed data.
     * If data is read, the data set will be returned.
     * </p>
     *
     * @param dataCollectorMap Refers to {@link DataCollector} instance.
     * @param dateMap Refers to to get startDate and EndDate.
     * @param promise In the success scenario, {@link ReadReply} instance is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void read(final ReadableMap dataCollectorMap, final ReadableMap dateMap, final ReadableMap groupingMap,
        final Promise promise) {
        String logName = "HmsDataController.read";
        logger.startMethodExecutionTimer(logName);

        checkDataController();
        // Build a DataCollector object.
        DataCollector dataCollector = Utils.INSTANCE.toDataCollector(dataCollectorMap, reactContext);

        // Build the start time, end time, and incremental step count for a DT_CONTINUOUS_STEPS_DELTA sampling point.
        // Build the condition-based query object
        ReadOptions readOptions = DataControllerUtils.INSTANCE.toReadOptions(dataCollector, dateMap, groupingMap,
            promise);

        dataViewModel.readData(dataController, readOptions,
            new ResultHelper<>(ReadReply.class, promise, logger, logName));
    }

    /**
     * Querying the User's Fitness and Health Data
     * <p>
     * Note: To read historical data from the Health platform,
     * for example, to read the number of steps taken within a period of time, you can specify the read conditions in ReadOptions.
     * Furthermore, you can specify the data collector, data type, and detailed data.
     * If data is read, the data set will be returned.
     * </p>
     *
     * @param dataTypeArray Refers to {@link List<DataType>} instance.
     * @param dateMap Refers to to get startDate and EndDate.
     * @param promise In the success scenario, {@link ReadReply} instance is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void readByDataType(final ReadableArray dataTypeArray, final ReadableMap dateMap,
        final ReadableMap groupingMap, final Promise promise) {
        String logName = "HmsDataController.readByDataType";
        logger.startMethodExecutionTimer(logName);

        checkDataController();

        List<DataType> dataTypes = Utils.INSTANCE.toDataTypeList(dataTypeArray);
        // Build the start time, end time, and incremental step count for a DT_CONTINUOUS_STEPS_DELTA sampling point.
        // Build the condition-based query object
        ReadOptions readOptions = DataControllerUtils.INSTANCE.toReadOptions(dataTypes, dateMap, groupingMap, promise);

        dataViewModel.readData(dataController, readOptions,
            new ResultHelper<>(ReadReply.class, promise, logger, logName));
    }

    /**
     * Deleting the User's Fitness and Health Data
     *
     * <p>
     * Note: Only historical data that has been inserted by the current app can be deleted from the Health platform.     * </p>
     *
     * @param dataCollectorMap Refers to {@link DataCollector} instance.
     * @param dateMap Refers to to get startDate and EndDate.
     * @param promise In the success scenario, Void is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void delete(final ReadableMap dataCollectorMap, final ReadableMap dateMap, final Promise promise) {
        String logName = "HmsDataController.delete";
        logger.startMethodExecutionTimer(logName);

        checkDataController();
        // Build the condition for data deletion: a DataCollector object.
        DataCollector dataCollector = Utils.INSTANCE.toDataCollector(dataCollectorMap, reactContext);
        // Build the time range for the deletion: start time and end time.
        // Build a parameter object as the conditions for the deletion.
        DeleteOptions deleteOptions = DataControllerUtils.INSTANCE.toDeleteOptions(dataCollector, dateMap, promise);

        dataViewModel.deleteData(dataController, deleteOptions, new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Deleting the User's Fitness and Health Data
     *
     * <p>
     * Note: Only historical data that has been inserted by the current app can be deleted from the Health platform.     * </p>
     *
     * @param dataTypeArray Refers to {@link List<DataType>} instance.
     * @param dateMap Refers to to get startDate and EndDate.
     * @param promise In the success scenario, Void is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void deleteByDataType(final ReadableArray dataTypeArray, final ReadableMap dateMap, final Promise promise) {
        String logName = "HmsDataController.deleteByDataType";
        logger.startMethodExecutionTimer(logName);

        checkDataController();

        List<DataType> dataTypes = Utils.INSTANCE.toDataTypeList(dataTypeArray);
        // Build the time range for the deletion: start time and end time.
        // Build a parameter object as the conditions for the deletion.
        DeleteOptions deleteOptions = DataControllerUtils.INSTANCE.toDeleteOptions(dataTypes, dateMap, promise);

        dataViewModel.deleteData(dataController, deleteOptions, new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Querying the Summary Fitness and Health Data of the User of the Current day
     *
     * @param dataTypeMap Refers to {@link DataType} instance.
     * @param promise In the success scenario, {@link SampleSet} instance is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void readTodaySummation(final ReadableMap dataTypeMap, final Promise promise) {
        String logName = "HmsDataController.readTodaySummation";
        logger.startMethodExecutionTimer(logName);

        checkDataController();
        DataType dataType = Utils.INSTANCE.toDataType(dataTypeMap);

        // Use the specified data type (DT_CONTINUOUS_STEPS_DELTA) to call the data controller to query
        // the summary data of this data type of the current day.
        dataViewModel.readToday(dataController, dataType,
            new ResultHelper<>(SampleSet.class, promise, logger, logName));
    }

    /**
     * Querying the Summary Fitness and Health Data of the User of the Current day
     *
     * @param dataTypeMap Refers to {@link DataType} instance.
     * @param startTime An 8-digit integer in the format of YYYYMMDD, for example, 20200803.
     * @param endTime An 8-digit integer in the format of YYYYMMDD, for example, 20200903.
     * @param promise In the success scenario, {@link SampleSet} instance is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void readDailySummation(final ReadableMap dataTypeMap, int startTime, int endTime, Promise promise) {
        String logName = "HmsDataController.readDailySummation";
        logger.startMethodExecutionTimer(logName);

        checkDataController();
        DataType dataType = Utils.INSTANCE.toDataType(dataTypeMap);

        dataViewModel.readDailySummation(dataController, dataType, startTime, endTime,
            new ResultHelper<>(SampleSet.class, promise, logger, logName));
    }

    /**
     * Clearing the User's Fitness and Health Data from the Device and Cloud
     *
     * @param promise In the success scenario, Void is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void clearAll(final Promise promise) {
        String logName = "HmsDataController.clearAll";
        logger.startMethodExecutionTimer(logName);

        checkDataController();
        dataViewModel.clearTaskData(dataController, new VoidResultHelper(promise, logger, logName));
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
     * Initialize variable of dataController with no dataType params,
     * in case it is null.
     */
    private void initDataController() {
        // create HiHealth Options, donnot add any datatype here.
        HiHealthOptions hiHealthOptions = HiHealthOptions.builder().build();
        // get AuthHuaweiId by HiHealth Options.
        AuthHuaweiId signInHuaweiId = HuaweiIdAuthManager.getExtendedAuthResult(hiHealthOptions);
        this.dataController = HuaweiHiHealth.getDataController(reactContext, signInHuaweiId);
    }

    /**
     * Check whether dataController is initialized, or not.
     */
    private void checkDataController() {
        if (this.dataController == null) {
            initDataController();
        }
    }

}
