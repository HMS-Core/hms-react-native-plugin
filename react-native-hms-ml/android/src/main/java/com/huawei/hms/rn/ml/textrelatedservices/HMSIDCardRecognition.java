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
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.IDCARD_IMAGE_SAVE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.IDCARD_PLUGIN_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CANCEL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.DENY;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCapture;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureConfig;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureFactory;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureResult;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzer;
import com.huawei.hms.mlsdk.card.icr.MLIdCard;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSBackgroundTasks;

import java.io.IOException;

public class HMSIDCardRecognition extends HMSBase {
    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSIDCardRecognition(ReactApplicationContext reactContext) {
        super(reactContext, HMSIDCardRecognition.class.getSimpleName(), IDCARD_PLUGIN_CONSTANTS);
    }

    @ReactMethod
    public void captureCamera(boolean save, boolean isFront, Promise promise) {
        startMethodExecTimer("captureCamera");
        try {
            Activity currentActivity = getCurrentActivity();

            MLCnIcrCaptureConfig config = new MLCnIcrCaptureConfig.Factory()
                    .setFront(isFront)
                    .create();
            MLCnIcrCapture icrCapture = MLCnIcrCaptureFactory.getInstance().getIcrCapture(config);
            icrCapture.capture(callbackResult(save, isFront, promise), currentActivity);
        } catch (Exception e) {
            handleResult("captureCamera", e, promise);
        }
    }

    @ReactMethod
    public void captureImage(String imageUri, boolean isFront, Promise promise) {
        startMethodExecTimer("captureImage");
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(imageUri));

            MLCnIcrCaptureConfig config = new MLCnIcrCaptureConfig.Factory()
                    .setFront(isFront)
                    .create();
            MLCnIcrCapture icrCapture = MLCnIcrCaptureFactory.getInstance().getIcrCapture(config);
            icrCapture.captureImage(bitmap, callbackResult(false, isFront, promise));
        } catch (Exception e) {
            handleResult("captureImage", e, promise);
        }
    }

    @ReactMethod
    public void asyncAnalyzerImageOnDevice(boolean isStop, String imageUri, boolean isFront, Promise promise) {
        startMethodExecTimer("asyncAnalyzerImageOnDevice");
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(imageUri));
            MLFrame frame = MLFrame.fromBitmap(bitmap);

            MLIcrAnalyzer analyzer = HMSObjectCreator.getInstance().createICRAnalyzer("CN", isFront);
            Task<MLIdCard> task = analyzer.asyncAnalyseFrame(frame);

            task.addOnSuccessListener(mlIdCard -> {
                // Recognition success.
                WritableMap wm = HMSResultCreator.getInstance().getICRResult(mlIdCard, true);
                if (isStop) {
                    try {
                        analyzer.stop();
                    } catch (IOException e) {
                        handleResult("asyncAnalyzerImageOnDevice", e, promise);
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
    public void analyzerImageOnDevice(boolean isStop, String imageUri, boolean isFront, Promise promise) {
        startMethodExecTimer("analyzerImageOnDevice");
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(imageUri));
            MLFrame frame = MLFrame.fromBitmap(bitmap);

            MLIcrAnalyzer analyzer = HMSObjectCreator.getInstance().createICRAnalyzer("CN", isFront);
            SparseArray<MLIdCard> array = analyzer.analyseFrame(frame);
            int key = array.keyAt(0);
            MLIdCard mlIdCard = array.get(key);
            WritableMap wm = HMSResultCreator.getInstance().getICRResult(mlIdCard, true);
            if (isStop) {
                try {
                    analyzer.stop();
                } catch (IOException ex) {
                    handleResult("analyzerImageOnDevice", ex, promise);
                }
            }
            handleResult("analyzerImageOnDevice", wm, promise);
        } catch (Exception e) {
            handleResult("analyzerImageOnDevice", e, promise);
        }
    }

    /**
     * Result Callback
     *
     * @param promise A Promise that resolves a result object
     * @param isFront Is front side id card
     * @param save save to device
     * @return MLCnIcrCapture.Callback
     */
    private MLCnIcrCapture.CallBack callbackResult(boolean save, boolean isFront, Promise promise) {
        return new MLCnIcrCapture.CallBack() {
            // Identify successful processing.
            @Override
            public void onSuccess(MLCnIcrCaptureResult idCardResult) {
                Log.i("SUCCESS", "IdCallBack onRecSuccess");
                if (idCardResult == null) {
                    Log.i("ERR", "IdCallBack onRecSuccess idCardResult is null");
                    return;
                }

                if (save) {
                    HMSBackgroundTasks.getInstance()
                            .saveImageAndGetUri(getContext(), idCardResult.cardBitmap)
                            .addOnSuccessListener(
                                    s -> sendEvent(IDCARD_IMAGE_SAVE, "onResult", HMSResultCreator.getInstance().getIDCardImage(s, isFront)))
                            .addOnFailureListener(
                                    e -> sendEvent(IDCARD_IMAGE_SAVE, "onResult", FAILURE.getStatusAndMessage(null, e.getMessage())));
                }

                WritableMap cardResultFront = HMSResultCreator.getInstance().getFormatIdCardResult(idCardResult, isFront);
                handleResult("MLCnIcrCapture.Callback", cardResultFront, promise);
            }

            // User cancellation processing.
            @Override
            public void onCanceled() {
                handleResult("MLCnIcrCapture.Callback", CANCEL, promise);
            }

            // Identify failure processing.
            @Override
            public void onFailure(int retCode, Bitmap bitmap) {
                handleResult("MLCnIcrCapture.Callback", FAILURE.getStatusAndMessage(retCode, null), promise);
            }

            // Camera unavailable processing, the reason that the camera is unavailable is generally that the user has not been granted camera permissions.
            @Override
            public void onDenied() {
                handleResult("MLCnIcrCapture.Callback", DENY, promise);
            }
        };
    }
}
