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

package com.huawei.hms.rn.ml.facebodyrelatedservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.LIVENESS_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.CURRENT_ACTIVITY_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import android.app.Activity;

import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCapture;
import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCaptureResult;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

public class HMSLivenessDetection extends HMSBase implements MLLivenessCapture.Callback {
    private Promise livenessDetectionPromise;

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSLivenessDetection(ReactApplicationContext reactContext) {
        super(reactContext, HMSLivenessDetection.class.getSimpleName(), LIVENESS_CONSTANTS);
    }

    /**
     * Enables liveness detection.
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void startDetect(final Promise promise) {
        startMethodExecTimer("startDetect");
        Activity mActivity = getCurrentActivity();

        if (mActivity == null) {
            handleResult("startDetect", CURRENT_ACTIVITY_NULL, promise);
            return;
        }

        MLLivenessCapture.getInstance().startDetect(mActivity, this);
        livenessDetectionPromise = promise;
    }

    /**
     * Sets the liveness detection capture config
     *
     * @param config configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void setConfig(ReadableMap config, final Promise promise) {
        startMethodExecTimer("startDetect");
        MLLivenessCapture.getInstance().setConfig(HMSObjectCreator.getInstance().createLivenessCaptureConfig(config));
        handleResult("setConfig", SUCCESS, promise);
    }

    /**
     * onSuccess callback
     * @param result The result that will be resolve
     */
    @Override
    public void onSuccess(MLLivenessCaptureResult result) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableMap rm = Arguments.createMap();
        rm.putDouble("pitch", result.getPitch());
        rm.putDouble("roll", result.getRoll());
        rm.putDouble("score", result.getScore());
        rm.putDouble("yaw", result.getYaw());
        rm.putBoolean("isLive", result.isLive());
        wm.putMap("result", rm);
        handleResult("MLLivenessCapture.Callback", wm, livenessDetectionPromise);
        livenessDetectionPromise = null;
    }

    /**
     * onFailure callback
     * @param errorCode Error code
     */
    @Override
    public void onFailure(int errorCode) {
        handleResult("MLLivenessCapture.Callback", FAILURE.getStatusAndMessage(errorCode, null),
            livenessDetectionPromise);
        livenessDetectionPromise = null;
    }

}
