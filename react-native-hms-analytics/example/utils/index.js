/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

import {Platform} from "react-native";
import HmsAnalytics from '@hmscore/react-native-hms-analytics';

export const isIOS = Platform.OS === 'ios';
export const isAndroid = Platform.OS === 'android';

/**
 * Below functions differ while calling the Android & IOS Platforms.
 * For further detail, please refer to documentation.
 */
export default {
    /**
     * Obtains the app instance ID from AppGallery Connect.
     * @note For Android specific platforms 'HmsAnalytics.getAAID()', for IOS specific platforms 'HmsAnalytics.aaid()' is being called.
     */
    async getAAID() {
        if (isIOS) {
            return await HmsAnalytics.aaid()
        } else {
            return await HmsAnalytics.getAAID()
        }
    },
    /**
     * Enables AB Testing. Predefined or custom user attributes are supported.
     * @note For Android specific platforms 'HmsAnalytics.getUserProfiles()', for IOS specific platforms 'HmsAnalytics.userProfiles()' is being called.
     */
    async getUserProfiles(preDefined) {
        if (isIOS) {
            return await HmsAnalytics.userProfiles(preDefined)
        } else {
            return await HmsAnalytics.getUserProfiles(preDefined)
        }
    },
    /**
     * Sets data reporting policies.
     * @note This function is specifically used by IOS Platforms.
     */
    async setReportPolicies(reportPolicyArray) {
        if (isIOS) {
            return await HmsAnalytics.setReportPolicies(reportPolicyArray)
        }
    }
}
