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
package com.huawei.hms.rn.iap.client.utils;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.iap.IapApiException;

import static com.huawei.hms.rn.iap.client.utils.MapUtil.addErrorMessage;
import static com.huawei.hms.rn.iap.client.utils.MapUtil.createWritableMapWithSuccessStatus;

/**
 * ExceptionHandler simply looks for exception then whether gives an IapApiException log information with return code
 * or throws an exception with localized message.
 *
 * @since v.5.0.0
 */
public enum ExceptionHandler {
    INSTANCE;
    public static final String TAG = ExceptionHandler.class.getSimpleName();

    /**
     * Promise handler method, in failure.
     *
     * @param exception Exception instance.
     * @param promise   Promise instance.
     */
    public synchronized static void handle(Exception exception, Promise promise) {
        WritableMap writableMap = createWritableMapWithSuccessStatus(false);
        if (exception instanceof IapApiException) {
            IapApiException iapApiException = (IapApiException) exception;
            Log.i(TAG, "returnCode: " + iapApiException.getStatusCode());
            writableMap.putInt("statusCode", iapApiException.getStatusCode());
            promise.resolve(writableMap);
        } else {
            promise.resolve(addErrorMessage(writableMap, exception.getLocalizedMessage()));
        }
    }

    /**
     * Exception logger.
     *
     * @param exception Exception instance.
     */
    public synchronized void handle(Exception exception) {
        Log.e(TAG, exception.toString());
    }
}