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

package com.huawei.hms.rn.ml.facebodyrelatedservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.FACE_RECOGNITION_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import android.util.Log;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzer;
import com.huawei.hms.mlsdk.face.face3d.ML3DFaceAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.io.IOException;

public class HMSFaceRecognition extends HMSBase {

    /**
     * Initialize module
     *
     * @param context app context
     */
    public HMSFaceRecognition(ReactApplicationContext context) {
        super(context, HMSFaceRecognition.class.getSimpleName(), FACE_RECOGNITION_CONSTANTS);
    }

    /**
     * Detects faces in a supplied image in asynchronous mode.
     *
     * @param is3D makes 3D face analyze if true
     * @param isStop releases the analyzer resources
     * @param frameConfiguration frame obtaining configuration
     * @param faceAnalyzerConfiguration analyzer configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean is3D, boolean isStop, ReadableMap frameConfiguration,
        ReadableMap faceAnalyzerConfiguration, final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        if (is3D) {
            ML3DFaceAnalyzer faceAnalyzer = HMSObjectCreator.getInstance()
                .create3DFaceAnalyzer(faceAnalyzerConfiguration);
            faceAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(ml3DFaces -> {
                if (isStop) {
                    stopAnalyzer(faceAnalyzer);
                }
                handleResult("asyncAnalyzeFrame", HMSResultCreator.getInstance().get3DFaceResult(ml3DFaces), promise);
            }).addOnFailureListener(e -> {
                if (isStop) {
                    stopAnalyzer(faceAnalyzer);
                }
                handleResult("asyncAnalyzeFrame", e, promise);
            });
        } else {
            MLFaceAnalyzer faceAnalyzer = HMSObjectCreator.getInstance()
                .create2DFaceAnalyzer(faceAnalyzerConfiguration);
            faceAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(mlFaces -> {
                if (isStop) {
                    stopAnalyzer(faceAnalyzer);
                }
                handleResult("asyncAnalyzeFrame", HMSResultCreator.getInstance().getFaceResult(mlFaces), promise);
            }).addOnFailureListener(e -> {
                if (isStop) {
                    stopAnalyzer(faceAnalyzer);
                }
                handleResult("asyncAnalyzeFrame", e, promise);
            });
        }
    }

    /**
     * Detects faces in a supplied image in synchronous mode.
     *
     * @param is3D makes 3D face analyze if true
     * @param isStop releases the analyzer resources
     * @param frameConfiguration frame obtaining configuration
     * @param faceAnalyzerConfiguration analyzer configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeFrame(boolean is3D, boolean isStop, ReadableMap frameConfiguration,
        ReadableMap faceAnalyzerConfiguration, final Promise promise) {
        startMethodExecTimer("analyzeFrame2D");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("analyzeFrame", FRAME_NULL, promise);
            return;
        }

        if (is3D) {
            ML3DFaceAnalyzer faceAnalyzer = HMSObjectCreator.getInstance()
                .create3DFaceAnalyzer(faceAnalyzerConfiguration);
            WritableMap wm = HMSResultCreator.getInstance()
                .get3DFaceResult(HMSUtils.getInstance().convertSparseArrayToList(faceAnalyzer.analyseFrame(frame)));

            if (isStop) {
                stopAnalyzer(faceAnalyzer);
            }

            handleResult("analyzeFrame", wm, promise);
        } else {
            MLFaceAnalyzer faceAnalyzer = HMSObjectCreator.getInstance()
                .create2DFaceAnalyzer(faceAnalyzerConfiguration);
            WritableMap wm = HMSResultCreator.getInstance()
                .getFaceResult(HMSUtils.getInstance().convertSparseArrayToList(faceAnalyzer.analyseFrame(frame)));

            if (isStop) {
                stopAnalyzer(faceAnalyzer);
            }

            handleResult("analyzeFrame", wm, promise);
        }
    }

    /**
     * Stops face analyzer
     *
     * @param faceAnalyzer analyzer
     */
    private void stopAnalyzer(MLFaceAnalyzer faceAnalyzer) {
        try {
            faceAnalyzer.stop();
            Log.i(getName(), "MLFaceAnalyzer stop");
        } catch (IOException e) {
            Log.i(getName(), "MLFaceAnalyzer stop : " + e.getMessage());
        }
    }

    /**
     * Stops 3D face analyzer
     *
     * @param faceAnalyzer analyzer
     */
    private void stopAnalyzer(ML3DFaceAnalyzer faceAnalyzer) {
        try {
            faceAnalyzer.stop();
            Log.i(getName(), "ML3DFaceAnalyzer stop");
        } catch (IOException e) {
            Log.i(getName(), "ML3DFaceAnalyzer stop : " + e.getMessage());
        }
    }
}
