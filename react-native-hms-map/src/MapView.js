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

import {func} from "prop-types";
import React, {Component} from "react";
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  NativeModules,
  ViewPropTypes,
} from "react-native";

const {RNHMSMapViewModule} = NativeModules;

class HMSMapView extends Component {
  constructor() {
    super();
    this.state = {
      isMapReady: false,
    };
  }

  getHuaweiMapInfo = () => {
    return RNHMSMapViewModule.getHuaweiMapInfo(findNodeHandle(this.mapView));
  };

  getPointFromCoordinate = (coordinate) => {
    return RNHMSMapViewModule.getPointFromCoordinate(
      findNodeHandle(this.mapView),
      coordinate,
    );
  };

  getCoordinateFromPoint = (point) => {
    return RNHMSMapViewModule.getCoordinateFromPoint(
      findNodeHandle(this.mapView),
      point,
    );
  };

  getDistance = (from, to) => {
    return RNHMSMapViewModule.getDistance(from, to);
  };

  clear = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "clear",
      null,
    );
  };

  takeSnapshot = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "takeSnapshot",
      null,
    );
  };

  resetMinMaxZoomPreference = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "resetMinMaxZoomPreference",
      null,
    );
  };

  stopAnimation = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "stopAnimation",
      null,
    );
  };

  setCameraPosition = (cameraPosition) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "setCameraPosition",
      [cameraPosition],
    );
  };

  setCoordinates = (latLng, zoom) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "setCoordinates",
      [latLng, zoom],
    );
  };

  setBounds = (latLngBounds, padding, width, height) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "setBounds",
      [latLngBounds, padding, width, height],
    );
  };

  scrollBy = (x, y) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "scrollBy",
      [x, y],
    );
  };

  zoomBy = (amount, focus) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "zoomBy",
      [amount, focus],
    );
  };

  zoomTo = (zoom) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "zoomTo",
      [zoom],
    );
  };

  zoomIn = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "zoomIn",
      null,
    );
  };

  zoomOut = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      "zoomOut",
      null,
    );
  };

  render() {
    return this.state.isMapReady ? (
      <RNHMSMapView {...this.props} ref={(el) => (this.mapView = el)} />
    ) : (
      <RNHMSMapView
        style={this.props.style}
        onMapReady={(e) => {
          if (typeof this.props.onMapReady === "function") {
            this.props.onMapReady(e);
          }
          this.setState({isMapReady: true});
        }}
        ref={(el) => (this.mapView = el)}
      />
    );
  }
}
HMSMapView.defaultProps = {
  style: {height: "100%"},
};

HMSMapView.propTypes = {
  ...ViewPropTypes,
  onMapReady: func,
  onCameraUpdateFinished: func,
  onCameraUpdateCanceled: func,
  onCameraIdle: func,
  onCameraMoveCanceled: func,
  onCameraMove: func,
  onCameraMoveStarted: func,
  onMapClick: func,
  onMapLoaded: func,
  onMapLongClick: func,
  onMyLocationButtonClick: func,
  onMyLocationClick: func,
  onPoiClick: func,
  onSnapshotReady: func,
};

const RNHMSMapView = requireNativeComponent("RNHMSMapView", HMSMapView);

HMSMapView.module = RNHMSMapViewModule;

export default HMSMapView;
