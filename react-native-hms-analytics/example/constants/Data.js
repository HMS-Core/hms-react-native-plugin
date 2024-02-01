/*
    Copyright 2020-2024. Huawei Technologies Co., Ltd. All rights reserved.

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
export default class Data { }
import {
    Dimensions,
    StyleSheet
} from 'react-native';

export const apiName = {
    getInstance: "getInstance",
    setAnalyEnabled: "setAnalyticsEnabled",
    startPage: "startPage",
    endPage: "endPage",
    setRestEnabled: "setRestrictionEnabled",
    isRestEnabled: "isRestrictionEnabled",
    setCollAdsIdEnabled: "setCollectAdsIdEnabled",
    enableLog: "enableLog",
    enableLogWithLevel: "enableLogWithLevel",
    setUserId: "setUserId",
    setUserProf: "setUserProfile",
    deleteUserProf: "deleteUserProfile",
    setPushToken: "setPushToken",
    setSesDuration: "setSessionDuration",
    minActSession: "minActivitySession",
    onEvent: "onEvent",
    clearCachedData: "clearCachedData",
    getAAID: "getAAID",
    getUserProf: "getUserProfile",
    setReportPolic: "setReportPolicies",
    getRepPolicyT: "getReportPolicyThreshold",
    addDefEventPar: "addDefaultEventParams",
    getDataUploadSiteInfo: "getDataUploadSiteInfo",
    setCustomReferrer: "setCustomReferrer",
    setPropertyCollection: "setPropertyCollection",
    setChannel: "setChannel"
}
export const pickerType = {
    logLevel: "logLevel",
    eventType: "eventType",
    eventParamType: "eventParamType"
}
export const logLevel = {
    debug: "DEBUG",
    info: "INFO",
    warn: "WARN",
    error: "ERROR"
}
export const ScreenWidth = Dimensions.get("window").width;
export const ScreenHeight = Dimensions.get("window").height;

export const styles = StyleSheet.create({
    mainContainer: {
        flex: 1
    },
    header: {
        width: "100%",
        height: 80,
        backgroundColor: "#0b1528",
        justifyContent: 'center',
        borderColor: "#00ffad",
        borderWidth: 1
    },
    headerText: {
        color: "#00ffad",
        fontWeight: "bold",
        fontSize: 20,
        textAlign: "center"
    },
    scrollView: {
        flex: 1,
        backgroundColor: '#2e343b'
    },
    container: {
        paddingTop: 30,
        paddingBottom: 30,
        flex: 1,
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'center'
    },
    btn: {
        marginTop: 20,
        backgroundColor: '#0b1528',
        width: 170,
        height: 45,
        justifyContent: 'center',
        alignSelf: 'center',
        borderRadius: 10,
        marginLeft: 5,
        marginRight: 5,
        padding: 2
    },
    txt: {
        fontSize: 14,
        color: '#00ffad',
        textAlign: 'center'
    },
    resultView: {
        flexDirection: 'row',
        width: 250,
        alignSelf: 'center',
        marginLeft: 5,
        marginRight: 5,
        marginTop: 20
    },
    picker: {
        marginTop: 20,
        alignSelf: 'center',
        backgroundColor: '#0b1528',
        width: 250,
        borderRadius: 10,
        marginLeft: 5,
        marginRight: 5,
        maxHeight: 100
    },
    pickerItem: {
        fontSize: 14,
        color: 'white',
        marginTop: 5,
        marginBottom: 5,
        marginLeft: 25
    },
    input: {
        fontSize: 14,
        color: 'white',
        width: 135,
        borderWidth: 1,
        alignSelf: 'center',
        paddingLeft: 10
    },
    partialView: {
        width: '100%',
        borderColor: '#00ffad',
        borderWidth: 1,
        paddingBottom: 10,
        paddingTop: 10,
        marginTop: 10
    },
    title: {
        fontSize: 20,
        textAlign: 'center',
        color: 'white',
        marginTop: 30,
        width: '100%',
        textDecorationLine: "underline"
    },
    bundleView: {
        borderColor: "#0b1528",
        borderWidth: 2,
        width: '90%',
        marginTop: 5,
        height: 410,
        alignSelf: "center",
        justifyContent: 'center'
    },
    eventView: {
        borderColor: "#0b1528",
        width: '90%',
        height: 220,
        alignSelf: "center",
        borderWidth: 2
    }
});





