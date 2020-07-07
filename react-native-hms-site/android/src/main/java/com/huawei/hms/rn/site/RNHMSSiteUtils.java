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

package com.huawei.hms.rn.site;

import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.google.gson.Gson;
import com.huawei.hms.site.api.model.LocationType;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

public class RNHMSSiteUtils {

    private static String TAG = RNHMSSiteUtils.class.getSimpleName();

    private static Gson gson = new Gson();

    private RNHMSSiteUtils() {
    }

    public static <T> Map<String, Object> toMap(T obj) {
        return gson.fromJson(gson.toJson(obj), Map.class);
    }

    public static <T> T toObject(ReadableMap params, Class<T> clazz) {

        HashMap<String, Object> paramMap = params.toHashMap();

        T instance = gson.fromJson(gson.toJson(paramMap), clazz);

        // workaround for poiTypes
        if (paramMap.get("poiType") != null) {
            try {
                Field poiTypeField = clazz.getDeclaredField("poiType");
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    @Override
                    public Void run() {
                        poiTypeField.setAccessible(true);
                        return null;
                    }
                });
                poiTypeField.set(instance,
                        LocationType.valueOf(((String) paramMap.get("poiType")).toUpperCase(Locale.ENGLISH)));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "Can not set poiType. " + e.getMessage());
            }
        }

        if (paramMap.get("poiTypes") != null) {
            List<LocationType> locationTypes = new ArrayList<>();
            try {
                List<String> poiTypes = (List<String>) paramMap.get("poiTypes");
                for (String poiType : poiTypes) {
                    locationTypes.add(LocationType.valueOf(poiType.toUpperCase(Locale.ENGLISH)));
                }

                Field poiTypesField = clazz.getDeclaredField("poiTypes");
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    @Override
                    public Void run() {
                        poiTypesField.setAccessible(true);
                        return null;
                    }
                });
                poiTypesField.set(instance, locationTypes);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "Can not set poiTypes. " + e.getMessage());
            }
        }

        return instance;
    }

}
