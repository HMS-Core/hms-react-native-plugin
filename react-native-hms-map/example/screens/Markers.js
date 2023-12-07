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
  HMSInfoWindow,
  HMSMarker,
  Hue,
  MapTypes,
  FillMode,
  RepeatMode,
  Interpolator,
} from "@hmscore/react-native-hms-map";
import React from "react";
import {
  Button,
  SafeAreaView,
  ScrollView,
  Text,
  TextInput,
  View,
  Switch,
  Image,
  ToastAndroid,
  StyleSheet,
} from "react-native";
import { styles } from "../styles/styles";

let planeRef;
let count = 0;
const exampleAnimation = {
  rotate: {
    fromDegree: 0,
    toDegree: 150,
    duration: 3000,
  },
  translate: {
    latitude: 40.97511809993672,
    longitude: 29.066076168319412,
    duration: 10000,
    interpolator: Interpolator.ACCELERATE_DECELERATE,
  },
  scale: {
    fromX: 0.5,
    fromY: 0.5,
    toX: 1.5,
    toY: 1.5,
    repeatCount: 1,
  },
  alpha: {
    fromAlpha: 0.6,
    toAlpha: 1,
    repeatCount: 2,
  },
};

const defaultOptions = {
  duration: 5000,
  fillMode: FillMode.FORWARDS,
  repeatCount: 0,
  repeatMode: RepeatMode.REVERSE,
  interpolator: Interpolator.LINEAR,
};

export default class Markers extends React.Component {
  static options = {
    topBar: {
      title: {
        text: "Markers",
      },
    },
  };

  state = {
    defaultActionOnClick: true,
    rotation: 0,
    markerClustering: true,
    params: {},
    fillMode: FillMode.FORWARDS,
    repeatMode: RepeatMode.RESTART,
    interpolator: 0,
    duration: 2000,
    repeatCount: 0,
    animation: {},
    additionalMarkers: [],
  };

  renderAdditionalMarkers() {
    return this.state.additionalMarkers.map((m) => (
      <HMSMarker
        key={m.id}
        coordinate={m.coordinate}
        title={"Marker-" + m.id}
        clusterable
        snippet={`Lat:${m.coordinate.latitude.toFixed(
          4
        )}, Long:${m.coordinate.longitude.toFixed(4)}`}
        icon={{ hue: (m.id * 30) % 360 }}
        onInfoWindowLongClick={() => {
          this.setState((state) => ({
            additionalMarkers: state.additionalMarkers.filter(
              (value) => value.id != m.id
            ),
          }));
        }}
      />
    ));
  }

  CustomInfoWindow = () => (
    <HMSInfoWindow>
      <View style={customStyles.transparent}>
        <View style={customStyles.container}>
          <Text style={[customStyles.infoText, customStyles.font13]}>
            Galata Tower
          </Text>
          <Text style={[customStyles.infoText, customStyles.font11]}>
            Custom info window
          </Text>
        </View>
        <View style={customStyles.infoWindow} />
      </View>
    </HMSInfoWindow>
  );

  TextFieldView = () => {
    const { params } = this.state;

    return (
      <View style={styles.flexCol}>
        <View>
          <Text style={customStyles.font11}>
            Param1: fromDegree/fromAlpha/fromX/latitude
          </Text>
          <Text style={customStyles.font11}>
            Param2: toDegree/toAlpha/toX/longitude
          </Text>
          <Text style={customStyles.font11}>Param3: fromY</Text>
          <Text style={customStyles.font11}>Param4: toY</Text>
        </View>
        <View style={[styles.flexRow, { justifyContent: "space-between" }]}>
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({ params: { ...params, param1: parseFloat(text) } })
            }
            placeholder="Param1"
            keyboardType="number-pad"
          />
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({ params: { ...params, param2: parseFloat(text) } })
            }
            placeholder="Param2"
            keyboardType="number-pad"
          />
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({ params: { ...params, param3: parseFloat(text) } })
            }
            placeholder="Param3"
            keyboardType="number-pad"
          />
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({ params: { ...params, param4: parseFloat(text) } })
            }
            placeholder="Param4"
            keyboardType="number-pad"
          />
        </View>
        <View style={[styles.flexRow]}>
          <View style={[styles.flexRow, { alignItems: "center", padding: 4 }]}>
            <Switch
              value={this.state.fillMode == FillMode.FORWARDS ? false : true}
              onValueChange={() => {
                this.setState((state) => ({
                  fillMode:
                    state.fillMode == FillMode.FORWARDS
                      ? FillMode.BACKWARDS
                      : FillMode.FORWARDS,
                }));
              }}
            />
            <View style={styles.width100}>
              <Text>
                {this.state.fillMode == FillMode.FORWARDS
                  ? "FORWARDS"
                  : "BACKWARDS"}
              </Text>
            </View>
          </View>
          <View style={[styles.flexRow, { alignItems: "center", padding: 4 }]}>
            <Switch
              value={this.state.repeatMode == RepeatMode.RESTART ? false : true}
              onValueChange={() => {
                this.setState((state) => ({
                  repeatMode:
                    state.repeatMode == RepeatMode.RESTART
                      ? RepeatMode.REVERSE
                      : RepeatMode.RESTART,
                }));
              }}
            />
            <View style={styles.width100}>
              <Text>
                {this.state.repeatMode == RepeatMode.RESTART
                  ? "RESTART"
                  : "REVERSE"}
              </Text>
            </View>
          </View>
        </View>
        <View style={[styles.flexRow]}>
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({ duration: parseInt(text, 10) })
            }
            placeholder="Duration"
            keyboardType="number-pad"
          />
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({ repeatCount: parseInt(text, 10) })
            }
            placeholder="Repeat Count"
            keyboardType="number-pad"
          />
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({ interpolator: parseInt(text, 10) })
            }
            placeholder="Interpolator 0-9"
            keyboardType="number-pad"
          />
        </View>
        <View style={[styles.flexRow]}>
          <View style={[styles.flex1, styles.m2]}>
            <Button
              onPress={() => {
                this.setState((state) => ({
                  animation: {
                    ...state.animation,
                    rotate: {
                      fromDegree: state.params.param1,
                      toDegree: state.params.param2,
                      duration: state.duration,
                      repeatMode: state.repeatMode,
                      fillMode: state.fillMode,
                      repeatCount: state.repeatCount,
                      interpolator: state.interpolator,
                    },
                  },
                }));
                ToastAndroid.show(`rotation set`, ToastAndroid.SHORT);
              }}
              title="Set Rotate"
            />
          </View>
          <View style={[styles.flex1, styles.m2]}>
            <Button
              onPress={() => {
                this.setState((state) => ({
                  animation: {
                    ...state.animation,
                    alpha: {
                      fromAlpha: state.params.param1,
                      toAlpha: state.params.param2,
                      duration: state.duration,
                      repeatMode: state.repeatMode,
                      fillMode: state.fillMode,
                      repeatCount: state.repeatCount,
                      interpolator: state.interpolator,
                    },
                  },
                }));
                ToastAndroid.show(`alpha set`, ToastAndroid.SHORT);
              }}
              title="Set Alpha"
            />
          </View>
        </View>
        <View style={[styles.flexRow]}>
          <View style={[styles.flex1, styles.m2]}>
            <Button
              onPress={() => {
                this.setState((state) => ({
                  animation: {
                    ...state.animation,
                    scale: {
                      fromX: state.params.param1,
                      toX: state.params.param2,
                      fromY: state.params.param3,
                      toY: state.params.param4,
                      duration: state.duration,
                      repeatMode: state.repeatMode,
                      fillMode: state.fillMode,
                      repeatCount: state.repeatCount,
                      interpolator: state.interpolator,
                    },
                  },
                }));
                ToastAndroid.show(`scale set`, ToastAndroid.SHORT);
              }}
              title="Set Scale"
            />
          </View>
          <View style={[styles.flex1, styles.m2]}>
            <Button
              onPress={() => {
                this.setState((state) => ({
                  animation: {
                    ...state.animation,
                    translate: {
                      latitude: state.params.param1,
                      longitude: state.params.param2,
                      duration: state.duration,
                      repeatMode: state.repeatMode,
                      fillMode: state.fillMode,
                      repeatCount: state.repeatCount,
                      interpolator: state.interpolator,
                    },
                  },
                }));
                ToastAndroid.show(`translate set`, ToastAndroid.SHORT);
              }}
              title="Set Translate"
            />
          </View>
        </View>
        <View style={[styles.flexRow]}>
          <View style={[styles.flex1, styles.m2]}>
            <Button
              onPress={() => {
                planeRef.setAnimation(this.state.animation);
                planeRef.startAnimation();
              }}
              title="Animate"
              color="#841584"
            />
          </View>
          <View style={[styles.flex1, styles.m2]}>
            <Button
              onPress={() => {
                this.setState({ animation: {} });
              }}
              title="Clear"
              color="red"
            />
          </View>
          <View style={[styles.flex1, styles.m2]}>
            <Button
              onPress={() => {
                planeRef.setAnimation(exampleAnimation, defaultOptions);
                planeRef.startAnimation();
              }}
              title="FLY PLANE"
              color="orange"
            />
          </View>
        </View>
      </View>
    );
  };

  render() {
    return (
      <SafeAreaView>
        <HMSMap
          onMapLongClick={(e) => {
            console.log("HMSMap onMapLongClick, result", e.nativeEvent);
            const coordinate = e.nativeEvent.coordinate;
            this.setState((state) => ({
              additionalMarkers: [
                ...state.additionalMarkers,
                {
                  coordinate: coordinate,
                  id: count++,
                },
              ],
            }));
          }}
          zoomControlsEnabled
          markerClustering={this.state.markerClustering}
          compassEnabled
          markerClusterColor={[255, 230, 130, 0]}
          markerClusterTextColor={[200, 0, 0, 0]}
          markerClusterIcon={{
            asset: "ic_launcher.png",
          }}
          style={styles.height200}
          mapType={MapTypes.NORMAL}
          camera={{
            target: {
              latitude: 41.02155220194891,
              longitude: 29.0037998967586,
            },
            zoom: 12,
          }}
        >
          <HMSMarker
            title="Maiden's Tower"
            snippet="This is a default marker"
            rotation={this.state.rotation}
            draggable
            onClick={() => {
              this.setState((state) => ({
                rotation: (state.rotation + 30) % 360,
              }));
            }}
            defaultActionOnClick={this.state.defaultActionOnClick}
            coordinate={{
              latitude: 41.02155220194891,
              longitude: 29.0037998967586,
            }}
          />
          <HMSMarker
            icon={{ hue: Hue.ORANGE }}
            title="Ayasofia"
            snippet="This is a colored default marker"
            clusterable
            coordinate={{
              latitude: 41.008699470240245,
              longitude: 28.98015976031702,
            }}
          />
          <HMSMarker
            icon={{ hue: Hue.MAGENTA }}
            title="Sultan Ahmet"
            snippet="This is a colored default marker"
            clusterable
            coordinate={{
              latitude: 41.00542331543524,
              longitude: 28.97691153026857,
            }}
          />
          <HMSMarker
            icon={{ hue: Hue.AZURE }}
            title="Topkapı Museum"
            snippet="This is a colored default marker"
            clusterable
            coordinate={{
              latitude: 41.01223774385668,
              longitude: 28.983498212850378,
            }}
          />
          <HMSMarker
            icon={{ hue: Hue.ROSE }}
            title="Column of Constantine"
            snippet="This is a colored default marker"
            clusterable
            coordinate={{
              latitude: 41.0087711,
              longitude: 28.97133142052397,
            }}
          />
          <HMSMarker
            icon={{ hue: Hue.CYAN }}
            title="Gülhane Parkı"
            snippet="This is a colored default marker"
            clusterable
            coordinate={{
              latitude: 41.01358808151719,
              longitude: 28.98213804657346,
            }}
          />
          <HMSMarker
            icon={{
              uri: Image.resolveAssetSource(
                require("../assets/galata-tower.png")
              ).uri,
              width: 140,
              height: 150,
            }}
            clickable={true}
            coordinate={{
              latitude: 41.02564844393837,
              longitude: 28.974169719709817,
            }}
          >
            {this.CustomInfoWindow()}
          </HMSMarker>
          <HMSMarker
            title="Validebağ Korusu"
            snippet="This is custom icon from url."
            icon={{
              uri:
                "https://www-file.huawei.com/-/media/corp/home/image/logo_400x200.png",
              width: 200,
              height: 100,
            }}
            coordinate={{
              latitude: 41.01432234547145,
              longitude: 29.046580953343877,
            }}
          />
          <HMSMarker
            ref={(e) => {
              planeRef = e;
            }}
            onAnimationStart={(e) =>
              console.log(`Animation ${e.nativeEvent.type} Started`)
            }
            onAnimationEnd={(e) =>
              console.log(
                `Animation ${e.nativeEvent.type} Ended in ${e.nativeEvent.duration} ms`
              )
            }
            icon={{
              asset: "plane.png",
            }}
            coordinate={{
              latitude: 41.02664844393837,
              longitude: 28.984169719709817,
            }}
          />
          {this.renderAdditionalMarkers()}
        </HMSMap>
        <ScrollView style={styles.height450}>
          <View style={[styles.flexRow]}>
            <View
              style={[styles.flexRow, { alignItems: "center", padding: 4 }]}
            >
              <Switch
                value={this.state.defaultActionOnClick}
                onValueChange={() => {
                  this.setState((state) => ({
                    defaultActionOnClick: !state.defaultActionOnClick,
                  }));
                }}
              />
              <View style={styles.width100}>
                <Text>Default Action on Click</Text>
              </View>
            </View>
            <View
              style={[styles.flexRow, { alignItems: "center", padding: 4 }]}
            >
              <Switch
                value={this.state.markerClustering}
                onValueChange={() => {
                  this.setState((state) => ({
                    markerClustering: !state.markerClustering,
                  }));
                }}
              />
              <View style={styles.width100}>
                <Text>Marker Clustering</Text>
              </View>
            </View>
          </View>
          <View style={styles.p4}>
            <Text>Animate Plane</Text>
            {this.TextFieldView()}
          </View>
        </ScrollView>
      </SafeAreaView>
    );
  }
}

const customStyles = StyleSheet.create({
  viewHeight: { height: 400 },
  flexMargin: { flex: 1, margin: 1 },
  container: {
    backgroundColor: "rgb(49,49,49)",
    borderRadius: 6,
    paddingHorizontal: 14,
    paddingVertical: 6,
  },
  font11: { fontSize: 11 },
  font13: { fontSize: 13 },
  transparent: { backgroundColor: "transparent" },
  infoText: { color: "#fff", fontFamily: "Muli", fontSize: 12 },
  infoWindow: {
    width: 0,
    height: 0,
    backgroundColor: "transparent",
    borderStyle: "solid",
    borderLeftWidth: 8,
    borderRightWidth: 8,
    borderBottomWidth: 16,
    borderLeftColor: "transparent",
    borderRightColor: "transparent",
    borderBottomColor: "rgb(49,49,49)",
    alignSelf: "center",
    transform: [{ rotate: "180deg" }],
  },
});
