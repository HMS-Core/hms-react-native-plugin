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

package com.huawei.hms.rn.health.foundation.util;

import static com.huawei.hms.rn.health.foundation.util.MapUtils.addErrorMessage;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.createWritableMapWithSuccessStatus;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.toWritableMapWithMessage;

import android.util.Log;

import com.huawei.hms.common.ApiException;
import com.huawei.hms.rn.health.kits.account.HmsHealthAccount;
import com.huawei.hms.support.hwid.result.HuaweiIdAuthResult;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;

/**
 * {@link ExceptionHandler} simply looks for exception then,
 * whether gives an ApiException log information with return code
 * or throws an exception with localized message.
 *
 * @since v.5.0.1
 */
public enum ExceptionHandler {
    INSTANCE;
    private final String MODULE_NAME = "HMSHealth";

    /**
     * Exception Error Listener.
     */
    public interface ErrorListener {
        /**
         * Error Message Description.
         *
         * @param errMessage String value.
         */
        void onMessageReceived(String errMessage);
    }

    /**
     * Simple handle method, which returns errorListener, onMessageReceived.
     *
     * @param exception Exception instance.
     * @param errorListener ErrorListener instance.
     */
    public synchronized void fail(Exception exception, ErrorListener errorListener) {
        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            Log.i(MODULE_NAME, "returnCode: " + apiException.getStatusCode());
            if (errorListener == null) {
                return;
            }
            errorListener.onMessageReceived(String.valueOf(apiException.getStatusCode()));
        } else {
            Log.e(MODULE_NAME, exception.getMessage());
            if (errorListener == null) {
                return;
            }
            Log.i(MODULE_NAME, "exception: " + exception.getLocalizedMessage());
            errorListener.onMessageReceived(exception.getLocalizedMessage());
        }
    }

    /**
     * Simple handle method.
     *
     * @param exception Exception instance.
     */
    public synchronized void fail(Exception exception) {
        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            Log.i(MODULE_NAME, "returnCode: " + apiException.getStatusCode());
        } else {
            Log.e(MODULE_NAME, exception.getMessage());
        }
    }

    /**
     * Promise handler method, in failure.
     *
     * @param exception Exception instance.
     * @param promise Promise instance.
     */
    public synchronized void fail(final Exception exception, Promise promise) {
        WritableMap writableMap = createWritableMapWithSuccessStatus(false);
        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            Log.i(MODULE_NAME, "returnCode: " + apiException.getStatusCode());
            writableMap.putInt("returnCode", apiException.getStatusCode());
            promise.resolve(writableMap);
            return;
        }
        Log.i(MODULE_NAME, "exception: " + exception.getLocalizedMessage());
        promise.resolve(addErrorMessage(writableMap, exception.getLocalizedMessage()));
    }

    /**
     * Promise handler method, in failure.
     * This method is specifically useful through {@link HmsHealthAccount} operations.
     *
     * @param promise Promise instance.
     * @param result {@link HuaweiIdAuthResult} instance.
     */
    public synchronized void fail(Promise promise, HuaweiIdAuthResult result) {
        WritableMap writableMap = createWritableMapWithSuccessStatus(false);
        writableMap.putInt("statusCode", result.getStatus().getStatusCode());
        promise.resolve(writableMap);
    }

    /**
     * Fails promise with a requested message.
     *
     * @param promise Promise instance.
     * @param message String message.
     */
    public synchronized void fail(Promise promise, String message) {
        promise.resolve(toWritableMapWithMessage(message, false));
    }

    /**
     * Fails promise with {@code "isSuccess", false} params.
     *
     * @param promise Promise instance.
     */
    public synchronized void fail(Promise promise) {
        promise.resolve(createWritableMapWithSuccessStatus(false));
    }

    /**
     * This method is used through Receivers. Checks whether receiver is already registered then,
     * in case the pending intent object is in use, informs the RN Side.
     *
     * @param isRegistered Boolean value, aims to get info for whether receiver is registered or not.
     * @param promise Promise instance.
     */
    public synchronized void failPendingIntent(final boolean isRegistered, final Promise promise) {
        if (isRegistered) {
            Log.d("RegisteringEvent", "There is already an listener");
            promise.resolve(toWritableMapWithMessage("There is already an listener, no need to re listener", true));
            return;
        }
        promise.resolve(toWritableMapWithMessage("There is no listener, no need to un listener", true));
    }
}
