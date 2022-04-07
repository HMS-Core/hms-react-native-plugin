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

import { number, bool, exact, string, oneOfType, arrayOf } from 'prop-types';
import React, { Component } from 'react';
import { findNodeHandle, requireNativeComponent, UIManager, ViewPropTypes } from 'react-native';

class HMSTileOverlayView extends Component {
  constructor() {
    super();
  }

  clearTileCache = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.tileOverlayView),
      UIManager.getViewManagerConfig('HMSTileOverlayView').Commands.clearTileCache,
      null
    );
  };

  render() {
    return <RNHMSTileOverlayView {...this.props} ref={(el) => (this.tileOverlayView = el)} />;
  }
}

HMSTileOverlayView.propTypes = {
  ...ViewPropTypes,
  tileProvider: oneOfType([
    exact({
      url: string.isRequired,
      zoom: arrayOf(number),
      width: number,
      height: number,
    }),
    arrayOf(
      exact({
        asset: string.isRequired,
        x: number.isRequired,
        y: number.isRequired,
        zoom: number.isRequired,
        width: number,
        height: number,
      })
    ),
  ]).isRequired,
  fadeIn: bool,
  transparency: number,
  visible: bool,
  zIndex: number,
};

const RNHMSTileOverlayView = requireNativeComponent('HMSTileOverlayView', HMSTileOverlayView);

export default HMSTileOverlayView;
