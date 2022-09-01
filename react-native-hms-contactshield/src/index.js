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

import {
  NativeModules,
} from "react-native";

const {
  HMSContactShieldModule,
} = NativeModules;

const HMSStatusCode = {
  STATUS_SUCCESS: 0,
  STATUS_FAILURE: -1,
  STATUS_API_DISORDER: 8001,
  STATUS_APP_QUOTA_LIMITED: 8100,
  STATUS_DISK_FULL: 8101,
  STATUS_BLUETOOTH_OPERATION_ERROR: 8102,
  STATUS_MISSING_PERMISSION_BLUETOOTH: 8016,
  STATUS_MISSING_SETTING_LOCATION_ON: 8020,
  STATUS_INTERNAL_ERROR: 8060,
  STATUS_MISSING_PERMISSION_INTERNET: 8064
}
Object.freeze(HMSStatusCode);

const HMSTokenMode = {
  TOKEN_A: "TOKEN_WINDOW_MODE"
}
Object.freeze(HMSTokenMode);

const HMSContactShieldSetting = {
  DEFAULT: 14
}
Object.freeze(HMSContactShieldSetting);

const HMSRiskLevel = {
  RISK_LEVEL_INVALID: 0,
  RISK_LEVEL_LOWEST: 1,
  RISK_LEVEL_LOW: 2,
  RISK_LEVEL_MEDIUM_LOW: 3,
  RISK_LEVEL_MEDIUM: 4,
  RISK_LEVEL_MEDIUM_HIGH: 5,
  RISK_LEVEL_HIGH: 6,
  RISK_LEVEL_EXT_HIGH: 7,
  RISK_LEVEL_HIGHEST: 8
}
Object.freeze(HMSRiskLevel);

const ContactShieldStatus = {
  RUNNING: 1,
  NOT_RUNNING: 2,
  BLUETOOTH_OFF: 4,
  LOCATION_OFF: 8,
  NO_LOCATION_PERMISSION: 16,
  HARDWARE_NOT_SUPPORT: 32,
  STORAGE_LIMITED: 64,
  RUNNING_FOR_ANOTHER_APP: 128,
  UNKNOWN: 1024
}
Object.freeze(ContactShieldStatus);

const CalibrationConfidence = {
  LOWEST: 0,
  LOW: 1,
  MEDIUM: 2,
  HIGH: 3
}
Object.freeze(CalibrationConfidence);

export default HMSContactShieldModule;

export { HMSContactShieldSetting, HMSTokenMode, HMSStatusCode, HMSRiskLevel, ContactShieldStatus, CalibrationConfidence};