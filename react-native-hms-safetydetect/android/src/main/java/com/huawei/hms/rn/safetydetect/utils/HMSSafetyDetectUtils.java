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

package com.huawei.hms.rn.safetydetect.utils;

import com.facebook.react.bridge.Promise;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.rn.safetydetect.logger.HMSLogger;

public class HMSSafetyDetectUtils {
    public static void taskHandler (Task task, Promise promise, HMSLogger hmsLogger, String methodName) {
        task.addOnSuccessListener((OnSuccessListener<Void>) aVoid -> {
            hmsLogger.sendSingleEvent(methodName);
            promise.resolve(true);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                hmsLogger.sendSingleEvent(methodName, e.getMessage());
                promise.reject("ERROR: " + e.getMessage());
            }
        });
    }
}