/*
    Copyright 2020-2022. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.ml.imagerelatedservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.FRAME_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSBackgroundTasks;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;

public class HMSFrame extends HMSBase {

    /**
     * Sets module name and frame constants
     *
     * @param reactContext context
     */
    public HMSFrame(ReactApplicationContext reactContext) {
        super(reactContext, HMSFrame.class.getSimpleName(), FRAME_CONSTANTS);
    }

    /**
     * Obtains bitmap data of the preview image.
     *
     * @param frameConfiguration configuration to obtain frame
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getPreviewBitmap(ReadableMap frameConfiguration, final Promise promise) {
        startMethodExecTimer("getPreviewBitmap");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("getPreviewBitmap", FRAME_NULL, promise);
            return;
        }

        HMSBackgroundTasks.getInstance()
            .saveImageAndGetUri(getContext(), frame.getPreviewBitmap())
            .addOnSuccessListener(
                string -> handleResult("getPreviewBitmap", HMSResultCreator.getInstance().getStringResult(string),
                    promise))
            .addOnFailureListener(e -> handleResult("getPreviewBitmap", e, promise));
    }

    /**
     * Obtains bitmap data of a converted image.
     *
     * @param frameConfiguration configuration to obtain frame
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void readBitmap(ReadableMap frameConfiguration, final Promise promise) {
        startMethodExecTimer("readBitmap");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("readBitmap", FRAME_NULL, promise);
            return;
        }

        HMSBackgroundTasks.getInstance()
            .saveImageAndGetUri(getContext(), frame.readBitmap())
            .addOnSuccessListener(
                s -> handleResult("readBitmap", HMSResultCreator.getInstance().getStringResult(s), promise))
            .addOnFailureListener(e -> handleResult("readBitmap", e, promise));
    }

    /**
     * Rotates the bitmap of a preview image based on the screen orientation.
     *
     * @param quadrant screen quadrant
     * @param fileUri image uri
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void rotate(int quadrant, String fileUri, final Promise promise) {
        startMethodExecTimer("rotate");
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(fileUri));
            HMSBackgroundTasks.getInstance()
                .saveImageAndGetUri(getContext(), MLFrame.rotate(bitmap, quadrant))
                .addOnSuccessListener(
                    s -> handleResult("rotate", HMSResultCreator.getInstance().getStringResult(s), promise))
                .addOnFailureListener(e -> handleResult("rotate", e, promise));
        } catch (IOException e) {
            handleResult("rotate", e, promise);
        }
    }
}
