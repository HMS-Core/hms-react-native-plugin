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

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.SOUND_DETECT_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.SOUND_DETECT_ON_FAILURE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.SOUND_DETECT_ON_SUCCESS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SOUND_DECT_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import android.os.Bundle;

import com.huawei.hms.mlsdk.sounddect.MLSoundDetectListener;
import com.huawei.hms.mlsdk.sounddect.MLSoundDetector;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

public class HMSSoundDetect extends HMSBase implements MLSoundDetectListener {
    private MLSoundDetector soundDetector;

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSSoundDetect(ReactApplicationContext reactContext) {
        super(reactContext, HMSSoundDetect.class.getSimpleName(), SOUND_DETECT_CONSTANTS);
        soundDetector = null;
    }

    /**
     * Creates sound detector
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void createSoundDetector(final Promise promise) {
        startMethodExecTimer("createSoundDetector");
        soundDetector = MLSoundDetector.createSoundDetector();
        handleResult("createSoundDetector", SUCCESS, promise);
    }

    /**
     * Destroys sound detector and releases resources
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void destroy(final Promise promise) {
        startMethodExecTimer("destroy");

        if (soundDetector == null) {
            handleResult("destroy", SOUND_DECT_NULL, promise);
            return;
        }

        soundDetector.destroy();
        soundDetector = null;
        handleResult("destroy", SUCCESS, promise);
    }

    /**
     * Stops sound detector
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void stop(final Promise promise) {
        startMethodExecTimer("stop");

        if (soundDetector == null) {
            handleResult("stop", SOUND_DECT_NULL, promise);
            return;
        }

        soundDetector.stop();
        handleResult("stop", SUCCESS, promise);
    }

    /**
     * Starts sound detector and returns if it started
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void start(final Promise promise) {
        startMethodExecTimer("start");

        if (soundDetector == null) {
            handleResult("start", SOUND_DECT_NULL, promise);
            return;
        }

        boolean isStarted = soundDetector.start(getContext());
        handleResult("start", HMSResultCreator.getInstance().getBooleanResult(isStarted), promise);
    }

    /**
     * Sets listener to obtain results
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void setSoundDetectorListener(final Promise promise) {
        startMethodExecTimer("setSoundDetectorListener");

        if (soundDetector == null) {
            handleResult("setSoundDetectorListener", SOUND_DECT_NULL, promise);
            return;
        }

        soundDetector.setSoundDetectListener(this);
        handleResult("setSoundDetectorListener", SUCCESS, promise);
    }

    /**
     * onSoundSuccessResult callback
     * @param result Result that will be resolve
     */
    @Override
    public void onSoundSuccessResult(Bundle result) {
        WritableMap wm = Arguments.createMap();
        wm.putInt("soundType", result.getInt(MLSoundDetector.RESULTS_RECOGNIZED, -1));
        sendEvent(SOUND_DETECT_ON_SUCCESS, "MLSoundDectListener", wm);
    }

    /**
     * onSoundFailResult callback
     * @param errorCode Error code
     */
    @Override
    public void onSoundFailResult(int errorCode) {
        WritableMap wm = Arguments.createMap();
        wm.putInt("errorCode", errorCode);
        sendEvent(SOUND_DETECT_ON_FAILURE, "MLSoundDectListener", wm);
    }
}
