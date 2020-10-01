/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.nearby;

import android.net.Uri;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.huawei.hms.nearby.Nearby;
import com.huawei.hms.nearby.transfer.Data;
import com.huawei.hms.rn.nearby.constants.Constants;
import com.huawei.hms.rn.nearby.utils.HmsLogger;
import com.huawei.hms.rn.nearby.utils.HmsUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.annotation.Nullable;

import static com.huawei.hms.rn.nearby.constants.Errors.E_TRANSFER;

public class HmsTransferModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = HmsTransferModule.class.getSimpleName();
    private final ReactApplicationContext mContext;

    HmsTransferModule(ReactApplicationContext context) {
        super(context);
        mContext = context;
    }

    @ReactMethod
    public void transferBytes(ReadableArray bytes, ReadableArray endpointIds, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("transferBytes");

        if (bytes.size() == 0) {
            HmsLogger.getInstance(mContext).sendSingleEvent("transferBytes", "given byte array is empty");
            promise.reject(E_TRANSFER, "given byte array is empty");
            return;
        }

        if (!HmsUtils.getInstance().checkEndpointIds(endpointIds)) {
            HmsLogger.getInstance(mContext).sendSingleEvent("transferBytes", "given readable array must not be empty. Only non-null and non-empty strings are allowed in it");
            promise.reject(E_TRANSFER, "given readable array must not be empty. Only non-null and non-empty strings are allowed in it");
            return;
        }

        Nearby.getTransferEngine(mContext)
                .sendData(HmsUtils.getInstance().convertToArrayList(endpointIds), Data.fromBytes(HmsUtils.getInstance().convertRaToByteArray(bytes)))
                .addOnSuccessListener(aVoid -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("transferBytes");
                    promise.resolve("Transfer bytes success");
                })
                .addOnFailureListener(e -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("transferBytes", e.getMessage());
                    promise.reject(E_TRANSFER, e.getMessage());
                });
    }

    @ReactMethod
    public void transferFile(String uri, ReadableArray endpointIds, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("transferFile");

        if (uri == null || uri.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("transferFile", "given uri is null or empty");
            promise.reject(E_TRANSFER, "given uri is null or empty");
            return;
        }

        if (!HmsUtils.getInstance().checkEndpointIds(endpointIds)) {
            HmsLogger.getInstance(mContext).sendSingleEvent("transferFile", "given readable array must not be empty. Only non-null and non-empty strings are allowed in it");
            promise.reject(E_TRANSFER, "given readable array must not be empty. Only non-null and non-empty strings are allowed in it");
            return;
        }

        try {
            ParcelFileDescriptor pfd = mContext.getContentResolver().openFileDescriptor(Uri.parse(uri), "r");
            Nearby.getTransferEngine(mContext)
                    .sendData(HmsUtils.getInstance().convertToArrayList(endpointIds), Data.fromFile(pfd))
                    .addOnSuccessListener(aVoid -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("transferFile");
                        promise.resolve("Transfer file data success");
                    })
                    .addOnFailureListener(e -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("transferFile", e.getMessage());
                        promise.reject(E_TRANSFER, e.getMessage());
                    });
        } catch (FileNotFoundException e) {
            HmsLogger.getInstance(mContext).sendSingleEvent("transferFile", e.getMessage());
            promise.reject(E_TRANSFER, e.getMessage());
        }
    }

    @ReactMethod
    public void transferStream(String endpoint, ReadableArray endpointIds, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("transferStream");

        if (endpoint == null || endpoint.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("transferStream", "given endpoint is null or empty");
            promise.reject(E_TRANSFER, "given endpoint is null or empty");
            return;
        }

        if (!HmsUtils.getInstance().checkEndpointIds(endpointIds)) {
            HmsLogger.getInstance(mContext).sendSingleEvent("transferStream", "given readable array must not be empty. Only non-null and non-empty strings are allowed in it");
            promise.reject(E_TRANSFER, "given readable array must not be empty. Only non-null and non-empty strings are allowed in it");
            return;
        }

        try {
            URL url = new URL(endpoint);
            Nearby.getTransferEngine(mContext)
                    .sendData(HmsUtils.getInstance().convertToArrayList(endpointIds), Data.fromStream(url.openStream()))
                    .addOnSuccessListener(aVoid -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("transferStream");
                        promise.resolve("Transfer from stream success");
                    })
                    .addOnFailureListener(e -> {
                        HmsLogger.getInstance(mContext).sendSingleEvent("transferStream", e.getMessage());
                        promise.reject(E_TRANSFER, e.getMessage());
                    });
        } catch (IOException e) {
            HmsLogger.getInstance(mContext).sendSingleEvent("transferStream", e.getMessage());
            promise.reject(E_TRANSFER, e.getMessage());
        }
    }

    @ReactMethod
    public void cancelDataTransfer(String id, final Promise promise) {
        HmsLogger.getInstance(mContext).startMethodExecutionTimer("cancelDataTransfer");

        if (id == null || id.isEmpty()) {
            HmsLogger.getInstance(mContext).sendSingleEvent("cancelDataTransfer", "given data id is null or empty");
            promise.reject(E_TRANSFER, "given data id is null or empty");
            return;
        }

        Nearby.getTransferEngine(mContext)
                .cancelDataTransfer(Long.parseLong(id))
                .addOnSuccessListener(aVoid -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("cancelDataTransfer");
                    promise.resolve("Cancel data transfer success");
                })
                .addOnFailureListener(e -> {
                    HmsLogger.getInstance(mContext).sendSingleEvent("cancelDataTransfer", e.getMessage());
                    promise.reject(E_TRANSFER, e.getMessage());
                });
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return Constants.getTransferConstants();
    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }
}
