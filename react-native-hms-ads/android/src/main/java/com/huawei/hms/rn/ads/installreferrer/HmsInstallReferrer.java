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

package com.huawei.hms.rn.ads.installreferrer;

import androidx.annotation.Nullable;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.ads.installreferrer.api.InstallReferrerClient;

import static com.huawei.hms.rn.ads.RNHMSAdsInstallReferrerModule.Event;

public abstract class HmsInstallReferrer {
    private static final String TAG = HmsInstallReferrer.class.getSimpleName();

    final ReactApplicationContext context;
    Status status;

    public enum Status {
        CREATED,
        CONNECTED,
        DISCONNECTED
    }

    HmsInstallReferrer(ReactApplicationContext context) {
        this.context = context;
        this.status = Status.CREATED;
    }

    public static InstallReferrerSdkUtil createSdkReferrer(ReactApplicationContext context) {
        return new InstallReferrerSdkUtil(context);
    }

    public static InstallReferrerAidlUtil createAidlReferrer(ReactApplicationContext context, String pkgName) {
        return new InstallReferrerAidlUtil(context, pkgName);
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Connects to service
     *
     * @param isTest is test package
     * @return is connected
     */
    public abstract boolean connect(boolean isTest);

    /**
     * Disconnects from service
     */
    public abstract void disconnect();

    /**
     * Gets install referrer details
     *
     * @param promise response to the JS part
     */
    public abstract void getInstallReferrer(Promise promise);

    /**
     * Checks service is ready
     *
     * @return is service ready
     */
    public abstract boolean isReady();

    void sendEvent(Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event.getName(), wm);
    }

    String getResponseMessage(int responseCode) {
        String responseMessage = "Unknown response";
        switch (responseCode) {
            case InstallReferrerClient.InstallReferrerResponse.OK:
                responseMessage = "Connected to the service successfully";
                break;
            case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                // Service not supported. Please download and install the latest version of Huawei Mobile
                // Services(APK).
                responseMessage = "The service is not supported";
                break;
            case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                // Service unavailable. Please update the version of Huawei Mobile Services(APK) to 2.6.5 or later.
                responseMessage = "The service does not exist";
                break;
            case InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED:
                responseMessage = "Failed to connect to the service";
                break;
            case InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR:
                responseMessage = "A call error occurred";
                break;
            default:
                break;
        }
        Log.i(TAG, responseMessage);
        return responseMessage;
    }
}
