/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

import android.app.Activity;
import android.content.Intent;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.rn.account.logger.HMSLogger;
import com.huawei.hms.rn.account.utils.Utils;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AccountIcon;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.util.Objects;

import javax.annotation.Nonnull;

public class HMSAccountAuthService extends ReactContextBaseJavaModule implements ActivityEventListener {
    private static final int REQUEST_CODE_LOG_IN = 0;

    private static final String FIELD_ACCOUNT_AUTH_PARAMS = "accountAuthParams";

    private static final String FIELD_REQUEST_OPTION = "authRequestOption";

    private static final String FIELD_AUTH_SCOPES_LIST = "authScopeList";

    private AccountAuthService accountAuthService;

    private Promise mSignInAccountPromise;

    private HMSLogger logger;

    public HMSAccountAuthService(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
        logger = HMSLogger.getInstance(reactContext);
        reactContext.addActivityEventListener(this);
    }

    @Nonnull
    @Override
    public String getName() {
        return "HMSAccountAuthService";
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE_LOG_IN && mSignInAccountPromise != null) {
            Task<AuthAccount> accountAuthTask = AccountAuthManager.parseAuthResultFromIntent(intent);
            accountAuthTask.addOnSuccessListener(authAccount -> mSignInAccountPromise.resolve(
                Utils.parseAuthAccount(authAccount, getReactApplicationContext())))
                .addOnFailureListener(e -> Utils.handleError(mSignInAccountPromise, e));
        }
    }

    @ReactMethod
    public void signIn(ReadableMap arguments, final Promise promise) {
        String fieldName = (String) Utils.argumentNullCheck(arguments, FIELD_ACCOUNT_AUTH_PARAMS);
        ReadableArray requestOption = (ReadableArray) Utils.argumentNullCheck(arguments, FIELD_REQUEST_OPTION);
        ReadableArray authScopeList = (ReadableArray) Utils.argumentNullCheck(arguments, FIELD_AUTH_SCOPES_LIST);

        if (fieldName == null) {
            promise.reject("3014", "Null accountAuthParams Parameter");
        } else {
            logger.startMethodExecutionTimer("signIn");
            accountAuthService = AccountAuthManager.getService(Objects.requireNonNull(getCurrentActivity()),
                Utils.toAccountAuthParams(requestOption, fieldName, authScopeList, promise));
            getCurrentActivity().startActivityForResult(accountAuthService.getSignInIntent(), REQUEST_CODE_LOG_IN);
            logger.sendSingleEvent("signIn");
            this.mSignInAccountPromise = promise;
        }
    }

    @ReactMethod
    public void signOut(final Promise promise) {
        if (accountAuthService != null) {
            logger.startMethodExecutionTimer("signOut");
            Task<Void> signOutTask = accountAuthService.signOut();
            logger.sendSingleEvent("signOut");
            signOutTask.addOnSuccessListener(task -> promise.resolve(true))
                .addOnFailureListener(e -> Utils.handleError(promise, e));
        } else {
            promise.reject("3001", "Null service");
        }
    }

    @ReactMethod
    public void getChannel(final Promise promise) {
        if (accountAuthService != null) {
            logger.startMethodExecutionTimer("getChannel");
            Task<AccountIcon> task = accountAuthService.getChannel();
            logger.sendSingleEvent("getChannel");
            task.addOnSuccessListener(accountIcon -> promise.resolve(Utils.parseAccountIcon(accountIcon)))
                .addOnFailureListener(e -> Utils.handleError(promise, e));
        } else {
            promise.reject("3001", "Null service");
        }
    }

    @ReactMethod
    public void silentSignIn(ReadableMap arguments, final Promise promise) {
        String fieldName = (String) Utils.argumentNullCheck(arguments, FIELD_ACCOUNT_AUTH_PARAMS);
        AccountAuthParams authParams = null;
        if (fieldName != null) {
            logger.startMethodExecutionTimer("silentSignIn");
            if (fieldName.equals("DEFAULT_AUTH_REQUEST_PARAM")) {
                authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).createParams();
            } else if (fieldName.equals("DEFAULT_AUTH_REQUEST_PARAM_GAME")) {
                authParams = new AccountAuthParamsHelper(
                    AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM_GAME).createParams();
            } else {
                promise.reject("3015", "Invalid accountAuthParams Parameter");
            }
            accountAuthService = AccountAuthManager.getService(Objects.requireNonNull(getCurrentActivity()),
                authParams);
            Task<AuthAccount> silentSignInTask = accountAuthService.silentSignIn();
            logger.sendSingleEvent("silentSignIn");
            silentSignInTask.addOnSuccessListener(
                authAccount -> promise.resolve(Utils.parseAuthAccount(authAccount, getReactApplicationContext())))
                .addOnFailureListener(e -> Utils.handleError(promise, e));
        } else {
            promise.reject("3014", "Null accountAuthParams Parameter");
        }
    }

    @ReactMethod
    public void cancelAuthorization(final Promise promise) {
        if (accountAuthService != null) {
            logger.startMethodExecutionTimer("cancelAuthorization");
            Task<Void> cancelAuthorizationTask = accountAuthService.cancelAuthorization();
            logger.sendSingleEvent("cancelAuthorization");
            cancelAuthorizationTask.addOnSuccessListener(task -> promise.resolve(true))
                .addOnFailureListener(e -> Utils.handleError(promise, e));
        } else {
            promise.reject("3001", "Null service");
        }
    }

    @ReactMethod
    public void getIndependentSignInIntent(String accessToken, final Promise promise) {
        if (accessToken != null) {
            logger.startMethodExecutionTimer("getIndependentSignInIntent");
            AccountAuthParams authParams = new AccountAuthParamsHelper().setProfile().createParams();
            accountAuthService = AccountAuthManager.getService(Objects.requireNonNull(getCurrentActivity()),
                authParams);
            this.mSignInAccountPromise = promise;
            getCurrentActivity().startActivityForResult(accountAuthService.getIndependentSignInIntent(accessToken),
                REQUEST_CODE_LOG_IN);
            logger.sendSingleEvent("getIndependentSignInIntent");
        } else {
            promise.reject("3017", "Null accessToken");
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
