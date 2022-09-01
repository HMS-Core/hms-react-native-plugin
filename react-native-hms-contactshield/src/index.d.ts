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

declare module "@hmscore/react-native-hms-contactshield" {

    export default HMSContactShieldModule = {
        startContactShield(incubationPeriod: number): Promise<void>;,
        startContactShieldCallback(incubationPeriod: number): Promise<void>;,
        startContactShieldNoPersistent(incubationPeriod: number): Promise<void>;,
        stopContactShield(): Promise<void>;,
        getContactDetail(token: string): Promise<ContactDetail[]>;,
        getContactSketch(token: string): Promise<ContactSketch>;,
        putSharedKeyFiles(paths: string[], token: string, diagnosisConfiguration: DiagnosisConfiguration): Promise<void>;,
        putSharedKeyFilesCallback(paths: string[], token: string, diagnosisConfiguration: DiagnosisConfiguration): Promise<void>;,
        putSharedKeyFilesKeys(paths: string[], sharedKeyFiles: SharedKeyData): Promise<void>;,
        putSharedKeyFilesProvider(paths: string[]): Promise<void>;,
        putSharedKeyFilesKeysProvider(paths: string[], publicKeys: string[]): Promise<void>;,
        getContactWindow(token: string): Promise<ContactWindow[]>;,
        clearAllData(): Promise<void>;,
        getPeriodicKey(): Promise<PeriodicKey[]>;,
        isContactShieldRunning(): Promise<void>;,
        getDiagnosisConfiguration(): Promise<DiagnosisConfiguration>;,
        getDailySketch(dailySketchConfiguration: DailySketchConfiguration): Promise<DailySketch[]>;,
        getSharedKeysDataMapping(): Promise<SharedKeysDataMapping>;,
        setSharedKeysDataMapping(sharedKeysDataMapping: SharedKeysDataMapping): Promise<void>;,
        isSupportScanningWithoutLocation(): Promise<boolean>;,
        getDeviceCalibrationConfidence(): Promise<number>;,
        getContactShieldVersion(): Promise<number>;,
        getStatus(): Promise<ContactShieldStatus>;,
        enableLogger(): null;,
        disableLogger(): null;,
    }

    export declare enum HMSContactShieldSetting {
        DEFAULT = 14
    }

    export declare enum ContactShieldStatus {
        RUNNING = 1,
        NOT_RUNNING = 2,
        BLUETOOTH_OFF = 4,
        LOCATION_OFF = 8,
        NO_LOCATION_PERMISSION = 16,
        HARDWARE_NOT_SUPPORT = 32,
        STORAGE_LIMITED = 64,
        RUNNING_FOR_ANOTHER_APP = 128,
        UNKNOWN = 1024
    }

    export declare enum CalibrationConfidence {
        LOWEST = 0,
        LOW = 1,
        MEDIUM = 2,
        HIGH = 3
    }

    export declare enum HMSTokenMode {
        TOKEN_A = "TOKEN_WINDOW_MODE"
    }

    export declare enum  HMSRiskLevel {
        RISK_LEVEL_INVALID = 0,
        RISK_LEVEL_LOWEST = 1,
        RISK_LEVEL_LOW = 2,
        RISK_LEVEL_MEDIUM_LOW = 3,
        RISK_LEVEL_MEDIUM = 4,
        RISK_LEVEL_MEDIUM_HIGH = 5,
        RISK_LEVEL_HIGH = 6,
        RISK_LEVEL_EXT_HIGH = 7,
        RISK_LEVEL_HIGHEST = 8
    }

    export declare enum  HMSStatusCode {
        STATUS_SUCCESS = 0,
        STATUS_FAILURE = -1,
        STATUS_API_DISORDER = 8001,
        STATUS_APP_QUOTA_LIMITED = 8100,
        STATUS_DISK_FULL = 8101,
        STATUS_BLUETOOTH_OPERATION_ERROR = 8102,
        STATUS_MISSING_PERMISSION_BLUETOOTH = 8016,
        STATUS_MISSING_SETTING_LOCATION_ON = 8020,
        STATUS_INTERNAL_ERROR = 8060,
        STATUS_MISSING_PERMISSION_INTERNET = 8064
    }

    interface ContactDetail {
        attenuationDurations: number[],
        attenuationRiskValue: number,
        dayNumber: number,
        durationMinutes: number,
        initialRiskLevel: number,
        totalRiskValue: number
    }

    interface ContactSketch {
        attenuationDurations: number[],
        daysSinceLastHint: number,
        maxRiskValue: number,
        numberOfHits: number,
        summationRiskValue: number
    }

    interface ContactWindow {
        dateMillis: number,
        getReportType: number,
        scanInfos: ScanInfo[]
    }

    interface DailySketch {
        daysSinceEpoch: number,
        reportSketches: SketchData[],
        sketchData: SketchData
    }

    interface DailySketchConfiguration {
        weightsOfReportType: number[],
        weightsOfContagiousness: number[],
        thresholdOfAttenuationInDb: number[],
        weightsOfAttenuationBucket: number[],
        thresholdOfDaysSinceHit: number,
        minWindowScore: number,
    }

    interface DiagnosisConfiguration {
        attenuationDurationThresholds: number[],
        attenuationRiskValue: number[],
        daysAfterContactedRiskValues: number[],
        durationRiskValues: number[],
        initialRiskLevelRiskValues: number[],
        minimumRiskValueThresold: number
    }

    interface PeriodicKey {
        content: number[],
        initialRiskLevel: number,
        periodicKeyLifeTime: number,
        periodicKeyValidTime: number,
        reportType: number
    }

    interface SketchData {
        maxScore: number,
        scoreSum: number,
        weightedDurationSum: number
    }

    interface SharedKeyData {
        token: string,
        diagnosisConfiguration: DiagnosisConfiguration,
        publicKeys: string[]
    }

    interface SharedKeysDataMapping {
        daysSinceCreationToContagiousness: number[],
        defaultReportType: number,
        defaultContagiousness: number
    }

    interface ScanInfo {
        averageAttenuation: number,
        minimumAttenuation: number,
        secondsSinceLastScan: number
    }

}