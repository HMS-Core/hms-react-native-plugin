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

package com.huawei.hms.rn.health.kits.autorecorder.viewmodel;

import com.huawei.hms.hihealth.AutoRecorderController;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.rn.health.kits.autorecorder.listener.TaskVoidResultListener;

/**
 * Blueprint of {@link AutoRecorderViewModel}.
 *
 * @since v.5.0.1
 */
public interface AutoRecorderService {
    /**
     * Record data via DataType supported by Huawei.
     *
     * @param autoRecorderController AutoRecorderController instance.
     * @param dataType DataType instance.
     * @param listener AutoRecorderTaskResultListener instance.
     */
    void startRecord(final AutoRecorderController autoRecorderController, final DataType dataType,
        final TaskVoidResultListener listener);

    /**
     * Stop recording by specifying the data type.
     *
     * @param autoRecorderController AutoRecorderController instance.
     * @param dataType DataType instance.
     * @param listener AutoRecorderTaskResultListener instance.
     */
    void stopRecord(final AutoRecorderController autoRecorderController, final DataType dataType,
        final TaskVoidResultListener listener);

}
