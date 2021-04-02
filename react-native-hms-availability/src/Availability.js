/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

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

import {NativeModules, Platform, NativeEventEmitter} from "react-native";
const {HMSAvailabilityModule} = NativeModules;

class HMSAvailability {

     static eventEmitter = new NativeEventEmitter(HMSAvailabilityModule)
     static event = null;
 
    static OnErrorDialogFragmentCancelledListenerAdd(handler){
        if(this.event == null){
            this.event = this.eventEmitter.addListener('OnErrorDialogFragmentCancelled', handler); 
        }
       }

    static OnErrorDialogFragmentCancelledListenerRemove(handler){
       this.event.remove();
       this.event = null;
    }

    static isHuaweiMobileServicesAvailable(minApkVersion){
        if(arguments.length === 0){
            return HMSAvailabilityModule.isHuaweiMobileServicesAvailableWithoutParam();
        }else{
            return HMSAvailabilityModule.isHuaweiMobileServicesAvailableWithParam(minApkVersion);
        }      
    }

    static getApiMap(){
        return HMSAvailabilityModule.getApiMap();
    }

    static getServicesVersionCode(){
        return HMSAvailabilityModule.getServicesVersionCode();
    }

    static setServicesVersionCode(versionCode){
        return HMSAvailabilityModule.setServicesVersionCode(versionCode);
    }

    static getErrorString(errorCode){
        return HMSAvailabilityModule.getErrorString(errorCode);
    }

    static resolveError(errorCode, requestCode){
        return HMSAvailabilityModule.resolveError(errorCode, requestCode);
    }

    static isUserResolvableError(errorCode){
        return HMSAvailabilityModule.isUserResolvableError(errorCode);
    }

    static isHuaweiMobileNoticeAvailable(){
        return HMSAvailabilityModule.isHuaweiMobileNoticeAvailable();
    }

    static setServicesVersionCode(versionCode){
        return HMSAvailabilityModule.setServicesVersionCode(versionCode);
    }

    static showErrorDialogFragment(errorCode, resultCode){
        return HMSAvailabilityModule.showErrorDialogFragment(errorCode, resultCode);
    }

    static showErrorNotification(errorCode){
        return HMSAvailabilityModule.showErrorNotification(errorCode);
    }
}
   
export const ErrorCode = Platform.OS === "android" ? HMSAvailabilityModule.ErrorCode : null;

export default HMSAvailability;



