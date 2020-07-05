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


package com.huawei.hms.push.react;

import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.hms.push.SendException;


public class RNHmsMessageService extends HmsMessageService {
    private static final String TAG = "RNHmsMessageService";

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Log.w(TAG,"** onMessageReceived **");

        RNReceiver.sendMsgEvent(message);
    }

    @Override
    public void onDeletedMessages() {
        Log.w(TAG,"** onDeletedMessages **");
    }

    @Override
    public void onMessageSent(String msgId) {
        Log.w(TAG,"** onMessageSent **");

        RNReceiver.sendXmppMsgEvent(msgId, 0, "");
    }

    @Override
    public void onSendError(String msgId, Exception exception) {
        Log.w(TAG,"** onSendError **");

        int errCode = ((SendException) exception).getErrorCode();
        String errInfo = exception.getMessage();
        RNReceiver.sendXmppMsgEvent(msgId, errCode, errInfo);
    }

    @Override
    public void onNewToken(String token) {
        try {
            super.onNewToken(token);
            Log.w(TAG,"** onNewToken **");
            RNReceiver.sendTokenMsgEvent(token);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }


}
