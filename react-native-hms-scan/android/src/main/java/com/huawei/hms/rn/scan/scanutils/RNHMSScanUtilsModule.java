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

package com.huawei.hms.rn.scan.scanutils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.huawei.hms.rn.scan.logger.HMSLogger;
import com.huawei.hms.rn.scan.utils.Errors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Nullable;

import static android.app.Activity.RESULT_OK;
import static com.huawei.hms.rn.scan.utils.ReactUtils.getIntegerArrayFromReadableArray;
import static com.huawei.hms.rn.scan.utils.ReactUtils.hasValidKey;
import static com.huawei.hms.rn.scan.utils.ReactUtils.toWM;

public class RNHMSScanUtilsModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private ReactContext mReactContext;

    private Promise mPromise;

    private final HMSLogger mHMSLogger;

    private final Gson gson;

    private static final int REQUEST_CODE_SCAN_DEFAULT = 13;

    public RNHMSScanUtilsModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
        mReactContext.addActivityEventListener(this);
        gson = new GsonBuilder().setPrettyPrinting().create();
        mHMSLogger = HMSLogger.getInstance(mReactContext);
    }

    public Map<String, Object> getScanTypes() {
        Map<String, Object> scanTypes = new ArrayMap<>();
        scanTypes.put("Other", HmsScan.OTHER_FORM);
        scanTypes.put("All", HmsScan.ALL_SCAN_TYPE);
        scanTypes.put("Code128", HmsScan.CODE128_SCAN_TYPE);
        scanTypes.put("Code39", HmsScan.CODE39_SCAN_TYPE);
        scanTypes.put("Code93", HmsScan.CODE93_SCAN_TYPE);
        scanTypes.put("Codabar", HmsScan.CODABAR_SCAN_TYPE);
        scanTypes.put("DataMatrix", HmsScan.DATAMATRIX_SCAN_TYPE);
        scanTypes.put("EAN13", HmsScan.EAN13_SCAN_TYPE);
        scanTypes.put("EAN8", HmsScan.EAN8_SCAN_TYPE);
        scanTypes.put("ITF14", HmsScan.ITF14_SCAN_TYPE);
        scanTypes.put("QRCode", HmsScan.QRCODE_SCAN_TYPE);
        scanTypes.put("UPCCodeA", HmsScan.UPCCODE_A_SCAN_TYPE);
        scanTypes.put("UPCCodeE", HmsScan.UPCCODE_E_SCAN_TYPE);
        scanTypes.put("Pdf417", HmsScan.PDF417_SCAN_TYPE);
        scanTypes.put("Aztec", HmsScan.AZTEC_SCAN_TYPE);
        return scanTypes;
    }

    public Map<String, Object> getScanForms() {
        Map<String, Object> scanForms = new ArrayMap<>();
        scanForms.put("Other", HmsScan.OTHER_FORM);
        scanForms.put("ContactDetail", HmsScan.CONTACT_DETAIL_FORM);
        scanForms.put("EmailContent", HmsScan.EMAIL_CONTENT_FORM);
        scanForms.put("ISBNNumber", HmsScan.ISBN_NUMBER_FORM);
        scanForms.put("TelPhoneNumber", HmsScan.TEL_PHONE_NUMBER_FORM);
        scanForms.put("ArticleNumber", HmsScan.ARTICLE_NUMBER_FORM);
        scanForms.put("SMS", HmsScan.SMS_FORM);
        scanForms.put("PureText", HmsScan.PURE_TEXT_FORM);
        scanForms.put("Url", HmsScan.URL_FORM);
        scanForms.put("WIFIConnectInfo", HmsScan.WIFI_CONNECT_INFO_FORM);
        scanForms.put("LocationCoordinate", HmsScan.LOCATION_COORDINATE_FORM);
        scanForms.put("EventInfo", HmsScan.EVENT_INFO_FORM);
        scanForms.put("DriverInfo", HmsScan.DRIVER_INFO_FORM);
        return scanForms;
    }

    public Map<String, Object> getAddressTypes() {
        Map<String, Object> addressTypes = new ArrayMap<>();
        addressTypes.put("Residential", HmsScan.AddressInfo.RESIDENTIAL_USE_TYPE);
        addressTypes.put("Other", HmsScan.AddressInfo.OTHER_USE_TYPE);
        addressTypes.put("Office", HmsScan.AddressInfo.OFFICE_TYPE);
        return addressTypes;
    }

    public Map<String, Object> getTelPhoneNumberUseTypes() {
        Map<String, Object> telPhoneNumberUseTypes = new ArrayMap<>();
        telPhoneNumberUseTypes.put("Fax", HmsScan.TelPhoneNumber.FAX_USE_TYPE);
        telPhoneNumberUseTypes.put("Residential", HmsScan.TelPhoneNumber.RESIDENTIAL_USE_TYPE);
        telPhoneNumberUseTypes.put("Cellphone", HmsScan.TelPhoneNumber.CELLPHONE_NUMBER_USE_TYPE);
        telPhoneNumberUseTypes.put("Other", HmsScan.TelPhoneNumber.OTHER_USE_TYPE);
        telPhoneNumberUseTypes.put("Office", HmsScan.TelPhoneNumber.OFFICE_USE_TYPE);
        return telPhoneNumberUseTypes;
    }

    public Map<String, Object> getEmailAddressTypes() {
        Map<String, Object> emailAddressTypes = new ArrayMap<>();
        emailAddressTypes.put("Office", HmsScan.EmailContent.OFFICE_USE_TYPE);
        emailAddressTypes.put("Residential", HmsScan.EmailContent.RESIDENTIAL_USE_TYPE);
        emailAddressTypes.put("Other", HmsScan.EmailContent.OTHER_USE_TYPE);
        return emailAddressTypes;
    }

    public Map<String, Object> getWIFIModeTypes() {
        Map<String, Object> wifiModeTypes = new ArrayMap<>();
        wifiModeTypes.put("NoPass", HmsScan.WiFiConnectionInfo.NO_PASSWORD_MODE_TYPE);
        wifiModeTypes.put("WEP", HmsScan.WiFiConnectionInfo.WEP_MODE_TYPE);
        wifiModeTypes.put("WPA", HmsScan.WiFiConnectionInfo.WPA_MODE_TYPE);
        return wifiModeTypes;
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new ArrayMap<>();
        constants.put("SCAN_TYPES", getScanTypes());
        constants.put("SCAN_FORMS", getScanForms());
        constants.put("ADDRESS_TYPES", getAddressTypes());
        constants.put("TEL_PHONE_NUMBER_USE_TYPES", getTelPhoneNumberUseTypes());
        constants.put("EMAIL_ADDRESS_TYPES", getEmailAddressTypes());
        constants.put("WIFI_MODE_TYPES", getWIFIModeTypes());
        return constants;
    }

    @NonNull
    @Override
    public String getName() {
        return "RNHMSScanUtilsModule";
    }

    @ReactMethod
    public void disableLogger() {
        mHMSLogger.disableLogger();
    }

    @ReactMethod
    public void enableLogger() {
        mHMSLogger.enableLogger();
    }

    @ReactMethod
    public void startDefaultView(final ReadableMap defaultViewRequest, final Promise promise) {
        mPromise = promise;
        HmsScanAnalyzerOptions.Creator creator = new HmsScanAnalyzerOptions.Creator();
        if (defaultViewRequest != null) {
            if (hasValidKey(defaultViewRequest, "scanType", ReadableType.Number)) {
                int scanType = defaultViewRequest.getInt("scanType");
                int[] additionalScanTypes = new int[] {};
                if (hasValidKey(defaultViewRequest, "additionalScanTypes", ReadableType.Array)) {
                    additionalScanTypes = getIntegerArrayFromReadableArray(
                        defaultViewRequest.getArray("additionalScanTypes"));
                }
                creator.setHmsScanTypes(scanType, additionalScanTypes);
            }
        }
        HmsScanAnalyzerOptions options = creator.create();

        mHMSLogger.startMethodExecutionTimer("RNHMSScanUtilsModule.defaultView");

        if (ScanUtil.startScan(getCurrentActivity(), REQUEST_CODE_SCAN_DEFAULT, options) == ScanUtil.SUCCESS) {
            Log.i("DefaultView", "Camera started.");
        } else {
            Log.i("DefaultView", Errors.SCAN_UTIL_NO_CAMERA_PERMISSION.getErrorMessage());
            promise.reject(Errors.SCAN_UTIL_NO_CAMERA_PERMISSION.getErrorCode(),
                Errors.SCAN_UTIL_NO_CAMERA_PERMISSION.getErrorMessage());
            mHMSLogger.sendSingleEvent("RNHMSScanUtilsModule.defaultView",
                Errors.SCAN_UTIL_NO_CAMERA_PERMISSION.getErrorCode());
        }
    }

    @ReactMethod
    public void buildBitmap(final ReadableMap buildBitmapRequest, final Promise promise) {
        mPromise = promise;
        String content = "";
        int type = 0;
        int width = 200;
        int height = 200;

        try {
            HmsBuildBitmapOption.Creator creator = new HmsBuildBitmapOption.Creator();
            if (buildBitmapRequest != null) {

                if (hasValidKey(buildBitmapRequest, "content", ReadableType.String)) {
                    content = buildBitmapRequest.getString("content");
                }
                if (hasValidKey(buildBitmapRequest, "type", ReadableType.Number)) {
                    type = buildBitmapRequest.getInt("type");
                }
                if (hasValidKey(buildBitmapRequest, "width", ReadableType.Number)) {
                    width = buildBitmapRequest.getInt("width");
                }
                if (hasValidKey(buildBitmapRequest, "height", ReadableType.Number)) {
                    height = buildBitmapRequest.getInt("height");
                }
                if (hasValidKey(buildBitmapRequest, "backgroundColor", ReadableType.Number)) {
                    creator.setBitmapBackgroundColor(buildBitmapRequest.getInt("backgroundColor"));
                }
                if (hasValidKey(buildBitmapRequest, "color", ReadableType.Number)) {
                    creator.setBitmapColor(buildBitmapRequest.getInt("color"));
                }
                if (hasValidKey(buildBitmapRequest, "margin", ReadableType.Number)) {
                    creator.setBitmapMargin(buildBitmapRequest.getInt("margin"));
                }
                if (hasValidKey(buildBitmapRequest, "qrErrorCorrectionLevel", ReadableType.String)) {
                    creator.setQRErrorCorrection(
                        HmsBuildBitmapOption.ErrorCorrectionLevel.valueOf("qrErrorCorrectionLevel"));
                }
                if (hasValidKey(buildBitmapRequest, "qrLogoBitmap", ReadableType.String)) {
                    final String qrLogoBitmap = buildBitmapRequest.getString("qrLogoBitmap");
                    final Uri imageUri = Uri.parse(qrLogoBitmap);
                    Bitmap logoBitmap = null;

                    try {
                        logoBitmap = MediaStore.Images.Media.getBitmap(
                            getReactApplicationContext().getContentResolver(), imageUri);
                    } catch (IOException e) {
                        Log.i("qrLogoBitmap", "buildBitmap: imageUri is null");
                    }
                    creator.setQRLogoBitmap(logoBitmap).create();
                }
            }
            HmsBuildBitmapOption options = creator.create();

            mHMSLogger.startMethodExecutionTimer("ScanUtilsMethodCallHandler.buildBitmap");
            final Bitmap qrBmp = ScanUtil.buildBitmap(content, type, width, height, options);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            qrBmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            final byte[] byteArray = byteArrayOutputStream.toByteArray();

            mHMSLogger.sendSingleEvent("RNHMSScanUtilsModule.buildBitmap");
            promise.resolve(Base64.encodeToString(byteArray, Base64.DEFAULT));

        } catch (WriterException e) {
            mHMSLogger.sendSingleEvent("RNHMSScanUtilsModule.buildBitmap", e.getLocalizedMessage());
            promise.reject(Errors.BUILD_BITMAP.getErrorCode(), Errors.BUILD_BITMAP.getErrorMessage());
        }
    }

    @ReactMethod
    public void decodeWithBitmap(final ReadableMap decodeBitmapRequest, final Promise promise) {
        mPromise = promise;
        String data = "";
        int scanType = 0;
        int[] additionalScanTypes = new int[] {};
        if (decodeBitmapRequest != null) {
            if (hasValidKey(decodeBitmapRequest, "data", ReadableType.String)) {
                data = decodeBitmapRequest.getString("data");
            }
            if (hasValidKey(decodeBitmapRequest, "scanType", ReadableType.Number)) {
                scanType = decodeBitmapRequest.getInt("scanType");
            }
            if (hasValidKey(decodeBitmapRequest, "additionalScanTypes", ReadableType.Array)) {
                additionalScanTypes = getIntegerArrayFromReadableArray(
                    decodeBitmapRequest.getArray("additionalScanTypes"));
            }
        }

        byte[] parsed = Base64.decode(data, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(parsed, 0, parsed.length);

        HmsScanAnalyzerOptions.Creator creator = new HmsScanAnalyzerOptions.Creator();
        creator.setHmsScanTypes(scanType, additionalScanTypes);
        creator.setPhotoMode(true);
        HmsScanAnalyzerOptions options = creator.create();

        mHMSLogger.startMethodExecutionTimer("RNHMSScanUtilsModule.decodeWithBitmap");
        HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(mReactContext.getCurrentActivity(), bitmap, options);
        mHMSLogger.sendSingleEvent("RNHMSScanUtilsModule.decodeWithBitmap");

        if (hmsScans != null && hmsScans.length > 0 && hmsScans[0] != null && !TextUtils.isEmpty(
            hmsScans[0].getOriginalValue())) {
            promise.resolve(toWM(gson.toJson(hmsScans[0])));
        } else {
            promise.reject(Errors.DECODE_WITH_BITMAP_ERROR.getErrorCode(), Errors.DECODE_WITH_BITMAP_ERROR.getErrorMessage());
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SCAN_DEFAULT && mPromise != null) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
                    HMSLogger.getInstance(mReactContext).sendSingleEvent("RNHMSScanUtilsModule.defaultView");
                    mPromise.resolve(toWM(gson.toJson(obj)));
                } else {
                    HMSLogger.getInstance(mReactContext).sendSingleEvent("RNHMSScanUtilsModule", "null data");
                    mPromise.reject("NULL", "Data is null");
                }
            } else {
                WritableMap params = Arguments.createMap();
                params.putString("NOT_OK", "Result is not ok");
                sendEvent(mReactContext, "returnButtonClicked", params);
            }
            mPromise = null;
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
    }
}
