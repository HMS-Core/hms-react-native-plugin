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

import HMSMap, {
  MapTypes,
  HMSTileOverlay,
} from "@hmscore/react-native-hms-map";
import React from "react";
import {
  Button,
  SafeAreaView,
  ScrollView,
  Switch,
  Text,
  View,
} from "react-native";

import mapStyleJson from "../mapStyle.json";
import { styles } from "../styles/styles";

let tileOverlay;

export default class MapStyle extends React.Component {
  static options = {
    topBar: {
      title: {
        text: "Map Style",
      },
    },
  };

  state = {
    mapType: true,
    customMapStyle: false,
    showTileOverlay: false,
    showCustomTiles: false,
    trafficEnabled: false,
    buildingsEnabled: false,
  };

  render() {
    const CustomTileOverlay = (
      <HMSTileOverlay
        ref={(e) => {
          tileOverlay = e;
        }}
        tileProvider={{
          url: "https://a.tile.openstreetmap.org/{z}/{x}/{y}.png",
        }}
        visible={this.state.showTileOverlay}
        fadeIn={false}
        zIndex={10}
        transparency={0.2}
      />
    );

    const CustomTiles = (
      <HMSTileOverlay
        tileProvider={[
          {
            x: 3,
            y: 4,
            zoom: 3,
            asset: "ic_launcher.png",
          },
          {
            x: 4,
            y: 3,
            zoom: 3,
            asset: "ic_launcher.png",
          },
          {
            x: 4,
            y: 4,
            zoom: 3,
            asset: "ic_launcher.png",
          },
        ]}
        visible={this.state.showCustomTiles}
        fadeIn={false}
        zIndex={10}
        transparency={0.2}
      />
    );

    return (
      <SafeAreaView>
        <ScrollView>
          <HMSMap
            style={{ height: 450 }}
            trafficEnabled={this.state.trafficEnabled}
            buildingsEnabled={this.state.buildingsEnabled}
            mapType={!this.state.mapType ? MapTypes.NONE : MapTypes.NORMAL}
            mapStyle={
              this.state.customMapStyle ? JSON.stringify(mapStyleJson) : ""
            }
            camera={
              !this.state.showCustomTiles
                ? {
                  target: {
                    latitude: 41.02155220194891,
                    longitude: 29.0037998967586,
                  },
                  zoom: 12,
                }
                : {
                  target: {
                    latitude: 0,
                    longitude: 0,
                  },
                  zoom: 3,
                }
            }
          >
            {CustomTileOverlay}
            {CustomTiles}
          </HMSMap>
          <View style={[styles.flexRow, styles.p4]}>
            <View
              style={[styles.flexRow, styles.flex1, { alignItems: "center" }]}
            >
              <Switch
                onValueChange={() =>
                  this.setState((state) => ({
                    mapType: !state.mapType,
                  }))
                }
                value={this.state.mapType}
              />
              <Text>Map Type</Text>
            </View>

            <View
              style={[styles.flexRow, styles.flex1, { alignItems: "center" }]}
            >
              <Switch
                onValueChange={() =>
                  this.setState((state) => ({
                    customMapStyle: !state.customMapStyle,
                  }))
                }
                value={this.state.customMapStyle}
              />
              <Text>Custom Map Style</Text>
            </View>
          </View>
          <View style={[styles.flexRow, styles.p4]}>
            <View
              style={[styles.flexRow, styles.flex1, { alignItems: "center" }]}
            >
              <Switch
                onValueChange={() =>
                  this.setState((state) => ({
                    showTileOverlay: !state.showTileOverlay,
                  }))
                }
                value={this.state.showTileOverlay}
              />
              <Text>Tile Overlay</Text>
            </View>

            <View
              style={[styles.flexRow, styles.flex1, { alignItems: "center" }]}
            >
              <Switch
                onValueChange={() =>
                  this.setState((state) => ({
                    showCustomTiles: !state.showCustomTiles,
                  }))
                }
                value={this.state.showCustomTiles}
              />
              <Text>Custom Tiles</Text>
            </View>
          </View>
          <View style={[styles.flexRow, styles.p4]}>
            <View
              style={[styles.flexRow, styles.flex1, { alignItems: "center" }]}
            >
              <Switch
                onValueChange={() =>
                  this.setState((state) => ({
                    trafficEnabled: !state.trafficEnabled,
                  }))
                }
                value={this.state.trafficEnabled}
              />
              <Text>Traffic Enabled</Text>
            </View>
            <View
              style={[styles.flexRow, styles.flex1, { alignItems: "center" }]}
            >
              <Switch
                onValueChange={() =>
                  this.setState((state) => ({
                    buildingsEnabled: !state.buildingsEnabled,
                  }))
                }
                value={this.state.buildingsEnabled}
              />
              <Text>Buildings Enabled</Text>
            </View>
          </View>
          <View style={[styles.p4]}>
            <Button
              onPress={() => {
                tileOverlay && tileOverlay.clearTileCache();
              }}
              title="Clear Tile Cache"
              color="red"
            />
          </View>
        </ScrollView>
      </SafeAreaView>
    );
  }
}
