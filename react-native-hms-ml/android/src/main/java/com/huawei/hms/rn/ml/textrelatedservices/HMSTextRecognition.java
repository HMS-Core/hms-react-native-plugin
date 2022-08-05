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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.TEXT_SETTING_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.ANALYZER_NOT_AVAILABLE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import android.util.Log;
import android.util.SparseArray;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.text.MLText;
import com.huawei.hms.mlsdk.text.MLTextAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;

import javax.annotation.Nonnull;

public class HMSTextRecognition extends HMSBase {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSTextRecognition(ReactApplicationContext reactContext) {
        super(reactContext, HMSTextRecognition.class.getSimpleName(), TEXT_SETTING_CONSTANTS);
    }

    /**
     * Runs analyze operation asynchronously
     *
     * @param isRemote for analyzer setting on-cloud or on-device
     * @param isStop resources for analyzer. Recommended to use this in latest frame
     * @param frameConfiguration MLFrame configuration parameters
     * @param analyzerConfiguration Analyzer configuration for on-cloud or on-device
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isRemote, boolean isStop, ReadableMap frameConfiguration,
        ReadableMap analyzerConfiguration, final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLTextAnalyzer textAnalyzer = HMSObjectCreator.getInstance()
            .createTextAnalyzer(analyzerConfiguration, isRemote);
        textAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(mlText -> {
            if (isStop) {
                stopSilent(textAnalyzer);
            }
            handleResult("asyncAnalyzeFrame", HMSResultCreator.getInstance().getTextRecognitionResult(mlText), promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                stopSilent(textAnalyzer);
            }
            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }

    /**
     * Runs analyze operation synchronously
     *
     * @param isStop if true releases resources for analyzer. Recommended to use this in latest frame for better performance
     * @param frameConfiguration MLFrame configuration parameters
     * @param analyzerConfiguration Analyzer configuration to create on-device analyzer
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeFrame(boolean isStop, ReadableMap frameConfiguration, ReadableMap analyzerConfiguration,
        final Promise promise) {
        startMethodExecTimer("analyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("analyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLTextAnalyzer textAnalyzer = HMSObjectCreator.getInstance()
            .createTextAnalyzer(analyzerConfiguration, getContext());

        if (!textAnalyzer.isAvailable()) {
            handleResult("analyzeFrame", ANALYZER_NOT_AVAILABLE, promise);
            return;
        }

        SparseArray<MLText.Block> result = textAnalyzer.analyseFrame(frame);

        if (isStop) {
            stopSilent(textAnalyzer);
        }

        handleResult("analyzeFrame", HMSResultCreator.getInstance().getTextRecognitionResult(result), promise);
    }

    /**
     * Releases resources of analyzer
     *
     * @param textAnalyzer analyzer
     */
    private void stopSilent(@Nonnull MLTextAnalyzer textAnalyzer) {
        try {
            textAnalyzer.stop();
            Log.i(getName(), "MLTextAnalyzer stop : OK");
        } catch (IOException e) {
            Log.i(getName(), "MLTextAnalyzer stop : " + e.getMessage());
        }
    }
}