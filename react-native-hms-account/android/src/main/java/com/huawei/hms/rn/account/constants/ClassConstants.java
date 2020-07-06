/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

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
    CONSTANT_HUAWEI_ID_AUTH_PARAMS_DEFAULT_AUTH_REQUEST_PARAM("DEFAULT_AUTH_REQUEST_PARAM"),
    CONSTANT_HUAWEI_ID_AUTH_PARAMS_DEFAULT_AUTH_REQUEST_PARAM_GAME("DEFAULT_AUTH_REQUEST_PARAM_GAME"),
    CONSTANT_READ_SMS_CONSTANT_EXTRA_SMS_MESSAGE(ReadSmsConstant.EXTRA_SMS_MESSAGE),
    CONSTANT_READ_SMS_CONSTANT_EXTRA_STATUS(ReadSmsConstant.EXTRA_STATUS),
    CONSTANT_READ_SMS_BROADCAST_ACTION(ReadSmsConstant.READ_SMS_BROADCAST_ACTION),
    CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_BLUE(HuaweiIdAuthButton.COLOR_POLICY_BLUE),
    CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_RED(HuaweiIdAuthButton.COLOR_POLICY_RED),
    CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_WHITE(HuaweiIdAuthButton.COLOR_POLICY_WHITE),
    CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_WHITE_WITH_BORDER(HuaweiIdAuthButton.COLOR_POLICY_WHITE_WITH_BORDER),
    CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_BLACK(HuaweiIdAuthButton.COLOR_POLICY_BLACK),
    CONSTANT_HUAWEI_ID_AUTH_BUTTON_COLOR_POLICY_GRAY(HuaweiIdAuthButton.COLOR_POLICY_GRAY),
    CONSTANT_HUAWEI_ID_AUTH_BUTTON_THEME_FULL_TITLE(HuaweiIdAuthButton.THEME_FULL_TITLE),
    CONSTANT_HUAWEI_ID_AUTH_BUTTON_THEME_NO_TITLE(HuaweiIdAuthButton.THEME_NO_TITLE),
    CONSTANT_HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_LARGE(HuaweiIdAuthButton.CORNER_RADIUS_LARGE),
    CONSTANT_HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_MEDIUM(HuaweiIdAuthButton.CORNER_RADIUS_MEDIUM),
    CONSTANT_HUAWEI_ID_AUTH_BUTTON_CORNER_RADIUS_SMALL(HuaweiIdAuthButton.CORNER_RADIUS_SMALL);
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
