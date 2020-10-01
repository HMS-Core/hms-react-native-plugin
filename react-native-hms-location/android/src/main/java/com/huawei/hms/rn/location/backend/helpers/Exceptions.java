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

package com.huawei.hms.rn.location.backend.helpers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Exceptions {
    public static final int ERR_GENERIC = 800;
    public static final int ERR_JSON = 801;
    public static final int ERR_NO_PERMISSION = 802;
    public static final int ERR_NO_FUSED_LOCATION_PROVIDER = 803;
    public static final int ERR_EMPTY_CALLBACK = 804;
    public static final int ERR_NO_HW_LOCATION = 805;
    public static final int ERR_NO_EXISTENT_REQUEST_ID = 806;
    public static final int ERR_DUPLICATE_ID = 807;
    public static final int ERR_RESOLUTION_FAILED = 808;
    public static final int ERR_PENDING_RESOLUTION = 809;
    public static final int ERR_NULL_VALUE = 810;

    static final Map<Integer, String> ERROR_MSGS = new HashMap<>();

    static {
        ERROR_MSGS.put(ERR_NO_PERMISSION, "App does not have location permission");
        ERROR_MSGS.put(ERR_EMPTY_CALLBACK, "Callback is empty");
        ERROR_MSGS.put(ERR_NO_EXISTENT_REQUEST_ID, "RequestId does not in Geofence list");
        ERROR_MSGS.put(ERR_DUPLICATE_ID, "Callback id already exist");
        ERROR_MSGS.put(ERR_NO_HW_LOCATION, "HWLocation is null");
        ERROR_MSGS.put(ERR_RESOLUTION_FAILED, "Resolution failed.");
        ERROR_MSGS.put(ERR_PENDING_RESOLUTION, "Error occurred, a resolution is available and being applied.");
        ERROR_MSGS.put(ERR_NULL_VALUE, "Result from location kit is null.");
        ERROR_MSGS.put(ERR_JSON, "JSON Error.");
    }

    public Exceptions() {
    }

    public static JSONObject toErrorJSON(int errorCode) {
        try {
            return new JSONObject()
                    .put("errorCode", errorCode)
                    .put("errorMessage", ERROR_MSGS.get(errorCode));
        } catch (JSONException e) {
            Log.e("toErrorJSON get error: ", e.getMessage());
        }
        return new JSONObject();
    }

    public static JSONObject toErrorJSON(int errorCode, Throwable t) {
        try {
            return new JSONObject()
                    .put("errorCode", errorCode)
                    .put("errorMessage", t.getMessage());
        } catch (JSONException e) {
            Log.e("toErrorJSON get error: ", e.getMessage());
        }

        return new JSONObject();
    }
}
