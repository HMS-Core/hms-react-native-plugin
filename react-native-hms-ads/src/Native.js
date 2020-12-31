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

class HMSAdsNative extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    this.loadAd();
  }

  getInfo = () => {
    return HMSAds.getViewInfo(findNodeHandle(this.nativeView));
  };

  loadAd = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsNativeView").Commands.loadAd,
      null,
    );
  };

  dislikeAd = (desc) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsNativeView").Commands.dislikeAd,
      [desc],
    );
  };

  gotoWhyThisAdPage = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsNativeView").Commands
        .gotoWhyThisAdPage,
      null,
    );
  };

  destroy = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsNativeView").Commands.destroy,
      null,
    );
  };

  setAllowCustomClick = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsNativeView").Commands
        .setAllowCustomClick,
      null,
    );
  };

  recordClickEvent = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsNativeView").Commands
        .recordClickEvent,
      null,
    );
  };

  recordImpressionEvent = (data) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsNativeView").Commands
        .recordImpressionEvent,
      [data],
    );
  };

  render() {
    return (
      <HMSAdsNativeView {...this.props} ref={(el) => (this.nativeView = el)}>
        {this.props.children}
      </HMSAdsNativeView>
    );
  }
}

const HMSAdsNativeView = requireNativeComponent(
  "HMSAdsNativeView",
  HMSAdsNative,
);

export default HMSAdsNative;
