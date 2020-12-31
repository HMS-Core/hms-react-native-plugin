/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.ml.commonservices;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.mlsdk.common.MLCompositeAnalyzer;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.ANALYZER_NOT_AVAILABLE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FRAME_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

public class HMSComposite extends HMSBase {
    private MLCompositeAnalyzer compositeAnalyzer;

    /**
     * Initializes module
     *
     * @param mContext context
     */
    public HMSComposite(ReactApplicationContext mContext) {
        super(mContext, HMSComposite.class.getSimpleName(), null);
    }

    /**
     * Creates composite analyzer
     * Resolve : Result Object
     *
     * @param configuration analyzer configuration
     */
    @ReactMethod
    public void createCompositeAnalyzer(ReadableMap configuration, final Promise promise) {
        startMethodExecTimer("createCompositeAnalyzer");
        compositeAnalyzer = HMSObjectCreator.getInstance().createCompositeAnalyzer(configuration);
        handleResult("createCompositeAnalyzer", SUCCESS, promise);
    }

    /**
     * Checks whether an analyzer is available, that is, whether all required resources are loaded.
     * Resolve : Result Object
     */
    @ReactMethod
    public void isAvailable(final Promise promise) {
        startMethodExecTimer("isAvailable");

        if (compositeAnalyzer == null) {
            handleResult("isAvailable", ANALYZER_NOT_AVAILABLE, promise);
            return;
        }

        handleResult("createCompositeAnalyzer", HMSResultCreator.getInstance().getBooleanResult(compositeAnalyzer.isAvailable()), promise);
    }

    /**
     * Releases resources occupied by a composite analyzer.
     * Resolve : Result Object
     */
    @ReactMethod
    public void destroy(final Promise promise) {
        startMethodExecTimer("destroy");

        if (compositeAnalyzer == null) {
            handleResult("destroy", ANALYZER_NOT_AVAILABLE, promise);
            return;
        }

        compositeAnalyzer.destroy();
        compositeAnalyzer = null;
        handleResult("destroy", SUCCESS, promise);
    }

    /**
     * Uses a composite analyzer to detect information in an image and returns the detection result list,
     * Resolve : Result Object
     *
     * @param frameConfiguration frame configuration
     */
    @ReactMethod
    public void analyzeFrame(ReadableMap frameConfiguration, final Promise promise) {
        startMethodExecTimer("analyzeFrame");
        MLFrame frame = HMSObjectCreator.getInstance().createFrame(frameConfiguration, getContext());

        if (frame == null) {
            handleResult("analyzeFrame", FRAME_NULL, promise);
            return;
        }

        if (compositeAnalyzer == null) {
            handleResult("destroy", ANALYZER_NOT_AVAILABLE, promise);
            return;
        }

        handleResult("analyzeFrame", SUCCESS, promise);
    }
}
