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

import {
    NativeModules,
    NativeEventEmitter,
    AppRegistry,
    Platform,
} from "react-native";

const {
    HmsPushInstanceId,
    HmsLocalNotification,
    HmsPushMessaging,
    HmsPushOpenDevice,
    HmsPushProfile,
} = NativeModules;

import ProfileType from "./HmsPushProfileTypes";

if (Platform.OS === "android") {
    HmsPushProfile.Type = ProfileType;
}

import {
    Importance,
    Priority,
    RepeatType,
    Visibility,
} from "./LocalNotification";

Platform.OS === "android" &&
    Object.assign(HmsLocalNotification, {
        Importance: { ...Importance },
        Priority: { ...Priority },
        RepeatType: { ...RepeatType },
        Visibility: { ...Visibility },
        Attr: {
            id: "id",
            message: "message",
            fireDate: "fireDate",
            title: "title",
            ticker: "ticker",
            showWhen: "showWhen",
            autoCancel: "autoCancel",
            largeIcon: "largeIcon",
            largeIconUrl: "largeIconUrl",
            smallIcon: "smallIcon",
            bigText: "bigText",
            subText: "subText",
            bigPictureUrl: "bigPictureUrl",
            shortcutId: "shortcutId",
            number: "number",
            channelId: "channelId",
            channelName: "channelName",
            channelDescription: "channelDescription",
            color: "color",
            group: "group",
            groupSummary: "groupSummary",
            playSound: "playSound",
            soundName: "soundName",
            vibrate: "vibrate",
            vibrateDuration: "vibrateDuration",
            actions: "actions",
            invokeApp: "invokeApp",
            tag: "tag",
            repeatType: "repeatType",
            repeatTime: "repeatTime",
            ongoing: "ongoing",
            allowWhileIdle: "allowWhileIdle",
            dontNotifyInForeground: "dontNotifyInForeground",
            priority: "priority",
            importance: "importance",
            visibility: "visibility",
            data: "data",
        },
    });

import { HmsPushEvent } from "./HmsPushEvent";

HmsPushEvent.onRemoteMessageReceived = (result) =>
    new NativeEventEmitter().addListener(
        HmsPushEvent.REMOTE_DATA_MESSAGE_RECEIVED,
        result
    );

HmsPushEvent.onTokenReceived = (result) =>
    new NativeEventEmitter().addListener(
        HmsPushEvent.ON_TOKEN_RECEIVED_EVENT,
        result
    );

HmsPushEvent.onMultiSenderTokenReceived = (result) =>
    new NativeEventEmitter().addListener(
        HmsPushEvent.ON_MULTI_SENDER_TOKEN_RECEIVED_EVENT,
        result
    );

HmsPushEvent.onTokenError = (result) =>
    new NativeEventEmitter().addListener(
        HmsPushEvent.ON_TOKEN_ERROR_EVENT,
        result
    );

HmsPushEvent.onMultiSenderTokenError = (result) =>
    new NativeEventEmitter().addListener(
        HmsPushEvent.ON_MULTI_SENDER_TOKEN_ERROR_EVENT,
        result
    );

HmsPushEvent.onPushMessageSent = (result) =>
    new NativeEventEmitter().addListener(
        HmsPushEvent.ON_PUSH_MESSAGE_SENT,
        result
    );

HmsPushEvent.onPushMessageSentError = (result) =>
    new NativeEventEmitter().addListener(
        HmsPushEvent.ON_PUSH_MESSAGE_SENT_ERROR,
        result
    );

HmsPushEvent.onPushMessageSentDelivered = (result) =>
    new NativeEventEmitter().addListener(
        HmsPushEvent.ON_PUSH_MESSAGE_SENT_DELIVERED,
        result
    );

HmsPushEvent.onLocalNotificationAction = (result) =>
    new NativeEventEmitter().addListener(
        HmsPushEvent.LOCAL_NOTIFICATION_ACTION_EVENT,
        result
    );

HmsPushEvent.onNotificationOpenedApp = (result) =>
    new NativeEventEmitter().addListener(
        HmsPushEvent.NOTIFICATION_OPENED_EVENT,
        result
    );

let backgroundMessageHandler;
AppRegistry.registerHeadlessTask("HMSPushHeadlessTask", () => {
    if (!backgroundMessageHandler) {
        console.warn(
            "There is not any handler method. Use 'setBackgroundMessageHandler' method."
        );
        return () => Promise.resolve();
    }
    return (remoteMessage) => backgroundMessageHandler(remoteMessage);
});

if (Platform.OS === "android") {
    HmsPushMessaging.setBackgroundMessageHandler = (handler) => {
        if (handler && typeof handler !== "function") {
            console.error("backgroundMessageHandler must be a function.");
        }
        backgroundMessageHandler = handler;
        console.log("backgroundMessageHandler registered ✔");
    };
}

export { RNRemoteMessage } from "./RNRemoteMessage";
export { HmsPushResultCode } from "./HmsPushResultCode";
export { RemoteMessageBuilder } from "./RemoteMessageBuilder";

export {
    HmsPushInstanceId,
    HmsPushOpenDevice,
    HmsLocalNotification,
    HmsPushMessaging,
    HmsPushEvent,
    HmsPushProfile,
};

export default HmsPushInstanceId;
