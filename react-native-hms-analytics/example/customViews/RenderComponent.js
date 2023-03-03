/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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
import React, { Component } from 'react';
import { TouchableOpacity, TextInput, Text, ScrollView, View } from 'react-native';
import Combobox from './ComboBox';
import { pickerType, logLevel, styles, apiName } from '../constants/Data';
import HMSAnalytics from '@hmscore/react-native-hms-analytics';

export default class RenderComponent extends Component {
    constructor(props) {
        super(props);
        this.self = null;
        this.componentName = null;
        this.state = {
            eventId: "",
            logLevelList: [],
            eventList: [],
            selectedLogLevel: null,
            paramList: [],
            isCustomEvent: true,
            showHaParamList: false
        };
    }

    init(self, componentName) {
        this.self = self;
        this.componentName = componentName;
    }

    showResult(api, res) {
        if (res != undefined) {
            alert(api + ":" + JSON.stringify(res));
            console.log(api + "::" + JSON.stringify(res));
        }
    }

    validation() {
        const eventId = this.state.eventId;
        const paramId = this.self.state.paramId;
        const eventBundleValue = this.state.eventBundleValue;
        if (eventId == "" || eventId == undefined) {
            alert("Please select or enter a eventID");
            return false;
        }

        else if (paramId == "" || paramId == null) {
            alert("Please enter a bundleName");
            return false;
        }
        else if (eventBundleValue == "" || eventBundleValue == null) {
            alert("Please enter a bundleValue");
            return false;
        }
        return true;
    }

    pickerView(type) {
        var data = [];
        switch (type) {
            case pickerType.logLevel: {
                data = logLevel;
                break;
            }
            case pickerType.eventType: {
                data = HMSAnalytics.HAEventType;
                break;
            }
            case pickerType.eventParamType: {
                data = HMSAnalytics.HAParamType;
                break;
            }
        }

        var list = [];
        for (var i = 0; i < Object.keys(data).length; i++) {

            let value = Object.values(data)[i] ? Object.values(data)[i] : logLevel.debug;
            var item = (
                <TouchableOpacity
                    key={type + "" + i}
                    style={{
                        width: '100%',
                        height: 30,
                        borderBottomColor: 'red',
                        borderBottomWidth: .5
                    }}
                    onPress={() => this.changePickerSelection(value, type)} >
                    <Text style={styles.pickerItem}>{value}</Text>
                </TouchableOpacity>
            );
            list.push(item);

        }
        switch (type) {
            case pickerType.logLevel: {
                this.setState({ logLevelList: list });
                break;
            }
            case pickerType.eventType: {
                this.setState({ eventList: list });
                break;
            }
            case pickerType.eventParamType: {
                this.setState({ paramList: list });
                break;
            }
        }
    }

    enableLogWithLevelView() {
        var level = this.state.selectedLogLevel ? this.state.selectedLogLevel : logLevel.debug;
        return (
            <View key={"enableLogWithLevel"} style={styles.partialView}>

                <Text style={[styles.pickerItem, { marginLeft: 60, marginBottom: -10 }]}>Select a logLevel </Text>

                <ScrollView
                    showsVerticalScrollIndicator={true}
                    nestedScrollEnabled={true}
                    style={styles.picker}>

                    {this.state.logLevelList}

                </ScrollView>

                <TouchableOpacity activeOpacity={.7} style={styles.btn}
                    onPress={() => this.enableLogWithLevel()}>
                    <Text style={styles.txt}>{'Enable Log With Level: ' + level}</Text>
                </TouchableOpacity>

            </View>
        );
    }

    onEventView() {
        return (
            <View key={"onEvent"} style={[styles.partialView, { marginTop: 10 }]} >

                {this.eventView()}
                {this.bundleView()}
                <TouchableOpacity activeOpacity={.7} style={[styles.btn, { width: 180 }]}
                    onPress={() => this.self.onEvent()}>
                    <Text style={styles.txt}>{'On Event \n' + this.state.eventId}</Text>
                </TouchableOpacity>

            </View>
        );
    }

    bundleView() {
        return (
            <View style={styles.bundleView}>

                <TouchableOpacity activeOpacity={.7} style={styles.btn}
                    onPress={() => [
                        this.setState(prevState => ({
                            showHaParamList: !prevState.showHaParamList
                        })),

                        this.pickerView(pickerType.eventParamType.toString())
                    ]
                    }>
                    <Text style={styles.txt}>{this.state.showHaParamList ? "Invisible HaParams" : "Visible HaParams"}</Text>
                </TouchableOpacity>

                {this.haParamListView()}

                <Text style={[styles.pickerItem, { marginLeft: 93, marginTop: 20 }]}>BundleName </Text>
                <TextInput
                    placeholder={"examp: 'Coin'"}
                    placeholderTextColor={"gray"}
                    style={[styles.input, { marginTop: 0 }]}
                    numberOfLines={1}
                    value={this.self.state.paramId}
                    onChangeText={text => this.setState({ paramId: text })} />

                <Text style={[styles.pickerItem, { marginLeft: 93, marginTop: 20 }]}>BundleValue </Text>
                <TextInput
                    placeholder={"examp: '130'"}
                    placeholderTextColor={"gray"}
                    style={[styles.input, { marginTop: 0 }]}
                    numberOfLines={1}
                    value={this.state.eventBundleValue}
                    onChangeText={text => this.setState({ eventBundleValue: text })} />
            </View>
        );
    }

    eventView() {
        var customView = this.state.isCustomEvent || this.state.isCustomEvent == undefined ?
            (
                <View>
                    <Text style={[styles.pickerItem, { marginLeft: 95, marginTop: 10 }]}>EventID </Text>
                    <TextInput
                        style={styles.input}
                        placeholder={"examp: HelpDesk"}
                        placeholderTextColor={"gray"}
                        numberOfLines={1}
                        value={this.state.eventId}
                        onChangeText={text => this.setState({ eventId: text })} />
                </View>
            )
            :
            (
                <View>
                    <Text style={[styles.pickerItem, { marginLeft: 80, marginTop: 10 }]}>Please, select a eventID </Text>
                    <ScrollView showsVerticalScrollIndicator={true} nestedScrollEnabled={true}
                        style={[styles.picker, { marginTop: 0 }]}>
                        {this.state.eventList}
                    </ScrollView>
                </View>
            );

        return (
            <View style={styles.eventView}>
                <Combobox changeSelection={(selection) => this.changeEventType(selection)} select={false} />
                {customView}
            </View>
        );
    }

    setUserIdView() {
        return (
            <View key={apiName.setUserId} style={[styles.partialView, { marginBottom: 10 }]}>
                <Text style={[styles.pickerItem, { marginLeft: 115 }]}>User ID</Text>
                <TextInput
                    ref={component => this._setUserId = component}
                    style={styles.input}
                    placeholder={"examp: f84137265"}
                    placeholderTextColor={"gray"}
                    numberOfLines={1} value={this.state.userId}
                    onChangeText={text => this.setState({ userId: text })} />
                <TouchableOpacity activeOpacity={.7} style={styles.btn}
                    onPress={() => this.checkUserId()}>
                    <Text style={styles.txt}>{apiName.setUserId}</Text>
                </TouchableOpacity>
            </View>
        );
    }

    haParamListView() {
        return (
            this.state.showHaParamList ?
                <ScrollView showsVerticalScrollIndicator={true} nestedScrollEnabled={true}
                    style={styles.picker}>

                    {this.state.paramList}

                </ScrollView>
                : null
        );
    }

    checkUserId() {
        if (!this.state.userId) {
            this._setUserId.setNativeProps({ borderColor: "red" });
            return;
        } else {
            this._setUserId.setNativeProps({ borderColor: "black" });
        }
        this.self.setUserId(this.state.setUserId);
    }

    changePickerSelection(value, type) {
        switch (type) {
            case pickerType.logLevel: {
                this.setState({ selectedLogLevel: value });
                break;
            }
            case pickerType.eventType: {
                this.self.setState({ eventId: value });
                break;
            }
            case pickerType.eventParamType: {
                this.self.setState({ paramId: value });
                break;
            }
        }
    }

    changeEventType(selection) {
        this.pickerView(pickerType.eventType.toString());
        this.self.setState({ isCustomEvent: selection });
    }

}