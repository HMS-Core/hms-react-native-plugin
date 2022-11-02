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

import android.util.Log;

import com.huawei.hms.hihealth.AutoRecorderController;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.options.OnSamplePointListener;
import com.huawei.hms.rn.health.kits.autorecorder.listener.TaskVoidResultListener;

/**
 * All the tasks for {@link AutoRecorderController} methods
 * are used in {@link AutoRecorderViewModel} class.
 *
 * @since v.5.0.1
 */
public class AutoRecorderViewModel implements AutoRecorderService {

    private static final String TAG = AutoRecorderViewModel.class.getSimpleName();

    /**
     * Record data via DataType supported by Huawei.
     *
     * @param autoRecorderController AutoRecorderController instance.
     * @param dataType DataType instance.
     * @param listener AutoRecorderTaskResultListener instance.
     */
    @Override
    public void startRecord(final AutoRecorderController autoRecorderController, final DataType dataType,
        final TaskVoidResultListener listener) {
        Log.i(TAG, "call startRecordByType");

        OnSamplePointListener onSamplePointListener = samplePoint -> Log.i(TAG, samplePoint.toString());

        // Calling the autoRecorderController to startRecord by DataType is an asynchronous operation.
        autoRecorderController.startRecord(dataType, onSamplePointListener).addOnCompleteListener(taskResult -> {
            // The fail reason includes:
            //  1.the app hasn't been granted the scopes
            //  2.this type is not supported so far
            Log.i(TAG, "startRecordByType onComplete");
            listener.onComplete(taskResult);
        }).addOnSuccessListener(result -> {
            Log.i(TAG, "startRecordByType success");
            listener.onSuccess(result);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "startRecordByType error");
            listener.onFail(error);
        });
    }

    /**
     * Stop recording by specifying the data type.
     *
     * @param autoRecorderController AutoRecorderController instance.
     * @param dataType DataType instance.
     * @param listener AutoRecorderTaskResultListener instance.
     */
    @Override
    public void stopRecord(final AutoRecorderController autoRecorderController, final DataType dataType,
        final TaskVoidResultListener listener) {
        Log.i(TAG, "call stopRecordByType");

        OnSamplePointListener onSamplePointListener = samplePoint -> {
            // Nothing will happen here
        };
        // Calling the autoRecorderController to stopRecord by DataType is an asynchronous operation.
        autoRecorderController.stopRecord(dataType, onSamplePointListener).addOnCompleteListener(taskResult -> {
            // The fail reason includes:
            //  1.the app hasn't been granted the scopes
            //  2.this type is not supported so far
            Log.i(TAG, "stopRecordByType onComplete");
            listener.onComplete(taskResult);
        }).addOnSuccessListener(result -> {
            Log.i(TAG, "stopRecordByType success");
            listener.onSuccess(result);
        }).addOnFailureListener(error -> {
            Log.i(TAG, "stopRecordByType error");
            listener.onFail(error);
        });
    }
}


