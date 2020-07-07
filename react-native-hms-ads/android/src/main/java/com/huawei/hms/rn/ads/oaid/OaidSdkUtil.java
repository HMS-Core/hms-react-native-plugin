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

import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.huawei.hms.ads.identifier.AdvertisingIdClient;
import com.huawei.hms.rn.ads.utils.ReactUtils;

import java.io.IOException;

public class OaidSdkUtil {
    private static final String TAG = OaidSdkUtil.class.getSimpleName();

    public static void getOaid(Context context, final Promise promise) {
        if (null == context || null == promise) {
            Log.e(TAG, "invalid input param");
            return;
        }
        try {
            //Get advertising id information. Do not call this method in the main thread.
            AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(context);
            promise.resolve(ReactUtils.getWritableMapFromAdvertisingIdClientInfo(info));
        } catch (IOException e) {
            Log.e(TAG, "getAdvertisingIdInfo IOException");
            promise.reject("GET_AD_ID_INFO_FAILED", "getAdvertisingIdInfo IOException");
        }
    }

}
