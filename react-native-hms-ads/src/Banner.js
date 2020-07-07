/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

import {func, string, exact, oneOf, number} from 'prop-types';
import React, {Component} from 'react';
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  ViewPropTypes,
} from 'react-native';

import {typeCheck} from './utils';
import {
  ContentClassification,
  UnderAge,
  TagForChild,
  NonPersonalizedAd,
  Gender,
  BannerAdSizes,
} from './constants';

class HMSAdsBanner extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    this.loadAd();
  }

  loadAd() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.bannerView),
      UIManager.RNHMSAdsBannerView.Commands.loadAd,
      null,
    );
  }

  setRefresh(refreshTime) {
    typeCheck(refreshTime, 'integer');
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.bannerView),
      UIManager.RNHMSAdsBannerView.Commands.setRefresh,
      [refreshTime],
    );
  }

  pause() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.bannerView),
      UIManager.RNHMSAdsBannerView.Commands.pause,
      null,
    );
  }

  resume() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.bannerView),
      UIManager.RNHMSAdsBannerView.Commands.resume,
      null,
    );
  }

  destroy() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.bannerView),
      UIManager.RNHMSAdsBannerView.Commands.destroy,
      null,
    );
  }

  render() {
    return (
      <RNHMSAdsBannerView
        {...this.props}
        ref={(el) => (this.bannerView = el)}
      />
    );
  }
}

HMSAdsBanner.defaultProps = {
  bannerAdSize: {
    bannerAdSize: BannerAdSizes.B_320_100,
    // width: 100,
  },
  adId: 'testw6vs28auh3',
  adParam: {
    adContentClassification:
      ContentClassification.AD_CONTENT_CLASSIFICATION_UNKOWN,
    // appCountry: '',
    // appLang: '',
    // belongCountryCode: '',
    gender: Gender.UNKNOWN,
    nonPersonalizedAd: NonPersonalizedAd.ALLOW_ALL,
    // requestOrigin: '',
    tagForChildProtection: TagForChild.TAG_FOR_CHILD_PROTECTION_UNSPECIFIED,
    tagForUnderAgeOfPromise: UnderAge.PROMISE_UNSPECIFIED,
    // targetingContentUrl: '',
  },
  onAdLoaded: (e) => e,
  onAdFailed: (e) => e,
  onAdOpened: (e) => e,
  onAdClicked: (e) => e,
  onAdClosed: (e) => e,
  onAdImpression: (e) => e,
  onAdLeave: (e) => e,
};
HMSAdsBanner.propTypes = {
  ...ViewPropTypes,
  bannerAdSize: exact({
    bannerAdSize: oneOf(Object.values(BannerAdSizes)),
    width: number,
  }),
  adId: string,
  adParam: exact({
    adContentClassification: oneOf(Object.values(ContentClassification)),
    appCountry: string,
    appLang: string,
    belongCountryCode: string,
    gender: oneOf(Object.values(Gender)),
    nonPersonalizedAd: oneOf(Object.values(NonPersonalizedAd)),
    requestOrigin: string,
    tagForChildProtection: oneOf(Object.values(TagForChild)),
    tagForUnderAgeOfPromise: oneOf(Object.values(UnderAge)),
    targetingContentUrl: string,
  }),
  onAdLoaded: func,
  onAdFailed: func,
  onAdOpened: func,
  onAdClicked: func,
  onAdClosed: func,
  onAdImpression: func,
  onAdLeave: func,
};

const RNHMSAdsBannerView = requireNativeComponent(
  'RNHMSAdsBannerView',
  HMSAdsBanner,
);

export default HMSAdsBanner;
