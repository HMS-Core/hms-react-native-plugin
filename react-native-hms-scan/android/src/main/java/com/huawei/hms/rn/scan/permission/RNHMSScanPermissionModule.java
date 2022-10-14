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

package com.huawei.hms.rn.scan.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.PermissionChecker;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.huawei.hms.rn.scan.utils.Errors;

public class RNHMSScanPermissionModule extends ReactContextBaseJavaModule implements PermissionListener {
    private ReactContext mReactContext;
    private Promise mPromise;

    public RNHMSScanPermissionModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "RNHMSScanPermissionModule";
    }

    private boolean isGrantedCameraPermission() {
        final int camera = PermissionChecker.checkSelfPermission(mReactContext, Manifest.permission.CAMERA);
        if (camera == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            Log.e("Error code: " + Errors.SCAN_UTIL_NO_CAMERA_PERMISSION.getErrorCode(),
                Errors.SCAN_UTIL_NO_CAMERA_PERMISSION.getErrorMessage(), null);
            return false;
        }
    }

    private boolean isGrantedReadStoragePermission() {
        final int read = PermissionChecker.checkSelfPermission(mReactContext,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (read == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            Log.e("Error code: " + Errors.SCAN_UTIL_NO_READ_PERMISSION.getErrorCode(),
                Errors.SCAN_UTIL_NO_READ_PERMISSION.getErrorMessage(), null);
            return false;
        }
    }

    private boolean checkGrantStatus(final int[] grantResults) {
        for (final int i : grantResults) {
            return !(i == -1);
        }
        return true;
    }

    @ReactMethod
    public void hasCameraAndStoragePermission(final Promise promise) {
        promise.resolve(isGrantedCameraPermission() && isGrantedReadStoragePermission());
    }

    private int requestCodeAll = 1;

    @ReactMethod
    public void requestCameraAndStoragePermission(final Promise promise) {
        mPromise = promise;
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

        ((PermissionAwareActivity) getCurrentActivity()).requestPermissions(
            permissions, requestCodeAll, this);
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mPromise != null) {
            if (requestCode == requestCodeAll) {
                mPromise.resolve(grantResults[0] == 0 && grantResults[1] == 0);
            } else {
                mPromise.resolve(checkGrantStatus(grantResults));
            }
        }
        return true;
    }
}
