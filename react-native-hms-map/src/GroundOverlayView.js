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

import {
  string,
  exact,
  number,
  oneOfType,
  bool,
  func,
  array,
  arrayOf,
} from "prop-types";
import React, { Component } from "react";
import { requireNativeComponent, ViewPropTypes } from "react-native";

class HMSGroundOverlayView extends Component {
  constructor() {
    super();
  }

  render() {
    return (
      <RNHMSGroundOverlayView
        {...this.props}
        ref={(el) => (this.groundOverlayView = el)}
      />
    );
  }
}

HMSGroundOverlayView.propTypes = {
  ...ViewPropTypes,
  image: exact({
    uri: string,
    hue: number,
    asset: string,
    file: string,
    path: string,
  }).isRequired,
  coordinate: oneOfType([
    exact({
      latitude: number.isRequired,
      longitude: number.isRequired,
      height: number.isRequired,
      width: number.isRequired,
    }),
    arrayOf(
      exact({
        latitude: number.isRequired,
        longitude: number.isRequired,
      }),
    ),
  ]).isRequired,
  anchor: array,
  bearing: number,
  clickable: bool,
  transparency: number,
  visible: bool,
  zIndex: number,
  onClick: func,
};

const RNHMSGroundOverlayView = requireNativeComponent(
  "HMSGroundOverlayView",
  HMSGroundOverlayView,
);

export default HMSGroundOverlayView;
