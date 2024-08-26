/*
 * Copyright 2020-2024. Huawei Technologies Co., Ltd. All rights reserved.
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

import React, { Component } from "react";
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  NativeModules,
} from "react-native";
const { HMSAdsPrime } = NativeModules;

class HMSAdsPrimeBanner extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    this.loadAd();
  }

  getInfo = () => {
    return HMSAdsPrime.getViewInfo(findNodeHandle(this.bannerView));
  };

  loadAd = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.bannerView),
      UIManager.getViewManagerConfig("HMSAdsPrimeBannerView").Commands.loadAd,
      null,
    );
  };

  setRefresh = (refreshTime) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.bannerView),
      UIManager.getViewManagerConfig("HMSAdsPrimeBannerView").Commands.setRefresh,
      [refreshTime],
    );
  };

  pause = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.bannerView),
      UIManager.getViewManagerConfig("HMSAdsPrimeBannerView").Commands.pause,
      null,
    );
  };

  resume = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.bannerView),
      UIManager.getViewManagerConfig("HMSAdsPrimeBannerView").Commands.resume,
      null,
    );
  };

  destroy = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.bannerView),
      UIManager.getViewManagerConfig("HMSAdsPrimeBannerView").Commands.destroy,
      null,
    );
  };

  render() {
    return (
      <HMSAdsPrimeBannerView
        {...this.props}
        ref={(el) => (this.bannerView = el)}
      />
    );
  }
}

const HMSAdsPrimeBannerView = requireNativeComponent(
  "HMSAdsPrimeBannerView",
  HMSAdsPrimeBanner,
);

export default HMSAdsPrimeBanner;
