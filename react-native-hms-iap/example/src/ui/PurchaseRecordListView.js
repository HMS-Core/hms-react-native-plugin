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
  StyleSheet,
  Text,
  Image,
  View
} from 'react-native';
import HmsIapModule from '@hmscore/react-native-hms-iap';
import GLOBALS from '../utils/Globals';
import PurchaseTypes from '../foundation/PurchaseTypes';
import ProductTypes from '../foundation/ProductTypes';
import Utils from '../utils/Utils';


class PurchaseRecordListView extends React.Component {
  constructor() {
    super();
    this.state = {
      productList: []
    };
  }

  async componentDidMount() {
    try {
      console.log('call getPurchaseRecord');
      const response = await this.getPurchaseRecord();
      console.log('PurchasedRecordListView :: ' + JSON.stringify(response.inAppPurchaseDataList));
      this.createList(response.inAppPurchaseDataList)
    } catch (error) {
      console.log('PurchaseRecordList fail:: ' + JSON.stringify(error));
      Utils.logError(JSON.stringify(error));
    }
  }

  async getPurchaseRecord() {
    const productType = this.props.productType
    switch (productType) {
      case ProductTypes.CONSUMABLE:
        return await HmsIapModule.obtainOwnedPurchaseRecord(
          GLOBALS.CONSUMABLE.OWNED_PURCHASES_DATA,
        );
      case ProductTypes.NON_CONSUMABLE:
        return await HmsIapModule.obtainOwnedPurchaseRecord(
          GLOBALS.NON_CONSUMABLE.OWNED_PURCHASES_DATA,
        );
      case ProductTypes.SUBSCRIPTION:
        return await HmsIapModule.obtainOwnedPurchaseRecord(
          GLOBALS.SUBSCRIPTION.OWNED_PURCHASES_DATA,
        );
    }
  }

  createList(products) {
    if (products != null && products.length > 0) {
      var list = []
      for (var i = 0; i < products.length; i++) {
        let index = i;
        var item = JSON.parse(products[index])
        list.push(
          <View
            key={index}
            style={styles.btn}>
            <View style={styles.info}>
              <Text numberOfLines={1} style={styles.name}>{item.productName}</Text>
              <View style={styles.priceContainer}>
                <Text style={styles.currency}>{item.currency}</Text>
                <Text style={styles.currency}>{item.price}</Text>
              </View>
            </View>

            <Image
              resizeMode="contain"
              style={styles.basket}
              source={require('../../assets/images/sandclock.png')} />

          </View>
        )
      }
      this.setState({ productList: list })
      this.props.sizeChange(list.length)
    }
  }


  render() {
    const listHeight = this.state.productList.height
    return (
      <View>
        <Text style={styles.title}>{PurchaseTypes.PURCHASED_RECORD}</Text>
        {this.state.productList.length == 0 ?
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

export default PurchaseRecordListView;

const styles = StyleSheet.create({
  container: {
    marginVertical: 8,
    marginHorizontal: 16,
  },
  title: {
    fontSize: 17,
    marginLeft: 2,
    marginBottom: 4,
    color: 'red',
    fontWeight: 'bold',
  },
  priceContainer: {
    flexDirection: 'row'
  },
  price: {
    fontSize: 14,
    fontWeight: 'bold',
    marginVertical: 2,
    color: 'white'
  },
  currency: {
    fontSize: 14,
    color: 'white',
    marginRight: 5
  },
  btn: {
    flexDirection: 'row',
    backgroundColor: '#222222',
    width: 300,
    height: 100,
    borderColor: 'white',
    borderWidth: 1,
    paddingLeft: 15,
    borderRadius: 5,
  },
  name: {
    fontSize: 16,
    fontWeight: 'bold',
    color: 'white',
    marginTop: 20,
  },
  desc: {
    color: '#222222',
    fontSize: 15,
    marginLeft: 8,
    marginBottom: 10,
  },
  info: {
    flex: 7,
    height: '100%'
  },
  basket: {
    width: 30,
    height: 30,
    alignSelf: 'center',
    flex: 3
  },
  iconTitle: {
    marginTop: 15,
    color: 'white',
    alignSelf: 'center'
  }
});


