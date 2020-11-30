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

package com.huawei.hms.rn.account.modules;

import android.accounts.Account;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.rn.account.utils.Utils;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.hwid.common.HuaweiIdAuthException;
import com.huawei.hms.support.hwid.tools.HuaweiIdAuthTool;
import com.huawei.hms.rn.account.logger.HMSLogger;

import java.util.List;
import java.util.Objects;


public class HMSHuaweiIdAuthTool extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "HMSHuaweiIdAuthTool";
    private static final String FIELD_ACCESS_TOKEN = "accessToken";
    private static final String FIELD_HUAWEI_ACCOUNT_NAME = "huaweiAccountName";
    private static final String FIELD_HUAWEI_ACCOUNT = "huaweiAccount";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TYPE = "type";
    private HMSLogger logger;

    public HMSHuaweiIdAuthTool(ReactApplicationContext reactContext) {
        super(reactContext);
        logger = HMSLogger.getInstance(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void deleteAuthInfo(ReadableMap arguments, Promise promise) {
        String fieldAccessToken = (String) Utils.argumentNullCheck(arguments, FIELD_ACCESS_TOKEN);
        if(fieldAccessToken != null) {
            logger.startMethodExecutionTimer("deleteAuthInfo");
            try {
                HuaweiIdAuthTool.deleteAuthInfo(Objects.requireNonNull(getCurrentActivity()), fieldAccessToken);
                logger.sendSingleEvent("deleteAuthInfo");
                promise.resolve(true);
            } catch (HuaweiIdAuthException e) {
                logger.sendSingleEvent("deleteAuthInfo", e.getLocalizedMessage());
                Utils.handleError(promise, e);
            }
        } else {
            promise.reject("3006", "Null accessToken");
        }
    }

    @ReactMethod
    public void requestUnionId(ReadableMap arguments, Promise promise) {
        String fieldAccountName = (String) Utils.argumentNullCheck(arguments, FIELD_HUAWEI_ACCOUNT_NAME);
        if(fieldAccountName != null) {
            logger.startMethodExecutionTimer("requestUnionId");
            try {
                String requestedUnionId = HuaweiIdAuthTool.requestUnionId(Objects.requireNonNull(getCurrentActivity()), fieldAccountName);
                logger.sendSingleEvent("requestUnionId");
                promise.resolve (requestedUnionId);
            } catch (HuaweiIdAuthException e) {
                logger.sendSingleEvent("requestUnionId", e.getLocalizedMessage());
                Utils.handleError(promise, e);
            }
        } else {
            promise.reject("3007", "Null huaweiAccountName");
        }
    }

    @ReactMethod
    public void requestAccessToken(ReadableMap arguments, Promise promise) {
        ReadableMap fieldAccount = (ReadableMap) Utils.argumentNullCheck(arguments, FIELD_HUAWEI_ACCOUNT);
        List<Scope> scopeList = Utils.toScopeList(Utils.getScopeArray(arguments));
        if (fieldAccount != null) {
        String fieldAccountName = (String) Utils.argumentNullCheck(fieldAccount, FIELD_NAME);
        String fieldAccountType = (String) Utils.argumentNullCheck(fieldAccount, FIELD_TYPE);
            if(fieldAccountName != null && fieldAccountType != null) {
                Account account = new Account(fieldAccountName, fieldAccountType);
                logger.startMethodExecutionTimer("requestAccessToken");
                try {
                    String requestedAccessToken = HuaweiIdAuthTool.requestAccessToken(Objects.requireNonNull(getCurrentActivity()), account, scopeList);
                    logger.sendSingleEvent("requestAccessToken");
                    promise.resolve(requestedAccessToken);
                } catch (HuaweiIdAuthException e) {
                    logger.sendSingleEvent("requestAccessToken", e.getLocalizedMessage());
                    Utils.handleError(promise, e);
                }
            } else{
                promise.reject("3009", "Null huaweiAccount name or type parameter");
            }
        } else {
            promise.reject("3008", "Null huaweiAccount");
        }
    }
}
