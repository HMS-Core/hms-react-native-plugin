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

package com.huawei.hms.rn.ml.helpers.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.SurfaceHolder;

import com.huawei.hms.mlsdk.skeleton.MLJoint;
import com.huawei.hms.mlsdk.skeleton.MLSkeleton;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class HMSUtils {
    private static volatile HMSUtils instance;

    private SurfaceHolder surfaceViewHolder;

    public static HMSUtils getInstance() {
        if (instance == null) {
            synchronized (HMSUtils.class) {
                if (instance == null) {
                    instance = new HMSUtils();
                }
            }
        }
        return instance;
    }

    /**
     * Converts JSONObject to WritableMap
     *
     * @param jsonObject JSONObject
     * @return WritableMap
     * @throws JSONException jsonObject.get throws it
     */
    public WritableMap convertJsonToWritableMap(JSONObject jsonObject) throws JSONException {
        WritableMap map = Arguments.createMap();
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.putMap(key, convertJsonToWritableMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                map.putArray(key, convertJsonToWritableArray((JSONArray) value));
            } else if (value instanceof Boolean) {
                map.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                map.putInt(key, (Integer) value);
            } else if (value instanceof Double) {
                map.putDouble(key, (Double) value);
            } else if (value instanceof String) {
                map.putString(key, (String) value);
            } else {
                map.putString(key, value.toString());
            }
        }
        return map;
    }

    /**
     * Converts JSONArray to WritableArray
     *
     * @param jsonArray JSONArray
     * @return WritableArray
     * @throws JSONException jsonObject.get throws it
     */
    private WritableArray convertJsonToWritableArray(JSONArray jsonArray) throws JSONException {
        WritableArray array = Arguments.createArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                array.pushMap(convertJsonToWritableMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                array.pushArray(convertJsonToWritableArray((JSONArray) value));
            } else if (value instanceof Boolean) {
                array.pushBoolean((Boolean) value);
            } else if (value instanceof Integer) {
                array.pushInt((Integer) value);
            } else if (value instanceof Double) {
                array.pushDouble((Double) value);
            } else if (value instanceof String) {
                array.pushString((String) value);
            } else {
                array.pushString(value.toString());
            }
        }

        return array;
    }

    /**
     * Converts ReadableArray to Point list
     *
     * @param ra readable array
     * @return point list
     */
    public List<Point> convertRaToPointList(ReadableArray ra) {
        List<Point> pointList = new ArrayList<>();
        for (int i = 0; i < ra.size(); i++) {
            pointList.add(new Point(ra.getMap(i).getInt("x"), ra.getMap(i).getInt("y")));
        }
        return pointList;
    }

    /**
     * Converts readable array to skeleton list
     *
     * @param ra readable array
     * @return skeleton list
     */
    public List<MLSkeleton> convertRaToSkeletonList(ReadableArray ra) {
        List<MLSkeleton> skeletonList = new ArrayList<>();
        for (int i = 0; i < ra.size(); i++) {
            if (ra.getType(i) == ReadableType.Map) {
                skeletonList.add(convertRaToSkeleton(ra.getMap(i)));
            }
        }
        return skeletonList;
    }

    /**
     * Converts readable map to MLSkeleton
     *
     * @param rm readable map
     * @return MLSkeleton
     */
    private MLSkeleton convertRaToSkeleton(ReadableMap rm) {
        List<MLJoint> mlJoints = new ArrayList<>();
        if (hasValidKey(rm, "joints", ReadableType.Array)) {
            ReadableArray jointsArray = rm.getArray("joints");
            for (int i = 0; i < jointsArray.size(); i++) {
                if (jointsArray.getType(i) == ReadableType.Map) {
                    ReadableMap map = jointsArray.getMap(i);
                    float pointX = HMSUtils.getInstance().hasValidKey(map, "pointX", ReadableType.Number)
                        ? (float) map.getDouble("pointX")
                        : 0;
                    float pointY = HMSUtils.getInstance().hasValidKey(map, "pointY", ReadableType.Number)
                        ? (float) map.getDouble("pointY")
                        : 0;
                    int type = HMSUtils.getInstance().hasValidKey(map, "type", ReadableType.Number)
                        ? map.getInt("type")
                        : -1;
                    float score = HMSUtils.getInstance().hasValidKey(map, "score", ReadableType.Number)
                        ? (float) map.getDouble("score")
                        : 0;
                    mlJoints.add(new MLJoint(pointX, pointY, type, score));
                }
            }
        }
        return new MLSkeleton(mlJoints);
    }

    /**
     * Converts ReadableArray to String List
     *
     * @param readableArray array
     * @return List
     */
    public List<String> readableArrayIntoStringList(ReadableArray readableArray) {
        if (readableArray.size() == 0) {
            return Collections.emptyList();
        }
        List<String> arrList = new ArrayList<>();
        for (int i = 0; i < readableArray.size(); i++) {
            arrList.add(readableArray.getString(i));
        }
        return arrList;
    }

    /**
     * Converts string list to WritableArray
     *
     * @param list list of strings
     * @return WritableArray
     */
    public WritableArray convertStringListIntoWa(List<String> list) {
        WritableArray writableArray = Arguments.createArray();
        for (String value : list) {
            writableArray.pushString(value);
        }
        return writableArray;
    }

    /**
     * Checks if ReadableMap has valid key
     *
     * @param readableMap ReadableMap
     * @param key key to be checked
     * @param type key's type
     * @return true or false
     */
    public boolean hasValidKey(ReadableMap readableMap, String key, ReadableType type) {
        return readableMap.hasKey(key) && readableMap.getType(key) == type;
    }

    /**
     * Checks boolean key is valid or not and if valid then returns its value
     *
     * @param readableMap ReadableMap
     * @param key key to be checked
     * @return true or false
     */
    public boolean boolKeyCheck(ReadableMap readableMap, String key) {
        if (!hasValidKey(readableMap, key, ReadableType.Boolean)) {
            return false;
        }
        return readableMap.getBoolean(key);
    }

    /**
     * Converts byte array to WritableArray
     *
     * @param ba byte array
     * @return WritableArray
     */
    public WritableArray convertByteArrToWa(byte[] ba) {
        WritableArray wa = Arguments.createArray();
        for (byte b : ba) {
            wa.pushInt(b);
        }
        return wa;
    }

    /**
     * Converts ReadableArray to byte array
     *
     * @param ra ReadableArray
     * @return byte array
     */
    public byte[] convertRaToByteArray(ReadableArray ra) {
        byte[] bytes = new byte[ra.size()];
        for (int i = 0; i < ra.size(); i++) {
            bytes[i] = (byte) ra.getInt(i);
        }
        return bytes;
    }

    /**
     * Converts byte array to byte buffer
     *
     * @param arr byte array
     * @return byte buffer
     */
    public ByteBuffer convertByteArrToByteBuffer(byte[] arr) {
        return ByteBuffer.wrap(arr);
    }

    /**
     * Converts sparse array to list
     *
     * @param result sparse array of any type
     * @param <T> generic parameter
     * @return List
     */
    public <T> List<T> convertSparseArrayToList(SparseArray<T> result) {
        List<T> converted = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            converted.add(result.get(result.keyAt(i)));
        }
        return converted;
    }

    /**
     * Converts ReadableArray to String Set
     *
     * @param ra ReadableArray
     * @return String Set
     */
    public Set<String> convertRaToStringSet(ReadableArray ra) {
        Set<String> word = new HashSet<>();
        for (int i = 0; i < ra.size(); i++) {
            if (ra.getType(i) == ReadableType.String) {
                word.add(ra.getString(i));
            }
        }
        return word;
    }

    /**
     * Converts Float array to WritableArray
     *
     * @param arr Float array
     * @return WritableArray
     */
    public WritableArray convert2DFloatArrToWa(Float[][] arr) {
        WritableArray wa = Arguments.createArray();
        for (float val : arr[0]) {
            wa.pushDouble(val);
        }
        return wa;
    }

    /**
     * Converts Float array to WritableArray
     *
     * @param arr Float array
     * @return WritableArray
     */
    public WritableArray convertFloatArrToWa(Float[] arr) {
        WritableArray wa = Arguments.createArray();
        for (float val : arr) {
            wa.pushDouble(val);
        }
        return wa;
    }

    /**
     * Save image to gallery
     *
     * @param inContext app context
     * @param inImage bitmap image
     * @return image uri
     */
    public String saveImageAndGetUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        return MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, null, null);
    }

    /**
     * Sets the holder for lensEngine
     *
     * @param surfaceViewHolder holder
     */
    public void setSurfaceViewHolder(SurfaceHolder surfaceViewHolder) {
        this.surfaceViewHolder = surfaceViewHolder;
    }

    /**
     * Returns the holder for lensEngine
     *
     * @return holder
     */
    public SurfaceHolder getSurfaceViewHolder() {
        return surfaceViewHolder;
    }
}
