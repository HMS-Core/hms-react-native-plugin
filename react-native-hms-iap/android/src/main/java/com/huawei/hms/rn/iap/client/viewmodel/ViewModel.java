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

import android.content.Context;

import com.facebook.react.bridge.Promise;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.iap.IapClient;
import com.huawei.hms.iap.entity.ConsumeOwnedPurchaseReq;
import com.huawei.hms.iap.entity.ConsumeOwnedPurchaseResult;
import com.huawei.hms.iap.entity.IsEnvReadyResult;
import com.huawei.hms.iap.entity.IsSandboxActivatedReq;
import com.huawei.hms.iap.entity.IsSandboxActivatedResult;
import com.huawei.hms.iap.entity.OwnedPurchasesReq;
import com.huawei.hms.iap.entity.OwnedPurchasesResult;
import com.huawei.hms.iap.entity.ProductInfoReq;
import com.huawei.hms.iap.entity.ProductInfoResult;
import com.huawei.hms.iap.entity.PurchaseIntentReq;
import com.huawei.hms.iap.entity.PurchaseIntentResult;
import com.huawei.hms.iap.entity.StartIapActivityReq;
import com.huawei.hms.iap.entity.StartIapActivityResult;
import com.huawei.hms.rn.iap.HmsIapModule;
import com.huawei.hms.rn.iap.client.logger.HMSLogger;

import java.lang.ref.WeakReference;

import javax.annotation.Nullable;

/**
 * IapClientReqPresenter works as a mediator between {@link IapClient} and {@link HmsIapModule}.
 * <p>
 * It fetches data from the {@link IapClient}, format the data and return to the {@link HmsIapModule}.
 *
 * @since v.5.0.0
 */

public class ViewModel implements Service.Presenter {
    //Context Variable
    private WeakReference<Context> weakContext;

    public ViewModel(@Nullable Context context) {
        this.weakContext = new WeakReference<>(context);
    }

    /**
     * isEnvironmentReady Checks whether the currently signed-in HUAWEI ID
     * is located in a country or region where HUAWEI IAP is available.
     *
     * @param mClient:                  IapClient instance to call the isEnvReady API.
     * @param isEnvReadyResultListener: IAPResultListener with IsEnvReadyResult instance.
     */
    public void isEnvironmentReady(final IapClient mClient, final Service.IAPResultListener<IsEnvReadyResult> isEnvReadyResultListener) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("isEnvironmentReady");
        Task<IsEnvReadyResult> task = mClient.isEnvReady();
        task.addOnSuccessListener(result -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("isEnvironmentReady");
            if (result != null) {
                isEnvReadyResultListener.onSuccess(result);
            }
        }).addOnFailureListener(exception -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("isEnvironmentReady", exception.getLocalizedMessage());
            isEnvReadyResultListener.onFail(exception);
        });
    }

    /**
     * Checks whether the sign-in HUAWEI ID and app APK version meets the requirements
     * of the sandbox testing.
     *
     * @param mClient:                          IapClient instance to call the isSandboxActivated API.
     * @param isSandboxActivatedResultListener: IAPResultListener with IsSandboxActivatedListener instance.
     */
    public void isSandboxActivated(final IapClient mClient, final Service.IAPResultListener<IsSandboxActivatedResult> isSandboxActivatedResultListener) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("isSandboxActivated");
        Task<IsSandboxActivatedResult> task = mClient.isSandboxActivated(new IsSandboxActivatedReq());
        task.addOnSuccessListener(result -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("isSandboxActivated");
            if (result != null) {
                isSandboxActivatedResultListener.onSuccess(result);
            }
        }).addOnFailureListener(exception -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("isSandboxActivated",
                    exception.getLocalizedMessage());
            isSandboxActivatedResultListener.onFail(exception);
        });
    }

    /**
     * Queries information about all purchased in-app products, including consumables, non-consumables,
     * and auto-renewable subscriptions.</br>
     * If the information about consumables is returned,
     * the consumables might not be delivered due to some exceptions.
     * In this case, your app needs to check whether the consumables are delivered.
     * If not, the app needs to deliver them and calls the consumeOwnedPurchase API to consume them.
     * If the information about non-consumables is returned,
     * the non-consumables do not need to be consumed.
     * If the information about subscriptions is returned,
     * all existing subscription relationships of the user in the app are returned.
     * The subscription relationships are as follows:
     * Renewal (normal use and normal renewal in the next period)
     * Expiring (expiration instead of renewal when the next period starts)
     * Expired (The subscription is unavailable but can still be found in the subscription history.)
     *
     * @param mClient:                      IapClient instance to call the obtainOwnedPurchases API.
     * @param ownedPurchasesReq:            OwnedPurchasesReq object.
     * @param ownedPurchasesResultListener: IAPResultListener with QueryPurchasesListener instance.
     */
    public void obtainOwnedPurchases(final IapClient mClient, final OwnedPurchasesReq ownedPurchasesReq, final Service.IAPResultListener<OwnedPurchasesResult> ownedPurchasesResultListener) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("obtainOwnedPurchases");
        Task<OwnedPurchasesResult> task = mClient.obtainOwnedPurchases(ownedPurchasesReq);
        task.addOnSuccessListener(result -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("obtainOwnedPurchases");
            if (result != null) {
                ownedPurchasesResultListener.onSuccess(result);
            }
        }).addOnFailureListener(exception -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("obtainOwnedPurchases",
                    exception.getLocalizedMessage());
            ownedPurchasesResultListener.onFail(exception);
        });
    }

    /**
     * Obtains in-app product details configured in AppGallery Connect.
     * If you use Huawei’s PMS to price in-app products, you can use this API to obtain
     * in-app product details from the PMS to ensure that the in-app product information
     * in your app is the same as that displayed on the checkout page of HUAWEI IAP.
     * </br>
     * Avoid obtaining in-app product information from your own server. Otherwise,
     * price information may be inconsistent between your app and the checkout page.
     *
     * @param iapClient:IapClient           instance to call the obtainOwnedPurchases API.
     * @param productInfoReq:ProductInfoReq object.
     * @param productInfoResultListener:    IAPResultListener with ProductInfoResultListener instance.
     */
    public void obtainProductInfo(final IapClient iapClient, final ProductInfoReq productInfoReq, final Service.IAPResultListener<ProductInfoResult> productInfoResultListener) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("obtainProductInfo");
        Task<ProductInfoResult> task = iapClient.obtainProductInfo(productInfoReq);
        task.addOnSuccessListener(result -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("obtainProductInfo");
            if (result != null) {
                productInfoResultListener.onSuccess(result);
            }
        }).addOnFailureListener(exception -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("obtainProductInfo",
                    exception.getLocalizedMessage());
            productInfoResultListener.onFail(exception);
        });
    }

    /**
     * Creates orders for PMS products, including consumables, non-consumables, and subscriptions.
     * </br>
     * After creating an in-app product in AppGallery Connect,
     * you can call this API to open the HUAWEI IAP checkout page and display the product,
     * price, and payment method.
     *
     * @param mClient:                      IapClient instance to call the obtainOwnedPurchases API.
     * @param purchaseIntentReq:            PurchaseIntentReq object.
     * @param purchaseIntentResultListener: IAPResultListener with PurchaseIntentResultListener instance.
     */
    public void createPurchaseIntent(final IapClient mClient, final PurchaseIntentReq purchaseIntentReq, final Service.IAPResultListener<PurchaseIntentResult> purchaseIntentResultListener) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("createPurchaseIntent");
        Task<PurchaseIntentResult> task = mClient.createPurchaseIntent(purchaseIntentReq);
        task.addOnSuccessListener(result -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("createPurchaseIntent");
            if (result != null) {
                purchaseIntentResultListener.onSuccess(result);
            }
        }).addOnFailureListener(exception -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("createPurchaseIntent",
                    exception.getLocalizedMessage());
            purchaseIntentResultListener.onFail(exception);
        });
    }

    /**
     * Consumes a consumable after the consumable is delivered to a user who has completed payment.
     *
     * @param iapClient:                          IapClient instance to call the consumeOwnedPurchase API.
     * @param consumeOwnedPurchaseReq:            ConsumeOwnedPurchaseReq instance which contains request information.
     * @param consumeOwnedPurchaseResultListener: IAPResultListener with ConsumeOwnedPurchaseResultListener instance.
     */
    public void consumeOwnedPurchase(final IapClient iapClient, final ConsumeOwnedPurchaseReq consumeOwnedPurchaseReq, final Service.IAPResultListener<ConsumeOwnedPurchaseResult> consumeOwnedPurchaseResultListener) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("consumeOwnedPurchase");
        Task<ConsumeOwnedPurchaseResult> task = iapClient.consumeOwnedPurchase(consumeOwnedPurchaseReq);
        task.addOnSuccessListener(result -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("consumeOwnedPurchase");
            if (result != null) {
                consumeOwnedPurchaseResultListener.onSuccess(result);
            }
        }).addOnFailureListener(exception -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("consumeOwnedPurchase",
                    exception.getLocalizedMessage());
            consumeOwnedPurchaseResultListener.onFail(exception);
        });
    }

    /**
     * Obtains the historical consumption information about a consumable
     * or all subscription receipts of a subscription.
     * </br>
     * For consumables, this method returns information about products that have been delivered
     * or consumed in the product list.
     * For non-consumables, this method does not return product information.
     * For subscriptions, this method returns all subscription receipts of the current user in this app.
     *
     * @param iapClient:                    IapClient instance fto call the obtainOwnedPurchaseRecord API.
     * @param ownedPurchasesReq:            OwnedPurchasesReq instance.
     * @param ownedPurchasesResultListener: IAPResultListener with QueryPurchasesListener instance.
     */
    public void obtainOwnedPurchaseRecord(final IapClient iapClient, final OwnedPurchasesReq ownedPurchasesReq, final Service.IAPResultListener<OwnedPurchasesResult> ownedPurchasesResultListener) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("obtainOwnedPurchaseRecord");
        Task<OwnedPurchasesResult> task = iapClient.obtainOwnedPurchaseRecord(ownedPurchasesReq);
        task.addOnSuccessListener(result -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("obtainOwnedPurchaseRecord");
            if (result != null) {
                ownedPurchasesResultListener.onSuccess(result);
            }
        }).addOnFailureListener(exception -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("obtainOwnedPurchaseRecord",
                    exception.getLocalizedMessage());
            ownedPurchasesResultListener.onFail(exception);
        });
    }

    /**
     * Brings up in-app payment pages, including:
     * </br>
     * Subscription editing page
     * Subscription management page
     *
     * @param iapClient:                      IapClient instance to call the obtainOwnedPurchaseRecord API.
     * @param startIapActivityReq:            StartIapActivityReq instance.
     * @param startIapActivityResultListener: IAPResultListener with StartIapActivityResult instance.
     */
    public void startIapActivity(final IapClient iapClient, final StartIapActivityReq startIapActivityReq, final Service.IAPResultListener<StartIapActivityResult> startIapActivityResultListener) {
        HMSLogger.getInstance(getContext()).startMethodExecutionTimer("startIapActivity");
        Task<StartIapActivityResult> task = iapClient.startIapActivity(startIapActivityReq);
        task.addOnSuccessListener(result -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("startIapActivity");
            if (result != null) {
                startIapActivityResultListener.onSuccess(result);
            }
        }).addOnFailureListener(exception -> {
            HMSLogger.getInstance(getContext()).sendSingleEvent("startIapActivity", exception.getLocalizedMessage());
            startIapActivityResultListener.onFail(exception);
        });
    }

    /**
     * Enables logging.
     *
     * @param promise: The promise value of the HmsLogger enableLogger function.
     */
    public void enableLogger(Promise promise) {
        HMSLogger.getInstance(getContext()).enableLogger();
    }

    /**
     * Disables logging.
     *
     * @param promise: The promise value of the HmsLogger disableLogger function.
     */
    public void disableLogger(Promise promise) {
        HMSLogger.getInstance(getContext()).disableLogger();
        promise.resolve(true);
    }

    /**
     * Returns Context instance.
     *
     * @return Context
     */
    private Context getContext() {
        return weakContext.get();
    }
}