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

package com.huawei.hms.rn.health.kits.settings.viewmodel;

import android.util.Log;

import com.huawei.hms.hihealth.SettingController;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.options.DataTypeAddOptions;
import com.huawei.hms.rn.health.foundation.listener.ResultListener;
import com.huawei.hms.rn.health.foundation.listener.VoidResultListener;
import com.huawei.hms.rn.health.kits.settings.HmsSettingController;

/**
 * All the tasks for {@link HmsSettingController} methods
 * are used in {@link SettingsViewModel} class.
 *
 * @since v.5.0.1
 */
public class SettingsViewModel implements SettingsService {
    private static final String TAG = SettingsViewModel.class.getSimpleName();

    /**
     * Creates and adds a customized data type.
     * The name of the created data type must be prefixed with the package name of the app.
     * Otherwise, the creation fails.
     *
     * @param settingController SettingController instance.
     * @param dataTypeAddOptions DataTypeAddOptions instance.
     * @param listener ResultListener<DataType> instance.
     */
    @Override
    public void addNewDataType(final SettingController settingController, final DataTypeAddOptions dataTypeAddOptions,
        ResultListener<DataType> listener) {
        Log.i(TAG, "call addNewDataType");

        // create SettingController and add new DataType
        settingController.addDataType(dataTypeAddOptions)
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);
    }

    /**
     * Reads the data type based on the data type name.
     * This method is used to read the customized data types of the app.
     *
     * @param settingController SettingController instance.
     * @param dataTypeName String value of DataType name.
     * @param listener ResultListener<DataType> instance.
     */
    @Override
    public void readDataType(final SettingController settingController, final String dataTypeName,
        ResultListener<DataType> listener) {
        Log.i(TAG, "call readDataType");

        // create SettingController and get DataType with the specified name
        settingController.readDataType(dataTypeName)
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);
    }

    /**
     * Disables the Health Kit function, cancels user authorization, and cancels all data records.
     * (The task takes effect in 24 hours.)
     *
     * @param settingController SettingController instance.
     * @param listener VoidResultListener instance.
     */
    @Override
    public void disableHiHealth(final SettingController settingController, VoidResultListener listener) {
        Log.i(TAG, "call disableHiHealth");

        // create SettingController and get DataType with the specified name
        settingController.disableHiHealth()
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);
    }

    /**
     * Checks the user privacy authorization to Health Kit.
     * If the authorization has not been granted, the user will be redirected to the authorization screen
     * where they can authorize the Huawei Health app to open data to Health Kit.
     *
     * @param settingController SettingController instance.
     * @param listener VoidResultListener instance.
     */
    @Override
    public void checkHealthAppAuthorization(SettingController settingController, VoidResultListener listener) {

        settingController.checkHealthAppAuthorization()
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);
    }

    /**
     * Checks the user privacy authorization to Health Kit. Task returns true if authorized, false if unauthorized.
     *
     * @param settingController SettingController instance.
     * @param listener ResultListener instance.
     */
    @Override
    public void getHealthAppAuthorization(SettingController settingController, ResultListener<Boolean> listener) {

        settingController.getHealthAppAuthorization()
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);
    }
}