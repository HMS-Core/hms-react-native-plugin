/*
 * Copyright 2023. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.hms.rn.mlbody.helpers.creators;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.common.LensEngine;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzer;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.face.face3d.ML3DFaceAnalyzer;
import com.huawei.hms.mlsdk.face.face3d.ML3DFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.gesture.MLGestureAnalyzer;
import com.huawei.hms.mlsdk.gesture.MLGestureAnalyzerFactory;
import com.huawei.hms.mlsdk.gesture.MLGestureAnalyzerSetting;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypointAnalyzer;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypointAnalyzerFactory;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypointAnalyzerSetting;
import com.huawei.hms.mlsdk.interactiveliveness.MLInteractiveLivenessCaptureConfig;
import com.huawei.hms.mlsdk.interactiveliveness.action.MLInteractiveLivenessConfig;
import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCaptureConfig;
import com.huawei.hms.mlsdk.skeleton.MLSkeletonAnalyzer;
import com.huawei.hms.mlsdk.skeleton.MLSkeletonAnalyzerFactory;
import com.huawei.hms.mlsdk.skeleton.MLSkeletonAnalyzerSetting;
import com.huawei.hms.rn.mlbody.helpers.transactors.HMS2DFaceAnalyzerTransactor;
import com.huawei.hms.rn.mlbody.helpers.transactors.HMS3DFaceAnalyzerTransactor;
import com.huawei.hms.rn.mlbody.helpers.transactors.HMSGestureTransactor;
import com.huawei.hms.rn.mlbody.helpers.transactors.HMSHandKeypointTransactor;
import com.huawei.hms.rn.mlbody.helpers.transactors.HMSSkeletonAnalyzerTransactor;
import com.huawei.hms.rn.mlbody.helpers.utils.HMSUtils;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public final class HMSObjectCreator {
    private static final String TAG = HMSObjectCreator.class.getSimpleName();

    private static volatile HMSObjectCreator instance;

    public static HMSObjectCreator getInstance() {
        if (instance == null) {
            synchronized (HMSObjectCreator.class) {
                if (instance == null) {
                    instance = new HMSObjectCreator();
                }
            }
        }
        return instance;
    }

    public static String bitmapToBase64(final Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * Creates MLFaceAnalyzer
     *
     * @param faceAnalyzerConfiguration analyzer configuration
     * @return MLFaceAnalyzer  object
     */
    public MLFaceAnalyzer create2DFaceAnalyzer(ReadableMap faceAnalyzerConfiguration) {
        return MLAnalyzerFactory.getInstance().getFaceAnalyzer(createFaceAnalyzerSetting(faceAnalyzerConfiguration));
    }

    /**
     * Creates ML3DFaceAnalyzer
     *
     * @param faceAnalyzerConfiguration analyzer configuration
     * @return ML3DFaceAnalyzer object
     */
    public ML3DFaceAnalyzer create3DFaceAnalyzer(ReadableMap faceAnalyzerConfiguration) {
        return MLAnalyzerFactory.getInstance()
            .get3DFaceAnalyzer(create3DFaceAnalyzerSetting(faceAnalyzerConfiguration));
    }

    /**
     * Creates ML3DFaceAnalyzerSetting
     *
     * @param readableMap configuration
     * @return ML3DFaceAnalyzerSetting object
     */
    private ML3DFaceAnalyzerSetting create3DFaceAnalyzerSetting(ReadableMap readableMap) {
        int performanceType = ML3DFaceAnalyzerSetting.TYPE_SPEED;
        boolean isTracingAllowed = false;

        if (readableMap == null) {
            Log.i(TAG, "ML3DFaceAnalyzerSetting object is created using default options.");
            return new ML3DFaceAnalyzerSetting.Factory().create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "performanceType", ReadableType.Number)) {
            performanceType = readableMap.getInt("performanceType");
            Log.i(TAG, "ML3DFaceAnalyzerSetting performanceType option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "isTracingAllowed", ReadableType.Boolean)) {
            isTracingAllowed = readableMap.getBoolean("isTracingAllowed");
            Log.i(TAG, "ML3DFaceAnalyzerSetting isTracingAllowed option set");
        }

        return new ML3DFaceAnalyzerSetting.Factory().setPerformanceType(performanceType)
            .setTracingAllowed(isTracingAllowed)
            .create();
    }

    /**
     * Creates MLFaceAnalyzerSetting object
     *
     * @param readableMap configuration
     * @return MLFaceAnalyzerSetting object
     */
    private MLFaceAnalyzerSetting createFaceAnalyzerSetting(ReadableMap readableMap) {
        int featureType = MLFaceAnalyzerSetting.TYPE_FEATURES;
        int setShapeType = MLFaceAnalyzerSetting.TYPE_SHAPES;
        int keyPointType = MLFaceAnalyzerSetting.TYPE_KEYPOINTS;
        int performanceType = MLFaceAnalyzerSetting.TYPE_SPEED;
        int tracingMode = MLFaceAnalyzerSetting.MODE_TRACING_ROBUST;
        double minFaceProportion = 0.1d;
        boolean isPoseDisabled = false;
        boolean isTracingAllowed = false;
        boolean isMaxSizeFaceOnly = false;

        if (readableMap == null) {
            Log.i(TAG, "MLFaceAnalyzerSetting object is created using default options.");
            return new MLFaceAnalyzerSetting.Factory().create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "featureType", ReadableType.Number)) {
            featureType = readableMap.getInt("featureType");
            Log.i(TAG, "MLFaceAnalyzerSetting featureType option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "keyPointType", ReadableType.Number)) {
            keyPointType = readableMap.getInt("keyPointType");
            Log.i(TAG, "MLFaceAnalyzerSetting keyPointType option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "shapeType", ReadableType.Number)) {
            setShapeType = readableMap.getInt("shapeType");
            Log.i(TAG, "MLFaceAnalyzerSetting shapeType option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "performanceType", ReadableType.Number)) {
            performanceType = readableMap.getInt("performanceType");
            Log.i(TAG, "MLFaceAnalyzerSetting performanceType option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "tracingMode", ReadableType.Number)) {
            tracingMode = readableMap.getInt("tracingMode");
            Log.i(TAG, "MLFaceAnalyzerSetting tracingMode option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "minFaceProportion", ReadableType.Number)) {
            minFaceProportion = readableMap.getDouble("minFaceProportion");
            Log.i(TAG, "MLFaceAnalyzerSetting minFaceProportion option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "isMaxSizeFaceOnly", ReadableType.Boolean)) {
            isMaxSizeFaceOnly = readableMap.getBoolean("isMaxSizeFaceOnly");
            Log.i(TAG, "MLFaceAnalyzerSetting isMaxSizeFaceOnly option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "isTracingAllowed", ReadableType.Boolean)) {
            isTracingAllowed = readableMap.getBoolean("isTracingAllowed");
            Log.i(TAG, "MLFaceAnalyzerSetting isTracingAllowed option set");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "isPoseDisabled", ReadableType.Boolean)) {
            isPoseDisabled = readableMap.getBoolean("isPoseDisabled");
            Log.i(TAG, "MLFaceAnalyzerSetting isPoseDisabled option set");
        }

        return new MLFaceAnalyzerSetting.Factory().setFeatureType(featureType)
            .setKeyPointType(keyPointType)
            .setMaxSizeFaceOnly(isMaxSizeFaceOnly)
            .setMinFaceProportion((float) minFaceProportion)
            .setPerformanceType(performanceType)
            .setPoseDisabled(isPoseDisabled)
            .setShapeType(setShapeType)
            .setTracingAllowed(isTracingAllowed, tracingMode)
            .create();
    }

    /**
     * Creates and returns MLSkeletonAnalyzer object
     *
     * @param analyzeType analyze type
     * @return MLSkeletonAnalyzer object
     */
    public MLSkeletonAnalyzer createSkeletonAnalyzer(int analyzeType) {
        return MLSkeletonAnalyzerFactory.getInstance().getSkeletonAnalyzer(createSkeletonAnalyzerSetting(analyzeType));
    }

    /**
     * Creates MLSkeletonAnalyzerSetting object
     *
     * @param analyzerType analyze type
     * @return MLSkeletonAnalyzerSetting object
     */
    private MLSkeletonAnalyzerSetting createSkeletonAnalyzerSetting(int analyzerType) {
        return new MLSkeletonAnalyzerSetting.Factory().setAnalyzerType(analyzerType).create();
    }

    /**
     * Creates MLLivenessCaptureConfig object
     *
     * @param options options
     * @return MLLivenessCaptureConfig object
     */
    public MLLivenessCaptureConfig createLivenessCaptureConfig(ReadableMap options) {
        MLLivenessCaptureConfig.Builder builder = new MLLivenessCaptureConfig.Builder();

        if (options == null) {
            Log.i(TAG, "MLLivenessCaptureConfig options is null");
            return builder.build();
        }
        if (HMSUtils.getInstance().hasValidKey(options, "option", ReadableType.Number)) {
            builder.setOptions(options.getInt("option"));
        }

        return builder.build();
    }

    /**
     * Creates MLInteractiveLivenessCaptureConfig object
     *
     * @param options options
     * @return MLInteractiveLivenessCaptureConfig object
     */

    public MLInteractiveLivenessCaptureConfig createInteractiveLivenessCaptureConfig(ReadableMap options) {

        ReadableArray actionArray = null;
        boolean isRandomable = true;
        int[] intArray = new int[6];
        int num = 0;
        MLInteractiveLivenessCaptureConfig.Builder builder = new MLInteractiveLivenessCaptureConfig.Builder();
        MLInteractiveLivenessConfig.Builder interactiveLivenessConfig = new MLInteractiveLivenessConfig.Builder();

        if (options == null) {
            Log.i(TAG, "MLInteractiveLivenessCaptureConfig options is null");
            return builder.build();
        }
        if (HMSUtils.getInstance().hasValidKey(options, "option", ReadableType.Number)) {

            builder.setOptions(options.getInt("option"));
        }
        if (HMSUtils.getInstance().hasValidKey(options, "config", ReadableType.Map)) {
            ReadableMap config = options.getMap("config");

            if (HMSUtils.getInstance().hasValidKey(config, "actionArray", ReadableType.Array)) {

                actionArray = config.getArray("actionArray");
                intArray = new int[actionArray.size()];
                for (int i = 0; i < actionArray.size(); i++) {
                    intArray[i] = actionArray.getInt(i);
                }
            }
            if (HMSUtils.getInstance().hasValidKey(config, "num", ReadableType.Number)) {

                num = config.getInt("num");
            }
            if (HMSUtils.getInstance().hasValidKey(config, "isRandomable", ReadableType.Boolean)) {

                isRandomable = config.getBoolean("isRandomable");
            }

            interactiveLivenessConfig.setActionArray(intArray, num, isRandomable);
            builder.setActionConfig(interactiveLivenessConfig.build());

        }

        if (HMSUtils.getInstance().hasValidKey(options, "detectionTimeOut", ReadableType.Number)) {

            builder.setDetectionTimeOut((long) options.getDouble("detectionTimeOut"));
        }
        return builder.build();
    }

    /**
     * Create MLHandKeypointAnalyzer
     *
     * @param analyzerSetting configuration
     * @return MLHandKeypointAnalyzer
     */
    public MLHandKeypointAnalyzer createHandKeyPointAnalyzer(ReadableMap analyzerSetting) {
        return MLHandKeypointAnalyzerFactory.getInstance()
            .getHandKeypointAnalyzer(createHandKeyPointAnalyzerSetting(analyzerSetting));
    }

    /**
     * Create MLGestureAnalyzer
     *
     * @return MLGestureAnalyzer
     */
    public MLGestureAnalyzer createGestureAnalyzer() {
        MLGestureAnalyzerSetting analyzerSetting = new MLGestureAnalyzerSetting.Factory().create();
        return MLGestureAnalyzerFactory.getInstance().getGestureAnalyzer(analyzerSetting);
    }

    /**
     * Creates MLHandKeypointAnalyzerSetting object
     *
     * @param readableMap configuration
     * @return MLHandKeypointAnalyzerSetting object
     */
    private MLHandKeypointAnalyzerSetting createHandKeyPointAnalyzerSetting(ReadableMap readableMap) {
        int maxHandResults = 10;
        int sceneType = MLHandKeypointAnalyzerSetting.TYPE_ALL;

        if (readableMap == null) {
            Log.i(TAG, "MLHandKeyPointAnalyzerSetting object is created using default options.");
            return new MLHandKeypointAnalyzerSetting.Factory().setMaxHandResults(maxHandResults)
                .setSceneType(sceneType)
                .create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "sceneType", ReadableType.Number)) {
            sceneType = readableMap.getInt("sceneType");
            Log.i(TAG, "MLHandKeyPointAnalyzerSetting sceneType option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "maxHandResults", ReadableType.Number)) {
            maxHandResults = readableMap.getInt("maxHandResults") <= 0 ? 10 : readableMap.getInt("maxHandResults");
            Log.i(TAG, "MLHandKeyPointAnalyzerSetting maxHandResults option set.");
        }

        return new MLHandKeypointAnalyzerSetting.Factory().setMaxHandResults(maxHandResults)
            .setSceneType(sceneType)
            .create();
    }

    /**
     * Creates MLFrame object to use analyze
     *
     * @param frameConfiguration keys and values to create MLFrame from existing methods
     * @param context ReactApplicationContext
     * @return MLFrame object or null
     */
    public MLFrame createFrame(ReadableMap frameConfiguration, ReactApplicationContext context) {
        if (frameConfiguration == null) {
            Log.i(TAG, "MLFrame frameConfiguration is null");
            return null;
        } else if (HMSUtils.getInstance().hasValidKey(frameConfiguration, "bitmap", ReadableType.String)) {
            byte[] refactored = Base64.decode(frameConfiguration.getString("bitmap"), Base64.DEFAULT);
            return MLFrame.fromBitmap(BitmapFactory.decodeByteArray(refactored, 0, refactored.length));
        } else if (HMSUtils.getInstance().hasValidKey(frameConfiguration, "bytes", ReadableType.Map)) {
            ReadableMap bytes = frameConfiguration.getMap("bytes");
            if (HMSUtils.getInstance().hasValidKey(bytes, "frameProperty", ReadableType.Map) && HMSUtils.getInstance()
                .hasValidKey(bytes, "values", ReadableType.Array)) {
                MLFrame.Property property = createFrameProperty(bytes.getMap("frameProperty"));
                ReadableArray values = bytes.getArray("values");
                return MLFrame.fromByteArray(HMSUtils.getInstance().convertRaToByteArray(values), property);
            } else {
                Log.i(TAG, "MLFrame bytes object does not contain required keys");
                return null;
            }
        } else if (HMSUtils.getInstance().hasValidKey(frameConfiguration, "byteBuffer", ReadableType.Map)) {
            ReadableMap byteBuffer = frameConfiguration.getMap("byteBuffer");
            if (HMSUtils.getInstance().hasValidKey(byteBuffer, "buffer", ReadableType.String) && HMSUtils.getInstance()
                .hasValidKey(byteBuffer, "frameProperty", ReadableType.Map)) {
                MLFrame.Property frameProperty = createFrameProperty(byteBuffer.getMap("frameProperty"));
                String buffer = byteBuffer.getString("buffer");
                return MLFrame.fromByteBuffer(
                    HMSUtils.getInstance().convertByteArrToByteBuffer(Base64.decode(buffer, Base64.DEFAULT)),
                    frameProperty);
            } else {
                Log.i(TAG, "MLFrame byteBuffer object does not contain required keys");
                return null;
            }
        } else if (HMSUtils.getInstance().hasValidKey(frameConfiguration, "filePath", ReadableType.String)) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),
                    Uri.parse(frameConfiguration.getString("filePath")));
                return new MLFrame.Creator().setBitmap(bitmap).create();
            } catch (Exception e) {
                Log.i(TAG, "MLFrame exception happened fromFilePath " + e.getMessage());
                return null;
            }

        } else if (HMSUtils.getInstance().hasValidKey(frameConfiguration, "creator", ReadableType.Map)) {
            ReadableMap creator = frameConfiguration.getMap("creator");
            return createFrameUsingCreator(creator);
        } else {
            Log.i(TAG, "MLFrame frameConfiguration does not contain keys for creating a frame");
            return null;
        }
    }

    /**
     * Creates MLFrame.Property object
     *
     * @param readableMap configuration keys and values
     * @return MLFrame.Property object
     */
    private MLFrame.Property createFrameProperty(ReadableMap readableMap) {
        MLFrame.Property.Creator creator = new MLFrame.Property.Creator();

        if (readableMap == null) {
            Log.i(TAG, "MLFrame.Property object is created using default options.");
            return creator.create();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "width", ReadableType.Number)) {
            creator.setWidth(readableMap.getInt("width"));
            Log.i(TAG, "MLFrame.Property width option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "height", ReadableType.Number)) {
            creator.setHeight(readableMap.getInt("height"));
            Log.i(TAG, "MLFrame.Property height option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "quadrant", ReadableType.Number)) {
            creator.setQuadrant(readableMap.getInt("quadrant"));
            Log.i(TAG, "MLFrame.Property quadrant option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "formatType", ReadableType.Number)) {
            creator.setFormatType(readableMap.getInt("formatType"));
            Log.i(TAG, "MLFrame.Property formatType option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "itemIdentity", ReadableType.Number)) {
            creator.setItemIdentity(readableMap.getInt("itemIdentity"));
            Log.i(TAG, "MLFrame.Property itemIdentity option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "timeStamp", ReadableType.Number)) {
            creator.setTimestamp(readableMap.getInt("timeStamp"));
            Log.i(TAG, "MLFrame.Property timeStamp option set.");
        }

        return creator.create();
    }

    /**
     * Creates MLFrame.Property.Ext object
     *
     * @param readableMap configuration keys and values
     * @return MLFrame.Property.Ext object
     */
    private MLFrame.Property.Ext createFramePropertyExt(ReadableMap readableMap) {
        int lensId = 0;
        int maxZoom = 0;
        int zoom = 0;
        int bottom = 0;
        int left = 0;
        int right = 0;
        int top = 0;

        if (readableMap == null) {
            Log.i(TAG, "MLFrameProperty.Ext object is created using default options.");
            return new MLFrame.Property.Ext.Creator().build();
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "lensId", ReadableType.Number)) {
            lensId = readableMap.getInt("lensId");
            Log.i(TAG, "MLFrameProperty.Ext lensId option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "maxZoom", ReadableType.Number)) {
            maxZoom = readableMap.getInt("maxZoom");
            Log.i(TAG, "MLFrameProperty.Ext maxZoom option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "zoom", ReadableType.Number)) {
            zoom = readableMap.getInt("zoom");
            Log.i(TAG, "MLFrameProperty.Ext zoom option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "bottom", ReadableType.Number)) {
            bottom = readableMap.getInt("bottom");
            Log.i(TAG, "MLFrameProperty.Ext bottom option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "left", ReadableType.Number)) {
            left = readableMap.getInt("left");
            Log.i(TAG, "MLFrameProperty.Ext left option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "right", ReadableType.Number)) {
            right = readableMap.getInt("right");
            Log.i(TAG, "MLFrameProperty.Ext right option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "top", ReadableType.Number)) {
            top = readableMap.getInt("top");
            Log.i(TAG, "MLFrameProperty.Ext top option set.");
        }
        return new MLFrame.Property.Ext.Creator().setLensId(lensId)
            .setMaxZoom(maxZoom)
            .setRect(new Rect(left, top, right, bottom))
            .setZoom(zoom)
            .build();
    }

    /**
     * Creates MLFrame using creator
     *
     * @param readableMap configuration keys and values
     * @return MLFrame object
     */
    private MLFrame createFrameUsingCreator(ReadableMap readableMap) {
        MLFrame.Creator creator = new MLFrame.Creator();

        if (readableMap == null) {
            Log.i(TAG, "MLFrame given ReadableMap object is null");
            return null;
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "base64Bitmap", ReadableType.String)) {
            byte[] refactored = Base64.decode(readableMap.getString("base64Bitmap"), Base64.DEFAULT);
            creator.setBitmap(BitmapFactory.decodeByteArray(refactored, 0, refactored.length));
            Log.i(TAG, "MLFrame base64Bitmap option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "itemIdentity", ReadableType.Number)) {
            creator.setItemIdentity(readableMap.getInt("itemIdentity"));
            Log.i(TAG, "MLFrame itemIdentity option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "quadrant", ReadableType.Number)) {
            creator.setQuadrant(readableMap.getInt("quadrant"));
            Log.i(TAG, "MLFrame quadrant option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "timeStamp", ReadableType.String)) {
            creator.setTimestamp(Long.parseLong(readableMap.getString("timeStamp")));
            Log.i(TAG, "MLFrame timeStamp option set.");
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "framePropertyExt", ReadableType.Map)) {
            creator.setFramePropertyExt(createFramePropertyExt(readableMap.getMap("framePropertyExt")));
        }
        if (HMSUtils.getInstance().hasValidKey(readableMap, "writeByteBufferData", ReadableType.Map)) {
            ReadableMap writeByteBufferData = readableMap.getMap("writeByteBufferData");
            if (HMSUtils.getInstance().hasValidKey(writeByteBufferData, "data", ReadableType.String)
                && HMSUtils.getInstance().hasValidKey(writeByteBufferData, "height", ReadableType.Number)
                && HMSUtils.getInstance().hasValidKey(writeByteBufferData, "width", ReadableType.Number)
                && HMSUtils.getInstance().hasValidKey(writeByteBufferData, "formatType", ReadableType.Number)) {

                ByteBuffer bufferData = HMSUtils.getInstance()
                    .convertByteArrToByteBuffer(Base64.decode(writeByteBufferData.getString("data"), Base64.DEFAULT));
                int height = writeByteBufferData.getInt("height");
                int width = writeByteBufferData.getInt("width");
                int formatType = writeByteBufferData.getInt("formatType");
                creator.writeByteBufferData(bufferData, width, height, formatType);
                Log.i(TAG, "MLFrame writeByteBufferData option set.");
            } else {
                Log.i(TAG, "MLFrame writeByteBufferData option keys are not valid.");
            }

        }

        return creator.create();
    }

    /**
     * Creates LensEngine
     *
     * @param context context object
     * @param analyzer analyzer
     * @param configuration configurations for LensEngine
     * @return LensEngine object
     */
    public LensEngine createLensEngine(ReactApplicationContext context, MLAnalyzer analyzer,
        ReadableMap configuration) {
        int width = 1440;
        int height = 1080;
        float fps = 30.0f;
        boolean automaticFocus = false;
        String flashMode = Camera.Parameters.FLASH_MODE_OFF;
        String focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO;
        int lensType = LensEngine.BACK_LENS;

        if (configuration == null) {
            Log.i(TAG, "LensEngine created with default options");
            return new LensEngine.Creator(context, analyzer).setLensType(lensType)
                .setFocusMode(focusMode)
                .setFlashMode(flashMode)
                .enableAutomaticFocus(false)
                .applyFps(fps)
                .applyDisplayDimension(width, height)
                .create();
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "width", ReadableType.Number)) {
            Log.i(TAG, "LensEngine width set");
            width = configuration.getInt("width");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "height", ReadableType.Number)) {
            Log.i(TAG, "LensEngine height set");
            height = configuration.getInt("height");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "lensType", ReadableType.Number)) {
            Log.i(TAG, "LensEngine lensType set");
            lensType = configuration.getInt("lensType");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "fps", ReadableType.Number)) {
            Log.i(TAG, "LensEngine fps set");
            fps = (float) configuration.getDouble("fps");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "automaticFocus", ReadableType.Boolean)) {
            Log.i(TAG, "LensEngine automaticFocus set");
            automaticFocus = configuration.getBoolean("automaticFocus");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "flashMode", ReadableType.String)) {
            Log.i(TAG, "LensEngine flashMode set");
            flashMode = configuration.getString("flashMode");
        }
        if (HMSUtils.getInstance().hasValidKey(configuration, "focusMode", ReadableType.String)) {
            Log.i(TAG, "LensEngine focusMode set");
            focusMode = configuration.getString("focusMode");
        }

        return new LensEngine.Creator(context, analyzer).setLensType(lensType)
            .setFocusMode(focusMode)
            .setFlashMode(flashMode)
            .enableAutomaticFocus(automaticFocus)
            .applyFps(fps)
            .applyDisplayDimension(width, height)
            .create();
    }

    /**
     * Creates analyzer for lens engine
     *
     * @param analyzer analyzer tag number
     * @param analyzerConfig analyzer configuration for related tag
     * @param context app context
     * @return MLAnalyzer
     */
    public MLAnalyzer createLensEngineAnalyzer(int analyzer, ReadableMap analyzerConfig,
        ReactApplicationContext context) {
        switch (analyzer) {
            case 1:
                MLFaceAnalyzer faceAnalyzer2d = create2DFaceAnalyzer(analyzerConfig);
                faceAnalyzer2d.setTransactor(new HMS2DFaceAnalyzerTransactor(context));
                return faceAnalyzer2d;
            case 2:
                ML3DFaceAnalyzer faceAnalyzer3d = create3DFaceAnalyzer(analyzerConfig);
                faceAnalyzer3d.setTransactor(new HMS3DFaceAnalyzerTransactor(context));
                return faceAnalyzer3d;
            case 3:
                int analyzeType = HMSUtils.getInstance().hasValidKey(analyzerConfig, "analyzeType", ReadableType.Number)
                    ? analyzerConfig.getInt("analyzeType")
                    : MLSkeletonAnalyzerSetting.TYPE_NORMAL;
                MLSkeletonAnalyzer skeletonAnalyzer = createSkeletonAnalyzer(analyzeType);
                skeletonAnalyzer.setTransactor(new HMSSkeletonAnalyzerTransactor(context));
                return skeletonAnalyzer;
            case 7:
                MLHandKeypointAnalyzer handKeypointAnalyzer = createHandKeyPointAnalyzer(analyzerConfig);
                handKeypointAnalyzer.setTransactor(new HMSHandKeypointTransactor(context));
                return handKeypointAnalyzer;
            case 8:
                MLGestureAnalyzer gestureAnalyzer = createGestureAnalyzer();
                gestureAnalyzer.setTransactor(new HMSGestureTransactor(context));
                return gestureAnalyzer;
            default:
                return null;
        }
    }

}
