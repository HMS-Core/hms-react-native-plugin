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

package com.huawei.hms.rn.mlimage;

import androidx.annotation.NonNull;

import com.huawei.hms.rn.mlimage.commonservices.HMSApplication;
import com.huawei.hms.rn.mlimage.commonservices.HMSComposite;
import com.huawei.hms.rn.mlimage.commonservices.HMSLensEngine;
import com.huawei.hms.rn.mlimage.helpers.views.HMSSurfaceView;
import com.huawei.hms.rn.mlimage.imagerelatedservices.HMSDocumentSkewCorrection;
import com.huawei.hms.rn.mlimage.imagerelatedservices.HMSFrame;
import com.huawei.hms.rn.mlimage.imagerelatedservices.HMSImageClassification;
import com.huawei.hms.rn.mlimage.imagerelatedservices.HMSImageSegmentation;
import com.huawei.hms.rn.mlimage.imagerelatedservices.HMSImageSuperResolution;
import com.huawei.hms.rn.mlimage.imagerelatedservices.HMSLandmarkRecognition;
import com.huawei.hms.rn.mlimage.imagerelatedservices.HMSObjectRecognition;
import com.huawei.hms.rn.mlimage.imagerelatedservices.HMSProductVisionSearch;
import com.huawei.hms.rn.mlimage.imagerelatedservices.HMSSceneDetection;
import com.huawei.hms.rn.mlimage.imagerelatedservices.HMSTextImageSuperResolution;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HMSMLImage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new HMSApplication(reactContext));
        modules.add(new HMSFrame(reactContext));
        modules.add(new HMSImageClassification(reactContext));
        modules.add(new HMSObjectRecognition(reactContext));
        modules.add(new HMSLandmarkRecognition(reactContext));
        modules.add(new HMSImageSegmentation(reactContext));
        modules.add(new HMSImageSuperResolution(reactContext));
        modules.add(new HMSProductVisionSearch(reactContext));
        modules.add(new HMSDocumentSkewCorrection(reactContext));
        modules.add(new HMSTextImageSuperResolution(reactContext));
        modules.add(new HMSSceneDetection(reactContext));
        modules.add(new HMSLensEngine(reactContext));
        modules.add(new HMSComposite(reactContext));

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
