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

import android.util.Log;

import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.iap.entity.ConsumeOwnedPurchaseResult;
import com.huawei.hms.iap.entity.InAppPurchaseData;
import com.huawei.hms.iap.entity.IsEnvReadyResult;
import com.huawei.hms.iap.entity.IsSandboxActivatedResult;
import com.huawei.hms.iap.entity.OwnedPurchasesResult;
import com.huawei.hms.iap.entity.ProductInfo;
import com.huawei.hms.iap.entity.ProductInfoResult;
import com.huawei.hms.iap.entity.PurchaseIntentResult;
import com.huawei.hms.iap.entity.PurchaseResultInfo;
import com.huawei.hms.support.api.client.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class DataUtils {
    private static final String TAG = DataUtils.class.getSimpleName();

    public static JSONObject getJSONFromStatus(Status obj) {
        JSONObject j = new JSONObject();
        try {
            j.put("errorString", obj.getErrorString());
            j.put("statusCode", obj.getStatusCode());
            j.put("statusMessage", obj.getStatusMessage());
            j.put("hasResolution", obj.hasResolution());
            j.put("isCanceled", obj.isCanceled());
            j.put("isInterrupted", obj.isInterrupted());
            j.put("isSuccess", obj.isSuccess());
            j.put("hasResolution", obj.hasResolution());
            j.put("describeContents", obj.describeContents());
            j.put("hashCode", obj.hashCode());
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return j;
    }

    public static WritableMap getMapCreatePurchaseIntent(PurchaseResultInfo obj) {
        WritableMap writableMap = null;
        try {
            JSONObject j = new JSONObject();
            j.put("returnCode", obj.getReturnCode());
            j.put("inAppPurchaseData", obj.getInAppPurchaseData());
            j.put("inAppDataSignature", obj.getInAppDataSignature());
            j.put("errMsg", obj.getErrMsg());
            writableMap = MapUtil.toWritableMap(j);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return writableMap;
    }

    public static WritableMap getMapFromConsumeOwnedPurchaseResult(ConsumeOwnedPurchaseResult obj) {
        WritableMap writableMap = null;
        try {
            JSONObject j = new JSONObject();
            j.put("consumePurchaseData", obj.getConsumePurchaseData());
            j.put("dataSignature", obj.getDataSignature());
            j.put("errMsg", obj.getErrMsg());
            j.put("returnCode", obj.getReturnCode());
            j.put("status", getJSONFromStatus(obj.getStatus()));
            writableMap = MapUtil.toWritableMap(j);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return writableMap;
    }

    public static WritableMap getMapFromInAppPurchaseData(InAppPurchaseData obj) {
        WritableMap writableMap = null;
        try {
            JSONObject j = new JSONObject();
            j.put("appInfo", obj.getAppInfo());
            j.put("applicationId", obj.getApplicationId());
            j.put("cancelledSubKeepDays", obj.getCancelledSubKeepDays());
            j.put("cancelReason", obj.getCancelReason());
            j.put("cancelTime", obj.getCancelTime());
            j.put("country", obj.getCountry());
            j.put("currency", obj.getCurrency());
            j.put("daysLasted", obj.getDaysLasted());
            j.put("developerPayload", obj.getDeveloperPayload());
            j.put("expirationDate", obj.getExpirationDate());
            j.put("expirationIntent", obj.getExpirationIntent());
            j.put("introductoryFlag", obj.getIntroductoryFlag());
            j.put("lastOrderId", obj.getLastOrderId());
            j.put("notifyClosed", obj.getNotifyClosed());
            j.put("numOfDiscount", obj.getNumOfDiscount());
            j.put("numOfPeriods", obj.getNumOfPeriods());
            j.put("orderID", obj.getOrderID());
            j.put("oriPurchaseTime", obj.getOriPurchaseTime());
            j.put("packageName", obj.getPackageName());
            j.put("price", obj.getPrice());
            j.put("priceConsentStatus", obj.getPriceConsentStatus());
            j.put("productGroup", obj.getProductGroup());
            j.put("productId", obj.getProductId());
            j.put("productName", obj.getProductName());
            j.put("purchaseState", obj.getPurchaseState());
            j.put("purchaseTime", obj.getPurchaseTime());
            j.put("purchaseToken", obj.getPurchaseToken());
            j.put("purchaseType", obj.getPurchaseType());
            j.put("quantity", obj.getQuantity());
            j.put("renewPrice", obj.getRenewPrice());
            j.put("renewStatus", obj.getRenewStatus());
            j.put("retryFlag", obj.getRetryFlag());
            j.put("subscriptionId", obj.getSubscriptionId());
            j.put("trialFlag", obj.getTrialFlag());
            j.put("isAutoRenewing", obj.isAutoRenewing());
            j.put("isSubValid", obj.isSubValid());
            j.put("cancelledSubKeepDays", obj.getCancelledSubKeepDays());
            j.put("kind", obj.getKind());
            j.put("developerChallenge", obj.getDeveloperChallenge());
            j.put("consumptionState", obj.getConsumptionState());
            j.put("payOrderId", obj.getPayOrderId());
            j.put("payType", obj.getPayType());
            j.put("deferFlag", obj.getDeferFlag());
            j.put("oriSubscriptionId", obj.getOriSubscriptionId());
            j.put("cancelWay", obj.getCancelWay());
            j.put("cancellationTime", obj.getCancellationTime());
            j.put("resumeTime", obj.getResumeTime());
            writableMap = MapUtil.toWritableMap(j);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return writableMap;
    }

    public static WritableMap getMapFromIsEnvReadyResult(IsEnvReadyResult obj) {
        WritableMap writableMap = null;
        try {
            JSONObject j = new JSONObject();
            j.put("returnCode", obj.getReturnCode());
            j.put("status", getJSONFromStatus(obj.getStatus()));
            writableMap = MapUtil.toWritableMap(j);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return writableMap;
    }

    public static WritableMap getMapFromIsSandboxActivatedResult(IsSandboxActivatedResult obj) {
        WritableMap writableMap = null;
        try {
            JSONObject j = new JSONObject();
            j.put("errMsg", obj.getErrMsg());
            j.put("isSandboxApk", obj.getIsSandboxApk());
            j.put("isSandboxUser", obj.getIsSandboxUser());
            j.put("returnCode", obj.getReturnCode());
            j.put("versionFrMarket", obj.getVersionFrMarket());
            j.put("versionInApk", obj.getVersionInApk());
            j.put("status", getJSONFromStatus(obj.getStatus()));
            writableMap = MapUtil.toWritableMap(j);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return writableMap;
    }

    public static WritableMap getMapFromOwnedPurchasesResult(OwnedPurchasesResult obj) {
        WritableMap writableMap = null;
        try {
            JSONObject j = new JSONObject();
            j.put("continuationToken", obj.getContinuationToken());
            j.put("errMsg", obj.getErrMsg());
            j.put("itemList", new JSONArray(obj.getItemList()));
            j.put("inAppPurchaseDataList", getArrayFromInAppPurchaseDataList(obj));
            j.put("inAppSignature", new JSONArray(obj.getInAppSignature()));
            j.put("placedInappPurchaseDataList", new JSONArray(obj.getPlacedInappPurchaseDataList()));
            j.put("placedInappSignatureList", new JSONArray(obj.getPlacedInappSignatureList()));
            j.put("returnCode", obj.getReturnCode());
            j.put("status", getJSONFromStatus(obj.getStatus()));
            writableMap = MapUtil.toWritableMap(j);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return writableMap;
    }

    public static JSONArray getArrayFromInAppPurchaseDataList(OwnedPurchasesResult obj) {
        List<String> inAppPurchaseDataList = obj.getInAppPurchaseDataList();
        JSONArray productArray = new JSONArray();
        for (int i = 0; i < inAppPurchaseDataList.size(); i++) {
            String item = inAppPurchaseDataList.get(i);
            productArray.put(item);
        }
        return productArray;
    }

    public static WritableMap getMapFromProductInfoResult(ProductInfoResult obj) {
        WritableMap writableMap = null;
        try {
            JSONObject j = new JSONObject();
            j.put("returnCode", obj.getReturnCode());
            j.put("errMsg", obj.getErrMsg());
            j.put("productInfoList", getArrayFromProductInfoList(obj));
            j.put("status", getJSONFromStatus(obj.getStatus()));
            writableMap = MapUtil.toWritableMap(j);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return writableMap;
    }

    public static JSONArray getArrayFromProductInfoList(ProductInfoResult obj) {
        List<ProductInfo> productInfoList = obj.getProductInfoList();
        JSONArray productArray = new JSONArray();
        for (int i = 0; i < productInfoList.size(); i++) {
            ProductInfo item = productInfoList.get(i);
            JSONObject product = getMapFromProductInfo(item);
            productArray.put(product);
        }
        return productArray;
    }

    public static WritableMap getMapFromPurchaseIntentResult(PurchaseIntentResult obj) {
        WritableMap writableMap = null;
        try {
            JSONObject j = new JSONObject();
            j.put("returnCode", obj.getReturnCode());
            j.put("errMsg", obj.getErrMsg());
            j.put("status", getJSONFromStatus(obj.getStatus()));
            writableMap = MapUtil.toWritableMap(j);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return writableMap;
    }

    public static WritableMap getMapFromPurchaseResultInfo(PurchaseResultInfo obj) {
        WritableMap writableMap = null;
        try {
            JSONObject j = new JSONObject();
            j.put("returnCode", obj.getReturnCode());
            j.put("errMsg", obj.getErrMsg());
            j.put("inAppPurchaseData", obj.getInAppPurchaseData());
            j.put("inAppDataSignature", obj.getInAppDataSignature());
            writableMap = MapUtil.toWritableMap(j);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return writableMap;
    }

    public static JSONObject getMapFromProductInfo(ProductInfo obj) {
        JSONObject j = new JSONObject();
        try {
            j.put("productId", obj.getProductId());
            j.put("priceType", obj.getPriceType());
            j.put("price", obj.getPrice());
            j.put("microsPrice", obj.getMicrosPrice());
            j.put("originalLocalPrice", obj.getOriginalLocalPrice());
            j.put("originalMicroPrice", obj.getOriginalMicroPrice());
            j.put("currency", obj.getCurrency());
            j.put("productName", obj.getProductName());
            j.put("productDesc", obj.getProductDesc());
            j.put("subPeriod", obj.getSubPeriod());
            j.put("subSpecialPrice", obj.getSubSpecialPrice());
            j.put("subSpecialPriceMicros", obj.getSubSpecialPriceMicros());
            j.put("subSpecialPeriod", obj.getSubSpecialPeriod());
            j.put("subSpecialPeriodCycles", obj.getSubSpecialPeriodCycles());
            j.put("subFreeTrialPeriod", obj.getSubFreeTrialPeriod());
            j.put("subGroupId", obj.getSubGroupId());
            j.put("subGroupTitle", obj.getSubGroupTitle());
            j.put("subProductLevel", obj.getSubProductLevel());
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return j;
    }
}