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

package com.huawei.hms.rn.location.backend.interfaces;

import org.json.JSONException;

public interface JSONTriMapper<T, U, V, R> {
    /**
     * A simple mapping from T, U, V to R that may throw JSONException.
     *
     * @param in  T
     * @param in2 U
     * @param in3 V
     * @return R
     * @throws JSONException if needed
     */
    R run(T in, U in2, V in3) throws JSONException;
}
