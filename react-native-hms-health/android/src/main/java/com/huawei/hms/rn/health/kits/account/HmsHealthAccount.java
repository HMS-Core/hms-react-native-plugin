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

package com.huawei.hms.rn.health.kits.account;

import static com.huawei.hms.rn.health.foundation.util.MapUtils.toWritableMap;
import static com.huawei.hms.rn.health.foundation.util.MapUtils.wrapWritableObjectWithSuccessStatus;
import static com.huawei.hms.rn.health.foundation.view.BaseProtocol.View.getActivity;
import static com.huawei.hms.rn.health.kits.account.util.AccountConstants.CONSTANTS_MAP;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.huawei.hms.common.ApiException;
import com.huawei.hms.rn.health.foundation.util.ExceptionHandler;
import com.huawei.hms.rn.health.foundation.util.HMSLogger;
import com.huawei.hms.rn.health.kits.account.listener.AccountResultListener;
import com.huawei.hms.rn.health.kits.account.util.AccountConstants;
import com.huawei.hms.rn.health.kits.account.util.AccountUtils;
import com.huawei.hms.rn.health.kits.account.viewmodel.AccountService;
import com.huawei.hms.rn.health.kits.account.viewmodel.AccountViewModel;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.hwid.HuaweiIdAuthAPIManager;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.result.HuaweiIdAuthResult;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.List;
import java.util.Map;

/**
 * {@link HmsHealthAccount} class is a module to do signIn in RN Side.
 *
 * @since v.5.0.1
 */
public class HmsHealthAccount extends ReactContextBaseJavaModule {

    private static final String TAG = HmsHealthAccount.class.getSimpleName();

    // Internal signInPickerPromise that will be used in activityEventListener
    private Promise signInPickerPromise;

    /**
     * activityEventListener listens for signing in method, obtain the authorization result and informs via Promise.
     * In case of failure informs RN Side via Promise.
     */
    private final ActivityEventListener activityEventListener = new ActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
            Log.i(TAG, "onActivityResult, requestCode=" + requestCode + ", resultCode=" + resultCode);
            if (signInPickerPromise != null) {
                if (requestCode == AccountConstants.RequestTypes.SIGN_IN.getValue()) {
                    if (intent == null) {
                        Log.e(TAG, "intent is null");
                        return;
                    }
                    // Obtain the authorization response from the intent.
                    HuaweiIdAuthResult result = HuaweiIdAuthAPIManager.HuaweiIdAuthAPIService.parseHuaweiIdFromIntent(
                        intent);
                    if (result != null) {
                        Log.d(TAG,
                            "handleSignInResult status = " + result.getStatus() + ", result = " + result.isSuccess());
                        if (result.isSuccess()) {
                            Log.d(TAG, "sign in is success");
                            // Obtain the authorization result.
                            HuaweiIdAuthResult authResult
                                = HuaweiIdAuthAPIManager.HuaweiIdAuthAPIService.parseHuaweiIdFromIntent(intent);

                            WritableMap authResultMap = toWritableMap(authResult.getHuaweiId());
                            signInPickerPromise.resolve(wrapWritableObjectWithSuccessStatus(authResultMap, true));
                            HMSLogger.getInstance(getReactApplicationContext())
                                .sendSingleEvent("HmsHealthAccount.signIn");

                        } else {
                            ExceptionHandler.INSTANCE.fail(signInPickerPromise, result);
                        }
                    } else {
                        ExceptionHandler.INSTANCE.fail(signInPickerPromise);
                    }

                }
            }
        }

        @Override
        public void onNewIntent(Intent intent) {
            Log.d(TAG, "onNewIntent");
        }
    };

    /**
     * Initialization
     */
    public HmsHealthAccount(ReactApplicationContext reactContext) {
        super(reactContext);
        // Internal context object
        reactContext.addActivityEventListener(activityEventListener);
    }

    /**
     * {@link HmsHealthAccount} class name.
     *
     * @return class name
     */
    @NonNull
    @Override
    public String getName() {
        return TAG;
    }

    /**
     * getConstants exposes the constant values
     * to RN Side.
     *
     * @return constants
     */
    @Override
    public Map<String, Object> getConstants() {
        return CONSTANTS_MAP;
    }

    /**
     * Sign-in and authorization method.
     * The authorization screen will display up if authorization has not granted by the current account.
     *
     * @param promise In the success scenario, {@link AuthHuaweiId} instance is returned , or {@link ApiException} is returned in the failure scenario.
     */
    @ReactMethod
    public void signIn(final ReadableArray scopeListReadableArr, final Promise promise) {
        HMSLogger.getInstance(getReactApplicationContext()).startMethodExecutionTimer("HmsHealthAccount.signIn");
        AccountService loginViewModel = new AccountViewModel();
        Log.i(TAG, "begin sign in");
        final List<Scope> scopeList = AccountUtils.INSTANCE.toScopeList(scopeListReadableArr);

        /* Configure authorization parameters. */
        final HuaweiIdAuthParamsHelper authParamsHelper = AccountUtils.INSTANCE.getAuthParamsHelper();
        final HuaweiIdAuthParams authParams = AccountUtils.INSTANCE.getAuthParams(authParamsHelper, scopeList);

        /* Initialize the HuaweiIdAuthService object. */
        final HuaweiIdAuthService authService = HuaweiIdAuthManager.getService(getActivity(getCurrentActivity()),
            authParams);

        loginViewModel.signIn(authService, new AccountResultListener() {
            @Override
            public void onSilentSignInFail() {
                Log.i(TAG, "begin sign in by intent");
                Intent signInIntent = authService.getSignInIntent();
                signInPickerPromise = promise;
                getActivity(getCurrentActivity()).startActivityForResult(signInIntent,
                    AccountConstants.RequestTypes.SIGN_IN.getValue());
            }

            @Override
            public void onSuccess(AuthHuaweiId result) {
                promise.resolve(wrapWritableObjectWithSuccessStatus(toWritableMap(result), true));
                HMSLogger.getInstance(getReactApplicationContext()).sendSingleEvent("HmsHealthAccount.signIn");
            }

            @Override
            public void onFail(Exception exception) {
                ExceptionHandler.INSTANCE.fail(exception, promise);
                HMSLogger.getInstance(getReactApplicationContext()).sendSingleEvent("HmsHealthAccount.signIn");
            }
        });
    }
}
