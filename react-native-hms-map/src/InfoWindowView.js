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

import React, { Component } from "react";
import { requireNativeComponent, ViewPropTypes, View } from "react-native";

class HMSInfoWindowView extends Component {
  constructor() {
    super();
  }

  render() {
    return (
      <RNHMSInfoWindowView
        {...this.props}
        ref={(el) => (this.infoWindowView = el)}
        style={{
          ...this.props.style,
          alignSelf: "baseline",
        }} />
    );
  }
}

HMSInfoWindowView.defaultProps = {};

HMSInfoWindowView.propTypes = {
  ...ViewPropTypes,
};

const RNHMSInfoWindowView = requireNativeComponent(
  "HMSInfoWindowView",
  HMSInfoWindowView,
);

export default HMSInfoWindowView;
