/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

declare module "@hmscore/react-native-hms-safetydetect" {
    export const HMSHuaweiApi = {
        isHuaweiMobileServicesAvailable(): Promise<boolean>;,
        enableLogger(): void;,
        disableLogger(): void;
    }

    export const HMSSysIntegrity = {
        sysIntegrity(nonce: string, appId: string): Promise<string>;,
        sysIntegrityWithRequest(args: integrityArgs): Promise<string>;
    }

    export interface integrityArgs {
        nonce: string,
        appId: string,
        alg: string
    }

    export const HMSAppsCheck = {
        enableAppsCheck(): Promise<boolean>;,
        isVerifyAppsCheck(): Promise<boolean>;,
        getMaliciousAppsList(): Promise<MaliciousApp[]>;,
        RISK_APP = 0,
        VIRUS_APP = 2
    }

    export interface MaliciousApp {
        appPackageName: string,
        apkSha256: string,
        apkCategory: HMSAppsCheck
    }

    export const HMSUrlCheck = {
        initUrlCheck(): Promise<boolean>;,
        shutdownUrlCheck(): Promise<boolean>;,
        urlCheck(params: UrlCheckParam): Promise<HMSURLCheck[]>;,
        MALWARE = 1,
        PHISHING = 3
    }

    export interface UrlCheckParam {
        appId: string,
        url: string,
        UrlCheckThreat: HMSAppsCheck
    }

    export const HMSUserDetect = {
        initUserDetect(): Promise<boolean>;,
        userDetection(appId: string): Promise<string>;,
        shutdownUserDetect(): Promise<boolean>;,
        initAntiFraud(appId: string): Promise<boolean>;,
        getRiskToken(): Promise<string>;,
        releaseAntiFraud(): Promise<boolean>;
    }

    export const HMSWifiDetect = {
        getWifiDetectStatus(): Promise<HMSWifiDetect>;,
        NO_WiFi = 0,
        WiFi_SECURE = 1,
        WiFi_INSECURE = 2
    }
}