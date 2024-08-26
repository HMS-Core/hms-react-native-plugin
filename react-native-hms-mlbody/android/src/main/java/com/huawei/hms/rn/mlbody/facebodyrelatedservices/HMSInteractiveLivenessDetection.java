/*
 * Copyright 2023-2024. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.hms.rn.mlbody.facebodyrelatedservices;

import static com.huawei.hms.rn.mlbody.helpers.constants.HMSConstants.INTERACTIVE_LIVENESS_CONSTANTS;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.CURRENT_ACTIVITY_NULL;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.SUCCESS;

import android.app.Activity;
import android.util.Log;

import com.huawei.hms.mlsdk.interactiveliveness.MLInteractiveLivenessCapture;
import com.huawei.hms.mlsdk.interactiveliveness.MLInteractiveLivenessCaptureResult;
import com.huawei.hms.mlsdk.interactiveliveness.action.InteractiveLivenessStateCode;
import com.huawei.hms.rn.mlbody.HMSBase;
import com.huawei.hms.rn.mlbody.helpers.creators.HMSObjectCreator;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

public class HMSInteractiveLivenessDetection extends HMSBase implements MLInteractiveLivenessCapture.Callback {
    private static final String TAG = HMSInteractiveLivenessDetection.class.getSimpleName();

    private Promise interactiveLivenessDetectionPromise;

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSInteractiveLivenessDetection(ReactApplicationContext reactContext) {
        super(reactContext, HMSInteractiveLivenessDetection.class.getSimpleName(), INTERACTIVE_LIVENESS_CONSTANTS);
    }

    /**
     * Enables interactiveLiveness detection.
     *
     * @param promise A Promise that resolves a result object
     */

    @ReactMethod
    public void startDetect(ReadableMap config, final Promise promise) {

        startMethodExecTimer("startDetect");
        Activity mActivity = getCurrentActivity();
        if (mActivity == null) {
            handleResult("startDetect", CURRENT_ACTIVITY_NULL, promise);
            return;
        }
        MLInteractiveLivenessCapture capture = MLInteractiveLivenessCapture.getInstance();
        capture.setConfig(HMSObjectCreator.getInstance().createInteractiveLivenessCaptureConfig(config));
        capture.startDetect(mActivity, this);
        interactiveLivenessDetectionPromise = promise;
    }

    /**
     * onSuccess callback
     *
     * @param result The result that will be resolve
     */

    @Override
    public void onSuccess(MLInteractiveLivenessCaptureResult result) {

        switch (result.getStateCode()) {
            case InteractiveLivenessStateCode.ALL_ACTION_CORRECT:
                WritableMap wm = SUCCESS.getStatusAndMessage();
                WritableMap rm = Arguments.createMap();
                rm.putInt("status", result.getStateCode());
                rm.putInt("action", result.getActionType());
                rm.putString("bitMap", HMSObjectCreator.bitmapToBase64(result.getBitmap()));
                wm.putMap("result", rm);
                handleResult("MLInteractiveLivenessCapture.Callback", wm, interactiveLivenessDetectionPromise);
                interactiveLivenessDetectionPromise = null;
                break;
            case InteractiveLivenessStateCode.IN_PROGRESS:

                Log.i(TAG, "In Progress");
                break;
            default:
                break;
        }
    }

    /**
     * onFailure callback
     *
     * @param errorCode Error code
     */

    @Override
    public void onFailure(int errorCode) {
        if (interactiveLivenessDetectionPromise != null) {
            handleResult("MLInteractiveLivenessCapture.Callback", FAILURE.getStatusAndMessage(errorCode, null),
                interactiveLivenessDetectionPromise);
            interactiveLivenessDetectionPromise = null;
        }
    }
}