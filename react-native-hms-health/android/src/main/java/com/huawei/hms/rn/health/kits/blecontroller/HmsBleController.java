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

package com.huawei.hms.rn.health.kits.blecontroller;

import static com.huawei.hms.rn.health.foundation.util.MapUtils.createWritableMapWithSuccessStatus;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.wrapWritableObjectWithSuccessStatus;
import static com.huawei.hms.rn.health.foundation.view.BaseProtocol.View.getActivity;
import static com.huawei.hms.rn.health.kits.blecontroller.util.BleControllerUtils.toBleDeviceInfo;
import static com.huawei.hms.rn.health.kits.blecontroller.util.BleControllerUtils.toDataTypeList;
import static com.huawei.hms.rn.health.kits.blecontroller.util.BleControllerUtils.toWritableMap;

import androidx.annotation.Nullable;

import com.huawei.hms.hihealth.BleController;
import com.huawei.hms.hihealth.HiHealthOptions;
import com.huawei.hms.hihealth.HuaweiHiHealth;
import com.huawei.hms.hihealth.data.BleDeviceInfo;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.rn.health.foundation.helper.VoidResultHelper;
import com.huawei.hms.rn.health.foundation.util.ExceptionHandler;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;
import com.huawei.hms.rn.health.foundation.util.MapUtils;
import com.huawei.hms.rn.health.foundation.view.BaseController;
import com.huawei.hms.rn.health.foundation.view.BaseProtocol;
import com.huawei.hms.rn.health.kits.autorecorder.HmsAutoRecorderController;
import com.huawei.hms.rn.health.kits.blecontroller.helper.BleResultHelper;
import com.huawei.hms.rn.health.kits.blecontroller.listener.BleScanResultListener;
import com.huawei.hms.rn.health.kits.blecontroller.viewmodel.BleService;
import com.huawei.hms.rn.health.kits.blecontroller.viewmodel.BleViewModel;
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
 * {@link HmsBleController} class is a module that refers to {@link HmsBleController}
 *
 * @since v.5.0.1
 */
public class HmsBleController extends BaseController implements BaseProtocol.Event {

    private static final String TAG = HmsBleController.class.getSimpleName();

    // Internal context object
    private final ReactApplicationContext reactContext;

    // ViewModel instance to reach HmsBleController tasks
    private final BleService bleViewModel;

    // Huawei Account authentication and identification information
    private final AuthHuaweiId signInHuaweiId;

    // Internal Controller object
    private BleController bleController;

    private HMSLogger logger;

    /**
     * Initialization
     */
    public HmsBleController(ReactApplicationContext reactContext) {
        super(TAG, reactContext);
        this.reactContext = reactContext;
        bleViewModel = new BleViewModel();

        // Obtain SensorsController first when accessing the UI.
        HiHealthOptions options = HiHealthOptions.builder().build();

        // Sign in to the HUAWEI ID.
        signInHuaweiId = HuaweiIdAuthManager.getExtendedAuthResult(options);

        logger = HMSLogger.getInstance(reactContext);
    }

    /**
     * Obtain BleController first when accessing the UI.
     *
     * @param promise In the success scenario, promise is returned with {@code isSuccess: true} param , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void initBleController(final Promise promise) {
        String logName = "HmsBleController.initBleController";
        logger.startMethodExecutionTimer(logName);

        // Obtain the BleController.
        this.bleController = HuaweiHiHealth.getBleController(getActivity(getCurrentActivity()), signInHuaweiId);
        promise.resolve(MapUtils.createWritableMapWithSuccessStatus(true));
        logger.sendSingleEvent("HmsBleController.initBleController");
    }

    /**
     * To connect to an external BLE device (such as a bathroom scale and heart rate strap) to obtain data,
     * you need to first create a BleController object,
     * call the beginScan() method to scan for available BLE devices,
     * and call the saveDevice method to save the device information.
     * Then you can call the register method of SensorsController to register a listener to obtain the data reported by the device.
     * <p>
     *
     * @param dataTypeMapArr ReadableArray instance that will be referred to {@code List<DataType>}
     * @param time The scanning time
     * @param promise In the success scenario, BleDeviceInfo instance is returned with {@code isSuccess: true} param , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void beginScan(final ReadableArray dataTypeMapArr, int time, final String bleControllerIdentifier,
        final Promise promise) {
        String logName = "HmsBleController.beginScan";
        logger.startMethodExecutionTimer(logName);

        checkBleController(promise);

        List<DataType> dataTypes = toDataTypeList(dataTypeMapArr);

        bleViewModel.beginScan(bleController, dataTypes, time, bleControllerIdentifier, new BleScanResultListener() {
            @Override
            public void onDeviceDiscover(BleDeviceInfo bleDeviceInfo) {
                sendEvent(reactContext, "OnDeviceDiscover:" + bleControllerIdentifier,
                    wrapWritableObjectWithSuccessStatus(toWritableMap(bleDeviceInfo), true));
                logger.sendPeriodicEvent("HmsBleController.beginScan.onDeviceDiscover");
            }

            @Override
            public void onScanEnd() {
                sendEvent(reactContext, "OnScanEnd:" + bleControllerIdentifier,
                    createWritableMapWithSuccessStatus(true));
                logger.sendSingleEvent("HmsBleController.beginScan");
            }
        });
        promise.resolve("beginScan set");
    }

    /**
     * Stop scanning for Bluetooth devices.
     *
     * @param promise In the success scenario, {@link Boolean} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void endScan(final String bleControllerIdentifier, final Promise promise) {
        String logName = "HmsBleController.endScan";
        logger.startMethodExecutionTimer(logName);

        checkBleController(promise);

        bleViewModel.endScan(bleController, bleControllerIdentifier,
            new BleResultHelper<>(Boolean.class, promise, logger, logName));
    }

    /**
     * List all external Bluetooth devices that have been saved to the local device.
     *
     * @param promise In the success scenario, {@link List<BleDeviceInfo>} instance is returned , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void getSavedDevices(final Promise promise) {
        String logName = "HmsBleController.getSavedDevices";
        logger.startMethodExecutionTimer(logName);

        checkBleController(promise);

        bleViewModel.getSavedDevices(bleController, new BleResultHelper<>(List.class, promise, logger, logName));
    }

    /**
     * Save the scanned devices to the local device for the listener that will be registered later to obtain data.
     *
     * @param bleDeviceInfoReadableMap refers to BleDeviceInfo instance.
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void saveDevice(final ReadableMap bleDeviceInfoReadableMap, final Promise promise) {
        String logName = "HmsBleController.saveDevice";
        logger.startMethodExecutionTimer(logName);

        checkBleController(promise);
        BleDeviceInfo bleDeviceInfo = toBleDeviceInfo(bleDeviceInfoReadableMap);

        bleViewModel.saveDevice(bleController, bleDeviceInfo, new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Save the scanned devices to the local device for the listener that will be registered later to obtain data.
     *
     * @param deviceAddress refers to BleDeviceInfo instance.
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void saveDeviceByAddress(final String deviceAddress, final Promise promise) {
        String logName = "HmsBleController.saveDeviceByAddress";
        logger.startMethodExecutionTimer(logName);

        checkBleController(promise);

        bleViewModel.saveDevice(bleController, deviceAddress, new VoidResultHelper(promise, logger, logName));

    }

    /**
     * Delete the device information that has been saved.
     *
     * @param bleDeviceInfoReadableMap refers to BleDeviceInfo instance.
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void deleteDevice(final ReadableMap bleDeviceInfoReadableMap, final Promise promise) {
        String logName = "HmsBleController.deleteDevice";
        logger.startMethodExecutionTimer(logName);

        checkBleController(promise);
        BleDeviceInfo bleDeviceInfo = toBleDeviceInfo(bleDeviceInfoReadableMap);

        bleViewModel.deleteDevice(bleController, bleDeviceInfo, new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Delete the device information that has been saved.
     *
     * @param deviceAddress refers to Address string.
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void deleteDeviceByAddress(final String deviceAddress, final Promise promise) {
        String logName = "HmsBleController.deleteDeviceByAddress";
        logger.startMethodExecutionTimer(logName);

        checkBleController(promise);

        bleViewModel.deleteDevice(bleController, deviceAddress, new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Sends event to RN Side.
     *
     * @param reactContext ReactContext instance.
     * @param eventName Event name that will be available via {@link HmsAutoRecorderController}.
     * @param params Event params.
     */
    @Override
    public void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    /* Private Methods */

    /**
     * Check whether bleController is initialized, or not.
     */
    private void checkBleController(final Promise promise) {
        if (this.bleController == null) {
            ExceptionHandler.INSTANCE.fail(promise, "BleController must be initialized first.");
            this.bleController = HuaweiHiHealth.getBleController(getActivity(getCurrentActivity()), signInHuaweiId);
        }
    }

}