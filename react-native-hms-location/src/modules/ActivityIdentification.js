/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

import { NativeModules, DeviceEventEmitter } from 'react-native';
import { areKeysExact, isArray } from '../utils/typeCheck';

const { HMSActivityIdentification } = NativeModules;

export const ActivityConversionInfo = {
    configure(val) {
        if (areKeysExact(this.activityConversionInfo, val)) {
            this.activityConversionInfo = val;
        } else {
            throw new Error(
                'Provided ActivityConversionInfo object keys do not match required ones ' +
                {
                    conversionType: 'int',
                    activityType: 'int'
                },
            );
        }
        return this;
    },
    activityConversionInfo: {
        conversionType: 0,
        activityType: 0
    },
    build() {
        return this.activityConversionInfo;
    },
};

export const ActivityConversionData = {
    configure(val) {
        if (areKeysExact(this.activityConversionData, val)) {
            this.activityConversionData = val;
        } else {
            throw new Error(
                'Provided ActivityConversionData object keys do not match required ones ' +
                {
                    activityType: 'int',
                    elapsedTimeFromReboot: 'float',
                    conversionType: 'int'
                },
            );
        }
        return this;
    },
    activityConversionData: {
        activityType: 0,
        elapsedTimeFromReboot: 0.0,
        conversionType: 0
    },
    build() {
        return this.activityConversionData;
    },
};

export const ActivityConversionRequest = {
    configure(val) {
        if (isArray(val)) {
            val.array.forEach(reqData => {
                if (!areKeysExact(ActivityConversionInfo.activityConversionInfo, reqData)) {
                    throw new Error(
                        'Keys of one of the elements of the provided ActivityConversionRequest object do not match required ones ' +
                        {
                            conversionType: 'int',
                            activityType: 'int'
                        },
                    );
                }
            });
            this.activityConversionRequest = val;
        } else {
            throw new Error('Provided ActivityConversionRequest object is not an array ');
        }
        return this;
    },
    activityConversionRequest: [],
    build() {
        return this.activityConversionRequest;
    },
};

export const ActivityConversionResponse = {
    configure(val) {
        if (isArray(val)) {
            val.array.forEach(resData => {
                if (!areKeysExact(ActivityConversionData.activityConversionData, resData)) {
                    throw new Error(
                        'Keys of one of the elements of the provided ActivityConversionResponse object do not match required ones ' +
                        {
                            activityType: 'int',
                            elapsedTimeFromReboot: 'float',
                            conversionType: 'int'
                        },
                    );
                }
            });
            this.activityConversionResponse = val;
        } else {
            throw new Error('Provided ActivityConversionResponse object is not an array ');
        }
        return this;
    },
    activityConversionResponse: [],
    build() {
        return this.activityConversionResponse;
    },
};

export const ActivityIdentificationData = {
    configure(val) {
        if (areKeysExact(this.activityIdentificationData, val)) {
            this.activityIdentificationData = val;
        } else {
            throw new Error(
                'Provided ActivityIdentificationData object keys do not match required ones ' +
                {
                    possibility: 'int',
                    identificationActivity: 'int'
                },
            );
        }
        return this;
    },
    activityIdentificationData: {
        possibility: 0,
        identificationActivity: 0
    },
    build() {
        return this.activityIdentificationData;
    },
};

export const ActivityIdentificationResponse = {
    configure(val) {
        if (areKeysExact(this.activityIdentificationResponse, val)) {
            if (isArray(val.activityIdentificationDatas)) {
                val.activityIdentificationDatas.forEach(resData => {
                    if (!areKeysExact(ActivityIdentificationData.activityIdentificationData, resData)) {
                        throw new Error(
                            'Keys of one of the elements of the provided ActivityIdentificationResponse object do not match required ones ' +
                            {
                                possibility: 'int',
                                identificationActivity: 'int'
                            },
                        );
                    }
                });
                this.activityIdentificationResponse = val;
                return this;
            }
            throw new Error('activityIdentificationDatas element of the provided ActivityIdentificationResponse object is not an array ');
        } else {
            throw new Error(
                'Provided ActivityIdentificationResponse object keys do not match required ones ' +
                {
                    elapsedTimeFromReboot: 'int',
                    mostActivityIdentification: 'ActivityIdentificationData',
                    activityIdentificationDatas: 'array',
                    time: 'int'
                },
            );
        }
    },
    activityIdentificationResponse: {
        elapsedTimeFromReboot: 0,
        mostActivityIdentification: 0,
        activityIdentificationDatas: [],
        time: 0
    },
    build() {
        return this.activityIdentificationResponse;
    },
};

export const addActivityIdentificationEventListener = callback => {
    DeviceEventEmitter.addListener(
        HMSActivityIdentification.Events.ACTIVITY_IDENTIFICATION_RESULT,
        callback,
    );
};

export const removeActivityIdentificationEventListener = async (callback) => {
    DeviceEventEmitter.removeListener(
        HMSActivityIdentification.Events.ACTIVITY_IDENTIFICATION_RESULT,
        callback,
    );
};


export const addActivityConversionEventListener = callback => {
    DeviceEventEmitter.addListener(
        HMSActivityIdentification.Events.ACTIVITY_CONVERSION_RESULT,
        callback,
    );
};

export const removeActivityConversionEventListener = async (callback) => {
    DeviceEventEmitter.removeListener(
        HMSActivityIdentification.Events.ACTIVITY_CONVERSION_RESULT,
        callback,
    );
};

export default HMSActivityIdentification;
