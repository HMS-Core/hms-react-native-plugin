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
import {StyleSheet, View} from 'react-native';
import GLOBALS from '../utils/Globals';
import ProductTypes from '../foundation/ProductTypes';
import TitleView from './TitleView';
import PurchasedProductListView from './PurchasedProductListView';
import AvailableProductsListView from './AvailableProductsListView';
import PurchaseRecordListView from './PurchaseRecordListView';

function getTitle(productType) {
  switch (productType) {
    case ProductTypes.CONSUMABLE:
      return (
        <TitleView
          title={GLOBALS.CONSUMABLE.TITLE}
          subTitle={GLOBALS.CONSUMABLE.SUBTITLE}
        />
      );
    case ProductTypes.NON_CONSUMABLE:
      return (
        <TitleView
          title={GLOBALS.NON_CONSUMABLE.TITLE}
          subTitle={GLOBALS.NON_CONSUMABLE.SUBTITLE}
        />
      );
    case ProductTypes.SUBSCRIPTION:
      return (
        <TitleView
          title={GLOBALS.SUBSCRIPTION.TITLE}
          subTitle={GLOBALS.SUBSCRIPTION.SUBTITLE}
        />
      );
  }
}

function getList(productType) {
  return (
    <View>
      <PurchasedProductListView productType={productType} />
      <AvailableProductsListView productType={productType} />
      <PurchaseRecordListView productType={productType} />
    </View>
  );
}

class ListView extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <View style={styles.sectionContainer}>
        {getTitle(this.props.productType)}
        {getList(this.props.productType)}
      </View>
    );
  }
}

export default ListView;

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
});
