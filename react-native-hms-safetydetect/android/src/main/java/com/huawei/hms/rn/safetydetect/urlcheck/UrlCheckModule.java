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

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.util.Map;

public class UrlCheckModule extends ReactContextBaseJavaModule {
    private final UrlCheckService urlCheckService;

    public UrlCheckModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.urlCheckService = new UrlCheckService(reactContext);
    }

    @Override
    public String getName() {
        return "HMSUrlCheck";
    }

    @Override
    public Map<String, Object> getConstants() {
        return urlCheckService.getConstants();
    }

    @ReactMethod
    public void initUrlCheck(Promise promise) {
        urlCheckService.initUrlCheck(promise);
    }

    @ReactMethod
    public void urlCheck(ReadableMap params, Promise promise) {
        urlCheckService.urlCheck(params, promise);
    }

    @ReactMethod
    public void shutdownUrlCheck(Promise promise) {
        urlCheckService.shutdownUrlCheck(promise);
    }
}
