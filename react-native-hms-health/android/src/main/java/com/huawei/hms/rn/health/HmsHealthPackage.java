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

package com.huawei.hms.rn.health;

import com.huawei.hms.rn.health.kits.account.HmsHealthAccount;
import com.huawei.hms.rn.health.kits.activityrecords.HmsActivityRecordsController;
import com.huawei.hms.rn.health.kits.autorecorder.HmsAutoRecorderController;
import com.huawei.hms.rn.health.kits.blecontroller.HmsBleController;
import com.huawei.hms.rn.health.kits.consents.HmsConsentsController;
import com.huawei.hms.rn.health.kits.datacontroller.HmsDataController;
import com.huawei.hms.rn.health.kits.healthrecordcontroller.HmsHealthRecordController;
import com.huawei.hms.rn.health.kits.settings.HmsSettingController;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * HmsHealthPackage registers {@link HmsHealthPackage into RN Side.}
 *
 * @since v.5.0.5
 */
public class HmsHealthPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new HmsHealthAccount(reactContext));
        modules.add(new HmsActivityRecordsController(reactContext));
        modules.add(new HmsAutoRecorderController(reactContext));
        modules.add(new HmsBleController(reactContext));
        modules.add(new HmsConsentsController(reactContext));
        modules.add(new HmsDataController(reactContext));
        modules.add(new HmsSettingController(reactContext));
        modules.add(new HmsHealthRecordController(reactContext));
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}