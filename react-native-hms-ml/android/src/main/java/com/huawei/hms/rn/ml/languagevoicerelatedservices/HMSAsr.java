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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.ASR_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.ASR_ON_ERROR;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.ASR_ON_RECOGNIZING_RESULTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.ASR_ON_RESULTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.ASR_ON_STARTING_SPEECH;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.ASR_ON_START_LISTENING;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.ASR_ON_STATE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.ASR_ON_VOICE_DATA_RECEIVED;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.ASR_RECOGNIZER_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CURRENT_ACTIVITY_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.STRING_PARAM_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureActivity;
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import com.huawei.hms.mlsdk.asr.MLAsrConstants;
import com.huawei.hms.mlsdk.asr.MLAsrListener;
import com.huawei.hms.mlsdk.asr.MLAsrRecognizer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.List;

public class HMSAsr extends HMSBase implements MLAsrListener, MLAsrRecognizer.LanguageCallback {
    private Promise asrPluginPromise;

    private MLAsrRecognizer asrRecognizer;

    private Promise languageListPromise;

    private static final int ASR_PLUGIN_REQUEST = 300;

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSAsr(ReactApplicationContext reactContext) {
        super(reactContext, HMSAsr.class.getSimpleName(), ASR_CONSTANTS);
        getContext().addActivityEventListener(mActivityEventListener);
    }

    /**
     * Destroy and release Asr Recognizer
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void destroy(final Promise promise) {
        startMethodExecTimer("destroy");

        if (asrRecognizer == null) {
            handleResult("destroy", ASR_RECOGNIZER_NULL, promise);
            return;
        }

        asrRecognizer.destroy();
        asrRecognizer = null;
        handleResult("destroy", SUCCESS, promise);
    }

    /**
     * Obtains supported languages
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getLanguages(final Promise promise) {
        startMethodExecTimer("getLanguages");

        if (asrRecognizer == null) {
            handleResult("getLanguages", ASR_RECOGNIZER_NULL, promise);
            return;
        }

        asrRecognizer.getLanguages(this);
        languageListPromise = promise;
    }

    /**
     * Creates Asr Recognizer
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void createAsrRecognizer(final Promise promise) {
        startMethodExecTimer("createAsrRecognizer");
        asrRecognizer = MLAsrRecognizer.createAsrRecognizer(getContext());
        asrRecognizer.setAsrListener(this);
        handleResult("createAsrRecognizer", SUCCESS, promise);
    }

    /**
     * Start Recognizer
     *
     * @param language language code
     * @param feature feature type
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void startRecognizing(String language, int feature, final Promise promise) {
        startMethodExecTimer("startRecognizing");

        if (asrRecognizer == null) {
            handleResult("startRecognizing", ASR_RECOGNIZER_NULL, promise);
            return;
        }

        if (TextUtils.isEmpty(language)) {
            handleResult("startRecognizing", STRING_PARAM_NULL, promise);
            return;
        }

        Intent asrIntent = new Intent(MLAsrConstants.ACTION_HMS_ASR_SPEECH).putExtra(MLAsrConstants.LANGUAGE, language)
            .putExtra(MLAsrConstants.FEATURE, feature);
        asrRecognizer.startRecognizing(asrIntent);
        handleResult("startRecognizing", SUCCESS, promise);
    }

    /**
     * Start Asr Recognizer plugin
     *
     * @param language language code
     * @param feature feature type
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void startRecognizingPlugin(String language, int feature, final Promise promise) {
        startMethodExecTimer("startRecognizingPlugin");
        Activity currentActivity = getCurrentActivity();

        if (TextUtils.isEmpty(language)) {
            handleResult("startRecognizingPlugin", STRING_PARAM_NULL, promise);
            return;
        }

        if (currentActivity == null) {
            handleResult("startRecognizingPlugin", CURRENT_ACTIVITY_NULL, promise);
            return;
        }

        asrPluginPromise = promise;

        final Intent asrIntent = new Intent(currentActivity, MLAsrCaptureActivity.class).putExtra(
            MLAsrConstants.LANGUAGE, language).putExtra(MLAsrConstants.FEATURE, feature);
        currentActivity.startActivityForResult(asrIntent, ASR_PLUGIN_REQUEST);
    }

    /**
     * Activity result listener for plugin
     */
    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
            if (requestCode == ASR_PLUGIN_REQUEST) {
                if (asrPluginPromise != null) {
                    Bundle result = intent.getExtras();
                    switch (resultCode) {
                        case MLAsrCaptureConstants.ASR_SUCCESS:
                            WritableMap success = SUCCESS.getStatusAndMessage();
                            String asrResult = "";
                            if (result != null) {
                                if (result.containsKey(MLAsrCaptureConstants.ASR_RESULT)) {
                                    asrResult = result.getString(MLAsrCaptureConstants.ASR_RESULT);
                                }
                            }
                            success.putString("result", asrResult);
                            asrPluginPromise.resolve(success);
                            break;
                        case MLAsrCaptureConstants.ASR_FAILURE:
                            WritableMap fail = FAILURE.getStatusAndMessage();
                            if (result != null) {
                                if (result.containsKey(MLAsrCaptureConstants.ASR_ERROR_CODE)) {
                                    int errorCode = result.getInt(MLAsrCaptureConstants.ASR_ERROR_CODE);
                                    fail.putInt("errorCode", errorCode);
                                }
                                if (result.containsKey(MLAsrCaptureConstants.ASR_ERROR_MESSAGE)) {
                                    String errorMsg = result.getString(MLAsrCaptureConstants.ASR_ERROR_MESSAGE);
                                    fail.putString("errorMessage", errorMsg);
                                }
                                if (result.containsKey(MLAsrCaptureConstants.ASR_SUB_ERROR_CODE)) {
                                    int subErrorCode = result.getInt(MLAsrCaptureConstants.ASR_SUB_ERROR_CODE);
                                    fail.putInt("subErrorCode", subErrorCode);
                                }
                            }
                            asrPluginPromise.resolve(fail);
                            break;
                        default:
                            asrPluginPromise.resolve(FAILURE.getStatusAndMessage());
                            break;
                    }
                    asrPluginPromise = null;
                }
            }
        }
    };

    @Override
    public void onResults(Bundle bundle) {
        WritableMap wm = Arguments.createMap();
        wm.putString("result", bundle.getString(MLAsrRecognizer.RESULTS_RECOGNIZED));
        sendEvent(ASR_ON_RESULTS, "MLAsrListener", wm);
    }

    @Override
    public void onRecognizingResults(Bundle bundle) {
        WritableMap wm = Arguments.createMap();
        wm.putString("result", bundle.getString(MLAsrRecognizer.RESULTS_RECOGNIZING));
        sendEvent(ASR_ON_RECOGNIZING_RESULTS, "MLAsrListener", wm);
    }

    @Override
    public void onResult(List<String> list) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        for (String language : list) {
            wa.pushString(language);
        }
        wm.putArray("result", wa);
        handleResult("MLAsrRecognizer.LanguageCallback", wm, languageListPromise);
        languageListPromise = null;
    }

    @Override
    public void onError(int error, String errorMsg) {
        WritableMap wm = Arguments.createMap();
        wm.putInt("error", error);
        wm.putString("errorMessage", errorMsg);
        sendEvent(ASR_ON_ERROR, "MLAsrListener", wm);
    }

    @Override
    public void onStartListening() {
        WritableMap wm = Arguments.createMap();
        wm.putString("info", "Listening started");
        sendEvent(ASR_ON_START_LISTENING, "MLAsrListener", wm);
    }

    @Override
    public void onStartingOfSpeech() {
        WritableMap wm = Arguments.createMap();
        wm.putString("info", "Speech started");
        sendEvent(ASR_ON_STARTING_SPEECH, "MLAsrListener", wm);
    }

    @Override
    public void onVoiceDataReceived(byte[] bytes, float v, Bundle bundle) {
        WritableMap wm = Arguments.createMap();
        wm.putArray("data", HMSUtils.getInstance().convertByteArrToWa(bytes));
        wm.putDouble("energy", v);
        sendEvent(ASR_ON_VOICE_DATA_RECEIVED, "MLAsrListener", wm);
    }

    @Override
    public void onState(int state, Bundle bundle) {
        WritableMap wm = Arguments.createMap();
        wm.putInt("state", state);
        sendEvent(ASR_ON_STATE, "MLAsrListener", wm);
    }
}
