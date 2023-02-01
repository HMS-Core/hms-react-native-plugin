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

package com.huawei.hms.rn.safetydetect.huaweiapi;

import android.app.Activity;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;

public class HuaweiApiService {
    private final String TAG = HuaweiApiService.class.getSimpleName();

    public void isHuaweiMobileServicesAvailable(Activity activity, Promise promise) {
        if (HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(activity) == ConnectionResult.SUCCESS) {
            Log.i(TAG, "HMS is Available");
            promise.resolve(true);
        } else {
            Log.e(TAG, "ERROR: Unavailable. Please update your HMS");
            promise.resolve(false);
        }
    }
}
