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

package com.huawei.hms.rn.account.constants;

import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton;
import com.huawei.hms.support.sms.common.ReadSmsConstant;

public enum ClassConstants {
    READ_SMS_CONSTANT_EXTRA_SMS_MESSAGE(ReadSmsConstant.EXTRA_SMS_MESSAGE),
    READ_SMS_CONSTANT_EXTRA_STATUS(ReadSmsConstant.EXTRA_STATUS),
    READ_SMS_BROADCAST_ACTION(ReadSmsConstant.READ_SMS_BROADCAST_ACTION),
    HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_BLUE(HuaweiIdAuthButton.COLOR_POLICY_BLUE),
    HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_RED(HuaweiIdAuthButton.COLOR_POLICY_RED),
    HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_WHITE(HuaweiIdAuthButton.COLOR_POLICY_WHITE),
    HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_WHITE_WITH_BORDER(HuaweiIdAuthButton.COLOR_POLICY_WHITE_WITH_BORDER),
    HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_BLACK(HuaweiIdAuthButton.COLOR_POLICY_BLACK),
    HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_GRAY(HuaweiIdAuthButton.COLOR_POLICY_GRAY),
    HUAWEI_ID_AUTH_BUTTON_THEME_FULL_TITLE(HuaweiIdAuthButton.THEME_FULL_TITLE),
    HUAWEI_ID_AUTH_BUTTON_THEME_NO_TITLE(HuaweiIdAuthButton.THEME_NO_TITLE),
    HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_LARGE(HuaweiIdAuthButton.CORNER_RADIUS_LARGE),
    HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_MEDIUM(HuaweiIdAuthButton.CORNER_RADIUS_MEDIUM),
    HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_SMALL(HuaweiIdAuthButton.CORNER_RADIUS_SMALL);
    private String stringValue;
    private Integer intValue;

    ClassConstants(String stringValue) {
        this.stringValue = stringValue;
    }

    ClassConstants(Integer intValue) {
        this.intValue = intValue;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
