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

import React, {useEffect} from "react";
import {
  TouchableOpacity,
  View,
  Text,
  ScrollView,
  Alert} from "react-native";
import HMSAvailability, {ErrorCode} from "@hmscore/react-native-hms-availability";
import { styles } from "./styles";

const Button = (props) => (
  <TouchableOpacity
    style={[
      styles.buttonContainer,
      styles.secondaryButton,
      styles.buttonContainerSlim,
    ]}
    onPress={props.onPress}
  >
    <Text style={styles.buttonText}>{props.text}</Text>
  </TouchableOpacity>
);

const showResult = (res) => Alert.alert("Result", JSON.stringify(res, null, 4));
class App extends React.Component {

  componentDidMount() {
    HMSAvailability.OnErrorDialogFragmentCancelledListenerAdd(() => {
       console.log("Dialog Fragment Cancelled");
    })
  };

  componentWillUnmount(){
    HMSAvailability.OnErrorDialogFragmentCancelledListenerRemove();    
  }

  isHuaweiMobileServicesAvailable() {
   
    HMSAvailability.isHuaweiMobileServicesAvailable()
      .then((res) => this.getErrorString(res))
      .catch((err) => showResult(err));
  }

  getApiMap() {
    HMSAvailability.getApiMap()
     .then((res) => showResult(res))
     .catch((err) => showResult(err));
  }

  getServicesVersionCode() {
    HMSAvailability.getServicesVersionCode()
     .then((res) => showResult(res))
     .catch((err) => showResult(err));
  }

  getErrorString(errorCode = ErrorCode.HMS_CORE_APK_OUT_OF_DATE) {
    HMSAvailability.getErrorString(errorCode)
     .then((res) => showResult(errorCode +": "+res))
     .catch((err) => showResult(err));
  }

  resolveError() {
    HMSAvailability.resolveError(ErrorCode.HMS_CORE_APK_OUT_OF_DATE, 111)  
     .then(() => console.log("resolveError"))
    .catch((err) => showResult(err));
  }

  isUserResolvableError() {
    HMSAvailability.isUserResolvableError(1)
     .then((res) => showResult(res))
     .catch((err) => showResult(err));
  }

  isHuaweiMobileNoticeAvailable() {
    HMSAvailability.isHuaweiMobileNoticeAvailable()
     .then((res) => showResult(res))
     .catch((err) => showResult(err));
  }

  setServicesVersionCode() {
    HMSAvailability.setServicesVersionCode(50000000)
     .then(() => this.getServicesVersionCode())
     .catch((err) => showResult(err));
  }

  showErrorDialogFragment() {
    HMSAvailability.showErrorDialogFragment(ErrorCode.HMS_CORE_APK_OUT_OF_DATE, 111)    
     .then((res) => console.log("ShowErrorDialogFragment result: " + res))
     .catch((err) => showResult(err));
  }

  showErrorNotification() {
    HMSAvailability.showErrorNotification(ErrorCode.HMS_CORE_APK_OUT_OF_DATE)
    .then(() => console.log("showErrorNotification"))
    .catch((err) => showResult(err));
  }
  render(){
    return (
    
      <ScrollView>
        <View style={styles.title}>
          <Text style={styles.header}>HMS Availability</Text>
        </View>
  
        <Button
          text="IsHuaweiMobileServicesAvailable"
          onPress={() => {
            this.isHuaweiMobileServicesAvailable();
          }}
        />
  
        <Button
          text="isHuaweiMobileNoticeAvailable"
          onPress={() => {
            this.isHuaweiMobileNoticeAvailable();
          }}
        />
  
        <Button
          text="getApiMap"
          onPress={() => {
            this.getApiMap();
          }}
        />
  
        <Button
          text="getServicesVersionCode"
          onPress={() => {
            this.getServicesVersionCode();
          }}
        />
  
        <Button
          text="setServicesVersionCode"
          onPress={() => {
            this.setServicesVersionCode();
          }}
        />
  
        <Button
          text="getErrorString"
          onPress={() => {
            this.getErrorString();
          }}
        />
  
        <Button
          text="isUserResolvableError"
          onPress={() => {
            this.isUserResolvableError();
          }}
        />
  
        <Button
          text="resolveError"
          onPress={() => {
            this.resolveError();
          }}
        />
  
        <Button
          text="showErrorDialogFragment"
          onPress={() => {
            this.showErrorDialogFragment();
          }}
        />
  
        <Button
          text="showErrorNotification"
          onPress={() => {
            this.showErrorNotification();
          }}
        />
  
      </ScrollView>
    );
  }
};

export default App;
