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

import { exact, oneOf, number, arrayOf, bool, func, oneOfType } from "prop-types";
import React, { Component } from "react";
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager
} from "react-native";
import { PatternItemTypes } from "./constants";

class HMSCircleView extends Component {
  constructor() {
    super();
  }
  startAnimation = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.circleView),
      UIManager.getViewManagerConfig("HMSCircleView").Commands.startAnimation,
      null,
    );
  };

  setAnimation = (animation, defaultParams) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.circleView),
      UIManager.getViewManagerConfig("HMSCircleView").Commands.setAnimation,
      [animation, defaultParams],
    );
  };

  cleanAnimation = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.circleView),
      UIManager.getViewManagerConfig("HMSCircleView").Commands.cleanAnimation,
      null,
    );
  };

  render() {
    return (
      <RNHMSCircleView {...this.props} ref={(el) => (this.circleView = el)} />
    );
  }
}

HMSCircleView.propTypes = {
  center: exact({
    latitude: number.isRequired,
    longitude: number.isRequired,
  }).isRequired,
  radius: number.isRequired,
  clickable: bool,
  fillColor: oneOfType([number, arrayOf(number)]),
  strokeColor: oneOfType([number, arrayOf(number)]),
  strokeWidth: number,
  strokePattern: arrayOf(
    exact({
      type: oneOf(Object.values(PatternItemTypes)).isRequired,
      length: number,
    }),
  ),
  visible: bool,
  zIndex: number,
  onClick: func,
  onAnimationStart: func,
  onAnimationEnd: func,
};

const RNHMSCircleView = requireNativeComponent(
  "HMSCircleView",
  HMSCircleView,
);

export default HMSCircleView;
