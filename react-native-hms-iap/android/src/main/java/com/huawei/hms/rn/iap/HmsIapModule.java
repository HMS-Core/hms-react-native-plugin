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

package com.huawei.hms.rn.iap;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.iap.Iap;
import com.huawei.hms.iap.IapApiException;
import com.huawei.hms.iap.IapClient;
import com.huawei.hms.iap.entity.ConsumeOwnedPurchaseReq;
import com.huawei.hms.iap.entity.ConsumeOwnedPurchaseResult;
import com.huawei.hms.iap.entity.IsEnvReadyResult;
import com.huawei.hms.iap.entity.IsSandboxActivatedResult;
import com.huawei.hms.iap.entity.OrderStatusCode;
import com.huawei.hms.iap.entity.OwnedPurchasesReq;
import com.huawei.hms.iap.entity.OwnedPurchasesResult;
import com.huawei.hms.iap.entity.ProductInfoReq;
import com.huawei.hms.iap.entity.ProductInfoResult;
import com.huawei.hms.iap.entity.PurchaseIntentReq;
import com.huawei.hms.iap.entity.PurchaseIntentResult;
import com.huawei.hms.iap.entity.PurchaseResultInfo;
import com.huawei.hms.iap.entity.StartIapActivityReq;
import com.huawei.hms.iap.entity.StartIapActivityResult;
import com.huawei.hms.rn.iap.client.Constants;
import com.huawei.hms.rn.iap.client.ExceptionHandler;
import com.huawei.hms.rn.iap.client.IapClientReqContract;
import com.huawei.hms.rn.iap.client.IapClientReqHelper;
import com.huawei.hms.rn.iap.client.IapClientReqPresenter;

import java.util.Map;

import static com.huawei.hms.rn.iap.client.Constants.CONSTANTS;
import static com.huawei.hms.rn.iap.client.Constants.REQ_CODE_PURCHASE_INTENT;
import static com.huawei.hms.rn.iap.client.IapClientReqHelper.toIAPObject;
import static com.huawei.hms.rn.iap.client.MapUtil.getKeyByValue;
import static com.huawei.hms.rn.iap.client.MapUtil.toWritableMap;

/**
 * IapModule class is the tool class of {@link IapClient}.
 *
 * @since v.5.0.0
 */
public class HmsIapModule extends ReactContextBaseJavaModule implements IapClientReqContract.View {

    private final String TAG = HmsIapModule.class.getSimpleName();
    private IapClient iapClient;
    private IapClientReqContract.Presenter iapClientPresenter;

    private Promise mPickerPromise;
    private final ActivityEventListener activityEventListener = new ActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
            Log.i(TAG, "onActivityResult, requestCode=" + requestCode + ", resultCode=" + resultCode);
            if (mPickerPromise != null) {
                if (requestCode == Constants.REQ_CODE_PURCHASE_INTENT) {
                    if (intent == null) {
                        Log.e(TAG, "intent is null");
                        return;
                    }
                    PurchaseResultInfo purchaseIntentResult = Iap.getIapClient(getActivity()).parsePurchaseResultInfoFromIntent(intent);
                    WritableMap purchaseIntentResultMap = toWritableMap(purchaseIntentResult);
                    switch (purchaseIntentResult.getReturnCode()) {
                        case OrderStatusCode.ORDER_STATE_CANCEL:
                            String message = getKeyByValue(CONSTANTS, OrderStatusCode.ORDER_STATE_CANCEL);
                            Log.i(TAG, message);
                            mPickerPromise.resolve(purchaseIntentResultMap);
                            break;
                        case OrderStatusCode.ORDER_STATE_FAILED:
                            message = getKeyByValue(CONSTANTS, OrderStatusCode.ORDER_STATE_FAILED);
                            Log.i(TAG, message);
                            mPickerPromise.resolve(purchaseIntentResultMap);
                            break;
                        case OrderStatusCode.ORDER_PRODUCT_OWNED:
                            message = getKeyByValue(CONSTANTS, OrderStatusCode.ORDER_PRODUCT_OWNED);
                            Log.i(TAG, message);
                            mPickerPromise.resolve(purchaseIntentResultMap);
                            break;
                        case OrderStatusCode.ORDER_STATE_SUCCESS:
                            message = getKeyByValue(CONSTANTS, OrderStatusCode.ORDER_STATE_SUCCESS);
                            Log.i(TAG, message);
                            mPickerPromise.resolve(purchaseIntentResultMap);
                            break;
                        default:
                            message = "Order unknown error.";
                            Log.i(TAG, message);
                            mPickerPromise.resolve(purchaseIntentResultMap);
                            break;
                    }
                }
                mPickerPromise = null;
            }
        }

        @Override
        public void onNewIntent(Intent intent) {
            Log.d(TAG, "onNewIntent");
        }
    };

    HmsIapModule(ReactApplicationContext context) {
        super(context);
        initializeIapClient(context);
    }

    private void initializeIapClient(ReactApplicationContext context) {
        iapClient = Iap.getIapClient(context);

        //Initialize iapClientPresenter
        iapClientPresenter = new IapClientReqPresenter();
        context.addActivityEventListener(activityEventListener);
    }

    /**
     * Here we will call this IapModule so that
     * we can access it through
     * React.NativeModules.IapModule in RN Side.
     *
     * @return name
     */
    @NonNull
    @Override
    public String getName() {
        return TAG;
    }

    /**
     * getConstants returns the constant values
     * exposed to RN Side.
     *
     * @return constants
     */
    @Override
    public Map<String, Object> getConstants() {
        return CONSTANTS;
    }

    /**
     * Checks whether the currently signed-in HUAWEI ID
     * is located in a country or region where HUAWEI IAP is available.
     *
     * @param promise: In the success scenario, {@link IsEnvReadyResult} instance is returned , or {@link IapApiException} is returned in the failure scenario.
     */
    @ReactMethod
    public void isEnvironmentReady(final Promise promise) {
        iapClientPresenter.isEnvironmentReady(iapClient, new IapReqHelper(promise));
    }

    /**
     * Checks whether the sign-in HUAWEI ID and app APK version meets the requirements
     * of the sandbox testing.
     *
     * @param promise: In the success scenario, {@link IsSandboxActivatedResult} instance is returned , or {@link IapApiException} is returned in the failure scenario.
     */
    @ReactMethod
    public void isSandboxActivated(final Promise promise) {
        iapClientPresenter.isSandboxActivated(iapClient, new IapReqHelper(promise));
    }

    /**
     * Queries information about all purchased in-app products, including consumables, non-consumables, and auto-renewable subscriptions.
     * </br>
     * If the information about consumables is returned, the consumables might not be delivered due to some exceptions. In this case, your app needs to check whether the consumables are delivered. If not, the app needs to deliver them and calls the consumeOwnedPurchase API to consume them.
     * If the information about non-consumables is returned, the non-consumables do not need to be consumed.
     * If the information about subscriptions is returned, all existing subscription relationships of the user in the app are returned. The subscription relationships are as follows:
     * Renewal (normal use and normal renewal in the next period)
     * Expiring (expiration instead of renewal when the next period starts)
     * Expired (The subscription is unavailable but can still be found in the subscription history.)
     *
     * @param ownedPurchasesRequestMap: ReadableMap instance to get {@link OwnedPurchasesReq} object.
     * @param promise:                  In the success scenario, {@link OwnedPurchasesResult} instance is returned , or {@link IapApiException} is returned in the failure scenario.
     */
    @ReactMethod
    public void obtainOwnedPurchases(final ReadableMap ownedPurchasesRequestMap, final Promise promise) {
        OwnedPurchasesReq ownedPurchasesReq = toIAPObject(ownedPurchasesRequestMap, OwnedPurchasesReq.class);
        iapClientPresenter.obtainOwnedPurchases(iapClient, ownedPurchasesReq, new IapReqHelper(promise));
    }

    /**
     * Obtains in-app product details configured in AppGallery Connect.
     * If you use Huawei’s PMS to price in-app products, you can use this API to obtain in-app product details from the PMS to ensure that the in-app product information in your app is the same as that displayed on the checkout page of HUAWEI IAP.
     * </br>
     * Avoid obtaining in-app product information from your own server. Otherwise, price information may be inconsistent between your app and the checkout page.
     *
     * @param productInfoRequestMap: ReadableMap instance to get {@link ProductInfoReq} object that contains request information.
     * @param promise:               In the success scenario, {@link ProductInfoResult} instance is returned , or {@link IapApiException} is returned in the failure scenario.
     */
    @ReactMethod
    public void obtainProductInfo(final ReadableMap productInfoRequestMap, final Promise promise) {
        ProductInfoReq productInfoReq = toIAPObject(productInfoRequestMap, ProductInfoReq.class);
        iapClientPresenter.obtainProductInfo(iapClient, productInfoReq, new IapReqHelper(promise));
    }

    /**
     * Creates orders for PMS products, including consumables, non-consumables, and subscriptions.
     * </br>
     * After creating an in-app product in AppGallery Connect, you can call this API to open the HUAWEI IAP checkout page and display the product, price, and payment method.
     *
     * @param purchaseIntentRequestMap: ReadableMap instance to get {@link PurchaseIntentReq} object.
     * @param promise:                  {@link PurchaseResultInfo } instance returned via promise in the success scenario, {@link IapApiException} instance returned in the failure scenario.
     */
    @ReactMethod
    public void createPurchaseIntent(final ReadableMap purchaseIntentRequestMap, final Promise promise) {
        PurchaseIntentReq purchaseIntentReq = toIAPObject(purchaseIntentRequestMap, PurchaseIntentReq.class);
        iapClientPresenter.createPurchaseIntent(iapClient, purchaseIntentReq, new IapClientReqContract.IAPResultListener<PurchaseIntentResult>() {
            @Override
            public void onSuccess(PurchaseIntentResult result) {
                if (result == null) {
                    Log.e(TAG, "PurchaseIntentResult is null");
                    return;
                }
                // Store the promise to resolve/reject when picker returns data
                mPickerPromise = promise;
                IapClientReqHelper.startResolutionForResult(getActivity(), result.getStatus(), REQ_CODE_PURCHASE_INTENT);
            }

            @Override
            public void onFail(Exception exception) {
                promise.reject(TAG, exception.getLocalizedMessage());
            }
        });
    }

    /**
     * Consumes a consumable after the consumable is delivered to a user who has completed payment.
     *
     * @param consumeOwnedPurchaseRequestMap: ReadableMap instance to get {@link ConsumeOwnedPurchaseReq} object that contains request information.
     * @param promise:                        In the success scenario, {@link ConsumeOwnedPurchaseResult} instance is returned , or {@link IapApiException} is returned in the failure scenario.
     */
    @ReactMethod
    public void consumeOwnedPurchase(final ReadableMap consumeOwnedPurchaseRequestMap, final Promise promise) {
        ConsumeOwnedPurchaseReq consumeOwnedPurchaseReq = toIAPObject(consumeOwnedPurchaseRequestMap, ConsumeOwnedPurchaseReq.class);
        iapClientPresenter.consumeOwnedPurchase(iapClient, consumeOwnedPurchaseReq, new IapReqHelper(promise));
    }

    /**
     * Obtains the historical consumption information about a consumable or all subscription receipts of a subscription.
     * </br>
     * For consumables, this method returns information about products that have been delivered or consumed in the product list.
     * For non-consumables, this method does not return product information.
     * For subscriptions, this method returns all subscription receipts of the current user in this app.
     *
     * @param ownedPurchasesRequestMap: ReadableMap instance to get {@link OwnedPurchasesResult} object that contains request information.
     * @param promise:                  In the success scenario, {@link ProductInfoResult} instance is returned , or {@link IapApiException} is returned in the failure scenario.
     */
    @ReactMethod
    public void obtainOwnedPurchaseRecord(final ReadableMap ownedPurchasesRequestMap, final Promise promise) {
        OwnedPurchasesReq ownedPurchasesReq = toIAPObject(ownedPurchasesRequestMap, OwnedPurchasesReq.class);
        iapClientPresenter.obtainOwnedPurchaseRecord(iapClient, ownedPurchasesReq, new IapReqHelper(promise));
    }

    /**
     * Brings up in-app payment pages, including:
     * </br>
     * Subscription editing page
     * Subscription management page
     *
     * @param startIapActivityReqMap: ReadableMap instance to get {@link StartIapActivityReq} object that contains request information.
     * @param promise:                In the success scenario, starts activity with the StartIapActivityResult instance, or {@link IapApiException} is returned in the failure scenario.
     */
    @ReactMethod
    public void startIapActivity(final ReadableMap startIapActivityReqMap, final Promise promise) {
        StartIapActivityReq startIapActivityReq = toIAPObject(startIapActivityReqMap, StartIapActivityReq.class);
        iapClientPresenter.startIapActivity(iapClient, startIapActivityReq, new IapClientReqContract.IAPResultListener<StartIapActivityResult>() {
            @Override
            public void onSuccess(StartIapActivityResult result) {
                result.startActivity(getActivity());
                promise.resolve(null);
            }

            @Override
            public void onFail(Exception exception) {
                ExceptionHandler.handle(exception, promise);
            }
        });

    }

    @Override
    public Activity getActivity() {
        return getCurrentActivity();
    }

    /* Private Inner Class */

    /**
     * IapReqHelper static nested class is a helper class for reaching {@link IapClientReqContract.IAPResultListener}.
     */
    private static final class IapReqHelper implements IapClientReqContract.IAPResultListener {

        private Promise promise;

        IapReqHelper(final Promise promise) {
            this.promise = promise;
        }

        @Override
        public void onSuccess(Object result) {
            promise.resolve(toWritableMap(result));
        }

        @Override
        public void onFail(Exception exception) {
            ExceptionHandler.handle(exception, promise);
        }
    }
}

