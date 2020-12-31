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

package com.huawei.hms.rn.ml.languagevoicerelatedservices;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.mlsdk.aft.cloud.MLRemoteAftEngine;
import com.huawei.hms.mlsdk.aft.cloud.MLRemoteAftListener;
import com.huawei.hms.mlsdk.aft.cloud.MLRemoteAftResult;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.AFT_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.AFT_ON_ERROR;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.AFT_ON_EVENT;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.AFT_ON_INIT_COMPLETE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.AFT_ON_RESULT;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.AFT_ON_UPLOAD_PROGRESS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.STRING_PARAM_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

public class HMSAft extends HMSBase implements MLRemoteAftListener {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSAft(ReactApplicationContext reactContext) {
        super(reactContext, HMSAft.class.getSimpleName(), AFT_CONSTANTS);
    }

    /**
     * Initializes the audio transcription engine on the cloud and loads engine resources.
     * Resolve : Result Object
     */
    @ReactMethod
    public void init(final Promise promise) {
        startMethodExecTimer("init");
        MLRemoteAftEngine.getInstance().init(getContext());
        handleResult("init", SUCCESS, promise);
    }

    /**
     * Disables the audio transcription engine to release engine resources.
     * Resolve : Result Object
     */
    @ReactMethod
    public void close(final Promise promise) {
        startMethodExecTimer("close");
        MLRemoteAftEngine.getInstance().close();
        handleResult("close", SUCCESS, promise);
    }

    /**
     * Destroys a long audio transcription task on the cloud.
     * If the task is destroyed after the audio file is successfully uploaded,
     * the transcription has started and charging cannot be canceled.
     * Resolve : Result Object
     *
     * @param taskId task id
     */
    @ReactMethod
    public void destroyTask(String taskId, final Promise promise) {
        startMethodExecTimer("destroyTask");

        if (TextUtils.isEmpty(taskId)) {
            handleResult("destroyTask", STRING_PARAM_NULL, promise);
            return;
        }

        MLRemoteAftEngine.getInstance().destroyTask(taskId);
        handleResult("destroyTask", SUCCESS, promise);
    }

    /**
     * Obtains the long audio transcription result from the cloud.
     * Resolve : Result Object
     *
     * @param taskId task id
     */
    @ReactMethod
    public void getLongAftResult(String taskId, final Promise promise) {
        startMethodExecTimer("getLongAftResult");

        if (TextUtils.isEmpty(taskId)) {
            handleResult("getLongAftResult", STRING_PARAM_NULL, promise);
            return;
        }

        MLRemoteAftEngine.getInstance().getLongAftResult(taskId);
        handleResult("getLongAftResult", SUCCESS, promise);
    }

    /**
     * Pause the task for given taskId
     * Resolve : Result Object
     *
     * @param taskId task id
     */
    @ReactMethod
    public void pauseTask(String taskId, final Promise promise) {
        startMethodExecTimer("pauseTask");

        if (TextUtils.isEmpty(taskId)) {
            handleResult("pauseTask", STRING_PARAM_NULL, promise);
            return;
        }

        MLRemoteAftEngine.getInstance().pauseTask(taskId);
        handleResult("pauseTask", SUCCESS, promise);
    }

    /**
     * Resumes long audio transcription task on the cloud.
     * Resolve : Result Object
     *
     * @param taskId task id
     */
    @ReactMethod
    public void startTask(String taskId, final Promise promise) {
        startMethodExecTimer("startTask");

        if (TextUtils.isEmpty(taskId)) {
            handleResult("startTask", STRING_PARAM_NULL, promise);
            return;
        }

        MLRemoteAftEngine.getInstance().startTask(taskId);
        handleResult("startTask", SUCCESS, promise);
    }

    /**
     * Starts the task for given taskId
     * Resolve : Result Object
     */
    @ReactMethod
    public void setAftListener(final Promise promise) {
        startMethodExecTimer("setAftListener");
        MLRemoteAftEngine.getInstance().setAftListener(this);
        handleResult("setAftListener", SUCCESS, promise);
    }

    /**
     * Converts a short audio file on the cloud.
     * Resolve : Result Object
     *
     * @param uri              file uri
     * @param remoteAftSetting aft setting for recognition
     */
    @ReactMethod
    public void shortRecognize(String uri, ReadableMap remoteAftSetting, final Promise promise) {
        startMethodExecTimer("shortRecognize");

        if (TextUtils.isEmpty(uri)) {
            handleResult("shortRecognize", STRING_PARAM_NULL, promise);
            return;
        }

        String result = MLRemoteAftEngine.getInstance().shortRecognize(Uri.parse(uri),
                HMSObjectCreator.getInstance().createRemoteAftSetting(remoteAftSetting));

        handleResult("shortRecognize", HMSResultCreator.getInstance().getStringResult(result), promise);
    }

    /**
     * Converts a long audio file on the cloud.
     * Resolve: Result Object
     *
     * @param uri              file uri
     * @param remoteAftSetting aft setting for recognition
     */
    @ReactMethod
    public void longRecognize(String uri, ReadableMap remoteAftSetting, final Promise promise) {
        startMethodExecTimer("longRecognize");

        if (TextUtils.isEmpty(uri)) {
            handleResult("longRecognize", STRING_PARAM_NULL, promise);
            return;
        }

        String result = MLRemoteAftEngine.getInstance().longRecognize(Uri.parse(uri),
                HMSObjectCreator.getInstance().createRemoteAftSetting(remoteAftSetting));

        handleResult("longRecognize", HMSResultCreator.getInstance().getStringResult(result), promise);
    }

    /**
     * onInitComplete callback
     */
    @Override
    public void onInitComplete(String taskId, Object o) {
        WritableMap wm = Arguments.createMap();
        wm.putString("taskId", taskId);
        sendEvent(AFT_ON_INIT_COMPLETE, "MLRemoteAftListener", wm);
    }

    /**
     * onUploadProgress callback
     */
    @Override
    public void onUploadProgress(String taskId, double progress, Object o) {
        WritableMap wm = Arguments.createMap();
        wm.putString("taskId", taskId);
        wm.putDouble("progress", progress);
        sendEvent(AFT_ON_UPLOAD_PROGRESS, "MLRemoteAftListener", wm);
    }

    /**
     * onEvent callback
     */
    @Override
    public void onEvent(String taskId, int eventId, Object o) {
        WritableMap wm = Arguments.createMap();
        wm.putString("taskId", taskId);
        wm.putInt("eventId", eventId);
        sendEvent(AFT_ON_EVENT, "MLRemoteAftListener", wm);
    }

    /**
     * onResult callback
     */
    @Override
    public void onResult(String taskId, MLRemoteAftResult mlRemoteAftResult, Object o) {
        WritableMap wm = Arguments.createMap();
        wm.putBoolean("isComplete", mlRemoteAftResult.isComplete());
        if (mlRemoteAftResult.isComplete()) {
            wm.putString("taskId", taskId);
            wm.putString("text", mlRemoteAftResult.getText());
            wm.putArray("words", mlRemoteAftResult.getWords() == null ? Arguments.createArray() : HMSResultCreator.getInstance().getAftResult(mlRemoteAftResult.getWords()));
            wm.putArray("sentences", mlRemoteAftResult.getSentences() == null ? Arguments.createArray() : HMSResultCreator.getInstance().getAftResult(mlRemoteAftResult.getSentences()));
        }
        sendEvent(AFT_ON_RESULT, "MLRemoteAftListener", wm);
    }

    /**
     * onError callback
     */
    @Override
    public void onError(String taskId, int error, String message) {
        WritableMap wm = Arguments.createMap();
        wm.putString("taskId", taskId);
        wm.putInt("error", error);
        wm.putString("message", message);
        sendEvent(AFT_ON_ERROR, "MLRemoteAftListener", wm);
    }
}
