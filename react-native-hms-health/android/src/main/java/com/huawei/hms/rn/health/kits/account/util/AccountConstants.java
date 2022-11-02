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

package com.huawei.hms.rn.health.kits.account.util;

import com.huawei.hms.rn.health.kits.account.HmsHealthAccount;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link HmsHealthAccount} Constant Values.
 *
 * @since v.5.0.1
 */
public interface AccountConstants {
    /**
     * {@link HmsHealthAccount} Activity Request types.
     */
    enum RequestTypes {
        SIGN_IN(1022);

        private final int value;

        RequestTypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * {@link HmsHealthAccount} constants.
     */
    enum AccConstants {
        MODULE_NAME("HmsHealthAccount"),

        /* Scopes */
        HEALTHKIT_HEIGHTWEIGHT_READ("https://www.huawei.com/healthkit/heightweight.read"),
        HEALTHKIT_HEIGHTWEIGHT_WRITE("https://www.huawei.com/healthkit/heightweight.write"),
        HEALTHKIT_STEP_READ("https://www.huawei.com/healthkit/step.read"),
        HEALTHKIT_STEP_WRITE("https://www.huawei.com/healthkit/step.write"),
        HEALTHKIT_LOCATION_READ("https://www.huawei.com/healthkit/location.read"),
        HEALTHKIT_LOCATION_WRITE("https://www.huawei.com/healthkit/location.write"),
        HEALTHKIT_HEARTRATE_READ("https://www.huawei.com/healthkit/heartrate.read"),
        HEALTHKIT_HEARTRATE_WRITE("https://www.huawei.com/healthkit/heartrate.write"),
        HEALTHKIT_BLOODGLUCOSE_READ("https://www.huawei.com/healthkit/bloodglucose.read"),
        HEALTHKIT_BLOODGLUCOSE_WRITE("https://www.huawei.com/healthkit/bloodglucose.write"),
        HEALTHKIT_DISTANCE_READ("https://www.huawei.com/healthkit/distance.read"),
        HEALTHKIT_DISTANCE_WRITE("https://www.huawei.com/healthkit/distance.write"),
        HEALTHKIT_SPEED_READ("https://www.huawei.com/healthkit/speed.read"),
        HEALTHKIT_SPEED_WRITE("https://www.huawei.com/healthkit/speed.write"),
        HEALTHKIT_CALORIES_READ("https://www.huawei.com/healthkit/calories.read"),
        HEALTHKIT_CALORIES_WRITE("https://www.huawei.com/healthkit/calories.write"),
        HEALTHKIT_PULMONARY_READ("https://www.huawei.com/healthkit/pulmonary.read"),
        HEALTHKIT_PULMONARY_WRITE("https://www.huawei.com/healthkit/pulmonary.write"),
        HEALTHKIT_STRENGTH_READ("https://www.huawei.com/healthkit/strength.read"),
        HEALTHKIT_STRENGTH_WRITE("https://www.huawei.com/healthkit/strength.write"),
        HEALTHKIT_ACTIVITY_READ("https://www.huawei.com/healthkit/activity.read"),
        HEALTHKIT_ACTIVITY_WRITE("https://www.huawei.com/healthkit/activity.write"),
        HEALTHKIT_BODYFAT_READ("https://www.huawei.com/healthkit/bodyfat.read"),
        HEALTHKIT_BODYFAT_WRITE("https://www.huawei.com/healthkit/bodyfat.write"),
        HEALTHKIT_SLEEP_READ("https://www.huawei.com/healthkit/sleep.read"),
        HEALTHKIT_SLEEP_WRITE("https://www.huawei.com/healthkit/sleep.write"),
        HEALTHKIT_NUTRITION_READ("https://www.huawei.com/healthkit/nutrition.read"),
        HEALTHKIT_NUTRITION_WRITE("https://www.huawei.com/healthkit/nutrition.write"),
        HEALTHKIT_BLOODPRESSURE_READ("https://www.huawei.com/healthkit/bloodpressure.read"),
        HEALTHKIT_BLOODPRESSURE_WRITE("https://www.huawei.com/healthkit/bloodpressure.write"),
        HEALTHKIT_OXYGENSTATURATION_READ("https://www.huawei.com/healthkit/oxygensaturation.read"),
        HEALTHKIT_OXYGENSTATURATION_WRITE("https://www.huawei.com/healthkit/oxygensaturation.write"),
        HEALTHKIT_BODYTEMPERATURE_READ("https://www.huawei.com/healthkit/bodytemperature.read"),
        HEALTHKIT_BODYTEMPERATURE_WRITE("https://www.huawei.com/healthkit/bodytemperature.write"),
        HEALTHKIT_REPRODUCTIVE_READ("https://www.huawei.com/healthkit/reproductive.read"),
        HEALTHKIT_REPRODUCTIVE_WRITE("https://www.huawei.com/healthkit/reproductive.write"),
        HEALTHKIT_ACTIVITY_RECORD_READ("https://www.huawei.com/healthkit/activityrecord.read"),
        HEALTHKIT_ACTIVITY_RECORD_WRITE("https://www.huawei.com/healthkit/activityrecord.write"),
        HEALTHKIT_STRESS_READ("https://www.huawei.com/healthkit/stress.read"),
        HEALTHKIT_STRESS_WRITE("https://www.huawei.com/healthkit/stress.write"),
        HEALTHKIT_HEARTHEALTH_READ("https://www.huawei.com/healthkit/hearthealth.read"),
        HEALTHKIT_HEARTHEALTH_WRITE("https://www.huawei.com/healthkit/hearthealth.write");

        private final String value;

        AccConstants(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Whole base constants variables as Map
     **/
    Map<String, Object> CONSTANTS_MAP = initMap();

    /**
     * Initializes Account Constants Map.
     *
     * @return Constants map
     */
    static Map<String, Object> initMap() {
        Map<String, Object> constantMap = new HashMap<>();
        for (AccConstants variable : EnumSet.allOf(AccConstants.class)) {
            String key = variable.name();
            String value = variable.getValue();
            constantMap.put(key, value);
        }
        return Collections.unmodifiableMap(constantMap);
    }
}