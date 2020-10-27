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
package com.huawei.hms.rn.iap.client.utils;

import com.huawei.hms.rn.iap.HmsIapModule;
import com.huawei.hms.iap.IapClient;
import com.huawei.hms.iap.entity.InAppPurchaseData;
import com.huawei.hms.iap.entity.OrderStatusCode;
import com.huawei.hms.iap.entity.StartIapActivityReq;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface Constants {
    /**
     * requestCode for pull up the pmsPay page
     */
    Integer REQ_CODE_PURCHASE_INTENT = 222;
    String isSuccessKey = "isSuccess";
    String errorMessageKey = "errorMessage";
    /**
     * iapReqConstants variables that are used by {@link HmsIapModule} class.
     **/
    Map<String, Object> CONSTANTS = initMap();

    /**
     * Constant HashMap Value.
     *
     * @return HashMap Instance.
     */
    static Map<String, Object> initMap() {
        Map<String, Object> constantMap = new HashMap<>();
        // OrderStatusCode
        constantMap.put("ORDER_ACCOUNT_AREA_NOT_SUPPORTED", OrderStatusCode.ORDER_ACCOUNT_AREA_NOT_SUPPORTED);
        constantMap.put("ORDER_HIGH_RISK_OPERATIONS", OrderStatusCode.ORDER_HIGH_RISK_OPERATIONS);
        constantMap.put("ORDER_HWID_NOT_LOGIN", OrderStatusCode.ORDER_HWID_NOT_LOGIN);
        constantMap.put("ORDER_NOT_ACCEPT_AGREEMENT", OrderStatusCode.ORDER_NOT_ACCEPT_AGREEMENT);
        constantMap.put("ORDER_PRODUCT_CONSUMED", OrderStatusCode.ORDER_PRODUCT_CONSUMED);
        constantMap.put("ORDER_PRODUCT_NOT_OWNED", OrderStatusCode.ORDER_PRODUCT_NOT_OWNED);
        constantMap.put("ORDER_PRODUCT_OWNED", OrderStatusCode.ORDER_PRODUCT_OWNED);
        constantMap.put("ORDER_STATE_CANCEL", OrderStatusCode.ORDER_STATE_CANCEL);
        constantMap.put("ORDER_STATE_FAILED", OrderStatusCode.ORDER_STATE_FAILED);
        constantMap.put("ORDER_STATE_NET_ERROR", OrderStatusCode.ORDER_STATE_NET_ERROR);
        constantMap.put("ORDER_STATE_PARAM_ERROR", OrderStatusCode.ORDER_STATE_PARAM_ERROR);
        constantMap.put("ORDER_STATE_SUCCESS", OrderStatusCode.ORDER_STATE_SUCCESS);
        constantMap.put("ORDER_VR_UNINSTALL_ERROR", OrderStatusCode.ORDER_VR_UNINSTALL_ERROR);
        constantMap.put("ORDER_STATE_IAP_NOT_ACTIVATED", OrderStatusCode.ORDER_STATE_IAP_NOT_ACTIVATED);
        constantMap.put("ORDER_STATE_PRODUCT_INVALID", OrderStatusCode.ORDER_STATE_PRODUCT_INVALID);
        constantMap.put("ORDER_STATE_CALLS_FREQUENT", OrderStatusCode.ORDER_STATE_CALLS_FREQUENT);
        constantMap.put("ORDER_STATE_PMS_TYPE_NOT_MATCH", OrderStatusCode.ORDER_STATE_PMS_TYPE_NOT_MATCH);
        constantMap.put("ORDER_STATE_PRODUCT_COUNTRY_NOT_SUPPORTED",
                OrderStatusCode.ORDER_STATE_PRODUCT_COUNTRY_NOT_SUPPORTED);
        // StartIapActivityReq
        constantMap.put("TYPE_SUBSCRIBE_MANAGER_ACTIVITY", StartIapActivityReq.TYPE_SUBSCRIBE_MANAGER_ACTIVITY);
        constantMap.put("TYPE_SUBSCRIBE_EDIT_ACTIVITY", StartIapActivityReq.TYPE_SUBSCRIBE_EDIT_ACTIVITY);
        // InAppPurchaseData
        constantMap.put("PURCHASE_DATA_NOT_PRESENT", InAppPurchaseData.NOT_PRESENT);
        // PurchaseState
        constantMap.put("PURCHASE_STATE_CANCELED", InAppPurchaseData.PurchaseState.CANCELED);
        constantMap.put("PURCHASE_STATE_INITIALIZED", InAppPurchaseData.PurchaseState.INITIALIZED);
        constantMap.put("PURCHASE_STATE_PURCHASED", InAppPurchaseData.PurchaseState.PURCHASED);
        constantMap.put("PURCHASE_STATE_REFUNDED", InAppPurchaseData.PurchaseState.REFUNDED);
        // PriceType
        constantMap.put("PRICE_TYPE_IN_APP_CONSUMABLE", IapClient.PriceType.IN_APP_CONSUMABLE);
        constantMap.put("PRICE_TYPE_IN_APP_NONCONSUMABLE", IapClient.PriceType.IN_APP_NONCONSUMABLE);
        constantMap.put("PRICE_TYPE_IN_APP_SUBSCRIPTION", IapClient.PriceType.IN_APP_SUBSCRIPTION);
        return Collections.unmodifiableMap(constantMap);
    }
}