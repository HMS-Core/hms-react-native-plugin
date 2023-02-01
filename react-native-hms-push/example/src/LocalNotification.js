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
    TouchableOpacity,
    Text,
    ScrollView,
    TextInput,
    ToastAndroid,
} from "react-native";

import { HmsLocalNotification } from "@hmscore/react-native-hms-push";

import { styles } from "./styles";

const defaultNotification = {
    [HmsLocalNotification.Attr.title]: "Notification Title",
    [HmsLocalNotification.Attr.message]: "Notification Message", // (required)
    [HmsLocalNotification.Attr.ticker]: "Optional Ticker",
    [HmsLocalNotification.Attr.showWhen]: true,
    // [HmsLocalNotification.Attr.largeIconUrl]: 'https://developer.huawei.com/Enexport/sites/default/images/en/Develop/hms/push/push2-tuidedao.png', //
    [HmsLocalNotification.Attr.largeIcon]: "ic_launcher",
    [HmsLocalNotification.Attr.smallIcon]: "ic_notification",
    [HmsLocalNotification.Attr.bigText]: "This is a bigText",
    [HmsLocalNotification.Attr.subText]: "This is a subText",
    [HmsLocalNotification.Attr.color]: "white",
    [HmsLocalNotification.Attr.vibrate]: false,
    [HmsLocalNotification.Attr.vibrateDuration]: 1000,
    [HmsLocalNotification.Attr.tag]: "hms_tag",
    [HmsLocalNotification.Attr.groupSummary]: false,
    [HmsLocalNotification.Attr.ongoing]: false,
    [HmsLocalNotification.Attr.importance]: HmsLocalNotification.Importance.max,
    [HmsLocalNotification.Attr.dontNotifyInForeground]: false,
    [HmsLocalNotification.Attr.autoCancel]: false, // for Custom Actions, it should be false
    [HmsLocalNotification.Attr.actions]: '["Yes", "No"]',
    [HmsLocalNotification.Attr.invokeApp]: false,
    // [HmsLocalNotification.Attr.channelId]: 'huawei-hms-rn-push-channel-id', // Please read the documentation before using this param
    [HmsLocalNotification.Attr.data]: { data: "data" },
};

const CustomTextInput = ({ type, children }) => {
    return (
        <View style={styles.container}>
            <Text
                style={[
                    styles.buttonText,
                    styles.width30,
                    styles.paddingTop20,
                ]}
            >
                {type} :
            </Text>
            {children}
        </View>
    );
};

export default class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            log: "",
            title: "HMS Push",
            message: "This is Local Notification",
            bigText: "This is a bigText",
            subText: "This is a subText",
            tag: null,
        };
    }

    toast = (msg) => {
        ToastAndroid.show(msg, ToastAndroid.SHORT);
    };

    log(tag, msg) {
        this.setState(
            {
                log: `[${tag}]: ${JSON.stringify(msg, "\n", 4)} \n ${this.state.log}`,
            },
            this.toast(JSON.stringify(msg, "\n", 4))
        );
    }

    changeNotificationValue(key, data) {
        this.setState({
            [key]: data,
        });
    }

    localNotificationScheduled() {
        HmsLocalNotification.localNotificationSchedule({
            ...defaultNotification,
            [HmsLocalNotification.Attr.title]: this.state.title,
            [HmsLocalNotification.Attr.message]: this.state.message,
            [HmsLocalNotification.Attr.bigText]: this.state.bigText,
            [HmsLocalNotification.Attr.subText]: this.state.subText,
            [HmsLocalNotification.Attr.tag]: this.state.tag,
            [HmsLocalNotification.Attr.fireDate]: new Date(
                Date.now() + 60 * 1000
            ).getTime(), // in 1 min
            [HmsLocalNotification.Attr.allowWhileIdle]: true,
        })
            .then((result) => {
                this.log("LocalNotification Scheduled", result);
            })
            .catch((err) => {
                alert(
                    "[LocalNotification Scheduled] Error/Exception: " +
                    JSON.stringify(err)
                );
            });
    }

    localNotification() {
        HmsLocalNotification.localNotification({
            ...defaultNotification,
            [HmsLocalNotification.Attr.title]: this.state.title,
            [HmsLocalNotification.Attr.message]: this.state.message,
            [HmsLocalNotification.Attr.bigText]: this.state.bigText,
            [HmsLocalNotification.Attr.subText]: this.state.subText,
            [HmsLocalNotification.Attr.tag]: this.state.tag,
        })
            .then((result) => {
                this.log("LocalNotification Default", result);
            })
            .catch((err) => {
                alert(
                    "[LocalNotification Default] Error/Exception: " +
                    JSON.stringify(err)
                );
            });
    }

    localNotificationVibrate() {
        HmsLocalNotification.localNotification({
            ...defaultNotification,
            [HmsLocalNotification.Attr.title]: this.state.title,
            [HmsLocalNotification.Attr.message]: this.state.message,
            [HmsLocalNotification.Attr.bigText]: this.state.bigText,
            [HmsLocalNotification.Attr.subText]: this.state.subText,
            [HmsLocalNotification.Attr.tag]: this.state.tag,
            [HmsLocalNotification.Attr.vibrate]: true,
            [HmsLocalNotification.Attr.vibrateDuration]: 5000,
        })
            .then((result) => {
                this.log("LocalNotification Vibrate", result);
            })
            .catch((err) => {
                alert(
                    "[LocalNotification Vibrate] Error/Exception: " +
                    JSON.stringify(err)
                );
            });
    }

    localNotificationRepeat() {
        HmsLocalNotification.localNotification({
            ...defaultNotification,
            [HmsLocalNotification.Attr.title]: this.state.title,
            [HmsLocalNotification.Attr.message]: this.state.message,
            [HmsLocalNotification.Attr.bigText]: this.state.bigText,
            [HmsLocalNotification.Attr.subText]: this.state.subText,
            [HmsLocalNotification.Attr.tag]: this.state.tag,
            [HmsLocalNotification.Attr.repeatType]:
                HmsLocalNotification.RepeatType.minute,
        })
            .then((result) => {
                this.log("LocalNotification Repeat", result);
            })
            .catch((err) => {
                alert(
                    "[LocalNotification Repeat] Error/Exception: " +
                    JSON.stringify(err)
                );
            });
    }
    localNotificationSound() {
        HmsLocalNotification.localNotification({
            ...defaultNotification,
            [HmsLocalNotification.Attr.title]: this.state.title,
            [HmsLocalNotification.Attr.message]: this.state.message,
            [HmsLocalNotification.Attr.bigText]: this.state.bigText,
            [HmsLocalNotification.Attr.subText]: this.state.subText,
            [HmsLocalNotification.Attr.tag]: this.state.tag,
            [HmsLocalNotification.Attr.playSound]: true,
            [HmsLocalNotification.Attr.soundName]: "huawei_bounce.mp3",
        })
            .then((result) => {
                this.log("LocalNotification Sound", result);
            })
            .catch((err) => {
                alert(
                    "[LocalNotification Sound] Error/Exception: " +
                    JSON.stringify(err)
                );
            });
    }
    localNotificationPriority() {
        HmsLocalNotification.localNotification({
            ...defaultNotification,
            [HmsLocalNotification.Attr.title]: this.state.title,
            [HmsLocalNotification.Attr.message]: this.state.message,
            [HmsLocalNotification.Attr.bigText]: this.state.bigText,
            [HmsLocalNotification.Attr.subText]: this.state.subText,
            [HmsLocalNotification.Attr.tag]: this.state.tag,
            [HmsLocalNotification.Attr.priority]:
                HmsLocalNotification.Priority.max,
        })
            .then((result) => {
                this.log("LocalNotification Priority", result);
            })
            .catch((err) => {
                alert(
                    "[LocalNotification Priority] Error/Exception: " +
                    JSON.stringify(err)
                );
            });
    }

    localNotificationOngoing() {
        HmsLocalNotification.localNotification({
            ...defaultNotification,
            [HmsLocalNotification.Attr.title]: this.state.title,
            [HmsLocalNotification.Attr.message]: this.state.message,
            [HmsLocalNotification.Attr.bigText]: this.state.bigText,
            [HmsLocalNotification.Attr.subText]: this.state.subText,
            [HmsLocalNotification.Attr.tag]: this.state.tag,
            [HmsLocalNotification.Attr.ongoing]: true,
        })
            .then((result) => {
                this.log("LocalNotification Ongoing", result);
            })
            .catch((err) => {
                alert(
                    "[LocalNotification Ongoing] Error/Exception: " +
                    JSON.stringify(err)
                );
            });
    }
    localNotificationBigImage() {
        HmsLocalNotification.localNotification({
            ...defaultNotification,
            [HmsLocalNotification.Attr.title]: this.state.title,
            [HmsLocalNotification.Attr.message]: this.state.message,
            [HmsLocalNotification.Attr.bigText]: this.state.bigText,
            [HmsLocalNotification.Attr.subText]: this.state.subText,
            [HmsLocalNotification.Attr.tag]: this.state.tag,
            [HmsLocalNotification.Attr.bigPictureUrl]:
                "https://www-file.huawei.com/-/media/corp/home/image/logo_400x200.png",
        })
            .then((result) => {
                this.log("LocalNotification BigImage", result);
            })
            .catch((err) => {
                alert(
                    "[LocalNotification BigImage] Error/Exception: " +
                    JSON.stringify(err)
                );
            });
    }

    render() {
        return (
            <ScrollView>
                <View style={styles.container}>
                    <Text
                        style={[
                            styles.buttonText,
                            styles.width30,
                            styles.paddingTop20,
                        ]}
                    >
                        Title :
                    </Text>
                    <TextInput
                        value={this.state.title}
                        style={[styles.inputTopic, styles.width35]}
                        placeholder="title"
                        onChangeText={(e) =>
                            this.changeNotificationValue("title", e)
                        }
                    />
                    <TextInput
                        value={this.state.tag}
                        style={[styles.inputTopic, styles.width35]}
                        placeholder="tag"
                        onChangeText={(e) =>
                            this.changeNotificationValue("tag", e)
                        }
                    />
                </View>

                <CustomTextInput type={"Message"}>
                    <TextInput
                        value={this.state.message}
                        style={[styles.inputTopic, styles.width70]}
                        placeholder="message"
                        onChangeText={(e) =>
                            this.changeNotificationValue("message", e)
                        }
                    />
                </CustomTextInput>

                <CustomTextInput type={"BigText"}>
                    <TextInput
                        value={this.state.bigText}
                        style={[
                            styles.inputTopic,
                            styles.width70,
                            styles.fontSizeSmall,
                        ]}
                        placeholder="bigText"
                        onChangeText={(e) =>
                            this.changeNotificationValue("bigText", e)
                        }
                    />
                </CustomTextInput>

                <CustomTextInput type={"SubText"}>
                    <TextInput
                        value={this.state.subText}
                        style={[
                            styles.inputTopic,
                            styles.width70,
                            styles.fontSizeSmall,
                        ]}
                        placeholder="subText"
                        onChangeText={(e) =>
                            this.changeNotificationValue("subText", e)
                        }
                    />
                </CustomTextInput>

                <View style={[styles.container, styles.containerSlim]}>
                    <TouchableOpacity
                        style={[
                            styles.buttonContainer,
                            styles.secondaryButton,
                            styles.buttonContainerSlim,
                        ]}
                        onPress={() => this.localNotification()}
                    >
                        <Text style={styles.buttonText}>
                            Local Notification (Default)
                        </Text>
                    </TouchableOpacity>
                </View>

                <View style={[styles.container, styles.containerSlim]}>
                    <TouchableOpacity
                        style={[
                            styles.buttonContainer,
                            styles.secondaryButton,
                            styles.buttonContainerSlim,
                        ]}
                        onPress={() => this.localNotificationOngoing()}
                    >
                        <Text style={styles.buttonText}>+ Ongoing</Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={[
                            styles.buttonContainer,
                            styles.secondaryButton,
                            styles.buttonContainerSlim,
                        ]}
                        onPress={() => this.localNotificationSound()}
                    >
                        <Text style={styles.buttonText}>+ Sound</Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={[
                            styles.buttonContainer,
                            styles.secondaryButton,
                            styles.buttonContainerSlim,
                        ]}
                        onPress={() => this.localNotificationVibrate()}
                    >
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
                        onPress={() => this.localNotificationBigImage()}
                    >
                        <Text style={styles.buttonText}>+ BigImage</Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={[
                            styles.buttonContainer,
                            styles.secondaryButton,
                            styles.buttonContainerSlim,
                        ]}
                        onPress={() => this.localNotificationRepeat()}
                    >
                        <Text style={styles.buttonText}>+ Repeat</Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={[
                            styles.buttonContainer,
                            styles.secondaryButton,
                            styles.buttonContainerSlim,
                        ]}
                        onPress={() => this.localNotificationScheduled()}
                    >
                        <Text style={styles.buttonText}>+ Scheduled</Text>
                    </TouchableOpacity>
                </View>

                <View style={styles.container}>
                    <TouchableOpacity
                        style={[styles.buttonContainer, styles.primaryButton]}
                        onPress={() => {
                            HmsLocalNotification.cancelAllNotifications()
                                .then((result) => {
                                    this.log("cancelAllNotifications", result);
                                })
                                .catch((err) => {
                                    alert(
                                        "[cancelAllNotifications] Error/Exception: " +
                                        JSON.stringify(err)
                                    );
                                });
                        }}
                    >
                        <Text style={styles.buttonText}>
                            cancelAllNotifications
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={[styles.buttonContainer, styles.primaryButton]}
                        onPress={() => {
                            HmsLocalNotification.getNotifications()
                                .then((result) => {
                                    this.log("getNotifications", result);
                                })
                                .catch((err) => {
                                    alert(
                                        "[getNotifications] Error/Exception: " +
                                        JSON.stringify(err)
                                    );
                                });
                        }}
                    >
                        <Text style={styles.buttonText}>getNotifications</Text>
                    </TouchableOpacity>
                </View>
                <View style={styles.container}>
                    <TouchableOpacity
                        style={[styles.buttonContainer, styles.primaryButton]}
                        onPress={() => {
                            HmsLocalNotification.cancelScheduledNotifications()
                                .then((result) => {
                                    this.log(
                                        "cancelScheduledNotifications",
                                        result
                                    );
                                })
                                .catch((err) => {
                                    alert(
                                        "[cancelScheduledNotifications] Error/Exception: " +
                                        JSON.stringify(err)
                                    );
                                });
                        }}
                    >
                        <Text
                            style={[styles.buttonText, styles.buttonTextSmall]}
                        >
                            cancelScheduledNotifications
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={[styles.buttonContainer, styles.primaryButton]}
                        onPress={() => {
                            HmsLocalNotification.getScheduledNotifications()
                                .then((result) => {
                                    this.log(
                                        "getScheduledNotifications",
                                        result
                                    );
                                })
                                .catch((err) => {
                                    alert(
                                        "[getScheduledNotifications] Error/Exception: " +
                                        JSON.stringify(err)
                                    );
                                });
                        }}
                    >
                        <Text style={[styles.buttonText, styles.buttonTextSmallest]} > getScheduledLocalNotifications </Text>
                    </TouchableOpacity>
                </View>
                <View style={styles.container}>
                    <TouchableOpacity
                        style={[styles.buttonContainer, styles.primaryButton]}
                        onPress={() => {
                            HmsLocalNotification.cancelNotificationsWithTag(
                                "tag"
                            )
                                .then((result) => {
                                    this.log(
                                        "cancelNotificationsWithTag",
                                        result
                                    );
                                })
                                .catch((err) => {
                                    alert(
                                        "[cancelNotificationsWithTag] Error/Exception: " +
                                        JSON.stringify(err)
                                    );
                                });
                        }}
                    >
                        <Text style={[styles.buttonText, styles.buttonTextSmallest]} > cancelNotificationsWithTag(tag)  </Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={[styles.buttonContainer, styles.primaryButton]}
                        onPress={() => {
                            HmsLocalNotification.getChannels()
                                .then((result) => {
                                    this.log("getChannels", result);
                                })
                                .catch((err) => {
                                    alert(
                                        "[getChannels] Error/Exception: " +
                                        JSON.stringify(err)
                                    );
                                });
                        }}
                    >
                        <Text style={styles.buttonText}>getChannels</Text>
                    </TouchableOpacity>
                </View>
                <View style={styles.container}>
                    <TouchableOpacity
                        style={[styles.buttonContainer, styles.primaryButton]}
                        onPress={() => {
                            HmsLocalNotification.cancelNotifications()
                                .then((result) => {
                                    this.log("cancelNotifications", result);
                                })
                                .catch((err) => {
                                    alert(
                                        "[cancelNotifications] Error/Exception: " +
                                        JSON.stringify(err)
                                    );
                                });
                        }}
                    >
                        <Text style={styles.buttonText}>
                            cancelNotifications
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={[styles.buttonContainer, styles.primaryButton]}
                        onPress={() => {
                            HmsLocalNotification.deleteChannel(
                                "hms-channel-custom"
                            )
                                .then((result) => {
                                    this.log("deleteChannel", result);
                                })
                                .catch((err) => {
                                    alert(
                                        "[deleteChannel] Error/Exception: " +
                                        JSON.stringify(err)
                                    );
                                });
                        }}
                    >
                        <Text style={styles.buttonText}>deleteChannel</Text>
                    </TouchableOpacity>
                </View>
                <View style={styles.container}>
                    <TouchableOpacity
                        style={[styles.buttonContainer, styles.primaryButton]}
                        onPress={() => {
                            HmsLocalNotification.channelBlocked(
                                "huawei-hms-rn-push-channel-id"
                            )
                                .then((result) => {
                                    this.log("channelBlocked", result);
                                })
                                .catch((err) => {
                                    alert(
                                        "[channelBlocked] Error/Exception: " +
                                        JSON.stringify(err)
                                    );
                                });
                        }}
                    >
                        <Text style={styles.buttonText}>channelBlocked</Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={[styles.buttonContainer, styles.primaryButton]}
                        onPress={() => {
                            HmsLocalNotification.channelExists(
                                "huawei-hms-rn-push-channel-id"
                            )
                                .then((result) => {
                                    this.log("channelExists", result);
                                })
                                .catch((err) => {
                                    alert(
                                        "[channelExists] Error/Exception: " +
                                        JSON.stringify(err)
                                    );
                                });
                        }}
                    >
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
