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

import android.text.TextUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.nearby.Nearby;
import com.huawei.hms.nearby.discovery.ScanEndpointInfo;
import com.huawei.hms.nearby.wifishare.WifiShareCallback;
import com.huawei.hms.nearby.wifishare.WifiShareEngine;
import com.huawei.hms.nearby.wifishare.WifiSharePolicy;
import com.huawei.hms.rn.nearby.utils.HMSUtils;

import static com.huawei.hms.rn.nearby.constants.HMSConstants.WIFI_CALLBACK;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.WIFI_ON_FETCH_AUTH_CODE;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.WIFI_ON_FOUND;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.WIFI_ON_LOST;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.WIFI_ON_RESULT;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.WIFI_SHARE_CONSTANTS;
import static com.huawei.hms.rn.nearby.utils.HMSResult.POLICY_FAIL;
import static com.huawei.hms.rn.nearby.utils.HMSResult.STRING_PARAM_FAIL;

public class HMSWifiShare extends HMSBase {
    private WifiShareEngine wifiShareEngine;

    /**
     * Constructor that initializes wifi share module and wifi share engine
     *
     * @param context app context
     */
    public HMSWifiShare(ReactApplicationContext context) {
        super(context, HMSWifiShare.class.getSimpleName(), WIFI_SHARE_CONSTANTS);
        wifiShareEngine = Nearby.getWifiShareEngine(context);
    }

    /**
     * Enables the Wi-Fi sharing function. Set WifiSharePolicy based on function requirements.
     * Sets {@link #getWifiShareCallback()} )} to discovering or detecting the loss of devices on which Wi-Fi can be configured,
     * obtaining verification codes, and obtaining the Wi-Fi configuration result.
     *
     * @param policy Specifies the policy type that creates WifiSharePolicy: SET, SHARE.
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void startWifiShare(int policy, final Promise promise) {
        startMethodExecTimer("startWifiShare");
        WifiSharePolicy wifiSharePolicy = HMSUtils.getInstance().getWifiSharePolicyByNumber(policy);

        if (wifiSharePolicy == null) {
            handleResult("startWifiShare", POLICY_FAIL, promise);
            return;
        }

        handleResult("startWifiShare", wifiShareEngine.startWifiShare(getWifiShareCallback(), wifiSharePolicy),
            promise);
    }

    /**
     * Disables the Wi-Fi sharing function.
     * 
     * @param promise Return a status code and message.
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void stopWifiShare(final Promise promise) {
        startMethodExecTimer("stopWifiShare");
        handleResult("stopWifiShare", wifiShareEngine.stopWifiShare(), promise);
    }

    /**
     * Shares Wi-Fi with a remote device.
     *
     * @param endpointId ID of the remote endpoint.
     * @param promise A Promise that resolves a result object.
     */
    @ReactMethod
    public void shareWifiConfig(String endpointId, final Promise promise) {
        startMethodExecTimer("shareWifiConfig");

        if (TextUtils.isEmpty(endpointId)) {
            handleResult("shareWifiConfig", STRING_PARAM_FAIL, promise);
            return;
        }

        handleResult("shareWifiConfig", wifiShareEngine.shareWifiConfig(endpointId), promise);
    }

    /**
     * Creates wifi share callback
     *
     * @return WifiShareCallback
     */
    private WifiShareCallback getWifiShareCallback() {
        return new WifiShareCallback() {
            @Override
            public void onFound(String endpointId, ScanEndpointInfo scanEndpointInfo) {
                WritableMap wm = Arguments.createMap();
                wm.putString("endpointId", endpointId);
                wm.putString("name", scanEndpointInfo.getName());
                wm.putString("serviceId", scanEndpointInfo.getServiceId());
                sendEvent(WIFI_ON_FOUND, WIFI_CALLBACK, wm);
            }

            @Override
            public void onLost(String endpointId) {
                WritableMap wm = Arguments.createMap();
                wm.putString("endpointId", endpointId);
                sendEvent(WIFI_ON_LOST, WIFI_CALLBACK, wm);
            }

            @Override
            public void onFetchAuthCode(String endpointId, String authCode) {
                WritableMap wm = Arguments.createMap();
                wm.putString("endpointId", endpointId);
                wm.putString("authCode", authCode);
                sendEvent(WIFI_ON_FETCH_AUTH_CODE, WIFI_CALLBACK, wm);
            }

            @Override
            public void onWifiShareResult(String endpointId, int statusCode) {
                WritableMap wm = Arguments.createMap();
                wm.putString("endpointId", endpointId);
                wm.putInt("statusCode", statusCode);
                sendEvent(WIFI_ON_RESULT, WIFI_CALLBACK, wm);
            }
        };
    }

}
