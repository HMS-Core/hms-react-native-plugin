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
  SafeAreaView,
  StyleSheet,
  ScrollView,
  Text,
  View,
  TouchableHighlight,
} from "react-native";

import BasicMap from "./screens/BasicMap";
import CameraControl from "./screens/CameraControl";
import Gestures from "./screens/Gestures";
import Location from "./screens/Location";
import MapLayers from "./screens/MapLayers";
import MapStyle from "./screens/MapStyle";
import Markers from "./screens/Markers";
import AdvancedMap from "./screens/AdvancedMap";
import HeatMap from "./screens/HeatMap";
import { styles } from "./styles/styles";

const buttons = [
  {
    title: "Basic Map",
    component: BasicMap,
    description: "The most basic map component to show.",
  },
  {
    title: "Camera Controls",
    component: CameraControl,
    description:
      "Manipulate the camera via move, zoom, tilt, bearing. Animate the camera and stop the animation.",
  },
  {
    title: "Gestures and UI",
    component: Gestures,
    description:
      "Control zoom, rotate, scroll, tilt gestures and show/hide zoom/compass.",
  },
  {
    title: "Location",
    component: Location,
    description: "Show your location on the map and show/hide location button.",
  },
  {
    title: "Markers",
    component: Markers,
    description:
      "Show markers with default, colored and customized options. Show/hide default and customized info windows, animate markers, apply clustering. Add markers via long click and remove them via long click on ino window.",
  },
  {
    title: "Map Layers",
    component: MapLayers,
    description:
      "Show basic and customized circles, polylines, polygons and ground overlays.",
  },
  {
    title: "Map Styles",
    component: MapStyle,
    description:
      "Show different ways how to style a map via mapType, mapStyle and tile overlay",
  },
  {
    title: "Advanced Map",
    component: AdvancedMap,
    description: "More advanced settings",
  },
  {
    title: "Heat Map",
    component: HeatMap,
    description: "Shows Heat Map",
  },
];

export default class App extends React.Component {
  state = {
    currentScreen: buttons[0],
  };

  renderButtons() {
    return buttons.map((b) => (
      <View
        key={b.title}
        style={[
          styles.p4,
          styles.m2,
          this.state.currentScreen == b ? customStyle.buttonBorder : null,
        ]}
      >
        <TouchableHighlight
          onPress={() => {
            this.setState({ currentScreen: b });
          }}
        >
          <Text>{b.title}</Text>
        </TouchableHighlight>
      </View>
    ));
  }

  renderScreen() {
    const Map = this.state.currentScreen.component;
    return <Map />;
  }

  render() {
    return (
      <SafeAreaView>
        <View>
          <ScrollView horizontal style={[styles.p4]}>
            {this.renderButtons()}
          </ScrollView>
        </View>

        <View style={customStyle.lineStyle} />
        {this.renderScreen()}
      </SafeAreaView>
    );
  }
}

const customStyle = StyleSheet.create({
  lineStyle: {
    marginTop: 8,
    borderBottomColor: "gray",
    borderBottomWidth: 1,
  },
  buttonBorder: {
    borderColor: "black",
    borderWidth: 1,
    borderRadius: 5,
  },
});
