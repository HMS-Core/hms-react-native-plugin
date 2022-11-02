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

package com.huawei.hms.rn.health.kits.account.util;

import static com.huawei.hms.rn.health.foundation.util.MapUtils.toArrayList;

import com.huawei.hms.rn.health.kits.account.HmsHealthAccount;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;

import com.facebook.react.bridge.ReadableArray;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AccountUtils} exposes a set of helper methods for working with
 * {@link HmsHealthAccount}.
 *
 * @since v.5.0.1
 */
public enum AccountUtils {
    INSTANCE;

    /**
     * Converts ReadableArray into {@link List<Scope>} instance.
     *
     * @param readableArray ReadableArray instance that will be referred to {@code List<Scope>}
     * @return List<Scope>
     */
    public synchronized List<Scope> toScopeList(final ReadableArray readableArray) {
        List<Object> scopeList = toArrayList(readableArray);
        List<Scope> scopes = new ArrayList<>();
        for (Object scope : scopeList) {
            scopes.add(new Scope((String) scope));
        }
        return scopes;
    }

    /**
     * Creates and returns {@link HuaweiIdAuthParamsHelper} instance.
     *
     * @return {@link HuaweiIdAuthParamsHelper} instance
     */
    public synchronized HuaweiIdAuthParamsHelper getAuthParamsHelper() {
        return new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM);
    }

    /**
     * Creates and returns {@link HuaweiIdAuthParams} instance.
     *
     * @return {@link HuaweiIdAuthParams} instance
     */
    public synchronized HuaweiIdAuthParams getAuthParams(final HuaweiIdAuthParamsHelper authParamsHelper,
        final List<Scope> scopeList) {
        return authParamsHelper.setIdToken().setAccessToken().setScopeList(scopeList).createParams();
    }
}