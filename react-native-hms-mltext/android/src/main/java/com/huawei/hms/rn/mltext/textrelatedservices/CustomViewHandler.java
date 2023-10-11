/*
 * Copyright 2023. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.hms.rn.mltext.textrelatedservices;


import static com.huawei.hms.rn.mltext.helpers.constants.HMSConstants.BCR_IMAGE_SAVE;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSConstants.BCR_PLUGIN_CONSTANTS;
import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.FAILURE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.huawei.hms.mlplugin.card.bcr.CustomView;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureResult;
import com.huawei.hms.rn.mltext.HMSBase;
import com.huawei.hms.rn.mltext.R;
import com.huawei.hms.rn.mltext.helpers.constants.HMSResults;
import com.huawei.hms.rn.mltext.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.mltext.helpers.utils.HMSBackgroundTasks;
import com.huawei.hms.rn.mltext.helpers.utils.HMSUtils;
import com.huawei.hms.rn.mltext.helpers.views.CustomViewActivity;

public class CustomViewHandler extends HMSBase implements ActivityEventListener {
    private ReactContext mReactContext;
    private Promise mPromise;
    private static MLBcrCaptureResult cardResult;
    private final int[] img = {R.drawable.flash_light_on, R.drawable.flash_light_off};

    private static final int REQUEST_CODE_SCAN_CUSTOMIZED = 14;
    @SuppressLint("StaticFieldLeak")
    private static CustomView remoteView;
    @SuppressLint("StaticFieldLeak")
    private static ImageView flashImage;

    public static void setViews(CustomView remoteView, ImageView flashImage) {
        CustomViewHandler.remoteView = remoteView;
        CustomViewHandler.flashImage = flashImage;
    }

    public static void setCardResult(MLBcrCaptureResult result) {
        cardResult = result;
    }

    public CustomViewHandler(@NonNull ReactApplicationContext reactContext) {
        super(reactContext, CustomViewActivity.class.getSimpleName(), BCR_PLUGIN_CONSTANTS);
        this.mReactContext = reactContext;
        mReactContext.addActivityEventListener(this);
    }

    /**
     * Start the camera with custom UI to capture bank card according to given configurations and sets callback
     *
     * @param bcrCustomCaptureConfiguration capture and UI configurations
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void startCustomizedView(final ReadableMap bcrCustomCaptureConfiguration, final Promise promise) {
        mPromise = promise;

        int resultType = 0;
        if (HMSUtils.getInstance().hasValidKey(bcrCustomCaptureConfiguration, "resultType", ReadableType.Number)) {
            resultType = bcrCustomCaptureConfiguration.getInt("resultType");
        }

        int recMode = 0;
        if (HMSUtils.getInstance().hasValidKey(bcrCustomCaptureConfiguration, "recMode", ReadableType.Number)) {
            recMode = bcrCustomCaptureConfiguration.getInt("recMode");
        }

        boolean isFlashAvailable = false;
        if (HMSUtils.getInstance().hasValidKey(bcrCustomCaptureConfiguration, "isFlashAvailable", ReadableType.Boolean)) {
            isFlashAvailable = bcrCustomCaptureConfiguration.getBoolean("isFlashAvailable");
        }

        boolean isTitleAvailable = true;
        if (HMSUtils.getInstance().hasValidKey(bcrCustomCaptureConfiguration, "isTitleAvailable", ReadableType.Boolean)) {
            isTitleAvailable = bcrCustomCaptureConfiguration.getBoolean("isTitleAvailable");
        }

        String title = "Place the card within the frame";
        if (HMSUtils.getInstance().hasValidKey(bcrCustomCaptureConfiguration, "title", ReadableType.String)) {
            title = bcrCustomCaptureConfiguration.getString("title");
        }
        double heightFactor = 0.63084F;
        if (HMSUtils.getInstance().hasValidKey(bcrCustomCaptureConfiguration, "heightFactor", ReadableType.Number)) {
            heightFactor = bcrCustomCaptureConfiguration.getDouble("heightFactor");
        }

        double widthFactor = 0.8F;
        if (HMSUtils.getInstance().hasValidKey(bcrCustomCaptureConfiguration, "widthFactor", ReadableType.Number)) {
            widthFactor = bcrCustomCaptureConfiguration.getDouble("widthFactor");
        }

        Intent intent = new Intent(mReactContext, CustomViewActivity.class);

        intent.putExtra("widthFactor", widthFactor);
        intent.putExtra("heightFactor", heightFactor);
        intent.putExtra("resultType", resultType);
        intent.putExtra("recMode", recMode);
        intent.putExtra("isFlashAvailable", isFlashAvailable);
        intent.putExtra("isTitleAvailable", isTitleAvailable);
        intent.putExtra("title", title);

        mReactContext.getCurrentActivity().startActivityForResult(intent, REQUEST_CODE_SCAN_CUSTOMIZED);
    }

    /**
     * Turn the flash light on or off
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void switchLight(final Promise promise) {
        if (remoteView != null) {
            remoteView.switchLight();
            if (remoteView.getLightStatus()) {
                flashImage.setImageResource(img[1]);
            } else {
                flashImage.setImageResource(img[0]);
            }
        } else {
            promise.reject(HMSResults.CUSTOM_VIEW_ERROR.getStringErrCode(), HMSResults.CUSTOM_VIEW_ERROR.getMessage());
        }
    }

    /**
     * Return the status of the the flash light
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getLightStatus(final Promise promise) {
        if (remoteView != null) {
            promise.resolve(remoteView.getLightStatus());
        } else {
            promise.reject(HMSResults.CUSTOM_VIEW_ERROR.getStringErrCode(), HMSResults.CUSTOM_VIEW_ERROR.getMessage());
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_SCAN_CUSTOMIZED) {
            if (resultCode == Activity.RESULT_OK && cardResult != null) {
                HMSBackgroundTasks.getInstance()
                        .saveImageAndGetUri(getReactApplicationContext(), cardResult.getNumberBitmap())
                        .addOnSuccessListener(
                                s -> sendEvent(BCR_IMAGE_SAVE, "onSuccess", HMSResultCreator.getInstance().getStringResult(s)))
                        .addOnFailureListener(
                                e -> sendEvent(BCR_IMAGE_SAVE, "onFailure", FAILURE.getStatusAndMessage(null, e.getMessage())));

                handleResult("MLBcrCapture.Callback",
                        HMSResultCreator.getInstance().getBankCardRecognitionSuccessResults(cardResult),
                        mPromise);
                mPromise = null;
            } else if (resultCode == Activity.RESULT_CANCELED && cardResult != null) {
                handleResult("MLBcrCapture.Callback", FAILURE.getStatusAndMessage(cardResult.getErrorCode(), null), mPromise);
                mPromise = null;
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @NonNull
    @Override
    public String getName() {
        return "CustomViewHandler";
    }
}