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

package com.huawei.hms.rn.scan.customized;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huawei.hms.hmsscankit.RemoteView;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.rn.scan.R;
import com.huawei.hms.rn.scan.utils.Errors;

import static android.app.Activity.RESULT_OK;
import static com.huawei.hms.rn.scan.utils.ReactUtils.getIntegerArrayFromReadableArray;
import static com.huawei.hms.rn.scan.utils.ReactUtils.hasValidKey;
import static com.huawei.hms.rn.scan.utils.ReactUtils.toWM;

public class RNHMSScanCustomizedViewModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private ReactContext mReactContext;
    private Promise mPromise;
    private final Gson gson;
    private final int[] img = {R.drawable.flashlight_on, R.drawable.flashlight_off};

    private static final int REQUEST_CODE_SCAN_CUSTOMIZED = 14;
    @SuppressLint("StaticFieldLeak")
    private static RemoteView remoteView;
    @SuppressLint("StaticFieldLeak")
    private static ImageView flashButton;

    public static void setViews(RemoteView remoteView, ImageView flashButton) {
        RNHMSScanCustomizedViewModule.remoteView = remoteView;
        RNHMSScanCustomizedViewModule.flashButton = flashButton;
    }

    public RNHMSScanCustomizedViewModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
        mReactContext.addActivityEventListener(this);
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @NonNull
    @Override
    public String getName() {
        return "RNHMSScanCustomizedViewModule";
    }

    @ReactMethod
    public void startCustomizedView(final ReadableMap buildBitmapRequest, final Promise promise) {
        mPromise = promise;
        int scanType = 0;
        if (hasValidKey(buildBitmapRequest, "scanType", ReadableType.Number)) {
            scanType = buildBitmapRequest.getInt("scanType");
        }

        int[] additionalScanTypes = null;
        if (hasValidKey(buildBitmapRequest, "additionalScanTypes", ReadableType.Array)) {
            additionalScanTypes = getIntegerArrayFromReadableArray(buildBitmapRequest.getArray("additionalScanTypes"));
        }

        int rectWidth = 0;
        if (hasValidKey(buildBitmapRequest, "rectWidth", ReadableType.Number)) {
            rectWidth = buildBitmapRequest.getInt("rectWidth");
        }

        int rectHeight = 0;
        if (hasValidKey(buildBitmapRequest, "rectHeight", ReadableType.Number)) {
            rectHeight = buildBitmapRequest.getInt("rectHeight");
        }

        boolean isGalleryAvailable = false;
        if (hasValidKey(buildBitmapRequest, "isGalleryAvailable", ReadableType.Boolean)) {
            isGalleryAvailable = buildBitmapRequest.getBoolean("isGalleryAvailable");
        }
        boolean flashOnLightChange = false;
        if (hasValidKey(buildBitmapRequest, "flashOnLightChange", ReadableType.Boolean)) {
            flashOnLightChange = buildBitmapRequest.getBoolean("flashOnLightChange");
        }
        boolean isFlashAvailable = false;
        if (hasValidKey(buildBitmapRequest, "isFlashAvailable", ReadableType.Boolean)) {
            isFlashAvailable = buildBitmapRequest.getBoolean("isFlashAvailable");
        }
        boolean continuouslyScan = false;
        if (hasValidKey(buildBitmapRequest, "continuouslyScan", ReadableType.Boolean)) {
            continuouslyScan = buildBitmapRequest.getBoolean("continuouslyScan");
        }
        boolean enableReturnOriginalScan = false;
        if (hasValidKey(buildBitmapRequest, "enableReturnOriginalScan", ReadableType.Boolean)) {
            enableReturnOriginalScan = buildBitmapRequest.getBoolean("enableReturnOriginalScan");
        }

        Intent intent = new Intent(mReactContext, CustomizedViewActivity.class);

        intent.putExtra("scanType", scanType);
        if (additionalScanTypes != null) {
            intent.putExtra("additionalScanTypes", additionalScanTypes);
        }
        intent.putExtra("rectWidth", rectWidth);
        intent.putExtra("rectHeight", rectHeight);

        intent.putExtra("flashOnLightChange", flashOnLightChange);
        intent.putExtra("isFlashAvailable", isFlashAvailable);
        intent.putExtra("isGalleryAvailable", isGalleryAvailable);
        intent.putExtra("continuouslyScan", continuouslyScan);
        intent.putExtra("enableReturnOriginalScan", enableReturnOriginalScan);


        mReactContext.getCurrentActivity().startActivityForResult(intent, REQUEST_CODE_SCAN_CUSTOMIZED);
    }

    @ReactMethod
    public void pauseContinuouslyScan(final Promise promise) {
        if (remoteView != null) {
            remoteView.pauseContinuouslyScan();
            promise.resolve(true);
        } else {
            promise.reject(Errors.REMOTE_VIEW_ERROR.getErrorCode(), Errors.REMOTE_VIEW_ERROR.getErrorMessage());
        }
    }

    @ReactMethod
    public void resumeContinuouslyScan(final Promise promise) {
        if (remoteView != null) {
            remoteView.resumeContinuouslyScan();
            promise.resolve(true);
        } else {
            promise.reject(Errors.REMOTE_VIEW_ERROR.getErrorCode(), Errors.REMOTE_VIEW_ERROR.getErrorMessage());
        }
    }

    @ReactMethod
    public void switchLight(final Promise promise) {
        if (remoteView != null) {
            remoteView.switchLight();
            if (remoteView.getLightStatus()) {
                flashButton.setImageResource(img[1]);
            } else {
                flashButton.setImageResource(img[0]);
            }
        } else {
            promise.reject(Errors.REMOTE_VIEW_ERROR.getErrorCode(), Errors.REMOTE_VIEW_ERROR.getErrorMessage());
        }
    }

    @ReactMethod
    public void getLightStatus(final Promise promise) {
        if (remoteView != null) {
            promise.resolve(remoteView.getLightStatus());
        } else {
            promise.reject(Errors.REMOTE_VIEW_ERROR.getErrorCode(), Errors.REMOTE_VIEW_ERROR.getErrorMessage());
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_SCAN_CUSTOMIZED) {
            HmsScan hmsScan = data.getParcelableExtra(ScanUtil.RESULT);
            if (hmsScan != null && !TextUtils.isEmpty(hmsScan.getOriginalValue()) && mPromise != null) {
                mPromise.resolve(toWM(gson.toJson(hmsScan)));
                mPromise = null;
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
    }
}
