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

export class RNRemoteMessage {
  static INSTANCE_ID_SCOPE = "HCM";

  static PRIORITY_UNKNOWN = 0;
  static PRIORITY_HIGH = 1;
  static PRIORITY_NORMAL = 2;

  static COLLAPSEKEY = "collapseKey";
  static DATA = "data";
  static DATAOFMAP = "dataOfMap";
  static MESSAGEID = "messageId";
  static MESSAGETYPE = "messageType";
  static ORIGINALURGENCY = "originalUrgency";
  static URGENCY = "urgency";
  static TTL = "ttl";
  static SENTTIME = "sentTime";
  static TO = "to";
  static FROM = "from";
  static TOKEN = "token";
  static ANALYTIC_INFO = "analyticInfo";
  static ANALYTIC_INFO_MAP = "analyticInfoMap";

  static NOTIFICATION = {
    TITLE: "title",
    TITLELOCALIZATIONKEY: "titleLocalizationKey",
    TITLELOCALIZATIONARGS: "titleLocalizationArgs",
    BODYLOCALIZATIONKEY: "bodyLocalizationKey",
    BODYLOCALIZATIONARGS: "bodyLocalizationArgs",
    BODY: "body",
    ICON: "icon",
    SOUND: "Sound",
    TAG: "Tag",
    COLOR: "Color",
    CLICKACTION: "ClickAction",
    CHANNELID: "ChannelId",
    IMAGEURL: "ImageUrl",
    LINK: "Link",
    NOTIFYID: "NotifyId",
    WHEN: "When",
    LIGHTSETTINGS: "LightSettings",
    BADGENUMBER: "BadgeNumber",
    IMPORTANCE: "Importance",
    TICKER: "Ticker",
    VIBRATECONFIG: "vibrateConfig",
    VISIBILITY: "visibility",
    INTENTURI: "intentUri",
    ISAUTOCANCEL: "isAutoCancel",
    ISLOCALONLY: "isLocalOnly",
    ISDEFAULTLIGHT: "isDefaultLight",
    ISDEFAULTSOUND: "isDefaultSound",
    ISDEFAULTVIBRATE: "isDefaultVibrate",
  };

  constructor(options = {}) {
    this.remoteMsg = options;
  }

  /*
   * parse all element,unordered
   */
  parseMsgAllAttribute() {
    let pushResult = "";
    for (const key in this.remoteMsg) {
      pushResult = pushResult + key + " : " + this.remoteMsg[key] + "\n";
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
   * getAnalyticInfo() Obtains the tag of a message
   */
  getAnalyticInfo() {
    return this.remoteMsg[RNRemoteMessage.ANALYTIC_INFO];
  }

  /*
   * getAnalyticInfoMap() Obtains the analysis data of the Map
   */
  getAnalyticInfoMap() {
    return this.remoteMsg[RNRemoteMessage.ANALYTIC_INFO_MAP];
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
    return this.remoteMsg[RNRemoteMessage.NOTIFICATION.TITLELOCALIZATIONARGS];
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
