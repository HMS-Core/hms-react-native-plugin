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

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.push.RemoteMessage;

import java.util.Arrays;

public class RNReceiver extends ReactContextBaseJavaModule {
    private static ReactApplicationContext mContext;
    private static String TAG = RNReceiver.class.toString();

    public RNReceiver(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }


    @Override
    public String getName() {
        return "RNReceiver";
    }

    public static void sendXmppMsgEvent(String msgId, int statusCode, String errInfo) {
        WritableMap params = Arguments.createMap();
        params.putString("result", statusCode + "");
        params.putString("msgId", msgId);
        params.putString("resultInfo", errInfo);

        Log.d(TAG, "send Msg Xmpp Event params:" + params);
        mContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Constants.PushXmppMsgReceiverEvent, params);
    }

    public static void sendTokenMsgEvent(String token) {
        WritableMap params = Arguments.createMap();
        params.putString("token", token);

        Log.d(TAG, "send Msg TokenEvent params:" + params);
        mContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Constants.PushTokenMsgReceiverEvent, params);
    }


    public static void sendMsgEvent(
            RemoteMessage message) {
        Log.d(TAG, "send Msg Event data:" + message.getData());
        WritableMap params = Arguments.createMap();
        params.putMap("msg", convertRmToMap(message));

        mContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Constants.PushMsgReceiverEvent, params);
    }

    private static WritableMap convertRmToMap(RemoteMessage message) {

        WritableMap params = Arguments.createMap();

        params.putString("collapseKey", message.getCollapseKey());
        params.putString("data", message.getData());
        params.putString("dataOfMap", message.getDataOfMap() + "");
        params.putString("messageId", message.getMessageId());
        params.putString("messageType", message.getMessageType());
        params.putString("originalUrgency", message.getOriginalUrgency() + "");
        params.putString("urgency", message.getUrgency() + "");
        params.putString("ttl", message.getTtl() + "");
        params.putString("sentTime", message.getSentTime() + "");
        params.putString("to", message.getTo());
        params.putString("from", message.getFrom());
        params.putString("token", message.getToken());

        if (message.getNotification() != null) {
            RemoteMessage.Notification notification = message.getNotification();
            params.putString("title", notification.getTitle());
            params.putString("titleLocalizationKey", notification.getTitleLocalizationKey());
            params.putString("titleLocalizationArgs", Arrays.toString(notification.getTitleLocalizationArgs()));
            params.putString("bodyLocalizationKey", notification.getBodyLocalizationKey());
            params.putString("bodyLocalizationArgs", Arrays.toString(notification.getBodyLocalizationArgs()));
            params.putString("body", notification.getBody());
            params.putString("icon", notification.getIcon());
            params.putString("Sound", notification.getSound());
            params.putString("Tag", notification.getTag());
            params.putString("Color", notification.getColor());
            params.putString("ClickAction", notification.getClickAction());
            params.putString("ChannelId", notification.getChannelId());
            params.putString("ImageUrl", notification.getImageUrl() + "");
            params.putString("Link", notification.getLink() + "");
            params.putString("NotifyId", notification.getNotifyId() + "");
            params.putString("When", notification.getWhen() + "");
            params.putString("LightSettings", Arrays.toString(notification.getLightSettings()));
            params.putString("BadgeNumber", notification.getBadgeNumber() + "");
            params.putString("Importance", notification.getImportance() + "");
            params.putString("Ticker", notification.getTicker());
            params.putString("vibrateConfig", Arrays.toString(notification.getVibrateConfig()));
            params.putString("visibility", notification.getVisibility() + "");
            params.putString("intentUri", notification.getIntentUri());
            params.putString("isAutoCancel", notification.isAutoCancel() + "");
            params.putString("isLocalOnly", notification.isLocalOnly() + "");
            params.putString("isDefaultLight", notification.isDefaultLight() + "");
            params.putString("isDefaultSound", notification.isDefaultSound() + "");
            params.putString("isDefaultVibrate", notification.isDefaultVibrate() + "");
        }

        return params;
    }
}
