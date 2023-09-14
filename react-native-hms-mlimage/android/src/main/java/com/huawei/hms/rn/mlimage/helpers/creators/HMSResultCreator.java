/*
 * Copyright 2023. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.hms.rn.mlimage.helpers.creators;

import static com.huawei.hms.rn.mlimage.helpers.constants.HMSResults.SUCCESS;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huawei.hms.common.size.Size;
import com.huawei.hms.mlsdk.classification.MLImageClassification;
import com.huawei.hms.mlsdk.common.MLCoordinate;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionResult;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewDetectResult;
import com.huawei.hms.mlsdk.imagesuperresolution.MLImageSuperResolutionResult;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentation;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmark;
import com.huawei.hms.mlsdk.objects.MLObject;
import com.huawei.hms.mlsdk.productvisionsearch.MLProductVisionSearch;
import com.huawei.hms.mlsdk.productvisionsearch.MLVisionSearchProduct;
import com.huawei.hms.mlsdk.productvisionsearch.MLVisionSearchProductImage;
import com.huawei.hms.mlsdk.scd.MLSceneDetection;
import com.huawei.hms.mlsdk.textimagesuperresolution.MLTextImageSuperResolution;
import com.huawei.hms.rn.mlimage.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HMSResultCreator {
    private static volatile HMSResultCreator instance;

    private static Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

    public static HMSResultCreator getInstance() {
        if (instance == null) {
            synchronized (HMSResultCreator.class) {
                if (instance == null) {
                    instance = new HMSResultCreator();
                }
            }
        }
        return instance;
    }

    /**
     * Converts point to WritableMap
     *
     * @param point x and y values container
     * @return WritableMap
     */
    private WritableMap getPoint(Point point) {
        WritableMap map = Arguments.createMap();
        map.putInt("y", point.y);
        map.putInt("x", point.x);
        return map;
    }

    /**
     * Converts Rect to WritableMap
     *
     * @param border rect object
     * @return WritableMap
     */
    private WritableMap getBorders(Rect border) {
        WritableMap writableMap = Arguments.createMap();
        writableMap.putInt("left", border.left);
        writableMap.putInt("right", border.right);
        writableMap.putInt("top", border.top);
        writableMap.putInt("down", border.bottom);
        return writableMap;
    }

    /**
     * Converts result to WritableMap
     *
     * @param result classification result list
     * @return WritableMap
     */
    public WritableMap getImageClassificationResult(List<MLImageClassification> result) {
        WritableMap classificationResult = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        for (MLImageClassification classification : result) {
            WritableMap wm = Arguments.createMap();
            wm.putString("classificationIdentity", classification.getClassificationIdentity());
            wm.putString("name", classification.getName());
            wm.putDouble("possibility", classification.getPossibility());
            wa.pushMap(wm);
        }
        classificationResult.putArray("result", wa);
        return classificationResult;
    }

    /**
     * Converts result to WritableMap
     *
     * @param result sparse array result of classification
     * @return WritableMap
     */
    public WritableMap getImageClassificationResult(SparseArray<MLImageClassification> result) {
        return getImageClassificationResult(HMSUtils.getInstance().convertSparseArrayToList(result));
    }

    /**
     * Convert object recognition list result to WritableMap
     *
     * @param list object recognition list
     * @return WritableMap
     */
    public WritableMap getObjectResult(List<MLObject> list) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray array = Arguments.createArray();
        for (MLObject object : list) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putInt("tracingIdentity",
                object.getTracingIdentity() == null ? 0 : object.getTracingIdentity());
            writableMap.putInt("typeIdentity", object.getTypeIdentity());
            writableMap.putDouble("typePossibility",
                object.getTypePossibility() == null ? 0.0 : object.getTypePossibility());
            writableMap.putMap("border", getBorders(object.getBorder()));
            array.pushMap(writableMap);
        }
        wm.putArray("result", array);
        return wm;
    }

    /**
     * Convert object recognition array result to WritableMap
     *
     * @param result sparse array result
     * @return WritableMap
     */
    public WritableMap getObjectResult(SparseArray<MLObject> result) {
        return getObjectResult(HMSUtils.getInstance().convertSparseArrayToList(result));
    }

    /**
     * Converts coordinates to WritableArray
     *
     * @param coordinates list of coordinates
     * @return WritableArray
     */
    private WritableArray getCoordinates(List<MLCoordinate> coordinates) {
        WritableArray wa = Arguments.createArray();
        for (MLCoordinate coordinate : coordinates) {
            WritableMap wm = Arguments.createMap();
            wm.putDouble("latitude", coordinate.getLat());
            wm.putDouble("longitude", coordinate.getLng());
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Convert landmark recognition results to WritableArray
     *
     * @param landmarkResults landmark recognition results
     * @return WritableMap
     */
    public WritableMap getLandmarkDetectionResults(List<MLRemoteLandmark> landmarkResults) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        for (MLRemoteLandmark landMark : landmarkResults) {
            WritableMap temp = Arguments.createMap();
            temp.putString("landMark", landMark.getLandmark());
            temp.putDouble("possibility", landMark.getPossibility());
            temp.putArray("coordinates", getCoordinates(landMark.getPositionInfos()));
            temp.putMap("border", getBorders(landMark.getBorder()));
            wa.pushMap(temp);
        }
        wm.putArray("result", wa);
        return wm;
    }

    /**
     * Converts image segmentation result list to WritableMap
     *
     * @param imageSegmentation image segmentation results
     * @param context app contextl
     * @param isBodySeg analyzer type
     * @return WritableMap
     */
    public WritableMap getImageSegmentationResult(ReactApplicationContext context,
        SparseArray<MLImageSegmentation> imageSegmentation, boolean isBodySeg) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        for (int i = 0; i < imageSegmentation.size(); i++) {
            wa.pushMap(getImageSegmentationResult(context, imageSegmentation.get(i), isBodySeg));
        }
        wm.putArray("result", wa);
        return wm;
    }

    /**
     * Converts image segmentation result list to WritableMap
     *
     * @param imageSegmentation image segmentation results
     * @param context app context
     * @param isBodySeg analyzer type
     * @return WritableMap
     */
    public WritableMap getImageSegmentationAsyncResult(ReactApplicationContext context,
        MLImageSegmentation imageSegmentation, boolean isBodySeg) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putMap("result", getImageSegmentationResult(context, imageSegmentation, isBodySeg));
        return wm;
    }

    /**
     * Converts image segmentation result list to WritableMap
     *
     * @param imageSegmentation result of segmentation
     * @param context app context
     * @param isBodySeg analyzer type
     * @return WritableMap
     */
    private WritableMap getImageSegmentationResult(ReactApplicationContext context,
        MLImageSegmentation imageSegmentation, boolean isBodySeg) {
        WritableMap wm = Arguments.createMap();

        Bitmap grayScale = null;
        if (!isBodySeg) {
            grayScale = createFromMask(imageSegmentation);
        } else {
            wm.putString("foreground",
                HMSUtils.getInstance().saveImageAndGetUri(context, imageSegmentation.getForeground()));
            grayScale = imageSegmentation.getGrayscale();
        }
        wm.putString("grayscale", HMSUtils.getInstance().saveImageAndGetUri(context, grayScale));
        wm.putArray("masks", masksToWA(imageSegmentation.getMasks()));

        return wm;
    }

    private Bitmap createFromMask(MLImageSegmentation imageSegmentation) {
        byte[] masks = imageSegmentation.getMasks();
        int[] results = new int[masks.length];
        for (int i = 0; i < masks.length; i++) {
            if (masks[i] == 1) {
                results[i] = Color.WHITE;
            } else if (masks[i] == 2) {
                results[i] = Color.BLUE;
            } else if (masks[i] == 3) {
                results[i] = Color.DKGRAY;
            } else if (masks[i] == 4) {
                results[i] = Color.YELLOW;
            } else if (masks[i] == 5) {
                results[i] = Color.LTGRAY;
            } else if (masks[i] == 6) {
                results[i] = Color.CYAN;
            } else if (masks[i] == 7) {
                results[i] = Color.RED;
            } else if (masks[i] == 8) {
                results[i] = Color.GRAY;
            } else if (masks[i] == 9) {
                results[i] = Color.MAGENTA;
            } else if (masks[i] == 10) {
                results[i] = Color.GREEN;
            } else {
                results[i] = Color.BLACK;
            }
        }

        return Bitmap.createBitmap(results, 0, imageSegmentation.getOriginal().getWidth(),
            imageSegmentation.getOriginal().getWidth(), imageSegmentation.getOriginal().getHeight(),
            Bitmap.Config.ARGB_8888);
    }

    private WritableArray masksToWA(byte[] masks) {
        WritableArray wa = Arguments.createArray();
        for (int i = 0; i < masks.length; i++) {
            wa.pushInt(masks[i]);
        }
        return wa;
    }

    /**
     * Converts product vision search result to WritableMap
     *
     * @param resultList product vision search result list
     * @return WritableMap
     */
    public WritableMap getProductVisionSearchResult(List<MLProductVisionSearch> resultList) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray array = Arguments.createArray();
        for (MLProductVisionSearch result : resultList) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("type", result.getType());
            writableMap.putArray("products", productListArray(result.getProductList()));
            writableMap.putMap("border", getBorders(result.getBorder()));
            array.pushMap(writableMap);
        }
        wm.putArray("result", array);
        return wm;
    }

    /**
     * Converts product list to WritableArray
     *
     * @param products product list
     * @return WritableArray
     */
    private WritableArray productListArray(List<MLVisionSearchProduct> products) {
        WritableArray array = Arguments.createArray();
        for (MLVisionSearchProduct result : products) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("productId", result.getProductId());
            writableMap.putString("productUrl", result.getProductUrl());
            writableMap.putString("customContent", result.getCustomContent());
            writableMap.putArray("images", productImages(result.getImageList()));
            writableMap.putDouble("possibility", result.getPossibility());
            array.pushMap(writableMap);
        }
        return array;
    }

    /**
     * Converts product iamges to WritableArray
     *
     * @param images product images
     * @return WritableArray
     */
    private WritableArray productImages(List<MLVisionSearchProductImage> images) {
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

    /**
     * Converts image super resolution results to WritableArray
     *
     * @param context Context object
     * @param results image super resolution results
     * @return WritableArray
     */
    public WritableMap getMLImageSuperResolutionResults(ReactApplicationContext context,
        SparseArray<MLImageSuperResolutionResult> results) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        for (int i = 0; i < results.size(); i++) {
            wa.pushString(HMSUtils.getInstance().saveImageAndGetUri(context, results.get(i).getBitmap()));
        }
        wm.putArray("result", wa);
        return wm;
    }

    /**
     * Converts document skew detection results to WritableMap
     *
     * @param results document skew detect results
     * @return WritableMap
     */
    public WritableMap getDocumentSkewDetectResults(SparseArray<MLDocumentSkewDetectResult> results) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        for (int i = 0; i < results.size(); i++) {
            wa.pushMap(getDocumentSkewDetectResult(results.get(i)));
        }
        wm.putArray("result", wa);
        return wm;
    }

    /**
     * Converts document skew detection result to WritableMap
     *
     * @param result document skew detect result
     * @return WritableMap
     */
    private WritableMap getDocumentSkewDetectResult(MLDocumentSkewDetectResult result) {
        WritableMap wm = Arguments.createMap();
        wm.putMap("leftBottomPosition",
            result.getLeftBottomPosition() == null ? Arguments.createMap() : getPoint(result.getLeftBottomPosition()));
        wm.putMap("leftTopPosition",
            result.getLeftTopPosition() == null ? Arguments.createMap() : getPoint(result.getLeftTopPosition()));
        wm.putMap("rightBottomPosition", result.getRightBottomPosition() == null
            ? Arguments.createMap()
            : getPoint(result.getRightBottomPosition()));
        wm.putMap("rightTopPosition",
            result.getRightTopPosition() == null ? Arguments.createMap() : getPoint(result.getRightTopPosition()));
        return wm;
    }

    /**
     * Converts document skew detection result to WritableMap
     *
     * @param result document skew detect result
     * @return WritableMap
     */
    public WritableMap getDocumentSkewDetectAsyncResult(MLDocumentSkewDetectResult result) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putMap("result", getDocumentSkewDetectResult(result));
        return wm;
    }

    /**
     * Converts document skew correction results to WritableMap
     *
     * @param context Context object
     * @param results Document skew correction results
     * @return WritableMap
     */
    public WritableMap getDocumentSkewCorrectionResult(ReactApplicationContext context,
        SparseArray<MLDocumentSkewCorrectionResult> results) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        for (int i = 0; i < results.size(); i++) {
            wa.pushString(HMSUtils.getInstance().saveImageAndGetUri(context, results.get(i).getCorrected()));
        }
        wm.putArray("result", wa);
        return wm;
    }

    /**
     * Converts text image super resolution result list WritableMap
     *
     * @param context app context
     * @param textImageSuperResolution text image super resolution result list
     * @return WritableMap
     */
    public WritableMap getTextImageSuperResolutionResult(ReactApplicationContext context,
        SparseArray<MLTextImageSuperResolution> textImageSuperResolution) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        for (int i = 0; i < textImageSuperResolution.size(); i++) {
            wa.pushString(
                HMSUtils.getInstance().saveImageAndGetUri(context, textImageSuperResolution.get(i).getBitmap()));
        }
        wm.putArray("result", wa);
        return wm;
    }

    /**
     * Converts sparse array Scene detection result to WritableMap
     *
     * @param sceneDetection scene detection result array
     * @return WritableMap
     */
    public WritableMap getSceneDetectionResultSync(SparseArray<MLSceneDetection> sceneDetection) {
        return getSceneDetectionResultAsync(HMSUtils.getInstance().convertSparseArrayToList(sceneDetection));
    }

    /**
     * Converts sparse array Scene detection result to WritableArray
     *
     * @param sceneDetection scene detection result array
     * @return WritableArray
     */
    public WritableMap getSceneDetectionResultAsync(List<MLSceneDetection> sceneDetection) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", getSceneDetectionResult(sceneDetection));
        return wm;
    }

    /**
     * Converts Scene detection result list to WritableArray
     *
     * @param results scene detection result list
     * @return WritableArray
     */
    private WritableArray getSceneDetectionResult(List<MLSceneDetection> results) {
        WritableArray wa = Arguments.createArray();
        for (MLSceneDetection result : results) {
            WritableMap wm = Arguments.createMap();
            wm.putString("result", result.getResult());
            wm.putDouble("confidence", result.getConfidence());
            wa.pushMap(wm);
        }
        return wa;
    }

    public <T> Map<String, Object> toMap(T obj) {
        return gson.fromJson(gson.toJson(obj), Map.class);
    }

    public WritableMap getCompositeResult(Object obj) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        Map<String, Object> res = toMap(obj);
        Arguments.makeNativeMap(res);
        wm.putMap("result", Arguments.makeNativeMap(res));
        return wm;
    }

    /**
     * converts integer result to WritableMap
     *
     * @param integer method result
     * @return WritableMap
     */
    public WritableMap integerResult(int integer) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putInt("result", integer);
        return wm;
    }

    /**
     * converts integer result to WritableMap
     *
     * @param set method result
     * @return WritableMap
     */
    public WritableMap stringSetResult(Set<String> set) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        if (set != null) {
            for (String element : set) {
                wa.pushString(element);
            }
        }
        wm.putArray("result", wa);
        return wm;
    }

    /**
     * converts string result to WritableMap
     *
     * @param string result
     * @return WritableMap
     */
    public WritableMap getStringResult(String string) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putString("result", TextUtils.isEmpty(string) ? "" : string);
        return wm;
    }

    /**
     * converts string result to WritableMap
     *
     * @param is result
     * @return WritableMap
     */
    public WritableMap getBooleanResult(boolean is) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putBoolean("result", is);
        return wm;
    }

    /**
     * converts float result to WritableMap
     *
     * @param aFloat method result
     * @return WritableMap
     */
    public WritableMap floatResult(Float aFloat) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putDouble("result", aFloat);
        return wm;
    }

    /**
     * Converts string list to result
     *
     * @param list data list
     * @return WritableMap
     */
    public WritableMap stringListResult(List<String> list) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", HMSUtils.getInstance().convertStringListIntoWa(list));
        return wm;
    }

    /**
     * Converts display dimension to result
     *
     * @param displayDimension preview size
     * @return WritableMap
     */
    public WritableMap displayDimensionResult(Size displayDimension) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putInt("width", displayDimension.getWidth());
        wm.putInt("height", displayDimension.getHeight());
        return wm;
    }
}
