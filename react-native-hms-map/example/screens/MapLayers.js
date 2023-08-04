/*
 * Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.
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

import HMSMap, {
  MapTypes,
  HMSCircle,
  HMSPolygon,
  HMSPolyline,
  HMSGroundOverlay,
  PatternItemTypes,
  JointTypes,
  CapTypes,  
  FillMode,
  RepeatMode,
  Interpolator,
} from "@hmscore/react-native-hms-map";
import React from "react";
import { Image, SafeAreaView } from "react-native";
import { styles } from "../styles/styles";
let circleRef;
const exampleAnimation = {
  translate: {
    latitude: 40.97511809993672,
    longitude: 29.066076168319412,
    duration: 10000,
    interpolator: Interpolator.ACCELERATE_DECELERATE,
  },
};

const defaultOptions = {
  duration: 5000,
  fillMode: FillMode.FORWARDS,
  repeatCount: 0,
  repeatMode: RepeatMode.REVERSE,
  interpolator: Interpolator.LINEAR,
};

export default class MapLayers extends React.Component {
  static options = {
    topBar: {
      title: {
        text: "Map Layers",
      },
    },
  };

  render() {
    return (
      <SafeAreaView>
        <HMSMap
          camera={{
            target: {
              latitude: 41.02155220194891,
              longitude: 29.0037998967586,
            },
            zoom: 8,
          }}
          style={styles.fullHeight}
          mapType={MapTypes.NORMAL}
        >
          <HMSCircle
            center={{
              latitude: 40.44762707013626,
              longitude: 29.521178547464814,
            }}
            radius={20000}
          />
          <HMSCircle
            ref={(e) => {
              circleRef = e;
            }}
            center={{
              latitude: 40.200098529472164,
              longitude: 29.051574903330724,
            }}
            radius={20000}
            clickable={true}
            fillColor={538066306} // transparent blue(0x20123D82)
            strokeWidth={10}
            strokeColor={-256} // yellow(0xFFFFFF00)
            strokePattern={[
              { type: PatternItemTypes.DASH, length: 20 },
              { type: PatternItemTypes.DOT },
              { type: PatternItemTypes.GAP, length: 20 },
            ]}
            visible={true}
            zIndex={2}
            onClick={(e) => {
              circleRef.setAnimation(exampleAnimation, defaultOptions);
              circleRef.startAnimation();
            }}
            
            onAnimationStart={(e) =>
              console.log(`Animation ${e.nativeEvent.type} Started`)
            }
            onAnimationEnd={(e) =>
              console.log(
                `Animation ${e.nativeEvent.type} Ended in ${e.nativeEvent.duration} ms`
              )
            }
          />
          <HMSPolygon
            points={[
              { latitude: 40.88081833259618, longitude: 28.511668913805224 },
              { latitude: 40.56100089185301, longitude: 28.529800093962084 },
              { latitude: 40.482097086813454, longitude: 29.19903056928332 },
            ]}
          />
          <HMSPolygon
            points={[
              { latitude: 41.33004402346952, longitude: 28.700774668034082 },
              { latitude: 41.169912047344546, longitude: 29.571561348562035 },
              { latitude: 40.875001445894, longitude: 29.26068331264643 },
              { latitude: 41.01477214098381, longitude: 28.558158204360694 },
            ]}
            holes={[
              [
                { latitude: 41.21526341029057, longitude: 28.58659971544569 },
                { latitude: 41.19356639661979, longitude: 28.828612292975148 },
                { latitude: 41.12483856408342, longitude: 28.738931829023343 },
              ],
              [
                { latitude: 41.10222454052367, longitude: 29.135029255619823 },
                { latitude: 41.025468754193646, longitude: 29.179012497818803 },
                { latitude: 40.99655710812257, longitude: 29.144235633489714 },
                { latitude: 41.030558427678145, longitude: 29.07145278656271 },
              ],
            ]}
            clickable={true}
            geodesic={true}
            fillColor={[60, 0, 0, 255]} // transparent blue(0x20123D82)
            strokeColor={[255, 255, 0, 255]} // yellow(0xFFFFFF00)
            strokeJointType={JointTypes.BEVEL}
            strokePattern={[
              { type: PatternItemTypes.DASH, length: 20 },
              { type: PatternItemTypes.DOT },
              { type: PatternItemTypes.GAP, length: 20 },
            ]}
            zIndex={2}
            onClick={(e) => console.log("Polygon onClick")}
          />
          <HMSPolyline
            points={[
              { latitude: 40.827129114265524, longitude: 29.373611701881106 },
              { latitude: 40.790073321984195, longitude: 29.512501011809462 },
              { latitude: 40.69835270230068, longitude: 29.49504690851675 },
            ]}
          />
          <HMSPolyline
            points={[
              { latitude: 40.915138580349236, longitude: 29.01346841103354 },
              { latitude: 40.85695204475017, longitude: 29.128712763739536 },
              { latitude: 40.75361525383429, longitude: 29.128183927718386 },
              { latitude: 40.65765558913118, longitude: 29.28286041547111 },
            ]}
            gradient={true}
            ColorValues={[-655362,-1671168,-16711936]}
            clickable={true}
            geodesic={true}
            color={538066306} // transparent blue(0x20123D82)
            jointType={JointTypes.BEVEL}
            pattern={[{ type: PatternItemTypes.DASH, length: 20 }]}
            startCap={{
              type: CapTypes.ROUND,
            }}
            endCap={{
              type: CapTypes.CUSTOM,
              refWidth: 2000,
              asset: "plane.png", // under assets folder
            }}
            visible={true}
            width={15.0}
            zIndex={2}
            onClick={(e) => console.log("Polyline onClick")}
          />
          <HMSGroundOverlay
            image={{
              asset: "ic_launcher.png", // under assets folder
              uri: Image.resolveAssetSource(
                require("../assets/galata-tower.png")
              ).uri,
            }}
            coordinate={[
              { latitude: 41.10969168434648, longitude: 28.21047623250003 },
              { latitude: 41.092865202093456, longitude: 28.294297083978915 },
              { latitude: 41.04744527333003, longitude: 28.235456717798996 },
            ]}
          />
          <HMSGroundOverlay
            image={{
              asset: "plane.png", // under assets folder
            }}
            coordinate={{
              latitude: 40.795036723040276,
              longitude: 28.9733225727439,
              height: 20000,
              width: 20000,
            }}
            anchor={[0.5, 0.5]}
            bearing={220}
            clickable={true}
            transparency={0.5}
            visible={true}
            zIndex={3}
            onClick={(e) =>
              console.log("GroundOverlay onClick e:", e.nativeEvent)
            }
          />
        </HMSMap>
      </SafeAreaView>
    );
  }
}
