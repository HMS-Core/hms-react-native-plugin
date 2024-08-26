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
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.SUCCESS;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.huawei.hms.mlsdk.interactiveliveness.MLInteractiveLivenessCaptureResult;
import com.huawei.hms.rn.mlbody.HMSBase;
import com.huawei.hms.rn.mlbody.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.mlbody.helpers.utils.HMSUtils;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;

import java.util.HashMap;
import java.util.Map;

public class HMSInteractiveLivenessCustomDetectionHandler extends HMSBase implements ActivityEventListener {
    public static final int DETECT_FACE_TIME_OUT = 11404;

    private static final int REQUEST_CODE_DETECT_CUSTOMIZED = 14;

    private static MLInteractiveLivenessCaptureResult detectResult;

    private static final String TAG = HMSInteractiveLivenessCustomDetectionHandler.class.getSimpleName();

    private ReactContext mReactContext;

    private Promise mPromise;

    private HashMap<Integer, String> actionMap = new HashMap<>();

    private HashMap<Integer, String> statusCodeMessageMap = new HashMap<>();

    public HMSInteractiveLivenessCustomDetectionHandler(@NonNull ReactApplicationContext reactContext) {

        super(reactContext, HMSInteractiveLivenessCustomDetectionHandler.class.getSimpleName(),
            INTERACTIVE_LIVENESS_CONSTANTS);
        this.mReactContext = reactContext;
        mReactContext.addActivityEventListener(this);
    }

    public static void setDetectResult(MLInteractiveLivenessCaptureResult result) {
        detectResult = result;
    }

    @ReactMethod
    public void startCustomizedView(ReadableMap config, final Promise promise) {
        mPromise = promise;
        int detectionTimeOut = 10000;
        int textMargin = 0;
        int cameraLeft = 0;
        int cameraRight = 1080;
        int cameraTop = 0;
        int cameraBottom = 1440;
        int faceLeft = 84;
        int faceRight = 396;
        int faceTop = 122;
        int faceBottom = 518;
        int textColor = -16777216;
        float textSize = 20.0F;
        boolean autoSizeText = false;
        int minTextSize = 15;
        int maxTextSize = 30;
        int granularity = 2;
        int num = 0;
        String header = "";
        boolean isRandom = false;
        boolean showStatusCodes = true;

        initStatusCodeList(statusCodeMessageMap);

        if (HMSUtils.getInstance().hasValidKey(config, "showStatusCodes", ReadableType.Boolean)) {
            showStatusCodes = config.getBoolean("showStatusCodes");
        }
        if (HMSUtils.getInstance().hasValidKey(config, "statusCodeMessage", ReadableType.Map)) {
            ReadableMap statusCodeMessage = config.getMap("statusCodeMessage");
            statusCodeMessageMap.clear();
            int[] statusCodeArray = {
                1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1014, 1018, 1019, 1020, 1021, 2000, 2002, 2003,
                2004, 2007, 5020
            };
            for (int i = 0; i < statusCodeArray.length; i++) {
                String keys = String.valueOf(statusCodeArray[i]);
                if (HMSUtils.getInstance().hasValidKey(statusCodeMessage, keys, ReadableType.String)) {
                    statusCodeMessageMap.put(statusCodeArray[i], statusCodeMessage.getString(keys));
                }
            }
        }
        if (HMSUtils.getInstance().hasValidKey(config, "detectionTimeOut", ReadableType.Number)) {
            detectionTimeOut = config.getInt("detectionTimeOut");
        }
        if (HMSUtils.getInstance().hasValidKey(config, "textMargin", ReadableType.Number)) {
            textMargin = config.getInt("textMargin");
        }
        if (HMSUtils.getInstance().hasValidKey(config, "header", ReadableType.String)) {
            header = config.getString("header");
        }

        if (HMSUtils.getInstance().hasValidKey(config, "action", ReadableType.Map)) {
            ReadableMap action = config.getMap("action");

            if (HMSUtils.getInstance().hasValidKey(action, "actionArray", ReadableType.Map)) {
                ReadableMap actionArray = action.getMap("actionArray");

                for (int i = 1; i <= 6; i++) {
                    String key = String.valueOf(i);
                    if (HMSUtils.getInstance().hasValidKey(actionArray, key, ReadableType.String)) {
                        actionMap.put(i, actionArray.getString(key));
                    }
                }
            }
            if (HMSUtils.getInstance().hasValidKey(action, "num", ReadableType.Number)) {
                num = action.getInt("num");
            }
            if (HMSUtils.getInstance().hasValidKey(action, "isRandom", ReadableType.Boolean)) {
                isRandom = action.getBoolean("isRandom");
            }
        }

        if (HMSUtils.getInstance().hasValidKey(config, "cameraFrame", ReadableType.Map)) {
            ReadableMap cameraFrame = config.getMap("cameraFrame");

            if (HMSUtils.getInstance().hasValidKey(cameraFrame, "left", ReadableType.Number)) {
                cameraLeft = cameraFrame.getInt("left");
            }
            if (HMSUtils.getInstance().hasValidKey(cameraFrame, "right", ReadableType.Number)) {
                cameraRight = cameraFrame.getInt("right");
            }
            if (HMSUtils.getInstance().hasValidKey(cameraFrame, "top", ReadableType.Number)) {
                cameraTop = cameraFrame.getInt("top");
            }
            if (HMSUtils.getInstance().hasValidKey(cameraFrame, "bottom", ReadableType.Number)) {
                cameraBottom = cameraFrame.getInt("bottom");
            }
        }

        if (HMSUtils.getInstance().hasValidKey(config, "faceFrame", ReadableType.Map)) {
            ReadableMap faceFrame = config.getMap("faceFrame");

            if (HMSUtils.getInstance().hasValidKey(faceFrame, "left", ReadableType.Number)) {
                faceLeft = faceFrame.getInt("left");
            }
            if (HMSUtils.getInstance().hasValidKey(faceFrame, "right", ReadableType.Number)) {
                faceRight = faceFrame.getInt("right");
            }
            if (HMSUtils.getInstance().hasValidKey(faceFrame, "top", ReadableType.Number)) {
                faceTop = faceFrame.getInt("top");
            }
            if (HMSUtils.getInstance().hasValidKey(faceFrame, "bottom", ReadableType.Number)) {
                faceBottom = faceFrame.getInt("bottom");
            }
        }

        if (HMSUtils.getInstance().hasValidKey(config, "textOptions", ReadableType.Map)) {
            ReadableMap textOptions = config.getMap("textOptions");

            if (HMSUtils.getInstance().hasValidKey(textOptions, "textColor", ReadableType.Number)) {
                textColor = textOptions.getInt("textColor");
            }
            if (HMSUtils.getInstance().hasValidKey(textOptions, "textSize", ReadableType.Number)) {
                textSize = (float) textOptions.getDouble("textSize");
            }
            if (HMSUtils.getInstance().hasValidKey(textOptions, "autoSizeText", ReadableType.Boolean)) {
                autoSizeText = textOptions.getBoolean("autoSizeText");
            }
            if (HMSUtils.getInstance().hasValidKey(textOptions, "minTextSize", ReadableType.Number)) {
                minTextSize = textOptions.getInt("minTextSize");
            }
            if (HMSUtils.getInstance().hasValidKey(textOptions, "maxTextSize", ReadableType.Number)) {
                maxTextSize = textOptions.getInt("maxTextSize");
            }
            if (HMSUtils.getInstance().hasValidKey(textOptions, "granularity", ReadableType.Number)) {
                granularity = textOptions.getInt("granularity");
            }
        }

        for (Map.Entry<Integer, String> entry : actionMap.entrySet()) {
            actionMap.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Integer, String> entry : statusCodeMessageMap.entrySet()) {
            statusCodeMessageMap.put(entry.getKey(), entry.getValue());
        }

        Intent intent = new Intent(mReactContext, HMSInteractiveCustomLivenessDetectionActivity.class);

        if (faceBottom > cameraBottom) {
            faceBottom = cameraBottom;
        }

        if (faceRight > cameraRight) {
            faceRight = cameraRight;
        }

        if (faceTop < cameraTop) {
            faceTop = cameraTop;
        }
        if (faceLeft < cameraLeft) {
            faceLeft = cameraLeft;
        }

        intent.putExtra("detectionTimeOut", detectionTimeOut);
        intent.putExtra("textMargin", textMargin);
        intent.putExtra("cameraLeft", cameraLeft);
        intent.putExtra("cameraRight", cameraRight);
        intent.putExtra("cameraTop", cameraTop);
        intent.putExtra("cameraBottom", cameraBottom);
        intent.putExtra("faceLeft", faceLeft);
        intent.putExtra("faceRight", faceRight);
        intent.putExtra("faceTop", faceTop);
        intent.putExtra("faceBottom", faceBottom);
        intent.putExtra("textColor", textColor);
        intent.putExtra("textSize", textSize);
        intent.putExtra("autoSizeText", autoSizeText);
        intent.putExtra("minTextSize", minTextSize);
        intent.putExtra("maxTextSize", maxTextSize);
        intent.putExtra("granularity", granularity);
        intent.putExtra("num", num);
        intent.putExtra("isRandom", isRandom);
        intent.putExtra("header", header);
        intent.putExtra("showStatusCodes", showStatusCodes);
        intent.putExtra("actionArray", actionMap);
        intent.putExtra("statusCodeMessageMap", statusCodeMessageMap);

        mReactContext.getCurrentActivity().startActivityForResult(intent, REQUEST_CODE_DETECT_CUSTOMIZED);
    }

    void initStatusCodeList(HashMap<Integer, String> statusCodeList) {
        statusCodeList.put(1001, "The face orientation is inconsistent with that of the phone.");
        statusCodeList.put(1002, "No face is detected.");
        statusCodeList.put(1003, "Multiple faces are detected.");
        statusCodeList.put(1004, "The face deviates from the center of the face frame.");
        statusCodeList.put(1005, "The face is too large.");
        statusCodeList.put(1006, "The face is too small.");
        statusCodeList.put(1007, "The face is blocked by the sunglasses.");
        statusCodeList.put(1008, "The face is blocked by the mask.");
        statusCodeList.put(1009, "The detected action is not the required one.");
        statusCodeList.put(1014, "The continuity detection fails.");
        statusCodeList.put(1018, "The light is dark.");
        statusCodeList.put(1019, "The image is blurry.");
        statusCodeList.put(1020, "The face is backlit.");
        statusCodeList.put(1021, "The light is bright.");
        statusCodeList.put(2000, "In progress");
        statusCodeList.put(2002, "The face does not belong to a real person. ");
        statusCodeList.put(2003, "Verification is performed, and the detected action is correct.");
        statusCodeList.put(2004, "Verification succeeded.");
        statusCodeList.put(2007, "The position of the face frame is not set before the algorithm is called.");
        statusCodeList.put(5020, "The previous detection ended when it was not complete.");

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
        WritableMap wmap = SUCCESS.getStatusAndMessage();
        WritableMap rmap = Arguments.createMap();
        if (requestCode == REQUEST_CODE_DETECT_CUSTOMIZED) {
            if (resultCode == Activity.RESULT_OK && detectResult != null) {
                rmap.putInt("status", detectResult.getStateCode());
                rmap.putInt("action", detectResult.getActionType());
                rmap.putString("bitMap", HMSObjectCreator.bitmapToBase64(detectResult.getBitmap()));
                wmap.putMap("result", rmap);
                handleResult("MLInteractiveCustomLivenessCapture.Callback", wmap, mPromise);
                mPromise = null;
            } else if (resultCode == Activity.RESULT_CANCELED && detectResult != null) {
                handleResult("MLInteractiveCustomLivenessCapture.Callback",
                    FAILURE.getStatusAndMessage(resultCode, null), mPromise);
                rmap.putInt("status", resultCode);
                wmap.putMap("result", rmap);
                mPromise = null;
            } else if (resultCode == DETECT_FACE_TIME_OUT) {
                handleResult("MLInteractiveCustomLivenessCapture.Callback",
                    FAILURE.getStatusAndMessage(resultCode, "The face detection module times out."), mPromise);
                rmap.putInt("status", resultCode);
                wmap.putMap("result", rmap);
                mPromise = null;
            }
        }
        Log.i(TAG, "onActivityResult requestCode " + requestCode + ", resultCode " + resultCode);
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}



