/*
 * Copyright 2023. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.hms.rn.mllanguage;

import androidx.annotation.NonNull;

import com.huawei.hms.rn.mllanguage.commonservices.HMSApplication;
import com.huawei.hms.rn.mllanguage.languagevoicerelatedservices.HMSAft;
import com.huawei.hms.rn.mllanguage.languagevoicerelatedservices.HMSAsr;
import com.huawei.hms.rn.mllanguage.languagevoicerelatedservices.HMSLanguageDetection;
import com.huawei.hms.rn.mllanguage.languagevoicerelatedservices.HMSSoundDetect;
import com.huawei.hms.rn.mllanguage.languagevoicerelatedservices.HMSSpeechRtt;
import com.huawei.hms.rn.mllanguage.languagevoicerelatedservices.HMSTextToSpeech;
import com.huawei.hms.rn.mllanguage.languagevoicerelatedservices.HMSTranslate;
import com.huawei.hms.rn.mllanguage.modelrelatedservices.HMSModelDownload;
import com.huawei.hms.rn.mllanguage.modelrelatedservices.HMSCustomModel;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HMSML implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new HMSApplication(reactContext));
        modules.add(new HMSTranslate(reactContext));
        modules.add(new HMSLanguageDetection(reactContext));
        modules.add(new HMSAsr(reactContext));
        modules.add(new HMSAft(reactContext));
        modules.add(new HMSSpeechRtt(reactContext));
        modules.add(new HMSSoundDetect(reactContext));
        modules.add(new HMSTextToSpeech(reactContext));
        modules.add(new HMSModelDownload(reactContext));
        modules.add(new HMSCustomModel(reactContext));

        return modules;
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
