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

package com.huawei.hms.rn.site;

import androidx.annotation.Nullable;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import java.util.Map;

public class RNHMSSiteModule extends ReactContextBaseJavaModule {

    private RNHMSSiteService siteService = new RNHMSSiteService();

    private final ReactApplicationContext reactContext;


    public RNHMSSiteModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "HmsSite";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return siteService.getConstants();
    }

    @ReactMethod
    public void initializeService(ReadableMap params, Promise promise) {
        siteService.initializeService(params, reactContext.getCurrentActivity(), promise);
    }


    @ReactMethod
    public void textSearch(ReadableMap params, Promise promise) {
        siteService.textSearch(params, promise);
    }

    @ReactMethod
    public void detailSearch(ReadableMap params, Promise promise) {
        siteService.detailSearch(params, promise);
    }

    @ReactMethod
    public void querySuggestion(ReadableMap params, Promise promise) {
        siteService.querySuggestion(params, promise);
    }

    @ReactMethod
    public void nearbySearch(ReadableMap params, Promise promise) {
        siteService.nearbySearch(params, promise);
    }
}
