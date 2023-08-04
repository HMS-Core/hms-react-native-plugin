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

import HMSMap, { MapTypes, Gravity } from "@hmscore/react-native-hms-map";
import React from "react";
import { SafeAreaView } from "react-native";

import { styles } from "../styles/styles";
export default class BasicMap extends React.Component {
  static options = {
    topBar: {
      title: {
        text: "Basic Map",
      },
    },
  };

  render() {
    return (
      <SafeAreaView>
        <HMSMap
          style={styles.fullHeight}
          mapType={MapTypes.NORMAL}
          liteMode={false}
          darkMode={false}
          camera={{
            target: {
              latitude: 41.02155220194891,
              longitude: 29.0037998967586,
            },
            zoom: 12,
          }}
          logoPosition={Gravity.TOP | Gravity.START}
          logoPadding={{
            paddingStart: 0,
            paddingTop: 0,
            paddingBottom: 0,
            paddingEnd: 0,
          }}
        />
      </SafeAreaView>
    );
  }
}
