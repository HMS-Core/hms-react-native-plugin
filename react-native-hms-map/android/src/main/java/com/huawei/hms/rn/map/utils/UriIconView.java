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

package com.huawei.hms.rn.map.utils;

import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.maps.model.BitmapDescriptor;


public interface UriIconView {
    /**
     * This methods sets the icon or image according to parameter and the parent class.
     * @param bitmapDescriptor bitmap that will be set
     * @param options Additional options to customize icon
     */
    void setUriIcon(BitmapDescriptor bitmapDescriptor, ReadableMap options);
}
