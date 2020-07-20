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

package com.huawei.hms.rn.ml.tts;

import java.util.Map;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.huawei.hms.mlsdk.tts.MLTtsCallback;
import com.huawei.hms.mlsdk.tts.MLTtsEngine;
import com.huawei.hms.mlsdk.tts.MLTtsError;
import com.huawei.hms.mlsdk.tts.MLTtsWarn;
import com.huawei.hms.rn.ml.constants.Constants;


public class HmsTextToSpeech extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME =  HmsTextToSpeech.class.getSimpleName();

    public HmsTextToSpeech(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return Constants.getTtsConstants();
    }

    @ReactMethod
    public void configure(ReadableMap readableMap, final Promise promise) {
        HmsTextToSpeechManager.getInstance().setConfig(readableMap);
        promise.resolve("Configuration created");
    }

    @ReactMethod
    public void createEngine(final Promise promise) {
        HmsTextToSpeechManager.getInstance()
                .setMlTtsEngine(new MLTtsEngine(HmsTextToSpeechManager.getInstance().getConfig()));
        promise.resolve("Engine created");
    }

    @ReactMethod
    public void speak(String text, int mode, final Promise promise) {
        HmsTextToSpeechManager.getInstance().getMlTtsEngine().speak(text, mode);
        promise.resolve("Speak");
    }

    @ReactMethod
    public void resume(final Promise promise) {
        HmsTextToSpeechManager.getInstance().getMlTtsEngine().resume();
        promise.resolve("Resume");
    }

    @ReactMethod
    public void stop(final Promise promise) {
        HmsTextToSpeechManager.getInstance().getMlTtsEngine().stop();
        promise.resolve("Stop");
    }

    @ReactMethod
    public void pause(final Promise promise) {
        HmsTextToSpeechManager.getInstance().getMlTtsEngine().pause();
        promise.resolve("pause");
    }

    @ReactMethod
    public void shutdown(final Promise promise) {
        HmsTextToSpeechManager.getInstance().getMlTtsEngine().shutdown();
        promise.resolve("shutdown");
    }

    @ReactMethod
    public void updateConfig(ReadableMap rm, final Promise promise) {
        HmsTextToSpeechManager.getInstance().setConfig(rm);
        HmsTextToSpeechManager.getInstance().getMlTtsEngine().updateConfig(HmsTextToSpeechManager.getInstance().getConfig());
        promise.resolve("config update");
    }

    @ReactMethod
    public void getLanguage(final Promise promise) {
        promise.resolve(HmsTextToSpeechManager.getInstance().getConfig().getLanguage());
    }

    @ReactMethod
    public void getPerson(final Promise promise) {
        promise.resolve(HmsTextToSpeechManager.getInstance().getConfig().getPerson());
    }

    @ReactMethod
    public void getSpeed(final Promise promise) {
        promise.resolve(HmsTextToSpeechManager.getInstance().getConfig().getSpeed());
    }

    @ReactMethod
    public void getVolume(final Promise promise) {
        promise.resolve(HmsTextToSpeechManager.getInstance().getConfig().getVolume());
    }

    @ReactMethod
    public void setTtsCallback(final Promise promise) {
        HmsTextToSpeechManager.getInstance().getMlTtsEngine().setTtsCallback(new MLTtsCallback() {
            @Override
            public void onError(String s, MLTtsError mlTtsError) {
                WritableMap map = Arguments.createMap();
                map.putString("s", s);
                map.putString("errorMessage", mlTtsError.getErrorMsg());
                map.putInt("errorId", mlTtsError.getErrorId());
                map.putString("full", mlTtsError.toString());
                sendEvent(getReactApplicationContext(), "ttsCallback", map);
            }

            @Override
            public void onWarn(String s, MLTtsWarn mlTtsWarn) {
                WritableMap map = Arguments.createMap();
                map.putString("s", s);
                map.putString("warningMessage", mlTtsWarn.getWarnMsg());
                map.putInt("warningId", mlTtsWarn.getWarnId());
                map.putString("full", mlTtsWarn.toString());
                sendEvent(getReactApplicationContext(), "ttsCallback", map);
            }

            @Override
            public void onRangeStart(String s, int i, int i1) {
                WritableMap map = Arguments.createMap();
                map.putString("s", s);
                map.putInt("i", i);
                map.putInt("i", i1);
                sendEvent(getReactApplicationContext(), "ttsCallback", map);
            }

            @Override
            public void onEvent(String s, int i, Bundle bundle) {
                WritableMap map = Arguments.createMap();
                map.putString("s", s);
                map.putInt("i", i);
                sendEvent(getReactApplicationContext(), "ttsCallback", map);
            }
        });
        promise.resolve("Callback set");
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

}
