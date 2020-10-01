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

package com.huawei.hms.rn.push.utils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.hms.rn.push.constants.Core;
import com.huawei.hms.rn.push.constants.RemoteMessageAttributes;

import java.util.Arrays;

public class RemoteMessageUtils {

    private RemoteMessageUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static WritableMap fromMap(RemoteMessage message) {

        WritableMap params = Arguments.createMap();

        params.putString(RemoteMessageAttributes.COLLAPSE_KEY, message.getCollapseKey());
        params.putString(RemoteMessageAttributes.DATA, message.getData());
        params.putString(RemoteMessageAttributes.DATA_OF_MAP, message.getDataOfMap() + "");
        params.putString(RemoteMessageAttributes.MESSAGE_ID, message.getMessageId());
        params.putString(RemoteMessageAttributes.MESSAGE_TYPE, message.getMessageType());
        params.putString(RemoteMessageAttributes.ORIGINAL_URGENCY, message.getOriginalUrgency() + "");
        params.putString(RemoteMessageAttributes.URGENCY, message.getUrgency() + "");
        params.putString(RemoteMessageAttributes.TTL, message.getTtl() + "");
        params.putString(RemoteMessageAttributes.SENT_TIME, message.getSentTime() + "");
        params.putString(RemoteMessageAttributes.TO, message.getTo());
        params.putString(RemoteMessageAttributes.FROM, message.getFrom());
        params.putString(RemoteMessageAttributes.TOKEN, message.getToken());
        params.putString(RemoteMessageAttributes.RECEIPT_MODE, message.getReceiptMode() + "");
        params.putString(RemoteMessageAttributes.SEND_MODE, message.getSendMode() + "");
        params.putString(RemoteMessageAttributes.CONTENTS, message.describeContents() + "");


        if (message.getNotification() != null) {
            RemoteMessage.Notification notification = message.getNotification();
            params.putString(RemoteMessageAttributes.TITLE, notification.getTitle());
            params.putString(RemoteMessageAttributes.TITLE_LOCALIZATION_KEY, notification.getTitleLocalizationKey());
            params.putString(RemoteMessageAttributes.TITLE_LOCALIZATION_ARGS, Arrays.toString(notification.getTitleLocalizationArgs()));
            params.putString(RemoteMessageAttributes.BODY_LOCALIZATION_KEY, notification.getBodyLocalizationKey());
            params.putString(RemoteMessageAttributes.BODY_LOCALIZATION_ARGS, Arrays.toString(notification.getBodyLocalizationArgs()));
            params.putString(RemoteMessageAttributes.BODY, notification.getBody());
            params.putString(RemoteMessageAttributes.ICON, notification.getIcon());
            params.putString(RemoteMessageAttributes.SOUND, notification.getSound());
            params.putString(RemoteMessageAttributes.TAG, notification.getTag());
            params.putString(RemoteMessageAttributes.COLOR, notification.getColor());
            params.putString(RemoteMessageAttributes.CLICK_ACTION, notification.getClickAction());
            params.putString(RemoteMessageAttributes.CHANNEL_ID, notification.getChannelId());
            params.putString(RemoteMessageAttributes.IMAGE_URL, notification.getImageUrl() + "");
            params.putString(RemoteMessageAttributes.LINK, notification.getLink() + "");
            params.putString(RemoteMessageAttributes.NOTIFY_ID, notification.getNotifyId() + "");
            params.putString(RemoteMessageAttributes.WHEN, notification.getWhen() + "");
            params.putString(RemoteMessageAttributes.LIGHT_SETTINGS, Arrays.toString(notification.getLightSettings()));
            params.putString(RemoteMessageAttributes.BADGE_NUMBER, notification.getBadgeNumber() + "");
            params.putString(RemoteMessageAttributes.IMPORTANCE, notification.getImportance() + "");
            params.putString(RemoteMessageAttributes.TICKER, notification.getTicker());
            params.putString(RemoteMessageAttributes.VIBRATE_CONFIG, Arrays.toString(notification.getVibrateConfig()));
            params.putString(RemoteMessageAttributes.VISIBILITY, notification.getVisibility() + "");
            params.putString(RemoteMessageAttributes.INTENT_URI, notification.getIntentUri());
            params.putString(RemoteMessageAttributes.IS_AUTO_CANCEL, notification.isAutoCancel() + "");
            params.putString(RemoteMessageAttributes.IS_LOCAL_ONLY, notification.isLocalOnly() + "");
            params.putString(RemoteMessageAttributes.IS_DEFAULT_LIGHT, notification.isDefaultLight() + "");
            params.putString(RemoteMessageAttributes.IS_DEFAULT_SOUND, notification.isDefaultSound() + "");
            params.putString(RemoteMessageAttributes.IS_DEFAULT_VIBRATE, notification.isDefaultVibrate() + "");
        }

        return params;
    }

    public static RemoteMessage toRemoteMessage(ReadableMap params) {

        String to = params.getString(RemoteMessageAttributes.TO);
        if (to == null || to.equals("")) to = Core.REMOTE_MESSAGE_UPLINK_TO;

        RemoteMessage.Builder builder = new RemoteMessage.Builder(to);

        String messageId = params.hasKey(RemoteMessageAttributes.MESSAGE_ID) ? params.getString(RemoteMessageAttributes.MESSAGE_ID) : NotificationConfigUtils.generateNotificationId();
        String messageType = params.hasKey(RemoteMessageAttributes.MESSAGE_TYPE) ? params.getString(RemoteMessageAttributes.MESSAGE_TYPE) : "hms";
        int ttl = params.hasKey(RemoteMessageAttributes.TTL) ? params.getInt(RemoteMessageAttributes.TTL) : 120;
        String collapseKey = params.hasKey(RemoteMessageAttributes.COLLAPSE_KEY) ? params.getString(RemoteMessageAttributes.COLLAPSE_KEY) : "-1";
        int receiptMode = params.hasKey(RemoteMessageAttributes.RECEIPT_MODE) ? params.getInt(RemoteMessageAttributes.RECEIPT_MODE) : 1;
        int sendMode = params.hasKey(RemoteMessageAttributes.SEND_MODE) ? params.getInt(RemoteMessageAttributes.SEND_MODE) : 1;

        ReadableMap map = params.hasKey(RemoteMessageAttributes.DATA) ? params.getMap(RemoteMessageAttributes.DATA) : null;
        if (map != null)
            builder.setData(MapUtils.toStringMap(map));

        builder.setCollapseKey(collapseKey);
        builder.setMessageId(messageId);
        builder.setReceiptMode(receiptMode);
        builder.setSendMode(sendMode);
        builder.setMessageType(messageType);
        builder.setTtl(ttl);

        return builder.build();
    }

}
