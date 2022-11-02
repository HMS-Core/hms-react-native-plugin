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

package com.huawei.hms.rn.dtm.interfaces;

import android.os.Build;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.dtm.ICustomTag;
import com.huawei.hms.rn.dtm.helpers.ContextHolder;
import com.huawei.hms.rn.dtm.helpers.MapHelper;
import com.huawei.hms.rn.dtm.logger.HMSLogger;

import org.json.JSONObject;

import java.util.Map;

public class CustomTag implements ICustomTag {

    @Override
    public void call(Map<String, Object> map) {
        ReactApplicationContext context = (ReactApplicationContext) ContextHolder.getInstance().getContext();
        try {
            if (ContextHolder.getInstance().getContext() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    HMSLogger.getInstance(ContextHolder.getInstance().getContext()).startMethodExecutionTimer("CustomTag");
                }
            }
            JSONObject jsonObject = new JSONObject(map);
            WritableMap writableMap = MapHelper.toWritableMap(jsonObject);

            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("CustomTag", writableMap);

            if (ContextHolder.getInstance().getContext() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    HMSLogger.getInstance(ContextHolder.getInstance().getContext()).sendSingleEvent("CustomTag");
                }
            }
        } catch (IllegalArgumentException e) {
            WritableMap errorMap = MapHelper.createResponseObject(true, "CustomTag-call", e.toString());
            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("CustomTag", errorMap);
        }
    }
}