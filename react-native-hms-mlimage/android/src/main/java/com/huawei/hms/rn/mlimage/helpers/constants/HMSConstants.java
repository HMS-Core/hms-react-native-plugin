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

package com.huawei.hms.rn.mlimage.helpers.constants;

import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.ANALYZER_NOT_AVAILABLE;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.ASR_RECOGNIZER_NULL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.CANCEL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.CURRENT_ACTIVITY_NULL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.CUSTOM_MODEL_EXECUTOR_SETTING_NULL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.CUSTOM_MODEL_INPUT_NULL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.CUSTOM_MODEL_SETTING_NULL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.DATA_SET_NOT_VALID;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.DENY;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.FRAME_NULL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.LENS_ENGINE_NULL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.LENS_HOLDER_NULL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.REMOTE_MODEL_NULL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.SOUND_DECT_NULL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.STRING_PARAM_NULL;
import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.TTS_ENGINE_NULL;

import android.hardware.Camera;

import com.huawei.hms.mlsdk.common.LensEngine;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionConstant;
import com.huawei.hms.mlsdk.imagesuperresolution.MLImageSuperResolutionAnalyzerSetting;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationClassification;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationScene;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmarkAnalyzerSetting;
import com.huawei.hms.mlsdk.objects.MLObject;
import com.huawei.hms.mlsdk.objects.MLObjectAnalyzerSetting;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.MLRemoteProductVisionSearchAnalyzerSetting;


import com.facebook.common.internal.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public final class HMSConstants {

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
}