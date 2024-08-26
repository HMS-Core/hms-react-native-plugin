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

package com.huawei.hms.rn.mllanguage.helpers.creators;

import static com.huawei.hms.rn.mllanguage.helpers.constants.HMSResults.SUCCESS;

import android.text.TextUtils;

import com.huawei.hms.mlsdk.aft.cloud.MLRemoteAftResult;
import com.huawei.hms.mlsdk.custom.MLModelOutputs;
import com.huawei.hms.mlsdk.langdetect.MLDetectedLang;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.speechrtt.MLSpeechRealTimeTranscriptionResult;
import com.huawei.hms.mlsdk.tts.MLTtsSpeaker;
import com.huawei.hms.rn.mllanguage.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;


import java.io.File;
import java.util.List;
import java.util.Set;

public class HMSResultCreator {
    private static volatile HMSResultCreator instance;

    public static HMSResultCreator getInstance() {
        if (instance == null) {
            synchronized (HMSResultCreator.class) {
                if (instance == null) {
                    instance = new HMSResultCreator();
                }
            }
        }
        return instance;
    }

    /**
     * Converts aft results to WritableArray
     *
     * @param results aft results
     * @return WritableArray
     */
    public WritableArray getAftResult(List<MLRemoteAftResult.Segment> results) {
        WritableArray array = Arguments.createArray();
        for (MLRemoteAftResult.Segment segment : results) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putInt("startTime", segment.getStartTime());
            writableMap.putInt("endTime", segment.getEndTime());
            writableMap.putString("text", segment.getText());
            array.pushMap(writableMap);
        }
        return array;
    }

    /**
     * Convert detected language list to WritableMap
     *
     * @param languageList detected language list
     * @return WritableMap
     */
    public WritableMap getLangDetectionResult(List<MLDetectedLang> languageList) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray array = Arguments.createArray();
        for (MLDetectedLang lang : languageList) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("languageCode", lang.getLangCode());
            writableMap.putDouble("probability", lang.getProbability());
            array.pushMap(writableMap);
        }
        wm.putArray("result", array);
        return wm;
    }

    /**
     * Converts speech rtt result to WritableMap
     *
     * @param rttResults result list
     * @return WritableMap
     */
    public WritableMap getRttResult(List<MLSpeechRealTimeTranscriptionResult> rttResults) {
        WritableMap wm = Arguments.createMap();
        WritableArray array = Arguments.createArray();
        for (MLSpeechRealTimeTranscriptionResult result : rttResults) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("startTime", result.startTime);
            writableMap.putString("endTime", result.endTime);
            writableMap.putString("text", result.text);
            array.pushMap(writableMap);
        }
        wm.putArray("result", array);
        return wm;
    }

    /**
     * Converts speaker list to WritableMap
     *
     * @param speakers speaker list
     * @return WritableMap
     */
    public WritableMap getSpeakers(List<MLTtsSpeaker> speakers) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray array = Arguments.createArray();
        for (MLTtsSpeaker speaker : speakers) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("language", speaker.getLanguage());
            writableMap.putString("name", speaker.getName());
            array.pushMap(writableMap);
        }
        wm.putArray("result", array);
        return wm;
    }


    /**
     * returns CustomModel exec method result
     *
     * @param mlModelOutputs method result
     * @return WritableMap
     */
    public WritableMap customModelResult(MLModelOutputs mlModelOutputs) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", HMSUtils.getInstance().convert2DFloatArrToWa(mlModelOutputs.getOutput(0)));
        return wm;
    }

    /**
     * Returns getModels method result
     *
     * @param models remote models
     * @return WritableMap
     */
    public WritableMap getModels(Set<MLRemoteModel> models) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        for (MLRemoteModel s : models) {
            wa.pushString(s.getModelName());
        }
        wm.putArray("result", wa);
        return wm;
    }

    /**
     * Recent model file method result
     *
     * @param file file object
     * @return WritableMap
     */
    public WritableMap getFilePathResult(File file) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putString("result", file.getPath());
        return wm;
    }



    /**
     * converts integer result to WritableMap
     *
     * @param integer method result
     * @return WritableMap
     */
    public WritableMap integerResult(int integer) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putInt("result", integer);
        return wm;
    }

    /**
     * converts integer result to WritableMap
     *
     * @param set method result
     * @return WritableMap
     */
    public WritableMap stringSetResult(Set<String> set) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        if (set != null) {
            for (String element : set) {
                wa.pushString(element);
            }
        }
        wm.putArray("result", wa);
        return wm;
    }

    /**
     * converts string result to WritableMap
     *
     * @param string result
     * @return WritableMap
     */
    public WritableMap getStringResult(String string) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putString("result", TextUtils.isEmpty(string) ? "" : string);
        return wm;
    }

    /**
     * converts string result to WritableMap
     *
     * @param is result
     * @return WritableMap
     */
    public WritableMap getBooleanResult(boolean is) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putBoolean("result", is);
        return wm;
    }


    /**
     * Converts string list to result
     *
     * @param list data list
     * @return WritableMap
     */
    public WritableMap stringListResult(List<String> list) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", HMSUtils.getInstance().convertStringListIntoWa(list));
        return wm;
    }

}
