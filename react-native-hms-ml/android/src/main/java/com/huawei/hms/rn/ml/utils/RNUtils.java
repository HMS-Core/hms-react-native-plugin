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

package com.huawei.hms.rn.ml.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.graphics.Rect;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.document.MLDocument;
import com.huawei.hms.mlsdk.text.MLText;
import com.huawei.hms.mlsdk.text.TextLanguage;
import com.huawei.hms.rn.ml.card.HmsCardRecognitionManager;
import com.huawei.hms.rn.ml.classification.HmsClassificationManager;
import com.huawei.hms.rn.ml.composite.HmsCompositAnalyzerManager;
import com.huawei.hms.rn.ml.face.HmsFaceRecognitionManager;
import com.huawei.hms.rn.ml.imseg.HmsImageSegmentationManager;
import com.huawei.hms.rn.ml.object.HmsObjectRecognitionManager;
import com.huawei.hms.rn.ml.text.HmsTextRecognitionLocalManager;
import com.huawei.hms.rn.ml.text.HmsTextRecognitionRemoteManager;


public class RNUtils {
    private static final String TAG = RNUtils.class.getSimpleName();

    public static List<String> readableArrayIntoStringList(ReadableArray readableArray) {
        if (readableArray == null) {
            readableArray = Arguments.createArray();
        }
        List<String> arrList = new ArrayList<>();
        for (int i = 0; i < readableArray.size(); i++) {
            arrList.add(readableArray.getString(i));
        }
        return arrList;
    }

    public static WritableArray stringListIntoWritableArray(List<String> list) {
        WritableArray writableArray = Arguments.createArray();
        for (String value : list) {
            writableArray.pushString(value);
        }
        return writableArray;
    }

    public static WritableArray documentBlockIntoWritableArray(List<MLDocument.Block> mlDocumentBlockList) {
        WritableArray writableArray = Arguments.createArray();
        for (int i = 0; i < mlDocumentBlockList.size(); i++) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("stringValue", mlDocumentBlockList.get(i).getStringValue());
            writableMap.putArray("sections", documentSectionIntoWritableArray(mlDocumentBlockList.get(i).getSections()));
            writableMap.putMap("border", borders(mlDocumentBlockList.get(i).getBorder()));
            writableMap.putArray("languages", languages(mlDocumentBlockList.get(i).getLanguageList()));
            writableMap.putMap("interval", intervalIntoWritableMap(mlDocumentBlockList.get(i).getInterval()));
            writableArray.pushMap(writableMap);
        }
        return writableArray;
    }

    public static WritableMap intervalIntoWritableMap(MLDocument.Interval mlDocumentInterval) {
        WritableMap writableMap = Arguments.createMap();
        writableMap.putInt("intervalType", mlDocumentInterval.getIntervalType());
        writableMap.putBoolean("isTextFollowed", mlDocumentInterval.isTextFollowed());
        return writableMap;
    }

    public static WritableArray documentSectionIntoWritableArray(List<MLDocument.Section> mlDocumentSectionList) {
        WritableArray writableArray = Arguments.createArray();
        for (MLDocument.Section value : mlDocumentSectionList) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("stringValue", value.getStringValue());
            writableMap.putMap("border", borders(value.getBorder()));
            writableMap.putMap("interval", intervalIntoWritableMap(value.getInterval()));
            writableMap.putArray("languages", languages(value.getLanguageList()));
            writableMap.putArray("lines", lineListIntoWritableArray(value.getLineList()));
            writableMap.putArray("words", documentWordsIntoArray(value.getWordList()));
            writableArray.pushMap(writableMap);
        }
        return writableArray;
    }

    public static WritableArray blockListIntoWritableArray(List<MLText.Block> mlTextBlockList) {
        WritableArray writableArray = Arguments.createArray();

        for (MLText.Block block : mlTextBlockList) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("stringValue", block.getStringValue());
            writableMap.putString("language", block.getLanguage());
            writableMap.putMap("border", borders(block.getBorder()));
            writableMap.putArray("contents", textLineIntoWritableMap(block.getContents()));
            writableMap.putArray("vertexes", points(block.getVertexes()));
            writableMap.putArray("languageList", languages(block.getLanguageList()));
            writableArray.pushMap(writableMap);
        }
        return writableArray;
    }

    public static WritableArray lineListIntoWritableArray(List<MLDocument.Line> list) {
        WritableArray writableArray = Arguments.createArray();

        for (MLDocument.Line line : list) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("stringValue", line.getStringValue());
            writableMap.putArray("languages", languages(line.getLanguageList()));
            writableMap.putMap("border", borders(line.getBorder()));
            writableMap.putMap("interval", intervalIntoWritableMap(line.getInterval()));
            writableMap.putArray("points", documentPoints(line.getPoints()));
            writableMap.putArray("words", documentWordsIntoArray(line.getWordList()));
            writableArray.pushMap(writableMap);
        }
        return writableArray;
    }

    public static WritableArray documentPoints(List<Point> pointList) {
        WritableArray writableArray = Arguments.createArray();
        for (Point point : pointList) {
            WritableMap map = Arguments.createMap();
            map.putInt("y", point.y);
            map.putInt("x", point.x);
            writableArray.pushMap(map);
        }
        return writableArray;
    }

    public static WritableMap borders(Rect border) {
        WritableMap writableMap = Arguments.createMap();
        writableMap.putInt("centerX", border.centerX());
        writableMap.putInt("centerY", border.centerY());
        writableMap.putDouble("exactCenterX", border.exactCenterX());
        writableMap.putDouble("exactCenterY", border.exactCenterY());
        return writableMap;
    }

    public static WritableArray points(Point[] points) {
        WritableArray writableArray = Arguments.createArray();
        for (Point point : points) {
            WritableMap map = Arguments.createMap();
            map.putInt("y", point.y);
            map.putInt("x", point.x);
            writableArray.pushMap(map);
        }
        return writableArray;
    }

    public static WritableArray languages(List<TextLanguage> textLanguageList) {
        WritableArray writableArray = Arguments.createArray();
        for (TextLanguage lang : textLanguageList) {
            writableArray.pushString(lang.getLanguage());
        }
        return writableArray;
    }

    public static WritableArray textLineIntoWritableMap(List<MLText.TextLine> mlTextTextLineList) {
        WritableArray writableArray = Arguments.createArray();
        for (MLText.TextLine line : mlTextTextLineList) {
            WritableMap map = Arguments.createMap();
            map.putString("stringValue", line.getStringValue());
            map.putMap("borders", borders(line.getBorder()));
            map.putArray("vertexes", points(line.getVertexes()));
            map.putArray("words", wordsIntoMap(line.getContents()));
            map.putArray("languages", languages(line.getLanguageList()));
            writableArray.pushMap(map);
        }
        return writableArray;
    }

    public static WritableArray wordsIntoMap(List<MLText.Word> mlTextWordList) {
        WritableArray writableArray = Arguments.createArray();
        for (int i = 0; i < mlTextWordList.size(); i++) {
            WritableMap map = Arguments.createMap();
            map.putString(Integer.toString(i), mlTextWordList.get(i).getStringValue());
            map.putMap("borders", borders(mlTextWordList.get(i).getBorder()));
            map.putArray("vertexes", points(mlTextWordList.get(i).getVertexes()));
            map.putArray("languages", languages(mlTextWordList.get(i).getLanguageList()));
            writableArray.pushMap(map);
        }
        return writableArray;
    }

    public static WritableArray documentWordsIntoArray(List<MLDocument.Word> mlDocumentWordList) {
        WritableArray writableArray = Arguments.createArray();
        for (int i = 0; i < mlDocumentWordList.size(); i++) {
            WritableMap writableMap = Arguments.createMap();
            writableMap.putString("stringValue", mlDocumentWordList.get(i).getStringValue());
            writableMap.putMap("border", borders(mlDocumentWordList.get(i).getBorder()));
            writableMap.putMap("vertexes", intervalIntoWritableMap(mlDocumentWordList.get(i).getInterval()));
            writableMap.putArray("languages", languages(mlDocumentWordList.get(i).getLanguageList()));
            writableMap.putMap("interval", intervalIntoWritableMap(mlDocumentWordList.get(i).getInterval()));
            writableArray.pushMap(writableMap);
        }
        return writableArray;
    }

    public static boolean hasValidKey(ReadableMap readableMap, String key, ReadableType type) {
        return readableMap.hasKey(key) && readableMap.getType(key) == type;
    }

    public static MLAnalyzer findAnalyzerByTag(int analyzerTag) {
        MLAnalyzer analyzer;
        switch (analyzerTag) {
            case 1:
                analyzer = HmsCardRecognitionManager.getInstance().getMlBcrAnalyzer();
                break;
            case 2:
                analyzer = HmsCardRecognitionManager.getInstance().getAnalyzer();
                break;
            case 3:
                analyzer = HmsClassificationManager.getInstance().getMlImageClassificationAnalyzer();
                break;
            case 4:
                analyzer = HmsClassificationManager.getInstance().getMlRemoteImageClassificationAnalyzer();
                break;
            case 5:
                analyzer = HmsFaceRecognitionManager.getInstance().getMlFaceAnalyzer();
                break;
            case 6:
                analyzer = HmsImageSegmentationManager.getInstance().getAnalyzer();
                break;
            case 7:
                analyzer = HmsObjectRecognitionManager.getInstance().getAnalyzer();
                break;
            case 8:
                analyzer = HmsTextRecognitionLocalManager.getInstance().getMlTextAnalyzer();
                break;
            case 9:
                analyzer = HmsTextRecognitionRemoteManager.getInstance().getMlTextAnalyzer();
                break;
            case 10:
                analyzer = HmsCompositAnalyzerManager.getInstance().getMlCompositeAnalyzer();
                break;
            default:
                analyzer = HmsTextRecognitionLocalManager.getInstance().getMlTextAnalyzer();
                break;
        }
        return analyzer;
    }
}
