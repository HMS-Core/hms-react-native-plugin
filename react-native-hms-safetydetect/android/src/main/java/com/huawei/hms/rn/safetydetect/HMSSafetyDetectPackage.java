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

package com.huawei.hms.rn.safetydetect;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.huawei.hms.rn.safetydetect.appcheck.AppCheckModule;
import com.huawei.hms.rn.safetydetect.huaweiapi.HuaweiApiModule;
import com.huawei.hms.rn.safetydetect.sysintegrity.SysIntegrityModule;
import com.huawei.hms.rn.safetydetect.urlcheck.UrlCheckModule;
import com.huawei.hms.rn.safetydetect.userdetect.UserDetectModule;
import com.huawei.hms.rn.safetydetect.wifidetect.WifiDetectModule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HMSSafetyDetectPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(
                new HuaweiApiModule(reactContext),
                new SysIntegrityModule(reactContext),
                new UserDetectModule(reactContext),
                new WifiDetectModule(reactContext),
                new UrlCheckModule(reactContext),
                new AppCheckModule(reactContext)
        );
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
