/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.iap.client;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.huawei.hms.iap.IapApiException;
import com.huawei.hms.iap.entity.OrderStatusCode;

/**
 * ExceptionHandler simply looks for exception then whether gives an IapApiException log information with return code
 * or throws an exception with localized message.
 *
 * @since v.5.0.0
 */
public class ExceptionHandler {
    private static final String TAG = ExceptionHandler.class.getSimpleName();

    public static void handle(Exception exception, Promise promise) {
        if (exception instanceof IapApiException) {
            IapApiException iapApiException = (IapApiException) exception;
            Log.i(TAG, "returnCode: " + iapApiException.getStatusCode());
            switch (iapApiException.getStatusCode()) {
                case OrderStatusCode.ORDER_STATE_CANCEL:
                    Log.i(TAG, "Order state has been canceled.");
                    break;
                case OrderStatusCode.ORDER_STATE_PARAM_ERROR:
                    Log.i(TAG, "Order state param error.");
                    break;
                case OrderStatusCode.ORDER_STATE_NET_ERROR:
                    Log.i(TAG, "Order state net error.");
                    break;
                case OrderStatusCode.ORDER_VR_UNINSTALL_ERROR:
                    Log.i(TAG, "Order vr uninstall error.");
                    break;
                case OrderStatusCode.ORDER_HWID_NOT_LOGIN:
                    Log.i(TAG, "User is not logged in.");
                    break;
                case OrderStatusCode.ORDER_PRODUCT_OWNED:
                    Log.i(TAG, "Product is already owned error.");
                    break;
                case OrderStatusCode.ORDER_PRODUCT_NOT_OWNED:
                    Log.i(TAG, "Product is not owned error.");
                    break;
                case OrderStatusCode.ORDER_PRODUCT_CONSUMED:
                    Log.i(TAG, "Product is consumed error.");
                    break;
                case OrderStatusCode.ORDER_ACCOUNT_AREA_NOT_SUPPORTED:
                    Log.i(TAG, "Order account area is not supported error.");
                    break;
                case OrderStatusCode.ORDER_NOT_ACCEPT_AGREEMENT:
                    Log.i(TAG, "User does not accept the agreement.");
                    break;
                default:
                    Log.i(TAG, "OrderStatusCode unknown error.");
                    break;
            }
            promise.reject(String.valueOf(iapApiException.getStatusCode()), exception);
        } else {
            Log.e(TAG, exception.getMessage());
            promise.reject(exception.getLocalizedMessage(), exception);
        }
    }

    static void handle(Exception exception) {
        Log.e(TAG, exception.getLocalizedMessage());
    }
}

