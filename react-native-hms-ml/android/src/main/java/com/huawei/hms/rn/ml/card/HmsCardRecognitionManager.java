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

package com.huawei.hms.rn.ml.card;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.mlsdk.card.bcr.MLBcrAnalyzer;
import com.huawei.hms.mlsdk.card.bcr.MLBcrAnalyzerSetting;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzer;
import com.huawei.hms.mlsdk.card.icr.MLIcrAnalyzerSetting;
import com.huawei.hms.mlsdk.card.icr.MLIdCard;
import com.huawei.hms.mlsdk.card.icr.cloud.MLRemoteIcrAnalyzer;
import com.huawei.hms.mlsdk.card.icr.cloud.MLRemoteIcrAnalyzerSetting;
import com.huawei.hms.rn.ml.utils.RNUtils;


public class HmsCardRecognitionManager {
    private static final String TAG = HmsCardRecognitionManager.class.getSimpleName();
    private static volatile HmsCardRecognitionManager instance;
    private MLBcrAnalyzerSetting mlBcrAnalyzerSetting;
    private MLIcrAnalyzerSetting mlIcrAnalyzerSetting;
    private MLRemoteIcrAnalyzerSetting mlRemoteIcrAnalyzerSetting;
    private MLBcrAnalyzer mlBcrAnalyzer;
    private MLIcrAnalyzer analyzer;
    private MLRemoteIcrAnalyzer remoteIcrAnalyzer;

    public static HmsCardRecognitionManager getInstance() {
        if (instance == null)
            instance = new HmsCardRecognitionManager();
        return instance;
    }

    public void setMlBcrAnalyzerSetting(ReadableMap readableMap) {
        mlBcrAnalyzerSetting = createBcrAnalyzerSetting(readableMap);
    }

    public MLBcrAnalyzerSetting getMlBcrAnalyzerSetting() {
        return mlBcrAnalyzerSetting == null ? new MLBcrAnalyzerSetting.Factory().create() : mlBcrAnalyzerSetting;
    }

    public MLBcrAnalyzer getMlBcrAnalyzer() {
        return mlBcrAnalyzer;
    }

    public void setMlBcrAnalyzer(MLBcrAnalyzer mlBcrAnalyzer) {
        this.mlBcrAnalyzer = mlBcrAnalyzer;
    }

    public MLIcrAnalyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(MLIcrAnalyzer mlIcrAnalyzer) {
        this.analyzer = mlIcrAnalyzer;
    }

    public MLRemoteIcrAnalyzer getRemoteIcrAnalyzer() {
        return remoteIcrAnalyzer;
    }

    public void setRemoteIcrAnalyzer(MLRemoteIcrAnalyzer remoteIcrAnalyzer) {
        this.remoteIcrAnalyzer = remoteIcrAnalyzer;
    }

    public MLBcrAnalyzerSetting createBcrAnalyzerSetting(ReadableMap readableMap) {
        String languageType = "zh";

        if (readableMap == null) {
            Log.i(TAG, "BcrRecognitionSetting object is created using default options.");
            return new MLBcrAnalyzerSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "languageType", ReadableType.String)) {
            languageType = readableMap.getString("languageType");
            Log.i(TAG, "languageType option set.");
        }

        return new MLBcrAnalyzerSetting.Factory().setLangType(languageType).create();
    }

    public void setMlIcrAnalyzerSetting(ReadableMap readableMap, Boolean isRemote) {
        if (isRemote)
            mlRemoteIcrAnalyzerSetting = (MLRemoteIcrAnalyzerSetting) createIcrAnalyzerSetting(readableMap, true);
        else
            mlIcrAnalyzerSetting = (MLIcrAnalyzerSetting) createIcrAnalyzerSetting(readableMap, false);
    }

    public Object createIcrAnalyzerSetting(ReadableMap readableMap, Boolean isRemote) {
        String countryCode = "CN";
        String sideType = MLIcrAnalyzerSetting.FRONT;

        if (readableMap == null) {
            Log.i(TAG, "IcrRecognitionObject object is created using default options.");
            return isRemote ? new MLRemoteIcrAnalyzerSetting.Factory().create() : new MLIcrAnalyzerSetting.Factory().create();
        }
        if (RNUtils.hasValidKey(readableMap, "countryCode", ReadableType.String)) {
            countryCode = readableMap.getString("countryCode");
            Log.i(TAG, "countryCode option set.");
        }

        if (RNUtils.hasValidKey(readableMap, "sideType", ReadableType.String)) {
            sideType = readableMap.getString("sideType");
            Log.i(TAG, "sideType option set.");
        }

        if (isRemote)
            return new MLRemoteIcrAnalyzerSetting.Factory().setCountryCode(countryCode).setSideType(sideType).create();
        else
            return new MLIcrAnalyzerSetting.Factory().setCountryCode(countryCode).setSideType(sideType).create();
    }

    public MLRemoteIcrAnalyzerSetting getMlRemoteIcrAnalyzerSetting() {
        return mlRemoteIcrAnalyzerSetting == null ? new MLRemoteIcrAnalyzerSetting.Factory().create() : mlRemoteIcrAnalyzerSetting;
    }

    public MLIcrAnalyzerSetting getMlIcrAnalyzerSetting() {
        return mlIcrAnalyzerSetting == null ? new MLIcrAnalyzerSetting.Factory().create() : mlIcrAnalyzerSetting;
    }

    public WritableMap resultIcrAnalyze(MLIdCard card) {
        WritableMap writableMap = Arguments.createMap();
        writableMap.putString("name", card.getName());
        writableMap.putString("nation", card.getNation());
        writableMap.putString("address", card.getAddress());
        writableMap.putString("authority", card.getAuthority());
        writableMap.putString("birthday", card.getBirthday());
        writableMap.putString("idNumber", card.getIdNum());
        writableMap.putString("sex", card.getSex());
        writableMap.putString("validDate", card.getValidDate());
        writableMap.putInt("cardSide", card.getSideType());
        return writableMap;
    }

    public WritableMap captureResult(Context inContext, Bitmap originalImage, Bitmap numberImage, String number, String expire) {
        WritableMap writableMap = Arguments.createMap();
        writableMap.putString("originalImage", getImagePathFromUri(inContext, originalImage));
        writableMap.putString("numberImage", getImagePathFromUri(inContext, numberImage));
        writableMap.putString("number", number);
        writableMap.putString("expire", expire);
        return writableMap;
    }

    private String getImagePathFromUri(Context inContext, Bitmap inImage) {
        if (inImage != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            return MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        }
        return null;
    }
}
