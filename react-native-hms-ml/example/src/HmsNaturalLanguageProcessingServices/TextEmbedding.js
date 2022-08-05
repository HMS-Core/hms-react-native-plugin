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

import React from 'react';
import {
  Text,
  View,
  TextInput,
  TouchableOpacity,
  ScrollView
} from 'react-native';
import { styles } from '../Styles';
import { HMSApplication, HMSTextEmbedding } from '@hmscore/react-native-hms-ml';

const initialState = {
  isSentencevector: false,
  isSentenceSimilarity: false,
  isAnalyzeSimilarWords: false,
  isAnalyzeWordVector: false,
  isAnalyzeWordSimilarity: false,
  isAnalyzeWordVectorBatch: false,
  isGetVocabularyVersion: false,
  sentence: '',
  sentenceSecond: '',
  word: '',
  wordSecond: '',
  result: '',
};

export default class TextEmbedding extends React.Component {

  constructor(props) {
    super(props);
    this.state = initialState;
  }

  componentDidMount() { }

  componentWillUnmount() { }

  reset = () => {
    this.setState(initialState);
  }

  parseResult = (result) => {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
      this.setState({
        result: result.result.toString()
      });
    }
    else {
      this.setState({ result: result.message });
    }
  }

  async analyzeSentenceVector() {
    try {
      var result = await HMSTextEmbedding.analyzeSentenceVector(this.state.sentence, HMSTextEmbedding.LANGUAGE_EN);
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async analyzeSentencesSimilarity() {
    try {
      var result = await HMSTextEmbedding.analyzeSentencesSimilarity(this.state.sentence, this.state.sentenceSecond, HMSTextEmbedding.LANGUAGE_EN);
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async analyzeSimilarWords() {
    try {
      var result = await HMSTextEmbedding.analyzeSimilarWords(this.state.word, 10, HMSTextEmbedding.LANGUAGE_EN);
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async analyzeWordVector() {
    try {
      var result = await HMSTextEmbedding.analyzeWordVector(this.state.word, HMSTextEmbedding.LANGUAGE_EN);
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async analyzeWordsSimilarity() {
    try {
      var result = await HMSTextEmbedding.analyzeWordsSimilarity(this.state.word, this.state.wordSecond, HMSTextEmbedding.LANGUAGE_EN);
      this.parseResult(result);
    } catch (e) {
      console.log(e);
    }
  }

  async analyzeWordVectorBatch() {
    try {
      var result = await HMSTextEmbedding.analyzeWordVectorBatch(['hello', 'huawei', 'phone'], HMSTextEmbedding.LANGUAGE_EN);
      console.log(result);
      this.setState({
        result: "See Console log for result"
      });
    } catch (e) {
      console.log(e);
    }
  }

  async getVocabularyVersion() {
    try {
      var result = await HMSTextEmbedding.getVocabularyVersion(HMSTextEmbedding.LANGUAGE_EN);
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({
          result: "Dimension :" + result.result.dictionaryDimension + "\n" +
            "Size :" + result.result.dictionarySize + " \n" +
            "Version No :" + result.result.versionNo
        });
      }
      else {
        this.setState({ result: result.message });
      }
    } catch (e) {
      console.log(e);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <Text style={styles.h1}>Choose Method</Text>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => { this.reset(); this.setState({ isSentencevector: true }) }}>
            <Text style={styles.startButtonLabel}> Analyze Sentence Vector </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => { this.reset(); this.setState({ isSentenceSimilarity: true }) }}>
            <Text style={styles.startButtonLabel}> Analyze Sentences Similarity </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => { this.reset(); this.setState({ isAnalyzeSimilarWords: true }) }}>
            <Text style={styles.startButtonLabel}> Analyze Similar Words </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => { this.reset(); this.setState({ isAnalyzeWordVector: true }) }}>
            <Text style={styles.startButtonLabel}> Analyze Word Vector </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => { this.reset(); this.setState({ isAnalyzeWordSimilarity: true }) }}>
            <Text style={styles.startButtonLabel}> Analyze Words Similarity </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => { this.reset(); this.setState({ isAnalyzeWordVectorBatch: true }) }}>
            <Text style={styles.startButtonLabel}> Analyze Word Vector Batch </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => { this.reset(); this.setState({ isGetVocabularyVersion: true }) }}>
            <Text style={styles.startButtonLabel}> Get Vocabulary Version </Text>
          </TouchableOpacity>
        </View>

        {this.state.isSentencevector ?
          <View>
            <TextInput
              style={styles.customInput}
              onChangeText={text => this.setState({ sentence: text })}
              placeholder="Sentence"
              multiline={false}
              editable={true}
            />

            <TextInput
              style={styles.customEditBox2}
              value={this.state.result}
              placeholder="Result"
              multiline={true}
              editable={false}
            />

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={() => this.analyzeSentenceVector()}
              >
                <Text style={styles.startButtonLabel}> START </Text>
              </TouchableOpacity>
            </View>
          </View>
          :
          <View></View>
        }

        {this.state.isSentenceSimilarity ?
          <View>
            <TextInput
              style={styles.customInput}
              onChangeText={text => this.setState({ sentence: text.trim() })}
              placeholder="Sentence"
              multiline={false}
              editable={true}
            />

            <TextInput
              style={styles.customInput}
              onChangeText={text => this.setState({ sentenceSecond: text.trim() })}
              placeholder="Second Sentence"
              multiline={false}
              editable={true}
            />

            <TextInput
              style={styles.customEditBox2}
              value={this.state.result}
              placeholder="Result"
              multiline={true}
              editable={false}
            />

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={() => this.analyzeSentencesSimilarity()}
              >
                <Text style={styles.startButtonLabel}> START </Text>
              </TouchableOpacity>
            </View>
          </View>
          :
          <View></View>
        }

        {this.state.isAnalyzeSimilarWords ?
          <View>
            <TextInput
              style={styles.customInput}
              onChangeText={text => this.setState({ word: text.trim() })}
              placeholder="Your Word"
              multiline={false}
              editable={true}
            />

            <TextInput
              style={styles.customEditBox2}
              value={this.state.result}
              placeholder="Result"
              multiline={true}
              editable={false}
            />

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={() => this.analyzeSimilarWords()}
              >
                <Text style={styles.startButtonLabel}> START </Text>
              </TouchableOpacity>
            </View>
          </View>
          :
          <View></View>
        }

        {this.state.isAnalyzeWordVector ?
          <View>
            <TextInput
              style={styles.customInput}
              onChangeText={text => this.setState({ word: text.trim() })}
              placeholder="Sentence"
              multiline={false}
              editable={true}
            />

            <TextInput
              style={styles.customEditBox2}
              value={this.state.result}
              placeholder="Result"
              multiline={true}
              editable={false}
            />

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={() => this.analyzeWordVector()}
              >
                <Text style={styles.startButtonLabel}> START </Text>
              </TouchableOpacity>
            </View>
          </View>
          :
          <View></View>
        }

        {this.state.isAnalyzeWordSimilarity ?
          <View>
            <TextInput
              style={styles.customInput}
              onChangeText={text => this.setState({ word: text.trim() })}
              placeholder="Your Word"
              multiline={false}
              editable={true}
            />

            <TextInput
              style={styles.customInput}
              onChangeText={text => this.setState({ wordSecond: text.trim() })}
              placeholder="Your Second Word"
              multiline={false}
              editable={true}
            />

            <TextInput
              style={styles.customEditBox2}
              value={this.state.result}
              placeholder="Result"
              multiline={true}
              editable={false}
            />

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={() => this.analyzeWordsSimilarity()}
              >
                <Text style={styles.startButtonLabel}> START </Text>
              </TouchableOpacity>
            </View>
          </View>
          :
          <View></View>
        }

        {this.state.isAnalyzeWordVectorBatch ?
          <View>
            <TextInput
              style={styles.customInput}
              placeholder="['hello','huawei','phone']"
              multiline={false}
              editable={false}
            />

            <TextInput
              style={styles.customEditBox2}
              value={this.state.result}
              placeholder="Result"
              multiline={true}
              editable={false}
            />

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={() => this.analyzeWordVectorBatch()}
              >
                <Text style={styles.startButtonLabel}> START </Text>
              </TouchableOpacity>
            </View>
          </View>
          :
          <View></View>
        }

        {this.state.isGetVocabularyVersion ?
          <View>
            <TextInput
              style={styles.customEditBox2}
              value={this.state.result}
              placeholder="Result"
              multiline={true}
              editable={false}
            />

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={() => this.getVocabularyVersion()}
              >
                <Text style={styles.startButtonLabel}> START </Text>
              </TouchableOpacity>
            </View>
          </View>
          :
          <View></View>
        }
      </ScrollView >
    );
  }
}
