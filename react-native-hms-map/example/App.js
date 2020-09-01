/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

import React, {useState, useEffect} from "react";
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
  Picker,
  ToastAndroid,
  PermissionsAndroid,
  ScrollView,
} from "react-native";

import MapView, {
  Circle,
  Marker,
  Polygon,
  Polyline,
  GroundOverlay,
  TileOverlay,
  InfoWindow,
  MapTypes,
  PatternItemTypes,
  JointTypes,
  CapTypes,
} from "@hmscore/react-native-hms-map";

import mapStyleJson from "./mapStyle.json";

const toast = (val) => {
  ToastAndroid.show(val, ToastAndroid.SHORT);
};

const MapViewExample = () => {
  const [zoom, setZoom] = useState(3);
  const [lat, setLat] = useState(0.0);
  const [lng, setLng] = useState(0.0);
  const [bearing, setBearing] = useState(0.0);
  const [tilt, setTilt] = useState(0.0);
  const [compassEnabled, setCompassEnabled] = useState(true);
  const [mapType, setMapType] = useState(1); // Just use normal(1) and empty(0) map
  const [minZoomPreference, setMinZoomPreference] = useState(3);
  const [maxZoomPreference, setMaxZoomPreference] = useState(20);
  const [rotateGesturesEnabled, setRotateGesturesEnabled] = useState(true);
  const [scrollGesturesEnabled, setScrollGesturesEnabled] = useState(true);
  const [tiltGesturesEnabled, setTiltGesturesEnabled] = useState(true);
  const [zoomControlsEnabled, setZoomControlsEnabled] = useState(true);
  const [zoomGesturesEnabled, setZoomGesturesEnabled] = useState(true);
  const [myLocationEnabled, setMyLocationEnabled] = useState(false);
  const [myLocationButtonEnabled, setMyLocationButtonEnabled] = useState(false);
  const [markerClustering, setMarkerClustering] = useState(false);
  const [
    scrollGesturesEnabledDuringRotateOrZoom,
    setScrollGesturesEnabledDuringRotateOrZoom,
  ] = useState(true);
  const [stylingMap, setStylingMap] = useState(false);
  const [useAnimation, setUseAnimation] = useState(true);
  const [markerCol, setMarkerCol] = useState(2);
  const [markerRow, setMarkerRow] = useState(5);
  const [showTileOvelay, setShowTileOvelay] = useState(false);
  const [locationPermission, setLocationPermission] = useState(false);
  const [pixel, setPixel] = useState(100);
  const base64String = "data:image/png;base64,";
  const [showSnapshot, setShowSnapshot] = useState(false);
  const [snapshotString, setSnapshotString] = useState(base64String);

  let mapView;
  let markerView;
  let tileOverlayView;

  PermissionsAndroid.check(
    PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
  ).then((res) => !locationPermission && setLocationPermission(res));
  PermissionsAndroid.check(
    PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION,
  ).then((res) => !locationPermission && setLocationPermission(res));
  return (
    <>
      <View>
        <MapView
          style={styles.mapView}
          camera={{
            target: {latitude: 0, longitude: 0},
            zoom: 3,
            bearing: 50,
            tilt: 80,
          }}
          latLngBoundsForCameraTarget={[
            {latitude: -20, longitude: -20},
            {latitude: 20, longitude: 20},
          ]}
          useAnimation={useAnimation}
          animationDuration={2000}
          compassEnabled={compassEnabled}
          mapType={mapType}
          minZoomPreference={minZoomPreference}
          maxZoomPreference={maxZoomPreference}
          rotateGesturesEnabled={rotateGesturesEnabled}
          scrollGesturesEnabled={scrollGesturesEnabled}
          tiltGesturesEnabled={tiltGesturesEnabled}
          zOrderOnTop={false}
          zoomControlsEnabled={zoomControlsEnabled}
          zoomGesturesEnabled={zoomGesturesEnabled}
          buildingsEnabled={true}
          description="Huawei Map"
          mapStyle={stylingMap ? JSON.stringify(mapStyleJson) : ""}
          myLocationEnabled={myLocationEnabled}
          mapPadding={{right: 100, left: 10, top: 10, bottom: 10}}
          markerClustering={markerClustering}
          myLocationButtonEnabled={myLocationButtonEnabled}
          scrollGesturesEnabledDuringRotateOrZoom={
            scrollGesturesEnabledDuringRotateOrZoom
          }
          onCameraUpdateFinished={(e) =>
            console.log("MapView onCameraUpdateFinished")
          }
          onCameraUpdateCanceled={(e) =>
            console.log("MapView onCameraUpdateCanceled")
          }
          onCameraIdle={(e) => {
            console.log("MapView onCameraIdle, result", e.nativeEvent);
            const cameraPosition = e.nativeEvent;
            setZoom(parseFloat(cameraPosition.zoom.toFixed(2)));
            setLat(parseFloat(cameraPosition.target.latitude.toFixed(5)));
            setLng(parseFloat(cameraPosition.target.longitude.toFixed(5)));
            setBearing(parseFloat(cameraPosition.bearing.toFixed(2)));
            setTilt(parseFloat(cameraPosition.tilt.toFixed(2)));
          }}
          onMapReady={(e) => console.log("MapView onMapReady")}
          onCameraMoveCanceled={(e) =>
            console.log("MapView onCameraMoveCanceled")
          }
          onCameraMove={(e) => {
            console.log("MapView onCameraMove result", e.nativeEvent);
          }}
          onCameraMoveStarted={(e) =>
            console.log("MapView onCameraMoveStarted, result", e.nativeEvent)
          }
          onMapClick={(e) =>
            console.log("MapView onMapClick, result", e.nativeEvent)
          }
          onMapLoaded={(e) => console.log("MapView onMapLoaded")}
          onMapLongClick={(e) =>
            console.log("MapView onMapLongClick, result", e.nativeEvent)
          }
          onMyLocationButtonClick={(e) =>
            console.log("MapView onMyLocationButtonClick")
          }
          onMyLocationClick={(e) => console.log("MapView onMyLocationClick")}
          onPoiClick={(e) =>
            console.log("MapView onPoiClick, result", e.nativeEvent)
          }
          onSnapshotReady={(e) => {
            console.log("MapView onSnapshotReady");
            setShowSnapshot(true);
            setSnapshotString(base64String + e.nativeEvent.bitmap);
          }}
          ref={(e) => {
            mapView = e;
          }}>
          <Circle
            center={{latitude: 10, longitude: 0}}
            radius={900000}
            clickable={true}
            fillColor={538066306} // transparent blue(0x20123D82)
            strokeWidth={10}
            strokeColor={-256} // yellow(0xFFFFFF00)
            strokePattern={[
              {type: PatternItemTypes.DASH, length: 20},
              {type: PatternItemTypes.DOT},
              {type: PatternItemTypes.GAP, length: 20},
            ]}
            visible={true}
            zIndex={2}
            onClick={(e) => console.log("Circle onClick")}
          />

          <Polygon
            points={[
              {latitude: 10.5, longitude: 18.5},
              {latitude: 0.5, longitude: 18.5},
              {latitude: 0.5, longitude: 9.5},
              {latitude: 10.5, longitude: 9.5},
            ]}
            holes={[
              [
                {latitude: 5.5, longitude: 13.5},
                {latitude: 3.5, longitude: 13.5},
                {latitude: 3.5, longitude: 15.5},
              ],
              [
                {latitude: 6.5, longitude: 18.0},
                {latitude: 8.5, longitude: 18.0},
                {latitude: 8.5, longitude: 16.5},
              ],
            ]}
            clickable={true}
            geodesic={true}
            fillColor={538066306} // transparent blue(0x20123D82)
            strokeColor={-256} // yellow(0xFFFFFF00)
            strokeJointType={JointTypes.BEVEL}
            strokePattern={[
              {type: PatternItemTypes.DASH, length: 20},
              {type: PatternItemTypes.DOT},
              {type: PatternItemTypes.GAP, length: 20},
            ]}
            zIndex={2}
            onClick={(e) => console.log("Polygon onClick")}
          />

          <Polyline
            points={[
              {latitude: -10, longitude: -10},
              {latitude: -15, longitude: -10},
              {latitude: -10, longitude: -15},
            ]}
            clickable={true}
            geodesic={true}
            color={538066306} // transparent blue(0x20123D82)
            jointType={JointTypes.BEVEL}
            pattern={[{type: PatternItemTypes.DASH, length: 20}]}
            startCap={{type: CapTypes.ROUND}}
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

          <Marker
            coordinate={{latitude: -10, longitude: 0}}
            draggable={true}
            flat={true}
            icon={{
              asset: "ic_launcher.png", // under assets folder
            }}
            alpha={0.8}
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
            onInfoWindowLongClick={(e) =>
              console.log("Marker onInfoWindowLongClick")
            }
            ref={(e) => {
              markerView = e;
            }}>
            <InfoWindow>
              <Text>Hello</Text>
              <Text>I am a marker</Text>
            </InfoWindow>
          </Marker>
          {[...Array(markerCol).keys()].map((col) =>
            [...Array(markerRow).keys()].map((row) => (
              <Marker
                key={markerRow * col + row}
                icon={{hue: (markerRow * col + row) * 30}}
                coordinate={{latitude: col, longitude: row}}
                title="Hello"
                snippet={"My lat:" + col + " lon:" + row}
                clusterable={true}
              />
            )),
          )}

          <GroundOverlay
            image={{
              asset: "ic_launcher.png", // under assets folder
            }}
            coordinate={{
              latitude: 0,
              longitude: -10,
              height: 1000000,
              width: 1000000,
            }}
            anchor={[0.5, 0.5]}
            bearing={220}
            clickable={true}
            transparency={0.5}
            visible={true}
            zIndex={3}
            onClick={(e) =>
              console.log("GroundOverlay onClick e:", e.nativeEvent)
            }
          />
          <GroundOverlay
            image={{
              // hue: 30.0,
              asset: "ic_launcher.png", // under assets folder
              // path on the device
              // path:
              //   "/data/data/com.huawei.rnhmsmapdemo/files/map-style/img/native_dianhua_dire_arrow.png",
              // file: 'filename',
            }}
            coordinate={[
              {
                latitude: -10,
                longitude: 10,
              },
              {
                latitude: -10,
                longitude: 20,
              },
              {
                latitude: -25,
                longitude: 10,
              },
            ]}
          />
          <TileOverlay
            tileProvider={{
              url: "https://a.tile.openstreetmap.org/{z}/{x}/{y}.png",
            }}
            visible={showTileOvelay}
            fadeIn={false}
            zIndex={10}
            transparency={0.2}
            ref={(el) => (tileOverlayView = el)}
          />
        </MapView>
        <View style={styles.flexRow}>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="Map Type">Map Type</Text>
            <Switch
              onValueChange={() => setMapType(1 - mapType)}
              value={mapType === MapTypes.NORMAL}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="Map Style">Map Style</Text>
            <Switch
              onValueChange={() => setStylingMap(!stylingMap)}
              value={stylingMap}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="Tile Overlay">Tile Overlay</Text>
            <Switch
              onValueChange={() => setShowTileOvelay(!showTileOvelay)}
              value={showTileOvelay}
            />
          </View>
        </View>
        <View style={styles.flexRow}>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="My Location">My Location</Text>
            <Switch
              onValueChange={() =>
                setMyLocationEnabled(
                  myLocationEnabled ? false : locationPermission,
                )
              }
              value={myLocationEnabled}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="My Location Button">My Location Button</Text>
            <Switch
              onValueChange={() =>
                setMyLocationButtonEnabled(!myLocationButtonEnabled)
              }
              value={myLocationButtonEnabled}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="Use Animation">Use Animation</Text>
            <Switch
              onValueChange={() => setUseAnimation(!useAnimation)}
              value={useAnimation}
            />
          </View>
        </View>
        <View style={styles.flexRow}>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="Clustering">Clustering</Text>
            <Switch
              onValueChange={() => setMarkerClustering(!markerClustering)}
              value={markerClustering}
            />
          </View>
          <View style={[styles.flexRow, styles.flex2]}>
            <Text title="Scroll gesture during rotate or zoom">
              Scroll gesture during rotate or zoom
            </Text>
            <Switch
              onValueChange={() =>
                setScrollGesturesEnabledDuringRotateOrZoom(
                  !scrollGesturesEnabledDuringRotateOrZoom,
                )
              }
              value={scrollGesturesEnabledDuringRotateOrZoom}
            />
          </View>
        </View>
        <View style={styles.flexRow}>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="Rotate gesture">Rotate gesture</Text>
            <Switch
              onValueChange={() =>
                setRotateGesturesEnabled(!rotateGesturesEnabled)
              }
              value={rotateGesturesEnabled}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="Scroll gesture">Scroll gesture</Text>
            <Switch
              onValueChange={() =>
                setScrollGesturesEnabled(!scrollGesturesEnabled)
              }
              value={scrollGesturesEnabled}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="Tilt gesture">Tilt gesture</Text>
            <Switch
              onValueChange={() => setTiltGesturesEnabled(!tiltGesturesEnabled)}
              value={tiltGesturesEnabled}
            />
          </View>
        </View>
        <View style={styles.flexRow}>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="Compass">Compass</Text>
            <Switch
              onValueChange={() => setCompassEnabled(!compassEnabled)}
              value={compassEnabled}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="Zoom gesture">Zoom gesture</Text>
            <Switch
              onValueChange={() => setZoomGesturesEnabled(!zoomGesturesEnabled)}
              value={zoomGesturesEnabled}
            />
          </View>
          <View style={[styles.flexRow, styles.flex1]}>
            <Text title="Zoom control">Zoom control</Text>
            <Switch
              onValueChange={() => setZoomControlsEnabled(!zoomControlsEnabled)}
              value={zoomControlsEnabled}
            />
          </View>
        </View>
        <View style={styles.flexRow}>
          <View style={[styles.flexCol, styles.width100]}>
            <View style={styles.flexRow}>
              <Text title="Min" style={styles.width40}>
                Min
              </Text>
              <View style={[styles.flexRow, styles.flex1]}>
                <Button
                  color="red"
                  onPress={() =>
                    minZoomPreference > 0 &&
                    setMinZoomPreference(minZoomPreference - 1)
                  }
                  title={"-"}
                />
              </View>
              <View style={[styles.flexRow, styles.flex1]}>
                <Text>{"" + minZoomPreference}</Text>
              </View>
              <View style={[styles.flexRow, styles.flex1]}>
                <Button
                  color="green"
                  onPress={() =>
                    minZoomPreference < zoom &&
                    setMinZoomPreference(minZoomPreference + 1)
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
                    if (zoom > minZoomPreference) {
                      setZoom(zoom - 1);
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
                    if (zoom < maxZoomPreference) {
                      setZoom(zoom + 1);
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
                    maxZoomPreference > zoom &&
                    setMaxZoomPreference(maxZoomPreference - 1)
                  }
                  title={"-"}
                />
              </View>
              <View style={[styles.flexRow, styles.flex1]}>
                <Text>{"" + maxZoomPreference}</Text>
              </View>
              <View style={[styles.flexRow, styles.flex1]}>
                <Button
                  color="green"
                  onPress={() =>
                    maxZoomPreference < 20 &&
                    setMaxZoomPreference(maxZoomPreference + 1)
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
                <Button
                  onPress={() => mapView.scrollBy(-pixel, pixel)}
                  title={"⬉"}
                />
              </View>
              <View style={[styles.flexRow, styles.flex1]}>
                <Button
                  onPress={() => mapView.scrollBy(0, pixel)}
                  title={"⬆"}
                />
              </View>
              <View style={[styles.flexRow, styles.flex1]}>
                <Button
                  onPress={() => mapView.scrollBy(pixel, pixel)}
                  title={"⬈"}
                />
              </View>
            </View>
            <View style={styles.flexRow}>
              <View style={[styles.flexRow, styles.flex1]}>
                <Button
                  onPress={() => mapView.scrollBy(-pixel, 0)}
                  title={"⬅"}
                />
              </View>
              <View style={[styles.flexRow, styles.flex1]}>
                <TextInput
                  keyboardType="number-pad"
                  value={"" + pixel}
                  onChangeText={(x) => setPixel(parseInt(x, 10))}
                />
              </View>
              <View style={[styles.flexRow, styles.flex1]}>
                <Button
                  onPress={() => mapView.scrollBy(pixel, 0)}
                  title={"➝"}
                />
              </View>
            </View>
            <View style={styles.flexRow}>
              <View style={[styles.flexRow, styles.flex1]}>
                <Button
                  onPress={() => mapView.scrollBy(-pixel, -pixel)}
                  title={"⬋"}
                />
              </View>
              <View style={[styles.flexRow, styles.flex1]}>
                <Button
                  onPress={() => mapView.scrollBy(0, -pixel)}
                  title={"⬇"}
                />
              </View>
              <View style={[styles.flexRow, styles.flex1]}>
                <Button
                  onPress={() => mapView.scrollBy(pixel, -pixel)}
                  title={"⬊"}
                />
              </View>
            </View>
          </View>
          <View style={[styles.flexCol, styles.width100]}>
            <View style={[styles.flexRow, styles.flex1]}>
              <Text title="Lat">Lat</Text>
              <TextInput
                keyboardType="number-pad"
                value={"" + lat}
                onChangeText={(r) => r !== "" && setLat(parseFloat(r))}
              />
              <Text title="Lng">Lng</Text>
              <TextInput
                keyboardType="number-pad"
                value={"" + lng}
                onChangeText={(r) => r !== "" && setLng(parseFloat(r))}
              />
            </View>
            <View style={[styles.flexRow, styles.flex1]}>
              <Text title="Lng">Tilt</Text>
              <TextInput
                keyboardType="number-pad"
                value={"" + tilt}
                onChangeText={(r) => r !== "" && setTilt(parseFloat(r))}
              />
              <Text title="Bearing">Bearing</Text>
              <TextInput
                keyboardType="number-pad"
                value={"" + bearing}
                onChangeText={(r) => r !== "" && setBearing(parseFloat(r))}
              />
              <Button
                onPress={() => {
                  console.log(lat, lng);
                  mapView.setCameraPosition({
                    target: {latitude: lat, longitude: lng},
                    zoom: zoom,
                    tilt: tilt,
                    bearing: bearing,
                  });
                }}
                title={"Go"}
              />
            </View>
          </View>
        </View>
        <Button
          title="Take snapshot"
          onPress={() => {
            mapView.takeSnapshot();
          }}
        />
        {showSnapshot && (
          <TouchableHighlight onPress={() => setShowSnapshot(false)}>
            <Image
              style={styles.mapView}
              source={{
                uri: snapshotString,
              }}
            />
          </TouchableHighlight>
        )}

        <View style={styles.flexRow}>
          <Button
            color="green"
            title="CameraUpdate"
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
              mapView.setCoordinates({latitude: 41, longitude: 29}, 12);
            }}
          />
          <Button
            title="Stop Animation"
            onPress={() => {
              mapView.stopAnimation();
            }}
          />
          <Button
            title="Clear Map"
            color="red"
            onPress={() => {
              mapView.clear();
            }}
          />
          <Button
            title="Get Map Info"
            onPress={() => {
              mapView
                .getHuaweiMapInfo()
                .then((a) => console.log(a.visibleRegion))
                .catch((a) => console.log(a));
            }}
          />
        </View>

        <View style={styles.flexRow}>
          <Button
            color="purple"
            title="Get Coordinate From Point"
            onPress={() => {
              mapView
                .getCoordinateFromPoint({x: 100, y: 100})
                .then((a) => console.log(a))
                .catch((a) => console.log(a));
            }}
          />
          <Button
            color="green"
            title="Get Point From Coordinate"
            onPress={() => {
              mapView
                .getPointFromCoordinate({latitude: 0, longitude: 0})
                .then((a) => console.log(a))
                .catch((a) => console.log(a));
            }}
          />
        </View>
        <Button
          title="Calculate distance"
          onPress={() => {
            MapView.module
              .getDistance(
                {latitude: 41, longitude: 29},
                {latitude: 41, longitude: 28},
              )
              .then((a) => console.log(a))
              .catch((a) => console.log(a));
          }}
        />
        <Button
          title="Reset Zoom Preference"
          color="red"
          onPress={() => {
            mapView.resetMinMaxZoomPreference();
          }}
        />
        <Button
          title="Clear Tile Cache"
          onPress={() => {
            tileOverlayView.clearTileCache();
          }}
        />
        <Button
          title="Show Info Window"
          color="green"
          onPress={() => {
            markerView.showInfoWindow();
          }}
        />
        <Button
          title="Hide Info Window"
          color="purple"
          onPress={() => {
            markerView.hideInfoWindow();
          }}
        />
        <Button
          title="Remove marker"
          color="red"
          onPress={() => {
            setMarkerRow(markerRow - 1);
          }}
        />
        <Button
          title="Add marker"
          onPress={() => {
            setMarkerRow(markerRow + 1);
          }}
        />
      </View>
    </>
  );
};

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {pageId: "MapViewExample"};
  }

  render() {
    return (
      <>
        <StatusBar barStyle="dark-content" />
        <SafeAreaView>
          <View contentInsetAdjustmentBehavior="automatic">
            <MapViewExample />
          </View>
        </SafeAreaView>
      </>
    );
  }
}
const styles = StyleSheet.create({
  flexRow: {flexDirection: "row"},
  flexCol: {flexDirection: "column"},
  flex1: {flex: 1},
  flex2: {flex: 2},
  width30: {width: 30},
  width40: {width: 40},
  width100: {width: 100},
  mapView: {
    height: 300,
    backgroundColor: "red",
  },
  snapView: {
    height: 200,
    backgroundColor: "yellow",
  },
  infoWindow: {
    backgroundColor: "white",
    alignSelf: "baseline",
  },
  container: {
    flexDirection: "column",
    alignSelf: "flex-start",
  },
  // bubble: {
  //   // height:50,
  //   width: 140,
  //   flexDirection: 'row',
  //   // alignSelf: 'flex-start',
  //   // backgroundColor: '#4da2ab',
  //   // paddingHorizontal: 20,
  //   // paddingVertical: 12,
  //   // borderRadius: 6,
  //   // borderColor: '#007a87',
  //   // borderWidth: 0.5,
  // },
  // amount: {
  //   flex: 1,
  // },
  // arrow: {
  //   backgroundColor: 'transparent',
  //   borderWidth: 16,
  //   borderColor: 'transparent',
  //   borderTopColor: '#4da2ab',
  //   alignSelf: 'center',
  //   marginTop: -32,
  // },
  // arrowBorder: {
  //   backgroundColor: 'transparent',
  //   borderWidth: 16,
  //   borderColor: 'transparent',
  //   borderTopColor: '#007a87',
  //   alignSelf: 'center',
  //   marginTop: -0.5,
  // },
});

export default App;
