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
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.rn.account.utils.Utils;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.common.HuaweiIdAuthException;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;

import java.util.List;
import java.util.Objects;

public class HuaweiIdAuthManagerModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private static final String MODULE_NAME = "HuaweiIdAuthManager";
    private static final String FIELD_AUTH_HUAWEI_ID = "authHuaweiId";
    private static final int REQUEST_ADD_AUTH_SCOPES = 999;
    private Promise mAddAuthScopesPromise;

    public HuaweiIdAuthManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void getAuthResult(Promise promise) {
        promise.resolve(Utils.parseAuthHuaweiId(HuaweiIdAuthManager.getAuthResult()));
    }

    @ReactMethod
    public void getAuthResultWithScopes(ReadableMap arguments, Promise promise) {
        List<Scope> scopeList = Utils.toScopeList(Utils.getScopeArray(arguments));
        try {
            promise.resolve(Utils.parseAuthHuaweiId(HuaweiIdAuthManager.getAuthResultWithScopes((scopeList))));
        } catch (HuaweiIdAuthException e) {
            Utils.handleError(promise, e);
        }
    }

    @ReactMethod
    public void addAuthScopes(ReadableMap readableMap, Promise promise) {
        mAddAuthScopesPromise = promise;
        HuaweiIdAuthManager.addAuthScopes(Objects.requireNonNull(getCurrentActivity()),
            REQUEST_ADD_AUTH_SCOPES,
            Utils.toScopeList(Utils.getScopeArray(readableMap)));
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_ADD_AUTH_SCOPES && mAddAuthScopesPromise != null) {
            mAddAuthScopesPromise.resolve(true);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // ignored
    }

    @ReactMethod
    public void containScopes(ReadableMap readableMap, Promise promise) {
        AuthHuaweiId authHuaweiId =
            Utils.toAuthHuaweiId(Objects.requireNonNull
                (readableMap.getMap(FIELD_AUTH_HUAWEI_ID)));
        ReadableArray array = Utils.getScopeArray(readableMap);
        if (array != null) {
            List<Scope> scopeList = Utils.toScopeList(array);
            promise.resolve(HuaweiIdAuthManager.containScopes(authHuaweiId, scopeList));
        }
    }
}
