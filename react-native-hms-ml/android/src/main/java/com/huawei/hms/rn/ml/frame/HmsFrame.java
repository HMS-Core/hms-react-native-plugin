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

import java.io.IOException;
import java.util.Map;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.rn.ml.constants.Constants;

import static com.huawei.hms.rn.ml.constants.Errors.E_FRAME_MODULE;


public class HmsFrame extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsFrame.class.getSimpleName();

    public HmsFrame(ReactApplicationContext context) {
        super(context);
    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return Constants.getFrameConstants();
    }

    @ReactMethod
    public void fromBitmap(String uri, Promise promise) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getReactApplicationContext().getContentResolver(),
                    Uri.parse(uri));
            HmsFrameManager.getInstance().setFrame(MLFrame.fromBitmap(bitmap));
            promise.resolve("Frame set by fromBitmap");
        } catch (IOException e) {
            promise.reject(E_FRAME_MODULE, e.getMessage());
        }
    }

    @ReactMethod
    public void fromFilePath(String uri, Promise promise) {
        try {
            HmsFrameManager.getInstance().setFrame(MLFrame.fromFilePath(getReactApplicationContext(), Uri.parse(uri)));
            promise.resolve("Frame set by fromFilePath");
        } catch (IOException e) {
            promise.reject(E_FRAME_MODULE, e.getMessage());
        }
    }

}
