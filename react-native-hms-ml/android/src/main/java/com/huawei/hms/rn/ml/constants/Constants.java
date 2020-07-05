/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.ml.constants;

import com.facebook.common.internal.ImmutableMap;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzerSetting;
import com.huawei.hms.mlsdk.common.LensEngine;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.face.MLFaceKeyPoint;
import com.huawei.hms.mlsdk.face.MLFaceShape;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationClassification;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationScene;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmarkAnalyzerSetting;
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetector;
import com.huawei.hms.mlsdk.objects.MLObject;
import com.huawei.hms.mlsdk.objects.MLObjectAnalyzerSetting;
import com.huawei.hms.mlsdk.text.MLLocalTextSetting;
import com.huawei.hms.mlsdk.text.MLRemoteTextSetting;
import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import com.huawei.hms.mlsdk.tts.MLTtsEngine;
import com.huawei.hms.mlsdk.tts.MLTtsError;
import com.huawei.hms.mlsdk.tts.MLTtsWarn;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    private static final Map<String, Object> BASE_LANGUAGES = new HashMap<String, Object>() {
        {
            put("CHINESE", "zh");
            put("ENGLISH", "ar");
            put("FRENCH", "ar");
            put("GERMAN", "de");
            put("ITALIAN", "it");
            put("JAPANESE", "ja");
            put("KOREAN", "ko");
            put("PORTUGUESE", "pt");
            put("RUSSIAN", "ru");
            put("SPANISH", "es");
        }
    };

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

    private static final Map<String, Object> BORDER_TYPE = new HashMap<String, Object>() {
        {
            put("ARC", MLRemoteTextSetting.ARC);
            put("NGON", MLRemoteTextSetting.NGON);
        }
    };

    private static final Map<String, Object> LOCAL_TEXT_SETTING_CONSTANTS = new HashMap<String, Object>() {
        {
            putAll(BASE_LANGUAGES);
            put("OCR_DETECT_MODE", MLLocalTextSetting.OCR_DETECT_MODE);
            put("OCR_TRACKING_MODE", MLLocalTextSetting.OCR_TRACKING_MODE);
        }
    };

    private static final Map<String, Object> REMOTE_TEXT_SETTING_CONSTANTS = new HashMap<String, Object>() {
        {
            putAll(BASE_LANGUAGES);
            putAll(EXTRA_LANGUAGES);
            putAll(BORDER_TYPE);
            put("OCR_COMPACT_SCENE", MLRemoteTextSetting.OCR_COMPACT_SCENE);
            put("OCR_LOOSE_SCENE", MLRemoteTextSetting.OCR_LOOSE_SCENE);
        }
    };

    private static final Map<String, Object> LENS_ENGINE_CONSTANTS = new HashMap<String, Object>() {
        {
            put("BACK_LENS", LensEngine.BACK_LENS);
            put("FRONT_LENS", LensEngine.FRONT_LENS);
        }
    };

    private static final Map<String, Object> IMAGE_SEGMENTATION_CONSTANTS = new HashMap<String, Object>() {
        {
            put("BODY_SEG", MLImageSegmentationSetting.BODY_SEG);
            put("IMAGE_SEG", MLImageSegmentationSetting.IMAGE_SEG);
            put("TYPE_BACKGOURND", MLImageSegmentationClassification.TYPE_BACKGOURND);
            put("TYPE_BUILD", MLImageSegmentationClassification.TYPE_BUILD);
            put("TYPE_CAT", MLImageSegmentationClassification.TYPE_CAT);
            put("TYPE_FLOWER", MLImageSegmentationClassification.TYPE_FLOWER);
            put("TYPE_FOOD", MLImageSegmentationClassification.TYPE_FOOD);
            put("TYPE_GRASS", MLImageSegmentationClassification.TYPE_GRASS);
            put("TYPE_HUMAN", MLImageSegmentationClassification.TYPE_HUMAN);
            put("TYPE_MOUNTAIN", MLImageSegmentationClassification.TYPE_MOUNTAIN);
            put("TYPE_SAND", MLImageSegmentationClassification.TYPE_SAND);
            put("TYPE_SKY", MLImageSegmentationClassification.TYPE_SKY);
            put("TYPE_WATER", MLImageSegmentationClassification.TYPE_WATER);
            put("ALL", MLImageSegmentationScene.ALL);
            put("FOREGROUND_ONLY", MLImageSegmentationScene.FOREGROUND_ONLY);
            put("GRAYSCALE_ONLY", MLImageSegmentationScene.GRAYSCALE_ONLY);
            put("MASK_ONLY", MLImageSegmentationScene.MASK_ONLY);
        }
    };

    private static final Map<String, Object> FRAME_CONSTANTS = new HashMap<String, Object>() {
        {
            put("SCREEN_FIRST_QUADRANT", MLFrame.SCREEN_FIRST_QUADRANT);
            put("SCREEN_SECOND_QUADRANT", MLFrame.SCREEN_SECOND_QUADRANT);
            put("SCREEN_THIRD_QUADRANT", MLFrame.SCREEN_THIRD_QUADRANT);
            put("SCREEN_FOURTH_QUADRANT", MLFrame.SCREEN_FOURTH_QUADRANT);
        }
    };

    private static final Map<String, Object> DOCUMENT_RECOGNITION_CONSTANTS = new HashMap<String, Object>() {
        {
            putAll(BASE_LANGUAGES);
            putAll(EXTRA_LANGUAGES);
            putAll(BORDER_TYPE);
        }
    };

    private static final Map<String, Object> ICR_CONSTANTS = new HashMap<String, Object>() {
        {
            put("BACK", MLIcrAnalyzerSetting.BACK);
            put("FRONT", MLIcrAnalyzerSetting.FRONT);
        }
    };

    private static final Map<String, Object> LANDMARK_RECOGNITION_CONSTANTS = new HashMap<String, Object>() {
        {
            put("NEWEST_PATTERN", MLRemoteLandmarkAnalyzerSetting.NEWEST_PATTERN);
            put("STEADY_PATTERN", MLRemoteLandmarkAnalyzerSetting.STEADY_PATTERN);
        }
    };

    private static final Map<String, Object> OBJECT_RECOGNITION_CONSTANTS = new HashMap<String, Object>() {
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
    };

    private static final Map<String, Object> TTS_CONSTANTS = new HashMap<String, Object>() {
        {
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
            put("TTS_EN_US", MLTtsConstants.TTS_EN_US);
            put("TTS_SPEAKER_FEMALE_EN", MLTtsConstants.TTS_SPEAKER_FEMALE_EN);
            put("TTS_SPEAKER_FEMALE_ZH", MLTtsConstants.TTS_SPEAKER_FEMALE_ZH);
            put("TTS_SPEAKER_MALE_EN", MLTtsConstants.TTS_SPEAKER_MALE_EN);
            put("TTS_SPEAKER_MALE_ZH", MLTtsConstants.TTS_SPEAKER_MALE_ZH);
            put("TTS_ZH_HANS", MLTtsConstants.TTS_ZH_HANS);
        }
    };

    private static final Map<String, Object> LANGUAGE_DETECTION_CONSTANTS = new HashMap<String, Object>() {
        {
            put("FIRST_BEST_DETECTION_LANGUAGE_TRUSTED_THRESHOLD", MLRemoteLangDetector
                    .FIRST_BEST_DETECTION_LANGUAGE_TRUSTED_THRESHOLD);
            put("PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD", MLRemoteLangDetector
                    .PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD);
            put("UNDETECTION_LANGUAGE_TRUSTED_THRESHOLD", MLRemoteLangDetector
                    .UNDETECTION_LANGUAGE_TRUSTED_THRESHOLD);
        }
    };

    private static final Map<String, Object> FACE_RECOGNITION_CONSTANTS = new HashMap<String, Object>() {
        {
            put("TYPE_FEATURES", MLFaceAnalyzerSetting.TYPE_FEATURES);
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
    };

    public static Map<String, Object> getLocalTextSettingConstants() {
        return ImmutableMap.copyOf(LOCAL_TEXT_SETTING_CONSTANTS);
    }

    public static Map<String, Object> getRemoteTextSettingConstants() {
        return ImmutableMap.copyOf(REMOTE_TEXT_SETTING_CONSTANTS);
    }

    public static Map<String, Object> getLensEngineConstants() {
        return ImmutableMap.copyOf(LENS_ENGINE_CONSTANTS);
    }

    public static Map<String, Object> getImageSegmentationConstants() {
        return ImmutableMap.copyOf(IMAGE_SEGMENTATION_CONSTANTS);
    }

    public static Map<String, Object> getFrameConstants() {
        return ImmutableMap.copyOf(FRAME_CONSTANTS);
    }

    public static Map<String, Object> getDocumentRecognitionConstants() {
        return ImmutableMap.copyOf(DOCUMENT_RECOGNITION_CONSTANTS);
    }

    public static Map<String, Object> getIcrConstants() {
        return ImmutableMap.copyOf(ICR_CONSTANTS);
    }

    public static Map<String, Object> getLandmarkRecognitionConstants() {
        return ImmutableMap.copyOf(LANDMARK_RECOGNITION_CONSTANTS);
    }

    public static Map<String, Object> getObjectRecognitionConstants() {
        return ImmutableMap.copyOf(OBJECT_RECOGNITION_CONSTANTS);
    }

    public static Map<String, Object> getTtsConstants() {
        return ImmutableMap.copyOf(TTS_CONSTANTS);
    }

    public static Map<String, Object> getLanguageDetectionConstants() {
        return ImmutableMap.copyOf(LANGUAGE_DETECTION_CONSTANTS);
    }

    public static Map<String, Object> getFaceRecognitionConstants() {
        return ImmutableMap.copyOf(FACE_RECOGNITION_CONSTANTS);
    }
}