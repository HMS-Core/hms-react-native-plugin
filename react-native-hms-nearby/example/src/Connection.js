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

import React from "react";
import {
  Text,
  View,
  TextInput,
  TouchableOpacity,
  NativeEventEmitter,
  FlatList,
  ActivityIndicator,
  ToastAndroid,
  Switch,
  Alert,
} from "react-native";
import {
  HMSDiscovery,
  HMSTransfer,
  HMSNearbyApplication,
} from "@hmscore/react-native-hms-nearby";
import { styles } from "./Styles";
import ImagePicker from "react-native-image-picker";
import { stringToByteArray, byteArrayToString } from "./Converter.js";

const options = {
  title: "CHOOSE METHOD",
  storageOptions: {
    skipBackup: true,
    path: "images",
  },
};

export default class Connection extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      endpointId: "",
      messageData: [],
      serviceId: "com.huawei.hms.rn.nearby.demo",
      localEndpointName: "huawei.hms.core.rn",
      showLoading: false,
      currentMessage: "",
      isEnabled: false,
      startChatEnabled: true,
      connectionEstablish: false,
      imageUri: "",
      isFile: "",
      dataReceived: false,
    };
  }

  componentDidMount() {
    this.timer = null;

    this.eventEmitter = new NativeEventEmitter(HMSDiscovery);

    this.eventEmitter.addListener(
      HMSDiscovery.CONNECT_ON_ESTABLISH,
      (event) => {
        console.log(event);
        this.setState({ endpointId: event.endpointId }, () =>
          this.acceptConnect()
        );
      }
    );

    this.eventEmitter.addListener(HMSDiscovery.CONNECT_ON_RESULT, (event) => {
      console.log(event);
      ToastAndroid.showWithGravity(
        "Connection Successfull. Lets Chat...",
        ToastAndroid.SHORT,
        ToastAndroid.CENTER
      );
      this.setState({ showLoading: false, connectionEstablish: true }, () => {
        if (!this.state.isEnabled) {
          this.stopBroadCasting();
          clearTimeout(this.timer);
        }
      });
    });

    this.eventEmitter.addListener(
      HMSDiscovery.CONNECT_ON_DISCONNECTED,
      (event) => {
        console.log(event);
        ToastAndroid.showWithGravity(
          "Your Friend is Disconnected",
          ToastAndroid.SHORT,
          ToastAndroid.CENTER
        );
      }
    );

    this.eventEmitter.addListener(HMSDiscovery.SCAN_ON_FOUND, (event) => {
      console.log(event);
      ToastAndroid.showWithGravity(
        "Scanning : Friend Found",
        ToastAndroid.SHORT,
        ToastAndroid.CENTER
      );
      this.setState({ endpointId: event.endpointId }, () =>
        this.requestConnect().then(() => this.stopScan())
      );
    });

    this.eventEmitter.addListener(HMSDiscovery.SCAN_ON_LOST, (event) => {
      console.log(event);
      ToastAndroid.showWithGravity(
        "Scanning : Friend Lost",
        ToastAndroid.SHORT,
        ToastAndroid.CENTER
      );
    });

    this.eventEmitter.addListener(HMSDiscovery.DATA_ON_RECEIVED, (event) => {
      console.log(event);
      if (event.type == HMSTransfer.FILE) {
        var message = { sender: true, content: event.fileUri };
        this.state.messageData.push(message);
      } else if (event.type == HMSTransfer.BYTES) {
        var message = { sender: true, content: byteArrayToString(event.data) };
        this.state.messageData.push(message);
      } else if (event.type == HMSTransfer.STREAM) {
      }
      this.setState({ dataReceived: !this.state.dataReceived });
    });

    this.eventEmitter.addListener(
      HMSDiscovery.DATA_ON_TRANSFER_UPDATE,
      (event) => {
        console.log(event);
        if (this.state.isFile) {
          if (event.status == HMSTransfer.TRANSFER_STATE_SUCCESS) {
            var message = { sender: false, content: this.state.imageUri };
            this.state.messageData.push(message);
            this.setState({ imageUri: "", showLoading: false, isFile: false });
          } else if (event.status == HMSTransfer.TRANSFER_STATE_IN_PROGRESS) {
            ToastAndroid.showWithGravity(
              "Progress : " + event.transferredBytes + "/" + event.totalBytes,
              ToastAndroid.SHORT,
              ToastAndroid.CENTER
            );
          } else if (event.status == HMSTransfer.TRANSFER_STATE_FAILURE) {
            this.setState({ imageUri: "", showLoading: false, isFile: false });
            ToastAndroid.showWithGravity(
              "Tranfer Failed",
              ToastAndroid.SHORT,
              ToastAndroid.CENTER
            );
          } else if (event.status == HMSTransfer.TRANSFER_STATE_CANCEL) {
            this.setState({ imageUri: "", showLoading: false, isFile: false });
            ToastAndroid.showWithGravity(
              "Tranfer Cancelled",
              ToastAndroid.SHORT,
              ToastAndroid.CENTER
            );
          }
        }
      }
    );
  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSDiscovery.CONNECT_ON_ESTABLISH);
    this.eventEmitter.removeAllListeners(HMSDiscovery.CONNECT_ON_RESULT);
    this.eventEmitter.removeAllListeners(HMSDiscovery.CONNECT_ON_DISCONNECTED);
    this.eventEmitter.removeAllListeners(HMSDiscovery.SCAN_ON_FOUND);
    this.eventEmitter.removeAllListeners(HMSDiscovery.SCAN_ON_LOST);
    this.eventEmitter.removeAllListeners(HMSDiscovery.DATA_ON_RECEIVED);
    this.eventEmitter.removeAllListeners(HMSDiscovery.DATA_ON_TRANSFER_UPDATE);

    this.manageStopAndDisconnect(false);

    clearTimeout(this.timer);
  }

  manageStopAndDisconnect = (isTryAgain) => {
    if (this.state.isEnabled) {
      if (isTryAgain) {
        this.setState({ connectionEstablish: false, showLoading: true }, () => {
          this.stopScan()
            .then(() => this.disconnectAll())
            .then(() => this.scanOrBroadCast(true));
        });
      } else {
        this.stopScan().then(() => this.disconnectAll());
      }
    } else {
      if (isTryAgain) {
        this.setState({ connectionEstablish: false, showLoading: true }, () => {
          this.stopBroadCasting()
            .then(() => this.disconnectAll())
            .then(() => this.scanOrBroadCast(true));
        });
      } else {
        this.stopBroadCasting().then(() => this.disconnectAll());
      }
    }
  };

  tryAgainAlert = (errorMessage) => {
    Alert.alert(
      "Try Again",
      errorMessage + ", would you like to try again?",
      [
        {
          text: "NO",
          onPress: () => this.props.navigation.navigate("StartPage"),
          style: "cancel",
        },
        {
          text: "YES",
          onPress: () => this.manageStopAndDisconnect(this.state.isEnabled),
        },
      ],
      { cancelable: false }
    );
  };

  showImagePicker() {
    ImagePicker.showImagePicker(options, (response) => {
      if (response.didCancel) {
        console.log("User cancelled image picker");
      } else if (response.error) {
        console.log("ImagePicker Error: ", response.error);
      } else {
        this.setState(
          {
            imageUri: response.uri,
          },
          () => this.transferFile()
        );
      }
    });
  }

  scanOrBroadCast = (isEnabled) => {
    this.timer = setTimeout(() => {
      if (!this.state.connectionEstablish) {
        this.tryAgainAlert("Connection did not establish");
      }
    }, 20000);

    if (isEnabled) {
      this.startScan().then(() =>
        this.setState({ startChatEnabled: false, showLoading: true })
      );
    } else {
      this.startBroadcasting().then(() =>
        this.setState({ startChatEnabled: false, showLoading: true })
      );
    }
  };

  renderMessage = ({ item, index }) => {
    if (!item.sender) {
      return (
        <View style={styles.sendedMessageView}>
          <Text style={styles.sendedMessageContent}>{item.content}</Text>
        </View>
      );
    } else {
      return (
        <View style={styles.receivedMessageView}>
          <Text style={styles.receivedMessageContent}>{item.content}</Text>
        </View>
      );
    }
  };

  toggleSwitch = () => {
    this.setState({
      isEnabled: !this.state.isEnabled,
    });
  };

  async startBroadcasting() {
    try {
      var result = await HMSDiscovery.startBroadcasting(
        this.state.localEndpointName,
        this.state.serviceId,
        HMSDiscovery.STAR
      );
      console.log(result);
      this.handleResult(result, "Broadcasting For Friend ...");
    } catch (e) {
      this.logError(e);
    }
  }

  async startScan() {
    try {
      var result = await HMSDiscovery.startScan(
        this.state.serviceId,
        HMSDiscovery.STAR
      );
      console.log(result);
      this.handleResult(result, "Scanning for Friend ...");
    } catch (e) {
      this.logError(e);
    }
  }

  async acceptConnect() {
    try {
      var result = await HMSDiscovery.acceptConnect(this.state.endpointId);
      console.log(result);
      this.handleResult(result, "Accepted Connection...");
    } catch (e) {
      this.logError(e);
    }
  }

  async disconnect() {
    try {
      var result = await HMSDiscovery.disconnect(this.state.endpointId);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async rejectConnect() {
    try {
      var result = await HMSDiscovery.rejectConnect(this.state.endpointId);
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async requestConnect() {
    try {
      var result = await HMSDiscovery.requestConnectEx(
        this.state.localEndpointName,
        this.state.endpointId,
        { policy: HMSDiscovery.CHANNEL_AUTO }
      );
      console.log(result);
      this.handleResult(result, "Requested For Connection...");
    } catch (e) {
      this.logError(e);
    }
  }

  async stopBroadCasting() {
    try {
      var result = await HMSDiscovery.stopBroadCasting();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async disconnectAll() {
    try {
      var result = await HMSDiscovery.disconnectAll();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async stopScan() {
    try {
      var result = await HMSDiscovery.stopScan();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  async transferBytes() {
    try {
      this.setState({ showLoading: true });
      var result = await HMSTransfer.transferBytes(
        stringToByteArray(this.state.currentMessage),
        [this.state.endpointId]
      );
      console.log(result);
      if (result.status == HMSNearbyApplication.SUCCESS) {
        ToastAndroid.showWithGravity(
          "Message Sent",
          ToastAndroid.SHORT,
          ToastAndroid.CENTER
        );
        var message = { sender: false, content: this.state.currentMessage };
        this.state.messageData.push(message);
        this.setState({ showLoading: false, currentMessage: "" });
      } else {
        this.tryAgainAlert(result.message);
      }
    } catch (e) {
      console.log(e);
      ToastAndroid.showWithGravity(e, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
  }

  async transferFile() {
    try {
      this.setState({ showLoading: true, isFile: true });
      var result = await HMSTransfer.transferFile(this.state.imageUri, [
        this.state.endpointId,
      ]);
      console.log(result);
      if (result.status == HMSNearbyApplication.SUCCESS) {
        ToastAndroid.showWithGravity(
          "Image Transfer Start",
          ToastAndroid.SHORT,
          ToastAndroid.CENTER
        );
      } else {
        this.setState({ isFile: false, showLoading: false });
        this.tryAgainAlert(result.message);
      }
    } catch (e) {
      console.log(e);
    }
  }

  async transferStream() {
    try {
      var result = await HMSTransfer.transferStream(
        this.endpointId,
        this.getEndpointIds()
      );
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  }

  handleResult(result, message) {
    if (result.status == HMSNearbyApplication.SUCCESS) {
      ToastAndroid.showWithGravity(
        message,
        ToastAndroid.SHORT,
        ToastAndroid.CENTER
      );
    } else {
      this.tryAgainAlert(result.message);
    }
  }

  logError(e) {
    console.log(e);
    ToastAndroid.showWithGravity(e, ToastAndroid.SHORT, ToastAndroid.CENTER);
  }

  render() {
    return (
      <View style={styles.baseView}>
        <View style={styles.toolbar}>
          {this.state.startChatEnabled ? (
            <View style={styles.viewdividedtwo}>
              <View style={styles.halfItem4}>
                <Text style={styles.titleToolbar}>
                  {this.state.isEnabled ? "Discover" : "Broadcast"}
                </Text>
              </View>
              <View style={styles.halfItem4}>
                <Switch
                  style={{ alignSelf: "center" }}
                  trackColor={{ false: "#ffffff", true: "#50AF52" }}
                  thumbColor={this.state.isEnabled ? "#ffffff" : "#ffffff"}
                  onValueChange={this.toggleSwitch.bind(this)}
                  value={this.state.isEnabled}
                />
              </View>
              <View style={styles.halfItem4}>
                <TouchableOpacity
                  style={styles.basicButtonOpacity}
                  onPress={() => {
                    this.scanOrBroadCast(this.state.isEnabled);
                  }}
                  underlayColor="#fff"
                >
                  <Text style={styles.basicButtonLabel}> CHAT </Text>
                </TouchableOpacity>
              </View>
            </View>
          ) : (
            <View>
              {this.state.showLoading ? (
                <ActivityIndicator size="large" />
              ) : (
                <Text style={styles.titleToolbar}>Welcome To Chat</Text>
              )}
            </View>
          )}
        </View>

        <View style={styles.baseView}>
          <FlatList
            inverted={false}
            style={styles.baseView}
            data={this.state.messageData}
            renderItem={this.renderMessage}
            keyExtractor={(item, index) => index.toString()}
            extraData={this.state.dataReceived}
            contentContainerStyle={{ paddingTop: 10, paddingBottom: 10 }}
          />

          <View style={styles.connectionInput}>
            <TextInput
              style={styles.baseView}
              placeholder="Type your message ..."
              onChangeText={(value) => {
                this.setState({ currentMessage: value });
              }}
              value={this.state.currentMessage}
              editable={
                !(this.state.showLoading || this.state.startChatEnabled)
              }
            />

            <TouchableOpacity
              onPress={this.showImagePicker.bind(this)}
              disabled={this.state.showLoading || this.state.startChatEnabled}
            >
              <View
                style={{
                  backgroundColor: "purple",
                  padding: 8,
                  margin: 4,
                  borderRadius: 5,
                }}
              >
                <Text style={{ color: "white" }}>Pick File</Text>
              </View>
            </TouchableOpacity>

            <TouchableOpacity
              onPress={() => {
                this.state.currentMessage != "" ? this.transferBytes() : null;
              }}
              disabled={this.state.showLoading || this.state.startChatEnabled}
            >
              <View style={{ padding: 8, margin: 4, borderRadius: 5 }}>
                <Text style={{ color: "purple" }}>Send</Text>
              </View>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    );
  }
}
