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

import React, {Component} from "react";
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  NativeModules,
} from "react-native";

const {HMSAds} = NativeModules;

class HMSAdsInstream extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    this.loadAd();
  }

  getInfo = () => {
    return HMSAds.getViewInfo(findNodeHandle(this.instreamView));
  };

  loadAd = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.instreamView),
      UIManager.getViewManagerConfig("HMSAdsInstreamView").Commands.loadAd,
      null,
    );
  };

  register = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.instreamView),
      UIManager.getViewManagerConfig("HMSAdsInstreamView").Commands.register,
      null,
    );
  };

  mute = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.instreamView),
      UIManager.getViewManagerConfig("HMSAdsInstreamView").Commands.mute,
      null,
    );
  };

  unmute = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.instreamView),
      UIManager.getViewManagerConfig("HMSAdsInstreamView").Commands.unmute,
      null,
    );
  };

  stop = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.instreamView),
      UIManager.getViewManagerConfig("HMSAdsInstreamView").Commands.stop,
      null,
    );
  };

  pause = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.instreamView),
      UIManager.getViewManagerConfig("HMSAdsInstreamView").Commands.pause,
      null,
    );
  };

  play = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.instreamView),
      UIManager.getViewManagerConfig("HMSAdsInstreamView").Commands.play,
      null,
    );
  };

  destroy = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.instreamView),
      UIManager.getViewManagerConfig("HMSAdsInstreamView").Commands.destroy,
      null,
    );
  };

  render() {
    return (
      <HMSAdsInstreamView
        {...this.props}
        ref={(el) => (this.instreamView = el)}
      />
    );
  }
}

const HMSAdsInstreamView = requireNativeComponent(
  "HMSAdsInstreamView",
  HMSAdsInstream,
);

export default HMSAdsInstream;
