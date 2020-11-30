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

package com.huawei.hms.rn.account;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.huawei.hms.rn.account.modules.HMSAccount;
import com.huawei.hms.rn.account.modules.HMSHuaweiIdAuthManager;
import com.huawei.hms.rn.account.modules.HMSHuaweiIdAuthTool;
import com.huawei.hms.rn.account.modules.HMSNetworkTool;
import com.huawei.hms.rn.account.modules.HMSReadSMSManager;
import com.huawei.hms.rn.account.views.HMSHuaweiIdAuthButton;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HmsAccountPackage implements ReactPackage {
    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(
            new HMSAccount(reactContext),
            new HMSReadSMSManager(reactContext),
            new HMSHuaweiIdAuthManager(reactContext),
            new HMSHuaweiIdAuthTool(reactContext),
            new HMSNetworkTool(reactContext)
        );
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.<ViewManager>singletonList(new HMSHuaweiIdAuthButton(reactContext));
    }
}
