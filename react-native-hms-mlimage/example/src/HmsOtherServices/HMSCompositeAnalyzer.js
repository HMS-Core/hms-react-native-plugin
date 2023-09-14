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

import React from 'react';
import {
    Text,
    View,
    ScrollView,
    TouchableOpacity,
    ToastAndroid,
    Image,
} from 'react-native';
import { CompositeFaceAnalyzerConstants, HMSComposite, CompositeHandKeyPointAnalyzerConstants, HMSApplication } from '@hmscore/react-native-hms-mlimage';
import { showImagePicker } from './Helper';
import { styles } from '../Styles';

export default class HMSCompositeAnalyzer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            imageUri: ""
        }
    }

    componentDidMount() {
        this.initialize();
    }

    componentWillUnmount() {
        this.destroy()
    }

    destroy = async () => {
        let res = await HMSComposite.destroy();
        console.log(res);
    }

    initialize = async () => {
        let res = await HMSComposite.createCompositeAnalyzer(this.getFaceAnalyzerSetting());
        console.log(res);
    }

    getFrameConfiguration = (txt) => {
        return { filePath: txt };
    }

    parseResult = (result, alertShow) => {
        console.log(result);
        if (result.status == HMSApplication.SUCCESS) {
            if (alertShow) {
                alert(JSON.stringify(result.result));
            } else {
                ToastAndroid.showWithGravity(JSON.stringify(result.result), ToastAndroid.SHORT, ToastAndroid.CENTER);
            }
        }
        else {
            ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
        }
    }

    getFaceAnalyzerSetting = () => {
        return {
            face: {
                featureType: CompositeFaceAnalyzerConstants.TYPE_FEATURES,
                shapeType: CompositeFaceAnalyzerConstants.TYPE_SHAPES,
                keyPointType: CompositeFaceAnalyzerConstants.TYPE_KEYPOINTS,
                performanceType: CompositeFaceAnalyzerConstants.TYPE_SPEED,
                tracingMode: CompositeFaceAnalyzerConstants.MODE_TRACING_ROBUST,
                minFaceProportion: 0.5,
                isPoseDisabled: false,
                isTracingAllowed: false,
                isMaxSizeFaceOnly: false,
            },
        };
    }

    getCompositeClassificationAnalyzerConfigurationRemoteSetting = () => {
        return {
            classification: { maxNumberOfReturns: 5, minAcceptablePossibility: 0.8, isRemote: true },

        };
    }


    getHandAnalyzerSetting = () => {
        return {
            hand: {
                sceneType: CompositeHandKeyPointAnalyzerConstants.TYPE_KEYPOINT_ONLY,
                maxHandResults: CompositeHandKeyPointAnalyzerConstants.MAX_HANDS_NUM
            },
        };
    }

    render() {
        return (
            <ScrollView style={styles.bg}>

                <View style={styles.containerCenter}>
                    <TouchableOpacity
                        onPress={async () => {
                            let res = await HMSComposite.isAvailable();
                            this.parseResult(res);
                        }}
                        style={[styles.startButton, styles.enable]}>
                        <Text style={styles.startButtonLabel}>Is Available ?</Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        onPress={() => {
                            showImagePicker().then(async (result) => {
                                this.setState({ imageUri: result })
                                let res = await HMSComposite.analyzeFrame(this.getFrameConfiguration(result));
                                this.parseResult(res, true);
                            })
                        }}
                        style={[styles.startButton, styles.enable]}>
                        <Text style={styles.startButtonLabel}>Select Image</Text>
                    </TouchableOpacity>
                    {this.state.imageUri !== '' &&
                        <Image
                            style={styles.imageSelectView}
                            source={{ uri: this.state.imageUri }}
                        />
                    }
                </View>

            </ScrollView>
        );
    }
}
