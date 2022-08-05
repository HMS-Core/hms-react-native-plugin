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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.DSC_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import android.util.Log;
import android.util.SparseArray;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionAnalyzer;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionConstant;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionCoordinateInput;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionResult;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewDetectResult;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSBackgroundTasks;
import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;

public class HMSDocumentSkewCorrection extends HMSBase {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSDocumentSkewCorrection(ReactApplicationContext reactContext) {
        super(reactContext, HMSDocumentSkewCorrection.class.getSimpleName(), DSC_CONSTANTS);
    }

    /**
     * Synchronous calling entry for text box tilt detection
     *
     * @param isStop releases analyzer resources
     * @param frameConfiguration frame configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeFrame(boolean isStop, ReadableMap frameConfiguration, final Promise promise) {
        startMethodExecTimer("analyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("analyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLDocumentSkewCorrectionAnalyzer dscAnalyzer = HMSObjectCreator.getInstance()
            .createDocumentSkewCorrectionAnalyzer();
        SparseArray<MLDocumentSkewDetectResult> result = dscAnalyzer.analyseFrame(frame);

        if (isStop) {
            stopAnalyzer(dscAnalyzer);
        }

        if (result != null && result.get(0).getResultCode() == MLDocumentSkewCorrectionConstant.SUCCESS) {
            handleResult("analyzeFrame", HMSResultCreator.getInstance().getDocumentSkewDetectResults(result), promise);
        } else {
            handleResult("analyzeFrame", FAILURE, promise);
        }
    }

    /**
     * Asynchronous calling entry for text box tilt detection.
     *
     * @param isStop releases resources of analyzer
     * @param frameConfiguration frame configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncDocumentSkewDetect(boolean isStop, ReadableMap frameConfiguration, final Promise promise) {
        startMethodExecTimer("asyncDocumentSkewDetect");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncDocumentSkewDetect", FRAME_NULL, promise);
            return;
        }

        MLDocumentSkewCorrectionAnalyzer dscAnalyzer = HMSObjectCreator.getInstance()
            .createDocumentSkewCorrectionAnalyzer();
        dscAnalyzer.asyncDocumentSkewDetect(frame).addOnSuccessListener(result -> {
            if (isStop) {
                stopAnalyzer(dscAnalyzer);
            }

            if (result.getResultCode() == MLDocumentSkewCorrectionConstant.SUCCESS) {
                handleResult("asyncDocumentSkewDetect",
                    HMSResultCreator.getInstance().getDocumentSkewDetectAsyncResult(result), promise);
            } else if (result.getResultCode() == MLDocumentSkewCorrectionConstant.DETECT_FAILED) {
                handleResult("asyncDocumentSkewDetect",
                    FAILURE.getStatusAndMessage(MLDocumentSkewCorrectionConstant.DETECT_FAILED, "Detection Failure"),
                    promise);
            } else if (result.getResultCode() == MLDocumentSkewCorrectionConstant.IMAGE_DATA_ERROR) {
                handleResult("asyncDocumentSkewDetect",
                    FAILURE.getStatusAndMessage(MLDocumentSkewCorrectionConstant.IMAGE_DATA_ERROR, "Image Data Error"),
                    promise);
            } else {
                handleResult("asyncDocumentSkewDetect", FAILURE, promise);
            }
        }).addOnFailureListener(e -> {
            if (isStop) {
                stopAnalyzer(dscAnalyzer);
            }
            handleResult("asyncDocumentSkewDetect", e, promise);
        });
    }

    /**
     * Asynchronous calling entry for text box tilt correction.
     *
     * @param isStop releases resources of analyzer
     * @param frameConfiguration frame configuration
     * @param points points to be corrected
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncDocumentSkewCorrect(boolean isStop, ReadableMap frameConfiguration, ReadableArray points,
        final Promise promise) {
        startMethodExecTimer("asyncDocumentSkewCorrect");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncDocumentSkewCorrect", FRAME_NULL, promise);
            return;
        }

        if (points.size() == 0) {
            handleResult("asyncDocumentSkewCorrect", FAILURE.getStatusAndMessage(null, "Points array size is 0"),
                promise);
            return;
        }

        MLDocumentSkewCorrectionAnalyzer dscAnalyzer = HMSObjectCreator.getInstance()
            .createDocumentSkewCorrectionAnalyzer();
        dscAnalyzer.asyncDocumentSkewCorrect(frame,
            new MLDocumentSkewCorrectionCoordinateInput(HMSUtils.getInstance().convertRaToPointList(points)))
            .addOnSuccessListener(mlDocumentSkewCorrectionResult -> {
                if (isStop) {
                    stopAnalyzer(dscAnalyzer);
                }

                if (mlDocumentSkewCorrectionResult.getResultCode() == MLDocumentSkewCorrectionConstant.SUCCESS) {
                    HMSBackgroundTasks.getInstance()
                        .saveImageAndGetUri(getContext(), mlDocumentSkewCorrectionResult.getCorrected())
                        .addOnSuccessListener(string -> handleResult("asyncDocumentSkewCorrect",
                            HMSResultCreator.getInstance().getStringResult(string), promise))
                        .addOnFailureListener(e -> handleResult("asyncDocumentSkewCorrect", e, promise));
                } else if (mlDocumentSkewCorrectionResult.getResultCode()
                    == MLDocumentSkewCorrectionConstant.CORRECTION_FAILED) {
                    handleResult("asyncDocumentSkewCorrect",
                        FAILURE.getStatusAndMessage(MLDocumentSkewCorrectionConstant.CORRECTION_FAILED,
                            "Detection Failure"), promise);
                } else if (mlDocumentSkewCorrectionResult.getResultCode()
                    == MLDocumentSkewCorrectionConstant.IMAGE_DATA_ERROR) {
                    handleResult("asyncDocumentSkewCorrect",
                        FAILURE.getStatusAndMessage(MLDocumentSkewCorrectionConstant.IMAGE_DATA_ERROR,
                            "Image Data Error"), promise);
                } else {
                    handleResult("asyncDocumentSkewCorrect", FAILURE, promise);
                }
            })
            .addOnFailureListener(e -> {
                if (isStop) {
                    stopAnalyzer(dscAnalyzer);
                }
                handleResult("asyncDocumentSkewCorrect", e, promise);
            });
    }

    /**
     * Synchronous calling entry for text box tilt correction.
     *
     * @param isStop releases analyzer resources
     * @param frameConfiguration frame configuration
     * @param points points to be corrected
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void syncDocumentSkewCorrect(boolean isStop, ReadableMap frameConfiguration, ReadableArray points,
        final Promise promise) {
        startMethodExecTimer("syncDocumentSkewCorrect");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("syncDocumentSkewCorrect", FRAME_NULL, promise);
            return;
        }

        if (points.size() == 0) {
            handleResult("syncDocumentSkewCorrect", FAILURE.getStatusAndMessage(null, "Points array size is 0"),
                promise);
            return;
        }

        MLDocumentSkewCorrectionAnalyzer dscAnalyzer = HMSObjectCreator.getInstance()
            .createDocumentSkewCorrectionAnalyzer();
        SparseArray<MLDocumentSkewCorrectionResult> result = dscAnalyzer.syncDocumentSkewCorrect(frame,
            new MLDocumentSkewCorrectionCoordinateInput(HMSUtils.getInstance().convertRaToPointList(points)));

        if (isStop) {
            stopAnalyzer(dscAnalyzer);
        }

        if (result != null && result.get(0).getResultCode() == MLDocumentSkewCorrectionConstant.SUCCESS) {
            HMSBackgroundTasks.getInstance()
                .saveDscImages(getContext(), result)
                .addOnSuccessListener(writableMap -> handleResult("syncDocumentSkewCorrect", writableMap, promise))
                .addOnFailureListener(e -> handleResult("syncDocumentSkewCorrect", e, promise));
        } else {
            handleResult("syncDocumentSkewCorrect", FAILURE, promise);
        }
    }

    /**
     * Releases resources of analyzer
     *
     * @param dscAnalyzer document skew correction
     */
    private void stopAnalyzer(MLDocumentSkewCorrectionAnalyzer dscAnalyzer) {
        try {
            dscAnalyzer.stop();
            Log.i(getName(), "MLDocumentSkewCorrectionAnalyzer stop");
        } catch (IOException e) {
            Log.i(getName(), "MLDocumentSkewCorrectionAnalyzer stop:" + e.getMessage());
        }
    }
}
