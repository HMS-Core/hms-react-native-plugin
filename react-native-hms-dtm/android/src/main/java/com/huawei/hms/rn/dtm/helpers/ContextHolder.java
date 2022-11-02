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

package com.huawei.hms.rn.dtm.helpers;

import android.annotation.SuppressLint;
import android.content.Context;

public final class ContextHolder {
    @SuppressLint("StaticFieldLeak")
    private static ContextHolder instance;
    private Context context;

    private ContextHolder() {
    }

    public static synchronized ContextHolder getInstance() {
        if (instance == null) {
            instance = new ContextHolder();
        }
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
