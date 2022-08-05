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

package com.huawei.hms.rn.ml.helpers.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huawei.hms.mlplugin.productvisionsearch.MLProductVisionSearchCapture;
import com.huawei.hms.mlsdk.productvisionsearch.MLProductVisionSearch;
import com.huawei.hms.mlsdk.productvisionsearch.MLVisionSearchProduct;
import com.huawei.hms.rn.ml.R;
import com.huawei.hms.rn.ml.helpers.adapters.HMSBottomSheetAdapter;
import com.huawei.hms.rn.ml.helpers.constants.HMSConstants;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.models.HMSProductModel;
import com.huawei.hms.rn.ml.helpers.utils.HMSLogger;
import com.huawei.hms.rn.ml.helpers.views.HMSBottomSheetGridView;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;
import java.util.List;

public class HMSProductFragment extends MLProductVisionSearchCapture.AbstractProductFragment<HMSProductModel> {
    private static final String TAG = HMSProductFragment.class.getSimpleName();

    private List<HMSProductModel> productList = new ArrayList<>();

    private View productView;

    private HMSBottomSheetGridView gridView;

    private HMSBottomSheetAdapter bottomSheetAdapter;

    private ReactApplicationContext reactApplicationContext;

    public HMSProductFragment(ReactApplicationContext context) {
        reactApplicationContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        productView = inflater.inflate(R.layout.hms_product_fragment, container, false);
        gridView = productView.findViewById(R.id.hms_bottom_sheet_grid_view);
        gridView.setNumColumns(2);
        bottomSheetAdapter = new HMSBottomSheetAdapter(getContext(), productList);
        gridView.setAdapter(bottomSheetAdapter);
        return productView;
    }

    @Override
    public List<HMSProductModel> getProductList(List<MLProductVisionSearch> list) {
        return obtainProducts(list);
    }

    @Override
    public void onResult(List<HMSProductModel> list) {
        if (null == list || list.isEmpty()) {
            Log.i(TAG, "onResult list is empty");
            return;
        }
        Log.i(TAG, list.toString());
        productList.clear();
        productList.addAll(list);
        bottomSheetAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onError(Exception e) {
        return false;
    }

    private List<HMSProductModel> obtainProducts(List<MLProductVisionSearch> list) {
        List<HMSProductModel> productBeans = new ArrayList<>();
        for (MLProductVisionSearch mlProductVisionSearch : list) {
            for (MLVisionSearchProduct product : mlProductVisionSearch.getProductList()) {
                HMSProductModel productBean = new HMSProductModel();
                productBean.setImgUrl(product.getImageList().get(0).getImageId());
                productBean.setName(product.getProductId());
                productBeans.add(productBean);
            }
        }
        sendEvent(HMSResultCreator.getInstance().getProductVisionSearchResult(list));
        return productBeans;
    }

    /**
     * Helper method that sends an event to RN side.
     *
     * @param params WritableMap object that contains related keys and values
     */
    private void sendEvent(WritableMap params) {
        HMSLogger.getInstance(reactApplicationContext).sendSingleEvent("onResult");
        reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(HMSConstants.PRODUCT_ON_RESULT, params);
    }

}
