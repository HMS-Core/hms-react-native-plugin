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

package com.huawei.hms.rn.ads.oaid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.huawei.hms.rn.ads.utils.Constants;
import com.uodis.opendevice.aidl.OpenDeviceIdentifierService;

public class OaidAidlUtil {
    private static final String TAG = OaidAidlUtil.class.getSimpleName();
    private static final String SERVICE_ACTION = "com.uodis.opendevice.OPENIDS_SERVICE";
    private Context mContext;
    private ServiceConnection mServiceConnection;
    private OpenDeviceIdentifierService mService;
    private Promise mPromise;

    public OaidAidlUtil(Context context) {
        mContext = context;
    }

    /**
     * Bind OAID aidl services.
     */
    private boolean bindService() {
        Log.i(TAG, "bindService");
        if (null == mContext) {
            Log.e(TAG, "context is null");
            return false;
        }
        mServiceConnection = new IdentifierServiceConnection();
        Intent intent = new Intent(SERVICE_ACTION);
        intent.setPackage(Constants.SERVICE_PACKAGE_NAME);
        boolean result = mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "bindService result: " + result);
        return result;
    }

    /**
     * Unbind OAID aidl services.
     */
    private void unbindService() {
        Log.i(TAG, "unbindService");
        if (null == mContext) {
            Log.e(TAG, "context is null");
            return;
        }
        if (null != mServiceConnection) {
            mContext.unbindService(mServiceConnection);
            mService = null;
            mContext = null;
            mPromise = null;
        }
    }

    public void getOaid(final Promise promise) {
        mPromise = promise;
        //  Bind OAID aidl services.
        bindService();
    }

    private final class IdentifierServiceConnection implements ServiceConnection {
        private IdentifierServiceConnection() {
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "onServiceConnected");
            mService = OpenDeviceIdentifierService.Stub.asInterface(iBinder);
            if (null != mService) {
                try {
                    if (null != mPromise) {
                        WritableMap wm = new WritableNativeMap();
                        wm.putString("id", mService.getOaid());
                        wm.putBoolean("isLimitAdTrackingEnabled", mService.isOaidTrackLimited());
                        mPromise.resolve(wm);
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "getChannelInfo RemoteException");
                    mPromise.reject("REMOTE_EXCEPTION", e.getMessage());
                } finally {
                    unbindService();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected");
            mService = null;
        }
    }
}
