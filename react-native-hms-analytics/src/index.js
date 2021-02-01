/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

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

import {NativeModules} from 'react-native';
import HAEventType from './constants/HAEventType';
import HAParamType from './constants/HAParamType';
import HAUserProfileType from "./constants/HAUserProfileType";
import ReportPolicyType from "./constants/ReportPolicyType";
import Constants from "./constants/Constants";
import {Platform} from "react-native";

const {HmsAnalyticsModule} = NativeModules;

const isIOS = Platform.OS === 'ios';
const isAndroid = Platform.OS === 'android';

class HmsAnalytics{

    static setMinActivitySessions(minActivitySessionValue){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HmsAnalyticsModule.setMinActivitySessions(minActivitySessionValue);
    }

    static setPushToken(token) {
        if (isIOS) {
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HmsAnalyticsModule.setPushToken(token);
    }

    static enableLogWithLevel(level){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HmsAnalyticsModule.enableLogWithLevel(level);
    }

    static enableLog(){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HmsAnalyticsModule.enableLog();
    }

    static pageEnd(pageName){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        HmsAnalyticsModule.pageEnd(pageName);
    }

    static pageStart(pageName, pageClassOverride){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HmsAnalyticsModule.pageStart(pageName, pageClassOverride);
    }

    static getReportPolicyThreshold(reportPolicyType){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HmsAnalyticsModule.getReportPolicyThreshold(reportPolicyType);
    }

    static setAnalyticsEnabled(isEnabled){
        return HmsAnalyticsModule.setAnalyticsEnabled(isEnabled);
    }

    static setRestrictionEnabled(isEnabled){
        return HmsAnalyticsModule.setRestrictionEnabled(isEnabled);
    }

    static setUserId(userId){
        return HmsAnalyticsModule.setUserId(userId);
    }

    static setUserProfile(name, value){
        return HmsAnalyticsModule.setUserProfile(name, value);
    }

    static deleteUserProfile(name){
        return HmsAnalyticsModule.deleteUserProfile(name);
    }

    static setSessionDuration(milliseconds) {
        return HmsAnalyticsModule.setSessionDuration(milliseconds);
    }

    static clearCachedData(){
        return HmsAnalyticsModule.clearCachedData();
    }

    static getAAID(){
        return HmsAnalyticsModule.getAAID();
    }

    static getUserProfiles(isEnabled){
        return HmsAnalyticsModule.getUserProfiles(isEnabled);
    }

    static onEvent(event, rMap){
        return HmsAnalyticsModule.onEvent(event, rMap);
    }

    static isRestrictionEnabled(){
        return HmsAnalyticsModule.isRestrictionEnabled();
    }

    static setReportPolicies(policies) {
        return HmsAnalyticsModule.setReportPolicies(policies);
    }

    static enableLogger(){
        return HmsAnalyticsModule.enableLogger();
    }

    static disableLogger(){
        return HmsAnalyticsModule.disableLogger();
    }
}

HmsAnalytics.HAEventType = HAEventType;
HmsAnalytics.HAParamType = HAParamType;
HmsAnalytics.HAUserProfileType = HAUserProfileType;
HmsAnalytics.ReportPolicyType = ReportPolicyType;
HmsAnalytics.Constants = Constants;

export default HmsAnalytics;
