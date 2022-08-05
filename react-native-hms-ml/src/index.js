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

import { NativeModules } from 'react-native';
import React, { Component } from 'react';
import { requireNativeComponent, View } from 'react-native';

const HMSSurfaceView = requireNativeComponent('HMSSurfaceView');

export const {
  HMSFrame,
  HMSTextRecognition,
  HMSDocumentRecognition,
  HMSBankCardRecognition,
  HMSGeneralCardRecognition,
  HMSIDCardRecognition,
  HMSVietnamCardRecognition,
  HMSFormRecognition,
  HMSTranslate,
  HMSLanguageDetection,
  HMSAsr,
  HMSAft,
  HMSSpeechRtt,
  HMSSoundDetect,
  HMSImageClassification,
  HMSObjectRecognition,
  HMSLandmarkRecognition,
  HMSImageSegmentation,
  HMSImageSuperResolution,
  HMSProductVisionSearch,
  HMSDocumentSkewCorrection,
  HMSTextImageSuperResolution,
  HMSSceneDetection,
  HMSFaceRecognition,
  HMSFaceVerification,
  HMSModelDownload,
  HMSTextToSpeech,
  HMSApplication,
  HMSTextEmbedding,
  HMSCustomModel,
  HMSSkeletonDetection,
  HMSLivenessDetection,
  HMSLensEngine,
  HMSHandKeypointDetection,
  HMSGestureDetection,
  HMSComposite
} = NativeModules;

export default class SurfaceView extends Component {

  constructor(props) {
    super(props);
  }

  render() {
    return <HMSSurfaceView {...this.props} />;
  }
}

SurfaceView.propTypes = {
  ...View.propTypes
}