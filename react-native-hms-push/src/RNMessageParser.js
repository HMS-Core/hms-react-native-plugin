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

export class RNRemoteMessage {

    static INSTANCE_ID_SCOPE = 'HCM';

    static PRIORITY_UNKNOWN = 0;
    static PRIORITY_HIGH = 1;
    static PRIORITY_NORMAL = 2;

    static COLLAPSEKEY= 'collapseKey';
    static DATA= 'data';
    static DATAOFMAP= 'dataOfMap';
    static MESSAGEID= 'messageId';
    static MESSAGETYPE= 'messageType';
    static ORIGINALURGENCY= 'originalUrgency';
    static URGENCY=  'urgency';
    static TTL= 'ttl';
    static SENTTIME=  'sentTime';
    static TO= 'to';
    static FROM= 'from';
    static TOKEN=  'token';

    static NOTIFICATION = {
        TITLE: 'title',
        TITLELOCALIZATIONKEY: 'titleLocalizationKey',
        TITLELOCALIZATIONARGS: 'titleLocalizationArgs',
        BODYLOCALIZATIONKEY: 'bodyLocalizationKey',
        BODYLOCALIZATIONARGS: 'bodyLocalizationArgs',
        BODY: 'body',
        ICON: 'icon',
        SOUND: 'Sound',
        TAG: 'Tag',
        COLOR: 'Color',
        CLICKACTION: 'ClickAction',
        CHANNELID: 'ChannelId',
        IMAGEURL: 'ImageUrl',
        LINK: 'Link',
        NOTIFYID: 'NotifyId',
        WHEN: 'When',
        LIGHTSETTINGS: 'LightSettings',
        BADGENUMBER: 'BadgeNumber',
        IMPORTANCE: 'Importance',
        TICKER: 'Ticker',
        VIBRATECONFIG: 'vibrateConfig',
        VISIBILITY: 'visibility',
        INTENTURI: 'intentUri',
        ISAUTOCANCEL: 'isAutoCancel',
        ISLOCALONLY: 'isLocalOnly',
        ISDEFAULTLIGHT: 'isDefaultLight',
        ISDEFAULTSOUND: 'isDefaultSound',
        ISDEFAULTVIBRATE: 'isDefaultVibrate',
    };


    constructor(options = {}) {
        this.remoteMsg = options;
    }

    /*
     * parse all element,unordered
     */
    parseMsgAllAttribute() {
        let pushResult = '';
        for (const key in this.remoteMsg) {
            pushResult = pushResult + key + ' : ' + this.remoteMsg[key] + '\n';
        }
        return pushResult;
    }

    /*
     * getCollapseKey() Obtains the classification identifier (collapse key) of a message.
     */
    getCollapseKey() {
        return this.remoteMsg[RNRemoteMessage.COLLAPSEKEY];
    }

    /*
     * getData() Obtains valid content data of a message.
     */
    getData() {
        return this.remoteMsg[RNRemoteMessage.DATA];
    }

    /*
     * getDataOfMap() a message map.
     */
    getDataOfMap() {
        return this.remoteMsg[RNRemoteMessage.DATAOFMAP];
    }

    /*
     * getMessageId() Obtains the ID of a message.
     */
    getMessageId() {
        return this.remoteMsg[RNRemoteMessage.MESSAGEID];
    }

    /*
     * getMessageType() Obtains the type of a message.
     */
    getMessageType() {
        return this.remoteMsg[RNRemoteMessage.MESSAGETYPE];
    }

    /*
     * getOriginalUrgency() Obtains the original priority of a message.
     */
    getOriginalUrgency() {
        return this.remoteMsg[RNRemoteMessage.ORIGINALURGENCY];
    }

    /*
     * getUrgency() Obtains priority of a message.
     */
    getUrgency() {
        return this.remoteMsg[RNRemoteMessage.URGENCY];
    }

    /*
     * getTtl() Obtains valid getTtl of a message.
     */
    getTtl() {
        return this.remoteMsg[RNRemoteMessage.TTL];
    }

    /*
     * getSentTime() Obtains the time when a message is sent from the server.
     */
    getSentTime() {
        return this.remoteMsg[RNRemoteMessage.SENTTIME];
    }

    /*
     * getTo() Obtains the recipient of a message.
     */
    getTo() {
        return this.remoteMsg[RNRemoteMessage.TO];
    }

    /*
     * getFrom() Obtains the sender of a message.
     */
    getFrom() {
        return this.remoteMsg[RNRemoteMessage.FROM];
    }

    /*
     * getToken() Obtains valid token
     */
    getToken() {
        return this.remoteMsg[RNRemoteMessage.TOKEN];
    }

    /*
     * Notification:getTitle() Obtains the title of a message
     */
    getNotificationTitle() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.TITLE];
    }

    /*
     * Notification:getTitleLocalizationKey() Obtains the key of the displayed title of a notification message
     */
    getTitleLocalizationKey() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.TITLELOCALIZATIONKEY];
    }

    /*
     * Notification:getTitleLocalizationArgs() Obtains variable parameters of the displayed title of a message
     */
    getTitleLocalizationArgs() {
        return this.remoteMsg[
            RNRemoteMessage.NOTIFICATION.TITLELOCALIZATIONARGS
            ];
    }

    /*
     * Notification:getBodyLocalizationKey() Obtains the key of the displayed content of a message
     */
    getBodyLocalizationKey() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.BODYLOCALIZATIONKEY];
    }

    /*
     * Notification:getBodyLocalizationArgs() Obtains variable parameters of the displayed content of a message
     */
    getBodyLocalizationArgs() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.BODYLOCALIZATIONARGS];
    }

    /*
     * Notification:getBody() Obtains the body of a message
     */
    getBody() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.BODY];
    }

    /*
     * Notification:getIcon() Obtains the icon of a message
     */
    getIcon() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.ICON];
    }

    /*
     * Notification:getSound() Obtains the sound from a message
     */
    getSound() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.SOUND];
    }

    /*
     * Notification:getTag() Obtains the tag from a message for message overwriting
     */
    getTag() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.TAG];
    }

    /*
     * Notification:getColor() Obtains the colors of icons in a message
     */
    getColor() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.COLOR];
    }

    /*
     * Notification:getClickAction() Obtains actions triggered by message tapping
     */
    getClickAction() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.CLICKACTION];
    }

    /*
     * Notification:getChannelId() Obtains IDs of channels that support the display of messages
     */
    getChannelId() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.CHANNELID];
    }

    /*
     * Notification:getImageUrl() Obtains the image URL from a message
     */
    getImageUrl() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.IMAGEURL];
    }

    /*
     * Notification:getLink() Obtains the URL to be accessed from a message
     */
    getLink() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.LINK];
    }

    /*
     * Notification:getNotifyId() Obtains the unique ID of a message
     */
    getNotifyId() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.NOTIFYID];
    }

    /*
     * Notification:getWhen()
     */
    getWhen() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.WHEN];
    }

    /*
     * Notification:getLightSettings()
     */
    getLightSettings() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.LIGHTSETTINGS];
    }

    /*
     * Notification:getBadgeNumber()
     */
    getBadgeNumber() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.BADGENUMBER];
    }

    /*
     * Notification:getImportance()
     */
    getImportance() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.IMPORTANCE];
    }

    /*
     * Notification:getTicker()
     */
    getTicker() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.TICKER];
    }

    /*
     * Notification:getVibrateConfig()
     */
    getVibrateConfig() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.VIBRATECONFIG];
    }

    /*
     * Notification:getVisibility()
     */
    getVisibility() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.VISIBILITY];
    }

    /*
     * Notification:getIntentUri()
     */
    getIntentUri() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.INTENTURI];
    }

    /*
     * Notification:isAutoCancel()
     */
    isAutoCancel() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.ISAUTOCANCEL];
    }

    /*
     * Notification:getIntentUri()
     */
    isLocalOnly() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.ISLOCALONLY];
    }

    /*
     * Notification:isDefaultLight()
     */
    isDefaultLight() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.ISDEFAULTLIGHT];
    }

    /*
     * Notification:isDefaultSound()
     */
    isDefaultSound() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.ISDEFAULTSOUND];
    }

    /*
     * Notification:isDefaultVibrate()
     */
    isDefaultVibrate() {
        return this.remoteMsg[RNRemoteMessage.NOTIFICATION.ISDEFAULTVIBRATE];
    }
}


export const RNReceiverEvent = {
    PushMsgReceiverEvent: 'PushMsgReceiverEvent',
    PushXmppMsgReceiverEvent: 'PushXmppMsgReceiverEvent',
    PushTokenMsgReceiverEvent: 'PushTokenMsgReceiverEvent',
}

export const RNErrorEnum = {
    SUCCESS: 0,

    // You do not have a token. Apply for a token.
    ERROR_NO_TOKEN: 907122030,

    // The current network is unavailable. Check the network connection.
    ERROR_NO_NETWORK: 907122031,

    // The token has expired. Delete the token and apply for a new one.
    ERROR_TOKEN_INVALID: 907122032,

    // If the Push service is unavailable, contact Huawei technical support.
    ERROR_SERVICE_NOT_AVAILABLE: 907122046,

    // If the Push server returns an error, contact Huawei technical support.
    ERROR_PUSH_SERVER: 907122047,

    // Unknown error. Contact Huawei technical support.
    ERROR_UNKNOWN: 907122045,

    // The number of subscribed topics exceeds 2000.
    ERROR_TOPIC_EXCEED: 907122034,

    // Failed to send the subscription topic. Contact Huawei technical support.
    ERROR_TOPIC_SEND: 907122035,

    // Push rights are not enabled. Enable the service and set push service parameters at AppGallery Connect.
    ERROR_NO_RIGHT: 907122036,

    // Failed to apply for the token. Contact Huawei technical support.
    ERROR_GET_TOKEN_ERR: 907122037,

    // No storage location is selected for the application or the storage location is invalid.
    ERROR_STORAGE_LOCATION_EMPTY: 907122038,

    // Failed to apply for a token. Cross-region token application is not allowed.
    ERROR_NOT_ALLOW_CROSS_APPLY: 907122053,

    // The message body size exceeds the maximum.
    ERROR_SIZE: 907122041,

    // The message contains invalid parameters.
    ERROR_INVALID_PARAMETERS: 907122042,

    // The number of sent messages reaches the upper limit. The messages will be discarded.
    ERROR_TOO_MANY_MESSAGES: 907122043,

    // The message lifetime expires before the message is successfully sent to the APP server.
    ERROR_TTL_EXCEEDED: 907122044,

    //  Huawei Mobile Services (APK) can't connect  Huawei Push  Kit.
    ERROR_HMS_CLIENT_API: 907122048,

    // The current EMUI version is too early to use the capability.
    ERROR_OPERATION_NOT_SUPPORTED: 907122049,

    // The operation cannot be performed in the main thread.
    ERROR_MAIN_THREAD: 907122050,

    // The device certificate authentication fails.
    ERROR_HMS_DEVICE_AUTH_FAILED_SELF_MAPPING: 907122051,

    // Failed to bind the service.
    ERROR_BIND_SERVICE_SELF_MAPPING: 907122052,

    // The SDK is being automatically initialized. Try again later.
    ERROR_AUTO_INITIALIZING: 907122054,

    /*The input parameter is incorrect. Check whether the related configuration information is correct.
     * Example: app_id in the agconnect - services.json file;
     * Check whether the build.gradle file is configured with the certificate signature.
     */
    ERROR_ARGUMENTS_INVALID: 907135000,
    // Internal Push error. Contact Huawei technical support engineers.
    ERROR_INTERNAL_ERROR: 907135001,
    // The service does not exist. The invoked interface does not exist.
    ERROR_NAMING_INVALID: 907135002,
    // The ApiClient object is invalid.
    ERROR_CLIENT_API_INVALID: 907135003,
    // Invoking AIDL times out. Contact Huawei technical support.
    ERROR_EXECUTE_TIMEOUT: 907135004,
    // The current area does not support this service.
    ERROR_NOT_IN_SERVICE: 907135005,
    // If the AIDL connection session is invalid, contact Huawei technical support.
    ERROR_SESSION_INVALID: 907135006,
    // An error occurred when invoking an unspecified API.
    ERROR_API_NOT_SPECIFIED: 1002,

    /* Failed to invoke the gateway to query the application scope.
     * Check whether the current app has been created and enabled in AppGallery Connect.
     * If yes, contact Huawei technical support.
     */
    ERROR_GET_SCOPE_ERROR: 907135700,
    /* Scope is not configured on the AppGallery Connect.
     * Check whether the current app has been created and enabled in AppGallery Connect.
     * If yes, contact Huawei technical support.
     */
    ERROR_SCOPE_LIST_EMPTY: 907135701,

    /* The certificate fingerprint is not configured on the AppGallery Connect.
     * 1. Check whether your phone can access the Internet.
     * 2. Check whether the correct certificate fingerprint is configured in AppGallery Connect. For details, see AppGallery Connect configuration in Development Preparations.
     * 3. If the check result is correct, contact Huawei technical support.
     */
    ERROR_CERT_FINGERPRINT_EMPTY: 907135702,

    //Permission is not configured on the AppGallery Connect.
    ERROR_PERMISSION_LIST_EMPTY: 907135703,

    // The authentication information of the application does not exist.
    ERROR_AUTH_INFO_NOT_EXIST: 6002,

    // An error occurred during certificate fingerprint verification. Check whether the correct certificate fingerprint is configured in AppGallery Connect. For details, see AppGallery Connect configuration in Development Preparations.
    ERROR_CERT_FINGERPRINT_ERROR: 6003,

    // Interface authentication: The permission does not exist and is not applied for in AppGallery Connect.
    ERROR_PERMISSION_NOT_EXIST: 6004,

    // Interface authentication: unauthorized.
    ERROR_PERMISSION_NOT_AUTHORIZED: 6005,

    // Interface authentication: The authorization expires.
    ERROR_PERMISSION_EXPIRED: 6006,

}
