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
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  NativeModules,
} from "react-native";

const { HMSAdsPrime } = NativeModules;

class HMSAdsNative extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    this.loadAd();
  }

  getInfo = () => {
    return HMSAdsPrime.getViewInfo(findNodeHandle(this.nativeView));
  };

  loadAd = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsPrimeNativeView").Commands.loadAd,
      null,
    );
  };

  dislikeAd = (desc) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsPrimeNativeView").Commands.dislikeAd,
      [desc],
    );
  };

  gotoWhyThisAdPage = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsPrimeNativeView").Commands
        .gotoWhyThisAdPage,
      null,
    );
  };

  destroy = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsPrimeNativeView").Commands.destroy,
      null,
    );
  };

  setAllowCustomClick = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsPrimeNativeView").Commands
        .setAllowCustomClick,
      null,
    );
  };

  recordClickEvent = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsPrimeNativeView").Commands
        .recordClickEvent,
      null,
    );
  };

  recordImpressionEvent = (data) => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.getViewManagerConfig("HMSAdsPrimeNativeView").Commands
        .recordImpressionEvent,
      [data],
    );
  };

  render() {
    return (
      <HMSAdsPrimeNativeView {...this.props} ref={(el) => (this.nativeView = el)}>
        {this.props.children}
      </HMSAdsPrimeNativeView>
    );
  }
}

const HMSAdsPrimeNativeView = requireNativeComponent(
  "HMSAdsPrimeNativeView",
  HMSAdsNative,
);

export default HMSAdsNative;
