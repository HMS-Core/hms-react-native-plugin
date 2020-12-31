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

package com.huawei.hms.rn.health.foundation.helper;

import com.facebook.react.bridge.Promise;

import com.huawei.hms.rn.health.foundation.listener.VoidResultListener;
import com.huawei.hms.rn.health.foundation.util.ExceptionHandler;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;

import static com.huawei.hms.rn.health.foundation.util.MapUtils.createWritableMapWithSuccessStatus;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toWritableMap;

/**
 * VoidResultHelper is a helper class for reaching {@link VoidResultListener}.
 *
 * @since v.5.0.1
 */
public class VoidResultHelper implements VoidResultListener {

    //Internal promise instance that will be initialized during construction.
    private final Promise promise;

    private final HMSLogger logger;
    private final String logName;

    public VoidResultHelper(final Promise promise, HMSLogger logger, String logName) {
        this.promise = promise;
        this.logger = logger;
        this.logName = logName;
    }

    /**
     * Returns success result via Promise instance.
     *
     * @param result Health Result instance.
     */
    @Override
    public void onSuccess(Void result) {
        promise.resolve(createWritableMapWithSuccessStatus(true));
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