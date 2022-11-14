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

package com.huawei.hms.rn.health.kits.activityrecords.receiver;

import static com.facebook.react.bridge.Arguments.createMap;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.addIsSuccess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.huawei.hms.hihealth.data.ActivityRecord;
import com.huawei.hms.rn.health.kits.activityrecords.HmsActivityRecordsController;

import com.facebook.react.bridge.WritableMap;

/**
 * ActivityRecord status receiving and processing class.
 * <p>
 * {@link ActivityRecordsMonitorReceiver} uses an intent in the {@link HmsActivityRecordsController},
 * gives to result via {@link OnActivityRegisterReceiverListener} to HmsActivityRecordsController.
 *
 * @since v.5.0.1
 */
public class ActivityRecordsMonitorReceiver extends BroadcastReceiver {
    private static final String TAG = ActivityRecordsMonitorReceiver.class.getSimpleName();

    private static OnActivityRegisterReceiverListener listener = null;

    /**
     * OnActivityRegisterReceiverListener informs the {@link HmsActivityRecordsController}
     * for the ActivityRecord Status.
     */
    public interface OnActivityRegisterReceiverListener {
        /**
         * Gets WritableMap instance and informs the calling side,
         * every time onReceive is triggered.
         *
         * @param writableMap WritableMap instance.
         */
        void onReceived(WritableMap writableMap);
    }

    /**
     * Setting {@link OnActivityRegisterReceiverListener}
     *
     * @param listener static ActivityRecordsMonitorReceiver.OnActivityRegisterReceiverListener
     */
    public static void setActivityRegisterReceiverListener(OnActivityRegisterReceiverListener listener) {
        ActivityRecordsMonitorReceiver.listener = listener;
    }

    /**
     * This method is called when the {@link ActivityRecordsMonitorReceiver} is receiving an Intent broadcast.
     * During this time you can use the other methods on {@link ActivityRecordsMonitorReceiver}  to view/modify the current result values.
     *
     * @param context ReactContext instance.
     * @param intent PendingIntent instance
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive");
        // an Intent broadcast.
        if (intent != null) {
            ActivityRecord activityRecord = ActivityRecord.extract(intent);
            WritableMap writableMap = createMap();
            if (activityRecord != null) {
                writableMap.putString("activityRecordInfo", activityRecord.toString());
            }
            if (ActivityRecordsMonitorReceiver.listener != null) {
                ActivityRecordsMonitorReceiver.listener.onReceived(addIsSuccess(writableMap, true));
            }
        }
    }
}

