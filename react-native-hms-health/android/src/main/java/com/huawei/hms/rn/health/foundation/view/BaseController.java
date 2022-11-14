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

package com.huawei.hms.rn.health.foundation.view;

import static com.huawei.hms.rn.health.kits.datacontroller.util.DataControllerConstants.DATA_CONSTANTS_MAP;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

import java.util.Map;

/**
 * BaseController of all the {@link com.huawei.hms.rn.health} Module implementations.
 *
 * @since v.5.0.1
 */
public class BaseController extends ReactContextBaseJavaModule {
    private String name;

    public BaseController(String name, @NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        this.name = name;
    }

    /**
     * "com.huawei.hms.rn.health" package class names.
     *
     * @return class name
     */
    @NonNull
    @Override
    public String getName() {
        return name;
    }

    /**
     * getConstants exposes the constant values
     * to RN Side.
     *
     * @return constants
     */
    @Override
    public Map<String, Object> getConstants() {
        return DATA_CONSTANTS_MAP;
    }
}
