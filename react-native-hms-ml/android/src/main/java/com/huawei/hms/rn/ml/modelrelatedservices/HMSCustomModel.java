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

package com.huawei.hms.rn.ml.modelrelatedservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CUSTOM_MODEL_EXECUTOR_SETTING_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CUSTOM_MODEL_INPUT_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CUSTOM_MODEL_SETTING_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.custom.MLModelExecutor;
import com.huawei.hms.mlsdk.custom.MLModelExecutorSettings;
import com.huawei.hms.mlsdk.custom.MLModelInputOutputSettings;
import com.huawei.hms.mlsdk.custom.MLModelInputs;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;

public class HMSCustomModel extends HMSBase {

    /**
     * Initializes Module
     *
     * @param reactContext app context
     */
    public HMSCustomModel(ReactApplicationContext reactContext) {
        super(reactContext, HMSCustomModel.class.getSimpleName(), null);
    }

    /**
     * Performs inference using input and output configurations and content
     *
     * @param modelInputOutputSettings input output setting
     * @param modelInputConfiguration Source data to be inferred.
     * @param isRemote remote or local model
     * @param modelConfig model information container
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void exec(boolean isRemote, ReadableMap modelInputOutputSettings, ReadableMap modelInputConfiguration,
        ReadableMap modelConfig, final Promise promise) {
        startMethodExecTimer("exec");

        MLModelInputOutputSettings settings = HMSObjectCreator.getInstance()
            .createCustomModelInputOutputSetting(modelInputOutputSettings);
        MLModelInputs modelInputs = HMSObjectCreator.getInstance()
            .createCustomModelInputs(modelInputConfiguration, getContext());
        MLModelExecutorSettings modelExecutorSetting = HMSObjectCreator.getInstance()
            .createCustomModelExecutorSettings(isRemote, modelConfig);

        if (settings == null) {
            handleResult("exec", CUSTOM_MODEL_SETTING_NULL, promise);
            return;
        }
        if (modelInputs == null) {
            handleResult("exec", CUSTOM_MODEL_INPUT_NULL, promise);
            return;
        }
        if (modelExecutorSetting == null) {
            handleResult("exec", CUSTOM_MODEL_EXECUTOR_SETTING_NULL, promise);
            return;
        }

        try {
            MLModelExecutor.getInstance(modelExecutorSetting)
                .exec(modelInputs, settings)
                .addOnSuccessListener(mlModelOutputs -> handleResult("exec",
                    HMSResultCreator.getInstance().customModelResult(mlModelOutputs), promise))
                .addOnFailureListener(e -> handleResult("exec", e, promise));
        } catch (MLException e) {
            handleResult("exec", e, promise);
        }
    }

    /**
     * Stops an inference task to release resources.
     *
     * @param isRemote remote or local model
     * @param modelConfig model configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void close(boolean isRemote, ReadableMap modelConfig, final Promise promise) {
        startMethodExecTimer("close");
        MLModelExecutorSettings modelExecutorSetting = HMSObjectCreator.getInstance()
            .createCustomModelExecutorSettings(isRemote, modelConfig);

        if (modelExecutorSetting == null) {
            handleResult("close", CUSTOM_MODEL_EXECUTOR_SETTING_NULL, promise);
            return;
        }

        try {
            MLModelExecutor.getInstance(modelExecutorSetting).close();
            handleResult("close", SUCCESS, promise);
        } catch (MLException | IOException e) {
            handleResult("close", e, promise);
        }
    }

    /**
     * Obtains the channel index based on the output channel name.
     *
     * @param isRemote local or remote model
     * @param channelName channel name
     * @param modelConfig model configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getOutputIndex(boolean isRemote, String channelName, ReadableMap modelConfig, final Promise promise) {
        startMethodExecTimer("getOutputIndex");
        MLModelExecutorSettings modelExecutorSetting = HMSObjectCreator.getInstance()
            .createCustomModelExecutorSettings(isRemote, modelConfig);

        if (modelExecutorSetting == null) {
            handleResult("getOutputIndex", CUSTOM_MODEL_EXECUTOR_SETTING_NULL, promise);
            return;
        }

        try {
            MLModelExecutor.getInstance(modelExecutorSetting)
                .getOutputIndex(channelName)
                .addOnSuccessListener(
                    integer -> handleResult("getOutputIndex", HMSResultCreator.getInstance().integerResult(integer),
                        promise))
                .addOnFailureListener(e -> handleResult("getOutputIndex", e, promise));
        } catch (MLException e) {
            handleResult("getOutputIndex", e, promise);
        }
    }

}
