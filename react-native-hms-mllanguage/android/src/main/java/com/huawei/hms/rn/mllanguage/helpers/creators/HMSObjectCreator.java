/*
 * Copyright 2023-2024. Huawei Technologies Co., Ltd. All rights reserved.
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

package com.huawei.hms.rn.mllanguage.helpers.creators;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;


import com.huawei.hms.mlsdk.aft.cloud.MLRemoteAftSetting;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.custom.MLCustomLocalModel;
import com.huawei.hms.mlsdk.custom.MLCustomRemoteModel;
import com.huawei.hms.mlsdk.custom.MLModelDataType;
import com.huawei.hms.mlsdk.custom.MLModelExecutorSettings;
import com.huawei.hms.mlsdk.custom.MLModelInputOutputSettings;
import com.huawei.hms.mlsdk.custom.MLModelInputs;
import com.huawei.hms.mlsdk.langdetect.MLLangDetectorFactory;
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetector;
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetectorSetting;
import com.huawei.hms.mlsdk.langdetect.local.MLLocalLangDetector;
import com.huawei.hms.mlsdk.langdetect.local.MLLocalLangDetectorSetting;
import com.huawei.hms.mlsdk.model.download.MLModelDownloadStrategy;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.speechrtt.MLSpeechRealTimeTranscriptionConfig;
import com.huawei.hms.mlsdk.speechrtt.MLSpeechRealTimeTranscriptionConstants;
import com.huawei.hms.mlsdk.translate.MLTranslatorFactory;
import com.huawei.hms.mlsdk.translate.cloud.MLRemoteTranslateSetting;
import com.huawei.hms.mlsdk.translate.cloud.MLRemoteTranslator;
import com.huawei.hms.mlsdk.translate.local.MLLocalTranslateSetting;
import com.huawei.hms.mlsdk.translate.local.MLLocalTranslator;
import com.huawei.hms.mlsdk.translate.local.MLLocalTranslatorModel;
import com.huawei.hms.mlsdk.tts.MLTtsConfig;
import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import com.huawei.hms.mlsdk.tts.MLTtsLocalModel;
import com.huawei.hms.rn.mllanguage.helpers.utils.HMSUtils;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import java.io.IOException;

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
        double speed = 1.0D;
        double volume = 1.0D;
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

}
