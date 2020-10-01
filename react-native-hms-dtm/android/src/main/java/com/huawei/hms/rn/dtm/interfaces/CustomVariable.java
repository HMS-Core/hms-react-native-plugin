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

import com.huawei.hms.dtm.ICustomVariable;
import com.huawei.hms.rn.dtm.helpers.ContextHolder;
import com.huawei.hms.rn.dtm.logger.HMSLogger;
import android.util.Log;
import java.util.Map;

/*The name of your class must match your variable name(server side).
You can return the value you want to the server.*/

public class CustomVariable implements ICustomVariable {
    @Override
    public String getValue(Map<String, Object> map) {
        String returnValue = "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Log.i("CustomVariable::", String.valueOf(entry));
            returnValue = String.valueOf(entry);
        }
        if (ContextHolder.getInstance().getContext() != null) {
            HMSLogger.getInstance(ContextHolder.getInstance().getContext()).sendSingleEvent("CustomVariable");
        }
        return returnValue;
    }
}