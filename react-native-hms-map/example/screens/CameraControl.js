/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

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

import HMSMap, { MapTypes } from "@hmscore/react-native-hms-map";
import React, { useState } from "react";
import {
  Button,
  SafeAreaView,
  ScrollView,
  Switch,
  Text,
  TextInput,
  View,
} from "react-native";
import { styles } from "../styles/styles";

let mapView;

export default class CameraControl extends React.Component {
  static options = {
    topBar: {
      title: {
        text: "Location",
      },
    },
  };

  state = {
    pixel: 100,
    useAnimation: false,
    minZoom: 1,
    maxZoom: 15,
    zoom: 12,
    customCamera: {},
  };

  render() {
    const {
      pixel,
      useAnimation,
      minZoom,
      maxZoom,
      zoom,
      customCamera,
    } = this.state;

    const CameraButtons = (
      <View style={[styles.flexCol, styles.width125]}>
        <Text style={[styles.m4, styles.textBold]}>Camera Position</Text>
        <View style={styles.flexRow}>
          <View style={[styles.flexRow, styles.flex1, styles.p4]}>
            <Button
              onPress={() => mapView.scrollBy(-pixel, pixel)}
              title={"⬉"}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1, styles.p4]}>
            <Button onPress={() => mapView.scrollBy(0, pixel)} title={"⬆"} />
          </View>
          <View style={[styles.flexRow, styles.flex1, styles.p4]}>
            <Button
              onPress={() => mapView.scrollBy(pixel, pixel)}
              title={"⬈"}
            />
          </View>
        </View>
        <View style={styles.flexRow}>
          <View style={[styles.flexRow, styles.flex1, styles.p4]}>
            <Button onPress={() => mapView.scrollBy(-pixel, 0)} title={"⬅"} />
          </View>
          <View style={[styles.flexRow, styles.flex1, styles.p4]}>
            <TextInput
              underlineColorAndroid="gray"
              keyboardType="number-pad"
              value={"" + pixel}
              onChangeText={(x) => this.setState({ pixel: parseInt(x, 10) })}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1, styles.p4]}>
            <Button onPress={() => mapView.scrollBy(pixel, 0)} title={"➝"} />
          </View>
        </View>
        <View style={styles.flexRow}>
          <View style={[styles.flexRow, styles.flex1, styles.p4]}>
            <Button
              onPress={() => mapView.scrollBy(-pixel, -pixel)}
              title={"⬋"}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1, styles.p4]}>
            <Button onPress={() => mapView.scrollBy(0, -pixel)} title={"⬇"} />
          </View>
          <View style={[styles.flexRow, styles.flex1, styles.p4]}>
            <Button
              onPress={() => mapView.scrollBy(pixel, -pixel)}
              title={"⬊"}
            />
          </View>
        </View>
      </View>
    );
    const ZoomControls = (
      <View style={[styles.flexCol, styles.width125]}>
        <Text style={[styles.m4, styles.textBold]}>Zoom Change</Text>
        <View style={[styles.flexRow, { marginTop: 2 }]}>
          <Text title="Min" style={styles.width40}>
            Min
          </Text>
          <View style={[styles.flexRow, styles.flex1]}>
            <Button
              color="red"
              onPress={() =>
                minZoom > 0 && this.setState({ minZoom: minZoom - 1 })
              }
              title={"-"}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text>{"" + minZoom}</Text>
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Button
              color="green"
              onPress={() =>
                minZoom < zoom && this.setState({ minZoom: minZoom + 1 })
              }
              title={"+"}
            />
          </View>
        </View>
        <View style={[styles.flexRow, { marginTop: 2 }]}>
          <Text title="Zoom" style={styles.width40}>
            Zoom
          </Text>
          <View style={[styles.flexRow, styles.flex1]}>
            <Button
              color="red"
              onPress={() => {
                if (zoom > minZoom) {
                  this.setState({ zoom: zoom - 1 });
                  mapView.zoomOut();
                }
              }}
              title={"-"}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text>{"" + zoom}</Text>
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Button
              color="green"
              onPress={() => {
                if (zoom < maxZoom) {
                  this.setState({ zoom: zoom + 1 });
                  mapView.zoomIn();
                }
              }}
              title={"+"}
            />
          </View>
        </View>
        <View style={[styles.flexRow, { marginTop: 2 }]}>
          <Text title="Min" style={styles.width40}>
            Max
          </Text>
          <View style={[styles.flexRow, styles.flex1]}>
            <Button
              color="red"
              onPress={() =>
                maxZoom > zoom && this.setState({ maxZoom: maxZoom - 1 })
              }
              title={"-"}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text>{"" + maxZoom}</Text>
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Button
              color="green"
              onPress={() =>
                maxZoom < 20 && this.setState({ maxZoom: maxZoom + 1 })
              }
              title={"+"}
            />
          </View>
        </View>
      </View>
    );
    const UseAnimationView = (
      <View style={[styles.flexRow, { alignItems: "center" }]}>
        <Switch
          value={useAnimation}
          onValueChange={() => {
            this.setState({ useAnimation: !useAnimation });
          }}
        />
        <Text>Use Animation</Text>
      </View>
    );
    const TextFieldView = (
      <View style={styles.flexCol}>
        <View style={[styles.flexRow, { justifyContent: "space-between" }]}>
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({
                customCamera: {
                  ...customCamera,
                  target: {
                    ...customCamera.target,
                    latitude: parseFloat(text),
                  },
                },
              })
            }
            placeholder="Lat"
            keyboardType="number-pad"
          />
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({
                customCamera: {
                  ...customCamera,
                  target: {
                    ...customCamera.target,
                    longitude: parseFloat(text),
                  },
                },
              })
            }
            placeholder="Long"
            keyboardType="number-pad"
          />
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({
                customCamera: {
                  ...customCamera,
                  zoom: parseInt(text, 10),
                },
              })
            }
            placeholder="Zoom"
            keyboardType="number-pad"
          />
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({
                customCamera: {
                  ...customCamera,
                  bearing: parseInt(text, 10),
                },
              })
            }
            placeholder="Bearing"
            keyboardType="number-pad"
          />
          <TextInput
            style={styles.textInput}
            onChangeText={(text) =>
              this.setState({
                customCamera: {
                  ...customCamera,
                  tilt: parseInt(text, 10),
                },
              })
            }
            placeholder="Tilt"
            keyboardType="number-pad"
          />
        </View>
        <Button
          onPress={() => {
            mapView && mapView.setCameraPosition(customCamera);
          }}
          title="Go"
          color="#841584"
        />
      </View>
    );
    return (
      <SafeAreaView>
        <ScrollView style={[styles.flexCol]}>
          <HMSMap
            ref={(ref) => {
              mapView = ref;
            }}
            style={styles.height250}
            animationDuration={2000}
            mapType={MapTypes.NORMAL}
            useAnimation={useAnimation}
            minZoomPreference={minZoom}
            maxZoomPreference={maxZoom}
            camera={{
              target: {
                latitude: 41.02155220194891,
                longitude: 29.0037998967586,
              },
              zoom: 12,
            }}
          />

          <View style={[styles.flexCol, styles.p4]}>{UseAnimationView}</View>
          <View style={[styles.flexRow, styles.p4]}>
            {CameraButtons}
            {ZoomControls}
          </View>
          <View style={styles.p4}>
            <Text style={styles.textBold}>Move Camera</Text>
            {TextFieldView}
          </View>
          <View style={[styles.flexRow, styles.p4]}>
            <View style={[styles.flex1, styles.m2]}>
              <Button
                onPress={() => {
                  /*
                   * These functions can be used for camera update
                   *  mapView.zoomIn();
                   *  mapView.zoomOut();
                   *  mapView.zoomTo(10.5);
                   *  mapView.zoomBy(-1.5);
                   *  mapView.zoomBy(2, {x: 0, y: 0});
                   *  mapView.scrollBy(100, 100);
                   *  mapView.setBounds([{latitude: 41.5, longitude: 28.5},{latitude: 40.5, longitude: 29.5}],1);
                   *  mapView.setCameraPosition({target: {latitude: 41, longitude: 29}, zoom:13, tilt:40});
                   */
                  mapView.setCoordinates({ latitude: 0, longitude: 0 }, 3);
                }}
                title="Go to (0,0)"
                color="blue"
              />
            </View>
            <View style={[styles.flex1, styles.m2]}>
              <Button
                onPress={() => {
                  mapView.stopAnimation();
                }}
                title="Stop Animation"
                color="red"
              />
            </View>
          </View>
        </ScrollView>
      </SafeAreaView>
    );
  }
}
