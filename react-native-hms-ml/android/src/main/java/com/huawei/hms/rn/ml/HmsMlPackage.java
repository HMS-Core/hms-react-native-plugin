package com.huawei.hms.rn.ml;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.huawei.hms.rn.ml.card.HmsBcrRecognition;
import com.huawei.hms.rn.ml.card.HmsIcrRecognition;
import com.huawei.hms.rn.ml.classification.HmsClassificationLocal;
import com.huawei.hms.rn.ml.classification.HmsClassificationRemote;
import com.huawei.hms.rn.ml.composite.HmsCompositeAnalyzer;
import com.huawei.hms.rn.ml.document.HmsDocumentRecognition;
import com.huawei.hms.rn.ml.face.HmsFaceRecognition;
import com.huawei.hms.rn.ml.frame.HmsFrame;
import com.huawei.hms.rn.ml.imseg.HmsImageSegmentation;
import com.huawei.hms.rn.ml.landmark.HmsLandmarkRecognition;
import com.huawei.hms.rn.ml.langdetect.HmsLanguageDetection;
import com.huawei.hms.rn.ml.lensengine.HmsLensEngine;
import com.huawei.hms.rn.ml.object.HmsObjectRecognition;
import com.huawei.hms.rn.ml.productvision.HmsProductVision;
import com.huawei.hms.rn.ml.text.HmsTextRecognitionLocal;
import com.huawei.hms.rn.ml.text.HmsTextRecognitionRemote;
import com.huawei.hms.rn.ml.translate.HmsTranslate;
import com.huawei.hms.rn.ml.tts.HmsTextToSpeech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;

public class HmsMlPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new HmsBcrRecognition(reactContext));
        modules.add(new HmsIcrRecognition(reactContext));
        modules.add(new HmsClassificationLocal(reactContext));
        modules.add(new HmsClassificationRemote(reactContext));
        modules.add(new HmsCompositeAnalyzer(reactContext));
        modules.add(new HmsDocumentRecognition(reactContext));
        modules.add(new HmsFaceRecognition(reactContext));
        modules.add(new HmsFrame(reactContext));
        modules.add(new HmsImageSegmentation(reactContext));
        modules.add(new HmsLandmarkRecognition(reactContext));
        modules.add(new HmsLanguageDetection(reactContext));
        modules.add(new HmsLensEngine(reactContext));
        modules.add(new HmsObjectRecognition(reactContext));
        modules.add(new HmsProductVision(reactContext));
        modules.add(new HmsTextRecognitionLocal(reactContext));
        modules.add(new HmsTextRecognitionRemote(reactContext));
        modules.add(new HmsTranslate(reactContext));
        modules.add(new HmsTextToSpeech(reactContext));

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
