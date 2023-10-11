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

declare module "@hmscore/react-native-hms-mltext" {

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

    export const HMSTextRecognition = {
        asyncAnalyzeFrame(isRemote: boolean, isStop: boolean, frameConfiguration: Frame, analyzerConfiguration: OnCloudAnalyzerConfiguration | OnDeviceAnalyzerConfiguration): Promise<Object>;,
        analyzeFrame(isStop: boolean, frameConfiguration: Frame, analyzerConfiguration: OnDeviceAnalyzerConfiguration): Promise<Object>;
    }

    export declare enum HMSTextRecognition {
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

    interface OnCloudAnalyzerConfiguration {
        borderType: HMSTextRecognition,
        textDentisyScene: HMSTextRecognition,
        languageList: HMSTextRecognition[]
    }

    interface OnDeviceAnalyzerConfiguration {
        language: HMSTextRecognition,
        OCRMode: HMSTextRecognition
    }

    export const HMSDocumentRecognition = {
        asyncAnalyzeFrame(isStop: boolean, frameConfiguration: Frame, analyzerConfiguration: DocumentConfiguration): Promise<Object>;
    }

    export declare enum HMSDocumentRecognition {
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
        HINDI = "hi",
        OTHER = 5,
        NEW_LINE_CHARACTER = 8,
        SPACE = 6
    }

    interface DocumentConfiguration {
        isFingerPrintEnabled: boolean,
        borderType: HMSDocumentRecognition,
        languageList: HMSDocumentRecognition[]
    }

    export const HMSBankCardRecognition = {
        captureFrame(bcrCaptureConfiguration: BcrCaptureConfiguration): Promise<Object>;
    }

    export declare enum HMSBankCardRecognition {
        ORIENTATION_AUTO = 0,
        ORIENTATION_LANDSCAPE = 1,
        ORIENTATION_PORTRAIT = 2,
        ERROR_CODE_INIT_CAMERA_FAILED = 10101,
        RESULT_ALL = 2,
        RESULT_NUM_ONLY = 0,
        RESULT_SIMPLE = 1,
        STRICT_MODE = 1,
        SIMPLE_MODE = 0,
        BCR_IMAGE_SAVE = "bcrSuccessImage"
    }

    interface BcrCaptureConfiguration {
        orientation: HMSBankCardRecognition,
        resultType: HMSBankCardRecognition,
        recMode: HMSBankCardRecognition
    }

    export const HMSBankCardRecognitionCustomView = {
        startCustomizedView(bcrCustomCaptureConfiguration: BcrCustomCaptureConfiguration): Promise<Object>;,
        switchLight(): Promise<>;,
        getLightStatus(): Promise<boolean>;
    }

    interface BcrCustomCaptureConfiguration {
        
        isFlashAvailable?: boolean;

        isTitleAvailable?: boolean;

        title?: String;

        widthFactor?: number;

        heightFactor?: number;

        resultType: HMSBankCardRecognition,

        recMode: HMSBankCardRecognition
    }

    export const HMSGeneralCardRecognition = {
        capturePreview(language: string, uiConfig: UiConfig): Promise<Object>;,
        capturePhoto(language: string, uiConfig: UiConfig): Promise<Object>;,
        captureImage(language: string, imageUri: string): Promise<Object>;
    }

    export declare enum HMSGeneralCardRecognition {
        ORIENTATION_AUTO = 0,
        ORIENTATION_LANDSCAPE = 1,
        ORIENTATION_PORTRAIT = 2,
        BLACK = -16777216,
        BLUE = -16776961,
        CYAN = -16711681,
        DKGRAY = -12303292,
        GRAY = -7829368,
        GREEN = -16711936,
        LTGRAY = -3355444,
        MAGENTA = -65281,
        RED = -65536,
        TRANSPARENT = 0,
        WHITE = -1,
        YELLOW = -256,
        GCR_IMAGE_SAVE = "gcrOnResult"
    }

    interface UiConfig {
        orientation: HMSGeneralCardRecognition,
        tipTextColor: HMSGeneralCardRecognition,
        scanBoxCornerColor: HMSGeneralCardRecognition,
        tipText: string
    }

    export const HMSFormRecognition = {
        asyncAnalyzeFrame(isStop: boolean, frameConfiguration: Frame): Promise<Object>;,
        analyzeFrame(isStop: boolean, frameConfiguration: Frame): Promise<Object>;
    }
    
    export const HMSIDCardRecognition = {
        captureImage(imageUri: string, isFront: boolean): Promise<object>;,
    
        captureCamera(save: boolean, isFront: boolean): Promise<object>;,

        analyzerImageOnDevice(isStop: boolean, imageUri: string, isFront: boolean): Promise<object>;,

        asyncAnalyzerImageOnDevice(isStop: boolean, imageUri: string, isFront: boolean): Promise<object>;,
    };

    export declare enum HMSIDCardRecognition {
        IDCARD_IMAGE_SAVE = "idCardOnResult",
    }

    export const HMSVietnamCardRecognition = {
        captureImage(imageUri: string): Promise<object>;,
    
        captureCamera(save: boolean): Promise<object>;,

        analyzerImageOnDevice(isStop: boolean, imageUri: string): Promise<object>;,

        asyncAnalyzerImageOnDevice(isStop: boolean, imageUri: string): Promise<object>;,
    };

    export declare enum HMSVietnamCardRecognition {
        ICRVN_IMAGE_SAVE = "vnCardOnResult",
    }

    export const HMSLensEngine = {
        createLensEngine(analyzerTag: number, analyzerConfig: TextAnalyzerConfiguration, lensConfig: LensConfiguration): Promise<object>;,
    
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
    
    interface LensConfiguration {
        width: number;
        height: number;
        lensType: number;
        fps: number;
        automaticFocus: boolean;
        flashMode: string;
        focusMode: string;
    } 
    
    export const HMSTextEmbedding = {
        asyncAnalyzeFrame(sentence: string, language: HMSTextEmbedding): Promise<object>;,
    
        analyzeSentencesSimilarity(sentenceFirst: string, sentenceSecond: string, language: HMSTextEmbedding): Promise<object>;,
    
        analyzeSimilarWords(word: string, similarNum: number, language: HMSTextEmbedding): Promise<object>;,
    
        analyzeWordVector(word: string, language: HMSTextEmbedding): Promise<object>;,
    
        analyzeWordsSimilarity(wordFirst: string, wordSecond: string, language: HMSTextEmbedding): Promise<object>;,
    
        getVocabularyVersion(language: HMSTextEmbedding): Promise<object>;,
    
        analyzeWordVectorBatch(words: String[], language: HMSTextEmbedding): Promise<object>;,
    };

    export declare enum HMSTextEmbedding {
        LANGUAGE_ZH = "zh",
        LANGUAGE_EN = "en",
        ERR_SERVICE_IS_UNAVAILABLE = 12199,
        ERR_NET_UNAVAILABLE = 12198,
        ERR_INNER = 12101,
        ERR_AUTH_FAILED = 12102,
        ERR_PARAM_ILLEGAL = 12103,
        ERR_ANALYZE_FAILED = 12104,
        ERR_AUTH_TOKEN_INVALIDE = 12105
    }
}