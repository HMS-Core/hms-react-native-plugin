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

package com.huawei.hms.rn.health.kits.consents;

import androidx.annotation.NonNull;

import com.huawei.hms.hihealth.HiHealthOptions;
import com.huawei.hms.hihealth.HuaweiHiHealth;
import com.huawei.hms.hihealth.data.ScopeLangItem;
import com.huawei.hms.rn.health.foundation.helper.ResultHelper;
import com.huawei.hms.rn.health.foundation.helper.VoidResultHelper;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;
import com.huawei.hms.rn.health.foundation.util.MapUtils;
import com.huawei.hms.rn.health.foundation.view.BaseController;
import com.huawei.hms.rn.health.kits.consents.viewmodel.ConsentsService;
import com.huawei.hms.rn.health.kits.consents.viewmodel.ConsentsViewModel;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

import java.util.List;

public class HmsConsentsController extends BaseController {

    private static final String TAG = HmsConsentsController.class.getSimpleName();

    // Internal context object
    private final ReactApplicationContext reactContext;

    // ViewModel instance to reach SettingController tasks
    private ConsentsService consentsViewModel;

    // Huawei Account authentication and identification information
    private AuthHuaweiId signInHuaweiId;

    private HMSLogger logger;

    public HmsConsentsController(@NonNull ReactApplicationContext reactContext) {
        super(TAG, reactContext);
        this.reactContext = reactContext;
        consentsViewModel = new ConsentsViewModel();
        logger = HMSLogger.getInstance(reactContext);
        initConsentsController();
    }

    /**
     * Queries the list of permissions granted to your app.
     *
     * @param language Language code. If the specified value is invalid, "en-us" will be used.
     * @param appId ID of your app.
     * @param promise React promise Object
     */
    @ReactMethod
    public void get(String language, String appId, Promise promise) {
        String logName = "HmsConsentsController.get";
        logger.startMethodExecutionTimer(logName);

        checkConsentsController();

        consentsViewModel.get(HuaweiHiHealth.getConsentsController(reactContext, signInHuaweiId), language, appId,
            new ResultHelper<>(ScopeLangItem.class, promise, logger, logName));
    }

    /**
     * Revokes certain Health Kit related permissions granted to your app.
     *
     * @param appId ID of your app.
     * @param scopeArray List of Health Kit related permissions to be revoked. The value is the key value of url2Desc in ScopeLangItem.
     * @param promise React Promise Object
     */
    @ReactMethod
    public void revoke(String appId, ReadableArray scopeArray, Promise promise) {
        String logName = "HmsConsentsController.revoke";
        logger.startMethodExecutionTimer(logName);

        checkConsentsController();

        List<String> scopeList = null;
        if (scopeArray != null) {
            scopeList = MapUtils.toStringList(scopeArray);
        }

        consentsViewModel.revoke(HuaweiHiHealth.getConsentsController(reactContext, signInHuaweiId), appId, scopeList,
            new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Cancels certain Health Kit related scopes granted to your app.
     *
     * @param appId ID of your app.
     * @param scopeArray List of Health Kit related permissions to be revoked. The value is the key value of url2Desc in ScopeLangItem.
     * @param promise React Promise Object
     */
    @ReactMethod
    public void cancelAuthorization(String appId, ReadableArray scopeArray, Promise promise) {
        String logName = "HmsConsentsController.cancelAuthorization";
        logger.startMethodExecutionTimer(logName);

        checkConsentsController();

        List<String> scopeList = null;
        if (scopeArray != null) {
            scopeList = MapUtils.toStringList(scopeArray);
        }

        consentsViewModel.cancelAuthorization(HuaweiHiHealth.getConsentsController(reactContext, signInHuaweiId), appId,
            scopeList, new VoidResultHelper(promise, logger, logName));
    }

    /**
     * Specifies whether to delete user data when all scopes granted to your app are canceled.
     *
     * @param deleteData Whether to delete user data.
     * @param promise React Promise Object
     */
    @ReactMethod
    public void cancelAuthorizationAll(boolean deleteData, Promise promise) {
        String logName = "HmsConsentsController.cancelAuthorizationAll";
        logger.startMethodExecutionTimer(logName);

        checkConsentsController();

        consentsViewModel.cancelAuthorizationAll(HuaweiHiHealth.getConsentsController(reactContext, signInHuaweiId),
            deleteData, new VoidResultHelper(promise, logger, logName));
    }

    /* Private Methods */

    /**
     * Initialize variable of mSignInHuaweiId.
     */
    private void initConsentsController() {
        // create HiHealth Options, do not add any data type here.
        HiHealthOptions hiHealthOptions = HiHealthOptions.builder().build();
        // get AuthHuaweiId by HiHealth Options.
        signInHuaweiId = HuaweiIdAuthManager.getExtendedAuthResult(hiHealthOptions);
    }

    /**
     * Check whether consentsController is initialized, or not.
     */
    private void checkConsentsController() {
        if (this.signInHuaweiId == null) {
            initConsentsController();
        }
    }

}
