/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

import { ToastAndroid } from 'react-native';
import { HMSApplication } from '@hmscore/react-native-hms-nearby';

export function stringToByteArray(str) {
    var result = [];
    for (var i = 0; i < str.length; i++) {
        result.push(str.charCodeAt(i));
    }
    return result;
}

export function byteArrayToString(array) {
    return String.fromCharCode.apply(String, array);
}

export function messageResult(result, mes) {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
        ToastAndroid.showWithGravity(mes, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
    else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
}