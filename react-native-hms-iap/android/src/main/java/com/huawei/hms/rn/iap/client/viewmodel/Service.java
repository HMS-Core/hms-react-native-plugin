/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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
package com.huawei.hms.rn.iap.client.viewmodel;

import android.app.Activity;

import com.facebook.react.bridge.Promise;
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
 * IapClientReqPresenter defines a blueprint of {@link ViewModel} methods.
 *
 * @since v.5.0.0
 */
public interface Service {
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
        void isEnvironmentReady(IapClient mClient, IAPResultListener<IsEnvReadyResult> isEnvReadyResultListener);

        /**
         * Enables logging.
         *
         * @param promise: The promise value of the HmsLogger enableLogger function.
         */
        void enableLogger(Promise promise);

        /**
         * Enables logging.
         *
         * @param promise: The promise value of the HmsLogger disableLogger function.
         */
        void disableLogger(Promise promise);

        /**
         * isSandboxActivated presents to check whether the sign-in HUAWEI ID and app APK version meets the requirements
         * of the sandbox testing.
         *
         * @param mClient:                          IapClient instance to call the isSandboxActivated API.
         * @param isSandboxActivatedResultListener: IAPResultListener with IsSandboxActivatedListener instance.
         */
        void isSandboxActivated(IapClient mClient,
        IAPResultListener<IsSandboxActivatedResult> isSandboxActivatedResultListener);

        /**
         * Presents to query information about all purchased in-app products,
         * including consumables, non-consumables, and auto-renewable subscriptions.</br>
         *
         * @param mClient:                      IapClient instance to call the obtainOwnedPurchases API.
         * @param ownedPurchasesReq:            OwnedPurchasesReq object.
         * @param ownedPurchasesResultListener: IAPResultListener with QueryPurchasesListener instance.
         */
        void obtainOwnedPurchases(IapClient mClient, OwnedPurchasesReq ownedPurchasesReq,
        IAPResultListener<OwnedPurchasesResult> ownedPurchasesResultListener);

        /**
         * Presents to obtain in-app product details configured in AppGallery Connect.
         * If you use Huawei’s PMS to price in-app products,
         * you can use this API to obtain in-app product details from the PMS to ensure that the in-app product
         * information in your app is the same as that displayed on the checkout page of HUAWEI IAP.
         * </br>
         *
         * @param iapClient:                 IapClient instance to call the obtainOwnedPurchases API.
         * @param productInfoReq:            ProductInfoReq object.
         * @param productInfoResultListener: IAPResultListener with ProductInfoResultListener instance.
         */
        void obtainProductInfo(IapClient iapClient, ProductInfoReq productInfoReq,
        final IAPResultListener<ProductInfoResult> productInfoResultListener);

        /**
         * Presents to create orders for PMS products, including consumables, non-consumables, and subscriptions.
         *
         * @param mClient:                      IapClient instance to call the obtainOwnedPurchases API.
         * @param purchaseIntentReq:            PurchaseIntentReq object.
         * @param purchaseIntentResultListener: IAPResultListener with PurchaseIntentResultListener instance.
         */
        void createPurchaseIntent(IapClient mClient, PurchaseIntentReq purchaseIntentReq,
        IAPResultListener<PurchaseIntentResult> purchaseIntentResultListener);

        /**
         * Presents to consume a consumable after the consumable is delivered to a user who has completed payment.
         *
         * @param iapClient:                          IapClient instance to call the consumeOwnedPurchase API.
         * @param consumeOwnedPurchaseReq:            ConsumeOwnedPurchaseReq instance which contains request
         *                                            information.
         * @param consumeOwnedPurchaseResultListener: IAPResultListener with ConsumeOwnedPurchaseResultListener
         *                                            instance.
         */
        void consumeOwnedPurchase(IapClient iapClient, ConsumeOwnedPurchaseReq consumeOwnedPurchaseReq,
        IAPResultListener<ConsumeOwnedPurchaseResult> consumeOwnedPurchaseResultListener);

        /**
         * Presents to obtain the historical consumption information about a consumable
         * or all subscription receipts of a subscription.
         *
         * @param iapClient:                    IapClient instance to call the obtainOwnedPurchaseRecord API.
         * @param ownedPurchasesReq:            OwnedPurchasesReq instance.
         * @param ownedPurchasesResultListener: IAPResultListener with QueryPurchasesListener instance.
         */
        void obtainOwnedPurchaseRecord(IapClient iapClient, OwnedPurchasesReq ownedPurchasesReq,
        IAPResultListener<OwnedPurchasesResult> ownedPurchasesResultListener);

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
        void startIapActivity(IapClient iapClient, StartIapActivityReq startIapActivityReq,
        IAPResultListener<StartIapActivityResult> startIapActivityResultListener);
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