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
package com.huawei.hms.rn.ml.imseg;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.mlsdk.imgseg.MLImageSegmentation;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting;
import com.huawei.hms.rn.ml.utils.RNUtils;


public class HmsImageSegmentationManager {
    private static final String TAG = HmsImageSegmentationManager.class.getSimpleName();
    private static volatile HmsImageSegmentationManager instance;
    private MLImageSegmentationSetting imageSegmentationSetting;
    private MLImageSegmentationAnalyzer analyzer;

    public static HmsImageSegmentationManager getInstance() {
        if (instance == null)
            instance = new HmsImageSegmentationManager();
        return instance;
    }

    public void setImageSegmentationSetting(ReadableMap readableMap) {
        imageSegmentationSetting = createImageSegmentation(readableMap);
    }

    public MLImageSegmentationSetting getImageSegmentationSetting() {
        return imageSegmentationSetting;
    }

    public MLImageSegmentationAnalyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(MLImageSegmentationAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public MLImageSegmentationSetting createImageSegmentation(ReadableMap rm) {
        int analyzerType = 0;
        int scene = 0;
        boolean exact = true;

        if (rm == null) {
            Log.i(TAG, "MLImageSegmentationSetting object is created using default options.");
            return new MLImageSegmentationSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(rm, "analyzerType", ReadableType.Number)) {
            analyzerType = rm.getInt("analyzerType");
            Log.i(TAG, "analyzerType option set.");
        }
        if (RNUtils.hasValidKey(rm, "scene", ReadableType.Number)) {
            scene = rm.getInt("scene");
            Log.i(TAG, "borderType option set.");
        }
        if (RNUtils.hasValidKey(rm, "exact", ReadableType.Boolean)) {
            exact = rm.getBoolean("exact");
            Log.i(TAG, "exact option set.");
        }

        return new MLImageSegmentationSetting
                .Factory()
                .setScene(scene)
                .setAnalyzerType(analyzerType)
                .setExact(exact)
                .create();
    }

    public WritableMap imageSegmentationResult(Context context, MLImageSegmentation imageSegmentation) {
        WritableMap writableMap = Arguments.createMap();
        writableMap.putString("foreground", getImagePathFromUri(context, imageSegmentation.getForeground()));
        writableMap.putString("grayscale", getImagePathFromUri(context, imageSegmentation.getGrayscale()));
        writableMap.putString("original", getImagePathFromUri(context, imageSegmentation.getOriginal()));
        return writableMap;
    }

    private String getImagePathFromUri(Context inContext, Bitmap inImage) {
        if (inImage != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            return MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        }
        return null;
    }
}
