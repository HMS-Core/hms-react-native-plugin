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

package com.huawei.hms.rn.awareness.utils;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.kit.awareness.barrier.BarrierStatus;
import com.huawei.hms.rn.awareness.logger.HMSLogger;

import java.util.List;
import java.util.Objects;

import static com.huawei.hms.rn.awareness.utils.DataUtils.barrierStatusConvertToMap;
import static com.huawei.hms.rn.awareness.utils.DataUtils.errorMessage;

public class BarrierReceiver extends BroadcastReceiver {
    private ReactContext context;

    public BarrierReceiver(ReactContext reactContext) {
        context = reactContext;
    }

    @Override
    public void onReceive(Context c, Intent intent) {
        try {
            HMSLogger.getInstance(context).sendPeriodicEvent("barrierReceiver-barrierStatusChanged");
            if (!isAppOnForeground(context)){
                Log.d("BarrierReceiver:", "onReceive, app is not on foreground");
                return;
            }
            BarrierStatus barrierStatus = BarrierStatus.extract(intent);
            WritableMap map = barrierStatusConvertToMap(barrierStatus);
            if(map == null){
                return;
            }
            String status = map.getString("presentStatusName");
            String barrierLabel = map.getString("barrierLabel");
            Log.i("barrierReceiver::", barrierLabel + "::" + Objects.requireNonNull(status));

            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("barrierReceiver", map);
        } catch (IllegalArgumentException e) {
            errorMessage(context, "barrierReceiver-barrierStatusChanged", "AwarenessBarrier::", e, null);
        }
    }

    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

}
