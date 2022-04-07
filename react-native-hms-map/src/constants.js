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

export const PatternItemTypes = {
  DASH: 0,
  DOT: 1,
  GAP: 2,
};

export const CapTypes = {
  BUTT: 0,
  SQUARE: 1,
  ROUND: 2,
  CUSTOM: 3,
};

export const JointTypes = {
  DEFAULT: 0,
  BEVEL: 1,
  ROUND: 2,
};

export const MapTypes = {
  NONE: 0,
  NORMAL: 1,
  SATELLITE: 2,
  TERRAIN: 3,
  HYBRID: 4,
};

export const Reason = {
  GESTURE: 1,
  API_ANIMATION: 2,
  DEVELOPER_ANIMATION: 3,
};

export const FillMode = { FORWARDS: 0, BACKWARDS: 1 };

export const RepeatMode = { RESTART: 1, REVERSE: 2 };

export const Interpolator = {
  LINEAR: 0,
  ACCELERATE: 1,
  ANTICIPATE: 2,
  BOUNCE: 3,
  DECELERATE: 4,
  OVERSHOOT: 5,
  ACCELERATE_DECELERATE: 6,
  FAST_OUT_LINEAR_IN: 7,
  FAST_OUT_SLOW_IN: 8,
  LINEAR_OUT_SLOW_IN: 9,
};

export const Hue = {
  RED: 0,
  ORANGE: 30,
  YELLOW: 60,
  GREEN: 120,
  CYAN: 180,
  AZURE: 210,
  BLUE: 240,
  VIOLET: 270,
  MAGENTA: 300,
  ROSE: 330,
};

export const Gravity = {
  TOP: 48,
  BOTTOM: 80,
  START: 8388611,
  END: 8388613
};

export const RadiusUnit = {
  PIXEL: "PIXEL",
  METER: "METER"
};
