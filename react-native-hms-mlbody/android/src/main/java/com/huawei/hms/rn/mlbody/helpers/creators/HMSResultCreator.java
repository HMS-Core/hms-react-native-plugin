/*
 * Copyright 2023-2024. Huawei Technologies Co., Ltd. All rights reserved.
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

package com.huawei.hms.rn.mlbody.helpers.creators;

import static com.huawei.hms.rn.mlbody.helpers.constants.HMSResults.SUCCESS;

import android.graphics.PointF;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.SparseArray;
import com.huawei.hms.common.size.Size;
import com.huawei.hms.mlsdk.common.MLPosition;
import com.huawei.hms.mlsdk.face.MLFace;
import com.huawei.hms.mlsdk.face.MLFaceEmotion;
import com.huawei.hms.mlsdk.face.MLFaceFeature;
import com.huawei.hms.mlsdk.face.MLFaceKeyPoint;
import com.huawei.hms.mlsdk.face.MLFaceShape;
import com.huawei.hms.mlsdk.face.face3d.ML3DFace;
import com.huawei.hms.mlsdk.faceverify.MLFaceTemplateResult;
import com.huawei.hms.mlsdk.faceverify.MLFaceVerificationResult;
import com.huawei.hms.mlsdk.gesture.MLGesture;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypoint;
import com.huawei.hms.mlsdk.handkeypoint.MLHandKeypoints;
import com.huawei.hms.mlsdk.skeleton.MLJoint;
import com.huawei.hms.mlsdk.skeleton.MLSkeleton;
import com.huawei.hms.rn.mlbody.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.List;

public class HMSResultCreator {
    private static volatile HMSResultCreator instance;

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
     * Face recognition result creator
     *
     * @param faces results
     * @return WritableMap
     */
    public WritableMap getFaceResult(List<MLFace> faces) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", getFaceAnalyzerResult(faces));
        return wm;
    }

    /**
     * Converts face analyze results to WritableArray
     *
     * @param faces analyze results
     * @return WritableArray
     */
    private WritableArray getFaceAnalyzerResult(List<MLFace> faces) {
        WritableArray array = Arguments.createArray();
        for (MLFace face : faces) {
            WritableMap map = Arguments.createMap();
            map.putArray("allPoints", getAllPoints(face.getAllPoints()));
            map.putMap("border", getBorders(face.getBorder()));
            map.putMap("coordinatePoints", getCoordinatePoints(face.getCoordinatePoint()));
            map.putMap("emotions", getEmotions(face.getEmotions()));
            map.putArray("faceKeyPointList", getFaceKeyPointList(face.getFaceKeyPoints()));
            map.putArray("faceShapeList", getShapeList(face.getFaceShapeList()));
            map.putMap("features", getFeatures(face.getFeatures()));
            map.putDouble("height", face.getHeight());
            map.putDouble("width", face.getWidth());
            map.putDouble("rotationAngleX", face.getRotationAngleX());
            map.putDouble("rotationAngleY", face.getRotationAngleY());
            map.putDouble("rotationAngleZ", face.getRotationAngleZ());
            map.putInt("tracingIdentity", face.getTracingIdentity());
            array.pushMap(map);
        }
        return array;
    }

    /**
     * Converts face features to WritableMap
     *
     * @param feature face features
     * @return WritableMap
     */
    private WritableMap getFeatures(MLFaceFeature feature) {
        WritableMap map = Arguments.createMap();
        map.putInt("age", feature.getAge());
        map.putDouble("hatProbability", feature.getHatProbability());
        map.putDouble("leftEyeOpenProbability", feature.getLeftEyeOpenProbability());
        map.putDouble("rightEyeOpenProbability", feature.getRightEyeOpenProbability());
        map.putDouble("moustacheProbability", feature.getMoustacheProbability());
        map.putDouble("sexProbability", feature.getSexProbability());
        map.putDouble("sunGlassProbability", feature.getSunGlassProbability());
        return map;
    }

    /**
     * Converts face positions to WritableArray
     *
     * @param positions positions
     * @return WritableArray
     */
    private WritableArray getAllPoints(List<MLPosition> positions) {
        WritableArray array = Arguments.createArray();
        for (MLPosition position : positions) {
            array.pushMap(getPosition(position));
        }
        return array;
    }

    /**
     * Converts face coordinate points to WritableMap
     *
     * @param pointF coordinate point
     * @return WritableMap
     */
    private WritableMap getCoordinatePoints(PointF pointF) {
        WritableMap map = Arguments.createMap();
        map.putDouble("length", pointF.length());
        map.putDouble("x", pointF.x);
        map.putDouble("y", pointF.y);
        return map;
    }

    /**
     * Converts face emotions to WritableMap
     *
     * @param emotion emotions
     * @return WritableMap
     */
    private WritableMap getEmotions(MLFaceEmotion emotion) {
        WritableMap map = Arguments.createMap();
        map.putDouble("angryProbability", emotion.getAngryProbability());
        map.putDouble("disgustProbability", emotion.getDisgustProbability());
        map.putDouble("fearProbability", emotion.getFearProbability());
        map.putDouble("neutralProbability", emotion.getNeutralProbability());
        map.putDouble("sadProbability", emotion.getSadProbability());
        map.putDouble("smilingProbability", emotion.getSmilingProbability());
        map.putDouble("surpriseProbability", emotion.getSurpriseProbability());
        return map;
    }

    /**
     * Converts face shapes to WritableArray
     *
     * @param shapes shape list
     * @return WritableArray
     */
    private WritableArray getShapeList(List<MLFaceShape> shapes) {
        WritableArray array = Arguments.createArray();
        for (MLFaceShape shape : shapes) {
            WritableMap map = Arguments.createMap();
            map.putInt("faceShapeType", shape.getFaceShapeType());
            map.putArray("points", getAllPoints(shape.getPoints()));
            array.pushMap(map);
        }
        return array;
    }

    /**
     * Convert face key points to WritableArray
     *
     * @param points key points
     * @return WritableArray
     */
    private WritableArray getFaceKeyPointList(List<MLFaceKeyPoint> points) {
        WritableArray array = Arguments.createArray();
        for (MLFaceKeyPoint point : points) {
            WritableMap map = Arguments.createMap();
            map.putMap("points", getPosition(point.getPoint()));
            map.putInt("type", point.getType());
            array.pushMap(map);
        }
        return array;
    }

    /**
     * Converts positions to WritableMap
     *
     * @param position positions
     * @return WritableMap
     */
    private WritableMap getPosition(MLPosition position) {
        WritableMap map = Arguments.createMap();
        map.putDouble("Y", position.getY() == null ? 0.0 : position.getY());
        map.putDouble("Z", position.getZ() == null ? 0.0 : position.getZ());
        map.putDouble("X", position.getX() == null ? 0.0 : position.getX());
        return map;
    }

    /**
     * 3D face recognition results
     *
     * @param faces results from analyzer
     * @return WritableMap
     */
    public WritableMap get3DFaceResult(List<ML3DFace> faces) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", get3DFaceResults(faces));
        return wm;
    }

    /**
     * 3D face recognition result creator
     *
     * @param faces result from analyzer
     * @return WritableArray
     */
    private WritableArray get3DFaceResults(List<ML3DFace> faces) {
        WritableArray wa = Arguments.createArray();
        for (ML3DFace face : faces) {
            WritableMap wm = Arguments.createMap();
            wm.putArray("allPoints", getAllPoints(face.get3DAllVertexs()));
            wm.putDouble("faceEulerY", face.get3DFaceEulerY());
            wm.putDouble("faceEulerX", face.get3DFaceEulerX());
            wm.putDouble("faceEulerZ", face.get3DFaceEulerZ());
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Converts skeleton results to WritableMap
     *
     * @param results skeleton recognition results
     * @return WritableMap
     */
    public WritableMap getSkeletonSyncResults(SparseArray<MLSkeleton> results) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", getSkeletonResults(HMSUtils.getInstance().convertSparseArrayToList(results)));
        return wm;
    }

    /**
     * Converts skeleton results to WritableMap
     *
     * @param results skeleton recognition results
     * @return WritableMap
     */
    public WritableMap getSkeletonAsyncResults(List<MLSkeleton> results) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", getSkeletonResults(results));
        return wm;
    }

    /**
     * Converts skeleton results to WritableArray
     *
     * @param results skeleton recognition results
     * @return WritableArray
     */
    private WritableArray getSkeletonResults(List<MLSkeleton> results) {
        WritableArray wa = Arguments.createArray();
        for (MLSkeleton skeleton : results) {
            WritableMap wm = Arguments.createMap();
            wm.putArray("joints", getJoints(skeleton.getJoints()));
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Converts joints to WritableArray
     *
     * @param results joints
     * @return WritableArray
     */
    private WritableArray getJoints(List<MLJoint> results) {
        WritableArray wa = Arguments.createArray();
        for (MLJoint joint : results) {
            WritableMap wm = Arguments.createMap();
            wm.putInt("type", joint.getType());
            wm.putDouble("pointX", joint.getPointX());
            wm.putDouble("pointY", joint.getPointY());
            wm.putDouble("score", joint.getScore());
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Converts hand key point results to WritableMap
     *
     * @param results hand key point results
     * @return WritableMap
     */
    public WritableMap getHandKeyPointResults(SparseArray<MLHandKeypoints> results) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", getHandKeyPoints(HMSUtils.getInstance().convertSparseArrayToList(results)));
        return wm;
    }

    /**
     * Converts hand key point results to WritableMap
     *
     * @param results hand key point results
     * @return WritableMap
     */
    public WritableMap getHandKeyPointResults(List<MLHandKeypoints> results) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", getHandKeyPoints(results));
        return wm;
    }

    /**
     * Converts gesture results to WritableMap
     *
     * @param results gesture results
     * @return WritableMap
     */
    public WritableMap getGestureResults(SparseArray<MLGesture> results) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", getGesture(HMSUtils.getInstance().convertSparseArrayToList(results)));
        return wm;
    }

    /**
     * Converts gesture results to WritableMap
     *
     * @param results gesture results
     * @return WritableMap
     */
    public WritableMap getGestureResults(List<MLGesture> results) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", getGesture(results));
        return wm;
    }

    /**
     * Get Gesture
     *
     * @param gestures Gesture result
     * @return WritableArray
     */
    private WritableArray getGesture(List<MLGesture> gestures) {
        WritableArray wa = Arguments.createArray();
        for (MLGesture gesture : gestures) {
            WritableMap temp = Arguments.createMap();
            temp.putInt("category", gesture.getCategory());
            temp.putDouble("score", gesture.getScore());
            temp.putMap("border", getBorders(gesture.getRect()));
            wa.pushMap(temp);
        }
        return wa;
    }

    /**
     * Converts face verification results to WritableMap
     *
     * @param results face verification results
     * @param cost face verification cost
     * @return WritableMap
     */
    public WritableMap getFaceVerificationCompareResults(SparseArray<MLFaceVerificationResult> results, int cost) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putMap("result", getFaceVerificationCompare(HMSUtils.getInstance().convertSparseArrayToList(results), cost));
        return wm;
    }

    /**
     * Converts face verification results to WritableMap
     *
     * @param results face verification results
     * @param cost face verification cost
     * @return WritableMap
     */
    public WritableMap getFaceVerificationCompareResults(List<MLFaceVerificationResult> results, int cost) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putMap("result", getFaceVerificationCompare(results, cost));
        return wm;
    }

    /**
     * Get Face Verification
     *
     * @param results Face verification result
     * @param cost Face verification cost
     * @return WritableMap
     */
    private WritableMap getFaceVerificationCompare(List<MLFaceVerificationResult> results, int cost) {
        WritableMap res = Arguments.createMap();
        WritableArray arry = Arguments.createArray();

        res.putInt("cost", cost);
        res.putBoolean("success", true);
        for (MLFaceVerificationResult template : results) {
            WritableMap item = Arguments.createMap();

            Rect location = template.getFaceInfo().getFaceRect();
            int id = template.getTemplateId();
            float similarity = template.getSimilarity();
            item.putString("face", location.toString());
            item.putInt("id", id);
            item.putDouble("similarity", similarity);

            arry.pushMap(item);
        }

        res.putArray("faces", arry);
        return res;
    }

    /**
     * Converts face verification results to WritableMap
     *
     * @param results face verification results
     * @param cost face verification cost
     * @return WritableMap
     */
    public WritableMap getFaceVerificationTemplateResult(List<MLFaceTemplateResult> results, int cost) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putMap("result", getFaceVerificationTemplate(results, cost));
        return wm;
    }

    private WritableMap getFaceVerificationTemplate(List<MLFaceTemplateResult> results, int cost) {
        WritableMap res = Arguments.createMap();
        WritableArray wa = Arguments.createArray();
        res.putInt("cost", cost);
        if (results.isEmpty()) {
            res.putBoolean("success", false);
        } else {
            res.putBoolean("success", true);
        }
        for (MLFaceTemplateResult result : results) {
            WritableMap temp = Arguments.createMap();
            int id = result.getTemplateId();
            Rect location = result.getFaceInfo().getFaceRect();
            temp.putString("face", location.toString());
            temp.putInt("id", id);
            wa.pushMap(temp);
        }
        res.putArray("faces", wa);
        return res;
    }

    /**
     * Converts list of MLHandKeypoints to WritableArray
     *
     * @param keypoints result of analyzer
     * @return WritableArray
     */
    private WritableArray getHandKeyPoints(List<MLHandKeypoints> keypoints) {
        WritableArray wa = Arguments.createArray();
        for (MLHandKeypoints keyPoints : keypoints) {
            WritableMap temp = Arguments.createMap();
            temp.putArray("handKeyPoints", getHandKeyPoint(keyPoints.getHandKeypoints()));
            temp.putDouble("score", keyPoints.getScore());
            temp.putMap("border", getBorders(keyPoints.getRect()));
            wa.pushMap(temp);
        }
        return wa;
    }

    /**
     * Converts hand key points to WritableArray
     *
     * @param keyPoints hand key points
     * @return WritableArray
     */
    private WritableArray getHandKeyPoint(List<MLHandKeypoint> keyPoints) {
        WritableArray wa = Arguments.createArray();
        for (MLHandKeypoint keyPoint : keyPoints) {
            WritableMap wm = Arguments.createMap();
            wm.putInt("type", keyPoint.getType());
            wm.putDouble("pointX", keyPoint.getPointX());
            wm.putDouble("pointY", keyPoint.getPointY());
            wm.putDouble("score", keyPoint.getScore());
            wa.pushMap(wm);
        }
        return wa;
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
