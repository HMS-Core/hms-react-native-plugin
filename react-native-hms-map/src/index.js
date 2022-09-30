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

import HMSMap from "./MapView";
import HMSCircle from "./CircleView";
import HMSMarker from "./MarkerView";
import HMSPolygon from "./PolygonView";
import HMSPolyline from "./PolylineView";
import HMSGroundOverlay from "./GroundOverlayView";
import HMSTileOverlay from "./TileOverlayView";
import HMSHeatMap from "./HeatMapView";
import HMSInfoWindow from "./InfoWindowView";
import {
  PatternItemTypes,
  CapTypes,
  JointTypes,
  MapTypes,
  FillMode,
  RepeatMode,
  Interpolator,
  Hue,
  Gravity,
  RadiusUnit
} from "./constants";

export {
  HMSCircle,
  HMSMarker,
  HMSPolygon,
  HMSPolyline,
  HMSGroundOverlay,
  HMSTileOverlay,
  HMSHeatMap,
  HMSInfoWindow,
  RepeatMode,
  CapTypes,
  Gravity,
  JointTypes,
  MapTypes,
  RadiusUnit,
  FillMode,
  Interpolator,
  Hue,
  PatternItemTypes
};

export default HMSMap;
