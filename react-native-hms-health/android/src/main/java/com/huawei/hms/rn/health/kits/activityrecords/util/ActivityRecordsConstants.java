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

package com.huawei.hms.rn.health.kits.activityrecords.util;

import static com.huawei.hms.rn.health.kits.datacontroller.util.DataControllerConstants.DATA_CONSTANTS_MAP;

import com.huawei.hms.hihealth.HiHealthActivities;
import com.huawei.hms.rn.health.kits.activityrecords.HmsActivityRecordsController;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link HmsActivityRecordsController} Constant Values.
 *
 * @since v.5.0.1
 */
public interface ActivityRecordsConstants {
    /**
     * All the {@link HiHealthActivities} constants.
     */
    enum HiHealthConstants {
        /* Data Types */
        MIME_TYPE_PREFIX("vnd.huawei.hihealth.activity/"),
        EXTRA_ACTION_STATUS("actionStatus"),
        STATUS_ACTION_START("StartedActionStatus"),
        STATUS_ACTION_END("EndedActionStatus"),
        AEROBICS("aerobics"),
        ARCHERY("archery"),
        APNEA_TRAINING("apnea_training"),
        APNEA_TEST("apnea_test"),
        BADMINTON("badminton"),
        BASEBALL("baseball"),
        BASKETBALL("basketball"),
        BIATHLON("biathlon"),
        BOXING("boxing"),
        CALISTHENICS("calisthenics"),
        CIRCUIT_TRAINING("circuit_training"),
        CRICKET("cricket"),
        CROSSFIT("crossfit"),
        CURLING("curling"),
        CYCLING("cycling"),
        CYCLING_INDOOR("cycling_indoor"),
        DANCING("dancing"),
        DIVING("diving"),
        ELEVATOR("elevator"),
        ELLIPTICAL("elliptical"),
        ERGOMETER("ergometer"),
        ESCALATOR("escalator"),
        FENCING("fencing"),
        FOOTBALL_AMERICAN("football.american"),
        FOOTBALL_AUSTRALIAN("football.australian"),
        FOOTBALL_SOCCER("football.soccer"),
        FLYING_DISC("flying_disc"),
        GARDENING("gardening"),
        GOLF("golf"),
        GYMNASTICS("gymnastics"),
        HANDBALL("handball"),
        HIIT("interval_training.high_intensity"),
        HIKING("hiking"),
        HOCKEY("hockey"),
        HORSE_RIDING("horse_riding"),
        HOUSEWORK("housework"),
        ICE_SKATING("ice_skating"),
        IN_VEHICLE("in_vehicle"),
        INTERVAL_TRAINING("interval_training"),
        JUMPING_ROPE("jumping_rope"),
        KAYAKING("kayaking"),
        KETTLEBELL_TRAINING("kettlebell_training"),
        KICKBOXING("kickboxing"),
        KITESURFING("kitesurfing"),
        MARTIAL_ARTS("martial_arts"),
        MEDITATION("meditation"),
        MIXED_MARTIAL_ARTS("martial_arts.mixed"),
        ON_FOOT("on_foot"),
        OTHER("other"),
        P90X("p90x"),
        PARAGLIDING("paragliding"),
        PILATES("pilates"),
        POLO("polo"),
        RACQUETBALL("racquetball"),
        ROCK_CLIMBING("rock_climbing"),
        ROWING("rowing"),
        ROWING_MACHINE("rowing.machine"),
        RUGBY("rugby"),
        RUNNING("running"),
        RUNNING_MACHINE("running.machine"),
        SAILING("sailing"),
        SCUBA_DIVING("scuba_diving"),
        SCOOTER_RIDING("scooter_riding"),
        SKATEBOARDING("skateboarding"),
        SKATING("skating"),
        SKIING("skiing"),
        SLEDDING("sledding"),
        SLEEP("sleep"),
        SLEEP_LIGHT("sleep.light"),
        SLEEP_DEEP("sleep.deep"),
        SLEEP_REM("sleep.rem"),
        SLEEP_AWAKE("sleep.awake"),
        SNOWBOARDING("snowboarding"),
        SNOWMOBILE("snowmobile"),
        SNOWSHOEING("snowshoeing"),
        SOFTBALL("softball"),
        SQUASH("squash"),
        STAIR_CLIMBING("stair_climbing"),
        STAIR_CLIMBING_MACHINE("stair_climbing.machine"),
        STANDUP_PADDLEBOARDING("standup_paddleboarding"),
        STILL("still"),
        STRENGTH_TRAINING("strength_training"),
        SURFING("surfing"),
        SWIMMING("swimming"),
        SWIMMING_POOL("swimming.pool"),
        SWIMMING_OPEN_WATER("swimming.open_water"),
        TABLE_TENNIS("table_tennis"),
        TEAM_SPORTS("team_sports"),
        TENNIS("tennis"),
        TILTING("tilting"),
        UNKNOWN("unknown"),
        VOLLEYBALL("volleyball"),
        WAKEBOARDING("wakeboarding"),
        WALKING("walking"),
        WATER_POLO("water_polo"),
        WEIGHTLIFTING("weightlifting"),
        WHEELCHAIR("wheelchair"),
        WINDSURFING("windsurfing"),
        YOGA("yoga"),
        ZUMBA("zumba");

        private final String value;

        HiHealthConstants(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Whole base constants variables as Map
     **/
    Map<String, Object> ACTIVITY_CONSTANTS_MAP = initMap();

    /**
     * Initializes Constants map.
     *
     * @return Map<String, Object> Constants map
     */
    static Map<String, Object> initMap() {
        Map<String, Object> constantMap = new HashMap<>();
        // Hi-health Constants
        for (HiHealthConstants variable : EnumSet.allOf(HiHealthConstants.class)) {
            String key = variable.name();
            String value = variable.getValue();
            constantMap.put(key, value);
        }

        // Put DATA_CONSTANTS_MAP
        constantMap.putAll(DATA_CONSTANTS_MAP);

        return Collections.unmodifiableMap(constantMap);
    }
}
