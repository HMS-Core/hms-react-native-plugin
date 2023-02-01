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

package com.huawei.hms.rn.push.constants;

public interface Core {
    String CLIENT_APP_ID = "client/app_id";
    String DEFAULT_TOKEN_SCOPE = "HCM";
    String RAW = "raw";

    String REMOTE_MESSAGE_UPLINK_TO = "push.hcm.upstream";

    String PREFERENCE_NAME = "huawei_hms_rn_push";

    String DEFAULT_MESSAGE = "HMS Push";
    long DEFAULT_VIBRATE_DURATION = 250L;
    String NOTIFICATION_CHANNEL_ID = "huawei-hms-rn-push-channel-id";
    String NOTIFICATION_CHANNEL_NAME = "huawei-hms-rn-push-channel";
    String NOTIFICATION_CHANNEL_DESC = "Huawei HMS Push";

    interface Event {
        String REMOTE_NOTIFICATION_RECEIVED = "REMOTE_NOTIFICATION_RECEIVED"; // SDK Don`t support yet.
        String REMOTE_DATA_MESSAGE_RECEIVED = "REMOTE_DATA_MESSAGE_RECEIVED";
        String LOCAL_NOTIFICATION_ACTION_EVENT = "LOCAL_NOTIFICATION_ACTION_EVENT";
        String ON_TOKEN_RECEIVED_EVENT = "ON_TOKEN_RECEIVED_EVENT";
        String ON_PUSH_MESSAGE_SENT = "ON_PUSH_MESSAGE_SENT";
        String ON_PUSH_MESSAGE_SENT_ERROR = "ON_PUSH_MESSAGE_SENT_ERROR";
        String ON_PUSH_MESSAGE_SENT_DELIVERED = "ON_PUSH_MESSAGE_SENT_DELIVERED";
        String NOTIFICATION_OPENED_EVENT = "NOTIFICATION_OPENED_EVENT";
        String ON_TOKEN_ERROR_EVENT = "ON_TOKEN_ERROR_EVENT";
        String PUSH_ON_START_COMMAND_EVENT = "PUSH_ON_START_COMMAND_EVENT";
        String ON_MULTI_SENDER_TOKEN_RECEIVED_EVENT = "ON_MULTI_SENDER_TOKEN_RECEIVED_EVENT";
        String ON_MULTI_SENDER_TOKEN_ERROR_EVENT = "ON_MULTI_SENDER_TOKEN_ERROR_EVENT";


        interface Result {
            String DATA_JSON = "dataJSON";
            String RESULT = "result";
            String RESULT_CODE = "resultCode";
            String MSG_ID = "msgId";
            String RESULT_INFO = "resultInfo";
            String TOKEN = "token";
            String MSG = "msg";
            String EXCEPTION = "exception";
            String ON_START_COMMAND = "COMMAND_START";

        }
    }

    interface Resource {
        String MIPMAP = "mipmap";
        String NOTIFICATION = "ic_notification";
        String LAUNCHER = "ic_launcher";
        String DEFAULT = "default";
    }

    interface NotificationType {
        String NOW = "NOW";
        String SCHEDULED = "SCHEDULED";
        String REMOTE = "REMOTE";
        String DATA = "DATA";
    }

    interface ScheduledPublisher {
        String NOTIFICATION_ID = "notificationId";
        String BOOT_EVENT = "android.intent.action.BOOT_COMPLETED";
    }
}
