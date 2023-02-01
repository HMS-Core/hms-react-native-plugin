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

import React, { Component } from "react";
import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  TextInput,
  ToastAndroid,
} from "react-native";

import {
  HmsPushEvent,
  RNRemoteMessage,
  HmsPushMessaging,
  HmsPushInstanceId,
  HmsLocalNotification,
  HmsPushOpenDevice,
  RemoteMessageBuilder,
  HmsPushProfile,
} from "@hmscore/react-native-hms-push";

import { styles } from "./styles";

export default class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      log: "",
      topic: "",
      subjectId: "<project_id>",
    };

    this.componentDidMount = this.componentDidMount.bind(this);
  }

  componentDidMount() {
    if(this.state.subjectId === "<project_id>") {
      alert("You have not added your subjectId to the demo. This will cause some functions not to work.");
    }

    this.onRemoteMessageReceivedListener = HmsPushEvent.onRemoteMessageReceived(
      (result) => {
        const RNRemoteMessageObj = new RNRemoteMessage(result.msg);
        HmsLocalNotification.localNotification({
          [HmsLocalNotification.Attr.title]: "DataMessage Received",
          [HmsLocalNotification.Attr
            .message]: RNRemoteMessageObj.getDataOfMap(),
        });
        this.log("onRemoteMessageReceived", result);
      }
    );

    this.onTokenReceivedListener = HmsPushEvent.onTokenReceived((result) => {
      this.log("onTokenReceived", result);
    });

    this.onTokenErrorListener = HmsPushEvent.onTokenError((result) => {
      this.log("onTokenError", result);
    });

    this.onMultiSenderTokenReceivedListener = HmsPushEvent.onMultiSenderTokenReceived(
      (result) => {
        this.log("onMultiSenderTokenReceived", result);
      }
    );

    this.onMultiSenderTokenErrorListener = HmsPushEvent.onMultiSenderTokenError(
      (result) => {
        this.log("onMultiSenderTokenError", result);
      }
    );

    this.onPushMessageSentListener = HmsPushEvent.onPushMessageSent(
      (result) => {
        this.log("onPushMessageSent", result);
      }
    );

    this.onMessageSentErrorListener = HmsPushEvent.onPushMessageSentError(
      (result) => {
        this.log("onMessageSentError", result);
      }
    );

    this.onMessageSentDeliveredListener = HmsPushEvent.onPushMessageSentDelivered(
      (result) => {
        this.log("onMessageSentDelivered", result);
      }
    );

    this.onLocalNotificationActionListener = HmsPushEvent.onLocalNotificationAction(
      (result) => {
        this.log("onLocalNotificationAction", result);

        var notification = JSON.parse(result.dataJSON);
        if (notification.action === "Yes") {
          HmsLocalNotification.cancelNotificationsWithId([notification.id]);
        }
        this.log("onLocalNotificationAction-Clicked", notification.action);
      }
    );

    this.onNotificationOpenedAppListener = HmsPushEvent.onNotificationOpenedApp(
      (result) => {
        this.log("onNotificationOpenedApp", result);
      }
    );
  }

  componentWillUnmount() {
    this.onRemoteMessageReceivedListener.remove();
    this.onTokenReceivedListener.remove();
    this.onTokenErrorListener.remove();
    this.onMultiSenderTokenReceivedListener.remove();
    this.onMultiSenderTokenErrorListener.remove();
    this.onPushMessageSentListener.remove();
    this.onMessageSentErrorListener.remove();
    this.onMessageSentDeliveredListener.remove();
    this.onLocalNotificationActionListener.remove();
    this.onNotificationOpenedAppListener.remove();
  }

  log(tag, msg) {
    this.setState(
      {
        log: `[${tag}]: ${JSON.stringify(msg, "\n", 4)} \n ${this.state.log}`,
      },
      this.toast(JSON.stringify(msg, "\n", 4))
    );
  }

  toast = (msg) => {
    ToastAndroid.show(msg, ToastAndroid.SHORT);
  };

  clearLog() {
    this.setState({
      log: "",
    });
  }

  onChangeTopic(inputData) {
    this.setState({
      topic: inputData,
    });
  }

  turnOnPush() {
    HmsPushMessaging.turnOnPush()
      .then((result) => {
        this.log("turnOnPush", result);
      })
      .catch((err) => {
        alert("[turnOnPush] Error/Exception: " + JSON.stringify(err));
      });
  }

  turnOffPush() {
    HmsPushMessaging.turnOffPush()
      .then((result) => {
        this.log("turnOffPush", result);
      })
      .catch((err) => {
        alert("[turnOffPush] Error/Exception: " + JSON.stringify(err));
      });
  }

  getID() {
    HmsPushInstanceId.getId()
      .then((result) => {
        this.log("getId", result);
      })
      .catch((err) => {
        alert("[getID] Error/Exception: " + JSON.stringify(err));
      });
  }

  getAAID() {
    HmsPushInstanceId.getAAID()
      .then((result) => {
        this.log("getAAID", result);
      })
      .catch((err) => {
        alert("[getAAID] Error/Exception: " + JSON.stringify(err));
      });
  }
  getOdid() {
    HmsPushOpenDevice.getOdid()
      .then((result) => {
        this.log("getOdid", result);
      })
      .catch((err) => {
        alert("[getOdid] Error/Exception: " + JSON.stringify(err));
      });
  }

  getToken() {
    HmsPushInstanceId.getToken("")
      .then((result) => {
        this.log("getToken", result);
      })
      .catch((err) => {
        alert("[getToken] Error/Exception: " + JSON.stringify(err));
      });
  }

  getTokenWithSubjectId() {
    HmsPushInstanceId.getTokenWithSubjectId(this.state.subjectId)
      .then((result) => {
        this.log("getTokenWithSubjectId", result);
      })
      .catch((err) => {
        alert(
          "[getTokenWithSubjectId] Error/Exception: " + JSON.stringify(err)
        );
      });
  }

  getCreationTime() {
    HmsPushInstanceId.getCreationTime()
      .then((result) => {
        this.log("getCreationTime", result);
      })
      .catch((err) => {
        alert("[getCreationTime] Error/Exception: " + JSON.stringify(err));
      });
  }

  deleteAAID() {
    HmsPushInstanceId.deleteAAID()
      .then((result) => {
        this.log("deleteAAID", result);
      })
      .catch((err) => {
        alert("[deleteAAID] Error/Exception: " + JSON.stringify(err));
      });
  }

  deleteToken() {
    HmsPushInstanceId.deleteToken("")
      .then((result) => {
        this.log("deleteToken", result);
      })
      .catch((err) => {
        alert("[deleteToken] Error/Exception: " + JSON.stringify(err));
      });
  }

  deleteTokenWithSubjectId() {
    HmsPushInstanceId.deleteTokenWithSubjectId(this.state.subjectId)
      .then((result) => {
        this.log("deleteTokenWithSubjectId", result);
      })
      .catch((err) => {
        alert(
          "[deleteTokenWithSubjectId] Error/Exception: " + JSON.stringify(err)
        );
      });
  }

  subscribe() {
    HmsPushMessaging.subscribe(this.state.topic)
      .then((result) => {
        this.log("subscribe", result);
      })
      .catch((err) => {
        alert("[subscribe] Error/Exception: " + JSON.stringify(err));
      });
  }

  unsubscribe() {
    HmsPushMessaging.unsubscribe(this.state.topic)
      .then((result) => {
        this.log("unsubscribe", result);
      })
      .catch((err) => {
        alert("[unsubscribe] Error/Exception: " + JSON.stringify(err));
      });
  }

  sendRemoteMessage() {
    HmsPushMessaging.sendRemoteMessage({
      [RemoteMessageBuilder.TO]: "",
      //[RemoteMessageBuilder.MESSAGE_ID]: '', // Auto generated
      [RemoteMessageBuilder.MESSAGE_TYPE]: "hms",
      [RemoteMessageBuilder.COLLAPSE_KEY]: "-1",
      [RemoteMessageBuilder.TTL]: 120,
      [RemoteMessageBuilder.RECEIPT_MODE]: 1,
      [RemoteMessageBuilder.SEND_MODE]: 1,
      [RemoteMessageBuilder.DATA]: { key1: "test", message: "huawei-test" },
    })
      .then((result) => {
        this.log("sendRemoteMessage", result);
      })
      .catch((err) => {
        alert("[sendRemoteMessage] Error/Exception: " + JSON.stringify(err));
      });
  }

  isAutoInitEnabled() {
    HmsPushMessaging.isAutoInitEnabled()
      .then((result) => {
        this.log("isAutoInitEnabled", result);
      })
      .catch((err) => {
        alert("[isAutoInitEnabled] Error/Exception: " + JSON.stringify(err));
      });
  }
  setAutoInitEnabled(value) {
    HmsPushMessaging.setAutoInitEnabled(value)
      .then((result) => {
        this.log("setAutoInitEnabled", result);
      })
      .catch((err) => {
        alert("[setAutoInitEnabled] Error/Exception: " + JSON.stringify(err));
      });
  }

  getInitialNotification() {
    HmsPushMessaging.getInitialNotification()
      .then((result) => {
        this.log("getInitialNotification", result);
      })
      .catch((err) => {
        alert(
          "[getInitialNotification] Error/Exception: " + JSON.stringify(err)
        );
      });
  }

  isSupportProfile() {
    HmsPushProfile.isSupportProfile()
      .then((result) => {
        this.log("isSupportProfile", result);
      })
      .catch((err) => {
        alert("[isSupportProfile] Error/Exception: " + JSON.stringify(err));
      });
  }

  addProfile() {
    HmsPushProfile.addProfile(HmsPushProfile.Type.HUAWEI_PROFILE, "profileId")
      .then((result) => {
        this.log("addProfile", result);
      })
      .catch((err) => {
        alert("[addProfile] Error/Exception: " + JSON.stringify(err));
      });
  }

  addProfileWithSubjectId() {
    HmsPushProfile.addProfileWithSubjectId(
      "<subject_Id>",
      HmsPushProfile.Type.HUAWEI_PROFILE,
      "<profileId>"
    )
      .then((result) => {
        this.log("addProfileWithSubjectId", result);
      })
      .catch((err) => {
        alert(
          "[addProfileWithSubjectId] Error/Exception: " + JSON.stringify(err)
        );
      });
  }

  deleteProfile() {
    HmsPushProfile.deleteProfile("<profile_Id>")
      .then((result) => {
        this.log("deleteProfile", result);
      })
      .catch((err) => {
        alert("[deleteProfile] Error/Exception: " + JSON.stringify(err));
      });
  }

  deleteProfileWithSubjectId() {
    HmsPushProfile.deleteProfileWithSubjectId("<subject_Id>", "<profile_Id>")
      .then((result) => {
        this.log("deleteProfileWithSubjectId", result);
      })
      .catch((err) => {
        alert(
          "[deleteProfileWithSubjectId] Error/Exception: " + JSON.stringify(err)
        );
      });
  }

  render() {
    return (
      <ScrollView>
        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.secondaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.props.navigation.navigate("LocalNotification")}
          >
            <Text style={styles.buttonText}>Local Notification</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.turnOffPush();
            }}
          >
            <Text style={styles.buttonText}>TurnOffPush</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.turnOnPush();
            }}
          >
            <Text style={styles.buttonText}>TurnOnPush</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getID();
            }}
          >
            <Text style={styles.buttonText}>Get ID</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getAAID();
            }}
          >
            <Text style={styles.buttonText}>Get AAID</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getOdid();
            }}
          >
            <Text style={styles.buttonText}>Get Odid</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getToken();
            }}
          >
            <Text style={styles.buttonText}>Get Token</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getCreationTime();
            }}
          >
            <Text style={styles.buttonText}>Get CreationTime</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.deleteAAID();
            }}
          >
            <Text style={styles.buttonText}>Delete AAID</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.deleteToken();
            }}
          >
            <Text style={styles.buttonText}>Delete Token</Text>
          </TouchableOpacity>
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.primaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.getTokenWithSubjectId()}
          >
            <Text style={styles.buttonText}>Get Token With Subject ID</Text>
          </TouchableOpacity>
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.primaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.deleteTokenWithSubjectId()}
          >
            <Text style={styles.buttonText}>Delete Token With Subject ID</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TextInput
            value={this.state.topic}
            style={styles.inputTopic}
            placeholder="topic"
            onChangeText={(e) => this.onChangeTopic(e)}
          />
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.subscribe();
            }}
          >
            <Text style={styles.buttonText}>Subscribe</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.unsubscribe();
            }}
          >
            <Text style={styles.buttonText}>UnSubscribe</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.setAutoInitEnabled(false);
            }}
          >
            <Text style={styles.buttonText}>Disable AutoInit</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.setAutoInitEnabled(true);
            }}
          >
            <Text style={styles.buttonText}>Enable AutoInit</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.container}>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.isAutoInitEnabled();
            }}
          >
            <Text style={styles.buttonText}>Is AutoInit Enabled</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.buttonContainer, styles.primaryButton]}
            onPress={() => {
              this.getInitialNotification();
            }}
          >
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
            }}
          >
            <Text style={styles.buttonText}>sendRemoteMessage</Text>
          </TouchableOpacity>
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.primaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.isSupportProfile()}
          >
            <Text style={styles.buttonText}>isSupportProfile</Text>
          </TouchableOpacity>
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.primaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.addProfile()}
          >
            <Text style={styles.buttonText}>addProfile</Text>
          </TouchableOpacity>
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.primaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.addProfileWithSubjectId()}
          >
            <Text style={styles.buttonText}>addProfileWithSubjectId</Text>
          </TouchableOpacity>
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.primaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.deleteProfile()}
          >
            <Text style={styles.buttonText}>deleteProfile</Text>
          </TouchableOpacity>
        </View>

        <View style={[styles.container, styles.containerSlim]}>
          <TouchableOpacity
            style={[
              styles.buttonContainer,
              styles.primaryButton,
              styles.buttonContainerSlim,
            ]}
            onPress={() => this.deleteProfileWithSubjectId()}
          >
            <Text style={styles.buttonText}>deleteProfileWithSubjectId</Text>
          </TouchableOpacity>
        </View>

        <ScrollView style={styles.containerShowResultMsg}>
          <Text>{this.state.log}</Text>
        </ScrollView>
      </ScrollView>
    );
  }
}
