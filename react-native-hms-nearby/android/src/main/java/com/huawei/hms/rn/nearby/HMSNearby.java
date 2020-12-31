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

package com.huawei.hms.rn.nearby;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.huawei.hms.rn.nearby.modules.HMSApplication;
import com.huawei.hms.rn.nearby.modules.HMSDiscovery;
import com.huawei.hms.rn.nearby.modules.HMSMessage;
import com.huawei.hms.rn.nearby.modules.HMSTransfer;
import com.huawei.hms.rn.nearby.modules.HMSWifiShare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HMSNearby implements ReactPackage {
    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new HMSApplication(reactContext));
        modules.add(new HMSTransfer(reactContext));
        modules.add(new HMSDiscovery(reactContext));
        modules.add(new HMSMessage(reactContext));
        modules.add(new HMSWifiShare(reactContext));

        return modules;
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

}
