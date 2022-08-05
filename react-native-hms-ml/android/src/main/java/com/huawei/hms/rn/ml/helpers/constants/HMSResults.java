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

package com.huawei.hms.rn.ml.helpers.constants;

import com.huawei.hms.mlsdk.common.MLException;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public enum HMSResults {
    UNKNOWN(MLException.UNKNOWN, ""),
    SUCCESS(MLException.SUCCESS, "Success"),
    FRAME_NULL(20, "No frame object created with given parameters"),
    ANALYZER_NOT_AVAILABLE(21, "Analyzer is not available"),
    CURRENT_ACTIVITY_NULL(22, "Current activity is null"),
    CANCEL(23, "Recognition cancelled by user"),
    FAILURE(24, "Recognition failed"),
    DENY(25, "Camera does not support BCR"),
    STRING_PARAM_NULL(26, "Given string parameter is null or empty"),
    REMOTE_MODEL_NULL(27, "No remote model object created with given parameters"),
    ASR_RECOGNIZER_NULL(28, "Asr recognizer is null or not created"),
    TTS_ENGINE_NULL(30, "Tts engine is not created"),
    SOUND_DECT_NULL(31, "Sound detector is not initialized"),
    CUSTOM_MODEL_SETTING_NULL(32, "Input output setting is not initialized with given parameters"),
    CUSTOM_MODEL_INPUT_NULL(33, "Model inputs is not initialized with given parameters"),
    CUSTOM_MODEL_EXECUTOR_SETTING_NULL(34, "Model executor setting is not initialized with given parameters"),
    DATA_SET_NOT_VALID(35, "Data set is not valid."),
    LENS_ENGINE_NULL(36, "LensEngine is not created"),
    LENS_HOLDER_NULL(37, "LensEngine holder is null");

    private int errCode;

    private String message;

    HMSResults(int errCode, String message) {
        this.errCode = errCode;
        this.message = message;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Combines status and message
     *
     * @return WritableMap
     */
    public WritableMap getStatusAndMessage() {
        WritableMap wm = Arguments.createMap();
        wm.putInt("status", this.errCode);
        wm.putString("message", this.message);
        return wm;
    }

    /**
     * Combines status and message with optional parameters
     *
     * @param errorCode Error code
     * @param errorMessage Error message
     * @return WritableMap
     */
    public WritableMap getStatusAndMessage(Integer errorCode, String errorMessage) {
        WritableMap wm = Arguments.createMap();
        wm.putInt("status", errorCode == null ? this.errCode : errorCode);
        wm.putString("message", errorMessage == null ? this.message : errorMessage);
        return wm;
    }
}
