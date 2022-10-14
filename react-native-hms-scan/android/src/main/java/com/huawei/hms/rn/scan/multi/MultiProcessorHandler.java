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

package com.huawei.hms.rn.scan.multi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.hmsscankit.ScanUtil;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzer;
import com.huawei.hms.mlsdk.common.MLFrame;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.huawei.hms.rn.scan.multi.RNHMSScanMultiProcessorModule.MULTIPROCESSOR_ASYNC_CODE;
import static com.huawei.hms.rn.scan.multi.RNHMSScanMultiProcessorModule.MULTIPROCESSOR_SYNC_CODE;
import static com.huawei.hms.rn.scan.utils.ReactUtils.toWM;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huawei.hms.rn.scan.logger.HMSLogger;
import com.huawei.hms.rn.scan.utils.Errors;

public final class MultiProcessorHandler extends Handler {

    private static final double DEFAULT_ZOOM = 1.0d;
    private final MultiProcessorCamera mMultiProcessorCamera;

    private final HandlerThread decodeThread;
    private final Handler decodeHandle;

    private Activity activity;
    private ReactApplicationContext reactContext;

    private final long[] mColorList;

    private final int mTextColor;
    private final float mTextSize;
    private final float mStrokeWidth;

    private final int mTextBackgroundColor;
    private final boolean mShowText;
    private final boolean mShowTextOutBounds;
    private final boolean mAutoSizeText;

    private final int mMinTextSize;
    private final int mGranularity;

    private final int mode;

    private final Gson mGson = new GsonBuilder().setPrettyPrinting().create();
    private HMSLogger mHMSLogger;

    private final HmsScanAnalyzer analyzer;

    MultiProcessorHandler(final Activity activity, ReactApplicationContext reactContext,
        MultiProcessorCamera multiProcessorCamera, final int mode, final long[] colorList, final int textColor,
        final float textSize, final float strokeWidth, final int textBackgroundColor, final boolean showText,
        final boolean showTextOutBounds, final boolean autoSizeText, final int minTextSize, final int granularity,
        final HmsScanAnalyzer mAnalyzer) {

        this.mMultiProcessorCamera = multiProcessorCamera;
        this.activity = activity;
        this.reactContext = reactContext;
        this.mode = mode;

        this.mColorList = colorList;

        this.mTextColor = textColor;
        this.mTextSize = textSize;
        this.mStrokeWidth = strokeWidth;

        this.mTextBackgroundColor = textBackgroundColor;
        this.mShowText = showText;
        this.mShowTextOutBounds = showTextOutBounds;
        this.mAutoSizeText = autoSizeText;

        this.mMinTextSize = minTextSize;
        this.mGranularity = granularity;

        this.analyzer = mAnalyzer;

        mHMSLogger = HMSLogger.getInstance(activity.getApplicationContext());

        decodeThread = new HandlerThread("DecodeThread");
        decodeThread.start();
        decodeHandle = new Handler(decodeThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg == null) {
                    return;
                }
                if (mode == MULTIPROCESSOR_SYNC_CODE) {
                    HmsScan[] result = decodeSync(msg.arg1, msg.arg2, (byte[]) msg.obj);
                    if (result == null || result.length == 0) {
                        restart(DEFAULT_ZOOM);
                    } else if (TextUtils.isEmpty(result[0].getOriginalValue()) && result[0].getZoomValue() != 1.0) {
                        restart(result[0].getZoomValue());
                    } else if (!TextUtils.isEmpty(result[0].getOriginalValue())) {
                        Message message = new Message();
                        message.what = msg.what;
                        message.obj = result;
                        MultiProcessorHandler.this.sendMessage(message);
                        restart(DEFAULT_ZOOM);
                    } else {
                        restart(DEFAULT_ZOOM);
                    }
                }
                if (mode == MULTIPROCESSOR_ASYNC_CODE) {
                    decodeAsync(msg.arg1, msg.arg2, (byte[]) msg.obj);
                }
            }
        };
        multiProcessorCamera.startPreview();
        restart(DEFAULT_ZOOM);
    }

    /**
     * Call the MultiProcessor API in synchronous mode.
     */
    private HmsScan[] decodeSync(int width, int height, byte[] data) {
        HmsScan[] info = new HmsScan[0];
        Bitmap bitmap = convertToBitmap(width, height, data);
        MLFrame image = MLFrame.fromBitmap(bitmap);
        if (analyzer.isAvailable()) {
            mHMSLogger.startMethodExecutionTimer("MultiProcessorHandler.decodeMultiSync");
            SparseArray<HmsScan> result = analyzer.analyseFrame(image);
            mHMSLogger.sendSingleEvent("MultiProcessorHandler.decodeMultiSync");
            if (result != null && result.size() > 0 && result.valueAt(0) != null && !TextUtils.isEmpty(
                    result.valueAt(0).getOriginalValue())) {
                info = new HmsScan[result.size()];
                for (int index = 0; index < result.size(); index++) {
                    info[index] = result.valueAt(index);
                }
                return info;
            }
        } else {
            Log.e(Errors.HMS_SCAN_ANALYZER_ERROR.getErrorCode(), Errors.HMS_SCAN_ANALYZER_ERROR.getErrorMessage(), null);
        }
        return info;
    }

    /**
     * Call the MultiProcessor API in asynchronous mode.
     */
    private void decodeAsync(int width, int height, byte[] data) {

        final Bitmap bitmap = convertToBitmap(width, height, data);
        MLFrame image = MLFrame.fromBitmap(bitmap);

        if (analyzer.isAvailable()) {
            mHMSLogger.startMethodExecutionTimer("MultiProcessorHandler.decodeMultiAsync");
            analyzer.analyzInAsyn(image).addOnSuccessListener(new OnSuccessListener<List<HmsScan>>() {
                @Override
                public void onSuccess(List<HmsScan> hmsScans) {
                    if (hmsScans != null && hmsScans.size() > 0 && hmsScans.get(0) != null && !TextUtils.isEmpty(
                            hmsScans.get(0).getOriginalValue())) {
                        mHMSLogger.sendSingleEvent("MultiProcessorHandler.decodeMultiAsync");
                        HmsScan[] infos = new HmsScan[hmsScans.size()];
                        Message message = new Message();
                        message.obj = hmsScans.toArray(infos);
                        MultiProcessorHandler.this.sendMessage(message);
                    }
                    restart(DEFAULT_ZOOM);
                    bitmap.recycle();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    restart(DEFAULT_ZOOM);
                    bitmap.recycle();
                }
            });
        } else {
            Log.e(Errors.HMS_SCAN_ANALYZER_ERROR.getErrorCode(), Errors.HMS_SCAN_ANALYZER_ERROR.getErrorMessage(), null);
        }
    }

    /**
     * Convert camera data into bitmap data.
     */
    private Bitmap convertToBitmap(int width, int height, byte[] data) {
        YuvImage yuv = new YuvImage(data, ImageFormat.NV21, width, height, null);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        yuv.compressToJpeg(new Rect(0, 0, width, height), 100, stream);
        return BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().length);
    }

    @Override
    public void handleMessage(Message message) {
        removeMessages(1);
        if (message.what == 0) {
            MultiProcessorActivity multiProcessorActivity1 = (MultiProcessorActivity) activity;
            multiProcessorActivity1.scanResultView.clear();
            Intent intent = new Intent();
            intent.putExtra(ScanUtil.RESULT, (HmsScan[]) message.obj);
            activity.setResult(RESULT_OK, intent);
            if (mode == MULTIPROCESSOR_ASYNC_CODE
                    || mode == MULTIPROCESSOR_SYNC_CODE) {
                MultiProcessorActivity multiProcessorActivity = (MultiProcessorActivity) activity;

                HmsScan[] arr = (HmsScan[]) message.obj;
                for (int i = 0; i < arr.length; i++) {
                    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(
                            "onMultiProcessorResponse", toWM(mGson.toJson(arr[i])));
                    multiProcessorActivity.scanResultView.add(
                            new ScanResultView.HmsScanGraphic(multiProcessorActivity.scanResultView, arr[i],
                                    (int) mColorList[i % mColorList.length],
                                    mTextColor, mTextSize, mStrokeWidth,
                                    mTextBackgroundColor, mShowText, mShowTextOutBounds, mAutoSizeText, mMinTextSize,
                                    mGranularity));
                }
                multiProcessorActivity.scanResultView.setCameraInfo(1080, 1920);
                multiProcessorActivity.scanResultView.invalidate();
                sendEmptyMessageDelayed(1, 1000);
            } else {
                activity.finish();
            }
        } else if (message.what == 1) {
            MultiProcessorActivity multiProcessorActivity1 = (MultiProcessorActivity) activity;
            multiProcessorActivity1.scanResultView.clear();
        }
    }

    void quit() {
        try {
            mMultiProcessorCamera.stopPreview();
            decodeHandle.getLooper().quit();
            decodeThread.join(500);
        } catch (InterruptedException e) {
            Log.w("Quit Camera Exception", e);
        }
    }

    private void restart(double zoomValue) {
        mMultiProcessorCamera.callbackFrame(decodeHandle, zoomValue);
    }
}
