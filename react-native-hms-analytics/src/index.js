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

const {HMSAnalyticsModule} = NativeModules;
const isIOS = Platform.OS === 'ios';

class HMSAnalytics{

    static setMinActivitySessions(minActivitySessionValue){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HMSAnalyticsModule.setMinActivitySessions(minActivitySessionValue);
    }

    static setPushToken(token) {
        if (isIOS) {
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HMSAnalyticsModule.setPushToken(token);
    }

    static enableLogWithLevel(level){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HMSAnalyticsModule.enableLogWithLevel(level);
    }

    static enableLog(){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HMSAnalyticsModule.enableLog();
    }

    static pageEnd(pageName){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HMSAnalyticsModule.pageEnd(pageName);
    }

    static pageStart(pageName, pageClassOverride){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HMSAnalyticsModule.pageStart(pageName, pageClassOverride);
    }

    static getReportPolicyThreshold(reportPolicyType){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HMSAnalyticsModule.getReportPolicyThreshold(reportPolicyType);
    }

    static setCollectAdsIdEnabled(isEnabled){
        if(isIOS){
            console.log("This function is not available in iOS platforms.");
            return;
        }
        return HMSAnalyticsModule.setCollectAdsIdEnabled(isEnabled);
    }

    static addDefaultEventParams(map){
        return HMSAnalyticsModule.addDefaultEventParams(map);
    }

    static setAnalyticsEnabled(isEnabled){
        return HMSAnalyticsModule.setAnalyticsEnabled(isEnabled);
    }

    static setRestrictionEnabled(isEnabled){
        return HMSAnalyticsModule.setRestrictionEnabled(isEnabled);
    }

    static setUserId(userId){
        return HMSAnalyticsModule.setUserId(userId);
    }

    static setUserProfile(name, value){
        return HMSAnalyticsModule.setUserProfile(name, value);
    }

    static deleteUserProfile(name){
        return HMSAnalyticsModule.deleteUserProfile(name);
    }

    static setSessionDuration(milliseconds) {
        return HMSAnalyticsModule.setSessionDuration(milliseconds);
    }

    static clearCachedData(){
        return HMSAnalyticsModule.clearCachedData();
    }

    static getAAID(){
        return HMSAnalyticsModule.getAAID();
    }

    static getUserProfiles(isEnabled){
        return HMSAnalyticsModule.getUserProfiles(isEnabled);
    }

    static onEvent(event, rMap){
        return HMSAnalyticsModule.onEvent(event, rMap);
    }

    static isRestrictionEnabled(){
        return HMSAnalyticsModule.isRestrictionEnabled();
    }

    static setReportPolicies(policies) {
        return HMSAnalyticsModule.setReportPolicies(policies);
    }

    static enableLogger(){
        return HMSAnalyticsModule.enableLogger();
    }

    static disableLogger(){
        return HMSAnalyticsModule.disableLogger();
    }
}

HMSAnalytics.HAEventType = HAEventType;
HMSAnalytics.HAParamType = HAParamType;
HMSAnalytics.HAUserProfileType = HAUserProfileType;
HMSAnalytics.ReportPolicyType = ReportPolicyType;
HMSAnalytics.Constants = Constants;

export default HMSAnalytics;