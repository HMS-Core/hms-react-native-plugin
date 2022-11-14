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

package com.huawei.hms.rn.health.kits.settings;

import com.huawei.hms.hihealth.HiHealthOptions;
import com.huawei.hms.hihealth.HuaweiHiHealth;
import com.huawei.hms.hihealth.SettingController;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.Field;
import com.huawei.hms.hihealth.options.DataTypeAddOptions;
import com.huawei.hms.rn.health.foundation.helper.ResultHelper;
import com.huawei.hms.rn.health.foundation.helper.VoidResultHelper;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;
import com.huawei.hms.rn.health.foundation.util.MapUtils;
import com.huawei.hms.rn.health.foundation.util.Utils;
import com.huawei.hms.rn.health.foundation.view.BaseController;
import com.huawei.hms.rn.health.kits.settings.viewmodel.SettingsService;
import com.huawei.hms.rn.health.kits.settings.viewmodel.SettingsViewModel;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

/**
 * {@link HmsSettingController} class is a module that refers to {@link SettingController}
 *
 * @since v.5.0.1
 */
public class HmsSettingController extends BaseController {

    private static final String TAG = HmsSettingController.class.getSimpleName();

    // Internal context object
    private final ReactApplicationContext reactContext;

    // ViewModel instance to reach SettingController tasks
    private SettingsService settingsViewModel;

    // Huawei Account authentication and identification information
    private AuthHuaweiId signInHuaweiId;

    private final HMSLogger logger;

    /**
     * Initialization
     */
    public HmsSettingController(ReactApplicationContext reactContext) {
        super(TAG, reactContext);
        this.reactContext = reactContext;
        settingsViewModel = new SettingsViewModel();
        logger = HMSLogger.getInstance(reactContext);
        initSettingController();
    }

    /**
     * Creates and adds a customized data type.
     * The name of the created data type must be prefixed with the package name of the app.
     * Otherwise, the creation fails.
     *
     * @param dataTypeName String value of DataType name.
     * @param fieldTypes String value of Field Type.
     * @param promise In the success scenario, {@link DataType} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void addNewDataType(final String dataTypeName, final ReadableArray fieldTypes, final Promise promise) {
        String logName = "HmsSettingController.addNewDataType";
        logger.startMethodExecutionTimer(logName);

        checkSettingController();
        // get DataType name from EditText view,
        // The name must start with package name, and End with a custom name.
        DataTypeAddOptions.Builder builder = new DataTypeAddOptions.Builder().setName(dataTypeName);
        // create DataTypeAddOptions,You must specify the Field that you want to add,
        // You can add multiple Fields here.
        if (fieldTypes != null) {
            for (int i = 0; i < fieldTypes.size(); i++) {
                Field requestedField = Utils.INSTANCE.toFieldType(fieldTypes.getString(i));
                builder.addField(requestedField);
            }
        }
        DataTypeAddOptions dataTypeAddOptions = builder.build();

        // create SettingController and add new DataType
        // The added results are displayed in the phone screen
        settingsViewModel.addNewDataType(HuaweiHiHealth.getSettingController(reactContext, signInHuaweiId),
            dataTypeAddOptions, new ResultHelper<>(DataType.class, promise, logger, logName));

    }

    /**
     * Reads the data type based on the data type name.
     * This method is used to read the customized data types of the app.
     *
     * @param dataTypeName String value of DataType name.
     * @param promise In the success scenario, {@link DataType} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void readDataType(final String dataTypeName, final Promise promise) {
        String logName = "HmsSettingController.readDataType";
        logger.startMethodExecutionTimer(logName);

        checkSettingController();
        // create SettingController and get the DataType with requested dataTypeName
        settingsViewModel.readDataType(HuaweiHiHealth.getSettingController(reactContext, signInHuaweiId), dataTypeName,
            new ResultHelper<>(DataType.class, promise, logger, logName));
    }

    /**
     * Disables the Health Kit function, cancels user authorization, and cancels all data records.
     * (The task takes effect in 24 hours.)
     *
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void disableHiHealth(final Promise promise) {
        String logName = "HmsSettingController.disableHiHealth";
        logger.startMethodExecutionTimer(logName);

        checkSettingController();

        // create SettingController and disable HiHealth (cancel All your Records).
        settingsViewModel.disableHiHealth(HuaweiHiHealth.getSettingController(reactContext, signInHuaweiId),
            new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Checks the user privacy authorization to Health Kit.
     * If the authorization has not been granted, the user will be redirected to the authorization screen
     * where they can authorize the Huawei Health app to open data to Health Kit.
     *
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void checkHealthAppAuthorization(final Promise promise) {
        String logName = "HmsSettingController.checkHealthAppAuthorization";
        logger.startMethodExecutionTimer(logName);

        checkSettingController();

        settingsViewModel.checkHealthAppAuthorization(HuaweiHiHealth.getSettingController(reactContext, signInHuaweiId),
            new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Checks the user privacy authorization to Health Kit. Task returns true if authorized, false if unauthorized.
     *
     * @param promise In the success scenario, {@link Boolean} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void getHealthAppAuthorization(final Promise promise) {
        String logName = "HmsSettingController.getHealthAppAuthorization";
        logger.startMethodExecutionTimer(logName);

        checkSettingController();

        settingsViewModel.getHealthAppAuthorization(HuaweiHiHealth.getSettingController(reactContext, signInHuaweiId),
            new ResultHelper<>(Boolean.class, promise, logger, logName));
    }

    /**
     * Enables HMSLogger
     *
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void enableLogger(final Promise promise) {
        String logName = "HmsSettingController.enableLogger";
        logger.startMethodExecutionTimer(logName);

        logger.enableLogger();
        promise.resolve(MapUtils.createWritableMapWithSuccessStatus(true));
        logger.sendSingleEvent(logName);
    }

    /**
     * Disables HMSLogger
     *
     * @param promise In the success scenario, {@link Void} instance is returned with {@code isSuccess: true} params , or Exception is returned in the failure scenario.
     */
    @ReactMethod
    public void disableLogger(final Promise promise) {
        String logName = "HmsSettingController.disableLogger";
        logger.startMethodExecutionTimer(logName);

        logger.disableLogger();
        promise.resolve(MapUtils.createWritableMapWithSuccessStatus(true));
        logger.sendSingleEvent(logName);
    }

    /* Private Methods */

    /**
     * Initialize variable of mSignInHuaweiId.
     */
    private void initSettingController() {
        // create HiHealth Options, do not add any data type here.
        HiHealthOptions hiHealthOptions = HiHealthOptions.builder().build();
        // get AuthHuaweiId by HiHealth Options.
        signInHuaweiId = HuaweiIdAuthManager.getExtendedAuthResult(hiHealthOptions);
    }

    /**
     * Check whether dataController is initialized, or not.
     */
    private void checkSettingController() {
        if (this.signInHuaweiId == null) {
            initSettingController();
        }
    }
}
