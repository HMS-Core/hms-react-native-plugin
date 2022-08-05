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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.ICRVN_IMAGE_SAVE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.ICRVN_PLUGIN_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CANCEL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.DENY;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlplugin.card.icr.vn.MLVnIcrCapture;
import com.huawei.hms.mlplugin.card.icr.vn.MLVnIcrCaptureConfig;
import com.huawei.hms.mlplugin.card.icr.vn.MLVnIcrCaptureFactory;
import com.huawei.hms.mlplugin.card.icr.vn.MLVnIcrCaptureResult;
import com.huawei.hms.mlsdk.card.MLCardAnalyzerFactory;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzer;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzerSetting;
import com.huawei.hms.mlsdk.card.icr.MLIdCard;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.rn.ml.HMSBase;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSBackgroundTasks;

import java.io.IOException;

public class HMSVietnamCardRecognition extends HMSBase {
    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSVietnamCardRecognition(ReactApplicationContext reactContext) {
        super(reactContext, HMSVietnamCardRecognition.class.getSimpleName(), ICRVN_PLUGIN_CONSTANTS);
    }

    /**
     * Enables the plug-in for recognizing static images of vietnam id card.
     *
     * @param imageUri image uri
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void captureImage(String imageUri, Promise promise) {
        startMethodExecTimer("captureImage");
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(imageUri));

            MLVnIcrCaptureConfig config = new MLVnIcrCaptureConfig.Factory().create();
            MLVnIcrCapture icrCapture = MLVnIcrCaptureFactory.getInstance().getIcrCapture(config);
            icrCapture.captureImage(bitmap, callbackResult(false, promise));
        } catch (Exception e) {
            handleResult("captureImage", e, promise);
        }
    }

    @ReactMethod
    public void asyncAnalyzerImageOnDevice(boolean isStop, String imageUri, Promise promise) {
        startMethodExecTimer("asyncAnalyzerImageOnDevice");
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(imageUri));
            MLFrame frame = MLFrame.fromBitmap(bitmap);

            MLIcrAnalyzer analyzer = HMSObjectCreator.getInstance().createICRAnalyzer("VN", true);
            Task<MLIdCard> task = analyzer.asyncAnalyseFrame(frame);
            task.addOnSuccessListener(mlIdCard -> {
                // Recognition success.
                WritableMap wm = HMSResultCreator.getInstance().getICRResult(mlIdCard, true);
                if (isStop) {
                    try {
                        analyzer.stop();
                    } catch (IOException ex) {
                        handleResult("asyncAnalyzerImageOnDevice", ex, promise);
                    }
                }
                handleResult("asyncAnalyzerImageOnDevice", wm, promise);
            }).addOnFailureListener(e -> {
                // Recognition failure.
                if (isStop) {
                    try {
                        analyzer.stop();
                    } catch (IOException ex) {
                        handleResult("asyncAnalyzerImageOnDevice", ex, promise);
                    }
                }
                handleResult("asyncAnalyzerImageOnDevice", e, promise);
            });
        } catch (Exception e) {
            handleResult("asyncAnalyzerImageOnDevice", e, promise);
        }
    }

    @ReactMethod
    public void analyzerImageOnDevice(boolean isStop, String imageUri, Promise promise) {
        startMethodExecTimer("analyzerImageOnDevice");
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(imageUri));
            MLFrame frame = MLFrame.fromBitmap(bitmap);

            MLIcrAnalyzer analyzer = HMSObjectCreator.getInstance().createICRAnalyzer("VN", true);

            SparseArray<MLIdCard> array = analyzer.analyseFrame(frame);
            int key = array.keyAt(0);
            MLIdCard mlIdCard = array.get(key);
            WritableMap wm = HMSResultCreator.getInstance().getICRResult(mlIdCard, true);
            handleResult("analyzerImageOnDevice", wm, promise);
        } catch (Exception e) {
            handleResult("analyzerImageOnDevice", e, promise);
        }
    }

    /**
     * Enables the plugin to recognize the Vietnamese ID card with the camera.
     *
     * @param save Save capture
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void captureCamera(boolean save, Promise promise) {
        startMethodExecTimer("captureCamera");
        Activity currentActivity = getCurrentActivity();

        try {
            MLVnIcrCaptureConfig config = new MLVnIcrCaptureConfig.Factory().create();
            MLVnIcrCapture icrCapture = MLVnIcrCaptureFactory.getInstance().getIcrCapture(config);
            icrCapture.capture(callbackResult(save, promise), currentActivity);
        } catch (Exception e) {
            handleResult("captureCamera", e, promise);
        }
    }

    /**
     * Result Callback
     *
     * @param promise A Promise that resolves a result object
     * @param save Save capture
     * @return MLVnIcrCapture.CallBack
     */
    private MLVnIcrCapture.CallBack callbackResult(boolean save, Promise promise) {
        return new MLVnIcrCapture.CallBack() {
            // Identify successful processing.
            @Override
            public void onSuccess(MLVnIcrCaptureResult idCardResult) {
                Log.i("SUCCESS", "IdCallBack onRecSuccess");
                if (idCardResult == null) {
                    Log.i("ERR", "IdCallBack onRecSuccess idCardResult is null");
                    return;
                }

                if (save) {
                    HMSBackgroundTasks.getInstance()
                            .saveImageAndGetUri(getContext(), idCardResult.getCardBitmap())
                            .addOnSuccessListener(
                                    s -> sendEvent(ICRVN_IMAGE_SAVE, "onResult", HMSResultCreator.getInstance().getStringResult(s)))
                            .addOnFailureListener(
                                    e -> sendEvent(ICRVN_IMAGE_SAVE, "onResult", FAILURE.getStatusAndMessage(null, e.getMessage())));
                }

                WritableMap cardResultFront = HMSResultCreator.getInstance().getVNFormatIdCardResult(idCardResult);
                handleResult("MLVnIcrCapture.Callback", cardResultFront, promise);
            }

            // User cancellation processing.
            @Override
            public void onCanceled() {
                handleResult("MLVnIcrCapture.Callback", CANCEL, promise);
            }

            // Identify failure processing.
            @Override
            public void onFailure(int retCode, Bitmap bitmap) {
                handleResult("MLVnIcrCapture.Callback", FAILURE.getStatusAndMessage(retCode, null), promise);
            }

            // Camera unavailable processing, the reason that the camera is unavailable is generally that the user has not been granted camera permissions.
            @Override
            public void onDenied() {
                handleResult("MLVnIcrCapture.Callback", DENY, promise);
            }
        };
    }
}
