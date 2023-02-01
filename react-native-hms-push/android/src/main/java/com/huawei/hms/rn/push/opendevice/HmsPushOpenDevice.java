/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.push.opendevice;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.opendevice.OpenDevice;

import com.huawei.hms.rn.push.logger.HMSLogger;
import com.huawei.hms.rn.push.utils.ActivityUtils;
import com.huawei.hms.rn.push.utils.ResultUtils;
import com.huawei.hms.support.api.opendevice.OdidResult;

import java.util.HashMap;
import java.util.Map;

public class HmsPushOpenDevice extends ReactContextBaseJavaModule {
    private final String TAG = HmsPushOpenDevice.class.getSimpleName();

    private static volatile ReactApplicationContext context;

    public HmsPushOpenDevice(ReactApplicationContext reactContext) {

        super(reactContext);
        setContext(reactContext);
    }

    @Override
    public Map<String, Object> getConstants() {

        return new HashMap<>();
    }

    @Override
    public void initialize() {

        super.initialize();
    }

    @Override
    public String getName() {

        return TAG;
    }

    public static void setContext(ReactApplicationContext context) {
        HmsPushOpenDevice.context = context;
    }

    public static ReactApplicationContext getContext() {
        return HmsPushOpenDevice.context;
    }

    @ReactMethod
    public void getOdid(final Promise promise) {

        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("getOdid");
        Task<OdidResult> idResult = OpenDevice.getOpenDeviceClient(ActivityUtils.getRealActivity(getCurrentActivity(), getContext())).getOdid();
        idResult
            .addOnSuccessListener(result -> {
                HMSLogger.getInstance(getContext()).sendSingleEvent("getOdid");
                ResultUtils.handleResult(true, result.getId(), promise);
            })
            .addOnFailureListener(e -> {
                HMSLogger.getInstance(getContext()).sendSingleEvent("getOdid");
                ResultUtils.handleResult(false, e.getLocalizedMessage(), promise);
            });
    }

}
