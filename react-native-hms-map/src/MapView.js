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

import { func } from "prop-types";
import React, { Component } from "react";
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  NativeModules,
  ViewPropTypes,
} from "react-native";

const { HMSMapViewModule } = NativeModules;

class HMSMapView extends Component {
  constructor(props) {
    super();
    this.state = {
      isMapReady: false,
    };

    if (props.liteMode)
      HMSMapViewModule.setLiteMod(props.liteMode);
    else
      HMSMapViewModule.setLiteMod(false);
  }

  getHuaweiMapInfo = () => {
    return HMSMapViewModule.getHuaweiMapInfo(findNodeHandle(this.mapView));
  };

  getLayerInfo = (layer) => {
    return HMSMapViewModule.getLayerInfo(findNodeHandle(layer));
  };

  getLayerOptionsInfo = (layer) => {
    return HMSMapViewModule.getLayerOptionsInfo(findNodeHandle(layer));
  };

  getPointFromCoordinate = (coordinate) => {
    return HMSMapViewModule.getPointFromCoordinate(
      findNodeHandle(this.mapView),
      coordinate,
    );
  };

  getCoordinateFromPoint = (point) => {
    return HMSMapViewModule.getCoordinateFromPoint(
      findNodeHandle(this.mapView),
      point,
    );
  };

  getDistance = (from, to) => {
    return HMSMapViewModule.getDistance(from, to);
  };

  enableLogger = () => {
    HMSMapViewModule.enableLogger();
  };

  disableLogger = () => {
    HMSMapViewModule.disableLogger();
  };

  clear = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands.clear,
      null,
    );
  };

  takeSnapshot = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands.takeSnapshot,
      null,
    );
  };

  resetMinMaxZoomPreference = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands
        .resetMinMaxZoomPreference,
      null,
    );
  };

  stopAnimation = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands.stopAnimation,
      null,
    );
  };

  setCameraPosition = (cameraPosition) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands.setCameraPosition,
      [cameraPosition],
    );
  };

  setCoordinates = (latLng, zoom) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands.setCoordinates,
      [latLng, zoom],
    );
  };

  setBounds = (latLngBounds, padding, width, height) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands.setBounds,
      [latLngBounds, padding, width, height],
    );
  };

  scrollBy = (x, y) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands.scrollBy,
      [x, y],
    );
  };

  zoomBy = (amount, focus) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands.zoomBy,
      [amount, focus],
    );
  };

  zoomTo = (zoom) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands.zoomTo,
      [zoom],
    );
  };

  zoomIn = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands.zoomIn,
      null,
    );
  };

  zoomOut = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.mapView),
      UIManager.getViewManagerConfig("HMSMapView").Commands.zoomOut,
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
          this.setState({ isMapReady: true });
        }}
        ref={(el) => (this.mapView = el)}
      />
    );
  }
}
HMSMapView.defaultProps = {
  style: { height: "100%" },
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

const RNHMSMapView = requireNativeComponent("HMSMapView", HMSMapView);

HMSMapView.module = HMSMapViewModule;

export default HMSMapView;
