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

package com.huawei.hms.rn.availability;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.Map;

public class Util {

    private Util() {
    }

    public static WritableMap mapToWM(Map<String, Integer> map) {
        WritableMap resultData = new WritableNativeMap();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            resultData.putInt(entry.getKey(), entry.getValue());
        }
        return resultData;
    }

}
