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

import React, { Component } from 'react';
import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  Image,
  PermissionsAndroid,
} from 'react-native';

import { styles } from './styles';

export default class App extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount(){
    this.requestPermissions();
  }

  async requestPermissions() {
    try {
      const userResponse = await PermissionsAndroid.requestMultiple(
        [ 
          PermissionsAndroid.PERMISSIONS.CAMERA, 
          PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
          PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION,
          PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE,
          PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE
        ]
      );
    }
    catch (err) {
      console.error(err);
    }
  }

  render() {
    return (
      <ScrollView style={styles.bg}>
        <View style={styles.containerCenter}>
          <Image style={styles.img} source={require('../assets/nearby.png')} />
        </View>

        <Text style={styles.h1}>Rn Hms Nearby Examples</Text>

        <View style={styles.containerFlex}>
          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('NearbyConnection')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/connection.png')}
                />
              </View>
              <Text style={styles.buttonText}>Nearby Connection</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.button}>
            <TouchableOpacity
              style={styles.buttonRadius}
              onPress={() => this.props.navigation.navigate('MessagePublish')}
              underlayColor="#fff">
              <View style={styles.centerImg}>
                <Image
                  style={styles.imgButton}
                  source={require('../assets/message.png')}
                />
              </View>
              <Text style={styles.buttonText}>Nearby Message</Text>
            </TouchableOpacity>
          </View>
        </View>
      </ScrollView>
    );
  }
}
