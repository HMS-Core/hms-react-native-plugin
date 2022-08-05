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

package com.huawei.hms.rn.ml;

import androidx.annotation.NonNull;

import com.huawei.hms.rn.ml.commonservices.HMSApplication;
import com.huawei.hms.rn.ml.commonservices.HMSComposite;
import com.huawei.hms.rn.ml.commonservices.HMSLensEngine;
import com.huawei.hms.rn.ml.facebodyrelatedservices.HMSFaceRecognition;
import com.huawei.hms.rn.ml.facebodyrelatedservices.HMSFaceVerification;
import com.huawei.hms.rn.ml.facebodyrelatedservices.HMSGestureDetection;
import com.huawei.hms.rn.ml.facebodyrelatedservices.HMSHandKeypointDetection;
import com.huawei.hms.rn.ml.facebodyrelatedservices.HMSLivenessDetection;
import com.huawei.hms.rn.ml.facebodyrelatedservices.HMSSkeletonDetection;
import com.huawei.hms.rn.ml.helpers.views.HMSSurfaceView;
import com.huawei.hms.rn.ml.imagerelatedservices.HMSDocumentSkewCorrection;
import com.huawei.hms.rn.ml.imagerelatedservices.HMSFrame;
import com.huawei.hms.rn.ml.imagerelatedservices.HMSImageClassification;
import com.huawei.hms.rn.ml.imagerelatedservices.HMSImageSegmentation;
import com.huawei.hms.rn.ml.imagerelatedservices.HMSImageSuperResolution;
import com.huawei.hms.rn.ml.imagerelatedservices.HMSLandmarkRecognition;
import com.huawei.hms.rn.ml.imagerelatedservices.HMSObjectRecognition;
import com.huawei.hms.rn.ml.imagerelatedservices.HMSProductVisionSearch;
import com.huawei.hms.rn.ml.imagerelatedservices.HMSSceneDetection;
import com.huawei.hms.rn.ml.imagerelatedservices.HMSTextImageSuperResolution;
import com.huawei.hms.rn.ml.languagevoicerelatedservices.HMSAft;
import com.huawei.hms.rn.ml.languagevoicerelatedservices.HMSAsr;
import com.huawei.hms.rn.ml.languagevoicerelatedservices.HMSLanguageDetection;
import com.huawei.hms.rn.ml.languagevoicerelatedservices.HMSSoundDetect;
import com.huawei.hms.rn.ml.languagevoicerelatedservices.HMSSpeechRtt;
import com.huawei.hms.rn.ml.languagevoicerelatedservices.HMSTextToSpeech;
import com.huawei.hms.rn.ml.languagevoicerelatedservices.HMSTranslate;
import com.huawei.hms.rn.ml.modelrelatedservices.HMSCustomModel;
import com.huawei.hms.rn.ml.modelrelatedservices.HMSModelDownload;
import com.huawei.hms.rn.ml.nlprelatedservices.HMSTextEmbedding;
import com.huawei.hms.rn.ml.textrelatedservices.HMSBankCardRecognition;
import com.huawei.hms.rn.ml.textrelatedservices.HMSDocumentRecognition;
import com.huawei.hms.rn.ml.textrelatedservices.HMSFormRecognition;
import com.huawei.hms.rn.ml.textrelatedservices.HMSGeneralCardRecognition;
import com.huawei.hms.rn.ml.textrelatedservices.HMSIDCardRecognition;
import com.huawei.hms.rn.ml.textrelatedservices.HMSTextRecognition;
import com.huawei.hms.rn.ml.textrelatedservices.HMSVietnamCardRecognition;

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
        modules.add(new HMSFrame(reactContext));
        modules.add(new HMSTextRecognition(reactContext));
        modules.add(new HMSDocumentRecognition(reactContext));
        modules.add(new HMSBankCardRecognition(reactContext));
        modules.add(new HMSGeneralCardRecognition(reactContext));
        modules.add(new HMSIDCardRecognition(reactContext));
        modules.add(new HMSVietnamCardRecognition(reactContext));
        modules.add(new HMSFormRecognition(reactContext));
        modules.add(new HMSTranslate(reactContext));
        modules.add(new HMSLanguageDetection(reactContext));
        modules.add(new HMSAsr(reactContext));
        modules.add(new HMSAft(reactContext));
        modules.add(new HMSSpeechRtt(reactContext));
        modules.add(new HMSSoundDetect(reactContext));
        modules.add(new HMSImageClassification(reactContext));
        modules.add(new HMSObjectRecognition(reactContext));
        modules.add(new HMSLandmarkRecognition(reactContext));
        modules.add(new HMSImageSegmentation(reactContext));
        modules.add(new HMSImageSuperResolution(reactContext));
        modules.add(new HMSProductVisionSearch(reactContext));
        modules.add(new HMSDocumentSkewCorrection(reactContext));
        modules.add(new HMSTextImageSuperResolution(reactContext));
        modules.add(new HMSSceneDetection(reactContext));
        modules.add(new HMSFaceRecognition(reactContext));
        modules.add(new HMSFaceVerification(reactContext));
        modules.add(new HMSModelDownload(reactContext));
        modules.add(new HMSTextToSpeech(reactContext));
        modules.add(new HMSTextEmbedding(reactContext));
        modules.add(new HMSCustomModel(reactContext));
        modules.add(new HMSSkeletonDetection(reactContext));
        modules.add(new HMSLivenessDetection(reactContext));
        modules.add(new HMSHandKeypointDetection(reactContext));
        modules.add(new HMSGestureDetection(reactContext));
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
