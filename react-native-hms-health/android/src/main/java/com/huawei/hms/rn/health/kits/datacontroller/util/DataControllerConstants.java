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

package com.huawei.hms.rn.health.kits.datacontroller.util;

import static com.huawei.hms.rn.health.foundation.constant.Constants.DATA_COLLECTOR_CONSTANTS;
import static com.huawei.hms.rn.health.foundation.constant.Constants.DATA_TYPES_MAP;
import static com.huawei.hms.rn.health.foundation.constant.Constants.FIELD_TYPES_MAP;
import static com.huawei.hms.rn.health.foundation.constant.Constants.FIELD_VALUE_ENUMS_MAP;
import static com.huawei.hms.rn.health.foundation.constant.Constants.SLEEP_STATE_CONSTANTS;
import static com.huawei.hms.rn.health.foundation.constant.Constants.TIME_UNITS_MAP;

import com.huawei.hms.hihealth.HiHealthOptions;
import com.huawei.hms.rn.health.kits.datacontroller.HmsDataController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link HmsDataController} Constant Values.
 *
 * @since v.5.0.1
 */
public interface DataControllerConstants {

    /**
     * Constant Variable Keys That will be Used in {@link HmsDataController}.
     */
    String DATA_STREAM_NAME_KEY = "dataStreamName";
    String DEVICE_ID_KEY = "deviceId";
    String DEVICE_INFO_KEY = "deviceInfo";
    String DATA_COLLECTOR_NAME_KEY = "dataCollectorName";
    String IS_LOCALIZED_KEY = "isLocalized";
    String DATA_GENERATE_TYPE_KEY = "dataGenerateType";
    String GROUP_BY_TIME_KEY = "groupByTime";

    /**
     * whole base constants variables as Map
     **/
    Map<String, Object> DATA_CONSTANTS_MAP = initMap();

    /**
     * Initializes Constants map.
     *
     * @return Map<String, Object> Constants map.
     */
    static Map<String, Object> initMap() {
        Map<String, Object> constantMap = new HashMap<>();

        constantMap.putAll(FIELD_TYPES_MAP);
        constantMap.putAll(TIME_UNITS_MAP);
        constantMap.putAll(DATA_TYPES_MAP);
        constantMap.putAll(FIELD_VALUE_ENUMS_MAP);
        constantMap.putAll(SLEEP_STATE_CONSTANTS);
        constantMap.putAll(DATA_COLLECTOR_CONSTANTS);

        constantMap.put("AUTO_RECORDER_POINT_LISTENER", "onSamplePointListener");
        constantMap.put("ACCESS_READ", HiHealthOptions.ACCESS_READ);
        constantMap.put("ACCESS_WRITE", HiHealthOptions.ACCESS_WRITE);

        return Collections.unmodifiableMap(constantMap);
    }
}
