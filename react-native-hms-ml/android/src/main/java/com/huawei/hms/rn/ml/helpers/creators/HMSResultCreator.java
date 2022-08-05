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

package com.huawei.hms.rn.ml.helpers.creators;

import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.SUCCESS;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huawei.hms.common.size.Size;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureResult;
import com.huawei.hms.mlplugin.card.gcr.MLGcrCaptureResult;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureResult;
import com.huawei.hms.mlplugin.card.icr.vn.MLVnIcrCaptureResult;
import com.huawei.hms.mlsdk.aft.cloud.MLRemoteAftResult;
import com.huawei.hms.mlsdk.card.icr.MLIdCard;
import com.huawei.hms.mlsdk.classification.MLImageClassification;
import com.huawei.hms.mlsdk.common.MLCoordinate;
import com.huawei.hms.mlsdk.common.MLPosition;
import com.huawei.hms.mlsdk.custom.MLModelOutputs;
import com.huawei.hms.mlsdk.document.MLDocument;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewCorrectionResult;
import com.huawei.hms.mlsdk.dsc.MLDocumentSkewDetectResult;
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
import com.huawei.hms.mlsdk.imagesuperresolution.MLImageSuperResolutionResult;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentation;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmark;
import com.huawei.hms.mlsdk.langdetect.MLDetectedLang;
import com.huawei.hms.mlsdk.model.download.MLRemoteModel;
import com.huawei.hms.mlsdk.objects.MLObject;
import com.huawei.hms.mlsdk.productvisionsearch.MLProductVisionSearch;
import com.huawei.hms.mlsdk.productvisionsearch.MLVisionSearchProduct;
import com.huawei.hms.mlsdk.productvisionsearch.MLVisionSearchProductImage;
import com.huawei.hms.mlsdk.scd.MLSceneDetection;
import com.huawei.hms.mlsdk.skeleton.MLJoint;
import com.huawei.hms.mlsdk.skeleton.MLSkeleton;
import com.huawei.hms.mlsdk.speechrtt.MLSpeechRealTimeTranscriptionResult;
import com.huawei.hms.mlsdk.text.MLText;
import com.huawei.hms.mlsdk.text.TextLanguage;
import com.huawei.hms.mlsdk.textembedding.MLVocabularyVersion;
import com.huawei.hms.mlsdk.textimagesuperresolution.MLTextImageSuperResolution;
import com.huawei.hms.mlsdk.tts.MLTtsSpeaker;
import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
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
     * Converts aft results to WritableArray
     *
     * @param results aft results
     * @return WritableArray
     */
    public WritableArray getAftResult(List<MLRemoteAftResult.Segment> results) {
        WritableArray array = Arguments.createArray();
        for (MLRemoteAftResult.Segment segment : results) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putInt("startTime", segment.getStartTime());
            writableMap.putInt("endTime", segment.getEndTime());
            writableMap.putString("text", segment.getText());
            array.pushMap(writableMap);
        }
        return array;
    }

    /**
     * Convert detected language list to WritableMap
     *
     * @param languageList detected language list
     * @return WritableMap
     */
    public WritableMap getLangDetectionResult(List<MLDetectedLang> languageList) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray array = Arguments.createArray();
        for (MLDetectedLang lang : languageList) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("languageCode", lang.getLangCode());
            writableMap.putDouble("probability", lang.getProbability());
            array.pushMap(writableMap);
        }
        wm.putArray("result", array);
        return wm;
    }

    /**
     * Converts speech rtt result to WritableMap
     *
     * @param rttResults result list
     * @return WritableMap
     */
    public WritableMap getRttResult(List<MLSpeechRealTimeTranscriptionResult> rttResults) {
        WritableMap wm = Arguments.createMap();
        WritableArray array = Arguments.createArray();
        for (MLSpeechRealTimeTranscriptionResult result : rttResults) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("startTime", result.startTime);
            writableMap.putString("endTime", result.endTime);
            writableMap.putString("text", result.text);
            array.pushMap(writableMap);
        }
        wm.putArray("result", array);
        return wm;
    }

    /**
     * Converts speaker list to WritableMap
     *
     * @param speakers speaker list
     * @return WritableMap
     */
    public WritableMap getSpeakers(List<MLTtsSpeaker> speakers) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray array = Arguments.createArray();
        for (MLTtsSpeaker speaker : speakers) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("language", speaker.getLanguage());
            writableMap.putString("name", speaker.getName());
            array.pushMap(writableMap);
        }
        wm.putArray("result", array);
        return wm;
    }

    /**
     * returns vector methods result
     *
     * @param floats method result
     * @return WritableMap
     */
    public WritableMap getTextEmbedVectorResult(Float[] floats) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", HMSUtils.getInstance().convertFloatArrToWa(floats));
        return wm;
    }

    /**
     * returns analyseSimilarWords method result
     *
     * @param strings method result
     * @return WritableMap
     */
    public WritableMap analyseSimilarWordsResult(List<String> strings) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", HMSUtils.getInstance().convertStringListIntoWa(strings));
        return wm;
    }

    /**
     * returns vocabularyVersion method result
     *
     * @param mlVocabularyVersion method result
     * @return WritableMap
     */
    public WritableMap vocabularyVersionResult(MLVocabularyVersion mlVocabularyVersion) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableMap temp = Arguments.createMap();
        temp.putString("dictionaryDimension", mlVocabularyVersion.getDictionaryDimension());
        temp.putString("dictionarySize", mlVocabularyVersion.getDictionarySize());
        temp.putString("versionNo", mlVocabularyVersion.getVersionNo());
        wm.putMap("result", temp);
        return wm;
    }

    /**
     * returns WordVectorBatch method result
     *
     * @param batch method result
     * @return WritableMap
     */
    public WritableMap vectorBatchResult(Map<String, Float[]> batch) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableMap temp = Arguments.createMap();
        for (Map.Entry<String, Float[]> entry : batch.entrySet()) {
            temp.putArray(entry.getKey(), HMSUtils.getInstance().convertFloatArrToWa(entry.getValue()));
        }
        wm.putMap("result", temp);
        return wm;
    }

    /**
     * returns CustomModel exec method result
     *
     * @param mlModelOutputs method result
     * @return WritableMap
     */
    public WritableMap customModelResult(MLModelOutputs mlModelOutputs) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putArray("result", HMSUtils.getInstance().convert2DFloatArrToWa(mlModelOutputs.getOutput(0)));
        return wm;
    }

    /**
     * Returns getModels method result
     *
     * @param models remote models
     * @return WritableMap
     */
    public WritableMap getModels(Set<MLRemoteModel> models) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();
        for (MLRemoteModel s : models) {
            wa.pushString(s.getModelName());
        }
        wm.putArray("result", wa);
        return wm;
    }

    /**
     * Recent model file method result
     *
     * @param file file object
     * @return WritableMap
     */
    public WritableMap getFilePathResult(File file) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putString("result", file.getPath());
        return wm;
    }

    /**
     * Converts sdk text recognition result to WritableMap
     *
     * @param textRecognitionResult result to be converted
     * @return WritableMap
     */
    public WritableMap getTextRecognitionResult(MLText textRecognitionResult) {
        WritableMap result = SUCCESS.getStatusAndMessage();
        result.putString("completeResult", textRecognitionResult.getStringValue());
        result.putArray("blocks", getTextBlocks(textRecognitionResult.getBlocks()));
        return result;
    }

    /**
     * Converts sdk text recognition result to WritableMap
     *
     * @param blocks result to be converted
     * @return WritableMap
     */
    public WritableMap getTextRecognitionResult(SparseArray<MLText.Block> blocks) {
        WritableMap result = SUCCESS.getStatusAndMessage();
        result.putArray("blocks", getTextBlocks(blocks));
        return result;
    }

    /**
     * Converts block array to WritableArray
     *
     * @param blocks blocks to be converted
     * @return WritableArray
     */
    private WritableArray getTextBlocks(SparseArray<MLText.Block> blocks) {
        return getTextBlocks(HMSUtils.getInstance().convertSparseArrayToList(blocks));
    }

    /**
     * Converts text block list to WritableArray
     *
     * @param mlTextBlockList block list
     * @return WritableArray
     */
    private WritableArray getTextBlocks(List<MLText.Block> mlTextBlockList) {
        WritableArray wa = Arguments.createArray();
        for (MLText.Block block : mlTextBlockList) {
            WritableMap wm = Arguments.createMap();
            wm.putString("stringValue", block.getStringValue());
            wm.putDouble("possibility", block.getPossibility() == null ? 0.0 : block.getPossibility());
            wm.putMap("border", getBorders(block.getBorder()));
            wm.putArray("vertexes", getPoints(Arrays.asList(block.getVertexes())));
            wm.putArray("lines", getLines(block.getContents()));
            wm.putArray("languageList", getLanguageList(block.getLanguageList()));
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Converts text line list into WritableArray
     *
     * @param mlTextTextLineList textlines
     * @return WritableArray
     */
    private WritableArray getLines(List<MLText.TextLine> mlTextTextLineList) {
        WritableArray wa = Arguments.createArray();
        for (MLText.TextLine line : mlTextTextLineList) {
            WritableMap wm = Arguments.createMap();
            wm.putString("stringValue", line.getStringValue());
            wm.putMap("border", getBorders(line.getBorder()));
            wm.putArray("vertexes", getPoints(Arrays.asList(line.getVertexes())));
            wm.putArray("words", getWords(line.getContents()));
            wm.putDouble("rotatingDegree", line.getRotatingDegree());
            wm.putBoolean("isVertical", line.isVertical());
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Converts word list into WritableArray
     *
     * @param mlWordList word list
     * @return WritableArray
     */
    private WritableArray getWords(List<MLText.Word> mlWordList) {
        WritableArray wa = Arguments.createArray();
        for (MLText.Word word : mlWordList) {
            WritableMap wm = Arguments.createMap();
            wm.putString("stringValue", word.getStringValue());
            wm.putMap("border", getBorders(word.getBorder()));
            wm.putArray("vertexes", getPoints(Arrays.asList(word.getVertexes())));
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Converts document result to WritableMap
     *
     * @param mlDocument document result
     * @return WritableMap
     */
    public WritableMap getDocumentRecognitionResult(MLDocument mlDocument) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putString("completeResult", mlDocument.getStringValue());
        wm.putArray("blocks", getDocumentBlocks(mlDocument.getBlocks()));
        return wm;
    }

    /**
     * Converts document blocks to WritableArray
     *
     * @param mlDocumentBlockList block list
     * @return WritableArray
     */
    private WritableArray getDocumentBlocks(List<MLDocument.Block> mlDocumentBlockList) {
        WritableArray wa = Arguments.createArray();
        for (MLDocument.Block block : mlDocumentBlockList) {
            WritableMap wm = Arguments.createMap();
            wm.putString("stringValue", block.getStringValue());
            wm.putArray("sections", getSections(block.getSections()));
            wm.putMap("border", getBorders(block.getBorder()));
            wm.putMap("interval", getInterval(block.getInterval()));
            wm.putArray("languageList", getLanguageList(block.getLanguageList()));
            wm.putDouble("possibility", block.getPossibility() == null ? 0.0 : block.getPossibility());
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Converts document sections to WritableArray
     *
     * @param mlDocumentSectionList section list
     * @return WritableArray
     */
    private WritableArray getSections(List<MLDocument.Section> mlDocumentSectionList) {
        WritableArray wa = Arguments.createArray();
        for (MLDocument.Section value : mlDocumentSectionList) {
            WritableMap wm = Arguments.createMap();
            wm.putString("stringValue", value.getStringValue());
            wm.putMap("border", getBorders(value.getBorder()));
            wm.putArray("lineList", getLineList(value.getLineList()));
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Converts document interval to WritableMap
     *
     * @param mlDocumentInterval interval result
     * @return WritableMap
     */
    private WritableMap getInterval(MLDocument.Interval mlDocumentInterval) {
        WritableMap wm = Arguments.createMap();
        wm.putInt("intervalType", mlDocumentInterval.getIntervalType());
        wm.putBoolean("isTextFollowed", mlDocumentInterval.isTextFollowed());
        return wm;
    }

    /**
     * Converts document line list to WritableArray
     *
     * @param list line result list
     * @return WritableArray
     */
    private WritableArray getLineList(List<MLDocument.Line> list) {
        WritableArray wa = Arguments.createArray();
        for (MLDocument.Line line : list) {
            WritableMap wm = Arguments.createMap();
            wm.putString("stringValue", line.getStringValue());
            wm.putMap("border", getBorders(line.getBorder()));
            wm.putArray("points", getPoints(line.getPoints()));
            wm.putArray("wordList", getDocumentWords(line.getWordList()));
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Converts document word list to WritableArray
     *
     * @param mlDocumentWordList word result list
     * @return WritableArray
     */
    private WritableArray getDocumentWords(List<MLDocument.Word> mlDocumentWordList) {
        WritableArray wa = Arguments.createArray();
        for (MLDocument.Word word : mlDocumentWordList) {
            WritableMap wm = Arguments.createMap();
            wm.putString("stringValue", word.getStringValue());
            wm.putMap("border", getBorders(word.getBorder()));
            wm.putArray("characterList", getCharacterList(word.getCharacterList()));
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Converts document character list to WritableArray
     *
     * @param mlDocumentCharList character result list
     * @return WritableArray
     */
    private WritableArray getCharacterList(List<MLDocument.Character> mlDocumentCharList) {
        WritableArray wa = Arguments.createArray();
        for (MLDocument.Character character : mlDocumentCharList) {
            WritableMap wm = Arguments.createMap();
            wm.putString("stringValue", character.getStringValue());
            wm.putMap("border", getBorders(character.getBorder()));
            wa.pushMap(wm);
        }
        return wa;
    }

    /**
     * Converts point list to WritableArray
     *
     * @param pointList list to be converted
     * @return WritableArray
     */
    private WritableArray getPoints(List<Point> pointList) {
        WritableArray writableArray = Arguments.createArray();
        for (Point point : pointList) {
            writableArray.pushMap(getPoint(point));
        }
        return writableArray;
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
     * Converts text language list to WritableArray
     *
     * @param textLanguageList text language list
     * @return WritableArray
     */
    private WritableArray getLanguageList(List<TextLanguage> textLanguageList) {
        WritableArray writableArray = Arguments.createArray();
        for (TextLanguage lang : textLanguageList) {
            writableArray.pushString(lang.getLanguage());
        }
        return writableArray;
    }

    /**
     * Converts recognition successs to WritableMap
     *
     * @param mlBcrCaptureResult bcr result
     * @return WritableMap
     */
    public WritableMap getBankCardRecognitionSuccessResults(MLBcrCaptureResult mlBcrCaptureResult) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putInt("errorCode", mlBcrCaptureResult.getErrorCode());
        wm.putString("expire", mlBcrCaptureResult.getExpire());
        wm.putString("issuer", mlBcrCaptureResult.getIssuer());
        wm.putString("number", mlBcrCaptureResult.getNumber());
        wm.putString("organization", mlBcrCaptureResult.getOrganization());
        wm.putString("type", mlBcrCaptureResult.getType());
        return wm;
    }

    /**
     * Converts recognition result to WritableMap
     *
     * @param mlGcrCaptureResult recognition result
     * @return WritableMap
     */
    public WritableMap getGeneralCardRecognitionSuccessResult(MLGcrCaptureResult mlGcrCaptureResult) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putString("stringValue", mlGcrCaptureResult.text.getStringValue());
        wm.putArray("textBlocks", getTextBlocks(mlGcrCaptureResult.text.getBlocks()));
        return wm;
    }

    /**
     * Converts JsonObject to WritableMap
     *
     * @param jsonObject json result
     * @return WritableMap
     * @throws JSONException inner methods throws
     */
    public WritableMap getFormRecognitionResult(JsonObject jsonObject) throws JSONException {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        wm.putMap("result", HMSUtils.getInstance().convertJsonToWritableMap(new JSONObject(jsonObject.toString())));
        return wm;
    }

    /**
     * Converts sparse array of JsonObject to WritableMap
     *
     * @param sparseArray contains JsonObjects
     * @return WritableMap
     * @throws JSONException convert method throws it
     */
    public WritableMap getSyncFormRecognitionResult(SparseArray<JsonObject> sparseArray) throws JSONException {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableArray wa = Arguments.createArray();

        for (int i = 0; i < sparseArray.size(); i++) {
            wa.pushMap(HMSUtils.getInstance().convertJsonToWritableMap(new JSONObject(sparseArray.get(i).toString())));
        }
        wm.putArray("result", wa);
        return wm;
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
     * Converts vietnam card result to WritableMap
     *
     * @param idCardResult vietnam card result
     * @return WritableMap
     */
    public WritableMap getVNFormatIdCardResult(MLVnIcrCaptureResult idCardResult) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableMap res = Arguments.createMap();

        res.putString("name", idCardResult.getName());
        res.putString("sex", idCardResult.getSex());
        res.putString("birthday", idCardResult.getBirthday());
        res.putString("idNum", idCardResult.getIdNum());

        wm.putMap("result", res);
        return wm;
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
     * Converts id card result to WritableMap
     *
     * @param idCardResult id card result
     * @param isFront is front side id card
     * @return WritableMap
     */
    public WritableMap getFormatIdCardResult(MLCnIcrCaptureResult idCardResult, boolean isFront) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableMap res = Arguments.createMap();
        if (isFront) {
            res.putString("name", idCardResult.name);
            res.putString("sex", idCardResult.sex);
            res.putString("idNum", idCardResult.idNum);
            res.putString("birtday", idCardResult.birthday);
            res.putString("nation", idCardResult.nation);
            res.putString("address", idCardResult.address);
        } else {
            res.putString("validDate", idCardResult.validDate);
            res.putString("authority", idCardResult.authority);
        }
        wm.putMap("result", res);
        return wm;
    }

    public WritableMap getICRResult(MLIdCard idCardResult, boolean isFront) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableMap res = Arguments.createMap();
        if (isFront) {
            res.putString("name", idCardResult.name);
            res.putString("sex", idCardResult.sex);
            res.putString("idNum", idCardResult.idNum);
            res.putString("birtday", idCardResult.birthday);
            res.putString("nation", idCardResult.nation);
            res.putString("address", idCardResult.address);
        } else {
            res.putString("validDate", idCardResult.validDate);
            res.putString("authority", idCardResult.authority);
        }
        wm.putMap("result", res);
        return wm;
    }

    /**
     * converts image result to WritableMap
     *
     * @param string result
     * @param isFront is front side id card
     * @return WritableMap
     */
    public WritableMap getIDCardImage(String string, boolean isFront) {
        WritableMap wm = SUCCESS.getStatusAndMessage();
        WritableMap res = Arguments.createMap();
        res.putString("image", TextUtils.isEmpty(string) ? "" : string);
        res.putBoolean("isFront", isFront);
        wm.putMap("result", res);
        return wm;
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
