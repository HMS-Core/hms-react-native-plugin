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

import android.util.Log;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.rn.health.kits.account.listener.AccountResultListener;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

/**
 * The authorization and sign-in API must be called before each time the health module is used.
 *
 * @since v.5.0.1
 */
public class AccountViewModel implements AccountService {

    private static final String TAG = AccountService.class.getSimpleName();

    /**
     * Silent sign-in.
     * </br>
     * If authorization has been granted by the current account,
     * the authorization screen will not display.
     *
     * @param accountResultListener returns LoginResultListener instance.
     */
    @Override
    public void signIn(final HuaweiIdAuthService authService, final AccountResultListener accountResultListener) {
        Log.i(TAG, "call signIn");
        Task<AuthHuaweiId> authHuaweiIdTask = authService.silentSignIn();
        authHuaweiIdTask.addOnSuccessListener(huaweiId -> {
            /* The silent sign-in is successful. */
            Log.i(TAG, "silentSignIn success");
            accountResultListener.onSuccess(huaweiId);
        }).addOnFailureListener(exception -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.i(TAG, "sign failed status:" + apiException.getStatusCode());
                /* The silent sign-in fails. */
                /* This indicates that the authorization has not been granted by the current account. */
                accountResultListener.onSilentSignInFail();
            } else {
                accountResultListener.onFail(exception);
            }
        });
    }
}
