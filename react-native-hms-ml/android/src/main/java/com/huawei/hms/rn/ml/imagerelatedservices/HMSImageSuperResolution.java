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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.IMAGE_RESOLUTION_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;

import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.imagesuperresolution.MLImageSuperResolutionAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSBackgroundTasks;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class HMSImageSuperResolution extends HMSBase {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSImageSuperResolution(ReactApplicationContext reactContext) {
        super(reactContext, HMSImageSuperResolution.class.getSimpleName(), IMAGE_RESOLUTION_CONSTANTS);
    }

    /**
     * Performs super-resolution processing on the source image using the synchronous method.
     *
     * @param isStop releases resources of analyzer
     * @param frameConfiguration frame obtaining configuration
     * @param scale scale config
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeFrame(boolean isStop, ReadableMap frameConfiguration, float scale, final Promise promise) {
        startMethodExecTimer("analyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("analyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLImageSuperResolutionAnalyzer imageSuperResolutionAnalyzer = HMSObjectCreator.getInstance()
            .createImageSuperResolutionAnalyzer(scale);
        HMSBackgroundTasks.getInstance()
            .saveImageSuperResolutionImages(getContext(), imageSuperResolutionAnalyzer.analyseFrame(frame))
            .addOnSuccessListener(writableMap -> {
                if (isStop) {
                    imageSuperResolutionAnalyzer.stop();
                }
                handleResult("analyzeFrame", writableMap, promise);
            })
            .addOnFailureListener(e -> {
                if (isStop) {
                    imageSuperResolutionAnalyzer.stop();
                }
                handleResult("analyzeFrame", e, promise);
            });
    }

    /**
     * Performs super-resolution processing on the source image using the asynchronous method.
     *
     * @param isStop releases resources of analyzer
     * @param frameConfiguration frame obtaining configuration
     * @param scale scale config
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isStop, ReadableMap frameConfiguration, float scale, final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLImageSuperResolutionAnalyzer imageSuperResolutionAnalyzer = HMSObjectCreator.getInstance()
            .createImageSuperResolutionAnalyzer(scale);
        imageSuperResolutionAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(mlImageSuperResolutionResult -> {
            if (isStop) {
                imageSuperResolutionAnalyzer.stop();
            }
            HMSBackgroundTasks.getInstance()
                .saveImageAndGetUri(getContext(), mlImageSuperResolutionResult.getBitmap())
                .addOnSuccessListener(
                    string -> handleResult("asyncAnalyzeFrame", HMSResultCreator.getInstance().getStringResult(string),
                        promise))
                .addOnFailureListener(e -> handleResult("asyncAnalyzeFrame", e, promise));
        }).addOnFailureListener(e -> {
            if (isStop) {
                imageSuperResolutionAnalyzer.stop();
            }
            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }
}
