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

package com.huawei.hms.rn.mlbody;

import androidx.annotation.NonNull;

import com.huawei.hms.rn.mlbody.commonservices.HMSApplication;
import com.huawei.hms.rn.mlbody.commonservices.HMSLensEngine;
import com.huawei.hms.rn.mlbody.facebodyrelatedservices.HMSFaceRecognition;
import com.huawei.hms.rn.mlbody.facebodyrelatedservices.HMSFaceVerification;
import com.huawei.hms.rn.mlbody.facebodyrelatedservices.HMSGestureDetection;
import com.huawei.hms.rn.mlbody.facebodyrelatedservices.HMSHandKeypointDetection;
import com.huawei.hms.rn.mlbody.facebodyrelatedservices.HMSInteractiveLivenessCustomDetectionHandler;
import com.huawei.hms.rn.mlbody.facebodyrelatedservices.HMSInteractiveLivenessDetection;
import com.huawei.hms.rn.mlbody.facebodyrelatedservices.HMSLivenessDetection;
import com.huawei.hms.rn.mlbody.facebodyrelatedservices.HMSSkeletonDetection;
import com.huawei.hms.rn.mlbody.helpers.views.HMSSurfaceView;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HMSMLBody implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new HMSApplication(reactContext));
        modules.add(new HMSFaceRecognition(reactContext));
        modules.add(new HMSFaceVerification(reactContext));
        modules.add(new HMSSkeletonDetection(reactContext));
        modules.add(new HMSLivenessDetection(reactContext));
        modules.add(new HMSHandKeypointDetection(reactContext));
        modules.add(new HMSGestureDetection(reactContext));
        modules.add(new HMSLensEngine(reactContext));
        modules.add(new HMSInteractiveLivenessDetection(reactContext));
        modules.add(new HMSInteractiveLivenessCustomDetectionHandler(reactContext));
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
