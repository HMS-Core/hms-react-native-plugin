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

package com.huawei.hms.rn.location.utils;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.rn.location.helpers.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ReactUtils {

    public static void sendEvent(ReactContext reactContext, String eventName, WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
    }

    public static WritableMap basicMap(String... args) {
        WritableMap map = Arguments.createMap();
        for (int i = 0; i < args.length; i = i + 2) {
            map.putString(args[i], args[i + 1]);
        }
        return map;
    }

    public static WritableMap intMap(String key, int val) {
        WritableMap map = Arguments.createMap();
        map.putInt(key, val);
        return map;
    }

    public static WritableMap fromMapToWritableMap(Map<String, Object> map) {
        WritableMap writableMap = Arguments.createMap();
        if (map == null) {
            return writableMap;
        }

        for (String key: map.keySet()) {
            writableMap.putString(key, map.get(key).toString());
        }
        return writableMap;
    }

    public static <R> List<R> mapReadableArray(ReadableArray array, Mapper<ReadableMap, R> mapper) {
        List<R> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            list.add(mapper.map(array.getMap(i)));
        }
        return list;
    }

    public static <T> WritableArray mapList(List<T> list, Mapper<T, WritableMap> mapper) {
        WritableArray array = Arguments.createArray();
        for (T item : list) {
            array.pushMap(mapper.map(item));
        }
        return array;
    }

}
