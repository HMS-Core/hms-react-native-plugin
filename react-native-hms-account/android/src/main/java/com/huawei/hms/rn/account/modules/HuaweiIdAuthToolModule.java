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

package com.huawei.hms.rn.account.modules;

import android.accounts.Account;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.rn.account.utils.Utils;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.hwid.common.HuaweiIdAuthException;
import com.huawei.hms.support.hwid.tools.HuaweiIdAuthTool;

import java.util.List;
import java.util.Objects;

public class HuaweiIdAuthToolModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "HuaweiIdAuthTool";
    private static final String FIELD_ACCESS_TOKEN = "accessToken";
    private static final String FIELD_HUAWEI_ACCOUNT_NAME = "huaweiAccountName";
    private static final String FIELD_HUAWEI_ACCOUNT = "huaweiAccount";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TYPE = "type";

    public HuaweiIdAuthToolModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void deleteAuthInfo(ReadableMap arguments, Promise promise) {
        try {
            HuaweiIdAuthTool.deleteAuthInfo(getCurrentActivity(), arguments.getString(FIELD_ACCESS_TOKEN));
            promise.resolve(true);
        } catch (HuaweiIdAuthException e) {
            Utils.handleError(promise, e);
        }
    }

    @ReactMethod
    public void requestUnionId(ReadableMap arguments, Promise promise) {
        try {
            promise.resolve (HuaweiIdAuthTool.requestUnionId(Objects.requireNonNull(getCurrentActivity()), arguments.getString(FIELD_HUAWEI_ACCOUNT_NAME)));
        } catch (HuaweiIdAuthException e) {
            Utils.handleError(promise, e);
        }
    }

    @ReactMethod
    public void requestAccessToken(ReadableMap arguments, Promise promise) {
        List<Scope> scopeList = Utils.toScopeList(Utils.getScopeArray(arguments));
        ReadableMap huaweiAccountMap = arguments.getMap(FIELD_HUAWEI_ACCOUNT);
        if (huaweiAccountMap != null) {
            Account account = new Account(huaweiAccountMap.getString(FIELD_NAME), huaweiAccountMap.getString(FIELD_TYPE));
            try {
                promise.resolve(HuaweiIdAuthTool.requestAccessToken(Objects.requireNonNull(getCurrentActivity()), account, scopeList));
            } catch (HuaweiIdAuthException e) {
                Utils.handleError(promise, e);
            }
        } else {
            String huaweiAccountNameString =
                arguments.getString(FIELD_HUAWEI_ACCOUNT_NAME);
            try {
                promise.resolve(HuaweiIdAuthTool.requestAccessToken(Objects.requireNonNull(getCurrentActivity()), huaweiAccountNameString, scopeList));
            } catch (HuaweiIdAuthException e) {
                Utils.handleError(promise, e);
            }
        }
    }
}
