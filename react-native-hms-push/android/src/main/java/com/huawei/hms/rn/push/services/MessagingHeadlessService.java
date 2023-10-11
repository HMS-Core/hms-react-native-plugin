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

package com.huawei.hms.rn.push.services;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.hms.rn.push.constants.Core;
import com.huawei.hms.rn.push.utils.RemoteMessageUtils;

import javax.annotation.Nullable;

public class MessagingHeadlessService extends HeadlessJsTaskService {

    private static final String TASK = "HMSPushHeadlessTask";

    @Override
    protected @Nullable
    HeadlessJsTaskConfig getTaskConfig(Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras == null) return null;
        RemoteMessage remoteMessage = intent.getParcelableExtra(Core.Event.Result.MSG);
        if (remoteMessage == null) return null;

        return new HeadlessJsTaskConfig(
            TASK,
            RemoteMessageUtils.toWritableMap(remoteMessage),
            60000,
            true
        );
    }
}
