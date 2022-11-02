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

package com.huawei.hms.rn.health.kits.healthrecordcontroller.viewmodel;

import com.huawei.hms.hihealth.HealthRecordController;
import com.huawei.hms.hihealth.data.HealthRecord;
import com.huawei.hms.hihealth.options.HealthRecordDeleteOptions;
import com.huawei.hms.hihealth.options.HealthRecordReadOptions;
import com.huawei.hms.rn.health.foundation.helper.ResultHelper;
import com.huawei.hms.rn.health.foundation.helper.VoidResultHelper;
import com.huawei.hms.rn.health.foundation.listener.VoidResultListener;

public interface HealthRecordService {

    /**
     * Inserts the health records into Health Kit.
     *
     * @param healthRecordController {@link HealthRecordController} instance.
     * @param healthRecord {@link HealthRecord} instance.
     * @param resultHelper {@link ResultHelper} instance.
     */
    void addHealthRecord(final HealthRecordController healthRecordController, final HealthRecord healthRecord,
        final ResultHelper resultHelper);

    /**
     * update the health records into Health Kit.
     *
     * @param healthRecordController {@link HealthRecordController} instance.
     * @param healthRecord {@link HealthRecord} instance.
     * @param resultHelper {@link VoidResultHelper} instance.
     */
    void updateHealthRecord(final HealthRecordController healthRecordController, final HealthRecord healthRecord,
        final VoidResultHelper resultHelper);

    /**
     * get the health records into Health Kit.
     *
     * @param healthRecordController {@link HealthRecordController} instance.
     * @param healthRecordReadOptions {@link HealthRecordReadOptions} instance.
     * @param resultHelper {@link ResultHelper} instance.
     */
    void getHealthRecord(final HealthRecordController healthRecordController,
        final HealthRecordReadOptions healthRecordReadOptions, final ResultHelper resultHelper);

    /**
     * Deletes health records based on the request parameters.
     *
     * @param healthRecordController {@link HealthRecordController} instance.
     * @param deleteOptions Request for querying health records to be deleted.
     * @param resultHelper {@link VoidResultListener} instance.
     */
    void deleteHealthRecord(final HealthRecordController healthRecordController,
        final HealthRecordDeleteOptions deleteOptions, final VoidResultHelper resultHelper);
}
