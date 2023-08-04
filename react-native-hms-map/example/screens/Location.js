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
import {
  PermissionsAndroid,
  SafeAreaView,
  Switch,
  Text,
  View,
  Image,
} from "react-native";
import { styles } from "../styles/styles";

export default class Location extends React.Component {
  static options = {
    topBar: {
      title: {
        text: "Location",
      },
    },
  };

  state = {
    myLocationEnabled: false,
    myLocationButtonEnabled: false,
    myLocationStyleEnabled:false,
    myLocationStyle: null
  };

  render() {
    return (
      <SafeAreaView>
        <HMSMap
          zoomControlsEnabled
          compassEnabled
          myLocationEnabled={this.state.myLocationEnabled}
          myLocationButtonEnabled={this.state.myLocationButtonEnabled}
          onMyLocationButtonClick={() =>
            console.log("HMSMap onMyLocationButtonClick")
          }
          onMyLocationClick={() => console.log("HMSMap onMyLocationClick")}
          style={styles.height300}
          mapType={MapTypes.NORMAL}
          camera={{
            target: {
              latitude: 41.02155220194891,
              longitude: 29.0037998967586,
            },
            zoom: 2,
          }}
          myLocationStyle={this.state.myLocationStyle}
        />
        <View style={[styles.flexRow, { padding: 4 }]}>
          <View
            style={[styles.flexRow, styles.flex1, { alignItems: "center" }]}
          >
            <Switch
              onValueChange={() => {
                if (this.state.myLocationEnabled) {
                  this.setState({ myLocationEnabled: false });
                  this.setState({ myLocationButtonEnabled: false });
                  this.setState({ myLocationStyleEnabled: false });
                } else {
                  PermissionsAndroid.check(
                    PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION
                  ).then((res) => {
                    res
                      ? this.setState({ myLocationEnabled: true })
                      : PermissionsAndroid.request(
                        PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION
                      ).then(
                        (granted) =>
                          PermissionsAndroid.RESULTS.GRANTED === granted &&
                          this.setState({ myLocationEnabled: true })
                      );
                  });
                }
              }}
              value={this.state.myLocationEnabled}
            />
            <Text title="My Location">Location</Text>
          </View>
          <View
            style={[styles.flexRow, styles.flex1, { alignItems: "center" }]}
          >
          <Switch
              onValueChange={() =>
                this.setState((state) => ({
                  myLocationButtonEnabled: !state.myLocationButtonEnabled,
                }))
              }
              value={this.state.myLocationButtonEnabled}
            />
            <Text title="My Location Button">Location Button</Text>
          </View>
        </View>
        <View style={[styles.flexRow, { padding: 4 }]}>
        <View
            style={[styles.flexRow, styles.flex1, { alignItems: "center" }]}
          >
          <Switch
              onValueChange={() => {
                console.log(" onValueChange myLocationStyleEnabled is "+ this.state.myLocationStyleEnabled);
                if (this.state.myLocationStyleEnabled) {
                  this.setState({ 
                    myLocationStyle: null
                });
                } else {
                  this.setState({ 
                    myLocationStyle: {
                    anchor: [0.1, 0.1],
                    icon:{
                      asset: "plane.png",
                      width: 30,
                      height: 30,
                    },
                  } 
                });
                }
                this.setState((state) => ({
                  myLocationStyleEnabled: !state.myLocationStyleEnabled,
                }))
                console.log(" onValueChange myLocationStyle is "+ this.state.myLocationStyle);
              }}
              
              value={this.state.myLocationStyleEnabled}
            />
            <Text title="Style My Location Button"> Style Location Button</Text>
          </View>
        </View>
      </SafeAreaView>
    );
  }
}
