/*
    Copyright 2023-2024. Huawei Technologies Co., Ltd. All rights reserved.

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

declare module "@hmscore/react-native-hms-mllanguage" {

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
    }
    export declare enum CountryCode {
        REGION_DR_CHINA = 1002,
        REGION_DR_RUSSIA = 1005,
        REGION_DR_GERMAN = 1006,
        REGION_DR_SINGAPORE = 1007
    }

    interface UiConfig {
        orientation: HMSGeneralCardRecognition,
        tipTextColor: HMSGeneralCardRecognition,
        scanBoxCornerColor: HMSGeneralCardRecognition,
        tipText: string
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

}