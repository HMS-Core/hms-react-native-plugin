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
  const purchasedItem = JSON.parse(item);
  return (
    <TouchableHighlight
      style={styles.touchableHighlight}
      underlayColor={GLOBALS.COLORS.WHITE}>
      <View style={styles.container}>
        <View style={itemStyles.container}>
          <Text style={itemStyles.title}>
            Recorded Product Name: {purchasedItem.productName}
          </Text>
          <Text style={itemStyles.currency}>
            currency:
            {purchasedItem.currency}
          </Text>
        </View>
      </View>
    </TouchableHighlight>
  );
}

async function getPurchaseRecord(productType) {
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

class PurchaseRecordListView extends React.Component {
  constructor(inProps) {
    super(inProps);
    this.state = {inAppPurchaseDataList: []};
  }

  render() {
    return (
      <View>
        <Text style={styles.title}>{PurchaseTypes.PURCHASED_RECORD}</Text>
        <FlatList
          data={this.state.inAppPurchaseDataList}
          renderItem={({item}) =>
            getProductItem({item}, this.props.productType)
          }
          keyExtractor={(item) => JSON.parse(item).productId}
        />
      </View>
    );
  }

  async componentDidMount() {
    try {
      console.log('call obtainOwnedPurchaseRecord');
      let products = await getPurchaseRecord(this.props.productType);
      console.log('obtainOwnedPurchaseRecord success');
      console.log(JSON.stringify(products.inAppPurchaseDataList));
      this.setState({inAppPurchaseDataList: products.inAppPurchaseDataList});
    } catch (error) {
      console.log('obtainOwnedPurchaseRecord fail');
      Utils.logError(JSON.stringify(error));
    }
  }
}

export default PurchaseRecordListView;

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
    textAlign: 'center',
  },
  currency: {
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
