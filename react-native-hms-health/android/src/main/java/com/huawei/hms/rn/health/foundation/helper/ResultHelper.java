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

package com.huawei.hms.rn.health.foundation.helper;

import static com.huawei.hms.rn.health.foundation.util.MapUtils.toWritableArray;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toWritableMap;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toWritableMapWithMessage;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.wrapWritableObjectWithSuccessStatus;

import com.huawei.hms.hihealth.data.ActivityRecord;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.SampleSet;
import com.huawei.hms.hihealth.data.ScopeLangItem;
import com.huawei.hms.hihealth.result.ActivityRecordReply;
import com.huawei.hms.hihealth.result.HealthRecordReply;
import com.huawei.hms.hihealth.result.ReadReply;
import com.huawei.hms.rn.health.foundation.listener.ResultListener;
import com.huawei.hms.rn.health.foundation.util.ExceptionHandler;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;

import com.facebook.react.bridge.Promise;

import java.util.List;

/**
 * ResultHelper<T> is a helper class for reaching {@link ResultListener<T>}.
 * </br>
 * All the generic result request types will be converted into writable map and
 * returned via Promise instance.
 *
 * @since v.5.0.1
 */
public final class ResultHelper<T> implements ResultListener<T> {
    // Internal promise instance that will be initialized during construction.
    private final Promise promise;

    // Internal Class type instance that will be initialized during construction.
    private final Class<T> type;

    // Log name for HMSLogger instance
    private final String logName;

    // HMSLogger instance
    private final HMSLogger logger;

    public ResultHelper(Class<T> classType, final Promise promise, final HMSLogger logger, final String logName) {
        this.type = classType;
        this.promise = promise;
        this.logger = logger;
        this.logName = logName;
    }

    /**
     * Looks for class type, then
     * Returns success result via Promise instance.
     *
     * @param result Health Result instance.
     */
    @Override
    public void onSuccess(T result) {
        if (type.equals(String.class)) {
            promise.resolve(toWritableMapWithMessage((String) result, true));
        } else if (type.equals(ActivityRecordReply.class)) {
            promise.resolve(wrapWritableObjectWithSuccessStatus(toWritableArray((ActivityRecordReply) result), true));
        } else if (type.equals(List.class)) {
            promise.resolve(wrapWritableObjectWithSuccessStatus(toWritableArray((List<ActivityRecord>) result), true));
        } else if (type.equals(ReadReply.class)) {
            promise.resolve(wrapWritableObjectWithSuccessStatus(toWritableMap((ReadReply) result), true));
        } else if (type.equals(SampleSet.class)) {
            promise.resolve(wrapWritableObjectWithSuccessStatus(toWritableMap((SampleSet) result), true));
        } else if (type.equals(Boolean.class)) {
            promise.resolve(wrapWritableObjectWithSuccessStatus((Boolean) result, true));
        } else if (type.equals(ScopeLangItem.class)) {
            promise.resolve(wrapWritableObjectWithSuccessStatus(toWritableMap((ScopeLangItem) result), true));
        } else if (type.equals(DataType.class)) {
            promise.resolve(wrapWritableObjectWithSuccessStatus(toWritableMap((DataType) result), true));
        } else if (type.equals(HealthRecordReply.class)) {
            promise.resolve(wrapWritableObjectWithSuccessStatus(toWritableArray((HealthRecordReply) result), true));
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
