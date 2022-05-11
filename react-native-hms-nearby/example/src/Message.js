/*
    Copyright 2020-2022. Huawei Technologies Co., Ltd. All rights reserved.

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

import React from 'react';
import {
  Text,
  View,
  TextInput,
  TouchableOpacity,
  Switch,
  NativeEventEmitter,
  ToastAndroid
} from 'react-native';
import { HMSMessage } from '@hmscore/react-native-hms-nearby';
import { styles } from './Styles';
import { stringToByteArray, byteArrayToString, messageResult } from './Converter.js';

export default class Message extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      messageType: false,
      isClicked: false,
      subscribedMessage: '',
      publishedMessage: '',
    };
  }

  componentDidMount() {

    this.eventEmitter = new NativeEventEmitter(HMSMessage);

    this.eventEmitter.addListener(HMSMessage.GET_ON_TIMEOUT, (event) => {
      console.log(event);
      ToastAndroid.showWithGravity(event.onTimeout, ToastAndroid.SHORT, ToastAndroid.CENTER);
    });

    this.eventEmitter.addListener(HMSMessage.PUT_ON_TIMEOUT, (event) => {
      console.log(event);
      ToastAndroid.showWithGravity(event.onTimeout, ToastAndroid.SHORT, ToastAndroid.CENTER);
    });

    this.eventEmitter.addListener(HMSMessage.STATUS_ON_CHANGED, (event) => {
      console.log(event);
    });

    this.eventEmitter.addListener(HMSMessage.BLE_ON_SIGNAL_CHANGED, (event) => {
      console.log(event);
    });

    this.eventEmitter.addListener(HMSMessage.DISTANCE_ON_CHANGED, (event) => {
      console.log(event);
    });

    this.eventEmitter.addListener(HMSMessage.MESSAGE_ON_FOUND, (event) => {
      console.log(event);
      ToastAndroid.showWithGravity(byteArrayToString(event.content) + " " + event.type, ToastAndroid.SHORT, ToastAndroid.CENTER);
      this.setState({ subscribedMessage: byteArrayToString(event.content) });
    });

    this.eventEmitter.addListener(HMSMessage.MESSAGE_ON_LOST, (event) => {
      console.log(event);
    });

    this.registerStatusCallback();
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSMessage.GET_ON_TIMEOUT);
    this.eventEmitter.removeAllListeners(HMSMessage.PUT_ON_TIMEOUT);
    this.eventEmitter.removeAllListeners(HMSMessage.STATUS_ON_CHANGED);
    this.eventEmitter.removeAllListeners(HMSMessage.BLE_ON_SIGNAL_CHANGED);
    this.eventEmitter.removeAllListeners(HMSMessage.DISTANCE_ON_CHANGED);
    this.eventEmitter.removeAllListeners(HMSMessage.MESSAGE_ON_FOUND);
    this.eventEmitter.removeAllListeners(HMSMessage.MESSAGE_ON_LOST);
    this.unregisterStatusCallback();
  }

  async putMessage() {
    try {
      this.setState({ isClicked: true });
      var result = await HMSMessage.put(this.getMessageConfig(), stringToByteArray(this.state.publishedMessage));
      messageResult(result, "Message Published");
    } catch (e) {
      console.log(e);
    }
  }

  async putMessageWithOption() {
    try {
      this.setState({ isClicked: true });
      var result = await HMSMessage.putWithOption(this.getMessageConfig(), stringToByteArray(this.state.publishedMessage), this.getPutOption());
      messageResult(result, "Message Put With Option");
    } catch (e) {
      console.log(e);
    }
  }

  async registerStatusCallback() {
    try {
      var result = await HMSMessage.registerStatusCallback();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async unregisterStatusCallback() {
    try {
      var result = await HMSMessage.unRegisterStatusCallback();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async getMessage() {
    try {
      this.setState({ isClicked: true });
      var result = await HMSMessage.getMessage();
      messageResult(result, "Message Get");
    } catch (e) {
      console.log(e);
    }
  }

  async getMessageWithOption() {
    try {
      this.setState({ isClicked: true });
      var result = await HMSMessage.getMessageWithOption(this.getGetOption());
      messageResult(result, "Message Get With Option");
    } catch (e) {
      console.log(e);
    }
  }

  async getMessagePending() {
    try {
      this.setState({ isClicked: true });
      var result = await HMSMessage.getMessagePending();
      messageResult(result, "Message Get Pending");
    } catch (e) {
      console.log(e);
    }
  }

  async getMessagePendingWithOption() {
    try {
      this.setState({ isClicked: true });
      var result = await HMSMessage.getMessagePendingWithOption(this.getGetOption());
      messageResult(result, "Message Get Pending With Option");
    } catch (e) {
      console.log(e);
    }
  }

  async ungetMessage() {
    try {
      this.setState({ isClicked: true });
      var result = await HMSMessage.unget();
      messageResult(result, "Message Unget");
    } catch (e) {
      console.log(e);
    }
  }

  async ungetMessagePending() {
    try {
      this.setState({ isClicked: true });
      var result = await HMSMessage.ungetPending();
      messageResult(result, "Message Unget Pending");
    } catch (e) {
      console.log(e);
    }
  }

  async unputMessage() {
    try {
      this.setState({ isClicked: true });
      var result = await HMSMessage.unput(this.getMessageConfig(), stringToByteArray(this.state.publishedMessage));
      messageResult(result, "Messagae Unput");
    } catch (e) {
      console.log(e);
    }
  }

  toggleSwitch = () => {
    this.setState({
      messageType: !this.state.messageType,
      subscribedMessage: '',
      publishedMessage: ''
    })
  }

  getGetOption = () => {
    return {
      policy: this.getPolicyConfig(),
      picker: {
        includeAllTypes: true,
        includeIBeaconIds: [{
          iBeaconUuid: "<your-beacon-uuid>",
          major: "0",
          minor: "0"
        }],
        includeNamespaceType: [{
          namespace: "<your-namespace>",
          type: "<your-type>"
        }]
      },
      setCallback: true
    }
  }

  getPutOption = () => {
    return {
      policy: this.getPolicyConfig(),
      setCallback: true
    }
  }

  getMessageConfig = () => {
    return {
      type: HMSMessage.MESSAGE_TYPE_EDDYSTONE_UID,
      namespace: HMSMessage.MESSAGE_NAMESPACE_RESERVED
    }
  }

  getPolicyConfig = () => {
    return {
      findingMode: HMSMessage.POLICY_FINDING_MODE_DEFAULT,
      distanceType: HMSMessage.POLICY_DISTANCE_TYPE_DEFAULT,
      ttlSeconds: HMSMessage.POLICY_TTL_SECONDS_DEFAULT
    }
  }

  render() {
    return (
      <View style={styles.baseView} >

        <View style={styles.toolbar}>

          <View style={styles.viewdividedtwo}>
            <View style={styles.halfItem1}>
              <Text style={styles.titleToolbar}>{this.state.messageType ? 'Beacon Based' : 'App Based'} Message Operations</Text>
            </View>
            <View style={styles.halfItem2}>
              <Switch
                style={{ alignSelf: "center" }}
                trackColor={{ false: "#e5e5e5", true: "#50AF52" }}
                thumbColor={this.state.messageType ? "#ffffff" : "#ffffff"}
                onValueChange={this.toggleSwitch.bind(this)}
                value={this.state.messageType}
              />
            </View>
          </View >

        </View>

        {this.state.messageType ?
          <View>

            <TextInput
              style={styles.customInput}
              placeholder="Beacon Message"
              value={this.state.subscribedMessage}
              multiline={true}
              editable={false}
            />
            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.basicButtonOpacity}
                onPress={() => this.getMessagePending().then(() => this.setState({ isClicked: false }))}
                underlayColor="#fff"
                disabled={this.state.isClicked}>
                <Text style={styles.basicButtonLabel}> GET PENDING </Text>
              </TouchableOpacity>
            </View>
            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.basicButtonOpacity}
                onPress={() => this.getMessagePendingWithOption().then(() => this.setState({ isClicked: false }))}
                underlayColor="#fff"
                disabled={this.state.isClicked}>
                <Text style={styles.basicButtonLabel}> GET PENDING WITH OPTIONS</Text>
              </TouchableOpacity>
            </View>
            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.basicButtonOpacity}
                onPress={() => this.ungetMessagePending().then(() => this.setState({ isClicked: false }))}
                underlayColor="#fff"
                disabled={this.state.isClicked}>
                <Text style={styles.basicButtonLabel}> UNGET PENDING </Text>
              </TouchableOpacity>
            </View>

          </View>
          :
          <View style={{ marginTop: 5 }}>
            <TextInput
              style={styles.customInput}
              placeholder="Type your message to publish..."
              onChangeText={text => this.setState({ publishedMessage: text })}
              multiline={false}
              editable={!this.state.isClicked}
            />

            <TextInput
              style={styles.customInput}
              placeholder="Subscribed Message"
              value={this.state.subscribedMessage}
              multiline={true}
              editable={false}
            />
            <View style={styles.viewdividedtwo}>
              <View style={styles.halfItem3}>
                <View style={styles.basicButton}>
                  <TouchableOpacity
                    style={styles.basicButtonOpacity}
                    onPress={() => this.putMessage().then(() => this.setState({ isClicked: false }))}
                    underlayColor="#fff"
                    disabled={this.state.isClicked}>
                    <Text style={styles.basicButtonLabel}> PUT </Text>
                  </TouchableOpacity>
                </View>
              </View>
              <View style={styles.halfItem3}>
                <View style={styles.basicButton}>
                  <TouchableOpacity
                    style={styles.basicButtonOpacity}
                    onPress={() => this.unputMessage(this.state.publishedMessage).then(() => this.setState({ isClicked: false }))}
                    underlayColor="#fff"
                    disabled={this.state.isClicked}>
                    <Text style={styles.basicButtonLabel}> UNPUT</Text>
                  </TouchableOpacity>
                </View>
              </View>
            </View>
            <View style={{ marginTop: 40 }}>
              <View style={styles.viewdividedtwo}>
                <View style={styles.halfItem3}>
                  <View style={styles.basicButton}>
                    <TouchableOpacity
                      style={styles.basicButtonOpacity}
                      onPress={() => this.getMessage().then(() => this.setState({ isClicked: false }))}
                      underlayColor="#fff"
                      disabled={this.state.isClicked}>
                      <Text style={styles.basicButtonLabel}> GET </Text>
                    </TouchableOpacity>
                  </View>
                </View>
                <View style={styles.halfItem3}>
                  <View style={styles.basicButton}>
                    <TouchableOpacity
                      style={styles.basicButtonOpacity}
                      onPress={() => this.ungetMessage().then(() => this.setState({ isClicked: false }))}
                      underlayColor="#fff"
                      disabled={this.state.isClicked}>
                      <Text style={styles.basicButtonLabel}> UNGET </Text>
                    </TouchableOpacity>
                  </View>
                </View>
              </View>
            </View>
            <View style={{ marginTop: 40 }}>
              <View style={styles.viewdividedtwo}>
                <View style={styles.halfItem3}>
                  <View style={styles.basicButton}>
                    <TouchableOpacity
                      style={styles.basicButtonOpacity}
                      onPress={() => this.putMessageWithOption().then(() => this.setState({ isClicked: false }))}
                      underlayColor="#fff"
                      disabled={this.state.isClicked}>
                      <Text style={styles.basicButtonLabel}> PUT OPTION </Text>
                    </TouchableOpacity>
                  </View>
                </View>
                <View style={styles.halfItem3}>
                  <View style={styles.basicButton}>
                    <TouchableOpacity
                      style={styles.basicButtonOpacity}
                      onPress={() => this.getMessageWithOption().then(() => this.setState({ isClicked: false }))}
                      underlayColor="#fff"
                      disabled={this.state.isClicked}>
                      <Text style={styles.basicButtonLabel}> GET OPTION </Text>
                    </TouchableOpacity>
                  </View>
                </View>
              </View>
            </View>
          </View>
        }

      </View>

    );
  }
}
