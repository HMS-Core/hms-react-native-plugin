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

package com.huawei.hms.rn.ml.helpers.utils;

import android.graphics.Bitmap;
import android.util.SparseArray;

import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionResult;
import com.huawei.hms.mlsdk.imagesuperresolution.MLImageSuperResolutionResult;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentation;
import com.huawei.hms.mlsdk.textimagesuperresolution.MLTextImageSuperResolution;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;

public final class HMSBackgroundTasks {
    private static volatile HMSBackgroundTasks instance;

    public static HMSBackgroundTasks getInstance() {
        if (instance == null) {
            synchronized (HMSBackgroundTasks.class) {
                if (instance == null) {
                    instance = new HMSBackgroundTasks();
                }
            }
        }
        return instance;
    }

    /**
     * Handles single image saving task
     *
     * @param context app context
     * @param image image to be saved
     * @return WritableMap
     */
    public Task<String> saveImageAndGetUri(ReactApplicationContext context, Bitmap image) {
        return Tasks.callInBackground(() -> HMSUtils.getInstance().saveImageAndGetUri(context, image));
    }

    /**
     * Handles saving segmentation images task
     *
     * @param context app context
     * @param segmentation segmentation result
     * @param isBodySeg analyzer type
     * @return WritableMap
     */
    public Task<WritableMap> saveImageSegmentationImages(ReactApplicationContext context,
        MLImageSegmentation segmentation, boolean isBodySeg) {
        return Tasks.callInBackground(
            () -> HMSResultCreator.getInstance().getImageSegmentationAsyncResult(context, segmentation, isBodySeg));
    }

    /**
     * Handles saving segmentation images task
     *
     * @param context app context
     * @param segmentation segmentation result
     * @param isBodySeg analyzer type
     * @return WritableMap
     */
    public Task<WritableMap> saveImageSegmentationImages(ReactApplicationContext context,
        SparseArray<MLImageSegmentation> segmentation, boolean isBodySeg) {
        return Tasks.callInBackground(
            () -> HMSResultCreator.getInstance().getImageSegmentationResult(context, segmentation, isBodySeg));
    }

    /**
     * Handles saving super resolution images task
     *
     * @param context app context
     * @param result analyze method  result
     * @return WritableMap
     */
    public Task<WritableMap> saveImageSuperResolutionImages(ReactApplicationContext context,
        SparseArray<MLImageSuperResolutionResult> result) {
        return Tasks.callInBackground(
            () -> HMSResultCreator.getInstance().getMLImageSuperResolutionResults(context, result));
    }

    /**
     * Handles saving document skew correction images task
     *
     * @param context app context
     * @param results correction result
     * @return WritableMap
     */
    public Task<WritableMap> saveDscImages(ReactApplicationContext context,
        SparseArray<MLDocumentSkewCorrectionResult> results) {
        return Tasks.callInBackground(
            () -> HMSResultCreator.getInstance().getDocumentSkewCorrectionResult(context, results));
    }

    /**
     * Handles saving text image super resolution images task
     *
     * @param context app context
     * @param results super resolution result
     * @return WritableMap
     */
    public Task<WritableMap> saveTextImageSuperResolutionImages(ReactApplicationContext context,
        SparseArray<MLTextImageSuperResolution> results) {
        return Tasks.callInBackground(
            () -> HMSResultCreator.getInstance().getTextImageSuperResolutionResult(context, results));
    }

}
