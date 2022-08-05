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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.TTS_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.TTS_ON_AUDIO_AVAILABLE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.TTS_ON_ERROR;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.TTS_ON_EVENT;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.TTS_ON_RANGE_START;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.TTS_ON_WARN;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.STRING_PARAM_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.TTS_ENGINE_NULL;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;

import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment;
import com.huawei.hms.mlsdk.tts.MLTtsCallback;
import com.huawei.hms.mlsdk.tts.MLTtsConfig;
import com.huawei.hms.mlsdk.tts.MLTtsEngine;
import com.huawei.hms.mlsdk.tts.MLTtsError;
import com.huawei.hms.mlsdk.tts.MLTtsWarn;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

public class HMSTextToSpeech extends HMSBase implements MLTtsCallback {
    private MLTtsConfig ttsConfig;

    private MLTtsEngine ttsEngine;

    /**
     * Initializes module
     *
     * @param context app context
     */
    public HMSTextToSpeech(ReactApplicationContext context) {
        super(context, HMSTextToSpeech.class.getSimpleName(), TTS_CONSTANTS);
    }

    /**
     * Creates tts engine
     *
     * @param ttsConfiguration tts engine configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void createEngine(ReadableMap ttsConfiguration, final Promise promise) {
        startMethodExecTimer("createEngine");
        ttsConfig = HMSObjectCreator.getInstance().createTtsConfiguration(ttsConfiguration);
        ttsEngine = new MLTtsEngine(ttsConfig);
        ttsEngine.setTtsCallback(this);
        handleResult("createEngine", SUCCESS, promise);
    }

    /**
     * Runs engine to speak
     *
     * @param text text to be vocalize
     * @param mode engine mode
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void speak(String text, int mode, final Promise promise) {
        startMethodExecTimer("speak");

        if (TextUtils.isEmpty(text) || text.length() > 500) {
            handleResult("speak", STRING_PARAM_NULL, promise);
            return;
        }

        if (ttsEngine == null) {
            handleResult("speak", TTS_ENGINE_NULL, promise);
            return;
        }

        String id = ttsEngine.speak(text, mode);
        handleResult("speak", HMSResultCreator.getInstance().getStringResult(id), promise);
    }

    /**
     * Resumes engine
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void resume(final Promise promise) {
        startMethodExecTimer("resume");

        if (ttsEngine == null) {
            handleResult("resume", TTS_ENGINE_NULL, promise);
            return;
        }

        ttsEngine.resume();
        handleResult("resume", SUCCESS, promise);
    }

    /**
     * Set engine's volume
     *
     * @param volume the volume of the built-in player, in dBs. The value is in the range of [0, 100]
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void setPlayerVolume(int volume, final Promise promise) {
        startMethodExecTimer("setPlayerVolume");

        if (ttsEngine == null) {
            handleResult("setPlayerVolume", TTS_ENGINE_NULL, promise);
            return;
        }

        ttsEngine.setPlayerVolume(volume);
        handleResult("setPlayerVolume", SUCCESS, promise);
    }

    /**
     * Stops engine
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void stop(final Promise promise) {
        startMethodExecTimer("stop");

        if (ttsEngine == null) {
            handleResult("stop", TTS_ENGINE_NULL, promise);
            return;
        }

        ttsEngine.stop();
        handleResult("stop", SUCCESS, promise);
    }

    /**
     * Pause engine
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void pause(final Promise promise) {
        startMethodExecTimer("pause");

        if (ttsEngine == null) {
            handleResult("pause", TTS_ENGINE_NULL, promise);
            return;
        }

        ttsEngine.pause();
        handleResult("pause", SUCCESS, promise);
    }

    /**
     * Shutdown engine and release engine and config resources
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void shutdown(final Promise promise) {
        startMethodExecTimer("shutdown");

        if (ttsEngine == null) {
            handleResult("shutdown", TTS_ENGINE_NULL, promise);
            return;
        }

        ttsEngine.shutdown();
        ttsEngine = null;
        ttsConfig = null;
        handleResult("shutdown", SUCCESS, promise);
    }

    /**
     * Obtains supported languages.
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getLanguages(final Promise promise) {
        startMethodExecTimer("getLanguages");

        if (ttsEngine == null) {
            handleResult("getLanguages", TTS_ENGINE_NULL, promise);
            return;
        }

        handleResult("getLanguages", HMSResultCreator.getInstance().stringListResult(ttsEngine.getLanguages()),
            promise);
    }

    /**
     * Obtain the speaker of a specified language.
     *
     * @param language language code
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getSpeaker(String language, final Promise promise) {
        startMethodExecTimer("getSpeaker");

        if (TextUtils.isEmpty(language)) {
            handleResult("getSpeaker", STRING_PARAM_NULL, promise);
            return;
        }

        if (ttsEngine == null) {
            handleResult("getSpeaker", STRING_PARAM_NULL, promise);
            return;
        }

        handleResult("getSpeaker", HMSResultCreator.getInstance().getStringResult(language), promise);
    }

    /**
     * Obtain the all speakers
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getSpeakers(final Promise promise) {
        startMethodExecTimer("getSpeakers");

        if (ttsEngine == null) {
            handleResult("getSpeakers", STRING_PARAM_NULL, promise);
            return;
        }

        handleResult("getSpeaker", HMSResultCreator.getInstance().getSpeakers(ttsEngine.getSpeakers()), promise);
    }

    /**
     * Obtains if given language available
     *
     * @param language Language
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void isLanguageAvailable(String language, final Promise promise) {
        startMethodExecTimer("isLanguageAvailable");

        if (TextUtils.isEmpty(language)) {
            handleResult("isLanguageAvailable", STRING_PARAM_NULL, promise);
            return;
        }

        if (ttsEngine == null) {
            handleResult("isLanguageAvailable", TTS_ENGINE_NULL, promise);
            return;
        }

        handleResult("isLanguageAvailable",
            HMSResultCreator.getInstance().integerResult(ttsEngine.isLanguageAvailable(language)), promise);
    }

    /**
     * Updates configuration created before. If no configuration created before, creates a new one
     *
     * @param ttsConfiguration Configration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void updateConfig(ReadableMap ttsConfiguration, final Promise promise) {
        startMethodExecTimer("updateConfig");

        if (ttsEngine == null) {
            handleResult("updateConfig", TTS_ENGINE_NULL, promise);
            return;
        }

        ttsConfig = HMSObjectCreator.getInstance().createTtsConfiguration(ttsConfiguration);
        ttsEngine.updateConfig(ttsConfig);
        ttsEngine.setTtsCallback(this);
        handleResult("updateConfig", SUCCESS, promise);
    }

    /**
     * onError Callback
     *
     * @param taskId The id of the task
     * @param mlTtsError Error
     */
    @Override
    public void onError(String taskId, MLTtsError mlTtsError) {
        WritableMap wm = Arguments.createMap();
        wm.putString("taskId", taskId);
        wm.putString("errorMessage", mlTtsError.getErrorMsg());
        wm.putInt("errorId", mlTtsError.getErrorId());
        sendEvent(TTS_ON_ERROR, "MLTtsCallback", wm);
    }

    /**
     * onWarn Callback
     *
     * @param taskId The id of the task
     * @param mlTtsWarn Warn
     */
    @Override
    public void onWarn(String taskId, MLTtsWarn mlTtsWarn) {
        WritableMap wm = Arguments.createMap();
        wm.putString("taskId", taskId);
        wm.putString("warningMessage", mlTtsWarn.getWarnMsg());
        wm.putInt("warningId", mlTtsWarn.getWarnId());
        sendEvent(TTS_ON_WARN, "MLTtsCallback", wm);
    }

    /**
     * onRangeStart Callback
     *
     * @param taskId The id of the task
     * @param start Start range
     * @param end End range
     */
    @Override
    public void onRangeStart(String taskId, int start, int end) {
        WritableMap wm = Arguments.createMap();
        wm.putString("taskId", taskId);
        wm.putInt("start", start);
        wm.putInt("end", end);
        sendEvent(TTS_ON_RANGE_START, "MLTtsCallback", wm);
    }

    /**
     * onAudioAvailable Callback
     *
     * @param taskId The id of the task
     * @param mlTtsAudioFragment Audio fragment
     * @param offset Offset value
     * @param range Range
     * @param bundle Bundle
     */
    @Override
    public void onAudioAvailable(String taskId, MLTtsAudioFragment mlTtsAudioFragment, int offset,
        Pair<Integer, Integer> range, Bundle bundle) {
        WritableMap wm = Arguments.createMap();
        wm.putString("taskId", taskId);
        wm.putArray("audioData", HMSUtils.getInstance().convertByteArrToWa(mlTtsAudioFragment.getAudioData()));
        wm.putInt("channelInfo", mlTtsAudioFragment.getChannelInfo());
        wm.putInt("sampleRateInHz", mlTtsAudioFragment.getSampleRateInHz());
        wm.putInt("audioFormat", mlTtsAudioFragment.getAudioFormat());
        wm.putInt("offset", offset);
        wm.putInt("first", range.first);
        wm.putInt("second", range.second);
        sendEvent(TTS_ON_AUDIO_AVAILABLE, "MLTtsCallback", wm);
    }

    /**
     * onEvent Callback
     * @param taskId The id of the task
     * @param eventId The id of the event
     * @param bundle Bundle
     */
    @Override
    public void onEvent(String taskId, int eventId, Bundle bundle) {
        WritableMap wm = Arguments.createMap();
        wm.putString("taskId", taskId);
        wm.putInt("eventId", eventId);
        sendEvent(TTS_ON_EVENT, "MLTtsCallback", wm);
    }
}
