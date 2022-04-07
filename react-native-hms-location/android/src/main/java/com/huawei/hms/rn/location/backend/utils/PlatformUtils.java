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

package com.huawei.hms.rn.location.backend.utils;

import static com.huawei.hms.rn.location.backend.helpers.Exceptions.ERR_GENERIC;
import static com.huawei.hms.rn.location.backend.helpers.Exceptions.ERR_NULL_VALUE;
import static com.huawei.hms.rn.location.backend.helpers.Exceptions.ERR_RESOLUTION_FAILED;

import android.content.IntentSender;
import android.os.Build;
import android.util.Log;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.ResolvableApiException;
import com.huawei.hms.location.LocationSettingsStatusCodes;
import com.huawei.hms.rn.location.backend.helpers.Exceptions;
import com.huawei.hms.rn.location.backend.interfaces.HMSCallback;
import com.huawei.hms.rn.location.backend.interfaces.JSONMapper;
import com.huawei.hms.rn.location.backend.interfaces.JSONTriMapper;
import com.huawei.hms.rn.location.backend.interfaces.Mapper;
import com.huawei.hms.rn.location.backend.interfaces.TriMapper;
import com.huawei.hms.rn.location.backend.logger.HMSMethod;

import com.facebook.react.bridge.ReactApplicationContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlatformUtils {
    private final static String TAG = PlatformUtils.class.getSimpleName();

    public static final boolean GE_OREO = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;

    public static final int RESOLUTION_REQUEST = 0;

    public static <T> JSONObject keyValPair(String key, T val) {
        JSONObject map = new JSONObject();
        try {
            map.put(key, val);
        } catch (JSONException e) {
            Log.d(TAG, "JSONException :: " + e.getMessage());
        }
        return map;
    }

    public static JSONObject fromMapToJSONObject(Map<String, Object> map) {
        JSONObject writableMap = new JSONObject();
        if (map == null) {
            return writableMap;
        }

        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                writableMap.put(entry.getKey(), entry.getValue().toString());
            }
        } catch (JSONException e) {
            Log.d(TAG, "JSONException :: " + e.getMessage());
        }
        return writableMap;
    }

    public static <R> List<R> mapJSONArray(JSONArray array, Mapper<JSONObject, R> mapper) {
        List<R> list = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                list.add(mapper.map(array.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.d(TAG, "JSONException :: " + e.getMessage());
        }
        return list;
    }

    public static <T> JSONArray mapList(List<T> list, Mapper<T, Object> mapper) {
        JSONArray array = new JSONArray();
        for (T item : list) {
            array.put(mapper.map(item));
        }
        return array;
    }

    public static <T> OnSuccessListener<T> successListener(HMSMethod method, ReactApplicationContext context,
        HMSCallback callback) {
        return value -> {
            Log.d(TAG, "successListener()");
            method.sendLoggerEvent(context);
            callback.success();
        };
    }

    public static <T> OnSuccessListener<T> successListener(HMSMethod method, ReactApplicationContext context,
        HMSCallback callback, Mapper<T, Object> mapper) {
        return value -> {
            Log.d(TAG, "successListener()");
            if (value == null) {
                Log.e(TAG, "Value is null.");
                callback.error(Exceptions.toErrorJSON(ERR_NULL_VALUE));
                method.sendLoggerEvent(context, "-1");
                return;
            }

            method.sendLoggerEvent(context);

            if (mapper.map(value) instanceof JSONObject) {
                callback.success((JSONObject) mapper.map(value));
            } else if (mapper.map(value) instanceof JSONArray) {
                callback.success((JSONArray) mapper.map(value));
            }
        };
    }

    public static <T> OnSuccessListener<T> successListener(HMSMethod method, ReactApplicationContext context,
        HMSCallback callback, JSONObject json) {
        return value -> {
            Log.d(TAG, "successListener()");
            method.sendLoggerEvent(context);
            callback.success(json);
        };
    }

    public static OnFailureListener failureListener(HMSMethod method, ReactApplicationContext context,
        HMSCallback callback) {
        return e -> {
            Log.d(TAG, "failureListener() :: " + e.getMessage());

            JSONObject genericEx = Exceptions.toErrorJSON(ERR_GENERIC, e);
            if (!(e instanceof ApiException)) {
                Log.d(TAG, ">> not an api exception");
                method.sendLoggerEvent(context, "-1");
                callback.error(genericEx);
                return;
            }

            int statusCode = ((ApiException) e).getStatusCode();
            if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                if (!(e instanceof ResolvableApiException)) {
                    Log.d(TAG, ">> not an resolvable api exception");
                    method.sendLoggerEvent(context, String.valueOf(statusCode));
                    callback.error(genericEx);
                    return;
                }

                try {
                    // callback will be used after resolution
                    ((ResolvableApiException) e).startResolutionForResult(context.getCurrentActivity(),
                        RESOLUTION_REQUEST);
                    method.sendLoggerEvent(context, String.valueOf(statusCode));
                } catch (IntentSender.SendIntentException ex) {
                    Log.e(TAG, ">> " + ex.getMessage());
                    method.sendLoggerEvent(context, String.valueOf(statusCode));
                    callback.error(Exceptions.toErrorJSON(ERR_RESOLUTION_FAILED));
                }
            } else {
                method.sendLoggerEvent(context, String.valueOf(statusCode));
                callback.error(genericEx);
            }
        };
    }

    public static <T, R> Mapper<T, R> mapperWrapper(JSONMapper<T, R> jm) {
        return mapperWrapper(jm, null);
    }

    public static <T, R> Mapper<T, R> mapperWrapper(JSONMapper<T, R> jm, R def) {
        return arg -> {
            if (arg == null) {
                return null;
            }

            try {
                return jm.map(arg);
            } catch (JSONException | NullPointerException e) {
                Log.e(TAG, "wrapper :: JSONException, " + e.getMessage());
                return def;
            }
        };
    }

    public static <T, U, V, R> TriMapper<T, U, V, R> triMapperWrapper(JSONTriMapper<T, U, V, R> jtm) {
        return triMapperWrapper(jtm, null);
    }

    public static <T, U, V, R> TriMapper<T, U, V, R> triMapperWrapper(JSONTriMapper<T, U, V, R> jtm, R def) {
        return (arg1, arg2, arg3) -> {
            if (arg1 == null || arg2 == null || arg3 == null) {
                return null;
            }

            try {
                return jtm.run(arg1, arg2, arg3);
            } catch (JSONException | NullPointerException e) {
                Log.e(TAG, "wrapper :: JSONException, " + e.getMessage());
                return def;
            }
        };
    }
}
