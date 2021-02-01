/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.site;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class RNHMSSiteModule extends ReactContextBaseJavaModule {

    private RNHMSSiteWrapper siteWrapper;
    private RNHMSWidgetWrapper widgetWrapper;
    private final ReactApplicationContext reactContext;

    public RNHMSSiteModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public void initialize() {
        super.initialize();

    }

    @Override
    public String getName() {
        return "HmsSite";
    }

    @ReactMethod
    public void initializeService(ReadableMap params, Promise promise) {

        siteWrapper = new RNHMSSiteWrapper(getCurrentActivity());
        widgetWrapper = new RNHMSWidgetWrapper(getCurrentActivity());
        reactContext.addActivityEventListener(widgetWrapper);

        siteWrapper.initializeService(params, reactContext.getCurrentActivity(), promise);
    }

    @ReactMethod
    public void textSearch(ReadableMap params, Promise promise) {
        siteWrapper.textSearch(params, promise);
    }

    @ReactMethod
    public void detailSearch(ReadableMap params, Promise promise) {
        siteWrapper.detailSearch(params, promise);
    }

    @ReactMethod
    public void querySuggestion(ReadableMap params, Promise promise) {
        siteWrapper.querySuggestion(params, promise);
    }

    @ReactMethod
    public void nearbySearch(ReadableMap params, Promise promise) {
        siteWrapper.nearbySearch(params, promise);
    }

    @ReactMethod
    public void queryAutocomplete(ReadableMap params, Promise promise) {
        siteWrapper.queryAutocomplete(params, promise);
    }

    @ReactMethod
    public void createWidget(ReadableMap params, Promise promise) {
        if (widgetWrapper != null) {
            widgetWrapper.createSearchWidget(params, promise);
        } else {
            promise.reject("The widget is not initialized.");
        }
    }

    @ReactMethod
    public void enableLogger(Promise promise) {
        if (getCurrentActivity() == null) {
            promise.reject("The activity is not initialized.");
            return;
        }
        HMSLogger.getInstance(getCurrentActivity()).enableLogger();
        promise.resolve("The logger is enabled.");
    }

    @ReactMethod
    public void disableLogger(Promise promise) {
        if (getCurrentActivity() == null) {
            promise.reject("The activity is not initialized.");
            return;
        }
        HMSLogger.getInstance(getCurrentActivity()).disableLogger();
        promise.resolve("The logger is disabled.");
    }
}
