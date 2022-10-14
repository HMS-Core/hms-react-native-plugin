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
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzer;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.rn.scan.logger.HMSLogger;
import com.huawei.hms.rn.scan.utils.Errors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import static android.app.Activity.RESULT_OK;
import static com.huawei.hms.rn.scan.utils.ReactUtils.getIntegerArrayFromReadableArray;
import static com.huawei.hms.rn.scan.utils.ReactUtils.getLongArrayFromReadableArray;
import static com.huawei.hms.rn.scan.utils.ReactUtils.hasValidKey;
import static com.huawei.hms.rn.scan.utils.ReactUtils.toWA;

public class RNHMSScanMultiProcessorModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private ReactContext mReactContext;
    private Promise mPromise;
    private final HMSLogger mHMSLogger;
    private final Gson gson;

    private static final int REQUEST_CODE_SCAN_MULTI = 15;

    static final int MULTIPROCESSOR_SYNC_CODE = 444;
    static final int MULTIPROCESSOR_ASYNC_CODE = 555;

    public RNHMSScanMultiProcessorModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
        mReactContext.addActivityEventListener(this);
        gson = new GsonBuilder().setPrettyPrinting().create();
        mHMSLogger = HMSLogger.getInstance(mReactContext);
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> scanModes = new ArrayMap<>();
        scanModes.put("Sync", MULTIPROCESSOR_SYNC_CODE);
        scanModes.put("Async", MULTIPROCESSOR_ASYNC_CODE);
        Map<String, Object> constants = new ArrayMap<>();
        constants.put("SCAN_MODES", scanModes);
        return constants;
    }

    @NonNull
    @Override
    public String getName() {
        return "RNHMSScanMultiProcessorModule";
    }

    @ReactMethod
    public void startMultiProcessorCamera(final ReadableMap rm, final Promise promise) {
        mPromise = promise;
        int scanMode = 0;
        if (hasValidKey(rm, "scanMode", ReadableType.Number)) {
            scanMode = rm.getInt("scanMode");
        }

        int scanType = 0;
        if (hasValidKey(rm, "scanType", ReadableType.Number)) {
            scanType = rm.getInt("scanType");
        }

        int[] additionalScanTypes = null;
        if (hasValidKey(rm, "additionalScanTypes", ReadableType.Array)) {
            additionalScanTypes = getIntegerArrayFromReadableArray(rm.getArray("additionalScanTypes"));
        }

        long[] colorList = new long[]{};
        if (hasValidKey(rm, "colorList", ReadableType.Array)) {
            colorList = getLongArrayFromReadableArray(rm.getArray("colorList"));
        }

        float strokeWidth = 0f;
        if (hasValidKey(rm, "strokeWidth", ReadableType.Number)) {
            strokeWidth = (float) rm.getDouble("strokeWidth");
        }
        ScanTextOptions scanTextOptions;
        if (hasValidKey(rm, "scanTextOptions", ReadableType.Map)) {
            scanTextOptions = gson.fromJson(rm.getMap("scanTextOptions").toHashMap().toString(), ScanTextOptions.class);
        } else {
            scanTextOptions = new ScanTextOptions();
        }

        boolean isGalleryAvailable = false;
        if (hasValidKey(rm, "isGalleryAvailable", ReadableType.Boolean)) {
            isGalleryAvailable = rm.getBoolean("isGalleryAvailable");
        }

        Intent intent = new Intent(mReactContext.getCurrentActivity(), MultiProcessorActivity.class);

        intent.putExtra("scanType", scanType);
        if (additionalScanTypes != null) {
            intent.putExtra("additionalScanTypes", additionalScanTypes);
        }
        intent.putExtra("colorList", colorList);

        intent.putExtra("isGalleryAvailable", isGalleryAvailable);
        intent.putExtra("strokeWidth", strokeWidth);

        intent.putExtra("textColor", scanTextOptions.getTextColor());
        intent.putExtra("textSize", scanTextOptions.getTextSize());
        intent.putExtra("showText", scanTextOptions.getShowText());
        intent.putExtra("textBackgroundColor", scanTextOptions.getTextBackgroundColor());
        intent.putExtra("showTextOutBounds", scanTextOptions.getShowTextOutBounds());
        intent.putExtra("autoSizeText", scanTextOptions.getAutoSizeText());
        intent.putExtra("minTextSize", scanTextOptions.getMinTextSize());
        intent.putExtra("granularity", scanTextOptions.getGranularity());

        intent.putExtra("scanMode", scanMode);

        if (scanMode == MULTIPROCESSOR_ASYNC_CODE || scanMode == MULTIPROCESSOR_SYNC_CODE) {
            mReactContext.getCurrentActivity().startActivityForResult(intent, REQUEST_CODE_SCAN_MULTI);
        } else {
            mPromise.reject(Errors.MP_CAMERA_SCAN_MODE_ERROR.getErrorCode(),
                Errors.MP_CAMERA_SCAN_MODE_ERROR.getErrorMessage());
        }
    }

    @ReactMethod
    public void decodeMultiSync(final ReadableMap rm, final Promise promise) {
        mPromise = promise;
        int scanType = 0;
        if (hasValidKey(rm, "scanType", ReadableType.Number)) {
            scanType = rm.getInt("scanType");
        }
        int[] additionalScanTypes = null;
        if (hasValidKey(rm, "additionalScanTypes", ReadableType.Array)) {
            additionalScanTypes = getIntegerArrayFromReadableArray(rm.getArray("additionalScanTypes"));
        }
        String data = "";
        if (hasValidKey(rm, "data", ReadableType.String)) {
            data = rm.getString("data");
        }

        byte[] parsed = Base64.decode(data, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(parsed, 0, parsed.length);

        MLFrame image = MLFrame.fromBitmap(bitmap);

        HmsScanAnalyzer analyzer = new HmsScanAnalyzer.Creator(
            getCurrentActivity()).setHmsScanTypes(scanType, additionalScanTypes).create();

        if (analyzer.isAvailable()) {
            mHMSLogger.startMethodExecutionTimer("MultiProcessorMethodCallHandler.decodeMultiSync");
            SparseArray<HmsScan> scanResult = analyzer.analyseFrame(image);
            mHMSLogger.sendSingleEvent("MultiProcessorMethodCallHandler.decodeMultiSync");

            if (scanResult != null && scanResult.size() > 0 && scanResult.valueAt(0) != null && !TextUtils.isEmpty(
                    scanResult.valueAt(0).getOriginalValue())) {
                HmsScan[] info = new HmsScan[scanResult.size()];
                for (int index = 0; index < scanResult.size(); index++) {
                    info[index] = scanResult.valueAt(index);
                }
                mPromise.resolve(toWA(gson.toJson(info)));
            } else {
                mPromise.reject(Errors.DECODE_MULTI_SYNC_COULD_NOT_FIND.getErrorCode(),
                        Errors.DECODE_MULTI_SYNC_COULD_NOT_FIND.getErrorMessage());
            }
        } else {
            Log.e(Errors.HMS_SCAN_ANALYZER_ERROR.getErrorCode(), Errors.HMS_SCAN_ANALYZER_ERROR.getErrorMessage(), null);
            mPromise.reject(Errors.REMOTE_VIEW_ERROR.getErrorCode(), Errors.REMOTE_VIEW_ERROR.getErrorMessage());
        }
        analyzer.destroy();
    }

    @ReactMethod
    public void decodeMultiAsync(final ReadableMap rm, final Promise promise) {
        mPromise = promise;
        String data = "";
        if (hasValidKey(rm, "data", ReadableType.String)) {
            data = rm.getString("data");
        }
        int scanType = 0;
        if (hasValidKey(rm, "scanType", ReadableType.Number)) {
            scanType = rm.getInt("scanType");
        }

        int[] additionalScanTypes = null;
        if (hasValidKey(rm, "additionalScanTypes", ReadableType.Array)) {
            additionalScanTypes = getIntegerArrayFromReadableArray(rm.getArray("additionalScanTypes"));
        }

        byte[] parsed = Base64.decode(data, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(parsed, 0, parsed.length);

        MLFrame image = MLFrame.fromBitmap(bitmap);

        HmsScanAnalyzer analyzer = new HmsScanAnalyzer.Creator(
            getCurrentActivity()).setHmsScanTypes(scanType, additionalScanTypes).create();

        if (analyzer.isAvailable()) {
            mHMSLogger.startMethodExecutionTimer("MultiProcessorMethodCallHandler.decodeMultiAsync");
            analyzer.analyzInAsyn(image).addOnSuccessListener(new OnSuccessListener<List<HmsScan>>() {
                @Override
                public void onSuccess(List<HmsScan> hmsScans) {
                    if (hmsScans != null && hmsScans.size() > 0 && hmsScans.get(0) != null && !TextUtils.isEmpty(
                            hmsScans.get(0).getOriginalValue())) {
                        HmsScan[] infos = new HmsScan[hmsScans.size()];
                        for (int index = 0; index < hmsScans.size(); index++) {
                            infos[index] = hmsScans.get(index);
                        }
                        promise.resolve(toWA(gson.toJson(infos)));
                        mHMSLogger.sendSingleEvent("MultiProcessorMethodCallHandler.decodeMultiAsync");
                    } else {
                        promise.reject(Errors.DECODE_MULTI_ASYNC_COULD_NOT_FIND.getErrorCode(),
                                Errors.DECODE_MULTI_ASYNC_COULD_NOT_FIND.getErrorMessage());
                    }
                    analyzer.destroy();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    promise.reject(Errors.DECODE_MULTI_ASYNC_ON_FAILURE.getErrorCode(),
                            Errors.DECODE_MULTI_ASYNC_ON_FAILURE.getErrorMessage());
                    mHMSLogger.sendSingleEvent("MultiProcessorMethodCallHandler.decodeMultiAsync",
                            Errors.DECODE_MULTI_ASYNC_ON_FAILURE.getErrorCode());
                    analyzer.destroy();
                }
            });
        } else {
            Log.e(Errors.HMS_SCAN_ANALYZER_ERROR.getErrorCode(), Errors.HMS_SCAN_ANALYZER_ERROR.getErrorMessage(), null);
            promise.reject(Errors.REMOTE_VIEW_ERROR.getErrorCode(), Errors.REMOTE_VIEW_ERROR.getErrorMessage());
            analyzer.destroy();
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_SCAN_MULTI) {
            Parcelable[] obj = data.getParcelableArrayExtra(ScanUtil.RESULT);
            if (obj != null && obj.length > 0 && mPromise != null) {
                mPromise.resolve(toWA(gson.toJson(obj)));
                mPromise = null;
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
    }
}
