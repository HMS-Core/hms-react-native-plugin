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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.IMSEG_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import android.util.Log;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSBackgroundTasks;
import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import java.io.IOException;

public class HMSImageSegmentation extends HMSBase {

    /**
     * Initializes module
     *
     * @param context app context
     */
    public HMSImageSegmentation(ReactApplicationContext context) {
        super(context, HMSImageSegmentation.class.getSimpleName(), IMSEG_CONSTANTS);
    }

    /**
     * Implements image segmentation in synchronous mode.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param analyzerConfiguration Setting for creating analyzer
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

        boolean isBodySeg = true;
        if (analyzerConfiguration != null && HMSUtils.getInstance()
            .hasValidKey(analyzerConfiguration, "analyzerType", ReadableType.Number)) {
            int analyzerType = analyzerConfiguration.getInt("analyzerType");
            isBodySeg = analyzerType == (int) (IMSEG_CONSTANTS.get("BODY_SEG"));
        }
        boolean finalIsBodySeg = isBodySeg;

        MLImageSegmentationAnalyzer imageSegmentationAnalyzer = HMSObjectCreator.getInstance()
            .createImageSegmentationAnalyzer(analyzerConfiguration);
        HMSBackgroundTasks.getInstance()
            .saveImageSegmentationImages(getContext(), imageSegmentationAnalyzer.analyseFrame(frame), finalIsBodySeg)
            .addOnSuccessListener(writableMap -> {
                if (isStop) {
                    stopSilent(imageSegmentationAnalyzer);
                }
                handleResult("analyzeFrame", writableMap, promise);
            })
            .addOnFailureListener(e -> {
                if (isStop) {
                    stopSilent(imageSegmentationAnalyzer);
                }
                handleResult("analyzeFrame", e, promise);
            });
    }

    /**
     * Implements image segmentation in asynchronous mode.
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param analyzerConfiguration Setting for creating analyzer
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isStop, ReadableMap frameConfiguration, ReadableMap analyzerConfiguration,
        final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        boolean isBodySeg = true;
        if (analyzerConfiguration != null && HMSUtils.getInstance()
            .hasValidKey(analyzerConfiguration, "analyzerType", ReadableType.Number)) {
            int analyzerType = analyzerConfiguration.getInt("analyzerType");
            isBodySeg = analyzerType == (int) (IMSEG_CONSTANTS.get("BODY_SEG"));
        }
        boolean finalIsBodySeg = isBodySeg;

        MLImageSegmentationAnalyzer imageSegmentationAnalyzer = HMSObjectCreator.getInstance()
            .createImageSegmentationAnalyzer(analyzerConfiguration);
        imageSegmentationAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(imageSegmentation -> {
            HMSBackgroundTasks.getInstance()
                .saveImageSegmentationImages(getContext(), imageSegmentation, finalIsBodySeg)
                .addOnSuccessListener(writableMap -> {
                    if (isStop) {
                        stopSilent(imageSegmentationAnalyzer);
                    }
                    handleResult("asyncAnalyzeFrame", writableMap, promise);
                })
                .addOnFailureListener(e -> {
                    if (isStop) {
                        stopSilent(imageSegmentationAnalyzer);
                    }
                    handleResult("asyncAnalyzeFrame", e, promise);
                });
        }).addOnFailureListener(e -> {
            if (isStop) {
                stopSilent(imageSegmentationAnalyzer);
            }
            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }

    /**
     * Releases resources of analyzer
     *
     * @param imageSegmentationAnalyzer analyzer
     */
    private void stopSilent(MLImageSegmentationAnalyzer imageSegmentationAnalyzer) {
        try {
            imageSegmentationAnalyzer.stop();
            Log.i(getName(), "MLImageSegmentationAnalyzer stop : OK");
        } catch (IOException e) {
            Log.i(getName(), "MLImageSegmentationAnalyzer stop : " + e.getMessage());
        }
    }
}
