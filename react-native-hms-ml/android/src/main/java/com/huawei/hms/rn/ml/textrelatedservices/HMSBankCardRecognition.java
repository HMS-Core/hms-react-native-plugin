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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.BCR_IMAGE_SAVE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.BCR_PLUGIN_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CANCEL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CURRENT_ACTIVITY_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.DENY;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;

import android.app.Activity;
import android.graphics.Bitmap;

import com.huawei.hms.mlplugin.card.bcr.MLBcrCapture;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureResult;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSBackgroundTasks;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class HMSBankCardRecognition extends HMSBase implements MLBcrCapture.Callback {
    private Promise captureResultPromise;

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSBankCardRecognition(ReactApplicationContext reactContext) {
        super(reactContext, HMSBankCardRecognition.class.getSimpleName(), BCR_PLUGIN_CONSTANTS);
    }

    /**
     * Start an activity to capture bank card according to given configurations and sets callback
     *
     * @param bcrCaptureConfiguration capture configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void captureFrame(ReadableMap bcrCaptureConfiguration, final Promise promise) {
        startMethodExecTimer("captureFrame");
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            handleResult("captureFrame", CURRENT_ACTIVITY_NULL, promise);
            return;
        }

        MLBcrCapture bcrCapture = HMSObjectCreator.getInstance().createBcrCapture(bcrCaptureConfiguration);
        captureResultPromise = promise;
        bcrCapture.captureFrame(currentActivity, this);
    }

    @Override
    public void onSuccess(MLBcrCaptureResult mlBcrCaptureResult) {
        HMSBackgroundTasks.getInstance()
            .saveImageAndGetUri(getContext(), mlBcrCaptureResult.getNumberBitmap())
            .addOnSuccessListener(
                s -> sendEvent(BCR_IMAGE_SAVE, "onSuccess", HMSResultCreator.getInstance().getStringResult(s)))
            .addOnFailureListener(
                e -> sendEvent(BCR_IMAGE_SAVE, "onSuccess", FAILURE.getStatusAndMessage(null, e.getMessage())));

        handleResult("MLBcrCapture.Callback",
            HMSResultCreator.getInstance().getBankCardRecognitionSuccessResults(mlBcrCaptureResult),
            captureResultPromise);
        captureResultPromise = null;
    }

    @Override
    public void onCanceled() {
        handleResult("MLBcrCapture.Callback", CANCEL, captureResultPromise);
        captureResultPromise = null;
    }

    @Override
    public void onFailure(int i, Bitmap bitmap) {
        handleResult("MLBcrCapture.Callback", FAILURE.getStatusAndMessage(i, null), captureResultPromise);
        captureResultPromise = null;
    }

    @Override
    public void onDenied() {
        handleResult("MLBcrCapture.Callback", DENY, captureResultPromise);
        captureResultPromise = null;
    }

}