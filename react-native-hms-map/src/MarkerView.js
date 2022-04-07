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

import { string, exact, number, array, bool, func } from "prop-types";
import React, { Component } from "react";
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  ViewPropTypes,
} from "react-native";

class HMSMarkerView extends Component {
  constructor() {
    super();
  }

  showInfoWindow = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.markerView),
      UIManager.getViewManagerConfig("HMSMarkerView").Commands.showInfoWindow,
      null,
    );
  };

  hideInfoWindow = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.markerView),
      UIManager.getViewManagerConfig("HMSMarkerView").Commands.hideInfoWindow,
      null,
    );
  };

  startAnimation = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.markerView),
      UIManager.getViewManagerConfig("HMSMarkerView").Commands.startAnimation,
      null,
    );
  };

  setAnimation = (animation, defaultParams) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.markerView),
      UIManager.getViewManagerConfig("HMSMarkerView").Commands.setAnimation,
      [animation, defaultParams],
    );
  };

  cleanAnimation = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.markerView),
      UIManager.getViewManagerConfig("HMSMarkerView").Commands.cleanAnimation,
      null,
    );
  };

  render() {
    return (
      <RNHMSMarkerView {...this.props} ref={(el) => (this.markerView = el)} />
    );
  }
}

HMSMarkerView.propTypes = {
  ...ViewPropTypes,
  coordinate: exact({
    latitude: number.isRequired,
    longitude: number.isRequired,
  }).isRequired,
  draggable: bool,
  icon: exact({
    hue: number,
    asset: string,
    file: string,
    path: string,
    uri: string,
    width: number,
    height: number,
  }),
  alpha: number,
  flat: bool,
  markerAnchor: array,
  infoWindowAnchor: array,
  rotation: number,
  title: string,
  snippet: string,
  visible: bool,
  zIndex: number,
  clusterable: bool,
  defaultActionOnClick: bool,
  onClick: func,
  onDragStart: func,
  onDrag: func,
  onDragEnd: func,
  onInfoWindowClick: func,
  onInfoWindowClose: func,
  onInfoWindowLongClick: func,
  onAnimationStart: func,
  onAnimationEnd: func,
};

const RNHMSMarkerView = requireNativeComponent(
  "HMSMarkerView",
  HMSMarkerView,
);

export default HMSMarkerView;
