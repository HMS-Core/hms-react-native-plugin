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

package com.huawei.hms.rn.health.kits.consents.viewmodel;

import android.util.Log;

import com.huawei.hms.hihealth.ConsentsController;
import com.huawei.hms.hihealth.data.ScopeLangItem;
import com.huawei.hms.rn.health.foundation.helper.VoidResultHelper;
import com.huawei.hms.rn.health.foundation.listener.ResultListener;

import java.util.List;

public class ConsentsViewModel implements ConsentsService {
    private static final String TAG = ConsentsViewModel.class.getSimpleName();

    /**
     * This methods gets will return a list of scopes that is given to the application.
     *
     * @param consentsController ConsentsController instance
     * @param language Language string. If it's not specified the default is 'en-us'
     * @param appId ID of your app (not the package name)
     * @param listener Helper class for returning result.
     */
    @Override
    public void get(ConsentsController consentsController, String language, String appId,
        ResultListener<ScopeLangItem> listener) {
        Log.i(TAG, "call ConsentsController.get");
        consentsController.get(language, appId)
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);
    }

    /**
     * Revokes certain Health Kit related permissions granted to your app.
     *
     * @param consentsController ConsentsController instance
     * @param appId Id of your app (not the package name)
     * @param scopeList List of the scopes that are wanted to be removed. If it's null, revokes every scope.
     * @param listener Helper class for returning result.
     */
    @Override
    public void revoke(ConsentsController consentsController, String appId, List<String> scopeList,
        VoidResultHelper listener) {
        Log.i(TAG, "call ConsentsController.revoke");

        if (scopeList == null) {
            consentsController.revoke(appId)
                .addOnSuccessListener(listener::onSuccess)
                .addOnFailureListener(listener::onFail);
        } else {
            consentsController.revoke(appId, scopeList)
                .addOnSuccessListener(listener::onSuccess)
                .addOnFailureListener(listener::onFail);
        }

    }

    /**
     * Cancels certain Health Kit related scopes granted to your app.
     *
     * @param consentsController ConsentsController instance
     * @param appId ID of your app.
     * @param scopeList List of Health Kit related permissions to be revoked. The value is the key value of url2Desc in ScopeLangItem.
     * @param @param listener Helper class for returning result.
     */
    @Override
    public void cancelAuthorization(ConsentsController consentsController, String appId, List<String> scopeList,
        VoidResultHelper listener) {
        Log.i(TAG, "call ConsentsController.cancelAuthorization");

        consentsController.cancelAuthorization(appId, scopeList)
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);

    }

    /**
     * Specifies whether to delete user data when all scopes granted to your app are canceled.
     *
     * @param consentsController ConsentsController instance
     * @param deleteData Whether to delete user data.
     * @param listener Helper class for returning result.
     */
    @Override
    public void cancelAuthorizationAll(ConsentsController consentsController, boolean deleteData,
        VoidResultHelper listener) {
        Log.i(TAG, "call ConsentsController.cancelAuthorizationAll");

        consentsController.cancelAuthorization(deleteData)
            .addOnSuccessListener(listener::onSuccess)
            .addOnFailureListener(listener::onFail);

    }
}
