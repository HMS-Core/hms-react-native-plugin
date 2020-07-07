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

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.huawei.hms.ads.installreferrer.api.InstallReferrerClient;
import com.huawei.hms.ads.installreferrer.api.ReferrerDetails;
import com.huawei.hms.rn.ads.RNHMSAdsInstallReferrerModule;
import com.huawei.hms.rn.ads.utils.Constants;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.huawei.hms.rn.ads.RNHMSAdsInstallReferrerModule.Event;

public class InstallReferrerAidlUtil extends HmsInstallReferrer {
    private static final String TAG = InstallReferrerAidlUtil.class.getSimpleName();
    private ReactApplicationContext mContext;
    private ServiceConnection mServiceConnection;
    private IPPSChannelInfoService mService;
    private Promise mPromise;
    private ReferrerDetails mReferrerDetails;
    private String mPkgName;

    public InstallReferrerAidlUtil(ReactApplicationContext context, String pkgName) {
        super(context);
        mContext = context;
        mPkgName = pkgName;
    }

    public boolean connect(boolean isTest) {
        Log.i(TAG, "bindService");
        if (null == mContext) {
            Log.e(TAG, "context is null");
            return false;
        }
        mServiceConnection = new InstallReferrerServiceConnection();
        Intent intent = new Intent(Constants.SERVICE_ACTION);
        intent.setPackage(isTest ? mPkgName : Constants.SERVICE_PACKAGE_NAME);
        // Bind HUAWEI Ads kit
        boolean isConnected = mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "bindService isConnected: " + isConnected);
        if (isConnected) {
            status = Status.CONNECTED;
        }
        return isConnected;
    }

    public void disconnect() {
        Log.i(TAG, "unbindService");
        if (null == mContext) {
            Log.e(TAG, "context is null");
            return;
        }
        if (null != mServiceConnection) {
            // Unbind HUAWEI Ads kit
            mContext.unbindService(mServiceConnection);
            status = Status.DISCONNECTED;
            mService = null;
            mContext = null;
            mPromise = null;
        }
    }

    public void getInstallReferrer(final Promise promise) {
        mPromise = promise;
        mPromise.resolve(ReactUtils.getWritableMapFromReferrerDetails(mReferrerDetails));
    }

    public boolean isReady() {
        return mService != null;
    }

    private final class InstallReferrerServiceConnection implements ServiceConnection {
        private InstallReferrerServiceConnection() {
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "onServiceConnected");
            int responseCode = InstallReferrerClient.InstallReferrerResponse.OK;
            WritableMap wm = new WritableNativeMap();
            wm.putInt("responseCode", responseCode);
            wm.putString("responseMessage", getResponseMessage(responseCode));
            mService = IPPSChannelInfoService.Stub.asInterface(iBinder);
            if (null != mService) {
                try {
                    sendEvent(Event.SERVICE_CONNECTED, wm);
                    // Get channel info（Json format）
                    String channelJson = mService.getChannelInfo();
                    Log.i(TAG, "channelJson: " + channelJson);
                    // Parser
                    JSONObject jsonObject = new JSONObject(mService.getChannelInfo());
                    // Get install referrer.
                    mReferrerDetails = new ReferrerDetails(
                            jsonObject.optString("channelInfo"),
                            jsonObject.optLong("clickTimestamp", 0),
                            jsonObject.optLong("installTimestamp", 0));

                } catch (RemoteException e) {
                    Log.e(TAG, "getChannelInfo RemoteException");
                } catch (JSONException e) {
                    Log.e(TAG, "getChannelInfo JSONException");
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected");
            sendEvent(Event.SERVICE_DISCONNECTED, null);
            mService = null;
        }
    }

}
