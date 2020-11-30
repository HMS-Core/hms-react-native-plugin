/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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
  Switch,
  View,
  Text,
  Image,
  TextInput,
  TouchableHighlight,
  StatusBar,
  Button,
  PermissionsAndroid,
  ScrollView,
} from "react-native";

import HMSMap, {
  HMSCircle,
  HMSMarker,
  HMSPolygon,
  HMSPolyline,
  HMSGroundOverlay,
  HMSTileOverlay,
  HMSInfoWindow,
  MapTypes,
  PatternItemTypes,
  JointTypes,
  CapTypes,
  FillMode,
  Interpolator,
  RepeatMode,
} from "@hmscore/react-native-hms-map";

import mapStyleJson from "./mapStyle.json";

const base64String = "data:image/png;base64,";
let mapView;
let markerView;
let tileOverlayView;

const takeSnapshot = () => mapView.takeSnapshot();

const setCameraPosition = (lat, lng, zoom, tilt, bearing) => {
  console.log(lat, lng);
  mapView &&
    mapView.setCameraPosition({ target: { latitude: lat, longitude: lng }, zoom: zoom, tilt: tilt, bearing: bearing });
};
const updateCamera = () => {
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
  mapView.setCoordinates({ latitude: 41, longitude: 29 }, 12);
};
const getHuaweiMapInfo = () =>
  mapView &&
  mapView
    .getHuaweiMapInfo()
    .then((a) => console.log(a.visibleRegion))
    .catch((a) => console.log(a));

const clearMap = () => mapView && mapView.clear();

const getLayerOptionsInfo = () =>
  mapView &&
  markerView &&
  mapView
    .getLayerOptionsInfo(markerView)
    .then((a) => console.log(a))
    .catch((e) => console.log(e));

const getLayerInfo = () =>
  mapView &&
  markerView &&
  mapView
    .getLayerInfo(markerView)
    .then((a) => console.log(a))
    .catch((e) => console.log(e));

const showInfoWindow = () => markerView && markerView.showInfoWindow();

const hideInfoWindow = () => markerView && markerView.hideInfoWindow();

const getCoordinateFromPoint = () =>
  mapView &&
  mapView
    .getCoordinateFromPoint({ x: 100, y: 100 })
    .then((a) => console.log(a))
    .catch((a) => console.log(a));

const getPointFromCoordinate = () =>
  mapView &&
  mapView
    .getPointFromCoordinate({ latitude: 0, longitude: 0 })
    .then((a) => console.log(a))
    .catch((a) => console.log(a));

const stopAnimation = () => mapView.stopAnimation();

const animateMarker = () => {
  console.log("Animation Button");
  if (markerView) {
    markerView.setAnimation(
      {
        rotate: { fromDegree: 0, toDegree: 250, duration: 5000 },
        alpha: { fromAlpha: 0.5, toAlpha: 1 },
        scale: { fromX: 1, fromY: 1, toX: 3, toY: 3, duration: 6000, fillMode: FillMode.FORWARDS },
        translate: {
          latitude: 4.196762137072112,
          longitude: 15.686358445008585,
          duration: 1000,
          fillMode: FillMode.BACKWARDS,
          interpolator: Interpolator.BOUNCE,
          repeatCount: 3,
        },
      },
      { duration: 2000, repeatMode: RepeatMode.REVERSE }
    );
    markerView.startAnimation();
  }
};

const calculateDistance = () => {
  HMSMap.module
    .getDistance({ latitude: 41, longitude: 29 }, { latitude: 41, longitude: 28 })
    .then((a) => console.log(a))
    .catch((a) => console.log(a));
};

const resetMinMaxZoomPreference = () => mapView && mapView.resetMinMaxZoomPreference();

const clearTileCache = () => tileOverlayView && tileOverlayView.clearTileCache();

const enableLogger = () => HMSMap.module.enableLogger().then(() => console.log("Logger enabled"));

const disableLogger = () => HMSMap.module.disableLogger().then(() => console.log("Logger disabled"));

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
    fillColor={538066306} // transparent blue(0x20123D82)
    strokeWidth={10}
    strokeColor={-256} // yellow(0xFFFFFF00)
    strokePattern={[
      { type: PatternItemTypes.DASH, length: 20 },
      { type: PatternItemTypes.DOT },
      { type: PatternItemTypes.GAP, length: 20 },
    ]}
    visible={true}
    zIndex={2}
    onClick={(e) => console.log("Circle onClick")}
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
    onClick={(e) => console.log("Polygon onClick")}
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
    jointType={JointTypes.BEVEL}
    pattern={[{ type: PatternItemTypes.DASH, length: 20 }]}
    startCap={{ type: CapTypes.ROUND }}
    endCap={{
      type: CapTypes.CUSTOM,
      refWidth: 1000,
      asset: "ic_launcher.png", // under assets folder
    }}
    visible={true}
    width={20.0}
    zIndex={2}
    onClick={(e) => console.log("Polyline onClick")}
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

const GroundOverlayComplex = () => (
  <HMSGroundOverlay
    image={{
      asset: "ic_launcher.png", // under assets folder
    }}
    coordinate={{ latitude: 0, longitude: -10, height: 1000000, width: 1000000 }}
    anchor={[0.5, 0.5]}
    bearing={220}
    clickable={true}
    transparency={0.5}
    visible={true}
    zIndex={3}
    onClick={(e) => console.log("GroundOverlay onClick e:", e.nativeEvent)}
  />
);
const MarkerComplex = (props) => (
  <HMSMarker
    onAnimationStart={(e) => console.log(`Animation ${e.nativeEvent.type} Started`)}
    onAnimationEnd={(e) => console.log(`Animation ${e.nativeEvent.type} Ended in ${e.nativeEvent.duration} ms`)}
    coordinate={{ latitude: -10, longitude: 0 }}
    draggable={true}
    flat={true}
    icon={{
      asset: "ic_launcher.png", // under assets folder
    }}
    alpha={0.8}
    defaultActionOnClick={props.isDefaultAction}
    markerAnchor={[0.5, 0.5]}
    infoWindowAnchor={[0.5, 0.5]}
    rotation={30.0}
    visible={true}
    zIndex={0}
    clusterable={false}
    onClick={(e) => console.log("Marker onClick")}
    onDragStart={(e) => console.log("Marker onDragStart")}
    onDrag={(e) => console.log("Marker onDrag")}
    onDragEnd={(e) => console.log("Marker onDragEnd")}
    onInfoWindowClick={(e) => console.log("Marker onInfoWindowClick")}
    onInfoWindowClose={(e) => console.log("Marker onInfoWindowClose")}
    onInfoWindowLongClick={(e) => console.log("Marker onInfoWindowLongClick")}
    ref={(e) => {
      markerView = e;
    }}
  >
    <HMSInfoWindow>
      <View style={{ backgroundColor: "transparent" }}>
        <View style={{ backgroundColor: "rgb(49,49,49)", borderRadius: 6, paddingHorizontal: 14, paddingVertical: 6 }}>
          <Text style={{ color: "#fff", fontFamily: "Muli", fontSize: 12 }}> This is a custom window</Text>
        </View>
        <View
          style={{
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
          }}
        />
      </View>
    </HMSInfoWindow>
  </HMSMarker>
);

const MarkerSimple = (props) => (
  <HMSMarker
    icon={{ hue: (props.markerRow * props.col + props.row) * 30 }}
    coordinate={{ latitude: props.col, longitude: props.row }}
    title="Hello"
    snippet={"My lat:" + props.col + " lon:" + props.row}
    clusterable={true}
  />
);

const TileOverlayComplex = (props) => (
  <HMSTileOverlay
    // tileProvider={{ 
    //   url: "https://a.tile.openstreetmap.org/{z}/{x}/{y}.png",    
    //   zoom: [4,5,6]
    //  }}
    tileProvider={[
      {
          "x": 3,
          "y": 4,
          "zoom": 3,
          "asset": "ic_launcher.png"
      }
      , {
          "x": 4,
          "y": 3,
          "zoom": 3,
          "asset": "ic_launcher.png"
      }
      , {
          "x": 4,
          "y": 4,
          "zoom": 3,
          "asset": "ic_launcher.png"
      }
  ]}
    visible={props.showTileOvelay}
    fadeIn={false}
    zIndex={10}
    transparency={0.2}
    ref={(el) => (tileOverlayView = el)}
  />
);

class App extends React.Component {
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
            camera={{ target: { latitude: 0, longitude: 0 }, zoom: 3, bearing: 50, tilt: 80 }}
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
            mapStyle={this.state.stylingMap ? JSON.stringify(mapStyleJson) : ""}
            myLocationEnabled={this.state.myLocationEnabled}
            mapPadding={{ right: 100, left: 10, top: 10, bottom: 10 }}
            markerClustering={this.state.markerClustering}
            myLocationButtonEnabled={this.state.myLocationButtonEnabled}
            scrollGesturesEnabledDuringRotateOrZoom={this.state.scrollGesturesEnabledDuringRotateOrZoom}
            onCameraUpdateFinished={(e) => console.log("HMSMap onCameraUpdateFinished")}
            onCameraUpdateCanceled={(e) => console.log("HMSMap onCameraUpdateCanceled")}
            onCameraIdle={(e) => {
              console.log("HMSMap onCameraIdle, result", e.nativeEvent);
              const cameraPosition = e.nativeEvent;
              this.setState({ zoom: parseFloat(cameraPosition.zoom.toFixed(2)) });
              this.setState({ lat: parseFloat(cameraPosition.target.latitude.toFixed(5)) });
              this.setState({ lng: parseFloat(cameraPosition.target.longitude.toFixed(5)) });
              this.setState({ bearing: parseFloat(cameraPosition.bearing.toFixed(2)) });
              this.setState({ tilt: parseFloat(cameraPosition.tilt.toFixed(2)) });
            }}
            onMapReady={(e) => console.log("HMSMap onMapReady")}
            onCameraMoveCanceled={(e) => console.log("HMSMap onCameraMoveCanceled")}
            onCameraMove={(e) => console.log("HMSMap onCameraMove result", e.nativeEvent)}
            onCameraMoveStarted={(e) => console.log("HMSMap onCameraMoveStarted, result", e.nativeEvent)}
            onMapClick={(e) => console.log("HMSMap onMapClick, result", e.nativeEvent)}
            onMapLoaded={(e) => console.log("HMSMap onMapLoaded")}
            onMapLongClick={(e) => console.log("HMSMap onMapLongClick, result", e.nativeEvent)}
            onMyLocationButtonClick={(e) => console.log("HMSMap onMyLocationButtonClick")}
            onMyLocationClick={(e) => console.log("HMSMap onMyLocationClick")}
            onPoiClick={(e) => console.log("HMSMap onPoiClick, result", e.nativeEvent)}
            onSnapshotReady={(e) => {
              console.log("HMSMap onSnapshotReady");
              this.setState({ showSnapshot: true });
              this.setState({ snapshotString: base64String + e.nativeEvent.bitmap });
            }}
            ref={(e) => {
              mapView = e;
            }}
          >
            <MarkerComplex isDefaultAction={this.state.isDefaultAction} />
            {[...Array(this.state.markerCol).keys()].map((col) =>
              [...Array(this.state.markerRow).keys()].map((row) => (
                <MarkerSimple
                  key={this.state.markerRow * col + row}
                  markerRow={this.state.markerRow}
                  col={col}
                  row={row}
                />
              ))
            )}
            <TileOverlayComplex showTileOvelay={this.state.showTileOvelay} />
            <CircleComplex />
            <PolygonComplex />
            <PolylineComplex />
            <GroundOverlaySimple />
            <GroundOverlayComplex />
          </HMSMap>
          <SafeAreaView style={{ height: 400 }}>
            <ScrollView>
              <ScrollView horizontal={true} style={styles.flexRow}>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Map Type">Map Type</Text>
                  <Switch
                    onValueChange={() => this.setState({ mapType: 1 - this.state.mapType })}
                    value={this.state.mapType === MapTypes.NORMAL}
                  />
                </View>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Map Style">Map Style</Text>
                  <Switch
                    onValueChange={() => this.setState({ stylingMap: !this.state.stylingMap })}
                    value={this.state.stylingMap}
                  />
                </View>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Tile Overlay">Tile Overlay</Text>
                  <Switch
                    onValueChange={() => this.setState({ showTileOvelay: !this.state.showTileOvelay })}
                    value={this.state.showTileOvelay}
                  />
                </View>
              </ScrollView>
              <ScrollView horizontal={true} style={styles.flexRow}>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="My Location">My Location</Text>
                  <Switch
                    onValueChange={() => {
                      if (this.state.myLocationEnabled) {
                        this.setState({ myLocationEnabled: false });
                        this.setState({ myLocationButtonEnabled: false });
                      } else {
                        PermissionsAndroid.check(PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION).then((res) => {
                          res
                            ? this.setState({ myLocationEnabled: true })
                            : PermissionsAndroid.request(PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION).then(
                                (granted) =>
                                  PermissionsAndroid.RESULTS.GRANTED === granted &&
                                  this.setState({ myLocationEnabled: true })
                              );
                        });
                      }
                    }}
                    value={this.state.myLocationEnabled}
                  />
                </View>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="My Location Button">My Location Button</Text>
                  <Switch
                    onValueChange={() =>
                      this.setState({ myLocationButtonEnabled: !this.state.myLocationButtonEnabled })
                    }
                    value={this.state.myLocationButtonEnabled}
                  />
                </View>
              </ScrollView>
              <ScrollView horizontal={true} style={styles.flexRow}>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Compass">Compass</Text>
                  <Switch
                    onValueChange={() => this.setState({ compassEnabled: !this.state.compassEnabled })}
                    value={this.state.compassEnabled}
                  />
                </View>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Zoom control">Zoom control</Text>
                  <Switch
                    onValueChange={() => this.setState({ zoomControlsEnabled: !this.state.zoomControlsEnabled })}
                    value={this.state.zoomControlsEnabled}
                  />
                </View>
              </ScrollView>
              <ScrollView horizontal={true} style={styles.flexRow}>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Use Animation">Use Animation</Text>
                  <Switch
                    onValueChange={() => this.setState({ useAnimation: !this.state.useAnimation })}
                    value={this.state.useAnimation}
                  />
                </View>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Defatult Marker Click Action">Defatult Marker Click Action</Text>
                  <Switch
                    onValueChange={() => this.setState({ isDefaultAction: !this.state.isDefaultAction })}
                    value={this.state.isDefaultAction}
                  />
                </View>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Clustering">Clustering</Text>
                  <Switch
                    onValueChange={() => this.setState({ markerClustering: !this.state.markerClustering })}
                    value={this.state.markerClustering}
                  />
                </View>
              </ScrollView>
              <ScrollView horizontal={true} style={styles.flexRow}>
                <View style={[styles.flexRow, styles.flex2]}>
                  <Text title="Scroll gesture during rotate or zoom">Scroll gesture during rotate or zoom</Text>
                  <Switch
                    onValueChange={() =>
                      this.setState({
                        scrollGesturesEnabledDuringRotateOrZoom: !this.state.scrollGesturesEnabledDuringRotateOrZoom,
                      })
                    }
                    value={this.state.scrollGesturesEnabledDuringRotateOrZoom}
                  />
                </View>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Zoom gesture">Zoom gesture</Text>
                  <Switch
                    onValueChange={() => this.setState({ zoomGesturesEnabled: !this.state.zoomGesturesEnabled })}
                    value={this.state.zoomGesturesEnabled}
                  />
                </View>
              </ScrollView>
              <ScrollView horizontal={true} style={styles.flexRow}>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Rotate gesture">Rotate gesture</Text>
                  <Switch
                    onValueChange={() => this.setState({ rotateGesturesEnabled: !this.state.rotateGesturesEnabled })}
                    value={this.state.rotateGesturesEnabled}
                  />
                </View>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Scroll gesture">Scroll gesture</Text>
                  <Switch
                    onValueChange={() => this.setState({ scrollGesturesEnabled: !this.state.scrollGesturesEnabled })}
                    value={this.state.scrollGesturesEnabled}
                  />
                </View>
                <View style={[styles.flexRow, styles.flex1]}>
                  <Text title="Tilt gesture">Tilt gesture</Text>
                  <Switch
                    onValueChange={() => this.setState({ tiltGesturesEnabled: !this.state.tiltGesturesEnabled })}
                    value={this.state.tiltGesturesEnabled}
                  />
                </View>
              </ScrollView>
              <ScrollView horizontal={true} style={styles.flexRow}>
                <View style={[styles.flexCol, styles.width100]}>
                  <View style={styles.flexRow}>
                    <Text title="Min" style={styles.width40}>
                      Min
                    </Text>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button
                        color="red"
                        onPress={() =>
                          this.state.minZoomPreference > 0 &&
                          this.setState({ minZoomPreference: this.state.minZoomPreference - 1 })
                        }
                        title={"-"}
                      />
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Text>{"" + this.state.minZoomPreference}</Text>
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button
                        color="green"
                        onPress={() =>
                          this.state.minZoomPreference < this.state.zoom &&
                          this.setState({ minZoomPreference: this.state.minZoomPreference + 1 })
                        }
                        title={"+"}
                      />
                    </View>
                  </View>
                  <View style={styles.flexRow}>
                    <Text title="Zoom" style={styles.width40}>
                      Zoom
                    </Text>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button
                        color="red"
                        onPress={() => {
                          if (this.state.zoom > this.state.minZoomPreference) {
                            this.setState({ zoom: this.state.zoom - 1 });
                            mapView.zoomOut();
                          }
                        }}
                        title={"-"}
                      />
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Text>{"" + this.state.zoom}</Text>
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button
                        color="green"
                        onPress={() => {
                          if (this.state.zoom < this.state.maxZoomPreference) {
                            this.setState({ zoom: this.state.zoom + 1 });
                            mapView.zoomIn();
                          }
                        }}
                        title={"+"}
                      />
                    </View>
                  </View>
                  <View style={styles.flexRow}>
                    <Text title="Min" style={styles.width40}>
                      Max
                    </Text>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button
                        color="red"
                        onPress={() =>
                          this.state.maxZoomPreference > this.state.zoom &&
                          this.setState({ maxZoomPreference: this.state.maxZoomPreference - 1 })
                        }
                        title={"-"}
                      />
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Text>{"" + this.state.maxZoomPreference}</Text>
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button
                        color="green"
                        onPress={() =>
                          this.state.maxZoomPreference < 20 &&
                          this.setState({ maxZoomPreference: this.state.maxZoomPreference + 1 })
                        }
                        title={"+"}
                      />
                    </View>
                  </View>
                </View>
                <View style={[styles.flexCol, styles.width30]} />
                <View style={[styles.flexCol, styles.width100]}>
                  <View style={styles.flexRow}>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button onPress={() => mapView.scrollBy(-this.state.pixel, this.state.pixel)} title={"⬉"} />
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button onPress={() => mapView.scrollBy(0, this.state.pixel)} title={"⬆"} />
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button onPress={() => mapView.scrollBy(this.state.pixel, this.state.pixel)} title={"⬈"} />
                    </View>
                  </View>
                  <View style={styles.flexRow}>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button onPress={() => mapView.scrollBy(-this.state.pixel, 0)} title={"⬅"} />
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <TextInput
                        keyboardType="number-pad"
                        value={"" + this.state.pixel}
                        onChangeText={(x) => this.setState({ pixel: parseInt(x, 10) })}
                      />
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button onPress={() => mapView.scrollBy(this.state.pixel, 0)} title={"➝"} />
                    </View>
                  </View>
                  <View style={styles.flexRow}>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button onPress={() => mapView.scrollBy(-this.state.pixel, -this.state.pixel)} title={"⬋"} />
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button onPress={() => mapView.scrollBy(0, -this.state.pixel)} title={"⬇"} />
                    </View>
                    <View style={[styles.flexRow, styles.flex1]}>
                      <Button onPress={() => mapView.scrollBy(this.state.pixel, -this.state.pixel)} title={"⬊"} />
                    </View>
                  </View>
                </View>
                <View style={[styles.flexCol]}>
                  <View style={[styles.flexRow, styles.flex1]}>
                    <View>
                      <Text title="Lat">Lat</Text>
                      <TextInput
                        keyboardType="number-pad"
                        value={"" + this.state.lat}
                        onChangeText={(r) => r !== "" && this.setState({ lat: parseFloat(r) })}
                      />
                    </View>
                    <View>
                      <Text title="Lng">Lng</Text>
                      <TextInput
                        keyboardType="number-pad"
                        value={"" + this.state.lng}
                        onChangeText={(r) => r !== "" && this.setState({ lng: parseFloat(r) })}
                      />
                    </View>
                  </View>
                  <View style={[styles.flexRow, styles.flex1]}>
                    <View>
                      <Text title="Lng">Tilt</Text>
                      <TextInput
                        keyboardType="number-pad"
                        value={"" + this.state.tilt}
                        onChangeText={(r) => r !== "" && this.setState({ tilt: parseFloat(r) })}
                      />
                    </View>
                    <View>
                      <Text title="Bearing">Bearing</Text>
                      <TextInput
                        keyboardType="number-pad"
                        value={"" + this.state.bearing}
                        onChangeText={(r) => r !== "" && this.setState({ bearing: parseFloat(r) })}
                      />
                    </View>

                    <Button
                      onPress={() =>
                        setCameraPosition(
                          this.state.lat,
                          this.state.lng,
                          this.state.zoom,
                          this.state.tilt,
                          this.state.bearing
                        )
                      }
                      title={"Go"}
                    />
                  </View>
                </View>
              </ScrollView>
              <View>
                <View style={{ display: "flex", flexDirection: "row" }}>
                  <Button title="Take snapshot" onPress={takeSnapshot} />
                </View>
                {this.state.showSnapshot && (
                  <TouchableHighlight onPress={() => this.setState({ showSnapshot: false })}>
                    <Image style={styles.mapView} source={{ uri: this.state.snapshotString }} />
                  </TouchableHighlight>
                )}
                <View style={styles.flexRow}>
                  <Button color="green" title="Update Camera" onPress={updateCamera} />
                  <Button title="Stop Animation" onPress={stopAnimation} />
                </View>
                <View style={styles.flexRow}>
                  <Button title="Get Map Info" onPress={getHuaweiMapInfo} />
                  <Button title="Layer Info" color="green" onPress={getLayerInfo} />
                  <Button title="Layer Options Info" onPress={getLayerOptionsInfo} />
                  <Button title="Clear Map" color="red" onPress={clearMap} />
                </View>
                <View style={styles.flexRow}>
                  <Button title="Show Info Window" color="green" onPress={showInfoWindow} />
                  <Button title="Hide Info Window" color="purple" onPress={hideInfoWindow} />
                </View>
                <View style={styles.flexRow}>
                  <Button
                    title="Remove marker"
                    color="red"
                    onPress={() => this.setState({ markerRow: this.state.markerRow - 1 })}
                  />
                  <Button title="Add marker" onPress={() => this.setState({ markerRow: this.state.markerRow + 1 })} />
                </View>
                <View style={styles.flexRow}>
                  <Button color="purple" title="Get Coord. from Point" onPress={getCoordinateFromPoint} />
                  <Button color="green" title="Get Point From Coord." onPress={getPointFromCoordinate} />
                </View>
                <View style={{ flexDirection: "row" }}>
                  <Button color="green" title="Animate Marker" onPress={animateMarker} />
                  <Button title="Calculate distance" onPress={calculateDistance} />
                </View>
                <View style={styles.flexRow}>
                  <Button title="Reset Zoom Preference" color="red" onPress={resetMinMaxZoomPreference} />
                  <Button title="Clear Tile Cache" onPress={clearTileCache} />
                </View>
                <View style={styles.flexRow}>
                  <Button color="green" title="Enable Logger" onPress={enableLogger} />
                  <Button color="orange" title="Disable Logger" onPress={disableLogger} />
                </View>
              </View>
            </ScrollView>
          </SafeAreaView>
        </View>
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  flexRow: { flexDirection: "row" },
  flexCol: { flexDirection: "column" },
  flex1: { flex: 1 },
  flex2: { flex: 2 },
  width30: { width: 30 },
  width40: { width: 40 },
  width100: { width: 100 },
  mapView: { height: 300, backgroundColor: "red" },
  snapView: { height: 200, backgroundColor: "yellow" },
  infoWindow: { backgroundColor: "white", alignSelf: "baseline" },
  container: { flexDirection: "column", alignSelf: "flex-start" },
});

export default App;
