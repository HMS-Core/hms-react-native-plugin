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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.LANGUAGE_DETECTION_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.STRING_PARAM_NULL;

import android.text.TextUtils;
import android.util.Log;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.langdetect.MLDetectedLang;
import com.huawei.hms.mlsdk.langdetect.cloud.MLRemoteLangDetector;
import com.huawei.hms.mlsdk.langdetect.local.MLLocalLangDetector;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import java.util.List;

public class HMSLanguageDetection extends HMSBase {

    /**
     * Initializes module
     *
     * @param context app context
     */
    public HMSLanguageDetection(ReactApplicationContext context) {
        super(context, HMSLanguageDetection.class.getSimpleName(), LANGUAGE_DETECTION_CONSTANTS);
    }

    /**
     * Returns multi-language detection results based on the supplied text
     *
     * @param isRemote on-cloud or on-device detection
     * @param isStop calls stop if true
     * @param trustedThreshold trust threshold
     * @param sourceText text to be detect
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void probabilityDetect(boolean isRemote, boolean isStop, double trustedThreshold, String sourceText,
        final Promise promise) {
        startMethodExecTimer("probabilityDetect");

        if (TextUtils.isEmpty(sourceText)) {
            handleResult("probabilityDetect", STRING_PARAM_NULL, promise);
            return;
        }

        if (isRemote) {
            MLRemoteLangDetector langDetector = HMSObjectCreator.getInstance()
                .createRemoteLanguageDetector(trustedThreshold);
            handleProbabilityTask(isStop, langDetector, langDetector.probabilityDetect(sourceText), promise);
        } else {
            MLLocalLangDetector langDetector = HMSObjectCreator.getInstance()
                .createLocalLanguageDetector(trustedThreshold);
            handleProbabilityTask(isStop, langDetector, langDetector.probabilityDetect(sourceText), promise);
        }
    }

    /**
     * Returns the language detection result with the highest confidence based on the supplied text.
     *
     * @param isRemote on-cloud or on-device detection
     * @param isStop if true releases resources for detector.
     * @param trustedThreshold trust threshold for detection
     * @param sourceText text to be detect
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void firstBestDetect(boolean isRemote, boolean isStop, double trustedThreshold, String sourceText,
        final Promise promise) {
        startMethodExecTimer("firstBestDetect");

        if (TextUtils.isEmpty(sourceText)) {
            handleResult("firstBestDetect", STRING_PARAM_NULL, promise);
            return;
        }

        if (isRemote) {
            MLRemoteLangDetector langDetector = HMSObjectCreator.getInstance()
                .createRemoteLanguageDetector(trustedThreshold);
            handleFirstBestTask(isStop, langDetector, langDetector.firstBestDetect(sourceText), promise);

        } else {
            MLLocalLangDetector langDetector = HMSObjectCreator.getInstance()
                .createLocalLanguageDetector(trustedThreshold);
            handleFirstBestTask(isStop, langDetector, langDetector.firstBestDetect(sourceText), promise);
        }
    }

    /**
     * Synchronously returns multi-language detection results based on the supplied text.
     *
     * @param isRemote on-cloud or on-device detection
     * @param isStop if true releases resources for detector.
     * @param trustedThreshold trust threshold for detection
     * @param sourceText text to be detect
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void syncProbabilityDetect(boolean isRemote, boolean isStop, double trustedThreshold, String sourceText,
        final Promise promise) {
        startMethodExecTimer("syncProbabilityDetect");

        if (TextUtils.isEmpty(sourceText)) {
            handleResult("syncProbabilityDetect", STRING_PARAM_NULL, promise);
            return;
        }

        Object detector = isRemote
            ? HMSObjectCreator.getInstance().createRemoteLanguageDetector(trustedThreshold)
            : HMSObjectCreator.getInstance().createLocalLanguageDetector(trustedThreshold);
        try {
            WritableMap wm = HMSResultCreator.getInstance()
                .getLangDetectionResult(
                    detector instanceof MLRemoteLangDetector ? ((MLRemoteLangDetector) detector).syncProbabilityDetect(
                        sourceText) : ((MLLocalLangDetector) detector).syncProbabilityDetect(sourceText));

            if (isStop) {
                handleStop(detector);
            }

            handleResult("syncProbabilityDetect", wm, promise);
        } catch (MLException e) {
            if (isStop) {
                handleStop(detector);
            }

            handleResult("syncProbabilityDetect", e, promise);
        }

    }

    /**
     * Synchronously returns the language detection result with the highest confidence based on the supplied text.
     *
     * @param isRemote on-cloud or on-device detection
     * @param isStop if true releases resources for detector.
     * @param trustedThreshold trust threshold for detection
     * @param sourceText text to be detect
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void syncFirstBestDetect(boolean isRemote, boolean isStop, double trustedThreshold, String sourceText,
        final Promise promise) {
        startMethodExecTimer("syncFirstBestDetect");

        if (TextUtils.isEmpty(sourceText)) {
            handleResult("syncFirstBestDetect", STRING_PARAM_NULL, promise);
            return;
        }

        Object detector = isRemote
            ? HMSObjectCreator.getInstance().createRemoteLanguageDetector(trustedThreshold)
            : HMSObjectCreator.getInstance().createLocalLanguageDetector(trustedThreshold);

        try {
            WritableMap wm = HMSResultCreator.getInstance()
                .getStringResult(
                    detector instanceof MLRemoteLangDetector ? ((MLRemoteLangDetector) detector).syncFirstBestDetect(
                        sourceText) : ((MLLocalLangDetector) detector).syncFirstBestDetect(sourceText));

            if (isStop) {
                handleStop(detector);
            }

            handleResult("syncFirstBestDetect", wm, promise);
        } catch (MLException e) {
            if (isStop) {
                handleStop(detector);
            }

            handleResult("syncFirstBestDetect", e, promise);
        }
    }

    private void handleProbabilityTask(boolean isStop, Object detector, Task<List<MLDetectedLang>> task,
        Promise promise) {
        task.addOnSuccessListener(detectedLanguages -> {
            if (isStop) {
                handleStop(detector);
            }

            handleResult("probabilityDetect", HMSResultCreator.getInstance().getLangDetectionResult(detectedLanguages),
                promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                handleStop(detector);
            }

            handleResult("probabilityDetect", e, promise);
        });
    }

    private void handleFirstBestTask(boolean isStop, Object detector, Task<String> task, Promise promise) {
        task.addOnSuccessListener(s -> {
            if (isStop) {
                handleStop(detector);
            }

            handleResult("firstBestDetect", HMSResultCreator.getInstance().getStringResult(s), promise);
        }).addOnFailureListener(e -> {
            if (isStop) {
                handleStop(detector);
            }

            handleResult("firstBestDetect", e, promise);
        });
    }

    private void handleStop(Object detector) {
        if (detector instanceof MLRemoteLangDetector) {
            ((MLRemoteLangDetector) detector).stop();
            Log.i(getName(), "MLRemoteLangDetector stop");
        } else {
            ((MLLocalLangDetector) detector).stop();
            Log.i(getName(), "MLLocalLangDetector stop");
        }
    }
}