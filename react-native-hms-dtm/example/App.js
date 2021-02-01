/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

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
  StyleSheet,
  TouchableOpacity,
  Text,
  View,
  ScrollView,
  Image,
  DeviceEventEmitter
} from 'react-native';

import HMSDTMModule from '@hmscore/react-native-hms-dtm/src';

export default class App extends React.Component {

  async componentDidMount() {

    DeviceEventEmitter.addListener('CustomTag', (map) => {
      console.log("CustomTag::" + JSON.stringify(map))

      //For CustomVariable
      if (map.price == "90" && map.discount == "10") {
        this.setCustomVariable(map)
      }

    })

    DeviceEventEmitter.addListener('CustomVariable', (map) => {
      console.log("CustomVariable::" + JSON.stringify(map))
    })
  }

  async customEvent() {
    /*NOTE: 
      Firebase, AppsFlyer configurations can be related with this event.
      In this case, platformName should be created as an "Event parameter" on the console.
      You can use the documentation of the kit for examples.
    */
    try {
      const eventId = "Platform"
      const bundle = {
        platformName: "React Native",
      }
      var res = await HMSDTMModule.onEvent(eventId, bundle)
      alert("Response: " + JSON.stringify(res))
    } catch (e) {
      console.log("customEvent:: " + JSON.stringify(e))
      alert("Error: " + "onEvent::" + JSON.stringify(e))
    }
  }

  //CustomTag
  async customTag() {
    try {
      const eventId = "PurchaseShoes"
      const bundle = {
        "itemName": "Shoes",
        "quantity": "1",
      }

      var res = await HMSDTMModule.onEvent(eventId, bundle)
      console.log("onEvent-customTag::" + JSON.stringify(res))
      alert("Response: " + JSON.stringify(res))
    } catch (e) {
      console.log("onEvent-customTag:: " + JSON.stringify(e))
      alert("Error: " + "onEvent-customTag!")
    }
  }

  //CustomVariable with Tag
  async tagWithCustVar() {
    try {

      const eventId = "PantsSold"
      const bundle = {
        "discount": "10",
        "price": "100"
      }
      var res = await HMSDTMModule.onEvent(eventId, bundle)
      console.log("onEvent-customTag::" + JSON.stringify(res))
    } catch (e) {
      console.log("onEvent-customTag:: " + JSON.stringify(e))
      alert("Error: " + "onEvent-customTag!")
    }
  }

  //Sets the return value for the CustomVariable.
  async setCustomVariable(map) {
    try {
      var price = Number(map.price)
      var discount = Number(map.discount)
      const value = Number(price - (discount * price) / 100)

      var response = await HMSDTMModule.setCustomVariable("varName", value + "")
      console.log("setCustomVariable res message:: " + JSON.stringify(response))
      this.customVariable(response)
    } catch (e) {
      console.log("setCustomVariable :: " + JSON.stringify(e))
      alert("Error: " + "setCustomVariable!")
    }
  }

  async customVariable(response) {
    try {
      if (response.data == "Success") {
        const eventId = "PurchasePants"
        const bundle = {
          "varName": "PantsPrice"
        }
        var res = await HMSDTMModule.onEvent(eventId, bundle)
        console.log("onEvent CustomVariable::Function Call with CustomTag:: " + res)
        alert("Response: " + JSON.stringify(res))
      }
    } catch (e) {
      console.log("customVariable :: " + JSON.stringify(e))
      alert("Error: " + "customVariableFunctonCall!")
    }
  }

  //HMS Logger
  async enableLogger() {
    try {
      var res = await HMSDTMModule.enableLogger()
      console.log("HMSLogger-enabled :: " + JSON.stringify(res))
      alert("Response: " + JSON.stringify(res))
    } catch (e) {
      console.log("HMSLogger-enabled :: " + JSON.stringify(e))
      alert("Error: " + "HMSLogger-enabled!")
    }
  }

  async disableLogger() {
    try {
      var res = await HMSDTMModule.disableLogger()
      console.log("HMSLogger-disabled :: " + JSON.stringify(res))
      alert("Response: " + JSON.stringify(res))
    } catch (e) {
      console.log("HMSLogger-disabled :: " + JSON.stringify(e))
      alert("Error: " + "HMSLogger-disabled!")
    }
  }

  render() {
    return (

      <View style={styles.mainContainer}>

        <View style={styles.header}>
          <Text style={styles.headerTitle}>HMS DTM Plugin</Text>
          <Image
            resizeMode="contain"
            style={styles.headerLogo}
            source={require('./assets/images/huawei_icon.jpg')} />
        </View>


        <ScrollView
          ref={(scrollView) => { this._scrollView = scrollView; }}
          onScroll={this.handleScroll}
          style={styles.scrollView}
          nestedScrollEnabled={true}>

          <View style={styles.container}>
            <View style={styles.partialView}>
              <Text style={styles.title}>DTM Module</Text>

              <TouchableOpacity activeOpacity={.8} style={styles.btn}
                onPress={() => this.customEvent()}>
                <Text style={styles.txt}> Basic Custom Event </Text>
              </TouchableOpacity>

              <TouchableOpacity activeOpacity={.8} style={styles.btn}
                onPress={() => this.customTag()}>
                <Text style={styles.txt}> Custom Tag </Text>
              </TouchableOpacity>

              <TouchableOpacity activeOpacity={.8} style={styles.btn}
                onPress={() => this.tagWithCustVar()}>
                <Text style={styles.txt}>CustomVariable With CustomTag</Text>
              </TouchableOpacity>

            </View>

          </View>

        </ScrollView>
      </View>
    )
  }

}

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    backgroundColor: 'black',
    borderColor: 'orange',
    borderWidth: 1
  },
  scrollView: {
    flex: 1
  },
  container: {
    flex: 1,
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
    paddingBottom: 50
  },
  partialView: {
    marginTop: 10,
    width: "100%"
  },
  header: {
    backgroundColor: 'black',
    height: 160,
    width: '100%',
    flexDirection: 'row',
    borderBottomColor: 'orange',
    borderBottomWidth: 1
  },
  headerTitle: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#FFB300',
    flex: 1.1,
    alignSelf: 'center',
    marginLeft: 20
  },
  headerLogo: {
    flex: .7,
    height: '100%',
  },
  btn: {
    marginTop: 15,
    width: 125,
    height: 45,
    justifyContent: 'center',
    alignSelf: 'center',
    borderRadius: 10,
    marginLeft: 5,
    marginRight: 5,
    borderColor: "orange",
    borderWidth: 1
  },
  txt: {
    fontSize: 14,
    color: '#FFA500',
    textAlign: 'center'
  },
  title: {
    fontSize: 18,
    color: '#FFB300',
    width: 200,
    alignSelf: 'center',
    borderBottomColor: '#FFB300',
    borderBottomWidth: .7,
    textAlign: 'center',
    marginTop: 30
  },
  input: {
    color: "orange",
    width: 80,
    height: 45,
    alignSelf: 'center',
    borderColor: "yellow",
    borderWidth: .5,
    marginTop: 20

  }
})