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

declare module "@hmscore/react-native-hms-push" {

    export const HmsPushInstanceId = {
        getId(): Promise<Object>;,
        getAAID(): Promise<Object>;,
        getCreationTime(): Promise<Object>;,
        deleteAAID(): Promise<Object>;,
        getToken(scope: string): Promise<Object>;,
        deleteToken(scope: string): Promise<Object>;,
        getTokenWithSubjectId(subjectId: string): Promise<string>;,
        getTokenWithSubjectId(subjectId: string): Promise<boolean>;
    }

    export const HmsPushOpenDevice = {
        getOdid(): Promise<Object>;
    }

    export const HmsPushMessaging = {
        isAutoInitEnabled(): Promise<Object>;,
        setAutoInitEnabled(isEnabled: boolean): Promise<Object>;,
        subscribe(topic: string): Promise<Object>;,
        unsubscribe(topic: string): Promise<Object>;,
        turnOnPush(): Promise<Object>;,
        turnOffPush(): Promise<Object>;,
        getInitialNotification(): Promise<Object>;,
        enableLogger(): Promise<Object>;,
        disableLogger(): Promise<Object>;,
        sendRemoteMessage(uplinkMessage: RemoteMessageBuilder): Promise<Object>;,
        setBackgroundMessageHandler(callback: Function): Promise<Object>;,
    }

    export declare enum RemoteMessageBuilder {
        TO = "to",
        MESSAGE_ID = "messageId",
        MESSAGE_TYPE = "messageType",
        TTL = "ttl",
        COLLAPSE_KEY = "collapseKey",
        RECEIPT_MODE = "receiptMode",
        SEND_MODE = "sendMode",
        DATA = "data",
    }

    export const HmsPushProfile = {
        isSupportProfile(): Promise<boolean>;,
        addProfile(type: Type, profileId: string): Promise<boolean>;,
        addProfileWithSubjectId(subjectId: string , type: Type , profileId: string): Promise<boolean>;,
        deleteProfile(profileId: string): Promise<boolean>;,
        deleteProfileWithSubjectId(subjectId: string, profileId: string): Promise<boolean>;,
        Type
    }

    export declare enum Type {
        HUAWEI_PROFILE = 1,
        CUSTOM_PROFILE = 2,
        UNDEFINED_PROFILE = -1
    }

    export const HmsPushEvent = {
        onRemoteMessageReceived(callback: Function): void;,
        onTokenReceived(callback: Function): void;,
        onTokenError(callback: Function): void;,
        onNotificationOpenedApp(callback: Function): void;,
        onLocalNotificationAction(callback: Function): void;,
        onPushMessageSent(callback: Function): void;,
        onPushMessageSentError(callback: Function): void;,
        onPushMessageSentDelivered(callback: Function): void;,
        onMultiSenderTokenReceived(callback: Function): void;,
        onMultiSenderTokenError(callback: Function): void;
    }

    export const HmsLocalNotification = {
        localNotification(notification: Object): Promise<Object>;,
        localNotificationSchedule(notification: Object): Promise<Object>;,
        cancelAllNotifications(): Promise<Object>;,
        cancelNotifications(): Promise<Object>;,
        cancelScheduledNotifications(): Promise<Object>;,
        cancelNotificationsWithId(ids: string[]): Promise<Object>;,
        cancelNotificationsWithIdTag(idsAndTags: Object[]): Promise<Object>;,
        cancelNotificationsWithTag(tag: string): Promise<Object>;,
        getNotifications(): Promise<Object>;,
        getScheduledNotifications(): Promise<Object>;,
        getChannels(): Promise<Object>;,
        channelExists(channeld: string): Promise<Object>;,
        channelBlocked(channeld: string): Promise<Object>;,
        deleteChannel(channeld: string): Promise<Object>;,
        Attr,
        Priority,
        Visibility,
        Importance,
        RepeatType
    }

    export declare enum Attr {
        id = "id",
        message =  "message",
        fireDate =  "fireDate",
        title =  "title",
        ticker =  "ticker",
        showWhen =  "showWhen",
        autoCancel =  "autoCancel",
        largeIcon =  "largeIcon",
        largeIconUrl =  "largeIconUrl",
        smallIcon =  "smallIcon",
        bigText =  "bigText",
        subText =  "subText",
        bigPictureUrl =  "bigPictureUrl",
        shortcutId =  "shortcutId",
        number =  "number",
        channelId =  "channelId",
        channelName =  "channelName",
        channelDescription =  "channelDescription",
        color =  "color",
        group =  "group",
        groupSummary =  "groupSummary",
        playSound =  "playSound",
        soundName =  "soundName",
        vibrate =  "vibrate",
        vibrateDuration =  "vibrateDuration",
        actions =  "actions",
        invokeApp =  "invokeApp",
        tag =  "tag",
        repeatType =  "repeatType",
        repeatTime =  "repeatTime",
        ongoing =  "ongoing",
        allowWhileIdle = "allowWhileIdle",
        dontNotifyInForeground =  "dontNotifyInForeground",
        priority =  "priority",
        importance =  "importance",
        visibility =  "visibility",
        data =  "data",
    }

    export declare enum Priority {
        max = "max",
        high = "high",
        default = "default",
        low = "low",
        min = "min",
    }

    export declare enum Importance {
        max = "max",
        high = "high",
        default = "default",
        low = "low",
        min = "min",
        none = "none",
        unspecified = "unspecified",
    }

    export declare enum RepeatType {
        hour = "hour",
        minute = "minute",
        day = "day",
        week = "week",
        customTime = "custom_time",
    }

    export declare enum HmsPushResultCode {
        SUCCESS = "0",
        ERROR = "-1",
        NULL_BUNDLE = "333",
        RESULT_FAILURE = "907122045",
        PARAMETER_IS_EMPTY = "907122042",
        ERROR_NO_TOKEN = "907122030",
        ERROR_NO_NETWORK = "907122031",
        ERROR_TOKEN_INVALID = "907122032",
        ERROR_SERVICE_NOT_AVAILABLE = "907122046",
        ERROR_PUSH_SERVER = "907122047",
        ERROR_TOPIC_EXCEED = "907122034",
        ERROR_TOPIC_SEND = "907122035",
        ERROR_NO_RIGHT = "907122036",
        ERROR_GET_TOKEN_ERR = "907122037",
        ERROR_STORAGE_LOCATION_EMPTY = "907122038",
        ERROR_NOT_ALLOW_CROSS_APPLY = "907122053",
        ERROR_SIZE = "907122041",
        ERROR_TOO_MANY_MESSAGES = "907122043",
        ERROR_TTL_EXCEEDED = "907122044",
        ERROR_HMS_CLIENT_API = "907122048",
        ERROR_OPERATION_NOT_SUPPORTED = "907122049",
        ERROR_MAIN_THREAD = "907122050",
        ERROR_HMS_DEVICE_AUTH_FAILED_SELF_MAPPING = "907122051",
        ERROR_BIND_SERVICE_SELF_MAPPING = "907122052",
        ERROR_AUTO_INITIALIZING = "907122054",
        ERROR_ARGUMENTS_INVALID = "907135000",
        ERROR_INTERNAL_ERROR = "907135001",
        ERROR_NAMING_INVALID = "907135002",
        ERROR_CLIENT_API_INVALID = "907135003",
        ERROR_EXECUTE_TIMEOUT = "907135004",
        ERROR_NOT_IN_SERVICE = "907135005",
        ERROR_SESSION_INVALID = "907135006",
        ERROR_API_NOT_SPECIFIED = "1002",
        ERROR_GET_SCOPE_ERROR = "907135700",
        ERROR_SCOPE_LIST_EMPTY = "907135701",
        ERROR_CERT_FINGERPRINT_EMPTY = "907135702",
        ERROR_PERMISSION_LIST_EMPTY = "907135703",
        ERROR_AUTH_INFO_NOT_EXIST = "6002",
        ERROR_CERT_FINGERPRINT_ERROR = "6003",
        ERROR_PERMISSION_NOT_EXIST = "6004",
        ERROR_PERMISSION_NOT_AUTHORIZED = "6005",
        ERROR_PERMISSION_EXPIRED = "6006",
    }

    export class RNRemoteMessage  {
        getCollapseKey(): string;
        getData(): string;
        getDataOfMap(): string;
        getMessageId(): string;
        getMessageType(): string;
        getOriginalUrgency(): number;
        getUrgency(): number;
        getTtl(): number;
        getSentTime(): number;
        getTo(): string;
        getFrom(): string;
        getToken(): string;
        getAnalyticInfo(): string;
        getAnalyticInfoMap(): Object;
        getNotificationTitle(): string;
        getTitleLocalizationKey(): string;
        getTitleLocalizationArgs(): Object;
        getBodyLocalizationKey(): string;
        getBodyLocalizationArgs(): Object;
        getBody(): string;
        getIcon(): string;
        getSound(): string;
        getTag(): string;
        getColor(): string;
        getClickAction(): string;
        getChannelId(): string;
        getImageUrl(): Object;
        getLink(): Object;
        getNotifyId(): number;
        isDefaultLight(): boolean;
        isDefaultSound(): boolean;
        isDefaultVibrate(): boolean;
        getWhen(): number;
        getLightSettings(): number[];
        getBadgeNumber(): number;
        isAutoCancel(): boolean;
        getImportance(): number;
        getTicker(): string;
        getVibrateConfig(): number[];
        getVisibility(): number;
        getIntentUri(): string;
        parseMsgAllAttribute(): string;
    }

    export declare enum RNRemoteMessage {
        PRIORITY_UNKNOWN = 0,
        PRIORITY_HIGH = 1,
        PRIORITY_NORMAL = 2,
        COLLAPSEKEY = "collapseKey",
        DATA = "data",
        DATAOFMAP = "dataOfMap",
        MESSAGEID = "messageId",
        MESSAGETYPE = "messageType",
        ORIGINALURGENCY = "originalUrgency",
        URGENCY = "urgency",
        TTL = "ttl",
        SENTTIME = "sentTime",
        TO = "to",
        FROM = "from",
        TOKEN = "token",
        ANALYTIC_INFO = "analyticInfo",
        ANALYTIC_INFO_MAP = "analyticInfoMap",
    }


}