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

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.rn.account.utils.Utils;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;

public class AuthHuaweiIdModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "AuthHuaweiId";

    public AuthHuaweiIdModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void createDefault(Promise promise) {
        promise.resolve(Utils.parseAuthHuaweiId(AuthHuaweiId.createDefault()));
    }

    @ReactMethod
    public void build(ReadableMap readableMap, Promise promise) {
        promise.resolve(Utils.parseAuthHuaweiId(Utils.toAuthHuaweiId(readableMap)));
    }

    @ReactMethod
    public void getRequestedScopes(ReadableMap readableMap, Promise promise) {
        promise.resolve(Utils.parseScopeSet(Utils.toAuthHuaweiId(readableMap).getRequestedScopes()));
    }
}
