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
  ScrollView,
  TextInput,
  TouchableOpacity,
  Image,
  NativeEventEmitter,
  ToastAndroid
} from 'react-native';
import { HMSAft, HMSApplication } from '@hmscore/react-native-hms-ml';
import { styles } from '../Styles';
import DocumentPicker from 'react-native-document-picker';

export default class AudioFileTranscription extends React.Component {

  componentDidMount() {
    this.init()
      .then(() => this.setAftListener());

    this.eventEmitter = new NativeEventEmitter(HMSAft);

    this.eventEmitter.addListener(HMSAft.AFT_ON_EVENT, (event) => {
      console.log(event);
    });

    this.eventEmitter.addListener(HMSAft.AFT_ON_RESULT, (event) => {
      console.log(event);
      if (event.isComplete == true) {
        this.setState({ result: event.text });
      }
      else {
        this.setState({ result: "Transcription not completed" });
      }
    });

    this.eventEmitter.addListener(HMSAft.AFT_ON_INIT_COMPLETE, (event) => {
      console.log(event);
      this.setState({ taskId: event.taskId });
    });

    this.eventEmitter.addListener(HMSAft.AFT_ON_UPLOAD_PROGRESS, (event) => {
      console.log(event);
      this.setState({ progress: event.progress });
    });

    this.eventEmitter.addListener(HMSAft.AFT_ON_ERROR, (event) => {
      console.log(event);
      this.setState({ result: event.error.toString() + " " + event.message });
    });

  }

  componentWillUnmount() {
    this.eventEmitter.removeAllListeners(HMSAft.AFT_ON_EVENT);
    this.eventEmitter.removeAllListeners(HMSAft.AFT_ON_RESULT);
    this.eventEmitter.removeAllListeners(HMSAft.AFT_ON_INIT_COMPLETE);
    this.eventEmitter.removeAllListeners(HMSAft.AFT_ON_UPLOAD_PROGRESS);
    this.eventEmitter.removeAllListeners(HMSAft.AFT_ON_ERROR);
    this.close();
  }

  constructor(props) {
    super(props);
    this.state = {
      audioUri: '',
      audioType: '',
      audioName: '',
      audioSize: 0,
      result: '',
      taskId: '',
      progress: 0,
      isLongRecognize: false,
    };
  }

  getAftConfig = () => {
    return {
      languageCode: HMSAft.LANGUAGE_EN_US,
      enablePunctuation: true,
      enableWordTimeOffset: false,
      enableSentenceTimeOffset: false
    }
  }

  async pickAudioFile() {
    try {
      const res = await DocumentPicker.pick({
        type: [DocumentPicker.types.audio],
      });
      console.log(res);
      this.setState({ audioUri: res.uri, audioType: res.type, audioName: res.name, audioSize: res.size, isLongRecognize: res.size > 4000000 ? true : false });
    } catch (err) {
      if (DocumentPicker.isCancel(err)) {
        console.log(err);
      }
    }
  }

  async init() {
    try {
      var result = await HMSAft.init();
      this.methodResult(result, "Engine Init");
    } catch (e) {
      console.log(e);
    }
  }

  async close() {
    try {
      var result = await HMSAft.close();
      this.methodResult(result, "Engine Closed");
    } catch (e) {
      console.log(e);
    }
  }

  async destroyTask() {
    try {
      var result = await HMSAft.destroyTask(this.state.taskId);
      this.methodResult(result, "Destroy Task");
    } catch (e) {
      console.log(e);
    }
  }

  async getLongAftResult() {
    try {
      var result = await HMSAft.getLongAftResult(this.state.taskId);
      this.methodResult(result, "Get Long Aft Result");
    } catch (e) {
      console.log(e);
    }
  }

  async pauseTask() {
    try {
      var result = await HMSAft.pauseTask(this.state.taskId);
      this.methodResult(result, "Pause Task");
    } catch (e) {
      console.log(e);
    }
  }

  async startTask() {
    try {
      var result = await HMSAft.startTask(this.state.taskId);
      this.methodResult(result, "Start Long Recognition Task");
    } catch (e) {
      console.log(e);
    }
  }

  async getShortAftLanguages() {
    try {
      var result = await HMSAft.getShortAftLanguages();
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({
          result: result.result.toString()
        });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  async getLongAftLanguages() {
    try {
      var result = await HMSAft.getLongAftLanguages();
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({
          result: result.result.toString()
        });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  async setAftListener() {
    try {
      var result = await HMSAft.setAftListener();
      this.methodResult(result, "Aft listener set");
    } catch (e) {
      console.log(e);
    }
  }

  async shortRecognize() {
    try {
      var result = await HMSAft.shortRecognize(this.state.audioUri, this.getAftConfig());
      this.methodResult(result, "Short Recognize Started :" + result.result);
    } catch (e) {
      console.log(e);
    }
  }

  async longRecognize() {
    try {
      var result = await HMSAft.longRecognize(this.state.audioUri, this.getAftConfig());
      this.methodResult(result, "Long Recognize Started :" + result.result);
    } catch (e) {
      console.log(e);
    }
  }

  methodResult = (result, mes) => {
    console.log(result);
    if (result.status == HMSApplication.SUCCESS) {
      ToastAndroid.showWithGravity(mes, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
    else {
      ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
    }
  }

  startAnalyze = () => {
    this.setState({
      result: 'Recognizing ... ',
    }, () => {
      this.state.isLongRecognize ? this.longRecognize() : this.shortRecognize();
    });
  }

  render() {
    return (
      <ScrollView style={styles.bg}>

        <View>
          <View style={styles.containerCenter}>
            <TouchableOpacity onPress={this.pickAudioFile.bind(this)}
              style={styles.startButton}>
              <Text style={styles.startButtonLabel}>Select WAV/M4A/AMR/MP3</Text>
            </TouchableOpacity>
          </View>
        </View>

        <View>
          <TextInput
            style={styles.customInput}
            value={this.state.audioName}
            placeholder="Audio Name"
            multiline={true}
            scrollEnabled={true}
            editable={false}
          />

          <TextInput
            style={styles.customInput}
            value={this.state.audioType}
            placeholder="Audio Type"
            multiline={true}
            scrollEnabled={true}
            editable={false}
          />

          <TextInput
            style={styles.customInput}
            value={this.state.audioUri}
            placeholder="Audio Uri"
            multiline={true}
            scrollEnabled={true}
            editable={false}
          />
        </View>

        <TextInput
          style={styles.customInput}
          value={"Upload Progress :" + this.state.progress.toString()}
          placeholder="Upload Progress"
          multiline={true}
          scrollEnabled={true}
          editable={false}
        />

        <TextInput
          style={styles.customEditBox2}
          value={this.state.result}
          placeholder="Recognition Result"
          multiline={true}
          scrollEnabled={true}
          editable={this.state.result == '' ? false : true}
        />

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.startAnalyze.bind(this)}
            disabled={this.state.audioUri == '' ? true : false}
          >
            <Text style={styles.startButtonLabel}> Start AFT </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.getShortAftLanguages.bind(this)}>
            <Text style={styles.startButtonLabel}> Get Short AFT Languages </Text>
          </TouchableOpacity>
        </View>

        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.getLongAftLanguages.bind(this)}>
            <Text style={styles.startButtonLabel}> Get Long AFT Languages </Text>
          </TouchableOpacity>
        </View>

        {this.state.isLongRecognize ?
          <View>

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={this.getLongAftResult.bind(this)}
                disabled={this.state.audioUri == '' ? true : false}
              >
                <Text style={styles.startButtonLabel}> Get Long Aft Result </Text>
              </TouchableOpacity>
            </View>

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={this.startTask.bind(this)}
                disabled={this.state.audioUri == '' ? true : false}
              >
                <Text style={styles.startButtonLabel}>Start Task</Text>
              </TouchableOpacity>
            </View>

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={this.pauseTask.bind(this)}
                disabled={this.state.audioUri == '' ? true : false}
              >
                <Text style={styles.startButtonLabel}>Pause Task</Text>
              </TouchableOpacity>
            </View>

            <View style={styles.basicButton}>
              <TouchableOpacity
                style={styles.startButton}
                onPress={this.destroyTask.bind(this)}
                disabled={this.state.audioUri == '' ? true : false}
              >
                <Text style={styles.startButtonLabel}>Destroy Task</Text>
              </TouchableOpacity>
            </View>
          </View>
          :
          <View>
          </View>
        }

      </ScrollView >
    );
  }
}