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

package com.huawei.hms.rn.nearby.utils;

import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.nearby.discovery.Policy;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HmsUtils {
    private static volatile HmsUtils instance;

    public static HmsUtils getInstance() {
        if (instance == null)
            instance = new HmsUtils();
        return instance;
    }

    public Policy getPolicyByNumber(int policy) {
        if (policy == Policy.MESH) {
            return Policy.POLICY_MESH;
        } else if (policy == Policy.P2P) {
            return Policy.POLICY_P2P;
        } else if (policy == Policy.STAR) {
            return Policy.POLICY_STAR;
        } else {
            return null;
        }
    }

    public void sendEvent(ReactContext reactContext, String eventName, String methodName, @Nullable WritableMap params) {
        HmsLogger.getInstance(reactContext).sendSingleEvent(methodName);
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    public boolean hasValidKey(ReadableMap readableMap, String key, ReadableType type) {
        return readableMap.hasKey(key) && readableMap.getType(key) == type;
    }

    public boolean boolKeyCheck(ReadableMap readableMap, String key) {
        if (!hasValidKey(readableMap, key, ReadableType.Boolean))
            return false;
        return readableMap.getBoolean(key);
    }

    public byte[] convertRaToByteArray(ReadableArray ra) {
        byte[] bytes = new byte[ra.size()];
        for (int i = 0; i < ra.size(); i++) {
            bytes[i] = (byte) ra.getInt(i);
        }
        return bytes;
    }

    public WritableArray convertByteArrToWa(byte[] ba) {
        WritableArray wa = Arguments.createArray();
        for (byte b : ba) {
            wa.pushInt(b);
        }
        return wa;
    }

    public String convertStreamToString(InputStream is, String ecoding) throws IOException {
        StringBuilder sb = new StringBuilder(Math.max(16, is.available()));
        char[] tmp = new char[4096];

        try {
            InputStreamReader reader = new InputStreamReader(is, ecoding);
            for (int cnt; (cnt = reader.read(tmp)) > 0; )
                sb.append(tmp, 0, cnt);
        } catch (UnsupportedEncodingException e) {
            Log.d("convertStreamToString", "unsupported encoding");
        } finally {
            is.close();
        }
        return sb.toString();
    }

    public boolean checkEndpointIds(ReadableArray ra) {
        if (ra.size() == 0) {
            return false;
        }
        for (int i = 0; i < ra.size(); i++) {
            if (ra.getType(i) != ReadableType.String)
                return false;
            else if (ra.getString(i) == null || ra.getString(i).isEmpty())
                return false;
        }
        return true;
    }

    public ArrayList<String> convertToArrayList(ReadableArray ra) {
        ArrayList<String> endpointIds = new ArrayList<>();
        for (int i = 0; i < ra.size(); i++) {
            endpointIds.add(ra.getString(i));
        }
        return endpointIds;
    }
}
