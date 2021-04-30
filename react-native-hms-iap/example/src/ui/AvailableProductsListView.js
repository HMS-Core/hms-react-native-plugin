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
  TouchableOpacity,
  View
} from 'react-native';
import HMSIapModule from '@hmscore/react-native-hms-iap';
import GLOBALS from '../utils/Globals';
import PurchaseTypes from '../foundation/PurchaseTypes';
import ProductTypes from '../foundation/ProductTypes';
import Utils from '../utils/Utils';

class AvailableProductsListView extends React.Component {
  constructor() {
    super();
    this.state = {
      productList: []
    };
  }

  async componentDidMount() {
    if (this.state.productList.length === 0) {
      try {
        console.log('call getProducts');
        const response = await this.getProducts(this.props.productType);
        console.log('AvailableProductList :: ' + JSON.stringify(response));
        this.createList(response.productInfoList)
      } catch (error) {
        console.log('AvailableProductList fail:: ' + JSON.stringify(error));
        Utils.logError(JSON.stringify(error));
      }
    }
  }

  async getProducts(productType) {
    switch (productType) {
      case ProductTypes.CONSUMABLE:
        return await HMSIapModule.obtainProductInfo(
          GLOBALS.CONSUMABLE.PRODUCT_INFO_DATA
        );
      case ProductTypes.NON_CONSUMABLE:
        return await HMSIapModule.obtainProductInfo(
          GLOBALS.NON_CONSUMABLE.PRODUCT_INFO_DATA
        );
      case ProductTypes.SUBSCRIPTION:
        return await HMSIapModule.obtainProductInfo(
          GLOBALS.SUBSCRIPTION.PRODUCT_INFO_DATA
        );
    }
  }

  async buyProduct(item) {
    const productType = this.props.productType;
    let type;
    switch (productType) {
      case ProductTypes.CONSUMABLE:
        type = HMSIapModule.PRICE_TYPE_IN_APP_CONSUMABLE;
        break;
      case ProductTypes.NON_CONSUMABLE:
        type = HMSIapModule.PRICE_TYPE_IN_APP_NONCONSUMABLE;
        break;
      case ProductTypes.SUBSCRIPTION:
        type = HMSIapModule.PRICE_TYPE_IN_APP_SUBSCRIPTION;
        break;
      default:
        Utils.logError('ProductType must be specified. ');
        return;
    }

    const reservedInfo = {
      "key1": "value1"
    }
    const purchaseData = {
      priceType: type,
      productId: item.productId,
      developerPayload: GLOBALS.DEVELOPER.PAYLOAD,
      reservedInfor: JSON.stringify(reservedInfo),
    };
    try {
      console.log('call createPurchaseIntent');
      const response = await HMSIapModule.createPurchaseIntent(purchaseData);
      console.log('createPurchaseIntent :: ' + JSON.stringify(response));
      this.responseState(response)
    } catch (error) {
      console.log('createPurchaseIntent fail');
      alert(JSON.stringify(error));
    }
  }

  createList(products) {
    if (products != null) {
      let list = []
      for (let i = 0; i < products.length; i++) {
        let index = i;
        let item = products[index]

        list.push(
          <TouchableOpacity
            key={index}
            activeOpacity={.7}
            style={styles.btn}
            onPress={() => this.buyProduct(item)}>
            <View style={{ flexDirection: 'row', height: '100%' }}>
              <View style={styles.info}>
                <Text ellipsizeMode='tail' numberOfLines={1} style={styles.name}>{item.productName}</Text>
                <Text style={styles.description}>{item.productDesc}</Text>
                <Text style={styles.price}>{item.price}</Text>
              </View>
              <View style={{ flex: 3 }}>
                <Text style={styles.basket}>BUY</Text>
              </View>
            </View>
          </TouchableOpacity>
        )
      }
      this.setState({ productList: list })
      this.props.sizeChange(list.length)
    }
  }

  responseState(response) {
    if (response.errMsg === "success" || response.errMsg === "") {
      const res = JSON.stringify(response)
      this.props.onRefresh(res)
    } else {
      alert(JSON.stringify(response.errMsg))
    }
  }

  render() {
    const listHeight = this.state.productList.length * 110
    return (
      <View>
        <Text style={styles.title}>{PurchaseTypes.AVAILABLE}</Text>
        {this.state.productList.length === 0 ?
          <Text style={styles.desc}>No available product</Text>
          :
          <View style={{ height: listHeight }}>
            {this.state.productList}
          </View>
        }
      </View>
    );
  }
}

export default AvailableProductsListView;

const styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    alignItems: 'center'
  },
  title: {
    fontSize: 17,
    marginLeft: 2,
    marginBottom: 4,
    color: 'red',
    fontWeight: 'bold',
  },
  description: {
    fontSize: 14,
    marginVertical: 2,
    color: 'gray'
  },
  price: {
    fontSize: 13,
    fontWeight: 'bold',
    marginVertical: 2,
    color: 'white'
  },
  btn: {
    backgroundColor: '#222222',
    borderColor: 'white',
    borderWidth: 1,
    paddingLeft: 15,
    borderRadius: 5,
    width: 300,
    height: 100
  },
  name: {
    fontSize: 16,
    fontWeight: 'bold',
    color: 'white',
    marginTop: 15,
    maxWidth: 175,
  },
  desc: {
    color: '#222222',
    fontSize: 13,
    marginLeft: 8,
  },
  info: {
    flex: 7,
    borderRightWidth: 1,
    borderRightColor: 'white',
    height: '100%'
  },
  basket: {
    marginTop: 35,
    color: '#47d147',
    alignSelf: 'center'
  }
});


