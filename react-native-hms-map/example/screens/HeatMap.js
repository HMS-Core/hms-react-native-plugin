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

import HMSMap, { MapTypes, RadiusUnit, HMSHeatMap } from "@hmscore/react-native-hms-map";
import React from "react";
import { SafeAreaView } from "react-native";
import { styles } from "../styles/styles";

import earthquakes_with_usa from '../earthquakes_with_usa.json';

export default class HeatMap extends React.Component {
    static options = {
        topBar: {
            title: {
                text: "HeatMap",
            },
        },
    };

    render() {
        return (
            <SafeAreaView>
                <HMSMap
                    camera={{
                        target: {
                            latitude: 37.770443,
                            longitude: -121.331571,
                        },
                        zoom: 6,
                    }}
                    style={styles.fullHeight}
                    mapType={MapTypes.NORMAL}
                >
                    <HMSHeatMap
                        dataSet={
                            JSON.stringify(earthquakes_with_usa)
                        }
                        opacity={{
                            9: 0,
                            13: 0
                        }}
                        color={{
                            0.0: [0, 33, 102, 172],
                            0.2: [255, 103, 169, 207],
                            0.4: [255, 209, 229, 240],
                            0.5: [255, 253, 219, 199],
                            0.8: [255, 239, 138, 98],
                            1.0: [255, 178, 24, 43]
                        }}
                        radius={10}
                        isVisible={true}
                        radiusUnit={RadiusUnit.PIXEL}
                    />
                </HMSMap>
            </SafeAreaView>
        );
    }
}
