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

package com.huawei.hms.rn.push;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.huawei.hms.rn.push.opendevice.HmsPushOpenDevice;
import com.huawei.hms.rn.push.local.HmsLocalNotification;
import com.huawei.hms.rn.push.remote.HmsMessagePublisher;
import com.huawei.hms.rn.push.remote.HmsPushInstanceId;
import com.huawei.hms.rn.push.remote.HmsPushMessaging;
import com.huawei.hms.rn.push.remote.HmsPushProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HmsPushPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {

        List<NativeModule> modules = new ArrayList<>();

        modules.add(new HmsPushInstanceId(reactContext));
        modules.add(new HmsPushMessaging(reactContext));
        modules.add(new HmsMessagePublisher(reactContext));
        modules.add(new HmsLocalNotification(reactContext));
        modules.add(new HmsPushOpenDevice(reactContext));
        modules.add(new HmsPushProfile(reactContext));

        return modules;
    }

    // Deprecated from RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {

        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {

        return Collections.emptyList();
    }
}
