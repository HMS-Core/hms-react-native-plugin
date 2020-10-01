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
import { Text, View, ScrollView, TextInput, TouchableOpacity, Switch, NativeEventEmitter, ToastAndroid } from 'react-native';
import { HmsMessageModule } from  'react-native-hms-nearby';
import { styles } from './styles';
import {convertByteArrayToString, convertStringToByteArray} from './Converter.js';

export default class MessagePublish extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      getMessage : "",
      isPublisher : true,
      myMessageET : "",
    };
  }

  componentDidMount(){

    const eventEmitter = new NativeEventEmitter(HmsMessageModule);
    
    eventEmitter.addListener('onBleSignalChanged', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('onDistanceChanged', (event) => {
      console.log(event);
    });

    eventEmitter.addListener('onFound', (event) => {
      console.log(event);
      this.setState({getMessage : convertByteArrayToString(event.content)});
    });

    eventEmitter.addListener('onLost', (event) => {
      console.log(event);
    });
  }

  async putMessage(message) {
    try {
      var result = await HmsMessageModule.put(convertStringToByteArray(message),false);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async unputMessage(message) {
    try {
      var result = await HmsMessageModule.unput(convertStringToByteArray(message));
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async getMessage() {
    try {
      var result = await HmsMessageModule.getMessage(false);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async ungetMessage() {
    try {
      var result = await HmsMessageModule.unget(false);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  toggleSwitch = () => {
    this.setState({
      isPublisher: !this.state.isPublisher,
    })
  }

  showInputError = () => {
    ToastAndroid.showWithGravity(
      "Please provide a message",
      ToastAndroid.SHORT,
      ToastAndroid.CENTER
    );
  }

  handleMessagePublishGet = () => {
    if(this.state.isPublisher){
      if (this.state.myMessageET.length > 0){
        this.putMessage(this.state.myMessageET);
      }
      else{
        this.showInputError();
      }
    }
    else{
        this.getMessage();
    }
  }

  handleMessageUnPublishUnGet = () => {
    if(this.state.isPublisher){
      if (this.state.myMessageET.length > 0){
        this.unputMessage(this.state.myMessageET);
      }
      else{
        this.showInputError();
      }
    }
    else{
        this.ungetMessage();
    }
  }

  render() {
      return (
        <ScrollView >

        <View style={styles.container}>
          <Text>{this.state.isPublisher ? 'PUBLISHER' : 'SUBSCRIBER' }</Text>
          <Switch
            trackColor={{ false: "#767577", true: "#81b0ff" }}
            thumbColor={this.state.isPublisher ? "#f5dd4b" : "#f4f3f4"}
            onValueChange={this.toggleSwitch.bind(this)}
            value={this.state.isPublisher}
          />
        </View>
        
        { this.state.isPublisher ?
            <TextInput
            style={styles.customEditBox3}
            placeholder="Message"
            multiline={true} 
            editable = {this.state.isPublisher}
            onChangeText = {text => this.setState({myMessageET : text})}
            />
            :
            <TextInput
              style={styles.customEditBox3}
              placeholder="Found Message"
              multiline={true}
              editable = {this.state.isPublisher}
              value = {this.state.getMessage}
            />

        }
        
        <View style={styles.buttonTts}>
            <TouchableOpacity
              style={styles.startButton}
              onPress = { this.handleMessagePublishGet.bind(this) }
              underlayColor="#fff">
              <Text style={styles.startButtonLabel}> {this.state.isPublisher ? "Publish" : "Subscribe"} Message </Text>
            </TouchableOpacity>
        </View>

        <View style={styles.buttonTts}>
            <TouchableOpacity
              style={styles.startButton}
              onPress = { this.handleMessageUnPublishUnGet.bind(this) }
              underlayColor="#fff">
              <Text style={styles.startButtonLabel}> {this.state.isPublisher ? "UnPublish" : "UnSubscribe"} Message </Text>
            </TouchableOpacity>
        </View>

        </ScrollView>

      );
}
}
