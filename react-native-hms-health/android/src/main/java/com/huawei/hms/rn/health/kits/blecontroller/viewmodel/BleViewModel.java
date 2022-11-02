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

package com.huawei.hms.rn.health.kits.blecontroller.viewmodel;

import android.util.Log;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.hihealth.BleController;
import com.huawei.hms.hihealth.data.BleDeviceInfo;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.options.BleScanCallback;
import com.huawei.hms.rn.health.foundation.listener.ResultListener;
import com.huawei.hms.rn.health.foundation.listener.VoidResultListener;
import com.huawei.hms.rn.health.kits.blecontroller.listener.BleScanResultListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BleViewModel implements BleService {
    private static final String TAG = BleViewModel.class.getSimpleName();

    /**
     * Bluetooth scanning callback object
     */
    private final Map<String, BleScanCallback> onBleCallbackMap = new HashMap<>();

    /**
     * To connect to an external BLE device (such as a bathroom scale and heart rate strap) to obtain data,
     * you need to first create a BleController object,
     * call the beginScan() method to scan for available BLE devices,
     * and call the saveDevice method to save the device information.
     * Then you can call the register method of SensorsController to register a listener to obtain the data reported by the device.
     * <p>
     *
     * @param bleController BleController instance.
     * @param dataTypes List<DataType> dataTypes.
     * @param time refers to time in int value.
     * @param bleControllerIdentifier refers to String identifier to identify bleControllerIdentifier in viewModel side.
     * @param listener BleScanResultListener instance.
     */
    @Override
    public void beginScan(final BleController bleController, final List<DataType> dataTypes, int time,
        final String bleControllerIdentifier, final BleScanResultListener listener) {
        Log.i(TAG, "call beginScan");

        BleScanCallback mBleCallback = new BleScanCallbackHelper(listener).getBleScanCallBack();
        onBleCallbackMap.put(bleControllerIdentifier, mBleCallback);
        bleController.beginScan(dataTypes, time, mBleCallback);
    }

    /**
     * Stop scanning for Bluetooth devices.
     *
     * @param bleController BleController instance.
     * @param bleControllerIdentifier refers to String identifier to identify bleControllerIdentifier in viewModel side.
     * @param listener ResultListener<Boolean> instance.
     */
    @Override
    public void endScan(final BleController bleController, final String bleControllerIdentifier,
        final ResultListener<Boolean> listener) {
        Log.i(TAG, "call endScan");
        if (this.onBleCallbackMap.isEmpty() || this.onBleCallbackMap.get(bleControllerIdentifier) == null) {
            listener.onFail(new NullPointerException("There is none listener to end scan."));
        }

        bleController.endScan(onBleCallbackMap.get(bleControllerIdentifier));
        listener.onSuccess(true);
    }

    /**
     * List all external Bluetooth devices that have been saved to the local device.
     *
     * @param bleController BleController instance.
     * @param listener ResultListener<List<BleDeviceInfo>> instance.
     */
    @Override
    public void getSavedDevices(final BleController bleController, final ResultListener<List> listener) {
        Log.i(TAG, "call getSavedDevices");
        Task<List<BleDeviceInfo>> bleDeviceInfoTask = bleController.getSavedDevices();
        // bleDeviceInfo List contains the list of the saved devices.
        bleDeviceInfoTask.addOnSuccessListener(listener::onSuccess).addOnFailureListener(listener::onFail);
    }

    /**
     * Save the scanned devices to the local device for the listener that will be registered later to obtain data.
     *
     * @param bleController BleController instance.
     * @param bleDeviceInfo BleDeviceInfo instance.
     * @param listener VoidResultListener instance.
     */
    @Override
    public void saveDevice(final BleController bleController, final BleDeviceInfo bleDeviceInfo,
        final VoidResultListener listener) {
        Log.i(TAG, "call saveDevice");
        bleController.saveDevice(bleDeviceInfo)
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);
    }

    /**
     * Save the devices by address to the local device for the listener that will be registered later to obtain data.
     *
     * @param bleController BleController instance.
     * @param deviceAddress Address string.
     * @param listener VoidResultListener instance.
     */
    @Override
    public void saveDevice(final BleController bleController, final String deviceAddress,
        final VoidResultListener listener) {
        Log.i(TAG, "call saveDeviceByAddress");
        bleController.saveDevice(deviceAddress)
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);
    }

    /**
     * Delete the device information that has been saved.
     *
     * @param bleController BleController instance.
     * @param bleDeviceInfo BleDeviceInfo instance.
     * @param listener VoidResultListener instance.
     */
    @Override
    public void deleteDevice(final BleController bleController, final BleDeviceInfo bleDeviceInfo,
        final VoidResultListener listener) {
        Log.i(TAG, "call deleteDevice");
        bleController.deleteDevice(bleDeviceInfo)
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);
    }

    /**
     * Delete the device information that has been saved.
     *
     * @param bleController BleController instance.
     * @param deviceAddress BleDeviceInfo instance.
     * @param listener VoidResultListener instance.
     */
    @Override
    public void deleteDevice(BleController bleController, String deviceAddress, VoidResultListener listener) {
        Log.i(TAG, "call deleteDeviceByAddress");
        bleController.deleteDevice(deviceAddress)
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);
    }

    /**
     * Inner static class to create {@link BleScanCallback} instance.
     */
    private static final class BleScanCallbackHelper {
        private BleScanResultListener listener;

        BleScanCallbackHelper(final BleScanResultListener listener) {
            this.listener = listener;
        }

        /**
         * Create inner bleScanCallBack instance.
         *
         * @return {@link BleScanCallback} instance.
         */
        BleScanCallback getBleScanCallBack() {
            return new BleScanCallback() {
                @Override
                public void onDeviceDiscover(BleDeviceInfo bleDeviceInfo) {
                    listener.onDeviceDiscover(bleDeviceInfo);
                }

                @Override
                public void onScanEnd() {
                    listener.onScanEnd();
                }
            };
        }
    }
}
