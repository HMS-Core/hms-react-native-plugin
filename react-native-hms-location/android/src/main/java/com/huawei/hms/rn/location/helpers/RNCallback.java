/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.location.helpers;

import com.facebook.react.bridge.Promise;
import com.huawei.hms.rn.location.backend.interfaces.HMSCallback;

import org.json.JSONObject;

public class RNCallback implements HMSCallback {
    private Promise promise;

    public static RNCallback fromPromise(Promise promise) {
        return new RNCallback(promise);
    }

    private RNCallback(Promise promise) {
        this.promise = promise;
    }

    @Override
    public void success() {
        promise.resolve(true);
    }

    @Override
    public void success(JSONObject json) {
        promise.resolve(ReactUtils.toWM(json));
    }

    @Override
    public void error(JSONObject json) {
        promise.reject(json.toString());
    }
}
