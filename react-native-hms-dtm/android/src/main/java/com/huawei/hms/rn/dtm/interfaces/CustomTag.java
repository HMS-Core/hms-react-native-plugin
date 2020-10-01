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

package com.huawei.hms.rn.dtm.interfaces;

import com.huawei.hms.dtm.ICustomTag;
import com.huawei.hms.rn.dtm.helpers.ContextHolder;
import com.huawei.hms.rn.dtm.logger.HMSLogger;
import java.util.Map;
import android.util.Log;

/* When tag (map) returns, type the action you want to do.
The name of your class must match your tag name(server side).*/

public class CustomTag implements ICustomTag {
    @Override
    public void call(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Log.i("CustomTag::", String.valueOf(entry));
        }
        if (ContextHolder.getInstance().getContext() != null) {
            HMSLogger.getInstance(ContextHolder.getInstance().getContext()).sendSingleEvent("CustomTag::");
        }
    }
}