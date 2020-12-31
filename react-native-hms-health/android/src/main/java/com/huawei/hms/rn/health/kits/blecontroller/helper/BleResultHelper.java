/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.health.kits.blecontroller.helper;

import com.facebook.react.bridge.Promise;
import com.huawei.hms.hihealth.data.BleDeviceInfo;
import com.huawei.hms.rn.health.foundation.listener.ResultListener;
import com.huawei.hms.rn.health.foundation.util.ExceptionHandler;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;

import java.util.List;

import static com.huawei.hms.rn.health.foundation.util.MapUtils.wrapWritableObjectWithSuccessStatus;
import static com.huawei.hms.rn.health.kits.blecontroller.util.BleControllerUtils.bleDeviceInfoListToWritableArray;

public class BleResultHelper<T> implements ResultListener<T> {
    //Internal promise instance that will be initialized during construction.
    private final Promise promise;
    //Internal Class type instance that will be initialized during construction.
    private final Class<T> type;

    //Log name for HMSLogger instance
    private final String logName;
    //HMSLogger instance
    private final HMSLogger logger;

    public BleResultHelper(Class<T> classType, final Promise promise, HMSLogger logger, String logName) {
        this.type = classType;
        this.promise = promise;
        this.logger = logger;
        this.logName = logName;
    }

    @Override
    public void onSuccess(T result) {
        if (type.equals(List.class)) {
            promise.resolve(wrapWritableObjectWithSuccessStatus(bleDeviceInfoListToWritableArray((List<BleDeviceInfo>) result), true));
        } else if(type.equals(Boolean.class)) {
            promise.resolve(wrapWritableObjectWithSuccessStatus((Boolean) result, true));
        }

        logger.sendSingleEvent(logName);
    }

    /**
     * Returns exception via Promise instance.
     *
     * @param exception Exception instance.
     */
    @Override
    public void onFail(Exception exception) {
        ExceptionHandler.INSTANCE.fail(exception, promise);
    }
}
