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

import {shape, exact, oneOf, func, string, bool, number} from 'prop-types';
import React, {Component} from 'react';
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  ViewPropTypes,
} from 'react-native';

import {typeCheck} from './utils';
import {
  AudioFocusType,
  ChoicesPosition,
  Direction,
  NativeMediaTypes,
  ScaleType,
  ContentClassification,
  UnderAge,
  TagForChild,
  NonPersonalizedAd,
  Gender,
} from './constants';

class HMSAdsNative extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    this.loadAd();
  }

  loadAd() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.RNHMSAdsNativeView.Commands.loadAd,
      null,
    );
  }

  dislikeAd(desc) {
    typeCheck(desc, 'string');
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.RNHMSAdsNativeView.Commands.dislikeAd,
      [desc],
    );
  }

  gotoWhyThisAdPage() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.RNHMSAdsNativeView.Commands.gotoWhyThisAdPage,
      null,
    );
  }

  destroy() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.RNHMSAdsNativeView.Commands.destroy,
      null,
    );
  }

  setAllowCustomClick() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.RNHMSAdsNativeView.Commands.setAllowCustomClick,
      null,
    );
  }

  recordClickEvent() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.RNHMSAdsNativeView.Commands.recordClickEvent,
      null,
    );
  }

  recordImpressionEvent(data) {
    typeCheck(data, 'object');
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeView),
      UIManager.RNHMSAdsNativeView.Commands.recordImpressionEvent,
      [data],
    );
  }

  render() {
    return (
      <RNHMSAdsNativeView {...this.props} ref={(el) => (this.nativeView = el)}>
        {this.props.children}
      </RNHMSAdsNativeView>
    );
  }
}

HMSAdsNative.defaultProps = {
  displayForm: {
    mediaType: NativeMediaTypes.video,
    adId: 'testy63txaom86',
  },
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
  nativeConfig: {
    // choicesPosition: ChoicesPosition.BOTTOM_RIGHT,
    mediaDirection: Direction.ANY,
    // mediaAspect: 2,
    // requestCustomDislikeThisAd: false,
    // requestMultiImages: false,
    // returnUrlsForImages: false,
    // adSize: {
    //   height: 100,
    //   width: 100,
    // },
    // videoConfiguration: {
    //   audioFocusType: AudioFocusType.NOT_GAIN_AUDIO_FOCUS_ALL,
    //   clickToFullScreenRequested: true,
    //   customizeOperateRequested: true,
    //   startMuted: true,
    // },
  },
  viewOptions: {
    // showMediaContent: false,
    // mediaImageScaleType: ScaleType.FIT_CENTER,
    // adSourceTextStyle: {color: 'red'},
    // adFlagTextStyle: {backgroundColor: 'red', fontSize: 10},
    // titleTextStyle: {color: 'red'},
    // descriptionTextStyle: {visibility: false},
    // callToActionStyle: {color: 'black', fontSize: 12},
  },
  onNativeAdLoaded: (e) => e,
  onAdDisliked: (e) => e,
  onAdFailed: (e) => e,
  onAdClicked: (e) => e,
  onAdImpression: (e) => e,
  onVideoStart: (e) => e,
  onVideoPlay: (e) => e,
  onVideoEnd: (e) => e,
};

const adTextStylePropTypes = shape({
  color: string,
  backgroundColor: string,
  fontSize: number,
  visibility: bool,
});

HMSAdsNative.propTypes = {
  ...ViewPropTypes,
  displayForm: exact({
    mediaType: oneOf(Object.values(NativeMediaTypes)),
    adId: string,
  }),
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
  nativeConfig: exact({
    choicesPosition: oneOf(Object.values(ChoicesPosition)),
    mediaDirection: oneOf(Object.values(Direction)),
    mediaAspect: number,
    requestCustomDislikeThisAd: bool,
    requestMultiImages: bool,
    returnUrlsForImages: bool,
    adSize: exact({
      height: number,
      width: number,
    }),
    videoConfiguration: exact({
      audioFocusType: oneOf(Object.values(AudioFocusType)),
      clickToFullScreenRequested: bool,
      customizeOperateRequested: bool,
      startMuted: bool,
    }),
  }),
  viewOptions: exact({
    showMediaContent: bool,
    mediaImageScaleType: oneOf(Object.values(ScaleType)),
    adSourceTextStyle: adTextStylePropTypes,
    adFlagTextStyle: adTextStylePropTypes,
    titleTextStyle: adTextStylePropTypes,
    descriptionTextStyle: adTextStylePropTypes,
    callToActionStyle: adTextStylePropTypes,
  }),
  onNativeAdLoaded: func,
  onAdDisliked: func,
  onAdFailed: func,
  onAdClicked: func,
  onAdImpression: func,
  onVideoStart: func,
  onVideoPlay: func,
  onVideoEnd: func,
};

const RNHMSAdsNativeView = requireNativeComponent(
  'RNHMSAdsNativeView',
  HMSAdsNative,
);

export default HMSAdsNative;
