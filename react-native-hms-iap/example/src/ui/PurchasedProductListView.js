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
      underlayColor={GLOBALS.COLORS.WHITE}
      onPress={() => consumeProduct({item}, productType)}>
      <View style={styles.container}>
        <View style={itemStyles.container}>
          <Text style={itemStyles.title}>
            Purchased Product Name: {purchasedItem.productName}
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

async function getPurchases(productType) {
  switch (productType) {
    case ProductTypes.CONSUMABLE:
      return await HmsIapModule.obtainOwnedPurchases(
        GLOBALS.CONSUMABLE.OWNED_PURCHASES_DATA,
      );
    case ProductTypes.NON_CONSUMABLE:
      return await HmsIapModule.obtainOwnedPurchases(
        GLOBALS.NON_CONSUMABLE.OWNED_PURCHASES_DATA,
      );
    case ProductTypes.SUBSCRIPTION:
      return await HmsIapModule.obtainOwnedPurchases(
        GLOBALS.SUBSCRIPTION.OWNED_PURCHASES_DATA,
      );
  }
}

async function consumeProduct({item}, productType) {
  const consumeItem = JSON.parse(item);
  if (consumeItem.purchaseToken == null) {
    Utils.notifyMessage('Purchase token cannot be null.');
  }
  if (productType !== ProductTypes.CONSUMABLE) {
    Utils.notifyMessage(
      'ConsumeOwnedPurchase consumes a consumable after the consumable is delivered to a user who has completed payment.',
    );
    return;
  }
  const consumeOwnedPurchaseData = {
    developerChallenge: GLOBALS.DEVELOPER.CHALLENGE,
    purchaseToken: consumeItem.purchaseToken,
  };
  try {
    console.log('call consumeOwnedPurchase');
    const consumePurchase = await HmsIapModule.consumeOwnedPurchase(
      consumeOwnedPurchaseData,
    );
    console.log('consumeOwnedPurchase success');
    Utils.notifyMessage(
      'consumeOwnedPurchase success ' + JSON.stringify(consumePurchase),
    );
    return consumePurchase;
  } catch (error) {
    console.log('consumeOwnedPurchase fail');
    Utils.logError(JSON.stringify(error));
  }
}

class PurchasedProductListView extends React.Component {
  constructor(inProps) {
    super(inProps);
    this.state = {inAppPurchaseDataList: []};
  }

  render() {
    return (
      <View>
        <Text style={styles.title}>{PurchaseTypes.PURCHASED}</Text>
        <FlatList
          data={this.state.inAppPurchaseDataList}
          renderItem={({item}) =>
            getProductItem({item}, this.props.productType)
          }
          keyExtractor={(item) => JSON.parse(item).purchaseToken}
        />
      </View>
    );
  }

  async componentDidMount() {
    try {
      console.log('call obtainOwnedPurchases');
      let products = await getPurchases(this.props.productType);
      console.log('obtainOwnedPurchases success');
      console.log(JSON.stringify(products.inAppPurchaseDataList));
      this.setState({inAppPurchaseDataList: products.inAppPurchaseDataList});
    } catch (error) {
      console.log('obtainOwnedPurchases fail');
      Utils.logError(JSON.stringify(error));
    }
  }
}

export default PurchasedProductListView;

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
