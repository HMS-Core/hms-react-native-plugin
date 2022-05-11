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

package com.huawei.hms.rn.nearby.utils;

import android.text.TextUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.nearby.StatusCode;

public enum HMSResult {
    SUCCESS(StatusCode.STATUS_SUCCESS, "Success"),
    FAILURE(StatusCode.STATUS_FAILURE, "Failure"),
    POLICY_FAIL(8200, "Policy is not valid"),
    STRING_PARAM_FAIL(8201, "String parameter is null or empty"),
    ENDPOINT_ID_FAIL(8202, "Endpoint ids are not valid"),
    BYTES_DATA_FAIL(8203, "Bytes data is empty or exceeds max size"),
    WIFI_NOT_SUPPORT_SHARE(StatusCode.STATUS_WIFI_NOT_SUPPORT_SHARE, "This type of Wi-Fi network cannot be shared."),
    WIFI_MUST_BE_ENABLED(StatusCode.STATUS_WIFI_MUST_BE_ENABLED,
        "Failed to call the Nearby Connection API when the Wi-Fi network is disabled."),
    ANDROID_HMS_RESTRICTED(StatusCode.STATUS_ANDROID_HMS_RESTRICTED, "An error occurred during Nearby so loading.");

    private int statusCode;

    private String resultMessage;

    HMSResult(int statusCode, String resultMessage) {
        this.statusCode = statusCode;
        this.resultMessage = resultMessage;
    }

    /**
     * Getter method for status code
     *
     * @return int
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Getter method for status message
     *
     * @return String
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Creates a result WritableMap
     *
     * @return WritableMap
     */
    public WritableMap getStatusAndMessage() {
        WritableMap wm = Arguments.createMap();
        wm.putInt("status", this.statusCode);
        wm.putString("message", this.resultMessage);
        return wm;
    }

    /**
     * Creates a result WritableMap
     * 
     * @param code Code of result
     * @param message Result message
     *
     * @return WritableMap
     */
    public WritableMap getStatusAndMessage(Integer code, String message) {
        WritableMap wm = Arguments.createMap();
        wm.putInt("status", code == null ? this.statusCode : code);
        wm.putString("message", TextUtils.isEmpty(message) ? this.resultMessage : message);
        return wm;
    }

}
