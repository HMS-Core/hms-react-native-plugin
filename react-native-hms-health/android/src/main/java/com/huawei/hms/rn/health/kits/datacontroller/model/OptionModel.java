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

package com.huawei.hms.rn.health.kits.datacontroller.model;

import com.huawei.hms.rn.health.kits.datacontroller.HmsDataController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * {@link OptionModel} is used among creating UpdateOptions, DeleteOptions, ReadOptions operations,
 * In {@link HmsDataController}
 *
 * @since v.5.0.1
 */
public class OptionModel {
    private Date startDate;

    private Date endDate;

    private TimeUnit timeUnit;

    /**
     * Initialization
     */
    public OptionModel(Date startDate, Date endDate, TimeUnit timeUnit) {
        this.startDate = new Date(startDate.getTime());
        this.endDate = new Date(endDate.getTime());
        this.timeUnit = timeUnit;
    }

    /* Getter Methods */

    public Date getStartDate() {
        return new Date(startDate.getTime());
    }

    public Date getEndDate() {
        return new Date(endDate.getTime());
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
