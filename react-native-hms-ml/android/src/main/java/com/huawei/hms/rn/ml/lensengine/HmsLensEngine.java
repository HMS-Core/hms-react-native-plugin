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

package com.huawei.hms.rn.ml.lensengine;

import java.io.IOException;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
import com.huawei.hms.rn.ml.constants.Constants;

import static com.huawei.hms.rn.ml.constants.Errors.E_LENS_ENGINE_MODULE;

public class HmsLensEngine extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsLensEngine.class.getSimpleName();

    public HmsLensEngine(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return Constants.getLensEngineConstants();
    }

    @ReactMethod
    public void create(ReadableMap readableMap, int analyzerTag, final Promise promise) {
        HmsLensEngineManager.getInstance().setmLensEngine(readableMap, getReactApplicationContext(), analyzerTag);
        promise.resolve("lensEngine create");
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    @ReactMethod
    public void photograph() {
        HmsLensEngineManager.getInstance().getmLensEngine().photograph(
                () -> sendEvent(getReactApplicationContext(), "clickShutter", null),
                bytes -> {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    WritableMap map = Arguments.createMap();
                    map.putString("imageURi", HmsLensEngineManager.getInstance().getImagePathFromUri(getReactApplicationContext(), bitmap));
                    sendEvent(getReactApplicationContext(), "takenPhotograph", map);
                });
    }

    @ReactMethod
    public void close(final Promise promise) {
        HmsLensEngineManager.getInstance().getmLensEngine().close();
        promise.resolve("LensEngine close");
    }

    @ReactMethod
    public void doZoom(float scale, final Promise promise) {
        HmsLensEngineManager.getInstance().getmLensEngine().doZoom(scale);
        promise.resolve("doZoom call");
    }

    @ReactMethod
    public void getDisplayDimension(final Promise promise) {
        WritableMap map = Arguments.createMap();
        map.putDouble("height", HmsLensEngineManager.getInstance().getmLensEngine().getDisplayDimension().getHeight());
        map.putDouble("width", HmsLensEngineManager.getInstance().getmLensEngine().getDisplayDimension().getWidth());
        promise.resolve(map);
    }

    @ReactMethod
    public void getLensType(final Promise promise) {
        promise.resolve(HmsLensEngineManager.getInstance().getmLensEngine().getLensType());
    }

    @ReactMethod
    public void release(final Promise promise) {
        HmsLensEngineManager.getInstance().getmLensEngine().release();
        promise.resolve("LensEngine released");
    }

    @ReactMethod
    public void run(final Promise promise) {
        try {
            HmsLensEngineManager.getInstance().getmLensEngine().run();
            promise.resolve("LensEngine run");
        } catch (IOException e) {
            promise.reject(E_LENS_ENGINE_MODULE, e.getMessage());
        }
    }

}
