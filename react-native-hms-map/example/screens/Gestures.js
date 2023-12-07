/*
 * Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import HMSMap, { MapTypes } from "@hmscore/react-native-hms-map";
import React from "react";
import { SafeAreaView, ScrollView, Switch, Text, View } from "react-native";
import { styles } from "../styles/styles";

export default class Gestures extends React.Component {
  static options = {
    topBar: {
      title: {
        text: "Gestures",
      },
    },
  };

  state = {
    zoomGesture: true,
    zoomButtons: true,
    compass: true,
    scroll: true,
    tilt: true,
    rotate: true,
    scrollGesturesEnabledDuringRotateOrZoom: true,
    pointToCenter: { x: 200, y: 200 },
    scaleByMapCenter: false,
  };

  leftColumn = [
    {
      title: "Zoom Buttons",
      value: "zoomButtons",
    },
    {
      title: "Zoom Gesture",
      value: "zoomGesture",
    },
    {
      title: "Scroll Gesture",
      value: "scroll",
    },
    {
      title: "Scale By Map Center",
      value: "scaleByMapCenter",
    },
  ];

  rightColumn = [
    {
      title: "Compass",
      value: "compass",
    },
    {
      title: "Rotate Gesture",
      value: "rotate",
    },
    {
      title: "Tilt Gesture",
      value: "tilt",
    },
    {
      title: "Scroll Enabled During Zoom&Rotate",
      value: "scrollGesturesEnabledDuringRotateOrZoom",
    },
  ];

  renderColumn(column) {
    return column.map((c) => (
      <View
        key={c.title}
        style={[styles.flexRow, { alignItems: "center", padding: 4 }]}
      >
        <Switch
          value={this.state[c.value]}
          onValueChange={() => {
            this.setState({ [c.value]: !this.state[c.value] });
          }}
        />
        <View style={styles.width100}>
          <Text>{c.title}</Text>
        </View>
      </View>
    ));
  }

  render() {
    return (
      <SafeAreaView>
        <ScrollView>
          <HMSMap
            onMapLongClick={(e) => {
              console.log(e.nativeEvent);
            }}
            zoomControlsEnabled={this.state.zoomButtons}
            compassEnabled={this.state.compass}
            zoomGesturesEnabled={this.state.zoomGesture}
            scrollGesturesEnabled={this.state.scroll}
            rotateGesturesEnabled={this.state.rotate}
            tiltGesturesEnabled={this.state.tilt}
            scrollGesturesEnabledDuringRotateOrZoom={
              this.state.scrollGesturesEnabledDuringRotateOrZoom
            }
            gestureScaleByMapCenter={this.state.scaleByMapCenter}
            pointToCenter={this.state.pointToCenter}
            style={styles.height300}
            mapType={MapTypes.NORMAL}
            camera={{
              target: {
                latitude: 41.02155220194891,
                longitude: 29.0037998967586,
              },
              zoom: 12,
              bearing: 10,
            }}
          />
          <View style={[styles.flexRow, styles.p4]}>
            <View style={styles.flexCol}>
              {this.renderColumn(this.leftColumn)}
            </View>
            <View style={styles.flexCol}>
              {this.renderColumn(this.rightColumn)}
            </View>
          </View>
        </ScrollView>
      </SafeAreaView>
    );
  }
}
