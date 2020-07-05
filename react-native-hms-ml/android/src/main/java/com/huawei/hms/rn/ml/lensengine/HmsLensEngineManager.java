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

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import com.huawei.hms.mlsdk.common.LensEngine;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.rn.ml.utils.RNUtils;


public class HmsLensEngineManager {
    private static final String TAG = HmsLensEngineManager.class.getSimpleName();
    private static volatile HmsLensEngineManager instance;
    private LensEngine mLensEngine;
    private MLAnalyzer currentAnalyzer;

    public static HmsLensEngineManager getInstance() {
        if (instance == null)
            instance = new HmsLensEngineManager();
        return instance;
    }

    public void setmLensEngine(ReadableMap readableMap, Context context, int analyzerTag) {
        currentAnalyzer = RNUtils.findAnalyzerByTag(analyzerTag);
        mLensEngine = createLensEngine(readableMap, context, currentAnalyzer);
    }

    public MLAnalyzer getCurrentAnalyzer() {
        return currentAnalyzer;
    }

    public LensEngine getmLensEngine() {
        return mLensEngine;
    }

    public LensEngine createLensEngine(ReadableMap readableMap, Context context, MLAnalyzer analyzer) {
        int width = 480;
        int height = 640;
        int lensType = LensEngine.BACK_LENS;
        float fps = 20.0f;
        boolean automaticFocus = true;
        String flashMode = "auto";
        String focusMode = "continuous-video";

        Log.i(TAG, "LensEngine object is being created...");

        if (readableMap == null) {
            Log.i(TAG, "LensEngine object is created using default options.");
            return new LensEngine.Creator(context, analyzer).create();
        }
        if (RNUtils.hasValidKey(readableMap, "flashMode", ReadableType.String)) {
            flashMode = readableMap.getString("flashMode");
            Log.i(TAG, "flashMode option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "focusMode", ReadableType.String)) {
            focusMode = readableMap.getString("focusMode");
            Log.i(TAG, "focusMode option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "automaticFocus", ReadableType.Boolean)) {
            automaticFocus = readableMap.getBoolean("automaticFocus");
            Log.i(TAG, "automaticFocus option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "width", ReadableType.Number)) {
            width = readableMap.getInt("width");
            Log.i(TAG, "width option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "height", ReadableType.Number)) {
            height = readableMap.getInt("height");
            Log.i(TAG, "height option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "fps", ReadableType.Number)) {
            fps = readableMap.getInt("fps");
            Log.i(TAG, "fps option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "lensType", ReadableType.Number)) {
            lensType = readableMap.getInt("lensType");
            Log.i(TAG, "lensType option set.");
        }

        Log.i(TAG, "LensEngine object is created.");
        return new LensEngine.Creator(context, analyzer)
                .applyDisplayDimension(width, height)
                .applyFps(fps)
                .enableAutomaticFocus(automaticFocus)
                .setFlashMode(flashMode)
                .setFocusMode(focusMode)
                .setLensType(lensType)
                .create();
    }

    public String getImagePathFromUri(Context inContext, Bitmap inImage) {
        if (inImage != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            return MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        }
        return null;
    }

}
