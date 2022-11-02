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

package com.huawei.hms.rn.health.kits.account.viewmodel;

import com.huawei.hms.rn.health.kits.account.listener.AccountResultListener;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

/**
 * Blueprint of {@link AccountViewModel}.
 *
 * @since v.5.0.1
 */
public interface AccountService {
    /**
     * Blueprint of signIn function
     *
     * @param authService HuaweiIdAuthService instance.
     * @param accountResultListener LoginResultListener instance.
     */
    void signIn(final HuaweiIdAuthService authService, final AccountResultListener accountResultListener);
}
