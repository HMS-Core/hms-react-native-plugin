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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.SPEECH_RTT_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.SPEECH_RTT_ON_ERROR;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.SPEECH_RTT_ON_LISTENING;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.SPEECH_RTT_ON_RECOGNIZING_RESULTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.SPEECH_RTT_ON_STARTING_OF_SPEECH;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.SPEECH_RTT_ON_STATE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.SPEECH_RTT_ON_VOICE_DATA_RECEIVED;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import android.os.Bundle;
import android.text.TextUtils;

import com.huawei.hms.mlsdk.speechrtt.MLSpeechRealTimeTranscription;
import com.huawei.hms.mlsdk.speechrtt.MLSpeechRealTimeTranscriptionConstants;
import com.huawei.hms.mlsdk.speechrtt.MLSpeechRealTimeTranscriptionListener;
import com.huawei.hms.mlsdk.speechrtt.MLSpeechRealTimeTranscriptionResult;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.ArrayList;
import java.util.List;

public class HMSSpeechRtt extends HMSBase
    implements MLSpeechRealTimeTranscriptionListener, MLSpeechRealTimeTranscription.LanguageCallback {
    private Promise languageListPromise;

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSSpeechRtt(ReactApplicationContext reactContext) {
        super(reactContext, HMSSpeechRtt.class.getSimpleName(), SPEECH_RTT_CONSTANTS);
    }

    /**
     * Starts recognition.
     *
     * @param realTimeTranscriptionConfiguration recognition configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void startRecognizing(ReadableMap realTimeTranscriptionConfiguration, final Promise promise) {
        startMethodExecTimer("startRecognizing");
        MLSpeechRealTimeTranscription.getInstance()
            .startRecognizing(HMSObjectCreator.getInstance()
                .createSpeechRealtimeTranscriptionConfig(realTimeTranscriptionConfiguration));
        handleResult("startRecognizing", SUCCESS, promise);
    }

    /**
     * Obtains languages supported for real-time audio transcription
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getLanguages(final Promise promise) {
        startMethodExecTimer("getLanguages");
        MLSpeechRealTimeTranscription.getInstance().getLanguages(this);
        languageListPromise = promise;
    }

    /**
     * Sets listener to obtain the results
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void setRealTimeTranscriptionListener(final Promise promise) {
        startMethodExecTimer("setRealtimeTranscriptionListener");
        MLSpeechRealTimeTranscription.getInstance().setRealTimeTranscriptionListener(this);
        handleResult("setRealtimeTranscriptionListener", SUCCESS, promise);
    }

    /**
     * Stops recognition and releases resources.
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void destroy(final Promise promise) {
        startMethodExecTimer("destroy");
        MLSpeechRealTimeTranscription.getInstance().destroy();
        handleResult("destroy", SUCCESS, promise);
    }

    /**
     * onRecognizingResults callback
     *
     * @param bundle Bundle
     */
    @Override
    public void onRecognizingResults(Bundle bundle) {
        WritableMap wm = Arguments.createMap();
        if (bundle != null) {
            wm.putString("text", bundle.getString(MLSpeechRealTimeTranscriptionConstants.RESULTS_RECOGNIZING, ""));
            ArrayList<MLSpeechRealTimeTranscriptionResult> sentenceOffset = bundle.getParcelableArrayList(
                MLSpeechRealTimeTranscriptionConstants.RESULTS_SENTENCE_OFFSET);
            wm.putMap("sentenceOffset", sentenceOffset == null
                ? Arguments.createMap()
                : HMSResultCreator.getInstance().getRttResult(sentenceOffset));
            ArrayList<MLSpeechRealTimeTranscriptionResult> wordOffset = bundle.getParcelableArrayList(
                MLSpeechRealTimeTranscriptionConstants.RESULTS_WORD_OFFSET);
            wm.putMap("wordOffset",
                wordOffset == null ? Arguments.createMap() : HMSResultCreator.getInstance().getRttResult(wordOffset));
            wm.putBoolean("isComplete",
                bundle.getBoolean(MLSpeechRealTimeTranscriptionConstants.RESULTS_PARTIALFINAL, false));
        }
        sendEvent(SPEECH_RTT_ON_RECOGNIZING_RESULTS, "MLSpeechRealTimeTranscriptionListener", wm);
    }

    @Override
    public void onResult(List<String> list) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        for (String language : list) {
            wa.pushString(language);
        }
        wm.putArray("result", wa);
        handleResult("MLSpeechRealTimeTranscription.LanguageCallback", wm, languageListPromise);
        languageListPromise = null;
    }

    /**
     * onError callback
     *
     * @param error Error
     * @param errorMessage The error message
     */
    @Override
    public void onError(int error, String errorMessage) {
        WritableMap wm = Arguments.createMap();
        wm.putInt("error", error);
        wm.putString("errorMessage", TextUtils.isEmpty(errorMessage) ? "" : errorMessage);
        sendEvent(SPEECH_RTT_ON_ERROR, "MLSpeechRealTimeTranscriptionListener", wm);
    }

    /**
     * onStartListening callback
     */
    @Override
    public void onStartListening() {
        WritableMap wm = Arguments.createMap();
        wm.putString("info", "Listening start");
        sendEvent(SPEECH_RTT_ON_LISTENING, "MLSpeechRealTimeTranscriptionListener", wm);
    }

    /**
     * onStartingOfSpeech callback
     */
    @Override
    public void onStartingOfSpeech() {
        WritableMap wm = Arguments.createMap();
        wm.putString("info", "Speech start");
        sendEvent(SPEECH_RTT_ON_STARTING_OF_SPEECH, "MLSpeechRealTimeTranscriptionListener", wm);
    }

    /**
     * onVoiceDataReceived callback
     *
     * @param data Data in bytes.
     * @param energy Data energy
     * @param bundle Bundle
     */
    @Override
    public void onVoiceDataReceived(byte[] data, float energy, Bundle bundle) {
        WritableMap wm = Arguments.createMap();
        wm.putArray("data", HMSUtils.getInstance().convertByteArrToWa(data));
        wm.putDouble("energy", energy);
        wm.putString("encoding", bundle.getString(MLSpeechRealTimeTranscriptionConstants.WAVE_PAINE_ENCODING, ""));
        wm.putInt("sampleRate", bundle.getInt(MLSpeechRealTimeTranscriptionConstants.WAVE_PAINE_SAMPLE_RATE, 0));
        wm.putInt("channelCount", bundle.getInt(MLSpeechRealTimeTranscriptionConstants.WAVE_PAINE_CHANNEL_COUNT, 0));
        wm.putInt("bitWidth", bundle.getInt(MLSpeechRealTimeTranscriptionConstants.WAVE_PAINE_BIT_WIDTH, 0));
        sendEvent(SPEECH_RTT_ON_VOICE_DATA_RECEIVED, "MLSpeechRealTimeTranscriptionListener", wm);
    }

    /**
     * onState callback
     *
     * @param state State
     * @param bundle Bundle
     */
    @Override
    public void onState(int state, Bundle bundle) {
        WritableMap wm = Arguments.createMap();
        wm.putInt("state", state);
        sendEvent(SPEECH_RTT_ON_STATE, "MLSpeechRealTimeTranscriptionListener", wm);
    }
}