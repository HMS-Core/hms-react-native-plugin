/*
 *    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License")
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
*/
declare module "@hmscore/react-native-hms-availability" {

  
  export enum ErrorCode {
    HMS_CORE_APK_AVAILABLE = "0",
    NO_HMS_CORE_APK = "1",
    HMS_CORE_APK_OUT_OF_DATE = "2",
    HMS_CORE_APK_UNAVAILABLE = "3",
    HMS_CORE_APK_IS_NOT_OFFICIAL_VERSION = "9",
    HMS_CORE_APK_TOO_OLD = "21",
  }

  export interface ApiMap {
    'HuaweiIap.API': number,
    'HuaweiID.API': number,
    'HuaweiGame.API': number,
    'HuaweiPay.API': number,
    'HuaweiSns.API': number,
    'HuaweiOpenDevice.API': number,
    'HuaweiPPSkit.API': number,
    'HuaweiPush.API': number
  }
  
  export class HMSAvailability{

    /**
     * Obtains the API verssion number of each service.
     */
    getApiMap(): Promise<ApiMap>;

    /**
     * Obtains the minimum version number of HMS Core that is supported currently.
     */
    getServicesVersionCode(): Promise<number>;

    
    /**
     * Displays a readable text result code returned by the isHuaweiMobileServicesAvailable
     * (minApkVersion?: number) method.
     */
    getErrorString(errorCode: number): Promise<string>;

    /**
     * Checks whether HMS Core (APK) is successfully installed and integrated on a device, 
     * and whether the version of the installed APK is that required by the client or is later 
     * than the required version.
     */
    isHuaweiMobileServicesAvailable(minApkVersion?: number): Promise<number>;

    /**
     * Checks whether the HMS Core (APK) version supports notice obtaining.
     */
    isHuaweiMobileNoticeAvailable(): Promise<number>;

    /**
     * Checks whether an exception is rectified through user operations.
     */
    isUserResolvableError(errorCode: number): Promise<boolean>; 

    
    /**
     * Displays a notification or dialog box is displayed for the returned result code if
     * an exception can be rectified through user operations.
     */
    resolveError(errorCode: number, requestCode: number): Promise<void>;

    
    /**
     * Sets the minimum version number of HMS Core that is supported currently.
     */
    setServicesVersionCode(servicesVersionCode: number): Promise<void>;
    
    /**
     * Creates and displays a dialog box for a result code.
     */
    showErrorDialogFragment(errorCode: number, requestCode: number): Promise<boolean>;

    
    /**
     * Creates and displays a dialog box for a result code.
     */
    showErrorNotification(errorCode: number): Promise<void>;

    
    /**
     * Add a listener for the event when dialog fragment cancelled.
     */
    OnErrorDialogFragmentCancelledListenerAdd(listenerFn: () => void): void;

    
    /**
    * Remove the listener for the event when dialog fragment cancelled.
     */
    OnErrorDialogFragmentCancelledListenerRemove(): void;

  }

}