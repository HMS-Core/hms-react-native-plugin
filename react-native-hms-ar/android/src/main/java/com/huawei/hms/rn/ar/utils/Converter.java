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

package com.huawei.hms.rn.ar.utils;

import static com.huawei.hms.plugin.ar.core.serializer.CommonSerializer.arCameraConfigToMap;
import static com.huawei.hms.plugin.ar.core.serializer.CommonSerializer.arCameraIntrinsicsToMap;
import static com.huawei.hms.plugin.ar.core.serializer.CommonSerializer.arSceneMeshToMap;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import com.huawei.hiar.ARCameraConfig;
import com.huawei.hiar.ARCameraIntrinsics;
import com.huawei.hiar.ARConfigBase;
import com.huawei.hiar.ARSceneMesh;
import com.huawei.hiar.ARTrackable;
import com.huawei.hms.plugin.ar.core.config.ColorRGBA;
import com.huawei.hms.plugin.ar.core.model.AugmentedImageDBModel;
import com.huawei.hms.plugin.ar.core.serializer.PluginARTrackableSerializer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Converter {
    private static String TAG = Converter.class.getSimpleName();

    public static boolean hasValidKey(ReadableMap rm, String key, ReadableType type) {
        return rm.hasKey(key) && rm.getType(key) == type;
    }

    public static boolean hasValidElement(ReadableArray ra, int index, ReadableType type) {
        return !ra.isNull(index) && ra.getType(index) == type;
    }

    public static ColorRGBA toColorRGBA(final ReadableMap rm) {
        int red = 0;
        int blue = 0;
        int green = 0;
        int alpha = 0;
        if (rm != null) {
            if (hasValidKey(rm, "red", ReadableType.Number)) {
                red = (int) rm.getDouble("red");
            }
            if (hasValidKey(rm, "blue", ReadableType.Number)) {
                blue = (int) rm.getDouble("blue");
            }
            if (hasValidKey(rm, "green", ReadableType.Number)) {
                green = (int) rm.getDouble("green");
            }
            if (hasValidKey(rm, "alpha", ReadableType.Number)) {
                alpha = (int) rm.getDouble("alpha");
            }
        }
        return new ColorRGBA(red, green, blue, alpha);
    }

    public static WritableMap arTrackableToWritableMap(List<ARTrackable> arTrackables) {
        for (ARTrackable arTrackable : arTrackables) {
            Map<String, Object> m = PluginARTrackableSerializer.serialize(arTrackable);
            return toWritableMap(m);
        }
        return null;
    }

    public static WritableMap arCameraConfigToWritableMap(ARCameraConfig cameraConfig) {
        Map<String, Object> m = arCameraConfigToMap(cameraConfig);
        return toWritableMap(m);
    }

    public static WritableMap arCameraIntrinsicsToWritableMap(ARCameraIntrinsics cameraIntrinsics) {
        Map<String, Object> m = arCameraIntrinsicsToMap(cameraIntrinsics);
        return toWritableMap(m);
    }

    public static WritableMap arSceneMeshToWritableMap(ARSceneMesh arSceneMesh) {
        Map<String, Object> m = arSceneMeshToMap(arSceneMesh);
        return toWritableMap(m);
    }

    public static int IntToLightEnum(int val) {
        switch (val) {
            // fall-through
            case ARConfigBase.LIGHT_MODE_NONE:
                ;
                // fall-through
            case ARConfigBase.LIGHT_MODE_AMBIENT_INTENSITY:
                ;
                // fall-through
            case ARConfigBase.LIGHT_MODE_ENVIRONMENT_LIGHTING:
                ;
                // fall-through
            case ARConfigBase.LIGHT_MODE_ENVIRONMENT_TEXTURE:
                ;
                // fall-through
            case ARConfigBase.LIGHT_MODE_ALL:
                return val;
            default:
                return ARConfigBase.LIGHT_MODE_NONE;
        }
    }

    public static ARConfigBase.FocusMode IntToFocusModeEnum(int val) {
        if (val == ARConfigBase.FocusMode.FIXED_FOCUS.ordinal()) {
            return ARConfigBase.FocusMode.FIXED_FOCUS;
        }
        return ARConfigBase.FocusMode.AUTO_FOCUS;
    }

    public static ARConfigBase.PowerMode IntToPowerModeEnum(int val) {
        if (val == ARConfigBase.PowerMode.NORMAL.ordinal()) {
            return ARConfigBase.PowerMode.NORMAL;
        } else if (val == ARConfigBase.PowerMode.POWER_SAVING.ordinal()) {
            return ARConfigBase.PowerMode.POWER_SAVING;
        } else if (val == ARConfigBase.PowerMode.ULTRA_POWER_SAVING.ordinal()) {
            return ARConfigBase.PowerMode.ULTRA_POWER_SAVING;
        } else {
            return ARConfigBase.PowerMode.PERFORMANCE_FIRST;
        }
    }

    public static ARConfigBase.UpdateMode IntToUpdateModeEnum(int val) {
        if (val == ARConfigBase.UpdateMode.BLOCKING.ordinal()) {
            return ARConfigBase.UpdateMode.BLOCKING;
        }
        return ARConfigBase.UpdateMode.LATEST_CAMERA_IMAGE;
    }

    public static ARConfigBase.PlaneFindingMode IntToPlaneFindingModeEnum(int val) {
        if (val == ARConfigBase.PlaneFindingMode.DISABLED.ordinal()) {
            return ARConfigBase.PlaneFindingMode.DISABLED;
        } else if (val == ARConfigBase.PlaneFindingMode.VERTICAL_ONLY.ordinal()) {
            return ARConfigBase.PlaneFindingMode.VERTICAL_ONLY;
        } else if (val == ARConfigBase.PlaneFindingMode.HORIZONTAL_ONLY.ordinal()) {
            return ARConfigBase.PlaneFindingMode.HORIZONTAL_ONLY;
        } else {
            return ARConfigBase.PlaneFindingMode.ENABLE;
        }
    }

    public static WritableMap toWritableMap(final Map<String, Object> map) {
        WritableNativeMap result = new WritableNativeMap();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                result.putNull(key);
            } else if (value instanceof Map) {
                //noinspection unchecked,rawtypes
                result.putMap(key, toWritableMap((Map) value));
            } else if (value instanceof List) {
                //noinspection unchecked,rawtypes
                result.putArray(key, toWritableArray((List) value));
            } else if (value instanceof float[]) {
                WritableArray wa = Arguments.fromArray(value);
                result.putArray(key, wa);
            } else if (value instanceof String[]) {
                WritableArray wa = Arguments.fromArray(value);
                result.putArray(key, wa);
            } else if (value instanceof int[]) {
                WritableArray wa = Arguments.fromArray(value);
                result.putArray(key, wa);
            } else if (value instanceof Boolean) {
                result.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                result.putInt(key, (Integer) value);
            } else if (value instanceof String) {
                result.putString(key, (String) value);
            } else if (value instanceof Double) {
                result.putDouble(key, (Double) value);
            } else if (value instanceof Float) {
                result.putDouble(key, (float) value);
            } else {
                Log.e(TAG, "Could not convert object " + value.toString());
            }
        }
        return result;
    }

    private static WritableArray toWritableArray(List<Object> array) {
        WritableNativeArray result = new WritableNativeArray();

        for (Object value : array) {
            if (value == null) {
                result.pushNull();
            } else if (value instanceof Map) {
                //noinspection unchecked,rawtypes
                result.pushMap(toWritableMap((Map) value));
            } else if (value instanceof List) {
                //noinspection unchecked,rawtypes
                result.pushArray(toWritableArray((List) value));
            } else if (value instanceof float[]) {
                WritableArray wa = Arguments.fromArray(value);
                result.pushArray(wa);
            } else if (value instanceof Boolean) {
                result.pushBoolean((Boolean) value);
            } else if (value instanceof Integer) {
                result.pushInt((Integer) value);
            } else if (value instanceof String) {
                result.pushString((String) value);
            } else if (value instanceof Double) {
                result.pushDouble((Double) value);
            } else if (value instanceof Float) {
                result.pushDouble((float) value);
            } else {
                Log.e(TAG, "Could not convert object " + value.toString());
            }
        }
        return result;
    }

    public static List<AugmentedImageDBModel> toAugmentedImageDBModelList(ReadableArray paramsAI) {
        AugmentedImageDBModel[] array = new AugmentedImageDBModel[paramsAI.size()];
        for (int i = 0; i < paramsAI.size(); i++) {
            ReadableMap item = paramsAI.getMap(i);
            AugmentedImageDBModel augmentedImageDBModel = new AugmentedImageDBModel();
            if (hasValidKey(item, "imgFileFromAsset", ReadableType.String)) {
                augmentedImageDBModel.setImgFileFromAsset(item.getString("imgFileFromAsset"));
            }
            if (hasValidKey(item, "widthInMeters", ReadableType.Number)) {
                augmentedImageDBModel.setWidthInMeters(item.getInt("widthInMeters"));
            }
            if (hasValidKey(item, "imgName", ReadableType.String)) {
                augmentedImageDBModel.setImgName(item.getString("imgName"));
            }
            array[i] = augmentedImageDBModel;
        }
        return Arrays.asList(array);
    }
}
