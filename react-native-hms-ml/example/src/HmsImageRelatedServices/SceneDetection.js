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

import React from 'react';
import {
    Text,
    View,
    ScrollView,
    TextInput,
    TouchableOpacity,
    Image,
    ToastAndroid
} from 'react-native';
import { styles } from '../Styles';
import { HMSSceneDetection, HMSApplication } from '@hmscore/react-native-hms-ml';
import { showImagePicker } from '../HmsOtherServices/Helper';

export default class SceneDetection extends React.Component {

    componentDidMount() { }

    componentWillUnmount() { }

    constructor(props) {
        super(props);
        this.state = {
            imageUri: '',
            results: [],
            confindence: [],
        };
    }

    getFrameConfiguration = () => {
        return { filePath: this.state.imageUri };
    }

    async asyncAnalyzeFrame() {
        try {
            var result = await HMSSceneDetection.asyncAnalyzeFrame(true, 0.5, this.getFrameConfiguration());
            console.log(result);
            if (result.status == HMSApplication.SUCCESS) {
                if (result.result.length == 0) {
                    this.setState({ results: ["No Results"], confindence: ["No Confidence Values"] });
                }
                else {
                    result.result.forEach(element => {
                        this.state.results.push(element.result);
                        this.state.confindence.push(element.confidence);
                    });
                    this.setState({ results: this.state.results, confindence: this.state.confindence });
                }
            }
            else {
                ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
            }
        } catch (e) {
            console.log(e);
        }
    }

    startAnalyze() {
        this.setState({
            results: [],
            confindence: [],
        });
        this.asyncAnalyzeFrame();
    }

    render() {
        return (
            <ScrollView style={styles.bg}>

                <View style={styles.containerCenter}>
                    <TouchableOpacity onPress={() => { showImagePicker().then((result) => this.setState({ imageUri: result })) }}
                        style={styles.startButton}>
                        <Text style={styles.startButtonLabel}>Select Image</Text>
                    </TouchableOpacity>
                    {this.state.imageUri !== '' &&
                        <Image
                            style={styles.imageSelectView}
                            source={{ uri: this.state.imageUri }}
                        />
                    }
                </View>

                <Text style={styles.h1}>Results / Confidence</Text>

                <TextInput
                    style={styles.customInput}
                    value={this.state.results.toString()}
                    placeholder="Results"
                    multiline={false}
                    editable={false}
                />
                <TextInput
                    style={styles.customInput}
                    value={this.state.confindence.toString()}
                    placeholder="Confidence"
                    multiline={false}
                    editable={false}
                />

                <View style={styles.basicButton}>
                    <TouchableOpacity
                        style={styles.startButton}
                        onPress={this.startAnalyze.bind(this)}
                        disabled={this.state.imageUri == '' ? true : false}
                    >
                        <Text style={styles.startButtonLabel}> Start Analyze </Text>
                    </TouchableOpacity>
                </View>
            </ScrollView>
        );
    }
}
