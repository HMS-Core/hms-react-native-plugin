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

package com.huawei.hms.rn.push.utils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.hms.rn.push.constants.Core;
import com.huawei.hms.rn.push.constants.RemoteMessageAttributes;

import java.util.Arrays;
import java.util.HashMap;

public class RemoteMessageUtils {

    private RemoteMessageUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static HashMap<String, Object> toMap(RemoteMessage message) {

        HashMap<String, Object> map = new HashMap<>();

        map.put(RemoteMessageAttributes.COLLAPSE_KEY, message.getCollapseKey());
        map.put(RemoteMessageAttributes.DATA, message.getData());
        map.put(RemoteMessageAttributes.DATA_OF_MAP, message.getDataOfMap() + "");
        map.put(RemoteMessageAttributes.MESSAGE_ID, message.getMessageId());
        map.put(RemoteMessageAttributes.MESSAGE_TYPE, message.getMessageType());
        map.put(RemoteMessageAttributes.ORIGINAL_URGENCY, message.getOriginalUrgency() + "");
        map.put(RemoteMessageAttributes.URGENCY, message.getUrgency() + "");
        map.put(RemoteMessageAttributes.TTL, message.getTtl() + "");
        map.put(RemoteMessageAttributes.SENT_TIME, message.getSentTime() + "");
        map.put(RemoteMessageAttributes.TO, message.getTo());
        map.put(RemoteMessageAttributes.FROM, message.getFrom());
        map.put(RemoteMessageAttributes.TOKEN, message.getToken());
        map.put(RemoteMessageAttributes.RECEIPT_MODE, message.getReceiptMode() + "");
        map.put(RemoteMessageAttributes.SEND_MODE, message.getSendMode() + "");
        map.put(RemoteMessageAttributes.CONTENTS, message.describeContents() + "");
        map.put(RemoteMessageAttributes.ANALYTIC_INFO, message.getAnalyticInfo());
        map.put(RemoteMessageAttributes.ANALYTIC_INFO_MAP, message.getAnalyticInfoMap() + "");


        if (message.getNotification() != null) {
            RemoteMessage.Notification notification = message.getNotification();
            map.put(RemoteMessageAttributes.TITLE, notification.getTitle());
            map.put(RemoteMessageAttributes.TITLE_LOCALIZATION_KEY, notification.getTitleLocalizationKey());
            map.put(RemoteMessageAttributes.TITLE_LOCALIZATION_ARGS, Arrays.toString(notification.getTitleLocalizationArgs()));
            map.put(RemoteMessageAttributes.BODY_LOCALIZATION_KEY, notification.getBodyLocalizationKey());
            map.put(RemoteMessageAttributes.BODY_LOCALIZATION_ARGS, Arrays.toString(notification.getBodyLocalizationArgs()));
            map.put(RemoteMessageAttributes.BODY, notification.getBody());
            map.put(RemoteMessageAttributes.ICON, notification.getIcon());
            map.put(RemoteMessageAttributes.SOUND, notification.getSound());
            map.put(RemoteMessageAttributes.TAG, notification.getTag());
            map.put(RemoteMessageAttributes.COLOR, notification.getColor());
            map.put(RemoteMessageAttributes.CLICK_ACTION, notification.getClickAction());
            map.put(RemoteMessageAttributes.CHANNEL_ID, notification.getChannelId());
            map.put(RemoteMessageAttributes.IMAGE_URL, notification.getImageUrl() + "");
            map.put(RemoteMessageAttributes.LINK, notification.getLink() + "");
            map.put(RemoteMessageAttributes.NOTIFY_ID, notification.getNotifyId() + "");
            map.put(RemoteMessageAttributes.WHEN, notification.getWhen() + "");
            map.put(RemoteMessageAttributes.LIGHT_SETTINGS, Arrays.toString(notification.getLightSettings()));
            map.put(RemoteMessageAttributes.BADGE_NUMBER, notification.getBadgeNumber() + "");
            map.put(RemoteMessageAttributes.IMPORTANCE, notification.getImportance() + "");
            map.put(RemoteMessageAttributes.TICKER, notification.getTicker());
            map.put(RemoteMessageAttributes.VIBRATE_CONFIG, Arrays.toString(notification.getVibrateConfig()));
            map.put(RemoteMessageAttributes.VISIBILITY, notification.getVisibility() + "");
            map.put(RemoteMessageAttributes.INTENT_URI, notification.getIntentUri());
            map.put(RemoteMessageAttributes.IS_AUTO_CANCEL, notification.isAutoCancel() + "");
            map.put(RemoteMessageAttributes.IS_LOCAL_ONLY, notification.isLocalOnly() + "");
            map.put(RemoteMessageAttributes.IS_DEFAULT_LIGHT, notification.isDefaultLight() + "");
            map.put(RemoteMessageAttributes.IS_DEFAULT_SOUND, notification.isDefaultSound() + "");
            map.put(RemoteMessageAttributes.IS_DEFAULT_VIBRATE, notification.isDefaultVibrate() + "");
        }

        return map;
    }

    public static WritableMap toWritableMap(RemoteMessage message) {

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
        params.putString(RemoteMessageAttributes.ANALYTIC_INFO, message.getAnalyticInfo());
        params.putString(RemoteMessageAttributes.ANALYTIC_INFO_MAP, message.getAnalyticInfoMap() + "");

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
