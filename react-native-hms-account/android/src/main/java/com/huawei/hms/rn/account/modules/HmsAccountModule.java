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

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.rn.account.utils.Mapper;
import com.huawei.hms.rn.account.utils.Utils;
import com.huawei.hms.rn.account.constants.ClassConstants;
import com.huawei.hms.rn.account.constants.ScopeConstants;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class HmsAccountModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private static final String MODULE_NAME = "HmsAccount";

    private static final int REQUEST_CODE_LOG_IN = 0;

    private static final String FIELD_HUAWEI_ID_AUTH_PARAMS = "huaweiIdAuthParams";
    private static final String FIELD_SCOPES = "scopes";
    private static final String SET_METHOD_IDENTIFIER = "set";

    private HuaweiIdAuthService service;
    private Promise mSignInPromise;

    public HmsAccountModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constantsToBeExposed = new HashMap<>();
        for (ClassConstants constant : ClassConstants.values()) {
            constantsToBeExposed.put(constant.name(),
                constant.getIntValue() == null ? constant.getStringValue() : constant.getIntValue());
        }
        for (ScopeConstants constant : ScopeConstants.values()) {
            constantsToBeExposed.put(constant.name(), constant.getValue());
        }
        return constantsToBeExposed;
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE_LOG_IN && mSignInPromise != null) {
            Task<AuthHuaweiId> authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(intent);
            authHuaweiIdTask.addOnCompleteListener(newOnCompleteListener(mSignInPromise, Utils::parseAuthHuaweiId));
        }
    }

    @ReactMethod
    public void signIn(ReadableMap arguments, final Promise promise) {
        String fieldName = arguments.getString(FIELD_HUAWEI_ID_AUTH_PARAMS);
        ReadableArray scopes = arguments.getArray(FIELD_SCOPES);
        try {
            HuaweiIdAuthParams authParams = (HuaweiIdAuthParams) HuaweiIdAuthParams.class.getField(Objects.requireNonNull(fieldName)).get(null);
            HuaweiIdAuthParamsHelper huaweiIdAuthParamsHelper = new HuaweiIdAuthParamsHelper(authParams);
            for (int index = 0; index < Objects.requireNonNull(scopes).size(); index++) {
                String scope = scopes.getString(index);
                String methodToBeCalledName = SET_METHOD_IDENTIFIER +
                    ((scope.charAt(0) + "").toUpperCase(Locale.ENGLISH)) + scope.substring(1);
                huaweiIdAuthParamsHelper = (HuaweiIdAuthParamsHelper) HuaweiIdAuthParamsHelper.class
                    .getMethod(Objects.requireNonNull(methodToBeCalledName)).invoke(huaweiIdAuthParamsHelper);
            }
            HuaweiIdAuthParams createdAuthParams = huaweiIdAuthParamsHelper.createParams();
            service = HuaweiIdAuthManager.getService(Objects.requireNonNull(getCurrentActivity()), createdAuthParams);
            getCurrentActivity().startActivityForResult(service.getSignInIntent(), REQUEST_CODE_LOG_IN);
            this.mSignInPromise = promise;
        } catch (RuntimeException | NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Utils.handleError(promise, e);
        }
    }

    @ReactMethod
    public void signOut(final Promise promise) {
        Task<Void> signOutTask = service == null ? HuaweiIdAuthManager
            .getService(Objects.requireNonNull(getCurrentActivity()), HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .signOut() : service.signOut();

        signOutTask.addOnCompleteListener(newOnCompleteListener(promise, voidMapper));
    }

    @ReactMethod
    public void silentSignIn(final Promise promise) {
        Task<AuthHuaweiId> silentSignInTask = service == null ? HuaweiIdAuthManager
            .getService(Objects.requireNonNull(getCurrentActivity()), HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .silentSignIn() : service.silentSignIn();
        silentSignInTask.addOnCompleteListener(newOnCompleteListener(promise, Utils::parseAuthHuaweiId));
    }

    @ReactMethod
    public void cancelAuthorization(final Promise promise) {
        Task<Void> cancelAuthorizationTask = service == null
            ? HuaweiIdAuthManager.getService(Objects.requireNonNull(getCurrentActivity()),
            HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).cancelAuthorization()
            : service.cancelAuthorization();
        cancelAuthorizationTask.addOnCompleteListener(newOnCompleteListener(promise, voidMapper));
    }

    // //////////////////////////////////////////////////////
    // Private utils
    // //////////////////////////////////////////////////////

    private <T> OnCompleteListener<T> newOnCompleteListener(final Promise promise, final Mapper<T, ReadableMap> mapper) {
        return task -> {
            if (task.isSuccessful()) {
                promise.resolve(mapper.map(task.getResult()));
            } else {
                Utils.handleError(promise, task.getException());
            }
        };
    }

    private Mapper<Void, ReadableMap> voidMapper = aVoid -> Arguments.createMap();
}
