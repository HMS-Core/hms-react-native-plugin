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
  NativeEventEmitter,
  NativeModules,
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  TextInput,
  ToastAndroid,
} from 'react-native';

import {
  HmsPushResultCode,
  HmsPushEvent,
  RNRemoteMessage,
  HmsPushMessaging,
  HmsPushInstanceId,
  HmsLocalNotification,
  HmsPushOpenDevice,
  RemoteMessageBuilder,
} from '@hmscore/react-native-hms-push';

import { styles } from './styles';

export default class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      log: '',
      topic: '',
    };

    this.componentDidMount = this.componentDidMount.bind(this);
  }

  componentDidMount() {
    this.onRemoteMessageReceivedListener = HmsPushEvent.onRemoteMessageReceived(result => {
      {
        const RNRemoteMessageObj = new RNRemoteMessage(result.msg);
        HmsLocalNotification.localNotification(
          {
            [HmsLocalNotification.Attr.title]: 'Data Message Received',
            [HmsLocalNotification.Attr.message]: RNRemoteMessageObj.getDataOfMap()
          }, null)
        const msg = RNRemoteMessageObj.parseMsgAllAttribute(result.msg);
        this.log(
          '----- [onRemoteMessageReceived] ----------\n' +
          msg +
          '----------------------------------------------------------------' +
          '\n',
        );
      }
    });


    this.onTokenReceivedListener = HmsPushEvent.onTokenReceived(
      result => {
        this.log('[onTokenReceived]: ' + result.token);
      },
    );

    this.onTokenErrorListener = HmsPushEvent.onTokenError(
      result => {
        this.log('[onTokenError]: ' + result.exception);
      },
    );

    this.onPushMessageSentListener = HmsPushEvent.onPushMessageSent(
      result => {
        this.log('[onPushMessageSent]: msgId:' + result.msgId);
      });

    this.onMessageSentErrorListener = HmsPushEvent.onPushMessageSentError(
      result => {
        this.log('[onPushMessageSentError]:  msgId: ' + result.msgId + ', result: ' + result.result + ', resultInfo: ' + result.resultInfo);
      },
    );

    this.onMessageSentDeliveredListener = HmsPushEvent.onPushMessageSentDelivered(
      result => {
        this.log('[onPushMessageSentDelivered]:  msgId: ' + result.msgId + ', result: ' + result.result + ', resultInfo: ' + result.resultInfo);
      },
    );

    this.onLocalNotificationActionListener = HmsPushEvent.onLocalNotificationAction(
      result => {
        this.log('[onLocalNotificationAction]: ' + result);

        var notification = JSON.parse(result.dataJSON);
        if (notification.action === 'Yes') {
          HmsLocalNotification.cancelNotificationsWithId([notification.id]);
        }
        this.log('Clicked: ' + notification.action);
      },
    );

    this.onNotificationOpenedAppListener = HmsPushEvent.onNotificationOpenedApp(result => {
      this.log('[onNotificationOpenedApp]: ' + JSON.stringify(result));
    });

  }

  componentWillUnmount() {
    this.onRemoteMessageReceivedListener.remove();
    this.onTokenReceivedListener.remove();
    this.onTokenErrorListener.remove();
    this.onPushMessageSentListener.remove();
    this.onMessageSentErrorListener.remove();
    this.onMessageSentDeliveredListener.remove();
    this.onLocalNotificationActionListener.remove();
    this.onNotificationOpenedAppListener.remove();
  }

  log(msg) {
    this.setState(
      {
        log: msg + '\n' + this.state.log,
      },
      this.toast(msg),
    );
  }

  toast = msg => {
    ToastAndroid.show(msg, ToastAndroid.SHORT);
  };

  clearLog() {
    this.setState({
      log: '',
    });
  }

  onChangeTopic(inputData) {
    this.setState({
      topic: inputData,
    });
  }

  turnOnPush() {
    HmsPushMessaging.turnOnPush((result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[turnOnPush] success'
          : '[turnOnPush] Error/Exception: ' + result,
      );
    });
  }

  turnOffPush() {
    HmsPushMessaging.turnOffPush((result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[turnOffPush] success'
          : '[turnOffPush] Error/Exception: ' + result,
      );
    });
  }

  getID() {
    HmsPushInstanceId.getId((result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[getId] ' + resultInfo
          : '[getId] Error/Exception: ' + result,
      );
    });
  }

  getAAID() {
    HmsPushInstanceId.getAAID((result, resultInfo) => {
      this.log(result == HmsPushResultCode.SUCCESS
        ? '[getAAID] ' + resultInfo
        : '[getAAID] Error/Exception: ' + result);
    });
  }
  getOdid() {
    HmsPushOpenDevice.getOdid((result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[getOdid] ' + resultInfo
          : '[getOdid] Error/Exception: ' + resultsg,
      );
    });
  }

  getToken() {
    HmsPushInstanceId.getToken((result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[getToken] ' + resultInfo
          : '[getToken] Error/Exception: ' + result,
      );
    });
  }

  getCreationTime() {
    this.toast('getCreationTime');

    HmsPushInstanceId.getCreationTime((result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[getCreationTime] ' + resultInfo
          : '[getCreationTime] Error/Exception: ' + result,
      );
    });
  }

  deleteAAID() {
    HmsPushInstanceId.deleteAAID((result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[deleteAAID] success'
          : '[deleteAAID] Error/Exception: ' + resultInfo,
      );
    });
  }

  deleteToken() {
    HmsPushInstanceId.deleteToken((result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[deleteToken] success'
          : '[deleteToken] Error/Exception: ' + result,
      );
    });
  }

  subscribe() {
    HmsPushMessaging.subscribe(this.state.topic, (result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[subscribe] success: ' + this.state.topic
          : '[subscribe] Error/Exception: ' + resultInfo,
      );
    });
  }

  unsubscribe() {
    HmsPushMessaging.unsubscribe(this.state.topic, (result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[unsubscribe] success : ' + this.state.topic
          : '[unsubscribe] Error/Exception: ' + resultInfo,
      );
    });
  }

  sendRemoteMessage() {
    HmsPushMessaging.sendRemoteMessage(
      {
        [RemoteMessageBuilder.TO]: '',
        //[RemoteMessageBuilder.MESSAGE_ID]: '', // Auto generated
        [RemoteMessageBuilder.MESSAGE_TYPE]: 'hms',
        [RemoteMessageBuilder.COLLAPSE_KEY]: '-1',
        [RemoteMessageBuilder.TTL]: 120,
        [RemoteMessageBuilder.RECEIPT_MODE]: 1,
        [RemoteMessageBuilder.SEND_MODE]: 1,
        [RemoteMessageBuilder.DATA]: { key1: 'test', message: 'huawei-test' },
      }
    );
  }

  isAutoInitEnabled() {
    HmsPushMessaging.isAutoInitEnabled((result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[isAutoInitEnabled] success ' + resultInfo
          : '[isAutoInitEnabled] Error/Exception: ' + resultInfo,
      );
    });
  }
  setAutoInitEnabled(value) {
    HmsPushMessaging.setAutoInitEnabled(value, (result, resultInfo) => {
      this.log(
        result == HmsPushResultCode.SUCCESS
          ? '[setAutoInitEnabled] ' + value + ' success ' + resultInfo
          : '[setAutoInitEnabled] Error/Exception: ' + value + resultInfo,
      );
    });
  }

  getInitialNotification() {
    HmsPushMessaging.getInitialNotification((result, resultInfo) => {
      this.log('[getInitialNotification] : ' + JSON.stringify(resultInfo));
    });
  }

  render() {
    return (
      <ScrollView>
        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.tertiaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.props.navigation.navigate('CustomURI')}>
            <Text style={styles.buttonText}>Open Custom intent URI Page</Text>
          </TouchableOpacity>
        </View>
        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.secondaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.props.navigation.navigate('LocalNotification')}>
            <Text style={styles.buttonText}>Local Notification</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.turnOffPush();
            }}>
            <Text style={styles.buttonText}>TurnOffPush</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.turnOnPush();
            }}>
            <Text style={styles.buttonText}>TurnOnPush</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getID();
            }}>
            <Text style={styles.buttonText}>Get ID</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getAAID();
            }}>
            <Text style={styles.buttonText}>Get AAID</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getOdid();
            }}>
            <Text style={styles.buttonText}>Get Odid</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getToken();
            }}>
            <Text style={styles.buttonText}>Get Token</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getCreationTime();
            }}>
            <Text style={styles.buttonText}>Get CreationTime</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.deleteAAID();
            }}>
            <Text style={styles.buttonText}>Delete AAID</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.deleteToken();
            }}>
            <Text style={styles.buttonText}>Delete Token</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TextInput
            value={this.state.topic}
            style={styles.inputTopic}
            placeholder="topic"
            onChangeText={e => this.onChangeTopic(e)}
          />
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.subscribe();
            }}>
            <Text style={styles.buttonText}>Subscribe</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.unsubscribe();
            }}>
            <Text style={styles.buttonText}>UnSubscribe</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.setAutoInitEnabled(false);
            }}>
            <Text style={styles.buttonText}>Disable AutoInit</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.setAutoInitEnabled(true);
            }}>
            <Text style={styles.buttonText}>Enable AutoInit</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.isAutoInitEnabled();
            }}>
            <Text style={styles.buttonText}>Is AutoInit Enabled</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getInitialNotification();
            }}>
            <Text style={[styles.buttonText, styles.buttonTextSmall]}>
              getInitialNotification
            </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.sendRemoteMessage();
            }}>
            <Text style={styles.buttonText}>sendRemoteMessage</Text>
          </TouchableOpacity>
        </View>

        <ScrollView style={styles.containerShowResultMsg}>
          <Text>{this.state.log}</Text>
        </ScrollView>
      </ScrollView>
    );
  }
}
