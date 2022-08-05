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

package com.huawei.hms.rn.ml.helpers.constants;

import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.ANALYZER_NOT_AVAILABLE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.ASR_RECOGNIZER_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CANCEL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CURRENT_ACTIVITY_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CUSTOM_MODEL_EXECUTOR_SETTING_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CUSTOM_MODEL_INPUT_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CUSTOM_MODEL_SETTING_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.DATA_SET_NOT_VALID;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.DENY;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.LENS_ENGINE_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.LENS_HOLDER_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.REMOTE_MODEL_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SOUND_DECT_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.STRING_PARAM_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.TTS_ENGINE_NULL;

import android.graphics.Color;
import android.hardware.Camera;

import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureConfig;
import com.huawei.hms.mlsdk.aft.MLAftConstants;
import com.huawei.hms.mlsdk.aft.MLAftErrors;
import com.huawei.hms.mlsdk.asr.MLAsrConstants;
import com.huawei.hms.mlsdk.common.LensEngine;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.document.MLDocument;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionConstant;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.face.MLFaceKeyPoint;
import com.huawei.hms.mlsdk.face.MLFaceShape;
import com.huawei.hms.mlsdk.gesture.MLGesture;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypoint;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypointAnalyzerSetting;
import com.huawei.hms.mlsdk.imagesuperresolution.MLImageSuperResolutionAnalyzerSetting;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationClassification;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationScene;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmarkAnalyzerSetting;
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetector;
import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCaptureConfig;
import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCaptureError;
import com.huawei.hms.mlsdk.model.download.MLModelDownloadStrategy;
import com.huawei.hms.mlsdk.objects.MLObject;
import com.huawei.hms.mlsdk.objects.MLObjectAnalyzerSetting;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.MLRemoteProductVisionSearchAnalyzerSetting;
import com.huawei.hms.mlsdk.skeleton.MLJoint;
import com.huawei.hms.mlsdk.skeleton.MLSkeletonAnalyzerSetting;
import com.huawei.hms.mlsdk.sounddect.MLSoundDetectConstants;
import com.huawei.hms.mlsdk.speechrtt.MLSpeechRealTimeTranscriptionConstants;
import com.huawei.hms.mlsdk.text.MLLocalTextSetting;
import com.huawei.hms.mlsdk.text.MLRemoteTextSetting;
import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingException;
import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingSetting;
import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment;
import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import com.huawei.hms.mlsdk.tts.MLTtsEngine;
import com.huawei.hms.mlsdk.tts.MLTtsError;
import com.huawei.hms.mlsdk.tts.MLTtsWarn;

import com.facebook.common.internal.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public final class HMSConstants {
    // Translate
    public static final String TRANSLATE_DOWNLOAD_ON_PROCESS = "translateDownloadProcess";

    // Tts
    public static final String TTS_ON_AUDIO_AVAILABLE = "ttsOnAudioAvailable";

    public static final String TTS_ON_EVENT = "ttsOnEvent";

    public static final String TTS_ON_RANGE_START = "ttsOnRangeStart";

    public static final String TTS_ON_WARN = "ttsOnWarn";

    public static final String TTS_ON_ERROR = "ttsOnError";

    // Aft
    public static final String AFT_ON_INIT_COMPLETE = "aftOnInitComplete";

    public static final String AFT_ON_UPLOAD_PROGRESS = "aftOnUploadProgress";

    public static final String AFT_ON_EVENT = "aftOnEvent";

    public static final String AFT_ON_RESULT = "aftOnResult";

    public static final String AFT_ON_ERROR = "aftOnError";

    // Speech rtt
    public static final String SPEECH_RTT_ON_RECOGNIZING_RESULTS = "speechRttOnRecognizingResults";

    public static final String SPEECH_RTT_ON_ERROR = "speechRttOnError";

    public static final String SPEECH_RTT_ON_LISTENING = "speechRttOnListening";

    public static final String SPEECH_RTT_ON_STARTING_OF_SPEECH = "speechRttOnStartingOfSpeech";

    public static final String SPEECH_RTT_ON_VOICE_DATA_RECEIVED = "speechRttOnVoiceDataReceived";

    public static final String SPEECH_RTT_ON_STATE = "speechRttOnState";

    // Sound detect
    public static final String SOUND_DETECT_ON_FAILURE = "soundDetectOnFailure";

    public static final String SOUND_DETECT_ON_SUCCESS = "soundDetectOnSuccess";

    // Asr
    public static final String ASR_ON_STATE = "asrOnState";

    public static final String ASR_ON_VOICE_DATA_RECEIVED = "asrOnVoiceDataReceived";

    public static final String ASR_ON_STARTING_SPEECH = "asrOnStartingOfSpeech";

    public static final String ASR_ON_START_LISTENING = "asrOnStartListening";

    public static final String ASR_ON_ERROR = "asrOnError";

    public static final String ASR_ON_RECOGNIZING_RESULTS = "asrOnRecognizingResults";

    public static final String ASR_ON_RESULTS = "asrOnResults";

    // Model download
    public static final String DOWNLOAD_ON_PROCESS = "modelDownloadOnProcess";

    public static final int MODEL_TTS_TAG = 1;

    public static final int MODEL_TRANSLATE_TAG = 2;

    public static final int MODEL_CUSTOM_TAG = 3;

    // Bcr
    public static final String BCR_IMAGE_SAVE = "bcrSuccessImage";

    // Gcr
    public static final String GCR_IMAGE_SAVE = "gcrOnResult";

    // Id card
    public static final String IDCARD_IMAGE_SAVE = "idCardOnResult";

    // VN Id card
    public static final String ICRVN_IMAGE_SAVE = "vnCardOnResult";

    // product vision plugin
    public static final String PRODUCT_ON_RESULT = "productOnResult";

    // lens engine
    public static final String LENS_ON_PHOTO_TAKEN = "lensOnPhotoTaken";

    public static final String LENS_ON_CLICK_SHUTTER = "lensOnClickShutter";

    public static final String LENS_SURFACE_ON_CREATED = "lensSurfaceOnCreated";

    public static final String LENS_SURFACE_ON_CHANGED = "lensSurfaceOnChanged";

    public static final String LENS_SURFACE_ON_DESTROY = "lensSurfaceOnDestroyed";

    // text transactor
    public static final String TEXT_TRANSACTOR_ON_DESTROY = "textTransactorOnDestroy";

    public static final String TEXT_TRANSACTOR_ON_RESULT = "textTransactorOnResult";

    // 2d face transactor
    public static final String FACE_2D_TRANSACTOR_ON_DESTROY = "face2dTransactorOnDestroy";

    public static final String FACE_2D_TRANSACTOR_ON_RESULT = "face2dTransactorOnResult";

    // 3d face transactor
    public static final String FACE_3D_TRANSACTOR_ON_DESTROY = "face3dTransactorOnDestroy";

    public static final String FACE_3D_TRANSACTOR_ON_RESULT = "face3dTransactorOnResult";

    // skeleton transactor
    public static final String SKELETON_TRANSACTOR_ON_DESTROY = "skeletonTransactorOnDestroy";

    public static final String SKELETON_TRANSACTOR_ON_RESULT = "skeletonTransactorOnResult";

    // scene transactor
    public static final String SCENE_TRANSACTOR_ON_DESTROY = "sceneTransactorOnDestroy";

    public static final String SCENE_TRANSACTOR_ON_RESULT = "sceneTransactorOnResult";

    // classification transactor
    public static final String CLASSIFICATION_TRANSACTOR_ON_DESTROY = "classificationTransactorOnDestroy";

    public static final String CLASSIFICATION_TRANSACTOR_ON_RESULT = "classificationTransactorOnResult";

    // object transactor
    public static final String OBJECT_TRANSACTOR_ON_DESTROY = "objectTransactorOnDestroy";

    public static final String OBJECT_TRANSACTOR_ON_RESULT = "objectTransactorOnResult";

    // hand keypoint transactor
    public static final String HAND_TRANSACTOR_ON_DESTROY = "handTransactorOnDestroy";

    public static final String HAND_TRANSACTOR_ON_RESULT = "handTransactorOnResult";

    // gesture transactor
    public static final String GESTURE_TRANSACTOR_ON_DESTROY = "gestureTransactorOnDestroy";

    public static final String GESTURE_TRANSACTOR_ON_RESULT = "gestureTransactorOnResult";

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

    // translate language constants HMSTranslate
    public static final Map<String, Object> TRANSLATE_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("TRANSLATE_DOWNLOAD_ON_PROCESS", TRANSLATE_DOWNLOAD_ON_PROCESS);
            putAll(BASE_LANGUAGES);
            putAll(EXTRA_LANGUAGES);
            put("TAMIL", "ta");
            put("HUNGARIAN", "hu");
            put("DUTCH", "nl");
            put("PERSIAN", "fa");
            put("SLOVAK", "sk");
            put("ESTONIAN", "et");
            put("LATVIAN", "lv");
            put("KHMER", "km");
            put("INDONESIAN", "id");
            put("SERBIAN", "sr");
            put("GUJARATI", "gu");
            put("AFRIKAANS", "af");
            put("MALAY", "ms");
            put("GREEK", "el");
            put("IRISH", "is");
            put("BURMESE", "my");
            put("MARATHI", "mr");
            put("CZECH", "cs");
            put("VIETNAMESE", "vi");
            put("TAGALOG", "tl");
            put("HEBREW", "he");
            put("ROMANIAN", "ro");
            put("BULGARIAN", "bg");
            put("CROATIAN", "hr");
            put("PUNJABI", "pa");
            put("TELUGU", "te");
            put("TRADITIONAL_CHINESE", "zh-hk");
        }
    });

    // frame constants HMSFrame
    public static final Map<String, Object> FRAME_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("SCREEN_FIRST_QUADRANT", MLFrame.SCREEN_FIRST_QUADRANT);
            put("SCREEN_SECOND_QUADRANT", MLFrame.SCREEN_SECOND_QUADRANT);
            put("SCREEN_THIRD_QUADRANT", MLFrame.SCREEN_THIRD_QUADRANT);
            put("SCREEN_FOURTH_QUADRANT", MLFrame.SCREEN_FOURTH_QUADRANT);
            put("IMAGE_FORMAT_NV21", MLFrame.Property.IMAGE_FORMAT_NV21);
            put("IMAGE_FORMAT_YV12", MLFrame.Property.IMAGE_FORMAT_YV12);
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

    // language detection constants HMSLanguageDetection
    public static final Map<String, Object> LANGUAGE_DETECTION_CONSTANTS = ImmutableMap.copyOf(
        new HashMap<String, Object>() {
            {
                put("FIRST_BEST_DETECTION_LANGUAGE_TRUSTED_THRESHOLD",
                    MLRemoteLangDetector.FIRST_BEST_DETECTION_LANGUAGE_TRUSTED_THRESHOLD);
                put("PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD",
                    MLRemoteLangDetector.PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD);
                put("UNDETECTION_LANGUAGE_TRUSTED_THRESHOLD",
                    MLRemoteLangDetector.UNDETECTION_LANGUAGE_TRUSTED_THRESHOLD);
            }
        });

    // asr constants HMSAsr
    public static final Map<String, Object> ASR_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("ERR_NO_NETWORK", MLAsrConstants.ERR_NO_NETWORK);
            put("ERR_NO_UNDERSTAND", MLAsrConstants.ERR_NO_UNDERSTAND);
            put("ERR_SERVICE_UNAVAILABLE", MLAsrConstants.ERR_SERVICE_UNAVAILABLE);
            put("ERR_INVALIDATE_TOKEN", MLAsrConstants.ERR_INVALIDATE_TOKEN);
            put("FEATURE_ALL_IN_ONE", MLAsrConstants.FEATURE_ALLINONE);
            put("FEATURE_WORD_FLUX", MLAsrConstants.FEATURE_WORDFLUX);
            put("LAN_EN_US", MLAsrConstants.LAN_EN_US);
            put("LAN_FR_FR", MLAsrConstants.LAN_FR_FR);
            put("LAN_ZH_CN", MLAsrConstants.LAN_ZH_CN);
            put("LAN_ES_ES", MLAsrConstants.LAN_ES_ES);
            put("LAN_DE_DE", MLAsrConstants.LAN_DE_DE);
            put("LAN_RU_RU", MLAsrConstants.LAN_RU_RU);
            put("LAN_IT_IT", MLAsrConstants.LAN_IT_IT);
            put("LAN_AR", MLAsrConstants.LAN_AR);
            put("LAN_RU_RU", MLAsrConstants.LAN_RU_RU);
            put("LAN_TH_TH", MLAsrConstants.LAN_TH_TH);
            put("LAN_MS_MY", MLAsrConstants.LAN_MS_MY);
            put("LAN_FIL_PH", MLAsrConstants.LAN_FIL_PH);
            put("STATE_WAITING", MLAsrConstants.STATE_WAITING);
            put("STATE_NO_UNDERSTAND", MLAsrConstants.STATE_NO_UNDERSTAND);
            put("STATE_NO_SOUND_TIMES_EXCEED", MLAsrConstants.STATE_NO_SOUND_TIMES_EXCEED);
            put("STATE_NO_SOUND", MLAsrConstants.STATE_NO_SOUND);
            put("STATE_NO_NETWORK", MLAsrConstants.STATE_NO_NETWORK);
            put("STATE_LISTENING", MLAsrConstants.STATE_LISTENING);
            put("ASR_ON_ERROR", ASR_ON_ERROR);
            put("ASR_ON_RECOGNIZING_RESULTS", ASR_ON_RECOGNIZING_RESULTS);
            put("ASR_ON_RESULTS", ASR_ON_RESULTS);
            put("ASR_ON_START_LISTENING", ASR_ON_START_LISTENING);
            put("ASR_ON_STARTING_SPEECH", ASR_ON_STARTING_SPEECH);
            put("ASR_ON_STATE", ASR_ON_STATE);
            put("ASR_ON_VOICE_DATA_RECEIVED", ASR_ON_VOICE_DATA_RECEIVED);
        }
    });

    // tts constants HMSTextToSpeech
    public static final Map<String, Object> TTS_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("TTS_OFFLINE_MODE", MLTtsConstants.TTS_OFFLINE_MODE);
            put("TTS_ONLINE_MODE", MLTtsConstants.TTS_ONLINE_MODE);
            put("EXTERNAL_PLAYBACK", MLTtsEngine.EXTERNAL_PLAYBACK);
            put("OPEN_STREAM", MLTtsEngine.OPEN_STREAM);
            put("QUEUE_APPEND", MLTtsEngine.QUEUE_APPEND);
            put("QUEUE_FLUSH", MLTtsEngine.QUEUE_FLUSH);
            put("ERR_AUDIO_PLAYER_FAILED", MLTtsError.ERR_AUDIO_PLAYER_FAILED);
            put("ERR_AUTHORIZE_FAILED", MLTtsError.ERR_AUTHORIZE_FAILED);
            put("ERR_ILLEGAL_PARAMETER", MLTtsError.ERR_ILLEGAL_PARAMETER);
            put("ERR_INSUFFICIENT_BALANCE", MLTtsError.ERR_INSUFFICIENT_BALANCE);
            put("ERR_INTERNAL", MLTtsError.ERR_INTERNAL);
            put("ERR_NET_CONNECT_FAILED", MLTtsError.ERR_NET_CONNECT_FAILED);
            put("ERR_SPEECH_SYNTHESIS_FAILED", MLTtsError.ERR_SPEECH_SYNTHESIS_FAILED);
            put("ERR_UNKNOWN", MLTtsError.ERR_UNKNOWN);
            put("WARN_INSUFFICIENT_BANDWIDTH", MLTtsWarn.WARN_INSUFFICIENT_BANDWIDTH);
            put("EVENT_PLAY_PAUSE", MLTtsConstants.EVENT_PLAY_PAUSE);
            put("EVENT_PLAY_RESUME", MLTtsConstants.EVENT_PLAY_RESUME);
            put("EVENT_PLAY_START", MLTtsConstants.EVENT_PLAY_START);
            put("EVENT_PLAY_STOP", MLTtsConstants.EVENT_PLAY_STOP);
            put("EVENT_PLAY_STOP_INTERRUPTED", MLTtsConstants.EVENT_PLAY_STOP_INTERRUPTED);
            put("EVENT_SYNTHESIS_START", MLTtsConstants.EVENT_SYNTHESIS_START);
            put("EVENT_SYNTHESIS_COMPLETE", MLTtsConstants.EVENT_SYNTHESIS_COMPLETE);
            put("EVENT_SYNTHESIS_END", MLTtsConstants.EVENT_SYNTHESIS_END);
            put("EVENT_SYNTHESIS_INTERRUPTED", MLTtsConstants.EVENT_SYNTHESIS_INTERRUPTED);
            put("TTS_EN_US", MLTtsConstants.TTS_EN_US);
            put("TTS_LAN_DE_DE", MLTtsConstants.TTS_LAN_DE_DE);
            put("TTS_LAN_ES_ES", MLTtsConstants.TTS_LAN_ES_ES);
            put("TTS_LAN_FR_FR", MLTtsConstants.TTS_LAN_FR_FR);
            put("TTS_LAN_IT_IT", MLTtsConstants.TTS_LAN_IT_IT);
            put("TTS_LAN_TH_TH", MLTtsConstants.TTS_LAN_TH_TH);
            put("TTS_LAN_MS_MS", MLTtsConstants.TTS_LAN_MS_MS);
            put("TTS_LAN_RU_RU", MLTtsConstants.TTS_LAN_RU_RU);
            put("TTS_LAN_PL_PL", MLTtsConstants.TTS_LAN_PL_PL);
            put("TTS_LAN_AR_AR", MLTtsConstants.TTS_LAN_AR_AR);
            put("TTS_LAN_TR_TR", MLTtsConstants.TTS_LAN_TR_TR);
            put("TTS_SPEAKER_FEMALE_EN", MLTtsConstants.TTS_SPEAKER_FEMALE_EN);
            put("TTS_SPEAKER_FEMALE_ZH", MLTtsConstants.TTS_SPEAKER_FEMALE_ZH);
            put("TTS_SPEAKER_FEMALE_ZH_2", MLTtsConstants.TTS_SPEAKER_FEMALE_ZH_2);
            put("TTS_SPEAKER_MALE_EN", MLTtsConstants.TTS_SPEAKER_MALE_EN);
            put("TTS_SPEAKER_MALE_EN_2", MLTtsConstants.TTS_SPEAKER_MALE_EN_2);
            put("TTS_SPEAKER_MALE_ZH", MLTtsConstants.TTS_SPEAKER_MALE_ZH);
            put("TTS_SPEAKER_MALE_ZH_2", MLTtsConstants.TTS_SPEAKER_MALE_ZH_2);
            put("TTS_ZH_HANS", MLTtsConstants.TTS_ZH_HANS);
            put("TTS_SPEAKER_FEMALE_DE", MLTtsConstants.TTS_SPEAKER_FEMALE_DE);
            put("TTS_SPEAKER_FEMALE_ES", MLTtsConstants.TTS_SPEAKER_FEMALE_ES);
            put("TTS_SPEAKER_FEMALE_FR", MLTtsConstants.TTS_SPEAKER_FEMALE_FR);
            put("TTS_SPEAKER_FEMALE_IT", MLTtsConstants.TTS_SPEAKER_FEMALE_IT);
            put("TTS_SPEAKER_FEMALE_TH", MLTtsConstants.TTS_SPEAKER_FEMALE_TH);
            put("TTS_SPEAKER_FEMALE_MS", MLTtsConstants.TTS_SPEAKER_FEMALE_MS);
            put("TTS_SPEAKER_FEMALE_RU", MLTtsConstants.TTS_SPEAKER_FEMALE_RU);
            put("TTS_SPEAKER_FEMALE_PL", MLTtsConstants.TTS_SPEAKER_FEMALE_PL);
            put("TTS_SPEAKER_FEMALE_AR", MLTtsConstants.TTS_SPEAKER_FEMALE_AR);
            put("TTS_SPEAKER_FEMALE_TR", MLTtsConstants.TTS_SPEAKER_FEMALE_TR);
            put("TTS_SPEAKER_OFFLINE_DE_DE_FEMALE_BEE", MLTtsConstants.TTS_SPEAKER_OFFLINE_DE_DE_FEMALE_BEE);
            put("TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE", MLTtsConstants.TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE);
            put("TTS_SPEAKER_OFFLINE_ES_ES_FEMALE_BEE", MLTtsConstants.TTS_SPEAKER_OFFLINE_ES_ES_FEMALE_BEE);
            put("TTS_SPEAKER_OFFLINE_FR_FR_FEMALE_BEE", MLTtsConstants.TTS_SPEAKER_OFFLINE_FR_FR_FEMALE_BEE);
            put("TTS_SPEAKER_OFFLINE_IT_IT_FEMALE_BEE", MLTtsConstants.TTS_SPEAKER_OFFLINE_IT_IT_FEMALE_BEE);
            put("TTS_SPEAKER_OFFLINE_ZH_HANS_MALE_EAGLE", MLTtsConstants.TTS_SPEAKER_OFFLINE_ZH_HANS_MALE_EAGLE);
            put("TTS_SPEAKER_OFFLINE_ZH_HANS_FEMALE_EAGLE", MLTtsConstants.TTS_SPEAKER_OFFLINE_ZH_HANS_FEMALE_EAGLE);
            put("TTS_SPEAKER_OFFLINE_EN_US_MALE_EAGLE", MLTtsConstants.TTS_SPEAKER_OFFLINE_EN_US_MALE_EAGLE);
            put("TTS_SPEAKER_OFFLINE_EN_US_FEMALE_EAGLE", MLTtsConstants.TTS_SPEAKER_OFFLINE_EN_US_FEMALE_EAGLE);
            put("LANGUAGE_AVAILABLE", MLTtsConstants.LANGUAGE_AVAILABLE);
            put("LANGUAGE_NOT_SUPPORT", MLTtsConstants.LANGUAGE_NOT_SUPPORT);
            put("LANGUAGE_UPDATING", MLTtsConstants.LANGUAGE_UPDATING);
            put("CHANNEL_OUT_MONO", MLTtsAudioFragment.CHANNEL_OUT_MONO);
            put("FORMAT_PCM_8BIT", MLTtsAudioFragment.FORMAT_PCM_8BIT);
            put("FORMAT_PCM_16BIT", MLTtsAudioFragment.FORMAT_PCM_16BIT);
            put("SAMPLE_RATE_16K", MLTtsAudioFragment.SAMPLE_RATE_16K);
            put("TTS_ON_AUDIO_AVAILABLE", TTS_ON_AUDIO_AVAILABLE);
            put("TTS_ON_EVENT", TTS_ON_EVENT);
            put("TTS_ON_RANGE_START", TTS_ON_RANGE_START);
            put("TTS_ON_WARN", TTS_ON_WARN);
            put("TTS_ON_ERROR", TTS_ON_ERROR);
        }
    });

    // aft constants HMSAft
    public static final Map<String, Object> AFT_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("LANGUAGE_ZH", MLAftConstants.LANGUAGE_ZH);
            put("LANGUAGE_EN_US", MLAftConstants.LANGUAGE_EN_US);
            put("ERR_UNKNOWN", MLAftErrors.ERR_UNKNOWN);
            put("ERR_TASK_NOT_EXISTED", MLAftErrors.ERR_TASK_NOT_EXISTED);
            put("ERR_TASK_ALREADY_INPROGRESS", MLAftErrors.ERR_TASK_ALREADY_INPROGRESS);
            put("ERR_RESULT_WHEN_UPLOADING", MLAftErrors.ERR_RESULT_WHEN_UPLOADING);
            put("ERR_NO_ENOUGH_STORAGE", MLAftErrors.ERR_NO_ENOUGH_STORAGE);
            put("ERR_NETCONNECT_FAILED", MLAftErrors.ERR_NETCONNECT_FAILED);
            put("ERR_LANGUAGE_CODE_NOTSUPPORTED", MLAftErrors.ERR_LANGUAGE_CODE_NOTSUPPORTED);
            put("ERR_INTERNAL", MLAftErrors.ERR_INTERNAL);
            put("ERR_ILLEGAL_PARAMETER", MLAftErrors.ERR_ILLEGAL_PARAMETER);
            put("ERR_FILE_NOT_FOUND", MLAftErrors.ERR_FILE_NOT_FOUND);
            put("ERR_ENGINE_BUSY", MLAftErrors.ERR_ENGINE_BUSY);
            put("ERR_SERVICE_CREDIT", MLAftErrors.ERR_SERVICE_CREDIT);
            put("ERR_SERVICE_UNSUBSCRIBED", MLAftErrors.ERR_SERVICE_UNSUBSCRIBED);
            put("ERR_SERVICE_FREE_USED_UP", MLAftErrors.ERR_SERVICE_FREE_USED_UP);
            put("ERR_AUTHORIZE_FAILED", MLAftErrors.ERR_AUTHORIZE_FAILED);
            put("ERR_AUDIO_UPLOAD_FAILED", MLAftErrors.ERR_AUDIO_UPLOAD_FAILED);
            put("ERR_AUDIO_TRANSCRIPT_FAILED", MLAftErrors.ERR_AUDIO_TRANSCRIPT_FAILED);
            put("ERR_AUDIO_LENGTH_OVERFLOW", MLAftErrors.ERR_AUDIO_LENGTH_OVERFLOW);
            put("ERR_AUDIO_FILE_NOTSUPPORTED", MLAftErrors.ERR_AUDIO_FILE_NOTSUPPORTED);
            put("ERR_AUDIO_FILE_SIZE_OVERFLOW", MLAftErrors.ERR_AUDIO_FILE_SIZE_OVERFLOW);
            put("ERR_AUDIO_INIT_FAILED", MLAftErrors.ERR_AUDIO_INIT_FAILED);
            put("AFT_ON_ERROR", AFT_ON_ERROR);
            put("AFT_ON_EVENT", AFT_ON_EVENT);
            put("AFT_ON_INIT_COMPLETE", AFT_ON_INIT_COMPLETE);
            put("AFT_ON_UPLOAD_PROGRESS", AFT_ON_UPLOAD_PROGRESS);
            put("AFT_ON_RESULT", AFT_ON_RESULT);
        }
    });

    // speech rtt constants HMSSpeechRtt
    public static final Map<String, Object> SPEECH_RTT_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("ERR_INVALID_TOKEN", MLSpeechRealTimeTranscriptionConstants.ERR_INVALIDE_TOKEN);
            put("ERR_NO_NETWORK", MLSpeechRealTimeTranscriptionConstants.ERR_NO_NETWORK);
            put("ERR_SERVICE_CREDIT", MLSpeechRealTimeTranscriptionConstants.ERR_SERVICE_CREDIT);
            put("ERR_SERVICE_UNAVAILABLE", MLSpeechRealTimeTranscriptionConstants.ERR_SERVICE_UNAVAILABLE);
            put("ERR_SERVICE_UNSUBSCRIBED", MLSpeechRealTimeTranscriptionConstants.ERR_SERVICE_UNSUBSCRIBED);
            put("ERR_SERVICE_FREE_USED_UP", MLSpeechRealTimeTranscriptionConstants.ERR_SERVICE_FREE_USED_UP);
            put("LAN_EN_US", MLSpeechRealTimeTranscriptionConstants.LAN_EN_US);
            put("LAN_FR_FR", MLSpeechRealTimeTranscriptionConstants.LAN_FR_FR);
            put("LAN_ZH_CN", MLSpeechRealTimeTranscriptionConstants.LAN_ZH_CN);
            put("LAN_ES_ES", MLSpeechRealTimeTranscriptionConstants.LAN_ES_ES);
            put("LAN_EN_IN", MLSpeechRealTimeTranscriptionConstants.LAN_EN_IN);
            put("LAN_DE_DE", MLSpeechRealTimeTranscriptionConstants.LAN_DE_DE);
            put("STATE_LISTENING", MLSpeechRealTimeTranscriptionConstants.STATE_LISTENING);
            put("STATE_NO_NETWORK", MLSpeechRealTimeTranscriptionConstants.STATE_NO_NETWORK);
            put("STATE_NO_UNDERSTAND", MLSpeechRealTimeTranscriptionConstants.STATE_NO_UNDERSTAND);
            put("STATE_SERVICE_RECONNECTED", MLSpeechRealTimeTranscriptionConstants.STATE_SERVICE_RECONNECTED);
            put("STATE_SERVICE_RECONNECTING", MLSpeechRealTimeTranscriptionConstants.STATE_SERVICE_RECONNECTING);
            put("SPEECH_RTT_ON_RECOGNIZING_RESULTS", SPEECH_RTT_ON_RECOGNIZING_RESULTS);
            put("SPEECH_RTT_ON_ERROR", SPEECH_RTT_ON_ERROR);
            put("SPEECH_RTT_ON_LISTENING", SPEECH_RTT_ON_LISTENING);
            put("SPEECH_RTT_ON_STARTING_OF_SPEECH", SPEECH_RTT_ON_STARTING_OF_SPEECH);
            put("SPEECH_RTT_ON_VOICE_DATA_RECEIVED", SPEECH_RTT_ON_VOICE_DATA_RECEIVED);
            put("SPEECH_RTT_ON_STATE", SPEECH_RTT_ON_STATE);
            put("SCENES_SHOPPING", MLSpeechRealTimeTranscriptionConstants.SCENES_SHOPPING);
        }
    });

    // sound detect constants HMSSoundDetect
    public static final Map<String, Object> SOUND_DETECT_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("SOUND_DETECT_ERROR_AUDIO", MLSoundDetectConstants.SOUND_DETECT_ERROR_AUDIO);
            put("SOUND_DETECT_ERROR_FATAL_ERROR", MLSoundDetectConstants.SOUND_DETECT_ERROR_FATAL_ERROR);
            put("SOUND_DETECT_ERROR_NO_MEM", MLSoundDetectConstants.SOUND_DETECT_ERROR_NO_MEM);
            put("SOUND_DETECT_ERROR_INTERNAL", MLSoundDetectConstants.SOUND_DETECT_ERROR_INTERNAL);
            put("SOUND_EVENT_TYPE_ALARM", MLSoundDetectConstants.SOUND_EVENT_TYPE_ALARM);
            put("SOUND_EVENT_TYPE_BABY_CRY", MLSoundDetectConstants.SOUND_EVENT_TYPE_BABY_CRY);
            put("SOUND_EVENT_TYPE_BARK", MLSoundDetectConstants.SOUND_EVENT_TYPE_BARK);
            put("SOUND_EVENT_TYPE_CAR_ALARM", MLSoundDetectConstants.SOUND_EVENT_TYPE_CAR_ALARM);
            put("SOUND_EVENT_TYPE_DOOR_BELL", MLSoundDetectConstants.SOUND_EVENT_TYPE_DOOR_BELL);
            put("SOUND_EVENT_TYPE_KNOCK", MLSoundDetectConstants.SOUND_EVENT_TYPE_KNOCK);
            put("SOUND_EVENT_TYPE_LAUGHTER", MLSoundDetectConstants.SOUND_EVENT_TYPE_LAUGHTER);
            put("SOUND_EVENT_TYPE_MEOW", MLSoundDetectConstants.SOUND_EVENT_TYPE_MEOW);
            put("SOUND_EVENT_TYPE_SCREAMING", MLSoundDetectConstants.SOUND_EVENT_TYPE_SCREAMING);
            put("SOUND_EVENT_TYPE_SNEEZE", MLSoundDetectConstants.SOUND_EVENT_TYPE_SNEEZE);
            put("SOUND_EVENT_TYPE_SNORING", MLSoundDetectConstants.SOUND_EVENT_TYPE_SNORING);
            put("SOUND_EVENT_TYPE_STEAM_WHISTLE", MLSoundDetectConstants.SOUND_EVENT_TYPE_STEAM_WHISTLE);
            put("SOUND_EVENT_TYPE_WATER", MLSoundDetectConstants.SOUND_EVENT_TYPE_WATER);
            put("SOUND_DETECT_ON_FAILURE", SOUND_DETECT_ON_FAILURE);
            put("SOUND_DETECT_ON_SUCCESS", SOUND_DETECT_ON_SUCCESS);
        }
    });

    // object recognition constants HMSObjectRecognition
    public static final Map<String, Object> OBJECT_RECOGNITION_CONSTANTS = ImmutableMap.copyOf(
        new HashMap<String, Object>() {
            {
                put("TYPE_PICTURE", MLObjectAnalyzerSetting.TYPE_PICTURE);
                put("TYPE_VIDEO", MLObjectAnalyzerSetting.TYPE_VIDEO);
                put("TYPE_OTHER", MLObject.TYPE_OTHER);
                put("TYPE_FACE", MLObject.TYPE_FACE);
                put("TYPE_FOOD", MLObject.TYPE_FOOD);
                put("TYPE_FURNITURE", MLObject.TYPE_FURNITURE);
                put("TYPE_GOODS", MLObject.TYPE_GOODS);
                put("TYPE_PLACE", MLObject.TYPE_PLACE);
                put("TYPE_PLANT", MLObject.TYPE_PLANT);
            }
        });

    // landmark recognition constants HMSLandmarkRecognition
    public static final Map<String, Object> LANDMARK_RECOGNITION_CONSTANTS = ImmutableMap.copyOf(
        new HashMap<String, Object>() {
            {
                put("NEWEST_PATTERN", MLRemoteLandmarkAnalyzerSetting.NEWEST_PATTERN);
                put("STEADY_PATTERN", MLRemoteLandmarkAnalyzerSetting.STEADY_PATTERN);
            }
        });

    // image segmentation constants HMSImageSegmentation
    public static final Map<String, Object> IMSEG_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("TYPE_BACKGROUND", MLImageSegmentationClassification.TYPE_BACKGOURND);
            put("TYPE_HUMAN", MLImageSegmentationClassification.TYPE_HUMAN);
            put("TYPE_SKY", MLImageSegmentationClassification.TYPE_SKY);
            put("TYPE_GRASS", MLImageSegmentationClassification.TYPE_GRASS);
            put("TYPE_FOOD", MLImageSegmentationClassification.TYPE_FOOD);
            put("TYPE_CAT", MLImageSegmentationClassification.TYPE_CAT);
            put("TYPE_BUILD", MLImageSegmentationClassification.TYPE_BUILD);
            put("TYPE_FLOWER", MLImageSegmentationClassification.TYPE_FLOWER);
            put("TYPE_WATER", MLImageSegmentationClassification.TYPE_WATER);
            put("TYPE_SAND", MLImageSegmentationClassification.TYPE_SAND);
            put("TYPE_MOUNTAIN", MLImageSegmentationClassification.TYPE_MOUNTAIN);
            put("ALL", MLImageSegmentationScene.ALL);
            put("FOREGROUND_ONLY", MLImageSegmentationScene.FOREGROUND_ONLY);
            put("GRAYSCALE_ONLY", MLImageSegmentationScene.GRAYSCALE_ONLY);
            put("MASK_ONLY", MLImageSegmentationScene.MASK_ONLY);
            put("BODY_SEG", MLImageSegmentationSetting.BODY_SEG);
            put("IMAGE_SEG", MLImageSegmentationSetting.IMAGE_SEG);
            put("HAIR_SEG", MLImageSegmentationSetting.HAIR_SEG);
        }
    });

    // product visual search constants HMSProductVisionSearch
    public static final Map<String, Object> PRODUCT_VISION_CONSTANTS = ImmutableMap.copyOf(
        new HashMap<String, Object>() {
            {
                put("REGION_DR_CHINA", MLRemoteProductVisionSearchAnalyzerSetting.REGION_DR_CHINA);
                put("REGION_DR_SIANGAPORE", MLRemoteProductVisionSearchAnalyzerSetting.REGION_DR_SINGAPORE);
                put("REGION_DR_GERMAN", MLRemoteProductVisionSearchAnalyzerSetting.REGION_DR_GERMAN);
                put("REGION_DR_RUSSIA", MLRemoteProductVisionSearchAnalyzerSetting.REGION_DR_RUSSIA);
                put("PRODUCT_ON_RESULT", PRODUCT_ON_RESULT);
            }
        });

    // image super resolution constants HMSImageSuperResolution
    public static final Map<String, Object> IMAGE_RESOLUTION_CONSTANTS = ImmutableMap.copyOf(
        new HashMap<String, Object>() {
            {
                put("ISR_SCALE_1X", MLImageSuperResolutionAnalyzerSetting.ISR_SCALE_1X);
                put("ISR_SCALE_3X", MLImageSuperResolutionAnalyzerSetting.ISR_SCALE_3X);
            }
        });

    // document skew correction constants HMSDocumentSkewCorrection
    public static final Map<String, Object> DSC_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("CORRECTION_FAILED", MLDocumentSkewCorrectionConstant.CORRECTION_FAILED);
            put("DETECT_FAILED", MLDocumentSkewCorrectionConstant.DETECT_FAILED);
            put("IMAGE_DATA_ERROR", MLDocumentSkewCorrectionConstant.IMAGE_DATA_ERROR);
            put("SUCCESS", MLDocumentSkewCorrectionConstant.SUCCESS);
        }
    });

    // face recognition constants HMSFaceRecognition
    public static final Map<String, Object> FACE_RECOGNITION_CONSTANTS = ImmutableMap.copyOf(
        new HashMap<String, Object>() {
            {
                put("MODE_TRACING_FAST", MLFaceAnalyzerSetting.MODE_TRACING_FAST);
                put("MODE_TRACING_ROBUST", MLFaceAnalyzerSetting.MODE_TRACING_ROBUST);
                put("TYPE_FEATURES", MLFaceAnalyzerSetting.TYPE_FEATURES);
                put("TYPE_FEATURE_AGE", MLFaceAnalyzerSetting.TYPE_FEATURE_AGE);
                put("TYPE_FEATURE_BEARD", MLFaceAnalyzerSetting.TYPE_FEATURE_BEARD);
                put("TYPE_FEATURE_EMOTION", MLFaceAnalyzerSetting.TYPE_FEATURE_EMOTION);
                put("TYPE_FEATURE_EYEGLASS", MLFaceAnalyzerSetting.TYPE_FEATURE_EYEGLASS);
                put("TYPE_FEATURE_GENDAR", MLFaceAnalyzerSetting.TYPE_FEATURE_GENDAR);
                put("TYPE_FEATURE_HAT", MLFaceAnalyzerSetting.TYPE_FEATURE_HAT);
                put("TYPE_FEATURE_OPEN_CLOSE_EYE", MLFaceAnalyzerSetting.TYPE_FEATURE_OPENCLOSEEYE);
                put("TYPE_KEYPOINTS", MLFaceAnalyzerSetting.TYPE_KEYPOINTS);
                put("TYPE_PRECISION", MLFaceAnalyzerSetting.TYPE_PRECISION);
                put("TYPE_SHAPES", MLFaceAnalyzerSetting.TYPE_SHAPES);
                put("TYPE_SPEED", MLFaceAnalyzerSetting.TYPE_SPEED);
                put("TYPE_UNSUPPORT_FEATURES", MLFaceAnalyzerSetting.TYPE_UNSUPPORT_FEATURES);
                put("TYPE_UNSUPPORT_KEYPOINTS", MLFaceAnalyzerSetting.TYPE_UNSUPPORT_KEYPOINTS);
                put("TYPE_UNSUPPORT_SHAPES", MLFaceAnalyzerSetting.TYPE_UNSUPPORT_SHAPES);
                put("TYPE_BOTTOM_OF_MOUTH", MLFaceKeyPoint.TYPE_BOTTOM_OF_MOUTH);
                put("TYPE_LEFT_CHEEK", MLFaceKeyPoint.TYPE_LEFT_CHEEK);
                put("TYPE_LEFT_EAR", MLFaceKeyPoint.TYPE_LEFT_EAR);
                put("TYPE_LEFT_EYE", MLFaceKeyPoint.TYPE_LEFT_EYE);
                put("TYPE_LEFT_SIDE_OF_MOUTH", MLFaceKeyPoint.TYPE_LEFT_SIDE_OF_MOUTH);
                put("TYPE_RIGHT_CHEEK", MLFaceKeyPoint.TYPE_RIGHT_CHEEK);
                put("TYPE_RIGHT_EAR", MLFaceKeyPoint.TYPE_RIGHT_EAR);
                put("TYPE_RIGHT_EYE", MLFaceKeyPoint.TYPE_RIGHT_EYE);
                put("TYPE_RIGHT_SIDE_OF_MOUTH", MLFaceKeyPoint.TYPE_RIGHT_SIDE_OF_MOUTH);
                put("TYPE_TIP_OF_LEFT_EAR", MLFaceKeyPoint.TYPE_TIP_OF_LEFT_EAR);
                put("TYPE_TIP_OF_NOSE", MLFaceKeyPoint.TYPE_TIP_OF_NOSE);
                put("TYPE_TIP_OF_RIGHT_EAR", MLFaceKeyPoint.TYPE_TIP_OF_RIGHT_EAR);
                put("TYPE_ALL", MLFaceShape.TYPE_ALL);
                put("TYPE_BOTTOM_OF_LEFT_EYEBROW", MLFaceShape.TYPE_BOTTOM_OF_LEFT_EYEBROW);
                put("TYPE_BOTTOM_OF_LOWER_LIP", MLFaceShape.TYPE_BOTTOM_OF_LOWER_LIP);
                put("TYPE_BOTTOM_OF_NOSE", MLFaceShape.TYPE_BOTTOM_OF_NOSE);
                put("TYPE_BOTTOM_OF_RIGHT_EYEBROW", MLFaceShape.TYPE_BOTTOM_OF_RIGHT_EYEBROW);
                put("TYPE_BOTTOM_OF_UPPER_LIP", MLFaceShape.TYPE_BOTTOM_OF_UPPER_LIP);
                put("TYPE_BRIDGE_OF_NOSE", MLFaceShape.TYPE_BRIDGE_OF_NOSE);
                put("TYPE_FACE", MLFaceShape.TYPE_FACE);
                put("TYPE_LEFT_EYE_SHAPE", MLFaceShape.TYPE_LEFT_EYE);
                put("TYPE_RIGHT_EYE_SHAPE", MLFaceShape.TYPE_RIGHT_EYE);
                put("TYPE_TOP_OF_LEFT_EYEBROW", MLFaceShape.TYPE_TOP_OF_LEFT_EYEBROW);
                put("TYPE_TOP_OF_LOWER_LIP", MLFaceShape.TYPE_TOP_OF_LOWER_LIP);
                put("TYPE_TOP_OF_RIGHT_EYEBROW", MLFaceShape.TYPE_TOP_OF_RIGHT_EYEBROW);
                put("TYPE_TOP_OF_UPPER_LIP", MLFaceShape.TYPE_TOP_OF_UPPER_LIP);
            }
        });

    // skeleton recognition constants HMSSkeleton
    public static final Map<String, Object> SKELETON_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("TYPE_HEAD_TOP", MLJoint.TYPE_HEAD_TOP);
            put("TYPE_LEFT_ANKLE", MLJoint.TYPE_LEFT_ANKLE);
            put("TYPE_LEFT_ELBOW", MLJoint.TYPE_LEFT_ELBOW);
            put("TYPE_LEFT_HIP", MLJoint.TYPE_LEFT_HIP);
            put("TYPE_LEFT_KNEE", MLJoint.TYPE_LEFT_KNEE);
            put("TYPE_LEFT_SHOULDER", MLJoint.TYPE_LEFT_SHOULDER);
            put("TYPE_LEFT_WRIST", MLJoint.TYPE_LEFT_WRIST);
            put("TYPE_NECK", MLJoint.TYPE_NECK);
            put("TYPE_RIGHT_ANKLE", MLJoint.TYPE_RIGHT_ANKLE);
            put("TYPE_RIGHT_ELBOW", MLJoint.TYPE_RIGHT_ELBOW);
            put("TYPE_RIGHT_HIP", MLJoint.TYPE_RIGHT_HIP);
            put("TYPE_RIGHT_KNEE", MLJoint.TYPE_RIGHT_KNEE);
            put("TYPE_RIGHT_SHOULDER", MLJoint.TYPE_RIGHT_SHOULDER);
            put("TYPE_RIGHT_WRIST", MLJoint.TYPE_RIGHT_WRIST);
            put("TYPE_NORMAL", MLSkeletonAnalyzerSetting.TYPE_NORMAL);
            put("TYPE_YOGA", MLSkeletonAnalyzerSetting.TYPE_YOGA);
        }
    });

    // liveness constants HMSLiveness
    public static final Map<String, Object> LIVENESS_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("CAMERA_NO_PERMISSION", MLLivenessCaptureError.CAMERA_NO_PERMISSION);
            put("CAMERA_START_FAILED", MLLivenessCaptureError.CAMERA_START_FAILED);
            put("USER_CANCEL", MLLivenessCaptureError.USER_CANCEL);
            put("DETECT_FACE_TIME_OUT", MLLivenessCaptureError.DETECT_FACE_TIME_OUT);
            put("DETECT_MASK", MLLivenessCaptureConfig.DETECT_MASK);
        }
    });

    // hand keypoint constants HMSHandKeypoint
    public static final Map<String, Object> HANDKEYPOINT_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("TYPE_FOREFINGER_FIRST", MLHandKeypoint.TYPE_FOREFINGER_FIRST);
            put("TYPE_FOREFINGER_FOURTH", MLHandKeypoint.TYPE_FOREFINGER_FOURTH);
            put("TYPE_FOREFINGER_SECOND", MLHandKeypoint.TYPE_FOREFINGER_SECOND);
            put("TYPE_FOREFINGER_THIRD", MLHandKeypoint.TYPE_FOREFINGER_THIRD);
            put("TYPE_LITTLE_FINGER_FIRST", MLHandKeypoint.TYPE_LITTLE_FINGER_FIRST);
            put("TYPE_LITTLE_FINGER_FOURTH", MLHandKeypoint.TYPE_LITTLE_FINGER_FOURTH);
            put("TYPE_LITTLE_FINGER_SECOND", MLHandKeypoint.TYPE_LITTLE_FINGER_SECOND);
            put("TYPE_LITTLE_FINGER_THIRD", MLHandKeypoint.TYPE_LITTLE_FINGER_THIRD);
            put("TYPE_MIDDLE_FINGER_FIRST", MLHandKeypoint.TYPE_MIDDLE_FINGER_FIRST);
            put("TYPE_MIDDLE_FINGER_FOURTH", MLHandKeypoint.TYPE_MIDDLE_FINGER_FOURTH);
            put("TYPE_MIDDLE_FINGER_SECOND", MLHandKeypoint.TYPE_MIDDLE_FINGER_SECOND);
            put("TYPE_MIDDLE_FINGER_THIRD", MLHandKeypoint.TYPE_MIDDLE_FINGER_THIRD);
            put("TYPE_RING_FINGER_FIRST", MLHandKeypoint.TYPE_RING_FINGER_FIRST);
            put("TYPE_RING_FINGER_FOURTH", MLHandKeypoint.TYPE_RING_FINGER_FOURTH);
            put("TYPE_RING_FINGER_SECOND", MLHandKeypoint.TYPE_RING_FINGER_SECOND);
            put("TYPE_RING_FINGER_THIRD", MLHandKeypoint.TYPE_RING_FINGER_THIRD);
            put("TYPE_THUMB_FIRST", MLHandKeypoint.TYPE_THUMB_FIRST);
            put("TYPE_THUMB_FOURTH", MLHandKeypoint.TYPE_THUMB_FOURTH);
            put("TYPE_THUMB_SECOND", MLHandKeypoint.TYPE_THUMB_SECOND);
            put("TYPE_THUMB_THIRD", MLHandKeypoint.TYPE_THUMB_THIRD);
            put("TYPE_WRIST", MLHandKeypoint.TYPE_WRIST);
            put("TYPE_ALL", MLHandKeypointAnalyzerSetting.TYPE_ALL);
            put("TYPE_KEYPOINT_ONLY", MLHandKeypointAnalyzerSetting.TYPE_KEYPOINT_ONLY);
            put("TYPE_RECT_ONLY", MLHandKeypointAnalyzerSetting.TYPE_RECT_ONLY);
            put("MAX_HANDS_NUM", MLHandKeypointAnalyzerSetting.MAX_HANDS_NUM);

        }
    });

    // gesture constants HMSGesture
    public static final Map<String, Object> GESTURE_CONSTANTS = ImmutableMap.copyOf(new HashMap<String, Object>() {
        {
            put("ONE", MLGesture.ONE);
            put("SECOND", MLGesture.SECOND);
            put("THREE", MLGesture.THREE);
            put("FOUR", MLGesture.FOUR);
            put("FIVE", MLGesture.FIVE);
            put("SIX", MLGesture.SIX);
            put("SEVEN", MLGesture.SEVEN);
            put("EIGHT", MLGesture.EIGHT);
            put("NINE", MLGesture.NINE);
            put("DISS", MLGesture.DISS);
            put("FIST", MLGesture.FIST);
            put("GOOD", MLGesture.GOOD);
            put("HEART", MLGesture.HEART);
            put("OK", MLGesture.OK);
            put("UNKNOWN", MLGesture.UNKNOWN);
            put("BOW", MLGesture.BOW);
            put("DOUBLE_UP", MLGesture.DOUBLE_UP);
            put("FUCK", MLGesture.FUCK);
            put("HEART_A", MLGesture.HEART_A);
            put("HEART_B", MLGesture.HEART_B);
            put("HEART_C", MLGesture.HEART_C);
            put("PRAY", MLGesture.PRAY);
            put("ROCK", MLGesture.ROCK);
            put("THANK", MLGesture.THANK);
            put("UP", MLGesture.UP);
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

    // model download constants
    public static final Map<String, Object> MODEL_DOWNLOAD_CONSTANTS = ImmutableMap.copyOf(
        new HashMap<String, Object>() {
            {
                put("REGION_DR_AFILA", MLModelDownloadStrategy.REGION_DR_AFILA);
                put("REGION_DR_CHINA", MLModelDownloadStrategy.REGION_DR_CHINA);
                put("REGION_DR_EUROPE", MLModelDownloadStrategy.REGION_DR_EUROPE);
                put("REGION_DR_RUSSIA", MLModelDownloadStrategy.REGION_DR_RUSSIA);
                put("DOWNLOAD_ON_PROCESS", DOWNLOAD_ON_PROCESS);
                put("MODEL_TTS_TAG", MODEL_TTS_TAG);
                put("MODEL_TRANSLATE_TAG", MODEL_TRANSLATE_TAG);
                put("MODEL_CUSTOM_TAG", MODEL_CUSTOM_TAG);
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
            put("FACE_2D_TRANSACTOR_ON_DESTROY", FACE_2D_TRANSACTOR_ON_DESTROY);
            put("FACE_2D_TRANSACTOR_ON_RESULT", FACE_2D_TRANSACTOR_ON_RESULT);
            put("FACE_3D_TRANSACTOR_ON_DESTROY", FACE_3D_TRANSACTOR_ON_DESTROY);
            put("FACE_3D_TRANSACTOR_ON_RESULT", FACE_3D_TRANSACTOR_ON_RESULT);
            put("CLASSIFICATION_TRANSACTOR_ON_DESTROY", CLASSIFICATION_TRANSACTOR_ON_DESTROY);
            put("CLASSIFICATION_TRANSACTOR_ON_RESULT", CLASSIFICATION_TRANSACTOR_ON_RESULT);
            put("OBJECT_TRANSACTOR_ON_DESTROY", OBJECT_TRANSACTOR_ON_DESTROY);
            put("OBJECT_TRANSACTOR_ON_RESULT", OBJECT_TRANSACTOR_ON_RESULT);
            put("SCENE_TRANSACTOR_ON_DESTROY", SCENE_TRANSACTOR_ON_DESTROY);
            put("SCENE_TRANSACTOR_ON_RESULT", SCENE_TRANSACTOR_ON_RESULT);
            put("SKELETON_TRANSACTOR_ON_DESTROY", SKELETON_TRANSACTOR_ON_DESTROY);
            put("SKELETON_TRANSACTOR_ON_RESULT", SKELETON_TRANSACTOR_ON_RESULT);
            put("HAND_TRANSACTOR_ON_DESTROY", HAND_TRANSACTOR_ON_DESTROY);
            put("HAND_TRANSACTOR_ON_RESULT", HAND_TRANSACTOR_ON_RESULT);
            put("GESTURE_TRANSACTOR_ON_DESTROY", GESTURE_TRANSACTOR_ON_DESTROY);
            put("GESTURE_TRANSACTOR_ON_RESULT", GESTURE_TRANSACTOR_ON_RESULT);
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

    public static final Map<String, Object> FACE_VERIFICATION_CONSTANTS = ImmutableMap.copyOf(
        new HashMap<String, Object>() { });
}