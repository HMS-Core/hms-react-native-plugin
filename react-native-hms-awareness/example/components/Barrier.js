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
  StyleSheet,
  TouchableOpacity,
  Text,
  View,
  ActivityIndicator,
  ScrollView,
  ToastAndroid,
} from "react-native";

import { platinum, darkJungleGreen, green } from "../constants/Colors.js";
import {
  ScreenWidth,
  ScreenHeight,
  buttonWidth,
  buttonHeight,
  eventEmitter,
} from "../constants/Data.js";
import { HMSAwarenessBarrierModule } from "@hmscore/react-native-hms-awareness";
import UpdateBarrier from "./UpdateBarrier";

export default class App extends React.Component {
  constructor() {
    super();
    this.state = {
      showProgress: false,
      barrierList: [],
      receiverMessage: "",
    };
  }

  /**
   * map: Object
   * barriersReceiver fetches information when recorded barriers are triggered.
   */
  componentDidMount() {
    eventEmitter.addListener("barrierReceiver", (map) => {
      let status = `${map.presentStatusName}`;
      let eventType = `${map.barrierLabel}`;

      this.setState({
        receiverMessage: `${eventType} - ${status}`,
      });
      console.log(`Barrier Receiver: ${eventType} - ${status}`);
    });
  }

  changeStateProgress(isProgressShowing) {
    this.setState({ showProgress: isProgressShowing });
  }

  async setBackgroundNotification() {
    try {
      this.changeStateProgress(true);

      const notificationReq = {
        contentTitle: "Custom title",
        contentText: "Custom text",
        type: "mipmap",
        resourceName: "ic_launcher"
      }

      var res = await HMSAwarenessBarrierModule.setBackgroundNotification(notificationReq);
      this.changeStateProgress(false);
      alert(JSON.stringify(res));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }


  /**
   * @param  QueryBarrierReq : Object
   * Returns the registered barriers and their properties in the array.
   */
  async queryBarrier() {
    try {
      this.changeStateProgress(true);
      this.setState({ receiverMessage: "" });
      const QueryBarrierReq = [
        "headset connecting barrier",
        "headset disconnecting barrier",
        "light above barrier",
        "wifi keeping with bssid barrier",
        "wifi keeping barrier",
      ];
      var res = await HMSAwarenessBarrierModule.queryBarrier(QueryBarrierReq);
      this.changeStateProgress(false);
      alert(JSON.stringify(res));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * Returns all added barriers and their attributes.
   */
  async queryAllBarrier() {
    try {
      this.changeStateProgress(true);
      this.setState({ receiverMessage: "" });
      var BarrierQueryResponse = await HMSAwarenessBarrierModule.queryAllBarrier();
      this.changeStateProgress(false);
      alert(JSON.stringify(BarrierQueryResponse));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * @param DeleteBarrierReq : Object
   * This method removes barrier labels in the array.
   */
  async deleteBarrier() {
    try {
      this.changeStateProgress(true);
      this.setState({ receiverMessage: "" });
      const DeleteBarrierReq = [
        "headset keeping barrier",
        "wifi keeping barrier",
      ];
      var BarrierDeleteRes = await HMSAwarenessBarrierModule.deleteBarrier(
        DeleteBarrierReq
      );
      this.changeStateProgress(false);
      alert(JSON.stringify(BarrierDeleteRes));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * This method deletes all registered barriers.
   */
  async deleteAllBarrier() {
    try {
      this.changeStateProgress(true);
      this.setState({ receiverMessage: "" });
      var AllBarrierDeleteRes = await HMSAwarenessBarrierModule.deleteAllBarrier();
      this.changeStateProgress(false);
      alert(JSON.stringify(AllBarrierDeleteRes));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  /**
   * @param CombinationBarrierReq : Object
   * You can create a combination of barriers using "and", "or" and "not".
   * You can listen to different awareness features with a single barrier.
   */
  async combinationBarrier() {
    try {
      this.changeStateProgress(true);
      this.setState({ receiverMessage: "" });
      const barrierLabel = "Combination Barrier";

      //Ambient Light Barrier
      const lightReceiverAction = HMSAwarenessBarrierModule.AMBIENTLIGHT_ABOVE;
      const minLightIntensity = 1000.0;
      const lightBarrier = {
        barrierReceiverAction: lightReceiverAction,
        minLightIntensity: minLightIntensity,
        barrierEventType: HMSAwarenessBarrierModule.EVENT_AMBIENTLIGHT,
      };

      //Screen Barrier
      const barrierReceiverAction = HMSAwarenessBarrierModule.SCREEN_KEEPING;
      const screenStatus = HMSAwarenessBarrierModule.ScreenStatus_UNLOCK;
      const screenBarrier = {
        barrierReceiverAction: barrierReceiverAction,
        screenStatus: screenStatus,
        barrierEventType: HMSAwarenessBarrierModule.EVENT_SCREEN,
      };

      //Headset barrier
      const headsetReceiverAction =
        HMSAwarenessBarrierModule.EVENT_HEADSET_KEEPING;
      const headsetStatus = HMSAwarenessBarrierModule.HeadsetStatus_CONNECTED;
      const headsetBarrier = {
        barrierReceiverAction: headsetReceiverAction,
        headsetStatus: headsetStatus,
        barrierEventType: HMSAwarenessBarrierModule.EVENT_HEADSET,
      };

      const notObject = {
        type: HMSAwarenessBarrierModule.BARRIER_TYPE_NOT,
        child: screenBarrier,
      };

      const andChildren = [];
      andChildren.push(headsetBarrier);
      andChildren.push(notObject);

      const andObject = {
        type: HMSAwarenessBarrierModule.BARRIER_TYPE_AND,
        children: andChildren,
      };

      const orChildren = [];
      orChildren.push(lightBarrier);
      orChildren.push(andObject);

      const orObject = {
        type: HMSAwarenessBarrierModule.BARRIER_TYPE_OR,
        children: orChildren,
      };
      const CombinationBarrierReq = [];
      CombinationBarrierReq.push(orObject);

      var CombiantionBarrierRes = await HMSAwarenessBarrierModule.combinationBarrier(
        barrierLabel,
        CombinationBarrierReq
      );
      this.changeStateProgress(false);
      alert(JSON.stringify(CombiantionBarrierRes));
    } catch (e) {
      this.changeStateProgress(false);
      alert(JSON.stringify(e));
    }
  }

  render() {
    return (
      <View style={styles.barrierContainer}>
        {this.state.showProgress ? (
          <View style={styles.disableOverlayBarrier}>
            <ActivityIndicator
              size={"large"}
              color={green}
              style={styles.activityIndicatorBarrier}
            />
          </View>
        ) : null}

        <ScrollView nestedScrollEnabled={true} style={styles.scrollViewBarrier}>
          <View style={styles.innerContainer}>
            <Text style={styles.titleBarrier}>Combination Barrier</Text>

            <TouchableOpacity
              style={styles.barrierBtn}
              onPress={() => this.combinationBarrier()}
            >
              <Text style={styles.txt}>Combination Barrier</Text>
            </TouchableOpacity>

            <Text style={styles.titleBarrier}>Query Barrier</Text>

            <TouchableOpacity
              style={styles.barrierBtn}
              onPress={() => this.queryBarrier()}
            >
              <Text style={styles.txt}>Query Barrier</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.barrierBtn}
              onPress={() => this.queryAllBarrier()}
            >
              <Text style={styles.txt}>Query Barrier All</Text>
            </TouchableOpacity>

            <Text style={styles.titleBarrier}>Update Barrier</Text>

            <UpdateBarrier
              changeStateProgress={(state) => this.changeStateProgress(state)}
            />

            <Text style={styles.titleBarrier}>Delete Barrier</Text>

            <TouchableOpacity style={styles.barrierBtn}>
              <Text style={styles.txt}>Delete All Barrier</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.barrierBtn}
              onPress={() => this.deleteBarrier()}>
              <Text style={styles.txt}>Delete Barrier (Or Barriers)</Text>
            </TouchableOpacity>

            <View style={styles.receiverMeesageView}>
              <Text style={[styles.titleReceiverMessage, { color: "red" }]}>
                {" "}
                Receiver Message
              </Text>
              <Text style={styles.txt}>{this.state.receiverMessage}</Text>
            </View>
          </View>
        </ScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  barrierContainer: {
    flex: 1,
    backgroundColor: platinum,
    borderColor: darkJungleGreen,
    borderWidth: 1,
  },
  disableOverlayBarrier: {
    zIndex: 10,
    backgroundColor: "gray",
    opacity: 0.8,
    position: "absolute",
    width: ScreenWidth,
    height: ScreenHeight,
  },
  activityIndicatorBarrier: {
    top: ScreenHeight / 3,
    alignSelf: "center",
    position: "absolute",
    zIndex: 11,
  },
  scrollViewBarrier: {
    flex: 1,
  },
  innerContainer: {
    flex: 1,
    justifyContent: "center",
    paddingBottom: 50,
    alignSelf: "center",
  },
  titleBarrier: {
    color: green,
    fontSize: 18,
    fontStyle: "italic",
    fontWeight: "bold",
    marginTop: 20,
  },
  barrierBtn: {
    borderRadius: 10,
    marginLeft: 5,
    marginRight: 5,
    marginTop: 16,
    width: buttonWidth,
    height: buttonHeight,
    justifyContent: "center",
    alignSelf: "center",
    borderColor: darkJungleGreen,
    borderWidth: 1.4,
  },
  txt: {
    fontSize: 14.5,
    color: darkJungleGreen,
    textAlign: "center",
  },
  receiverMeesageView: {
    width: buttonWidth,
    marginTop: 10,
  },
  titleReceiverMessage: {
    fontSize: 14,
    color: darkJungleGreen,
    textAlign: "center",
    borderBottomColor: "red",
    borderBottomWidth: 0.6,
  },
});
