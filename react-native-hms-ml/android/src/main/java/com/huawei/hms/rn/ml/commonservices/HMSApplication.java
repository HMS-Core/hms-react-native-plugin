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

package com.huawei.hms.rn.ml.commonservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.ERROR_CODES;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.STRING_PARAM_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import android.text.TextUtils;

import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSLogger;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;

public class HMSApplication extends HMSBase {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSApplication(ReactApplicationContext reactContext) {
        super(reactContext, HMSApplication.class.getSimpleName(), ERROR_CODES);
    }

    /**
     * Sets the api key dynamically
     *
     * @param apiKey api key
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void setApiKey(String apiKey, final Promise promise) {
        startMethodExecTimer("setApiKey");

        if (TextUtils.isEmpty(apiKey)) {
            handleResult("setApiKey", STRING_PARAM_NULL, promise);
            return;
        }

        MLApplication.getInstance().setApiKey(apiKey);
        handleResult("setApiKey", SUCCESS, promise);
    }

    /**
     * Sets the api key dynamically
     *
     * @param accessToken access token
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void setAccessToken(String accessToken, final Promise promise) {
        startMethodExecTimer("setAccessToken");

        if (TextUtils.isEmpty(accessToken)) {
            handleResult("setAccessToken", STRING_PARAM_NULL, promise);
            return;
        }

        MLApplication.getInstance().setAccessToken(accessToken);
        handleResult("setAccessToken", SUCCESS, promise);
    }

    /**
     * Returns api key that set before
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getApiKey(final Promise promise) {
        startMethodExecTimer("getApiKey");
        handleResult("getApiKey",
            HMSResultCreator.getInstance().getStringResult(MLApplication.getInstance().getApiKey()), promise);
    }

    /**
     * Enables logging
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void enableLogger(final Promise promise) {
        HMSLogger.getInstance(getContext()).enableLogger();
        handleResult("enableLogger", SUCCESS, promise);
    }

    /**
     * Disables logging
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void disableLogger(final Promise promise) {
        HMSLogger.getInstance(getContext()).disableLogger();
        handleResult("disableLogger", SUCCESS, promise);
    }
}
