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
  Text,
  View,
  Image,
  TouchableOpacity
} from 'react-native';
import HMSIapModule from '@hmscore/react-native-hms-iap';
import GLOBALS from '../utils/Globals';
import PurchaseTypes from '../foundation/PurchaseTypes';
import ProductTypes from '../foundation/ProductTypes';
import Utils from '../utils/Utils';


class PurchasedProductListView extends React.Component {
  constructor() {
    super();
    this.state = {
      productList: [],
      text: ""
    };
  }

  async componentDidMount() {
    try {
      console.log('call obtainOwnedPurchases');
      let response = await this.getPurchases();
      console.log('obtainOwnedPurchases success:: ' + JSON.stringify(response.inAppPurchaseDataList));
      this.createList(response.inAppPurchaseDataList)
    } catch (error) {
      console.log('obtainOwnedPurchases fail');
      Utils.logError(JSON.stringify(error));
    }
  }

  async getPurchases() {
    const productType = this.props.productType
    switch (productType) {
      case ProductTypes.CONSUMABLE:
        this.setState({ text: "USE" })
        return await HMSIapModule.obtainOwnedPurchases(
          GLOBALS.CONSUMABLE.OWNED_PURCHASES_DATA,
        );
      case ProductTypes.NON_CONSUMABLE:
        this.setState({ text: "NON_CONSUMABLE" })
        return await HMSIapModule.obtainOwnedPurchases(
          GLOBALS.NON_CONSUMABLE.OWNED_PURCHASES_DATA,
        );
      case ProductTypes.SUBSCRIPTION:
        this.setState({ text: "EDIT SUBSCRIPTION" })
        return await HMSIapModule.obtainOwnedPurchases(
          GLOBALS.SUBSCRIPTION.OWNED_PURCHASES_DATA,
        );
    }
  }

  async consumeProduct(item) {
    const token = item.purchaseToken
    if (token === null) {
      Utils.notifyMessage('Purchase token cannot be null.');
    } else {
      try {
        const consumeOwnedPurchaseData = {
          developerChallenge: GLOBALS.DEVELOPER.CHALLENGE,
          purchaseToken: token,
        };
        console.log('call consumeOwnedPurchase');
        const response = await HMSIapModule.consumeOwnedPurchase(consumeOwnedPurchaseData);
        console.log('consumeOwnedPurchase :: ' + JSON.stringify(response));
        this.responseState(response)
      } catch (error) {
        console.log('consumeOwnedPurchase fail');
        Utils.logError(JSON.stringify(error));
      }
    }
  }

  async startIapActivity(item) {
    console.log(item)
    try {
      const StartIapActivityReq = {
        type: HMSIapModule.TYPE_SUBSCRIBE_EDIT_ACTIVITY,
        subscribeProductId: item.productId
      };
      console.log('call startIapActivity');
      const response = await HMSIapModule.startIapActivity(StartIapActivityReq);
      console.log('startIapActivity :: ' + JSON.stringify(response));
      this.responseState(response)
    } catch (error) {
      console.log('startIapActivity fail');
      Utils.logError(JSON.stringify(error));
    }
  }

  createList(products) {
    if (products != null) {
      let list = []
      for (let i = 0; i < products.length; i++) {
        let index = i;
        let item = JSON.parse(products[index])
        list.push(
          <View key={index + "main"}>
            <TouchableOpacity
              key={index + "button"}
              style={styles.btn}
              onPress={() => {
                this.props.productType === ProductTypes.CONSUMABLE && this.consumeProduct(item);
                this.props.productType === ProductTypes.SUBSCRIPTION && this.startIapActivity(item);
                this.props.productType === ProductTypes.NON_CONSUMABLE && alert("Product cannot be consumed");
              }}>
              <View style={{ flexDirection: 'row', height: '100%' }}>
                <View style={styles.info}>
                  <Text numberOfLines={1} style={styles.name}>{item.productName}</Text>
                  <View style={styles.priceContainer}>
                    <Text style={styles.currency}>{item.currency}</Text>
                    <Text style={styles.currency}>{item.price}</Text>
                  </View>
                </View>
                <View style={{ flex: 3 }}>
                  <Text style={styles.iconTitle}>{this.state.text}</Text>
                </View>
              </View>
            </TouchableOpacity>

          </View>
        )
      }
      this.setState({ productList: list })
      this.props.sizeChange(list.length)
    }
  }

  responseState(response) {
    if (response.errMsg === "success") {
      const res = JSON.stringify(response)
      this.props.onRefresh(res)
    }
    else if (response.errMsg) {
      alert(response.errMsg)
    } else {
      alert("Error!")
    }
  }

  render() {
    let listHeight = this.state.productList.length * 110
    return (
      <View>
        <Text style={styles.title}>{PurchaseTypes.PURCHASED}</Text>
        {this.state.productList.length === 0 ?
          <Text style={styles.desc}>No product</Text>
          :
          <View style={{ height: listHeight }}>
            {this.state.productList}
          </View>
        }
      </View>
    );
  }
}

export default PurchasedProductListView;

const styles = StyleSheet.create({
  container: {
    padding: 20,
    marginVertical: 8,
    marginHorizontal: 16,
  },
  title: {
    fontSize: 17,
    marginLeft: 2,
    color: 'red',
    fontWeight: 'bold',
  },
  currency: {
    fontSize: 13,
    justifyContent: 'center',
    marginVertical: 2,
    textAlign: 'center',
    color: 'white',
    marginRight: 5
  },
  priceContainer: {
    flexDirection: 'row'
  },
  name: {
    fontSize: 16,
    fontWeight: 'bold',
    color: 'white',
    marginTop: 20,
    maxWidth: 175
  },
  desc: {
    color: '#222222',
    fontSize: 13,
    marginLeft: 8,
    marginBottom: 10
  },
  btn: {
    backgroundColor: '#222222',
    width: 300,
    height: 100,
    borderColor: 'white',
    borderWidth: 1,
    paddingLeft: 15,
    borderRadius: 5
  },
  info: {
    flex: 7,
    borderRightWidth: 1,
    borderRightColor: 'white',
    height: '100%'
  },
  basket: {
    width: 30,
    height: 30,
    marginTop: 10,
    alignSelf: 'center'
  },
  iconTitle: {
    marginTop: 35,
    color: '#FBCA00',
    alignSelf: 'center'
  }
});
