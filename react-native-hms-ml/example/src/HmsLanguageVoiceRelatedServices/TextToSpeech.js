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
  TouchableOpacity,
  NativeEventEmitter,
  ToastAndroid,
  ScrollView
} from 'react-native';
import { styles } from '../Styles';
import { HMSTextToSpeech, HMSApplication } from '@hmscore/react-native-hms-ml';

export default class TextToSpeech extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      text: '',
      value: '',
      volume: 50,
    };
  }

  hungryWolfStory = 'Once, a wolf was very hungry. It looked for food here and there. But it could not get any. At last it found a loaf of bread and piece of meat in the hole of a tree. \
  The hungry wolf squeezed into the hole. It ate all the food. It was a woodcutter lunch. He was on his way back to the tree to have lunch. But he saw there was no food in the hole, instead, a wolf.';

  tonqueTwister = 'Peter Piper picked a peck of pickled peppers \
  A peck of pickled peppers Peter Piper picked\
  If Peter Piper picked a peck of pickled peppers\
  Where’s the peck of pickled peppers Peter Piper picked?';

  componentDidMount() {

    this.createEngine();

    this.eventEmitter = new NativeEventEmitter(HMSTextToSpeech);

    this.eventEmitter.addListener(HMSTextToSpeech.TTS_ON_ERROR, (event) => {
      console.log(event);
    });

    this.eventEmitter.addListener(HMSTextToSpeech.TTS_ON_WARN, (event) => {
      console.log(event);
    });

    this.eventEmitter.addListener(HMSTextToSpeech.TTS_ON_RANGE_START, (event) => {
      console.log(event);
    });

    this.eventEmitter.addListener(HMSTextToSpeech.TTS_ON_AUDIO_AVAILABLE, (event) => {
      console.log(event);
    });

    this.eventEmitter.addListener(HMSTextToSpeech.TTS_ON_EVENT, (event) => {
      console.log(event);
    });

  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSTextToSpeech.TTS_ON_ERROR);
    this.eventEmitter.removeAllListeners(HMSTextToSpeech.TTS_ON_WARN);
    this.eventEmitter.removeAllListeners(HMSTextToSpeech.TTS_ON_RANGE_START);
    this.eventEmitter.removeAllListeners(HMSTextToSpeech.TTS_ON_AUDIO_AVAILABLE);
    this.eventEmitter.removeAllListeners(HMSTextToSpeech.TTS_ON_EVENT);
    this.shutdown();
  }

  async shutdown() {
    try {
      var result = await HMSTextToSpeech.shutdown();
      this.resultMessage(result, "Engine Resources Clear");
    } catch (e) {
      console.log(e);
    }
  }

  async createEngine() {
    try {
      var result = await HMSTextToSpeech.createEngine({
        "volume": 1.0,
        "speed": 1.0,
        "language": HMSTextToSpeech.TTS_EN_US,
        "person": HMSTextToSpeech.TTS_SPEAKER_FEMALE_EN,
        "synthesizeMode": HMSTextToSpeech.TTS_ONLINE_MODE
      });
      this.resultMessage(result, "Create Engine");
    } catch (e) {
      console.log(e);
    }
  }

  async updateConfiguration() {
    try {
      var result = await HMSTextToSpeech.updateConfig({
        "volume": 1.0,
        "speed": 1.0,
        "language": HMSTextToSpeech.TTS_EN_US,
        "person": HMSTextToSpeech.TTS_SPEAKER_MALE_EN,
        "synthesizeMode": HMSTextToSpeech.TTS_ONLINE_MODE
      });
      this.resultMessage(result, "Update Config");
    } catch (e) {
      console.log(e);
    }
  }

  async speak(word) {
    try {
      var result = await HMSTextToSpeech.speak(word, HMSTextToSpeech.QUEUE_FLUSH);
      this.resultMessage(result, "Speak");
    } catch (e) {
      console.log(e);
    }
  }

  async resume() {
    try {
      var result = await HMSTextToSpeech.resume();
      this.resultMessage(result, "Resume");
    } catch (e) {
      console.log(e);
    }
  }

  async volumeUp() {
    try {
      this.setState((prevState) => {
        return {volume: prevState.volume + 10}
      }, () => {
        console.log(this.state.volume);
        
      })
      
      var result = await HMSTextToSpeech.setPlayerVolume(this.state.volume)
      this.resultMessage(result, "volumeUp");
    } catch (e) {
      console.log(e);
    }
  }
  
  async volumeDown() {
    try {
      this.setState((prevState) => {
        return {volume: prevState.volume - 10}
      }, () => {console.log(this.state.volume)})

      var result = await HMSTextToSpeech.setPlayerVolume(this.state.volume)
      this.resultMessage(result, "volumeDown");
    } catch (e) {
      console.log(e);
    }
  }

  async pause() {
    try {
      var result = await HMSTextToSpeech.pause();
      this.resultMessage(result, "Pause");
    } catch (e) {
      console.log(e);
    }
  }

  async getLanguages() {
    try {
      var result = await HMSTextToSpeech.getLanguages();
      this.resultMessage(result, "Languages");
    } catch (e) {
      console.log(e);
    }
  }

  async getSpeaker() {
    try {
      var result = await HMSTextToSpeech.getSpeaker(HMSTextToSpeech.TTS_EN_US);
      this.resultMessage(result, "Speaker English");
    } catch (e) {
      console.log(e);
    }
  }

  async isLanguageAvailable() {
    try {
      var result = await HMSTextToSpeech.isLanguageAvailable(HMSTextToSpeech.TTS_EN_US);
      this.resultMessage(result, "English isAvailable ?");
    } catch (e) {
      console.log(e);
    }
  }

  async getSpeakers() {
    try {
      var result = await HMSTextToSpeech.getSpeakers();
      this.resultMessage(result, "Speaker List");
    } catch (e) {
      console.log(e);
    }
  }

  async stop() {
    try {
      var result = await HMSTextToSpeech.stop();
      this.resultMessage(result, "Stop");
    } catch (e) {
      console.log(e);
    }
  }

  resultMessage = (result, mes) => {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
      ToastAndroid.showWithGravity(mes, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
    else {
      ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View style={{ width: '95%', alignSelf: 'center', alignContent: 'center' }}>

          <Text style={styles.h1}>Click and Listen Text</Text>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.speak("Hello World")}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Hello World</Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.speak("How are you")}
              underlayColor="#fff">

              <Text style={styles.buttonText}>How are you ?</Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.speak("Welcome to Text to Speech Application")}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Welcome to Text to Speech Application</Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.speak("Tap to me ! Lets talk !")}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Tap to me ! Lets talk !</Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.speak("How can I get to Taksim ?")}
              underlayColor="#fff">
              <Text style={styles.buttonText}>How can I get to Taksim ?</Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.speak("I will be there in 2 hours")}
              underlayColor="#fff">
              <Text style={styles.buttonText}>I will be there in 2 hours</Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.speak(this.hungryWolfStory)}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Listen Hungry Wolf Story </Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.speak(this.tonqueTwister)}
              underlayColor="#fff">
              <Text style={styles.buttonText}>Listen Tonque Twister</Text>
            </TouchableOpacity>
          </View>

          <Text style={styles.h1}>Speech Commands</Text>

          <View style={{ marginTop: 10 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.pause()}
              underlayColor="#fff">
              <Text style={styles.buttonText}> Pause Speech </Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.stop()}
              underlayColor="#fff">
              <Text style={styles.buttonText}> Stop Speech </Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.resume()}
              underlayColor="#fff">
              <Text style={styles.buttonText}> Resume Speech </Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.volumeUp()}
              underlayColor="#fff">
              <Text style={styles.buttonText}> Volume Up </Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.volumeDown()}
              underlayColor="#fff">
              <Text style={styles.buttonText}> Volume Down </Text>
            </TouchableOpacity>
          </View>

          <View style={{ marginTop: 5 }}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.updateConfiguration()}
              underlayColor="#fff">
              <Text style={styles.buttonText}> Switch To Male Sound </Text>
            </TouchableOpacity>
          </View>
        </View>
      </ScrollView>
    );
  }
}
