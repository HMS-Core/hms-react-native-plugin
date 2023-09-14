/*
 * Copyright 2023. Huawei Technologies Co., Ltd. All rights reserved.
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

export const CompositeFaceAnalyzerConstants = {
  MODE_TRACING_FAST : 1,
  MODE_TRACING_ROBUST : 2,
  TYPE_FEATURES : 1,
  TYPE_FEATURE_AGE : 256,
  TYPE_FEATURE_BEARD : 32,
  TYPE_FEATURE_EMOTION : 4,
  TYPE_FEATURE_EYEGLASS : 8,
  TYPE_FEATURE_GENDAR : 128,
  TYPE_FEATURE_HAT : 16,
  TYPE_FEATURE_OPENCLOSEEYE : 64,
  TYPE_KEYPOINTS : 1,
  TYPE_PRECISION : 1,
  TYPE_SHAPES : 2,
  TYPE_SPEED : 2,
  TYPE_UNSUPPORT_FEATURES : 2,
  TYPE_UNSUPPORT_KEYPOINTS : 0,
  TYPE_UNSUPPORT_SHAPES : 3,
  TYPE_BOTTOM_OF_MOUTH : 1,
  TYPE_LEFT_CHEEK : 2,
  TYPE_LEFT_EAR : 3,
  TYPE_LEFT_EYE : 5,
  TYPE_LEFT_SIDE_OF_MOUTH : 6,
  TYPE_RIGHT_CHEEK : 8,
  TYPE_RIGHT_EAR : 9,
  TYPE_RIGHT_EYE : 11,
  TYPE_RIGHT_SIDE_OF_MOUTH : 12,
  TYPE_TIP_OF_LEFT_EAR : 4,
  TYPE_TIP_OF_NOSE : 7,
  TYPE_TIP_OF_RIGHT_EAR : 10,
  TYPE_ALL : 0,
  TYPE_BOTTOM_OF_LEFT_EYEBROW : 4,
  TYPE_BOTTOM_OF_LOWER_LIP : 8,
  TYPE_BOTTOM_OF_NOSE : 12,
  TYPE_BOTTOM_OF_RIGHT_EYEBROW : 5,
  TYPE_BOTTOM_OF_UPPER_LIP : 10,
  TYPE_BRIDGE_OF_NOSE : 13,
  TYPE_FACE : 1,
  TYPE_LEFT_EYE : 2,
  TYPE_RIGHT_EYE : 3,
  TYPE_TOP_OF_LEFT_EYEBROW : 6,
  TYPE_TOP_OF_LOWER_LIP : 9,
  TYPE_TOP_OF_RIGHT_EYEBROW : 7,
  TYPE_TOP_OF_UPPER_LIP : 11
}

export const CompositeTextAnalyzeConstants = {
  OCR_DETECT_MODE : 1,
  OCR_TRACKING_MODE : 2,
  OCR_LOOSE_SCENE : 1,
  OCR_COMPACT_SCENE : 2,
  ARC : "ARC",
  NGON : "NGON",
  LATIN : "rm",
  ENGLISH : "en",
  CHINESE : "zh",
  JAPANESE : "ja",
  KOREAN : "ko",
  RUSSIAN : "ru",
  GERMAN : "de",
  FRENCH : "fr",
  ITALIAN : "it",
  PORTUGUESE : "pt",
  SPANISH : "es",
  POLISH : "pl",
  NORWEGIAN : "no",
  SWEDISH : "sv",
  DANISH : "da",
  TURKISH : "tr",
  FINNISH : "fi",
  THAI : "th",
  ARABIC : "ar",
  HINDI : "hi"
}

export const CompositeSkeletonAnalyzerConstants = {
  TYPE_RIGHT_SHOULDER : 101,
  TYPE_RIGHT_ELBOW : 102,
  TYPE_RIGHT_WRIST : 103,
  TYPE_LEFT_SHOULDER : 104,
  TYPE_LEFT_ELBOW : 105,
  TYPE_LEFT_WRIST : 106,
  TYPE_RIGHT_HIP : 107,
  TYPE_RIGHT_KNEE : 108,
  TYPE_RIGHT_ANKLE : 109,
  TYPE_LEFT_HIP : 110,
  TYPE_LEFT_KNEE : 111,
  TYPE_LEFT_ANKLE : 112,
  TYPE_HEAD_TOP : 113,
  TYPE_NECK : 114,
  TYPE_NORMAL : 0,
  TYPE_YOGA : 1,
}

export const  CompositeHandKeyPointAnalyzerConstants ={
  TYPE_WRIST : 0,
  TYPE_THUMB_FIRST : 1,
  TYPE_THUMB_SECOND : 2,
  TYPE_THUMB_THIRD : 3,
  TYPE_THUMB_FOURTH : 4,
  TYPE_FOREFINGER_FIRST : 5,
  TYPE_FOREFINGER_SECOND : 6,
  TYPE_FOREFINGER_THIRD : 7,
  TYPE_FOREFINGER_FOURTH : 8,
  TYPE_MIDDLE_FINGER_FIRST : 9,
  TYPE_MIDDLE_FINGER_SECOND : 10,
  TYPE_MIDDLE_FINGER_THIRD : 11,
  TYPE_MIDDLE_FINGER_FOURTH : 12,
  TYPE_RING_FINGER_FIRST : 13,
  TYPE_RING_FINGER_SECOND : 14,
  TYPE_RING_FINGER_THIRD : 15,
  TYPE_RING_FINGER_FOURTH : 16,
  TYPE_LITTLE_FINGER_FIRST : 17,
  TYPE_LITTLE_FINGER_SECOND : 18,
  TYPE_LITTLE_FINGER_THIRD : 19,
  TYPE_LITTLE_FINGER_FOURTH : 20,
  TYPE_ALL : 0,
  TYPE_KEYPOINT_ONLY : 1,
  TYPE_RECT_ONLY : 2,
  MAX_HANDS_NUM : 10
}

export const CompositeObjectAnalyzerConstants = {
  TYPE_PICTURE : 0,
  TYPE_VIDEO : 1,
  TYPE_OTHER : 0,
  TYPE_GOODS : 1,
  TYPE_FOOD : 2,
  TYPE_FURNITURE : 3,
  TYPE_PLANT : 4,
  TYPE_PLACE : 5,
  TYPE_FACE : 6
}