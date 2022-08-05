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

package com.huawei.hms.rn.ml.helpers.transactors;

import com.huawei.hms.rn.ml.helpers.utils.HMSLogger;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class HMSBaseTransactor {
    private ReactApplicationContext applicationContext;

    private HMSLogger logger;

    HMSBaseTransactor(ReactApplicationContext context) {
        applicationContext = context;
        logger = HMSLogger.getInstance(applicationContext);
    }

    protected void sendEvent(String eventName, String methodName, WritableMap params) {
        logger.sendSingleEvent(methodName);
        applicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
