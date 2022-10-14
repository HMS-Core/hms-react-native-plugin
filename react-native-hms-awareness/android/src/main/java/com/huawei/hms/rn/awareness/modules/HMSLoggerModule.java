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

package com.huawei.hms.rn.awareness.modules;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.hms.rn.awareness.logger.HMSLogger;
import com.huawei.hms.rn.awareness.utils.DataUtils;

import static com.huawei.hms.rn.awareness.utils.DataUtils.errorMessage;

public class HMSLoggerModule extends ReactContextBaseJavaModule {

    ReactContext context;
    String TAG = "HMSLoggerModule";

    public HMSLoggerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSLoggerModule";
    }

    /**
     * Enables logging.
     *
     * @param promise: WritableMap
     */
    @ReactMethod
    public void enableLogger(Promise promise) {
        String method = "enableLogger";
        try {
            HMSLogger.getInstance(context).enableLogger();
            DataUtils.valueConvertToMap("HMSLogger", true, "enableLogger", promise);
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }

    /**
     * Disables logging.
     *
     * @param promise: WritableMap
     */
    @ReactMethod
    public void disableLogger(Promise promise) {
        String method = "disableLogger";
        try {
            HMSLogger.getInstance(context).disableLogger();
            DataUtils.valueConvertToMap("HMSLogger", false, "disableLogger", promise);
        } catch (IllegalArgumentException e) {
            errorMessage(context, method, TAG, e, promise);
        }
    }
}

