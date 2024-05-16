/*
 *   Copyright 2020-2024. Huawei Technologies Co., Ltd. All rights reserved.
 *   
 *   Licensed under the Apache License, Version 2.0 (the "License")
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import React from 'react';
import {
  ScrollView,
  StyleSheet,
  View,
  Text
} from 'react-native';

import { Colors } from 'react-native/Libraries/NewAppScreen';
import ProductTypes from './src/foundation/ProductTypes';
import PartialView from './src/ui/PartialView';
import HMSIapModule from '@hmscore/react-native-hms-iap';

async function enableLogger() {
  try {
    const response = await HMSIapModule.enableLogger();
    console.log("enabledLogger:: " + response);
  } catch (e) {
    console.log("enableLogger fail!");
  }
}

async function disableLogger() {
  try {
    const response = await HMSIapModule.disableLogger();
    console.log("disableLogger:: " + response);
  } catch (e) {
    console.log("disableLogger fail!");
  }
}

async function isSandboxActivated() {
  try {
    const res = await HMSIapModule.isSandboxActivated();
    console.log(JSON.stringify(res) + "");
  } catch (e) {
    console.log("isSandboxActivated fail!");
  }
}

async function enablePendingPurchase() {
  try {
    const res = await HMSIapModule.enablePendingPurchase();
    console.log("enablePendingPurchase", JSON.stringify(res) + "");
  } catch (e) {
    console.log("enablePendingPurchase fail!");
  }
}

export default class App extends React.Component {

  constructor() {
    super();
    this.state = {
      isEnvReady: false
    };
  }

  async componentDidMount() {
    this.isEnvReady();
    isSandboxActivated();
    enablePendingPurchase();
  }

  async isEnvReady(arg = false) {
    try {
      const res = await HMSIapModule.isEnvironmentReady(arg);
      console.log(JSON.stringify(res));
      if (res.returnCode === 0) {
        this.setState({ isEnvReady: true });
        console.log("isEnvReady:" + "Success");
      } else {
        this.setState({ isEnvReady: false });
        console.log("isEnvReady:" + "False");
      }
    } catch (e) {
      console.log("isEnvironmentReady fail!");
    }
  }

  render() {
    return (
      <>
        <View style={styles.header}>
          <Text style={styles.title}>HMS IAP Plugin</Text>
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
            <Text style={[styles.title, { color: "black" }]}>IsEnviromentReady: false</Text>
          }
        </ScrollView>
      </>
    );
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
    paddingLeft: 50,
    alignItems: 'center'
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    color: 'white',
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

