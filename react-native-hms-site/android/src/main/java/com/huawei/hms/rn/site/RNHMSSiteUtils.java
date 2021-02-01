/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.site;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RNHMSSiteUtils {

    private static Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

    private RNHMSSiteUtils() {
    }

    public static <T> Map<String, Object> toMap(T obj) {
        return gson.fromJson(gson.toJson(obj), Map.class);
    }

    public static <T> T toObject(ReadableMap params, Class<T> clazz) {

        if (params == null || clazz == null) return null;

        HashMap<String, Object> paramMap = params.toHashMap();

        T instance = gson.fromJson(gson.toJson(paramMap), clazz);

        return instance;
    }

    public static void handleResult(Object response, boolean isSuccess, Promise promise) {
        Map<String, Object> result = RNHMSSiteUtils.toMap(response);
        if (isSuccess) {
            promise.resolve(Arguments.makeNativeMap(result));
        } else {
            promise.reject("SEARCH_ERROR", Arguments.makeNativeMap(result));
        }
    }

    public static boolean isValidPoiType(String poiType){
        boolean isContains;

        String[] availablePoiTypes = {
                "GEOCODE",
                "ADDRESS",
                "ESTABLISHMENT",
                "REGIONS",
                "CITIES"
        };

        isContains = Arrays.asList(availablePoiTypes).contains((String) poiType);

        return isContains;
    }
}
