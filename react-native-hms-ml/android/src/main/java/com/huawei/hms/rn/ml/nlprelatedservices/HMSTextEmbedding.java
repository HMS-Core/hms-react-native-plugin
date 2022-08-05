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

package com.huawei.hms.rn.ml.nlprelatedservices;

import static com.huawei.hms.rn.ml.helpers.constants.HMSConstants.TEXT_EMBED_CONSTANTS;
import static com.huawei.hms.rn.ml.helpers.constants.HMSResults.UNKNOWN;

import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingAnalyzer;
import com.huawei.hms.mlsdk.textembedding.MLTextEmbeddingException;
import com.huawei.hms.rn.ml.HMSBase;
import com.huawei.hms.rn.ml.helpers.creators.HMSObjectCreator;
import com.huawei.hms.rn.ml.helpers.creators.HMSResultCreator;
import com.huawei.hms.rn.ml.helpers.utils.HMSUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

public class HMSTextEmbedding extends HMSBase {

    /**
     * Initializes module
     *
     * @param reactContext app context
     */
    public HMSTextEmbedding(ReactApplicationContext reactContext) {
        super(reactContext, HMSTextEmbedding.class.getSimpleName(), TEXT_EMBED_CONSTANTS);
    }

    /**
     * Queries the sentence vector asynchronously.
     *
     * @param sentence sentence to be embedded
     * @param language language code
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeSentenceVector(String sentence, String language, final Promise promise) {
        startMethodExecTimer("analyzeSentenceVector");
        MLTextEmbeddingAnalyzer textEmbeddingAnalyzer = HMSObjectCreator.getInstance()
            .createTextEmbeddingAnalyzer(language);
        textEmbeddingAnalyzer.analyseSentenceVector(sentence)
            .addOnSuccessListener(floats -> handleResult("analyzeSentenceVector",
                HMSResultCreator.getInstance().getTextEmbedVectorResult(floats), promise))
            .addOnFailureListener(e -> handleException("analyzeSentenceVector", e, promise));
    }

    /**
     * Asynchronously queries the similarity between two sentences. The similarity range is [–1,1].
     *
     * @param sentenceFirst first sentence
     * @param sentenceSecond second sentence
     * @param language language code
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeSentencesSimilarity(String sentenceFirst, String sentenceSecond, String language,
        final Promise promise) {
        startMethodExecTimer("analyzeSentencesSimilarity");
        MLTextEmbeddingAnalyzer textEmbeddingAnalyzer = HMSObjectCreator.getInstance()
            .createTextEmbeddingAnalyzer(language);
        textEmbeddingAnalyzer.analyseSentencesSimilarity(sentenceFirst, sentenceSecond)
            .addOnSuccessListener(
                aFloat -> handleResult("analyzeSentencesSimilarity", HMSResultCreator.getInstance().floatResult(aFloat),
                    promise))
            .addOnFailureListener(e -> handleException("analyzeSentencesSimilarity", e, promise));
    }

    /**
     * Asynchronously queries a specified number of similar words.
     *
     * @param word a word
     * @param similarNum Number of similar words. The value range is [1,30].
     * @param language language code
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeSimilarWords(String word, int similarNum, String language, final Promise promise) {
        startMethodExecTimer("analyzeSimilarWords");
        MLTextEmbeddingAnalyzer textEmbeddingAnalyzer = HMSObjectCreator.getInstance()
            .createTextEmbeddingAnalyzer(language);
        textEmbeddingAnalyzer.analyseSimilarWords(word, similarNum)
            .addOnSuccessListener(list -> handleResult("analyzeSimilarWords",
                HMSResultCreator.getInstance().analyseSimilarWordsResult(list), promise))
            .addOnFailureListener(e -> handleException("analyzeSimilarWords", e, promise));
    }

    /**
     * Queries the word vector asynchronously.
     *
     * @param word a word
     * @param language language code
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeWordVector(String word, String language, final Promise promise) {
        startMethodExecTimer("analyzeWordVector");
        MLTextEmbeddingAnalyzer textEmbeddingAnalyzer = HMSObjectCreator.getInstance()
            .createTextEmbeddingAnalyzer(language);
        textEmbeddingAnalyzer.analyseWordVector(word)
            .addOnSuccessListener(floats -> handleResult("analyzeWordVector",
                HMSResultCreator.getInstance().getTextEmbedVectorResult(floats), promise))
            .addOnFailureListener(e -> handleException("analyzeWordVector", e, promise));
    }

    /**
     * Asynchronously queries the similarity between two words. The similarity range is [–1,1].
     *
     * @param wordFirst first word
     * @param wordSecond second word
     * @param language language code
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeWordsSimilarity(String wordFirst, String wordSecond, String language, final Promise promise) {
        startMethodExecTimer("analyzeWordsSimilarity");
        MLTextEmbeddingAnalyzer textEmbeddingAnalyzer = HMSObjectCreator.getInstance()
            .createTextEmbeddingAnalyzer(language);
        textEmbeddingAnalyzer.analyseWordsSimilarity(wordFirst, wordSecond)
            .addOnSuccessListener(
                aFloat -> handleResult("analyzeWordsSimilarity", HMSResultCreator.getInstance().floatResult(aFloat),
                    promise))
            .addOnFailureListener(e -> handleException("analyzeWordsSimilarity", e, promise));
    }

    /**
     * Asynchronously queries dictionary version information.
     *
     * @param language language code
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void getVocabularyVersion(String language, final Promise promise) {
        startMethodExecTimer("getVocabularyVersion");
        MLTextEmbeddingAnalyzer textEmbeddingAnalyzer = HMSObjectCreator.getInstance()
            .createTextEmbeddingAnalyzer(language);
        textEmbeddingAnalyzer.getVocabularyVersion()
            .addOnSuccessListener(mlVocabularyVersion -> handleResult("getVocabularyVersion",
                HMSResultCreator.getInstance().vocabularyVersionResult(mlVocabularyVersion), promise))
            .addOnFailureListener(e -> handleException("getVocabularyVersion", e, promise));
    }

    /**
     * Asynchronously queries word vectors in batches. (The number of words ranges from 1 to 500.)
     *
     * @param words list of words
     * @param language language code
     * @param promise A Promise that resolves a result object
     */
    @ReactMethod
    public void analyzeWordVectorBatch(ReadableArray words, String language, final Promise promise) {
        startMethodExecTimer("analyzeWordVectorBatch");
        MLTextEmbeddingAnalyzer textEmbeddingAnalyzer = HMSObjectCreator.getInstance()
            .createTextEmbeddingAnalyzer(language);
        textEmbeddingAnalyzer.analyseWordVectorBatch(HMSUtils.getInstance().convertRaToStringSet(words))
            .addOnSuccessListener(stringMap -> handleResult("analyzeWordVectorBatch",
                HMSResultCreator.getInstance().vectorBatchResult(stringMap), promise))
            .addOnFailureListener(e -> handleException("analyzeWordVectorBatch", e, promise));
    }

    /**
     * Handles on failure exception and resolves promise
     *
     * @param methodName method name
     * @param e exception object
     * @param promise A Promise that resolves a result object
     */
    private void handleException(String methodName, Exception e, Promise promise) {
        if (e instanceof MLTextEmbeddingException) {
            MLTextEmbeddingException e1 = (MLTextEmbeddingException) e;
            handleResult(methodName, UNKNOWN.getStatusAndMessage(e1.getErrCode(), e1.getMessage()), promise);
        } else {
            handleResult(methodName, e, promise);
        }
    }
}
