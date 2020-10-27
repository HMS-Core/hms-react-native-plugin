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
package com.huawei.hms.rn.iap.client;

import android.app.Activity;
import android.content.IntentSender;
import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.iap.entity.ProductInfoReq;
import com.huawei.hms.rn.iap.client.viewmodel.ViewModel;
import com.huawei.hms.support.api.client.Status;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import static com.huawei.hms.rn.iap.client.utils.MapUtil.fromJson;
import static com.huawei.hms.rn.iap.client.utils.MapUtil.toArrayList;
import static com.huawei.hms.rn.iap.client.utils.MapUtil.toJson;
import static com.huawei.hms.rn.iap.client.utils.MapUtil.toStringArrayList;

/**
 * IapClientReqHelper is the tool class of {@link ViewModel}.
 *
 * @since v.5.0.0
 */
public class Helper {
    public final static String TAG = Helper.class.getSimpleName();

    /**
     * Converts ReadableMap to a
     * requested generic IAP instance(ProductInfoReq, PurchaseIntentReq, PurchaseIntentReq,
     * ConsumeOwnedPurchaseReq, StartIapActivityReq).
     *
     * @return <T> IAP instance.
     */
    public static <T> T toIAPObject(final ReadableMap readableMap, Class<? extends T> type) {
        T instance;
        JSONObject jsonObject = toJson(readableMap);
        instance = fromJson(jsonObject.toString(), type);
        if (instance instanceof ProductInfoReq) {
            if (readableMap.hasKey("productList") && readableMap.getArray("productList") != null) {
                List<Object> productList = toArrayList(readableMap.getArray("productList"));
                ((ProductInfoReq) instance).setProductIds(toStringArrayList(productList));
            }
        }
        return instance;
    }

    /**
     * To start an activity.
     *
     * @param activity:   the activity to launch a new page.
     * @param status:     This parameter contains the pendingIntent object of the payment page.
     * @param resultCode: Result code.
     */
    public static void startResolutionForResult(final Activity activity, final Status status, final int resultCode) {
        if (status == null) {
            Log.e(TAG, "status is null");
            return;
        }
        if (status.hasResolution()) {
            try {
                status.startResolutionForResult(activity, resultCode);
            } catch (IntentSender.SendIntentException exp) {
                Log.e(TAG, Objects.requireNonNull(exp.getMessage()));
            }
        } else {
            Log.e(TAG, "intent is null");
        }
    }
}