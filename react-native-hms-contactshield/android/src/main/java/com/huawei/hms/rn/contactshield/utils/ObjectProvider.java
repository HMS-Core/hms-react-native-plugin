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

package com.huawei.hms.rn.contactshield.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import com.huawei.hms.contactshield.DailySketchConfiguration;
import com.huawei.hms.contactshield.DiagnosisConfiguration;

import org.json.JSONObject;

public class ObjectProvider {
    private ObjectProvider() {
    }

    public static PendingIntent getPendingIntent(final Context context, final String action, final int requestCode) {
        final Intent intent = new Intent(action).setPackage(context.getPackageName());
        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static DailySketchConfiguration dailySketchConfiguration(final JSONObject dailySketchConfigurationJson,
        final Gson gson) {
        return gson.fromJson(dailySketchConfigurationJson.toString(), DailySketchConfiguration.class);
    }

    public static DiagnosisConfiguration getDiagnosisConfiguration(final JSONObject diagnosisConfigJson,
        final Gson gson) {
        return gson.fromJson(diagnosisConfigJson.toString(), DiagnosisConfiguration.class);
    }
}
