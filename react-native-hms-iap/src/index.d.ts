/*
 *   Copyright 2020-2024. Huawei Technologies Co., Ltd. All rights reserved.
 *   
 *   Licensed under the Apache License, Version 2.0 (the "License")
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

declare module "@hmscore/react-native-hms-iap" {

    /**
     * Status object that contains the task processing result. status object can be seen only user have already signed-in HUAWEI ID.
     */
    interface Status { 

         /**
         * Status code. 0: success, 1: failure, 404: no resource found, 500: internal error
         */
        statusCode: string;

         /**
         * Status description.
         */
        statusMessage : string;
    }

     /**
     * Information returned when the isSandboxActivated API is successfully called.
     */
    interface IsSandboxActivatedResult {

         /**
         * Result code description.
         */
        errMsg : string;

        /**
         * 	Indicates whether the APK version meets the requirements of the sandbox testing.
         */
        isSandboxApk : boolean;

        /**
         * 	Indicates whether a sandbox testing ID is used.
         */
        isSandboxUser : boolean;

        /**
         * Result code. 0: success
         */
        returnCode : number;

        /**
         * Information about the app version that is last released on HUAWEI AppGallery.
         */
        versionFrMarket : string;

        /**
         * App version information.
         */
        versionInApk : string;

        /**
         * Status object that contains the task processing result.
         */
        status : Status;
    }

    /**
     * Information returned when the isEnvironmentReady API is successfully called.
    */
    interface IsEnvReadyResult { 

        /**
         * Result code. 0: The country or region of the signed-in HUAWEI ID supports HUAWEI IAP.
         */
        returnCode : number;

        /**
         * Status object that contains the task processing result. status object can be seen only user have already signed-in HUAWEI ID.
         */
        status : Status;

        /**
         * 	Account type. 1: AppTouch user account. Other values: Huawei account
         */
        accountFlag : number;

        /**
         * The carrier ID.
         */
        carrierId : string;

        /**
         * The code of the country or region where the currently signed-in account is located.
         */
        country : string;
    }

    /**
     * Request information of the obtainProductInfo API.
     */
    interface ProductInfoReq{

        /**
        * 	Type of a product to be queried. 0: consumable, 1: non-consumable, 2: auto-renewable subscription
        */
        priceType: number;

        /**
        * ID array of products to be queried. Each product ID must exist and be unique in the current app. The product ID is the same as that you set when configuring product information in AppGallery Connect.
        */
        skuIds : string;
    }

    /**
     * Request information of the createPurchaseIntent API.
     */
    interface PurchaseIntentReq{

        /**
         * Product type. 0: consumable, 1: non-consumable, 2: auto-renewable subscription
         */
        priceType: number;

         /**
         * 	ID of a product to be paid. The product ID is the same as that you set when configuring product information in AppGallery Connect.
         */
        productId: string;

         /**
         * Information stored on the merchant side. If the parameter is set to a value, the value will be returned in the callback result to the app after successful payment.
         * Note: The value length of this parameter is within (0, 128).
         */
        developerPayload: string;

         /**
         * Obtains extended fields.
         */
        reservedInfor: string;

         /**
         * Signature algorithm, which is optional. If a signature algorithm is passed, IAP will use it to sign consumption result data.
         * Currently, the value can only be SIGNATURE_ALGORITHM_SHA256WITHRSA_PSS, which indicates the SHA256WithRSA/PSS algorithm.
         */
        signatureAlgorithm: string;
    }

    /**
     * Request information of the obtainOwnedPurchases or obtainOwnedPurchaseRecord API.
     */
    interface OwnedPurchasesReq{

        /**
         * Type of a product to be queried. 0: consumable, 1: non-consumable, 2: auto-renewable subscription.
         */
        priceType: number;

        /**
         * Data location flag for query in pagination mode.
         * This parameter is optional for the first query. After the API is called, the returned information contains this parameter.
         * If query in pagination mode is required for the next API call, this parameter can be set for the second query.
         */
        continuationToken: string;

        /**
         * Signature algorithm, which is optional. If a signature algorithm is passed, IAP will use it to sign consumption result data.
         * Currently, the value can only be SIGNATURE_ALGORITHM_SHA256WITHRSA_PSS, which indicates the SHA256WithRSA/PSS algorithm.
         */
        signatureAlgorithm: string;
    }

    /**
     * Request information of the consumeOwnedPurchase API.
     */
    interface ConsumeOwnedPurchaseReq{

        /**
         * Purchase token, which is generated by the Huawei IAP server during payment and returned to the app through InAppPurchaseData.
         *  The app passes this parameter for the Huawei IAP server to update the order status and then deliver the product.
         */
        purchaseToken: string;

        /**
         * Custom challenge, which uniquely identifies a consumption request.
         *  After the consumption is successful, the challenge is recorded in the purchase information and returned.
         */
        developerChallenge: string;

        /**
         * Signature algorithm, which is optional. If a signature algorithm is passed, IAP will use it to sign consumption result data.
         * Currently, the value can only be SIGNATURE_ALGORITHM_SHA256WITHRSA_PSS, which indicates the SHA256WithRSA/PSS algorithm.
         */
        signatureAlgorithm: string;
    }


    /**
     * Information returned when the obtainOwnedPurchases or obtainOwnedPurchaseRecord API is successfully called.
     */
    interface OwnedPurchasesResult {

        /**
         * 	Data location flag.
         * If a user has a large number of products and the response contains continuationToken, the app must initiate another call on the current method and pass continuationToken currently received. 
         * If product query is still incomplete, the app needs to call the API again until no continuationToken is returned, indicating that all products are returned.
         */
        continuationToken: string;

        /**
         * Result code description.
         */
        errMsg: string;

        /**
         * Information about products that have been purchased but not consumed or about all existing subscription relationships of users, which is returned using the obtainOwnedPurchases method.
         * Historical consumable information or all subscription receipts, which are returned using the obtainOwnedPurchaseRecord method.
         */
        inAppPurchaseDataList: InAppPurchaseData[];

        /**
         * Signature string of each subscription relationship in the InAppPurchaseDataList array.
         */
        inAppSignature: string;

        /**
         * ID list of found products. The value is a string array.
         */
        itemList: string;

        /**
         * Result code.0: The query is successful.
         */
        returnCode: number;

        /**
         * Status object that contains the task processing result.
         */
        status: Status;

        /**
         * Subscription relationship information about a user who has performed subscription switchover. The value is a JSON string array.
         * For details about the parameters contained in each JSON string, please refer to InAppPurchaseData.
         */
        placedInappPurchaseDataList: string;

        /**
         * Signature string of each subscription relationship in the PlacedInappPurchaseDataList list.
         */
        placedInappSignatureList: string;

        /**
         * 	Signature algorithm.
         */
        signatureAlgorithm: string;
    }

    interface StartIapActivityReq {

        /**
         * Type of the screen to be redirected to.
         *   2: subscription management screen
         *   3: subscription editing screen
         */
        type : 2|3;

        /**
         * ID of a subscription.
         */
        subscribeProductId: string;
    }

    interface ConsumePurchaseData{

        /**
         * ID of an app that initiates a purchase.
         */
        applicationId: number ;

        /**
         * Indicates whether the subscription is automatically renewed. Currently, the value is always false.
         */
        autoRenewing: boolean;

        /**
         * Confirmation.
         */
        confirmed: number;

        /**
         * 	Order ID on the Huawei IAP server, which uniquely identifies a transaction and is generated by the Huawei IAP server during payment.
         */
        orderId: string;

        /**
         * Product type.
         *  0: consumable
         *  1: non-consumable
         *  2: renewable subscription
         *  3: non-renewable subscription
         */
        kind: number;

        /**
         * Software package name of the app that initiates a purchase.
         */
        packageName: string;

         /**
         * Merchant ID, which uniquely identifies a transaction and is generated by the Huawei IAP server during payment.
         */
        payOrderId: string;

         /**
         * Payment method.
         *   0: HUAWEI Points
         *   3: credit card
         *   4: Alipay
         *   6: carrier billing
         *   13: PayPal
         *   16: debit card
         *   17: WeChat Pay
         *   19: gift card
         *   20: balance
         *   21: HUAWEI Point card
         *   24: WorldPay
         *   31: HUAWEI Pay
         *   32: Ant Credit Pay
         *   200: M-Pesa
         */
        payType: string;

         /**
         * Product ID.
         */
        productId: string;

         /**
         * Product name.
         */
        productName: string;

         /**
         * Purchase timestamp from 00:00:00 on January 1, 1970 to the purchase time, in millionseconds.
         */
        purchaseTime: number;

         /**
         * Purchase time.
         */
        purchaseTimeMillis: number;

         /**
         * Purchase type.
         *   0: purchase during sandbox testing
         *   1: purchase during the promotion period (currently unsupported)
         *   This parameter is not returned during formal purchase.
         */
        purchaseType: number;

         /**
         * Order status.
         *   -1: initialized and invisible
         *   0: purchased
         *   1: canceled
         *   2: refunded
         */
        purchaseState: number;

         /**
         * Reserved information on the merchant side, which is passed by the app during payment.
         */
        developerPayload: string;

         /**
         * Purchase token, which uniquely identifies the mapping between a product and a user. 
         * It is generated by the Huawei IAP server when the payment is complete.
         */
        purchaseToken: string;

         /**
         * Challenge defined when the app initiates a consumption request, which uniquely identifies a consumption request.
         */
        developerChallenge: string;

         /**
         * Consumption status.
         *   0: not consumed
         *   1: consumed
         */
        consumptionState: number;

         /**
         * Receiving status.
         *   0: not received
         *   1: received
         *   This parameter is valid only for receiving APIs.
         *   The value is always 0. You can ignore this parameter.
         */
        acknowledged: number;

         /**
         * Currency. The value must be a currency defined in the ISO 4217 standard, for example: USD, CNY, and TRY.
         */
        currency: string;

         /**
         * Value after the actual price of a product is multiplied by 100. The actual price is accurate to two decimal places. For example, if the value of this parameter is 501, the actual product price is 5.01.
         */
        price: number;

         /**
         * Country or region code of a user service area. The value must comply with the ISO 3166 standard, for example: US, CN, and TR.
         */
        country: string;

         /**
         * Response code.
         *   0: The execution is successful.
         */
        responseCode: string;

         /**
         * Response information.
         */
        responseMessage: string;
    }

    interface ConsumeOwnedPurchaseResult{

        /**
         * ConsumePurchaseData object that contains consumption result data.
         */
        consumePurchaseData: ConsumePurchaseData; 

        /**
         * Signature string generated after consumption data is signed using a private payment key. The signature algorithm is SHA256withRSA.
         */
        dataSignature: string;

        /**
         * Result code description.
         */
        errMsg: string;

        /**
         * 	Result code.
         */
        returnCode: number;

        /**
         * Status object that contains the task processing result.
         */
        status: Status;

        /**
         * Signature algorithm.
         */
        signatureAlgorithm: string;
    }

    interface InAppPurchaseData{

        /**
         * ID of an app that initiates a purchase.
         */
        applicationId:  number;

         /**
         * 	Indicates whether the subscription is automatically renewed. Currently, the value is always false.
         */
        autoRenewing: boolean;

         /**
         * Order ID on the Huawei IAP server, which uniquely identifies a transaction and is generated by the Huawei IAP server during payment.
         */
        orderId: string;

         /**
         * Software package name of the app that initiates a purchase.
         */
        packageName: string;

         /**
         * Product ID.
         */
        productId: string;

         /**
         * Product name.
         */
        productName: string;

         /**
         * Purchase timestamp, which is the number of milliseconds from 00:00:00 on January 1, 1970 to the purchase time.
         */
        purchaseTime: number;

         /**
         * Order status.
         *   -1: initialized and invisible
         *   0: purchased
         *   1: canceled
         *   2: refunded
         */
        purchaseState: number;

         /**
         * Reserved information on the merchant side, which is passed by the app during payment.
         */
        developerPayload: string;

         /**
         * Reserved information on the merchant side, which is passed by the app during payment.
         */
        purchaseToken: string;

         /**
         *  Purchase type.
         *   0: in the sandbox
         *   1: in the promotion period (currently unsupported). This parameter is not returned during formal purchase.
         */
        purchaseType: number;

         /**
         * Currency. The value must be a currency defined in the ISO 4217 standard. Example: USD, CNY, and TRY
         */
        currency: string;

         /**
         * Value after the actual price of a product is multiplied by 100. The actual price is accurate to two decimal places. 
         * For example, if the value of this parameter is 501, the actual product price is 5.01.
         */
        price: number;

         /**
         * Country or region code of a user service area. The value must comply with the ISO 3166 standard. Example: US, CN, and TR
         */
        country: string;

         /**
         * Order ID generated by the Huawei IAP server during fee deduction on the previous renewal.
         */
        lastOrderId: string;

         /**
         * ID of the subscription group to which a subscription belongs.
         */
        productGroup: string;

         /**
         * First fee deduction timestamp, which is the number of milliseconds since 00:00:00 on January 1, 1970.
         */
        oriPurchaseTime: number;

         /**
         * Subscription ID.
         */
        subscriptionId: string;

         /**
         * Purchase quantity.
         */
        quantity: number;

         /**
         * Days of a paid subscription, excluding the free trial period and promotion period.
         */
        daysLasted: number;

         /**
         * Days of a paid subscription, excluding the free trial period and promotion period.
         */
        numOfPeriods: number;

         /**
         * Number of successful renewal periods with promotion.
         */
        numOfDiscounts: number;

         /**
         * Subscription expiration timestamp. For an automatic renewal receipt where the fee has been deducted successfully, this parameter indicates the renewal date or expiration date.
         *  If the value is a past time for the latest receipt of a product, the subscription has expired.
         */
        expirationDate: number;

         /**
         * A timestamp when a grace period ends.
         */
        graceExpirationTime: number;

         /**
         * Reason why a subscription expires.
         *   1: canceled by a user
         *   2: product being unavailable
         *   3: abnormal user signing information
         *   4: billing error
         *   5: price increase disagreed with by a user
         *   6: unknown error
         */
        expirationIntent: number;

         /**
         * Indicates whether the system still tries to renew an expired subscription.
         */
        retryFlag: number;

         /**
         * Indicates whether a subscription is in the renewal period with promotion.
         */
        introductoryFlag: number;

         /**
         * Indicates whether a subscription is in the free trial period.
         */
        trialFlag: number;

         /**
         * Subscription cancellation timestamp. 
         * This parameter has a value when a user makes a complaint and cancels a subscription through the customer service, or when a user performs subscription upgrade or cross-grade that immediately takes effect and cancels the previous receipt of the original subscription.
         */
        cancelTime: number;

         /**
         * Reason why a subscription is canceled.
         *   0: others. For example, a user mistakenly purchases a subscription and has to cancel it.
         *   1: A user encounters a problem within the app and cancels the subscription.
         *   2: A user performs subscription upgrade or cross-grade.
         */
        cancelReason: number;

         /**
         * App information, which is reserved.
         */
        appInfo: string;

         /**
         * Indicates whether a user has disabled the subscription notification function.
         */
        notifyClosed: number;

         /**
         * Renewal status.
         *   1: The subscription renewal is normal.
         *   0: The user cancels subscription renewal.
         */
        renewStatus: number;

         /**
         * User opinion on the price increase of a product.
         *   1: The user has agreed to the price increase.
         *   0: The user does not take any action. After the subscription expires, it becomes invalid.
         */
        priceConsentStatus: number;

         /**
         * Renewal price. It is provided as a reference for users when the getPriceConsentStatus() parameter is returned.
         */
        renewPrice: number;

         /**
         * true: A user has been charged for an in-app product, the in-app product has not expired, and no refund has been made. In this case, you can provide services for the user.
         * false: The purchase of a product is not finished, the product has expired, or a refund has been made for the product after the subscription is valid.
         */
        subIsvalid: boolean;

         /**
         * Number of days for retaining a subscription relationship after the subscription is canceled.
         */
        cancelledSubKeepDays: number;

         /**
         * Product type.
         *   0: consumable
         *   1: non-consumable
         *   2: renewable subscription
         *   3: non-renewable subscription
         */
        kind: number;

         /**
         * Challenge defined when the app initiates a consumption request, which uniquely identifies a consumption request.
         *  This parameter is valid only for one-off products.
         */
        developerChallenge: string;

         /**
         * Consumption status, which is valid only for one-off products.
         * 0: not consumed
         * 1: consumed
         */
        consumptionState: number;

         /**
         * Merchant ID, which uniquely identifies a transaction and is generated by the Huawei IAP server during payment.
         */
        payOrderId: string;

         /**
         * Payment method.
         *       0: HUAWEI Points
         *       3: credit card
         *       4: Alipay
         *       6: carrier billing
         *       13: PayPal
         *       16: debit card
         *       17: WeChat Pay
         *       19: gift card
         *       20: balance
         *       21: HUAWEI Point card
         *       24: WorldPay
         *       31: HUAWEI Pay
         *       32: Ant Credit Pay
         *       200: M-Pesa
         */
        payType: string;

         /**
         * Indicates whether to postpone the settlement date. The value 1 indicates that the settlement date is postponed.
         */
        deferFlag: number;

         /**
         * Original subscription ID. If the parameter is set to a value, the current subscription is switched from another one. 
         * The value can be associated with the original subscription.
         */
        oriSubscriptionId: string;

         /**
         * Subscription cancellation initiator.
         *   0: user
         *   1: developer
         *   2: Huawei
         */
        cancelWay: number;

         /**
         * Subscription cancellation time in UTC.
         */
        cancellationTime: number;

         /**
         * Time when a subscription is resumed.
         */
        resumeTime: number;

         /**
         * Account type.
         *   1: AppTouch ID
         *   Other values: HUAWEI ID
         */
        accountFlag: number;
    }

    interface PurchaseResultInfo{

        /**
         * Result code.
         * 0: The payment is successful.
         * Other values: The payment failed. For details about the result codes, please refer to Troubleshooting and Common Result Codes.
         */
        returnCode: number;

        /**
         * InAppPurchaseData object that contains purchase order details.
         * For details about the parameters contained in the string, please refer to InAppPurchaseData.
         */
        inAppPurchaseData:  InAppPurchaseData;

        /**
         * Signature string generated after purchase data is signed using a private payment key. The signature algorithm is SHA256withRSA. 
         * After the payment is successful, the app needs to perform signature verification on the string of InAppPurchaseData using the payment public key.
         */
        inAppDataSignature:  string;

        /**
         * Result code description.
         */
        errMsg:  string;

        /**
         * Signature algorithm.
         */
        signatureAlgorithm:  string;
    }

    interface ProductInfo{

        /**
         * Product ID.
         */
        productId : string;

        /**
         * Product type.
         *   0: consumable
         *   1: non-consumable
         *   2: auto-renewable subscription
         */
        priceType: number;

        /**
         * Displayed price of a product, including the currency symbol and actual price of the product. 
         * The value is in the Currency symbolPrice format, for example, ¥0.15. The price includes the tax.
         */
        price: string;

        /**
         * Product price in micro unit, which equals to the actual product price multiplied by 1,000,000.
         * For example, if the actual price of a product is US$1.99, the product price in micro unit is 1,990,000 (1.99 x 1,000,000).
         */
        microsPrice: number;

        /**
         * Original price of a product, including the currency symbol and actual price of the product. 
         * The value is in the Currency symbolPrice format, for example, ¥0.15. The price includes the tax.
         */
        originalLocalPrice: string;

        /**
         * Original price of a product in micro unit, which equals to the original product price multiplied by 1,000,000.
         */
        originalMicroPrice: number;

        /**
         * Currency used to pay for a product. The value must comply with the ISO 4217 standard.
         */
        currency: string;

        /**
         * Product name, which is set during product information configuration.
         */
        productName: string;

        /**
         * Description of a product, which is set during product information configuration.
         */
        productDesc: string;

        /**
         * Promotional subscription price in micro unit, which equals to the actual promotional subscription price multiplied by 1,000,000.
         */
        subSpecialPriceMicros : number; 

        /**
         * Number of promotion periods of a subscription. 
         */
        subSpecialPeriodCycles: number;

        /**
         * Level of a subscription in its subscription group.
         */
        subProductLevel: number;

        /**
         * Product status.
         * 0: valid.
         * 1: deleted. Products in this state cannot be renewed or subscribed to.
         * 6: removed. New subscriptions are not allowed, but users who have subscribed to products can still renew them.
         */
        status: number;

        /**
         * Checks whether a user has enjoyed a promotion in a subscription group.
         */
        offerUsedStatus: number;

        /**
         * Free trial period of a subscription. It is set when you set the promotional price of a subscription in AppGallery Connect.
         */
        subFreeTrialPeriod: string;

        /**
         * ID of the subscription group to which a subscription belongs.
         */
        subGroupId: string;

        /**
         * Description of the subscription group to which a subscription belongs.
         */
        subGroupTitle: string;

        /**
         * Promotion period unit of a subscription, which complies with the ISO 8601 standard.
         */
        subSpecialPeriod: string;

        /**
         * Unit of a subscription period, which complies with the ISO 8601 standard.
         */
        subPeriod: string;

        /**
         * Promotional price of a subscription, including the currency symbol and actual price.
         */
        subSpecialPrice: string;
    }

    interface ProductInfoResult{

        /**
         * Result code description.
         */
        errMsg: string;

        /**
         * Array of found products.
         */
        productInfoList: ProductInfo[];

        /**
         * Result code.
         * 0: The query is successful.
         */
        returnCode: number;

        /**
         * Status object that contains the task processing result.
         */
        status: Status;

        /**
         * Obtains extended fields.
         */
        ReservedInfor:string;
    }

    export abstract class HmsIapModuleEnum {

        /**
         * Success.
         */
        static readonly ORDER_STATE_SUCCESS = 0; 
        
        /**
         * Common failure result code.
         */
        static readonly ORDER_STATE_FAILED = -1;  
        
        /**
         * Default Code.
         */
        static readonly ORDER_STATE_DEFAULT_CODE = 1;   
        
        /**
         * The payment is canceled by the user.
         */
        static readonly ORDER_STATE_CANCEL = 60000; 
        
        /**
         * Parameter error (including no parameter).
         */
        static readonly ORDER_STATE_PARAM_ERROR = 60001;   
        
        /**
         * IAP could not be activated.
         */
        static readonly ORDER_STATE_IAP_NOT_ACTIVATED = 60002;  
        
        /**
         * Incorrect product information.
         */
        static readonly ORDER_STATE_PRODUCT_INVALID = 60003;  
        
        /**
         * Too frequent API calls.
         */
        static readonly ORDER_STATE_CALLS_FREQUENT = 60004; 
        
        /**
         * Network connection exception.
         */
        static readonly ORDER_STATE_NET_ERROR = 60005;    
        
        /**
         * The found product type is inconsistent with that defined in the PMS
         */
        static readonly ORDER_STATE_PMS_TYPE_NOT_MATCH = 60006; 
        
        /**
         * The app to which the product belongs is not released in a specified location.
         */
        static readonly ORDER_STATE_PRODUCT_COUNTRY_NOT_SUPPORTED = 60007; 
        
        /**
         * The VR APK is not installed.
         */
        static readonly ORDER_VR_UNINSTALL_ERROR = 60020;  
        
        /**
         * The user does not sign in using a HUAWEI ID.
         */
        static readonly ORDER_HWID_NOT_LOGIN = 60050;    
        
        /**
         * The user failed to purchase a product because the user already owns the product.
         */
        static readonly ORDER_PRODUCT_OWNED = 60051; 
        
        /**
         *  The user failed to consume a product because the user does not own the product.
         */
        static readonly ORDER_PRODUCT_NOT_OWNED = 60052;  
        
        /**
         * The product has been consumed and cannot be consumed again.
         */
        static readonly ORDER_PRODUCT_CONSUMED = 60053;   
        
        /**
         * The country or region of the signed-in HUAWEI ID does not support HUAWEI IAP.
         */
        static readonly ORDER_ACCOUNT_AREA_NOT_SUPPORTED = 60054;    
        
        /**
         * The user does not agree to the payment agreement.
         */
        static readonly ORDER_NOT_ACCEPT_AGREEMENT = 60055;   
        
        /**
         * The user triggers risk control, and the transaction is rejected.
         */
        static readonly ORDER_HIGH_RISK_OPERATIONS = 60056;
        
        /**
         * This result code is returned only when the pending purchase function is used.
         */
        static readonly ORDER_STATE_PENDING = 60057;

        /**
         * Your app is redirected to the subscription management screen of HUAWEI IAP.
         */
        static readonly TYPE_SUBSCRIBE_MANAGER_ACTIVITY = 2;

        /**
         * Your app is redirected to the subscription editing screen of HUAWEI IAP.
         */
        static readonly TYPE_SUBSCRIBE_EDIT_ACTIVITY = 3;


        /**
         * SHA256WITHRSAPSS
         */
        static readonly SIGNATURE_ALGORITHM_SHA256WITHRSA_PSS = "SHA256WithRSA/PSS";


        /**
         * Not present.
         */
        static readonly PURCHASE_DATA_NOT_PRESENT = -2147483648;

        /**
         * Initialized.
         */
        static readonly PURCHASE_STATE_INITIALIZED = -2147483648;

        /**
         * Purchased.
         */
        static readonly PURCHASE_STATE_PURCHASED = 0;

        /**
         * Canceled.
         */
        static readonly PURCHASE_STATE_CANCELED = 1;

        /**
         * Refunded.
         */
        static readonly PURCHASE_STATE_REFUNDED = 2;

        /**
         * Pending.
         */
        static readonly PURCHASE_STATE_PENDING = 3;

        /**
         * Consumable.
         */
        static readonly PRICE_TYPE_IN_APP_CONSUMABLE = 0;

        /**
         * Non-consumable.
         */
        static readonly PRICE_TYPE_IN_APP_NONCONSUMABLE = 1;

        /**
         * Subscription.
         */
        static readonly PRICE_TYPE_IN_APP_SUBSCRIPTION = 2;
      }
    export default class HMSIapModule extends HmsIapModuleEnum{
        /**
         * Enables HMS Plugin Method Analytics.
         */
        static enableLogger():Promise <Boolean>;

        /**
         * Disables HMS Plugin Method Analytics.
         */
        static disableLogger():Promise <Boolean>;

        /**
         * Returns a response which indicates user's account capabilities of sandbox testing.
         */
        static isSandboxActivated():Promise <IsSandboxActivatedResult>;
        
        /**
         * Enables pending purchase.
         */
        static enablePendingPurchase():Promise <Boolean>;
        
        /**
         * Returns a response which indicates user's environment status.
         */
        static isEnvironmentReady(isSupportAppTouch:boolean):Promise <IsEnvReadyResult>;
        
        /**
         * Returns an array of product information.
         */
        static obtainProductInfo(productInfoReq: ProductInfoReq):Promise <ProductInfoResult>;
        
        /**
         * Starts an activity to buy the desired product or subscribe a product.
         */
        static createPurchaseIntent(purchaseIntentReq:PurchaseIntentReq):Promise <PurchaseResultInfo>;
        
        /**
         * Returns an array of products that purchased by user.
         */
        static obtainOwnedPurchases(ownedPurchasesReq:OwnedPurchasesReq):Promise <OwnedPurchasesResult>;
        
        /**
         * Consumes the desired purchased product.
         */
        static consumeOwnedPurchase(consumeOwnedPurchaseReq:ConsumeOwnedPurchaseReq):Promise <ConsumeOwnedPurchaseResult>;
        
        /**
         * 	Displays screens of HUAWEI IAP, including:
         *       - Subscription editing screen
         *       - Subscription management screen
         */
        static startIapActivity(startIapActivityReq:StartIapActivityReq):Promise <void>;
        
        /**
         * Returns an array of products that purchased and consumed by user.
         */
        static obtainOwnedPurchaseRecord(ownedPurchasesReq: OwnedPurchasesReq):Promise <OwnedPurchasesResult>;
        
    }
    
}