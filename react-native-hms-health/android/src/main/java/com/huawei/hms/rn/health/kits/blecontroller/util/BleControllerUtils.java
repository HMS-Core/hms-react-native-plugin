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

package com.huawei.hms.rn.health.kits.blecontroller.util;

import static com.facebook.react.bridge.Arguments.createArray;

import com.huawei.hms.hihealth.data.BleDeviceInfo;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.rn.health.foundation.util.MapUtils;
import com.huawei.hms.rn.health.foundation.util.Utils;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * HmsBleControllerUtils exposes a set of helper methods for working with
 * {@link com.huawei.hms.rn.health.kits.blecontroller.HmsBleController}.
 *
 * @since v.5.0.1
 */
public class BleControllerUtils extends MapUtils {
    static final String DEVICE_ADDRESS = "deviceAddress";

    static final String DEVICE_NAME = "deviceName";

    static final String AVAILABLE_PROFILES = "availableProfiles";

    static final String DATA_TYPES = "dataTypes";

    /**
     * Converts ReadableArray instance into {@link List<DataType>}
     *
     * @param dataTypeMapArr ReadableArray instance.
     * @return List<com.huawei.hms.hihealth.data.DataType>
     */
    public static List<com.huawei.hms.hihealth.data.DataType> toDataTypeList(final ReadableArray dataTypeMapArr) {
        List<Object> dataTypeList = toArrayList(dataTypeMapArr);
        List<DataType> dataTypes = new ArrayList<>();
        for (Object dataType : dataTypeList) {
            dataTypes.add(Utils.INSTANCE.toDataType(String.valueOf(dataType)));
        }
        return dataTypes;
    }

    public static WritableArray stringListToWritableArray(List<String> stringList) {
        WritableArray writableArray = createArray();
        if (stringList != null) {
            for (String str : stringList) {
                writableArray.pushString(str);
            }
        }

        return writableArray;
    }

    public static WritableArray dataTypeListToWritableArray(List<DataType> dataTypes) {
        WritableArray writableArray = createArray();
        if (dataTypes != null) {
            for (DataType dataType : dataTypes) {
                writableArray.pushMap(toWritableMap(dataType));
            }
        }

        return writableArray;
    }

    public static BleDeviceInfo toBleDeviceInfo(final ReadableMap readableMap) {
        BleDeviceInfo bleDeviceInfo = new BleDeviceInfo();
        if (Utils.INSTANCE.hasKey(readableMap, DEVICE_NAME)) {
            bleDeviceInfo.setDeviceName(readableMap.getString(DEVICE_NAME));
        }
        if (Utils.INSTANCE.hasKey(readableMap, DEVICE_ADDRESS)) {
            bleDeviceInfo.setDeviceAddress(readableMap.getString(DEVICE_ADDRESS));
        }
        if (Utils.INSTANCE.hasKey(readableMap, AVAILABLE_PROFILES)) {
            bleDeviceInfo.setAvailableProfiles(toStringList(readableMap.getArray(AVAILABLE_PROFILES)));
        }
        if (Utils.INSTANCE.hasKey(readableMap, DATA_TYPES)) {
            bleDeviceInfo.setDataTypes(toDataTypeList(readableMap.getArray(DATA_TYPES)));
        }
        return bleDeviceInfo;
    }

    public static WritableMap toWritableMap(final BleDeviceInfo bleDeviceInfo) {
        WritableMap map = new WritableNativeMap();
        if (bleDeviceInfo != null) {
            map.putString(DEVICE_ADDRESS, bleDeviceInfo.getDeviceAddress());
            map.putString(DEVICE_NAME, bleDeviceInfo.getDeviceName());
            map.putArray(AVAILABLE_PROFILES, stringListToWritableArray(bleDeviceInfo.getAvailableProfiles()));
            map.putArray(DATA_TYPES, dataTypeListToWritableArray(bleDeviceInfo.getDataTypes()));
        }

        return map;
    }

    public static WritableArray bleDeviceInfoListToWritableArray(List<BleDeviceInfo> deviceInfoList) {
        WritableArray writableArray = createArray();
        if (deviceInfoList != null) {
            for (BleDeviceInfo deviceInfo : deviceInfoList) {
                writableArray.pushMap(toWritableMap(deviceInfo));
            }
        }

        return writableArray;
    }
}
