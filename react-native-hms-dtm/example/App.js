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
  StyleSheet,
  Text,
  View,
  ScrollView,
  Dimensions,
  TouchableOpacity,
  ActivityIndicator,
  DeviceEventEmitter,
  Easing
} from 'react-native';

import HMSDtmModule from '@hmscore/react-native-hms-dtm';
export const ScreenWidth = Dimensions.get("window").width;
export const ScreenHeight = Dimensions.get("window").height;

export default class App extends React.Component {

  constructor() {
    super();
    this.state = { showProgress: false }
  }
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
    this.setState({showProgress: true})
    const eventId = "Purchase";
    const bundle = {
      price: 9,
      quantity: 100,
      currency: "CNY"
    }

    HMSDtmModule.onEvent(eventId, bundle)
      .then((res) => { this.showResult("CustomEvent::", true, res) })
      .catch((err) => { this.showResult("CustomEvent Err::", true, err) })

  }

  async customEventBundleList() {
    const eventId = "$ViewProductList"
    const bundleList = []
    const product1 = {
     "$ProductId":"HUAWEI_Mate_40_AI8I03Q423G",
     "$ProductName":"HUAWEI Mate 40 Pro",
     "$Category": "Phone/Mate/Black",
     "$ProductFeature":"Black",
     "$Brand": "HUAWEI",
     "$Price":6999.00,
     "$CurrName":"CNY",
     "$PositionId":1
    }

    const product2 = {
      "$ProductId":"HUAWEI_Mate_40_AI8I03QJ2J90",
      "$ProductName":"HUAWEI Mate 40 Pro",
      "$Category": "Phone/Mate/Yellow",
      "$ProductFeature":"Yellow",
      "$Brand": "HUAWEI",
      "$Price":6999.00,
      "$CurrName":"CNY",
      "$PositionId":2
    }

    bundleList.push(product1)
    bundleList.push(product2)

    const ecommerceBundle = {
      items: bundleList,
      "$ProductList": "Search Results"
    }
    HMSDtmModule.onEvent(eventId, ecommerceBundle)
    .then((res) => { this.showResult("CustomEventBundleList::", true, res) })
    .catch((err) => { this.showResult("CustomEventBundleList Err::", true, err) })
  }

  //CustomTag
  async customTag() {
    this.setState({showProgress: true})
    const eventId = "PurchaseShoes"
    const bundle = {
      "itemName": "Shoes",
      "quantity": "1",
    }

    HMSDtmModule.onEvent(eventId, bundle)
      .then((res) => { this.showResult("CustomTag::", true, res) })
      .catch((err) => { this.showResult("CustomTag Err::", true, err) })
  }

  //CustomVariable with Tag
  async tagWithCustVar() {
    this.setState({showProgress: true})
    const eventId = "PantsSold"
    const bundle = {
      "discount": "10",
      "price": "100"
    }

    HMSDtmModule.onEvent(eventId, bundle)
      .then((res) => { this.showResult("tagWithCustVar::", false, res) })
      .catch((err) => { this.showResult("tagWithCustVar Err::", true, err) })
  }

  //Sets the return value for the CustomVariable.
  async setCustomVariable(map) {
    this.setState({showProgress: true})
    var price = Number(map.price)
    var discount = Number(map.discount)
    const value = Number(price - (discount * price) / 100)

    HMSDtmModule.setCustomVariable("varName", value + "")
      .then((res) => {
        this.showResult("setCustomVariable:: ", false, res)
        this.customVariable(res)
      })
      .catch((err) => { this.showResult("setCustomVariable Err::", true, err) })
  }

  async customVariable(response) {

    if (response.data == "Success") {
      this.setState({showProgress: true})
      const eventId = "PurchasePants"
      const bundle = {
        "varName": "PantsPrice"
      }
      HMSDtmModule.onEvent(eventId, bundle)
        .then((res) => { this.showResult("customVariable::", true, res) })
        .catch((err) => { this.showResult("customVariable Err::", true, err) })
    } else {
      this.showResult("customVariable-response.data::", true, "null")
    }

  }

  //HMS Logger
  async enableLogger() {
    HMSDtmModule.enableLogger()
      .then((res) => { this.showResult("HMSLogger-enabled::", true, res) })
      .catch((err) => { this.showResult("HMSLogger-enabled Err::", true, err) })
  }

  async disableLogger() {
    HMSDtmModule.disableLogger()
      .then((res) => { this.showResult("HMSLogger-disabled::", true, res) })
      .catch((err) => { this.showResult("HMSLogger-disabled Err::", true, err) })
  }

  //UI
  showResult(desc, hasAlert, res) {
    if (res != undefined && hasAlert) {
      alert(desc + ":\n" + JSON.stringify(res),Easing)
      console.log(desc + "::" + res)
    } else if (res != undefined && !hasAlert) {
      console.log(desc + ":\n" + res)
    }
    this.setState({ showProgress: false })
  }

  render() {
    return (
      <View style={styles.mainContainer}>

        <View style={styles.header}>
          <Text style={styles.headerTitle}>\|/   </Text>
          <Text style={styles.headerTitle}>HMS DTM Plugin</Text>
          <Text style={styles.headerTitle}>   \|/</Text>
        </View>

        <ScrollView
          ref={(scrollView) => { this._scrollView = scrollView; }}
          onScroll={this.handleScroll}
          style={styles.scrollView}
          nestedScrollEnabled={true}>

          {this.state.showProgress ? (
            <View style={styles.disableOverlay}>
              <ActivityIndicator
                size={"large"}
                color={"orange"}
                style={styles.activityIndicator}
              />
            </View>
          ) : null}

          <View style={styles.container}>
            <View style={styles.partialView}>
              <Text style={styles.title}>DTM Module</Text>

              <TouchableOpacity activeOpacity={.8} style={styles.btn}
                onPress={() => this.customEvent()}>
                <Text style={styles.txt}> Basic Custom Event </Text>
              </TouchableOpacity>

              <TouchableOpacity activeOpacity={.8} style={styles.btn}
                onPress={() => this.customEventBundleList()}>
                <Text style={styles.txt}>Custom Event-Bundle List</Text>
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
  disableOverlay: {
    zIndex: 10,
    backgroundColor: "black",
    opacity: 0.8,
    position: "absolute",
    width: ScreenWidth,
    height: ScreenHeight,
  },
  activityIndicator: {
    top: 100,
    alignSelf: "center",
    position: "absolute",
    zIndex: 11,
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
    borderBottomWidth: 1,
    alignItems: 'center',
    justifyContent: 'center'
  },
  headerTitle: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#FFB300'
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