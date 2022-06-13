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

package com.huawei.hms.rn.availability;

import static com.huawei.hms.rn.availability.Util.mapToWM;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.huawei.hms.api.HuaweiApiAvailability;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Map;

public class HMSAvailabilityModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    HMSAvailabilityModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "HMSAvailabilityModule";
    }

    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new ArrayMap<>();

        Map<String, Object> errorCode = new ArrayMap<>();
        errorCode.put("HMS_CORE_APK_AVAILABLE", 0);
        errorCode.put("NO_HMS_CORE_APK", 1);
        errorCode.put("HMS_CORE_APK_OUT_OF_DATE", 2);
        errorCode.put("HMS_CORE_APK_UNAVAILABLE", 3);
        errorCode.put("APK_IS_NOT_OFFICIAL_VERSION", 9);
        errorCode.put("HMS_CORE_APK_TOO_OLD", 21);
        constants.put("ErrorCode", errorCode);
        return constants;
    }

    @ReactMethod
    public void isHuaweiMobileServicesAvailableWithoutParam(final Promise promise) {
        int isHuaweiMobileServicesAvailable = HuaweiApiAvailability.getInstance()
            .isHuaweiMobileServicesAvailable(reactContext);
        promise.resolve(isHuaweiMobileServicesAvailable);
    }

    @ReactMethod
    public void isHuaweiMobileServicesAvailableWithParam(int minApkVersion, final Promise promise) {
        int isHuaweiMobileServicesAvailable = HuaweiApiAvailability.getInstance()
            .isHuaweiMobileServicesAvailable(reactContext, minApkVersion);
        promise.resolve(isHuaweiMobileServicesAvailable);
    }

    @ReactMethod
    public void getApiMap(final Promise promise) {
        Map<String, Integer> map = HuaweiApiAvailability.getApiMap();
        WritableMap result = mapToWM(map);
        promise.resolve(result);
    }

    @ReactMethod
    public void getServicesVersionCode(final Promise promise) {
        int code = HuaweiApiAvailability.getServicesVersionCode();
        promise.resolve(code);
    }

    @ReactMethod
    public void getErrorString(int code, final Promise promise) {
        String result = HuaweiApiAvailability.getInstance().getErrorString(code);
        promise.resolve(result);
    }

    @ReactMethod
    public void isUserResolvableError(int code, final Promise promise) {
        boolean result = HuaweiApiAvailability.getInstance().isUserResolvableError(code);
        promise.resolve(result);
    }

    @ReactMethod
    public void isHuaweiMobileNoticeAvailable(final Promise promise) {
        int isHuaweiMobileNoticeAvailable = HuaweiApiAvailability.getInstance()
            .isHuaweiMobileNoticeAvailable(reactContext);
        promise.resolve(isHuaweiMobileNoticeAvailable);
    }

    @ReactMethod
    public void resolveError(int errorCode, int requestCode, final Promise promise) {
        HuaweiApiAvailability.getInstance().resolveError(getCurrentActivity(), errorCode, requestCode);
        promise.resolve(null);
    }

    @ReactMethod
    public void setServicesVersionCode(int code, final Promise promise) {
        HuaweiApiAvailability.setServicesVersionCode(code);
        promise.resolve(null);
    }

    @ReactMethod
    public void showErrorDialogFragment(int errorCode, int requestCode, final Promise promise) {
        getCurrentActivity().runOnUiThread(() -> {
            boolean result = HuaweiApiAvailability.getInstance()
                .showErrorDialogFragment(getCurrentActivity(), errorCode, requestCode, dialog -> {
                    sendEvent(reactContext, "OnErrorDialogFragmentCancelled");
                });
            promise.resolve(result);
        });
    }

    private void sendEvent(ReactContext reactContext, String eventName) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, null);
    }

    @ReactMethod
    public void showErrorNotification(int code, final Promise promise) {
        HuaweiApiAvailability.getInstance().showErrorNotification(getCurrentActivity(), code);
        promise.resolve(null);
    }
}
