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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.DOWNLOAD_ON_PROCESS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.MODEL_CUSTOM_TAG;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.MODEL_DOWNLOAD_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.MODEL_TRANSLATE_TAG;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.MODEL_TTS_TAG;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.REMOTE_MODEL_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.custom.MLCustomRemoteModel;
import com.huawei.hms.mlsdk.model.download.MLLocalModelManager;
import com.huawei.hms.mlsdk.model.download.MLModelDownloadListener;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.translate.local.MLLocalTranslatorModel;
import com.huawei.hms.mlsdk.tts.MLTtsLocalModel;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.util.Set;

public class HMSModelDownload extends HMSBase implements MLModelDownloadListener {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSModelDownload(ReactApplicationContext reactContext) {
        super(reactContext, HMSModelDownload.class.getSimpleName(), MODEL_DOWNLOAD_CONSTANTS);
    }

    /**
     * Downloads a specified model from the cloud based on a specified download condition.
     * If the download condition is not met, a failure message will be returned.
     * If the model does not exist locally or a new version is available on the cloud, the model will be downloaded.
     * Otherwise, the call of this method will stop immediately and onSuccess will be called.
     *
     * @param modelConfig specifies model
     * @param strategyConfig includes download strategy
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void downloadModel(ReadableMap modelConfig, ReadableMap strategyConfig, final Promise promise) {
        startMethodExecTimer("downloadModel");
        MLRemoteModel remoteModel = HMSObjectCreator.getInstance().createRemoteModel(modelConfig);

        if (remoteModel == null) {
            handleResult("downloadModel", REMOTE_MODEL_NULL, promise);
            return;
        }

        MLLocalModelManager.getInstance()
            .downloadModel(remoteModel, HMSObjectCreator.getInstance().createModelDownloadStrategy(strategyConfig),
                this)
            .addOnSuccessListener(aVoid -> handleResult("downloadModel", SUCCESS, promise))
            .addOnFailureListener(e -> handleResult("downloadModel", e, promise));
    }

    /**
     * Deletes a specified model from the device.
     * If the model has not been downloaded or is being downloaded, a failure message will be returned.
     *
     * @param modelConfig specifies model
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void deleteModel(ReadableMap modelConfig, final Promise promise) {
        startMethodExecTimer("deleteModel");
        MLRemoteModel remoteModel = HMSObjectCreator.getInstance().createRemoteModel(modelConfig);

        if (remoteModel == null) {
            handleResult("deleteModel", REMOTE_MODEL_NULL, promise);
            return;
        }

        MLLocalModelManager.getInstance()
            .deleteModel(remoteModel)
            .addOnSuccessListener(aVoid -> handleResult("deleteModel", SUCCESS, promise))
            .addOnFailureListener(e -> handleResult("deleteModel", e, promise));
    }

    /**
     * Checks whether a specified model has been downloaded to the device.
     *
     * @param modelConfig specifies model
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void isModelExist(ReadableMap modelConfig, final Promise promise) {
        startMethodExecTimer("isModelExists");
        MLRemoteModel remoteModel = HMSObjectCreator.getInstance().createRemoteModel(modelConfig);

        if (remoteModel == null) {
            handleResult("isModelExists", REMOTE_MODEL_NULL, promise);
            return;
        }

        MLLocalModelManager.getInstance()
            .isModelExist(remoteModel)
            .addOnSuccessListener(
                aBoolean -> handleResult("isModelExists", HMSResultCreator.getInstance().getBooleanResult(aBoolean),
                    promise))
            .addOnFailureListener(e -> handleResult("isModelExists", e, promise));
    }

    /**
     * Queries the model set that has been downloaded to a local path.
     *
     * @param modelTag Tag of the model
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getModels(int modelTag, final Promise promise) {
        startMethodExecTimer("getModels");

        switch (modelTag) {
            case MODEL_TTS_TAG:
                handleGetModels(MLLocalModelManager.getInstance().getModels(MLTtsLocalModel.class), promise);
                return;
            case MODEL_TRANSLATE_TAG:
                handleGetModels(MLLocalModelManager.getInstance().getModels(MLLocalTranslatorModel.class), promise);
                return;
            case MODEL_CUSTOM_TAG:
                handleGetModels(MLLocalModelManager.getInstance().getModels(MLCustomRemoteModel.class), promise);
                return;
            default:
                handleResult("getModels", FAILURE, promise);
        }
    }

    /**
     * Queries the storage path of a specified model on the device.
     *
     * @param modelConfig specifies model
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getRecentModelFile(final ReadableMap modelConfig, final Promise promise) {
        startMethodExecTimer("getRecentModelFile");
        MLRemoteModel remoteModel = HMSObjectCreator.getInstance().createRemoteModel(modelConfig);

        if (remoteModel == null) {
            handleResult("getRecentModelFile", REMOTE_MODEL_NULL, promise);
            return;
        }

        MLLocalModelManager.getInstance()
            .getRecentModelFile(remoteModel)
            .addOnSuccessListener(
                file -> handleResult("getRecentModelFile", HMSResultCreator.getInstance().getFilePathResult(file),
                    promise))
            .addOnFailureListener(e -> handleResult("getRecentModelFile", e, promise));
    }

    @Override
    public void onProcess(long alreadyDownLength, long totalLength) {
        WritableMap wm = Arguments.createMap();
        wm.putString("alreadyDownloadLength", Long.toString(alreadyDownLength));
        wm.putString("totalLength", Long.toString(totalLength));
        sendEvent(DOWNLOAD_ON_PROCESS, "onProcess", wm);
    }

    private <T extends MLRemoteModel> void handleGetModels(Task<Set<T>> task, Promise promise) {
        task.addOnSuccessListener(
            models -> handleResult("getModels", HMSResultCreator.getInstance().getModels((Set<MLRemoteModel>) models),
                promise)).addOnFailureListener(e -> handleResult("getModels", e, promise));
    }
}
