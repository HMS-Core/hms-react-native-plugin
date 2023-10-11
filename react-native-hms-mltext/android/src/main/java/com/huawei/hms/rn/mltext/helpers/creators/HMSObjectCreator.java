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

package com.huawei.hms.rn.mltext.helpers.creators;

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
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.card.MLCardAnalyzerFactory;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzer;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzerSetting;
import com.huawei.hms.mlsdk.common.LensEngine;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.document.MLDocumentAnalyzer;
import com.huawei.hms.mlsdk.document.MLDocumentSetting;
import com.huawei.hms.mlsdk.fr.MLFormRecognitionAnalyzer;
import com.huawei.hms.mlsdk.fr.MLFormRecognitionAnalyzerFactory;
import com.huawei.hms.mlsdk.fr.MLFormRecognitionAnalyzerSetting;
import com.huawei.hms.mlsdk.text.MLLocalTextSetting;
import com.huawei.hms.mlsdk.text.MLRemoteTextSetting;
import com.huawei.hms.mlsdk.text.MLTextAnalyzer;
import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingAnalyzer;
import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingAnalyzerFactory;
import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingSetting;
import com.huawei.hms.rn.mltext.helpers.transactors.HMSTextAnalyzerTransactor;
import com.huawei.hms.rn.mltext.helpers.utils.HMSUtils;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

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
            default:
                return null;
        }
    }

}
