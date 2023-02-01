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

package com.huawei.hms.rn.safetydetect.appcheck;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.support.api.entity.safetydetect.MaliciousAppsData;

import java.util.List;

public class AppCheckUtils {
    public static WritableArray convertToWritableMap(List<MaliciousAppsData> appsDataList) {
        WritableArray array = Arguments.createArray();
        for (MaliciousAppsData maliciousApp : appsDataList) {
            WritableMap map = Arguments.createMap();
            map.putString("apkPackageName", maliciousApp.getApkPackageName());
            map.putString("apkSha256", maliciousApp.getApkSha256());
            map.putInt("apkCategory", maliciousApp.getApkCategory());
            array.pushMap(map);
        }
        return array;
    }
}
