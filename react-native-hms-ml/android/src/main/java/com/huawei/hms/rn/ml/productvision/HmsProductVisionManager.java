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

package com.huawei.hms.rn.ml.productvision;

import java.util.List;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.mlsdk.productvisionsearch.MLProductVisionSearch;
import com.huawei.hms.mlsdk.productvisionsearch.MLVisionSearchProduct;
import com.huawei.hms.mlsdk.productvisionsearch.MLVisionSearchProductImage;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.MLRemoteProductVisionSearchAnalyzer;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.MLRemoteProductVisionSearchAnalyzerSetting;
import com.huawei.hms.rn.ml.utils.RNUtils;


public class HmsProductVisionManager {
    private static final String TAG = HmsProductVisionManager.class.getSimpleName();
    private static volatile HmsProductVisionManager instance;
    private MLRemoteProductVisionSearchAnalyzerSetting searchAnalyzerSetting;
    private MLRemoteProductVisionSearchAnalyzer productVisionSearchAnalyzer;

    public static HmsProductVisionManager getInstance() {
        if (instance == null)
            instance = new HmsProductVisionManager();
        return instance;
    }

    public MLRemoteProductVisionSearchAnalyzerSetting getSearchAnalyzerSetting() {
        return searchAnalyzerSetting == null ? new MLRemoteProductVisionSearchAnalyzerSetting.Factory().create() : searchAnalyzerSetting;
    }

    public void setSearchAnalyzerSetting(ReadableMap readableMap) {
        this.searchAnalyzerSetting = createSearchAnalyzerSetting(readableMap);
    }

    public MLRemoteProductVisionSearchAnalyzer getProductVisionSearchAnalyzer() {
        return productVisionSearchAnalyzer;
    }

    public void setProductVisionSearchAnalyzer(MLRemoteProductVisionSearchAnalyzer productVisionSearchAnalyzer) {
        this.productVisionSearchAnalyzer = productVisionSearchAnalyzer;
    }

    public MLRemoteProductVisionSearchAnalyzerSetting createSearchAnalyzerSetting(ReadableMap readableMap) {
        int largestNumOfReturns = 20;

        if (readableMap == null) {
            Log.i(TAG, "MLRemoteProductVisionSearchAnalyzerSetting object is created using default options.");
            return new MLRemoteProductVisionSearchAnalyzerSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "maxResults", ReadableType.Number)) {
            largestNumOfReturns = readableMap.getInt("maxResults");
            Log.i(TAG, "maxResults option set.");
        }

        return new MLRemoteProductVisionSearchAnalyzerSetting.Factory().setLargestNumOfReturns(largestNumOfReturns).create();
    }

    public WritableArray analyzeResult(List<MLProductVisionSearch> resultList) {
        WritableArray array = Arguments.createArray();
        for (MLProductVisionSearch result : resultList) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("type", result.getType());
            writableMap.putArray("products", productListArray(result.getProductList()));
            writableMap.putInt("bottom", result.getBorder().bottom);
            writableMap.putInt("top", result.getBorder().top);
            writableMap.putInt("left", result.getBorder().left);
            writableMap.putInt("right", result.getBorder().right);
            array.pushMap(writableMap);
        }
        return array;
    }

    public WritableArray productListArray(List<MLVisionSearchProduct> products) {
        WritableArray array = Arguments.createArray();
        for (MLVisionSearchProduct result : products) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("productId", result.getProductId());
            writableMap.putDouble("possibility", result.getPossibility());
            writableMap.putArray("images", productImages(result.getImageList()));
            array.pushMap(writableMap);
        }
        return array;
    }

    public WritableArray productImages(List<MLVisionSearchProductImage> images) {
        WritableArray array = Arguments.createArray();
        for (MLVisionSearchProductImage image : images) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("imageId", image.getImageId());
            writableMap.putString("productId", image.getProductId());
            writableMap.putDouble("possibility", image.getPossibility());
            array.pushMap(writableMap);
        }
        return array;
    }
}
