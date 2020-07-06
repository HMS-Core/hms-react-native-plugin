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

package com.huawei.hms.rn.iap.client;

import android.app.Activity;

import com.huawei.hms.iap.IapClient;
import com.huawei.hms.iap.entity.ConsumeOwnedPurchaseReq;
import com.huawei.hms.iap.entity.ConsumeOwnedPurchaseResult;
import com.huawei.hms.iap.entity.IsEnvReadyResult;
import com.huawei.hms.iap.entity.IsSandboxActivatedResult;
import com.huawei.hms.iap.entity.OwnedPurchasesReq;
import com.huawei.hms.iap.entity.OwnedPurchasesResult;
import com.huawei.hms.iap.entity.ProductInfoReq;
import com.huawei.hms.iap.entity.ProductInfoResult;
import com.huawei.hms.iap.entity.PurchaseIntentReq;
import com.huawei.hms.iap.entity.PurchaseIntentResult;
import com.huawei.hms.iap.entity.StartIapActivityReq;
import com.huawei.hms.iap.entity.StartIapActivityResult;

/**
 * IapRequestContract defines a blueprint of {@link IapClientReqContract} methods.
 *
 * @since v.5.0.0
 */

public interface IapClientReqContract {

    /**
     * View protocol that presents current Activity.
     */
    interface View {
        /**
         * Represents current activity.
         *
         * @return Activity instance.
         */
        Activity getActivity();
    }

    /**
     *
     */
    interface Presenter {
        /**
         * isEnvironmentReady presents to check whether the currently signed-in HUAWEI ID
         * is located in a country or region where HUAWEI IAP is available.
         *
         * @param mClient:                  IapClient instance to call the isEnvReady API.
         * @param isEnvReadyResultListener: IAPResultListener with IsEnvReadyResult instance.
         */
        void isEnvironmentReady(IapClient mClient, final IAPResultListener<IsEnvReadyResult> isEnvReadyResultListener);

        /**
         * isSandboxActivated presents to check whether the sign-in HUAWEI ID and app APK version meets the requirements
         * of the sandbox testing.
         *
         * @param mClient:                          IapClient instance to call the isSandboxActivated API.
         * @param isSandboxActivatedResultListener: IAPResultListener with IsSandboxActivatedListener instance.
         */
        void isSandboxActivated(final IapClient mClient, final IAPResultListener<IsSandboxActivatedResult> isSandboxActivatedResultListener);

        /**
         * Presents to query information about all purchased in-app products, including consumables, non-consumables, and auto-renewable subscriptions.</br>
         *
         * @param mClient:                      IapClient instance to call the obtainOwnedPurchases API.
         * @param ownedPurchasesReq:            OwnedPurchasesReq object.
         * @param ownedPurchasesResultListener: IAPResultListener with QueryPurchasesListener instance.
         */
        void obtainOwnedPurchases(final IapClient mClient, final OwnedPurchasesReq ownedPurchasesReq, final IAPResultListener<OwnedPurchasesResult> ownedPurchasesResultListener);

        /**
         * Presents to obtain in-app product details configured in AppGallery Connect.
         * If you use Huawei’s PMS to price in-app products, you can use this API to obtain in-app product details from the PMS to ensure that the in-app product information in your app is the same as that displayed on the checkout page of HUAWEI IAP.
         * </br>
         *
         * @param iapClient:                 IapClient instance to call the obtainOwnedPurchases API.
         * @param productInfoReq:            ProductInfoReq object.
         * @param productInfoResultListener: IAPResultListener with ProductInfoResultListener instance.
         */
        void obtainProductInfo(final IapClient iapClient, final ProductInfoReq productInfoReq, final IAPResultListener<ProductInfoResult> productInfoResultListener);

        /**
         * Presents to create orders for PMS products, including consumables, non-consumables, and subscriptions.
         *
         * @param mClient:                      IapClient instance to call the obtainOwnedPurchases API.
         * @param purchaseIntentReq:            PurchaseIntentReq object.
         * @param purchaseIntentResultListener: IAPResultListener with PurchaseIntentResultListener instance.
         */
        void createPurchaseIntent(final IapClient mClient, final PurchaseIntentReq purchaseIntentReq, final IAPResultListener<PurchaseIntentResult> purchaseIntentResultListener);

        /**
         * Presents to consume a consumable after the consumable is delivered to a user who has completed payment.
         *
         * @param iapClient:                          IapClient instance to call the consumeOwnedPurchase API.
         * @param consumeOwnedPurchaseReq:            ConsumeOwnedPurchaseReq instance which contains request information.
         * @param consumeOwnedPurchaseResultListener: IAPResultListener with ConsumeOwnedPurchaseResultListener instance.
         */
        void consumeOwnedPurchase(final IapClient iapClient, final ConsumeOwnedPurchaseReq consumeOwnedPurchaseReq, final IAPResultListener<ConsumeOwnedPurchaseResult> consumeOwnedPurchaseResultListener);

        /**
         * Presents to obtain the historical consumption information about a consumable or all subscription receipts of a subscription.
         *
         * @param iapClient:                    IapClient instance to call the obtainOwnedPurchaseRecord API.
         * @param ownedPurchasesReq:            OwnedPurchasesReq instance.
         * @param ownedPurchasesResultListener: IAPResultListener with QueryPurchasesListener instance.
         */
        void obtainOwnedPurchaseRecord(final IapClient iapClient, final OwnedPurchasesReq ownedPurchasesReq, final IAPResultListener<OwnedPurchasesResult> ownedPurchasesResultListener);

        /**
         * Presents to bring up in-app payment pages, including:
         * </br>
         * Subscription editing page
         * Subscription management page
         *
         * @param iapClient:                      IapClient instance to call the obtainOwnedPurchaseRecord API.
         * @param startIapActivityReq:            StartIapActivityReq instance.
         * @param startIapActivityResultListener: IAPResultListener with StartIapActivityResult instance.
         */
        void startIapActivity(final IapClient iapClient, final StartIapActivityReq startIapActivityReq, final IAPResultListener<StartIapActivityResult> startIapActivityResultListener);
    }

    /**
     * IAPResultListener
     *
     * @param <T>: IAPInstances.
     */
    interface IAPResultListener<T> {
        /**
         * Presents the success scenario, IAP Result instance is returned.
         *
         * @param result: IAP Result instance.
         */
        void onSuccess(T result);

        /**
         * Presents the failure scenario, Exception instance is returned.
         *
         * @param exception: Exception instance.
         */
        void onFail(Exception exception);
    }
}

