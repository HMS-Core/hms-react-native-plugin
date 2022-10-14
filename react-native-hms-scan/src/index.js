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

import Utils from "./ScanUtils";
import CustomizedView from "./ScanCustomizedView";
import MultiProcessor from "./ScanMultiProcessor";
import Permission from "./ScanPermission";

export default {
  Utils,
  CustomizedView,
  MultiProcessor,
  Permission,
  ScanMode: MultiProcessor.SCAN_MODES,
  ScanType: Utils.SCAN_TYPES,
  ScanForm: Utils.SCAN_FORMS,
  AddressType: Utils.ADDRESS_TYPES,
  PhoneNumberType: Utils.TEL_PHONE_NUMBER_USE_TYPES,
  EmailAddressType: Utils.EMAIL_ADDRESS_TYPES,
  WifiModeType: Utils.WIFI_MODE_TYPES,
};
