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

package com.huawei.hms.rn.ml.commonservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.LENS_ENGINE_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.LENS_ON_CLICK_SHUTTER;
import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.LENS_ON_PHOTO_TAKEN;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.ANALYZER_NOT_AVAILABLE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.FAILURE;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.LENS_ENGINE_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.LENS_HOLDER_NULL;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.huawei.hms.mlsdk.common.LensEngine;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSBackgroundTasks;
import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;

public class HMSLensEngine extends HMSBase implements LensEngine.ShutterListener, LensEngine.PhotographListener {
    private LensEngine lensEngine;

    /**
     * Initializes base fields for each module
     *
     * @param mContext context
     */
    public HMSLensEngine(ReactApplicationContext mContext) {
        super(mContext, HMSLensEngine.class.getSimpleName(), LENS_ENGINE_CONSTANTS);
    }

    /**
     * Creates lens engine with an analyzer
     *
     * @param analyzerTag analyzer tag
     * @param analyzerConfig analyzer configuration
     * @param lensConfig lens engine configuration
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void createLensEngine(int analyzerTag, ReadableMap analyzerConfig, ReadableMap lensConfig,
        final Promise promise) {
        startMethodExecTimer("createLensEngine");
        MLAnalyzer analyzer = HMSObjectCreator.getInstance()
            .createLensEngineAnalyzer(analyzerTag, analyzerConfig, getContext());

        if (analyzer == null) {
            handleResult("close", ANALYZER_NOT_AVAILABLE, promise);
            return;
        }

        lensEngine = HMSObjectCreator.getInstance().createLensEngine(getContext(), analyzer, lensConfig);
        handleResult("createLensEngine", SUCCESS, promise);
    }

    /**
     * Closes the camera and stops sending frames to the frame analyzer.
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void close(final Promise promise) {
        startMethodExecTimer("close");

        if (lensEngine == null) {
            handleResult("close", LENS_ENGINE_NULL, promise);
            return;
        }

        lensEngine.close();
        handleResult("close", SUCCESS, promise);
    }

    /**
     * Adjusts the focal length of the camera based on the scaling coefficient (digital zoom).
     *
     * @param scale zoom scale
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void doZoom(double scale, final Promise promise) {
        startMethodExecTimer("doZoom");

        if (lensEngine == null) {
            handleResult("doZoom", LENS_ENGINE_NULL, promise);
            return;
        }

        lensEngine.doZoom((float) scale);
        handleResult("doZoom", SUCCESS, promise);
    }

    /**
     * Obtains the size of the preview image of a camera.
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getDisplayDimension(final Promise promise) {
        startMethodExecTimer("getDisplayDimension");

        if (lensEngine == null) {
            handleResult("getDisplayDimension", LENS_ENGINE_NULL, promise);
            return;
        }

        handleResult("getDisplayDimension",
            HMSResultCreator.getInstance().displayDimensionResult(lensEngine.getDisplayDimension()), promise);
    }

    /**
     * Obtains the selected camera type.
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getLensType(final Promise promise) {
        startMethodExecTimer("getLensType");

        if (lensEngine == null) {
            handleResult("getLensType", LENS_ENGINE_NULL, promise);
            return;
        }

        handleResult("getLensType", HMSResultCreator.getInstance().integerResult(lensEngine.getLensType()), promise);
    }

    /**
     * Monitors photographing.
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void photograph(final Promise promise) {
        startMethodExecTimer("photograph");

        if (lensEngine == null) {
            handleResult("photograph", LENS_ENGINE_NULL, promise);
            return;
        }

        lensEngine.photograph(this, this);
        handleResult("photograph", SUCCESS, promise);
    }

    /**
     * Releases resources occupied by LensEngine.
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void release(final Promise promise) {
        startMethodExecTimer("release");

        if (lensEngine == null) {
            handleResult("release", LENS_ENGINE_NULL, promise);
            return;
        }

        lensEngine.release();
        lensEngine = null;
        handleResult("release", SUCCESS, promise);
    }

    /**
     * Starts LensEngine.
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void run(final Promise promise) {
        startMethodExecTimer("run");

        if (lensEngine == null) {
            handleResult("run", LENS_ENGINE_NULL, promise);
            return;
        }

        try {
            lensEngine.run();
            handleResult("run", SUCCESS, promise);
        } catch (IOException e) {
            handleResult("run", e, promise);
        }
    }

    /**
     * Starts the LensEngine and uses SurfaceView as the frame preview panel.
     *
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void runWithView(final Promise promise) {
        startMethodExecTimer("runWithView");

        if (lensEngine == null) {
            handleResult("runWithView", LENS_ENGINE_NULL, promise);
            return;
        }

        if (HMSUtils.getInstance().getSurfaceViewHolder() == null) {
            handleResult("runWithView", LENS_HOLDER_NULL, promise);
            return;
        }

        try {
            lensEngine.run(HMSUtils.getInstance().getSurfaceViewHolder());
            handleResult("runWithView", SUCCESS, promise);
        } catch (IOException e) {
            handleResult("runWithView", e, promise);
        }
    }

    /**
     * Photograph take event
     *
     * @param bytes Picture as bytes
     */
    @Override
    public void takenPhotograph(byte[] bytes) {
        Bitmap photo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        HMSBackgroundTasks.getInstance()
            .saveImageAndGetUri(getContext(), photo)
            .addOnSuccessListener(s -> sendEvent(LENS_ON_PHOTO_TAKEN, "takenPhotograph",
                HMSResultCreator.getInstance().getStringResult(s)))
            .addOnFailureListener(e -> sendEvent(LENS_ON_PHOTO_TAKEN, "takenPhotograph",
                FAILURE.getStatusAndMessage(null, e.getMessage())));
    }

    /**
     * Shutter click event
     */
    @Override
    public void clickShutter() {
        sendEvent(LENS_ON_CLICK_SHUTTER, "clickShutter", null);
    }
}
