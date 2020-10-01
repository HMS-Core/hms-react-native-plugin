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

package com.huawei.hms.rn.push.remote;

import android.content.Intent;
import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.hms.push.SendException;
import com.huawei.hms.rn.push.logger.HMSLogger;


public class HmsPushMessageService extends HmsMessageService {
    private final String TAG = HmsPushMessageService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage message) {

        Log.w(TAG, "** onMessageReceived **");
        HMSLogger.getInstance(HmsPushMessaging.getContext()).sendPeriodicEvent("onMessageReceived");
        HmsMessagePublisher.sendMessageReceivedEvent(message);
    }

    @Override
    public void onDeletedMessages() {

        Log.w(TAG, "** onDeletedMessages **");
    }

    @Override
    public void onMessageSent(String msgId) {

        Log.w(TAG, "** onMessageSent **");
        HMSLogger.getInstance(HmsPushMessaging.getContext()).sendPeriodicEvent("onMessageSent");
        HmsMessagePublisher.sendOnMessageSentEvent(msgId);
    }

    @Override
    public void onSendError(String msgId, Exception exception) {

        Log.w(TAG, "** onSendError **");

        int errorCode = ((SendException) exception).getErrorCode();
        String errorInfo = exception.getMessage();
        HMSLogger.getInstance(HmsPushMessaging.getContext()).sendPeriodicEvent("onSendError");
        HmsMessagePublisher.sendOnMessageSentErrorEvent(msgId, errorCode, errorInfo);
    }

    @Override
    public void onMessageDelivered(String msgId, Exception e) {

        Log.w(TAG, "** onMessageDelivered **");
        if (e == null) {
            HMSLogger.getInstance(HmsPushMessaging.getContext()).sendPeriodicEvent("onMessageDelivered");
            HmsMessagePublisher.sendOnMessageDeliveredEvent(msgId, 0, "");
        } else {
            int errorCode = ((SendException) e).getErrorCode();
            String errorInfo = e.getMessage();
            HmsMessagePublisher.sendOnMessageDeliveredEvent(msgId, errorCode, errorInfo);
        }
    }

    @Override
    public void onTokenError(Exception e) {

        Log.w(TAG, "** onTokenError **");
        HMSLogger.getInstance(HmsPushInstanceId.getContext()).sendPeriodicEvent("onTokenError");
        HmsMessagePublisher.sendTokenErrorEvent(e);
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i1) {

        Log.w(TAG, "** onStartCommand **");
        int result = super.onStartCommand(intent, i, i1);
        HmsMessagePublisher.sendOnStartCommandEvent();
        return result;
    }

    @Override
    public void onNewToken(String token) {

        try {
            super.onNewToken(token);
            Log.w(TAG, "** onNewToken **");
            HMSLogger.getInstance(HmsPushInstanceId.getContext()).sendPeriodicEvent("onNewToken");
            HmsMessagePublisher.sendOnNewTokenEvent(token);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
