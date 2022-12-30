/*
    Copyright 2020-2022. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.ads.utils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CommonUtils {
    private static final String TAG = CommonUtils.class.getSimpleName();

    public static int getIntegerExtra(Intent intent, String name, int defaultValue) {
        try {
            return intent.getIntExtra(name, defaultValue);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return defaultValue;
        }
    }

    public static String getStringExtra(Intent intent, String name) {
        try {
            return intent.getStringExtra(name);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return "";
        }
    }

    public static Bundle getBundleExtra(Intent intent, String name) {
        try {
            return intent.getBundleExtra(name);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
