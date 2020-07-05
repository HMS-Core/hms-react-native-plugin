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
import { Text, View, TouchableOpacity, Image, NativeEventEmitter } from 'react-native';

import { styles } from './styles';
import { ScrollView } from 'react-native-gesture-handler';

import { HmsTextToSpeech } from 'react-native-hms-ml';

export default class TTS extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      text: '',
      value: '',
    };
  }

  hungryWolfStory = 'Once, a wolf was very hungry. It looked for food here and there. But it couldn not get any. At last it found a loaf of bread and piece of meat in the hole of a tree. \
  The hungry wolf squeezed into the hole. It ate all the food. It was a woodcutter lunch. He was on his way back to the tree to have lunch. But he saw there was no food in the hole, instead, a wolf.';

  tonqueTwister = 'Peter Piper picked a peck of pickled peppers \
  A peck of pickled peppers Peter Piper picked\
  If Peter Piper picked a peck of pickled peppers\
  Where’s the peck of pickled peppers Peter Piper picked?';

  componentDidMount() {
    const eventEmitter = new NativeEventEmitter(HmsTextToSpeech);
    this.eventListener = eventEmitter.addListener('ttsCallback', (event) => {
      console.log(event)
    });

    this.configuration.call();
    this.engineCreation.call();
    this.callback.call();
  }

  componentWillUnmount() {
    this.eventListener.remove();
    this.stop.call();
  }

  async configuration() {
    try {
      var result = await HmsTextToSpeech.configure({
        "volume": 1.0,
        "speed": 1.0,
        "language": HmsTextToSpeech.TTS_EN_US,
        "person": HmsTextToSpeech.TTS_SPEAKER_FEMALE_EN
      });

      console.log(result);

    } catch (e) {
      console.error(e);
    }
  }

  async engineCreation() {
    try {
      var result = await HmsTextToSpeech.createEngine();
      console.log(result);

    } catch (e) {
      console.error(e);
    }
  }

  async callback() {
    try {
      var result = await HmsTextToSpeech.setTtsCallback();
      console.log(result);

    } catch (e) {
      console.error(e);
    }
  }


  async speak(word) {
    try {
      var result = await HmsTextToSpeech.speak(word, HmsTextToSpeech.QUEUE_FLUSH);
      console.log(result);

    } catch (e) {
      console.error(e);
    }
  }

  async stop() {
    try {
      var result = await HmsTextToSpeech.stop();
      console.log(result);

    } catch (e) {
      console.error(e);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View>
          <TouchableOpacity
            style={styles.buttonRadius}
            onPress={() => this.speak("Hello World")}
            underlayColor="#fff">
            <Text style={styles.buttonText}>Hello World</Text>
          </TouchableOpacity>
        </View>

        <View>
          <TouchableOpacity
            style={styles.buttonRadius}
            onPress={() => this.speak("How are you")}
            underlayColor="#fff">

            <Text style={styles.buttonText}>How are you ?</Text>
          </TouchableOpacity>
        </View>

        <View>
          <TouchableOpacity
            style={styles.buttonRadius}
            onPress={() => this.speak("Welcome to Text to Speech Application")}
            underlayColor="#fff">
            <Text style={styles.buttonText}>Welcome to Text to Speech Application</Text>
          </TouchableOpacity>
        </View>

        <View>
          <TouchableOpacity
            style={styles.buttonRadius}
            onPress={() => this.speak("Tap to me ! Lets talk !")}
            underlayColor="#fff">
            <Text style={styles.buttonText}>Tap to me ! Lets talk !</Text>
          </TouchableOpacity>
        </View>

        <View>
          <TouchableOpacity
            style={styles.buttonRadius}
            onPress={() => this.speak("How can I get to Taksim ?")}
            underlayColor="#fff">
            <Text style={styles.buttonText}>How can I get to Taksim ?</Text>
          </TouchableOpacity>
        </View>

        <View>
          <TouchableOpacity
            style={styles.buttonRadius}
            onPress={() => this.speak("I will be there in 2 hours")}
            underlayColor="#fff">
            <Text style={styles.buttonText}>I will be there in 2 hours</Text>
          </TouchableOpacity>
        </View>

        <View>
          <TouchableOpacity
            style={styles.buttonRadius}
            onPress={() => this.speak(this.hungryWolfStory)}
            underlayColor="#fff">
            <Text style={styles.buttonText}>Listen Hungry Wolf Story </Text>
          </TouchableOpacity>
        </View>

        <View>
          <TouchableOpacity
            style={styles.buttonRadius}
            onPress={() => this.speak(this.tonqueTwister)}
            underlayColor="#fff">
            <Text style={styles.buttonText}>Listen Tonque Twister</Text>
          </TouchableOpacity>
        </View>


      </ScrollView>
    );
  }
}
