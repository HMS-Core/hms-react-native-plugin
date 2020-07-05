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

import React, {Component} from 'react';
import {
  NativeEventEmitter,
  NativeModules,
  View,
  Text,
  ScrollView,
  TouchableHighlight,
  TouchableOpacity,
  TouchableWithoutFeedback,
  TextInput,
  Button,
  ToastAndroid,
} from 'react-native';

import {
  RNErrorEnum,
  RNReceiverEvent,
  RNRemoteMessage,
} from 'react-native-hwpush';

import {styles} from './styles';

let pushResult = '';
const MaxNumbers = 45;

export default class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      receiveContent: '',
      topic: '',
      disableAutoInit: false,
      enableAutoInit: true,
    };

    this.componentDidMount = this.componentDidMount.bind(this);

    this.turnOnPush = this.turnOnPush.bind(this);
    this.turnOffPush = this.turnOffPush.bind(this);

    this.getID = this.getID.bind(this);
    this.getAAID = this.getAAID.bind(this);
    this.getToken = this.getToken.bind(this);
    this.getCreationTime = this.getCreationTime.bind(this);
    this.deleteAAID = this.deleteAAID.bind(this);
    this.deleteToken = this.deleteToken.bind(this);

    this.onChangeTopic = this.onChangeTopic.bind(this);
    this.subscribe = this.subscribe.bind(this);
    this.unsubscribe = this.unsubscribe.bind(this);

    this.isAutoInitEnabled = this.isAutoInitEnabled.bind(this);

    this.clearLog = this.clearLog.bind(this);

    this.setShowMsgState = this.setShowMsgState.bind(this);
  }

  componentDidMount() {
    const eventEmitter = new NativeEventEmitter(NativeModules.ToastExample);
    this.listenerPushMsg = eventEmitter.addListener(
      // 'PushMsgReceiverEvent',
      RNReceiverEvent.PushMsgReceiverEvent,
      event => {
        pushResult =
          pushResult +
          '----------------------------------------------------------------' +
          '\n' +
          'receive message content start' +
          '\n' +
          '----------------------------------------------------------------' +
          '\n';

        const RNMessageParserSinglen = new RNRemoteMessage(event.msg);

        // const msg = RNMessageParserSinglen.parseMsgAllAttribute();
        const msg =
          RNRemoteMessage.COLLAPSEKEY +
          ':' +
          RNMessageParserSinglen.getCollapseKey() +
          '\n' +
          RNRemoteMessage.DATA +
          ':' +
          RNMessageParserSinglen.getData() +
          '\n' +
          RNRemoteMessage.DATAOFMAP +
          ':' +
          RNMessageParserSinglen.getDataOfMap() +
          '\n' +
          RNRemoteMessage.MESSAGEID +
          ':' +
          RNMessageParserSinglen.getMessageId() +
          '\n' +
          RNRemoteMessage.MESSAGETYPE +
          ':' +
          RNMessageParserSinglen.getMessageType() +
          '\n' +
          RNRemoteMessage.ORIGINALURGENCY +
          ':' +
          RNMessageParserSinglen.getOriginalUrgency() +
          '\n' +
          RNRemoteMessage.URGENCY +
          ':' +
          RNMessageParserSinglen.getUrgency() +
          '\n' +
          RNRemoteMessage.TTL +
          ':' +
          RNMessageParserSinglen.getTtl() +
          '\n' +
          RNRemoteMessage.SENTTIME +
          ':' +
          RNMessageParserSinglen.getSentTime() +
          '\n' +
          RNRemoteMessage.TO +
          ':' +
          RNMessageParserSinglen.getTo() +
          '\n' +
          RNRemoteMessage.FROM +
          ':' +
          RNMessageParserSinglen.getFrom() +
          '\n' +
          RNRemoteMessage.TOKEN +
          ':' +
          this.getFixedNumCharacters(RNMessageParserSinglen.getToken()) +
          RNRemoteMessage.NOTIFICATION.TITLE +
          ':' +
          RNMessageParserSinglen.getNotificationTitle() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.TITLELOCALIZATIONKEY +
          ':' +
          RNMessageParserSinglen.getTitleLocalizationKey() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.TITLELOCALIZATIONARGS +
          ':' +
          RNMessageParserSinglen.getTitleLocalizationArgs() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.BODYLOCALIZATIONKEY +
          ':' +
          RNMessageParserSinglen.getBodyLocalizationKey() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.BODYLOCALIZATIONARGS +
          ':' +
          RNMessageParserSinglen.getBodyLocalizationArgs() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.BODY +
          ':' +
          RNMessageParserSinglen.getBody() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.ICON +
          ':' +
          RNMessageParserSinglen.getIcon() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.SOUND +
          ':' +
          RNMessageParserSinglen.getSound() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.TAG +
          ':' +
          RNMessageParserSinglen.getTag() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.COLOR +
          ':' +
          RNMessageParserSinglen.getColor() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.CLICKACTION +
          ':' +
          RNMessageParserSinglen.getClickAction() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.CHANNELID +
          ':' +
          RNMessageParserSinglen.getChannelId() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.IMAGEURL +
          ':' +
          RNMessageParserSinglen.getImageUrl() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.LINK +
          ':' +
          RNMessageParserSinglen.getLink() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.NOTIFYID +
          ':' +
          RNMessageParserSinglen.getNotifyId() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.WHEN +
          ':' +
          RNMessageParserSinglen.getWhen() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.LIGHTSETTINGS +
          ':' +
          RNMessageParserSinglen.getLightSettings() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.BADGENUMBER +
          ':' +
          RNMessageParserSinglen.getBadgeNumber() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.IMPORTANCE +
          ':' +
          RNMessageParserSinglen.getImportance() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.TICKER +
          ':' +
          RNMessageParserSinglen.getTicker() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.VIBRATECONFIG +
          ':' +
          RNMessageParserSinglen.getVibrateConfig() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.VISIBILITY +
          ':' +
          RNMessageParserSinglen.getVisibility() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.INTENTURI +
          ':' +
          RNMessageParserSinglen.getIntentUri() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.ISAUTOCANCEL +
          ':' +
          RNMessageParserSinglen.isAutoCancel() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.ISLOCALONLY +
          ':' +
          RNMessageParserSinglen.isLocalOnly() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.ISDEFAULTLIGHT +
          ':' +
          RNMessageParserSinglen.isDefaultLight() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.ISDEFAULTSOUND +
          ':' +
          RNMessageParserSinglen.isDefaultSound() +
          '\n' +
          RNRemoteMessage.NOTIFICATION.ISDEFAULTVIBRATE +
          ':' +
          RNMessageParserSinglen.isDefaultVibrate() +
          '\n';
        pushResult = pushResult + msg;

        pushResult =
          pushResult +
          '----------------------------------------------------------------' +
          '\n' +
          'receive message content end' +
          '\n' +
          '----------------------------------------------------------------' +
          '\n';

        this.setState({
          receiveContent: pushResult,
        });
      },
    );

    const eventTokenEmitter = new NativeEventEmitter(
      NativeModules.ToastExample,
    );
    this.listenerToken = eventTokenEmitter.addListener(
      RNReceiverEvent.PushTokenMsgReceiverEvent,
      event => {
        pushResult =
          pushResult +
          'received token:\n' +
          this.getFixedNumCharacters(event.token);

        this.setState({
          receiveContent: pushResult,
        });
      },
    );
  }

  componentWillUnmount() {
    //  listener;
    this.listenerPushMsg.remove();
    this.listenerToken.remove();
  }

  getFixedNumCharacters(content) {
    if (content == null) {
      return 'null';
    }
    const len = content.length;
    let msg = '';
    if (len < MaxNumbers) {
      return content + '\n';
    } else {
      let index = 0;
      while (index * MaxNumbers < len) {
        let subLen = len - index * MaxNumbers;

        if (subLen > MaxNumbers) {
          subLen = MaxNumbers;
        }
        msg = msg + content.substr(index * MaxNumbers, subLen) + '\n';
        index = index + 1;
      }
    }
    return msg;
  }

  toast(msg) {
    ToastAndroid.show(msg, ToastAndroid.SHORT);
  }

  turnOnPush() {
    this.toast('turnOnPush');

    const startTime = new Date().getTime();
    NativeModules.RNHmsMessaging.turnOnPush((result, errinfo) => {
      const spendTime = new Date().getTime() - startTime;
      let msg = '[spend ' + spendTime + 'ms]';

      if (result == RNErrorEnum.SUCCESS) {
        msg = msg + 'turnOnPush success!' + '\n';
      } else {
        msg = msg + 'turnOnPush failed:' + errinfo + '\n';
      }
      this.setShowMsgState(msg);
    });
  }

  turnOffPush() {
    this.toast('turnOffPush');

    const startTime = new Date().getTime();
    NativeModules.RNHmsMessaging.turnOffPush((result, errinfo) => {
      const spendTime = new Date().getTime() - startTime;
      let msg = '[spend ' + spendTime + 'ms]';
      if (result == RNErrorEnum.SUCCESS) {
        msg = msg + 'turnOffPush success!' + '\n';
      } else {
        msg = msg + 'turnOffPush failed:' + errinfo + '\n';
      }
      this.setShowMsgState(msg);
    });
  }

  clearLog() {
    this.toast('clearLog');

    pushResult = '';
    console.log('start clearLog');
    this.setState({
      receiveContent: pushResult,
    });
  }

  getID() {
    this.toast('getID');

    const startTime = new Date().getTime();
    NativeModules.RNHmsInstanceId.getId((result, resultInfo) => {
      const spendTime = new Date().getTime() - startTime;
      let msg = '[spend ' + spendTime + 'ms]';
      if (result == RNErrorEnum.SUCCESS) {
        msg = msg + 'getID success:' + resultInfo + '\n';
      } else {
        msg = msg + 'getID failed:' + resultInfo + '\n';
      }

      this.setShowMsgState(msg);
    });
  }

  getAAID() {
    this.toast('getAAID');

    const startTime = new Date().getTime();
    NativeModules.RNHmsInstanceId.getAAID((result, resultInfo) => {
      const spendTime = new Date().getTime() - startTime;
      let msg = '[spend ' + spendTime + 'ms]';
      if (result == RNErrorEnum.SUCCESS) {
        msg = msg + 'getAAID success:' + resultInfo + '\n';
      } else {
        msg = msg + 'getAAID failed:' + resultInfo + '\n';
      }
      this.setShowMsgState(msg);
    });
  }

  getToken() {
    this.toast('getToken');

    const startTime = new Date().getTime();
    NativeModules.RNHmsInstanceId.getToken((result, token) => {
      const spendTime = new Date().getTime() - startTime;
      let msg = '[spend ' + spendTime + 'ms]';

      if (result == RNErrorEnum.SUCCESS) {
        msg = msg + 'getToken result:' + '\n';
      } else {
        msg = msg + 'getToken exception,error: ' + result + '\n';
      }

      msg = msg + this.getFixedNumCharacters(token);
      this.setShowMsgState(msg);
    });
  }

  getCreationTime() {
    this.toast('getCreationTime');

    const startTime = new Date().getTime();
    NativeModules.RNHmsInstanceId.getCreationTime((result, resultInfo) => {
      const spendTime = new Date().getTime() - startTime;
      let msg = '[spend ' + spendTime + 'ms]';
      if (result == RNErrorEnum.SUCCESS) {
        msg = msg + 'getCreationTime success:' + resultInfo + '\n';
      } else {
        msg = msg + 'getCreationTime failed:' + resultInfo + '\n';
      }
      this.setShowMsgState(msg);
    });
  }

  deleteAAID() {
    this.toast('deleteAAID');

    const startTime = new Date().getTime();
    NativeModules.RNHmsInstanceId.deleteAAID((result, resultInfo) => {
      const spendTime = new Date().getTime() - startTime;
      let msg = '[spend12 ' + spendTime + 'ms]';
      if (result == RNErrorEnum.SUCCESS) {
        msg = msg + 'deleteAAID success!' + '\n';
      } else {
        msg = msg + 'deleteAAID failed:' + resultInfo + '\n';
      }
      this.setShowMsgState(msg);
    });
  }

  deleteToken() {
    this.toast('deleteToken');

    const startTime = new Date().getTime();
    NativeModules.RNHmsInstanceId.deleteToken((result, resultInfo) => {
      const spendTime = new Date().getTime() - startTime;
      let msg = '[spend ' + spendTime + 'ms]';
      if (result == RNErrorEnum.SUCCESS) {
        msg = msg + 'deleteToken success!' + '\n';
      } else {
        msg = msg + 'deleteToken failed:' + resultInfo + '\n';
      }

      this.setShowMsgState(msg);
    });
  }

  subscribe() {
    this.toast('subscribe topic:' + this.state.topic);

    const startTime = new Date().getTime();
    NativeModules.RNHmsMessaging.subscribe(
      this.state.topic,
      (result, errinfo) => {
        const spendTime = new Date().getTime() - startTime;
        let msg = '[spend ' + spendTime + 'ms]';

        if (result == RNErrorEnum.SUCCESS) {
          msg = msg + 'subscribe success!' + '\n';
        } else {
          msg = msg + 'subscribe failed:' + errinfo + '\n';
        }

        this.setShowMsgState(msg);
      },
    );
  }

  unsubscribe() {
    this.toast('unsubscribe topic:' + this.state.topic);

    const startTime = new Date().getTime();
    NativeModules.RNHmsMessaging.unsubscribe(
      this.state.topic,
      (result, errinfo) => {
        const spendTime = new Date().getTime() - startTime;
        let msg = '[spend ' + spendTime + 'ms]';

        if (result == RNErrorEnum.SUCCESS) {
          msg = msg + 'unsubscribe success!' + '\n';
        } else {
          msg = msg + 'unsubscribe failed:' + errinfo + '\n';
        }

        this.setShowMsgState(msg);
      },
    );
  }

  onChangeTopic(inputData) {
    console.log('subscribe input:', inputData);

    this.setState({
      topic: inputData,
    });
  }

  isAutoInitEnabled() {
    this.toast('isAutoInitEnabled');

    const startTime = new Date().getTime();
    NativeModules.RNHmsMessaging.isAutoInitEnabled((result, resultInfo) => {
      const spendTime = new Date().getTime() - startTime;
      let msg = '[spend ' + spendTime + 'ms]';
      if (result == RNErrorEnum.SUCCESS) {
        msg = msg + 'isAutoInitEnabled success:' + resultInfo + '\n';
      } else {
        msg = msg + 'isAutoInitEnabled failed:' + resultInfo + '\n';
      }

      this.setShowMsgState(msg);
    });
  }

  setShowMsgState(msg) {
    pushResult = pushResult + msg;
    this.setState({
      receiveContent: pushResult,
    });
  }

  render() {
    return (
      <ScrollView>
        <Button
          title="Open Custom intent URI Page"
          style={styles.buttonText}
          onPress={() => this.props.navigation.navigate('SecondPage')}
        />
        <View style={styles.container10Top}>
          <TouchableHighlight onPress={this.turnOffPush}>
            <View style={styles.buttonLeft}>
              <Text style={styles.buttonText}>TurnOffPush</Text>
            </View>
          </TouchableHighlight>
          <TouchableOpacity onPress={this.turnOnPush}>
            <View style={styles.buttonRight}>
              <Text style={styles.buttonText}>TurnOnPush</Text>
            </View>
          </TouchableOpacity>
        </View>

        <View style={styles.container10Top}>
          <TouchableHighlight onPress={this.getID}>
            <View style={styles.buttonLeft}>
              <Text style={styles.buttonText}>Get ID</Text>
            </View>
          </TouchableHighlight>
          <TouchableHighlight onPress={this.getAAID}>
            <View style={styles.buttonRight}>
              <Text style={styles.buttonText}>Get AAID</Text>
            </View>
          </TouchableHighlight>
        </View>
        <View style={styles.containerSeamless}>
          <TouchableHighlight onPress={this.getToken}>
            <View style={styles.buttonLeft}>
              <Text style={styles.buttonText}>Get Token</Text>
            </View>
          </TouchableHighlight>
          <TouchableHighlight onPress={this.getCreationTime}>
            <View style={styles.buttonRight}>
              <Text style={styles.buttonText}>Get CreationTime</Text>
            </View>
          </TouchableHighlight>
        </View>
        <View style={styles.containerSeamless}>
          <TouchableHighlight onPress={this.deleteAAID}>
            <View style={styles.buttonLeft}>
              <Text style={styles.buttonText}>Delete AAID</Text>
            </View>
          </TouchableHighlight>
          <TouchableHighlight onPress={this.deleteToken}>
            <View style={styles.buttonRight}>
              <Text style={styles.buttonText}>Delete Token</Text>
            </View>
          </TouchableHighlight>
        </View>

        <View style={styles.containerTopicInput}>
          <TextInput
            value={this.state.topic}
            style={styles.inputTopic}
            placeholder="topic"
            onChangeText={this.onChangeTopic}
          />
        </View>

        <View style={styles.containerSeamless}>
          <TouchableHighlight onPress={this.subscribe}>
            <View style={styles.buttonLeft}>
              <Text style={styles.buttonText}>Subscribe</Text>
            </View>
          </TouchableHighlight>
          <TouchableHighlight onPress={this.unsubscribe}>
            <View style={styles.buttonRight}>
              <Text style={styles.buttonText}>UnSubscribe</Text>
            </View>
          </TouchableHighlight>
        </View>

        <View style={styles.container10Top}>
          <TouchableHighlight
            onPress={() => {
              const startTime = new Date().getTime();
              NativeModules.RNHmsMessaging.setAutoInitEnabled(
                this.state.disableAutoInit,
                (result, resultInfo) => {
                  const spendTime = new Date().getTime() - startTime;
                  let msg = '[spend ' + spendTime + 'ms]';
                  if (result == RNErrorEnum.SUCCESS) {
                    msg =
                      msg + 'setAutoInitEnabled success:' + resultInfo + '\n';
                  } else {
                    msg =
                      msg + 'setAutoInitEnabled failed:' + resultInfo + '\n';
                  }

                  pushResult = pushResult + msg;
                  this.setState({
                    receiveContent: pushResult,
                  });
                },
              );
            }}>
            <View style={styles.buttonLeft}>
              <Text
                value={this.state.disableAutoInit}
                style={styles.buttonText}>
                Disable AutoInit
              </Text>
            </View>
          </TouchableHighlight>
          <TouchableHighlight
            onPress={() => {
              const startTime = new Date().getTime();
              NativeModules.RNHmsMessaging.setAutoInitEnabled(
                this.state.enableAutoInit,
                (result, resultInfo) => {
                  const spendTime = new Date().getTime() - startTime;
                  let msg = '[spend ' + spendTime + 'ms]';
                  if (result == RNErrorEnum.SUCCESS) {
                    msg =
                      msg + 'setAutoInitEnabled success:' + resultInfo + '\n';
                  } else {
                    msg =
                      msg + 'setAutoInitEnabled failed:' + resultInfo + '\n';
                  }

                  pushResult = pushResult + msg;
                  this.setState({
                    receiveContent: pushResult,
                  });
                },
              );
            }}>
            <View value={this.state.enableAutoInit} style={styles.buttonRight}>
              <Text style={styles.buttonText}>Enable AutoInit</Text>
            </View>
          </TouchableHighlight>
        </View>
        <View style={styles.containerSeamless}>
          <TouchableHighlight onPress={this.isAutoInitEnabled}>
            <View style={styles.buttonLeft}>
              <Text style={styles.buttonText}>Is AutoInit Enabled</Text>
            </View>
          </TouchableHighlight>
          <TouchableWithoutFeedback onPress={this.clearLog}>
            <View style={styles.buttonRight}>
              <Text style={styles.buttonText}>ClearLog</Text>
            </View>
          </TouchableWithoutFeedback>
        </View>

        <ScrollView style={styles.containerShowResultMsg}>
          <Text>{this.state.receiveContent}</Text>
        </ScrollView>
      </ScrollView>
    );
  }
}
