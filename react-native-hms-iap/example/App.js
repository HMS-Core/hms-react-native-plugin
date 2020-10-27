/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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
  ScrollView,
  StyleSheet,
  View,
  Image,
  Text,
} from 'react-native';

import { Colors } from 'react-native/Libraries/NewAppScreen';
import ProductTypes from './src/foundation/ProductTypes';
import PartialView from './src/ui/PartialView';
import HmsIapModule from '@hmscore/react-native-hms-iap';

async function enableLogger() {
  try {
    const response = await HmsIapModule.enableLogger()
    console.log("enabledLogger:: " + response)
  }catch(e){
    console.log("enableLogger fail!")
  }
}

async function disableLogger() {
  try {
    const response = await HmsIapModule.disableLogger()
    console.log("disableLogger:: " + response)
  }catch(e){
    console.log("disableLogger fail!")
  }
}

async function isSandboxActivated() {
  try {
    var res = await HmsIapModule.isSandboxActivated()
    console.log(JSON.stringify(res) + "")
  }catch(e){
    console.log("isSandboxActivated fail!")
  }
}

export default class App extends React.Component {

  constructor() {
    super()
    this.state = {
      isEnvReady: false
    }
  }

  async componentDidMount() {
    this.isEnvReady()
    isSandboxActivated()
  }

  async isEnvReady() {
    try {
      var res = await HmsIapModule.isEnvironmentReady()
      console.log(JSON.stringify(res) + "")
      if (res.status && res.status.statusMessage && res.status.statusMessage == "supported") {
        this.setState({ isEnvReady: true })
        console.log("isEnvReady:" + "Success")
      } else {
        this.setState({ isEnvReady: false })
        console.log("isEnvReady:" + "False")
      }
    }catch(e){
      console.log("isEnvironmentReady fail!")
    }
  }

  render() {
    return (
      <>
        <View style={styles.header}>
          <Text style={styles.title}>HMS IAP Plugin</Text>
          <Image
            resizeMode="contain"
            style={styles.logo}
            source={require('./assets/images/logo.jpg')} />
        </View>
        <ScrollView
          style={styles.scrollView}>
          {this.state.isEnvReady ?
            <View style={styles.body}>
              <PartialView productType={ProductTypes.CONSUMABLE} />
              <PartialView productType={ProductTypes.NON_CONSUMABLE} />
              <PartialView productType={ProductTypes.SUBSCRIPTION} />
              <View style={{ height: 70, width: '100%' }} />
            </View>
            :
            <Text style={[styles.title, { color: "black" }]}>IsEnviromentReady:false</Text>
          }
        </ScrollView>
      </>
    )
  }
}

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  header: {
    height: 130,
    width: '100%',
    backgroundColor: '#222222',
    flexDirection: 'row',
    paddingLeft: 20,
    alignItems: 'center'
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    color: 'white',
    width: 175,
  },
  logo: {
    height: 125,
    width: 200
  },
  engine: {
    position: 'absolute',
    right: 0
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

