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

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.huawei.hms.rn.ads.utils.Constants;

public class PpsChannelInfoService extends Service {
    private static final String TAG = PpsChannelInfoService.class.getSimpleName();

    private final IPPSChannelInfoService.Stub mBinder = new IPPSChannelInfoService.Stub() {
        @Override
        public String getChannelInfo() throws RemoteException {
            PackageManager pkgmgr = getPackageManager();
            final String callerPkg = getCallerPkgSafe(pkgmgr, Binder.getCallingUid());
            Log.i(TAG, "callerPkg=" + callerPkg);
            SharedPreferences sp = getSharedPreferences(Constants.INSTALL_REFERRER_FILE, Context.MODE_PRIVATE);
            String installReferrer = sp.getString(callerPkg, "");
            Log.i(TAG, "installReferrer=" + installReferrer);
            return installReferrer;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    public static String getCallerPkgSafe(PackageManager packageManager, int uid) {
        if (null == packageManager) {
            return "";
        }
        return packageManager.getNameForUid(uid);
    }
}

