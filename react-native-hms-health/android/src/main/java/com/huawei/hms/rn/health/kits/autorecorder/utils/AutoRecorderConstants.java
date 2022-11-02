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

package com.huawei.hms.rn.health.kits.autorecorder.utils;

import com.huawei.hms.rn.health.kits.autorecorder.HmsAutoRecorderController;

/**
 * {@link HmsAutoRecorderController} Constant Values.
 *
 * @since v.5.0.1
 */
public interface AutoRecorderConstants {
    /**
     * Constant Variable Keys That Will be Used in {@link HmsAutoRecorderController}.
     */
    String ACCURACY_LEVEL_KEY = "accuracyLevel";
    String SAMPLING_RATE_KEY = "samplingRate";
    String DATA_COLLECTOR_KEY = "dataCollector";
    String BACKGROUND_SERVICE_KEY = "HealthKitService";

    /**
     * {@link HmsAutoRecorderController} OnComplete Event types.
     */
    enum OnCompleteEventType {
        /**
         * START RECORD TYPES
         */
        START_RECORD_BY_TYPE("onCompleteStartRecordByType"),
        START_RECORD_BY_COLLECTOR("onCompleteStartRecordByCollector"),

        /**
         * STOP RECORD TYPES
         */
        STOP_RECORD_BY_RECORD("onCompleteStopRecordByRecord"),
        STOP_RECORD_BY_TYPE("onCompleteStopRecordByType"),
        STOP_RECORD_BY_COLLECTOR("onCompleteStopRecordByCollector");

        private final String value;

        OnCompleteEventType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
