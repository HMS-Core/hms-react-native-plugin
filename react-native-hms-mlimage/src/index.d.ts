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

declare module "@hmscore/react-native-hms-mlimage" {

    export const HMSApplication = {
        enableLogger(): Promise<Object>;,
        disableLogger(): Promise<Object>;,
        setApiKey(apiKey: string): Promise<Object>;,
        getApiKey(): Promise<Object>;,
        setAccessToken(token: string): Promise<Object>;,
        getCountryCode(): Promise<Object>;,
        setUserRegion(userRegion: int): Promise<Object>;
    }

    export declare enum HMSApplication {
        UNKNOWN = -1,
        SUCCESS = 0,
        DISCARDED = 1,
        INNER = 2,
        INACTIVE = 3,
        NOT_SUPPORTED = 4,
        ILLEGAL_PARAMETER = 5,
        OVERDUE = 6,
        NO_FOUND = 7,
        DUPLICATE_FOUND = 8,
        NO_PERMISSION = 9,
        INSUFFICIENT_RESOURCE = 10,
        ANALYSIS_FAILURE = 11,
        INTERRUPTED = 12,
        EXCEED_RANGE = 13,
        DATA_MISSING = 14,
        AUTHENTICATION_REQUIRED = 15,
        TFLITE_NOT_COMPATIBLE = 16,
        INSUFFICIENT_SPACE = 17,
        HASH_MISS = 18,
        TOKEN_INVALID = 19,
        FRAME_NULL = 20,
        ANALYZER_NOT_AVAILABLE = 21,
        CURRENT_ACTIVITY_NULL = 22,
        CANCEL = 23,
        FAILURE = 24,
        DENY = 25,
        STRING_PARAM_NULL = 26,
        REMOTE_MODEL_NULL = 27,
        ASR_RECOGNIZER_NULL = 28,
        TTS_ENGINE_NULL = 30,
        SOUND_DECT_NULL = 31,
        CUSTOM_MODEL_SETTING_NULL = 32,
        CUSTOM_MODEL_INPUT_NULL = 33,
        CUSTOM_MODEL_EXECUTOR_SETTING_NULL = 34,
        DATA_SET_NOT_VALID = 35,
        LENS_ENGINE_NULL = 36,
        LENS_HOLDER_NULL = 37
    }

    export const HMSFrame = {
        getPreviewBitmap(frameConfiguration: Frame): Promise<Object>;,
        readBitmap(frameConfiguration: Frame): Promise<Object>;,
        rotate(quadrant: HMSFrame, fileUri: string): Promise<Object>;
    }

    export declare enum HMSFrame {
        SCREEN_FIRST_QUADRANT = 0,
        SCREEN_SECOND_QUADRANT = 1,
        SCREEN_THIRD_QUADRANT = 2,
        SCREEN_FOURTH_QUADRANT = 3,
        IMAGE_FORMAT_NV21 = 17,
        IMAGE_FORMAT_YV12 = 842094169
    }

    interface Frame {
        bitmap: string,
        bytes: Bytes,
        byteBuffer: ByteBuffer,
        filePath: string,
        creator: Creator
    }

    interface Bytes {
        frameProperty: FrameProperty,
        values: number[],
    }

    interface ByteBuffer {
        frameProperty: FrameProperty,
        buffer: string,
    }

    interface Creator {
        base64Bitmap: string,
        itemIdentity: number,
        quadrant: HMSFrame,
        timeStamp: number,
        framePropertyExt: FramePropertyExt,
        writeByteBufferData: ByteBufferData
    }

    interface FrameProperty {
        width: number,
        height: number,
        quadrant: HMSFrame,
        formatType: HMSFrame,
        itemIdentity: number,
        timeStamp: number,
    }

    interface FramePropertyExt {
        lensId: number,
        maxZoom: number,
        zoom: number,
        bottom: number,
        left: number,
        right: number,
        top: number,
    }

    interface ByteBufferData {
        width: number,
        height: number,
        data: string,
        formatType: HMSFrame,
    }
    
    export const HMSSceneDetection = {
        analyzeFrame(isStop: boolean, confidence: number, frameConfiguration: FrameConfiguration): Promise<object>;,
    
        asyncAnalyzeFrame(isStop: boolean, confidence: number, frameConfiguration: FrameConfiguration): Promise<object>;,
    };
    
    export const HMSTextImageSuperResolution = {
        analyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration): Promise<object>;,
    
        asyncAnalyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration): Promise<object>;,
    };
    
    export const HMSDocumentSkewCorrection = {
        analyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration): Promise<object>;,
    
        asyncDocumentSkewDetect(isStop: boolean, frameConfiguration: FrameConfiguration): Promise<object>;,
      
        asyncDocumentSkewCorrect(isStop: boolean, frameConfiguration: FrameConfiguration, points: Point[]): Promise<object>;,
    
        syncDocumentSkewCorrect(isStop: boolean, frameConfiguration: FrameConfiguration, points: Point[]): Promise<object>;,
    };
    
    interface Point {
        x: number;
        y: number;
    }
    
    export const HMSLandmarkRecognition = {
        asyncAnalyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration, landmarkAnalyzerConfiguration: LandmarkAnalyzerConfiguration): Promise<object>;,
    };
    
    interface LandmarkAnalyzerConfiguration {
        largestNumOfReturns: number;
        patternType: HMSLandmarkRecognition;
    }

    export declare enum HMSLandmarkRecognition {
        STEADY_PATTERN = 1,
        NEWEST_PATTERN = 2
    }
    
    export const HMSObjectRecognition = {
        analyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration, objectAnalyzerSettingConfiguration: ObjectAnalyzerSettingConfiguration): Promise<object>;,
    
        asyncAnalyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration, objectAnalyzerSettingConfiguration: ObjectAnalyzerSettingConfiguration): Promise<object>;,
    };
  
    export declare enum HMSObjectRecognition {
        TYPE_PICTURE = 0,
        TYPE_VIDEO = 1,
        TYPE_OTHER = 0,
        TYPE_GOODS = 1,
        TYPE_FOOD = 2,
        TYPE_FURNITURE = 3,
        TYPE_PLANT = 4,
        TYPE_PLACE = 5,
        TYPE_FACE = 6
    }

    interface ObjectAnalyzerSettingConfiguration {
        analyzerType: HMSObjectRecognition;
        allowMultiResults: boolean;
        allowClassification: boolean;
    }
    
    export const HMSImageSegmentation = {
        analyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration, analyzerConfiguration: ImageSegmentationAnalyzerConfiguration): Promise<object>;,
    
        asyncAnalyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration, analyzerConfiguration: ImageSegmentationAnalyzerConfiguration): Promise<object>;,
    };
    
    export declare enum HMSImageSegmentation {
        TYPE_BACKGOURND = 0,
        TYPE_HUMAN = 1,
        TYPE_SKY = 2,
        TYPE_GRASS = 3,
        TYPE_FOOD = 4,
        TYPE_CAT = 5,
        TYPE_BUILD = 6,
        TYPE_FLOWER = 7,
        TYPE_WATER = 8,
        TYPE_SAND = 9,
        TYPE_MOUNTAIN = 10,
        ALL = 0,
        MASK_ONLY = 1,
        FOREGROUND_ONLY = 2,
        GRAYSCALE_ONLY = 3,
        BODY_SEG = 0,
        IMAGE_SEG = 1,
        HAIR_SEG = 2
    }

    interface ImageSegmentationAnalyzerConfiguration {
        analyzerType: HMSImageSegmentation;
        scene: HMSImageSegmentation;
        exact: boolean;
    }
    
    export const HMSImageClassification = {
        analyzeFrame(isRemote: boolean, isStop: boolean, frameConfiguration: FrameConfiguration, analyzerSetting: ImageClassAnalyzerSettingsOnCloud|ImageClassAnalyzerSettingsOnDevice): Promise<object>;,
    
        asyncAnalyzeFrame(isRemote: boolean, isStop: boolean, frameConfiguration: FrameConfiguration, analyzerSetting: ImageClassAnalyzerSettingsOnCloud|ImageClassAnalyzerSettingsOnDevice): Promise<object>;,
    };
    
    interface ImageClassAnalyzerSettingsOnCloud {
        maxNumberOfReturns: number;
        minAcceptablePossibility: number;
    }
    
    interface ImageClassAnalyzerSettingsOnDevice {
        minAcceptablePossibility: number;
    }
    
    export const HMSImageSuperResolution = {
        asyncAnalyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration, scale: HMSImageSuperResolution): Promise<object>;,
    
        analyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration, scale: HMSImageSuperResolution): Promise<object>;,
    };

    export declare enum HMSImageSuperResolution {
        ISR_SCALE_1X = 1.0,
        ISR_SCALE_3X = 3.0
    }
    
    export const HMSProductVisionSearch = {
        asyncAnalyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration, analyzerSetting: ProductVisionSearchAnalyzerSettings): Promise<object>;,
    
        startProductVisionSearchCapturePlugin(pluginConfiguration: FrameConfiguration): Promise<object>;,
    };
    
    export declare enum HMSProductVisionSearch {
        REGION_DR_CHINA = 1002,
        REGION_DR_SINGAPORE = 1007,
        REGION_DR_GERMAN = 1006,
        REGION_DR_RUSSIA = 1005,
        PRODUCT_ON_RESULT = "productOnResult"
    }

    interface ProductVisionSearchAnalyzerSettings {
        maxResults: number;
        productSetId: string;
        region: HMSProductVisionSearch;
    }
    
    export const HMSLensEngine = {
        createLensEngine(analyzerTag: number, analyzerConfig: TextAnalyzerConfiguration | FaceAnalyzerConfiguration2D | FaceAnalyzerConfiguration3D | SkeletonAnalyzerConfiguration | ClassificationAnalyzerConfigurationRemote | ClassificationAnalyzerConfigurationLocal | ObjectAnalyzerSettingConfiguration | SceneAnalyzerConfiguration | HandKeyPointAnalyzerConfiguration, lensConfig: LensConfiguration): Promise<object>;,
    
        close(): Promise<object>;,
    
        doZoom(scale: number): Promise<object>;,
    
        getDisplayDimension(): Promise<object>;,
    
        getLensType(): Promise<object>;,
    
        photograph(): Promise<object>;,
    
        release(): Promise<object>;,
    
        run(): Promise<object>;,
    
        runWithView(): Promise<object>;,
    };

    export declare enum HMSLensEngine {
        FLASH_MODE_OFF = "off",
        FLASH_MODE_ON = "on",
        FLASH_MODE_AUTO = "auto",
        FOCUS_MODE_CONTINUOUS_PICTURE = "continuous-picture",
        FOCUS_MODE_CONTINUOUS_VIDEO = "continuous-video",
        BACK_LENS = 0,
        FRONT_LENS = 1,
        LENS_ON_PHOTO_TAKEN = "lensOnPhotoTaken",
        LENS_ON_CLICK_SHUTTER = "lensOnClickShutter",
        LENS_SURFACE_ON_CREATED = "lensSurfaceOnCreated",
        LENS_SURFACE_ON_CHANGED = "lensSurfaceOnChanged",
        LENS_SURFACE_ON_DESTROY = "lensSurfaceOnDestroyed",
        TEXT_TRANSACTOR_ON_DESTROY = "textTransactorOnDestroy",
        TEXT_TRANSACTOR_ON_RESULT = "textTransactorOnResult",
        FACE_2D_TRANSACTOR_ON_DESTROY = "face2dTransactorOnDestroy",
        FACE_2D_TRANSACTOR_ON_RESULT = "face2dTransactorOnResult",
        FACE_3D_TRANSACTOR_ON_DESTROY = "face3dTransactorOnDestroy",
        FACE_3D_TRANSACTOR_ON_RESULT = "face3dTransactorOnResult",
        CLASSIFICATION_TRANSACTOR_ON_DESTROY = "classificationTransactorOnDestroy",
        CLASSIFICATION_TRANSACTOR_ON_RESULT = "classificationTransactorOnResult",
        OBJECT_TRANSACTOR_ON_DESTROY = "objectTransactorOnDestroy",
        OBJECT_TRANSACTOR_ON_RESULT = "objectTransactorOnResult",
        SCENE_TRANSACTOR_ON_DESTROY = "sceneTransactorOnDestroy",
        SCENE_TRANSACTOR_ON_RESULT = "sceneTransactorOnResult",
        SKELETON_TRANSACTOR_ON_DESTROY = "skeletonTransactorOnDestroy",
        SKELETON_TRANSACTOR_ON_RESULT = "skeletonTransactorOnResult",
        HAND_TRANSACTOR_ON_DESTROY = "handTransactorOnDestroy",
        HAND_TRANSACTOR_ON_RESULT = "handTransactorOnResult",
        GESTURE_TRANSACTOR_ON_DESTROY = "gestureTransactorOnDestroy",
        GESTURE_TRANSACTOR_ON_RESULT = "gestureTransactorOnResult",
        LENS_TEXT_ANALYZER = 0,
        LENS_FACE_2D_ANALYZER = 1,
        LENS_FACE_3D_ANALYZER = 2,
        LENS_SKELETON_ANALYZER = 3,
        LENS_CLASSIFICATION_ANALYZER = 4,
        LENS_OBJECT_ANALYZER = 5,
        LENS_SCENE_ANALYZER = 6,
        LENS_HAND_ANALYZER = 7,
        LENS_GESTURE_ANALYZER = 8
    }
    
    interface TextAnalyzerConfiguration {
        language: string;
        OCRMode: HMSTextRecognition;
    }
    
    interface FaceAnalyzerConfiguration2D {
        featureType: HMSFaceRecognition;
        shapeType: HMSFaceRecognition;
        keyPointType: HMSFaceRecognition;
        performanceType: HMSFaceRecognition;
        tracingMode: HMSFaceRecognition;
        minFaceProportion: number;
        isPoseDisabled: boolean;
        isTracingAllowed: boolean;
        isMaxSizeFaceOnly: boolean;
    }
    
    interface FaceAnalyzerConfiguration3D {
        performanceType: HMSFaceRecognition;
        isTracingAllowed: boolean;
    }
    
    interface SkeletonAnalyzerConfiguration {
        analyzeType: HMSSkeletonDetection;
    }
    
    interface ClassificationAnalyzerConfigurationRemote {
        maxNumberOfReturns: number;
        minAcceptablePossibility: number;
    }
    
    interface ClassificationAnalyzerConfigurationLocal {
        minAcceptablePossibility: number;
    }
    
    interface SceneAnalyzerConfiguration {
        confidence: number;
    }
    
    interface HandKeyPointAnalyzerConfiguration {
        sceneType: HMSHandKeypointDetection;
        maxHandResults: HMSHandKeypointDetection;
    }
    
    interface LensConfiguration {
        width: number;
        height: number;
        lensType: number;
        fps: number;
        automaticFocus: boolean;
        flashMode: string;
        focusMode: string;
    } 

    export const HMSComposite = {
        createCompositeAnalyzer(configuration: CompositeTextAnalyzerConfiguration | CompositeFaceAnalyzerFaceAnalyzerConfiguration2D | CompositeFaceAnalyzerConfiguration3D | CompositeSkeletonAnalyzerConfiguration | CompositeClassificationAnalyzerConfigurationRemote | CompositeClassificationAnalyzerConfigurationLocal | CompositeObjectAnalyzerSettingConfiguration |  CompositeHandKeyPointAnalyzerConfiguration): Promise<object>;,
    
        isAvailable(): Promise<object>;,
    
        destroy(): Promise<object>;,
    
        analyzeFrame(frameConfiguration: FrameConfiguration): Promise<object>;,
    };

    export declare enum CompositeFaceAnalyzerConstants {
        MODE_TRACING_FAST = 1,
        MODE_TRACING_ROBUST = 2,
        TYPE_FEATURES = 1,
        TYPE_FEATURE_AGE = 256,
        TYPE_FEATURE_BEARD = 32,
        TYPE_FEATURE_EMOTION = 4,
        TYPE_FEATURE_EYEGLASS = 8,
        TYPE_FEATURE_GENDAR = 128,
        TYPE_FEATURE_HAT = 16,
        TYPE_FEATURE_OPENCLOSEEYE = 64,
        TYPE_KEYPOINTS = 1,
        TYPE_PRECISION = 1,
        TYPE_SHAPES = 2,
        TYPE_SPEED = 2,
        TYPE_UNSUPPORT_FEATURES = 2,
        TYPE_UNSUPPORT_KEYPOINTS = 0,
        TYPE_UNSUPPORT_SHAPES = 3,
        TYPE_BOTTOM_OF_MOUTH = 1,
        TYPE_LEFT_CHEEK = 2,
        TYPE_LEFT_EAR = 3,
        TYPE_LEFT_EYE = 5,
        TYPE_LEFT_SIDE_OF_MOUTH = 6,
        TYPE_RIGHT_CHEEK = 8,
        TYPE_RIGHT_EAR = 9,
        TYPE_RIGHT_EYE = 11,
        TYPE_RIGHT_SIDE_OF_MOUTH = 12,
        TYPE_TIP_OF_LEFT_EAR = 4,
        TYPE_TIP_OF_NOSE = 7,
        TYPE_TIP_OF_RIGHT_EAR = 10,
        TYPE_ALL = 0,
        TYPE_BOTTOM_OF_LEFT_EYEBROW = 4,
        TYPE_BOTTOM_OF_LOWER_LIP = 8,
        TYPE_BOTTOM_OF_NOSE = 12,
        TYPE_BOTTOM_OF_RIGHT_EYEBROW = 5,
        TYPE_BOTTOM_OF_UPPER_LIP = 10,
        TYPE_BRIDGE_OF_NOSE = 13,
        TYPE_FACE = 1,
        TYPE_LEFT_EYE = 2,
        TYPE_RIGHT_EYE = 3,
        TYPE_TOP_OF_LEFT_EYEBROW = 6,
        TYPE_TOP_OF_LOWER_LIP = 9,
        TYPE_TOP_OF_RIGHT_EYEBROW = 7,
        TYPE_TOP_OF_UPPER_LIP = 11
      }
      
      export declare enum CompositeTextAnalyzeConstants {
        OCR_DETECT_MODE = 1,
        OCR_TRACKING_MODE = 2,
        OCR_LOOSE_SCENE = 1,
        OCR_COMPACT_SCENE = 2,
        ARC = "ARC",
        NGON = "NGON",
        LATIN = "rm",
        ENGLISH = "en",
        CHINESE = "zh",
        JAPANESE = "ja",
        KOREAN = "ko",
        RUSSIAN = "ru",
        GERMAN = "de",
        FRENCH = "fr",
        ITALIAN = "it",
        PORTUGUESE = "pt",
        SPANISH = "es",
        POLISH = "pl",
        NORWEGIAN = "no",
        SWEDISH = "sv",
        DANISH = "da",
        TURKISH = "tr",
        FINNISH = "fi",
        THAI = "th",
        ARABIC = "ar",
        HINDI = "hi"
      }
      
      export declare enum CompositeSkeletonAnalyzerConstants {
        TYPE_RIGHT_SHOULDER = 101,
        TYPE_RIGHT_ELBOW = 102,
        TYPE_RIGHT_WRIST = 103,
        TYPE_LEFT_SHOULDER = 104,
        TYPE_LEFT_ELBOW = 105,
        TYPE_LEFT_WRIST = 106,
        TYPE_RIGHT_HIP = 107,
        TYPE_RIGHT_KNEE = 108,
        TYPE_RIGHT_ANKLE = 109,
        TYPE_LEFT_HIP = 110,
        TYPE_LEFT_KNEE = 111,
        TYPE_LEFT_ANKLE = 112,
        TYPE_HEAD_TOP = 113,
        TYPE_NECK = 114,
        TYPE_NORMAL = 0,
        TYPE_YOGA = 1,
      }
      
      export declare enum  CompositeHandKeyPointAnalyzerConstants {
        TYPE_WRIST = 0,
        TYPE_THUMB_FIRST = 1,
        TYPE_THUMB_SECOND = 2,
        TYPE_THUMB_THIRD = 3,
        TYPE_THUMB_FOURTH = 4,
        TYPE_FOREFINGER_FIRST = 5,
        TYPE_FOREFINGER_SECOND = 6,
        TYPE_FOREFINGER_THIRD = 7,
        TYPE_FOREFINGER_FOURTH = 8,
        TYPE_MIDDLE_FINGER_FIRST = 9,
        TYPE_MIDDLE_FINGER_SECOND = 10,
        TYPE_MIDDLE_FINGER_THIRD = 11,
        TYPE_MIDDLE_FINGER_FOURTH = 12,
        TYPE_RING_FINGER_FIRST = 13,
        TYPE_RING_FINGER_SECOND = 14,
        TYPE_RING_FINGER_THIRD = 15,
        TYPE_RING_FINGER_FOURTH = 16,
        TYPE_LITTLE_FINGER_FIRST = 17,
        TYPE_LITTLE_FINGER_SECOND = 18,
        TYPE_LITTLE_FINGER_THIRD = 19,
        TYPE_LITTLE_FINGER_FOURTH = 20,
        TYPE_ALL = 0,
        TYPE_KEYPOINT_ONLY = 1,
        TYPE_RECT_ONLY = 2,
        MAX_HANDS_NUM = 10
      }
      
      export declare enum CompositeObjectAnalyzerConstants {
        TYPE_PICTURE = 0,
        TYPE_VIDEO = 1,
        TYPE_OTHER = 0,
        TYPE_GOODS = 1,
        TYPE_FOOD = 2,
        TYPE_FURNITURE = 3,
        TYPE_PLANT = 4,
        TYPE_PLACE = 5,
        TYPE_FACE = 6
      }

    interface CompositeFaceAnalyzerFaceAnalyzerConfiguration2D {
        featureType: CompositeFaceAnalyzerConstants;
        shapeType: CompositeFaceAnalyzerConstants;
        keyPointType: CompositeFaceAnalyzerConstants;
        performanceType: CompositeFaceAnalyzerConstants;
        tracingMode: CompositeFaceAnalyzerConstants;
        minFaceProportion: number;
        isPoseDisabled: boolean;
        isTracingAllowed: boolean;
        isFace2D: true;
    }

    interface CompositeFaceAnalyzerConfiguration3D {
        performanceType: CompositeFaceAnalyzerConstants;
        isTracingAllowed: boolean;
        isFace2D: false;
    }

    interface CompositeClassificationAnalyzerConfigurationRemote {
        maxNumberOfReturns: number;
        minAcceptablePossibility: number;
        isRemote: true;
    }

    interface CompositeClassificationAnalyzerConfigurationLocal {
        minAcceptablePossibility: number;
        isRemote: false;
    }

    interface CompositeTextAnalyzerConfiguration {
        language: string;
        OCRMode: CompositeTextAnalyzeConstants;
    }

    interface CompositeSkeletonAnalyzerConfiguration {
        analyzeType: CompositeSkeletonAnalyzerConstants;
    }

    interface CompositeHandKeyPointAnalyzerConfiguration {
        sceneType: CompositeHandKeyPointAnalyzerConstants;
        maxHandResults: CompositeHandKeyPointAnalyzerConstants;
    }

    interface CompositeObjectAnalyzerSettingConfiguration {
        analyzerType: CompositeObjectAnalyzerConstants;
        allowMultiResults: boolean;
        allowClassification: boolean;
    }
}