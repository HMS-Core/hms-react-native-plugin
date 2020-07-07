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

import android.os.RemoteException;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.huawei.hms.ads.installreferrer.api.InstallReferrerClient;
import com.huawei.hms.ads.installreferrer.api.InstallReferrerStateListener;
import com.huawei.hms.ads.installreferrer.api.ReferrerDetails;
import com.huawei.hms.rn.ads.RNHMSAdsInstallReferrerModule;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import java.io.IOException;

import static com.huawei.hms.rn.ads.RNHMSAdsInstallReferrerModule.Event;

public class InstallReferrerSdkUtil extends HmsInstallReferrer {
    private static final String TAG = InstallReferrerSdkUtil.class.getSimpleName();
    private ReactApplicationContext mContext;
    private InstallReferrerClient mReferrerClient;
    private Promise mPromise;

    public InstallReferrerSdkUtil(ReactApplicationContext context) {
        super(context);
        mContext = context;
    }

    /**
     * connect huawei ads service.
     */
    public boolean connect(boolean isTest) {
        Log.i(TAG, "connect...");
        if (null == mContext) {
            Log.e(TAG, "connect context is null");
            return false;
        }
        // Create InstallReferrerClient
        mReferrerClient = InstallReferrerClient.newBuilder(mContext).setTest(isTest).build();
        // Start connecting service
        mReferrerClient.startConnection(installReferrerStateListener);
        status = Status.CONNECTED;
        return true;
    }

    /**
     * diconnect from huawei ads service.
     */
    public void disconnect() {
        Log.i(TAG, "disconnect");
        if (null != mReferrerClient) {
            mReferrerClient.endConnection();
            status = Status.DISCONNECTED;
            mReferrerClient = null;
            mContext = null;
        }
    }

    public void getInstallReferrer(final Promise promise) {
        if (null == promise) {
            Log.e(TAG, "getInstallReferrer promise is null");
            return;
        }
        mPromise = promise;
        get();
    }

    public boolean isReady() {
        if (mReferrerClient != null) {
            return mReferrerClient.isReady();
        }
        return false;
    }

    /**
     * Obtain install referrer.
     */
    private void get() {
        if (null != mReferrerClient) {
            try {
                // install referrer id information. Do not call this method in the main thread.
                ReferrerDetails referrerDetails = mReferrerClient.getInstallReferrer();
                if (null != referrerDetails && null != mPromise) {
                    // Update install referer details.
                    mPromise.resolve(ReactUtils.getWritableMapFromReferrerDetails(referrerDetails));
                }
            } catch (RemoteException e) {
                Log.i(TAG, "getInstallReferrer RemoteException: " + e.getMessage());
            } catch (IOException e) {
                Log.i(TAG, "getInstallReferrer IOException: " + e.getMessage());
            }
        }
    }

    /**
     * create new connect listener.
     */
    private InstallReferrerStateListener installReferrerStateListener = new InstallReferrerStateListener() {
        @Override
        public void onInstallReferrerSetupFinished(int responseCode) {
            WritableMap wm = new WritableNativeMap();
            wm.putInt("responseCode", responseCode);
            wm.putString("responseMessage", getResponseMessage(responseCode));
            sendEvent(Event.SERVICE_CONNECTED, wm);
        }

        @Override
        public void onInstallReferrerServiceDisconnected() {
            Log.i(TAG, "onInstallReferrerServiceDisconnected");
            sendEvent(Event.SERVICE_DISCONNECTED, null);
        }
    };

}
