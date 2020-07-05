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

import React from 'react';
import { Text, View, TextInput, TouchableOpacity, ScrollView } from 'react-native';

import { styles } from './styles';

import { HmsTranslate } from 'react-native-hms-ml';

export default class Translate extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      text: '',
      result: '',
    };
  }


  componentDidMount() {
    this.create.call();
  }

  async create() {
    try {
      var result = await HmsTranslate.create({});
      console.log(result);

    } catch (e) {
      console.error(e);
    }
  }

  async translate(sentence) {
    try {
      if (sentence !== "") {
        var result = await HmsTranslate.translate(sentence);
        this.setState({ result });
        console.log(result);
      }

    } catch (e) {
      console.error(e);
    }
  }

  async stop(sentence) {
    try {
      var result = await HmsTranslate.stop();
      console.log(result);

    } catch (e) {
      console.error(e);
    }
  }


  translateAndStop = (values) => {
    this.translate(values);
    this.stop();
  }


  render() {
    return (
      <ScrollView style={styles.bg}>
        <TextInput
          style={styles.customEditBox2}
          placeholder="Type here to translate!"
          onChangeText={text => this.setState({ text: text })}
          multiline={true}
        />

        <TextInput
          style={styles.customEditBox2}
          value={this.state.result}
          placeholder="Result comes here after translate!"
          multiline={true}
          editable={false}
        />

        <View style={styles.buttonTts}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={() => this.translateAndStop(this.state.text)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Translate </Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    );
  }
}
