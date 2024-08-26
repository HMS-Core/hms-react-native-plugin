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

import React from 'react';
import {
  Text,
  View,
  ScrollView,
  TextInput,
  Dimensions,
  PixelRatio,
  TouchableOpacity,
  ToastAndroid
} from 'react-native';
import { HMSInteractiveLivenessDetection, HMSApplication,HMSInteractiveLivenessCustomDetectionHandler} from '@hmscore/react-native-hms-mlbody';
import { styles } from '../Styles';

export default class InteractiveLivenessDetection extends React.Component {

  componentDidMount() { 
  }

  componentWillUnmount() { }

  constructor(props) {
    super(props);
  
    
    this.state = {
      status: 0.0,
      action: 0.0,
    };
  } 
  
  async startCustomizedView() {

    const { width, height } = Dimensions.get('window');
    windowWidth= PixelRatio.getPixelSizeForLayoutSize(width);
    windowHeight=PixelRatio.getPixelSizeForLayoutSize(height);
    
    const actionObject = {};
    const actionMap= new Map();
    const statusCodeMessageObject = {};
    const statusCodeMessageMap= new Map();
  
    actionMap.set(1, "Nod your head.");
    actionMap.set(2, "Open your mouth. ");
    actionMap.set(3, "Blink. ");
    actionMap.set(4, "Turn your head to the left. ");
    actionMap.set(5, "Turn your head to the right. ");
    actionMap.set(6, "Stare at the screen. ");

    statusCodeMessageMap.set(1001, "The face orientation is inconsistent with that of the phone.");
    statusCodeMessageMap.set(1002, "No face is detected.");
    statusCodeMessageMap.set(1003, "Multiple faces are detected.");
    statusCodeMessageMap.set(1004, "The face deviates from the center of the face frame.");
    statusCodeMessageMap.set(1005, "The face is too large.");
    statusCodeMessageMap.set(1006, "The face is too small.");
    statusCodeMessageMap.set(1007, "The face is blocked by the sunglasses.");
    statusCodeMessageMap.set(1008, "The face is blocked by the mask.");
    statusCodeMessageMap.set(1009, "The detected action is not the required one.");
    statusCodeMessageMap.set(1014, "The continuity detection fails.");
    statusCodeMessageMap.set(1018, "The light is dark.");
    statusCodeMessageMap.set(1019, "The image is blurry.");
    statusCodeMessageMap.set(1020, "The face is backlit.");
    statusCodeMessageMap.set(1021, "The light is bright.");
    statusCodeMessageMap.set(2000, "In progress");
    statusCodeMessageMap.set(2002, "The face does not belong to a real person. ");
    statusCodeMessageMap.set(2003, "Verification is performed, and the detected action is correct.");
    statusCodeMessageMap.set(2004,"Verification succeeded.");
    statusCodeMessageMap.set(2007,"The position of the face frame is not set before the algorithm is called.");
    statusCodeMessageMap.set(5020,"The previous detection ended when it was not complete.");
    
    for (const [key, value] of actionMap) {
      actionObject[key] = value;
    }
    for (const [keys, values] of statusCodeMessageMap) {
      statusCodeMessageObject[keys] = values;
    }

      try {
        var result = await HMSInteractiveLivenessCustomDetectionHandler.startCustomizedView(
         option = {
            action:{
              actionArray:actionObject,
              num:1,
              isRandom:true
            },    
            detectionTimeOut:10000,
            cameraFrame:{ 
              left:0,
              top:0,
              right:1040,
              bottom:1440,      
            },
            faceFrame:{
              left:14,
              top:122,
              right:396,
              bottom:518             
            },
            statusCodeMessage:statusCodeMessageObject,
            showStatusCodes:true,
            header:"Face Detection",
            textMargin:windowHeight*0.7,
            textOptions:{
              textColor:-16776961,
              textSize:16,
              autoSizeText:false,
              maxTextSize:30,
              minTextSize:15,
              granularity:2,
            }
          }
        );
        console.log(result)
      if (result.status == HMSApplication.SUCCESS) {  
        this.setState({
          status: result.result.status,
          action:result.result.action    
        });
      }
    } catch (e) {
      console.log(e);
    }
  }

  async startDetect() {
    try {
      var result = await HMSInteractiveLivenessDetection.startDetect(
        {
          option:HMSInteractiveLivenessDetection.DETECT_MASK,
          detectionTimeOut:10000,
          config:{
            actionArray:[HMSInteractiveLivenessDetection.SHAKE_DOWN_ACTION,HMSInteractiveLivenessDetection.EYE_CLOSE_ACTION,HMSInteractiveLivenessDetection.SHAKE_LEFT_ACTION],
            num:2,
            isRandomable:true
          }
        }
      );
      console.log(result);
      if (result.status == HMSApplication.SUCCESS) {
        this.setState({
          status: result.result.status,
          action:result.result.action    
        });
      }
      else {
        ToastAndroid.showWithGravity(result.message, ToastAndroid.SHORT, ToastAndroid.CENTER);
      }
    } catch (e) {
      console.log(e);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <Text style={styles.h1}>Interactive Detection Results</Text>

        <TextInput
          style={styles.customInput}
          value={"Status :" + this.state.status.toString()}
          placeholder="Status"
          multiline={true}
          editable={false}
        />
        <TextInput
          style={styles.customInput}
          value={"Action :" + this.state.action.toString()}
          placeholder="Action"
          multiline={true}
          editable={false}
        />
        
        <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.startDetect.bind(this)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Start Detection </Text>
          </TouchableOpacity>
        </View>

         <View style={styles.basicButton}>
          <TouchableOpacity
            style={styles.startButton}
            onPress={this.startCustomizedView.bind(this)}
            underlayColor="#fff">
            <Text style={styles.startButtonLabel}> Set Detect Mask Config </Text>
          </TouchableOpacity>
        </View> 

      </ScrollView>
    );
  }
}
