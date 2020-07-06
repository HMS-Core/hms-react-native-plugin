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

public enum ScopeConstants {
    SCOPE_ID_TOKEN("idToken"),
    SCOPE_ID("id"),
    SCOPE_ACCESS_TOKEN("accessToken"),
    SCOPE_AUTHORIZATION_CODE("authorizationCode"),
    SCOPE_EMAIL("email"),
    SCOPE_MOBILE_NUMBER("mobileNumber"),
    SCOPE_PROFILE("profile"),
    SCOPE_SCOPE_LIST("scopeList"),
    SCOPE_SHIPPING_ADDRESS("shippingAddress"),
    SCOPE_UID("uid"),
    SCOPE_CREATE_PARAMS("createParams");
    private String value;

    ScopeConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
