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
import { Text, View, TextInput, TouchableOpacity, NativeEventEmitter, FlatList, ToastAndroid  } from 'react-native';
import { HmsDiscoveryModule, HmsTransferModule } from 'react-native-hms-nearby';
import { styles } from './styles';
import {convertByteArrayToString, convertStringToByteArray} from './Converter.js';

export default class NearbyConnection extends React.Component {
  
  constructor(props) {
    super(props);
    this.state = {
      myNameET: '',
      friendNameET:'',
      myMessageET : '',
      endPointId : '',
      refresh: false,
      myNameETeditable : false,
      friendNameETeditable : false,
      myMessageETeditable : false,
      chatBTdisabled : true,
      sendBTdisabled : true,
      connectionEstablished : false,
      messages : [],
    };
  }

  componentDidMount(){

      this.enableDisableStartChat(true);

      const eventEmitter = new NativeEventEmitter(HmsDiscoveryModule);
     
      eventEmitter.addListener('connectOnEstablish', (event) => {
        console.log("connectOnEstablish");
        console.log(event);
        this.setState({connectionEstablished : true, endPointId : event.endpointId});
        if(this.state.myNameET.localeCompare(this.state.friendNameET) < 0 ){
          
          this.acceptConnect()
            .then(() => this.stopScan());
        }
        else{
          this.acceptConnect()
            .then(()=> this.stopBroadCasting());
        }        
        console.log("Connection Accepted")
      });
      
      eventEmitter.addListener('connectOnResult', (event) => {
        console.log(event);
        this.setState({endPointId : event.endpointId});
        this.enableDisableMessaging(true);
         ToastAndroid.showWithGravity(
          "Lets Chat",
          ToastAndroid.SHORT,
          ToastAndroid.CENTER
        );
      });

      eventEmitter.addListener('connectOnDisconnected', (event) => {
        console.log(event);
        ToastAndroid.showWithGravity(
          "Disconnect",
          ToastAndroid.SHORT,
          ToastAndroid.CENTER
        );
        this.enableDisableMessaging(false);
        this.enableDisableStartChat(true);
      });

      eventEmitter.addListener('scanCallbackOnFound', (event) => {
        console.log("scanCallbackOnFound");
        console.log(event);
        this.setState({endPointId : event.endpointId});
        this.requestConnect();
      });

      eventEmitter.addListener('scanCallbackOnLost', (event) => {
        console.log("scanCallbackOnLost");
        console.log(event);
        this.enableDisableMessaging(false);
        this.enableDisableStartChat(true);
      });

      eventEmitter.addListener('dataCallbackOnReceived', (event) => {
        console.log("dataCallbackOnReceived");
        console.log(event);
        this.state.messages.push({message:convertByteArrayToString(event.data)});
        this.setState({ 
          refresh: !this.state.refresh
        })
      });

      eventEmitter.addListener('dataCallbackOnTransferUpdate', (event) => {
        console.log("dataCallbackOnTransferUpdate");
        console.log(event);
      });
  }

  componentWillUnmount(){
    if(this.state.connectionEstablished){
      this.disconnectAll();
    }
  }

  startChat = () => {

    if (!this.state.myNameET.length > 0  || !this.state.friendNameET.length > 0){
      ToastAndroid.showWithGravity(
        "Please Write Your Name and Your Friend Name",
        ToastAndroid.SHORT,
        ToastAndroid.CENTER
      );
    }
    else {
      this.enableDisableStartChat(false);

      if (this.state.myNameET.localeCompare(this.state.friendNameET) < 0){
        // scan
        this.setScanOption()
          .then(() => this.startScan());
      }
      else if (this.state.myNameET.localeCompare(this.state.friendNameET) > 0){
        // broadcast
        this.setBroadcastOption()
          .then(() => this.startBroadCasting());
      }
      else {
        ToastAndroid.showWithGravity(
          "Same names are not allowed",
          ToastAndroid.SHORT,
          ToastAndroid.CENTER
        );
      }
    }
  }

  async setBroadcastOption() {
    try {
      var result = await HmsDiscoveryModule.createBroadcastOption(3);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async startBroadCasting() {
    try {
      var result = await HmsDiscoveryModule.startBroadCasting(this.state.myNameET, this.getServiceId());
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async stopBroadCasting() {
    try {
      var result = await HmsDiscoveryModule.stopBroadCasting();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async setScanOption() {
    try {
      var result = await HmsDiscoveryModule.createScanOption(3);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async startScan() {
    try {
      var result = await HmsDiscoveryModule.startScan(this.getServiceId());
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async stopScan() {
    try {
      var result = await HmsDiscoveryModule.stopScan();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async requestConnect(){
    try {
      var result = await HmsDiscoveryModule.requestConnect(this.state.myNameET, this.state.endPointId);
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  async sendMessage(){
    this.enableDisableMessaging(false);

    if (this.state.myMessageET.length > 0){
      try {
        console.log(this.state.myMessageET);
        console.log(this.state.endPointId);
        var result = await HmsTransferModule.transferBytes(convertStringToByteArray(this.state.myMessageET), [this.state.endPointId]);
        this.state.messages.push({message:this.state.myMessageET})
        console.log(result);
      } catch (e) {
        console.error(e);
      }
    }
    else{
      ToastAndroid.showWithGravity(
        "Please provide a message",
        ToastAndroid.SHORT,
        ToastAndroid.CENTER
      );
    }

    this.enableDisableMessaging(true);
  }

  async acceptConnect(){
      try {
        var result = await HmsDiscoveryModule.acceptConnect(this.state.endPointId);
        console.log(result);
      } catch (e) {
        console.error(e);
      } 
  }

  async disconnectAll() {
    try {
      var result = await HmsDiscoveryModule.disconnectAll();
      console.log(result);
    } catch (e) {
      console.error(e);
    }
  }

  enableDisableStartChat = (isEnable) => {
    if (isEnable){
      this.setState({myNameETeditable : true, friendNameETeditable : true, chatBTdisabled : false });
    }
    else {
      this.setState({myNameETeditable : false, friendNameETeditable : false, chatBTdisabled : true });
    }
  }

  enableDisableMessaging = (isEnable) => {
    if (isEnable){
      this.setState({sendBTdisabled : false,  myMessageETeditable : true });
    }
    else {
      this.setState({sendBTdisabled : true,  myMessageETeditable : false });
    }
  }

  getServiceId = () => {
    if(this.state.myNameET.localeCompare(this.state.friendNameET) < 0) {
      // scan
      return this.state.myNameET + this.state.friendNameET;
    }
    else {
      // broadcast
      return this.state.friendNameET + this.state.myNameET;
    }
  }

  stopFeatures = (isScan) => {
      if (isScan) {
        this.stopScan();
        this.enableDisableStartChat(true);
      }
      else{
        this.stopBroadCasting();
        this.enableDisableStartChat(true);
      }
  } 


  render() {
    return (
      <View>
            <View style={styles.containerFlex}>
              <TextInput
                style={styles.customInput}
                placeholder="My Name"
                multiline={false}
                editable={this.state.myNameETeditable}
                onChangeText={text => this.setState({ myNameET: text })}
              />
               <TextInput
                style={styles.customInput}
                placeholder="Friend's Name"
                multiline={false}
                editable={this.state.friendNameETeditable}
                onChangeText={text => this.setState({ friendNameET: text })}
              />
            </View>

            <View style={styles.buttonTts}>
                  <TouchableOpacity
                    style={styles.startButton}
                    onPress={ this.startChat.bind(this)}
                    underlayColor="#fff"
                    disabled = {this.state.chatBTdisabled}>                
                    <Text style={styles.startButtonLabel}> Start Chat </Text>
                  </TouchableOpacity>
            </View>

            <View style={styles.containerList}>
              <FlatList
                key={this.state.messages.length}
                data={this.state.messages}
                renderItem={({item}) => 
                <Text style={styles.item}> {item.message} </Text>
              }
                extraData={this.state.refresh}
              />
            </View>

            <TextInput
                style={styles.customEditBox3}
                placeholder="Write Your Message"
                multiline={true}
                editable={this.state.myMessageETeditable}
                onChangeText = {text => this.setState({myMessageET : text})}
              />

                <View style={styles.buttonTts}>
                  <TouchableOpacity
                  style={styles.startButton}
                  onPress={ this.sendMessage.bind(this)}
                  underlayColor="#fff"
                  disabled ={this.state.sendBTdisabled}>
                  <Text style={styles.startButtonLabel}> Send </Text>
                </TouchableOpacity>
                </View>
          </View>
    );
  }
}