/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.push.local;

import android.os.Bundle;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.rn.push.constants.Core;
import com.huawei.hms.rn.push.utils.BundleUtils;

public class HmsLocalNotificationActionPublisher {
    private ReactContext context;

    public HmsLocalNotificationActionPublisher(ReactContext reactContext) {

        context = reactContext;
    }

    public void notifyNotificationAction(Bundle bundle) {

        String bundleString = BundleUtils.convertJSON(bundle);

        WritableMap params = Arguments.createMap();
        params.putString(Core.Event.Result.DATA_JSON, bundleString);

        sendEvent(params);
    }

    void sendEvent(Object params) {

        if (context.hasActiveCatalystInstance()) {
            context
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Core.Event.LOCAL_NOTIFICATION_ACTION_EVENT, params);
        }
    }


}
