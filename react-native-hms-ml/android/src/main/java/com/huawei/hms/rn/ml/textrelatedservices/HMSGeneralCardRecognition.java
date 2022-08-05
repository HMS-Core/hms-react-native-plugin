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

package com.huawei.hms.rn.ml.textrelatedservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.GCR_IMAGE_SAVE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.GCR_PLUGIN_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CANCEL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CURRENT_ACTIVITY_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.DENY;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.huawei.hms.mlplugin.card.gcr.MLGcrCapture;
import com.huawei.hms.mlplugin.card.gcr.MLGcrCaptureResult;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSBackgroundTasks;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;

public class HMSGeneralCardRecognition extends HMSBase {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSGeneralCardRecognition(ReactApplicationContext reactContext) {
        super(reactContext, HMSGeneralCardRecognition.class.getSimpleName(), GCR_PLUGIN_CONSTANTS);
    }

    /**
     * Enables the plug-in for recognizing general cards in camera streams.
     *
     * @param language language code
     * @param uiConfiguration ui configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void capturePreview(String language, ReadableMap uiConfiguration, final Promise promise) {
        startMethodExecTimer("capturePreview");
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            handleResult("capturePreview", CURRENT_ACTIVITY_NULL, promise);
            return;
        }

        MLGcrCapture gcrManager = HMSObjectCreator.getInstance().createGcrCapture(language, uiConfiguration);
        gcrManager.capturePreview(currentActivity, null, callbackResult(promise));
    }

    /**
     * Enables the plug-in for taking a photo of a general card and recognizing the general card on the photo.
     *
     * @param language language code
     * @param uiConfiguration ui configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void capturePhoto(String language, ReadableMap uiConfiguration, final Promise promise) {
        startMethodExecTimer("capturePhoto");
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            handleResult("capturePhoto", CURRENT_ACTIVITY_NULL, promise);
            return;
        }

        MLGcrCapture gcrManager = HMSObjectCreator.getInstance().createGcrCapture(language, uiConfiguration);
        gcrManager.capturePhoto(currentActivity, null, callbackResult(promise));
    }

    /**
     * Enables the plug-in for recognizing static images of general cards.
     *
     * @param language language code
     * @param imageUri image uri
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void captureImage(String language, String imageUri, final Promise promise) {
        startMethodExecTimer("captureImage");
        MLGcrCapture gcrManager = HMSObjectCreator.getInstance().createGcrCapture(language, null);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(imageUri));
            gcrManager.captureImage(bitmap, null, callbackResult(promise));
        } catch (IOException e) {
            handleResult("captureImage", e, promise);
        }
    }

    /**
     * Result Callback
     *
     * @param promise A Promise that resolves a result object
     * @return MLGcrCapture.Callback
     */
    private MLGcrCapture.Callback callbackResult(Promise promise) {
        return new MLGcrCapture.Callback() {
            @Override
            public int onResult(MLGcrCaptureResult mlGcrCaptureResult, Object o) {
                if (mlGcrCaptureResult == null) {
                    return MLGcrCaptureResult.CAPTURE_CONTINUE;
                }

                HMSBackgroundTasks.getInstance()
                    .saveImageAndGetUri(getContext(), mlGcrCaptureResult.cardBitmap)
                    .addOnSuccessListener(
                        s -> sendEvent(GCR_IMAGE_SAVE, "onResult", HMSResultCreator.getInstance().getStringResult(s)))
                    .addOnFailureListener(
                        e -> sendEvent(GCR_IMAGE_SAVE, "onResult", FAILURE.getStatusAndMessage(null, e.getMessage())));

                handleResult("MLGcrCapture.Callback",
                    HMSResultCreator.getInstance().getGeneralCardRecognitionSuccessResult(mlGcrCaptureResult), promise);
                return MLGcrCaptureResult.CAPTURE_STOP;
            }

            @Override
            public void onCanceled() {
                handleResult("MLGcrCapture.Callback", CANCEL, promise);
            }

            @Override
            public void onFailure(int i, Bitmap bitmap) {
                handleResult("MLGcrCapture.Callback", FAILURE.getStatusAndMessage(i, null), promise);
            }

            @Override
            public void onDenied() {
                handleResult("MLGcrCapture.Callback", DENY, promise);
            }
        };
    }
}
