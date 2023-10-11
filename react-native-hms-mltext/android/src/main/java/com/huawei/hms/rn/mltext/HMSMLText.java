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

package com.huawei.hms.rn.mltext;

import androidx.annotation.NonNull;

import com.huawei.hms.rn.mltext.commonservices.HMSApplication;
import com.huawei.hms.rn.mltext.commonservices.HMSLensEngine;
import com.huawei.hms.rn.mltext.textrelatedservices.CustomViewHandler;
import com.huawei.hms.rn.mltext.helpers.views.HMSSurfaceView;
import com.huawei.hms.rn.mltext.nlprelatedservices.HMSTextEmbedding;
import com.huawei.hms.rn.mltext.textrelatedservices.HMSBankCardRecognition;
import com.huawei.hms.rn.mltext.textrelatedservices.HMSDocumentRecognition;
import com.huawei.hms.rn.mltext.textrelatedservices.HMSFormRecognition;
import com.huawei.hms.rn.mltext.textrelatedservices.HMSGeneralCardRecognition;
import com.huawei.hms.rn.mltext.textrelatedservices.HMSIDCardRecognition;
import com.huawei.hms.rn.mltext.textrelatedservices.HMSTextRecognition;
import com.huawei.hms.rn.mltext.textrelatedservices.HMSVietnamCardRecognition;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HMSMLText implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new HMSApplication(reactContext));
        modules.add(new HMSTextRecognition(reactContext));
        modules.add(new HMSDocumentRecognition(reactContext));
        modules.add(new HMSBankCardRecognition(reactContext));
        modules.add(new HMSGeneralCardRecognition(reactContext));
        modules.add(new HMSIDCardRecognition(reactContext));
        modules.add(new HMSVietnamCardRecognition(reactContext));
        modules.add(new HMSFormRecognition(reactContext));
        modules.add(new HMSTextEmbedding(reactContext));
        modules.add(new HMSLensEngine(reactContext));
        modules.add(new CustomViewHandler(reactContext));

        return modules;
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.singletonList(new HMSSurfaceView());
    }
}
