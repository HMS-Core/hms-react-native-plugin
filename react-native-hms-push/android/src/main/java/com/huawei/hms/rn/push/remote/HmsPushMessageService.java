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

package com.huawei.hms.rn.push.remote;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;

import com.huawei.hms.common.ResolvableApiException;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.hms.push.SendException;
import com.huawei.hms.rn.push.constants.Core;
import com.huawei.hms.rn.push.logger.HMSLogger;
import com.huawei.hms.rn.push.services.MessagingHeadlessService;
import com.huawei.hms.rn.push.utils.ApplicationUtils;

public class HmsPushMessageService extends HmsMessageService {
    private final String TAG = HmsPushMessageService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.w(TAG, "** onMessageReceived **");
        Context context = getApplicationContext();
        boolean isApplicationInForeground = ApplicationUtils.isApplicationInForeground(context);
        if (isApplicationInForeground) {
            HMSLogger.getInstance(context).sendPeriodicEvent("onMessageReceived");
            HmsMessagePublisher.sendMessageReceivedEvent(remoteMessage);
        } else {
            try {
                HMSLogger.getInstance(context).sendPeriodicEvent("onMessageReceived");
                Intent backgroundIntent = new Intent(context, MessagingHeadlessService.class);
                backgroundIntent.putExtra(Core.Event.Result.MSG, remoteMessage);
                ComponentName name = context.startService(backgroundIntent);
                if (name != null) {
                    HeadlessJsTaskService.acquireWakeLockNow(context);
                }
            } catch (IllegalStateException e) {
                Log.e(TAG, "Change Priority to 'high'", e);
            }
        }
    }

    @Override
    public void onDeletedMessages() {
        try {
            Log.w(TAG, "** onDeletedMessages **");
            HMSLogger.getInstance(getApplicationContext()).sendPeriodicEvent("onDeletedMessages");
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onMessageSent(String msgId) {
        try {
            Log.w(TAG, "** onMessageSent **");
            HMSLogger.getInstance(getApplicationContext()).sendPeriodicEvent("onMessageSent");
            HmsMessagePublisher.sendOnMessageSentEvent(msgId);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onSendError(String msgId, Exception exception) {
        try {
            Log.w(TAG, "** onSendError **");

            int errorCode = ((SendException) exception).getErrorCode();
            String errorInfo = exception.getMessage();
            HMSLogger.getInstance(getApplicationContext()).sendPeriodicEvent("onSendError");
            HmsMessagePublisher.sendOnMessageSentErrorEvent(msgId, errorCode, errorInfo);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onMessageDelivered(String msgId, Exception e) {
        try {
            Log.w(TAG, "** onMessageDelivered **");
            if (e == null) {
                HMSLogger.getInstance(getApplicationContext()).sendPeriodicEvent("onMessageDelivered");
                HmsMessagePublisher.sendOnMessageDeliveredEvent(msgId, 0, "");
            } else {
                int errorCode = ((SendException) e).getErrorCode();
                String errorInfo = e.getMessage();
                HmsMessagePublisher.sendOnMessageDeliveredEvent(msgId, errorCode, errorInfo);
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onTokenError(Exception e) {
        try {
            Log.w(TAG, "** onTokenError **");
            HMSLogger.getInstance(getApplicationContext()).sendPeriodicEvent("onTokenError");
            HmsMessagePublisher.sendTokenErrorEvent(e);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onNewToken(String token) {

        try {
            super.onNewToken(token);
            Log.w(TAG, "** onNewToken **");
            HMSLogger.getInstance(getApplicationContext()).sendPeriodicEvent("onNewToken");
            HmsMessagePublisher.sendOnNewTokenEvent(token);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onNewToken(String token, Bundle bundle) {
        try {
            super.onNewToken(token, bundle);
            Log.w(TAG, "** onNewToken **");
            HmsMessagePublisher.sendOnNewMultiSenderTokenEvent(token, bundle);
            HMSLogger.getInstance(getApplicationContext()).sendPeriodicEvent("onNewToken");
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

    }

    @Override
    public void onTokenError(Exception e, Bundle bundle) {
        try {
            Log.w(TAG, "** onTokenError **");
            if (e instanceof ResolvableApiException) {
                PendingIntent resolution = ((ResolvableApiException) e).getResolution();
                if (resolution != null) {
                    try {
                        resolution.send();
                    } catch (PendingIntent.CanceledException ex) {
                        HMSLogger.getInstance(getApplicationContext()).sendSingleEvent("onTokenError," + e.getMessage());
                    }
                } else {
                    Intent resolutionIntent = ((ResolvableApiException) e).getResolutionIntent();
                    if (resolutionIntent != null) {
                        HMSLogger.getInstance(getApplicationContext()).sendSingleEvent("onTokenError," + "has resolution by intent");
                        resolutionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(resolutionIntent);
                    }
                }
            }
            HmsMessagePublisher.sendMultiSenderTokenErrorEvent(e, bundle);
            HMSLogger.getInstance(getApplicationContext()).sendPeriodicEvent("onTokenError");
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }
}
