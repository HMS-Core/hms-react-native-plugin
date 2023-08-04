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

import React from "react";
import {
  SafeAreaView,
  View,
  Text,
  Image,
  TouchableHighlight,
  Button,
  ScrollView,
  ToastAndroid,
} from "react-native";

import HMSMap, {
  HMSMarker,
  HMSCircle,
  HMSPolygon,
  HMSPolyline,
  HMSGroundOverlay,
  PatternItemTypes,
  JointTypes,
  CapTypes,
} from "@hmscore/react-native-hms-map";

import { styles } from "../styles/styles";

const base64String = "data:image/png;base64,";
let mapView;
let markerView;
const logMessage = (message) => {
  console.log(message);
  ToastAndroid.show(JSON.stringify(message), ToastAndroid.LONG);
};
const takeSnapshot = () => mapView.takeSnapshot();

const getHuaweiMapInfo = () =>
mapView &&
  mapView
    .getHuaweiMapInfo()
    .then((a) => logMessage(a))
    .catch((a) => logMessage(a));

const clearMap = () => mapView && mapView.clear();

const getLayerOptionsInfo = () =>
  mapView &&
  markerView &&
  mapView
    .getLayerOptionsInfo(markerView)
    .then((a) => logMessage(a))
    .catch((e) => logMessage(a));

const getLayerInfo = () =>
  mapView &&
  markerView &&
  mapView
    .getLayerInfo(markerView)
    .then((a) => logMessage(a))
    .catch((a) => logMessage(a));

const getCoordinateFromPoint = () =>
  mapView &&
  mapView
    .getCoordinateFromPoint({ x: 100, y: 100 })
    .then((a) => logMessage(a))
    .catch((a) => logMessage(a));

const getPointFromCoordinate = () =>
  mapView &&
  mapView
    .getPointFromCoordinate({ latitude: 0, longitude: 0 })
    .then((a) => logMessage(a))
    .catch((a) => logMessage(a));

const getScalePerPixel = () =>
mapView &&
  mapView
    .getScalePerPixel()
    .then((a) => logMessage(a))
    .catch((a) => logMessage(a));

const calculateDistance = () => {
  HMSMap.module
    .getDistance(
      { latitude: 41, longitude: 29 },
      { latitude: 41, longitude: 28 }
    )
    .then((a) => logMessage(a))
    .catch((a) => logMessage(a));
};

const enableLogger = () =>
  HMSMap.module.enableLogger().then(() => logMessage("Logger enabled"));

const disableLogger = () =>
  HMSMap.module.disableLogger().then(() => logMessage("Logger disabled"));

const getDefaultState = () => ({
  zoom: 3,
  lat: 0.0,
  lng: 0.0,
  bearing: 0.0,
  tilt: 0.0,
  compassEnabled: true,
  mapType: 1, // Just use normal(1) and empty(0) m,
  minZoomPreference: 3,
  maxZoomPreference: 20,
  rotateGesturesEnabled: true,
  scrollGesturesEnabled: true,
  tiltGesturesEnabled: true,
  zoomControlsEnabled: true,
  zoomGesturesEnabled: true,
  myLocationEnabled: false,
  myLocationButtonEnabled: false,
  markerClustering: false,
  scrollGesturesEnabledDuringRotateOrZoom: true,
  stylingMap: false,
  useAnimation: true,
  markerCol: 2,
  markerRow: 5,
  showTileOvelay: false,
  pixel: 100,
  showSnapshot: false,
  snapshotString: base64String,
  isDefaultAction: true,
});
const CircleComplex = () => (
  <HMSCircle
    center={{ latitude: 10, longitude: 0 }}
    radius={900000}
    clickable={true}
    strokeColor={-256} // yellow(0xFFFFFF00)
    fillColor={538066306} // transparent blue(0x20123D82)
    strokeWidth={8}
    visible={true}
    zIndex={2}
    onClick={(e) => logMessage("Circle onClick")}
  />
);

const PolygonComplex = () => (
  <HMSPolygon
    points={[
      { latitude: 10.5, longitude: 18.5 },
      { latitude: 0.5, longitude: 18.5 },
      { latitude: 0.5, longitude: 9.5 },
      { latitude: 10.5, longitude: 9.5 },
    ]}
    holes={[
      [
        { latitude: 5.5, longitude: 13.5 },
        { latitude: 3.5, longitude: 13.5 },
        { latitude: 3.5, longitude: 15.5 },
      ],
      [
        { latitude: 6.5, longitude: 18.0 },
        { latitude: 8.5, longitude: 18.0 },
        { latitude: 8.5, longitude: 16.5 },
      ],
    ]}
    clickable={true}
    geodesic={true}
    fillColor={538066306} // transparent blue(0x20123D82)
    strokeColor={-256} // yellow(0xFFFFFF00)
    strokeJointType={JointTypes.BEVEL}
    strokePattern={[
      { type: PatternItemTypes.DASH, length: 20 },
      { type: PatternItemTypes.DOT },
      { type: PatternItemTypes.GAP, length: 20 },
    ]}
    zIndex={2}
    onClick={(e) => logMessage("Polygon onClick")}
  />
);
const PolylineComplex = () => (
  <HMSPolyline
    points={[
      { latitude: -10, longitude: -10 },
      { latitude: -15, longitude: -10 },
      { latitude: -10, longitude: -15 },
    ]}
    clickable={true}
    geodesic={true}
    color={538066306} // transparent blue(0x20123D82)
    visible={true}
    width={22.0}
    zIndex={2}
    onClick={(e) => logMessage("Polyline onClick")}
  />
);

const GroundOverlaySimple = () => (
  <HMSGroundOverlay
    image={{
      // hue: 30.0,
      asset: "ic_launcher.png", // under assets folder
      // path on the device
      // path:
      //   "/data/data/com.huawei.rnhmsmapdemo/files/map-style/img/native_dianhua_dire_arrow.png",
      // file: 'filename',
    }}
    coordinate={[
      { latitude: -10, longitude: 10 },
      { latitude: -10, longitude: 20 },
      { latitude: -25, longitude: 10 },
    ]}
  />
);
const MarkerComplex = (props) => (
  <HMSMarker
    coordinate={{ latitude: -10, longitude: 0 }}
    draggable={true}
    flat={true}
    icon={{
      asset: "ic_launcher.png", // under assets folder
    }}
    alpha={0.8}
    title="Complex Marker"
    defaultActionOnClick={props.isDefaultAction}
    markerAnchor={[0.5, 0.5]}
    infoWindowAnchor={[0.5, 0.5]}
    rotation={30.0}
    visible={true}
    zIndex={0}
    clusterable={false}
    onClick={(e) => logMessage("Marker onClick")}
    onDragStart={(e) => logMessage("Marker onDragStart")}
    onDrag={(e) => console.log("Marker onDrag")}
    onDragEnd={(e) => logMessage("Marker onDragEnd")}
    onInfoWindowClick={(e) => logMessage("Marker onInfoWindowClick")}
    onInfoWindowClose={(e) => logMessage("Marker onInfoWindowClose")}
    onInfoWindowLongClick={(e) => logMessage("Marker onInfoWindowLongClick")}
    ref={(e) => {
      markerView = e;
    }}
  />
);

export default class AdvancedMap extends React.Component {
  static options = {
    topBar: {
      title: {
        text: "Advanced Map",
      },
    },
  };

  constructor() {
    super();
    this.state = getDefaultState();
  }

  render() {
    return (
      <SafeAreaView>
        <View contentInsetAdjustmentBehavior="automatic">
          <HMSMap
            style={styles.mapView}
            darkMode={true}
            camera={{
              target: { latitude: 0, longitude: 0 },
              zoom: 3,
              bearing: 50,
              tilt: 80,
            }}
            latLngBoundsForCameraTarget={[
              { latitude: -20, longitude: -20 },
              { latitude: 20, longitude: 20 },
            ]}
            useAnimation={this.state.useAnimation}
            animationDuration={2000}
            compassEnabled={this.state.compassEnabled}
            mapType={this.state.mapType}
            minZoomPreference={this.state.minZoomPreference}
            maxZoomPreference={this.state.maxZoomPreference}
            rotateGesturesEnabled={this.state.rotateGesturesEnabled}
            scrollGesturesEnabled={this.state.scrollGesturesEnabled}
            tiltGesturesEnabled={this.state.tiltGesturesEnabled}
            zOrderOnTop={false}
            zoomControlsEnabled={this.state.zoomControlsEnabled}
            zoomGesturesEnabled={this.state.zoomGesturesEnabled}
            buildingsEnabled={true}
            description="Huawei Map"
            myLocationEnabled={this.state.myLocationEnabled}
            mapPadding={{ right: 100, left: 10, top: 10, bottom: 10 }}
            markerClustering={this.state.markerClustering}
            myLocationButtonEnabled={this.state.myLocationButtonEnabled}
            scrollGesturesEnabledDuringRotateOrZoom={
              this.state.scrollGesturesEnabledDuringRotateOrZoom
            }
            onCameraUpdateFinished={(e) =>
              console.log("HMSMap onCameraUpdateFinished")
            }
            onCameraUpdateCanceled={(e) =>
              console.log("HMSMap onCameraUpdateCanceled")
            }
            onCameraIdle={(e) => {
              console.log("HMSMap onCameraIdle, result", e.nativeEvent);
              const cameraPosition = e.nativeEvent;
              this.setState({
                zoom: parseFloat(cameraPosition.zoom.toFixed(2)),
              });
              this.setState({
                lat: parseFloat(cameraPosition.target.latitude.toFixed(5)),
              });
              this.setState({
                lng: parseFloat(cameraPosition.target.longitude.toFixed(5)),
              });
              this.setState({
                bearing: parseFloat(cameraPosition.bearing.toFixed(2)),
              });
              this.setState({
                tilt: parseFloat(cameraPosition.tilt.toFixed(2)),
              });
            }}
            onMapReady={() => console.log("HMSMap onMapReady")}
            onCameraMoveCanceled={() =>
              console.log("HMSMap onCameraMoveCanceled")
            }
            onCameraMove={(e) =>
              console.log("HMSMap onCameraMove result", e.nativeEvent)
            }
            onCameraMoveStarted={(e) =>
              console.log("HMSMap onCameraMoveStarted, result", e.nativeEvent)
            }
            onMapClick={(e) =>
              console.log("HMSMap onMapClick, result", e.nativeEvent)
            }
            onMapLoaded={() => console.log("HMSMap onMapLoaded")}
            onMapLongClick={(e) =>
              console.log("HMSMap onMapLongClick, result", e.nativeEvent)
            }
            onMyLocationButtonClick={() =>
              console.log("HMSMap onMyLocationButtonClick")
            }
            onMyLocationClick={() => console.log("HMSMap onMyLocationClick")}
            onPoiClick={(e) =>
              console.log("HMSMap onPoiClick, result", e.nativeEvent)
            }
            onSnapshotReady={(e) => {
              console.log("HMSMap onSnapshotReady");
              this.setState({ showSnapshot: true });
              this.setState({
                snapshotString: base64String + e.nativeEvent.bitmap,
              });
            }}
            ref={(e) => {
              mapView = e;
            }}
          >
            <MarkerComplex isDefaultAction={this.state.isDefaultAction} />
            <CircleComplex />
            <PolygonComplex />
            <PolylineComplex />
            <GroundOverlaySimple />
          </HMSMap>
          <SafeAreaView style={styles.height400}>
            <ScrollView>
              <View>
                <View style={[styles.p4]}>
                  <Text>Get info about components</Text>
                </View>

                <View
                  style={[
                    styles.flexRow,
                    { justifyContent: "space-around", padding: 4 },
                  ]}
                >
                  <View style={[styles.flex1, styles.m1]}>
                    <Button title="Map" onPress={getHuaweiMapInfo} />
                  </View>
                  <View style={[styles.flex1, styles.m1]}>
                    <Button
                      title="Layer"
                      color="green"
                      onPress={getLayerInfo}
                    />
                  </View>
                  <View style={[styles.flex2, styles.m1]}>
                    <Button
                      title="Layer Options"
                      onPress={getLayerOptionsInfo}
                    />
                  </View>
                </View>
                <View style={[styles.flexRow, styles.p4]}>
                  <View style={[styles.flex1, styles.m1]}>
                    <Button
                      color="purple"
                      title="Get Coord. from Point"
                      onPress={getCoordinateFromPoint}
                    />
                  </View>
                  <View style={[styles.flex1, styles.m1]}>
                    <Button
                      color="green"
                      title="Get Point From Coord."
                      onPress={getPointFromCoordinate}
                    />
                  </View>
                  <View style={[styles.flex1, styles.m1]}>
                    <Button
                      color="red"
                      title="Get Scale Per Pixel"
                      onPress={getScalePerPixel}
                    />
                  </View>
                </View>
                <View style={[styles.flexRow, styles.p4]}>
                  <View style={[styles.flex1, styles.m1]}>
                    <Button
                      title="Calculate distance"
                      onPress={calculateDistance}
                    />
                  </View>
                  <View style={[styles.flex1, styles.m1]}>
                    <Button title="Clear Map" color="red" onPress={clearMap} />
                  </View>
                </View>
              </View>
              <View style={[styles.flexRow, styles.p4]}>
                <View style={[styles.flex1, styles.m1]}>
                  <Button
                    color="green"
                    title="Enable Logger"
                    onPress={enableLogger}
                  />
                </View>
                <View style={[styles.flex1, styles.m1]}>
                  <Button
                    color="orange"
                    title="Disable Logger"
                    onPress={disableLogger}
                  />
                </View>
              </View>
              <View style={[styles.flex1, styles.m1]}>
                <Button title="Take snapshot" onPress={takeSnapshot} />
              </View>

              {this.state.showSnapshot && (
                <TouchableHighlight
                  onPress={() => this.setState({ showSnapshot: false })}
                >
                  <Image
                    style={styles.mapView}
                    source={{ uri: this.state.snapshotString }}
                  />
                </TouchableHighlight>
              )}
            </ScrollView>
          </SafeAreaView>
        </View>
      </SafeAreaView>
    );
  }
}
