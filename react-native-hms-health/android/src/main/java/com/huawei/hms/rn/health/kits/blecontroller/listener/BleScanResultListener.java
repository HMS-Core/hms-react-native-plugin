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

package com.huawei.hms.rn.health.kits.blecontroller.listener;

import com.huawei.hms.hihealth.data.BleDeviceInfo;

/**
 * All the BleScanListener results are used among {@link com.huawei.hms.rn.health} kit.
 *
 * @since v.5.0.1
 */
public interface BleScanResultListener {

    /**
     * When the device is discovered, this method will be triggered.
     *
     * @param bleDeviceInfo BleDeviceInfo instance.
     */
    void onDeviceDiscover(BleDeviceInfo bleDeviceInfo);

    /**
     * When the scan ends, this method will be triggered.
     */
    void onScanEnd();
}
