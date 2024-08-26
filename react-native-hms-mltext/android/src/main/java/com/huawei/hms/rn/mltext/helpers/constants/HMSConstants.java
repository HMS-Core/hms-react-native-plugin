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

package com.huawei.hms.rn.mltext.helpers.constants;

import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.ANALYZER_NOT_AVAILABLE;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.ASR_RECOGNIZER_NULL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.CANCEL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.CURRENT_ACTIVITY_NULL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.CUSTOM_MODEL_EXECUTOR_SETTING_NULL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.CUSTOM_MODEL_INPUT_NULL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.CUSTOM_MODEL_SETTING_NULL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.DATA_SET_NOT_VALID;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.DENY;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.FRAME_NULL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.LENS_ENGINE_NULL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.LENS_HOLDER_NULL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.REMOTE_MODEL_NULL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.SOUND_DECT_NULL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.STRING_PARAM_NULL;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.TTS_ENGINE_NULL;

import android.graphics.Color;
import android.hardware.Camera;

import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureConfig;
import com.huawei.hms.mlsdk.common.LensEngine;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.document.MLDocument;
import com.huawei.hms.mlsdk.text.MLLocalTextSetting;
import com.huawei.hms.mlsdk.text.MLRemoteTextSetting;
import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingException;
import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingSetting;

import com.facebook.common.internal.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public final class HMSConstants {

    // Bcr
    public static final String BCR_IMAGE_SAVE = "bcrSuccessImage";

    // Gcr
    public static final String GCR_IMAGE_SAVE = "gcrOnResult";

    // Id card
    public static final String IDCARD_IMAGE_SAVE = "idCardOnResult";

    // VN Id card
    public static final String ICRVN_IMAGE_SAVE = "vnCardOnResult";

    // lens engine
    public static final String LENS_ON_PHOTO_TAKEN = "lensOnPhotoTaken";

    public static final String LENS_ON_CLICK_SHUTTER = "lensOnClickShutter";

    public static final String LENS_SURFACE_ON_CREATED = "lensSurfaceOnCreated";

    public static final String LENS_SURFACE_ON_CHANGED = "lensSurfaceOnChanged";

    public static final String LENS_SURFACE_ON_DESTROY = "lensSurfaceOnDestroyed";

    // text transactor
    public static final String TEXT_TRANSACTOR_ON_DESTROY = "textTransactorOnDestroy";

    public static final String TEXT_TRANSACTOR_ON_RESULT = "textTransactorOnResult";

    // on-device languages
    private static final Map<String, Object> BASE_LANGUAGES = new HashMap<String, Object>() {
        {
            put("LATIN", "rm");
            put("ENGLISH", "en");
            put("CHINESE", "zh");
            put("JAPANESE", "ja");
            put("KOREAN", "ko");
            put("RUSSIAN", "ru");
            put("GERMAN", "de");
            put("FRENCH", "fr");
            put("ITALIAN", "it");
            put("PORTUGUESE", "pt");
            put("SPANISH", "es");
        }
    };

    // on-cloud languages
    private static final Map<String, Object> EXTRA_LANGUAGES = new HashMap<String, Object>() {
        {
            put("POLISH", "pl");
            put("NORWEGIAN", "no");
            put("SWEDISH", "sv");
            put("DANISH", "da");
            put("TURKISH", "tr");
            put("FINNISH", "fi");
            put("THAI", "th");
            put("ARABIC", "ar");
            put("HINDI", "hi");
        }
    };

    // border types
    private static final Map<String, Object> BORDER_TYPE = new HashMap<String, Object>() {
        {
            put("ARC", MLRemoteTextSetting.ARC);
            put("NGON", MLRemoteTextSetting.NGON);
        }
    };

    // device orientation constants
    private static final Map<String, Object> ORIENTATION_CONSTANTS = new HashMap<String, Object>() {
        {
            put("ORIENTATION_AUTO", 0);
            put("ORIENTATION_LANDSCAPE", 1);
            put("ORIENTATION_PORTRAIT", 2);
        }
    };

    // color codes of Android Color class
    private static final Map<String, Object> COLOR_CONSTANTS = new HashMap<String, Object>() {
        {
            put("BLACK", Color.BLACK);
            put("BLUE", Color.BLUE);
            put("CYAN", Color.CYAN);
            put("DKGRAY", Color.DKGRAY);
            put("GRAY", Color.GRAY);
            put("GREEN", Color.GREEN);
            put("LTGRAY", Color.LTGRAY);
            put("MAGENTA", Color.MAGENTA);
            put("RED", Color.RED);
            put("TRANSPARENT", Color.TRANSPARENT);
            put("WHITE", Color.WHITE);
            put("YELLOW", Color.YELLOW);
        }
    };

    // error codes exposed by HMSApplication
    public static final Map<String, Object> ERROR_CODES = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("UNKNOWN", MLException.UNKNOWN);
            put("SUCCESS", MLException.SUCCESS);
            put("DISCARDED", MLException.DISCARDED);
            put("INNER", MLException.INNER);
            put("INACTIVE", MLException.INACTIVE);
            put("NOT_SUPPORTED", MLException.NOT_SUPPORTED);
            put("ILLEGAL_PARAMETER", MLException.ILLEGAL_PARAMETER);
            put("OVERDUE", MLException.OVERDUE);
            put("NO_FOUND", MLException.NO_FOUND);
            put("DUPLICATE_FOUND", MLException.DUPLICATE_FOUND);
            put("NO_PERMISSION", MLException.NO_PERMISSION);
            put("INSUFFICIENT_RESOURCE", MLException.INSUFFICIENT_RESOURCE);
            put("ANALYSIS_FAILURE", MLException.ANALYSIS_FAILURE);
            put("INTERRUPTED", MLException.INTERRUPTED);
            put("EXCEED_RANGE", MLException.EXCEED_RANGE);
            put("DATA_MISSING", MLException.DATA_MISSING);
            put("AUTHENTICATION_REQUIRED", MLException.AUTHENTICATION_REQUIRED);
            put("TFLITE_NOT_COMPATIBLE", MLException.TFLITE_NOT_COMPATIBLE);
            put("INSUFFICIENT_SPACE", MLException.INSUFFICIENT_SPACE);
            put("HASH_MISS", MLException.HASH_MISS);
            put("TOKEN_INVALID", MLException.TOKEN_INVALID);
            put("FRAME_NULL", FRAME_NULL.getErrCode());
            put("ANALYZER_NOT_AVAILABLE", ANALYZER_NOT_AVAILABLE.getErrCode());
            put("CURRENT_ACTIVITY_NULL", CURRENT_ACTIVITY_NULL.getErrCode());
            put("CANCEL", CANCEL.getErrCode());
            put("FAILURE", FAILURE.getErrCode());
            put("DENY", DENY.getErrCode());
            put("STRING_PARAM_NULL", STRING_PARAM_NULL.getErrCode());
            put("REMOTE_MODEL_NULL", REMOTE_MODEL_NULL.getErrCode());
            put("ASR_RECOGNIZER_NULL", ASR_RECOGNIZER_NULL.getErrCode());
            put("TTS_ENGINE_NULL", TTS_ENGINE_NULL.getErrCode());
            put("SOUND_DECT_NULL", SOUND_DECT_NULL.getErrCode());
            put("CUSTOM_MODEL_SETTING_NULL", CUSTOM_MODEL_SETTING_NULL.getErrCode());
            put("CUSTOM_MODEL_INPUT_NULL", CUSTOM_MODEL_INPUT_NULL.getErrCode());
            put("CUSTOM_MODEL_EXECUTOR_SETTING_NULL", CUSTOM_MODEL_EXECUTOR_SETTING_NULL.getErrCode());
            put("DATA_SET_NOT_VALID", DATA_SET_NOT_VALID.getErrCode());
            put("LENS_ENGINE_NULL", LENS_ENGINE_NULL.getErrCode());
            put("LENS_HOLDER_NULL", LENS_HOLDER_NULL.getErrCode());
        }
    });

    // text analyzer setting constants HMSTextRecognition
    public static final Map<String, Object> TEXT_SETTING_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            putAll(BASE_LANGUAGES);
            putAll(EXTRA_LANGUAGES);
            putAll(BORDER_TYPE);
            put("OCR_DETECT_MODE", MLLocalTextSetting.OCR_DETECT_MODE);
            put("OCR_TRACKING_MODE", MLLocalTextSetting.OCR_TRACKING_MODE);
            put("OCR_COMPACT_SCENE", MLRemoteTextSetting.OCR_COMPACT_SCENE);
            put("OCR_LOOSE_SCENE", MLRemoteTextSetting.OCR_LOOSE_SCENE);
        }
    });

    // document analyzer constants HMSDocumentRecognition
    public static final Map<String, Object> DOCUMENT_RECOGNITION_CONSTANTS = ImmutableMap.copyOf(
        new HashMap<String, Object>() {
            {
                putAll(BASE_LANGUAGES);
                putAll(EXTRA_LANGUAGES);
                putAll(BORDER_TYPE);
                put("OTHER", MLDocument.Interval.OTHER);
                put("NEW_LINE_CHARACTER", MLDocument.Interval.NEW_LINE_CHARACTER);
                put("SPACE", MLDocument.Interval.SPACE);
            }
        });

    // bank card recognition constants HMSBankCardRecognition
    public static final Map<String, Object> BCR_PLUGIN_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            putAll(ORIENTATION_CONSTANTS);
            put("ERROR_CODE_INIT_CAMERA_FAILED", MLBcrCaptureConfig.ERROR_CODE_INIT_CAMERA_FAILED);
            put("RESULT_ALL", MLBcrCaptureConfig.RESULT_ALL);
            put("RESULT_NUM_ONLY", MLBcrCaptureConfig.RESULT_NUM_ONLY);
            put("RESULT_SIMPLE", MLBcrCaptureConfig.RESULT_SIMPLE);
            put("STRICT_MODE", MLBcrCaptureConfig.STRICT_MODE);
            put("SIMPLE_MODE", MLBcrCaptureConfig.SIMPLE_MODE);
            put("BCR_IMAGE_SAVE", BCR_IMAGE_SAVE);
        }
    });

    // general card recognition constants HMSGeneralCardRecognition
    public static final Map<String, Object> GCR_PLUGIN_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            putAll(ORIENTATION_CONSTANTS);
            putAll(COLOR_CONSTANTS);
            put("GCR_IMAGE_SAVE", GCR_IMAGE_SAVE);
        }
    });

    // vn card recognition constants HMSVietnamCardRecognition
    public static final Map<String, Object> ICRVN_PLUGIN_CONSTANTS = ImmutableMap.copyOf(
            new HashMap<String, Object>() {
                {
                    put("ICRVN_IMAGE_SAVE", ICRVN_IMAGE_SAVE);
                }
            });

    // id card recognition constants HMSIDCardRecognition
    public static final Map<String, Object> IDCARD_PLUGIN_CONSTANTS = ImmutableMap.copyOf(
            new HashMap<String, Object>() {
                {
                    put("IDCARD_IMAGE_SAVE", IDCARD_IMAGE_SAVE);
                }
            });



    // text embedding constants HMSTextEmbedding
    public static final Map<String, Object> TEXT_EMBED_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("LANGUAGE_EN", MLTextEmbeddingSetting.LANGUAGE_EN);
            put("LANGUAGE_ZH", MLTextEmbeddingSetting.LANGUAGE_ZH);
            put("INNER", MLTextEmbeddingException.ERR_INNER);
            put("ERR_AUTH_FAILED", MLTextEmbeddingException.ERR_AUTH_FAILED);
            put("ERR_PARAM_ILLEGAL", MLTextEmbeddingException.ERR_PARAM_ILLEGAL);
            put("ERR_ANALYZE_FAILED", MLTextEmbeddingException.ERR_ANALYZE_FAILED);
            put("ERR_AUTH_TOKEN_INVALID", MLTextEmbeddingException.ERR_AUTH_TOKEN_INVALIDE);
            put("ERR_NET_UNAVAILABLE", MLTextEmbeddingException.ERR_NET_UNAVAILABLE);
            put("ERR_SERVICE_IS_UNAVAILABLE", MLTextEmbeddingException.ERR_SERVICE_IS_UNAVAILABLE);
        }
    });

    // lens engine constants
    public static final Map<String, Object> LENS_ENGINE_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("FLASH_MODE_OFF", Camera.Parameters.FLASH_MODE_OFF);
            put("FLASH_MODE_ON", Camera.Parameters.FLASH_MODE_ON);
            put("FLASH_MODE_AUTO", Camera.Parameters.FLASH_MODE_AUTO);
            put("FOCUS_MODE_CONTINUOUS_PICTURE", Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            put("FOCUS_MODE_CONTINUOUS_VIDEO", Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            put("BACK_LENS", LensEngine.BACK_LENS);
            put("FRONT_LENS", LensEngine.FRONT_LENS);
            put("LENS_ON_PHOTO_TAKEN", LENS_ON_PHOTO_TAKEN);
            put("LENS_ON_CLICK_SHUTTER", LENS_ON_CLICK_SHUTTER);
            put("LENS_SURFACE_ON_CREATED", LENS_SURFACE_ON_CREATED);
            put("LENS_SURFACE_ON_CHANGED", LENS_SURFACE_ON_CHANGED);
            put("LENS_SURFACE_ON_DESTROY", LENS_SURFACE_ON_DESTROY);
            put("TEXT_TRANSACTOR_ON_DESTROY", TEXT_TRANSACTOR_ON_DESTROY);
            put("TEXT_TRANSACTOR_ON_RESULT", TEXT_TRANSACTOR_ON_RESULT);
            put("LENS_TEXT_ANALYZER", 0);
            put("LENS_FACE_2D_ANALYZER", 1);
            put("LENS_FACE_3D_ANALYZER", 2);
            put("LENS_SKELETON_ANALYZER", 3);
            put("LENS_CLASSIFICATION_ANALYZER", 4);
            put("LENS_OBJECT_ANALYZER", 5);
            put("LENS_SCENE_ANALYZER", 6);
            put("LENS_HAND_ANALYZER", 7);
            put("LENS_GESTURE_ANALYZER", 8);
        }
    });
}