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

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.UIManagerModule;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.rn.account.utils.Mapper;
import com.huawei.hms.rn.account.utils.Utils;
import com.huawei.hms.rn.account.constants.ClassConstants;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;
import com.huawei.hms.rn.account.logger.HMSLogger;
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HMSAccount extends ReactContextBaseJavaModule implements ActivityEventListener {
    private static final String MODULE_NAME = "HMSAccount";
    private static final int REQUEST_CODE_LOG_IN = 0;
    private static final String FIELD_HUAWEI_ID_AUTH_PARAMS = "huaweiIdAuthParams";
    private static final String FIELD_REQUEST_OPTION = "authRequestOption";
    private static final String FIELD_AUTH_SCOPES_LIST = "authScopeList";
    private HuaweiIdAuthService service;
    private Promise mSignInPromise;
    private HMSLogger logger;

    public HMSAccount(ReactApplicationContext reactContext) {
        super(reactContext);
        logger = HMSLogger.getInstance(reactContext);
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
        return constantsToBeExposed;
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE_LOG_IN && mSignInPromise != null) {
            Task<AuthHuaweiId> authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(intent);
            authHuaweiIdTask.addOnCompleteListener(newOnCompleteListener(mSignInPromise, Utils::parseAuthHuaweiId, "signIn"));
        }
    }

    @ReactMethod
    public void signIn(ReadableMap arguments, final Promise promise) {
        String fieldName = (String) Utils.argumentNullCheck(arguments, FIELD_HUAWEI_ID_AUTH_PARAMS);
        ReadableArray requestOption = (ReadableArray) Utils.argumentNullCheck(arguments, FIELD_REQUEST_OPTION);
        ReadableArray authScopeList = (ReadableArray) Utils.argumentNullCheck(arguments, FIELD_AUTH_SCOPES_LIST);

        if(fieldName == null) {
            promise.reject("3000", "Null huaweiIdAuthParams Parameter");
        } else {
            logger.startMethodExecutionTimer("signIn");
            service = HuaweiIdAuthManager.getService(Objects.requireNonNull(getCurrentActivity()), Utils.toHuaweiIdAuthParams(requestOption, fieldName, authScopeList, promise));
            getCurrentActivity().startActivityForResult(service.getSignInIntent(), REQUEST_CODE_LOG_IN);
            this.mSignInPromise = promise;
        }
    }

    @ReactMethod
    public void signOut(final Promise promise) {
        if(service != null) {
            logger.startMethodExecutionTimer("signOut");
            Task<Void> signOutTask = service.signOut();
            signOutTask.addOnCompleteListener(newOnCompleteListener(promise, voidMapper, "signOut"));
        } else {
            promise.reject("3001", "Null service");
        }
    }

    @ReactMethod
    public void silentSignIn(ReadableMap arguments, final Promise promise) {
        String fieldName = (String) Utils.argumentNullCheck(arguments, FIELD_HUAWEI_ID_AUTH_PARAMS);
        HuaweiIdAuthParams authParams = null;
        if (fieldName != null) {
            logger.startMethodExecutionTimer("silentSignIn");
            if (fieldName.equals("DEFAULT_AUTH_REQUEST_PARAM")) {
                authParams = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).createParams();
            } else if (fieldName.equals("DEFAULT_AUTH_REQUEST_PARAM_GAME")) {
                authParams = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM_GAME).createParams();
            } else {
                promise.reject("3003", "Invalid huaweiIdAuthParams Parameter");
            }
            service = HuaweiIdAuthManager.getService(Objects.requireNonNull(getCurrentActivity()), authParams);
            Task<AuthHuaweiId> silentSignInTask = service.silentSignIn();
            silentSignInTask.addOnCompleteListener(newOnCompleteListener(promise, Utils::parseAuthHuaweiId, "silentSignIn"));
        } else {
            promise.reject("3000", "Null huaweiIdAuthParams Parameter");
        }
    }

    @ReactMethod
    public void cancelAuthorization(final Promise promise) {
        if(service != null) {
            logger.startMethodExecutionTimer("cancelAuthorization");
            Task<Void> cancelAuthorizationTask = service.cancelAuthorization();
            cancelAuthorizationTask.addOnCompleteListener(newOnCompleteListener(promise, voidMapper, "cancelAuthorization"));
        } else {
            promise.reject("3001", "Null service");
        }
    }

    @ReactMethod
    public void getButtonInfo(final int viewId, final Promise promise) {
        UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(nativeViewHierarchyManager -> {
            View view = nativeViewHierarchyManager.resolveView(viewId);
            if (view instanceof HuaweiIdAuthButton) {
                HuaweiIdAuthButton button = (HuaweiIdAuthButton) view;
                promise.resolve(Utils.parseButton(button));
                return;
            }
            promise.resolve(null);
        });
    }

    @ReactMethod
    public void enableLogger() {
        logger.enableLogger();
    }

    @ReactMethod
    public void disableLogger() {
        logger.disableLogger();
    }

    // //////////////////////////////////////////////////////
    // Private utils
    // //////////////////////////////////////////////////////

    private <T> OnCompleteListener<T> newOnCompleteListener(final Promise promise, final Mapper<T, ReadableMap> mapper, String methodName) {
        return task -> {
            if (task.isSuccessful()) {
                logger.sendSingleEvent(methodName);
                promise.resolve(mapper.map(task.getResult()));
            } else {
                logger.sendSingleEvent(methodName, "-1");
                Utils.handleError(promise, task.getException());
            }
        };
    }

    private Mapper<Void, ReadableMap> voidMapper = aVoid -> (ReadableMap) null;

}
