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

package com.huawei.hms.rn.nearby.modules;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

import com.huawei.hms.nearby.Nearby;
import com.huawei.hms.nearby.transfer.Data;
import com.huawei.hms.nearby.transfer.TransferEngine;
import com.huawei.hms.rn.nearby.utils.HMSUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static com.huawei.hms.rn.nearby.constants.HMSConstants.TRANSFER_CONSTANTS;
import static com.huawei.hms.rn.nearby.utils.HMSResult.BYTES_DATA_FAIL;
import static com.huawei.hms.rn.nearby.utils.HMSResult.ENDPOINT_ID_FAIL;
import static com.huawei.hms.rn.nearby.utils.HMSResult.STRING_PARAM_FAIL;

public class HMSTransfer extends HMSBase {

    /**
     * Constructor that initializes transfer module
     *
     * @param context app context
     */
    public HMSTransfer(ReactApplicationContext context) {
        super(context, HMSTransfer.class.getSimpleName(), TRANSFER_CONSTANTS);
    }

    /**
     * Sends byte array to multiple endpoints specified in a list.
     * The method can be called only when a connection has been successfully established.
     *
     * @param bytes Integer array that contains data. Value range is [-127, 127]
     * @param endpointIds String array that contains endpoint ids to transfer data
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void transferBytes(ReadableArray bytes, ReadableArray endpointIds, final Promise promise) {
        startMethodExecTimer("transferBytes");

        if (bytes.size() == 0 || bytes.size() >= TransferEngine.MAX_SIZE_DATA) {
            handleResult("transferBytes", BYTES_DATA_FAIL, promise);
            return;
        }

        if (HMSUtils.getInstance().checkEndpointIds(endpointIds)) {
            handleResult("transferBytes", ENDPOINT_ID_FAIL, promise);
            return;
        }

        handleResult("transferBytes", Nearby.getTransferEngine(getContext())
            .sendData(HMSUtils.getInstance().convertToArrayList(endpointIds),
                Data.fromBytes(HMSUtils.getInstance().convertReadableArrayToByteArray(bytes))), promise);
    }

    /**
     * Sends file data to multiple endpoints specified in a list.
     * The method can be called only when a connection has been successfully established.
     *
     * @param uri File uri.
     * @param endpointIds String array that contains endpoint ids to transfer data
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void transferFile(String uri, ReadableArray endpointIds, final Promise promise) {
        startMethodExecTimer("transferFile");

        if (TextUtils.isEmpty(uri)) {
            handleResult("transferFile", STRING_PARAM_FAIL, promise);
            return;
        }

        if (HMSUtils.getInstance().checkEndpointIds(endpointIds)) {
            handleResult("transferFile", ENDPOINT_ID_FAIL, promise);
            return;
        }

        try {
            ParcelFileDescriptor pfd = getContext().getContentResolver().openFileDescriptor(Uri.parse(uri), "r");
            handleResult("transferFile", Nearby.getTransferEngine(getContext())
                .sendData(HMSUtils.getInstance().convertToArrayList(endpointIds), Data.fromFile(pfd)), promise);
        } catch (FileNotFoundException e) {
            handleResult("transferFile", e, promise);
        }
    }

    /**
     * Sends stream data to multiple endpoints specified in a list.
     * The method can be called only when a connection has been successfully established.
     *
     * @param endpoint Url endpoint
     * @param endpointIds String array that contains endpoint ids to transfer data
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void transferStream(String endpoint, ReadableArray endpointIds, final Promise promise) {
        startMethodExecTimer("transferStream");

        if (TextUtils.isEmpty(endpoint)) {
            handleResult("transferStream", STRING_PARAM_FAIL, promise);
            return;
        }

        if (HMSUtils.getInstance().checkEndpointIds(endpointIds)) {
            handleResult("transferStream", ENDPOINT_ID_FAIL, promise);
            return;
        }

        try {
            URL url = new URL(endpoint);
            handleResult("transferStream", Nearby.getTransferEngine(getContext())
                    .sendData(HMSUtils.getInstance().convertToArrayList(endpointIds), Data.fromStream(url.openStream())),
                promise);
        } catch (IOException e) {
            handleResult("transferStream", e, promise);
        }
    }

    /**
     * Cancel data transfer for given data id.
     * The method can be called only when a connection has been successfully established.
     *
     * @param id Data id
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void cancelDataTransfer(String id, final Promise promise) {
        startMethodExecTimer("cancelDataTransfer");

        if (TextUtils.isEmpty(id)) {
            handleResult("cancelDataTransfer", STRING_PARAM_FAIL, promise);
            return;
        }

        handleResult("transferStream", Nearby.getTransferEngine(getContext()).cancelDataTransfer(Long.parseLong(id)),
            promise);
    }

}
