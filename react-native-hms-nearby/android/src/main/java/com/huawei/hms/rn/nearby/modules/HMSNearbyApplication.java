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

package com.huawei.hms.rn.nearby.modules;

import android.text.TextUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.nearby.Nearby;
import com.huawei.hms.nearby.NearbyApiContext;

import static com.huawei.hms.rn.nearby.constants.HMSConstants.APPLICATION_CONSTANTS;
import static com.huawei.hms.rn.nearby.utils.HMSResult.STRING_PARAM_FAIL;
import static com.huawei.hms.rn.nearby.utils.HMSResult.SUCCESS;

public class HMSNearbyApplication extends HMSBase {

    /**
     * Constructor that initializes application module
     *
     * @param mContext app context
     */
    public HMSNearbyApplication(ReactApplicationContext mContext) {
        super(mContext, HMSNearbyApplication.class.getSimpleName(), APPLICATION_CONSTANTS);
    }

    /**
     * Enables logging feature for method calls
     * 
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void enableLogger(final Promise promise) {
        getLogger().enableLogger();
        promise.resolve(SUCCESS.getStatusAndMessage());
    }

    /**
     * Disables logging feature for method calls
     * 
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void disableLogger(final Promise promise) {
        getLogger().disableLogger();
        promise.resolve(SUCCESS.getStatusAndMessage());
    }

    /**
     * Sets the API credential for your app.
     *
     * @param apiKey api key string
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void setApiKey(String apiKey, final Promise promise) {
        startMethodExecTimer("setApiKey");

        if (TextUtils.isEmpty(apiKey)) {
            handleResult("setApiKey", STRING_PARAM_FAIL, promise);
            return;
        }

        NearbyApiContext.getInstance().setApiKey(apiKey);
        handleResult("setApiKey", SUCCESS, promise);
    }

    /**
     * Obtains the current API credential.
     * 
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getApiKey(final Promise promise) {
        startMethodExecTimer("getApiKey");
        String apiKey = NearbyApiContext.getInstance().getApiKey();
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putString("result", TextUtils.isEmpty(apiKey) ? "" : apiKey);
        getLogger().sendSingleEvent("getApiKey");
        promise.resolve(wm);
    }

    /**
     * Obtains the Nearby Service SDK version number.
     * 
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getVersion(final Promise promise) {
        startMethodExecTimer("getVersion");
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putString("result", Nearby.getVersion());
        getLogger().sendSingleEvent("getVersion");
        promise.resolve(wm);
    }

}
