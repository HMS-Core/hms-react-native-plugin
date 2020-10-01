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

package com.huawei.hms.rn.location.backend.logger;

import android.app.Activity;

public class HMSMethod {
    private String name;
    private boolean periodical = false;

    public HMSMethod(String name) {
        this.name = name;
    }

    public HMSMethod(String name, boolean isPeriodical) {
        this(name);
        this.periodical = isPeriodical;
    }

    public String getName() {
        return name;
    }

    public void sendLoggerEvent(Activity activity) {
        if (periodical) {
            HMSLogger.getInstance(activity).sendPeriodicEvent(name);
            return;
        }
        HMSLogger.getInstance(activity).sendSingleEvent(name);
    }

    public void sendLoggerEvent(Activity activity, String errorCode) {
        if (periodical) {
            HMSLogger.getInstance(activity).sendPeriodicEvent(name, errorCode);
            return;
        }
        HMSLogger.getInstance(activity).sendSingleEvent(name, errorCode);
    }

    public static void enableLogger(Activity activity) {
        HMSLogger.getInstance(activity).enableLogger();
    }

    public static void disableLogger(Activity activity) {
        HMSLogger.getInstance(activity).disableLogger();
    }
}
