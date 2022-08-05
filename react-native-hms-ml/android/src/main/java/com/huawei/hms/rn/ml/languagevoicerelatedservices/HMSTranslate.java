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

package com.huawei.hms.rn.ml.languagevoicerelatedservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.TRANSLATE_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.TRANSLATE_DOWNLOAD_ON_PROCESS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.STRING_PARAM_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import android.text.TextUtils;
import android.util.Log;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.model.download.MLModelDownloadListener;
import com.huawei.hms.mlsdk.translate.MLTranslateLanguage;
import com.huawei.hms.mlsdk.translate.cloud.MLRemoteTranslator;
import com.huawei.hms.mlsdk.translate.local.MLLocalTranslator;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

public class HMSTranslate extends HMSBase implements MLModelDownloadListener {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSTranslate(ReactApplicationContext reactContext) {
        super(reactContext, HMSTranslate.class.getSimpleName(), TRANSLATE_CONSTANTS);
    }

    /**
     * Asynchronously translates text with analyzer created with given configuration
     *
     * @param isRemote if true translates on-cloud otherwise on-device
     * @param isStop stops translator and releases resources
     * @param text text to be translated
     * @param translatorSetting configuration for translator
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void asyncTranslate(boolean isRemote, boolean isStop, String text, ReadableMap translatorSetting,
        final Promise promise) {
        startMethodExecTimer("asyncTranslate");

        if (TextUtils.isEmpty(text)) {
            handleResult("asyncTranslate", STRING_PARAM_NULL, promise);
            return;
        }

        if (isRemote) {
            MLRemoteTranslator remoteTranslator = HMSObjectCreator.getInstance()
                .createRemoteTranslator(translatorSetting);
            handleAsyncTranslate(isStop, remoteTranslator, remoteTranslator.asyncTranslate(text), promise);
        } else {
            MLLocalTranslator localTranslator = HMSObjectCreator.getInstance().createLocalTranslator(translatorSetting);
            handleAsyncTranslate(isStop, localTranslator, localTranslator.asyncTranslate(text), promise);
        }
    }

    /**
     * Downloads the model for local translation
     *
     * @param strategyConfiguration defines model download strategy
     * @param translatorSetting configuration for translator
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void preparedModel(ReadableMap strategyConfiguration, ReadableMap translatorSetting, final Promise promise) {
        startMethodExecTimer("preparedModel");

        MLLocalTranslator localTranslator = HMSObjectCreator.getInstance().createLocalTranslator(translatorSetting);

        localTranslator.preparedModel(HMSObjectCreator.getInstance().createModelDownloadStrategy(strategyConfiguration),
            this)
            .addOnSuccessListener(aVoid -> handleResult("preparedModel", SUCCESS, promise))
            .addOnFailureListener(e -> handleResult("preparedModel", e, promise));
    }

    /**
     * Synchronously translates text with analyzer created with given configuration
     *
     * @param isRemote if true translates on-cloud otherwise on-device
     * @param isStop stops translator and releases resources
     * @param text text to be translated
     * @param translatorSetting configuration for translator
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void syncTranslate(boolean isRemote, boolean isStop, String text, ReadableMap translatorSetting,
        final Promise promise) {
        startMethodExecTimer("syncTranslate");

        if (TextUtils.isEmpty(text)) {
            handleResult("syncTranslate", STRING_PARAM_NULL, promise);
            return;
        }

        Object detector = isRemote
            ? HMSObjectCreator.getInstance().createRemoteTranslator(translatorSetting)
            : HMSObjectCreator.getInstance().createLocalTranslator(translatorSetting);

        try {
            String s = (detector instanceof MLRemoteTranslator
                ? ((MLRemoteTranslator) detector).syncTranslate(text)
                : ((MLLocalTranslator) detector).syncTranslate(text));

            if (isStop) {
                handleStop(detector);
            }

            handleResult("syncTranslate", HMSResultCreator.getInstance().getStringResult(s), promise);
        } catch (MLException e) {
            if (isStop) {
                handleStop(detector);
            }

            handleResult("syncTranslate", e, promise);
        }
    }

    /**
     * Asynchronously obtains languages
     *
     * @param isRemote if true translates on-cloud otherwise on-device
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getAllLanguages(boolean isRemote, final Promise promise) {
        startMethodExecTimer("getAllLanguages");

        if (isRemote) {
            MLTranslateLanguage.getCloudAllLanguages()
                .addOnSuccessListener(
                    strings -> handleResult("getAllLanguages", HMSResultCreator.getInstance().stringSetResult(strings),
                        promise))
                .addOnFailureListener(e -> handleResult("getAllLanguages", e, promise));
        } else {
            MLTranslateLanguage.getLocalAllLanguages()
                .addOnSuccessListener(
                    strings -> handleResult("getAllLanguages", HMSResultCreator.getInstance().stringSetResult(strings),
                        promise))
                .addOnFailureListener(e -> handleResult("getAllLanguages", e, promise));
        }
    }

    /**
     * Synchronously obtains languages
     *
     * @param isRemote if true returns on-cloud languages otherwise on-device
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void syncGetAllLanguages(boolean isRemote, final Promise promise) {
        startMethodExecTimer("syncGetAllLanguages");

        if (isRemote) {
            try {
                handleResult("syncGetAllLanguages",
                    HMSResultCreator.getInstance().stringSetResult(MLTranslateLanguage.syncGetCloudAllLanguages()),
                    promise);
            } catch (MLException e) {
                handleResult("syncGetAllLanguages", e, promise);
            }
        } else {
            handleResult("syncGetAllLanguages",
                HMSResultCreator.getInstance().stringSetResult(MLTranslateLanguage.syncGetLocalAllLanguages()),
                promise);
        }
    }

    /**
     * onProcess callback for model download
     * @param alreadyDownLength Already downloaded part
     * @param totalLength Total length to be downloaded
     */
    @Override
    public void onProcess(long alreadyDownLength, long totalLength) {
        WritableMap wm = Arguments.createMap();
        wm.putString("alreadyDownloadLength", Long.toString(alreadyDownLength));
        wm.putString("totalLength", Long.toString(totalLength));
        sendEvent(TRANSLATE_DOWNLOAD_ON_PROCESS, "onProcess", wm);
    }

    /**
     * Releases resources of remote translator
     *
     * @param translator translator object
     */
    private void handleStop(Object translator) {
        if (translator instanceof MLRemoteTranslator) {
            MLRemoteTranslator remoteTranslator = (MLRemoteTranslator) translator;
            remoteTranslator.stop();
            Log.i(getName(), "MLRemoteTranslator stop");
        } else {
            MLLocalTranslator localTranslator = (MLLocalTranslator) translator;
            localTranslator.stop();
            Log.i(getName(), "MLLocalTranslator stop");
        }
    }

    private void handleAsyncTranslate(boolean isStop, Object detector, Task<String> task, Promise promise) {
        task.addOnSuccessListener(s -> {
            if (isStop) {
                handleStop(detector);
            }

            handleResult("asyncTranslate", HMSResultCreator.getInstance().getStringResult(s), promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                handleStop(detector);
            }

            handleResult("asyncTranslate", e, promise);
        });
    }

}