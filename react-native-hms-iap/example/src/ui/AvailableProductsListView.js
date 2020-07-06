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

import React from 'react';
import {
  FlatList,
  StyleSheet,
  Text,
  TouchableHighlight,
  View,
} from 'react-native';
import HmsIapModule from 'react-native-hms-iap';
import GLOBALS from '../utils/Globals';
import PurchaseTypes from '../foundation/PurchaseTypes';
import ProductTypes from '../foundation/ProductTypes';
import Utils from '../utils/Utils';

function getProductItem({item}, productType) {
  return (
    <TouchableHighlight
      style={styles.touchableHighlight}
      underlayColor={GLOBALS.COLORS.WHITE}
      onPress={() => buyProduct({item}, productType)}>
      <View style={styles.container}>
        <View style={itemStyles.container}>
          <Text style={itemStyles.title}>{item.h}</Text>
          <Text style={itemStyles.description}>{item.i}</Text>
          <Text style={itemStyles.price}>{item.c}</Text>
        </View>
      </View>
    </TouchableHighlight>
  );
}

async function getProducts(productType) {
  switch (productType) {
    case ProductTypes.CONSUMABLE:
      return await HmsIapModule.obtainProductInfo(
        GLOBALS.CONSUMABLE.PRODUCT_INFO_DATA,
      );
    case ProductTypes.NON_CONSUMABLE:
      return await HmsIapModule.obtainProductInfo(
        GLOBALS.NON_CONSUMABLE.PRODUCT_INFO_DATA,
      );
    case ProductTypes.SUBSCRIPTION:
      return await HmsIapModule.obtainProductInfo(
        GLOBALS.SUBSCRIPTION.PRODUCT_INFO_DATA,
      );
  }
}

async function buyProduct({item}, productType) {
  let type;
  switch (productType) {
    case ProductTypes.CONSUMABLE:
      type = HmsIapModule.PRICE_TYPE_IN_APP_CONSUMABLE;
      break;
    case ProductTypes.NON_CONSUMABLE:
      type = HmsIapModule.PRICE_TYPE_IN_APP_NONCONSUMABLE;
      break;
    case ProductTypes.SUBSCRIPTION:
      type = HmsIapModule.PRICE_TYPE_IN_APP_SUBSCRIPTION;
      break;
    default:
      Utils.logError('ProductType must be specified. ');
      return;
  }
  const purchaseData = {
    priceType: type,
    productId: item.a,
    developerPayload: GLOBALS.DEVELOPER.PAYLOAD,
  };
  try {
    console.log('call createPurchaseIntent');
    const intentResult = await HmsIapModule.createPurchaseIntent(
      purchaseData,
    );
    console.log('createPurchaseIntent success');
    Utils.notifyMessage(JSON.stringify(intentResult));
    return intentResult;
  } catch (error) {
    console.log('createPurchaseIntent fail');
    Utils.logError(JSON.stringify(error));
  }
}

class AvailableProductsListView extends React.Component {
  constructor(props) {
    super(props);
    this.state = {productInfoList: []};
  }

  render() {
    return (
      <View>
        <Text style={styles.title}>{PurchaseTypes.AVAILABLE}</Text>
        <FlatList
          data={this.state.productInfoList}
          renderItem={({item}) =>
            getProductItem({item}, this.props.productType)
          }
          keyExtractor={(item) => item.h}
        />
      </View>
    );
  }

  async componentDidMount() {
    try {
      console.log('call obtainProductInfo');
      let products = await getProducts(this.props.productType);
      console.log('obtainProductInfo success');
      console.log(products.productInfoList);
      this.setState({productInfoList: products.productInfoList});
    } catch (error) {
      console.log('obtainProductInfo fail');
      Utils.logError(JSON.stringify(error));
    }
  }
}

export default AvailableProductsListView;

const itemStyles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    marginVertical: 8,
  },
  title: {
    fontSize: 16,
    marginVertical: 8,
  },
  description: {
    fontSize: 14,
    justifyContent: 'center',
    marginVertical: 2,
    textAlign: 'center',
  },
  price: {
    fontSize: 14,
    fontWeight: 'bold',
    marginVertical: 2,
  },
});

const styles = StyleSheet.create({
  title: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  container: {
    padding: 20,
    marginVertical: 8,
    marginHorizontal: 16,
  },
  touchableHighlight: {
    backgroundColor: GLOBALS.COLORS.PRIMARY_COLOR,
    marginVertical: 2,
    padding: 10,
  },
});
