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

import { exact, oneOf, number, arrayOf, bool, func, oneOfType } from "prop-types";
import React, { Component } from "react";
import { requireNativeComponent, ViewPropTypes } from "react-native";
import { PatternItemTypes } from "./constants";

class HMSPolygonView extends Component {
  constructor() {
    super();
  }

  render() {
    return (
      <RNHMSPolygonView {...this.props} ref={(el) => (this.polygonView = el)} />
    );
  }
}

HMSPolygonView.propTypes = {
  ...ViewPropTypes,
  points: arrayOf(
    exact({
      latitude: number.isRequired,
      longitude: number.isRequired,
    }),
  ),
  holes: arrayOf(
    arrayOf(
      exact({
        latitude: number.isRequired,
        longitude: number.isRequired,
      }),
    ),
  ),
  clickable: bool,
  fillColor: oneOfType([number, arrayOf(number)]),
  geodesic: bool,
  strokeColor: oneOfType([number, arrayOf(number)]),
  strokeJointType: number,
  strokeWidth: number,
  strokePattern: arrayOf(
    exact({
      type: oneOf(Object.values(PatternItemTypes)).isRequired,
      length: number,
    }).isRequired,
  ),
  visible: bool,
  zIndex: number,
  onClick: func,
};

const RNHMSPolygonView = requireNativeComponent(
  "HMSPolygonView",
  HMSPolygonView,
);

export default HMSPolygonView;
