/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.health.kits.datacontroller.receiver;

import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.hihealth.data.DataModifyInfo;
import com.huawei.hms.rn.health.foundation.util.Utils;
import com.huawei.hms.rn.health.kits.datacontroller.HmsDataController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.facebook.react.bridge.Arguments.createMap;
import static com.huawei.hms.rn.health.foundation.constant.Constants.dataTypeKey;
import static com.huawei.hms.rn.health.foundation.constant.Constants.endTimeKey;
import static com.huawei.hms.rn.health.foundation.constant.Constants.startTimeKey;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.addIsSuccess;
import static com.huawei.hms.rn.health.kits.autorecorder.utils.AutoRecorderConstants.dataCollectorKey;

/**
 * Data management monitor
 * <p>
 * {@link DataRegisterReceiver} uses an intent in the {@link HmsDataController},
 * gives to result via {@link DataRegisterReceiver.OnDataRegisterReceiverListener} to HmsDataController.
 *
 * @since v.5.0.1
 */
public class DataRegisterReceiver extends BroadcastReceiver {
    private static final String TAG = DataRegisterReceiver.class.getSimpleName();
    private static OnDataRegisterReceiverListener listener = null;

    /**
     * OnDataRegisterReceiverListener informs the {@link HmsDataController}
     * for the registerModifyDataMonitor.
     */
    public interface OnDataRegisterReceiverListener {
        /**
         * Gets WritableMap instance and informs the calling side,
         * every time onReceive is triggered.
         *
         * @param writableMap WritableMap instance.
         */
        void onReceived(WritableMap writableMap);
    }

    /**
     * Setting {@link DataRegisterReceiver.OnDataRegisterReceiverListener}
     *
     * @param listener static DataRegisterReceiver.OnDataRegisterReceiverListener
     */
    public static void setDataRegisterReceiverListener(OnDataRegisterReceiverListener listener) {
        DataRegisterReceiver.listener = listener;
    }

    /**
     * This method is called when the {@link HmsDataController} is receiving an Intent broadcast.
     * During this time you can use the other methods on {@link HmsDataController}  to view/modify the current result values.
     *
     * @param context ReactContext instance.
     * @param intent  PendingIntent instance
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive");
        // an Intent broadcast.
        DataModifyInfo updateNotification = DataModifyInfo.getModifyInfo(intent);
        if (updateNotification != null) {
            WritableMap writableMap = createMap();
            writableMap = Utils.INSTANCE.putKeyIfNotNull(writableMap, updateNotification.getDataType(), dataTypeKey);
            writableMap = Utils.INSTANCE.putKeyIfNotNull(writableMap, updateNotification.getDataCollector(), dataCollectorKey);
            writableMap = Utils.INSTANCE.putKeyIfNotNull(writableMap, new Date(updateNotification.getModifyStartTime(TimeUnit.MILLISECONDS)), startTimeKey);
            writableMap = Utils.INSTANCE.putKeyIfNotNull(writableMap, new Date(updateNotification.getModifyEndTime(TimeUnit.MILLISECONDS)), endTimeKey);

            if (DataRegisterReceiver.listener != null) {
                DataRegisterReceiver.listener.onReceived(addIsSuccess(writableMap, true));
            }
        }
    }
}
