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

package com.huawei.hms.rn.safetydetect.urlcheck;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.huawei.hms.support.api.entity.safetydetect.UrlCheckThreat;

import java.util.List;

public class UrlCheckUtils {
    public static int[] convertIntegerArray(List<Double> integers) {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }

    public static WritableArray convertListToArray(List<UrlCheckThreat> urlCheckThreats) {
        WritableArray array = Arguments.createArray();
        for (UrlCheckThreat threat : urlCheckThreats) {
            array.pushInt(threat.getUrlCheckResult());
        }
        return  array;
    }
}