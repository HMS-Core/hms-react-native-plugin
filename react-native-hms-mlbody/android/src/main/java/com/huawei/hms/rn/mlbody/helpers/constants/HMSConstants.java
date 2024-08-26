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

package com.huawei.hms.rn.mlbody.helpers.constants;

import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.ANALYZER_NOT_AVAILABLE;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.ASR_RECOGNIZER_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.CANCEL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.CURRENT_ACTIVITY_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.CUSTOM_MODEL_EXECUTOR_SETTING_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.CUSTOM_MODEL_INPUT_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.CUSTOM_MODEL_SETTING_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.DATA_SET_NOT_VALID;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.DENY;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.FRAME_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.LENS_ENGINE_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.LENS_HOLDER_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.REMOTE_MODEL_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.SOUND_DECT_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.STRING_PARAM_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.TTS_ENGINE_NULL;

import android.graphics.Color;
import android.hardware.Camera;

import com.huawei.hms.mlsdk.common.LensEngine;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.face.MLFaceKeyPoint;
import com.huawei.hms.mlsdk.face.MLFaceShape;
import com.huawei.hms.mlsdk.gesture.MLGesture;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypoint;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypointAnalyzerSetting;
import com.huawei.hms.mlsdk.interactiveliveness.MLInteractiveLivenessCaptureConfig;
import com.huawei.hms.mlsdk.interactiveliveness.MLInteractiveLivenessCaptureError;
import com.huawei.hms.mlsdk.interactiveliveness.action.MLInteractiveLivenessConfig;
import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCaptureConfig;
import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCaptureError;
import com.huawei.hms.mlsdk.skeleton.MLJoint;
import com.huawei.hms.mlsdk.skeleton.MLSkeletonAnalyzerSetting;
import com.huawei.hms.mlsdk.text.MLRemoteTextSetting;

import com.facebook.common.internal.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public final class HMSConstants {

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

    // liveness constants HMSLiveness
    public static final Map<String, Object> INTERACTIVE_LIVENESS_CONSTANTS = ImmutableMap.copyOf(
        new HashMap<String, Object>() {
            {
                put("DETECT_SUNGLASS", MLInteractiveLivenessCaptureConfig.DETECT_SUNGLASS);
                put("DETECT_MASK", MLInteractiveLivenessCaptureConfig.DETECT_MASK);
                put("CAMERA_NO_PERMISSION", MLInteractiveLivenessCaptureError.CAMERA_NO_PERMISSION);
                put("CAMERA_START_FAILED", MLInteractiveLivenessCaptureError.CAMERA_START_FAILED);
                put("DETECT_FACE_TIME_OUT", MLInteractiveLivenessCaptureError.DETECT_FACE_TIME_OUT);
                put("USER_CANCEL", MLInteractiveLivenessCaptureError.USER_CANCEL);
                put("USER_ DEFINED_ACTIONS_INVALID", MLInteractiveLivenessCaptureError.USER_DEFINED_ACTIONS_INVALID);
                put("SHAKE_DOWN_ACTION", MLInteractiveLivenessConfig.SHAKE_DOWN_ACTION);
                put("OPEN_MOUTH_ACTION", MLInteractiveLivenessConfig.OPEN_MOUTH_ACTION);
                put("EYE_CLOSE_ACTION", MLInteractiveLivenessConfig.EYE_CLOSE_ACTION);
                put("SHAKE_LEFT_ACTION", MLInteractiveLivenessConfig.SHAKE_LEFT_ACTION);
                put("SHAKE_RIGHT_ACTION", MLInteractiveLivenessConfig.SHAKE_RIGHT_ACTION);
                put("GAZED_ACTION", MLInteractiveLivenessConfig.GAZED_ACTION);
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
}