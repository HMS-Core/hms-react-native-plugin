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

declare module "@hmscore/react-native-hms-ml" {

    export const HMSApplication = {
        enableLogger(): Promise<Object>;,
        disableLogger(): Promise<Object>;,
        setApiKey(apiKey: string): Promise<Object>;,
        getApiKey(): Promise<Object>;,
        setAccessToken(token: string): Promise<Object>;
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

    export const HMSAft = {
        init(): Promise<Object>;,
        close(): Promise<Object>;,
        destroyTask(taskId: string): Promise<Object>;,
        getLongAftResult(taskId: string): Promise<Object>;,
        pauseTask(taskId: string): Promise<Object>;,
        startTask(taskId: string): Promise<Object>;,
        setAftListener(): Promise<Object>;,
        shortRecognize(uri: string, remoteAftSetting: RemoteAftSetting): Promise<Object>;,
        longRecognize(uri: string, remoteAftSetting: RemoteAftSetting): Promise<Object>;,
        getLongAftLanguages(): Promise<Object>;,
        getShortAftLanguages(): Promise<Object>;
    }

    export declare enum HMSAft {
        LANGUAGE_ZH = "zh",
        LANGUAGE_EN_US = "en-US",
        ERR_UNKNOWN = 11199,
        ERR_TASK_NOT_EXISTED = 11110,
        ERR_TASK_ALREADY_INPROGRESS = 11114,
        ERR_RESULT_WHEN_UPLOADING = 11109,
        ERR_NO_ENOUGH_STORAGE = 11115,
        ERR_NETCONNECT_FAILED = 11108,
        ERR_LANGUAGE_CODE_NOTSUPPORTED = 11102,
        ERR_INTERNAL = 11198,
        ERR_ILLEGAL_PARAMETER = 11106,
        ERR_FILE_NOT_FOUND = 11105,
        ERR_ENGINE_BUSY = 11107,
        ERR_SERVICE_CREDIT = 11122,
        AFT_ON_EVENTERR_AUTHORIZE_FAILED = 11119,
        ERR_AUDIO_UPLOAD_FAILED = 11113,
        ERR_AUDIO_TRANSCRIPT_FAILED = 11111,
        ERR_AUDIO_LENGTH_OVERFLOW = 11104,
        ERR_AUDIO_FILE_NOTSUPPORTED = 11101,
        ERR_AUDIO_FILE_SIZE_OVERFLOW = 11103,
        ERR_AUDIO_INIT_FAILED = 11112,
        AFT_ON_ERROR = "aftOnError",
        AFT_ON_EVENT = "aftOnEvent",
        AFT_ON_INIT_COMPLETE = "aftOnInitComplete",
        AFT_ON_UPLOAD_PROGRESS = "aftOnUploadProgress",
        AFT_ON_RESULT =	"aftOnResult"
    }

    interface RemoteAftSetting {
        languageCode: string,
        enablePunctuation: boolean,
        enableWordTimeOffset: boolean,
        enableSentenceTimeOffset: boolean
    }

    export const HMSAsr = {
        destroy(): Promise<Object>;,
        createAsrRecognizer(): Promise<Object>;,
        startRecognizing(language: HMSAsr, feature: HMSAsr): Promise<Object>;,
        startRecognizingPlugin(language: HMSAsr, feature: HMSAsr): Promise<Object>;,
        getLanguages(): Promise<Object>;
    }

    export declare enum HMSAsr {
        LAN_ZH = "zh",
        LAN_ZH_CN = "zh-CN",
        LAN_EN_US = "en-US",
        LAN_FR_FR = "fr-R",
        LAN_ES_ES = "es-ES",
        LAN_EN_IN = "en-IN",
        LAN_DE_DE = "de-DE",
        LAN_RU_RU = "ru-RU",
        LAN_IT_IT = "it-IT",
        LAN_AR = "ar",
        LAN_TH_TH = "th-TH",
        LAN_FIL_PH = "fil-PH",
        LAN_MS_MY = "ms-MY",
        STATE_LISTENING = 1,
        STATE_NO_SOUND = 2,
        STATE_NO_SOUND_TIMES_EXCEED = 3,
        STATE_NO_UNDERSTAND = 6,
        STATE_NO_NETWORK = 7,
        STATE_WAITING = 9,
        ASR_ON_ERROR = "asrOnError",
        ASR_ON_RECOGNIZING_RESULTS = "asrOnRecognizingResults",
        ASR_ON_RESULTS = "asrOnResults",
        ASR_ON_START_LISTENING = "asrOnStartListening",
        ASR_ON_STARTING_SPEECH = "asrOnStartingOfSpeech",
        ASR_ON_STATE = "asrOnState",
        ASR_ON_VOICE_DATA_RECEIVED = "asrOnVoiceDataReceived",
        ERR_NO_NETWORK = 11202,
        ERR_SERVICE_UNAVAILABLE = 11203,
        ERR_NO_UNDERSTAND = 11204,
        ERR_INVALIDATE_TOKEN = 11219,
        FEATURE_ALL_IN_ONE = 12,
        FEATURE_WORD_FLUX = 11
    }

    export const HMSTranslate = {
        asyncTranslate(isRemote: boolean, isStop: boolean, text: string, translatorSetting: TranslatorSetting): Promise<Object>;,
        preparedModel(strategyConfiguration: StrategyConfiguration, translatorSetting: TranslatorSetting): Promise<Object>;,
        syncTranslate(isRemote: boolean, isStop: boolean, text: string, translatorSetting: TranslatorSetting): Promise<Object>;,
        syncGetAllLanguages(isRemote: boolean): Promise<Object>;,
        getAllLanguages(isRemote: boolean): Promise<Object>;
    }

    export declare enum HMSTranslate {
        TRANSLATE_DOWNLOAD_ON_PROCESS = "translateDownloadProcess",
        AFRIKAANS = "af",
        ARABIC = "ar",
        BULGARIAN = "bg",
        BURMESE = "my",
        CROATIAN = "hr",
        CZECH = "cs",
        CHINESE = "zh",
        TRADITIONAL_CHINESE = "zh-hk",
        DANISH = "da",
        DUTCH = "nl",
        ESTONIAN = "et",
        ENGLISH = "en",
        FINNISH = "fi",
        FRENCH = "fr",
        GERMAN = "de",
        GUJARATI = "gu",
        GREEK = "el",
        HUNGARIAN = "hu",
        HINDI = "hi",
        HEBREW = "he",
        IRISH = "is",
        ITALIAN = "it",
        INDONESIAN = "id",
        KHMER = "km",
        KOREAN = "ko",
        JAPANESE = "ja",
        LATIN = "rm",
        LATVIAN = "lv",
        MALAY = "ms",
        MARATHI = "mr",
        NORWEGIAN = "no",
        PUNJABI = "pa",
        POLISH = "pl",
        PORTUGUESE = "pt",
        PERSIAN = "fa",
        RUSSIAN = "ru",
        ROMANIAN = "ro",
        SERBIAN = "sr",
        SPANISH = "es",
        SLOVAK = "sk",
        SWEDISH = "sv",
        TAMIL = "ta",
        TURKISH = "tr",
        THAI = "th",
        TAGALOG = "tl",
        TELUGU = "te",
        VIETNAMESE = "vi"
    }

    interface TranslatorSetting {
        sourceLanguageCode: HMSTranslate,
        targetLanguageCode: HMSTranslate
    }

    interface StrategyConfiguration {
        needWifi: boolean,
        needCharging: boolean,
        deviceIdle: boolean,
        region: HMSModelDownload
    }

    export const HMSLanguageDetection = {
        probabilityDetect(isRemote: boolean, isStop: boolean, trustedThreshold, sourceText: string): Promise<Object>;,
        firstBestDetect(isRemote: boolean, isStop: boolean, trustedThreshold, sourceText: string): Promise<Object>;,
        syncProbabilityDetect(isRemote: boolean, isStop: boolean, trustedThreshold, sourceText: string): Promise<Object>;,
        syncFirstBestDetect(isRemote: boolean, isStop: boolean, trustedThreshold, sourceText: string): Promise<Object>;
    }

    export declare enum HMSLanguageDetection {
        FIRST_BEST_DETECTION_LANGUAGE_TRUSTED_THRESHOLD = 0.5,
        PROBABILITY_DETECTION_LANGUAGE_TRUSTED_THRESHOLD = 0.01,
        UNDETECTION_LANGUAGE_TRUSTED_THRESHOLD = "unknown"
    }

    export const HMSSpeechRtt = {
        startRecognizing(rttConfiguration: RttConfiguration): Promise<Object>;,
        setRealTimeTranscriptionListener(): Promise<Object>;,
        destroy(): Promise<Object>;
    }

    export declare enum HMSSpeechRtt {
        ERR_NO_NETWORK = 13202,
        ERR_SERVICE_UNAVAILABLE = 13203,
        ERR_INVALIDE_TOKEN = 13219,
        ERR_SERVICE_CREDIT = 13222,
        ERR_SERVICE_UNSUBSCRIBED = 13223,
        ERR_SERVICE_FREE_USED_UP = 13224,
        LAN_ZH_CN = "zh-CN",
        LAN_EN_US = "en-US",
        LAN_FR_FR = "fr-FR",
        LAN_ES_ES = "es-ES",
        LAN_EN_IN = "en-IN",
        LAN_DE_DE = "de-DE",
        STATE_LISTENING = 1,
        STATE_NO_UNDERSTAND = 6,
        STATE_NO_NETWORK = 7,
        STATE_SERVICE_RECONNECTING = 42,
        STATE_SERVICE_RECONNECTED = 43,
        SPEECH_RTT_ON_RECOGNIZING_RESULTS = "speechRttOnError",
        SPEECH_RTT_ON_ERROR = "speechRttOnError",
        SPEECH_RTT_ON_LISTENING = "speechRttOnListening",
        SPEECH_RTT_ON_STARTING_OF_SPEECH = "speechRttOnStartingOfSpeech",
        SPEECH_RTT_ON_VOICE_DATA_RECEIVED = "speechRttOnVoiceDataReceived",
        SPEECH_RTT_ON_STATE = "speechRttOnState",
        SCENES_SHOPPING = "shopping"
    }

    interface RttConfiguration {
        language: HMSSpeechRtt,
        enablePunctuation: boolean,
        enableSentenceTimeOffset: boolean,
        enableWordTimeOffset: boolean,
    }

    export const HMSGestureDetection = {
        analyzeFrame(isStop: boolean, frameConfiguration: Frame): Promise<Object>;,
        asyncAnalyzeFrame(isStop: boolean, frameConfiguration: Frame): Promise<Object>;
    }

    export declare enum HMSGestureDetection {
        ONE = 0,
        SECOND = 1,
        THREE = 2,
        FOUR = 3,
        FIVE = 4,
        SIX = 5,
        SEVEN = 6,
        EIGHT = 7,
        NINE = 8,
        DISS = 9,
        FIST = 10,
        GOOD = 11,
        HEART = 12,
        OK = 13,
        UNKNOWN = 14,
        BOW = 15,
        DOUBLE_UP = 16,
        FUCK = 17,
        HEART_A = 18,
        HEART_B = 19,
        HEART_C = 20,
        PRAY = 21,
        ROCK = 22,
        THANK = 23,
        UP = 24
    }

    export const HMSFaceVerification = {
        setMaxFaceDetected(maxFaceDetected: 1|2|3): Promise<object>;,

        getMaxFaceDetected(): Promise<MaxFaceCount>;,
    
        loadTemplatePic(frameConfiguration: FrameConfiguration): Promise<object>;,
    
        compare(isStop: boolean, frameConfiguration: FrameConfiguration): Promise<object>;,
    
        asyncCompare(isStop: boolean, frameConfiguration: FrameConfiguration): Promise<object>;,
    };

    interface MaxFaceCount {
        maxFaceCount: number
    }
    
    interface FrameConfiguration {
        filePath: string;
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
    
    export const HMSSkeletonDetection = {
        analyzeFrame(isStop: boolean, analyzeType: HMSSkeletonDetection, frameConfiguration: FrameConfiguration): Promise<object>;,
    
        asyncAnalyzeFrame(isStop: boolean, analyzeType: HMSSkeletonDetection, frameConfiguration: FrameConfiguration): Promise<object>;,
    
        calculateSimilarity(isStop: boolean, analyzeType: HMSSkeletonDetection, dataSet1: SkeletonDataSet[], dataSet2: SkeletonDataSet[]): Promise<object>;,
    };
    
    export declare enum HMSSkeletonDetection {
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

    interface SkeletonDataSet {
        joints: Joints[]
    }
    
    interface Joints {
        type: number;
        pointX: number;
        pointY: number;
        score: number;
    }
    
    export const HMSLivenessDetection = {
        startDetect(): Promise<object>;,
    
        setConfig(config: CaptureConfiguration): Promise<object>;,
    };

    export declare enum HMSLivenessDetection {
        CAMERA_NO_PERMISSION = 11401,
        CAMERA_START_FAILED = 11402,
        USER_CANCEL = 11403,
        DETECT_FACE_TIME_OUT = 11404,
        DETECT_MASK = 1
    }
    
    interface CaptureConfiguration {
        option: number;
    }
    
    export const HMSHandKeypointDetection = {
        asyncAnalyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration, analyzerSetting: HandKeyPointAnalyzerConfiguration): Promise<object>;,
    
        analyzeFrame(isStop: boolean, frameConfiguration: FrameConfiguration, analyzerSetting: HandKeyPointAnalyzerConfiguration): Promise<object>;,
    };

    export declare enum HMSHandKeypointDetection {
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
    
    export const HMSFaceRecognition = {
        asyncAnalyzeFrame(is3D: boolean, isStop: boolean, frameConfiguration: FrameConfiguration, faceAnalyzerConfiguration: FaceAnalyzerConfiguration2D | FaceAnalyzerConfiguration3D): Promise<object>;,
    
        analyzeFrame(is3D: boolean, isStop: boolean, frameConfiguration: FrameConfiguration, faceAnalyzerConfiguration: FaceAnalyzerConfiguration2D | FaceAnalyzerConfiguration3D): Promise<object>;,
    };
    
    export declare enum HMSFaceRecognition {
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
    
    export const HMSCustomModel = {
        exec(isRemote: boolean, modelInputOutputSettings: ModelInputOutputSettings, modelInputConfiguration: ModelInputConfiguration, modelConfig: CustomModelConfiguration): Promise<object>;,
    
        close(isRemote: boolean, modelConfig: CustomModelConfiguration): Promise<object>;,
    
        getOutputIndex(isRemote: boolean, channelName: string, modelConfig: CustomModelConfiguration): Promise<object>;,
    };
    
    interface ModelInputOutputSettings {
        inputFormat: InputFormat;
        outputFormat: OutputFormat;
    }
    
    interface InputFormat {
        width: number;
        height: number;
    }
    
    interface OutputFormat {
        outputSize: number;
    }
    
    interface ModelInputConfiguration {
        uri: string;
        height: number;
        width: number;
    }
    
    interface CustomModelConfiguration {
        modelName: string;
        assetPath: string;
        localFullPath: string;
    }
    
    export const HMSModelDownload = {
        downloadModel(modelConfig: DownloadModelConfiguration, strategyConfig: StrategyConfiguration): Promise<object>;,
    
        deleteModel(modelConfig: DownloadModelConfiguration): Promise<object>;,
    
        isModelExist(modelConfig: DownloadModelConfiguration): Promise<object>;,
    
        getModels(modelTag: number): Promise<object>;,
    
        getRecentModelFile(modelConfig: DownloadModelConfiguration): Promise<object>;,
    };

    export declare enum HMSModelDownload {
        REGION_DR_CHINA = 1002,
        REGION_DR_RUSSIA = 1005,
        REGION_DR_GERMAN = 1006,
        REGION_DR_SINGAPORE = 1007,
        DOWNLOAD_ON_PROCESS = "modelDownloadOnProcess",
        MODEL_TTS_TAG = 1,
        MODEL_TRANSLATE_TAG = 2,
        MODEL_CUSTOM_TAG = 3
    }
    
    interface DownloadModelConfiguration {
        translate: TranslateModelConfiguration;
        tts: TtsModelConfiguration;
        customRemoteModel: string;
    }
    
    interface TranslateModelConfiguration {
        languageCode: HMSTextToSpeech;
    }
    
    interface TtsModelConfiguration {
        speakerName: HMSTextToSpeech;
    }
    
    export const HMSTextToSpeech = {
        createEngine(ttsConfiguration: TtsConfiguration): Promise<object>;,
    
        speak(text: string, mode: HMSTextToSpeech): Promise<object>;,
        
        resume(): Promise<object>;,
        
        setPlayerVolume(volume: number): Promise<object>;,
    
        stop(): Promise<object>;,
    
        pause(): Promise<object>;,
    
        shutdown(): Promise<object>;,
    
        getLanguages(): Promise<object>;,
    
        getSpeaker(language: HMSTextToSpeech): Promise<object>;,
    
        getSpeakers(): Promise<object>;,
    
        isLanguageAvailable(language: HMSTextToSpeech): Promise<object>;,
    
        updateConfig(ttsConfiguration: TtsConfiguration): Promise<object>;,
    };

    export declare enum HMSTextToSpeech {
        TTS_SPEAKER_FEMALE_ZH = "Female-zh",
        TTS_SPEAKER_FEMALE_EN = "Female-en",
        TTS_SPEAKER_MALE_ZH = "Male-zh",
        TTS_SPEAKER_MALE_EN = "Male-en",
        TTS_SPEAKER_FEMALE_ZH_1 = "zh-Hans-st-1",
        TTS_SPEAKER_MALE_ZH_1 = "zh-Hans-st-2",
        TTS_SPEAKER_FEMALE_ZH_2 = "zh-Hans-st-3",
        TTS_SPEAKER_MALE_ZH_2 = "zh-Hans-st-4",
        TTS_SPEAKER_FEMALE_EN_1 = "en-US-st-1",
        TTS_SPEAKER_MALE_EN_1 = "en-US-st-2",
        TTS_SPEAKER_FEMALE_EN_2 = "en-US-st-3",
        TTS_SPEAKER_MALE_EN_2 = "en-US-st-4",
        TTS_SPEAKER_FEMALE_ZH_HQ = "zh-Hans-hq-1",
        TTS_SPEAKER_FEMALE_EN_HQ = "en-US-hq-1",
        TTS_SPEAKER_MALE_ZH_HQ = "zh-Hans-hq-2",
        TTS_SPEAKER_MALE_EN_HQ = "en-US-hq-2",
        TTS_SPEAKER_FEMALE_FR = "fr-FR-st-1",
        TTS_SPEAKER_FEMALE_ES = "es-ES-st-1",
        TTS_SPEAKER_FEMALE_DE = "de-DE-st-1",
        TTS_SPEAKER_FEMALE_IT = "it-IT-st-1",
        TTS_SPEAKER_FEMALE_AR = "ar-AR-st-1",
        TTS_SPEAKER_FEMALE_RU = "ru-RU-st-1",
        TTS_SPEAKER_FEMALE_TH = "th-TH-st-1",
        TTS_SPEAKER_FEMALE_PL = "pl-PL-st-1",
        TTS_SPEAKER_FEMALE_MS = "ms-MS-st-1",
        TTS_SPEAKER_FEMALE_TR = "tr-TR-st-1",
        TTS_ZH_HANS = "zh-Hans",
        TTS_EN_US = "en-US",
        TTS_LAN_FR_FR = "fr-FR",
        TTS_LAN_ES_ES = "es-ES",
        TTS_LAN_DE_DE = "de-DE",
        TTS_LAN_IT_IT = "it-IT",
        TTS_LAN_AR_AR = "ar-AR",
        TTS_LAN_RU_RU = "ru-RU",
        TTS_LAN_TH_TH = "th-TH",
        TTS_LAN_PL_PL = "pl-PL",
        TTS_LAN_MS_MS = "ms-MS",
        TTS_LAN_TR_TR = "tr-TR",
        TTS_SPEAKER_OFFLINE_ZH_HANS_FEMALE_BOLT = "zh-Hans-st-bolt-1",
        TTS_SPEAKER_OFFLINE_ZH_HANS_MALE_BOLT = "zh-Hans-st-bolt-2",
        TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BOLT = "en-US-st-bolt-1",
        TTS_SPEAKER_OFFLINE_EN_US_MALE_BOLT = "en-US-st-bolt-2",
        TTS_SPEAKER_OFFLINE_ZH_HANS_FEMALE_EAGLE = "zh-Hans-st-eagle-,",
        TTS_SPEAKER_OFFLINE_ZH_HANS_MALE_EAGLE = "zh-Hans-st-eagle-2",
        TTS_SPEAKER_OFFLINE_EN_US_FEMALE_EAGLE = "en-US-st-eagle-1",
        TTS_SPEAKER_OFFLINE_EN_US_MALE_EAGLE = "en-US-st-eagle-2",
        TTS_SPEAKER_OFFLINE_EN_US_FEMALE_BEE = "en-US-st-bee-1",
        TTS_SPEAKER_OFFLINE_FR_FR_FEMALE_BEE = "fr-FR-st-bee-1",
        TTS_SPEAKER_OFFLINE_ES_ES_FEMALE_BEE = "es-ES-st-bee-1",
        TTS_SPEAKER_OFFLINE_DE_DE_FEMALE_BEE = "de-DE-st-bee-1",
        TTS_SPEAKER_OFFLINE_IT_IT_FEMALE_BEE = "it-IT-st-bee-1",
        TTS_SPEAKER_OFFLINE_EN_US_FEMALE_FLY = "en-US-st-fly-1",
        TTS_SPEAKER_OFFLINE_FR_FR_FEMALE_FLY = "fr-FR-st-fly-1",
        TTS_SPEAKER_OFFLINE_ES_ES_FEMALE_FLY = "es-ES-st-fly-1",
        TTS_SPEAKER_OFFLINE_DE_DE_FEMALE_FLY = "de-DE-st-fly-1",
        TTS_SPEAKER_OFFLINE_IT_IT_FEMALE_FLY = "it-IT-st-fly-1",
        TTS_SPEAKER_OFFLINE_AR_AR_FEMALE_FLY = "ar-AR-st-fly-1",
        TTS_SPEAKER_OFFLINE_RU_RU_FEMALE_FLY = "ru-RU-st-fly-1",
        TTS_SPEAKER_OFFLINE_TH_TH_FEMALE_FLY = "th-TH-st-fly-1",
        EVENT_PLAY_START = 1,
        EVENT_PLAY_RESUME = 2,
        EVENT_PLAY_PAUSE = 3,
        EVENT_PLAY_STOP = 4,
        EVENT_SYNTHESIS_START = 5,
        EVENT_SYNTHESIS_END = 6,
        EVENT_SYNTHESIS_COMPLETE = 7,
        EVENT_PLAY_STOP_INTERRUPTED = "interrupted",
        EVENT_SYNTHESIS_INTERRUPTED = "interrupted",
        LANGUAGE_AVAILABLE = 0,
        LANGUAGE_NOT_SUPPORT = 1,
        LANGUAGE_UPDATING = 2,
        TTS_ONLINE_MODE = "online",
        TTS_OFFLINE_MODE = "offline",
        QUEUE_APPEND = 0,
        QUEUE_FLUSH = 1,
        EXTERNAL_PLAYBACK = 2,
        OPEN_STREAM = 4,
        ERR_ILLEGAL_PARAMETER = 11301,
        ERR_NET_CONNECT_FAILED = 11302,
        ERR_INSUFFICIENT_BALANCE = 11303,
        ERR_SPEECH_SYNTHESIS_FAILED = 11304,
        ERR_AUDIO_PLAYER_FAILED = 11305,
        ERR_AUTHORIZE_FAILED = 11306,
        ERR_AUTHORIZE_TOKEN_INVALIDE = 11307,
        ERR_INTERNAL = 11398,
        ERR_UNKNOWN = 11399,
        WARN_INSUFFICIENT_BANDWIDTH = 113001,
        FORMAT_PCM_8BIT = 1,
        FORMAT_PCM_16BIT = 2,
        SAMPLE_RATE_16K = 16000,
        CHANNEL_OUT_MONO = 4,
        TTS_ON_AUDIO_AVAILABLE = "ttsOnAudioAvailable",
        TTS_ON_EVENT = "ttsOnEvent",
        TTS_ON_RANGE_START = "ttsOnRangeStart",
        TTS_ON_WARN = "ttsOnWarn",
        TTS_ON_ERROR = "ttsOnError"
    }
    
    interface TtsConfiguration {
        volume: number;
        speed: number;
        language: HMSTextToSpeech;
        person: HMSTextToSpeech;
        synthesizeMode: HMSTextToSpeech;
    }
    
    export const HMSSoundDetect = {
        createSoundDetector(): Promise<object>;,
    
        destroy(): Promise<object>;,
    
        stop(): Promise<object>;,
    
        start(): Promise<object>;,
    
        setSoundDetectorListener(): Promise<object>;,
    };

    export declare enum HMSSoundDetect {
        SOUND_DETECT_ERROR_NO_MEM = 12201,
        SOUND_DETECT_ERROR_FATAL_ERROR = 12202,
        SOUND_DETECT_ERROR_AUDIO = 12203,
        SOUND_DETECT_ERROR_INTERNAL = 12298,
        SOUND_EVENT_TYPE_LAUGHTER = 0,
        SOUND_EVENT_TYPE_BABY_CRY = 1,
        SOUND_EVENT_TYPE_SNORING = 2,
        SOUND_EVENT_TYPE_SNEEZE = 3,
        SOUND_EVENT_TYPE_SCREAMING = 4,
        SOUND_EVENT_TYPE_MEOW = 5,
        SOUND_EVENT_TYPE_BARK = 6,
        SOUND_EVENT_TYPE_WATER = 7,
        SOUND_EVENT_TYPE_CAR_ALARM = 8,
        SOUND_EVENT_TYPE_DOOR_BELL = 9,
        SOUND_EVENT_TYPE_KNOCK = 10,
        SOUND_EVENT_TYPE_ALARM = 11,
        SOUND_EVENT_TYPE_STEAM_WHISTLE = 12,
        SOUND_DETECT_ON_FAILURE = "soundDetectOnFailure",
        SOUND_DETECT_ON_SUCCESS = "soundDetectOnSuccess"
    }

    export declare enum MLSpeechRealTimeTranscriptionConstants {
        ERR_NO_NETWORK = 13202,
        ERR_SERVICE_UNAVAILABLE = 13203,
        ERR_INVALIDE_TOKEN = 13219,
        ERR_SERVICE_CREDIT = 13222,
        ERR_SERVICE_UNSUBSCRIBED = 13223,
        ERR_SERVICE_FREE_USED_UP = 13224,
        LAN_ZH_CN = "zh-CN",
        LAN_EN_US = "en-US",
        LAN_FR_FR = "fr-FR",
        LAN_ES_ES = "es-ES",
        LAN_EN_IN = "en-IN",
        LAN_DE_DE = "de-DE",
        STATE_LISTENING = 1,
        STATE_NO_UNDERSTAND = 6,
        STATE_NO_NETWORK = 7,
        STATE_SERVICE_RECONNECTING = 42,
        STATE_SERVICE_RECONNECTED = 43,
        SPEECH_RTT_ON_RECOGNIZING_RESULTS = "speechRttOnError",
        SPEECH_RTT_ON_ERROR = "speechRttOnError",
        SPEECH_RTT_ON_LISTENING = "speechRttOnListening",
        SPEECH_RTT_ON_STARTING_OF_SPEECH = "speechRttOnStartingOfSpeech",
        SPEECH_RTT_ON_VOICE_DATA_RECEIVED = "speechRttOnVoiceDataReceived",
        SPEECH_RTT_ON_STATE = "speechRttOnState",
        SCENES_SHOPPING = "shopping"
    }

    export const HMSComposite = {
        createCompositeAnalyzer(configuration: TextAnalyzerConfiguration | CompositeFaceAnalyzerFaceAnalyzerConfiguration2D | CompositeFaceAnalyzerConfiguration3D | SkeletonAnalyzerConfiguration | CompositeClassificationAnalyzerConfigurationRemote | CompositeClassificationAnalyzerConfigurationLocal | ObjectAnalyzerSettingConfiguration |  HandKeyPointAnalyzerConfiguration): Promise<object>;,
    
        isAvailable(): Promise<object>;,
    
        destroy(): Promise<object>;,
    
        analyzeFrame(frameConfiguration: FrameConfiguration): Promise<object>;,
    };

    interface CompositeFaceAnalyzerFaceAnalyzerConfiguration2D {
        featureType: HMSFaceRecognition;
        shapeType: HMSFaceRecognition;
        keyPointType: HMSFaceRecognition;
        performanceType: HMSFaceRecognition;
        tracingMode: HMSFaceRecognition;
        minFaceProportion: number;
        isPoseDisabled: boolean;
        isTracingAllowed: boolean;
        isFace2D: true;
    }

    interface CompositeFaceAnalyzerConfiguration3D {
        performanceType: HMSFaceRecognition;
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
}