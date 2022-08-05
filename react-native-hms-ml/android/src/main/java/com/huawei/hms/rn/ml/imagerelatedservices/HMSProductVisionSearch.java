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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.PRODUCT_VISION_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CURRENT_ACTIVITY_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import android.app.Activity;

import com.huawei.hms.mlplugin.productvisionsearch.MLProductVisionSearchCapture;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.MLRemoteProductVisionSearchAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class HMSProductVisionSearch extends HMSBase {

    /**
     * Initializes module
     *
     * @param context app context
     */
    public HMSProductVisionSearch(ReactApplicationContext context) {
        super(context, HMSProductVisionSearch.class.getSimpleName(), PRODUCT_VISION_CONSTANTS);
        Fresco.initialize(context);
    }

    /**
     * Asynchronous product search
     *
     * @param isStop Releases resources for analyzer. Recommended to use on latest frame
     * @param frameConfiguration Frame configuration to obtain frame
     * @param analyzerSetting Setting for creating analyzer
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncAnalyzeFrame(boolean isStop, ReadableMap frameConfiguration, ReadableMap analyzerSetting,
        final Promise promise) {
        startMethodExecTimer("asyncAnalyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("asyncAnalyzeFrame", FRAME_NULL, promise);
            return;
        }

        MLRemoteProductVisionSearchAnalyzer remoteProductVisionSearchAnalyzer = HMSObjectCreator.getInstance()
            .createProductVisionSearchAnalyzer(analyzerSetting);
        remoteProductVisionSearchAnalyzer.asyncAnalyseFrame(frame).addOnSuccessListener(mlProductVisionSearches -> {
            if (isStop) {
                remoteProductVisionSearchAnalyzer.stop();
            }
            handleResult("asyncAnalyzeFrame",
                HMSResultCreator.getInstance().getProductVisionSearchResult(mlProductVisionSearches), promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                remoteProductVisionSearchAnalyzer.stop();
            }
            handleResult("asyncAnalyzeFrame", e, promise);
        });
    }

    /**
     * Start product vision search plugin
     *
     * @param pluginConfiguration plugin configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void startProductVisionSearchCapturePlugin(ReadableMap pluginConfiguration, final Promise promise) {
        startMethodExecTimer("startProductVisionSearchCapturePlugin");
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            handleResult("startProductVisionSearchCapturePlugin", CURRENT_ACTIVITY_NULL, promise);
            return;
        }

        MLProductVisionSearchCapture capture = HMSObjectCreator.getInstance()
            .createProductVisionSearchCapture(pluginConfiguration, getContext());
        capture.startCapture(currentActivity);
        handleResult("startProductVisionSearchCapturePlugin", SUCCESS, promise);
    }

}
