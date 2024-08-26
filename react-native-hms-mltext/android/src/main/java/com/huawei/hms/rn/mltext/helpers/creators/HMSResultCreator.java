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

package com.huawei.hms.rn.mltext.helpers.creators;

import static com.huawei.hms.rn.mltext.helpers.constants.HMSResults.SUCCESS;

import android.graphics.Point;
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
import com.huawei.hms.mlsdk.card.icr.MLIdCard;
import com.huawei.hms.mlsdk.document.MLDocument;
import com.huawei.hms.mlsdk.text.MLText;
import com.huawei.hms.mlsdk.text.TextLanguage;
import com.huawei.hms.mlsdk.textembedding.MLVocabularyVersion;
import com.huawei.hms.rn.mltext.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
            res.putString("birthday", idCardResult.birthday);
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
            res.putString("birthday", idCardResult.birthday);
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
