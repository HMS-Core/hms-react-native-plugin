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
package com.huawei.hms.rn.ml.frame;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.rn.ml.utils.RNUtils;


public class HmsFrameManager {
    private static volatile HmsFrameManager instance;
    private static final String TAG = HmsFrameManager.class.getSimpleName();
    private MLFrame frame;
    private MLFrame.Property.Ext propertyExt;
    private Bitmap mbitmap;

    public static HmsFrameManager getInstance() {
        if (instance == null)
            instance = new HmsFrameManager();
        return instance;
    }

    public void setFrame(MLFrame mlFrame) {
        frame = mlFrame;
    }

    public MLFrame getFrame() {
        return frame;
    }

    public MLFrame.Property.Ext propertyCreator(ReadableMap readableMap) {
        int lensId = 0;
        int maxZoom = 0;
        int zoom = 0;
        int bottom = 0;
        int left = 0;
        int right = 0;
        int top = 0;

        if (readableMap == null) {
            Log.i(TAG, "MLFrameProperty object is created using default options.");
            return new MLFrame.Property.Ext.Creator().build();
        }
        if (RNUtils.hasValidKey(readableMap, "lensId", ReadableType.Number)) {
            lensId = readableMap.getInt("lensId");
            Log.i(TAG, "lensId option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "maxZoom", ReadableType.Number)) {
            maxZoom = readableMap.getInt("maxZoom");
            Log.i(TAG, "maxZoom option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "zoom", ReadableType.Number)) {
            zoom = readableMap.getInt("zoom");
            Log.i(TAG, "zoom option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "bottom", ReadableType.Number)) {
            bottom = readableMap.getInt("bottom");
            Log.i(TAG, "bottom option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "left", ReadableType.Number)) {
            left = readableMap.getInt("left");
            Log.i(TAG, "left option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "right", ReadableType.Number)) {
            right = readableMap.getInt("right");
            Log.i(TAG, "right option set.");
        }
        if (RNUtils.hasValidKey(readableMap, "top", ReadableType.Number)) {
            top = readableMap.getInt("top");
            Log.i(TAG, "top option set.");
        }
        return new MLFrame.Property.Ext.Creator()
                .setLensId(lensId)
                .setMaxZoom(maxZoom)
                .setRect(new Rect(left, top, right, bottom))
                .setZoom(zoom)
                .build();
    }
}
