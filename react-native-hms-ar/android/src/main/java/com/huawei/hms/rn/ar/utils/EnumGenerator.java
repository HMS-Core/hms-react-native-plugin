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

import android.util.ArrayMap;

import com.huawei.hiar.ARBody;
import com.huawei.hiar.ARConfigBase;
import com.huawei.hiar.ARCoordinateSystemType;
import com.huawei.hiar.ARHand;
import com.huawei.hiar.ARPlane;
import com.huawei.hiar.ARTrackable;
import com.huawei.hiar.ARWorldTrackingConfig;

import java.util.HashMap;
import java.util.Map;

public class EnumGenerator {

    public static Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();
        constants.put("PlaneFindingMode", getPlaneFindingMode());
        constants.put("UpdateMode", getUpdateMode());
        constants.put("FocusMode", getFocusMode());
        constants.put("PowerMode", getPowerMode());
        constants.put("SemanticMode", getSemanticMode());
        constants.put("CameraLensFacing", getCameraLensFacing());
        constants.put("LightMode", getLightMode());
        constants.put("TrackingState", getTracingState());
        constants.put("HandType", getHandTypes());
        constants.put("HandSkeletonType", getHandSkeletonType());
        constants.put("BodySkeletonType", getBodySkeletonType());
        constants.put("PlaneLabel", getSemanticPlaneLabel());
        constants.put("PlaneType", getPlaneTypes());
        constants.put("CoordinateSystemType", getCoordinateSystemType());
        return constants;
    }

    public static Map<String, Object> getSemanticMode() {
        Map<String, Object> semanticMode = new ArrayMap<>();
        semanticMode.put("NONE", ARWorldTrackingConfig.SEMANTIC_NONE);
        semanticMode.put("PLANE", ARWorldTrackingConfig.SEMANTIC_PLANE);
        semanticMode.put("TARGET", ARWorldTrackingConfig.SEMANTIC_TARGET);
        semanticMode.put("ALL", (ARWorldTrackingConfig.SEMANTIC_PLANE | ARWorldTrackingConfig.SEMANTIC_TARGET));
        return semanticMode;
    }

    public static Map<String, Object> getPowerMode() {
        Map<String, Object> powerMode = new ArrayMap<>();
        powerMode.put("NORMAL", ARConfigBase.PowerMode.NORMAL.ordinal());
        powerMode.put("POWER_SAVING", ARConfigBase.PowerMode.POWER_SAVING.ordinal());
        powerMode.put("ULTRA_POWER_SAVING", ARConfigBase.PowerMode.ULTRA_POWER_SAVING.ordinal());
        powerMode.put("PERFORMANCE_FIRST", ARConfigBase.PowerMode.PERFORMANCE_FIRST.ordinal());
        return powerMode;
    }

    public static Map<String, Object> getFocusMode() {
        Map<String, Object> focusMode = new ArrayMap<>();
        focusMode.put("FIXED_FOCUS", ARConfigBase.FocusMode.FIXED_FOCUS.ordinal());
        focusMode.put("AUTO_FOCUS", ARConfigBase.FocusMode.AUTO_FOCUS.ordinal());
        return focusMode;
    }

    public static Map<String, Object> getUpdateMode() {
        Map<String, Object> updateMode = new ArrayMap<>();
        updateMode.put("BLOCKING", ARConfigBase.UpdateMode.BLOCKING.ordinal());
        updateMode.put("LATEST_CAMERA_IMAGE", ARConfigBase.UpdateMode.LATEST_CAMERA_IMAGE.ordinal());
        return updateMode;
    }

    public static Map<String, Object> getPlaneFindingMode() {
        Map<String, Object> planeFindingMode = new ArrayMap<>();
        planeFindingMode.put("DISABLED", ARConfigBase.PlaneFindingMode.DISABLED.ordinal());
        planeFindingMode.put("VERTICAL_ONLY", ARConfigBase.PlaneFindingMode.VERTICAL_ONLY.ordinal());
        planeFindingMode.put("HORIZONTAL_ONLY", ARConfigBase.PlaneFindingMode.HORIZONTAL_ONLY.ordinal());
        planeFindingMode.put("ENABLE", ARConfigBase.PlaneFindingMode.ENABLE.ordinal());
        return planeFindingMode;
    }

    public static Map<String, Object> getCameraLensFacing() {
        Map<String, Object> cameraLensFacing = new ArrayMap<>();
        cameraLensFacing.put("FRONT", ARConfigBase.CameraLensFacing.FRONT.ordinal());
        cameraLensFacing.put("REAR", ARConfigBase.CameraLensFacing.REAR.ordinal());
        return cameraLensFacing;
    }

    public static Map<String, Object> getLightMode() {
        Map<String, Object> lightMode = new ArrayMap<>();
        lightMode.put("NONE", ARConfigBase.LIGHT_MODE_NONE);
        lightMode.put("AMBIENT_INTENSITY", ARConfigBase.LIGHT_MODE_AMBIENT_INTENSITY);
        lightMode.put("ENVIRONMENT_LIGHTING", ARConfigBase.LIGHT_MODE_ENVIRONMENT_LIGHTING);
        lightMode.put("ENVIRONMENT_TEXTURE", ARConfigBase.LIGHT_MODE_ENVIRONMENT_TEXTURE);
        lightMode.put("ALL", ARConfigBase.LIGHT_MODE_ALL);
        return lightMode;
    }

    public static Map<String, Object> getTracingState() {
        Map<String, Object> trackingStates = new HashMap<>();
        trackingStates.put("PAUSED", ARTrackable.TrackingState.PAUSED.ordinal());
        trackingStates.put("TRACKING", ARTrackable.TrackingState.TRACKING.ordinal());
        trackingStates.put("STOPPED", ARTrackable.TrackingState.STOPPED.ordinal());
        trackingStates.put("UNKNOWN_STATE", ARTrackable.TrackingState.UNKNOWN_STATE.ordinal());
        return trackingStates;
    }

    public static Map<String, Object> getHandTypes() {
        Map<String, Object> handTypes = new HashMap<>();
        handTypes.put("AR_HAND_LEFT", ARHand.ARHandType.AR_HAND_LEFT.ordinal());
        handTypes.put("AR_HAND_RIGHT", ARHand.ARHandType.AR_HAND_RIGHT.ordinal());
        handTypes.put("AR_HAND_UNKNOWN", ARHand.ARHandType.AR_HAND_UNKNOWN.ordinal());
        return handTypes;
    }

    public static Map<String, Object> getHandSkeletonType() {
        Map<String, Object> handSkeletonTypes = new HashMap<>();
        handSkeletonTypes.put("INDEX_1", ARHand.ARHandSkeletonType.HANDSKELETON_INDEX_1.ordinal());
        handSkeletonTypes.put("INDEX_2", ARHand.ARHandSkeletonType.HANDSKELETON_INDEX_2.ordinal());
        handSkeletonTypes.put("INDEX_3", ARHand.ARHandSkeletonType.HANDSKELETON_INDEX_3.ordinal());
        handSkeletonTypes.put("INDEX_4", ARHand.ARHandSkeletonType.HANDSKELETON_INDEX_4.ordinal());
        handSkeletonTypes.put("LENGTH", ARHand.ARHandSkeletonType.HANDSKELETON_LENGTH.ordinal());
        handSkeletonTypes.put("MIDDLE_1", ARHand.ARHandSkeletonType.HANDSKELETON_MIDDLE_1.ordinal());
        handSkeletonTypes.put("MIDDLE_2", ARHand.ARHandSkeletonType.HANDSKELETON_MIDDLE_2.ordinal());
        handSkeletonTypes.put("MIDDLE_3", ARHand.ARHandSkeletonType.HANDSKELETON_MIDDLE_3.ordinal());
        handSkeletonTypes.put("MIDDLE_4", ARHand.ARHandSkeletonType.HANDSKELETON_MIDDLE_4.ordinal());
        handSkeletonTypes.put("PINKY_1", ARHand.ARHandSkeletonType.HANDSKELETON_PINKY_1.ordinal());
        handSkeletonTypes.put("PINKY_2", ARHand.ARHandSkeletonType.HANDSKELETON_PINKY_2.ordinal());
        handSkeletonTypes.put("PINKY_3", ARHand.ARHandSkeletonType.HANDSKELETON_PINKY_3.ordinal());
        handSkeletonTypes.put("PINKY_4", ARHand.ARHandSkeletonType.HANDSKELETON_PINKY_4.ordinal());
        handSkeletonTypes.put("RING_1", ARHand.ARHandSkeletonType.HANDSKELETON_RING_1.ordinal());
        handSkeletonTypes.put("RING_2", ARHand.ARHandSkeletonType.HANDSKELETON_RING_2.ordinal());
        handSkeletonTypes.put("RING_3", ARHand.ARHandSkeletonType.HANDSKELETON_RING_3.ordinal());
        handSkeletonTypes.put("RING_4", ARHand.ARHandSkeletonType.HANDSKELETON_RING_4.ordinal());
        handSkeletonTypes.put("ROOT", ARHand.ARHandSkeletonType.HANDSKELETON_ROOT.ordinal());
        handSkeletonTypes.put("THUMB_1", ARHand.ARHandSkeletonType.HANDSKELETON_THUMB_1.ordinal());
        handSkeletonTypes.put("THUMB_2", ARHand.ARHandSkeletonType.HANDSKELETON_THUMB_2.ordinal());
        handSkeletonTypes.put("THUMB_3", ARHand.ARHandSkeletonType.HANDSKELETON_THUMB_3.ordinal());
        handSkeletonTypes.put("THUMB_4", ARHand.ARHandSkeletonType.HANDSKELETON_THUMB_4.ordinal());
        handSkeletonTypes.put("UNKNOWN", ARHand.ARHandSkeletonType.HANDSKELETON_UNKNOWN.ordinal());
        return handSkeletonTypes;
    }

    public static Map<String, Object> getBodySkeletonType() {
        Map<String, Object> bodySkeletonTypes = new HashMap<>();
        bodySkeletonTypes.put("L_ANKLE", ARBody.ARBodySkeletonType.BodySkeleton_l_Ankle.ordinal());
        bodySkeletonTypes.put("L_EAR", ARBody.ARBodySkeletonType.BodySkeleton_l_ear.ordinal());
        bodySkeletonTypes.put("L_ELBOW", ARBody.ARBodySkeletonType.BodySkeleton_l_Elbow.ordinal());
        bodySkeletonTypes.put("L_EYE", ARBody.ARBodySkeletonType.BodySkeleton_l_eye.ordinal());
        bodySkeletonTypes.put("L_HIP", ARBody.ARBodySkeletonType.BodySkeleton_l_Hip.ordinal());
        bodySkeletonTypes.put("L_KNEE", ARBody.ARBodySkeletonType.BodySkeleton_l_Knee.ordinal());
        bodySkeletonTypes.put("L_SHO", ARBody.ARBodySkeletonType.BodySkeleton_l_Sho.ordinal());
        bodySkeletonTypes.put("L_TOE", ARBody.ARBodySkeletonType.BodySkeleton_l_toe.ordinal());
        bodySkeletonTypes.put("L_WRIST", ARBody.ARBodySkeletonType.BodySkeleton_l_Wrist.ordinal());
        bodySkeletonTypes.put("LENGTH", ARBody.ARBodySkeletonType.BodySkeleton_Length.ordinal());
        bodySkeletonTypes.put("NECK", ARBody.ARBodySkeletonType.BodySkeleton_Neck.ordinal());
        bodySkeletonTypes.put("NOSE", ARBody.ARBodySkeletonType.BodySkeleton_nose.ordinal());
        bodySkeletonTypes.put("SPINE", ARBody.ARBodySkeletonType.BodySkeleton_spine.ordinal());
        bodySkeletonTypes.put("UNKNOWN", ARBody.ARBodySkeletonType.BodySkeleton_Unknown.ordinal());
        bodySkeletonTypes.put("HEAD", ARBody.ARBodySkeletonType.BodySkeleton_Head.ordinal());
        bodySkeletonTypes.put("HIP_MID", ARBody.ARBodySkeletonType.BodySkeleton_Hip_mid.ordinal());
        bodySkeletonTypes.put("R_ANKLE", ARBody.ARBodySkeletonType.BodySkeleton_r_Ankle.ordinal());
        bodySkeletonTypes.put("R_EAR", ARBody.ARBodySkeletonType.BodySkeleton_r_ear.ordinal());
        bodySkeletonTypes.put("R_ELBOW", ARBody.ARBodySkeletonType.BodySkeleton_r_Elbow.ordinal());
        bodySkeletonTypes.put("R_EYE", ARBody.ARBodySkeletonType.BodySkeleton_r_eye.ordinal());
        bodySkeletonTypes.put("R_HIP", ARBody.ARBodySkeletonType.BodySkeleton_r_Hip.ordinal());
        bodySkeletonTypes.put("R_KNEE", ARBody.ARBodySkeletonType.BodySkeleton_r_Knee.ordinal());
        bodySkeletonTypes.put("R_SHO", ARBody.ARBodySkeletonType.BodySkeleton_r_Sho.ordinal());
        bodySkeletonTypes.put("R_TOE", ARBody.ARBodySkeletonType.BodySkeleton_r_toe.ordinal());
        bodySkeletonTypes.put("R_WRIST", ARBody.ARBodySkeletonType.BodySkeleton_r_Wrist.ordinal());
        return bodySkeletonTypes;
    }

    public static Map<String, Object> getSemanticPlaneLabel() {
        Map<String, Object> semanticPlaneLabel = new HashMap<>();
        semanticPlaneLabel.put("PLANE_CEILING", ARPlane.SemanticPlaneLabel.PLANE_CEILING.ordinal());
        semanticPlaneLabel.put("PLANE_FLOOR", ARPlane.SemanticPlaneLabel.PLANE_FLOOR.ordinal());
        semanticPlaneLabel.put("PLANE_OTHER", ARPlane.SemanticPlaneLabel.PLANE_OTHER.ordinal());
        semanticPlaneLabel.put("PLANE_SEAT", ARPlane.SemanticPlaneLabel.PLANE_SEAT.ordinal());
        semanticPlaneLabel.put("PLANE_TABLE", ARPlane.SemanticPlaneLabel.PLANE_TABLE.ordinal());
        semanticPlaneLabel.put("PLANE_WALL", ARPlane.SemanticPlaneLabel.PLANE_WALL.ordinal());
        semanticPlaneLabel.put("PLANE_DOOR", ARPlane.SemanticPlaneLabel.PLANE_DOOR.ordinal());
        semanticPlaneLabel.put("PLANE_WINDOW", ARPlane.SemanticPlaneLabel.PLANE_WINDOW.ordinal());
        semanticPlaneLabel.put("PLANE_BED", ARPlane.SemanticPlaneLabel.PLANE_BED.ordinal());
        return semanticPlaneLabel;
    }

    public static Map<String, Object> getPlaneTypes() {
        Map<String, Object> planeTypes = new HashMap<>();
        planeTypes.put("HORIZONTAL_DOWNWARD_FACING", ARPlane.PlaneType.HORIZONTAL_DOWNWARD_FACING.ordinal());
        planeTypes.put("VERTICAL_FACING", ARPlane.PlaneType.VERTICAL_FACING.ordinal());
        planeTypes.put("UNKNOWN_FACING", ARPlane.PlaneType.UNKNOWN_FACING.ordinal());
        planeTypes.put("HORIZONTAL_UPWARD_FACING", ARPlane.PlaneType.HORIZONTAL_UPWARD_FACING.ordinal());
        return planeTypes;
    }

    public static Map<String, Object> getCoordinateSystemType() {
        Map<String, Object> coordinateSystemTypes = new HashMap<>();
        coordinateSystemTypes.put("2D_IMAGE", ARCoordinateSystemType.COORDINATE_SYSTEM_TYPE_2D_IMAGE.ordinal());
        coordinateSystemTypes.put("3D_CAMERA", ARCoordinateSystemType.COORDINATE_SYSTEM_TYPE_3D_CAMERA.ordinal());
        coordinateSystemTypes.put("3D_SELF", ARCoordinateSystemType.COORDINATE_SYSTEM_TYPE_3D_SELF.ordinal());
        coordinateSystemTypes.put("3D_WORLD", ARCoordinateSystemType.COORDINATE_SYSTEM_TYPE_3D_WORLD.ordinal());
        coordinateSystemTypes.put("UNKNOWN", ARCoordinateSystemType.COORDINATE_SYSTEM_TYPE_UNKNOWN.ordinal());
        return coordinateSystemTypes;
    }
}
