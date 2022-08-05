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

package com.huawei.hms.rn.ml.helpers.creators;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.huawei.hms.mlplugin.card.bcr.MLBcrCapture;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureConfig;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureFactory;
import com.huawei.hms.mlplugin.card.gcr.MLGcrCapture;
import com.huawei.hms.mlplugin.card.gcr.MLGcrCaptureConfig;
import com.huawei.hms.mlplugin.card.gcr.MLGcrCaptureFactory;
import com.huawei.hms.mlplugin.card.gcr.MLGcrCaptureUIConfig;
import com.huawei.hms.mlplugin.productvisionsearch.MLProductVisionSearchCapture;
import com.huawei.hms.mlplugin.productvisionsearch.MLProductVisionSearchCaptureConfig;
import com.huawei.hms.mlplugin.productvisionsearch.MLProductVisionSearchCaptureFactory;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.aft.cloud.MLRemoteAftSetting;
import com.huawei.hms.mlsdk.card.MLCardAnalyzerFactory;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzer;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzerSetting;
import com.huawei.hms.mlsdk.classification.MLImageClassificationAnalyzer;
import com.huawei.hms.mlsdk.classification.MLLocalClassificationAnalyzerSetting;
import com.huawei.hms.mlsdk.classification.MLRemoteClassificationAnalyzerSetting;
import com.huawei.hms.mlsdk.common.LensEngine;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.common.MLCompositeAnalyzer;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.custom.MLCustomLocalModel;
import com.huawei.hms.mlsdk.custom.MLCustomRemoteModel;
import com.huawei.hms.mlsdk.custom.MLModelDataType;
import com.huawei.hms.mlsdk.custom.MLModelExecutorSettings;
import com.huawei.hms.mlsdk.custom.MLModelInputOutputSettings;
import com.huawei.hms.mlsdk.custom.MLModelInputs;
import com.huawei.hms.mlsdk.document.MLDocumentAnalyzer;
import com.huawei.hms.mlsdk.document.MLDocumentSetting;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionAnalyzer;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionAnalyzerFactory;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionAnalyzerSetting;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzer;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.face.face3d.ML3DFaceAnalyzer;
import com.huawei.hms.mlsdk.face.face3d.ML3DFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.faceverify.MLFaceVerificationAnalyzer;
import com.huawei.hms.mlsdk.faceverify.MLFaceVerificationAnalyzerFactory;
import com.huawei.hms.mlsdk.faceverify.MLFaceVerificationAnalyzerSetting;
import com.huawei.hms.mlsdk.fr.MLFormRecognitionAnalyzer;
import com.huawei.hms.mlsdk.fr.MLFormRecognitionAnalyzerFactory;
import com.huawei.hms.mlsdk.fr.MLFormRecognitionAnalyzerSetting;
import com.huawei.hms.mlsdk.gesture.MLGestureAnalyzer;
import com.huawei.hms.mlsdk.gesture.MLGestureAnalyzerFactory;
import com.huawei.hms.mlsdk.gesture.MLGestureAnalyzerSetting;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypointAnalyzer;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypointAnalyzerFactory;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypointAnalyzerSetting;
import com.huawei.hms.mlsdk.imagesuperresolution.MLImageSuperResolutionAnalyzer;
import com.huawei.hms.mlsdk.imagesuperresolution.MLImageSuperResolutionAnalyzerFactory;
import com.huawei.hms.mlsdk.imagesuperresolution.MLImageSuperResolutionAnalyzerSetting;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationScene;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmarkAnalyzer;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmarkAnalyzerSetting;
import com.huawei.hms.mlsdk.langdetect.MLLangDetectorFactory;
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetector;
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetectorSetting;
import com.huawei.hms.mlsdk.langdetect.local.MLLocalLangDetector;
import com.huawei.hms.mlsdk.langdetect.local.MLLocalLangDetectorSetting;
import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCaptureConfig;
import com.huawei.hms.mlsdk.model.download.MLModelDownloadStrategy;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.objects.MLObjectAnalyzer;
import com.huawei.hms.mlsdk.objects.MLObjectAnalyzerSetting;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.MLRemoteProductVisionSearchAnalyzer;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.MLRemoteProductVisionSearchAnalyzerSetting;
import com.huawei.hms.mlsdk.scd.MLSceneDetectionAnalyzer;
import com.huawei.hms.mlsdk.scd.MLSceneDetectionAnalyzerFactory;
import com.huawei.hms.mlsdk.scd.MLSceneDetectionAnalyzerSetting;
import com.huawei.hms.mlsdk.skeleton.MLSkeletonAnalyzer;
import com.huawei.hms.mlsdk.skeleton.MLSkeletonAnalyzerFactory;
import com.huawei.hms.mlsdk.skeleton.MLSkeletonAnalyzerSetting;
import com.huawei.hms.mlsdk.speechrtt.MLSpeechRealTimeTranscriptionConfig;
import com.huawei.hms.mlsdk.speechrtt.MLSpeechRealTimeTranscriptionConstants;
import com.huawei.hms.mlsdk.text.MLLocalTextSetting;
import com.huawei.hms.mlsdk.text.MLRemoteTextSetting;
import com.huawei.hms.mlsdk.text.MLTextAnalyzer;
import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingAnalyzer;
import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingAnalyzerFactory;
import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingSetting;
import com.huawei.hms.mlsdk.translate.MLTranslatorFactory;
import com.huawei.hms.mlsdk.translate.cloud.MLRemoteTranslateSetting;
import com.huawei.hms.mlsdk.translate.cloud.MLRemoteTranslator;
import com.huawei.hms.mlsdk.translate.local.MLLocalTranslateSetting;
import com.huawei.hms.mlsdk.translate.local.MLLocalTranslator;
import com.huawei.hms.mlsdk.translate.local.MLLocalTranslatorModel;
import com.huawei.hms.mlsdk.tts.MLTtsConfig;
import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import com.huawei.hms.mlsdk.tts.MLTtsLocalModel;
import com.huawei.hms.rn.ml.helpers.fragments.HMSProductFragment;
import com.huawei.hms.rn.ml.helpers.transactors.HMS2DFaceAnalyzerTransactor;
import com.huawei.hms.rn.ml.helpers.transactors.HMS3DFaceAnalyzerTransactor;
import com.huawei.hms.rn.ml.helpers.transactors.HMSClassificationAnalyzerTransactor;
import com.huawei.hms.rn.ml.helpers.transactors.HMSGestureTransactor;
import com.huawei.hms.rn.ml.helpers.transactors.HMSHandKeypointTransactor;
import com.huawei.hms.rn.ml.helpers.transactors.HMSObjectAnalyzerTransactor;
import com.huawei.hms.rn.ml.helpers.transactors.HMSSceneDetectionAnalyzerTransactor;
import com.huawei.hms.rn.ml.helpers.transactors.HMSSkeletonAnalyzerTransactor;
import com.huawei.hms.rn.ml.helpers.transactors.HMSTextAnalyzerTransactor;
import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class HMSObjectCreator {
    private static final String TAG = HMSObjectCreator.class.getSimpleName();

    private static volatile HMSObjectCreator instance;

    public static HMSObjectCreator getInstance() {
        if (instance == null) {
            synchronized (HMSObjectCreator.class) {
                if (instance == null) {
                    instance = new HMSObjectCreator();
                }
            }
        }
        return instance;
    }

    /**
     * Creates MLRemoteAftSetting object
     *
     * @param readableMap configuration
     * @return MLRemoteAftSetting object
     */
    public MLRemoteAftSetting createRemoteAftSetting(ReadableMap readableMap) {
        MLRemoteAftSetting.Factory setting = new MLRemoteAftSetting.Factory();

        if (readableMap == null) {
            Log.i(TAG, "MLRemoteAftSetting object is created using default options.");
            return setting.create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "languageCode", ReadableType.String)) {
            setting.setLanguageCode(readableMap.getString("languageCode"));
            Log.i(TAG, "MLRemoteAftSetting languageCode option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "enablePunctuation", ReadableType.Boolean)) {
            setting.enablePunctuation(readableMap.getBoolean("enablePunctuation"));
            Log.i(TAG, "MLRemoteAftSetting enablePunctuation option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "enableWordTimeOffset", ReadableType.Boolean)) {
            setting.enableWordTimeOffset(readableMap.getBoolean("enableWordTimeOffset"));
            Log.i(TAG, "MLRemoteAftSetting enableWordTimeOffset option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "enableSentenceTimeOffset", ReadableType.Boolean)) {
            setting.enableSentenceTimeOffset(readableMap.getBoolean("enableSentenceTimeOffset"));
            Log.i(TAG, "MLRemoteAftSetting enableSentenceTimeOffset option set.");
        }
        return setting.create();
    }

    /**
     * Creates remote language detector
     *
     * @param trustedThreshold threshold value for detection
     * @return MLRemoteLangDetector object
     */
    public MLRemoteLangDetector createRemoteLanguageDetector(double trustedThreshold) {
        return MLLangDetectorFactory.getInstance()
            .getRemoteLangDetector(createRemoteLanguageDetectorSetting(trustedThreshold));
    }

    /**
     * Creates local language detector
     *
     * @param trustedThreshold threshold value for detection
     * @return MLRemoteLangDetector object
     */
    public MLLocalLangDetector createLocalLanguageDetector(double trustedThreshold) {
        return MLLangDetectorFactory.getInstance()
            .getLocalLangDetector(createLocalLanguageDetectorSetting(trustedThreshold));
    }

    /**
     * Creates MLRemoteLangDetectorSetting object
     *
     * @param trustedThreshold threshold value for detection
     * @return MLRemoteLangDetectorSetting object
     */
    private MLRemoteLangDetectorSetting createRemoteLanguageDetectorSetting(double trustedThreshold) {
        return new MLRemoteLangDetectorSetting.Factory().setTrustedThreshold((float) trustedThreshold).create();
    }

    /**
     * Creates MLLocalLangDetectorSetting object
     *
     * @param trustedThreshold threshold value for detection
     * @return MLLocalLangDetectorSetting object
     */
    private MLLocalLangDetectorSetting createLocalLanguageDetectorSetting(double trustedThreshold) {
        return new MLLocalLangDetectorSetting.Factory().setTrustedThreshold((float) trustedThreshold).create();
    }

    /**
     * Creates MLSpeechRealTimeTranscriptionConfig object
     *
     * @param readableMap configuration
     * @return MLSpeechRealTimeTranscriptionConfig object
     */
    public MLSpeechRealTimeTranscriptionConfig createSpeechRealtimeTranscriptionConfig(ReadableMap readableMap) {
        String language = MLSpeechRealTimeTranscriptionConstants.LAN_EN_US;
        boolean enablePunctuation = true;
        boolean enableWordTimeOffset = false;
        boolean enableSentenceTimeOffset = false;

        if (readableMap == null) {
            Log.i(TAG, "MLSpeechRealTimeTranscriptionConfig object is created using default options.");
            return new MLSpeechRealTimeTranscriptionConfig.Factory().setLanguage(language)
                .enableWordTimeOffset(false)
                .enableSentenceTimeOffset(false)
                .enablePunctuation(true)
                .create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "language", ReadableType.String)) {
            language = readableMap.getString("language");
            Log.i(TAG, "MLSpeechRealTimeTranscriptionConfig language option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "enablePunctuation", ReadableType.Boolean)) {
            enablePunctuation = readableMap.getBoolean("enablePunctuation");
            Log.i(TAG, "MLSpeechRealTimeTranscriptionConfig enablePunctuation option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "enableSentenceTimeOffset", ReadableType.Boolean)) {
            enableSentenceTimeOffset = readableMap.getBoolean("enableSentenceTimeOffset");
            Log.i(TAG, "MLSpeechRealTimeTranscriptionConfig enableSentenceTimeOffset option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "enableWordTimeOffset", ReadableType.Boolean)) {
            enableWordTimeOffset = readableMap.getBoolean("enableWordTimeOffset");
            Log.i(TAG, "MLSpeechRealTimeTranscriptionConfig enableWordTimeOffset option set.");
        }

        return new MLSpeechRealTimeTranscriptionConfig.Factory().setLanguage(language)
            .enableWordTimeOffset(enableWordTimeOffset)
            .enableSentenceTimeOffset(enableSentenceTimeOffset)
            .enablePunctuation(enablePunctuation)
            .create();
    }

    /**
     * Creates MLTtsConfig object
     *
     * @param readableMap configuration
     * @return MLTtsConfig object
     */
    public MLTtsConfig createTtsConfiguration(ReadableMap readableMap) {
        double speed = 1.0F;
        double volume = 1.0F;
        String language = MLTtsConstants.TTS_EN_US;
        String person = MLTtsConstants.TTS_SPEAKER_FEMALE_EN;
        String synthesizeMode = MLTtsConstants.TTS_OFFLINE_MODE;

        if (readableMap == null) {
            Log.i(TAG, "MLTtsConfig object is created using default options.");
            return new MLTtsConfig().setVolume((float) volume)
                .setSpeed((float) speed)
                .setLanguage(language)
                .setPerson(person);
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "volume", ReadableType.Number)) {
            volume = readableMap.getDouble("volume");
            Log.i(TAG, "MLTtsConfig volume option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "speed", ReadableType.Number)) {
            speed = readableMap.getDouble("speed");
            Log.i(TAG, "MLTtsConfig speed option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "language", ReadableType.String)) {
            language = readableMap.getString("language");
            Log.i(TAG, "MLTtsConfig language option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "person", ReadableType.String)) {
            person = readableMap.getString("person");
            Log.i(TAG, "MLTtsConfig person option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "synthesizeMode", ReadableType.String)) {
            synthesizeMode = readableMap.getString("synthesizeMode");
            Log.i(TAG, "MLTtsConfig synthesizeMode option set.");
        }

        return new MLTtsConfig().setVolume((float) volume)
            .setSpeed((float) speed)
            .setLanguage(language)
            .setPerson(person)
            .setSynthesizeMode(synthesizeMode);
    }

    /**
     * Creates remote translator
     *
     * @param translatorSetting setting
     * @return MLRemoteTranslator object
     */
    public MLRemoteTranslator createRemoteTranslator(ReadableMap translatorSetting) {
        return MLTranslatorFactory.getInstance().getRemoteTranslator(createRemoteTranslateSetting(translatorSetting));
    }

    /**
     * Creates local translator
     *
     * @param translatorSetting setting
     * @return MLRemoteTranslator object
     */
    public MLLocalTranslator createLocalTranslator(ReadableMap translatorSetting) {
        return MLTranslatorFactory.getInstance().getLocalTranslator(createLocalTranslateSetting(translatorSetting));
    }

    /**
     * Creates MLModelDownloadStrategy object
     *
     * @param readableMap configuration
     * @return MLModelDownloadStrategy object
     */
    public MLModelDownloadStrategy createModelDownloadStrategy(ReadableMap readableMap) {
        MLModelDownloadStrategy.Factory creator = new MLModelDownloadStrategy.Factory();

        if (readableMap == null) {
            Log.i(TAG, "MLModelDownloadStrategy object is created using default options.");
            return creator.create();
        }
        if (HMSUtils.getInstance().boolKeyCheck(readableMap, "needWifi")) {
            creator.needWifi();
            Log.i(TAG, "MLModelDownloadStrategy needWifi option set");
        }
        if (HMSUtils.getInstance().boolKeyCheck(readableMap, "needCharging")) {
            creator.needCharging();
            Log.i(TAG, "MLModelDownloadStrategy needCharging option set");
        }
        if (HMSUtils.getInstance().boolKeyCheck(readableMap, "needDeviceIdle")) {
            creator.needDeviceIdle();
            Log.i(TAG, "MLModelDownloadStrategy needDeviceIdle option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "region", ReadableType.Number)) {
            creator.setRegion(readableMap.getInt("region"));
            Log.i(TAG, "MLModelDownloadStrategy region option set");
        }
        return creator.create();
    }

    /**
     * Creates MLTextEmbeddingSetting object
     *
     * @param language language code default "zh"
     * @return MLTextEmbeddingSetting object
     */
    private MLTextEmbeddingSetting createTextEmbeddingSetting(String language) {
        return new MLTextEmbeddingSetting.Factory().setLanguage(language).create();
    }

    /**
     * Creates Text Embedding Analyzer
     *
     * @param language language code default "zh"
     * @return MLTextEmbeddingAnalyzer
     */
    public MLTextEmbeddingAnalyzer createTextEmbeddingAnalyzer(String language) {
        return MLTextEmbeddingAnalyzerFactory.getInstance()
            .getMLTextEmbeddingAnalyzer(createTextEmbeddingSetting(language));
    }

    /**
     * Creates model input output settings
     *
     * @param readableMap model configuration
     * @return MLModelInputOutputSettings
     */
    public MLModelInputOutputSettings createCustomModelInputOutputSetting(ReadableMap readableMap) {
        if (readableMap == null) {
            Log.i(TAG, "MLModelInputOutputSettings object is null.");
            return null;
        }

        int width = 224;
        int height = 224;
        int outputSize = 1001;

        if (HMSUtils.getInstance().hasValidKey(readableMap, "inputFormat", ReadableType.Map)) {
            ReadableMap inputFormatMap = readableMap.getMap("inputFormat");
            if (HMSUtils.getInstance().hasValidKey(inputFormatMap, "width", ReadableType.Number)) {
                width = inputFormatMap.getInt("width");
                Log.i(TAG, "MLModelInputOutputSettings inputFormat width option set.");
            }
            if (HMSUtils.getInstance().hasValidKey(inputFormatMap, "height", ReadableType.Number)) {
                height = inputFormatMap.getInt("height");
                Log.i(TAG, "MLModelInputOutputSettings inputFormat height option set.");
            }
        }

        if (HMSUtils.getInstance().hasValidKey(readableMap, "outputFormat", ReadableType.Map)) {
            ReadableMap outputFormatMap = readableMap.getMap("outputFormat");
            if (HMSUtils.getInstance().hasValidKey(outputFormatMap, "outputSize", ReadableType.Number)) {
                outputSize = outputFormatMap.getInt("outputSize");
                Log.i(TAG, "MLModelInputOutputSettings outputFormat dimensions option set.");
            }
        }

        try {
            Log.i(TAG, "MLModelInputOutputSettings object created.");
            return new MLModelInputOutputSettings.Factory().setInputFormat(1, MLModelDataType.FLOAT32,
                new int[] {1, 3, height, width})
                .setOutputFormat(1, MLModelDataType.FLOAT32, new int[] {1, outputSize})
                .create();
        } catch (MLException e) {
            Log.i(TAG, "MLModelInputOutputSettings is not created :" + e.getMessage());
            return null;
        }
    }

    /**
     * Creates local model
     *
     * @param localModelConfiguration model configuration
     * @return MLCustomLocalModel
     */
    private MLCustomLocalModel createCustomLocalModel(ReadableMap localModelConfiguration) {
        String assetPath = "";
        String localFullPath = "";
        String modelName = "";

        if (localModelConfiguration == null) {
            Log.i(TAG, "MLCustomLocalModel configuration is null");
            return null;
        }
        if (HMSUtils.getInstance().hasValidKey(localModelConfiguration, "assetPath", ReadableType.String)) {
            assetPath = localModelConfiguration.getString("assetPath");
            Log.i(TAG, "MLCustomLocalModel assetPath set");
        }
        if (HMSUtils.getInstance().hasValidKey(localModelConfiguration, "localFullPath", ReadableType.String)) {
            localFullPath = localModelConfiguration.getString("localFullPath");
            Log.i(TAG, "MLCustomLocalModel localFullPath set");
        }
        if (HMSUtils.getInstance().hasValidKey(localModelConfiguration, "modelName", ReadableType.String)) {
            modelName = localModelConfiguration.getString("modelName");
            Log.i(TAG, "MLCustomLocalModel modelName set");
        }

        if (TextUtils.isEmpty(modelName)) {
            Log.i(TAG, "MLCustomLocalModel modelName null or empty");
            return null;
        }

        if (!TextUtils.isEmpty(assetPath)) {
            return new MLCustomLocalModel.Factory(modelName).setAssetPathFile(assetPath).create();
        } else if (!TextUtils.isEmpty(localFullPath)) {
            return new MLCustomLocalModel.Factory(modelName).setLocalFullPathFile(localFullPath).create();
        } else {
            return null;
        }
    }

    /**
     * Creates remote model
     *
     * @param modelName model name
     * @return MLCustomRemoteModel
     */
    private MLCustomRemoteModel createCustomRemoteModel(String modelName) {
        if (TextUtils.isEmpty(modelName)) {
            Log.i(TAG, "MLCustomRemoteModel modelName is null or empty");
            return null;
        }
        Log.i(TAG, "MLCustomRemoteModel object is created.");
        return new MLCustomRemoteModel.Factory(modelName).create();
    }

    /**
     * Creates model executor settings
     *
     * @param isRemote Determines whether the process is in the cloud
     * @param modelSetting Model setting
     * @return MLModelExecutorSettings
     */
    public MLModelExecutorSettings createCustomModelExecutorSettings(boolean isRemote, ReadableMap modelSetting) {
        if (modelSetting == null) {
            Log.i(TAG, "MLModelExecutorSettings object is not created setting null.");
            return null;
        }
        if (isRemote) {
            Log.i(TAG, "MLModelExecutorSettings object is created - remote.");
            return new MLModelExecutorSettings.Factory(
                createCustomRemoteModel(modelSetting.getString("modelName"))).create();
        }
        Log.i(TAG, "MLModelExecutorSettings object is created - local.");
        return new MLModelExecutorSettings.Factory(createCustomLocalModel(modelSetting)).create();
    }

    /**
     * Create MLModelInputs object
     *
     * @param readableMap model objects container
     * @param context context object
     * @return MLModelInputs
     */
    public MLModelInputs createCustomModelInputs(ReadableMap readableMap, ReactApplicationContext context) {
        if (readableMap == null) {
            Log.i(TAG, "MLModelInputs object array is empty.");
            return null;
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "uri", ReadableType.String)) {
            try {
                int height = 224;
                int width = 224;
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),
                    Uri.parse(readableMap.getString("uri")));
                if (HMSUtils.getInstance().hasValidKey(readableMap, "height", ReadableType.Number)) {
                    height = readableMap.getInt("height");
                }
                if (HMSUtils.getInstance().hasValidKey(readableMap, "width", ReadableType.Number)) {
                    width = readableMap.getInt("width");
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                final float[][][][] input = new float[1][height][width][3];
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        int pixel = bitmap.getPixel(i, j);
                        input[0][j][i][0] = (Color.red(pixel) - 128.0f) / 128.0f;
                        input[0][j][i][1] = (Color.green(pixel) - 128.0f) / 128.0f;
                        input[0][j][i][2] = (Color.blue(pixel) - 128.0f) / 128.0f;
                    }
                }
                return new MLModelInputs.Factory().add(input).create();
            } catch (IOException | MLException e) {
                Log.i(TAG, "MLModelInputs : " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * Creates Remote Model from given configuration
     *
     * @param modelConfiguration model configuration
     * @return MLRemoteModel
     */
    public MLRemoteModel createRemoteModel(ReadableMap modelConfiguration) {
        if (modelConfiguration == null) {
            Log.i(TAG, "Given model configuration is null");
            return null;
        } else if (HMSUtils.getInstance().hasValidKey(modelConfiguration, "translate", ReadableType.Map)) {
            ReadableMap translate = modelConfiguration.getMap("translate");
            if (HMSUtils.getInstance().hasValidKey(translate, "languageCode", ReadableType.String)) {
                Log.i(TAG, "translate language code is set and object created");
                return new MLLocalTranslatorModel.Factory(translate.getString("languageCode")).create();
            }
        } else if (HMSUtils.getInstance().hasValidKey(modelConfiguration, "tts", ReadableType.Map)) {
            ReadableMap tts = modelConfiguration.getMap("tts");
            if (HMSUtils.getInstance().hasValidKey(tts, "speakerName", ReadableType.String)) {
                Log.i(TAG, "tts speaker name is set and object created");
                return new MLTtsLocalModel.Factory(tts.getString("speakerName")).create();
            }
        } else if (HMSUtils.getInstance().hasValidKey(modelConfiguration, "customRemoteModel", ReadableType.Map)) {
            ReadableMap customRemoteModel = modelConfiguration.getMap("customRemoteModel");
            if (HMSUtils.getInstance().hasValidKey(customRemoteModel, "modelName", ReadableType.String)) {
                Log.i(TAG, "customRemoteModel model name is set and object created");
                return createCustomRemoteModel(customRemoteModel.getString("modelName"));
            }
        }
        Log.i(TAG, "No matching option with given configuration");
        return null;
    }

    /**
     * Creates MLLocalTextSetting using creator
     *
     * @param readableMap configuration keys and values
     * @return MLLocalTextSetting object
     */
    private MLLocalTextSetting createLocalTextSetting(ReadableMap readableMap) {
        if (readableMap == null) {
            Log.i(TAG, "LocalTextSetting object is created using default options.");
            return new MLLocalTextSetting.Factory().create();
        }

        Log.i(TAG, "LocalTextSetting object is created.");
        return new MLLocalTextSetting.Factory().setLanguage(setLanguage(readableMap))
            .setOCRMode(setOCRMode(readableMap))
            .create();
    }

    /**
     * Creates MLRemoteTextSetting using creator
     *
     * @param readableMap configuration keys and values
     * @return MLRemoteTextSetting object
     */
    private MLRemoteTextSetting createRemoteTextSetting(ReadableMap readableMap) {
        int textDensityScene = MLRemoteTextSetting.OCR_LOOSE_SCENE;
        String borderType = MLRemoteTextSetting.NGON;
        List<String> languageList = new ArrayList<>();

        if (readableMap == null) {
            Log.i(TAG, "RemoteTextSetting object is created using default options.");
            return new MLRemoteTextSetting.Factory().create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "borderType", ReadableType.String)) {
            borderType = readableMap.getString("borderType");
            Log.i(TAG, "RemoteTextSetting borderType option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "textDensityScene", ReadableType.Number)) {
            textDensityScene = readableMap.getInt("textDensityScene");
            Log.i(TAG, "RemoteTextSetting textDensityScene option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "languageList", ReadableType.Array)) {
            languageList = HMSUtils.getInstance().readableArrayIntoStringList(readableMap.getArray("languageList"));
            Log.i(TAG, "RemoteTextSetting languageList option set.");
        }

        return new MLRemoteTextSetting.Factory().setTextDensityScene(textDensityScene)
            .setBorderType(borderType)
            .setLanguageList(languageList)
            .create();
    }

    /**
     * Creates MLTextAnalyzer using creator
     *
     * @param readableMap configuration keys and values
     * @param context ReactApplicationContext
     * @return MLTextAnalyzer object
     */
    public MLTextAnalyzer createTextAnalyzer(ReadableMap readableMap, ReactApplicationContext context) {
        if (readableMap == null) {
            Log.i(TAG, "MLTextAnalyzer object is created using default options.");
            return new MLTextAnalyzer.Factory(context).create();
        }

        Log.i(TAG, "MLTextAnalyzer object is created.");
        return new MLTextAnalyzer.Factory(context).setLanguage(setLanguage(readableMap))
            .setLocalOCRMode(setOCRMode(readableMap))
            .create();
    }

    /**
     * Creates MLTextAnalyzer using factory
     *
     * @param readableMap configuration keys and values
     * @param isRemote remote or local text analyzer
     * @return MLTextAnalyzer object
     */
    public MLTextAnalyzer createTextAnalyzer(ReadableMap readableMap, boolean isRemote) {
        if (isRemote) {
            Log.i(TAG, "MLRemoteTextAnalyzer object is created.");
            return MLAnalyzerFactory.getInstance().getRemoteTextAnalyzer(createRemoteTextSetting(readableMap));
        } else {
            Log.i(TAG, "MLLocalTextAnalyzer object is created.");
            return MLAnalyzerFactory.getInstance().getLocalTextAnalyzer(createLocalTextSetting(readableMap));
        }
    }

    /**
     * Creates document analyzer
     *
     * @param readableMap document analyzer setting
     * @return MLDocumentAnalyzer
     */
    public MLDocumentAnalyzer createDocumentAnalyzer(ReadableMap readableMap) {
        return MLAnalyzerFactory.getInstance().getRemoteDocumentAnalyzer(createDocumentSetting(readableMap));
    }

    /**
     * Creates MLDocumentSetting using creator
     *
     * @param readableMap configuration keys and values
     * @return MLDocumentSetting object
     */
    private MLDocumentSetting createDocumentSetting(ReadableMap readableMap) {
        String borderType = MLRemoteTextSetting.NGON;
        List<String> languageList = new ArrayList<>();
        boolean isFingerPrintEnabled = false;

        if (readableMap == null) {
            Log.i(TAG, "MLDocumentSetting object is created using default options.");
            return new MLDocumentSetting.Factory().create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "borderType", ReadableType.String)) {
            borderType = readableMap.getString("borderType");
            Log.i(TAG, "MLDocumentSetting borderType option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "languageList", ReadableType.Array)) {
            languageList = HMSUtils.getInstance().readableArrayIntoStringList(readableMap.getArray("languageList"));
            Log.i(TAG, "MLDocumentSetting languageList option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "isFingerPrintEnabled", ReadableType.Boolean)) {
            isFingerPrintEnabled = readableMap.getBoolean("isFingerPrintEnabled");
            Log.i(TAG, "MLDocumentSetting isFingerPrintEnabled option set.");
        }

        if (!isFingerPrintEnabled) {
            return new MLDocumentSetting.Factory().setBorderType(borderType).setLanguageList(languageList).create();
        }

        return new MLDocumentSetting.Factory().setBorderType(borderType)
            .setLanguageList(languageList)
            .enableFingerprintVerification()
            .create();
    }

    /**
     * Creates MLBcrCapture object
     *
     * @param readableMap configuration
     * @return MLBcrCapture object
     */
    public MLBcrCapture createBcrCapture(ReadableMap readableMap) {
        return MLBcrCaptureFactory.getInstance().getBcrCapture(createBcrCaptureConfig(readableMap));
    }

    /**
     * creates MLBcrCaptureConfig object
     *
     * @param readableMap configuration
     * @return MLBcrCaptureConfig object
     */
    private MLBcrCaptureConfig createBcrCaptureConfig(ReadableMap readableMap) {
        int orientation = MLBcrCaptureConfig.ORIENTATION_AUTO;
        int resultType = MLBcrCaptureConfig.RESULT_SIMPLE;
        int recMode = MLBcrCaptureConfig.STRICT_MODE;

        if (readableMap == null) {
            Log.i(TAG, "MLBcrCaptureConfig object is created using default options.");
            return new MLBcrCaptureConfig.Factory().create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "orientation", ReadableType.Number)) {
            orientation = readableMap.getInt("orientation");
            Log.i(TAG, "MLBcrCaptureConfig orientation option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "resultType", ReadableType.Number)) {
            resultType = readableMap.getInt("resultType");
            Log.i(TAG, "MLBcrCaptureConfig resultType option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "recMode", ReadableType.Number)) {
            recMode = readableMap.getInt("recMode");
            Log.i(TAG, "MLBcrCaptureConfig recMode option set.");
        }

        return new MLBcrCaptureConfig.Factory().setOrientation(orientation)
            .setRecMode(recMode)
            .setResultType(resultType)
            .create();
    }

    /**
     * Creates MLGcrCapture object
     *
     * @param language language
     * @param uiConfiguration ui config
     * @return MLGcrCapture object
     */
    public MLGcrCapture createGcrCapture(String language, ReadableMap uiConfiguration) {
        return MLGcrCaptureFactory.getInstance()
            .getGcrCapture(createGcrCaptureConfig(language), createGcrCaptureUiConfig(uiConfiguration));
    }

    /**
     * creates MLGcrCaptureConfig object
     *
     * @param language language configuration
     * @return MLGcrCaptureConfig object
     */
    private MLGcrCaptureConfig createGcrCaptureConfig(String language) {
        if (TextUtils.isEmpty(language)) {
            Log.i(TAG, "MLGcrCaptureConfig object is created using default options.");
            return new MLGcrCaptureConfig.Factory().create();
        }

        return new MLGcrCaptureConfig.Factory().setLanguage(language).create();
    }

    /**
     * creates MLGcrCaptureUIConfig object
     *
     * @param readableMap configuration
     * @return MLGcrCaptureUIConfig object
     */
    private MLGcrCaptureUIConfig createGcrCaptureUiConfig(ReadableMap readableMap) {
        int orientation = MLGcrCaptureUIConfig.ORIENTATION_AUTO;
        int tipTextColor = Color.GREEN;
        int scanBoxCornerColor = Color.RED;
        String tipText = "Recognizing, align edges";

        if (readableMap == null) {
            Log.i(TAG, "MLGcrCaptureUIConfig object is created using default options.");
            return new MLGcrCaptureUIConfig.Factory().create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "orientation", ReadableType.Number)) {
            orientation = readableMap.getInt("orientation");
            Log.i(TAG, "MLGcrCaptureUIConfig orientation option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "tipTextColor", ReadableType.Number)) {
            tipTextColor = readableMap.getInt("tipTextColor");
            Log.i(TAG, "MLGcrCaptureUIConfig tipTextColor option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "scanBoxCornerColor", ReadableType.Number)) {
            scanBoxCornerColor = readableMap.getInt("scanBoxCornerColor");
            Log.i(TAG, "MLGcrCaptureUIConfig scanBoxCornerColor option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "tipText", ReadableType.String)) {
            tipText = readableMap.getString("tipText");
            Log.i(TAG, "MLGcrCaptureUIConfig tipText option set.");
        }

        return new MLGcrCaptureUIConfig.Factory().setOrientation(orientation)
            .setScanBoxCornerColor(scanBoxCornerColor)
            .setTipText(tipText)
            .setTipTextColor(tipTextColor)
            .create();
    }

    /**
     * creates MLFormRecognitionAnalyzer object
     *
     * @return MLFormRecognitionAnalyzer object
     */
    public MLFormRecognitionAnalyzer createFormRecognizerAnalyzer() {
        return MLFormRecognitionAnalyzerFactory.getInstance()
            .getFormRecognitionAnalyzer(createFormRecognitionAnalyzerSetting());
    }

    /**
     * Creates MLFormRecognitionAnalyzerSetting object
     *
     * @return MLFormRecognitionAnalyzerSetting object
     */
    private MLFormRecognitionAnalyzerSetting createFormRecognitionAnalyzerSetting() {
        return new MLFormRecognitionAnalyzerSetting.Factory().create();
    }

    /**
     * Creates MLRemoteClassificationAnalyzerSetting object
     *
     * @param readableMap configuration
     * @return MLRemoteClassificationAnalyzerSetting object
     */
    private MLRemoteClassificationAnalyzerSetting createRemoteClassificationAnalyzerSetting(ReadableMap readableMap) {
        int largestNumOfReturns = 10;
        double minAcceptablePossibility = 0.5F;

        if (readableMap == null) {
            Log.i(TAG, "MLRemoteClassificationAnalyzerSetting object is being created...");
            return new MLRemoteClassificationAnalyzerSetting.Factory().create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "maxNumberOfReturns", ReadableType.Number)) {
            largestNumOfReturns = readableMap.getInt("maxNumberOfReturns");
            Log.i(TAG, "MLRemoteClassificationAnalyzerSetting maxNumberOfReturns option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "minAcceptablePossibility", ReadableType.Number)) {
            minAcceptablePossibility = readableMap.getDouble("minAcceptablePossibility");
            Log.i(TAG, "MLRemoteClassificationAnalyzerSetting minAcceptablePossibility option set.");
        }

        return new MLRemoteClassificationAnalyzerSetting.Factory().setLargestNumOfReturns(largestNumOfReturns)
            .setMinAcceptablePossibility((float) minAcceptablePossibility)
            .create();
    }

    /**
     * Creates and returns classification analyzer
     *
     * @param isRemote on-cloud or on-device
     * @param analyzerSetting setting to create analyzer
     * @return MLImageClassificationAnalyzer object
     */
    public MLImageClassificationAnalyzer createClassificationAnalyzer(boolean isRemote, ReadableMap analyzerSetting) {
        if (isRemote) {
            return MLAnalyzerFactory.getInstance()
                .getRemoteImageClassificationAnalyzer(createRemoteClassificationAnalyzerSetting(analyzerSetting));
        } else {
            return MLAnalyzerFactory.getInstance()
                .getLocalImageClassificationAnalyzer(createLocalClassificationAnalyzerSetting(analyzerSetting));
        }
    }

    /**
     * Creates MLLocalClassificationAnalyzerSetting object
     *
     * @param readableMap configuration
     * @return MLLocalClassificationAnalyzerSetting object
     */
    private MLLocalClassificationAnalyzerSetting createLocalClassificationAnalyzerSetting(ReadableMap readableMap) {
        double minAcceptablePossibility = 0.5F;

        if (readableMap == null) {
            Log.i(TAG, "MLLocalClassificationAnalyzerSetting object is being created...");
            return new MLLocalClassificationAnalyzerSetting.Factory().create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "minAcceptablePossibility", ReadableType.Number)) {
            minAcceptablePossibility = readableMap.getDouble("minAcceptablePossibility");
            Log.i(TAG, "MLLocalClassificationAnalyzerSetting minAcceptablePossibility option set.");
        }
        return new MLLocalClassificationAnalyzerSetting.Factory().setMinAcceptablePossibility(
            (float) minAcceptablePossibility).create();
    }

    /**
     * Creates MLObjectAnalyzerSetting object
     *
     * @param readableMap configuration
     * @return MLObjectAnalyzerSetting object
     */
    private MLObjectAnalyzerSetting createObjectAnalyzerSetting(ReadableMap readableMap) {
        MLObjectAnalyzerSetting.Factory objectAnalyzer = new MLObjectAnalyzerSetting.Factory();
        int analyzerType = MLObjectAnalyzerSetting.TYPE_PICTURE;

        if (readableMap == null) {
            Log.i(TAG, "MLObjectAnalyzerSetting object is created using default options.");
            return objectAnalyzer.create();
        }
        if (HMSUtils.getInstance().boolKeyCheck(readableMap, "allowClassification")) {
            objectAnalyzer.allowClassification();
            Log.i(TAG, "MLObjectAnalyzerSetting allowClassification option set.");
        }
        if (HMSUtils.getInstance().boolKeyCheck(readableMap, "allowMultiResults")) {
            objectAnalyzer.allowMultiResults();
            Log.i(TAG, "MLObjectAnalyzerSetting allowMultiResults option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "analyzerType", ReadableType.Number)) {
            analyzerType = readableMap.getInt("analyzerType");
            Log.i(TAG, "MLObjectAnalyzerSetting analyzerType option set.");
        }
        return objectAnalyzer.setAnalyzerType(analyzerType).create();
    }

    /**
     * Creates and returns object analyzer
     *
     * @param objectAnalyzerSetting setting to create analyzer
     * @return MLObjectAnalyzer object
     */
    public MLObjectAnalyzer createObjectAnalyzer(ReadableMap objectAnalyzerSetting) {
        return MLAnalyzerFactory.getInstance()
            .getLocalObjectAnalyzer(createObjectAnalyzerSetting(objectAnalyzerSetting));
    }

    /**
     * Creates and returns landmark analyzer
     *
     * @param readableMap landmark analyzer setting
     * @return MLRemoteLandmarkAnalyzer object
     */
    public MLRemoteLandmarkAnalyzer createLandmarkAnalyzer(ReadableMap readableMap) {
        return MLAnalyzerFactory.getInstance().getRemoteLandmarkAnalyzer(createLandMarkAnalyzerSetting(readableMap));
    }

    /**
     * Creates MLRemoteLandmarkAnalyzerSetting object
     *
     * @param readableMap configuration
     * @return MLRemoteLandmarkAnalyzerSetting object
     */
    private MLRemoteLandmarkAnalyzerSetting createLandMarkAnalyzerSetting(ReadableMap readableMap) {
        int largestNumOfReturns = 10;
        int patternType = MLRemoteLandmarkAnalyzerSetting.STEADY_PATTERN;

        if (readableMap == null) {
            Log.i(TAG, "MLRemoteLandmarkAnalyzerSetting object is created using default options.");
            return MLRemoteLandmarkAnalyzerSetting.DEFAULT_SETTINGS;
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "largestNumOfReturns", ReadableType.Number)) {
            largestNumOfReturns = readableMap.getInt("largestNumOfReturns");
            Log.i(TAG, "MLRemoteLandmarkAnalyzerSetting largestNumOfReturns option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "patternType", ReadableType.Number)) {
            patternType = readableMap.getInt("patternType");
            Log.i(TAG, "MLRemoteLandmarkAnalyzerSetting patternType option set.");
        }

        return new MLRemoteLandmarkAnalyzerSetting.Factory().setLargestNumOfReturns(largestNumOfReturns)
            .setPatternType(patternType)
            .create();
    }

    /**
     * Creates and returns MLImageSegmentationAnalyzer object
     *
     * @param analyzerConfiguration analyzer configuration
     * @return MLImageSegmentationAnalyzer object
     */
    public MLImageSegmentationAnalyzer createImageSegmentationAnalyzer(ReadableMap analyzerConfiguration) {
        return MLAnalyzerFactory.getInstance()
            .getImageSegmentationAnalyzer(createImageSegmentationSetting(analyzerConfiguration));
    }

    /**
     * Creates and returns MLRemoteProductVisionSearchAnalyzer object
     *
     * @param readableMap analyzer configuration
     * @return MLRemoteProductVisionSearchAnalyzer object
     */
    public MLRemoteProductVisionSearchAnalyzer createProductVisionSearchAnalyzer(ReadableMap readableMap) {
        return MLAnalyzerFactory.getInstance()
            .getRemoteProductVisionSearchAnalyzer(createSearchAnalyzerSetting(readableMap));
    }

    /**
     * Creates MLRemoteProductVisionSearchAnalyzerSetting object
     *
     * @param readableMap configuration
     * @return MLRemoteProductVisionSearchAnalyzerSetting object
     */
    private MLRemoteProductVisionSearchAnalyzerSetting createSearchAnalyzerSetting(ReadableMap readableMap) {
        MLRemoteProductVisionSearchAnalyzerSetting.Factory creator
            = new MLRemoteProductVisionSearchAnalyzerSetting.Factory();

        if (readableMap == null) {
            Log.i(TAG, "MLRemoteProductVisionSearchAnalyzerSetting object is created using default options.");
            return creator.create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "maxResults", ReadableType.Number)) {
            creator.setLargestNumOfReturns(readableMap.getInt("maxResults"));
            Log.i(TAG, "MLRemoteProductVisionSearchAnalyzerSetting maxResults option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "productSetId", ReadableType.String)) {
            creator.setProductSetId(readableMap.getString("productSetId"));
            Log.i(TAG, "MLRemoteProductVisionSearchAnalyzerSetting productSetId option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "region", ReadableType.Number)) {
            creator.setRegion(readableMap.getInt("region"));
            Log.i(TAG, "MLRemoteProductVisionSearchAnalyzerSetting region option set.");
        }
        return creator.create();
    }

    /**
     * Creates MLProductVisionSearchCaptureConfig object
     *
     * @param readableMap configuration
     * @param context context object
     * @return MLProductVisionSearchCaptureConfig object
     */
    private MLProductVisionSearchCaptureConfig createProductSearchPluginConfig(ReadableMap readableMap,
        ReactApplicationContext context) {
        MLProductVisionSearchCaptureConfig.Factory creator = new MLProductVisionSearchCaptureConfig.Factory();

        if (readableMap == null) {
            Log.i(TAG, "MLProductVisionSearchCaptureConfig object is created using default options.");
            return creator.create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "maxResults", ReadableType.Number)) {
            creator.setLargestNumOfReturns(readableMap.getInt("maxResults"));
            Log.i(TAG, "MLProductVisionSearchCaptureConfig maxResults option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "productSetId", ReadableType.String)) {
            creator.setProductSetId(readableMap.getString("productSetId"));
            Log.i(TAG, "MLProductVisionSearchCaptureConfig productSetId option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "region", ReadableType.Number)) {
            creator.setRegion(readableMap.getInt("region"));
            Log.i(TAG, "MLProductVisionSearchCaptureConfig region option set.");
        }

        return creator.setProductFragment(new HMSProductFragment(context)).create();
    }

    /**
     * Creates product vision plugin capture
     *
     * @param readableMap configuration
     * @param context context object
     * @return MLProductVisionSearchCapture
     */
    public MLProductVisionSearchCapture createProductVisionSearchCapture(ReadableMap readableMap,
        ReactApplicationContext context) {
        return MLProductVisionSearchCaptureFactory.getInstance()
            .create(createProductSearchPluginConfig(readableMap, context));
    }

    /**
     * Creates MLImageSuperResolutionAnalyzerSetting object
     *
     * @param scale scale value
     * @return MLImageSuperResolutionAnalyzerSetting object
     */
    private MLImageSuperResolutionAnalyzerSetting createImageSuperResolutionSetting(double scale) {
        return new MLImageSuperResolutionAnalyzerSetting.Factory().setScale((float) scale).create();
    }

    /**
     * Creates MLImageSuperResolutionAnalyzer object
     *
     * @param scale scale value
     * @return MLImageSuperResolutionAnalyzer object
     */
    public MLImageSuperResolutionAnalyzer createImageSuperResolutionAnalyzer(double scale) {
        return MLImageSuperResolutionAnalyzerFactory.getInstance()
            .getImageSuperResolutionAnalyzer(createImageSuperResolutionSetting(scale));
    }

    /**
     * Creates MLDocumentSkewCorrectionAnalyzer object
     *
     * @return MLDocumentSkewCorrectionAnalyzer
     */
    public MLDocumentSkewCorrectionAnalyzer createDocumentSkewCorrectionAnalyzer() {
        return MLDocumentSkewCorrectionAnalyzerFactory.getInstance()
            .getDocumentSkewCorrectionAnalyzer(createDscAnalyzerSetting());
    }

    /**
     * Create MLSceneDetectionAnalyzer
     *
     * @param confidence confidence setting
     * @return MLSceneDetectionAnalyzer object
     */
    public MLSceneDetectionAnalyzer getSceneDetectionAnalyzer(double confidence) {
        return MLSceneDetectionAnalyzerFactory.getInstance()
            .getSceneDetectionAnalyzer(createScdAnalyzerSetting(confidence));
    }

    /**
     * Creates MLSceneDetectionAnalyzerSetting object
     *
     * @param confidence confidence value
     * @return MLSceneDetectionAnalyzerSetting object
     */
    private MLSceneDetectionAnalyzerSetting createScdAnalyzerSetting(double confidence) {
        return new MLSceneDetectionAnalyzerSetting.Factory().setConfidence((float) confidence).create();
    }

    /**
     * Creates MLFaceAnalyzer
     *
     * @param faceAnalyzerConfiguration analyzer configuration
     * @return MLFaceAnalyzer  object
     */
    public MLFaceAnalyzer create2DFaceAnalyzer(ReadableMap faceAnalyzerConfiguration) {
        return MLAnalyzerFactory.getInstance().getFaceAnalyzer(createFaceAnalyzerSetting(faceAnalyzerConfiguration));
    }

    /**
     * Creates ML3DFaceAnalyzer
     *
     * @param faceAnalyzerConfiguration analyzer configuration
     * @return ML3DFaceAnalyzer object
     */
    public ML3DFaceAnalyzer create3DFaceAnalyzer(ReadableMap faceAnalyzerConfiguration) {
        return MLAnalyzerFactory.getInstance()
            .get3DFaceAnalyzer(create3DFaceAnalyzerSetting(faceAnalyzerConfiguration));
    }

    /**
     * Creates ML3DFaceAnalyzerSetting
     *
     * @param readableMap configuration
     * @return ML3DFaceAnalyzerSetting object
     */
    private ML3DFaceAnalyzerSetting create3DFaceAnalyzerSetting(ReadableMap readableMap) {
        int performanceType = ML3DFaceAnalyzerSetting.TYPE_SPEED;
        boolean isTracingAllowed = false;

        if (readableMap == null) {
            Log.i(TAG, "ML3DFaceAnalyzerSetting object is created using default options.");
            return new ML3DFaceAnalyzerSetting.Factory().create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "performanceType", ReadableType.Number)) {
            performanceType = readableMap.getInt("performanceType");
            Log.i(TAG, "ML3DFaceAnalyzerSetting performanceType option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "isTracingAllowed", ReadableType.Boolean)) {
            isTracingAllowed = readableMap.getBoolean("isTracingAllowed");
            Log.i(TAG, "ML3DFaceAnalyzerSetting isTracingAllowed option set");
        }

        return new ML3DFaceAnalyzerSetting.Factory().setPerformanceType(performanceType)
            .setTracingAllowed(isTracingAllowed)
            .create();
    }

    /**
     * Creates MLFaceAnalyzerSetting object
     *
     * @param readableMap configuration
     * @return MLFaceAnalyzerSetting object
     */
    private MLFaceAnalyzerSetting createFaceAnalyzerSetting(ReadableMap readableMap) {
        int featureType = MLFaceAnalyzerSetting.TYPE_FEATURES;
        int setShapeType = MLFaceAnalyzerSetting.TYPE_SHAPES;
        int keyPointType = MLFaceAnalyzerSetting.TYPE_KEYPOINTS;
        int performanceType = MLFaceAnalyzerSetting.TYPE_SPEED;
        int tracingMode = MLFaceAnalyzerSetting.MODE_TRACING_ROBUST;
        double minFaceProportion = 0.1F;
        boolean isPoseDisabled = false;
        boolean isTracingAllowed = false;
        boolean isMaxSizeFaceOnly = false;

        if (readableMap == null) {
            Log.i(TAG, "MLFaceAnalyzerSetting object is created using default options.");
            return new MLFaceAnalyzerSetting.Factory().create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "featureType", ReadableType.Number)) {
            featureType = readableMap.getInt("featureType");
            Log.i(TAG, "MLFaceAnalyzerSetting featureType option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "keyPointType", ReadableType.Number)) {
            keyPointType = readableMap.getInt("keyPointType");
            Log.i(TAG, "MLFaceAnalyzerSetting keyPointType option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "shapeType", ReadableType.Number)) {
            setShapeType = readableMap.getInt("shapeType");
            Log.i(TAG, "MLFaceAnalyzerSetting shapeType option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "performanceType", ReadableType.Number)) {
            performanceType = readableMap.getInt("performanceType");
            Log.i(TAG, "MLFaceAnalyzerSetting performanceType option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "tracingMode", ReadableType.Number)) {
            tracingMode = readableMap.getInt("tracingMode");
            Log.i(TAG, "MLFaceAnalyzerSetting tracingMode option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "minFaceProportion", ReadableType.Number)) {
            minFaceProportion = readableMap.getDouble("minFaceProportion");
            Log.i(TAG, "MLFaceAnalyzerSetting minFaceProportion option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "isMaxSizeFaceOnly", ReadableType.Boolean)) {
            isMaxSizeFaceOnly = readableMap.getBoolean("isMaxSizeFaceOnly");
            Log.i(TAG, "MLFaceAnalyzerSetting isMaxSizeFaceOnly option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "isTracingAllowed", ReadableType.Boolean)) {
            isTracingAllowed = readableMap.getBoolean("isTracingAllowed");
            Log.i(TAG, "MLFaceAnalyzerSetting isTracingAllowed option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "isPoseDisabled", ReadableType.Boolean)) {
            isPoseDisabled = readableMap.getBoolean("isPoseDisabled");
            Log.i(TAG, "MLFaceAnalyzerSetting isPoseDisabled option set");
        }

        return new MLFaceAnalyzerSetting.Factory().setFeatureType(featureType)
            .setKeyPointType(keyPointType)
            .setMaxSizeFaceOnly(isMaxSizeFaceOnly)
            .setMinFaceProportion((float) minFaceProportion)
            .setPerformanceType(performanceType)
            .setPoseDisabled(isPoseDisabled)
            .setShapeType(setShapeType)
            .setTracingAllowed(isTracingAllowed, tracingMode)
            .create();
    }

    /**
     * Creates and returns MLSkeletonAnalyzer object
     *
     * @param analyzeType analyze type
     * @return MLSkeletonAnalyzer object
     */
    public MLSkeletonAnalyzer createSkeletonAnalyzer(int analyzeType) {
        return MLSkeletonAnalyzerFactory.getInstance().getSkeletonAnalyzer(createSkeletonAnalyzerSetting(analyzeType));
    }

    /**
     * Creates MLSkeletonAnalyzerSetting object
     *
     * @param analyzerType analyze type
     * @return MLSkeletonAnalyzerSetting object
     */
    private MLSkeletonAnalyzerSetting createSkeletonAnalyzerSetting(int analyzerType) {
        return new MLSkeletonAnalyzerSetting.Factory().setAnalyzerType(analyzerType).create();
    }

    /**
     * Creates MLLivenessCaptureConfig object
     *
     * @param options options
     * @return MLLivenessCaptureConfig object
     */
    public MLLivenessCaptureConfig createLivenessCaptureConfig(ReadableMap options) {
        MLLivenessCaptureConfig.Builder builder = new MLLivenessCaptureConfig.Builder();

        if (options == null) {
            Log.i(TAG, "MLLivenessCaptureConfig options is null");
            return builder.build();
        }
        if (HMSUtils.getInstance().hasValidKey(options, "option", ReadableType.Number)) {
            builder.setOptions(options.getInt("option"));
        }

        return builder.build();
    }

    /**
     * Create MLHandKeypointAnalyzer
     *
     * @param analyzerSetting configuration
     * @return MLHandKeypointAnalyzer
     */
    public MLHandKeypointAnalyzer createHandKeyPointAnalyzer(ReadableMap analyzerSetting) {
        return MLHandKeypointAnalyzerFactory.getInstance()
            .getHandKeypointAnalyzer(createHandKeyPointAnalyzerSetting(analyzerSetting));
    }

    /**
     * Create MLGestureAnalyzer
     *
     * @return MLGestureAnalyzer
     */
    public MLGestureAnalyzer createGestureAnalyzer() {
        MLGestureAnalyzerSetting analyzerSetting = new MLGestureAnalyzerSetting.Factory().create();
        return MLGestureAnalyzerFactory.getInstance().getGestureAnalyzer(analyzerSetting);
    }

    /**
     * Create MLIcrAnalyzer
     *
     * @param countryCode Country code 
     * @param isFront Side of card 
     * @return MLIcrAnalyzer
     */
    public MLIcrAnalyzer createICRAnalyzer(String countryCode, boolean isFront) {
        MLIcrAnalyzerSetting setting = new MLIcrAnalyzerSetting.Factory()
                .setSideType(isFront ? MLIcrAnalyzerSetting.FRONT : MLIcrAnalyzerSetting.BACK)
                .setCountryCode(countryCode)
                .create();
        return MLCardAnalyzerFactory.getInstance().getIcrAnalyzer(setting);
    }

    /**
     * Creates MLHandKeypointAnalyzerSetting object
     *
     * @param readableMap configuration
     * @return MLHandKeypointAnalyzerSetting object
     */
    private MLHandKeypointAnalyzerSetting createHandKeyPointAnalyzerSetting(ReadableMap readableMap) {
        int maxHandResults = 10;
        int sceneType = MLHandKeypointAnalyzerSetting.TYPE_ALL;

        if (readableMap == null) {
            Log.i(TAG, "MLHandKeyPointAnalyzerSetting object is created using default options.");
            return new MLHandKeypointAnalyzerSetting.Factory().setMaxHandResults(maxHandResults)
                .setSceneType(sceneType)
                .create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "sceneType", ReadableType.Number)) {
            sceneType = readableMap.getInt("sceneType");
            Log.i(TAG, "MLHandKeyPointAnalyzerSetting sceneType option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "maxHandResults", ReadableType.Number)) {
            maxHandResults = readableMap.getInt("maxHandResults") <= 0 ? 10 : readableMap.getInt("maxHandResults");
            Log.i(TAG, "MLHandKeyPointAnalyzerSetting maxHandResults option set.");
        }

        return new MLHandKeypointAnalyzerSetting.Factory().setMaxHandResults(maxHandResults)
            .setSceneType(sceneType)
            .create();
    }

    /**
     * Creates MLFrame object to use analyze
     *
     * @param frameConfiguration keys and values to create MLFrame from existing methods
     * @param context ReactApplicationContext
     * @return MLFrame object or null
     */
    public MLFrame createFrame(ReadableMap frameConfiguration, ReactApplicationContext context) {
        if (frameConfiguration == null) {
            Log.i(TAG, "MLFrame frameConfiguration is null");
            return null;
        } else if (HMSUtils.getInstance().hasValidKey(frameConfiguration, "bitmap", ReadableType.String)) {
            byte[] refactored = Base64.decode(frameConfiguration.getString("bitmap"), Base64.DEFAULT);
            return MLFrame.fromBitmap(BitmapFactory.decodeByteArray(refactored, 0, refactored.length));
        } else if (HMSUtils.getInstance().hasValidKey(frameConfiguration, "bytes", ReadableType.Map)) {
            ReadableMap bytes = frameConfiguration.getMap("bytes");
            if (HMSUtils.getInstance().hasValidKey(bytes, "frameProperty", ReadableType.Map) && HMSUtils.getInstance()
                .hasValidKey(bytes, "values", ReadableType.Array)) {
                MLFrame.Property property = createFrameProperty(bytes.getMap("frameProperty"));
                ReadableArray values = bytes.getArray("values");
                return MLFrame.fromByteArray(HMSUtils.getInstance().convertRaToByteArray(values), property);
            } else {
                Log.i(TAG, "MLFrame bytes object does not contain required keys");
                return null;
            }
        } else if (HMSUtils.getInstance().hasValidKey(frameConfiguration, "byteBuffer", ReadableType.Map)) {
            ReadableMap byteBuffer = frameConfiguration.getMap("byteBuffer");
            if (HMSUtils.getInstance().hasValidKey(byteBuffer, "buffer", ReadableType.String) && HMSUtils.getInstance()
                .hasValidKey(byteBuffer, "frameProperty", ReadableType.Map)) {
                MLFrame.Property frameProperty = createFrameProperty(byteBuffer.getMap("frameProperty"));
                String buffer = byteBuffer.getString("buffer");
                return MLFrame.fromByteBuffer(
                    HMSUtils.getInstance().convertByteArrToByteBuffer(Base64.decode(buffer, Base64.DEFAULT)),
                    frameProperty);
            } else {
                Log.i(TAG, "MLFrame byteBuffer object does not contain required keys");
                return null;
            }
        } else if (HMSUtils.getInstance().hasValidKey(frameConfiguration, "filePath", ReadableType.String)) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),
                    Uri.parse(frameConfiguration.getString("filePath")));
                return new MLFrame.Creator().setBitmap(bitmap).create();
            } catch (Exception e) {
                Log.i(TAG, "MLFrame exception happened fromFilePath " + e.getMessage());
                return null;
            }

        } else if (HMSUtils.getInstance().hasValidKey(frameConfiguration, "creator", ReadableType.Map)) {
            ReadableMap creator = frameConfiguration.getMap("creator");
            return createFrameUsingCreator(creator);
        } else {
            Log.i(TAG, "MLFrame frameConfiguration does not contain keys for creating a frame");
            return null;
        }
    }

    /**
     * Creates MLFrame.Property object
     *
     * @param readableMap configuration keys and values
     * @return MLFrame.Property object
     */
    private MLFrame.Property createFrameProperty(ReadableMap readableMap) {
        MLFrame.Property.Creator creator = new MLFrame.Property.Creator();

        if (readableMap == null) {
            Log.i(TAG, "MLFrame.Property object is created using default options.");
            return creator.create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "width", ReadableType.Number)) {
            creator.setWidth(readableMap.getInt("width"));
            Log.i(TAG, "MLFrame.Property width option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "height", ReadableType.Number)) {
            creator.setHeight(readableMap.getInt("height"));
            Log.i(TAG, "MLFrame.Property height option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "quadrant", ReadableType.Number)) {
            creator.setQuadrant(readableMap.getInt("quadrant"));
            Log.i(TAG, "MLFrame.Property quadrant option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "formatType", ReadableType.Number)) {
            creator.setFormatType(readableMap.getInt("formatType"));
            Log.i(TAG, "MLFrame.Property formatType option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "itemIdentity", ReadableType.Number)) {
            creator.setItemIdentity(readableMap.getInt("itemIdentity"));
            Log.i(TAG, "MLFrame.Property itemIdentity option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "timeStamp", ReadableType.Number)) {
            creator.setTimestamp(readableMap.getInt("timeStamp"));
            Log.i(TAG, "MLFrame.Property timeStamp option set.");
        }

        return creator.create();
    }

    /**
     * Creates MLFrame.Property.Ext object
     *
     * @param readableMap configuration keys and values
     * @return MLFrame.Property.Ext object
     */
    private MLFrame.Property.Ext createFramePropertyExt(ReadableMap readableMap) {
        int lensId = 0;
        int maxZoom = 0;
        int zoom = 0;
        int bottom = 0;
        int left = 0;
        int right = 0;
        int top = 0;

        if (readableMap == null) {
            Log.i(TAG, "MLFrameProperty.Ext object is created using default options.");
            return new MLFrame.Property.Ext.Creator().build();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "lensId", ReadableType.Number)) {
            lensId = readableMap.getInt("lensId");
            Log.i(TAG, "MLFrameProperty.Ext lensId option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "maxZoom", ReadableType.Number)) {
            maxZoom = readableMap.getInt("maxZoom");
            Log.i(TAG, "MLFrameProperty.Ext maxZoom option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "zoom", ReadableType.Number)) {
            zoom = readableMap.getInt("zoom");
            Log.i(TAG, "MLFrameProperty.Ext zoom option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "bottom", ReadableType.Number)) {
            bottom = readableMap.getInt("bottom");
            Log.i(TAG, "MLFrameProperty.Ext bottom option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "left", ReadableType.Number)) {
            left = readableMap.getInt("left");
            Log.i(TAG, "MLFrameProperty.Ext left option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "right", ReadableType.Number)) {
            right = readableMap.getInt("right");
            Log.i(TAG, "MLFrameProperty.Ext right option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "top", ReadableType.Number)) {
            top = readableMap.getInt("top");
            Log.i(TAG, "MLFrameProperty.Ext top option set.");
        }
        return new MLFrame.Property.Ext.Creator().setLensId(lensId)
            .setMaxZoom(maxZoom)
            .setRect(new Rect(left, top, right, bottom))
            .setZoom(zoom)
            .build();
    }

    /**
     * Creates MLFrame using creator
     *
     * @param readableMap configuration keys and values
     * @return MLFrame object
     */
    private MLFrame createFrameUsingCreator(ReadableMap readableMap) {
        MLFrame.Creator creator = new MLFrame.Creator();

        if (readableMap == null) {
            Log.i(TAG, "MLFrame given ReadableMap object is null");
            return null;
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "base64Bitmap", ReadableType.String)) {
            byte[] refactored = Base64.decode(readableMap.getString("base64Bitmap"), Base64.DEFAULT);
            creator.setBitmap(BitmapFactory.decodeByteArray(refactored, 0, refactored.length));
            Log.i(TAG, "MLFrame base64Bitmap option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "itemIdentity", ReadableType.Number)) {
            creator.setItemIdentity(readableMap.getInt("itemIdentity"));
            Log.i(TAG, "MLFrame itemIdentity option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "quadrant", ReadableType.Number)) {
            creator.setQuadrant(readableMap.getInt("quadrant"));
            Log.i(TAG, "MLFrame quadrant option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "timeStamp", ReadableType.String)) {
            creator.setTimestamp(Long.parseLong(readableMap.getString("timeStamp")));
            Log.i(TAG, "MLFrame timeStamp option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "framePropertyExt", ReadableType.Map)) {
            creator.setFramePropertyExt(createFramePropertyExt(readableMap.getMap("framePropertyExt")));
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "writeByteBufferData", ReadableType.Map)) {
            ReadableMap writeByteBufferData = readableMap.getMap("writeByteBufferData");
            if (HMSUtils.getInstance().hasValidKey(writeByteBufferData, "data", ReadableType.String)
                && HMSUtils.getInstance().hasValidKey(writeByteBufferData, "height", ReadableType.Number)
                && HMSUtils.getInstance().hasValidKey(writeByteBufferData, "width", ReadableType.Number)
                && HMSUtils.getInstance().hasValidKey(writeByteBufferData, "formatType", ReadableType.Number)) {

                ByteBuffer bufferData = HMSUtils.getInstance()
                    .convertByteArrToByteBuffer(Base64.decode(writeByteBufferData.getString("data"), Base64.DEFAULT));
                int height = writeByteBufferData.getInt("height");
                int width = writeByteBufferData.getInt("width");
                int formatType = writeByteBufferData.getInt("formatType");
                creator.writeByteBufferData(bufferData, width, height, formatType);
                Log.i(TAG, "MLFrame writeByteBufferData option set.");
            } else {
                Log.i(TAG, "MLFrame writeByteBufferData option keys are not valid.");
            }

        }

        return creator.create();
    }

    /**
     * Helper method that sets language
     *
     * @param readableMap configuration
     * @return language
     */
    private String setLanguage(ReadableMap readableMap) {
        String language = "rm";

        if (HMSUtils.getInstance().hasValidKey(readableMap, "language", ReadableType.String)) {
            language = readableMap.getString("language");
            Log.i(TAG, "Language option set.");
        }

        return language;
    }

    /**
     * Helper method that sets OCRMode
     *
     * @param readableMap configuration
     * @return OCRMode
     */
    private int setOCRMode(ReadableMap readableMap) {
        int ocrDetectMode = MLLocalTextSetting.OCR_DETECT_MODE;

        if (HMSUtils.getInstance().hasValidKey(readableMap, "OCRMode", ReadableType.Number)) {
            ocrDetectMode = readableMap.getInt("OCRMode");
            Log.i(TAG, "OCRMode option set.");
        }

        return ocrDetectMode;
    }

    /**
     * Creates MLLocalTranslateSetting object
     *
     * @param readableMap configuration
     * @return MLLocalTranslateSetting object
     */
    private MLLocalTranslateSetting createLocalTranslateSetting(ReadableMap readableMap) {
        String sourceLanguageCode = "en";
        String targetLanguageCode = "zh";

        if (readableMap == null) {
            Log.i(TAG, "MLLocalTranslateSetting object is created using default options.");
            return new MLLocalTranslateSetting.Factory().setSourceLangCode(sourceLanguageCode)
                .setTargetLangCode(targetLanguageCode)
                .create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "sourceLanguageCode", ReadableType.String)) {
            sourceLanguageCode = readableMap.getString("sourceLanguageCode");
            Log.i(TAG, "MLLocalTranslateSetting sourceLanguageCode option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "targetLanguageCode", ReadableType.String)) {
            targetLanguageCode = readableMap.getString("targetLanguageCode");
            Log.i(TAG, "MLLocalTranslateSetting targetLanguageCode option set.");
        }

        return new MLLocalTranslateSetting.Factory().setSourceLangCode(sourceLanguageCode)
            .setTargetLangCode(targetLanguageCode)
            .create();
    }

    /**
     * Creates MLRemoteTranslateSetting object
     *
     * @param readableMap configuration
     * @return MLRemoteTranslateSetting object
     */
    private MLRemoteTranslateSetting createRemoteTranslateSetting(ReadableMap readableMap) {
        String sourceLanguageCode = "en";
        String targetLanguageCode = "zh";

        if (readableMap == null) {
            Log.i(TAG, "MLRemoteTranslateSetting object is created using default options.");
            return new MLRemoteTranslateSetting.Factory().setSourceLangCode(sourceLanguageCode)
                .setTargetLangCode(targetLanguageCode)
                .create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "sourceLanguageCode", ReadableType.String)) {
            sourceLanguageCode = readableMap.getString("sourceLanguageCode");
            Log.i(TAG, "MLRemoteTranslateSetting sourceLanguageCode option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "targetLanguageCode", ReadableType.String)) {
            targetLanguageCode = readableMap.getString("targetLanguageCode");
            Log.i(TAG, "MLRemoteTranslateSetting targetLanguageCode option set.");
        }

        return new MLRemoteTranslateSetting.Factory().setSourceLangCode(sourceLanguageCode)
            .setTargetLangCode(targetLanguageCode)
            .create();
    }

    /**
     * Creates MLImageSegmentationSetting object
     *
     * @param readableMap configuration
     * @return MLImageSegmentationSetting object
     */
    private MLImageSegmentationSetting createImageSegmentationSetting(ReadableMap readableMap) {
        int analyzerType = MLImageSegmentationSetting.BODY_SEG;
        int scene = MLImageSegmentationScene.ALL;
        boolean exact = true;

        if (readableMap == null) {
            Log.i(TAG, "MLImageSegmentationSetting object is being created...");
            return new MLImageSegmentationSetting.Factory().create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "analyzerType", ReadableType.Number)) {
            analyzerType = readableMap.getInt("analyzerType");
            Log.i(TAG, "MLImageSegmentationSetting analyzerType option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "scene", ReadableType.Number)) {
            scene = readableMap.getInt("scene");
            Log.i(TAG, "MLImageSegmentationSetting scene option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "exact", ReadableType.Boolean)) {
            exact = readableMap.getBoolean("exact");
            Log.i(TAG, "MLImageSegmentationSetting exact option set.");
        }

        return new MLImageSegmentationSetting.Factory().setScene(scene)
            .setAnalyzerType(analyzerType)
            .setExact(exact)
            .create();
    }

    /**
     * Creates MLDocumentSkewCorrectionAnalyzerSetting object
     *
     * @return MLDocumentSkewCorrectionAnalyzerSetting object
     */
    private MLDocumentSkewCorrectionAnalyzerSetting createDscAnalyzerSetting() {
        return new MLDocumentSkewCorrectionAnalyzerSetting.Factory().create();
    }

    /**
     * Creates MLCompositeAnalyzer
     *
     * @param readableMap analyzer configurations
     * @return MLCompositeAnalyzer
     */
    public MLCompositeAnalyzer createCompositeAnalyzer(ReadableMap readableMap) {
        MLCompositeAnalyzer.Creator creator = new MLCompositeAnalyzer.Creator();

        if (readableMap == null) {
            Log.i(TAG, "MLCompositeAnalyzer object created with local text analyzer.");
            return creator.add(createTextAnalyzer(null, false)).create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "face", ReadableType.Map)) {
            ReadableMap face = readableMap.getMap("face");
            if (HMSUtils.getInstance().boolKeyCheck(face, "isFace2D")) {
                Log.i(TAG, "MLCompositeAnalyzer added face2D analyzer");
                creator.add(create2DFaceAnalyzer(face));
            } else {
                Log.i(TAG, "MLCompositeAnalyzer added face3D analyzer");
                creator.add(create2DFaceAnalyzer(face));
            }
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "hand", ReadableType.Map)) {
            Log.i(TAG, "MLCompositeAnalyzer added hand analyzer");
            creator.add(createHandKeyPointAnalyzer(readableMap.getMap("hand")));
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "skeleton", ReadableType.Number)) {
            Log.i(TAG, "MLCompositeAnalyzer added skeleton analyzer");
            creator.add(createSkeletonAnalyzer(readableMap.getInt("skeleton")));
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "text", ReadableType.Map)) {
            Log.i(TAG, "MLCompositeAnalyzer added text analyzer");
            creator.add(createTextAnalyzer(readableMap.getMap("text"),
                HMSUtils.getInstance().boolKeyCheck(readableMap.getMap("text"), "isRemote")));
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "object", ReadableType.Map)) {
            Log.i(TAG, "MLCompositeAnalyzer added object analyzer");
            creator.add(createObjectAnalyzer(readableMap.getMap("object")));
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "classification", ReadableType.Map)) {
            Log.i(TAG, "MLCompositeAnalyzer added classification analyzer");
            creator.add(createClassificationAnalyzer(
                HMSUtils.getInstance().boolKeyCheck(readableMap.getMap("classification"), "isRemote"),
                readableMap.getMap("classification")));
        }

        return creator.create();
    }

    /**
     * Creates LensEngine
     *
     * @param context context object
     * @param analyzer analyzer
     * @param configuration configurations for LensEngine
     * @return LensEngine object
     */
    public LensEngine createLensEngine(ReactApplicationContext context, MLAnalyzer analyzer,
        ReadableMap configuration) {
        int width = 1440;
        int height = 1080;
        float fps = 30.0f;
        boolean automaticFocus = false;
        String flashMode = Camera.Parameters.FLASH_MODE_OFF;
        String focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO;
        int lensType = LensEngine.BACK_LENS;

        if (configuration == null) {
            Log.i(TAG, "LensEngine created with default options");
            return new LensEngine.Creator(context, analyzer).setLensType(lensType)
                .setFocusMode(focusMode)
                .setFlashMode(flashMode)
                .enableAutomaticFocus(false)
                .applyFps(fps)
                .applyDisplayDimension(width, height)
                .create();
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "width", ReadableType.Number)) {
            Log.i(TAG, "LensEngine width set");
            width = configuration.getInt("width");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "height", ReadableType.Number)) {
            Log.i(TAG, "LensEngine height set");
            height = configuration.getInt("height");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "lensType", ReadableType.Number)) {
            Log.i(TAG, "LensEngine lensType set");
            lensType = configuration.getInt("lensType");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "fps", ReadableType.Number)) {
            Log.i(TAG, "LensEngine fps set");
            fps = (float) configuration.getDouble("fps");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "automaticFocus", ReadableType.Boolean)) {
            Log.i(TAG, "LensEngine automaticFocus set");
            automaticFocus = configuration.getBoolean("automaticFocus");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "flashMode", ReadableType.String)) {
            Log.i(TAG, "LensEngine flashMode set");
            flashMode = configuration.getString("flashMode");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "focusMode", ReadableType.String)) {
            Log.i(TAG, "LensEngine focusMode set");
            focusMode = configuration.getString("focusMode");
        }

        return new LensEngine.Creator(context, analyzer).setLensType(lensType)
            .setFocusMode(focusMode)
            .setFlashMode(flashMode)
            .enableAutomaticFocus(automaticFocus)
            .applyFps(fps)
            .applyDisplayDimension(width, height)
            .create();
    }

    /**
     * Creates analyzer for lens engine
     *
     * @param analyzer analyzer tag number
     * @param analyzerConfig analyzer configuration for related tag
     * @param context app context
     * @return MLAnalyzer
     */
    public MLAnalyzer createLensEngineAnalyzer(int analyzer, ReadableMap analyzerConfig,
        ReactApplicationContext context) {
        switch (analyzer) {
            case 0:
                MLTextAnalyzer localTextAnalyzer = createTextAnalyzer(analyzerConfig, context);
                localTextAnalyzer.setTransactor(new HMSTextAnalyzerTransactor(context));
                return localTextAnalyzer;
            case 1:
                MLFaceAnalyzer faceAnalyzer2d = create2DFaceAnalyzer(analyzerConfig);
                faceAnalyzer2d.setTransactor(new HMS2DFaceAnalyzerTransactor(context));
                return faceAnalyzer2d;
            case 2:
                ML3DFaceAnalyzer faceAnalyzer3d = create3DFaceAnalyzer(analyzerConfig);
                faceAnalyzer3d.setTransactor(new HMS3DFaceAnalyzerTransactor(context));
                return faceAnalyzer3d;
            case 3:
                int analyzeType = HMSUtils.getInstance().hasValidKey(analyzerConfig, "analyzeType", ReadableType.Number)
                    ? analyzerConfig.getInt("analyzeType")
                    : MLSkeletonAnalyzerSetting.TYPE_NORMAL;
                MLSkeletonAnalyzer skeletonAnalyzer = createSkeletonAnalyzer(analyzeType);
                skeletonAnalyzer.setTransactor(new HMSSkeletonAnalyzerTransactor(context));
                return skeletonAnalyzer;
            case 4:
                MLImageClassificationAnalyzer classificationAnalyzer = createClassificationAnalyzer(false,
                    analyzerConfig);
                classificationAnalyzer.setTransactor(new HMSClassificationAnalyzerTransactor(context));
                return classificationAnalyzer;
            case 5:
                MLObjectAnalyzer objectAnalyzer = createObjectAnalyzer(analyzerConfig);
                objectAnalyzer.setTransactor(new HMSObjectAnalyzerTransactor(context));
                return objectAnalyzer;
            case 6:
                double confidence = HMSUtils.getInstance()
                    .hasValidKey(analyzerConfig, "confidence", ReadableType.Number) ? analyzerConfig.getInt(
                    "confidence") : 0.0;
                MLSceneDetectionAnalyzer sceneDetectionAnalyzer = MLSceneDetectionAnalyzerFactory.getInstance()
                    .getSceneDetectionAnalyzer(createScdAnalyzerSetting(confidence));
                sceneDetectionAnalyzer.setTransactor(new HMSSceneDetectionAnalyzerTransactor(context));
                return sceneDetectionAnalyzer;
            case 7:
                MLHandKeypointAnalyzer handKeypointAnalyzer = createHandKeyPointAnalyzer(analyzerConfig);
                handKeypointAnalyzer.setTransactor(new HMSHandKeypointTransactor(context));
                return handKeypointAnalyzer;
            case 8:
                MLGestureAnalyzer gestureAnalyzer = createGestureAnalyzer();
                gestureAnalyzer.setTransactor(new HMSGestureTransactor(context));
                return gestureAnalyzer;
            default:
                return null;
        }
    }

}
