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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.FACE_VERIFICATION_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;

import com.facebook.react.bridge.Arguments;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.faceverify.MLFaceTemplateResult;
import com.huawei.hms.mlsdk.faceverify.MLFaceVerificationAnalyzer;
import com.huawei.hms.mlsdk.faceverify.MLFaceVerificationAnalyzerFactory;
import com.huawei.hms.mlsdk.faceverify.MLFaceVerificationAnalyzerSetting;
import com.huawei.hms.mlsdk.faceverify.MLFaceVerificationResult;
import com.huawei.hms.mlsdk.gesture.MLGestureAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.constants.HMSResults;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import java.util.List;

public class HMSFaceVerification extends HMSBase {

    private static final String TAG = HMSFaceVerification.class.getSimpleName();

    private MLFaceVerificationAnalyzer analyzer;
    private MLFaceVerificationAnalyzerSetting setting;
    /**
     * Initialize module
     *
     * @param context app context
     */
    public HMSFaceVerification(ReactApplicationContext context) {
        super(context, HMSFaceVerification.class.getSimpleName(), FACE_VERIFICATION_CONSTANTS);
        initAnalyzer();
    }

    private void initAnalyzer() {
        setting = new MLFaceVerificationAnalyzerSetting.Factory().setMaxFaceDetected(3).create();
        analyzer = MLFaceVerificationAnalyzerFactory.getInstance().getFaceVerificationAnalyzer(setting);
    }

    @ReactMethod
    public void setMaxFaceDetected(int maxFaceDetected, Promise promise) {
        startMethodExecTimer("setMaxFaceDetected");
        if (maxFaceDetected < 1) {
            maxFaceDetected = 1;
        } else if (maxFaceDetected > 3) {
            maxFaceDetected = 3;
        }

        setting = new MLFaceVerificationAnalyzerSetting.Factory().setMaxFaceDetected(maxFaceDetected).create();
        analyzer = MLFaceVerificationAnalyzerFactory.getInstance().getFaceVerificationAnalyzer(setting);
        handleResult("setMaxFaceDetected", SUCCESS, promise);
    }

    @ReactMethod
    public void getMaxFaceDetected(Promise promise) {
        startMethodExecTimer("setMaxFaceDetected");
        int maxFaceCount = setting.getMaxFaceDetcted();
        WritableMap res = Arguments.createMap();
        res.putInt("maxFaceCount", maxFaceCount);
        handleResult("setMaxFaceDetected", res, promise);
    }

    @ReactMethod
    public void loadTemplatePic(ReadableMap frameConfiguration, Promise promise) {
        startMethodExecTimer("loadTemplatePic");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("loadTemplatePic", FRAME_NULL, promise);
            return;
        }

        long startTime = System.currentTimeMillis();
        List<MLFaceTemplateResult> results = analyzer.setTemplateFace(frame);
        long endTime = System.currentTimeMillis();
        int cost = (int)(endTime - startTime);
        WritableMap wm = HMSResultCreator.getInstance().getFaceVerificationTemplateResult(results, cost);

        handleResult("loadTemplatePic", wm, promise);
    }

    @ReactMethod
    public void compare(boolean isStop, ReadableMap frameConfiguration, Promise promise) {
        startMethodExecTimer("compare");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("compare", FRAME_NULL, promise);
            return;
        }

        final long startTime = System.currentTimeMillis();
        SparseArray<MLFaceVerificationResult> list = analyzer.analyseFrame(frame);
        long endTime = System.currentTimeMillis();
        int cost = (int)(endTime - startTime);
        WritableMap wm = HMSResultCreator.getInstance().getFaceVerificationCompareResults(list, cost);

        if (isStop) {
            analyzer.stop();
        }

        handleResult("compare", wm, promise);
    }

    @ReactMethod
    public void asyncCompare(boolean isStop, ReadableMap frameConfiguration, Promise promise) {
        startMethodExecTimer("asyncCompare");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncCompare", FRAME_NULL, promise);
            return;
        }

        final long startTime = System.currentTimeMillis();
        Task<List<MLFaceVerificationResult>> task = analyzer.asyncAnalyseFrame(frame);
        task.addOnSuccessListener(mlCompareList -> {
            long endTime = System.currentTimeMillis();
            int cost = (int)(endTime - startTime);
            WritableMap wm = HMSResultCreator.getInstance().getFaceVerificationCompareResults(mlCompareList, cost);
            if (isStop) {
                analyzer.stop();
            }

            handleResult("asyncCompare", wm, promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                analyzer.stop();
            }
            handleResult("asyncCompare", e, promise);
        });
    }
}
