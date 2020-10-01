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

import React, { Component } from 'react';
import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  TextInput,
  ToastAndroid,
} from 'react-native';

import {
  HmsLocalNotification,
  HmsPushResultCode,
} from '@hmscore/react-native-hms-push';

import { styles } from './styles';

const defaultNotification = {
  [HmsLocalNotification.Attr.title]: 'Notification Title',
  [HmsLocalNotification.Attr.message]: 'Notification Message', // (required)
  [HmsLocalNotification.Attr.ticker]: 'Optional Ticker',
  [HmsLocalNotification.Attr.showWhen]: true,
  // [HmsLocalNotification.Attr.largeIconUrl]: 'https://developer.huawei.com/Enexport/sites/default/images/en/Develop/hms/push/push2-tuidedao.png', //
  [HmsLocalNotification.Attr.largeIcon]: 'ic_launcher',
  [HmsLocalNotification.Attr.smallIcon]: 'ic_notification',
  [HmsLocalNotification.Attr.bigText]: 'This is a bigText',
  [HmsLocalNotification.Attr.subText]: 'This is a subText',
  [HmsLocalNotification.Attr.color]: 'white',
  [HmsLocalNotification.Attr.vibrate]: false,
  [HmsLocalNotification.Attr.vibrateDuration]: 1000,
  [HmsLocalNotification.Attr.tag]: 'hms_tag',
  [HmsLocalNotification.Attr.groupSummary]: false,
  [HmsLocalNotification.Attr.ongoing]: false,
  [HmsLocalNotification.Attr.importance]: HmsLocalNotification.Importance.max,
  [HmsLocalNotification.Attr.dontNotifyInForeground]: false,
  [HmsLocalNotification.Attr.autoCancel]: false,
  [HmsLocalNotification.Attr.actions]: '["Yes", "No"]',
  [HmsLocalNotification.Attr.invokeApp]: false,
  // [HmsLocalNotification.Attr.channelId]: 'huawei-hms-rn-push-channel-id', // Please read the documentation before using this param
};

export default class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      log: '',
      title: 'HMS Push',
      message: 'This is Local Notification',
      bigText: 'This is a bigText',
      subText: 'This is a subText',
      tag: null,
    };
  }

  toast = msg => {
    ToastAndroid.show(msg, ToastAndroid.SHORT);
  };

  log(msg) {
    this.setState(
      {
        log: msg + '\n' + this.state.log,
      },
      this.toast(msg),
    );
  }

  changeNotificationValue(key, data) {
    this.setState({
      [key]: data,
    });
  }

  localNotificationScheduled() {
    HmsLocalNotification.localNotificationSchedule(
      {
        ...defaultNotification,
        [HmsLocalNotification.Attr.title]: this.state.title,
        [HmsLocalNotification.Attr.message]: this.state.message,
        [HmsLocalNotification.Attr.bigText]: this.state.bigText,
        [HmsLocalNotification.Attr.subText]: this.state.subText,
        [HmsLocalNotification.Attr.tag]: this.state.tag,
        [HmsLocalNotification.Attr.fireDate]: new Date(
          Date.now() + 60 * 1000,
        ).getTime(), // in 1 min
        [HmsLocalNotification.Attr.allowWhileIdle]: true,
      },
      (result, resultInfo) => {
        this.log(
          result == HmsPushResultCode.SUCCESS
            ? '[LocalNotification Scheduled] ' + JSON.stringify(resultInfo)
            : '[LocalNotification Scheduled] Error/Exception: ' + result,
          ToastAndroid.SHORT,
        );
      },
    );
  }

  localNotification() {
    HmsLocalNotification.localNotification(
      {
        ...defaultNotification,
        [HmsLocalNotification.Attr.title]: this.state.title,
        [HmsLocalNotification.Attr.message]: this.state.message,
        [HmsLocalNotification.Attr.bigText]: this.state.bigText,
        [HmsLocalNotification.Attr.subText]: this.state.subText,
        [HmsLocalNotification.Attr.tag]: this.state.tag,
      },
      (result, resultInfo) => {
        this.log(
          result == HmsPushResultCode.SUCCESS
            ? '[LocalNotification Default] ' + JSON.stringify(resultInfo)
            : '[LocalNotification Default] Error/Exception: ' + result,
          ToastAndroid.SHORT,
        );
      },
    );
  }
  localNotificationVibrate() {
    HmsLocalNotification.localNotification(
      {
        ...defaultNotification,
        [HmsLocalNotification.Attr.title]: this.state.title,
        [HmsLocalNotification.Attr.message]: this.state.message,
        [HmsLocalNotification.Attr.bigText]: this.state.bigText,
        [HmsLocalNotification.Attr.subText]: this.state.subText,
        [HmsLocalNotification.Attr.tag]: this.state.tag,
        [HmsLocalNotification.Attr.vibrate]: true,
        [HmsLocalNotification.Attr.vibrateDuration]: 5000,
      },
      (result, resultInfo) => {
        this.log(
          result == HmsPushResultCode.SUCCESS
            ? '[LocalNotification Vibrate] ' + JSON.stringify(resultInfo)
            : '[LocalNotification Vibrate] Error/Exception: ' + result,
          ToastAndroid.SHORT,
        );
      },
    );
  }

  localNotificationRepeat() {
    HmsLocalNotification.localNotification(
      {
        ...defaultNotification,
        [HmsLocalNotification.Attr.title]: this.state.title,
        [HmsLocalNotification.Attr.message]: this.state.message,
        [HmsLocalNotification.Attr.bigText]: this.state.bigText,
        [HmsLocalNotification.Attr.subText]: this.state.subText,
        [HmsLocalNotification.Attr.tag]: this.state.tag,
        [HmsLocalNotification.Attr.repeatType]:
          HmsLocalNotification.RepeatType.minute,
      },
      (result, resultInfo) => {
        this.log(
          result == HmsPushResultCode.SUCCESS
            ? '[LocalNotification Repeat] ' + JSON.stringify(resultInfo)
            : '[LocalNotification Repeat] Error/Exception: ' + result,
          ToastAndroid.SHORT,
        );
      },
    );
  }
  localNotificationSound() {
    HmsLocalNotification.localNotification(
      {
        ...defaultNotification,
        [HmsLocalNotification.Attr.title]: this.state.title,
        [HmsLocalNotification.Attr.message]: this.state.message,
        [HmsLocalNotification.Attr.bigText]: this.state.bigText,
        [HmsLocalNotification.Attr.subText]: this.state.subText,
        [HmsLocalNotification.Attr.tag]: this.state.tag,
        [HmsLocalNotification.Attr.playSound]: true,
        [HmsLocalNotification.Attr.soundName]: 'huawei_bounce.mp3',
      },
      (result, resultInfo) => {
        this.log(
          result == HmsPushResultCode.SUCCESS
            ? '[LocalNotification Sound] ' + JSON.stringify(resultInfo)
            : '[LocalNotification Sound] Error/Exception: ' + result,
          ToastAndroid.SHORT,
        );
      },
    );
  }
  localNotificationPriority() {
    HmsLocalNotification.localNotification(
      {
        ...defaultNotification,
        [HmsLocalNotification.Attr.title]: this.state.title,
        [HmsLocalNotification.Attr.message]: this.state.message,
        [HmsLocalNotification.Attr.bigText]: this.state.bigText,
        [HmsLocalNotification.Attr.subText]: this.state.subText,
        [HmsLocalNotification.Attr.tag]: this.state.tag,
        [HmsLocalNotification.Attr.priority]: HmsLocalNotification.Priority.max,
      },
      (result, resultInfo) => {
        this.log(
          result == HmsPushResultCode.SUCCESS
            ? '[LocalNotification Priority] ' + JSON.stringify(resultInfo)
            : '[LocalNotification Priority] Error/Exception: ' + result,
          ToastAndroid.SHORT,
        );
      },
    );
  }

  localNotificationOngoing() {
    HmsLocalNotification.localNotification(
      {
        ...defaultNotification,
        [HmsLocalNotification.Attr.title]: this.state.title,
        [HmsLocalNotification.Attr.message]: this.state.message,
        [HmsLocalNotification.Attr.bigText]: this.state.bigText,
        [HmsLocalNotification.Attr.subText]: this.state.subText,
        [HmsLocalNotification.Attr.tag]: this.state.tag,
        [HmsLocalNotification.Attr.ongoing]: true,
      },
      (result, resultInfo) => {
        this.log(
          result == HmsPushResultCode.SUCCESS
            ? '[LocalNotification Ongoing] ' + JSON.stringify(resultInfo)
            : '[LocalNotification Ongoing] Error/Exception: ' + result,
          ToastAndroid.SHORT,
        );
      },
    );
  }
  localNotificationBigImage() {
    HmsLocalNotification.localNotification(
      {
        ...defaultNotification,
        [HmsLocalNotification.Attr.title]: this.state.title,
        [HmsLocalNotification.Attr.message]: this.state.message,
        [HmsLocalNotification.Attr.bigText]: this.state.bigText,
        [HmsLocalNotification.Attr.subText]: this.state.subText,
        [HmsLocalNotification.Attr.tag]: this.state.tag,
        [HmsLocalNotification.Attr.bigPictureUrl]:
          'https://www-file.huawei.com/-/media/corp/home/image/logo_400x200.png',
      },
      (result, resultInfo) => {
        this.log(
          result == HmsPushResultCode.SUCCESS
            ? '[LocalNotification BigImage] ' + JSON.stringify(resultInfo)
            : '[LocalNotification BigImage] Error/Exception: ' + result,
          ToastAndroid.SHORT,
        );
      },
    );
  }

  render() {
    return (
      <ScrollView>
        <View style={styles.container}>
          <Text
            style={[styles.buttonText, styles.width30, styles.paddingTop20]}>
            Title :
          </Text>
          <TextInput
            value={this.state.title}
            style={[styles.inputTopic, styles.width35]}
            placeholder="title"
            onChangeText={e => this.changeNotificationValue('title', e)}
          />
          <TextInput
            value={this.state.tag}
            style={[styles.inputTopic, styles.width35]}
            placeholder="tag"
            onChangeText={e => this.changeNotificationValue('tag', e)}
          />
        </View>
        <View style={styles.container}>
          <Text
            style={[styles.buttonText, styles.width30, styles.paddingTop20]}>
            Message :
          </Text>
          <TextInput
            value={this.state.message}
            style={[styles.inputTopic, styles.width70]}
            placeholder="message"
            onChangeText={e => this.changeNotificationValue('message', e)}
          />
        </View>
        <View style={styles.container}>
          <Text
            style={[styles.buttonText, styles.width30, styles.paddingTop20]}>
            BigText :
          </Text>
          <TextInput
            value={this.state.bigText}
            style={[styles.inputTopic, styles.width70, styles.fontSizeSmall]}
            placeholder="bigText"
            onChangeText={e => this.changeNotificationValue('bigText', e)}
          />
        </View>
        <View style={styles.container}>
          <Text
            style={[styles.buttonText, styles.width30, styles.paddingTop20]}>
            SubText :
          </Text>
          <TextInput
            value={this.state.subText}
            style={[styles.inputTopic, styles.width70, styles.fontSizeSmall]}
            placeholder="subText"
            onChangeText={e => this.changeNotificationValue('subText', e)}
          />
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.secondaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.localNotification()}>
            <Text style={styles.buttonText}>Local Notification (Default)</Text>
          </TouchableOpacity>
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.secondaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.localNotificationOngoing()}>
            <Text style={styles.buttonText}>+ Ongoing</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.secondaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.localNotificationSound()}>
            <Text style={styles.buttonText}>+ Sound</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.secondaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.localNotificationVibrate()}>
            <Text style={styles.buttonText}>+ Vibrate</Text>
          </TouchableOpacity>
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.secondaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.localNotificationBigImage()}>
            <Text style={styles.buttonText}>+ BigImage</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.secondaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.localNotificationRepeat()}>
            <Text style={styles.buttonText}>+ Repeat</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.secondaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.localNotificationScheduled()}>
            <Text style={styles.buttonText}>+ Scheduled</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              HmsLocalNotification.cancelAllNotifications((result, resultInfo) => {
                this.log(
                  result == HmsPushResultCode.SUCCESS
                    ? '[cancelAllNotifications] ' + resultInfo
                    : '[cancelAllNotifications] Error/Exception: ' + result,
                );
              });
            }}>
            <Text style={styles.buttonText}>cancelAllNotifications</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              HmsLocalNotification.getNotifications((result, resultInfo) => {
                this.log(
                  result == HmsPushResultCode.SUCCESS
                    ? '[getNotifications] active ' +
                    resultInfo.length +
                    ' notifications'
                    : '[getNotifications] Error/Exception: ' + result,
                );
              });
            }}>
            <Text style={styles.buttonText}>getNotifications</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              HmsLocalNotification.cancelScheduledNotifications((result, resultInfo) => {
                this.log(
                  result == HmsPushResultCode.SUCCESS
                    ? '[cancelScheduledNotifications] ' + resultInfo
                    : '[cancelScheduledNotifications] Error/Exception: ' + result,
                );
              });
            }}>
            <Text style={[styles.buttonText, styles.buttonTextSmall]}>
              cancelScheduledNotifications
            </Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() =>
              HmsLocalNotification.getScheduledNotifications(
                (result, resultInfo) => {
                  this.log(
                    result == HmsPushResultCode.SUCCESS
                      ? '[getScheduledNotifications] ' +
                      JSON.stringify(resultInfo) +
                      ' notifications'
                      : '[getNotifications] Error/Exception: ' + result,
                  );
                })
            }>
            <Text style={[styles.buttonText, styles.buttonTextSmallest]}>
              getScheduledLocalNotifications
            </Text>
          </TouchableOpacity>
        </View>
        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              HmsLocalNotification.cancelNotificationsWithTag('tag',(result, resultInfo) => {
                this.log(
                  result == HmsPushResultCode.SUCCESS
                    ? '[cancelNotificationsWithTag] ' + resultInfo
                    : '[cancelNotificationsWithTag] Error/Exception: ' + result,
                );
              });
            }}>
            <Text style={[styles.buttonText, styles.buttonTextSmallest]}>
              cancelNotificationsWithTag(tag)
            </Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              HmsLocalNotification.getChannels((result, resultInfo) => {
                this.log(
                  result == HmsPushResultCode.SUCCESS
                    ? '[getChannels] ' +
                    JSON.stringify(resultInfo)
                    : '[getChannels] Error/Exception: ' + result,
                );
              });
            }}>
            <Text style={styles.buttonText}>getChannels</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              HmsLocalNotification.cancelNotifications( (result, resultInfo) => {
                this.log(
                  result == HmsPushResultCode.SUCCESS
                    ? '[cancelNotifications] ' + resultInfo
                    : '[cancelNotifications] Error/Exception: ' + result,
                );
              });
            }}>
            <Text style={styles.buttonText}>cancelNotifications</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              HmsLocalNotification.deleteChannel("hms-channel-custom", (result, resultInfo) => {
                this.log(
                  result == HmsPushResultCode.SUCCESS
                    ? '[deleteChannel] ' + resultInfo
                    : '[deleteChannel] Error/Exception: ' + result,
                );
              });
            }}>
            <Text style={styles.buttonText}>deleteChannel</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              HmsLocalNotification.channelBlocked(
                'huawei-hms-rn-push-channel-id',
                (result, resultInfo) => {
                  this.log(
                    result == HmsPushResultCode.SUCCESS
                      ? '[channelBlocked] ' + resultInfo
                      : '[channelBlocked] Error/Exception: ' + result,
                  );
                },
              );
            }}>
            <Text style={styles.buttonText}>channelBlocked</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              HmsLocalNotification.channelExists(
                'huawei-hms-rn-push-channel-id',
                (result, resultInfo) => {
                  this.log(
                    result == HmsPushResultCode.SUCCESS
                      ? '[channelExists] ' + resultInfo
                      : '[channelExists] Error/Exception: ' + result,
                  );
                },
              );
            }}>
            <Text style={styles.buttonText}>channelExists</Text>
          </TouchableOpacity>
        </View>

        <ScrollView style={styles.containerShowResultMsg}>
          <Text>{this.state.log}</Text>
        </ScrollView>
      </ScrollView>
    );
  }
}
