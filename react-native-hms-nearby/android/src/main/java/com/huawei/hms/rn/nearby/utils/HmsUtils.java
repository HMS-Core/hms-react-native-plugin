/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.nearby.utils;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.huawei.hms.nearby.discovery.Policy;
import com.huawei.hms.nearby.wifishare.WifiSharePolicy;
import com.huawei.hms.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.huawei.hms.rn.nearby.constants.HMSConstants.POLICY_MESH;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.POLICY_P2P;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.POLICY_SET;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.POLICY_SHARE;
import static com.huawei.hms.rn.nearby.constants.HMSConstants.POLICY_STAR;

public class HMSUtils {
    private static volatile HMSUtils instance;

    public static HMSUtils getInstance() {
        if (instance == null)
            instance = new HMSUtils();
        return instance;
    }

    /**
     * Helper method for finding Policy type for Wifi Sharing
     *
     * @param policy number that defines policy type
     * @return WifiSharePolicy object or null
     */
    @Nullable
    public WifiSharePolicy getWifiSharePolicyByNumber(int policy) {
        if (policy == POLICY_SHARE) {
            return WifiSharePolicy.POLICY_SHARE;
        } else if (policy == POLICY_SET) {
            return WifiSharePolicy.POLICY_SET;
        } else {
            return null;
        }
    }

    /**
     * Helper method for finding Policy type for Connection
     *
     * @param policy number that defines policy type
     * @return Policy object or null
     */
    @Nullable
    public Policy getPolicyByNumber(int policy) {
        if (policy == POLICY_MESH) {
            return Policy.POLICY_MESH;
        } else if (policy == POLICY_P2P) {
            return Policy.POLICY_P2P;
        } else if (policy == POLICY_STAR) {
            return Policy.POLICY_STAR;
        } else {
            return null;
        }
    }

    /**
     * Helper method that checks if given key valid or not
     *
     * @param readableMap ReadableMap object
     * @param key         key in ReadableMap
     * @param type        key type
     * @return true or false
     */
    public boolean hasValidKey(ReadableMap readableMap, String key, ReadableType type) {
        return readableMap.hasKey(key) && readableMap.getType(key) == type;
    }

    /**
     * Helper method that returns given keys boolean value if given key is valid
     *
     * @param readableMap ReadableMap object
     * @param key         key in ReadableMap
     * @return true or false
     */
    public boolean boolKeyCheck(ReadableMap readableMap, String key) {
        if (!hasValidKey(readableMap, key, ReadableType.Boolean))
            return false;
        return readableMap.getBoolean(key);
    }

    /**
     * Helper method that converts ReadableArray to byte[]
     *
     * @param ra ReadableArray
     * @return byte[]
     */
    public byte[] convertReadableArrayToByteArray(ReadableArray ra) {
        byte[] bytes = new byte[ra.size()];
        for (int i = 0; i < ra.size(); i++) {
            bytes[i] = (byte) ra.getInt(i);
        }
        return bytes;
    }

    /**
     * Helper method that converts byte[] to WritableArray
     *
     * @param ba ReadableArray
     * @return WritableArray
     */
    public WritableArray convertByteArrayToWritableArray(byte[] ba) {
        WritableArray wa = Arguments.createArray();
        for (byte b : ba) {
            wa.pushInt(b);
        }
        return wa;
    }

    /**
     * Helper method that converts InputStream to WritableArray
     *
     * @param is InputStream
     * @return WritableArray
     * @throws IOException Input Output Exception
     */
    public WritableArray convertInputStreamToWritableArray(InputStream is) throws IOException {
        byte[] bytes = IOUtils.toByteArray(is);
        return convertByteArrayToWritableArray(bytes);
    }

    /**
     * Helper method that checks endpoint ids in ReadableArray are valid
     *
     * @param ra ReadableArray
     * @return true or false
     */
    public boolean checkEndpointIds(ReadableArray ra) {
        if (ra.size() == 0) {
            return true;
        }
        for (int i = 0; i < ra.size(); i++) {
            if (ra.getType(i) != ReadableType.String || ra.getString(i).isEmpty())
                return true;
        }
        return false;
    }

    /**
     * Helper method that converts ReadableArray of endpoint ids to ArrayList
     *
     * @param ra ReadableArray
     * @return ArrayList
     */
    public ArrayList<String> convertToArrayList(ReadableArray ra) {
        ArrayList<String> endpointIds = new ArrayList<>();
        for (int i = 0; i < ra.size(); i++) {
            endpointIds.add(ra.getString(i));
        }
        return endpointIds;
    }

}
